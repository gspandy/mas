package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

/**
 * 根据ip获取所属国家信息
 * @author wangsk
 */
public class LocationTpDto {
    private String clientIP;// 客户端ip
    private String userCountry;// 所属国家，例如 ：CN/US
    private String statusCode;// 返回状态码

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}
