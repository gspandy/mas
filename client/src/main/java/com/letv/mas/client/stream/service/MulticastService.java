package com.letv.mas.client.stream.service;

import com.letv.mas.common.stream.model.binding.MulticastSource;
import com.letv.mas.common.stream.model.dto.StreamMessage;
import com.letv.mas.common.trace.MessageTrace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Date;

/**
 * 组播消息发送服务类
 * <p>
 *
 * @author dalvikchen
 * @date 18/7/9
 */
@ConditionalOnProperty(value = "spring.cloud.stream.message.enabled", havingValue = "true", matchIfMissing = false)
@EnableBinding(MulticastSource.class)
public class MulticastService {
    private static Logger logger = LoggerFactory.getLogger(MulticastService.class);

    private MulticastSource multicastSource;

    @Autowired
    public MulticastService(MulticastSource multicastSource) {
        this.multicastSource = multicastSource;
    }

    /**
     * 发送组播消息
     *
     * @param msg
     */
    @MessageTrace(spanName = "MulticastMessageSpan", tagName = "multicast", eventName = "Multicast Message", msgId = "${msgId}", msg = "${msg}")
    public void send(String msgId, String msg) {
        StreamMessage streamMessage = new StreamMessage();
        streamMessage.setId(msgId);
        streamMessage.setFrom("MulticastService");
        streamMessage.setDate(new Date());
        streamMessage.setType(1);
        streamMessage.setContent(msg);
        streamMessage.setAction(1);
        // 通过绑定的出站消息通道发送消息
        multicastSource.output().send(MessageBuilder.withPayload(streamMessage).build());
    }
}
