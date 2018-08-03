package com.letv.mas.client.stream.controller;

import com.letv.mas.client.stream.service.PointcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 点播通信控制器
 * <p>
 * 消费者为一组服务时，消息需要通知到其中一个实例的场景下
 * 需要结合消息的分区规则来实现消息的点播
 * <p>
 *
 * @author dalvikchen
 * @date 18/7/9
 */
@RestController
@RequestMapping("/pointcast")
@ConditionalOnProperty(value = "spring.cloud.stream.message.enabled", havingValue = "true", matchIfMissing = false)
public class PointcastController {
    @Autowired
    private PointcastService pointcastService;

    @RequestMapping("/msg")
    public String pointcastMsg(@RequestParam(required = true) String content, @RequestParam(required = true) int partition) {
        String msgId = UUID.randomUUID().toString().replace("-", "");
        pointcastService.send(msgId, content, partition);
        return "Pointcasting Message:" + content + " Partition:" + partition;
    }
}
