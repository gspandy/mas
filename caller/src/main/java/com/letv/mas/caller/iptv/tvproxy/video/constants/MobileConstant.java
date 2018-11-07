package com.letv.mas.caller.iptv.tvproxy.video.constants;

import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.Stream;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class MobileConstant {

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
    // 新增360全景码流
    public static final String CODE_NAME_350_360 = "350_360";
    public static final String CODE_NAME_800_360 = "800_360";
    public static final String CODE_NAME_1300_360 = "1300_360";
    // 杜比新增加码流--start
    public static final String CODE_NAME_DOLBY_800 = "800_db";
    public static final String CODE_NAME_DOLBY_1300 = "1300_db";
    public static final String CODE_NAME_DOLBY_720p = "720p_db";
    public static final String CODE_NAME_DOLBY_1080p = "1080p_db";
    public static final String CODE_NAME_DOLBY_1080p6m = "1080p6m_db";
    public static final String CODE_NAME_DOLBY_2K = "2k_db";
    public static final String CODE_NAME_DOLBY_4K = "4k_db";

    public static final String CODE_NAME_DTS_800 = "800_dts";
    public static final String CODE_NAME_DTS_1300 = "1300_dts";
    public static final String CODE_NAME_DTS_720p = "720p_dts";
    public static final String CODE_NAME_DTS_1080p = "1080p_dts";
    public static final String CODE_NAME_DTS_1080p3m = "1080p3m_dts";
    public static final String CODE_NAME_DTS_1080p6m = "1080p6m_dts";
    public static final String CODE_NAME_DTS_2K = "2k_dts";
    public static final String CODE_NAME_DTS_2K_H265 = "2k_h265_dts";
    public static final String CODE_NAME_DTS_4K = "4k_dts";

    // TODO
    public static final String VTYPE_COMPATIBLE_180 = "58";
    public static final String VTYPE_COMPATIBLE_350 = "21,1";
    public static final String VTYPE_COMPATIBLE_800 = "13,16";

    /**
     * 没有1000的码流
     */
    public static final String VTYPE_COMPATIBLE_1300 = "22,17";
    public static final String VTYPE_COMPATIBLE_720P = "51,18";
    public static final String VTYPE_COMPATIBLE_1080P = "52,53,35,20";
    public static final String VTYPE_COMPATIBLE_2K = "134";
    public static final String VTYPE_COMPATIBLE_4K = "54";
    public static final String VTYPE_COMPATIBLE_800_DB = "23";
    public static final String VTYPE_COMPATIBLE_1300_DB = "24";
    public static final String VTYPE_COMPATIBLE_720P_DB = "25";
    public static final String VTYPE_COMPATIBLE_1080P_DB = "26";
    public static final String VTYPE_COMPATIBLE_2K_DB = "";
    public static final String VTYPE_COMPATIBLE_4K_DB = "59";
    public static final String VTYPE_COMPATIBLE_800_DTS = "138";
    public static final String VTYPE_COMPATIBLE_1300_DTS = "139";
    public static final String VTYPE_COMPATIBLE_720P_DTS = "140";
    public static final String VTYPE_COMPATIBLE_1080P_DTS = "141,142";
    public static final String VTYPE_COMPATIBLE_2K_DTS = "152,143";
    public static final String VTYPE_COMPATIBLE_4K_DTS = "144";
    public static final String VTYPE_COMPATIBLE_3D_1080P = "32,31";
    public static final String VTYPE_COMPATIBLE_3D_720P = "30,29";
    public static final String VTYPE_COMPATIBLE_350_360 = "161";
    public static final String VTYPE_COMPATIBLE_800_360 = "162,168";
    public static final String VTYPE_COMPATIBLE_1300_360 = "163,169";

    public static final String TERMINAL_PLATFORM_WEB = "420001";// WEB
    public static final String TERMINAL_PLATFORM_MOBILE = "420003";// Mobile
    public static final String TERMINAL_PLATFORM_PAD = "420005";// Pad
    public static final String TERMINAL_PLATFORM_TV = "420007";// TV
    public static final String TERMINAL_PLATFORM_CAR = "420015";// Car
    public static final String TERMINAL_PLATFORM_ALL = "ALL";// no limit

    public static final int TERMINAL_DEVICE_TYPE_MOBILE = 1;// Mobile
    public static final int TERMINAL_DEVICE_TYPE_TV = 2;// TV
    public static final int TERMINAL_DEVICE_TYPE_PAD = 3;// Pad
    public static final int TERMINAL_DEVICE_TYPE_CAR = 4;// Car

    public static final Set<String> PLAY_CHARGE_STREAM = new HashSet<String>();
    public static final Set<String> PLAY_DTS_STREAM = new HashSet<String>();
    public static final Set<String> PLAY_NORMAL_STREAM = new HashSet<String>();
    public static final Set<String> PLAY_360_STREAM = new HashSet<String>();
    public static final Set<String> PLAY_3D_STREAM = new HashSet<String>();
    public static final Set<String> PLAY_DOLBY_STREAM = new HashSet<String>();

    public static final String PLAY_SORT_STREAM_T2 = CODE_NAME_3d1080p6M + "#" + CODE_NAME_3d720p + "#"
            + CODE_NAME_DOLBY_1080p + "#" + CODE_NAME_DOLBY_720p + "#" + CODE_NAME_DOLBY_1300 + "#"
            + CODE_NAME_DOLBY_800 + "#" + CODE_NAME_4K + "#" + CODE_NAME_2K + "#" + CODE_NAME_1080p6m + "#"
            + CODE_NAME_720p + "#" + CODE_NAME_1300 + "#" + CODE_NAME_1000 + "#" + CODE_NAME_350 + "#" + CODE_NAME_180;

    public static final String ALL_STREAMS = CODE_NAME_1300_360 + "#" + CODE_NAME_800_360 + "#" + CODE_NAME_350_360
            + "#" + CODE_NAME_3d1080p6M + "#" + CODE_NAME_3d1080p + "#" + CODE_NAME_3d720p + "#" + CODE_NAME_DTS_4K
            + "#" + CODE_NAME_DTS_2K_H265 + "#" + CODE_NAME_DTS_2K + "#" + CODE_NAME_DTS_1080p6m + "#"
            + CODE_NAME_DTS_1080p3m + "#" + CODE_NAME_DTS_1080p + "#" + CODE_NAME_DTS_720p + "#" + CODE_NAME_DTS_1300
            + "#" + CODE_NAME_DTS_800 + "#" + CODE_NAME_DOLBY_4K + "#" + CODE_NAME_DOLBY_2K + "#"
            + CODE_NAME_DOLBY_1080p + "#" + CODE_NAME_DOLBY_720p + "#" + CODE_NAME_DOLBY_1300 + "#"
            + CODE_NAME_DOLBY_800 + "#" + CODE_NAME_4K + "#" + CODE_NAME_2K_H265 + "#" + CODE_NAME_2K + "#"
            + CODE_NAME_1080p6m + "#" + CODE_NAME_1080p + "#" + CODE_NAME_720p + "#" + CODE_NAME_1300 + "#"
            + CODE_NAME_800 + "#" + CODE_NAME_350 + "#" + CODE_NAME_180;

    public static final Map<String, String> STREAM_CODE_NAME_MAP = new HashMap<String, String>();// 码流对应的中文名称
    public static final Map<String, Integer> PLAY_THEATRE_STREAM_CODE2ORDER = new HashMap<String, Integer>();
    public static final Map<Integer, String> PLAY_THEATRE_STREAM_ORDER2CODE = new HashMap<Integer, String>();
    public static final List<String> PLAY_MOBILE_USE_STREAM = new ArrayList<String>();

    public interface TERMINALAPPLICATION {
        public static final String LEAUTOLINK = "leautolink"; // leautolink
        public static final String LECOM = "lecom";
        public static final String LEADING = "letv_leading_app";// 领先版
        public static final String LEADING_DEFAULT = "letv_leading_app_default_rse";// 大陆车载乐视视频
        public static final String TV = "tvserver";// tv
        public static final String TVLE = "tvle";// tv lecom
        public static final String LEFM = "com.letv.android.lefm";// 乐视FM
        public static final String LEMUSIC = "letv_lemusic_app_default_rse";// 乐视动听
        public static final String YIDAOECO = "letv_yidaoeco_app_default_rse";// 生态屏
        public static final String YIDAOENT = "letv_yidaoent_app_default_rse";// 娱乐屏
        public static final String LE_AUTO_ONLINEENT = "le_auto_onlineent";// 在线娱乐桌面
        public static final String LE_AUTO_LETV = "le_auto_letv";// 车载版乐视视频
        public static final String LE_AUTO_COMMONPLAYER = "le_auto_commonplayer";// 全功能播放器
    }

    public static final String[] LECOM_TERMINAL = { TERMINALAPPLICATION.LECOM, TERMINALAPPLICATION.TVLE,
            TERMINALAPPLICATION.LE_AUTO_COMMONPLAYER, TERMINALAPPLICATION.LE_AUTO_LETV,
            TERMINALAPPLICATION.LE_AUTO_ONLINEENT };

    /**
     * 备案的IMEI号
     */
    public static final Set<String> CRACK_PHONE_IMEI = new HashSet<String>();
    public static final Map<String, Integer> PLAY_STREAM_CODE2ORDER = new HashMap<String, Integer>();
    public static final Map<String, String> VTYPE_REDUCED_MAP = new HashMap<String, String>();
    public static final Map<String, Integer> COMMON_PLAYER_SPLATID = new HashMap<String, Integer>();
    public static final Map<String, Integer> COMMON_PLAYER_PLATID = new HashMap<String, Integer>();
    public static final Map<String, String> DEVICE_TERMINAL_PLATFORM = new HashMap<String, String>();
    static {
        CRACK_PHONE_IMEI.add("861790032957635");
        CRACK_PHONE_IMEI.add("861891036221075");
        CRACK_PHONE_IMEI.add("869636020189701");
        CRACK_PHONE_IMEI.add("866479020027503");// test
        CRACK_PHONE_IMEI.add("866647020763141");
        CRACK_PHONE_IMEI.add("866647020142536");
        CRACK_PHONE_IMEI.add("866647020115078");
        CRACK_PHONE_IMEI.add("866479021906127");
        CRACK_PHONE_IMEI.add("866647021530317");// ces

        CRACK_PHONE_IMEI.add("866647020687282");// ces
        CRACK_PHONE_IMEI.add("866647021619573");// ces
        CRACK_PHONE_IMEI.add("866646020058569");// ces
        CRACK_PHONE_IMEI.add("868038020294188");// ces
        CRACK_PHONE_IMEI.add("869611022166692");// ces
        CRACK_PHONE_IMEI.add("869611024813713");// ces
        CRACK_PHONE_IMEI.add("869611022562684");// ces
        CRACK_PHONE_IMEI.add("866647021406336");// ces
        CRACK_PHONE_IMEI.add("866647021619912");// ces
        CRACK_PHONE_IMEI.add("866647020701471");// ces
        CRACK_PHONE_IMEI.add("866647021620233");// ces
        CRACK_PHONE_IMEI.add("868038020354230");// ces
        CRACK_PHONE_IMEI.add("866646020040179");// ces
        CRACK_PHONE_IMEI.add("866646020062066");// ces
        CRACK_PHONE_IMEI.add("868038020312287");// ces
        CRACK_PHONE_IMEI.add("868918020018861");// ces
        CRACK_PHONE_IMEI.add("869611024815692");// ces
        CRACK_PHONE_IMEI.add("869611024815114");// ces
        CRACK_PHONE_IMEI.add("869611022515039");// ces
        CRACK_PHONE_IMEI.add("868918020009993");// ces
        CRACK_PHONE_IMEI.add("868918020014332");// ces
        CRACK_PHONE_IMEI.add("868918020018218");// ces
        CRACK_PHONE_IMEI.add("868918020026849");// ces
        CRACK_PHONE_IMEI.add("868918020018176");// ces
        CRACK_PHONE_IMEI.add("868918020017798");// ces
        CRACK_PHONE_IMEI.add("868918020017848");// ces
        CRACK_PHONE_IMEI.add("868918020018028");// ces
        CRACK_PHONE_IMEI.add("868918020026955");// ces
        CRACK_PHONE_IMEI.add("868918020026872");// ces
        CRACK_PHONE_IMEI.add("868918020008789");// ces
        CRACK_PHONE_IMEI.add("868256020050356");// ces
        CRACK_PHONE_IMEI.add("B43A28F4BD36");// ces-car
        CRACK_PHONE_IMEI.add("B43A28F4B252");// ces
        CRACK_PHONE_IMEI.add("08FD0EF237D3");// ces
        CRACK_PHONE_IMEI.add("EC4FE2AC");// ces

        CRACK_PHONE_IMEI.add("866647020071552");
        CRACK_PHONE_IMEI.add("869087020007008");
        CRACK_PHONE_IMEI.add("868189020026144");
        CRACK_PHONE_IMEI.add("869087020013881");
        CRACK_PHONE_IMEI.add("869087020008022");
        CRACK_PHONE_IMEI.add("866647020270279");

        CRACK_PHONE_IMEI.add("869636020149069");
        CRACK_PHONE_IMEI.add("869636020148269");

        CRACK_PHONE_IMEI.add("869636020086147");
        CRACK_PHONE_IMEI.add("869636020086154");

        CRACK_PHONE_IMEI.add("862524030010090");
        CRACK_PHONE_IMEI.add("861790033025853");

        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_180, 0);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_350, 1);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_800, 2);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_1300, 3);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_720p, 4);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_1080p, 5);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_1080p3m, 6);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_1080p6m, 7);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_2K, 8);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_2K_H265, 9);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_4K, 10);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_DOLBY_800, 11);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_DOLBY_1300, 12);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_DOLBY_720p, 13);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_DOLBY_1080p, 14);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_DOLBY_1080p6m, 15);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_DOLBY_2K, 16);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_DOLBY_4K, 17);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_DTS_800, 18);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_DTS_1300, 19);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_DTS_720p, 20);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_DTS_1080p, 21);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_DTS_1080p3m, 22);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_DTS_1080p6m, 23);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_DTS_2K, 24);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_DTS_2K_H265, 25);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_DTS_4K, 26);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_3d720p, 27);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_3d1080p, 28);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_3d1080p6M, 29);

        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_350_360, 30);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_800_360, 31);
        PLAY_STREAM_CODE2ORDER.put(CODE_NAME_1300_360, 32);

        VTYPE_REDUCED_MAP.put(CODE_NAME_180, VTYPE_COMPATIBLE_180);
        VTYPE_REDUCED_MAP.put(CODE_NAME_350, VTYPE_COMPATIBLE_350);
        VTYPE_REDUCED_MAP.put(CODE_NAME_800, VTYPE_COMPATIBLE_800);
        VTYPE_REDUCED_MAP.put(CODE_NAME_1000, VTYPE_COMPATIBLE_800);
        VTYPE_REDUCED_MAP.put(CODE_NAME_1300, VTYPE_COMPATIBLE_1300);
        VTYPE_REDUCED_MAP.put(CODE_NAME_720p, VTYPE_COMPATIBLE_720P);
        VTYPE_REDUCED_MAP.put(CODE_NAME_1080p, VTYPE_COMPATIBLE_1080P);
        VTYPE_REDUCED_MAP.put(CODE_NAME_1080p3m, VTYPE_COMPATIBLE_1080P);
        VTYPE_REDUCED_MAP.put(CODE_NAME_1080p6m, VTYPE_COMPATIBLE_1080P);
        VTYPE_REDUCED_MAP.put(CODE_NAME_2K, VTYPE_COMPATIBLE_2K);
        VTYPE_REDUCED_MAP.put(CODE_NAME_2K_H265, VTYPE_COMPATIBLE_2K);
        VTYPE_REDUCED_MAP.put(CODE_NAME_4K, VTYPE_COMPATIBLE_4K);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DOLBY_800, VTYPE_COMPATIBLE_800_DB);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DOLBY_1300, VTYPE_COMPATIBLE_1300_DB);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DOLBY_720p, VTYPE_COMPATIBLE_720P_DB);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DOLBY_1080p, VTYPE_COMPATIBLE_1080P_DB);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DOLBY_1080p6m, VTYPE_COMPATIBLE_1080P_DB);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DOLBY_4K, VTYPE_COMPATIBLE_4K_DB);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DTS_800, VTYPE_COMPATIBLE_800_DTS);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DTS_1300, VTYPE_COMPATIBLE_1300_DTS);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DTS_720p, VTYPE_COMPATIBLE_720P_DTS);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DTS_1080p, VTYPE_COMPATIBLE_1080P_DTS);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DTS_1080p3m, VTYPE_COMPATIBLE_1080P_DTS);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DTS_1080p6m, VTYPE_COMPATIBLE_1080P_DTS);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DTS_2K, VTYPE_COMPATIBLE_2K_DTS);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DTS_2K_H265, VTYPE_COMPATIBLE_2K_DTS);
        VTYPE_REDUCED_MAP.put(CODE_NAME_DTS_4K, VTYPE_COMPATIBLE_4K_DTS);
        VTYPE_REDUCED_MAP.put(CODE_NAME_3d720p, VTYPE_COMPATIBLE_3D_720P);
        VTYPE_REDUCED_MAP.put(CODE_NAME_3d1080p, VTYPE_COMPATIBLE_3D_1080P);
        VTYPE_REDUCED_MAP.put(CODE_NAME_3d1080p6M, VTYPE_COMPATIBLE_3D_1080P);

        VTYPE_REDUCED_MAP.put(CODE_NAME_350_360, VTYPE_COMPATIBLE_350_360);
        VTYPE_REDUCED_MAP.put(CODE_NAME_800_360, VTYPE_COMPATIBLE_800_360);
        VTYPE_REDUCED_MAP.put(CODE_NAME_1300_360, VTYPE_COMPATIBLE_1300_360);

        COMMON_PLAYER_SPLATID.put("720", 1506);// 明星闹钟
        COMMON_PLAYER_SPLATID.put("3004", 1504);// 乐视音乐app Android
        COMMON_PLAYER_SPLATID.put("3002", 1502);// 乐视体育app Android/TV
        COMMON_PLAYER_SPLATID.put("841", 2201);// 游戏中心Android 领先版
        COMMON_PLAYER_SPLATID.put("851", 1704);// 国际版Sarrs
        COMMON_PLAYER_SPLATID.put("911", 2701);// 乐小宝(设备launcher)
        COMMON_PLAYER_SPLATID.put("912", 2701);// 乐小宝(语音app)
        COMMON_PLAYER_SPLATID.put("3009", 1516);// 乐小宝(语音app)

        COMMON_PLAYER_PLATID.put("841", 22);// 游戏中心Android 领先版
        COMMON_PLAYER_PLATID.put("851", 17);// 国际版Sarrs
        COMMON_PLAYER_PLATID.put("911", 27);// 乐小宝(设备launcher)
        COMMON_PLAYER_PLATID.put("912", 27);// 乐小宝(语音app)

        PLAY_CHARGE_STREAM.add(CODE_NAME_1080p);
        PLAY_CHARGE_STREAM.add(CODE_NAME_1080p3m);
        PLAY_CHARGE_STREAM.add(CODE_NAME_1080p6m);
        PLAY_CHARGE_STREAM.add(CODE_NAME_2K);
        PLAY_CHARGE_STREAM.add(CODE_NAME_2K_H265);
        PLAY_CHARGE_STREAM.add(CODE_NAME_4K);
        PLAY_CHARGE_STREAM.add(CODE_NAME_3d720p);
        PLAY_CHARGE_STREAM.add(CODE_NAME_3d1080p);
        PLAY_CHARGE_STREAM.add(CODE_NAME_3d1080p6M);
        PLAY_CHARGE_STREAM.add(CODE_NAME_DOLBY_800);
        PLAY_CHARGE_STREAM.add(CODE_NAME_DOLBY_1300);
        PLAY_CHARGE_STREAM.add(CODE_NAME_DOLBY_720p);
        PLAY_CHARGE_STREAM.add(CODE_NAME_DOLBY_1080p);
        PLAY_CHARGE_STREAM.add(CODE_NAME_DOLBY_1080p6m);
        PLAY_CHARGE_STREAM.add(CODE_NAME_DOLBY_2K);
        PLAY_CHARGE_STREAM.add(CODE_NAME_DOLBY_4K);
        PLAY_CHARGE_STREAM.add(CODE_NAME_DTS_800);
        PLAY_CHARGE_STREAM.add(CODE_NAME_DTS_1300);
        PLAY_CHARGE_STREAM.add(CODE_NAME_DTS_720p);
        PLAY_CHARGE_STREAM.add(CODE_NAME_DTS_1080p);
        PLAY_CHARGE_STREAM.add(CODE_NAME_DTS_1080p3m);
        PLAY_CHARGE_STREAM.add(CODE_NAME_DTS_1080p6m);
        PLAY_CHARGE_STREAM.add(CODE_NAME_DTS_2K);
        PLAY_CHARGE_STREAM.add(CODE_NAME_DTS_2K_H265);
        PLAY_CHARGE_STREAM.add(CODE_NAME_DTS_4K);

        STREAM_CODE_NAME_MAP.put(CODE_NAME_180, "极速");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_350, "流畅");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_800, "标清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_1000, "标清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_1300, "高清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_720p, "超清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_1080p, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_1080p3m, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_1080p6m, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_2K, "2K");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_2K_H265, "2K");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_4K, "4K");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_3d1080p, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_3d720p, "超清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_3d1080p6M, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_800, "标清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_1300, "高清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_720p, "超清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_1080p, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_1080p6m, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_2K, "2K");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DOLBY_4K, "4K");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DTS_800, "标清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DTS_1300, "高清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DTS_720p, "超清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DTS_1080p, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DTS_1080p3m, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DTS_1080p6m, "1080P");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DTS_2K, "2K");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DTS_2K_H265, "2K");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_DTS_4K, "4K");
        // STREAM_CODE_NAME_MAP.put(CODE_NAME_360, "VR");
        // STREAM_CODE_NAME_MAP.put(CODE_NAME_180_360, "极速");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_350_360, "流畅");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_800_360, "标清");
        STREAM_CODE_NAME_MAP.put(CODE_NAME_1300_360, "高清");
        // STREAM_CODE_NAME_MAP.put(CODE_NAME_720p_360, "超清");
        // STREAM_CODE_NAME_MAP.put(CODE_NAME_1080p_360, "1080P");
        // STREAM_CODE_NAME_MAP.put(CODE_NAME_2k_360, "2K");
        // STREAM_CODE_NAME_MAP.put(CODE_NAME_4k_360, "4K");
        // STREAM_CODE_NAME_MAP.put(CODE_NAME_800_360_h265, "标清");
        // STREAM_CODE_NAME_MAP.put(CODE_NAME_1300_360_h265, "高清");
        // STREAM_CODE_NAME_MAP.put(CODE_NAME_720p_360_h265, "超清");
        // STREAM_CODE_NAME_MAP.put(CODE_NAME_1080p_360_h265, "1080P");
        // STREAM_CODE_NAME_MAP.put(CODE_NAME_2k_360_h265, "2K");
        // STREAM_CODE_NAME_MAP.put(CODE_NAME_4k_360_h265, "4K");

        PLAY_THEATRE_STREAM_CODE2ORDER.put(CODE_NAME_DOLBY_800, 0);
        PLAY_THEATRE_STREAM_CODE2ORDER.put(CODE_NAME_DOLBY_1300, 1);
        PLAY_THEATRE_STREAM_CODE2ORDER.put(CODE_NAME_DOLBY_720p, 2);
        PLAY_THEATRE_STREAM_CODE2ORDER.put(CODE_NAME_DOLBY_1080p, 3);
        PLAY_THEATRE_STREAM_CODE2ORDER.put(CODE_NAME_DOLBY_1080p6m, 3);
        PLAY_THEATRE_STREAM_CODE2ORDER.put(CODE_NAME_DOLBY_2K, 4);
        PLAY_THEATRE_STREAM_CODE2ORDER.put(CODE_NAME_DOLBY_4K, 5);
        PLAY_THEATRE_STREAM_CODE2ORDER.put(CODE_NAME_DTS_800, 0);
        PLAY_THEATRE_STREAM_CODE2ORDER.put(CODE_NAME_DTS_1300, 1);
        PLAY_THEATRE_STREAM_CODE2ORDER.put(CODE_NAME_DTS_720p, 2);
        PLAY_THEATRE_STREAM_CODE2ORDER.put(CODE_NAME_DTS_1080p, 3);
        PLAY_THEATRE_STREAM_CODE2ORDER.put(CODE_NAME_DTS_1080p3m, 3);
        PLAY_THEATRE_STREAM_CODE2ORDER.put(CODE_NAME_DTS_1080p6m, 3);
        PLAY_THEATRE_STREAM_CODE2ORDER.put(CODE_NAME_DTS_2K, 4);
        PLAY_THEATRE_STREAM_CODE2ORDER.put(CODE_NAME_DTS_2K_H265, 4);
        PLAY_THEATRE_STREAM_CODE2ORDER.put(CODE_NAME_DTS_4K, 5);

        PLAY_THEATRE_STREAM_ORDER2CODE.put(0, CODE_NAME_DTS_800);
        PLAY_THEATRE_STREAM_ORDER2CODE.put(1, CODE_NAME_DTS_1300);
        PLAY_THEATRE_STREAM_ORDER2CODE.put(2, CODE_NAME_DTS_720p);
        PLAY_THEATRE_STREAM_ORDER2CODE.put(3, CODE_NAME_DTS_1080p);
        PLAY_THEATRE_STREAM_ORDER2CODE.put(4, CODE_NAME_DTS_2K_H265);
        PLAY_THEATRE_STREAM_ORDER2CODE.put(5, CODE_NAME_DTS_4K);

        PLAY_DTS_STREAM.add(CODE_NAME_DTS_800);
        PLAY_DTS_STREAM.add(CODE_NAME_DTS_1300);
        PLAY_DTS_STREAM.add(CODE_NAME_DTS_720p);
        PLAY_DTS_STREAM.add(CODE_NAME_DTS_1080p);
        PLAY_DTS_STREAM.add(CODE_NAME_DTS_1080p3m);
        PLAY_DTS_STREAM.add(CODE_NAME_DTS_1080p6m);
        PLAY_DTS_STREAM.add(CODE_NAME_DTS_2K);
        PLAY_DTS_STREAM.add(CODE_NAME_DTS_2K_H265);
        PLAY_DTS_STREAM.add(CODE_NAME_DTS_4K);

        PLAY_NORMAL_STREAM.add(CODE_NAME_180);
        PLAY_NORMAL_STREAM.add(CODE_NAME_350);
        PLAY_NORMAL_STREAM.add(CODE_NAME_800);
        PLAY_NORMAL_STREAM.add(CODE_NAME_1300);
        PLAY_NORMAL_STREAM.add(CODE_NAME_720p);
        PLAY_NORMAL_STREAM.add(CODE_NAME_1080p);
        PLAY_NORMAL_STREAM.add(CODE_NAME_1080p3m);
        PLAY_NORMAL_STREAM.add(CODE_NAME_1080p6m);
        PLAY_NORMAL_STREAM.add(CODE_NAME_2K);
        PLAY_NORMAL_STREAM.add(CODE_NAME_2K_H265);
        PLAY_NORMAL_STREAM.add(CODE_NAME_4K);

        PLAY_3D_STREAM.add(CODE_NAME_3d720p);
        PLAY_3D_STREAM.add(CODE_NAME_3d1080p);
        PLAY_3D_STREAM.add(CODE_NAME_3d1080p6M);

        PLAY_360_STREAM.add(CODE_NAME_350_360);
        PLAY_360_STREAM.add(CODE_NAME_800_360);
        PLAY_360_STREAM.add(CODE_NAME_1300_360);

        PLAY_DOLBY_STREAM.add(CODE_NAME_DOLBY_800);
        PLAY_DOLBY_STREAM.add(CODE_NAME_DOLBY_1300);
        PLAY_DOLBY_STREAM.add(CODE_NAME_DOLBY_720p);
        PLAY_DOLBY_STREAM.add(CODE_NAME_DOLBY_1080p);
        PLAY_DOLBY_STREAM.add(CODE_NAME_DOLBY_1080p6m);
        PLAY_DOLBY_STREAM.add(CODE_NAME_DOLBY_2K);
        PLAY_DOLBY_STREAM.add(CODE_NAME_DOLBY_4K);

        DEVICE_TERMINAL_PLATFORM.put("1", TERMINAL_PLATFORM_MOBILE);
        DEVICE_TERMINAL_PLATFORM.put("2", TERMINAL_PLATFORM_TV);
        DEVICE_TERMINAL_PLATFORM.put("3", TERMINAL_PLATFORM_PAD);
        DEVICE_TERMINAL_PLATFORM.put("4", TERMINAL_PLATFORM_CAR);

        PLAY_MOBILE_USE_STREAM.add(CODE_NAME_180);
        PLAY_MOBILE_USE_STREAM.add(CODE_NAME_350);
        PLAY_MOBILE_USE_STREAM.add(CODE_NAME_800);
        PLAY_MOBILE_USE_STREAM.add(CODE_NAME_1000);
        PLAY_MOBILE_USE_STREAM.add(CODE_NAME_1300);
        PLAY_MOBILE_USE_STREAM.add(CODE_NAME_720p);
    }

    public static Map<String, Stream> getStreamMap(String sortStream, String locale, String sourceSite) {
        Map<String, Stream> streamMap = new HashMap<String, Stream>();
        String[] streams = sortStream.split("#");
        for (String stream : streams) {
            Stream v = new Stream();
            v.setCode(stream);
            // v.setCanDown(STREAM_CODE_CANDOWN.get(stream));
            // v.setCanPlay(STREAM_CODE_CANPLAY.get(stream));
            if (StringUtils.isNotEmpty(MessageUtils.getMessage(STREAM_CODE_NAME_MAP.get(stream), locale))) {
                v.setName(MessageUtils.getMessage(STREAM_CODE_NAME_MAP.get(stream), locale));
            } else {
                v.setName(STREAM_CODE_NAME_MAP.get(stream));
            }
            // if ("en_us".equalsIgnoreCase(locale) ||
            // "en".equalsIgnoreCase(locale)) {
            // v.setBandWidth(MessageUtils.getMessage("VIDEO.STREAM.BANDWIDTH",
            // locale)
            // + STREAM_CODE_TIP_MAP.get(stream));
            // } else {
            // v.setBandWidth(STREAM_CODE_TIP_MAP.get(stream)
            // + MessageUtils.getMessage("VIDEO.STREAM.BANDWIDTH", locale));
            // }
            Integer ifCharge = StringUtils.equals("US", sourceSite) ? 0 : PLAY_CHARGE_STREAM.contains(stream) ? 1 : 0;
            v.setIfCharge(ifCharge);
            // v.setKbps(STREAM_CODE_KBPS_MAP.get(stream));
            streamMap.put(stream, v);
        }

        return streamMap;
    }
}
