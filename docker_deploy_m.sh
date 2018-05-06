#!/usr/bin/env bash
#eg. sh ./docker_deploy_m.sh manager 8011

# load envionment variables
source /etc/profile
docker_hub_host="reg-sre.lecloud.com"
docker_hub_path="/test_image/"
log_base_path=/letv/logs/mas
module=$1
port=$2
env_file=$3
hosts_file=$4
app_base="letv-mas-${module}"
app="${app_base}-${port}"
tsurl="http://localhost:${port}/health"

# current directory
cur_dir=$(cd "$(dirname "${0}")"; pwd)

run_env=""
if [ -z "$env_file" ]; then
    run_env=""
else
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

docker_deploy="sh ./docker_deploy.sh --image=${docker_hub_host}${docker_hub_path}${app_base} --app=${app} --run_opts='-v /${log_base_path}/${app}:/letv/logs/mas/${module} -p ${port}:${port} -P --expose=${port} ${run_env} ${run_hosts} --restart=always' --port=${port} --turl='${tsurl}'"

echo "${docker_deploy}"
echo "docker deploy ..."
eval "$docker_deploy"