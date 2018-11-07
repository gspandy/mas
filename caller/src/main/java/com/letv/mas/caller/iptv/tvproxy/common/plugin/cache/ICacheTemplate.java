package com.letv.mas.caller.iptv.tvproxy.common.plugin.cache;


import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 缓存操作模板类，定义支持缓存数据操作行为；
 * @author KevinYi
 */
public interface ICacheTemplate {

    /**
     * 写数据
     * @param key
     * @param value
     * @return
     */
    public int set(String key, Object value);

    /**
     * 写数据,可以设置超时时间
     * @param key
     * @param value
     * @param duration
     *            单位-秒
     * @return
     */
    public int set(String key, Object value, int duration);

    /**
     * 一次写多条数据
     * @param valueMap
     * @return
     */
    public <T> int mset(Map<String, T> valueMap);

    /**
     * 一次写多条数据,可以设置超时时间
     * @param valueMap
     * @param duration
     *            单位-秒
     * @return
     */
    public <T> int mset(Map<String, T> valueMap, int duration);

    /**
     * 获取key对应的缓存数据；其中，针对数据存储和获取过程中，对类型信息的要求不用；如果使用Java序列化存取数据，
     * 则使用Class即可；如果使用org.codehaus.jackson.map.ObjectMapper存取数据，则针对普通Object
     * （包括Java
     * Object，如Integer，String等），仅使用Class即可，但对于包含Collection、Map或自定义封装（如显而易见的A
     * <B>）等封装类型，则需要使用TypeReference<T>才能正常解析，参见get(String key,
     * LetvTypeReference<T> typeReference)；
     * @param key
     * @param clazz
     * @return
     */
    public <T> T get(String key, Class<T> clazz);

    /**
     * 获取key对应的缓存数据；其中，存储的数据是包含Collection、Map等封装类型，而不能是普通Object
     * （包括Java Object，如Integer， String等）；
     * @param key
     * @param typeReference
     * @return
     */
    public <T> T get(String key, LetvTypeReference<T> typeReference);



    /**
     * 一次性读多个key；其中，针对数据存储和获取过程中，对类型信息的要求不用；如果使用Java序列化存取数据，
     * 则使用Class即可；如果使用org.codehaus.jackson.map.ObjectMapper存取数据，则针对普通Object
     * （包括Java Object，如Integer，
     * String等），仅使用Class即可，但对于包含Collection、Map或自定义封装（如显而易见的A
     * <B>）等封装类型，则需要使用TypeReference<T>才能正常解析，参见get(String key,
     * LetvTypeReference<T>
     * typeReference)；
     * @param keys
     * @param c
     * @return
     */
    public <T> Map<String, T> mget(List<String> keys, Class<T> c);

    public <T> Map<String, T> mget(List<String> keys, Class<T> c, int batchSize);

    /**
     * 一次性读多个key；其中，存储的数据是包含Collection、Map等封装类型，而不能是普通Object
     * （包括Java Object，如Integer， String等）；
     * @param keys
     * @param typeReference
     * @return
     */
    public <T> Map<String, T> mget(List<String> keys, LetvTypeReference<T> typeReference);

    /**
     * 删除key
     * @param key
     * @return
     */
    public int delete(String key);

    /**
     * 确定某个元素是一个集合的成员
     * @param key
     * @param member
     * @return 1：存在 -1：不存在 0：无该集合
     */
    public int sismember(String key, String member);

    /**
     * 创建一个以key为键的集合
     * @param key
     * @param member
     * @return 1：存在 -1：不存在 0：无该集合
     */
    public int sadd(String key, Set<String> member);

}
