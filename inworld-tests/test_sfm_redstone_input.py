#!/usr/bin/env python3
"""IF REDSTONE > 0 gating by a Steve's Factory Manager receiver on the manager's network.

A manager moves a chest's iron to another chest only while the receiver is powered: nothing moves until a redstone
block is placed next to the receiver, and then all 64 ingots do.  Runs in its own superflat world on the SFM test
runner:

    mc-fleet test --instance sfm-test-runner -- python3 inworld-tests/test_sfm_redstone_input.py
"""

import os
import sys
import time

import nhdev  # noqa: F401 (nh-dev tools on sys.path)
from tools.agentharness import HarnessClient
from tools.bot.world_manager import WorldSandbox
from tools.mcfleet.client import FleetClient
from tools.runner_instance import ensure_sfm_instance_running, get_saves_dir

HARNESS_PORT = int(os.environ.get("MCFLEET_HARNESS_PORT", "25572"))

# Manager, cable and receiver in a row along x.  The destination chest sits beside the cable: on top of the receiver
# it would not be on the manager's network, and next to the source chest the two would make one double chest.
MANAGER = (30, 6, 420)
CABLE = (31, 6, 420)
RECEIVER = (32, 6, 420)
POWER = (33, 6, 420)
SRC = (30, 7, 420)
DST = (31, 6, 421)


def iron(client, pos):
    info = client.container_info(*pos)
    return sum(s.get("count", 0) for s in info.get("slots", []) if s.get("item") == "minecraft:iron_ingot")


def build_and_check(client):
    mx, my, mz = MANAGER
    client.stand(28, 6, 418)
    client.clear_area(28, 6, 418, 36, 12, 425)
    for x in range(28, 36):
        for z in range(419, 423):
            client.set_block(x, 5, z, "stone")
    client.set_block(*MANAGER, "superfactorymanager:manager")
    client.set_block(*CABLE, "superfactorymanager:cable")
    client.set_block(*RECEIVER, "StevesFactoryManager:BlockCableInputName")
    client.set_block(*SRC, "chest")
    client.set_block(*DST, "chest")
    time.sleep(0.5)
    client.set_container_slot(*SRC, 0, "minecraft:iron_ingot", 64)

    for label, pos in (("src", SRC), ("dst", DST), ("rcv", RECEIVER)):
        print(f"Label {label}:", client.sfm_label(mx, my, mz, label, *pos))
    program = 'EVERY 20 TICKS DO\n  IF REDSTONE > 0 THEN\n    INPUT FROM "src"\n    OUTPUT TO "dst"\n  END\nEND\n'
    res = client.sfm_program(mx, my, mz, program, start=True)
    print("Program:", res)
    assert res["valid"], f"Program failed to compile: {res}"

    time.sleep(1.5)
    unpowered = iron(client, DST)
    print("Unpowered, dst iron:", unpowered)
    assert unpowered == 0, f"Items moved with the receiver unpowered: {unpowered}"

    client.set_block(*POWER, "redstone_block")
    time.sleep(1.5)
    powered = iron(client, DST)
    print("Powered, dst iron:", powered)
    if powered != 64:
        print(client.sfm_logs(mx, my, mz))
    assert powered == 64, f"Expected 64 iron in dst once powered, got {powered}"
    print("✓ SFM REDSTONE INPUT TEST PASSED")


def main():
    ensure_sfm_instance_running()
    client = HarnessClient(port=HARNESS_PORT)
    saves = get_saves_dir(FleetClient(), "sfm-test-runner")
    saves.mkdir(parents=True, exist_ok=True)
    world = "sfm_redstone_input"
    with WorldSandbox("superflat_empty", world_name=world, saves_dir=saves, on_unmount=lambda: client.exitworld()):
        client.autoworld(name=world)
        start = time.time()
        while client.state() != "IN_WORLD":
            if time.time() - start > 120:
                raise RuntimeError(f"Failed to enter world: {client.status()}")
            time.sleep(1.0)
        time.sleep(1.5)
        client.close_gui()
        client.chat(message="/gamemode 1")
        build_and_check(client)


if __name__ == "__main__":
    try:
        main()
    except AssertionError as e:
        print(f"FAILED: {e}")
        sys.exit(1)
