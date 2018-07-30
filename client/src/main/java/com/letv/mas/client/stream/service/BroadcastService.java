package com.letv.mas.client.stream.service;

import com.letv.mas.common.stream.model.binding.BroadcastSource;
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
 * 广播消息服务类
 *
 * @author dalvikchen
 * @date 18/7/9
 */
@ConditionalOnProperty(value = "spring.cloud.stream.message.enabled", havingValue = "true", matchIfMissing = false)
@EnableBinding(BroadcastSource.class)
public class BroadcastService {
    private static Logger logger = LoggerFactory.getLogger(BroadcastService.class);

    private BroadcastSource broadcastSource;

    @Autowired
    public BroadcastService(BroadcastSource broadcastSource) {
        this.broadcastSource = broadcastSource;
    }

    /**
     * 发送广播消息
     *
     * @param msg
     */
    public void send(String msg) {
        StreamMessage streamMessage = new StreamMessage();
        streamMessage.setId(UUID.randomUUID().toString().replace("-", ""));
        streamMessage.setFrom("BroadcastService");
        streamMessage.setDate(new Date());
        streamMessage.setType(0);
        streamMessage.setContent(msg);
        streamMessage.setAction(0);
        // 通过绑定的出站消息通道发送消息
        broadcastSource.output().send(MessageBuilder.withPayload(streamMessage).build());
    }
}
