package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

/**
 * 频道数据表
 */
public class ChannelDataMysqlTable {

    /**
     * 频道ID
     */
    private Integer channel_id;

    /**
     * 标题
     */
    private String title;

    /**
     * 标题数据类型
     */
    private Integer title_datatype;

    /**
     * 标题背景色
     */
    private String title_bgcolor;

    /**
     * 标题频道ID
     */
    private Integer title_channelid;

    /**
     * 标题检索条件
     */
    private String title_searchcondition;

    /**
     * 标题专辑ID
     */
    private Integer title_albumid;

    /**
     * 数据源
     */
    private Integer data_source;

    /**
     * 数据获取URL
     */
    private String data_url;

    /**
     * 数据预加载类型
     */
    private Integer data_preloadtype;

    /**
     * 数据预加载数量
     */
    private Integer data_preloadsize;

    /**
     * UI模版类型，用户只是当前频道数据在客户端页面上的展示位置， 0--推荐大焦点图，1--推荐旁六个小焦点图，2--猜你喜欢，3--超前影院；
     * 2015-08-27“超前院线”修添加，目前客户端仅对3进行特殊处理
     */
    private Integer ui_plate_type;

    /**
     * 排序
     */
    private Integer order_num;

    public Integer getChannel_id() {
        return this.channel_id;
    }

    public void setChannel_id(Integer channel_id) {
        this.channel_id = channel_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTitle_datatype() {
        return this.title_datatype;
    }

    public void setTitle_datatype(Integer title_datatype) {
        this.title_datatype = title_datatype;
    }

    public String getTitle_bgcolor() {
        return this.title_bgcolor;
    }

    public void setTitle_bgcolor(String title_bgcolor) {
        this.title_bgcolor = title_bgcolor;
    }

    public Integer getTitle_channelid() {
        return this.title_channelid;
    }

    public void setTitle_channelid(Integer title_channelid) {
        this.title_channelid = title_channelid;
    }

    public String getTitle_searchcondition() {
        return this.title_searchcondition;
    }

    public void setTitle_searchcondition(String title_searchcondition) {
        this.title_searchcondition = title_searchcondition;
    }

    public Integer getTitle_albumid() {
        return this.title_albumid;
    }

    public void setTitle_albumid(Integer title_albumid) {
        this.title_albumid = title_albumid;
    }

    public Integer getData_source() {
        return this.data_source;
    }

    public void setData_source(Integer data_source) {
        this.data_source = data_source;
    }

    public String getData_url() {
        return this.data_url;
    }

    public void setData_url(String data_url) {
        this.data_url = data_url;
    }

    public Integer getData_preloadtype() {
        return this.data_preloadtype;
    }

    public void setData_preloadtype(Integer data_preloadtype) {
        this.data_preloadtype = data_preloadtype;
    }

    public Integer getData_preloadsize() {
        return this.data_preloadsize;
    }

    public void setData_preloadsize(Integer data_preloadsize) {
        this.data_preloadsize = data_preloadsize;
    }

    public Integer getOrder_num() {
        return this.order_num;
    }

    public void setOrder_num(Integer order_num) {
        this.order_num = order_num;
    }

    public Integer getUi_plate_type() {
        return this.ui_plate_type;
    }

    public void setUi_plate_type(Integer ui_plate_type) {
        this.ui_plate_type = ui_plate_type;
    }

}
