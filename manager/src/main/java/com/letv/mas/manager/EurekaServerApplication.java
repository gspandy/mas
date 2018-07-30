package com.letv.mas.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by leeco on 18/4/18.
 */
@EnableEurekaServer
@EnableEurekaClient
@SpringBootApplication
@ComponentScan(basePackages = { "com.letv.mas.common.config" ,  "com.letv.mas.manager"})
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}