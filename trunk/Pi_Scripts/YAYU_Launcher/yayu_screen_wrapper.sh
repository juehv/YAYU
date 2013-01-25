#!/bin/bash
# Wrapper for YAYU Launcher
# Start the launcher in a separate screen
#
# License: GPLv3
# Author: Jens Heuschkel

# Variables
PWD="/usr/bin/YAYU_Launcher"
LAUNCHER_CALL=$PWD"/launcher.sh"
WORK_DIR="/home/pi/"

# Main
cd $WORK_DIR
screen -S yayu -d -m $LAUNCHER_CALL
echo start yayu launcher