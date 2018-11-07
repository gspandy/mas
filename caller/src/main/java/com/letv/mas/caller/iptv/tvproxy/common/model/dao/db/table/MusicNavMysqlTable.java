package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

/**
 * 频道表
 */
public class MusicNavMysqlTable {
    private int id; // 主键
    private String name; // 名称
    private String nav_code; // 标识
    private Float vnum; // 版本
    private String url; // 请求url
    private Integer req_type; // 请求数据类型1Tag,2专辑,3检索,4首页,5搜索,6直播,7视频,8体育直接播放,9直播大厅,11外跳，12媒资CMS数据
    private Integer status; // 是否有效（1有效，0无效）
    private Integer order_num; // 排序
    private int parent_id; // 父id
    private Integer cid; // 媒资cid
    private Integer cibn; // 国广播控（1上线，0下线）
    private Integer wasu; // 华数播控（1上线，0下线）
    private Integer cntv; // CNTV播控（1上线，0下线）
    private Long le_id;// 媒资歌手id

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNav_code() {
        return this.nav_code;
    }

    public void setNav_code(String nav_code) {
        this.nav_code = nav_code;
    }

    public Float getVnum() {
        return this.vnum;
    }

    public void setVnum(Float vnum) {
        this.vnum = vnum;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getReq_type() {
        return this.req_type;
    }

    public void setReq_type(Integer req_type) {
        this.req_type = req_type;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrder_num() {
        return this.order_num;
    }

    public void setOrder_num(Integer order_num) {
        this.order_num = order_num;
    }

    public int getParent_id() {
        return this.parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public Integer getCid() {
        return this.cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getCibn() {
        return this.cibn;
    }

    public void setCibn(Integer cibn) {
        this.cibn = cibn;
    }

    public Integer getWasu() {
        return this.wasu;
    }

    public void setWasu(Integer wasu) {
        this.wasu = wasu;
    }

    public Integer getCntv() {
        return this.cntv;
    }

    public void setCntv(Integer cntv) {
        this.cntv = cntv;
    }

    public Long getLe_id() {
        return le_id;
    }

    public void setLe_ids(Long le_id) {
        this.le_id = le_id;
    }

    @Override
    public String toString() {
        return "MusicNavMysqlTable [id=" + this.id + ", name=" + this.name + ", menu_code=" + this.nav_code + ", vnum="
                + this.vnum + ", url=" + this.url + ", req_type=" + this.req_type + ", status=" + this.status
                + ", order_num=" + this.order_num + ", parent_id=" + this.parent_id + ", cid=" + this.cid + ", cibn="
                + this.cibn + ", wasu=" + this.wasu + ", cntv=" + this.cntv + ", getId()=" + getId() + ", getName()="
                + getName() + ", getMenu_code()=" + getNav_code() + ", getVnum()=" + getVnum() + ", getUrl()="
                + getUrl() + ", getReq_type()=" + getReq_type() + ", getStatus()=" + getStatus() + ", getOrder_num()="
                + getOrder_num() + ", getParent_id()=" + getParent_id() + ", getCid()=" + getCid() + ", getCibn()="
                + getCibn() + ", getWasu()=" + getWasu() + ", getCntv()=" + getCntv() + ", getClass()=" + getClass()
                + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

}
