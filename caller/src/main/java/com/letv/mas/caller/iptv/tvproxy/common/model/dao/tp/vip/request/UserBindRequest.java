package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstantUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 机卡绑定需求，鉴别该账号是否领取过该机器的机卡绑定套餐（调用用户绑定查询接口）的请求参数封装类。
 * @author yikun
 * @since 2014-08-07
 */
public class UserBindRequest extends DeviceBindCommonRequest {
    private final Logger log = LoggerFactory.getLogger(UserBindRequest.class);

    /**
     * 用户ID，必填
     */
    protected String userid;

    public UserBindRequest(String deviceKey, String mac, Integer deviceType, String userid) {
        super(deviceKey, mac, deviceType);
        this.userid = userid;
        this.init();
    }

    private void init() {
        if (StringUtils.isBlank(this.deviceKey)) {
            this.deviceKey = StringUtils.EMPTY;
        }
    }

    /**
     * 参数校验；暂只对规定了取值范围且由客户端传值的参数进行校验
     * @return boolean；true--参数合法，false--参数非法
     */
    public boolean assertValid() {
        if (this.deviceType == null
                || VipTpConstantUtils.DEVICE_BIND_LETV_APPLICATION_TYPE.getDeviceTypeApplicationById(this.deviceType) == null
                || this.deviceKey == null || StringUtils.isBlank(this.mac)) {
            return false;
        }
        return true;
    }

    @Override
    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("deviceKey", this.getDeviceKey());
        parametersMap.put("mac", this.getMac());
        parametersMap.put("deviceType", this.getDeviceType());
        parametersMap.put("userid", this.userid);
        parametersMap.put("sign", this.getSign());

        return parametersMap;
    }

    public String getSign() {
        // 根据规则sign=md5(deviceKey=**&mac=**&userid=**&deviceType=**&{key})计算出sign
        try {
            StringBuilder signBuilder = new StringBuilder();
            signBuilder.append("deviceKey=").append(this.getDeviceKey()).append("&");
            signBuilder.append("mac=").append(this.getMac()).append("&");
            signBuilder.append("userid=").append(this.getUserid()).append("&");
            signBuilder.append("deviceType=").append(this.getDeviceType()).append("&")
                    .append(VipTpConstant.DEVICE_BIND_SIGN_KEY);
            return MessageDigestUtil.md5(signBuilder.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            log.error("getSign_error", e.getMessage(), e);
        }
        return "";
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
