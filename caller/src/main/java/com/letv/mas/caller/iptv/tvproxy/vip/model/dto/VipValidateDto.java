package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.user.model.dto.ChildLockDto;

import java.util.List;

public class VipValidateDto {
    private Integer productId; // 会员id
    private String name; // 会员名
    private Integer subscribed; // 是否订阅，0、未订阅，1.已订阅
    private String desc; // 描述
    private String img; // 图片
    private Double price; // 价格
    private Long expireTime; // 会员有效期
    private Integer vipStatus; // 会员状态,0.未订阅会员,1:会员可用,2:会员已过期
    private Integer tryStatus; // 是否可以试用, 0:不可以试用,1:可以试用,还未试用, 2.试用中
    private Long tryEndTime; // 试用结束时间
    private TrialData trialData; // 试用信息
    private List<PackageInfoDto> packageInfo; // 套餐信息

    /**
     * 儿童锁信息
     */
    private ChildLockDto childLock;

    /**
     * 未订阅,不可试用,不在试用状态中
     */
    public static final int UN_SUBSCRIBED = 0;

    /**
     * 已订阅
     */
    public static final int IS_SUBSCRIBED = 1;

    /**
     * 从未订阅,可以试用
     */
    // public static final int TRY_USE = 2;

    /**
     * 试用中
     */
    public static final int TRY_USING = 2;

    /**
     * 从未订阅,可以试用
     */
    public static final int TRY_CAN = 1;

    /**
     * 不可试用
     */
    public static final int TRY_NOT_CAN = 0;

    /**
     * 非会员
     */
    public static final int VIP_NOT = 0;

    /**
     * 会员可用
     */
    public static final int VIP_CAN_USE = 1;

    /**
     * 会员过期
     */
    public static final int VIP_EXPIRE = 2;

    /**
     * 试用信息
     */
    public static class TrialData {
        private String trialField; // 单位.可能值 month year date
        private Integer trialDuration; // 时长
        private String trialDurationName; // 单位名称

        public String getTrialField() {
            return trialField;
        }

        public void setTrialField(String trialField) {
            this.trialField = trialField;
        }

        public Integer getTrialDuration() {
            return trialDuration;
        }

        public void setTrialDuration(Integer trialDuration) {
            this.trialDuration = trialDuration;
        }

        public String getTrialDurationName() {
            return trialDurationName;
        }

        public void setTrialDurationName(String trialDurationName) {
            this.trialDurationName = trialDurationName;
        }
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Integer subscribed) {
        this.subscribed = subscribed;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<PackageInfoDto> getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(List<PackageInfoDto> packageInfo) {
        this.packageInfo = packageInfo;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public TrialData getTrialData() {
        return trialData;
    }

    public void setTrialData(TrialData trialData) {
        this.trialData = trialData;
    }

    public Integer getTryStatus() {
        return tryStatus;
    }

    public void setTryStatus(Integer tryStatus) {
        this.tryStatus = tryStatus;
    }

    public Long getTryEndTime() {
        return tryEndTime;
    }

    public void setTryEndTime(Long tryEndTime) {
        this.tryEndTime = tryEndTime;
    }

    public Integer getVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(Integer vipStatus) {
        this.vipStatus = vipStatus;
    }

    public ChildLockDto getChildLock() {
        return childLock;
    }

    public void setChildLock(ChildLockDto childLock) {
        this.childLock = childLock;
    }

}
