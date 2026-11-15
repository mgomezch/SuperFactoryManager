#!/usr/bin/env python3
"""
Automated SFM Logistics Test Script for AgentHarness & InWorldTestDriver
Tests StevesFactoryManager item logistics:
  Stage 1 (Build Phase): Sets up Manager, Cables, Chests, loads Chest A with iron,
                         assigns labels, and compiles SFM program while ticks are frozen.
                         Saves snapshot to 'sfm_logistics_pre'.
  Stage 2 (Execution Phase): Ephemeral run from 'sfm_logistics_pre' with ticks unfrozen.
                             SFM program executes, moving items from Chest A to Chest B.
                             Asserts successful item transfer.
                             Saves snapshot to 'sfm_logistics_post'.
"""

import sys
import os
import time
from pathlib import Path

# Resolve repository root
from nhdev import NH_DEV_ROOT as REPO_ROOT

from tools.agentharness import HarnessClient, HarnessError
from tools.inworld_test_driver import InWorldTestDriver

# Spawn chunk coordinates
MX, MY, MZ = 500, 6, 40
CA_X, CA_Y, CA_Z = 501, 7, 40
CB_X, CB_Y, CB_Z = 502, 7, 40
PL_X, PL_Y, PL_Z = 500, 6, 38

def build_sfm_logistics(client: HarnessClient):
    """Stage 1: Builds SFM network, populates Chest A, assigns labels, sets program with ticks frozen."""
    print("------------------------------------------------------------")
    print("Closing any open GUI...")
    client.close_gui()

    # Clear build area
    print(f"Clearing build area [{MX-2}, {MY-1}, {MZ-2}] -> [{MX+5}, {MY+4}, {MZ+5}]...")
    client.clear_area(MX - 2, MY - 1, MZ - 2, MX + 5, MY + 4, MZ + 5)

    # Floor under machines
    for x in range(MX - 1, MX + 4):
        for z in range(MZ - 1, MZ + 2):
            client.setblock(x, MY - 1, z, "stone")

    # Place Manager
    print("Placing Manager...")
    client.setblock(MX, MY, MZ, "StevesFactoryManager:BlockMachineManagerName")

    # Place Cables
    print("Placing Cables...")
    client.setblock(MX + 1, MY, MZ, "StevesFactoryManager:BlockCableName")
    client.setblock(MX + 2, MY, MZ, "StevesFactoryManager:BlockCableName")

    # Place Chests
    print("Placing Chests...")
    client.setblock(CA_X, CA_Y, CA_Z, "chest")
    client.setblock(CB_X, CB_Y, CB_Z, "chest")

    time.sleep(0.5)

    # Put items into Chest A: slot 0 with 64 iron ingots
    print("Populating Chest A with 64 iron ingots...")
    client.set_container_slot(CA_X, CA_Y, CA_Z, 0, "minecraft:iron_ingot", 64)

    # Assign labels
    print("Assigning SFM labels...")
    client.sfm_label(MX, MY, MZ, "a", CA_X, CA_Y, CA_Z)
    client.sfm_label(MX, MY, MZ, "b", CB_X, CB_Y, CB_Z)

    # Set SFM program
    program = "EVERY 20 TICKS DO\n  INPUT FROM \"a\"\n  OUTPUT TO \"b\"\nEND\n"
    print("Setting SFM program...")
    client.sfm_program(MX, MY, MZ, program)

    print("Pre-test inventory:")
    print("  Chest A:", client.container_info(CA_X, CA_Y, CA_Z))
    print("  Chest B:", client.container_info(CB_X, CB_Y, CB_Z))
    print("[✓] Build Phase completed: SFM network wired, programmed, and staged.")


def test_sfm_logistics(client: HarnessClient):
    """Stage 2: Runs live game ticks, triggers SFM execution, verifies item transfer."""
    print("------------------------------------------------------------")
    print("Positioning player near SFM setup...")
    client.stand(PL_X, PL_Y, PL_Z)
    client.wait(5)

    # Wait for game ticks to allow SFM execution cycle
    print("Waiting 2.0s for SFM execution cycle...")
    time.sleep(2.0)

    # Trigger manual sfm_update pulse
    print("Triggering sfm_update...")
    client.sfm_update(MX, MY, MZ)
    time.sleep(1.0)

    # Check SFM logs
    print("--- SFM LOGS ---")
    logs = client.sfm_logs(MX, MY, MZ)
    print(logs)
    print("----------------")

    chest_a_info = client.container_info(CA_X, CA_Y, CA_Z)
    chest_b_info = client.container_info(CB_X, CB_Y, CB_Z)
    print("Post-test inventory:")
    print("  Chest A:", chest_a_info)
    print("  Chest B:", chest_b_info)

    # Assert Chest B has received the iron ingots
    assert "iron_ingot" in chest_b_info or "64" in chest_b_info, "Expected iron_ingot to transfer to Chest B"
    print("[✓] SFM item transfer verified successfully!")


def run_test(port: int = 25571):
    driver = InWorldTestDriver(
        tag="sfm_logistics",
        port=port,
        no_mobs=True,
        base_template="superflat_empty",
    )
    return driver.run(build_fn=build_sfm_logistics, test_fn=test_sfm_logistics)


if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser(description="SFM Logistics In-World Test")
    parser.add_argument("--port", type=int, default=25571, help="AgentHarness port (default: 25571)")
    args = parser.parse_args()
    success = run_test(port=args.port)
    sys.exit(0 if success else 1)
