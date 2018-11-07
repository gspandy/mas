package com.letv.mas.caller.iptv.tvproxy.notification.model.dto;

import com.letv.mas.caller.iptv.tvproxy.notification.QrCode;

import java.util.List;

/**
 * 活动
 */
public class QrCodeDto {

    private Integer type;

    private String title;

    private String qrCode;

    private String imageUrl;

    private Long beginTime;

    private Long endTime;

    private String region;

    private Integer duration;

    private Integer interval;

    private Integer intervalue;

    private String stream;

    private String pid;

    private String vid;

    private Integer dmIdentify;

    public Integer getDmIdentify() {
        return dmIdentify;
    }

    public void setDmIdentify(Integer dmIdentify) {
        this.dmIdentify = dmIdentify;
    }

    private List<QrCode> qrList;

    public List<QrCode> getQrList() {
        return this.qrList;
    }

    public void setQrList(List<QrCode> qrList) {
        this.qrList = qrList;
    }

    private CommodityDto commodityData;

    public CommodityDto getCommodityData() {
        return commodityData;
    }

    public void setCommodityData(CommodityDto commodityData) {
        this.commodityData = commodityData;
    }

    public String getStream() {
        return this.stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public Integer getInterval() {
        return this.interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQrCode() {
        return this.qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Long getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getIntervalue() {
        return this.intervalue;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getVid() {
        return this.vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public void setIntervalue(Integer intervalue) {
        this.intervalue = intervalue;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
