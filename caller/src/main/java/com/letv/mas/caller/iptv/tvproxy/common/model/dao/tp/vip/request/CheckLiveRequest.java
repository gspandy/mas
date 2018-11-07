package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import org.apache.commons.lang.StringUtils;

/**
 * 单场直播鉴权请求封装类
 * @author KevinYi
 */
public class CheckLiveRequest {

    private String username;
    private String loginTime;// 登录时间，用于在登录情况下进行异地登录校验
    private Long userid; // 用户ID
    private String pid; // 直播id，对应"live/liveProjects"接口中的"id"字段，无需拼接"01"，必填
    private String liveid; // 场次id，对应"live/liveProjects"接口中的"screenings"字段，必填
    private String from; // 请求来源类型，必填，PC--加入试看以后的播放器、M站，play--PC、M站，tv--电视，TV--加入试看之后的TV端，mobile--手机，mac--第三方SDK-mac，iphone--移动端加入了试看
    private String streamId; // 流Id，必填，会影响鉴权结果uinfo的生成，不传则不生成uinfo
    private String splatId; // 子平台ID，必填
    private Integer isstart; // 直播是否开始，0--未开始，1--开始，非必填
    private String callback; // Callback接口名，非必填，暂不使用，传null

    /**
     * 移动端、tv为mac地址；pc为flash Ic
     */
    private String flag;

    /**
     * 机卡绑定设备key
     */
    private String deviceKey;

    /**
     * 会员加速终端termid，终端ID。0--未知，1--PC端，2--移动端（移动设备），3--盒端（各种型号盒子），4--电视（超级电视，海信电视
     * ）
     */
    private String termid;

    public CheckLiveRequest(String username, Long userid, String pid, String liveid, String streamId, String splatId,
            Integer isstart, String callback, String mac) {
        super();
        this.username = username;
        this.userid = userid;
        this.pid = pid;
        this.liveid = liveid;
        this.streamId = streamId;
        this.splatId = splatId;
        this.isstart = isstart;
        this.callback = callback;
        this.flag = mac;
        this.init();
    }

    private void init() {
        if (this.isstart == null || (0 != this.isstart && 1 != this.isstart)) {
            this.isstart = 0;
        }
    }

    public Map<String, Object> getParametersMap() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("pid", this.pid);
        // 根据boss校验规则，live应为16位，这里未做校验
        parametersMap.put("liveid", this.liveid);
        parametersMap.put("from", VipTpConstant.LIVE_CHECK_FROM_TV_ADD_TRYPLAY);
        parametersMap.put("streamId", this.streamId);
        parametersMap.put("splatId", this.splatId);
        parametersMap.put("userId", this.userid);
        parametersMap.put("isstart", this.isstart);
        parametersMap.put("callback", this.callback);
        parametersMap.put("flag", this.flag);
        parametersMap.put("mac", this.flag);
        parametersMap.put("deviceKey", StringUtils.trimToEmpty(this.deviceKey));
        parametersMap.put("termid", this.termid);
        parametersMap.put("sign", this.getSign());

        return parametersMap;
    }

    /**
     * 签名，签名规则：sign_key=f8da39f11dbdafc03efa1ad0250c9ae6，
     * sign=MD5(liveid+sign_key+userId)
     */
    public String getSign() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuilder sign = new StringBuilder();
        if (this.userid == null || this.userid < 0) {
            sign.append(this.liveid).append(VipTpConstant.LIVE_CHECK_SIGN_KEY);
        } else {
            sign.append(this.liveid).append(VipTpConstant.LIVE_CHECK_SIGN_KEY).append(this.userid);
        }
        return MessageDigestUtil.md5(sign.toString().getBytes(CommonConstants.UTF8));
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getLiveid() {
        return this.liveid;
    }

    public void setLiveid(String liveid) {
        this.liveid = liveid;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getStreamId() {
        return this.streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getSplatId() {
        return this.splatId;
    }

    public void setSplatId(String splatId) {
        this.splatId = splatId;
    }

    public Integer getIsstart() {
        return this.isstart;
    }

    public void setIsstart(Integer isstart) {
        this.isstart = isstart;
    }

    public String getCallback() {
        return this.callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getFlag() {
        return this.flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTermid() {
        return this.termid;
    }

    public void setTermid(String termid) {
        this.termid = termid;
    }

    public String getDeviceKey() {
        return this.deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    /**
     * 直播鉴权参数校验,直播id为空，场次id为空，mac地址为空，子平台id为空
     * @return
     */
    public int assertValid() {
        if (StringUtils.isBlank(this.pid)) {
            return VipMsgCodeConstant.VIP_CHECK_LIVE_REQUEST_PID_EMPTY;
        } else if (StringUtils.isBlank(this.liveid)) {
            return VipMsgCodeConstant.VIP_CHECK_LIVE_REQUEST_LIVEID_EMPTY;
        } else if (StringUtils.isBlank(this.flag)) {
            return VipMsgCodeConstant.REQUEST_MAC_EMPTY;
        } else if (StringUtils.isBlank(this.splatId)) {
            return VipMsgCodeConstant.VIP_CHECK_LIVE_REQUEST_SPLATID_EMPTY;
        }
        return VipMsgCodeConstant.REQUEST_SUCCESS;
    }
}
