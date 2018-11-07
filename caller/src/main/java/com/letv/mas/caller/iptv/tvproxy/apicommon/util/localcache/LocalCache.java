package com.letv.mas.caller.iptv.tvproxy.apicommon.util.localcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存LocalCache实现：
 * 由bigMap储存缓存的键值对，缓存值会被LocalCacheWrapper包装，用于存放缓存的建立时间和过期时间以实现过期判断
 * 目前缓存过期机制采用被动刷新，即调用方调用get()，才判断缓存是否过期，
 * 依据为LocalCacheWrapper包装类中的expireTime和initTime
 * @author guoyunfeng
 */
public class LocalCache<K, V> implements LocalCacheInterface<K, V> {
    private final static Logger LOG = LoggerFactory.getLogger(LocalCache.class);
    /**
     * 缓存名称，考虑到缓存粒度，可以考虑选择方法名，或者方法所在类名作为缓存名称
     */
    private String cacheName;

    /**
     * 实际存储缓存的结构体，又ConcurrentHashMap保证线程安全
     */
    private Map<K, LocalCacheWrapper> bigMap = new ConcurrentHashMap();

    public LocalCache(String name) {
        this.cacheName = name;
    }

    /**
     * 注，value可以传null，key不能
     * @param key
     * @param value
     * @param expireTimeInMillis
     *            过期时间，单位毫秒
     */
    @Override
    public void set(K key, V value, long expireTimeInMillis) {
        if (key != null) {
            LOG.info(this.cacheName + " set cache: " + key.toString());
            bigMap.put(key, new LocalCacheWrapper<V>(value, expireTimeInMillis));
        } else {
            LOG.warn(this.cacheName + " set cache error due to illegal parameters");
        }
    }

    /**
     * 获取指定key的缓存值，因为存缓存时允许value=null，所以读取时也会存在返回null的情况；
     * 所以返回null为正常情况，并表示存在该缓存；
     * @param key
     * @param flush
     * @return
     * @throws NoSuchKeyException
     *             如缓存没有键，则抛出NoSuchKeyException
     * @throws CacheExpiredException
     *             如缓存过期，则抛出CacheExpiredException
     */
    @Override
    public V get(K key) throws NoSuchKeyException, CacheExpiredException {
        V result = null;
        if (key != null) {
            LocalCacheWrapper<V> localCacheWrapper = bigMap.get(key);
            if (localCacheWrapper != null) {
                LOG.info(this.cacheName + " get cache: " + key.toString());
                try {
                    result = localCacheWrapper.getValue();
                } catch (CacheExpiredException e) {
                    LOG.info(this.cacheName + " cache expired " + key.toString());
                    // 如果缓存过期，抛出CacheExpiredException异常
                    throw new CacheExpiredException();
                } catch (Exception e) {
                    LOG.error(e.getMessage());
                }
            } else {
                // 如果无该缓存键，抛出NoSuchKeyException异常
                throw new NoSuchKeyException();
            }
        } else {
            LOG.warn(this.cacheName + " no key given");
        }
        return result;
    }

    @Override
    public void flush(K key) {
        bigMap.remove(key);
        LOG.info(this.cacheName + " flush key: " + key.toString());
    }

    @Override
    public void flushAll() {
        bigMap.clear();
        LOG.info(this.cacheName + " flushall");
    }

    /**
     * 缓存value的包装类，附加缓存创建时间和过期时间
     * @param <V>
     */
    class LocalCacheWrapper<V> {
        private V value;
        private long initTime;
        private long expireTime;

        public LocalCacheWrapper(V v, long expireTime) {
            this.value = v;
            this.initTime = System.currentTimeMillis();
            this.expireTime = expireTime;
        }

        public V getValue() throws CacheExpiredException {
            if (System.currentTimeMillis() < (initTime + expireTime)) {
                return value;
            } else {
                throw new CacheExpiredException();
            }
        }
    }
}
