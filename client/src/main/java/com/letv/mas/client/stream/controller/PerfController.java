package com.letv.mas.client.stream.controller;

import com.letv.mas.client.stream.service.PerfService;
import com.letv.mas.client.stream.service.PerfSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息性能测试控制器
 * <p>
 * Created by dalvikchen on 18/7/9.
 */
@RestController
@RequestMapping("/perf")
@ConditionalOnProperty(value = "spring.cloud.stream.perf.enabled", havingValue = "true", matchIfMissing = false)
public class PerfController {

    @Autowired
    private PerfService perfService;

    @Autowired
    private PerfSimpleService perfSimpleService;

    @RequestMapping("/msg")
    public String perfMsg(@RequestParam(required = true) String content) {
        perfService.send(content);
        return "Sending Message:" + content;
    }

    @RequestMapping("/msg/big")
    public String perfBigMsg() {
        String content = "This is a big message!";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 200; i++) {
            sb.append(content);
        }
        perfService.send(sb.toString());
        return "Sending Big Message:" + content;
    }

    @RequestMapping("/simple")
    public String perfSimpleMsg(@RequestParam(required = true) String content) {
        perfSimpleService.sendSimple(content);
        return "Sending Simple Message:" + content;
    }
}
