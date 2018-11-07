package com.letv.mas.caller.iptv.tvproxy.channel.model.dto;

import java.util.List;

/**
 * 排行榜
 * @author KevinYi
 */
public class ChartsDto extends ChannelData {

    /**
     * 榜单id
     */
    private Integer chartsId;

    /**
     * 榜单类型,int型，具体定义再讨论
     */
    private Integer chartsType;

    /**
     * 榜单名称
     */
    private String chartsName;

    /**
     * 榜单背景色块
     */
    private String chartsBgColor;

    /**
     * 榜单小背景图
     */
    private String chartsImg;

    /**
     * 榜单元素总数
     */
    private Integer contentAmount;

    /**
     * 榜单前几个内容的背景图列表
     */
    private List<String> rankListImgs;

    /**
     * 背景元素
     */
    private String chartsCornerColor;

    /**
     * 内容列表
     */
    // private List<BaseData> dataList;

    public Integer getChartsId() {
        return this.chartsId;
    }

    public void setChartsId(Integer chartsId) {
        this.chartsId = chartsId;
    }

    public Integer getChartsType() {
        return this.chartsType;
    }

    public void setChartsType(Integer chartsType) {
        this.chartsType = chartsType;
    }

    public String getChartsName() {
        return this.chartsName;
    }

    public void setChartsName(String chartsName) {
        this.chartsName = chartsName;
    }

    public String getChartsBgColor() {
        return this.chartsBgColor;
    }

    public void setChartsBgColor(String chartsBgColor) {
        this.chartsBgColor = chartsBgColor;
    }

    public String getChartsImg() {
        return this.chartsImg;
    }

    public void setChartsImg(String chartsImg) {
        this.chartsImg = chartsImg;
    }

    public Integer getContentAmount() {
        return this.contentAmount;
    }

    public void setContentAmount(Integer contentAmount) {
        this.contentAmount = contentAmount;
    }

    public List<String> getRankListImgs() {
        return this.rankListImgs;
    }

    public void setRankListImgs(List<String> rankListImgs) {
        this.rankListImgs = rankListImgs;
    }

    public String getChartsCornerColor() {
        return this.chartsCornerColor;
    }

    public void setChartsCornerColor(String chartsCornerColor) {
        this.chartsCornerColor = chartsCornerColor;
    }

}
