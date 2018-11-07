package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

/**
 * 排行榜表数据库模型；
 * 设计思路为，将若干排行榜，划分到某一频道下；根据频道查找所拥有的排行榜；排行榜表内设置排行榜所有关键属性，可开发
 * 相关后台，提供运用功能。
 * @author KevinYi
 */
public class ChartsMysqlTable {

    /**
     * 自增主键id
     */
    private Integer id;

    /**
     * 榜单名称，非空必填
     */
    private String name;

    /**
     * 所属频道id，参见iptv.ITV_MENU主键，未添加外键属性，非空必填
     */
    private Integer channel_id;

    /**
     * 排行榜类型，1--新片推荐，2--飙升榜，3--一周热播电影，4--一周热播电视剧，5--一周热播综艺，6--一周热播动漫，非空必填
     */
    private Integer type;

    /**
     * 排行榜背景色块，形如"#123456"，非空必填
     */
    private String bg_color;

    /**
     * 排行榜背景小图片，非空必填
     */
    private String small_pic;

    /**
     * 数据来源，目前用到的为1--CMS，4--本地数据库，参见ChannelConstants.DATASOURCE_*，非空必填
     */
    private Integer data_source;

    /**
     * 数据来源url，一般是CMS url
     */
    private String data_url;

    /**
     * 数据预加载类型，0--不加载，1--全部加载，2-部分加载，预加载量参见data_preloadsize，非空必填
     */
    private Integer data_preloadtype;

    /**
     * 数据预加载量，在data_preloadtype=2时有意义
     */
    private Integer data_preloadsize;

    /**
     * 排序，数值越小排序越靠前，非空必填
     */
    private Integer order_num;

    /**
     * 排行榜要过滤的视频或专辑的分类id，0--不过滤，其他参见VideoConstants.Category，非空必填
     */
    private Integer category_id;

    /**
     * 数据状态，1--可用，0--不可用，非空必填
     */
    private Integer status;
    /**
     * 小角标背景颜色
     */
    private String corner_color;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getChannel_id() {
        return this.channel_id;
    }

    public void setChannel_id(Integer channel_id) {
        this.channel_id = channel_id;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBg_color() {
        return this.bg_color;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }

    public String getSmall_pic() {
        return this.small_pic;
    }

    public void setSmall_pic(String small_pic) {
        this.small_pic = small_pic;
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

    public Integer getCategory_id() {
        return this.category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCorner_color() {
        return this.corner_color;
    }

    public void setCorner_color(String corner_color) {
        this.corner_color = corner_color;
    }

}
