package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

public class GetVipTypeTpResponse {

    /**
     * 编码，0--成功，1--失败
     */
    private String code;

    /**
     * 返回结果
     */
    private ResultValues values;

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
        /**
         * 是否是此会员类型，true--是，false--否
         */
        private Boolean vipType;

        /**
         * 返回的信息
         */
        private String msg;

        public Boolean getVipType() {
            return this.vipType;
        }

        public void setVipType(Boolean vipType) {
            this.vipType = vipType;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
