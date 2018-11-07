package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.NumberFormatUtil;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;


public class GetCheckoutCounterRequest {

    /**
     * 用户ID
     */
    private String userid;

    /**
     * 消费类型，1--影片单点，2--套餐，3--直播单点
     */
    private Integer purchaseType;

    /**
     * 会员类型，0--非会员，适用于单点；1--PC端会员，普通会员；9--TV端会员，高级会员
     */
    private Integer vipType;

    /**
     * 套餐优先参数，用于定向弹窗
     */
    private String packagePriority;
    /**
     * 支付渠道优先参数，用于定向弹窗
     */
    private String channelPriority;

    public GetCheckoutCounterRequest(String userid, Integer purchaseType, String packagePriority,
            String channelPriority, Integer vipType) {
        this.userid = userid;
        this.purchaseType = purchaseType;
        this.packagePriority = packagePriority;
        this.channelPriority = channelPriority;
        this.vipType = vipType;
    }

    /**
     * 参数校验
     * @return
     */
    public int assertValid() {
        if (StringUtils.isNotBlank(this.userid)
                && (!NumberFormatUtil.isNumeric(this.userid) || (Long.valueOf(this.userid)) < 0)) {
            return VipMsgCodeConstant.REQUEST_USERINFO_ERROR;
        }
        if (this.purchaseType == null || this.purchaseType < 1 || this.purchaseType > 3) {
            return VipMsgCodeConstant.VIP_GET_CHECK_COUNTER_REQUEST_PURCHASE_TYPE_ERROR;
        }
        return VipMsgCodeConstant.REQUEST_SUCCESS;
    }

    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>(6);

        parametersMap.put("userid", this.userid);
        parametersMap.put("purchaseType", this.purchaseType);
        parametersMap.put("vipType", this.vipType);

        return parametersMap;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getPurchaseType() {
        return this.purchaseType;
    }

    public void setPurchaseType(Integer purchaseType) {
        this.purchaseType = purchaseType;
    }

    public Integer getVipType() {
        return this.vipType;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
    }

    public String getPackagePriority() {
        return this.packagePriority;
    }

    public void setPackagePriority(String packagePriority) {
        this.packagePriority = packagePriority;
    }

    public String getChannelPriority() {
        return this.channelPriority;
    }

    public void setChannelPriority(String channelPriority) {
        this.channelPriority = channelPriority;
    }

}
