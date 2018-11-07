package com.letv.mas.caller.iptv.tvproxy.apicommon.util.localcache;

/**
 * LocalCache接口
 * @author guoyunfeng
 */
public interface LocalCacheInterface<K, V> {

    final static long ONE_MINUTE = 1000 * 60;
    final static long FIVE_MINUTES = 1000 * 60 * 5;
    final static long TEN_MINUTES = 1000 * 60 * 10;
    final static long HALF_HOUR = 1000 * 60 * 30;
    final static long ONE_HOUR = 1000 * 60 * 60;
    final static long TWO_HOUR = 1000 * 60 * 60 * 2;
    final static long HALF_DAY = 1000 * 60 * 60 * 12;
    final static long ONE_DAY = 1000 * 60 * 60 * 24;
    /**
     * 默认缓存5分钟
     */
    final static long DEFAULT_EXPIRE_TIME = FIVE_MINUTES;

    /**
     * 添加缓存
     * @param key
     * @param value
     * @param expireTimeInMillis
     *            过期时间，单位毫秒
     */
    void set(K key, V value, long expireTimeInMillis);

    /**
     * 获取缓存
     * @param key
     * @return 如果有键，返回对应的值
     * @throws NoSuchKeyException
     *             如果没有键
     * @throws CacheExpiredException
     *             缓存过期
     */
    V get(K key) throws NoSuchKeyException, CacheExpiredException;

    /**
     * 清除缓存
     * @param key
     */
    void flush(K key);

    /**
     * 清除所有缓存
     */
    void flushAll();
}