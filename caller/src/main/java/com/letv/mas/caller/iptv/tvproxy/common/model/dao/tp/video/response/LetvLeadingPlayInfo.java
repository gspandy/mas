package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.io.Serializable;
import java.util.List;

/**
 * 领先版播放接口返回结果封装类
 * @author KevinYi
 */
public class LetvLeadingPlayInfo implements Serializable {
    public LetvLeadingPlayInfo() {

    }

    public LetvLeadingPlayInfo(Integer type) {
        this.type = type;// 标识0专辑、1视频
    }

    private Integer type;
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
    private String videoType;

    /**
     * 第几集、第几期
     */
    private String episode;

    /**
     * 预告片类型 0 有效的预告片(0<跟播集数<3) 1 过期的预告片
     */
    private Integer preType;

    private String img;

    private String albumImg;

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
     * 时长(毫秒)
     */
    private Long duration;

    private String defaultStream;

    /**
     * 普通码流
     */
    private List<LetvPlayStream> normalStreams;

    /**
     * 影院声码流
     */
    private List<LetvPlayStream> theatreStreams;

    /**
     * 3D码流
     */
    private List<LetvPlayStream> threeDStreams;
    /**
     * 推送平台
     */
    private String playPlatform;

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
     * 备用播放地址3
     */
    private String backUrl2;

    /**
     * 当前码流
     */
    private String currentStream;

    /**
     * 当前码流名称
     */
    private String currentStreamName;
    /**
     * 当前码流播放速度
     */
    private String currentStreamKps;

    /**
     * 当前免费码流 code :1300 name:高清
     */
    private LetvPlayStream freeStream;

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
     * 播放模式 1:正常播放 2:试看播放 3:350免费播放 4:院线试看 5:码流收费
     */
    private Integer playType;

    /**
     * 试看时长
     */
    private Long tryPlayTime;

    /**
     * 是否院线
     */
    private Long isYuanXian;

    private String tryPlayTipCode;

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
     * 单点付费
     */
    private String price;

    /**
     * 海报图，单点支付页面使用
     */
    private String poster;

    /**
     * 有效时长
     */
    private Integer expiredTime;

    /**
     * 付费类型 1支持单点付费
     */
    private Integer payType;

    /**
     * 家长锁 0否，1是
     */
    private Integer isPlayLock;

    private Integer vipStatus;// 0：非vip 1:vip

    /**
     * 有版权播放的平台
     */
    private Integer playCopyright;// 1:Web 0:TV

    private Integer danmu;// 弹幕开关 : 1开 0关

    /**
     * 当前视频在所属专辑的剧集列表数据中的分页信息
     */
    private Integer pageNum;

    /**
     * 领先版剧集列表接口（http://s.itv.letv.com/s/d/mobile/video/albumInfo/get.json?act=1
     * &page=0&albumid=10015173）中返回了page，这里想复用LetvLeadingPlayInfo
     */
    private Integer page;

    /**
     * Cooperation platform，合作平台id列表，多个id使用英文逗号","拼接
     */
    private String coopPlatform;

    private List<AudioTrack> audioTracks;// 音轨列表

    private List<Subtitle> subtitles;// 字幕列表

    private AudioTrack currentAudioTrack;// 默认音轨

    private Subtitle currentSubtitle;// 默认字幕

    /**
     * 专辑类型id，如180001--正片等；2016-11-16lecom需求添加，播放下拉列表要做“综艺样式展示”需求，
     * 需要在播放接口获取专辑相关信息
     */
    private Integer albumType;

    /**
     * 是不是综艺类型,1是 0否,2016-11-16lecom需求添加，播放下拉列表要做“综艺样式展示”需求，
     * 需要在播放接口获取专辑相关信息
     */
    private Integer varietyShow;

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public Integer getHasAlbum() {
        return this.hasAlbum;
    }

    public void setHasAlbum(Integer hasAlbum) {
        this.hasAlbum = hasAlbum;
    }

    public Integer getOrderInAlbum() {
        return this.orderInAlbum;
    }

    public void setOrderInAlbum(Integer orderInAlbum) {
        this.orderInAlbum = orderInAlbum;
    }

    public Integer getPositive() {
        return this.positive;
    }

    public void setPositive(Integer positive) {
        this.positive = positive;
    }

    public Integer getVideoTypeId() {
        return this.videoTypeId;
    }

    public void setVideoTypeId(Integer videoTypeId) {
        this.videoTypeId = videoTypeId;
    }

    public String getVideoType() {
        return this.videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getEpisode() {
        return this.episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public Integer getPreType() {
        return this.preType;
    }

    public void setPreType(Integer preType) {
        this.preType = preType;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAlbumImg() {
        return this.albumImg;
    }

    public void setAlbumImg(String albumImg) {
        this.albumImg = albumImg;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return this.subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getViewPic() {
        return this.viewPic;
    }

    public void setViewPic(String viewPic) {
        this.viewPic = viewPic;
    }

    public Long getDuration() {
        return this.duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getDefaultStream() {
        return this.defaultStream;
    }

    public void setDefaultStream(String defaultStream) {
        this.defaultStream = defaultStream;
    }

    public List<LetvPlayStream> getNormalStreams() {
        return this.normalStreams;
    }

    public void setNormalStreams(List<LetvPlayStream> normalStreams) {
        this.normalStreams = normalStreams;
    }

    public List<LetvPlayStream> getTheatreStreams() {
        return this.theatreStreams;
    }

    public void setTheatreStreams(List<LetvPlayStream> theatreStreams) {
        this.theatreStreams = theatreStreams;
    }

    public List<LetvPlayStream> getThreeDStreams() {
        return this.threeDStreams;
    }

    public void setThreeDStreams(List<LetvPlayStream> threeDStreams) {
        this.threeDStreams = threeDStreams;
    }

    public String getPlayPlatform() {
        return this.playPlatform;
    }

    public void setPlayPlatform(String playPlatform) {
        this.playPlatform = playPlatform;
    }

    public String getIfCharge() {
        return this.ifCharge;
    }

    public void setIfCharge(String ifCharge) {
        this.ifCharge = ifCharge;
    }

    public String getStorePath() {
        return this.storePath;
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

    public String getCurrentStream() {
        return this.currentStream;
    }

    public void setCurrentStream(String currentStream) {
        this.currentStream = currentStream;
    }

    public String getCurrentStreamName() {
        return this.currentStreamName;
    }

    public void setCurrentStreamName(String currentStreamName) {
        this.currentStreamName = currentStreamName;
    }

    public String getCurrentStreamKps() {
        return this.currentStreamKps;
    }

    public void setCurrentStreamKps(String currentStreamKps) {
        this.currentStreamKps = currentStreamKps;
    }

    public LetvPlayStream getFreeStream() {
        return this.freeStream;
    }

    public void setFreeStream(LetvPlayStream freeStream) {
        this.freeStream = freeStream;
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

    public Integer getPlayType() {
        return this.playType;
    }

    public void setPlayType(Integer playType) {
        this.playType = playType;
    }

    public Long getTryPlayTime() {
        return this.tryPlayTime;
    }

    public void setTryPlayTime(Long tryPlayTime) {
        this.tryPlayTime = tryPlayTime;
    }

    public Long getIsYuanXian() {
        return this.isYuanXian;
    }

    public void setIsYuanXian(Long isYuanXian) {
        this.isYuanXian = isYuanXian;
    }

    public String getTryPlayTipCode() {
        return this.tryPlayTipCode;
    }

    public void setTryPlayTipCode(String tryPlayTipCode) {
        this.tryPlayTipCode = tryPlayTipCode;
    }

    public String getTryPlayTipMsg() {
        return this.tryPlayTipMsg;
    }

    public void setTryPlayTipMsg(String tryPlayTipMsg) {
        this.tryPlayTipMsg = tryPlayTipMsg;
    }

    public String getDrmFlagId() {
        return this.drmFlagId;
    }

    public void setDrmFlagId(String drmFlagId) {
        this.drmFlagId = drmFlagId;
    }

    public String getDrmTokenUrl() {
        return this.drmTokenUrl;
    }

    public void setDrmTokenUrl(String drmTokenUrl) {
        this.drmTokenUrl = drmTokenUrl;
    }

    public String getSoundInkCodeUrl() {
        return this.soundInkCodeUrl;
    }

    public void setSoundInkCodeUrl(String soundInkCodeUrl) {
        this.soundInkCodeUrl = soundInkCodeUrl;
    }

    public Integer getSeriesType() {
        return this.seriesType;
    }

    public void setSeriesType(Integer seriesType) {
        this.seriesType = seriesType;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPoster() {
        return this.poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Integer getExpiredTime() {
        return this.expiredTime;
    }

    public void setExpiredTime(Integer expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Integer getPayType() {
        return this.payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getIsPlayLock() {
        return this.isPlayLock;
    }

    public void setIsPlayLock(Integer isPlayLock) {
        this.isPlayLock = isPlayLock;
    }

    public Integer getVipStatus() {
        return this.vipStatus;
    }

    public void setVipStatus(Integer vipStatus) {
        this.vipStatus = vipStatus;
    }

    public Integer getPlayCopyright() {
        return this.playCopyright;
    }

    public void setPlayCopyright(Integer playCopyright) {
        this.playCopyright = playCopyright;
    }

    public Integer getDanmu() {
        return this.danmu;
    }

    public void setDanmu(Integer danmu) {
        this.danmu = danmu;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getCoopPlatform() {
        return coopPlatform;
    }

    public void setCoopPlatform(String coopPlatform) {
        this.coopPlatform = coopPlatform;
    }

    public static class AudioTrack {
        private String langCode;// 语种id
        private String lang;// 语种
        private String atype;// 音质
        private String atId;// 音轨id

        public String getLangCode() {
            return langCode;
        }

        public void setLangCode(String langCode) {
            this.langCode = langCode;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getAtId() {
            return atId;
        }

        public void setAtId(String atId) {
            this.atId = atId;
        }

        public String getAtype() {
            return atype;
        }

        public void setAtype(String atype) {
            this.atype = atype;
        }

    }

    public static class Subtitle {
        private String id;// 字幕id
        private String name;// 字幕
        private String addr;// 字幕地址

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

    }

    public List<AudioTrack> getAudioTracks() {
        return audioTracks;
    }

    public void setAudioTracks(List<AudioTrack> audioTracks) {
        this.audioTracks = audioTracks;
    }

    public AudioTrack getCurrentAudioTrack() {
        return currentAudioTrack;
    }

    public void setCurrentAudioTrack(AudioTrack currentAudioTrack) {
        this.currentAudioTrack = currentAudioTrack;
    }

    public List<Subtitle> getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(List<Subtitle> subtitles) {
        this.subtitles = subtitles;
    }

    public Subtitle getCurrentSubtitle() {
        return currentSubtitle;
    }

    public void setCurrentSubtitle(Subtitle currentSubtitle) {
        this.currentSubtitle = currentSubtitle;
    }

    public Integer getAlbumType() {
        return albumType;
    }

    public void setAlbumType(Integer albumType) {
        this.albumType = albumType;
    }

    public Integer getVarietyShow() {
        return varietyShow;
    }

    public void setVarietyShow(Integer varietyShow) {
        this.varietyShow = varietyShow;
    }

}
