package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import java.io.Serializable;

public class DanmuListResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4957027407818975142L;

    private Integer code;

    private DanmuListInnerResponse data;

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public DanmuListInnerResponse getData() {
        return this.data;
    }

    public void setData(DanmuListInnerResponse data) {
        this.data = data;
    }

}
