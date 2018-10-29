package com.letv.mas.caller.iptv.tvproxy.common.model.dao;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letv.mas.caller.iptv.tvproxy.common.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 第三方服务Dao基类
 */
public abstract class BaseTpDao {

    protected static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // allow empty string parse to null object
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    @Autowired
    protected HttpClientUtil restTemplate;

}
