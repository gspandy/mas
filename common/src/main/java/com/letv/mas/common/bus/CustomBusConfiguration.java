package com.letv.mas.common.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.bus.ConditionalOnBusEnabled;
import org.springframework.cloud.bus.endpoint.BusEndpoint;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dalvikchen
 * @date 18/5/28
 */
@Configuration
@ConditionalOnBusEnabled
@RemoteApplicationEventScan
public class CustomBusConfiguration {

    @Bean
    @ConditionalOnProperty(value = "endpoints.spring.cloud.bus.pub.enabled", matchIfMissing = true)
    public CustomBusEndpoint pubBusEndpoint(ApplicationContext context,
                                            BusEndpoint busEndpoint) {
        return new CustomBusEndpoint(context, context.getId(), busEndpoint);
    }

    @Bean
    @ConditionalOnProperty(value = "spring.cloud.bus.pub.enabled", matchIfMissing = true)
    @ConditionalOnMissingBean(value = CustomBusListener.class)
    @Autowired
    public CustomBusListener busListener(ApplicationContext applicationContext) {
        return new CustomBusListener(applicationContext);
    }
}
