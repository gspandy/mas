package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 第三方SDK调用会员消费逻辑请求封装类；
 * 2015-02-05，海信直播SDK
 * @author KevinYi
 */
public class TpsdkPurchaseVipRequest extends PurchaseVipCommonRequest {

    private final static Logger LOG = LoggerFactory.getLogger(TpsdkPurchaseVipRequest.class);

    /**
     * 终端应用名
     */
    private String terminalApplication;

    /**
     * 厂商号
     */
    private String terminalBrand;

    /**
     * 渠道号
     */
    private String bsChannel;

    /**
     * 设备终端类型，如"hisense"代码为0
     */
    private Integer subend;

    public TpsdkPurchaseVipRequest(String username, Long userid, String corderid, Integer purchaseType, Long productid,
            Integer paymentChannel, String price, String activityIds, String productname, Integer av, String mac,
            Integer subend) {
        super(username, userid, corderid, purchaseType, productid, paymentChannel, price, activityIds, productname, av,
                mac);
        this.subend = subend;
    }

    /**
     * 参数校验
     * @return
     *         <h1>TYPE</h1> int
     *         <h1>RESULT</h1>
     *         直播SDK中，av必须为2；直播SDK，MAC必传；直播SDK暂只支持单点；直播SDK暂支持海信设备；
     *         直播SDK暂只支持支付宝和微信支付；
     *         父类方法校验的校验结果
     */
    @Override
    public int assertValid() {
        // 直播SDK中，av必须为2
        if (this.getAv() == null || VipTpConstant.BROADCAST_APK_VERSION_TPSDK != this.getAv()) {
            return VipMsgCodeConstant.VIP_TP_PURCHASE_VIP_REQUEST_AV_ERROR;
        }

        // 直播SDK MAC必传
        if (StringUtils.isBlank(this.getMac())) {
            return VipMsgCodeConstant.VIP_TP_PURCHASE_VIP_REQUEST_MAC_EMPTY;
        }

        // 直播SDK暂只支持单点
        if (this.getBuyType() == null || this.getBuyType() != VipTpConstant.PRODUCT_TYPE_SINGLE_FILM) {
            return VipMsgCodeConstant.VIP_TP_PURCHASE_VIP_REQUEST_ONLY_SINGLE;
        }

        // 直播SDK支持更多的设备，但是不能为空
        if (this.subend == null) {
            return VipMsgCodeConstant.VIP_TP_PURCHASE_VIP_REQUEST_SUBEND_ERROR;
        }

        // 直播SDK暂只支持支付宝和微信支付
        if (this.getPaymentChannel() == null
                || (VipTpConstant.PAYMENT_CHANNEL_ALIPAY != this.getPaymentChannel() && VipTpConstant.PAYMENT_CHANNEL_WEIXIN != this
                        .getPaymentChannel())) {
            return VipMsgCodeConstant.VIP_TP_PURCHASE_VIP_REQUEST_PAYMENTCHANNEL_ERROR;
        }
        return super.assertValid();
    }

    @Override
    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = super.getParametersMap();

        parametersMap.put("subend", this.subend);

        return parametersMap;
    }

    @Override
    public String getSign() {
        // TODO
        // 签名规则
        // corderid=xxx&userid=xxx&price=xxx&companyid=xxx&{signedSecKey}&deptid=xxx&pid=xxx&deviceid=xxx
        StringBuilder sign = new StringBuilder();
        // String secKey = "ce0806981627d00d4b96beb051a2b629";

        if (this.getBuyType() != null && this.getBuyType() == VipTpConstant.PRODUCT_TYPE_SINGLE_FILM) {
            // 单片加密串
            sign.append("corderid=").append(this.getCorderid()).append("&");
            sign.append("userid=").append(this.getUserid()).append("&");
            sign.append("price=").append(this.getPrice()).append("&");
            sign.append("companyid=").append(1).append("&");
            sign.append(VipTpConstant.BOSS_API_ZHIFU_COMMON_SING_KEY).append("&");
            sign.append("deptid=").append(this.getDeptid()).append("&");
            sign.append("pid=").append(this.getPid()).append("&");
            sign.append("deviceid=").append(this.getMac());
            // sign.append("productid=").append(this.productid);
        }
        /*
         * else {
         * // 套餐加密串
         * sign.append("corderid=").append(this.corderid).append("&");
         * sign.append("userid=").append(this.userid).append("&");
         * sign.append("companyid=").append(1).append("&");
         * sign.append(VipTpConstant.BOSS_API_ZHIFU_COMMON_SING_KEY).append("&");
         * sign.append("deptid=").append(this.deptid).append("&");
         * sign.append("pid=").append(this.pid);
         * }
         */
        try {
            return MessageDigestUtil.md5(sign.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            LOG.error("ConsumeVipRequest_" + this.getUsername() + "_" + this.getUserid() + "_" + this.getCorderid()
                    + ": getSign error.", e);
        }

        return "";
    }

    public String getTerminalApplication() {
        return this.terminalApplication;
    }

    public void setTerminalApplication(String terminalApplication) {
        this.terminalApplication = terminalApplication;
    }

    public String getTerminalBrand() {
        return this.terminalBrand;
    }

    public void setTerminalBrand(String terminalBrand) {
        this.terminalBrand = terminalBrand;
    }

    public String getBsChannel() {
        return this.bsChannel;
    }

    public void setBsChannel(String bsChannel) {
        this.bsChannel = bsChannel;
    }

    @Override
    public Integer getSubend() {
        return this.subend;
    }

    @Override
    public void setSubend(Integer subend) {
        this.subend = subend;
    }

}
