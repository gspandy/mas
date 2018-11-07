package com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache;

/**
 * Author：apple on 17/10/16 10:48
 * eMail：dengliwei@le.com
 */
public class TestCacheDao extends BaseCacheDao {

    public String getValue(String key) {
        return this.cacheTemplate.get(key, String.class);
    }

    public void setValue(String key, String value) {
        this.cacheTemplate.set(key, value);
    }

    public void delValue(String key) {
        this.cacheTemplate.delete(key);
    }
}
