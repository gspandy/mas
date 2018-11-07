package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.io.Serializable;

public class GetFreeVipTpResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5630183880420251612L;

    private String code;// 响应码

    private ResultValues values;// 返回值对象

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ResultValues getValues() {
        return this.values;
    }

    public void setValues(ResultValues values) {
        this.values = values;
    }

    public static class ResultValues {
        private String orderId;// 成功则返回订单id
        private String msg;// 失败返回错误信息

        public String getOrderId() {
            return this.orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

    }
}
