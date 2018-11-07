package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "TrailerVideoDto", description = "预告片视频详情")
public class TrailerVideoDto {

    /**
     * 视频id
     */
    @ApiModelProperty(value = "视频id")
    private String videoId;

    @ApiModelProperty(value = "图片")
    private String img; // 图片

    /**
     * 视频名称
     */
    @ApiModelProperty(value = "视频名称")
    private String name;

    /**
     * 视频子名称
     */
    @ApiModelProperty(value = "视频子名称")
    private String subName;

    /**
     * 时长，单位-毫秒
     */
    @ApiModelProperty(value = "时长，单位-毫秒")
    private Long duration;

    /**
     * 视频播放短地址
     */
    @ApiModelProperty(value = "视频播放短地址")
    private String storePath;

    /**
     * 播放/下载地址
     */
    @ApiModelProperty(value = "播放/下载地址")
    private String playUrl;

    /**
     * 备用播放地址1
     */
    @ApiModelProperty(value = "备用播放地址1")
    private String backUrl0;

    /**
     * 备用播放地址2
     */
    @ApiModelProperty(value = "备用播放地址2")
    private String backUrl1;

    /**
     * 备用播放地址3
     */
    @ApiModelProperty(value = "备用播放地址3")
    private String backUrl2;

    /**
     * 当前码流
     */
    @ApiModelProperty(value = "当前码流")
    private String currentStream;

    /**
     * 播放码流的vtype值
     */
    @ApiModelProperty(value = "播放码流的vtype值")
    private Integer vtype;

    /**
     * 当前码流播放速度
     */
    @ApiModelProperty(value = "当前码流播放速度")
    private String currentStreamKps;

    /**
     * 媒资返回的md5值
     */
    @ApiModelProperty(value = "媒资返回的md5值")
    private String md5;

    @ApiModelProperty(value = "是否降码流")
    private Integer hasBelow;

    /**
     * 视频介质播放形式，目前特殊码流（3D系列、杜比系列）使用Mp4流播放，其他码流使用m3u8调度播放，目前仅返回“mediatype=mp4”和“
     * ext=m3u8”，客户端将其传递给CDE
     */
    @ApiModelProperty(value = "视频介质播放形式，目前特殊码流（3D系列、杜比系列）使用Mp4流播放，其他码流使用m3u8调度播放，目前仅返回“mediatype=mp4”和“ext=m3u8”，客户端将其传递给CDE")
    private String playMediaFormat;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getBackUrl0() {
        return backUrl0;
    }

    public void setBackUrl0(String backUrl0) {
        this.backUrl0 = backUrl0;
    }

    public String getBackUrl1() {
        return backUrl1;
    }

    public void setBackUrl1(String backUrl1) {
        this.backUrl1 = backUrl1;
    }

    public String getBackUrl2() {
        return backUrl2;
    }

    public void setBackUrl2(String backUrl2) {
        this.backUrl2 = backUrl2;
    }

    public String getCurrentStream() {
        return currentStream;
    }

    public void setCurrentStream(String currentStream) {
        this.currentStream = currentStream;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getHasBelow() {
        return hasBelow;
    }

    public void setHasBelow(Integer hasBelow) {
        this.hasBelow = hasBelow;
    }

    public String getPlayMediaFormat() {
        return playMediaFormat;
    }

    public void setPlayMediaFormat(String playMediaFormat) {
        this.playMediaFormat = playMediaFormat;
    }

    public Integer getVtype() {
        return vtype;
    }

    public void setVtype(Integer vtype) {
        this.vtype = vtype;
    }

    public String getCurrentStreamKps() {
        return currentStreamKps;
    }

    public void setCurrentStreamKps(String currentStreamKps) {
        this.currentStreamKps = currentStreamKps;
    }
}
