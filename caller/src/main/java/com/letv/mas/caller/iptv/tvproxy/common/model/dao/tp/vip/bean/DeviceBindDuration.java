package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean;

import java.io.Serializable;

public class DeviceBindDuration implements Serializable {

    private Integer productId;
    private Integer duration;
    private Integer durationUnit;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(Integer durationUnit) {
        this.durationUnit = durationUnit;
    }

}
