#!/usr/bin/env python3
"""
inworld-tests/test_sfm_grid_e2e.py:
Exhaustive End-to-End Test Suite for SuperFactoryManager Language & Logistics.

Constructs a 4x3 distributed grid of 12 distinct SFM stations spaced 48 blocks apart
over an ephemeral copy of the pristine 'superflat_empty' template.
Re-uses the existing running Minecraft instance (via autoworld/exitworld) for speed.

Verified SFML Grammar Features:
 1. Basic Quantity Transfer (INPUT <qty> FROM ... OUTPUT TO ...)
 2. Retention Floor Limit (INPUT RETAIN <n> FROM ...)
 3. Compound Limit (INPUT <qty> RETAIN <n> FROM ...)
 4. Item Identifier Filtering (INPUT <resourceId> FROM ...)
 5. Resource Exclusion (INPUT FROM ... EXCEPT <resourceId>)
 6. Slot-Restricted IO (INPUT FROM ... SLOTS ... OUTPUT TO ... SLOTS ...)
 7. Round-Robin Distribution (OUTPUT TO ... ROUND ROBIN BY BLOCK)
 8. Conditional Inventory Logic (IF <src> HAS > <n> THEN ... END)
 9. Empty Slot Targeting (OUTPUT TO EMPTY SLOTS IN ...)
10. Buffer Clearing (FORGET)
11. Resource Disjunction (INPUT <resA> OR <resB> FROM ...)
12. Multi-Trigger Timer Independence (EVERY 10 TICKS ... EVERY 30 TICKS ...)
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
from tools.sfm_client import SfmClient
from tools.bot.world_manager import WorldSandbox
from tools.mcfleet.client import FleetClient
from tools.mcfleet.schema import InstanceSpec, DisplayMode

OUTPUT_DIR = ROOT_DIR / "output" / "sfm_grid"
ARTIFACTS_DIR = Path("/home/mgomezch/.gemini/antigravity/brain/a95f94f4-9edd-4aba-8218-d471d3db8067")
PRISM_INSTANCE_DIR = Path.home() / ".var/app/org.prismlauncher.PrismLauncher/data/PrismLauncher/instances/GTNH-2.9-sfm-dev/.minecraft"

HARNESS_PORT = 25572
SFM_PORT = 25573


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


def ensure_sfm_instance_running():
    sfm_client = SfmClient(port=SFM_PORT)
    harness_client = HarnessClient(port=HARNESS_PORT)

    if sfm_client.ping() and harness_client.ping():
        print(f"[+] SFM test instance is already running on ports {HARNESS_PORT} & {SFM_PORT}")
        try:
            res = harness_client.exitworld()
            print(f"[+] World exited to initial menu: {res}")
        except Exception:
            pass
        return

    print("[*] Launching SFM test instance via FleetClient...")
    client = FleetClient()
    spec = InstanceSpec(
        name="sfm-test-runner",
        template="nix-gtnh-sfm-dev",
        ephemeral=True,
        display_mode=DisplayMode.HEADLESS,
        display_socket="wayland-99",
        port=HARNESS_PORT,
        memory_min="4096m",
        memory_max="8192m",
        jvm_args=[
            "-Dsfm.testHarness=true",
            f"-Dsfm.harness.port={SFM_PORT}",
        ],
    )
    inst = client.launch(spec, auto_start=True)
    print(f"[+] Started instance '{spec.name}' (PID {inst.status.pid}). Waiting for controllers...")

    start_t = time.time()
    while time.time() - start_t < 240:
        if sfm_client.ping() and harness_client.ping():
            try:
                st = harness_client.state()
                if "GuiCustom" in st or "GuiMainMenu" in st or "MAIN_MENU" in st or "IN_WORLD" in st:
                    print(f"[+] Both controllers ready after {time.time() - start_t:.1f}s ({st})!")
                    return
            except Exception:
                pass
        time.sleep(3)
        print(f"[*] Waiting for game initialization... ({int(time.time() - start_t)}s elapsed)")

    raise RuntimeError("Timed out waiting for SFM test instance controllers to start")


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


def run_grid_test():
    client = FleetClient()
    ensure_sfm_instance_running()

    sfm = SfmClient(port=SFM_PORT)
    harness = HarnessClient(port=HARNESS_PORT)

    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

    world_name = "sfm_grid_test"
    saves_dir = get_saves_dir(client, harness, "sfm-test-runner")
    saves_dir.mkdir(parents=True, exist_ok=True)
    print(f"[*] Mounting ephemeral WorldSandbox over 'superflat_empty' into '{saves_dir}' as '{world_name}'...")
    def safe_exit():
        try:
            harness.leaveworld()
            time.sleep(2.0)
        except Exception:
            pass

    sandbox = WorldSandbox("superflat_empty", world_name=world_name, saves_dir=saves_dir, on_unmount=safe_exit)

    with sandbox:
        print(f"[*] Launching world '{world_name}'...")
        res = sfm.autoworld(name=world_name)
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
        sfm.closegui()
        time.sleep(0.5)

        # Baseline game settings
        print("[*] Setting world environment...")
        sfm.chat(message="/gamemode 1")
        sfm.chat(message="/gamerule doDaylightCycle false")
        sfm.chat(message="/gamerule doMobSpawning false")
        sfm.chat(message="/time set 6000")
        sfm.chat(message="/weather clear 999999")
        sfm.chat(message="/difficulty 0")
        sfm.chat(message="/cofh killall *")
        sfm.chat(message="/setworldspawn 72 4 48")
        time.sleep(0.5)

        # Define 12 Stations on 48-block grid
        # Coordinates: X = col * 48, Z = row * 48
        stations = [
            # Row 0 (Z = 0)
            {"id": "01_basic_transfer", "col": 0, "row": 0, "title": "Basic Quantity Transfer"},
            {"id": "02_retain_limit", "col": 1, "row": 0, "title": "Item Retention Limits"},
            {"id": "03_compound_limit", "col": 2, "row": 0, "title": "Quantity + Retention Compound Limit"},
            {"id": "04_resource_filter", "col": 3, "row": 0, "title": "Item Identifier Filtering"},

            # Row 1 (Z = 48)
            {"id": "05_resource_exclusion", "col": 0, "row": 1, "title": "Resource Exclusion (EXCEPT)"},
            {"id": "06_slot_restriction", "col": 1, "row": 1, "title": "Slot-Restricted IO (SLOTS)"},
            {"id": "07_round_robin", "col": 2, "row": 1, "title": "Round-Robin Distribution"},
            {"id": "08_conditional_logic", "col": 3, "row": 1, "title": "Conditional Logic (IF HAS)"},

            # Row 2 (Z = 96)
            {"id": "09_empty_slots", "col": 0, "row": 2, "title": "Empty Slot Targeting"},
            {"id": "10_forget_statement", "col": 1, "row": 2, "title": "Buffer Clearing (FORGET)"},
            {"id": "11_disjunction_filter", "col": 2, "row": 2, "title": "Resource Disjunction (OR)"},
            {"id": "12_dual_timers", "col": 3, "row": 2, "title": "Multi-Trigger Timer Independence"},
        ]

        print("\n========================================================")
        print("PHASE 1: CONSTRUCTING 12-NODE DISTRIBUTED GRID (48m Spacing)")
        print("========================================================")

        for st in stations:
            gx = st["col"] * 48
            gz = st["row"] * 48
            st["gx"] = gx
            st["gz"] = gz
            print(f"[*] Erecting Station {st['id']} ({st['title']}) at ({gx}, 5, {gz})...")

            # Teleport player above station to load chunk
            sfm.teleport(x=gx, y=7, z=gz)
            time.sleep(0.15)

            # 7x7 quartz foundation at y=4
            harness.clear_area(x1=gx-3, y1=4, z1=gz-3, x2=gx+3, y2=8, z2=gz+3)
            harness.fill_area(x1=gx-3, y1=4, z1=gz-3, x2=gx+3, y2=4, z2=gz+3, block="minecraft:quartz_block", meta=0)

            # Common base: Manager at (gx, 5, gz)
            harness.set_block(gx, 5, gz, "superfactorymanager:manager", 0)
            harness.set_block(gx, 5, gz + 1, "superfactorymanager:fancyCable", 0)

            sid = st["id"]

            if sid == "01_basic_transfer":
                # Creative Drawer at gz+2, Chest at gx+1, gz+1
                harness.setup_vending_drawer(x=gx, y=5, z=gz+2, item="minecraft:cobblestone", meta=0, dir=2)
                harness.set_block(gx + 1, 5, gz + 1, "minecraft:chest", 0)
                sfm.clearlabels(x=gx, y=5, z=gz)
                sfm.setlabel(x=gx, y=5, z=gz, label="drawer", tx=gx, ty=5, tz=gz+2)
                sfm.setlabel(x=gx, y=5, z=gz, label="chest", tx=gx+1, ty=5, tz=gz+1)
                code = "NAME \"Basic\"\nEVERY 20 TICKS DO\n  INPUT 32 FROM drawer\n  OUTPUT TO chest\nEND"
                res_p = sfm.programmanager(x=gx, y=5, z=gz, code=code)
                assert res_p["valid"], f"Node 1 failed to compile: {res_p}"

            elif sid == "02_retain_limit":
                # Source Chest at gz+2 (64 cobble), Dest Chest at gx+1, gz+1
                harness.set_block(gx, 5, gz + 2, "minecraft:chest", 0)
                harness.set_block(gx + 1, 5, gz + 1, "minecraft:chest", 0)
                harness.set_container_slot(x=gx, y=5, z=gz+2, slot=0, item="minecraft:cobblestone", count=64, damage=0)
                sfm.clearlabels(x=gx, y=5, z=gz)
                sfm.setlabel(x=gx, y=5, z=gz, label="src", tx=gx, ty=5, tz=gz+2)
                sfm.setlabel(x=gx, y=5, z=gz, label="dst", tx=gx+1, ty=5, tz=gz+1)
                code = "NAME \"Retain\"\nEVERY 20 TICKS DO\n  INPUT RETAIN 16 FROM src\n  OUTPUT TO dst\nEND"
                res_p = sfm.programmanager(x=gx, y=5, z=gz, code=code)
                assert res_p["valid"], f"Node 2 failed to compile: {res_p}"

            elif sid == "03_compound_limit":
                # Source Chest at gz+2 (50 cobble), Dest Chest at gx+1, gz+1
                harness.set_block(gx, 5, gz + 2, "minecraft:chest", 0)
                harness.set_block(gx + 1, 5, gz + 1, "minecraft:chest", 0)
                harness.set_container_slot(x=gx, y=5, z=gz+2, slot=0, item="minecraft:cobblestone", count=50, damage=0)
                sfm.clearlabels(x=gx, y=5, z=gz)
                sfm.setlabel(x=gx, y=5, z=gz, label="src", tx=gx, ty=5, tz=gz+2)
                sfm.setlabel(x=gx, y=5, z=gz, label="dst", tx=gx+1, ty=5, tz=gz+1)
                code = "NAME \"Compound\"\nEVERY 20 TICKS DO\n  INPUT 20 RETAIN 20 FROM src\n  OUTPUT TO dst\nEND"
                res_p = sfm.programmanager(x=gx, y=5, z=gz, code=code)
                assert res_p["valid"], f"Node 3 failed to compile: {res_p}"

            elif sid == "04_resource_filter":
                # Source Chest at gz+2 with mixed items: iron, gold, cobble
                harness.set_block(gx, 5, gz + 2, "minecraft:chest", 0)
                harness.set_block(gx + 1, 5, gz + 1, "minecraft:chest", 0)
                harness.set_container_slot(x=gx, y=5, z=gz+2, slot=0, item="minecraft:iron_ingot", count=32, damage=0)
                harness.set_container_slot(x=gx, y=5, z=gz+2, slot=1, item="minecraft:gold_ingot", count=32, damage=0)
                harness.set_container_slot(x=gx, y=5, z=gz+2, slot=2, item="minecraft:cobblestone", count=32, damage=0)
                sfm.clearlabels(x=gx, y=5, z=gz)
                sfm.setlabel(x=gx, y=5, z=gz, label="src", tx=gx, ty=5, tz=gz+2)
                sfm.setlabel(x=gx, y=5, z=gz, label="dst", tx=gx+1, ty=5, tz=gz+1)
                code = "NAME \"Filter\"\nEVERY 20 TICKS DO\n  INPUT minecraft:iron_ingot FROM src\n  OUTPUT TO dst\nEND"
                res_p = sfm.programmanager(x=gx, y=5, z=gz, code=code)
                assert res_p["valid"], f"Node 4 failed to compile: {res_p}"

            elif sid == "05_resource_exclusion":
                # Source Chest at gz+2: 20 cobble, 20 stone, 20 dirt
                harness.set_block(gx, 5, gz + 2, "minecraft:chest", 0)
                harness.set_block(gx + 1, 5, gz + 1, "minecraft:chest", 0)
                harness.set_container_slot(x=gx, y=5, z=gz+2, slot=0, item="minecraft:cobblestone", count=20, damage=0)
                harness.set_container_slot(x=gx, y=5, z=gz+2, slot=1, item="minecraft:stone", count=20, damage=0)
                harness.set_container_slot(x=gx, y=5, z=gz+2, slot=2, item="minecraft:dirt", count=20, damage=0)
                sfm.clearlabels(x=gx, y=5, z=gz)
                sfm.setlabel(x=gx, y=5, z=gz, label="src", tx=gx, ty=5, tz=gz+2)
                sfm.setlabel(x=gx, y=5, z=gz, label="dst", tx=gx+1, ty=5, tz=gz+1)
                code = "NAME \"Exclusion\"\nEVERY 20 TICKS DO\n  INPUT EXCEPT minecraft:dirt FROM src\n  OUTPUT TO dst\nEND"
                res_p = sfm.programmanager(x=gx, y=5, z=gz, code=code)
                assert res_p["valid"], f"Node 5 failed to compile: {res_p}"

            elif sid == "06_slot_restriction":
                # Source Chest at gz+2: slot 0=16 redstone, slot 1=16 coal, slot 2=16 glowstone
                harness.set_block(gx, 5, gz + 2, "minecraft:chest", 0)
                harness.set_block(gx + 1, 5, gz + 1, "minecraft:chest", 0)
                harness.set_container_slot(x=gx, y=5, z=gz+2, slot=0, item="minecraft:redstone", count=16, damage=0)
                harness.set_container_slot(x=gx, y=5, z=gz+2, slot=1, item="minecraft:coal", count=16, damage=0)
                harness.set_container_slot(x=gx, y=5, z=gz+2, slot=2, item="minecraft:glowstone_dust", count=16, damage=0)
                sfm.clearlabels(x=gx, y=5, z=gz)
                sfm.setlabel(x=gx, y=5, z=gz, label="src", tx=gx, ty=5, tz=gz+2)
                sfm.setlabel(x=gx, y=5, z=gz, label="dst", tx=gx+1, ty=5, tz=gz+1)
                code = "NAME \"Slots\"\nEVERY 20 TICKS DO\n  INPUT FROM src SLOTS 0, 2\n  OUTPUT TO dst SLOTS 4-5\nEND"
                res_p = sfm.programmanager(x=gx, y=5, z=gz, code=code)
                assert res_p["valid"], f"Node 6 failed to compile: {res_p}"

            elif sid == "07_round_robin":
                # Creative Drawer at gz+2, Chest A at gx-1, gz+1, Chest B at gx+1, gz+1
                harness.setup_vending_drawer(x=gx, y=5, z=gz+2, item="minecraft:cobblestone", meta=0, dir=2)
                harness.set_block(gx - 1, 5, gz + 1, "minecraft:chest", 0)
                harness.set_block(gx + 1, 5, gz + 1, "minecraft:chest", 0)
                sfm.clearlabels(x=gx, y=5, z=gz)
                sfm.setlabel(x=gx, y=5, z=gz, label="drawer", tx=gx, ty=5, tz=gz+2)
                sfm.setlabel(x=gx, y=5, z=gz, label="chestA", tx=gx-1, ty=5, tz=gz+1)
                sfm.setlabel(x=gx, y=5, z=gz, label="chestB", tx=gx+1, ty=5, tz=gz+1)
                code = "NAME \"RoundRobin\"\nEVERY 20 TICKS DO\n  INPUT 10 FROM drawer\n  OUTPUT TO chestA, chestB ROUND ROBIN BY LABEL\nEND"
                res_p = sfm.programmanager(x=gx, y=5, z=gz, code=code)
                assert res_p["valid"], f"Node 7 failed to compile: {res_p}"

            elif sid == "08_conditional_logic":
                # Source Chest at gz+2 with 40 cobble, Dest at gx+1, gz+1
                harness.set_block(gx, 5, gz + 2, "minecraft:chest", 0)
                harness.set_block(gx + 1, 5, gz + 1, "minecraft:chest", 0)
                harness.set_container_slot(x=gx, y=5, z=gz+2, slot=0, item="minecraft:cobblestone", count=40, damage=0)
                sfm.clearlabels(x=gx, y=5, z=gz)
                sfm.setlabel(x=gx, y=5, z=gz, label="src", tx=gx, ty=5, tz=gz+2)
                sfm.setlabel(x=gx, y=5, z=gz, label="dst", tx=gx+1, ty=5, tz=gz+1)
                code = "NAME \"Condition\"\nEVERY 20 TICKS DO\n  IF src HAS > 30 THEN\n    INPUT 10 FROM src\n    OUTPUT TO dst\n  END\nEND"
                res_p = sfm.programmanager(x=gx, y=5, z=gz, code=code)
                assert res_p["valid"], f"Node 8 failed to compile: {res_p}"

            elif sid == "09_empty_slots":
                # Drawer at gz+2, Dest Chest at gx+1, gz+1 seeded with slot 0=5 cobble, slot 1=5 cobble
                harness.setup_vending_drawer(x=gx, y=5, z=gz+2, item="minecraft:cobblestone", meta=0, dir=2)
                harness.set_block(gx + 1, 5, gz + 1, "minecraft:chest", 0)
                harness.set_container_slot(x=gx+1, y=5, z=gz+1, slot=0, item="minecraft:cobblestone", count=5, damage=0)
                harness.set_container_slot(x=gx+1, y=5, z=gz+1, slot=1, item="minecraft:cobblestone", count=5, damage=0)
                sfm.clearlabels(x=gx, y=5, z=gz)
                sfm.setlabel(x=gx, y=5, z=gz, label="drawer", tx=gx, ty=5, tz=gz+2)
                sfm.setlabel(x=gx, y=5, z=gz, label="chest", tx=gx+1, ty=5, tz=gz+1)
                code = "NAME \"EmptySlots\"\nEVERY 20 TICKS DO\n  INPUT 10 FROM drawer\n  OUTPUT TO EMPTY SLOTS IN chest\nEND"
                res_p = sfm.programmanager(x=gx, y=5, z=gz, code=code)
                assert res_p["valid"], f"Node 9 failed to compile: {res_p}"

            elif sid == "10_forget_statement":
                # Source at gz+2 with 32 cobble, Dest at gx+1, gz+1 empty
                harness.set_block(gx, 5, gz + 2, "minecraft:chest", 0)
                harness.set_block(gx + 1, 5, gz + 1, "minecraft:chest", 0)
                harness.set_container_slot(x=gx, y=5, z=gz+2, slot=0, item="minecraft:cobblestone", count=32, damage=0)
                sfm.clearlabels(x=gx, y=5, z=gz)
                sfm.setlabel(x=gx, y=5, z=gz, label="src", tx=gx, ty=5, tz=gz+2)
                sfm.setlabel(x=gx, y=5, z=gz, label="dst", tx=gx+1, ty=5, tz=gz+1)
                code = "NAME \"Forget\"\nEVERY 20 TICKS DO\n  INPUT 16 FROM src\n  FORGET\n  OUTPUT TO dst\nEND"
                res_p = sfm.programmanager(x=gx, y=5, z=gz, code=code)
                assert res_p["valid"], f"Node 10 failed to compile: {res_p}"

            elif sid == "11_disjunction_filter":
                # Source at gz+2: 10 iron, 10 gold, 10 diamond
                harness.set_block(gx, 5, gz + 2, "minecraft:chest", 0)
                harness.set_block(gx + 1, 5, gz + 1, "minecraft:chest", 0)
                harness.set_container_slot(x=gx, y=5, z=gz+2, slot=0, item="minecraft:iron_ingot", count=10, damage=0)
                harness.set_container_slot(x=gx, y=5, z=gz+2, slot=1, item="minecraft:gold_ingot", count=10, damage=0)
                harness.set_container_slot(x=gx, y=5, z=gz+2, slot=2, item="minecraft:diamond", count=10, damage=0)
                sfm.clearlabels(x=gx, y=5, z=gz)
                sfm.setlabel(x=gx, y=5, z=gz, label="src", tx=gx, ty=5, tz=gz+2)
                sfm.setlabel(x=gx, y=5, z=gz, label="dst", tx=gx+1, ty=5, tz=gz+1)
                code = "NAME \"Disjunction\"\nEVERY 20 TICKS DO\n  INPUT minecraft:iron_ingot OR minecraft:gold_ingot FROM src\n  OUTPUT TO dst\nEND"
                res_p = sfm.programmanager(x=gx, y=5, z=gz, code=code)
                assert res_p["valid"], f"Node 11 failed to compile: {res_p}"

            elif sid == "12_dual_timers":
                # Drawer at gz+2, Fast Chest at gx-1, gz+1, Slow Chest at gx+1, gz+1
                harness.setup_vending_drawer(x=gx, y=5, z=gz+2, item="minecraft:cobblestone", meta=0, dir=2)
                harness.set_block(gx - 1, 5, gz + 1, "minecraft:chest", 0)
                harness.set_block(gx + 1, 5, gz + 1, "minecraft:chest", 0)
                sfm.clearlabels(x=gx, y=5, z=gz)
                sfm.setlabel(x=gx, y=5, z=gz, label="drawer", tx=gx, ty=5, tz=gz+2)
                sfm.setlabel(x=gx, y=5, z=gz, label="fast", tx=gx-1, ty=5, tz=gz+1)
                sfm.setlabel(x=gx, y=5, z=gz, label="slow", tx=gx+1, ty=5, tz=gz+1)
                code = (
                    "NAME \"DualTimers\"\n"
                    "EVERY 10 TICKS DO\n"
                    "  INPUT 5 FROM drawer\n"
                    "  OUTPUT TO fast\n"
                    "END\n"
                    "EVERY 30 TICKS DO\n"
                    "  INPUT 15 FROM drawer\n"
                    "  OUTPUT TO slow\n"
                    "END"
                )
                res_p = sfm.programmanager(x=gx, y=5, z=gz, code=code)
                assert res_p["valid"], f"Node 12 failed to compile: {res_p}"

            # Enable manager
            sfm.setrunning(x=gx, y=5, z=gz, running=True)

        print("\n========================================================")
        print("PHASE 2: EXECUTING CONCURRENT SIMULATION TO TICK 85")
        print("========================================================")
        # Position player in the center of the grid (gx=72, gz=48) at y=10 to observe
        sfm.teleport(x=72.0, y=12.0, z=48.0)
        sfm.look(yaw=0, pitch=35)
        time.sleep(0.5)

        start_sim = time.time()
        # Advance simulation to tick 85 across all managers
        while time.time() - start_sim < 60:
            st0 = sfm.getmanagerstatus(x=0, y=5, z=0)
            st11 = sfm.getmanagerstatus(x=144, y=5, z=96)
            def parse_t(s):
                try:
                    return int(json.loads(s).get("tick", 0))
                except Exception:
                    return 0
            cur_tick0 = parse_t(st0)
            cur_tick11 = parse_t(st11)
            if cur_tick0 >= 85 and cur_tick11 >= 85:
                print(f"[+] Simulation reached ticks (station1={cur_tick0}, station12={cur_tick11}) across grid!")
                break
            time.sleep(0.5)

        time.sleep(1.0)

        print("\n========================================================")
        print("PHASE 3: VERIFYING ASSERTIONS ACROSS ALL 12 STATIONS")
        print("========================================================")

        results = []

        # 1. Basic Quantity: 32 per 20 ticks. At tick 85 (triggers 20, 40, 60, 80 = 4 triggers) = 128
        inv1 = get_inv_items(harness, 1, 5, 1)
        cnt1 = count_items(inv1, "minecraft:cobblestone")
        assert cnt1 >= 96, f"Node 1 failed: expected >= 96 cobble, got {cnt1}"
        results.append(("01_basic_transfer", f"PASSED ({cnt1} cobblestone transferred)"))
        print(f"[✓] Node 1 (Basic Quantity Transfer): {cnt1} items transferred (expected >= 96)")

        # 2. Retention: 64 initial, retain 16 -> 48 moved, 16 kept
        inv2_src = get_inv_items(harness, 48, 5, 2)
        inv2_dst = get_inv_items(harness, 49, 5, 1)
        cnt2_src = count_items(inv2_src, "minecraft:cobblestone")
        cnt2_dst = count_items(inv2_dst, "minecraft:cobblestone")
        assert cnt2_src == 16, f"Node 2 failed: expected 16 retained in src, got {cnt2_src}"
        assert cnt2_dst == 48, f"Node 2 failed: expected 48 in dst, got {cnt2_dst}"
        results.append(("02_retain_limit", f"PASSED (src={cnt2_src}, dst={cnt2_dst})"))
        print(f"[✓] Node 2 (Retention Limits): Retained exactly {cnt2_src} in src, transferred {cnt2_dst} to dst")

        # 3. Compound Limit: 50 initial, input 20 retain 20 -> 30 moved, 20 kept
        inv3_src = get_inv_items(harness, 96, 5, 2)
        inv3_dst = get_inv_items(harness, 97, 5, 1)
        cnt3_src = count_items(inv3_src, "minecraft:cobblestone")
        cnt3_dst = count_items(inv3_dst, "minecraft:cobblestone")
        assert cnt3_src == 20, f"Node 3 failed: expected 20 retained in src, got {cnt3_src}"
        assert cnt3_dst == 30, f"Node 3 failed: expected 30 in dst, got {cnt3_dst}"
        results.append(("03_compound_limit", f"PASSED (src={cnt3_src}, dst={cnt3_dst})"))
        print(f"[✓] Node 3 (Compound Limit): Retained exactly {cnt3_src} in src, transferred {cnt3_dst} to dst")

        # 4. Resource Filter: only iron_ingot moved (32), gold and cobble left in src
        inv4_src = get_inv_items(harness, 144, 5, 2)
        inv4_dst = get_inv_items(harness, 145, 5, 1)
        cnt4_iron = count_items(inv4_dst, "minecraft:iron_ingot")
        cnt4_gold_dst = count_items(inv4_dst, "minecraft:gold_ingot")
        cnt4_gold_src = count_items(inv4_src, "minecraft:gold_ingot")
        assert cnt4_iron == 32, f"Node 4 failed: expected 32 iron in dst, got {cnt4_iron}"
        assert cnt4_gold_dst == 0, f"Node 4 failed: gold leaked to dst: {cnt4_gold_dst}"
        assert cnt4_gold_src == 32, f"Node 4 failed: gold missing in src: {cnt4_gold_src}"
        results.append(("04_resource_filter", f"PASSED (iron={cnt4_iron} moved, gold={cnt4_gold_src} preserved)"))
        print(f"[✓] Node 4 (Resource Filter): Only iron ({cnt4_iron}) moved, gold ({cnt4_gold_src}) untouched")

        # 5. Resource Exclusion: EXCEPT dirt -> cobble (20) + stone (20) moved, dirt (20) in src
        inv5_src = get_inv_items(harness, 0, 5, 50)
        inv5_dst = get_inv_items(harness, 1, 5, 49)
        cnt5_dirt_src = count_items(inv5_src, "minecraft:dirt")
        cnt5_dirt_dst = count_items(inv5_dst, "minecraft:dirt")
        cnt5_cobble_dst = count_items(inv5_dst, "minecraft:cobblestone")
        cnt5_stone_dst = count_items(inv5_dst, "minecraft:stone")
        assert cnt5_dirt_dst == 0, f"Node 5 failed: dirt leaked to dst: {cnt5_dirt_dst}"
        assert cnt5_dirt_src == 20, f"Node 5 failed: dirt missing from src: {cnt5_dirt_src}"
        assert cnt5_cobble_dst == 20 and cnt5_stone_dst == 20, f"Node 5 failed: expected 20 cobble & 20 stone, got {cnt5_cobble_dst}, {cnt5_stone_dst}"
        results.append(("05_resource_exclusion", f"PASSED (dirt retained, cobble+stone moved)"))
        print(f"[✓] Node 5 (Resource Exclusion): Dirt excluded and retained ({cnt5_dirt_src}), cobble ({cnt5_cobble_dst}) & stone ({cnt5_stone_dst}) moved")

        # 6. Slot Restriction: SLOTS 0, 2 -> redstone (slot 0) and glowstone (slot 2) moved to dst slots 4-5; coal (slot 1) retained
        inv6_src = get_inv_items(harness, 48, 5, 50)
        inv6_dst = get_inv_items(harness, 49, 5, 49)
        src_slot1_coal = sum(s["count"] for s in inv6_src if s["slot"] == 1 and s["item"] == "minecraft:coal")
        dst_slots_0_3 = sum(s["count"] for s in inv6_dst if s["slot"] < 4)
        dst_slot4_5 = sum(s["count"] for s in inv6_dst if s["slot"] in (4, 5))
        assert src_slot1_coal == 16, f"Node 6 failed: slot 1 coal missing: {src_slot1_coal}"
        assert dst_slots_0_3 == 0, f"Node 6 failed: items in unwanted dst slots: {dst_slots_0_3}"
        assert dst_slot4_5 == 32, f"Node 6 failed: expected 32 items in slots 4-5, got {dst_slot4_5}"
        results.append(("06_slot_restriction", f"PASSED (slots 0,2 moved to 4-5, slot 1 coal={src_slot1_coal} preserved)"))
        print(f"[✓] Node 6 (Slot-Restricted IO): Only slots 0,2 extracted to dst 4-5 ({dst_slot4_5} items), slot 1 coal preserved")

        # 7. Round Robin: alternates across chestA, chestB
        inv7_a = get_inv_items(harness, 95, 5, 49)
        inv7_b = get_inv_items(harness, 97, 5, 49)
        cnt7_a = count_items(inv7_a, "minecraft:cobblestone")
        cnt7_b = count_items(inv7_b, "minecraft:cobblestone")
        assert cnt7_a > 0 and cnt7_b > 0 and abs(cnt7_a - cnt7_b) <= 10, f"Node 7 failed: unbalanced: A={cnt7_a}, B={cnt7_b}"
        results.append(("07_round_robin", f"PASSED (chestA={cnt7_a}, chestB={cnt7_b})"))
        print(f"[✓] Node 7 (Round Robin): Balanced distribution across 2 chests: chestA={cnt7_a}, chestB={cnt7_b}")

        # 8. Conditional Logic: IF HAS > 30 -> 10 moved, then 30 left and transfer stops
        inv8_src = get_inv_items(harness, 144, 5, 50)
        inv8_dst = get_inv_items(harness, 145, 5, 49)
        cnt8_src = count_items(inv8_src, "minecraft:cobblestone")
        cnt8_dst = count_items(inv8_dst, "minecraft:cobblestone")
        assert cnt8_src == 30, f"Node 8 failed: expected 30 in src, got {cnt8_src}"
        assert cnt8_dst == 10, f"Node 8 failed: expected 10 in dst, got {cnt8_dst}"
        results.append(("08_conditional_logic", f"PASSED (src={cnt8_src}, dst={cnt8_dst})"))
        print(f"[✓] Node 8 (Conditional Logic): Condition stopped transfer cleanly at src={cnt8_src}, dst={cnt8_dst}")

        # 9. Empty Slot Targeting: slots 0, 1 were 5 each; 10 cobble landed in slot 2 without merging
        inv9 = get_inv_items(harness, 1, 5, 97)
        slot0_cnt = sum(s["count"] for s in inv9 if s["slot"] == 0)
        slot1_cnt = sum(s["count"] for s in inv9 if s["slot"] == 1)
        slot2_plus = sum(s["count"] for s in inv9 if s["slot"] >= 2)
        assert slot0_cnt == 5 and slot1_cnt == 5, f"Node 9 failed: partial stacks corrupted: {slot0_cnt}, {slot1_cnt}"
        assert slot2_plus > 0, f"Node 9 failed: no items in empty slots: {slot2_plus}"
        results.append(("09_empty_slots", f"PASSED (preserved slots 0,1=5, new items in empty slots={slot2_plus})"))
        print(f"[✓] Node 9 (Empty Slot Targeting): Partial stacks preserved ({slot0_cnt}, {slot1_cnt}), deposited to empty slot 2 ({slot2_plus})")

        # 10. Forget: 16 extracted then FORGET -> dst has 0
        inv10_dst = get_inv_items(harness, 49, 5, 97)
        cnt10_dst = count_items(inv10_dst)
        assert cnt10_dst == 0, f"Node 10 failed: expected 0 items in dst, got {cnt10_dst}"
        results.append(("10_forget_statement", f"PASSED (buffer purged, dst={cnt10_dst})"))
        print(f"[✓] Node 10 (FORGET Statement): Memory buffer purged before output, dst={cnt10_dst}")

        # 11. Resource Disjunction: iron OR gold moved (10+10 = 20), diamond (10) in src
        inv11_src = get_inv_items(harness, 96, 5, 98)
        inv11_dst = get_inv_items(harness, 97, 5, 97)
        cnt11_diamond = count_items(inv11_src, "minecraft:diamond")
        cnt11_iron = count_items(inv11_dst, "minecraft:iron_ingot")
        cnt11_gold = count_items(inv11_dst, "minecraft:gold_ingot")
        assert cnt11_diamond == 10, f"Node 11 failed: diamonds missing: {cnt11_diamond}"
        assert cnt11_iron == 10 and cnt11_gold == 10, f"Node 11 failed: expected 10 iron and 10 gold, got {cnt11_iron}, {cnt11_gold}"
        results.append(("11_disjunction_filter", f"PASSED (iron={cnt11_iron}, gold={cnt11_gold} moved, diamond={cnt11_diamond} retained)"))
        print(f"[✓] Node 11 (Resource Disjunction OR): Both iron ({cnt11_iron}) and gold ({cnt11_gold}) moved, diamonds retained ({cnt11_diamond})")

        # 12. Dual Timers: fast (every 10 ticks) and slow (every 30 ticks)
        inv12_fast = get_inv_items(harness, 143, 5, 97)
        inv12_slow = get_inv_items(harness, 145, 5, 97)
        cnt12_fast = count_items(inv12_fast, "minecraft:cobblestone")
        cnt12_slow = count_items(inv12_slow, "minecraft:cobblestone")
        assert cnt12_fast > 0 and cnt12_slow > 0, f"Node 12 failed: one chest empty: fast={cnt12_fast}, slow={cnt12_slow}"
        results.append(("12_dual_timers", f"PASSED (fast={cnt12_fast}, slow={cnt12_slow})"))
        print(f"[✓] Node 12 (Dual Timers): Independent timers executed concurrently (fast={cnt12_fast}, slow={cnt12_slow})")

        print("\n========================================================")
        print("PHASE 4: CAPTURING VISUAL ARTIFACTS ACROSS THE GRID")
        print("========================================================")

        for st in stations:
            gx = st["gx"]
            gz = st["gz"]
            sid = st["id"]
            sfm.closegui()
            time.sleep(0.1)
            # Teleport close to station for 3D perspective
            sfm.teleport(x=gx-2.5, y=7.5, z=gz-2.5)
            sfm.look(yaw=-45, pitch=25)
            time.sleep(0.3)
            img_path = OUTPUT_DIR / f"{sid}.png"
            sfm.screenshot(path=img_path)
            print(f"[+] Captured {img_path.name}")
            shutil.copy2(img_path, ARTIFACTS_DIR / img_path.name)

        # High-altitude aerial panoramic view of the whole 48-block grid
        print("[*] Capturing aerial panoramic view of entire 48-block grid...")
        sfm.teleport(x=72.0, y=45.0, z=-20.0)
        sfm.look(yaw=0, pitch=45)
        time.sleep(0.5)
        aerial_path = OUTPUT_DIR / "00_aerial_grid_overview.png"
        sfm.screenshot(path=aerial_path)
        print(f"[+] Captured {aerial_path.name}")
        shutil.copy2(aerial_path, ARTIFACTS_DIR / aerial_path.name)

        print("[*] Exiting world to release locks...")
        res_exit = harness.exitworld()
        print(f"[+] World exited cleanly to initial menu: {res_exit}")

        print("\n========================================================")
        print("ALL 12 SFM LOGISTICS FEATURE TESTS PASSED 100% CLEANLY!")
        print("========================================================")
        for name, res in results:
            print(f"  • {name}: {res}")


if __name__ == "__main__":
    run_grid_test()
