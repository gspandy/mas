package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

/**
 * 领取7天免费会员调用第三方接口参数列表，新增“康佳30天限免和点卡”需求（通用版，2014-08-20）
 * @author dunhongqin
 */
public class FreeVipRequest {

    /**
     * 登录账户的用户名（非登录名）
     */
    private String username;

    /**
     * 设备mac
     */
    private String mac;

    /**
     * 终端类型
     */
    private String terminalType;

    /**
     * 设备系列号
     */
    private String terminalSeries;

    /**
     * 设备品牌
     */
    private String terminalBrand;

    /**
     * 是否是康佳电视,"0"--不是，"1"是，“康佳30天限免和点卡”需求新增
     */
    private String iskonka;

    /**
     * 康佳设备唯一标识，设备串号，“康佳30天限免和点卡”需求新增，iskonka为"1"时有效
     */
    private String imei;

    /**
     * 用户中心id
     */
    private Long userid;

    public FreeVipRequest() {
        super();
    }

    public FreeVipRequest(String username, Long userid, String mac, String terminalType, String terminalBrand,
            String terminalSeries) {
        super();
        this.username = username;
        this.userid = userid;
        this.mac = mac;
        this.terminalType = terminalType;
        this.terminalBrand = terminalBrand;
        this.terminalSeries = terminalSeries;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getTerminalType() {
        return this.terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getTerminalSeries() {
        return this.terminalSeries;
    }

    public void setTerminalSeries(String terminalSeries) {
        this.terminalSeries = terminalSeries;
    }

    public String getTerminalBrand() {
        return this.terminalBrand;
    }

    public void setTerminalBrand(String terminalBrand) {
        this.terminalBrand = terminalBrand;
    }

    public String getIskonka() {
        return this.iskonka;
    }

    public void setIskonka(String iskonka) {
        this.iskonka = iskonka;
    }

    public String getImei() {
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

}
