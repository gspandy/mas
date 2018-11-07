package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstantUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 机卡绑定需求，鉴别该机器是否有未领取的机卡绑定套餐（调用设备绑定查询接口）的请求参数封装类。
 * @author yikun
 * @since 2014-08-07
 */
public class GetDeviceBindRequest extends DeviceBindCommonRequest {

    private final static Logger log = LoggerFactory.getLogger(GetDeviceBindRequest.class);

    /**
     * 签名，用于数字签名验证，根据一定规则生成，必填（不提供set方法）
     */
    private String sign;

    public GetDeviceBindRequest(String deviceKey, String mac, Integer deviceType) {
        super(deviceKey, mac, deviceType);
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

    /**
     * 从当前对象中组装出参数Map，供调用第三方接口是作为URL参数传入
     * @return Map<String, Object>
     *         参数map，key--String，根据第三方接口调用要求，各参数key分别固定；
     *         value--Object，对应参数
     */
    @Override
    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = super.getParametersMap();

        parametersMap.put("sign", this.getSign());

        return parametersMap;
    }

    public String getSign() {
        // 根据规则sign=md5(deviceKey=**&mac=** &deviceType=**&{key})计算出sign
        try {
            StringBuilder signBuilder = new StringBuilder();
            signBuilder.append("deviceKey=").append(this.deviceKey).append("&");
            signBuilder.append("mac=").append(this.mac).append("&");
            signBuilder.append("deviceType=").append(this.deviceType).append("&")
                    .append(VipTpConstant.DEVICE_BIND_SIGN_KEY);
            this.sign = MessageDigestUtil.md5(signBuilder.toString().getBytes(CommonConstants.UTF8));
            return this.sign;
        } catch (Exception e) {
            log.error("getSign_error", e.getMessage(), e);
        }
        return "";
    }

    @Override
    public String getDeviceKey() {
        return this.deviceKey;
    }

    @Override
    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    @Override
    public String getMac() {
        return this.mac;
    }

    @Override
    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public Integer getDeviceType() {
        return this.deviceType;
    }

    @Override
    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

}
