package com.letv.mas.caller.iptv.tvproxy.vip.service;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.BusinessCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.LetvUserVipCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.ResponseUtil;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageResponse;
import com.letv.mas.caller.iptv.tvproxy.channel.constants.ChannelConstants;
import com.letv.mas.caller.iptv.tvproxy.channel.service.ChannelService;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.constant.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.bean.ExtendParam;
import com.letv.mas.caller.iptv.tvproxy.common.model.bean.UserStatus;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request.LoginRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstantUtils;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request.CheckOrderRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request.PurchaseVipCommonRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.LetvUserDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import com.letv.mas.caller.iptv.tvproxy.user.UserService;
import com.letv.mas.caller.iptv.tvproxy.vip.VipConstants;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.vip.util.VipUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstantUtils.BUYTYPE;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstantUtils.PAYMENT_CHANNEL;

import java.util.*;

/**
 * 会员消费Sevice，提供会员消费相关方法，如，乐点充值，购买会员，领取会员等
 * @author yikun
 */
@Service("vipConsumptionService")
@SuppressWarnings("all")
public class VipConsumptionService extends BaseService {

    @Autowired
    ChannelService channelService;
    
    @Autowired
    UserService userService;

    @Autowired
    VipMetadataService vipMetadataService;
    
    private final static Logger LOG = LoggerFactory.getLogger(VipConsumptionService.class);

    public Response<FreeVipDto> getFreeVip(String iskonka, String imei, CommonParam commonParam) {
        Response<FreeVipDto> response = new Response<FreeVipDto>();
        String errorCode = null;

        boolean iskonkaFlag = VipConstants.ISKONKA_YES.equals(iskonka); // 是否是康佳电视,"0"--不是，"1"是
        String serialNumber = iskonkaFlag ? imei : commonParam.getMac(); // 只在iskonka为"1"时才进行处理

        // 领取会员试用
        Integer productType = CommonConstants.PRODUCT_TYPE_SERVICE_PACKAGE;
        String productId = null;
        Integer channel = null;// 领取免费会员的渠道号，不同类型的免费会员对应不同的渠道
        String signKey = null;// 领取免费会员的签名key，不同类型的免费会员对应不同的签名key
        if (iskonkaFlag) {
            // 新接口的康佳免费会员渠道号
            channel = ApplicationUtils.getInt(ApplicationConstants.FREE_VIP_CHANNEL_KONKA);
            // 新接口的康佳免费会员签名key
            signKey = ApplicationUtils.get(ApplicationConstants.FREE_VIP_SIGN_KEY_KONKA);
            productId = String.valueOf(VipTpConstant.ORDER_TYPE_44);
        } else {
            // 新接口的7天免费会员渠道号
            channel = ApplicationUtils.getInt(ApplicationConstants.FREE_VIP_CHANNEL_SEVEN_DAY);
            // 新接口的康佳免费会员签名key
            signKey = ApplicationUtils.get(ApplicationConstants.FREE_VIP_SIGN_KEY_SEVEN_DAY);
            productId = String.valueOf(VipTpConstant.ORDER_TYPE_40);
        }
        // invoke boss interface to receive free vip
        GetFreeVipTpResponse getFreeVipTpResponse = this.facadeTpDao.getVipTpDao().getFreeVip(channel, signKey,
                commonParam);
        if (getFreeVipTpResponse != null && "0".equals(getFreeVipTpResponse.getCode())
                && getFreeVipTpResponse.getValues() != null && getFreeVipTpResponse.getValues().getOrderId() != null) {// 成功

            // 获取用户到期时间
            FreeVipDto dto = new FreeVipDto();
            dto.setOrderId(getFreeVipTpResponse.getValues().getOrderId());
            VipAccountTpResponse accountTpResponse = this.facadeTpDao.getVipTpDao().getVipAccount(commonParam);
            if (accountTpResponse != null) {
                dto.setCancelTime(String.valueOf(accountTpResponse.getSeniorendtime()));
                dto.setVipEndTime(TimeUtil.getDateStringFromLong(accountTpResponse.getSeniorendtime(),
                        TimeUtil.SHORT_DATE_FORMAT));
            }

            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
            response.setData(dto);
        } else {// 开通失败则删除订单
            if (getFreeVipTpResponse != null && getFreeVipTpResponse.getValues() != null
                    && getFreeVipTpResponse.getValues().getOrderId() != null) {
                // 成功生成订单但是开通失败时，才删除订单
                StatusResponse statusResponse = this.facadeTpDao.getVipTpDao().deleteOrder(
                        getFreeVipTpResponse.getValues().getOrderId(), commonParam.getUserId());
                LOG.info("getFreeVip_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + commonParam.getDeviceKey() + " delete aborted free vip order result ["
                        + statusResponse.getStatus() + "]");
            } else {
                LOG.info("getFreeVip_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + commonParam.getDeviceKey() + " create free VIP order failure.");
            }
            errorCode = ErrorCodeConstants.USER_FREE_VIP_GET_FAILURE;
            LOG.info("getFreeVip_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + commonParam.getDeviceKey() + "[errorCode=" + errorCode + "]: get free VIP failed.");
        }

        if (errorCode != null) {// 最后的失败处理
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
            response.setResultStatus(1);
        }
        return response;
    }

    public Response<QRCodeDto> getLotteryQRCode(Long expire, Long validDate, Integer productType, Integer productId,
                                                String position, CommonParam commonParam) {
        Response<QRCodeDto> response = new Response<QRCodeDto>();
        String logPrefix = "getLotteryQRCode_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + position;
        String errorCode = null;

        String callback = null;// generate qrcode source url
        if (StringUtil.isNotBlank(position)) {
            callback = this.facadeCacheDao.getVipCacheDao().getDeliverAddress(commonParam.getUserId(), position);
            if (StringUtil.isNotBlank(callback)) {
                // get qrcode with user token info
                SsoQRCodeTpResponse ssoTp = this.facadeTpDao.getVipTpDao().createSsoQRCode(callback, expire,
                        commonParam);
                if (ssoTp == null || !"1".equals(ssoTp.getStatus()) || ssoTp.getBean() == null) {
                    // 第三方成功status返回"1"
                    errorCode = ErrorCodeConstants.USER_CREATE_SSO_QRCODE_FAILURE;
                    LOG.info(logPrefix + "[errorCode=" + errorCode + "]: create sso qrcode failed.");
                    ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
                } else {
                    QRCodeDto dto = new QRCodeDto();
                    dto.setQrcode(ssoTp.getBean().getQrcode());
                    Map<String, String> map = channelService.getVideoSaleConfigs();
                    if (!CollectionUtils.isEmpty(map)) {
                        dto.setQrcodeText(map.get(ChannelConstants.INDEX_VIDEO_SALE_URL_TEXT));
                        dto.setTitleIcon(map.get(ChannelConstants.INDEX_VIDEO_SALE_URL_TITLE_ICON));
                        dto.setExpire(map.get(ChannelConstants.INDEX_VIDEO_SALE_URL_EXPIRE_TIME));
                    }
                    response.setData(dto);
                }
            }
        }

        return response;
    }

    /**
     * 更新定点投放活动对象
     * @return Boolean True--更新成功，False--更新失败
     */
    public Response<Boolean> updateDirectionalPushUser() {
        Response<Boolean> response = new Response<Boolean>();
        response.setData(true);// This business has been offline.
        return response;
    }

    /**
     * 根据可使用的乐点数，直接购买最贵的套餐
     * @param commonParam
     * @param deviceType
     * @param deviceKey
     * @param mediaType
     * @param serialNumber
     * @param amount
     * @param username
     * @param purchaseVipRequest
     * @param getUserAccountInfoRequest
     * @param letvPoint
     * @param locale
     * @return
     */
    public Response<LetvPointConsumeVipDto> consumeVipByAvailableLetvPoint(Integer amount, String serialNumber,
                                                                           String mediaType, Integer deviceType, Integer subend, Integer av, CommonParam commonParam) {
        Response<LetvPointConsumeVipDto> response = new Response<LetvPointConsumeVipDto>();
        StringBuilder logPrefix = new StringBuilder("consumeVipByAvailableLetvPoint_").append(commonParam.getMac())
                .append("_").append(commonParam.getUserId()).append("_").append(amount);
        String errorCode = null;

        if (StringUtils.isBlank(commonParam.getUserId())) {
            UserStatus user = userService.getUserStatus(commonParam);
            if (user == null || user.getUserId() <= 0) {
                errorCode = ErrorCodeConstants.USER_NOT_EXIST;
                LOG.info(logPrefix + "[errorCode=" + errorCode + "]: user not exist.");
                response = (Response<LetvPointConsumeVipDto>) LetvUserVipCommonConstant.setErrorResponse(response,
                        errorCode, commonParam.getLangcode());
            } else {
                commonParam.setUserId(String.valueOf(user.getUserId()));
            }
        }

        if (errorCode == null) {
            LetvPointConsumeVipDto dto = new LetvPointConsumeVipDto();
            // 校验参数
            if (amount == null || amount < 0) {
                // 非法参数
                errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
                LOG.error(logPrefix + "[errorCode=" + errorCode + "]: illegal parameter [letvPoint--" + amount + "]");
                dto.setResultStatus(VipConstants.LETVPOINT_CONSUME_VIP_ILLEGAL_PARAMETERS);
            }
            // 余额校验，参照账户余额，尽可能地购买套餐
            int availableLetvPoint = 0;
            PageResponse<PricePackageListDto> packages = null;
            if (errorCode == null) {
                // 查询余额，避免直接使用letvPoint下单时可能面临余额不足的问题
                int letvPointBalance = vipMetadataService.getLetvPointBalance(commonParam);
                LOG.info(logPrefix.toString() + ": got account letvPoint balance [" + letvPointBalance
                        + "] while letvPoint parameter [" + amount + "].");
                // 取letvPoint与letvPointBalance的最小值，进行后续操作，提升下单率和支付成功率
                availableLetvPoint = amount > letvPointBalance ? letvPointBalance : amount;

                // 获取套餐包
                packages = vipMetadataService.getPricePackageList(commonParam.getLangcode());
                if (packages.getResultStatus() != 1 || CollectionUtils.isEmpty(packages.getData())) {
                    // 获取可买最贵套餐失败
                    errorCode = ErrorCodeConstants.PAY_GET_PRICE_PARCKAGE_FAILURE;
                    LOG.error(logPrefix + "[errorCode=" + errorCode + "]: can't get price package.");
                    dto.setResultStatus(VipConstants.LETVPOINT_CONSUME_VIP_PRODUCT_FAILURE);
                }
            }

            // 筛选可买最贵套餐
            PricePackageListDto highestPackage = null;
            if (errorCode == null) {
                // 查找letvPoint乐点能购买的最贵套餐
                highestPackage = this.getHighestPricePackageByLetvPoint(availableLetvPoint, packages.getData());
                if (highestPackage == null) {
                    errorCode = ErrorCodeConstants.PAY_LETVPOINT_AVAILABLE_INSUFFICIENT;
                    LOG.error(logPrefix + "[errorCode=" + errorCode + "]: insufficient letvPoint ["
                            + availableLetvPoint + "].");
                    dto.setResultStatus(VipConstants.LETVPOINT_CONSUME_VIP_ILLEGAL_PARAMETERS);
                }
            }

            // 支付
            if (errorCode == null) {
                String corderId = VipTpConstant.DEFAULT_CORDERID;
                Integer bindingtype = null;
                Integer purchaseType = VipTpConstant.PRODUCT_TYPE_SERVICE_PACKAGE;
                Long productId = Long.valueOf(highestPackage.getId());
                String price = highestPackage.getLetvPointPrice();
                Integer paymentChannel = VipTpConstant.PAYMENT_CHANNEL_LETVPOINT;
                if (paymentChannel != null && VipTpConstant.PAYMENT_CHANNEL_PAYPAL == paymentChannel) {
                    bindingtype = 1;
                }
                Integer buyType = VipTpConstant.PRODUCT_TYPE_SERVICE_PACKAGE;
                Long pid = Long.valueOf(VipTpConstant.DEFAULT_PID);
                Integer svip = VipTpConstant.SVIP_TV;
                Long videoId = null;
                int validCode = VipMsgCodeConstant.REQUEST_SUCCESS;
                String activityIds = null;
                PurchaseProductResponse consumeTp = this.facadeTpDao.getVipTpDao().purchaseProduct(corderId, buyType,
                        productId, pid, price, svip, activityIds, null, av, videoId, bindingtype, null, subend, null,
                        null, null, null, null, paymentChannel, commonParam);
                if (consumeTp == null || !"0000".equalsIgnoreCase(consumeTp.getResponse())) {
                    errorCode = ErrorCodeConstants.PAY_PURCHASE_VIP_LETVPOINT_PAY_FAILURE;
                    LOG.error(logPrefix + "[errorCode=" + errorCode + "]: consume VIP by letvPoint failed.");
                }
            }

            // 获取账户会员有效期
            VipAccountTpResponse accountTpResponse = this.facadeTpDao.getVipTpDao().getVipAccount(commonParam);
            String validDate = null;
            if (accountTpResponse != null) {
                Long seniorEndTime = accountTpResponse.getSeniorendtime();
                if (seniorEndTime != null) {
                    validDate = TimeUtil.getDateStringFromLong(seniorEndTime, TimeUtil.SHORT_DATE_FORMAT);
                    dto.setValidDate(validDate);
                }
            }
            // 获取账户乐点余额
            int currentLetvPointBalance = vipMetadataService.getLetvPointBalance(commonParam);
            dto.setLetvPoint(currentLetvPointBalance);
            // 筛选余额能购买的最贵套餐
            if (packages != null) {
                PricePackageListDto pricePackage = this.getHighestPricePackageByLetvPoint(currentLetvPointBalance,
                        packages.getData());
                String packageName = null;
                if (pricePackage != null) {
                    packageName = pricePackage.getPackageName();
                    dto.setPackageName(packageName);
                }
                LOG.info(logPrefix + ": user account after consuming is letvPoint[" + currentLetvPointBalance
                        + "], validate[" + validDate + "], can by package[ " + packageName + "].");
            }

            if (errorCode != null) {
                dto.setErrCode(errorCode);
                dto.setErrMsg(MessageUtils.getMessage(errorCode, commonParam.getLangcode()));
            } else {
                dto.setResultStatus(VipConstants.LETVPOINT_CONSUME_VIP_SUCCEED);
            }

            // 因为无论购买套餐的结果如何，都要将下单或支付操作之后的账户信息和可购买最贵套餐返回给客户端，故这里设置永远响应成功
            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
            response.setData(dto);
        }

        return response;
    }

    public Response<PurchaseVipDto> purchaseProduct(String corderId, Integer purchaseType, Integer paymentChannel,
                                                    Long productId, String productName, String price, String activityIds, String couponCode, String bindid,
                                                    Integer av, Integer subend, String deviceId, String siteId, String tvId, String cardNumber, String appType,
                                                    CommonParam commonParam) {
        Response<PurchaseVipDto> response = new Response<PurchaseVipDto>();

        String errorCode = null;
        String errorMsgCode = null;
        if (StringUtils.isBlank(corderId)) {
            // 其中支付中心直接到院线下单
            corderId = VipTpConstant.DEFAULT_CORDERID;
        }
        Integer bindingtype = null;
        if (paymentChannel != null && VipTpConstant.PAYMENT_CHANNEL_PAYPAL == paymentChannel) {
            bindingtype = 1;
        }
        Integer buyType = null;// 购买类型，1--购买单点和直播，2--购买会员套餐
        Long pid = null;// 产品id，购买单点和直播时使用，购买会员套餐则赋值为0
        Integer svip = null;// 9--购买会员套餐，0--购买单点
        Long videoId = null;// 中间变量，用于处理直播的场次id，最终还是赋值回到pid
        String screeningsId = null;
        int validCode = VipMsgCodeConstant.REQUEST_SUCCESS;
        if (purchaseType != null) {
            if (purchaseType == 2) {// 套餐
                buyType = VipTpConstant.PRODUCT_TYPE_SERVICE_PACKAGE;
                svip = VipTpConstant.SVIP_TV;
                pid = Long.valueOf(VipTpConstant.DEFAULT_PID);
            } else {
                if (purchaseType == 1) {// 一定要在productId被赋值之前判断
                    pid = productId;
                } else if (purchaseType == 3) {// 一定要在productId被赋值之前判断
                    videoId = productId;
                    if (videoId != null) {
                        String videoidStr = String.valueOf(videoId);
                        if (videoidStr.length() > 17) {
                            /*
                             * 根据Boss
                             * Wiki定义，videoid是将频道、赛事类型、年份、轮次、场次、类型（type）、
                             * count组合起来
                             * ，
                             * 格式为00 000 0000 000 0000 00 000，分表表示频道、赛事类型、年份 、轮次
                             * 、 场次 、类型和count；
                             * 而从直播大厅获取的直播id仅为前面16位数据，需要在这里拼接上“类型”数据，这里拼接“01”（
                             * 表示直播券
                             * ），
                             * 得到下单使用的正确pid。
                             * 而针对支付中心接口中pid定义为Long型数据的问题，
                             * 这里保证拼接后的videoid仍能正确转型为Long
                             * （最长19位），
                             * 故这里对videoid的字符串形式数据长队进行限制（不能超过17位）
                             */
                            // pid formatter like 00 000 0000 000 0000 00 000,
                            // the formatter
                            // combine of
                            // channel(2)+competition(3)+year(4)+rounds(3)+screenings(4)+type(2)+count(3)
                            validCode = VipMsgCodeConstant.VIP_ORDER_PAYCODE_REQUEST_VIDEOID_ERROR;
                        } else {
                            videoidStr += VipTpConstant.LIVE_DEFAULT_TYPE;
                            try {
                                videoId = Long.parseLong(videoidStr);
                                pid = videoId;
                            } catch (Exception e) {
                                validCode = VipMsgCodeConstant.VIP_ORDER_PAYCODE_REQUEST_VIDEOID_ERROR;
                            }
                        }
                    }
                }
                buyType = VipTpConstant.PRODUCT_TYPE_SINGLE_FILM;
                productId = Long.valueOf(VipTpConstant.DEFAULT_PRODUCTID);
                svip = VipTpConstant.SVIP_NOT_NUMBER;
            }
        }
        // 当初为了易宝支付出现9.9元包月，服务端虚拟一个活动，让最终价变成9.9，所以支付时这个活动需要去掉，boss会在支付时默认9.9元
        activityIds = this.trimYeepayActivityIds(activityIds);
        String actualProductId = "";
        if (buyType != null && VipTpConstant.PRODUCT_TYPE_SINGLE_FILM == buyType) {
            actualProductId = String.valueOf(pid);
        } else {
            actualProductId = String.valueOf(productId);
        }
        if (validCode == VipMsgCodeConstant.REQUEST_SUCCESS) {// 前面处理直播id的时候也做了参数校验，但是并不完整，需要接着校验
            validCode = this.validateForPurchaseProduct(av, commonParam.getUserId(), corderId, buyType, productId,
                    paymentChannel, bindid, price, commonParam.getMac(), subend, appType);
        }
        if (validCode == VipMsgCodeConstant.REQUEST_SUCCESS) {
            // 当前请求是否来自直播SDK，true--是，false--否
            boolean isTpsdk = (av != null && VipTpConstant.BROADCAST_APK_VERSION_TPSDK == av);
            // 所有的支付渠道都是用一个dao方法，在dao方法中根据支付渠道获取url前缀，然后进行参数拼接
            PurchaseProductResponse purchaseProductResponse = this.facadeTpDao.getVipTpDao().purchaseProduct(corderId,
                    buyType, productId, pid, price, svip, activityIds, couponCode, av, videoId, bindingtype, null,
                    subend, bindid, cardNumber, deviceId, siteId, tvId, paymentChannel, commonParam);
            // 先校验下单结果
            if (purchaseProductResponse == null
                    || !this.paySuccess(paymentChannel, purchaseProductResponse.getStatus(),
                            purchaseProductResponse.getResponse(), purchaseProductResponse.getCorderid())) {// 支付失败
                errorCode = ErrorCodeConstants.PAY_FAILURE;
                response = (Response<PurchaseVipDto>) LetvUserVipCommonConstant.setErrorResponse(response, errorCode,
                        commonParam.getLangcode());
            } else {
                if (paymentChannel != null) {
                    PurchaseVipDto data = new PurchaseVipDto();
                    response.setData(data);
                    if (paymentChannel == VipTpConstant.PAYMENT_CHANNEL_ONE_KEY_QUICK_PAY) {// 快捷支付，没有二维码url
                        data.setCorderid(isTpsdk ? purchaseProductResponse.getOrdernumber() : purchaseProductResponse
                                .getCorderid());
                        data.setOrdernumber(purchaseProductResponse.getOrdernumber());
                        data.setOrderStatus(1);
                    } else if (paymentChannel == VipTpConstant.PAYMENT_CHANNEL_LETVPOINT) {// 乐点，没有二维码url
                        data.setCorderid(isTpsdk ? purchaseProductResponse.getOrdernumber() : purchaseProductResponse
                                .getCorderid());
                        data.setOrdernumber(purchaseProductResponse.getOrdernumber());
                        if (VipConstants.VIP_PURCHASE_VIP_SHOW_END_TIME) {
                            // 查看订单详情
                            OrderStatusTpResponse orderTp = this.facadeTpDao.getVipTpDao().checkOrderStatus(
                                    purchaseProductResponse.getCorderid(), commonParam);
                            if (orderTp != null && orderTp.getError() == null && orderTp.getData() != null
                                    && orderTp.getData().size() > 0) {
                                OrderStatusTpResponse.UserPackageInfo packageInfo = orderTp.getData().get(0);
                                if (StringUtils.isNotBlank(packageInfo.getCancelTime())) {
                                    data.setVipEndDate(TimeUtil.getDateString(
                                            new Date(Long.valueOf(packageInfo.getCancelTime())),
                                            TimeUtil.SHORT_DATE_FORMAT));
                                }
                            } else {
                                LOG.error("purchaseProduct_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                        + "_" + productId + "[errorCode="
                                        + ErrorCodeConstants.PAY_CHECK_ORDER_STATUS_FAILURE
                                        + "]: get order error or order not exist.");
                            }
                        }
                    } else {// 返回值有二维码url支付方式，其实易宝支付在绑卡之后也是没有二维码地址的
                        String url = null;
                        if (paymentChannel == VipTpConstant.PAYMENT_CHANNEL_ALIPAY) {// 支付宝
                            url = purchaseProductResponse.getBigUrl();
                        } else if (paymentChannel == VipTpConstant.PAYMENT_CHANNEL_WEIXIN) {// 微信
                            url = purchaseProductResponse.getWxurl();
                        } else if (paymentChannel == VipTpConstant.PAYMENT_CHANNEL_HUASHU) {// 华数支付
                            url = purchaseProductResponse.getQrcodeUrl();
                        } else {// 拉卡拉、paypal支付、易宝支付未绑卡、易宝支付绑卡之后
                            url = purchaseProductResponse.getUrl();
                        }
                        if (StringUtils.isBlank(url) && paymentChannel != VipTpConstant.PAYMENT_CHANNEL_YEEPAYBIND) {
                            // url为空表示下单失败，但是易宝支付在绑卡之后的支付除外
                            errorCode = ErrorCodeConstants.PAY_FAILURE;
                            response = (Response<PurchaseVipDto>) LetvUserVipCommonConstant.setErrorResponse(response,
                                    errorCode, commonParam.getLangcode());
                        } else {
                            data.setCorderid(isTpsdk ? purchaseProductResponse.getOrdernumber()
                                    : purchaseProductResponse.getCorderid());
                            data.setOrdernumber(purchaseProductResponse.getOrdernumber());
                            data.setUrl(url);
                        }
                    }
                } else {// 非法支付方式
                    errorCode = ErrorCodeConstants.PAY_ILLEGATE_PAYMENT_CHANNEL;
                    ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
                    LOG.info("purchaseProduct_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                            + productId + "[errorCode=" + errorCode + "]: illegal payment channel[" + paymentChannel
                            + "].");
                }
            }
        } else {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            errorMsgCode = errorCode + VipUtil.parseErrorMsgCode(VipConstants.PAY, validCode);
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, errorMsgCode, commonParam.getLangcode());
            LOG.info("purchaseProduct_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + productId
                    + "[errorCode=" + errorCode + "]: illegal parameters.");
        }

        return response;
    }

    /**
     * 收银台下单，返回订单号，生成支付二维码； 兼容单点和套餐；支持支付宝、微信、拉卡拉支付方式；
     * @param purchaseVipCommonRequest
     * @param locale
     * @return
     */
    public Response<PurchaseVipDto> purchaseVip(PurchaseVipCommonRequest purchaseVipCommonRequest, Locale locale) {
        Response<PurchaseVipDto> response = new Response<PurchaseVipDto>();
        String logPrefix = "purchaseVip_" + purchaseVipCommonRequest.getUsername() + "_"
                + purchaseVipCommonRequest.getUserid() + "_" + purchaseVipCommonRequest.getMac();
        String errorCode = null;
        String errorMsgCode = null;

        int validCode = purchaseVipCommonRequest.assertValid();
        if (VipMsgCodeConstant.REQUEST_SUCCESS != validCode) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            errorMsgCode = errorCode + VipUtil.parseErrorMsgCode(VipConstants.PAY, validCode);
            LOG.info(logPrefix + "[errorCode=" + errorCode + "]: illegal parameters.");
        }

        logPrefix += (purchaseVipCommonRequest.getPaymentChannel() + "_" + purchaseVipCommonRequest.getBuyType() + "_"
                + purchaseVipCommonRequest.getActualProductId() + "_" + purchaseVipCommonRequest.getCorderid());

        PurchaseVipDto data = null;
        if (errorCode == null) {
            // 当前请求是否来自直播SDK，true--是，false--否
            boolean isTpsdk = (purchaseVipCommonRequest.getAv() != null && VipTpConstant.BROADCAST_APK_VERSION_TPSDK == purchaseVipCommonRequest
                    .getAv());

            String url = null;// 易宝支付时使用，减少代码量
            switch (purchaseVipCommonRequest.getPaymentChannel()) {
            case VipTpConstant.PAYMENT_CHANNEL_ALIPAY:
                // 支付宝图片二维码（不再支持网页形式二维码）
                AliPayCodeIMGTpResponse aliIMGTp = this.facadeTpDao.getVipTpDao().getPurchaseVipAliPaycodeImg(
                        purchaseVipCommonRequest);
                if (aliIMGTp != null && aliIMGTp.isSucess() && StringUtils.isNotBlank(aliIMGTp.getBigUrl())
                        && StringUtils.isNotBlank(aliIMGTp.getCorderid())) {
                    data = new PurchaseVipDto();
                    data.setCorderid(isTpsdk ? aliIMGTp.getOrdernumber() : aliIMGTp.getCorderid());
                    data.setOrdernumber(aliIMGTp.getOrdernumber());
                    data.setUrl(aliIMGTp.getBigUrl());
                } else {
                    errorCode = ErrorCodeConstants.PAY_PURCHASE_VIP_ALI_PAY_FAILURE;
                    LOG.info(logPrefix + "[errorCode=" + errorCode + "]: get ali paycode failure.");
                }
                break;
            case VipTpConstant.PAYMENT_CHANNEL_WEIXIN:
                // 微信二维码
                WeixinPayCodeTpResponse wxTp = this.facadeTpDao.getVipTpDao().getPurchaseVipWeinxinPaycodeImg(
                        purchaseVipCommonRequest);
                if (wxTp != null && wxTp.isSucess() && StringUtils.isNotBlank(wxTp.getWxurl())
                        && StringUtils.isNotBlank(wxTp.getCorderid())) {
                    data = new PurchaseVipDto();
                    data.setCorderid(isTpsdk ? wxTp.getOrdernumber() : wxTp.getCorderid());
                    data.setOrdernumber(wxTp.getOrdernumber());
                    data.setUrl(wxTp.getWxurl());
                } else {
                    errorCode = ErrorCodeConstants.PAY_PURCHASE_VIP_WEIXIN_PAY_FAILURE;
                    LOG.info(logPrefix + "[errorCode=" + errorCode + "]: get weixin paycode failure.");
                }
                break;
            case VipTpConstant.PAYMENT_CHANNEL_LAKALA:
                // 拉卡拉二维码
                LakalaPayCodeTpResponse lakalaTp = this.facadeTpDao.getVipTpDao().getPurchaseVipLakalaPaycodeImg(
                        purchaseVipCommonRequest);
                if (lakalaTp != null && lakalaTp.isSucess() && StringUtils.isNotBlank(lakalaTp.getUrl())
                        && StringUtils.isNotBlank(lakalaTp.getCorderid())) {
                    data = new PurchaseVipDto();
                    data.setCorderid(isTpsdk ? lakalaTp.getOrdernumber() : lakalaTp.getCorderid());
                    data.setOrdernumber(lakalaTp.getOrdernumber());
                    data.setUrl(lakalaTp.getUrl());
                } else {
                    String errMsg = null;
                    if (lakalaTp != null) {
                        errMsg = lakalaTp.getErrormsg();
                    } else {
                        errMsg = "get lakala paycode failure.";
                    }

                    errorCode = ErrorCodeConstants.PAY_PURCHASE_VIP_LAKALA_PAY_FAILURE;
                    LOG.error(logPrefix + "[errorCode=" + errorCode + "]: " + errMsg);
                }
                break;
            case VipTpConstant.PAYMENT_CHANNEL_LETVPOINT:
                // 乐点支付
                if ("0".equals(VipTpConstant.PAYMENT_CHANNEL_LETVPOINT_SWITCH)) {
                    purchaseVipCommonRequest.setActivityIds("");
                }
                LetvpointPaymentTpResponse consumeTp = this.facadeTpDao.getVipTpDao().purchaseVipByLetvpoint(
                        purchaseVipCommonRequest);
                if (consumeTp != null && "0000".equalsIgnoreCase(consumeTp.getResponse())) {
                    data = new PurchaseVipDto();
                    data.setCorderid(isTpsdk ? consumeTp.getOrdernumber() : consumeTp.getCorderid());
                    data.setOrdernumber(consumeTp.getOrdernumber());

                    if (VipConstants.VIP_PURCHASE_VIP_SHOW_END_TIME) {
                        // 查看订单详情
                        CheckOrderRequest checkOrderRequest = new CheckOrderRequest(
                                purchaseVipCommonRequest.getUsername(), consumeTp.getCorderid(),
                                purchaseVipCommonRequest.getUserid());
                        OrderStatusTpResponse orderTp = this.facadeTpDao.getVipTpDao().checkOrderStatus(
                                consumeTp.getCorderid(), new CommonParam());

                        if (orderTp != null && orderTp.getError() == null && orderTp.getData() != null
                                && orderTp.getData().size() > 0) {
                            OrderStatusTpResponse.UserPackageInfo packageInfo = orderTp.getData().get(0);
                            if (StringUtils.isNotBlank(packageInfo.getCancelTime())) {
                                data.setVipEndDate(TimeUtil.getDateString(
                                        new Date(Long.valueOf(packageInfo.getCancelTime())), TimeUtil.SHORT_DATE_FORMAT));
                            }
                        } else {
                            LOG.error(logPrefix + "[errorCode=" + ErrorCodeConstants.PAY_CHECK_ORDER_STATUS_FAILURE
                                    + "]: get order error or order not exist.");
                        }
                    }
                } else {
                    errorCode = ErrorCodeConstants.PAY_PURCHASE_VIP_LETVPOINT_PAY_FAILURE;
                    LOG.error(logPrefix + "[errorCode=" + errorCode + "]: consume VIP by letvPoint failed.");
                }
                break;
            case VipTpConstant.PAYMENT_CHANNEL_PAYPAL:
                PaypalPaymentWithBindTpResponse tpResponse = this.facadeTpDao.getVipTpDao().purchaseVipByPaypal(
                        purchaseVipCommonRequest, locale);

                if (tpResponse != null && tpResponse.isSucess()) {
                    data = new PurchaseVipDto();
                    data.setCorderid(isTpsdk ? tpResponse.getOrdernumber() : tpResponse.getCorderid());
                    data.setOrdernumber(tpResponse.getOrdernumber());
                    data.setUrl(tpResponse.getUrl());
                } else {
                    errorCode = ErrorCodeConstants.PAY_PURCHASE_VIP_PAYPAL_PAY_FAILURE;
                    LOG.error(logPrefix + "[errorCode=" + errorCode + "]: consume VIP by paypal failed.");
                }
                break;
            case VipTpConstant.PAYMENT_CHANNEL_ONE_KEY_QUICK_PAY:
                OneKeyQucikPayTpResponse oneKeyPayResponse = this.facadeTpDao.getVipTpDao()
                        .purchaseVipByOneKeyQuickPay(purchaseVipCommonRequest, locale);

                if (oneKeyPayResponse != null && oneKeyPayResponse.isSucess()) {
                    data = new PurchaseVipDto();
                    data.setCorderid(isTpsdk ? oneKeyPayResponse.getOrdernumber() : oneKeyPayResponse.getCorderid());
                    data.setOrdernumber(oneKeyPayResponse.getOrdernumber());
                    data.setOrderStatus(1);
                } else {
                    errorCode = ErrorCodeConstants.PAY_PURCHASE_VIP_ONE_KEY_QUICK_PAY_FAILURE;
                    LOG.error(logPrefix + "[errorCode=" + errorCode + "]: consume VIP by one key pay failed.");
                }
                break;
            case VipTpConstant.PAYMENT_CHANNEL_YEEPAYUNBIND:
                // 未绑卡下单支付
                url = VipTpConstant.YEEPAY_UNBIND_ORDER_URL;
            case VipTpConstant.PAYMENT_CHANNEL_YEEPAYBIND:
                // 如果url为空，表示不是未绑卡下单支付，则url取绑卡下单支付url
                url = StringUtils.isBlank(url) ? VipTpConstant.YEEPAY_BIND_ORDER_URL : url;
                YeePayOrderTpResponse yeePayOrderTpResponse = this.facadeTpDao.getVipTpDao().purchaseVipByYeePay(
                        purchaseVipCommonRequest, url);
                if (yeePayOrderTpResponse != null && yeePayOrderTpResponse.getStatus() != null
                        && yeePayOrderTpResponse.getStatus() == 1) {
                    data = new PurchaseVipDto();
                    data.setCorderid(isTpsdk ? yeePayOrderTpResponse.getOrdernumber() : yeePayOrderTpResponse
                            .getCorderid());
                    data.setOrderStatus(1);
                    data.setOrdernumber(yeePayOrderTpResponse.getOrdernumber());
                    data.setUrl(yeePayOrderTpResponse.getUrl());
                } else {
                    errorCode = ErrorCodeConstants.PAY_YEEPAY_ORDER_FAILURE;
                    LOG.error(logPrefix + "[errorCode=" + errorCode + "]: consume VIP by yeepay unbind failed.");
                }
                break;
            default:
                // 非法支付方式
                errorCode = ErrorCodeConstants.PAY_ILLEGATE_PAYMENT_CHANNEL;
                LOG.info(logPrefix + "[errorCode=" + errorCode + "]: illegal payment channel["
                        + purchaseVipCommonRequest.getPaymentChannel() + "].");
                break;
            }
        }

        if (errorCode == null) {
            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
            response.setData(data);
        } else {
            errorMsgCode = errorMsgCode == null ? errorCode : errorMsgCode;
            response = (Response<PurchaseVipDto>) LetvUserVipCommonConstant.setErrorResponse(response, errorCode,
                    errorMsgCode, locale);
        }

        return response;
    }

    public Response<Boolean> updateUserAggrement(String localeStr) {
        Response<Boolean> response = new Response<Boolean>();
        String logPrefix = "updateUserAggrement_";
        if (StringUtils.isEmpty(localeStr)) {
            localeStr = MessageUtils.DEFAULT_FULL_LOCAL_ZH_CN;
        }

        String userAggrement = this.facadeTpDao.getVipTpDao().readUserAggrement(
                VipConstants.VIP_USER_AGGREMENT_COMMON_FILENAME + localeStr
                        + VipConstants.VIP_USER_AGGREMENT_FILENAME_POSTFIX);
        if (userAggrement == null) {
            LOG.info(logPrefix + " return error: Get null user aggrement!");
            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
            response.setData(Boolean.FALSE);
        } else {
            this.facadeCacheDao.getVipCacheDao().setUserAgreement(localeStr, userAggrement);
            response.setResultStatus(VipConstants.RESPONSE_SUCCESS);
            response.setData(Boolean.TRUE);
        }

        return response;
    }

    /**
     * 领取（自有或赠送的）机卡时长
     * @param commonParam
     * @param presentId
     * @param type
     * @param deviceType
     * @param deviceKey
     * @param username
     * @param receiveDeviceBindRequest
     * @param receivePresentDeviceBindRequest
     * @param locale
     * @return
     */
    public Response<ReceiveDeviceBindDto> receiveDeviceBind(String username, String deviceKey, Integer deviceType,
            Integer type, String presentId, CommonParam commonParam) {
        Response<ReceiveDeviceBindDto> response = new Response<ReceiveDeviceBindDto>();

        StringBuilder logPrefix = new StringBuilder("receiveDeviceBind_").append(username).append("_")
                .append(commonParam.getUserId()).append("_").append(deviceKey).append("_").append(type).append("_")
                .append(commonParam.getMac());
        String errorCode = null;

        if (type == null
                || (type != VipConstants.DEVICE_BIND_RECEIVE_TYPE_1 && type != VipConstants.DEVICE_BIND_RECEIVE_TYPE_2)) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            LOG.info(logPrefix + "[errorCode=" + errorCode + "]: illegal parameter.");
            response = (Response<ReceiveDeviceBindDto>) LetvUserVipCommonConstant.setErrorResponse(response, errorCode,
                    commonParam.getLangcode());
        } else {
            if (type == VipConstants.DEVICE_BIND_RECEIVE_TYPE_1) {// 防止误调用，此功能不在TV端做
                errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
                LOG.info(logPrefix + "[errorCode=" + errorCode + "]: receive device bind illegal parameters.");
                response = (Response<ReceiveDeviceBindDto>) LetvUserVipCommonConstant.setErrorResponse(response,
                        errorCode, commonParam.getLangcode());
            } else if (type == VipConstants.DEVICE_BIND_RECEIVE_TYPE_2) {
                logPrefix.append("_").append(presentId);
                if (deviceKey == null || StringUtils.isBlank(commonParam.getMac()) || StringUtils.isBlank(presentId)) {
                    errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
                    LOG.info(logPrefix + "[errorCode=" + errorCode
                            + "]: receive present device bind illegal parameters.");
                    response = (Response<ReceiveDeviceBindDto>) LetvUserVipCommonConstant.setErrorResponse(response,
                            errorCode, commonParam.getLangcode());
                } else {
                    ReceivePresentDeviceBindTpResponse tpResponse = this.facadeTpDao.getVipTpDao()
                            .receivePresentDeviceBind(deviceKey, presentId, logPrefix.toString(), commonParam);
                    if (tpResponse == null || !tpResponse.isSuccess()) {
                        // 领取失败
                        errorCode = ErrorCodeConstants.PAY_RECEIVE_PRESENT_DEVICE_BIND_FAILURE;
                        LOG.info(logPrefix + "[errorCode=" + errorCode + "]: receive present device bind falied.");
                        response = (Response<ReceiveDeviceBindDto>) LetvUserVipCommonConstant.setErrorResponse(
                                response, errorCode, commonParam.getLangcode());
                    } else {
                        // 领取成功
                        ReceiveDeviceBindDto data = new ReceiveDeviceBindDto();
                        if (tpResponse.getValues() != null && tpResponse.getValues().getItems() != null) {
                            for (ReceivePresentDeviceBindTpResponse.ReceivePresentDeviceBindMsgTpResponse.DeviceBindMsg deviceBindMsg : tpResponse.getValues().getItems()) {
                                if (deviceBindMsg != null && deviceBindMsg.getVipType() != null
                                        && deviceBindMsg.getVipType() == 2) {
                                    data.setEndTime(deviceBindMsg.getCancelTime());
                                }
                            }
                        }
                        if (StringUtil.isBlank(data.getEndTime())) {
                            data.setEndTime(TimeUtil.getDateStringFromLong(tpResponse.getValues().getCanceltime(),
                                    TimeUtil.SHORT_DATE_FORMAT));
                        }
                        response.setData(data);
                    }
                }
            }
        }

        return response;
    }

    /**
     * 获取支付活动信息
     * @param paymentChannel
     *            支付通道
     * @param productId
     * @param terminalType
     *            终端类型
     * @param username
     * @param locale
     * @return
     */
    private String getPayActivity(Integer paymentChannel, Long productId, String terminalType, String username,
            Long userid, Locale locale) {

        return "";
    }

    /**
     * 验证是否可以购买免费会员
     * @param username
     * @param days
     * @return
     */
    private boolean canBuyFreeVip(CommonParam commonParam) {
        boolean bool = false;
        VipAccountTpResponse accountTpResponse = this.facadeTpDao.getVipTpDao().getVipAccount(commonParam);
        if (accountTpResponse == null
                || (accountTpResponse.getSeniorendtime() == null && accountTpResponse.getEndtime() == null)) {
            // 从未成为会员时，查看是否有支付成功的消费记录，无支付成功的消费记录才能领取
            ConsumptionRecordTpResponse tpResult = this.facadeTpDao.getVipTpDao().getConsumptionRecord(0, 365, 1, 2,
                    commonParam);
            if (tpResult != null && tpResult.getTotal() != null && tpResult.getData() != null) {
                // 如果有多条消费订单，确认是否有支付成功的订单
                List<ConsumptionRecordTpResponse.ConsumptionRecord> data = tpResult.getData();
                String notPay = String.valueOf(VipConstants.ORDER_STATUS_NOT_PAY);// 未支付状态
                boolean hasPackageConsumptionRecord = false; // 是否有套餐消费记录
                for (ConsumptionRecordTpResponse.ConsumptionRecord consumptionRecord : data) {
                    // 订单状态不等于未支付且订单类型为购买套餐类型
                    String statuName = String.valueOf(consumptionRecord.getStatusName());
                    if (!notPay.equals(consumptionRecord.getStatus())
                            && ("2".equals(statuName) || "-2".equals(statuName))) {
                        hasPackageConsumptionRecord = true;
                        LOG.info("canBuyFreeVip_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                + "_ConsumptionRecord is pay success[" + consumptionRecord.getOrderId() + "]");
                        break;
                    }
                }
                // 有消费记录则不能领，否则能领
                bool = !hasPackageConsumptionRecord;
            } else {
                // 不是会员又无消费记录，则可以领
                bool = true;
            }
        }

        return bool;
    }

    /**
     * 从套餐包列表pricePackages中获取letvPoint可购买的最贵套餐;
     * 该方法默认pricePackages中元素不全为null，不再进行校验
     * @param letvPoint
     * @param pricePackages
     * @return
     */
    private PricePackageListDto getHighestPricePackageByLetvPoint(int letvPoint,
            Collection<PricePackageListDto> pricePackages) {
        if (letvPoint < 0) {
            return null;
        }
        PricePackageListDto highestPackage = null;
        StringBuilder sb = new StringBuilder();
        int min = letvPoint;
        for (PricePackageListDto packageDto : pricePackages) {
            Integer currentPrice = StringUtil.toInteger(packageDto.getLetvPointPrice());
            if (currentPrice != null) {
                if (letvPoint >= currentPrice && min > (letvPoint - currentPrice)) {
                    min = letvPoint - currentPrice;
                    highestPackage = packageDto;
                }
            }
        }
        return highestPackage;
    }

    /**
     * 乐卡校验
     * @param cardSecret
     * @param terminalBrand
     * @return
     */
    public Response<CardSecretValidateDto> isCardSecretExisting(String cardNumber, CommonParam commonParam) {
        CardSecretValidateDto data = new CardSecretValidateDto();
        String errorCode = null;

        if (StringUtils.isBlank(cardNumber)) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            LOG.info("isCardSecretExisting_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + cardNumber
                    + "[errorCode=" + errorCode + "]: cardsecret validate illegal parameters.");
        } else {
            RechargeValidateResult result = this.facadeTpDao.getVipTpDao()
                    .isCardSecretExisting(cardNumber, commonParam);
            if (result == null || result.getCode() == null) {// 接口调用失败
                errorCode = ErrorCodeConstants.PAY_LETV_CARD_VALIDATE_ERROR;
                LOG.info("isCardSecretExisting_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + cardNumber + "[errorCode=" + errorCode + "]: cardsecret validate failure.");
            } else {
                data.setCode(result.getCode());
                data.setCardtype(result.getCardtype());
                String errCode = result.getErrorcode();
                data.setErrorcode(errCode);
                String msg = null;
                if (StringUtils.isNotBlank(errCode)) {// 读取国际化文件
                    msg = MessageUtils.getMessage(VipConstants.VIP_LETV_CARD_ERROR_CODE_PREFIX + errCode,
                            commonParam.getLangcode());
                }
                if (StringUtils.isBlank(msg)) {
                    msg = result.getMsg();
                }
                data.setMsg(msg);
                data.setExpireDate(result.getExpireDate());
                data.setMoney(result.getMoney());
            }
        }

        Response<CardSecretValidateDto> response = new Response<CardSecretValidateDto>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setData(data);
        }
        return response;
    }

    public Response<LetvCardRechargeDto> rechargeByLetvCardPasswd(String cardNumber, CommonParam commonParam) {
        LetvCardRechargeDto data = new LetvCardRechargeDto();
        String errorCode = null;

        int validCode = CommonMsgCodeConstant.REQUEST_SUCCESS;
        if (StringUtils.isBlank(commonParam.getUserId()) || StringUtils.isBlank(cardNumber)) {
            // userid and cardnumber can not be empty
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            LOG.info("rechargeByLetvCardPasswd_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + cardNumber + "[errorCode=" + errorCode + "]: recharge by letv card illegal parameters.");
        } else {
            // check letvcard if can use
            RechargeValidateResult rechargeValidateResult = this.facadeTpDao.getVipTpDao().isCardSecretExisting(
                    cardNumber, commonParam);
            if (rechargeValidateResult == null || rechargeValidateResult.getCode() == null) {
                errorCode = ErrorCodeConstants.PAY_LETV_CARD_VALIDATE_ERROR;
                LOG.info("rechargeByLetvCardPasswd_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + cardNumber + "[errorCode=" + errorCode + "]: cardsecret validate failure.");
            } else if (rechargeValidateResult.getCode() != 0) {// 校验不通过，返回错误信息
                data.setStatus(0);
                data.setCardType(rechargeValidateResult.getCardtype());
                String errCode = rechargeValidateResult.getErrorcode();
                data.setErrorcode(errCode);
                String msg = null;
                if (StringUtils.isNotBlank(errCode)) {// 读取国际化文件
                    msg = MessageUtils.getMessage(VipConstants.VIP_LETV_CARD_ERROR_CODE_PREFIX + errCode,
                            commonParam.getLangcode());
                }
                if (StringUtils.isBlank(msg)) {
                    msg = rechargeValidateResult.getMsg();
                }
                data.setMsg(msg);
                LOG.info("rechargeByLetvCardPasswd_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + cardNumber + "[errorCode=" + errorCode + "]: cardsecret validate failure.");
            } else {
                try {
                    // recharge by letvcard
                    RechargeResult rechargeResult = this.facadeTpDao.getVipTpDao().rechargeByLetvCardPasswd(cardNumber,
                            commonParam);
                    if (rechargeResult != null && rechargeResult.getCode() != null && rechargeResult.getCode() == 0) {// 成功
                        Float money = rechargeResult.getMoney();
                        if (money != null && money > 0) {
                            // 余额增加为充值码
                            data.setCardType(1);
                        } else {
                            data.setCardType(2);
                        }
                        data.setStatus(1);
                        data.setOldBalance(rechargeResult.getBeforepoint());
                        data.setRechargeLetvPoint((int) (money * 10));
                        data.setNewBalance(data.getOldBalance() + data.getRechargeLetvPoint());
                        String orderCode = System.currentTimeMillis() + "";
                        data.setOrderCode(orderCode);
                    } else {
                        errorCode = ErrorCodeConstant.LETV_LEKA_RECHARGE_ERROR;
                        LOG.info("rechargeByLetvCardPasswd_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                + "_" + cardNumber + "[errorCode=" + errorCode + "],letv card recharge failure.");
                    }
                } catch (Exception e) {
                    errorCode = ErrorCodeConstant.LETV_LEKA_RECHARGE_ERROR;
                    LOG.error("rechargeByLetvCardPasswd_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                            + cardNumber + "[errorCode=" + errorCode + "]" + e.getMessage());
                }
            }
        }
        Response<LetvCardRechargeDto> response = new Response<LetvCardRechargeDto>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setData(data);
        }

        return response;
    }

    /**
     * backup the letvcard recharge letvpoint and exchange service method
     * @param amount
     * @param cardNumber
     * @param operationType
     * @param commonParam
     * @param letvCardActiveResultDto
     * @return
     */
    public Response<LetvCardRechargeDto> rechargeByLetvCardPasswdNew_bak(Integer amount, String cardNumber,
            Integer operationType, CommonParam commonParam, LetvCardActiveDto.LetvCardActiveResultDto letvCardActiveResultDto) {
        Response<LetvCardRechargeDto> response = new Response<LetvCardRechargeDto>();
        String errorCode = null;
        String orderCode = System.currentTimeMillis() + "";

        int validCode = CommonMsgCodeConstant.REQUEST_SUCCESS;
        if (StringUtils.isBlank(commonParam.getUserId())) {
            validCode = CommonMsgCodeConstant.REQUEST_USERINFO_ERROR;
        } else if (operationType != null && operationType == 2) {// 兑换
            if (StringUtils.isBlank(cardNumber) && (amount == null || amount < 1)) {
                // 不传卡号和最大兑换乐点数，不允许兑换
                validCode = VipMsgCodeConstant.VIP_LETV_CARD_REQUEST_EXCHANGE_INFO_EMPTY;
            }
        } else {// 否则认为是充值，卡号不能为空
            if (StringUtils.isBlank(cardNumber)) {
                validCode = VipMsgCodeConstant.VIP_LETV_CARD_REQUEST_CARDSECRET_EMPTY;
            }
        }
        if (validCode != CommonMsgCodeConstant.REQUEST_SUCCESS) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode,
                    ErrorMsgCodeUtil.parseErrorMsgCode(errorCode, BusinessCodeConstant.LETVCARD, validCode),
                    commonParam.getLangcode());
            LOG.info("rechargeByLetvCardPasswdNew_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + operationType + "_" + cardNumber + "_" + amount + "[errorCode=" + errorCode
                    + "]: recharge by letv card illegal parameters.");
        } else {
            if (operationType != null && operationType == 2) {// 兑换接口
                PageResponse<PricePackageListDto> packages = vipMetadataService
                        .getPricePackageList(commonParam.getLangcode());
                if (packages.getResultStatus() == null || packages.getResultStatus() != 1
                        || CollectionUtils.isEmpty(packages.getData())) {
                    // get vip package data failure and can not exchange
                    errorCode = ErrorCodeConstants.PAY_GET_PRICE_PARCKAGE_FAILURE;
                    LOG.error("rechargeByLetvCardPasswdNew_" + commonParam.getMac() + "_" + commonParam.getUserId()
                            + "_" + operationType + "_" + cardNumber + "_" + amount + "[errorCode=" + errorCode
                            + "]: can't get price package.");
                    ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
                } else {
                    Integer availableLetvPoint = this.getUserUsableLetvPoint(response, amount, cardNumber, commonParam);
                    Integer cardType = null;
                    LetvCardRechargeDto data = response.getData();
                    if (data != null) {
                        cardType = this.getCardType(data.getCardType());
                    } else {
                        if (availableLetvPoint != null) {// 乐点余额兑换，则认为是充值兑换
                            cardType = 1;
                        }
                        data = new LetvCardRechargeDto();
                        data.setCardType(cardType);
                    }
                    if (cardType != null && cardType != 1) {// 兑换卡，直接兑换
                        if (data != null && data.getStatus() == null) {
                            RechargeResult rechargeResult = this.facadeTpDao.getVipTpDao().rechargeByLetvCardPasswd(
                                    cardNumber, commonParam);
                            if (rechargeResult != null && rechargeResult.getCode() != null
                                    && rechargeResult.getCode() == 0) {// 成功
                                Float money = rechargeResult.getMoney();
                                data.setStatus(1);
                                data.setMsg(MessageUtils.getMessage(VipConstants.VIP_LETV_CARD_EXCHANGE_SUCCESS_TEXT,
                                        commonParam.getLangcode()));
                                data.setOldBalance(rechargeResult.getBeforepoint());
                                if (money != null) {// 判空处理
                                    data.setRechargeLetvPoint((int) (money * 10));
                                }
                                data.setNewBalance(data.getOldBalance() + data.getRechargeLetvPoint());
                                data.setOrderCode(orderCode);
                            } else {
                                errorCode = ErrorCodeConstant.LETV_LEKA_RECHARGE_ERROR;
                                data.setErrorcode(errorCode);
                                data.setMsg(MessageUtils.getMessage(errorCode, commonParam.getLangcode()));
                                LOG.info("rechargeByLetvCardPasswdNew_" + commonParam.getMac() + "_"
                                        + commonParam.getUserId() + "_" + operationType + "_" + cardNumber + "_"
                                        + amount + "[errorCode=" + errorCode + "],letv card recharge failure.");
                            }
                        }
                    } else {// 不是兑换卡或者直接根据用户可用余额进行购买
                        if (availableLetvPoint != null) {// 如果为空，表示乐卡不可用，此时response已有值
                            if (availableLetvPoint == 0) {// 表示用户余额不足
                                errorCode = ErrorCodeConstants.PAY_LETVPOINT_AVAILABLE_INSUFFICIENT;
                                data.setErrorcode(errorCode);
                                data.setMsg(MessageUtils.getMessage(errorCode, commonParam.getLangcode()));
                                LOG.error("rechargeByLetvCardPasswdNew_" + commonParam.getMac() + "_"
                                        + commonParam.getUserId() + "_" + operationType + "_" + cardNumber + "_"
                                        + amount + "[errorCode=" + errorCode + "]: insufficient letvPoint ["
                                        + availableLetvPoint + "].");
                            } else {// 表示乐卡可用或者用户有乐点余额
                                // 筛选可买最贵套餐
                                PricePackageListDto highestPackage = null;
                                // 查找letvPoint乐点能购买的最贵套餐
                                highestPackage = this.getHighestPricePackageByLetvPoint(availableLetvPoint,
                                        packages.getData());
                                if (highestPackage == null) {// 获取最贵套餐失败
                                    errorCode = ErrorCodeConstants.PAY_LETVPOINT_AVAILABLE_INSUFFICIENT;
                                    data.setErrorcode(errorCode);
                                    data.setMsg(MessageUtils.getMessage(errorCode, commonParam.getLangcode()));
                                    LOG.error("rechargeByLetvCardPasswdNew_" + commonParam.getMac() + "_"
                                            + commonParam.getUserId() + "_" + operationType + "_" + cardNumber + "_"
                                            + amount + "[errorCode=" + errorCode + "]: insufficient letvPoint ["
                                            + availableLetvPoint + "].");
                                } else {
                                    String corderId = VipTpConstant.DEFAULT_CORDERID;
                                    Integer bindingtype = null;
                                    Integer purchaseType = VipTpConstant.PRODUCT_TYPE_SERVICE_PACKAGE;
                                    Long productId = Long.valueOf(highestPackage.getId());
                                    String price = highestPackage.getLetvPointPrice();

                                    // 2016-01-27，恢复老乐卡激活接口临时修改，添加额外参数LetvCardActiveResultDto以返回更多充值信息
                                    if (letvCardActiveResultDto != null) {
                                        letvCardActiveResultDto.setAmount(StringUtil.toInteger(
                                                highestPackage.getCurrentPrice(), 0));
                                        letvCardActiveResultDto.setPurchaseName(highestPackage.getPackageName());
                                    }

                                    Integer av = VipTpConstant.BROADCAST_APK_VERSION_LETV;
                                    Integer paymentChannel = VipTpConstant.PAYMENT_CHANNEL_LETVPOINT;
                                    if (paymentChannel != null
                                            && VipTpConstant.PAYMENT_CHANNEL_PAYPAL == paymentChannel) {
                                        bindingtype = 1;
                                    }
                                    Integer buyType = VipTpConstant.PRODUCT_TYPE_SERVICE_PACKAGE;
                                    Long pid = Long.valueOf(VipTpConstant.DEFAULT_PID);
                                    Integer svip = VipTpConstant.SVIP_TV;
                                    Long videoId = null;
                                    validCode = VipMsgCodeConstant.REQUEST_SUCCESS;
                                    String activityIds = null;
                                    PurchaseProductResponse purchaseTpResponse = this.facadeTpDao.getVipTpDao()
                                            .purchaseProduct(corderId, buyType, productId, pid, price, svip,
                                                    activityIds, null, av, videoId, bindingtype, null, null, null,
                                                    cardNumber, null, null, null, paymentChannel, commonParam);

                                    if (purchaseTpResponse != null && "0000".equals(purchaseTpResponse.getResponse())) {
                                        // 2016-01-27，恢复老乐卡激活接口临时修改，添加额外参数LetvCardActiveResultDto以返回更多充值信息
                                        if (letvCardActiveResultDto != null
                                                && StringUtils.trimToNull(purchaseTpResponse.getCorderid()) != null) {
                                            letvCardActiveResultDto.setOrderId(StringUtils
                                                    .trimToNull(purchaseTpResponse.getCorderid()));
                                        }
                                        // 充值卡使用成功
                                        data.setStatus(1);
                                        data.setMsg(MessageUtils.getMessage(
                                                VipConstants.VIP_LETV_CARD_EXCHANGE_SUCCESS_TEXT,
                                                commonParam.getLangcode()));
                                    } else {
                                        errorCode = ErrorCodeConstant.LETV_LEKA_RECHARGE_ERROR;
                                        data.setErrorcode(errorCode);
                                        data.setMsg(MessageUtils.getMessage(errorCode, commonParam.getLangcode()));
                                        LOG.info("rechargeByLetvCardPasswdNew_" + commonParam.getMac() + "_"
                                                + commonParam.getUserId() + "_" + operationType + "_" + cardNumber
                                                + "_" + amount + "[errorCode=" + errorCode
                                                + "],letv card recharge failure.");
                                    }
                                }
                            }
                        }
                    }
                    // 获取账户会员有效期
                    VipAccountTpResponse accountTpResponse = this.facadeTpDao.getVipTpDao().getVipAccount(commonParam);
                    if (accountTpResponse != null) {
                        Long seniorEndTime = accountTpResponse.getSeniorendtime();
                        if (seniorEndTime != null) {
                            String validDate = TimeUtil
                                    .getDateStringFromLong(seniorEndTime, TimeUtil.SHORT_DATE_FORMAT);
                            data.setValidDate(validDate);
                            if (letvCardActiveResultDto != null) {
                                letvCardActiveResultDto.setEndDate(validDate);
                            }
                        }
                    }
                    // 获取账户乐点余额
                    Integer currentLetvPointBalance = vipMetadataService.getLetvPointBalance(
                            commonParam);
                    data.setLetvPoint(currentLetvPointBalance);
                    // 筛选余额能购买的最贵套餐
                    if (packages != null) {
                        PricePackageListDto pricePackage = this.getHighestPricePackageByLetvPoint(
                                currentLetvPointBalance, packages.getData());
                        if (pricePackage != null) {
                            data.setPackageName(pricePackage.getPackageName());
                        }
                    }
                    response.setData(data);
                }
            } else {// recharge by letvcard
                // check letvcard if can use
                RechargeValidateResult rechargeValidateResult = this.facadeTpDao.getVipTpDao().isCardSecretExisting(
                        cardNumber, commonParam);
                if (rechargeValidateResult == null || rechargeValidateResult.getCode() == null) {
                    errorCode = ErrorCodeConstants.PAY_LETV_CARD_VALIDATE_ERROR;
                    ResponseUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
                    LOG.info("rechargeByLetvCardPasswdNew_" + commonParam.getMac() + "_" + commonParam.getUserId()
                            + "_" + operationType + "_" + cardNumber + "_" + amount + "[errorCode=" + errorCode
                            + "]: cardsecret validate failure.");
                } else if (rechargeValidateResult.getCode() != 0) {// 校验不通过，返回错误信息
                    String code = String.valueOf(rechargeValidateResult.getCode());
                    LetvCardRechargeDto data = new LetvCardRechargeDto();
                    data.setStatus(0);
                    data.setCardType(rechargeValidateResult.getCardtype());
                    data.setErrorcode(code);
                    String msg = null;
                    if (StringUtils.isNotBlank(code)) {// 读取国际化文件
                        msg = MessageUtils.getMessage(VipConstants.VIP_LETV_CARD_ERROR_CODE_PREFIX + code,
                                commonParam.getLangcode());
                    }
                    if (StringUtils.isBlank(msg)) {
                        msg = rechargeValidateResult.getMsg();
                        if (StringUtils.isBlank(msg)) {// 此时返回充值失败默认文案
                            msg = MessageUtils.getMessage(VipConstants.VIP_LETV_CARD_RECHARGE_FAILURE_TEXT,
                                    commonParam.getLangcode());
                        }
                    }
                    data.setMsg(msg);
                    response.setData(data);
                    LOG.info("rechargeByLetvCardPasswdNew_" + commonParam.getMac() + "_" + commonParam.getUserId()
                            + "_" + operationType + "_" + cardNumber + "_" + amount + "[errorCode=" + errorCode
                            + "]: cardsecret validate failure.");
                } else {// 校验通过，可以进行充值
                    LetvCardRechargeDto data = new LetvCardRechargeDto();
                    data.setStatus(0);
                    Integer cardType = rechargeValidateResult.getCardtype();
                    data.setCardType(cardType);// 根据校验结果返回卡类型
                    if (cardType != null && cardType == 1) {// 是充值卡，调用充值接口
                        RechargeResult rechargeResult = this.facadeTpDao.getVipTpDao().rechargeByLetvCardPasswd(
                                cardNumber, commonParam);
                        if (rechargeResult != null && rechargeResult.getCode() == 0) {// 成功
                            data.setStatus(1);
                            Integer beforePoint = rechargeResult.getBeforepoint();// 充值前的乐点数
                            data.setOldBalance(beforePoint);
                            Integer rechargeLetvPoint = null;// 本次充值乐点数
                            Float money = rechargeResult.getMoney();
                            if (money != null) {// 判空操作
                                rechargeLetvPoint = (int) (money * 10);
                                data.setRechargeLetvPoint(rechargeLetvPoint);
                            }
                            if (rechargeLetvPoint == null) {// 判空处理
                                rechargeLetvPoint = 0;
                            }
                            if (beforePoint == null) {// 判空处理
                                beforePoint = 0;
                            }
                            data.setNewBalance(beforePoint + rechargeLetvPoint);
                            data.setLetvPoint(beforePoint + rechargeLetvPoint);
                            data.setOrderCode(orderCode);
                            response.setData(data);
                        } else {
                            errorCode = ErrorCodeConstant.LETV_LEKA_RECHARGE_ERROR;
                            ResponseUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
                            LOG.info("rechargeByLetvCardPasswdNew_" + commonParam.getMac() + "_"
                                    + commonParam.getUserId() + "_" + operationType + "_" + cardNumber + "_" + amount
                                    + "[errorCode=" + errorCode + "],letv card recharge failure.");
                        }
                    } else {// 充值接口，不允许使用兑换卡，返回提示信息
                        errorCode = ErrorCodeConstants.PAY_LETV_CARD_TYPE_ERROR;
                        ResponseUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
                        LOG.info("rechargeByLetvCardPasswdNew_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                + "_" + operationType + "_" + cardNumber + "_" + amount + "[errorCode=" + errorCode
                                + "]:cann't use exchange card for recharge.");
                    }
                }
            }
        }

        return response;
    }

    /**
     * letvcard recharge letvpoint and exchange service
     * @param amount
     * @param cardNumber
     * @param operationType
     * @param commonParam
     * @param letvCardActiveResultDto
     * @return
     */
    public Response<LetvCardRechargeDto> rechargeByLetvCardPasswdNew(Integer amount, String cardNumber,
            Integer operationType, CommonParam commonParam, LetvCardActiveDto.LetvCardActiveResultDto letvCardActiveResultDto) {
        Response<LetvCardRechargeDto> response = new Response<LetvCardRechargeDto>();
        String errorCode = null;
        String orderCode = System.currentTimeMillis() + "";

        int validCode = CommonMsgCodeConstant.REQUEST_SUCCESS;
        if (StringUtils.isBlank(commonParam.getUserId())) {
            validCode = CommonMsgCodeConstant.REQUEST_USERINFO_ERROR;
        } else if (operationType != null && operationType == 2) {// 兑换
            if (StringUtils.isBlank(cardNumber) && (amount == null || amount < 1)) {
                // 不传卡号和最大兑换乐点数，不允许兑换
                validCode = VipMsgCodeConstant.VIP_LETV_CARD_REQUEST_EXCHANGE_INFO_EMPTY;
            }
        } else {// 否则认为是充值，卡号不能为空
            if (StringUtils.isBlank(cardNumber)) {
                validCode = VipMsgCodeConstant.VIP_LETV_CARD_REQUEST_CARDSECRET_EMPTY;
            }
        }
        if (validCode != CommonMsgCodeConstant.REQUEST_SUCCESS) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode,
                    ErrorMsgCodeUtil.parseErrorMsgCode(errorCode, BusinessCodeConstant.LETVCARD, validCode),
                    commonParam.getLangcode());
            LOG.info("rechargeByLetvCardPasswdNew_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + operationType + "_" + cardNumber + "_" + amount + "[errorCode=" + errorCode
                    + "]: recharge by letv card illegal parameters.");
        } else {
            if (operationType != null && operationType == 2) {// 兑换接口
                response = this.exchangeServiceByLetvCardOrAmount(cardNumber, amount, letvCardActiveResultDto,
                        commonParam);
            } else {
                response = this.rechargeByLetvCard(cardNumber, commonParam);
            }
        }
        return response;
    }

    /**
     * exchange by letvcard or amount
     * @param cardNumber
     * @param amount
     * @param letvCardActiveResultDto
     * @param commonParam
     */
    public Response<LetvCardRechargeDto> exchangeServiceByLetvCardOrAmount(String cardNumber, Integer amount,
                                                                           LetvCardActiveDto.LetvCardActiveResultDto letvCardActiveResultDto, CommonParam commonParam) {
        Response<LetvCardRechargeDto> response = new Response<LetvCardRechargeDto>();
        String errorCode = null;
        PageResponse<PricePackageListDto> packages = vipMetadataService.getPricePackageList(
                commonParam.getLangcode());
        if (packages.getResultStatus() == null || packages.getResultStatus() != 1
                || CollectionUtils.isEmpty(packages.getData())) {
            // get vip package data failure and can not exchange
            errorCode = ErrorCodeConstants.PAY_GET_PRICE_PARCKAGE_FAILURE;
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
            LOG.error("rechargeByLetvCardPasswdNew_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + cardNumber + "_" + amount + "[errorCode=" + errorCode + "]: can't get price package.");
        } else {
            Integer availableLetvPoint = this.getUserUsableLetvPoint(response, amount, cardNumber, commonParam);
            Integer cardType = null;
            LetvCardRechargeDto data = response.getData();
            if (data != null) {
                cardType = this.getCardType(data.getCardType());
            } else {
                if (availableLetvPoint != null) {// 乐点余额兑换，则认为是充值兑换
                    cardType = 1;
                }
                data = new LetvCardRechargeDto();
                data.setCardType(cardType);
            }
            if (cardType != null && cardType != 1) {// 兑换卡，直接兑换
                if (data != null && data.getStatus() == null) {
                    RechargeResult rechargeResult = this.facadeTpDao.getVipTpDao().rechargeByLetvCardPasswd(cardNumber,
                            commonParam);
                    if (rechargeResult != null && rechargeResult.getCode() != null && rechargeResult.getCode() == 0) {// 成功
                        Float money = rechargeResult.getMoney();
                        data.setStatus(1);
                        data.setMsg(MessageUtils.getMessage(VipConstants.VIP_LETV_CARD_EXCHANGE_SUCCESS_TEXT,
                                commonParam.getLangcode()));
                        data.setOldBalance(rechargeResult.getBeforepoint());
                        if (money != null) {// 判空处理
                            data.setRechargeLetvPoint((int) (money * 10));
                        }
                        data.setNewBalance(data.getOldBalance() + data.getRechargeLetvPoint());
                        String orderCode = System.currentTimeMillis() + "";
                        data.setOrderCode(orderCode);
                    } else {
                        errorCode = ErrorCodeConstant.LETV_LEKA_RECHARGE_ERROR;
                        data.setErrorcode(errorCode);
                        data.setMsg(MessageUtils.getMessage(errorCode, commonParam.getLangcode()));
                        LOG.info("rechargeByLetvCardPasswdNew_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                + "_" + cardNumber + "_" + amount + "[errorCode=" + errorCode
                                + "],letv card recharge failure.");
                    }
                }
            } else {// 不是兑换卡或者直接根据用户可用余额进行购买
                if (availableLetvPoint != null) {// 如果为空，表示乐卡不可用，此时response已有值
                    if (availableLetvPoint == 0) {// 表示用户余额不足
                        errorCode = ErrorCodeConstants.PAY_LETVPOINT_AVAILABLE_INSUFFICIENT;
                        data.setErrorcode(errorCode);
                        data.setMsg(MessageUtils.getMessage(errorCode, commonParam.getLangcode()));
                        LOG.error("rechargeByLetvCardPasswdNew_" + commonParam.getMac() + "_" + commonParam.getUserId()
                                + "_" + cardNumber + "_" + amount + "[errorCode=" + errorCode
                                + "]: insufficient letvPoint [" + availableLetvPoint + "].");
                    } else {// 表示乐卡可用或者用户有乐点余额
                        // 筛选可买最贵套餐
                        PricePackageListDto highestPackage = null;
                        // 查找letvPoint乐点能购买的最贵套餐
                        highestPackage = this.getHighestPricePackageByLetvPoint(availableLetvPoint, packages.getData());
                        if (highestPackage == null) {// 获取最贵套餐失败
                            errorCode = ErrorCodeConstants.PAY_LETVPOINT_AVAILABLE_INSUFFICIENT;
                            data.setErrorcode(errorCode);
                            data.setMsg(MessageUtils.getMessage(errorCode, commonParam.getLangcode()));
                            LOG.error("rechargeByLetvCardPasswdNew_" + commonParam.getMac() + "_"
                                    + commonParam.getUserId() + "_" + cardNumber + "_" + amount + "[errorCode="
                                    + errorCode + "]: insufficient letvPoint [" + availableLetvPoint + "].");
                        } else {
                            String corderId = VipTpConstant.DEFAULT_CORDERID;
                            Integer bindingtype = null;
                            Integer purchaseType = VipTpConstant.PRODUCT_TYPE_SERVICE_PACKAGE;
                            Long productId = Long.valueOf(highestPackage.getId());
                            String price = highestPackage.getLetvPointPrice();

                            // 2016-01-27，恢复老乐卡激活接口临时修改，添加额外参数LetvCardActiveResultDto以返回更多充值信息
                            if (letvCardActiveResultDto != null) {
                                letvCardActiveResultDto.setAmount(StringUtil.toInteger(
                                        highestPackage.getCurrentPrice(), 0));
                                letvCardActiveResultDto.setPurchaseName(highestPackage.getPackageName());
                            }

                            Integer av = VipTpConstant.BROADCAST_APK_VERSION_LETV;
                            Integer paymentChannel = VipTpConstant.PAYMENT_CHANNEL_LETVPOINT;
                            if (paymentChannel != null && VipTpConstant.PAYMENT_CHANNEL_PAYPAL == paymentChannel) {
                                bindingtype = 1;
                            }
                            Integer buyType = VipTpConstant.PRODUCT_TYPE_SERVICE_PACKAGE;
                            Long pid = Long.valueOf(VipTpConstant.DEFAULT_PID);
                            Integer svip = VipTpConstant.SVIP_TV;
                            Long videoId = null;
                            int validCode = VipMsgCodeConstant.REQUEST_SUCCESS;
                            String activityIds = null;
                            PurchaseProductResponse purchaseTpResponse = this.facadeTpDao.getVipTpDao()
                                    .purchaseProduct(corderId, buyType, productId, pid, price, svip, activityIds, null,
                                            av, videoId, bindingtype, null, null, null, cardNumber, null, null, null,
                                            paymentChannel, commonParam);

                            if (purchaseTpResponse != null && "0000".equals(purchaseTpResponse.getResponse())) {
                                // 2016-01-27，恢复老乐卡激活接口临时修改，添加额外参数LetvCardActiveResultDto以返回更多充值信息
                                if (letvCardActiveResultDto != null
                                        && StringUtils.trimToNull(purchaseTpResponse.getCorderid()) != null) {
                                    letvCardActiveResultDto.setOrderId(StringUtils.trimToNull(purchaseTpResponse
                                            .getCorderid()));
                                }
                                // 充值卡使用成功
                                data.setStatus(1);
                                data.setMsg(MessageUtils.getMessage(VipConstants.VIP_LETV_CARD_EXCHANGE_SUCCESS_TEXT,
                                        commonParam.getLangcode()));
                            } else {
                                errorCode = ErrorCodeConstant.LETV_LEKA_RECHARGE_ERROR;
                                data.setErrorcode(errorCode);
                                data.setMsg(MessageUtils.getMessage(errorCode, commonParam.getLangcode()));
                                LOG.info("rechargeByLetvCardPasswdNew_" + commonParam.getMac() + "_"
                                        + commonParam.getUserId() + "_" + cardNumber + "_" + amount + "[errorCode="
                                        + errorCode + "],letv card recharge failure.");
                            }
                        }
                    }
                }
            }
            // 获取账户会员有效期
            VipAccountTpResponse accountTpResponse = this.facadeTpDao.getVipTpDao().getVipAccount(commonParam);
            if (accountTpResponse != null) {
                Long seniorEndTime = accountTpResponse.getSeniorendtime();
                if (seniorEndTime != null) {
                    String validDate = TimeUtil.getDateStringFromLong(seniorEndTime, TimeUtil.SHORT_DATE_FORMAT);
                    data.setValidDate(validDate);
                    if (letvCardActiveResultDto != null) {
                        letvCardActiveResultDto.setEndDate(validDate);
                    }
                }
            }
            // 获取账户乐点余额
            Integer currentLetvPointBalance = vipMetadataService.getLetvPointBalance(
                    commonParam);
            data.setLetvPoint(currentLetvPointBalance);
            // 筛选余额能购买的最贵套餐
            if (packages != null) {
                PricePackageListDto pricePackage = this.getHighestPricePackageByLetvPoint(currentLetvPointBalance,
                        packages.getData());
                if (pricePackage != null) {
                    data.setPackageName(pricePackage.getPackageName());
                }
            }
            response.setData(data);
        }

        return response;
    }

    /**
     * recharge by letvcard
     * @param cardNumber
     *            letvcard number
     * @param commonParam
     */
    public Response<LetvCardRechargeDto> rechargeByLetvCard(String cardNumber, CommonParam commonParam) {
        LetvCardRechargeDto data = new LetvCardRechargeDto();
        String errorCode = null;

        // check letvcard if can use
        RechargeValidateResult rechargeValidateResult = this.facadeTpDao.getVipTpDao().isCardSecretExisting(cardNumber,
                commonParam);
        if (rechargeValidateResult == null || rechargeValidateResult.getCode() == null) {
            errorCode = ErrorCodeConstants.PAY_LETV_CARD_VALIDATE_ERROR;
            LOG.info("rechargeByLetvCard_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + cardNumber
                    + "[errorCode=" + errorCode + "]: cardsecret validate failure.");
        } else if (rechargeValidateResult.getCode() != 0) {
            // letvcard check failure and return failure info
            String code = String.valueOf(rechargeValidateResult.getCode());
            data.setStatus(0);
            data.setCardType(rechargeValidateResult.getCardtype());
            data.setErrorcode(code);
            String msg = null;
            if (StringUtils.isNotBlank(code)) {// read error message
                msg = MessageUtils.getMessage(VipConstants.VIP_LETV_CARD_ERROR_CODE_PREFIX + code,
                        commonParam.getLangcode());
            }
            if (StringUtils.isBlank(msg)) {
                msg = rechargeValidateResult.getMsg();
                if (StringUtils.isBlank(msg)) {// 此时返回充值失败默认文案
                    msg = MessageUtils.getMessage(VipConstants.VIP_LETV_CARD_RECHARGE_FAILURE_TEXT,
                            commonParam.getLangcode());
                }
            }
            data.setMsg(msg);
            LOG.info("rechargeByLetvCard_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + cardNumber
                    + "[errorCode=" + errorCode + "]: cardsecret validate failure.");
        } else {// 校验通过，可以进行充值
            data.setStatus(0);// default failure
            Integer cardType = rechargeValidateResult.getCardtype();
            data.setCardType(cardType);// 根据校验结果返回卡类型
            if (cardType != null && cardType == 1) {// 是充值卡，调用充值接口
                RechargeResult rechargeResult = this.facadeTpDao.getVipTpDao().rechargeByLetvCardPasswd(cardNumber,
                        commonParam);
                if (rechargeResult != null && rechargeResult.getCode() == 0) {// 成功
                    data.setStatus(1);// recharge success
                    Integer beforePoint = rechargeResult.getBeforepoint();// 充值前的乐点数
                    data.setOldBalance(beforePoint);
                    Integer rechargeLetvPoint = null;// 本次充值乐点数
                    Float money = rechargeResult.getMoney();
                    if (money != null) {// 判空操作
                        rechargeLetvPoint = (int) (money * 10);
                        data.setRechargeLetvPoint(rechargeLetvPoint);
                    }
                    if (rechargeLetvPoint == null) {// 判空处理
                        rechargeLetvPoint = 0;
                    }
                    if (beforePoint == null) {// 判空处理
                        beforePoint = 0;
                    }
                    data.setNewBalance(beforePoint + rechargeLetvPoint);
                    data.setLetvPoint(beforePoint + rechargeLetvPoint);
                    String orderCode = System.currentTimeMillis() + "";
                    data.setOrderCode(orderCode);
                } else {
                    errorCode = ErrorCodeConstant.LETV_LEKA_RECHARGE_ERROR;
                    LOG.info("rechargeByLetvCard_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                            + cardNumber + "[errorCode=" + errorCode + "],letv card recharge failure.");
                }
            } else {// 充值接口，不允许使用兑换卡，返回提示信息
                errorCode = ErrorCodeConstants.PAY_LETV_CARD_TYPE_ERROR;
                LOG.info("rechargeByLetvCard_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + cardNumber + "[errorCode=" + errorCode + "]:cann't use exchange card for recharge.");
            }
        }
        Response<LetvCardRechargeDto> response = new Response<LetvCardRechargeDto>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setData(data);
        }
        return response;
    }

    /**
     * @param loginName
     * @param cardSecret
     * @param password
     * @param channel
     * @param clientIp
     * @param userAuthCode
     * @param isCIBN
     * @param av
     * @param commonParam
     * @return
     */
    public Response<LetvCardActiveDto> consumeByLetvCardOld(String loginName, String cardSecret, String password,
            String channel, String userAuthCode, Integer isCIBN, Integer av, CommonParam commonParam) {
        Response<LetvCardActiveDto> response = new Response<LetvCardActiveDto>();
        // 1、验证用户相关
        Boolean mobileExisting = userService.checkMobileExisting(loginName,
                commonParam.getMac(), channel, commonParam.getBroadcastId());
        LetvUserDto resp = null;
        if (mobileExisting) {// 手机号存在则登录
            LoginRequest request = new LoginRequest(loginName, password, Calendar.getInstance(), null, null, null);
            request.setMac(commonParam.getMac());
            request.setChannel(channel);

            request.setClientIp(commonParam.getClientIp());
            resp = userService.login_v3(request, commonParam);
        } else {// 不存在则使用无密码快捷注册
            resp = userService.register_v3(loginName, userAuthCode, isCIBN,
                    commonParam.getMac(), channel, commonParam.getBroadcastId(), commonParam);
        }
        // 2、充值
        LetvCardActiveDto.LetvCardActiveResultDto purchaseInfo = new LetvCardActiveDto.LetvCardActiveResultDto();
        Response<LetvCardRechargeDto> rechargeResponse = this.rechargeByLetvCardPasswdNew(null, cardSecret, 2,
                commonParam, purchaseInfo);

        if (rechargeResponse.getResultStatus() != null && 1 == rechargeResponse.getResultStatus()
                && rechargeResponse.getErrCode() == null) {
            // 走充值卡兑换逻辑，实际不返orderId，这里容错
            if (purchaseInfo.getOrderId() != null) {
                LetvCardRechargeDto rechargeDto = rechargeResponse.getData();
                Response<OrderStatusDto> orderStatusResponse = vipMetadataService
                        .checkOrderStatus(purchaseInfo.getOrderId(), commonParam);
                if (orderStatusResponse.getResultStatus() != null && 1 == orderStatusResponse.getResultStatus()
                        && orderStatusResponse.getErrCode() == null) {
                    OrderStatusDto orderStatus = orderStatusResponse.getData();
                    purchaseInfo.setPurchaseName(orderStatus.getPurchaseName());
                    purchaseInfo.setPurchaseType(orderStatus.getPurchaseType());
                    purchaseInfo.setStartDate(orderStatus.getStartDate());
                    purchaseInfo.setEndDate(orderStatus.getEndDate());

                    LetvCardActiveDto data = new LetvCardActiveDto(resp, purchaseInfo);
                    response.setResultStatus(ResponseUtil.RESPONSE_SUCCESS);
                    response.setData(data);
                } else {
                    // 查询订单失败
                    response.setResultStatus(orderStatusResponse.getResultStatus());
                    response.setErrCode(orderStatusResponse.getErrCode());
                    response.setErrMsg(orderStatusResponse.getErrMsg());
                }
            } else {
                LetvCardRechargeDto rechargeDto = rechargeResponse.getData();
                LetvCardActiveDto data = new LetvCardActiveDto(resp, purchaseInfo);
                response.setResultStatus(ResponseUtil.RESPONSE_SUCCESS);
                response.setData(data);
            }
        } else {
            // 充值失败
            response.setResultStatus(rechargeResponse.getResultStatus());
            response.setErrCode(rechargeResponse.getErrCode());
            response.setErrMsg(rechargeResponse.getErrMsg());
        }

        return response;
    }

    /**
     * 获取此次兑换应使用的乐点数
     * @param response
     * @param rechargeRequest
     * @param logPrefix
     * @param locale
     * @return
     */
    private Integer getUserUsableLetvPoint(Response<LetvCardRechargeDto> response, Integer amount, String cardNumber,
            CommonParam commonParam) {
        Integer letvPoint = null;// 如果值为null，表示不可以继续兑换
        String errorCode = null;

        if (StringUtils.isNotBlank(cardNumber)) {// 根据乐卡来获取余额
            // 验证乐卡是否有效
            RechargeValidateResult rechargeValidateResult = this.facadeTpDao.getVipTpDao().isCardSecretExisting(
                    cardNumber, commonParam);
            if (rechargeValidateResult == null || rechargeValidateResult.getCode() == null) {
                errorCode = ErrorCodeConstants.PAY_LETV_CARD_VALIDATE_ERROR;
                ResponseUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
                LOG.info("getUserUsableLetvPoint_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                        + cardNumber + "_" + amount + "[errorCode=" + errorCode + "]: cardsecret validate failure.");
            } else {
                LetvCardRechargeDto data = new LetvCardRechargeDto();
                data.setCardType(rechargeValidateResult.getCardtype());
                response.setData(data);
                errorCode = String.valueOf(rechargeValidateResult.getCode());
                if (rechargeValidateResult.getCode() != null && rechargeValidateResult.getCode() != 0) {// 校验不通过，返回错误信息
                    data.setStatus(0);
                    data.setErrorcode(errorCode);
                    String msg = null;
                    if (StringUtils.isNotBlank(errorCode)) {// 读取国际化文件
                        msg = MessageUtils.getMessage(VipConstants.VIP_LETV_CARD_ERROR_CODE_PREFIX + errorCode,
                                commonParam.getLangcode());
                    }
                    if (StringUtils.isBlank(msg)) {
                        msg = rechargeValidateResult.getMsg();
                        if (StringUtils.isBlank(msg)) {// 此时返回兑换失败默认文案
                            msg = MessageUtils.getMessage(VipConstants.VIP_LETV_CARD_EXCHANGE_FAILURE_TEXT,
                                    commonParam.getLangcode());
                        }
                    }
                    data.setMsg(msg);
                    LOG.info("getUserUsableLetvPoint_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                            + cardNumber + "_" + amount + "[errorCode=" + errorCode + "]: cardsecret validate failure.");
                } else {// 校验通过，就需要进行充值
                    String cardMoneyStr = rechargeValidateResult.getMoney();
                    if (StringUtils.isNotBlank(cardMoneyStr)) {
                        Double money = Double.valueOf(cardMoneyStr);
                        if (money != null && money > 0) {
                            money = money * 10;
                            letvPoint = money.intValue();
                        }
                    }
                }
            }
        } else {// 根据客户端传参和用户余额来获取
            Integer userUsableLetvPoint = this.getUserUsableLetvPoint(commonParam);
            letvPoint = amount > userUsableLetvPoint ? userUsableLetvPoint : amount;
        }
        return letvPoint;
    }

    /**
     * 获取用户可用乐点余额
     * @param userName
     * @param userId
     * @return
     */
    private Integer getUserUsableLetvPoint(CommonParam commonParam) {
        Integer letvPointBalance = vipMetadataService.getLetvPointBalance(commonParam);
        return letvPointBalance;
    }

    /**
     * 根据BOSS返回的卡类型，决定返回给客户端的卡类型，服务端做兼容一种终端设备多种卡类型
     * @param cardType
     * @return
     */
    private Integer getCardType(Integer cardType) {
        return VipConstants.getCardType(cardType);
    }

    private String getSubend(String subend, Boolean isFromCntv, Boolean isFromCibn, CommonParam commonParam) {
        if (subend != null) {
            return subend;
        }
        Integer result = null;
        if (isFromCntv != null && isFromCntv) {
            result = VipTpConstant.SUB_ORDER_FROM_7;
        } else if (isFromCibn != null && isFromCibn) {
            result = VipTpConstant.SUB_ORDER_FROM_12;
        } else if (commonParam.getTerminalSeries() != null && commonParam.getTerminalBrand() != null
                && "letv".equalsIgnoreCase(commonParam.getTerminalBrand())) {
            result = VipTpConstantUtils.getSubendByTerminalSeries(commonParam.getTerminalSeries());
        } else if (commonParam.getTerminalBrand() != null) {
            result = VipTpConstantUtils.getSubendByTerminalBrand(commonParam.getTerminalBrand());
        }
        return result == null ? "" : result + "";
    }

    /**
     * 在活动中去除易宝支付的活动（当时这个活动是由服务端虚拟返给客户端的，为了展示成9.9元，所以支付时需要过滤掉）
     * @param activityIds
     * @return
     */
    private String trimYeepayActivityIds(String activityIds) {
        // 活动ID中去除虚拟的一元支付ID，活动ID的数据结构形如：1,2,3
        if (StringUtils.isNotBlank(activityIds)) {
            if (activityIds.indexOf(VipTpConstant.PAYMENT_ACTIVITY_ONE_FOR_MONTH_VIRTUALID) != -1) {
                activityIds = activityIds + ",";// 在字符串末尾加上逗号，便于替换时连逗号一起替换而不需要判断位置
                // 替换虚拟活动ID为空串
                activityIds = activityIds.replace(VipTpConstant.PAYMENT_ACTIVITY_ONE_FOR_MONTH_VIRTUALID + ",", "");
                if (StringUtils.isNotBlank(activityIds)) {
                    // 去掉最后一个逗号
                    activityIds = activityIds.substring(0, activityIds.length() - 1);
                }
            }
            if (StringUtils.isNotBlank(activityIds)) {// 处理一些非法的活动ID序列
                String[] actids = activityIds.split(",");
                StringBuilder sb = new StringBuilder();
                for (String id : actids) {
                    if (StringUtils.isNotBlank(id) && !"null".equals(id)) {
                        sb.append(id).append(",");
                    }
                }
                if (sb != null && sb.length() > 0) {
                    activityIds = sb.substring(0, sb.length() - 1);
                } else {
                    activityIds = null;
                }
            }
        }
        return activityIds;
    }

    /**
     * 购买商品接口的参数校验
     */
    private int validateForPurchaseProduct(Integer av, String userId, String corderid, Integer buyType, Long productid,
            Integer paymentChannel, String bindid, String price, String mac, Integer subend, String appType) {
        // appType的值是在controller类中的两个接口写死的，以后需要客户端传参，然后就可以统一一个接口入口
        // 直播SDK中，av必须为2
        if (VipConstants.APPTYPE_LETV_LIVE_SDK.equals(appType)) {// 先校验直播sdk的参数
            if (av == null || VipTpConstant.BROADCAST_APK_VERSION_TPSDK != av) {
                return VipMsgCodeConstant.VIP_TP_PURCHASE_VIP_REQUEST_AV_ERROR;
            }
            // 直播SDK MAC必传
            if (StringUtils.isBlank(mac)) {
                return VipMsgCodeConstant.VIP_TP_PURCHASE_VIP_REQUEST_MAC_EMPTY;
            }
            // 直播SDK暂只支持单点
            if (buyType == null || buyType != VipTpConstant.PRODUCT_TYPE_SINGLE_FILM) {
                return VipMsgCodeConstant.VIP_TP_PURCHASE_VIP_REQUEST_ONLY_SINGLE;
            }
            // 直播SDK暂支持海信设备
            if (subend == null) {
                return VipMsgCodeConstant.VIP_TP_PURCHASE_VIP_REQUEST_SUBEND_ERROR;
            }
            // 直播SDK支持支付宝和微信支付和华数
            if (paymentChannel != null) {
                if (VipTpConstant.PAYMENT_CHANNEL_ALIPAY != paymentChannel
                        && VipTpConstant.PAYMENT_CHANNEL_WEIXIN != paymentChannel) {
                    if (subend == 101) {
                        if (paymentChannel != VipTpConstant.PAYMENT_CHANNEL_HUASHU) {// 华数支付需要判断支付渠道是否为46
                            return VipMsgCodeConstant.VIP_TP_PURCHASE_VIP_REQUEST_PAYMENTCHANNEL_ERROR;
                        }
                    } else {
                        return VipMsgCodeConstant.VIP_TP_PURCHASE_VIP_REQUEST_PAYMENTCHANNEL_ERROR;
                    }
                }
            } else {
                return VipMsgCodeConstant.VIP_TP_PURCHASE_VIP_REQUEST_PAYMENTCHANNEL_ERROR;
            }
        }

        if (av != null && VipTpConstant.BROADCAST_APK_VERSION_TPSDK != av) {
            if (StringUtils.isBlank(userId)) {
                return VipMsgCodeConstant.REQUEST_USERINFO_ERROR;
            }
        }
        if ("*".equals(corderid)) {
            return VipMsgCodeConstant.VIP_VIP_PURCHASE_ORDER_REQUEST_CORDERID_ERROR;
        }
        if (buyType == null || BUYTYPE.getBuytypeById(buyType) == null) {
            return VipMsgCodeConstant.VIP_PURCHASE_ORDER_REQUEST_PRODUCT_TYPE_ERROR;
        }
        if (buyType == 2) {
            if (productid == null || productid < 1) {
                return VipMsgCodeConstant.VIP_PURCHASE_ORDER_REQUEST_PRODUCTID_ERROR;
            }
        }
        if (paymentChannel == null || PAYMENT_CHANNEL.getPaymentChannelById(paymentChannel) == null) {
            return VipMsgCodeConstant.VIP_PURCHASE_ORDER_REQUEST_PAYMENTCHANNEL_ILLEGAL;
        }

        if (paymentChannel == VipTpConstant.PAYMENT_CHANNEL_YEEPAYBIND) {
            if (StringUtils.isBlank(bindid)) {
                return VipMsgCodeConstant.VIP_PURCHASE_VIP_REQUEST_QUICK_PAY_BINDID_EMPTY;
            }
        }
        // 单点影片不允许使用乐点支付
        if (VipTpConstant.PRODUCT_TYPE_SINGLE_FILM == buyType
                && VipTpConstant.PAYMENT_CHANNEL_LETVPOINT == paymentChannel) {
            return VipMsgCodeConstant.VIP_PURCHASE_VIP_REQUEST_SINGLE_PAYMENTCHANNEL_ERROR;
        }

        // 单点时必须传价格，价格是整形或double形式的字符串格式，但传向支付中心接口中的参数为int型
        if (VipTpConstant.PRODUCT_TYPE_SINGLE_FILM == buyType) {
            if (price == null || !NumberFormatUtil.isNumeric(price) || Double.parseDouble(price) < 0) {
                return VipMsgCodeConstant.VIP_PURCHASE_VIP_REQUEST_SINGLE_PRICE_ERROR;
            }
        }

        return VipMsgCodeConstant.REQUEST_SUCCESS;
    }

    /**
     * 根据下单接口返回的状态码判断下单是否成功
     * @param paymentChannel
     * @param status
     * @param response
     * @param corderId
     * @return
     */
    private boolean paySuccess(Integer paymentChannel, Integer status, String response, String corderId) {
        if (paymentChannel != null && paymentChannel == VipTpConstant.PAYMENT_CHANNEL_LETVPOINT) {
            // 乐点支付方式第三方接口响应成功
            return "0000".equals(response);
        } else {
            // 其他支付方式第三方接口响应成功
            return status != null && status == 1 && StringUtils.isNotBlank(corderId);
        }
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
    public Response<PurchaseVipDto> purchaseProductNoPaymentChannel(String productId, String price, String activityIds,
            String couponCode, Integer purchaseType, Integer av, String position, String productName,
            String callBackUrl, String displayName, CommonParam commonParam) {
        PurchaseVipDto data = new PurchaseVipDto();
        String errorCode = null;

        if (purchaseType == null || purchaseType < 1 || purchaseType > 3) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            LOG.info("purchaseProductNoPaymentChannel_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + productId + "_" + purchaseType + "[errorCode=" + errorCode + "]: illegal parameters.");
        } else if (av != null && VipTpConstant.BROADCAST_APK_VERSION_TPSDK != av
                && StringUtils.isBlank(commonParam.getUserId())) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            LOG.info("purchaseProductNoPaymentChannel_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + productId + "_" + purchaseType + "[errorCode=" + errorCode + "]: illegal parameters.");
        } else if (purchaseType != 2 && (!NumberFormatUtil.isNumeric(price) || Double.parseDouble(price) <= 0)) {
            errorCode = ErrorCodeConstants.PAY_ILLEGAL_PARAMETER;
            LOG.info("purchaseProductNoPaymentChannel_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                    + productId + "_" + purchaseType + "[errorCode=" + errorCode + "]: illegal parameters.");
        } else {
            if (StringUtil.isBlank(position)) {
                activityIds = vipMetadataService.getVipConfigInfo(
                        VipConstants.VIP_CHECKOUT_COUNTER_DEFAULT_ACTIVITY_KEY, "");
                if (StringUtil.isNotBlank(activityIds)) {
                    GetActivityDataResponse activityResponse = this.facadeTpDao.getVipTpDao().getActivityData(
                            activityIds, commonParam);
                    if (activityResponse == null || activityResponse.getCode() == null
                            || activityResponse.getCode() != 0 || CollectionUtils.isEmpty(activityResponse.getData())) {
                        LOG.info("purchaseProductNoPaymentChannel_" + commonParam.getMac() + "_"
                                + commonParam.getUserId() + "_" + productId + "_" + purchaseType
                                + " get stable payment activity [" + activityIds + "] failed.");
                        activityIds = "";
                    } else {
                        Map<String, PaymentActivityDto> map = vipMetadataService
                                .parsePaymentActivityFromTpNew(activityResponse.getData(), commonParam.getLangcode());
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
            String pid = null;// BOSS parameter: buy live or album
            String orderFrom = null;// BOSS parameter: buy album
            String screenings = null;// temp variable to save screenings
            if (purchaseType == 3) {
                // pid formatter like 00 000 0000 000 0000 00 000, the formatter
                // combine of
                // channel(2)+competition(3)+year(4)+rounds(3)+screenings(4)+type(2)+count(3)
                screenings = productId;
                pid = productId + VipTpConstant.LIVE_DEFAULT_TYPE + "001";
                productId = "1001";// BOSS parameter: buy live send 1001
            } else if (purchaseType == 1) {
                pid = String.valueOf(productId);
                productId = "0";// BOSS parameter: buy album send 0
                orderFrom = "9";// BOSS parameter: buy album send 9
            }
            PurchaseProductResponse purchaseProductResponse = this.facadeTpDao.getVipTpDao()
                    .purchaseProductNoPaymentchannel(productId, pid, activityIds, couponCode, orderFrom, commonParam);
            if (purchaseProductResponse == null || StringUtil.isBlank(purchaseProductResponse.getOrderId())) {
                errorCode = ErrorCodeConstants.PAY_FAILURE;
            } else {
                String orderId = purchaseProductResponse.getOrderId();
                data.setCorderid(orderId);
                data.setOrdernumber(orderId);
                ExtendParam extendParam = new ExtendParam();
                data.setExtendParam(extendParam);
                extendParam.setMerchant_no(orderId);
                extendParam.setOut_trade_no(orderId);
                if (screenings == null) {
                    extendParam.setProduct_id(String.valueOf(productId));
                } else {// buy live send screenings for productId
                    extendParam.setProduct_id(screenings);
                }
                extendParam.setPrice(price);
                if (LocaleConstant.Langcode.ZH_HK.equalsIgnoreCase(commonParam.getLangcode())) {
                    extendParam.setLanguage("0");// Traditional
                } else if (LocaleConstant.Langcode.EN_US.equalsIgnoreCase(commonParam.getLangcode())) {
                    extendParam.setLanguage("1");// English
                } else {
                    extendParam.setLanguage("2");// Simplified
                }
                String baseUrl = "";
                if (TerminalUtil.isLetvHK(commonParam)) {
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
                    GetPayTokenResponse tokenResponse = this.facadeTpDao.getVipTpDao().getPayTokenForOverseas(baseUrl,
                            VipTpConstant.SIGN_KEY_HK, productName, extendParam, displayName, img4Sdk, commonParam);
                    if (tokenResponse != null && tokenResponse.getCode() != null && tokenResponse.getCode() == 0) {
                        // get pay token success
                        extendParam.setToken(tokenResponse.getToken());
                        extendParam.setLepayOrderNo(tokenResponse.getLepayOrderNo());
                    } else {
                        // get pay token failure
                        errorCode = ErrorCodeConstants.PAY_FAILURE;
                    }
                } else if (TerminalUtil.isLetvUs(commonParam)) {
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
                    GetPayTokenResponse tokenResponse = this.facadeTpDao.getVipTpDao().getPayTokenForOverseas(baseUrl,
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
                    extendParam.setNotify_url(VipConstants.VIP_PURCHASE_EXTEND_NOTIFY_URL_CN);
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
                    String sign = this.facadeTpDao.getVipTpDao().getPurchaseVipSign(extendParam, commonParam);
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
}
