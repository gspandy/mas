package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.bean;

import java.util.List;

/**
 * Created by wangli on 4/28/17.
 * 收银台信息
 */
public class PVCashier {

    // 模板id
    private String tvTemplateId;
    // 模板类型
    private int templateType;
    // 基本信息
    private Template baseInfo;
    // 套餐数组
    private List<CashierPackage> sales;

    public String getTvTemplateId() {
        return tvTemplateId;
    }

    public void setTvTemplateId(String tvTemplateId) {
        this.tvTemplateId = tvTemplateId;
    }

    public int getTemplateType() {
        return templateType;
    }

    public void setTemplateType(int templateType) {
        this.templateType = templateType;
    }

    public Template getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(Template baseInfo) {
        this.baseInfo = baseInfo;
    }

    public List<CashierPackage> getSales() {
        return sales;
    }

    public void setSales(List<CashierPackage> sales) {
        this.sales = sales;
    }

    public static class Template {
        // 背景图
        private String background;
        // 视频ID
        private String vid;

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }
    }

    public static class CashierPackage {

        // 套餐ID
        private String saleId;
        // 活动Id
        private String actId;
        // 活动小图
        private String actImg;
        // 活动描述
        private String actDescription;
        // 奖品领取地址
        private String actReceivedUrl;
        // 收银台商品Id
        private String cashierProductId;
        // 是否默认套餐
        private boolean isDefault;
        // 礼品数组
        private List<Present> presents;

        // 自动续费标识（true:订阅自动续费，false:非自动续费）
        private boolean renewal;

        public String getSaleId() {
            return saleId;
        }

        public void setSaleId(String saleId) {
            this.saleId = saleId;
        }

        public String getActId() {
            return actId;
        }

        public void setActId(String actId) {
            this.actId = actId;
        }

        public String getActImg() {
            return actImg;
        }

        public void setActImg(String actImg) {
            this.actImg = actImg;
        }

        public String getActDescription() {
            return actDescription;
        }

        public void setActDescription(String actDescription) {
            this.actDescription = actDescription;
        }

        public String getActReceivedUrl() {
            return actReceivedUrl;
        }

        public void setActReceivedUrl(String actReceivedUrl) {
            this.actReceivedUrl = actReceivedUrl;
        }

        public String getCashierProductId() {
            return cashierProductId;
        }

        public void setCashierProductId(String cashierProductId) {
            this.cashierProductId = cashierProductId;
        }

        public List<Present> getPresents() {
            return presents;
        }

        public void setPresents(List<Present> presents) {
            this.presents = presents;
        }

        public boolean isDefault() {
            return isDefault;
        }

        public void setDefault(boolean aDefault) {
            isDefault = aDefault;
        }

        public boolean isRenewal() {
            return renewal;
        }

        public void setRenewal(boolean renewal) {
            this.renewal = renewal;
        }

    }

    public static class Present {

        // 礼品Id
        private String presentId;
        // 礼品标价
        private String presentPrice;
        // 礼品名称
        private String presentName;
        // 礼品类型
        private Integer presentType;

        public String getPresentId() {
            return presentId;
        }

        public void setPresentId(String presentId) {
            this.presentId = presentId;
        }

        public String getPresentPrice() {
            return presentPrice;
        }

        public void setPresentPrice(String presentPrice) {
            this.presentPrice = presentPrice;
        }

        public String getPresentName() {
            return presentName;
        }

        public void setPresentName(String presentName) {
            this.presentName = presentName;
        }

        public Integer getPresentType() {
            return presentType;
        }

        public void setPresentType(Integer presentType) {
            this.presentType = presentType;
        }
    }
}
