package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request;

import java.util.HashMap;
import java.util.Map;

/**
 * 更新播放时间调用第三方接口参数
 * @author dunhongqin
 */
public class UpdatePlaytimeRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户中心id
     */
    private Long userid;
    /**
     * 角色id
     */
    private Long roleid;
    /**
     * 用户登录token
     */
    private String token;

    /**
     * 是频道ID
     */
    private Integer categoryId;

    /**
     * 专辑ID
     */
    private Long albumid;

    /**
     * 视频ID
     */
    private Long videoid;

    /**
     * 下一集视频的ID
     */
    private Long nextVideoid;

    /**
     * 视频类型 （1ptv 2vip）
     */
    @Deprecated
    private Integer vtype;

    /**
     * 视频来源 （1web 2mobile 3pad 4tv 5pc桌面）
     */
    private Integer from;

    /**
     * 是观看时间点（0开始播放 -1播放完闭）
     */
    private Long htime;

    /**
     * 播控方
     */
    private Integer broadcastId;

    /**
     * 上传设备的型号，通过terminalBrand和terminalSeries获取乐视自由设备型号系列
     */
    private String product;

    /**
     * 用户IP
     */
    private String clientIp;

    public UpdatePlaytimeRequest() {
        super();
    }

    public UpdatePlaytimeRequest(String username, Long userid, String token, Long albumid, Long videoid,
            Integer categoryId, Long nextVideoId, Long htime, Integer broadcastId, String product, Long roleid) {
        super();
        this.username = username;
        this.userid = userid;
        this.token = token;
        this.albumid = albumid;
        this.videoid = videoid;
        this.categoryId = categoryId;
        this.nextVideoid = nextVideoId;
        this.htime = htime;
        this.product = product;
        this.roleid = roleid;
    }

    /**
     * 获取参数列表
     * @return
     */
    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("cid", this.categoryId);
        parametersMap.put("pid", this.albumid);
        parametersMap.put("vid", this.videoid);
        parametersMap.put("nvid", this.nextVideoid);
        // parametersMap.put("uid", this.userid);
        // parametersMap.put("vtype", this.vtype);
        parametersMap.put("from", this.from);
        parametersMap.put("htime", this.htime);
        parametersMap.put("product", this.product);
        parametersMap.put("sso_tk", this.token);
        parametersMap.put("rid", this.roleid == null ? 0 : this.roleid);// 儿童模式添加角色id,默认0
        parametersMap.put("clientIp", this.clientIp);

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

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public Long getNextVideoid() {
        return this.nextVideoid;
    }

    public void setNextVideoid(Long nextVideoid) {
        this.nextVideoid = nextVideoid;
    }

    public Integer getVtype() {
        return this.vtype;
    }

    public void setVtype(Integer vtype) {
        this.vtype = vtype;
    }

    public Integer getFrom() {
        return this.from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Long getHtime() {
        return this.htime;
    }

    public void setHtime(Long htime) {
        this.htime = htime;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProduct() {
        return this.product;
    }

    public void setProduct(String product) {
        this.product = product;
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

    public String getClientIp() {
        return this.clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

}
