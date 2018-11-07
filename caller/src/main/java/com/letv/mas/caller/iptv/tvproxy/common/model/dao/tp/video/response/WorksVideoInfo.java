package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.CopyFieldAnnotation;

/**
 * Created by leeco on 18/9/12.
 */
public class WorksVideoInfo {
    private Long id; // 视频id

    @CopyFieldAnnotation("name_cn")
    private String name; // 名称

    private Integer category; // 频道

    @CopyFieldAnnotation("status")
    private Integer state; // 状态

    @CopyFieldAnnotation("coopPlatform")
    private Integer siteId; // 站点id

    @CopyFieldAnnotation(isCopy = false)
    private Integer sysAdd; // 来源方式

    @CopyFieldAnnotation("video_type")
    private Integer videoType; // 视频类型

    @CopyFieldAnnotation("directory")
    private String director; // 导演

    private String actor; // 演员

    @CopyFieldAnnotation(isCopy = false)
    private String guest; // 嘉宾

    private String compere; // 主持人

    @CopyFieldAnnotation(isCopy = false)
    private String actorPlay; // 演员饰演角色

    private String episode; // 集数或期数

    private Integer porder; // 视频顺序

    @CopyFieldAnnotation(isCopy = false)
    private Integer gorder; // 所有视频中的顺序

    private Long pid; // 专辑id

    @CopyFieldAnnotation(isCopy = false)
    private String globalVid;// 全局视频id

    @CopyFieldAnnotation(isCopy = false)
    private Integer screenYear; // 上映年份

    @CopyFieldAnnotation("tvFirstOnTime")
    private String screenTime; // 上映时间

    @CopyFieldAnnotation("sub_category")
    private String subCategory; // 子分类

    @CopyFieldAnnotation("relative_content")
    private String playLink; // 播放地址

    @CopyFieldAnnotation("description")
    private String introduction; // 简介

    @CopyFieldAnnotation("duration")
    private Integer durationSecond; // 时长（秒）

    @CopyFieldAnnotation("score")
    private Float score; // 评分

    @CopyFieldAnnotation(isCopy = false)
    private Integer quality; // 质量

    @CopyFieldAnnotation("tag")
    private String tags; // 标签

    @CopyFieldAnnotation(isCopy = false)
    private Long playcount;// 播放量

    @CopyFieldAnnotation(isCopy = false)
    private String createName; // 创建人;

    @CopyFieldAnnotation(isCopy = false)
    private String updateName; // 更新人

    @CopyFieldAnnotation("create_time")
    private String createTime; // 创建时间

    @CopyFieldAnnotation("update_time")
    private String updateTime; // 更新时间

    @CopyFieldAnnotation(isCopy = false)
    private Integer deadlinks;// 死链标志

    private String mid; // 介质id

    @CopyFieldAnnotation("sub_title")
    private String subtitle; // 副标题

    private String alias; // 别名

    @CopyFieldAnnotation(isCopy = false)
    private String reason; // 原因

    private String externalId; // 第三方id

    @CopyFieldAnnotation("video_info_id")
    private String externalPlayId; // 第三方启播id

    private Integer deleted; // 是否删除（1：是，0：否）

    @CopyFieldAnnotation("mid_streams")
    private String codeRate; // 码率

    @CopyFieldAnnotation("pay_platform")
    private String payPlatform;// 支付平台

    @CopyFieldAnnotation("download_platform")
    private String downloadPlatform;// 下载平台

    @CopyFieldAnnotation(isCopy = false)
    private Integer isPay;// 是否免费

    @CopyFieldAnnotation("play_platform")
    private String playPlatform;// 播控

    @CopyFieldAnnotation(isCopy = false)
    private String extData;// 额外字段

    @CopyFieldAnnotation("pic_all")
    private String img; // 图片json, {"比例":{"尺寸":URL}}, 具体参考
                        // http://wiki.letv.cn/pages/viewpage.action?pageId=75629445

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getSysAdd() {
        return sysAdd;
    }

    public void setSysAdd(Integer sysAdd) {
        this.sysAdd = sysAdd;
    }

    public Integer getVideoType() {
        return videoType;
    }

    public void setVideoType(Integer videoType) {
        this.videoType = videoType;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getCompere() {
        return compere;
    }

    public void setCompere(String compere) {
        this.compere = compere;
    }

    public String getActorPlay() {
        return actorPlay;
    }

    public void setActorPlay(String actorPlay) {
        this.actorPlay = actorPlay;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public Integer getPorder() {
        return porder;
    }

    public void setPorder(Integer porder) {
        this.porder = porder;
    }

    public Integer getGorder() {
        return gorder;
    }

    public void setGorder(Integer gorder) {
        this.gorder = gorder;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getGlobalVid() {
        return globalVid;
    }

    public void setGlobalVid(String globalVid) {
        this.globalVid = globalVid;
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

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
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

    public Integer getDurationSecond() {
        return durationSecond;
    }

    public void setDurationSecond(Integer durationSecond) {
        this.durationSecond = durationSecond;
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

    public Long getPlaycount() {
        return playcount;
    }

    public void setPlaycount(Long playcount) {
        this.playcount = playcount;
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

    public Integer getDeadlinks() {
        return deadlinks;
    }

    public void setDeadlinks(Integer deadlinks) {
        this.deadlinks = deadlinks;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getCodeRate() {
        return codeRate;
    }

    public void setCodeRate(String codeRate) {
        this.codeRate = codeRate;
    }

    public String getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(String payPlatform) {
        this.payPlatform = payPlatform;
    }

    public String getDownloadPlatform() {
        return downloadPlatform;
    }

    public void setDownloadPlatform(String downloadPlatform) {
        this.downloadPlatform = downloadPlatform;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public String getPlayPlatform() {
        return playPlatform;
    }

    public void setPlayPlatform(String playPlatform) {
        this.playPlatform = playPlatform;
    }

    public String getExtData() {
        return extData;
    }

    public void setExtData(String extData) {
        this.extData = extData;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
