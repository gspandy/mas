package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request;

import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BalanceQueryRequest {
    private static final Logger LOG = LoggerFactory.getLogger(BalanceQueryRequest.class);

    private String username;
    private Calendar startTime;
    private Calendar endTime;
    private String queryType;
    private Long userId;

    public BalanceQueryRequest() {
    }

    public BalanceQueryRequest(String username, Long userId, String queryType) {
        this.username = username;
        this.userId = userId;
        this.queryType = queryType;
    }

    public BalanceQueryRequest(String username, Calendar startTime, Calendar endTime, String queryType) {
        this.username = username;
        this.startTime = startTime;
        this.endTime = endTime;
        this.queryType = queryType;
    }

    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("userid", this.userId);
        parametersMap.put("origin", UserTpConstant.BALANCE_QUERY_DEFAULT_ORIGIN);
        parametersMap.put("auth", this.getAuth());

        return parametersMap;
    }

    private String getAuth() {
        try {
            return MessageDigestUtil.md5((VipTpConstant.BOSS_API_ZHIFU_SIGN_KEY + this.userId).toString().getBytes(
                    Charset.forName("UTF-8")));
        } catch (Exception e) {
            LOG.error("BalanceQueryRequest#getAuth:", e);
            return "";
        }
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Calendar getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public String getQueryType() {
        return this.queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
