package com.letv.mas.client.interceptor;

import net.devh.springboot.autoconfigure.grpc.server.GlobalServerInterceptorConfigurerAdapter;
import net.devh.springboot.autoconfigure.grpc.server.GlobalServerInterceptorRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by leeco on 18/5/17.
 */
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
