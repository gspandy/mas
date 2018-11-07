package com.letv.mas.caller.iptv.tvproxy.apicommon.constants;

public class ErrorCodeConstants {

    /**
     * 通用错误码，“抱歉，当前服务已下线！”
     */
    public static final String COMMON_SERVICE_OFFLINE = "CSC001";

    /**
     * 通用错误码，“当前服务繁忙，请您稍后重试！”
     */
    public static final String COMMON_SERVICE_BUSY = "CSC002";

    /**
     * 通用错误码，“非法参数！”
     */
    public static final String COMMON_ILLEGAL_PARAMETER = "CSC003";

    /**
     * 细化非法参数
     */
    public static final String ILLEGAL_PARAMETER_USERID = "CSC003_1";

    /**
     * 通用错误码，“当前版本不支持，请升级超级影视”
     */
    public static final String COMMON_SERVICE_UPGRADE = "CSC004";

    /**
     * 用户相关错误码
     */
    public static final String USER_COLLECTION_NULL = "SUC001"; // 收藏追剧为空
    public static final String USER_PLAYLOG_NULL = "SUC002"; // 播放记录为空
    public static final String USER_PLAYLOG_DEL_FALURE = "SUC003"; // 播放记录删除失败
    public static final String USER_NOT_LOGIN = "SUC004"; // 用户未登录
    public static final String USER_NOT_LOGIN_4_INDIA = "SUC004_IN"; // 用户未登录
    public static final String USER_NOT_EXIST = "SUC005"; // 用户不存在
    public static final String USER_PASSWORD_ERROR = "SUC006"; // 密码错误
    public static final String USER_TOKEN_EXPIRE = "SUC007"; // 用户token过期
    public static final String USER_TOKEN_FAILURE = "SUC008"; // 用户token失效
    public static final String USER_MUTIL_LOGIN = "SUC009"; // 用户异地登录-"您的账号已在另一台电视设备上登录，请重新进行登录"
    public static final String USER_GET_PLAYTAG_ABLUM_LIST_FAILURE = "SUC010"; // 获取收藏追剧失败
    public static final String USER_ADD_PLAYLIST_FALIURE = "SUC011"; // 添加追剧或收藏失败
    public static final String USER_CHECK_PLAYLIST_FAILURE = "SUC012"; // 检查追剧收藏状态失败
    public static final String USER_DELETE_PLAYLIST_FAILURE = "SUC013"; // 删除追剧和收藏失败
    public static final String USER_GET_PLAYLOG_FAILURE = "SUC014"; // 获取播放记录失败
    public static final String USER_DELETE_PLAYLOG_FAILURE = "SUC015"; // 删除播放记录失败
    public static final String USER_UPDATE_PLAYTIME_FAILURE = "SUC016"; // 更新播放记录失败
    public static final String USER_FREE_VIP_REPEAT = "SUC017"; // 用户或者设备已经领取过会员试用，无法再次领取
    public static final String USER_FREE_VIP_ORDER_EXIST = "SUC018"; // 用户开通过套餐或有过消费记录，无法领取会员试用
    public static final String USER_FREE_VIP_GET_FAILURE = "SUC019"; // 领取会员试用失败
    public static final String USER_LOTTERY_EXPIRE = "SUC020"; // 活动尚未开始或者已经结束
    public static final String USER_CREATE_SSO_QRCODE_FAILURE = "SUC021"; // 创建会员中心认证URL失败
    public static final String USER_CHECK_DIRECTIONAL_PUSH_FAILURE = "SUC022"; // （检查当前用户是否符合某定点投放活动条件）定点投放用户查询失败
    public static final String USER_GET_USER_INFO_FAILURE = "SUC025"; // 获取账户基本信息失败
    public static final String USER_GET_ROLE_ERROR = "SUC026"; // 获取用户的角色身份失败
    public static final String USER_DELETE_ROLE_ERROR = "SUC027"; // 获取用户的角色身份失败
    public static final String USER_ADD_ROLE_ERROR = "SUC028"; // 用户添加角色失败
    public static final String USER_UPDATE_GET_ROLE_ERROR = "SUC029"; // 获取用户角色信息失败，无法更新
    public static final String USER_UPDATE_USER_ROLE_ERROR = "SUC030"; // 更新用户角色信息失败
    public static final String USER_ILLEGAL_PARAMETER = "SUC031"; // 请求参数不合法
    public static final String USER_ADD_ROLEPLAYLIST_ERROR = "SUC032"; // 用户添加父母拨单失败
    public static final String USER_DELETE_ROLEPLAYLIST_ERROR = "SUC033"; // 用户删除父母拨单失败
    public static final String USER_GET_ROLEPLAYLIST_ERROR = "SUC034"; // 用户获取父母拨单信息失败
    public static final String USER_GET_ZTFAVORITELIST_ERROR = "SUC035"; // 获取专题收藏列表信息失败
    public static final String USER_GET_USERINFO_SIGN_ERROR = "SUC036"; // 获取用户信息签名错误
    public static final String USER_FROM_ILLEGAL_ERROR = "SUC037"; // 非法来源
    public static final String USER_GET_EROS_TOKEN_FAIL = "SUC038"; // 获取eros-token失败
    public static final String USER_CHECK_ROLEPLAYLIST_ERROR = "SUC039"; // 校验用户播单失败
    public static final String USER_GET_COMMENT_FAILURE = "SUC040"; // 获取评论失败
    public static final String USER_ADD_COLLECTION_FAILURE = "SUC041"; // 添加收藏失败
    public static final String USER_GET_COLLECTION_LIST_FAILURE = "SUC042"; // 获取收藏失败
    public static final String USER_CHECK_COLLECTION_FAILURE = "SUC044"; // 检查收藏状态失败
    public static final String USER_DEL_COLLECTION_FAILURE = "SUC045"; // 删除收藏状态失败
    public static final String USER_GET_MEMBERSHIP_FAILURE = "SUC046"; // 获取用户影视会员权益失败
    public static final String USER_GET_PROEXPIRE_FAILURE = "SUC047"; // 获取用户超级影视会员到期日期失败

    public static final String LECOM_USER_VERIFY_PWD_ERROR = "SUC2001"; // 校验密码失败
    public static final String LECOM_USER_CHILD_LOCK_SET_TOKEN_ERROR = "SUC2002"; // 儿童锁设置token失败
    public static final String LECOM_USER_CHILD_LOCK_VERIFY_PIN_ERROR = "SUC2003"; // 校验PIN码失败
    public static final String LECOM_USER_ILLEGAL_OPERATION = "SUC2004"; // 非法请求
    public static final String LECOM_USER_CHILD_LOCK_EXISTED = "SUC2005"; // 儿童锁已创建，无法重复创建
    public static final String LECOM_USER_CHILD_LOCK_NOT_EXISTED = "SUC2006"; // 您尚未创建儿童锁，请先创建
    public static final String LECOM_USER_CHILD_LOCK_VERIFY_TOKEN_ERROR = "SUC2007"; // 校验lockTokenN码失败
    public static final String LECOM_USER_CHILD_LOCK_SET_LOCK_ERROR = "SUC2008"; // 儿童锁设置状态失败

    /**
     * 支付付费相关错误码
     */
    public static final String PAY_GET_PACKAGE_TYPE_FAILURE = "SPC001"; // 套餐信息获取失败
    public static final String PAY_GET_QRCODE_FAILURE = "SPC002"; // 支付获取二维码失败
    public static final String PAY_COMMIT_ORDER_FAILURE = "SPC003"; // 提交订单失败
    public static final String PAY_FAILURE = "SPC004"; // 支付失败
    public static final String PAY_VIPINFO_SYNC_FAILURE = "SPC005"; // 会员套餐信息同步失败
    public static final String PAY_GET_CONSUMPTION_RECORD_FAILURE = "SPC006"; // 获取消费记录失败
    public static final String PAY_CONSUMPTION_RECORD_NULL = "SPC007"; // 消费记录为空
    public static final String PAY_ORDER_NULL = "SPC008"; // 订单记录为空
    public static final String PAY_ORDER_FAILURE = "SPC009"; // 获取订单失败
    public static final String PAY_GET_RECHARGE_RECORD_FAILURE = "SPC010"; // 获取充值记录失败
    public static final String PAY_RECHARGE_RECORD_NULL = "SPC011"; // 充值记录为空
    public static final String PAY_GET_PRICE_PARCKAGE_LIST_FAILURE = "SPC012"; // 获取产品包列表失败
    public static final String PAY_PRICE_PACKAGE_LIST_NULL = "SPC013"; // 产品包列表为空
    public static final String PAY_ILLEGAL_PRODUCT_TYPE = "SPC014"; // 不合法的产品类型
    public static final String PAY_GET_PURCHASE_ORDER_ID_FAILURE = "SPC015"; // 获取订单号失败
    public static final String PAY_GET_ALI_PAYCODE_FAILURE = "SPC016"; // 获取阿里支付二维码失败
    public static final String PAY_GET_WX_PAYCODE_FALIURE = "SPC017"; // 获取微信支付二维码失败
    public static final String PAY_ILLEGATE_PAYMENT_CHANNEL = "SPC018"; // 非法的支付方式
    public static final String PAY_PHONE_PAY_FAILURE = "SPC019"; // 手机支付失败
    public static final String PAY_LAKALA_PAY_FAILURE = "SPC020"; // 拉卡拉支付失败
    public static final String PAY_CHECK_PHONE_FAILURE = "SPC021"; // 检查手机套餐信息失败
    public static final String PAY_CHECK_ORDER_STATUS_FAILURE = "SPC022"; // 获取订单详情失败
    public static final String PAY_CONSUME_VIP_BY_LETVPOINT_FAILURE = "SPC023"; // 乐点开通VIP失败
    public static final String PAY_GET_PAYMENT_ACTIVITY_FAILURE = "SPC024"; // 获取付费活动信息失败
    public static final String PAY_GET_LETV_POINT_FAILURE = "SPC025"; // 查询乐点失败
    public static final String PAY_GET_DEVICE_BIND_FAILURE = "SPC026"; // 查询机卡绑定套餐信息失败
    public static final String PAY_ORDER_PAYCODE_ILLEGAL_PARAMETER = "SPC027"; // 单点支付请求参数不合法
    public static final String PAY_ORDER_PAYCODE_ALI_FAILURE = "SPC028"; // 单点支付--获取支付宝二维码图片失败
    public static final String PAY_ORDER_PAYCODE_WEIXIN_FAILURE = "SPC029"; // 单点支付--获取微信二维码图片失败
    public static final String PAY_ORDER_PAYCODE_LAKALA_FAILURE = "SPC030"; // 单点支付--获取拉卡拉二维码图片失败
    public static final String PAY_ILLEGAL_PARAMETER = "SPC031"; // 请求参数不合法
    public static final String PAY_LETVPOINT_NOT_SUPPORT_919 = "SPC032"; // 919活动当天不接收乐点支付，敬请谅解
    public static final String PAY_GET_PRICE_PARCKAGE_FAILURE = "SPC033"; // 获取（单个）产品包失败
    public static final String PAY_LETVPOINT_AVAILABLE_INSUFFICIENT = "SPC034"; // 可用乐点不足
    public static final String PAY_PURCHASE_VIP_ALI_PAY_FAILURE = "SPC035"; // 支付宝支付失败（升级后的收银台）
    public static final String PAY_PURCHASE_VIP_WEIXIN_PAY_FAILURE = "SPC036"; // 微信支付失败（升级后的收银台）
    public static final String PAY_PURCHASE_VIP_LAKALA_PAY_FAILURE = "SPC037"; // 拉卡拉支付失败（升级后的收银台）
    public static final String PAY_PURCHASE_VIP_LETVPOINT_PAY_FAILURE = "SPC038"; // 乐点支付失败（升级后的收银台）
    public static final String PAY_CHECK_VOUCHER_STATUS_FAILURE = "SPC039"; // 校验代金券状态失败
    public static final String PAY_VOUCHER_NOT_AVAILABLE = "SPC040"; // 代金券不可用
    public static final String PAY_VOUCHER_NOT_APPLICATIVE = "SPC041"; // 代金券不适用
    public static final String PAY_GET_USER_AGGREMENT_FAILURE = "SPC042"; // 获取收银台用户协议文案失败
    public static final String PAY_CHECK_LIVE_FAILURE = "SPC043"; // 直播鉴权失败
    public static final String PAY_CHECK_ONE_KEY_PAY_FAILURE = "SPC044"; // 一键支付绑定查询失败
    public static final String PAY_PURCHASE_VIP_PAYPAL_PAY_FAILURE = "SPC045"; // paypal二维码生成失败
    public static final String PAY_PURCHASE_VIP_ONE_KEY_QUICK_PAY_FAILURE = "SPC046"; // 一键支付请求失败
    public static final String PAY_RECEIVE_PRESENT_DEVICE_BIND_FAILURE = "SPC048"; // 领取超级手机赠送会员时长失败
    public static final String PAY_GET_PRESENT_DEVICE_BIND_FAILURE = "SPC049"; // 查询超级手机赠送会员时长失败
    public static final String PAY_GET_VIP_ACCOUNT_INFO_FAILURE = "SPC052"; // 获取会员账户信息失败
    public static final String PAY_GET_USER_DEVICE_BIND_FAILURE = "SPC056"; // 查询用户机卡绑定信息失败
    public static final String PILOT_GET_FAILURE = "SPC057"; // 获取定向引导信息失败
    public static final String PAY_YEEPAY_GET_BINDINFO_FAILURE = "SPC058"; // 查询绑卡信息失败
    public static final String PAY_YEEPAY_GET_ONEBIND_FAILURE = "SPC059"; // 查询用户是否可以一元绑卡失败
    public static final String PAY_YEEPAY_CHECK_BIND_TYPE_ILEGAL = "SPC060"; // 查询卡绑定信息的卡类型非法
    public static final String PAY_YEEPAY_ORDER_FAILURE = "SPC061"; // 易宝支付下单失败
    public static final String PAY_YEEPAY_GET_VERIFY_CODE_ERROR = "SPC062";// 易宝支付获取验证码失败
    public static final String PAY_YEEPAY_CONFIRMPAY_ERROR = "SPC063";// 易宝支付确认支付失败
    public static final String PAY_YEEPAY_UNBIND_ERROR = "SPC064";// 易宝支付信用卡解绑失败
    public static final String PAY_GET_RECOMMENDPOP_ERROR = "SPC065";// 获取用户定向弹窗信息失败
    public static final String PAY_GET_PAYMENTCHANNEL_ERROR = "SPC066";// 获取支付渠道失败
    public static final String PAY_CHECK_LIVE_ERROR = "SPC067";// 服务器不给力，请重试
    public static final String PAY_LETV_CARD_VALIDATE_ERROR = "SPC068";// 乐卡校验失败
    public static final String PAY_HUASHU_ORDER_FAILURE = "SPC069";// 华数下单失败
    public static final String PAY_LETV_CARD_TYPE_ERROR = "SPC070";// 充值失败，不能使用兑换卡进行充值
    public static final String TOUCH_USERCENTER_ILLEGAL = "SPC071"; // 触达错误
    public static final String VIP_TOUCH_ACTIVITY_OFFLINE = "SPC072"; // 活动已下线，敬请期待
    public static final String VIP_MEMBER_DESK_INFO_USERID = "SPC073"; // 会员桌面-服务位/功能位-userId是非数字字符串
    public static final String VIP_MEMBER_DESK_INFO_NON_USER = "SPC074"; // 会员桌面-服务位/功能位-userId查询不到用户信息
    public static final String VIP_MEMBER_DESK_ACTIVITY_NULL_MAC = "SPC075"; // 会员桌面-urm触达位-mac地址为空
    public static final String VIP_ACTIVE_FREEVIP_ERROR = "SPC076"; // 激活失败，请重试
    public static final String VIP_GET_FREEVIP_INFO_ERROR = "SPC077"; // 获取数据失败，请重试
    public static final String VIP_GET_CONTENT_PACKAGE_FAIL = "SPC078"; // 获取会员内容包失败
    public static final String VIP_GET_PACKAGE_INFO_FAIL = "SPC079"; // 获取会员套餐信息失败
    public static final String VIP_GET_PRODUCT_INFO_FAIL = "SPC080"; // 获取会员信息失败
    public static final String VIP_PAY_PRODUCTID_NULL = "SPC081"; // productId为空
    public static final String PAY_GET_PURCHASE_INFO_FAILURE = "SPC082"; // 获取一键支付支付信息失败
    public static final String UN_SUB_SCRIBE_PAYMENT_MONTHLY_FAILE = "SPC083";// 取消连续包月失败

    /**
     * 频道相关错误码
     */
    public static final String CHANNEL_PARAMETER_ERROR = "SCC001";// 请求参数错误
    public static final String CHANNEL_GET_TJPACKAGE_ERROR = "SCC002";// 获取特辑包数据错误
    public static final String CHANNEL_GET_RANK_ERROR = "SCC003";// 排行榜id格式错误
    public static final String CHANNEL_GET_SUBJECT_PACKAGE_ERROR = "SCC004";// 获取专题下视频包数据错误
    public static final String CHANNEL_GET_BLOCK_CONTENT_ERROR = "SCC005";// 获取CMS板块数据失败
    public static final String CHANNEL_GET_SUBJECT_DATA_OFFLINE = "SCC006";// 专题已下线

    /**
     * 搜索相关错误码(SSC001、SSC002这两个值目前使用SearchConstant中的变量,在这里只是表示已经被使用了)
     */
    public final static String SEARCH_ERROR_CONDITION_ERROR = "SSC001"; // 搜索条件异常
    public final static String SEARCH_ERROR_RESULT_NULL = "SSC002"; // 搜索结果返回空
    public final static String SEARCH_PARAMETER_ERROR = "SSC003";// 请求参数错误

    /**
     * 开机启动图出错
     */
    public static final String STARTUP_PIC_GET_ERROR = "SUP001";

    /**
     * 儿童桌面相关错误码
     */
    public static final String DESK_ILLEGAL_PARAMETER = "SDC001"; // 桌面参数错误

    /**
     * （直播、轮播、卫视）无节目数据可用，“当前节目暂无法播放，请您关注其他精彩内容！错误码：SLC001”
     */
    public static final String LIVE_CHANNEL_PROGRAM_NULL = "SLC001";

    /**
     * （直播、轮播、卫视）签名校验异常
     */
    public static final String LIVE_CHANNEL_SIG_ILLEGAL = "SLC002";

    /**
     * 第三方直播接口返回错误，将直接使用第三方错误信息，不再设置错误码
     */
    public static final String LIVE_CHANNEL_SEND_DANMU_CONTENT_ERROR = "SLC003";

    /**
     * 调用第三放弹幕发送接口失败，“暂无法发送弹幕，请稍后重试！错误码：SLC004”
     */
    public static final String LIVE_CHANNEL_SEND_DANMU_FAILED = "SLC004";

    /**
     * （直播、轮播、卫视）无节目数据可用，“因版权原因，当前节目暂无法播放，请您关注其他精彩内容！错误码：SLC005”
     */
    public static final String LIVE_CHANNEL_PROGRAM_NO_COPYRIGHT = "SLC005";

    /**
     * 
     */
    public static final String TERMINAL_GET_SERVICE_TERM_FAIL = "STC001";

    public static final String GET_ACTIVITY_FAILURE = "STC005"; // 获取活动信息失败

    public static final String GET_ACTIVITY_PARAM_FAILURE = "STC006"; // 请求参数错误

    public static final String GET_AGREEMENT_FAILURE = "STC007"; // 获取套餐协议错误
}