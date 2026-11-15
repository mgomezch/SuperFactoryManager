#!/usr/bin/env python3
import sys
import time

import nhdev  # noqa: F401 (nh-dev tools on sys.path)
from tools.agentharness import HarnessClient

def run_test():
    client = HarnessClient(port=25572)
    print("Closing any GUI:", client.close_gui())
    print("Status:", client.status())

    # Build area at X=28..36, Y=5..10, Z=418..425
    print("Clearing redstone input test area...")
    client.clear_area(28, 6, 418, 36, 12, 425)

    for x in range(28, 36):
        for z in range(419, 422):
            client.setblock(x=x, y=5, z=z, block="stone")

    # Place Manager at 30, 6, 420
    print("Manager:", client.setblock(x=30, y=6, z=420, block="superfactorymanager:manager"))

    # Place Cable at 31, 6, 420
    print("Cable:", client.setblock(x=31, y=6, z=420, block="superfactorymanager:cable"))

    # Place Receiver at 32, 6, 420
    print("Receiver:", client.setblock(x=32, y=6, z=420, block="StevesFactoryManager:BlockCableInputName"))

    # Place Chests
    print("Chest src:", client.setblock(x=30, y=7, z=420, block="chest"))
    print("Chest dst:", client.setblock(x=31, y=7, z=420, block="chest"))

    time.sleep(0.5)

    # Populate Chest src
    client.set_container_slot(x=30, y=7, z=420, slot=0, item="minecraft:iron_ingot", count=64)
    print("Chest src info:", client.container_info(x=30, y=7, z=420))
    print("Chest dst info:", client.container_info(x=31, y=7, z=420))

    # Wait for network scan
    time.sleep(1.5)

    # Labels
    print("Label src:", client.sfm_label(x=30, y=6, z=420, label="src", bx=30, by=7, bz=420))
    print("Label dst:", client.sfm_label(x=30, y=6, z=420, label="dst", bx=31, by=7, bz=420))
    print("Label rcv:", client.sfm_label(x=30, y=6, z=420, label="rcv", bx=32, by=6, bz=420))

    # Program: Only transfer if redstone > 0
    program = "EVERY 20 TICKS DO\n  IF REDSTONE > 0 THEN\n    INPUT FROM \"src\"\n    OUTPUT TO \"dst\"\n  END\nEND\n"
    print("Setting program conditional on REDSTONE > 0...")
    print(client.sfm_program(x=30, y=6, z=420, code=program))

    # Wait 1.5s - since no redstone power, items should NOT move
    time.sleep(1.5)
    print("Chest dst (expect empty):", client.container_info(x=31, y=7, z=420))

    # Now apply redstone to the receiver at (32, 6, 420) by placing a redstone block next to it at (33, 6, 420)
    print("Powering receiver with redstone_block at (33, 6, 420)...")
    client.setblock(x=33, y=6, z=420, block="redstone_block")
    time.sleep(0.2)
    print("Receiver power reading:", client.redstone_power(x=32, y=6, z=420))

    # Wait 1.5s for next trigger cycle
    time.sleep(1.5)

    print("--- SFM LOGS ---")
    print(client.sfm_logs(x=30, y=6, z=420))
    print("----------------")

    print("Chest src (expect empty or reduced):", client.container_info(x=30, y=7, z=420))
    print("Chest dst (expect 64 iron ingots):", client.container_info(x=31, y=7, z=420))

if __name__ == "__main__":
    run_test()
