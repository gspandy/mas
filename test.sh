#!/usr/bin/env bash

# current directory
cur_dir=$(cd "$(dirname "${0}")"; pwd)

limited_nodes=$(cat $cur_dir/nodes.txt | awk '{str=(str","$0)}END{print str}')
limited_nodes=$(echo $limited_nodes | sed 's/[[:space:]]//g' | sed 's/,/ /g' | sed 's/[ \t]*$//g')

function exec_script() {
  node=$1
  if [ -n "$node" ]; then
    node_region=""
    node_zone=""
    node_ip=""
    node_port=$webserver_port

    arr=(${node//:/ });
    arr_len=${#arr[@]}

    #echo $arr_len
    #for i in ${arr[@]}
    #do
    #  echo $i
    #done

    if [ $arr_len == 1 ]; then
      node_ip=${arr[0]}
    elif [ $arr_len == 2 ]; then
      node_ip=${arr[0]}
      node_port=${arr[1]}
    elif [ $arr_len == 4 ]; then
      node_region=${arr[0]}
      node_zone=${arr[1]}
      node_ip=${arr[2]}
      node_port=${arr[3]}
    else
      return
    fi
  fi
  echo "node_region=${node_region},$node_zone=${node_zone},node_ip=${node_ip},node_port=${node_port}"
}

for node in $limited_nodes; do
  exec_script $node
  if [ $? -ne 0 ]; then
    exit 1
  fi
done
