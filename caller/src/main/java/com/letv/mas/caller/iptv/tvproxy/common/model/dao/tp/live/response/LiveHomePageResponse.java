package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.dto.LiveIndexPageDto;

public class LiveHomePageResponse {
    private Integer status;
    private String errorCode;
    private String errorMessage;
    private LiveIndexPageDto data;

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

    public LiveIndexPageDto getData() {
        return data;
    }

    public void setData(LiveIndexPageDto data) {
        this.data = data;
    }
}
