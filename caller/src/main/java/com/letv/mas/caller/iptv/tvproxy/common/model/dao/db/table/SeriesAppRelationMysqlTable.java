package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

/**
 * 品牌，型号，平台相关信息，里面只列出了需要的字段，没有查询全部数据
 * @author zhangwen3
 */
public class SeriesAppRelationMysqlTable {
    private Integer series_app_id;
    private Integer play_format_ls_ts;
    private Integer broadcastId;
    private String config;
    private Integer seriesId;

    public Integer getPlay_format_ls_ts() {
        return play_format_ls_ts;
    }

    public void setPlay_format_ls_ts(Integer play_format_ls_ts) {
        this.play_format_ls_ts = play_format_ls_ts;
    }

    public Integer getSeries_app_id() {
        return series_app_id;
    }

    public void setSeries_app_id(Integer series_app_id) {
        this.series_app_id = series_app_id;
    }

    public Integer getBroadcastId() {
        return broadcastId;
    }

    public void setBroadcastId(Integer broadcastId) {
        this.broadcastId = broadcastId;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

}
