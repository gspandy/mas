package com.letv.mas.client.stream.service.listener;

import com.letv.mas.common.stream.model.binding.MessageConfirmSource;
import com.letv.mas.common.stream.model.binding.MulticastSink;
import com.letv.mas.common.stream.model.dto.ConfirmMessage;
import com.letv.mas.common.stream.model.dto.StreamMessage;
import com.letv.mas.common.trace.MessageTrace;
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
 * 组播类消息监听器
 * <p>
 * 组播类消息会被每个监听该multicast-topic的消费组中的一个实例接收
 *
 * @author dalvikchen
 * @date 18/7/9
 */
@ConditionalOnProperty(value = "spring.cloud.stream.message.enabled", havingValue = "true", matchIfMissing = false)
@EnableBinding({MulticastSink.class, MessageConfirmSource.class})
public class MulticastListener {

    private static Logger logger = LoggerFactory.getLogger(MulticastListener.class);

    @Value("${server.port}")
    private String port;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MessageConfirmSource messageConfirmSource;

    @ConditionalOnProperty(value = "spring.cloud.stream.bindings.multicast_input.destination",  matchIfMissing = false)
    @StreamListener(MulticastSink.INPUT)
    @MessageTrace(spanName = "MulticastMessageSpan", tagName = "multicast", eventName = "Received Multicast Message", msgId = "${payload.id}", msg = "${payload.content}")
    public void receive(StreamMessage payload) {
        StringBuilder sb = new StringBuilder();
        sb.append("================================");
        sb.append("\nMulticast Message Received:");
        sb.append("\nApplicationId:");
        sb.append(applicationContext.getId());
        sb.append("\nMessage:");
        sb.append(payload.toString());
        sb.append("\nServer.Port:");
        sb.append(port);
        logger.info(sb.toString());

        // 发送确认消息
        ConfirmMessage confirmMessage = new ConfirmMessage();
        confirmMessage.setId(UUID.randomUUID().toString().replace("-", ""));
        confirmMessage.setMessageType("multicast");
        confirmMessage.setOriginMessage(payload);
        confirmMessage.setReceiver(applicationContext.getId() + ":" + port);
        confirmMessage.setDate(new Date());
        messageConfirmSource.output().send(MessageBuilder.withPayload(confirmMessage).build());
    }
}
