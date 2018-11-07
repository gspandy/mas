package com.letv.mas.caller.iptv.tvproxy.common.config;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.HttpClientFactoryBean;
import com.letv.mas.caller.iptv.tvproxy.common.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@Configuration
public class HttpClientConfiguration {

    private static final Logger logger = Logger.getLogger(HttpClientConfiguration.class);

    @Bean
    RestConfig getRestConfig() {
        logger.info("RestConfig init ....");
        return new RestConfig();
    }

    @Bean
    @RefreshScope
    @Qualifier("restTemplate")
    HttpClientUtil getHttpClientUtil(RestConfig config) {
        logger.info("HttpClientUtil init ...."+config);
        try {
            HttpClientFactoryBean httpClientFactoryBean = new HttpClientFactoryBean(config);
            HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClientFactoryBean.getObject());
            return new HttpClientUtil(httpRequestFactory);
        } catch (Exception e) {
            e.printStackTrace();
            //throw new LetvCommonException();
        }
        return null;
    }
}
