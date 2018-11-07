package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response;

public class LivePeopleCountResponse {
    private Integer status;
    private String errorCode;
    private String errorMessage;
    private LivePeopleCount data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LivePeopleCount getData() {
        return data;
    }

    public void setData(LivePeopleCount data) {
        this.data = data;
    }

    public static class LivePeopleCount {
        private String onlineNum; // 当前在线人数
        private String allNum; // 频道累计人数

        public String getOnlineNum() {
            return onlineNum;
        }

        public void setOnlineNum(String onlineNum) {
            this.onlineNum = onlineNum;
        }

        public String getAllNum() {
            return allNum;
        }

        public void setAllNum(String allNum) {
            this.allNum = allNum;
        }
    }
}
