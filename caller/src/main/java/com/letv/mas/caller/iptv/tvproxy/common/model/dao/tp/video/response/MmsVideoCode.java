package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.util.Map;

public class MmsVideoCode {

    private static final long serialVersionUID = 334596256734827997L;

    private Map<Integer, String> code;
    private String status;

    public Map<Integer, String> getCode() {
        return this.code;
    }

    public void setCode(Map<Integer, String> code) {
        this.code = code;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
