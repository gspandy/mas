package com.letv.mas.common.config.monitor.influxdb;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.spring.autoconfigure.MeterRegistryCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
