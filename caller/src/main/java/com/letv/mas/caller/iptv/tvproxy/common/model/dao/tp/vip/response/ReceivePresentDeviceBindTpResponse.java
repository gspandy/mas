package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.util.List;

/**
 * 领取超级手机赠送机卡绑定TV会员请求响应封装类
 * @author
 * @since Mar 29, 2015
 */
public class ReceivePresentDeviceBindTpResponse {

    /**
     * 响应状态码，0--响应成功，1--响应失败；原响应中为int型，这里使用String接收
     */
    private String code;

    /**
     * 具体信息，一般为错误信息
     */
    private ReceivePresentDeviceBindMsgTpResponse values;

    /**
     * 判断是否响应成功
     * @return
     */
    public boolean isSuccess() {
        if ("0".equals(this.code)) {
            if (this.values != null && this.values.getItems() != null) {
                for (ReceivePresentDeviceBindMsgTpResponse.DeviceBindMsg deviceBindMsg : this.values.getItems()) {
                    if (deviceBindMsg != null && deviceBindMsg.getVipType() != null && deviceBindMsg.getVipType() == 2) {
                        return true;
                    }
                }
            }
            if (this.values != null && this.values.getCanceltime() != null) { // 兼容老版本
                return true;
            }
        }
        return false;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ReceivePresentDeviceBindMsgTpResponse getValues() {
        return this.values;
    }

    public void setValues(ReceivePresentDeviceBindMsgTpResponse values) {
        this.values = values;
    }

    /**
     * @author
     * @since Mar 29, 2015
     */
    public static class ReceivePresentDeviceBindMsgTpResponse {

        /**
         * 领取成功时返回当前机卡对应的会员有效期截至时间，精确到毫秒
         */
        private Long canceltime;

        /**
         * 返回多种卡的绑定结果
         */
        private List<DeviceBindMsg> items;

        /**
         * 错误信息，code为1时有值
         */
        private String msg;

        /**
         * 错误码；具体包含：
         * UNKNOWN_USER(1001, "绑定用户不存在"), NOTBIND(1002,
         * "该设备没有绑定过设备，赠送的机卡绑定时常无法领取"), NOTBINDUSER(1003,
         * "该设备机卡绑定的用户与当前登录用户不符"), UNKNOWN_TYPE(//1004, "激活类型不正确"),
         * EXCEPTION(1005, "系统出错"),SIGN_ERROR(1006,
         * "签名错误"),UNKNOWN_PRESENTBIND(1007, "赠送记录不存在")
         */
        private String errorCode;

        public Long getCanceltime() {
            return this.canceltime;
        }

        public void setCanceltime(Long canceltime) {
            this.canceltime = canceltime;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getErrorCode() {
            return this.errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public List<DeviceBindMsg> getItems() {
            return items;
        }

        public void setItems(List<DeviceBindMsg> items) {
            this.items = items;
        }

        public static class DeviceBindMsg {
            /**
             * 领取成功时返回当前机卡对应的会员有效期截至时间，为一个可显示的字符串
             */
            private String cancelTime;

            /**
             * 会员类型，1-乐次元 2-全屏 3-体育
             */
            private Integer vipType;

            public String getCancelTime() {
                return cancelTime;
            }

            public void setCancelTime(String cancelTime) {
                this.cancelTime = cancelTime;
            }

            public Integer getVipType() {
                return vipType;
            }

            public void setVipType(Integer vipType) {
                this.vipType = vipType;
            }
        }

    }

}
