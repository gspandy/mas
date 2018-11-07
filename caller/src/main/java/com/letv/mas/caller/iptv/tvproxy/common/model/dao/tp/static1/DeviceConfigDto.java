package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1;

import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * Author：apple on 17/6/20 13:15
 * eMail：dengliwei@le.com
 */
@ApiModel(value = "DeviceConfigDto", description = "设备配置信息")
public class DeviceConfigDto {
    private List<DeviceConfig> devices;

    public List<DeviceConfig> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceConfig> devices) {
        this.devices = devices;
    }
}
