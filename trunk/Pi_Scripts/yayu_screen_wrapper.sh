#!/bin/bash
# Wrapper for YAYU Launcher
# Start the launcher in a separate screen
#
# License: GPLv3
# Author: Jens He chkel

# Variables
PWD=`pwd`
LAUNCHER_CALL=$PWD"launcher.sh"
WORK_DIR="/home/pi/"

# Main
cd $WORK_DIR
screen -S yayu -d -m $LAUNCHER_CALL
echo $!
