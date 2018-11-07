package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.util.List;

/**
 * 检查订单支付状态返回参数
 * @author dunhongqin
 */
public class OrderStatusTpResponseV3 {
    private boolean success;
    private Head head;
    private List<UserPackageV3> body;
    private ConsumptionCenterErrorV2 error;

    public OrderStatusTpResponseV3() {
    }

    public OrderStatusTpResponseV3(boolean success, Head head, List<UserPackageV3> data) {
        this.success = success;
        this.body = body;
        this.head = head;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<UserPackageV3> getBody() {
        return body;
    }

    public void setBody(List<UserPackageV3> body) {
        this.body = body;
    }

    public ConsumptionCenterErrorV2 getError() {
        return this.error;
    }

    public void setError(ConsumptionCenterErrorV2 error) {
        this.error = error;
    }

    public static class ConsumptionCenterErrorV2 {
        private Integer code;
        private String msg;
        private String end;
        private String pId;

        public ConsumptionCenterErrorV2() {
        }

        public ConsumptionCenterErrorV2(Integer code, String msg, String end, String pId) {
            this.code = code;
            this.msg = msg;
            this.end = end;
            this.pId = pId;
        }

        public Integer getCode() {
            return this.code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getEnd() {
            return this.end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getpId() {
            return this.pId;
        }

        public void setpId(String pId) {
            this.pId = pId;
        }
    }

    public static class UserPackageV3 {
        private String order_name;
        private String status;
        private String pay_status;
        private String order_start_time;
        private String order_end_time;
        private String pay_time;
        private String vip_order_id;
        private String act_id;
        private String act_type;
        private String present_name;
        private String present_img_url;
        private String present_url;
        private String shipping_url;
        private String is_month_payment;

        public String getOrder_name() {
            return order_name;
        }

        public String getStatus() {
            return status;
        }

        public String getOrder_start_time() {
            return order_start_time;
        }

        public String getOrder_end_time() {
            return order_end_time;
        }

        public String getPay_time() {
            return pay_time;
        }

        public String getVip_order_id() {
            return vip_order_id;
        }

        public String getAct_id() {
            return act_id;
        }

        public String getAct_type() {
            return act_type;
        }

        public String getPresent_name() {
            return present_name;
        }

        public String getPresent_img_url() {
            return present_img_url;
        }

        public String getPresent_url() {
            return present_url;
        }

        public String getShipping_url() {
            return shipping_url;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getIs_Month_payment() {
            return this.is_month_payment;
        }

        public void setIs_Month_payment(String is_renew) {
            if (is_renew == null || is_renew.length() == 0) {
                this.is_month_payment = "0";
            } else {
                this.is_month_payment = is_renew;
            }
        }

        public void setOrder_name(String order_name) {
            this.order_name = order_name;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setOrder_start_time(String order_start_time) {
            this.order_start_time = order_start_time;
        }

        public void setOrder_end_time(String order_end_time) {
            this.order_end_time = order_end_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public void setVip_order_id(String vip_order_id) {
            this.vip_order_id = vip_order_id;
        }

        public void setAct_id(String act_id) {
            this.act_id = act_id;
        }

        public void setAct_type(String act_type) {
            this.act_type = act_type;
        }

        public void setPresent_name(String present_name) {
            this.present_name = present_name;
        }

        public void setPresent_img_url(String present_img_url) {
            this.present_img_url = present_img_url;
        }

        public void setPresent_url(String present_url) {
            this.present_url = present_url;
        }

        public void setShipping_url(String shipping_url) {
            this.shipping_url = shipping_url;
        }
    }

    public static class Head {
        private String msg;
        private String ret;

        public Head(String msg, String ret) {
            this.msg = msg;
            this.ret = ret;
        }

        public Head() {
        }

        public String getMsg() {
            return msg;
        }

        public String getRet() {
            return ret;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public void setRet(String ret) {
            this.ret = ret;
        }
    }
}