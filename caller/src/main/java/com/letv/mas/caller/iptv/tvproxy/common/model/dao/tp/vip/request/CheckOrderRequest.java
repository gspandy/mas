package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.util.HashMap;
import java.util.Map;

/**
 * 检查订单状态请求封装类
 * @author yikun
 */
public class CheckOrderRequest {

    /**
     * 登录账户的用户名
     */
    private String username;

    /**
     * 订单号
     */
    private String orderid;

    /**
     * 用户中心id
     */
    private Long userid;

    public CheckOrderRequest() {
        super();
    }

    public CheckOrderRequest(String username, String orderid, Long userid) {
        super();
        this.username = username;
        this.orderid = orderid;
        this.userid = userid;
    }

    /**
     * 获取参数列表
     * @return
     */
    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("orderid", this.orderid);
        parametersMap.put("userid", this.userid);

        return parametersMap;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrderid() {
        return this.orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

}
