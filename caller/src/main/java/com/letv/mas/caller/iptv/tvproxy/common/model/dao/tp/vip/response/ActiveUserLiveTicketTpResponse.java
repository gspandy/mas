package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

/**
 * 激活用户已购买的某一直播券请求响应封装类
 * @author KevinYi
 */
public class ActiveUserLiveTicketTpResponse {

    private Integer status; // 响应状态，0--激活失败，1--激活成功
    private String error; // 错误消息，status为0时有值

    public ActiveUserLiveTicketTpResponse() {
        super();
    }

    /**
     * 是否激活成功
     * @return
     */
    public boolean isSucceed() {
        return this.status != null && this.status == 1;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
