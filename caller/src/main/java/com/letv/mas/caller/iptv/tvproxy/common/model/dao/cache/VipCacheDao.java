package com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheContentConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.SubscribeInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.SubscribeInfoV2;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstantUtils;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;
import org.springframework.stereotype.Component;

import org.springframework.util.Assert;

// TODO liyunlong 会员类缓存完全可以放内存，因为量非常小
@Component
public class VipCacheDao extends BaseCacheDao {

    // TODO liyunlong 1、这个走配置接口2、应该走内存
    public String getUserAgreement(String localeStr) {
        return this.cacheTemplate.get(CacheContentConstants.VIP_USER_AGGREMENT + localeStr, String.class);
    }

    public void setUserAgreement(String localeStr, String userAggrement) {
        this.cacheTemplate.set(CacheContentConstants.VIP_USER_AGGREMENT + localeStr, userAggrement);
    }

    // TODO liyunlong 1、注释含义2、走内存
    public String getPaymentChannel(String productType, Integer subend) {
        return this.cacheTemplate.get(VipTpConstantUtils.getPaymentChannelCacheKey(productType, subend), String.class);
    }

    public void setPaymentChannel(String productType, Integer subend, String paymentChannel) {
        this.cacheTemplate.set(VipTpConstantUtils.getPaymentChannelCacheKey(productType, subend), paymentChannel);
    }

    // TODO liyunlong 这个需求是什么？注释清楚
    public String getFlagBuyMonthlyPackage(String userId) {
        String key = CacheContentConstants.VIP_CANNOT_ONEFORMONTH_KEY_PREFIX + userId;
        return this.cacheTemplate.get(key, String.class);
    }

    public void setFlagBuyMonthlyPackage(String userId) {
        String key = CacheContentConstants.VIP_CANNOT_ONEFORMONTH_KEY_PREFIX + userId;
        this.cacheTemplate.set(key, CacheContentConstants.VIP_CANNOT_ONEFORMONTH_VALUE,
                CommonConstants.SECONDS_OF_1_DAY);
    }

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

    /**
     * get user vip endTime from cache
     * @param userId
     * @param deviceKey
     * @return
     */
    public Long getUserVipSeniorEndTime(String userId, String deviceKey) {
        String key = CacheContentConstants.getUserVipEndTimeKey(userId, deviceKey);
        return this.cacheTemplate.get(key, Long.class);
    }

    // TODO liyunlong 内存
    public String getPackageOrder() {
        return this.cacheTemplate.get(CacheContentConstants.PACKAGE_ORDER_KEY, String.class);
    }

    public void setPackageOrder(String packageOrder) {
        this.cacheTemplate.set(CacheContentConstants.PACKAGE_ORDER_KEY, packageOrder);
    }

    // TODO liyunlong 这还是底层次抽象方法
    public String getValue(String key) {
        return this.cacheTemplate.get(key, String.class);
    }

    public void setValue(String key, String value) {
        this.cacheTemplate.set(key, value);
    }

    /**
     * 判断用户是否可以参加“定向弹窗”的活动
     * @param userId
     * @return
     */
    public String getDirectionalPushFlag(String userId) {
        return this.cacheTemplate.get(CacheContentConstants.DIRECTIONAL_PUSH_USER_41 + userId, String.class);
    }

    /**
     * 缓存触达位活动的收货地址
     * @param userId
     * @param position
     * @param url
     */
    public void setDeliverAddress(String userId, String position, String url) {
        this.cacheTemplate.set(position + "_" + userId, url, CommonConstants.SECONDS_OF_1_DAY);
    }

    /**
     * 读取触达位活动的收货地址
     * @param userId
     * @param position
     * @return
     */
    public String getDeliverAddress(String userId, String position) {
        return this.cacheTemplate.get(position + "_" + userId, String.class);
    }

    /**
     * 删除该位置该用户的缓存收货地址
     * @param userId
     * @param position
     * @return
     */
    public void deleteDeliverAddress(String userId, String position) {
        this.cacheTemplate.delete(position + "_" + userId);
    }

    /**
     * 缓存用户下浮层弹过标志信息
     * @param userId
     * @param mac
     * @param activityId
     */
    public void setUserBottomFlag(String userId, String mac, String activityId, String value) {
        long current = System.currentTimeMillis();
        Calendar nextDay = TimeUtil.startOfDayTomorrow();
        int duration = CommonConstants.SECONDS_OF_1_DAY;
        if (nextDay != null) {
            duration = (int) ((nextDay.getTimeInMillis() - current) / 1000);
        }
        if (duration <= 0) {
            duration = CommonConstants.SECONDS_OF_1_DAY;
        }
        String key = userId + "_" + mac + "_" + activityId;
        this.cacheTemplate.set(key, value, duration);
    }

    /**
     * 读取用户下浮层缓存标志信息
     * @param userId
     * @param mac
     * @param activityId
     * @return
     */
    public String getUserBottomFlag(String userId, String mac, String activityId) {
        String key = userId + "_" + mac + "_" + activityId;
        return this.cacheTemplate.get(key, String.class);
    }

    /**
     * 获取白条还款信息
     * @param commonParam
     * @return
     */
    public Object getPaymentInfo(CommonParam commonParam) {
        String key = "freevip_" + commonParam.getUserId();
        return this.cacheTemplate.get(key, Object.class);
    }

    /**
     * 将白条还款信息添加到缓存中
     * @param commonParam
     * @param object
     */
    public void setPaymentInfo(CommonParam commonParam, Object object) {
        String key = "freevip_" + commonParam.getUserId();
        this.cacheTemplate.set(key, object, CommonConstants.SECONDS_OF_1_DAY);
    }

    /**
     * 缓存用户会员信息,缓存时间一天
     * @param userId
     *            用户id
     * @param terminal
     *            终端,见
     * @param vipInfoMap
     *            用户会员列表
     */
    public void setVipInfoV2(String userId, Integer terminal, Map<Integer, SubscribeInfo> vipInfoMap) {
        Assert.notNull(userId, "'userId' can't null");
        String key = this.getVipInfoV2Key(userId, terminal);
        this.cacheTemplate.set(key, vipInfoMap, CacheContentConstants.CACHE_EXPIRES_ONE_DAY);
    }

    /**
     * 设置用户会员信息(boss)
     * @param userId
     *            用户id
     * @param terminal
     *            终端,见
     */
    public Map<Integer, SubscribeInfo> getVipInfoV2(String userId, Integer terminal) {
        return this.cacheTemplate.get(this.getVipInfoV2Key(userId, terminal),
                new LetvTypeReference<Map<Integer, SubscribeInfo>>() {
                });
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
     * 获取用户会员信息(lepay)
     * @param userId
     * @return
     */
    public Map<String, SubscribeInfoV2> getVipInfoV3(String userId) {
        if (StringUtil.isBlank(userId)) {
            return null;
        }

        return this.cacheTemplate.get(this.getVipInfoV2Key(userId, 0),
                new LetvTypeReference<Map<String, SubscribeInfoV2>>() {
                });
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
