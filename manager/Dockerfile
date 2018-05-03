############################################################
# Dockerfile to build letv-mas-manager Installed Containers
# Based on Centos
# Clean up context:
#docker ps -a | grep "Exited" | awk '{print $1 }'|xargs docker rm;
#docker images | grep "<none>" | awk '{print $3 }' | xargs docker rmi;
# Remove container:
#if docker stop letv-mas-manager; then docker rm letv-mas-manager; fi
# Remove image:
#docker rmi -f letv-mas-manager:1.0
# Build image:
#[local]: docker build -t letv-mas-manager:1.0 -f ./manager/Dockerfile ./manager
#[test/pre/prod]: docker build -t letv-mas-manager:1.0 -f /letv/deploy/letv-mas-manager/Dockerfile /letv/deploy/letv-mas-manager
# Run image:
#[local]: docker run -it -d -v /letv/logs/mas/manager:/letv/logs/mas/manager -p 8011:8010 --restart=always --name letv-mas-manager letv-mas-manager:1.0 sh -c "java -jar /letv/app/mas/manager/manager-1.0-SNAPSHOT.jar"
#[pre/prod]: docker run -it -d -v /letv/logs/tomcat/iptv:/letv/logs/tomcat/iptv --net=host --restart=always --name letv-mas-manager letv-mas-manager:1.0 sh -c "java -jar /letv/app/mas/manager/manager-1.0-SNAPSHOT.jar"
# Enter the container on this image:
#docker exec -it letv-mas-manager /bin/bash
# Check host net:
#route |grep default | awk '{print $2 }'
############################################################
FROM reg-sre.lecloud.com/test_image/letv-mas-base

# File Author / Maintainer
MAINTAINER dengliwei dengliwei@le.com

# Expose ports
#EXPOSE {$EUREKA_SERVER_PORT}
EXPOSE 8010

# create mw and app dir
RUN mkdir -p /letv/app/mas/manager
# create the dir linking with external volume!!!
RUN mkdir -p /letv/logs/mas/manager
COPY ./target/manager-1.0-SNAPSHOT.jar /letv/app/mas/manager/

# create the dir linking with external volume
VOLUME ["/letv/app/mas/manager", "/letv/logs/mas/manager"]

WORKDIR /letv/app/mas/manager

# set environmental vars
#ENV EUREKA_SERVER_PORT={$EUREKA_SERVER_PORT}
ENV EUREKA_SERVER_PORT=8010
ENV EUREKA_SERVER_LIST=

# Set the default command to execute
# when creating a new container
CMD [ "/bin/bash", "-c", "java -jar /letv/app/mas/manager/manager-1.0-SNAPSHOT.jar" ]
