package com.letv.mas.caller.iptv.tvproxy.terminal.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

public class Prompt extends BaseDto {
    private String head;// 页面标题
    private String title;// 文案标题
    private String confirmButtonName;// 确认按钮标题

    Prompt() {
    }

    public Prompt(String head, String title, String confirmButtonName) {
        this.head = head;
        this.title = title;
        this.confirmButtonName = confirmButtonName;
    }

    public String getHead() {
        return this.head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConfirmButtonName() {
        return this.confirmButtonName;
    }

    public void setConfirmButtonName(String confirmButtonName) {
        this.confirmButtonName = confirmButtonName;
    }
}
