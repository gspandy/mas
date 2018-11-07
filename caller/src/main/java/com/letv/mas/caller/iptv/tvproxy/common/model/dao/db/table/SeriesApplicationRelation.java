package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

import java.util.Date;

public class SeriesApplicationRelation {
    private static final long serialVersionUID = 7354117137645505605L;

    private Integer id;
    private Long platform_id;// 平台id
    private Long series_id;// 型号id
    private Long app_id;// 应用id
    private Date expire_time;
    private Integer max_num;// 最大台数
    private Integer play_format_ls_ts;// 返回的碼流格式： 1、ture 2、false 3、其它
    private Integer broadcastId;
    private String config;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getPlatform_id() {
        return this.platform_id;
    }

    public void setPlatform_id(Long platform_id) {
        this.platform_id = platform_id;
    }

    public Long getSeries_id() {
        return this.series_id;
    }

    public void setSeries_id(Long series_id) {
        this.series_id = series_id;
    }

    public Long getApp_id() {
        return this.app_id;
    }

    public void setApp_id(Long app_id) {
        this.app_id = app_id;
    }

    public Date getExpire_time() {
        return this.expire_time;
    }

    public void setExpire_time(Date expire_time) {
        this.expire_time = expire_time;
    }

    public Integer getMax_num() {
        return this.max_num;
    }

    public void setMax_num(Integer max_num) {
        this.max_num = max_num;
    }

    public Integer getPlay_format_ls_ts() {
        return this.play_format_ls_ts;
    }

    public void setPlay_format_ls_ts(Integer play_format_ls_ts) {
        this.play_format_ls_ts = play_format_ls_ts;
    }

    public Integer getBroadcastId() {
        return this.broadcastId;
    }

    public void setBroadcastId(Integer broadcastId) {
        this.broadcastId = broadcastId;
    }

    public String getConfig() {
        return this.config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

}
