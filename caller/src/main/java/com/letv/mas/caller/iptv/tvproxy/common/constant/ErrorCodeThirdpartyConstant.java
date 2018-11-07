package com.letv.mas.caller.iptv.tvproxy.common.constant;

public class ErrorCodeThirdpartyConstant {
    /**************************************** 消费 ****************************************************/
    public static String PAY_ERRORCODE_1004 = "1004"; // 消费中心没有对应的产品包
    public static String PAY_LEDIAN_SUCCESS = "0000";// 乐点消费成功
    public static String PAY_LEDIAN_0001 = "0001";
    public static String PAY_LEDIAN_0002 = "0002";
    public static String PAY_LEDIAN_1000 = "1000";
    public static String PAY_LEDIAN_1001 = "1001";
    public static String PAY_LEDIAN_1002 = "1002";
    public static String PAY_LEDIAN_1003 = "1003";
    public static String PAY_LEDIAN_1004 = "1004";
    public static String PAY_LEDIAN_1005 = "1005";

    public static String PAY_ALIPAY_GETSIGNNO_SUCCESS = "0000";// 支付宝接口成功
    public static String PAY_ALIPAY_VERIFY_CODE_ERROR = "9998";// 支付宝md5加密错误

    public static String PAY_ALIPAY_NOT_HAVE_ENOUGH_BALANCE = "NOT_HAVE_ENOUGH_BALANCE";// 余额不足
    public static String PAY_ALIPAY_ACK_NO_IS_NOT_ALLOWED_NULL = "ACK_NO_IS_NOT_ALLOWED_NULL";// 用户确认码参数不能为空
    public static String PAY_ALIPAY_VERIFY_FIAL_OF_CARD_NO = "ILLEGAL_ARGUMENT";// 银行卡校验未通过
    public static String PAY_ALIPAY_VERIFY_FIAL_OF_REAL_NAME = "ILLEGAL_ARGUMENT";// 姓名校验未通过
    public static String PAY_ALIPAY_VERIFY_FIAL_OF_VALID_DATE = "ILLEGAL_ARGUMENT";// 有效期校验未通过
    public static String PAY_ALIPAY_VERIFY_FIAL_OF_CERT_NO = "VERIFY_FIAL_OF_CERT_NO";// 证件号校验未通过
    public static String PAY_ALIPAY_VERIFY_FIAL_OF_MOBILE = "VERIFY_FIAL_OF_MOBILE";// 手机号校验未通过
    public static String PAY_ALIPAY_CARD_SIGN_FAIL = "CARD_SIGN_FAIL";// 银行卡签约失败
    public static String PAY_ALIPAY_USER_REPLY_TIMEOUT = "USER_REPLY_TIMEOUT";// 用户回复超时
    public static String PAY_ALIPAY_MOBILE_BIND_EXCEED_MAX_CARD_SIZE = "MOBILE_BIND_EXCEED_MAX_CARD_SIZE";// 手机申请绑定账号个数超过系统设置
    public static String PAY_ALIPAY_USER_SIGNED = "USER_SIGNED";// 用户已签约
    public static String PAY_ALIPAY_EXTERNAL_USER_MISMATCH = "EXTERNAL_USER_MISMATCH";// 商户用户账号或支付宝账号与已绑定的不一致
    public static String PAY_ALIPAY_USER_MOBILE_NOT_ALLOW_SIGN = "USER_MOBILE_NOT_ALLOW_SIGN";// 商户限制用户绑定手机但用户未绑定手机
    public static String PAY_ALIPAY_USER_CERTIFY_NOT_ALLOW_SIGN = "USER_CERTIFY_NOT_ALLOW_SIGN";// 用户未通过实名认证不允许签约
    public static String PAY_ALIPAY_USER_HAS_BLOCK = "USER_HAS_BLOCK";// 用户账户被冻结
    public static String PAY_ALIPAY_VALIDATECODE_EXPIRED = "VALIDATECODE_EXPIRED";// 确认支付验证码过期
    public static String PAY_ALIPAY_VALIDATECODE_EXCEED_LIMITED = "VALIDATECODE_EXCEED_LIMITED";// 验证码输入超过次数限制
    public static String PAY_ALIPAY_VALIDATECODE_ANSWER_ERROR = "VALIDATECODE_ANSWER_ERROR";// 验证码输入错误
    public static String PAY_ALIPAY_VALIDATECODE_IS_NOT_EXIST = "VALIDATECODE_IS_NOT_EXIST";// 无效验证码，请重新获取

    public static final String CONSUMPTION_ERROR_CODE_1001 = "1001";
    public static final String CONSUMPTION_ERROR_CODE_1002 = "1002";
    public static final String CONSUMPTION_ERROR_CODE_1003 = "1003";
    public static final String CONSUMPTION_ERROR_CODE_1004 = "1004";
    public static final String CONSUMPTION_ERROR_CODE_1005 = "1005";
    public static final String CONSUMPTION_ERROR_CODE_0 = "0";
    public static final String CONSUMPTION_ERROR_CODE_0000 = "0000";
    public static final String CONSUMPTION_ERROR_CODE_0001 = "0001";
    public static final String CONSUMPTION_ERROR_CODE_0002 = "0002";
    public static final String CONSUMPTION_ERROR_CODE_0003 = "0003";
    public static final String CONSUMPTION_ERROR_CODE_0004 = "0004";
    public static final String CONSUMPTION_ERROR_CODE_0005 = "0005";
    public static final String CONSUMPTION_ERROR_CODE_0006 = "0006";

}
