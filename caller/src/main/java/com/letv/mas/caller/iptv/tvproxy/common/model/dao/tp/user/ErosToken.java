package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user;

import java.io.Serializable;

/**
 * Eros Token Bean
 * @author liudaojie
 */
public class ErosToken implements Serializable {
    private static final long serialVersionUID = 5079457339315115618L;

    private String token; // eros token
    private String tokenSecret;// eros token-secret.
    private Long expireTime;// remaining avaliable time.

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

}
