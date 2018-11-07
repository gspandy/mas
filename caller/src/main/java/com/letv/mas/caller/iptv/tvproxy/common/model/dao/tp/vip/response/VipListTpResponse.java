package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.util.List;

public class VipListTpResponse {
    private Integer code;// 0代表正常，其他值代表错误
    private String msg;
    private List<Vip> data;

    public boolean isSucceed() {
        if (code != null && code == 0) {
            return true;
        }
        return false;
    }

    public static class Vip {
        private String name; // 会员名
        private Integer typeGroup; // 所属会员类型
        private Integer productId; // 会员id

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getTypeGroup() {
            return typeGroup;
        }

        public void setTypeGroup(Integer typeGroup) {
            this.typeGroup = typeGroup;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Vip> getData() {
        return data;
    }

    public void setData(List<Vip> data) {
        this.data = data;
    }
}
