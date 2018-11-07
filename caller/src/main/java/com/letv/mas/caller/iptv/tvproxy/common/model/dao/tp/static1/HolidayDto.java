package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1;

import java.io.Serializable;

/**
 * 弹幕配置实体
 */
public class HolidayDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1096502694496791460L;
    private Integer enable;

    public HolidayDto(Integer enable) {
        this.enable = enable;
    }

    public Integer getEnable() {
        return this.enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
