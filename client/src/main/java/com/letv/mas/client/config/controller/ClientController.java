package com.letv.mas.client.config.controller;

import com.letv.mas.client.config.model.bean.EurekaClient;
import com.letv.mas.client.config.model.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 动态属性配置Bean获取配置
 *
 * Created by David.Liu on 18/5/23.
 */
@RestController
@RefreshScope
@ConditionalOnProperty(value = "spring.cloud.bus.enabled", havingValue = "true", matchIfMissing = false)
public class ClientController {

    @Autowired
    private User user;

    @Autowired
    private EurekaClient eurekaClient1;

    @Value("${spring.cloud.bus.destination}")
    private String springCloudBusDestination;

    @GetMapping("/user")
    public User user() {
        return user;
    }

    @GetMapping("/eurekaclient")
    public EurekaClient eurekaClient() {
        return eurekaClient1;
    }

    @GetMapping("/destination")
    public String getDestination(){
        return springCloudBusDestination;
    }
}
