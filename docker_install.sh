#!/bin/bash
#***************************************************************************************************************************************
#This script will automately isntall the docker on centos6.
#Discription: The script file is edited by the file which written by lichao http://wiki.letv.cn/pages/viewpage.action?pageId=23795105
#Author:Simon
#Email:sunxiaoning@letv.com
#Date:2014.7.22
#***************************************************************************************************************************************
#This script will automately isntall the docker on centos6.
#ipv4 forward setting
sed -i '/net.ipv4.ip_forward/s/0/1/' /etc/sysctl.conf
sysctl -p
#iptables setting
sed -i 's/^install nf_conntrack/#\0/g' /etc/modprobe.d/nf_conntrack.conf
modprobe ip_tables
 
#disable puppet iptalbes setting
grep -q 'export FACTER_ENABLE_IPTABLES=YES' /etc/profile || sed -i '$ a export FACTER_ENABLE_IPTALBLES=YES' /etc/profile
. /etc/profile
#install docker-io on your host 
state=`yum list installed | grep docker-io`
if [ -z $state ]
then
   yum install docker-io -y;
   service docker start;
else
   yum reinstall docker-io -y;
   service docker start;
fi

if [ -f "/var/run/docker.sock" ]; then
   chmod a+rw /var/run/docker.sock
   sudo groupadd docker
   sudo gpasswd -a leworker docker
   newgrp - docker
   sudo service docker restart
fi
