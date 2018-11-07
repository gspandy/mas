package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

import java.util.Date;

public class Itv_Menu {

    private Integer id;
    private String name;
    private String channelCode;// 频道编号
    private String url;// 请求URL
    private String icon;// 图标
    private Integer requestType;// 请求类型：1TAG列表 2 专辑列表
    private Integer parentId;// 父菜单
    private Integer isRank;
    private String terminal_type;// 客户端类型
    private String terminal_brand;// 终端品牌
    private String terminal_series;// 终端型号
    private Integer broadcastid;// 播控方
    private Integer status;// 状态 0不可用 1可用
    private Integer ordernum;// 排序
    private Integer categoryid;// 对应的分类id
    private Float vnum;// 版本号
    private Date createdate;// 创建时间
    private Date updatedate;// 更新时间
    private String createby;
    private String updateby;
    private Integer relationId;// 资源Id
    private Integer cntv;
    private Integer wasu;
    private Integer cibn;
    private String subCode;
    private String bigPic;
    private String smallPic;
    private String focus;
    private String focus1;
    private String color;
    private String order2p5;
    private String focus2;

    public String getFocus1() {
        return focus1;
    }

    public void setFocus1(String focus1) {
        this.focus1 = focus1;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public Integer getCntv() {
        return this.cntv;
    }

    public void setCntv(Integer cntv) {
        this.cntv = cntv;
    }

    public Integer getWasu() {
        return this.wasu;
    }

    public void setWasu(Integer wasu) {
        this.wasu = wasu;
    }

    public Integer getCibn() {
        return this.cibn;
    }

    public void setCibn(Integer cibn) {
        this.cibn = cibn;
    }

    public Integer getRequestType() {
        return this.requestType;
    }

    public Integer getIsRank() {
        return this.isRank;
    }

    public void setIsRank(Integer isRank) {
        this.isRank = isRank;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

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

    public String getChannelCode() {
        return this.channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTerminal_type() {
        return this.terminal_type;
    }

    public void setTerminal_type(String terminal_type) {
        this.terminal_type = terminal_type;
    }

    public String getTerminal_brand() {
        return this.terminal_brand;
    }

    public void setTerminal_brand(String terminal_brand) {
        this.terminal_brand = terminal_brand;
    }

    public String getTerminal_series() {
        return this.terminal_series;
    }

    public void setTerminal_series(String terminal_series) {
        this.terminal_series = terminal_series;
    }

    public Integer getBroadcastid() {
        return this.broadcastid;
    }

    public void setBroadcastid(Integer broadcastid) {
        this.broadcastid = broadcastid;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrdernum() {
        return this.ordernum;
    }

    public void setOrdernum(Integer ordernum) {
        this.ordernum = ordernum;
    }

    public Integer getCategoryid() {
        return this.categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public Float getVnum() {
        return this.vnum;
    }

    public void setVnum(Float vnum) {
        this.vnum = vnum;
    }

    public Date getCreatedate() {
        return this.createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return this.updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getCreateby() {
        return this.createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getUpdateby() {
        return this.updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }

    public Integer getRelationId() {
        return this.relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public String getBigPic() {
        return bigPic;
    }

    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOrder2p5() {
        return order2p5;
    }

    public void setOrder2p5(String order2p5) {
        this.order2p5 = order2p5;
    }

    public String getFocus2() {
        return focus2;
    }

    public void setFocus2(String focus2) {
        this.focus2 = focus2;
    }

}
