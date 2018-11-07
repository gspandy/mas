package com.letv.mas.caller.iptv.tvproxy.common.service;


import com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache.FacadeCacheDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.FacadeMysqlDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.FacadeTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.cache.CacheTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * Service基类
 */
public abstract class BaseService {

    @Autowired
    protected FacadeTpDao facadeTpDao;

    @Autowired
    protected FacadeCacheDao facadeCacheDao;

    @Autowired
    protected FacadeMysqlDao facadeMysqlDao;

    /*@Autowired
    private MessageSource messageSource;*/

    @Deprecated
    @Autowired
    protected CacheTemplate cacheTemplate;

}
