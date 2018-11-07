package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

/**
 * 查询用户是否可以一元绑卡返回数据封装类
 * @author liyunlong
 */
public class OneBindTpResponse {
    /**
     * 状态，0--失败，1--成功
     */
    private Integer code;

    /**
     * 用户ID
     */
    private String userid;

    /**
     * 是否可以一元绑卡
     */
    private Boolean issale;

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Boolean getIssale() {
        return this.issale;
    }

    public void setIssale(Boolean issale) {
        this.issale = issale;
    }

}
