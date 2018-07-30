package com.letv.mas.caller.stream.service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.SubscribableChannel;

/**
 * 简单类消息监听器-性能测试
 * <p>
 *
 * @author dalvikchen
 * @date 18/7/9
 */
@EnableBinding({PerfSimpleListener.PerfSimpleSink.class})
@ConditionalOnProperty(value = "spring.cloud.stream.perf.enabled", havingValue = "true", matchIfMissing = false)
public class PerfSimpleListener {

    private static Logger logger = LoggerFactory.getLogger(PerfSimpleListener.class);

    @ConditionalOnProperty(value = "spring.cloud.stream.bindings.perf_simple_input.destination", matchIfMissing = false)
    @StreamListener(PerfSimpleSink.INPUT)
    public void receive(String payload) {
        logger.info(payload);
    }

    public interface PerfSimpleSink {
        String INPUT = "perf_simple_input";

        @Input(INPUT)
        SubscribableChannel input();
    }
}
