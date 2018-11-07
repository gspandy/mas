package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response;

/**
 * 调用第三方接口获取专题包信息封装类
 * @author liyunlong
 */
public class GetSubjectPackageTpResponse {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 专题包数据
     */
    private SubjectTjPackageTpResponse data;

    /**
     * 返回消息
     */
    private String msg;

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public SubjectTjPackageTpResponse getData() {
        return this.data;
    }

    public void setData(SubjectTjPackageTpResponse data) {
        this.data = data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
