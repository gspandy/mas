#!/usr/bin/env bash
#eg. sh ./docker_deploy_m.sh --module=manager --port=8011 --zone=[zone] --region=[region] --jmxport=[jmxport]

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
    --zone)
      zone=$value;;
    --region)
      region=$value;;
    --env_file)
      env_file=$value;;
    --hosts_file)
      hosts_file=$value;;
    --jmxport)
      jmxport=$value;;
    --help)
      echo "args:"
      echo "--module="
      echo "--port="
      echo "--zone="
      echo "--region="
      echo "--env_file="
      echo "--hosts_file="
      echo "--jmxport="
      echo "--help"
      exit 0;;
  esac
done

docker_hub_host="reg-sre.lecloud.com"
docker_hub_path="/test_image/"
app_base_path=/letv/app/mas
log_base_path=/letv/logs/mas
app_base="letv-mas-${module}"
app="${app_base}-${port}"
tsurl="http://localhost:${port}/info"

# current directory
cur_dir=$(cd "$(dirname "${0}")"; pwd)

run_env=""
if [ -z "$env_file" ]; then
    run_env=""
else
    if [ -f "${env_file}" ]; then
        host_ip=$(ifconfig eth0 | grep 'inet ' | sed s/^.*addr://g | sed s/Bcast.*$//g | sed 's/\s\+//g')
        host_ip_len=`echo "$host_ip" | awk '{print length($0)}'`
        if [ "$host_ip_len" -gt 15 ]; then
            host_ip=$(ifconfig eth0 | grep 'inet ' | awk '{print $2}')
        fi
        if [ -z "$host_ip" ]; then
            host_ip="127.0.0.1"
        fi
        sed -i 's/{$SERVER-IP}/'"${host_ip}"'/g' "${env_file}"
        sed -i 's/{$SERVER-PORT}/'"${port}"'/g' "${env_file}"
        if [ -n "$zone" ]; then
            sed -i 's/{$SERVER-ZONE}/'"${zone}"'/g' "${env_file}"
        fi
        if [ -n "$region" ]; then
            sed -i 's/{$SERVER-REGION}/'"${region}"'/g' "${env_file}"
        fi
        if [ -n "$jmxport" ]; then
            sed -i 's/{$JMX-PORT}/'"${jmxport}"'/g' "${env_file}"
        fi
    fi
    run_env="--env-file=${env_file}"
fi

run_hosts=""
if [ -z "$hosts_file" ]; then
    run_hosts=""
else
    hosts=$(cat $hosts_file | awk '{str=(str","$0)}END{print str}')
    hosts=$(echo $hosts | sed 's/[[:space:]]//g' | sed 's/,/ /g' | sed 's/[ \t]*$//g')
    if [ -z "$hosts" ]; then
        run_hosts=""
    else
        for host in $hosts; do
            run_hosts="$run_hosts --add-host $host"
        done
    fi
fi

run_jmx=""
if [ -n "$jmxport" ]; then
    run_jmx="-p ${jmxport}:${jmxport}"
fi

docker_deploy="sh ${cur_dir}/docker_deploy.sh --image=${docker_hub_host}${docker_hub_path}${app_base} --app=${app} --run_opts='-v ${app_base_path}/${app}:/letv/app/mas/${module} -v ${log_base_path}/${app}:/letv/logs/mas/${module} -p ${port}:${port} ${run_jmx} ${run_env} ${run_hosts} --restart=always' --port=${port} --turl='${tsurl}'"

echo "${docker_deploy}"
echo "docker deploy ..."
eval "$docker_deploy"