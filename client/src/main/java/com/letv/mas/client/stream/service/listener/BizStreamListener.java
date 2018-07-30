package com.letv.mas.client.stream.service.listener;

import com.letv.mas.client.stream.model.BizSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationContext;

/**
 * Created by dalvikchen on 18/7/9.
 */
@ConditionalOnProperty(value = "spring.cloud.stream.enabled", havingValue = "true", matchIfMissing = false)
@EnableBinding(BizSink.class)
public class BizStreamListener {

    private static Logger log = LoggerFactory.getLogger(BizStreamListener.class);

    @Autowired
    private ApplicationContext applicationContext;

    @ConditionalOnProperty(value = "spring.cloud.stream.bindings.mas-biz-broadcast-input.destination",  matchIfMissing = false)
    @StreamListener(BizSink.BROADCAST_INPUT)
    public void onReceivedFromBroadcast(String payload) {
        log.info("BizSink [{}] Channel received Message in appId[{}]: {}",
                BizSink.BROADCAST_INPUT, applicationContext.getId(), payload);
    }
}
