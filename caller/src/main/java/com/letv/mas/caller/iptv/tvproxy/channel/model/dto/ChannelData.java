package com.letv.mas.caller.iptv.tvproxy.channel.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 频道数据
 */
@ApiModel(value = "ChannelData", description = "频道数据")
public class ChannelData extends BaseChannel {

    /**
     * 
     */
    private static final long serialVersionUID = 1878805453491006110L;

    /**
     * 频道ID
     */
    @ApiModelProperty(value = "频道ID")
    private Integer channelId;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 标题背景色
     */
    @ApiModelProperty(value = "标题背景色")
    private String titleBgColor;

    /**
     * 标题频道ID
     */
    @ApiModelProperty(value = "标题频道ID")
    private Integer titleChannelId;

    /**
     * 标题检索条件
     */
    @ApiModelProperty(value = "标题检索条件")
    private String titleSearchCondition;

    /**
     * 标题专辑ID
     */
    @ApiModelProperty(value = "标题专辑ID")
    private Integer titleAlbumId;

    /**
     * UI模版类型，用户只是当前频道数据在客户端页面上的展示位置， 0--推荐大焦点图，1--推荐旁六个小焦点图，2--猜你喜欢，3--超前影院；
     * 2015-08-27“超前院线”修添加，目前客户端仅对3进行特殊处理
     */
    @ApiModelProperty(value = "UI模版类型，用户只是当前频道数据在客户端页面上的展示位置， 0--推荐大焦点图，1--推荐旁六个小焦点图，2--猜你喜欢，3--超前影院")
    private Integer uiPlateType;

    /**
     * 数据列表
     */
    @ApiModelProperty(value = "数据列表")
    private List<BaseData> dataList;

    @ApiModelProperty(value = "推荐数据桶id")
    private String bucket;

    @ApiModelProperty(value = "推荐位")
    private String area;

    @ApiModelProperty(value = "推荐id, 数据上报使用")
    private String reid;

    @ApiModelProperty(value = "板块类型")
    private String blockType;

    @ApiModelProperty(value = "广告图片")
    private String advertisementImg;

    @ApiModelProperty(value = "时间时间")
    private Long gmt;

    @ApiModelProperty(value = "推荐片段id")
    private String fragId;

    @ApiModelProperty(value = "作品库id")
    private String cpId;

    @ApiModelProperty(value = "作品库分类id")
    private String cpCategoryId;

    /**
     * 是否订阅
     */
    @ApiModelProperty(value = "是否订阅")
    private Integer subscribed;

    /**
     * 频道类型 1：电影
     */
    @ApiModelProperty(value = "频道类型 1：电影")
    private Integer channelType;

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public ChannelData() {
    }

    public Long getGmt() {
        return this.gmt;
    }

    public void setGmt(Long gmt) {
        this.gmt = gmt;
    }

    private String img;

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBlockType() {
        return this.blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public String getBucket() {
        return this.bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getReid() {
        return this.reid;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

    public Integer getChannelId() {
        return this.channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleBgColor() {
        return this.titleBgColor;
    }

    public void setTitleBgColor(String titleBgColor) {
        this.titleBgColor = titleBgColor;
    }

    public Integer getTitleChannelId() {
        return this.titleChannelId;
    }

    public void setTitleChannelId(Integer titleChannelId) {
        this.titleChannelId = titleChannelId;
    }

    public String getTitleSearchCondition() {
        return this.titleSearchCondition;
    }

    public void setTitleSearchCondition(String titleSearchCondition) {
        this.titleSearchCondition = titleSearchCondition;
    }

    public Integer getTitleAlbumId() {
        return this.titleAlbumId;
    }

    public void setTitleAlbumId(Integer titleAlbumId) {
        this.titleAlbumId = titleAlbumId;
    }

    public List<BaseData> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<BaseData> dataList) {
        this.dataList = dataList;
    }

    public Integer getUiPlateType() {
        return this.uiPlateType;
    }

    public void setUiPlateType(Integer uiPlateType) {
        this.uiPlateType = uiPlateType;
    }

    public String getAdvertisementImg() {
        return this.advertisementImg;
    }

    public void setAdvertisementImg(String advertisementImg) {
        this.advertisementImg = advertisementImg;
    }

    public Integer getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Integer subscribed) {
        this.subscribed = subscribed;
    }

    public String getFragId() {
        return fragId;
    }

    public void setFragId(String fragId) {
        this.fragId = fragId;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getCpCategoryId() {
        return cpCategoryId;
    }

    public void setCpCategoryId(String cpCategoryId) {
        this.cpCategoryId = cpCategoryId;
    }

}
