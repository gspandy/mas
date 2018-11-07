package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1.StreamCode;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.WaterMarkImage;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/*xml可能会定义返回节点的顺序*/
@XmlRootElement
public class VideoPlayDto {
    // public String actionType;// 播放/下载
    private String storePath;
    private String playUrl;// 播放/下载地址
    private String backUrl0;// 备用播放地址1
    private String backUrl1;// 备用播放地址2
    private String backUrl2;// 备用播放地址3
    private Long duration;// 时长
    private Long gsize;// 视频大小byte
    private String currentStream;// 当前码流
    private String currentStream_kps;// 当前码流播放速度
    private String md5;// 媒资返回的md5值
    private Integer hasBelow = 0;
    private String tipMsg;// 附属信息。 如已经向下兼容
    private String showName;// 播放时提示的标题
    private Integer videoHeadTime = 0;// 片头时间 单位毫秒
    private Integer videoTailTime = 0;// 片尾时间
    private String statisticsCode;
    private Integer needWaterMarking = 0;// 0不需要加水印,1需要加水印
    private Integer playType;// 播放模式 1:正常播放 2:试看播放 3:350免费播放
    private Long tryPlayTime;// 试看时长
    private Long isYuanXian;// 是否院线
    private List<WaterMarkImage> WaterMarkImage = new ArrayList<>();
    private String tryPlayTipMsg = "";// 试看提示
    private List<StreamCode> streams;
    private String drmFlagId;
    private Integer categoryId;
    private Integer videoType;
    private Integer porder;
    private String drmTokenUrl;
    private String episode;

    public VideoPlayDto() {

    }

    public VideoPlayDto(String actionType, String playUrl, Long duration, String currentStream) {
        // this.actionType = actionType;
        this.playUrl = playUrl;
        this.duration = duration;
        this.currentStream = currentStream;
    }

    // public String getActionType() {
    // return actionType;
    // }
    //
    // public void setActionType(String actionType) {
    // this.actionType = actionType;
    // }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public String getPlayUrl() {
        return this.playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getDuration() {
        return this.duration;
    }

    public String getCurrentStream() {
        return this.currentStream;
    }

    public void setCurrentStream(String currentStream) {
        this.currentStream = currentStream;
    }

    public String getBackUrl0() {
        return this.backUrl0;
    }

    public void setBackUrl0(String backUrl0) {
        this.backUrl0 = backUrl0;
    }

    public String getBackUrl1() {
        return this.backUrl1;
    }

    public void setBackUrl1(String backUrl1) {
        this.backUrl1 = backUrl1;
    }

    public String getBackUrl2() {
        return this.backUrl2;
    }

    public void setBackUrl2(String backUrl2) {
        this.backUrl2 = backUrl2;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getCurrentStream_kps() {
        return this.currentStream_kps;
    }

    public void setCurrentStream_kps(String currentStream_kps) {
        this.currentStream_kps = currentStream_kps;
    }

    public Long getGsize() {
        return this.gsize;
    }

    public void setGsize(Long gsize) {
        this.gsize = gsize;
    }

    public Integer getHasBelow() {
        return this.hasBelow;
    }

    public void setHasBelow(Integer hasBelow) {
        this.hasBelow = hasBelow;
    }

    public String getTipMsg() {
        return this.tipMsg;
    }

    public void setTipMsg(String tipMsg) {
        this.tipMsg = tipMsg;
    }

    public String getShowName() {
        return this.showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public Integer getVideoHeadTime() {
        return this.videoHeadTime;
    }

    public void setVideoHeadTime(Integer videoHeadTime) {
        this.videoHeadTime = videoHeadTime;
    }

    public Integer getVideoTailTime() {
        return this.videoTailTime;
    }

    public void setVideoTailTime(Integer videoTailTime) {
        this.videoTailTime = videoTailTime;
    }

    public String getStatisticsCode() {
        return this.statisticsCode;
    }

    public void setStatisticsCode(String statisticsCode) {
        this.statisticsCode = statisticsCode;
    }

    public Integer getNeedWaterMarking() {
        return this.needWaterMarking;
    }

    public void setNeedWaterMarking(Integer needWaterMarking) {
        this.needWaterMarking = needWaterMarking;
    }

    public List<WaterMarkImage> getWaterMarkImage() {
        return this.WaterMarkImage;
    }

    public void setWaterMarkImage(List<WaterMarkImage> waterMarkImage) {
        this.WaterMarkImage = waterMarkImage;
    }

    public Integer getPlayType() {
        return playType;
    }

    public void setPlayType(Integer playType) {
        this.playType = playType;
    }

    public Long getTryPlayTime() {
        return tryPlayTime;
    }

    public void setTryPlayTime(Long tryPlayTime) {
        this.tryPlayTime = tryPlayTime;
    }

    public Long getIsYuanXian() {
        return isYuanXian;
    }

    public void setIsYuanXian(Long isYuanXian) {
        this.isYuanXian = isYuanXian;
    }

    public String getTryPlayTipMsg() {
        return tryPlayTipMsg;
    }

    public void setTryPlayTipMsg(String tryPlayTipMsg) {
        this.tryPlayTipMsg = tryPlayTipMsg;
    }

    public List<StreamCode> getStreams() {
        return streams;
    }

    public void setStreams(List<StreamCode> streams) {
        this.streams = streams;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getVideoType() {
        return videoType;
    }

    public void setVideoType(Integer videoType) {
        this.videoType = videoType;
    }

    public Integer getPorder() {
        return porder;
    }

    public void setPorder(Integer porder) {
        this.porder = porder;
    }

    public String getDrmFlagId() {
        return drmFlagId;
    }

    public void setDrmFlagId(String drmFlagId) {
        this.drmFlagId = drmFlagId;
    }

    public String getDrmTokenUrl() {
        return drmTokenUrl;
    }

    public void setDrmTokenUrl(String drmTokenUrl) {
        this.drmTokenUrl = drmTokenUrl;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

}
