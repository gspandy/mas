#/bin/bash

port=$1
#根据端口号查询对应的pid
pid=$(ps -ef|grep java|grep server.port=$port|awk '{print $2}');
echo "pid :$pid"
#杀掉对应的进程，如果pid不存在，则不执行
if [  -n  "$pid"  ];  then
    kill  -9  $pid;
    echo "kill over"
fi
echo "Succeed"
