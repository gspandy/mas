package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "VideoPlayConsumeGuideInfoDto", description = "播放引导页信息")
public class VideoPlayConsumeGuideInfoDto extends BaseData {

    @ApiModelProperty(value = "活动id")
    private Integer id;
    @ApiModelProperty(value = "活动图地址")
    private String imgUrl;
    /**
     * 引导用户类型：1前贴片，2码流付费，3单点影片，4卡顿
     */
    @ApiModelProperty(value = "引导用户类型：1前贴片，2码流付费，3单点影片，4卡顿")
    private Integer userType;
    /**
     * 全屏套餐：-1暂无，2包月，3包季，5包年
     */
    @ApiModelProperty(value = "全屏套餐：-1暂无，2包月，3包季，5包年")
    private String packageType;
    /**
     * 支付方式：-1暂无，41,42信用卡，24微信，5支付宝，33乐点
     */
    @ApiModelProperty(value = "支付方式：-1暂无，41,42信用卡，24微信，5支付宝，33乐点")
    private String payType;

    @ApiModelProperty(value = "活动描述")
    private String keyDesc;
    @ApiModelProperty(value = "活动主标题")
    private String mainTitle;
    @ApiModelProperty(value = "活动副标题")
    private String subTitle;
    /**
     * vip package id
     */
    @ApiModelProperty(value = "订单类型")
    private String orderType;

    /**
     * activity id
     */
    @ApiModelProperty(value = "活动id，多个逗号分隔")
    private String activityIds;

    /**
     * 播放引导对接URM，所以需要定义字段记录位置信息
     */
    @ApiModelProperty(value = "播放引导位置值，由URM或观星商定之")
    private String position;

    /**
     * 播放引导场景，1-前贴片，2-码流付费，3-单点影片，4-卡顿，5-仅会员
     */
    @ApiModelProperty(value = "播放引导场景，1-前贴片，2-码流付费，3-单点影片，4-卡顿，5-仅会员")
    private Integer activeScene;

    /**
     * 是否展示单点购买按钮
     */
    @ApiModelProperty(value = "是否展示单点购买按钮")
    private String showButton;

    @ApiModelProperty(value = "URM后台的活动id")
    private String campaignId;// URM后台的活动id
    @ApiModelProperty(value = "上报字段")
    private String touchMessageId;// 上报字段
    @ApiModelProperty(value = "URM后台的触达位id")
    private String touchSpotId;// URM后台的触达位id

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getUserType() {
        return this.userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getPackageType() {
        return this.packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getPayType() {
        return this.payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyDesc() {
        return this.keyDesc;
    }

    public void setKeyDesc(String keyDesc) {
        this.keyDesc = keyDesc;
    }

    public String getMainTitle() {
        return this.mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getActivityIds() {
        return this.activityIds;
    }

    public void setActivityIds(String activityIds) {
        this.activityIds = activityIds;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getActiveScene() {
        return activeScene;
    }

    public void setActiveScene(Integer activeScene) {
        this.activeScene = activeScene;
    }

    public String getShowButton() {
        return showButton;
    }

    public void setShowButton(String showButton) {
        this.showButton = showButton;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getTouchMessageId() {
        return touchMessageId;
    }

    public void setTouchMessageId(String touchMessageId) {
        this.touchMessageId = touchMessageId;
    }

    public String getTouchSpotId() {
        return touchSpotId;
    }

    public void setTouchSpotId(String touchSpotId) {
        this.touchSpotId = touchSpotId;
    }

}
