#CPU
LOAD=$(ps -eo pcpu | awk ' {cpu_load+=$1} END {print cpu_load}')
FREQ=$(cpufreq-info | cut -c 30-60 | tail -n 1 | tr -d [:blank:])
#MEM
TOTAL=$(cat /proc/meminfo | cut -c 15-25 | head -n 1)
FREE=$(cat /proc/meminfo | cut -c 15-25 | head -n 2 | tail -n 1)
echo "${LOAD}:${FREQ%MHz.}:$(((TOALT-FREE)/1024)):$((TOTAL/1024))"
exit 0