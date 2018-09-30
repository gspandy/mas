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
| caller (服务调用方)| 体检：spring-boot-starter-actuator<br/> 降服：spring-cloud-starter-hystrix<br/> 降服仪表盘：spring-cloud-starter-hystrix-dashboard<br/> HA(基于eureka-client-cluster)：spring-cloud-starter-eureka，spring-cloud-starter-ribbon<br/> 链路跟踪：spring-cloud-starter-zipkin<br/> grpc客户端：grpc-client-spring-boot-starter| API: http://localhost:8764/hi?name=Ivan.deng<br/> 本机仪表盘: http://localhost:8764/hystrix<br/> rpc调用: http://localhost:8764/grpc | LB：硬－nginx，软－eureka|
| router (服务路由／过滤)| 体检：spring-boot-starter-actuator<br/> 路由：spring-cloud-starter-zuul<br/> HA：spring-cloud-starter-eureka<br/> 链路跟踪：spring-cloud-starter-zipkin<br/> 配置更新：spring-cloud-starter-config| API: http://localhost:8101/api-1/hi?name=Leeway&token=123<br/> 配置读取： http://localhost:8101/config | HA：硬－nginx，软－eureka|
| manager (服务注册管理中心)| 服务注册发现：spring-cloud-starter-eureka-server | http://localhost:8011/ | HA：硬－nginx| 
| client (服务提供方)| 体检：spring-cloud-starter-actuator<br/> HA：spring-cloud-starter-eureka<br/> 链路跟踪：spring-cloud-starter-zipkin<br/> grpc服务端：grpc-server-spring-boot-starter| http://localhost:8901/hi?name=Ivan | HA：软－eureka| 
| trace (服务链路跟踪)| 链路收集利器：zipkin-server, zipkin-autoconfigure-ui <br/> HA：spring-cloud-starter-eureka| http://localhost:9001 | HA：硬－nginx|
| config (服务配置中心)| 配置管理：spring-cloud-config-server <br/> HA：spring-cloud-starter-eureka| http://localhost:8021/letv-mas-router/dev | HA：软－eureka|
| grpc (rpc模块)| io.grpc.grpc-netty<br/> io.grpc.grpc-protobuf<br/> grpc-client-spring-boot-starter<br/> grpc-server-spring-boot-starter | 编译方式: sh ./mvn_build.sh grpc/demo install | 其它模块调用前先执行mvn install将其添加至本地仓库|

#### 获取代码
~~~
git clone https://github.com/leeway-deng/mas.git
~~~

###### 代码结构
├── module  
│   ├── admin 管理相关
│   │   ├── deploy 部署相关
│   │   │   ├── profile 业务xx环境变量配置文件集
│   ├── src 代码及资源目录 
│   │   ├── main
│   │   │   ├── java 代码目录
│   │   │   │   ├── com.letv.mas.{module}.{部门}.{业务线}[.{功能集}]
│   │   │   │   │   ├── controller 开放API层逻辑，直接封装Service方法，定义输入输出参数对象，加入流控、安全、在线接口文档等；
│   │   │   │   │   ├── interceptor 拦截器
│   │   │   │   │   ├── model 数据集合
│   │   │   │   │   │   ├── bean
│   │   │   │   │   │   │   ├──bo 业务对象，由Service层输出的封装业务逻辑的对象
│   │   │   │   │   │   │   ├──do 与数据库表结构一一对应,通过DAO层向上传输数据源对象
│   │   │   │   │   │   ├── dao 数据访问层逻辑，与底层 MySQL、Oracle、Hbase 等进行数据交互
│   │   │   │   │   │   ├── dto 数据传输对象，Service 或 Manager 向外传输的对象
│   │   │   │   │   ├── service 相对具体的业务逻辑服务层
│   │   │   │   │   ├── web 展示型业务控制层，区别与纯API层
│   │   │   │   │   ├── util 内部工具集合
│   │   │   │   │   ├── XxxApplication.java SpringBoot程序主入口
│   │   │   ├── resources 资源目录
│   │   │   │   ├── {业务线}[{功能集}]
│   │   │   │   │   ├── ...xml/yml/properties 相关配置文件
│   ├── pom.xml 默认本module的demo编译配置文件
│   ├── pom[-{部门}-{业务线}[-{功能集}]].xml maven编译配置文件[注]

备注：
- pom[-{部门}-{业务线}[-{功能集}]].xml 如存在不同业务线、不同功能集共存维护，可做隔离打包配置，具体说明如下：
eg.可参考文件{mas}/caller/pom-tvproxy-user.xml
~~~
    <build>
        <sourceDirectory>${basedir}/src/main/java/com/letv/mas/{module}/{部门}/{业务线}[/{功能集}]</sourceDirectory>
        <outputDirectory>${project.build.directory}/classes</outputDirectory>
        <resources>
            <resource>
                <directory>${basedir}/src/main/resources/{业务线}[{功能集}]</directory>
            </resource>
            <!--如指定文件类型，参考-->
            <resource>
                <directory>${basedir}/src/main/java/com/letv/mas/{module}/{部门}/{业务线}[/{功能集}]</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
            </resource>
        </resources>  
        <!--如增加其他代码目录，参考caller-->  
        <plugins>
            <plugin>
                <!--http://www.mojohaus.org/build-helper-maven-plugin/index.html-->
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>build-helper-maven-plugin</artifactId>
                <version>3.0.0</version>
                <executions>
                    <execution>
                        <id>add-source</id>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>add-source</goal>
                        </goals>
                        <configuration>
                            <sources>
                                <source>${basedir}/src/main/java/com/letv/mas/caller/demo</source>
                            </sources>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <!--如指定特定主程序入口，参考caller-->  
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>1.5.10.RELEASE</version>
                <configuration>
                    <!-- 可以把依赖的包都打包到生成的Jar包中 -->
                    <mainClass>com.letv.mas.caller.iptv.tvproxy.user.ServiceRibbonApplication</mainClass>
                    <layout>ZIP</layout>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <!-- 把依赖的包都打包到生成的Jar包中 -->
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>                        
          </plugins>  
    </build>
~~~

* 本地编译
./mvn_build.sh caller package '' pom-tvproxy-user.xml
* JK编译
Root POM选取 caller/pom-tvproxy-user.xml
* 本地IDE调试运行
先执行如上本地编译，如IdeaIDE在Run/Debug配置修改Main入口类，并去掉Make加载项（不执行pom.xml编译）即可进行调试运行

-/info增加git版本及编译信息，具体参考{mas}/router/pom-tvproxy.xml中相关部分：
~~~
...
            <plugin>
                <groupId>pl.project13.maven</groupId>
                <artifactId>git-commit-id-plugin</artifactId>
                ...
            </plugin>   
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                ...
            </plugin>  
...                                                    
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
| 服务管理中心 | manager | maven2.5+  | jk+docker | localhost:8011<br/> localhost:8012 | 编译：sh ./mvn_build.sh manager<br/> 执行：EUREKA_SERVER_IP=127.0.0.1 EUREKA_SERVER_PORT=8011 EUREKA_SERVER_LIST= java -jar ./manager/target/manager-1.0-SNAPSHOT.jar<br> Docker镜像：sh ./docker_build.sh manager<br/> 部署：sh ./docker_deploy_ha_m.sh manager 8011 local, sh ./docker_deploy_ha_m.sh manager 8012 local |
| 服务配置中心 | config | maven2.5+ | jk+docker | localhost:8021 | 编译：sh ./mvn_build.sh config<br/> 执行：EUREKA_SERVER_IP=127.0.0.1 EUREKA_SERVER_PORT=8021 EUREKA_SERVER_LIST=http://localhost:8011/eureka/ java -jar ./config/target/config-1.0-SNAPSHOT.jar<br> Docker镜像：sh ./docker_build.sh manager<br/> 部署：sh ./docker_deploy_ha_m.sh config 8021 local |
| 服务路由 | router | maven2.5+ | jk+docker | localhost:8101 | 编译：sh ./mvn_build.sh router<br/> 执行：ROUTER_SERVER_IP=127.0.0.1 ROUTER_SERVER_PORT=8101 EUREKA_SERVER_LIST=http://localhost:8011/eureka/ TRACE_SERVER_URL=http://letv-mas-trace/ java -jar ./router/target/router-1.0-SNAPSHOT.jar<br> Docker镜像：sh ./docker_build.sh manager<br/> 部署：sh ./docker_deploy_ha_m.sh router 8101 local |
| 服务链路跟踪 | trace | maven2.5+ | jk+docker | localhost:9001 | 编译：sh ./mvn_build.sh trace<br/> 执行：TRACE_SERVER_IP=127.0.0.1 TRACE_SERVER_PORT=9001 EUREKA_SERVER_LIST=http://localhost:8011/eureka/ java -jar ./trace/target/trace-1.0-SNAPSHOT.jar<br> Docker镜像：sh ./docker_build.sh trace<br/> 部署：sh ./docker_deploy_ha_m.sh trace 9001 local |
| 服务提供方 | client | maven2.5+ | jk+docker | localhost:8901<br/>localhost:8902 | 编译：sh ./mvn_build.sh client<br/> 执行：CLIENT_SERVER_IP=127.0.0.1 CLIENT_SERVER_PORT=8902 EUREKA_SERVER_LIST=http://localhost:8011/eureka/ TRACE_SERVER_URL=http://letv-mas-trace/ java -jar ./client/target/client-1.0-SNAPSHOT.jar<br> Docker镜像：sh ./docker_build.sh client<br/> 部署：sh ./docker_deploy_ha_m.sh client 8901 local, sh ./docker_deploy_ha_m.sh client 8902 local |
| 服务调用方 | caller | maven2.5+ | jk+docker | localhost:8764 | 编译：sh ./mvn_build.sh caller<br/> 执行：CALLER_SERVER_IP=127.0.0.1 CALLER_SERVER_PORT=8764 EUREKA_SERVER_LIST=http://localhost:8011/eureka/ TRACE_SERVER_URL=http://letv-mas-trace/ java -jar ./caller/target/caller-1.0-SNAPSHOT.jar<br> Docker镜像：sh ./docker_build.sh caller<br/> 部署：sh ./docker_deploy_ha_m.sh caller 8764 local |
| 监控平台 | omp | js | TODO:jk+docker | 测试环境：10.58.89.189:8897 | scp -r ./omp root@10.58.89.189:/letv/web/omp.mas.letv.cn | - |


[url-SpringCloud]: https://projects.spring.io/spring-cloud "SpringCloud"
[url-SpringCloud-manual-E]: http://cloud.spring.io/spring-cloud-static/Edgware.SR3/index.html
[img-zipkin-workflow]: ./docs/img/sleuth-zipkin-services.jpg "zipkin工作图解"
[img-spring-cloud-fw-plan]: ./docs/img/spring-cloud-fw-plan.jpg "SpringCloud框架计划"
[img-letv-mas-fw]: ./docs/img/letv-mas-fw.jpg "MAS框架设计"

