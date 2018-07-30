#!/usr/bin/env bash

# current directory
cur_dir=$(cd "$(dirname "${0}")"; pwd)

module=$1
cmd=$2

if [ -z "$module" ]; then
  echo "The building target module is invalid"
  exit 1
fi

if [ -z "$cmd" ]; then
    cmd="package"
fi

mvn -B -e -f $module/pom.xml clean $cmd
#mvn -B -e -U -f $module/pom.xml clean $cmd

#mvn -B -f common/pom.xml clean deploy
#mvn -B -f grpc/pom.xml clean deploy