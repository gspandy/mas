package com.letv.mas.caller.iptv.tvproxy.common.model.dao;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.ActReportDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.UserTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.VipTpDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * TpDao门面类
 */
@Component
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
