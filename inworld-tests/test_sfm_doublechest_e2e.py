#!/usr/bin/env python3
"""
test_sfm_doublechest_e2e.py: End-to-end integration test suite verifying native
vanilla double chest support in Super Factory Manager (SFM).

Tests 6 key behaviors:
1. Extract from unconnected partner half (labeling left half extracts items stored in right half).
2. Reverse extraction from partner half (labeling right half extracts items stored in left half).
3. Deduplication when both halves are connected & labeled (single 54-slot gathering, no double transfer).
4. Seamless insertion overflow across halves (slots 0..26 full -> automatically fills slots 27..53).
5. Unified conditional evaluation (BoolHas accurately checks split item totals without double counting).
6. Sided / range slot filtering (SLOTS 0..26 vs SLOTS 27..53 addressing halves canonically).
"""

from __future__ import annotations

import os
import sys
import json
import time
import shutil
from pathlib import Path
from typing import Dict, Any, List, Optional

from nhdev import NH_DEV_ROOT as REPO_ROOT

from tools.mcfleet.client import FleetClient
from tools.mcfleet.schema import InstanceSpec, DisplayMode
from tools.bot.world_manager import WorldSandbox
from tools.agentharness import HarnessClient
from tools.runner_instance import ensure_sfm_instance_running, get_saves_dir

OUTPUT_DIR = Path("/tmp/sfm_doublechest_output")
ARTIFACTS_DIR = Path("/home/mgomezch/.gemini/antigravity/brain/a95f94f4-9edd-4aba-8218-d471d3db8067")
HARNESS_PORT = 25572


def get_inv_items(harness: HarnessClient, x: int, y: int, z: int) -> List[Dict[str, Any]]:
    for _ in range(5):
        raw = harness.container_info(x=x, y=y, z=z)
        try:
            data = raw
            return data.get("slots", [])
        except Exception:
            time.sleep(0.3)
    return []


def count_items(slots: List[Dict[str, Any]], item_id: Optional[str] = None) -> int:
    total = 0
    for s in slots:
        if item_id is None or s.get("item") == item_id:
            total += s.get("count", 0)
    return total


def set_slot(harness: HarnessClient, x: int, y: int, z: int, slot: int, item: str, count: int = 1, damage: int = 0) -> str:
    res = ""
    for attempt in range(10):
        try:
            return harness.set_container_slot(x, y, z, slot, item, count, damage)
        except Exception as e:
            res = str(e)
        time.sleep(0.5)
    print(f"[set_slot FAILED/RETURNED] ({x},{y},{z}) slot {slot} -> {res}")
    return res


def run_doublechest_suite():
    client = FleetClient()
    ensure_sfm_instance_running()

    harness = HarnessClient(port=HARNESS_PORT)

    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

    world_name = "sfm_dchest_test"
    saves_dir = get_saves_dir(client)
    saves_dir.mkdir(parents=True, exist_ok=True)
    print(f"[*] Mounting ephemeral WorldSandbox into '{saves_dir}' as '{world_name}'...")

    def safe_exit():
        try:
            harness.exitworld()
        except Exception:
            pass

    sandbox = WorldSandbox("superflat_empty", world_name=world_name, saves_dir=saves_dir, on_unmount=safe_exit)

    stations = [
        {"id": "01_extract_from_unconnected_half", "title": "Extract from Unconnected Half (Slot 30 in Half 2)", "gx": 0, "gz": 0},
        {"id": "02_extract_reverse_unconnected_half", "title": "Reverse Extraction (Slot 5 in Half 1 via Half 2)", "gx": 24, "gz": 0},
        {"id": "03_deduplication_both_halves_labeled", "title": "Slot Gathering Deduplication (Both Halves Labeled)", "gx": 48, "gz": 0},
        {"id": "04_overflow_insertion_across_halves", "title": "Seamless Insertion Overflow (Half 1 Full -> Half 2)", "gx": 72, "gz": 0},
        {"id": "05_unified_conditional_boolhas", "title": "Unified Conditional Quantity (BoolHas Split Items)", "gx": 96, "gz": 0},
        {"id": "06_slot_range_half_filtering", "title": "Sided Slot Range Filtering (0..26 vs 27..53)", "gx": 120, "gz": 0},
    ]

    test_results = []

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
        harness.chat(message="/weather clear 99999")
        time.sleep(1.0)

        # Teleport player near the test grid so chunks are actively loaded
        print("[*] Teleporting player to test grid center...")
        harness.teleport(x=60.0, y=10.0, z=0.0)
        time.sleep(1.0)

        # =============================================================
        # Station 1: Extract from Unconnected Half (Slot 30 in Half 2)
        # =============================================================
        print("\n=======================================================")
        print("Executing Station 1: Extract from Unconnected Half")
        s1 = stations[0]
        x1, z1 = s1["gx"], s1["gz"]
        harness.clear_area(x1 - 2, 4, z1 - 2, x1 + 6, 8, z1 + 6)
        for dx in range(-1, 5):
            for dz in range(-1, 5):
                harness.set_block(x1 + dx, 3, z1 + dz, "stone", 0)

        # Manager at (x1, 4, z1)
        # Cable line to (x1, 4, z1+1) and (x1, 4, z1+2)
        # Dst chest at (x1, 5, z1+2) -- physically separated from doublechest by air at (x1, 5, z1+1)
        # Doublechest: Half 1 at (x1, 5, z1), Half 2 at (x1+1, 5, z1)
        harness.set_block(x1, 4, z1, "superfactorymanager:manager", 0)
        harness.set_block(x1, 4, z1 + 1, "superfactorymanager:cable", 0)
        harness.set_block(x1, 4, z1 + 2, "superfactorymanager:cable", 0)
        harness.set_block(x1, 5, z1 + 2, "chest", 0)
        harness.set_block(x1, 5, z1, "chest", 0)
        harness.set_block(x1 + 1, 5, z1, "chest", 0)
        time.sleep(0.5)

        # Put 42 redstone in Half 2 (slot 3 of x1+1 = slot 30 of large chest)
        res_slot1 = set_slot(harness, x1 + 1, 5, z1, 3, "minecraft:redstone", 42)
        print(f"[Station 1 Setup] set_slot: {res_slot1}")
        time.sleep(0.3)

        # Label ONLY Half 1 (x1, 5, z1) as src
        harness.sfm_label(x=x1, y=4, z=z1, label="src", bx=x1, by=5, bz=z1)
        harness.sfm_label(x=x1, y=4, z=z1, label="dst", bx=x1, by=5, bz=z1+2)

        prog1 = "EVERY 20 TICKS DO\n  INPUT 42 FROM \"src\"\n  OUTPUT TO \"dst\"\nEND\n"
        harness.sfm_program(x=x1, y=4, z=z1, code=prog1)
        time.sleep(1.2)
        harness.sfm_update(x=x1, y=4, z=z1)
        time.sleep(0.5)

        dst_redstone = count_items(get_inv_items(harness, x1, 5, z1 + 2), "minecraft:redstone")
        src_half2_redstone = count_items(get_inv_items(harness, x1 + 1, 5, z1), "minecraft:redstone")
        print(f"[Station 1] Dst redstone: {dst_redstone}, Src Half 2 remaining: {src_half2_redstone}")
        assert dst_redstone == 42, f"Expected 42 redstone in dst, got {dst_redstone}"
        assert src_half2_redstone == 0, f"Expected 0 remaining in half 2, got {src_half2_redstone}"
        test_results.append({"id": s1["id"], "title": s1["title"], "status": "PASS", "details": "Successfully extracted 42 items from unconnected partner half (slot 30) via connected half 1."})

        # =============================================================
        # Station 2: Reverse Extraction (Slot 5 in Half 1 via Half 2)
        # =============================================================
        print("\n=======================================================")
        print("Executing Station 2: Reverse Extraction from Partner Half")
        s2 = stations[1]
        x2, z2 = s2["gx"], s2["gz"]
        harness.clear_area(x2 - 2, 4, z2 - 2, x2 + 6, 8, z2 + 6)
        for dx in range(-1, 5):
            for dz in range(-1, 5):
                harness.set_block(x2 + dx, 3, z2 + dz, "stone", 0)

        # Doublechest: Half 1 at (x2, 5, z2), Half 2 at (x2+1, 5, z2)
        # Manager at (x2+1, 4, z2) -> directly connects ONLY to Half 2!
        # Cable line to (x2+1, 4, z2+1) and (x2+1, 4, z2+2)
        # Dst chest at (x2+1, 5, z2+2)
        harness.set_block(x2 + 1, 4, z2, "superfactorymanager:manager", 0)
        harness.set_block(x2 + 1, 4, z2 + 1, "superfactorymanager:cable", 0)
        harness.set_block(x2 + 1, 4, z2 + 2, "superfactorymanager:cable", 0)
        harness.set_block(x2 + 1, 5, z2 + 2, "chest", 0)
        harness.set_block(x2, 5, z2, "chest", 0)
        harness.set_block(x2 + 1, 5, z2, "chest", 0)
        time.sleep(0.5)

        # Put 35 dye (lapis lazuli, dmg 4) in Half 1 (slot 5 of x2)
        set_slot(harness, x2, 5, z2, 5, "minecraft:dye", 35, 4)
        time.sleep(0.3)

        # Label ONLY Half 2 (x2+1, 5, z2) as src
        harness.sfm_label(x=x2+1, y=4, z=z2, label="src", bx=x2+1, by=5, bz=z2)
        harness.sfm_label(x=x2+1, y=4, z=z2, label="dst", bx=x2+1, by=5, bz=z2+2)

        prog2 = "EVERY 20 TICKS DO\n  INPUT 35 FROM \"src\"\n  OUTPUT TO \"dst\"\nEND\n"
        harness.sfm_program(x=x2+1, y=4, z=z2, code=prog2)
        time.sleep(1.2)
        harness.sfm_update(x=x2+1, y=4, z=z2)
        time.sleep(0.5)

        dst_lapis = count_items(get_inv_items(harness, x2 + 1, 5, z2 + 2), "minecraft:dye")
        src_half1_lapis = count_items(get_inv_items(harness, x2, 5, z2), "minecraft:dye")
        print(f"[Station 2] Dst lapis: {dst_lapis}, Src Half 1 remaining: {src_half1_lapis}")
        assert dst_lapis == 35, f"Expected 35 lapis in dst, got {dst_lapis}"
        assert src_half1_lapis == 0, f"Expected 0 remaining in half 1, got {src_half1_lapis}"
        test_results.append({"id": s2["id"], "title": s2["title"], "status": "PASS", "details": "Successfully extracted 35 items from unconnected lower half (slot 5) via connected half 2."})

        # =============================================================
        # Station 3: Slot Gathering Deduplication (Both Halves Labeled)
        # =============================================================
        print("\n=======================================================")
        print("Executing Station 3: Deduplication with Both Halves Labeled")
        s3 = stations[2]
        x3, z3 = s3["gx"], s3["gz"]
        harness.clear_area(x3 - 2, 4, z3 - 2, x3 + 6, 8, z3 + 6)
        for dx in range(-1, 5):
            for dz in range(-1, 5):
                harness.set_block(x3 + dx, 3, z3 + dz, "stone", 0)

        # Manager at (x3, 4, z3), Cable under Half 2 at (x3+1, 4, z3)
        # Cable line to (x3, 4, z3+1) and (x3, 4, z3+2)
        # Dst chest at (x3, 5, z3+2)
        # Doublechest: Half 1 at (x3, 5, z3), Half 2 at (x3+1, 5, z3)
        harness.set_block(x3, 4, z3, "superfactorymanager:manager", 0)
        harness.set_block(x3 + 1, 4, z3, "superfactorymanager:cable", 0)
        harness.set_block(x3, 4, z3 + 1, "superfactorymanager:cable", 0)
        harness.set_block(x3, 4, z3 + 2, "superfactorymanager:cable", 0)
        harness.set_block(x3, 5, z3 + 2, "chest", 0)
        harness.set_block(x3, 5, z3, "chest", 0)
        harness.set_block(x3 + 1, 5, z3, "chest", 0)
        time.sleep(0.5)

        # Place 20 glowstone_dust in Half 1 slot 0, and 30 in Half 2 slot 8. Total = 50.
        set_slot(harness, x3, 5, z3, 0, "minecraft:glowstone_dust", 20)
        set_slot(harness, x3 + 1, 5, z3, 8, "minecraft:glowstone_dust", 30)
        time.sleep(0.3)

        # Label BOTH halves under the SAME label 'dchest'
        harness.sfm_label(x=x3, y=4, z=z3, label="dchest", bx=x3, by=5, bz=z3)
        harness.sfm_label(x=x3, y=4, z=z3, label="dchest", bx=x3+1, by=5, bz=z3)
        harness.sfm_label(x=x3, y=4, z=z3, label="dst", bx=x3, by=5, bz=z3+2)

        # Transfer 10 items. If deduplication failed, it would gather slots twice and move 20 items!
        prog3 = "EVERY 20 TICKS DO\n  INPUT 10 FROM \"dchest\"\n  OUTPUT TO \"dst\"\nEND\n"
        harness.sfm_program(x=x3, y=4, z=z3, code=prog3)
        time.sleep(1.2)
        harness.sfm_update(x=x3, y=4, z=z3)
        time.sleep(0.5)

        dst_glow = count_items(get_inv_items(harness, x3, 5, z3 + 2), "minecraft:glowstone_dust")
        h1_glow = count_items(get_inv_items(harness, x3, 5, z3), "minecraft:glowstone_dust")
        h2_glow = count_items(get_inv_items(harness, x3 + 1, 5, z3), "minecraft:glowstone_dust")
        print(f"[Station 3] Dst glowstone: {dst_glow}, Remaining: H1={h1_glow}, H2={h2_glow}, Total={h1_glow + h2_glow}")
        assert dst_glow == 10, f"Expected exactly 10 glowstone transferred, got {dst_glow} (deduplication bug!)"
        assert h1_glow + h2_glow == 40, f"Expected 40 glowstone remaining in doublechest, got {h1_glow + h2_glow}"
        test_results.append({"id": s3["id"], "title": s3["title"], "status": "PASS", "details": "Verified slot gathering deduplication: exactly 10 items transferred despite both halves being labeled and connected."})

        # =============================================================
        # Station 4: Seamless Insertion Overflow across Halves
        # =============================================================
        print("\n=======================================================")
        print("Executing Station 4: Seamless Insertion Overflow across Halves")
        s4 = stations[3]
        x4, z4 = s4["gx"], s4["gz"]
        harness.clear_area(x4 - 2, 4, z4 - 2, x4 + 6, 8, z4 + 6)
        for dx in range(-1, 5):
            for dz in range(-1, 5):
                harness.set_block(x4 + dx, 3, z4 + dz, "stone", 0)

        harness.set_block(x4, 4, z4, "superfactorymanager:manager", 0)
        harness.set_block(x4, 4, z4 + 1, "superfactorymanager:cable", 0)
        harness.set_block(x4, 4, z4 + 2, "superfactorymanager:cable", 0)
        harness.set_block(x4, 5, z4 + 2, "chest", 0)
        harness.set_block(x4, 5, z4, "chest", 0)
        harness.set_block(x4 + 1, 5, z4, "chest", 0)
        time.sleep(0.5)

        # Pre-fill all 27 slots of Half 1 (x4, 5, z4) with 64 dirt each!
        print("[Station 4] Pre-filling Half 1 (slots 0..26) completely with dirt...")
        for s in range(27):
            set_slot(harness, x4, 5, z4, s, "minecraft:dirt", 64)
        time.sleep(0.5)

        # Put 64 diamonds into source chest at (x4, 5, z4+2)
        set_slot(harness, x4, 5, z4 + 2, 0, "minecraft:diamond", 64)
        time.sleep(0.3)

        # Label source chest and label doublechest (only Half 1 is labeled)
        harness.sfm_label(x=x4, y=4, z=z4, label="src", bx=x4, by=5, bz=z4+2)
        harness.sfm_label(x=x4, y=4, z=z4, label="dchest", bx=x4, by=5, bz=z4)

        # Output to dchest. Since Half 1 is 100% full, it must overflow into Half 2!
        prog4 = "EVERY 20 TICKS DO\n  INPUT 64 FROM \"src\"\n  OUTPUT TO \"dchest\"\nEND\n"
        harness.sfm_program(x=x4, y=4, z=z4, code=prog4)
        time.sleep(1.2)
        harness.sfm_update(x=x4, y=4, z=z4)
        time.sleep(0.5)

        src_diamonds = count_items(get_inv_items(harness, x4, 5, z4 + 2), "minecraft:diamond")
        h2_diamonds = count_items(get_inv_items(harness, x4 + 1, 5, z4), "minecraft:diamond")
        print(f"[Station 4] Src diamonds remaining: {src_diamonds}, Half 2 diamonds received: {h2_diamonds}")
        assert src_diamonds == 0, f"Expected 0 diamonds remaining in src, got {src_diamonds}"
        assert h2_diamonds == 64, f"Expected 64 diamonds overflowed into Half 2, got {h2_diamonds}"
        test_results.append({"id": s4["id"], "title": s4["title"], "status": "PASS", "details": "Verified seamless insertion overflow: 64 diamonds flowed into Half 2 when Half 1 was completely full."})

        # =============================================================
        # Station 5: Unified Conditional Quantity (BoolHas Split Items)
        # =============================================================
        print("\n=======================================================")
        print("Executing Station 5: Unified Conditional Quantity (BoolHas)")
        s5 = stations[4]
        x5, z5 = s5["gx"], s5["gz"]
        harness.clear_area(x5 - 2, 4, z5 - 2, x5 + 6, 8, z5 + 6)
        for dx in range(-1, 5):
            for dz in range(-1, 5):
                harness.set_block(x5 + dx, 3, z5 + dz, "stone", 0)

        # Manager at (x5, 4, z5), Cable under Half 2 at (x5+1, 4, z5)
        harness.set_block(x5, 4, z5, "superfactorymanager:manager", 0)
        harness.set_block(x5 + 1, 4, z5, "superfactorymanager:cable", 0)
        # Continuous cable branch to z+2
        harness.set_block(x5, 4, z5 + 1, "superfactorymanager:cable", 0)
        harness.set_block(x5, 4, z5 + 2, "superfactorymanager:cable", 0)
        harness.set_block(x5 + 1, 4, z5 + 2, "superfactorymanager:cable", 0)
        harness.set_block(x5 + 2, 4, z5 + 2, "superfactorymanager:cable", 0)
        # Feed chest at (x5, 5, z5+2)
        harness.set_block(x5, 5, z5 + 2, "chest", 0)
        # Result chest at (x5+2, 5, z5+2) -- separated from feed chest by air at (x5+1, 5, z5+2)
        harness.set_block(x5 + 2, 5, z5 + 2, "chest", 0)
        # Doublechest: Half 1 at (x5, 5, z5), Half 2 at (x5+1, 5, z5)
        harness.set_block(x5, 5, z5, "chest", 0)
        harness.set_block(x5 + 1, 5, z5, "chest", 0)
        time.sleep(0.5)

        # Place 20 iron_ingot in Half 1 slot 2, and 20 iron_ingot in Half 2 slot 14. Total = 40.
        set_slot(harness, x5, 5, z5, 2, "minecraft:iron_ingot", 20)
        set_slot(harness, x5 + 1, 5, z5, 14, "minecraft:iron_ingot", 20)
        # Put probe items in feed chest: 64 gold_ingot, 64 diamond, 64 emerald
        set_slot(harness, x5, 5, z5 + 2, 0, "minecraft:gold_ingot", 64)
        set_slot(harness, x5, 5, z5 + 2, 1, "minecraft:diamond", 64)
        set_slot(harness, x5, 5, z5 + 2, 2, "minecraft:emerald", 64)
        time.sleep(0.3)

        # Label both halves as dchest
        harness.sfm_label(x=x5, y=4, z=z5, label="dchest", bx=x5, by=5, bz=z5)
        harness.sfm_label(x=x5, y=4, z=z5, label="dchest", bx=x5+1, by=5, bz=z5)
        harness.sfm_label(x=x5, y=4, z=z5, label="feed", bx=x5, by=5, bz=z5+2)
        harness.sfm_label(x=x5, y=4, z=z5, label="result", bx=x5+2, by=5, bz=z5+2)

        # Subtest 5A: IF dchest HAS >= 40 iron_ingot (Split 20 + 20 = 40) -> transfer gold
        prog5a = "EVERY 20 TICKS DO\n  IF \"dchest\" HAS >= 40 iron_ingot THEN\n    INPUT 1 gold_ingot FROM \"feed\"\n    OUTPUT TO \"result\"\n  END\nEND\n"
        res_p5a = harness.sfm_program(x=x5, y=4, z=z5, code=prog5a)
        print(f"[Station 5A] sfm_program response: {res_p5a}")
        time.sleep(1.2)
        harness.sfm_update(x=x5, y=4, z=z5)
        time.sleep(0.5)

        res5a = count_items(get_inv_items(harness, x5 + 2, 5, z5 + 2), "minecraft:gold_ingot")
        print(f"[Station 5A] Condition >= 40: Result gold = {res5a}")
        assert res5a >= 1, f"Expected at least 1 gold in result, got {res5a}"

        # Subtest 5B: IF dchest HAS > 40 iron_ingot (Should evaluate FALSE) -> transfer diamond
        # If double-counting bug existed, count would be 80 and this would fire!
        prog5b = "EVERY 20 TICKS DO\n  IF \"dchest\" HAS > 40 iron_ingot THEN\n    INPUT 10 diamond FROM \"feed\"\n    OUTPUT TO \"result\"\n  END\nEND\n"
        res_p5b = harness.sfm_program(x=x5, y=4, z=z5, code=prog5b)
        print(f"[Station 5B] sfm_program response: {res_p5b}")
        time.sleep(1.2)
        harness.sfm_update(x=x5, y=4, z=z5)
        time.sleep(0.5)

        res5b = count_items(get_inv_items(harness, x5 + 2, 5, z5 + 2), "minecraft:diamond")
        print(f"[Station 5B] Condition > 40: Result diamonds = {res5b}")
        assert res5b == 0, f"Expected 0 diamonds in result, got {res5b} (double counting occurred!)"

        # Subtest 5C: IF dchest HAS EQ 40 iron_ingot (Exact match using EQ) -> transfer emerald
        prog5c = "EVERY 20 TICKS DO\n  IF \"dchest\" HAS EQ 40 iron_ingot THEN\n    INPUT 2 emerald FROM \"feed\"\n    OUTPUT TO \"result\"\n  END\nEND\n"
        res_p5c = harness.sfm_program(x=x5, y=4, z=z5, code=prog5c)
        print(f"[Station 5C] sfm_program response: {res_p5c}")
        time.sleep(1.2)
        harness.sfm_update(x=x5, y=4, z=z5)
        time.sleep(0.5)

        res5c = count_items(get_inv_items(harness, x5 + 2, 5, z5 + 2), "minecraft:emerald")
        print(f"[Station 5C] Condition == 40: Result emeralds = {res5c}")
        assert res5c >= 2 and res5c % 2 == 0, f"Expected at least 2 emeralds (multiple of 2), got {res5c}"
        test_results.append({"id": s5["id"], "title": s5["title"], "status": "PASS", "details": "Verified unified BoolHas evaluation: split quantities (20+20=40) accurately satisfy >= 40 and == 40 without double-counting on > 40."})

        # =============================================================
        # Station 6: Sided Slot Range Filtering (0..26 vs 27..53)
        # =============================================================
        print("\n=======================================================")
        print("Executing Station 6: Sided Slot Range Filtering")
        s6 = stations[5]
        x6, z6 = s6["gx"], s6["gz"]
        harness.clear_area(x6 - 2, 4, z6 - 2, x6 + 6, 8, z6 + 6)
        for dx in range(-1, 5):
            for dz in range(-1, 5):
                harness.set_block(x6 + dx, 3, z6 + dz, "stone", 0)

        # Manager at (x6, 4, z6), Cable under Half 2 at (x6+1, 4, z6)
        harness.set_block(x6, 4, z6, "superfactorymanager:manager", 0)
        harness.set_block(x6 + 1, 4, z6, "superfactorymanager:cable", 0)
        # Continuous cable line to z+2
        harness.set_block(x6, 4, z6 + 1, "superfactorymanager:cable", 0)
        harness.set_block(x6, 4, z6 + 2, "superfactorymanager:cable", 0)
        harness.set_block(x6 + 1, 4, z6 + 2, "superfactorymanager:cable", 0)
        harness.set_block(x6 + 2, 4, z6 + 2, "superfactorymanager:cable", 0)
        # Dst chests
        harness.set_block(x6, 5, z6 + 2, "chest", 0)
        harness.set_block(x6 + 2, 5, z6 + 2, "chest", 0)
        # Doublechest
        harness.set_block(x6, 5, z6, "chest", 0)
        harness.set_block(x6 + 1, 5, z6, "chest", 0)
        time.sleep(0.5)

        # Place 32 emeralds in Half 1 slot 0 (slot 0 of large chest)
        # Place 32 gold_ingots in Half 2 slot 5 (slot 32 of large chest)
        set_slot(harness, x6, 5, z6, 0, "minecraft:emerald", 32)
        set_slot(harness, x6 + 1, 5, z6, 5, "minecraft:gold_ingot", 32)
        time.sleep(0.3)

        harness.sfm_label(x=x6, y=4, z=z6, label="dchest", bx=x6, by=5, bz=z6)
        harness.sfm_label(x=x6, y=4, z=z6, label="dst_half1", bx=x6, by=5, bz=z6+2)
        harness.sfm_label(x=x6, y=4, z=z6, label="dst_half2", bx=x6+2, by=5, bz=z6+2)

        # Program: Extract SLOTS 27-53 into dst_half2, then FORGET and extract SLOTS 0-26 into dst_half1
        prog6 = (
            "EVERY 20 TICKS DO\n"
            "  INPUT 16 FROM \"dchest\" SLOTS 27-53\n"
            "  OUTPUT TO \"dst_half2\"\n"
            "  FORGET\n"
            "  INPUT 16 FROM \"dchest\" SLOTS 0-26\n"
            "  OUTPUT TO \"dst_half1\"\n"
            "END\n"
        )
        harness.sfm_program(x=x6, y=4, z=z6, code=prog6)
        time.sleep(1.2)
        harness.sfm_update(x=x6, y=4, z=z6)
        time.sleep(0.5)

        dst1_emeralds = count_items(get_inv_items(harness, x6, 5, z6 + 2), "minecraft:emerald")
        dst1_gold = count_items(get_inv_items(harness, x6, 5, z6 + 2), "minecraft:gold_ingot")
        dst2_emeralds = count_items(get_inv_items(harness, x6 + 2, 5, z6 + 2), "minecraft:emerald")
        dst2_gold = count_items(get_inv_items(harness, x6 + 2, 5, z6 + 2), "minecraft:gold_ingot")

        print(f"[Station 6] Dst Half 1: Emeralds={dst1_emeralds}, Gold={dst1_gold}")
        print(f"[Station 6] Dst Half 2: Emeralds={dst2_emeralds}, Gold={dst2_gold}")

        assert dst1_emeralds >= 16 and dst1_gold == 0, f"Dst Half 1 filter failed: {dst1_emeralds} emeralds, {dst1_gold} gold"
        assert dst2_gold >= 16 and dst2_emeralds == 0, f"Dst Half 2 filter failed: {dst2_emeralds} emeralds, {dst2_gold} gold"
        test_results.append({"id": s6["id"], "title": s6["title"], "status": "PASS", "details": "Verified canonical slot range filtering: SLOTS 0-26 and 27-53 accurately addressed their respective halves with zero cross-contamination."})

        # =============================================================
        # Capture Screenshots
        # =============================================================
        print("\n=======================================================")
        print("CAPTURING VISUAL ARTIFACTS")
        print("=======================================================")

        for st in stations:
            gx = st["gx"]
            gz = st["gz"]
            sid = st["id"]
            harness.close_gui()
            time.sleep(0.1)
            harness.teleport(x=gx-2.0, y=7.5, z=gz-2.0)
            harness.look(yaw=-45, pitch=25)
            time.sleep(0.3)
            img_path = OUTPUT_DIR / f"{sid}.png"
            harness.screenshot(path=str(img_path))
            print(f"[+] Captured {img_path.name}")
            shutil.copy2(img_path, ARTIFACTS_DIR / img_path.name)

        # Aerial overview
        harness.teleport(x=60.0, y=30.0, z=-15.0)
        harness.look(yaw=0, pitch=45)
        time.sleep(0.5)
        aerial_path = OUTPUT_DIR / "00_doublechest_aerial_overview.png"
        harness.screenshot(path=str(aerial_path))
        print(f"[+] Captured {aerial_path.name}")
        shutil.copy2(aerial_path, ARTIFACTS_DIR / aerial_path.name)

        print("\n[*] Exiting world gracefully via HarnessClient...")
        harness.exitworld()

    print("\n=======================================================")
    print("ALL 6 DOUBLE CHEST STATIONS PASSED PROGRAMMATIC ASSERTIONS!")
    print("=======================================================")

    return test_results


if __name__ == "__main__":
    results = run_doublechest_suite()
    print(json.dumps(results, indent=2))
