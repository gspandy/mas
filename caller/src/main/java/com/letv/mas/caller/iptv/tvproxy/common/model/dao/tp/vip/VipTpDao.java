package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.bean.ExtendParam;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.SessionCache;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class VipTpDao extends BaseTpDao {

    @Autowired(required = false)
    private SessionCache sessionCache;

    private final static Logger LOG = LoggerFactory.getLogger(VipTpDao.class);

    /**
     * get user recharge record
     * @param day
     * @param commonParam
     * @return
     */
    public RechargeRecordTpResponse getRechargeRecord(Integer day, CommonParam commonParam) {
        String logPrefix = "getRechargeRecord_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + day;
        RechargeRecordTpResponse recTp = null;

        StringBuilder url = new StringBuilder(VipTpConstant.VIP_GET_RECHARGE_RECORD_URL);
        url.append(commonParam.getUserId());
        url.append("?origin=tv");// tv terminal recharge record
        if (day != null && day > 0) {// if day is not null then append it.
            url.append("&days=").append(day);
        }
        String auth = MD5Util.md5(VipTpConstant.BOSS_API_ZHIFU_SIGN_KEY + commonParam.getUserId());
        if (auth != null) {
            url.append("&auth=").append(auth);
        }
        try {
            // http://api.zhifu.letv.com/queryrecord/{userid}?origin={origin}&days={days}&auth={auth}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                // the third party return result need to decode
                result = URLDecoder.decode(result, "UTF-8");
                recTp = this.objectMapper.readValue(result, RechargeRecordTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return recTp;
    }

    /**
     * get user consumption record
     * @param status
     * @param day
     * @param page
     * @param pageSize
     * @param commonParam
     * @return
     */
    public ConsumptionRecordTpResponse getConsumptionRecord(Integer status, Integer day, Integer page,
                                                            Integer pageSize, CommonParam commonParam) {
        ConsumptionRecordTpResponse recTp = null;
        String logPrefix = "getConsumptionRecord_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                + commonParam.getDeviceKey();

        try {
            StringBuilder url = new StringBuilder(VipTpConstant.VIP_GET_CONSUMPTION_RECORD_URL);
            url.append("&status=").append(status);// query record status
            url.append("&day=").append(day);// within days
            url.append("&userid=").append(commonParam.getUserId());
            url.append("&page=").append(page);
            url.append("&pagesize=").append(pageSize);
            // TV terminal end is 9
            url.append("&end=").append(VipTpConstant.ORDER_FROM_TV2);
            url.append("&mac=").append(commonParam.getMac());
            url.append("&devicekey=").append(commonParam.getDeviceKey());
            long timestamp = System.currentTimeMillis();
            url.append("&timestamp=").append(timestamp);
            url.append("&busnissId=").append(VipTpConstant.BOSS_YUANXIAN_SAFE_BUSSINESS_ID);
            url.append("&sign=").append(getBossSafeSign(commonParam.getUserId(), timestamp));
            // http://yuanxian.letv.com/letv/sale.ldo?type=saleRecord&status={status}&day={day}&userid={userid}&page={page}&pagesize={pagesize}&end={end}&mac={mac}&devicekey={deviceKey}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                recTp = this.objectMapper.readValue(result, ConsumptionRecordTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return recTp;
    }

    /**
     * get user consumption record
     * @param status
     * @param day
     * @param page
     * @param pageSize
     * @param commonParam
     * @return
     */
    public OrderStatusTpResponseV2 getConsumptionRecordV2(Integer status, Integer day, Integer page, Integer pageSize,
                                                          CommonParam commonParam) {
        OrderStatusTpResponseV2 orderTp = null;
        String logPrefix = "checkOrderStatus_" + commonParam.getMac() + "_" + commonParam.getUserId();
        try {
            String type = "orderList";
            // int rows = 3;
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(VipTpConstant.VIP_CHECK_ORDER_STATUS_URL_V2);
            StringBuilder params = new StringBuilder();
            params.append("type=").append(type);
            params.append("&userId=").append(commonParam.getUserId());
            VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
            if (country != null) {
                params.append("&country=").append(country.getCode());
            }
            params.append("&rows=").append(pageSize);
            params.append("&page=").append(page);
            params.append("&status=").append(status);
            params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
            String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
            params.append("&sign=").append(sign);
            subUrl.append(params);
            // http://yuanxian.letv.com/letv/vip2.ldo?type=orderList&userId=123123123&status=2&productType=1&page=1&rows=5
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                orderTp = this.objectMapper.readValue(result, OrderStatusTpResponseV2.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return orderTp;
    }

    public String CalculateOrderEndTime(OrderTXData.UserPackageTX order) {
        Date successTime = order.getSuccessTime();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (successTime == null) {
            successTime = new Date();
            return formater.format(successTime);
        }
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(successTime);
        if (order.getVendorProductDurationType().equals(1)) {
            rightNow.add(Calendar.YEAR, order.getVendorProductDuration());
        } else if (order.getVendorProductDurationType().equals(2)) {
            rightNow.add(Calendar.MONTH, order.getVendorProductDuration());
        } else {
            rightNow.add(Calendar.DAY_OF_YEAR, order.getVendorProductDuration());
        }
        Date orderEndTime = rightNow.getTime();
        return formater.format(orderEndTime);
    }

    public OrderStatusTpResponseV3 OrderTxToOrderTp(OrderStatusTpResponseTX orderTx) {
        // 乐购接口和代理端接口statusz状态码对应关系
        Map<Integer, Integer> statusCode = new HashMap<Integer, Integer>();
        statusCode.put(0, 0);
        statusCode.put(1, 1);
        statusCode.put(2, 4);
        statusCode.put(3, 2);
        OrderStatusTpResponseV3 orderTp = new OrderStatusTpResponseV3();
        List<OrderStatusTpResponseV3.UserPackageV3> consumerOrders = new ArrayList<>();
        orderTp.setSuccess(orderTx.getData().isThisTimeDone());
        for (OrderTXData.UserPackageTX otx : orderTx.getData().getDatas()) {
            OrderStatusTpResponseV3.UserPackageV3 order = new OrderStatusTpResponseV3.UserPackageV3();
            order.setVip_order_id(otx.getOrderNo());
            order.setOrder_name(otx.getOrderName());
            Object successTime = otx.getSuccessTime();
            if (successTime == null) {
                successTime = new Date();
            }
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            order.setOrder_start_time(formater.format(successTime));
            order.setOrder_end_time(this.CalculateOrderEndTime(otx));
            order.setPay_time(formater.format(successTime));
            order.setStatus("0");
            order.setPay_status(Integer.toString(statusCode.get(otx.getStatus())));
            order.setAct_type("0");
            consumerOrders.add(order);
        }
        orderTp.setBody(consumerOrders);
        return orderTp;
    }

    /**
     * get user consumption record
     * @param status
     * @param day
     * @param page
     * @param pageSize
     * @param commonParam
     * @return
     */
    public OrderStatusTpResponseV3 getConsumptionRecordV3(Integer status, Integer day, Integer page, Integer pageSize,
            String orderType, CommonParam commonParam) {
        OrderStatusTpResponseV3 orderTp = null;
        String logPrefix = "checkOrderStatus_" + commonParam.getMac() + "_" + commonParam.getUserId();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String endTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime());
        String startTime;
        if (day > 0) {
            calendar.add(Calendar.DATE, day - (day * 2));
            startTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime());
        } else {// 全部订单,从2013年1月1日开始计算
            startTime = VipTpConstant.ORDER_ORIGINALLY_GENERATED_TIME;
        }
        try {
            StringBuilder subUrl = new StringBuilder();
            if (!orderType.equals("TEN_FAM")) {
                subUrl.append(VipTpConstant.VIP_CHECK_ORDER_STATUS_URL_V3);
                // subUrl.append("http://api.vip.le.com/act/p/engine/act/v1.0/orderConsumer/list?");
                StringBuilder params = new StringBuilder();
                params.append("pagesize=").append(pageSize);
                params.append("&vipcsrf=").append("vipcsrf");
                params.append("&vip_type=").append(orderType);
                params.append("&start_time=").append(URLEncoder.encode(startTime, "utf-8"));
                params.append("&end_time=").append(URLEncoder.encode(endTime, "utf-8"));
                subUrl.append(params);
                String[] urls = { subUrl.toString() };
                // http://api.vip.le.com/act/p/engine/act/v1.0/orderConsumer/list?
                // start_time=2017-04-01 00:00:00&end_time=2017-05-04
                // 12:00:00&vipcsrf=vipcsrf
                Map<String, String> headers = new HashMap<>();
                StringBuilder Cookie = new StringBuilder();
                Cookie.append("vipcsrf=vipcsrf;");
                Cookie.append("ssouid=").append(commonParam.getUserId()).append(";");
                Cookie.append("sso_tk=").append(commonParam.getToken());
                // headers.put("Cookie",
                // "vipcsrf=vipcsrf;ssouid=147371639;sso_tk=102XXXo6jom3m1QZokuaPm2m50JPsym2e8GZm3MyWqkxB1PuRFkqB58g7FUg3lbcvCjet1EajhUqdVm5aeLJRAw3ByeJCKsTLVaLY2K1THY3Pvm5nZm26rPm5Ecm4");
                headers.put("Cookie", Cookie.toString());
                headers.put("Referer", "http://le.com");
                Map<String, String> result = this.restTemplate.multiHttpRequests(urls, null, null, headers);
                LOG.info(logPrefix + ": invoke return [" + result + "]");
                if (StringUtil.isNotBlank(result.get(subUrl.toString()))) {
                    String res = URLDecoder.decode(result.get(subUrl.toString()), "UTF-8");
                    orderTp = this.objectMapper.readValue(res, OrderStatusTpResponseV3.class);
                }
            } else {
                subUrl.append(VipTpConstant.VIP_CHECK_ORDER_STATUS_URL_TX);
                StringBuilder params = new StringBuilder();
                params.append("userId=").append(commonParam.getUserId());
                long timestamp = System.currentTimeMillis();
                params.append("&timestamp=").append(timestamp);
                params.append("&businessId=").append(4);
                params.append("&is_super=").append(1);
                String sign = this.getSign(params.toString(), "wq0euzrccodqjcjaihxngx0cwhdr49q9");
                params.append("&sign=").append(sign);
                subUrl.append(params);
                String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
                OrderStatusTpResponseTX orderTx = this.objectMapper.readValue(result, OrderStatusTpResponseTX.class);
                orderTp = this.OrderTxToOrderTp(orderTx);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return orderTp;
    }

    public OrderStatusTpResponseTX getConsumptionRecordV3ForTM(Integer status, Integer day, Integer page,
            Integer pageSize, String orderType, CommonParam commonParam) {
        OrderStatusTpResponseTX orderTp = null;
        String logPrefix = "getConsumptionRecordV3ForTM_" + commonParam.getMac() + "_" + commonParam.getUserId();
        try {
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(VipTpConstant.VIP_CHECK_ORDER_STATUS_URL_TX);
            StringBuilder params = new StringBuilder();
            params.append("userId=").append(commonParam.getUserId());
            long timestamp = System.currentTimeMillis();
            params.append("&timestamp=").append(timestamp);
            params.append("&businessId=").append(4);
            params.append("&is_super=").append(1);
            params.append("&page=").append(1);
            params.append("&pageSize=").append(50);
            String sign = this.getSign(params.toString(), "wq0euzrccodqjcjaihxngx0cwhdr49q9");
            params.append("&sign=").append(sign);
            subUrl.append(params);
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            orderTp = this.objectMapper.readValue(result, OrderStatusTpResponseTX.class);
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return orderTp;
    }

    /**
     * get vip package data
     * @return
     */
    public PricePackageListTpResponse getPricePackageList() {
        PricePackageListTpResponse recTp = null;
        String logPrefix = "getPricePackageList_";

        try {
            // http://price.zhifu.letv.com/pinfo/get/package?end={end}
            String result = this.restTemplate.getForObject(VipTpConstant.QUERY_PRICE_PACKAGE_TYPE_URL, String.class,
                    VipTpConstant.TERMINAL_TYPE_TV2);// tv terminal end send 9
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                recTp = objectMapper.readValue(result, PricePackageListTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return recTp;
    }

    /**
     * 领取免费会员接口
     * @param channel
     * @param signKey
     * @param commonParam
     * @return
     */
    public GetFreeVipTpResponse getFreeVip(Integer channel, String signKey, CommonParam commonParam) {
        GetFreeVipTpResponse response = null;
        String logPrefix = "getFreeVip_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                + commonParam.getDeviceKey() + "_" + channel;
        try {
            Long currentTime = System.currentTimeMillis();
            StringBuilder url = new StringBuilder(VipTpConstant.VIP_GET_FREE_VIP_URL);
            url.append("&type=").append("presentVip");// 固定值为presentVip
            url.append("&userId=").append(commonParam.getUserId());
            url.append("&channel=").append(channel);// 申请的渠道号
            url.append("&t=").append(currentTime);// 当前时间戳
            url.append("&sign=").append(this.getFreeVipSign(channel, currentTime, signKey));// 签名
            url.append("&subEnd=").append(111);// 子终端，TV为111
            url.append("&sVip=").append(9);// 1移动影视会员，9全屏影视会员(boss全屏影视会员类型由2改为9。。。)，如果不填默认渠道配置的会员级别
            url.append("&ip=").append(commonParam.getClientIp());// 客户端IP
            url.append("&mac=").append(commonParam.getMac());
            url.append("&devid=").append(commonParam.getDeviceKey());
            // http://yuanxian.letv.com/letv/sale.ldo?type=presentVip&userId={userId}&sign={sign}&channel={channel}&t={currTime}&ip={ip}&mac={mac}&subEnd=2&sVip=1&days=7
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, GetFreeVipTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error:", e);
        }
        return response;
    }

    /**
     * 领取免费会员接口V2
     * @param channel
     * @param signKey
     * @param commonParam
     * @return
     */
    public GetFreeVipV2TpResponse getFreeVipV2(Integer channel, String signKey, CommonParam commonParam) {
        GetFreeVipV2TpResponse response = null;
        String logPrefix = "getFreeVipV2_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                + commonParam.getDeviceKey() + "_" + channel;
        try {
            Long currentTime = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
            Date now = new Date(currentTime);
            String type = "presentVip";
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(VipTpConstant.VIP_CHECK_ORDER_STATUS_URL_V2);
            StringBuilder params = new StringBuilder();

            params.append("type=").append(type);
            params.append("&timestamp=").append(currentTime);// 当前时间戳
            params.append("&channel=").append(channel);
            params.append("&channelSign=").append(this.getFreeVipSign(channel, currentTime, signKey));// 渠道签名
            params.append("&userId=").append(commonParam.getUserId());
            params.append("&ip=").append(commonParam.getClientIp());// 客户端IP
            params.append("&mac=").append(commonParam.getMac());
            params.append("&deviceId=").append(commonParam.getDeviceKey());
            VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
            if (country != null) {
                params.append("&country=").append(country.getCode());
            }
            // 14位日期+12随机数，为防止同用户重复提交，日期取整小时，随机数取用户id补零
            params.append("&out_trade_no=").append(
                    sdf.format(now) + "0000" + String.format("%012d", Integer.parseInt(commonParam.getUserId())));
            params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
            String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
            params.append("&sign=").append(sign);
            subUrl.append(params);
            // http://yuanxian.cp21.ott.cibntv.net/letv/vip2.ldo?type=presentVip&timestamp={timestamp}&channel={channel}&channelSign={channelSign}&userId={userId}&ip={ip}&mac={mac}&deviceId={deviceId}&country={country}&out_trade_no={out_trade_no}&businessId={businessId}&sign={sign}
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, GetFreeVipV2TpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error:", e);
        }
        return response;
    }

    /**
     * 购买单片，获取订单号
     *            调用第三方接口参数列表
     * @param locale
     *            调用第三方接口的locale
     * @return
     */
    public PurchaseOrderTpResponse purchaseForSingle(PurchaseOrderRequest purchaseOrderRequest, Locale locale) {
        PurchaseOrderTpResponse purOrder = null;
        String logPrefix = "getPurchaseOrder_" + purchaseOrderRequest.getUsername() + "_"
                + purchaseOrderRequest.getUserid() + "_" + purchaseOrderRequest.getProductId();

        try {
            // http://yuanxian.letv.com/letv/newPay.ldo?ptype={ptype}&pid={pid}&vid={vid}&end={end}&userid={userid}&packtype={packtype}
            String result = this.restTemplate.getForObject(VipTpConstant.getOrderIdForSingleUrl(locale), String.class,
                    purchaseOrderRequest.getParametersMap());
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                purOrder = this.objectMapper.readValue(result, PurchaseOrderTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error:", e);
        }

        return purOrder;
    }

    /**
     * 获取阿里支付二维码
     *            调用第三方接口参数列表
     * @param locale
     *            调用第三方接口locale
     * @return
     */
    @Deprecated
    public AliPayCodeTpResponse getAliPayCode(ConsumeVipRequest request, Locale locale) {
        AliPayCodeTpResponse payCode = null;

        try {
            String result = this.restTemplate.getForObject(VipTpConstant.getAliPayCodeUrl(locale), String.class,
                    request.getParametersMap());
            LOG.info("getAliPayCode_" + request.getUsername() + "_" + request.getUserid() + "_" + request.getCorderid()
                    + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                payCode = this.objectMapper.readValue(result, AliPayCodeTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(
                    "getAliPayCode_" + request.getUsername() + "_" + request.getUserid() + "_" + request.getCorderid()
                            + " [error]: ", e);
        }

        return payCode;
    }

    /**
     * 获取阿里支付图片类型的二维码
     * @param consumeVipRequest
     *            调用第三方接口参数列表
     * @param locale
     *            调用第三方接口locale
     * @return
     */
    @Deprecated
    public AliPayCodeIMGTpResponse getAliPayCodeImg(ConsumeVipRequest consumeVipRequest, Locale locale) {
        AliPayCodeIMGTpResponse payCodeIMG = null;
        String logPrefix = "createPayCode_" + consumeVipRequest.getUsername() + "_" + consumeVipRequest.getUserid()
                + "_" + consumeVipRequest.getCorderid();

        String url = VipTpConstant.getAliPayCodeImgUrl(locale);
        // Integer av = consumeVipRequest.getAv();
        // if (av != null && 1 == av) {
        // // 是国广订单
        // url = VipTpConstant.getAliPayCodeImgCibnUrl(locale);
        // }

        try {
            String result = this.restTemplate.getForObject(url, String.class, consumeVipRequest.getParametersMap());
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                payCodeIMG = this.objectMapper.readValue(result, AliPayCodeIMGTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return payCodeIMG;
    }

    /**
     * 创建微信支付二维码
     *            调用第三方接口参数列表
     * @param locale
     *            调用第三方接口参数列表
     * @return
     */
    @Deprecated
    public WeixinPayCodeTpResponse getWXPayCode(ConsumeVipRequest request, Locale locale) {
        WeixinPayCodeTpResponse payCode = null;
        String logPrefix = "getWXPayCode_" + request.getUsername() + "_" + request.getUserid() + "_"
                + request.getCorderid();

        try {
            String result = this.restTemplate.getForObject(VipTpConstant.getWeixinCodeUrl(locale), String.class,
                    request.getParametersMap());
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                payCode = this.objectMapper.readValue(result, WeixinPayCodeTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return payCode;
    }

    /**
     * 手机和拉卡拉支付
     *            调用第三方接口参数列表
     * @param locale
     *            调用第三方语言环境
     * @return
     */
    @Deprecated
    public PhonePayTpResponse phonePay(PhonePayRequest phonePayRequest, Locale locale) {
        PhonePayTpResponse payTp = null;
        String logPrefix = "phonePay_" + phonePayRequest.getUsername() + "_" + phonePayRequest.getUserid() + "_"
                + phonePayRequest.getCorderid();
        String url = null;

        Map<String, Object> params = phonePayRequest.getParameterMap();

        // 拉卡拉支付
        if (phonePayRequest.isLakala()) {
            url = VipTpConstant.getLakalaPayUrl(locale);
        } else {
            // 手机支付
            params.put("productid", "2");
            url = VipTpConstant.getPhonePayUrl(locale);
        }

        try {
            String result = this.restTemplate.getForObject(url, String.class, phonePayRequest.getParameterMap());
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                payTp = this.objectMapper.readValue(result, PhonePayTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return payTp;
    }

    /**
     * check phone if support pay
     * @param phone
     *            phone number
     * @param ip
     *            client ip
     * @return
     */
    public PhoneCheckTpResponse getPhoneCheck(String phone, String ip) {
        PhoneCheckTpResponse checkTp = null;
        String logPrefix = "getPhoneCheck_" + phone + "_" + ip;

        try {
            // http://api.zhifu.letv.com/phone/{phone}?ip={ip}
            String result = this.restTemplate.getForObject(VipTpConstant.CHECK_PHONE_URL, String.class, phone, ip);
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                checkTp = this.objectMapper.readValue(result, PhoneCheckTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return checkTp;
    }

    /**
     * check order status
     * @param orderId
     * @param commonParam
     * @return
     */
    public OrderStatusTpResponse checkOrderStatus(String orderId, CommonParam commonParam) {
        OrderStatusTpResponse orderTp = null;
        String logPrefix = "checkOrderStatus_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + orderId;

        try {
            StringBuilder url = new StringBuilder(VipTpConstant.VIP_CHECK_ORDER_STATUS_URL);
            url.append("type=").append("order");
            url.append("&orderid=").append(orderId);
            url.append("&userid=").append(commonParam.getUserId());
            // http://yuanxian.letv.com/letv/sale.ldo?type=order&orderid={orderid}&userid={userid}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                orderTp = this.objectMapper.readValue(result, OrderStatusTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return orderTp;
    }

    /**
     * check order status
     * @param orderId
     * @param commonParam
     * @return
     */
    public OrderStatusTpResponseV2 checkOrderStatusV2(String orderId, CommonParam commonParam) {
        OrderStatusTpResponseV2 orderTp = null;
        String logPrefix = "checkOrderStatus_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + orderId;
        try {
            String type = "orderList";
            int rows = 5;
            int page = 1;
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(VipTpConstant.VIP_CHECK_ORDER_STATUS_URL_V2);
            StringBuilder params = new StringBuilder();
            params.append("type=").append(type);
            params.append("&userId=").append(commonParam.getUserId());
            VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
            if (country != null) {
                params.append("&country=").append(country.getCode());
            }
            params.append("&orderId=").append(orderId);
            params.append("&rows=").append(rows);
            params.append("&page=").append(page);
            params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
            String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
            params.append("&sign=").append(sign);
            subUrl.append(params);
            // http://yuanxian.letv.com/letv/vip2.ldo?type=orderList&userId=123123123&status=2&productType=1&page=1&rows=5
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                orderTp = this.objectMapper.readValue(result, OrderStatusTpResponseV2.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return orderTp;
    }

    /**
     * 使用乐点开通高级vip包年服务
     * corderid：商品订单id,我们双方建立关系的根据 userid：用户登录的id。 companyid：乐视为1，致新为2。
     * deptid：部门id，没有部门id默认给0即可。 pid：专辑id，没有默认给0即可。 productid:产品id，没有默认给0就可。
     * backurl：服务器notifyurl fronturl：页面通知url price：价格 productnum:商品数量
     * productname:商品名称 productdesc:商品描述 defaultbank：默认的银行,银行编码见支付宝附件
     *            开通VIP参数列表
     * @param locale
     *            调用第三方接口环境locale
     * @return
     */
    @Deprecated
    public ConsumptionTpResponse consumeVIPByLetvPoint(ConsumeVipRequest consumeVipRequest, Locale locale) {
        ConsumptionTpResponse conTp = null;
        String logPrefix = "consumeVIPByLetvPoint_" + consumeVipRequest.getUsername() + "_"
                + consumeVipRequest.getUserid() + "_" + consumeVipRequest.getCorderid()
                + consumeVipRequest.getProductid();
        try {
            String result = this.restTemplate.getForObject(VipTpConstant.getConsumptionVipByLetvPointUrl(locale),
                    String.class, consumeVipRequest.getParametersMap());
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                conTp = this.objectMapper.readValue(result, ConsumptionTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return conTp;
    }

    /**
     * get qrcode with user token info
     * @param next_action
     *            source qrcode
     * @param expire
     * @param commonParam
     * @return
     */
    public SsoQRCodeTpResponse createSsoQRCode(String next_action, Long expire, CommonParam commonParam) {
        String logPrefix = "createSsoQRCode_" + commonParam.getMac() + "_" + commonParam.getUserId();
        SsoQRCodeTpResponse ssoTp = null;
        try {
            StringBuilder url = new StringBuilder(VipTpConstant.VIP_SSO_QRCODE_URL);
            url.append("sso_tk=").append(commonParam.getUserToken());
            url.append("&next_action=").append(StringUtils.trimToEmpty(next_action));
            url.append("&expire=").append(expire);
            // http://sso.le.com/user/createQRCode?sso_tk={sso_tk}&next_action={next_action}&expire={expire}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                ssoTp = this.objectMapper.readValue(result, SsoQRCodeTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " error: ", e);
        }
        return ssoTp;
    }

    /**
     * get bind info of machine and user
     * @param deviceType
     * @param commonParam
     * @return
     */
    public DeviceBindTpResponse getDeviceBindInfo(Integer deviceType, CommonParam commonParam) {
        DeviceBindTpResponse tp = null;
        String logPrefix = "getDeviceBindInfo_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                + commonParam.getDeviceKey();
        try {
            StringBuilder url = new StringBuilder(VipTpConstant.VIP_GET_DEVICE_BIND_INFO_URL);
            // query type
            url.append("type=").append("queryByDevice");
            url.append("&deviceKey=").append(commonParam.getDeviceKey());
            url.append("&mac=").append(commonParam.getMac());
            url.append("&deviceType=").append(deviceType);
            url.append("&sign=").append(
                    this.getDeviceBindSign(commonParam.getDeviceKey(), commonParam.getMac(), deviceType));
            // http://yuanxian.letv.com/letv/deviceBind.ldo?type=queryByDevice&deviceKey={deviceKey}&mac={mac}&deviceType={deviceType}&sign={sign}
            String response = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + response + "]");

            if (response != null) {
                response = URLDecoder.decode(response, "UTF-8");
                tp = objectMapper.readValue(response, DeviceBindTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error:", e);
        }

        return tp;
    }

    /**
     * 查询未领取的超级手机赠送机卡绑定TV会员
     * @return
     */
    public GetPresentDeviceBindTpResponse getPresentDeviceBind(CommonParam commonParam) {
        GetPresentDeviceBindTpResponse tp = null;
        String logPrefix = "getPresentDeviceBind_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                + commonParam.getDeviceKey();

        try {
            StringBuilder url = new StringBuilder(VipTpConstant.PRESENT_DEVICE_BIND_URL);
            url.append("type=").append("queryPresentVip");
            url.append("&userId=").append(commonParam.getUserId());
            url.append("&presentType=").append(VipTpConstant.PRESENT_DEBICE_BIND_TYPE_TV);
            url.append("&presentDeviceKey=").append(StringUtils.trimToEmpty(commonParam.getDeviceKey()));
            url.append("&presentDeviceInfo=").append(commonParam.getMac());
            url.append("&sign=").append(
                    this.getPresentDeviceBindSign(commonParam.getDeviceKey(), commonParam.getMac(),
                            commonParam.getUserId(), VipTpConstant.PRESENT_DEBICE_BIND_TYPE_TV));
            // http://yuanxian.letv.com/letv/deviceBind.ldo?type=queryPresentVip&userId={userId}&presentType={presentType}&presentDeviceKey={presentDeviceKey}&presentDeviceInfo={presentDeviceInfo}&sign={sign}
            String response = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + response + "]");

            if (response != null) {
                response = URLDecoder.decode(response, "UTF-8");
                tp = objectMapper.readValue(response, GetPresentDeviceBindTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error:", e);
        }

        return tp;
    }

    /**
     * get user device bind info
     * @param deviceType
     * @param commonParam
     * @return
     */
    public UserBindTpResponse getUserBindInfo(Integer deviceType, CommonParam commonParam) {
        UserBindTpResponse response = null;
        String logPrefix = "getUserBindInfo_" + commonParam.getMac() + "_" + commonParam.getDeviceKey() + "_"
                + commonParam.getUserId();
        StringBuilder url = new StringBuilder(VipTpConstant.VIP_DEVICE_BIND_INFO_GET_URL);
        url.append("type=").append("queryByUid");
        url.append("&userid=").append(commonParam.getUserId());
        url.append("&deviceKey=").append(commonParam.getDeviceKey());
        url.append("&mac=").append(commonParam.getMac());
        url.append("&deviceType=").append(deviceType);
        url.append("&sign=").append(
                this.getUserBindSign(commonParam.getUserId(), commonParam.getDeviceKey(), commonParam.getMac(),
                        deviceType));
        try {
            // http://yuanxian.letv.com/letv/deviceBind.ldo?type=queryByUid&userid={userid}&deviceKey={deviceKey}&mac={mac}&deviceType={deviceType}&sign={sign}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                response = objectMapper.readValue(result, UserBindTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    /**
     * 调用第三方接口，获取单点支付--支付宝图片二维码
     * @param locale
     * @return
     */
    @Deprecated
    public AliPayCodeIMGTpResponse getOrderPaycodeAlipayIMG(OrderPayCodeRequest orderPayCodeRequest, Locale locale) {
        AliPayCodeIMGTpResponse payCodeIMG = null;
        String logPrefix = "getOrderPaycode_" + orderPayCodeRequest.getUsername() + "_"
                + orderPayCodeRequest.getUserid() + "_" + orderPayCodeRequest.getPid();

        try {
            String response = this.restTemplate.getForObject(VipTpConstant.getOrderPaycodeAlipayImgUrl(locale),
                    String.class, orderPayCodeRequest.getParametersMap());
            LOG.info(logPrefix + ": invoke return [" + response + "]");

            if (response != null) {
                response = URLDecoder.decode(response, "UTF-8");
                payCodeIMG = objectMapper.readValue(response, AliPayCodeIMGTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return payCodeIMG;
    }

    /**
     * 调用第三方接口，获取单点支付--微信图片二维码
     * @param locale
     * @return
     */
    @Deprecated
    public WeixinPayCodeTpResponse getOrderPaycodeWeinxinIMG(OrderPayCodeRequest orderPayCodeRequest, Locale locale) {
        WeixinPayCodeTpResponse payCode = null;
        String logPrefix = "getOrderPaycode_" + orderPayCodeRequest.getUsername() + "_"
                + orderPayCodeRequest.getUserid() + "_" + orderPayCodeRequest.getPid();

        try {
            String response = this.restTemplate.getForObject(VipTpConstant.getOrderPaycodeWeinxinImgUrl(locale),
                    String.class, orderPayCodeRequest.getParametersMap());
            LOG.info(logPrefix + ": invoke return[" + response + "]");

            if (response != null) {
                response = URLDecoder.decode(response, "UTF-8");
                payCode = objectMapper.readValue(response, WeixinPayCodeTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return payCode;
    }

    /**
     * 调用第三方接口，获取单点支付--拉卡拉图片二维码
     * @param locale
     * @return
     */
    @Deprecated
    public LakalaPayCodeTpResponse getOrderPaycodeLakalaIMG(OrderPayCodeRequest orderPayCodeRequest, Locale locale) {
        LakalaPayCodeTpResponse payCode = null;
        String logPrefix = "getOrderPaycode_" + orderPayCodeRequest.getUsername() + "_"
                + orderPayCodeRequest.getUserid() + "_" + orderPayCodeRequest.getPid();

        try {
            String response = this.restTemplate.getForObject(VipTpConstant.getOrderPaycodeLakalaImgUrl(locale),
                    String.class, orderPayCodeRequest.getParametersMap());
            LOG.info(logPrefix + ": invoke return[" + response + "]");

            if (response != null) {
                response = URLDecoder.decode(response, "UTF-8");
                payCode = objectMapper.readValue(response, LakalaPayCodeTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return payCode;
    }

    /**
     * 读取静态服务器上文件fileName中的定向活动用户名列表，并转换为Set<String>
     * @param fileName
     * @return
     */
    public Set<String> readDirectionalPushUser(String fileName) {
        Set<String> set = null;
        if (StringUtils.isNotBlank(fileName)) {
            String staticFileUrl = VipTpConstant.STATIC_FILE_SERVER_BASE_URL + fileName;

            String response = this.restTemplate.getForObject(staticFileUrl, String.class);
            LOG.info("readDirectionalPushUser_url[" + staticFileUrl + "], invoke return[" + response + "]");

            if (StringUtils.isNotBlank(response)) {
                String separator = System.getProperty("line.separator");
                String[] usernames = response.split(separator);
                if (ArrayUtils.isNotEmpty(usernames)) {
                    set = new HashSet<String>((int) (usernames.length / 0.75) + 1);
                    for (String username : usernames) {
                        if (StringUtils.isNotBlank(username)) {
                            set.add(username.trim());
                        }
                    }
                    LOG.info("readDirectionalPushUser_read " + set.size() + " Users");
                }
            }
        }
        return set;
    }

    /**
     * 删除消费记录
     * @return
     */
    public StatusResponse deleteOrder(String orderid, String userid) {
        StatusResponse response = new StatusResponse();
        String logPrefix = "deleteOrder_" + userid + "_" + orderid;

        try {
            // http://yuanxian.letv.com/letv/sale.ldo?type=delete&orderid={orderid}&userid={userid}
            String result = this.restTemplate.getForObject(VipTpConstant.DELETE_ORDER_URL, String.class, orderid,
                    userid);
            LOG.info(logPrefix + " invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                DeleteConsumeRecordResponse dr = objectMapper.readValue(result, DeleteConsumeRecordResponse.class);

                if ("true".equalsIgnoreCase(dr.getStatus())) {
                    response.setStatus(1);
                } else {
                    response.setStatus(0);
                }
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return response;
    }

    /**
     * 获取支付宝图片二维码通用接口（支持自有版和国广版本）
     * @return
     */
    public AliPayCodeIMGTpResponse getPurchaseVipAliPaycodeImg(PurchaseVipCommonRequest purchaseVipCommonRequest) {
        AliPayCodeIMGTpResponse payCodeIMG = null;
        String logPrefix = "purchaseVip_" + purchaseVipCommonRequest.getUsername() + "_"
                + purchaseVipCommonRequest.getUserid() + "_" + purchaseVipCommonRequest.getMac();

        try {
            // http://api.zhifu.letv.com/pay/tvtwodimensioncode/5?corderid={corderid}&userid={userid}&buyType={buyType}&productid={productid}&pid={pid}&videoid={videoid}&price={price}&svip={svip}&sign={sign}&productnum={productnum}&deviceid={deviceid}&ip={ip}&av={av}&subend={subend}&activityIds={activityIds}&companyid={companyid}&deptid={deptid}&fronturl={fronturl}&backurl={backurl}
            String url = VipTpConstant.PURCHASE_VIP_ALI_PAYCODE_URL;
            String response = this.restTemplate.getForObject(url, String.class,
                    purchaseVipCommonRequest.getParametersMap());
            LOG.info(logPrefix + ": invoke return [" + response + "]");

            if (response != null) {
                response = URLDecoder.decode(response, "UTF-8");
                payCodeIMG = objectMapper.readValue(response, AliPayCodeIMGTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return payCodeIMG;
    }

    /**
     * 生成微信图片二维码通用接口
     * @return
     */
    public WeixinPayCodeTpResponse getPurchaseVipWeinxinPaycodeImg(PurchaseVipCommonRequest purchaseVipCommonRequest) {
        WeixinPayCodeTpResponse payCode = null;
        String logPrefix = "purchaseVip_" + purchaseVipCommonRequest.getUsername() + "_"
                + purchaseVipCommonRequest.getUserid() + "_" + purchaseVipCommonRequest.getMac();

        try {
            // http://api.zhifu.letv.com/pay/wxcommon/24?corderid={corderid}&userid={userid}&buyType={buyType}&productid={productid}&pid={pid}&videoid={videoid}&price={price}&svip={svip}&sign={sign}&deviceid={deviceid}&ip={ip}&av={av}&subend={subend}&activityIds={activityIds}&productnum={productnum}&companyid={companyid}&deptid={deptid}&fronturl={fronturl}&backurl={backurl}
            String url = VipTpConstant.PURCHASE_VIP_WEIXIN_PAYCODE_URL;
            String response = this.restTemplate.getForObject(url, String.class,
                    purchaseVipCommonRequest.getParametersMap());
            LOG.info(logPrefix + ": invoke return[" + response + "]");

            if (response != null) {
                response = URLDecoder.decode(response, "UTF-8");
                payCode = objectMapper.readValue(response, WeixinPayCodeTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return payCode;
    }

    /**
     * 生成拉卡拉图片二维码通用接口
     * @return
     */
    public LakalaPayCodeTpResponse getPurchaseVipLakalaPaycodeImg(PurchaseVipCommonRequest purchaseVipCommonRequest) {
        LakalaPayCodeTpResponse payCode = null;
        String logPrefix = "purchaseVip_" + purchaseVipCommonRequest.getUsername() + "_"
                + purchaseVipCommonRequest.getUserid() + "_" + purchaseVipCommonRequest.getMac();

        try {
            // http://api.zhifu.letv.com/pay/lakala/16?corderid={corderid}&userid={userid}&buyType={buyType}&productid={productid}&pid={pid}&videoid={videoid}&price={price}&svip={svip}&sign={sign}&productnum={productnum}&deviceid={deviceid}&ip={ip}&av={av}&subend={subend}&activityIds={activityIds}&coupons={couponCode}&companyid={companyid}&deptid={deptid}&fronturl={fronturl}&backurl={backurl}
            String url = VipTpConstant.PURCHASE_VIP_LAKALA_PAYCODE_URL;
            String response = this.restTemplate.getForObject(url, String.class,
                    purchaseVipCommonRequest.getParametersMap());
            LOG.info(logPrefix + ": invoke return[" + response + "]");

            if (response != null) {
                response = URLDecoder.decode(response, "UTF-8");
                payCode = objectMapper.readValue(response, LakalaPayCodeTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return payCode;
    }

    /**
     * 使用乐点购买TV端产品通用接口
     *            开通VIP参数列表
     * @return
     */
    public LetvpointPaymentTpResponse purchaseVipByLetvpoint(PurchaseVipCommonRequest purchaseVipCommonRequest) {
        LetvpointPaymentTpResponse conTp = null;
        String logPrefix = "purchaseVip_" + purchaseVipCommonRequest.getUsername() + "_"
                + purchaseVipCommonRequest.getUserid() + "_" + purchaseVipCommonRequest.getMac();
        try {
            // http://api.zhifu.letv.com/pay/point/33?corderid={corderid}&userid={userid}&buyType={buyType}&productid={productid}&pid={pid}&videoid={videoid}&price={price}&svip={svip}&sign={sign}&productnum={productnum}&deviceid={deviceid}&ip={ip}&av={av}&subend={subend}&activityIds={activityIds}&coupons={couponCode}&companyid={companyid}&deptid={deptid}&fronturl={fronturl}&backurl={backurl}
            String url = VipTpConstant.PURCHASE_VIP_LETVPOINT_PAYMENT_URL;
            String result = this.restTemplate.getForObject(url, String.class,
                    purchaseVipCommonRequest.getParametersMap());
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                conTp = objectMapper.readValue(result, LetvpointPaymentTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return conTp;
    }

    /**
     * 易宝支付下单
     * @param purchaseVipCommonRequest
     *            请求参数
     * @param url
     *            访问地址，针对绑卡支付和未绑卡支付
     * @return
     */
    public YeePayOrderTpResponse purchaseVipByYeePay(PurchaseVipCommonRequest purchaseVipCommonRequest, String url) {
        YeePayOrderTpResponse response = null;
        String logPrefix = "purchaseVipByYeePay_" + purchaseVipCommonRequest.getUsername() + "_"
                + purchaseVipCommonRequest.getUserid() + "_" + purchaseVipCommonRequest.getPaymentChannel();

        try {
            // http://api.zhifu.letv.com/pay/mobile/41?corderid={corderid}&userid={userid}&buyType={buyType}&productid={productid}&pid={pid}&videoid={videoid}&price={price}&svip={svip}&sign={sign}&productnum={productnum}&deviceid={deviceid}&ip={ip}&av={av}&subend={subend}&activityIds={activityIds}&coupons={couponCode}&companyid={companyid}&deptid={deptid}&fronturl={fronturl}&backurl={backurl}
            // http://api.zhifu.letv.com/pay/mobile/42?corderid={corderid}&userid={userid}&buyType={buyType}&productid={productid}&pid={pid}&videoid={videoid}&price={price}&svip={svip}&sign={sign}&productnum={productnum}&deviceid={deviceid}&ip={ip}&av={av}&subend={subend}&activityIds={activityIds}&coupons={couponCode}&companyid={companyid}&deptid={deptid}&bindid={bindid}&fronturl={fronturl}&backurl={backurl}
            String result = this.restTemplate.getForObject(url, String.class,
                    purchaseVipCommonRequest.getParametersMap());
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                response = objectMapper.readValue(result, YeePayOrderTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    /**
     * get pay url
     */
    public PurchaseProductResponse purchaseProduct(String corderId, Integer buyType, Long productId, Long pid,
            String price, Integer svip, String activityIds, String couponCode, Integer av, Long videoId,
            Integer bindingtype, Integer addroverride, Integer subend, String bindid, String cardNumber,
            String deviceId, String siteId, String tvId, Integer paymentChannel, CommonParam commonParam) {
        String logPrefix = "purchaseProduct_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + productId
                + "_" + videoId;
        PurchaseProductResponse response = null;
        StringBuilder url = new StringBuilder(VipTpConstant.getPayUrlByPaymentChannel(String.valueOf(paymentChannel),
                null));
        url.append("corderid=").append(corderId);
        url.append("&userid=").append(commonParam.getUserId());
        url.append("&buyType=").append(buyType);
        url.append("&productid=").append(productId);
        url.append("&pid=").append(pid);
        url.append("&price=").append(price);
        url.append("&svip=").append(svip);
        url.append("&activityIds=").append(activityIds);
        url.append("&couponCode=").append(couponCode);
        url.append("&av=").append(av);
        url.append("&videoid=").append(videoId);
        url.append("&deviceid=").append(commonParam.getMac());
        url.append("&ip=").append(commonParam.getClientIp());
        url.append("&productnum=").append(VipTpConstant.DEFAULT_PRODUCTNUM);
        url.append("&bindingtype=").append(bindingtype);
        url.append("&addroverride=").append(addroverride);
        url.append("&sign=").append(
                this.getPurchaseProductSign(corderId, commonParam.getUserId(), pid, price,
                        VipTpConstant.DEFAULT_DEPTID, buyType));
        url.append("&deptid=").append(VipTpConstant.DEFAULT_DEPTID);
        url.append("&companyid=").append(VipTpConstant.DEFAULT_COMPANYID);
        url.append("&backurl=").append(VipTpConstant.DEFAULT_BACKURL);
        url.append("&fronturl=").append(VipTpConstant.DEFAULT_FRONTURL);
        url.append("&subend=").append(subend);
        url.append("&bindid=").append(bindid);// 易宝支付参数
        url.append("&cardNumber=").append(StringUtils.trimToEmpty(cardNumber));
        url.append("&mac=").append(StringUtils.trimToEmpty(commonParam.getMac()));
        url.append("&deviceKey=").append(StringUtils.trimToEmpty(commonParam.getDeviceKey()));
        url.append("&tpSdkDeviceId=").append(StringUtils.trimToEmpty(deviceId));// 华数参数
        url.append("&siteId=").append(StringUtils.trimToEmpty(siteId));// 华数参数
        url.append("&tvId=").append(StringUtils.trimToEmpty(tvId));// 华数参数
        try {
            // urlPrefix?corderid={corderid}&userid={userid}&buyType={buyType}&productid={productid}&pid={pid}&videoid={videoid}&price={price}&svip={svip}&sign={sign}&productnum={productnum}&deviceid={deviceid}&ip={ip}&av={av}&subend={subend}&activityIds={activityIds}&coupons={couponCode}&companyid={companyid}&deptid={deptid}&bindid={bindid}&fronturl={fronturl}&backurl={backurl}&device_id={tpSdkDeviceId}&site_id={siteId}&tv_id={tvId}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                response = objectMapper.readValue(result, PurchaseProductResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    /**
     * generate order without payment channel
     * @param productId
     * @param activityIds
     *            activity id for vip package
     * @param couponCode
     * @param commonParam
     * @return
     */
    public PurchaseProductResponse purchaseProductNoPaymentchannel(String productId, String pid, String activityIds,
            String couponCode, String orderFrom, CommonParam commonParam) {
        PurchaseProductResponse response = null;
        String logPrefix = "purchaseProductNoPaychannel_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                + productId;

        StringBuilder url = new StringBuilder(VipTpConstant.VIP_PURCHASE_PRODUCT_URL);
        url.append("type=").append("orderid");
        if (StringUtil.isBlank(pid)) {
            url.append("&pid=").append(9);
        } else {
            url.append("&pid=").append(pid);
        }
        url.append("&end=").append(9);
        url.append("&uid=").append(commonParam.getUserId());
        url.append("&username=").append(commonParam.getUsername());
        url.append("&productid=").append(productId);
        url.append("&activityIds=").append(StringUtils.trimToEmpty(activityIds));
        url.append("&coupons=").append(StringUtils.trimToEmpty(couponCode));
        url.append("&terminal=").append(VipTpConstant.TERMINAL_TV);
        if (StringUtil.isNotBlank(orderFrom)) {// buy album need send 9
            url.append("&orderfrom=").append(orderFrom);
        }
        url.append("&pfrom=").append(VipTpConstant.VIP_GENERATE_ORDER_FROM);// 1--乐视，2--乐视国广，3--运营商，4--TCL，5--飞利浦
        url.append("&deviceModel=").append(StringUtils.trimToEmpty(commonParam.getTerminalSeries()));
        try {
            // http://yuanxian.letv.com/letv/payMent.ldo?type=orderid&pid=9&end=9&terminal=141007&uid={userId}&username={username}&productid={productId}&activityIds={activityIds}&coupons={couponCode}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, PurchaseProductResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    /**
     * 获取活动信息
     * @param terminalType
     *            终端类型
     * @param userid
     *            用户中心id
     * @param locale
     *            调用第三方接口的locale
     * @return
     */
    public PaymentActivityTpResponse getPaymentActivity(VipCommonRequest vipCommonRequest, String langcode) {
        PaymentActivityTpResponse actTp = null;
        String logPrefix = "getPaymentActivity_" + vipCommonRequest.getUsername() + "_" + vipCommonRequest.getUserid();

        try {
            // http://yuanxian.letv.com/letv/activity.ldo?userId={userid}&svip=9&from=141007
            String result = this.restTemplate.getForObject(VipTpConstant.GET_PAYMENT_ACTIVITY_URL, String.class,
                    vipCommonRequest.getParametersMap());
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                actTp = this.objectMapper.readValue(result, PaymentActivityTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return actTp;
    }

    public GetActivityDataResponse getActivityData(String activityIds, CommonParam commonParam) {
        GetActivityDataResponse response = null;
        String logPrefix = "getActivityData_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                + activityIds;
        try {
            // http://price.zhifu.letv.com/activity/gets?ids={ids}
            String result = this.restTemplate.getForObject(VipTpConstant.GET_BOSS_ACTIVITY_DATA, String.class,
                    activityIds);
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = this.objectMapper.readValue(result, GetActivityDataResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    /**
     * check coupon status
     * @param couponCode
     *            the discount ticket
     * @param commonParam
     * @return
     */
    public VoucherStatusTpResponse checkVoucherStatus(String couponCode, CommonParam commonParam) {
        VoucherStatusTpResponse tpResponse = null;
        String logPrefix = "checkVoucherStatus_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                + couponCode;

        try {
            String result = this.restTemplate.getForObject(VipTpConstant.CHECK_VOUCHER_STATUS_URL, String.class,
                    couponCode);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                tpResponse = this.objectMapper.readValue(result, VoucherStatusTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return tpResponse;
    }

    /**
     * read static file content from static server
     * @param fileName
     * @return
     */
    public String readUserAggrement(String fileName) {
        String response = null;
        if (StringUtils.isNotBlank(fileName)) {
            String staticFileUrl = VipTpConstant.STATIC_FILE_SERVER_BASE_URL + fileName;
            // http://i.static.itv.letv.com/api/vip_user_aggrement_{localeStr}.txt
            response = this.restTemplate.getForObject(staticFileUrl, String.class);
            LOG.info("readUserAggrement_url[" + staticFileUrl + "], invoke return[" + response + "]");
        }
        return response;
    }

    /**
     * 直播付费鉴权，查看当前用户是否已经购买过直播（是否有播放权限）
     * @param logPrefix
     *            日志关键参数
     * @return
     */
    public CheckLiveTpResponse doCheckLive(String pid, String liveId, String streamId, String splatId, Integer isstart,
            String termid, String logPrefix, CommonParam commonParam) {
        CheckLiveTpResponse response = null;
        StringBuilder url = new StringBuilder(VipTpConstant.CHECK_LIVE_URL);
        url.append("pid=").append(pid);
        url.append("&liveid=").append(liveId);
        url.append("&from=").append(VipTpConstant.LIVE_CHECK_FROM_TV_ADD_TRYPLAY);
        url.append("&streamId=").append(streamId);
        url.append("&splatId=").append(splatId);
        url.append("&userId=").append(commonParam.getUserId());
        url.append("&isstart=").append(isstart);
        url.append("&sign=").append(this.getCheckLiveSign(commonParam.getUserId(), liveId));
        url.append("&callback=").append(StringUtils.EMPTY);
        url.append("&flag=").append(commonParam.getMac());
        url.append("&termid=").append(termid);
        url.append("&mac=").append(commonParam.getMac());
        url.append("&devicekey=").append(StringUtils.trimToEmpty(commonParam.getDeviceKey()));
        try {
            // http://yuanxian.letv.com/letv/liveValidate.ldo?pid={pid}&liveid={liveid}&from={from}&streamId={streamId}&splatId={splatId}&userId={userId}&isstart={isstart}&sign={sign}&callback={callback}&flag={flag}&termid={termid}&mac={mac}&devicekey={deviceKey}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                response = this.objectMapper.readValue(result, CheckLiveTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return response;
    }

    /**
     * 一键支付绑定查询，暂只校验paypal支付
     * @return
     */
    public CheckOneKeyPayTpResponse checkOneKeyPay(Integer paymentChannel, String logPrefix, CommonParam commonParam) {
        CheckOneKeyPayTpResponse response = null;

        StringBuilder url = new StringBuilder(VipTpConstant.CHECK_ONEKEY_PAY_URL);
        url.append("userid=").append(commonParam.getUserId());
        url.append("&paytype=").append(paymentChannel);
        url.append("&sign=").append(this.getCheckOneKeyPaySign(commonParam.getUserId(), paymentChannel));
        try {
            // http://api.zhifu.letv.com/pay/queryonekey?userid={userid}&paytype={paytype}&sign={sign}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                response = this.objectMapper.readValue(result, CheckOneKeyPayTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return response;
    }

    /**
     * 查询用户是否购买过某一直播券
     * @return
     */
    public CheckUserLiveTicketTpResponse checkUserLiveTicket(String channel, String category, String season,
            String turn, String game, String logPrefix, CommonParam commonParam) {
        CheckUserLiveTicketTpResponse response = null;

        StringBuilder url = new StringBuilder(VipTpConstant.LIVE_TICKET_URL);
        url.append("type=").append("getticket");
        url.append("&userid=").append(commonParam.getUserId());
        url.append("&channel=").append(channel);
        url.append("&category=").append(category);
        url.append("&season=").append(season);
        url.append("&turn=").append(turn);
        url.append("&game=").append(game);
        url.append("&sign=")
                .append(this.getCheckUserLiveTicketSign(commonParam.getUserId(), channel, category, season));
        try {
            // http://yuanxian.letv.com/letv/live.ldo?type=getticket&userid={userid}&channel={channel}&category={category}&season={season}&turn={turn}&game={game}&sign={sign}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                // 返回结果如{"status":1,"package":[{"count":1,"type":1,"status":1}，包含Java关键字package，需进行替换
                result = result.replaceAll("package\"", "packages\"");
                result = URLDecoder.decode(result, "UTF-8");
                response = objectMapper.readValue(result, CheckUserLiveTicketTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return response;
    }

    /**
     * 激活用户已购买的某一直播券
     * @return
     */
    public ActiveUserLiveTicketTpResponse activeUserLiveTicket(String channel, String category, String season,
            String turn, String game, String logPrefix, CommonParam commonParam) {
        ActiveUserLiveTicketTpResponse response = null;
        StringBuilder url = new StringBuilder(VipTpConstant.LIVE_TICKET_URL);
        url.append("type=").append("useticket");
        url.append("&userid=").append(commonParam.getUserId());
        url.append("&channel=").append(channel);
        url.append("&category=").append(category);
        url.append("&season=").append(season);
        url.append("&turn=").append(turn);
        url.append("&game=").append(game);
        url.append("&sign=")
                .append(this.getCheckUserLiveTicketSign(commonParam.getUserId(), channel, category, season));
        url.append("&tickettype=1");
        try {
            // http://yuanxian.letv.com/letv/live.ldo?type=useticket&userid={userid}&channel={channel}&category={category}&season={season}&turn={turn}&game={game}&sign={sign}&tickettype=1
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                response = objectMapper.readValue(result, ActiveUserLiveTicketTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return response;
    }

    /**
     * 获取paypal支付二维码，用于引导用户到paypal移动APP上继续操作，完成绑定和支付操作
     * @param purchaseVipCommonRequest
     * @param locale
     * @return
     */
    public PaypalPaymentWithBindTpResponse purchaseVipByPaypal(PurchaseVipCommonRequest purchaseVipCommonRequest,
            Locale locale) {
        PaypalPaymentWithBindTpResponse conTp = null;
        String logPrefix = "purchaseVip_" + purchaseVipCommonRequest.getUsername() + "_"
                + purchaseVipCommonRequest.getUserid() + "_" + purchaseVipCommonRequest.getMac();
        try {
            // http://api.zhifu.letv.com/pay/mobile/39?corderid={corderid}&userid={userid}&buyType={buyType}&productid={productid}&pid={pid}&videoid={videoid}&price={price}&svip={svip}&sign={sign}&productnum={productnum}&deviceid={deviceid}&ip={ip}&subend={subend}&bindingtype={bindingtype}&addroverride={addroverride}&activityIds={activityIds}&coupons={couponCode}&companyid={companyid}&deptid={deptid}&fronturl={fronturl}&backurl={backurl}
            String result = this.restTemplate.getForObject(VipTpConstant.PURCHASE_VIP_PAYPAL_BIND_AND_PAY_URL,
                    String.class, purchaseVipCommonRequest.getParametersMap());
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                conTp = this.objectMapper.readValue(result, PaypalPaymentWithBindTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return conTp;
    }

    /**
     * 一键快捷支付
     * @param purchaseVipCommonRequest
     * @param locale
     * @return
     */
    public OneKeyQucikPayTpResponse purchaseVipByOneKeyQuickPay(PurchaseVipCommonRequest purchaseVipCommonRequest,
            Locale locale) {
        OneKeyQucikPayTpResponse conTp = null;
        String logPrefix = "purchaseVip_" + purchaseVipCommonRequest.getUsername() + "_"
                + purchaseVipCommonRequest.getUserid() + "_" + purchaseVipCommonRequest.getMac();
        try {
            // http://api.zhifu.letv.com/pay/mobile/49?corderid={corderid}&userid={userid}&buyType={buyType}&productid={productid}&pid={pid}&videoid={videoid}&price={price}&svip={svip}&sign={sign}&productnum={productnum}&deviceid={deviceid}&ip={ip}&subend={subend}&addroverride={addroverride}&activityIds={activityIds}&coupons={couponCode}&companyid={companyid}&deptid={deptid}&fronturl={fronturl}&backurl={backurl}
            String result = this.restTemplate.getForObject(VipTpConstant.PURCHASE_VIP_ONE_KEY_QUICK_PAY_URL,
                    String.class, purchaseVipCommonRequest.getParametersMap());
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                conTp = this.objectMapper.readValue(result, OneKeyQucikPayTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return conTp;
    }

    /**
     * 直播付费鉴权，查看当前用户是否已经购买过直播（是否有播放权限）
     * @return
     */
    public CheckLiveTpResponse tpsdkCheckLive(String pid, String liveId, String streamId, String splatId,
            Integer isstart, String logPrefix, CommonParam commonParam) {
        CheckLiveTpResponse response = null;
        StringBuilder url = new StringBuilder(VipTpConstant.TP_SDK_TICKET_LIVE_URL);
        url.append("pid=").append(pid);
        url.append("&liveid=").append(liveId);
        url.append("&from=").append(VipTpConstant.LIVE_CHECK_FROM_MAC);
        url.append("&streamId=").append(streamId);
        url.append("&splatId=").append(splatId);
        url.append("&mac=").append(commonParam.getMac());
        url.append("&type=").append("liveValidate");
        url.append("&isstart=").append(isstart);
        url.append("&sign=").append(this.getCheckLiveSign(commonParam.getUserId(), liveId));
        url.append("&callback=").append(StringUtils.EMPTY);
        try {
            // http://yuanxian.letv.com/letv/livemac.ldo?pid={pid}&liveid={liveid}&from={from}&streamId={streamId}&splatId={splatId}&mac={mac}&type={type}&isstart={isstart}&sign={sign}&callback={callback}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                response = this.objectMapper.readValue(result, CheckLiveTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return response;
    }

    /**
     * 从支付中心查询订单信息
     * @param corderId
     * @param logPrefix
     * @param commonParam
     * @return
     */
    public OrderStatusFromZhifuTpResponse checkTpSdkOrderStatus(String corderId, String logPrefix,
            CommonParam commonParam) {
        OrderStatusFromZhifuTpResponse response = null;
        StringBuilder url = new StringBuilder(VipTpConstant.TP_SDK_CHECK_ORDER_STATUS_URL);
        url.append("corderid=").append(corderId);
        url.append("&companyid=").append(VipTpConstant.DEFAULT_COMPANYID);
        url.append("&sign=").append(this.getCheckOrderStatusSign(corderId));
        try {
            // http://api.zhifu.letv.com/pay/querystat?corderid={corderid}&companyid={companyid}&sign={sign}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                response = this.objectMapper.readValue(result, OrderStatusFromZhifuTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return response;
    }

    /**
     * 激活未领取的超级手机赠送机卡绑定TV会员
     * @return
     */
    public ReceivePresentDeviceBindTpResponse receivePresentDeviceBind(String deviceKey, String presentId,
            String logPrefix, CommonParam commonParam) {
        ReceivePresentDeviceBindTpResponse tp = null;
        StringBuilder url = new StringBuilder(VipTpConstant.RECEIVE_PRESENT_DEVICE_BIND_URL);
        url.append("type=").append("receivePresentVip");
        url.append("&userId=").append(commonParam.getUserId());
        url.append("&presentType=").append(VipTpConstant.PRESENT_DEBICE_BIND_TYPE_TV);
        url.append("&presentDeviceKey=").append(deviceKey);
        url.append("&presentDeviceInfo=").append(commonParam.getMac());
        url.append("&id=").append(presentId);
        url.append("&sign=").append(
                this.getReceivePresentDeviceBindSign(deviceKey, commonParam.getMac(), commonParam.getUserId(),
                        presentId, VipTpConstant.PRESENT_DEBICE_BIND_TYPE_TV));
        try {
            // http://yuanxian.letv.com/letv/deviceBind.ldo?type=receivePresentVip&userId={userId}&presentType={presentType}&presentDeviceKey={presentDeviceKey}&presentDeviceInfo={presentDeviceInfo}&id={id}&sign={sign}
            String response = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + response + "]");

            if (response != null) {
                response = URLDecoder.decode(response, "UTF-8");
                tp = objectMapper.readValue(response, ReceivePresentDeviceBindTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error:", e);
        }

        return tp;
    }

    /**
     * get user vip info by userId,mac and deviceKey
     * @param commonParam
     * @return
     */
    public VipAccountTpResponse getVipAccount(CommonParam commonParam) {
        VipAccountTpResponse response = null;
        String logPrefix = "getVipAccount_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                + commonParam.getDeviceKey();

        try {
            StringBuilder url = new StringBuilder(VipTpConstant.VIP_GET_VIP_INFO_URL);
            url.append("from=").append("center");
            url.append("&userid=").append(commonParam.getUserId());
            url.append("&mac=").append(commonParam.getMac());
            url.append("&devicekey=").append(commonParam.getDeviceKey());
            long timestamp = System.currentTimeMillis();
            url.append("&timestamp=").append(timestamp);
            url.append("&busnissId=").append(VipTpConstant.BOSS_YUANXIAN_SAFE_BUSSINESS_ID);
            url.append("&sign=").append(getBossSafeSign(commonParam.getUserId(), timestamp));
            // need return sports vip info
            url.append("&needSport=").append(1);
            // http://yuanxian.letv.com/letv/getService.ldo?from=center&userid={userid}&mac={mac}&devicekey={deviceKey}&needSport={needSport}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                response = objectMapper.readValue(result, VipAccountTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return response;
    }

    /**
     * get user letvpoint balance
     * @param commonParam
     * @return
     */
    public GetLetvPointBalanceTpResponse getAccountLetvPointBalance(CommonParam commonParam) {
        GetLetvPointBalanceTpResponse response = null;
        String logPrefix = "getAccountLetvPointBalance_" + commonParam.getMac() + "_" + commonParam.getUserId();

        try {
            StringBuilder url = new StringBuilder(VipTpConstant.VIP_GET_USER_LETVPOINT_BALANCE_URL);
            url.append(commonParam.getUserId());
            url.append("?origin=").append("tv");// from tv terminal
            url.append("&auth=").append(this.getAccountLetvPointBalanceAuth(commonParam.getUserId()));
            // http://api.zhifu.letv.com/querylepoint/{userid}?origin=tv&auth={auth}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, GetLetvPointBalanceTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return response;
    }

    /**
     * 查询绑卡信息
     * @param getBindInfoRequest
     * @return
     */
    public GetBindInfoTpResponse getBindInfo(GetBindInfoRequest getBindInfoRequest) {
        GetBindInfoTpResponse response = null;
        String logPrefix = "getBindInfo_" + getBindInfoRequest.getUserid() + "_" + getBindInfoRequest.getCompanyid();

        try {
            // http://api.zhifu.letv.com/yeepay/getbindinfo?userid={userid}&companyid={companyid}&sign={sign}
            String url = VipTpConstant.GET_YEEPAY_GET_BIND_INFO_URL;
            String result = this.restTemplate.getForObject(url, String.class, getBindInfoRequest.getParametersMap());
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                response = objectMapper.readValue(result, GetBindInfoTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    /**
     * 查询用户是否可以一元绑卡
     * @return
     */
    public OneBindTpResponse getOneBind(String logPrefix, CommonParam commonParam) {
        OneBindTpResponse response = null;
        StringBuilder url = new StringBuilder(VipTpConstant.VIP_CHECK_ONE_YUAN_BIND_URL);
        url.append("userid=").append(commonParam.getUserId());

        try {
            // http://api.zhifu.letv.com/yeepay/issale?userid={userid}
            // String url = VipTpConstant.GET_YEEPAY_GET_ONE_BIND_URL;
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                response = objectMapper.readValue(result, OneBindTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    public VerificationCodeTpResponse yeepayVerificationCode(Integer operType, String ordernumber, String smscode,
            String logPrefix) {
        VerificationCodeTpResponse response = null;
        StringBuilder url = new StringBuilder();
        if (operType == null || operType < 1 || operType > 2) {
            return null;
        }
        if (operType == 1) {
            url.append(ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST + "/yeepay/sendmsg?");
            url.append("ordernumber=").append(ordernumber);
            url.append("&sign=").append(this.getVerificationCodeSign(operType, ordernumber, smscode));
        } else if (operType == 2) {
            url.append(ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST + "/yeepay/confirmpay?");
            url.append("ordernumber=").append(ordernumber);
            url.append("&sign=").append(this.getVerificationCodeSign(operType, ordernumber, smscode));
            url.append("&smscode=").append(smscode);
        }

        try {
            // 获取验证码：http://api.zhifu.letv.com/yeepay/sendmsg?ordernumber={ordernumber}&sign={sign}
            // 确认支付：http://api.zhifu.letv.com/yeepay/confirmpay?ordernumber={ordernumber}&sign={sign}&smscode={smscode}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                response = objectMapper.readValue(result, VerificationCodeTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return response;
    }

    /**
     * check letvcard if can use for recharge
     * @param cardNumber
     * @param commonParam
     * @return
     */
    public RechargeValidateResult isCardSecretExisting(String cardNumber, CommonParam commonParam) {
        RechargeValidateResult response = null;
        String logPrefix = "isCardSecretExisting_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                + cardNumber;
        try {
            StringBuilder url = new StringBuilder(VipTpConstant.VIP_LETVCARD_VALIDATE_URL);
            url.append("cardtype=").append("-1");
            url.append("&cardnumber=").append(cardNumber);
            url.append("&mac=").append(commonParam.getMac());
            url.append("&deviceKey=").append(commonParam.getDeviceKey());
            url.append("&userid=").append(commonParam.getUserId());
            // http://api.zhifu.letv.com/recharge/validate?cardtype=-1&cardnumber={cardnumber}&mac={mac}&deviceKey={deviceKey}&userid={userid}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, RechargeValidateResult.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    public RechargeResult rechargeByLetvCardPasswd(String cardNumber, CommonParam commonParam) {
        RechargeResult response = new RechargeResult();
        String logPrefix = "rechargeByLetvCardPasswd_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                + cardNumber;

        try {
            StringBuilder url = new StringBuilder(VipTpConstant.VIP_RECHARGE_LETVCARD_URL);
            url.append("buyType=").append(0);
            url.append("&cardnumber=").append(cardNumber);
            url.append("&chargetype=").append(1);
            url.append("&companyid=").append(1);
            url.append("&corderid=").append(0);
            url.append("&productdesc=").append("充值乐点");
            url.append("&productname=").append("充值乐点");
            url.append("&userid=").append(commonParam.getUserId());
            url.append("&username=").append(commonParam.getUsername());
            url.append("&backurl=").append(ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST).append("/tobuylepoint");
            url.append("&deptid=").append(VipTpConstant.DEFAULT_DEPTID);
            url.append("&subend=").append(
                    this.getLetvCardSubend(commonParam.getTerminalSeries(), commonParam.getTerminalBrand()));
            url.append("&mac=").append(commonParam.getMac());
            url.append("&deviceKey=").append(commonParam.getDeviceKey());
            // http://api.zhifu.letv.com/lecardrecharge?buyType={buyType}&cardnumber={cardnumber}&chargetype={chargetype}&companyid={companyid}&corderid={corderid}&productdesc={productdesc}&productname={productname}&userid={userid}&username={username}&backurl={backurl}&deptid={deptid}&subend={subend}&mac={mac}&deviceKey={deviceKey}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, RechargeResult.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    /**
     * 获取用户的定向弹窗信息
     * @param getRecommendPopInfoRequest
     * @return
     */
    public GetRecommendPopInfoTpResponse getRecommendPopInfo(GetRecommendPopInfoRequest getRecommendPopInfoRequest,
            String logPrefix) {
        GetRecommendPopInfoTpResponse response = null;

        try {
            // http://yuanxian.letv.com/letv/recommendPop.ldo?type={type}&userId={userId}&terminal={terminal}&devicekey={deviceKey}&mac={mac}
            String url = VipTpConstant.VIP_GET_RECOMMENDPOP_URL;
            String result = this.restTemplate.getForObject(url, String.class,
                    getRecommendPopInfoRequest.getParametersMap());
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, GetRecommendPopInfoTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return response;
    }

    /**
     * 判断用户是否是某个会员类型
     * @param userId
     *            用户id
     * @param vipType
     *            5--包年会员
     */
    public GetVipTypeTpResponse getVipType(GetVipTypeRequest getVipTypeRequest, String logPrefix) {
        GetVipTypeTpResponse response = new GetVipTypeTpResponse();
        try {
            // http://yuanxian.letv.com/letv/vip.ldo?type=vipType&vipType={vipType}&userId={userId}&mac={mac}&devicekey={deviceKey}
            String url = VipTpConstant.VIP_GET_VIPTYPE_URL;
            String result = this.restTemplate.getForObject(url, String.class, getVipTypeRequest.getParametersMap());
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, GetVipTypeTpResponse.class);
            }
            LOG.info(logPrefix + ": invoke return [" + result + "]");
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    public String getPurchaseProductSign(String corderid, String userId, Long pid, String price, String deptid,
            Integer buyType) {
        StringBuilder sign = new StringBuilder();
        // String secKey = "ce0806981627d00d4b96beb051a2b629";
        if (buyType != null && buyType == 1) {
            // 单片加密串
            sign.append("corderid=").append(corderid).append("&");
            sign.append("userid=").append(userId).append("&");
            sign.append("price=").append(price).append("&");
            sign.append("companyid=").append(1).append("&");
            sign.append(VipTpConstant.BOSS_API_ZHIFU_COMMON_SING_KEY).append("&");
            sign.append("deptid=").append(deptid).append("&");
            sign.append("pid=").append(pid);
        } else {
            // 套餐加密串
            sign.append("corderid=").append(corderid).append("&");
            sign.append("userid=").append(userId).append("&");
            sign.append("companyid=").append(1).append("&");
            sign.append(VipTpConstant.BOSS_API_ZHIFU_COMMON_SING_KEY).append("&");
            sign.append("deptid=").append(deptid).append("&");
            sign.append("pid=").append(pid);
        }
        try {
            return MessageDigestUtil.md5(sign.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            LOG.error("ConsumeVipRequest_" + "_" + userId + "_" + corderid + ": getSign error.", e);
        }
        return "";
    }

    public String getDeviceBindSign(String deviceKey, String mac, Integer deviceType) {
        // 根据规则sign=md5(deviceKey=**&mac=** &deviceType=**&{key})计算出sign
        try {
            StringBuilder signBuilder = new StringBuilder();
            signBuilder.append("deviceKey=").append(deviceKey).append("&");
            signBuilder.append("mac=").append(mac).append("&");
            signBuilder.append("deviceType=").append(deviceType).append("&").append(VipTpConstant.DEVICE_BIND_SIGN_KEY);
            return MessageDigestUtil.md5(signBuilder.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            LOG.error("getDeviceBindSign_error", e.getMessage(), e);
        }
        return "";
    }

    public String getPresentDeviceBindSign(String presentDeviceKey, String presentDeviceInfo, String userId,
            Integer presentType) {
        // 签名规则sign=md5(presentDeviceKey=**&mac=**&userId=%s&presentType=%s&{key})
        StringBuilder signBuilder = new StringBuilder();
        signBuilder.append("presentDeviceKey=").append(presentDeviceKey).append("&").append("mac=")
                .append(presentDeviceInfo).append("&").append("userId=").append(userId).append("&")
                .append("presentType=").append(presentType).append("&").append(VipTpConstant.DEVICE_BIND_SIGN_KEY);
        try {
            return MessageDigestUtil.md5(signBuilder.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            LOG.error("getPresentDeviceBindSign_error", e.getMessage(), e);
        }
        return "";
    }

    /**
     * 签名，签名规则：sign_key=f8da39f11dbdafc03efa1ad0250c9ae6，
     * sign=MD5(liveid+sign_key+userId)
     */
    public String getCheckLiveSign(String userId, String liveId) {
        StringBuilder sign = new StringBuilder();
        if (StringUtils.isBlank(userId)) {
            sign.append(liveId).append(VipTpConstant.LIVE_CHECK_SIGN_KEY);
        } else {
            sign.append(liveId).append(VipTpConstant.LIVE_CHECK_SIGN_KEY).append(userId);
        }
        try {
            return MessageDigestUtil.md5(sign.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            LOG.error("getCheckLiveSign_error", e.getMessage(), e);
        }
        return "";
    }

    /**
     * sign生成规则 sign_key= f8da39f11dbdafc03efa1ad0250c9ae6，
     * MD5(userid + channel+category+season+sign_key)
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public String getCheckUserLiveTicketSign(String userId, String channel, String category, String season) {
        StringBuilder sign = new StringBuilder();
        sign.append(userId).append(channel).append(category).append(season).append(VipTpConstant.LIVE_CHECK_SIGN_KEY);
        try {
            return MessageDigestUtil.md5(sign.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            LOG.error("getCheckUserLiveTicketSign_error", e.getMessage(), e);
        }
        return "";
    }

    private String getCheckOneKeyPaySign(String userId, Integer paymentChannel) {
        StringBuilder sign = new StringBuilder();
        sign.append("userid=").append(userId).append("&paytype=").append(paymentChannel).append("&")
                .append(VipTpConstant.BOSS_API_ZHIFU_COMMON_SING_KEY);
        try {
            return MessageDigestUtil.md5(sign.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            LOG.error("getCheckOneKeyPaySign_error", e.getMessage(), e);
        }
        return "";
    }

    /**
     * 获取签名字符串，签名规则：corderid={corderid}&{signedSecKey}&companyid={companyid}
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private String getCheckOrderStatusSign(String orderId) {
        StringBuilder sign = new StringBuilder();
        sign.append("corderid=").append(orderId).append("&").append(VipTpConstant.BOSS_API_ZHIFU_COMMON_SING_KEY)
                .append("&companyid=").append(VipTpConstant.DEFAULT_COMPANYID);
        try {
            return MessageDigestUtil.md5(sign.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            LOG.error("getCheckOrderStatusSign_error", e.getMessage(), e);
        }
        return "";
    }

    public String getReceivePresentDeviceBindSign(String deviceKey, String mac, String userId, String presentId,
            Integer presentType) {
        // 签名规则sign=md5(presentDeviceKey=**&mac=**&userid=**&presentType=**&id=**&{key})
        StringBuilder signBuilder = new StringBuilder();
        signBuilder.append("presentDeviceKey=").append(deviceKey).append("&").append("mac=").append(mac).append("&")
                .append("userId=").append(userId).append("&").append("presentType=").append(presentType).append("&")
                .append("id=").append(presentId).append("&").append(VipTpConstant.DEVICE_BIND_SIGN_KEY);
        try {
            return MessageDigestUtil.md5(signBuilder.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            LOG.error("getReceivePresentDeviceBindSign_error", e.getMessage(), e);
        }
        return "";
    }

    private static final Map<String, Integer> brand_map = new HashMap<String, Integer>();
    private static final Map<String, Integer> series_map = new HashMap<String, Integer>();

    static {
        brand_map.put("hisense", 0);// 海信
        brand_map.put("haier", 1);// 海尔
        brand_map.put("skyworth", 2);// 创维
        brand_map.put("tcl", 3);// TCL
        brand_map.put("konka", 4);// 康佳
        brand_map.put("tongfang", 5);// 同方
        brand_map.put("panda", 9);// 熊猫
        brand_map.put("xiaomi", 10);// 小米
        brand_map.put("tpv", 11);// 冠捷
        brand_map.put("changhong", 8);// 长虹

        series_map.put("msm8960", 50);// X60
        series_map.put("letvx60", 50);// X60
        series_map.put("Android TV on MStar Amber3 S50".toLowerCase(), 51);// S50
        series_map.put("Android TV on MStar Amber3".toLowerCase(), 52);// S40
        series_map.put("Android TV on MStar Amber3 S40".toLowerCase(), 52);// S40
        series_map.put("epg", 53);// S10
        series_map.put("Hi3716C".toLowerCase(), 54);// ST1
        series_map.put("AMLOGIC8726MX".toLowerCase(), 55);// C1
        series_map.put("AMLOGIC8726MX_C1S".toLowerCase(), 56);// C1s
        series_map.put("AMLOGIC8726MX_C1S_UI_2".toLowerCase(), 56);// C1s
    }

    private String getLetvCardSubend(String terminalSeries, String terminalBrand) {
        Integer result = null;
        if (terminalSeries != null && terminalBrand != null && "letv".equalsIgnoreCase(terminalBrand)) {
            result = series_map.get(terminalSeries.toLowerCase());
        } else if (terminalBrand != null) {
            result = brand_map.get(terminalBrand.toLowerCase());
        }
        return result == null ? "" : result + "";
    }

    /**
     * 签名
     * @return
     */
    private String getVerificationCodeSign(Integer operType, String ordernumber, String smscode) {
        StringBuilder sb = new StringBuilder();
        if (operType == null) {
            return "";
        }
        if (operType == 1) {
            // 下发验证码时，签名串ordernumber=xxx&{key}
            sb.append("ordernumber=").append(ordernumber).append("&")
                    .append(VipTpConstant.BOSS_API_ZHIFU_COMMON_SING_KEY);
        } else if (operType == 2) {
            // 确认支付时，签名串smscode=xxx&ordernumber=xxx&{key}&companyid=1
            sb.append("smscode=").append(smscode).append("&ordernumber=").append(ordernumber).append("&")
                    .append(VipTpConstant.BOSS_API_ZHIFU_COMMON_SING_KEY).append("&companyid=1");
        }
        try {
            return MessageDigestUtil.md5(sb.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            LOG.error("getVerificationCodeSign_error", e.getMessage(), e);
        }
        return "";
    }

    private String getUserBindSign(String userId, String deviceKey, String mac, Integer deviceType) {
        // 根据规则sign=md5(deviceKey=**&mac=**&userid=**&deviceType=**&{key})计算出sign
        try {
            StringBuilder signBuilder = new StringBuilder();
            signBuilder.append("deviceKey=").append(deviceKey).append("&");
            signBuilder.append("mac=").append(mac).append("&");
            signBuilder.append("userid=").append(userId).append("&");
            signBuilder.append("deviceType=").append(deviceType).append("&").append(VipTpConstant.DEVICE_BIND_SIGN_KEY);
            return MessageDigestUtil.md5(signBuilder.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            LOG.error("getUserBindSign_error", e.getMessage(), e);
        }
        return "";
    }

    /**
     * generate md5 sign
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private String getAccountLetvPointBalanceAuth(String userId) {
        try {
            return MessageDigestUtil.md5((VipTpConstant.BOSS_API_ZHIFU_SIGN_KEY + userId).toString().getBytes(
                    Charset.forName("UTF-8")));
        } catch (Exception e) {
            LOG.error("getAccountLetvPointBalanceAuth_error", e.getMessage(), e);
        }
        return "";
    }

    /**
     * 检查手机支付套餐信息
     * http://tes.touch.my.letv.com/activity/checkuser
     * @param phone
     *            手机号
     * @param ip
     *            用户IP地址
     * @param locale
     *            调用第三方环境locale
     * @return
     */
    public TouchTpResponse getTouchData(String uid, String id, String ip, String devid) {
        TouchTpResponse checkTp = null;
        String logPrefix = "getTouch_" + uid + "_" + id + "_" + ip + "_" + devid;
        MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
        parametersMap.add("uid", uid);
        parametersMap.add("id", id);
        parametersMap.add("ip", ip);
        parametersMap.add("devid", devid);
        try {
            String result = this.restTemplate.postForObject(VipTpConstant.TOUCH_USER_URL, parametersMap, String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                checkTp = objectMapper.readValue(result, TouchTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return checkTp;
    }

    /**
     * get activity data from vipcenter
     * @param position
     * @param deviceType
     * @param commonParam
     * @return
     */
    public GetVipCenterActivityResponse getVipCenterActivity(String position, Integer deviceType,
            CommonParam commonParam) {
        GetVipCenterActivityResponse response = null;
        MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
        parametersMap.add("uid", commonParam.getUserId());
        parametersMap.add("position", position);// The activity position
        parametersMap.add("mac", commonParam.getMac());
        parametersMap.add("devicekey", commonParam.getDeviceKey());
        // distinguish from what device type
        parametersMap.add("deviceType", VipTpConstant.deviceType.get(String.valueOf(deviceType)));
        try {
            // http://tes.touch.my.letv.com/activity/getdata
            String result = this.restTemplate.postForObject(VipTpConstant.GET_VIPCENTER_DATA, parametersMap,
                    String.class);
            LOG.info("getVipCenterActivity_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + commonParam.getDeviceKey() + "_" + position + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, GetVipCenterActivityResponse.class);
            }
        } catch (Exception e) {
            LOG.error("getVipCenterActivity_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + commonParam.getDeviceKey() + "_" + position + " return error: ", e);
        }
        return response;
    }

    /**
     * get urm touch data
     * @param position
     * @param deviceType
     * @param commonParam
     * @return
     */
    public GetUrmActivityResponse getUrmTouchData(String position, Integer deviceType, CommonParam commonParam) {
        GetUrmActivityResponse response = null;
        try {
            // MultiValueMap<String, String> parametersMap = new
            // LinkedMultiValueMap<String, String>();
            // parametersMap.add("userId", commonParam.getUserId());// user id
            // parametersMap.add("deviceId", commonParam.getMac());// machine
            // mac
            // parametersMap.add("touchSpotId", position);// The activity
            // position
            // if (StringUtil.isNotBlank(commonParam.getDeviceKey())) {
            // parametersMap.add("devicekey",
            // StringUtils.trimToEmpty(commonParam.getDeviceKey()));
            // }
            // // distinguish from what device type
            // parametersMap.add("deviceType",
            // VipTpConstant.deviceType.get(String.valueOf(deviceType)));
            // parametersMap.add("clientKey",
            // VipTpConstant.URM_ACTIVITY_CLIENT_KEY);

            StringBuilder url = new StringBuilder(VipTpConstant.GET_URM_TOUCH_DATA);
            url.append("?userId=").append(StringUtils.trimToEmpty(commonParam.getUserId()));
            url.append("&deviceId=").append(StringUtils.trimToEmpty(commonParam.getMac()));
            url.append("&touchSpotId=").append(position);
            url.append("&devicekey=").append(StringUtils.trimToEmpty(commonParam.getDeviceKey()));
            url.append("&deviceType=").append(VipTpConstant.deviceType.get(String.valueOf(deviceType)));
            url.append("&clientKey=").append(VipTpConstant.URM_ACTIVITY_CLIENT_KEY);
            if ((commonParam.getBroadcastId() != null) && (commonParam.getBroadcastId() == CommonConstants.CIBN)) {
                url.append("&sarft=true");// 国广版本传true，非国广版本不传或者传false
            }
            // url.append("&dryrun=true");// 加上此参数则没有频次控制

            // http://api.message.le.com/v1/messages
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            // LOG.info("getUrmTouchData_" + commonParam.getMac() + "_" +
            // commonParam.getUserId() + "_"
            // + commonParam.getDeviceKey() + "_" + position +
            // ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = result.replaceAll("\\[\\]", "null");
                response = objectMapper.readValue(result, GetUrmActivityResponse.class);
            }
        } catch (Exception e) {
            LOG.info(
                    "getUrmTouchData_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                            + commonParam.getDeviceKey() + "_" + position + " return error: ", e);
        }
        return response;
    }

    /**
     * get activity info of member IOUs
     * @param activeScene
     *            from where to participate activity
     * @param commonParam
     * @return
     */
    public ActiveFreeVipTpResponse getFreeVipInfo(Integer activeScene, CommonParam commonParam) {
        ActiveFreeVipTpResponse response = null;
        MultiValueMap<String, Object> parametersMap = new LinkedMultiValueMap<String, Object>();
        parametersMap.add("uid", commonParam.getUserId());
        parametersMap.add("username", commonParam.getUsername());
        parametersMap.add("activescene", String.valueOf(activeScene));
        // StringBuilder url = new
        // StringBuilder(VipTpConstant.VIP_GET_FREEVIP_URL);
        // url.append("?").append("uid=").append(commonParam.getUserId());
        // url.append("&username=").append(commonParam.getUsername());
        // url.append("&activescene=").append(activeScene);
        try {
            // http://tes.touch.my.letv.com/activity/freesb?uid={uid}&username={username}&activescene={activescene}
            String result = this.restTemplate.postForObject(VipTpConstant.VIP_GET_FREEVIP_URL, parametersMap,
                    String.class);
            LOG.info("activeFreeVip_" + commonParam.getMac() + "_" + commonParam.getUserId() + ": invoke return ["
                    + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, ActiveFreeVipTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error("activeFreeVip_" + commonParam.getMac() + "_" + commonParam.getUserId() + " return error: ", e);
        }
        return response;
    }

    /**
     * active member IOUs service
     * @param activeScene
     *            from where to participate activity
     * @param commonParam
     * @return
     */
    public ActiveFreeVipTpResponse activeFreeVip(Integer activeScene, CommonParam commonParam) {
        ActiveFreeVipTpResponse response = null;
        MultiValueMap<String, Object> parametersMap = new LinkedMultiValueMap<String, Object>();
        parametersMap.add("uid", commonParam.getUserId());
        parametersMap.add("username", commonParam.getUsername());
        parametersMap.add("activescene", String.valueOf(activeScene));
        // StringBuilder url = new
        // StringBuilder(VipTpConstant.VIP_ACTIVE_FREEVIP_URL);
        // url.append("?").append("uid=").append(commonParam.getUserId());
        // url.append("&username=").append(commonParam.getUsername());
        // url.append("&activescene=").append(activeScene);
        try {
            // http://tes.touch.my.letv.com/activity/freewatch?uid={uid}&username={username}&activescene={activescene}
            String result = this.restTemplate.postForObject(VipTpConstant.VIP_ACTIVE_FREEVIP_URL, parametersMap,
                    String.class);
            LOG.info("activeFreeVip_" + commonParam.getMac() + "_" + commonParam.getUserId() + ": invoke return ["
                    + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, ActiveFreeVipTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error("activeFreeVip_" + commonParam.getMac() + "_" + commonParam.getUserId() + " return error: ", e);
        }

        return response;
    }

    /**
     * get urm touch data list
     * @param position
     *            请使用touchSpotId,touchSpotId,touchSpotId...的形式传递多个touchSpotId
     * @param deviceType
     * @param commonParam
     * @return
     */
    public GetUrmActivityBatchResponse getUrmTouchDataList(String position, Integer deviceType, CommonParam commonParam) {
        GetUrmActivityBatchResponse response = null;
        try {
            StringBuilder url = new StringBuilder(VipTpConstant.GET_URM_TOUCH_DATA_LIST);
            url.append("?userId=").append(StringUtils.trimToEmpty(commonParam.getUserId()));
            url.append("&deviceId=").append(StringUtils.trimToEmpty(commonParam.getMac()));
            url.append("&touchSpotIds=").append(position);
            url.append("&devicekey=").append(StringUtils.trimToEmpty(commonParam.getDeviceKey()));
            url.append("&deviceType=").append(VipTpConstant.deviceType.get(String.valueOf(deviceType)));
            url.append("&clientKey=").append(VipTpConstant.URM_ACTIVITY_CLIENT_KEY);
            // url.append("&dryrun=true");//压测时添加此参数可以不限频次

            // http://api.message.le.com/v1/batch/messages
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            // LOG.info("getUrmTouchData_" + commonParam.getMac() + "_" +
            // commonParam.getUserId() + "_"
            // + commonParam.getDeviceKey() + "_" + position +
            // ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = result.replaceAll("\\[\\]", "null");
                response = objectMapper.readValue(result, GetUrmActivityBatchResponse.class);
            }
        } catch (Exception e) {
            LOG.info(
                    "getUrmTouchDataList_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                            + commonParam.getDeviceKey() + "_" + position + " return error: ", e);
        }
        return response;
    }

    /**
     * 获取免费会员签名
     * @param channel
     * @param currentTime
     * @return
     */
    private String getFreeVipSign(Integer channel, Long currentTime, String key) {
        // MD5(channel + t + secret)
        try {
            StringBuilder signBuilder = new StringBuilder();
            signBuilder.append(channel).append(currentTime).append(key);
            return MessageDigestUtil.md5(signBuilder.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            LOG.error("getFreeVipSign_error", e.getMessage(), e);
        }
        return "";
    }

    /**
     * BOSS风险接口添加签名
     * @param userId
     * @return
     */
    private String getBossSafeSign(String userId, long timestamp) {
        StringBuilder sb = new StringBuilder();
        sb.append(userId).append(timestamp).append(VipTpConstant.BOSS_YUANXIAN_SAFE_SIGN_KEY);
        try {
            return MessageDigestUtil.md5(sb.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            LOG.error("getBossSafeSign_error", e.getMessage(), e);
        }
        return "";
    }

    // http://us.yuanxian.letv.com/letv/v2/deviceBind.ldo?type=queryByDevice&deviceKey={deviceKey}&mac={mac}&sign={sign}
    public BossTpResponse<DeviceBindConentV2> getDeviceBindInfoV2(Integer deviceType, CommonParam commonParam) {
        BossTpResponse<DeviceBindConentV2> response = null;
        String logPrefix = "getDeviceBindInfo_" + commonParam.getMac() + "_" + deviceType + "_"
                + commonParam.getDeviceKey();
        try {
            StringBuilder url = new StringBuilder(VipTpConstant.BOSS_YUANXIAN_V2_DEVICE_BIND_BASE_URL);
            StringBuilder params = new StringBuilder();
            params.append("type=queryByDevice");
            params.append("&deviceKey=").append(commonParam.getDeviceKey());
            params.append("&mac=").append(commonParam.getMac());
            params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
            // url.append("&deviceType=").append(deviceType);
            String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
            params.append("&sign=").append(sign);
            url.append(params);
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new LetvTypeReference<BossTpResponse<DeviceBindConentV2>>() {
                });
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error:", e);
        }

        return response;
    }

    /**
     * 生成支付时传给支付sdk的签名
     * @param extendParam
     * @param commonParam
     * @return
     */
    public String getPurchaseVipSign(ExtendParam extendParam, CommonParam commonParam) {
        if (extendParam == null) {
            return "";
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("version", extendParam.getVersion());
        params.put("service", extendParam.getService());
        params.put("merchant_business_id", extendParam.getMerchant_business_id());
        params.put("user_id", extendParam.getUser_id());
        params.put("user_name", extendParam.getDisplayName());
        params.put("notify_url", extendParam.getNotify_url());
        params.put("call_back_url", CommonUtil.urlDecode(extendParam.getCall_back_url())); // 测试,先解码再加密
        params.put("merchant_no", extendParam.getMerchant_no());
        params.put("out_trade_no", extendParam.getOut_trade_no());
        params.put("price", extendParam.getPrice());
        params.put("currency", extendParam.getCurrency());
        params.put("pay_expire", extendParam.getPay_expire());
        params.put("product_id", extendParam.getProduct_id());
        params.put("product_name", extendParam.getProduct_name());
        params.put("product_desc", extendParam.getProduct_desc());
        params.put("product_urls", extendParam.getProduct_urls());
        params.put("timestamp", extendParam.getTimestamp());
        params.put("key_index", extendParam.getIndex());
        params.put("input_charset", extendParam.getInput_charset());
        params.put("ip", extendParam.getIp());
        params.put("sign_type", extendParam.getSign_type());

        String secrectKey; // 签名key
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LETV_COMMON.equals(commonParam.getTerminalApplication())) {
            secrectKey = VipTpConstant.VIP_LETV_COMMON_ZHIFU_SIGN_KEY;
        } else {
            secrectKey = "55bc6cb95344ed2a1e576fe267aaa602";
        }
        List<String> listStr = new ArrayList<String>();
        String md5 = "";
        String key_value = "";
        Iterator<String> it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            if (params.get(key) == null) { // 过滤掉value为null的数据
                continue;
            }
            String value = params.get(key) + "";
            if (StringUtil.isBlank(value)) { // 过滤掉value为空的数据
                continue;
            }
            listStr.add(key + "=" + value);
        }
        Collections.sort(listStr); // 按字典序升序排列
        for (String str : listStr) { // 拼接成key1=value1&key2=value2的形式
            key_value += str + "&";
        }
        String md5str = key_value + "key=" + secrectKey;// 最后将key拼接上
        try {
            md5 = MD5Util.md5(md5str);
            LOG.info("getPurchaseVipSign signurl:" + md5str + " md5:" + md5);
        } catch (Exception e) {
            LOG.error("getPurchaseVipSign error", e.getMessage(), e);
        }
        return md5;
    }

    /**
     * 根据会员类型，获取会员信息列表接口
     * @param typeGroup
     *            会员类型，见{@link VipTpConstant.Type_Group_US}
     * @param commonParam
     * @return
     */
    public BossTpResponse<List<Vip>> getVipListByType(Integer typeGroup, CommonParam commonParam) {
        BossTpResponse<List<Vip>> response = null;
        if (typeGroup != null && typeGroup > 0) {
            try {
                String type = "vipListByType";
                long timestamp = System.currentTimeMillis();
                StringBuilder subUrl = new StringBuilder();
                subUrl.append(VipTpConstant.GET_VIP_LIST_BY_TYPE_URL()).append("?");
                StringBuilder params = new StringBuilder();
                params.append("type=").append(type);
                VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
                if (country != null) {
                    params.append("&country=").append(country.getCode());
                }
                params.append("&typeGroup=").append(typeGroup);
                params.append("&timestamp=").append(timestamp);
                params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
                String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam)); // 获取签名
                params.append("&sign=").append(sign);
                subUrl.append(params);
                String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, new LetvTypeReference<BossTpResponse<List<Vip>>>() {
                    });
                }
            } catch (Exception e) {
                LOG.error("getVipListByType_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + commonParam.getDeviceKey() + "_" + typeGroup + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 根据会员id，获取会员信息列表接口(批量)
     * @param productIds
     *            会员idlist
     * @param commonParam
     * @return
     */
    public BossTpResponse<List<Vip>> getVipListByIds(List<String> productIds, CommonParam commonParam) {
        BossTpResponse<List<Vip>> response = null;
        if (productIds != null && productIds.size() > 0) {
            try {
                String type = "productsByIds";
                StringBuilder subUrl = new StringBuilder();
                subUrl.append(VipTpConstant.GET_PRODUCT_BY_ID()).append("?");
                StringBuilder params = new StringBuilder();
                params.append("type=").append(type);
                VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
                if (country != null) {
                    params.append("&country=").append(country.getCode());
                }

                StringBuffer pidsSB = new StringBuffer();
                for (String pid : productIds) {
                    if (pid != null) {
                        pidsSB.append(pid).append(",");
                    }
                }

                params.append("&productIds=").append(pidsSB.substring(0, pidsSB.length() - 1));

                params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
                String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam)); // 获取签名
                params.append("&sign=").append(sign);
                subUrl.append(params);
                String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, new LetvTypeReference<BossTpResponse<List<Vip>>>() {
                    });
                }
            } catch (Exception e) {
                LOG.error(
                        "getVipListByIds_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                                + commonParam.getDeviceKey() + "_" + productIds + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 根据会员id，获取会员信息
     * @param productId
     *            会员id
     * @param commonParam
     * @return
     */
    public BossTpResponse<Vip> getProductById(Integer productId, CommonParam commonParam) {
        BossTpResponse<Vip> response = null;
        if (productId != null) {
            try {
                String type = "productById";
                StringBuilder subUrl = new StringBuilder();
                subUrl.append(VipTpConstant.GET_PRODUCT_BY_ID()).append("?");
                StringBuilder params = new StringBuilder();
                params.append("type=").append(type);
                VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
                if (country != null) {
                    params.append("&country=").append(country.getCode());
                }
                params.append("&productId=").append(productId);
                params.append("&businessId=").append(this.getBussinessIdV2(commonParam)); // 业务线id
                String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam)); // 获取签名
                params.append("&sign=").append(sign);
                subUrl.append(params);
                String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, new LetvTypeReference<BossTpResponse<Vip>>() {
                    });
                }
            } catch (Exception e) {
                LOG.error(
                        "getProductById_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                                + commonParam.getDeviceKey() + "_" + productId + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 根据会员id，获取对应的“售卖套餐”接口
     * @param productId
     *            会员id
     * @param commonParam
     * @return
     */
    public BossTpResponse<List<VipPackage>> getPackageByProductId(Integer productId, CommonParam commonParam) {
        BossTpResponse<List<VipPackage>> response = null;
        if (productId != null) {
            try {
                String type = "packageByProductId";
                StringBuilder subUrl = new StringBuilder();
                subUrl.append(VipTpConstant.GET_PACKAGE_BY_PRODUCT_ID()).append("?");
                StringBuilder params = new StringBuilder();
                params.append("type=").append(type);
                VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
                if (country != null) {
                    params.append("&country=").append(country.getCode());
                }
                params.append("&productId=").append(productId);
                params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
                String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
                params.append("&sign=").append(sign);
                subUrl.append(params);
                String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result,
                            new LetvTypeReference<BossTpResponse<List<VipPackage>>>() {
                            });
                }
            } catch (Exception e) {
                LOG.error("getPackageByProductId_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + commonParam.getDeviceKey() + "_" + productId + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 根据套餐id，获取套餐信息
     * @param packageId
     *            套餐id
     * @param commonParam
     * @return
     */
    public BossTpResponse<VipPackage> getPackageById(Integer packageId, CommonParam commonParam) {
        BossTpResponse<VipPackage> response = null;
        if (packageId != null) {
            try {
                String type = "packageById";
                StringBuilder subUrl = new StringBuilder();
                subUrl.append(VipTpConstant.GET_PACKAGE_BY_ID()).append("?");
                StringBuilder params = new StringBuilder();
                params.append("type=").append(type);
                VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
                if (country != null) {
                    params.append("&country=").append(country.getCode());
                }
                params.append("&packageId=").append(packageId);
                params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
                String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
                params.append("&sign=").append(sign);
                subUrl.append(params);
                String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, new LetvTypeReference<BossTpResponse<VipPackage>>() {
                    });
                }
            } catch (Exception e) {
                LOG.error("getPackageByProductId_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + commonParam.getDeviceKey() + "_" + packageId + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 根据套餐id，获取套餐信息(批量)
     * @param packageId
     *            套餐id
     * @param commonParam
     * @return
     */
    public BossTpResponse<List<VipPackage>> getPackageByIds(List<Integer> packageIds, CommonParam commonParam) {
        BossTpResponse<List<VipPackage>> response = null;
        if (packageIds != null && packageIds.size() > 0) {
            try {
                String type = "packageByIds";
                StringBuilder subUrl = new StringBuilder();
                subUrl.append(VipTpConstant.GET_PACKAGE_BY_ID()).append("?");
                StringBuilder params = new StringBuilder();
                params.append("type=").append(type);
                VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
                if (country != null) {
                    params.append("&country=").append(country.getCode());
                }
                StringBuffer pidsSB = new StringBuffer();
                for (Integer pid : packageIds) {
                    if (pid != null) {
                        pidsSB.append(pid.toString()).append(",");
                    }
                }
                params.append("&packageIds=").append(pidsSB.substring(0, pidsSB.length() - 1));
                params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
                String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
                params.append("&sign=").append(sign);
                subUrl.append(params);
                String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result,
                            new LetvTypeReference<BossTpResponse<List<VipPackage>>>() {
                            });
                }
            } catch (Exception e) {
                LOG.error("getPackageByProductIds_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + commonParam.getDeviceKey() + "_" + packageIds + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 根据套餐id，获取对应内容包信息
     * @param packageId
     * @param page
     * @param pageSize
     * @param commonParam
     * @return
     */
    public BossTpResponse<ContentPackage> getPackageInfoById(Integer packageId, Integer page, Integer pageSize,
                                                             CommonParam commonParam) {
        BossTpResponse<ContentPackage> response = null;
        if (packageId != null) {
            try {
                String type = "packageInfo";
                StringBuilder subUrl = new StringBuilder();
                subUrl.append(VipTpConstant.GET_PACKAGE_INFO_BY_ID()).append("?");
                StringBuilder params = new StringBuilder();
                params.append("type=").append(type);
                VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
                if (country != null) {
                    params.append("&country=").append(country.getCode());
                }
                params.append("&userId=").append(commonParam.getUserId());
                params.append("&packageId=").append(packageId);
                if (page != null) {
                    params.append("&page=").append(page);
                }
                if (pageSize != null) {
                    params.append("&rows=").append(pageSize);
                }
                params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
                String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
                params.append("&sign=").append(sign);
                subUrl.append(params);
                String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, new LetvTypeReference<BossTpResponse<ContentPackage>>() {
                    });
                }
            } catch (Exception e) {
                LOG.error("getPackageInfoById_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + commonParam.getDeviceKey() + "_" + packageId + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 获取会员状态时长信息
     * @param terminal
     *            终端类型,见{@link VipTpConstant.BossTerminalType}
     * @param commonParam
     *            公共参数,其中的userId不能为空
     * @return
     */
    public BossTpResponse<List<SubscribeInfo>> getVipInfo(Integer terminal, CommonParam commonParam) {
        if (StringUtil.isBlank(commonParam.getUserId())) {
            return null;
        }
        BossTpResponse<List<SubscribeInfo>> response = null;
        try {
            String type = "info";
            final long timestamp = System.currentTimeMillis();
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(VipTpConstant.GET_PACKAGE_INFO_BY_ID()).append("?");
            StringBuilder params = new StringBuilder();
            params.append("type=").append(type);
            VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
            if (country != null) {
                params.append("&country=").append(country.getCode());
            }
            params.append("&userId=").append(commonParam.getUserId());

            if (MmsDataUtil.existPayPlatform(this.sessionCache)) {
                params.append("&terminal=").append(MmsDataUtil.getPayPlatform(this.sessionCache));
            } else {
                params.append("&terminal=").append(terminal);
            }

            if (commonParam.getMac() != null) {
                params.append("&mac=").append(commonParam.getMac());
            }
            if (commonParam.getDeviceKey() != null) {
                params.append("&deviceKey=").append(commonParam.getDeviceKey());
            }
            params.append("&timestamp=").append(timestamp);
            params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
            String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
            params.append("&sign=").append(sign);
            subUrl.append(params);
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            LOG.info("getVipInfo_" + commonParam.getUserId() + "_" + commonParam.getMac() + "|invoke return:[" + result
                    + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new LetvTypeReference<BossTpResponse<List<SubscribeInfo>>>() {
                });
            }
        } catch (Exception e) {
            LOG.error(
                    "getVipInfo_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                            + commonParam.getDeviceKey() + " return error: ", e);
        }
        return response;
    }

    /**
     * 视频播放鉴权
     * @param userId
     * @param albumId
     * @param storePath
     * @param commonParam
     * @return
     */
    public BossTpResponse<ValidateServiceTp> validateVideoPlayService(String userId, String albumId, String storePath,
                                                                      CommonParam commonParam, int isVod) {
        StringBuilder logPrefixBuilder = new StringBuilder("validatePlayService_");
        logPrefixBuilder.append(userId).append("_").append(commonParam.getMac()).append("_")
                .append(commonParam.getDevId()).append("_").append(commonParam.getDeviceKey()).append("_")
                .append(VipTpConstant.BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_ALBUM).append("_").append(albumId);
        return validatePlayService(userId, VipTpConstant.BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_ALBUM, albumId,
                storePath, null, null, logPrefixBuilder.toString(), commonParam, isVod);
    }

    /**
     * 视频播放鉴权
     * @param userId
     * @param storePath
     * @param commonParam
     * @return
     */
    public BossTpResponse<ValidateServiceTp> validateVideoPlayServiceV2(String userId, String videoId,
            String storePath, CommonParam commonParam, int isVod) {
        StringBuilder logPrefixBuilder = new StringBuilder("validatePlayService_");
        logPrefixBuilder.append(userId).append("_").append(commonParam.getMac()).append("_")
                .append(commonParam.getDevId()).append("_").append(commonParam.getDeviceKey()).append("_")
                .append(VipTpConstant.BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_VIDEO).append("_").append(videoId);
        return validatePlayService(userId, VipTpConstant.BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_VIDEO, videoId,
                storePath, null, null, logPrefixBuilder.toString(), commonParam, isVod);
    }

    /**
     * 专辑鉴权
     * @param userId
     * @param storePath
     * @param commonParam
     * @param isVod
     * @return
     */
    public BossTpResponse<ValidateServiceTp> validateAlbumPlayService(String userId, String albumId, String storePath,
            CommonParam commonParam, int isVod) {
        StringBuilder logPrefixBuilder = new StringBuilder("validatePlayService_");
        logPrefixBuilder.append(userId).append("_").append(commonParam.getMac()).append("_")
                .append(commonParam.getDevId()).append("_").append(commonParam.getDeviceKey()).append("_")
                .append(VipTpConstant.BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_ALBUM).append("_").append(albumId);
        return validatePlayService(userId, VipTpConstant.BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_ALBUM, albumId,
                storePath, null, null, logPrefixBuilder.toString(), commonParam, isVod);
    }

    /**
     * 轮播鉴权
     * @param userId
     * @param lunboId
     * @param streamId
     * @param splatId
     * @param commonParam
     * @return
     */
    public BossTpResponse<ValidateServiceTp> validateLunboPlayService(String userId, String lunboId, String streamId,
            String splatId, CommonParam commonParam) {
        StringBuilder logPrefixBuilder = new StringBuilder("validatePlayService_");
        logPrefixBuilder.append(userId).append("_").append(commonParam.getMac()).append("_")
                .append(commonParam.getDevId()).append("_").append(commonParam.getDeviceKey()).append("_")
                .append(VipTpConstant.BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_LUNBO).append("_").append(lunboId)
                .append("_").append(streamId).append("_").append(splatId);
        BossTpResponse<ValidateServiceTp> response = null;
        Map<String, String> liveTokenMap = new HashMap<String, String>();
        liveTokenMap.put("streamId", streamId);
        liveTokenMap.put("splatId", splatId);
        try {
            String liveToken = JsonUtil.parseToString(liveTokenMap);
            response = validatePlayService(userId, VipTpConstant.BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_LUNBO,
                    lunboId, null, liveToken, null, logPrefixBuilder.toString(), commonParam, -1);
        } catch (Exception e) {
            LOG.error(logPrefixBuilder.append(" return error: ").toString(), e);
        }
        return response;
    }

    /**
     * 直播鉴权
     * @param userId
     * @param liveId
     * @param streamId
     * @param splatId
     * @param commonParam
     * @return
     */
    public BossTpResponse<ValidateServiceTp> validateLivePlayService(String userId, String liveId, String screenings,
            Integer liveStatus, String streamId, String splatId, CommonParam commonParam) {
        BossTpResponse<ValidateServiceTp> response = null;
        StringBuilder logPrefixBuilder = new StringBuilder("validatePlayService_");
        logPrefixBuilder.append(userId).append("_").append(commonParam.getMac()).append("_")
                .append(commonParam.getDevId()).append("_").append(commonParam.getDeviceKey()).append("_")
                .append(VipTpConstant.BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_LIVE).append("_").append(liveId)
                .append("_").append(screenings).append("_").append(liveStatus).append("_").append(streamId).append("_")
                .append(splatId);
        Map<String, String> liveTokenMap = new HashMap<String, String>();
        if (StringUtil.isNotBlank(streamId)) {
            liveTokenMap.put("streamId", streamId);
        }
        if (StringUtil.isNotBlank(splatId)) {
            liveTokenMap.put("splatId", splatId);
        }
        try {
            String liveToken = null;
            if (!liveTokenMap.isEmpty()) {
                liveToken = JsonUtil.parseToString(liveTokenMap);
            }
            response = validatePlayService(userId, VipTpConstant.BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_LIVE,
                    screenings, null, liveToken, liveStatus, logPrefixBuilder.toString(), commonParam, -1);
        } catch (Exception e) {
            LOG.error(logPrefixBuilder.append(" return error: ").toString(), e);
        }

        return response;
    }

    /**
     * boss播放鉴权（V2），
     * http://wiki.letv.cn/pages/viewpage.action?pageId=55252838；
     * http://us.yuanxian.le.com/letv/vip2.ldo?type=validate&ctype=&cid=&
     * terminal=&storepath=&liveTokenInfo=&userId=&country=&mac=&deviceId=&
     * deviceKey=&businessId=&sign=
     * @param userId
     * @param contentType
     *            1--专辑，2--轮播,3--视频，4--体育赛事（直播），5--直播频道
     * @param contentId
     *            内容ID（ctype=1是传专辑ID，ctype=2时传轮播台ID , ctype=3是视频id，ctype=4是直播场次）
     * @param storePath
     *            点播token所需信息（视频存储路径），ctype=3时必传
     * @param liveTokenInfo
     *            直播token所需信息 JSON，格式：{"streamId":1,"splatId":111}，注意：ctype =
     *            2、4 或 5 时必传
     * @param liveStatus
     *            直播状态(1：未开始 2：进行中 3：已结束) ,ctype=4时直播类试看必传
     * @param logPrefix
     * @param commonParam
     * @param isVod
     *            1--不支持，2--支持
     * @return
     */
    protected BossTpResponse<ValidateServiceTp> validatePlayService(String userId, int contentType, String contentId,
            String storePath, String liveTokenInfo, Integer liveStatus, String logPrefix, CommonParam commonParam,
            int isVod) {
        BossTpResponse<ValidateServiceTp> response = null;

        try {
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(VipTpConstant.BOSS_YUANXIAN_V2_BASE_URL);
            StringBuilder params = new StringBuilder();
            params.append("type=validate");
            params.append("&ctype=").append(contentType);
            params.append("&cid=").append(contentId);
            if (isVod > 0) {
                params.append("&isTvVod=").append(isVod);
            }

            if (MmsDataUtil.existPayPlatform(this.sessionCache)) {
                params.append("&terminal=").append(MmsDataUtil.getPayPlatform(this.sessionCache));
            } else {
                params.append("&terminal=141007");
            }

            if (storePath != null) {
                params.append("&storepath=").append(storePath);
            }
            if (liveTokenInfo != null) {
                params.append("&liveTokenInfo=").append(liveTokenInfo);
            }
            if (liveStatus != null) {
                params.append("&liveStatus=").append(liveStatus);
            }
            if (StringUtils.isEmpty(userId)) {
                userId = "-1";
            }
            params.append("&userId=").append(userId);
            VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
            if (country != null) {
                params.append("&country=").append(country.getCode());
            }
            if (commonParam.getMac() != null) {
                params.append("&mac=").append(commonParam.getMac());
            }
            if (commonParam.getDevId() != null) {
                params.append("&deviceId=").append(commonParam.getDevId());
            }
            if (commonParam.getDeviceKey() != null) {
                params.append("&deviceKey=").append(commonParam.getDeviceKey());
            }
            params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
            String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
            params.append("&sign=").append(sign);
            subUrl.append(params);
            String url = subUrl.toString();
            String result = null;
            if (null != liveTokenInfo) {
                url = this.handleUrlParams(url); // BT:
                                                 // 特殊处理get请求中liveTokenInfo中的json数据传递!!!
                String[] urls = { url };
                Map<String, String> retMap = this.restTemplate.multiHttpRequests(urls, "GET", null, null);
                result = retMap.get(url);
            } else {
                result = this.restTemplate.getForObject(url, String.class);
            }
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new LetvTypeReference<BossTpResponse<ValidateServiceTp>>() {
                });
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    private String handleUrlParams(String urlStr) {
        String ret = "";
        try {
            URL url = new URL(urlStr);
            ret = url.getProtocol() + "://" + url.getHost() + url.getPath();
            if (StringUtils.isNotEmpty(url.getQuery())) {
                Map params = HttpClientUtil.getUrlParams(url.getQuery());
                if (params.containsKey("liveTokenInfo") || null != params.get("liveTokenInfo")) {
                    params.put("liveTokenInfo", URLEncoder.encode((String) params.get("liveTokenInfo")));
                }
                ret += "?" + HttpClientUtil.getUrlParamsByMap(params);
            }
        } catch (Exception e) {

        }
        return ret;
    }

    public BossTpResponse<List<VipPackage>> getVideoChargePackage(String videoId, CommonParam commonParam) {
        StringBuilder logPrefixBuilder = new StringBuilder("getPackageByContent_");
        logPrefixBuilder.append(commonParam.getMac()).append("_").append(commonParam.getDevId()).append("_")
                .append(commonParam.getDeviceKey()).append("_")
                .append(VipTpConstant.BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_VIDEO).append("_").append(videoId);
        return getPackageByContent(VipTpConstant.BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_VIDEO, videoId,
                logPrefixBuilder.toString(), commonParam);
    }

    public BossTpResponse<List<VipPackage>> getAlbumChargePackage(String albumId, CommonParam commonParam) {
        StringBuilder logPrefixBuilder = new StringBuilder("getPackageByContent_");
        logPrefixBuilder.append(commonParam.getMac()).append("_").append(commonParam.getDevId()).append("_")
                .append(commonParam.getDeviceKey()).append("_")
                .append(VipTpConstant.BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_ALBUM).append("_").append(albumId);
        return getPackageByContent(VipTpConstant.BOSS_VALIDATE_PLAY_SERVICE_V2_CONTENT_TYPE_ALBUM, albumId,
                logPrefixBuilder.toString(), commonParam);
    }

    /**
     * 根据内容id查询对应所属的付费包
     * @param contentType
     * @param contentId
     * @param logPrefix
     * @param commonParam
     * @return
     */
    private BossTpResponse<List<VipPackage>> getPackageByContent(int contentType, String contentId, String logPrefix,
            CommonParam commonParam) {
        BossTpResponse<List<VipPackage>> response = null;
        try {
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(VipTpConstant.BOSS_YUANXIAN_V2_BASE_URL);
            StringBuilder params = new StringBuilder();
            params.append("type=packageByContent");
            params.append("&ctype=").append(contentType);
            params.append("&cid=").append(contentId);
            VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
            if (country != null) {
                params.append("&country=").append(country.getCode());
            }
            // params.append("&mac=").append(StringUtils.trimToEmpty(commonParam.getMac()));
            // params.append("&deviceId=").append(StringUtils.trimToEmpty(commonParam.getDevId()));
            // params.append("&deviceKey=").append(StringUtils.trimToEmpty(commonParam.getDeviceKey()));
            params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
            String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
            params.append("&sign=").append(sign);
            subUrl.append(params);
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new LetvTypeReference<BossTpResponse<List<VipPackage>>>() {
                });
            }
        } catch (Exception e) {
            LOG.info(logPrefix + " return error: " + e.getMessage(), e);
        }
        return response;
    }

    public GetPayTokenResponse getPayTokenForOverseas(String baseUrl, String signKey, String productName,
            ExtendParam extendParam, String displayName, String img4Sdk, CommonParam commonParam) {
        GetPayTokenResponse response = null;
        String logPrefix = "getPayTokenForOverseas_" + commonParam.getMac() + "_" + commonParam.getUserId();

        String timestamp = TimeUtil.timestamp2date(System.currentTimeMillis());
        MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
        parametersMap.add("locale", extendParam.getLocale());
        parametersMap.add("language", extendParam.getLanguage());
        parametersMap.add("extend_info", this.buildExtendInfoValue(extendParam));
        parametersMap.add("insuranceamt", "0");
        parametersMap.add("itemamt", extendParam.getPrice());
        parametersMap.add("taxamt", "0");
        parametersMap.add("shippingamt", "0");
        parametersMap.add("call_back_url", extendParam.getNotify_url());
        parametersMap.add("version", extendParam.getVersion());
        parametersMap.add("merchant_business_id", extendParam.getMerchant_business_id());
        parametersMap.add("user_id", commonParam.getUserId());
        parametersMap.add("user_name", displayName);
        parametersMap.add("notify_url", extendParam.getNotify_url());
        parametersMap.add("need_calc_tax", "1");
        parametersMap.add("merchant_no", extendParam.getMerchant_no());
        parametersMap.add("out_trade_no", extendParam.getOut_trade_no());
        parametersMap.add("price", extendParam.getPrice());
        parametersMap.add("price_detail", this.buildPriceDetail(extendParam.getPrice()));
        parametersMap.add("currency", extendParam.getCurrency());
        parametersMap.add("pay_expire", extendParam.getPay_expire());
        parametersMap.add("product", this.buildProductValue(productName, img4Sdk, extendParam));
        parametersMap.add("timestamp", timestamp);
        parametersMap.add("key_index", extendParam.getIndex());
        parametersMap.add("input_charset", extendParam.getInput_charset());
        parametersMap.add("sign_type", extendParam.getSign_type());
        parametersMap.add("ip", commonParam.getClientIp());
        parametersMap.add("service", extendParam.getService());
        parametersMap.add("sign",
                this.getPayTokenSign(productName, timestamp, signKey, displayName, img4Sdk, extendParam, commonParam));

        try {
            // http://ipay.letv.com/hk/gettoken
            // http://uspay.le.com/us/gettoken
            String result = this.restTemplate.postForObject(baseUrl, parametersMap, String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (result != null) {
                response = objectMapper.readValue(result, GetPayTokenResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return response;
    }

    /**
     * get user device bind info
     * @param deviceType
     * @param commonParam
     * @return
     */
    public BossTpResponse<DeviceBindConentV2> getUserDeviceBind4Lecom(Integer deviceType, CommonParam commonParam) {
        BossTpResponse<DeviceBindConentV2> response = null;
        String logPrefix = "getUserBindInfo_" + commonParam.getMac() + "_" + commonParam.getDeviceKey() + "_"
                + commonParam.getUserId();
        StringBuilder url = new StringBuilder(VipTpConstant.BOSS_YUANXIAN_V2_DEVICE_BIND_BASE_URL);
        StringBuilder params = new StringBuilder();
        params.append("type=queryByUid");
        String userId = commonParam.getUserId();
        if (StringUtils.isEmpty(userId)) {
            userId = "-1";
        }
        params.append("&userId=").append(userId);
        params.append("&deviceKey=").append(commonParam.getDeviceKey());
        params.append("&mac=").append(commonParam.getMac());
        VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
        if (country != null) {
            params.append("&country=").append(country.getCode());
        }
        params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
        // url.append("&deviceType=").append(deviceType);
        String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
        params.append("&sign=").append(sign);
        url.append(params);
        try {
            // http://yuanxian.letv.com/letv/deviceBind.ldo?type=queryByUid&userid={userid}&deviceKey={deviceKey}&mac={mac}&businessId={businessId}&sign={sign}&sn={sn}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + response + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new LetvTypeReference<BossTpResponse<DeviceBindConentV2>>() {
                });
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    public int uploadDeviceInfo(String sn, Integer deviceType, CommonParam commonParam) {
        // TODO
        StringBuilder url = new StringBuilder(VipTpConstant.BOSS_YUANXIAN_V2_DEVICE_BIND_BASE_URL);
        StringBuilder params = new StringBuilder();
        params.append("type=uploadDevInfo");
        params.append("&deviceKey=").append(commonParam.getDeviceKey());
        params.append("&mac=").append(commonParam.getMac());
        params.append("&sn=").append(sn);
        params.append("&deviceType=").append(String.valueOf(deviceType));
        params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
        String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
        params.append("&sign=").append(sign);
        url.append(params);

        return 0;
    }

    public int syncDeviceDuration(Integer isBindDevice, List<DeviceBindDuration> vipList, CommonParam commonParam) {
        // TODO
        StringBuilder url = new StringBuilder(VipTpConstant.BOSS_YUANXIAN_V2_DEVICE_BIND_BASE_URL);
        StringBuilder params = new StringBuilder();
        params.append("type=syncByMac");
        params.append("&mac=").append(commonParam.getMac());
        params.append("&isBindDevice=").append(String.valueOf(isBindDevice));
        params.append("&businessId=").append(this.getBussinessIdV2(commonParam));

        try {
            params.append("&vipList=").append(JsonUtil.parseToString(vipList));
            String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
            params.append("&sign=").append(sign);
            url.append(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int activeDeviceBind(Integer productId, CommonParam commonParam) {
        // TODO
        StringBuilder url = new StringBuilder(VipTpConstant.BOSS_YUANXIAN_V2_DEVICE_BIND_BASE_URL);
        StringBuilder params = new StringBuilder();
        params.append("type=activeVip");
        params.append("&deviceKey=").append(commonParam.getDeviceKey());
        params.append("&mac=").append(commonParam.getMac());
        params.append("&userId=").append(commonParam.getUserId());
        if (productId == null) {
            productId = 0;
        }
        params.append("&productId=").append(String.valueOf(productId));
        VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
        if (country != null) {
            params.append("&country=").append(country.getCode());
        }
        params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
        String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
        params.append("&sign=").append(sign);
        url.append(params);

        return 0;
    }

    public int unbindDevice(Integer productId, CommonParam commonParam) {
        // TODO
        StringBuilder url = new StringBuilder(VipTpConstant.BOSS_YUANXIAN_V2_DEVICE_BIND_BASE_URL);
        StringBuilder params = new StringBuilder();
        params.append("type=unbind");
        params.append("&deviceKey=").append(commonParam.getDeviceKey());
        params.append("&mac=").append(commonParam.getMac());
        params.append("&userId=").append(commonParam.getUserId());
        if (productId != null) {
            params.append("&productId=").append(String.valueOf(productId));
        }
        VipTpConstant.Country country = CommonUtil.getCountryInfo(commonParam);
        if (country != null) {
            params.append("&country=").append(country.getCode());
        }
        params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
        String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
        params.append("&sign=").append(sign);
        url.append(params);

        return 0;
    }

    /**
     * 获得签名
     * @param plainText
     * @param signKey
     *            签名秘钥
     * @return
     */
    protected String getSign(String plainText, String signKey) {
        if (plainText == null) {
            return "";
        }
        String[] text = plainText.split("&");
        Arrays.sort(text); // 对所有参数按参数名的字典升序排序
        String key_value = "";
        for (String str : text) {
            if (str == null || str.indexOf('=') <= 0 || str.indexOf('=') == str.length() - 1) { // 过滤掉'='左右两侧为空的数据
                continue;
            }
            key_value += str + "&";
        }
        String md5str = key_value + "key=" + signKey;// 拼接key值
        return MD5Util.md5(md5str);
    }

    protected Integer getBussinessIdV2(CommonParam commonParam) {
        VipTpConstant.BussinessIdAndSignKey bussinessIdAndSignKey = VipTpConstant.BussinessIdAndSignKey
                .getBussinessIdAndSignKey(commonParam);
        if (bussinessIdAndSignKey != null) {
            return bussinessIdAndSignKey.getBussinessId();
        }
        return null;
    }

    protected String getSignKeyV2(CommonParam commonParam) {
        VipTpConstant.BussinessIdAndSignKey bussinessIdAndSignKey = VipTpConstant.BussinessIdAndSignKey
                .getBussinessIdAndSignKey(commonParam);
        if (bussinessIdAndSignKey != null) {
            return bussinessIdAndSignKey.getSignKey();
        }
        return null;
    }

    public GetUserVoucherTpResponse getUserVoucher(String status, Integer page, Integer pageSize,
            CommonParam commonParam) {
        GetUserVoucherTpResponse response = null;
        String logPrefix = "getUserVoucher_" + commonParam.getMac() + "_" + commonParam.getUserId();
        try {
            if (page == null) {
                page = 1;
            }
            if (pageSize == null) {
                pageSize = 60;
            }
            StringBuilder url = new StringBuilder(ApplicationConstants.BOSS_PRICE_ZHIFU_BASE_HOST);
            url.append("/api/voucher/").append(commonParam.getUserId());
            // 状态 unused：未使用，默认值, used：已使用，expire： 过期
            url.append("?status=").append(status);
            url.append("&page=").append(page);
            url.append("&rows=").append(pageSize);
            // http://price.zhifu.letv.com/api/voucher/{userId}?status={status}&page={page}&rows={rows}&sort={sort}&order={order}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (result != null) {
                response = objectMapper.readValue(result, GetUserVoucherTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error:", e);
        }
        return response;
    }

    public GetUserVoucherV2TpResponse getUserVoucherV2(String status, Integer page, Integer pageSize,
            CommonParam commonParam) {
        GetUserVoucherV2TpResponse response = null;
        String logPrefix = "getUserVoucherV2_" + commonParam.getMac() + "_" + commonParam.getUserId();
        try {
            if (page == null) {
                page = 1;
            }
            if (pageSize == null) {
                pageSize = 60;
            }
            StringBuilder url = new StringBuilder(ApplicationConstants.BOSS_PRICE_ZHIFU_BASE_HOST);
            url.append("/v2/api/coupon/coupons");
            // 状态 unused：未使用，默认值, used：已使用，expire： 过期
            Map<String, Integer> statusMap = new HashMap<String, Integer>() {
                {
                    put("unused", 2);
                    put("used", 4);
                    put("expire", -2);
                }
            };
            url.append("?status=").append(statusMap.get(status));
            url.append("&ownerId=").append(commonParam.getUserId());
            url.append("&page=").append(page);
            url.append("&rows=").append(pageSize);
            // http://price.zhifu.letv.com/v2/api/coupon/coupons?ownerId={userId}&status={status}&page={page}&rows={rows}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (result != null) {
                response = objectMapper.readValue(result, GetUserVoucherV2TpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error:", e);
        }
        return response;
    }

    /**
     * get extend info of json string
     * @param extendParam
     * @return
     */
    private String buildExtendInfoValue(ExtendParam extendParam) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"");
        sb.append("is_auth");
        sb.append("\":\"");
        sb.append(extendParam.getIs_auth());
        sb.append("\",\"");
        sb.append("is_create_token");
        sb.append("\":\"");
        sb.append(extendParam.getIs_create_token());
        sb.append("\"}");
        return sb.toString();
    }

    /**
     * get product info of json string
     * @param productName
     * @param extendParam
     * @return
     */
    private String buildProductValue(String productName, String img4Sdk, ExtendParam extendParam) {
        StringBuffer sb = new StringBuffer();
        sb.append("[{\"");
        // us pay need pay tax
        sb.append("taxcode").append("\":\"").append(VipTpConstant.VIP_USPAY_TAXCODE).append("\",\"");
        // let category empty
        sb.append("category").append("\":\"").append("").append("\",\"");
        sb.append("desc").append("\":\"").append(productName).append("\",\"");
        sb.append("id").append("\":\"").append(extendParam.getProduct_id()).append("\",\"");
        sb.append("name").append("\":\"").append(productName).append("\",\"");
        sb.append("price").append("\":\"").append(extendParam.getPrice()).append("\",\"");
        // let quantity constants 1
        sb.append("quantity").append("\":\"").append("1").append("\",\"");
        // let sku constants 12
        sb.append("sku").append("\":\"").append("12").append("\",\"");
        // product img
        sb.append("img1X1").append("\":\"").append(img4Sdk).append("\",\"");
        sb.append("url").append("\":\"").append(extendParam.getProduct_urls());
        sb.append("\"}]");
        LOG.info("uspay_" + sb.toString());
        return sb.toString();
    }

    private String buildPriceDetail(String price) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"");
        sb.append("insurance_amt").append("\":\"").append("0").append("\",\"");
        sb.append("item_amt").append("\":\"").append(price).append("\",\"");
        sb.append("shipping_amt").append("\":\"").append("0").append("\",\"");
        sb.append("tax_amt").append("\":\"").append("0");
        sb.append("\"}");
        LOG.info("uspay_" + sb.toString());
        return sb.toString();
    }

    /**
     * generate md5 sign for get hk pay token
     * @return
     */
    private String getPayTokenSign(String productName, String timestamp, String signKey, String displayName,
            String img4Sdk, ExtendParam extendParam, CommonParam commonParam) {
        // all the parameter need to sort by key
        StringBuilder url = new StringBuilder();
        url.append("call_back_url=").append(extendParam.getNotify_url());
        url.append("&currency=").append(extendParam.getCurrency());
        url.append("&extend_info=").append(this.buildExtendInfoValue(extendParam));
        url.append("&input_charset=").append(extendParam.getInput_charset());
        url.append("&insuranceamt=").append("0");
        url.append("&ip=").append(commonParam.getClientIp());
        url.append("&itemamt=").append(extendParam.getPrice());
        url.append("&key_index=").append(extendParam.getIndex());
        url.append("&language=").append(extendParam.getLanguage());
        url.append("&locale=").append(extendParam.getLocale());
        url.append("&merchant_business_id=").append(extendParam.getMerchant_business_id());
        url.append("&merchant_no=").append(extendParam.getMerchant_no());
        url.append("&need_calc_tax=").append("1");
        url.append("&notify_url=").append(extendParam.getNotify_url());
        url.append("&out_trade_no=").append(extendParam.getOut_trade_no());
        url.append("&pay_expire=").append(extendParam.getPay_expire());
        url.append("&price=").append(extendParam.getPrice());
        url.append("&price_detail=").append(this.buildPriceDetail(extendParam.getPrice()));
        url.append("&product=").append(this.buildProductValue(productName, img4Sdk, extendParam));
        url.append("&service=").append(extendParam.getService());
        url.append("&shippingamt=").append("0");
        url.append("&sign_type=").append(extendParam.getSign_type());
        url.append("&taxamt=").append("0");
        url.append("&timestamp=").append(timestamp);
        url.append("&user_id=").append(commonParam.getUserId());
        url.append("&user_name=").append(displayName);
        url.append("&version=").append(extendParam.getVersion());
        url.append("&key=").append(signKey);

        // url.append("&companyurl=").append();
        // url.append("&logourl=").append();
        // url.append("&address=").append();
        // url.append("&channel_ids=").append();
        String sign = MD5Util.md5(url.toString());
        LOG.info("uspay_" + url.toString() + " sign:" + sign);
        return sign;
    }

    /**
     * get guanxing promotion record
     * @param direct
     * @param posids
     * @param commonParam
     * @return
     */
    public OrderStatusTpResponseV2 getGuanXingPromotion(String direct, String posids, CommonParam commonParam) {
        OrderStatusTpResponseV2 orderTp = null;
        String logPrefix = "checkUserStatus_" + commonParam.getMac() + "_" + commonParam.getUserId();
        try {
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(VipTpConstant.GET_GUANXING_PROMOTION_URL);
            StringBuilder params = new StringBuilder();
            params.append("direct=").append(direct);
            params.append("&uid=").append(commonParam.getUserId());
            params.append("&posid=").append(posids);
            params.append("&mac=").append(commonParam.getMac());
            params.append("&devicekey=").append(commonParam.getDeviceKey());
            subUrl.append(params);
            // http://stargazer-scloud.cp21.ott.cibntv.net/proxy/api/v1/promotion?mac=1&model=1&posid=pos_1&uid=1
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                orderTp = this.objectMapper.readValue(result, OrderStatusTpResponseV2.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }

        return orderTp;
    }
}
