package com.letv.mas.router;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.spring.autoconfigure.MeterRegistryCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * Created by leeco on 18/4/21.
 */
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class ServiceZuulApplication {

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

    public static void main(String[] args) {
        SpringApplication.run(ServiceZuulApplication.class, args);
    }
}