package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonMsgCodeConstant;

public class VipMsgCodeConstant extends CommonMsgCodeConstant {

    /**
     * 用户模块--充值记录参数校验返回码
     */
    public final static int VIP_GET_RECHARGE_RECORD_REQUEST_DAY_ILLEGAL = 1000;// 查询的日期范围不合法

    /**
     * 会员模块--消费记录参数校验返回码
     */
    public final static int VIP_GET_CONSUMPTION_RECORD_REQUEST_ORDER_TYPE_ERROR = 1000;// 订单类型错误
    public final static int VIP_GET_CONSUMPTION_RECORD_REQUEST_STATUS_ERROR = 1001;// 订单状态错误
    public final static int VIP_GET_CONSUMPTION_RECORD_REQUEST_DAY_ERROR = 1002;// 查询的日期范围不合法

    /**
     * 会员模块--支付接口参数校验返回码
     */
    public final static int VIP_PURCHASE_ORDER_REQUEST_PRODUCT_TYPE_ERROR = 1000;// 产品类型错误
    public final static int VIP_PURCHASE_ORDER_REQUEST_PRODUCTID_ERROR = 1001;// 产品id错误
    public final static int VIP_PURCHASE_ORDER_REQUEST_PAYMENTCHANNEL_ILLEGAL = 1002;// 支付渠道非法
    public final static int VIP_PURCHASE_ORDER_REQUEST_AMOUNT_ERROR = 1003;// 产品价格错误
    public final static int VIP_ORDER_PAYCODE_REQUEST_VIDEOID_ERROR = 1004;// 购买直播的直播id错误
    public final static int VIP_PURCHASE_VIP_REQUEST_SINGLE_PAYMENTCHANNEL_ERROR = 1005;// 单点影片不允许使用乐点支付
    public final static int VIP_PURCHASE_VIP_REQUEST_SINGLE_PRICE_ERROR = 1006;// 购买单点影片时价格不能为空
    public final static int VIP_TP_PURCHASE_VIP_REQUEST_AV_ERROR = 1007;// 直播SDK中，av必须为2
    public final static int VIP_TP_PURCHASE_VIP_REQUEST_MAC_EMPTY = 1008;// 直播SDK，MAC必传
    public final static int VIP_TP_PURCHASE_VIP_REQUEST_ONLY_SINGLE = 1009;// 直播SDK暂只支持单点
    public final static int VIP_TP_PURCHASE_VIP_REQUEST_SUBEND_ERROR = 1010;// 直播SDK--设备信息不合法
    public final static int VIP_TP_PURCHASE_VIP_REQUEST_PAYMENTCHANNEL_ERROR = 1011;// 直播SDK暂只支持支付宝和微信支付
    public final static int VIP_VIP_PURCHASE_ORDER_REQUEST_PID_VIDEOID_ERROR = 1012;// 购买直播时pid和videoid的值必须一致
    public final static int VIP_VIP_PURCHASE_ORDER_REQUEST_CORDERID_ERROR = 1013;// 订单id错误
    public final static int VIP_PURCHASE_VIP_REQUEST_QUICK_PAY_BINDID_EMPTY = 1014;// 快捷支付时，绑定ID不能为空

    /**
     * 会员模块--查询会员信息
     */
    public final static int VIP_GET_ACCOUNT_REQUEST_DEVICETYPE_ERROR = 1000;// 设备类型错误
    public final static int VIP_GET_CHECK_COUNTER_REQUEST_PURCHASE_TYPE_ERROR = 1001;// 购买类型错误
    public final static int VIP_GET_CHECK_COUNTER_REQUEST_APK_VERSION_ERROR = 1002;// 终端信息错误
    public final static int VIP_GET_CHECK_COUNTER_REQUEST_TPSDK_PURCHASE_ERROR = 1003;// 第三方设备购买类型错误

    /**
     * 会员模块使用观影券请求校验返回码
     */
    public final static int VIP_MOVIE_TICKET_ACTIVE_REQUEST_MOVIE_INCOMPLETE = 1000;// 电影信息不完整
    public final static int VIP_MOVIE_TICKET_ACTIVE_REQUEST_FROM_ILLEGAL = 1001;// 观影券使用端不合法
    public final static int VIP_MOVIE_TICKET_LIST_REQUEST_PID_ILLEGAL = 1002;// 产品id不合法
    public final static int VIP_MOVIE_TICKET_LIST_REQUEST_TYPE_ERROR = 1003;// 查询观影券终端类型错误
    public final static int VIP_MOVIE_TICKET_LIST_REQUEST_USERNAME_EMPTY = 1004;// 查询数量时username不能为空

    /**
     * 易宝支付验证码接口
     */
    public final static int VIP_YEEPAY_VERIFY_CODE_REQUEST_CORDERID_EMPTY = 1000;// 易宝支付验证码接口订单号不能为空
    public final static int VIP_YEEPAY_VERIFY_CODE_REQUEST_OPERTYPE_ERROR = 1001;// 易宝支付验证码接口操作类型错误
    public final static int VIP_YEEPAY_VERIFY_CODE_REQUEST_SMSCODE_ERROR = 1002;// 验证码错误
    public final static int VIP_YEEPAY_UNBIND_REQUEST_BINDID_EMPTY = 1003;// 绑卡ID不能为空

    /**
     * 直播鉴权接口参数校验返回码
     */
    public final static int VIP_CHECK_LIVE_REQUEST_PID_EMPTY = 1000;// 直播id为空
    public final static int VIP_CHECK_LIVE_REQUEST_LIVEID_EMPTY = 1001;// 场次id为空
    public final static int VIP_CHECK_LIVE_REQUEST_SPLATID_EMPTY = 1002;// 子平台id为空

    /**
     * 乐卡充值与校验接口参数校验返回码
     */
    public final static int VIP_LETV_CARD_REQUEST_CARDSECRET_EMPTY = 1000;// 卡号不能为空
    public final static int VIP_LETV_CARD_REQUEST_EXCHANGE_INFO_EMPTY = 1001;// 兑换信息不足，无法兑换

    /**
     * 会员桌面 跳转类型
     */
    public final static int VIP_TABLE_TYPE_BURROW = 1;// 客户端打洞
    public final static int VIP_TABLE_TYPE_SKIP = 2;// 客户端跳转

    /**
     * 会员桌面-会员状态-图片类型
     */
    public final static int VIP_TABLE_IMG_TYPE_MONEY = 1; // 消费
    public final static int VIP_TABLE_TYPE_PRIVILEGE = 2; // 特权
    public final static int VIP_TABLE_TYPE_ACTIVITY = 3; // 活动
    public final static int VIP_TABLE_TYPE_LIVE = 4; // 直播
    public final static int VIP_TABLE_TYPE_MESSAGE = 5; // 消息
    public final static int VIP_TABLE_TYPE_CHANNEL = 6; // 频道

    /**
     * 会员桌面 会员状态
     */
    public final static int VIP_TABLE_MEMBER_STATUS_NON = 0;// 非会员
    public final static int VIP_TABLE_MEMBER_STATUS_YES = 1;// 会员
    public final static int VIP_TABLE_MEMBER_STATUS_EXPIRE = 2;// 过期会员

    /**
     * 会员桌面 会员类型
     */
    public final static int VIP_TABLE_MEMBER_TYPE_NON = -1; // 既不是影视会员又不是体育会员
    public final static int VIP_TABLE_MEMBER_TYPE_MOVIE = 0; // 影视
    public final static int VIP_TABLE_MEMBER_TYPE_SPORT = 1; // 体育
    public final static int VIP_TABLE_MEMBER_TYPE_BOTH = 2; // 既是影视会员又是体育会员
    public final static int VIP_TABLE_MEMBER_TYPE_BOTH_EXPIRE = 3; // 都过期会员
    public final static int VIP_TABLE_MEMBER_TYPE_MOVIE_EXPIRE = 4; // 影视过期会员
    public final static int VIP_TABLE_MEMBER_TYPE_SPORT_EXPIRE = 5; // 体育过期会员
    public final static int VIP_TABLE_MEMBER_TYPE_NOT_LOGIN = 6; // 未登录

    /**
     * 会员桌面 urm触达位 会员类型标记
     */
    public final static int VIP_TABLE_MEMBER_URM_TYPE_MOVIE = 1; // 影视
    public final static int VIP_TABLE_MEMBER_URM_TYPE_SPORT = 2; // 体育

    /**
     * 会员桌面 打洞action
     */
    public final static String VIP_TABLE_MOVIE_CHECKSTAND_ACTION = "com.letv.external.new";// 影视-收银台
    public final static String VIP_TABLE_MOVIE_MESSAGE_ACTION = "android.intent.action.MESSAGE_CENTER";// 影视-我的消息
    public final static String VIP_TABLE_MOVIE_MACHINE_CARD_ACTION = "com.letv.account.ACCOUNTCENTER";// 影视-机卡绑定

    public final static String ACTION_MOVIE_MEMBER = "com.letv.external.new"; // 影视
    public final static String ACTION_SPORT = "com.lesports.tv.ACTION.BURROW"; // 体育
    public final static String ACTION_SHOPPING = "com.stv.shopping.action.external"; // 购物
    public final static String ACTION_LESTORE_SUBJECT = "com.letv.tvos.appstore.external.new";// lestore
                                                                                              // 专题
    public final static String ACTION_GAMECENTER = "com.letv.tvos.gamecenter.external.new";// 游戏中心

    /**
     * Extension中launchMode
     */
    public final static int LAUNCH_MODE_BROADCAST = 0; // 广播方式
    public final static int LAUNCH_MODE_HIDE_ACTIVITY = 1; // 隐式启动Activity方式
    public final static int LAUNCH_MODE_OBVIOUS_ACTIVITY = 2; // 显式启动Activity方式
}