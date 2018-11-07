package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CommonListResponse<T> extends BaseResponse {
    private static final long serialVersionUID = -6207819265134660954L;

    private List<T> data;

    public CommonListResponse() {

    }

    public CommonListResponse(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
