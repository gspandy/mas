package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp;

import com.letv.mas.caller.iptv.tvproxy.common.model.bean.SubscribeInfoV2;
import com.letv.mas.caller.iptv.tvproxy.common.constant.UserConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.VipConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.response.BalanceQueryResultResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.response.LePayTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.response.LetvUserResponse;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserTpDao extends BaseTpDao {

    private final Logger log = LoggerFactory.getLogger(UserTpDao.class);

    @HystrixCommand(threadPoolKey = "userThreadPool",fallbackMethod = "getVipInfoV2Fallback")
    public LePayTpResponse<List<SubscribeInfoV2>> getVipInfoV2(String vendor_no_product_id, CommonParam commonParam) {
        if (StringUtil.isBlank(commonParam.getUserId())) {
            return null;
        }
        LePayTpResponse<List<SubscribeInfoV2>> response = null;

        try {
            String type = "info";
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(VipConstants.getLepayVipGettimeUrl());

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("user_id", commonParam.getUserId());
            if (StringUtil.isNotBlank(vendor_no_product_id)) {
                params.put("vendor_no_product_id", vendor_no_product_id);
            }
            if (StringUtil.isNotBlank(commonParam.getMac())) {
                params.put("mac", commonParam.getMac());
            }
            if (StringUtil.isNotBlank(commonParam.getDeviceKey())) {
                params.put("device_key", commonParam.getDeviceKey());
            }
            params.put("business_id", VipConstants.BussinessIdAndSignKey.LEPAY_CIBN.getBussinessId());
            String sign = this.getSign(HttpClientUtil.getUrlParamsByMap(params), VipConstants.BussinessIdAndSignKey.LEPAY_CIBN.getSignKey());
            params.put("sign", sign);

            //String result = this.restTemplate.postForObject(subUrl.toString(), params, String.class, params);
            subUrl.append("?").append(HttpClientUtil.getUrlParamsByMap(params));
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            log.info("getVipInfoV2_" + commonParam.getUserId() + "_" + commonParam.getMac()
                    + "|invoke return:[" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new LetvTypeReference<LePayTpResponse<List<SubscribeInfoV2>>>() {
                });
            }

        } catch (Exception e) {
            log.error(
                    "getVipInfoV2_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_"
                            + commonParam.getDeviceKey() + " return error: ", e);
        }
        return response;
    }

    public LePayTpResponse<List<SubscribeInfoV2>> getVipInfoV2Fallback(String vendor_no_product_id, CommonParam commonParam, Throwable e) {
        HystrixUtil.logFallBack("UserTpDao","getVipInfoV2",UserConstants.getUserInfoGetUrl(),e);
        log.info(" ---------- getVipInfoV2Fallback ----------- ");
        return null;
    }

    /**
     * get user info by username
     * @param username
     * @return
     */
    @HystrixCommand(threadPoolKey = "userThreadPool",fallbackMethod = "getUserInfoFallback")
    public LetvUserResponse getUserInfo(String username) {
        LetvUserResponse response = null;
        String logPrefix = "getUserInfo_" + username;

        try {
            String result = this.restTemplate.getForObject(UserConstants.getUserInfoGetUrl(), String.class, username);
            log.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, LetvUserResponse.class);
            }
        } catch (Exception e) {
            log.error(logPrefix, e);
        }
        return response;
    }

    public LetvUserResponse getUserInfoFallback(String username,Throwable e) {
        HystrixUtil.logFallBack("UserTpDao","getUserInfo",UserConstants.getUserInfoGetUrl(),e);
        log.info(" ---------- getUserInfoFallback ----------- ");
        return null;
    }

    /**
     * get user lepoint balance
     * @param commonParam
     * @return
     */
    @HystrixCommand(threadPoolKey = "userThreadPool",fallbackMethod = "queryBalanceFallback")
    public BalanceQueryResultResponse queryBalance(CommonParam commonParam) {
        String logPrefix = "queryBalance_" + commonParam.getMac() + "_" + commonParam.getUserId();
        BalanceQueryResultResponse response = null;

        try {
            StringBuilder url = new StringBuilder(UserConstants.getUserLepointBalanceGetUrl());
            url.append(commonParam.getUserId()).append("?");
            // from tv terminal
            url.append("origin=").append(UserConstants.BALANCE_QUERY_DEFAULT_ORIGIN);
            // generate md5 sign by userId
            url.append("&auth=").append(this.getBalanceQueryAuth(commonParam.getUserId()));

            // http://api.zhifu.letv.com/querylepoint/{userid}?origin={origin}&auth={auth}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            this.log.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, BalanceQueryResultResponse.class);
            }
        } catch (Exception e) {
            this.log.error(logPrefix + " [error]: ", e);
        }

        return response;
    }

    public BalanceQueryResultResponse queryBalanceFallback(CommonParam commonParam,Throwable e) {
        HystrixUtil.logFallBack("UserTpDao","queryBalance",UserConstants.getUserLepointBalanceGetUrl(),e);
        log.info(" ---------- queryBalanceFallback ----------- ");
        return null;
    }

    private String getBalanceQueryAuth(String userId) {
        try {
            return MessageDigestUtil.md5((VipConstants.BOSS_API_ZHIFU_SIGN_KEY + userId).toString().getBytes(
                    Charset.forName("UTF-8")));
        } catch (Exception e) {
            log.error("getBalanceQueryAuth_error", e.getMessage(), e);
        }
        return "";
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

}
