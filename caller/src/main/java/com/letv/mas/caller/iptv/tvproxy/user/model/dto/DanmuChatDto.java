package com.letv.mas.caller.iptv.tvproxy.user.model.dto;

import java.io.Serializable;

/**
 * 弹幕发送结果信息封装类
 * @author KevinYi
 */
public class DanmuChatDto implements Serializable {

    /**
     * 弹幕发送结果，int型，1--成功，0--失败
     */
    private Integer chatResult;

    private String code;

    public DanmuChatDto() {
    }

    public DanmuChatDto(Integer chatResult) {
        this.chatResult = chatResult;
    }

    public Integer getChatResult() {
        return this.chatResult;
    }

    public void setChatResult(Integer chatResult) {
        this.chatResult = chatResult;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
