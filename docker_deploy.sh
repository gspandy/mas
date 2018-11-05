#!/usr/bin/env bash
#eg. sh ./docker_deploy.sh --image=reg-sre.lecloud.com/test_image/letv-mas-manager --app=letv-mas-manager-8011 --run_opts='-v /$log_base_path/$app:/letv/logs/mas/manager -p 8011:8010 --restart=always' --port=8011 --turl='http://localhost:8011/'

# load envionment variables
source /etc/profile
docker_hub_local=true
docker_hub_host="reg-sre.lecloud.com"
docker_hub_path="/test_image/"
docker_hub_email="letv_monitor@le.com"
docker_hub_username="letv_monitor"
docker_hub_password="!@s20181105"
app_base_path=/letv/app/mas
log_base_path=/letv/logs/mas

# current directory
cur_dir=$(cd "$(dirname "${0}")"; pwd)

# initialize parameters
for arg in "$@"; do
  param=${arg%%=*}
  value=${arg#*=}
  case $param in
    --image)
      image=$value;;
    --app)
      app=$value;;
    --run_opts)
      run_opts=$value;;
    --port)
      port=$value;;
    --turl)
      turl=$value;;
    --help)
      echo "args eg.:"
      echo "--image='reg-sre.lecloud.com/test_image/letv-mas-manager'"
      echo "--app='letv-mas-manager-8011'"
      echo "--run_opts='-v /$log_base_path/$app:/letv/logs/mas/manager -p 8011:8010 --restart=always'"
      echo "--port=8011"
      echo "--turl='http://localhost:8011/'"
      echo "--help"
      exit 0;;
  esac
done

# verify parameters
if [ -z "$image" ] || [ -z "$app" ]; then
  echo "[error]parameters are invalid, please try --help"
  exit 1
fi

if [ -z "$port" ]; then
  webserver_port="80"
fi

if [ -z "$run_opts" ]; then
  run_opts=""
fi

docker_login="docker login --email=\'${docker_hub_email}\' --username=\'${docker_hub_username}\' --password=\'${docker_hub_password}\' ${docker_hub_host}"
docker_pull="docker pull $image"
app_start="docker run -it -d $run_opts --name $app $image"
app_stop="if docker stop $app; then docker rm $app; fi"

echo "$app stop..."
eval "$app_stop"
sleep_count=0
while true; do
  pids=$(docker ps -a | grep $app | awk '{if ($1 != "")print $1}')
  if [ -z "$pids" ]; then
    echo "$app stopped normally"
    break;
  else
    sleep_count=$(($sleep_count+1))
    sleep 1s
  fi

  if [ $sleep_count -ge 10 ]; then
    pids=$(docker ps -a | grep $app | awk '{if ($1 != "")print $1}')
    if [ -n "$pids" ]; then
      for pid in $pids; do
        eval "if docker stop $pid; then docker rm $pid; fi"
        done
    fi
    echo "$app stopped abnormally"
    break
  fi
done

echo "init host FS ..."
mkdir -p $log_base_path/$app
mkdir -p $app_base_path/$app

if [ "$docker_hub_local" == "false" ]; then
    echo "get the remote $app..."
    eval $docker_login
    if [ $? -eq 0 ]; then
        eval $docker_pull
    fi
fi

echo "$app start..."
eval "$app_start"
if [ $? -ne 0 ]; then
  exit 1
fi

echo "wait for $app starting..."
sleep 1s

sleep_count=0
while true; do
  serverstatus=$(curl -I -m 10 -o /dev/null -s -w %{http_code} -x 127.0.0.1:$port $turl)
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

docker images | grep '<none>' | awk '{print $3 }' | xargs docker rmi;
echo "Succeed"