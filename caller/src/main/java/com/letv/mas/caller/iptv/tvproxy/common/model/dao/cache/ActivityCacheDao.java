package com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheContentConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.activity.response.ActivityBatchTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.MD5Util;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class ActivityCacheDao extends BaseCacheDao {

    public static Boolean isDebug = false;

    public void setActivity(String direct, String posStr, CommonParam commonParam, ActivityBatchTpResponse tpResponse) {
        String key = getKey(direct, posStr, commonParam);
        int timeout = getTimeOut(direct, posStr, commonParam);
        if (key != null) {
            this.cacheTemplate.set(key, tpResponse, timeout);
        }
    }

    public ActivityBatchTpResponse getActivity(String direct, String posStr, CommonParam commonParam) {
        String key = getKey(direct, posStr, commonParam);
        if (key != null) {
            if (commonParam != null && commonParam.getFlush() != null && commonParam.getFlush().intValue() == 1) {
                this.cacheTemplate.delete(key);
            } else {
                return this.cacheTemplate.get(key, ActivityBatchTpResponse.class);
            }
        }
        return null;
    }

    public void deleteActivity(String direct, String posStr, CommonParam commonParam) {
        String key = getKey(direct, posStr, commonParam);
        if (key != null) {
            this.cacheTemplate.delete(key);
        }
    }

    public String getKey(String direct, String posStr, CommonParam commonParam) {
        String key = null;
        if (commonParam != null) {
            String uid = commonParam.getUserId() == null ? "" : commonParam.getUserId();
            String mac = commonParam.getMac() == null ? "" : commonParam.getMac();
            direct = direct == null ? "" : direct;
            key = CacheContentConstants.ACTIVITY_USER_CACHE_KEY + uid + "_" + mac + "_" + posStr + "_" + direct;
        }
        if (StringUtil.isNotBlank(key)) {
            key = MD5Util.md5(key);
        }
        return key;
    }

    public int getTimeOut(String direct, String posStr, CommonParam commonParam) {
        if (isDebug) {
            return CommonConstants.SECONDS_OF_1_MINUTE;
        }
        /*
         * if(commonParam != null &&
         * StringUtil.isNotBlank(commonParam.getUserId())){
         * return CommonConstants.SECONDS_OF_5_MINUTE;
         * }
         */
        return CommonConstants.SECONDS_OF_10_MINUTE;
    }

    public void setActivityDefaultData(String direct, String posStr, CommonParam commonParam,
            ActivityBatchTpResponse tpResponse) {
        if (StringUtil.isErrorMacForGuanXing(commonParam)) {
            return;
        }
        this.cacheTemplate.set(CacheContentConstants.ACTIVITY_NO_DATA_USER_CACHE_KEY, tpResponse,
                CommonConstants.SECONDS_OF_1_HOUR);
    }

    public ActivityBatchTpResponse getActivityDefaultData(String direct, String posStr, CommonParam commonParam) {
        if (StringUtil.isErrorMacForGuanXing(commonParam)) {
            return null;
        }
        if (commonParam != null && commonParam.getFlush() != null && commonParam.getFlush().intValue() == 1) {
            this.cacheTemplate.delete(CacheContentConstants.ACTIVITY_NO_DATA_USER_CACHE_KEY);
        } else {
            return this.cacheTemplate.get(CacheContentConstants.ACTIVITY_NO_DATA_USER_CACHE_KEY,
                    ActivityBatchTpResponse.class);
        }
        return null;
    }

    public void clearDefaultData() {
        this.cacheTemplate.delete(CacheContentConstants.ACTIVITY_NO_DATA_USER_CACHE_KEY);
    }
}
