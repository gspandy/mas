package com.letv.mas.caller.iptv.tvproxy.channel.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;

/**
 * 频道
 */
public class ChannleLeGuide extends BaseChannel {
    private String charts_id;

    private Integer charts_type;

    public ChannleLeGuide() {
    }

    public String getCharts_id() {
        return charts_id;
    }

    public Integer getCharts_type() {
        return charts_type;
    }

    @Override
    public int getDataType() {
        return DataConstant.DATA_TYPE_CHANNEL;
    }

    public void setCharts_id(String charts_id) {
        this.charts_id = charts_id;
    }

    public void setCharts_type(Integer charts_type) {
        this.charts_type = charts_type;
    }
}
