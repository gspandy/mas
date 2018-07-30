package com.letv.mas.client.stream.service;

import com.letv.mas.common.stream.model.binding.PartitionSource;
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
 * 消息分区服务类
 * <p>
 * 消息分区特性支持将消息按照特性分别发送给不同的消费者实例，使得消息可以被消费者分区消费。
 * 消息分区基于组播，某一组消费者针对消息进行分区消费的场景
 * <p>
 *
 * @author dalvikchen
 * @date 18/7/9
 */
@ConditionalOnProperty(value = "spring.cloud.stream.message.enabled", havingValue = "true", matchIfMissing = false)
@EnableBinding(PartitionSource.class)
public class PartitionService {
    private static Logger logger = LoggerFactory.getLogger(PartitionService.class);

    private PartitionSource partitionSource;

    @Autowired
    public PartitionService(PartitionSource partitionSource) {
        this.partitionSource = partitionSource;
    }

    /**
     * 发送分区消息
     *
     * @param msg
     */
    public void send(String msg, int type) {
        StreamMessage streamMessage = new StreamMessage();
        streamMessage.setId(UUID.randomUUID().toString().replace("-", ""));
        streamMessage.setFrom("PartitionService");
        streamMessage.setDate(new Date());
        streamMessage.setType(type);
        streamMessage.setContent(msg);
        streamMessage.setAction(2);
        // 通过绑定的出站消息通道发送消息
        partitionSource.output().send(MessageBuilder.withPayload(streamMessage).build());
    }
}
