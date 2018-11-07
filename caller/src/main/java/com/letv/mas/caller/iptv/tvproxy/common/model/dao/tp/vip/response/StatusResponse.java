package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatusResponse {
    private Integer status = 1;

    public StatusResponse() {

    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
