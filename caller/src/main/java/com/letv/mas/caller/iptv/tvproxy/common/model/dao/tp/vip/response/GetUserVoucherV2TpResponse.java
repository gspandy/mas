package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.util.List;

/**
 * Created by imom0 on 10/3/2017.
 */
public class GetUserVoucherV2TpResponse {

    private Integer code;// 接口状态码，0--正常，非0--异常
    private Data data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private List<Coupon> rows;

        public List<Coupon> getRows() {
            return rows;
        }

        public void setRows(List<Coupon> rows) {
            this.rows = rows;
        }

        public static class Coupon {
            private String code;
            private String name;
            private String amount;

            private String validStartTime;
            private String validEndTime;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getValidStartTime() {
                return validStartTime;
            }

            public void setValidStartTime(String validStartTime) {
                this.validStartTime = validStartTime;
            }

            public String getValidEndTime() {
                return validEndTime;
            }

            public void setValidEndTime(String validEndTime) {
                this.validEndTime = validEndTime;
            }
        }
    }
}
