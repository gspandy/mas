package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.alarm.request;

import java.io.Serializable;

/**
 * 短信报警请求类对象
 */
public class AlarmSMSRequest implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5113155664290418480L;

    private String token;// 验证令牌
    private String mobile;// 接收人手机号，多个用英文逗号分隔
    private String msg;// 短信内容，1-500个汉字，强烈建议300字符内，UTF-8编码。

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "AlarmSMSRequest [token=" + token + ", mobile=" + mobile + ", msg=" + msg + "]";
    }

}
