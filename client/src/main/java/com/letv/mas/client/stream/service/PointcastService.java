package com.letv.mas.client.stream.service;

import com.letv.mas.common.stream.model.binding.PartitionSource;
import com.letv.mas.common.stream.model.binding.PointcastSource;
import com.letv.mas.common.stream.model.dto.StreamMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Date;
import java.util.UUID;

/**
 * 点播消息服务类
 * <p>
 * 根据生产者分区key以及分区选择的配置可以指定消息发送到的消费者实例
 *
 * @author dalvikchen
 * @date 18/7/9
 */
@ConditionalOnProperty(value = "spring.cloud.stream.message.enabled", havingValue = "true", matchIfMissing = false)
@EnableBinding(PointcastSource.class)
public class PointcastService {
    private static Logger logger = LoggerFactory.getLogger(PartitionService.class);

    private PointcastSource pointcastSource;

    @Autowired
    public PointcastService(PointcastSource pointcastSource) {
        this.pointcastSource = pointcastSource;
    }

    /**
     * 发送点播消息
     *
     * @param msg
     */
    public void send(String msg, int partition) {
        StreamMessage streamMessage = new StreamMessage();
        streamMessage.setId(UUID.randomUUID().toString().replace("-", ""));
        streamMessage.setFrom("PointcastService");
        streamMessage.setDate(new Date());
        streamMessage.setType(4);
        streamMessage.setContent(msg);
        streamMessage.setAction(3);
        streamMessage.setPartition(partition);
        // 通过绑定的出站消息通道发送消息
        pointcastSource.output().send(MessageBuilder.withPayload(streamMessage).build());
    }
}
