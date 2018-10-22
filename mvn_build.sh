#!/usr/bin/env bash

# current directory
cur_dir=$(cd "$(dirname "${0}")"; pwd)

module=$1
cmd=$2
biz=$3
file=$4
os=$(uname)

if [ -z "$module" ]; then
  echo "The building target module is invalid"
  exit 1
fi

if [ -z "$cmd" ]; then
  cmd="clean package"
fi

# 指定项目编译的pom文件
resource_path="/"
if [ -z "$file" ]; then
  file="pom.xml"
else
    cp -f $module/$file $module/pom.xml
    if [ -f "$module/pom.xml" ]; then
        resource_path=$(cat $module/pom.xml | grep '<directory>${basedir}/src/main/resources' | sed 's/.*\/src\/main\/resources\/\(.*\)<\/directory>/\1/g')
        if [ -z "$file" ]; then
            resource_path="/"
        else
            resource_path="/${resource_path}/"
        fi
    fi
fi
#echo ${resource_path}

# 如指定biz进行本地启动配置切换
if [ -n "$biz" ] && [ -f "$module/src/main/resources${resource_path}bootstrap-$biz" ]; then
  if [ ! -f "$module/src/main/resources${resource_path}bootstrap.yml.bak" ]; then
    cp -pf $module/src/main/resources${resource_path}bootstrap.yml $module/src/main/resources${resource_path}bootstrap.yml.bak
  fi
  cp -f $module/src/main/resources${resource_path}bootstrap-$biz $module/src/main/resources${resource_path}bootstrap.yml
fi

# 编译 & 打包
mvn -B -e -f $module/$file $cmd

# 如指定子编译完成后替换pom.xml中源码目录, 便于IDE代码调试；
if [ -n "$file" ]; then
    if [ -n "$os" ]; then
        if [ "$os" == "Darwin" ]; then
            sed -i "" '/sourceDirectory/d' $module/pom.xml
        else
            sed -i '/sourceDirectory/d' $module/pom.xml
        fi
    fi
fi

#mvn -B -e -U -f $module/$file clean $cmd

#mvn -B -f common/pom.xml clean deploy
#mvn -B -f grpc/pom.xml clean deploy

# scp -r ./omp root@10.58.89.189:/letv/web/omp.mas.letv.cn ott@20170601