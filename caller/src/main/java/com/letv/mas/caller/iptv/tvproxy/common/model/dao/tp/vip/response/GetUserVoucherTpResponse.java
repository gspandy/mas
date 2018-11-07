package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.util.List;
import java.util.Map;

public class GetUserVoucherTpResponse {

    private Integer code;// 接口状态码，0--正常，非0--异常
    private List<Voucher> data;// 代金券数据列表

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<Voucher> getData() {
        return data;
    }

    public void setData(List<Voucher> data) {
        this.data = data;
    }

    public static class Voucher {
        private Integer amount;// 代金券金额，单位分
        private String amount4Yuan;//
        private String couponCode;// 代金券
        private String startTime;// 24小时格式，开始时间
        private String endTime;// 24小时格式，结束时间
        private String name;// 代金券名称，来源
        private Integer ownerType;// 代金券所属类型[1:公共类型|2:私有类型]
        private List<VoucherRule> rules;//
        private Map<String, String> rulesDesc;// 套餐类型
        private Integer status;// 0|正常（默认），1|冻结
        private Integer templateId;// 模板id
        private Integer userId;// 绑定用户id
        private Integer userQuota;// 每个用户id使用次数。0，没有限制
        private List<Integer> terminals;// 代金券勾选的可使用终端平台

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getAmount4Yuan() {
            return amount4Yuan;
        }

        public void setAmount4Yuan(String amount4Yuan) {
            this.amount4Yuan = amount4Yuan;
        }

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getOwnerType() {
            return ownerType;
        }

        public void setOwnerType(Integer ownerType) {
            this.ownerType = ownerType;
        }

        public List<VoucherRule> getRules() {
            return rules;
        }

        public void setRules(List<VoucherRule> rules) {
            this.rules = rules;
        }

        public Map<String, String> getRulesDesc() {
            return rulesDesc;
        }

        public void setRulesDesc(Map<String, String> rulesDesc) {
            this.rulesDesc = rulesDesc;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getTemplateId() {
            return templateId;
        }

        public void setTemplateId(Integer templateId) {
            this.templateId = templateId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getUserQuota() {
            return userQuota;
        }

        public void setUserQuota(Integer userQuota) {
            this.userQuota = userQuota;
        }

        public List<Integer> getTerminals() {
            return terminals;
        }

        public void setTerminals(List<Integer> terminals) {
            this.terminals = terminals;
        }

    }
}
