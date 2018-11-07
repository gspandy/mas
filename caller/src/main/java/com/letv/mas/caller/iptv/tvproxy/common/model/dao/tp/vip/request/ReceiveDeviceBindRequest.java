package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 领取自带机卡绑定请求封装类
 * @author KevinYi
 */
public class ReceiveDeviceBindRequest extends DeviceBindCommonRequest {
    private final Logger log = LoggerFactory.getLogger(ReceiveDeviceBindRequest.class);

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户id
     */
    private String userid;

    /**
     * 设备型号，如"X60"等，默认“-1”
     */
    private String deviceVersion;

    public ReceiveDeviceBindRequest(String username, String userid, String deviceKey, String mac, Integer deviceType,
            String deviceVersion) {
        super(deviceKey, mac, deviceType);
        this.username = username;
        this.userid = userid;
        this.deviceVersion = deviceVersion;
    }

    @Override
    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("userid", this.userid);
        parametersMap.put("deviceKey", this.deviceKey);
        parametersMap.put("mac", this.getMac());
        parametersMap.put("deviceType", this.deviceType);
        parametersMap.put("deviceVersion", this.deviceVersion);
        parametersMap.put("sign", this.getSign());

        return parametersMap;
    }

    public String getSign() {
        // 根据规则sign=md5(deviceKey=**&mc=**&deviceType=**&{key}&userid=**)计算出sign
        try {
            StringBuilder signBuilder = new StringBuilder();
            signBuilder.append("deviceKey=").append(this.getDeviceKey()).append("&");
            signBuilder.append("mac=").append(this.getMac()).append("&");
            signBuilder.append("deviceType=").append(this.getDeviceType()).append("&");
            signBuilder.append(VipTpConstant.DEVICE_BIND_SIGN_KEY).append("&");
            signBuilder.append("userid=").append(this.getUserid());

            return MessageDigestUtil.md5(signBuilder.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            log.error("getSign_error", e.getMessage(), e);
        }
        return "";
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

    public String getDeviceVersion() {
        return this.deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

}
