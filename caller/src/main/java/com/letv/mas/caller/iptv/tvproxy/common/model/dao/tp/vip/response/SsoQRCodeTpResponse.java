package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

/**
 * 获取会员中心认证二维码，调用第三方返回参数
 * @author dunhongqin
 */
public class SsoQRCodeTpResponse {

    private String status; // 响应状态,0:失败,1:成功
    private String action; // 请求接口名称
    private String responseType; // 响应类型:json
    private String errorCode; // 错误码 1001:参数错误 1002:用户ID为空或者票据已过期
    private String message; // 错误信息
    private QRCode bean;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public QRCode getBean() {
        return bean;
    }

    public void setBean(QRCode bean) {
        this.bean = bean;
    }

    public static class QRCode {
        private String qrcode; // 二维码信息
        private Long expire; // 二维码有时长(单位:秒)

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public Long getExpire() {
            return expire;
        }

        public void setExpire(Long expire) {
            this.expire = expire;
        }
    }
}
