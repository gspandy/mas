package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.CopyFieldAnnotation;

/**
 * Created by leeco on 18/9/12.
 */
public class WorksAlbumInfo {
    private Long id; // 专辑id

    @CopyFieldAnnotation("name_cn")
    private String name; // 名称

    private String alias; // 别名

    @CopyFieldAnnotation("name_en")
    private String nameEn; // 英文名称

    @CopyFieldAnnotation("sub_title")
    private String subtitle; // 副标题

    private Integer category; // 频道

    @CopyFieldAnnotation("coopPlatform")
    private Integer siteId; // 站点id

    @CopyFieldAnnotation("status")
    private Integer state; // TODO: 状态

    @CopyFieldAnnotation(isCopy = false)
    private Integer sysAdd; // 来源方式

    @CopyFieldAnnotation("album_type")
    private Integer albumType; // 专辑类型

    @CopyFieldAnnotation(isCopy = false)
    private Integer screenYear; // 上映年份

    @CopyFieldAnnotation("tvFirstOnTime")
    private String screenTime; // 上映时间

    @CopyFieldAnnotation(isCopy = false)
    private String director; // 导演

    @CopyFieldAnnotation("directory")
    private String directorName; // 导演名字

    @CopyFieldAnnotation("maker")
    private String prodManager; // 制片人

    @CopyFieldAnnotation(isCopy = false)
    private String actor; // 演员

    @CopyFieldAnnotation("actor")
    private String actorName; // 演员名字

    @CopyFieldAnnotation("actor_play")
    private String actorPlay; // 饰演角色

    @CopyFieldAnnotation("dub")
    private String dubbing; // 配音

    @CopyFieldAnnotation("screen_writer")
    private String screenwriter; // 编剧

    private String compere; // 主持人

    @CopyFieldAnnotation("originator")
    private String author; // 原著

    private String area; // 地区

    @CopyFieldAnnotation("area_name")
    private String areaName;// 地区名字

    @CopyFieldAnnotation("sub_category")
    private String subCategory; // 子分类

    @CopyFieldAnnotation("sub_category_name")
    private String subCategoryName;

    private String language; // 语言

    @CopyFieldAnnotation("language_name")
    private String languageName;// 语言名字

    @CopyFieldAnnotation("episode")
    private Integer episodes; // 总集数

    @CopyFieldAnnotation("nowEpisodes")
    private String currentEpisode; // 当前集数

    @CopyFieldAnnotation(isCopy = false)
    private Integer episodeStatus; // 剧集更新状态

    @CopyFieldAnnotation(isCopy = false)
    private String updateFrequency; // 更新频率

    @CopyFieldAnnotation(isCopy = false)
    private String gatherUrl; // 专辑详情地址

    @CopyFieldAnnotation(isCopy = false)
    private String playLink; // 专辑默认播放地址

    @CopyFieldAnnotation("description")
    private String introduction; // 简介

    @CopyFieldAnnotation("score")
    private Float score; // 评分

    @CopyFieldAnnotation(isCopy = false)
    private Integer quality; // 质量

    @CopyFieldAnnotation("tag")
    private String tags; // 标签

    @CopyFieldAnnotation(isCopy = false)
    private String tagsName; // 标签

    @CopyFieldAnnotation(isCopy = false)
    private Long clusterId; // 合集id

    @CopyFieldAnnotation(isCopy = false)
    private Integer clusterOrder; // 顺序

    @CopyFieldAnnotation("playCount")
    private Long playcount;// 播放量

    private Integer isPay; // 是否付费（1：是，0：否）

    @CopyFieldAnnotation("pay_platform")
    private String payPlatform; // 付费平台

    @CopyFieldAnnotation(isCopy = false)
    private String createName; // 创建人;

    @CopyFieldAnnotation(isCopy = false)
    private String updateName; // 更新人

    @CopyFieldAnnotation("create_time")
    private String createTime; // 创建时间

    @CopyFieldAnnotation("update_time")
    private String updateTime; // 更新时间

    @CopyFieldAnnotation(isCopy = false)
    private String bundingTime; // 绑定时间

    @CopyFieldAnnotation(isCopy = false)
    private Integer bundingType; // 绑定类型

    @CopyFieldAnnotation("play_platform")
    private String playPlatform; // 播放平台

    @CopyFieldAnnotation(isCopy = false)
    private Long daycount;// 日播放量

    @CopyFieldAnnotation(isCopy = false)
    private Long weekcount;// 周播放量

    @CopyFieldAnnotation(isCopy = false)
    private Long monthcount;// 月播放量

    @CopyFieldAnnotation(isCopy = false)
    private String photosurl; //

    @CopyFieldAnnotation("play_streams")
    private String playStream;//

    @CopyFieldAnnotation(isCopy = false)
    private String extData; // 从媒资同步过来

    @CopyFieldAnnotation("download_platform")
    private String downloadPlatform;// 下载平台

    private String videoFollowTime;

    @CopyFieldAnnotation(isCopy = false)
    private String subLeid;// 从媒资同步的leid

    @CopyFieldAnnotation("is_homemade")
    private Integer isHomemade; // 是否自制（1：是，0：否）

    @CopyFieldAnnotation(isCopy = false)
    private Integer externalRank;

    @CopyFieldAnnotation("album_base_type")
    private Integer contentType; // 内容类型

    @CopyFieldAnnotation("cibn")
    private Integer isCibnChecked; // 国广过审

    @CopyFieldAnnotation("wasu")
    private Integer isWasuChecked;// 华数过审

    @CopyFieldAnnotation("externalId")
    private String externalId; // 第三方id

    @CopyFieldAnnotation(isCopy = false)
    private String externalPlayId; // 第三方启播id

    @CopyFieldAnnotation(isCopy = false)
    private String reason; // 原因

    @CopyFieldAnnotation(isCopy = false)
    private Integer isExclusive; // 是否独播（1：是，0：否

    @CopyFieldAnnotation(isCopy = false)
    private String doubanId; // 豆瓣id

    @CopyFieldAnnotation(isCopy = false)
    private String baikeId; // 百科id

    @CopyFieldAnnotation(isCopy = false)
    private String tvmaoId; // tv猫id

    @CopyFieldAnnotation(isCopy = false)
    private String mtimeId; // 时光网id

    private Integer deleted; // 是否删除（1：是，0：否）

    private Integer duration; // 时长（分）

    @CopyFieldAnnotation("pic_collections")
    private String img; // 图片json, {"比例":{"尺寸":URL}}, 具体参考
                        // http://wiki.letv.cn/pages/viewpage.action?pageId=75629445

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSysAdd() {
        return sysAdd;
    }

    public void setSysAdd(Integer sysAdd) {
        this.sysAdd = sysAdd;
    }

    public Integer getAlbumType() {
        return albumType;
    }

    public void setAlbumType(Integer albumType) {
        this.albumType = albumType;
    }

    public Integer getScreenYear() {
        return screenYear;
    }

    public void setScreenYear(Integer screenYear) {
        this.screenYear = screenYear;
    }

    public String getScreenTime() {
        return screenTime;
    }

    public void setScreenTime(String screenTime) {
        this.screenTime = screenTime;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getProdManager() {
        return prodManager;
    }

    public void setProdManager(String prodManager) {
        this.prodManager = prodManager;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActorPlay() {
        return actorPlay;
    }

    public void setActorPlay(String actorPlay) {
        this.actorPlay = actorPlay;
    }

    public String getDubbing() {
        return dubbing;
    }

    public void setDubbing(String dubbing) {
        this.dubbing = dubbing;
    }

    public String getScreenwriter() {
        return screenwriter;
    }

    public void setScreenwriter(String screenwriter) {
        this.screenwriter = screenwriter;
    }

    public String getCompere() {
        return compere;
    }

    public void setCompere(String compere) {
        this.compere = compere;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public String getCurrentEpisode() {
        return currentEpisode;
    }

    public void setCurrentEpisode(String currentEpisode) {
        this.currentEpisode = currentEpisode;
    }

    public Integer getEpisodeStatus() {
        return episodeStatus;
    }

    public void setEpisodeStatus(Integer episodeStatus) {
        this.episodeStatus = episodeStatus;
    }

    public String getUpdateFrequency() {
        return updateFrequency;
    }

    public void setUpdateFrequency(String updateFrequency) {
        this.updateFrequency = updateFrequency;
    }

    public String getGatherUrl() {
        return gatherUrl;
    }

    public void setGatherUrl(String gatherUrl) {
        this.gatherUrl = gatherUrl;
    }

    public String getPlayLink() {
        return playLink;
    }

    public void setPlayLink(String playLink) {
        this.playLink = playLink;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTagsName() {
        return tagsName;
    }

    public void setTagsName(String tagsName) {
        this.tagsName = tagsName;
    }

    public Long getClusterId() {
        return clusterId;
    }

    public void setClusterId(Long clusterId) {
        this.clusterId = clusterId;
    }

    public Integer getClusterOrder() {
        return clusterOrder;
    }

    public void setClusterOrder(Integer clusterOrder) {
        this.clusterOrder = clusterOrder;
    }

    public Long getPlaycount() {
        return playcount;
    }

    public void setPlaycount(Long playcount) {
        this.playcount = playcount;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public String getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(String payPlatform) {
        this.payPlatform = payPlatform;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getBundingTime() {
        return bundingTime;
    }

    public void setBundingTime(String bundingTime) {
        this.bundingTime = bundingTime;
    }

    public Integer getBundingType() {
        return bundingType;
    }

    public void setBundingType(Integer bundingType) {
        this.bundingType = bundingType;
    }

    public String getPlayPlatform() {
        return playPlatform;
    }

    public void setPlayPlatform(String playPlatform) {
        this.playPlatform = playPlatform;
    }

    public Long getDaycount() {
        return daycount;
    }

    public void setDaycount(Long daycount) {
        this.daycount = daycount;
    }

    public Long getWeekcount() {
        return weekcount;
    }

    public void setWeekcount(Long weekcount) {
        this.weekcount = weekcount;
    }

    public Long getMonthcount() {
        return monthcount;
    }

    public void setMonthcount(Long monthcount) {
        this.monthcount = monthcount;
    }

    public String getPhotosurl() {
        return photosurl;
    }

    public void setPhotosurl(String photosurl) {
        this.photosurl = photosurl;
    }

    public String getPlayStream() {
        return playStream;
    }

    public void setPlayStream(String playStream) {
        this.playStream = playStream;
    }

    public String getExtData() {
        return extData;
    }

    public void setExtData(String extData) {
        this.extData = extData;
    }

    public String getDownloadPlatform() {
        return downloadPlatform;
    }

    public void setDownloadPlatform(String downloadPlatform) {
        this.downloadPlatform = downloadPlatform;
    }

    public String getVideoFollowTime() {
        return videoFollowTime;
    }

    public void setVideoFollowTime(String videoFollowTime) {
        this.videoFollowTime = videoFollowTime;
    }

    public String getSubLeid() {
        return subLeid;
    }

    public void setSubLeid(String subLeid) {
        this.subLeid = subLeid;
    }

    public Integer getIsHomemade() {
        return isHomemade;
    }

    public void setIsHomemade(Integer isHomemade) {
        this.isHomemade = isHomemade;
    }

    public Integer getExternalRank() {
        return externalRank;
    }

    public void setExternalRank(Integer externalRank) {
        this.externalRank = externalRank;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Integer getIsCibnChecked() {
        return isCibnChecked;
    }

    public void setIsCibnChecked(Integer isCibnChecked) {
        this.isCibnChecked = isCibnChecked;
    }

    public Integer getIsWasuChecked() {
        return isWasuChecked;
    }

    public void setIsWasuChecked(Integer isWasuChecked) {
        this.isWasuChecked = isWasuChecked;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getExternalPlayId() {
        return externalPlayId;
    }

    public void setExternalPlayId(String externalPlayId) {
        this.externalPlayId = externalPlayId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getIsExclusive() {
        return isExclusive;
    }

    public void setIsExclusive(Integer isExclusive) {
        this.isExclusive = isExclusive;
    }

    public String getDoubanId() {
        return doubanId;
    }

    public void setDoubanId(String doubanId) {
        this.doubanId = doubanId;
    }

    public String getBaikeId() {
        return baikeId;
    }

    public void setBaikeId(String baikeId) {
        this.baikeId = baikeId;
    }

    public String getTvmaoId() {
        return tvmaoId;
    }

    public void setTvmaoId(String tvmaoId) {
        this.tvmaoId = tvmaoId;
    }

    public String getMtimeId() {
        return mtimeId;
    }

    public void setMtimeId(String mtimeId) {
        this.mtimeId = mtimeId;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
