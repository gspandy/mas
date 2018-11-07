/*
 *  Copyright (c) 2011 乐视网（letv.com）. All rights reserved
 * 
 *  LETV PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Defining the constants of Stream Code.
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
public final class VideoStreamCode {
    // 1、流畅版，2、高清版，3、1.3M，4、720P，5、1080P，6、1080p6m
    public static final String CODE_NAME_350 = "350";
    public static final String CODE_NAME_1000 = "1000";
    public static final String CODE_NAME_1300 = "1300";
    public static final String CODE_NAME_720p = "720p";
    public static final String CODE_NAME_1080p = "1080p";
    public static final String CODE_NAME_1080p6m = "1080p6m";
    public static final String CODE_NAME_3d720p = "3d720p";
    public static final String CODE_NAME_3d1080p = "3d1080p";
    public static final String CODE_NAME_3d1080p6M = "3d1080p6m";
    // 杜比新增加码流--start
    public static final String CODE_NAME_DOLBY_720p = "720p_db";
    public static final String CODE_NAME_DOLBY_1300 = "1300_db";
    public static final String CODE_NAME_DOLBY_1000 = "800_db";
    public static final String CODE_NAME_DOLBY_1080p = "1080p6m_db";
    // 杜比新增加码流--end
    public static final Map<String, String> STREAM_CODE_IFCHARGE_MAP = new HashMap<String, String>();
    private static final Map<String, String> STREAM_CODE_NAME_MAP = new HashMap<String, String>();
    static {
        STREAM_CODE_NAME_MAP.put("350", "免费播放");
        STREAM_CODE_NAME_MAP.put("1000", "标清播放");
        STREAM_CODE_NAME_MAP.put("1300", "高清播放");
        STREAM_CODE_NAME_MAP.put("720p", "720P播放");
        STREAM_CODE_NAME_MAP.put("1080p", "超清下载");
        STREAM_CODE_NAME_MAP.put("1080p6m", "1080p播放");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_350, "0");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_1000, "0");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_1300, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_720p, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_1080p, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_1080p6m, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_3d720p, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_3d1080p, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_3d1080p6M, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_DOLBY_720p, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_DOLBY_1300, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_DOLBY_1000, "1");
        STREAM_CODE_IFCHARGE_MAP.put(CODE_NAME_DOLBY_1080p, "1");
    }

    public static final int STREAM_CODE_350 = 1;
    // public static final int STREAM_CODE_800 = "800"; // @TODO where is the
    // 800?
    public static final int STREAM_CODE_1000 = 2;
    public static final int STREAM_CODE_1300 = 3;
    public static final int STREAM_CODE_720p = 4;
    public static final int STREAM_CODE_1080p = 5;
    public static final int STREAM_CODE_1080p6m = 6;

    private static final Map<String, Integer> STREAM_CODE_MAP = new HashMap<String, Integer>();
    static {
        STREAM_CODE_MAP.put("350", STREAM_CODE_350);
        STREAM_CODE_MAP.put("1000", STREAM_CODE_1000);
        STREAM_CODE_MAP.put("1300", STREAM_CODE_1300);
        STREAM_CODE_MAP.put("720p", STREAM_CODE_720p);
        STREAM_CODE_MAP.put("1080p", STREAM_CODE_1080p);
        STREAM_CODE_MAP.put("1080p6m", STREAM_CODE_1080p6m);
    }

    public static int codeOf(String streamName) {
        streamName = streamName.toLowerCase(); // case insensitive
        return STREAM_CODE_MAP.get(streamName);
    }

    public static String nameOf(String streamCode) {
        streamCode = streamCode.toLowerCase();
        return STREAM_CODE_NAME_MAP.get(streamCode);
    }

    public static boolean isValidStreamCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return false;
        }
        if (STREAM_CODE_MAP.get(code.toLowerCase()) == null) {
            return false;
        }
        return true;
    }
}
