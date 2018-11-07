package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LocaleConstant;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class VipTpConstant {

    /**
     * 第三方接口中，数组类型JSON数据默认分隔符
     */
    public final static String COMMON_SPILT_SEPARATOR = ",";

    /**
     * 院选定义终端代码
     */
    public final static String TERMINAL_TV = "141007";

    /**
     * 第三方接口响应信息中的状态吗，通常0表示失败，1表示成功，但是有例外的情况
     */
    public final static String TP_STATTUS_CODE_0 = "0";
    public final static String TP_STATTUS_CODE_1 = "1";

    /**
     * 调用查询用户充值记录接口认证秘钥
     */
    /**
     * api.zhifu接口通用sign key
     */
    public final static String BOSS_API_ZHIFU_SIGN_KEY = "16ec384124bcdfee2a90ecc9267373bc";

    /**
     * 下单接口sign值计算key
     */
    public static final String OPEN_ORDER_SIGN_KEY = "902ddc5998aadfe579b9f12de08c3b57";

    /**
     * 通用版支付SDK签名秘钥（线上测试不一致，所以写在配置文件里）
     * 线上：815a3fe6c4addaba1e72d11c4285844b
     * 测试：e68303cc28e7bf5b6dc410fb39fbd324
     */
    private static final String VIP_LETV_COMMON_ZHIFU = "vip.letv.common.zhifu.sign.key";
    public static final String VIP_LETV_COMMON_ZHIFU_SIGN_KEY = ApplicationUtils.get(VIP_LETV_COMMON_ZHIFU,
            "815a3fe6c4addaba1e72d11c4285844b");

    /**
     * 获取支付二维码通用参数
     */
    public static final int DEFAULT_PRODUCTID = 0;
    public static final int DEFAULT_PID = 0;
    public static final String DEFAULT_CORDERID = "0";
    public static final String DEFAULT_COMPANYID = "1";
    public static final String DEFAULT_DEPTID = "111";
    public static final String DEFAULT_BACKURL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST + "/letv/newPay.ldo";
    public static final String DEFAULT_FRONTURL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST;
    public static final int DEFAULT_PRODUCTNUM = 1;

    /**
     * 支付中心下单和生成二维码所需的密钥
     */
    public static final String BOSS_API_ZHIFU_COMMON_SING_KEY = "ce0806981627d00d4b96beb051a2b629";

    /**
     * 机卡绑定需求第三方接口sign计算key
     */
    public static final String DEVICE_BIND_SIGN_KEY = "@*#&*&^&%##oihoi^&%*))@#(*$@$_)+|}{~?><:";

    /**
     * signKey for HK get pay token
     */
    public final static String SIGN_KEY_HK = "bd7a26cf26baf8ac1d4cfb3c3fcd06d7";

    /**
     * signKey for US get pay token
     */
    public final static String SIGN_KEY_US = "c99exfgvpaybacdsfds3szb7e68238iiu";

    /**
     * 机卡绑定TV版参数deviceType，设备类型，电视传固定值1，手机传固定值2，盒子固定传3
     */
    // tv terminal type
    public static final int DEVICE_BIND_DEVICE_TYPE_TV = 1;
    // mobileterminal type
    public static final int DEVICE_BIND_DEVICE_TYPE_MOBILE = 2;
    // box terminal type
    public static final int DEVICE_BIND_DEVICE_TYPE_LETV_BOX = 3;

    /**
     *
     */
    public static final String PAY_CENTER_KEY = "LETVletvPayPlate";

    /**
     * 媒介类型 (专辑还是视频)
     */
    public static final String MEDIA_TYPE_ALBUM = "a"; // 专辑
    public static final String MEDIA_TYPE_VIDEO = "v"; // 视频

    /**
     * 产品类型
     */
    public static final int PRODUCT_TYPE_LETV_POINT = 0; // 乐点
    public static final int PRODUCT_TYPE_SINGLE_FILM = 1; // 单片
    public static final int PRODUCT_TYPE_SERVICE_PACKAGE = 2;// 套餐产品包
    /*
     * 自定义的直播类型，实际上，从订单类型（ORDER_TYPE）来说，均属于单点订单（ORDER_TYPE_0）
     */
    public static final int PRODUCT_TYPE_COMSUMR_LIVE = 3;

    /**
     * 支付中心定义的终端编号，141001--Web，141002--PC客户端，141003--手机，141004--移动领先版测试使用，141005
     * --Pad，1410006--M站， 141007--TV，141009--超级手机
     */
    public final static String PRICE_ZHIFU_TERMINAL_MOBILE_DEFAULT = "141004";
    public final static String PRICE_ZHIFU_TERMINAL_MOBILE = ApplicationUtils.get(
            ApplicationConstants.VIP_BOSS_PRICE_ZHIFU_TERMINAL_SUPER_PHONE, PRICE_ZHIFU_TERMINAL_MOBILE_DEFAULT);
    public final static String PRICE_ZHIFU_TERMINAL_TV = "141007";

    /**
     * 会员类型
     */
    public static final int SVIP_NOT_NUMBER = 0; // 非会员，适用于单点
    public static final int SVIP_PC = 1; // PC端会员，普通会员
    public static final int SVIP_TV = 9; // TV端会员，高级会员

    /**
     * 支付渠道 2014-09-29从支付中心更新TV版使用支付渠道数据
     */
    public static final int PAYMENT_CHANNEL_NONE = 0; // 不传支付渠道，默认0
    public static final int PAYMENT_CHANNEL_ALIPAY = 5; // 支付宝二维码
    public static final int PAYMENT_CHANNEL_TV_ONE_YUAN = 6; // TV一元续费
    public static final int PAYMENT_CHANNEL_ALI_TVBOX = 11; // 阿里TV盒子
    public static final int PAYMENT_CHANNEL_CIBN_ALI = 13; // CIBN版本，2014-10-15
    public static final int PAYMENT_CHANNEL_LAKALA = 16; // 拉卡拉支付
    public static final int PAYMENT_CHANNEL_CIBN_WEIXIN = 20; // 微信支付CIBN版本
    // 历史遗留错误，之前标识错误的代码，以为是“支付宝”，实际为非TV版使用的微信支付，这里保留，为做兼容
    public static final int PAYMENT_CHANNEL_ALIPAY_DEPRECATED = 23; //
    public static final int PAYMENT_CHANNEL_WEIXIN = 24; // 支付渠道id，微信支付
    public static final int PAYMENT_CHANNEL_PHONEPAY_UNICOM = 31; // 手机支付-联通
    public static final int PAYMENT_CHANNEL_LETVPOINT = 33; // 乐点支付
    public static final int PAYMENT_CHANNEL_PHONEPAY_MOBILE = 34; // 手机支付-移动
    public static final int PAYMENT_CHANNEL_PHONEPAY_TELECOM = 35; // 手机支付-电信
    public static final int PAYMENT_CHANNEL_PAYPAL = 39; // paypal支付
    public static final int PAYMENT_CHANNEL_YEEPAYUNBIND = 41; // yeepay未绑卡时下单支付
    public static final int PAYMENT_CHANNEL_YEEPAYBIND = 42; // yeepay绑卡支付支付
    public static final int PAYMENT_CHANNEL_HUASHU = 46;// 华数支付渠道
    public static final int PAYMENT_CHANNEL_ONE_KEY_QUICK_PAY = 49; // 一键支付

    /**
     * 消费中心--终端类型(数字：1:VIP 2: WWW 3:乐视TV 4: 手机 5: 海信TV) 废弃不用
     */
    public static final int TERMINAL_TYPE_VIP = 1; // PC-VIP
    public static final int TERMINAL_TYPE_WWW = 2; // www，即PC
    public static final int TERMINAL_TYPE_TV_98 = 3; // tv
    public static final int TERMINAL_TYPE_MOBILE = 4; // 手机
    public static final int TERMINAL_TYPE_HISENSE_TV = 5; // 院线-PC
    public static final int TERMINAL_TYPE_COOPERATE_TV = 6; // 合作TV
    public static final int TERMINAL_TYPE_COOPERATE_CNTV = 7; // CNTV
    public static final int TERMINAL_TYPE_TV2 = 9; // TV2.0

    /**
     * 订单来源，也即终端类型
     */
    public static final String ORDER_FROM_NULL = "0"; // 非会员
    public static final String ORDER_FROM_PC_VIP = "1"; // 1 VIP 网页
    public static final String ORDER_FROM_PC = "2"; // 2 WWW 网页pc
    public static final String ORDER_FROM_TV = "3"; // 3 乐视TV tvCloud
    public static final String ORDER_FROM_MOBILE = "4"; // 4 手机
    public static final String ORDER_FROM_NETWORK_Cinemas = "5"; // 5 网络院线
    public static final String ORDER_FROM_TV_48 = "6"; // 6 tv对外合作电视厂商
    public static final String ORDER_FROM_CNTV = "7"; // 7 CNTV未来电视
    public static final String ORDER_FROM_TV2 = "9"; // 9 tv2.0
    public static final String ORDER_FROM_DEVICEBIND = "13"; // 机卡绑定合作的渠道

    /**
     * 消费中心--子终端类型，客户端或者电视的型号
     */
    public final static int SUB_ORDER_FROM_DEFAULT = -1; // 默认值
    public final static int SUB_ORDER_FROM_0 = 0; // 海信
    public final static int SUB_ORDER_FROM_1 = 1; // 海尔
    public final static int SUB_ORDER_FROM_2 = 2; // 创维
    public final static int SUB_ORDER_FROM_3 = 3; // TCL
    public final static int SUB_ORDER_FROM_4 = 4; // 康佳
    public final static int SUB_ORDER_FROM_5 = 5; // 清华同方
    public final static int SUB_ORDER_FROM_7 = 7; // CNTV与乐视充值打通
    public final static int SUB_ORDER_FROM_8 = 8; // 长虹
    public final static int SUB_ORDER_FROM_9 = 9; // panda
    public final static int SUB_ORDER_FROM_10 = 10; // 小米
    public final static int SUB_ORDER_FROM_11 = 11; // tpv
    public final static int SUB_ORDER_FROM_12 = 12; // CIBN与乐视充值打通，国广播控
    public final static int SUB_ORDER_FROM_30 = 30; // pc Client
    public final static int SUB_ORDER_FROM_101 = 101; // 华数
    public final static int SUB_ORDER_FROM_102 = 102; // 多乐
    public final static int SUB_ORDER_FROM_103 = 103; // VST
    public final static int SUB_ORDER_FROM_104 = 104; // 网视
    public final static int SUB_ORDER_FROM_105 = 105; // 当贝
    public final static int SUB_ORDER_FROM_106 = 106; // 奇珀
    public final static int SUB_ORDER_FROM_107 = 107; // 沙发
    public final static int SUB_ORDER_FROM_108 = 108; // 爱家

    // 以下为自有设备
    public final static int SUB_ORDER_FROM_X60 = 50; // X60
    public final static int SUB_ORDER_FROM_S50 = 51; // S50
    public final static int SUB_ORDER_FROM_S40 = 52; // S40
    public final static int SUB_ORDER_FROM_S10 = 53; // S10
    public final static int SUB_ORDER_FROM_T1 = 54; // ST1
    public final static int SUB_ORDER_FROM_C1 = 55; // C1
    public final static int SUB_ORDER_FROM_C1S = 56; // C1S
    public final static int SUB_ORDER_FROM_C1A = 57; // C1a
    public final static int SUB_ORDER_FROM_C1B = 58; // C1b
    public final static int SUB_ORDER_FROM_T1S = 59; // T1S
    public final static int SUB_ORDER_FROM_NEWC1S = 60; // NEWC1S
    public final static int SUB_ORDER_FROM_C2 = 61; // C2
    public final static int SUB_ORDER_FROM_MAX70 = 62; // Max70
    public final static int SUB_ORDER_FROM_S250F = 63; // S250F
    public final static int SUB_ORDER_FROM_S250U = 64; // S250U
    public final static int SUB_ORDER_FROM_S240F = 65; // S40AIR
    public final static int SUB_ORDER_FROM_MXYTV = 66; // 茂鑫源
    public final static int SUB_ORDER_FROM_X40 = 67; // X40

    /**
     * 订单类型，即套餐id，现使用int型，与支付中心保持一致（2014-11-12）
     */
    public final static int ORDER_TYPE_0 = 0;// 单片点播--电影
    public final static int ORDER_TYPE_1 = 1;// 单片点播--电视剧
    public final static int ORDER_TYPE_2 = 2;// 包月
    public final static int ORDER_TYPE_3 = 3;// 包季
    public final static int ORDER_TYPE_4 = 4;// 包半年
    public final static int ORDER_TYPE_5 = 5;// 包年
    public final static int ORDER_TYPE_6 = 6;// 包3年
    public final static int ORDER_TYPE_7 = 7;// 用户中心红包赠送，包两年
    public final static int ORDER_TYPE_8 = 8;// 影片打包购买，包六年
    public final static int ORDER_TYPE_9 = 9;// 会员升级，tv做活动，
    public final static int ORDER_TYPE_40 = 40;// 七天高级vip免费
    public final static int ORDER_TYPE_41 = 41;// 1元高级vip包月
    public final static int ORDER_TYPE_42 = 42;// 自动续费包月
    public final static int ORDER_TYPE_43 = 43; // 免费七天
    public final static int ORDER_TYPE_44 = 44;// 一月免费
    public final static int ORDER_TYPE_45 = 45;// 一天免费
    public final static int ORDER_TYPE_46 = 46;// 免费包季
    public final static int ORDER_TYPE_100 = 1000;// 免费按天赠送
    public final static int ORDER_TYPE_1001 = 1001; // 直播内容点播

    /**
     * 订单状态，0待付, -1 (单片)点播过期,1(单片)点播成功,-2包月过期,2包月中,3直播支付成功
     */
    public static final String ORDER_STATUS_NOT_PAY = "0"; // 代付款
    public static final String ORDER_STATUS_VIDEO_PASTDUE = "-1"; // 单点过期
    public static final String ORDER_STATUS_VIDEO_SUCCEED = "1"; // 单点成功
    public static final String ORDER_STATUS_PACKAGE_PASTDUE = "-2"; // 套餐过期
    public static final String ORDER_STATUS_PACKAGE_SUCCEED = "2"; // 套餐成功，套餐使用中
    public static final String ORDER_STATUS_LIVE_SUCCEED = "3"; // 直播付费成功

    /**
     * 订单支付状态0支付失败，1支付成功，2退款，3:自动续费扣费失败 4:过期关闭
     */
    public static final String ORDER_PAY_STATUS_FAIL = "0";
    public static final String ORDER_PAY_STATUS_SUCC = "1";
    public static final String ORDER_PAY_STATUS_REFUND = "2";
    public static final String ORDER_PAY_STATUS_AUTOMATIC_CHARGE_FAIL = "3";
    public static final String ORDER_PAY_STATUS_EXPIRE = "4";

    /**
     * 乐购订单支付状态，0：未支付 1：已支付 2：过期关闭 3：退款
     */
    public static final int TM_ORDER_PAY_STATUS_FAIL = 0;
    public static final int TM_ORDER_PAY_STATUS_SUCC = 1;
    public static final int TM_ORDER_PAY_STATUS_EXPIRE = 2;
    public static final int TM_ORDER_PAY_STATUS_REFUND = 3;

    /**
     * 腾讯订单类型
     */

    public static final int ORDER_TM_TYPE_0 = 0;
    public static final int ORDER_TM_TYPE_1 = 1;

    /**
     * 腾讯子订单订单类型
     */
    public static final int SUB_ORDER_TM_TYPE_1 = 1;
    public static final int SUB_ORDER_TM_TYPE_2 = 2;

    public static final String ORDER_PAY_STATUS_SUCC_STR = "ORDER_PAY_STATUS_SUCC_STR";// 购买成功
    public static final String ORDER_PAY_STATUS_FAIL_STR = "ORDER_PAY_STATUS_FAIL_STR";// 购买失败
    public static final String ORDER_PAY_STATUS_REFUND_STR = "ORDER_PAY_STATUS_REFUND_STR";// 退款订单
    public static final String ORDER_PAY_STATUS_CHARGE_FAIL_STR = "ORDER_PAY_STATUS_CHARGE_FAIL_STR";// 扣款失败
    public static final String ORDER_PAY_STATUS_FM_SUCC_STR = "ORDER_PAY_STATUS_FM_SUCC_STR";// 定金已付

    /**
     * 播控方版本
     */
    public static final int BROADCAST_APK_VERSION_LETV = 0;
    public static final int BROADCAST_APK_VERSION_CIBN = 1;
    public static final int BROADCAST_APK_VERSION_TPSDK = 2;

    /**
     * 代金券适用范围，1--普通vip套餐；2--高级vip套餐；3--电影单片；4--直播
     */
    public static final int VOUCHER_APPLICATION_TYPE_PC = 1;
    public static final int VOUCHER_APPLICATION_TYPE_TV = 2;
    public static final int VOUCHER_APPLICATION_TYPE_SINGLE_FILM = 3;
    public static final int VOUCHER_APPLICATION_TYPE_LIVE = 4;

    /**
     * 直播付费鉴权的请求来源类型，1、PC--加入试看以后的播放器、M站；2、play--PC、M站；3、tv--电视；4、TV--加入试看之后的TV端
     * 5、mobile--手机；6、mac--第三方SDK-mac；7、iphone--移动端加入了试看；
     */
    public static final String LIVE_CHECK_FROM_TV = "tv";
    public static final String LIVE_CHECK_FROM_MAC = "mac";
    public static final String LIVE_CHECK_FROM_PC_ADD_TRYPLAY = "PC";
    public static final String LIVE_CHECK_FROM_PLAY = "play";
    public static final String LIVE_CHECK_FROM_TV_ADD_TRYPLAY = "TV";
    public static final String LIVE_CHECK_FROM_MOBILE = "mobile";
    public static final String LIVE_CHECK_FROM_MOBILE_ADD_TRYPLAY = "iphone";

    /**
     * 直播付费鉴权的签名规则sign key
     */
    public static final String LIVE_CHECK_SIGN_KEY = "f8da39f11dbdafc03efa1ad0250c9ae6";

    /**
     * 直播鉴权返回的鉴权详细信息 1004--用户没有权限 1011--赛季包鉴权成功，但直播未开始 1012--球队包鉴权成功，但直播未开始
     * 1013--场次卷鉴权成功，但直播未开始
     */
    public static final String CHECK_LIVE_HAVE_NO_PERMISSION = "1004";
    public static final String CHECK_LIVE_SEASON_PACKAGE_NOT_START = "1011";
    public static final String CHECK_LIVE_TEAM_PACKAGE_NOT_START = "1012";
    public static final String CHECK_LIVE_SCREENING_TICKET_NOT_START = "1013";

    /**
     * 单点直播默认的直播类型
     */
    public static final String LIVE_DEFAULT_TYPE = "01";

    /**
     * 直播类型,1--直播券，2--轮次包，预定义类型，暂无数据，3--赛季包，4--球队宝
     */
    public static final int LIVE_TYPE_TICKET = 1;

    /**
     * api.zhifu接口返回的订单状态，0--查询失败，1--支付成功，2--通知失败（实际也是支付成功），3--已退款
     */
    public static final int ORDER_STATUS_API_ZHIFU_ERROR = 0;
    public static final int ORDER_STATUS_API_ZHIFU_PAY_SUCCESS = 1;
    public static final int ORDER_STATUS_API_ZHIFU_INFORM_FAILED = 2;
    public static final int ORDER_STATUS_API_ZHIFU_REFUND = 3;

    /**
     * 超级手机赠送的机卡绑定类型，2--赠送的手机时长，9--赠送的TV时长
     */
    public static final int PRESENT_DEBICE_BIND_TYPE_MOBILE = 2;
    public static final int PRESENT_DEBICE_BIND_TYPE_TV = 9;

    /**
     * 付费活动类型，1--套餐活动，2--支付渠道活动
     */
    public static final int PAYMENT_ACTIVITY_TYPE_PACKAGE = 1;
    public static final int PAYMENT_ACTIVITY_TYPE_PAYMENT_CHANNEL = 2;

    /**
     * 购买类型
     */
    public static final int PURCHASE_TYPE_1 = 1;
    public static final int PURCHASE_TYPE_2 = 2;
    public static final int PURCHASE_TYPE_3 = 3;

    /**
     * 生成订单接口添加参数，表示来源。1--乐视，2--乐视国广，3--运营商，4--TCL，5--飞利浦
     */
    public static final String VIP_GENERATE_ORDER_FROM_DEFAULT = "1";
    public static final String VIP_GENERATE_ORDER_FROM_CODE = "vip.generate.order.from.code";
    public static final String VIP_GENERATE_ORDER_FROM = ApplicationUtils.get(VIP_GENERATE_ORDER_FROM_CODE,
            VIP_GENERATE_ORDER_FROM_DEFAULT);

    public static final Boolean PAYMENT_STATUS_SUCCESS = true; // 成功
    public static final Boolean PAYMENT_STATUS_FAILURE = false; // 失败
    public static final String PAYMENT_CENTER_SUCCESS_LEKA_RECHARGE_0000 = "0000";// 成功

    /**
     * 付费活动模块，虚拟活动ID
     */
    public static final String PAYMENT_ACTIVITY_ONE_FOR_MONTH_VIRTUALID = "yeepayone";// 虚拟易宝支付一元包月活动的活动ID

    public static final String VIP_CENTER_POSITION_TOP = "ucenter_1";// 会员运营中心参数，顶部触达位置
    public static final String VIP_CENTER_POSITION_BOTTOM = "ucenter_2";// 会员运营中心参数，下浮层触达位置
    public static final String VIP_CENTER_POSITION_POPUP = "ucenter_3";// 会员运营中心参数，定向弹窗触达位置
    public static final String VIP_CENTER_POSITION_MINE_1 = "ucenter_4";// 会员运营中心参数，我的页面中间列第一个位置
    public static final String VIP_CENTER_POSITION_MINE_2 = "ucenter_5";// 会员运营中心参数，我的页面中间列第二个位置
    public static final String VIP_CENTER_POSITION_MINE_3 = "ucenter_6";// 会员运营中心参数，我的页面中间列第三个位置
    public static final String VIP_CENTER_POSITION_MINE_4 = "ucenter_7";// 会员运营中心参数，我的页面第三列会员专享位置

    public static final String USER_TOUCH_POSITION_TOP = "top";// 返给客户端的字段，顶部触达位置
    public static final String USER_TOUCH_POSITION_POPUP = "popup";// 返给客户端的字段，定向弹窗触达位置
    public static final String USER_TOUCH_POSITION_BOTTOM = "bottom";// 返给客户端的字段，下浮层触达位置
    public static final String USER_TOUCH_POSITION_VIDEO_SALE = "videosale_";// 视频营销里有个按钮支持购买，所以标记此位置
    public static final String USER_TOUCH_POSITION_MINE = "mine";// 我的页面活动位置前缀
    public static final String USER_TOUCH_POSITION_MINE_1 = "mine1";// 我的页面活动位置1
    public static final String USER_TOUCH_POSITION_MINE_2 = "mine2";// 我的页面活动位置2
    public static final String USER_TOUCH_POSITION_MINE_3 = "mine3";// 我的页面活动位置3
    public static final String USER_TOUCH_POSITION_MINE_4 = "mine4";// 我的页面活动位置4
    public static final String USER_TOUCH_POSITION_PLAY_SINGLE = "single";// 播放引导之单点引导
    public static final String USER_TOUCH_POSITION_PLAY_STREAM = "stream";// 播放引导之码流付费
    public static final String USER_TOUCH_POSITION_PLAY_NO_AD = "noad";// 播放引导之去广告
    public static final String USER_TOUCH_POSITION_PLAY_SLOW = "slow";// 播放引导之卡顿
    public static final String USER_TOUCH_POSITION_PLAY_ONLYVIP = "onlyvip";// 播放引导之仅会员
    public static final String USER_TOUCH_POSITION_BOTTOM_FLAG = "1";// 用户当天下浮层参加过活动标志位

    public static final String USER_TOUCH_DEVICE_TYPE_TV = "tv";
    public static final String USER_TOUCH_DEVICE_TYPE_MOBILE = "mobile";
    public static final String USER_TOUCH_DEVICE_TYPE_BOX = "box";

    /**
     * 最早订单生成时间
     */
    public static final String ORDER_ORIGINALLY_GENERATED_TIME = "2013-01-01 00:00:00";

    public static final String URM_POSITION_TOP = ApplicationUtils.get(ApplicationConstants.URM_TOUCH_POSITION_TOP);// 会员运营中心参数，顶部触达位置
    public static final String URM_POSITION_BOTTOM = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_POSITION_BOTTOM);// 会员运营中心参数，下浮层触达位置
    public static final String URM_POSITION_POPUP = ApplicationUtils.get(ApplicationConstants.URM_TOUCH_POSITION_POPUP);// 会员运营中心参数，定向弹窗触达位置
    public static final String URM_POSITION_MINE_1 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_POSITION_MINE1);// 会员运营中心参数，我的页面中间列第一个位置
    public static final String URM_POSITION_MINE_2 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_POSITION_MINE2);// 会员运营中心参数，我的页面中间列第二个位置
    public static final String URM_POSITION_MINE_3 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_POSITION_MINE3);// 会员运营中心参数，我的页面中间列第三个位置
    public static final String URM_POSITION_MINE_4 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_POSITION_MINE4);// 会员运营中心参数，我的页面第三列会员专享位置
    public static final String URM_POSITION_PLAY_SINGLE = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_POSITION_PLAY_SINGLE);// 会员运营中心参数，播放引导之单点引导
    public static final String URM_POSITION_PLAY_STREAM = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_POSITION_PLAY_STREAM);// 会员运营中心参数，播放引导之码流付费
    public static final String URM_POSITION_PLAY_NO_AD = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_POSITION_PLAY_NO_AD);// 会员运营中心参数，播放引导之去广告
    public static final String URM_POSITION_PLAY_SLOW = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_POSITION_PLAY_SLOW);// 会员运营中心参数，播放引导之卡顿
    public static final String URM_POSITION_PLAY_ONLYVIP = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_POSITION_PLAY_ONLYVIP);// 会员运营中心参数，播放引导之仅会员

    // 会员桌面-用户信息
    public static final String URM_USER_INFO_MOVIE_OPERATION = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_MOVIE_OPERATION);// 会员桌面-会员信息-影视运营触达位
    public static final String URM_USER_INFO_SPORT_OPERATION = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_SPORT_OPERATION);// 会员桌面-会员信息-体育运营触达位

    /*-----------------------------------------会员桌面-活动触达位  start------------------------------------------*/
    // 焦点图
    public static final String URM_USER_INFO_MOVIE_FOCUS_1 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_MOVIE_FOCUS_1); // 会员桌面-影视运营触达位-焦点图-13
    public static final String URM_USER_INFO_MOVIE_FOCUS_2 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_MOVIE_FOCUS_2); // 会员桌面-影视运营触达位-焦点图-14
    public static final String URM_USER_INFO_MOVIE_FOCUS_3 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_MOVIE_FOCUS_3); // 会员桌面-影视运营触达位-焦点图-15
    public static final String URM_USER_INFO_MOVIE_FOCUS_4 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_MOVIE_FOCUS_4); // 会员桌面-影视运营触达位-焦点图-16
    public static final String URM_USER_INFO_MOVIE_FOCUS_5 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_MOVIE_FOCUS_5); // 会员桌面-影视运营触达位-焦点图-17

    public static final String URM_USER_INFO_SPORT_FOCUS_1 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_SPORT_FOCUS_1); // 会员桌面-体育运营触达位-焦点图-18
    public static final String URM_USER_INFO_SPORT_FOCUS_2 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_SPORT_FOCUS_2); // 会员桌面-体育运营触达位-焦点图-88
    public static final String URM_USER_INFO_SPORT_FOCUS_3 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_SPORT_FOCUS_3); // 会员桌面-体育运营触达位-焦点图-86
    public static final String URM_USER_INFO_SPORT_FOCUS_4 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_SPORT_FOCUS_4); // 会员桌面-体育运营触达位-焦点图-87
    public static final String URM_USER_INFO_SPORT_FOCUS_5 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_SPORT_FOCUS_5); // 会员桌面-体育运营触达位-焦点图-22

    // 权益
    public static final String URM_USER_INFO_MOVIE_RIGHTS_1 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_MOVIE_RIGHTS_1); // 会员桌面-影视运营触达位-会员权益-23
    public static final String URM_USER_INFO_MOVIE_RIGHTS_2 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_MOVIE_RIGHTS_2); // 会员桌面-影视运营触达位-会员权益-24
    public static final String URM_USER_INFO_MOVIE_RIGHTS_3 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_MOVIE_RIGHTS_3); // 会员桌面-影视运营触达位-会员权益-25
    public static final String URM_USER_INFO_MOVIE_RIGHTS_4 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_MOVIE_RIGHTS_4); // 会员桌面-影视运营触达位-会员权益-26
    public static final String URM_USER_INFO_MOVIE_RIGHTS_5 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_MOVIE_RIGHTS_5); // 会员桌面-影视运营触达位-会员权益-27
    public static final String URM_USER_INFO_MOVIE_RIGHTS_6 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_MOVIE_RIGHTS_6); // 会员桌面-影视运营触达位-会员权益-28

    public static final String URM_USER_INFO_SPORT_RIGHTS_1 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_SPORT_RIGHTS_1); // 会员桌面-体育运营触达位-会员权益-29
    public static final String URM_USER_INFO_SPORT_RIGHTS_2 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_SPORT_RIGHTS_2); // 会员桌面-体育运营触达位-会员权益-30
    public static final String URM_USER_INFO_SPORT_RIGHTS_3 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_SPORT_RIGHTS_3); // 会员桌面-体育运营触达位-会员权益-31
    public static final String URM_USER_INFO_SPORT_RIGHTS_4 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_SPORT_RIGHTS_4); // 会员桌面-体育运营触达位-会员权益-32
    public static final String URM_USER_INFO_SPORT_RIGHTS_5 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_SPORT_RIGHTS_5); // 会员桌面-体育运营触达位-会员权益-33
    public static final String URM_USER_INFO_SPORT_RIGHTS_6 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_SPORT_RIGHTS_6); // 会员桌面-体育运营触达位-会员权益-34

    // 活动
    public static final String URM_USER_INFO_MOVIE_ACTIVITY_1 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_MOVIE_ACTIVITY_1); // 会员桌面-影视运营触达位-会员活动-35
    public static final String URM_USER_INFO_MOVIE_ACTIVITY_2 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_MOVIE_ACTIVITY_2); // 会员桌面-影视运营触达位-会员活动-36
    public static final String URM_USER_INFO_MOVIE_ACTIVITY_3 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_MOVIE_ACTIVITY_3); // 会员桌面-影视运营触达位-会员活动-27
    public static final String URM_USER_INFO_MOVIE_ACTIVITY_4 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_MOVIE_ACTIVITY_4); // 会员桌面-影视运营触达位-会员活动-28
    public static final String URM_USER_INFO_MOVIE_ACTIVITY_5 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_MOVIE_ACTIVITY_5); // 会员桌面-影视运营触达位-会员活动-29
    public static final String URM_USER_INFO_MOVIE_ACTIVITY_6 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_MOVIE_ACTIVITY_6); // 会员桌面-影视运营触达位-会员活动-40

    public static final String URM_USER_INFO_SPORT_ACTIVITY_1 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_SPORT_ACTIVITY_1); // 会员桌面-影视运营触达位-会员活动-85
    public static final String URM_USER_INFO_SPORT_ACTIVITY_2 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_SPORT_ACTIVITY_2); // 会员桌面-影视运营触达位-会员活动-42
    public static final String URM_USER_INFO_SPORT_ACTIVITY_3 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_SPORT_ACTIVITY_3); // 会员桌面-影视运营触达位-会员活动-43
    public static final String URM_USER_INFO_SPORT_ACTIVITY_4 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_SPORT_ACTIVITY_4); // 会员桌面-影视运营触达位-会员活动-44
    public static final String URM_USER_INFO_SPORT_ACTIVITY_5 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_SPORT_ACTIVITY_5); // 会员桌面-影视运营触达位-会员活动-45
    public static final String URM_USER_INFO_SPORT_ACTIVITY_6 = ApplicationUtils
            .get(ApplicationConstants.URM_TOUCH_MEMBER_SPORT_ACTIVITY_6); // 会员桌面-影视运营触达位-会员活动-47
    /*------------------------------------------------会员桌面-活动触达位  end------------------------------------------------*/

    // client key
    public static final String URM_ACTIVITY_CLIENT_KEY = "tv_c97210b8-9552-462f-bef9-fb78d98d1dae";
    public static final String URM_ACTIVITY_LIST_CLIENT_KEY = "tv_vipcentre_4e61972f-90ea-4d7a-8501-29da51d72088";

    public static final String BOSS_YUANXIAN_SAFE_BUSSINESS_ID = "10001";// BOSS风险接口业务线ID
    public static final String BOSS_YUANXIAN_SAFE_SIGN_KEY = "520af3e884aee462e087f352";// BOSS风险接口签名使用的key

    public static final Integer BUSINESS_ID_LETV_US = 10013; // le.com的bussinessId（业务线id）

    /**
     * boss播放鉴权（V2）时区分的播放资源类型，1--专辑，2--轮播台，3--视频，4--直播
     */
    public static final int BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_ALBUM = 1;
    public static final int BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_LUNBO = 2;
    public static final int BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_VIDEO = 3;
    public static final int BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_LIVE = 4;

    /**
     * boss V2 机卡绑定业务--机卡状态，1-已上报，2-已同步（可领取），3-领取中，6已领取，9已退货
     */
    public static final int BOSS_DEVICE_BIND_V2_DEVICE_STATUS_2 = 2;
    public static final int BOSS_DEVICE_BIND_V2_DEVICE_STATUS_6 = 6;

    public static final String VIP_USPAY_TAXID = "1";
    public static final String VIP_USPAY_TAXCODE = "SuperMovie";
    public static final String VIP_USPAY_PRODUCT_IMG = "http://i1.img.cp21.ott.cibntv.net/lc03_iscms/201611/22/14/24/40fb5df70bcb40d68e383690a245ed33.jpg";
    public static final String VIP_USPAY_PRODUCT_IMG_KEY = "vip.checkout.counter.paysdk.img";

    public static final Map<String, String> deviceType = new HashMap<String, String>();

    static {
        deviceType.put(String.valueOf(DEVICE_BIND_DEVICE_TYPE_TV), USER_TOUCH_DEVICE_TYPE_TV);
        deviceType.put(String.valueOf(DEVICE_BIND_DEVICE_TYPE_MOBILE), USER_TOUCH_DEVICE_TYPE_MOBILE);
        deviceType.put(String.valueOf(DEVICE_BIND_DEVICE_TYPE_LETV_BOX), USER_TOUCH_DEVICE_TYPE_BOX);
    }

    /** 触达位置数组，依次是：顶部》下浮层》定向弹窗 */
    public static final String[] vip_center_positions_pilot = { VIP_CENTER_POSITION_TOP, VIP_CENTER_POSITION_BOTTOM,
            VIP_CENTER_POSITION_POPUP };

    /** 触达位置数组，依次是：我的页面中间列从上到下3个位置、第3列会员专享位置 */
    public static final String[] vip_center_positions_mine = { VIP_CENTER_POSITION_MINE_1, VIP_CENTER_POSITION_MINE_2,
            VIP_CENTER_POSITION_MINE_3, VIP_CENTER_POSITION_MINE_4 };

    /** URM位置 */
    public static final String[] urm_positions_mine = { URM_POSITION_MINE_1, URM_POSITION_MINE_2, URM_POSITION_MINE_3,
            URM_POSITION_MINE_4 };
    /***/
    public static final String[] urm_positions_play = { URM_POSITION_PLAY_NO_AD, URM_POSITION_PLAY_STREAM,
            URM_POSITION_PLAY_SINGLE, URM_POSITION_PLAY_SLOW, URM_POSITION_PLAY_ONLYVIP };

    private static final Map<String, String> userTouchPositionMap = new HashMap<String, String>();
    private static final Map<String, String> vipCenterPositionMap = new HashMap<String, String>();
    private static final Map<String, String> urmTouchPositionMap = new HashMap<String, String>();
    private static final Map<String, String> userTypeMap = new HashMap<String, String>();

    static {
        userTouchPositionMap.put(VIP_CENTER_POSITION_TOP, USER_TOUCH_POSITION_TOP);
        userTouchPositionMap.put(VIP_CENTER_POSITION_BOTTOM, USER_TOUCH_POSITION_BOTTOM);
        userTouchPositionMap.put(VIP_CENTER_POSITION_POPUP, USER_TOUCH_POSITION_POPUP);
        userTouchPositionMap.put(VIP_CENTER_POSITION_MINE_1, USER_TOUCH_POSITION_MINE_1);
        userTouchPositionMap.put(VIP_CENTER_POSITION_MINE_2, USER_TOUCH_POSITION_MINE_2);
        userTouchPositionMap.put(VIP_CENTER_POSITION_MINE_3, USER_TOUCH_POSITION_MINE_3);
        userTouchPositionMap.put(VIP_CENTER_POSITION_MINE_4, USER_TOUCH_POSITION_MINE_4);

        vipCenterPositionMap.put(USER_TOUCH_POSITION_TOP, VIP_CENTER_POSITION_TOP);
        vipCenterPositionMap.put(USER_TOUCH_POSITION_BOTTOM, VIP_CENTER_POSITION_BOTTOM);
        vipCenterPositionMap.put(USER_TOUCH_POSITION_POPUP, VIP_CENTER_POSITION_POPUP);
        vipCenterPositionMap.put(USER_TOUCH_POSITION_MINE_1, VIP_CENTER_POSITION_MINE_1);
        vipCenterPositionMap.put(USER_TOUCH_POSITION_MINE_2, VIP_CENTER_POSITION_MINE_2);
        vipCenterPositionMap.put(USER_TOUCH_POSITION_MINE_3, VIP_CENTER_POSITION_MINE_3);
        vipCenterPositionMap.put(USER_TOUCH_POSITION_MINE_4, VIP_CENTER_POSITION_MINE_4);

        urmTouchPositionMap.put(URM_POSITION_TOP, USER_TOUCH_POSITION_TOP);
        urmTouchPositionMap.put(URM_POSITION_BOTTOM, USER_TOUCH_POSITION_BOTTOM);
        urmTouchPositionMap.put(URM_POSITION_POPUP, USER_TOUCH_POSITION_POPUP);
        urmTouchPositionMap.put(URM_POSITION_MINE_1, USER_TOUCH_POSITION_MINE_1);
        urmTouchPositionMap.put(URM_POSITION_MINE_2, USER_TOUCH_POSITION_MINE_2);
        urmTouchPositionMap.put(URM_POSITION_MINE_3, USER_TOUCH_POSITION_MINE_3);
        urmTouchPositionMap.put(URM_POSITION_MINE_4, USER_TOUCH_POSITION_MINE_4);
        urmTouchPositionMap.put(URM_POSITION_PLAY_SINGLE, USER_TOUCH_POSITION_PLAY_SINGLE);
        urmTouchPositionMap.put(URM_POSITION_PLAY_STREAM, USER_TOUCH_POSITION_PLAY_STREAM);
        urmTouchPositionMap.put(URM_POSITION_PLAY_NO_AD, USER_TOUCH_POSITION_PLAY_NO_AD);
        urmTouchPositionMap.put(URM_POSITION_PLAY_SLOW, USER_TOUCH_POSITION_PLAY_SLOW);
        urmTouchPositionMap.put(URM_POSITION_PLAY_ONLYVIP, USER_TOUCH_POSITION_PLAY_ONLYVIP);

        userTypeMap.put(URM_POSITION_PLAY_SINGLE, "3");
        userTypeMap.put(URM_POSITION_PLAY_STREAM, "2");
        userTypeMap.put(URM_POSITION_PLAY_NO_AD, "1");
        userTypeMap.put(URM_POSITION_PLAY_SLOW, "4");
        userTypeMap.put(URM_POSITION_PLAY_ONLYVIP, "5");
    }

    /**
     * 会员中心位置转换成服务端定义的位置
     * @param vipCenterPostion
     * @return
     */
    public static String getUserTouchPosition(String vipCenterPostion) {
        return userTouchPositionMap.get(vipCenterPostion);
    }

    /**
     * URM系统位置信息转换成服务端定义的位置
     * @param urmPostion
     * @return
     */
    public static String getUserTouchPositionV2(String urmPostion) {
        return urmTouchPositionMap.get(urmPostion);
    }

    /**
     * 服务端定义的位置转换成会员中心的位置
     * @param userTouchPosition
     * @return
     */
    public static String getVipCenterPosition(String userTouchPosition) {
        return vipCenterPositionMap.get(userTouchPosition);
    }

    public static String getUserType(String touchSpotId) {
        return userTypeMap.get(touchSpotId);
    }

    /**
     * 静态文件服务器的文件存放URL（父目录）
     */
    public final static String STATIC_FILE_SERVER_BASE_URL = ApplicationConstants.I_STATIC_ITV_BASE_HOST + "/api/"; // 静态文件服务器的文件存放URL（父目录）

    /**
     * 查询用户充值记录URL
     */
    private final static String QUERY_RECHARGE_RECORD_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_RECHARGE_RECORD);

    /**
     * 获取消费记录
     */
    public final static String QUERY_CONSUMPTION_RECORD_URL_BAK = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/sale.ldo?type=sale&status={status}&day={day}&uname={uname}&page={page}&pagesize={pagesize}";

    /**
     * 获取消费记录；根据CIBN整改需求，将原接口中包含的参数username或uname去除，改为使用userid，升级为通用接口，下同
     */
    public final static String QUERY_CONSUMPTION_RECORD_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_CONSUME_RECORD_V1);

    /**
     * 废弃不用
     */
    private final static String QUERY_CONSUMPTION_RECORD_URL_v2 = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_CONSUME_RECORD_V2);

    /**
     * 获取消费记录，包含机卡绑定需求
     */
    public final static String QUERY_CONSUMPTION_RECORD_URL_V3_BAK = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/sale.ldo?type=saleRecord&status={status}&day={day}&uname={uname}&page={page}&pagesize={pagesize}&end={end}&mac={mac}&devicekey={deviceKey}";
    public final static String QUERY_CONSUMPTION_RECORD_URL_V3 = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_CONSUME_RECORD_V3);

    /**
     * get vip package data
     */
    public final static String QUERY_PRICE_PACKAGE_TYPE_URL = ApplicationConstants.BOSS_PRICE_ZHIFU_BASE_HOST
            + "/pinfo/get/package?end={end}";

    /**
     * 获取单品订单
     */
    public final static String GET_ORDER_ID_FOR_SINGLE_URL_BAK = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/newPay.ldo?ptype={ptype}&pid={pid}&vid={vid}&end={end}&uname={uname}&packtype={packtype}";
    private final static String GET_ORDER_ID_FOR_SINGLE_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_ORDER_SINGLE_PRODUCT);

    /**
     * 获取产品包订单
     */
    public final static String GET_ORDER_ID_FOR_PACKAGE_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_ORDER_PACKAGE);

    /**
     * 获取支付宝支付二维码
     */
    public final static String GET_ALIPAY_CODE_URL_BAK = ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST
            + "/pay/getqrcode/6?corderid={corderid}&userid={userid}&username={username}&uname={uname}&price={price}&companyid={companyid}&deptid={deptid}&pid={pid}&backurl={backurl}&fronturl={fronturl}&productid={productid}&svip={svip}&buyType={buyType}&productnum={productnum}&productname={productname}&productdesc={productdesc}&defaultbank={defaultbank}&sign={sign}";
    private final static String GET_ALIPAY_CODE_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_ALIPAY_CODE_WEB);

    /**
     * 获取支付宝图片类型的支付二维码（上面的二维码是网页形式的）;
     */
    public final static String GET_ALIPAY_CODE_LMG_URL_BAK = ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST
            + "/pay/tvtwodimensioncode/5?corderid={corderid}&userid={userid}&username={username}&uname={uname}&price={price}&companyid={companyid}&deptid={deptid}&pid={pid}&backurl={backurl}&fronturl={fronturl}&productid={productid}&svip={svip}&buyType={buyType}&productnum={productnum}&productname={productname}&productdesc={productdesc}&defaultbank={defaultbank}&sign={sign}";
    private final static String GET_ALIPAY_CODE_LMG_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_ALIPAY_CODE_IMG);

    /**
     * 获取支付宝二维码图片； 2012-10-15CIBN需求新增接口； 注意：相比原接口，仅支付渠道发生改变（由5改为13）
     */
    public final static String GET_ALIPAY_CODE_LMG_CIBN_URL_BAK = ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST
            + "/pay/tvtwodimensioncode/13?corderid={corderid}&userid={userid}&username={username}&uname={uname}&price={price}&companyid={companyid}&deptid={deptid}&pid={pid}&backurl={backurl}&fronturl={fronturl}&productid={productid}&svip={svip}&buyType={buyType}&productnum={productnum}&productname={productname}&productdesc={productdesc}&defaultbank={defaultbank}&sign={sign}&av={av}";
    private final static String GET_ALIPAY_CODE_LMG_CIBN_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_ALIPAY_CODE_CIBN_IMG);

    /**
     * 获取微信支付二维码
     */
    public final static String GET_WEIXIN_CODE_URL_BAK = ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST
            + "/pay/wxcommon/24?corderid={corderid}&userid={userid}&username={username}&uname={uname}&price={price}&companyid={companyid}&deptid={deptid}&pid={pid}&backurl={backurl}&fronturl={fronturl}&productid={productid}&svip={svip}&buyType={buyType}&productnum={productnum}&productname={productname}&productdesc={productdesc}&defaultbank={defaultbank}&sign={sign}&activityIds={activityIds}";
    private final static String GET_WEIXIN_CODE_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_WEIXIN_CODE_IMG);

    /**
     * 手机支付
     */
    private static final String PHONEPAY_URL = ApplicationUtils.get(ApplicationConstants.BOSS_API_ZHIFU_PHONE_PAY);

    /**
     * 拉卡拉支付
     */
    private static final String LAKALAPAY_URL = ApplicationUtils.get(ApplicationConstants.BOSS_API_ZHIFU_LAKALA_PAY);

    /**
     * 单点支付--支付宝二维码图片URL 2014-10-15废弃
     */
    public static final String ORDER_PAYCODE_ALIPAY_IMG_URL_BAK = ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST
            + "/pay/tvtwodimensioncode/5?corderid={corderid}&userid={userid}&username={username}&companyid={companyid}&deptid={deptid}&productid={productid}&productnum={productnum}&backurl={backurl}&price={price}&buyType={buyType}&pid={pid}&svip={svip}&sign={sign}";
    private static final String ORDER_PAYCODE_ALIPAY_IMG_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_ALIPAY_SINGLE_CODE_IMG);

    /**
     * CINB版单点支付--支付宝二维码图片URL
     */
    public static final String ORDER_PAYCODE_ALIPAY_IMG_CIBN_URL_BAK = ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST
            + "/pay/tvtwodimensioncode/13?corderid={corderid}&userid={userid}&username={username}&companyid={companyid}&deptid={deptid}&productid={productid}&productnum={productnum}&backurl={backurl}&price={price}&buyType={buyType}&pid={pid}&svip={svip}&sign={sign}&av={av}";
    private static final String ORDER_PAYCODE_ALIPAY_IMG_CIBN_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_ALIPAY_SINGLE_CODE_CIBN_IMG);

    /**
     * 单点支付--微信二维码图片URL
     */
    public static final String ORDER_PAYCODE_WEINXIN_IMG_URL_BAK = ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST
            + "/pay/wxcommon/24?corderid={corderid}&userid={userid}&username={username}&companyid={companyid}&deptid={deptid}&productid={productid}&productnum={productnum}&backurl={backurl}&price={price}&buyType={buyType}&pid={pid}&svip={svip}&sign={sign}";
    private static final String ORDER_PAYCODE_WEINXIN_IMG_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_WEIXIN_SINGLE_CODE_IMG);

    /**
     * 单点支付--拉卡拉二维码图片URL
     */
    public static final String ORDER_PAYCODE_LAKALA_IMG_URL_BAK = ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST
            + "/pay/lakala/16?corderid={corderid}&userid={userid}&username={username}&companyid={companyid}&deptid={deptid}&productid={productid}&productnum={productnum}&backurl={backurl}&price={price}&buyType={buyType}&pid={pid}&svip={svip}&sign={sign}";
    private static final String ORDER_PAYCODE_LAKALA_IMG_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_LAKALA_SINGLE_CODE_IMG);

    /**
     * check phone if support pay url
     */
    public static final String CHECK_PHONE_URL = ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST
            + "/phone/{phone}?ip={ip}";

    /**
     * 高级vip包年服务,盒端快捷购买套餐用 废弃
     */
    private static final String CONSUMPTION_VIP_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_PACKAGE_YEAR_PURCHASE);

    /**
     * 高级vip包年服务,盒端快捷购买套餐用 2014-10-15新增CIBN需求
     */
    public static final String CONSUMPTION_VIP_BY_LETV_POINT_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_LETVPOINT_PAY);
    private static final String CONSUMPTION_VIP_CIBN_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_PACKAGE_YEAR_PURCHASE);

    /**
     * 会员中心创建认证二维码URL
     */
    public static final String SSO_CREATE_QRCODE_URL = ApplicationUtils.get(ApplicationConstants.USERCENTER_SSO_IMG);

    /**
     * 机卡绑定套餐绑定信息查询URL 废弃不用
     */
    public static final String DEVICE_BIND_QUERY_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_DEVICE_BIND_QUERY);

    /**
     * 机卡绑定设备信息查询URL
     */
    public static final String DEVICE_BIND_QUERY_DEVICE_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_BIND_QUERY_DEVICE_URL);

    /**
     * 机卡绑定用户信息查询URL
     */
    public static final String DEVICE_BIND_QUERY_USER_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_BIND_QUERY_USRE_URL);

    /**
     * 删除订单及其对应消费记录
     */
    public static final String DELETE_ORDER_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_DELETE_ORDER_URL);

    /**
     * 支付宝图片二维码通用接口（支持套餐和单点）
     */
    public static final String PURCHASE_VIP_ALI_PAYCODE_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_PURCHASE_VIP_ALI_PAYCODE);

    /**
     * 支付宝二维码国广版通用接口（支持套餐和单点）
     */
    public static final String PURCHASE_VIP_ALI_PAYCODE_CIBN_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_PURCHASE_VIP_ALI_CIBN_PAYCODE);

    /**
     * 微信图片二维码通用接口（支持套餐和单点）
     */
    public static final String PURCHASE_VIP_WEIXIN_PAYCODE_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_PURCHASE_VIP_WEIXIN_PAYCODE);

    /**
     * 拉卡拉图片二维码通用接口（支持套餐和单点）
     */
    public static final String PURCHASE_VIP_LAKALA_PAYCODE_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_PURCHASE_VIP_LAKALA_PAYCODE);

    /**
     * 乐点支付通用接口（支持套餐和单点）
     */
    public static final String PURCHASE_VIP_LETVPOINT_PAYMENT_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_PURCHASE_VIP_LETVPOINT_PAYCODE);

    /**
     * 获取TV端活动信息url
     */
    public final static String GET_PAYMENT_ACTIVITY_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_PAYMENT_ACTIVITY_QUERY_URL);

    /**
     * check voucher status url
     */
    public final static String CHECK_VOUCHER_STATUS_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_PRICE_ZHIFU_CHECK_VOUCHER);

    /**
     * 获取活动信息url
     */
    public final static String GET_ACTIVITY_INFO_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_ACTIVITY_INFO);

    /**
     * 直播付费鉴权URL
     */
    public final static String CHECK_LIVE_STATUS_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_CHECK_LIVE_URL);

    /**
     * 微信支付国广版本URL
     */
    private static final String GET_WEIXIN_PAYCODE_CIBN_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_WEIXIN_PAYCODE_CIBN);

    /**
     * 一键支付绑定查询（暂只查询paypal支付），香港
     */
    public final static String CHECK_ONE_KEY_PAY_BIND_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_CHECK_ONE_KEY_PAY);

    /**
     * paypal绑定和支付接口（香港）
     */
    public final static String PURCHASE_VIP_PAYPAL_BIND_AND_PAY_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_PAYPAL_BIND_AND_PAY);

    /**
     * 一键快捷支付（香港）
     */
    public final static String PURCHASE_VIP_ONE_KEY_QUICK_PAY_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_ONE_KEY_QUICK_PAY);

    /**
     * 查询用户是否购买某一直播券URL
     */
    public final static String CHECK_USER_LIVE_TICKET_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_CHECK_USER_LIVE_TICKET);

    /**
     * 激活用户已购买的某一直播券URL
     */
    public final static String ACTIVE_USER_LIVE_TICKET_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_ACTIVE_USER_LIVE_TICKET);

    /**
     * 获取会员有效期
     */
    public final static String GET_VIP_ACCOUNT_INFO_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_VIP_ACCOUNT_INFO_GET);

    /**
     * 获取乐点账户余额
     */
    public final static String GET_VIP_ACCOUNT_LETV_POINT_BALANCE_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_LETV_POINT_BALANCE_GET);

    /**
     * 易宝支付未绑卡下单支付URL
     */
    public final static String YEEPAY_UNBIND_ORDER_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_YEEPAY_UNBIND_PAY);

    /**
     * 易宝支付绑卡下单支付
     */
    public final static String YEEPAY_BIND_ORDER_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_YEEPAY_BIND_PAY);

    /**
     * 查询绑卡信息
     */
    public final static String GET_YEEPAY_GET_BIND_INFO_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_YEEPAY_BINDINFO_GET);

    /**
     * 查询用户是否可以一元绑卡
     */
    public final static String GET_YEEPAY_GET_ONE_BIND_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_YEEPAY_ONEBIND_GET);

    /**
     * 易宝支付获取验证码URL
     */
    public final static String GET_YEEPAY_SENDMSG_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_YEEPAY_SENDMSG_URL);

    /**
     * 易宝支付确认支付URL
     */
    public final static String GET_YEEPAY_CONFIRM_PAY_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_YEEPAY_CONFIRM_PAY_URL);

    /** 直播试看标题 **/
    public final static String LIVE_TRY_PLAY_TEXT = "LIVE_TRY_PLAY_TEXT";

    public static String calculateMd5(String departmentId, String username) {
        // MD5(pid +username + Key)
        StringBuilder plain = new StringBuilder();
        plain.append(departmentId).append(username).append(PAY_CENTER_KEY);

        String md5 = null;
        try {
            md5 = MessageDigestUtil.md5(plain.toString().getBytes(Charset.forName(CommonConstants.UTF8)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return md5;
    }

    /**
     * get user recharge record url
     */
    public final static String VIP_GET_RECHARGE_RECORD_URL = ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST
            + "/queryrecord/";

    /**
     * get user consumption record
     */
    public final static String VIP_GET_CONSUMPTION_RECORD_URL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/sale.ldo?type=saleRecord";

    /**
     * generate order without payment channel url
     */
    public final static String VIP_PURCHASE_PRODUCT_URL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/payMent.ldo?";

    /**
     * generate qrcode when pay success
     */
    public final static String VIP_SSO_QRCODE_URL = ApplicationConstants.USERCENTER_SSO_LE_BASE_HOST
            + "/user/createQRCode?";

    /**
     * 获取直播鉴权url
     * @param locale
     * @return
     */
    public final static String CHECK_LIVE_URL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/liveValidate.ldo?";

    /**
     * 获取直播券的查券用券url
     * @param locale
     * @return
     */
    public final static String LIVE_TICKET_URL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST + "/letv/live.ldo?";

    /**
     * 获取校验信用卡url
     * @param locale
     * @return
     */
    public final static String CHECK_ONEKEY_PAY_URL = ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST
            + "/pay/queryonekey?";

    /**
     * 获取第三方sdk直播鉴权url
     * @param locale
     * @return
     */
    public final static String TP_SDK_TICKET_LIVE_URL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/livemac.ldo?";

    /**
     * get user vip info url
     */
    public final static String VIP_GET_VIP_INFO_URL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/getService.ldo?";

    /**
     * validate the letvcard if can use for recharge
     */
    public final static String VIP_LETVCARD_VALIDATE_URL = ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST
            + "/recharge/validate?";

    /**
     * get user device bind info url
     */
    public final static String VIP_DEVICE_BIND_INFO_GET_URL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/deviceBind.ldo?";

    /**
     * get user letvpoint balance url
     */
    public final static String VIP_GET_USER_LETVPOINT_BALANCE_URL = ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST
            + "/querylepoint/";

    /**
     * receive free vip url
     */
    public final static String VIP_GET_FREE_VIP_URL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST + "/letv/sale.ldo?";

    /**
     * check user has one yuan bind card
     */
    public final static String VIP_CHECK_ONE_YUAN_BIND_URL = ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST
            + "/yeepay/issale?";

    /**
     * get pay token url
     */
    public final static String VIP_GET_HK_PAY_TOKEN_URL = "http://hkpay.le.com/hk/gettoken";

    /**
     * get pay token url
     */
    public final static String VIP_GET_US_PAY_TOKEN_URL = ApplicationConstants.BOSS_USPAY_LE_BASE_HOST + "/us/gettoken";

    /**
     * 获取消费记录URL，兼容机卡绑定需求
     * @param locale
     * @return
     */
    public static String getConsumptionRecordUrlV3(Locale locale) {
        return QUERY_CONSUMPTION_RECORD_URL_V3;
    }

    /**
     * 获取单品订单URL
     * @param locale
     * @return
     */
    public static String getOrderIdForSingleUrl(Locale locale) {
        return GET_ORDER_ID_FOR_SINGLE_URL;
    }

    /**
     * 支付url集合，key：支付渠道，value：支付url前缀
     */
    private final static Map<String, String> PAY_URL_MAP = new HashMap<String, String>();

    static {
        PAY_URL_MAP.put(String.valueOf(VipTpConstant.PAYMENT_CHANNEL_ALIPAY),
                ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST + "/pay/tvtwodimensioncode/5?");
        PAY_URL_MAP.put(String.valueOf(VipTpConstant.PAYMENT_CHANNEL_WEIXIN),
                ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST + "/pay/wxcommon/24?");
        PAY_URL_MAP.put(String.valueOf(VipTpConstant.PAYMENT_CHANNEL_LAKALA),
                ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST + "/pay/lakala/16?");
        PAY_URL_MAP.put(String.valueOf(VipTpConstant.PAYMENT_CHANNEL_LETVPOINT),
                ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST + "/pay/point/33?");
        PAY_URL_MAP.put(String.valueOf(VipTpConstant.PAYMENT_CHANNEL_PAYPAL),
                ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST + "/pay/mobile/39?");
        PAY_URL_MAP.put(String.valueOf(VipTpConstant.PAYMENT_CHANNEL_ONE_KEY_QUICK_PAY),
                ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST + "/pay/mobile/49?");
        PAY_URL_MAP.put(String.valueOf(VipTpConstant.PAYMENT_CHANNEL_YEEPAYUNBIND),
                ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST + "/pay/mobile/41?");
        PAY_URL_MAP.put(String.valueOf(VipTpConstant.PAYMENT_CHANNEL_YEEPAYBIND),
                ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST + "/pay/mobile/42?");
        PAY_URL_MAP.put(String.valueOf(VipTpConstant.PAYMENT_CHANNEL_YEEPAYBIND),
                ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST + "/pay/mobile/42?");
        PAY_URL_MAP.put(String.valueOf(VipTpConstant.PAYMENT_CHANNEL_HUASHU),
                ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST + "/pay/mobile/46?");
    }

    /**
     * 根据支付渠道获取对应的支付URL
     * @param paymentChannel
     * @param locale
     * @return
     */
    public static String getPayUrlByPaymentChannel(String paymentChannel, Locale locale) {
        return PAY_URL_MAP.get(paymentChannel);
    }

    /**
     * 获取支付宝支付二维码URL
     * @param locale
     * @return
     */
    public static String getAliPayCodeUrl(Locale locale) {
        return GET_ALIPAY_CODE_URL;
    }

    /**
     * 获取支付宝支付图片类型的二维码URL 2014-10-15废弃
     * @param locale
     * @return
     */
    public static String getAliPayCodeImgUrl(Locale locale) {
        return GET_ALIPAY_CODE_LMG_URL;
    }

    /**
     * 获取国广整改后的支付宝图片类型二维码URL
     * @param locale
     * @return
     */
    public static String getAliPayCodeImgCibnUrl(Locale locale) {
        return GET_ALIPAY_CODE_LMG_CIBN_URL;
    }

    /**
     * 获取微信支付二维码URL
     * @param locale
     * @return
     */
    public static String getWeixinCodeUrl(Locale locale) {
        return GET_WEIXIN_CODE_URL;
    }

    /**
     * 获取拉卡拉支付二维码
     * @param locale
     * @return
     */
    public static String getLakalaPayUrl(Locale locale) {
        return LAKALAPAY_URL;
    }

    /**
     * 获取手机支付URL
     * @param locale
     * @return
     */
    public static String getPhonePayUrl(Locale locale) {
        return PHONEPAY_URL;
    }

    /**
     * 获取开通VIP URL、
     * @param locale
     * @return
     */
    public static String getConsumptionVipUrl(Locale locale) {
        return CONSUMPTION_VIP_URL;
    }

    /**
     * 获取单点支付--支付宝二维码图片
     * @param locale
     * @return
     */
    public static String getOrderPaycodeAlipayImgUrl(Locale locale) {
        return ORDER_PAYCODE_ALIPAY_IMG_URL;
    }

    /**
     * CIBN版获取单点支付--支付宝二维码图片
     * @param locale
     * @return
     */
    public static String getOrderPaycodeAlipayImgCIBNUrl(Locale locale) {
        return ORDER_PAYCODE_ALIPAY_IMG_CIBN_URL;
    }

    /**
     * 获取单点支付--微信二维码图片
     * @param locale
     * @return
     */
    public static String getOrderPaycodeWeinxinImgUrl(Locale locale) {
        return ORDER_PAYCODE_WEINXIN_IMG_URL;
    }

    /**
     * 获取微信支付二维码CIBN版本URL
     * @param locale
     * @return
     */
    public static String getWeixinCodeCibnUrl(Locale locale) {
        return GET_WEIXIN_PAYCODE_CIBN_URL;
    }

    /**
     * 获取单点支付--拉卡拉二维码图片
     * @param locale
     * @return
     */
    public static String getOrderPaycodeLakalaImgUrl(Locale locale) {
        return ORDER_PAYCODE_LAKALA_IMG_URL;
    }

    /**
     * check order status url
     */
    public final static String VIP_CHECK_ORDER_STATUS_URL_V2 = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/vip2.ldo?";

    public final static String VIP_CHECK_ORDER_STATUS_URL_V3 = ApplicationConstants.VIP_LE_BASE_HOST
            + "/act/p/engine/act/v1.0/orderConsumer/list?";

    public final static String VIP_CHECK_ORDER_STATUS_URL_TX = ApplicationConstants.LEPAY_BASE_HOST
            + "/order/m/list.json?";

    public final static String VIP_CHECK_ORDER_STATUS_URL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/sale.ldo?";

    /**
     * 获取订单详情URL
     * @param locale
     * @return
     */
    public final static String TP_SDK_CHECK_ORDER_STATUS_URL = ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST + ""
            + "/pay/querystat?";

    /**
     * 获取乐点开通VIP URL（兼容CIBN）
     * @param locale
     * @return
     */
    public static String getConsumptionVipByLetvPointUrl(Locale locale) {
        return CONSUMPTION_VIP_BY_LETV_POINT_URL;
    }

    /**
     * get bind info of machine and user
     */
    public final static String VIP_GET_DEVICE_BIND_INFO_URL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/deviceBind.ldo?";

    public final static String PRESENT_DEVICE_BIND_URL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/deviceBind.ldo?";

    public final static String RECEIVE_PRESENT_DEVICE_BIND_URL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/deviceBind.ldo?";

    /**
     * letvcard recharge url
     */
    public final static String VIP_RECHARGE_LETVCARD_URL = ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST
            + "/lecardrecharge?";

    /**
     * 获取机卡绑定用户信息查询URL
     * @param locale
     * @return
     */

    public static String getDeviceBindQueryUserUrl(Locale locale) {
        return DEVICE_BIND_QUERY_USER_URL;
    }

    /**
     * 获取生成支付宝二维码图片通用URL
     * @param locale
     * @return
     */
    @Deprecated
    public static String getPurchaseVipAliPaycodeUrl(Locale locale) {
        return PURCHASE_VIP_ALI_PAYCODE_URL;
    }

    /**
     * 获取生成国广版支付宝二维码图片通用URL
     * @param locale
     * @return
     */
    public static String getPurchaseVipAliPaycodeCIBNUrl(Locale locale) {
        return PURCHASE_VIP_ALI_PAYCODE_CIBN_URL;
    }

    /**
     * 获取生成微信二维码图片通用URL
     * @param locale
     * @return
     */
    public static String getPurchaseVipWeinxinPaycodeUrl(Locale locale) {
        return PURCHASE_VIP_WEIXIN_PAYCODE_URL;
    }

    /**
     * 获取生成拉卡拉二维码图片通用URL
     * @param locale
     * @return
     */
    public static String getPurchaseVipLakalaPaycodeUrl(Locale locale) {
        return PURCHASE_VIP_LAKALA_PAYCODE_URL;
    }

    /**
     * 获取乐点支付通用URL
     * @param locale
     * @return
     */
    @Deprecated
    public static String getPurchaseVipLetvpointPaymentUrl(Locale locale) {
        return PURCHASE_VIP_LETVPOINT_PAYMENT_URL;
    }

    /**
     * 获取活动信息URL
     * @param locale
     * @return
     */
    public static String getPaymentActivityUrl(Locale locale) {
        return GET_PAYMENT_ACTIVITY_URL;
    }

    /**
     * 第三方SDK直播付费鉴权URL
     */
    public final static String TPSDK_CHECK_LIVE_STATUS_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_TPSDK_CHECK_LIVE__URL);

    /**
     * 第三方SDK查询订单信息
     */
    public static final String CHECK_ORDER_STATUS_FROM_ZHIFU_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_ORDER_INFO);

    /**
     * 移动领先版--超级手机机卡绑定，查询手机赠送的高级会员绑定信息
     */
    public static final String QUERY_PRESENT_DEVICE_BIND_FOR_TV = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_QUERY_PRESENT_DEVICE_BIND);

    /**
     * 移动领先版--超级手机机卡绑定，激活手机赠送的高级会员绑定信息
     */
    public static final String RECEIVE_PRESENT_DEVICE_BIND_FOR_TV = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_REVEIVE_PRESENT_DEVICE_BIND);

    /**
     * 获取会员状态
     */
    public final static String VIP_GET_VIPTYPE_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_VIP_TYPE_URL);
    /**
     * 乐点支付是否参加活动开关，参加活动：1;不参加活动:0
     */
    public final static String PAYMENT_CHANNEL_LETVPOINT_SWITCH = ApplicationUtils
            .get(ApplicationConstants.VIP_CHECKOUT_COUNTER_PAYMENT_CHANNEL_LETVPOINT_SWITCH);
    /**
     * 验证充值卡密是否合法
     */
    public final static String GET_CARD_SECRET_URL_NEW = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_LETVPOINT_CARD_SECRET_VALIDATE);

    /**
     * 乐卡充值url
     */
    public final static String GET_RECHARGE_BY_LETV_CARD = ApplicationUtils
            .get(ApplicationConstants.BOSS_API_ZHIFU_LETVPOINT_CARD_RECHARGE);

    /**
     * 包月活动金额
     */
    public final static String VIP_MONTH_PAYMENT_AMOUNT = ApplicationUtils
            .get(ApplicationConstants.VIP_MONTH_PAYMENT_AMOUNT);

    /**
     * 获取用户定向弹窗信息接口URL
     */
    public final static String VIP_GET_RECOMMENDPOP_URL = ApplicationUtils
            .get(ApplicationConstants.BOSS_YUANXIAN_RECOMMENDPOP_URL);

    /**
     * 触达用户信息
     */
    public static final String TOUCH_USER_URL = ApplicationUtils
            .get(ApplicationConstants.USERCENTER_API_TOUCH__INFO_GET_BY_USERID);

    /**
     * 获取激活白条信息url
     */
    public static final String VIP_GET_FREEVIP_URL = ApplicationConstants.VIPCENTER_USER_TOUCH_BASE_HOST
            + "/activity/freesb";

    /**
     * 激活白条url
     */
    public static final String VIP_ACTIVE_FREEVIP_URL = ApplicationConstants.VIPCENTER_USER_TOUCH_BASE_HOST
            + "/activity/freewatch";

    /**
     * get vip center activity data
     */
    public static final String GET_VIPCENTER_DATA = ApplicationConstants.VIPCENTER_USER_TOUCH_BASE_HOST
            + "/activity/getdata";

    /**
     * get urm touch data
     */
    public static final String GET_URM_TOUCH_DATA = ApplicationConstants.URM_TOUCH_BASE_HOST + "/v1/messages";

    /**
     * get urm touch data
     */
    public static final String GET_URM_TOUCH_DATA_LIST = ApplicationConstants.URM_TOUCH_BASE_HOST
            + "/v1/batch/messages";

    /**
     * get guanxing promotion url
     */
    public final static String GET_GUANXING_PROMOTION_URL = ApplicationConstants.GUANXING_PROMOTION_BASE_HOST_URL
            + "/api/v1/promotion?";

    /**
     * 获取BOSS活动接口URL
     */
    public static final String GET_BOSS_ACTIVITY_DATA = ApplicationUtils
            .get(ApplicationConstants.GET_BOSS_ACTIVITY_DATA);

    public static final String GET_VIP_LIST_BY_TYPE_URL() {
        return ApplicationConstants.BOSS_YUANXIAN_BASE_HOST + "/letv/vip2.ldo";
    }

    /**
     * 根据会员id获取会员信息接口
     */
    public static final String GET_PRODUCT_BY_ID() {
        return ApplicationConstants.BOSS_YUANXIAN_BASE_HOST + "/letv/vip2.ldo";
    }

    /**
     * 根据会员id获取对应套餐接口
     */
    public static final String GET_PACKAGE_BY_PRODUCT_ID() {
        return ApplicationConstants.BOSS_YUANXIAN_BASE_HOST + "/letv/vip2.ldo";
    }

    /**
     * 统一套餐下单接口
     */
    public static final String SET_ORDER_BY_PRODUCT_ID() {
        return ApplicationConstants.BOSS_YUANXIAN_BASE_HOST + "/letv/vip2.ldo";
    }

    public final static String VIP_V2_PURCHASE_EXTEND_NOTIFY_URL_CN = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/vip2/payNotify.ldo";

    /**
     * 根据会员套餐ID获取活动列表接口
     */
    public static final String GET_ACTIVITY_BY_PACKAGE_ID() {
        return ApplicationConstants.BOSS_YUANXIAN_BASE_HOST + "/letv/vip2.ldo";
    }

    // getProductById
    public static final String GET_PACKAGE_BY_ID() {
        return ApplicationConstants.BOSS_YUANXIAN_BASE_HOST + "/letv/vip2.ldo";
    }

    // getProductById
    public static final String GET_PACKAGE_INFO_BY_ID() {
        return ApplicationConstants.BOSS_YUANXIAN_BASE_HOST + "/letv/vip2.ldo";
    }

    // live module via boss
    public static final String GET_LIVE_HOST() {
        return ApplicationConstants.BOSS_YUANXIAN_BASE_HOST + "/letv/vip2.ldo";
    }

    // live module via boss
    public static final String GET_PAYINFO_HOST() {
        return ApplicationConstants.BOSS_YUANXIAN_BASE_HOST + "/letv/vip2.ldo";
    }

    /**
     * 美国版boss统一鉴权url
     */
    // public static final String BOSS_VALIDATE_PLAY_SERVICE_URL =
    // ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
    // +"/letv/vip2.ldo?type=validate";

    /**
     * 美国版，根据内容ID，获取对应的“会员售卖套餐”接口
     */
    // public static final String BOSS_GET_PACKAGE_BY_CONTENT_URL =
    // ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
    // +"/letv/vip2.ldo?type=packageByContent";

    /**
     * 美国版，Boss V2接口公共URI
     */
    public static final String BOSS_YUANXIAN_V2_BASE_URL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/vip2.ldo?";

    /**
     * 美国版，Boss V2接口公共URI
     */
    public static final String BOSS_YUANXIAN_V2_DEVICE_BIND_BASE_URL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/v2/deviceBind.ldo?";

    private static final String BOSS_YUANXIAN_BASEURL = "boss.yuanxian.baseurl";
    public static final String BOSS_YUANXIAN_BASE_HOST = ApplicationUtils.get(BOSS_YUANXIAN_BASEURL);// http://yuanxian.letv.com

    public static final String LEPAY_VIP_GETTIME_URL = ApplicationConstants.LEPAY_BASE_HOST
            + "/open_api/vip/m/get_time_new.json";

    public static final Integer VIP_INDIA = 101; // 印度会员

    /**
     * 美国会员类型
     */
    public enum Type_Group_US {
        BASIC(101), // basic包
        ADD_ON(102), // add_on包
        SITE_OFF(103); // 站外会员包(hulu等)
        private Integer code;

        private Type_Group_US(Integer code) {
            this.code = code;
        }

        /*
         * find type by code
         */
        public static Type_Group_US findType(Integer code) {
            for (Type_Group_US typeGroup : Type_Group_US.values()) {
                if (typeGroup.code.equals(code)) {
                    return typeGroup;
                }
            }
            return null;
        }

        public Integer getCode() {
            return this.code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return this.code + "";
        }
    }

    /**
     * 大陆会员类型：<p>
     * 影视会员 301<p>
     * 体育会员 302<p>
     * 音乐会员 303
     */
    public enum Type_Group_CN {
        TV(301), // 影视会员
        SPORT(302), // 体育会员
        MUSIC(303); // 音乐会员
        private Integer code;

        private Type_Group_CN(Integer code) {
            this.code = code;
        }

        /*
         * find type by code
         */
        public static Type_Group_CN findType(Integer code) {
            for (Type_Group_CN typeGroup : Type_Group_CN.values()) {
                if (typeGroup.code.equals(code)) {
                    return typeGroup;
                }
            }
            return null;
        }

        public Integer getCode() {
            return this.code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return this.code + "";
        }
    }

    /**
     * 大陆影视会员对应productId值
     */
    public enum PRODUCT_ID_CN {
        SUPERTV(9), // 超级影视会员
        MOBILE(1); // 乐次元
        private Integer code;

        private PRODUCT_ID_CN(Integer code) {
            this.code = code;
        }

        /*
         * find type by code
         */
        public static PRODUCT_ID_CN findType(Integer code) {
            for (PRODUCT_ID_CN typeGroup : PRODUCT_ID_CN.values()) {
                if (typeGroup.code.equals(code)) {
                    return typeGroup;
                }
            }
            return null;
        }

        public Integer getCode() {
            return this.code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return this.code + "";
        }
    }

    /**
     * Boss对应国家字典
     */
    public enum Country {
        US(1),
        CN(86),
        HK(852),
        IN(91),
        RU(7);
        private Integer code;

        private Country(Integer code) {
            this.code = code;
        }

        /*
         * find type by code
         */
        public static Country findType(Integer code) {
            for (Country country : Country.values()) {
                if (country.code.equals(code)) {
                    return country;
                }
            }
            return null;
        }

        /*
         * find type by code
         */
        public static Country findType(String wcode) {
            if (LocaleConstant.Wcode.US.equalsIgnoreCase(wcode)) {
                return US;
            } else if (LocaleConstant.Wcode.CN.equalsIgnoreCase(wcode)) {
                return CN;
            } else if (LocaleConstant.Wcode.HK.equalsIgnoreCase(wcode)) {
                return HK;
            } else if (LocaleConstant.Wcode.IN.equalsIgnoreCase(wcode)) {
                return IN;
            } else if (LocaleConstant.Wcode.RU.equalsIgnoreCase(wcode)) {
                return RU;
            }
            return CN; // 默认返回cn
        }

        public Integer getCode() {
            return this.code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return this.code + "";
        }
    }

    /**
     * Boss对应内容类型ContentType<p>
     * 专辑 1<p>
     * 轮播台 2<p>
     * 视频 3<p>
     * 直播频道 5<p>
     * 体育赛事 4<p>
     * 会员 100
     * 直播票(券) 200
     */
    public enum ContentType {
        ALBUM(1),
        VIDEO(3),
        SPORT(4),
        LIVE(5),
        VIP(100),
        LIVE_TICKET(200);

        private Integer code;

        private ContentType(Integer code) {
            this.code = code;
        }

        /*
         * find type by code
         */
        public static ContentType findType(Integer code) {
            for (ContentType contentType : ContentType.values()) {
                if (contentType.code.equals(code)) {
                    return contentType;
                }
            }
            return null;
        }

        public Integer getCode() {
            return this.code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return this.code + "";
        }
    }

    /**
     * Boss对应终端类型TerminalType<p>
     * 超级电视 141007<p>
     * 超级手机 141004
     */
    public enum BossTerminalType {
        TV(141007), // 超级电视
        SUPER_MOBILE(141004); // 超级手机
        private Integer code;

        private BossTerminalType(Integer code) {
            this.code = code;
        }

        /*
         * find type by code
         */
        public static BossTerminalType findType(Integer code) {
            for (BossTerminalType terminalType : BossTerminalType.values()) {
                if (terminalType.code.equals(code)) {
                    return terminalType;
                }
            }
            return null;
        }

        public Integer getCode() {
            return this.code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return this.code + "";
        }
    }

    /**
     * bussinessId和signKey的对应值
     */
    public enum BussinessIdAndSignKey {
        LECOM(10013, "SnAeArgNqnxZ4CPLv4LWYBmHnhDe1scc"), // TV le
        LEVIDI(10019, "mbac0054676d9ebs178d22273f7c38d0"), // levidi
        CIBN(10031, "3effec38e0b12860b9113abcc60da273"), // cibn
        LEPAY_CIBN(4, "wq0euzrccodqjcjaihxngx0cwhdr49q9"), // lepay-cibn
        DEFAULT(10031, "3effec38e0b12860b9113abcc60da273");
        private Integer bussinessId;
        private String signKey;

        private BussinessIdAndSignKey(Integer bussinessId, String signKey) {
            this.bussinessId = bussinessId;
            this.signKey = signKey;
        }

        public static BussinessIdAndSignKey getBussinessIdAndSignKey(CommonParam commonParam) {
            if (commonParam != null) {
                if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                        .getTerminalApplication())) {
                    return LECOM;
                } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(commonParam
                        .getTerminalApplication())) {
                    return LEVIDI;
                } else if (commonParam.getBroadcastId() != null
                        && commonParam.getBroadcastId().intValue() == CommonConstants.CIBN) {
                    return CIBN;
                }
            }
            return DEFAULT;
        }

        public String getSignKey() {
            return signKey;
        }

        public void setSignKey(String signKey) {
            this.signKey = signKey;
        }

        public Integer getBussinessId() {
            return bussinessId;
        }

        public void setBussinessId(Integer bussinessId) {
            this.bussinessId = bussinessId;
        }
    }

    /**
     * 商户VIP映射关系
     * 1-体验会员，2-乐次元影视会员，3-超级影视会员，4-超级家庭会员，5-国广CIBN会员，6-华数会员，7-芒果会员，8-腾讯会员
     */
    public enum LEPAY_VIP_TYPE {
        DEMO(1, ""),
        LE_MOVIE(2, ""),
        LE_TV(3, "511703141887278862336"),
        LE_HOME(4, "511803124110985523245056"),
        LE_CIBN(5, "511703141886101704704"),
        LE_HUASU(6, "511703141884168916992"),
        LE_MANGO(7, "511708211815898872758272"),
        LE_TENCENT(8, "511803124111241400426496"),

        // XXX_TT => 测试商户编号
        DEMO_(1, ""),
        LE_MOVIE_TT(2, ""),
        LE_TV_TT(3, "511703141887278862336"),
        LE_HOME_TT(4, "511801083399039277731840"),
        LE_CIBN_TT(5, "511487568308537788369744035181"),
        LE_HUASU_TT(6, "511487569155542382567151195224"),
        LE_MANGO_TT(7, "511708231837041023979520"),
        LE_TENCENT_TT(8, "511801313661615726465024");
        private Integer type;
        private String vendor;

        private LEPAY_VIP_TYPE(Integer type, String vendor) {
            this.type = type;
            this.vendor = vendor;
        }

        public static LEPAY_VIP_TYPE getVipByType(boolean test, Integer type) {
            for (LEPAY_VIP_TYPE vipType : LEPAY_VIP_TYPE.values()) {
                if (test) {
                    if ((vipType.name().lastIndexOf("_TT") > 0) && vipType.getType().intValue() == type.intValue()) {
                        return vipType;
                    }
                } else {
                    if ((vipType.name().lastIndexOf("_TT") == -1) && vipType.getType().intValue() == type.intValue()) {
                        return vipType;
                    }
                }
            }
            return DEMO;
        }

        public static LEPAY_VIP_TYPE getVipByVendor(boolean test, String vendor) {
            for (LEPAY_VIP_TYPE vipType : LEPAY_VIP_TYPE.values()) {
                if (test) {
                    if ((vipType.name().lastIndexOf("_TT") > 0) && vipType.getVendor().equals(vendor)) {
                        return vipType;
                    }
                } else {
                    if ((vipType.name().lastIndexOf("_TT") == -1) && vipType.getVendor().equals(vendor)) {
                        return vipType;
                    }
                }
            }
            return DEMO;
        }

        public static String getVipProducts(boolean test, String vendor) {
            StringBuilder sb = new StringBuilder();
            for (LEPAY_VIP_TYPE vipType : LEPAY_VIP_TYPE.values()) {
                if (test) {
                    if ((vipType.name().lastIndexOf("_TT") > 0)) {
                        if (null != vendor && !vipType.getVendor().equals(vendor)
                                || StringUtil.isBlank(vipType.getVendor())) {
                            continue;
                        }
                        if (sb.length() > 0) {
                            sb.append(",");
                        }
                        if (vipType.name().indexOf("LE_MOVIE") != -1) {
                            sb.append(vipType.getVendor()).append(":").append(PRODUCT_ID_CN.MOBILE.getCode());
                        } else if (vipType.name().indexOf("LE_TV") != -1) {
                            sb.append(vipType.getVendor()).append(":").append(PRODUCT_ID_CN.SUPERTV.getCode());
                        } else {
                            sb.append(vipType.getVendor()).append(":").append(PRODUCT_ID_CN.MOBILE.getCode());
                        }
                    }
                } else {
                    if ((vipType.name().lastIndexOf("_TT") == -1)) {
                        if (null != vendor && !vipType.getVendor().equals(vendor)
                                || StringUtil.isBlank(vipType.getVendor())) {
                            continue;
                        }
                        if (sb.length() > 0) {
                            sb.append(",");
                        }
                        if (vipType.name().indexOf("LE_MOVIE") != -1) {
                            sb.append(vipType.getVendor()).append(":").append(PRODUCT_ID_CN.MOBILE.getCode());
                        } else if (vipType.name().indexOf("LE_TV") != -1) {
                            sb.append(vipType.getVendor()).append(":").append(PRODUCT_ID_CN.SUPERTV.getCode());
                        } else {
                            sb.append(vipType.getVendor()).append(":").append(PRODUCT_ID_CN.MOBILE.getCode());
                        }
                    }
                }
            }
            return sb.toString();
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }
    }
}
