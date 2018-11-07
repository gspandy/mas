package com.letv.mas.caller.iptv.tvproxy.cashier;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangli on 4/26/17.
 * 收银台常量配置：
 * 配置文件来源于本地配置文件和远程网络资源(远程网络资源会刷新，本地资源不会刷新)
 * 还包含一些静态常量
 */
public class CashierConstants {

    // 刷新二维码的间隔时间，传递给前端使用
    public static final String CASHIER_ORDER_QRCODE_DEFAULT_TIME = "cashier.order.qrcode.default.refresh.time";
    // 默认的收银台的ID
    public static final String CASHIER_CASHIER_DEFAULT_ID = "cashier.cashier.default.display.id";
    // 默认的儿童收银台的ID
    public static final String CASHIER_CHILD_CASHIER_DEFAULT_ID = "cashier.child.cashier.default.display.id";
    // 收银台推荐信息的缓存时长
    public static final String CASHIER_RECOMMEND_CACAHE_DURATION = "cashier.recommend.cache.duration";

    // The interval time of update the cashier mudule config file to the
    // constant map.
    public static final long UPDATE_CASHIER_CONFIG_INFO_INTERVAL_TIME = CommonConstants.SECONDS_OF_5_MINUTE * 1000L;
    // The last refresh time of constant map.
    public static long LAST_UPDATE_CASHIER_CONFIG_INFO_TIME = 0L;
    // The constant map of cashier config file.
    public static Map<String, String> cashierConfigInfoMap = new HashMap<String, String>();

    // 通用终端类型WEB浏览器
    public static final String COMMON_LE_TERMINA_TYPE_WEB = "141001";
    // 通用终端类型手机浏览器
    public static final String COMMON_LE_TERMINA_TYPE_MWEB = "141006";
    // 通用终端类型个人电脑
    public static final String COMMON_LE_TERMINA_TYPE_PC = "141002";
    // 通用终端类型移动手机
    public static final String COMMON_LE_TERMINA_TYPE_MOBILE = "141003";
    // 通用终端类型乐视手机
    public static final String COMMON_LE_TERMINA_TYPE_LEMOBILE = "141004";
    // 通用终端类型平板电脑
    public static final String COMMON_LE_TERMINA_TYPE_PAD = "141005";
    // 通用终端类型苹果平板
    public static final String COMMON_LE_TERMINA_TYPE_IPAD = "141008";
    // 通用终端类型苹果手机
    public static final String COMMON_LE_TERMINA_TYPE_IPHONE = "141009";
    // 通用终端类型普通电视(非乐视TV, 仅美国LE.COM启用)
    public static final String COMMON_LE_TERMINA_TYPE_TV = "141010";
    // 通用终端类型乐视电视
    public static final String COMMON_LE_TERMINA_TYPE_LETV = "141007";

}
