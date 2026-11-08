#!/usr/bin/env python3
"""
Test setup for Fancy Inventory Cable visual rendering.
Builds multiple cable connection topologies (standalone, straight, T-junction, 6-way star, chest connection)
and captures screenshots from several perspectives to verify mesh continuity and detect missing faces / transparent gaps.
"""

import json
import os
import shutil
import sys
import time
from pathlib import Path

from nhdev import NH_DEV_ROOT as REPO_ROOT

from tools.agentharness import HarnessClient
from tools.mcfleet.client import FleetClient
from tools.sfm_client import SfmClient
from tools.runner_instance import ensure_sfm_instance_running, get_saves_dir
from tools.bot.world_manager import WorldSandbox

HARNESS_PORT = 25572
SFM_PORT = 25573

OUTPUT_DIR = REPO_ROOT / "output" / "fancy_cable_visual"
ARTIFACTS_DIR = Path("/home/mgomezch/.gemini/antigravity/brain/a95f94f4-9edd-4aba-8218-d471d3db8067")


import math

def capture_look_at(sfm: SfmClient, filename: str, cx: float, cy: float, cz: float, tx: float, ty: float, tz: float, sleep_after: float = 0.5):
    sfm.closegui()
    time.sleep(0.1)
    sfm.teleport(x=cx, y=cy, z=cz)
    dx = tx - cx
    dy = ty - (cy + 1.62)
    dz = tz - cz
    dist_xz = math.hypot(dx, dz)
    yaw = math.degrees(math.atan2(-dx, dz))
    pitch = math.degrees(math.atan2(-dy, dist_xz))
    sfm.look(yaw=yaw, pitch=pitch)
    time.sleep(sleep_after)
    target_path = OUTPUT_DIR / filename
    sfm.screenshot(path=target_path)
    print(f"[+] Captured {filename}", flush=True)
    if ARTIFACTS_DIR.exists():
        shutil.copy2(target_path, ARTIFACTS_DIR / filename)


def run_visual_suite(prefix: str = "current"):
    client = FleetClient()
    ensure_sfm_instance_running()

    sfm = SfmClient(port=SFM_PORT)
    harness = HarnessClient(port=HARNESS_PORT)

    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)
    ARTIFACTS_DIR.mkdir(parents=True, exist_ok=True)

    world_name = "fancy_cable_visual"
    saves_dir = get_saves_dir(client)
    saves_dir.mkdir(parents=True, exist_ok=True)
    print(f"[*] Mounting ephemeral WorldSandbox into '{saves_dir}' as '{world_name}'...")

    def safe_exit():
        try:
            harness.exitworld()
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
            raise RuntimeError(f"Failed to enter world: {harness.status()}")

        time.sleep(1.5)
        sfm.closegui()
        sfm.chat(message="/gamemode 1")
        sfm.chat(message="/gamerule doDaylightCycle false")
        sfm.chat(message="/gamerule doMobSpawning false")
        sfm.chat(message="/time set 6000")
        sfm.chat(message="/weather clear 999999")
        sfm.chat(message="/difficulty 0")
        time.sleep(0.5)

        # Clear area
        harness.clear_area(-5, 4, -10, 55, 12, 10)
        # Stone floor at Y=3
        for dx in range(-5, 55):
            for dz in range(-10, 10):
                harness.set_block(dx, 3, dz, "stone", 0)
        time.sleep(0.5)

        # -------------------------------------------------------------
        # Station A: Isolated single fancy cable at (0, 5, 0)
        # -------------------------------------------------------------
        harness.set_block(0, 5, 0, "superfactorymanager:fancyCable", 0)

        # -------------------------------------------------------------
        # Station B: Straight line of 3 fancy cables at (10..12, 5, 0)
        # -------------------------------------------------------------
        harness.set_block(10, 5, 0, "superfactorymanager:fancyCable", 0)
        harness.set_block(11, 5, 0, "superfactorymanager:fancyCable", 0)
        harness.set_block(12, 5, 0, "superfactorymanager:fancyCable", 0)

        # -------------------------------------------------------------
        # Station C: T-Junction at (20, 5, 0) connected West, East, South
        # -------------------------------------------------------------
        harness.set_block(19, 5, 0, "superfactorymanager:fancyCable", 0)
        harness.set_block(20, 5, 0, "superfactorymanager:fancyCable", 0)
        harness.set_block(21, 5, 0, "superfactorymanager:fancyCable", 0)
        harness.set_block(20, 5, 1, "superfactorymanager:fancyCable", 0)

        # -------------------------------------------------------------
        # Station D: 6-Way Star Cross centered at (30, 6, 0)
        # -------------------------------------------------------------
        harness.set_block(30, 6, 0, "superfactorymanager:fancyCable", 0)
        harness.set_block(30, 7, 0, "superfactorymanager:fancyCable", 0) # Up
        harness.set_block(30, 5, 0, "superfactorymanager:fancyCable", 0) # Down
        harness.set_block(29, 6, 0, "superfactorymanager:fancyCable", 0) # West
        harness.set_block(31, 6, 0, "superfactorymanager:fancyCable", 0) # East
        harness.set_block(30, 6, -1, "superfactorymanager:fancyCable", 0) # North
        harness.set_block(30, 6, 1, "superfactorymanager:fancyCable", 0) # South

        # -------------------------------------------------------------
        # Station E: Fancy Cable connected to a Chest at (40, 5, 0) -> (41, 5, 0)
        # -------------------------------------------------------------
        harness.set_block(40, 5, 0, "superfactorymanager:fancyCable", 0)
        harness.set_block(41, 5, 0, "chest", 0)

        time.sleep(1.0)

        # -------------------------------------------------------------
        # Capture Perspectives
        # -------------------------------------------------------------
        print("\n[*] Capturing screenshots from various perspectives...", flush=True)

        # 1. Overview of all test setups
        capture_look_at(sfm, f"{prefix}_00_all_setups_overview.png", 20.0, 11.0, -12.0, 20.0, 5.5, 0.5)

        # 2. Station A: Standalone cable close-up
        capture_look_at(sfm, f"{prefix}_01_standalone_cable.png", 0.5, 4.2, -1.8, 0.5, 5.5, 0.5)

        # 3. Station B: Straight line cable - isometric showing core shoulders
        capture_look_at(sfm, f"{prefix}_02_straight_line_iso.png", 9.0, 4.8, -2.0, 11.0, 5.5, 0.5)

        # 4. Station B: Straight line cable - close-up on middle cable junction
        capture_look_at(sfm, f"{prefix}_03_straight_line_junction_closeup.png", 10.3, 4.2, -1.2, 11.0, 5.5, 0.5)

        # 5. Station C: T-Junction isometric
        capture_look_at(sfm, f"{prefix}_04_t_junction_iso.png", 18.5, 4.8, -2.0, 20.5, 5.5, 0.5)

        # 6. Station C: T-Junction looking into the corner junction / shoulder
        capture_look_at(sfm, f"{prefix}_05_t_junction_corner_closeup.png", 19.5, 4.2, 1.8, 20.5, 5.5, 0.5)

        # 7. Station D: 6-Way Star Cross isometric
        capture_look_at(sfm, f"{prefix}_06_star_cross_iso.png", 28.0, 5.5, -2.5, 30.5, 6.5, 0.5)

        # 8. Station D: 6-Way Star Cross looking slightly up at underside shoulders
        capture_look_at(sfm, f"{prefix}_07_star_cross_underneath.png", 29.0, 3.8, -1.8, 30.5, 6.5, 0.5)

        # 9. Station E: Cable to Chest connection
        capture_look_at(sfm, f"{prefix}_08_chest_connection.png", 39.0, 4.5, -1.8, 40.5, 5.5, 0.5)

        print("[*] Completed capturing visual screenshots!", flush=True)
        time.sleep(0.5)
        harness.exitworld()

    print("[+] Test run finished cleanly.")


if __name__ == "__main__":
    prefix = sys.argv[1] if len(sys.argv) > 1 else "current"
    run_visual_suite(prefix)
