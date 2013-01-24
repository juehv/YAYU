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
MNTDIR=$PWD"/auto"
USBDIR_NAME="yayu"
AUTOFS_CONF="/etc/auto.yayu"

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

function removeX () {
	apt-get --yes purge xserver* x11-common x11-utils x11-xkb-utils x11-xserver-utils xarchiver xauth xkb-data console-setup xinit lightdm libx{composite,cb,cursor,damage,dmcp,ext,font,ft,i,inerama,kbfile,klavier,mu,pm,randr,render,res,t,xf86}* lxde* lx{input,menu-data,panel,polkit,randr,session,session-edit,shortcut,task,terminal} obconf openbox gtk* libgtk* alsa* python-pygame python-tk python3-tk scratch tsconf xdg-tools desktop-file-utils python3-numpy python3 python omxplayer
	apt-get --yes autoremove
	apt-get --yes autoclean
	apt-get --yes clean
}

function install_tools(){
	apt-get update
	apt-get -y install vim htop openjdk-7-jre-headless screen autofs ntfs-3g exfat-fuse coreutils
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

function create_autofs_config (){
	mkdir $MNTDIR
	# wie bei crontab lösen
	echo $MNTDIR" "$AUTOFS_CONF" --timeout=1 --ghost" >> /etc/auto.master
	echo "yayu -fstype=auto,sync :/dev/disk/by-label/yayu_drive" > $AUTOFS_CONF
}

function cleanup () {
	$RM $FILE_DIR
	reboot
}


# Main
check_if_root
removeX
install_tools
unzip_files
copy_files
create_links
change_rights
create_cron_tab_entry
cleanup
create_autofs_config
