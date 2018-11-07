package com.letv.mas.caller.iptv.tvproxy.common.model.dao.cache;

import java.util.List;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheContentConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1.DeviceConfig;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1.LesoContentSportsJumpConfig;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1.LetvContentSportsJumpConfig;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 终端模块和缓存工具类
 * @author KevinYi
 */
@Component
public class TerminalCacheDao extends BaseCacheDao {

    public Map<String, LetvContentSportsJumpConfig> getLetvSportsJumpConfig() {
        return this.cacheTemplate.get(CacheContentConstants.SPORTS_JUMP_CONFIG_LETV_KEY,
                new LetvTypeReference<Map<String, LetvContentSportsJumpConfig>>() {
                });
    }

    public int setLetvSportsJumpConfig(Map<String, LetvContentSportsJumpConfig> letvSportsJumpConfig, int duration) {
        return this.cacheTemplate
                .set(CacheContentConstants.SPORTS_JUMP_CONFIG_LETV_KEY, letvSportsJumpConfig, duration);
    }

    public Map<String, LesoContentSportsJumpConfig> getLesoSportsJumpConfig() {
        return this.cacheTemplate.get(CacheContentConstants.SPORTS_JUMP_CONFIG_LESO_KEY,
                new LetvTypeReference<Map<String, LesoContentSportsJumpConfig>>() {
                });
    }

    public int setLesoSportsJumpConfig(Map<String, LesoContentSportsJumpConfig> lesoSportsJumpConfig, int duration) {
        return this.cacheTemplate
                .set(CacheContentConstants.SPORTS_JUMP_CONFIG_LESO_KEY, lesoSportsJumpConfig, duration);
    }

    public String getLetvHolidayConfig(String terminalApplication, String area) {
        return this.cacheTemplate.get(buildHolidayConfigCacheKey(terminalApplication, area), String.class);
    }

    public int setLetvHolidayConfig(String terminalApplication, String area, String holidayConfig) {
        return this.cacheTemplate.set(buildHolidayConfigCacheKey(terminalApplication, area), holidayConfig);
    }

    public List<String> getLetvTabConfigList(String terminalApplication, String area) {
        return this.cacheTemplate.get(buildTabConfigCacheKey(terminalApplication, area),
                new LetvTypeReference<List<String>>() {
                });
    }

    public int setLetvTabConfigList(String terminalApplication, String area, List<String> tabConfig) {
        return this.cacheTemplate.set(buildTabConfigCacheKey(terminalApplication, area), tabConfig);
    }

    public List<String> getUnsupport360StreamSeries(String key) {
        return this.cacheTemplate.get(key, new LetvTypeReference<List<String>>() {
        });
    }

    public int setUnsupport360StreamSeries(String key, List<String> unsupport360StreamList) {
        return this.cacheTemplate.set(key, unsupport360StreamList);
    }

    /**
     * set terminal config info to cache
     * @param terminalApplication
     * @param area
     * @param value
     */
    public void setTerminalConfig(String terminalApplication, String area, Integer broadcastId, String version,
            Map<String, Object> value) {
        String key = getTerminalConfigKey(terminalApplication, area, broadcastId, version);
        this.cacheTemplate.set(key, value);
    }

    /**
     * get terminal config info from cache
     * @return
     */
    public Map<String, Object> getTerminalConfig(String terminalApplication, String area, Integer broadcastId,
            String version) {
        String key = getTerminalConfigKey(terminalApplication, area, broadcastId, version);
        return this.cacheTemplate.get(key, new LetvTypeReference<Map<String, Object>>() {
        });
    }

    private String buildTabConfigCacheKey(String terminalApplication, String area) {
        return terminalApplication + ".tab.config." + area;
    }

    private String buildHolidayConfigCacheKey(String terminalApplication, String area) {
        return terminalApplication + ".holiday." + area;
    }

    /**
     * 获取配置下发的缓存key
     * @param terminalApplication
     * @param area
     * @param broadcastId
     * @return
     */
    public static String getTerminalConfigKey(String terminalApplication, String area, Integer broadcastId,
            String version) {
        if (area != null) {
            area = area.toUpperCase();
        }
        String broadcast = "";
        if ((broadcastId != null) && (broadcastId != 0)) {
            broadcast = broadcastId.toString();
        }
        if (StringUtil.isBlank(version)) {
            return CacheContentConstants.TERMINAL_CONFIG + terminalApplication + "_" + StringUtils.trimToEmpty(area)
                    + "_" + broadcast;
        } else {
            return CacheContentConstants.TERMINAL_CONFIG + terminalApplication + "_" + StringUtils.trimToEmpty(area)
                    + "_" + broadcast + "_" + StringUtils.trimToEmpty(version);
        }
    }

    public String getServiceTermContent(String termKey) {
        return this.cacheTemplate.get(CacheContentConstants.TERMINAL_SERVICE_TERM_PREFIX + termKey, String.class);
    }

    public void setServiceTermContent(String termKey, String content) {
        this.cacheTemplate.set(CacheContentConstants.TERMINAL_SERVICE_TERM_PREFIX + termKey, content);
    }

    public int setDeviceConfigs(Map<String, DeviceConfig> deviceConfigs, int duration) {
        return this.cacheTemplate.set(CacheContentConstants.LETV_TERMINAL_DEVICE_CONFIG_KEY, deviceConfigs, duration);
    }

    public Map<String, DeviceConfig> getDeviceConfigs() {
        return this.cacheTemplate.get(CacheContentConstants.LETV_TERMINAL_DEVICE_CONFIG_KEY,
                new LetvTypeReference<Map<String, DeviceConfig>>() {
                });
    }

    public int setAppConfigs(String appConfigs, int duration) {
        return this.setAppConfigs(appConfigs, duration, "");
    }

    public String getAppConfigs() {
        return this.getAppConfigs("");
    }

    public void clearAppConfigs() {
        this.clearAppConfigs("");
    }

    public int setAppConfigs(String appConfigs, int duration, String profile) {
        return this.cacheTemplate.set(CacheContentConstants.LETV_TERMINAL_APP_CONFIG_KEY + profile, appConfigs,
                duration);
    }

    public String getAppConfigs(String profile) {
        return this.cacheTemplate.get(CacheContentConstants.LETV_TERMINAL_APP_CONFIG_KEY + profile, String.class);
    }

    public void clearAppConfigs(String profile) {
        this.cacheTemplate.delete(CacheContentConstants.LETV_TERMINAL_APP_CONFIG_KEY + profile);
    }
}
