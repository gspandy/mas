package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.util.List;

/**
 * 定向弹窗接口返回数据封装类，包括：续费弹窗数据返回、新片推荐弹窗数据返回、活动弹窗数据返回
 * @author liyunlong
 */
public class GetRecommendPopInfoTpResponse {

    /**
     * 返回码，0：成功，1：失败
     */
    private String code;

    /**
     * 错误信息描述
     */
    private String msg;

    /**
     * 详细业务信息
     */
    private RecommendPopInfo values;

    public static class RecommendPopInfo {

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

        /**
         * 弹窗图片信息
         */
        private List<ShowInfo> showInfo;

        /**
         * 会员信息
         */
        private VipInfo vipInfo;

        /**
         * 价格信息
         */
        private PriceInfo priceInfo;

        public static class PriceInfo {

        }

        public static class VipInfo {

            /**
            *
            */
            private Boolean isExpired;

            /**
            *
            */
            private Integer days;

            /**
            *
            */
            private Integer vipLevel;

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

            public Integer getVipLevel() {
                return this.vipLevel;
            }

            public void setVipLevel(Integer vipLevel) {
                this.vipLevel = vipLevel;
            }

        }

        public static class ShowInfo {

            /**
             * 名称
             */
            private String name;

            /**
             * 位置
             */
            private String position;

            /**
             * url地址
             */
            private String url;

            /**
             * 图片url
             */
            private String picUrl;

            /**
             * 链接信息
             */
            private String link;

            /**
             * 图片地址，存在另一个字段picUrl，是PC端其他类型的弹窗
             */
            private String pic;

            /**
             * 标题
             */
            private String title;

            public String getName() {
                return this.name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPosition() {
                return this.position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getUrl() {
                return this.url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getPicUrl() {
                return this.picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getLink() {
                return this.link;
            }

            public void setLink(String link) {
                this.link = link;
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

        public List<ShowInfo> getShowInfo() {
            return this.showInfo;
        }

        public void setShowInfo(List<ShowInfo> showInfo) {
            this.showInfo = showInfo;
        }

        public VipInfo getVipInfo() {
            return this.vipInfo;
        }

        public void setVipInfo(VipInfo vipInfo) {
            this.vipInfo = vipInfo;
        }

        public PriceInfo getPriceInfo() {
            return this.priceInfo;
        }

        public void setPriceInfo(PriceInfo priceInfo) {
            this.priceInfo = priceInfo;
        }
    }

    /**
     * @see com.letv.itv.v2.tp.vip.response.GetRecommendPopInfoTpResponse#code
     * @return
     */
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RecommendPopInfo getValues() {
        return this.values;
    }

    public void setValues(RecommendPopInfo values) {
        this.values = values;
    }

}
