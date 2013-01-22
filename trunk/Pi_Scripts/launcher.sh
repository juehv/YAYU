#!/bin/bash
# YAYU Launcher for YAYU Services
# Launches the bundeled Java Server
#
# License: GPLv3
#
# Author: Jens Heuschkel
#

# Dirs
PWD="/home/pi"
SYS_DIR=$PWD"/yayu_sys"
USR_DIR=$PWD"/auto/yayu"
USR_LOGFILE=$USR_DIR"/yayu.log"
SYS_LOGFILE=$SYS_DIR"/lastrun_yayu.log"

# Actions
ACTION_UPLOAD=$USR_DIR"/actionUpload"
ACTION_UPDATE=$USR_DIR"/actionUpdate"

# Run Files
RUN_LOCK=$SYS_DIR"/lock"
RUN_UPLOAD=$SYS_DIR"/runningUpload"
RUN_UPDATE=$SYS_DIR"/runningUpdate"

#Calls
TEE="tee -a -i"
RM="rm -rf"
MKDIR="mkdir"
SERVER_CALL=$USR_DIR"/yayu-server"

# Functions
function start_server (){
	# "cd" because of java working dir
	cd $USR_DIR
	$SERVER_CALL 2>&1 | $TEE $USR_LOGFILE | $TEE $SYS_LOGFILE
}

function clean_and_exit (){
	$RM $RUN_UPLOAD
	$RM $RUN_UPDATE
	$RM $RUN_LOCK
	exit
}

function exit_without_clean (){
	exit
}

function setup_env (){
	if [ ! -e "$SYS_DIR" ]
	then
        	$MKDIR $SYS_DIR
	fi
	if [ -e "$RUN_LOCK" ]
	then
		echo "Launcher is running. exit." | $TEE $SYS_LOGFILE
		exit_without_clean
	else
		touch $RUN_LOCK
	fi
	echo "#################################" | $TEE $SYS_LOGFILE
	echo "### START YAYU LAUNCHER #########" | $TEE $SYS_LOGFILE
	echo "### `date +"%d.%m.%y %T"` ###########" | $TEE $SYS_LOGFILE
	echo "#################################" | $TEE $SYS_LOGFILE
}


# "Main"
setup_env

if [ -d "$USR_DIR" ]
then
	echo "dir exists, check action" | $TEE $SYS_LOGFILE
	
	if [ -e "$ACTION_UPLOAD" ]
	then
		echo "found upload action, start server." | $TEE $SYS_LOGFILE
		if [ -e "$RUN_UPLOAD" ]
		then
			echo "the server is already running. exit." | $TEE $SYS_LOGFILE
			exit_without_clean
		else
			touch $RUN_UPLOAD
			start_server
			$RM $RUN_UPLOAD
			echo "upload done. exit." | $TEE $SYS_LOGFILE
			$RM $ACTION_UPLOAD
			clean_and_exit
		fi
	elif [ -e "$ACTION_UPDATE" ]
	then
		echo "found update action, start updater." | $TEE $SYS_LOGFILE
		echo "not supported" | $TEE $SYS_LOGFILE
		# $RM $ACTION_UPDATE
	else
		echo "no suitable action found." | $TEE $SYS_LOGFILE
		clean_and_exit
	fi
else 
	echo "dir is not mounted. exit." | $TEE $SYS_LOGFILE
fi

clean_and_exit
