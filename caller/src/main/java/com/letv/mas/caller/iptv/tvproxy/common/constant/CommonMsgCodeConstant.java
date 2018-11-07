package com.letv.mas.caller.iptv.tvproxy.common.constant;

/**
 * 参数校验公共错误码常量类，其他模块的参数校验错误码常量类继承该类
 * @author liyunlong
 */
public class CommonMsgCodeConstant {

    /**
     * 通用的参数校验返回码,编码范围{0,{9000-9999}}
     */
    public final static int REQUEST_SUCCESS = 0;// 校验成功
    public final static int REQUEST_USERINFO_ERROR = 9000;// 用户信息错误
    public final static int REQUEST_PAGE_ILLEGAL = 9001;// 分页信息错误
    public final static int REQUEST_MAC_EMPTY = 9002;// 设备MAC地址为空
    public final static int REQUEST_DEVICEKEY_EMPTY = 9003;// 设备码为空
    public final static int REUQEST_DEVICEVERSION_EMPTY = 9004;// 设备型号为空
    public final static int REUQEST_PAYMENTCHANNEL_ERROR = 9005;// 支付渠道错误
    public final static int REUQEST_ILLEGAL_VISIT_ERROR = 9006;// 非法授权访问
}
