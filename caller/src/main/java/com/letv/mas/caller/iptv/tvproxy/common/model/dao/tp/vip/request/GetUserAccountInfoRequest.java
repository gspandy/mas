package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstantUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 获取用户账户信息请求封装类
 * @author KevinYi
 */
public class GetUserAccountInfoRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户id
     */
    private String userid;

    /**
     * 设备码，设备唯一标识，必填
     */
    private String deviceKey;

    /**
     * 设备MAC地址，必填
     */
    private String mac;

    /**
     * 设备类型，电视传固定值1，手机传固定值2，盒子固定传3，必填
     */
    private Integer deviceType;

    /**
     * 播控方
     */
    private Integer broadcastId;

    /**
     * 应用版本
     */
    private String terminalApplication;

    public GetUserAccountInfoRequest(String username, String userid, String deviceKey, String mac, Integer deviceType) {
        super();
        this.username = username;
        this.userid = userid;
        this.deviceKey = deviceKey;
        this.mac = mac;
        this.deviceType = deviceType;
        this.init();
    }

    private void init() {
        if (StringUtils.isBlank(this.deviceKey)) {
            this.deviceKey = StringUtils.EMPTY;
        }
    }

    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("userid", this.userid);
        parametersMap.put("mac", this.mac);
        parametersMap.put("deviceKey", this.deviceKey);

        return parametersMap;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public Integer getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getBroadcastId() {
        return this.broadcastId;
    }

    public void setBroadcastId(Integer broadcastId) {
        this.broadcastId = broadcastId;
    }

    public String getTerminalApplication() {
        return this.terminalApplication;
    }

    public void setTerminalApplication(String terminalApplication) {
        this.terminalApplication = terminalApplication;
    }

    public int assertValid() {
        if (StringUtils.isBlank(this.username) || StringUtils.isBlank(this.userid)) {
            return VipMsgCodeConstant.REQUEST_USERINFO_ERROR;
        }
        if (this.deviceType != null && !VipTpConstantUtils.deviceTypeIsLegal(this.deviceType)) {
            return VipMsgCodeConstant.VIP_GET_ACCOUNT_REQUEST_DEVICETYPE_ERROR;
        }
        return VipMsgCodeConstant.REQUEST_SUCCESS;
    }
}
