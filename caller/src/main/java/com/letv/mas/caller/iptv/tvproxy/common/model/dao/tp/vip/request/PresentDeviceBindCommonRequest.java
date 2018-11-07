package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


/**
 * 移动领先版--超级手机赠送机卡绑定请求通用封装类
 * @author
 * @since Mar 27, 2015
 */
public class PresentDeviceBindCommonRequest {

    /**
     * 用户名
     */
    protected String username;

    /**
     * 用户id
     */
    protected String userid;

    /**
     * 当前设备mac（电视）或imei（手机）
     */
    protected String presentDeviceInfo;

    /**
     * 领取时长的类型，类似“会员类型”，超级手机传2，TV传9
     */
    protected Integer presentType;

    /**
     * 当前领取设备的key
     */
    protected String presentDeviceKey;

    public PresentDeviceBindCommonRequest(String username, String userid, String presentDeviceKey, String mac,
            Integer presentType) {
        super();
        this.username = username;
        this.userid = userid;
        this.presentDeviceInfo = mac;
        this.presentDeviceKey = presentDeviceKey;
        this.presentType = presentType;
        this.init();
    }

    private void init() {
        // 兼容非机卡绑定机型策略，使用mac作为deviceKey
        if (StringUtils.isBlank(this.presentDeviceKey)) {
            this.presentDeviceKey = StringUtils.EMPTY;
        }
    }

    /**
     * 从当前对象中组装出参数Map，供调用第三方接口是作为URL参数传入
     * @return Map<String, Object>
     *         参数map，key--String，根据第三方接口调用要求，各参数key分别固定；
     *         value--Object，对应参数
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public Map<String, Object> getParametersMap() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Map<String, Object> parametersMap = new HashMap<String, Object>(6);

        parametersMap.put("userId", this.userid);
        parametersMap.put("presentType", this.presentType);
        parametersMap.put("presentDeviceKey", this.presentDeviceKey);
        parametersMap.put("presentDeviceInfo", this.presentDeviceInfo);

        return parametersMap;
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

    public String getPresentDeviceInfo() {
        return this.presentDeviceInfo;
    }

    public void setPresentDeviceInfo(String presentDeviceInfo) {
        this.presentDeviceInfo = presentDeviceInfo;
    }

    public Integer getPresentType() {
        return this.presentType;
    }

    public void setPresentType(Integer presentType) {
        this.presentType = presentType;
    }

    public String getPresentDeviceKey() {
        return this.presentDeviceKey;
    }

    public void setPresentDeviceKey(String presentDeviceKey) {
        this.presentDeviceKey = presentDeviceKey;
    }

}
