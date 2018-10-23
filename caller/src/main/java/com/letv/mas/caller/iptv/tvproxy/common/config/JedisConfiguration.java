package com.letv.mas.caller.iptv.tvproxy.common.config;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.JedisPoolConnector;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.cache.CacheTemplate;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.cache.ICacheTemplate;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.cache.RedisJsonDao;
import org.apache.log4j.Logger;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class JedisConfiguration {

    private static final Logger logger = Logger.getLogger(JedisConfiguration.class);

    @Bean
    public JedisConfig getJedisConfig(){
        logger.info("JedisConfig init ....");
        return new JedisConfig();
    }

    @Bean
    @Primary
    @RefreshScope
    public CacheTemplate getCacheTemplate(JedisConfig jedisConfig){
        logger.info("CacheTemplate init ....");
        List<ICacheTemplate> cacheTemplates = new ArrayList<>();
        cacheTemplates.add(new RedisJsonDao(new JedisPoolConnector(jedisConfig)));
        return new CacheTemplate(cacheTemplates);
    }
}

