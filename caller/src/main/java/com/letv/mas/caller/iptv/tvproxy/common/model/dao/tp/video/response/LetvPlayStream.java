package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.io.Serializable;
import java.util.List;

/**
 * 播放接口返回结果码流信息封装类，聚合了TV版码流和领先版码流模型
 * @author KevinYi
 */
public class LetvPlayStream implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4937694454702794488L;
    private String name;
    private String code;
    private Integer ifCharge;// 是否收费 0不收费，1收费
    private String canPlay;// 能否播放——视频或者专辑使用
    private String canDown;// 能否下载——视频或者专辑使用
    private String kbps;
    private String bandWidth;// 带宽
    private Boolean isDefault;// 是否默认
    private Long fileSize;
    private List<LetvPlayStream> liveStreams;// 设置——直播码流列表
    private List<LetvPlayStream> playStreams;// 设置——播放码流列表

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIfCharge() {
        return this.ifCharge;
    }

    public void setIfCharge(Integer ifCharge) {
        this.ifCharge = ifCharge;
    }

    public String getCanPlay() {
        return this.canPlay;
    }

    public void setCanPlay(String canPlay) {
        this.canPlay = canPlay;
    }

    public String getCanDown() {
        return this.canDown;
    }

    public void setCanDown(String canDown) {
        this.canDown = canDown;
    }

    public String getKbps() {
        return this.kbps;
    }

    public void setKbps(String kbps) {
        this.kbps = kbps;
    }

    public String getBandWidth() {
        return this.bandWidth;
    }

    public void setBandWidth(String bandWidth) {
        this.bandWidth = bandWidth;
    }

    public Boolean getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public List<LetvPlayStream> getLiveStreams() {
        return this.liveStreams;
    }

    public void setLiveStreams(List<LetvPlayStream> liveStreams) {
        this.liveStreams = liveStreams;
    }

    public List<LetvPlayStream> getPlayStreams() {
        return this.playStreams;
    }

    public void setPlayStreams(List<LetvPlayStream> playStreams) {
        this.playStreams = playStreams;
    }

}
