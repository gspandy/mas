package com.letv.mas.caller.iptv.tvproxy.common.model.dto.response;

/**
 * 机卡绑定需求，鉴别该账号是否领取过该机器的机卡绑定套餐（调用用户绑定查询接口）的请求返回结果封装类
 * @author yikun
 * @since 2014-08-07
 */
public class UserBindTpResponse {

    private Integer code; // 状态，0--成功，1--失败
    private UserBindConent values; // 返回结果具体内容

    public boolean isSuccess() {
        return this.code != null && 0 == this.code && this.values != null;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public UserBindConent getValues() {
        return this.values;
    }

    public void setValues(UserBindConent values) {
        this.values = values;
    }

    public static class UserBindConent {
        private String deviceKey; // 设备唯一标识
        private String userid; // 用户id
        private Boolean isUserActive; // 用户是否激活，true--已激活；false--未激活（只要当前用户领取过机卡绑定套餐就会显示为true）
        private String bindKeyList; // 用户绑定设备列表，英文逗号（,）分割，isDeviceActive为true时有效
        private String msg; // code为1时返回失败原因

        /**
         * 新增移动领先把那机卡绑定后，返回的设备信息，格式为
         * "{deviceKey}-{deviceType},...,{deviceKey}-{deviceType}"，
         * 即一组deviceKey和deviceType使用"-"连接，多组设备使用英文逗号","连接
         */
        private String bindInfos;

        public UserBindConent() {
            super();
        }

        public UserBindConent(String deviceKey, String userid, Boolean isUserActive, String bindKeyList) {
            super();
            this.deviceKey = deviceKey;
            this.userid = userid;
            this.isUserActive = isUserActive;
            this.bindKeyList = bindKeyList;
        }

        public String getDeviceKey() {
            return this.deviceKey;
        }

        public void setDeviceKey(String deviceKey) {
            this.deviceKey = deviceKey;
        }

        public String getUserid() {
            return this.userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public Boolean getIsUserActive() {
            return this.isUserActive;
        }

        public void setIsUserActive(Boolean isUserActive) {
            this.isUserActive = isUserActive;
        }

        public String getBindKeyList() {
            return this.bindKeyList;
        }

        public void setBindKeyList(String bindKeyList) {
            this.bindKeyList = bindKeyList;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getBindInfos() {
            return this.bindInfos;
        }

        public void setBindInfos(String bindInfos) {
            this.bindInfos = bindInfos;
        }

    }
}
