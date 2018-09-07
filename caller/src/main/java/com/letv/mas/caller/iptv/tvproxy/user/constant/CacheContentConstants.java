package com.letv.mas.caller.iptv.tvproxy.user.constant;

import org.apache.commons.lang.StringUtils;

/**
 * 缓存内容静态配，用于取代RedisConstants，2016-01-11
 * @author KevinYi
 */
public class CacheContentConstants {

    /**
     * 缓存用户基本信息前缀
     */
    public static final String USER_CACHE_FOR_USER_INFO = "USERID_";

    /**
     * 会员模块，用户账户会员有效期截至时间缓存key，需拼接userid使用，取值参见VipAccountTpResponse.
     * seniorendtime说明
     */
    public static final String VIP_USER_VIP_END_TIME = "VIP_USER_VIP_END_TIME_";

    public static final String VIP_INFO_V2 = "vip_info_v2_";

    /**
     * 缓存一小时
     */
    public static final int CACHE_EXPIRES_ONE_HOUR = 60 * 60;

    /**
     * 缓存一天
     */
    public static final int CACHE_EXPIRES_ONE_DAY = CACHE_EXPIRES_ONE_HOUR * 24;


    public static final String USER_BALANCE_PREFIX = "USER_BALANCE_";

    public static final boolean IS_USER_BALANCE = true;


    /**
     * continue play video table
     */
    public static final String ACT_REPORT_ANTI_REPLAY_KEY_PREFIX = "act_report_anti_replay_";

    /**
     * 通过userid和deviceKey获取用户账户会员有效期截至时间缓存key；
     * 2015-09-09，受机卡问题影响，用户登录机卡设备上时，会员有效期可能会不同；
     * 这样设计，会导致因机卡设备数量，导致数据副本，但可以规避mac副本数量带来的影响
     * @param userid
     * @param deviceKey
     *            设备暗码
     * @return
     */
    public static final String getUserVipEndTimeKey(String userid, String deviceKey) {
        return VIP_USER_VIP_END_TIME + userid + "_" + StringUtils.trimToEmpty(deviceKey);
    }
}
