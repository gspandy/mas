package com.letv.mas.caller.iptv.tvproxy.common.model.dto.response;

public enum LetvPayResponseStatus {
    FAILURE("0"),
    KEY_IS_NULL("7"),
    SUCCESS("1");
    private final String status;

    private LetvPayResponseStatus(String status) {
        this.status = status;
    }

    public String value() {
        return this.status;
    }

    @Override
    public String toString() {
        return this.status;
    }

}
