package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerificationCodeCommonRequest {
    private final Logger log = LoggerFactory.getLogger(VerificationCodeCommonRequest.class);

    /**
     * 支付平台流水
     */
    private String ordernumber;

    /**
     * 签名
     */
    private String smscode;

    /**
     * 操作类型，1--下发验证码，2--提交验证码确认支付
     */
    private Integer operType;

    public VerificationCodeCommonRequest(String ordernumber, Integer operType, String smscode) {
        this.ordernumber = ordernumber;
        this.operType = operType;
        this.smscode = smscode;
    }

    /**
     * 参数校验
     * @return
     */
    public int assertValid() {
        if (StringUtils.isBlank(this.ordernumber)) {
            return VipMsgCodeConstant.VIP_YEEPAY_VERIFY_CODE_REQUEST_CORDERID_EMPTY;
        }
        if (this.operType == null || this.operType < 1 || this.operType > 2) {
            return VipMsgCodeConstant.VIP_YEEPAY_VERIFY_CODE_REQUEST_OPERTYPE_ERROR;
        }
        if (this.operType == 2 && StringUtils.isBlank(this.smscode)) {
            return VipMsgCodeConstant.VIP_YEEPAY_VERIFY_CODE_REQUEST_SMSCODE_ERROR;
        }
        return VipMsgCodeConstant.REQUEST_SUCCESS;
    }

    /**
     * 签名
     * @return
     */
    private String getSign() {
        StringBuilder sb = new StringBuilder();
        if (this.operType == 1) {
            // 下发验证码时，签名串ordernumber=xxx&{key}
            sb.append("ordernumber=").append(this.ordernumber).append("&")
                    .append(VipTpConstant.BOSS_API_ZHIFU_COMMON_SING_KEY);
        } else if (this.operType == 2) {
            // 确认支付时，签名串smscode=xxx&ordernumber=xxx&{key}&companyid=1
            sb.append("smscode=").append(this.smscode).append("&ordernumber=").append(this.ordernumber).append("&")
                    .append(VipTpConstant.BOSS_API_ZHIFU_COMMON_SING_KEY).append("&companyid=1");
        }
        try {
            return MessageDigestUtil.md5(sb.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            log.error("getSign_error", e.getMessage(), e);
        }
        return "";
    }

    /**
     * 获取参数map集合
     * @return
     */
    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("ordernumber", this.ordernumber);
        parametersMap.put("sign", this.getSign());
        parametersMap.put("smscode", this.smscode);

        return parametersMap;
    }

    public String getOrdernumber() {
        return this.ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getSmscode() {
        return this.smscode;
    }

    public void setSmscode(String smscode) {
        this.smscode = smscode;
    }

    public Integer getOperType() {
        return this.operType;
    }

    public void setOperType(Integer operType) {
        this.operType = operType;
    }

}
