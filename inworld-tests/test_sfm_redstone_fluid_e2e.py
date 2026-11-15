#!/usr/bin/env python3
"""
inworld-tests/test_sfm_redstone_fluid_e2e.py:
Comprehensive End-to-End Test Suite for SFM Redstone Signals and Fluid Logistics.

Constructs a 4x3 distributed grid of 12 distinct SFM stations spaced 48 blocks apart
over an ephemeral copy of the pristine 'superflat_empty' template.
Re-uses the existing running Minecraft instance (via autoworld/exitworld) for speed.

Verified Features:
 1. Digital Redstone Output Modulation (0 vs 15)
 2. Analog Redstone Emission (Arbitrary levels: level 7)
 3. Sided Redstone Directional Emission (e.g. TOP SIDE level 11 vs 0 on others)
 4. Remote Redstone Receiver & Trigger (EVERY REDSTONE PULSE on BlockCableInputName)
 5. Redstone Conditional Gating (IF REDSTONE LEVEL >= 8)
 6. Redstone Signal Propagation & Wire Introspection
 7. Basic Fluid Transfer (GT Debug Tank ID 238 -> GT Supertank LV ID 130)
 8. Exact Fluid Quantity Limiting & Batching (INPUT 2500 fluid:lava)
 9. Fluid Retention Floor (INPUT RETAIN 2000 fluid:water)
10. Multi-Fluid Routing & Identifier Filtering (fluid:water vs fluid:lava)
11. Fluid Voiding into Extra Utilities Fluid Trash Can (ExtraUtilities:trashcan:1)
12. Conditional Fluid Logic (IF src HAS > 3000 fluid:water)
"""

import sys
import os
import time
import json
import shutil
import socket
from pathlib import Path
from typing import Dict, Any, List, Optional

# Monorepo root setup
from nhdev import NH_DEV_ROOT as ROOT_DIR

from tools.agentharness import HarnessClient
from tools.bot.world_manager import WorldSandbox
from tools.mcfleet.client import FleetClient
from tools.runner_instance import ensure_sfm_instance_running

OUTPUT_DIR = ROOT_DIR / "output" / "sfm_redstone_fluid"
ARTIFACTS_DIR = Path("/home/mgomezch/.gemini/antigravity/brain/a95f94f4-9edd-4aba-8218-d471d3db8067")
PRISM_INSTANCE_DIR = Path.home() / ".var/app/org.prismlauncher.PrismLauncher/data/PrismLauncher/instances/GTNH-2.9-sfm-dev/.minecraft"

HARNESS_PORT = 25572


def get_saves_dir(client: FleetClient, harness: Optional[HarnessClient] = None, inst_name: str = "sfm-test-runner") -> Path:
    if harness:
        try:
            h_dir = harness.saves_dir()
            if h_dir and os.path.isdir(h_dir):
                return Path(h_dir)
        except Exception:
            pass
    try:
        inst = client.get_instance(inst_name)
        if inst and inst.status.mount_point:
            m = Path(inst.status.mount_point)
            if (m / ".minecraft" / "saves").is_dir():
                return m / ".minecraft" / "saves"
            if (m / "minecraft" / "saves").is_dir():
                return m / "minecraft" / "saves"
            return m / "saves"
    except Exception:
        pass
    return PRISM_INSTANCE_DIR / "saves"


def get_inv_items(harness: HarnessClient, x: int, y: int, z: int) -> List[Dict[str, Any]]:
    raw = harness.container_info(x=x, y=y, z=z)
    try:
        data = raw
        return data.get("slots", [])
    except Exception:
        return []


def count_items(slots: List[Dict[str, Any]], item_id: Optional[str] = None) -> int:
    total = 0
    for s in slots:
        if item_id is None or s.get("item") == item_id:
            total += s.get("count", 0)
    return total


def get_redstone_data(harness: HarnessClient, x: int, y: int, z: int) -> Dict[str, Any]:
    rs = harness.redstone_info(x, y, z)
    if isinstance(rs, str):
        try:
            return rs
        except Exception:
            return {}
    return rs if isinstance(rs, dict) else {}


def get_tank_fluid(harness: HarnessClient, x: int, y: int, z: int) -> Dict[str, Any]:
    raw = harness.te_fluid(x, y, z)
    if isinstance(raw, str):
        try:
            data = raw
        except Exception:
            data = {}
    elif isinstance(raw, dict):
        data = raw
    else:
        data = {}
    tanks = data.get("tanks", [])
    if tanks:
        return tanks[0]
    return {"fluid": "empty", "amount": 0, "capacity": 0}


def run_test_suite():
    client = FleetClient()
    ensure_sfm_instance_running()

    harness = HarnessClient(port=HARNESS_PORT)

    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

    world_name = "sfm_rf_test"
    saves_dir = get_saves_dir(client, harness, "sfm-test-runner")
    saves_dir.mkdir(parents=True, exist_ok=True)
    print(f"[*] Mounting ephemeral WorldSandbox over 'superflat_empty' into '{saves_dir}' as '{world_name}'...")

    def safe_exit():
        try:
            harness.exitworld()
        except Exception:
            pass

    sandbox = WorldSandbox("superflat_empty", world_name=world_name, saves_dir=saves_dir, on_unmount=safe_exit)

    with sandbox:
        print(f"[*] Launching world '{world_name}'...")
        res = harness.autoworld(name=world_name)
        print(f"[<] {res}")

        print("[*] Waiting for player to enter world...")
        start_w = time.time()
        in_world = False
        while time.time() - start_w < 120:
            st = harness.state()
            if "IN_WORLD" in st:
                in_world = True
                print(f"[+] In world: {st}")
                break
            time.sleep(1.0)

        if not in_world:
            raise RuntimeError(f"Failed to enter world, status: {harness.status()}")

        time.sleep(1.5)
        harness.close_gui()
        time.sleep(0.5)

        # Baseline game settings
        print("[*] Setting world environment...")
        harness.chat(message="/gamemode 1")
        harness.chat(message="/gamerule doDaylightCycle false")
        harness.chat(message="/gamerule doMobSpawning false")
        harness.chat(message="/time set 6000")
        harness.chat(message="/weather clear 999999")
        harness.chat(message="/difficulty 0")
        harness.chat(message="/cofh killall *")
        harness.chat(message="/setworldspawn 72 4 48")
        time.sleep(0.5)

        # 12 Stations on 48-block grid
        stations = [
            # Row 0 (Z = 0): Redstone Basics
            {"id": "01_digital_redstone", "col": 0, "row": 0, "title": "Digital Redstone Output (0 vs 15)"},
            {"id": "02_analog_redstone", "col": 1, "row": 0, "title": "Analog Redstone Output (Level 7)"},
            {"id": "03_sided_redstone", "col": 2, "row": 0, "title": "Sided Directional Redstone Output"},
            {"id": "04_redstone_pulse_trigger", "col": 3, "row": 0, "title": "Redstone Pulse Trigger (EVERY REDSTONE PULSE)"},

            # Row 1 (Z = 48): Redstone Logic & Fluid Basics
            {"id": "05_redstone_conditional", "col": 0, "row": 1, "title": "Redstone Conditional Gating (IF REDSTONE LEVEL)"},
            {"id": "06_wire_propagation", "col": 1, "row": 1, "title": "Redstone Wire Signal Propagation"},
            {"id": "07_basic_fluid_transfer", "col": 2, "row": 1, "title": "Basic Fluid Transfer (GT Debug -> Supertank)"},
            {"id": "08_fluid_quantity_limit", "col": 3, "row": 1, "title": "Fluid Quantity Batching (2500 mB)"},

            # Row 2 (Z = 96): Advanced Fluid Logistics
            {"id": "09_fluid_retention_floor", "col": 0, "row": 2, "title": "Fluid Retention Floor (RETAIN 2000)"},
            {"id": "10_multi_fluid_routing", "col": 1, "row": 2, "title": "Multi-Fluid Identifier Routing (Water vs Lava)"},
            {"id": "11_fluid_voiding_trashcan", "col": 2, "row": 2, "title": "Fluid Voiding (Extra Utilities Fluid Trash Can)"},
            {"id": "12_fluid_conditional_logic", "col": 3, "row": 2, "title": "Conditional Fluid Logic (IF HAS > 3000)"},
        ]

        for s in stations:
            s["gx"] = s["col"] * 48
            s["gz"] = s["row"] * 48

        test_results = []

        # -------------------------------------------------------------
        # Station 1: Digital Redstone Output (0 vs 15)
        # -------------------------------------------------------------
        print("\n=======================================================")
        print("Executing Station 1: Digital Redstone Output")
        s1 = stations[0]
        x1, z1 = s1["gx"], s1["gz"]
        harness.clear_area(x1 - 2, 4, z1 - 2, x1 + 6, 8, z1 + 4)
        for dx in range(-1, 5):
            for dz in range(-1, 2):
                harness.set_block(x1 + dx, 3, z1 + dz, "stone", 0)

        harness.set_block(x1, 4, z1, "superfactorymanager:manager", 0)
        harness.set_block(x1 + 1, 4, z1, "superfactorymanager:cable", 0)
        harness.set_block(x1 + 2, 4, z1, "StevesFactoryManager:BlockCableOutputName", 0)
        harness.set_block(x1 + 2, 5, z1, "stone", 0)
        time.sleep(0.5)

        harness.sfm_label(x=x1, y=4, z=z1, label="emit", bx=x1+2, by=4, bz=z1)
        prog1_on = "EVERY 20 TICKS DO\n  OUTPUT 15 REDSTONE LEVEL TO \"emit\"\nEND\n"
        harness.sfm_program(x=x1, y=4, z=z1, code=prog1_on)
        time.sleep(1.2)
        harness.sfm_update(x=x1, y=4, z=z1)
        time.sleep(0.5)

        rs_on = get_redstone_data(harness, x1 + 2, 4, z1)
        print(f"[Station 1 ON] Redstone info: maxEmitted={rs_on.get('maxEmittedPower')}, strengths={rs_on.get('emitterStrengths')}")
        assert rs_on.get("maxEmittedPower") == 15, f"Expected 15, got {rs_on}"

        # Turn OFF
        prog1_off = "EVERY 20 TICKS DO\n  OUTPUT 0 REDSTONE LEVEL TO \"emit\"\nEND\n"
        harness.sfm_program(x=x1, y=4, z=z1, code=prog1_off)
        time.sleep(1.2)
        harness.sfm_update(x=x1, y=4, z=z1)
        time.sleep(0.5)

        rs_off = get_redstone_data(harness, x1 + 2, 4, z1)
        print(f"[Station 1 OFF] Redstone info: maxEmitted={rs_off.get('maxEmittedPower')}, strengths={rs_off.get('emitterStrengths')}")
        assert rs_off.get("maxEmittedPower") == 0, f"Expected 0, got {rs_off}"
        test_results.append({"id": s1["id"], "title": s1["title"], "status": "PASS", "details": "Verified digital emission 15 then 0 via TileEntityOutput."})

        # -------------------------------------------------------------
        # Station 2: Analog Redstone Output (Level 7)
        # -------------------------------------------------------------
        print("\n=======================================================")
        print("Executing Station 2: Analog Redstone Output")
        s2 = stations[1]
        x2, z2 = s2["gx"], s2["gz"]
        harness.clear_area(x2 - 2, 4, z2 - 2, x2 + 6, 8, z2 + 4)
        for dx in range(-1, 5):
            for dz in range(-1, 2):
                harness.set_block(x2 + dx, 3, z2 + dz, "stone", 0)

        harness.set_block(x2, 4, z2, "superfactorymanager:manager", 0)
        harness.set_block(x2 + 1, 4, z2, "superfactorymanager:cable", 0)
        harness.set_block(x2 + 2, 4, z2, "StevesFactoryManager:BlockCableOutputName", 0)
        time.sleep(0.5)

        harness.sfm_label(x=x2, y=4, z=z2, label="emit", bx=x2+2, by=4, bz=z2)
        prog2 = "EVERY 20 TICKS DO\n  OUTPUT 7 REDSTONE LEVEL TO \"emit\"\nEND\n"
        harness.sfm_program(x=x2, y=4, z=z2, code=prog2)
        time.sleep(1.2)
        harness.sfm_update(x=x2, y=4, z=z2)
        time.sleep(0.5)

        rs2 = get_redstone_data(harness, x2 + 2, 4, z2)
        print(f"[Station 2] Redstone info: maxEmitted={rs2.get('maxEmittedPower')}, strengths={rs2.get('emitterStrengths')}")
        assert rs2.get("maxEmittedPower") == 7, f"Expected 7, got {rs2}"
        test_results.append({"id": s2["id"], "title": s2["title"], "status": "PASS", "details": "Verified analog emission at level 7."})

        # -------------------------------------------------------------
        # Station 3: Sided Directional Redstone Output
        # -------------------------------------------------------------
        print("\n=======================================================")
        print("Executing Station 3: Sided Directional Redstone Output")
        s3 = stations[2]
        x3, z3 = s3["gx"], s3["gz"]
        harness.clear_area(x3 - 2, 4, z3 - 2, x3 + 6, 8, z3 + 4)
        for dx in range(-1, 5):
            for dz in range(-1, 2):
                harness.set_block(x3 + dx, 3, z3 + dz, "stone", 0)

        harness.set_block(x3, 4, z3, "superfactorymanager:manager", 0)
        harness.set_block(x3 + 1, 4, z3, "superfactorymanager:cable", 0)
        harness.set_block(x3 + 2, 4, z3, "StevesFactoryManager:BlockCableOutputName", 0)
        time.sleep(0.5)

        harness.sfm_label(x=x3, y=4, z=z3, label="emit", bx=x3+2, by=4, bz=z3)
        prog3 = "EVERY 20 TICKS DO\n  OUTPUT 11 REDSTONE LEVEL TO \"emit\" TOP SIDE\nEND\n"
        harness.sfm_program(x=x3, y=4, z=z3, code=prog3)
        time.sleep(1.2)
        harness.sfm_update(x=x3, y=4, z=z3)
        time.sleep(0.5)

        rs3 = get_redstone_data(harness, x3 + 2, 4, z3)
        emitted_sides = rs3.get("emittedSides", {})
        print(f"[Station 3] Sided emission: {emitted_sides}")
        assert emitted_sides.get("UP") == 11, f"Expected UP=11, got {emitted_sides}"
        assert emitted_sides.get("NORTH", 0) == 0, f"Expected NORTH=0, got {emitted_sides}"
        assert emitted_sides.get("SOUTH", 0) == 0, f"Expected SOUTH=0, got {emitted_sides}"
        assert emitted_sides.get("EAST", 0) == 0, f"Expected EAST=0, got {emitted_sides}"
        assert emitted_sides.get("WEST", 0) == 0, f"Expected WEST=0, got {emitted_sides}"
        assert emitted_sides.get("DOWN", 0) == 0, f"Expected DOWN=0, got {emitted_sides}"
        test_results.append({"id": s3["id"], "title": s3["title"], "status": "PASS", "details": "Verified UP=11 while all 5 other sides remain 0."})

        # -------------------------------------------------------------
        # Station 4: Redstone Pulse Trigger (EVERY REDSTONE PULSE)
        # -------------------------------------------------------------
        print("\n=======================================================")
        print("Executing Station 4: Redstone Pulse Trigger")
        s4 = stations[3]
        x4, z4 = s4["gx"], s4["gz"]
        harness.clear_area(x4 - 2, 4, z4 - 2, x4 + 6, 8, z4 + 4)
        for dx in range(-1, 5):
            for dz in range(-1, 2):
                harness.set_block(x4 + dx, 3, z4 + dz, "stone", 0)

        harness.set_block(x4, 4, z4, "superfactorymanager:manager", 0)
        harness.set_block(x4 + 1, 4, z4, "superfactorymanager:cable", 0)
        harness.set_block(x4 + 2, 4, z4, "StevesFactoryManager:BlockCableInputName", 0)
        harness.set_block(x4, 5, z4, "chest", 0)
        harness.set_block(x4 + 2, 5, z4, "chest", 0)
        time.sleep(0.5)

        harness.set_container_slot(x4, 5, z4, 0, "minecraft:gold_ingot", 64)
        harness.sfm_label(x=x4, y=4, z=z4, label="src", bx=x4, by=5, bz=z4)
        harness.sfm_label(x=x4, y=4, z=z4, label="dst", bx=x4+2, by=5, bz=z4)
        harness.sfm_label(x=x4, y=4, z=z4, label="rcv", bx=x4+2, by=4, bz=z4)

        prog4 = "EVERY REDSTONE PULSE DO\n  INPUT 16 FROM \"src\"\n  OUTPUT TO \"dst\"\nEND\n"
        harness.sfm_program(x=x4, y=4, z=z4, code=prog4)
        time.sleep(1.2)
        harness.sfm_update(x=x4, y=4, z=z4)
        time.sleep(0.5)

        # Before pulse: dst must have 0
        items_pre = count_items(get_inv_items(harness, x4 + 2, 5, z4), "minecraft:gold_ingot")
        print(f"[Station 4 Pre-pulse] Dst items: {items_pre}")
        assert items_pre == 0, f"Expected 0 items before pulse, got {items_pre}"

        # Deliver rising-edge pulse: place redstone_block next to receiver at (x4+3, 4, z4)
        print("Delivering pulse 1...")
        harness.set_block(x4 + 3, 4, z4, "redstone_block", 0)
        time.sleep(0.2)
        harness.sfm_update(x=x4, y=4, z=z4)
        time.sleep(0.5)

        items_pulse1 = count_items(get_inv_items(harness, x4 + 2, 5, z4), "minecraft:gold_ingot")
        print(f"[Station 4 Pulse 1] Dst items: {items_pulse1}")
        assert items_pulse1 == 16, f"Expected 16 items after pulse 1, got {items_pulse1}"

        # Remove power (falling edge) - should NOT trigger
        harness.set_block(x4 + 3, 4, z4, "air", 0)
        time.sleep(0.2)
        harness.sfm_update(x=x4, y=4, z=z4)
        time.sleep(0.3)
        items_mid = count_items(get_inv_items(harness, x4 + 2, 5, z4), "minecraft:gold_ingot")
        assert items_mid == 16, f"Falling edge triggered unexpectedly: {items_mid}"

        # Deliver pulse 2 (rising edge)
        print("Delivering pulse 2...")
        harness.set_block(x4 + 3, 4, z4, "redstone_block", 0)
        time.sleep(0.2)
        harness.sfm_update(x=x4, y=4, z=z4)
        time.sleep(0.5)
        items_pulse2 = count_items(get_inv_items(harness, x4 + 2, 5, z4), "minecraft:gold_ingot")
        print(f"[Station 4 Pulse 2] Dst items: {items_pulse2}")
        assert items_pulse2 == 32, f"Expected 32 items after pulse 2, got {items_pulse2}"
        test_results.append({"id": s4["id"], "title": s4["title"], "status": "PASS", "details": "Verified rising-edge pulse trigger with 16 items transferred per pulse (32 total)."})

        # -------------------------------------------------------------
        # Station 5: Redstone Conditional Gating (IF REDSTONE LEVEL >= 8)
        # -------------------------------------------------------------
        print("\n=======================================================")
        print("Executing Station 5: Redstone Conditional Gating")
        s5 = stations[4]
        x5, z5 = s5["gx"], s5["gz"]
        harness.clear_area(x5 - 2, 4, z5 - 2, x5 + 6, 8, z5 + 4)
        for dx in range(-1, 5):
            for dz in range(-1, 2):
                harness.set_block(x5 + dx, 3, z5 + dz, "stone", 0)

        harness.set_block(x5, 4, z5, "superfactorymanager:manager", 0)
        harness.set_block(x5 + 1, 4, z5, "superfactorymanager:cable", 0)
        harness.set_block(x5 + 2, 4, z5, "StevesFactoryManager:BlockCableInputName", 0)
        harness.set_block(x5, 5, z5, "chest", 0)
        harness.set_block(x5 + 2, 5, z5, "chest", 0)
        time.sleep(0.5)

        harness.set_container_slot(x5, 5, z5, 0, "minecraft:diamond", 64)
        harness.sfm_label(x=x5, y=4, z=z5, label="src", bx=x5, by=5, bz=z5)
        harness.sfm_label(x=x5, y=4, z=z5, label="dst", bx=x5+2, by=5, bz=z5)
        harness.sfm_label(x=x5, y=4, z=z5, label="rcv", bx=x5+2, by=4, bz=z5)

        prog5 = "EVERY 20 TICKS DO\n  IF REDSTONE LEVEL >= 8 THEN\n    INPUT 10 FROM \"src\"\n    OUTPUT TO \"dst\"\n  END\nEND\n"
        harness.sfm_program(x=x5, y=4, z=z5, code=prog5)
        time.sleep(1.2)
        harness.sfm_update(x=x5, y=4, z=z5)
        time.sleep(0.5)

        # Without power: conditional fails
        pre5 = count_items(get_inv_items(harness, x5 + 2, 5, z5), "minecraft:diamond")
        print(f"[Station 5 Pre-power] Dst diamonds: {pre5}")
        assert pre5 == 0, f"Expected 0, got {pre5}"

        # Apply power (redstone_block is power 15 >= 8)
        harness.set_block(x5 + 3, 4, z5, "redstone_block", 0)
        time.sleep(1.2)
        harness.sfm_update(x=x5, y=4, z=z5)
        time.sleep(0.5)

        post5 = count_items(get_inv_items(harness, x5 + 2, 5, z5), "minecraft:diamond")
        print(f"[Station 5 Powered] Dst diamonds: {post5}")
        assert post5 >= 10, f"Expected at least 10 diamonds, got {post5}"

        # Remove power
        harness.set_block(x5 + 3, 4, z5, "air", 0)
        time.sleep(0.5)
        post5_stopped = count_items(get_inv_items(harness, x5 + 2, 5, z5), "minecraft:diamond")
        time.sleep(1.2)
        harness.sfm_update(x=x5, y=4, z=z5)
        time.sleep(0.5)
        post5_unpowered = count_items(get_inv_items(harness, x5 + 2, 5, z5), "minecraft:diamond")
        assert post5_unpowered == post5_stopped, f"Expected unchanged {post5_stopped} diamonds after power removed, got {post5_unpowered}"
        test_results.append({"id": s5["id"], "title": s5["title"], "status": "PASS", "details": "Gated item transfer based on network redstone signal level >= 8."})

        # -------------------------------------------------------------
        # Station 6: Redstone Wire Signal Propagation
        # -------------------------------------------------------------
        print("\n=======================================================")
        print("Executing Station 6: Redstone Wire Signal Propagation")
        s6 = stations[5]
        x6, z6 = s6["gx"], s6["gz"]
        harness.clear_area(x6 - 2, 4, z6 - 2, x6 + 6, 8, z6 + 4)
        for dx in range(-1, 5):
            for dz in range(-1, 2):
                harness.set_block(x6 + dx, 3, z6 + dz, "stone", 0)

        harness.set_block(x6, 4, z6, "superfactorymanager:manager", 0)
        harness.set_block(x6 + 1, 4, z6, "superfactorymanager:cable", 0)
        harness.set_block(x6 + 2, 4, z6, "StevesFactoryManager:BlockCableOutputName", 0)
        # Redstone wire adjacent on top of stone
        harness.set_block(x6 + 3, 3, z6, "stone", 0)
        harness.set_block(x6 + 3, 4, z6, "redstone_wire", 0)
        time.sleep(0.5)

        harness.sfm_label(x=x6, y=4, z=z6, label="emit", bx=x6+2, by=4, bz=z6)
        prog6 = "EVERY 20 TICKS DO\n  OUTPUT 14 REDSTONE LEVEL TO \"emit\"\nEND\n"
        harness.sfm_program(x=x6, y=4, z=z6, code=prog6)
        time.sleep(1.2)
        harness.sfm_update(x=x6, y=4, z=z6)
        time.sleep(0.5)

        rs6_emit = get_redstone_data(harness, x6 + 2, 4, z6)
        rs6_wire = get_redstone_data(harness, x6 + 3, 4, z6)
        print(f"[Station 6] Emitter: {rs6_emit.get('maxEmittedPower')}, Wire info: direct={rs6_wire.get('powerInput')}, meta={rs6_wire.get('meta')}")
        assert rs6_emit.get("maxEmittedPower") == 14, f"Expected 14 emitted, got {rs6_emit}"
        wire_signal = rs6_wire.get("meta", 0)
        assert wire_signal >= 13, f"Expected wire signal >= 13 (propagated from 14), got {wire_signal}"
        test_results.append({"id": s6["id"], "title": s6["title"], "status": "PASS", "details": f"Emitted signal 14 from cable output; wire at neighbor position registered signal {wire_signal}."})

        # -------------------------------------------------------------
        # Station 7: Basic Fluid Transfer (GT Debug Tank -> GT Supertank LV)
        # -------------------------------------------------------------
        print("\n=======================================================")
        print("Executing Station 7: Basic Fluid Transfer (Debug -> Supertank)")
        s7 = stations[6]
        x7, z7 = s7["gx"], s7["gz"]
        harness.clear_area(x7 - 2, 4, z7 - 2, x7 + 6, 8, z7 + 4)
        for dx in range(-1, 5):
            for dz in range(-1, 2):
                harness.set_block(x7 + dx, 3, z7 + dz, "stone", 0)

        harness.set_block(x7, 4, z7, "superfactorymanager:manager", 0)
        harness.set_block(x7 + 1, 4, z7, "superfactorymanager:cable", 0)

        # Set GT Debug Tank (ID 238) at (x7, 5, z7) facing 2 (North)
        harness.set_gt_machine(x7, 5, z7, 238, 2)
        # Set GT Supertank LV (ID 130) at (x7+1, 5, z7) facing 2 (North)
        harness.set_gt_machine(x7 + 1, 5, z7, 130, 2)
        time.sleep(0.5)

        # Configure debug tank with water
        harness.configure_debug_tank(x7, 5, z7, "water", False)
        # Configure supertank with void excess = false
        harness.configure_supertank(x7 + 1, 5, z7, False)
        time.sleep(0.5)

        harness.sfm_label(x=x7, y=4, z=z7, label="src", bx=x7, by=5, bz=z7)
        harness.sfm_label(x=x7, y=4, z=z7, label="dst", bx=x7+1, by=5, bz=z7)

        prog7 = "EVERY 20 TICKS DO\n  INPUT 1000 fluid:water FROM \"src\"\n  OUTPUT TO \"dst\"\nEND\n"
        harness.sfm_program(x=x7, y=4, z=z7, code=prog7)
        time.sleep(1.2)
        harness.sfm_update(x=x7, y=4, z=z7)
        time.sleep(0.5)

        tank7 = get_tank_fluid(harness, x7 + 1, 5, z7)
        print(f"[Station 7] Destination Supertank fluid: {tank7}")
        assert tank7.get("fluid") == "water" and tank7.get("amount") in (1000, 2000), f"Expected 1000 or 2000 water, got {tank7}"
        test_results.append({"id": s7["id"], "title": s7["title"], "status": "PASS", "details": f"Transferred {tank7.get('amount')} mB water from GT Debug Tank (ID 238) to GT Supertank LV (ID 130)."})

        # -------------------------------------------------------------
        # Station 8: Fluid Quantity Limiting & Batching (2500 mB)
        # -------------------------------------------------------------
        print("\n=======================================================")
        print("Executing Station 8: Fluid Quantity Batching (2500 mB)")
        s8 = stations[7]
        x8, z8 = s8["gx"], s8["gz"]
        harness.clear_area(x8 - 2, 4, z8 - 2, x8 + 6, 8, z8 + 4)
        for dx in range(-1, 5):
            for dz in range(-1, 2):
                harness.set_block(x8 + dx, 3, z8 + dz, "stone", 0)

        harness.set_block(x8, 4, z8, "superfactorymanager:manager", 0)
        harness.set_block(x8 + 1, 4, z8, "superfactorymanager:cable", 0)

        # Source Supertank at (x8, 5, z8), Dest Supertank at (x8+1, 5, z8)
        harness.set_gt_machine(x8, 5, z8, 130, 2)
        harness.set_gt_machine(x8 + 1, 5, z8, 130, 2)
        time.sleep(0.5)

        harness.configure_supertank(x8, 5, z8, False)
        harness.configure_supertank(x8 + 1, 5, z8, False)
        time.sleep(0.3)

        # Fill Source Supertank with 8000 mB lava
        harness.fill_fluid(x8, 5, z8, "lava", 8000)
        time.sleep(0.3)

        src8_init = get_tank_fluid(harness, x8, 5, z8)
        print(f"[Station 8 Init] Source fluid: {src8_init}")
        assert src8_init.get("fluid") == "lava" and src8_init.get("amount") == 8000, f"Failed pre-filling source tank: {src8_init}"

        harness.sfm_label(x=x8, y=4, z=z8, label="src", bx=x8, by=5, bz=z8)
        harness.sfm_label(x=x8, y=4, z=z8, label="dst", bx=x8+1, by=5, bz=z8)

        prog8 = "EVERY 20 TICKS DO\n  INPUT 2500 fluid:lava FROM \"src\"\n  OUTPUT TO \"dst\"\nEND\n"
        harness.sfm_program(x=x8, y=4, z=z8, code=prog8)
        time.sleep(1.2)

        dst8_1 = get_tank_fluid(harness, x8 + 1, 5, z8)
        src8_1 = get_tank_fluid(harness, x8, 5, z8)
        print(f"[Station 8 Cycle 1] Dst: {dst8_1}, Src: {src8_1}")
        assert dst8_1.get("amount") in (2500, 5000) and src8_1.get("amount") + dst8_1.get("amount") == 8000, f"Expected 2500 or 5000 moved, got Dst={dst8_1}, Src={src8_1}"

        # Cycle 2
        time.sleep(1.2)
        dst8_2 = get_tank_fluid(harness, x8 + 1, 5, z8)
        src8_2 = get_tank_fluid(harness, x8, 5, z8)
        print(f"[Station 8 Cycle 2] Dst: {dst8_2}, Src: {src8_2}")
        assert dst8_2.get("amount") > dst8_1.get("amount") and dst8_2.get("amount") % 2500 == 0 and src8_2.get("amount") + dst8_2.get("amount") == 8000, f"Expected > {dst8_1.get('amount')} moved, got Dst={dst8_2}, Src={src8_2}"
        test_results.append({"id": s8["id"], "title": s8["title"], "status": "PASS", "details": f"Transferred exact 2500 mB increments across multiple cycles ({dst8_1.get('amount')} -> {dst8_2.get('amount')} mB)."})

        # -------------------------------------------------------------
        # Station 9: Fluid Retention Floor (INPUT RETAIN 2000)
        # -------------------------------------------------------------
        print("\n=======================================================")
        print("Executing Station 9: Fluid Retention Floor (RETAIN 2000)")
        s9 = stations[8]
        x9, z9 = s9["gx"], s9["gz"]
        harness.clear_area(x9 - 2, 4, z9 - 2, x9 + 6, 8, z9 + 4)
        for dx in range(-1, 5):
            for dz in range(-1, 2):
                harness.set_block(x9 + dx, 3, z9 + dz, "stone", 0)

        harness.set_block(x9, 4, z9, "superfactorymanager:manager", 0)
        harness.set_block(x9 + 1, 4, z9, "superfactorymanager:cable", 0)

        harness.set_gt_machine(x9, 5, z9, 130, 2)
        harness.set_gt_machine(x9 + 1, 5, z9, 130, 2)
        time.sleep(0.5)

        harness.configure_supertank(x9, 5, z9, False)
        harness.configure_supertank(x9 + 1, 5, z9, False)
        time.sleep(0.3)

        # Pre-fill source with 5000 mB water
        harness.fill_fluid(x9, 5, z9, "water", 5000)
        time.sleep(0.3)

        harness.sfm_label(x=x9, y=4, z=z9, label="src", bx=x9, by=5, bz=z9)
        harness.sfm_label(x=x9, y=4, z=z9, label="dst", bx=x9+1, by=5, bz=z9)

        prog9 = "EVERY 20 TICKS DO\n  INPUT RETAIN 2000 fluid:water FROM \"src\"\n  OUTPUT TO \"dst\"\nEND\n"
        harness.sfm_program(x=x9, y=4, z=z9, code=prog9)
        time.sleep(1.2)

        src9 = get_tank_fluid(harness, x9, 5, z9)
        dst9 = get_tank_fluid(harness, x9 + 1, 5, z9)
        print(f"[Station 9 Cycle 1] Src: {src9}, Dst: {dst9}")
        assert src9.get("amount") == 2000, f"Expected 2000 retained in source, got {src9}"
        assert dst9.get("amount") == 3000, f"Expected 3000 transferred to destination, got {dst9}"

        # Run cycle 2 - should transfer 0 because retention floor 2000 is met
        time.sleep(1.2)
        src9_2 = get_tank_fluid(harness, x9, 5, z9)
        dst9_2 = get_tank_fluid(harness, x9 + 1, 5, z9)
        print(f"[Station 9 Cycle 2] Src: {src9_2}, Dst: {dst9_2}")
        assert src9_2.get("amount") == 2000 and dst9_2.get("amount") == 3000, f"Retention floor breached on cycle 2: Src={src9_2}, Dst={dst9_2}"
        test_results.append({"id": s9["id"], "title": s9["title"], "status": "PASS", "details": "Retained exactly 2000 mB floor in source tank while transferring 3000 mB excess."})

        # -------------------------------------------------------------
        # Station 10: Multi-Fluid Identifier Routing (Water vs Lava)
        # -------------------------------------------------------------
        print("\n=======================================================")
        print("Executing Station 10: Multi-Fluid Identifier Routing")
        s10 = stations[9]
        x10, z10 = s10["gx"], s10["gz"]
        harness.clear_area(x10 - 2, 4, z10 - 2, x10 + 8, 8, z10 + 4)
        for dx in range(-1, 7):
            for dz in range(-1, 2):
                harness.set_block(x10 + dx, 3, z10 + dz, "stone", 0)

        # Manager at (x10, 4, z10), Cable line from x10+1 to x10+4
        harness.set_block(x10, 4, z10, "superfactorymanager:manager", 0)
        for cx in range(1, 5):
            harness.set_block(x10 + cx, 4, z10, "superfactorymanager:cable", 0)

        # Source Water: GT Debug Tank (ID 238) at (x10, 5, z10)
        harness.set_gt_machine(x10, 5, z10, 238, 2)
        harness.configure_debug_tank(x10, 5, z10, "water", False)

        # Source Lava: GT Debug Tank (ID 238) at (x10+1, 5, z10)
        harness.set_gt_machine(x10 + 1, 5, z10, 238, 2)
        harness.configure_debug_tank(x10 + 1, 5, z10, "lava", False)

        # Dest Water: Supertank at (x10+2, 5, z10)
        harness.set_gt_machine(x10 + 2, 5, z10, 130, 2)
        harness.configure_supertank(x10 + 2, 5, z10, False)

        # Dest Lava: Supertank at (x10+3, 5, z10)
        harness.set_gt_machine(x10 + 3, 5, z10, 130, 2)
        harness.configure_supertank(x10 + 3, 5, z10, False)
        time.sleep(0.5)

        harness.sfm_label(x=x10, y=4, z=z10, label="src", bx=x10, by=5, bz=z10)
        harness.sfm_label(x=x10, y=4, z=z10, label="src", bx=x10+1, by=5, bz=z10)
        harness.sfm_label(x=x10, y=4, z=z10, label="dst_w", bx=x10+2, by=5, bz=z10)
        harness.sfm_label(x=x10, y=4, z=z10, label="dst_l", bx=x10+3, by=5, bz=z10)

        prog10 = (
            "EVERY 20 TICKS DO\n"
            "  INPUT 1000 fluid:lava FROM \"src\"\n"
            "  OUTPUT TO \"dst_l\"\n"
            "  FORGET\n"
            "  INPUT 2000 fluid:water FROM \"src\"\n"
            "  OUTPUT TO \"dst_w\"\n"
            "END\n"
        )
        harness.sfm_program(x=x10, y=4, z=z10, code=prog10)
        time.sleep(1.2)

        dst_l_fluid = get_tank_fluid(harness, x10 + 3, 5, z10)
        dst_w_fluid = get_tank_fluid(harness, x10 + 2, 5, z10)
        print(f"[Station 10] Dst Lava: {dst_l_fluid}, Dst Water: {dst_w_fluid}")
        assert dst_l_fluid.get("fluid") == "lava" and dst_l_fluid.get("amount") >= 1000 and dst_l_fluid.get("amount") % 1000 == 0, f"Expected lava multiple of 1000, got {dst_l_fluid}"
        assert dst_w_fluid.get("fluid") == "water" and dst_w_fluid.get("amount") >= 2000 and dst_w_fluid.get("amount") % 2000 == 0, f"Expected water multiple of 2000, got {dst_w_fluid}"
        test_results.append({"id": s10["id"], "title": s10["title"], "status": "PASS", "details": f"Filtered and routed {dst_l_fluid.get('amount')} mB lava and {dst_w_fluid.get('amount')} mB water into respective dedicated supertanks with zero crosstalk."})

        # -------------------------------------------------------------
        # Station 11: Fluid Voiding (Extra Utilities Fluid Trash Can)
        # -------------------------------------------------------------
        print("\n=======================================================")
        print("Executing Station 11: Fluid Voiding (Extra Utilities Fluid Trash Can)")
        s11 = stations[10]
        x11, z11 = s11["gx"], s11["gz"]
        harness.clear_area(x11 - 2, 4, z11 - 2, x11 + 6, 8, z11 + 4)
        for dx in range(-1, 5):
            for dz in range(-1, 2):
                harness.set_block(x11 + dx, 3, z11 + dz, "stone", 0)

        harness.set_block(x11, 4, z11, "superfactorymanager:manager", 0)
        harness.set_block(x11 + 1, 4, z11, "superfactorymanager:cable", 0)

        # Source Supertank at (x11, 5, z11)
        harness.set_gt_machine(x11, 5, z11, 130, 2)
        harness.configure_supertank(x11, 5, z11, False)
        # Fluid Trash Can: ExtraUtilities:trashcan meta 1 at (x11+1, 5, z11)
        harness.set_block(x11 + 1, 5, z11, "ExtraUtilities:trashcan", 1)
        time.sleep(0.5)

        # Pre-fill source with 6000 mB water
        harness.fill_fluid(x11, 5, z11, "water", 6000)
        time.sleep(0.3)

        src11_init = get_tank_fluid(harness, x11, 5, z11)
        assert src11_init.get("amount") == 6000, f"Failed pre-filling src: {src11_init}"

        harness.sfm_label(x=x11, y=4, z=z11, label="src", bx=x11, by=5, bz=z11)
        harness.sfm_label(x=x11, y=4, z=z11, label="trash", bx=x11+1, by=5, bz=z11)

        prog11 = "EVERY 20 TICKS DO\n  INPUT 4000 fluid:water FROM \"src\"\n  OUTPUT TO \"trash\"\nEND\n"
        harness.sfm_program(x=x11, y=4, z=z11, code=prog11)
        time.sleep(1.2)

        src11_after = get_tank_fluid(harness, x11, 5, z11)
        print(f"[Station 11 After voiding] Src water remaining: {src11_after}")
        assert src11_after.get("amount") in (0, 2000), f"Expected 0 or 2000 water remaining in source, got {src11_after}"
        test_results.append({"id": s11["id"], "title": s11["title"], "status": "PASS", "details": f"Voided fluid directly into Extra Utilities Fluid Trash Can (source dropped from 6000 mB to {src11_after.get('amount')} mB)."})

        # -------------------------------------------------------------
        # Station 12: Conditional Fluid Logic (IF src HAS > 3000)
        # -------------------------------------------------------------
        print("\n=======================================================")
        print("Executing Station 12: Conditional Fluid Logic (IF HAS > 3000)")
        s12 = stations[11]
        x12, z12 = s12["gx"], s12["gz"]
        harness.clear_area(x12 - 2, 4, z12 - 2, x12 + 6, 8, z12 + 4)
        for dx in range(-1, 5):
            for dz in range(-1, 2):
                harness.set_block(x12 + dx, 3, z12 + dz, "stone", 0)

        harness.set_block(x12, 4, z12, "superfactorymanager:manager", 0)
        harness.set_block(x12 + 1, 4, z12, "superfactorymanager:cable", 0)

        harness.set_gt_machine(x12, 5, z12, 130, 2)
        harness.set_gt_machine(x12 + 1, 5, z12, 130, 2)
        time.sleep(0.5)

        harness.configure_supertank(x12, 5, z12, False)
        harness.configure_supertank(x12 + 1, 5, z12, False)
        time.sleep(0.3)

        # Pre-fill source with 3500 mB water
        harness.fill_fluid(x12, 5, z12, "water", 3500)
        time.sleep(0.3)

        harness.sfm_label(x=x12, y=4, z=z12, label="src", bx=x12, by=5, bz=z12)
        harness.sfm_label(x=x12, y=4, z=z12, label="dst", bx=x12+1, by=5, bz=z12)

        prog12 = (
            "EVERY 20 TICKS DO\n"
            "  IF \"src\" HAS > 3000 fluid:water THEN\n"
            "    INPUT 1500 fluid:water FROM \"src\"\n"
            "    OUTPUT TO \"dst\"\n"
            "  END\n"
            "END\n"
        )
        harness.sfm_program(x=x12, y=4, z=z12, code=prog12)
        time.sleep(1.2)

        # Cycle 1: 3500 > 3000 is True -> transfers 1500 mB
        src12_1 = get_tank_fluid(harness, x12, 5, z12)
        dst12_1 = get_tank_fluid(harness, x12 + 1, 5, z12)
        print(f"[Station 12 Cycle 1] Src: {src12_1}, Dst: {dst12_1}")
        assert src12_1.get("amount") == 2000, f"Expected 2000 remaining, got {src12_1}"
        assert dst12_1.get("amount") == 1500, f"Expected 1500 moved, got {dst12_1}"

        # Cycle 2: 2000 > 3000 is False -> should not transfer
        time.sleep(1.2)
        src12_2 = get_tank_fluid(harness, x12, 5, z12)
        dst12_2 = get_tank_fluid(harness, x12 + 1, 5, z12)
        print(f"[Station 12 Cycle 2] Src: {src12_2}, Dst: {dst12_2}")
        assert src12_2.get("amount") == 2000 and dst12_2.get("amount") == 1500, f"Conditional check failed on cycle 2: Src={src12_2}, Dst={dst12_2}"
        test_results.append({"id": s12["id"], "title": s12["title"], "status": "PASS", "details": "Evaluated fluid threshold condition (HAS > 3000 fluid:water); passed on 3500 mB and halted when dropped to 2000 mB."})

        # -------------------------------------------------------------
        # Phase 4: Capture Visual Artifacts Across Grid
        # -------------------------------------------------------------
        print("\n=======================================================")
        print("PHASE 4: CAPTURING VISUAL ARTIFACTS ACROSS THE GRID")
        print("=======================================================")

        for st in stations:
            gx = st["gx"]
            gz = st["gz"]
            sid = st["id"]
            harness.close_gui()
            time.sleep(0.1)
            # Teleport close to station for 3D perspective
            harness.teleport(x=gx-2.5, y=7.5, z=gz-2.5)
            harness.look(yaw=-45, pitch=25)
            time.sleep(0.3)
            img_path = OUTPUT_DIR / f"{sid}.png"
            harness.screenshot(path=str(img_path))
            print(f"[+] Captured {img_path.name}")
            shutil.copy2(img_path, ARTIFACTS_DIR / img_path.name)

        # High-altitude aerial panoramic view of the whole 48-block grid
        print("[*] Capturing aerial panoramic view of entire 48-block grid...")
        harness.teleport(x=72.0, y=45.0, z=-20.0)
        harness.look(yaw=0, pitch=45)
        time.sleep(0.5)
        aerial_path = OUTPUT_DIR / "00_rf_aerial_grid_overview.png"
        harness.screenshot(path=str(aerial_path))
        print(f"[+] Captured {aerial_path.name}")
        shutil.copy2(aerial_path, ARTIFACTS_DIR / aerial_path.name)

        # Exit world cleanly with synchronization event
        print("\n[*] Exiting world gracefully via HarnessClient (sync event)...")
        exit_res = harness.exitworld()
        print(f"[+] World exit result: {exit_res}")

    print("\n=======================================================")
    print("ALL 12 REDSTONE & FLUID STATIONS PASSED PROGRAMMATIC ASSERTIONS!")
    print("=======================================================")

    return test_results


if __name__ == "__main__":
    results = run_test_suite()
    print(json.dumps(results, indent=2))
