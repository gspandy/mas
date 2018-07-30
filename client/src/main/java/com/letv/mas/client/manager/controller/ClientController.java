package com.letv.mas.client.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eureka")
@ConditionalOnProperty(value = "spring.cloud.eureka.client.enabled", havingValue = "true", matchIfMissing = false)
public class ClientController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/app")
    public List<ServiceInstance> eurekaClient(String serviceId) {
        return discoveryClient.getInstances(serviceId);
    }
}
