package com.letv.mas.caller.iptv.tvproxy.common.plugin.cache;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheConstants;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.JedisPoolConnector;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.SessionCache;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis缓存操作类，存数数据格式为json字符串
 * @author KevinYi
 */
/*@org.springframework.cloud.context.config.annotation.RefreshScope
@Lazy*/
//@Component
public class RedisJsonDao implements ICacheTemplate {

    private static Logger logger = LoggerFactory.getLogger(RedisJsonDao.class);
    // 缓存数据操作日志，主要是写入和删除操作
    private static Log CACHE_DATA_LOGGER = LogFactory.getLog(CacheConstants.CACHE_DATA_LOGGER_NAME);

    //@Autowired(required = false)
    //private SessionCache sessionCache = null;

    // protected ObjectMapper objectMapper = new ObjectMapper();
    // public RedisJsonDao() {
    // CacheConstants.OBJECT_MAPPER.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
    // false);
    // CacheConstants.OBJECT_MAPPER.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
    // CacheConstants.OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,
    // true);
    // }

    @Autowired
    private JedisPoolConnector jedisPoolConnector = null;

    public RedisJsonDao() {
        super();
    }

    public RedisJsonDao(JedisPoolConnector jedisPoolConnector) {
        super();
        this.jedisPoolConnector = jedisPoolConnector;
        //SessionCache.getSession() = sessionCache;
    }

    public JedisPoolConnector getJedisPoolConnector() {
        return this.jedisPoolConnector;
    }

    @Override
    public int set(String key, Object value) {
        String logPrefix = CacheConstants.CACHE_INSTANCE_REDIS_JSON + "|set|key=" + key;
        int result = CacheConstants.FAIL;
        if (!StringUtil.isBlank(key)) {
            if (value == null) {
                logger.info(logPrefix + "|result=-1|msg=null value, will do delete!");
                return delete(key);
            } else {
                String jsonValue = null;
                long begin = System.currentTimeMillis();
                if (value instanceof String) {
                    jsonValue = (String) value;
                } else {
                    try {
                        jsonValue = CacheConstants.OBJECT_MAPPER.writeValueAsString(value);
                    } catch (Exception e) {
                        logger.error(logPrefix + "|result=0|errMsg=writeValueAsString error!", e);
                    }
                }

                if (jsonValue != null) {
                    Jedis jedis = null;
                    try {
                        jedis = jedisPoolConnector.getMasterJedis();

                        jedis.set(key, jsonValue);
                        result = CacheConstants.SUCCESS;

                        String logInfo = logPrefix + "|result=1|timeCost=" + (System.currentTimeMillis() - begin);
                        if (null != SessionCache.getSession()) {
                            SessionCache.getSession().setResponse("redis://", jsonValue, logInfo);
                        }
                        CACHE_DATA_LOGGER.info(logInfo);
                    } catch (Exception e) {
                        String logInfo = logPrefix + "|result=0|timeCost="
                                + (System.currentTimeMillis() - begin) + "|errMsg=set error.";
                        if (null != SessionCache.getSession()) {
                            SessionCache.getSession().setResponse("redis://", jsonValue, logInfo);
                        }
                        CACHE_DATA_LOGGER.error(logInfo, e);

                        if (jedis != null) {
                            try {
                                jedisPoolConnector.returnMasterBrokenResourceObject(jedis);
                            } catch (Exception e1) {
                                CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return master broken resource error.", e1);
                            }

                            if (e instanceof JedisConnectionException) {
                                if (!jedisPoolConnector.checkPoolIsAlive(jedisPoolConnector.POOL_TYPE_MASTER)) {
                                    jedisPoolConnector.restartMaster();
                                }
                            }

                            jedis = null;
                        }
                    } finally {
                        if (jedis != null && result == CacheConstants.SUCCESS) {
                            try {
                                jedisPoolConnector.returnMasterResource(jedis);
                            } catch (Exception e) {
                                CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return master resource error.", e);
                            }
                        }
                    }
                }
            }
        } else {
            logger.error(logPrefix + "|result=0|errMsg=illegal param.");
        }
        return result;
    }

    @Override
    public int set(String key, Object value, int duration) {
        String logPrefix = CacheConstants.CACHE_INSTANCE_REDIS_JSON + "|set|duration=" + duration + "|key=" + key;
        int result = CacheConstants.FAIL;
        // 操作之前，需要检查是否正常初始化
        if (!StringUtil.isBlank(key) && duration >= 0) {
            if (value == null) {
                logger.info(logPrefix + "|result=-1|msg=null value, will do delete!");
                return delete(key);
            } else {
                long begin = System.currentTimeMillis();
                String jsonValue = null;

                if (value instanceof String) {
                    jsonValue = (String) value;
                } else {
                    try {
                        jsonValue = CacheConstants.OBJECT_MAPPER.writeValueAsString(value);
                    } catch (Exception e) {
                        logger.error(logPrefix + "|result=0|errMsg=writeValueAsString error!", e);
                    }
                }

                if (jsonValue != null) {
                    Jedis jedis = null;
                    try {
                        jedis = jedisPoolConnector.getMasterJedis();
                        jedis.setex(key, duration, jsonValue);
                        result = CacheConstants.SUCCESS;

                        String logInfo = logPrefix + "|result=1|timeCost=" + (System.currentTimeMillis() - begin);
                        if (null != SessionCache.getSession()) {
                            SessionCache.getSession().setResponse("redis://", jsonValue, logInfo);
                        }
                        CACHE_DATA_LOGGER.info(logInfo);
                    } catch (Exception e) {
                        String logInfo = logPrefix + "|result=0|timeCost="
                                + (System.currentTimeMillis() - begin) + "|errMsg=set error.";
                        if (null != SessionCache.getSession()) {
                            SessionCache.getSession().setResponse("redis://", jsonValue, logInfo);
                        }
                        CACHE_DATA_LOGGER.error(logInfo, e);

                        if (jedis != null) {
                            try {
                                jedisPoolConnector.returnMasterBrokenResourceObject(jedis);
                            } catch (Exception e1) {
                                CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return master broken resource error.", e1);
                            }

                            if (e instanceof JedisConnectionException) {
                                if (!jedisPoolConnector.checkPoolIsAlive(jedisPoolConnector.POOL_TYPE_MASTER)) {
                                    jedisPoolConnector.restartMaster();
                                }
                            }

                            jedis = null;
                        }
                    } finally {
                        if (jedis != null && result == CacheConstants.SUCCESS) {
                            try {
                                jedisPoolConnector.returnMasterResource(jedis);
                            } catch (Exception e) {
                                CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return master resource error.", e);
                            }
                        }
                    }
                }
            }
        } else {
            logger.error(logPrefix + "|result=0|errMsg=illegal parameter.");
        }
        return result;
    }

    @Override
    public <T> int mset(Map<String, T> valueMap) {
        String logPrefix = CacheConstants.CACHE_INSTANCE_REDIS_JSON + "|mset|";
        int result = CacheConstants.FAIL;
        if (!CollectionUtils.isEmpty(valueMap)) {
            long begin = System.currentTimeMillis();
            Set<String> keySet = valueMap.keySet();
            logPrefix = logPrefix + "|key=" + StringUtils.join(keySet, ",");

            Jedis jedis = null;
            try {
                jedis = jedisPoolConnector.getMasterJedis();
                // 初始化传入参数2倍长度的数组，用于存储Key，Value
                String[] values = new String[valueMap.size() * 2];
                // 记录索引位置
                int index = 0;
                String jsonValue = null;
                for (Map.Entry<String, T> entry : valueMap.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (key != null && value != null) {
                        if (value instanceof String) {
                            jsonValue = (String) value;
                        } else {
                            jsonValue = CacheConstants.OBJECT_MAPPER.writeValueAsString(value);
                        }
                        values[index++] = key;
                        values[index++] = jsonValue;
                    }
                }

                jedis.mset(values);
                result = CacheConstants.SUCCESS;

                String logInfo = logPrefix + "|result=1|timeCost=" + (System.currentTimeMillis() - begin);
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("redis://", valueMap.size(), logInfo);
                }
                CACHE_DATA_LOGGER.info(logInfo);
            } catch (Exception e) {
                String logInfo = logPrefix + "|result=0|timeCost="
                        + (System.currentTimeMillis() - begin) + "|errMsg=set error.";
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("redis://", null, logInfo);
                }
                CACHE_DATA_LOGGER.error(logInfo, e);

                if (jedis != null) {
                    try {
                        jedisPoolConnector.returnMasterBrokenResourceObject(jedis);
                    } catch (Exception e1) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return master broken resource error.", e1);
                    }

                    if (e instanceof JedisConnectionException) {
                        if (!jedisPoolConnector.checkPoolIsAlive(jedisPoolConnector.POOL_TYPE_MASTER)) {
                            jedisPoolConnector.restartMaster();
                        }
                    }

                    jedis = null;
                }
            } finally {
                if (jedis != null && result == CacheConstants.SUCCESS) {
                    try {
                        jedisPoolConnector.returnMasterResource(jedis);
                    } catch (Exception e) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return master resource error.", e);
                    }
                }
            }
        } else {
            CACHE_DATA_LOGGER.info(logPrefix + "|result=0|errMsg=empty data.");
        }
        return result;
    }

    @Override
    public <T> int mset(Map<String, T> valueMap, int duration) {
        String logPrefix = CacheConstants.CACHE_INSTANCE_REDIS + "|mset|duration=" + duration;
        int result = CacheConstants.FAIL;

        if (!CollectionUtils.isEmpty(valueMap) && duration >= 0) {
            long begin = System.currentTimeMillis();
            Set<String> keySet = valueMap.keySet();
            logPrefix = logPrefix + "|key=" + StringUtils.join(keySet, ",");

            Jedis jedis = null;
            try {
                jedis = jedisPoolConnector.getMasterJedis();
                // 初始化传入参数2倍长度的数组，用于存储Key，Value
                byte[][] arr = new byte[valueMap.size() * 2][];
                String[] values = new String[valueMap.size() * 2];
                // 记录索引位置
                int index = 0;
                String jsonValue = null;
                for (Map.Entry<String, T> entry : valueMap.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (key != null && value != null) {
                        if (value instanceof String) {
                            jsonValue = (String) value;
                        } else {
                            jsonValue = CacheConstants.OBJECT_MAPPER.writeValueAsString(value);
                        }
                        jedis.setex(key, duration, jsonValue);
                    }
                }
                result = CacheConstants.SUCCESS;

                String logInfo = logPrefix + "|result=1|timeCost=" + (System.currentTimeMillis() - begin);
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("redis://", valueMap.size(), logInfo);
                }
                CACHE_DATA_LOGGER.info(logInfo);
            } catch (Exception e) {
                String logInfo = logPrefix + "|result=0|timeCost="
                        + (System.currentTimeMillis() - begin) + "|errMsg=set error.";
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("redis://", null, logInfo);
                }
                CACHE_DATA_LOGGER.error(logInfo, e);

                if (jedis != null) {
                    try {
                        jedisPoolConnector.returnMasterBrokenResourceObject(jedis);
                    } catch (Exception e1) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return master broken resource error.", e1);
                    }

                    if (e instanceof JedisConnectionException) {
                        if (!jedisPoolConnector.checkPoolIsAlive(jedisPoolConnector.POOL_TYPE_MASTER)) {
                            jedisPoolConnector.restartMaster();
                        }
                    }

                    jedis = null;
                }
            } finally {
                if (jedis != null && result == CacheConstants.SUCCESS) {
                    try {
                        jedisPoolConnector.returnMasterResource(jedis);
                    } catch (Exception e) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return master resource error.", e);
                    }
                }
            }
        } else {
            CACHE_DATA_LOGGER.info(logPrefix + "|result=0|errMsg=illegal param.");
        }
        return result;
    }

    @Override
    public <T> T get(String key, Class<T> c) {
        String logPrefix = CacheConstants.CACHE_INSTANCE_REDIS + "|get|key=" + key;
        T resultData = null;
        if (!StringUtils.isEmpty(key) && c != null) {
            Jedis jedis = null;
            String resultStr = null;
            int result = CacheConstants.FAIL;
            long start = System.currentTimeMillis();
            try {
                jedis = jedisPoolConnector.getSlaveJedis();
                boolean isValid = true;
                if (CacheConstants.IS_TTL) {
                    Long time = jedis.ttl(key);
                    if (time != null) {
                        if (time.longValue() == 0) {
                            isValid = false;
                        } else if (time.longValue() == -2) {
                            isValid = false;
                        }
                    }
                }
                if (isValid) {
                    resultStr = jedis.get(key);
                    if (resultStr != null) {
                        if (c == String.class) {
                            resultData = (T) resultStr;
                        } else {
                            resultData = CacheConstants.OBJECT_MAPPER.readValue(resultStr, c);
                        }
                    }
                }
                result = CacheConstants.SUCCESS;

                String logInfo = logPrefix + "|result=1|timeCost=" + (System.currentTimeMillis() - start);
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("redis://", resultStr, logInfo);
                }
                CACHE_DATA_LOGGER.info(logInfo);
            } catch (Exception e) {
                String logInfo = logPrefix + "|result=0|timeCost="
                        + (System.currentTimeMillis() - start) + "|errMsg=set error.";
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("redis://", resultStr, logInfo);
                }
                CACHE_DATA_LOGGER.error(logInfo, e);

                if (jedis != null) {
                    try {
                        jedisPoolConnector.returnSlaveBrokenResourceObject(jedis);
                    } catch (Exception e1) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return slave broken resource error.", e1);
                    }

                    if (e instanceof JedisConnectionException) {
                        if (!jedisPoolConnector.checkPoolIsAlive(jedisPoolConnector.POOL_TYPE_SLAVE)) {
                            jedisPoolConnector.restartSlave();
                        }
                    }

                    jedis = null;
                }
            } finally {
                if (jedis != null && result == CacheConstants.SUCCESS) {
                    try {
                        jedisPoolConnector.returnSlaveResource(jedis);
                    } catch (Exception e) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return slave resource error.", e);
                    }
                }
            }
        } else {
            CACHE_DATA_LOGGER.info(logPrefix + "|result=0|errMsg=illegal parameter.");
        }

        return resultData;
    }

    /*@Override
    public <T> Map<String, T> mget(List<String> keys, Class<T> c) {
        String logPrefix = CacheConstants.CACHE_INSTANCE_REDIS_JSON + "|mget";
        Map<String, T> jsonValues = null;
        if (!CollectionUtils.isEmpty(keys) && c != null) {
            logPrefix = logPrefix + "|key=" + StringUtils.join(keys, ",");
            long begin = System.currentTimeMillis();
            Jedis jedis = null;
            int result = CacheConstants.FAIL;
            try {
                jedis = jedisPoolConnector.getSlaveJedis();
                long getPoolTime = System.currentTimeMillis() - begin;
                *//*String temp = "";
                if (getPoolTime > 100) {
                    temp = "-getSlaveJedisCount-active=" + jedisPoolConnector.getSlavePoolNumActive() + "-waiter=" + jedisPoolConnector.getSlavePoolNumWaiters();
                }
                CACHE_DATA_LOGGER.info(logPrefix + "|result=3"+temp+"|timeCost=" + (getPoolTime));*//*
                CACHE_DATA_LOGGER.info(logPrefix + "|result=3|timeCost=" + (getPoolTime));
                long begin1 = System.currentTimeMillis();
                List<String> values = jedis.mget(keys.toArray(new String[keys.size()]));
                CACHE_DATA_LOGGER.info(logPrefix + "|result=2|timeCost=" + (System.currentTimeMillis() - begin1));
                if (values != null) {
                    jsonValues = new HashMap<String, T>();
                    for (int i = 0; i < keys.size(); i++) {
                        String key = keys.get(i);
                        String value = values.get(i);

                        T jsonValue = null;

                        if (value != null) {
                            if (c == String.class) {
                                jsonValue = (T) value;
                            } else {
                                jsonValue = CacheConstants.OBJECT_MAPPER.readValue(value, c);
                            }
                        }

                        jsonValues.put(key, jsonValue);
                    }
                }
                result = CacheConstants.SUCCESS;

                String logInfo = logPrefix + "|result=1|timeCost=" + (System.currentTimeMillis() - begin);
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("redis://", jsonValues, logInfo);
                }
                CACHE_DATA_LOGGER.info(logInfo);
            } catch (Exception e) {
                String logInfo = logPrefix + "|result=0|timeCost="
                        + (System.currentTimeMillis() - begin) + "|errMsg=set error.";
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("redis://", null, logInfo);
                }
                CACHE_DATA_LOGGER.error(logInfo, e);

                if (jedis != null) {
                    try {
                        jedisPoolConnector.returnSlaveBrokenResourceObject(jedis);
                    } catch (Exception e1) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return slave broken resource error.", e1);
                    }

                    if (e instanceof JedisConnectionException) {
                        if (!jedisPoolConnector.checkPoolIsAlive(jedisPoolConnector.POOL_TYPE_SLAVE)) {
                            jedisPoolConnector.restartSlave();
                        }
                    }

                    jedis = null;
                }
            } finally {
                if (jedis != null && result == CacheConstants.SUCCESS) {
                    try {
                        jedisPoolConnector.returnSlaveResource(jedis);
                    } catch (Exception e) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return slave resource error.", e);
                    }
                }
            }
        } else {
            CACHE_DATA_LOGGER.info(logPrefix + "|result=0|errMsg=illegal parameter.");
        }

        return jsonValues;
    }*/

    @Override
    public <T> Map<String, T> mget(List<String> keys, Class<T> c) {
        return mget(keys, c, 0);
    }

    @Override
    public <T> Map<String, T> mget(List<String> keys, Class<T> c, int batchSize) {
        String logPrefix = CacheConstants.CACHE_INSTANCE_REDIS_JSON + "|mget";
        Map<String, T> jsonValues = null;
        if (!CollectionUtils.isEmpty(keys) && c != null) {
            logPrefix = logPrefix + "|key=" + StringUtils.join(keys, ",");
            long begin = System.currentTimeMillis();
            Jedis jedis = null;
            int result = CacheConstants.FAIL;
            try {
                jedis = jedisPoolConnector.getSlaveJedis();
                Pipeline pipeline = null;
                long getPoolTime = System.currentTimeMillis() - begin;

                if (batchSize > 0) {
                    pipeline = jedis.pipelined();
                }
                /*
                 * String temp = "";
                 * if (getPoolTime > 100) {
                 * temp = "-getSlaveJedisCount-active=" +
                 * jedisPoolConnector.getSlavePoolNumActive() + "-waiter=" +
                 * jedisPoolConnector.getSlavePoolNumWaiters();
                 * }
                 * CACHE_DATA_LOGGER.info(logPrefix +
                 * "|result=3"+temp+"|timeCost=" + (getPoolTime));
                 */
                CACHE_DATA_LOGGER.info(logPrefix + "|result=3|timeCost=" + (getPoolTime));
                long begin1 = System.currentTimeMillis();
                List<String> values = null, tmpList = null;
                if (batchSize > 0) {
                    int pageNumber = batchSize; // 每页记录数
                    int totalCount = keys.size(); // 总记录数
                    int totalPage = totalCount / pageNumber; // 总页数
                    if ((totalCount % pageNumber) > 0) {
                        totalPage += 1;
                    }
                    int fromIndex = 0; // 起始位置
                    int toIndex = 0; // 结束位置
                    for (int pageNo = 0; pageNo < totalPage; pageNo++) {
                        fromIndex = pageNo * pageNumber;
                        toIndex = ((pageNo + 1) * pageNumber);
                        if (toIndex > totalCount) {
                            toIndex = totalCount;
                        }
                        tmpList = keys.subList(fromIndex, toIndex);
                        if (null != tmpList && tmpList.size() > 0) {
                            pipeline.mget(tmpList.toArray(new String[tmpList.size()]));
                            tmpList = null;
                        }
                        if (pageNo == totalPage - 1) {
                            for (Object ret : pipeline.syncAndReturnAll()) {
                                if (null != ret) {
                                    if (null == values) {
                                        values = (List<String>) ret;
                                    } else {
                                        values.addAll((List<String>) (List) (ret));
                                    }
                                }
                            }
                        }
                    }
                } else {
                    values = jedis.mget(keys.toArray(new String[keys.size()]));
                }
                CACHE_DATA_LOGGER.info(logPrefix + "|result=2|timeCost=" + (System.currentTimeMillis() - begin1));
                if (values != null) {
                    jsonValues = new HashMap<String, T>();
                    for (int i = 0; i < keys.size(); i++) {
                        String key = keys.get(i);
                        String value = values.get(i);

                        T jsonValue = null;

                        if (value != null) {
                            if (c == String.class) {
                                jsonValue = (T) value;
                            } else {
                                jsonValue = CacheConstants.OBJECT_MAPPER.readValue(value, c);
                            }
                        }

                        jsonValues.put(key, jsonValue);
                    }
                }
                result = CacheConstants.SUCCESS;

                String logInfo = logPrefix + "|result=1|timeCost=" + (System.currentTimeMillis() - begin);
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("redis://", jsonValues, logInfo);
                }
                CACHE_DATA_LOGGER.info(logInfo);
            } catch (Exception e) {
                String logInfo = logPrefix + "|result=0|timeCost=" + (System.currentTimeMillis() - begin)
                        + "|errMsg=set error.";
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("redis://", null, logInfo);
                }
                CACHE_DATA_LOGGER.error(logInfo, e);

                if (jedis != null) {
                    try {
                        jedisPoolConnector.returnSlaveBrokenResourceObject(jedis);
                    } catch (Exception e1) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return slave broken resource error.", e1);
                    }

                    if (e instanceof JedisConnectionException) {
                        if (!jedisPoolConnector.checkPoolIsAlive(jedisPoolConnector.POOL_TYPE_SLAVE)) {
                            jedisPoolConnector.restartSlave();
                        }
                    }

                    jedis = null;
                }
            } finally {
                if (jedis != null && result == CacheConstants.SUCCESS) {
                    try {
                        jedisPoolConnector.returnSlaveResource(jedis);
                    } catch (Exception e) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return slave resource error.", e);
                    }
                }
            }
        } else {
            CACHE_DATA_LOGGER.info(logPrefix + "|result=0|errMsg=illegal parameter.");
        }

        return jsonValues;
    }

    /*public <T> Map<String, T> mget(List<String> keys, Class<T> c) {
        String logPrefix = CacheConstants.CACHE_INSTANCE_REDIS_JSON + "|mget";
        Map<String, T> jsonValues = null;
        if (!CollectionUtils.isEmpty(keys) && c != null) {
            logPrefix = logPrefix + "|key=" + StringUtils.join(keys, ",");
            long begin = System.currentTimeMillis();
            Jedis jedis = null;
            int result = CacheConstants.FAIL;
            try {
                jedis = jedisPoolConnector.getSlaveJedis();
                Pipeline p  = jedis.pipelined();
                for (int i = 0; i < keys.size(); i++) {
                    String key = keys.get(i);
                    p.get(key);
                }
                List<Object> values =  p.syncAndReturnAll();
                if (values != null) {
                    jsonValues = new HashMap<String, T>();
                    for (int i = 0; i < keys.size(); i++) {
                        String key = keys.get(i);
                        String value = (String)values.get(i);
                        T jsonValue = null;

                        if (value != null) {
                            if (c == String.class) {
                                jsonValue = (T) value;
                            } else {
                                jsonValue = CacheConstants.OBJECT_MAPPER.readValue(value, c);
                            }
                        }

                        jsonValues.put(key, jsonValue);
                    }
                }
                result = CacheConstants.SUCCESS;
                CACHE_DATA_LOGGER.info(logPrefix + "|result=1|timeCost=" + (System.currentTimeMillis() - begin));
            } catch (Exception e) {
                CACHE_DATA_LOGGER.info(logPrefix + "|result=0|timeCost=" + (System.currentTimeMillis() - begin)
                        + "|errMsg=set error.", e);
                if (jedis != null) {
                    try {
                        jedisPoolConnector.returnSlaveBrokenResourceObject(jedis);
                    } catch (Exception e1) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return slave broken resource error.", e1);
                    }

                    if (e instanceof JedisConnectionException) {
                        if (!jedisPoolConnector.checkPoolIsAlive(jedisPoolConnector.POOL_TYPE_SLAVE)) {
                            jedisPoolConnector.restartSlave();
                        }
                    }

                    jedis = null;
                }
            } finally {
                if (jedis != null && result == CacheConstants.SUCCESS) {
                    try {
                        jedisPoolConnector.returnSlaveResource(jedis);
                    } catch (Exception e) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return slave resource error.", e);
                    }
                }
            }
        } else {
            CACHE_DATA_LOGGER.info(logPrefix + "|result=0|errMsg=illegal parameter.");
        }

        return jsonValues;
    }*/

    @Override
    public int delete(String key) {
        String logPrefix = CacheConstants.CACHE_INSTANCE_REDIS + "|delete|key=" + key;
        int result = CacheConstants.FAIL;

        if (key != null) {
            long begin = System.currentTimeMillis();
            Jedis jedis = null;

            try {
                jedis = jedisPoolConnector.getMasterJedis();
                jedis.del(key);
                result = CacheConstants.SUCCESS;

                String logInfo = logPrefix + "|result=1|timeCost=" + (System.currentTimeMillis() - begin);
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("redis://", null, logInfo);
                }
                CACHE_DATA_LOGGER.info(logInfo);
            } catch (Exception e) {
                String logInfo = logPrefix + "|result=0|timeCost="
                        + (System.currentTimeMillis() - begin) + "|errMsg=set error.";
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("redis://", null, logInfo);
                }
                CACHE_DATA_LOGGER.error(logInfo, e);

                if (jedis != null) {
                    try {
                        jedisPoolConnector.returnMasterBrokenResourceObject(jedis);
                    } catch (Exception e1) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return master broken resource error.", e1);
                    }

                    if (e instanceof JedisConnectionException) {
                        if (!jedisPoolConnector.checkPoolIsAlive(jedisPoolConnector.POOL_TYPE_MASTER)) {
                            jedisPoolConnector.restartMaster();
                        }
                    }

                    jedis = null;
                }
            } finally {
                if (jedis != null && result == CacheConstants.SUCCESS) {
                    try {
                        jedisPoolConnector.returnMasterResource(jedis);
                    } catch (Exception e) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return master resource error.", e);
                    }
                }
            }
        } else {
            CACHE_DATA_LOGGER.info(logPrefix + "|result=0|errMsg=illegal parameter.");
        }

        return result;
    }

    @Override
    public <T> T get(String key, LetvTypeReference<T> typeReference) {
        String logPrefix = CacheConstants.CACHE_INSTANCE_REDIS + "|get|key=" + key;
        T resultData = null;
        if (!StringUtils.isEmpty(key) && typeReference != null) {
            Jedis jedis = null;
            String resultStr = null;
            int result = CacheConstants.FAIL;
            long start = System.currentTimeMillis();
            try {

                jedis = jedisPoolConnector.getSlaveJedis();
                boolean isValid = true;
                if (CacheConstants.IS_TTL) {
                    Long time = jedis.ttl(key);
                    if (time != null) {
                        if (time.longValue() == 0) {
                            isValid = false;
                        } else if (time.longValue() == -2) {
                            isValid = false;
                        }
                    }
                }
                if (isValid) {
                    resultStr = jedis.get(key);
                    if (resultStr != null) {
                        resultData = CacheConstants.OBJECT_MAPPER.readValue(resultStr, typeReference);
                    }
                }
                result = CacheConstants.SUCCESS;

                String logInfo = logPrefix + "|result=1|timeCost=" + (System.currentTimeMillis() - start);
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("redis://", resultStr, logInfo);
                }
                CACHE_DATA_LOGGER.info(logInfo);
            } catch (Exception e) {
                String logInfo = logPrefix + "|result=0|timeCost="
                        + (System.currentTimeMillis() - start) + "|errMsg=set error.";
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("redis://", resultStr, logInfo);
                }
                CACHE_DATA_LOGGER.error(logInfo, e);

                if (jedis != null) {
                    try {
                        jedisPoolConnector.returnSlaveBrokenResourceObject(jedis);
                    } catch (Exception e1) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return slave broken resource error.", e1);
                    }

                    if (e instanceof JedisConnectionException) {
                        if (!jedisPoolConnector.checkPoolIsAlive(jedisPoolConnector.POOL_TYPE_SLAVE)) {
                            jedisPoolConnector.restartSlave();
                        }
                    }

                    jedis = null;
                }
            } finally {
                if (jedis != null && result == CacheConstants.SUCCESS) {
                    try {
                        jedisPoolConnector.returnSlaveResource(jedis);
                    } catch (Exception e) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return slave resource error.", e);
                    }
                }
            }
        } else {
            CACHE_DATA_LOGGER.info(logPrefix + "|result=0|errMsg=illegal parameter.");
        }

        return resultData;
    }

    @Override
    public <T> Map<String, T> mget(List<String> keys, LetvTypeReference<T> typeReference) {
        String logPrefix = CacheConstants.CACHE_INSTANCE_REDIS_JSON + "|mget";
        Map<String, T> jsonValues = null;
        if (!CollectionUtils.isEmpty(keys) && typeReference != null) {
            logPrefix = logPrefix + "|key=" + StringUtils.join(keys, ",");
            long begin = System.currentTimeMillis();
            Jedis jedis = null;
            int result = CacheConstants.FAIL;
            try {
                jedis = jedisPoolConnector.getSlaveJedis();
                long getPoolTime = System.currentTimeMillis() - begin;
                /*String temp = "";
                if (getPoolTime > 100) {
                    temp = "-getSlaveJedisCount-active=" + jedisPoolConnector.getSlavePoolNumActive() + "-waiter=" + jedisPoolConnector.getSlavePoolNumWaiters();
                }
                CACHE_DATA_LOGGER.info(logPrefix + "|result=3"+temp+"|timeCost=" + (getPoolTime));*/
                CACHE_DATA_LOGGER.info(logPrefix + "|result=3|timeCost=" + (getPoolTime));
                long begin1 = System.currentTimeMillis();
                List<String> values = jedis.mget(keys.toArray(new String[keys.size()]));
                CACHE_DATA_LOGGER.info(logPrefix + "|result=2|timeCost=" + (System.currentTimeMillis() - begin1));

                if (values != null) {
                    jsonValues = new HashMap<String, T>();
                    for (int i = 0; i < keys.size(); i++) {
                        String key = keys.get(i);
                        String value = values.get(i);

                        T jsonValue = null;

                        if (value != null) {
                            jsonValue = CacheConstants.OBJECT_MAPPER.readValue(value, typeReference);
                        }

                        jsonValues.put(key, jsonValue);
                    }
                }
                result = CacheConstants.SUCCESS;

                String logInfo = logPrefix + "|result=1|timeCost=" + (System.currentTimeMillis() - begin);
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("redis://", jsonValues, logInfo);
                }
                CACHE_DATA_LOGGER.info(logInfo);
            } catch (Exception e) {
                String logInfo = logPrefix + "|result=0|timeCost="
                        + (System.currentTimeMillis() - begin) + "|errMsg=set error.";
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("redis://", null, logInfo);
                }
                CACHE_DATA_LOGGER.error(logInfo, e);

                if (jedis != null) {
                    try {
                        jedisPoolConnector.returnSlaveBrokenResourceObject(jedis);
                    } catch (Exception e1) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return slave broken resource error.", e1);
                    }

                    if (e instanceof JedisConnectionException) {
                        if (!jedisPoolConnector.checkPoolIsAlive(jedisPoolConnector.POOL_TYPE_SLAVE)) {
                            jedisPoolConnector.restartSlave();
                        }
                    }

                    jedis = null;
                }
            } finally {
                if (jedis != null && result == CacheConstants.SUCCESS) {
                    try {
                        jedisPoolConnector.returnSlaveResource(jedis);
                    } catch (Exception e) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return slave resource error.", e);
                    }
                }
            }
        } else {
            CACHE_DATA_LOGGER.info(logPrefix + "|result=0|errMsg=illegal parameter.");
        }

        return jsonValues;
    }

    // 返回：1：存在 -1：不存在 0：无该集合
    @Override
    public int sismember(String key, String member) {
        String logPrefix = CacheConstants.CACHE_INSTANCE_REDIS + "|sismember|key=" + key;
        // 是否存在名为key的集合，并且没过期
        boolean isSetExist = false;
        // member是否在集合key中
        boolean isMemberInSet = false;
        // redis是否连接正常
        boolean isRedisOK = false;
        if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(member)) {
            Jedis jedis = null;
            long start = System.currentTimeMillis();
            try {
                jedis = jedisPoolConnector.getSlaveJedis();
                if (CacheConstants.IS_TTL) {
                    Long time = jedis.ttl(key);
                    if (time != null && time.longValue() != 0 && time.longValue() != -2) {
                            isSetExist = true;
                    }
                }
                if (isSetExist) {
                    isMemberInSet = jedis.sismember(key, member);
                }
                isRedisOK = true;

                String logInfo = logPrefix + "|result=1|timeCost=" + (System.currentTimeMillis() - start);
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("redis://", "{\"" + member + ":\""  + isMemberInSet + "}", logInfo);
                }
                CACHE_DATA_LOGGER.info(logInfo);
            } catch (Exception e) {
                String logInfo = logPrefix + "|result=0|timeCost="
                        + (System.currentTimeMillis() - start) + "|errMsg=set error.";
                if (null != SessionCache.getSession()) {
                    SessionCache.getSession().setResponse("redis://", null, logInfo);
                }
                CACHE_DATA_LOGGER.error(logInfo, e);

                if (jedis != null) {
                    try {
                        jedisPoolConnector.returnSlaveBrokenResourceObject(jedis);
                    } catch (Exception e1) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return slave broken resource error.", e1);
                    }
                    jedis = null;
                }
            } finally {
                if (jedis != null && isRedisOK) {
                    try {
                        jedisPoolConnector.returnSlaveResource(jedis);
                    } catch (Exception e) {
                        CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return slave resource error.", e);
                    }
                }
            }
        } else {
            CACHE_DATA_LOGGER.info(logPrefix + "|result=0|errMsg=illegal parameter.");
        }

        if (isRedisOK && isSetExist) {
            return isMemberInSet ? CacheConstants.SISMEMBER_MEMBER_EXIST : CacheConstants.SISMEMBER_MEMBER_NOT_EXIST;
        } else {
            return CacheConstants.SISMEMBER_KEY_NOT_EXIST;
        }
    }

    @Override
    public int sadd(String key, Set<String> member) {
        String logPrefix = CacheConstants.CACHE_INSTANCE_REDIS + "|sadd|key=" + key;
        int result = CacheConstants.FAIL;
        // 操作之前，需要检查是否正常初始化
        if (!StringUtil.isBlank(key)) {
            if (member == null || member.isEmpty()) {
                logger.info(logPrefix + "|result=-1|msg=null value, will do delete!");
                return delete(key);
            } else {
                long begin = System.currentTimeMillis();
                String jsonValue = null;
                Jedis jedis = null;
                try {
                    jedis = jedisPoolConnector.getMasterJedis();
                    //使用pipeline优化大量sadd操作
                    Pipeline p = jedis.pipelined();
                    for (String m : member) {
                        p.sadd(key, m);
                    }
                    p.sync();
                    result = CacheConstants.SUCCESS;

                    String logInfo = logPrefix + "|result=1|timeCost=" + (System.currentTimeMillis() - begin);
                    if (null != SessionCache.getSession()) {
                        SessionCache.getSession().setResponse("redis://", member, logInfo);
                    }
                    CACHE_DATA_LOGGER.info(logInfo);
                    CACHE_DATA_LOGGER.info(logPrefix + "|result=1|timeCost=" + (System.currentTimeMillis() - begin));
                } catch (Exception e) {
                    String logInfo = logPrefix + "|result=0|timeCost="
                            + (System.currentTimeMillis() - begin) + "|errMsg=set error.";
                    if (null != SessionCache.getSession()) {
                        SessionCache.getSession().setResponse("redis://", null, logInfo);
                    }
                    CACHE_DATA_LOGGER.error(logInfo, e);

                    if (jedis != null) {
                        try {
                            jedisPoolConnector.returnMasterBrokenResourceObject(jedis);
                        } catch (Exception e1) {
                            CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return master broken resource error.", e1);
                        }
                        jedis = null;
                    }
                } finally {
                    if (jedis != null && result == CacheConstants.SUCCESS) {
                        try {
                            jedisPoolConnector.returnMasterResource(jedis);
                        } catch (Exception e) {
                            CACHE_DATA_LOGGER.info(logPrefix + "|errMsg=return master resource error.", e);
                        }
                    }
                }
            }
        } else {
            logger.error(logPrefix + "|result=0|errMsg=illegal parameter.");
        }
        return result;
    }
}
