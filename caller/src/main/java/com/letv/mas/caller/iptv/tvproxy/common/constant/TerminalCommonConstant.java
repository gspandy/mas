package com.letv.mas.caller.iptv.tvproxy.common.constant;

import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;

import java.util.*;

public class TerminalCommonConstant {

    // ==================terminal的返回值================
    // 响应返回状态
    public static final String RESPONSE_STATUS_SUCCESS = "0";// 成功
    public static final String RESPONSE_STATUS_FAILURE = "1";// 失败
    public static final String RESPONSE_MESSAGE_SUCCESS = "业务正常";// 业务正常
    public static final String RESPONSE_MESSAGE_FAILURE = "业务异常";// 业务异常

    // Response Map key（向用户返回的response的key值）
    public static final String RESPONSE_KEY_STATUS = "status";
    public static final String RESPONSE_KEY_USERNAME = "username";
    public static final String RESPONSE_KEY_TERMINALUUID = "terminalUuid";
    public static final String RESPONSE_KEY_IDENTIFYCODE = "identifyCode";
    public static final String RESPONSE_KEY_MESSAGE = "message";
    public static final String RESPONSE_KEY_VERSIONURL = "versionUrl";
    public static final String RESPONSE_KEY_VERSIONID = "versionId";
    public static final String RESPONSE_KEY_OTHER = "other";
    public static final String RESPONSE_KEY_DESCRIPTION = "description";
    public static final String RESPONSE_KEY_VERSIONNAME = "versionName";
    public static final String RESPONSE_KEY_PLAYFORMATISTS = "playFormatIsTs";
    public static final String RESPONSE_KEY_BROADCASTID = "broadcastId";
    public static final String RESPONSE_KEY_CONFIG = "config";
    public static final String RESPONSE_KEY_ROM_MINIMUM = "romMinimum";
    public static final String RESPONSE_KEY_PUBLISH_TIME = "publishTime";
    public static final String RESPONSE_KEY_CURROM_MINIMUM = "CurRomMinimum";
    public static final String RESPONSE_KEY_POJIESTATUS = "pojieVersion";
    public static final String RESPONSE_KEY_FREE_VIP = "freeVIP"; // 终端可以领取会员试用的时长
    public static final String RESPONSE_KEY_BROADCAST_STATUS = "broadcastStatus";
    public static final String RESPONSE_KEY_BROADCAST_MESSAGE = "broadcastMessage";

    // ==================白名单相关================
    public static final String WHITE_LIST_KEY = "white_list_key";// 白名单的key
    public static final String WHITE_LIST_TIME_KEY = "white_list_time_key";// 白名单生成的时间点
    public static final int WHITE_LIST_TIME = 1000 * 60 * 30;// 暂定30分钟
    public static final String LETV_UI = "LetvUI";// 乐视ui
    public static final String LETV_SEARCH = "letv_search";// 乐视ui

    // ==================审核状态================
    public static final int TERMINAL_NOT_AUDIT = 0;// 无需审核
    public static final int TERMINAL_CIBN_AUDIT = 1;// 国广审核

    // 播放验证以及升级相关
    public static final String TERMINAL_BROADCAST_PASS = "TERMINAL_BROADCAST_PASS";
    public static final String TERMINAL_BROADCAST_UNPASS = "TERMINAL_BROADCAST_UNPASS";
    public static final String TERMINAL_VERSION_HAS_NEW = "TERMINAL_VERSION_HAS_NEW";
    public static final String TERMINAL_VERSION_UNNEED_UPGRADE = "TERMINAL_VERSION_UNNEED_UPGRADE";
    public static final String TERMINAL_AUTH_PASS = "TERMINAL_AUTH_PASS";
    public static final String TERMINAL_AUTH_UNPASS = "TERMINAL_AUTH_UNPASS";

    // 升级状态相关
    public static final String STATUS_FORCE = "5";
    public static final String STATUS_RECOMMEND = "6";
    public static final String STATUS_NORMAL = "7";

    public static final Map<String, String> MSG = new HashMap<String, String>();
    static {
        MSG.put(TERMINAL_BROADCAST_PASS, "播控方验证通过");
        MSG.put(TERMINAL_BROADCAST_UNPASS, "播控方验证未通过，请重试");
        MSG.put(TERMINAL_VERSION_HAS_NEW, "亲，有新版本了哦!");
        MSG.put(TERMINAL_VERSION_UNNEED_UPGRADE, "不用升级!");
        MSG.put(TERMINAL_AUTH_PASS, "业务正常");
        MSG.put(TERMINAL_AUTH_UNPASS, "业务异常");
    }
    /** 升级开关key */
    public static final String UPDATE_SWITCH_KEY = "utpSwitch";
    /** 升级开关——打开 */
    public static final String UPDATE_SWITCH_ON = "on";
    /** 升级开关——关闭 */
    public static final String UPDATE_SWITCH_OFF = "off";
    /** 乐视商标 */
    public static final String BRAND_LETV = "letv";
    /** 乐视商标 */
    public static final String BRAND_FUZE = "fuze";
    /** 乐视商标 */
    public static final String SERIES_FUZE = "fuze";
    /** 乐视商标 */
    public static final String BS_FUZE = "fuze";
    /** 乐视应用 */
    public static final String APPLICATION_LETV = "letv";
    /** 乐视-李小璐应用 */
    public static final String APPLICATION_LIXIAOLU = "lixiaolu";
    /** 乐视-乐搜应用 */
    public static final String TERMINAL_APPLICATION_LETV_SEARCH = "letv_search";

    /**
     * 终端应用编码
     */
    public static final String TERMINAL_APPLICATION_LIXIAOLU = "lixiaolu"; // 乐视-李小璐应用
    public static final String TERMINAL_APPLICATION_LETV = "letv"; // 大陆版
    public static final String TERMINAL_APPLICATION_LETV_MOBILETV = "letv_mobileTV";
    public static final String TERMINAL_APPLICATION_MEDIA_CIBN = "media_cibn"; // 国广版
    public static final String TERMINAL_APPLICATION_LETV_COMMON = "letv_common";
    public static final String TERMINAL_APPLICATION_LESO = "letv_search"; // 乐搜
    public static final String TERMINAL_APPLICATION_LEVIDI = "levidi_gl";// levidi
    public static final String TERMINAL_APPLICATION_LETV_HK = "letv_hk";// 乐视视频香港版
    public static final String TERMINAL_APPLICATION_LETV_US = "letv_us";// 乐视视频美国版.包括美国乐视视频行货版
    public static final String TERMINAL_APPLICATION_LE = "le"; // 包括美国乐视视频Le.COM
    public final static String TERMINAL_APPLICATION_LEVIEW = "leview"; // 乐见终端型号
    public final static String TERMINAL_APPLICATION_CIBN_LEVIEW = "cibn_tvdesk"; // 国广乐见桌面
    public final static String TERMINAL_APPLICATION_WASU_LEVIEW = "tv_desktop_wasu"; // 国广乐见桌面
    public final static String TERMINAL_APPLICATION_CIBN = "media_cibn";// cibn终端型号
    public static final String TERMINAL_APPLICATION_TPSDK = "letv_live_sdk";// 直播sdk终端型号
    public static final String TERMINAL_APPLICATION_LECHILD_FISH = "lechild_fish"; // 小鱼在家合作版的
    public final static String TERMINAL_APPLICATION_LECHILD_DESK = "lekids_launcher"; // 儿童桌面终端型号
    public final static String TERMINAL_APPLICATION_LECHILD_MOBILE = "lechild_mobile"; // 儿童手机版终端型号
    public final static String TERMINAL_APPLICATION_GOLIVE = "cinema_desk"; // golive
    public final static String TERMINAL_APPLICATION_CHILD_APP = "child_cibn"; // 儿童app
    public final static String TERMINAL_APPLICATION_TVLIVE = "tvlive"; // TVLIVE
    public final static String TERMINAL_APPLICATION_SUPER_LIVE = "letv_superlive_app";// letv_superlive_app
    public final static String TERMINAL_APPLICATION_LECHILD_ALONE = "child_alone"; // 儿童独立版

    public final static Set<String> TERMINAL_APPLICATION_CMS_LE = new HashSet<String>();
    static {
        TERMINAL_APPLICATION_CMS_LE.add(TERMINAL_APPLICATION_LEVIEW);// 乐见
        TERMINAL_APPLICATION_CMS_LE.add(TERMINAL_APPLICATION_GOLIVE);// golive
        TERMINAL_APPLICATION_CMS_LE.add(TERMINAL_APPLICATION_CIBN_LEVIEW);// 国广桌面
        TERMINAL_APPLICATION_CMS_LE.add(TERMINAL_APPLICATION_WASU_LEVIEW);// 华数桌面
    }
    /**
     * 型号白名单列表
     */
    private static final String[] TV_WHITE_SERIES = { "Hi3716C", "Hi3716D", "Hi3716C_lixiaolu", "Full AOSP on godbox",
            "MBX reference board (g02ref)", "AMLOGIC8726MX", "Android TV on MStar Amber3", "msm8960", "OTT-letv",
            "AMLOGIC8726MX_C1S", "LeTVX60", "Android TV on MStar Amber3 S50", "AMLOGIC8726MX_UI_2",
            "Android TV on MStar Amber3 S40", "AMLOGIC8726MX_C1S_UI_2", "Android TV on MStar Amber3 S50_hotel",
            "LeTVX60_hotel", "Android TV on MStar Amber3 S50_hotel_C", "LeTVX60_hotel_C", "MStar Android TV",
            "AMLOGIC8726MX_NEWC1S", "LeTVX60_MAX70", "AMLOGIC8726MX_T1S_UI20", "MStar Android TV_S250F",
            "Android TV on MStar Amber3 S40_hotel_C", "MStar Android TV_S240F", "MStar Android TV_S250F_THTF",
            "AMLOGIC8726MX_NEWC1S_hongkong", "C2_C2", "letv25", "AMLOGIC8726MX_C1S_UI_2_hotel_C", "GS39",
            "MStar Android TV_S240F_hotel_C", "Android TV on MStar Amber3 MXYTV", "search_cibn", "0radixROM", "S255U",
            "Letv C1", "Letv C1S", "Letv T1S", "Letv New C1S", "LBA-010-CH", "Letv G1", "Letv S50", "Letv S40",
            "Letv X50 Air", "Letv X55 Air", "Letv S50 Air", "Letv GS39", "Letv C1", "Letv C1S", "Letv T1S",
            "Letv New C1S", "LBA-010-CH", "Letv G1", "Letv S50", "Letv S40", "Letv X50 Air", "Letv X55 Air",
            "Letv S50 Air", "Letv S40 Air", "Letv GS39", "Letv X3-65", "Letv U2", "Letv S43 Air", "Letv Max3-65",
            "Letv X3-55", "Keruo_NEWC1S", "Keruo", "Letv U1", "Letv X3-40", "Letv X3-50 UHD", "Letv X3-43",
            "Letv S40 Air L", "Letv Max4-70", "Letv X3-55 Pro", "Letv Max-120", "Letv Max3-120",
            "Letv_X910_whole-netcom", "Letv CES65", "Letv X3-50", "Letv Max4-65 Curved", "Letv_X500_default",
            "Letv X3-43S", "Letv X3-40S", "Letv X4-50", "Letv X4-50 Pro", "Letv uMax120", "Le_X507_india",
            "Letv X4-50", "Letv X4-40", "Letv X4-43", "Letv X4-49", "Letv X4-49 Pro ", "Letv X4-55",
            "Letv X4-55 Curved", "Letv X4-65", "Letv X4-65 Curved", "uMAX85", "uMAX120S", "Letv X4-50 Pro",
            "Letv max70", "SM-T800", "MSM8916 for arm64", "Letv_max70_default", "MSM8916_for_arm64_default",
            "SM-T800_default" };

    /** TV White Series update interval time */
    public static final long TERMINAL_WHITE_SERIES_UPDATE_INTERVAL = 60 * 1000;
    /** TV White Series last update time */
    public static long TERMINAL_WHITE_SERIES_LAST_UPDATE_TIME = 0;
    /** TV White Series list */
    public static List<String> TV_SERIES_WHITE_LIST = new LinkedList<String>();

    /**
     * 品牌白名单列表
     */
    private static final String[] TV_WHITE_BRANDS = { "letv", "maoxinyuan", "media", "softwinners" };

    /**
     * TV品牌白名单列表
     */
    public static final List<String> TV_BRAND_WHITE_LIST = Arrays.asList(TV_WHITE_BRANDS);
    /**
     * 下线盒子系列
     */
    private static final String[] BOX_SOLD_OUT_SERIES = { "MBX reference board (g02ref)", "Full AOSP on godbox",
            "Hi3716C_lixiaolu", "Hi3716D", "Hi3716C" };
    /**
     * 下线盒子系列列表
     */
    public static final List<String> BOX_SOLD_OUT_SERIES_LIST = Arrays.asList(BOX_SOLD_OUT_SERIES);
    public static String LIVE_UPGRADE_VERSION = "1.0.51";
    public static String LIVE_UPGRADE_MD5 = "51c7f783399c5e4176662f173c85a17e";
    public static String LIVE_UPGRADE_APK_DOWNLOAD_PATH = "http://g3.letv.cn/200/23/37/letv-itv/0/ext/common/data/html/static/android/20160520084555/com.stv_tvlive_1.0.51_20160520.apk?b=123456&platid=5&splatid=500";
    public static List<String> LIVE_CAN_UPGRADE_VERSION = new LinkedList<String>();
    public static List<String> LIVE_CANNOT_UPGRADE_APPCODE = new LinkedList<String>();
    public static boolean isUpgrade = true;
    static {
        LIVE_CAN_UPGRADE_VERSION.add("1.0.47");
        LIVE_CAN_UPGRADE_VERSION.add("1.0.48");
        LIVE_CAN_UPGRADE_VERSION.add("1.0.49");

        LIVE_CANNOT_UPGRADE_APPCODE.add("20160407");
        LIVE_CANNOT_UPGRADE_APPCODE.add("20160419");
        LIVE_CANNOT_UPGRADE_APPCODE.add("20160516");
        LIVE_CANNOT_UPGRADE_APPCODE.add("20160518");
    }

    public static final String CONF_DYNAMIC_DOMAIN = "dynamicDomain";
    public static final String CONF_STATIC_DOMAIN = "staticDomain";
    public static final String CONF_REPORT_DOMAIN = "reportDomain";
    public static final String CONF_DYNAMIC_LOOPING_IPS = "dynamicLoopingIps";
    public static final String CONF_STATIC_LOOPING_IPS = "staticLoopingIps";
    public static final String CONF_ISNEED_USER_CONFIRM = "isNeedUserConfim";
    public static final String CONF_ISNEED_LOGIN_PLAY = "isNeedLoginPlay";
    public static final String CONF_DAFAULT_PLAY_CHANNELID = "dafaultPlayChannelId";
    public static final String CONF_DAFAULT_PLAY_CATEGORYID = "dafaultPlayCategoryId";
    public static final String CONF_DEFAULT_PLAY_CHANNELTYPE = "defaultPlayChannelType";
    public static final String CONF_P1 = "p1";
    public static final String CONF_P2 = "p2";
    public static final String CONF_P3 = "p3";
    public static final String CONF_REPORTLOG_DOMAIN = "reportLogDomain";
    public static final String CONF_CONTACT_WAY = "contactWay";
    public static final String CONF_ISSETTING_QR_SHOW = "isSettingQRShow";
    public static final String CONF_CURRENT_PLAY_INTERVAL = "currentPlayInterval";
    public static final String CONF_ISCOMPOSITE_CHANNEL_LOGO = "isCompositeChannelLogo";
    /**
     * 升级接口调新升级接口服务开关
     */
    public static final String TERMINAL_UPGRADE_SWITCH_ON = "1";

    /**
     * 支持4K设备系列的静态配置文件url
     */
    public static String getSupport4kDevicesConfigFileUrl(String wcode) {
        return ApplicationConstants.I_STATIC_ITV_BASE_HOST + "/api/terminal/support4KDevices.json";
    }

    public static String getTerminalEnterURL(String wcode) {
        return ApplicationConstants.API_ITV_LETV_BASE_HOST + "/iptv/api/terminal/terminalEnter.json";
    }

    public static String getTerminalWhiteSeries(String wcode) {
        return ApplicationConstants.I_STATIC_ITV_BASE_HOST + "/api/conf/terminal/white_series.txt";
    }

    public static final String SUPPORT_4K_DEVICES_CONFIG_FILR_URL = ApplicationConstants.I_STATIC_ITV_BASE_HOST
            + "/api/terminal/support4KDevices.json";

    /**
     * new upgrade service interface url
     */
    public static final String TERMINAL_UPGRADE_SERVICE_URL = ApplicationConstants.TERMINAL_UPGRADE_BASE_HOST
            + "/mobile/app/upgrade.json";
    /**
     * 目前支持下发feature的区域
     */
    public static final String[] AREAS = { "CN", "HK", "US", "IN" };

    /**
     * 需要下发feature的应用
     */
    public static final List<String> APPLICATIONS = new LinkedList<String>();

    public static final Map<String, Integer> BROADCASTID_MAP = new HashMap<String, Integer>();

    static {
        APPLICATIONS.add("tvlive");
        APPLICATIONS.add("letv_superlive_app");
        APPLICATIONS.add("letv_search");

        BROADCASTID_MAP.put(CommonConstants.LETV_NAME, CommonConstants.LETV);
        BROADCASTID_MAP.put(CommonConstants.CNTV_NAME, CommonConstants.CNTV);
        BROADCASTID_MAP.put(CommonConstants.CIBN_NAME, CommonConstants.CIBN);
        BROADCASTID_MAP.put(CommonConstants.WASU_NAME, CommonConstants.WASU);
    }

    /**
     * 下线盒子
     * @param terminalSeries
     * @return
     */
    public static boolean isSoldOutForBox(String terminalSeries) {
        for (String box : BOX_SOLD_OUT_SERIES) {
            if (box.equalsIgnoreCase(terminalSeries)) {
                return true;
            }
        }
        return false;
    }

    public static String getMsg(String code, String lang) {
        String msg = "";
        try {
            msg = MessageUtils.getMessage(code, lang);
            if (msg == null || "".equals(msg)) {

                msg = MSG.get(code);
            }
        } catch (Exception e) {
        }
        return msg;
    }
}
