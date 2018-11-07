package com.letv.mas.caller.iptv.tvproxy.common.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvCommonException;
import org.apache.log4j.Logger;


/**
 * burrow.properties属性获取工具类
 */
public final class BurrowUtilsV3 {
    private static final Logger logger = Logger.getLogger(BurrowUtilsV3.class);
    private static Properties bundle = new Properties();// 防止异常空取。
    private static final String TEMPLATE_REQUIRED_PLACEHOLDER = "{TF}";// 模板中以改字段占位，则模板的key在程序运行过程中会保留，其值为"";
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
     * 根据key和参数对象获取指定的模板map
     * @param key
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static final Map<?, ?> get(String key, Object paramObj) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (paramObj instanceof Map) {
            paramMap = (Map) paramObj;
        } else {
            Class<?> cls = paramObj.getClass();
            Field fields[] = cls.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    paramMap.put(field.getName(), field.get(paramObj));
                } catch (Exception e) {
                    return null;
                }
            }
        }
        Map<?, ?> originMap = new HashMap((Map<?, ?>) bundle.get(key));
        matchKey(originMap, paramMap);
        originMap = clearSurplusField(originMap);
        return originMap;
    }

    /**
     * 加载属性文件，到内存
     */
    private static void loadLocalPath(String filePath) {
        Properties tmp = new Properties();
        Properties tmp_ = new Properties();
        InputStream inputStream = null;
        String value_ = null;
        try {
            tmp.putAll(bundle);
            inputStream = new FileInputStream(filePath);
            tmp.load(inputStream);

            Set<?> keyset = tmp.keySet();
            for (Object key : keyset) {
                String value = tmp.getProperty(key.toString());
                Pattern mPattern = Pattern.compile("\\$[a-zA-Z_]+");
                Matcher mMatcher = mPattern.matcher(value);
                while (mMatcher.find()) {
                    value = mMatcher.replaceFirst(tmp.getProperty(mMatcher.group(0)));
                    mMatcher = mPattern.matcher(value);
                }
                value_ = value;
                tmp_.put(key, stringToMap(value_));
            }

            bundle = tmp_;

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

    /**
     * 将模板string转换为map对象
     * @param value
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static Map stringToMap(String value) {
        Map<String, Object> map = new HashMap<String, Object>();
        map = JsonUtil.parse(value, Map.class);
        return map;
    }

    /**
     * 参数对象属性名称与模板key值匹配
     * @param templateMap
     * @param valueMap
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void matchKey(Map templateMap, Map valueMap) {
        if (templateMap != null && templateMap.size() > 0) {
            Set<String> keyset_temp = templateMap.keySet();
            for (String key : keyset_temp) {
                if ("extend".equals(key)) {
                    continue;
                }
                Object value = valueMap.get(key);
                Object template_value = templateMap.get(key);
                if (template_value instanceof String) {
                    String t_value = (String) template_value;
                    if (TEMPLATE_REQUIRED_PLACEHOLDER.equals(t_value)) {
                        continue;
                    }
                    if (!StringUtil.isBlank(t_value) && t_value.matches("[a-zA-Z]*")) {
                        value = valueMap.get(t_value);
                        templateMap.put(key, value);
                        continue;
                    }
                } else if (template_value instanceof Integer) {
                    continue;
                }
                if (value != null && !"".equals(value)) {
                    templateMap.put(key, value);
                    continue;
                }
                if (template_value instanceof Map) {
                    // 避免map的记忆功能
                    Map map = new HashMap((Map) template_value);
                    matchKey(map, valueMap);
                    templateMap.put(key, map);
                }
            }
        }
    }

    /**
     * 清楚模板中的多余字段
     * @param originMap
     * @return
     */
    @SuppressWarnings({ "rawtypes" })
    private static Map clearSurplusField(Map originMap) {
        Map resultMap = new HashMap();
        clearFieldHandler(originMap, resultMap);
        return resultMap;
    }

    /**
     * 清除多余字段处理器
     * @param originMap
     * @param resultMap
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void clearFieldHandler(Map originMap, Map resultMap) {
        Set<String> keysets = originMap.keySet();
        for (String key : keysets) {
            Object value = originMap.get(key);
            if (value instanceof Map) {
                Map map = new HashMap();
                clearFieldHandler((Map) value, map);
                resultMap.put(key, map);
            } else {
                if (!"".equals(value)) {
                    if (TEMPLATE_REQUIRED_PLACEHOLDER.equals(value)) {
                        resultMap.put(key, "");
                    } else {
                        resultMap.put(key, value);
                    }
                }
            }
        }
    }

}
