package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.IptvStaticConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class StaticTpDao extends BaseTpDao {

    private final static Logger LOG = LoggerFactory.getLogger(StaticTpDao.class);

    private static final String RMI_SERVER_ADDR = "115.182.93.27";
    private static final int RMI_SERVER_PORT = 21099;
    private static final String RMI_SERVER_NAME = "fileupload";
    private final Random random = new Random();// 随机打印成功

    public Set<String> getHkMacSet() {
        Set<String> macSet = new HashSet<String>();
        try {
            // http://i.static.itv.letv.com/api/hkmaclist/hkmaclist.txt
            String response = this.restTemplate.getForObject(IptvStaticConstant.HK_MAC_WHITE_LIST_URL, String.class);
            if (StringUtil.isNotBlank(response)) {
                String[] macs = response.split("\r\n");
                if (macs != null) {
                    for (String mac : macs) {
                        macSet.add(mac);
                    }
                }
            }
        } catch (Exception e) {
        }
        return macSet;
    }

    public Set<String> getUserIdSet() {
        Set<String> macSet = new HashSet<String>();
        try {
            // http://i.static.itv.letv.com/api/whiteList/userIdList.txt
            String response = this.restTemplate.getForObject(IptvStaticConstant.USERID_WHITE_LIST_URL, String.class);
            if (response != null) {
                // response = response.replaceAll("\r\n|\n| ", ",");
                String[] userIdList = response.split(",");
                if (userIdList != null) {
                    for (String userId : userIdList) {
                        if (StringUtil.isNotBlank(userId)) {
                            macSet.add(userId);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return macSet;
    }

    /**
     * @param fileName
     *            static file name, without File Suffixes(always ".txt")
     * @return
     */
    public Set<String> getPlayMacWhiteList(String fileName) {
        String logPrefix = "getPlayMacWhiteList" + fileName;
        Set<String> macSet = new HashSet<String>();
        try {
            // http://i.static.itv.letv.com//api/whiteList/mac/{fileName}.txt
            String response = this.restTemplate.getForObject(IptvStaticConstant.MAC_WHITE_LIST_BASE_URL + fileName
                    + ".txt", String.class);
            if (StringUtil.isNotBlank(response)) {
                String[] macs = response.split(",");
                if (macs != null) {
                    for (String mac : macs) {
                        mac = StringUtils.trimToNull(mac);
                        if (mac != null) {
                            macSet.add(mac);
                        }
                    }
                }
            } else {
                LOG.info(logPrefix + ": empty data");
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return macSet;
    }

    public Set<String> getPlayMacBlackList() {
        String logPrefix = "getPlayMacBlackList";
        Set<String> macSet = new HashSet<String>();
        try {
            // http://i.static.itv.letv.com/api/conf/video/blacklist.txt
            String response = this.restTemplate.getForObject(IptvStaticConstant.MAC_BLACK_LIST_BASE_URL, String.class);
            if (StringUtil.isNotBlank(response)) {
                String[] macs = response.split("\n");
                if (macs != null) {
                    for (String mac : macs) {
                        mac = StringUtils.trimToNull(mac);
                        if (mac != null) {
                            macSet.add(mac);
                        }
                    }
                }
            } else {
                LOG.info(logPrefix + ": empty data");
            }
        } catch (Exception e) {
            LOG.error(logPrefix + " return error: ", e);
        }
        return macSet;
    }

    /**
     * 根据TV版静态接口url，获取静态文件内容；
     * 该接口可以抽象为读取url对应的json数据;
     * @param url
     * @return
     */
    public String getIptvStaticFileContentByUrl(String url) {
        String logPrefix = "getIptvStaticFileContentByUrl_" + url;
        String result = null;
        if (StringUtils.isNotEmpty(url)) {
            try {
                result = this.restTemplate.getForObject(url, String.class);
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }

        return result;
    }

    /**
     * 解析静态文件内容，返回一个map
     * @param result
     * @return
     */
    public Map<String, String> getStaticFileContent(String url) {
        String logPrefix = "getStaticFileContent_" + url;

        Map<String, String> map = null;
        String result = this.getIptvStaticFileContentByUrl(url);

        if (StringUtil.isNotBlank(result)) {
            map = new HashMap<String, String>();
            try {
                InputStream inputStream = new ByteArrayInputStream(result.getBytes());
                Properties tmp = new Properties();
                tmp.load(inputStream);

                Set<String> keySet = tmp.stringPropertyNames();
                if (!CollectionUtils.isEmpty(keySet)) {
                    for (String key : keySet) {
                        map.put(key, tmp.getProperty(key));
                    }
                }
            } catch (IOException e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }

        return map;
    }

    /*
     * private void publishStaticFile(String fileName, String value) throws
     * Exception {
     * try {
     * String host = RMI_SERVER_ADDR;
     * int port = RMI_SERVER_PORT;
     * String serverName = RMI_SERVER_NAME;
     * // TODO 这种用法不行，要保存链接
     * Registry registry = LocateRegistry.getRegistry(host, port);
     * // 获取文件上传服务
     * FileUploadService fileUploadService = (FileUploadService)
     * registry.lookup(serverName);
     * String result = fileUploadService.upload("api/" + fileName, value, "");
     * if (this.random.nextInt() % 20 == 0) {
     * this.LOG.info("发布静态文件成功.result[" + result + "]");
     * }
     * } catch (Exception e) {
     * this.LOG.error("MONITOR EXCEPTION publish static file Error.fileName=" +
     * "api/" + fileName + ",value="
     * + value, e);
     * throw new Exception("发布静态文件失败");
     * }
     * }
     */

    private boolean executePublishMenu(String url, Object[] paramArray, String path) throws Exception {
        String object = this.restTemplate.getForObject(url, String.class, paramArray);
        CommonListResponse<MenuDto> response = null;
        if (object != null) {
            response = objectMapper.readValue(object, new TypeReference<CommonListResponse<MenuDto>>() {
            });
        }
        if (response != null && response.getResultStatus() != null && response.getResultStatus() == 1
                && response.getData() != null && !response.getData().isEmpty()) {
            List<MenuDto> menuDtos = response.getData();
            if (menuDtos != null && menuDtos.size() > 0) {
                // TODO ligang 20151210 暂时删除
                // this.publishStaticFile(path, object);
                if (this.random.nextInt() % 20 == 0) {
                    this.LOG.info("topMenu sync success:" + url + ",key:" + paramArray);
                }
                return true;
            }
        }
        return false;
    }

    private boolean executePublishAdPic(String url, Object[] paramArray, String path) throws Exception {
        String object = this.restTemplate.getForObject(url, String.class, paramArray);
        CommonResponse<StartupAdResponse> response = null;
        if (object != null) {
            response = objectMapper.readValue(object, new TypeReference<CommonResponse<StartupAdResponse>>() {
            });
        }
        if (response != null && response.getResultStatus() != null && response.getResultStatus() == 1
                && response.getData() != null) {
            StartupAdResponse adResponses = response.getData();
            if (adResponses != null && adResponses.getItems() != null && adResponses.getItems().size() > 0) {
                // TODO ligang 20151210 暂时删除
                // this.publishStaticFile(path, object);
                if (this.random.nextInt() % 20 == 0) {
                    this.LOG.info("adPic sync success:" + url + ",key:" + paramArray);
                }
                return true;
            }
        }
        return false;
    }

    private boolean executePublishCmsChannel(String url, Object[] paramArray, String path, String id) throws Exception {
        url = url.replaceAll("\\{id\\}", id);
        path = path.replaceAll("\\{id\\}", id);

        String object = this.restTemplate.getForObject(url, String.class, paramArray);
        CommonListResponse<ChannelDto> response = null;
        if (object != null) {
            response = objectMapper.readValue(object, new TypeReference<CommonListResponse<ChannelDto>>() {
            });
        }
        if (response != null && response.getResultStatus() != null && response.getResultStatus() == 1
                && response.getData() != null) {
            // TODO ligang 20151210 暂时删除
            // this.publishStaticFile(path, object);
            if (this.random.nextInt() % 20 == 0) {
                this.LOG.info("album sync success:" + url + ",key:" + paramArray);
            }
            return true;
        }
        return false;
    }

    public Map<String, DanMuDto> getDanMuConfig(Integer broadcastId) {
        Map<String, DanMuDto> response = null;
        try {
            String danmuUrl = ApplicationConstants.I_STATIC_ITV_BASE_HOST + "/api/conf/danmu/config.json";
            if (broadcastId != null && CommonConstants.CIBN == broadcastId) {
                danmuUrl = ApplicationConstants.I_STATIC_ITV_BASE_HOST + "/api/conf/danmu/cibn/config.json";
            }
            String result = this.restTemplate.getForObject(danmuUrl, String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new TypeReference<Map<String, DanMuDto>>() {
                });
            } else {
                response = new HashMap<String, DanMuDto>();
            }
        } catch (Exception e) {
            LOG.error("获取幕配置错误。getDanMuConfig error!", e);
        }
        if (response == null || response.isEmpty()) {
            DanMuDto danMuDto = new DanMuDto();
            danMuDto.setDef(0);
            danMuDto.setEnable(0);
            danMuDto.setLine(0);
            response.put("Default", danMuDto);
        }
        return response;
    }

    /**
     * 获取体育跳转配置
     * @return
     */
    public Map<String, LetvContentSportsJumpConfig> getLetvSportsJumpConfig() {
        Map<String, LetvContentSportsJumpConfig> response = null;
        try {
            String result = this.restTemplate.getForObject(ApplicationConstants.I_STATIC_ITV_BASE_HOST
                    + "/api/conf/sports/letvSportsJumpConfig.json", String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result,
                        new TypeReference<Map<String, LetvContentSportsJumpConfig>>() {
                        });
            }
        } catch (Exception e) {
            LOG.error("getLetvSportsJumpConfig: return error- ", e);
        }

        return response;
    }

    /**
     * 获取体育跳转配置
     * @return
     */
    public Map<String, LesoContentSportsJumpConfig> getLesoSportsJumpConfig() {
        Map<String, LesoContentSportsJumpConfig> response = null;
        try {
            String result = this.restTemplate.getForObject(ApplicationConstants.I_STATIC_ITV_BASE_HOST
                    + "/api/conf/sports/lesoSportsJumpConfig.json", String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result,
                        new TypeReference<Map<String, LesoContentSportsJumpConfig>>() {
                        });
            }
        } catch (Exception e) {
            LOG.error("getLesoSportsJumpConfig: return error- ", e);
        }

        return response;
    }

    /**
     * 获取体育跳转配置
     * @return
     */
    public Map<String, List<String>> getLetvTabConfig() {
        Map<String, List<String>> response = null;
        try {
            String result = this.restTemplate.getForObject(ApplicationConstants.I_STATIC_ITV_BASE_HOST
                    + "/api/conf/terminal/letvTabConfig.txt", String.class);
            if (StringUtil.isNotBlank(result)) {
                InputStream inputStream = new ByteArrayInputStream(result.getBytes());
                Properties tmp = new Properties();
                tmp.load(inputStream);

                Set<String> keySet = tmp.stringPropertyNames();
                if (!CollectionUtils.isEmpty(keySet)) {
                    response = new HashMap<String, List<String>>(keySet.size());
                    for (String key : keySet) {
                        if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty((String) tmp.get(key))) {
                            response.put(key, Arrays.asList(StringUtils.split((String) tmp.get(key), ",")));
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("getLesoSportsJumpConfig: return error- ", e);
        }

        return response;
    }

    public Set<String> getHolidayConfig() {
        Set<String> terminalSet = new HashSet<String>();
        try {
            String result = this.restTemplate.getForObject(ApplicationConstants.I_STATIC_ITV_BASE_HOST
                    + "/api/conf/holiday/config.txt", String.class);
            if (StringUtil.isNotBlank(result)) {
                String[] terminals = result.split("\n");
                if (terminals != null) {
                    for (String terminal : terminals) {
                        terminalSet.add(terminal);
                    }
                }
            }
        } catch (Exception e) {
            LOG.info("getHolidayConfig_", e);
        }
        return terminalSet;
    }

    public enum API_TYPE {
        MENU(),
        ADVISEMENT_PIC,
        ALBUM,
        CMS_CHANNEL
    }

    public String getServiceTermContent(String termKey) {
        String logPrefix = "getServiceTermContent_" + termKey;
        String result = null;
        if (StringUtils.isNotEmpty(termKey)) {
            try {
                result = this.restTemplate.getForObject(ApplicationConstants.I_STATIC_ITV_BASE_HOST
                        + "/api/terminal/serviceterm/" + termKey + ".html", String.class);
            } catch (Exception e) {
                LOG.error(logPrefix + " return error: ", e);
            }
        }

        return result;
    }

    public Object getDeviceConfig(String deviceModelType) {
        Object ret = null;
        Map<String, DeviceConfig> deviceConfigs = null;
        DeviceConfigDto response = null;
        try {
            String url = ApplicationConstants.I_STATIC_ITV_BASE_HOST + "/api/conf/terminal/deviceConfig.json";
            String result = this.restTemplate.getForObject(url, String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new TypeReference<DeviceConfigDto>() {
                });
            }
        } catch (Exception e) {
            LOG.error("getDeviceConfig error!", e);
        }
        if (null != response && null != response.getDevices() && !response.getDevices().isEmpty()) {
            deviceConfigs = new HashMap<String, DeviceConfig>();
            Iterator iterator = response.getDevices().iterator();
            DeviceConfig deviceConfig = null;
            while (iterator.hasNext()) {
                deviceConfig = (DeviceConfig) iterator.next();
                deviceConfigs.put(deviceConfig.getType(), deviceConfig);
            }
            if (StringUtil.isNotBlank(deviceModelType)) {
                ret = deviceConfigs.get(deviceModelType);
            } else {
                ret = deviceConfigs;
            }
        }
        return ret;
    }

    public String getAppConfig(CommonParam commonParam) {
        String ret = null;
        String response = null;

        String path = null;
        if (null != commonParam) {
            path = "cibn";
        } else {
            return ret;
        }

        String profile = ApplicationUtils.get(ApplicationConstants.IPTV_TERMINAL_CONFIG_PROFILE);
        if (StringUtil.isBlank(profile) || "prod".equals(profile)) {
            profile = "";
        } else {
            profile = "-" + profile;
        }

        try {
            String url = ApplicationConstants.I_STATIC_ITV_BASE_HOST + "/api/conf/terminal/" + path + "/appConfig"
                    + profile + ".json";
            response = this.restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            LOG.error("getAppConfig error!", e);
        }

        if (StringUtil.isNotBlank(response)) {
            ret = response;
        }

        return ret;
    }
}
