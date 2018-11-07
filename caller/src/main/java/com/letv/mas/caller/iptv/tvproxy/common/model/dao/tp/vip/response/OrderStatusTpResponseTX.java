package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;


/**
 * Created by leeco on 2018/2/6.
 */
public class OrderStatusTpResponseTX {
    private int code;
    private String msg;
    private String errors;
    private int cost;
    private OrderTXData data;
    private Boolean success;

    public OrderStatusTpResponseTX() {
    }

    public OrderStatusTpResponseTX(int code, String msg, String errors, int cost, OrderTXData data) {
        this.code = code;
        this.msg = msg;
        this.errors = errors;
        this.cost = cost;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getErrors() {
        return errors;
    }

    public int getCost() {
        return cost;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public OrderTXData getData() {
        return data;
    }

    public void setData(OrderTXData data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}