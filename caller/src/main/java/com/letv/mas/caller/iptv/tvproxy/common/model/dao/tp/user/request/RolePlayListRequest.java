package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request;

import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RolePlayListRequest {
    private final Logger log = LoggerFactory.getLogger(RolePlayListRequest.class);

    private Long userid;
    private Long roleid;
    private String albumid;
    private String username;
    private String token;
    private String mac;
    private Integer page;
    private Integer pageSize;

    public RolePlayListRequest(Long userid, Long roleid, String token, String mac, String albumid) {
        this.userid = userid;
        this.roleid = roleid;
        this.token = token;
        this.mac = mac;
        this.albumid = albumid;
    }

    public RolePlayListRequest(Long userid, Long roleid, String token, Integer page, Integer pageSize) {
        this.userid = userid;
        this.roleid = roleid;
        this.token = token;
        this.page = page;
        if (pageSize != null && pageSize.intValue() < 1) {
            this.pageSize = 1;
        } else {
            this.pageSize = pageSize;
        }
    }

    public Integer assertValid() {
        if (StringUtils.isBlank(String.valueOf(this.userid))) {
            return UserMsgCodeConstant.REQUEST_USERINFO_ERROR;
        }
        /*
         * if (StringUtils.isBlank(this.token)) {
         * return UserMsgCodeConstant.USER_REQUEST_DATA_TOKEN_ISBANK;
         * }
         */
        return UserMsgCodeConstant.REQUEST_SUCCESS;
    }

    public String getSign() {
        StringBuilder sign = new StringBuilder();
        sign.append(this.userid).append(String.format(("%0" + 8 + "d"), this.roleid));
        sign.append(UserTpConstant.EDIT_ROLE_PLAYLIST_SIGN_KEY);
        try {
            return MessageDigestUtil.md5(sign.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            log.error("getSign_error", e.getMessage(), e);
        }
        return "";
    }

    /**
     * 编辑播单传参
     * @return
     */
    public Map<String, Object> getEditParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();
        parametersMap.put("leset_id", this.userid + String.format(("%0" + 8 + "d"), this.roleid));
        parametersMap.put("sso_tk", this.token);
        parametersMap.put("roleid", this.roleid == null ? 1 : this.roleid);
        parametersMap.put("title", "");
        parametersMap.put("desc", "");
        parametersMap.put("sign", this.getSign());
        parametersMap.put("pid", this.albumid);

        return parametersMap;
    }

    /**
     * 删除播单传参
     * @return
     */
    public Map<String, Object> getDelParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();
        parametersMap.put("leset_id", this.userid + String.format(("%0" + 8 + "d"), this.roleid));
        parametersMap.put("sso_tk", this.token);
        parametersMap.put("pid", this.albumid);

        return parametersMap;
    }

    /**
     * 删除播单传参
     * @return
     */
    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();
        parametersMap.put("leset_id", this.userid + String.format(("%0" + 8 + "d"), this.roleid));
        parametersMap.put("sso_tk", this.token);
        parametersMap.put("page", this.page - 1);// 用户中心播单页从0开始
        parametersMap.put("pagecount", this.pageSize);

        return parametersMap;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getRoleid() {
        return this.roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAlbumid() {
        return this.albumid;
    }

    public void setAlbumid(String albumid) {
        this.albumid = albumid;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getPreFixLog() {
        return "[userid=" + this.userid + ", roleid=" + this.roleid + ", token=" + this.token + ", mac=" + this.mac
                + "]";
    }

}
