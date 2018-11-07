package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

public class GetPlayUserTokenResponse {

    /**
     * 响应码
     */
    private String code;

    /**
     * 校验结果
     */
    private PlayUserTokenInfo values;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PlayUserTokenInfo getValues() {
        return this.values;
    }

    public void setValues(PlayUserTokenInfo values) {
        this.values = values;
    }

    /**
     * 付费频道会员加速获取token信息
     * @author liyunlong
     */
    public static class PlayUserTokenInfo {

        /**
         *
         */
        private static final long serialVersionUID = -6798884413163085944L;
        /**
         * 用户加速字段
         */
        private String uinfo;
        /**
         * 用户tokenID
         */
        private String tokenUserId;
        /**
         * token值
         */
        private String token;
        /**
         * 返回的错误信息
         */
        private String msg;

        public String getUinfo() {
            return this.uinfo;
        }

        public void setUinfo(String uinfo) {
            this.uinfo = uinfo;
        }

        public String getTokenUserId() {
            return this.tokenUserId;
        }

        public void setTokenUserId(String tokenUserId) {
            this.tokenUserId = tokenUserId;
        }

        public String getToken() {
            return this.token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

    }
}
