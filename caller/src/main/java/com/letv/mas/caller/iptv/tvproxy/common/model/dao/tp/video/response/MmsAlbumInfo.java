package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.CopyFieldAnnotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MmsAlbumInfo {

    /**
     *
     */
    private static final long serialVersionUID = -3643034355362125566L;

    private Long id;// 专辑id

    @CopyFieldAnnotation(isSplit = true, mapKey = "actor_id", mapValue = "actor")
    private Map<String, String> actor;// 演员

    @CopyFieldAnnotation(isSplit = true, value = "actor_desc")
    private String[] actorDesc;// 演员简介

    @CopyFieldAnnotation("actor_play")
    private String actorPlay;// 饰演角色

    @CopyFieldAnnotation("actor_play_pic")
    private String actorPlayPic;// 角色定妆照

    @CopyFieldAnnotation("album_base_type")
    private Integer albumBaseType;// 1.目录（越狱）2.专辑（越狱第一季）

    @CopyFieldAnnotation(isSplit = true, mapKey = "album_type", mapValue = "album_type_name", valueToString = false)
    private Map<Integer, String> albumType;// 正片、片花、花絮、资讯

    @CopyFieldAnnotation("alias")
    private String alias;// 别名
    private Map<Integer, String> allowMonth;// 是否支持包月

    @CopyFieldAnnotation(isSplit = true, mapKey = "area", mapValue = "area_name")
    private Map<String, String> area;// 地区

    @CopyFieldAnnotation(isSplit = true, mapValue = "cast")
    private Map<Integer, String> cast;// 声优动漫频道

    @CopyFieldAnnotation(isSplit = true, mapKey = "category", mapValue = "category_name", valueToString = false)
    private Map<Integer, String> category;// 分类

    @CopyFieldAnnotation(isSplit = true, mapValue = "compere")
    private Map<String, String> compere;// 主持人

    private Integer allowForgienPlatform;// 是否海外播放

    @CopyFieldAnnotation(isSplit = true, mapKey = "carfilm_type", mapValue = "carfilm_type_name")
    private Map<Integer, String> carfilmType;// 汽车频道的专辑类型

    @CopyFieldAnnotation("copyright_company")
    private String copyrightCompany;// 版权公司
    @CopyFieldAnnotation("copyright_start")
    private String copyrightStart;// 版权结束时间
    @CopyFieldAnnotation("copyright_end")
    private String copyrightEnd;// 版权开始时间

    @CopyFieldAnnotation(isSplit = true, mapKey = "copyright_type", mapValue = "copyright_type_name", valueToString = false)
    private Map<Integer, String> copyrightType;// 版权类型
    @CopyFieldAnnotation(isCopy = false)
    private String createTime;
    private Integer deleted;// 删除标记
    private String description;// 介绍

    @CopyFieldAnnotation(isSplit = true, mapKey = "directory_id", mapValue = "directory")
    private Map<Integer, String> directory;// 导演
    private Map<Integer, String> disableType;// 屏蔽类型
    private String discountDate;// 折扣开始日期
    private String discountDateEnd;// 折扣结束日期
    private Float discountPrice;// 折扣价格
    private Float preprice;// 预购价格
    private String doubanId;// 豆瓣ID
    private String imdb;// IMDB

    @CopyFieldAnnotation(isSplit = true, mapKey = "download_platform")
    private Map<String, String> downloadPlatform;// 允许下载平台

    private String dub;// 配音
    private Integer duration;// 时长
    private Integer episode;// 共{episode}集

    @CopyFieldAnnotation(isSplit = true, mapKey = "field_type", mapValue = "field_type_name", valueToString = false)
    private Map<Integer, String> fieldType;// 领域类型

    @CopyFieldAnnotation("first_play_time")
    private String firstPlayTime;// 体育频道中的赛季

    @CopyFieldAnnotation(isSplit = true, mapValue = "fit_age")
    private Map<Integer, String> fitAge;// 适应年龄

    private Integer forder;// 在父专辑顺序

    @CopyFieldAnnotation(isSplit = true, mapValue = "instructor")
    private Map<Integer, String> instructor;// 讲师

    @CopyFieldAnnotation("is_end")
    private Integer isEnd;// 是否完结
    @CopyFieldAnnotation("is_follow")
    private Integer isFollow;// 是否跟播

    @CopyFieldAnnotation("is_height")
    private Integer isHeight;// 是否高清
    private Integer isPay;// 是否付费
    // for tvod icon type vsr
    private Integer isCoupon;// 是否支持观影券
    private Integer payType;// payType 整型 860001：点播 860002：点播或会员 860003：会员

    @CopyFieldAnnotation("issue_company")
    private String issueCompany;// 发行公司

    @CopyFieldAnnotation(isSplit = true, mapKey = "language", mapValue = "language_name", valueToString = false)
    private Map<Integer, String> language;// 内容语言

    @CopyFieldAnnotation(isSplit = true, mapKey = "letv_make_style", mapValue = "letv_make_style_name", valueToString = false)
    private Map<Integer, String> letvMakeStyle;// 乐视制造分类

    @CopyFieldAnnotation(isSplit = true, mapKey = "letv_produce_style", mapValue = "letv_produce_style_name", valueToString = false)
    private Map<Integer, String> letvProduceStyle;// 乐视出品分类

    @CopyFieldAnnotation(isSplit = true, mapKey = "film_base_type", mapValue = "film_base_type_name", valueToString = false)
    private Map<Integer, String> filmBaseType;// 电影频道分类

    @CopyFieldAnnotation("letv_release_date")
    private String letvReleaseDate;// 乐视上映时间

    @CopyFieldAnnotation("maker")
    private String maker;// 制片人

    @CopyFieldAnnotation("maker_company")
    private String makerCompany;// 出品公司

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

    @CopyFieldAnnotation(isSplit = true, mapValue = "originator")
    private Map<Integer, String> originator;// 原作

    private String payDate;// 服务期限

    @CopyFieldAnnotation(isSplit = true, mapKey = "pay_platform")
    private Map<String, String> payPlatform;// 收费平台
    private String payProduct;// 付费产品

    @CopyFieldAnnotation(isCopy = false)
    private Map<String, String> picCollections;// 图片集

    @CopyFieldAnnotation(isSplit = true, mapKey = "play_platform")
    private Map<String, String> playPlatform;// 推送平台

    @CopyFieldAnnotation("play_status")
    private String playStatus;// 跟播状态:例如“每周五更新1集”

    @CopyFieldAnnotation(isSplit = true, mapKey = "play_tv", mapValue = "play_tv_name")
    private Map<Integer, String> playTv;// 播出电视台

    @CopyFieldAnnotation(isSplit = true, mapKey = "pop_style", mapValue = "pop_style_name", valueToString = false)
    private Map<Integer, String> popStyle;// 风尚频道分类

    private String producer;// 监制

    @CopyFieldAnnotation(isSplit = true, mapKey = "program_style", mapValue = "program_style_name", valueToString = false)
    private Map<Integer, String> programStyle;// 项目分类

    @CopyFieldAnnotation("recomm_level")
    private Integer recommLevel;// 推荐指数

    @CopyFieldAnnotation("record_company")
    private String recordCompany;// 唱片公司

    @CopyFieldAnnotation(isSplit = true, mapKey = "recreation_type", mapValue = "recreation_type_name")
    private Map<Integer, String> recreationType;// 娱乐频道 娱乐分类 原娱乐频道的专辑类型

    @CopyFieldAnnotation("release_date")
    private String releaseDate;// 上映时间

    private String remark;// 备注
    private String school;// 公开课频道 学校字段
    private Float score;// 评分

    @CopyFieldAnnotation("screen_writer")
    private String screenWriter;// 编剧

    @CopyFieldAnnotation("short_desc")
    private String shortDesc;// 简要描述

    private Float singlePrice;// 单片价格

    @CopyFieldAnnotation(isSplit = true, mapKey = "source_id")
    private Map<Integer, String> sourceId;// 来源

    @CopyFieldAnnotation(isSplit = true, mapKey = "sports_type", mapValue = "sports_type_name", valueToString = false)
    private Map<Integer, String> sportsType;// 体育分类

    @CopyFieldAnnotation(isSplit = true, mapKey = "starring_id", mapValue = "starring")
    private Map<Integer, String> starring;// 主演

    @CopyFieldAnnotation(isSplit = true, value = "starring_desc")
    private String[] starringDesc;// 主演介绍

    @CopyFieldAnnotation("starring_play")
    private String starringPlay;// 主演饰演角色

    @CopyFieldAnnotation("starring_play_pic")
    private String starringPlayPic;// 主演定妆照

    private Integer status;// 发布状态

    @CopyFieldAnnotation(isSplit = true, mapKey = "sub_category", mapValue = "sub_category_name")
    private Map<Integer, String> subCategory;// 子分类

    @CopyFieldAnnotation("sub_title")
    private String subTitle;// 副标题

    @CopyFieldAnnotation(isSplit = true, mapValue = "supervise")
    private Map<Integer, String> supervise;// 动漫监督

    private String tag;// 标签

    @CopyFieldAnnotation(isSplit = true, mapKey = "travel_theme", mapValue = "travel_theme_name", valueToString = false)
    private Map<Integer, String> travelTheme;// 旅游主题

    @CopyFieldAnnotation(isSplit = true, mapKey = "travel_type", mapValue = "travel_type_name", valueToString = false)
    private Map<Integer, String> travelType;// 旅游类型

    private String tvTitle;// TV端标题
    @CopyFieldAnnotation(isCopy = false)
    private String updateTime;// 修改时间
    private Long userId;// 创建用户ID
    private String versionTitle;// 版本标题
    private String websiteTitle;// 网站标题
    @CopyFieldAnnotation(isCopy = false)
    private Map<Integer, String> videoBitRate;// 专辑下所有视频码率的并集
    private Map<String, String> platformVideoNum;// 专辑在平台下视频总数
    @CopyFieldAnnotation("is_homemade")
    private Integer isHomemade;// 是否是自制剧
    private String rCompany;// 节目来源

    @CopyFieldAnnotation("relationAlbumId")
    private String relationAlbumId;// 相关片段

    // 2016-04-01发现异常，媒资返回数字类型，但AlbumMysqlTable设计为String类型，数据库设计为数字类型，解析MmsAlbumInfo到AlbumMysqlTable报错，这里临时修改，后期需要归正
    @CopyFieldAnnotation("nowEpisodes")
    private String nowEpisodes;// 更新至{nowEpisodes}集

    // 2016-04-01发现异常，媒资返回数字类型，但AlbumMysqlTable设计为String类型，数据库设计为数字类型，解析MmsAlbumInfo到AlbumMysqlTable报错，这里临时修改，后期需要归正
    @CopyFieldAnnotation("nowIssue")
    private String nowIssue;// 更新至{nowIssue}期

    @CopyFieldAnnotation("isDanmaku")
    private Integer isDanmaku;// 是否有弹幕
    @CopyFieldAnnotation("relationId")
    private String relationId;// 关联专辑ID，片段

    @CopyFieldAnnotation(isCopy = false)
    private String videoFollowTime;

    private String varietyShow;// 纪录片是否综艺,区分"集"\"期"
    private Integer playControlPlatformGuoguang;// 播控平台国广
    private Integer playControlPlatformHuashu;// 播控平台华数

    @CopyFieldAnnotation("isPushChild")
    private Integer isPushChild;// 是否推送到儿童频道1推送0不推送

    @CopyFieldAnnotation(isSplit = true, mapKey = "contentRatingId", mapValue = "contentRatingValue", valueToString = false)
    private Map<Integer, String> contentRating;// 内容分级

    private String coopPlatform; // Cooperation platform
    /**
     * 动态图片，一般为gif格式
     */
    private String dynamicGraph;

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

    public Integer getIsPushChild() {
        return isPushChild;
    }

    public void setIsPushChild(Integer isPushChild) {
        this.isPushChild = isPushChild;
    }

    public Integer getIsHomemade() {
        return this.isHomemade;
    }

    public void setIsHomemade(Integer isHomemade) {
        this.isHomemade = isHomemade;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Integer, String> getFilmBaseType() {
        return this.filmBaseType;
    }

    public void setFilmBaseType(Map<Integer, String> filmBaseType) {
        this.filmBaseType = filmBaseType;
    }

    public Map<String, String> getActor() {
        return this.actor;
    }

    public void setActor(Map<String, String> actor) {
        this.actor = actor;
    }

    public String[] getActorDesc() {
        return this.actorDesc;
    }

    public void setActorDesc(String[] actorDesc) {
        this.actorDesc = actorDesc;
    }

    public String getActorPlay() {
        return this.actorPlay;
    }

    public void setActorPlay(String actorPlay) {
        this.actorPlay = actorPlay;
    }

    public String getActorPlayPic() {
        return this.actorPlayPic;
    }

    public void setActorPlayPic(String actorPlayPic) {
        this.actorPlayPic = actorPlayPic;
    }

    public Integer getAlbumBaseType() {
        return this.albumBaseType;
    }

    public void setAlbumBaseType(Integer albumBaseType) {
        this.albumBaseType = albumBaseType;
    }

    public Map<Integer, String> getAlbumType() {
        return this.albumType;
    }

    public void setAlbumType(Map<Integer, String> albumType) {
        this.albumType = albumType;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Map<Integer, String> getAllowMonth() {
        return this.allowMonth;
    }

    public void setAllowMonth(Map<Integer, String> allowMonth) {
        this.allowMonth = allowMonth;
    }

    public Map<String, String> getArea() {
        return this.area;
    }

    public void setArea(Map<String, String> area) {
        this.area = area;
    }

    public Map<Integer, String> getCast() {
        return this.cast;
    }

    public void setCast(Map<Integer, String> cast) {
        this.cast = cast;
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

    public Integer getAllowForgienPlatform() {
        return this.allowForgienPlatform;
    }

    public void setAllowForgienPlatform(Integer allowForgienPlatform) {
        this.allowForgienPlatform = allowForgienPlatform;
    }

    public Map<Integer, String> getCarfilmType() {
        return this.carfilmType;
    }

    public void setCarfilmType(Map<Integer, String> carfilmType) {
        this.carfilmType = carfilmType;
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

    public Map<Integer, String> getDirectory() {
        return this.directory;
    }

    public void setDirectory(Map<Integer, String> directory) {
        this.directory = directory;
    }

    public Map<Integer, String> getDisableType() {
        return this.disableType;
    }

    public void setDisableType(Map<Integer, String> disableType) {
        this.disableType = disableType;
    }

    public String getDiscountDate() {
        return this.discountDate;
    }

    public void setDiscountDate(String discountDate) {
        this.discountDate = discountDate;
    }

    public String getDiscountDateEnd() {
        return this.discountDateEnd;
    }

    public void setDiscountDateEnd(String discountDateEnd) {
        this.discountDateEnd = discountDateEnd;
    }

    public Float getDiscountPrice() {
        return this.discountPrice;
    }

    public void setDiscountPrice(Float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Float getPreprice() {
        return this.preprice;
    }

    public void setPreprice(Float preprice) {
        this.preprice = preprice;
    }

    public String getDoubanId() {
        return this.doubanId;
    }

    public void setDoubanId(String doubanId) {
        this.doubanId = doubanId;
    }

    public String getImdb() {
        return this.imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public Map<String, String> getDownloadPlatform() {
        return this.downloadPlatform;
    }

    public void setDownloadPlatform(Map<String, String> downloadPlatform) {
        this.downloadPlatform = downloadPlatform;
    }

    public String getDub() {
        return this.dub;
    }

    public void setDub(String dub) {
        this.dub = dub;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getEpisode() {
        return this.episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
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

    public Map<Integer, String> getFitAge() {
        return this.fitAge;
    }

    public void setFitAge(Map<Integer, String> fitAge) {
        this.fitAge = fitAge;
    }

    public Integer getForder() {
        return this.forder;
    }

    public void setForder(Integer forder) {
        this.forder = forder;
    }

    public Map<Integer, String> getInstructor() {
        return this.instructor;
    }

    public void setInstructor(Map<Integer, String> instructor) {
        this.instructor = instructor;
    }

    public Integer getIsEnd() {
        return this.isEnd;
    }

    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }

    public Integer getIsFollow() {
        return this.isFollow;
    }

    public void setIsFollow(Integer isFollow) {
        this.isFollow = isFollow;
    }

    public Integer getIsHeight() {
        return this.isHeight;
    }

    public void setIsHeight(Integer isHeight) {
        this.isHeight = isHeight;
    }

    public Integer getIsPay() {
        return this.isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public Integer getIsCoupon() {
        return isCoupon;
    }

    public void setIsCoupon(Integer isCoupon) {
        this.isCoupon = isCoupon;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
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

    public String getLetvReleaseDate() {
        return this.letvReleaseDate;
    }

    public void setLetvReleaseDate(String letvReleaseDate) {
        this.letvReleaseDate = letvReleaseDate;
    }

    public String getMaker() {
        return this.maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getMakerCompany() {
        return this.makerCompany;
    }

    public void setMakerCompany(String makerCompany) {
        this.makerCompany = makerCompany;
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

    public Map<Integer, String> getOriginator() {
        return this.originator;
    }

    public void setOriginator(Map<Integer, String> originator) {
        this.originator = originator;
    }

    public String getPayDate() {
        return this.payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public Map<String, String> getPayPlatform() {
        return this.payPlatform;
    }

    public void setPayPlatform(Map<String, String> payPlatform) {
        this.payPlatform = payPlatform;
    }

    public String getPayProduct() {
        return this.payProduct;
    }

    public void setPayProduct(String payProduct) {
        this.payProduct = payProduct;
    }

    public Map<String, String> getPicCollections() {
        return this.picCollections;
    }

    public void setPicCollections(Map<String, String> picCollections) {
        this.picCollections = picCollections;
    }

    public Map<String, String> getPlayPlatform() {
        return this.playPlatform;
    }

    public void setPlayPlatform(Map<String, String> playPlatform) {
        this.playPlatform = playPlatform;
    }

    public String getPlayStatus() {
        return this.playStatus;
    }

    public void setPlayStatus(String playStatus) {
        this.playStatus = playStatus;
    }

    public Map<Integer, String> getPlayTv() {
        return this.playTv;
    }

    public void setPlayTv(Map<Integer, String> playTv) {
        this.playTv = playTv;
    }

    public Map<Integer, String> getPopStyle() {
        return this.popStyle;
    }

    public void setPopStyle(Map<Integer, String> popStyle) {
        this.popStyle = popStyle;
    }

    public String getProducer() {
        return this.producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Map<Integer, String> getProgramStyle() {
        return this.programStyle;
    }

    public void setProgramStyle(Map<Integer, String> programStyle) {
        this.programStyle = programStyle;
    }

    public Integer getRecommLevel() {
        return this.recommLevel;
    }

    public void setRecommLevel(Integer recommLevel) {
        this.recommLevel = recommLevel;
    }

    public String getRecordCompany() {
        return this.recordCompany;
    }

    public void setRecordCompany(String recordCompany) {
        this.recordCompany = recordCompany;
    }

    public Map<Integer, String> getRecreationType() {
        return this.recreationType;
    }

    public void setRecreationType(Map<Integer, String> recreationType) {
        this.recreationType = recreationType;
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

    public String getScreenWriter() {
        return this.screenWriter;
    }

    public void setScreenWriter(String screenWriter) {
        this.screenWriter = screenWriter;
    }

    public String getShortDesc() {
        return this.shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public Float getSinglePrice() {
        return this.singlePrice;
    }

    public void setSinglePrice(Float singlePrice) {
        this.singlePrice = singlePrice;
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

    public String[] getStarringDesc() {
        return this.starringDesc;
    }

    public void setStarringDesc(String[] starringDesc) {
        this.starringDesc = starringDesc;
    }

    public String getStarringPlay() {
        return this.starringPlay;
    }

    public void setStarringPlay(String starringPlay) {
        this.starringPlay = starringPlay;
    }

    public String getStarringPlayPic() {
        return this.starringPlayPic;
    }

    public void setStarringPlayPic(String starringPlayPic) {
        this.starringPlayPic = starringPlayPic;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map<Integer, String> getSubCategory() {
        return this.subCategory;
    }

    public void setSubCategory(Map<Integer, String> subCategory) {
        this.subCategory = subCategory;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Map<Integer, String> getSupervise() {
        return this.supervise;
    }

    public void setSupervise(Map<Integer, String> supervise) {
        this.supervise = supervise;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public String getVersionTitle() {
        return this.versionTitle;
    }

    public void setVersionTitle(String versionTitle) {
        this.versionTitle = versionTitle;
    }

    public String getWebsiteTitle() {
        return this.websiteTitle;
    }

    public void setWebsiteTitle(String websiteTitle) {
        this.websiteTitle = websiteTitle;
    }

    public Map<Integer, String> getVideoBitRate() {
        return this.videoBitRate;
    }

    public void setVideoBitRate(Map<Integer, String> videoBitRate) {
        this.videoBitRate = videoBitRate;
    }

    public Map<String, String> getPlatformVideoNum() {
        return this.platformVideoNum;
    }

    public void setPlatformVideoNum(Map<String, String> platformVideoNum) {
        this.platformVideoNum = platformVideoNum;
    }

    public String getrCompany() {
        return this.rCompany;
    }

    public void setrCompany(String rCompany) {
        this.rCompany = rCompany;
    }

    public String getRelationAlbumId() {
        return this.relationAlbumId;
    }

    public void setRelationAlbumId(String relationAlbumId) {
        this.relationAlbumId = relationAlbumId;
    }

    public String getNowEpisodes() {
        return this.nowEpisodes;
    }

    public void setNowEpisodes(String nowEpisodes) {
        this.nowEpisodes = nowEpisodes;
    }

    public String getNowIssue() {
        return this.nowIssue;
    }

    public void setNowIssue(String nowIssue) {
        this.nowIssue = nowIssue;
    }

    public String getRelationId() {
        return this.relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getVideoFollowTime() {
        return this.videoFollowTime;
    }

    public void setVideoFollowTime(String videoFollowTime) {
        this.videoFollowTime = videoFollowTime;
    }

    public String getVarietyShow() {
        return this.varietyShow;
    }

    public void setVarietyShow(String varietyShow) {
        this.varietyShow = varietyShow;
    }

    public Map<Integer, String> getContentRating() {
        return this.contentRating;
    }

    public void setContentRating(Map<Integer, String> contentRating) {
        this.contentRating = contentRating;
    }

    public String getDynamicGraph() {
        return this.dynamicGraph;
    }

    public void setDynamicGraph(String dynamicGraph) {
        this.dynamicGraph = dynamicGraph;
    }

    public String getCoopPlatform() {
        return coopPlatform;
    }

    public void setCoopPlatform(String coopPlatform) {
        this.coopPlatform = coopPlatform;
    }

    public static void main(String[] args) throws Exception {
        Object object = Class.forName(MmsAlbumInfo.class.getName()).newInstance();
        Class<?> obj = object.getClass();
        Field f = obj.getField("actor");
        System.out.println(Map.class.isAssignableFrom(f.getType()));
        Class c = ArrayList.class;
        System.out.println(c.isInstance(List.class));
        /*
         * Field[] declaredFields = obj.getDeclaredFields();
         * for (Field field : declaredFields) {
         * System.out.println(field.getType());
         * }
         */
        // ArrayList a = new ArrayList();

        /*
         * TestAnnotation annotation = f.getAnnotation(TestAnnotation.class);
         * System.out.println(annotation.getClass().getName());
         */
        // for (int i = 0; i < fields.length; i++) {
        // fields[i].setAccessible(true);
        // // TestAnnotation annotation =
        // fields[i].getAnnotation(TestAnnotation.class);
        // System.out.println(fields[i].getName());
        /* System.out.println(annotation != null ? annotation.name() : "null"); */
        /*
         * for (int j = 0; j < args.length; j++) { String str = args[j]; String
         * strs[] = str.split(","); if (strs[0].equals(fields[i].getName())) {
         * fields[i].set(object, strs[1]); break; } }
         */
        // }
    }
}
