package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import org.apache.commons.lang.StringUtils;

/**
 * 查询用户与某一场次直播的购买关系请求封装类
 * @author KevinYi
 */
public class UserLiveCommonRequest {

    private String username;
    private Long userid; // 用户ID

    /**
     * 场次id，对应"live/liveProjects"接口中的"screenings"字段，无需拼接"01"，必填;
     * 格式为[频道]{2位数字}[赛事类型]{3位数字}[年份]{4位数字}[轮次]{3位数字}[场次]{4位数字}，共16位
     */
    private String pid;
    private String channel; // 频道，pid的0-1位，共两位
    private String category; // 赛事类型，pid的2-4位，共三位
    private String season; // 年份，pid的5-8位，共四位
    private String turn; // 轮次，pid的9-11位，共三位
    private String game; // 场次，pid的12-15位，共四位

    public UserLiveCommonRequest(String username, Long userid, String pid) {
        this.username = username;
        this.userid = userid;
        this.pid = pid;
        this.init();
    }

    private void init() {
        if (StringUtils.isNotBlank(this.pid) && this.pid.length() == 16) {
            this.channel = this.pid.substring(0, 2);
            this.category = this.pid.substring(2, 5);
            this.season = this.pid.substring(5, 9);
            this.turn = this.pid.substring(9, 12);
            this.game = this.pid.substring(12);
        }
    }

    /**
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public Map<String, Object> getParametersMap() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("userid", this.userid);
        parametersMap.put("channel", this.channel);
        parametersMap.put("category", this.category);
        parametersMap.put("season", this.season);
        parametersMap.put("turn", this.turn);
        parametersMap.put("game", this.game);
        parametersMap.put("sign", this.getSign());

        return parametersMap;
    }

    /**
     * sign生成规则 sign_key= f8da39f11dbdafc03efa1ad0250c9ae6，
     * MD5(userid + channel+category+season+sign_key)
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public String getSign() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuilder sign = new StringBuilder();

        sign.append(this.userid).append(this.channel).append(this.category).append(this.season)
                .append(VipTpConstant.LIVE_CHECK_SIGN_KEY);
        return MessageDigestUtil.md5(sign.toString().getBytes(CommonConstants.UTF8));
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

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSeason() {
        return this.season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getTurn() {
        return this.turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getGame() {
        return this.game;
    }

    public void setGame(String game) {
        this.game = game;
    }

}
