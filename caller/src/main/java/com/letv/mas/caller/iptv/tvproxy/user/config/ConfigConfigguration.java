package com.letv.mas.caller.iptv.tvproxy.user.config;

import com.letv.mas.caller.iptv.tvproxy.user.annotation.Iptv;
import com.letv.mas.caller.iptv.tvproxy.user.util.ConfigOperationUtil;
import com.letv.mas.caller.iptv.tvproxy.user.util.MessageUtils;
import org.apache.log4j.Logger;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

@Configuration
@Iptv
public class ConfigConfigguration {
    private static final Logger logger = Logger.getLogger(ConfigConfigguration.class);

    @Bean
    ConfigOperationUtil getConfigOperationUtil() {
        logger.info(" ConfigBean init .... ");
        ConfigOperationUtil configOperationUtil = new ConfigOperationUtil();
        return configOperationUtil;
    }


    @Bean
    @Primary
    @RefreshScope
    public MessageSource initMessageSource() {

        logger.info(" MessageSource init .... ");

        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename("messages/Message_i18n");

        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());

        messageSource.setCacheMillis(-1);

        MessageUtils.init(messageSource);

        return messageSource;

    }
}
