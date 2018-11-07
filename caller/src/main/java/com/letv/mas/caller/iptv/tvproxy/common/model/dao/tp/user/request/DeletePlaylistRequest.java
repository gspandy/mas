package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request;

import java.util.HashMap;
import java.util.Map;

public class DeletePlaylistRequest {

    /**
     * 账户的用户名
     */
    private String username;

    /**
     * 用户登录token
     */
    private String token;

    /**
     * 剧集id
     */
    private Long favid;

    public DeletePlaylistRequest() {
        super();
    }

    public DeletePlaylistRequest(String username, String token, Long favid) {
        super();
        this.username = username;
        this.token = token;
        this.favid = favid;
    }

    /**
     * 获取参数列表
     * @return
     */
    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("id", this.favid);
        parametersMap.put("sso_tk", this.token);

        return parametersMap;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getFavid() {
        return this.favid;
    }

    public void setFavid(Long favid) {
        this.favid = favid;
    }

}
