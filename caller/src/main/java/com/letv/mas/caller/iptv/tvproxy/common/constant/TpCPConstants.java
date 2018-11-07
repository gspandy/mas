package com.letv.mas.caller.iptv.tvproxy.common.constant;

public class TpCPConstants {
    /**
     * 数据来源cp
     */
    public static final String TP_COOPERATION_LETV = "letv";
    public static final String TP_COOPERATION_GOLIVE = "golive";

    /**
     * golive影片类型 1-同步院线 2-在线点播 3-活动 4-预告
     */
    public static final Integer GOLIVE_TYPE_TBYX = 1;
    public static final Integer GOLIVE_TYPE_ZXDB = 2;
    public static final Integer GOLIVE_TYPE_HD = 3;
    public static final Integer GOLIVE_TYPE_YGP = 4;

    /**
     * 影片所处售票状态
     */
    public static final Integer GOLIVE_SALESTATUS_FREE = 1;
    public static final Integer GOLIVE_SALESTATUS_FUTURE = 2;
    public static final Integer GOLIVE_SALESTATUS_SALE = 3;

    /**
     * 跳转类型 3-跳详情页 7-跳视频播放
     */
    public static final Integer GOLIVE_JUMPTYPE_XQY = 3;
    public static final Integer GOLIVE_JUMPTYPE_SPBF = 7;

}
