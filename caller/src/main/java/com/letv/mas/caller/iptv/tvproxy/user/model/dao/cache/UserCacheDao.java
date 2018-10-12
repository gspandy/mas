package com.letv.mas.caller.iptv.tvproxy.user.model.dao.cache;


import com.letv.mas.caller.iptv.tvproxy.user.annotation.Iptv;
import com.letv.mas.caller.iptv.tvproxy.user.constant.CacheContentConstants;
import com.letv.mas.caller.iptv.tvproxy.user.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.user.model.bean.UserStatus;
import com.letv.mas.caller.iptv.tvproxy.user.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.user.model.dto.response.BalanceQueryResultResponse;
import com.letv.mas.caller.iptv.tvproxy.user.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Iptv
public class UserCacheDao extends BaseCacheDao {

    //@HystrixCommand
    public UserStatus getUserStatusByName(String username) {
        return this.cacheTemplate.get(username, UserStatus.class);
    }

    //@HystrixCommand(fallbackMethod = "getUserStatusFallback")
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
    public UserStatus getUserStatusFallback(CommonParam commonParam) {
        UserStatus user = new UserStatus();
        String uid = commonParam.getUserId();
        user.setUserName(commonParam.getUsername());
        if (StringUtils.isNotBlank(uid)) {
            user.setUserId(StringUtil.toLong(uid, 0L));
        }
        user.setUserToken(commonParam.getUserToken());
        user.setDeviceId(commonParam.getMac());
        user.setLoginTime(new Date().getTime());
        return user;
    }
    //@HystrixCommand
    public BalanceQueryResultResponse queryBalance(CommonParam commonParam){
        if (commonParam == null || StringUtil.isBlank(commonParam.getUserId()) || !CacheContentConstants.IS_USER_BALANCE) {
            return null;
        }
        if(commonParam.getFlush() != null && commonParam.getFlush().intValue() ==1){
            this.cacheTemplate.delete(CacheContentConstants.USER_BALANCE_PREFIX + commonParam.getUserId());
            return null;
        }
        return this.cacheTemplate.get(CacheContentConstants.USER_BALANCE_PREFIX + commonParam.getUserId(), BalanceQueryResultResponse.class);
    }

    //@HystrixCommand
    public void setBalance(String userId,BalanceQueryResultResponse response){
        if(!CacheContentConstants.IS_USER_BALANCE){
            return;
        }
        if (StringUtil.isNotBlank(userId)) {
            this.cacheTemplate.set(CacheContentConstants.USER_BALANCE_PREFIX + userId, response,CommonConstants.SECONDS_OF_5_MINUTE);
        }
    }
}
