package com.letv.mas.caller.iptv.tvproxy.recommendation.constants;

import java.util.HashMap;
import java.util.Map;

public class RecommendationConstants {

    /**
     * 年龄与默认的标签导航 关系
     */
    private static final Map<Integer, String> CHILD_AGE_PAGEID = new HashMap<Integer, String>();

    public static final String[] dmVids = { "24292587", "164754", "164719" };

    /**
     * 印度版推荐area与cid映射
     */
    public static final Map<String, String> SARRS_REC_AREA_BY_CID = new HashMap<String, String>();

    /**
     * LEVIDI推荐area与cid映射
     */
    public static final Map<String, String> LEVIDI_REC_AREA_BY_CID = new HashMap<String, String>();
    static {
        LEVIDI_REC_AREA_BY_CID.put("cpId_us", "rec_2104");
        LEVIDI_REC_AREA_BY_CID.put("cId_us", "rec_1903");
    }

    /**
     * 播结束放单视频推荐 标志
     */
    public static final String REC_TYPE_PLAY_EXIT = "1";// 退出播放推荐，与客户端约定的参数
    public static final String REC_TYPE_PLAY_END = "2";// 播放结束推荐，与客户端之间的约定参数
    public static final String LEVIEW_DATA_SOURCE_CMS = "4";// 乐见从cms获取数据标识
    public static final String LEVIEW_DATA_SOURCE_REC = "5";// 乐见从推荐获取数据标识
    public static final String REC_TYPE_ALBUM_NO_TVCOPYRIGHT = "3";// 专辑详情页无版权时内容推荐，与客户端之间的约定参数

    static {
        CHILD_AGE_PAGEID.put(1, "1003171962");// 0-1岁
        CHILD_AGE_PAGEID.put(2, "1003172119");// 1-2岁
        CHILD_AGE_PAGEID.put(3, "1003183204");// 2-3岁
        CHILD_AGE_PAGEID.put(4, "1003183212");// 3-4岁
        CHILD_AGE_PAGEID.put(5, "1003184898");// 4-5岁
        CHILD_AGE_PAGEID.put(6, "1003184905");// 5-6岁
        CHILD_AGE_PAGEID.put(7, "1003184919");// 6-12岁
        CHILD_AGE_PAGEID.put(8, "1003184912");// 默认
    }

    static {
        SARRS_REC_AREA_BY_CID.put("tv_home", "rec_1903");
        SARRS_REC_AREA_BY_CID.put("eros", "rec_2104");
    }

    public static String getPageIdByAge(Integer age) {
        if (age == null) {
            return CHILD_AGE_PAGEID.get(8);
        } else if (0 < age && age <= 6) {
            return CHILD_AGE_PAGEID.get(age);
        } else if (6 < age && age <= 12) {
            return CHILD_AGE_PAGEID.get(7);
        } else {
            return CHILD_AGE_PAGEID.get(8);
        }
    }

    // 错误码
    public final static String RECOMMENDATION_ERROR_CONDITION_ERROR = "SSC001"; // 推荐条件异常
    public final static String RECOMMENDATION_ERROR_RESULT_NULL = "SSC002"; // 推荐结果返回空

    // 乐见终端型号
    public final static String TERMINAL_APPLICATION_LEVIEW = "leview"; // 推荐条件异常

    // 乐见推出指定频道数据(电影，电视剧，动漫，综艺，纪录片，频道的正片专辑){1,2,5,11,16}
    public final static Integer[] LEVIEW_REC_DATA_CID = new Integer[] { 1, 2, 5, 11, 16 };
}
