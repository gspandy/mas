package com.letv.mas.caller.iptv.tvproxy.channel.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "ChannelDto", description = "频道信息")
public class ChannelDto extends BaseDto {

    /**
     *
     */
    private static final long serialVersionUID = -8195312205996672700L;

    @ApiModelProperty(value = "频道名称")
    private String cName;// 频道名称

    @ApiModelProperty(value = "频道编码")
    private String cCode;// 频道编码

    @ApiModelProperty(value = "频道位置，在调整频道顺序时使用")
    private Integer position;// 频道位置，在调整频道顺序时使用

    @ApiModelProperty(value = "会员模块相关，提供一些背景图")
    private List<BaseData> resources;// 会员模块相关，提供一些背景图

    @ApiModelProperty(value = "活动列表，提供一些活动信息")
    private List<BaseData> activities;// 活动列表，提供一些活动信息

    @ApiModelProperty(value = "会员内容推荐，会员专享活动或者推荐一些会员专享专辑")
    private BlockContentDto vipRecommend;// 会员内容推荐，会员专享活动或者推荐一些会员专享专辑

    @ApiModelProperty(value = "［历史版本］色块")
    private String color;

    @ApiModelProperty(value = "［历史版本］触发条件")
    private String condition;

    @ApiModelProperty(value = "［历史版本］搜索url")
    private String searchUrl;

    @ApiModelProperty(value = "频道id")
    private String channelId;

    @ApiModelProperty(value = "分类id")
    private String categoryId;

    @ApiModelProperty(value = "［历史版本］数据源")
    private int dataSrc;

    /**
     * 活动是否有更新，0--没有，1--有
     */
    @ApiModelProperty(value = "活动是否有更新，0--没有，1--有")
    private Integer hasUpdate;

    public String getcName() {
        return this.cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcCode() {
        return this.cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public List<BaseData> getResources() {
        return this.resources;
    }

    public void setResources(List<BaseData> resources) {
        this.resources = resources;
    }

    public List<BaseData> getActivities() {
        return this.activities;
    }

    public void setActivities(List<BaseData> activities) {
        this.activities = activities;
    }

    public BlockContentDto getVipRecommend() {
        return this.vipRecommend;
    }

    public void setVipRecommend(BlockContentDto vipRecommend) {
        this.vipRecommend = vipRecommend;
    }

    public Integer getPosition() {
        return this.position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSearchUrl() {
        return this.searchUrl;
    }

    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getDataSrc() {
        return this.dataSrc;
    }

    public void setDataSrc(int dataSrc) {
        this.dataSrc = dataSrc;
    }

    public Integer getHasUpdate() {
        return this.hasUpdate;
    }

    public void setHasUpdate(Integer hasUpdate) {
        this.hasUpdate = hasUpdate;
    }

}
