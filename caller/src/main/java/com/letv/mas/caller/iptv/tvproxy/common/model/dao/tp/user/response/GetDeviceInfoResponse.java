package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

public class GetDeviceInfoResponse {

    private String code;
    private DeviceInfo values;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DeviceInfo getValues() {
        return this.values;
    }

    public void setValues(DeviceInfo values) {
        this.values = values;
    }

    public static class DeviceInfo {
        private String mac;
        private String key;

        public String getMac() {
            return this.mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

    }

}
