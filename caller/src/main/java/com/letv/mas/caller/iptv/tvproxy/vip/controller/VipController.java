package com.letv.mas.caller.iptv.tvproxy.vip.controller;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.BroadcastConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.LetvUserVipCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor.CheckLoginInterceptorAnnotation;
import com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor.HttpParameterInterceptorAnnotation;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageCommonResponse;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageResponse;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request.GetBindInfoRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.MmsDataUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.TerminalUtil;
import com.letv.mas.caller.iptv.tvproxy.vip.VipConstants;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.vip.service.VipConsumptionService;
import com.letv.mas.caller.iptv.tvproxy.vip.service.VipMetadataService;
import com.letv.mas.caller.iptv.tvproxy.vip.service.VipV2Service;
import com.letv.mas.caller.iptv.tvproxy.vip.util.VipUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@RestController("v2.vipController")
public class VipController {

    @Autowired
    VipMetadataService vipMetadataService;
    
    @Autowired
    VipConsumptionService vipConsumptionService;

    @Autowired
    VipV2Service vipV2Service;
    
    /**
     * get user recharge record
     * @param day
     *            query recharge record within days
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "查询用户乐点充值记录.", httpMethod = "GET", notes = "注:这个接口的原始文档没有流传下来，此文档是根据boss接口文档反推出来的，使用时请和调用方确认")
    @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/recharge/getRechargeRecord")
    public PageResponse<RechargeRecordDto> getRechargeRecord(
            @ApiParam(value = "最近多少天充值记录(优先以days为查询条件)", required = true) @RequestParam("day") Integer day,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipMetadataService.getRechargeRecord(day, commonParam);
    }

    /**
     * get user consumption record
     * @param status
     *            order status,0--all,1--pay failure,2--pay success,3--expired
     * @param day
     *            query consumption record within days
     * @param page
     * @param pageSize
     * @param orderTypes
     *            order types
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "get user consumption record", httpMethod = "GET", notes = "注:应用版本号(appCode)小于286时调用boss的v1接口http://wiki.letv.cn/pages/viewpage.action?pageId=24307160."
            + "应用版本号大于等于286调bosss的v2接口http://wiki.letv.cn/pages/viewpage.action?pageId=55252934")
    @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/consume/getConsumptionRecord")
    public PageCommonResponse<ConsumptionRecordDto> getConsumptionRecord(
            @ApiParam(value = "appcode小于286时0:是全部 1:待付款 2:交易成功 3:交易过期.appcode大于等于286时-1:全部 0:未开通 1:已开通 2:已关闭", required = true) @RequestParam("status") Integer status,
            @ApiParam(value = "查询日期以内消费记录", required = true) @RequestParam("day") Integer day,
            @ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
            @ApiParam(value = "每页数量", required = true) @RequestParam("pageSize") Integer pageSize,
            @ApiParam(value = "订单类型,多种类型用逗号分隔.0:单片点播--电影1:单片点播--电视剧1001:直播内容点播", required = false) @RequestParam(value = "orderTypes", required = false) String orderTypes,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        List<String> orderTypeList = new LinkedList<String>();
        if (StringUtils.isNotBlank(orderTypes)) {// 先解析要查询的订单类型
            String[] typeArray = orderTypes.trim().split(",");
            orderTypeList = Arrays.asList(typeArray);
        }
        Integer ac = TerminalUtil.parseAppCode(commonParam.getAppCode());
        Map<Integer, Integer> statusCode = new HashMap<Integer, Integer>();
        statusCode.put(0, -1);
        if (ac < 286) {
            return vipMetadataService.getConsumptionRecord(status, day, page, pageSize,
                    orderTypeList, commonParam);
        } else {
            return vipMetadataService.getConsumptionRecordV2(statusCode.get(status), day, page,
                    pageSize, orderTypeList, commonParam);
        }
    }

    /**
     * check phone if support pay
     * @param phone
     *            phone number
     * @return
     */
    @ApiOperation(value = "手机号验证接口,反馈手机号是否支持话费支付及套餐包价格信息", httpMethod = "GET", notes = "底层接口:http://wiki.letv.cn/pages/viewpage.action?pageId=23790433")
    @RequestMapping("/vip/consume/checkPhone")
    public Response<CheckPhoneDto> checkPhone(
            @ApiParam(value = "手机号", required = true) @RequestParam("phone") String phone,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipMetadataService.getPhoneCheck(phone, commonParam);
    }

    @ApiOperation(value = "查询订单状态v1", httpMethod = "GET", notes = "底层接口:http://wiki.letv.cn/pages/viewpage.action?pageId=24307160")
    @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/consume/checkOrder")
    public Response<OrderStatusDto> checkOrderStatus(
            @ApiParam(value = "订单号", required = true) @RequestParam("orderId") String orderId,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipMetadataService.checkOrderStatus(orderId, commonParam);
    }

    /**
     * check order if it has been pay success
     * @param orderId
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "查询订单状态v2", httpMethod = "GET", notes = "底层接口:http://wiki.letv.cn/pages/viewpage.action?pageId=55252934"

    )
    @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/v2/consume/checkOrder")
    public Response<OrderStatusDto> checkOrderStatusV2(
            @ApiParam(value = "订单号", required = true) @RequestParam("orderId") String orderId,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipMetadataService.checkOrderStatusV2(orderId, commonParam);
    }

    /**
     * get qrcode with user token info when pay success
     * @param expire
     * @param validDate
     * @param productType
     *            purchase type 1--album,2--vip package,3--live
     * @param productId
     * @param position
     *            if purchase vip package and have qrcode,need to distinguish
     *            the activity by position
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "get qrcode with user token info when pay success", httpMethod = "GET")
    @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/consume/getLotteryQRCode")
    public Response<QRCodeDto> getLotteryQRCode(
            @ApiParam(value = "有效时长", required = false) @RequestParam(value = "expire", required = false, defaultValue = "300") Long expire,
            @ApiParam(value = "传0", required = false) @RequestParam(value = "validDate", required = false) Long validDate,
            @ApiParam(value = "传0", required = false) @RequestParam(value = "productType", required = false, defaultValue = "2") Integer productType,
            @ApiParam(value = "传0", required = false) @RequestParam(value = "productId", required = false) Integer productId,
            @ApiParam(value = "触达位", required = false) @RequestParam(value = "position", required = false) String position,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        if (expire == null || expire <= VipConstants.CRITICAL_LOTTERY_QRCODE_EXPIRE_MIN) {
            // expire time default 300 seconds
            expire = VipConstants.DEFAULT_LOTTERY_QRCODE_EXPIRE;
        }

        return vipConsumptionService.getLotteryQRCode(expire, validDate, productType,
                productId, position, commonParam);
    }

    /**
     * get free vip by user info
     * @param iskonka
     * @param imei
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "领取免费会员接口v1", httpMethod = "GET")
    @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/consume/getFreeVip")
    public Response<FreeVipDto> getFreeVip(
            @ApiParam(value = "是否康佳电视，0--不是，1--是", required = false) @RequestParam(value = "iskonka", required = false) String iskonka,
            @ApiParam(value = "如果是康佳电视，则imei传康佳电视设备信息,其他电视不传", required = false) @RequestParam(value = "imei", required = false) String imei,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipConsumptionService.getFreeVip(iskonka, imei, commonParam);
    }

    @ApiOperation(value = "领取免费会员接口v2", httpMethod = "GET")
    @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/v2/consume/getFreeVip")
    public Response<FreeVipDto> getFreeVipV2(
            @ApiParam(value = "是否康佳电视，0--不是，1--是", required = false) @RequestParam(value = "iskonka", required = false) String iskonka,
            @ApiParam(value = "imei值.如果是康佳电视，则imei传康佳电视设备信息,其他电视不传", required = false) @RequestParam(value = "imei", required = false) String imei,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipV2Service.getFreeVipV2(iskonka, imei, commonParam);
    }

    /**
     * get bind info of machine and the user
     * @param deviceType
     *            terminal type,1--tv terminal,2--mobile terminal, 3--letv box
     *            terminal
     * @param type
     *            query type,0--all bind info,1--current machine bind info,
     *            2--present to current user bind info
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "查询设备机卡信息v1", httpMethod = "GET")
    @RequestMapping({ "/vip/devicebind/getDeviceBindInfo", "/vip/devicebind/get" })
    public Response<DeviceBindDto> getDeviceBindInfo(
            @ApiParam(value = "设备类型1--tv terminal,2--mobile terminal, 3--letv box terminal", required = true) @RequestParam(value = "deviceType", required = false) Integer deviceType,
            @ApiParam(value = "查询类型0--all bind info,1--current machine bind info 2--present to current user bind info", required = true) @RequestParam(value = "type", required = false) String type,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        if (deviceType == null) {// default tv terminal
            deviceType = VipTpConstant.DEVICE_BIND_DEVICE_TYPE_TV;
        }
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LETV_US.equals(commonParam.getTerminalApplication())
                || StringUtils.isBlank(type)) {
            // us application only get current machine bind info or
            // if type is blank then query current machine bing info
            type = VipConstants.DEVICE_BIND_QUERY_TYPE_1;
        }
        return vipMetadataService.getDeviceBindInfo(type, deviceType, commonParam);
    }

    /**
     * check user if can participate the directional activity
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "check user if can participate the directional activity", httpMethod = "GET")
    @CheckLoginInterceptorAnnotation
    @Deprecated
    @RequestMapping("/vip/consume/checkDirectionalPushUser")
    public Response<DirectionalPushPricePackageDto> checkDirectionalPushUser(@ModelAttribute CommonParam commonParam) {
        return vipMetadataService.checkDirectionalPushUser(commonParam);
    }

    /**
     * update user who can participate the directional activity to cache
     * @return
     */
    @ApiOperation(value = "update user who can participate the directional activity to cache", httpMethod = "GET")
    @Deprecated
    @RequestMapping("/vip/consume/updateDirectionalPushUser")
    public Response<Boolean> updateDirectionalPushUser() {
        return vipConsumptionService.updateDirectionalPushUser();
    }

    /**
     * purchase the most valuable vip package by user letv point
     * @param deviceType
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "根据可使用的乐点数，直接购买最贵的套餐", httpMethod = "GET")
    @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/consume/consumeVipByAvailableLetvPoint")
    public Response<LetvPointConsumeVipDto> consumeVipByAvailableLetvPoint(
            @ApiParam(value = "套餐乐点数", required = true) @RequestParam("amount") Integer amount,
            @ApiParam(value = "传空", required = false) @RequestParam(value = "serialNumber", required = false) String serialNumber,
            @ApiParam(value = "传空", required = false) @RequestParam(value = "mediaType", required = false) String mediaType,
            @ApiParam(value = "传空", required = false) @RequestParam(value = "deviceType", required = false, defaultValue = "1") Integer deviceType,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        Integer broadcastId = commonParam.getBroadcastId();
        // 2.6.1CIBN版本新需求
        Integer av = VipTpConstant.BROADCAST_APK_VERSION_LETV; // 默认letv
        if (broadcastId != null
                && VipConstants.BROADCAST_CIBN == broadcastId
                && TerminalCommonConstant.TERMINAL_APPLICATION_CIBN.equalsIgnoreCase(commonParam
                        .getTerminalApplication())
                && VipConstants.TERMINAL_BRAND_CIBN.equalsIgnoreCase(commonParam.getTerminalBrand())) {
            // 鉴别当前订单是否与国广相关
            // subend = String.valueOf(VipTpConstant.SUB_ORDER_FROM_12);

            // 2.6.1需求，针对国广订单，需要特殊标识参数，传给支付中心，便于订单同步
            av = VipTpConstant.BROADCAST_APK_VERSION_CIBN;
        }
        Integer subend = VipUtil.getSubend(commonParam.getTerminalBrand(), commonParam.getTerminalSeries());
        return vipConsumptionService.consumeVipByAvailableLetvPoint(amount, serialNumber,
                mediaType, deviceType, subend, av, commonParam);
    }

    /**
     * generate order or pay url
     * @param corderid
     * @param purchaseType
     *            product type
     * @param productid
     *            product id
     * @param paymentChannel
     *            payment channel
     * @param price
     * @param productName
     * @param activityIds
     *            activity id for vip package
     * @param couponCode
     * @param bindid
     *            yeepay parameter,if user has binded credit card
     * @param device_id
     *            wasu parameter
     * @param site_id
     *            wasu parameter
     * @param tv_id
     *            wasu parameter
     * @param position
     *            new checkout counter parameter
     * @param callBackUrl
     *            用于支付sdk签名的字段
     * @param displayName
     *            用于支付sdk签名的字段,用户显示名
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "下单接口v1", httpMethod = "GET")
    @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/consume/purchaseVip")
    public Response<PurchaseVipDto> purchaseVip(
            @ApiParam(value = "传0", required = false) @RequestParam(value = "corderid", required = false, defaultValue = "0") String corderid,
            @ApiParam(value = "商品类型", required = true) @RequestParam(value = "purchaseType") Integer purchaseType,
            @ApiParam(value = "商品id, 为对应的专辑id或视频id", required = true) @RequestParam(value = "productid") String productid,
            @ApiParam(value = "支付通道", required = false) @RequestParam(value = "paymentChannel", required = false) Integer paymentChannel,
            @ApiParam(value = "价格", required = false) @RequestParam(value = "price", required = false) String price,
            @ApiParam(value = "商品名称", required = false) @RequestParam(value = "productName", required = false) String productName,
            @ApiParam(value = "活动id，TBD", required = false) @RequestParam(value = "activityIds", required = false) String activityIds,
            @ApiParam(value = "优惠券号码", required = false) @RequestParam(value = "couponCode", required = false) String couponCode,
            @ApiParam(value = "易宝支付参数", required = false) @RequestParam(value = "bindid", required = false) String bindid,
            @ApiParam(value = "传空", required = false) @RequestParam(value = "device_id", required = false) String device_id,
            @ApiParam(value = "传空", required = false) @RequestParam(value = "site_id", required = false) String site_id,
            @ApiParam(value = "传空", required = false) @RequestParam(value = "tv_id", required = false) String tv_id,
            @ApiParam(value = "位置信息，此值为空时去boss取活动数据", required = false) @RequestParam(value = "position", required = false) String position,
            @ApiParam(value = "回跳地址", required = false) @RequestParam(value = "call_back_url", required = false) String callBackUrl,
            @ApiParam(value = "用户名", required = false) @RequestParam(value = "displayName", required = false) String displayName,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        Integer av = VipTpConstant.BROADCAST_APK_VERSION_LETV; // 默认letv
        if (commonParam.getBroadcastId() != null
                && VipConstants.BROADCAST_CIBN == commonParam.getBroadcastId()
                && TerminalCommonConstant.TERMINAL_APPLICATION_CIBN.equalsIgnoreCase(commonParam
                        .getTerminalApplication())
                && VipConstants.TERMINAL_BRAND_CIBN.equalsIgnoreCase(commonParam.getTerminalBrand())) {
            av = VipTpConstant.BROADCAST_APK_VERSION_CIBN;
        }
        if (paymentChannel == null || paymentChannel == 0) {
            if (StringUtil.isBlank(displayName)) {
                displayName = commonParam.getUsername();
            }
            return vipConsumptionService.purchaseProductNoPaymentChannel(productid, price,
                    activityIds, couponCode, purchaseType, av, position, productName, callBackUrl, displayName,
                    commonParam);
        } else {
            Integer subend = VipUtil.getSubend(commonParam.getTerminalBrand(), commonParam.getTerminalSeries());
            return vipConsumptionService.purchaseProduct(StringUtils.EMPTY, purchaseType,
                    paymentChannel, StringUtil.toLong(productid), productName, price, activityIds, couponCode, bindid,
                    av, subend, null, null, null, null, VipConstants.APPTYPE_LETV, commonParam);
        }
    }

    /**
     * check coupon status
     * @param couponCode
     *            the coupon number
     * @param types
     *            the coupon apply type
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "查询代金券状态", httpMethod = "GET")
    @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/consume/checkVoucherStatus")
    public Response<VoucherStatusDto> checkVoucherStatus(
            @ApiParam(value = "代金券code", required = true) @RequestParam(value = "couponCode") String couponCode,
            @ApiParam(value = "类型", required = false) @RequestParam(value = "types", required = false) String types,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        if (StringUtils.isBlank(types)) {
            types = VipConstants.VOUCHER_STATUS_DEFAULT_TYPES;
        }

        return vipMetadataService.checkVoucherStatus(couponCode, types, commonParam);
    }

    /**
     * get user agreement for checkout counter
     * @param request
     * @return
     */
    @ApiOperation(value = "获取收银台用户协议", httpMethod = "GET")
    @RequestMapping("/vip/consume/getUserAgreement")
    public Response<String> getUserAgreement(HttpServletRequest request) {
        return vipMetadataService
                .getUserAgreement(LetvUserVipCommonConstant.getLocale(request));
    }

    /**
     * update user agreement for checkout counter
     * @param request
     * @return
     */
    @ApiOperation(value = "修改收银台用户协议", httpMethod = "GET")
    @RequestMapping("/vip/consume/updateUserAggrement")
    public Response<Boolean> updateUserAggrement(HttpServletRequest request) {
        return vipConsumptionService.updateUserAggrement(
                LetvUserVipCommonConstant.getLocalString(request));
    }

    /**
     * 直播鉴权，登录状态下，查看当前用户是否有权播放某一直播；为登录状态下，获取试看信息
     * @return
     */
    @ApiOperation(value = "直播鉴权，登录状态下，查看当前用户是否有权播放某一直播；为登录状态下，获取试看信息", httpMethod = "GET")
    // @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/consume/checkLive")
    public Response<CheckLiveDto> checkLive(
            @ApiParam(value = "直播对象id，对应\"live/liveProjects\"接口中的\"id\"字段，无需拼接\"01\"", required = true) @RequestParam(value = "pid") String pid,
            @ApiParam(value = "场次id，对应\"live/liveProjects\"接口中的\"screenings\"字段", required = true) @RequestParam(value = "liveid") String liveid,
            @ApiParam(value = "streamId", required = false) @RequestParam(value = "streamId", required = false) String streamId,
            @ApiParam(value = "直播是否开始，0--未开始，1--开始，默认值0，传其他值则替换为默认值0", required = false) @RequestParam(value = "isstart", required = false, defaultValue = "0") Integer isstart,
            @ApiParam(value = "Callback接口名，暂不使用", required = false) @RequestParam(value = "callback", required = false) String callback,
            @ApiParam(value = "终端id，默认值4", required = false) @RequestParam(value = "termid", required = false, defaultValue = "4") String termid,
            @ApiParam(value = "直播流频道id", required = false) @RequestParam(value = "selectId", required = false) String selectId,
            @ApiParam(value = "起播码流code", required = false) @RequestParam(value = "streamCode", required = false) String streamCode,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        if (isstart == null || 1 != isstart) {// 不等于1的都置为0
            isstart = 0;
        }
        return vipMetadataService.checkLive(pid, liveid, streamId, isstart, termid, selectId,
                streamCode, commonParam);
    }

    /**
     * 一键支付绑定查询
     * @param username
     * @return Response<CheckOneKeyPayDto>
     */
    @ApiOperation(value = "一键支付绑定查询", httpMethod = "GET")
    @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/metedata/checkOneKeyPay")
    public Response<CheckOneKeyPayDto> checkOneKeyPay(
            @ApiParam(value = "用户名", required = true) @RequestParam(value = "username") String username,
            @ApiParam(value = "支付渠道号", required = false) @RequestParam(value = "paymentChannel", required = false, defaultValue = "39") Integer paymentChannel,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        username = BroadcastConstant.transFromBroadcastUsername(username, commonParam.getBroadcastId());

        return vipMetadataService.checkOneKeyPay(username, paymentChannel, commonParam);
    }

    /**
     * 会员消费，购买套餐或单片； 生成支付宝、微信、拉卡拉支付二维码或乐点支付；暂不支持手机支付；
     * 当corderid为0时，本接口将进行收银台下单，返回订单号，
     * @param username
     * @param corderid
     *            订单号，如果有值且不是“0”，表示已经下单，这里直接生成二维码或乐点支付，否则需要本次接口调用中下单并返回订单号
     * @param purchaseType
     *            消费类型，1--影片单点，2--套餐，3--直播单点
     * @param productid
     *            套餐或单片id
     * @param paymentChannel
     *            支付渠道，如5--支付宝，13--国广支付宝，24--微信，33--乐点等
     * @param price
     *            套餐或单片价格，单位-元
     * @param bsChannel
     *            直播SDK传参
     * @param device_id
     *            华数透传参数
     * @param site_id
     *            华数透传参数
     * @param tv_id
     *            华数透传参数
     * @return
     */
    @ApiOperation(value = "会员消费，购买套餐或单片； 生成支付宝、微信、拉卡拉支付二维码或乐点支付；暂不支持手机支付；", httpMethod = "GET")
    @RequestMapping("/vip/tpsdk/purchaseVip")
    public Response<PurchaseVipDto> tpsdkPurchaseVip(
            @ApiParam(value = "用户名", required = false) @RequestParam(value = "username", required = false) String username,
            @ApiParam(value = "订单号", required = false) @RequestParam(value = "corderid", required = false, defaultValue = "0") String corderid,
            @ApiParam(value = "消费类型，1--影片单点，2--套餐，3--直播单点", required = true) @RequestParam(value = "purchaseType") Integer purchaseType,
            @ApiParam(value = "套餐或单片id", required = true) @RequestParam(value = "productid") Long productid,
            @ApiParam(value = "支付渠道，如5--支付宝，13--国广支付宝，24--微信，33--乐点等", required = true) @RequestParam(value = "paymentChannel") Integer paymentChannel,
            @ApiParam(value = "套餐或单片价格，单位-元", required = false) @RequestParam(value = "price", required = false) String price,
            @ApiParam(value = "单片或套餐名称", required = false) @RequestParam(value = "productName", required = false) String productName,
            @ApiParam(value = "直播SDK传参", required = true) @RequestParam(value = "bsChannel") String bsChannel,
            @ApiParam(value = "华数透传参数", required = false) @RequestParam(value = "device_id", required = false) String device_id,
            @ApiParam(value = "华数透传参数", required = false) @RequestParam(value = "site_id", required = false) String site_id,
            @ApiParam(value = "华数透传参数", required = false) @RequestParam(value = "tv_id", required = false) String tv_id,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        username = BroadcastConstant.transFromBroadcastUsername(username, commonParam.getBroadcastId());
        // 直播SDK，海信合作需求
        Integer av = VipTpConstant.BROADCAST_APK_VERSION_LETV;// 默认letv
        Integer subend = VipTpConstant.SUB_ORDER_FROM_DEFAULT;
        if (TerminalCommonConstant.TERMINAL_APPLICATION_TPSDK.equalsIgnoreCase(commonParam.getTerminalApplication())) {
            av = VipTpConstant.BROADCAST_APK_VERSION_TPSDK;
            // bsChannel 是sdk合作厂商
            subend = VipUtil.getSubend(bsChannel, null);
        }
        return vipConsumptionService.purchaseProduct(StringUtils.EMPTY, purchaseType,
                paymentChannel, productid, productName, price, null, null, null, av, subend, device_id, site_id, tv_id,
                null, VipConstants.APPTYPE_LETV_LIVE_SDK, commonParam);
    }

    /**
     * 直播SDK鉴权，查看当前设备（MAC）是否有权播放某一直播
     * @param pid
     *            对应"live/liveProjects"接口中的"id"字段，无需拼接"01"
     * @param liveid
     *            场次id，对应"live/liveProjects"接口中的"screenings"字段
     * @param splatId
     *            子平台ID，默认1007
     * @param streamId
     *            流Id
     * @param isstart
     *            直播是否开始，0--未开始，1--开始，默认值0，传其他值则替换为默认值0
     * @param callback
     *            Callback接口名，暂不使用
     * @return
     */
    @ApiOperation(value = "直播SDK鉴权，查看当前设备（MAC）是否有权播放某一直播", httpMethod = "GET")
    @RequestMapping("/vip/tpsdk/checkLive")
    public Response<CheckLiveDto> tpsdkCheckLive(
            @ApiParam(value = "用户名", required = false) @RequestParam(value = "username", required = false) String username,
            @ApiParam(value = "对应\"live/liveProjects\"接口中的\"id\"字段，无需拼接\"01\"", required = true) @RequestParam(value = "pid") String pid,
            @ApiParam(value = "场次id，对应\"live/liveProjects\"接口中的\"screenings\"字段", required = true) @RequestParam(value = "liveid") String liveid,
            @ApiParam(value = "子平台ID，默认1007", required = true) @RequestParam(value = "splatId", defaultValue = "1007") String splatId,
            @ApiParam(value = "流Id", required = false) @RequestParam(value = "streamId", required = false) String streamId,
            @ApiParam(value = "直播是否开始，0--未开始，1--开始，默认值0，传其他值则替换为默认值0", required = false) @RequestParam(value = "isstart", required = false, defaultValue = "0") Integer isstart,
            @ApiParam(value = "Callback接口名，暂不使用", required = false) @RequestParam(value = "callback", required = false) String callback,
            @ApiParam(value = "直播SDK传参", required = false) @RequestParam(value = "bsChannel", required = false) String bsChannel,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        if (isstart == null || 1 != isstart) {// 不等于1的都置为0
            isstart = 0;
        }

        return vipMetadataService.tpsdkCheckLive(username, pid, liveid, splatId, streamId,
                isstart, bsChannel, commonParam);
    }

    /**
     * 直播SDK--查询订单信息
     * @param orderId
     * @return
     */
    @ApiOperation(value = "直播SDK--查询订单信息", httpMethod = "GET")
    @RequestMapping("/vip/tpsdk/checkOrder")
    public Response<OrderStatusDto> tpsdkCheckOrderStatus(
            @ApiParam(value = "订单ID", required = true) @RequestParam("orderId") String orderId,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {

        return vipMetadataService.tpsdkCheckOrderStatus(orderId, commonParam);
    }

    /**
     * 领取机卡绑定会员，兼容领取老机卡和赠送机卡
     * @param username
     * @param deviceKey
     * @param deviceType
     *            设备类型，电视固定传1，盒子固定传3
     * @param type
     *            1--领取自带机卡绑定。2--领取赠送机卡绑定，默认为2
     * @param presentId
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "领取机卡绑定会员，兼容领取老机卡和赠送机卡", httpMethod = "GET")
    @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/devicebind/receiveDeviceBind")
    public Response<ReceiveDeviceBindDto> receiveDeviceBind(
            @ApiParam(value = "用户名", required = true) @RequestParam(value = "username") String username,
            @ApiParam(value = "设备码，设备唯一标识", required = false) @RequestParam(value = "deviceKey", required = false) String deviceKey,
            @ApiParam(value = "设备类型，电视固定传1，盒子固定传3", required = false) @RequestParam(value = "deviceType", required = false, defaultValue = "1") Integer deviceType,
            @ApiParam(value = "1--领取自带机卡绑定。2--领取赠送机卡绑定，默认为2", required = false) @RequestParam(value = "type", required = false, defaultValue = "2") Integer type,
            @ApiParam(value = "待领取设备时长的id", required = false) @RequestParam(value = "presentId", required = false) String presentId,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        if (type == null) {
            type = VipConstants.DEVICE_BIND_RECEIVE_TYPE_2;
        }

        return vipConsumptionService.receiveDeviceBind(username, deviceKey, deviceType, type,
                presentId, commonParam);
    }

    /**
     * 获取账户的会员有效期
     * @return
     */
    @ApiOperation(value = "获取账户的会员有效期", httpMethod = "GET")
    @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/getAccount")
    public Response<UserAccountDto> getVipAccount(
            @ApiParam(value = "机卡绑定设备的掩码", required = false) @RequestParam(value = "deviceKey", required = false) String deviceKey,
            @ApiParam(value = "设备类型，--电视，2--手机，3--盒子，目前TV版仅支持1,3", required = false) @RequestParam(value = "deviceType", required = false, defaultValue = "1") Integer deviceType,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipMetadataService.getVipAccount(deviceKey, deviceType, true, commonParam);
    }

    /**
     * 获取会员桌面 用户信息接口
     * @return
     */
    @ApiOperation(value = "获取会员桌面 用户信息接口", httpMethod = "GET")
    @RequestMapping("/vip/getVipMemberInfo")
    public Response<BaseData> getVipMemberInfo(
            @ApiParam(value = "设备类型，1--电视，2--手机，3--盒子，目前TV版仅支持1,3", required = false) @RequestParam(value = "deviceType", required = false, defaultValue = "1") Integer deviceType,
            @ApiParam(value = "机卡绑定查询类型，0--查询所有（本机机卡和赠送机卡），1--本机机卡，2--赠送机卡", required = false) @RequestParam(value = "type", required = false) String type,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        if (deviceType == null) {// default tv terminal
            deviceType = VipTpConstant.DEVICE_BIND_DEVICE_TYPE_TV;
        }
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LETV_US.equals(commonParam.getTerminalApplication())
                || StringUtils.isBlank(type)) {
            // us application only get current machine bind info or
            // if type is blank then query current machine bing info
            type = VipConstants.DEVICE_BIND_QUERY_TYPE_1;
        }
        return vipMetadataService.memberDeskInfo(commonParam.getDeviceKey(), deviceType, type,
                commonParam);
    }

    /**
     * 获取会员桌面 活动信息接口
     * @return
     */
    @ApiOperation(value = "获取会员桌面 活动信息接口", httpMethod = "GET")
    @RequestMapping("/vip/getVipMemberUrmActivity")
    public Response<BaseData> getVipMemberUrmActivity(
            @ApiParam(value = "设备类型，1--电视，2--手机，3--盒子，目前TV版仅支持1,3", required = false) @RequestParam(value = "deviceType", required = false, defaultValue = "1") Integer deviceType,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipMetadataService.memberDeskUrmActivity(deviceType, commonParam);
    }

    /**
     * 查询绑卡信息
     * @param userid
     *            用户id
     * @param request
     * @return
     */
    @ApiOperation(value = "查询绑卡信息", httpMethod = "GET")
    @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/onekeypay/bindcard/get")
    public PageResponse<BandInfoDto> getBindInfo(
            @ApiParam(value = "用户ID", required = true) @RequestParam(value = "userid") String userid,
            @ApiParam(value = "用户ID", required = false) @RequestParam(value = "userId", required = false) String userId,
            @ApiParam(value = "request请求参数", required = true) HttpServletRequest request) {
        if (StringUtils.isBlank(userId)) {
            userId = userid;
        }
        GetBindInfoRequest getBindInfoRequest = new GetBindInfoRequest(userId);
        return vipMetadataService.getBindInfo(getBindInfoRequest,
                LetvUserVipCommonConstant.getLocale(request));
    }

    /**
     * 获取收银台相关信息，目前包含套餐包（套餐基本信息，套餐活动，支付渠道活动），会员权益文案，收银台焦点图 直播SDK也会走这个接口，所以不加登录校验

     * @param purchaseType
     *            消费类型，1--影片单点，2--套餐，3--直播单点
     * @param packagePriority
     *            套餐包优先参数，针对一些投放活动，希望进入收银台后某个套餐放在第一个位置上
     * @param channelPriority
     *            支付渠道优先参数，针对一些投放活动，希望进入收银台后某个支付渠道放在第一个位置上
     * @return
     */
    @ApiOperation(value = "获取收银台相关信息，目前包含套餐包（套餐基本信息，套餐活动，支付渠道活动），会员权益文案，收银台焦点图 直播SDK也会走这个接口，所以不加登录校验", httpMethod = "GET")
    @RequestMapping("/vip/pay/getCheckoutCounter")
    public Response<CheckoutCounterDto> getCheckoutCounter(
            @ApiParam(value = "套餐包优先参数，针对一些投放活动，希望进入收银台后某个套餐放在第一个位置上", required = false) @RequestParam(value = "packagePriority", required = false) String packagePriority,
            @ApiParam(value = "支付渠道优先参数，针对一些投放活动，希望进入收银台后某个支付渠道放在第一个位置上", required = false) @RequestParam(value = "channelPriority", required = false) String channelPriority,
            @ApiParam(value = "消费类型，1--影片单点，2--套餐，3--直播单点", required = true) @RequestParam(value = "purchaseType") Integer purchaseType,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        Integer av = VipTpConstant.BROADCAST_APK_VERSION_LETV;// 默认letv
        Integer subend = VipTpConstant.SUB_ORDER_FROM_DEFAULT;
        if (TerminalCommonConstant.TERMINAL_APPLICATION_TPSDK.equalsIgnoreCase(commonParam.getTerminalApplication())) {
            av = VipTpConstant.BROADCAST_APK_VERSION_TPSDK;
            // bsChannel 是sdk合作厂商
            subend = VipUtil.getSubend(commonParam.getBsChannel(), null);
        }
        // VipTpConstant.SVIP_TV表示TV版收银台，展示的都是全屏影视会员套餐
        return vipMetadataService.getCheckoutCounter(purchaseType, packagePriority,
                channelPriority, VipTpConstant.SVIP_TV, av, subend, commonParam);
    }

    /**
     * 获取验证码和提交验证码确认支付
     * @param ordernumber
     *            支付平台流水
     * @param operType
     *            操作类型
     * @param smscode
     *            短信验证码
     * @return
     */
    @ApiOperation(value = "获取验证码和提交验证码确认支付", httpMethod = "GET")
    @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/consume/yeepay/verificationCode")
    public Response<VerificationCodeResultDto> yeepayVerificationCode(
            @ApiParam(value = "支付平台流水", required = true) @RequestParam(value = "ordernumber") String ordernumber,
            @ApiParam(value = "操作类型", required = true) @RequestParam(value = "operType") Integer operType,
            @ApiParam(value = "短信验证码", required = false) @RequestParam(value = "smscode", required = false) String smscode,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {

        return vipMetadataService.yeepayVerificationCode(ordernumber, operType, smscode,
                commonParam);
    }

    /**
     * get activity by user info
     * @param supportNew
     * @param deviceType
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "获取URM活动信息", httpMethod = "GET")
    @RequestMapping("/vip/consume/getPilot")
    public Response<BaseData> getPilot(
            @ApiParam(value = "传0", required = false) @RequestParam(value = "supportNew", required = false) Integer supportNew,
            @ApiParam(value = "设备类型", required = false) @RequestParam(value = "deviceType", required = false, defaultValue = "1") Integer deviceType,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipMetadataService.getPilotV2(supportNew, deviceType, commonParam);
    }

    /**
     * get activity by user info
     * @param supportNew
     * @param deviceType
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "获取URM活动信息", httpMethod = "GET")
    @Deprecated
    @RequestMapping("/consume/getPilot")
    public Response<BaseData> getPilotErrVersion(
            @ApiParam(value = "传0", required = false) @RequestParam(value = "supportNew", required = false) Integer supportNew,
            @ApiParam(value = "设备类型", required = false) @RequestParam(value = "deviceType", required = false, defaultValue = "1") Integer deviceType,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) throws IOException {
        return vipMetadataService.getPilotV2(supportNew, deviceType, commonParam);
    }

    /**
     * check letvcard if can use
     * @param cardSecret
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "乐卡校验", httpMethod = "GET")
    @RequestMapping(value = "/vip/consume/isCardSecretExisting", method = RequestMethod.GET)
    public Response<CardSecretValidateDto> isCardSecretExisting(
            @ApiParam(value = "卡号", required = true) @RequestParam("cardSecret") String cardSecret,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipConsumptionService.isCardSecretExisting(cardSecret, commonParam);
    }

    /**
     * recharge by letvcard
     * @param cardSecret
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "使用乐卡充值", httpMethod = "GET")
    @CheckLoginInterceptorAnnotation
    @RequestMapping(value = "/vip/consume/rechargeByLetvCardPasswd")
    public Response<LetvCardRechargeDto> rechargeByLetvCardPasswd(
            @ApiParam(value = "卡号", required = true) @RequestParam("cardSecret") String cardSecret,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipConsumptionService.rechargeByLetvCardPasswd(cardSecret, commonParam);
    }

    /**
     * letvcard recharge letvpoint and exchange service
     * @param amount
     * @param cardSecret
     * @param operationType
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "letvcard recharge letvpoint and exchange service", httpMethod = "GET")
    @CheckLoginInterceptorAnnotation
    @RequestMapping(value = "/vip/consume/letvcard/recharge")
    public Response<LetvCardRechargeDto> rechargeOrExchangeByLetvCardNew(
            @ApiParam(value = "卡号", required = false) @RequestParam(value = "amount", required = false) Integer amount,
            @ApiParam(value = "最大兑换乐点数", required = false) @RequestParam(value = "cardSecret", required = false) String cardSecret,
            @ApiParam(value = "操作类型.(兑换，充值等)", required = false) @RequestParam(value = "operationType", required = false, defaultValue = "1") Integer operationType,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipConsumptionService.rechargeByLetvCardPasswdNew(amount, cardSecret,
                operationType, commonParam, null);
    }

    /**
     * 开机激活页使用充值卡兑换会员；仅支持充值卡，不支持兑换卡等； 盒端登录或注册、充值、购买套餐合并接口 1、盒端给手机号、密码，直接登录或注册
     * 2、根据卡密给用户充值 3、获取包年订单号 4、给用户购买包年套餐； 2014-10-15新增CIBN需求； 经继续维护只2016-02-29
     * @param loginName
     *            有可能是手机号
     * @param password
     * @param cardSecret
     * @return
     */
    @ApiOperation(value = "开机激活页使用充值卡兑换会员", httpMethod = "GET", notes = "仅支持充值卡，不支持兑换卡等； 盒端登录或注册、充值、购买套餐合并接口 1、盒端给手机号、密码，直接登录或注册\n"
            + "     * 2、根据卡密给用户充值 3、获取包年订单号 4、给用户购买包年套餐； 2014-10-15新增CIBN需求； 经继续维护只2016-02-29")
    @HttpParameterInterceptorAnnotation
    @RequestMapping(value = "/v2/consume/consumeByLetvCard", method = RequestMethod.GET)
    public Response<LetvCardActiveDto> consumeByLetvCard(
            @ApiParam(value = "登录名", required = true) @RequestParam("loginName") String loginName,
            @ApiParam(value = "传0", required = false) @RequestParam(value = "amount", required = false) Integer amount,
            @ApiParam(value = "登录密码", required = false) @RequestParam(value = "password", required = false) String password,
            @ApiParam(value = "短信验证码", required = false) @RequestParam(value = "userAuthCode", required = false) String userAuthCode,
            @ApiParam(value = "卡号", required = true) @RequestParam("cardSecret") String cardSecret,
            @ApiParam(value = "传空", required = false) @RequestParam(value = "mac", required = false) String mac,
            @ApiParam(value = "渠道标识", required = false) @RequestParam(value = "channel", required = false) String channel,
            HttpServletRequest httpRequest,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam,
            HttpServletRequest request) {
        loginName = BroadcastConstant.transFromBroadcastUsername(loginName, commonParam.getBroadcastId());

        Integer isCIBN = 0; // 盒端操作是否来自CIBN
        Integer av = 0; // 支付中心所需标识，是否是CIBN消费
        if (commonParam.getBroadcastId() != null && 2 == commonParam.getBroadcastId()) {
            isCIBN = 1;
            av = 1;
        }
        // String clientIp = HttpUtil.getIP(httpRequest);
        return vipConsumptionService.consumeByLetvCardOld(loginName, cardSecret, password,
                channel, userAuthCode, isCIBN, av, commonParam);
    }

    /**
     * 新增接口，保留老接口，避免出现404错误，因为目前日志转到的是consumeByCard，但以前的代码是consumeByLetvCard
     */
    @ApiOperation(value = "新增接口，保留老接口，避免出现404错误，因为目前日志转到的是consumeByCard，但以前的代码是consumeByLetvCard", httpMethod = "GET")
    @RequestMapping(value = "/v2/consume/consumeByCard")
    public Response<LetvCardActiveDto> consumeByCard(
            @ApiParam(value = "登录名", required = true) @RequestParam("loginName") String loginName,
            @ApiParam(value = "登录密码", required = false) @RequestParam(value = "password", required = false) String password,
            @ApiParam(value = "短信验证码", required = false) @RequestParam(value = "userAuthCode", required = false) String userAuthCode,
            @ApiParam(value = "卡号", required = true) @RequestParam("cardSecret") String cardSecret,
            @ApiParam(value = "传空", required = false) @RequestParam(value = "mac", required = false) String mac,
            @ApiParam(value = "渠道标识", required = false) @RequestParam(value = "channel", required = false) String channel,
            HttpServletRequest httpRequest,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam,
            HttpServletRequest request) {
        loginName = BroadcastConstant.transFromBroadcastUsername(loginName, commonParam.getBroadcastId());

        Integer isCIBN = 0; // 盒端操作是否来自CIBN
        Integer av = 0; // 支付中心所需标识，是否是CIBN消费
        if (commonParam.getBroadcastId() != null && 2 == commonParam.getBroadcastId()) {
            isCIBN = 1;
            av = 1;
        }
        return vipConsumptionService.consumeByLetvCardOld(loginName, cardSecret, password,
                channel, userAuthCode, isCIBN, av, commonParam);
    }

    /**
     * @param productId
     *            It can be vippackage Id albumId or liveId
     * @param activityIds
     *            Some discount activity
     * @param position
     *            The entry position parameter，it use to distinguish the
     *            checkout counter type.
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "新获取收银台相关信息", httpMethod = "GET")
    @RequestMapping(value = "/vip/pay/getCheckoutCounterNew")
    public Response<CheckOutCounter> getCheckoutCounterNew(
            @ApiParam(value = "套餐ID,专辑ID，直播ID", required = false) @RequestParam(value = "productId", required = false) String productId,
            @ApiParam(value = "活动ID", required = false) @RequestParam(value = "activityIds", required = false) String activityIds,
            @ApiParam(value = "入口位置参数，用于计数", required = false) @RequestParam(value = "position", required = false) String position,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipMetadataService.getCheckoutCounterNew(productId, activityIds, position,
                commonParam);
    }

    /**
     * 会员内容包鉴权
     * @param terminal
     *            终端，见{@link VipTpConstant.BossTerminalType}
     * @param productId
     *            会员id
     * @param packageId
     *            套餐id
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "会员内容包鉴权", httpMethod = "GET")
    @RequestMapping(value = "/vip/pay/validate")
    public Response<VipValidateDto> validate(
            @ApiParam(value = "终端ID", required = false) @RequestParam(value = "terminal", required = false) Integer terminal,
            @ApiParam(value = "套餐ID", required = false) @RequestParam(value = "packageId", required = false) Integer packageId,
            @ApiParam(value = "会员id", required = false) @RequestParam(value = "productId", required = false) Integer productId,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {

        if (null != commonParam && null != commonParam.getP_devType()) {
            terminal = Integer.parseInt(MmsDataUtil.getPayPlatform(String.valueOf(commonParam.getP_devType())));
        }

        if (terminal == null || terminal <= 0) { // 默认tv
            terminal = VipTpConstant.BossTerminalType.TV.getCode();
        }

        return vipMetadataService.validate(productId, packageId, terminal, commonParam);
    }

    /**
     * get activity info of member IOUs
     * @param activeScene
     *            from where to participate activity
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "get activity info of member IOUs", httpMethod = "GET")
    @RequestMapping(value = "/vip/freevip/get")
    public Response<BaseData> getFreeVipInfo(
            @ApiParam(value = "入口标识", required = false) @RequestParam(value = "activeScene", required = false) Integer activeScene,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipMetadataService.getFreeVipInfo(activeScene, commonParam);
    }

    /**
     * active member IOUs service
     * @param activeScene
     *            from where to participate activity
     * @param activeType
     *            active type,1--active,2--get payment info
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "active member IOUs service", httpMethod = "GET")
    @RequestMapping(value = "/vip/freevip/active")
    public Response<BaseData> activeFreeVip(
            @ApiParam(value = "入口标识", required = false) @RequestParam(value = "activeScene", required = false) Integer activeScene,
            @ApiParam(value = "1--激活,2--获取还款信息", required = false) @RequestParam(value = "activeType", required = false) Integer activeType,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipMetadataService.activeFreeVip(activeType, activeScene, commonParam);
    }

    /**
     * 获取代金券列表
     * @param status
     *            查询某一状态代金券：状态 unused：未使用；used：已使用；expire： 过期。默认值unused。
     * @param type
     *            代金券适用类型，0/null：所有类型；1：移动会员；2：购买套餐；3：购买专辑；4：购买直播。
     * @param page
     *            分页数据
     * @param pageSize
     *            分页数据
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "获取代金券列表(VIP)", httpMethod = "GET")
    @RequestMapping(value = "/vip/voucher/get")
    public Response<List<VoucherDto>> getUserVoucher(
            @ApiParam(value = "查询某一状态代金券：状态 unused：未使用；used：已使用；expire： 过期。默认值unused", required = false) @RequestParam(value = "status", required = false, defaultValue = "unused") String status,
            @ApiParam(value = "代金券适用类型，0/null：所有类型；1：移动会员；2：购买套餐；3：购买专辑；4：购买直播", required = false) @RequestParam(value = "type", required = false, defaultValue = "2") Integer type,
            @ApiParam(value = "页数", required = false) @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false, defaultValue = "60") Integer pageSize,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        if (type == null) {// now only the checkout counter use voucher
            type = 2;
        }
        return vipMetadataService.getUserVoucher(type, status, page, pageSize, commonParam);
    }

    /**
     * 获取代金券列表V2
     * @param status
     *            查询某一状态代金券：状态 unused：未使用；used：已使用；expire： 过期。默认值unused。
     * @param type
     *            代金券适用类型，0/null：所有类型；1：移动会员；2：购买套餐；3：购买专辑；4：购买直播。
     * @param page
     *            分页数据
     * @param pageSize
     *            分页数据
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "获取代金券列表V2", httpMethod = "GET")
    @RequestMapping(value = "/vip/v2/voucher/get")
    public Response<List<VoucherDto>> getUserVoucherV2(
            @ApiParam(value = "查询某一状态代金券：状态 unused：未使用；used：已使用；expire： 过期。默认值unused", required = false) @RequestParam(value = "status", required = false, defaultValue = "unused") String status,
            @ApiParam(value = "代金券适用类型，0/null：所有类型；1：移动会员；2：购买套餐；3：购买专辑；4：购买直播。", required = false) @RequestParam(value = "type", required = false, defaultValue = "2") Integer type,
            @ApiParam(value = "页数", required = false) @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam(value = "每页数量", required = false) @RequestParam(value = "pageSize", required = false, defaultValue = "60") Integer pageSize,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        if (type == null) {// now only the checkout counter use voucher
            type = 2;
        }
        return vipV2Service.getUserVoucherV2(type, status, page, pageSize, commonParam);
    }
}
