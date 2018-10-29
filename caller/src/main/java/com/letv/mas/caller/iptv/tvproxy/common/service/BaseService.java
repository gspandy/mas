package com.letv.mas.caller.iptv.tvproxy.common.service;


import com.letv.mas.caller.iptv.tvproxy.common.model.dao.FacadeCacheDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.FacadeMysqlDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.FacadeTpDao;
import org.springframework.beans.factory.annotation.Autowired;

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
}
