package com.letv.mas.caller.iptv.tvproxy.common.model.dao;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache.UserCacheDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache.VipCacheDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * CacheDao门面类，提供各业务模块CacheDao接口入口
 * @author KevinYi
 */
@Component(value = "facadeCacheDao")
public class FacadeCacheDao {

    @Resource
    private UserCacheDao userCacheDao;

    public UserCacheDao getUserCacheDao() {
        return this.userCacheDao;
    }

    @Resource
    private VipCacheDao vipCacheDao;

    public VipCacheDao getVipCacheDao() {
        return vipCacheDao;
    }
}
