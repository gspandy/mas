package com.letv.mas.caller.iptv.tvproxy.user;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpConstant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserConstants {

    /**
     * 响应状态码
     */
    public static final int RESPONSE_FAILURE = 0;
    public static final int RESPONSE_SUCCESS = 1;

    /**
     * 播控方上线
     */
    public final static int BROADCAST_ONLINE_STATUS = 1;

    /**
     * 播控方下线
     */
    public final static int BROADCAST_OFFLINE_STATUS = 0;

    // 查询类型, 查询充值记录 00, 查询消费记录 01, 查询乐点余额02
    public static final String QUERY_TYPE_RECHARGE = "00";
    public static final String QUERY_TYPE_CONSUMPTION = "01";
    public static final String QUERY_TYPE_LETV_POINT = "02";

    /**
     * 会员名称显示的key,用于多语言环境处理会员显示名称
     */
    public static final String TV_VIP_TITLE = "USER.VIP.TITLE";

    /**
     * 未开通会员或会员已过期显示的key, 用于多语言环境显示名称处理
     */
    public static final String TV_NOT_VIP_TITLE = "USER.NOTVIP.TITLE";

    /**
     * 直播SDK，是否开放登录注册，0--不开放，1--开放
     */
    public static final int TPSDK_REGISTER_AND_LOGIN_WITHOUT_AUTHORIZATION = 0;
    public static final int TPSDK_REGISTER_AND_LOGIN_WITH_AUTHORIZATION = 1;

    /**
     * 弹幕引导文案
     */
    public static String dmNoticeMin = "条弹幕装弹完毕~即将发射！";
    public static String dmNoticeMax = "条弹幕疯狂来袭~请做好准备！";

    /**
     * get user info md5 sign key.
     */
    public static String USER_GET_USERINFO_SIGN_KEY = "vtmorfyekngisofniresutegresuvtel";

    /**
     * get user info from type.
     */
    public static String USER_FROM_TYPE_GOLIVE = "golive";

    public static String USER_FAVORITE_ALBUM = "USER_FAVORITE_ALBUM";
    public static String USER_FAVORITE_COLLECT = "USER_FAVORITE_COLLECT";
    public static String USER_FAVORITE_ALBUM_COLLECT = "USER_FAVORITE_ALBUM_COLLECT";

    private static final String[] hignStreamDeviceArray = { "Letv Max3-65", "Letv X3-55", "Letv X3-65", "X4-40",
            "X4-50Pro", "X4-50", "X4-55", "X4-65" };
    public static final List<String> hignStreamDeviceList = Arrays.asList(hignStreamDeviceArray);

    public static final int USER_CHILD_LOCK_STATUS_UNSET = -1;
    public static final int USER_CHILD_LOCK_STATUS_OFF = 0;
    public static final int USER_CHILD_LOCK_STATUS_ON = 1;

    public static final int USER_CHILD_LOCK_CHECK_ACTION_TYPE_CHECK_STATUS = 1;
    public static final int USER_CHILD_LOCK_CHECK_ACTION_TYPE_VERIFY_PASSOWRD = 2;
    public static final int USER_CHILD_LOCK_CHECK_ACTION_TYPE_VERIFY_PIN = 3;

    public static final int USER_CHILD_LOCK_SET_ACTION_TYPE_CREATE = 1;
    public static final int USER_CHILD_LOCK_SET_ACTION_TYPE_RESET_WITH_PIN = 2;
    public static final int USER_CHILD_LOCK_SET_ACTION_TYPE_RESET_WITH_LOCALTOKEN = 3;

    public static final long USER_CHILD_LOCK_SET_TOKEN_EXPIRE_TIME = 5 * 60 * 1000L;

    /**
     * 用户中心（点播/直播）弹幕相关接口响应状态码-文案映射，key--状态码，value--国际化文案key
     */
    private static final Map<String, String> USERCENTER_DANMU_RESULT_CODE_MSG_MAP = new HashMap<String, String>();

    static {
        // USERCENTER_DANMU_RESULT_CODE_MSG_MAP.put(UserTpConstant.USERCENTER_DANMU_RESULT_CODE_200,
        // "USERCENTER.DANMU_RESULT_CODE_200");
        USERCENTER_DANMU_RESULT_CODE_MSG_MAP.put(UserTpConstant.USERCENTER_DANMU_RESULT_CODE_400,
                "USERCENTER_DANMU_RESULT_CODE_400");
        USERCENTER_DANMU_RESULT_CODE_MSG_MAP.put(UserTpConstant.USERCENTER_DANMU_RESULT_CODE_401,
                "USERCENTER_DANMU_RESULT_CODE_401");
        USERCENTER_DANMU_RESULT_CODE_MSG_MAP.put(UserTpConstant.USERCENTER_DANMU_RESULT_CODE_402,
                "USERCENTER_DANMU_RESULT_CODE_402");
        USERCENTER_DANMU_RESULT_CODE_MSG_MAP.put(UserTpConstant.USERCENTER_DANMU_RESULT_CODE_403,
                "USERCENTER_DANMU_RESULT_CODE_403");
        USERCENTER_DANMU_RESULT_CODE_MSG_MAP.put(UserTpConstant.USERCENTER_DANMU_RESULT_CODE_404,
                "USERCENTER_DANMU_RESULT_CODE_404");
        USERCENTER_DANMU_RESULT_CODE_MSG_MAP.put(UserTpConstant.USERCENTER_DANMU_RESULT_CODE_405,
                "USERCENTER_DANMU_RESULT_CODE_405");
        USERCENTER_DANMU_RESULT_CODE_MSG_MAP.put(UserTpConstant.USERCENTER_DANMU_RESULT_CODE_406,
                "USERCENTER_DANMU_RESULT_CODE_406");
        USERCENTER_DANMU_RESULT_CODE_MSG_MAP.put(UserTpConstant.USERCENTER_DANMU_RESULT_CODE_500,
                "USERCENTER_DANMU_RESULT_CODE_500");
    }

    /**
     * 根据用户中心（点播/直播）弹幕相关接口响应状态码获取对应文案key，未识别的状态码统一返回默认文案
     * @param code
     * @return
     */
    public static String getDanmuResultMsgKeyByCode(String code) {
        String msgCode = USERCENTER_DANMU_RESULT_CODE_MSG_MAP.get(code);
        if (msgCode == null) {
            msgCode = "USERCENTER_DANMU_RESULT_CODE_DEFAULT";
        }
        return msgCode;
    }

    /**
     * eros 相关的常量类
     * @author liudaojie
     */
    public static final class ErosConstant {
        /**
         * 码流类型
         */
        public static final class DeviceType {
            public static final Integer HIGNSTREAM = 1; // device of hign-stream
            public static final Integer LOWSTREAM = 0; // device of low-stream
        }

        /**
         * operation type
         */
        public static final class Type {
            public static final Integer GET = 1; // get eros-token
            public static final Integer UPDATE = 1; // update eros-token
        }

        /**
         * who call our API
         */
        public enum FromType {
            LEVII(1001, "LEVIDI_MOBILE"); // levidi
            private Integer code;
            private String value;

            private FromType(Integer code, String value) {
                this.setCode(code);
                this.setValue(value);
            }

            // 根据code值寻找类型
            public static FromType findType(Integer code) {
                for (FromType from : FromType.values()) {
                    if (from.code.equals(code)) {
                        return from;
                    }
                }
                return null;
            }

            public Integer getCode() {
                return code;
            }

            public void setCode(Integer code) {
                this.code = code;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

        }
    }
}
