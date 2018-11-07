package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.bean;

/**
 * Created by guoyunfeng on 2017/6/22.
 * 用户影视会员权益信息
 */
public class PVUserVIPInfo {
    public final static int UNSUBSCRIBE = 1;
    public final static int SUBSCRIBE = 2;

    private MembershipInfo membershipInfo;

    public MembershipInfo getMembershipInfo() {
        return membershipInfo;
    }

    public void setMembershipInfo(MembershipInfo membershipInfo) {
        this.membershipInfo = membershipInfo;
    }

    public static class MembershipInfo {
        private String proExpiresAt;
        private int isProSubscribe;
        private String normalExpiresAt;
        private int isNormalSubscribe;
        private String membershipType;
        private int normalSubscribeMode;
        private int proSubScribeMode;

        public String getProExpiresAt() {
            return proExpiresAt;
        }

        public void setProExpiresAt(String proExpiresAt) {
            this.proExpiresAt = proExpiresAt;
        }

        public int isProSubscribe() {
            return isProSubscribe;
        }

        public void setProSubscribe(int proSubscribe) {
            isProSubscribe = proSubscribe;
        }

        public String getNormalExpiresAt() {
            return normalExpiresAt;
        }

        public void setNormalExpiresAt(String normalExpiresAt) {
            this.normalExpiresAt = normalExpiresAt;
        }

        public int isNormalSubscribe() {
            return isNormalSubscribe;
        }

        public void setNormalSubscribe(int normalSubscribe) {
            isNormalSubscribe = normalSubscribe;
        }

        public String getMembershipType() {
            return membershipType;
        }

        public void setMembershipType(String membershipType) {
            this.membershipType = membershipType;
        }

        public int getNormalSubscribeMode() {
            return normalSubscribeMode;
        }

        public void setNormalSubscribeMode(int normalSubscribeMode) {
            this.normalSubscribeMode = normalSubscribeMode;
        }

        public int getProSubScribeMode() {
            return proSubScribeMode;
        }

        public void setProSubScribeMode(int proSubScribeMode) {
            this.proSubScribeMode = proSubScribeMode;
        }
    }
}
