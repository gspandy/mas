package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;

public class CheckOrderStatusCommonRequest {

    /**
     * 订单号
     */
    private String orderid;

    /**
     * 设备mac；既有需求中并不需要改字段，这里供日志使用
     */
    private String mac;

    public CheckOrderStatusCommonRequest() {
        super();
    }

    public CheckOrderStatusCommonRequest(String orderid) {
        super();
        this.orderid = orderid;
    }

    /**
     * 获取参数列表
     * @return
     * @throws Exception
     */
    public Map<String, Object> getParametersMap() throws Exception {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("corderid", this.orderid);
        parametersMap.put("companyid", VipTpConstant.DEFAULT_COMPANYID);
        parametersMap.put("sign", this.getSign());

        return parametersMap;
    }

    /**
     * 获取签名字符串，签名规则：corderid={corderid}&{signedSecKey}&companyid={companyid}
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private String getSign() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuilder sign = new StringBuilder();
        sign.append("corderid=").append(this.orderid).append("&").append(VipTpConstant.BOSS_API_ZHIFU_COMMON_SING_KEY)
                .append("&companyid=").append(VipTpConstant.DEFAULT_COMPANYID);

        return MessageDigestUtil.md5(sign.toString().getBytes(CommonConstants.UTF8));
    }

    public String getOrderid() {
        return this.orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

}
