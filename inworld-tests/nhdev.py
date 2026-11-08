"""Puts the nh-dev workspace on sys.path for its tools package: the AgentHarness and SFM clients, the in-world test
driver, world sandboxes and mc-fleet, which the tests here run on.

The workspace is $NH_DEV_ROOT, or else the one this repository is checked out in, as nh-dev/mods/<repository>.
"""

import os
import sys
from pathlib import Path

NH_DEV_ROOT = Path(os.environ.get("NH_DEV_ROOT") or Path(__file__).resolve().parents[3])

if not (NH_DEV_ROOT / "tools" / "inworld_test_driver.py").is_file():
    raise ImportError(f"No nh-dev workspace at {NH_DEV_ROOT}: set NH_DEV_ROOT")
if str(NH_DEV_ROOT) not in sys.path:
    sys.path.insert(0, str(NH_DEV_ROOT))
