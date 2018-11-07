package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request;

import java.util.HashMap;
import java.util.Map;

public class DeletePlaylogRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户中心id
     */
    private Long userid;

    /**
     * 用户登录token
     */
    private String token;

    /**
     * 专辑id
     */
    private Long albumid;

    /**
     * 视频id
     */
    private Long videoid;

    private Long roleid;
    /**
     * 播控方
     */
    private Integer broadcastId;

    public DeletePlaylogRequest() {
        super();
    }

    public DeletePlaylogRequest(String username, Long userid, String token, Integer broadcastId, Long roleid) {
        super();
        this.username = username;
        this.userid = userid;
        this.token = token;
        this.broadcastId = broadcastId;
        this.roleid = roleid;
    }

    public DeletePlaylogRequest(String username, Long userid, String token, Long albumid, Long videoid,
            Integer broadcastId, Long roleid) {
        super();
        this.username = username;
        this.userid = userid;
        this.token = token;
        this.albumid = albumid;
        this.videoid = videoid;
        this.broadcastId = broadcastId;
        this.roleid = roleid;
    }

    /**
     * 获取参数列表
     * @return
     */
    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("uid", this.userid);
        parametersMap.put("sso_tk", this.token);
        parametersMap.put("pid", this.albumid);
        parametersMap.put("vid", this.videoid);
        parametersMap.put("rid", this.roleid == null ? 0 : this.roleid);// 儿童模式加角色id,默认为0
        return parametersMap;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
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

    public Long getVideoid() {
        return this.videoid;
    }

    public void setVideoid(Long videoid) {
        this.videoid = videoid;
    }

    public Integer getBroadcastId() {
        return this.broadcastId;
    }

    public void setBroadcastId(Integer broadcastId) {
        this.broadcastId = broadcastId;
    }

    public Long getRoleid() {
        return this.roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

}
