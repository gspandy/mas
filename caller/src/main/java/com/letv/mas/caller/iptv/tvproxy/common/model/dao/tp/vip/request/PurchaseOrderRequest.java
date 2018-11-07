package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstantUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PurchaseOrderRequest {

    private final static Logger log = LoggerFactory.getLogger(PurchaseOrderRequest.class);

    /**
     * 登录账户的用户名（非登录名）
     */
    private String username;

    /**
     * 用户中心id
     */
    private Long userid;

    /**
     * 产品类型,1-单片，2-产品包
     */
    private Integer productType;

    /**
     * 产品id；单片时，专辑或视频 id；套餐包时套餐id（如2-包月，3-包季，5-包年，40-7天免费，44-包月和康佳30天限免等）；
     */
    private String productId;

    /**
     * 套餐类型，例如: 48元套餐，暂为空
     */
    private String packageType;

    /**
     * 终端mac
     */
    private String mac;

    /**
     * 与CNTV合作版本的电视mac，一般与mac相同
     */
    private String cntvMac;

    /**
     * 设备子终端类型，7--CNTV合作，12--CIBN合作等
     */
    private String subend = "";

    /**
     * 终端类型，即end，固定传9，表示TV2.0
     */
    private String terminalType;

    /**
     * 资源类型，标识当前产品是单片还是套餐包，(a: 专辑 v: 视频)
     */
    private String mediaType;

    /**
     * 支付渠道，如5--支付宝，24--微信等
     */
    private Integer paymentChannel;

    /**
     * 订单价格（单位：元）
     */
    private Integer amount;

    /**
     * 产品描述，套餐名称或专辑视频title
     */
    private String description;

    /**
     * 是否是CNTV合作版本（打洞）
     */
    private Boolean isFromCntv;

    /**
     * 是否是CIBN合作版本
     */
    private Boolean isFromCibn;

    /**
     * 设备品牌
     */
    private String terminalBrand;

    /**
     * 设备系列
     */
    private String terminalSeries;

    /**
     * 下单时参与的活动id，多个id用","分割
     */
    private String activityIds;

    /**
     * 型号，终端序列号，一般同terminalSeries
     */
    private String serialNumber;

    /**
     * apk version，当前TV版的版本，国光整改之后新增需求，0--letv，1--国广版
     */
    private Integer av;

    /**
     * 代金券兑换码
     */
    private String couponCode;

    public PurchaseOrderRequest() {
        super();
    }

    public PurchaseOrderRequest(String username, Long userid, Integer productType, String productId, String mac,
            String mediaType, Integer paymentChannel, Integer amount, String description, String terminalType,
            Boolean isFromCntv, Boolean isFromCibn, String terminalBrand, String terminalSeries, String serialNumber) {
        this.username = username;
        this.userid = userid;
        this.productType = productType;
        this.productId = productId;
        this.mac = mac;
        this.mediaType = mediaType;
        this.paymentChannel = paymentChannel;
        this.amount = amount;
        this.description = description;
        this.terminalType = terminalType;
        this.isFromCntv = isFromCntv;
        this.isFromCibn = isFromCibn;
        this.terminalBrand = terminalBrand;
        this.terminalSeries = terminalSeries;
        this.serialNumber = serialNumber;
    }

    public boolean isServicePackage() {
        return VipTpConstant.PRODUCT_TYPE_SERVICE_PACKAGE == this.productType;
    }

    /**
     * 获取参数列表
     * @return
     */
    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("userid", this.userid);
        parametersMap.put("ptype", this.productType);
        parametersMap.put("pid", this.productId);
        parametersMap.put("end", this.getTerminalType());
        parametersMap.put("mac", this.cntvMac == null ? this.mac : this.cntvMac);
        parametersMap.put("subend", this.getSubend());
        parametersMap.put("packtype", "");
        parametersMap.put("activityIds", this.getActivityIds());
        parametersMap.put("couponCode", this.couponCode);
        parametersMap.put("channel", this.paymentChannel == null ? "" : this.paymentChannel);
        parametersMap.put("serialNumber", this.serialNumber == null ? "" : this.serialNumber);
        parametersMap.put("sign", this.getOpenFreeOrderSign(this.username, this.productId));

        return parametersMap;
    }

    /**
     * 获取支付中心公共机密key
     * @param userName
     * @param orderType
     * @return
     */
    private String getOpenFreeOrderSign(String username, String productid) {
        String sign = "";
        try {
            String key = MessageDigestUtil.md5(VipTpConstant.OPEN_ORDER_SIGN_KEY.getBytes());
            sign = MessageDigestUtil.md5((key + username + productid).getBytes());
        } catch (Exception e) {
            log.error("PurchaseOrderRequest#getOpenFreeOrderSign error:", e);
        }
        return sign;
    }

    public String getSubend() {
        if (this.subend != null) {
            return this.subend;
        }
        Integer result = null;
        if (this.getIsFromCntv() != null && this.getIsFromCntv()) {
            result = VipTpConstant.SUB_ORDER_FROM_7;
        } else if (this.getIsFromCibn() != null && this.getIsFromCibn()) {
            result = VipTpConstant.SUB_ORDER_FROM_12;
        } else if (this.terminalSeries != null && this.terminalBrand != null
                && "letv".equalsIgnoreCase(this.terminalBrand)) {
            result = VipTpConstantUtils.getSubendByTerminalSeries(this.terminalSeries);
        } else if (this.terminalBrand != null) {
            result = VipTpConstantUtils.getSubendByTerminalBrand(this.terminalBrand);
        }
        return result == null ? "" : result + "";
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getProductType() {
        return this.productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPackageType() {
        return this.packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getCntvMac() {
        return this.cntvMac;
    }

    public void setCntvMac(String cntvMac) {
        this.cntvMac = cntvMac;
    }

    public String getTerminalType() {
        return this.terminalType == null ? VipTpConstant.ORDER_FROM_TV2 : this.terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getMediaType() {
        return this.mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Integer getPaymentChannel() {
        return this.paymentChannel;
    }

    public void setPaymentChannel(Integer paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsFromCntv() {
        return this.isFromCntv == null ? Boolean.FALSE : this.isFromCntv;
    }

    public void setIsFromCntv(Boolean isFromCntv) {
        this.isFromCntv = isFromCntv;
    }

    public Boolean getIsFromCibn() {
        return this.isFromCibn;
    }

    public void setIsFromCibn(Boolean isFromCibn) {
        this.isFromCibn = isFromCibn;
    }

    public String getTerminalBrand() {
        return this.terminalBrand;
    }

    public void setTerminalBrand(String terminalBrand) {
        this.terminalBrand = terminalBrand;
    }

    public String getTerminalSeries() {
        return this.terminalSeries;
    }

    public void setTerminalSeries(String terminalSeries) {
        this.terminalSeries = terminalSeries;
    }

    public String getActivityIds() {
        return this.activityIds == null ? "" : this.activityIds;
    }

    public void setActivityIds(String activityIds) {
        this.activityIds = activityIds;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setSubend(String subend) {
        this.subend = subend;
    }

    public Integer getAv() {
        return this.av;
    }

    public void setAv(Integer av) {
        this.av = av;
    }

    public String getCouponCode() {
        return this.couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    /**
     * 参数校验
     * @return
     */
    public int assertValid() {
        if (this.userid == null || this.userid < 0) {
            return VipMsgCodeConstant.REQUEST_USERINFO_ERROR;
        }
        if (this.productType == null || this.productType < 1 || this.productType > 2) {
            return VipMsgCodeConstant.VIP_PURCHASE_ORDER_REQUEST_PRODUCT_TYPE_ERROR;
        }
        if (StringUtils.isBlank(this.productId)) {
            return VipMsgCodeConstant.VIP_PURCHASE_ORDER_REQUEST_PRODUCTID_ERROR;
        }
        if (this.amount == null || this.amount < 0) {
            return VipMsgCodeConstant.VIP_PURCHASE_ORDER_REQUEST_AMOUNT_ERROR;
        }
        return VipMsgCodeConstant.REQUEST_SUCCESS;
    }
}
