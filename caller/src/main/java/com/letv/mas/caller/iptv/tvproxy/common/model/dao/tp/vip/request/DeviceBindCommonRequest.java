package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 机卡绑定通用请求封装类
 * @author KevinYi
 */
public class DeviceBindCommonRequest {

    /**
     * 设备码，设备唯一标识，必填
     */
    protected String deviceKey;

    /**
     * 设备MAC地址，必填
     */
    protected String mac;

    /**
     * 设备类型，电视传固定值1，手机传固定值2，盒子固定传3，必填
     */
    protected Integer deviceType;

    public DeviceBindCommonRequest(String deviceKey, String mac, Integer deviceType) {
        super();
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

    /**
     * 从当前对象中组装出参数Map，供调用第三方接口是作为URL参数传入
     * @return Map<String, Object>
     *         参数map，key--String，根据第三方接口调用要求，各参数key分别固定；
     *         value--Object，对应参数
     */
    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>(6);

        parametersMap.put("deviceKey", this.deviceKey);
        parametersMap.put("mac", this.mac);
        parametersMap.put("deviceType", this.deviceType);

        return parametersMap;
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

}
