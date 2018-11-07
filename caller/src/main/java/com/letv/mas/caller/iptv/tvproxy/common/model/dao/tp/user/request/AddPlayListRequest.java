package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加追剧，收藏，调用第三方接口的参数列表
 * @author dunhongqin
 */
public class AddPlayListRequest {

    /**
     * 账户的用户名
     */
    private String username;

    /**
     * 用户登录token
     */
    private String token;

    /**
     * 剧集类别id
     */
    private Integer categoryid;

    /**
     * 剧集id
     */
    private Long albumid;

    /**
     * 视频id
     */
    private Long videoId;

    /***
     * 播放平台
     */
    private Integer platForm;

    /**
     * 数据来源，1--收藏，2--追剧
     */
    private Integer fromType;

    /**
     * 播控方
     */
    private Integer broadcastId;

    public AddPlayListRequest() {
        super();
    }

    public AddPlayListRequest(String username, String token, Long albumid, Integer platForm, Integer fromType,
            Integer broadcastId) {
        super();
        this.username = username;
        this.token = token;
        this.albumid = albumid;
        this.platForm = platForm;
        this.fromType = fromType;
        this.broadcastId = broadcastId;
    }

    /**
     * 获取参数列表
     * @return
     */
    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("sso_tk", this.token);
        parametersMap.put("cid", this.categoryid);
        parametersMap.put("vid", this.videoId);
        parametersMap.put("pid", this.albumid);
        parametersMap.put("platform", this.platForm);
        parametersMap.put("fromtype", this.fromType);

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

    public Integer getCategoryid() {
        return this.categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public Long getAlbumid() {
        return this.albumid;
    }

    public void setAlbumid(Long albumid) {
        this.albumid = albumid;
    }

    public Long getVideoId() {
        return this.videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Integer getPlatForm() {
        return this.platForm;
    }

    public void setPlatForm(Integer platForm) {
        this.platForm = platForm;
    }

    public Integer getFromType() {
        return this.fromType;
    }

    public void setFromType(Integer fromType) {
        this.fromType = fromType;
    }

    public Integer getBroadcastId() {
        return this.broadcastId;
    }

    public void setBroadcastId(Integer broadcastId) {
        this.broadcastId = broadcastId;
    }

}
