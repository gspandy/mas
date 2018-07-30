package com.letv.mas.caller.stream.service.listener;

import com.letv.mas.common.stream.model.binding.MessageConfirmSource;
import com.letv.mas.common.stream.model.binding.MulticastSink;
import com.letv.mas.common.stream.model.binding.PartitionSink;
import com.letv.mas.common.stream.model.dto.ConfirmMessage;
import com.letv.mas.common.stream.model.dto.StreamMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Date;
import java.util.UUID;

/**
 * 分区消息监听器
 * <p>
 * 消息在生产时会根据消息的特性或者头信息进行分区，消费组开启接收分区消息时，每个消费者实例会接收不同的消息。
 * 消费实例需配置instanceCount和instanceIndex
 *
 * @author dalvikchen
 * @date 18/7/9
 */
@EnableBinding({PartitionSink.class, MessageConfirmSource.class})
@ConditionalOnProperty(value = "spring.cloud.stream.message.enabled", havingValue = "true", matchIfMissing = false)
public class PartitionListener {

    private static Logger logger = LoggerFactory.getLogger(PartitionListener.class);

    @Value("${server.port}")
    private String port;

    @Value("${spring.cloud.stream.instanceCount}")
    private String instanceCount;

    @Value("${spring.cloud.stream.instanceIndex}")
    private String instanceIndex;

    @Autowired
    private MessageConfirmSource messageConfirmSource;

    @Autowired
    private ApplicationContext applicationContext;

    @ConditionalOnProperty(value = "spring.cloud.stream.bindings.partition_input.destination",  matchIfMissing = false)
    @StreamListener(PartitionSink.INPUT)
    public void receive(StreamMessage payload) {
        StringBuilder sb = new StringBuilder();
        sb.append("===============================");
        sb.append("\nPartition Message Received:");
        sb.append("\nApplicationId:");
        sb.append(applicationContext.getId());
        sb.append("\nMessage:");
        sb.append(payload.toString());
        sb.append("\nServer.Port:");
        sb.append(port);
        sb.append("\nInstanceCount:");
        sb.append(instanceCount);
        sb.append("\nInstanceIndex:");
        sb.append(instanceIndex);
        logger.info(sb.toString());

        // 发送确认消息
        ConfirmMessage confirmMessage = new ConfirmMessage();
        confirmMessage.setId(UUID.randomUUID().toString().replace("-", ""));
        confirmMessage.setMessageType("partition");
        confirmMessage.setOriginMessage(payload);
        confirmMessage.setReceiver(applicationContext.getId() + ":" + port);
        confirmMessage.setDate(new Date());
        messageConfirmSource.output().send(MessageBuilder.withPayload(confirmMessage).build());
    }
}
