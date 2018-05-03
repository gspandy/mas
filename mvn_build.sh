#!/usr/bin/env bash

# current directory
cur_dir=$(cd "$(dirname "${0}")"; pwd)

module=$1

if [ -z "module" ]; then
  echo "The building target module is invalid"
  exit 1
fi

mvn -B -f $module/pom.xml clean package