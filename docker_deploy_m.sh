#!/usr/bin/env bash
#eg. sh ./docker_deploy_m.sh manager 8011

# load envionment variables
source /etc/profile
docker_hub_host="reg-sre.lecloud.com"
docker_hub_path="/test_image/"
log_base_path=/letv/logs/mas
module=$1
port=$2
app_base="letv-mas-${module}"
app="${app_base}-${port}"
tsurl="http://localhost:${port}/"

# current directory
cur_dir=$(cd "$(dirname "${0}")"; pwd)

docker_deploy="sh ./docker_deploy.sh --image=${docker_hub_host}${docker_hub_path}${app_base} --app=${app} --run_opts='-v /${log_base_path}/${app}:/letv/logs/mas/${module} -p ${port}:8010 --restart=always' --port=${port} --turl='${tsurl}'"

echo "docker deploy ..."
eval "$docker_deploy"