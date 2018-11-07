package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.util.List;

public class VideoGuideResponse {

    private String result;
    private List<VideoGuideInfoTpResponse> data;

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<VideoGuideInfoTpResponse> getData() {
        return this.data;
    }

    public void setData(List<VideoGuideInfoTpResponse> data) {
        this.data = data;
    }

    public static class VideoGuideInfoTpResponse {
        private String createTime;
        private Integer id;

        /*
         * 图片地址
         */
        private String imgUrl;
        private String keyDesc;
        private String mainTitle;
        private String subTitle;
        private String updateTime;

        /*
         * 引导用户类型：1会员专享，2码流付费，3单点影片，4卡顿
         */
        private Integer userType;

        /*
         * 全屏套餐：-1暂无，2包月，3包季，5包年
         */
        private String packageType;

        /*
         * 支付方式：-1暂无，41,42信用卡，24微信，5支付宝，33乐点
         */
        private String payType;

        public String getCreateTime() {
            return this.createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Integer getId() {
            return this.id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getImgUrl() {
            return this.imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
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

        public String getUpdateTime() {
            return this.updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
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

    }
}
