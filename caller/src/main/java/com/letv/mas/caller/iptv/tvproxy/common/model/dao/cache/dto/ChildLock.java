package com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache.dto;

import java.io.Serializable;
import java.util.List;

public class ChildLock implements Serializable {

    private String userId;

    /**
     * -1--never set PIN, the lock default off; 0--PIN ever set, now off,
     * 1--PIN set, now on; others are illegal, regard as -1;
     */
    private Integer status = -1;

    // private String mac;

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
    private String canPlayCRIds;

    /**
     * content rating id list that are allowed to play
     */
    private List<String> canPlayCRIdList;

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

    public List<String> getCanPlayCRIdList() {
        return canPlayCRIdList;
    }

    public void setCanPlayCRIdList(List<String> canPlayCRIdList) {
        this.canPlayCRIdList = canPlayCRIdList;
    }

}
