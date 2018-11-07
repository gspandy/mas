package com.letv.mas.caller.iptv.tvproxy.cashier;

import com.letv.mas.caller.iptv.tvproxy.cashier.model.dto.Agreements;
import com.letv.mas.caller.iptv.tvproxy.cashier.model.dto.Cashier;
import com.letv.mas.caller.iptv.tvproxy.cashier.model.dto.Order;
import com.letv.mas.caller.iptv.tvproxy.common.controller.BaseController;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.ErrorCodeCommonUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.MmsDataUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wangli on 4/24/17.
 * 收银台接口服务：获取收银台信息，生成支付二维码，查询订单状态
 */
@RestController("v1.cashierController")
public class CashierController extends BaseController {

    private final static Logger LOG = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    CashierService cashierService;
    
    /**
     * 获取收银台信息
     * @param cashierId
     *            收银台Id
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "获取收银台信息 (2017.6月-更新)", httpMethod = "GET")
    @RequestMapping(value = "/cashier/v1/cashier")
    public Response<Cashier> getCashier(
            @ApiParam(name = "cashierId", value = "收银台Id") @RequestParam(value = "cashierId", required = false) String cashierId,
            @ModelAttribute CommonParam commonParam) {
        Response<Cashier> response = new Response<>();
        try {
            Cashier cashier = cashierService.getCashier(cashierId, commonParam);
            response.setData(cashier);
        } catch (CashierException e) {
            ErrorCodeCommonUtil.setErrorResponse(response, e.getCode(), commonParam.getLangcode());
            LOG.info("getCashier_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + e.getMessage());
        }
        return response;
    }

    /**
     * 生成支付二维码
     * @param productIds
     *            商品Id列表
     * @param terminalId
     *            终端Id
     * @param activityUrl
     *            活动地址
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "生成支付二维码", httpMethod = "GET")
    @RequestMapping(value = "/cashier/v1/pay/qrcode")
    public Response<List<Order>> getQRCode(@RequestParam(value = "productIds", required = true) String[] productIds,
                                           @RequestParam(value = "terminalId", required = false) String terminalId,
                                           @RequestParam(value = "activityUrl", required = false) String activityUrl,
                                           @RequestParam(value = "paypath", required = false) String payPath, @ModelAttribute CommonParam commonParam) {
        Response<List<Order>> response = new Response<>();
        try {
            if (StringUtil.isBlank(terminalId)) {
                terminalId = MmsDataUtil.getPayPlatform(commonParam.getP_devType());
            }

            List<Order> orders = cashierService.refreshOrdersUrl(productIds,
                    commonParam.getUserId(), commonParam.getToken(), terminalId, commonParam.getClientIp(),
                    activityUrl, payPath);
            response.setData(orders);
        } catch (CashierException e) {
            ErrorCodeCommonUtil.setErrorResponse(response, e.getCode(), commonParam.getLangcode());
            LOG.info("getQRCode_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + e.getMessage());
        }
        return response;
    }

    /**
     * 生成支付二维码
     * @param productId
     *            商品Id
     * @param terminalId
     *            终端Id
     * @param activityUrl
     *            活动地址
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "生成支付二维码(VIP) (2017.6月-更新)", httpMethod = "GET")
    @RequestMapping(value = "/cashier/v1/pay/qrcode/single")
    public Response<Order> getQRCodeSingle(
            @RequestParam(value = "productId") String productId,
            @RequestParam(value = "terminalId", required = false) String terminalId,
            @RequestParam(value = "activityUrl", required = false) String activityUrl,
            @ApiParam(name = "renewal", value = "是否是连续包月(是传true,不是可不传") @RequestParam(value = "renewal", required = false) boolean renewal,
            @RequestParam(value = "paypath", required = false, defaultValue = "") String payPath,
            @ModelAttribute CommonParam commonParam) {
        Response<Order> response = new Response<>();
        try {
            if (StringUtil.isBlank(terminalId)) {
                terminalId = MmsDataUtil.getPayPlatform(commonParam.getP_devType());
            }

            Order order = cashierService.refreshOrderUrl(productId, commonParam.getUserId(),
                    commonParam.getToken(), terminalId, commonParam.getClientIp(), activityUrl, payPath);
            order.setRenewal(Boolean.TRUE.equals(renewal));
            response.setData(order);
        } catch (CashierException e) {
            ErrorCodeCommonUtil.setErrorResponse(response, e.getCode(), commonParam.getLangcode());
            LOG.info("getQRCode_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + e.getMessage());
        }
        return response;
    }

    /**
     * 获取订单状态
     * @param orderId
     *            订单Id
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "获取订单状态 (2017.6月-更新)", httpMethod = "GET", notes = "如果是连续包月订单查询，带上renewal=true，接口会额外返回卡密绑定信息Order.BoundPayment对象<br/>"
            + "该接口包含支付成功状态（参见tradeResult），和开通成功状态（参见valid）")
    @RequestMapping(value = "/cashier/v1/pay/status")
    public Response<Order> getPayStatus(
            @ApiParam(name = "orderId", value = "订单Id", required = true) @RequestParam(value = "orderId") String orderId,
            @ApiParam(name = "renewal", value = "是否是连续包月(是传true,不是可不传") @RequestParam(value = "renewal", required = false) boolean renewal,
            @ModelAttribute CommonParam commonParam) {
        Response<Order> response = new Response<>();
        try {
            Order order = cashierService.checkOrderStatus(orderId, commonParam.getUserId(),
                    commonParam.getToken(), commonParam.getDeviceKey(), commonParam.getMac(), renewal);
            response.setData(order);
        } catch (CashierException e) {
            ErrorCodeCommonUtil.setErrorResponse(response, e.getCode(), commonParam.getLangcode());
            LOG.info("getPayStatus_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + e.getMessage());
        }
        return response;
    }

    /**
     * 一键支付
     * @param cashierProductId
     * @param bind_id
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "一键支付 (2017.6月-新增)", httpMethod = "GET", notes = "resultStatus为1，则视为接口调用成功，支付成功开通成功需要轮询调用/cashier/v1/pay/status接口")
    @RequestMapping(value = "/cashier/v1/quickPurchase")
    public Response<Order> quickPurchase(
            @ApiParam(name = "cashierProductId", value = "商品Id", required = true) @RequestParam(value = "cashierProductId") String cashierProductId,
            @ApiParam(name = "bind_id", value = "代扣签约Id", required = true) @RequestParam(value = "bind_id") String bind_id,
            @ApiParam(name = "terminalId", value = "终端类型") @RequestParam(value = "terminalId", required = false) String terminalId,
            @ModelAttribute CommonParam commonParam) {
        Response<Order> response = new Response<>();
        try {
            if (StringUtil.isBlank(terminalId)) {
                terminalId = MmsDataUtil.getPayPlatform(commonParam.getP_devType());
            }
            Order order = cashierService.generateOrder(cashierProductId,
                    commonParam.getUserId(), commonParam.getToken());
            cashierService.payOrder(order, bind_id, commonParam.getUserId(),
                    commonParam.getUserToken(), terminalId);
            response.setData(order);
        } catch (CashierException e) {
            ErrorCodeCommonUtil.setErrorResponse(response, e.getCode(), commonParam.getLangcode());
            LOG.info("quickPurchase_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + e.getMessage());
        }
        return response;
    }

    /**
     * 会员协议接口
     * @return
     */
    @ApiOperation(value = "会员协议接口 (2017.6月-新增)", httpMethod = "GET", notes = "纯吐传CMS接口http://static.api.cp21.ott.cibntv.net/blockNew/get?id=9947&platform=tv")
    @RequestMapping(value = "/cashier/v1/agreement")
    public Response<Agreements> getAgreement(@ModelAttribute CommonParam commonParam) {
        Response<Agreements> response = new Response<>();
        try {
            Agreements agreements = cashierService.getAgreements(commonParam);
            response.setData(agreements);
        } catch (CashierException e) {
            ErrorCodeCommonUtil.setErrorResponse(response, e.getCode(), commonParam.getLangcode());
            LOG.info("getAgreement" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + e.getMessage());
        }
        return response;
    }
}
