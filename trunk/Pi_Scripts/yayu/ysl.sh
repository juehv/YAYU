#!/bin/bash
# YSL (YAYU Service Launcher)
# YAYU Launcher for YAYU Services
# Launches the bundeled Java Server
#
# License: GPLv3
#
# Author: Jens Heuschkel <jens@3und20.eu>
#

# Variables
PWD="/opt/yayu"
DISK_LABEL="yayu_drive"
DISK_DEV="/dev/disk/by-label/${DISK_LABEL}"
DISK_DIR="/opt/yayu/mnt"
LOG_DIR="/var/log"
LOG_FILE="${LOG_DIR}/yayu.log"
LOCK_DIR="/var/lock"
LOCK_FILE="${LOCK_DIR}/yayu.lock"

# Actions
ACTION_UPLOAD="${DISK_DIR}/upload"
ACTION_UPDATE="${DISK_DIR}/update"

# Calls
CMD_UPLOAD="${ACTION_UPLOAD}/YAYU_Uploader.jar"
CMD_UPDATE="${ACTION_UPDATE}/YAYU_Updater.jar"
SHUTDOWN="${PWD}/shutdown.sh"
JAVA="/usr/bin/java -jar"

# Funktions
function unlock_exit (){
	rm -f $LOCK_FILE
	exit 0
}

function umount_exit (){
	umount $DISK_DEV
	unlock_exit
}

function exit_shutdown (){
	umount $DISK_DEV
	rm -f $LOCK_FILE
	

function check_LOCK (){
 if [ -e "${LOCK_FILE}" ]
 then
  echo "$( date +%Y/%m/%d-%H:%M:%S ) found lock at ${LOCK_FILE}. exit." | tee -a -i $LOG_FILE
  exit 0
 else
  echo "$( date +%Y/%m/%d-%H:%M:%S ) create lock at ${LOCK_FILE}." | tee -a -i $LOG_FILE
 fi
}

function check_DISK (){
 if [ -e "${DISK_DEV}" ]
 then
  mount $DISK_DEV $DISK_DIR
  echo "$( date +%Y/%m/%d-%H:%M:%S ) disk found and mounted to ${DISK_DIR}." | tee -a -i $LOG_FILE
 else
  echo "$( date +%Y/%m/%d-%H:%M:%S ) no disk found. unlock and exit." | tee -a -i $LOG_FILE
  unlock_exit
 fi
}

function check_ACTIONS (){
 if [ -d "${ACTION_UPLOAD}" ]
 then
  echo "$( date +%Y/%m/%d-%H:%M:%S ) found upload action. start uploader." | tee -a -i $LOG_FILE
  if [ -e $CMD_UPLOAD ]
  then
   $JAVA $CMD_UPLOAD > $LOG_FILE 2>&1
   # TODO check if error and do not delete if there was an error
   echo "$( date +%Y/%m/%d-%H:%M:%S ) uploader exit with code \"${?}\". delete action" | tee -a -i $LOG_FILE
   rm -rf $ACTION_UPLOAD
   echo "$( date +%Y/%m/%d-%H:%M:%S ) copy log, unlock and shutdown." | tee -a -i $LOG_FILE
   cp $LOG_FILE $DISK_DIR
   mv $LOG_FILE "${LOG_FILE}.sav.$( date +%Y/%m/%d-%H:%M:%S )"
   exit_shutdown
  else
   echo "$( date +%Y/%m/%d-%H:%M:%S ) uploader not found. unlock and exit" | tee -a -i $LOG_FILE
  fi
 elif [ -d "${ACTION_UPDATE}" ]
 then
  echo "$( date +%Y/%m/%d-%H:%M:%S ) found upload action. start uploader." | tee -a -i $LOG_FILE
  if [ -e $CMD_UPDATE ]
  then
   $CMD_UPDATE > $LOG_FILE 2>&1
   echo "$( date +%Y/%m/%d-%H:%M:%S ) updater exit with code \"${?}\". delete action" | tee -a -i $LOG_FILE
   rm -rf $ACTION_UPLOAD
   echo "$( date +%Y/%m/%d-%H:%M:%S ) copy log, unlock and exit." | tee -a -i $LOG_FILE
   cp $LOG_FILE $DISK_DIR
   mv $LOG_FILE "${LOG_FILE}.sav.$( date +%Y/%m/%d-%H:%M:%S )"
  else
   echo "$( date +%Y/%m/%d-%H:%M:%S ) updater not found. unlock and exit" | tee -a -i $LOG_FILE
  fi
 else
  echo "$( date +%Y/%m/%d-%H:%M:%S ) no action found. unlock and exit." | tee -a -i $LOG_FILE
 fi
 umount_exit
}

function check_ROOT () {
 if [ $(id -u) -ne 0 ] 
 then
  echo "This script must be run as root" 1>&2 | tee -a -i $LOG_FILE
  exit 1
 fi
}

# MAIN
check_ROOT
check_LOCK
check_DISK
check_ACTIONS
echo "This should not happen!" 1>&2 | tee -a -i $LOG_FILE
exit 1