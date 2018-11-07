package com.letv.mas.caller.iptv.tvproxy.common.constant;


import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;

/**
 * 首先、application.properties中的key都必须在这里被定义。 其次、application.properties的key定义规则如下
 * url的命名为：部门.模块.功能.url.版本=value 通用参数命名为：iptv.模块.功能.param=value 比如
 * ：search.detail.url.1 最后、本类属性定义规范
 * application.properites中对应的key大写并以'_'间隔替换'.'间隔。
 * 比如：search.search.url.1对应url为SEARCH_SEARCH_URL_1
 */
public final class ApplicationConstants {

    /**
     * all base url application key
     */
    public static final String SEARCH_LE_SO_LETV_COM_BASEURL = "search.le.so.letv.com.baseurl";
    public static final String SEARCH_I_OPEN_API_LETV_COM_BASEURL = "search.i.open.api.letv.com.baseurl";
    public static final String SEARCH_DATA_SO_LETV_COM_BASEURL = "search.data.so.letv.com.baseurl";
    public static final String SEARCH_SUGGEST_BASEURL = "search.suggest.baseurl";
    public static final String SEARCH_TOP_BASEURL = "search.top.baseurl";
    public static final String BOSS_YUANXIAN_BASEURL = "boss.yuanxian.baseurl";
    public static final String VIP_LE_BASEURL = "huiyuan.vip.le.baseurl";
    public static final String BOSS_PRICE_ZHIFU_LETV_COM_BASEURL = "boss.price.zhifu.letv.com.baseurl";
    public static final String BOSS_API_ZHIFU_LETV_COM_BASEURL = "boss.api.zhifu.letv.com.baseurl";
    public final static String BOSS_LEPAY_LETV_COM_BASEURL = "boss.lepay.letv.com.baseurl";
    public static final String BOSS_ZHIFU_LETV_COM_BASEURL = "boss.zhifu.letv.com.baseurl";
    public static final String VRS_I_API_BASE_URL = "vrs.i.api.letv.com.baseurl";
    public static final String WORKS_I_API_BASE_URL = "works.i.api.letv.com.baseurl";
    public static final String BOSS_IPAY_LETV_COM_BASEURL = "boss.ipay.letv.com.baseurl";
    public static final String BOSS_USPAY_LE_COM_BASEURL = "boss.uspay.le.com.baseurl";
    public static final String CMS_BLOCK_BASEURL = "cms.block.baseurl";
    public static final String CMS_LE_BLOCK_BASEURL = "cms.le.block.baseurl";
    public static final String GSLB_G3_BASEURL = "gslb.g3.baseurl";
    public static final String USERCENTER_API_MY_LETV_COM_BASEURL = "usercenter.api.my.letv.com.baseurl";
    public static final String ODP_MY_LE_COM_BASEURL = "odp.my.le.com.baseurl";
    public static final String USERCENTER_API_SSO_LETV_COM_BASEURL = "usercenter.api.sso.letv.com.baseurl";
    public static final String USERCENTER_SSO_LETV_COM_BASEURL = "usercenter.sso.letv.com.baseurl";
    public static final String USERCENTER_SSO_LE_COM_BASEURL = "usercenter.sso.le.com.baseurl";
    public static final String RECOMMEND_DATA_BASEURL = "recommend.data.baseurl";
    public static final String RECOMMEND_LEVIEW_BLOCK_ID = "recommend.leview.block.id";
    public static final String RECOMMEND_CIBN_LEVIEW_BLOCK_ID = "recommend.cibn.leview.block.id";
    public static final String RECOMMEND_WASU_LEVIEW_BLOCK_ID = "recommend.wasu.leview.block.id";
    public static final String RECOMMEND_LEVIEW_RELEASE_BLOCK_ID = "recommend.leview.new.block.id";
    public static final String RECOMMEND_LEVIEW_RELEASE_DATA_BLOCK_ID = "recommend.leview.release.data.block.id";
    public static final String RELEASE_APP_VERSION = "leview_release_app_version";
    public static final String BOX_API_HDTV_LETV_COM_BASEURL = "box.api.hdtv.letv.com.baseurl";
    public static final String USER_API_EROSNOW_COM_BASEURL = "user.api.erosnow.com.baseurl";
    public static final String VIPCENTER_USER_TOUCH_COM_BASEURL = "vipcenter.user.touch.com.baseurl";
    public static final String URM_API_MESSAGE_LE_COM_BASEURL = "urm.api.message.le.com.baseurl";
    public static final String GUANXING_PROMOTION_BASE_HOST_KEY = "guanxing.baseurl";
    public static final String EXPERIMENT_ABTEST_LE_COM_BASEURL = "experiment.abtest.le.com.baseurl";
    public static final String IPTV_I_STATIC_BASEURL = "iptv.i.static.baseurl";
    public static final String IPTV_STATIC_BASEURL = "iptv.static.baseurl";
    public static final String IPTV_API_ITV_LETV_COM_BASEURL = "iptv.api.itv.letv.com.baseurl";
    public static final String WWW_LETV_COM_BASEURL = "cms.data.baseurl";
    public static final String WEBSITE_WWW_LETV_COM_BASEURL = "website.www.letv.com";
    public static final String WEBSITE_WWW_LE_COM_BASEURL = "website.www.le.com";
    public static final String CAPI_ITV_LETV_COM_BASEURL = "capi.itv.letv.com.baseurl";
    public static final String I_API_ITV_LETV_COM_BASEURL = "i.api.itv.letv.com.baseurl";
    public static final String HD_MY_LETV_COM_BASEURL = "hd.my.letv.com.baseurl";
    public static final String API_LIVE_LETV_COM_BASEURL = "api.live.letv.com.baseurl";
    public static final String D_ITV_LETV_COM_BASEURL = "d.itv.letv.com.baseurl";
    public static final String S_ITV_LETV_COM_BASEURL = "s.itv.letv.com.baseurl";
    public static final String TERMIANL_UPGRADE_BASEURL = "upgrade.itv.letv.com.baseurl";
    public static final String CMS_XIANMIAN_TV_HOST_KEY = "cms.xianmian.tv.get";
    public final static String LEPAY_BASEURL = "lepay.baseurl";
    public final static String IPTV_I_API_BASEURL = "iptv.i.api.itv.letv.com.baseurl";

    /**
     * all base url host
     */
    // ================search===================//
    public static final String SEARCH_LE_SO_BASE_HOST = ApplicationUtils.get(SEARCH_LE_SO_LETV_COM_BASEURL);// http://le.so.letv.com
    public static final String SEARCH_I_OPEN_API_BASE_HOST = ApplicationUtils.get(SEARCH_I_OPEN_API_LETV_COM_BASEURL);// http://i.open.api.letv.com
    public static final String SEARCH_DATA_SO_BASE_HOST = ApplicationUtils.get(SEARCH_DATA_SO_LETV_COM_BASEURL);// http://data.so.letv.com
    public static final String SEARCH_SUGGEST_BASE_HOST = ApplicationUtils.get(SEARCH_SUGGEST_BASEURL);// http://suggest.letv.cn
    public static final String SEARCH_I_TOP_BASE_HOST = ApplicationUtils.get(SEARCH_TOP_BASEURL);// http://i.top.letv.com
    public static final String REC_BASE_HOST = ApplicationUtils.get(RECOMMEND_DATA_BASEURL);// http://rec.letv.com
    public static final String REC_LEVIEW_BLOCK_ID = ApplicationUtils.get(
            ApplicationConstants.RECOMMEND_LEVIEW_BLOCK_ID, "");
    public static final String REC_CIBN_LEVIEW_BLOCK_ID = ApplicationUtils.get(
            ApplicationConstants.RECOMMEND_CIBN_LEVIEW_BLOCK_ID, "");
    public static final String REC_WASU_LEVIEW_BLOCK_ID = ApplicationUtils.get(
            ApplicationConstants.RECOMMEND_WASU_LEVIEW_BLOCK_ID, "");
    public static final String REC_LEVIEW_RELEASE_BLOCK_ID = ApplicationUtils.get(
            ApplicationConstants.RECOMMEND_LEVIEW_RELEASE_BLOCK_ID, "");
    public static final String REC_LEVIEW_RELEASE_DATA_BLOCK_ID = ApplicationUtils.get(
            ApplicationConstants.RECOMMEND_LEVIEW_RELEASE_DATA_BLOCK_ID, "");
    public static final String LEVIEW_RELEASE_APP_VERSION = ApplicationUtils.get(
            ApplicationConstants.RELEASE_APP_VERSION, "");
    // ================boss===================//
    public static final String BOSS_YUANXIAN_BASE_HOST = ApplicationUtils.get(BOSS_YUANXIAN_BASEURL);// http://yuanxian.letv.com
    public static final String VIP_LE_BASE_HOST = ApplicationUtils.get(VIP_LE_BASEURL);
    public static final String BOSS_PRICE_ZHIFU_BASE_HOST = ApplicationUtils.get(BOSS_PRICE_ZHIFU_LETV_COM_BASEURL);// http://price.zhifu.letv.com
    public static final String BOSS_LEPAY_BASE_HOST = ApplicationUtils.get(BOSS_LEPAY_LETV_COM_BASEURL);// http://lepay.letv.com
    public static final String BOSS_API_ZHIFU_BASE_HOST = ApplicationUtils.get(BOSS_API_ZHIFU_LETV_COM_BASEURL);// http://api.zhifu.letv.com
    public static final String BOSS_ZHIFU_BASE_HOST = ApplicationUtils.get(BOSS_ZHIFU_LETV_COM_BASEURL);// http://boss.zhifu.letv.com
    public static final String BOSS_IPAY_ZHIFU_BASE_HOST = ApplicationUtils.get(BOSS_IPAY_LETV_COM_BASEURL);// http://ipay.letv.com
    public static final String BOSS_USPAY_LE_BASE_HOST = ApplicationUtils.get(BOSS_USPAY_LE_COM_BASEURL);// http://uspay.le.com
    // ================vrs/cms/works===================//
    public static final String VRS_I_API_BASE_HOST = ApplicationUtils.get(VRS_I_API_BASE_URL);// http://i.api.letv.com
    public static final String CMS_STATIC_API_BASE_HOST = ApplicationUtils.get(CMS_BLOCK_BASEURL);// http://static.api.letv.com
    public static final String CMS_LE_API_BASE_HOST = ApplicationUtils.get(CMS_LE_BLOCK_BASEURL);// http://api.cms.le.com
    public static final String WWW_LETV_COM_BASE_HOST = ApplicationUtils.get(WWW_LETV_COM_BASEURL);// http://www.letv.com
    public static final String WORKS_I_API_BASE_HOST = ApplicationUtils.get(WORKS_I_API_BASE_URL);// TODO:
                                                                                                  // http://i.api.letv.com

    // ================usercenter===================//
    public static final String USERCENTER_API_MY_BASE_HOST = ApplicationUtils.get(USERCENTER_API_MY_LETV_COM_BASEURL);// http://api.my.letv.com
    public static final String USERCENTER_API_SSO_BASE_HOST = ApplicationUtils.get(USERCENTER_API_SSO_LETV_COM_BASEURL);// http://api.sso.letv.com
    public static final String USERCENTER_SSO_BASE_HOST = ApplicationUtils.get(USERCENTER_SSO_LETV_COM_BASEURL);// https://sso.letv.com
    public static final String USERCENTER_SSO_LE_BASE_HOST = ApplicationUtils.get(USERCENTER_SSO_LE_COM_BASEURL);// http://sso.le.com
    // ================odp===================//
    public static final String ODP_MY_BASE_HOST = ApplicationUtils.get(ODP_MY_LE_COM_BASEURL);// http://odp.my.le.com
    // ================other===================//
    public static final String PLAY_G3_BASE_HOST = ApplicationUtils.get(GSLB_G3_BASEURL);// http://g3.letv.com
    public static final String BOX_API_HDTV_BASE_HOST = ApplicationUtils.get(BOX_API_HDTV_LETV_COM_BASEURL);// http://api.hdtv.letv.com
    public static final String USER_API_EROSNOW_BASE_HOST = ApplicationUtils.get(USER_API_EROSNOW_COM_BASEURL);// https://api.erosnow.com
    public static final String VIPCENTER_USER_TOUCH_BASE_HOST = ApplicationUtils.get(VIPCENTER_USER_TOUCH_COM_BASEURL);// http://tes.touch.my.letv.com
    public static final String URM_TOUCH_BASE_HOST = ApplicationUtils.get(URM_API_MESSAGE_LE_COM_BASEURL);// http://api.message.le.com
    public static final String GUANXING_PROMOTION_BASE_HOST_URL = ApplicationUtils
            .get(GUANXING_PROMOTION_BASE_HOST_KEY);// http://stargazer-scloud.cp21.ott.cibntv.net/proxy
    public static final String EXPERIMENT_ABTEST_BASE_HOST = ApplicationUtils.get(EXPERIMENT_ABTEST_LE_COM_BASEURL);// http://dm-exp-planout4j-service.go.le.com
    public static final String I_STATIC_ITV_BASE_HOST = ApplicationUtils.get(IPTV_I_STATIC_BASEURL);// http://i.static.itv.letv.com
    public static final String API_ITV_LETV_BASE_HOST = ApplicationUtils.get(IPTV_API_ITV_LETV_COM_BASEURL);// http://api.itv.letv.com
    public static final String WEBSITE_WWW_LETV_COM = ApplicationUtils.get(WEBSITE_WWW_LETV_COM_BASEURL);// www.letv.com
    public static final String WEBSITE_WWW_LE_COM = ApplicationUtils.get(WEBSITE_WWW_LE_COM_BASEURL);// www.le.com
    public static final String CAPI_ITV_LETV_BASE_HOST = ApplicationUtils.get(CAPI_ITV_LETV_COM_BASEURL); // http://capi.itv.letv.com
    public static final String I_API_ITV_LETV_BASE_HOST = ApplicationUtils.get(I_API_ITV_LETV_COM_BASEURL); // http://i.api.itv.letv.com
    public static final String HD_MY_LETV_BASE_HOST = ApplicationUtils.get(HD_MY_LETV_COM_BASEURL); // http://hd.my.letv.com
    public static final String API_LIVE_LETV_BASE_HOST = ApplicationUtils.get(API_LIVE_LETV_COM_BASEURL); // http://api.live.letv.com
    public static final String D_ITV_LETV_BASE_HOST = ApplicationUtils.get(D_ITV_LETV_COM_BASEURL); // http://d.itv.letv.com
    public static final String S_ITV_LETV_BASE_HOST = ApplicationUtils.get(S_ITV_LETV_COM_BASEURL); // http://s.itv.letv.com
    public static final String TERMINAL_UPGRADE_BASE_HOST = ApplicationUtils.get(TERMIANL_UPGRADE_BASEURL); // http://upgrade.itv.letv.com
    public static final String STATIC_ITV_BASE_HOST = ApplicationUtils.get(IPTV_STATIC_BASEURL);
    public static final String CMS_XIANMIAN_TV_HOST_URL = ApplicationUtils.get(CMS_XIANMIAN_TV_HOST_KEY);// http://stargazer-scloud.cp21.ott.cibntv.net/proxy
    public static final String LEPAY_BASE_HOST = ApplicationUtils.get(LEPAY_BASEURL);
    public static final String IPTV_I_API_BASE_HOST = ApplicationUtils.get(IPTV_I_API_BASEURL);

    /**
     * 终端
     */
    public static final String IPTV_TERMINAL_VERSION_TVUPGRADE = "iptv.terminal.version.tvupgrade";
    public static final String IPTV_TERMINAL_BOXCONFIG_GROUPINFO = "iptv.terminal.boxconfig.groupinfo";
    public static final String IPTV_TERMINAL_SERVICE_TERM_CONTENT_TYPE_PRIVACY_POLICY = "iptv.terminal.service.term.content.type.privacy.policy";
    public static final String IPTV_TERMINAL_SERVICE_TERM_CONTENT_TYPE_SERVICE_TERMS = "iptv.terminal.service.term.content.type.service.terms";

    /**
     * 应用商店
     */
    public static final String LETVSTORE_RECOMMEND_APP_GET = "letvstore.recommend.app.get";
    public static final String LETVSTORE_SEARCH_APP_QUERY = "letvstore.search.app.query";

    /**
     * 消费
     */
    public static final String BOSS_YUANXIAN_ORDER_GET = "boss.yuanxian.order.get";
    public static final String BOSS_YUANXIAN_PERMISION = "boss.yuanxian.permision";
    public static final String BOSS_YUANXIAN_PERMISION_BY_MAC = "boss.yuanxian.permision.mac";
    public static final String BOSS_YUANXIAN_VIDEO_CANPLAY_CHECK = "boss.yuanxian.video.canplay.check";
    public final static String BOSS_API_ZHIFU_RECHARGE_RECORD = "boss.api.zhifu.recharge.record";

    public final static String BOSS_YUANXIAN_CONSUME_RECORD_V1 = "boss.yuanxian.consume.record.v1";
    public final static String BOSS_YUANXIAN_CONSUME_RECORD_V2 = "boss.yuanxian.consume.record.v2";
    public final static String BOSS_YUANXIAN_CONSUME_RECORD_V3 = "boss.yuanxian.consume.record.v3";

    public final static String BOSS_YUANXIAN_ACTIVITY_INFO = "boss.yuanxian.activity.info";
    public final static String BOSS_YUANXIAN_ORDER_SINGLE_PRODUCT = "boss.yuanxian.order.single.product";
    public final static String BOSS_YUANXIAN_ORDER_PACKAGE = "boss.yuanxian.order.package";
    public final static String BOSS_API_ZHIFU_ALIPAY_CODE_WEB = "boss.api.zhifu.alipay.code.web";
    public final static String BOSS_API_ZHIFU_ALIPAY_CODE_IMG = "boss.api.zhifu.alipay.code.img";
    public final static String BOSS_API_ZHIFU_ALIPAY_CODE_CIBN_IMG = "boss.api.zhifu.alipay.code.cibn.img";
    public final static String BOSS_API_ZHIFU_WEIXIN_CODE_IMG = "boss.api.zhifu.weixin.code.img";
    public final static String BOSS_API_ZHIFU_PHONE_PAY = "boss.api.zhifu.phone.pay";
    public final static String BOSS_API_ZHIFU_LAKALA_PAY = "boss.api.zhifu.lakala.pay";
    public final static String BOSS_API_ZHIFU_LETVPOINT_PAY = "boss.api.zhifu.letvpoint.pay";
    public final static String BOSS_API_ZHIFU_ALIPAY_SINGLE_CODE_IMG = "boss.api.zhifu.alipay.single.code.img";
    public final static String BOSS_API_ZHIFU_ALIPAY_SINGLE_CODE_CIBN_IMG = "boss.api.zhifu.alipay.single.code.cibn.img";
    public final static String BOSS_API_ZHIFU_WEIXIN_SINGLE_CODE_IMG = "boss.api.zhifu.weixin.single.code.img";
    public final static String BOSS_API_ZHIFU_LAKALA_SINGLE_CODE_IMG = "boss.api.zhifu.lakala.single.code.img";
    public final static String BOSS_API_ZHIFU_PACKAGE_PHONE_CHECK = "boss.api.zhifu.package.phone.check";
    public final static String BOSS_YUANXIAN_ORDER_INFO = "boss.yuanxian.order.info";
    public final static String BOSS_API_ZHIFU_PACKAGE_YEAR_PURCHASE = "boss.api.zhifu.package.year.purchase";
    public final static String BOSS_YUANXIAN_DEVICE_BIND_QUERY = "boss.yuanxian.device.bind.query";
    public static final String BOSS_YUANXIAN_VIDEO_CANPLAY_CHECK_1 = "boss.yuanxian.video.canplay.check.1";
    public static final String BOSS_YUANXIAN_BIND_QUERY_DEVICE_URL = "boss.yuanxian.bind.query.device.url";
    public static final String BOSS_YUANXIAN_BIND_QUERY_USRE_URL = "boss.yuanxian.bind.query.user.url";
    public static final String BOSS_YUANXIAN_PAYMENT_ACTIVITY_QUERY_URL = "boss.yuanxian.payment.activity.query.url";
    public static final String BOSS_YUANXIAN_CHECK_LIVE_URL = "boss.yuanxian.check.live.url";
    public static final String BOSS_YUANXIAN_DELETE_ORDER_URL = "boss.yuanxian.delete.order.url";
    public static final String BOSS_API_ZHIFU_PURCHASE_VIP_ALI_PAYCODE = "boss.api.zhifu.purchase.vip.ali.paycode";
    public static final String BOSS_API_ZHIFU_PURCHASE_VIP_ALI_CIBN_PAYCODE = "boss.api.zhfu.purchase.vip.ali.cibn.paycode";
    public static final String BOSS_API_ZHIFU_PURCHASE_VIP_WEIXIN_PAYCODE = "boss.api.zhifu.purchase.vip.weixin.paycode";
    public static final String BOSS_API_ZHIFU_PURCHASE_VIP_LAKALA_PAYCODE = "boss.api.zhifu.purchase.vip.lakala.paycode";
    public static final String BOSS_API_ZHIFU_PURCHASE_VIP_LETVPOINT_PAYCODE = "boss.api.zhifu.purchase.vip.letvpoint.paycode";
    public static final String BOSS_PRICE_ZHIFU_CHECK_VOUCHER = "boss.price.zhifu.check.voucher";
    public static final String BOSS_PRICE_ZHIFU_SYNC_USER_LOGIN_INFO = "boss.price.zhifu.sync.user.login.info";
    public static final String BOSS_API_ZHIFU_WEIXIN_PAYCODE_CIBN = "boss.api.zhifu.weixin.paycode.cibn";
    public static final String BOSS_API_ZHIFU_CHECK_ONE_KEY_PAY = "boss.api.zhifu.check.one.key.pay";
    public static final String BOSS_API_ZHIFU_PAYPAL_BIND_AND_PAY = "boss.api.zhifu.paypal.bind.and.pay";
    public static final String BOSS_API_ZHIFU_ONE_KEY_QUICK_PAY = "boss.api.zhifu.one.key.quick.pay";
    public static final String BOSS_YUANXIAN_TPSDK_CHECK_LIVE__URL = "boss.yuanxian.tpsdk.check.live.url";
    public final static String BOSS_API_ZHIFU_ORDER_INFO = "boss.api.zhifu.order.info";
    public static final String BOSS_YUANXIAN_QUERY_PRESENT_DEVICE_BIND = "boss.yuanxian.present.device.bind.query";
    public static final String BOSS_YUANXIAN_REVEIVE_PRESENT_DEVICE_BIND = "boss.yuanxian.present.device.bind.receive";
    public static final String BOSS_YUANXIAN_VIP_ACCOUNT_INFO_GET = "boss.yuanxian.vip.account.info.get";
    public static final String BOSS_API_ZHIFU_LETV_POINT_BALANCE_GET = "boss.api.zhifu.letv.point.balance.get";
    public static final String BOSS_API_ZHIFU_YEEPAY_UNBIND_PAY = "boss.api.zhifu.yeepay.unbind.pay";
    public static final String BOSS_API_ZHIFU_YEEPAY_BIND_PAY = "boss.api.zhifu.yeepay.bind.pay";
    public static final String BOSS_API_ZHIFU_YEEPAY_BINDINFO_GET = "boss.api.zhifu.yeepay.bindinfo.get";
    public static final String BOSS_API_ZHIFU_YEEPAY_ONEBIND_GET = "boss.api.zhifu.yeepay.onebind.get";
    public static final String BOSS_API_ZHIFU_YEEPAY_SENDMSG_URL = "boss.api.zhifu.yeepay.sendmsg.get";
    public static final String BOSS_API_ZHIFU_YEEPAY_CONFIRM_PAY_URL = "boss.api.zhifu.yeepay.pay.confirm";
    public static final String BOSS_API_ZHIFU_YEEPAY_UNBIND_URL = "boss.api.zhifu.yeepay.unbind";
    public static final String BOSS_YUANXIAN_CHECK_USER_LIVE_TICKET = "boss.yuanxian.check.user.live.ticket";
    public static final String BOSS_YUANXIAN_ACTIVE_USER_LIVE_TICKET = "boss.yuanxian.active.user.live.ticket";
    public static final String BOSS_YUANXIAN_VIP_TYPE_URL = "boss.yuanxian.vip.type.get";
    public final static String BOSS_PRICE_ZHIFU_GET_PAY_CHANNEL = "boss.price.zhifu.pay.channel.get";
    public final static String BOSS_YUANXIAN_GET_PAY_CHANNEL_VALIDATE = "boss.yuanxian.pay.channel.validate.get";
    // 2.8.1整理后
    public final static String BOSS_PRICE_ZHIFU_PACKAGE_LIST = "boss.price.zhifu.package.list";
    public final static String BOSS_PRICE_ZHIFU_NONMEMBER_GUIDE_LIST = "boss.price.zhifu.nonmember.guide.list";

    public final static String BOSS_API_ZHIFU_LETVPOINT_CARD_SECRET_VALIDATE = "boss.api.zhifu.letvpoint.card.secret.validate";
    public final static String BOSS_API_ZHIFU_LETVPOINT_CARD_RECHARGE = "boss.api.zhifu.letvpoint.card.recharge";

    public static final String BOSS_YUANXIAN_RECOMMENDPOP_URL = "boss.yuanxian.recommend.pop.get";

    public final static String BOSS_PRICE_ZHIFU_ALBUM_CHARGE_INFO = "boss.price.zhifu.album.charge.info";
    /**
     * 领取免费会员使用的渠道号
     */
    public final static String FREE_VIP_CHANNEL_SEVEN_DAY = "boss.yuanxian.free.vip.channel.seven.day";// 135-7天免费
    public final static String FREE_VIP_CHANNEL_KONKA = "boss.yuanxian.free.vip.channel.konka";// 136-康佳30天免费会员

    /**
     * 领取免费会员接口的签名key
     */
    public static final String FREE_VIP_SIGN_KEY_SEVEN_DAY = "boss.yuanxian.free.vip.sign.key.seven.day";// "568e409d84aeb0cafde43363";
    public static final String FREE_VIP_SIGN_KEY_KONKA = "boss.yuanxian.free.vip.sign.key.konka";// "568e40ee84aeb0cafde43364";
    /**
     * vip--purchaseVip接口中，是否显示会员有效期截至时间开关
     */
    public static final String VIP_PURCHASE_VIP_SHOW_END_TIME_SWITCH = "vip.purchase.vip.show.end.time.switch";

    public static final String VIP_MONTH_PAYMENT_AMOUNT = "vip.purchase.vip.month.package.yeepay.payment.acount";
    public static final String VIP_MONTH_PAYMENT_TEXT = "vip.purchase.vip.month.package.yeepay.payment.text";
    /**
     * 服务单收银台对套餐进行排序开关
     */
    public static final String VIP_CHECKOUT_COUNTER_SORT_PACKAGE_SWITCH = "vip.checkout.counter.sort.package.switch";

    /**
     * 收银台套餐默认排序
     */
    public static final String VIP_CHECKOUT_COUNTER_PACKAGE_DEFAULT_ORDER = "vip.checkout.counter.package.default.order";
    /**
     * 乐点支付是否参加活动开关，参加活动：1;不参加活动:0
     */
    public static final String VIP_CHECKOUT_COUNTER_PAYMENT_CHANNEL_LETVPOINT_SWITCH = "vip.checkout.counter.payment.channel.letvpoint.switch";

    /**
     * 领先版会员模块业务参数key; terminal--终端类型
     */
    public static final String VIP_BOSS_PRICE_ZHIFU_TERMINAL_SUPER_PHONE = "vip.boss.price.zhifu.terminal.super.phone";

    /**
     * vrs 相关
     */
    public static final String VRS_VIDEO_DETAIL_PC_GET = "vrs.video.detail.pc.get";
    public static final String VRS_BROADCAST_STATUS_NOTICE = "vrs.broadcast.status.notice";
    // public static final String VRS_MMS_VIDEO_URLS_GET =
    // "vrs.mms.video.urls.get";
    public static final String VRS_MMS_VIDEO_URLS_GET_1 = "vrs.mms.video.urls.get.1";
    public static final String VRS_MMS_VIDEO_URLS_GET_INNER = "vrs.mms.video.urls.get.inner";
    public static final String VRS_MMS_VIDEO_URLS_GET_F = "vrs.mms.video.urls.get.f";
    public static final String VRS_MMS_VIDEO_HOT_GET_OLD = "vrs.mms.video.hot.get.old";
    public static final String VRS_MMS_ALBUMINFO_GET = "vrs.mms.albuminfo.get";
    public static final String VRS_MMS_VIDEOINFO_INNER_GET = "vrs.mms.videoinfo.inner.get";
    public static final String VRS_MMS_DIC_OUT_GET = "vrs.mms.dic.out.get";
    public static final String VRS_MMS_LIVEWATERMARK_INNER_GET = "vrs.mms.livewatermark.inner.get";
    public static final String VRS_MMS_STAR_INNER_GET = "vrs.mms.star.inner.get";
    public static final String VRS_MMS_PLAY_GET_URL = "vrs.mms.play.get.url";
    public static final String VRS_MMS_INNER_DICT = "vrs.mms.inner.dict";
    public static final String VRS_MMS_INNER_GET_IPGEO = "vrs.mms.inner.get.ipgeo";
    public static final String VRS_MMS_ALBUM_INFO_GET = "vrs.mms.album.info.get";
    public static final String VRS_MMS_ALBUM_SERIALIS_LIST = "vrs.mms.album.serialis.list";
    /**
     * 云盘
     */
    public static final String YUNPAN_VIDEO_DATA_GET = "yunpan.video.data.get";
    public static final String YUNPAN_VIDEO_STREAM = "yunpan.video.stream";

    /**
     * TV动态服务接口相关
     */
    public static final String IPTV_DYNAMIC_TAGANDALBUM_GET = "iptv.dynamic.tagandalbum.get";
    public static final String IPTV_ALBUM_DETAIL_GET = "iptv.album.detail.get";
    public static final String IPTV_ALBUM_THIRDPARTY_DETAIL_GET = "iptv.album.thirdparty.detail.get";
    public static final String IPTV_LIVE_LIST_GET = "iptv.live.list.get";
    public static final String IPTV_SEARCH_CONDITION_LIST_GET = "iptv.search.condition.list.get";
    public static final String IPTV_CHANEL_DATA_LIST_GET = "iptv.chanel.data.list.get";
    public static final String IPTV_TV_CHANEL_DATA_LIST_GET = "iptv.tv.chanel.data.list.get";
    public static final String IPTV_CHANNEL_CHARTS_CONTENT_GET = "iptv.channel.charts.content.get";
    public static final String IPTV_CHANEL_COURSE_DATA_LIST_GET = "iptv.chanel.course.data.list.get";
    public static final String IPTV_CHANNEL_CHILD_DESK_DATA_GET = "iptv.channel.child.desk.data.get";
    public static final String IPTV_CHANNEL_CONTAINER_DATA_GET = "iptv.channel.container.data.get";
    public static final String IPTV_CHANNEL_PAGE_DATA_LIST_GET = "iptv.channel.page.data.list.get";
    /**
     * CMS 版块相关
     */
    public static final String CMS_BLOCK_INDEX_1098_GET = "cms.block.index.1098.get";
    public static final String CMS_BLOCK_INDEX_1597_GET = "cms.block.index.1597.get";
    public static final String CMS_BLOCK_MINE_1190_GET = "cms.block.mine.1190.get";
    public static final String CMS_BLOCK_MINE_1629_GET = "cms.block.mine.1629.get";
    public static final String CMS_BLOCK_MINE_1598_GET = "cms.block.mine.1598.get";
    public static final String CMS_BLOCKID_STARTUP_PIC_TV_GET = "cms.blockid.startup.tv.get";
    public static final String CMS_BLOCK_DATA_ID_GET = "cms.block.data.id.get";
    public static final String CMS_BLOCK_URL = "cms.block.url";
    public static final String CMS_BLOCK_LIVE_PROJECT_URL = "cms.block.live.project.url"; // 直播专题
    public static final String CMS_BLOCK_DATA_ID_GET_LAN = "cms.block.data.id.get.lan";
    public static final String CMS_SUBJECT_DATA_GET = "cms.subject.data.get";
    public static final String CMS_STATIC_API_BLOCK_GET_BY_ID = "cms.static.api.block.get.by.id";
    public static final String CMS_STATIC_API_SUBJECT_GET_BY_ID = "cms.static.api.subject.get.by.id";
    public static final String CMS_STATIC_API_SUBJECT_PACKAGE_GET = "cms.static.api.subject.package.get";
    public static final String CMS_BLOCK_BUY_ID_GET = "cms.block.buy.id.get";
    public static final String CMS_LEVIDI_NAVIGATION_BLOCK_ID = "cms.levidi.navigation.block.id";
    public static final String CMS_LEVIDI_HOME_PAGE_ID = "cms.levidi.home.page.id";

    public static final String CMS_GOLIVEDESKTOP_PAGE_ID = "cms.golivedesktop.page.id";
    public static final String CMS_GOLIVEDESKTOP_PAGE_ID2 = "cms.golivedesktop.page.id2";
    public static final String CMS_LECOM_LEPARTER_PAGE_ID = "cms_lecom_leparter_page_id";
    public static final String CMS_LECOM_CATEGORY_PAGE_ID = "cms_lecom_category_page_id";
    public static final String CMS_LECOM_HOME_CATEGORY_ID = "cms_lecom_home_category_id";
    public static final String CMS_LECOM_HOME_ADDON_ID = "cms_lecom_home_addon_id";
    public static final String CMS_BLOCKID_LOADING_PIC_TV_GET = "cms.blockid.loading.tv.get";
    public static final String CMS_BLOCKID_TRAILER_VIDEO_TV_GET = "cms.blockid.trailer.video.tv.get";
    public static final String CMS_BLOCKID_PAGES = "cms.blockid.pages";
    public static final String VRS_ALLSTREAMS_SOURCEIDS = "vrs.allstreams.sourceids";

    /**
     * 播放相关
     */
    public static final String VIDEO_PLAY_PAUSE_DEFAULT_IMG = "video.play.pause.default.img";
    public static final String VIDEO_PLAY_IS_NEED_MOOK_NOT_VIP_LOGIN_SWITCH = "video.play.is.need.mook.not.vip.login.switch";
    public static final String VIDEO_PLAY_MOOK_NOT_VIP_LOGIN_USERNAME = "video.play.mook.not.vip.login.username";
    public static final String VIDEO_PLAY_MOOK_NOT_VIP_LOGIN_USERID = "video.play.mook.not.vip.login.userid";
    public static final String VIDEO_PLAY_SHOW_PAUSE_AD_PAGE_DEFAULT = "video.play.show.pause.ad.page.default";
    public static final String VIDEO_PLAY_FLOAT_AD_POLICY_DEFAULT = "video.play.float.ad.policy.default";
    public static final String VIDEO_PLAY_WHITE_LIST_DEFAULT_CLIENT_IP = "video.play.white.list.default.client.ip";
    /**
     * A/B Test使用的配置参数
     */
    public static final String VIDEO_EXPERIMENT_APPID = "video.experiment.appid";// appid，每个app的标识
    public static final String VIDEO_EXPERIMENT_NAME = "video.experiment.name";// 实验名称
    public static final String VIDEO_EXPERIMENT_PARAMETER_NAME = "video.experiment.parameter.name";// 实验参数
    public static final String VIDEO_EXPERIMENT_DEFAULT_VALUE_LETV = "video.experiment.default.value.letv";// 实验参数
    public static final String VIDEO_EXPERIMENT_SWITCH = "video.experiment.switch";// A/B-Test开关

    /**
     * utp升级
     */
    public static final String UTP_UPGRADE_ADDR = "utp.upgrade.addr";
    public static final String UTP_BROADCAST_CHECK_V1 = "utp.broadcast.check.v1";
    public static final String UTP_BROADCAST_CHECK_V2 = "utp.broadcast.check.v2";
    /**
     * 用户中心
     */
    // 接口功能：播放数、打分数、评论数查询
    public static final String USERCENTER_STAT_ALBUM_QUERY = "usercenter.stat.album.query";
    public static final String USERCENTER_STAT_ALBUMS_QUERY = "usercenter.stat.albums.query";
    public final static String USERCENTER_SSO_IMG = "usercenter.sso.img";
    // 接口功能：获取各个频道视频更新数
    public static final String USERCENTER_STAT_CHANNEL_QUERY = "usercenter.stat.channel.query";
    public static final String USERCENTER_PLAY_GETPLAYTAGURL = "usercenter.play.getplaytagurl";
    public static final String USERCENTER_PLAY_ADDPLAYTAGURL = "usercenter.play.addplaytagurl";
    public static final String USERCENTER_PLAY_CHKPLAYTAGURL = "usercenter.play.chkplaytagurl";
    public static final String USERCENTER_PLAY_DELPLAYTAGURL = "usercenter.play.delplaytagurl";
    public static final String USERCENTER_PLAY_DELALL_PLAYTAGURL = "usercenter.play.delall.playtagurl";
    public static final String USERCENTER_PLAY_GETPLAYLOGURL = "usercenter.play.getplaylogurl";
    public static final String USERCENTER_PLAY_DELPLAYLOGURL = "usercenter.play.delplaylogurl";
    public static final String USERCENTER_PLAY_DELALLPLAYLOGURL = "usercenter.play.delallplaylogurl";
    public static final String USERCENTER_PLAY_UPDATEPLAYLOGURL = "usercenter.play.updateplaylogurl";
    public static final String USERCENTER_API_SSO_USER_INFO_GET_BY_USERID = "usercenter.api.sso.user.info.get.by.userid";
    public final static String USERCENTER_HD_MY_LETV_COM_VIDEO_REACTION_GET = "usercenter.hd.my.letv.com.video.reaction.get";
    public final static String USERCENTER_API_SSO_LETV_COM_LOGIN_TOKEN_CHECK = "usercenter.api.sso.letv.com.login.token.check";
    // 专题收藏-------------------start
    public static final String USERCENTER_FAVORITE_GETLISTURL = "usercenter.favorite.getfavoriteurl";
    public static final String USERCENTER_FAVORITE_ADDURL = "usercenter.favorite.addfavoriteurl";
    public static final String USERCENTER_FAVORITE_CHKURL = "usercenter.favorite.chkfavoriteurl";
    public static final String USERCENTER_FAVORITE_DELURL = "usercenter.favorite.delfavoriteurl";
    public static final String USERCENTER_FAVORITE_DELBYZTIDURL = "usercenter.favorite.delfavoritebyztidurl";
    // 专题收藏-------------------end

    // 弹幕-------------------------start
    public static final String USERCENTER_DM_GETLISTURL = "usercenter.dm.getlisturl";
    public static final String USERCENTER_DM_GETCONTURL = "usercenter.dm.getcounturl";
    public static final String USERCENTER_DM_ADD_URL = "usercenter.dm.addurl";
    public final static String USERCENTER_ROOM_CHAT_ADD_URL = "usercenter.room.chat.add";
    // 弹幕-------------------------end
    /**
     * 用户中心角色信息url地址
     */
    public static final String USERCENTER_API_SSO_ROLE_INFO_ADD_BY_USERID = "usercenter.api.sso.role.info.add.by.userid";
    public static final String USERCENTER_API_SSO_ROLE_INFO_UPDATE_BY_ROLEID = "usercenter.api.sso.role.info.update.by.roleid";
    public static final String USERCENTER_API_SSO_ROLE_INFO_DELETE_BY_ROLEID = "usercenter.api.sso.role.info.delete.by.roleid";
    public static final String USERCENTER_API_SSO_ROLE_INFO_GET_BY_ROLEID = "usercenter.api.sso.role.info.get.by.roleid";
    public static final String USERCENTER_API_MY_LETV_COM_ADD_ROLE_PLAYLIST = "usercenter.api.my.letv.com.add.role.playlist";
    public static final String USERCENTER_API_MY_LETV_COM_DELETE_ALUMBS_BY_LESETID = "usercenter.api.my.letv.com.delete.alumbs.by.lesetid";
    public static final String USERCENTER_API_MY_LETV_COM_GET_ALUMBS_BY_LESETID = "usercenter.api.my.letv.com.get.alumbs.by.lesetid";
    public static final String USERCENTER_API_MY_LETV_COM_LESET_EXIST = "usercenter.api.my.letv.com.leset.exist";
    /**
     * 用户账户默认头像url
     */
    public static final String USERCENTER_VRS_USER_ACCOUNT_DEFAULT_PIC = "usercenter.vrs.user.account.default.pic";
    public static final String USERCENTER_API_TOUCH__INFO_GET_BY_USERID = "usercenter.api.touch.info.get.by.uid";
    public static final String GET_VIPCENTER_DATA = "vipcenter.activity.get";
    public static final String GET_BOSS_ACTIVITY_DATA = "boss.price.zhifu.activity.data.get";
    /**
     * 检查手机号是否存在（是否是注册用户）
     */
    public static final String USERCENTER_API_SSO_CHECK_MOBILE_EXISTS = "usercenter.api.sso.check.mobile.exists";

    /**
     * 推荐相关
     */
    public static final String RECOMMEND_WEB_URL = "recommend.web.url";

    public static final String LEVIDI_REC_BASE_URL = ApplicationUtils.get("levidi.rec.base.url");

    public static final String LEVIDI_RECOMMENDATION_BASEURL = "levidi_recommendation_baseurl";

    /**
     * 盒端
     */
    public static final String BOX_USER_USERNAME_QUERY = "box.user.username.query";

    /**
     * 直播相关
     */
    public static final String LIVE_CHANNEL_LIST_GET = "live.channel.list.get";
    public static final String LIVE_PROGRAM_LIST_GET_1 = "live.program.list.get.1";
    public static final String LIVE_PROGRAM_LIST_GET_2 = "live.program.list.get.2";
    public static final String LIVE_ROOM_CHAT_HISTORY = "live.room.chat.history";
    public static final String LIVE_ROOM_LIST_GET_2 = "live.room.list.get.2";
    public static final String LIVE_ROOM_STREAM_LIST = "live.room.stream.list";

    /**
     * 直播大厅多主键查询接口，查询若干条直播信息
     */
    public static final String LIVE_ROOM_MULTI_LIVE_PROGRAM_GET = "live.room.multi.live.program.get";

    /**
     * 第三方合作伙伴
     */
    public static final String PARTNER_SOUND_INK = "partner.sound.ink";

    public static final String IPTV_VIDEO_MACLIST = "iptv.video.maclist";

    /**
     * 配置参数
     */
    public static final String IPTV_STATIC_SERVER_ADDR_PARAM = "iptv.static.server.addr.param";
    public static final String IPTV_STATIC_FILE_STORE_HOME_PARAM = "iptv.static.file.store.home.param";
    public static final String IPTV_VRS_MMS_QUEUE_EXCHANGE_NAME_PARAM = "iptv.vrs.mms.queue.exchange.name.param";
    public static final String IPTV_VRS_MMS_QUEUE_NAME_PARAM = "iptv.vrs.mms.queue.name.param";
    public static final String IPTV_VRS_MMS_QUEUE_HOST_PARAM = "iptv.vrs.mms.queue.host.param";
    public static final String IPTV_VRS_MMS_QUEUE_PORT_PARAM = "iptv.vrs.mms.queue.port.param";
    public static final String IPTV_VRS_MMS_QUEUE_VHOST_PARAM = "iptv.vrs.mms.queue.vhost.param";
    public static final String IPTV_VRS_MMS_QUEUE_USERNAME_PARAM = "iptv.vrs.mms.queue.username.param";
    public static final String IPTV_VRS_MMS_QUEUE_PASSWORD_PARAM = "iptv.vrs.mms.queue.password.param";
    public static final String IPTV_BROADCAST_SWITCH_PARAM = "iptv.broadcast.switch.param";
    public static final String IPTV_BROADCAST_SWITCH_WASU_PARAM = "iptv.broadcast.switch.wasu.param";
    public static final String IPTV_BURROW_SWITCH_PARAM = "iptv.burrow.switch.param";
    public static final String IPTV_FILE_GEN_SWITCH_PARAM = "iptv.file.gen.switch.param";
    public static final String IPTV_TV_COPYRIGHT_PARAM = "iptv.tv.copyright.param";
    public static final String IPTV_CACHE_SELECT_PARAM = "iptv.cache.select.param";
    public static final String IPTV_LIVE_MS_TV_QUEUE_NAME_PARAM = "iptv.live_ms.tv.queue.name.param";
    public static final String IPTV_ALL_SERVER_SERVICE = "iptv.all.server.service";
    public static final String IPTV_TIMER_ROOT_PATH = "iptv.timer.root.path";
    public static final String IPTV_CBASE_RMI_SERVER_HOST = "iptv.cbase.rmi.server.host"; // Cbase-rmi服务器host
    public static final String IPTV_CBASE_RMI_SERVER_PORT = "iptv.cbase.rmi.server.port"; // Cbase-rmi服务器port
    public static final String IPTV_WCODE_DEFAULT_VALUE = "iptv.wcode.default.value"; // 地域默认化参数
    public static final String IPTV_SALESAREA_DEFAULT_VALUE = "iptv.salesarea.default.value"; // 销售地默认化参数
    public static final String IPTV_LANDCODE_DEFAULT_VALUE = "iptv.langcode.default.value"; // 销售地默认化参数
    public static final String IPTV_OBTAIN_DATA_FROM_DB_SWITCH = "iptv.obtain.data.from.db.switch"; // 地域默认化参数
    public static final String IPTV_LIVE_CHAT_ROOM_SWITCH = "iptv.live.chat.room.switch"; // 聊天室弹幕开关
    public static final String IPTV_STATIC_BROADCAST_ID_PARAM = "iptv.static.broadcastId.param";
    public static final String IPTV_BURROW_MODE_START_TV = "iptv.burrow.mode.start.tv";
    public static final String IPTV_CIBN_FTP_SERVER_ADDRESS = "iptv.cibn.ftp.server.address";
    public static final String IPTV_CIBN_FTP_SERVER_USER = "iptv.cibn.ftp.server.user";
    public static final String IPTV_CIBN_FTP_SERVER_PASSWORD = "iptv.cibn.ftp.server.password";
    public static final String IPTV_CIBN_WS_URL = "iptv.cibn.ws.url";
    public static final String IPTV_TERMINAL_UPGRADE_SWITCH = "iptv.terminal.upgrade.switch";
    public static final String IPTV_CHILD_PLAYLIST_4VOIDE_ID_PARAM = "iptv.child.playlist.4voice.param";
    public static final String IPTV_PAGEDATALIST_SWITCH_PARAM = "iptv.pagedatalist.switch.param";
    public static final String IPTV_VIP_QUEUE_NAME_PARAM = "iptv.vip.queue.name.param";

    public static final String IPTV_PLAY_NOCIBN_SWITCH = "iptv.play.nocibn.switch";
    public static final String IPTV_PLAY_STREAM_FILTER_SWITCH = "iptv.play.stream.filter.switch";
    public static final String IPTV_APP_3RD_URI_BLACKLIST = "iptv.app.3rd.uri.blacklist";
    public static final String IPTV_USER_LEPAY_ISTEST = "iptv.user.lepay.istest";
    public static final String IPTV_PLAY_ANTIREPORT_SWITCH = "iptv.play.antireport.switch";
    public static final String IPTV_TERMINAL_CONFIG_PROFILE = "iptv.terminal.config.profile";

    /**
     * 直播展示时间提前，提前时间从缓存中读取，读取的key
     */
    public final static String CHANNEL_PRELIVE_LIVE_ADVANCE_TIME_KEY = "CHANNEL_PRELIVE_LIVE_ADVANCE_TIME_KEY";// 正式直播提前展示时间缓存key值
    public final static String CHANNEL_PRELIVE_LIVE_ADVANCE_TIME_VALUE = "channel.prelive.live.advance.time.value";// 正式直播提前展示时间配置
    public final static String CHANNEL_PRELIVE_AD_ADVANCE_TIME_KEY = "CHANNEL_PRELIVE_AD_ADVANCE_TIME_KEY";// 正式直播提前展示时间缓存的key值
    public final static String CHANNEL_PRELIVE_AD_ADVANCE_TIME_VALUE = "channel.prelive.ad.advance.time.value";// 正式直播开始前请求广告时间配置
    public final static String CHANNEL_PRELIVE_AD_PLAY_TIME_KEY = "CHANNEL_PRELIVE_AD_PLAY_TIME_KEY";// 广告播放时间缓存的key值
    public final static String CHANNEL_PRELIVE_AD_PLAY_TIME_VALUE = "channel.prelive.ad.play.time.value";// 广告播放时间
    public final static String CHANNEL_PRELIVE_PLAY_AD_KEY = "CHANNEL_PRELIVE_PLAY_AD_KEY";// 单独请求广告缓存的key值
    public final static String CHANNEL_PRELIVE_PLAY_AD_VALUE = "channel.prelive.play.ad.value";// 单独请求广告
    public final static String CHANNEL_PRELIVE_LIVE_FORECAST_KEY = "CHANNEL_PRELIVE_LIVE_FORECAST_KEY";// 走红毯直播需要刷新时间点缓存的key值
    public final static String CHANNEL_PRELIVE_LIVE_FORECAST_VALUE = "channel.prelive.live.forecast.value";// 走红毯直播需要刷新时间点的值

    public final static String CHANNEL_PRELIVE_ORDER_3D1_UNLOGIN_URL = "channel.prelive.order.3d1.unlogin.url";// 有3D电视---非年费会员---预约
    public final static String CHANNEL_PRELIVE_ORDER_3D1_YEARVIP_URL = "channel.prelive.order.3d1.yearvip.url";// 有3D电视---年费会员---预约
    public final static String CHANNEL_PRELIVE_ORDER_3D1_NOTYEARVIP_URL = "channel.prelive.order.3d1.notyearvip.url";// 有3D电视---非年费会员---预约
    public final static String CHANNEL_PRELIVE_ORDER_3D0_YEARVIP_URL = "channel.prelive.order.3d0.yearvip.url";// 无3D电视---预约
    public final static String CHANNEL_PRELIVE_ORDER_3D0_NOTYEARVIP_URL = "channel.prelive.order.3d0.notyearvip.url";// 无3D电视---预约
    public final static String CHANNEL_PRELIVE_BUY_3D1_YEARVIP_URL = "channel.prelive.buy.3d1.yearvip.url";// 有3D电视---年费会员---3D豪华套餐
    public final static String CHANNEL_PRELIVE_BUY_3D0_URL = "channel.prelive.buy.3d0.url";// 无3D电视---3D豪华套餐
    public final static String CHANNEL_PRELIVE_PROFILE_URL = "iptv.conf.channel.profile";// 静态文件路径
    public final static String CHANNEL_PRELIVE_LIVE_DEFAULT_PRICE = "channel.prelive.live.default.price";// 取价格失败取默认价格
    public final static String CHANNEL_BOLCK_TVPIC_PREFIX = "channel.block.tvpic.";// 取价格失败取默认价格
    public final static String CHANNEL_PAGEID_CHILD = "channel.pageid.child";// 儿童桌面page列表
    public final static String CHANNEL_WALL_PIC_BLOCKID = "channel.wall.pic.blockid";// tv频道墙前三个图片存储到板块
    public final static String CHANNEL_PAGEID_STATIC_URL = "channel.match.pageid.static.url";// pageid与channelid映射文件

    /**
     * 超前影院配置文件中的key值
     */
    public final static String CHANNEL_PRELIVE_DETAIL_PLAY_NUMBER = "channel_prelive_detail_play_number";// 详情页观影人数
    public final static String CHANNEL_PRELIVE_WATER_MARK_SHOW_TIME = "channel_prelive_water_mark_show_time";// 水印展示时间
    public final static String CHANNEL_PRELIVE_WATER_MARK_HIDDEN_TIME = "channel_prelive_water_mark_hidden_time";// 水印隐藏时间
    public final static String CHANNEL_PRELIVE_PLAYEND_PIC_URL = "channel_prelive_playend_pic_url";// 结束页的图片URL
    public final static String CHANNEL_PRELIVE_PLAYEND_PLAY_NUMBER = "channel_prelive_playend_play_number";// 结束页观影人数
    public final static String CHANNEL_PRELIVE_PLAYEND_TICKET_TOTAL = "channel_prelive_playend_ticket_total";// 结束页总票数

    /**
     * 会员模块的配置静态文件
     */
    public final static String VIP_PROFILE_URL = "iptv.conf.vip.profile";
    /**
     * 收银台模块的配置静态文件
     */
    public final static String CASHIER_PROFILE_URL = "iptv.cashier.vip.profile";

    /**
     * 直播大厅过滤直播数据的配置文件url，目前走静态化文件
     */
    public final static String LIVE_LIVEROOM_FILTER_LIVES_CONFIG_FILE_URL = "live.liveroom.filter.lives.config.file.url";

    /**
     * 游戏中心对接接口
     */
    public final static String PLAY_GAME_PID_LIST = "game.play.game.pid.list";

    public final static String GET_PLAY_GAME_BY_PID = "game.get.play.game.by.pid";

    public static final String GAME_CONTER_ACTION = "game.conter.action.param";
    public static final String GAME_CONTER_PACKAGENAME = "game.conter.packagename.param";

    /**
     * GOLIVE相关
     */
    public static final String IPTV_GOLIVE_MOVIELIST_DESK_DATA_GET = "iptv.golive.movielist.desk.data.get";

    public static final String URM_TOUCH_POSITION_TOP = "urm.touch.position.top";
    public static final String URM_TOUCH_POSITION_BOTTOM = "urm.touch.position.bottom";
    public static final String URM_TOUCH_POSITION_POPUP = "urm.touch.position.popup";
    public static final String URM_TOUCH_POSITION_MINE1 = "urm.touch.position.mine1";
    public static final String URM_TOUCH_POSITION_MINE2 = "urm.touch.position.mine2";
    public static final String URM_TOUCH_POSITION_MINE3 = "urm.touch.position.mine3";
    public static final String URM_TOUCH_POSITION_MINE4 = "urm.touch.position.mine4";
    public static final String URM_TOUCH_POSITION_PLAY_SINGLE = "urm.touch.position.play.single";
    public static final String URM_TOUCH_POSITION_PLAY_STREAM = "urm.touch.position.play.stream";
    public static final String URM_TOUCH_POSITION_PLAY_NO_AD = "urm.touch.position.play.noad";
    public static final String URM_TOUCH_POSITION_PLAY_SLOW = "urm.touch.position.play.slow";
    public static final String URM_TOUCH_POSITION_PLAY_ONLYVIP = "urm.touch.position.play.onlyvip";

    public static final String IPTV_GOLIVE_SERIES_LIST = "iptv.golive.series.list";

    // 会员桌面
    public static final String URM_TOUCH_MEMBER_MOVIE_OPERATION = "urm.touch.member.movie.operation";
    public static final String URM_TOUCH_MEMBER_SPORT_OPERATION = "urm.touch.member.sport.operation";

    /*--------------------------------------------会员桌面-活动触达位start----------------------------------*/
    public static final String URM_TOUCH_MEMBER_MOVIE_FOCUS_1 = "urm.touch.member.movie.focus.1";
    public static final String URM_TOUCH_MEMBER_MOVIE_FOCUS_2 = "urm.touch.member.movie.focus.2";
    public static final String URM_TOUCH_MEMBER_MOVIE_FOCUS_3 = "urm.touch.member.movie.focus.3";
    public static final String URM_TOUCH_MEMBER_MOVIE_FOCUS_4 = "urm.touch.member.movie.focus.4";
    public static final String URM_TOUCH_MEMBER_MOVIE_FOCUS_5 = "urm.touch.member.movie.focus.5";

    public static final String URM_TOUCH_MEMBER_SPORT_FOCUS_1 = "urm.touch.member.sport.focus.1";
    public static final String URM_TOUCH_MEMBER_SPORT_FOCUS_2 = "urm.touch.member.sport.focus.2";
    public static final String URM_TOUCH_MEMBER_SPORT_FOCUS_3 = "urm.touch.member.sport.focus.3";
    public static final String URM_TOUCH_MEMBER_SPORT_FOCUS_4 = "urm.touch.member.sport.focus.4";
    public static final String URM_TOUCH_MEMBER_SPORT_FOCUS_5 = "urm.touch.member.sport.focus.5";

    public static final String URM_TOUCH_MEMBER_MOVIE_RIGHTS_1 = "urm.touch.member.movie.rights.1";
    public static final String URM_TOUCH_MEMBER_MOVIE_RIGHTS_2 = "urm.touch.member.movie.rights.2";
    public static final String URM_TOUCH_MEMBER_MOVIE_RIGHTS_3 = "urm.touch.member.movie.rights.3";
    public static final String URM_TOUCH_MEMBER_MOVIE_RIGHTS_4 = "urm.touch.member.movie.rights.4";
    public static final String URM_TOUCH_MEMBER_MOVIE_RIGHTS_5 = "urm.touch.member.movie.rights.5";
    public static final String URM_TOUCH_MEMBER_MOVIE_RIGHTS_6 = "urm.touch.member.movie.rights.6";

    public static final String URM_TOUCH_MEMBER_SPORT_RIGHTS_1 = "urm.touch.member.sport.rights.1";
    public static final String URM_TOUCH_MEMBER_SPORT_RIGHTS_2 = "urm.touch.member.sport.rights.2";
    public static final String URM_TOUCH_MEMBER_SPORT_RIGHTS_3 = "urm.touch.member.sport.rights.3";
    public static final String URM_TOUCH_MEMBER_SPORT_RIGHTS_4 = "urm.touch.member.sport.rights.4";
    public static final String URM_TOUCH_MEMBER_SPORT_RIGHTS_5 = "urm.touch.member.sport.rights.5";
    public static final String URM_TOUCH_MEMBER_SPORT_RIGHTS_6 = "urm.touch.member.sport.rights.6";

    public static final String URM_TOUCH_MEMBER_MOVIE_ACTIVITY_1 = "urm.touch.member.movie.activity.1";
    public static final String URM_TOUCH_MEMBER_MOVIE_ACTIVITY_2 = "urm.touch.member.movie.activity.2";
    public static final String URM_TOUCH_MEMBER_MOVIE_ACTIVITY_3 = "urm.touch.member.movie.activity.3";
    public static final String URM_TOUCH_MEMBER_MOVIE_ACTIVITY_4 = "urm.touch.member.movie.activity.4";
    public static final String URM_TOUCH_MEMBER_MOVIE_ACTIVITY_5 = "urm.touch.member.movie.activity.5";
    public static final String URM_TOUCH_MEMBER_MOVIE_ACTIVITY_6 = "urm.touch.member.movie.activity.6";

    public static final String URM_TOUCH_MEMBER_SPORT_ACTIVITY_1 = "urm.touch.member.sport.activity.1";
    public static final String URM_TOUCH_MEMBER_SPORT_ACTIVITY_2 = "urm.touch.member.sport.activity.2";
    public static final String URM_TOUCH_MEMBER_SPORT_ACTIVITY_3 = "urm.touch.member.sport.activity.3";
    public static final String URM_TOUCH_MEMBER_SPORT_ACTIVITY_4 = "urm.touch.member.sport.activity.4";
    public static final String URM_TOUCH_MEMBER_SPORT_ACTIVITY_5 = "urm.touch.member.sport.activity.5";
    public static final String URM_TOUCH_MEMBER_SPORT_ACTIVITY_6 = "urm.touch.member.sport.activity.6";
    /*--------------------------------------------会员桌面-活动触达位end------------------------------------*/
    public static final String IPTV_HTTPCLIENT_INTERFACE_RESPONSE_TIME = "iptv.httpclient.interface.response.time";
    public static final String INTERFACE_RESPONSE_TIME = ApplicationUtils.get(IPTV_HTTPCLIENT_INTERFACE_RESPONSE_TIME);

    /**
     * 专辑、视频缓存相关配置
     */
    public static final String GLOBAL_VIDEO_CACHE_TIMEOUT = "global.video.cacheTimeout";
    public static final String GLOBAL_VIDEO_BLOCKED_CACHE_TIMEOUT = "global.video.blockedCacheTimeout";
    public static final String GLOBAL_VIDEO_ACCESS = "global.video.access";
    public static final String GLOBAL_VIDEO_USE_LOCAL_CACHE = "global.video.useLocalCache";
    public static final String GLOBAL_VIDEO_VRS_FETCH_DATA = "global.video.vrs.fetchData";
    public static final String GLOBAL_VIDEO_VRS_SITE = "global.video.vrs.site";

    /**
     * 审核参数配置
     */
    public static final String AUDIT_VRS_MMS_QUEUE_NAME_PARAM = "audit.vrs.mms.queue.name.param";
    public static final String AUDIT_QUEUE_CONSUMER_ID = "audit.queue.consumer.id";

    /**
     * CDN country list config key
     */
    public static final String CDN_COUNTRY_LIST_KEY = "cdn.country.list.key";

    /**
     * search 搜索参数配置
     */
    public static final String SEARCH_ADDON_SEARCH_CONTENT_SORT = "search.addon.search.content.sort"; // addon排序
    /**
     * 报警接口
     */
    public static final String LETV_ALARM_EMAIL_API = "letv.alarm.email.api";
    public static final String LETV_ALARM_SMS_API = "letv.alarm.sms.api";
    public static final String LETV_ALARM_VOICE_API = "letv.alarm.voice.api";

    public final static String VIP_OPENECO_VENDER = "vip.openeco.vender";// 商户编码

    /**
     * 从配置文件中获取活动位标识
     */
    public static final String ACTIVITY_POSID_TOP = "activity.posid.top";
    public static final String ACTIVITY_POSID_BOTTOM = "activity.posid.bottom";
    public static final String ACTIVITY_POSID_POPUP = "activity.posid.popup";

    public static final String ACTIVITY_POSID_MINE1 = "activity.posid.mine1";
    public static final String ACTIVITY_POSID_MINE2 = "activity.posid.mine2";
    public static final String ACTIVITY_POSID_MINE3 = "activity.posid.mine3";
    public static final String ACTIVITY_POSID_MINE4 = "activity.posid.mine4";
    public static final String ACTIVITY_POSID_MINE5 = "activity.posid.mine5";

    public static final String ACTIVITY_POSID_HOME1 = "activity.posid.home1";
    public static final String ACTIVITY_POSID_HOME2 = "activity.posid.home2";

    public static final String ACTIVITY_POSID_PLAY_PLAY_QUIT = "activity.posid.play.quit";
    public static final String ACTIVITY_POSID_PLAY_TOP_TRY = "activity.posid.play.top.try";
    public static final String ACTIVITY_POSID_PLAY_PAUSE = "activity.posid.play.pause";
    public static final String ACTIVITY_POSID_PLAY_JUMP_AD = "activity.posid.play.jump.ad";
    public static final String ACTIVITY_POSID_PLAY_HIGH_STREAM = "activity.posid.play.high.stream";

    /**
     * 收银台接口SDK
     */
    public static final String CASHIER_SDK_APPID = "cashier.sdk.appid";
    public static final String CASHIER_SDK_APPSECRET = "cashier.sdk.appsecret";
    public static final String AGREEMENT_CMS_BLOCK_ID = "cashier.agreement.cms.blockid";
    public static final String PRO_AGREEMENT_ID = "cashier.pro.agreement.id";
    public static final String RENEWAL_PRO_AGREEMENT_ID = "cashier.renewal.pro.agreement.id";

    /**
     * 北美版收银台url
     */
    public static final String US_CASHIER_URL = "us.cashier.url";

    /**
     * 瀑布流专题的模版类型
     */
    public static final String WF_SUBJECT_CMS_TYPE_BANNER_1_KEY = "wf.type.banner.1";// banner1
    public static final String WF_SUBJECT_CMS_TYPE_BANNER_2_KEY = "wf.type.banner.2";// banner2
    public static final String WF_SUBJECT_CMS_TYPE_BODY_1_KEY = "wf.type.body.1";// body1
    public static final String WF_SUBJECT_CMS_TYPE_BODY_2_KEY = "wf.type.body.2";// body2
    public static final String WF_SUBJECT_CMS_TYPE_BODY_3_KEY = "wf.type.body.3";// body3
    public static final String WF_SUBJECT_CMS_TYPE_BODY_4_KEY = "wf.type.body.4";// body4
    public static final String WF_SUBJECT_CMS_TYPE_BODY_5_KEY = "wf.type.body.5";// body5
    public static final String WF_SUBJECT_CMS_TYPE_BODY_6_KEY = "wf.type.body.6";// body6
    public static final String WF_SUBJECT_CMS_TYPE_BODY_7_KEY = "wf.type.body.7";// body7

    /**
     * 定时抓取观星打底
     */
    public static final String GET_DEFAULT_DATA_MAC = "get.default.data.mac";// mac
    public static final String GET_DEFAULT_DATA_UID = "get.default.data.uid";// uid
    public static final String GET_DEFAULT_DATA_URL = "get.default.data.url";// url
    public static final String GET_CASHIER_DATA_URL = "get.cashier.data.url";// 收银台ID

    public final static String STATIC_DEFAULT_DATA_URL = "static.iptv.vip.promotion";// 观星打底
    public final static String GET_STATIC_DEFAULT_DATA_URL = ApplicationUtils
            .get(ApplicationConstants.STATIC_DEFAULT_DATA_URL);

    /**
     * 提供给测试时模拟观星出错，正式线上不使用
     */
    public static final String GUANXING_DATA_ERROR_MAC = "get.guanxing.data.error.mac";// 制定mac吐出模拟观星异常
    public static final String GET_GUANXING_DATA_ERROR_MAC = ApplicationUtils.get(
            ApplicationConstants.GUANXING_DATA_ERROR_MAC, "");
    public static final String GUANXING_DATA_ERROR_URL = "guanxing.data.error.url";// 模拟观星异常访问的url
    public final static String GET_GUANXING_DATA_ERROR_URL = ApplicationUtils.get(
            ApplicationConstants.GUANXING_DATA_ERROR_URL, "");

    /**
     * 行为日志接口集
     */
    public static final String ACT_REPORT_SET_BASE = "act.report.baseurl";
    public static final String ACT_REPORT_ANTI_SET_URI = "act.report.anti.set.uri";
    public static final String ACT_REPORT_ANTI_OPT_URI = "act.report.anti.opt.uri";
}