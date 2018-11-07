package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import org.springframework.util.CollectionUtils;

/**
 * 代金券状态信息第三方响应结果封装类
 * @author yikun
 */
public class VoucherStatusTpResponse {

    /**
     * 响应状态，0--成功，1--失败
     */
    private Integer status;

    /**
     * 失败信息，status为1时有效
     */
    private String msg;

    /**
     * 错误码，1001-代金卷或兑换码不存在；1002-代金卷被冻结；1003-代金卷无效，已使用；status为1时有效，
     */
    private Integer code;

    /**
     * 代金券面额，精确到分
     */
    private Integer amount;

    /**
     * 代金卷使用开始时间
     */
    private Long startTime;

    /**
     * 代金卷使用结束时间
     */
    private Long endTime;

    /**
     * 代金券适用平台，各终端以英文逗号分隔，如"141001,141002,141003,141007"
     */
    private List<String> terminals;

    /**
     * 代金券适用规则
     */
    private List<VoucherStatusRule> rules;

    /**
     * 判断当前VoucherStatusTpResponse是否可用;
     * 判断标准：1.响应成功；2.适用终端不为空；3.适用规则不为空；4.适用终端包含TV端
     * @return
     */
    public boolean isAvailable() {
        return this.status != null && 0 == this.status && !CollectionUtils.isEmpty(this.terminals)
                && !CollectionUtils.isEmpty(this.rules) && this.terminals.contains(VipTpConstant.TERMINAL_TV);
    }

    /**
     * 代金券适用规则
     * @author yikun
     */
    public static class VoucherStatusRule {

        /**
         * 代金卷适用范围，1--普通vip套餐；2--高级vip套餐；3--电影单片；4--直播
         */
        private Integer type;

        /**
         * type对应的某一类可用产品id；当type为3或4时，表示对所有单片或直播都可用
         */
        private List<Integer> ids;

        public Integer getType() {
            return this.type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public List<Integer> getIds() {
            return this.ids;
        }

        public void setIds(List<Integer> ids) {
            this.ids = ids;
        }

    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public List<String> getTerminals() {
        return this.terminals;
    }

    public void setTerminals(List<String> terminals) {
        this.terminals = terminals;
    }

    public List<VoucherStatusRule> getRules() {
        return this.rules;
    }

    public void setRules(List<VoucherStatusRule> rules) {
        this.rules = rules;
    }

}
