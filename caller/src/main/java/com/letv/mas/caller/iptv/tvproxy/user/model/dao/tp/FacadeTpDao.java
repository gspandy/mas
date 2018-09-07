package com.letv.mas.caller.iptv.tvproxy.user.model.dao.tp;

import com.letv.mas.caller.iptv.tvproxy.user.annotation.Iptv;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * TpDao门面类
 */
@Component
@Iptv
public class FacadeTpDao {

    @Resource
    private UserTpDao userTpDao;

    @Resource
    private VipTpDao vipTpDao;

    @Resource
    private ActReportDao actReportDao;

    public UserTpDao getUserTpDao() {
        return this.userTpDao;
    }

    public VipTpDao getVipTpDao() {
        return vipTpDao;
    }

    public ActReportDao getActReportDao() {
        return actReportDao;
    }
}
