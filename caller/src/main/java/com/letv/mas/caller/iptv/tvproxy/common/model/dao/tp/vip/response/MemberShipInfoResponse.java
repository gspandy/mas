package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

/**
 * 会员包月信息请求响应封装类
 * Created by gongyishu on 2017/7/3.
 */
public class MemberShipInfoResponse<T> {

    private Head head;
    private T body;

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public static class Head {
        private String msg;
        private Integer ret;

        public String getMsg() {
            return msg;
        }

        public Integer getRet() {
            return ret;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public void setRet(Integer ret) {
            this.ret = ret;
        }

    }
}
