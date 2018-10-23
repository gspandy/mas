package com.letv.mas.caller.iptv.tvproxy.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Boss v2 机卡绑定信息封装类
 * @author KevinYi
 */
public class DeviceBindConentV2 implements Serializable {

    private List<DeviceBindConentItem> items;

    public List<DeviceBindConentItem> getItems() {
        return items;
    }

    public void setItems(List<DeviceBindConentItem> items) {
        this.items = items;
    }

    public static class DeviceBindConentItem implements Serializable {

        /**
         * 
         */
        private String mac;

        /**
         * 
         */
        private String deviceKey;

        /**
         * 会员ID
         */
        private Integer productId;

        /**
         * 
         */
        private String productName;

        /**
         * 
         */
        private String productInfo;

        /**
         * 会员图片地址
         */
        private String productImage;

        /**
         * 设备类型
         */
        private Integer deviceType;

        /**
         * 设备类型名称
         */
        private String deviceTypeName;

        /**
         * 会员类型，101:basic 102：add_on 103：站外
         */
        private Integer typeGroup;

        /**
         * 绑定时长数字
         */
        private Integer bindDuration;

        /**
         * 绑定时长单位，1-年，2-月，5-天
         */
        private Integer bindDurationUnit;

        /**
         * 兑换卡时长,单位-月
         */
        private Integer cardDuration;

        /**
         * 当前绑定状态 1-已上报，2-已同步（可领取），3-领取中，6已领取，9已退货
         */
        private Integer status;

        /**
         * 绑定用户ID，只有status=6时 该项存在
         */
        private String activeUserId;

        /**
         * 激活时间，只有status=6时，该项存在
         */
        private Long activeTime;

        /**
         * 激活后的套餐结束时间，只有status=6时，该项存在
         */
        private Long vipEndTime;

        /**
         * 会员结束时间有可能不存在
         */
        private Long vipBindEndTime;

        /**
         * 只有站外会员包返回该字段，包含站外会员兑换码信息
         */
        private DeviceBindSiteoffVipCode vipCodeInfo;

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getDeviceKey() {
            return deviceKey;
        }

        public void setDeviceKey(String deviceKey) {
            this.deviceKey = deviceKey;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductInfo() {
            return productInfo;
        }

        public void setProductInfo(String productInfo) {
            this.productInfo = productInfo;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public Integer getTypeGroup() {
            return typeGroup;
        }

        public void setTypeGroup(Integer typeGroup) {
            this.typeGroup = typeGroup;
        }

        public Integer getBindDuration() {
            return bindDuration;
        }

        public void setBindDuration(Integer bindDuration) {
            this.bindDuration = bindDuration;
        }

        public Integer getBindDurationUnit() {
            return bindDurationUnit;
        }

        public void setBindDurationUnit(Integer bindDurationUnit) {
            this.bindDurationUnit = bindDurationUnit;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Long getActiveTime() {
            return activeTime;
        }

        public void setActiveTime(Long activeTime) {
            this.activeTime = activeTime;
        }

        public Long getVipEndTime() {
            return vipEndTime;
        }

        public void setVipEndTime(Long vipEndTime) {
            this.vipEndTime = vipEndTime;
        }

        public String getActiveUserId() {
            return activeUserId;
        }

        public void setActiveUserId(String activeUserId) {
            this.activeUserId = activeUserId;
        }

        public DeviceBindSiteoffVipCode getVipCodeInfo() {
            return vipCodeInfo;
        }

        public void setVipCodeInfo(DeviceBindSiteoffVipCode vipCodeInfo) {
            this.vipCodeInfo = vipCodeInfo;
        }

        public Integer getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(Integer deviceType) {
            this.deviceType = deviceType;
        }

        public String getDeviceTypeName() {
            return deviceTypeName;
        }

        public void setDeviceTypeName(String deviceTypeName) {
            this.deviceTypeName = deviceTypeName;
        }

        public Integer getCardDuration() {
            return cardDuration;
        }

        public void setCardDuration(Integer cardDuration) {
            this.cardDuration = cardDuration;
        }

        public Long getVipBindEndTime() {
            return vipBindEndTime;
        }

        public void setVipBindEndTime(Long vipBindEndTime) {
            this.vipBindEndTime = vipBindEndTime;
        }

        public static class DeviceBindSiteoffVipCode implements Serializable {
            private String code;

            /**
             * 绑定时长数字
             */
            private Integer duration;

            /**
             * 绑定时长单位，1-年，2-月，5-天
             */
            private Integer durationUnit;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public Integer getDuration() {
                return duration;
            }

            public void setDuration(Integer duration) {
                this.duration = duration;
            }

            public Integer getDurationUnit() {
                return durationUnit;
            }

            public void setDurationUnit(Integer durationUnit) {
                this.durationUnit = durationUnit;
            }

        }
    }
}
