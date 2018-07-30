package com.letv.mas.caller.demo.controller;

import com.letv.mas.caller.demo.service.DemoService;
import com.letv.mas.caller.demo.service.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by leeco on 18/4/19.
 */
@RestController
public class DemoController {

    @Autowired
    DemoService demoService;

    @Autowired
    private GrpcClientService grpcClientService;

    @RequestMapping(value = "/hi")
    public String hello(@RequestParam String name){
        return demoService.sayHello(name);
    }

    @RequestMapping(value = "/config")
    public String config() {
        return demoService.getConfig();
    }


    @RequestMapping("/grpc")
    public String printMessage(@RequestParam(defaultValue = "Leeway") String name) {
        return grpcClientService.sendMessage(name);
    }
}
