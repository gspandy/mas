package com.letv.mas.client.omp.service;

import com.letv.mas.client.omp.model.dao.SSOLoginMapper;
import com.letv.mas.client.omp.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UnifiedCallbackService {

    @Resource
    private SSOLoginMapper ssoLoginMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public RedisUtil redisUtil(){
        RedisUtil redisUtil = new RedisUtil();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisUtil.setRedisTemplate(redisTemplate);
        return redisUtil;
    }

    public void reloadAllAcl() {
        redisUtil().lPOP("AllAcl", redisUtil().lGet("AllAcl", 0, -1).size());
        List allAcl = ssoLoginMapper.findAllAcls();
        redisUtil().lSet("AllAcl",allAcl);
        redisUtil().expire("AllAcl", 86400);
    }

}
