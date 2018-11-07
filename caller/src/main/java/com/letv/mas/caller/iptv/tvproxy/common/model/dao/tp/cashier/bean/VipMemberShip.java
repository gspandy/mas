package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.bean;

import java.util.List;

/**
 * 连续包月会员信息类
 * Created by gongyishu on 2017/7/3.
 */
public class VipMemberShip {
    private Body membershipInfo;

    public Body getMembershipInfo() {
        return membershipInfo;
    }

    public void setMembershipInfo(Body membershipInfo) {
        this.membershipInfo = membershipInfo;
    }

    public static class Body {
        private Integer isProSubscribe;
        private String proExpiresAt;

        public Integer getIsProSubscribe() {
            return isProSubscribe;
        }

        public void setIsProSubscribe(Integer isProSubscribe) {
            this.isProSubscribe = isProSubscribe;
        }

        public String getProExpiresAt() {
            return proExpiresAt;
        }

        public void setProExpiresAt(String proExpiresAt) {
            this.proExpiresAt = proExpiresAt;
        }

    }
}
