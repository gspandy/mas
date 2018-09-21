#!/usr/bin/env bash
#eg. sh ./docker_deploy_ha_m.sh --module=manager --port=8011 --profile=local --zone=[zone] --region=[region] --jmxport=[jmxport]

# load envionment variables
source /etc/profile

# initialize parameters
for arg in "$@"; do
  param=${arg%%=*}
  value=${arg#*=}
  case $param in
    --module)
      module=$value;;
    --port)
      port=$value;;
    --profile)
      profile=$value;;
    --zone)
      zone=$value;;
    --region)
      region=$value;;
    --jmxport)
      jmxport=$value;;
    --project_net)
      project_net=$value;;
    --help)
      echo "args:"
      echo "--module="
      echo "--port="
      echo "--profile="
      echo "--zone="
      echo "--region="
      echo "--jmxport="
      echo "--help"
      exit 0;;
  esac
done

# current directory
cur_dir=$(cd "$(dirname "${0}")"; pwd)

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
    docker_deploy="sh ${cur_dir}/docker_deploy_m.sh --module=${module} --port=${port} --zone=${zone} --region=${region} --env_file=${env_file} --hosts_file=${hosts_file} --jmxport=${jmxport} --project_net=${project_net}"
    eval "$docker_deploy"
else
    echo "[error]the file[${env_file}] was not existed!"
fi