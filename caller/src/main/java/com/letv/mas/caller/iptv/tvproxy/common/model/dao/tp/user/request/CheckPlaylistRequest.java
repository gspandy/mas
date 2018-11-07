package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request;

import java.util.HashMap;
import java.util.Map;

public class CheckPlaylistRequest {

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
    private Long albumid;

    public CheckPlaylistRequest() {
        super();
    }

    public CheckPlaylistRequest(String username, String token, Long albumid) {
        super();
        this.username = username;
        this.token = token;
        this.albumid = albumid;
    }

    /**
     * 获取参数列表
     * @return
     */
    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("sso_tk", this.token);
        parametersMap.put("pid", this.albumid);

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

    public Long getAlbumid() {
        return this.albumid;
    }

    public void setAlbumid(Long albumid) {
        this.albumid = albumid;
    }

}
