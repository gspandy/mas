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
| caller (服务调用方)| 体检：spring-boot-starter-actuator<br/> 降服：spring-cloud-starter-hystrix<br/> 降服仪表盘：spring-cloud-starter-hystrix-dashboard<br/> HA(基于eureka-client-cluster)：spring-cloud-starter-eureka，spring-cloud-starter-ribbon<br/> 链路跟踪：spring-cloud-starter-zipkin| API: http://localhost:8764/hi?name=Ivan.deng<br/> 本机仪表盘: http://localhost:8764/hystrix<br/> 链路跟踪：spring-cloud-starter-zipkin| LB：硬－nginx，软－eureka|
| router (服务路由／过滤)| 体检：spring-boot-starter-actuator<br/> 路由：spring-cloud-starter-zuul<br/> HA：spring-cloud-starter-eureka<br/> 链路跟踪：spring-cloud-starter-zipkin| http://localhost:8769/api-1/hi?name=Leeway&token=123 | HA：硬－nginx，软－eureka|
| manager (服务注册管理中心)| 服务注册发现：spring-cloud-starter-eureka-server | http://localhost:8761/ | HA：硬－nginx| 
| client (服务提供方)| 体检：spring-cloud-starter-actuator<br/> HA：spring-cloud-starter-eureka<br/> 链路跟踪：spring-cloud-starter-zipkin| http://localhost:8763/hi?name=Ivan | HA：软－eureka| 
| trace (服务链路跟踪)| 链路收集利器：zipkin-server, zipkin-autoconfigure-ui <br/> HA：spring-cloud-starter-eureka| http://localhost:9001 | HA：硬－nginx|

#### 获取代码
~~~
git clone https://github.com/leeway-deng/mas.git
~~~

![SpringCloud框架组成][img-spring-cloud-fw-plan]

![zipkin工作图解][img-zipkin-workflow]

[url-SpringCloud]: https://projects.spring.io/spring-cloud "SpringCloud"
[url-SpringCloud-manual-E]: http://cloud.spring.io/spring-cloud-static/Edgware.SR3/index.html
[img-zipkin-workflow]: ./docs/img/sleuth-zipkin-services.jpg "zipkin工作图解"
[img-spring-cloud-fw-plan]: ./docs/img/spring-cloud-fw-plan.jpg "SpringCloud框架计划"