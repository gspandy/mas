package com.letv.mas.caller.iptv.tvproxy.apicommon.util.localcache;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 所有LocalCache实例的管理类，调用者通过此类获得LocalCache实例，并且保证相同名称的LocalCache实例只会实例化一次
 * @author guoyunfeng
 */
@Component
public class LocalCacheManager {

    private final ConcurrentMap<String, LocalCacheInterface> cacheMap = new ConcurrentHashMap<>(16);

    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }

    public LocalCacheInterface getCache(String name) {
        LocalCacheInterface cache = this.cacheMap.get(name);
        if (cache == null) {
            synchronized (this.cacheMap) {
                cache = this.cacheMap.get(name);
                if (cache == null) {
                    cache = new LocalCache(name);
                    this.cacheMap.put(name, cache);
                }
            }
        }
        return cache;
    }
}
