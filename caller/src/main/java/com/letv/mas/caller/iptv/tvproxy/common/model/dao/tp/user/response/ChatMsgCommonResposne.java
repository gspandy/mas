package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import java.io.Serializable;

/**
 * 用户中心（点播/直播）弹幕相关接口响应公共封装类；
 * 用户中心弹幕相关接口返回的的数据结构为；
 * 成功时返回"{"code":"200","data":"<T>"}"；异常时返回"{"code":"200","data":"{errMsg}"}"，
 * 故这里将data统一使用String接受，在判断code之后，在对data进行对应解析成parsedData；
 * @author KevinYi
 * @param <T>
 */
public class ChatMsgCommonResposne<T> implements Serializable {

    private String code;
    // private String data;
    private T parsedData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    // public String getData() {
    // return data;
    // }
    //
    // public void setData(String data) {
    // this.data = data;
    // }

    public T getParsedData() {
        return parsedData;
    }

    public void setParsedData(T parsedData) {
        this.parsedData = parsedData;
    }

}
