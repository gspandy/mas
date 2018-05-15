package com.letv.mas.router.web;

import com.letv.mas.router.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by leeco on 18/4/28.
 */
@RestController
//@RefreshScope
public class DemoController {

    @Autowired
    DemoService demoService;

    @RequestMapping(value = "/config")
    public String config() {
        return demoService.getConfig();
    }
}
