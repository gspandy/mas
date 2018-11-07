package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;

/**
 * 一键支付绑定查询请求封装类
 * @author KevinYi
 */
public class CheckOneKeyPayRequest extends VipCommonRequest {

    private Integer paytype; // 支付方式，即paymentChannel，当前需求暂只包含校验paypal支付，固定传39

    public CheckOneKeyPayRequest() {
        super();
    }

    public CheckOneKeyPayRequest(String username, Long userid, String mac, Integer paytype) {
        super(username, userid, mac);
        this.paytype = paytype;
    }

    @Override
    public Map<String, Object> getParametersMap() throws Exception {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("userid", this.getUserid());
        parametersMap.put("paytype", this.paytype);
        parametersMap.put("sign", this.getSign());

        return parametersMap;
    }

    private String getSign() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuilder sign = new StringBuilder();

        sign.append("userid=").append(this.getUserid()).append("&paytype=").append(this.paytype).append("&")
                .append(VipTpConstant.BOSS_API_ZHIFU_COMMON_SING_KEY);

        return MessageDigestUtil.md5(sign.toString().getBytes(CommonConstants.UTF8));
    }

    public Integer getPaytype() {
        return this.paytype;
    }

    public void setPaytype(Integer paytype) {
        this.paytype = paytype;
    }

    /**
     * 参数校验
     * @return
     */
    public int assertValid() {
        if (this.userid == null || this.userid < 0) {
            return VipMsgCodeConstant.REQUEST_USERINFO_ERROR;
        }
        // 支付渠道只支持paypal、易宝
        if (this.paytype == null || (this.paytype != 39 && this.paytype != 41)) {
            return VipMsgCodeConstant.REUQEST_PAYMENTCHANNEL_ERROR;
        }
        return VipMsgCodeConstant.REQUEST_SUCCESS;
    }
}
