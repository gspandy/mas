package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request;

import java.util.HashMap;
import java.util.Map;

public class GetPlayLogListRequest {

    /**
     * 账户的用户名
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
     * 当前第几页
     */
    private Integer page;

    /**
     * 每页数据量
     */
    private Integer pagesize;

    /**
     * 查询距今intervalTime天内的播放记录， 如7天,30天等
     */
    private Integer intervalTime;

    /**
     * 播控方
     */
    private Integer broadcastId;
    /**
     * 角色id
     */
    private Long roleid;

    public GetPlayLogListRequest() {
        super();
    }

    public GetPlayLogListRequest(String username, Long userid, String token, Integer page, Integer pageSize,
            Integer intervalTime, Integer broadcastId, Long roleid) {
        super();
        this.username = username;
        this.userid = userid;
        this.token = token;
        this.page = page;
        this.pagesize = pageSize;
        this.intervalTime = intervalTime;
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
        parametersMap.put("page", this.page);
        parametersMap.put("pagesize", this.pagesize);
        parametersMap.put("btime", this.intervalTime);
        parametersMap.put("rid", this.roleid == null ? 0 : this.roleid);// 儿童模式加角色字段默认0
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

    public Integer getIntervalTime() {
        return this.intervalTime;
    }

    public void setIntervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
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
