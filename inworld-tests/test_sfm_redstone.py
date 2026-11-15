#!/usr/bin/env python3
"""
Automated SFM Redstone Test Script for AgentHarness & InWorldTestDriver
Tests StevesFactoryManager redstone output emission:
  Stage 1 (Build Phase): Sets up Manager, Cables, Redstone Emitter, labels emitter,
                         and configures network while ticks are frozen.
                         Saves snapshot to 'sfm_redstone_pre'.
  Stage 2 (Execution Phase): Ephemeral run from 'sfm_redstone_pre' with ticks unfrozen.
                             Emits Redstone 15, asserts power level 15.
                             Emits Redstone 0, asserts power level 0.
                             Saves snapshot to 'sfm_redstone_post'.
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
MX, MY, MZ = 520, 6, 40
CABLE_X, CABLE_Y, CABLE_Z = 521, 6, 40
EMIT_X, EMIT_Y, EMIT_Z = 522, 6, 40
BLOCK_X, BLOCK_Y, BLOCK_Z = 522, 7, 40
PL_X, PL_Y, PL_Z = 520, 6, 38

def build_sfm_redstone(client: HarnessClient):
    """Stage 1: Builds redstone test circuit with ticks frozen."""
    print("------------------------------------------------------------")
    print("Closing any open GUI...")
    client.close_gui()

    # Clear build area
    print(f"Clearing redstone test area [{MX-2}, {MY-1}, {MZ-2}] -> [{MX+6}, {MY+4}, {MZ+5}]...")
    client.clear_area(MX - 2, MY - 1, MZ - 2, MX + 6, MY + 4, MZ + 5)

    # Floor under blocks
    for x in range(MX - 1, MX + 5):
        for z in range(MZ - 1, MZ + 2):
            client.setblock(x, MY - 1, z, "stone")

    # Place Manager
    print("Placing Manager...")
    client.setblock(MX, MY, MZ, "StevesFactoryManager:BlockMachineManagerName")

    # Place Cable
    print("Placing Cable...")
    client.setblock(CABLE_X, CABLE_Y, CABLE_Z, "StevesFactoryManager:BlockCableName")

    # Place Emitter
    print("Placing Redstone Emitter...")
    client.setblock(EMIT_X, EMIT_Y, EMIT_Z, "StevesFactoryManager:BlockCableOutputName")

    # Place stone block above emitter
    print("Placing test block above emitter...")
    client.setblock(BLOCK_X, BLOCK_Y, BLOCK_Z, "stone")

    time.sleep(0.5)

    # Label emitter
    print("Assigning SFM label to emitter...")
    client.sfm_label(MX, MY, MZ, "emit", EMIT_X, EMIT_Y, EMIT_Z)

    # Initial redstone check (should be 0)
    init_rs = client.get_redstone(BLOCK_X, BLOCK_Y, BLOCK_Z)
    print(f"Initial redstone power at ({BLOCK_X}, {BLOCK_Y}, {BLOCK_Z}): {init_rs}")
    print("[✓] Build Phase completed: SFM redstone circuit assembled and labeled.")


def test_sfm_redstone(client: HarnessClient):
    """Stage 2: Tests redstone output modulation (ON -> 15, OFF -> 0)."""
    print("------------------------------------------------------------")
    print("Positioning player near redstone emitter...")
    client.stand(PL_X, PL_Y, PL_Z)
    client.wait(5)

    # 1. Program to emit 15
    program_on = "EVERY 20 TICKS DO\n  OUTPUT 15 REDSTONE LEVEL TO \"emit\"\nEND\n"
    print("Setting SFM program: OUTPUT 15 REDSTONE LEVEL...")
    client.sfm_program(MX, MY, MZ, program_on)

    time.sleep(1.2)
    client.sfm_update(MX, MY, MZ)
    time.sleep(0.5)

    print("--- SFM LOGS (ON) ---")
    print(client.sfm_logs(MX, MY, MZ))
    print("---------------------")

    rs_above = client.get_redstone(BLOCK_X, BLOCK_Y, BLOCK_Z)
    rs_direct = client.get_redstone(EMIT_X, EMIT_Y, EMIT_Z)
    print(f"Redstone after ON: above={rs_above}, direct={rs_direct}")
    assert "15" in rs_above or "15" in rs_direct, f"Expected redstone power 15, got above={rs_above}, direct={rs_direct}"
    print("[✓] Redstone ON (level 15) verified!")

    # 2. Program to emit 0
    program_off = "EVERY 20 TICKS DO\n  OUTPUT 0 REDSTONE LEVEL TO \"emit\"\nEND\n"
    print("Setting SFM program: OUTPUT 0 REDSTONE LEVEL...")
    client.sfm_program(MX, MY, MZ, program_off)

    time.sleep(1.2)
    client.sfm_update(MX, MY, MZ)
    time.sleep(0.5)

    print("--- SFM LOGS (OFF) ---")
    print(client.sfm_logs(MX, MY, MZ))
    print("----------------------")

    rs_after_off = client.get_redstone(BLOCK_X, BLOCK_Y, BLOCK_Z)
    print(f"Redstone after OFF at ({BLOCK_X}, {BLOCK_Y}, {BLOCK_Z}): {rs_after_off}")
    assert "powerInput=0" in rs_after_off or " 0" in rs_after_off or rs_after_off.strip() == "0", f"Expected redstone power 0, got {rs_after_off}"
    print("[✓] Redstone OFF (level 0) verified!")


def run_test(port: int = 25571):
    driver = InWorldTestDriver(
        tag="sfm_redstone",
        port=port,
        no_mobs=True,
        base_template="superflat_empty",
    )
    return driver.run(build_fn=build_sfm_redstone, test_fn=test_sfm_redstone)


if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser(description="SFM Redstone In-World Test")
    parser.add_argument("--port", type=int, default=25571, help="AgentHarness port (default: 25571)")
    args = parser.parse_args()
    success = run_test(port=args.port)
    sys.exit(0 if success else 1)
