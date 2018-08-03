package com.letv.mas.client.stream.controller;

import com.letv.mas.client.stream.service.PartitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 消息分区控制器
 * <p>
 *
 * @author dalvikchen
 * @date 18/7/9
 */
@RestController
@RequestMapping("/partition")
@ConditionalOnProperty(value = "spring.cloud.stream.message.enabled", havingValue = "true", matchIfMissing = false)
public class PartitionController {
    @Autowired
    private PartitionService partitionService;

    @RequestMapping("/msg")
    public String partitionMsg(@RequestParam(required = true) String content, @RequestParam(required = true) int type) {
        String msgId = UUID.randomUUID().toString().replace("-", "");
        partitionService.send(msgId, content, type);
        return "Partitioning Message:" + content + " Type:" + type;
    }
}
