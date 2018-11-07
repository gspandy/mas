package com.letv.mas.caller.iptv.tvproxy.common.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static ObjectMapper objectMapper = null;

    static {
        objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public static <T> T parse(String json, Class<T> clazz) {

        if (json == null || json.length() == 0) {
            return null;
        }

        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * json字符串解析，转换为复杂类型T
     * @param json
     * @param valueTypeRef
     * @return
     */
    public static <T> T parse(String json, TypeReference<T> valueTypeRef) {

        if (json == null || json.length() == 0) {
            return null;
        }

        try {
            return objectMapper.readValue(json, valueTypeRef);
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * 将对象转换成json字符串
     * @param o
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     */
    public static String parseToString(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }
}
