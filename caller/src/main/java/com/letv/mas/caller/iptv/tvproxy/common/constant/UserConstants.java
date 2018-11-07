package com.letv.mas.caller.iptv.tvproxy.common.constant;

import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;

import java.util.Arrays;
import java.util.List;

public class UserConstants {

    /**
     * 会员名称显示的key,用于多语言环境处理会员显示名称
     */
    public static final String TV_VIP_TITLE = "USER.VIP.TITLE";

    /**
     * 未开通会员或会员已过期显示的key, 用于多语言环境显示名称处理
     */
    public static final String TV_NOT_VIP_TITLE = "USER.NOTVIP.TITLE";


    /**
     * get user info from type.
     */
    public static String USER_FROM_TYPE_GOLIVE = "golive";


    public static final String BALANCE_QUERY_DEFAULT_ORIGIN = "tv";

    /**
     * get user info by username url
     */
    /*public static final String USER_INFO_GET_URL = ApplicationConstants.USERCENTER_API_SSO_BASE_HOST
            + "/api/getUserByName/username/{username}/dlevel/total";*/

    /**
     * get user lepoint balance url
     */
    /*public final static String USER_LEPOINT_BALANCE_GET_URL = ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST
            + "/querylepoint/";*/

    public static String getUserInfoGetUrl(){
        String baseUrl = ApplicationUtils.get(ApplicationConstants.USERCENTER_API_SSO_LETV_COM_BASEURL);
        if(StringUtil.isNotBlank(baseUrl)){
            return baseUrl + "/api/getUserByName/username/{username}/dlevel/total";
        }
        return null;
    }

    public static String getUserLepointBalanceGetUrl(){
        String baseUrl = ApplicationUtils.get(ApplicationConstants.BOSS_API_ZHIFU_LETV_COM_BASEURL);
        if(StringUtil.isNotBlank(baseUrl)){
            return baseUrl + "/querylepoint/";
        }
        return null;
    }

    /**
     * get user info from type.
     */
    public static String USER_FAVORITE_ALBUM = "USER_FAVORITE_ALBUM";
    public static String USER_FAVORITE_COLLECT = "USER_FAVORITE_COLLECT";
    public static String USER_FAVORITE_ALBUM_COLLECT = "USER_FAVORITE_ALBUM_COLLECT";

    private static final String[] hignStreamDeviceArray = { "Letv Max3-65", "Letv X3-55", "Letv X3-65", "X4-40",
            "X4-50Pro", "X4-50", "X4-55", "X4-65" };
    public static final List<String> hignStreamDeviceList = Arrays.asList(hignStreamDeviceArray);

    public static final int USER_CHILD_LOCK_STATUS_UNSET = -1;
    public static final int USER_CHILD_LOCK_STATUS_OFF = 0;
    public static final int USER_CHILD_LOCK_STATUS_ON = 1;

    public static final int USER_CHILD_LOCK_CHECK_ACTION_TYPE_CHECK_STATUS = 1;
    public static final int USER_CHILD_LOCK_CHECK_ACTION_TYPE_VERIFY_PASSOWRD = 2;
    public static final int USER_CHILD_LOCK_CHECK_ACTION_TYPE_VERIFY_PIN = 3;

    public static final int USER_CHILD_LOCK_SET_ACTION_TYPE_CREATE = 1;
    public static final int USER_CHILD_LOCK_SET_ACTION_TYPE_RESET_WITH_PIN = 2;
    public static final int USER_CHILD_LOCK_SET_ACTION_TYPE_RESET_WITH_LOCALTOKEN = 3;

    public static final long USER_CHILD_LOCK_SET_TOKEN_EXPIRE_TIME = 5 * 60 * 1000L;


}
