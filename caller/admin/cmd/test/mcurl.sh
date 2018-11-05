#!/usr/bin/env bash
#sh ./caller/admin/cmd/test/mcurl.sh ./caller/admin/cmd/test/urls.txt 10.58.89.189

# current directory
cur_dir=$(cd "$(dirname "${0}")"; pwd)

url_file=$1
url_host=$2
out_file=${cur_dir}/out.log

if [ -n "$url_host" ]; then
    url_file=$(sed 's/{{letv_mas_router_tvproxy_host}}/'"${url_host}"'/g' ${url_file})
else
    url_file=$(cat ${url_file})
fi
echo '' > ${out_file}
for line in ${url_file}; do
    if [[ "${line}" =~ ^http.* ]]; then
        line=${line}
    else
        line=${url_host}${line}
    fi
    echo $line >> ${out_file};curl $line >> ${out_file};echo '\n' >> ${out_file}
done