package com.letv.mas.caller.iptv.tvproxy.search.constant;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;

import java.util.HashMap;
import java.util.Map;

public class SearchConstant {

    /**
     * 多语言ITV_SEARCH_CONDITION表名称
     */
    public final static String ITV_SEARCH_CONDITION_TABLE = "ITV_SEARCH_CONDITION";

    /**
     * 多语言ITV_SEARCH_CONDITION表sc_name字段名称
     */
    public final static String ITV_SEARCH_CONDITION_SC_NAME = "sc_name";

    public final static int IS_SEARCHCONDITION_ITEM = 1;// 是否搜索项

    /**
     * 搜索数据类型--1.专辑 2.视频 3.明星 4.专题 5.APP 6.直播 8.音乐专辑 9.单首音乐
     * <<<<<<< HEAD
     * 搜索数据类型--1.专辑 2.视频 3.明星 4.专题 6.直播
     * =======
     * >>>>>>> 72f52747d7ad53f306bf896d0e2d0b767b23eaaa
     */
    public final static int SEARCH_DATA_TYPE_ALBUM = 1;
    public final static int SEARCH_DATA_TYPE_VIDEO = 2;
    public final static int SEARCH_DATA_TYPE_STAR = 3;
    public final static int SEARCH_DATA_TYPE_SUBJECT = 4;
    public final static int SEARCH_DATA_TYPE_APP = 5;
    public final static int SEARCH_DATA_TYPE_LIVE = 6;
    public final static int SEARCH_DATA_TYPE_MUSICALBUM = 8;
    public final static int SEARCH_DATA_TYPE_MUSICSINGLE = 9;

    /**
     * 搜索结果视频类型--专辑、视频、专题
     */
    public final static String VIDEO_TYPE_ALBUM = "album";
    public final static String VIDEO_TYPE_VIDEO = "video";
    public final static String VIDEO_TYPE_SUBJECT = "subject";

    /**
     * 专题类型--6.横向列表 7.竖向列表
     */
    public final static String SUBJECT_TYPE_HORIZONTAL = "6";
    public final static String SUBJECT_TYPE_VERTICAL = "7";

    /**
     * 搜索结果最大吐出量
     */
    public final static int SEARCH_RESULT_MAX_SIZE = 500;

    // 错误码
    public final static String SEARCH_ERROR_CONDITION_ERROR = "SSC001"; // 搜索条件异常
    public final static String SEARCH_ERROR_RESULT_NULL = "SSC002"; // 搜索结果返回空
    public final static String SEARCH_ERROR_FIBBDENAREA = "SSC003"; // 流地址 地址过滤

    /**
     * 流服务过滤
     */
    // 需要 过滤的省份id
    public final static String[] STREAMFILTER_AREA_PROVINCEID = {};
    // 需要 过滤的地区id
    // 下期做public final static String[] STREAMFILTER_AREA_DISTINCTID = {};
    public final static String STREAMFILTER_SITE = "";// 需要 过滤的站源
    public final static String STREAMFILTER_IP = "";// IP 流服务白名单
    public final static String STREAMFILTER_MAC = "";// MAC 流服务白名单
    public final static String STREAMFILTER_AID = "";// 过滤专辑
    public final static Boolean STREAMFILTER_SWITCH = false;// 流服务总开关

    public final static String SEARCH_SEARCHRESULT_SINGER = "SEARCH.SEARCHRESULT.SINGER";

    public final static String SHOW_PANOTAG = "tv_03  tv_clvoice10  tv_levoice11  tv_levoice11  tv_super15";
    public final static String SHOW_VIDEOTAG = "tv_01  tv_02 tv_leview19  tv_06  tv_lechild12 tv_live13 tv_live14 tv_levidi16 tv_le20";

    private final static Map<String, String[]> FROM_MAPPING = new HashMap<String, String[]>();

    // 搜索数据来源from值
    public final static String FROM_PANOSEARCH = "tv_super15";// 万象搜索
    public final static String FROM_PANODESK = "tv_03";// 万象桌面
    public final static String FROM_LEVIEW = "tv_leview19";// 乐见桌面
    public final static String FROM_TVLIVE = "tv_live13";// TV LIVE
    public final static String FROM_CIBN = "tv_cibn21";// CIBN APP
    public final static String FROM_LETVVIDEO = "tv_02";// TV超级影视
    public final static String FROM_LESO = "tv_01";// 老乐搜
    public final static String FROM_CHILD = "tv_child12"; // 乐视儿童
    public final static String FROM_MOBILELIVE = "mobile_live08";// 手机live

    static {
        String[] args1 = { CommonConstants.APP_PANOSEARCH, CommonConstants.PLATFORM_EUI };// 万象客户端
        FROM_MAPPING.put(FROM_PANOSEARCH, args1);
        String[] args2 = { CommonConstants.APP_PANODESK, CommonConstants.PLATFORM_EUI };// 搜索桌面
        FROM_MAPPING.put(FROM_PANODESK, args2);
        String[] args3 = { CommonConstants.APP_LEVIEW, CommonConstants.PLATFORM_EUI };// 乐见
        FROM_MAPPING.put(FROM_LEVIEW, args3);
        String[] args4 = { CommonConstants.APP_TVLIVE, CommonConstants.PLATFORM_EUI };// live
        FROM_MAPPING.put(FROM_TVLIVE, args4);
        String[] args5 = { CommonConstants.APP_CIBN, CommonConstants.PLATFORM_EUI };// cibn
        FROM_MAPPING.put(FROM_CIBN, args5);
        String[] args6 = { CommonConstants.APP_LETVVIDEO, CommonConstants.PLATFORM_EUI };// Tv版
        FROM_MAPPING.put(FROM_LETVVIDEO, args6);
        String[] args7 = { CommonConstants.APP_CHILD, CommonConstants.PLATFORM_EUI };// 乐视儿童
        FROM_MAPPING.put(FROM_CHILD, args7);
    }

    public static String[] getAppPlatform(String from) {
        return FROM_MAPPING.get(from);
    }

    public final static String DISPLAYAPPID = "100";
    public final static String DISPLAYPLATFORMID = "301";
}
