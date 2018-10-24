package com.letv.mas.client.demo;

import com.letv.mas.client.omp.model.dto.AclDto;
import com.letv.mas.client.omp.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class localRedisDemo {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    public void redisAddData(){
        List list = new ArrayList();
        AclDto acl = new AclDto();
        acl.setName("hello");
        list.add(acl);
        boolean set = redisUtil().lSet("zhangshuai6",list);
        boolean get = redisUtil().hasKey("zhangshuai6");
        long l = redisUtil().lPOP("zhangshuai6", 2);
        System.err.println(set+"--------"+l+"------"+get);
    }

    /**
     * 封装RedisTemplate
     * @return RedisUtil
     */
    private RedisUtil redisUtil(){
        RedisUtil redisUtil = new RedisUtil();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisUtil.setRedisTemplate(redisTemplate);
        return redisUtil;
    }
}
