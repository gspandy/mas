package com.letv.mas.client;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by leeco on 18/4/18.
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.letv.mas.mapper")
@ComponentScan(basePackages = {"com.letv.mas.common.config", "com.letv.mas.client", "com.letv.mas.common.bus", "com.letv.mas.common.trace"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ServletComponentScan
public class EurekaClientApplication {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(1000);
        simpleClientHttpRequestFactory.setReadTimeout(1000);
        return new RestTemplate(simpleClientHttpRequestFactory);
    }

    public static void main(String[] args) {

        SpringApplication.run(EurekaClientApplication.class, args);
    }
}
