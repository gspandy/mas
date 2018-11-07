package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstantUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * http://yuanxian.letv.com/letv/sale.ldo?type=saleRecord&status={status}&day={
 * day}&userid={userid}&page={page}&pagesize={pagesize}&end={end}&mac={mac}&
 * devicekey={deviceKey}
 */
public class ConsumptionRecordRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 订单状态，0是全部，1待付款，2交易成功，3交易过期
     */
    private Integer status;

    /**
     * 查询day天时间内的充值记录，0是全部，3是最近3天，7是最近7天，30是最近30天等
     */
    private Integer day;

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 每页记录数
     */
    private Integer pagesize;

    /**
     * 终端类型，可以根据终端类型获取消费记录，TV版传9
     */
    private String terminalType = VipTpConstant.ORDER_FROM_TV2;

    /**
     * 设备码，设备唯一标识（“机卡绑定”新增需求）
     */
    private String deviceKey;

    /**
     * 设备MAC地址（“机卡绑定”新增需求）
     */
    private String mac;

    /**
     * 用户中心id
     */
    private Long userid;

    /**
     * 播控方
     */
    private Integer broadcastId;

    /**
     * 要查询的订单类型列表，格式为多个orderType适用英文逗号拼接，为空则表示查询全部
     */
    private String orderTypes;

    /**
     * 解析后的订单类型列表
     */
    private List<String> orderTypeList;

    public ConsumptionRecordRequest() {
        super();
    }

    public ConsumptionRecordRequest(String username, Integer status, Integer day, Integer page, Integer pagesize,
            Long userid) {
        super();
        this.username = username;
        this.status = status;
        this.day = day;
        this.page = page;
        this.pagesize = pagesize;
        this.userid = userid;
    }

    /**
     * 参数校验
     * @return
     *         <h1>TYPE</h1> int
     *         <h1>RESULT</h1>
     *         userid为空，查询订单类型错误，消费记录查询状态不合法，分页信息错误，查询日期范围为错误，校验通过
     */
    public int assertValid() {
        if (this.userid == null || this.userid < 0) {
            return VipMsgCodeConstant.REQUEST_USERINFO_ERROR;
        }
        if (StringUtils.isNotBlank(this.orderTypes)) {
            // types为空格表示查询所有适用范围
            String[] typeArray = this.orderTypes.trim().split(VipTpConstant.COMMON_SPILT_SEPARATOR);
            if (ArrayUtils.isNotEmpty(typeArray)) {
                this.orderTypeList = Arrays.asList(typeArray);
                if (!VipTpConstantUtils.isOrderTypesApplyToTv(this.orderTypeList)) {
                    return VipMsgCodeConstant.VIP_GET_CONSUMPTION_RECORD_REQUEST_ORDER_TYPE_ERROR;
                }
            }
        }

        if (this.status == null || this.status < 0 || this.status > 3) {
            return VipMsgCodeConstant.VIP_GET_CONSUMPTION_RECORD_REQUEST_STATUS_ERROR;
        }

        if (this.page == null || this.page < 1 || this.pagesize == null || this.pagesize < 1) {
            return VipMsgCodeConstant.REQUEST_PAGE_ILLEGAL;
        }

        if (this.day == null || this.day < 0) {
            return VipMsgCodeConstant.VIP_GET_CONSUMPTION_RECORD_REQUEST_DAY_ERROR;
        }
        return VipMsgCodeConstant.REQUEST_SUCCESS;
    }

    /**
     * 获取参数列表
     * @return
     */
    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("status", this.status);
        parametersMap.put("day", this.day);
        // parametersMap.put("uname", this.username);
        parametersMap.put("page", this.page);
        parametersMap.put("pagesize", this.pagesize);
        parametersMap.put("end", this.terminalType);
        parametersMap.put("deviceKey", this.deviceKey);
        parametersMap.put("mac", this.mac);
        parametersMap.put("userid", this.userid);

        return parametersMap;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDay() {
        return this.day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPagesize() {
        return this.pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public String getTerminalType() {
        return this.terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getDeviceKey() {
        return this.deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getBroadcastId() {
        return this.broadcastId;
    }

    public void setBroadcastId(Integer broadcastId) {
        this.broadcastId = broadcastId;
    }

    public String getOrderTypes() {
        return this.orderTypes;
    }

    public void setOrderTypes(String orderTypes) {
        this.orderTypes = orderTypes;
    }

    public List<String> getOrderTypeList() {
        return this.orderTypeList;
    }

}
