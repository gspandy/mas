package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.util.List;

public class VoucherRule {

    private List<String> ids;// type=1或者type=2时，所适用的套餐id
    private Integer type;// 代金券适用类型，1--移动会员，2--全屏会员，3--电影单片，4--直播，5--组合套餐

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
