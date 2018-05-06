#!/usr/bin/env bash
#eg. sh ./docker_deploy_ha_m.sh manager 8011 local

# load envionment variables
source /etc/profile

# current directory
cur_dir=$(cd "$(dirname "${0}")"; pwd)

module=$1
port=$2
profile=$3

env_file="${cur_dir}/${module}/admin/deploy/${profile}/docker-HA-${port}.env"
if [ -f "${env_file}" ]; then
    env_file="${env_file}"
else
    env_file="${cur_dir}/${module}/admin/deploy/${profile}/docker-HA.env"
    if [ -f "${env_file}" ]; then
        env_file="${env_file}"
    else
        env_file=""
    fi
fi

hosts_file="${cur_dir}/${module}/admin/deploy/${profile}/docker-HA-${port}.hosts"
if [ -f "${hosts_file}" ]; then
    hosts_file="${hosts_file}"
else
    hosts_file="${cur_dir}/${module}/admin/deploy/${profile}/docker-HA.hosts"
    if [ -f "${hosts_file}" ]; then
        hosts_file="${hosts_file}"
    else
        hosts_file=""
    fi
fi

if [ -f "${env_file}" ]; then
    echo "${env_file}"
    docker_deploy="sh ./docker_deploy_m.sh ${module} ${port} ${env_file} ${hosts_file}"
    eval "$docker_deploy"
else
    echo "[error]the file[${env_file}] was not existed!"
fi