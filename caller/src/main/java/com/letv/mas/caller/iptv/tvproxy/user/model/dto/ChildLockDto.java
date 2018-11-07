package com.letv.mas.caller.iptv.tvproxy.user.model.dto;

import com.letv.mas.caller.iptv.tvproxy.user.UserConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * This class encapsulates basic information of child lock, which contains lock
 * status, userId, MAC (temp design), and lockToken (if next step is updating
 * the lock, a lockToken is needed), bug not PIN (the key password, only exists
 * in cache storage)
 * @author KevinYi
 */
@ApiModel(value = "ChildLockDto", description = "儿童锁信息")
public class ChildLockDto implements Serializable {

    @ApiModelProperty(value = "用户id")
    private String userId;

    /**
     * -1--never set PIN, the lock default off; 0--PIN ever set, now off,
     * 1--PIN set, now on; others are illegal, regard as -1;
     */
    @ApiModelProperty(value = "锁状态：-1 未设置，0 设置关闭，1 设置开启")
    private Integer status = -1;

    // private String mac;

    /**
     * the key to make updating PIN operation legal
     */
    @ApiModelProperty(value = "锁key")
    private String lockToken;

    /**
     * lockToken effective duration in milliseconds, -1 means effective forever
     */
    @ApiModelProperty(value = "锁时长，－1 永久，单位毫秒")
    private Long tokenEffectiveDuration = UserConstants.USER_CHILD_LOCK_SET_TOKEN_EXPIRE_TIME;

    /**
     * content rating ids that are allowed to play
     */
    @ApiModelProperty(value = "可播内容频次id集合")
    private List<String> canPlayCRIds;

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

    public Long getTokenEffectiveDuration() {
        return tokenEffectiveDuration;
    }

    public void setTokenEffectiveDuration(Long tokenEffectiveDuration) {
        this.tokenEffectiveDuration = tokenEffectiveDuration;
    }

    public List<String> getCanPlayCRIds() {
        return canPlayCRIds;
    }

    public void setCanPlayCRIds(List<String> canPlayCRIds) {
        this.canPlayCRIds = canPlayCRIds;
    }

}
