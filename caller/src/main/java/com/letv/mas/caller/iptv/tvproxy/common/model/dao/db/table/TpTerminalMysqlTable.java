package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

import java.util.Date;

public class TpTerminalMysqlTable {

    private Long id;
    private String terminalUUId;
    private Long seriesId;
    private String mac;
    private String ip;
    private Integer status;
    private Date activateTime;
    private Date validTime;
    private String userName;
    private Date userLoginTime;
    private Date createTime;
    private String createdBy;
    private Date updateTime;
    private String updatedBy;
    private Date expireTime;
    private Integer provid;// 省份id
    private Integer series_app_id;// 平台、型号和应用的关系表id
    private String versionName;
    private String brandName;
    private String seriesName;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getTerminalUUId() {
        return this.terminalUUId;
    }

    public void setTerminalUUId(String terminalUUId) {
        this.terminalUUId = terminalUUId;
    }

    public Long getSeriesId() {
        return this.seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getActivateTime() {
        return this.activateTime;
    }

    public void setActivateTime(Date activateTime) {
        this.activateTime = activateTime;
    }

    public Date getValidTime() {
        return this.validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getUserLoginTime() {
        return this.userLoginTime;
    }

    public void setUserLoginTime(Date userLoginTime) {
        this.userLoginTime = userLoginTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getProvid() {
        return this.provid;
    }

    public void setProvid(Integer provid) {
        this.provid = provid;
    }

    public Integer getSeries_app_id() {
        return this.series_app_id;
    }

    public void setSeries_app_id(Integer series_app_id) {
        this.series_app_id = series_app_id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

}
