package com.letv.mas.client.stream.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

/**
 * 消息性能测试服务类
 *
 * @author dalvikchen
 * @date 18/7/9
 */
@ConditionalOnProperty(value = "spring.cloud.stream.perf.enabled", havingValue = "true", matchIfMissing = false)
@EnableBinding(PerfSimpleService.PerfSimpleSource.class)
public class PerfSimpleService {
    private static Logger logger = LoggerFactory.getLogger(PerfSimpleService.class);


    @Autowired
    private PerfSimpleSource perfSimpleSource;

    /**
     * 发送字符串消息
     *
     * @param msg
     */
    public void sendSimple(String msg) {
        perfSimpleSource.output().send(MessageBuilder.withPayload(msg).build());
    }

    public interface PerfSimpleSource {
        String OUTPUT = "perf_simple_output";

        @Output(OUTPUT)
        MessageChannel output();
    }
}
