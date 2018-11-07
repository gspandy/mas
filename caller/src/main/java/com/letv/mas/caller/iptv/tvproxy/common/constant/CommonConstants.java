package com.letv.mas.caller.iptv.tvproxy.common.constant;

import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;

public class CommonConstants {

    // 各个应用 的appid
    public final static String APP_PANOSEARCH = "131";// 万象搜索
    public final static String APP_PANODESK = "120";// 万象桌面
    public final static String APP_LEVIEW = "127";// 乐见桌面
    public final static String APP_TVLIVE = "102";// LIVE
    public final static String APP_CIBN = "130";// CIBN APP
    public final static String APP_LETVVIDEO = "100";// 超级影视
    public final static String APP_CHILD = "121";// 儿童

    // 各个平台的 platformId
    public final static String PLATFORM_EUI = "301"; // EUI系统
    public final static String PLATFORM_ATV = "302";// ATV 系统，美国le live在用
    public final static String PLATFORM_ANDROID = "206";// 手机 安卓系统

    // 时间
    public static final int SECONDS_OF_1_MINUTE = 60; // 一分钟的秒数 60
    public static final int SECONDS_OF_5_MINUTE = 60 * 5;// 五分钟的秒数，300
    public static final int SECONDS_OF_10_MINUTE = 60 * 10;// 十分钟的秒数，600
    public static final int SECONDS_OF_15_MINUTE = 60 * 15;// 十五分钟的秒数，900
    public static final int SECONDS_OF_30_MINUTE = 60 * 30;// 三十分钟的秒数， 1800
    public static final int SECONDS_OF_1_HOUR = 60 * 60; // 一小时的秒数，3600
    public static final int SECONDS_OF_1_DAY = 60 * 60 * 24; // 一天的秒数，86400
    public static final int SECONDS_OF_1_MONTH = 60 * 60 * 24 * 30;// 一月的秒数
    public static final int SECONDS_OF_1_YEAR = 60 * 60 * 24 * 365;// 一年的秒数

    /**
     * 支付中心 产品类型
     */
    public static final int PRODUCT_TYPE_LETV_POINT = 0;// 乐点
    public static final int PRODUCT_TYPE_SINGLE_PROD = 1;// 单片
    public static final int PRODUCT_TYPE_SERVICE_PACKAGE = 2;// 产品包

    /**
     * 各端版权代码
     */
    public static final String WEB_PLAY_PLAT_FROM = "420001";
    public static final String TV_PLAY_PLAT_FROM = "420007";// ApplicationUtils.get(ApplicationConstants.IPTV_TV_COPYRIGHT_PARAM);
    public static final String TV_PLAY_BAOFENG_PLAT_FROM = "420009";
    public static final String TV_OUT_PLAY_PLAT_FROM = "420012";

    public static final String EPG_VERSION_2P0 = "2.0";// CIBN用

    /**
     * 返给客户端的响应状态
     */
    public static final String RESPONSE_STATUS_SUCCESS = "0";// 成功
    public static final String RESPONSE_STATUS_FAILURE = "1";// 失败
    public static final String RESPONSE_MESSAGE_SUCCESS = "业务正常";// 业务正常
    public static final String RESPONSE_MESSAGE_FAILURE = "业务异常";// 业务异常

    /**
     * 获取设备信息时 向用户返回的response的key值
     */
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
    public static final String RESPONSE_KEY_BROADCAST_SWITCH = "broadcastSwitch";

    /**
     * 编码
     */
    public static final String UTF8 = "UTF-8";

    /**
     * tv版 platform值
     */
    public static final String TV_PLATFROM = "tv";

    /**
     * tv版 code
     */
    public static final String TV_PLATFROM_CODE = "420007";
    public static final String TV_PAY_CODE = "141007"; // TV自有版
    public static final String TV_BOX_PAY_CODE = "141011"; // 盒子自有版
    public static final String TV_3RD_PAY_CODE = "141010"; // 第三方市场

    /**
     * braodcastId的定义
     */
    public static final int LETV = 0;
    public static final int CNTV = 1;
    public static final int CIBN = 2;
    public static final int WASU = 3;

    public static final String LETV_NAME = "letv";
    public static final String CNTV_NAME = "cntv";
    public static final String CIBN_NAME = "cibn";
    public static final String WASU_NAME = "wasu";
    public static final String WASU_NEW_NAME = "wasutv";
    public static final String GOLIVE_NAME = "golive";

    /**
     * 通用业务编码
     */
    public static final String BIZCODE_SUBSCRIBE = "subscribe"; // 订阅
    public static final String BIZCODE_ADD_ON = "add_on"; // add_on会员

    /**
     * 终端应用编码
     */
    public static final String TERMINAL_APPLICATION_LETV = "letv"; // 大陆版
    // public static final String TERMINAL_APPLICATION_LETV_COMMON =
    // "letv_common";
    public static final String TERMINAL_APPLICATION_LESO = "letv_search"; // 乐搜
    public static final String TERMINAL_APPLICATION_LEVIDI = "levidi_gl";// levidi
    public static final String TERMINAL_APPLICATION_LETV_HK = "letv_hk";// 包括香港版
    public static final String TERMINAL_APPLICATION_LETV_US = "letv_us";// 包括美国乐视视频行货版
    public static final String TERMINAL_APPLICATION_LE = "le"; // 包括美国乐视视频Le.COM

    public final static String LEVIEW_RELEASE_VERSION = ApplicationConstants.LEVIEW_RELEASE_APP_VERSION;

    public static final String[] LANG_CODES = { LocaleConstant.Langcode.ZH_CN, LocaleConstant.Langcode.ZH_HK,
            LocaleConstant.Langcode.EN_US };

    public static final String DEFAULT_WCODE = ApplicationUtils.get(ApplicationConstants.IPTV_WCODE_DEFAULT_VALUE);
    public static final String DEFAULT_SALESAREA = ApplicationUtils
            .get(ApplicationConstants.IPTV_SALESAREA_DEFAULT_VALUE);
    public static final String DEFAULT_LANGCODE = ApplicationUtils
            .get(ApplicationConstants.IPTV_LANDCODE_DEFAULT_VALUE);

    /**
     * 图片尺寸
     */
    // 媒资图片
    public static final String PIXEL_400_225 = "400x225";
    public static final String PIXEL_300_400 = "300x400";

    // cms图片前缀
    public static final String CMS_TV_PIC = "tvPic";

    /**
     * 播控方映射关系
     */
    public enum BROADCAST_TYPE {
        CNTV(CommonConstants.CNTV, CNTV_NAME),
        WASU(CommonConstants.WASU, WASU_NAME),
        CIBN(CommonConstants.CIBN, CIBN_NAME),
        LETV(CommonConstants.LETV, LETV_NAME);
        private int value;
        private String name;

        BROADCAST_TYPE(int value, String n) {
            this.value = value;
            this.name = n;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return this.value;
        }

        public static BROADCAST_TYPE getByName(String name) {
            if (CNTV.name.equals(name)) {
                return CNTV;
            }
            if (WASU.name.equals(name)) {
                return WASU;
            }
            if (CIBN.name.equals(name)) {
                return CIBN;
            }
            return null;
        }

        public static BROADCAST_TYPE getById(int id) {
            if (CNTV.value == id) {
                return CNTV;
            }
            if (WASU.value == id) {
                return WASU;
            }
            if (CIBN.value == id) {
                return CIBN;
            }
            return null;
        }
    }

    /**
     * 播控方审核状态常量
     */
    public static final int BROADCAST_UNCHECK = 4;
    public static final int BROADCAST_WAIT_END_CHECK = 5;
    public static final int BROADCAST_FIRST_CHECKED_FAILED = 2;
    public static final int BROADCAST_WAIT_END_CHECK_FAILED = 3;
    public static final int BROADCAST_ON_LINE = 1;
    public static final int BROADCAST_OFF_LINE = 0;
    public static final int BROADCAST_TEMP_OFF_LINE = 6;

    /**
     * 添加播控审核状态常量
     */
    public enum BROADCAST_CHECK_STATUS {
        ON_LINE(BROADCAST_ON_LINE, "已上线"),
        OFF_LINE(BROADCAST_OFF_LINE, "已下线"),
        ON_LINE_ALIAS(BROADCAST_ON_LINE, "审核通过"),
        FIRST_CHECKED_FAILED(BROADCAST_FIRST_CHECKED_FAILED, "审核不过"),
        UNCHECK(BROADCAST_UNCHECK, "待审核");
        private final int value;
        private final String name_CN;

        private BROADCAST_CHECK_STATUS(int _status, String _name_CN) {
            this.value = _status;
            this.name_CN = _name_CN;
        }

        public String getName_CN() {
            return this.name_CN;
        }

        public int getValue() {
            return this.value;
        }

        public static BROADCAST_CHECK_STATUS getSTATUSByValue(int _value) {
            if (BROADCAST_FIRST_CHECKED_FAILED == _value) {
                return FIRST_CHECKED_FAILED;
            }
            if (BROADCAST_ON_LINE == _value) {
                return ON_LINE;
            }
            if (BROADCAST_OFF_LINE == _value) {
                return OFF_LINE;
            }
            if (BROADCAST_UNCHECK == _value) {
                return UNCHECK;
            }
            return UNCHECK;
        }

    }

    /**
     * 视频请求类型
     */
    public enum VIDEO_REQUEST_TYPE {
        ALL(0),
        ZHENGPIAN(1),
        QITA(2);
        private Integer v;

        private VIDEO_REQUEST_TYPE(Integer v) {
            this.v = v;
        }

        public Integer getV() {
            return this.v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

    }

    /**
     * 专辑类型
     */
    public enum ALBUM_TYPE {
        YUGAO("180002", 0),
        ZHENGPIAN("180001", 1),
        HUAXU("180003", 2),
        TEJI("180217", 3),
        ZIXUN("180004", 4),
        OTHER("-1", -1);
        private String ms_videotype;
        private Integer selftype;

        private ALBUM_TYPE(String ms, Integer se) {
            this.ms_videotype = ms;
            this.selftype = se;
        }

        public String getMs_videotype() {
            return this.ms_videotype;
        }

        public void setMs_videotype(String ms_videotype) {
            this.ms_videotype = ms_videotype;
        }

        public Integer getSelftype() {
            return this.selftype;
        }

        public void setSelftype(Integer selftype) {
            this.selftype = selftype;
        }

    }

    /**
     * 播放类型
     */
    public enum PLAY_TYPE {
        ZHENGCHANG(1),
        SHIKAN(2),
        MIANFEI_350(3),
        SHIKAN_YUANXIAN(4),
        CHARGE_STREAM(5);
        private Integer value;

        private PLAY_TYPE(Integer v) {
            this.value = v;
        }

        public Integer getValue() {
            return this.value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

    }

    // 播放返回-----------------------
    public static final int PLAY_SERVICE_OPEN = 1;// 播放服务已开通
    public static final int PLAY_SERVICE_UNOPEN = 0;// 播放服务未开通

    public static final String CHANNEL_FOCUS_URI = "/v2/recommend/getRecommendByPos";
    public static final String CHANNEL_TOPIC_FOCUS_URI = "/v2/recommend/getTopicFocus";

    public static final Integer DESC = 0;// 排序降序
    public static final Integer ASC = 1;// 升序

    /**
     * 下载平台信息
     */
    public enum DOWNLAOD_PLATFORM {
        WEB("290001", "WEB", "WEB", "WEB", "WEB"),
        PAD("290002", "PAD", "PAD", "WEB", "WEB"),
        PHONE("290003", "PHONE", "手机", "手機", "PHONE"),
        PC("290004", "PC", "PC", "PC", "PC"),
        TV("290005", "TV", "TV", "TV", "TV"),
        BAOFENG("290009", "BAOFENG", "暴风", "暴風", "BAOFENG"),
        APP_MARKET("290010", "APP_MARKET", "应用市场", "應用市場", "APPLICATION MARKET");
        private String ms_code;
        private String name_code;
        private String name_cn;
        private String name_hk;
        private String name_us;

        private DOWNLAOD_PLATFORM(String ms_code, String name_code, String name_cn, String name_hk, String name_us) {
            this.ms_code = ms_code;
            this.name_code = name_code;
            this.name_cn = name_cn;
            this.name_hk = name_hk;
            this.name_us = name_us;
        }

        public String getMs_code() {
            return this.ms_code;
        }

        public void setMs_code(String ms_code) {
            this.ms_code = ms_code;
        }

        public String getName_code() {
            return this.name_code;
        }

        public void setName_code(String name_code) {
            this.name_code = name_code;
        }

        public String getName_cn() {
            return this.name_cn;
        }

        public void setName_cn(String name_cn) {
            this.name_cn = name_cn;
        }

        public String getName_hk() {
            return this.name_hk;
        }

        public void setName_hk(String name_hk) {
            this.name_hk = name_hk;
        }

        public String getName_us() {
            return this.name_us;
        }

        public void setName_us(String name_us) {
            this.name_us = name_us;
        }

        public String getNameByLangcode(String langcode) {
            if (LocaleConstant.Langcode.ZH_CN.equalsIgnoreCase(langcode)) {
                return this.name_cn;
            }
            if (LocaleConstant.Langcode.ZH_HK.equalsIgnoreCase(langcode)) {
                return this.name_hk;
            }
            if (LocaleConstant.Langcode.EN_US.equalsIgnoreCase(langcode)) {
                return this.name_us;
            }
            return this.name_cn;
        }
    }

    /**
     * 购买类型
     */
    public enum PAY_TYPE {
        NONE(PAY_TYPE_NONE),
        VIP(PAY_TYPE_VIP),
        COUPON(PAY_TYPE_COUPON);
        private Integer type;

        private PAY_TYPE(Integer v) {
            this.type = v;
        }

        public Integer getV() {
            return this.type;
        }

        public void setV(Integer v) {
            this.type = v;
        }

    }

    public static final int PAY_TYPE_NONE = 0;
    public static final int PAY_TYPE_VIP = 1;
    public static final int PAY_TYPE_COUPON = 2;

    /**
     * 分端付费总开关
     */
    public static final boolean VIP_DISTRIBUTED_PAYING_ENBABLE = true;

    /**
     * 付费频道开关
     */
    public static final boolean VIP_CHANNEL_PAYING_ENBABLE = false;

    /**
     * 分端付费平台映射关系
     */
    public enum PAY_PLATFORM_TYPE {
        TV(2, CommonConstants.TV_PAY_CODE),
        TV_BOX(5, CommonConstants.TV_BOX_PAY_CODE),
        TV_3RD(7, CommonConstants.TV_3RD_PAY_CODE);

        private Integer devType;
        private String platform;

        PAY_PLATFORM_TYPE(Integer devType, String platform) {
            this.devType = devType;
            this.platform = platform;
        }

        public Integer getDevType() {
            return this.devType;
        }

        public void setDevType(Integer devType) {
            this.devType = devType;
        }

        public String getPlatform() {
            return this.platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public static PAY_PLATFORM_TYPE getByDevType(Integer devType) {
            if (2 == devType) {
                return TV;
            }
            if (5 == devType) {
                return TV_BOX;
            }
            if (7 == devType) {
                return TV_3RD;
            }
            return null;
        }

        public static PAY_PLATFORM_TYPE getByDevType(String devType) {
            if (null != devType) {
                return getByDevType(Integer.valueOf(devType));
            }
            return null;
        }

        public static PAY_PLATFORM_TYPE getByPlatform(String platform) {
            if (CommonConstants.TV_PAY_CODE.equals(platform)) {
                return TV;
            }
            if (CommonConstants.TV_BOX_PAY_CODE.equals(platform)) {
                return TV_BOX;
            }
            if (CommonConstants.TV_3RD_PAY_CODE.equals(platform)) {
                return TV_3RD;
            }
            return null;
        }

        public static String getAllPlatforms() {
            StringBuilder sb = new StringBuilder();

            for (PAY_PLATFORM_TYPE pay_platform_type : PAY_PLATFORM_TYPE.values()) {
                sb.append(pay_platform_type.getPlatform()).append(",");
            }

            if (sb.length() > 0) { // remove the last ","
                sb.replace(sb.length() - 1, sb.length(), "");
            }

            return sb.toString();
        }
    }

    public static final String getDefaultWcode(){
        return ApplicationUtils.get(ApplicationConstants.IPTV_WCODE_DEFAULT_VALUE);
    }
}
