package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response;

import java.io.Serializable;

/**
 * 直播部门接口返回单条数据响应的通用封装类
 * @author KevinYi
 */
public class LiveSingleDataCommonTpResponse<T> implements Serializable {

    private T data;

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
