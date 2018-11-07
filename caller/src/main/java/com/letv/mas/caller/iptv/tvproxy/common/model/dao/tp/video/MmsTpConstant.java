package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video;

/**
 * 2016-03-17新增，媒资平台相关的常量定义，如频道id等
 * @author KevinYi
 */
public final class MmsTpConstant {

    /**
     * 媒资定义的所有频道信息；可直接参考媒资后台，补充数据
     */
    public static final int MMS_CATEGARY_ALL = -1; // 全部频道
    public static final int MMS_CATEGARY_FILM = 1; // 电影
    public static final int MMS_CATEGARY_COMEDY = 10; // 搞笑
    public static final int MMS_CATEGARY_INFORMATION = 1009; // 资讯
    public static final int MMS_CATEGARY_EDUCATION = 1021; // 教育
    public static final int MMS_CATEGARY_LEXIANG = 1029; // 乐享
    public static final int MMS_CATEGARY_PANORAMA = 1035; // 全景
    public static final int MMS_CATEGARY_VARIETY = 11; // 综艺
    public static final int MMS_CATEGARY_CAR = 14; // 汽车
    public static final int MMS_CATEGARY_DFILM = 16; // 纪录片
    public static final int MMS_CATEGARY_OPEN_CLASS = 17; // 公开课
    public static final int MMS_CATEGARY_LETV_MAKE = 19; // 乐视制造
    public static final int MMS_CATEGARY_TV = 2; // 电视剧
    public static final int MMS_CATEGARY_FASHION = 20; // 风尚
    public static final int MMS_CATEGARY_TEST = 200; // 测试频道
    public static final int MMS_CATEGARY_FINANCE = 22; // 财经
    public static final int MMS_CATEGARY_TRAVEL = 23; // 旅游
    public static final int MMS_CATEGARY_ENTERTAINMENT = 3; // 娱乐
    public static final int MMS_CATEGARY_HOTSPOT = 30; // 热点
    public static final int MMS_CATEGARY_QUYI = 32; // 曲艺
    public static final int MMS_CATEGARY_OPERA = 33; // 戏曲
    public static final int MMS_CATEGARY_QINZI = 34; // 亲子
    public static final int MMS_CATEGARY_PET = 35; // 宠物
    public static final int MMS_CATEGARY_AD = 36; // 广告
    public static final int MMS_CATEGARY_HEALTH = 38; // 健康
    public static final int MMS_CATEGARY_BUSINESS = 39; // 商业
    public static final int MMS_CATEGARY_SPORT = 4; // 体育
    public static final int MMS_CATEGARY_TV_YUANXIAN = 40; // 电视院线
    public static final int MMS_CATEGARY_RINGTONE = 43; // 铃声
    public static final int MMS_CATEGARY_LEYING = 45; // 乐影
    public static final int MMS_CATEGARY_DAKATAI = 46; // 大咖台
    public static final int MMS_CATEGARY_CARTOON = 5; // 动漫
    public static final int MMS_CATEGARY_ORIGINAL = 7; // 原创
    public static final int MMS_CATEGARY_OTHER = 8; // 其他
    public static final int MMS_CATEGARY_MUSIC = 9; // 音乐
    public static final int MMS_CATEGARY_LETING = 42; // 乐听

    /**
     * 媒资定义的视频类型，正片--180001，预告片--180002，星座--181204，生肖--181205，
     * 花絮--180003，咨询--180004，其他--180005
     */
    public static final int MMS_VIDEO_TYPE_ZHENG_PIAN = 180001;
    public static final int MMS_VIDEO_TYPE_YU_GAO_PIAN = 180002;
    public static final int MMS_VIDEO_TYPE_XING_ZUO = 181204;
    public static final int MMS_VIDEO_TYPE_SHENG_XIAO = 181205;
    public static final int MMS_VIDEO_TYPE_HUA_XU = 180003;
    public static final int MMS_VIDEO_TYPE_ZI_XUN = 180004;
    public static final int MMS_VIDEO_TYPE_QI_TA = 180005;

    /**
     * 媒资专辑动态图字段名称
     */
    public static final String MMS_ALBUM_DYNAMIC_GRAPH_FIELD_ANME = "dynamicGraph";

    // 字幕类型
    /** Chinese Simplified，简体 */
    public static final String VIDEO_SUBTITLE_TYPE_1000 = "1000";
    /** Traditional Chinese，繁体 */
    public static final String VIDEO_SUBTITLE_TYPE_1001 = "1001";
    /** English，英语 */
    public static final String VIDEO_SUBTITLE_TYPE_1002 = "1002";
    /** Simplified Chinese and English， 简英组合 */
    public static final String VIDEO_SUBTITLE_TYPE_1003 = "1003";
    /** Traditional Chinese and English，繁英组合 */
    public static final String VIDEO_SUBTITLE_TYPE_1004 = "1004";

    // 音质码率(atype)
    /** Bass, aac low bit rate */
    public static final String VIDEO_AUDIO_TYPE_1000 = "1000";
    /** Alto, aac code rate */
    public static final String VIDEO_AUDIO_TYPE_1001 = "1001";
    /** High-1, acc high rate */
    public static final String VIDEO_AUDIO_TYPE_1002 = "1002";
    /** High-2, Dolby high rate */
    public static final String VIDEO_AUDIO_TYPE_1003 = "1003";
    /** High-3, dts high rate */
    public static final String VIDEO_AUDIO_TYPE_1004 = "1004";

    // 音频语言（lang）
    /** Mandarin */
    public static final String VIDEO_LANG_TYPE_1000 = "1000";// Mandarin
    /** Cantonese */
    public static final String VIDEO_LANG_TYPE_1001 = "1001";// Cantonese
    /** English */
    public static final String VIDEO_LANG_TYPE_1002 = "1002";// English
    /** Korean */
    public static final String VIDEO_LANG_TYPE_1003 = "1003";// Korean
    /** Japanese */
    public static final String VIDEO_LANG_TYPE_1004 = "1004";// Japanese
    /** Russian */
    public static final String VIDEO_LANG_TYPE_1005 = "1005";// Russian
    /** French */
    public static final String VIDEO_LANG_TYPE_1006 = "1006";// French
    /** German */
    public static final String VIDEO_LANG_TYPE_1007 = "1007";// German
    /** Italian language */
    public static final String VIDEO_LANG_TYPE_1008 = "1008";// Italian language
    /** Spanish */
    public static final String VIDEO_LANG_TYPE_1009 = "1009";// Spanish
    /** Other */
    public static final String VIDEO_LANG_TYPE_1010 = "1010";// Other
}
