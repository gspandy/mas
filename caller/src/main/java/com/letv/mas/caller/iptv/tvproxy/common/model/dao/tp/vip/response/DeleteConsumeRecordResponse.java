package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

public class DeleteConsumeRecordResponse {
    private String status;
    private errorMsg error;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public errorMsg getError() {
        return this.error;
    }

    public void setError(errorMsg error) {
        this.error = error;
    }

    public class errorMsg {
        private String code;
        private String msg;

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

    }
}
