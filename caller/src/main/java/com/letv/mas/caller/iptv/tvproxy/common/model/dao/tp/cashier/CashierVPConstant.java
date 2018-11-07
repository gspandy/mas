package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier;

import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;

/**
 * Created by wangli on 4/24/17.
 * 收银台的依赖的服务的地址
 */
public class CashierVPConstant {

    public static final String GET_PV_ACT_ENGINE() {
        return ApplicationConstants.PAY_API_VL_BASE_HOST + "/act/p/engine/act/v1.0/";
    }

    // 支付成功信息
    public static final String GET_PV_CASHIER_CONSUMER() {
        return ApplicationConstants.PAY_API_VL_BASE_HOST + "/cashier/consumer/v1/";
    }

    public static final String PV_USER_VIP_INFO_URL = ApplicationConstants.PAY_API_VL_BASE_HOST
            + "/act/user-info/act/user/membership/info?term=%s&deviceKey=%s&mac=%s&vipcsrf=xxx";
    // 开通成功信息
    public static final String PV_VIP_STATUS_URL = ApplicationConstants.PAY_API_VL_BASE_HOST
            + "/act/p/engine/act/v1.0/cashier/membership/status?orderNo=%s&vipcsrf=xxx";
    public static final String PV_PURCHASE_INFO_URL = ApplicationConstants.PAY_API_VL_BASE_HOST
            + "/cashier/consumer/v1/order/purchase?mode=%s&order_no=%s&bind_id=%s&term=%s&vipcsrf=xxx";
    public static final String PV_PAY_ORDER_URL = ApplicationConstants.PAY_API_BOSS_PAY_BASE_HOST + "/directpay/quick";
    public static final String PAY_ORDER_PAP_MODE = "pap";
    public static final String LEPAY_BUSINESS_ID = "83";
    public static final String CASHIER_SDK_APPID = ApplicationUtils
            .get(com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants.CASHIER_SDK_APPID);
    public static final String CASHIER_SDK_APPSECRET = ApplicationUtils
            .get(com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants.CASHIER_SDK_APPSECRET);

    public static final String PAY_ORDER_SUCCESS = "success";
    public static final String PAY_ORDER_UNPAID = "unpaid";
    public static final String PAY_ORDER_FAILD = "failed";
    public static final String PAY_ORDER_INIT = "init";

    // http://api-vip.le.com/act/user-info/act/user/membership/info?term={term}&deviceKey={deviceKey}&mac={mac}中
    // term : 终端信息 (TV : 141007; Pad : 141005; Phone : 141003; PcClient :
    // 141002; Web : 141001; MSite : 141006;
    public static final String FETCH_USER_VIP_INFO_TERM = "141007";

    // 套餐协议内容CMS块ID
    public static final String AGREEMENT_CMS_BLOCK_ID = ApplicationUtils
            .get(com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants.AGREEMENT_CMS_BLOCK_ID);
    // 超级影视会员连续包月服务CMS里的协议ID
    public static final String PRO_AGREEMENT_ID = ApplicationUtils
            .get(com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants.PRO_AGREEMENT_ID);
    // 超级影视会员服务CMS里的协议ID
    public static final String RENEWAL_PRO_AGREEMENT_ID = ApplicationUtils
            .get(com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants.RENEWAL_PRO_AGREEMENT_ID);

    public static final String GET_IL_MMS_INNER() {
        return ApplicationConstants.PAY_API_IL_BASE_HOST + "/mms/inner/";
    }

    public static final String GET_SL() {
        return ApplicationConstants.PAY_API_SL_BASE_HOST + "/";
    }

    public static final String GET_SSO() {
        return ApplicationConstants.PAY_API_SSO_BASE_HOST + "/";
    }

    public static final String GET_TG() {
        return ApplicationConstants.PAY_API_TG_BASE_HOST + "/";
    }
}
