#!/usr/bin/env bash
#eg. sh ./docker_build.sh manager

# load envionment variables
source /etc/profile

# current directory
cur_dir=$(cd "$(dirname "${0}")"; pwd)

# initialize parameters
for arg in "$@"; do
  param=${arg%%=*}
  value=${arg#*=}
  case $param in
    --project_path)
      project_path=$value;;
    --project_name)
      project_name=$value;;
    --project_profile)
      project_profile=$value;;
    --package_file)
      package_file=$value;;
    --package_md5)
      package_md5=$value;;
    --webserver_port)
      webserver_port=$value;;
    --webserver_statusurl)
      webserver_statusurl=$value;;
    --node_group)
      node_group=$value;;
    --limited_nodes)
      limited_nodes=$value;;
    --remote_deploy_script)
      remote_deploy_script=$value;;
    --remote_deploy_script_dir)
      remote_deploy_script_dir=$value;;
    --help)
      echo "args:"
      echo "--project_path="
      echo "--project_name="
      echo "--project_profile="
      echo "--package_file="
      echo "--package_md5="
      echo "--webserver_port="
      echo "--webserver_statusurl="
      echo "--node_group="
      echo "--limited_nodes= this will clean the variable --node_group"
      echo "--remote_deploy_script="
      echo "--remote_deploy_script_dir="
      echo "--help"
      exit 0;;
  esac
done

# verify parameters
if [ -z "$package_file" ] && [ -z "$node_group" ] && [ -z "$limited_nodes" ]; then
  echo "parameters are invalid, please try --help"
  exit 1
fi

# set package_file
package_name=$(echo "${package_file%.*}")
if [ -n "$package_md5" ]; then
  local_md5=$(md5sum $cur_dir/$package_file | cut -d ' ' -f1)
  if [ "$package_md5" != "$local_md5" ]; then
    echo "$package_file md5 error"
    exit 1
  fi
  src_filename_prefix=$(echo "${package_file%.*}")
  src_filename_suffix=$(echo "${package_file##*.}")
  package_name=$src_filename_prefix
  if [ "$src_filename_suffix" == "zip" ]; then
    rm -rf "${cur_dir}/$src_filename_prefix"
    unzip -o $cur_dir/$package_file -d "${cur_dir}/$src_filename_prefix"
  fi
fi

if [ -z "$limited_nodes" ]; then
  limited_nodes=$(cat $cur_dir/$node_group | awk '{str=(str","$0)}END{print str}')
fi
limited_nodes=$(echo $limited_nodes | sed 's/[[:space:]]//g' | sed 's/,/ /g' | sed 's/[ \t]*$//g')
if [ -z "$limited_nodes" ]; then
  echo "no nodes for deploying"
  exit 1
fi

if [ $? -ne 0 ]; then
  exit 1
fi

echo "[DEPLOY] begin to build the package file $package_file..."
package_dockerfile="${cur_dir}/${package_name}/Dockerfile"
if [ -f "$package_dockerfile" ]; then
  sh $cur_dir/$package_name/docker_build.sh $project_name
  find $cur_dir/$package_name -name "*.jar" | xargs rm -rf
fi

echo "[DEPLOY] begin to deploy the package file $package_file to the nodes $limited_nodes..."
if [ -z "$remote_deploy_script_dir" ]; then
  remote_deploy_script_dir="/letv/deploy/docker"
fi
if [ -z "$project_path" ]; then
  project_path="${package_name}"
fi

if [ -z "$remote_deploy_script" ]; then
  remote_deploy_script="docker_deploy_m.sh"
fi
remote_deploy_script="${remote_deploy_script_dir}/${remote_deploy_script}"
for node in $limited_nodes; do
  if [ "$node" == "127.0.0.1" ]; then
    echo "[DEPLOY] begin to install on the node $node..."
    sh $remote_deploy_script
  else
    echo "[DEPLOY] scp package file $package_file to the node $node..."
    ssh leworker@$node "[ -d ${remote_deploy_script_dir} ] && echo done || mkdir -p ${remote_deploy_script_dir}"
    scp $cur_dir/$package_name leworker@$node:$remote_deploy_script_dir
    echo "[DEPLOY] begin to install on the node $node...$remote_deploy_script"
    ssh leworker@$node "chmod -R a+x ${remote_deploy_script};sh ${remote_deploy_script} ${project_name} ${webserver_port} ${project_profile}"
  fi
  if [ $? -ne 0 ]; then
    exit 1
  fi
done

docker images | grep '<none>' | awk '{print $3 }' | xargs docker rmi;
echo "[DEPLOY] all the nodes are completely deployed"