package com.letv.mas.caller.iptv.tvproxy.common.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 乐购接口响应基类
 * @param <T>
 * 用来接收乐购接口返回的值
 */
public class LePayTpResponse<T> {

    /**
     * 请求结果状态，0--正常，其他值代表错误(9998-参数错误,9999-系统异常)
     */
    private Integer code;

    /**
     * 结果信息
     */
    private String msg;

    /**
     * 错误信息
     */
    @JsonIgnore
    private String errors;

    /**
     * 属性值data
     */
    private T data;

    /**
     * 接口花费时间
     */
    private Integer cost;

    /**
     * 系统时间
     */
    private Long currentTime;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 判断响应是否成功
     */
    public boolean isSucceed() {
        if (success && code != null && code == 0 && data != null) {
            return true;
        }
        return false;
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

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
