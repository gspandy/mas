package com.letv.mas.caller.iptv.tvproxy.user.config;

import com.letv.mas.caller.iptv.tvproxy.user.annotation.Iptv;
import com.letv.mas.caller.iptv.tvproxy.user.model.dao.cache.CacheTemplate;
import com.letv.mas.caller.iptv.tvproxy.user.model.dao.cache.ICacheTemplate;
import com.letv.mas.caller.iptv.tvproxy.user.plugin.JedisPoolConnector;
import com.letv.mas.caller.iptv.tvproxy.user.model.dao.cache.RedisJsonDao;
import org.apache.log4j.Logger;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Iptv
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

