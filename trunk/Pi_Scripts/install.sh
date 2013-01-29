#!/bin/bash
# YAYU Installer
#
# License: GPLv3
#
# Author: Jens Heuschkel <jens@3und20.eu>
#

# Variables
PWD=`pwd`
TAR_NAME=$PWD"/yayu.tar"
UNPACK_TAR_NAME=$PWD"/yayu"
INSTALL_DIR="/opt/yayu"
MNT_DIR="${INSTALL_DIR}/mnt"

LINK_PiMS="${INSTALL_DIR}/pims"
LINK_LAUNCHER="${INSTALL_DIR}/ysl.sh"
LINK_JAR2SH="${INSTALL_DIR}/jar2sh"

LINK_NAME_PiMS="pims"
LINK_NAME_LAUNCHER="ysl.sh"
LINK_NAME_JAR2SH="jar2sh"

LINK_TRGT_PiMS="/etc/init.d/${LINK_NAME_PiMS}"
LINK_TRGT_LAUNCHER="/usr/bin/${LINK_NAME_LAUNCHER}"
LINK_TRGT_JAR2SH="/usr/bin/${LINK_NAME_JAR2SH}"

CRONTAB="/etc/crontab"
CRONTMP="/tmp/crontmp"

LOG_DIR="/var/log"
LOG_FILE="${LOG_DIR}/yayu_installer.log"

PACKAGES_STRIP="x11-common x11-utils x11-xkb-utils x11-xserver-utils xarchiver xauth xkb-data console-setup xinit lightdm lxde* obconf openbox gtk* libgtk* alsa* python-pygame python-tk python3-tk scratch tsconf xdg-utils desktop-file-utils python3-numpy python3 python omxplayer gnome-accessibility-themes gnome-themes-standard-data libqtdbus4 libqt4-xml libqtcore4"
PACKAGES_INSTALL="vim htop openjdk-7-jre-headless ntfs-3g exfat-fuse coreutils cpufrequtils"

function check_ROOT (){
 if [ $(id -u) -ne 0 ] 
 then
  echo "$( date +%Y/%m/%d-%H:%M:%S ) This script must be run as root" 1>&2 | tee -a -i $LOG_FILE
  exit 1
 fi
}

function strip_ROM (){
 echo "$( date +%Y/%m/%d-%H:%M:%S ) try to strip rom." | tee -a -i $LOG_FILE
 apt-get -y purge $PACKAGES_STRIP | tee -a -i $LOG_FILE
 #REGEX doesn't work sometimes ...
 apt-get -y purge libx{composite,cb,cursor,damage,dmcp,ext,font,ft,i,inerama,kbfile,klavier,mu,pm,randr,render,res,t,xf86}* | tee -a -i $LOG_FILE
 apt-get -y purge lx{input,menu-data,panel,polkit,randr,session,session-edit,shortcut,task,terminal}* | tee -a -i $LOG_FILE
 apt-get -y purge $(dpkg --get-selections | grep "\-dev" | sed s/install//) | tee -a -i $LOG_FILE
 apt-get -y autoremove | tee -a -i $LOG_FILE
 apt-get -y autoclean | tee -a -i $LOG_FILE
 apt-get -y clean | tee -a -i $LOG_FILE
 apt-get -f -y install
 echo "$( date +%Y/%m/%d-%H:%M:%S ) disable swap." | tee -a -i $LOG_FILE
 swapoff -a
 dd if=/dev/zero of=/var/swap bs=1M count=100
 echo "$( date +%Y/%m/%d-%H:%M:%S ) delete home files." | tee -a -i $LOG_FILE
 rm -rf Desktop | tee -a -i $LOG_FILE
 rm -rf python_games | tee -a -i $LOG_FILE
 echo "$( date +%Y/%m/%d-%H:%M:%S ) strip done." | tee -a -i $LOG_FILE
}

function install_TOOLS (){
 echo "$( date +%Y/%m/%d-%H:%M:%S ) try to install needed tools." | tee -a -i $LOG_FILE
 apt-get update | tee -a -i $LOG_FILE
 apt-get -y install $PACKAGES_INSTALL | tee -a -i $LOG_FILE
 if [ "$?" -ne "0" ]
 then
  echo "$( date +%Y/%m/%d-%H:%M:%S ) error while tool installation. exit." 1>&2 | tee -a -i $LOG_FILE
  exit 1
 else
  echo "$( date +%Y/%m/%d-%H:%M:%S ) tool installation done." | tee -a -i $LOG_FILE
 fi
}

function unzip_TAR (){
 echo "$( date +%Y/%m/%d-%H:%M:%S ) untar data." | tee -a -i $LOG_FILE
 if [ -e "${UNPACK_TAR_NAME}" ]
 then
  rm -rf $UNPACK_TAR_NAME | tee -a -i $LOG_FILE
 fi
 
 if [ -e "${TAR_NAME}" ]
 then
  tar -xf $TAR_NAME | tee -a -i $LOG_FILE
 else
  echo "$( date +%Y/%m/%d-%H:%M:%S ) no tar file found. exit." 1>&2 | tee -a -i $LOG_FILE
  exit 1
 fi
}

function copy_FILES (){
 echo "$( date +%Y/%m/%d-%H:%M:%S ) copy files." | tee -a -i $LOG_FILE
 if [ -e "$INSTALL_DIR" ]
 then
  rm -rf $INSTALL_DIR | tee -a -i $LOG_FILE
 fi

 mv $UNPACK_TAR_NAME $INSTALL_DIR | tee -a -i $LOG_FILE
 mkdir $MNT_DIR | tee -a -i $LOG_FILE
}

function create_LINKS (){
 echo "$( date +%Y/%m/%d-%H:%M:%S ) create links." | tee -a -i $LOG_FILE
 if [ -e "${LINK_TRGT_PiMS}" ]
 then
  rm -rf $LINK_TRGT_PiMS | tee -a -i $LOG_FILE
 fi
 if [ -e "${LINK_TRGT_LAUNCHER}" ]
 then
  rm -rf $LINK_TRGT_LAUNCHER | tee -a -i $LOG_FILE
 fi
 if [ -e "${LINK_TRGT_JAR2SH}" ]
 then
  rm -rf $LINK_TRGT_JAR2SH | tee -a -i $LOG_FILE
 fi
 
 ln -s -t $(dirname "${LINK_TRGT_PiMS}") $LINK_PiMS $LINK_NAME_PiMS | tee -a -i $LOG_FILE
 ln -s -t $(dirname "${LINK_TRGT_LAUNCHER}") $LINK_LAUNCHER $LINK_NAME_LAUNCHER | tee -a -i $LOG_FILE
 ln -s -t $(dirname "${LINK_TRGT_JAR2SH}") $LINK_JAR2SH $LINK_NAME_JAR2SH | tee -a -i $LOG_FILE
}

function change_RIGHTS (){
 echo "$( date +%Y/%m/%d-%H:%M:%S ) change rights." | tee -a -i $LOG_FILE
 chmod -R 755 $INSTALL_DIR | tee -a -i $LOG_FILE
}

function update_RC (){
 echo "$( date +%Y/%m/%d-%H:%M:%S ) update rc for launching PiMS on startup." | tee -a -i $LOG_FILE
 update-rc.d $LINK_NAME_PiMS defaults | tee -a -i $LOG_FILE
}

function setup_CRONTAB (){
 echo "$( date +%Y/%m/%d-%H:%M:%S ) setup cron tab." | tee -a -i $LOG_FILE
#	echo "* * * * * root $LINK_NAME" >> $CRONTAB
 echo "$( date +%Y/%m/%d-%H:%M:%S ) old crontab was:" | tee -a -i $LOG_FILE
 cat $CRONTAB | tee -a -i $LOG_FILE
 echo "" | tee -a -i $LOG_FILE
 fgrep -i -v "${LINK_TRGT_LAUNCHER}" $CRONTAB > $CRONTMP
 echo "* * * * * root ${LINK_TRGT_LAUNCHER}" >> $CRONTMP
 rm -f $CRONTAB
 mv $CRONTMP $CRONTAB
 echo "$( date +%Y/%m/%d-%H:%M:%S ) new crontab is:" | tee -a -i $LOG_FILE
 cat $CRONTAB | tee -a -i $LOG_FILE
 echo "" | tee -a -i $LOG_FILE
}


# Main
check_ROOT
echo "This will take some time. Go and take a coffee."
echo ""
cat <<'EOF'
         {
      {   }
       }_{ __{
    .-{   }   }-.
   (   }     {   )
   |`-.._____..-'|
   |             ;--.
   |            (__  \
   |             | )  )
   |             |/  /
   |             /  /    -Felix Lee-
   |            (  /
   \             y'
    `-.._____..-'"
EOF
echo ""
strip_ROM
install_TOOLS
unzip_TAR
copy_FILES
create_LINKS
change_RIGHTS
update_RC
setup_CRONTAB
echo "you can find the whole logfile at ${LOG_FILE}" 
echo "installation done. please reboot." | tee -a -i $LOG_FILE
