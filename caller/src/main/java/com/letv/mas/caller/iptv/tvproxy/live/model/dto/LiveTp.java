package com.letv.mas.caller.iptv.tvproxy.live.model.dto;

import java.util.HashMap;

/**
 * 第三方电视直播
 */
public class LiveTp {

    private String liveId;// 直播ID

    private String title;// 标题

    private String subTitle;// 副标题

    private String shortDesc;// 简介

    private String remark;// 备注

    private String pic1;// 背景图

    private String pic2;// 海报图

    private HashMap<String, String> streams;// 码流

    private String startTime;// 开始时间

    private String endTime;// 结束时间

    private String replayEndTime;// 回放结束时间

    private Long duration;// 直播时长

    public String getLiveId() {
        return this.liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getShortDesc() {
        return this.shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPic1() {
        return this.pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return this.pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public HashMap<String, String> getStreams() {
        return this.streams;
    }

    public void setStreams(HashMap<String, String> streams) {
        this.streams = streams;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getReplayEndTime() {
        return this.replayEndTime;
    }

    public void setReplayEndTime(String replayEndTime) {
        this.replayEndTime = replayEndTime;
    }

    public Long getDuration() {
        return this.duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
