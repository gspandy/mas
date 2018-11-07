package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.terminal.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BoxConfigResponse {
    private String groupid;// 机器组id

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }
}
