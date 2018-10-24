package com.letv.mas.caller.iptv.tvproxy.search;

import com.letv.mas.caller.iptv.tvproxy.common.TvProxyApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;


/**
 * Created by maning on 18/10/19.
 */
@ComponentScan(basePackages = {"com.letv.mas.caller.iptv.tvproxy.search"})
public class TvProxySearchApplication extends TvProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TvProxySearchApplication.class, args);
    }
}
