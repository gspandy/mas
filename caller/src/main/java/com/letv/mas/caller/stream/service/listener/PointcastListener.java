package com.letv.mas.caller.stream.service.listener;

import com.letv.mas.common.stream.model.binding.MessageConfirmSource;
import com.letv.mas.common.stream.model.binding.PointcastSink;
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
 * 点播消息监听器
 * <p>
 * 某些场景下需要将消息全部发送到某一个消费实例，可以在生产者中配置分区key以及分区选择策略实现
 *
 * @author dalvikchen
 * @date 18/7/9
 */
@EnableBinding({PointcastSink.class, MessageConfirmSource.class})
@ConditionalOnProperty(value = "spring.cloud.stream.message.enabled", havingValue = "true", matchIfMissing = false)
public class PointcastListener {

    private static Logger logger = LoggerFactory.getLogger(PointcastListener.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${server.port}")
    private String port;

    @Value("${spring.cloud.stream.instanceCount}")
    private String instanceCount;

    @Value("${spring.cloud.stream.instanceIndex}")
    private String instanceIndex;

    @Autowired
    private MessageConfirmSource messageConfirmSource;

    @ConditionalOnProperty(value = "spring.cloud.stream.bindings.pointcast_input.destination",  matchIfMissing = false)
    @StreamListener(PointcastSink.INPUT)
    @MessageTrace(spanName = "PointcastMessageSpan", tagName = "pointcast", eventName = "Received Pointcast Message", msgId = "${payload.id}", msg = "${payload.content}")
    public void receive(StreamMessage payload) {
        StringBuilder sb = new StringBuilder();
        sb.append("===============================");
        sb.append("\nPointcast Message Received:");
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
        confirmMessage.setMessageType("pointcast");
        confirmMessage.setOriginMessage(payload);
        confirmMessage.setReceiver(applicationContext.getId() + ":" + port);
        confirmMessage.setDate(new Date());
        messageConfirmSource.output().send(MessageBuilder.withPayload(confirmMessage).build());
    }
}
