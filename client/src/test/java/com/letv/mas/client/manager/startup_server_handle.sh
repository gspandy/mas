#/bin/bash

#java -jar /letv/project/spring-cloud/demo/demo/service-ribbon/target/service-ribbon-0.0.1-SNAPSHOT.jar --spring.profiles.active=$1 &

jar_path=$1
jar_profile=$2
jar_port=$3
main_class=$4
turl="http://127.0.0.1:$jar_port/info"

cur_dir=$(cd "$(dirname "${0}")"; pwd);

echo "cur_dir : $cur_dir"

cd $cur_dir

echo "wait for app($jar_port) stoping..."

sh kill_server_handle.sh $jar_port

echo "start app($jar_port)"

webserver_cmd_start="java -jar $jar_path $main_class --spring.profiles.active=$jar_profile --server.port=$jar_port &"
echo "$webserver_cmd_start"
eval "$webserver_cmd_start"

echo "wait for $app starting..."
sleep 1s

sleep_count=0
while true; do
  serverstatus=$(curl -I -m 10 -o /dev/null -s -w %{http_code} -x 127.0.0.1:$jar_port $turl)
  echo "Server http status:$serverstatus"
  if [ "$serverstatus" == "200" ]; then
    break
  else
    sleep_count=$(($sleep_count+1))
    sleep 1s
  fi

  if [ $sleep_count -ge 60 ]; then
    echo "Failed:"$sleep_count
    break
  fi
done

echo "Succeed"