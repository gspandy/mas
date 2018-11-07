package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.util.List;
import java.util.Map;

public class VODDto extends BaseDto {

    /**
     * 播放模式 1:正常播放 2:试看播放 3:350免费播放 4:院线试看 5:码流收费
     */
    private Integer playType;

    /**
     * 视频id
     */
    private String videoId;

    /**
     * 内容分类（电影、电视剧）
     */
    private Integer categoryId;

    /**
     * 视频所属专辑id
     */
    private String albumId;

    /**
     * 是否有所属专辑
     */
    private Integer hasAlbum;

    /**
     * 在专辑中顺序
     */
    private Integer orderInAlbum;

    /**
     * 是否正片
     */
    private Integer positive;
    private Integer videoTypeId;

    /**
     * 第几集、第几期
     */
    private String episode;

    /**
     * 预告片类型 0 有效的预告片(0<跟播集数<3) 1 过期的预告片
     */
    private Integer preType;

    private String img;

    /**
     * 视频名称
     */
    private String name;

    /**
     * 视频子名称
     */
    private String subName;

    /**
     * 描述
     */
    private String desc;

    /**
     * 视点图
     */
    private String viewPic;

    /**
     * 观看次数
     */
    private Long vv;

    /**
     * 评论数
     */
    private Long commentCnt;

    /**
     * 时长(毫秒)
     */
    private Long duration;

    /**
     * 嘉宾
     */
    private String guest;

    /**
     * 歌手
     */
    private String singer;

    /**
     * 推送到TV版的时间
     */
    private Long pushTVTime;

    private String defaultStream;
    private List<Stream> streams;
    private List<Stream> normalStreams;

    private List<Stream> theatreStreams;

    private List<Stream> threeDStreams;
    /**
     * 推送平台
     */
    private String playPlatform;

    /**
     * 下载平台
     */
    private String downloadPlatform;

    /**
     * 能否下载
     */
    private Integer download;

    /**
     * 是否收费
     */
    private String ifCharge;

    /**
     * 视频播放短地址
     */
    private String storePath;

    /**
     * 播放/下载地址
     */
    private String playUrl;

    /**
     * 备用播放地址1
     */
    private String backUrl0;

    /**
     * 备用播放地址2
     */
    private String backUrl1;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 备用播放地址3
     */
    private String backUrl2;

    /**
     * 视频大小byte
     */
    private Long gsize;

    /**
     * 当前码流
     */
    private String currentStream;

    /**
     * 当前码流播放速度
     */
    private String currentStreamKps;

    /**
     * 媒资返回的md5值
     */
    private String md5;
    private Integer hasBelow;

    /**
     * 附属信息。 如已经向下兼容
     */
    private String tipMsg;

    /**
     * 播放时提示的标题
     */
    private String showName;

    /**
     * 片头时间 单位毫秒
     */
    private Integer videoHeadTime;

    /**
     * 片尾时间
     */
    private Integer videoTailTime;
    private String statisticsCode;

    /**
     * 0不需要加水印,1需要加水印
     */
    private Integer needWaterMarking;

    /**
     * 播放模式 1:正常播放 2:试看播放 3:350免费播放 4:院线试看 5:码流收费
     */
    // private Integer playType;

    /**
     * 试看时长
     */
    private Long tryPlayTime;

    /**
     * 是否院线
     */
    private Long isYuanXian;

    /**
     * 试看提示
     */
    private String tryPlayTipMsg;
    private String drmFlagId;

    /**
     * DRM视频token文件地址
     */
    private String drmTokenUrl;

    /**
     * 声纹文件地址
     */
    private String soundInkCodeUrl;

    /**
     * 剧集列表视频类型 0预告
     */
    private Integer seriesType;

    /**
     * 是否是推荐
     */
    private Integer is_rec;

    private Map<String, String> allPlayUrl;

    /**
     * 家长锁 0否，1是
     */
    private Integer isPlayLock;

    /**
     * 看点
     */
    private List<WatchingFocusCache> watchingFocus;

    private Integer page;

    /**
     * 相关内容
     */
    private Object relation;

    private ChargeDto chargeObj = new ChargeDto();

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public Integer getHasAlbum() {
        return hasAlbum;
    }

    public void setHasAlbum(Integer hasAlbum) {
        this.hasAlbum = hasAlbum;
    }

    public Integer getOrderInAlbum() {
        return orderInAlbum;
    }

    public void setOrderInAlbum(Integer orderInAlbum) {
        this.orderInAlbum = orderInAlbum;
    }

    public Integer getPositive() {
        return positive;
    }

    public void setPositive(Integer positive) {
        this.positive = positive;
    }

    public Integer getVideoTypeId() {
        return videoTypeId;
    }

    public void setVideoTypeId(Integer videoTypeId) {
        this.videoTypeId = videoTypeId;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public Integer getPreType() {
        return preType;
    }

    public void setPreType(Integer preType) {
        this.preType = preType;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getViewPic() {
        return viewPic;
    }

    public void setViewPic(String viewPic) {
        this.viewPic = viewPic;
    }

    public Long getVv() {
        return vv;
    }

    public void setVv(Long vv) {
        this.vv = vv;
    }

    public Long getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(Long commentCnt) {
        this.commentCnt = commentCnt;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public Long getPushTVTime() {
        return pushTVTime;
    }

    public void setPushTVTime(Long pushTVTime) {
        this.pushTVTime = pushTVTime;
    }

    public String getDefaultStream() {
        return defaultStream;
    }

    public void setDefaultStream(String defaultStream) {
        this.defaultStream = defaultStream;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }

    public List<Stream> getNormalStreams() {
        return normalStreams;
    }

    public void setNormalStreams(List<Stream> normalStreams) {
        this.normalStreams = normalStreams;
    }

    public List<Stream> getTheatreStreams() {
        return theatreStreams;
    }

    public void setTheatreStreams(List<Stream> theatreStreams) {
        this.theatreStreams = theatreStreams;
    }

    public List<Stream> getThreeDStreams() {
        return threeDStreams;
    }

    public void setThreeDStreams(List<Stream> threeDStreams) {
        this.threeDStreams = threeDStreams;
    }

    public String getPlayPlatform() {
        return playPlatform;
    }

    public void setPlayPlatform(String playPlatform) {
        this.playPlatform = playPlatform;
    }

    public String getDownloadPlatform() {
        return downloadPlatform;
    }

    public void setDownloadPlatform(String downloadPlatform) {
        this.downloadPlatform = downloadPlatform;
    }

    public Integer getDownload() {
        return download;
    }

    public void setDownload(Integer download) {
        this.download = download;
    }

    public String getIfCharge() {
        return ifCharge;
    }

    public void setIfCharge(String ifCharge) {
        this.ifCharge = ifCharge;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getBackUrl2() {
        return backUrl2;
    }

    public void setBackUrl2(String backUrl2) {
        this.backUrl2 = backUrl2;
    }

    public Long getGsize() {
        return gsize;
    }

    public void setGsize(Long gsize) {
        this.gsize = gsize;
    }

    public String getCurrentStream() {
        return currentStream;
    }

    public void setCurrentStream(String currentStream) {
        this.currentStream = currentStream;
    }

    public String getCurrentStreamKps() {
        return currentStreamKps;
    }

    public void setCurrentStreamKps(String currentStreamKps) {
        this.currentStreamKps = currentStreamKps;
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

    public String getTipMsg() {
        return tipMsg;
    }

    public void setTipMsg(String tipMsg) {
        this.tipMsg = tipMsg;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public Integer getVideoHeadTime() {
        return videoHeadTime;
    }

    public void setVideoHeadTime(Integer videoHeadTime) {
        this.videoHeadTime = videoHeadTime;
    }

    public Integer getVideoTailTime() {
        return videoTailTime;
    }

    public void setVideoTailTime(Integer videoTailTime) {
        this.videoTailTime = videoTailTime;
    }

    public String getStatisticsCode() {
        return statisticsCode;
    }

    public void setStatisticsCode(String statisticsCode) {
        this.statisticsCode = statisticsCode;
    }

    public Integer getNeedWaterMarking() {
        return needWaterMarking;
    }

    public void setNeedWaterMarking(Integer needWaterMarking) {
        this.needWaterMarking = needWaterMarking;
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

    public String getSoundInkCodeUrl() {
        return soundInkCodeUrl;
    }

    public void setSoundInkCodeUrl(String soundInkCodeUrl) {
        this.soundInkCodeUrl = soundInkCodeUrl;
    }

    public Integer getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(Integer seriesType) {
        this.seriesType = seriesType;
    }

    public Integer getIs_rec() {
        return is_rec;
    }

    public void setIs_rec(Integer is_rec) {
        this.is_rec = is_rec;
    }

    public Map<String, String> getAllPlayUrl() {
        return allPlayUrl;
    }

    public void setAllPlayUrl(Map<String, String> allPlayUrl) {
        this.allPlayUrl = allPlayUrl;
    }

    public Integer getIsPlayLock() {
        return isPlayLock;
    }

    public void setIsPlayLock(Integer isPlayLock) {
        this.isPlayLock = isPlayLock;
    }

    public List<WatchingFocusCache> getWatchingFocus() {
        return watchingFocus;
    }

    public void setWatchingFocus(List<WatchingFocusCache> watchingFocus) {
        this.watchingFocus = watchingFocus;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Object getRelation() {
        return relation;
    }

    public void setRelation(Object relation) {
        this.relation = relation;
    }

    public ChargeDto getChargeObj() {
        return chargeObj;
    }

    public void setChargeObj(ChargeDto chargeObj) {
        this.chargeObj = chargeObj;
    }

    public Integer getPlayType() {
        return playType;
    }

    public void setPlayType(Integer playType) {
        this.playType = playType;
    }

}
