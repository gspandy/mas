package com.letv.mas.caller.iptv.tvproxy.user.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "iptv")
public class ConfigOperationUtil implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Map<String, String> httpUrls = new HashMap<>();
    private static Map<String, String> configs = new HashMap<>();

    public Map<String, String> getHttpUrls() {
        return httpUrls;
    }

    public void setHttpUrls(Map<String, String> httpUrls) {
        this.httpUrls = httpUrls;
    }

    public Map<String, String> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, String> configs) {
        this.configs = configs;
    }

    public static String getConfigValue(String key) {
        if (configs != null) {
            return configs.get(key);
        }
        return null;
    }

    public static String getHttpUrl(String key) {
        if (httpUrls != null) {
            return httpUrls.get(key);
        }
        return null;
    }

    public static Boolean getBoolean(String key) {
        String value = configs.get(key);
        Boolean v = null;
        if (value != null) {
            try {
                v = Boolean.parseBoolean(value);
            } catch (NumberFormatException e) {
            }
        }
        return v;
    }

    /**
     * 根据key获取value,整型
     *
     * @param key
     * @return
     */
    public static final Integer getInt(String key) {
        String value = configs.get(key);
        Integer v = null;
        if (value != null) {
            try {
                v = Integer.parseInt(value);
            } catch (NumberFormatException e) {
            }
        }
        return v;
    }

    /**
     * Get property value of int, and return the default value if null.
     *
     * @param key          property key
     * @param defaultValue default value
     * @return property value, and the default value if value is null.
     */
    public static int getInt(String key, int defaultValue) {
        Integer value = getInt(key);
        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }


    /**
     * Get property value of boolean, and return the default value if null.
     *
     * @param key          property key
     * @param defaultValue default value
     * @return property value, and the default value if value is null.
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        Boolean value = getBoolean(key);
        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }

    /**
     * 根据key获取value
     *
     * @param key
     * @return
     */
    public static final String get(String key) {
        if (httpUrls.containsKey(key)) {
            return httpUrls.get(key);
        }
        if (configs.containsKey(key)) {
            return configs.get(key);
        }
        return null;
    }

    /**
     * 根据key获取value，如果为null，则返回defaultValue
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static final String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }
}
