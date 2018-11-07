package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.WFSubjectDto;

/**
 * Created by maning on 2017/9/6.
 */
public class WFSubjectTpResponse {
    private Integer resultStatus;// 成功时为1
    private Integer status;// 成功时为1
    public WFSubjectDto data;

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public WFSubjectDto getData() {
        return data;
    }

    public void setData(WFSubjectDto data) {
        this.data = data;
    }
}
