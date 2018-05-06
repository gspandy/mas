#!/usr/bin/env bash
#eg. sh ./docker_build.sh manager

# load envionment variables
source /etc/profile
docker_hub_local=true
docker_hub_host="reg-sre.lecloud.com"
docker_hub_path="/test_image/"
docker_hub_username="letv_monitor"
docker_hub_password="!@s20180205"
module_prefix="letv-mas-"
module=$1

# current directory
cur_dir=$(cd "$(dirname "${0}")"; pwd)
docker_login="docker login --username=\"${docker_hub_username}\" --password=\"${docker_hub_password}\" ${docker_hub_host}"
image_name="${docker_hub_host}${docker_hub_path}${module_prefix}${module}"
container_remove="docker ps -a | grep \"${module_prefix}${module}\" | awk '{print \$1 }'|xargs docker rm -f;"
docker_remove="docker rmi -f ${image_name}"
docker_build="docker build -t ${image_name}:latest -f $cur_dir/${module}/Dockerfile $cur_dir/${module}"
docker_push="docker push ${image_name}"

echo "stop and remove containers based on the image[${image_name}]..."
echo "$container_remove"
eval "$container_remove"

echo "remove the image[${image_name}]..."
eval "$docker_remove"

if [ "$docker_hub_local" == "false" ]; then
    echo "login the dock hub[$docker_hub_host]..."
    eval $docker_login
fi

echo "docker build the image[${image_name}]..."
eval $docker_build

if [ "$docker_hub_local" == "false" ]; then
    echo "docker push the image[${image_name}] to the dock hub[$docker_hub_host]..."
    eval $docker_push
fi