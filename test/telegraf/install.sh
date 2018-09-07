#!/bin/bash
#***************************************************************************************************************************************
#This script will automately isntall the telegraf on centos6.
#Discription:
#how to publish it: scp -r ./monitor/telegraf root@10.58.89.189:/letv/web/apk.archive.itv.letv.com
#how to run it: wget -c -nH -np --reject=html http://10.58.89.189:10000/telegraf/;sudo chmod +x telegraf/install.sh;sudo telegraf/install.sh --profile=prod
#Author:dengliwei
#Email:dengliwei@le.com
#Date:2018.09.03
#***************************************************************************************************************************************
cur_dir=$(cd "$(dirname "${0}")"; pwd)

# initialize parameters
for arg in "$@"; do
  param=${arg%%=*}
  value=${arg#*=}
  case $param in
    --profile)
      profile=$value;;
    --help)
      echo "args:"
      echo "--profile="
      echo "--help"
      exit 0;;
  esac
done

if [ -z "$profile" ]; then
  profile="test"
fi

binFile="$cur_dir/bin/telegraf-1.5.0-1.x86_64.rpm"
confFile_src="$cur_dir/conf/$profile/telegraf.conf"
confFile_des="/etc/telegraf/telegraf.conf"

#install telegraf on your host
state=`yum list installed | grep telegraf`
if [ -z "$state" ]; then
   yum -y localinstall $binFile;
   sudo groupadd docker
   sudo usermod -aG docker telegraf
fi

cp -pf $confFile_des "$confFile_des.$(date '+%y%m%d%H%M%S')"
cp -f $confFile_src $confFile_des

#run telegraf on your host
state=`ps -ef|awk '{if ($8 == "/usr/bin/telegraf")print $2}'`
if [ -z "$state" ]; then
    service telegraf start
else
    service telegraf restart
fi