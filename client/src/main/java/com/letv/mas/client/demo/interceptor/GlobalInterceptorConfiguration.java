package com.letv.mas.client.demo.interceptor;

import net.devh.springboot.autoconfigure.grpc.server.GlobalServerInterceptorConfigurerAdapter;
import net.devh.springboot.autoconfigure.grpc.server.GlobalServerInterceptorRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by leeco on 18/5/17.
 */
@ConditionalOnProperty(value = "grpc.server.port", matchIfMissing = false)
@Configuration
public class GlobalInterceptorConfiguration {
    @Bean
    public GlobalServerInterceptorConfigurerAdapter globalInterceptorConfigurerAdapter() {
        return new GlobalServerInterceptorConfigurerAdapter() {
            @Override
            public void addServerInterceptors(GlobalServerInterceptorRegistry registry) {
                registry.addServerInterceptors(new GrpcInterceptor());
            }
        };
    }
}
