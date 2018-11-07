package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import java.util.Map;

/**
 * 儿童播单是否在播单列表响应类
 * Created by liqqc on 16/1/13.
 */
public class RolePlayListCheckTPResponse {
    private String code;;
    private Map<String, Integer> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, Integer> getData() {
        return data;
    }

    public void setData(Map<String, Integer> data) {
        this.data = data;
    }
}
