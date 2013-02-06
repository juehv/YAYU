#!/bin/bash
# Exits the YAYU-Servers and shuts the Pi down
#
# License: GPLv3
#
# Author: Jens Heuschkel
#

function check_ROOT () {
 if [ $(id -u) -ne 0 ] 
 then
  echo "This script must be run as root" 1>&2 | tee -a -i $LOG_FILE
  exit 1
 fi
}

check_ROOT
# TODO wait for apps with lock
/etc/init.d/pims stop
halt
exit 0