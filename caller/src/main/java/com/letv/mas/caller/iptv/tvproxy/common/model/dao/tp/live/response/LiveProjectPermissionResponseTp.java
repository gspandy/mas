package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response;

import java.util.List;

public class LiveProjectPermissionResponseTp {
    private Integer cost;
    private List<PermissionInfo> result;
    private String status;

    public LiveProjectPermissionResponseTp() {

    }

    public Integer getCost() {
        return this.cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public List<PermissionInfo> getResult() {
        return this.result;
    }

    public void setResult(List<PermissionInfo> result) {
        this.result = result;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class PermissionInfo {
        private String liveid;// 场次id
        private String status;// 状态 字符串 1 有权限 0 无权限
        private String vipprice;// vip价格
        private String price;// 价格
        private String finalprice;// 交易价格
        /**
         * 付费类型，1--包年以上会员免费，2--包年以上或单点免费，3--会员免费，4--会员或单点免费，5--单点
         */
        private Integer payType;

        public String getLiveid() {
            return this.liveid;
        }

        public void setLiveid(String liveid) {
            this.liveid = liveid;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getVipprice() {
            return this.vipprice;
        }

        public void setVipprice(String vipprice) {
            this.vipprice = vipprice;
        }

        public String getPrice() {
            return this.price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getFinalprice() {
            return this.finalprice;
        }

        public void setFinalprice(String finalprice) {
            this.finalprice = finalprice;
        }

        public Integer getPayType() {
            return this.payType;
        }

        public void setPayType(Integer payType) {
            this.payType = payType;
        }
    }
}
