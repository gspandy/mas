package com.letv.mas.caller.iptv.tvproxy.common.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceQueryResultResponse {
    private static final long serialVersionUID = 4171291568563554695L;
    private Long userId;
    private Integer lepoint;
    private String createDate;
    private String updateDate;
    private Integer totalPoint;
    private Integer code;
    private String msg;

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getLepoint() {
        return this.lepoint;
    }

    public void setLepoint(Integer lepoint) {
        this.lepoint = lepoint;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getTotalPoint() {
        return this.totalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
