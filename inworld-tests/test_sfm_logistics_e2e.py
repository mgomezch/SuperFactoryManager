#!/usr/bin/env python3
"""
End-to-End Automated Test for SuperFactoryManager Item Logistics.

Feature Under Test:
- Creative Vending Drawer -> Chest transfer every 20 ticks (1s) using 64 items per transfer.
- Chest -> Extra Utilities Trash Can transfer every 200 ticks (10s), clearing all items.
- Network connected via SuperFactoryManager Fancy Inventory Cables (superfactorymanager:fancyCable).
- Automated execution via WorldSandbox (RAM overlayfs over golden superflat template).
- Automatic screenshot generation of 3D build and all relevant block/editor GUIs.
"""

import sys
import os
import time
import json
import shutil
import socket
import subprocess
from pathlib import Path
from typing import Dict, Any, Optional

# Resolve nh-dev monorepo root
from nhdev import NH_DEV_ROOT as ROOT_DIR

from tools.agentharness import HarnessClient
from tools.sfm_client import SfmClient
from tools.runner_instance import ensure_sfm_instance_running, get_saves_dir
from tools.bot.world_manager import WorldSandbox
from tools.bot.display import ensure_display

OUTPUT_DIR = ROOT_DIR / "output" / "sfm_logistics"
ARTIFACTS_DIR = Path("/home/mgomezch/.gemini/antigravity/brain/a95f94f4-9edd-4aba-8218-d471d3db8067")
HARNESS_PORT = 25572
SFM_PORT = 25573


def run_test():
    from tools.mcfleet.client import FleetClient
    client = FleetClient()

    ensure_sfm_instance_running()

    sfm = SfmClient(port=SFM_PORT)
    harness = HarnessClient(port=HARNESS_PORT)

    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

    world_name = "sfm_logistics_e2e"
    saves_dir = get_saves_dir(client, "sfm-test-runner")
    saves_dir.mkdir(parents=True, exist_ok=True)
    print(f"[*] Mounting ephemeral WorldSandbox over 'superflat_empty' into '{saves_dir}' as '{world_name}'...")
    sandbox = WorldSandbox("superflat_empty", world_name=world_name, saves_dir=saves_dir)

    with sandbox:
        print(f"[*] Launching world '{world_name}'...")
        res = sfm.autoworld(name=world_name)
        print(f"[<] {res}")

        # Wait for IN_WORLD
        print("[*] Waiting for player to enter world...")
        start_w = time.time()
        in_world = False
        while time.time() - start_w < 120:
            st = sfm.status()
            if "IN_WORLD" in st:
                in_world = True
                print(f"[+] In world: {st}")
                break
            time.sleep(1)

        if not in_world:
            raise RuntimeError(f"Failed to enter world, status: {sfm.status()}")

        time.sleep(2)

        # Basic world configuration: zero daylight cycle, zero mob spawning, zero weather, fixed noon, peaceful, creative
        sfm.chat(message="/gamemode 1")
        sfm.chat(message="/gamerule doDaylightCycle false")
        sfm.chat(message="/gamerule doMobSpawning false")
        sfm.chat(message="/gamerule mobGriefing false")
        sfm.chat(message="/time set 6000")
        sfm.chat(message="/weather clear 999999")
        sfm.chat(message="/difficulty 0")
        sfm.chat(message="/cofh killall *")
        time.sleep(0.5)

        # Clear area and build clean floor
        print("[*] Clearing construction area and laying foundation...")
        harness.clear_area(x1=7, y1=4, z1=417, x2=13, y2=8, z2=425)
        time.sleep(0.5)
        harness.fill_area(x1=7, y1=4, z1=417, x2=13, y2=4, z2=425, block="minecraft:quartz_block", meta=0)
        time.sleep(0.5)

        # Teleport player to test site standing safely on quartz floor
        sfm.teleport(x=7.5, y=5.5, z=417.0)
        time.sleep(0.5)

        # Setup components:
        # Manager at (10, 5, 420)
        # Fancy Cable at (10, 5, 421)
        # Chest at (11, 5, 421)
        # Trash Can at (9, 5, 421)
        # Creative Vending Drawer at (10, 5, 422)
        print("[*] Placing Manager, Fancy Cable, Chest, and Trash Can...")
        harness.set_block(10, 5, 420, "superfactorymanager:manager", 0)
        harness.set_block(10, 5, 421, "superfactorymanager:fancyCable", 0)
        harness.set_block(11, 5, 421, "minecraft:chest", 0)
        harness.set_block(9, 5, 421, "ExtraUtilities:trashcan", 0)
        time.sleep(0.5)

        print("[*] Configuring Creative Vending Drawer with cobblestone at (10, 5, 422)...")
        # 10 5 422, minecraft:cobblestone, meta 0, facing 2 (North facing cable)
        res_dr = harness.setup_vending_drawer(x=10, y=5, z=422, item="minecraft:cobblestone", meta=0, dir=2)
        print(f"[<] Drawer setup: {res_dr}")
        time.sleep(0.5)

        # Configure SFM Labels
        print("[*] Assigning peripheral labels on Manager...")
        sfm.clearlabels(x=10, y=5, z=420)
        sfm.setlabel(x=10, y=5, z=420, label="drawer", tx=10, ty=5, tz=422)
        sfm.setlabel(x=10, y=5, z=420, label="chest", tx=11, ty=5, tz=421)
        sfm.setlabel(x=10, y=5, z=420, label="trash", tx=9, ty=5, tz=421)

        # Program the Manager
        program_code = (
            "NAME \"Item Logistics\"\n"
            "EVERY 20 TICKS DO\n"
            "  INPUT 64 FROM drawer\n"
            "  OUTPUT TO chest\n"
            "END\n"
            "EVERY 200 TICKS DO\n"
            "  INPUT FROM chest\n"
            "  OUTPUT TO trash\n"
            "END\n"
        )
        print("[*] Programming Manager with dual-interval logistics routine...")
        res_prog = sfm.programmanager(x=10, y=5, z=420, code=program_code)
        print(f"[<] Program Manager: {res_prog}")
        assert res_prog["valid"], f"Program compilation failed: {res_prog}"
        sfm.setrunning(x=10, y=5, z=420, running=False)

        # Assert initial state: Chest is empty
        chest_inv_raw = harness.container_info(x=11, y=5, z=421)
        chest_inv = chest_inv_raw
        initial_chest_items = sum(s["count"] for s in chest_inv.get("slots", []))
        print(f"[+] Initial chest item count: {initial_chest_items}")
        assert initial_chest_items == 0, f"Chest was not empty at start: {initial_chest_items}"

        # Take 3D Perspective Screenshot
        print("[*] Capturing 3D perspective screenshot of build...")
        sfm.closegui()
        time.sleep(0.3)
        sfm.teleport(x=7.5, y=7.5, z=417.0)
        sfm.look(yaw=-45, pitch=25)
        time.sleep(0.5)
        p3d = OUTPUT_DIR / "01_build_3d_perspective.png"
        sfm.screenshot(path=p3d)
        print(f"[+] Saved {p3d}")

        # Open and capture SFM Code Editor GUI
        print("[*] Capturing SFM Code Editor GUI...")
        sfm.opengui(gui="editor", x=10, y=5, z=420)
        time.sleep(0.5)
        p_edit = OUTPUT_DIR / "02_code_editor_gui.png"
        sfm.screenshot(path=p_edit)
        sfm.closegui()
        time.sleep(0.3)
        print(f"[+] Saved {p_edit}")

        # Open and capture SFM Manager GUI
        print("[*] Capturing SFM Manager GUI...")
        sfm.opengui(gui="manager", x=10, y=5, z=420)
        time.sleep(0.5)
        p_mgr = OUTPUT_DIR / "03_manager_gui.png"
        sfm.screenshot(path=p_mgr)
        sfm.closegui()
        time.sleep(0.3)
        print(f"[+] Saved {p_mgr}")

        # Start simulation and wait until tick 65 (3 full transfers of 64 cobblestones: ticks 20, 40, 60)
        print("[*] Starting simulation to tick 65 for Creative Drawer -> Chest transfer assertion...")
        sfm.settick(x=10, y=5, z=420, tick=0)
        sfm.setrunning(x=10, y=5, z=420, running=True)
        t0 = time.time()
        while time.time() - t0 < 15:
            mgr_status = sfm.getmanagerstatus(x=10, y=5, z=420)
            if mgr_status["tick"] >= 65:
                break
            time.sleep(0.1)

        # Pause manager during assertion and chest GUI capture so it doesn't tick further
        sfm.setrunning(x=10, y=5, z=420, running=False)
        time.sleep(0.2)

        mgr_status_after = sfm.getmanagerstatus(x=10, y=5, z=420)
        print(f"[+] Manager status at tick {mgr_status_after['tick']}: {mgr_status_after['status']}")

        # Verify Chest received 3 stacks of 64 = 192 cobblestone
        chest_inv_60 = harness.container_info(x=11, y=5, z=421)
        chest_items_60 = sum(s["count"] for s in chest_inv_60.get("slots", []))
        print(f"[+] Chest items at tick {mgr_status_after['tick']}: {chest_items_60} items")
        print(f"[+] Chest inventory details: {chest_inv_60.get('slots', [])}")
        assert chest_items_60 == 192, f"Expected 192 cobblestone in chest, got {chest_items_60}"
        print("[✓] ASSERTION PASSED: Drawer successfully transferred 192 items (3x64) into chest!")

        # Open and capture Chest GUI showing items
        print("[*] Capturing Chest GUI...")
        sfm.teleport(x=11.5, y=5.5, z=419.5)
        sfm.look(yaw=180, pitch=30)
        time.sleep(0.3)
        sfm.rightclickblock(x=11, y=5, z=421, side=1)
        time.sleep(0.5)
        p_chest = OUTPUT_DIR / "04_chest_gui.png"
        sfm.screenshot(path=p_chest)
        sfm.closegui()
        time.sleep(0.3)
        print(f"[+] Saved {p_chest}")

        # Step simulation to tick 201 (the 200 tick trash routine clears the chest)
        print("[*] Advancing simulation to tick 201 for Chest -> Trash Can voiding assertion...")
        sfm.settick(x=10, y=5, z=420, tick=199)
        sfm.setrunning(x=10, y=5, z=420, running=True)
        sfm.stepmanager(x=10, y=5, z=420, ticks=2)
        sfm.setrunning(x=10, y=5, z=420, running=False)
        time.sleep(0.2)

        mgr_status_final = sfm.getmanagerstatus(x=10, y=5, z=420)
        print(f"[+] Manager status at tick {mgr_status_final['tick']}: {mgr_status_final['status']}")

        # Verify Chest was cleared by the trash can
        chest_inv_final = harness.container_info(x=11, y=5, z=421)
        chest_items_final = sum(s["count"] for s in chest_inv_final.get("slots", []))
        print(f"[+] Chest items at tick {mgr_status_final['tick']}: {chest_items_final} items")
        assert chest_items_final == 0, f"Expected chest to be emptied by trash can, but had {chest_items_final}"
        print("[✓] ASSERTION PASSED: Trash Can successfully voided all items from chest!")

        # Open and capture Trash Can GUI
        print("[*] Capturing Trash Can GUI...")
        sfm.teleport(x=9.5, y=5.5, z=419.5)
        sfm.look(yaw=180, pitch=30)
        time.sleep(0.3)
        sfm.rightclickblock(x=9, y=5, z=421, side=1)
        time.sleep(0.5)
        p_trash = OUTPUT_DIR / "05_trashcan_gui.png"
        sfm.screenshot(path=p_trash)
        sfm.closegui()
        time.sleep(0.3)
        print(f"[+] Saved {p_trash}")

        # Close up of Drawer
        print("[*] Capturing Creative Vending Drawer close-up...")
        sfm.teleport(x=10.0, y=5.5, z=423.8)
        sfm.look(yaw=180, pitch=15)
        time.sleep(0.3)
        p_draw = OUTPUT_DIR / "06_drawer_closeup.png"
        sfm.screenshot(path=p_draw)
        print(f"[+] Saved {p_draw}")

        # Copy all generated screenshots into artifacts dir for easy viewing
        for f in OUTPUT_DIR.glob("*.png"):
            dest_art = ARTIFACTS_DIR / f.name
            shutil.copy2(f, dest_art)
            print(f"[+] Copied {f.name} -> {dest_art}")

        # Safely exit world
        print("[*] Exiting world to release locks...")
        sfm.chat(message="/save-all")
        time.sleep(0.5)
        res_exit = harness.exitworld()
        print(f"[+] Exited world: {res_exit}")

    print("\n========================================================")
    print("✓ SFM LOGISTICS END-TO-END TEST PASSED SUCCESSFULLY!")
    print(f"Artifacts saved in {OUTPUT_DIR}")
    print("========================================================\n")


if __name__ == "__main__":
    run_test()
