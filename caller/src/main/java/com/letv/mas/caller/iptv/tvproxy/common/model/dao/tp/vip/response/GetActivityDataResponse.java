package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.util.List;

public class GetActivityDataResponse {

    private Integer code;// 响应码，0--正常返回，非0--异常
    private List<BossActivityData> data;// 活动具体数据

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<BossActivityData> getData() {
        return this.data;
    }

    public void setData(List<BossActivityData> data) {
        this.data = data;
    }
}
