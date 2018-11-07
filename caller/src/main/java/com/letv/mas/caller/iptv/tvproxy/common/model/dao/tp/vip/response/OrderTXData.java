package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by leeco on 2018/2/6.
 */
public class OrderTXData {
    private List<UserPackageTX> datas;
    private int ps;
    private int pn;
    private int total;
    private boolean thisTimeDone = true;

    public OrderTXData() {
    }

    public OrderTXData(List<UserPackageTX> datas, int ps, int pn, int total, boolean thisTimeDone) {
        this.datas = datas;
        this.ps = ps;
        this.pn = pn;
        this.total = total;
        this.thisTimeDone = thisTimeDone;
    }

    public int getPs() {
        return ps;
    }

    public int getPn() {
        return pn;
    }

    public int getTotal() {
        return total;
    }

    public boolean isThisTimeDone() {
        return this.thisTimeDone;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public void setPn(int pn) {
        this.pn = pn;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setThisTimeDone(boolean thisTimeDone) {
        this.thisTimeDone = thisTimeDone;
    }

    public List<UserPackageTX> getDatas() {
        return datas;
    }

    public void setDatas(List<UserPackageTX> datas) {
        this.datas = datas;
    }

    public static class UserPackageTX {
        private long id;
        private String orderNo;
        private String orderName;
        private String skuName;
        private long skuId;
        private String skuNo;
        private int skuCnt;
        private String skuImageUrl;
        private long spuId;
        private String spuNo;
        private String spuName;
        private long userId;
        private String userIp;
        private BigDecimal price; // 售卖价格
        private BigDecimal deductions; // 优惠额
        private BigDecimal payPrice; // 支付价格
        private String payChannel; // 支付通道ID
        private String payChannelName; // 支付通道
        private String payOrderId; // 支付订单号
        private String payBusinessNo; // 支付商户号
        private int status; // 订单状态
        private Date successTime; // 成功时间
        private String exchangeCode; // 商品兑换码
        private String couponCode; // 商品优惠码
        private Date createTime; // 创建时间
        private Date refundTime; // 退款时间
        private int platform; // 来源平台
        private String terminal; // 终端
        private String cpsId;
        private Integer vendorProductDurationType; // 1:年 2:月 5:日 只有超级家庭会员
                                                   // 才给这个字段
        private Integer vendorProductDuration; // 只有超级家庭会员 才给这个字段

        private String virtualOrderNo;// 虚单号 预售用
        private Integer orderType;// 订单类型: 0普通订单,1预售订单
        private Integer orderSubType;// 订单子类型: null非预售单 1预售定金订单 2预售尾款订单

        // 临时字段
        private List<Map<String, Object>> vendorProductInfos; // 商家商品信息详情

        public String getVirtualOrderNo() {
            return virtualOrderNo;
        }

        public void setVirtualOrderNo(String virtualOrderNo) {
            this.virtualOrderNo = virtualOrderNo;
        }

        public Integer getOrderType() {
            return orderType;
        }

        public void setOrderType(Integer orderType) {
            this.orderType = orderType;
        }

        public Integer getOrderSubType() {
            return orderSubType;
        }

        public void setOrderSubType(Integer orderSubType) {
            this.orderSubType = orderSubType;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getOrderName() {
            return orderName;
        }

        public void setOrderName(String orderName) {
            this.orderName = orderName;
        }

        public String getSkuName() {
            return skuName;
        }

        public void setSkuName(String skuName) {
            this.skuName = skuName;
        }

        public long getSkuId() {
            return skuId;
        }

        public void setSkuId(long skuId) {
            this.skuId = skuId;
        }

        public String getSkuNo() {
            return skuNo;
        }

        public void setSkuNo(String skuNo) {
            this.skuNo = skuNo;
        }

        public int getSkuCnt() {
            return skuCnt;
        }

        public void setSkuCnt(int skuCnt) {
            this.skuCnt = skuCnt;
        }

        public String getSkuImageUrl() {
            return skuImageUrl;
        }

        public void setSkuImageUrl(String skuImageUrl) {
            this.skuImageUrl = skuImageUrl;
        }

        public long getSpuId() {
            return spuId;
        }

        public void setSpuId(long spuId) {
            this.spuId = spuId;
        }

        public String getSpuNo() {
            return spuNo;
        }

        public void setSpuNo(String spuNo) {
            this.spuNo = spuNo;
        }

        public String getSpuName() {
            return spuName;
        }

        public void setSpuName(String spuName) {
            this.spuName = spuName;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getUserIp() {
            return userIp;
        }

        public void setUserIp(String userIp) {
            this.userIp = userIp;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getDeductions() {
            return deductions;
        }

        public void setDeductions(BigDecimal deductions) {
            this.deductions = deductions;
        }

        public BigDecimal getPayPrice() {
            return payPrice;
        }

        public void setPayPrice(BigDecimal payPrice) {
            this.payPrice = payPrice;
        }

        public String getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(String payChannel) {
            this.payChannel = payChannel;
        }

        public String getPayChannelName() {
            return payChannelName;
        }

        public void setPayChannelName(String payChannelName) {
            this.payChannelName = payChannelName;
        }

        public String getPayOrderId() {
            return payOrderId;
        }

        public void setPayOrderId(String payOrderId) {
            this.payOrderId = payOrderId;
        }

        public String getPayBusinessNo() {
            return payBusinessNo;
        }

        public void setPayBusinessNo(String payBusinessNo) {
            this.payBusinessNo = payBusinessNo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Date getSuccessTime() {
            return successTime;
        }

        public void setSuccessTime(Date successTime) {
            this.successTime = successTime;
        }

        public String getExchangeCode() {
            return exchangeCode;
        }

        public void setExchangeCode(String exchangeCode) {
            this.exchangeCode = exchangeCode;
        }

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Date getRefundTime() {
            return refundTime;
        }

        public void setRefundTime(Date refundTime) {
            this.refundTime = refundTime;
        }

        public int getPlatform() {
            return platform;
        }

        public void setPlatform(int platform) {
            this.platform = platform;
        }

        public String getTerminal() {
            return terminal;
        }

        public void setTerminal(String terminal) {
            this.terminal = terminal;
        }

        public String getCpsId() {
            return cpsId;
        }

        public void setCpsId(String cpsId) {
            this.cpsId = cpsId;
        }

        public List<Map<String, Object>> getVendorProductInfos() {
            return vendorProductInfos;
        }

        public void setVendorProductInfos(List<Map<String, Object>> vendorProductInfos) {
            this.vendorProductInfos = vendorProductInfos;
        }

        public Integer getVendorProductDurationType() {
            return vendorProductDurationType;
        }

        public void setVendorProductDurationType(Integer vendorProductDurationType) {
            this.vendorProductDurationType = vendorProductDurationType;
        }

        public Integer getVendorProductDuration() {
            return vendorProductDuration;
        }

        public void setVendorProductDuration(Integer vendorProductDuration) {
            this.vendorProductDuration = vendorProductDuration;
        }

    }
}