package com.letv.mas.caller.iptv.tvproxy.user.model.dao.tp;

import com.letv.mas.caller.iptv.tvproxy.user.annotation.Iptv;
import com.letv.mas.caller.iptv.tvproxy.user.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.user.constant.VipConstants;
import com.letv.mas.caller.iptv.tvproxy.user.model.bean.DeviceBindConentV2;
import com.letv.mas.caller.iptv.tvproxy.user.model.bean.SubscribeInfo;
import com.letv.mas.caller.iptv.tvproxy.user.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.user.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.user.plugin.SessionCache;
import com.letv.mas.caller.iptv.tvproxy.user.response.BossTpResponse;
import com.letv.mas.caller.iptv.tvproxy.user.response.UserBindTpResponse;
import com.letv.mas.caller.iptv.tvproxy.user.util.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

@Component
@Iptv
public class VipTpDao extends BaseTpDao {

    private final static Logger LOG = LoggerFactory.getLogger(VipTpDao.class);

    /**
     * 获取会员状态时长信息
     * @param terminal
     *            终端类型,见{@link VipConstants.BossTerminalType}
     * @param commonParam
     *            公共参数,其中的userId不能为空
     * @return
     */
    @HystrixCommand(threadPoolKey = "vipThreadPool",fallbackMethod = "getVipInfoFallBack")
    public BossTpResponse<List<SubscribeInfo>> getVipInfo(Integer terminal, CommonParam commonParam) {
        if (StringUtil.isBlank(commonParam.getUserId())) {
            return null;
        }
        BossTpResponse<List<SubscribeInfo>> response = null;
        try {
            String type = "info";
            final long timestamp = System.currentTimeMillis();
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(VipConstants.GET_PACKAGE_INFO_BY_ID()).append("?");
            StringBuilder params = new StringBuilder();
            params.append("type=").append(type);
            VipConstants.Country country = CommonUtil.getCountryInfo(commonParam);
            if (country != null) {
                params.append("&country=").append(country.getCode());
            }
            params.append("&userId=").append(commonParam.getUserId());


            if (existPayPlatform(SessionCache.getSession())) {
                params.append("&terminal=").append(getPayPlatform(SessionCache.getSession()));
            } else {
                params.append("&terminal=").append(terminal);
            }

            if (commonParam.getMac() != null) {
                params.append("&mac=").append(commonParam.getMac());
            }
            if (commonParam.getDeviceKey() != null) {
                params.append("&deviceKey=").append(commonParam.getDeviceKey());
            }
            params.append("&timestamp=").append(timestamp);
            params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
            String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
            params.append("&sign=").append(sign);
            subUrl.append(params);
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            LOG.info("getVipInfo_" + commonParam.getUserId() + "_" + commonParam.getMac() + "|invoke return:[" + result
                    + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new LetvTypeReference<BossTpResponse<List<SubscribeInfo>>>() {
                });
            }
        } catch (Exception e) {
            LOG.error(
                    "getVipInfo_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                            + commonParam.getDeviceKey() + " return error: ", e);
        }
        return response;
    }

    public BossTpResponse<List<SubscribeInfo>> getVipInfoFallBack(Integer terminal, CommonParam commonParam,Throwable e) {
        HystrixUtil.logFallBack("VipTpDao","getVipInfo",VipConstants.GET_PACKAGE_INFO_BY_ID(),e);
        LOG.info(" ---------- queryBalanceFallback ----------- ");
        return null;
    }


    @HystrixCommand(threadPoolKey = "vipThreadPool",fallbackMethod = "getDeviceBindInfoV2FallBack")
    public BossTpResponse<DeviceBindConentV2> getDeviceBindInfoV2(Integer deviceType, CommonParam commonParam) {
        BossTpResponse<DeviceBindConentV2> response = null;
        String logPrefix = "getDeviceBindInfo_" + commonParam.getMac() + "_" + deviceType + "_"
                + commonParam.getDeviceKey();
        try {
            StringBuilder url = new StringBuilder(VipConstants.getBossYuanxianV2DeviceBindBaseUrl());
            StringBuilder params = new StringBuilder();
            params.append("type=queryByDevice");
            params.append("&deviceKey=").append(commonParam.getDeviceKey());
            params.append("&mac=").append(commonParam.getMac());
            params.append("&businessId=").append(this.getBussinessIdV2(commonParam));
            // url.append("&deviceType=").append(deviceType);
            String sign = this.getSign(params.toString(), this.getSignKeyV2(commonParam));
            params.append("&sign=").append(sign);
            url.append(params);
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new LetvTypeReference<BossTpResponse<DeviceBindConentV2>>() {
                });
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error:", e);
        }

        return response;
    }

    public BossTpResponse<DeviceBindConentV2> getDeviceBindInfoV2FallBack(Integer deviceType, CommonParam commonParam,Throwable e) {
        HystrixUtil.logFallBack("VipTpDao","getDeviceBindInfoV2",VipConstants.getBossYuanxianV2DeviceBindBaseUrl(),e);
        LOG.info(" ---------- getDeviceBindInfoV2FallBack ----------- ");
        return null;
    }

    /**
     * get user device bind info
     * @param deviceType
     * @param commonParam
     * @return
     */
    @HystrixCommand(threadPoolKey = "vipThreadPool",fallbackMethod = "getUserBindInfoFallBack")
    public UserBindTpResponse getUserBindInfo(Integer deviceType, CommonParam commonParam) {
        UserBindTpResponse response = null;
        String logPrefix = "getUserBindInfo_" + commonParam.getMac() + "_" + commonParam.getDeviceKey() + "_"
                + commonParam.getUserId();
        StringBuilder url = new StringBuilder(VipConstants.getVipDeviceBindInfoGetUrl());
        url.append("type=").append("queryByUid");
        url.append("&userid=").append(commonParam.getUserId());
        url.append("&deviceKey=").append(commonParam.getDeviceKey());
        url.append("&mac=").append(commonParam.getMac());
        url.append("&deviceType=").append(deviceType);
        url.append("&sign=").append(
                this.getUserBindSign(commonParam.getUserId(), commonParam.getDeviceKey(), commonParam.getMac(),
                        deviceType));
        try {
            // http://yuanxian.letv.com/letv/deviceBind.ldo?type=queryByUid&userid={userid}&deviceKey={deviceKey}&mac={mac}&deviceType={deviceType}&sign={sign}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            LOG.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                response = objectMapper.readValue(result, UserBindTpResponse.class);
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    public UserBindTpResponse getUserBindInfoFallBack(Integer deviceType, CommonParam commonParam,Throwable e) {
        HystrixUtil.logFallBack("VipTpDao","getUserBindInfo",VipConstants.getVipDeviceBindInfoGetUrl(),e);
        LOG.info(" ---------- getUserBindInfoFallBack ----------- ");
        return null;
    }

    private String getUserBindSign(String userId, String deviceKey, String mac, Integer deviceType) {
        // 根据规则sign=md5(deviceKey=**&mac=**&userid=**&deviceType=**&{key})计算出sign
        try {
            StringBuilder signBuilder = new StringBuilder();
            signBuilder.append("deviceKey=").append(deviceKey).append("&");
            signBuilder.append("mac=").append(mac).append("&");
            signBuilder.append("userid=").append(userId).append("&");
            signBuilder.append("deviceType=").append(deviceType).append("&").append(VipConstants.DEVICE_BIND_SIGN_KEY);
            return MessageDigestUtil.md5(signBuilder.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            LOG.error("getUserBindSign_error", e.getMessage(), e);
        }
        return "";
    }

    public static boolean existPayPlatform(SessionCache sessionCache) {
        if (null == sessionCache) {
            return false;
        }
        Object obj = sessionCache.getCommObj("p_devType");
        if (null != obj && obj instanceof String) {
            return true;
        } else {
            return false;
        }
    }
    public static String getPayPlatform(SessionCache sessionCache) {
        String platform = "";
        if (null == sessionCache) {
            return platform;
        }
        Object obj = sessionCache.getCommObj("p_devType");
        if (null != obj && obj instanceof String) {
            platform = getPayPlatform((String) sessionCache.getCommObj("p_devType"));
        }
        return platform;
    }

    public static String getPayPlatform(String devType) {
        String platform = "";
        CommonConstants.PAY_PLATFORM_TYPE pay_platform_type = CommonConstants.PAY_PLATFORM_TYPE.getByDevType(devType);
        if (null != pay_platform_type) {
            platform = pay_platform_type.getPlatform();
        }
        return platform;
    }

    /**
     * 获得签名
     * @param plainText
     * @param signKey
     *            签名秘钥
     * @return
     */
    protected String getSign(String plainText, String signKey) {
        if (plainText == null) {
            return "";
        }
        String[] text = plainText.split("&");
        Arrays.sort(text); // 对所有参数按参数名的字典升序排序
        String key_value = "";
        for (String str : text) {
            if (str == null || str.indexOf('=') <= 0 || str.indexOf('=') == str.length() - 1) { // 过滤掉'='左右两侧为空的数据
                continue;
            }
            key_value += str + "&";
        }
        String md5str = key_value + "key=" + signKey;// 拼接key值
        return MD5Util.md5(md5str);
    }

    protected Integer getBussinessIdV2(CommonParam commonParam) {
        VipConstants.BussinessIdAndSignKey bussinessIdAndSignKey = VipConstants.BussinessIdAndSignKey
                .getBussinessIdAndSignKey(commonParam);
        if (bussinessIdAndSignKey != null) {
            return bussinessIdAndSignKey.getBussinessId();
        }
        return null;
    }

    protected String getSignKeyV2(CommonParam commonParam) {
        VipConstants.BussinessIdAndSignKey bussinessIdAndSignKey = VipConstants.BussinessIdAndSignKey
                .getBussinessIdAndSignKey(commonParam);
        if (bussinessIdAndSignKey != null) {
            return bussinessIdAndSignKey.getSignKey();
        }
        return null;
    }

}
