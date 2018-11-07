package com.letv.mas.caller.iptv.tvproxy.terminal.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.JsonUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component(value = "v2.TerminalLogUploadService")
public class TerminalLogUploadService extends BaseService {
    private final Logger clientDataLog = LoggerFactory.getLogger("clientDataLog");
    private final Logger log = LoggerFactory.getLogger(TerminalLogUploadService.class);
    private final static float SIZEKB = 5f;

    public Response<Object> uploadLog(Integer type, Integer model, CommonParam commonParam, Map<String, Object> logMap) {
        Response<Object> response = new Response<Object>();
        response.setData(false);
        /**
         * 检查上传日志的开关是否开启
         */
        /*
         * String appConfigs =
         * this.facadeCacheDao.getTerminalCacheDao().getAppConfigs();
         * try {
         * Map<String, Object> appConfigsMap = (Map<String, Object>)
         * JSONObject.parse(appConfigs);
         * if (appConfigsMap != null) {
         * Boolean uploadLogSwitch = (Boolean)
         * appConfigsMap.get("uploadLogSwitch");
         * if (uploadLogSwitch != null && !uploadLogSwitch) {
         * return response;
         * }
         * }
         * } catch (Exception e) {
         * log.error("uploadLog_parse_appConfigs", e.getMessage(), e);
         * }
         */
        /**
         * 检测参数是否正确
         */
        if (!isLegalParams(commonParam, logMap)) {
            return response;
        }
        if (model == 0) {
            boolean result = uploadSingle(type, commonParam, logMap);
            response.setData(result);
        } else {
            Map<String, Object> results = new HashMap<String, Object>();
            for (Map.Entry<String, Object> entry : logMap.entrySet()) {
                Object value = entry.getValue();
                String key = entry.getKey();
                boolean result = false;
                if (value instanceof Map) {
                    Map<String, Object> singleMap = (Map<String, Object>) value;
                    if (singleMap != null && singleMap.size() > 0) {
                        result = uploadSingle(type, commonParam, (Map<String, Object>) value);
                    }
                }
                results.put(key, result);
            }
            response.setData(results);
        }

        return response;
    }

    private boolean uploadSingle(Integer type, CommonParam commonParam, Map<String, Object> logMap) {
        if (logMap == null || logMap.size() == 0 || logMap.size() > 20) {
            log.info("uploadSingle logMap is null or logMap size over 20 mac：" + commonParam.getMac());
            return false;
        }
        StringBuffer logs = new StringBuffer();
        logs.append(" form:" + commonParam.getTerminalApplication() + "|type:" + type + "|mac:" + commonParam.getMac());
        if (StringUtil.isNotBlank(commonParam.getUserId())) {
            logs.append("|uid:" + commonParam.getUserId());
        }
        for (Map.Entry<String, Object> entry : logMap.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map) {
                String json = JSONObject.toJSONString(value);
                logs.append("|" + entry.getKey() + ":" + json);
            } else {
                logs.append("|" + entry.getKey() + ":" + value);
            }
        }
        String commonParamStr = JSONObject.toJSONString(commonParam);
        if (commonParamStr != null) {
            logs.append("|commonParam:" + commonParamStr);
        }
        clientDataLog.info(logs.toString());
        return true;
    }

    public Response<Object> uploadLogV2(Integer type, Integer model, CommonParam commonParam, String infos) {
        Response<Object> response = new Response<Object>();
        response.setData(false);
        /**
         * 检测参数是否正确
         */
        if (commonParam == null || infos == null) {
            log.info("uploadLogV2 commonParam is null or infos is null");
            return response;
        }
        if (StringUtil.isBlank(commonParam.getMac())) {
            log.info("uploadLogV2 mac is null");
            return response;
        }
        if (StringUtil.isBlank(commonParam.getTerminalApplication())) {
            log.info("uploadLogV2 terminalApplication is null");
            return response;
        }
        String userId = commonParam.getUserId();
        if (userId == null) {
            commonParam.setUserId("");
        }
        long size = (infos).getBytes().length;
        if (size > SIZEKB * 1024) {
            log.info("uploadLogV2 size over " + SIZEKB + "KB  mac：" + commonParam.getMac());
            return response;
        }

        Map<String, Object> logMap = JsonUtil.parse(infos, new TypeReference<Map<String, Object>>() {
        });
        if (logMap == null || logMap.size() == 0) {
            log.info("uploadLogV2 logMap is null mac：" + commonParam.getMac());
            return response;
        }
        if (logMap.size() > 20) {
            log.info("uploadLogV2 logMap size over 20 mac：" + commonParam.getMac());
            return response;
        }
        if (model == 0) {
            boolean result = uploadSingle(type, commonParam, logMap);
            response.setData(result);
        } else {
            Map<String, Object> results = new HashMap<String, Object>();
            for (Map.Entry<String, Object> entry : logMap.entrySet()) {
                Object value = entry.getValue();
                String key = entry.getKey();
                boolean result = false;
                if (value instanceof Map) {
                    try {
                        Map<String, Object> singleMap = (Map<String, Object>) value;
                        if (singleMap != null && singleMap.size() > 0) {
                            result = uploadSingle(type, commonParam, (Map<String, Object>) value);
                        }
                    } catch (Exception e) {
                        log.info("uploadLogV2 model is 1 and value is error: " + e.getMessage());
                    }
                } else {
                    log.info("uploadLogV2 model is 1 and value is not map ");
                }
                results.put(key, result);
            }
            response.setData(results);
        }

        return response;
    }

    private boolean isLegalParams(CommonParam commonParam, Map<String, Object> logMap) {
        if (commonParam == null || StringUtil.isBlank(commonParam.getMac())) {
            return false;
        }
        if (StringUtil.isBlank(commonParam.getTerminalApplication())) {
            return false;
        }
        String userId = commonParam.getUserId();
        if (userId == null) {
            commonParam.setUserId("");
        }
        if (logMap == null || logMap.size() == 0 || logMap.size() > 20) {
            return false;
        }
        String bodyStr = JSONObject.toJSONString(logMap);
        if (StringUtil.isBlank(bodyStr)) {
            return false;
        }
        long size = bodyStr.getBytes().length;
        if (size > SIZEKB * 1024) {
            return false;
        }
        return true;
    }
}
