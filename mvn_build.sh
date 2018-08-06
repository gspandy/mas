#!/usr/bin/env bash

# current directory
cur_dir=$(cd "$(dirname "${0}")"; pwd)

module=$1
cmd=$2
biz=$3

if [ -z "$module" ]; then
  echo "The building target module is invalid"
  exit 1
fi

if [ -z "$cmd" ]; then
  cmd="package"
fi

if [ -n "$biz" ] && [ -f "$module/src/main/resources/bootstrap-$biz.yml" ]; then
  if [ ! -f "$module/src/main/resources/bootstrap.yml.bak" ]; then
    cp -pf $module/src/main/resources/bootstrap.yml $module/src/main/resources/bootstrap.yml.bak
  fi
  cp -f $module/src/main/resources/bootstrap-$biz.yml $module/src/main/resources/bootstrap.yml
fi

mvn -B -e -f $module/pom.xml clean $cmd
#mvn -B -e -U -f $module/pom.xml clean $cmd

#mvn -B -f common/pom.xml clean deploy
#mvn -B -f grpc/pom.xml clean deploy