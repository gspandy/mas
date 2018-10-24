package com.letv.mas.caller.iptv.tvproxy.common.dao.cache;

import com.letv.mas.caller.iptv.tvproxy.common.bean.SubscribeInfoV2;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheContentConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.dao.BaseCacheDao;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO liyunlong 会员类缓存完全可以放内存，因为量非常小
@Component
public class VipCacheDao extends BaseCacheDao {

    /**
     * set user vip endTime to cache
     * @param userId
     * @param deviceKey
     * @param seniorEndTime
     */
    public void setUserVipSeniorEndTime(String userId, String deviceKey, Long seniorEndTime) {
        String key = CacheContentConstants.getUserVipEndTimeKey(userId, deviceKey);
        this.cacheTemplate.set(key, seniorEndTime, CommonConstants.SECONDS_OF_1_DAY);
    }

    private String getVipInfoV2Key(String userId, Integer terminal) {
        return CacheContentConstants.VIP_INFO_V2 + userId + "_" + terminal;
    }


    /**
     * 设置用户会员信息(lepay)
     * @param subscribeInfoV2s
     * @param commonParam
     * @return
     */
    public void setVipInfoV3(List<SubscribeInfoV2> subscribeInfoV2s, CommonParam commonParam) {
        Map<String, SubscribeInfoV2> vipInfoMap = null;
        Assert.notNull(commonParam.getUserId(), "'userId' can't null");
        if (null != subscribeInfoV2s && subscribeInfoV2s.size() > 0) {
            vipInfoMap = new HashMap<String, SubscribeInfoV2>();
            for (SubscribeInfoV2 subscribeInfoV2 : subscribeInfoV2s) {
                if (null != subscribeInfoV2) {
                    vipInfoMap.put(subscribeInfoV2.getVendorNo(), subscribeInfoV2);
                }
            }
            String key = this.getVipInfoV2Key(commonParam.getUserId(), 0);
            this.cacheTemplate.set(key, vipInfoMap, CacheContentConstants.CACHE_EXPIRES_ONE_DAY);
        }
    }

    /**
     * 防盗链行为记录频控（R）
     * @return
     */
    public String getAntiReport(String devId, CommonParam commonParam) {
        String key = CacheContentConstants.ACT_REPORT_ANTI_REPLAY_KEY_PREFIX + devId;
        if (commonParam.getFlush() != null && commonParam.getFlush() == 1) {
            this.cacheTemplate.delete(key);
            return null;
        }
        return this.cacheTemplate.get(key, String.class);
    }

    /**
     * 防盗链行为记录频控（W）
     * @return
     */
    public void setAntiReport(String devId, CommonParam commonParam) {
        String key = CacheContentConstants.ACT_REPORT_ANTI_REPLAY_KEY_PREFIX + devId;
        String mac = commonParam.getMac();
        if (StringUtil.isBlank(mac)) {
            mac = "";
        }
        this.cacheTemplate.set(key, mac, CommonConstants.SECONDS_OF_30_MINUTE);
    }
}
