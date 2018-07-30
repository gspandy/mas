package com.letv.mas.client.manager.controller.service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.CloudEurekaClient;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by leeco on 18/7/18.
 */
@ConditionalOnProperty(value = "spring.cloud.eureka.client.enabled", havingValue = "true", matchIfMissing = false)
@Component
public class ManagerListener implements ApplicationListener<ApplicationEvent> {
    private static final Logger log = LoggerFactory.getLogger(ManagerListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        String curTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date(event.getTimestamp()));
        if (event instanceof InstanceRegisteredEvent) {
            InstanceRegisteredEvent instanceRegisteredEvent = (InstanceRegisteredEvent) event;
            log.info("onInstanceRegisteredEvent: time={}, source={}", curTime, instanceRegisteredEvent.toString());
        } else if (event instanceof HeartbeatEvent) {
            if (null != event.getSource() && event.getSource() instanceof CloudEurekaClient) {
                CloudEurekaClient cloudEurekaClient = (CloudEurekaClient) event.getSource();
                log.info("onHeartbeatEvent: time={}, source={}", curTime, cloudEurekaClient.toString());
            }
        } else {
            log.info("onApplicationEvent: time={}, source={}", curTime, event.getSource());
        }
    }
}
