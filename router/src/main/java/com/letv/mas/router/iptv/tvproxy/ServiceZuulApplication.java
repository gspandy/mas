package com.letv.mas.router.iptv.tvproxy;

import com.letv.mas.router.iptv.tvproxy.interceptor.HttpClientLoggingInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Created by leeco on 18/4/21.
 */
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
@ComponentScan(basePackages = { "com.letv.mas.common.config.monitor.influxdb", "com.letv.mas.router.config", "com.letv.mas.router.iptv.tvproxy"})
public class ServiceZuulApplication {

    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new HttpClientLoggingInterceptor()));
        return restTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceZuulApplication.class, args);
    }
}