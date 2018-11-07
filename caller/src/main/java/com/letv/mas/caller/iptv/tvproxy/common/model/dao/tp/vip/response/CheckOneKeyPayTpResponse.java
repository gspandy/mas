package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;

/**
 * 一键支付绑定请求响应结果封装类
 * @author KevinYi
 */
public class CheckOneKeyPayTpResponse {

    private static final String CHECK_ONE_KEY_PAY_FAILED = VipTpConstant.TP_STATTUS_CODE_0; // 0--响应失败
    private static final String CHECK_ONE_KEY_PAY_HAS_BIND = VipTpConstant.TP_STATTUS_CODE_1; // 1--查询成功，已绑定
    // private static final String CHECK_ONE_KEY_PAY_NOT_BIND =
    // VipTpConstant.TP_STATTUS_CODE_2; // 2--查询成功，未绑定

    private String status; // 响应状态码,0--查询失败，1--查询成功，已绑，2--未绑
    private Long userid; // 用户ID
    private String paypalaccount; // 是否绑定的paypal账号，status为1时有值
    private String msg; // 结果描述，status为0时有值

    /**
     * 判断是否响应成功
     * @return
     */
    public boolean assertSucceed() {
        return this.status != null && !CHECK_ONE_KEY_PAY_FAILED.equals(this.status);
    }

    /**
     * 判断是否已绑
     * @return
     */
    public boolean hasBind() {
        return this.status != null && CHECK_ONE_KEY_PAY_HAS_BIND.equals(this.status);
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getPaypalaccount() {
        return this.paypalaccount;
    }

    public void setPaypalaccount(String paypalaccount) {
        this.paypalaccount = paypalaccount;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
