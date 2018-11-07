package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;

/**
 * 获取用户账户乐点余额请求封装类
 * @author KevinYi
 */
public class GetLetvPointBalanceRequest {

    private String username;
    private String userid;

    // private String queryType;

    public GetLetvPointBalanceRequest(String username, String userid) {
        super();
        this.username = username;
        this.userid = userid;
        // this.queryType = this.queryType;
    }

    public GetLetvPointBalanceRequest(String username, String userid, String queryType) {
        super();
        this.username = username;
        this.userid = userid;
        // this.queryType = queryType;
    }

    public Map<String, Object> getParametersMap() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("userid", this.userid);
        // parametersMap.put("origin",
        // VipTpConstant.BOSS_API_ZHIFU_LEYV_POINT_BALANCE_QUERY_ORIGIN_TV);
        parametersMap.put("auth", this.getAuth());

        return parametersMap;
    }

    /**
     * 生成zhifu接口签名
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private String getAuth() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return MessageDigestUtil.md5((VipTpConstant.BOSS_API_ZHIFU_SIGN_KEY + this.userid).toString().getBytes(
                Charset.forName("UTF-8")));
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
