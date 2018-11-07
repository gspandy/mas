package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.CopyFieldAnnotation;

import java.util.List;
import java.util.Map;

public class MmsVideoInfo {

    private static final long serialVersionUID = -8437222851338880468L;

    private Long id;// 视频id

    @CopyFieldAnnotation(isSplit = true, mapValue = "actor")
    private Map<String, String> actor;// 演员

    @CopyFieldAnnotation("actor_play")
    private String actorPlay;// 饰演角色

    @CopyFieldAnnotation("ad_point")
    private String adPoint;// 广告插入点

    private String alias;// 别名
    private Integer porder;// 在专辑顺序

    @CopyFieldAnnotation(isSplit = true, mapKey = "area", mapValue = "area_name")
    private Map<String, String> area;// 地区

    private Integer btime;// 片头时间

    @CopyFieldAnnotation(isSplit = true, mapKey = "carfilm_type", mapValue = "carfilm_type_name")
    private Map<Integer, String> carfilmType;// 汽车频道影片分类

    @CopyFieldAnnotation(isSplit = true, mapKey = "cartoon_style", mapValue = "cartoon_style_name")
    private Map<Integer, String> cartoonStyle;// 动漫分类

    @CopyFieldAnnotation(isSplit = true, mapKey = "category", mapValue = "category_name", valueToString = false)
    private Map<Integer, String> category;// 分类

    @CopyFieldAnnotation(isSplit = true, mapValue = "compere")
    private Map<String, String> compere;// 主持人

    @CopyFieldAnnotation(isSplit = true, mapKey = "contentRatingId", mapValue = "contentRatingValue", valueToString = false)
    private Map<Integer, String> contentRating;// 内容分级

    @CopyFieldAnnotation(isCopy = false)
    private String controlAreas;// 海外屏蔽或者允许的地区

    @CopyFieldAnnotation("copyright_company")
    private String copyrightCompany;// 版权公司

    @CopyFieldAnnotation("copyright_start")
    private String copyrightStart;// 版权结束时间

    @CopyFieldAnnotation("copyright_end")
    private String copyrightEnd;// 版权开始时间

    @CopyFieldAnnotation(isSplit = true, mapKey = "copyright_type", mapValue = "copyright_type_name")
    private Map<Integer, String> copyrightType;// 版权类型

    @CopyFieldAnnotation(isCopy = false)
    private String createTime;
    private Integer deleted;// 删除标记
    private String description;// 介绍

    @CopyFieldAnnotation(isSplit = true, mapValue = "directory")
    private Map<Object, String> directory;// 导演

    @CopyFieldAnnotation(isSplit = true, mapKey = "disable_type", mapValue = "disable_type_name")
    private Map<Integer, String> disableType;// 屏蔽类型

    @CopyFieldAnnotation(isSplit = true, mapKey = "download_platform")
    private Map<String, String> downloadPlatform;// 允许下载平台

    private Integer duration;// 时长，单位-秒
    private String episode;// 总集数
    private Integer etime;// 片尾时间

    @CopyFieldAnnotation(isSplit = true, mapKey = "field_type", mapValue = "field_type_name", valueToString = false)
    private Map<Integer, String> fieldType;// 领域类型

    @CopyFieldAnnotation("first_play_time")
    private String firstPlayTime;// 体育频道中的赛季

    @CopyFieldAnnotation(isSplit = true, mapValue = "guest")
    private Map<Integer, String> guest;// 嘉宾

    @CopyFieldAnnotation(isSplit = true, mapValue = "instructor")
    private Map<Integer, String> instructor;// 讲师

    private Integer issue;// 期数

    @CopyFieldAnnotation("issue_company")
    private String issueCompany;// 发行公司

    @CopyFieldAnnotation(isSplit = true, mapKey = "language", mapValue = "language_name", valueToString = false)
    private Map<Integer, String> language;// 语言

    @CopyFieldAnnotation(isSplit = true, mapKey = "letv_make_style", mapValue = "letv_make_style_name", valueToString = false)
    private Map<Integer, String> letvMakeStyle;// 乐视制造分类

    @CopyFieldAnnotation(isSplit = true, mapKey = "letv_produce_style", mapValue = "letv_produce_style_name", valueToString = false)
    private Map<Integer, String> letvProduceStyle;// 乐视出品分类

    private String maker;// 制片人
    private String mid;// 媒资ID
    private String mobileTitle;// 移动端标题

    @CopyFieldAnnotation("music_authors")
    private String musicAuthors;// 音乐作词人

    @CopyFieldAnnotation("music_compose")
    private String musicCompose;// 音乐作曲人

    @CopyFieldAnnotation(isSplit = true, mapKey = "music_style", mapValue = "music_style_name")
    private Map<Integer, String> musicStyle;// 音乐风格

    @CopyFieldAnnotation("name_cn")
    private String nameCn;// 中文名称

    @CopyFieldAnnotation("name_en")
    private String nameEn;// 英文名称

    @CopyFieldAnnotation("name_pinyin_abb")
    private String namePinyinAbb;// 名字中每个字拼音的首字母
    private String officialUrl;// 官方网址

    @CopyFieldAnnotation(isSplit = true, mapKey = "pay_platform")
    private Map<String, String> payPlatform;// 收费平台

    @CopyFieldAnnotation(isCopy = false)
    private Map<String, String> picAll;// 图片地址
    private Long pid;// 专辑ID

    @CopyFieldAnnotation(isSplit = true, mapKey = "play_platform")
    private Map<String, String> playPlatform;// 推送平台

    @CopyFieldAnnotation(isSplit = true, mapKey = "pop_style", mapValue = "pop_style_name", valueToString = false)
    private Map<Integer, String> popStyle;// 风尚频道分类

    @CopyFieldAnnotation(isSplit = true, mapKey = "pre", mapValue = "pre_name", valueToString = false)
    private Map<Integer, String> pre;// 关联类型，娱乐频道

    @CopyFieldAnnotation("record_company")
    private String recordCompany;// 唱片公司

    @CopyFieldAnnotation(isSplit = true, mapKey = "recreation_type", mapValue = "recreation_type_name")
    private Map<String, String> recreationType;// 娱乐频道 娱乐分类 原娱乐频道的专辑类型

    @CopyFieldAnnotation("relative_content")
    private String relativeContent;// 关联内容

    @CopyFieldAnnotation("release_date")
    private String releaseDate;// 发行时间
    private String remark;// 备注

    @CopyFieldAnnotation("schedule_publish_date")
    private String schedulePublishDate;// 定时发布时间

    private String school;// 公开课频道 学校字段
    private Float score;// 评分

    @CopyFieldAnnotation("short_desc")
    private String shortDesc;// 简要描述
    private String singer;// 歌手

    @CopyFieldAnnotation(isSplit = true, mapKey = "singer_type", mapValue = "singer_type_name")
    private Map<Integer, String> singerType;// 歌手类型

    @CopyFieldAnnotation("single_name")
    private String singleName;// 单集名称 动漫频道

    @CopyFieldAnnotation(isSplit = true, mapKey = "source_id")
    private Map<Integer, String> sourceId;// 来源

    @CopyFieldAnnotation(isSplit = true, mapKey = "sports_type", mapValue = "sports_type_name", valueToString = false)
    private Map<Integer, String> sportsType;// 体育分类

    @CopyFieldAnnotation(isSplit = true, mapValue = "starring")
    private Map<Integer, String> starring;// 主演

    @CopyFieldAnnotation("starring_play")
    private String starringPlay;// 主演饰演角色
    private Integer status;// 发布状态

    // @CopyFieldAnnotation(isSplit = true, mapKey = "style", mapValue =
    // "style_name", valueToString = false)
    @CopyFieldAnnotation(isCopy = false)
    private Map<String, String> style;// 分类

    // private Integer style;

    // @CopyFieldAnnotation(isSplit = true, mapKey = "sub_category", mapValue =
    // "sub_category_name")
    // 2016-04-01，原key定义为Integer型，媒资返回数据错误，key值返回"nul"（实际媒资专辑、视频接口返回所有map数据接口的key均为String型），暂取消自动解析，改为代码解析
    @CopyFieldAnnotation(isCopy = false)
    private Map<Integer, String> subCategory;// 子分类

    @CopyFieldAnnotation("sub_title")
    private String subTitle;// 副标题
    private String tag;// 标签

    @CopyFieldAnnotation(isSplit = true, mapValue = "team")
    private Map<Integer, String> team;// 球队

    @CopyFieldAnnotation("trans_code_prefix")
    private String transCodePrefix;// 转码视频截图前缀

    @CopyFieldAnnotation(isSplit = true, mapKey = "travel_theme", mapValue = "travel_theme_name", valueToString = false)
    private Map<Integer, String> travelTheme;// 旅游主题

    @CopyFieldAnnotation(isSplit = true, mapKey = "travel_type", mapValue = "travel_type_name", valueToString = false)
    private Map<Integer, String> travelType;// 旅游类型

    @CopyFieldAnnotation("tv_title")
    private String tvTitle;// TV端标题
    private String updateTime;// 修改时间
    private Long userId;// 创建用户ID

    @CopyFieldAnnotation(isSplit = true, mapKey = "video_type", mapValue = "video_type_name", valueToString = false)
    private Map<Integer, String> videoType;// 视频类型
    private Map<Integer, List<MmsVideoCode>> videocode;// 视频码率
    // private String watchingFocus;//看点
    private String websiteTitle;// 网站标题
    private Integer logoNum;// 是否有水印

    @CopyFieldAnnotation(isSplit = true, mapKey = "drmFlagId")
    private Map<Integer, String> drmFlag;// DRM加密码流
    private Integer playControlPlatformGuoguang;
    private Integer playControlPlatformHuashu;

    @CopyFieldAnnotation("isPushChild")
    private Integer isPushChild;// 是否推送到儿童1推送0不推送

    @CopyFieldAnnotation("videoPic")
    private String videoPic;

    private Integer isPlayLock;// 家长锁功能0否，1是

    private String coopPlatform; // Cooperation platform
    /**
     * 当前视频在各终端首次上映时间，key--终端编号，web-420001，mobile-420003，pad-420005，tv-420007；
     * value--上映时间，"yyyy-MM-dd HH:mm:ss"格式字符串
     */
    private Map<String, String> platformFirstOnTime;
    @CopyFieldAnnotation("isDanmaku")
    private Integer isDanmaku;// 是否有弹幕

    // for tvod icon type vsr
    private Integer isPay;

    private String payType;

    private Integer isCoupon;

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getIsCoupon() {
        return isCoupon;
    }

    public void setIsCoupon(Integer isCoupon) {
        this.isCoupon = isCoupon;
    }

    public Integer getIsDanmaku() {
        return isDanmaku;
    }

    public void setIsDanmaku(Integer isDanmaku) {
        this.isDanmaku = isDanmaku;
    }

    public Integer getPlayControlPlatformHuashu() {
        return this.playControlPlatformHuashu;
    }

    public void setPlayControlPlatformHuashu(Integer playControlPlatformHuashu) {
        this.playControlPlatformHuashu = playControlPlatformHuashu;
    }

    public Integer getPlayControlPlatformGuoguang() {
        return this.playControlPlatformGuoguang;
    }

    public void setPlayControlPlatformGuoguang(Integer playControlPlatformGuoguang) {
        this.playControlPlatformGuoguang = playControlPlatformGuoguang;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getActor() {
        return this.actor;
    }

    public void setActor(Map<String, String> actor) {
        this.actor = actor;
    }

    public String getActorPlay() {
        return this.actorPlay;
    }

    public Map<Integer, String> getContentRating() {
        return contentRating;
    }

    public void setContentRating(Map<Integer, String> contentRating) {
        this.contentRating = contentRating;
    }

    public void setActorPlay(String actorPlay) {
        this.actorPlay = actorPlay;
    }

    public String getAdPoint() {
        return this.adPoint;
    }

    public void setAdPoint(String adPoint) {
        this.adPoint = adPoint;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Map<String, String> getArea() {
        return this.area;
    }

    public void setArea(Map<String, String> area) {
        this.area = area;
    }

    public Integer getBtime() {
        return this.btime;
    }

    public void setBtime(Integer btime) {
        this.btime = btime;
    }

    public Map<Integer, String> getCarfilmType() {
        return this.carfilmType;
    }

    public void setCarfilmType(Map<Integer, String> carfilmType) {
        this.carfilmType = carfilmType;
    }

    public Map<Integer, String> getCartoonStyle() {
        return this.cartoonStyle;
    }

    public void setCartoonStyle(Map<Integer, String> cartoonStyle) {
        this.cartoonStyle = cartoonStyle;
    }

    public Map<Integer, String> getCategory() {
        return this.category;
    }

    public void setCategory(Map<Integer, String> category) {
        this.category = category;
    }

    public Map<String, String> getCompere() {
        return this.compere;
    }

    public void setCompere(Map<String, String> compere) {
        this.compere = compere;
    }

    public String getControlAreas() {
        return this.controlAreas;
    }

    public void setControlAreas(String controlAreas) {
        this.controlAreas = controlAreas;
    }

    public String getCopyrightCompany() {
        return this.copyrightCompany;
    }

    public void setCopyrightCompany(String copyrightCompany) {
        this.copyrightCompany = copyrightCompany;
    }

    public String getCopyrightStart() {
        return this.copyrightStart;
    }

    public void setCopyrightStart(String copyrightStart) {
        this.copyrightStart = copyrightStart;
    }

    public String getCopyrightEnd() {
        return this.copyrightEnd;
    }

    public void setCopyrightEnd(String copyrightEnd) {
        this.copyrightEnd = copyrightEnd;
    }

    public Map<Integer, String> getCopyrightType() {
        return this.copyrightType;
    }

    public void setCopyrightType(Map<Integer, String> copyrightType) {
        this.copyrightType = copyrightType;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<Object, String> getDirectory() {
        return this.directory;
    }

    public void setDirectory(Map<Object, String> directory) {
        this.directory = directory;
    }

    public Map<Integer, String> getDisableType() {
        return this.disableType;
    }

    public void setDisableType(Map<Integer, String> disableType) {
        this.disableType = disableType;
    }

    public Map<String, String> getDownloadPlatform() {
        return this.downloadPlatform;
    }

    public void setDownloadPlatform(Map<String, String> downloadPlatform) {
        this.downloadPlatform = downloadPlatform;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getEpisode() {
        return this.episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public Integer getEtime() {
        return this.etime;
    }

    public void setEtime(Integer etime) {
        this.etime = etime;
    }

    public Map<Integer, String> getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(Map<Integer, String> fieldType) {
        this.fieldType = fieldType;
    }

    public String getFirstPlayTime() {
        return this.firstPlayTime;
    }

    public void setFirstPlayTime(String firstPlayTime) {
        this.firstPlayTime = firstPlayTime;
    }

    public Map<Integer, String> getGuest() {
        return this.guest;
    }

    public void setGuest(Map<Integer, String> guest) {
        this.guest = guest;
    }

    public Map<Integer, String> getInstructor() {
        return this.instructor;
    }

    public void setInstructor(Map<Integer, String> instructor) {
        this.instructor = instructor;
    }

    public Integer getIssue() {
        return this.issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public String getIssueCompany() {
        return this.issueCompany;
    }

    public void setIssueCompany(String issueCompany) {
        this.issueCompany = issueCompany;
    }

    public Map<Integer, String> getLanguage() {
        return this.language;
    }

    public void setLanguage(Map<Integer, String> language) {
        this.language = language;
    }

    public Map<Integer, String> getLetvMakeStyle() {
        return this.letvMakeStyle;
    }

    public void setLetvMakeStyle(Map<Integer, String> letvMakeStyle) {
        this.letvMakeStyle = letvMakeStyle;
    }

    public Map<Integer, String> getLetvProduceStyle() {
        return this.letvProduceStyle;
    }

    public void setLetvProduceStyle(Map<Integer, String> letvProduceStyle) {
        this.letvProduceStyle = letvProduceStyle;
    }

    public String getMid() {
        return this.mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMobileTitle() {
        return this.mobileTitle;
    }

    public void setMobileTitle(String mobileTitle) {
        this.mobileTitle = mobileTitle;
    }

    public String getMusicAuthors() {
        return this.musicAuthors;
    }

    public void setMusicAuthors(String musicAuthors) {
        this.musicAuthors = musicAuthors;
    }

    public String getMusicCompose() {
        return this.musicCompose;
    }

    public void setMusicCompose(String musicCompose) {
        this.musicCompose = musicCompose;
    }

    public Map<Integer, String> getMusicStyle() {
        return this.musicStyle;
    }

    public void setMusicStyle(Map<Integer, String> musicStyle) {
        this.musicStyle = musicStyle;
    }

    public String getNameCn() {
        return this.nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getNameEn() {
        return this.nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNamePinyinAbb() {
        return this.namePinyinAbb;
    }

    public void setNamePinyinAbb(String namePinyinAbb) {
        this.namePinyinAbb = namePinyinAbb;
    }

    public String getOfficialUrl() {
        return this.officialUrl;
    }

    public void setOfficialUrl(String officialUrl) {
        this.officialUrl = officialUrl;
    }

    public Map<String, String> getPayPlatform() {
        return this.payPlatform;
    }

    public void setPayPlatform(Map<String, String> payPlatform) {
        this.payPlatform = payPlatform;
    }

    public Map<String, String> getPicAll() {
        return this.picAll;
    }

    public void setPicAll(Map<String, String> picAll) {
        this.picAll = picAll;
    }

    public Long getPid() {
        return this.pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Map<String, String> getPlayPlatform() {
        return this.playPlatform;
    }

    public void setPlayPlatform(Map<String, String> playPlatform) {
        this.playPlatform = playPlatform;
    }

    public Map<Integer, String> getPopStyle() {
        return this.popStyle;
    }

    public void setPopStyle(Map<Integer, String> popStyle) {
        this.popStyle = popStyle;
    }

    public Map<Integer, String> getPre() {
        return this.pre;
    }

    public void setPre(Map<Integer, String> pre) {
        this.pre = pre;
    }

    public String getRecordCompany() {
        return this.recordCompany;
    }

    public void setRecordCompany(String recordCompany) {
        this.recordCompany = recordCompany;
    }

    public Map<String, String> getRecreationType() {
        return this.recreationType;
    }

    public void setRecreationType(Map<String, String> recreationType) {
        this.recreationType = recreationType;
    }

    public String getRelativeContent() {
        return this.relativeContent;
    }

    public void setRelativeContent(String relativeContent) {
        this.relativeContent = relativeContent;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSchedulePublishDate() {
        return this.schedulePublishDate;
    }

    public void setSchedulePublishDate(String schedulePublishDate) {
        this.schedulePublishDate = schedulePublishDate;
    }

    public String getMaker() {
        return this.maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getTransCodePrefix() {
        return this.transCodePrefix;
    }

    public void setTransCodePrefix(String transCodePrefix) {
        this.transCodePrefix = transCodePrefix;
    }

    public String getSchool() {
        return this.school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Float getScore() {
        return this.score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getShortDesc() {
        return this.shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getSinger() {
        return this.singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public Map<Integer, String> getSingerType() {
        return this.singerType;
    }

    public void setSingerType(Map<Integer, String> singerType) {
        this.singerType = singerType;
    }

    public String getSingleName() {
        return this.singleName;
    }

    public void setSingleName(String singleName) {
        this.singleName = singleName;
    }

    public Map<Integer, String> getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(Map<Integer, String> sourceId) {
        this.sourceId = sourceId;
    }

    public Map<Integer, String> getSportsType() {
        return this.sportsType;
    }

    public void setSportsType(Map<Integer, String> sportsType) {
        this.sportsType = sportsType;
    }

    public Map<Integer, String> getStarring() {
        return this.starring;
    }

    public void setStarring(Map<Integer, String> starring) {
        this.starring = starring;
    }

    public String getStarringPlay() {
        return this.starringPlay;
    }

    public void setStarringPlay(String starringPlay) {
        this.starringPlay = starringPlay;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /*
     * public Map<Integer, String> getStyle() {
     * return style;
     * }
     * public void setStyle(Map<Integer, String> style) {
     * this.style = style;
     * }
     */

    public Map<Integer, String> getSubCategory() {
        return this.subCategory;
    }

    /*
     * public Integer getStyle() {
     * return style;
     * }
     * public void setStyle(Integer style) {
     * this.style = style;
     * }
     */

    public void setSubCategory(Map<Integer, String> subCategory) {
        this.subCategory = subCategory;
    }

    public Map<String, String> getStyle() {
        return this.style;
    }

    public void setStyle(Map<String, String> style) {
        this.style = style;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Map<Integer, String> getTeam() {
        return this.team;
    }

    public void setTeam(Map<Integer, String> team) {
        this.team = team;
    }

    public Map<Integer, String> getTravelTheme() {
        return this.travelTheme;
    }

    public void setTravelTheme(Map<Integer, String> travelTheme) {
        this.travelTheme = travelTheme;
    }

    public Map<Integer, String> getTravelType() {
        return this.travelType;
    }

    public void setTravelType(Map<Integer, String> travelType) {
        this.travelType = travelType;
    }

    public String getTvTitle() {
        return this.tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Map<Integer, String> getVideoType() {
        return this.videoType;
    }

    public void setVideoType(Map<Integer, String> videoType) {
        this.videoType = videoType;
    }

    public Map<Integer, List<MmsVideoCode>> getVideocode() {
        return this.videocode;
    }

    public void setVideocode(Map<Integer, List<MmsVideoCode>> videocode) {
        this.videocode = videocode;
    }

    /*
     * public String getWatchingFocus() {
     * return watchingFocus;
     * }
     * public void setWatchingFocus(String watchingFocus) {
     * this.watchingFocus = watchingFocus;
     * }
     */
    public String getWebsiteTitle() {
        return this.websiteTitle;
    }

    public void setWebsiteTitle(String websiteTitle) {
        this.websiteTitle = websiteTitle;
    }

    public Integer getPorder() {
        return this.porder;
    }

    public void setPorder(Integer porder) {
        this.porder = porder;
    }

    public Integer getLogoNum() {
        return this.logoNum;
    }

    public void setLogoNum(Integer logoNum) {
        this.logoNum = logoNum;
    }

    public Map<Integer, String> getDrmFlag() {
        return this.drmFlag;
    }

    public void setDrmFlag(Map<Integer, String> drmFlag) {
        this.drmFlag = drmFlag;
    }

    public String getVideoPic() {
        return this.videoPic;
    }

    public void setVideoPic(String videoPic) {
        this.videoPic = videoPic;
    }

    public Integer getIsPlayLock() {
        return this.isPlayLock;
    }

    public void setIsPlayLock(Integer isPlayLock) {
        this.isPlayLock = isPlayLock;
    }

    public Map<String, String> getPlatformFirstOnTime() {
        return this.platformFirstOnTime;
    }

    public void setPlatformFirstOnTime(Map<String, String> platformFirstOnTime) {
        this.platformFirstOnTime = platformFirstOnTime;
    }

    public Integer getIsPushChild() {
        return isPushChild;
    }

    public void setIsPushChild(Integer isPushChild) {
        this.isPushChild = isPushChild;
    }

    public String getCoopPlatform() {
        return coopPlatform;
    }

    public void setCoopPlatform(String coopPlatform) {
        this.coopPlatform = coopPlatform;
    }
}
