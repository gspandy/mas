package com.letv.mas.caller.iptv.tvproxy.apicommon.constants;

import java.util.HashMap;
import java.util.Map;

public class ConsumptionRecordConstants {
    // 订单来源 (1是vip网页，2是pc网页，3是tv，4是手机,5网络院线)
    public static final String ORDER_FROM_PC_VIP = "1";
    public static final String ORDER_FROM_PC = "2";
    public static final String ORDER_FROM_TV = "3";
    public static final String ORDER_FROM_MOBILE = "4";
    public static final String ORDER_FROM_NETWORK_Cinemas = "5";
    public static final String ORDER_FROM_TV_48 = "6";
    public static final String ORDER_FROM_CNTV = "7";
    public static final String ORDER_FROM_TV2 = "9";

    // 0待付, -1 (单片)点播过期,1(单片)点播成功,-2包月过期,2包月中,3直播支付成功
    public static final String ORDER_STATUS_NOT_PAY = "0";
    public static final String ORDER_STATUS_VIDEO_PASTDUE = "-1";
    public static final String ORDER_STATUS_VIDEO_SUCCEED = "1";
    public static final String ORDER_STATUS_PACKAGE_PASTDUE = "-2";
    public static final String ORDER_STATUS_PACKAGE_SUCCEED = "2";
    public static final String ORDER_STATUS_LIVE_SUCCEED = "3";

    private final static Map<String, String> orderFromMap = new HashMap<String, String>();
    private final static Map<String, String> orderStatusMap = new HashMap<String, String>();

    static {
        orderFromMap.put(ORDER_FROM_PC_VIP, "VIP网页");
        orderFromMap.put(ORDER_FROM_PC, "PC网页");
        orderFromMap.put(ORDER_FROM_TV, "TV");
        orderFromMap.put(ORDER_FROM_MOBILE, "手机");
        orderFromMap.put(ORDER_FROM_NETWORK_Cinemas, "网络院线");
        orderFromMap.put(ORDER_FROM_TV_48, "TV-48");
        orderFromMap.put(ORDER_FROM_CNTV, "CNTV");
        orderFromMap.put(ORDER_FROM_TV2, "TV2.0");
    }

    static {
        orderStatusMap.put(ORDER_STATUS_NOT_PAY, "未支付");
        orderStatusMap.put(ORDER_STATUS_VIDEO_PASTDUE, "(单片)点播过期");
        orderStatusMap.put(ORDER_STATUS_VIDEO_SUCCEED, "(单片)点播成功");
        orderStatusMap.put(ORDER_STATUS_PACKAGE_PASTDUE, "套餐过期");
        orderStatusMap.put(ORDER_STATUS_PACKAGE_SUCCEED, "购买成功");
        orderStatusMap.put(ORDER_STATUS_LIVE_SUCCEED, "购买成功");
    }

    public static String getOrderFromByCode(String orderFromCode) {
        if (orderFromMap.get(orderFromCode) != null) {
            return orderFromMap.get(orderFromCode);
        } else {
            return "未知";
        }
    }

    public static String getOrderStatusByCode(String orderStatusCode) {
        return orderStatusMap.get(orderStatusCode);
    }
}
