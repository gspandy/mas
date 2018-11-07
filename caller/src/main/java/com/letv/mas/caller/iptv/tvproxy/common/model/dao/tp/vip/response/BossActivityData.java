package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.io.Serializable;
import java.util.List;

public class BossActivityData implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2293736139102738933L;
    private String activityPackageId;// 套餐活动组合唯一id
    private String id;// 活动id
    private Integer type;// 活动类型， 1--套餐优惠，2--支付方式优惠
    private Integer coupon;// 是否可以使用代金卷 11：不支持， 12：与活动互斥， 13：支持
    private String title;// 价格标题
    private String label;// 促销文字
    private String text;// 营销文字
    private String beginDate;// 开始日期
    private String endDate;// 结束日期
    private String beginTime;// 每日开始时间
    private String endTime;// 每日结束时间
    private Integer priority;// 优先级，越小优先级越高
    private Integer userQuota;// 用户次数限制，-1 不限
    // private String orienteering;// 定向活动， 没有此字段代表不是定向活动。
    private Integer monthType;// 套餐类型
    private String deduction;// 减免金额
    private Integer extension;// 延长天数
    private Integer quota;// 限制件数 -1 不限
    private String packageText;// 套餐级别促销文案
    private Integer vipType;// vip级别 1，移动，9，全屏
    private Boolean needLogin;// 是否需要登录
    private Boolean allowPayLepoint;// 是否支持乐点支付
    private List<Integer> payTypeList;// 活动支付方式payTypes, 空数组代表
                                      // “套餐优惠”，非空数组代表“支付方式优惠”
    private List<String> terminals;// 终端
    private Integer tvOrient;// 0表示否，1表示是tv定向活动
    private String tvOrientImg;// tv定向活动图片
    private String tvOrientUrl;// tv定向活动描述URL

    public String getActivityPackageId() {
        return this.activityPackageId;
    }

    public void setActivityPackageId(String activityPackageId) {
        this.activityPackageId = activityPackageId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCoupon() {
        return this.coupon;
    }

    public void setCoupon(Integer coupon) {
        this.coupon = coupon;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBeginDate() {
        return this.beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getUserQuota() {
        return this.userQuota;
    }

    public void setUserQuota(Integer userQuota) {
        this.userQuota = userQuota;
    }

    public Integer getMonthType() {
        return this.monthType;
    }

    public void setMonthType(Integer monthType) {
        this.monthType = monthType;
    }

    public String getDeduction() {
        return this.deduction;
    }

    public void setDeduction(String deduction) {
        this.deduction = deduction;
    }

    public Integer getExtension() {
        return this.extension;
    }

    public void setExtension(Integer extension) {
        this.extension = extension;
    }

    public Integer getQuota() {
        return this.quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public String getPackageText() {
        return this.packageText;
    }

    public void setPackageText(String packageText) {
        this.packageText = packageText;
    }

    public Integer getVipType() {
        return this.vipType;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
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

    public List<Integer> getPayTypeList() {
        return this.payTypeList;
    }

    public void setPayTypeList(List<Integer> payTypeList) {
        this.payTypeList = payTypeList;
    }

    public List<String> getTerminals() {
        return this.terminals;
    }

    public void setTerminals(List<String> terminals) {
        this.terminals = terminals;
    }

    public Integer getTvOrient() {
        return this.tvOrient;
    }

    public void setTvOrient(Integer tvOrient) {
        this.tvOrient = tvOrient;
    }

    public String getTvOrientImg() {
        return this.tvOrientImg;
    }

    public void setTvOrientImg(String tvOrientImg) {
        this.tvOrientImg = tvOrientImg;
    }

    public String getTvOrientUrl() {
        return this.tvOrientUrl;
    }

    public void setTvOrientUrl(String tvOrientUrl) {
        this.tvOrientUrl = tvOrientUrl;
    }
}
