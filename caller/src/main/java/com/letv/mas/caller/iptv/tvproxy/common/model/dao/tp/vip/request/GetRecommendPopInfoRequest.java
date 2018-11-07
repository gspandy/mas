package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipMsgCodeConstant;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;


public class GetRecommendPopInfoRequest {

    /**
     * 请求类型，固定值
     */
    private String type = "getPopInfo";

    /**
     * 用户id
     */
    private String userId;

    /**
     * 终端，固定值
     */
    private String terminal = "141007";

    /**
     * 设备码，设备唯一标识
     */
    private String deviceKey;

    /**
     * mac地址
     */
    private String mac;

    public GetRecommendPopInfoRequest(String userId, String deviceKey, String mac) {
        this.userId = userId;
        this.deviceKey = deviceKey;
        this.mac = mac;
    }

    /**
     * 参数校验
     * @return
     */
    public int assertValid() {
        if (StringUtils.isBlank(this.userId)) {
            return VipMsgCodeConstant.REQUEST_USERINFO_ERROR;
        }
        return VipMsgCodeConstant.REQUEST_SUCCESS;
    }

    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("type", this.type);
        parametersMap.put("userId", this.userId);
        parametersMap.put("terminal", this.terminal);
        parametersMap.put("deviceKey", this.deviceKey);
        parametersMap.put("mac", this.mac);

        return parametersMap;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTerminal() {
        return this.terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getDeviceKey() {
        return this.deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

}
