package com.letv.mas.client.stream.controller;

import com.letv.mas.client.stream.service.MulticastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 组播消息控制器
 * <p>
 * Created by dalvikchen on 18/7/9.
 */
@RestController
@RequestMapping("/multicast")
@ConditionalOnProperty(value = "spring.cloud.stream.message.enabled", havingValue = "true", matchIfMissing = false)
public class MulticastController {
    @Autowired
    private MulticastService multicastService;

    @RequestMapping("/msg")
    public String multicastMsg(@RequestParam(required = true) String content) {
        String msgId = UUID.randomUUID().toString().replace("-", "");
        multicastService.send(msgId, content);
        return "Multicasting Message:" + content;
    }
}
