package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.util.List;

/**
 * 付费活动DTO
 * @author yikun
 */
public class PaymentActivityDto extends BaseDto {

    /**
     * 活动id
     */
    private String activityId;

    /**
     * 活动类型，1，套餐活动 2:支付活动
     */
    private Integer type;

    /**
     * 套餐包id，type为1时有意义
     */
    private Integer monthType;

    /**
     * 活动支持的支付渠道，type为2时有意义
     */
    private List<Integer> payTypeList;

    /**
     * 折扣,代表减少多少钱，单位元
     */
    private String discount;

    /**
     * 延长天数
     */
    private Integer prolongDays;

    /**
     * 角标文案
     */
    private String iconText;

    /**
     * 标签文案
     */
    private String lableText;

    /**
     * 主体文案
     */
    private String bodyText;

    /**
     * 在每个套餐上填写的促销文字
     */
    private String packageText;

    /**
     * 营销文字
     */
    private String text;

    /**
     * 是否需要登录
     */
    private Boolean needLogin;

    /**
     * 是否允许乐点支付参与活动
     */
    private Boolean allowPayLepoint;

    /**
     * 是否有用户数量限制
     */
    private Boolean hasUserQuota;

    /**
     * 活动预设总数，-1表示不限量
     */
    private Integer quota;

    /**
     * 当前用户剩余参与次数
     */
    private Integer leftQuota;

    /**
     * 本用户参与数量限制，-1表示无限制
     */
    private Integer userQuota;

    /**
     * 乐点支付文案
     */
    private String letvPointPayText;

    private String img;// 图片
    private String url;// 定向活动二维码
    private Integer coupon;// 是否支持代金券，11：不支持， 12：与活动互斥， 13：支持

    public String getActivityId() {
        return this.activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMonthType() {
        return this.monthType;
    }

    public void setMonthType(Integer monthType) {
        this.monthType = monthType;
    }

    public List<Integer> getPayTypeList() {
        return this.payTypeList;
    }

    public void setPayTypeList(List<Integer> payTypeList) {
        this.payTypeList = payTypeList;
    }

    public String getDiscount() {
        return this.discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Integer getProlongDays() {
        return this.prolongDays;
    }

    public void setProlongDays(Integer prolongDays) {
        this.prolongDays = prolongDays;
    }

    public String getIconText() {
        return this.iconText;
    }

    public void setIconText(String iconText) {
        this.iconText = iconText;
    }

    public String getLableText() {
        return this.lableText;
    }

    public void setLableText(String lableText) {
        this.lableText = lableText;
    }

    public String getBodyText() {
        return this.bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getPackageText() {
        return this.packageText;
    }

    public void setPackageText(String packageText) {
        this.packageText = packageText;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getNeedLogin() {
        return this.needLogin;
    }

    public void setNeedLogin(Boolean needLogin) {
        this.needLogin = needLogin;
    }

    public Boolean getAllowPayLepoint() {
        return this.allowPayLepoint;
    }

    public void setAllowPayLepoint(Boolean allowPayLepoint) {
        this.allowPayLepoint = allowPayLepoint;
    }

    public Boolean getHasUserQuota() {
        return this.hasUserQuota;
    }

    public void setHasUserQuota(Boolean hasUserQuota) {
        this.hasUserQuota = hasUserQuota;
    }

    public Integer getQuota() {
        return this.quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public Integer getLeftQuota() {
        return this.leftQuota;
    }

    public void setLeftQuota(Integer leftQuota) {
        this.leftQuota = leftQuota;
    }

    public Integer getUserQuota() {
        return this.userQuota;
    }

    public void setUserQuota(Integer userQuota) {
        this.userQuota = userQuota;
    }

    public String getLetvPointPayText() {
        return this.letvPointPayText;
    }

    public void setLetvPointPayText(String letvPointPayText) {
        this.letvPointPayText = letvPointPayText;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCoupon() {
        return coupon;
    }

    public void setCoupon(Integer coupon) {
        this.coupon = coupon;
    }

}
