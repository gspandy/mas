package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean;

import java.util.List;

/**
 * Author：apple on 17/8/29 15:41
 * eMail：dengliwei@le.com
 */
public class VipObjInfo {
    /**
     * 专辑id
     */
    private String pid;
    /**
     * 专辑id
     */
    private String vid;
    /**
     * 状态 0:未发布 1： 已发布，2：定时发布
     */
    private String status;

    /**
     * 分端付费详情集合
     */
    private List<ChargeInfo> terminalCharges;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ChargeInfo> getTerminalCharges() {
        return terminalCharges;
    }

    public void setTerminalCharges(List<ChargeInfo> terminalCharges) {
        this.terminalCharges = terminalCharges;
    }
}
