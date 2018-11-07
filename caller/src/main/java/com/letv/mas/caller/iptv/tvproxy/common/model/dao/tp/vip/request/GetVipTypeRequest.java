package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonMsgCodeConstant;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;


public class GetVipTypeRequest {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 要确认的会员类型
     */
    private Integer vipType;

    /**
     * mac地址
     */
    private String mac;

    /**
     * 机卡绑定设备编号
     */
    private String deviceKey;

    public GetVipTypeRequest(String userId, Integer vipType, String mac, String deviceKey) {
        this.userId = userId;
        this.vipType = vipType;
        this.mac = mac;
        this.deviceKey = deviceKey;
    }

    /**
     * 参数校验
     * @return
     */
    public int assertValid() {
        return CommonMsgCodeConstant.REQUEST_SUCCESS;
    }

    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("userId", this.userId);
        parametersMap.put("vipType", this.vipType);
        parametersMap.put("mac", this.mac);
        if (StringUtils.isBlank(this.deviceKey)) {
            this.deviceKey = "";
        }
        parametersMap.put("deviceKey", this.deviceKey);

        return parametersMap;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getVipType() {
        return this.vipType;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDeviceKey() {
        return this.deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

}
