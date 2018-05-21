package com.letv.mas.caller.interceptor;

import net.devh.springboot.autoconfigure.grpc.client.GlobalClientInterceptorConfigurerAdapter;
import net.devh.springboot.autoconfigure.grpc.client.GlobalClientInterceptorRegistry;
import net.devh.springboot.autoconfigure.grpc.client.MetadataInjector;
import net.devh.springboot.autoconfigure.grpc.client.TraceClientInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Created by leeco on 18/5/17.
 */
@Order(Ordered.LOWEST_PRECEDENCE)
@Configuration
public class GlobalInterceptorConfiguration {
    @Bean
    public GlobalClientInterceptorConfigurerAdapter globalInterceptorConfigurerAdapter() {

        return new GlobalClientInterceptorConfigurerAdapter() {

            @Autowired(required = false)
            org.springframework.cloud.sleuth.Tracer tracer;

            @Override
            public void addClientInterceptors(GlobalClientInterceptorRegistry registry) {
                if (null != tracer) {
                    registry.addClientInterceptors(new TraceClientInterceptor((Tracer) tracer, new MetadataInjector()));
                }
                registry.addClientInterceptors(new GrpcInterceptor());
            }
        };
    }
}
