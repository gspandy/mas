package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

/**
 * Created by imom0 on 10/3/2017.
 */
public class GetFreeVipV2TpResponse {

    private String code;// 响应码

    private String msg;

    private GetFreeVipV2TpResponse.DataValues data;// 返回值对象

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public GetFreeVipV2TpResponse.DataValues getData() {
        return this.data;
    }

    public void setData(GetFreeVipV2TpResponse.DataValues data) {
        this.data = data;
    }

    public static class DataValues {
        private String orderid;// 成功则返回订单id

        public String getOrderid() {
            return this.orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }
    }
}
