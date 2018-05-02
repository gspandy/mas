# MAS框架演示

所有模块均基于[Spring Cloud][url-SpringCloud]

#### 环境要求
* JDK 1.8+
* Maven 3+ / Gradle 2.3+ 
* Spring Cloud 版本选择=Cloud代号 + 小版本号

Spring Cloud并没有熟悉的数字版本号，较近开发代号集如下所示：

| Cloud代号 (大版本号) | Boot版本 (train) | lifecycle
| - | :-: | -: | 
| Finchley | 2.x | - | 
| **[Edgware][url-SpringCloud-manual-E]** | **1.5.x** | - | 
| Dalston | 1.5.x | - |
| Camden | 1.4.x | - |
| _Brixton_ | _1.3.x_ | _2017/07卒_ |
| _Angle_ | _1.2.x_ | _EOL in July 2017_ |

开发代号看似没有什么规律，但实际上首字母是有序的，比如：Dalston版本，我们可以简称 D 版本，对应的 Edgware 版本我们可以简称 E 版本。

小版本号
SNAPSHOT：快照版本，随时可能修改。
M：MileStone，M1表示第1个里程碑版本，一般同时标注PRE，表示预览版。
SR：Service Release，SR1表示第1个正式版本，一般同时标注GA(GenerallyAvailable)， 表示稳定版本。

#### 模块介绍

| 模块名 | 中间件（core） | 启动方式 | 备注 | 
| - | - | - | - | 
| caller (服务调用方)| 体检：spring-boot-starter-actuator<br/> 降服：spring-cloud-starter-hystrix<br/> 降服仪表盘：spring-cloud-starter-hystrix-dashboard<br/> HA(基于eureka-client-cluster)：spring-cloud-starter-eureka，spring-cloud-starter-ribbon<br/> 链路跟踪：spring-cloud-starter-zipkin| API: http://localhost:8764/hi?name=Ivan.deng<br/> 本机仪表盘: http://localhost:8764/hystrix | LB：硬－nginx，软－eureka|
| router (服务路由／过滤)| 体检：spring-boot-starter-actuator<br/> 路由：spring-cloud-starter-zuul<br/> HA：spring-cloud-starter-eureka<br/> 链路跟踪：spring-cloud-starter-zipkin<br/> 配置更新：spring-cloud-starter-config| API: http://localhost:8101/api-1/hi?name=Leeway&token=123<br/> 配置读取： http://localhost:8101/config | HA：硬－nginx，软－eureka|
| manager (服务注册管理中心)| 服务注册发现：spring-cloud-starter-eureka-server | http://localhost:8011/ | HA：硬－nginx| 
| client (服务提供方)| 体检：spring-cloud-starter-actuator<br/> HA：spring-cloud-starter-eureka<br/> 链路跟踪：spring-cloud-starter-zipkin| http://localhost:8901/hi?name=Ivan | HA：软－eureka| 
| trace (服务链路跟踪)| 链路收集利器：zipkin-server, zipkin-autoconfigure-ui <br/> HA：spring-cloud-starter-eureka| http://localhost:9001 | HA：硬－nginx|
| config (服务配置中心)| 配置管理：spring-cloud-config-server <br/> HA：spring-cloud-starter-eureka| http://localhost:8021/service-router-zuul/dev | HA：软－eureka|

#### 获取代码
~~~
git clone https://github.com/leeway-deng/mas.git
~~~

#### 框架说明

###### SpringCloud框架组成

![SpringCloud框架组成][img-spring-cloud-fw-plan]

###### zipkin工作图解

![zipkin工作图解][img-zipkin-workflow]

###### Letv-MAS框架设计

![Letv-MAS框架设计][img-letv-mas-fw]

###### Letv-MAS部署说明

| （子）系统名称 | 代码工程 | 编译 | 发布 | 部署 | 备注 |
| - | - | - | - | - | - | 
| 服务管理中心 | manager | maven2.5+  | TODO:jk+docker | 10.58.89.189:8011 | 编译：mvn -B -f manager/pom.xml clean package<br/> 执行：EUREKA_SERVER_PORT=8011 EUREKA_SERVER_LIST= java -jar ./manager/target/manager-1.0-SNAPSHOT.jar<br> 部署：Docker+HA |
| 服务配置中心 | config | maven2.5+ | TODO:jk+docker | 10.58.89.189:8021 | 编译：mvn -B -f config/pom.xml clean package<br/> 执行：EUREKA_SERVER_PORT=8021 EUREKA_SERVER_LIST=http://localhost:8011/eureka/ java -jar ./config/target/config-1.0-SNAPSHOT.jar<br> 部署：Docker+HA |
| 服务路由 | router | maven2.5+ | TODO:jk+docker | 10.58.89.189:8101 | 编译：mvn -B -f router/pom.xml clean package<br/> 执行：ROUTER_SERVER_PORT=8101 EUREKA_SERVER_LIST=http://localhost:8011/eureka/ TRACE_SERVER_URL=http://trace-server-zipkin/ java -jar ./router/target/router-1.0-SNAPSHOT.jar<br> 部署：Docker+HA |
| 服务链路跟踪 | trace | maven2.5+ | TODO:jk+docker | 10.58.89.189:9001 | 编译：mvn -B -f trace/pom.xml clean package<br/> 执行：TRACE_SERVER_PORT=9001 EUREKA_SERVER_LIST=http://localhost:8011/eureka/ java -jar ./trace/target/trace-1.0-SNAPSHOT.jar<br> 部署：Docker+HA |
| 服务提供方 | client | maven2.5+ | TODO:jk+docker | 10.58.89.189:8901<br/>10.58.89.189:8902| 编译：mvn -B -f client/pom.xml clean package<br/> 执行：CLIENT_SERVER_PORT=8902 EUREKA_SERVER_LIST=http://localhost:8011/eureka/ TRACE_SERVER_URL=http://trace-server-zipkin/ java -jar ./client/target/client-1.0-SNAPSHOT.jar<br> 部署：Docker+HA |
| 服务调用方 | caller | maven2.5+ | TODO:jk+docker | 10.58.89.189:9001 | 编译：mvn -B -f caller/pom.xml clean package<br/> 执行：CALLER_SERVER_PORT=8764 EUREKA_SERVER_LIST=http://localhost:8011/eureka/ TRACE_SERVER_URL=http://trace-server-zipkin/ java -jar ./caller/target/caller-1.0-SNAPSHOT.jar<br> 部署：Docker+HA |


[url-SpringCloud]: https://projects.spring.io/spring-cloud "SpringCloud"
[url-SpringCloud-manual-E]: http://cloud.spring.io/spring-cloud-static/Edgware.SR3/index.html
[img-zipkin-workflow]: ./docs/img/sleuth-zipkin-services.jpg "zipkin工作图解"
[img-spring-cloud-fw-plan]: ./docs/img/spring-cloud-fw-plan.jpg "SpringCloud框架计划"
[img-letv-mas-fw]: ./docs/img/letv-mas-fw.jpg "MAS框架设计"