package com.letv.mas.caller.iptv.tvproxy.vip.service;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpDao;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.BusinessCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.JumpUtil;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.LetvUserVipCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.Browser;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ErrorCodeConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LiveConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LocaleConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.bean.ExtendParam;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.bean.VipMemberShip;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.LiveTpConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstantUtils;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpDaoV2;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.LiveTicket;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.VipActivity;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.VipPackage;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.VipProductOrder;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.BaseResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.TvStreamInfoDto;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import com.letv.mas.caller.iptv.tvproxy.live.LiveService;
import com.letv.mas.caller.iptv.tvproxy.vip.VipConstants;
import com.letv.mas.caller.iptv.tvproxy.vip.builder.PaymentActivityBuilder;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.vip.util.VipUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.*;

/**
 * Author：apple on 17/3/8 11:58
 * eMail：dengliwei@le.com
 */
@Service("vipPayService")
@SuppressWarnings("all")
public class VipPayService extends VipMetadataService {
    private final static Logger LOG = LoggerFactory.getLogger(VipPayService.class);
    protected static Map<String, String> i18Message = new HashMap<String, String>();
    private static String VIP_PRODUCT_TYPE_SORT_ORDER_DEFAULT = "1,2,5";
    
    @Autowired
    VipTpDaoV2 vipTpDaoV2;
    
    @Autowired
    VipMetadataService vipMetadataService;
    
    @Autowired
    LiveService liveService;

    @Autowired
    VipTpDao vipTpDao;

    static {
        i18Message.put(VipConstants.PRODUCT_TYPE_UINT_PREFIX_KEY + VipConstants.DURATION_TYPE_YEAR + "_zh_cn", "年");
        i18Message.put(VipConstants.PRODUCT_TYPE_UINT_PREFIX_KEY + VipConstants.DURATION_TYPE_YEAR + "_zh_hk", "年");
        i18Message.put(VipConstants.PRODUCT_TYPE_UINT_PREFIX_KEY + VipConstants.DURATION_TYPE_YEAR + "_en_us", "Year");
        i18Message.put(VipConstants.PRODUCT_TYPE_UINT_PREFIX_KEY + VipConstants.DURATION_TYPE_MONTH + "_zh_cn", "月");
        i18Message.put(VipConstants.PRODUCT_TYPE_UINT_PREFIX_KEY + VipConstants.DURATION_TYPE_MONTH + "_zh_hk", "月");
        i18Message
                .put(VipConstants.PRODUCT_TYPE_UINT_PREFIX_KEY + VipConstants.DURATION_TYPE_MONTH + "_en_us", "Month");
        i18Message.put(VipConstants.PRODUCT_TYPE_UINT_PREFIX_KEY + VipConstants.DURATION_TYPE_DAY + "_zh_cn", "天");
        i18Message.put(VipConstants.PRODUCT_TYPE_UINT_PREFIX_KEY + VipConstants.DURATION_TYPE_DAY + "_zh_hk", "天");
        i18Message.put(VipConstants.PRODUCT_TYPE_UINT_PREFIX_KEY + VipConstants.DURATION_TYPE_DAY + "_en_us", "Day");

        i18Message.put(VipConstants.VIP_PACKAGE_AVERAGE_PERDAY_TEXT + "_zh_cn", "(￥{0}/天)");
        i18Message.put(VipConstants.VIP_PACKAGE_AVERAGE_PERDAY_TEXT + "_zh_hk", "(HK${0}/天)");
        i18Message.put(VipConstants.VIP_PACKAGE_AVERAGE_PERDAY_TEXT + "_en_us", "(${0}/day)");
    }

    /**
     * get checkout counter data
     * @param productId
     * @param activityIds
     * @param position
     * @param commonParam
     * @return
     */
    public Response<CheckOutCounter> getCheckoutCounterNewV2(String productId, String activityIds, String position,
                                                             CommonParam commonParam) {
        CheckOutCounter data = new CheckOutCounter();
        String errorCode = null;
        // all checkout counter types need get vippackage data.
        Integer pid = (StringUtil.isBlank(productId)) ? 0 : Integer.parseInt(productId);
        BossTpResponse<List<VipPackage>> packageTpResponse = vipTpDaoV2.getPackageByProductId(
                pid, commonParam);
        if (packageTpResponse == null || !packageTpResponse.isSucceed()
                || CollectionUtils.isEmpty(packageTpResponse.getData())) {
            // get vippackage data failured.
            errorCode = ErrorCodeConstants.PAY_GET_PACKAGE_TYPE_FAILURE;
            LOG.info("getCheckoutCounterNewV2_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + productId + "_" + activityIds + "_" + position + "[errorCode=" + errorCode
                    + "]: get vip package failed.");
        } else {
            List<VipPackage> packageList = packageTpResponse.getData();
            Map<String, CheckOutCounter.ProductType> productDatas = new HashMap<String, CheckOutCounter.ProductType>();
            Map<String, CheckOutCounter.ProductType.ProductData> productDataMap = new HashMap<String, CheckOutCounter.ProductType.ProductData>();
            // default image for checkout counter
            String defaultImg = this.getVipConfigInfo(VipConstants.VIP_CHECKOUT_COUNTER_DEFAULT_IMG, "");
            boolean isVideoSale = false;
            if (position != null && position.indexOf(VipTpConstant.USER_TOUCH_POSITION_VIDEO_SALE) > -1) {
                isVideoSale = true;
                defaultImg = this.getVipConfigInfo(VipConstants.VIP_CHECKOUT_COUNTER_DEFAULT_IMG_VIDEO_SALE, "");
            }
            // set vip package info and get year package id
            String yearPackageId = this.setVipPackageValue(packageList, productDatas, productDataMap, defaultImg,
                    commonParam);
            if (isVideoSale) {// video sale default year vip package
                productId = yearPackageId;
            }
            if (CollectionUtils.isEmpty(productDatas) || CollectionUtils.isEmpty(productDataMap)
                    || (isVideoSale && StringUtil.isBlank(productId))) {
                errorCode = ErrorCodeConstants.PAY_GET_PACKAGE_TYPE_FAILURE;
                LOG.info("getCheckoutCounterNewV2_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + productId + "_" + activityIds + "_" + position + "[errorCode=" + errorCode
                        + "]: parse vippackage result is empty.");
            } else {
                Map<String, PaymentActivityDto> paymentActivityMap = null;
                if (StringUtil.isBlank(position)) {// non activity position
                    productId = "";
                    activityIds = this.getVipConfigInfo(VipConstants.VIP_CHECKOUT_COUNTER_DEFAULT_ACTIVITY_KEY, "");
                }
                if (StringUtil.isNotBlank(activityIds)) {
                    paymentActivityMap = this.getPaymentActivityById(activityIds, position, commonParam);
                    if (!CollectionUtils.isEmpty(paymentActivityMap)) {
                        if (StringUtil.isBlank(position)) {
                            for (Map.Entry<String, PaymentActivityDto> entry : paymentActivityMap.entrySet()) {
                                PaymentActivityDto dto = entry.getValue();
                                productId = String.valueOf(dto.getMonthType());
                            }
                        }
                    } else if (StringUtil.isNotBlank(position)) {
                        // if from activity entry and activity is invalid then
                        // return error.
                        errorCode = ErrorCodeConstants.VIP_TOUCH_ACTIVITY_OFFLINE;
                        LOG.info("getCheckoutCounterNewV2_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                + "_" + productId + "_" + activityIds + "_" + position
                                + " parse direct payment activity [" + activityIds + "] failed.");
                    }
                }
                if (errorCode == null) {
                    // set checkout counter data
                    this.setCheckOutCounterValue(data, productDataMap, productDatas, productId, position, commonParam);
                    if (CollectionUtils.isEmpty(data.getProductDatas())) {
                        errorCode = ErrorCodeConstants.PAY_GET_PACKAGE_TYPE_FAILURE;
                        LOG.info("getCheckoutCounterNewV2_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                + "_" + productId + "_" + activityIds + "_" + position + "[errorCode=" + errorCode
                                + "]: parse vippackage result is empty.");
                    } else {
                        // merge vip package and activity info
                        this.mergePackageAndActivityValue(data.getProductDatas(), paymentActivityMap, productDataMap,
                                commonParam.getLangcode());
                        // set checkout counter show text
                        this.setCheckoutCounterShowText(data, commonParam);
                    }
                }
            }
        }

        Response<CheckOutCounter> response = new Response<CheckOutCounter>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setData(data);
        }
        return response;
    }

    /**
     * generate order without payment channel
     * @param productId
     * @param price
     * @param activityIds
     *            activity ids for vip package
     * @param couponCode
     * @param purchaseType
     *            the type of product
     * @param av
     * @param productName
     * @param callBackUrl
     *            参与签名字段
     * @param displayName
     *            参与签名字段
     * @param commonParam
     * @return
     */
    public Response<PurchaseVipDto> purchaseProductV2(String productId, String activityIds, String cpsId,
                                                      String couponCode, Integer purchaseType, Integer av, String position, String productName,
                                                      String callBackUrl, String displayName, CommonParam commonParam) {
        PurchaseVipDto data = new PurchaseVipDto();
        String errorCode = null;

        if (purchaseType == null || purchaseType < VipTpConstant.ContentType.ALBUM.getCode()
                || purchaseType > VipTpConstant.ContentType.LIVE_TICKET.getCode()) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            LOG.info("purchaseProductV2_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + productId
                    + "_" + purchaseType + "[errorCode=" + errorCode + "]: illegal parameters.");
        } else if (av != null && VipTpConstant.BROADCAST_APK_VERSION_TPSDK != av
                && StringUtils.isBlank(commonParam.getUserId())) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            LOG.info("purchaseProductV2_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + productId
                    + "_" + purchaseType + "[errorCode=" + errorCode + "]: illegal parameters.");
        } else {
            if (StringUtil.isBlank(position)) {
                activityIds = vipMetadataService.getVipConfigInfo(
                        VipConstants.VIP_CHECKOUT_COUNTER_DEFAULT_ACTIVITY_KEY, "");
                if (StringUtil.isNotBlank(activityIds)) {
                    BossTpResponse<List<VipActivity>> activityResponse = vipTpDaoV2
                            .getActivityData(Integer.valueOf(activityIds), commonParam);
                    if (activityResponse == null || !activityResponse.isSucceed()
                            || CollectionUtils.isEmpty(activityResponse.getData())) {
                        LOG.info("purchaseProductV2_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                                + productId + "_" + purchaseType + " get stable payment activity [" + activityIds
                                + "] failed.");
                        activityIds = "";
                    } else {
                        Map<String, PaymentActivityDto> map = this.parsePaymentActivityFromTpV2(
                                activityResponse.getData(), commonParam.getLangcode());
                        if (CollectionUtils.isEmpty(map)) {
                            activityIds = "";
                        } else {
                            PaymentActivityDto paymentActivityDto = map.get(activityIds);
                            if ((paymentActivityDto != null) && (productId != null)
                                    && productId.equals(String.valueOf(paymentActivityDto.getMonthType()))) {
                                activityIds = paymentActivityDto.getActivityId();
                            } else {
                                activityIds = "";
                            }
                        }
                    }
                }
            }
            BossTpResponse<VipProductOrder> purchaseProductResponse = vipTpDaoV2
                    .purchaseProductV2(productId, purchaseType, couponCode, cpsId, commonParam);
            if (purchaseProductResponse == null || !purchaseProductResponse.isSucceed()
                    || null == purchaseProductResponse.getData()
                    || StringUtil.isBlank(purchaseProductResponse.getData().getOrderid())) {
                errorCode = ErrorCodeConstants.PAY_FAILURE;
                if (StringUtil.isNotBlank(purchaseProductResponse.getMsg())) {
                    errorCode = purchaseProductResponse.getMsg();
                }
            } else {
                String orderId = purchaseProductResponse.getData().getOrderid();
                data.setCorderid(orderId);
                data.setOrdernumber(orderId);
                ExtendParam extendParam = new ExtendParam();
                data.setExtendParam(extendParam);
                extendParam.setMerchant_no(orderId);
                extendParam.setOut_trade_no(orderId);
                extendParam.setProduct_id(String.valueOf(productId));
                extendParam.setPrice(String.valueOf(purchaseProductResponse.getData().getPayable()));
                if (LocaleConstant.Langcode.ZH_HK.equalsIgnoreCase(commonParam.getLangcode())) {
                    extendParam.setLanguage("0");// Traditional
                } else if (LocaleConstant.Langcode.EN_US.equalsIgnoreCase(commonParam.getLangcode())) {
                    extendParam.setLanguage("1");// English
                } else {
                    extendParam.setLanguage("2");// Simplified
                }
                String baseUrl = "";
                if (TerminalUtil.isLetvHK(commonParam)) { // TODO
                    extendParam.setService(VipConstants.VIP_PURCHASE_EXTEND_SERVICE_HK);
                    extendParam.setNotify_url(VipConstants.VIP_PURCHASE_EXTEND_NOTIFY_URL_HK);
                    extendParam.setMerchant_business_id(VipConstants.VIP_PURCHASE_EXTEND_BUSINESS_ID_HK);
                    extendParam.setCurrency(VipConstants.VIP_PURCHASE_EXTEND_CURRENCY_HK);
                    extendParam.setProduct_urls(VipConstants.VIP_PURCHASE_EXTEND_PRODUCT_URLS_HK);
                    extendParam.setLocale(VipConstants.VIP_PURCHASE_EXTEND_LOCAL_HK);
                    extendParam.setIs_auth(true);
                    extendParam.setIs_create_token(true);
                    String img4Sdk = vipMetadataService.getVipConfigInfo(
                            VipTpConstant.VIP_USPAY_PRODUCT_IMG_KEY, VipTpConstant.VIP_USPAY_PRODUCT_IMG);
                    baseUrl = VipTpConstant.VIP_GET_HK_PAY_TOKEN_URL;
                    GetPayTokenResponse tokenResponse = vipTpDao.getPayTokenForOverseas(baseUrl,
                            VipTpConstant.SIGN_KEY_HK, productName, extendParam, displayName, img4Sdk, commonParam);
                    if (tokenResponse != null && tokenResponse.getCode() != null && tokenResponse.getCode() == 0) {
                        // get pay token success
                        extendParam.setToken(tokenResponse.getToken());
                        extendParam.setLepayOrderNo(tokenResponse.getLepayOrderNo());
                    } else {
                        // get pay token failure
                        errorCode = ErrorCodeConstants.PAY_FAILURE;
                    }
                } else if (TerminalUtil.isLetvUs(commonParam)) { // TODO
                    extendParam.setService(VipConstants.VIP_PURCHASE_EXTEND_SERVICE_US);
                    extendParam.setNotify_url(VipConstants.VIP_PURCHASE_EXTEND_NOTIFY_URL_US);
                    extendParam.setMerchant_business_id(VipConstants.VIP_PURCHASE_EXTEND_BUSINESS_ID_US);
                    extendParam.setCurrency(VipConstants.VIP_PURCHASE_EXTEND_CURRENCY_US);
                    extendParam.setProduct_urls(VipConstants.VIP_PURCHASE_EXTEND_PRODUCT_URLS_US);
                    extendParam.setLocale(VipConstants.VIP_PURCHASE_EXTEND_LOCAL_US);
                    extendParam.setIs_auth(false);
                    extendParam.setIs_create_token(true);
                    String img4Sdk = vipMetadataService.getVipConfigInfo(
                            VipTpConstant.VIP_USPAY_PRODUCT_IMG_KEY, VipTpConstant.VIP_USPAY_PRODUCT_IMG);
                    baseUrl = VipTpConstant.VIP_GET_US_PAY_TOKEN_URL;
                    GetPayTokenResponse tokenResponse = vipTpDao.getPayTokenForOverseas(baseUrl,
                            VipTpConstant.SIGN_KEY_US, productName, extendParam, displayName, img4Sdk, commonParam);
                    if (tokenResponse != null && tokenResponse.getCode() != null && tokenResponse.getCode() == 0) {
                        // get pay token success
                        extendParam.setToken(tokenResponse.getToken());
                        extendParam.setLepayOrderNo(tokenResponse.getLepayOrderNo());
                    } else {
                        // get pay token failure
                        errorCode = ErrorCodeConstants.PAY_FAILURE;
                    }
                } else {// 没有分配支付业务线id的应用，默认使用大陆的支付业务线id
                    extendParam.setService(VipConstants.VIP_PURCHASE_EXTEND_SERVICE_CN);
                    extendParam.setNotify_url(VipTpConstant.VIP_V2_PURCHASE_EXTEND_NOTIFY_URL_CN);
                    if (TerminalUtil.isLetvCommon(commonParam)) {
                        extendParam.setMerchant_business_id(VipConstants.VIP_PURCHASE_EXTEND_COMMON_BUSINESS_ID);// 通用版业务线id
                    } else {
                        extendParam.setMerchant_business_id(VipConstants.VIP_PURCHASE_EXTEND_BUSINESS_ID);
                    }
                    extendParam.setCurrency(VipConstants.VIP_PURCHASE_EXTEND_CURRENCY_CN);
                    extendParam.setProduct_urls(VipConstants.VIP_PURCHASE_EXTEND_PRODUCT_URLS_CN);
                    extendParam.setUser_id(commonParam.getUserId());
                    extendParam.setDisplayName(displayName);

                    // 解决运营商对url参数中ip篡改导致支付验签问题 @dengliwei 20170210
                    String tmpIp = commonParam.getClientIp();
                    if (StringUtil.isNotBlank(tmpIp) && IpAddrUtil.isIP(tmpIp)) {
                        tmpIp = Long.toString(IpAddrUtil.ipToLong(tmpIp));
                    }
                    extendParam.setIp(tmpIp);

                    extendParam.setInput_charset(VipConstants.VIP_PURCHASE_EXTEND_INPUT_CHARSET);
                    extendParam.setProduct_name(productName);
                    extendParam.setProduct_desc(productName);
                    extendParam.setTimestamp(TimeUtil.getDateStringFromLong(System.currentTimeMillis(),
                            TimeUtil.SIMPLE_DATE_FORMAT));
                    if (StringUtil.isNotBlank(callBackUrl)) {
                        extendParam.setCall_back_url(CommonUtil.urlEncode(callBackUrl));
                    }
                    String sign = vipTpDao.getPurchaseVipSign(extendParam, commonParam);
                    extendParam.setSign(sign);
                }
            }
        }

        Response<PurchaseVipDto> response = new Response<PurchaseVipDto>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setData(data);
        }
        return response;
    }

    /**
     * set vip package info and get year package id
     * @param packageList
     * @param vipPackages
     * @param packageOptionMap
     */
    private String setVipPackageValue(List<VipPackage> packageList,
            Map<String, CheckOutCounter.ProductType> vipPackages,
            Map<String, CheckOutCounter.ProductType.ProductData> packageOptionMap, String defaultImg,
            CommonParam commonParam) {
        String yearPackageId = null;
        if (CollectionUtils.isEmpty(packageList)) {
            return yearPackageId;
        }
        List<String> packList = null;
        if (TerminalUtil.isLetvUs(commonParam)) {
            // 美国行货要控制展示哪些套餐
            String packagesConfig = this.getVipConfigInfo(VipConstants.VIP_PRODUCT_PACKAGES_US, "");
            if (StringUtil.isNotBlank(packagesConfig)) {
                packList = new ArrayList<String>(Arrays.asList(packagesConfig.split(",")));
            }
        }

        List<Integer> types = new ArrayList<Integer>();
        types.add(VipConstants.DURATION_TYPE_YEAR);
        types.add(VipConstants.DURATION_TYPE_MONTH);
        types.add(VipConstants.DURATION_TYPE_DAY);
        for (VipPackage packageRecord : packageList) {
            if (StringUtil.isBlank(packageRecord.getTerminal())) {
                // TODO;
                continue;
            }
            List<String> terminals = Arrays.asList(packageRecord.getTerminal().split(","));
            if (CollectionUtils.isEmpty(terminals) || !terminals.contains(VipPackage.TERMINAL_TV)
                    || packageRecord.getPrice() <= 0) {
                continue;
            }
            if (!CollectionUtils.isEmpty(packList) && !packList.contains(packageRecord.getDurationType())) {
                continue;
            }
            Integer duration = packageRecord.getDuration();

            for (int type : types) {
                String typeStr = String.valueOf(type);
                CheckOutCounter.ProductType productType = null;
                if (type == packageRecord.getDurationType()) {
                    if (packageRecord.getDurationType() == VipConstants.DURATION_TYPE_YEAR) {
                        yearPackageId = String.valueOf(packageRecord.getDurationType());
                    }
                    if (vipPackages.get(typeStr) == null) {
                        productType = new CheckOutCounter.ProductType();
                        vipPackages.put(typeStr, productType);
                    } else {
                        productType = vipPackages.get(typeStr);
                    }
                    if (productType.getItems() == null) {
                        List<CheckOutCounter.ProductType.ProductData> items = new LinkedList<CheckOutCounter.ProductType.ProductData>();
                        CheckOutCounter.ProductType.ProductData product = new CheckOutCounter.ProductType.ProductData();
                        this.setProductDataValue(product, packageRecord, duration, typeStr, defaultImg, commonParam);
                        packageOptionMap.put(product.getProductId(), product);
                        items.add(product);
                        productType.setItems(items);
                    } else {
                        CheckOutCounter.ProductType.ProductData product = new CheckOutCounter.ProductType.ProductData();
                        this.setProductDataValue(product, packageRecord, duration, typeStr, defaultImg, commonParam);
                        packageOptionMap.put(product.getProductId(), product);
                        productType.getItems().add(product);
                    }
                    break;
                }
            }
        }
        return yearPackageId;
    }

    private void setProductDataValue(CheckOutCounter.ProductType.ProductData productData, VipPackage packageRecord,
            Integer time, String typeStr, String defaultImg, CommonParam commonParam) {
        if (productData == null || packageRecord == null) {
            return;
        }
        productData.setProductId(String.valueOf(packageRecord.getId()));
        productData
                .setShowName(time
                        + i18Message.get(VipConstants.PRODUCT_TYPE_UINT_PREFIX_KEY + typeStr + "_"
                                + commonParam.getLangcode()));
        productData.setOrder(time);
        productData.setTitle(packageRecord.getName());
        productData.setProductName(packageRecord.getName());
        productData.setImg(defaultImg);
        productData.setDesc(packageRecord.getDesc());
        productData.setDurationType(Integer.valueOf(typeStr));
        if (TerminalUtil.isLetvUs(commonParam)) {
            // TODO: curPrice, oriPrice, desc
        } else {
            productData.setDesc(packageRecord.getDesc());
            if (packageRecord.getPrice() != null) {
                productData.setCurrentPrice(String.valueOf(packageRecord.getPrice()));
                productData.setOriginPrice(String.valueOf(packageRecord.getPrice()));
            }
        }
        productData.setDuration(String.valueOf(packageRecord.getDuration()));
        if (null != packageRecord.getVipDiscountPriceInfo() && packageRecord.getVipDiscountPriceInfo().size() > 0) {
            // TODO
        } else {
            productData.setDiscount(VipUtil.getDiscount(String.valueOf(packageRecord.getPrice()),
                    String.valueOf(packageRecord.getOriginalPrice())));
        }
    }

    /**
     * get activity by package id
     * @param packageIds
     *            package ids
     * @param position
     *            the position of checkout counter entry
     * @param commonParam
     * @return
     */
    private Map<String, PaymentActivityDto> getPaymentActivityById(String packageIds, String position,
            CommonParam commonParam) {
        Map<String, PaymentActivityDto> paymentActivityMap = null;

        BossTpResponse<List<VipActivity>> activityResponse = vipTpDaoV2.getActivityData(
                Integer.valueOf(packageIds), commonParam);
        if (activityResponse == null || !activityResponse.isSucceed()
                || CollectionUtils.isEmpty(activityResponse.getData())) {
            LOG.info("getPaymentActivityById_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + packageIds + "_" + position + " get payment activity [" + packageIds + "] failed.");
        } else {
            paymentActivityMap = this.parsePaymentActivityFromTpV2(activityResponse.getData(),
                    commonParam.getLangcode());
        }
        return paymentActivityMap;
    }

    public Map<String, PaymentActivityDto> parsePaymentActivityFromTpV2(List<VipActivity> dataList, String langcode) {
        if (CollectionUtils.isEmpty(dataList)) {
            return null;
        }
        Map<String, PaymentActivityDto> paymentActivityMap = new HashMap<String, PaymentActivityDto>();
        for (VipActivity activityData : dataList) {
            PaymentActivityDto activityDto = PaymentActivityBuilder.build(activityData, langcode);
            if (activityDto != null) {
                paymentActivityMap.put(activityDto.getActivityId(), activityDto);
            }
        }

        return paymentActivityMap;
    }

    /**
     * set checkout counter show text and sort vip package type
     * @param data
     *            checkout counter data
     * @param commonParam
     */
    private void setCheckoutCounterShowText(CheckOutCounter data, CommonParam commonParam) {
        boolean isDirect = VipConstants.VIP_CHECKOUTCOUNTER_TYPE_DIRECT.equals(data.getCheckOutCounterType());
        List<CheckOutCounter.TypeInfo> typeInfos = new LinkedList<CheckOutCounter.TypeInfo>();
        for (Map.Entry<String, CheckOutCounter.ProductType> entry : data.getProductDatas().entrySet()) {
            CheckOutCounter.ProductType productType = entry.getValue();
            if (productType == null || CollectionUtils.isEmpty(productType.getItems())) {
                continue;
            }
            CheckOutCounter.TypeInfo typeInfo = new CheckOutCounter.TypeInfo();
            typeInfo.setProductType(entry.getKey());
            typeInfo.setProductTypeName(i18Message.get(VipConstants.PRODUCT_TYPE_UINT_PREFIX_KEY + entry.getKey() + "_"
                    + commonParam.getLangcode()));
            typeInfos.add(typeInfo);
            Collections.sort(productType.getItems());
            if (isDirect) {
                // direct checkout counter no need to calculate
                // the average price
                continue;
            }
            for (CheckOutCounter.ProductType.ProductData productData : productType.getItems()) {
                Float price = StringUtil.toFloat(productData.getCurrentPrice(), 0F);
                Float duration = StringUtil.toFloat(productData.getDuration());
                if (price != null && price > 0 && duration != null && duration > 0) {
                    DecimalFormat format = new DecimalFormat("#0.0");
                    String msgCode = null;
                    if (productData.getDurationType() == VipConstants.DURATION_TYPE_YEAR) {
                        duration = duration * 12;
                        msgCode = VipConstants.VIP_PACKAGE_AVERAGE_PERYEAR_TEXT;
                    } else if (productData.getDurationType() == VipConstants.DURATION_TYPE_MONTH) {
                        msgCode = VipConstants.VIP_PACKAGE_AVERAGE_PERMONTH_TEXT;
                    } else if (productData.getDurationType() == VipConstants.DURATION_TYPE_DAY) {
                        msgCode = i18Message.get(VipConstants.VIP_PACKAGE_AVERAGE_PERDAY_TEXT + "_"
                                + commonParam.getLangcode());
                    }
                    if (msgCode != null) {
                        String average = format.format(price / duration);
                        if (average != null && average.indexOf(".") > 0) {
                            Integer i = StringUtil.toInteger(average.substring(0, average.indexOf(".")), 0);
                            Float f = StringUtil.toFloat(average, 0F);
                            if (i >= f) {
                                average = i + "";
                            }
                        }
                        String[] codeMap = { average };
                        String msg = null;
                        if (productData.getDurationType() == VipConstants.DURATION_TYPE_DAY) {
                            msg = MessageFormat.format(msgCode, codeMap);
                        } else {
                            msg = MessageUtils.getMessage(msgCode, commonParam.getLangcode(), codeMap);
                        }
                        productData.setAdditionalText(msg);
                    }
                }
            }
        }
        String sorts = VIP_PRODUCT_TYPE_SORT_ORDER_DEFAULT;
        if (sorts != null) {
            String[] types = sorts.split(",");
            List<CheckOutCounter.TypeInfo> sortList = new LinkedList<CheckOutCounter.TypeInfo>();
            for (String type : types) {
                for (CheckOutCounter.TypeInfo typeInfo : typeInfos) {
                    if (type != null && type.equals(typeInfo.getProductType())) {
                        sortList.add(typeInfo);
                    }
                }
            }
            data.setProductTypes(sortList);
        } else {
            data.setProductTypes(typeInfos);
        }

    }

    private void mergePackageAndActivityValue(Map<String, CheckOutCounter.ProductType> productTypes,
            Map<String, PaymentActivityDto> paymentActivityMap,
            Map<String, CheckOutCounter.ProductType.ProductData> productDataMap, String langcode) {
        if (CollectionUtils.isEmpty(productTypes) || CollectionUtils.isEmpty(productDataMap)
                || CollectionUtils.isEmpty(paymentActivityMap)) {
            return;
        }
        for (Map.Entry<String, PaymentActivityDto> entry : paymentActivityMap.entrySet()) {
            String activityId = entry.getKey();
            PaymentActivityDto paymentActivityDto = entry.getValue();
            Integer type = paymentActivityDto.getType();
            if (paymentActivityDto.getMonthType() == null || type == null || type != 1) {
                continue;
            }
            CheckOutCounter.ProductType.ProductData productData = productDataMap.get(String.valueOf(paymentActivityDto
                    .getMonthType()));
            if (productData == null) {
                continue;
            }
            productData.setTitle(paymentActivityDto.getBodyText());
            productData.setSubTitle(paymentActivityDto.getLableText());
            productData.setDesc(paymentActivityDto.getPackageText());
            String img = paymentActivityDto.getImg();
            if (StringUtil.isNotBlank(img)) {
                productData.setImg(img);
            }
            String url = paymentActivityDto.getUrl();
            if (StringUtil.isNotBlank(url)) {
                productData.setUrl(url);
                Browser browser = new Browser();
                browser.setDataType(DataConstant.DATA_TYPE_BROWSER);
                browser.setBrowserType(DataConstant.BROWSER_TYPE_BUILTIN);
                browser.setOpenType(DataConstant.BROWSER_OPENTYPE_NONE);
                browser.setUrl(url);
                Map<String, String> urlMap = new HashMap<String, String>();
                urlMap.put("0", url);
                browser.setUrlMap(urlMap);
                JumpUtil.bulidJumpObj(browser, DataConstant.DATA_TYPE_BROWSER, null, null);
                productData.setDetail(browser);
            } else {
                productData.setUrl(null);
                productData.setDetail(null);
            }
            Float packagePrice = StringUtil.toFloat(productData.getCurrentPrice(), 0F);
            Float activityDiscount = StringUtil.toFloat(paymentActivityDto.getDiscount(), 0F);
            if (packagePrice >= activityDiscount) {
                productData.setAdditionalText(productData.getCurrentPrice());
                productData.setActivityIds(activityId);
                DecimalFormat format = new DecimalFormat("#0.00");
                String price = format.format(packagePrice - activityDiscount);
                productData.setCurrentPrice(price);
            } else {
                productData.setAdditionalText(productData.getCurrentPrice());
                productData.setActivityIds(activityId);
                productData.setCurrentPrice("0.0");
            }
            if (paymentActivityDto.getCoupon() != null && paymentActivityDto.getCoupon() == 13) {
                productData.setSupportVoucher(1);
            } else {
                productData.setSupportVoucher(0);
                productData.setUnsupportVoucherText(MessageUtils.getMessage(
                        VipConstants.VIP_ACTIVITY_NOT_SUPPORT_VOUCHER, langcode));
            }
        }
    }

    /**
     * 校验当前用户是否有权播放某一直播
     * @param pid
     * @param liveId
     * @param streamId
     * @param isstart
     * @param termid
     * @param selectId
     * @param commonParam
     * @return
     */
    public Response<CheckLiveDto> checkLiveV2(String pid, String liveId, String streamId, Integer isstart,
                                              String termid, String selectId, String streamCode, CommonParam commonParam) {
        Response<CheckLiveDto> response = new Response<CheckLiveDto>();
        String mac = commonParam.getMac();
        StringBuilder logPrefix = new StringBuilder("checkLiveV2_").append(mac).append("_")
                .append(commonParam.getDeviceKey()).append("_").append(commonParam.getUserId()).append("_").append(pid)
                .append("_").append(liveId);
        String errorCode = null;
        String playUrl = "";
        // 参数校验
        int validCode = VipMsgCodeConstant.REQUEST_SUCCESS;
        if (StringUtils.isBlank(pid)) {
            validCode = VipMsgCodeConstant.VIP_CHECK_LIVE_REQUEST_PID_EMPTY;
        } else if (StringUtils.isBlank(liveId)) {
            validCode = VipMsgCodeConstant.VIP_CHECK_LIVE_REQUEST_LIVEID_EMPTY;
        } else if (StringUtils.isBlank(mac)) {
            validCode = VipMsgCodeConstant.REQUEST_MAC_EMPTY;
        }
        // else if (StringUtils.isBlank(splatId)) {
        // validCode = VipMsgCodeConstant.VIP_CHECK_LIVE_REQUEST_SPLATID_EMPTY;
        // }
        if (VipMsgCodeConstant.REQUEST_SUCCESS != validCode) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            response = (Response<CheckLiveDto>) LetvUserVipCommonConstant.setErrorResponse(response, errorCode,
                    errorCode + ErrorMsgCodeUtil.parseErrorMsgCode(BusinessCodeConstant.LIVE, validCode),
                    commonParam.getLangcode());
        } else {
            // 2016-08-26,splatid获取放在服务端，不再由客户端传值
            String splatId = LiveConstants.getLiveSplatIdByTerminalApplication(commonParam.getTerminalApplication(),
                    LiveTpConstants.LIVE_SPLITID_TV_LIVE_ROOM);
            if (StringUtil.isNotBlank(selectId) && StringUtil.isNotBlank(streamCode)) {
                List<TvStreamInfoDto> streamInfo = liveService.tp2StreamInfo(pid, selectId,
                        commonParam.getLangcode(), commonParam.getBroadcastId(), splatId);
                for (TvStreamInfoDto info : streamInfo) {
                    if (streamCode.equalsIgnoreCase(info.getCode())) {
                        playUrl = info.getLiveUrl();
                        streamId = HttpUtil.getUrlParamValue(playUrl, "stream_id");
                        break;
                    }
                }
            }
            CheckLiveTpResponse tpResponse = vipTpDaoV2.doCheckLiveV2(pid, liveId, streamId,
                    splatId, isstart, termid, logPrefix.toString(), commonParam);
            if (tpResponse != null && tpResponse.needActiveTicket()) {
                // 如果第一次鉴权失败，可能是因购买直播后未执行用券操作，则需执行激活直播券--重新鉴权操作
                LOG.info(logPrefix + "[errorCode=" + ErrorCodeConstants.PAY_CHECK_LIVE_FAILURE
                        + "]: check live permission failure 1st time, will try to active the live ticket.");
                // String channel = null; // 频道，liveId的0-1位，共两位
                // String category = null; // 赛事类型，liveId的2-4位，共三位
                // String season = null; // 年份，liveId的5-8位，共四位
                // String turn = null; // 轮次，liveId的9-11位，共三位
                // String game = null; // 场次，liveId的12-15位，共四位
                // if (StringUtils.isNotBlank(liveId)) {
                // channel = liveId.substring(0, 2);
                // category = liveId.substring(2, 5);
                // season = liveId.substring(5, 9);
                // turn = liveId.substring(9, 12);
                // game = liveId.substring(12);
                // }
                // 1.查询直播券；
                // 注意，该yuanxian接口有bug，仅能在购买成功后的第一次查询能查到数据，第二次及以后的查询则无法查到数据；该问题会对当前逻辑流程造成影响；
                // 暂时性可用解决方案为，如果第一次鉴权失败，则直接暴力激活该券，再进行第二次鉴权
                BossTpResponse<List<LiveTicket>> checkTicketTpResponse = vipTpDaoV2
                        .checkUserLiveTicketV2(liveId, logPrefix.toString(), commonParam);
                if (checkTicketTpResponse == null
                        || !vipTpDaoV2.hasBoughtLiveTicket(liveId, checkTicketTpResponse)) {
                    LOG.info(logPrefix + "[errorCode=" + ErrorCodeConstants.PAY_CHECK_LIVE_FAILURE
                            + "]: check user live ticket failure.");
                } else {
                    List<LiveTicket> packageList = checkTicketTpResponse.getData();
                    // 2.激活直播券
                    BossTpResponse<String> activeTicketTpResponse = vipTpDaoV2
                            .activeUserLiveTicketV2(liveId, logPrefix.toString(), commonParam);
                    if (activeTicketTpResponse == null || !activeTicketTpResponse.isSucceed()) {
                        LOG.info(logPrefix + "[errorCode=" + ErrorCodeConstants.PAY_CHECK_LIVE_FAILURE
                                + "]: active user live ticket failure.");
                    } else {
                        // 第二次鉴权
                        LOG.info(logPrefix + ", active user live ticket successful.");
                        tpResponse = vipTpDaoV2.doCheckLiveV2(pid, liveId, streamId, splatId,
                                isstart, termid, logPrefix.toString(), commonParam);
                    }
                }
            }

            if (tpResponse == null) {// 因为鉴权失败会返回其他信息，所以此处只判空
                // 请求失败
                LOG.info(logPrefix.toString() + "[errorCode=" + ErrorCodeConstants.PAY_CHECK_LIVE_FAILURE
                        + "]: check live failure.");
                response = (Response<CheckLiveDto>) LetvUserVipCommonConstant.setErrorResponse(response,
                        ErrorCodeConstants.PAY_CHECK_LIVE_FAILURE, commonParam.getLangcode());
            } else {
                CheckLiveDto data = new CheckLiveDto();
                if (VipTpConstant.TP_STATTUS_CODE_1.equals(tpResponse.getStatus())) {
                    // 请求直接返回有权限，已购买，可以试看，试看结束
                    if (tpResponse.getIsPre()) {
                        // 可以试看
                        data.setHasPlayPermission(VipConstants.CHECK_LIVE_HAVE_TRY_PLAY_PERMISSSION);
                        LOG.info(logPrefix + ", user have try play permission of this live.");
                    } else {
                        // 可以观看完整
                        data.setHasPlayPermission(VipConstants.CHECK_LIVE_HAVE_PLAY_PERMISSSION);
                        LOG.info(logPrefix + ", user have play permission of this live.");
                    }
                    data.setToken(tpResponse.getToken());
                    data.setUinfo(tpResponse.getUinfo());
                    data.setCurTime(tpResponse.getCurTime());
                    data.setLiveStatus(tpResponse.getLiveStatus());
                    data.setOrdertype(tpResponse.getOrdertype());
                    data.setPrestart(tpResponse.getPrestart());
                    data.setPreend(tpResponse.getPreend());
                    data.setTeamName(tpResponse.getTeamName());
                    LOG.info(logPrefix + data.initTryPlayInfo());// 初始化试看信息，并打个日志记录结果
                    data.setTryPlayText(MessageUtils.getMessage(VipTpConstant.LIVE_TRY_PLAY_TEXT,
                            commonParam.getLangcode()));
                    playUrl = getLiveUrl(playUrl, data.getToken(), data.getUinfo(), commonParam);
                } else {
                    CheckLiveTpResponse.CheckLiveError error = tpResponse.getError();
                    if (error != null) {// 参数校验失败，boss直接不鉴权，则认为无观看权限
                        CheckLiveDto.CheckLiveErrorDto checkLiveErrorDto = new CheckLiveDto.CheckLiveErrorDto();
                        checkLiveErrorDto.setCheckLiveErrorValue(error);
                        data.setError(checkLiveErrorDto);
                        data.setHasPlayPermission(VipConstants.CHECK_LIVE_NO_PLAY_PERMISSSION);
                        LOG.info(logPrefix + ", parameter validate failure.");
                    }
                    CheckLiveTpResponse.CheckLiveInfo info = tpResponse.getInfo();
                    if (info != null) {// info中code为1004时，表示试看结束
                        CheckLiveDto.CheckLiveInfoDto checkLiveInfoDto = new CheckLiveDto.CheckLiveInfoDto();
                        checkLiveInfoDto.setCheckLiveInfoValue(info);
                        data.setInfo(checkLiveInfoDto);
                        if ("1004".equals(info.getCode())) {// 用户没有试看权限，表示试看结束
                            data.setHasPlayPermission(VipConstants.CHECK_LIVE_NO_TRY_PLAY_PERMISSSION);
                            LOG.info(logPrefix + ", user haven't try play permission of this live.");
                        } else if (VipTpConstantUtils.checkLiveHasBounghtByCode(info.getCode())) {// 已经购买过该场直播，但直播未开始
                            data.setHasPlayPermission(VipConstants.CHECK_LIVE_HAVE_PLAY_PERMISSSION);
                            LOG.info(logPrefix + ", user have play permission of this live, but the live not start.");
                        } else {
                            data.setHasPlayPermission(VipConstants.CHECK_LIVE_NO_PLAY_PERMISSSION);
                            LOG.info(logPrefix + ", user haven't play permission of this live.");
                        }
                    }
                    if (data.getHasPlayPermission() == null) {// 不是上述的情况默认为不可观看
                        data.setHasPlayPermission(VipConstants.CHECK_LIVE_NO_PLAY_PERMISSSION);
                        LOG.info(logPrefix + ", user haven't play permission of this live.");
                    }
                }
                data.setPlayUrl(playUrl);
                response.setData(data);
                response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
            }
        }

        return response;
    }

    /**
     * 查询包月服务状态
     * @param commonParam
     * @return
     */
    public Response<MonthlyPaymentStatusDto> getMonthlyPaymentStatus(CommonParam commonParam) {
        String errorCode = null;
        String errorMsgCode = null;
        Response<MonthlyPaymentStatusDto> response = new Response<MonthlyPaymentStatusDto>();
        MonthlyPaymentStatusDto data = new MonthlyPaymentStatusDto();
        MemberShipInfoResponse<VipMemberShip> getMonthlyPaymentRespone = vipTpDaoV2
                .queryMonthlyPaymentStatus(commonParam);

        if (getMonthlyPaymentRespone == null || getMonthlyPaymentRespone.getBody() == null) {
            data.setStatus(0);
            response.setData(data);
            errorCode = ErrorCodeConstants.VIP_GET_PRODUCT_INFO_FAIL;
            LOG.info("getConsumptionRecord_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + commonParam.getDeviceKey() + "[errorCode=" + errorCode + "]: get consume record failure.");
        } else {
            TimeUtil format = new TimeUtil();
            VipMemberShip.Body userAccountInfo = getMonthlyPaymentRespone.getBody().getMembershipInfo();
            Integer userSubSubscribeStatus = 0;
            if (userAccountInfo.getIsProSubscribe() == 2) {
                userSubSubscribeStatus = 1;
            }
            data.setStatus(userSubSubscribeStatus);
            if (userAccountInfo.getProExpiresAt() != null) {
                data.setexpiredDate(format.getSimpleDateTimeMillis(Long.parseLong(userAccountInfo.getProExpiresAt())));
            }
            response.setData(data);
        }
        return response;
    }

    /**
     * 关闭包月服务
     * @param commonParam
     * @return
     */
    public BaseResponse unsubscribeMonthlyPayment(CommonParam commonParam) {
        VipAccountTpResponse unSubScribeResponse = vipTpDaoV2.unsubscribeMonthlyPayment(
                commonParam);
        VipAccountTpResponse.Head responseHead = unSubScribeResponse.getHead();
        Response<MonthlyPaymentStatusDto> response = new Response<MonthlyPaymentStatusDto>();
        MonthlyPaymentStatusDto data = new MonthlyPaymentStatusDto();
        String errorCode = null;
        // String errorMsgCode = null;
        if (unSubScribeResponse == null || responseHead.getRet() != 0) {
            data.setStatus(0);
            response.setData(data);
            errorCode = ErrorCodeConstants.UN_SUB_SCRIBE_PAYMENT_MONTHLY_FAILE;
            LOG.info("getConsumptionRecord_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + commonParam.getDeviceKey() + "[errorCode=" + errorCode + "]: get consume record failure.");
        } else {
            data.setStatus(1);
            response.setData(data);
        }
        return response;
    }
}
