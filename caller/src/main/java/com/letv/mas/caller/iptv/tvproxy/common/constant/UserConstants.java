package com.letv.mas.caller.iptv.tvproxy.common.constant;

import com.letv.mas.caller.iptv.tvproxy.common.util.ConfigOperationUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;

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
        String baseUrl = ConfigOperationUtil.get(ApplicationConstants.USERCENTER_API_SSO_LETV_COM_BASEURL);
        if(StringUtil.isNotBlank(baseUrl)){
            return baseUrl + "/api/getUserByName/username/{username}/dlevel/total";
        }
        return null;
    }

    public static String getUserLepointBalanceGetUrl(){
        String baseUrl = ConfigOperationUtil.get(ApplicationConstants.BOSS_API_ZHIFU_LETV_COM_BASEURL);
        if(StringUtil.isNotBlank(baseUrl)){
            return baseUrl + "/querylepoint/";
        }
        return null;
    }

}
