package com.letv.mas.caller.iptv.tvproxy.common.util;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * application.properties属性获取工具类
 */
public final class ApplicationUtils {
    private static Map<String, String> propertyFilesAndMD5Checksum = new HashMap<>(); // Map结构，文件路径<->MD5Checksum
    private static final Logger logger = Logger.getLogger(ApplicationUtils.class);
    private static Properties bundle = new Properties();// 防止异常空取。

    @ApolloConfig("config.properties")
    private static Config config;

    @ApolloConfig("httpurl.properties")
    private static Config httpurl;

    static {
        initApplicationCache();
    }

    public static void changeValue(String key,String Value){
        bundle.setProperty(key,Value);
    }

    /**
     * 初始化httpurl.properties/config.properties配置文件
     */
    public static void initApplicationCache() {
        loadClassPath("config.properties");
        loadClassPath("httpurl.properties");
    }

    public static String getProperty(String key) {
        String value = null;
        if (config != null) {
            value = config.getProperty(key, null);
        }
        if (value != null) {
            return value;
        }
        if (httpurl != null) {
            value = httpurl.getProperty(key, null);
        }
        if (value != null) {
            return value;
        }
        value = bundle.getProperty(key);
        return value;
    }

    /**
     * 根据key获取value
     * @param key
     * @return
     */
    public static final String get(String key, Map<String, Object> params) {
        String value = getProperty(key);
        if (value != null && params != null) {
            for (String k : params.keySet()) {
                Object v = params.get(k);
                if (v != null) {
                    value = value.replaceAll("{" + k + "}", v + "");
                }
            }
        }
        return value;
    }

    /**
     * 根据key获取value,整型
     * @param key
     * @return
     */
    public static final Integer getInt(String key) {
        String value = getProperty(key);
        Integer v = null;
        if (value != null) {
            try {
                v = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                logger.warn("ApllicationUtils.getInt error.key:" + key);
            }
        }
        return v;
    }

    /**
     * Get property value of int, and return the default value if null.
     * @param key
     *            property key
     * @param defaultValue
     *            default value
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
     * 根据key获取value,整型
     * @param key
     * @return
     */
    public static final Boolean getBoolean(String key) {
        String value = getProperty(key);
        Boolean v = null;
        if (value != null) {
            try {
                v = Boolean.parseBoolean(value);
            } catch (NumberFormatException e) {
                logger.warn("ApplicationUtils.getBoolean error.key:" + key);
            }
        }
        return v;
    }

    /**
     * Get property value of boolean, and return the default value if null.
     * @param key
     *            property key
     * @param defaultValue
     *            default value
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
     * @param key
     * @return
     */
    public static final String get(String key) {
        return getProperty(key);
    }

    /**
     * 根据key获取value，如果为null，则返回defaultValue
     * @param key
     * @param defaultValue
     * @return
     */
    public static final String get(String key, String defaultValue) {
        String value = getProperty(key);
        return value != null ? value : defaultValue;
    }

    private static void loadClassPath(String classFilePath) {
        try {
            Properties tmp = PropertiesUtil.getInstance().getPropertiesByClassPath("/" + classFilePath);
            tmp.putAll(bundle);
            bundle = tmp;
        } catch (Exception e) {
            throw new RuntimeException(classFilePath + " is not found!", e);
        }
    }

    public static void updateReloadableProperties() {
        for (Map.Entry<String, String> propertyFile : propertyFilesAndMD5Checksum.entrySet()) {
            String filepath = propertyFile.getKey();
            logger.debug("Start detecting " + filepath);
            String lastMD5Checksum = propertyFile.getValue();
            String currentMD5Checksum = calculateChecksum(filepath);
            if (lastMD5Checksum != null && !lastMD5Checksum.equals(currentMD5Checksum)) {
                logger.debug(filepath + " is updated, start reload this property file");
                loadLocalPath(filepath, currentMD5Checksum);
            }
        }
    }

    private static String calculateChecksum(String filepath) {
        String md5 = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filepath);
            md5 = DigestUtils.md5Hex(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(filepath + " is not found!", e);
        } catch (IOException e) {
            throw new RuntimeException(filepath + " calculate checksum failed!", e);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("Failed to close FileInputStream", e);
                }
            }
        }
        return md5;
    }

    private static void loadLocalPath(String filePath, String currentChecksum) {
        Properties tmp = new Properties();
        InputStream inputStream = null;
        try {
            tmp.putAll(bundle);
            inputStream = new FileInputStream(filePath);
            tmp.load(inputStream);
            bundle = tmp;
            String md5 = currentChecksum != null ? currentChecksum : calculateChecksum(filePath);
            propertyFilesAndMD5Checksum.put(filePath, md5);
        } catch (Exception e) {
            throw new RuntimeException(filePath + " is not found!", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    logger.error("Failed to close FileInputStream", e);
                }
            }
        }
    }
}
