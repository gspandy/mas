package com.letv.mas.caller.iptv.tvproxy.user.constant;

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
}