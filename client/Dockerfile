############################################################
# Dockerfile to build letv-mas-client Installed Containers
# Based on Centos
# Clean up context:
#docker ps -a | grep "Exited" | awk '{print $1 }'|xargs docker rm;
#docker images | grep "<none>" | awk '{print $3 }' | xargs docker rmi;
# Remove container:
#if docker stop letv-mas-client; then docker rm letv-mas-client; fi
# Remove image:
#docker rmi -f letv-mas-client:1.0
# Build image:
#[local]: docker build -t letv-mas-client:1.0 -f ./client/Dockerfile ./client
#[test/pre/prod]: docker build -t letv-mas-client:1.0 -f /letv/deploy/letv-mas-client/Dockerfile /letv/deploy/letv-mas-client
# Run image:
#[local]: docker run -it -d -v /letv/logs/mas/client:/letv/logs/mas/client -p 8011:8010 --restart=always --name letv-mas-client letv-mas-client:1.0 sh -c "java -jar /letv/app/mas/client/client-1.0-SNAPSHOT.jar"
#[pre/prod]: docker run -it -d -v /letv/logs/tomcat/iptv:/letv/logs/tomcat/iptv --net=host --restart=always --name letv-mas-client letv-mas-client:1.0 sh -c "java -jar /letv/app/mas/client/client-1.0-SNAPSHOT.jar"
# Enter the container on this image:
#docker exec -it letv-mas-client /bin/bash
# Check host net:
#route |grep default | awk '{print $2 }'
############################################################
FROM reg-sre.lecloud.com/test_image/letv-mas-base

# File Author / Maintainer
MAINTAINER dengliwei dengliwei@le.com

# Expose ports
#EXPOSE 8010

# create mw and app dir
RUN mkdir -p /letv/app/mas/client
# create the dir linking with external volume!!!
RUN mkdir -p /letv/logs/mas/client

# create the dir linking with external volume
VOLUME ["/letv/app/mas/client", "/letv/logs/mas/client"]

WORKDIR /letv/app/mas/client

# set environmental vars
#ENV TRACE_SERVER_URL=http://localhost:9001
#ENV CLIENT_SERVER_IP=127.0.0.1
#ENV CLIENT_SERVER_PORT=8901
#ENV EUREKA_SERVER_LIST=http:/localhost:8010/eureka/
ENV JAVA_OPTS=

# Set the default command to execute
# when creating a new container
CMD [ "/bin/bash", "-c", "java $JAVA_OPTS -jar /letv/app/mas/client-1.0-SNAPSHOT.jar" ]

COPY ./target/client-1.0-SNAPSHOT.jar /letv/app/mas/