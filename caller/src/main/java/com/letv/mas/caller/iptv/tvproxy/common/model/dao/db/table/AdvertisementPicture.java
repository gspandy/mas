package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

import java.util.Date;

public class AdvertisementPicture {

    private String id;
    private Date createTime;// 创建时间
    private String createTimeStr;
    private String imagUrl;// 图地址
    private Integer aOrder;
    private String showPosition;
    private Integer type;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getImagUrl() {
        return this.imagUrl;
    }

    public void setImagUrl(String imagUrl) {
        this.imagUrl = imagUrl;
    }

    public Integer getaOrder() {
        return this.aOrder;
    }

    public void setaOrder(Integer aOrder) {
        this.aOrder = aOrder;
    }

    public String getShowPosition() {
        return showPosition;
    }

    public void setShowPosition(String showPosition) {
        this.showPosition = showPosition;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AdvertisementPicture [id=" + this.id + ", createTime=" + this.createTime + ", imagUrl=" + this.imagUrl
                + "]";
    }
}
