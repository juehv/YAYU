#!/bin/bash
# YAYU Launcher Installer
#
#
# Author: Jens Heuschkel
#

# Variables
PWD=`pwd`
TAR_NAME=$PWD"/yayu_launcher.tgz"
FILE_DIR=$PWD"/YAYU_Launcher"
INSTALL_DIR="/usr/lib/YAYU_Launcher"
LINK_SRC=$INSTALL_DIR"/yayu_screen_wrapper.sh"
LINK_RAW_NAME="yayu-start"
LINK_NAME="/usr/bin/"$LINK_RAW_NAME
CRONTAB="/etc/crontab"
CRONTMP=$PWD"/tmp"

# Calls
UNTAR="tar -xzf"
LN="ln -s"
MV="mv"
CHMOD="chmod -R 755"
RM="rm -rf"
FGREP="fgrep -i -v"

# Functions
function check_if_root () {
	if [[ $EUID -ne 0 ]]; then
		echo "This script must be run as root" 1>&2
		exit 1
	fi
}

function unzip_files () {
	if [ -e "$FILE_DIR" ]
	then 
		$RM $FILE_DIR
	fi

	$UNTAR $TAR_NAME
}

function copy_files () {
	if [ -e "$INSTALL_DIR" ]
        then
                $RM $INSTALL_DIR
        fi

	$MV $FILE_DIR $INSTALL_DIR
}

function create_links () {
	cd /usr/bin
	if [ -e "$LINK_NAME" ]
	then
		rm -f $LINK_NAME
	fi
	$LN $LINK_SRC $LINK_RAW_NAME
	cd $PWD
}

function change_rights () {
        $CHMOD $INSTALL_DIR
}

function create_cron_tab_entry () {
#	echo "* * * * * root $LINK_NAME" >> $CRONTAB
	command="$LINK_NAME"
	job="* * * * * root $command"
	$FGREP "$command" $CRONTAB > $CRONTMP
	echo "$job" >> $CRONTMP
	$RM $CRONTAB
	$MV $CRONTMP $CRONTAB
}

function cleanup () {
	$RM $FILE_DIR
}


# Main
check_if_root
unzip_files
copy_files
create_links
change_rights
create_cron_tab_entry
cleanup
