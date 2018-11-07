package com.letv.mas.caller.iptv.tvproxy.daemon;

import com.letv.mas.caller.iptv.tvproxy.common.controller.BaseController;
import com.letv.mas.caller.iptv.tvproxy.daemon.config.RabbitMqBeanConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("daemon")
public class DaemonController extends BaseController {


    @Autowired
    RabbitTemplate template;

    @Autowired
    RabbitMqBeanConfig config;

    @RequestMapping("/testmq")
    public void testmq(@RequestParam(value = "message", required = true) String message){
        System.out.println("------ Sender message : " + message);

        template.convertAndSend(config.getFanout_exchange_name(),message);
    }
}

