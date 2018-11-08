package com.letv.mas.router.iptv.tvproxy.config;

import com.letv.mas.router.iptv.tvproxy.interceptor.CheckLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by leeco on 18/11/6.
 */
@ComponentScan(basePackages = "com.letv.mas.router.iptv.tvproxy.*", excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@Configuration
@EnableWebMvc
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @SuppressWarnings("unused")
    public InterceptorConfig() {
    }

    @Bean
    public CheckLoginInterceptor checkLoginInterceptor() {
        return new CheckLoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkLoginInterceptor()).addPathPatterns(new String[]{"/i/auth/**"});
        super.addInterceptors(registry);
    }
}