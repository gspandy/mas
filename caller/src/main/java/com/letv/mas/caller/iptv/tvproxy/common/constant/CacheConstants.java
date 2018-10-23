package com.letv.mas.caller.iptv.tvproxy.common.constant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 缓存相关静态文件定义
 * @author KevinYi
 */
public class CacheConstants {

    /**
     * 缓存实例，目前支持redis，redis_json，memcached，cbase
     */
    public static final String CACHE_INSTANCE_REDIS = "redis";

    public static final String CACHE_INSTANCE_REDIS_JSON = "redis_json";

    /**
     * 缓存加载失败时的提示信息
     */
    public static final String NO_CACHE_AVAILABLE_EXCEPTION_MSG = "No available cache config";

    /**
     * 缓存操作结果，0--失败，1--成功
     */
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;

    /**
     * redis sismember操作结果，1--元素存在，-1--元素不存在，0--集合不存在，
     */
    public static final int SISMEMBER_MEMBER_EXIST = 1;
    public static final int SISMEMBER_MEMBER_NOT_EXIST = -1;
    public static final int SISMEMBER_KEY_NOT_EXIST = 0;

    /**
     * 缓存数据操作日志，主要是写入和删除操作
     */
    public static final String CACHE_DATA_LOGGER_NAME = "cacheDataLog";


    /**
     * 由于线上的redis是3.2之前版本，应用读slave 被动清理（惰性清理）在slave上是不生效的，所以get前ttl 开关
     */

    public static final Boolean IS_TTL = true;


    /**
     * 对象转换为JSON字符串工具类
     */
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

}
