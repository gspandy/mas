package com.letv.mas.caller.iptv.tvproxy.common.model.dao;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.cache.CacheTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 各业务CacheDao的父类抽象，这里注入CacheDao，供各业务模块使用
 * @author KevinYi
 */
public abstract class BaseCacheDao {

    @Autowired
    protected CacheTemplate cacheTemplate;
}
