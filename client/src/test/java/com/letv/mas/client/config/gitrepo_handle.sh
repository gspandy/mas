#/bin/bash
rm -rf config/
git clone 'http://legitlab.letv.cn/letv-tv-mas/config.git'
cd ./config/letv-mas-client
sed -i '' 's/world/world1/g' letv-mas-client-dev.yml
git add .
git commit -m 'update config for junit test'
git push
