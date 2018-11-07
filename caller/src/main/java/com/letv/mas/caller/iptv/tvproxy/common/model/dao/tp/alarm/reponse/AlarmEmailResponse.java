package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.alarm.reponse;

import java.io.Serializable;

/**
 * 邮件报警，是否成功Response
 */
public class AlarmEmailResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4420976824852527814L;

    private Integer status;// 状态码 1成功、0失败
    private Object data;// 成功信息 或 空（失败时返回）
    private Integer code;// 错误码，成功时不返回
    private String msg;// 错误信息，成功时不返回

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "AlarmEmailResponse [status=" + status + ", data=" + data + ", code=" + code + ", msg=" + msg + "]";
    }

}
