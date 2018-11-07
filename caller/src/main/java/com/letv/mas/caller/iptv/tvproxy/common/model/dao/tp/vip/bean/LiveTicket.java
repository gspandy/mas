package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean;

import java.io.Serializable;

/**
 * Author：apple on 17/4/20 20:43
 * eMail：dengliwei@le.com
 */
public class LiveTicket implements Serializable {
    // 票数
    private Long amount;
    // 赛事
    private String project;
    // 场次id
    private String screening;
    // 频道
    private String channel;
    // 票有效期
    private Long deadline;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getScreening() {
        return screening;
    }

    public void setScreening(String screening) {
        this.screening = screening;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }
}
