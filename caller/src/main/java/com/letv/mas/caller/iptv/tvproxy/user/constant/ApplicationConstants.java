package com.letv.mas.caller.iptv.tvproxy.user.constant;

/**
 * 首先、application.properties中的key都必须在这里被定义。 其次、application.properties的key定义规则如下
 * url的命名为：部门.模块.功能.url.版本=value 通用参数命名为：iptv.模块.功能.param=value 比如
 * ：search.detail.url.1 最后、本类属性定义规范
 * application.properites中对应的key大写并以'_'间隔替换'.'间隔。
 * 比如：search.search.url.1对应url为SEARCH_SEARCH_URL_1
 */
public final class ApplicationConstants {

    /**
     * all base url application key
     */

    public static final String BOSS_YUANXIAN_BASEURL = "boss.yuanxian.baseurl";
    public static final String BOSS_API_ZHIFU_LETV_COM_BASEURL = "boss.api.zhifu.letv.com.baseurl";
    public static final String USERCENTER_API_SSO_LETV_COM_BASEURL = "usercenter.api.sso.letv.com.baseurl";
    public static final String ACT_REPORT_ANTI_SET_URI = "act.report.anti.set.uri";
    public static final String ACT_REPORT_ANTI_OPT_URI = "act.report.anti.opt.uri";
    public final static String LEPAY_BASEURL = "lepay.baseurl";

    /**
     * 配置参数
     */
    public static final String IPTV_WCODE_DEFAULT_VALUE = "iptv.wcode.default.value"; // 地域默认化参数
    public static final String IPTV_USER_LEPAY_ISTEST = "iptv.user.lepay.istest";
    public static final String IPTV_PLAY_ANTIREPORT_SWITCH = "iptv.play.antireport.switch";


}