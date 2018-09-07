package com.letv.mas.caller.iptv.tvproxy.user.service;

import com.letv.mas.caller.iptv.tvproxy.user.model.dao.cache.FacadeCacheDao;
import com.letv.mas.caller.iptv.tvproxy.user.model.dao.tp.FacadeTpDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 * Service基类
 */
public abstract class BaseService {

    @Autowired
    protected FacadeService facadeService;

    @Autowired
    protected FacadeTpDao facadeTpDao;

    @Autowired
    protected FacadeCacheDao facadeCacheDao;

    /*@Autowired
    private MessageSource messageSource;*/
}
