package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response;

import java.io.Serializable;

/**
 * 直播部门频道节目数据封装类
 * @author KevinYi
 */
public class LiveChannelProgramDetailTpResponse implements Serializable {

    private String branchType;
    private String duration;
    private String endTime;
    private String id;
    private String isRecorder;
    private String liveChannelId;
    private String playTime;
    private String programType;
    private String title;
    private String viewPic;
    private String aid;
    private String vid;
    private String liveId;

    public String getBranchType() {
        return this.branchType;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsRecorder() {
        return this.isRecorder;
    }

    public void setIsRecorder(String isRecorder) {
        this.isRecorder = isRecorder;
    }

    public String getLiveChannelId() {
        return this.liveChannelId;
    }

    public void setLiveChannelId(String liveChannelId) {
        this.liveChannelId = liveChannelId;
    }

    public String getPlayTime() {
        return this.playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getProgramType() {
        return this.programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewPic() {
        return this.viewPic;
    }

    public void setViewPic(String viewPic) {
        this.viewPic = viewPic;
    }

    public String getAid() {
        return this.aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getVid() {
        return this.vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getLiveId() {
        return this.liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

}
