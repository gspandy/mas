package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.Browser;

import java.util.List;
import java.util.Map;

public class CheckOutCounter extends BaseData {
    private String checkOutCounterType;// 收银台类型，direct--定向收银台，common--标准收银台
    private List<TypeInfo> productTypes;// 商品类型列表
    private Map<String, ProductType> productDatas;// 套餐类型数据
    private Map<String, PaymentActivityDto> activities;// 优惠活动，key--活动id，value--活动信息

    public String getCheckOutCounterType() {
        return this.checkOutCounterType;
    }

    public void setCheckOutCounterType(String checkOutCounterType) {
        this.checkOutCounterType = checkOutCounterType;
    }

    public List<TypeInfo> getProductTypes() {
        return this.productTypes;
    }

    public void setProductTypes(List<TypeInfo> productTypes) {
        this.productTypes = productTypes;
    }

    public Map<String, ProductType> getProductDatas() {
        return this.productDatas;
    }

    public void setProductDatas(Map<String, ProductType> productDatas) {
        this.productDatas = productDatas;
    }

    public Map<String, PaymentActivityDto> getActivities() {
        return this.activities;
    }

    public void setActivities(Map<String, PaymentActivityDto> activities) {
        this.activities = activities;
    }

    public static class TypeInfo {
        private String productType;// product(goods) type
        private String productTypeName;// product(goods) type name

        public String getProductType() {
            return this.productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getProductTypeName() {
            return this.productTypeName;
        }

        public void setProductTypeName(String productTypeName) {
            this.productTypeName = productTypeName;
        }

    }

    public static class ProductType {
        private String productType;// product(goods) type
        private String productTypeName;// product(goods) type name
        private List<ProductData> items;// product(goods) items

        public String getProductType() {
            return this.productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getProductTypeName() {
            return this.productTypeName;
        }

        public void setProductTypeName(String productTypeName) {
            this.productTypeName = productTypeName;
        }

        public List<ProductData> getItems() {
            return this.items;
        }

        public void setItems(List<ProductData> items) {
            this.items = items;
        }

        public static class ProductData implements Comparable<ProductData> {
            private String productId;// product id
            private String productName;// product name
            private String currentPrice;// 现价，单位：元，支持小数
            private String originPrice;// 原价，单位：元
            private String title;// 标题，有活动展示活动标题，没活动展示套餐标题
            private String subTitle;// 活动角标文案
            private String img;// 图片
            private String url;// 二维码地址，就是详情里的h5地址
            private String showName;// 展示名称
            private String activityIds;// 套餐活动id
            private Integer order;// 套餐展示顺序
            private String duration;// 套餐时长，单位：年／月／天

            public Integer getDurationType() {
                return durationType;
            }

            public void setDurationType(Integer durationType) {
                this.durationType = durationType;
            }

            private Integer durationType;// 时长类型 1:年 2:月 5:天
            private String discount;// 折扣信息，如“8.5”
            /**
             * 打折开始时间，格式为“yyyy-MM-dd HH:mm:ss”
             */
            private String discountStart;

            /**
             * 打折结束时间，格式为“yyyy-MM-dd HH:mm:ss”
             */
            private String discountEnd;

            /**
             * 是否使用当前价格（该字段仅在乐点支付时有效）
             */
            private Boolean useCurrentPrice;

            /**
             * product description or activity description
             */
            private String desc;

            /**
             * 计算平均一个月花多少钱，也可以支持其他文案
             */
            private String additionalText;

            /**
             * 如果套餐有详情介绍，则需要返给客户端
             */
            private Browser detail;

            /**
             * 活动是否支持代金券，0--不支持，1--支持
             */
            private Integer supportVoucher = 1;
            /**
             * 不支持代金券的按钮文案
             */
            private String unsupportVoucherText;

            public String getProductId() {
                return this.productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return this.productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
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

            public String getTitle() {
                return this.title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSubTitle() {
                return this.subTitle;
            }

            public void setSubTitle(String subTitle) {
                this.subTitle = subTitle;
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

            public String getShowName() {
                return this.showName;
            }

            public void setShowName(String showName) {
                this.showName = showName;
            }

            public String getActivityIds() {
                return this.activityIds;
            }

            public void setActivityIds(String activityIds) {
                this.activityIds = activityIds;
            }

            public Integer getOrder() {
                return this.order;
            }

            public void setOrder(Integer order) {
                this.order = order;
            }

            public String getDuration() {
                return this.duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getDiscount() {
                return this.discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
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

            public Boolean getUseCurrentPrice() {
                return this.useCurrentPrice;
            }

            public void setUseCurrentPrice(Boolean useCurrentPrice) {
                this.useCurrentPrice = useCurrentPrice;
            }

            public String getDesc() {
                return this.desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getAdditionalText() {
                return this.additionalText;
            }

            public void setAdditionalText(String additionalText) {
                this.additionalText = additionalText;
            }

            public Browser getDetail() {
                return this.detail;
            }

            public void setDetail(Browser detail) {
                this.detail = detail;
            }

            public Integer getSupportVoucher() {
                return supportVoucher;
            }

            public void setSupportVoucher(Integer supportVoucher) {
                this.supportVoucher = supportVoucher;
            }

            public String getUnsupportVoucherText() {
                return unsupportVoucherText;
            }

            public void setUnsupportVoucherText(String unsupportVoucherText) {
                this.unsupportVoucherText = unsupportVoucherText;
            }

            @Override
            public int compareTo(ProductData o) {
                // TODO Auto-generated method stub
                if (o == null) {
                    return 1;
                } else {
                    if (o.getOrder() == null) {
                        return 1;
                    } else if (this.order == null) {
                        return -1;
                    } else {
                        return this.order - o.getOrder();
                    }
                }
            }

        }

    }
}
