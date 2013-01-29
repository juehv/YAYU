#!/bin/bash
# 
# Creates an script with bundled data
#
# Author: Jens Heuschkel <jens@3und20.eu>
#

BANNER="packer v0.1"

PROG=`basename "$0"`
if [ $# -ne 3 -o "$1" == "--help" -o "$1" == "-h" ]; then
  echo $BANNER
  echo "Usage: ${PROG} NEWSCRIPT SCRIPT DATA_TAR"
  echo "       Creates a wrapper called NEWSCRIPT that wraps the"
  echo "       specified SCRIPT and appends the DATA_TAR"
  echo ""
  echo "   or: ${PROG} -h|--help"
  echo "       Prints this message."
  exit 0
fi

NEWSCRIPT=$1
SCRIPT=$2
DATA_TAR=$3

echo $BANNER
echo "Checking files.."

if [ ! -e ${SCRIPT} ]
then 
 echo "Script \"${SCRIPT}\" not found." >&2
 exit 1
fi 

if [ ! -e ${DATA_TAR} ]
then 
 echo "Data \"${DATA_TAR}\" not found." >&2
 exit 1
fi 

touch $NEWSCRIPT
if [ ! -e ${NEWSCRIPT} ]
then 
 echo "Can't write \"${NEWSCRIPT}\". Check rights." >&2
 exit 1
fi 

# Get size
TAR_SIZE=`stat -c %s ${DATA_TAR}`

#SPLIT_CMD="tail -c ${TAR_SIZE} \$0 | tar -xz ${DATA_TAR}"
SPLIT_CMD="tail -c ${TAR_SIZE} \$0 > ${DATA_TAR}"

# Write untar part
cat > ${NEWSCRIPT} <<EOF
#!/bin/bash
# Packed script created by ${BANNER}
#
# Untar the data part
#SIZE="${TAR_SIZE}"
$SPLIT_CMD

# The script goes here
EOF


# Write script
cat $SCRIPT >> $NEWSCRIPT

cat >> ${NEWSCRIPT} <<'EOF'
exit
# Data goes here
EOF
cat $DATA_TAR >> $NEWSCRIPT

chmod +x $NEWSCRIPT
echo "done"
exit 0
