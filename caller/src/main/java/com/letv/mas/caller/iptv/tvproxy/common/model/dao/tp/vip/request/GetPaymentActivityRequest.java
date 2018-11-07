package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

/**
 * 获取活动信息请求封装类
 * @author KevinYi
 */
public class GetPaymentActivityRequest extends VipCommonRequest {

    /**
     * apk version，当前TV版的版本，国光整改之后新增需求，0--letv，1--国广版，2--第三方SDK;
     */
    private Integer av;

    public GetPaymentActivityRequest(String username, Long userid, Integer av, String mac) {
        super(username, userid, mac);
        this.av = av;
    }

    public Integer getAv() {
        return this.av;
    }

    public void setAv(Integer av) {
        this.av = av;
    }

}
