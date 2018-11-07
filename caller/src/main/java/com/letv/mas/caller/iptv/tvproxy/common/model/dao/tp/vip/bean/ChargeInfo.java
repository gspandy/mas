package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean;

/**
 * Author：apple on 17/8/14 10:28
 * eMail：dengliwei@le.com
 * {
 * "vid": 10043221, // 视频id
 * "app": "{\"appId\":\"6tvod\",\"vipAppId\":\"3tvod\"}", // ios productId
 * "chargeType": 2, // 付费类型 0：点播 1：点播或会员 2：会员
 * "isCharge": 1, // 收费：1，免费：0
 * "chargePlatform": 10041, // 付费平台
 * “chargeRule”: { // 定价方案（当类型为用券、点播时返回）
 * "memberPrice": 0.02, // 会员价
 * "price": 0.04, // 原价
 * "preSalePrice": 0.02, // 预售卖价
 * "discountStartTime": 1500458025, // 折扣开始时间
 * "discountEndTime": 1500459134 // 折扣结束时间
 * },
 * "vipInfo": "1,9", // 对应授权会员id（chargeType为会员时返回）
 * "status": 1, // 状态 0:未发布 1： 已发布，2：定时发布
 * "iscoupon": 1, // 是否支持观影券。1：支持，2：不支持
 * "tryLookTime": 360 // 试看时长（秒）
 * }
 */
public class ChargeInfo {
    /**
     * 专辑id
     */
    private String pid;
    /**
     * 专辑id
     */
    private String vid;
    /**
     * app信息
     */
    private String app;
    /**
     * 付费类型 0：点播 1：点播和包月 2：包月，3：免费但TV包月收费＝》代理层兼容超级家庭会员内部扩展为：16-chargeType
     */
    private String chargeType;
    /**
     * 收费：1，免费：0
     */
    private String isCharge;
    /**
     * 付费平台
     */
    private String chargePlatform;
    /**
     * 对应授权会员id（chargeType为会员时返回）
     */
    private String vipInfo;
    /**
     * 状态 0:未发布 1： 已发布，2：定时发布
     */
    private String status;
    /**
     * 是否支持观影券。1：支持，2：不支持
     */
    private String iscoupon;
    /**
     * 试看时长（秒）
     */
    private String tryLookTime;
    /**
     * 有效时长（天）
     */
    private Integer validTime;
    /**
     * 定价方案（当类型为用券、点播时返回）
     */
    private ChargeRule chargeRule;
    /**
     * 定价id
     */
    private Integer chargeId; // 定价id

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getChargeType() {
        return chargeType;
    }

    public int getChargeType4i() {
        return getChargeType4i(this.chargeType);
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getIsCharge() {
        return isCharge;
    }

    public void setIsCharge(String isCharge) {
        this.isCharge = isCharge;
    }

    public String getChargePlatform() {
        return chargePlatform;
    }

    public void setChargePlatform(String chargePlatform) {
        this.chargePlatform = chargePlatform;
    }

    public String getVipInfo() {
        return vipInfo;
    }

    public void setVipInfo(String vipInfo) {
        this.vipInfo = vipInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIscoupon() {
        return iscoupon;
    }

    public void setIscoupon(String iscoupon) {
        this.iscoupon = iscoupon;
    }

    public String getTryLookTime() {
        return tryLookTime;
    }

    public void setTryLookTime(String tryLookTime) {
        this.tryLookTime = tryLookTime;
    }

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    public ChargeRule getChargeRule() {
        return chargeRule;
    }

    public void setChargeRule(ChargeRule chargeRule) {
        this.chargeRule = chargeRule;
    }

    public Integer getChargeId() {
        return chargeId;
    }

    public void setChargeId(Integer chargeId) {
        this.chargeId = chargeId;
    }

    /**
     * 版权信息
     */
    public static class ChargeRule {
        /**
         * 会员价
         */
        private String memberPrice;
        /**
         * 原价
         */
        private String price;
        /**
         * 预售卖价
         */
        private String preSalePrice;
        /**
         * 折扣开始时间
         */
        private String discountStartTime;
        /**
         * 折扣结束时间
         */
        private String discountEndTime;

        public String getMemberPrice() {
            return memberPrice;
        }

        public void setMemberPrice(String memberPrice) {
            this.memberPrice = memberPrice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPreSalePrice() {
            return preSalePrice;
        }

        public void setPreSalePrice(String preSalePrice) {
            this.preSalePrice = preSalePrice;
        }

        public String getDiscountStartTime() {
            return discountStartTime;
        }

        public void setDiscountStartTime(String discountStartTime) {
            this.discountStartTime = discountStartTime;
        }

        public String getDiscountEndTime() {
            return discountEndTime;
        }

        public void setDiscountEndTime(String discountEndTime) {
            this.discountEndTime = discountEndTime;
        }
    }

    public static boolean isHomeVip(String chargeType) {
        boolean ret = false;
        if (null != chargeType && chargeType.trim().length() > 0) {
            ret = chargeType.contains("16-");
        }
        return ret;
    }

    public static int getChargeType4i(String chargeType) {
        int ret = -1;
        if (null != chargeType && chargeType.trim().length() > 0) {
            if (chargeType.contains("-")) { // 内部扩展case
                String[] arr = chargeType.split("-");
                if (arr.length > 0) {
                    ret = Integer.parseInt(arr[arr.length - 1]);
                }
            } else {
                ret = Integer.parseInt(chargeType);
            }
        }
        return ret;
    }
}
