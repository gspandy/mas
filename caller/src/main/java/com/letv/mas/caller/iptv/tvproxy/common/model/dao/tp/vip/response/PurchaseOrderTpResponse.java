package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

/**
 * 调用第三方接口获取订单号返回结果
 * @author dunhongqin
 */
public class PurchaseOrderTpResponse {

    private String orderId;
    private ConsumptionCenterError error;
    private boolean code;

    public PurchaseOrderTpResponse() {
    }

    public PurchaseOrderTpResponse(ConsumptionCenterError error) {
        this.error = error;
    }

    public PurchaseOrderTpResponse(String orderId) {
        this.orderId = orderId;
    }

    public PurchaseOrderTpResponse(String orderId, ConsumptionCenterError error) {
        this.orderId = orderId;
        this.error = error;
    }

    public PurchaseOrderTpResponse(String orderId, boolean code) {
        this.orderId = orderId;
        this.code = code;
    }

    public PurchaseOrderTpResponse(String orderId, boolean code, ConsumptionCenterError error) {
        this.orderId = orderId;
        this.code = code;
        this.error = error;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public ConsumptionCenterError getError() {
        return this.error;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setError(ConsumptionCenterError error) {
        this.error = error;
    }

    public boolean isCode() {
        return this.code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public static class ConsumptionCenterError {
        private Integer code;
        private String msg;
        private String end;
        private String pId;

        public ConsumptionCenterError() {
        }

        public ConsumptionCenterError(Integer code, String msg, String end, String pId) {
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
}
