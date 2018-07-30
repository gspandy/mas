package com.letv.mas.client.stream.service;

import com.letv.mas.client.stream.model.BizSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 多通道发送，定时任务发送
 * <p/>
 * Created by dalvikchen on 18/6/11.
 */
@ConditionalOnProperty(value = "spring.cloud.stream.enabled", havingValue = "true", matchIfMissing = false)
@EnableBinding(BizSource.class)
public class BizStreamSender {
    private static final Logger log = LoggerFactory.getLogger(BizStreamSender.class);

    /**
     * 定时发送消息
     *
     * @return
     */
    @ConditionalOnProperty(value = "spring.cloud.stream.bindings.mas-biz-broadcast-output.destination", matchIfMissing = false)
    @Bean
    @InboundChannelAdapter(value = BizSource.BROADCAST_OUTPUT, poller = @Poller(fixedDelay = "30", maxMessagesPerPoll = "10"))
    public synchronized MessageSource<String> onTimerBroadcastSend() {
        return () -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Message From ")
                    .append(BizSource.class.getName())
                    .append(" [")
                    .append(BizSource.BROADCAST_OUTPUT)
                    .append("] Channel at ")
                    .append((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
            log.info("BizSource  [{}] Channel Sending Message: {}", BizSource.BROADCAST_OUTPUT, sb.toString());
            return new GenericMessage(sb.toString());
        };
    }
}
