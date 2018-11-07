package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.util.List;

/**
 * 获取套餐包请求结果封装类
 * @author KevinYi
 */
public class PricePackageListTpResponse {

    /**
     * 响应状态，“1”--响应成功，“0”--响应失败
     */
    private String status;

    /**
     * 套餐列表
     */
    private List<PackageRecord> packageList;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PackageRecord> getPackageList() {
        return this.packageList;
    }

    public void setPackageList(List<PackageRecord> packageList) {
        this.packageList = packageList;
    }

    /**
     * 套餐具体信息封装类
     * @author KevinYi
     */
    public static class PackageRecord {

        /**
         * 套餐id，boss返回的是int型
         */
        private String monthType;

        /**
         * 套餐名称
         */
        private String name;

        /**
         * 套餐适用类型，也及会员类型，1--普通vip套餐列表，9--高级vip套餐列表
         */
        private Integer end;

        /**
         * 现价，单位：元
         */
        private String currentPrice;

        /**
         * 原价，单位：元
         */
        private String originPrice;

        /**
         * 套餐时长，单位：天；每月按31天算
         */
        private Integer days;

        /**
         * 打折开始时间，格式为“yyyy-MM-dd HH:mm:ss”
         */
        private String discountStart;

        /**
         * 打折结束时间，格式为“yyyy-MM-dd HH:mm:ss”
         */
        private String discountEnd;

        /**
         * 适用终端列表，141001--Web，141002--PC客户端，141003--手机，141005--Pad，1410006--M站，
         * 141007--TV，141009--超级手机
         */
        private List<String> terminals;

        /**
         * 是否使用当前价格（该字段仅在乐点支付时有效）
         */
        private Boolean useCurrentPrice;

        /**
         * 移动端图片，TV端不使用
         */
        private String mobileImg;

        /**
         * 超级手机端图片，TV端不使用
         */
        private String superMobileImg;

        /**
         * 排序，实际boss已经根据该字段排好序了，值越小，排序越靠前
         */
        private Integer sort;

        /**
         * 套餐描述文案
         */
        private String vipDesc;

        public String getMonthType() {
            return this.monthType;
        }

        public void setMonthType(String monthType) {
            this.monthType = monthType;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getEnd() {
            return this.end;
        }

        public void setEnd(Integer end) {
            this.end = end;
        }

        public String getCurrentPrice() {
            return this.currentPrice;
        }

        public void setCurrentPrice(String currentPrice) {
            this.currentPrice = currentPrice;
        }

        public String getOriginPrice() {
            return this.originPrice;
        }

        public void setOriginPrice(String originPrice) {
            this.originPrice = originPrice;
        }

        public Integer getDays() {
            return this.days;
        }

        public void setDays(Integer days) {
            this.days = days;
        }

        public String getDiscountStart() {
            return this.discountStart;
        }

        public void setDiscountStart(String discountStart) {
            this.discountStart = discountStart;
        }

        public String getDiscountEnd() {
            return this.discountEnd;
        }

        public void setDiscountEnd(String discountEnd) {
            this.discountEnd = discountEnd;
        }

        public List<String> getTerminals() {
            return this.terminals;
        }

        public void setTerminals(List<String> terminals) {
            this.terminals = terminals;
        }

        public Boolean getUseCurrentPrice() {
            return this.useCurrentPrice;
        }

        public void setUseCurrentPrice(Boolean useCurrentPrice) {
            this.useCurrentPrice = useCurrentPrice;
        }

        public String getMobileImg() {
            return this.mobileImg;
        }

        public void setMobileImg(String mobileImg) {
            this.mobileImg = mobileImg;
        }

        public String getSuperMobileImg() {
            return this.superMobileImg;
        }

        public void setSuperMobileImg(String superMobileImg) {
            this.superMobileImg = superMobileImg;
        }

        public Integer getSort() {
            return this.sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public String getVipDesc() {
            return this.vipDesc;
        }

        public void setVipDesc(String vipDesc) {
            this.vipDesc = vipDesc;
        }

    }
}
