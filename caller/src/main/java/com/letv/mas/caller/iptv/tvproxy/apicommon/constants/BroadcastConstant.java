package com.letv.mas.caller.iptv.tvproxy.apicommon.constants;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;

public class BroadcastConstant {

    public static final Integer BROADCAST_LETV = CommonConstants.LETV;
    public static final Integer BROADCAST_CNTV = CommonConstants.CNTV;
    public static final Integer BROADCAST_CIBN = CommonConstants.CIBN;
    public static final Integer BROADCAST_WASU = CommonConstants.WASU;
    private static final String LETV_USERNAME = "letv";
    private static final String CIBN_USERNAME = "cibn";

    /**
     * 根据播控方，修改用户中心用户名
     * @param username
     * @param broadcastId
     * @return
     */
    public static String transToBroadcastUsername(String username, Integer broadcastId) {
        if (username != null && broadcastId != null && broadcastId == BROADCAST_CIBN
                && username.contains(LETV_USERNAME)) {
            return username.replaceAll(LETV_USERNAME, CIBN_USERNAME);
        }
        return username;

    }

    /**
     * 根据播控方，修改用户中心用户名
     * @param username
     * @param broadcastId
     * @return
     */
    public static void transToBroadcastUsername(CommonParam commonParam) {
        if (commonParam.getUsername() != null && commonParam.getBroadcastId() != null
                && commonParam.getBroadcastId() == BROADCAST_CIBN && commonParam.getUsername().contains(LETV_USERNAME)) {
            commonParam.setUsername(commonParam.getUsername().replaceAll(LETV_USERNAME, CIBN_USERNAME));
        }
    }

    /**
     * 根据播控方，还原用户中心用户名
     * @param username
     * @param broadcastId
     * @return
     */
    public static String transFromBroadcastUsername(String username, Integer broadcastId) {
        if (username != null && broadcastId != null && broadcastId == BROADCAST_CIBN
                && username.contains(CIBN_USERNAME)) {
            return username.replaceAll(CIBN_USERNAME, LETV_USERNAME);
        }

        return username;
    }

    /**
     * 根据播控方，还原用户中心用户名
     * @param commonParam
     * @return
     */
    public static void transFromBroadcastUsername(CommonParam commonParam) {
        if (commonParam.getUsername() != null && commonParam.getBroadcastId() != null
                && commonParam.getBroadcastId() == BROADCAST_CIBN && commonParam.getUsername().contains(CIBN_USERNAME)) {
            commonParam.setUsername(commonParam.getUsername().replaceAll(CIBN_USERNAME, LETV_USERNAME));
        }
    }

}
