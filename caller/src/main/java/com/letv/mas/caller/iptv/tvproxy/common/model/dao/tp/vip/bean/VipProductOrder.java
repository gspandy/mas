package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * 会员产品订单表
 * @author wangxin5
 */
public class VipProductOrder {
    private String orderid;
    private String orderName;
    private long userId;
    private int country;
    private double sellingPrice;
    private double deductions;
    private double payPrice;
    private int status;
    private int logisticsStatus;
    private String userIp;
    private Date successTime;
    private Date createTime;
    private Date startTime; // 服务开始时间，不完全精确（不考虑订单关闭），仅为满足大数据团队需求，所谓大数据就是写SQL查询
    private Date endTime; // 服务结束时间，不完全精确（不考虑订单关闭），仅为满足大数据团队需求，所谓大数据就是写SQL查询
    private int payChannel;
    private String payChannelDesc;
    private String payMerchantBusinessId;
    private String businessId;
    private int isRefund;
    private int productType;
    private int productSubtype;
    private String productId;
    private int productDuration;
    private int productDurationType;
    private int deviceType;
    private String device;
    private String payOrderid;
    private String packageId;
    private String subscribePackageId = null;
    private double subscribePrice = 0.0;
    private String productName;
    private int presentChannel;
    private int isRenew = 0;
    private int platform = 0;
    private String terminal = null;
    private String cpsId = "";
    private String appProductId = "";
    private String taxCode = "";
    private double tax = 0.0;
    private String coupon = "";
    private String outTradeNo = "";
    private String cardNum = null;
    private int orderFlag = 0;

    private int activityId = 0;
    private String orderDesc = "{}";
    private String orderVersion = null; // 订单version

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(double payPrice) {
        this.payPrice = payPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(int logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public String getPayChannelDesc() {
        return payChannelDesc;
    }

    public void setPayChannelDesc(String payChannelDesc) {
        this.payChannelDesc = payChannelDesc;
    }

    public String getPayMerchantBusinessId() {
        return payMerchantBusinessId;
    }

    public void setPayMerchantBusinessId(String payMerchantBusinessId) {
        this.payMerchantBusinessId = payMerchantBusinessId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public int getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(int isRefund) {
        this.isRefund = isRefund;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getProductDuration() {
        return productDuration;
    }

    public void setProductDuration(int productDuration) {
        this.productDuration = productDuration;
    }

    public int getProductDurationType() {
        return productDurationType;
    }

    public void setProductDurationType(int productDurationType) {
        this.productDurationType = productDurationType;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getPayOrderid() {
        return payOrderid;
    }

    public void setPayOrderid(String payOrderid) {
        this.payOrderid = payOrderid;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public int getProductSubtype() {
        return productSubtype;
    }

    public void setProductSubtype(int productSubtype) {
        this.productSubtype = productSubtype;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getIsRenew() {
        return isRenew;
    }

    public void setIsRenew(int isRenew) {
        this.isRenew = isRenew;
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

    public String getSubscribePackageId() {
        return subscribePackageId;
    }

    public void setSubscribePackageId(String subscribePackageId) {
        this.subscribePackageId = subscribePackageId;
    }

    public double getSubscribePrice() {
        return subscribePrice;
    }

    public void setSubscribePrice(double subscribePrice) {
        this.subscribePrice = subscribePrice;
    }

    public int getPresentChannel() {
        return presentChannel;
    }

    public void setPresentChannel(int presentChannel) {
        this.presentChannel = presentChannel;
    }

    public String getAppProductId() {
        return appProductId;
    }

    public void setAppProductId(String appProductId) {
        this.appProductId = appProductId;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public int getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(int orderFlag) {
        this.orderFlag = orderFlag;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public double getDeductions() {
        return deductions;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public String getOrderVersion() {
        return orderVersion;
    }

    public void setOrderVersion(String orderVersion) {
        this.orderVersion = orderVersion;
    }

    public double getPayable() {
        BigDecimal b1 = new BigDecimal(this.getSellingPrice());
        BigDecimal b2 = new BigDecimal(this.getDeductions());
        return b1.subtract(b2).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
}
