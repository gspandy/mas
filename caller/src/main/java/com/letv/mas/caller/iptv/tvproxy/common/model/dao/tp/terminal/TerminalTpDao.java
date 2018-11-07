package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.terminal;

import java.net.URI;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.terminal.response.BoxConfigResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.terminal.response.TVUpgradeInfoResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.terminal.response.UpgradeTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component("v2.TerminalTpDao")
public class TerminalTpDao extends BaseTpDao {
    private final static Logger log = LoggerFactory.getLogger(TerminalTpDao.class);

    /**
     * 获取升级信息
     * @param mac
     * @return
     */
    public TVUpgradeInfoResponse getTVUpgradeInfo(String app_code, String appversioncode, String brand, String devtype,
                                                  String platform, String channelCode) {
        String url = ApplicationUtils.get(ApplicationConstants.IPTV_TERMINAL_VERSION_TVUPGRADE);
        // String url = this.TVUPGRADE;
        // url =
        // "http://test.push.platform.letv.com/tvupgrade?app_code=letv&appversioncode=1.0.0&brand=hisense&devtype=XT710&platform=android";
        String result = this.restTemplate.getForObject(url, String.class, app_code, appversioncode, brand, devtype,
                platform, channelCode);
        TVUpgradeInfoResponse tTVUpgradeInfoDto = null;
        try {
            if (StringUtil.isNotBlank(result)) {
                tTVUpgradeInfoDto = objectMapper.readValue(result, TVUpgradeInfoResponse.class);
            }
            return tTVUpgradeInfoDto;
        } catch (Exception e) {
            log.error("getTVUpgradeInfo.error:" + result);
            return null;
        }
    }

    /**
     * 获取海外机器组的id
     * @param mac
     * @return
     */
    public String getBoxConfig(String mac) {
        String url = ApplicationUtils.get(ApplicationConstants.IPTV_TERMINAL_BOXCONFIG_GROUPINFO);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("mac", mac);
        String result = this.restTemplate.getForObject(url, String.class, mac);
        BoxConfigResponse conf = null;
        try {
            if (StringUtil.isNotBlank(result)) {
                conf = objectMapper.readValue(result, BoxConfigResponse.class);
            }
            if (conf == null || conf.getGroupid() == null || conf.getGroupid().equals("null")) {
                return "";
            }
            return conf.getGroupid();
        } catch (Exception e) {
            log.error("getBoxConfig.error:" + result);
            return "";
        }
    }

    /**
     * 获取支持4K的设备系列列表
     * @return
     */
    public Set<String> getSupport4KDevices() {
        Set<String> devices = null;
        String logPrefix = "getSupport4KDevices_";
        try {
            String result = this.restTemplate.getForObject(
                    TerminalCommonConstant.getSupport4kDevicesConfigFileUrl(null), String.class);
            log.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                String[] deviceArray = result.split(",");
                if (deviceArray != null && deviceArray.length > 0) {
                    devices = new HashSet<String>();
                    for (String device : deviceArray) {
                        device = StringUtils.trimToNull(device);
                        if (device != null) {
                            devices.add(device);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }
        return devices;
    }

    /**
     * get upgrade info from upgrade service
     * @param versionCode
     *            is necessary parameter
     * @param commonParam
     *            the common parameter
     * @return
     */
    public UpgradeTpResponse getUpgradeInfo(Integer versionCode, String versionName, String terminalUnique,
                                            CommonParam commonParam) {
        UpgradeTpResponse response = null;
        try {
            StringBuilder url = new StringBuilder(TerminalCommonConstant.TERMINAL_UPGRADE_SERVICE_URL);

            // 2016-08-29,美国lecom需求新增，背景是，lecom尚未完成开发时，需要提供给rom一个预装版本，
            // 客户端使用原美国行货(terminalApplication=letv_us)基础上，通过添加realTA为le得来表
            // 示作为le版本使用；服务端使用该参数解决升级问题，而其他接口还需出letv_us数据
            String terminalApplication = StringUtils.isNotEmpty(commonParam.getRealTA()) ? commonParam.getRealTA()
                    : commonParam.getTerminalApplication();

            url.append("?terminalApplication=").append(terminalApplication);
            url.append("&terminalBrand=").append(commonParam.getTerminalBrand());
            url.append("&bsChannel=").append(commonParam.getBsChannel());
            // 对可能包含空格的参数进行URL编码
            url.append("&terminalSeries=").append(URLEncoder.encode(commonParam.getTerminalSeries(), "UTF-8"));
            if (versionCode == null) {
                url.append("&versionCode=");
                url.append("&versionName=").append(versionName);
            } else {
                url.append("&versionCode=").append(versionCode);
                url.append("&versionName=").append(versionName);
            }
            String mac = commonParam.getMac();
            // get real mac value because some app use different parameter
            if (StringUtil.isBlank(mac)) {
                if (StringUtil.isBlank(terminalUnique)) {
                    mac = commonParam.getDevId();
                } else {
                    mac = terminalUnique;
                }
            }
            url.append("&devId=").append(StringUtils.trimToEmpty(mac));
            url.append("&langcode=").append(commonParam.getLangcode());
            url.append("&wcode=").append(commonParam.getWcode());
            url.append("&salesArea=").append(commonParam.getSalesArea());
            url.append("&countryArea=").append(commonParam.getCountryArea());
            url.append("&token=").append(commonParam.getUserToken());
            url.append("&uid=").append(commonParam.getUserId());

            /*
             * 2016-09-06，测试发现，针对诸如terminalSeries=Letv U4这样的参数带空格的情况，使用Spring
             * RestTemplate.getForObject(String url, Class<T> responseType,
             * Object... urlVariables)等以String url作为参数的API，在底层会对特殊字符（诸如" "
             * 等）进行二进制编码（如" "
             * 被编码成"%2B"），而不是期望的HTTP URL 编码规则（如" "
             * 被编码成"+"或"%20"），参见UriComponents.Type.QUERY_PARAM,
             * UriComponents.encode(String encoding)方法；
             * 采用
             * url.append("&terminalSeries=").append(commonParam.
             * getTerminalSeries());
             * URI uri = URI.create(StringUtils.replace(url.toString(), " ",
             * "%20"));
             * 或
             * url.append("&terminalSeries=").append(URLEncoder.encode(
             * commonParam.getTerminalSeries(), "UTF-8"));
             * URI uri = URI.create(url.toString());
             * 的组合，使用RestTemplate.getForObject(URI url, Class<T> responseType,
             * Object... urlVariables)接口可以规避Spring默认的二进制编码，完成正常请求
             */
            URI uri = URI.create(url.toString());
            String result = this.restTemplate.getForObject(uri, String.class);

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, UpgradeTpResponse.class);
            }
        } catch (Exception e) {
            log.error("getUpgradeInfo_" + commonParam.getTerminalApplication() + "_" + commonParam.getTerminalSeries()
                    + "_" + commonParam.getMac() + "_" + versionCode + " upgrade error:" + e.getMessage());
        }
        return response;
    }
}
