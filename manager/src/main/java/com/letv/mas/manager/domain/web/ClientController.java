package com.letv.mas.manager.domain.web;

import com.letv.mas.manager.domain.EurekaClient;
import com.netflix.servo.annotations.DataSourceType;
import com.netflix.servo.annotations.Monitor;
import com.netflix.servo.monitor.Monitors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RefreshScope
public class ClientController {

    @Autowired
    private EurekaClient eurekaClient1;

    @GetMapping("/eurekaclient")
    public EurekaClient eurekaClient() {
        return eurekaClient1;
    }

    public ClientController(){
        Monitors.registerObject("test", this);
    }

    @Monitor(name = "ClientControllerTest", type = DataSourceType.RATE)
    public int test(){
        return 1;
    }

}
