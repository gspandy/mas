#/bin/bash
rm -rf config/
git clone 'http://config_server:letv.0606@legitlab.letv.cn/letv-tv-mas/config.git'
cd ./config/letv-mas-client
sed -i '' 's/world/world1/g' application-configprod.yml
git add .
git commit -m 'update config for junit test'
git push
