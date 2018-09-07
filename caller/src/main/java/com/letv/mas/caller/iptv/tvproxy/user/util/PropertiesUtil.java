package com.letv.mas.caller.iptv.tvproxy.user.util;

import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties文件加载工具
 */
public class PropertiesUtil {

    private volatile static PropertiesUtil instance;

    public static PropertiesUtil getInstance() {
        if (instance == null) {
            synchronized (PropertiesUtil.class) {
                if (instance == null) {
                    instance = new PropertiesUtil();
                }
            }
        }

        return instance;
    }

    public Properties getPropertiesByClassPath(String path) throws Exception {
        Properties properties = new Properties();

        InputStream input = null;
        try {
            input = getClass().getResourceAsStream(path);
            properties.load(input);
            PropertyConfigurator.configure(properties);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (Exception e) {
            }
        }

        return properties;
    }

    public Properties getPropertiesByFilePath(String path) throws Exception {
        Properties properties = new Properties();

        InputStream input = null;
        try {
            input = new FileInputStream(path);
            properties.load(input);
            PropertyConfigurator.configure(properties);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (Exception e) {
            }
        }

        return properties;
    }
}
