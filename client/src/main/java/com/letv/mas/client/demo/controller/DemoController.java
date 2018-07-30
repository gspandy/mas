package com.letv.mas.client.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by leeco on 18/4/19.
 */
@RestController
public class DemoController {

    @Value("${eureka.instance.metadata-map.zone}")
    private String zone;

    @Value("${server.port}")
    String port;

    @RequestMapping("/hi")
    public String hello(@RequestParam String name) {
        return "hi " + name + ",i am from " + zone + ":" + port;
    }
}
