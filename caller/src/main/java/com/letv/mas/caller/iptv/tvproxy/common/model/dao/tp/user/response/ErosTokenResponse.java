package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import java.io.Serializable;

public class ErosTokenResponse implements Serializable {
    private static final long serialVersionUID = 4244845234707282063L;

    /**
     * Now I only see two value.It means success when value is null
     * and token has value. And ths other value -1000 sign failed.
     */
    private String code;
    private String message;
    private String token;
    private String token_secret;
    private String isSubscribed;
    private String emailvalidation;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_secret() {
        return token_secret;
    }

    public void setToken_secret(String token_secret) {
        this.token_secret = token_secret;
    }

    public String getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(String isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public String getEmailvalidation() {
        return emailvalidation;
    }

    public void setEmailvalidation(String emailvalidation) {
        this.emailvalidation = emailvalidation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
