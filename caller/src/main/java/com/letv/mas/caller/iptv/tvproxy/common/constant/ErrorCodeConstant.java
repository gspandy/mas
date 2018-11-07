package com.letv.mas.caller.iptv.tvproxy.common.constant;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvCommonException;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import org.apache.commons.lang.StringUtils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ErrorCodeConstant {
    public static String DEFAULT_ERROR_CODE = "0000";

    // 公用errorCode（00开头）
    public static String USER_NOT_LOGIN = "0001";// 请先登陆
    public static String ALBUM_NOT_FOUND = "0002";// 暂时无法观看此专辑，请先欣赏其他精彩内容
    public static String VIDEO_NOT_FOUND = "0003";// 暂时无法观看此视频，请先欣赏其他精彩内容
    public static String USER_BEEN_LOGOUT = "0004";// 9905 0004;//
                                                   // 此账号已在其他地方登录，请重试

    // 用户errorCode（10开头）
    public static String USER_LOGIN_1002 = "1001";// 登录名或密码不正确
    public static String USER_LOGIN_1003 = "1002";// 你的账号不可用
    public static String USER_LOGIN_1004 = "1003";// 请你在电脑上激活邮箱
    public static String USER_LOGIN_BAD = "1004";// 服务不给力，请重试
    public static String USER_NOT_EXIST = "1005";// 账号不存在
    public static String USER_OLDPASSWD_ERROR = "1006";// 旧密码不正确
    public static String USER_PASSWD_ERROR = "1007";// 密码少于6位
    public static String USER_UPPASSWD_ERROR = "1008";// 服务不给力，请重试
    public static String USER_REGISTER_PARAM_ERROR = "1009";// 密码不能为空或手机，邮箱不能同时为空,数据不能为空
    public static String USER_MAIL_FORMAT_ERROR = "1010";// 邮箱格式不正确
    public static String USER_MAIL_EXIST = "1011";// 邮箱已注册，换一个试试
    public static String USER_MOBILE_FORMAT_ERROR = "1012";// 手机号码有误
    public static String USER_MOBILE_EXIST = "1013";// 手机号已被注册
    public static String USER_NICKNAME_EXIST = "1014";// 昵称已被注册，换一个试试
    public static String USER_REGISTER_ERROR = "1015";// 服务不给力，请重试
    public static String USER_INFO_ERROR = "1016";// 服务不给力，请重试
    public static String USER_CREATE_QR_CODE = "1017";// 请求二维码信息失败，请重试
    public static String USER_CHECK_QR_CODE_1001 = "1018";// 二维码字符串为空
    public static String USER_CHECK_QR_CODE_1002 = "1019";// 二维码密钥认证失败
    public static String USER_CHECK_QR_CODE_1003 = "1020";// 二维码登录检测失败
    public static String USER_CHECK_TOKEN_LOGIN = "1021";// Token不正确或已过期
    public static String USER_SEND_AUTH_CODE_ERROR = "1022";// 发送短信验证码错误
    public static String USER_REGISTER_CODE_MOBILE_ERROR = "1023";// 激活码已经过期或者手机号错误
    public static String USER_FORMAT_ERROR = "1024";// 用户名格式不正确
    public static String USER_USERNAME_EXIST = "1025";// 用户名已存在
    public static final String USER_MAC_BLACKLIST = "1026";// 用户MAC黑名单
    public static final String USER_MAC_BLACKLIST_CACHE_FLUSH_FAIL = "1027";// 用户MAC黑名单缓存刷新失败

    // 播放errorCode（11开头）
    public static String LIVE_PLAY_ERROR_CODE = "1101";// 服务不给力，请重试
    public static final String CONSUMPTION_ERROR_CODE_0000 = "1102";// 网络不给力，请稍后重试
    public static final String CONSUMPTION_ERROR_CODE_0001 = "1103";// 此内容为收费服务，请先购买套餐
    public static final String CONSUMPTION_ERROR_CODE_0002 = "1104";// 套餐已经过期，请重新购买
    public static final String CONSUMPTION_ERROR_CODE_0003 = "1105";// 此内容为收费服务，请先购买套餐
    public static final String CONSUMPTION_ERROR_CODE_0004 = "1106";// 套餐已经过期，请重新购买
    public static final String CONSUMPTION_ERROR_CODE_0005 = "1107";// 未购买单片或包月服务
    public static final String CONSUMPTION_ERROR_CODE_0006 = "1108";// 视频不存在，请联系客服进行解决
    public static String STREAM_NOT_FOUND = "1109";// 没有可播放的码流
    public static String GET_PLAYURL_ERROR = "1110";// 获取播放地址失败
    public static String SIG_ERROR = "1111";// 签名错误
    public static String PLAY_NO_STREAM = "1112";// 没有可以播放的码流
    public static final String CN_PLAY_FORBIDDEN_CODE = "1113";// 国内禁播
    public static final String NOT_CN_PLAY_FORBIDDEN_CODE = "1114";// 国外禁播
    // 充值errorCode（12开头）
    public static String LETV_LEKA_RECHARGE_ERROR = "1201";// 乐卡充值失败
    // 购买errorCode(13开头)
    public static String PAY_INVALIDE_PRODUCTTYPE = "1301"; // 不合法的产品类型
    public static String PAY_GETORDERID_ERROR = "1302";// 获取订单号异常
    public static String PAY_INVALIDE_PACKAGETYPE = "1303";// 未查到该产品对应的价格
    public static String PAY_ALIPAY_VERIFY_CODE_ERROR = "1304";// 支付宝验证码错误
    public static String PAY_ALIPAY_VERIFY_FIAL_OF_CARD_NO = "1305";// 银行卡校验未通过
    public static String PAY_ALIPAY_VERIFY_FIAL_OF_REAL_NAME = "1306";// 姓名校验未通过
    public static String PAY_ALIPAY_VERIFY_FIAL_OF_VALID_DATE = "1307";// 有效期校验未通过
    public static String PAY_ALIPAY_VERIFY_FIAL_OF_CERT_NO = "1308";// 证件号校验未通过
    public static String PAY_ALIPAY_VERIFY_FIAL_OF_MOBILE = "1309";// 手机号校验未通过
    public static String PAY_ALIPAY_CARD_SIGN_FAIL = "1310";// 银行卡签约失败
    public static String PAY_ALIPAY_USER_REPLY_TIMEOUT = "1311";// 用户回复超时
    public static String PAY_ALIPAY_MOBILE_BIND_EXCEED_MAX_CARD_SIZE = "1312";// 手机申请绑定账号个数超过系统设置
    public static String PAY_ALIPAY_USER_SIGNED = "1313";// 用户已签约
    public static String PAY_ALIPAY_EXTERNAL_USER_MISMATCH = "1314";// 商户用户账号或支付宝账号与已绑定的不一致
    public static String PAY_ALIPAY_USER_MOBILE_NOT_ALLOW_SIGN = "1315";// 商户限制用户绑定手机但用户未绑定手机
    public static String PAY_ALIPAY_USER_CERTIFY_NOT_ALLOW_SIGN = "1316";// 用户未通过实名认证不允许签约
    public static String PAY_ALIPAY_USER_HAS_BLOCK = "1317";// 用户账户被冻结
    public static String PAY_ALIPAY_NOT_HAVE_ENOUGH_BALANCE = "1318";// 余额不足
    public static String PAY_ALIPAY_ACK_NO_IS_NOT_ALLOWED_NULL = "1319";// 用户确认码参数不能为空
    public static String PAY_LEDIAN_0001 = "1320";// 参数缺少或者错误
    public static String PAY_LEDIAN_0002 = "1321";// MD5校验失败
    public static String PAY_LEDIAN_1000 = "1322";// 系统侧原因交易失败
    public static String PAY_LEDIAN_1001 = "1323";// 交易超时
    public static String PAY_LEDIAN_1002 = "1324";// 传递了不符合要求的参数
    public static String PAY_LEDIAN_1003 = "1325";// 帐户乐点不足
    public static String PAY_LEDIAN_1004 = "1326";// 乐卡不存在或已失效
    public static String PAY_RECODE_NULL = "1327";// 订单不存在
    public static String PAY_ALIPAY_VALIDATECODE_EXPIRED = "1328";// 确认支付验证码过期
    public static String PAY_ALIPAY_VALIDATECODE_EXCEED_LIMITED = "1329";// 验证码输入超过次数限制
    public static String PAY_ALIPAY_VALIDATECODE_ANSWER_ERROR = "1330";// 验证码输入错误
    public static String PAY_ALIPAY_VALIDATECODE_IS_NOT_EXIST = "1331";// 无效验证码，请重新获取
    public static String USER_CONSUME_CREATE_QR_CODE = "1332";// 二维码信息获取失败，请重试
    public static String PAY_RECODE_EXIST = "1333";// 订单已存在
    public static String PAY_RECODE_USER = "1334";// 用户已购买
    public static String PAY_RECODE_MAC = "1335";// Mac已购买
    public static String USER_CONSUME_PHONE = "1336";// 短信发送失败，请重试
    public static String USER_ORDER_PAID = "1337";// 订单已支付，请勿重复支付
    private final static Map<String, String> errorCodeMap = new HashMap<String, String>();
    public final static Map<String, String> LETV_THIRDPARTY_PAY_MAP = new HashMap<String, String>();
    public final static Map<String, String> LETV_THIRDPARTY_CONSUME_MAP = new HashMap<String, String>();
    static {
        errorCodeMap.put(DEFAULT_ERROR_CODE, "未知异常");
        errorCodeMap.put(USER_BEEN_LOGOUT, "您的账号已在另一台电视设备上登录，请重新进行登录");
        errorCodeMap.put(USER_NOT_LOGIN, "请先登录");
        errorCodeMap.put(USER_LOGIN_1002, "账号或密码不正确");
        errorCodeMap.put(USER_LOGIN_1003, "你的账号不可用");
        errorCodeMap.put(USER_LOGIN_1004, "请你在电脑上激活邮箱");
        errorCodeMap.put(USER_LOGIN_BAD, "服务不给力，请重试");
        errorCodeMap.put(USER_NOT_EXIST, "账号不存在");
        errorCodeMap.put(USER_OLDPASSWD_ERROR, "旧密码不正确");
        errorCodeMap.put(USER_PASSWD_ERROR, "密码少于6位");
        errorCodeMap.put(USER_UPPASSWD_ERROR, "服务不给力，请重试");
        errorCodeMap.put(USER_REGISTER_PARAM_ERROR, "数据不能为空");
        errorCodeMap.put(USER_MAIL_FORMAT_ERROR, "邮箱格式不正确");
        errorCodeMap.put(USER_MAIL_EXIST, "邮箱已注册，换一个试试");
        errorCodeMap.put(USER_MOBILE_FORMAT_ERROR, "手机号码有误");
        errorCodeMap.put(USER_MOBILE_EXIST, "手机号已被注册");
        errorCodeMap.put(USER_NICKNAME_EXIST, "昵称已被注册，换一个试试");
        errorCodeMap.put(USER_USERNAME_EXIST, "用户名已存在");
        errorCodeMap.put(USER_FORMAT_ERROR, "用户名格式不正确，必须是以字母开头，数字、字母或“_”组合的6-50位字符");
        errorCodeMap.put(USER_REGISTER_ERROR, "服务不给力，请重试");
        errorCodeMap.put(USER_INFO_ERROR, "服务不给力，请重试");
        errorCodeMap.put(LIVE_PLAY_ERROR_CODE, "服务不给力，请重试");
        errorCodeMap.put(LETV_LEKA_RECHARGE_ERROR, "充值码有误或已失效");
        errorCodeMap.put(PAY_INVALIDE_PRODUCTTYPE, "请重新选择套餐");
        errorCodeMap.put(PAY_GETORDERID_ERROR, "服务不给力，请重试");
        errorCodeMap.put(PAY_INVALIDE_PACKAGETYPE, "服务不给力，请重试");
        errorCodeMap.put(PAY_ALIPAY_VERIFY_CODE_ERROR, "支付宝验证码错误");
        errorCodeMap.put(PAY_ALIPAY_VERIFY_FIAL_OF_CARD_NO, "银行卡校验未通过，请重试");
        errorCodeMap.put(PAY_ALIPAY_VERIFY_FIAL_OF_REAL_NAME, "姓名校验未通过");
        errorCodeMap.put(PAY_ALIPAY_VERIFY_FIAL_OF_VALID_DATE, "有效期校验未通过");
        errorCodeMap.put(PAY_ALIPAY_VERIFY_FIAL_OF_CERT_NO, "证件号校验未通过");
        errorCodeMap.put(PAY_ALIPAY_VERIFY_FIAL_OF_MOBILE, "手机号校验未通过");
        errorCodeMap.put(PAY_ALIPAY_CARD_SIGN_FAIL, "银行卡签约失败");
        errorCodeMap.put(PAY_ALIPAY_USER_REPLY_TIMEOUT, "服务不给力，请重试");
        errorCodeMap.put(PAY_ALIPAY_MOBILE_BIND_EXCEED_MAX_CARD_SIZE, "手机账号已被绑定过");
        errorCodeMap.put(PAY_ALIPAY_USER_SIGNED, "签约成功");
        errorCodeMap.put(PAY_ALIPAY_EXTERNAL_USER_MISMATCH, "服务不给力，请重试");
        errorCodeMap.put(PAY_ALIPAY_USER_MOBILE_NOT_ALLOW_SIGN, "服务不给力，请重试");
        errorCodeMap.put(PAY_ALIPAY_USER_CERTIFY_NOT_ALLOW_SIGN, "未通过实名认证");
        errorCodeMap.put(PAY_ALIPAY_USER_HAS_BLOCK, "账户被冻结");
        errorCodeMap.put(PAY_ALIPAY_NOT_HAVE_ENOUGH_BALANCE, "余额不足，请充值");
        errorCodeMap.put(PAY_ALIPAY_ACK_NO_IS_NOT_ALLOWED_NULL, "服务不给力，请重试");
        errorCodeMap.put(PAY_LEDIAN_0001, "服务不给力，请重试");
        errorCodeMap.put(PAY_LEDIAN_0002, "服务不给力，请重试");
        errorCodeMap.put(PAY_LEDIAN_1000, "服务不给力，请重试");
        errorCodeMap.put(PAY_LEDIAN_1001, "交易超时，请重试");
        errorCodeMap.put(PAY_LEDIAN_1002, "服务不给力，请重试");
        errorCodeMap.put(PAY_LEDIAN_1003, "乐点余额不足，请充值");
        errorCodeMap.put(PAY_LEDIAN_1004, "充值码有误或已失效");
        errorCodeMap.put(PAY_RECODE_NULL, "订单不存在");
        errorCodeMap.put(PAY_RECODE_EXIST, "订单已存在");
        errorCodeMap.put(PAY_RECODE_USER, "此用户已购买过");
        errorCodeMap.put(PAY_RECODE_MAC, "此设备上已购买过");
        errorCodeMap.put(PAY_ALIPAY_VALIDATECODE_EXPIRED, "支付验证码已过期，请重试");
        errorCodeMap.put(PAY_ALIPAY_VALIDATECODE_EXCEED_LIMITED, "验证码输入超过次数限制");
        errorCodeMap.put(PAY_ALIPAY_VALIDATECODE_ANSWER_ERROR, "验证码输入错误");
        errorCodeMap.put(PAY_ALIPAY_VALIDATECODE_IS_NOT_EXIST, "验证码有误，请重试");
        errorCodeMap.put(USER_CONSUME_CREATE_QR_CODE, "二维码信息获取失败，请重试");
        errorCodeMap.put(ALBUM_NOT_FOUND, "暂时无法观看此专辑，请先欣赏其他精彩内容");
        errorCodeMap.put(VIDEO_NOT_FOUND, "该视频在TV无版权或已下线");
        errorCodeMap.put(CONSUMPTION_ERROR_CODE_0000, "网络不给力，请稍后重试");
        errorCodeMap.put(CONSUMPTION_ERROR_CODE_0001, "此内容为收费服务，请先购买套餐");
        errorCodeMap.put(CONSUMPTION_ERROR_CODE_0002, "套餐已经过期，请重新购买");
        errorCodeMap.put(CONSUMPTION_ERROR_CODE_0003, "此内容为收费服务，请先购买套餐");
        errorCodeMap.put(CONSUMPTION_ERROR_CODE_0004, "套餐已经过期，请重新购买");
        errorCodeMap.put(CONSUMPTION_ERROR_CODE_0005, "此内容为收费服务，请先购买套餐");
        errorCodeMap.put(CONSUMPTION_ERROR_CODE_0006, "此视频已下线");
        errorCodeMap.put(STREAM_NOT_FOUND, "暂时无法观看此视频，请稍后重试");
        errorCodeMap.put(GET_PLAYURL_ERROR, "暂时无法观看此视频，请稍后重试");
        errorCodeMap.put(SIG_ERROR, "签名错误");
        errorCodeMap.put(PLAY_NO_STREAM, "此集内容暂无{0}视频，建议您选择更高清晰度进行播放");
        errorCodeMap.put(USER_CREATE_QR_CODE, "服务不给力，请重试");
        errorCodeMap.put(USER_CHECK_QR_CODE_1001, "服务不给力，请重试");
        errorCodeMap.put(USER_CHECK_QR_CODE_1002, "服务不给力，请重试");
        errorCodeMap.put(USER_CHECK_QR_CODE_1003, "服务不给力，请重试");
        errorCodeMap.put(USER_CHECK_TOKEN_LOGIN, "Token不正确或已过期");
        errorCodeMap.put(USER_SEND_AUTH_CODE_ERROR, "发送短信验证码错误");
        errorCodeMap.put(USER_REGISTER_CODE_MOBILE_ERROR, "激活码已经过期或者手机号错误");
        errorCodeMap.put(USER_CONSUME_PHONE, "短信发送失败，请重试");
        errorCodeMap.put(USER_ORDER_PAID, "订单已支付，请勿重复支付");
        errorCodeMap.put(CN_PLAY_FORBIDDEN_CODE, "因政策原因，该内容无法提供观看");
        errorCodeMap.put(NOT_CN_PLAY_FORBIDDEN_CODE, "因版权方要求，此视频仅支持在中国大陆播放");
        LETV_THIRDPARTY_CONSUME_MAP.put(ErrorCodeThirdpartyConstant.CONSUMPTION_ERROR_CODE_0000,
                CONSUMPTION_ERROR_CODE_0000);
        LETV_THIRDPARTY_CONSUME_MAP.put(ErrorCodeThirdpartyConstant.CONSUMPTION_ERROR_CODE_0001,
                CONSUMPTION_ERROR_CODE_0001);
        LETV_THIRDPARTY_CONSUME_MAP.put(ErrorCodeThirdpartyConstant.CONSUMPTION_ERROR_CODE_0002,
                CONSUMPTION_ERROR_CODE_0002);
        LETV_THIRDPARTY_CONSUME_MAP.put(ErrorCodeThirdpartyConstant.CONSUMPTION_ERROR_CODE_0003,
                CONSUMPTION_ERROR_CODE_0003);
        LETV_THIRDPARTY_CONSUME_MAP.put(ErrorCodeThirdpartyConstant.CONSUMPTION_ERROR_CODE_0004,
                CONSUMPTION_ERROR_CODE_0004);
        LETV_THIRDPARTY_CONSUME_MAP.put(ErrorCodeThirdpartyConstant.CONSUMPTION_ERROR_CODE_0005,
                CONSUMPTION_ERROR_CODE_0005);
        LETV_THIRDPARTY_CONSUME_MAP.put(ErrorCodeThirdpartyConstant.CONSUMPTION_ERROR_CODE_0006,
                CONSUMPTION_ERROR_CODE_0006);

        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_VALIDATECODE_EXPIRED,
                PAY_ALIPAY_VALIDATECODE_EXPIRED);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_VALIDATECODE_EXCEED_LIMITED,
                PAY_ALIPAY_VALIDATECODE_EXCEED_LIMITED);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_VALIDATECODE_ANSWER_ERROR,
                PAY_ALIPAY_VALIDATECODE_ANSWER_ERROR);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_VALIDATECODE_IS_NOT_EXIST,
                PAY_ALIPAY_VALIDATECODE_IS_NOT_EXIST);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_LEDIAN_0001, PAY_LEDIAN_0001);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_LEDIAN_0002, PAY_LEDIAN_0002);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_LEDIAN_1000, PAY_LEDIAN_1000);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_LEDIAN_1001, PAY_LEDIAN_1001);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_LEDIAN_1002, PAY_LEDIAN_1002);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_LEDIAN_1003, PAY_LEDIAN_1003);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_LEDIAN_1004, PAY_LEDIAN_1004);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ERRORCODE_1004, PAY_INVALIDE_PACKAGETYPE);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_VERIFY_CODE_ERROR,
                PAY_ALIPAY_VERIFY_CODE_ERROR);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_VERIFY_CODE_ERROR,
                PAY_ALIPAY_VERIFY_CODE_ERROR);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_VERIFY_FIAL_OF_CARD_NO,
                PAY_ALIPAY_VERIFY_FIAL_OF_CARD_NO);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_VERIFY_FIAL_OF_REAL_NAME,
                PAY_ALIPAY_VERIFY_FIAL_OF_REAL_NAME);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_VERIFY_FIAL_OF_VALID_DATE,
                PAY_ALIPAY_VERIFY_FIAL_OF_VALID_DATE);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_VERIFY_FIAL_OF_CERT_NO,
                PAY_ALIPAY_VERIFY_FIAL_OF_CERT_NO);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_VERIFY_FIAL_OF_MOBILE,
                PAY_ALIPAY_VERIFY_FIAL_OF_MOBILE);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_CARD_SIGN_FAIL, PAY_ALIPAY_CARD_SIGN_FAIL);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_USER_REPLY_TIMEOUT,
                PAY_ALIPAY_USER_REPLY_TIMEOUT);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_MOBILE_BIND_EXCEED_MAX_CARD_SIZE,
                PAY_ALIPAY_MOBILE_BIND_EXCEED_MAX_CARD_SIZE);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_USER_SIGNED, PAY_ALIPAY_USER_SIGNED);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_EXTERNAL_USER_MISMATCH,
                PAY_ALIPAY_EXTERNAL_USER_MISMATCH);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_USER_MOBILE_NOT_ALLOW_SIGN,
                PAY_ALIPAY_USER_MOBILE_NOT_ALLOW_SIGN);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_USER_CERTIFY_NOT_ALLOW_SIGN,
                PAY_ALIPAY_USER_CERTIFY_NOT_ALLOW_SIGN);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_USER_HAS_BLOCK, PAY_ALIPAY_USER_HAS_BLOCK);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_NOT_HAVE_ENOUGH_BALANCE,
                PAY_ALIPAY_NOT_HAVE_ENOUGH_BALANCE);
        LETV_THIRDPARTY_PAY_MAP.put(ErrorCodeThirdpartyConstant.PAY_ALIPAY_ACK_NO_IS_NOT_ALLOWED_NULL,
                PAY_ALIPAY_ACK_NO_IS_NOT_ALLOWED_NULL);
    }

    public static String getMessage(String key) {
        String value = errorCodeMap.get(key);
        if (StringUtils.isBlank(value)) {
            value = "未知异常";
        }
        return value;
    }

    public static String getThirdMessage(String thirdKey, Map<String, String> codeMap) {
        String key = codeMap.get(thirdKey);
        String value = errorCodeMap.get(key);
        if (StringUtils.isBlank(value)) {
            value = "未知异常";
        }
        return value;
    }

    /**
     * 验证是否需要抛出异常
     * @param code
     *            待验证code
     * @param allCodes
     *            所有会出现的code
     * @param errorCodes
     *            与所有出现的code对应的errorCode
     * @param defaultErrorCode
     *            匹配不上时的默认errorCode
     */
    public static void checkErrorCode(String code, String[] allCodes, String[] errorCodes, String defaultErrorCode) {
        if (code == null || code.trim().length() == 0) {
            return;
        }
        if (allCodes != null && allCodes.length > 0) {
            for (int i = 0; i < allCodes.length; i++) {
                if (code.equalsIgnoreCase(allCodes[i])) {// 配上上抛出异常
                    if (errorCodes != null && errorCodes.length > i) {
                        throw new LetvCommonException(errorCodes[i], getMessage(errorCodes[i]));
                    }
                }
            }
            throw new LetvCommonException(defaultErrorCode, getMessage(defaultErrorCode));
        }
    }

    /**
     * 判断异常类型来抛出异常
     * @param e
     *            异常
     * @param defaultErrorCode
     *            匹配不上时的默认errorCode
     */
    public static void throwLetvCommonException(Exception e, String defaultErrorCode) {
        if (e != null) {
            if (e instanceof LetvCommonException) {
                throw new LetvCommonException(((LetvCommonException) e).getErrorCode(), e.getMessage());
            } else {
                throw new LetvCommonException(defaultErrorCode, getMessage(defaultErrorCode));
            }
        }
    }

    public static void throwLetvCommonException(String code) {
        // throw new LetvCommonException(code, getMessage(code));
        throwLetvCommonException(code, MessageUtils.DEFAULT_LOCAL_ZH_CN);
    }

    public static void throwLetvCommonException(String code, Locale locale) {
        // throw new LetvCommonException(code, MessageUtils.getMessage(code,
        // locale));
        String errorMsg = MessageUtils.getMessage(code, locale);
        if (StringUtils.isBlank(errorMsg)) {
            code = DEFAULT_ERROR_CODE;
            errorMsg = MessageUtils.getMessage(code, locale);
        }
        throw new LetvCommonException(code, errorMsg);
    }

    public static void throwLetvCommonException(String code, String langcode) {
        String errorMsg = MessageUtils.getMessage(code, langcode);
        if (StringUtils.isBlank(errorMsg)) {
            code = DEFAULT_ERROR_CODE;
            errorMsg = MessageUtils.getMessage(code, langcode);
        }
        throw new LetvCommonException(code, errorMsg);
    }

    /**
     * 返回指定错误码和错误信息的异常对象
     * 2015-05-27
     * @param code
     * @param errorMsg
     * @param locale
     */
    public static void throwLetvCommonException(String code, String errorMsg, Locale locale) {
        if (StringUtils.isBlank(errorMsg)) {
            code = DEFAULT_ERROR_CODE;
            errorMsg = MessageUtils.getMessage(code, locale);
        }
        throw new LetvCommonException(code, errorMsg);
    }

    public static void throwLetvCommonException(String code, String... codeMap) {
        String msg = getMessage(code);
        throw new LetvCommonException(code, MessageFormat.format(msg, codeMap));
    }

    public static void throwLetvCommonExceptionByThirdpatyCode(String t_code, Map<String, String> codeMap) {
        String letvCode = codeMap.get(t_code);
        if (letvCode == null) {
            letvCode = DEFAULT_ERROR_CODE;
        }
        throw new LetvCommonException(letvCode, getMessage(letvCode));
    }
}
