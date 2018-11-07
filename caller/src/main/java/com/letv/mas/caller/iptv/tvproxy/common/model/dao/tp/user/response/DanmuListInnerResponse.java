package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import java.io.Serializable;
import java.util.List;

public class DanmuListInnerResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1186039207716233455L;

    private List<DanmuResponse> list;

    private List<DanmuResponse> user;

    public List<DanmuResponse> getList() {
        return this.list;
    }

    public void setList(List<DanmuResponse> list) {
        this.list = list;
    }

    public List<DanmuResponse> getUser() {
        return this.user;
    }

    public void setUser(List<DanmuResponse> user) {
        this.user = user;
    }
}
