package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier;

import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;

/**
 * Created by wangli on 4/24/17.
 * 收银台的常量
 */
public class ApplicationConstants {

    private static final String PAY_API_VL_COM_BASEURL = "pay.api.vip.le.com.baseurl";

    private static final String PAY_API_BOSS_PAY_BASEURL = "boss.quick.pay.baseurl";

    private static final String PAY_API_IL_COM_BASEURL = "pay.api.i.letv.com.baseurl";

    private static final String PAY_API_SL_COM_BASEURL = "pay.api.static.letv.com.baseurl";

    private static final String PAY_CIBN_SSO_NET_BASEURL = "pay.cibntv.sso.net.baseurl";

    private static final String PAY_CIBN_TG_NET_BASEURL = "pay.cibntv.tgo.net.baseurl";

    // 付费会员支付接口地址：获取收银台信息，获取订单信息，获取商品信息，创建订单
    public static final String PAY_API_VL_BASE_HOST = ApplicationUtils.get(PAY_API_VL_COM_BASEURL);
    // 视频信息获取媒资接口地址：获取视频信息
    public static final String PAY_API_IL_BASE_HOST = ApplicationUtils.get(PAY_API_IL_COM_BASEURL);
    // 收银台推荐视频接口地址：获取推荐信息
    public static final String PAY_API_SL_BASE_HOST = ApplicationUtils.get(PAY_API_SL_COM_BASEURL);
    // 订单支付地址生成接口地址：生成支付地址
    public static final String PAY_API_SSO_BASE_HOST = ApplicationUtils.get(PAY_CIBN_SSO_NET_BASEURL);
    // 短链服务接口地址：生成短链接
    public static final String PAY_API_TG_BASE_HOST = ApplicationUtils.get(PAY_CIBN_TG_NET_BASEURL);
    // Boss代扣接口
    public static final String PAY_API_BOSS_PAY_BASE_HOST = ApplicationUtils.get(PAY_API_BOSS_PAY_BASEURL);

    private static final String PAY_ORDER_QRCODE_BASEURL_KEY = "pay.order.qrcode.baseurl.key";

    private static final String PAY_ORDER_QRCODE_CREATEURL_KEY = "pay.order.qrcode.createurl.key";

    // 短链服务的域名
    public static final String PAY_ORDER_QRCODE_BASEURL_VALUE = ApplicationUtils.get(PAY_ORDER_QRCODE_BASEURL_KEY);
    // 收银台服务的跳转地址
    public static final String PAY_ORDER_QRCODE_CREATEURL_VALUE = ApplicationUtils.get(PAY_ORDER_QRCODE_CREATEURL_KEY);
}
