package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live;

import java.util.HashMap;
import java.util.Map;

public class LiveStreamConstants {
    public static final Map<String, Integer> STREAM_CODE_SORT_VSLUE = new HashMap<String, Integer>();// 码流排序值
    public static final String CODE_NAME_99 = "99";
    public static final String CODE_NAME_180 = "180";
    public static final String CODE_NAME_350 = "350";
    public static final String CODE_NAME_800 = "800";
    public static final String CODE_NAME_1000 = "1000";
    public static final String CODE_NAME_1300 = "1300";
    public static final String CODE_NAME_720p = "720p";
    public static final String CODE_NAME_1080p = "1080p";
    public static final String CODE_NAME_1080p3m = "1080p3m";
    public static final String CODE_NAME_1080p6m = "1080p6m";
    public static final String CODE_NAME_2K = "2k";// 2k
    public static final String CODE_NAME_2K_H265 = "2k_h265";// 2k
    public static final String CODE_NAME_4K = "4k";// 4k
    public static final String CODE_NAME_3d720p = "3d720p";
    public static final String CODE_NAME_3d1080p = "3d1080p";
    public static final String CODE_NAME_3d1080p6M = "3d1080p6m";
    // 杜比新增加码流--start
    public static final String CODE_NAME_DOLBY_800 = "800_db";
    public static final String CODE_NAME_DOLBY_1300 = "1300_db";
    public static final String CODE_NAME_DOLBY_720p = "720p_db";
    public static final String CODE_NAME_DOLBY_1080p = "1080p_db";
    public static final String CODE_NAME_DOLBY_1080p6m = "1080p6m_db";
    public static final String CODE_NAME_DOLBY_2K = "2k_db";
    public static final String CODE_NAME_DOLBY_4K = "4k_db";

    // 第三方cp码流
    public static final String CODE_NAME_CP_64 = "cp64";
    public static final String CODE_NAME_CP_200 = "cp200";
    public static final String CODE_NAME_CP_350 = "cp350";
    public static final String CODE_NAME_CP_400 = "cp400";
    public static final String CODE_NAME_CP_600 = "cp600";
    public static final String CODE_NAME_CP_800 = "cp800";
    public static final String CODE_NAME_CP_1100 = "cp1100";
    public static final String CODE_NAME_CP_1500 = "cp1500";
    public static final String CODE_NAME_CP_1800 = "cp1800";
    public static final String CODE_NAME_CP_2500 = "cp2500";

    static {
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_99, 0);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_350, 1);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1000, 2);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1300, 3);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_720p, 4);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1080p, 5);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1080p3m, 6);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_1080p6m, 7);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_3d1080p, 8);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_3d720p, 9);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_3d1080p6M, 10);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_DOLBY_720p, 11);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_DOLBY_1300, 12);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_DOLBY_800, 13);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_DOLBY_1080p, 14);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_4K, 15);

        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_64, 1);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_200, 2);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_350, 3);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_400, 4);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_600, 5);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_800, 6);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_1100, 7);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_1500, 8);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_1800, 9);
        STREAM_CODE_SORT_VSLUE.put(CODE_NAME_CP_2500, 10);
    }
}
