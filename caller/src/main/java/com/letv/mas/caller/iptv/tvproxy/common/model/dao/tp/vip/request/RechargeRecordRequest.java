package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import org.apache.commons.lang.StringUtils;

/**
 * 充值记录请求参数封装类
 * @author yikun
 */
public class RechargeRecordRequest {

    /**
     * 登录账户的用户名（非登录名）
     */
    private String username;

    /**
     * 查询时间间隔，单位：天，即查询day天内的消费记录，如3、7、30、365等
     */
    private Long day;

    /**
     * 用户中心id
     */
    private Long userid;

    public RechargeRecordRequest() {
        super();
    }

    public RechargeRecordRequest(String username, Long day) {
        super();
        this.username = username;
        this.day = day;
    }

    public RechargeRecordRequest(String username, Long day, Long userid) {
        super();
        this.username = username;
        this.day = day;
        this.userid = userid;
    }

    /**
     * 获取参数列表
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public Map<String, Object> getParametersMap() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        // 获取认证
        String auth = MessageDigestUtil.md5((VipTpConstant.BOSS_API_ZHIFU_SIGN_KEY + this.userid).getBytes(Charset
                .forName(CommonConstants.UTF8)));
        parametersMap.put("userid", this.userid);
        parametersMap.put("days", this.day);
        parametersMap.put("origin", "tv");
        parametersMap.put("auth", auth);

        return parametersMap;
    }

    public Long getDay() {
        return this.day;
    }

    public void setDay(Long day) {
        this.day = day;
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

    /**
     * 参数校验
     * @return
     *         <h1>TYPE</h1> int
     *         <h1>RESULT</h1> 用户信息错误，查询时间范围非法，校验通过
     */
    public int assertValid() {
        // 用户id为空
        if ((this.userid == null || this.userid < 0) || StringUtils.isBlank(this.username)) {
            return VipMsgCodeConstant.REQUEST_USERINFO_ERROR;
        }
        if (this.day == null || this.day < 0) {// 查询的时间范围值必须大于0
            return VipMsgCodeConstant.VIP_GET_RECHARGE_RECORD_REQUEST_DAY_ILLEGAL;
        }
        return VipMsgCodeConstant.REQUEST_SUCCESS;
    }
}
