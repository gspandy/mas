package com.letv.mas.caller.iptv.tvproxy.common.constant;

import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;

import java.util.*;

/**
 * 码流相关属性
 * @author Administrator
 */
public class LetvStreamCommonConstants {
    public static final String VIDEO_TYPE_FLV_PREFIX = "flv_";
    public static final String VIDEO_TYPE_MP4_PREFIX = "mp4_";
    public static final String CODE_NAME_180 = "180";
    public static final String CODE_NAME_350 = "350";
    public static final String CODE_NAME_LETV_LEADING_180 = "180"; // 领先版使用的180急速码流，在TV版不使用
    public static final String CODE_NAME_1000 = "1000";
    public static final String CODE_NAME_800 = "800";
    public static final String CODE_NAME_1300 = "1300";
    public static final String CODE_NAME_720p = "720p";
    public static final String CODE_NAME_1080p = "1080p";
    public static final String CODE_NAME_1080p3m = "1080p3m";
    public static final String CODE_NAME_1080p6m = "1080p6m";
    public static final String CODE_NAME_3d1080p = "3d1080p";
    public static final String CODE_NAME_3d720p = "3d720p";
    public static final String CODE_NAME_3d1080p6M = "3d1080p6m";
    // 杜比新增加码流--start
    public static final String CODE_NAME_DOLBY_720p = "720p_db";
    public static final String CODE_NAME_DOLBY_1300 = "1300_db";
    public static final String CODE_NAME_DOLBY_1000 = "800_db";
    public static final String CODE_NAME_DOLBY_1080p = "1080p6m_db";
    public static final String CODE_NAME_DOLBY_1080p_ATMOS = "1080p6m_db_atmos";// 杜比全景声
    public static final String CODE_NAME_DOLBY_VISION_4K = "4k_dv"; // 杜比视界
    public static final String CODE_NAME_LETV_LEADING_2K = "2k"; // 领先版使用的2K码流，在TV版不使用
    public static final String CODE_NAME_LETV_LEADING_2K_H265 = "2k_h265";// 2k
    public static final String CODE_NAME_4K = "4k";// 4k
    public static final String CODE_NAME_3D = "3d";// 3d
    public static final String CODE_NAME_DB = "db";// db
    public static final String CODE_NAME_360_180 = "180_360";
    public static final String CODE_NAME_360_350 = "350_360";
    public static final String CODE_NAME_360_800 = "800_360";
    public static final String CODE_NAME_360_1300 = "1300_360";
    public static final String CODE_NAME_360_720p = "720p_360";
    public static final String CODE_NAME_360_1080p = "1080p_360";
    public static final String CODE_NAME_360_2K = "2k_360";
    public static final String CODE_NAME_360_4K = "4k_360";

    // 码流顺序，码流顺序：全景》4k》杜比》3D》普通
    public static final String ALL_STREAMS = CODE_NAME_DOLBY_VISION_4K + "#" + CODE_NAME_DOLBY_1080p_ATMOS + "#"
            + CODE_NAME_360_4K + "#" + CODE_NAME_360_1080p + "#" + CODE_NAME_360_720p + "#" + CODE_NAME_360_1300 + "#"
            + CODE_NAME_360_800 + "#" + CODE_NAME_360_350 + "#" + CODE_NAME_4K + "#" + CODE_NAME_DOLBY_1080p + "#"
            + CODE_NAME_DOLBY_720p + "#" + CODE_NAME_DOLBY_1300 + "#" + CODE_NAME_DOLBY_1000 + "#"
            + CODE_NAME_3d1080p6M + "#" + CODE_NAME_3d1080p + "#" + CODE_NAME_3d720p + "#" + CODE_NAME_1080p6m + "#"
            + CODE_NAME_1080p + "#" + CODE_NAME_720p + "#" + CODE_NAME_1300 + "#" + CODE_NAME_800 + "#" + CODE_NAME_350;

    // 码流顺序备份
    public static final String ALL_STREAMS_BAK = CODE_NAME_DOLBY_1080p_ATMOS + "#" + CODE_NAME_360_4K + "#"
            + CODE_NAME_360_1080p + "#" + CODE_NAME_360_720p + "#" + CODE_NAME_360_1300 + "#" + CODE_NAME_360_800 + "#"
            + CODE_NAME_360_350 + "#" + CODE_NAME_3d1080p6M + "#" + CODE_NAME_3d1080p + "#" + CODE_NAME_3d720p + "#"
            + CODE_NAME_DOLBY_1080p + "#" + CODE_NAME_DOLBY_720p + "#" + CODE_NAME_DOLBY_1300 + "#"
            + CODE_NAME_DOLBY_1000 + "#" + CODE_NAME_4K + "#" + CODE_NAME_1080p6m + "#" + CODE_NAME_1080p + "#"
            + CODE_NAME_720p + "#" + CODE_NAME_1300 + "#" + CODE_NAME_800 + "#" + CODE_NAME_350;

    public static final String ALL_STREAMS_US = CODE_NAME_3d1080p6M + "#" + CODE_NAME_3d1080p + "#" + CODE_NAME_3d720p
            + "#" + CODE_NAME_DOLBY_1080p + "#" + CODE_NAME_DOLBY_720p + "#" + CODE_NAME_DOLBY_1300 + "#"
            + CODE_NAME_DOLBY_1000 + "#" + CODE_NAME_4K + "#" + CODE_NAME_1080p6m + "#" + CODE_NAME_1080p + "#"
            + CODE_NAME_720p + "#" + CODE_NAME_1300 + "#" + CODE_NAME_1000 + "#" + CODE_NAME_350;
    // 通用版码流和 线上一致 同ALL_STREAMS
    public static final String LETV_COMMON_STREAMS = ALL_STREAMS;

    /**
     * 直播投屏码流过滤，按照720p-1300-1000-800-350依次选择
     */
    public static final String LIVE_TOUPING_STREAM_FILTERS = CODE_NAME_720p + "#" + CODE_NAME_1300 + "#"
            + CODE_NAME_1000 + "#" + CODE_NAME_800 + "#" + CODE_NAME_350;

    public static final Map<String, String> STREAM_CODE_KBPS_MAP = new HashMap<String, String>();// 码流下载速度
    public static final Map<String, String> STREAM_CODE_IFCHARGE_MAP = new HashMap<String, String>();// 码流是否收费
    public static final Map<String, String> STREAM_CODE_IFCHARGE_YUANXIAN_MAP = new HashMap<String, String>();// 院线码流是否收费
    public static final Map<String, String> STREAM_CODE_DOWNLOAD_IFCHARGE_MAP = new HashMap<String, String>();// 码流是否下载收费
    private static final Map<String, String> STREAM_CODE_IFCHARGE_BOX_MAP = new HashMap<String, String>();// 盒子码流是否收费(按以前代码的逻辑
                                                                                                          // 盒子1000也是收费的,所以分别对待)
    public static final Map<String, String> STREAM_CODE_ONLYDOWN = new HashMap<String, String>();// 是否只能下载不能播放
    public static final HashMap<String, String> STREAM_CODE_CANDOWN = new HashMap<String, String>();// 超级电视判断逻辑(播放和下载都用6M的,这样可以保证边播边放同步)
                                                                                                    // 否可以下载
    public static final HashMap<String, String> STREAM_CODE_CANDOWN_V1 = new HashMap<String, String>();// 除超级电视以外的判断逻辑
                                                                                                       // 是否可以下载
    public static final HashMap<String, String> STREAM_CODE_CANPLAY = new HashMap<String, String>();// 是否可以播放

    public static final Map<String, String> STREAM_CODE_FILTER_MAP = new HashMap<String, String>();// 入库码流过滤

    /**
     * 入库码流过滤
     */
    public static final Map<String, String> STREAM_CODE_SAVE_FILTER_MAP = new HashMap<String, String>();

    public static final Map<String, Integer> STREAM_CODE_SORT_VSLUE = new HashMap<String, Integer>();// 码流排序值
    public static final Map<String, String> STREAM_CODE_NAME_MAP = new HashMap<String, String>();// 码流对应的中文名称
    public static final Map<String, String> STREAM_CODE_TIP_MAP = new HashMap<String, String>();// 码流提示文案
    public static final Map<String, String> LIVE_STREAM_CODE_TIP_MAP = new HashMap<String, String>();// 码流提示文案
    public static final Map<String, String> LIVE_STREAM_CODE_NAME_MAP = new HashMap<String, String>();// 码流对应的中文名称

    private static final Map<String, String> X40_STREAM_CODE_NAME_MAP = new HashMap<String, String>();// 码流对应的中文名称

    public static final Map<String, String> SEARCH_STREAM_CODE_MAP = new HashMap<String, String>();// 搜索码流对应id

    public static final String DEFAULT_STREAM_US = CODE_NAME_800 + "#" + CODE_NAME_350;
    public static final String DEFAULT_STREAM_FOLLOWING_ALBUM_720P = CODE_NAME_720p + "#" + CODE_NAME_1300 + "#"
            + CODE_NAME_800;// 跟播剧720p播放默认码流
    public static final String DEFAULT_STREAM_3D = CODE_NAME_3d720p + "#" + CODE_NAME_1080p6m;
    public static final String DEFAULT_STREAM_XBMC = CODE_NAME_350 + "#" + CODE_NAME_800 + "#" + CODE_NAME_1300 + "#"
            + CODE_NAME_720p + "#" + CODE_NAME_1080p6m;
    public static final String DEFAULT_STREAM_LXL = CODE_NAME_1080p6m + "#" + CODE_NAME_720p + "#" + CODE_NAME_1300
            + "#" + CODE_NAME_800 + "#" + CODE_NAME_350;
    public static final String DEFAULT_STREAM_T2_LIVE_DB = CODE_NAME_1080p6m + "#" + CODE_NAME_720p + "#"
            + CODE_NAME_1300 + "#" + CODE_NAME_800 + "#" + CODE_NAME_350;

    /** 客户端不传码流时的降码流列表 */
    public static final String DEFAULT_STREAM = CODE_NAME_720p + "#" + CODE_NAME_1300 + "#" + CODE_NAME_800 + "#"
            + CODE_NAME_350;

    /** 投屏播放时不支持3d码流使用的默认视频列表和降码流列表 */
    public static final String PLAY_SORT_STREAM_FOR_TOUPING = CODE_NAME_1080p6m + "#" + CODE_NAME_720p + "#"
            + CODE_NAME_1300 + "#" + CODE_NAME_1000 + "#" + CODE_NAME_800 + "#" + CODE_NAME_350;

    /** 正常播放时不支持3d码流的降码流列表 */
    public static final String LETV_COMMON_USER_SETTING_PLAY_STREAM = CODE_NAME_1080p6m + "#" + CODE_NAME_720p + "#"
            + CODE_NAME_1300 + "#" + CODE_NAME_800 + "#" + CODE_NAME_350;

    /** 投屏播放请求的码流视频不支持或设备不支持的降码流列表 */
    public static final String USER_SETTING_PLAY_STREAM = CODE_NAME_4K + "#" + CODE_NAME_1080p6m + "#" + CODE_NAME_720p
            + "#" + CODE_NAME_1300 + "#" + CODE_NAME_800 + "#" + CODE_NAME_350;
    /**
     * 为le单独建的，比上面多了CODE_NAME_1080p3m
     */
    public static final String USER_SETTING_PLAY_STREAM_LE = CODE_NAME_4K + "#" + CODE_NAME_1080p6m + "#"
            + CODE_NAME_1080p3m + "#" + CODE_NAME_720p + "#" + CODE_NAME_1300 + "#" + CODE_NAME_800 + "#"
            + CODE_NAME_350;

    /** 正常播放请求的码流视频不支持或设备不支持的降码流列表 */
    public static final String PLAY_SORT_STREAM_T2 = CODE_NAME_DOLBY_1080p_ATMOS + "#" + CODE_NAME_3d1080p6M + "#"
            + CODE_NAME_3d720p + "#" + CODE_NAME_DOLBY_1080p + "#" + CODE_NAME_DOLBY_720p + "#" + CODE_NAME_DOLBY_1300
            + "#" + CODE_NAME_DOLBY_1000 + "#" + CODE_NAME_4K + "#" + CODE_NAME_1080p6m + "#" + CODE_NAME_1080p3m + "#"
            + CODE_NAME_720p + "#" + CODE_NAME_1300 + "#" + CODE_NAME_800 + "#" + CODE_NAME_350;
    public static final String PLAY_SORT_STREAM_T2_US = CODE_NAME_DOLBY_1080p_ATMOS + "#" + CODE_NAME_3d1080p6M + "#"
            + CODE_NAME_3d720p + "#" + CODE_NAME_DOLBY_1080p + "#" + CODE_NAME_DOLBY_720p + "#" + CODE_NAME_DOLBY_1300
            + "#" + CODE_NAME_DOLBY_1000 + "#" + CODE_NAME_4K + "#" + CODE_NAME_1080p6m + "#" + CODE_NAME_720p + "#"
            + CODE_NAME_1300 + "#" + CODE_NAME_800 + "#" + CODE_NAME_350;
    // 包含全景码流的降码流列表
    public static final String PLAY_SORT_STREAM_T3 = CODE_NAME_DOLBY_1080p_ATMOS + "#" + CODE_NAME_360_1080p + "#"
            + CODE_NAME_360_720p + "#" + CODE_NAME_360_1300 + "#" + CODE_NAME_360_800 + "#" + CODE_NAME_360_350 + "#"
            + CODE_NAME_3d1080p6M + "#" + CODE_NAME_3d720p + "#" + CODE_NAME_DOLBY_1080p + "#" + CODE_NAME_DOLBY_720p
            + "#" + CODE_NAME_DOLBY_1300 + "#" + CODE_NAME_DOLBY_1000 + "#" + CODE_NAME_4K + "#" + CODE_NAME_1080p6m
            + "#" + CODE_NAME_1080p3m + "#" + CODE_NAME_720p + "#" + CODE_NAME_1300 + "#" + CODE_NAME_800 + "#"
            + CODE_NAME_350;

    public static final String PLAY_SORT_STREAM_T1 = CODE_NAME_3d1080p6M + "#" + CODE_NAME_3d720p + "#"
            + CODE_NAME_1080p6m + "#" + CODE_NAME_720p + "#" + CODE_NAME_1300 + "#" + CODE_NAME_800 + "#"
            + CODE_NAME_350;
    public static final String DOLBY_PLAY_SORT_STREAM_T1 = CODE_NAME_DOLBY_1080p_ATMOS + "#" + CODE_NAME_DOLBY_1080p
            + "#" + CODE_NAME_DOLBY_720p + "#" + CODE_NAME_DOLBY_1300 + "#" + CODE_NAME_DOLBY_1000;

    /**
     * 3d1080p 优先 mp4_3d1080p6m --> mp4_3d1080p --> flv_3d1080p6m -->
     * flv_3d1080p
     * 1080p 先找 mp4_1080p3m --> mp4_1080p6m --> flv_1080p3m --> flv_1080p6m
     * 3d720p 优先
     * 4K码流 优先 mp4_4k_m_265(135) --> mp4_4k(54)
     */
    public static final Integer[] PLAY_REDUCED_1080P = { 80, 52, 53, 35, 20 };
    public static final Integer[] PLAY_REDUCED_3D_1080P = { 112, 114, 32, 34, 31 };
    public static final Integer[] PLAY_REDUCED_3D_720P = { 110, 30, 29 };
    public static final Integer[] PLAY_REDUCED_4K = { 135, 86, 54 };
    public static final Map<String, Integer[]> PLAY_REDUCED_MAP = new HashMap<String, Integer[]>();

    // TODO
    public static final String VTYPE_COMPATIBLE_350 = "21,1";
    public static final String VTYPE_COMPATIBLE_800 = "13,16";
    /**
     * 没有1000的码流
     */
    // public static final String[] vtype_compatible_1000 = {};
    public static final String VTYPE_COMPATIBLE_1300 = "22,17";
    public static final String VTYPE_COMPATIBLE_720P = "51,18";
    public static final String VTYPE_COMPATIBLE_800_DB = "23";
    public static final String VTYPE_COMPATIBLE_1300_DB = "24";
    public static final String VTYPE_COMPATIBLE_720P_DB = "25";
    public static final String VTYPE_COMPATIBLE_1080P_DB = "26";
    public static final String VTYPE_COMPATIBLE_1080P_DB_ATMOS = "184";
    public static final String VTYPE_COMPATIBLE_1080P = "52,53,35,20";
    public static final String VTYPE_COMPATIBLE_3D_1080P = "32,34,31";
    public static final String VTYPE_COMPATIBLE_3D_720P = "30,29";
    public static final String VTYPE_COMPATIBLE_4K = "54";
    public static final String VTYPE_COMPATIBLE_360_180 = "160";
    public static final String VTYPE_COMPATIBLE_360_350 = "161";
    public static final String VTYPE_COMPATIBLE_360_800 = "162";
    public static final String VTYPE_COMPATIBLE_360_1300 = "163";
    public static final String VTYPE_COMPATIBLE_360_720p = "164";
    public static final String VTYPE_COMPATIBLE_360_1080p = "165";
    public static final String VTYPE_COMPATIBLE_360_2K = "166";
    public static final String VTYPE_COMPATIBLE_360_4K = "167";
    public static final Map<String, String> VTYPE_REDUCED_MAP = new HashMap<String, String>();

    // TODO
    public static final String H265_VTYPE_COMPATIBLE_350 = "21,1";
    public static final String H265_VTYPE_COMPATIBLE_800 = "13,16";
    /**
     * 没有1000的码流
     */
    // public static final String[] vtype_compatible_1000 = {};
    public static final String H265_VTYPE_COMPATIBLE_1300 = "22,17";
    public static final String H265_VTYPE_COMPATIBLE_720P = "51,18";
    public static final String H265_VTYPE_COMPATIBLE_800_DB = "23";
    public static final String H265_VTYPE_COMPATIBLE_1300_DB = "24";
    public static final String H265_VTYPE_COMPATIBLE_720P_DB = "25";
    public static final String H265_VTYPE_COMPATIBLE_1080P_DB = "26";
    public static final String H265_VTYPE_COMPATIBLE_1080P_DB_ATMOS = "184";
    public static final String H265_VTYPE_COMPATIBLE_1080P = "52,53,35,20";
    public static final String H265_VTYPE_COMPATIBLE_3D_1080P = "32,34,31";
    public static final String H265_VTYPE_COMPATIBLE_3D_720P = "30,29";
    public static final String H265_VTYPE_COMPATIBLE_4K = "135,54";
    public static final String H265_VTYPE_COMPATIBLE_360_350 = "161";
    public static final String H265_VTYPE_COMPATIBLE_360_800 = "168,162";
    public static final String H265_VTYPE_COMPATIBLE_360_1300 = "169,163";
    public static final String H265_VTYPE_COMPATIBLE_360_720P = "170,164";
    public static final String H265_VTYPE_COMPATIBLE_360_1080P = "171,165";
    public static final String H265_VTYPE_COMPATIBLE_360_4K = "173,167";
    public static final Map<String, String> SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP = new HashMap<String, String>();

    /**
     * 下载---------------码流向下兼容顺序
     */
    public static final String DOWNLOAD_SORT_STREAM_T2 = CODE_NAME_3d1080p + "#" + CODE_NAME_3d720p + "#"
            + CODE_NAME_DOLBY_1080p + "#" + CODE_NAME_DOLBY_720p + "#" + CODE_NAME_DOLBY_1300 + "#"
            + CODE_NAME_DOLBY_1000 + "#" + CODE_NAME_1080p + "#" + CODE_NAME_720p + "#" + CODE_NAME_1300 + "#"
            + CODE_NAME_800 + "#" + CODE_NAME_350;

    /**
     * 用户设置------------码流顺序
     */
    public static final String USER_SETTING_LIVE_STREAM = CODE_NAME_720p + "#" + CODE_NAME_1300 + "#" + CODE_NAME_800;

    /**
     * 详情页 ----------码流列表顺序
     */
    public static final String SORT_STREAM_TV = CODE_NAME_800 + "#" + CODE_NAME_350 + "#" + CODE_NAME_720p + "#"
            + CODE_NAME_1300 + "#" + CODE_NAME_3d720p + "#" + CODE_NAME_1080p + "#" + CODE_NAME_1080p6m + "#"
            + CODE_NAME_3d1080p + "#" + CODE_NAME_3d1080p6M;
    public static final String SORT_STREAM_C1 = CODE_NAME_350 + "#" + CODE_NAME_800 + "#" + CODE_NAME_1300 + "#"
            + CODE_NAME_720p + "#" + CODE_NAME_3d720p + "#" + CODE_NAME_1080p + "#" + CODE_NAME_1080p6m + "#"
            + CODE_NAME_3d1080p + "#" + CODE_NAME_3d1080p6M + "#" + CODE_NAME_DOLBY_1000 + "#" + CODE_NAME_DOLBY_1300
            + "#" + CODE_NAME_DOLBY_720p + "#" + CODE_NAME_DOLBY_1080p;// Tv2.0计划在使用，C1在使用
    public static final String SORT_STREAM_T1 = CODE_NAME_720p + "#" + CODE_NAME_1300 + "#" + CODE_NAME_800 + "#"
            + CODE_NAME_350 + "#" + CODE_NAME_3d720p + "#" + CODE_NAME_1080p + "#" + CODE_NAME_1080p6m + "#"
            + CODE_NAME_3d1080p + "#" + CODE_NAME_3d1080p6M;
    public static final String SORT_STREAM_T2_3D = CODE_NAME_3d1080p6M + "#" + CODE_NAME_3d1080p + "#"
            + CODE_NAME_3d720p;
    public static final String SORT_STREAM_T2_DB = CODE_NAME_DOLBY_1080p + "#" + CODE_NAME_DOLBY_720p + "#"
            + CODE_NAME_DOLBY_1300 + "#" + CODE_NAME_DOLBY_1000;
    public static final String SORT_STREAM_T2_COMMON = CODE_NAME_4K + "#" + CODE_NAME_1080p6m + "#" + CODE_NAME_1080p
            + "#" + CODE_NAME_720p + "#" + CODE_NAME_1300 + "#" + CODE_NAME_800 + "#" + CODE_NAME_350;

    /**
     * 详情页 ----------选中码流顺序
     */
    public static final String SORT_SELECTED_STREAM_T2_3D = CODE_NAME_3d720p + "#" + CODE_NAME_3d1080p + "#"
            + CODE_NAME_3d1080p6M;
    public static final String SORT_SELECTED_STREAM_T2_DB = CODE_NAME_DOLBY_720p + "#" + CODE_NAME_DOLBY_1300 + "#"
            + CODE_NAME_DOLBY_1000 + "#" + CODE_NAME_DOLBY_1080p;
    public static final String SORT_SELECTED_STREAM_T2_1080P = CODE_NAME_1080p + "#" + CODE_NAME_1080p6m + "#"
            + CODE_NAME_720p + "#" + CODE_NAME_1300 + "#" + CODE_NAME_800 + "#" + CODE_NAME_350;
    public static final String SORT_SELECTED_STREAM_T2_COMMON = CODE_NAME_4K + "#" + CODE_NAME_720p + "#"
            + CODE_NAME_1300 + "#" + CODE_NAME_800 + "#" + CODE_NAME_350 + "#" + CODE_NAME_1080p + "#"
            + CODE_NAME_1080p6m;

    public static final String SORT_SELECTED_STREAM_C1 = CODE_NAME_720p + "#" + CODE_NAME_1300 + "#" + CODE_NAME_800
            + "#" + CODE_NAME_350 + "#" + CODE_NAME_3d720p + "#" + CODE_NAME_1080p + "#" + CODE_NAME_1080p6m + "#"
            + CODE_NAME_3d1080p + "#" + CODE_NAME_3d1080p6M;// C1设置选中默认码流的顺序
    public static final String SORT_SELECTED_STREAM_C1_3D = CODE_NAME_3d720p + "#" + CODE_NAME_3d1080p + "#"
            + CODE_NAME_3d1080p6M;
    public static final String SORT_SELECTED_STREAM_C1_DB = CODE_NAME_DOLBY_720p + "#" + CODE_NAME_DOLBY_1300 + "#"
            + CODE_NAME_DOLBY_1000 + "#" + CODE_NAME_DOLBY_1080p;
    public static final String SORT_SELECTED_STREAM_C1_1080P = CODE_NAME_1080p + "#" + CODE_NAME_1080p6m + "#"
            + CODE_NAME_720p + "#" + CODE_NAME_1300 + "#" + CODE_NAME_800 + "#" + CODE_NAME_350;

    public static final Integer[] CHARGE_CATEGORY = { 4, 5, 6, 164, 111, 78, 66 };// 收费频道
                                                                                  // 电影（包含3D、1080P、杜比等）、电视剧、动漫、乐视制造、纪录片,综艺,音乐

    // CODE_NAME_360_350, CODE_NAME_360_800, CODE_NAME_360_1300,
    // CODE_NAME_360_720p, CODE_NAME_360_1080p, CODE_NAME_360_4K,
    public static final String[] CHARGE_STREAM = { CODE_NAME_DOLBY_VISION_4K, CODE_NAME_DOLBY_1080p_ATMOS,
            CODE_NAME_3d1080p6M, CODE_NAME_3d720p, CODE_NAME_DOLBY_1080p, CODE_NAME_DOLBY_720p, CODE_NAME_DOLBY_1300,
            CODE_NAME_DOLBY_1000, CODE_NAME_4K, CODE_NAME_1080p6m, CODE_NAME_1080p3m, CODE_NAME_1080p, CODE_NAME_720p,
            CODE_NAME_1300, CODE_NAME_800 };

    public static final String[] THIRD_PARTY_CHARGE_STREAM = { CODE_NAME_DOLBY_1080p_ATMOS, CODE_NAME_3d1080p6M,
            CODE_NAME_3d720p, CODE_NAME_DOLBY_1080p, CODE_NAME_DOLBY_720p, CODE_NAME_4K, CODE_NAME_1080p6m,
            CODE_NAME_1080p3m, CODE_NAME_1080p, CODE_NAME_720p };

    public static final String[] US_CHARGE_STREAM = { CODE_NAME_3d1080p6M, CODE_NAME_3d720p, CODE_NAME_DOLBY_1080p,
            CODE_NAME_DOLBY_720p, CODE_NAME_DOLBY_1300, CODE_NAME_DOLBY_1000, CODE_NAME_4K, CODE_NAME_1080p6m,
            CODE_NAME_1080p3m, CODE_NAME_1080p, CODE_NAME_720p, CODE_NAME_1300, CODE_NAME_1000, CODE_NAME_800 };

    public static final String[] YUANXIAN_CHARGE_STREAM = { CODE_NAME_3d1080p6M, CODE_NAME_3d720p,
            CODE_NAME_DOLBY_1080p, CODE_NAME_DOLBY_720p, CODE_NAME_DOLBY_1300, CODE_NAME_DOLBY_1000, CODE_NAME_4K,
            CODE_NAME_1080p6m, CODE_NAME_1080p3m, CODE_NAME_1080p, CODE_NAME_720p, CODE_NAME_1300, CODE_NAME_800,
            CODE_NAME_350 };

    public static final Set<Integer> CHARGE_CATEGORY_SET = new HashSet<Integer>();
    public static final Set<String> CHARGE_STREAM_SET = new HashSet<String>();
    public static final Set<String> THIRD_PARTY_CHARGE_STREAM_SET = new HashSet<String>();
    public static final Set<String> CHARGE_STREAM_SET_US = new HashSet<String>();// 美国版付费码流列表
    public static final Set<String> YUANXIAN_CHARGE_STREAM_SET = new HashSet<String>();
    public static final Map<Integer, String> CODE_STREAM_MAP = new HashMap<Integer, String>();
    public final static Map<String, Integer> MARLIN_STREAM_CODE_MAP = new HashMap<String, Integer>();

    static {
        STREAM_CODE_NAME_MAP.put(CODE_NAME_350, "VIDEO.STREAM.350");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_800, "VIDEO.STREAM.800");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_1000, "VIDEO.STREAM.1000");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_1300, "VIDEO.STREAM.1300");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_720p, "VIDEO.STREAM.720P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_1080p, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_1080p3m, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_1080p6m, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_3d1080p, "3D1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_3d720p, "VIDEO.STREAM.3D.720P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_3d1080p6M, "3D1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_720p, "VIDEO.STREAM.DOLBY.720P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_1300, "VIDEO.STREAM.DOLBY.1300");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_1000, "VIDEO.STREAM.DOLBY.1000");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_1080p, "VIDEO.STREAM.DOLBY.1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_1080p_ATMOS, "VIDEO.STREAM.DOLBY.1080P.ATMOS");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_VISION_4K, "VIDEO.STREAM.DOLBY.VISION.4K");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_LETV_LEADING_2K, "2K");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_4K, "4K");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_3D, "3D");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DB, "DB");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_360_350, "VIDEO.STREAM.360.350");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_360_800, "VIDEO.STREAM.360.800");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_360_1300, "VIDEO.STREAM.360.1300");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_360_720p, "VIDEO.STREAM.360.720P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_360_1080p, "VIDEO.STREAM.360.1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_360_4K, "VIDEO.STREAM.360.4K");

        STREAM_CODE_FILTER_MAP.put(CODE_NAME_350, "流畅");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_800, "标清");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_1000, "标清");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_1300, "高清");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_720p, "超清");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_1080p, "1080P");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_1080p3m, "1080P");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_1080p6m, "1080P");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_3d1080p, "3D1080P");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_3d720p, "3D超清");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_3d1080p6M, "3D1080P");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_DOLBY_720p, "杜比超清");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_DOLBY_1300, "杜比高清");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_DOLBY_1000, "杜比标清");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_DOLBY_1080p, "杜比1080P");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_DOLBY_1080p_ATMOS, "杜比全景声");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_DOLBY_VISION_4K, "杜比视界");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_4K, "4K");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_360_350, "全景流畅");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_360_800, "全景标清");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_360_1300, "全景高清");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_360_720p, "全景超清");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_360_1080p, "全景1080P");
        STREAM_CODE_FILTER_MAP.put(CODE_NAME_360_4K, "全景4K");

        // 入库码流过滤
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_180, "极速");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_350, "流畅");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_800, "标清");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_1000, "标清");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_1300, "高清");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_720p, "超清");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_1080p, "1080P");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_1080p3m, "1080P");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_1080p6m, "1080P");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_3d1080p, "3D1080P");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_3d720p, "3D超清");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_3d1080p6M, "3D1080P");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_DOLBY_720p, "杜比超清");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_DOLBY_1300, "杜比高清");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_DOLBY_1000, "杜比标清");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_DOLBY_1080p, "杜比1080P");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_DOLBY_1080p_ATMOS, "杜比全景声");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_DOLBY_VISION_4K, "杜比视界");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_4K, "4K");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_360_180, "全景极速");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_360_350, "全景流畅");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_360_800, "全景标清");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_360_1300, "全景高清");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_360_720p, "全景超清");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_360_1080p, "全景1080P");
        STREAM_CODE_SAVE_FILTER_MAP.put(CODE_NAME_360_4K, "全景4K");

        // 码流顺序：全景》4k》杜比》3D》普通
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_350, 1);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_800, 2);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1000, 2);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1300, 3);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_720p, 4);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1080p, 5);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1080p3m, 6);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1080p6m, 7);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_3d1080p, 8);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_3d720p, 9);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_3d1080p6M, 10);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_DOLBY_1000, 11);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_DOLBY_1300, 12);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_DOLBY_720p, 13);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_DOLBY_1080p, 14);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_4K, 15);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_360_350, 16);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_360_800, 17);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_360_1300, 18);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_360_720p, 19);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_360_1080p, 20);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_360_4K, 21);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_DOLBY_1080p_ATMOS, 22);

        LIVE_STREAM_CODE_TIP_MAP.put(CODE_NAME_350, "1M");
        LIVE_STREAM_CODE_TIP_MAP.put(CODE_NAME_800, "2M");
        LIVE_STREAM_CODE_TIP_MAP.put(CODE_NAME_1000, "2M");
        LIVE_STREAM_CODE_TIP_MAP.put(CODE_NAME_1300, "3M");
        LIVE_STREAM_CODE_TIP_MAP.put(CODE_NAME_720p, "4M");
        LIVE_STREAM_CODE_TIP_MAP.put(CODE_NAME_1080p, "8M");
        LIVE_STREAM_CODE_TIP_MAP.put(CODE_NAME_1080p6m, "8M");
        LIVE_STREAM_CODE_TIP_MAP.put(CODE_NAME_3d1080p, "8M");
        LIVE_STREAM_CODE_TIP_MAP.put(CODE_NAME_3d720p, "4M");
        LIVE_STREAM_CODE_TIP_MAP.put(CODE_NAME_3d1080p6M, "8M");
        LIVE_STREAM_CODE_TIP_MAP.put(CODE_NAME_DOLBY_720p, "4M");
        LIVE_STREAM_CODE_TIP_MAP.put(CODE_NAME_DOLBY_1300, "3M");
        LIVE_STREAM_CODE_TIP_MAP.put(CODE_NAME_DOLBY_1000, "2M");
        LIVE_STREAM_CODE_TIP_MAP.put(CODE_NAME_DOLBY_1080p, "8M");
        LIVE_STREAM_CODE_TIP_MAP.put(CODE_NAME_DOLBY_1080p_ATMOS, "8M");
        LIVE_STREAM_CODE_TIP_MAP.put(CODE_NAME_4K, "10M带宽");

        X40_STREAM_CODE_NAME_MAP.put(CODE_NAME_350, "流畅");
        X40_STREAM_CODE_NAME_MAP.put(CODE_NAME_800, "高清");
        X40_STREAM_CODE_NAME_MAP.put(CODE_NAME_1000, "高清");
        X40_STREAM_CODE_NAME_MAP.put(CODE_NAME_1300, "超清");
        X40_STREAM_CODE_NAME_MAP.put(CODE_NAME_720p, "720p");
        X40_STREAM_CODE_NAME_MAP.put(CODE_NAME_1080p, "1080p下载");
        X40_STREAM_CODE_NAME_MAP.put(CODE_NAME_1080p6m, "1080p");
        X40_STREAM_CODE_NAME_MAP.put(CODE_NAME_3d1080p, "3D 1080p下载");
        X40_STREAM_CODE_NAME_MAP.put(CODE_NAME_3d720p, "3D 720p");
        X40_STREAM_CODE_NAME_MAP.put(CODE_NAME_3d1080p6M, "3D 1080p");
        X40_STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_720p, "杜比 720P");
        X40_STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_1300, "杜比 超清");
        X40_STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_1000, "杜比 高清");
        X40_STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_1080p, "杜比 1080p");
        X40_STREAM_CODE_NAME_MAP.put(CODE_NAME_4K, "4K");
        X40_STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_1080p_ATMOS, "杜比 全景声");

        STREAM_CODE_TIP_MAP.put(CODE_NAME_350, "1M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_800, "2M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_1000, "2M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_1300, "3M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_720p, "4M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_1080p, "8M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_1080p6m, "8M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_3d1080p, "8M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_3d720p, "4M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_3d1080p6M, "8M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_DOLBY_720p, "4M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_DOLBY_1300, "3M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_DOLBY_1000, "2M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_DOLBY_1080p, "8M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_DOLBY_1080p_ATMOS, "8M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_DOLBY_VISION_4K, "10M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_4K, "10M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_360_350, "1M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_360_800, "2M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_360_1300, "3M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_360_720p, "4M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_360_1080p, "8M");
        STREAM_CODE_TIP_MAP.put(CODE_NAME_360_4K, "10M");

        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_350, "0");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_800, "0");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_1000, "0");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_1300, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_720p, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_1080p, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_1080p6m, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_1080p3m, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_3d720p, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_3d1080p, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_3d1080p6M, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_DOLBY_720p, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_DOLBY_1300, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_DOLBY_1000, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_DOLBY_1080p, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_DOLBY_1080p_ATMOS, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_4K, "1");

        STREAM_CODE_IFCHARGE_YUANXIAN_MAP.put(CODE_NAME_350, "1");
        STREAM_CODE_IFCHARGE_YUANXIAN_MAP.put(CODE_NAME_800, "1");
        STREAM_CODE_IFCHARGE_YUANXIAN_MAP.put(CODE_NAME_1000, "1");
        STREAM_CODE_IFCHARGE_YUANXIAN_MAP.put(CODE_NAME_1300, "1");
        STREAM_CODE_IFCHARGE_YUANXIAN_MAP.put(CODE_NAME_720p, "1");
        STREAM_CODE_IFCHARGE_YUANXIAN_MAP.put(CODE_NAME_1080p, "1");
        STREAM_CODE_IFCHARGE_YUANXIAN_MAP.put(CODE_NAME_1080p6m, "1");
        STREAM_CODE_IFCHARGE_YUANXIAN_MAP.put(CODE_NAME_3d720p, "1");
        STREAM_CODE_IFCHARGE_YUANXIAN_MAP.put(CODE_NAME_3d1080p, "1");
        STREAM_CODE_IFCHARGE_YUANXIAN_MAP.put(CODE_NAME_3d1080p6M, "1");
        STREAM_CODE_IFCHARGE_YUANXIAN_MAP.put(CODE_NAME_DOLBY_720p, "1");
        STREAM_CODE_IFCHARGE_YUANXIAN_MAP.put(CODE_NAME_DOLBY_1300, "1");
        STREAM_CODE_IFCHARGE_YUANXIAN_MAP.put(CODE_NAME_DOLBY_1000, "1");
        STREAM_CODE_IFCHARGE_YUANXIAN_MAP.put(CODE_NAME_DOLBY_1080p, "1");
        STREAM_CODE_IFCHARGE_YUANXIAN_MAP.put(CODE_NAME_DOLBY_1080p_ATMOS, "1");
        STREAM_CODE_IFCHARGE_YUANXIAN_MAP.put(CODE_NAME_4K, "1");

        STREAM_CODE_DOWNLOAD_IFCHARGE_MAP.put(CODE_NAME_350, "1");
        STREAM_CODE_DOWNLOAD_IFCHARGE_MAP.put(CODE_NAME_800, "1");
        STREAM_CODE_DOWNLOAD_IFCHARGE_MAP.put(CODE_NAME_1000, "1");
        STREAM_CODE_DOWNLOAD_IFCHARGE_MAP.put(CODE_NAME_1300, "1");
        STREAM_CODE_DOWNLOAD_IFCHARGE_MAP.put(CODE_NAME_720p, "1");
        STREAM_CODE_DOWNLOAD_IFCHARGE_MAP.put(CODE_NAME_1080p, "1");
        STREAM_CODE_DOWNLOAD_IFCHARGE_MAP.put(CODE_NAME_1080p6m, "1");
        STREAM_CODE_DOWNLOAD_IFCHARGE_MAP.put(CODE_NAME_3d720p, "1");
        STREAM_CODE_DOWNLOAD_IFCHARGE_MAP.put(CODE_NAME_3d1080p, "1");
        STREAM_CODE_DOWNLOAD_IFCHARGE_MAP.put(CODE_NAME_3d1080p6M, "1");
        STREAM_CODE_DOWNLOAD_IFCHARGE_MAP.put(CODE_NAME_DOLBY_720p, "1");
        STREAM_CODE_DOWNLOAD_IFCHARGE_MAP.put(CODE_NAME_DOLBY_1300, "1");
        STREAM_CODE_DOWNLOAD_IFCHARGE_MAP.put(CODE_NAME_DOLBY_1000, "1");
        STREAM_CODE_DOWNLOAD_IFCHARGE_MAP.put(CODE_NAME_DOLBY_1080p, "1");
        STREAM_CODE_DOWNLOAD_IFCHARGE_MAP.put(CODE_NAME_DOLBY_1080p_ATMOS, "1");
        STREAM_CODE_DOWNLOAD_IFCHARGE_MAP.put(CODE_NAME_4K, "1");

        STREAM_CODE_KBPS_MAP.put(CODE_NAME_350, "295");
        STREAM_CODE_KBPS_MAP.put(CODE_NAME_800, "585");
        STREAM_CODE_KBPS_MAP.put(CODE_NAME_1000, "585");
        STREAM_CODE_KBPS_MAP.put(CODE_NAME_1300, "1100");
        STREAM_CODE_KBPS_MAP.put(CODE_NAME_720p, "1800");
        STREAM_CODE_KBPS_MAP.put(CODE_NAME_1080p, "15000");
        STREAM_CODE_KBPS_MAP.put(CODE_NAME_1080p6m, "6200");
        STREAM_CODE_KBPS_MAP.put(CODE_NAME_1080p3m, "3000");
        STREAM_CODE_KBPS_MAP.put(CODE_NAME_3d720p, "1650");
        STREAM_CODE_KBPS_MAP.put(CODE_NAME_3d1080p, "15200");
        STREAM_CODE_KBPS_MAP.put(CODE_NAME_3d1080p6M, "6000");
        STREAM_CODE_KBPS_MAP.put(CODE_NAME_4K, "6000");
        // ------------------------------------------------------这几个需要确认
        STREAM_CODE_KBPS_MAP.put(CODE_NAME_DOLBY_720p, "1900");
        STREAM_CODE_KBPS_MAP.put(CODE_NAME_DOLBY_1300, "1200");
        STREAM_CODE_KBPS_MAP.put(CODE_NAME_DOLBY_1000, "500");
        STREAM_CODE_KBPS_MAP.put(CODE_NAME_DOLBY_1080p, "6300");
        STREAM_CODE_KBPS_MAP.put(CODE_NAME_DOLBY_1080p_ATMOS, "6300");
        STREAM_CODE_KBPS_MAP.put(CODE_NAME_DOLBY_VISION_4K, "10000");

        STREAM_CODE_IFCHARGE_BOX_MAP.put(CODE_NAME_350, "0");
        STREAM_CODE_IFCHARGE_BOX_MAP.put(CODE_NAME_800, "0");
        STREAM_CODE_IFCHARGE_BOX_MAP.put(CODE_NAME_1000, "0");
        STREAM_CODE_IFCHARGE_BOX_MAP.put(CODE_NAME_1300, "1");
        STREAM_CODE_IFCHARGE_BOX_MAP.put(CODE_NAME_720p, "1");
        STREAM_CODE_IFCHARGE_BOX_MAP.put(CODE_NAME_1080p, "1");
        STREAM_CODE_IFCHARGE_BOX_MAP.put(CODE_NAME_1080p6m, "1");
        STREAM_CODE_IFCHARGE_BOX_MAP.put(CODE_NAME_3d720p, "1");
        STREAM_CODE_IFCHARGE_BOX_MAP.put(CODE_NAME_3d1080p, "1");
        STREAM_CODE_IFCHARGE_BOX_MAP.put(CODE_NAME_3d1080p6M, "1");

        STREAM_CODE_ONLYDOWN.put(CODE_NAME_350, "0");
        STREAM_CODE_ONLYDOWN.put(CODE_NAME_800, "0");
        STREAM_CODE_ONLYDOWN.put(CODE_NAME_1000, "0");
        STREAM_CODE_ONLYDOWN.put(CODE_NAME_1300, "0");
        STREAM_CODE_ONLYDOWN.put(CODE_NAME_720p, "0");
        STREAM_CODE_ONLYDOWN.put(CODE_NAME_1080p, "1");
        STREAM_CODE_ONLYDOWN.put(CODE_NAME_1080p6m, "0");
        STREAM_CODE_ONLYDOWN.put(CODE_NAME_1080p3m, "0");
        STREAM_CODE_ONLYDOWN.put(CODE_NAME_3d720p, "0");
        STREAM_CODE_ONLYDOWN.put(CODE_NAME_3d1080p, "1");
        STREAM_CODE_ONLYDOWN.put(CODE_NAME_3d1080p6M, "0");
        STREAM_CODE_ONLYDOWN.put(CODE_NAME_DOLBY_720p, "0");
        STREAM_CODE_ONLYDOWN.put(CODE_NAME_DOLBY_1300, "0");
        STREAM_CODE_ONLYDOWN.put(CODE_NAME_DOLBY_1000, "0");
        STREAM_CODE_ONLYDOWN.put(CODE_NAME_DOLBY_1080p, "0");
        STREAM_CODE_ONLYDOWN.put(CODE_NAME_DOLBY_1080p_ATMOS, "0");
        STREAM_CODE_ONLYDOWN.put(CODE_NAME_4K, "0");

        STREAM_CODE_CANDOWN.put(CODE_NAME_350, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_800, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_1000, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_1300, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_720p, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_1080p, "0");// 15M的不用了
        STREAM_CODE_CANDOWN.put(CODE_NAME_1080p6m, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_3d720p, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_3d1080p, "0");// //15M的不用了
        STREAM_CODE_CANDOWN.put(CODE_NAME_3d1080p6M, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_DOLBY_720p, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_DOLBY_1300, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_DOLBY_1000, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_DOLBY_1080p, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_DOLBY_1080p_ATMOS, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_4K, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_360_350, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_360_800, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_360_1300, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_360_720p, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_360_1080p, "1");
        STREAM_CODE_CANDOWN.put(CODE_NAME_360_4K, "1");

        STREAM_CODE_CANDOWN_V1.put(CODE_NAME_350, "1");
        STREAM_CODE_CANDOWN_V1.put(CODE_NAME_800, "1");
        STREAM_CODE_CANDOWN_V1.put(CODE_NAME_1000, "1");
        STREAM_CODE_CANDOWN_V1.put(CODE_NAME_1300, "1");
        STREAM_CODE_CANDOWN_V1.put(CODE_NAME_720p, "1");
        STREAM_CODE_CANDOWN_V1.put(CODE_NAME_1080p, "1");// 15M的不用了
        STREAM_CODE_CANDOWN_V1.put(CODE_NAME_1080p6m, "0");
        STREAM_CODE_CANDOWN_V1.put(CODE_NAME_1080p3m, "0");
        STREAM_CODE_CANDOWN_V1.put(CODE_NAME_3d720p, "1");
        STREAM_CODE_CANDOWN_V1.put(CODE_NAME_3d1080p, "1");// //15M的不用了
        STREAM_CODE_CANDOWN_V1.put(CODE_NAME_3d1080p6M, "0");
        STREAM_CODE_CANDOWN_V1.put(CODE_NAME_DOLBY_720p, "1");
        STREAM_CODE_CANDOWN_V1.put(CODE_NAME_DOLBY_1300, "1");
        STREAM_CODE_CANDOWN_V1.put(CODE_NAME_DOLBY_1000, "1");
        STREAM_CODE_CANDOWN_V1.put(CODE_NAME_DOLBY_1080p, "1");
        STREAM_CODE_CANDOWN_V1.put(CODE_NAME_DOLBY_1080p_ATMOS, "1");
        STREAM_CODE_CANDOWN_V1.put(CODE_NAME_4K, "1");

        STREAM_CODE_CANPLAY.put(CODE_NAME_350, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_800, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_1000, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_1300, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_720p, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_1080p, "0");
        STREAM_CODE_CANPLAY.put(CODE_NAME_1080p6m, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_3d720p, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_3d1080p, "0");
        STREAM_CODE_CANPLAY.put(CODE_NAME_3d1080p6M, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_DOLBY_720p, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_DOLBY_1300, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_DOLBY_1000, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_DOLBY_1080p, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_DOLBY_1080p_ATMOS, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_DOLBY_VISION_4K, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_4K, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_360_350, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_360_800, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_360_1300, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_360_720p, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_360_1080p, "1");
        STREAM_CODE_CANPLAY.put(CODE_NAME_360_4K, "1");

        for (Integer cid : CHARGE_CATEGORY) {
            CHARGE_CATEGORY_SET.add(cid);
        }
        for (String stream : CHARGE_STREAM) {
            CHARGE_STREAM_SET.add(stream);
        }
        for (String stream : THIRD_PARTY_CHARGE_STREAM) {
            THIRD_PARTY_CHARGE_STREAM_SET.add(stream);
        }
        for (String stream : US_CHARGE_STREAM) {
            CHARGE_STREAM_SET_US.add(stream);
        }
        for (String stream : YUANXIAN_CHARGE_STREAM) {
            YUANXIAN_CHARGE_STREAM_SET.add(stream);
        }
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_FLV_PREFIX + CODE_NAME_350, "252001");
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_MP4_PREFIX + CODE_NAME_800, "252013");
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_FLV_PREFIX + CODE_NAME_1000, "252016");
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_FLV_PREFIX + CODE_NAME_1300, "252017");
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_FLV_PREFIX + CODE_NAME_720p, "252018");
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_MP4_PREFIX + CODE_NAME_1080p, "252019");
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_FLV_PREFIX + CODE_NAME_1080p6m, "252020");
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_MP4_PREFIX + CODE_NAME_350, "252021");
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_MP4_PREFIX + CODE_NAME_1300, "252022");
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_MP4_PREFIX + CODE_NAME_DOLBY_1000, "252023");
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_MP4_PREFIX + CODE_NAME_DOLBY_1300, "252024");
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_MP4_PREFIX + CODE_NAME_DOLBY_720p, "252025");
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_MP4_PREFIX + CODE_NAME_DOLBY_1080p, "252026");
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_FLV_PREFIX + CODE_NAME_3d720p, "252029");
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_MP4_PREFIX + CODE_NAME_3d720p, "252030");
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_FLV_PREFIX + CODE_NAME_3d1080p6M, "252031");
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_MP4_PREFIX + CODE_NAME_3d1080p6M, "252032");
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_FLV_PREFIX + CODE_NAME_1080p3m, "252035");
        SEARCH_STREAM_CODE_MAP.put(VIDEO_TYPE_MP4_PREFIX + CODE_NAME_4K, "252054");

        CODE_STREAM_MAP.put(1, "flv_350");
        CODE_STREAM_MAP.put(8, "flv_vip");
        CODE_STREAM_MAP.put(9, "mp4");
        CODE_STREAM_MAP.put(10, "flv_live");
        CODE_STREAM_MAP.put(11, "union_low");
        CODE_STREAM_MAP.put(12, "union_high");
        CODE_STREAM_MAP.put(13, "mp4_800");
        CODE_STREAM_MAP.put(16, "flv_1000");
        CODE_STREAM_MAP.put(17, "flv_1300");
        CODE_STREAM_MAP.put(18, "flv_720p");
        CODE_STREAM_MAP.put(19, "mp4_1080p");
        CODE_STREAM_MAP.put(20, "flv_1080p6m");
        CODE_STREAM_MAP.put(21, "mp4_350");
        CODE_STREAM_MAP.put(22, "mp4_1300");
        CODE_STREAM_MAP.put(23, "mp4_800_db");
        CODE_STREAM_MAP.put(24, "mp4_1300_db");
        CODE_STREAM_MAP.put(25, "mp4_720p_db");
        CODE_STREAM_MAP.put(26, "mp4_1080p6m_db");
        CODE_STREAM_MAP.put(27, "flv_yuanhua");
        CODE_STREAM_MAP.put(28, "mp4_yuanhua");
        CODE_STREAM_MAP.put(29, "flv_720p_3d");
        CODE_STREAM_MAP.put(30, "mp4_720p_3d");
        CODE_STREAM_MAP.put(31, "flv_1080p6m_3d");
        CODE_STREAM_MAP.put(32, "mp4_1080p6m_3d");
        CODE_STREAM_MAP.put(33, "flv_1080p_3d");
        CODE_STREAM_MAP.put(34, "mp4_1080p_3d");
        CODE_STREAM_MAP.put(35, "flv_1080p3m");
        CODE_STREAM_MAP.put(51, "mp4_720p");
        CODE_STREAM_MAP.put(52, "mp4_1080p3m");
        CODE_STREAM_MAP.put(53, "mp4_1080p6m");
        CODE_STREAM_MAP.put(54, "mp4_4k");

        MARLIN_STREAM_CODE_MAP.put("flv_350", 72);
        MARLIN_STREAM_CODE_MAP.put("flv_1000", 74);
        MARLIN_STREAM_CODE_MAP.put("flv_1300", 76);
        MARLIN_STREAM_CODE_MAP.put("flv_720p", 78);
        MARLIN_STREAM_CODE_MAP.put("mp4_1080p", 80);
        MARLIN_STREAM_CODE_MAP.put("flv_1080p6m", 82);
        MARLIN_STREAM_CODE_MAP.put("mp4_350", 72);
        MARLIN_STREAM_CODE_MAP.put("mp4_1300", 76);
        MARLIN_STREAM_CODE_MAP.put("mp4_800_db", 92);
        MARLIN_STREAM_CODE_MAP.put("mp4_1300_db", 76);
        MARLIN_STREAM_CODE_MAP.put("mp4_720p_db", 96);
        MARLIN_STREAM_CODE_MAP.put("mp4_1080p6m_db", 100);
        MARLIN_STREAM_CODE_MAP.put("flv_720p_3d", 110);
        MARLIN_STREAM_CODE_MAP.put("mp4_720p_3d", 110);
        MARLIN_STREAM_CODE_MAP.put("flv_1080p6m_3d", 114);
        MARLIN_STREAM_CODE_MAP.put("mp4_1080p6m_3d", 114);
        MARLIN_STREAM_CODE_MAP.put("flv_1080p_3d", 98);
        MARLIN_STREAM_CODE_MAP.put("mp4_1080p_3d", 98);
        MARLIN_STREAM_CODE_MAP.put("flv_1080p3m", 80);
        MARLIN_STREAM_CODE_MAP.put("mp4_4k", 86);
        MARLIN_STREAM_CODE_MAP.put("mp4_360_180", 160);
        MARLIN_STREAM_CODE_MAP.put("mp4_360_350", 161);
        MARLIN_STREAM_CODE_MAP.put("mp4_360_800", 162);
        MARLIN_STREAM_CODE_MAP.put("mp4_360_1300", 163);
        MARLIN_STREAM_CODE_MAP.put("mp4_360_720p", 164);
        MARLIN_STREAM_CODE_MAP.put("mp4_360_1080p", 165);
        MARLIN_STREAM_CODE_MAP.put("mp4_360_2k", 166);
        MARLIN_STREAM_CODE_MAP.put("mp4_360_4k", 167);

        PLAY_REDUCED_MAP.put(CODE_NAME_3d1080p6M, PLAY_REDUCED_3D_1080P);
        PLAY_REDUCED_MAP.put(CODE_NAME_1080p6m, PLAY_REDUCED_1080P);
        PLAY_REDUCED_MAP.put(CODE_NAME_3d720p, PLAY_REDUCED_3D_720P);
        PLAY_REDUCED_MAP.put(CODE_NAME_4K, PLAY_REDUCED_4K);

        VTYPE_REDUCED_MAP.put(CODE_NAME_350, VTYPE_COMPATIBLE_350);
        VTYPE_REDUCED_MAP.put(CODE_NAME_800, VTYPE_COMPATIBLE_800);
        VTYPE_REDUCED_MAP.put(CODE_NAME_1000, VTYPE_COMPATIBLE_800);
        VTYPE_REDUCED_MAP.put(CODE_NAME_1300, VTYPE_COMPATIBLE_1300);
        VTYPE_REDUCED_MAP.put(CODE_NAME_720p, VTYPE_COMPATIBLE_720P);
        VTYPE_REDUCED_MAP.put(CODE_NAME_1080p, VTYPE_COMPATIBLE_1080P);
        VTYPE_REDUCED_MAP.put(CODE_NAME_1080p3m, VTYPE_COMPATIBLE_1080P);
        VTYPE_REDUCED_MAP.put(CODE_NAME_1080p6m, VTYPE_COMPATIBLE_1080P);
        VTYPE_REDUCED_MAP.put(CODE_NAME_4K, VTYPE_COMPATIBLE_4K);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DOLBY_1000, VTYPE_COMPATIBLE_800_DB);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DOLBY_1300, VTYPE_COMPATIBLE_1300_DB);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DOLBY_720p, VTYPE_COMPATIBLE_720P_DB);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DOLBY_1080p, VTYPE_COMPATIBLE_1080P_DB);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DOLBY_1080p_ATMOS, VTYPE_COMPATIBLE_1080P_DB_ATMOS);
        VTYPE_REDUCED_MAP.put(CODE_NAME_3d720p, VTYPE_COMPATIBLE_3D_720P);
        VTYPE_REDUCED_MAP.put(CODE_NAME_3d1080p, VTYPE_COMPATIBLE_3D_1080P);
        VTYPE_REDUCED_MAP.put(CODE_NAME_3d1080p6M, VTYPE_COMPATIBLE_3D_1080P);
        VTYPE_REDUCED_MAP.put(CODE_NAME_360_350, VTYPE_COMPATIBLE_360_350);
        VTYPE_REDUCED_MAP.put(CODE_NAME_360_800, VTYPE_COMPATIBLE_360_800);
        VTYPE_REDUCED_MAP.put(CODE_NAME_360_1300, VTYPE_COMPATIBLE_360_1300);
        VTYPE_REDUCED_MAP.put(CODE_NAME_360_720p, VTYPE_COMPATIBLE_360_720p);
        VTYPE_REDUCED_MAP.put(CODE_NAME_360_1080p, VTYPE_COMPATIBLE_360_1080p);
        VTYPE_REDUCED_MAP.put(CODE_NAME_360_4K, VTYPE_COMPATIBLE_360_4K);

        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_350, H265_VTYPE_COMPATIBLE_350);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_800, H265_VTYPE_COMPATIBLE_800);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_1000, H265_VTYPE_COMPATIBLE_800);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_1300, H265_VTYPE_COMPATIBLE_1300);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_720p, H265_VTYPE_COMPATIBLE_720P);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_1080p, H265_VTYPE_COMPATIBLE_1080P);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_1080p3m, H265_VTYPE_COMPATIBLE_1080P);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_1080p6m, H265_VTYPE_COMPATIBLE_1080P);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_4K, H265_VTYPE_COMPATIBLE_4K);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_DOLBY_1000, H265_VTYPE_COMPATIBLE_800_DB);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_DOLBY_1300, H265_VTYPE_COMPATIBLE_1300_DB);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_DOLBY_720p, H265_VTYPE_COMPATIBLE_720P_DB);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_DOLBY_1080p, H265_VTYPE_COMPATIBLE_1080P_DB);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_DOLBY_1080p_ATMOS, H265_VTYPE_COMPATIBLE_1080P_DB_ATMOS);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_3d720p, H265_VTYPE_COMPATIBLE_3D_720P);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_3d1080p, H265_VTYPE_COMPATIBLE_3D_1080P);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_3d1080p6M, H265_VTYPE_COMPATIBLE_3D_1080P);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_360_350, H265_VTYPE_COMPATIBLE_350);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_360_800, H265_VTYPE_COMPATIBLE_800);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_360_1300, H265_VTYPE_COMPATIBLE_1300);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_360_720p, H265_VTYPE_COMPATIBLE_720P);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_360_1080p, H265_VTYPE_COMPATIBLE_1080P);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_360_4K, H265_VTYPE_COMPATIBLE_4K);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_360_350, H265_VTYPE_COMPATIBLE_360_350);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_360_800, H265_VTYPE_COMPATIBLE_360_800);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_360_1300, H265_VTYPE_COMPATIBLE_360_1300);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_360_720p, H265_VTYPE_COMPATIBLE_360_720P);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_360_1080p, H265_VTYPE_COMPATIBLE_360_1080P);
        SUPPORT_H265_DEVICE_VTYPE_REDUCED_MAP.put(CODE_NAME_360_4K, H265_VTYPE_COMPATIBLE_360_4K);
    }

    public static String nameOf(String code, String langcode) {
        String codeName = MessageUtils.getMessage(STREAM_CODE_NAME_MAP.get(code), langcode) != null ? MessageUtils
                .getMessage(STREAM_CODE_NAME_MAP.get(code), langcode) : STREAM_CODE_NAME_MAP.get(code);
        if (codeName == null) {
            codeName = MessageUtils.getMessage("VIDEO.STREAM.UNKNOWN", langcode);
        }
        return codeName;
    }

    /**
     * 获取码流名称
     * @param streamCode
     * @param langcode
     * @return
     */
    public static String getStreamName(String streamCode, String langcode) {
        if (StringUtil.isBlank(streamCode) || StringUtil.isBlank(STREAM_CODE_NAME_MAP.get(streamCode))) {
            return null;
        }
        return MessageUtils.getMessage(STREAM_CODE_NAME_MAP.get(streamCode), langcode);
    }

    public static String ifCharge(String code) {
        String ifCharge = STREAM_CODE_IFCHARGE_MAP.get(code);
        if (ifCharge == null) {
            ifCharge = "1";
        }
        return ifCharge;
    }

    public static String ifChargeForBox(String code) {
        String ifCharge = STREAM_CODE_IFCHARGE_BOX_MAP.get(code);
        if (ifCharge == null) {
            ifCharge = "1";
        }
        return ifCharge;
    }

    public static String getMbps(String code) {
        String mbps = STREAM_CODE_KBPS_MAP.get(code);
        return mbps;
    }

    public static String getTipText(String code, String locale) {
        String tipText = STREAM_CODE_TIP_MAP.get(code) + MessageUtils.getMessage("VIDEO.STREAM.BANDWIDTH", locale);
        if ("en_us".equalsIgnoreCase(locale) || "en".equalsIgnoreCase(locale)) {
            tipText = MessageUtils.getMessage("VIDEO.STREAM.BANDWIDTH", locale) + " " + STREAM_CODE_TIP_MAP.get(code);
        }

        return tipText;
    }

    public static String getLiveTipText(String code, String langCode) {
        String tipText = LIVE_STREAM_CODE_TIP_MAP.get(code)
                + MessageUtils.getMessage("VIDEO.STREAM.BANDWIDTH", langCode);
        if ("en_us".equalsIgnoreCase(langCode) || "en".equalsIgnoreCase(langCode)) {
            tipText = MessageUtils.getMessage("VIDEO.STREAM.BANDWIDTH", langCode) + " "
                    + LIVE_STREAM_CODE_TIP_MAP.get(code);
        }

        return tipText;
    }

    public static Map<String, String> getStreamMap() {
        return STREAM_CODE_NAME_MAP;
    }

    public static Map<String, String> getLiveStreamMap() {
        return LIVE_STREAM_CODE_NAME_MAP;
    }

    public static Boolean ifChargeByCategoryId(Integer cid) {
        Boolean isCharge = true;
        if (!CHARGE_CATEGORY_SET.contains(cid)) {
            isCharge = false;
        }
        return isCharge;
    }

    public static String nameOf(String code) {
        String codeName = STREAM_CODE_NAME_MAP.get(code);
        if (codeName == null) {
            codeName = "未知码流";
        }
        return codeName;
    }

    public static String nameOfForX40(String code) {
        String codeName = X40_STREAM_CODE_NAME_MAP.get(code);
        if (codeName == null) {
            codeName = "未知码流";
        }
        return codeName;
    }

    public static final String getStreamNameByVrsIds(String streamIds, String separator) {
        StringBuilder stringBuilder = new StringBuilder();
        if (streamIds == null || streamIds.trim().length() == 0) {
            return null;
        }
        if (streamIds.contains("252001") || streamIds.contains("252021")) {// 350
            stringBuilder.append(CODE_NAME_350);
        }
        if (streamIds.contains("252016")) {// 1000
            stringBuilder.append(separator).append(CODE_NAME_1000);
        }
        if (streamIds.contains("252013")) {// 800
            stringBuilder.append(separator).append(CODE_NAME_800);
        }
        if (streamIds.contains("252017")) {// 720p
            stringBuilder.append(separator).append(CODE_NAME_720p);
        }
        if (streamIds.contains("252019")) {// 1080p
            stringBuilder.append(separator).append(CODE_NAME_1080p);
        }
        if (streamIds.contains("252035")) {// 1080p3m
            stringBuilder.append(separator).append(CODE_NAME_1080p3m);
        }
        if (streamIds.contains("252020")) {// 1080p6m
            stringBuilder.append(separator).append(CODE_NAME_1080p6m);
        }
        if (streamIds.contains("252033") || streamIds.contains("252034")) {// 3d1080p
            stringBuilder.append(separator).append(CODE_NAME_3d1080p);
        }
        if (streamIds.contains("252029") || streamIds.contains("252030")) {// 720p
            stringBuilder.append(separator).append(CODE_NAME_3d720p);
        }
        if (streamIds.contains("252031") || streamIds.contains("252032")) {// 3d1080p6m
            stringBuilder.append(separator).append(CODE_NAME_3d1080p6M);
        }
        if (streamIds.contains("252025")) {// dolby720p
            stringBuilder.append(separator).append(CODE_NAME_DOLBY_720p);
        }
        if (streamIds.contains("252024")) {// dolby1300
            stringBuilder.append(separator).append(CODE_NAME_DOLBY_1300);
        }
        if (streamIds.contains("252023")) {// dolby1000
            stringBuilder.append(separator).append(CODE_NAME_DOLBY_1000);
        }
        if (streamIds.contains("252026")) {// dolby1080p
            stringBuilder.append(separator).append(CODE_NAME_DOLBY_1080p);
        }
        return stringBuilder.toString();
    }

    private static Long[] ForcePlay6MIds = { 93898l, 93933l, 94047l, 94050l, 94136l, 94138l, 94054l };

    public static Boolean contains(Long id) {
        if (id != null) {
            return Arrays.asList(ForcePlay6MIds).contains(id);
        } else {
            return false;
        }
    }
}
