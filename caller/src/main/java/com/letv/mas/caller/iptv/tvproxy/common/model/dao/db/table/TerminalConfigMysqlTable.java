package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

public class TerminalConfigMysqlTable {

    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 应用值--terminalApplication
     */
    private String appCode;

    /**
     * 区域
     */
    private String regionName;

    /**
     * 配置的键值
     */
    private String keyName;

    /**
     * 配置的值
     */
    private String keyValue;

    /**
     * 播控方
     */
    private String broadcast;

    /**
     * 版本名称
     */
    private String version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
