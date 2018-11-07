package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CommonResponse<T> extends BaseResponse {
    private static final long serialVersionUID = -3494488889682690829L;

    public CommonResponse() {

    }

    public CommonResponse(T data) {
        this.data = data;
    }

    private T data;

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
