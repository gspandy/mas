package com.letv.mas.caller.iptv.tvproxy.common.constant;

import java.util.HashSet;
import java.util.Set;

public class VideoConstants {

    public static String[] LETV_SERIES_BOX = {
            "C2",
            "Letv C1",
            "Letv C1S",
            "Letv C1S",
            "Letv C1S",
            "Letv C1S",
            "Letv G1",
            "Letv New C1S",
            "Letv T1S",
            "Letv U1",
            "Letv U2",
            "Letv U3",
            "Letv U4",
            "U3",
            "U4C",
            "U4S"
    };

    public static Set<String> LETV_SERIES_BOX_SET = new HashSet<String>();

    /**
     * 播放模式，0--TV版播放，1--乐视儿童播放，2--投屏播放
     */
    public static int PLAY_MODEL_COMMON = 0;
    public static int PLAY_MODEL_CHILD = 1;
    public static int PLAY_MODEL_TOUPING = 2;

    /**
     * 使用新版本cde的TV Le版本号，新版本cde可以识别新的splatid(513)，而老版本只能识别504
     */
    public static final int PLAY_HAS_NEW_CDE_LE_VERSION = 277;

    static {
        for (String letvSeries : LETV_SERIES_BOX) {
            LETV_SERIES_BOX_SET.add(letvSeries.toUpperCase());
        }
    }

    /**
     * 国广版本第三方合作bsChannel的参数传值
     */
    public static final String CIBN_BSCHANNEL_OPERATORS = "operators";


    /**
     * tv video play cde splatid;
     * 500--老盒子；501--超级电视；502--通用版第三方；503--自有版盒子；504--海外版；505--乐看搜索；506--在线影院；
     * 507--电视购物；508--国广后台审核；509--点播TV版投屏，由领先版下发；510--乐视儿童--小鱼版；511--TV版HTML5播放；
     * 512--乐视视频三方通用版本（TV/盒子）；513--电视TV ；529--超级电视NEW ；530--乐视盒子NEW ；531--乐视TV儿童 ；532--乐视盒子儿童
     * Le--美国版；514--电视TV版--香港版;601--PC，这里用来支持国广版本第三方合作播放
     */
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_BOX_OLD = 500;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_TV = 501;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_TV_COMMON = 502;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_BOX = 503;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_OVERSEA = 504;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_LESO = 505;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_VIDEO_ONLINE = 506;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_TV_SHOPPING = 507;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_CIBN_CHECK = 508;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_TV_TOUPING = 509;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_FISH = 510;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_TV_H5 = 511;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_COMMON = 512;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_TV_LECOM_US = 513;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_TV_HK = 514;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD = 521;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_BOX_NEW = 529;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_TV_NEW = 530;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_TV = 531;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_BOX = 532;
    public static int CDE_VIDEO_PLAY_SPLATID_LETV_CIBN_OPERATORS_PC = 601;
}
