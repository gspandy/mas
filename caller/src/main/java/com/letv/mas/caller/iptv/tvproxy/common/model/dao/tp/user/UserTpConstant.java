package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户相关调用第三方接口常量类
 * @author dunhongqin
 */
public class UserTpConstant {

    /**
     * 查询类型, 查询充值记录 00, 查询消费记录 01, 查询乐点余额02
     */
    public static final String QUERY_TYPE_RECHARGE = "00";
    public static final String QUERY_TYPE_CONSUMPTION = "01";
    public static final String QUERY_TYPE_LETV_POINT = "02";

    public static final String BALANCE_QUERY_DEFAULT_ORIGIN = "tv";

    /**
     * 2016-01-27，临时恢复线上接口
     */
    public static final String USER_CENTER_REGIST_SERVICE = "tv";

    /**
     * 播放记录来源，1--网页，2--手机客户端， 3--平板，4--TV，5--桌面客户端（PC桌面程序）
     */
    public static final int PLAY_LOG_FROM_TV = 4;
    /**
     * 父母播单md5秘钥
     */
    public static final String EDIT_ROLE_PLAYLIST_SIGN_KEY = "@lesetsign";

    public static final String USER_TOKEN_LOGIN_ALL = "1";
    public static final String USER_TOKEN_LOGIN_PLATFORM_TV = "letv_box_tv";
    /**
     * SSO分配的TV Le plat
     */
    public static final String LECOM_SSO_PLAT_TV = "letv_us_301";

    public static final String USER_EXPERIMENT_APPID_LETV = ApplicationUtils
            .get(ApplicationConstants.VIDEO_EXPERIMENT_APPID);// 乐视视频的实验数据中的应用id
    public static final String USER_EXPERIMENT_VIDEO_BUY_BUTTON = ApplicationUtils
            .get(ApplicationConstants.VIDEO_EXPERIMENT_NAME);// a/b测试的实验名称
    public static final String USER_EXPERIMENT_PARAMETER_SHOW = ApplicationUtils
            .get(ApplicationConstants.VIDEO_EXPERIMENT_PARAMETER_NAME);// 实验数据中的参数名称
    public static final String USER_EXPERIMENT_DEFAULT_VALUE = ApplicationUtils
            .get(ApplicationConstants.VIDEO_EXPERIMENT_DEFAULT_VALUE_LETV);// 实验默认值

    /**
     * token login url
     */
    public final static String USER_TOKEN_LOGIN_URL = ApplicationConstants.USERCENTER_API_SSO_BASE_HOST
            + "/api/checkTicket/tk/";

    /**
     * get user oauth url
     */
    public final static String USER_OAUTH_CODE_URL = ApplicationConstants.USERCENTER_SSO_BASE_HOST
            + "/oauthapp/authorize";

    /**
     * get user cptoken url
     */
    public final static String USER_API_EROSNOW_URL = ApplicationConstants.USER_API_EROSNOW_BASE_HOST
            + "/api/v2/secured/user/letvlogin";

    /**
     * if user icon is blank then use this default icon
     */
    public static final String USER_ACOUNT_DEFAULT_PIC = ApplicationUtils
            .get(ApplicationConstants.USERCENTER_VRS_USER_ACCOUNT_DEFAULT_PIC);

    /**
     * get user play list url
     */
    public static final String GET_PLAY_TAG_LIST_URL = ApplicationConstants.USERCENTER_API_MY_BASE_HOST
            + "/play/list?page={page}&pagesize={pagesize}&sso_tk={sso_tk}";

    /**
     * add favorite album url
     */
    public static final String ADD_PLAYLIST_URL = ApplicationConstants.USERCENTER_API_MY_BASE_HOST
            + "/play/add?sso_tk={sso_tk}&cid={cid}&pid={pid}&vid={vid}&platform={platform}&fromtype={fromtype}";

    /**
     * check if user has collect the album url
     */
    public static final String CHKCK_PLAYLIST_URL = ApplicationConstants.USERCENTER_API_MY_BASE_HOST
            + "/play/fromtype?pid={pid}&sso_tk={sso_tk}";

    /**
     * delete user favorite album url
     */
    public static final String DELETE_PLAYLIST_URL = ApplicationConstants.USERCENTER_API_MY_BASE_HOST
            + "/play/delete?id={id}&sso_tk={sso_tk}";

    /** 删除用户的全部追剧与收藏 */
    public static final String DELETE_ALL_PLAYLIST_URL = ApplicationUtils
            .get(ApplicationConstants.USERCENTER_PLAY_DELALL_PLAYTAGURL);

    /**
     * get user play log url
     */
    public static final String getPlayLogUrl() {
        return ApplicationConstants.USERCENTER_API_MY_BASE_HOST + "/vcs/list";
    }

    /**
     * delete user play log url
     */
    public static final String getDeletePlayLogUrl() {
        return ApplicationConstants.USERCENTER_API_MY_BASE_HOST + "/vcs/delete";
    }

    public static final String getUpdatePlayTimeUrl() {
        return ApplicationConstants.USERCENTER_API_MY_BASE_HOST + "/vcs/set";
    }

    /**
     * get user vip info url
     */
    public static final String USER_VIPINFO_GET_URL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/getService.ldo?";

    /**
     * get user info by username url
     */
    public static final String USER_INFO_GET_URL = ApplicationConstants.USERCENTER_API_SSO_BASE_HOST
            + "/api/getUserByName/username/{username}/dlevel/total";

    /**
     * get user lepoint balance url
     */
    public final static String USER_LEPOINT_BALANCE_GET_URL = ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST
            + "/querylepoint/";

    /**
     * get user info by userId
     */
    public static final String GET_USER_INFO_BY_USERID_URL = ApplicationConstants.USERCENTER_API_SSO_BASE_HOST
            + "/api/getUserById/uid/{userid}/dlevel/total";

    /**
     * add role for user url
     */
    public static final String ADD_ROLE_INFO_URL = ApplicationConstants.USERCENTER_API_SSO_BASE_HOST
            + "/api/addUserRole";

    /**
     * update user role info url
     */
    public static final String UPDATE_ROLE_INFO_URL = ApplicationConstants.USERCENTER_API_SSO_BASE_HOST
            + "/api/modifyUserRoleInfo";

    /**
     * a/b test url
     */
    public static final String EXPERIMENT_ABTEST_URL = ApplicationConstants.EXPERIMENT_ABTEST_BASE_HOST + "/param/";

    /**
     * 删除角色URL
     */
    public static final String DELETE_ROLE_INFO_URL = ApplicationUtils
            .get(ApplicationConstants.USERCENTER_API_SSO_ROLE_INFO_DELETE_BY_ROLEID);
    /**
     * 获取角色列表URL
     */
    public static final String GET_ROLE_INFO_URL = ApplicationUtils
            .get(ApplicationConstants.USERCENTER_API_SSO_ROLE_INFO_GET_BY_ROLEID);
    /**
     * 播单中是否存在此专辑
     */
    public static final String CHECK_ROLE_ALBUM_EXISTS = ApplicationUtils
            .get(ApplicationConstants.USERCENTER_API_MY_LETV_COM_LESET_EXIST);
    /**
     * 编辑角色默认播单
     */
    public static final String ADD_ROLE_PLAY_LIST_URL = ApplicationUtils
            .get(ApplicationConstants.USERCENTER_API_MY_LETV_COM_ADD_ROLE_PLAYLIST);
    /**
     * 删除角色默认播单中专辑
     */
    public static final String DELETE_ALUMBS_BY_LESETID_URL = ApplicationUtils
            .get(ApplicationConstants.USERCENTER_API_MY_LETV_COM_DELETE_ALUMBS_BY_LESETID);
    /**
     * 获取角色默认播单中专辑
     */
    public static final String GET_ALUMBS_BY_LESETID_URL = ApplicationUtils
            .get(ApplicationConstants.USERCENTER_API_MY_LETV_COM_GET_ALUMBS_BY_LESETID);

    /**
     * 校验手机号是否存在（是否是注册用户）
     */
    public static final String CHECK_MOBILE_EXISTS = ApplicationUtils
            .get(ApplicationConstants.USERCENTER_API_SSO_CHECK_MOBILE_EXISTS);

    /**
     * 获取专题收藏列表
     */
    // public static final String GET_FAVORITELIST_URL = ApplicationUtils
    // .get(ApplicationConstants.USERCENTER_FAVORITE_GETLISTURL);
    public static final String getFavoriteListUrl() {
        return ApplicationConstants.USERCENTER_API_MY_BASE_HOST + "/favorite/listfavorite";
    }

    /**
     * 收藏专题
     */
    // public static final String GET_ADDFAVORITE_URL = ApplicationUtils
    // .get(ApplicationConstants.USERCENTER_FAVORITE_ADDURL);
    public static final String getAddFavoriteUrl() {
        return ApplicationConstants.USERCENTER_API_MY_BASE_HOST + "/favorite/add";
    }

    /**
     * 验证专题是否收藏
     */
    // public static final String GET_CHKFAVORITE_URL = ApplicationUtils
    // .get(ApplicationConstants.USERCENTER_FAVORITE_CHKURL);
    public static final String getCheckFavoriteUrl() {
        return ApplicationConstants.USERCENTER_API_MY_BASE_HOST + "/favorite/isfavorite";
    }

    /**
     * 批量取消专题收藏
     */
    // public static final String GET_DELFAVORITE_URL = ApplicationUtils
    // .get(ApplicationConstants.USERCENTER_FAVORITE_DELURL);
    public static final String getMultiDelFavoriteUrl() {
        return ApplicationConstants.USERCENTER_API_MY_BASE_HOST + "/favorite/multidelete";
    }

    /**
     * 取消专题收藏
     */
    // public static final String GET_DELFAVORITEBYZTID_URL = ApplicationUtils
    // .get(ApplicationConstants.USERCENTER_FAVORITE_DELBYZTIDURL);
    public static final String getDelFavoriteUrl() {
        return ApplicationConstants.USERCENTER_API_MY_BASE_HOST + "/favorite/delete";
    }

    /**
     * 添加关注url
     */
    public static final String getAddFollowUrl() {
        return ApplicationConstants.USERCENTER_API_MY_BASE_HOST + "/follow/follow";
    }

    /**
     * 获取关注列表url
     */
    public static final String getFollowListUrl() {
        return ApplicationConstants.USERCENTER_API_MY_BASE_HOST + "/follow/followlist";
    }

    /**
     * 获取检查关注状态url
     */
    public static final String getCheckIsFollowUrl() {
        return ApplicationConstants.USERCENTER_API_MY_BASE_HOST + "/follow/check";
    }

    /**
     * 获取取消关注url
     */
    public static final String getDelFollowUrl() {
        return ApplicationConstants.USERCENTER_API_MY_BASE_HOST + "/follow/cancel";
    }

    /**
     * 获取用户评论url
     */
    public static final String getCommitListUrl() {
        return ApplicationConstants.USERCENTER_API_MY_BASE_HOST + "/vcm/api/list";
    }

    /**
     * 添加收藏新接口url
     */
    public static final String getAddCollectionUrl() {
        return ApplicationConstants.USERCENTER_API_MY_BASE_HOST + "/collection/set";
    }

    /**
     * 获取收藏新接口url
     */
    public static final String getCollectionUrl() {
        return ApplicationConstants.USERCENTER_API_MY_BASE_HOST + "/collection/get";
    }

    /**
     * 获取收藏列表新接口url
     */
    public static final String getCollectionListUrl() {
        return ApplicationConstants.USERCENTER_API_MY_BASE_HOST + "/collection/list";
    }

    /**
     * 获取删除收藏新接口url
     */
    public static final String getDelCollectionUrl() {
        return ApplicationConstants.USERCENTER_API_MY_BASE_HOST + "/collection/delete";
    }

    /**
     * 直播、轮播、卫视发送URL
     */
    public static final String USERCENTER_LIVE_CHATROOM_SEND_MSG_URL = ApplicationConstants.USERCENTER_API_MY_BASE_HOST
            + "/chat/message?roomId={roomId}&clientIp={clientIp}&message={message}&color={color}&font={font}&position={position}&from={from}&forhost={forhost}&sso_tk={sso_tk}&type={type}&realname={realname}&x={x}&y={y}&vtkey={vtkey}&callback={callback}";

    /**
     * 用户中心登录接口；2016-01-27，临时恢复线上接口
     */
    public static final String USERCENTER_LOGIN = ApplicationConstants.USERCENTER_API_SSO_BASE_HOST
            + "/api/clientLogin";

    /**
     * 用户中心注册接口；2016-01-27，临时恢复线上接口
     * ?mobile={mobile}&code={code}&plat={plat}&isCIBN={isCIBN}
     */
    public static final String USERCENTER_REGISTER = ApplicationConstants.USERCENTER_API_SSO_BASE_HOST
            + "/api/clientLogin/api/clientShortcutReg";

    // 登录信息同步至boss
    public final static String SYNC_USER_LOGIN_INFO_TO_BOSS = ApplicationUtils
            .get(ApplicationConstants.BOSS_PRICE_ZHIFU_SYNC_USER_LOGIN_INFO);
    /**
     * 获取弹幕列表
     */
    public static final String GET_DMLIST_URL = ApplicationConstants.HD_MY_LETV_BASE_HOST
            + "/danmu/list?vid={vid}&amount={amount}&start={start}&sso_tk={sso_tk}&from={from}";

    /**
     * 获取弹幕数量
     */
    public static final String GET_DMCOUNT_URL = ApplicationUtils.get(ApplicationConstants.USERCENTER_DM_GETCONTURL);

    /**
     * 点播弹幕
     */
    public static final String USERCENTER_DM_ADD_URL = ApplicationUtils.get(ApplicationConstants.USERCENTER_DM_ADD_URL);

    /**
     * get deviceKey by mac url
     */
    public static final String USER_DEVICE_INFO_URL = ApplicationConstants.BOSS_PRICE_ZHIFU_BASE_HOST
            + "/devicekeyinfo/get?";

    /**
     * verify user account password
     * /api/verifyPwd?ssotk={ssotk}&password={password}&plat={plat}&lang={lang}&
     * country={country}
     */
    public static final String VERIFY_USER_ACCOUNT_PWSSWORD_URL = ApplicationConstants.USERCENTER_API_SSO_BASE_HOST
            + "/api/verifyPwd";

    public final static Map<Integer, String> errorCodeMap = new HashMap<Integer, String>();

    /**
     * 用户中心（点播/直播）弹幕相关接口结果响应中的业务状态码；包括200--成功；400--必填参数为空；401--含有敏感词；402--发布太频繁，
     * 喝口水吧；403--未登录；404--用户被禁言；405--没有实名认证；406--没有ip；500--失败
     */
    public static final String USERCENTER_DANMU_RESULT_CODE_200 = "200";
    public static final String USERCENTER_DANMU_RESULT_CODE_400 = "400";
    public static final String USERCENTER_DANMU_RESULT_CODE_401 = "401";
    public static final String USERCENTER_DANMU_RESULT_CODE_402 = "402";
    public static final String USERCENTER_DANMU_RESULT_CODE_403 = "403";
    public static final String USERCENTER_DANMU_RESULT_CODE_404 = "404";
    public static final String USERCENTER_DANMU_RESULT_CODE_405 = "405";
    public static final String USERCENTER_DANMU_RESULT_CODE_406 = "406";
    public static final String USERCENTER_DANMU_RESULT_CODE_500 = "500";

    static {
        errorCodeMap.put(400, "必填参数为空");
        errorCodeMap.put(401, "含有敏感词");
        errorCodeMap.put(402, "发布太频繁，喝口水吧");
        errorCodeMap.put(403, "未登录");
        errorCodeMap.put(404, "用户被禁言");
        errorCodeMap.put(405, "没有实名认证");
        errorCodeMap.put(500, "失败");
    }

    /**
     * <b>用户中心appid对照表</b><p>
     * 1001 乐视视频PC端<p>
     * 1002 移动端乐视视频（基线版）<p>
     * 1003 自有手机乐视视频（领先版）<p>
     * 1004 乐视视频TV端<p>
     * 1011 乐见手机（影视版）<p>
     * 1012 乐见手机（视频版）<p>
     * 1013 乐见手机（图文版）<p>
     * 1021 超级LIVE移动端<p>
     * 1022 超级LIVE TV端<p>
     * 1041 LeVidi（手机端）<p>
     * 1042 LeVidi（TV端）<p>
     * 1061 乐听<p>
     */
    public enum APPID {
        LETV_PC(1001),
        LETV_TV(1004),
        LEVIDI_MOBILE_PHONE(1041),
        LEVIDI_TV(1042),
        LETING(1061),
        LE(1111),
        LETV_TV_CHILD(1070), // 乐视儿童
        LETV_TV_CIBN(1080); // 乐视视频
        private Integer code;

        private APPID(Integer code) {
            this.code = code;
        }

        /*
         * find type by code
         */
        public APPID findType(Integer code) {
            for (APPID appid : APPID.values()) {
                if (appid.code.equals(code)) {
                    return appid;
                }
            }
            return null;
        }

        /**
         * 根据应用版本获取APPID
         * @param terminalApplication
         * @return
         */
        public static final APPID getAppId(String terminalApplication) {
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(terminalApplication)) { // levidi
                return APPID.LEVIDI_TV;
            } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(terminalApplication)) { // le
                return APPID.LE;
            }
            return null;
        }

        public static final APPID getAppIdNew(String terminalApplication) {
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(terminalApplication)) { // levidi
                return APPID.LEVIDI_TV;
            } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(terminalApplication)) { // le
                return APPID.LE;
            } else if (TerminalCommonConstant.TERMINAL_APPLICATION_MEDIA_CIBN.equalsIgnoreCase(terminalApplication)) {
                return APPID.LETV_TV_CIBN;
            } else if (TerminalCommonConstant.TERMINAL_APPLICATION_CHILD_APP.equalsIgnoreCase(terminalApplication)
                    || TerminalCommonConstant.TERMINAL_APPLICATION_LECHILD_ALONE.equalsIgnoreCase(terminalApplication)) {
                return APPID.LETV_TV_CHILD;
            }
            return null;
        }

        public Integer getCode() {
            return code;
        }

        @Override
        public String toString() {
            return this.code + "";
        }
    }

    /**
     * 播放记录来源，1--网页，2--手机客户端， 3--平板，4--TV，5--桌面客户端（PC桌面程序）
     */
    public enum PLAY_LOG_FROM {
        WEB(1), // 网页
        MOBILE(2), // 手机客户端
        PAD(3), // 平板
        TV(4), // TV
        PC(5); // 桌面客户端（PC桌面程序）
        private Integer code;

        private PLAY_LOG_FROM(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        /*
         * find type by code
         */
        public static final PLAY_LOG_FROM findType(Integer code) {
            for (PLAY_LOG_FROM from : PLAY_LOG_FROM.values()) {
                if (from.code.equals(code)) {
                    return from;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return this.code + "";
        }
    }

    /*
     * 用户评论来源
     */
    public enum COMMIT_SOURCE {
        WEB(1),
        IPHONE(2),
        ANDROID(3),
        WPHONE(4),
        PAD(5),
        TV(6),
        PC(7),
        MSITE(8),
        SINAWEIBO(9),
        LEPHONE(10),
        SARRS(30),
        LESO(31);
        private Integer code;

        private COMMIT_SOURCE(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        /*
         * find type by code
         */
        public static final COMMIT_SOURCE findType(Integer code) {
            for (COMMIT_SOURCE from : COMMIT_SOURCE.values()) {
                if (from.code.equals(code)) {
                    return from;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return this.code + "";
        }
    }

    /*
     * 用户关注类型
     */
    public enum FOLLOW_TYPE {
        FOLLOW("follow", "用户"),
        STAR("star", "明星"),
        LEVIDI("vidi", "levidi");
        private String code;
        private String desc;

        private FOLLOW_TYPE(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        /*
         * find type by code
         */
        public static final FOLLOW_TYPE findType(Integer code) {
            for (FOLLOW_TYPE type : FOLLOW_TYPE.values()) {
                if (type.code.equals(code)) {
                    return type;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return this.code;
        }
    }

    /**
     * 关注来源，1 -- 手机客户端， 3 -- TV
     */
    public enum FOLLOW_FROM {
        MOBILE(1), // 手机客户端
        TV(3); // TV
        private Integer code;

        private FOLLOW_FROM(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        /*
         * find type by code
         */
        public static final FOLLOW_FROM findType(Integer code) {
            for (FOLLOW_FROM from : FOLLOW_FROM.values()) {
                if (from.code.equals(code)) {
                    return from;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return this.code + "";
        }
    }
}
