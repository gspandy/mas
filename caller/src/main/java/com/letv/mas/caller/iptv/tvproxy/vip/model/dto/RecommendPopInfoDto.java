package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.util.List;

public class RecommendPopInfoDto extends BaseDto {

    /**
     * 弹窗类型，0：会员到期续费提醒弹窗，1：会员新片推荐弹窗；2：会员活动弹窗,3：新片预约提醒。
     * 目前tv端只有类型0
     */
    private Integer type;

    /**
     * 是否需要弹窗，false:不弹窗，true：弹窗
     */
    private Boolean shouldPop;

    /**
     * vip级别，0：非会员，1：普通会员，2：高级会员
     */
    private Integer vipLevel;

    /**
     * 是否过期，true:已过期，false:未过期
     */
    private Boolean isExpired;

    /**
     * 过期天数或会员有效天数，
     * 当isExpired为true时是过期多少天，当isExpired为false是有效期剩余多少天
     */
    private Integer days;

    /**
     * 优惠多少钱，0:表示不优惠，大于0：表示优惠多少钱
     */
    private Integer price;

    /**
     * 起始天数，会员有效期剩余或过期最少多少天
     */
    private Integer start;

    /**
     * 截止天数，会员有效期剩余或过期最大多少天
     */
    private Integer end;

    /**
     * 按钮名称
     */
    private String buttonName;

    /**
     * 过滤用户类型 1：全部用户 ，2 过期用户，3 一直非会员用户 ，4 根据条件过滤
     */
    private Integer userType;

    private List<GuideInfo> showInfo;

    public static class GuideInfo {
        /**
         * 1--对应native,表示本地打开,2--跳h5页面
         */
        private Integer openType;

        /**
         * 本地需要打开的id
         */
        private String id;

        /**
         * 套餐ID，多个套餐id之间用逗号隔开
         */
        private String productId;

        /**
         * 支付渠道，多个支付渠道用逗号隔开
         */
        private String paymentChannel;

        /**
         * 图片地址
         */
        private String pic;

        /**
         * 标题
         */
        private String title;

        /**
         * 跳h5页面的url
         */
        private String jumpUrl;

        public Integer getOpenType() {
            return this.openType;
        }

        public void setOpenType(Integer openType) {
            this.openType = openType;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProductId() {
            return this.productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getPaymentChannel() {
            return this.paymentChannel;
        }

        public void setPaymentChannel(String paymentChannel) {
            this.paymentChannel = paymentChannel;
        }

        public String getPic() {
            return this.pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getJumpUrl() {
            return this.jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }

    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getShouldPop() {
        return this.shouldPop;
    }

    public void setShouldPop(Boolean shouldPop) {
        this.shouldPop = shouldPop;
    }

    public Integer getVipLevel() {
        return this.vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }

    public Boolean getIsExpired() {
        return this.isExpired;
    }

    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    public Integer getDays() {
        return this.days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStart() {
        return this.start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return this.end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String getButtonName() {
        return this.buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public Integer getUserType() {
        return this.userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public List<GuideInfo> getShowInfo() {
        return this.showInfo;
    }

    public void setShowInfo(List<GuideInfo> showInfo) {
        this.showInfo = showInfo;
    }

}
