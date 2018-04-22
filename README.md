## MAS
## all modules based on the spring cloud framework

### 运行
~~~
eureka-server:  http://localhost:8761/
eureka-client: http://localhost:8762/hi?name=Ivan
eureka-client: http://localhost:8763/hi?name=Ivan
service-ribbon & hystrix: http://localhost:8764/hi?name=Ivan.deng
service-ribbon-hystrix-monitor: http://localhost:8764/hystrix
=>single host: http://localhost:8764/hystrix/monitor?stream=http%3A%2F%2Flocalhost%3A8764%2Fhystrix.stream%20&title=Demo
service-router-zuul:  http://localhost:8769/api-1/hi?name=Leeway&token=123
service-trace-sleuth-zipkin: http://localhost:9001
~~~