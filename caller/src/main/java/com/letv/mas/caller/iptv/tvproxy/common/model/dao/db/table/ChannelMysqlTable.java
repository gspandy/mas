package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

/**
 * 频道表
 */
public class ChannelMysqlTable {

    /**
     * 频道ID
     */
    private Integer id;

    /**
     * 频道名称
     */
    private String name;

    /**
     * 父频道ID
     */
    private Integer parentid;

    /**
     * 请求类型
     */
    private Integer request_type;

    /**
     * 数据获取URL
     */
    private String url;

    /**
     * 内容ID
     */
    private Integer categoryid;

    /**
     * 是否排行
     */
    private Integer isRank;

    /**
     * 大图
     */
    private String big_pic;

    /**
     * 小图
     */
    private String small_pic;

    /**
     * 焦点1
     */
    private String focus;

    /**
     * 焦点1
     */
    private String focus1;

    /**
     * 焦点2
     */
    private String focus2;

    /**
     * 背景色
     */
    private String color;

    /**
     * 频道编号
     */
    private String channelcode;

    /**
     * 专辑id
     */
    private Integer albumId;//

    /**
     * 专题id
     */
    private Integer pid;//

    /**
     * 默认码流
     */
    private String default_stream;

    /**
     * 新逻辑跳转类型定义，原先的request_type保留
     */
    private Integer data_type;

    /**
     * 跳转配置信息
     */
    private String config_info;

    /**
     * 2016-06-01新增字段，数据库有但是以前没人用，此处用于判断频道墙某个频道是否返给客户端；
     * (1)如果为all，表示客户端不支持也要返给客户端；
     * (2)如果不为all，则表示要根据客户端所传参数来决定是否返回给客户端；
     */
    private String terminal_series;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getRequest_type() {
        return request_type;
    }

    public void setRequest_type(Integer request_type) {
        this.request_type = request_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public Integer getIsRank() {
        return isRank;
    }

    public void setIsRank(Integer isRank) {
        this.isRank = isRank;
    }

    public String getBig_pic() {
        return big_pic;
    }

    public void setBig_pic(String big_pic) {
        this.big_pic = big_pic;
    }

    public String getSmall_pic() {
        return small_pic;
    }

    public void setSmall_pic(String small_pic) {
        this.small_pic = small_pic;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getFocus1() {
        return focus1;
    }

    public void setFocus1(String focus1) {
        this.focus1 = focus1;
    }

    public String getFocus2() {
        return focus2;
    }

    public void setFocus2(String focus2) {
        this.focus2 = focus2;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getChannelcode() {
        return channelcode;
    }

    public void setChannelcode(String channelcode) {
        this.channelcode = channelcode;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getDefault_stream() {
        return default_stream;
    }

    public void setDefault_stream(String default_stream) {
        this.default_stream = default_stream;
    }

    public Integer getData_type() {
        return data_type;
    }

    public void setData_type(Integer data_type) {
        this.data_type = data_type;
    }

    public String getConfig_info() {
        return config_info;
    }

    public void setConfig_info(String config_info) {
        this.config_info = config_info;
    }

    public String getTerminal_series() {
        return terminal_series;
    }

    public void setTerminal_series(String terminal_series) {
        this.terminal_series = terminal_series;
    }

}
