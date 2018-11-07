package com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache;

import java.util.Date;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheContentConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.bean.UserStatus;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.UserChildLockTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.ErosToken;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.CollectionListInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.BalanceQueryResultResponse;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class UserCacheDao extends BaseCacheDao {

    /**
     * set user data to cache
     * @param userId
     * @param userStatus
     */
    public void setUserStatus(String userId, UserStatus userStatus) {
        String key = CacheContentConstants.USER_CACHE_FOR_USER_INFO + userId;
        this.cacheTemplate.set(key, userStatus, CommonConstants.SECONDS_OF_1_MONTH);
    }

    /**
     * set user data to cache to compatible hk and us application
     * @param username
     * @param userStatus
     */
    public void setUserStatusByUsername(String username, UserStatus userStatus) {
        this.cacheTemplate.set(username, userStatus, CommonConstants.SECONDS_OF_1_MONTH);
    }

    public UserStatus getUserStatusByName(String username) {
        return this.cacheTemplate.get(username, UserStatus.class);
    }

    public UserStatus getUserStatus(CommonParam commonParam) {
        UserStatus user = null;
        if (StringUtil.isBlank(commonParam.getUserId())) {
            return user;
        } else {
            String key = CacheContentConstants.USER_CACHE_FOR_USER_INFO + commonParam.getUserId();
            user = this.cacheTemplate.get(key, UserStatus.class);

            if (user == null) {
                user = new UserStatus();
                String uid = commonParam.getUserId();
                user.setUserName(commonParam.getUsername());
                if (StringUtils.isNotBlank(uid)) {
                    user.setUserId(StringUtil.toLong(uid, 0L));
                }
                user.setUserToken(commonParam.getUserToken());
                user.setDeviceId(commonParam.getMac());
                user.setLoginTime(new Date().getTime());
            }
            return user;
        }
    }

    public void setUserRoleId(Long userId, Long roleId) {
        String key = CacheContentConstants.USER_CACHE_FOR_ROLE_USERID + userId;
        this.cacheTemplate.set(key, roleId, CommonConstants.SECONDS_OF_1_YEAR);
    }

    public void deleteUserRoleId(Long userId) {
        String key = CacheContentConstants.USER_CACHE_FOR_ROLE_USERID + userId;
        this.cacheTemplate.delete(key);
    }

    public Long getUserRoleId(Long userId) {
        String key = CacheContentConstants.USER_CACHE_FOR_ROLE_USERID + userId;
        return this.cacheTemplate.get(key, Long.class);
    }

    public Object getRegisterAndLoginAuth(Integer subend) {
        return this.cacheTemplate.get(CacheContentConstants.TPSDK_BSCHANNEL_COMMON_PREFIX + subend, Object.class);
    }

    public ErosToken getErosToken(String userId, Integer deviceType) {
        String key = CacheContentConstants.USER_EROS_TOKEN + userId + "-" + deviceType;
        return this.cacheTemplate.get(key, ErosToken.class);
    }

    /**
     * Set eros token info to cache
     * @param userId
     * @param deviceType
     * @param erosToken
     */
    public int setErosToken(String userId, Integer deviceType, ErosToken erosToken) {
        String key = CacheContentConstants.USER_EROS_TOKEN + userId + "-" + deviceType;
        // eros avaliable time is 90 days. So we should cache 89 days for
        // ensuring token is avaliable consistently.
        return this.cacheTemplate.set(key, erosToken, CommonConstants.SECONDS_OF_1_DAY * 89);
    }

    /**
     * set deviceKey info to cache and the key contains mac
     * @param mac
     * @param deviceKey
     */
    public void setDeviceKeyInfo(String mac, String deviceKey) {
        this.cacheTemplate.set(CacheContentConstants.USER_GOLIVE_DEVICEKEY_CACHE + mac, deviceKey,
                CommonConstants.SECONDS_OF_1_MONTH);
    }

    /**
     * get deviceKey from cache by mac
     * @param mac
     * @return
     */
    public String getDeviceKeyInfo(String mac) {
        return this.cacheTemplate.get(CacheContentConstants.USER_GOLIVE_DEVICEKEY_CACHE + mac, String.class);
    }

    /**
     * get need mock non vip user flag for us application
     * @return
     */
    public Boolean getNeedMockUserFlag() {
        return this.cacheTemplate.get(CacheContentConstants.VIDEO_PLAY_IS_NEED_MOOK_NOT_VIP_LOGIN_KEY, Boolean.class);
    }

    /**
     * set need mock non vip user flag for us application
     * @param needMook
     */
    public void setNeedMockUserFlag(Boolean needMook) {
        this.cacheTemplate.set(CacheContentConstants.VIDEO_PLAY_IS_NEED_MOOK_NOT_VIP_LOGIN_KEY, needMook);
    }

    /**
     * 缓存用户的一条收藏数据,缓存时间:10秒
     * @param userId
     * @param collection
     */
    public void setUserNewAddCollection(String userId, CollectionListInfo.CollectionInfo collection) {
        this.cacheTemplate.set(CacheContentConstants.USER_NEW_ADD_COLLECTION_CACHE_KEY_PREFIX + userId, collection,
                CacheContentConstants.CACHE_EXPIRES_TEN_SECONDS);
    }

    /**
     * 获取缓存的收藏数据
     * @param userId
     * @return
     */
    public CollectionListInfo.CollectionInfo getUserNewAddColleciton(String userId) {
        return this.cacheTemplate.get(CacheContentConstants.USER_NEW_ADD_COLLECTION_CACHE_KEY_PREFIX + userId,
                CollectionListInfo.CollectionInfo.class);
    }

    /**
     * 删除缓存的收藏数据
     * @param userId
     */
    public void deleteUserNewAddCollection(String userId) {
        this.cacheTemplate.delete(CacheContentConstants.USER_NEW_ADD_COLLECTION_CACHE_KEY_PREFIX + userId);
    }

    public UserChildLockTable getChildLockByuserId(String userId) {
        if (userId == null) {
            return null;
        }
        return this.cacheTemplate.get(CacheContentConstants.USER_CHILD_LOCK_PREFIX + userId, UserChildLockTable.class);
    }

    public int setChildLockByuserId(String userId, UserChildLockTable childLock) {
        if (userId != null) {
            return this.cacheTemplate.set(CacheContentConstants.USER_CHILD_LOCK_PREFIX + userId, childLock);
        }
        return 1;
    }

    public BalanceQueryResultResponse queryBalance(CommonParam commonParam) {
        if (commonParam == null || StringUtil.isBlank(commonParam.getUserId())
                || !CacheContentConstants.IS_USER_BALANCE) {
            return null;
        }
        if (commonParam.getFlush() != null && commonParam.getFlush().intValue() == 1) {
            this.cacheTemplate.delete(CacheContentConstants.USER_BALANCE_PREFIX + commonParam.getUserId());
            return null;
        }
        return this.cacheTemplate.get(CacheContentConstants.USER_BALANCE_PREFIX + commonParam.getUserId(),
                BalanceQueryResultResponse.class);
    }

    public void setBalance(String userId, BalanceQueryResultResponse response) {
        if (!CacheContentConstants.IS_USER_BALANCE) {
            return;
        }
        if (StringUtil.isNotBlank(userId)) {
            this.cacheTemplate.set(CacheContentConstants.USER_BALANCE_PREFIX + userId, response,
                    CommonConstants.SECONDS_OF_5_MINUTE);
        }
    }

    public void deleteBalance(String userId) {
        if (StringUtil.isBlank(userId)) {
            return;
        }
        this.cacheTemplate.delete(CacheContentConstants.USER_BALANCE_PREFIX + userId);
    }
}
