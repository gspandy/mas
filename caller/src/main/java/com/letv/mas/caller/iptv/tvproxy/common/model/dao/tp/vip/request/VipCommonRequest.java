package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员模块通用request封装类；
 * 暂只封装username，userid;
 * 许多会员模块接口仅用到这些参数，这里统一封装，不再单独命名
 * @author yikun
 */
public class VipCommonRequest {

    /**
     * 登录账户的用户名
     */
    private String username;

    /**
     * 用户中心id
     */
    protected Long userid;

    /**
     * 客户端mac
     */
    private String mac;

    /**
     * 客户端ip，IPV4类型
     */
    private String ip;

    public VipCommonRequest() {
        super();
    }

    public VipCommonRequest(String username, Long userid) {
        super();
        this.username = username;
        this.userid = userid;
    }

    public VipCommonRequest(String username, Long userid, String mac) {
        super();
        this.username = username;
        this.userid = userid;
        this.mac = mac;
    }

    /**
     * 获取参数列表
     * @return
     */
    public Map<String, Object> getParametersMap() throws Exception {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("userid", this.userid);

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

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
