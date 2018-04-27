package com.letv.mas.router;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by leeco on 18/4/21.
 */
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
@RestController
public class ServiceZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceZuulApplication.class, args);
    }

    @Value("${eureka.client.serviceUrl.defaultZone}") // git配置文件里的key
    private String config;

    @RequestMapping(value = "/config")
    public String config() {
        return config;
    }

}
