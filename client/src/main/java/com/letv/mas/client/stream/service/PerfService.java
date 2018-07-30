package com.letv.mas.client.stream.service;

import com.letv.mas.common.stream.model.dto.StreamMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Date;
import java.util.UUID;

/**
 * 消息性能测试服务类
 *
 * @author dalvikchen
 * @date 18/7/9
 */
@ConditionalOnProperty(value = "spring.cloud.stream.perf.enabled", havingValue = "true", matchIfMissing = false)
@EnableBinding(PerfService.PerfSource.class)
public class PerfService {
    private static Logger logger = LoggerFactory.getLogger(PerfService.class);

    private PerfSource perfSource;

    @Autowired
    public PerfService(PerfSource perfSource) {
        this.perfSource = perfSource;
    }

    /**
     * 发送消息
     *
     * @param msg
     */
    public void send(String msg) {
        StreamMessage streamMessage = new StreamMessage();
        streamMessage.setId(UUID.randomUUID().toString().replace("-", ""));
        streamMessage.setFrom("PerfService");
        streamMessage.setDate(new Date());
        streamMessage.setType(0);
        streamMessage.setContent(msg);
        streamMessage.setAction(0);
        // 通过绑定的出站消息通道发送消息
        perfSource.output().send(MessageBuilder.withPayload(streamMessage).build());
    }

    public interface PerfSource {
        String OUTPUT = "perf_output";

        @Output(OUTPUT)
        MessageChannel output();
    }
}
