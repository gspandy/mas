############################################################
# Dockerfile to build letv-mas-router Installed Containers
# Based on Centos
# Clean up context:
#docker ps -a | grep "Exited" | awk '{print $1 }'|xargs docker rm;
#docker images | grep "<none>" | awk '{print $3 }' | xargs docker rmi;
# Remove container:
#if docker stop letv-mas-router; then docker rm letv-mas-router; fi
# Remove image:
#docker rmi -f letv-mas-router:1.0
# Build image:
#[local]: docker build -t letv-mas-router:1.0 -f ./router/Dockerfile ./router
#[test/pre/prod]: docker build -t letv-mas-router:1.0 -f /letv/deploy/letv-mas-router/Dockerfile /letv/deploy/letv-mas-router
# Run image:
#[local]: docker run -it -d -v /letv/logs/mas/router:/letv/logs/mas/router -p 8011:8010 --restart=always --name letv-mas-router letv-mas-router:1.0 sh -c "java -jar /letv/app/mas/router/router-1.0-SNAPSHOT.jar"
#[pre/prod]: docker run -it -d -v /letv/logs/tomcat/iptv:/letv/logs/tomcat/iptv --net=host --restart=always --name letv-mas-router letv-mas-router:1.0 sh -c "java -jar /letv/app/mas/router/router-1.0-SNAPSHOT.jar"
# Enter the container on this image:
#docker exec -it letv-mas-router /bin/bash
# Check host net:
#route |grep default | awk '{print $2 }'
############################################################
FROM reg-sre.lecloud.com/test_image/letv-mas-base

# File Author / Maintainer
MAINTAINER dengliwei dengliwei@le.com

# Expose ports
#EXPOSE 8010

# create mw and app dir
RUN mkdir -p /letv/app/mas/router
# create the dir linking with external volume!!!
RUN mkdir -p /letv/logs/mas/router && chmod 777 /letv/logs/mas/router

# create the dir linking with external volume
VOLUME ["/letv/app/mas/router", "/letv/logs/mas/router"]

WORKDIR /letv/app/mas/router

# set environmental vars
ENV TRACE_SERVER_URL=http://letv-mas-trace/
ENV CONFIG_SERVER_URL=http://127.0.0.1:8021/
ENV ROUTER_SERVER_IP=127.0.0.1
ENV ROUTER_SERVER_PORT=8101
ENV EUREKA_SERVER_LIST=http://localhost:8010/eureka/
ENV JAVA_OPTS=

# Set the default command to execute
# when creating a new container
CMD [ "/bin/bash", "-c", "echo '10.124.66.211 i.static.itv.letv.com' >> /etc/hosts;java $JAVA_OPTS -jar /letv/app/mas/router-1.0-SNAPSHOT.jar" ]

COPY ./target/router-1.0-SNAPSHOT.jar /letv/app/mas/