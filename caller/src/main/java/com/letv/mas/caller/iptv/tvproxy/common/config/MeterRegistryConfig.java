package com.letv.mas.caller.iptv.tvproxy.common.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.cache.CacheMeterBinder;
import io.micrometer.core.instrument.binder.hystrix.HystrixMetricsBinder;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.binder.system.UptimeMetrics;
import io.micrometer.spring.autoconfigure.MeterRegistryCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by leeco on 18/9/4.
 */
@Configuration
@ConditionalOnProperty(value = "management.metrics.export.influx.enabled", havingValue = "true", matchIfMissing = false)
public class MeterRegistryConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.application.index}")
    private String applicationIndex;

    @Bean
    @Primary
    MeterRegistryCustomizer<MeterRegistry> metricsConfig() {
        return registry -> {
            registry.config().commonTags("serviceName", applicationName);
            registry.config().commonTags("serviceIndex", applicationIndex);
            new JvmMemoryMetrics().bindTo(registry);
            new JvmGcMetrics().bindTo(registry);
            new ProcessorMetrics().bindTo(registry);
            new JvmThreadMetrics().bindTo(registry);
        };
    }

    /*@Bean
    @Primary
    MeterRegistryCustomizer<MeterRegistry> metricsConfig(CustomMetrics customMetrics) {
        return registry -> {
            registry.config().commonTags("serviceName", applicationName);
            registry.config().commonTags("serviceIndex", applicationIndex);
            new JvmMemoryMetrics().bindTo(registry);
            new JvmGcMetrics().bindTo(registry);
            new ProcessorMetrics().bindTo(registry);
            new JvmThreadMetrics().bindTo(registry);
            new HystrixMetricsBinder().bindTo(registry);
            new UptimeMetrics().bindTo(registry);
            customMetrics.bindTo(registry);
        };
    }

    @Bean
    CustomMetrics getCustomMetrics(){
        return new CustomMetrics();
    }*/
}
