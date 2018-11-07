package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.response;

/**
 * Created by wangli on 4/24/17.
 * 付费会员的接口返回封装类
 */
public class VpResponse<T> {

    // 信息头
    private Head head;

    // 信息体
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

        // 状态
        private int ret;
        // 返回消息
        private String msg;

        public int getRet() {
            return ret;
        }

        public void setRet(int ret) {
            this.ret = ret;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
