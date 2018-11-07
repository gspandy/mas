package com.letv.mas.caller.iptv.tvproxy.notification.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.notification.Commodity;

import java.util.List;

public class CommodityDto {

    private List<Commodity> commodityList;

    public List<Commodity> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<Commodity> commodityList) {
        this.commodityList = commodityList;
    }

}
