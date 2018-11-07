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
 * 领取超级手机赠送机卡绑定TV会员请求封装类
 * @author
 * @since Mar 29, 2015
 */
public class ReceivePresentDeviceBindRequest extends PresentDeviceBindCommonRequest {

    /**
     * 要领取的赠送机卡时长id
     */
    private String presentId;

    public ReceivePresentDeviceBindRequest(String username, String userId, String presentDeviceKey,
            String presentDeviceInfo, Integer presentType, String presentId) {
        super(username, userId, presentDeviceKey, presentDeviceInfo, presentType);
        this.presentId = presentId;
    }

    /**
     * 参数校验
     * @return boolean；true--参数合法，false--参数非法
     */
    public boolean assertValid() {
        if (this.presentDeviceKey == null || StringUtils.isBlank(this.presentDeviceInfo)
                || StringUtils.isBlank(this.presentId)) {
            return false;
        }
        return true;
    }

    /**
     * 从当前对象中组装出参数Map，供调用第三方接口是作为URL参数传入
     * @return Map<String, Object>
     *         参数map，key--String，根据第三方接口调用要求，各参数key分别固定；
     *         value--Object，对应参数
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @Override
    public Map<String, Object> getParametersMap() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Map<String, Object> parametersMap = new HashMap<String, Object>(6);

        parametersMap.put("userId", this.userid);
        parametersMap.put("presentType", this.presentType);
        parametersMap.put("presentDeviceKey", this.presentDeviceKey);
        parametersMap.put("presentDeviceInfo", this.presentDeviceInfo);
        parametersMap.put("id", this.presentId);
        parametersMap.put("sign", this.getSign());

        return parametersMap;
    }

    public String getSign() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 根据规则sign=md5(presentDeviceKey=**&mac=**&userid=**&presentType=**&id=**&{key})
        // 计算出sign
        StringBuilder signBuilder = new StringBuilder();
        signBuilder.append("presentDeviceKey=").append(this.presentDeviceKey).append("&").append("mac=")
                .append(this.presentDeviceInfo).append("&").append("userId=").append(this.userid).append("&")
                .append("presentType=").append(this.presentType).append("&").append("id=").append(this.presentId)
                .append("&").append(VipTpConstant.DEVICE_BIND_SIGN_KEY);
        return MessageDigestUtil.md5(signBuilder.toString().getBytes(CommonConstants.UTF8));
    }

    public String getPresentId() {
        return this.presentId;
    }

    public void setPresentId(String presentId) {
        this.presentId = presentId;
    }

}
