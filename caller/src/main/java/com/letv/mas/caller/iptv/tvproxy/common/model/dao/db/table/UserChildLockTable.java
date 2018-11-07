package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

import java.io.Serializable;

public class UserChildLockTable implements Serializable {

    private String userId;

    /**
     * -1--never set PIN, the lock default off; 0--PIN ever set, now off,
     * 1--PIN set, now on; others are illegal, regard as -1;
     */
    private Integer status = -1;

    /**
     * md5 encrypted PIN code
     */
    private String md5Pin;

    /**
     * the key to make updating PIN operation legal
     */
    private String lockToken;

    /**
     * token expire time in milliseconds
     */
    private Long tokenExpireTime;

    /**
     * content rating ids that are allowed to play for serialization
     */
    private String canPlayCRIds = "620011,620012,620062,620063,620064,620065,620066,620067";

    private String mac;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMd5Pin() {
        return md5Pin;
    }

    public void setMd5Pin(String md5Pin) {
        this.md5Pin = md5Pin;
    }

    public String getLockToken() {
        return lockToken;
    }

    public void setLockToken(String lockToken) {
        this.lockToken = lockToken;
    }

    public Long getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(Long tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public String getCanPlayCRIds() {
        return canPlayCRIds;
    }

    public void setCanPlayCRIds(String canPlayCRIds) {
        this.canPlayCRIds = canPlayCRIds;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

}
