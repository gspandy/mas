package com.letv.mas.caller.iptv.tvproxy.vip;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class VipConstants {

    /**
     * 会员模块业务编码，1--充值记录，2--会员信息，3--消费记录，4--支付，5--易宝支付
     */
    public static final int RECHRAGE_RECORD = 1;
    public static final int ACCOUNT_VIP = 2;
    public static final int CONSUMPTION_RECORD = 3;
    public static final int PAY = 4;
    public static final int YEEPAY = 5;
    public static final int MOVIE_TICKET = 6;

    /**
     * 响应状态码
     */
    public static final int RESPONSE_FAILURE = 0;
    public static final int RESPONSE_SUCCESS = 1;

    public final static String PRICE_PACKAGE_TYPE_NAME = "PRICE_PACKAGE_TYPE_NAME"; // 获取产品包列表名称
    public final static String PRICE_PACKAGE_TYPE_DESC = "PRICE_PACKAGE_TYPE_DESC"; // 获取产品包列表描述
    public final static String LOTTERY_REDIS_CALLBACK_URL = "lottery_callback_url"; // 续费抽奖活动回调URL
    public final static String LOTTERY_REDIS_START_TIME = "lottery_begin_time"; // 续费抽奖活动生效时间
    public final static String LOTTERY_REDIS_END_TIME = "lottery_end_time"; // 续费抽奖活动失效时间
    public final static String ACTIVITY_POPUP_SWITCH = "activity.popup.switch"; // 全屏弹窗活动开关

    /**
     * 二维码返回结果类型
     */
    public static final int PAYCODETYPE_ALIPAY_PAGE = 1; // 支付宝二维码网页
    public static final int PAYCODETYPE_ALIPAY_IMG = 2; // 支付宝二维码图片
    public static final int PAYCODETYPE_WEIXIN_IMG = 3; // 微信二维码图片
    public static final int PAYCODETYPE_LAKALA_IMG = 4; // 拉卡拉二维码图片

    /**
     * “康佳30天限免”需求新增，iskonka--是否是康佳机型，“0”--不是，“1”--是
     */
    public static final String ISKONKA_YES = "1";

    /**
     * 定向弹窗活动对象的用户名文件
     */
    public static final String DIRECTIONAL_PUSH_USERNAME_FILENAME = "directionalPushUser.txt";

    /**
     * 定向弹窗活动资格。0--不符合条件，没有资格，1--符合条件，有资格
     */
    public static final int DIRECTIONAL_PUSH_USER_QUALIFICATION_UNAVAILABLE = 0;
    public static final int DIRECTIONAL_PUSH_USER_QUALIFICATION_AVAILABLE = 1;

    /**
     * TV版播控方，参见CommonConstants, DataConstant；暂不使用，而使用公共类中的数据定义
     */
    public static final int BROADCAST_LETV = 0;
    public static final int BROADCAST_CNTV = 1;
    public static final int BROADCAST_CIBN = 2;
    public static final int BROADCAST_WASU = 3;

    /**
     * CIBN版本的terminalApplication和terminalBrand参数值
     */
    // public static String TERMINAL_APPLICATION_CIBN = "media_cibn";
    public static String TERMINAL_BRAND_CIBN = "media";

    public static final long CRITICAL_LOTTERY_QRCODE_EXPIRE_MIN = 0L;
    /**
     * 抽奖活动二维码默认有效时长
     */
    public static final long DEFAULT_LOTTERY_QRCODE_EXPIRE = 300L;

    /**
     * 机卡绑定套餐是否已激活，0--数据不可用，1--未激活，可领取；2--已激活，不可领取
     */
    public static final int DEVICE_BIND_STATUS_ILLEGAL = 0;
    public static final int DEVICE_BIND_STATUS_UNACTIVATED = 1;
    public static final int DEVICE_BIND_STATUS_ACTIVATED = 2;

    /**
     * 乐点直充--充值兑换结果状态码，0--购买失败（原因不明）；1--购买成功；2--非法参数；3--乐点余额不足；4--获取套餐包信息失败，5--
     * 下单失败；6--支付失败
     */
    public static final int LETVPOINT_CONSUME_VIP_FAILURE = 0;
    public static final int LETVPOINT_CONSUME_VIP_SUCCEED = 1;
    public static final int LETVPOINT_CONSUME_VIP_ILLEGAL_PARAMETERS = 2;
    public static final int LETVPOINT_CONSUME_VIP_LETVPOINT_INSUFFICIENT = 3;
    public static final int LETVPOINT_CONSUME_VIP_PRODUCT_FAILURE = 4;
    public static final int LETVPOINT_CONSUME_VIP_OEDER_FAILURE = 5;
    public static final int LETVPOINT_CONSUME_VIP_PAY_FAILURE = 6;

    /**
     * 通用字符串，无实际意义
     */
    public static final String COMMOM_DEFAULT_STRING_0 = "0";
    public static final String COMMOM_DEFAULT_STRING_1 = "1";

    /**
     * 缓存用户不可以参加一元包月活动的主键前缀
     */
    public static final String VIP_CANNOT_ONEFORMONTH_KEY_PREFIX = "yeepay";
    public static final String VIP_CANNOT_ONEFORMONTH_VALUE = "0";
    /**
     * 如果用户可以参加包月活动，缓存包月金额、文案
     */
    public static final String VIP_MONTH_PAYMENT_AMOUNT_KEY = "vipMonthPaymentAcount";
    public static final String VIP_MONTH_PAYMENT_TEXT_KEY = "vipMonthPaymentText";
    /**
     * 代金券接口默认适用产品类型，
     */
    public static final String VOUCHER_STATUS_DEFAULT_TYPES = "2,3";

    /**
     * 收银台用户协议文案文件通用名（不包含后缀）
     */
    public static final String VIP_USER_AGGREMENT_COMMON_FILENAME = "vip_user_aggrement_";

    /**
     * 收银台用户协议文案文件通用名（不包含后缀）
     */
    public static final String VIP_USER_AGGREMENT_FILENAME_POSTFIX = ".txt";

    /**
     * 收银台套餐默认排序策略，使用套餐id和英文逗号拼接形式
     */
    public static final String PRICE_PACKAGE_DEFAULT_ID_ORDER = "5,2,3,4";
    public static final String PRICE_PACKAGE_ID_ORDER = StringUtils.isNotBlank(ApplicationUtils
            .get(ApplicationConstants.VIP_CHECKOUT_COUNTER_PACKAGE_DEFAULT_ORDER)) ? ApplicationUtils
            .get(ApplicationConstants.VIP_CHECKOUT_COUNTER_PACKAGE_DEFAULT_ORDER) : PRICE_PACKAGE_DEFAULT_ID_ORDER;

    /**
     * 套餐包顺序放缓村的key值
     */
    public static final String PACKAGE_ORDER_KEY = "packageorder";

    /**
     * 默认的产品类型列表
     */
    public static final String DEFAULT_PRODUCT_TYPE_LIST = "1,2,3";

    /**
     * 获取收银台产品类型排序列表
     */
    public static final String VIP_PRODUCT_TYPE_SORT_ORDER_DEFAULT = "12,1";
    /**
     * 产品类型排序参数key
     */
    public static final String VIP_PRODUCT_TYPE_SORT_ORDER = "vip.product.type.sort.order";

    public static final String VIP_PRODUCT_TYPES_DEFAULT = "12,1";
    // 所支持的套餐类型，写在配置文件里，多个类型用英文逗号隔开，1--包月，12--包年
    public static final String VIP_PRODUCT_TYPES = "vip.product.types";
    // 美国行货要限制所展示的套餐，所以通过配置文件控制哪些套餐可展示
    public static final String VIP_PRODUCT_PACKAGES_US = "vip.product.packages.us";
    public static final String VIP_PACKAGE_CURRENT_PRICE_US = "vip.package.current.price.";// 配置文件中现价的前缀
    public static final String VIP_PACKAGE_ORIGIN_PRICE_US = "vip.package.origin.price.";// 配置文件中原价的前缀
    public static final String VIP_PACKAGE_DESC_US = "vip.package.desc.";// 配置文件中描述的前缀

    public static final String PRODUCT_TYPE_TEXT_PREFIX_KEY = "PRODUCT_TYPE_NAME_";
    public static final String PRODUCT_TYPE_UINT_PREFIX_KEY = "PRODUCT_TYPE_UNIT_";
    public static final String VIP_CHECKOUT_COUNTER_DEFAULT_ACTIVITY_KEY = "vip.checkout.counter.default.activity";
    public static final String VIP_CHECKOUT_COUNTER_DEFAULT_IMG = "vip.checkout.counter.default.img";
    public static final String VIP_CHECKOUT_COUNTER_DEFAULT_IMG_VIDEO_SALE = "vip.checkout.counter.default.img.video.sale";

    public final static String VIP_CHECKOUTCOUNTER_TYPE_COMMON = "common";// 标准收银台
    public final static String VIP_CHECKOUTCOUNTER_TYPE_DIRECT = "direct";// 定向收银台

    /**
     * 单点收银台默认支付渠道
     */
    public static final String PAYMENT_CHANNEL_DEFAULT_1 = ApplicationUtils
            .get("vip.checkout.counter.payment.channel.single");

    /**
     * 套餐收银台默认支付渠道
     */
    public static final String PAYMENT_CHANNEL_DEFAULT_2 = ApplicationUtils
            .get("vip.checkout.counter.payment.channel.package");

    /**
     * 直播收银台默认支付渠道
     */
    public static final String PAYMENT_CHANNEL_DEFAULT_3 = ApplicationUtils
            .get("vip.checkout.counter.payment.channel.live");

    /**
     * 直播鉴权结果，0--无播放权限，1--有播放权限，2--可以试看，3--试看结束
     */
    public static final int CHECK_LIVE_NO_PLAY_PERMISSSION = 0;
    public static final int CHECK_LIVE_HAVE_PLAY_PERMISSSION = 1;
    public static final int CHECK_LIVE_HAVE_TRY_PLAY_PERMISSSION = 2;
    public static final int CHECK_LIVE_NO_TRY_PLAY_PERMISSSION = 3;

    /**
     * 2.7.0 CIBN消费记录文案“高级VIP会员”修改为“TV影视会员”
     */
    public static final String CONSUME_RECORD_ORDERNAME_SRC_TEXT = "高级VIP会员";
    public static final String CONSUME_RECORD_ORDERNAME_TARGET_TEXT = "TV影视会员";
    public static final String VIP_RECEIVE_PRESENTDEVICE_TITLE_KEY = "VIP_RECEIVE_PRESENTDEVICE_VIP_TITLE";// 领取机卡设备会员文案
    public static final String VIP_PRESENTDEVICE_SUPER_MOBILE_TEXT_KEY = "VIP_PRESENTDEVICE_SUPER_MOBILE_TEXT";// 乐视手机
    public static final String VIP_ONE_YUAN_BUY_MONTHLY_TITLE_KEY = "VIP_ONE_YUAN_BUY_MONTHLY_TITLE";

    /**
     * 一键支付绑定查询，0--未绑paypal，1--已绑paypal
     */
    public static final int ONE_KEY_PAY_CHECK_PAYPAL_NOT_BIND = 0;
    public static final int ONE_KEY_PAY_CHECK_PAYPAL_HAS_BIND = 1;

    /**
     * 直播SDK专用通用参数
     */
    // public static final String TERMINAL_APPLICATION_TPSDK = "letv_live_sdk";
    public static final String BSCHANNEL_TPSDK = "hisense";
    public static final String TPSDK_BSCHANNEL_COMMON_PREFIX = "TPSDK_BSCHANNEL_";
    public static final String APPTYPE_LETV_LIVE_SDK = "tpsdk";// 第三方直播SDK
    public static final String APPTYPE_LETV = "letv";// 乐视TV

    /**
     * 自定义订单状态，0--未支付，1--支付成功，2--支付失败，3--订单失效或异常（如已退款等，不表示订单不存在）
     */
    public static final int ORDER_STATUS_NOT_PAY = 0;
    public static final int ORDER_STATUS_PAY_SUCCESS = 1;
    public static final int ORDER_STATUS_PAY_FAILED = 2;
    public static final int ORDER_STATUS_INVALID = 3;

    /**
     * 机卡绑定查询类型，0--查询所有（本机机卡和赠送机卡），1--本机机卡，2--赠送机卡
     */
    // all bind info for the machine and the user
    public static final String DEVICE_BIND_QUERY_TYPE_0 = "0";
    // current machine bind info
    public static final String DEVICE_BIND_QUERY_TYPE_1 = "1";
    // present bind info from other machine
    public static final String DEVICE_BIND_QUERY_TYPE_2 = "2";

    /**
     * 机卡绑定查询类型Int类型，0--查询所有（本机机卡和赠送机卡），1--本机机卡，2--赠送机卡
     */
    // all bind info for the machine and the user
    public static final int DEVICE_BIND_QUERY_INT_TYPE_0 = 0;
    // current machine bind info
    public static final int DEVICE_BIND_QUERY_INT_TYPE_1 = 1;
    // present bind info from other machine
    public static final int DEVICE_BIND_QUERY_INT_TYPE_2 = 2;

    /**
     * 机卡绑定领取类型，1--领取自带机卡，2--领取赠送机卡
     */
    public static final int DEVICE_BIND_RECEIVE_TYPE_1 = 1;
    public static final int DEVICE_BIND_RECEIVE_TYPE_2 = 2;

    /**
     * 定向弹窗打开类型字符串，从BOSS接收到的
     */
    public static final String VIP_RECOMMEND_POP_OPENTYPE_NATIVE = "native";
    public static final String VIP_RECOMMEND_POP_OPENTYPE_H5 = "h5";

    /**
     * 定向弹窗打开类型数值，返给客户端的，1--跳本地页面，2--打开H5页面
     */
    public static final int VIP_RECOMMEND_POP_OPENTYPE_1 = 1;
    public static final int VIP_RECOMMEND_POP_OPENTYPE_2 = 2;

    public static final int VIP_TYPE_NOT_LOGIN = -1;// 未登录，自定义的会员状态
    public static final int VIP_TYPE_0 = 0;// 暂未定义，目前只知道5代表包年
    public static final int VIP_TYPE_5 = 5;// 包年会员

    /**
     * 返回给客户端的卡类型
     */
    public static final int CARD_TYPE_1 = 1;// 充值码
    public static final int CARD_TYPE_2 = 2;// 兑换码
    public static final int CARD_TYPE_3 = 3;// 超级电视兑换码
    public static final int CARD_TYPE_4 = 4;// 超级手机兑换码

    /**
     * BOSS返回的卡类型
     */
    public static final String CARD_TYPE_BOSS_1 = "1";
    public static final String CARD_TYPE_BOSS_2 = "2";
    public static final String CARD_TYPE_BOSS_35 = "35";
    public static final String CARD_TYPE_BOSS_45 = "45";

    /**
     * 会员模块
     */
    public final static String VIP_PAYMENTACTIVITY_LETVPOINTPAYTEXT = "VIP_PAYMENTACTIVITY_LETVPOINTPAYTEXT";

    public final static String VIP_LETV_CARD_ERROR_CODE_PREFIX = "LETV_CARD_ERROR_CODE_";

    /**
     * 乐卡兑换充值相关文案
     */
    public final static String VIP_LETV_CARD_EXCHANGE_SUCCESS_TEXT = "VIP_LETV_CARD_EXCHANGE_SUCCESS_TEXT";
    public final static String VIP_LETV_CARD_EXCHANGE_FAILURE_TEXT = "VIP_LETV_CARD_EXCHANGE_FAILURE_TEXT";
    public final static String VIP_LETV_CARD_RECHARGE_SUCCESS_TEXT = "VIP_LETV_CARD_RECHARGE_SUCCESS_TEXT";
    public final static String VIP_LETV_CARD_RECHARGE_FAILURE_TEXT = "VIP_LETV_CARD_RECHARGE_FAILURE_TEXT";
    public final static String LETV_CARD_NAME_1 = "LETV_CARD_NAME_1";
    public final static String LETV_CARD_NAME_2 = "LETV_CARD_NAME_2";

    /**
     * Mainland purchase extend parameter
     */
    public final static String VIP_PURCHASE_EXTEND_SERVICE_CN = "lepay.tv.api.show.cashier";
    public final static String VIP_PURCHASE_EXTEND_NOTIFY_URL_CN = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/newPay2.ldo";
    public final static String VIP_PURCHASE_EXTEND_BUSINESS_ID_CN = "78";
    public final static String VIP_PURCHASE_EXTEND_BUSINESS_CODE = "vip.purchase.extend.business.id";
    public final static String VIP_PURCHASE_EXTEND_BUSINESS_ID = ApplicationUtils.get(
            VIP_PURCHASE_EXTEND_BUSINESS_CODE, VIP_PURCHASE_EXTEND_BUSINESS_ID_CN);
    public final static String VIP_PURCHASE_EXTEND_CURRENCY_CN = "RMB";
    public final static String VIP_PURCHASE_EXTEND_INPUT_CHARSET = "UTF-8";
    public final static String VIP_PURCHASE_EXTEND_PRODUCT_URLS_CN = ApplicationConstants.BOSS_ZHIFU_BASE_HOST + "/";

    /**
     * common purchase extend parameter
     */
    public final static String VIP_PURCHASE_EXTEND_COMMON_BUSINESS_CODE = "vip.purchase.extend.common.business.id";
    public final static String VIP_PURCHASE_EXTEND_COMMON_BUSINESS_ID = ApplicationUtils
            .get(VIP_PURCHASE_EXTEND_COMMON_BUSINESS_CODE);

    /**
     * HongKong purchase extend parameter
     */
    public final static String VIP_PURCHASE_EXTEND_SERVICE_HK = "lepay.pc.api.show.cashier.hk";
    public final static String VIP_PURCHASE_EXTEND_NOTIFY_URL_HK = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/newPay2.ldo";
    public final static String VIP_PURCHASE_EXTEND_BUSINESS_ID_HK = "810";
    public final static String VIP_PURCHASE_EXTEND_CURRENCY_HK = "HKD";
    public final static String VIP_PURCHASE_EXTEND_PRODUCT_URLS_HK = ApplicationConstants.BOSS_ZHIFU_BASE_HOST + "/";
    public final static String VIP_PURCHASE_EXTEND_LOCAL_HK = "hk";
    public final static String VIP_PURCHASE_EXTEND_LANGUAGE_HK = "0";// 0--Traditional,1--Simplified

    /**
     * US purchase extend parameter
     */
    public final static String VIP_PURCHASE_EXTEND_SERVICE_US = "lepay.tv.api.show.cashier.us";
    public final static String VIP_PURCHASE_EXTEND_NOTIFY_URL_US = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/newPay2.ldo";
    public final static String VIP_PURCHASE_EXTEND_BUSINESS_ID_US = "822";
    public final static String VIP_PURCHASE_EXTEND_CURRENCY_US = "USD";
    public final static String VIP_PURCHASE_EXTEND_PRODUCT_URLS_US = ApplicationConstants.BOSS_ZHIFU_BASE_HOST + "/";
    public final static String VIP_PURCHASE_EXTEND_LOCAL_US = "us";
    public final static String VIP_PURCHASE_EXTEND_LANGUAGE_US = "1";// 0--Traditional,1--Simplified

    /**
     * 体育会员 1.体育会员 2.过期体育会员
     */
    public static final int SPORT_VIP_NON = 0;
    public static final int SPORT_VIP = 1;
    public static final int SPORT_VIP_EXPIRE = 2;

    /**
     * 影视会员 0: 非会员 1:普通会员 2:高级会员
     */
    public static final int MOVIE_VIP_NON = 0;
    public static final int MOVIE_VIP_NORMAL = 1;
    public static final int MOVIE_VIP_SUPER = 2;

    /**
     * 时长类型 1:年 2:月 5:天
     */
    public static final int DURATION_TYPE_YEAR = 1;
    public static final int DURATION_TYPE_MONTH = 2;
    public static final int DURATION_TYPE_DAY = 5;

    /**
     * Boss接口V2中影视会员类型的定义
     */
    public static final int VIDEO_VIP_TYPE = 301;

    /**
     * 套餐价格计算平均每月多少钱的提示文案
     */
    public final static String VIP_PACKAGE_AVERAGE_PERMONTH_TEXT = "VIP_PACKAGE_AVERAGE_PERMONTH_TEXT";
    public final static String VIP_PACKAGE_AVERAGE_PERYEAR_TEXT = "VIP_PACKAGE_AVERAGE_PERYEAR_TEXT";
    public final static String VIP_PACKAGE_AVERAGE_PERDAY_TEXT = "VIP_PACKAGE_AVERAGE_PERDAY_TEXT";

    /**
     * 激活成功页的一些服务端配置文案
     */
    public final static String VIP_ACTIVE_FREEVIP_TITLE = "VIP_ACTIVE_FREEVIP_TITLE";// 激活成功页标题
    public final static String VIP_ACTIVE_FREEVIP_DESC = "VIP_ACTIVE_FREEVIP_DESC";// 激活成功页会员有效期描述
    public final static String VIP_ACTIVE_FREEVIP_PAY_BUTTON = "VIP_ACTIVE_FREEVIP_PAY_BUTTON";// 激活成功页的按钮
    public final static String VIP_ACTIVE_FREEVIP_DELAY_DESC = "VIP_ACTIVE_FREEVIP_DELAY_DESC";// 激活成功页权益描述
    public final static String VIP_ACTIVE_FREEVIP_DELAY_NOTICE = "VIP_ACTIVE_FREEVIP_DELAY_NOTICE";// 激活成功页权益声明

    public final static String VIP_FREEVIP_BUTTON = "VIP_FREEVIP_BUTTON";// 白条第一页的按钮
    public final static String VIP_FREEVIP_RULE_TITLE = "VIP_FREEVIP_RULE_TITLE";// 白条规则的标题
    public final static String VIP_FREEVIP_RULE_BUTTON = "VIP_FREEVIP_RULE_BUTTON";// 白条规则页面的按钮
    public final static String VIP_FREEVIP_STATEMENT_TITLE = "VIP_FREEVIP_STATEMENT_TITLE";// 免责声明的标题
    public final static String VIP_FREEVIP_STATEMENT_BUTTON = "VIP_FREEVIP_STATEMENT_BUTTON";// 免责声明页面的按钮
    public final static String VIP_FREEVIP_PAYBACK_TITLE = "VIP_FREEVIP_PAYBACK_TITLE";// 催还款标题
    public final static String VIP_FREEVIP_PAYBACK_TITLE_UNIT = "VIP_FREEVIP_PAYBACK_TITLE_UNIT";// 催还款标题单位
    public final static String VIP_FREEVIP_PAYBACK_STATEMENT = "VIP_FREEVIP_PAYBACK_STATEMENT";// 催还款声明
    public final static String VIP_FREEVIP_PAYBACK_CURRENCY = "VIP_FREEVIP_PAYBACK_CURRENCY";// 催还款价格单位

    public final static String VIP_ACTIVITY_NOT_SUPPORT_VOUCHER = "VIP_ACTIVITY_NOT_SUPPORT_VOUCHER";// 当前活动不支持代金券

    /**
     * vip--purchaseVip接口中，是否显示会员有效期截至时间开关，1--显示，0--不显示
     */
    public final static String VIP_PURCHASE_VIP_SHOW_END_TIME_SWITCH_ON = "1";
    public final static boolean VIP_PURCHASE_VIP_SHOW_END_TIME = StringUtils.equals(
            VIP_PURCHASE_VIP_SHOW_END_TIME_SWITCH_ON,
            ApplicationUtils.get(ApplicationConstants.VIP_PURCHASE_VIP_SHOW_END_TIME_SWITCH));

    /**
     * 服务端收银台针对套餐进行排序开关
     */
    public final static String VIP_CHECKOUT_COUNTER_SORT_PACKAGE_SWITCH_ON = "1";
    public final static boolean VIP_CHECKOUT_COUNTER_NEED_SORT_PACKAGE = StringUtils.equals(
            VIP_CHECKOUT_COUNTER_SORT_PACKAGE_SWITCH_ON,
            ApplicationUtils.get(ApplicationConstants.VIP_CHECKOUT_COUNTER_SORT_PACKAGE_SWITCH));

    /**
     * The interval time of update the vip mudule config file to the constant
     * map.
     */
    public static final long UPDATE_VIP_CONFIG_INFO_INTERVAL_TIME = CommonConstants.SECONDS_OF_5_MINUTE * 1000L;
    public static long LAST_UPDATE_VIP_CONFIG_INFO_TIME = 0L;

    /**
     * boss v2机卡绑定--绑定时长类型文案前缀，后拼接绑定时长类型（bindDurationUnit）
     */
    public final static String BOSS_V2_DEVICE_BIND_DURATION_UNIT_MSG_KEY_PREFIX = "BOSS_V2_DEVICE_BIND_DURATION_UNIT_";

    /**
     * boss v2会员包--会员时长类型文案前缀，后拼接会员时长类型（durationType）
     */
    public final static String BOSS_V2_VIP_PACKAGE_DURATION_TYPE_NAME_KEY_PREFIX = "BOSS_V2_VIP_PACKAGE_DURATION_TYPE_";

    public static Map<String, String> vipConfigInfoMap = new HashMap<String, String>();

    /**
     * 套餐焦点位置设置Map，key--套餐id，balue--1-设置为焦点，0-不是
     */
    private static Map<String, Integer> focusStatusMap = new HashMap<String, Integer>();

    /**
     * 订单来源Map，key--订单来源，也即终端类型，value--国际化文本
     */
    private final static Map<String, String> orderFromMap = new HashMap<String, String>();

    /**
     * 订单状态Map，key--订单状态码，value--国际化文本
     */
    private final static Map<String, String> orderStatusMap = new HashMap<String, String>();

    /**
     * 订单支付状态Map，key--订单支付状态码，value--国际化文本
     */
    private final static Map<String, String> orderPayStatusMap = new HashMap<String, String>();

    /**
     * 套餐类型-支付渠道默认值映射，key--产品类型的字符串值，value--每种产品类型对应的默认支付渠道组合
     */
    private final static Map<String, String> productTypePaymentChannelDefaultMap = new HashMap<String, String>();

    /**
     * 定向弹窗打开类型Map，key--接收到的打开类型字符串，value--定义的对应值
     */
    private final static Map<String, Integer> openTypeMap = new HashMap<String, Integer>();

    /**
     * 定向乐卡类型Map，key--BOSS返回的卡类型，value--返回给客户端的卡类型
     */
    private final static Map<String, Integer> cardTypeMap = new HashMap<String, Integer>();

    /**
     * 返回给客户端的V2会员id值
     */
    public static final int VIP_EXPERIENCE = 1; // 体验会员
    public static final int VIP_MOBILE = 2; // 乐次元会员
    public static final int VIP_TV = 3; // 超级影视会员

    /**
     * productId和vipId映射map
     */
    public static final Map<Integer, Integer> PRODUCT_ID_AND_VIP_ID_MAP = new HashMap<Integer, Integer>();

    /**
     * VIP账户名称集合
     */
    public static final String VIP_ACCOUNT_TYPE_NAMES[] = { "VIP_ACCOUNT_TYPE_DEMO", "VIP_ACCOUNT_TYPE_MOVIE",
            "VIP_ACCOUNT_TYPE_TV", "VIP_ACCOUNT_TYPE_HOME", "VIP_ACCOUNT_TYPE_CIBN", "VIP_ACCOUNT_TYPE_HUASU",
            "VIP_ACCOUNT_TYPE_MANGO", "VIP_ACCOUNT_TYPE_TENCENT" };

    static {
        orderFromMap.put(VipTpConstant.ORDER_FROM_PC_VIP, "ORDER_FROM_PC_VIP");
        orderFromMap.put(VipTpConstant.ORDER_FROM_PC, "ORDER_FROM_PC");
        orderFromMap.put(VipTpConstant.ORDER_FROM_TV, "ORDER_FROM_TV");
        orderFromMap.put(VipTpConstant.ORDER_FROM_MOBILE, "ORDER_FROM_MOBILE");
        orderFromMap.put(VipTpConstant.ORDER_FROM_NETWORK_Cinemas, "ORDER_FROM_NETWORK_Cinemas");
        orderFromMap.put(VipTpConstant.ORDER_FROM_TV_48, "ORDER_FROM_TV_48");
        orderFromMap.put(VipTpConstant.ORDER_FROM_CNTV, "ORDER_FROM_CNTV");
        orderFromMap.put(VipTpConstant.ORDER_FROM_TV2, "ORDER_FROM_TV2");

        orderStatusMap.put(VipTpConstant.ORDER_STATUS_NOT_PAY, "ORDER_STATUS_NOT_PAY");
        orderStatusMap.put(VipTpConstant.ORDER_STATUS_VIDEO_PASTDUE, "ORDER_STATUS_VIDEO_PASTDUE");
        orderStatusMap.put(VipTpConstant.ORDER_STATUS_VIDEO_SUCCEED, "ORDER_STATUS_VIDEO_SUCCEED");
        orderStatusMap.put(VipTpConstant.ORDER_STATUS_PACKAGE_PASTDUE, "ORDER_STATUS_PACKAGE_PASTDUE");
        orderStatusMap.put(VipTpConstant.ORDER_STATUS_PACKAGE_SUCCEED, "ORDER_STATUS_PACKAGE_SUCCEED");
        orderStatusMap.put(VipTpConstant.ORDER_STATUS_LIVE_SUCCEED, "ORDER_STATUS_LIVE_SUCCEED");

        orderPayStatusMap.put(VipTpConstant.ORDER_PAY_STATUS_FAIL, "ORDER_PAY_STATUS_FAIL");
        orderPayStatusMap.put(VipTpConstant.ORDER_PAY_STATUS_SUCC, "ORDER_PAY_STATUS_SUCC");
        orderPayStatusMap.put(VipTpConstant.ORDER_PAY_STATUS_REFUND, "ORDER_PAY_STATUS_REFUND");
        orderPayStatusMap.put(VipTpConstant.ORDER_PAY_STATUS_AUTOMATIC_CHARGE_FAIL,
                "ORDER_PAY_STATUS_AUTOMATIC_CHARGE_FAIL");

        focusStatusMap.put(String.valueOf(VipTpConstant.ORDER_TYPE_2), 0);
        focusStatusMap.put(String.valueOf(VipTpConstant.ORDER_TYPE_3), 0);
        focusStatusMap.put(String.valueOf(VipTpConstant.ORDER_TYPE_4), 0);
        focusStatusMap.put(String.valueOf(VipTpConstant.ORDER_TYPE_5), 1);
        focusStatusMap.put(String.valueOf(VipTpConstant.ORDER_TYPE_6), 0);

        productTypePaymentChannelDefaultMap.put(String.valueOf(VipTpConstant.PRODUCT_TYPE_SINGLE_FILM),
                PAYMENT_CHANNEL_DEFAULT_1);
        productTypePaymentChannelDefaultMap.put(String.valueOf(VipTpConstant.PRODUCT_TYPE_SERVICE_PACKAGE),
                PAYMENT_CHANNEL_DEFAULT_2);
        productTypePaymentChannelDefaultMap.put(String.valueOf(VipTpConstant.PRODUCT_TYPE_COMSUMR_LIVE),
                PAYMENT_CHANNEL_DEFAULT_3);

        openTypeMap.put(VIP_RECOMMEND_POP_OPENTYPE_NATIVE, VIP_RECOMMEND_POP_OPENTYPE_1);
        openTypeMap.put(VIP_RECOMMEND_POP_OPENTYPE_H5, VIP_RECOMMEND_POP_OPENTYPE_2);

        cardTypeMap.put(CARD_TYPE_BOSS_1, CARD_TYPE_1);
        cardTypeMap.put(CARD_TYPE_BOSS_2, CARD_TYPE_2);
        cardTypeMap.put(CARD_TYPE_BOSS_35, CARD_TYPE_3);
        cardTypeMap.put(CARD_TYPE_BOSS_45, CARD_TYPE_4);

        PRODUCT_ID_AND_VIP_ID_MAP.put(VipTpConstant.PRODUCT_ID_CN.MOBILE.getCode(), 2);
        PRODUCT_ID_AND_VIP_ID_MAP.put(VipTpConstant.PRODUCT_ID_CN.SUPERTV.getCode(), 3);
    }

    public static String getOrderFromByCode(String orderFromCode) {
        if (orderFromMap.get(orderFromCode) != null) {
            return orderFromMap.get(orderFromCode);
        } else {
            return "未知";
        }
    }

    public static String getOrderStatusByCode(String orderStatusCode) {
        return orderStatusMap.get(orderStatusCode);
    }

    public static String getOrderPayStatusByCode(String orderPayStatusCode, String isMonthPayment) {
        String orderPayStatusCodeTmp;
        // 连续包月订单且支付状态是失败
        if (isMonthPayment == "1" && orderPayStatusCode == "0") {
            orderPayStatusCodeTmp = VipTpConstant.ORDER_PAY_STATUS_AUTOMATIC_CHARGE_FAIL;
        } else {
            orderPayStatusCodeTmp = orderPayStatusCode;
        }
        return orderPayStatusCodeTmp;
    }

    public static Integer checkFocusStatus(Integer packageTypeId) {
        return focusStatusMap.get(String.valueOf(packageTypeId));
    }

    public static String getDefaultPaymentChannelByProductType(String productType) {
        return productTypePaymentChannelDefaultMap.get(productType);
    }

    public static Integer getRecommendPopOpenType(String openType) {
        if (StringUtils.isBlank(openType)) {
            return null;
        }
        return openTypeMap.get(openType);
    }

    /**
     * 根据BOSS返回的卡类型，决定返回给客户端的卡类型，服务端做兼容一种终端设备多种卡类型
     * @param cardType
     * @return
     */
    public static Integer getCardType(Integer cardType) {
        if (cardType == null) {
            return null;
        }
        Integer type = cardTypeMap.get(String.valueOf(cardType));
        if (type != null) {
            return type;
        }
        return cardType;// 实在没有收录此类型，则返回原始类型
    }

    /**
     * 会员模块，用户账户会员有效期截至时间缓存key，需拼接userid使用，取值参见VipAccountTpResponse.
     * seniorendtime说明
     */
    public static final String VIP_USER_VIP_END_TIME = "VIP_USER_VIP_END_TIME_";

    /**
     * 通过userid和deviceKey获取用户账户会员有效期截至时间缓存key；
     * 2015-09-09，受机卡问题影响，用户登录机卡设备上时，会员有效期可能会不同；
     * 这样设计，会导致因机卡设备数量，导致数据副本，但可以规避mac副本数量带来的影响
     * @param userid
     * @return
     */
    public static final String getUserVipEndTimeKey(String userid, String deviceKey) {
        return VIP_USER_VIP_END_TIME + userid + "_" + StringUtils.trimToEmpty(deviceKey);
    }

    /**
     * 获取机卡绑定v2的绑定时长msg完整key
     * @param bindDurationUnit
     * @return
     */
    public static String getBossV2DeciceBindDurationUnitMsgKey(Integer bindDurationUnit) {
        return BOSS_V2_DEVICE_BIND_DURATION_UNIT_MSG_KEY_PREFIX + String.valueOf(bindDurationUnit);
    }

    /**
     * 获取boss v2会员套餐时长类型文案
     * @param durationType
     * @return
     */
    public static String getBossV2VipPackageDurationTypeMsgKey(Integer durationType) {
        return BOSS_V2_VIP_PACKAGE_DURATION_TYPE_NAME_KEY_PREFIX + String.valueOf(durationType);
    }

    public static final String TRIAL_FIELD_MONTH_TP_RESPONSE = "month"; // boss返回的单位月
    public static final String TRIAL_FIELD_DATE_TP_RESPONSE = "date"; // boss返回的单位天
    public static final String TRIAL_FIELD_YEAR_TP_RESPONSE = "year"; // boss返回的单位年
    public static final String TRIAL_FIELD_DAY_TEXT = "VIP.VALIDATE.TRIAL.FIELD.DAY";
    public static final String TRIAL_FIELD_DAY_COMPLEX_TEXT = "VIP.VALIDATE.TRIAL.FIELD.DAY.COMPLEX";
    public static final String TRIAL_FIELD_MONTH_TEXT = "VIP.VALIDATE.TRIAL.FIELD.MONTH";
    public static final String TRIAL_FIELD_MONTH_COMPLEX_TEXT = "VIP.VALIDATE.TRIAL.FIELD.MONTH.COMPLEX";
    public static final String TRIAL_FIELD_YEAR_TEXT = "VIP.VALIDATE.TRIAL.FIELD.YEAR";
    public static final String TRIAL_FIELD_YEAR_COMPLEX_TEXT = "VIP.VALIDATE.TRIAL.FIELD.YEAR.COMPLEX";

    /**
     * 返回试用的单位
     * @param field
     * @param duration
     * @param commonParam
     * @return
     */
    public static String getTrialField(String field, Integer duration, CommonParam commonParam) {
        String finalField = null;
        String message = null;
        if (VipConstants.TRIAL_FIELD_DATE_TP_RESPONSE.equalsIgnoreCase(field)) {
            if (duration != null && duration > 1) {
                message = VipConstants.TRIAL_FIELD_DAY_COMPLEX_TEXT;
            } else {
                message = VipConstants.TRIAL_FIELD_DAY_TEXT;
            }
        } else if (VipConstants.TRIAL_FIELD_MONTH_TP_RESPONSE.equalsIgnoreCase(field)) {
            if (duration != null && duration > 1) {
                message = VipConstants.TRIAL_FIELD_MONTH_COMPLEX_TEXT;
            } else {
                message = VipConstants.TRIAL_FIELD_MONTH_TEXT;
            }
        } else if (VipConstants.TRIAL_FIELD_YEAR_TP_RESPONSE.equalsIgnoreCase(field)) {
            if (duration != null && duration > 1) {
                message = VipConstants.TRIAL_FIELD_YEAR_COMPLEX_TEXT;
            } else {
                message = VipConstants.TRIAL_FIELD_YEAR_TEXT;
            }
        }
        finalField = MessageUtils.getMessage(message, commonParam.getLangcode());
        if (StringUtil.isBlank(finalField)) { // 没找到,则返回传递过来的值
            finalField = field;
        }
        return finalField;
    }

}