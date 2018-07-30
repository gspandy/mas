#!/usr/bin/env bash
#eg. sh ./ssl_key.sh --module=manager --password=admin20180605
#eg. sh ./ssl_key.sh --module=manager --password=admin20180605 --alias=letv-mas-manager
#eg. sh ./ssl_key.sh --opt=import --module=manager --password=admin20180605

# load envionment variables
source /etc/profile

# current directory
cur_dir=$(cd "$(dirname "${0}")"; pwd)

# initialize parameters
for arg in "$@"; do
  param=${arg%%=*}
  value=${arg#*=}
  case $param in
    --opt)
      opt=$value;;
    --module)
      module=$value;;
    --password)
      password=$value;;
    --alias)
      alias=$value;;
    --type)
      type=$value;;
    --help)
      echo "args:"
      echo "--opt=gen | import"
      echo "--module="
      echo "--password="
      echo "--alias="
      echo "--type=client | server | '' or all"
      echo "--help"
      exit 0;;
  esac
done

# verify parameters
if [ -z "$module" ] || [ -z "$password" ]; then
  echo "[error]parameters are invalid, please try --help"
  exit 1
fi

if [ -z "$opt" ]; then
  opt="gen"
fi

if [ -z "$alias" ]; then
  alias="letv-mas-${module}"
fi

out_path="${cur_dir}/${module}/src/main/resources"

function gen_keystore() {
  keystore=$1
  certfile=$2
  cmd_del_keystore="keytool -delete -alias ${alias} -storepass ${password} -keystore ${keystore}"
  cmd_gen_keystore="keytool -genkeypair -alias ${alias} -validity 3650 -keysize 1024 -storetype JKS -keyalg RSA -dname \"CN=(${module}), OU=(mas), O=(letv), L=(BJ), ST=(BJ), C=(CN)\" -storepass ${password} -keypass ${password} -keystore ${keystore} -v"
  cmd_gen_cert="keytool -exportcert -alias ${alias} -storepass ${password} -keystore ${keystore} -file ${certfile}"
  echo "generate the ${module} keystore file..."
  eval "${cmd_del_keystore}"
  eval "${cmd_gen_keystore}"
  echo "generate the ${module} certification file..."
  eval "${cmd_gen_cert}"
}

function import_cert() {
  keystore=$1
  certfile=$2
  alias=$3
  echo "import the ${module} certification file..."
  cmd_del_keystore="keytool -delete -alias ${alias} -storepass ${password} -keystore ${keystore}"
  cmd_import_cert="keytool -importcert -alias ${alias} -storepass ${password} -keystore ${keystore} -file ${certfile} -noprompt -v"
  cmd_view_keystore="keytool -list -keystore ${keystore} -storepass ${password}"
  eval "${cmd_del_keystore}"
  eval "${cmd_import_cert}"
  eval "${cmd_view_keystore}"
}

if [ "$opt" == "gen" ]; then
  if [ -z "$type" ] || [ "$type" == "all" ]; then
    alias="letv-mas-${module}-client"
    gen_keystore "${out_path}/${alias}.jks" "${out_path}/${alias}.crt"
    alias="letv-mas-${module}-server"
    gen_keystore "${out_path}/${alias}.jks" "${out_path}/${alias}.crt"
  else
     alias="letv-mas-${module}-${type}"
     gen_keystore "${out_path}/${alias}.jks" "${out_path}/${alias}.crt"
  fi

elif [ "$opt" == "import" ]; then
  if [ -z "$type" ] || [ "$type" == "all" ]; then
    alias="letv-mas-${module}"
    # 将服务端证书导入客户端storce
    import_cert "${out_path}/${alias}-client.jks" "${out_path}/${alias}-server.crt" "${alias}-server"
    # 将客户端证书导入服务端storce
    alias="letv-mas-${module}"
    import_cert "${out_path}/${alias}-server.jks" "${out_path}/${alias}-client.crt" "${alias}-client"
  elif [ "$type" == "client" ]; then
    alias="letv-mas-${module}"
    import_cert "${out_path}/${alias}-server.jks" "${out_path}/${alias}-${type}.crt" "${alias}-${type}"
  elif [ "$type" == "server" ]; then
     alias="letv-mas-${module}"
     import_cert "${out_path}/${alias}-client.jks" "${out_path}/${alias}-${type}.crt" "${alias}-${type}"
  fi
fi