############################################################
# Dockerfile to build letv-mas-caller Installed Containers
# Based on Centos
# Clean up context:
#docker ps -a | grep "Exited" | awk '{print $1 }'|xargs docker rm;
#docker images | grep "<none>" | awk '{print $3 }' | xargs docker rmi;
# Remove container:
#if docker stop letv-mas-caller; then docker rm letv-mas-caller; fi
# Remove image:
#docker rmi -f letv-mas-caller:1.0
# Build image:
#[local]: docker build -t letv-mas-caller:1.0 -f ./caller/Dockerfile ./caller
#[test/pre/prod]: docker build -t letv-mas-caller:1.0 -f /letv/deploy/letv-mas-caller/Dockerfile /letv/deploy/letv-mas-caller
# Run image:
#[local]: docker run -it -d -v /letv/logs/mas/caller:/letv/logs/mas/caller -p 8011:8010 --restart=always --name letv-mas-caller letv-mas-caller:1.0 sh -c "java -jar /letv/app/mas/caller/caller-1.0-SNAPSHOT.jar"
#[pre/prod]: docker run -it -d -v /letv/logs/tomcat/iptv:/letv/logs/tomcat/iptv --net=host --restart=always --name letv-mas-caller letv-mas-caller:1.0 sh -c "java -jar /letv/app/mas/caller/caller-1.0-SNAPSHOT.jar"
# Enter the container on this image:
#docker exec -it letv-mas-caller /bin/bash
# Check host net:
#route |grep default | awk '{print $2 }'
############################################################
FROM reg-sre.lecloud.com/test_image/letv-mas-base

# File Author / Maintainer
MAINTAINER dengliwei dengliwei@le.com

# Expose ports
#EXPOSE 8010

# create mw and app dir
RUN mkdir -p /letv/app/mas/caller
# create the dir linking with external volume!!!
RUN mkdir -p /letv/logs/mas/caller

# create the dir linking with external volume
VOLUME ["/letv/app/mas/caller", "/letv/logs/mas/caller"]

WORKDIR /letv/app/mas/caller

# set environmental vars
ENV TRACE_SERVER_URL=http://letv-mas-trace/
# ENV CONFIG_SERVER_URL=http://127.0.0.1:8021/
ENV SERVER_IP=127.0.0.1
ENV SERVER_PORT=8101
ENV EUREKA_SERVER_LIST=http:/localhost:8010/eureka/

# Set the default command to execute
# when creating a new container
CMD [ "/bin/bash", "-c", "java $JAVA_OPTS  -jar /letv/app/mas/caller-1.0-SNAPSHOT.jar" ]
#CMD [ "/bin/bash", "-c", "java $JAVA_OPTS -Xms4g -Xmx4g -Xmn2g -Xss1024K -XX:PermSize=256m -XX:MaxPermSize=512m -XX:ParallelGCThreads=8 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+UseCMSCompactAtFullCollection -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=10 -XX:+CMSParallelRemarkEnabled -XX:CMSFullGCsBeforeCompaction=10 -XX:CMSInitiatingOccupancyFraction=80 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -Xloggc:/letv/logs/mas/caller/gc.log -XX:+ExplicitGCInvokesConcurrent -Dsun.rmi.dgc.client.gcInterval=14400000 -Dsun.rmi.dgc.server.gcInterval=14400000 -jar /letv/app/mas/caller-1.0-SNAPSHOT.jar" ]

COPY ./target/caller-1.0-SNAPSHOT.jar /letv/app/mas/