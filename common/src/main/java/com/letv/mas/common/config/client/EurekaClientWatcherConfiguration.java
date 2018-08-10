package com.letv.mas.common.config.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties
@ConditionalOnClass(EurekaClientConfigBean.class)
@ConditionalOnProperty(value = {"spring.cloud.eureka.watcher.enabled"}, havingValue = "true", matchIfMissing = false)
public class EurekaClientWatcherConfiguration {

    @Bean
    public EurekaClientWatcher eurekaClientWatcher() {
        return new EurekaClientWatcher();
    }

}
