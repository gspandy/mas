package com.letv.mas.client.stream.controller;

import com.letv.mas.client.stream.service.BroadcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 广播消息控制器
 * <p>
 * Created by dalvikchen on 18/7/9.
 */
@RestController
@RequestMapping("/broadcast")
@ConditionalOnProperty(value = "spring.cloud.stream.message.enabled", havingValue = "true", matchIfMissing = false)
public class BroadcastController {

    @Autowired
    private BroadcastService broadcastService;

    @RequestMapping("/msg")
    public String broadcastMsg(@RequestParam(required = true) String content) {
        broadcastService.send(content);
        return "Broadcasting Message:" + content;
    }
}
