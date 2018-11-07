package com.letv.mas.caller.iptv.tvproxy.cashier;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.ErrorCodeConstants;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.JumpUtil;
import com.letv.mas.caller.iptv.tvproxy.cashier.model.dto.Agreements;
import com.letv.mas.caller.iptv.tvproxy.cashier.model.dto.BoundPayment;
import com.letv.mas.caller.iptv.tvproxy.cashier.model.dto.Cashier;
import com.letv.mas.caller.iptv.tvproxy.cashier.model.dto.Order;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.CashierVPConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.bean.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.response.IPayResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.response.VpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockContentTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.terminal.constant.TerminalConstant;
import com.letvpicture.backend.cashier.app.sdk.model.PapBindInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.letv.mas.caller.iptv.tvproxy.common.util.VideoCommonUtil.isCharge;

/**
 * Created by wangli on 4/24/17.
 * 暴露的服务：获取收银台信息（获取商品信息，获取推荐信息），获取订单状态，生成支付二维码
 */
@Service("cashierService")
public class CashierService extends BaseService {

    // todo 此方法过缓存，user相关业务逻辑没上，之后等到加入user逻辑，需重新调整缓存粒度； wcode； flush清缓存
    Cashier getCashier(String cashierId, CommonParam commonParam) {
        if (cashierId == null || cashierId.trim().length() == 0) {
            // TODO 暂时客户端terminalapplication只会是超级影视，以后如果能区分就分开走各自打底ID
            if (TerminalConstant.TERMINAL_APPLICATION_CHILD_CIBN.equalsIgnoreCase(commonParam.getTerminalApplication())) {
                cashierId = getCashierConfigString(CashierConstants.CASHIER_CHILD_CASHIER_DEFAULT_ID);
            } else {
                cashierId = getCashierConfigString(CashierConstants.CASHIER_CASHIER_DEFAULT_ID);
            }
        }
        if (StringUtil.isBlank(cashierId)) {
            String errorCode = ErrorCodeConstants.COMMON_ILLEGAL_PARAMETER;
            throw new CashierException(errorCode, cashierId + "[errorCode=" + errorCode + "]: param is illegal.");
        }
        // 只做缓存的键中使用
        String terminalApplication;
        if (commonParam == null || commonParam.getTerminalApplication() == null) {
            terminalApplication = "unknown";
        } else {
            terminalApplication = commonParam.getTerminalApplication();
        }
        // 缓存key为 cashier_recommend_{cashierId}_{terminalApplication}
        String cacheKey = "cashier_" + cashierId + "_" + terminalApplication;
        Cashier cashier = this.cacheTemplate.get(cacheKey, Cashier.class);
        // 读到缓存则直接返回
        if (cashier != null) {
            return cashier;
        }
        // 未读到缓存
        cashier = new Cashier();
        VpResponse<PVCashier> response = this.facadeTpDao.getCashierVpDao().getCashierByCashierId(cashierId);
        if (response == null || response.getBody() == null) {

            String errorCode = ErrorCodeConstants.VIP_GET_PACKAGE_INFO_FAIL;
            throw new CashierException(errorCode, cashierId + "[errorCode=" + errorCode
                    + "]: get cashier packages failed.");
        }
        PVCashier pvcashier = response.getBody();
        // 设置收银台基本信息
        cashier.setCashierId(pvcashier.getTvTemplateId());
        cashier.setType(Integer.toString(pvcashier.getTemplateType()));
        PVCashier.Template temple = pvcashier.getBaseInfo();
        cashier.setPic(temple.getBackground());
        if (StringUtils.isNumeric(temple.getVid())) {
            cashier.setVid(Integer.parseInt(temple.getVid() == null ? "0" : temple.getVid()));
        }
        // 设置收银台推荐信息
        cashier.setRecommends(getRecommends(cashierId, commonParam));
        List<Cashier.PackageInfo> packageInfos = new ArrayList<>();
        // 设置收银台套餐信息
        cashier.setPackages(packageInfos);
        String[] productIds = new String[pvcashier.getSales().size()];
        for (int i = 0; i < productIds.length; i++) {
            productIds[i] = pvcashier.getSales().get(i).getCashierProductId();
        }
        Map<String, PVProduct.ProductInfo> products = getPackageInfo(productIds);
        for (PVCashier.CashierPackage sale : pvcashier.getSales()) {
            // boolean isRenewal = sale.isRenewal();
            // //如果是包月并且用户还在包月期内，则过滤
            // if (isRenewal && StringUtil.isNotBlank(commonParam.getUserId())
            // &&
            // this.IsUserMonthlyVIPValid(commonParam.getUserId(),
            // commonParam.getToken(),
            // commonParam.getDeviceKey(), commonParam.getMac())) {
            // continue;
            // }
            Cashier.PackageInfo packageInfo = new Cashier.PackageInfo();
            // if (isRenewal) {
            // packageInfo.setAgreementTitle("连续包月服务协议");
            // } else {
            // packageInfo.setAgreementTitle("超级影视会员服务协议");
            // }
            packageInfo.setPackageID(sale.getSaleId());
            packageInfo.setImg(sale.getActImg());
            packageInfo.setUrl(sale.getActReceivedUrl());
            if (sale.getActReceivedUrl() != null) {
                String shortUrl = this.facadeTpDao.getCashierVpDao().cutUrl(sale.getActReceivedUrl());
                if (!StringUtil.isBlank(shortUrl)) {
                    packageInfo.setShortUrl(shortUrl);
                }
            }
            // packageInfo.setRenewal(isRenewal);
            packageInfo.setDefault(sale.isDefault());
            List<Cashier.Activity> activities = new ArrayList<>();
            packageInfo.setActivitys(activities);
            if (sale.getPresents() != null) {
                for (PVCashier.Present present : sale.getPresents()) {
                    Cashier.Activity activity = new Cashier.Activity();
                    activity.setActivityId(present.getPresentId());
                    activity.setActivityName(present.getPresentName());
                    activity.setPrice(present.getPresentPrice());
                    activity.setActivityType(present.getPresentType());
                    activities.add(activity);
                }
            }
            if (products != null) {
                PVProduct.ProductInfo product = products.get(sale.getCashierProductId());
                if (product != null) {
                    Cashier.Product cproduct = new Cashier.Product();
                    cproduct.setProductId(product.getCashier_product_id());
                    cproduct.setProductName(product.getCashier_product_name());
                    cproduct.setActivityName(sale.getActDescription());
                    cproduct.setPrice(Double.parseDouble(product.getCashier_product_price()));
                    packageInfo.setProduct(cproduct);
                    packageInfos.add(packageInfo);
                }
            }
        }
        // //如果客户端传userId，设置收银台绑卡信息 (暂时不上)
        // if (StringUtil.isNotBlank(commonParam.getUserId())) {
        // cashier.setBoundPayment(this.getBoundPaymentInfo(commonParam.getUserId()));
        // }
        this.cacheTemplate.set(cacheKey, cashier, 10 * 60);
        return cashier;
    }

    private Map<String, PVProduct.ProductInfo> getPackageInfo(String[] productIds) {
        Map<String, PVProduct.ProductInfo> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        if (productIds.length < 1)
            return null;
        sb.append(productIds[0]);
        for (int i = 1; i < productIds.length; i++) {
            sb.append(",").append(productIds[i]);
        }
        VpResponse<PVProduct> pvproducts = this.facadeTpDao.getCashierVpDao().getProductByProductId(sb.toString());
        if (pvproducts == null || pvproducts.getBody() == null) {
            String errorCode = ErrorCodeConstants.PAY_GET_PRICE_PARCKAGE_LIST_FAILURE;
            throw new CashierException(errorCode, Arrays.toString(productIds) + "[errorCode=" + errorCode
                    + "]: get packages info failed");
        }
        List<PVProduct.ProductInfo> products = pvproducts.getBody().getCashier_products();
        for (PVProduct.ProductInfo product : products) {
            map.put(product.getCashier_product_id(), product);
        }
        return map;
    }

    // 数据库批量一次性取
    private List<Cashier.Recommend> getRecommends(String cashierId, CommonParam commonParam) {
        List<Cashier.Recommend> recommends = new ArrayList<>();
        String vids = this.facadeTpDao.getCashierVpDao().getRecommendsByCashierId(cashierId);
        if (vids == null || vids.length() == 0) {
            String errorCode = ErrorCodeConstants.PAY_GET_PACKAGE_TYPE_FAILURE;
            throw new CashierException(errorCode, cashierId + "[errorCode=" + errorCode
                    + "]: get cashier recommends failed.");
        }
        List<Long> vidList = new ArrayList<>();
        for (String vid : vids.split(",")) {
            try {
                vidList.add(Long.valueOf(vid));
            } catch (NumberFormatException ignored) {
            }
        }
        // 限制个数最多为10个
        if (vidList.size() > 10) {
            vidList = vidList.subList(0, 10);
        }
        List<AlbumMysqlTable> albums = this.facadeMysqlDao.getAlbumMysqlDao().getAlbumListByIdList(vidList);
        for (AlbumMysqlTable album : albums) {
            Cashier.Recommend recommend = new Cashier.Recommend();
            recommend.setTvCopyright(1);
            recommend.setAlbumId(String.valueOf(album.getId()));
            recommend.setName(album.getName_cn());
            recommend.setScore(album.getScore());
            recommend.setImage(getAblumPic(album.getPic_collections(), new String[] { "600*800", "300*400" }));
            recommend.setCategoryId(album.getCategory());
            recommend.setSubCategoryId(album.getSub_category());
            recommend.setIfCharge(isCharge(album.getPay_platform(), album.getPlay_platform(),
                    commonParam.getP_devType()) ? "1" : "0");
            recommend.setSubName(album.getSub_title());
            recommend.setVid(String.valueOf(album.getId()));
            recommend.setRating(album.getScore());
            recommend.setCharge(recommend.getIfCharge().equals("1"));
            recommend.setDesc(album.getDescription());
            JumpUtil.bulidJumpObj(recommend, DataConstant.DATA_TYPE_ALBUM, null, null, commonParam);
            recommends.add(recommend);
        }
        return recommends;
    }

    private String getAblumPic(Map<String, String> pics, String[] sizes) {
        if (pics.size() != 0 && sizes.length != 0) {
            for (String size : sizes) {
                String pic = pics.get(size);
                if (pic != null && pic.length() != 0) {
                    return pic;
                }
            }
        }
        return null;
    }

    private String getAblumPic(String pics, String[] sizes) {
        Map<String, String> pics_map;
        try {
            pics_map = new ObjectMapper().readValue(pics, new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            return "";
        }
        return this.getAblumPic(pics_map, sizes);
    }

    List<Order> refreshOrdersUrl(String[] productIds, String ssoUid, String ssoTk, String term, String clientIp,
                                 String activityUrl, String payPath) {
        if (StringUtil.isBlank(ssoUid) || StringUtil.isBlank(ssoTk) || productIds == null || productIds.length == 0) {
            String errorCode = ErrorCodeConstants.COMMON_ILLEGAL_PARAMETER;
            throw new CashierException(errorCode, Arrays.toString(productIds) + "_" + ssoUid + "[errorCode="
                    + errorCode + "]: param is illegal.");
        }
        List<Order> orders = new ArrayList<>();
        for (String productId : productIds) {
            if (!StringUtil.isBlank(productId)) {
                orders.add(refreshOrderUrl(productId, ssoUid, ssoTk, term, clientIp, activityUrl, payPath));
            }
        }
        return orders;
    }

    Order generateOrder(String productId, String ssoUid, String ssoTk) {
        if (StringUtil.isBlank(productId) || StringUtil.isBlank(ssoUid) || StringUtil.isBlank(ssoTk)) {
            String errorCode = ErrorCodeConstants.COMMON_ILLEGAL_PARAMETER;
            throw new CashierException(errorCode, productId + "_" + ssoUid + "[errorCode=" + errorCode
                    + "]: param is illegal.");
        }
        VpResponse<PVOrderI> prorder = this.facadeTpDao.getCashierVpDao().createOrder(productId, ssoUid, ssoTk);
        if (prorder != null && prorder.getHead().getRet() == 4) {
            String errorCode = ErrorCodeConstants.COMMON_SERVICE_BUSY;
            throw new CashierException(errorCode, productId + "[errorCode=" + errorCode + "]: create order failed");
        }
        if (prorder == null || prorder.getBody() == null) {
            String errorCode = ErrorCodeConstants.PAY_COMMIT_ORDER_FAILURE;
            throw new CashierException(errorCode, productId + "[errorCode=" + errorCode + "]: create order failed");
        }
        PVOrderI orderi = prorder.getBody();
        Order order = new Order();
        order.setOrderId(orderi.getOrder_no());
        order.setProductId(productId);
        return order;
    }

    // TODO 模板代码，需要重构
    void payOrder(Order order, String bind_id, String ssoUid, String ssoTk, String term) {
        if (StringUtil.isBlank(order.getOrderId()) || StringUtil.isBlank(order.getProductId())) {
            String errorCode = ErrorCodeConstants.PAY_ORDER_NULL;
            throw new CashierException(errorCode, "[errorCode=" + errorCode + "]: param is illegal.");
        }
        if (StringUtil.isBlank(term)) {
            term = CashierConstants.COMMON_LE_TERMINA_TYPE_LETV;
        }
        VpResponse<PVPurchaseInfo> pvPurchaseInfo = this.facadeTpDao.getCashierVpDao().getPurchaseInfo(bind_id,
                order.getOrderId(), ssoUid, ssoTk, term);
        if (pvPurchaseInfo != null && pvPurchaseInfo.getHead().getRet() != 0) {
            String errorCode = ErrorCodeConstants.PAY_GET_PURCHASE_INFO_FAILURE;
            throw new CashierException(errorCode, order.getOrderId() + "[errorCode=" + errorCode
                    + "]: pay order failed");
        }
        if (pvPurchaseInfo == null || pvPurchaseInfo.getBody() == null) {
            String errorCode = ErrorCodeConstants.COMMON_SERVICE_BUSY;
            throw new CashierException(errorCode, order.getOrderId()
                    + "[getPurchase info service error]: pay order failed");
        }
        PVPurchaseInfo.PurchaseInfo purchaseInfo = pvPurchaseInfo.getBody().getPurchaseInfo();
        IPayResponse<IPayResult> iPayResponse = this.facadeTpDao.getCashierVpDao().payOrder(purchaseInfo, bind_id);
        if (iPayResponse == null) {
            String errorCode = ErrorCodeConstants.COMMON_SERVICE_BUSY;
            throw new CashierException(errorCode, order.getOrderId()
                    + "[quick purchase service error]: pay order failed");
        }
        if (iPayResponse.getCode() != 0) {
            String errorCode = ErrorCodeConstants.PAY_FAILURE;
            throw new CashierException(errorCode, order.getOrderId() + "[errorCode=" + errorCode
                    + "]: pay order failed");
        }
    }

    Order refreshOrderUrl(String productId, String ssoUid, String ssoTk, String term, String clientIp,
            String activityUrl, String payPath) {
        if (StringUtil.isBlank(productId) || StringUtil.isBlank(ssoUid) || StringUtil.isBlank(ssoTk)) {
            String errorCode = ErrorCodeConstants.COMMON_ILLEGAL_PARAMETER;
            throw new CashierException(errorCode, productId + "_" + ssoUid + "[errorCode=" + errorCode
                    + "]: param is illegal.");
        }
        VpResponse<PVOrderI> prorder = this.facadeTpDao.getCashierVpDao().createOrder(productId, ssoUid, ssoTk);
        if (prorder != null && prorder.getHead().getRet() == 4) {
            String errorCode = ErrorCodeConstants.COMMON_SERVICE_BUSY;
            throw new CashierException(errorCode, productId + "[errorCode=" + errorCode + "]: create order failed");
        }
        if (prorder == null || prorder.getBody() == null) {
            String errorCode = ErrorCodeConstants.PAY_COMMIT_ORDER_FAILURE;
            throw new CashierException(errorCode, productId + "[errorCode=" + errorCode + "]: create order failed");
        }
        if (StringUtil.isBlank(term)) {
            term = CashierConstants.COMMON_LE_TERMINA_TYPE_LETV;
        }
        PVOrderI orderi = prorder.getBody();
        String qrcode = this.facadeTpDao.getCashierVpDao().createOrderqrCode(orderi.getOrder_no(), productId, ssoTk,
                term, clientIp, activityUrl, payPath);
        if (StringUtil.isBlank(qrcode)) {
            String errorCode = ErrorCodeConstants.PAY_GET_QRCODE_FAILURE;
            throw new CashierException(errorCode, productId + "[errorCode=" + errorCode + "]: create qrcode failed");
        }
        String dwz = this.facadeTpDao.getCashierVpDao().cutUrl(qrcode);
        if (StringUtil.isBlank(dwz)) {
            dwz = qrcode;
        }
        Order order = new Order();
        order.setOrderId(orderi.getOrder_no());
        order.setProductId(productId);
        order.setQrcodeUrl(dwz);
        order.setQrcodeDuration(getOrderQrcodeDuration());
        return order;
    }

    Order checkOrderStatus(String orderNo, String ssoUid, String ssoTk, String deviceKey, String mac, boolean renewal) {
        if (StringUtil.isBlank(orderNo) || StringUtil.isBlank(ssoUid) || StringUtil.isBlank(ssoTk)) {
            String errorCode = ErrorCodeConstants.COMMON_ILLEGAL_PARAMETER;
            throw new CashierException(errorCode, orderNo + "[errorCode=" + errorCode + "]: param is illegal.");
        }
        VpResponse<PVOrder> response = this.facadeTpDao.getCashierVpDao().getOrderStatusByOrderNo(orderNo, ssoUid,
                ssoTk);
        if (response == null) {
            String errorCode = ErrorCodeConstants.PAY_ORDER_FAILURE;
            throw new CashierException(errorCode, orderNo + "[errorCode=" + errorCode + "]: get order info failed");
        }
        if (response.getHead().getRet() != 0) {
            String errorCode = ErrorCodeConstants.PAY_ORDER_FAILURE;
            throw new CashierException(errorCode, orderNo + "[errorCode=" + errorCode + "]: "
                    + response.getHead().getMsg() + "(" + response.getHead().getRet() + ")");
        }
        PVOrder.OrderInfo orderInfo = response.getBody().getOrder_info();
        if (orderInfo == null) {
            String errorCode = ErrorCodeConstants.PAY_ORDER_FAILURE;
            throw new CashierException(errorCode, orderNo + "[errorCode=" + errorCode + "]: order info is empty");
        }
        Order order = new Order();
        order.setOrderId(orderInfo.getOrder_no());
        order.setProductId(orderInfo.getCashier_product_id());
        order.setTradeResult(orderInfo.getTrade_result());
        boolean isValid = this.checkVipStatus(orderNo, ssoUid, ssoTk);
        order.setValid(isValid);
        // 如果开通成功，返回会员到期时间
        if (isValid & StringUtil.isNotBlank(deviceKey) & StringUtil.isNotBlank(mac)) {
            String proExpiresAt = getProExpiresAt(ssoUid, ssoTk, deviceKey, mac);
            order.setProExpiresAt(proExpiresAt);
        }
        order.setPresentUrl("http://minisite.cp21.ott.cibntv.net/order/index.shtml#verify/" + order.getOrderId());
        // 如果是连续包月订单查询，返回绑卡信息
        if (renewal) {
            ArrayList<BoundPayment> boundPayment = this.getBoundPaymentInfo(ssoUid);
            order.setBoundPayments(boundPayment);
            order.setRenewal(true);
        } else {
            order.setRenewal(false);
        }
        return order;
    }

    private boolean checkVipStatus(String order_no, String ssoUid, String ssoTk) {
        if (StringUtil.isBlank(order_no) || StringUtil.isBlank(ssoUid) || StringUtil.isBlank(ssoTk)) {
            String errorCode = ErrorCodeConstants.COMMON_ILLEGAL_PARAMETER;
            throw new CashierException(errorCode, order_no + "[errorCode=" + errorCode + "]: param is illegal.");
        }
        VpResponse<PVVipInfo> response = this.facadeTpDao.getCashierVpDao().getVipStatusByOrderNo(order_no, ssoUid,
                ssoTk);
        if (response == null) {
            String errorCode = ErrorCodeConstants.PAY_ORDER_FAILURE;
            throw new CashierException(errorCode, order_no + "[errorCode=" + errorCode + "]: get order info failed");
        }
        if (response.getHead().getRet() != 0) {
            return false;
        }
        PVVipInfo.OrderInfo orderInfo = response.getBody().getOrderInfo();
        if (orderInfo == null) {
            String errorCode = ErrorCodeConstants.PAY_ORDER_FAILURE;
            throw new CashierException(errorCode, order_no + "[errorCode=" + errorCode + "]: order info is empty");
        }
        return Objects.equals(orderInfo.getStatus(), "1");
    }

    private long getOrderQrcodeDuration() {
        return getCashierConfigInteger(CashierConstants.CASHIER_ORDER_QRCODE_DEFAULT_TIME, 300 * 1000);
    }

    private int getCashierConfigInteger(String key, int defaultValue) {
        String value = getCashierConfigInfo(key);
        return StringUtil.isBlank(value) ? defaultValue : Integer.parseInt(value);
    }

    private String getCashierConfigString(String key) {
        String value = getCashierConfigInfo(key);
        return StringUtil.isBlank(value) ? "s7dscf2xo8289wk07c9a09vs77e7qzy3" : value;
    }

    private String getCashierConfigInfo(String key) {
        this.updateCashierConfigInfoMap();
        if (CashierConstants.cashierConfigInfoMap == null) {
            return null;
        }
        return CashierConstants.cashierConfigInfoMap.get(key);
    }

    private void updateCashierConfigInfoMap() {
        long curTime = System.currentTimeMillis();
        if (CashierConstants.LAST_UPDATE_CASHIER_CONFIG_INFO_TIME
                + CashierConstants.UPDATE_CASHIER_CONFIG_INFO_INTERVAL_TIME < curTime) {
            synchronized (CashierConstants.cashierConfigInfoMap) {
                if (CashierConstants.LAST_UPDATE_CASHIER_CONFIG_INFO_TIME
                        + CashierConstants.UPDATE_CASHIER_CONFIG_INFO_INTERVAL_TIME < curTime) {
                    CashierConstants.LAST_UPDATE_CASHIER_CONFIG_INFO_TIME = curTime;
                    Map<String, String> map = this.facadeTpDao.getStaticTpDao().getStaticFileContent(
                            ApplicationUtils.get(ApplicationConstants.CASHIER_PROFILE_URL));// 静态文件内容
                    if (this.isValidCashierConfig(map)) {
                        CashierConstants.cashierConfigInfoMap = map;
                    }
                }
            }
        }
    }

    private boolean isValidCashierConfig(Map map) {
        if (!CollectionUtils.isEmpty(map) && map.containsKey(CashierConstants.CASHIER_CASHIER_DEFAULT_ID)) {
            return true;
        }
        return false;
    }

    /*
     * 判断用户是否在包月期内，目前逻辑就是，按uid查
     * http://api-vip.le.com/act/user-info/act/user/membership/info?term={term}&
     * deviceKey={deviceKey}&mac={mac}
     * 获取用户影视会员权益信息，如果isProSubscribe=2（已订阅）则视为用户还在包月期内
     */
    private boolean IsUserMonthlyVIPValid(String ssouid, String sso_tk, String deviceKey, String mac) {
        VpResponse<PVUserVIPInfo> response = this.facadeTpDao.getCashierVpDao().getUserVIPInfo(ssouid, sso_tk,
                deviceKey, mac);
        if (response == null) {
            String errorCode = ErrorCodeConstants.USER_GET_MEMBERSHIP_FAILURE;
            throw new CashierException(errorCode, ssouid + "[errorCode=" + errorCode + "]: get membership info failed");
        }
        if (response.getHead().getRet() != 0) {
            String errorCode = ErrorCodeConstants.USER_GET_MEMBERSHIP_FAILURE;
            throw new CashierException(errorCode, ssouid + "[errorCode=" + errorCode + "]: "
                    + response.getHead().getMsg() + "(" + response.getHead().getRet() + ")");
        }
        PVUserVIPInfo userVIPInfo = response.getBody();
        PVUserVIPInfo.MembershipInfo membershipInfo = userVIPInfo.getMembershipInfo();
        return membershipInfo != null && membershipInfo.getProSubScribeMode() == PVUserVIPInfo.SUBSCRIBE;
    }

    private String getProExpiresAt(String ssouid, String sso_tk, String deviceKey, String mac) {
        VpResponse<PVUserVIPInfo> response = this.facadeTpDao.getCashierVpDao().getUserVIPInfo(ssouid, sso_tk,
                deviceKey, mac);
        if (response == null) {
            String errorCode = ErrorCodeConstants.USER_GET_MEMBERSHIP_FAILURE;
            throw new CashierException(errorCode, ssouid + "[errorCode=" + errorCode + "]: get membership info failed");
        }
        if (response.getHead().getRet() != 0) {
            String errorCode = ErrorCodeConstants.USER_GET_MEMBERSHIP_FAILURE;
            throw new CashierException(errorCode, ssouid + "[errorCode=" + errorCode + "]: "
                    + response.getHead().getMsg() + "(" + response.getHead().getRet() + ")");
        }
        PVUserVIPInfo userVIPInfo = response.getBody();
        PVUserVIPInfo.MembershipInfo membershipInfo = userVIPInfo.getMembershipInfo();
        String proExpiresAtTimestamp = membershipInfo.getProExpiresAt();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String proExpiresAtStr;
        try {
            proExpiresAtStr = sdf.format(Long.parseLong(proExpiresAtTimestamp));
        } catch (Exception e) {
            String errorCode = ErrorCodeConstants.USER_GET_PROEXPIRE_FAILURE;
            throw new CashierException(errorCode, ssouid + "[errorCode=" + errorCode + "]");
        }
        return proExpiresAtStr;
    }

    private ArrayList<BoundPayment> getBoundPaymentInfo(String userId) {
        if (StringUtil.isBlank(userId)) {
            String errorCode = ErrorCodeConstants.ILLEGAL_PARAMETER_USERID;
            throw new CashierException(errorCode, "[errorCode=" + errorCode + "]: param is illegal.");
        }
        List<PapBindInfo> papBindInfos = this.facadeTpDao.getCashierVpDao().getBoundPaymentByUserId(userId);
        ArrayList<BoundPayment> payments = new ArrayList<>();
        if (papBindInfos != null && !papBindInfos.isEmpty()) {
            for (PapBindInfo papBindInfo : papBindInfos) {
                BoundPayment boundPayment = new BoundPayment();
                boundPayment.setBound(System.currentTimeMillis() < papBindInfo.getExpireDate());
                boundPayment.setPaymentType(papBindInfo.getCardType());
                boundPayment.setBind_id(papBindInfo.getBindId());
                payments.add(boundPayment);
            }
        }
        return payments;
    }

    Agreements getAgreements(CommonParam commonParam) {
        List<Agreements.Agreement> agreementList = new ArrayList<>();
        CmsBlockTpResponse cmsBlockTpResponse = this.facadeTpDao.getCmsTpDao().getCmsBlockNewById(
                CashierVPConstant.AGREEMENT_CMS_BLOCK_ID, commonParam);
        if (cmsBlockTpResponse == null || cmsBlockTpResponse.getBlockContent() == null) {
            String errorCode = ErrorCodeConstants.GET_AGREEMENT_FAILURE;
            throw new CashierException(errorCode, "[errorCode=" + errorCode + "]: get agreement info failed");
        }
        for (CmsBlockContentTpResponse dataInfo : cmsBlockTpResponse.getBlockContent()) {
            if (dataInfo != null) {
                // 超级影视会员套餐
                Agreements.Agreement agreement = new Agreements.Agreement();
                if (Objects.equals(dataInfo.getId(), CashierVPConstant.PRO_AGREEMENT_ID)) {
                    agreement.setType("pro");
                } else if (Objects.equals(dataInfo.getId(), CashierVPConstant.RENEWAL_PRO_AGREEMENT_ID)) {
                    agreement.setType("renewal_pro");
                }
                agreement.setContent(dataInfo.getShorDesc());
                agreement.setTitle(dataInfo.getTitle());
                agreementList.add(agreement);
            }
        }
        Agreements agreements = new Agreements();
        agreements.setAgreementList(agreementList);
        return agreements;
    }

}
