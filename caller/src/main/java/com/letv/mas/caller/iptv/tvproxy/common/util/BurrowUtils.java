package com.letv.mas.caller.iptv.tvproxy.common.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvCommonException;
import org.apache.log4j.Logger;

/**
 * application.properties属性获取工具类
 */
@Deprecated
public final class BurrowUtils {
    private static final Logger logger = Logger.getLogger(BurrowUtils.class);
    private static Properties bundle = new Properties();// 防止异常空取。
    static {
        initBurrowCache();
    }

    /**
     * 初始化properties配置文件
     */
    public static void initBurrowCache() {
        String dir = System.getProperty("conf.dir");
        if (dir != null) {
            if (!dir.endsWith("/")) {
                dir += "/";
            }
            String dirTmp = dir + "burrow.properties";
            logger.info("will load properties in " + dirTmp);
            loadLocalPath(dirTmp);
        } else {
            logger.error("BurrowUtils con't load config", new LetvCommonException("BurrowUtils can't load config"));
        }
    }

    /**
     * 根据key获取value
     * @param key
     * @return
     */
    public static final String get(String key) {
        return bundle.getProperty(key);
    }

    /**
     * 根据key获取value
     * @param key
     * @return
     */
    public static final String get(String key, Map<String, Object> params) {
        for (String k : params.keySet()) {
            Object v = params.get(k);
        }

        String value = bundle.getProperty(key);
        Pattern mPattern = Pattern.compile("\\$[a-zA-Z_]+");
        Matcher mMatcher = mPattern.matcher(value);
        while (mMatcher.find()) {
            value = mMatcher.replaceFirst(bundle.getProperty(mMatcher.group(0)));
            mMatcher = mPattern.matcher(value);
        }
        value = value.replaceAll("\\s+", "");
        if (value != null && params != null) {
            value = replaceValue(value, params);
        }
        value = value
                .replaceAll(
                        "(,\b*(\"|\')[0-9A-Za-z_]*(\"|\'):(\"|\')*\\{[A-Za-z0-9_]*\\}(\"|\')*)|((\"|\')*[0-9A-Za-z_]*(\"|\')*:\b*(\"|\')*\\{[0-9A-Za-z_]*\\}(\"|\')*\b*,*)",
                        "").replaceAll(",*\b*(\"|\')[1-9A-Za-z_]*(\"|\'):(\"|\')*\\{[A-Za-z0-9_]*\\}(\"|\')*,*", "");
        return value;
    }

    private static String replaceValue(String value, Map<String, Object> params) {
        for (String k : params.keySet()) {
            Object v = params.get(k);
            if (v != null) {
                if (v instanceof Map) {
                    if (value.indexOf("{" + k + "}") != -1) {
                        try {
                            value = value.replaceAll("\\{" + k + "\\}", JsonUtil.parseToString(v));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        value = replaceValue(value, (Map) v);
                    }
                } else {
                    value = value.replaceAll("\\{" + k + "\\}", v + "");
                }
            }
        }
        return value;
    }

    /**
     * 加载属性文件，到内存
     */
    private static void loadLocalPath(String filePath) {
        Properties tmp = new Properties();
        InputStream inputStream = null;
        try {
            tmp.putAll(bundle);
            inputStream = new FileInputStream(filePath);
            tmp.load(inputStream);
            bundle = tmp;
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
