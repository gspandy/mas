package com.letv.mas.caller.stream.service.listener;

import com.letv.mas.common.stream.model.dto.StreamMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.SubscribableChannel;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 消息监听器-性能测试
 * <p>
 *
 * @author dalvikchen
 * @date 18/7/9
 */
@EnableBinding({PerfListener.PerfSink.class})
@ConditionalOnProperty(value = "spring.cloud.stream.perf.enabled", havingValue = "true", matchIfMissing = false)
public class PerfListener {

    private static Logger logger = LoggerFactory.getLogger(PerfListener.class);

    @Value("${server.port}")
    private String port;

    @Value("${spring.cloud.stream.instanceCount}")
    private String instanceCount;

    @Value("${spring.cloud.stream.instanceIndex}")
    private String instanceIndex;

    @Autowired
    private ApplicationContext applicationContext;

    @ConditionalOnProperty(value = "spring.cloud.stream.bindings.perf_input.destination", matchIfMissing = false)
    @StreamListener(PerfSink.INPUT)
    public void receive(StreamMessage payload) {
        StringBuilder sb = new StringBuilder();
        sb.append("===============================");
        sb.append("\nPerformance Message Received:");
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


        /**
         * 模拟消费真实场景，sleep一定时间
         */
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info(sb.toString());
    }

    public interface PerfSink {
        String INPUT = "perf_input";

        @Input(INPUT)
        SubscribableChannel input();
    }

    public static void main(String[] args) {
    }
}
