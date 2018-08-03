package com.letv.mas.client.stream.service.listener;

import com.letv.mas.common.stream.model.binding.BroadcastSink;
import com.letv.mas.common.stream.model.binding.MessageConfirmSource;
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
 * 广播类消息监听器
 * <p>
 * 广播类消息会被所有监听该broadcast-topic的消费者接收到
 *
 * @author dalvikchen
 * @date 18/7/9
 */
@ConditionalOnProperty(value = "spring.cloud.stream.message.enabled", havingValue = "true", matchIfMissing = false)
@EnableBinding({BroadcastSink.class, MessageConfirmSource.class})
public class BroadcastListener {

    private static Logger logger = LoggerFactory.getLogger(BroadcastListener.class);

    @Value("${server.port}")
    private String port;

    @Autowired
    private MessageConfirmSource messageConfirmSource;

    @Autowired
    private ApplicationContext applicationContext;

    @ConditionalOnProperty(value = "spring.cloud.stream.bindings.broadcast_input.destination",  matchIfMissing = false)
    @StreamListener(BroadcastSink.INPUT)
    @MessageTrace(spanName = "BroadcastMessageSpan", tagName = "broadcast", eventName = "Received Broadcast Message", msgId = "${payload.id}", msg = "${payload.content}")
    public void receive(StreamMessage payload) {
        StringBuilder sb = new StringBuilder();
        sb.append("================================");
        sb.append("\nBroadcast Message Received:");
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
        confirmMessage.setMessageType("broadcast");
        confirmMessage.setOriginMessage(payload);
        confirmMessage.setReceiver(applicationContext.getId() + ":" + port);
        confirmMessage.setDate(new Date());
        messageConfirmSource.output().send(MessageBuilder.withPayload(confirmMessage).build());
    }
}
