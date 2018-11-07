package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import java.io.Serializable;
import java.util.Map;

/**
 * 直播、轮播、卫视台发送弹幕请求第三方响应封装类
 * @author KevinYi
 */
public class LiveSendChatMsgResposne implements Serializable {

    private Integer code;
    private Map<String, Object> data;

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}
