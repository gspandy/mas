package com.letv.mas.caller.iptv.tvproxy.apicommon.config;

import com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor.AuthorizedInterceptor;
import com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor.CheckLoginInterceptor;
import com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor.HttpParameterInterceptor;
import com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor.HttpResponseInterceptor;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    private static final Logger logger = Logger.getLogger(WebMvcConfig.class);

    @Bean
    AuthorizedInterceptor getAuthorizedInterceptor(){
        return new AuthorizedInterceptor();
    }
    @Bean
    CheckLoginInterceptor getCheckLoginInterceptor(){
        return new CheckLoginInterceptor();
    }
    @Bean
    HttpParameterInterceptor getHttpParameterInterceptor(){
        return new HttpParameterInterceptor();
    }
    @Bean
    HttpResponseInterceptor getHttpResponseInterceptor(){
        return new HttpResponseInterceptor();
    }

    /*@Bean
    MACBlacklistInterceptor getMACBlacklistInterceptor(UserService userService, CacheTemplate cacheTemplate){
        return new MACBlacklistInterceptor(userService,cacheTemplate);
    }*/


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthorizedInterceptor())    //指定拦截器类
                .addPathPatterns("/iptv/api/**");
        registry.addInterceptor(getCheckLoginInterceptor())
                .addPathPatterns("/iptv/api/**");
        registry.addInterceptor(getHttpParameterInterceptor())
                .addPathPatterns("/iptv/api/**");
        registry.addInterceptor(getHttpResponseInterceptor())
                .addPathPatterns("/iptv/api/**");
        super.addInterceptors(registry);
    }
}
