package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.io.Serializable;
import java.util.List;

/**
 * 领先版专辑详情页数据，其正片剧集是分页数据
 * @author KevinYi
 */
public class LetvLeadingAlbumSeries implements Serializable {
    public LetvLeadingAlbumSeries() {
        super();
    }

    public LetvLeadingAlbumSeries(Integer type) {
        super();
        this.type = type;// 标识0专辑、1视频
    }

    private Integer type;
    /**
     * 专辑id
     */
    private String albumId;

    /**
     * 专辑分类id
     */
    private Integer categoryId;

    /**
     * 名称
     */
    private String name;

    /**
     * 子名称
     */
    private String subName;

    /**
     * 别名
     */
    private String alias;

    /**
     * 描述
     */
    private String description;

    /**
     * 评分
     */
    private String score;

    /**
     * 是否正片
     */
    private Integer positive;

    /**
     * 正片、预告片等id
     */
    private Integer albumTypeId;

    /**
     * 默认选中码流
     */
    private String defaultStream;

    /**
     * 是否收费 1 shi 0 fou
     */
    private Integer charge;

    /**
     * 剧集列表（正片）
     */
    private List<LetvLeadingVideoSeriesPage> positiveSeries;

    /**
     * 是不是综艺类型,1是 0否
     */
    private Integer varietyShow;

    /**
     * 付费类型 1支持单点付费
     */
    private Integer payType;

    /**
     * 剧集列表样式
     * 1.图文列表
     * 2.九宫格
     */
    private Integer seriesStyle;

    private Long createTime;// 体育非栏目类用

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScore() {
        return this.score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Integer getPositive() {
        return this.positive;
    }

    public void setPositive(Integer positive) {
        this.positive = positive;
    }

    public Integer getAlbumTypeId() {
        return this.albumTypeId;
    }

    public void setAlbumTypeId(Integer albumTypeId) {
        this.albumTypeId = albumTypeId;
    }

    public String getDefaultStream() {
        return this.defaultStream;
    }

    public void setDefaultStream(String defaultStream) {
        this.defaultStream = defaultStream;
    }

    public Integer getCharge() {
        return this.charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }

    public List<LetvLeadingVideoSeriesPage> getPositiveSeries() {
        return this.positiveSeries;
    }

    public void setPositiveSeries(List<LetvLeadingVideoSeriesPage> positiveSeries) {
        this.positiveSeries = positiveSeries;
    }

    public Integer getVarietyShow() {
        return this.varietyShow;
    }

    public void setVarietyShow(Integer varietyShow) {
        this.varietyShow = varietyShow;
    }

    public Integer getPayType() {
        return this.payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getSeriesStyle() {
        return this.seriesStyle;
    }

    public void setSeriesStyle(Integer seriesStyle) {
        this.seriesStyle = seriesStyle;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

}
