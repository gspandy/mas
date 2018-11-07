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

public class GetBindInfoRequest {

    private final static Logger LOG = LoggerFactory.getLogger(GetBindInfoRequest.class);
    /**
     * 用户ID
     */
    private String userid;

    /**
     * 公司ID
     */
    private String companyid = VipTpConstant.DEFAULT_COMPANYID;

    public GetBindInfoRequest(String userid) {
        this.userid = userid;
    }

    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();
        parametersMap.put("userid", this.userid);
        parametersMap.put("companyid", this.companyid);
        parametersMap.put("sign", this.getSign());
        return parametersMap;
    }

    /**
     * 签名，签名串userid=xxx&companyid=xxx&paytype=41
     * @return
     */
    private String getSign() {
        StringBuilder sign = new StringBuilder();
        sign.append("userid=").append(this.userid).append("&companyid=").append(this.companyid).append("&paytype=41");
        try {
            return MessageDigestUtil.md5(sign.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            LOG.error("GetBandInfoRequest_" + this.userid + "_" + this.companyid + ": getSign error.", e);
        }
        return "";
    }

    /**
     * 参数校验
     * @return
     */
    public int assertValid() {
        if (StringUtils.isBlank(this.userid)) {
            return VipMsgCodeConstant.REQUEST_USERINFO_ERROR;
        }
        return VipMsgCodeConstant.REQUEST_SUCCESS;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCompanyid() {
        return this.companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

}
