# MAS监控平台
* [联测环境][url-mas-dashboard-test] mas / mas123
* [生产环境][url-mas-dashboard-prod] TBD

#### 采集器
* 宿主机系统环境

| 软件 | 版本 | 备注
| - | :- | -: | 
| OS（实体机）| LetvOS WebServer 1.6.0 (Final)<br/> Kernel 2.6.32-926.504.30.3.letv.el6.x86_64<br/> Red Hat 4.4.7-4 | cat /proc/version | 
| OS（虚机）| LetvOS WebServer 1.6.0 (Final)<br/> Kernel 2.6.32-926.504.30.3.letv.el6.x86_64<br/> Red Hat 4.4.7-4 | - | 
| Docker | version 1.7.1, build 786b29d/1.7.1 | - | 
| 业务基础镜像 | centos6.6-jdk1.8 | reg-sre.lecloud.com/test_image/letv-mas-base | 

###### 宿主机Docker容器体征采集
Step-1. 安装（root权限）

* 生产环境

~~~
wget -c -r -nH -np --reject=html http://10.58.89.189:10000/telegraf/;sudo chmod +x telegraf/install.sh;sudo telegraf/install.sh --profile=prod
~~~

* 测试环境

~~~
wget -c -r -nH -np --reject=html http://10.58.89.189:10000/telegraf/;sudo chmod +x telegraf/install.sh;sudo telegraf/install.sh --profile=test
~~~
 
Step-2. 登录 MAS监控平台／letv-mas-dashboard，如果LETV-MAS-OMP-DOCKERS中letv_mas_docker_host选项已存在对应主机名则对接OK！

备注：
－安装脚本运维可参考{mas}/monitor/telegraf/install.sh
－面板监控项配置json备份：{mas}/monitor/grafana/template/LETV-MAS-OMP-DOCKERS.json

###### 容器内业务体征采集（爪哇系）
* Pom依赖：

~~~
    <dependencies>
        ...
        <dependency>
            <groupId>com.letv.mas</groupId>
            <artifactId>common</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        ...
    </dependencies>        
    <repositories>
        ...
        <repository>
            <id>levp-release</id>
            <name>Levp Release</name>
            <url>http://maven.letv.cn/nexus/content/repositories/levp-release/</url>
            <releases>
                <enabled>false</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
        <repository>
            <id>levp-snapshot</id>
            <name>levp snapshot</name>
            <url>http://maven.letv.cn/nexus/content/repositories/levp-snapshot/</url>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>        
~~~
备注：common包集成io.micrometer，初始化配置参考com.letv.mas.common.config.monitor.influxdb.MeterRegistryConfig。

* 应用配置：

~~~
management:
  metrics:
    export:
      enabled: true
      influx:
        enabled: true
        auto-create-db: true
        batch-size: 10000
        compressed: true
        connect-timeout: 1s
        consistency: one
        uri: ${METRICS_INFLUXDB_URI:http://10.58.89.189:8086}
        db: ${METRICS_INFLUXDB_DB:letv-mas-service}
        num-threads: 2
        user-name: ${METRICS_INFLUXDB_USERNAME:api}
        password: ${METRICS_INFLUXDB_PASSWORD:api@20180307}
        read-timeout: 2s
        retention-policy:
        step: PT10s
~~~

#### 监控系统

###### 数据存储
[InfluxDb][url-InfluxDb]
 
* 测试环境：10.58.89.189:8086
letv-mas-docker: 容器监控db
letv-mas-service: 应用监控db
* 生产环境：10.110.95.87:8086

###### 数据展示
登录 MAS监控平台／letv-mas-dashboard，如LETV-MAS-OMP-BIZS中letv_mas_biz_name以及host选项已存在对应服务则对接OK！
备注：
－面板监控项配置json备份：{mas}/monitor/grafana/template/LETV-MAS-OMP-BIZS.json

###### 监控报警
1. 登录 MAS监控平台／letv-mas-dashboard，添加Alerting的消息通道，如mail可参考LETV-MAS-BIZ-TVPROXY-MAIL，异构系统可以通过api方式接入。
2. 登录 MAS监控平台／letv-mas-dashboard，参考在LETV-MAS-OMP-BIZS中letv_mas_biz_name选择letv-mas-router-tvproxy业务的alerts配置。

[url-mas-dashboard-test]: http://10.58.89.189:8897/pages/mas/c_docker_dashboard.html "MAS Dashboard"
[url-mas-dashboard-prod]: http:// "TBD"
[url-InfluxDb]: https://www.influxdata.com "InfluxDb"