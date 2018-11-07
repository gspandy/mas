package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;

/**
 * 第三方SDK直播鉴权请求结果封装类
 * @author KevinYi
 */
public class TpsdkCheckLiveRequest extends CheckLiveRequest {

    /**
     * 第三方SDK设备mac
     */
    private String mac;

    /**
     * 请求类型，固定传"liveValidate"
     */
    private String type;

    public TpsdkCheckLiveRequest(String username, Long userid, String pid, String liveid, String streamId,
            String splatId, Integer isstart, String callback, String mac) {
        super(username, userid, pid, liveid, streamId, splatId, isstart, callback, mac);
        this.mac = mac;
        this.type = "liveValidate";
    }

    @Override
    public Map<String, Object> getParametersMap() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Map<String, Object> parametersMap = super.getParametersMap();

        parametersMap.put("from", VipTpConstant.LIVE_CHECK_FROM_MAC);
        parametersMap.put("mac", this.mac);
        parametersMap.put("type", "liveValidate");

        return parametersMap;
    }

    @Override
    public String getSign() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuilder sign = new StringBuilder();

        sign.append(this.getLiveid()).append(VipTpConstant.LIVE_CHECK_SIGN_KEY).append(this.mac);
        return MessageDigestUtil.md5(sign.toString().getBytes(CommonConstants.UTF8));
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
