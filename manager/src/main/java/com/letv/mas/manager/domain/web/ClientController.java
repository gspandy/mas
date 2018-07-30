package com.letv.mas.manager.domain.web;

import com.letv.mas.manager.domain.EurekaClient;
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

}
