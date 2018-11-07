package com.letv.mas.caller.iptv.tvproxy.music;

import java.util.List;

public class MusicNav {
    private long id; // 主键
    private String name; // 名称
    private String nav_code; // 标识
    private String url; // 请求url
    private Integer req_type; // 请求数据类型1Tag,2专辑,3检索,4首页,5搜索,6直播,7视频,8体育直接播放,9直播大厅,11外跳，12媒资CMS数据
    private Integer order_num; // 排序
    private int parent_id; // 父id
    private Integer cid; // 媒资cid
    private List<MusicNav> subNav;

    public List<MusicNav> getSubNav() {
        return this.subNav;
    }

    public void setSubNav(List<MusicNav> subNav) {
        this.subNav = subNav;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public String toString() {
        return "MusicNav [id=" + this.id + ", name=" + this.name + ", menu_code=" + this.nav_code + ", url=" + this.url
                + ", req_type=" + this.req_type + ", order_num=" + this.order_num + ", parent_id=" + this.parent_id
                + ", cid=" + this.cid + "]";
    }

}
