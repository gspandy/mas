package com.letv.mas.common.config.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Created by David.Liu on 2018/7/2.
 */
@Configuration
@ConditionalOnClass(value = {ConfigServicePropertySourceLocator.class, ContextRefresher.class})
@ConditionalOnProperty(value = {"spring.cloud.config.watcher.enabled", "spring.cloud.config.enabled"}, havingValue = "true", matchIfMissing = false)
public class ConfigClientWatcherConfiguration {

    @Resource
    ContextRefresher contextRefresher;

    @Resource
    ConfigServicePropertySourceLocator locator;

    @Bean
    public ConfigClientWatcher configClientWatcher() {
        return new ConfigClientWatcher(contextRefresher, locator);
    }

}
