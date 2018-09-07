package com.letv.mas.caller.iptv.tvproxy.user.model.dao.cache;

import com.letv.mas.caller.iptv.tvproxy.user.annotation.Iptv;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * CacheDao门面类，提供各业务模块CacheDao接口入口
 * @author KevinYi
 */
@Component(value = "facadeCacheDao")
@Iptv
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
