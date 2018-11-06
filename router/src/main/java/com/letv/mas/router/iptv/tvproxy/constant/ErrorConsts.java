package com.letv.mas.router.iptv.tvproxy.constant;

import com.letv.mas.router.iptv.tvproxy.model.dto.ExceptionDto;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leeco on 18/11/6.
 */
public class ErrorConsts {
    public static String DEFAULT_CODE = "0000";

    // 公用（00开头）
    public static String COM_OK = "0001";// 成功
    public static String COM_UNLOGIN = "0002";// 未登录
    public static String COM_FAIL = "0003";// 内部错误

    // 用户（10开头）
    public static String USER_LOGIN_INCORRECT = "1001";// 登录名或密码不正确

    private final static Map<String, String> errorCodeMap = new HashMap<String, String>();

    static {
        errorCodeMap.put(DEFAULT_CODE, "未知异常");
        errorCodeMap.put(COM_OK, "成功");
        errorCodeMap.put(COM_UNLOGIN, "未登录");
        errorCodeMap.put(COM_FAIL, "内部错误");
        errorCodeMap.put(USER_LOGIN_INCORRECT, "登录名或密码不正确");
    }

    public static void throwException(Exception e, String code) {
        if (e != null) {
            if (e instanceof ExceptionDto) {
                throw new ExceptionDto(((ExceptionDto) e).getErrorCode(), e.getMessage());
            } else {
                throw new ExceptionDto(code, getMessage(code));
            }
        }
    }

    public static void throwException(String code) {
        throw new ExceptionDto(code, getMessage(code));
    }

    public static String getMessage(String key) {
        String value = errorCodeMap.get(key);
        if (StringUtils.isBlank(value)) {
            value = errorCodeMap.get(DEFAULT_CODE);
        }
        return value;
    }
}
