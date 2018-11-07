package com.letv.mas.caller.iptv.tvproxy.channel.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.bean.ChargeInfoDto;

import java.util.List;
import java.util.Map;

/**
 * 排行榜数据，封装了排行榜所需的视频或专辑的简单信息；但因为之前的设计，排行榜数据不能直接继承自AlbumDto或
 * VideoDto，故这里单独作为一个类型；
 * 设计上可以优化重构
 * @author KevinYi
 */
public class ChartsDataDto extends BaseData {

    /**
     * 排序信息，如“No.1”
     */
    private String rankTitle;

    /**
     * 专辑id
     */
    private String albumId;

    /**
     * 视频id
     */
    private String videoId;

    /**
     * 普通专题id
     */
    private String subjectId;

    /**
     * 超前院线专题id
     */
    private String cinemaId;

    /**
     * 专题类型，1--专辑专题，0--视频专题，2--直播专题，参见SubjectConstant.SUBJECTTYPE_*
     */
    private Integer subjectType;

    /**
     * 分类id，如果是视频，则优先显示视频分类，否则显示专辑分类
     */
    private Integer categoryId;

    /**
     * 前端显示图片，优先取视频图片
     */
    private String img;

    /**
     * 专辑或视频名称
     */
    private String name;

    /**
     * 副标题，已完结展示一句话简介；未完结的，电视剧展示“更新至XX集”，动漫展示“更新到XX集”， 综艺展示“更新至XX期”
     */
    private String subName;

    /**
     * TV端上映时间，单位-天
     */
    private Integer publishTime;

    /**
     * 内容分类，形如“内容：大陆/动作”
     */
    private String contentCategory;

    /**
     * 演职员表，电影展示演员表（ 无演员表展示导演 ），电视剧展示演员表（ 无演员表展示导演 ） ，动漫展示适用年龄，综艺
     * 展示主持人
     */
    private String performers;

    /**
     * 地区信息，形如“国家/地区：香港”
     */
    private String areaContent;

    /**
     * 付费类型，1--付费，2--免费
     */
    private Integer chargeType;

    // for tvod icon type
    /**
     * 角标的类型
     */
    private String iconType;

    /**
     * 播放量标签，如“一周播放量”，或“3天播放量”
     */
    private String vvLabel;

    /**
     * 播放量，如“1111”，“123万”
     */
    private String vvContent;

    /**
     * 实际播放量，这里直接使用int型，方便排序；在统计表中，已经根据vv排好序了
     */
    private Integer vv;

    /**
     * 榜单特殊标签标题，如飙升榜的“排名提升”
     */
    private String markLabel;

    /**
     * 榜单特殊标签内容
     */
    private String markContent;

    /**
     * 实际排名，这里直接使用int型，方便排序
     */
    private Integer mark;

    /**
     * 描述，简介，目前音乐排行榜中用到
     */
    private String description;

    /**
     * 这里暂取一个无意义的值
     */
    private int dataType = DataConstant.DATA_TYPE_BLANK;

    private Map<String, String> imgs;

    /**
     * 分端付费参数集合
     */
    private List<ChargeInfoDto> chargeInfos;

    public Map<String, String> getImgs() {
        return this.imgs;
    }

    public void setImgs(Map<String, String> imgs) {
        this.imgs = imgs;
    }

    @Override
    public int getDataType() {
        return this.dataType;
    }

    public String getRankTitle() {
        return this.rankTitle;
    }

    public void setRankTitle(String rankTitle) {
        this.rankTitle = rankTitle;
    }

    public String getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
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

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public Integer getPublishTime() {
        return this.publishTime;
    }

    public void setPublishTime(Integer publishTime) {
        this.publishTime = publishTime;
    }

    public String getContentCategory() {
        return this.contentCategory;
    }

    public void setContentCategory(String contentCategory) {
        this.contentCategory = contentCategory;
    }

    public String getPerformers() {
        return this.performers;
    }

    public void setPerformers(String performers) {
        this.performers = performers;
    }

    public String getAreaContent() {
        return this.areaContent;
    }

    public void setAreaContent(String areaContent) {
        this.areaContent = areaContent;
    }

    public Integer getChargeType() {
        return this.chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    public String getVvLabel() {
        return this.vvLabel;
    }

    public void setVvLabel(String vvLabel) {
        this.vvLabel = vvLabel;
    }

    public String getVvContent() {
        return this.vvContent;
    }

    public void setVvContent(String vvContent) {
        this.vvContent = vvContent;
    }

    public String getMarkLabel() {
        return this.markLabel;
    }

    public void setMarkLabel(String markLabel) {
        this.markLabel = markLabel;
    }

    public String getMarkContent() {
        return this.markContent;
    }

    public void setMarkContent(String markContent) {
        this.markContent = markContent;
    }

    @Override
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public Integer getVv() {
        return this.vv;
    }

    public void setVv(Integer vv) {
        this.vv = vv;
    }

    public Integer getMark() {
        return this.mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getCinemaId() {
        return this.cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Integer getSubjectType() {
        return this.subjectType;
    }

    public void setSubjectType(Integer subjectType) {
        this.subjectType = subjectType;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public List<ChargeInfoDto> getChargeInfos() {
        return chargeInfos;
    }

    public void setChargeInfos(List<ChargeInfoDto> chargeInfos) {
        this.chargeInfos = chargeInfos;
    }
}
