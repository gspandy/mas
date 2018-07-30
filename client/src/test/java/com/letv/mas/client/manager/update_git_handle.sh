#/bin/bash
str=$1
new_str=$2
echo "str: $1 new_str: $2"
rm -rf config/
git clone 'http://config_server:letv.0606@legitlab.letv.cn/letv-tv-mas/config.git'
cd ./config/letv-mas-manager
echo "update $str to $new_str"
sed -i "" "s/$str/$new_str/g" application-prod.yml
git add .
git commit -m 'update config for junit test'
git push
cd  ../..
pwd
rm -rf config/
