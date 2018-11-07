package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp;

import com.letv.mas.caller.iptv.tvproxy.common.util.HttpClientUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 第三方服务Dao基类
 */
public abstract class BaseTpDao {

    protected static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // allow empty string parse to null object
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    @Autowired
    @Qualifier("restTemplate")
    protected HttpClientUtil restTemplate;

    public interface HttpHandler {
        /**
         * 构建打包HttpGet请求地址
         * @param ids
         *            : 逗号分割的id值
         * @param obj
         *            : 请求额外参数集合
         * @return
         */
        String[] buildHttpGetUrls(String ids, Object obj);

        /**
         * 构建打包HttpGet请求地址
         * @param total
         *            : 记录总数
         * @param pageSize
         *            : 分页大小
         * @param obj
         *            : 请求额外参数集合
         * @return
         */
        String[] buildHttpGetUrls(int total, int pageSize, Object obj);

        <T> T formatResponse(Object result) throws Exception;

        void log(String log, Exception e);
    }

    /**
     * 打包HttpGet请求
     * @param ids
     * @param params
     * @param handler
     * @param responseType
     * @param logPrefix
     * @return
     */
    public <T> Map<String, T> getHttpPackages(String ids, Object params, HttpHandler handler, Class<T> responseType,
            String logPrefix) {
        Map<String, T> ret = null;
        T response = null;
        if (StringUtil.isBlank(logPrefix)) {
            logPrefix = "getHttpPackages_" + ids + "_";
        }
        if (StringUtil.isBlank(ids) || null == handler) {
            return ret;
        }

        try {
            String[] idArr = StringUtils.split(ids, ",");
            if (null == idArr || idArr.length == 0) {
                return ret;
            }
            String[] urls = handler.buildHttpGetUrls(ids, params);
            Map<String, String> results = this.restTemplate.multiHttpRequests(urls, "GET", null, null);
            if (null != results && results.size() > 0) {
                String result = null;
                int index = 0;
                ret = new HashMap<String, T>();
                for (String url : urls) {
                    result = results.get(url);
                    if (StringUtil.isNotBlank(result)) {
                        response = handler.formatResponse(result);
                        if (null != response) {
                            ret.put(idArr[index++], response);
                        } else {
                            index++;
                        }
                    } else {
                        index++;
                    }
                    handler.log(logPrefix + idArr[index - 1] + ": invoke return [" + result + "]", null);
                }
            }
        } catch (Exception e) {
            handler.log(logPrefix + " return error: " + e.getMessage(), e);
        }
        return ret;
    }

    /**
     * 打包HttpGet请求
     * @param total
     * @param pageSize
     * @param params
     * @param handler
     * @param responseType
     * @param logPrefix
     * @return
     */
    public <T> List<T> getHttpPackages(int total, int pageSize, Object params, HttpHandler handler,
            Class<T> responseType, String logPrefix) {
        List<T> ret = null;
        T response = null;
        if (StringUtil.isBlank(logPrefix)) {
            logPrefix = "getHttpPackages_" + total + "_" + pageSize + "_";
        }
        if (null == handler) {
            return ret;
        }

        try {
            String[] urls = handler.buildHttpGetUrls(total, pageSize, params);
            Map<String, String> results = this.restTemplate.multiHttpRequests(urls, "GET", null, null);
            if (null != results && results.size() > 0) {
                String result = null;
                int index = 0;
                ret = new ArrayList<T>();
                for (String url : urls) {
                    result = results.get(url);
                    if (StringUtil.isNotBlank(result)) {
                        response = handler.formatResponse(result);
                        if (null != response) {
                            ret.add(response);
                        } else {
                            index++;
                        }
                    } else {
                        index++;
                    }
                    handler.log(logPrefix + index + ": invoke return [" + result + "]", null);
                }
            }
        } catch (Exception e) {
            handler.log(logPrefix + " return error: " + e.getMessage(), e);
        }
        return ret;
    }
}
