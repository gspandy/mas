package com.letv.mas.caller.iptv.tvproxy.vip.controller;

import com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor.CheckLoginInterceptorAnnotation;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageCommonResponse;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.controller.BaseController;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.BaseResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.vip.VipConstants;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.vip.service.VipMetadataService;
import com.letv.mas.caller.iptv.tvproxy.vip.service.VipPayService;
import com.letv.mas.caller.iptv.tvproxy.vip.service.VipV2Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Api(value = "/iptv/api/new/vip", description = "会员相关")
@RestController("v2.vipControllerV2")
public class VipControllerV2 extends BaseController {

    @Autowired
    VipPayService vipPayService;
    
    @Autowired
    VipV2Service vipV2Service;
    
    @Autowired
    VipMetadataService vipMetadataService;
    
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
    @ApiOperation(value = "新收银台接口(收银台获取套餐列表)", httpMethod = "GET")
    @RequestMapping(value = "/vip/v2/pay/getCheckoutCounterNew")
    public Response<CheckOutCounter> getCheckoutCounterNew(
            @ApiParam(value = "套餐id。如果有值，则收银台只展示该套餐", required = false) @RequestParam(value = "productId", required = false) String productId,
            @ApiParam(value = "BOSS活动id，配合套餐id使用。如果有值，则表示用户可以参加该活动", required = false) @RequestParam(value = "activityIds", required = false) String activityIds,
            @ApiParam(value = "触达位信息，非触达位则传空", required = false) @RequestParam(value = "position", required = false) String position,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipPayService.getCheckoutCounterNewV2(productId, activityIds, position,
                commonParam);
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
     * @param cpsId
     *            推广位ID，大数据埋点，由活动方产品提供之
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "下单接口v2", httpMethod = "GET")
    @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/v2/pay/purchaseVip")
    public Response<PurchaseVipDto> purchaseVip(
            @ApiParam(value = "订单id", required = false) @RequestParam(value = "corderid", required = false, defaultValue = "0") String corderid,
            @ApiParam(value = "商品类型，参考通用参数:productType", required = true) @RequestParam(value = "purchaseType") Integer purchaseType,
            @ApiParam(value = "商品id, 为对应的专辑id或视频id", required = true) @RequestParam(value = "productid") String productid,
            @ApiParam(value = "支付通道，已废弃", required = false) @RequestParam(value = "paymentChannel", required = false) Integer paymentChannel,
            @ApiParam(value = "价格", required = false) @RequestParam(value = "price", required = false) String price,
            @ApiParam(value = "商品名称", required = false) @RequestParam(value = "productName", required = false) String productName,
            @ApiParam(value = "活动id，TBD", required = false) @RequestParam(value = "activityIds", required = false) String activityIds,
            @ApiParam(value = "优惠券号码", required = false) @RequestParam(value = "couponCode", required = false) String couponCode,
            @ApiParam(value = "已废弃", required = false) @RequestParam(value = "bindid", required = false) String bindid,
            @ApiParam(value = "已废弃", required = false) @RequestParam(value = "device_id", required = false) String device_id,
            @ApiParam(value = "已废弃", required = false) @RequestParam(value = "site_id", required = false) String site_id,
            @ApiParam(value = "已废弃", required = false) @RequestParam(value = "tv_id", required = false) String tv_id,
            @ApiParam(value = "已废弃", required = false) @RequestParam(value = "position", required = false) String position,
            @ApiParam(value = "回跳地址", required = false) @RequestParam(value = "call_back_url", required = false) String callBackUrl,
            @ApiParam(value = "用户名", required = false) @RequestParam(value = "displayName", required = false) String displayName,
            @ApiParam(value = "推广位ID，大数据埋点，由活动方产品提供", required = false) @RequestParam(value = "cpsId", required = false) String cpsId,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        Integer av = VipTpConstant.BROADCAST_APK_VERSION_LETV; // 默认letv
        if (commonParam.getBroadcastId() != null
                && VipConstants.BROADCAST_CIBN == commonParam.getBroadcastId()
                && TerminalCommonConstant.TERMINAL_APPLICATION_CIBN.equalsIgnoreCase(commonParam
                        .getTerminalApplication())
                && VipConstants.TERMINAL_BRAND_CIBN.equalsIgnoreCase(commonParam.getTerminalBrand())) {
            av = VipTpConstant.BROADCAST_APK_VERSION_CIBN;
        }
        return vipPayService.purchaseProductV2(productid, activityIds, cpsId, couponCode,
                purchaseType, av, position, productName, callBackUrl, displayName, commonParam);
    }

    /**
     * get bind info of machine and the users
     * @param deviceType
     *            terminal type,1--tv terminal,2--mobile terminal, 3--letv box
     *            terminal
     * @param type
     *            query type,0--all bind info,1--current machine bind info,
     *            2--present to current user bind info
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "查询设备的机卡信息原http://wiki.letv.cn/pages/viewpage.action?pageId=65261507", httpMethod = "GET")
    @RequestMapping("/vip/v2/devicebind/getDeviceBindInfo")
    public Response<DeviceBindDto> getDeviceBindInfo(
            @ApiParam(value = "设备类型1--tv terminal,2--mobile terminal, 3--letv box terminal", required = true) @RequestParam(value = "deviceType") Integer deviceType,
            @ApiParam(value = "查询类型0--all bind info,1--current machine bind info 2--present to current user bind info", required = true) @RequestParam(value = "type") Integer type,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {

        if (TerminalCommonConstant.TERMINAL_APPLICATION_LETV_US.equals(commonParam.getTerminalApplication())) {
            // us application only get current machine bind info or
            // if type is blank then query current machine bing info
            type = VipConstants.DEVICE_BIND_QUERY_INT_TYPE_1;
        }
        return vipV2Service.getDeviceBindInfo(type, deviceType, commonParam);
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
    @ApiOperation(value = "获取用户消费记录(V2)", httpMethod = "GET")
    @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/v2/consume/getConsumptionRecord")
    public PageCommonResponse<ConsumptionRecordDto> getConsumptionRecordV2(
            @ApiParam(value = "订单状态,0--all,1--pay failure,2--pay success,3--expired", required = true) @RequestParam("status") Integer status,
            @ApiParam(value = "查询日期以内消费记录", required = true) @RequestParam("day") Integer day,
            @ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
            @ApiParam(value = "每页数量", required = true) @RequestParam("pageSize") Integer pageSize,
            @ApiParam(value = "订单类型", required = false) @RequestParam(value = "orderTypes", required = false) String orderTypes,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        List<String> orderTypeList = new LinkedList<String>();
        if (StringUtils.isNotBlank(orderTypes)) {// 先解析要查询的订单类型
            String[] typeArray = orderTypes.trim().split(",");
            orderTypeList = Arrays.asList(typeArray);
        }
        Map<Integer, Integer> statusCode = new HashMap<Integer, Integer>();
        statusCode.put(0, 1);
        return vipMetadataService.getConsumptionRecordV2(statusCode.get(status), day, page,
                pageSize, orderTypeList, commonParam);
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
    @ApiOperation(value = "获取用户消费记录(V3)", httpMethod = "GET")
    @CheckLoginInterceptorAnnotation
    @RequestMapping("/vip/v3/consume/getConsumptionRecord")
    public PageCommonResponse<ConsumptionRecordDto2> getConsumptionRecordV3(
            @ApiParam(value = "order status,0--all,1--pay failure,2--pay success,3--expired", required = true) @RequestParam("status") Integer status,
            @ApiParam(value = "查询日期以内消费记录", required = false) @RequestParam("day") Integer day,
            @ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
            @ApiParam(value = "每页数量", required = true) @RequestParam("pageSize") Integer pageSize,
            @ApiParam(value = "订单类型(1:全部订单2:赠品订单3:连续包月订单4:腾讯家庭会员订单)", required = false) @RequestParam(value = "orderTypes", required = false) String orderTypes,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {

        String orderType = "";
        List<String> orderTypeList = new LinkedList<String>();
        if (StringUtils.isNotBlank(orderTypes) && orderTypes != "0") {// 先解析要查询的订单类型
            String[] typeArray = orderTypes.trim().split(",");
            // orderTypeList = Arrays.asList(typeArray);
            Map<String, String> orderTypeCodes = new HashMap<String, String>();
            // 1:全部订单.2:赠品订单.3:超级影视连续包月.4:腾讯家庭会员订单
            orderTypeCodes.put("1", "PRO");
            orderTypeCodes.put("2", "PRO_ACT");
            orderTypeCodes.put("3", "PRO_RENEW");
            orderTypeCodes.put("4", "TEN_FAM");
            String commaSplit = "";
            for (int i = 0; i < typeArray.length; i++) {
                if (i > 0) {
                    commaSplit = ",";
                }
                orderType += commaSplit + orderTypeCodes.get(typeArray[i]);
            }
        } else {
            orderType = "PRO";
        }
        Map<Integer, Integer> statusCode = new HashMap<Integer, Integer>();
        statusCode.put(0, 1);
        return vipMetadataService.getConsumptionRecordV3(statusCode.get(status), day, page,
                pageSize, orderType, commonParam);
    }

    /**
     * get guanxing promotion
     * @param posIds
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "查询观星推广位接口http://wiki.letv.cn/pages/viewpage.action?pageId=67024968", httpMethod = "GET")
    @RequestMapping("/vip/v2/promotion")
    public Response<Map<String,List<PromotionDto>>> getGuanXingPromotion(
            @ApiParam(value = "推广位位置，多个可以逗号拼接.具体参见上面的wiki", required = false) @RequestParam(value = "posIds", required = false) String posIds,
            @ApiParam(value = "专辑ID（当获取 播放器 - 通知 时必传）", required = false) @RequestParam(value = "pid", required = false) String pid,
            @ApiParam(value = "视频ID（当获取 播放器 - 通知 时必传）", required = false) @RequestParam(value = "vid", required = false) String vid,
            @ApiParam(value = "会员过期时间", required = false) @RequestParam(value = "vet", required = false) Long vet,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipV2Service.getGuanXingPromotion(
                posIds, commonParam,pid,vid,vet);
    }

    /**
     * get activity by user info
     * @param supportNew
     * @param deviceType
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "获取推广位活动接口（顶部、下浮层).原wiki:http://wiki.letv.cn/pages/viewpage.action?pageId=67038124", httpMethod = "GET")
    @RequestMapping("/vip/v2/consume/getPilot")
    public Response<BaseData> getPilot(
            @ApiParam(value = "已废弃", required = false) @RequestParam(value = "supportNew", required = false) Integer supportNew,
            @ApiParam(value = "已废弃", required = false) @RequestParam(value = "deviceType", required = false, defaultValue = "1") Integer deviceType,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipV2Service.getPilotV2(supportNew, deviceType, commonParam);
    }

    /*
     * 直播鉴权，登录状态下，查看当前用户是否有权播放某一直播；为登录状态下，获取试看信息
     * @param username
     * @param userid
     * @param pid
     * 对应"live/liveProjects"接口中的"id"字段，无需拼接"01"
     * @param liveid
     * 场次id，对应"live/liveProjects"接口中的"screenings"字段
     * @param splatId
     * 子平台ID，默认1007
     * @param streamId
     * 流Id
     * @param isstart
     * 直播是否开始，0--未开始，1--开始，默认值0，传其他值则替换为默认值0
     * @param callback
     * Callback接口名，暂不使用
     * @param selectId
     * 直播流频道id
     * @param streamCode
     * 起播码流code
     * @return
     */
    // @CheckLoginInterceptorAnnotation
    @ApiOperation(value = "直播鉴权，登录状态下，查看当前用户是否有权播放某一直播；为登录状态下，获取试看信息,原wiki:http://wiki.letv.cn/pages/viewpage.action?pageId=67031249", httpMethod = "GET")
    @RequestMapping("/vip/v2/consume/checkLive")
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
        return vipPayService.checkLiveV2(pid, liveid, streamId, isstart, termid, selectId,
                streamCode, commonParam);
    }

    /**
     * 查询包月服务状态
     * @param commonParam
     *            通用参数
     * @return
     */
    @ApiOperation(value = "查询包月服务状态ststus:0--未开通，1--已开通.expiredDate:到期时间", httpMethod = "GET")
    @RequestMapping("/vip/v2/queryMonthlyPaymentStatus")
    public Response<MonthlyPaymentStatusDto> queryMonthlyPaymentStatus(
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return vipPayService.getMonthlyPaymentStatus(commonParam);
    }

    /**
     * 关闭包月服务接口
     * @param commonParam
     *            通用参数
     * @return
     */
    @ApiOperation(value = "关闭包月服务接口", httpMethod = "GET")
    @RequestMapping("/vip/v2/unsubscribeMonthlyPayment")
    public BaseResponse unsubscribeMonthlyPayment(
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {

        return vipPayService.unsubscribeMonthlyPayment(commonParam);
    }
}
