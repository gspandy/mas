package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier;

import com.fasterxml.jackson.databind.JsonNode;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.bean.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.response.IPayResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.response.VpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.response.VrResponse;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.SessionCache;
import com.letv.mas.caller.iptv.tvproxy.common.util.MmsDataUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letvpicture.backend.cashier.app.sdk.api.CashierAPI;
import com.letvpicture.backend.cashier.app.sdk.model.PapBindInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by wangli on 4/24/17.
 * 新收银台数据接口：共8个服务
 * 获取收银台信息，获取推荐视频列表，获取视频信息，获取商品信息
 * 生成订单接口，生成订单支付地址，生成短链服务，获取订单状态
 */
@Component
public class CashierVpDao extends BaseTpDao {

    @Autowired(required = false)
    private SessionCache sessionCache;

    private static final Logger LOG = LoggerFactory.getLogger(CashierVpDao.class);
    private static final CashierAPI cashierAPI = CashierAPI.create(CashierVPConstant.CASHIER_SDK_APPID,
            CashierVPConstant.CASHIER_SDK_APPSECRET);
    static {
        cashierAPI.apiClient(1, 1, 1);
    }

    /**
     * 根据订单编号获取订单信息
     * @param orderNo
     *            订单编号
     * @param ssoUid
     *            用户Id
     * @param ssoTk
     *            用户Token
     * @return 订单信息
     */
    public VpResponse<PVOrder> getOrderStatusByOrderNo(String orderNo, String ssoUid, String ssoTk) {
        String logPrefix = "getOrderByOrderNo_" + orderNo;
        VpResponse<PVOrder> response = null;
        if (orderNo != null && orderNo.length() != 0) {
            try {
                String resource = "order/status";
                StringBuilder subUrl = new StringBuilder();
                String url = CashierVPConstant.GET_PV_CASHIER_CONSUMER(); // "http://api.vip.le.com/cashier/consumer/v1/";
                subUrl.append(url).append(resource).append("?");
                StringBuilder params = new StringBuilder();
                params.append("order_no=").append(orderNo);
                params.append("&vipcsrf=").append("xxx");
                subUrl.append(params);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cookie", "ssouid=" + ssoUid + ";sso_tk=" + ssoTk + ";vipcsrf=xxx");
                headers.add("Referer", "http://www.le.com");
                HttpEntity httpEntity = new HttpEntity(headers);
                ResponseEntity<String> result = this.restTemplate.exchange(subUrl.toString(), HttpMethod.GET,
                        httpEntity, String.class, new Object[0]);
                LOG.info(logPrefix + " return info: " + result.getBody());
                if (StringUtil.isNotBlank(result.getBody())) {
                    response = objectMapper.readValue(result.getBody(), new LetvTypeReference<VpResponse<PVOrder>>() {
                    });
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 根据订单编号获取开通成功信息
     * @param orderNo
     *            订单编号
     * @param ssoUid
     *            用户Id
     * @param ssoTk
     *            用户Token
     * @return 会员开通信息
     */
    public VpResponse<PVVipInfo> getVipStatusByOrderNo(String orderNo, String ssoUid, String ssoTk) {
        String logPrefix = "getVipStatusByOrderNo_" + orderNo;
        VpResponse<PVVipInfo> response = null;
        if (orderNo != null && orderNo.length() != 0) {
            try {
                // "http://api.vip.le.com/act/p/engine/act/v1.0/cashier/membership/status?orderNo={orderNo}";
                String url = String.format(CashierVPConstant.PV_VIP_STATUS_URL, orderNo);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cookie", "ssouid=" + ssoUid + ";sso_tk=" + ssoTk + ";vipcsrf=xxx");
                headers.add("Referer", "http://www.le.com");
                HttpEntity httpEntity = new HttpEntity(headers);
                ResponseEntity<String> result = this.restTemplate.exchange(url, HttpMethod.GET, httpEntity,
                        String.class, new Object[0]);
                LOG.info(logPrefix + " return info: " + result.getBody());
                if (StringUtil.isNotBlank(result.getBody())) {
                    response = objectMapper.readValue(result.getBody(), new LetvTypeReference<VpResponse<PVVipInfo>>() {
                    });
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 根据收银台序号获取收银台信息
     * @param cashierId
     *            收银台Id
     * @return 收银台信息
     */
    public VpResponse<PVCashier> getCashierByCashierId(String cashierId) {
        String logPrefix = "getCashierByCashierId_" + cashierId;
        VpResponse<PVCashier> response = null;
        if (cashierId != null) {
            try {
                String resource = "tvTemplate/queryTemplate";
                StringBuilder subUrl = new StringBuilder();
                String url = CashierVPConstant.GET_PV_ACT_ENGINE();// "http://api.vip.le.com/act/p/engine/act/v1.0/";
                subUrl.append(url).append(resource).append("?");
                StringBuilder params = new StringBuilder();
                params.append("tvTemplateId=").append(cashierId);
                params.append("&vipcsrf=").append("xxx");
                subUrl.append(params);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cookie", "vipcsrf=xxx");
                headers.add("Referer", "http://www.le.com");
                HttpEntity httpEntity = new HttpEntity(headers);
                ResponseEntity<String> result = this.restTemplate.exchange(subUrl.toString(), HttpMethod.GET,
                        httpEntity, String.class, new Object[0]);
                LOG.info(logPrefix + " return info: " + result.getBody());
                if (StringUtil.isNotBlank(result.getBody())) {
                    response = objectMapper.readValue(result.getBody(), new LetvTypeReference<VpResponse<PVCashier>>() {
                    });
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 根据商品序号获取商品信息
     * @param productIds
     *            商品列表的字符串
     * @return 商品信息列表
     */
    // TODO 和上游确认，接口是否可以优化加缓存
    public VpResponse<PVProduct> getProductByProductId(String productIds) {
        String logPrefix = "getProductByProductId_" + productIds;
        VpResponse<PVProduct> response = null;
        if (productIds != null && productIds.length() != 0) {
            try {
                String resource = "cashier/product";
                StringBuilder subUrl = new StringBuilder();
                String url = CashierVPConstant.GET_PV_CASHIER_CONSUMER();// "http://api.vip.le.com/cashier/consumer/v1/";
                subUrl.append(url).append(resource).append("?");
                StringBuilder params = new StringBuilder();
                params.append("cashier_product_ids=").append(productIds);
                params.append("&vipcsrf=").append("xxx");
                subUrl.append(params);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cookie", "vipcsrf=xxx");
                headers.add("Referer", "http://www.le.com");
                HttpEntity httpEntity = new HttpEntity(headers);
                ResponseEntity<String> result = this.restTemplate.exchange(subUrl.toString(), HttpMethod.GET,
                        httpEntity, String.class, new Object[0]);
                LOG.info(logPrefix + " return info: " + result.getBody());
                if (StringUtil.isNotBlank(result.getBody())) {
                    response = objectMapper.readValue(result.getBody(), new LetvTypeReference<VpResponse<PVProduct>>() {
                    });
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 根据收银台序号获取推荐视频序号(当前的传入参数没有用)
     * @param cashierId
     *            收银台Id
     * @return 推荐视频Id列表
     */
    public String getRecommendsByCashierId(String cashierId) {
        String logPrefix = "getRecommendsByCashierId_" + cashierId;
        if (cashierId != null) {
            try {
                String operation = "block/get";
                StringBuilder subUrl = new StringBuilder();
                String demon = CashierVPConstant.GET_SL();// "https://static-api.letv.com/";
                subUrl.append(demon).append(operation).append("?");
                StringBuilder params = new StringBuilder();
                params.append("id=").append(9774);
                subUrl.append(params);
                ResponseEntity<String> result = this.restTemplate.getForEntity(subUrl.toString(), String.class,
                        new Object[0]);
                LOG.info(logPrefix + " return info: " + result.getBody());
                if (StringUtil.isNotBlank(result.getBody())) {
                    JsonNode root = objectMapper.readTree(result.getBody());
                    JsonNode data = root.get("data");
                    JsonNode contents = data.get("blockContent");
                    JsonNode shorDesc = contents.get(0).get("shorDesc");
                    return shorDesc.asText();
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return null;
    }

    /**
     * 根据商品序号和用户序号来创建订单
     * @param productId
     *            商品Id
     * @param ssoUid
     *            用户Id
     * @param ssoTk
     *            用户Token
     * @return 订单信息
     */
    public VpResponse<PVOrderI> createOrder(String productId, String ssoUid, String ssoTk) {
        String logPrefix = "createOrder_" + productId;
        VpResponse<PVOrderI> response = null;
        if (productId != null && productId.length() != 0) {
            try {
                String resource = "order/generation";
                StringBuilder subUrl = new StringBuilder();
                String url = CashierVPConstant.GET_PV_CASHIER_CONSUMER();// "http://api.vip.le.com/cashier/consumer/v1/";
                subUrl.append(url).append(resource).append("?");
                StringBuilder params = new StringBuilder();
                params.append("cashier_product_id=").append(productId);
                params.append("&vipcsrf=").append("xxx");
                subUrl.append(params);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cookie", "ssouid=" + ssoUid + ";sso_tk=" + ssoTk + ";vipcsrf=xxx");
                headers.add("Referer", "http://www.le.com");
                HttpEntity httpEntity = new HttpEntity(headers);
                ResponseEntity<String> result = this.restTemplate.exchange(subUrl.toString(), HttpMethod.GET,
                        httpEntity, String.class, new Object[0]);
                // LOG.info(logPrefix + " return info: " + result.getBody());
                if (StringUtil.isNotBlank(result.getBody())) {
                    response = objectMapper.readValue(result.getBody(), new LetvTypeReference<VpResponse<PVOrderI>>() {
                    });
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 生成支付二维码长链接
     * @param orderNo
     *            订单编号
     * @param productId
     *            商品Id
     * @param ssoTk
     *            用户Token
     * @param term
     *            终端Id
     * @param clientIp
     *            客户端Ip
     * @param activityUrl
     *            活动地址
     * @return 二维码长链接
     */
    public String createOrderqrCode(String orderNo, String productId, String ssoTk, String term, String clientIp,
            String activityUrl, String payPath) {
        String logPrefix = "createOrderUrl_" + orderNo;
        if (productId != null && productId.length() != 0 && orderNo != null && orderNo.length() != 0) {
            try {
                String es = ApplicationConstants.PAY_ORDER_QRCODE_CREATEURL_VALUE + "?mode=qrcode&order_no=" + orderNo
                        + "&term=" + term + "&ip=" + clientIp + "&pay_path=" + payPath + "&callback_url=" + activityUrl;
                String acturl = URLEncoder.encode(es, "UTF-8");
                String resource = "user/createQRCode";
                StringBuilder subUrl = new StringBuilder();
                String url = CashierVPConstant.GET_SSO();// "https://sso.cp21.ott.cibntv.net/";
                subUrl.append(url).append(resource).append("?");
                StringBuilder params = new StringBuilder();
                params.append("next_action=").append(acturl);
                params.append("&expire=").append(15 * 60);
                params.append("&sso_tk=").append(ssoTk);
                subUrl.append(params);
                ResponseEntity<String> result = this.restTemplate.getForEntity(subUrl.toString(), String.class,
                        new Object[0]);
                LOG.info(logPrefix + " return info: " + result.getBody());
                if (StringUtil.isNotBlank(result.getBody())) {
                    JsonNode root = objectMapper.readTree(result.getBody());
                    JsonNode data = root.get("bean");
                    JsonNode qrcode = data.get("qrcode");
                    return qrcode.asText();
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return null;
    }

    /**
     * 生成短链接
     * @param url
     *            长链接
     * @return 短链接
     */
    public String cutUrl(String url) {
        String logPrefix = "cutUrl_" + url;
        if (url != null && url.length() != 0) {
            try {
                String operation = "put";
                StringBuilder subUrl = new StringBuilder();
                String demon = CashierVPConstant.GET_TG();// "http://t.go.cp21.ott.cibntv.net/";
                subUrl.append(demon).append(operation).append("?");
                StringBuilder params = new StringBuilder();
                params.append("url={url}");
                subUrl.append(params);
                ResponseEntity<String> result = this.restTemplate.getForEntity(subUrl.toString(), String.class, url);
                LOG.info(logPrefix + " return info: " + result.getBody());
                if (StringUtil.isNotBlank(result.getBody())) {
                    Map<String, Object> map = objectMapper.readValue(result.getBody(),
                            new LetvTypeReference<Map<String, Object>>() {
                            });
                    return ApplicationConstants.PAY_ORDER_QRCODE_BASEURL_VALUE + "/" + map.get("code");
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return null;
    }

    /**
     * 根据视频序号获取视频信息
     * @param vid
     *            视频Id
     * @return 视频信息
     */
    public VrResponse<VRAlbum> getVideoIfnoByVid(String vid) {
        String logPrefix = "getVideoIfnoByVid_" + vid;
        VrResponse<VRAlbum> response = null;
        if (vid != null && vid.length() != 0) {
            try {
                String resource = "albumInfo/get";
                StringBuilder subUrl = new StringBuilder();
                String url = CashierVPConstant.GET_IL_MMS_INNER();// "http://i.api.letv.com/mms/inner";
                subUrl.append(url).append(resource).append("?");
                StringBuilder params = new StringBuilder();
                params.append("id=").append(vid);
                params.append("&amode=").append("0");
                params.append("&platform=").append("letv_vrs");
                subUrl.append(params);
                ResponseEntity<String> result = this.restTemplate.getForEntity(subUrl.toString(), String.class,
                        new Object[0]);
                LOG.info(logPrefix + " return info: " + result.getBody());
                if (StringUtil.isNotBlank(result.getBody())) {
                    response = objectMapper.readValue(result.getBody(), new LetvTypeReference<VrResponse<VRAlbum>>() {
                    });
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return response;
    }

    public List<PapBindInfo> getBoundPaymentByUserId(String userId) {
        String logPrefix = "getBoundPaymentByUserId_" + userId;
        if (userId != null) {
            try {
                String businessId = CashierVPConstant.LEPAY_BUSINESS_ID;
                return cashierAPI.getPapBindInfos(businessId, userId);
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return null;
    }

    public VpResponse<PVUserVIPInfo> getUserVIPInfo(String ssouid, String sso_tk, String deviceKey, String mac) {
        String logPrefix = "getUserVIPInfo" + ssouid;
        VpResponse<PVUserVIPInfo> response = null;
        if (ssouid != null && sso_tk != null && deviceKey != null && mac != null) {
            try {
                String term;
                if (MmsDataUtil.existPayPlatform(this.sessionCache)) {
                    term = MmsDataUtil.getPayPlatform(this.sessionCache);
                } else {
                    term = CashierVPConstant.FETCH_USER_VIP_INFO_TERM;
                }
                String url = String.format(CashierVPConstant.PV_USER_VIP_INFO_URL, term, deviceKey, mac);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cookie", "ssouid=" + ssouid + ";sso_tk=" + sso_tk + ";vipcsrf=xxx");
                headers.add("Referer", "http://www.le.com");
                HttpEntity httpEntity = new HttpEntity(headers);
                ResponseEntity<String> result = this.restTemplate.exchange(url, HttpMethod.GET, httpEntity,
                        String.class, new Object[0]);
                LOG.info(logPrefix + " return info: " + result.getBody());
                if (StringUtil.isNotBlank(result.getBody())) {
                    response = objectMapper.readValue(result.getBody(),
                            new LetvTypeReference<VpResponse<PVUserVIPInfo>>() {
                            });
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return response;
    }

    /**
     * 支付订单
     * @param order_no
     *            订单Id
     * @param bind_id
     *            签约代扣Id
     * @param ssoUid
     *            用户Id
     * @param ssoTk
     *            用户Token
     * @return 订单信息
     */
    public VpResponse<PVPurchaseInfo> getPurchaseInfo(String order_no, String bind_id, String ssoUid, String ssoTk,
            String term) {
        String logPrefix = "getPurchaseInfo_" + order_no;
        VpResponse<PVPurchaseInfo> response = null;
        if (order_no != null && bind_id != null) {
            try {
                String url = String.format(CashierVPConstant.PV_PURCHASE_INFO_URL,
                        CashierVPConstant.PAY_ORDER_PAP_MODE, order_no, bind_id, term);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cookie", "ssouid=" + ssoUid + ";sso_tk=" + ssoTk + ";vipcsrf=xxx");
                headers.add("Referer", "http://www.le.com");
                HttpEntity httpEntity = new HttpEntity(headers);
                ResponseEntity<String> result = this.restTemplate.exchange(url, HttpMethod.GET, httpEntity,
                        String.class, new Object[0]);
                LOG.info(logPrefix + " return info: " + result.getBody());
                if (StringUtil.isNotBlank(result.getBody())) {
                    response = objectMapper.readValue(result.getBody(),
                            new LetvTypeReference<VpResponse<PVPurchaseInfo>>() {
                            });
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return response;
    }

    public IPayResponse<IPayResult> payOrder(PVPurchaseInfo.PurchaseInfo purchaseInfo, String bind_id) {
        String logPrefix = "payOrder_" + purchaseInfo.getMerchant_no();
        IPayResponse<IPayResult> response = null;
        String version = purchaseInfo.getVersion();
        String service = purchaseInfo.getService();
        String merchant_business_id = purchaseInfo.getMerchant_business_id();
        String user_id = purchaseInfo.getUser_id();
        String user_name = purchaseInfo.getUser_name();
        String notify_url = purchaseInfo.getNotify_url();
        String call_back_url = purchaseInfo.getCall_back_url();
        String merchant_no = purchaseInfo.getMerchant_no();
        String out_trade_no = purchaseInfo.getOut_trade_no();
        String price = purchaseInfo.getPrice();
        String currency = purchaseInfo.getCurrency();
        String pay_expire = purchaseInfo.getPay_expire();
        String product_id = purchaseInfo.getProduct_id();
        String product_name = purchaseInfo.getProduct_name();
        String product_desc = purchaseInfo.getProduct_desc();
        String product_urls = purchaseInfo.getProduct_urls();
        String timestamp = purchaseInfo.getTimestamp();
        String key_index = purchaseInfo.getKey_index();
        String input_charset = purchaseInfo.getInput_charset();
        String ip = purchaseInfo.getIp();
        String sign = purchaseInfo.getSign();
        String sign_type = purchaseInfo.getSign_type();
        if (StringUtil.isNotBlank(version) && StringUtil.isNotBlank(service)
                && StringUtil.isNotBlank(merchant_business_id) && StringUtil.isNotBlank(user_id)
                && StringUtil.isNotBlank(notify_url) && StringUtil.isNotBlank(merchant_no)
                && StringUtil.isNotBlank(out_trade_no) && StringUtil.isNotBlank(price)
                && StringUtil.isNotBlank(currency) && StringUtil.isNotBlank(product_id)
                && StringUtil.isNotBlank(product_name) && StringUtil.isNotBlank(product_desc)
                && StringUtil.isNotBlank(timestamp) && StringUtil.isNotBlank(key_index)
                && StringUtil.isNotBlank(input_charset) && StringUtil.isNotBlank(ip) && StringUtil.isNotBlank(sign)
                && StringUtil.isNotBlank(sign_type) && StringUtil.isNotBlank(bind_id)) {
            try {
                MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
                params.add("version", version);
                params.add("service", service);
                params.add("merchant_business_id", merchant_business_id);
                params.add("user_id", user_id);
                params.add("notify_url", notify_url);
                params.add("merchant_no", merchant_no);
                params.add("out_trade_no", out_trade_no);
                params.add("price", price);
                params.add("currency", currency);
                params.add("product_id", product_id);
                params.add("product_name", product_name);
                params.add("product_desc", product_desc);
                params.add("timestamp", timestamp);
                params.add("key_index", key_index);
                params.add("input_charset", input_charset);
                params.add("ip", ip);
                params.add("sign", sign);
                params.add("sign_type", sign_type);
                params.add("bind_id", bind_id);
                String result = this.restTemplate.postForObject(CashierVPConstant.PV_PAY_ORDER_URL, params,
                        String.class);
                LOG.info(logPrefix + " return info: " + result);
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, new LetvTypeReference<IPayResponse<IPayResult>>() {
                    });
                }
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }
        return response;
    }

}
