package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

/**
 * 收藏接口第三方接口响应基类
 * Created by liudaojie on 16/9/18.
 */
public class CollectionTpResponse<T> {
    private Integer code; // 响应状态码
    private String msg; // 描述信息
    private String region; // 区域
    private String lang; // 语言
    private String reqid; // ??
    private T data; // 响应实体

    public Boolean isSucceed() {
        if (code != null && code == CODE_SUCCESS) {
            return true;
        }
        return false;
    }

    public static final int CODE_SUCCESS = 200; // 成功状态码

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
