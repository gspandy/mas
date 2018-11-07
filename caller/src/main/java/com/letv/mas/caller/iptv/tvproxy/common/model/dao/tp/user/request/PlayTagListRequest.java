package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取播放单,追剧,收藏; 调用第三方接口的参数列表
 * @author dunhongqin
 */
public class PlayTagListRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户token
     */
    private String token;

    /**
     * 第几页数据
     */
    private Integer page;

    /**
     * 每页数据大小
     */
    private Integer pagesize;

    /**
     * 返回结果类型，1--合并追剧收藏之后的数据
     */
    private Integer isFull;

    /**
     * 播控方
     */
    private Integer broadcastId;

    public PlayTagListRequest() {
        super();
    }

    public PlayTagListRequest(String username, String token, Integer page, Integer pagesize, Integer isFull,
            Integer broadcastId) {
        super();
        this.username = username;
        this.token = token;
        this.page = page;
        this.pagesize = pagesize;
        this.isFull = isFull;
        this.broadcastId = broadcastId;
    }

    /**
     * 获取参数列表
     * @return
     */
    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("sso_tk", this.token);
        parametersMap.put("page", this.page);
        parametersMap.put("pagesize", this.pagesize);

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

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPagesize() {
        return this.pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Integer getIsFull() {
        return this.isFull;
    }

    public void setIsFull(Integer isFull) {
        this.isFull = isFull;
    }

    public Integer getBroadcastId() {
        return this.broadcastId;
    }

    public void setBroadcastId(Integer broadcastId) {
        this.broadcastId = broadcastId;
    }
}
