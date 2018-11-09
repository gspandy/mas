package com.letv.mas.caller.iptv.tvproxy.common.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoAccess;
import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoAccessAdapter;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.activity.ActivityTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvExceptionResolver;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class ConfigConfigguration {
    private static final Logger logger = Logger.getLogger(ConfigConfigguration.class);

    @Bean
    ApplicationUtils getConfigOperationUtil() {
        logger.info(" ConfigBean init .... ");
        ApplicationUtils configOperationUtil = new ApplicationUtils();
        return configOperationUtil;
    }

    @Bean
    LetvExceptionResolver getLetvExceptionResolver(){
        return new LetvExceptionResolver();
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

    //@Bean
    public AlbumVideoAccess getAlbumVideoAccessAdapter(){
         return new AlbumVideoAccessAdapter();
    }

    /*@ApolloConfigChangeListener(value = "config.properties")
    public void onChangePubConfig(ConfigChangeEvent changeEvent) {
        for (String changedKey : changeEvent.changedKeys()) {
            logger.info(" ---onChangePubConfig--- "+changeEvent.getChange(changedKey));
            ApplicationUtils.changeValue(changedKey,changeEvent.getChange(changedKey).getNewValue());
        }
    }
    @ApolloConfigChangeListener(value = "httpurl.properties")
    public void onChangePubHttpUrl(ConfigChangeEvent changeEvent) {
        for (String changedKey : changeEvent.changedKeys()) {
            logger.info(" ---onChangePubHttpUrl--- "+changeEvent.getChange(changedKey));
            ApplicationUtils.changeValue(changedKey,changeEvent.getChange(changedKey).getNewValue());
        }
    }*/


    @ApolloConfigChangeListener(value = "guanxing.properties")
    public void onChangePubGXing(ConfigChangeEvent changeEvent) {
        for (String changedKey : changeEvent.changedKeys()) {
            logger.info(" ---onChangePubGXing--- "+changeEvent.getChange(changedKey));
            ActivityTpConstant.changeValue(changedKey,changeEvent.getChange(changedKey).getNewValue());
        }
    }

    public static void init() {
        /*Config config = ConfigService.getConfig("config");
        Set<String> keys = config.getPropertyNames();
        for (String changedKey : keys) {
            ApplicationUtils.changeValue(changedKey, config.getProperty(changedKey, null));
        }
        config = ConfigService.getConfig("httpurl");
        keys = config.getPropertyNames();
        for (String changedKey : keys) {
            ApplicationUtils.changeValue(changedKey, config.getProperty(changedKey, null));
        }*/
        Config config = ConfigService.getConfig("guanxing");
        Set<String> keys = config.getPropertyNames();
        for (String changedKey : keys) {
            ActivityTpConstant.changeValue(changedKey, config.getProperty(changedKey, null));
        }
    }

}
