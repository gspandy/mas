package com.letv.mas.caller.iptv.tvproxy.common;

import com.letv.mas.caller.iptv.tvproxy.common.config.ConfigConfigguration;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Created by leeco on 18/4/19.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@EnableHystrixDashboard
/*@EnableAspectJAutoProxy(proxyTargetClass = true)*/
@ComponentScan(basePackages = {"com.letv.mas.caller.iptv.tvproxy", "com.letv.mas.common.bus","com.letv.mas.common.trace"})
@ServletComponentScan(basePackages = {"com.letv.mas.caller.iptv.tvproxy"})
@MapperScan("com.letv.mas.caller.iptv.tvproxy.common.model.dao.db")
public class TvProxyApplication {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(TvProxyApplication.class, args);
        ConfigConfigguration.init();
    }

    @Bean
    public ServletRegistrationBean dispatcherRegistration(
            DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean registration =
                new ServletRegistrationBean(dispatcherServlet);
        registration.addUrlMappings("/iptv/api/new/*","/iptv/api/*","/*");
        return registration;
    }

}
