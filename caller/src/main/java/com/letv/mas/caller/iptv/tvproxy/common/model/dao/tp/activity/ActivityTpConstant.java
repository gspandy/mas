package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.activity;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.PropertiesUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ActivityTpConstant {
    /**
     * 观星系统的url
     */
    public static final String ACTIVITY_PROMOTION_URL = ApplicationConstants.GUANXING_PROMOTION_BASE_HOST_URL
            + "/api/v1/promotion?";

    public static final String ACTIVITY_PROMOTION_NO_DATA_URL = ApplicationConstants.GET_STATIC_DEFAULT_DATA_URL;

    public static final String ALBUM_DETAIL_URL = ApplicationConstants.STATIC_ITV_BASE_HOST
            + "/iptv/api/new/video/album/detailandseries/get.json?";

    /**
     * 观星系统后台定义的活动类型（推广活动、广告、推荐内容）
     */
    public static final String ACTIVITY_TYPE_PROMOTION = "promotion";// 推广活动类型

    /*
     * public static final String ACTIVITY_NOLOGIN_DIRECT = "UnVIPnative";//
     * 非登陆直达位
     * public static final String ACTIVITY_LOGIN_DIRECT = "VIPnative";// 登陆直达位
     */

    public static final String ACTIVITY_NOLOGIN_DIRECT = "";// 非登陆直达位

    public static final String ACTIVITY_LOGIN_DIRECT = "";// 登陆直达位

    /**
     * 在观星系统后台维护的运营推广位标识，用于获取对应运营位的活动数据
     */
    // public static final String ACTIVITY_GUANXING_POSITION_TOP =
    // ApplicationUtils
    // .get(ApplicationConstants.ACTIVITY_POSID_TOP);// 顶部导航运营位标识
    // public static final String ACTIVITY_GUANXING_POSITION_BOTTOM =
    // ApplicationUtils
    // .get(ApplicationConstants.ACTIVITY_POSID_BOTTOM);// 下浮层运营位标识
    // public static final String ACTIVITY_GUANXING_POSITION_POPUP =
    // ApplicationUtils
    // .get(ApplicationConstants.ACTIVITY_POSID_POPUP);// 全屏弹窗运营位标识
    //
    // public static final String ACTIVITY_GUANXING_POSITION_MINE1 =
    // ApplicationUtils
    // .get(ApplicationConstants.ACTIVITY_POSID_MINE1);// "我的"页面中间列第1栏运营位标识
    // public static final String ACTIVITY_GUANXING_POSITION_MINE2 =
    // ApplicationUtils
    // .get(ApplicationConstants.ACTIVITY_POSID_MINE2);// "我的"页面中间列第2栏运营位标识
    // public static final String ACTIVITY_GUANXING_POSITION_MINE3 =
    // ApplicationUtils
    // .get(ApplicationConstants.ACTIVITY_POSID_MINE3);// "我的"页面中间列第3栏运营位标识
    // public static final String ACTIVITY_GUANXING_POSITION_MINE4 =
    // ApplicationUtils
    // .get(ApplicationConstants.ACTIVITY_POSID_MINE4);// "我的"页面右边整列运营位标识
    // public static final String ACTIVITY_GUANXING_POSITION_MINE5 =
    // ApplicationUtils
    // .get(ApplicationConstants.ACTIVITY_POSID_MINE5);// "我的"影视会员开通运营位标识
    //
    // public static final String ACTIVITY_GUANXING_POSITION_HOME1 =
    // ApplicationUtils
    // .get(ApplicationConstants.ACTIVITY_POSID_HOME1);// 首页 – 推荐格
    // public static final String ACTIVITY_GUANXING_POSITION_HOME2 =
    // ApplicationUtils
    // .get(ApplicationConstants.ACTIVITY_POSID_HOME2);// 首页 – 电影
    //
    // public static final String ACTIVITY_GUANXING_POSITION_PLAY_QUIT =
    // ApplicationUtils
    // .get(ApplicationConstants.ACTIVITY_POSID_PLAY_PLAY_QUIT);// 播放器 – 退出
    // public static final String ACTIVITY_GUANXING_POSITION_PLAY_TOP_TRY =
    // ApplicationUtils
    // .get(ApplicationConstants.ACTIVITY_POSID_PLAY_TOP_TRY);// 播放器 – 上浮层 – 试看
    // public static final String ACTIVITY_GUANXING_POSITION_PLAY_PAUSE =
    // ApplicationUtils
    // .get(ApplicationConstants.ACTIVITY_POSID_PLAY_PAUSE);// 播放器 – 暂停
    // public static final String ACTIVITY_GUANXING_POSITION_PLAY_JUMP_AD =
    // ApplicationUtils
    // .get(ApplicationConstants.ACTIVITY_POSID_PLAY_JUMP_AD);// 播放器 – 跳过广告
    // public static final String ACTIVITY_GUANXING_POSITION_PLAY_HIGH_STREAM =
    // ApplicationUtils
    // .get(ApplicationConstants.ACTIVITY_POSID_PLAY_HIGH_STREAM);// 播放器 – 高码率

    public static final String ACTIVITY_GUANXING_POSITION_TOP = "AT_Up_Ad";// 顶部导航运营位标识
    public static final String ACTIVITY_GUANXING_POSITION_BOTTOM = "AT_Un_Ad";// 下浮层运营位标识
    public static final String ACTIVITY_GUANXING_POSITION_POPUP = "";// 全屏弹窗运营位标识

    public static final String ACTIVITY_GUANXING_POSITION_MINE1 = "MT_G_Ad1";// "我的"页面中间列第1栏运营位标识
    public static final String ACTIVITY_GUANXING_POSITION_MINE2 = "MT_G_Ad2";// "我的"页面中间列第2栏运营位标识
    public static final String ACTIVITY_GUANXING_POSITION_MINE3 = "MT_G_Ad3";// "我的"页面中间列第3栏运营位标识
    public static final String ACTIVITY_GUANXING_POSITION_MINE4 = "";// "我的"页面右边整列运营位标识
    public static final String ACTIVITY_GUANXING_POSITION_MINE5 = "MT_G_Ac";// "我的"影视会员开通运营位标识

    public static final String ACTIVITY_GUANXING_POSITION_HOME1 = "GT_G_Ad";// 首页
                                                                            // –
                                                                            // 推荐格
    public static final String ACTIVITY_GUANXING_POSITION_HOME2 = "GT_G_Mad";// 首页
                                                                             // –
                                                                             // 电影

    public static final String ACTIVITY_GUANXING_POSITION_DP_VIP_BTN = "Album_vip_btn";// 专辑详情页
                                                                                       // –
                                                                                       // 会员按钮

    public static final String ACTIVITY_GUANXING_POSITION_PLAY_QUIT = "V_Q_FP";// 播放器
                                                                               // –
                                                                               // 退出
    public static final String ACTIVITY_GUANXING_POSITION_PLAY_TOP_TRY = "V_Un_F6";// 播放器
                                                                                   // –
                                                                                   // 上浮层
                                                                                   // –
                                                                                   // 试看
    public static final String ACTIVITY_GUANXING_POSITION_PLAY_PAUSE = "V_S_Ad";// 播放器
                                                                                // –
                                                                                // 暂停
    public static final String ACTIVITY_GUANXING_POSITION_PLAY_JUMP_AD = "V_Ad_Ju";// 播放器
                                                                                   // –
                                                                                   // 跳过广告
    public static final String ACTIVITY_GUANXING_POSITION_PLAY_HIGH_STREAM = "V_Hd_Ju_Ra";// 播放器
                                                                                          // –
                                                                                          // 高码率
    public static final String ACTIVITY_GUANXING_POSITION_NOTIFICATION = "V_No_Ju";// 播放器
                                                                                   // –
                                                                                   // 通知

    public static final String ACTIVITY_GUANXING_POSITION_NOTIFICATION_PINCI = "V_No_Ju_Ra";// 播放器
                                                                                            // –
                                                                                            // 通知-频次

    public static final String ACTIVITY_GUANXING_POSITION_PLAY_PINCI = "V_Ge_Ra";// 通用-频次

    public static final String ACTIVITY_GUANXING_POSITION_NO_KNOW = "Un_Know_Ju";// 其余跳收银台

    public static final String ACTIVITY_GUANXING_POSITION_NEW_NO_KNOW = "New_Un_Know_Ju";// 新版未知来源

    public static final String ACTIVITY_GUANXING_POSITION_KID_NOTIFICATION = "Kid_V_No"; // 儿童TV
                                                                                         // -
                                                                                         // 播放器
                                                                                         // –
                                                                                         // 通知
    public static final String ACTIVITY_GUANXING_POSITION_KID_TOP = "Kid_Up_Nav"; // 儿童TV
                                                                                  // -
                                                                                  // 全局TAB
                                                                                  // -
                                                                                  // 顶部导航
    public static final String ACTIVITY_GUANXING_POSITION_KID_PLAY_TOP_TRY = "Kid_V_Up_Pre";// 儿童TV
                                                                                            // -
                                                                                            // 播放器
                                                                                            // -
                                                                                            // 上浮层
                                                                                            // -
                                                                                            // 试看
    public static final String ACTIVITY_GUANXING_POSITION_KID_PARENT_FOCUS = "Kid_P_Foc"; // 儿童TV
                                                                                          // -
                                                                                          // 父母中心
                                                                                          // -
                                                                                          // 大焦点图
    public static final String ACTIVITY_GUANXING_POSITION_WF_SUBJECT = "Spe_vip_btn";// 瀑布流专题接观星
    public static final String ACTIVITY_GUANXING_POSITION_GT = "GT_G_Foc";// 首页内容营销位

    // public static final String ACTIVITY_GUANXING_POSITION_PLAY_TOP =
    // "PY_TOP";// 新增上浮层会员运营位

    /**
     * 与客户端约定的运营位标识
     */
    public static final String ACTIVITY_CLIENT_POSITION_TOP = "top";// 顶部导航运营位标识
    public static final String ACTIVITY_CLIENT_POSITION_BOTTOM = "bottom";// 下浮层运营位标识
    public static final String ACTIVITY_CLIENT_POSITION_POPUP = "popup";// 全屏弹窗运营位标识

    public static final String ACTIVITY_CLIENT_POSITION_MINE1 = "mine1";// "我的"页面中间列第1栏运营位标识
    public static final String ACTIVITY_CLIENT_POSITION_MINE2 = "mine2";// "我的"页面中间列第2栏运营位标识
    public static final String ACTIVITY_CLIENT_POSITION_MINE3 = "mine3";// "我的"页面中间列第3栏运营位标识
    public static final String ACTIVITY_CLIENT_POSITION_MINE4 = "mine4";// "我的"页面右边整列运营位标识
    public static final String ACTIVITY_CLIENT_POSITION_MINE5 = "mine5";// "我的"页面最左边的影视会员开通运营位标识

    public static final String[] positions_mine = { ACTIVITY_CLIENT_POSITION_MINE1, ACTIVITY_CLIENT_POSITION_MINE2,
            ACTIVITY_CLIENT_POSITION_MINE3 };

    public static final String ACTIVITY_CLIENT_POSITION_HOME1 = "home1";// "首页"推荐格运营位标识
    public static final String ACTIVITY_CLIENT_POSITION_HOME2 = "home2";// "首页"二屏电影栏目下第一位运营位标识

    public static final String ACTIVITY_CLIENT_POSITION_PAUSE = "pause";// 播放器 –
                                                                        // 暂停
    public static final String ACTIVITY_CLIENT_POSITION_QUIT = "quit";// 播放器 –
                                                                      // 退出播放
    public static final String ACTIVITY_CLIENT_POSITION_TOP_TRY = "top_try";// 播放器
                                                                            // –
                                                                            // 上浮层
                                                                            // –
                                                                            // 试看
    public static final String ACTIVITY_CLIENT_POSITION_JUMP_AD = "jump_ad";// 播放器
                                                                            // –
                                                                            // 跳过广告
    public static final String ACTIVITY_CLIENT_POSITION_HIGH_STREAM = "high_stream";// 播放器
                                                                                    // –
                                                                                    // 高码率
    public static final String ACTIVITY_CLIENT_POSITION_NOTIFICATION = "p_notification";// 播放器
                                                                                        // –
                                                                                        // 通知
    public static final String ACTIVITY_CLIENT_POSITION_NOTIFICATION_PINCI = "notification_pc";// 播放器
                                                                                               // –
                                                                                               // 通知
    public static final String ACTIVITY_CLIENT_POSITION_PLAY_PINCI = "play_pc";// 播放器
                                                                               // –
                                                                               // 通知
    public static final String ACTIVITY_CLIENT_POSITION_NO_KNOW = "un_know_ju";// 其余跳收银台
    public static final String ACTIVITY_CLIENT_POSITION_VIP_REMIND = "vip_rd";// 会员通知

    /**
     * 与客户端约定的运营位标识（儿童APP）
     */
    public static final String ACTIVITY_KID_CLIENT_POSITION_TOP = "kid_top"; // 儿童APP顶部导航运营位标识
    public static final String ACTIVITY_KID_CLIENT_POSITION_NOTIFICATION = "kid_p_notification"; // 儿童APP播放器
                                                                                                 // -
                                                                                                 // 通知
    public static final String ACTIVITY_KID_CLIENT_POSITION_TOP_TRY = "kid_top_try"; // 儿童APP播放器
                                                                                     // -
                                                                                     // 上浮层
                                                                                     // -
                                                                                     // 试看
    public static final String ACTIVITY_KID_CLIENT_POSITION_PARENT_FOCUS = "kid_parent_focus"; // 儿童APP播放器
                                                                                               // -
                                                                                               // 父母中心
                                                                                               // -
                                                                                               // 大焦点图

    public static final String ACTIVITY_CLIENT_POSITION_WF_SUBJECT = "wf_subject";// 瀑布流专题接观星
    public static final String ACTIVITY_CLIENT_POSITION_GT = "home3";// 首页内容营销位
    public static final String ACTIVITY_CLIENT_POSITION_PLAY_TOP = "play_top";// 新增上浮层会员运营位

    /**
     * 在观星系统后台定义的模板类型
     */
    public static final String ACTIVITY_TEMPLATE_TOP_TIPS = "1";// 观星系统定义的模板类型，标题+按钮
    public static final String ACTIVITY_TEMPLATE_BOTTOM = "2";// 观星系统定义的模板类型，标题+副标题+按钮+图片
    public static final String ACTIVITY_TEMPLATE_POPUP = "3";// 观星系统定义的模板类型，标题+副标题+按钮1+按钮2+图片
    public static final String ACTIVITY_TEMPLATE_MINE = "4";// 观星系统定义的模板类型，图片
    public static final String ACTIVITY_TEMPLATE_PLAY_GUIDE = "5";// 观星系统定义的模板类型，标题+副标题+按钮1+按钮2+图片
    public static final String ACTIVITY_TEMPLATE_PLAY_PAUSE = "6";// 观星系统定义的模板类型，按钮+图片
    public static final String ACTIVITY_TEMPLATE_PLAY_TOP_LAYER = "7";// 观星系统定义的模板类型，标题+按钮

    public static final int GUANXING_DATA_TYPE_H5 = 1;// 观星系统定义的跳转类型，1表示h5
    public static final int GUANXING_DATA_TYPE_APP = 21;// 观星系统定义的跳转类型，21表示打洞到超级影视app
    public static final int GUANXING_DATA_TYPE_LE_FAN_APP = 13;// 观星系统定义的跳转类型，13表示打洞到乐范app
    public static final int GUANXING_DATA_TYPE_PAY_APP = 4;// 观星系统定义的跳转类型，4表示打洞到购物app
    public static final int GUANXING_DATA_TYPE_LAUNCHER_APP = 22;// 观星系统定义的跳转类型，22表示打洞到桌面

    public static final String MINE_COLUMN_NAME_LEFT = "left";// “我的”页面列名-左边列
    public static final String MINE_COLUMN_NAME_MIDDLE = "middle";// “我的”页面列名-中间列
    public static final String MINE_COLUMN_NAME_RIGHT = "right";// “我的”页面列名-右边列

    public static final int MINE_DATA_SOURCE_ACTIVITY = 1;// 表示这是一个活动数据
    public static final int MINE_DATA_SOURCE_ALBUM = 2;// 表示这是4个专辑数据
    private static final String ACTIVITY_MINE_DEFAULT_IMG_KEY = "activity.mine.default.img";
    public static final String ACTIVITY_MINE_DEFAULT_IMG = ApplicationUtils.get(ACTIVITY_MINE_DEFAULT_IMG_KEY);// 活动位无活动时默认图片

    /**
     * 观星定义的运营位标识与代理层和客户端约定的运营位标识映射
     */
    public static final Map<String, String> guanXing2ClientPosid = new HashMap<String, String>();

    public static final Map<String, String> client2guanXingPosid = new HashMap<String, String>();

    /**
     * guanxing.properties里第一位的配置项转二进制后子配置项的位置
     */
    public static final int SUB_CONFIG_DEFAULT = 1;// 取二进制从右数第一位

    public static String ALL_POSIDS = ACTIVITY_GUANXING_POSITION_TOP + "," + ACTIVITY_GUANXING_POSITION_BOTTOM + ","
            + ACTIVITY_GUANXING_POSITION_MINE1 + "," + ACTIVITY_GUANXING_POSITION_MINE2 + ","
            + ACTIVITY_GUANXING_POSITION_MINE3 + "," + ACTIVITY_GUANXING_POSITION_MINE5 + ","
            + ACTIVITY_GUANXING_POSITION_HOME1 + "," + ACTIVITY_GUANXING_POSITION_HOME2 + ","
            + ACTIVITY_GUANXING_POSITION_PLAY_QUIT + "," + ACTIVITY_GUANXING_POSITION_PLAY_TOP_TRY + ","
            + ACTIVITY_GUANXING_POSITION_PLAY_PAUSE + "," + ACTIVITY_GUANXING_POSITION_PLAY_JUMP_AD + ","
            + ACTIVITY_GUANXING_POSITION_PLAY_HIGH_STREAM + "," + ACTIVITY_GUANXING_POSITION_NO_KNOW + ","
            + ACTIVITY_GUANXING_POSITION_DP_VIP_BTN + "," + ACTIVITY_GUANXING_POSITION_GT + ","
            + ACTIVITY_GUANXING_POSITION_WF_SUBJECT + "," + ACTIVITY_GUANXING_POSITION_DP_VIP_BTN;
    // + "," + ACTIVITY_GUANXING_POSITION_PLAY_TOP;

    public static String DEFAULT_POSIDS = ACTIVITY_GUANXING_POSITION_TOP + "," + ACTIVITY_GUANXING_POSITION_HOME1 + ","
            + ACTIVITY_GUANXING_POSITION_HOME2 + "," + ACTIVITY_GUANXING_POSITION_MINE1 + ","
            + ACTIVITY_GUANXING_POSITION_MINE2 + "," + ACTIVITY_GUANXING_POSITION_MINE3 + ","
            + ACTIVITY_GUANXING_POSITION_MINE5 + "," + ACTIVITY_GUANXING_POSITION_PLAY_JUMP_AD + ","
            + ACTIVITY_GUANXING_POSITION_PLAY_HIGH_STREAM + "," + ACTIVITY_GUANXING_POSITION_PLAY_PAUSE + ","
            + ACTIVITY_GUANXING_POSITION_PLAY_TOP_TRY
            // + "," + ACTIVITY_GUANXING_POSITION_NO_KNOW;
            + "," + ACTIVITY_GUANXING_POSITION_DP_VIP_BTN + "," + ACTIVITY_GUANXING_POSITION_KID_TOP + ","
            + ACTIVITY_GUANXING_POSITION_KID_PLAY_TOP_TRY + "," + ACTIVITY_GUANXING_POSITION_KID_PARENT_FOCUS;

    public static String ALL_KID_POSIDS = ACTIVITY_GUANXING_POSITION_KID_NOTIFICATION + ","
            + ACTIVITY_GUANXING_POSITION_KID_PARENT_FOCUS + "," + ACTIVITY_GUANXING_POSITION_KID_PLAY_TOP_TRY + ","
            + ACTIVITY_GUANXING_POSITION_KID_TOP;

    static {
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_TOP, ACTIVITY_CLIENT_POSITION_TOP);
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_BOTTOM, ACTIVITY_CLIENT_POSITION_BOTTOM);
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_POPUP, ACTIVITY_CLIENT_POSITION_POPUP);

        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_MINE1, ACTIVITY_CLIENT_POSITION_MINE1);
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_MINE2, ACTIVITY_CLIENT_POSITION_MINE2);
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_MINE3, ACTIVITY_CLIENT_POSITION_MINE3);
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_MINE4, ACTIVITY_CLIENT_POSITION_MINE4);
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_MINE5, ACTIVITY_CLIENT_POSITION_MINE5);

        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_HOME1, ACTIVITY_CLIENT_POSITION_HOME1);
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_HOME2, ACTIVITY_CLIENT_POSITION_HOME2);

        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_DP_VIP_BTN, ACTIVITY_GUANXING_POSITION_DP_VIP_BTN);

        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_PLAY_QUIT, ACTIVITY_CLIENT_POSITION_QUIT);
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_PLAY_TOP_TRY, ACTIVITY_CLIENT_POSITION_TOP_TRY);
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_PLAY_PAUSE, ACTIVITY_CLIENT_POSITION_PAUSE);
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_PLAY_JUMP_AD, ACTIVITY_CLIENT_POSITION_JUMP_AD);
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_PLAY_HIGH_STREAM, ACTIVITY_CLIENT_POSITION_HIGH_STREAM);
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_NOTIFICATION, ACTIVITY_CLIENT_POSITION_NOTIFICATION);
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_NOTIFICATION_PINCI,
                ACTIVITY_CLIENT_POSITION_NOTIFICATION_PINCI);
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_PLAY_PINCI, ACTIVITY_CLIENT_POSITION_PLAY_PINCI);
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_NO_KNOW, ACTIVITY_CLIENT_POSITION_NO_KNOW);
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_WF_SUBJECT, ACTIVITY_CLIENT_POSITION_WF_SUBJECT);
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_GT, ACTIVITY_CLIENT_POSITION_GT);
        // guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_PLAY_TOP,
        // ACTIVITY_CLIENT_POSITION_PLAY_TOP);

        // 以下是儿童APP
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_KID_TOP, ACTIVITY_KID_CLIENT_POSITION_TOP);
        guanXing2ClientPosid
                .put(ACTIVITY_GUANXING_POSITION_KID_NOTIFICATION, ACTIVITY_KID_CLIENT_POSITION_NOTIFICATION);
        guanXing2ClientPosid.put(ACTIVITY_GUANXING_POSITION_KID_PLAY_TOP_TRY, ACTIVITY_KID_CLIENT_POSITION_TOP_TRY);
        guanXing2ClientPosid
                .put(ACTIVITY_GUANXING_POSITION_KID_PARENT_FOCUS, ACTIVITY_KID_CLIENT_POSITION_PARENT_FOCUS);

        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_TOP, ACTIVITY_GUANXING_POSITION_TOP);
        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_BOTTOM, ACTIVITY_GUANXING_POSITION_BOTTOM);
        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_POPUP, ACTIVITY_GUANXING_POSITION_POPUP);

        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_MINE1, ACTIVITY_GUANXING_POSITION_MINE1);
        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_MINE2, ACTIVITY_GUANXING_POSITION_MINE2);
        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_MINE3, ACTIVITY_GUANXING_POSITION_MINE3);
        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_MINE4, ACTIVITY_GUANXING_POSITION_MINE4);
        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_MINE5, ACTIVITY_GUANXING_POSITION_MINE5);

        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_HOME1, ACTIVITY_GUANXING_POSITION_HOME1);
        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_HOME2, ACTIVITY_GUANXING_POSITION_HOME2);

        client2guanXingPosid.put(ACTIVITY_GUANXING_POSITION_DP_VIP_BTN, ACTIVITY_GUANXING_POSITION_DP_VIP_BTN);

        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_QUIT, ACTIVITY_GUANXING_POSITION_PLAY_QUIT);
        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_TOP_TRY, ACTIVITY_GUANXING_POSITION_PLAY_TOP_TRY);
        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_PAUSE, ACTIVITY_GUANXING_POSITION_PLAY_PAUSE);
        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_JUMP_AD, ACTIVITY_GUANXING_POSITION_PLAY_JUMP_AD);
        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_HIGH_STREAM, ACTIVITY_GUANXING_POSITION_PLAY_HIGH_STREAM);
        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_NOTIFICATION, ACTIVITY_GUANXING_POSITION_NOTIFICATION);
        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_NOTIFICATION_PINCI,
                ACTIVITY_GUANXING_POSITION_NOTIFICATION_PINCI);
        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_PLAY_PINCI, ACTIVITY_GUANXING_POSITION_PLAY_PINCI);
        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_NO_KNOW, ACTIVITY_GUANXING_POSITION_NO_KNOW);
        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_WF_SUBJECT, ACTIVITY_GUANXING_POSITION_WF_SUBJECT);
        client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_GT, ACTIVITY_GUANXING_POSITION_GT);
        // client2guanXingPosid.put(ACTIVITY_CLIENT_POSITION_PLAY_TOP,
        // ACTIVITY_GUANXING_POSITION_PLAY_TOP);
        // 以下是儿童APP
        client2guanXingPosid.put(ACTIVITY_KID_CLIENT_POSITION_TOP, ACTIVITY_GUANXING_POSITION_KID_TOP);
        client2guanXingPosid
                .put(ACTIVITY_KID_CLIENT_POSITION_NOTIFICATION, ACTIVITY_GUANXING_POSITION_KID_NOTIFICATION);
        client2guanXingPosid.put(ACTIVITY_KID_CLIENT_POSITION_TOP_TRY, ACTIVITY_GUANXING_POSITION_KID_PLAY_TOP_TRY);
        client2guanXingPosid
                .put(ACTIVITY_KID_CLIENT_POSITION_PARENT_FOCUS, ACTIVITY_GUANXING_POSITION_KID_PARENT_FOCUS);
        //initGuanxingExtMap();
    }

    /**
     * 根据观星系统定义的运营位标识获取代理层与客户端约定的运营位标识
     * @param posid
     * @return
     */
    public static String getPositionByPosid(String posid, CommonParam commonParam) {
        if (guanXing2ClientPosid.containsKey(posid)) {
            return guanXing2ClientPosid.get(posid);
        }

        return null;
    }

    /**
     * 根据观星系统定义的运营位标识获取代理层与客户端约定的运营位标识
     * @param position
     * @return
     */
    public static String getPosidByPosition(String position, CommonParam commonParam) {
        if (client2guanXingPosid.containsKey(position)) {
            return client2guanXingPosid.get(position);
        }
        return null;
    }

    /**
     * 获得超级影视所有观星位置
     * @return
     */
    public static String getAllPosition(CommonParam commonParam) {
        return ALL_POSIDS;
    }

    /**
     * 获得儿童应用所有观星位置
     * @return
     */
    public static String getAllKidPosition(CommonParam commonParam) {
        return ALL_KID_POSIDS;
    }

    public static boolean isSupportFamilyCashier(CommonParam commonParam) {
        return true;
    }

    public static void initGuanxingExtMap() {
        loadClassPath("guanxing.properties");
    }

    private static void loadClassPath(String classFilePath) {
        try {
            Properties mProperties = PropertiesUtil.getInstance().getPropertiesByClassPath("/" + classFilePath);
            if (mProperties != null) {
                Set<String> keys = mProperties.stringPropertyNames();
                if (keys != null) {
                    for (String key : keys) {
                        changeValue(key,mProperties.getProperty(key));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(classFilePath + " is not found!", e);
        }
    }
    public static void changeValue(String key, String value) {
        if (StringUtil.isNotBlank(value)) {
            String temp[] = key.split("_");
            if (temp == null || temp.length == 0) {
                return;
            }
            int config = 0;
            if (StringUtil.isInteger(temp[0])) {
                config = StringUtil.toInteger(temp[0], 0);
                key = key.replace(config + "_", "");
            }
            changeValue(config, key, value);
        }
    }
    private static void changeValue(int config, String key, String value) {
        if (StringUtil.isBlank(key) || StringUtil.isBlank(value)) {
            return;
        }
        client2guanXingPosid.put(key, value);
        guanXing2ClientPosid.put(value, key);
        if (key.startsWith("kid_")) {
            if (StringUtil.isNotBlank(ALL_KID_POSIDS) && !ALL_KID_POSIDS.contains(value)) {
                ALL_KID_POSIDS = ALL_KID_POSIDS + "," + value;
            }
        } else {
            if (StringUtil.isNotBlank(ALL_POSIDS) && !ALL_POSIDS.contains(value)) {
                ALL_POSIDS = ALL_POSIDS + "," + value;
            }
        }

        if (config > 0) {
            boolean isDefault = getSubConfigV2(config, SUB_CONFIG_DEFAULT);
            if (isDefault) {
                if (StringUtil.isNotBlank(DEFAULT_POSIDS)) {
                    DEFAULT_POSIDS = DEFAULT_POSIDS + "," + value;
                }
            }
        }
    }

    /**
     * 十进制转8位二进制，取pos位置的开关
     * @return
     */
    private static int getSubConfig(int config, String pos) {
        try {
            if (StringUtil.isBlank(pos)) {
                return 0;
            }
            String[] temp = pos.split("_");
            if (temp == null || temp.length != 2) {
                return 0;
            }
            int right = StringUtil.toInteger(temp[0]);
            if (right <= 0) {
                return 0;
            }
            right = right - 1;
            int size = StringUtil.toInteger(temp[1]);
            size = ((int) Math.pow(2, size)) - 1;
            int result = config >> right & size;
            return result;
        } catch (Exception e) {

        }
        return 0;
    }

    private static boolean getSubConfigV2(int config, int pos) {
        try {
            if (pos <= 0) {
                return false;
            }
            int temp = (int) Math.pow(2, (pos - 1));// 2的pos-1次方
            return ((config & temp) == temp);
        } catch (Exception e) {

        }
        return false;
    }

    private static Properties loadGXLocalPath(String filePath) {
        Properties tmp = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            tmp.load(inputStream);
        } catch (Exception e) {

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                }
            }
        }
        return tmp;
    }

    public static final String GET_DEFAULT_DATA_MAC = ApplicationUtils.get(ApplicationConstants.GET_DEFAULT_DATA_MAC);

    public static final String GET_DEFAULT_DATA_UID = ApplicationUtils.get(ApplicationConstants.GET_DEFAULT_DATA_UID);

    public static final String SET_DEFAULT_DATA_URL = ApplicationUtils.get(ApplicationConstants.GET_DEFAULT_DATA_URL);

    public static final String SET_CASHIER_DATA_URL = ApplicationUtils.get(ApplicationConstants.GET_CASHIER_DATA_URL);

    /**
     * 正式上线时要去掉的
     */
    public static final String GET_GUANXING_DATA_ERROR_URL = ApplicationConstants.GET_GUANXING_DATA_ERROR_URL;
    public static final String GET_GUANXING_DATA_ERROR_MAC = ApplicationConstants.GET_GUANXING_DATA_ERROR_MAC;

}
