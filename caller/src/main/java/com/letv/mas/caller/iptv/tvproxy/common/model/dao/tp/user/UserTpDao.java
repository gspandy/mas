package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ErrorCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request.LoginRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request.PlayTagListRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request.RolePlayListRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request.UserRoleRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import org.apache.axis.encoding.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpConstant.APPID;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpConstant.FOLLOW_TYPE;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpConstant.FOLLOW_FROM;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants.BROADCAST_TYPE;

@Component
public class UserTpDao extends BaseTpDao {

    private final Logger log = LoggerFactory.getLogger(UserTpDao.class);

    private final String consumer_key = "bba5eeb1ea938d2c89993d4e9c3a790d056b16c25";
    private final String consumer_secret = "df98edf3027073a7a2851cab360ffd56";

    /**
     * 获取用户播放单，追剧，收藏列表
     * @param page
     * @param pagesize
     * @param token
     * @param locale
     * @return
     */
    public LetvUserPlayListTp getPlayTagList(PlayTagListRequest tagReq, Locale locale) {
        LetvUserPlayListTp playListTp = null;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", tagReq.getPage());
        params.put("pagesize", tagReq.getPagesize());
        params.put("sso_tk", tagReq.getToken());

        try {
            String result = this.restTemplate.getForObject(UserTpConstant.GET_PLAY_TAG_LIST_URL, String.class, params);

            /**
             * 暂时解决会员中心播放单记录为空时,list属性返回""无法解析 的bug,下个版本调用会员中心新接口
             */
            if (StringUtil.isNotBlank(result)) {
                if (result.contains("\"total\":0")) {
                    result = result.replace("\"list\":\"\"", "\"list\":[]");
                }
                playListTp = this.objectMapper.readValue(result, LetvUserPlayListTp.class);
            }
        } catch (Exception e) {
            this.log.error("getPlayTagList_" + tagReq.getUsername() + ": ", e);
        }

        return playListTp;
    }

    /**
     * get user play list and favorite
     * @param page
     * @param pageSize
     * @param commonParam
     * @return
     */
    public LetvUserPlayListTp getPlayTagAndAlbumList(Integer page, Integer pageSize, CommonParam commonParam) {
        LetvUserPlayListTp playListTp = null;

        try {
            Map<String, Object> parametersMap = new HashMap<String, Object>();
            parametersMap.put("sso_tk", commonParam.getUserToken());
            parametersMap.put("page", page);
            parametersMap.put("pagesize", pageSize);
            // http://api.my.letv.com/play/list?page={page}&pagesize={pagesize}&sso_tk={sso_tk}
            String result = this.restTemplate.getForObject(UserTpConstant.GET_PLAY_TAG_LIST_URL, String.class,
                    parametersMap);
            // this.log.info(logPrefix + ": invoke return [" + result + "]");

            /**
             * 暂时解决会员中心播放单记录为空时,list属性返回""无法解析 的bug,下个版本调用会员中心新接口
             */
            if (StringUtil.isNotBlank(result)) {
                if (result.contains("\"total\":0")) {
                    result = result.replace("\"list\":\"\"", "\"list\":[]");
                }
                playListTp = this.objectMapper.readValue(result, LetvUserPlayListTp.class);
            }
        } catch (Exception e) {
            this.log.error("getPlayTagAndAlbumList_" + commonParam.getMac() + "_" + commonParam.getUserId()
                    + " return error: ", e);
        }

        return playListTp;
    }

    /**
     * add favorite album
     * @param albumId
     * @param videoId
     * @param categoryId
     * @param platForm
     *            the plat from value
     * @param fromType
     *            from type
     * @param commonParam
     * @return
     */
    public PlayListCommonTp addPlayList(Long albumId, Long videoId, Integer categoryId, Integer platForm,
                                        Integer fromType, CommonParam commonParam) {
        PlayListCommonTp commonTp = null;

        try {
            Map<String, Object> parametersMap = new HashMap<String, Object>();
            parametersMap.put("sso_tk", commonParam.getUserToken());
            parametersMap.put("cid", categoryId);
            parametersMap.put("vid", videoId);
            parametersMap.put("pid", albumId);
            parametersMap.put("platform", platForm);
            parametersMap.put("fromtype", fromType);
            // http://api.my.letv.com/play/add?sso_tk={sso_tk}&cid={cid}&pid={pid}&vid={vid}&platform={platform}&fromtype={fromtype}
            String result = this.restTemplate
                    .getForObject(UserTpConstant.ADD_PLAYLIST_URL, String.class, parametersMap);
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                commonTp = this.objectMapper.readValue(result, PlayListCommonTp.class);
            }
        } catch (Exception e) {
            this.log.error("addPlayList_" + commonParam.getMac() + "_" + commonParam.getUserId() + " return error: ", e);
        }

        return commonTp;
    }

    /**
     * 检查剧集追剧,收藏状态
     * @param checkPlaylistRequest
     * @param locale
     *            调用第三方环境locale
     * @return
     */
    public CheckPlaylistTp checkPlaylist(Long albumid, CommonParam commonParam) {
        CheckPlaylistTp checkTp = null;

        try {
            // http://api.my.letv.com/play/fromtype?pid={pid}&sso_tk={sso_tk}
            Map<String, Object> parametersMap = new HashMap<String, Object>();
            parametersMap.put("sso_tk", commonParam.getUserToken());
            parametersMap.put("pid", albumid);
            String result = this.restTemplate.getForObject(UserTpConstant.CHKCK_PLAYLIST_URL, String.class,
                    parametersMap);

            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                checkTp = this.objectMapper.readValue(result, CheckPlaylistTp.class);
            }
        } catch (Exception e) {
            this.log.error("checkPlaylist_" + commonParam.getMac() + "_" + commonParam.getUserId() + " return error: ",
                    e);
        }

        return checkTp;
    }

    /**
     * 删除用户追剧和收藏信息
     * @param deletePlaylistRequest
     * @param locale
     *            调用第三方接口locale
     * @return
     */
    public PlayListCommonTp deletePlaylist(Long favid, CommonParam commonParam) {
        PlayListCommonTp commonTp = null;

        try {
            Map<String, Object> parametersMap = new HashMap<String, Object>();
            parametersMap.put("id", favid);
            parametersMap.put("sso_tk", commonParam.getUserToken());
            // http://api.my.letv.com/play/delete?id={id}&sso_tk={sso_tk}
            String result = this.restTemplate.getForObject(UserTpConstant.DELETE_PLAYLIST_URL, String.class,
                    parametersMap);
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                commonTp = objectMapper.readValue(result, PlayListCommonTp.class);
            }
        } catch (Exception e) {
            this.log.error("deletePlaylist_" + commonParam.getMac() + "_" + commonParam.getLangcode()
                    + " return error: ", e);
        }

        return commonTp;
    }

    /**
     * 删除用户的所有追剧和收藏信息
     * @param deletePlaylistRequest
     * @param locale
     *            调用第三方接口locale
     * @return
     */
    public PlayListCommonTp deleteAllPlaylist(String username, CommonParam commonParam) {
        PlayListCommonTp commonTp = null;
        String logPrefix = "deleteAllPlaylist_" + username + "_" + commonParam.getUserId() + "_"
                + commonParam.getUserToken();
        StringBuilder url = new StringBuilder(UserTpConstant.DELETE_ALL_PLAYLIST_URL);
        try {
            url.append("sso_tk=").append(commonParam.getUserToken());
            url.append("&callback=").append("");
            // http://api.my.letv.com/play/deletefromuser?sso_tk={sso_tk}&callback={callback}
            String result = this.restTemplate.getForObject(url.toString(), String.class);

            if (StringUtil.isNotBlank(result)) {
                commonTp = this.objectMapper.readValue(result, PlayListCommonTp.class);
            }
        } catch (Exception e) {
            this.log.error(logPrefix + " return error: ", e);
        }

        return commonTp;
    }

    /**
     * 获取播放记录列表
     * @param page
     *            当前页，从1开始
     * @param pageSize
     *            每页条数，默认 10
     * @param intervalTime
     *            最近n天的播放记录，时间戳
     * @param roleid
     *            角色id
     * @param type
     *            0：全部记录，1：点播记录，2：直播，3：轮播，4：卫视，10：外网
     * @param appId
     *            应用ID,见 {@link UserTpConstant.APPID}
     * @param from
     *            来源 （1：WEB，2：MOBILE，3：PAD， 4：TV， 5：PC桌面 ，默认1：web）
     * @param commonParam
     * @return
     */
    public PlayLogTp getPlaylogList(Integer page, Integer pageSize, Integer intervalTime, Long roleid, Integer type,
                                    UserTpConstant.APPID appId, Integer from, String region, CommonParam commonParam) {
        PlayLogTp playTp = null;
        if (pageSize != null && (pageSize < 1 || pageSize > 60)) {
            pageSize = 60;// 用户中心限制了pageSize不能大于60
        }
        StringBuilder subUrl = new StringBuilder();
        subUrl.append(UserTpConstant.getPlayLogUrl()).append("?");
        // subUrl.append("uid=").append(commonParam.getUserId()); //不用传uid
        subUrl.append("sso_tk=").append(commonParam.getUserToken());
        subUrl.append("&page=").append(page);
        subUrl.append("&pagesize=").append(pageSize);
        subUrl.append("&rid=").append(roleid == null ? 0 : roleid);
        if (intervalTime != null) {
            subUrl.append("&btime=").append(intervalTime);
        }
        if (commonParam.getLangcode() != null) {
            subUrl.append("&lang=").append(commonParam.getLangcode());
        }
        if (type != null) {
            subUrl.append("&type=").append(type);
        }
        if (appId != null) {
            // 因上游对之前未传appid的case默认按0处理，兼容旧数据需多传一组
            subUrl.append("&appid=").append("0,").append(appId.getCode());
        }
        if (from != null) {
            subUrl.append("&from=").append(from);
        }
        if (StringUtil.isNotBlank(region)) {
            subUrl.append("&regionFilter=").append(region.toUpperCase());
        }

        // Map<String, Object> params = new HashMap<String, Object>();
        // params.put("uid", commonParam.getUserId());
        // params.put("sso_tk", commonParam.getUserToken());
        // params.put("page", page);
        // params.put("pagesize", pageSize);
        // params.put("btime", intervalTime);
        // params.put("rid", roleid == null ? 0 : roleid);// 儿童模式加角色字段默认0
        // params.put("lang", commonParam.getLangcode());
        // params.put("region", commonParam.getWcode());
        // params.put("type", type);// India version need to distinguish the
        // // play log type
        // if (LocaleConstant.Wcode.IN.equalsIgnoreCase(commonParam.getWcode()))
        // {
        // params.put("appid", CommonConstants.APPID.LEVIDI_TV.getCode());
        // }
        // String url = UserTpConstant.GET_PLAYLOG_URL;
        // if (intervalTime == null) {
        // url = url.replace("&btime={btime}", "");
        // }
        String result = null;
        try {
            // http://api.my.letv.com/vcs/list?uid={uid}&sso_tk={sso_tk}&page={page}&pagesize={pagesize}&lang={lang}&region={region}&btime={btime}&rid={rid}&type={type}&appid={appid}&from=4
            result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            // this.log.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                playTp = objectMapper.readValue(result, PlayLogTp.class);
            }
        } catch (Exception e) {
            this.log.error("getPlaylogList_" + commonParam.getMac() + "_" + commonParam.getUserToken()
                    + " return error: ", e);
        }

        return playTp;
    }

    /**
     * 删除用户播放记录
     * @param albumId
     *            专辑id
     * @param videoId
     *            视频id
     * @param globalId
     *            作品库数据的globalId
     * @param roleId
     *            角色ID,ID为正整数（不传默认为主账号）
     * @param flush
     *            是否清空（1：清空，0：非清空）
     * @param appId
     *            应用ID,见 {@link UserTpConstant.APPID}
     * @param commonParam
     * @return
     */
    public PlaylogCommonTp deletePlaylog(String albumId, String videoId, String globalId, Long roleId, Integer flush,
                                         APPID appId, CommonParam commonParam) {
        PlaylogCommonTp response = null;
        try {
            // Map<String, Object> parametersMap = new HashMap<String,
            // Object>();
            // parametersMap.put("uid", commonParam.getUserId());
            // parametersMap.put("sso_tk", commonParam.getUserToken());//
            // userToken
            // parametersMap.put("lid", globalId);
            // if (appId != null) {
            // parametersMap.put("appid", appId.getCode());
            // }
            // parametersMap.put("pid", albumId);
            // parametersMap.put("vid", videoId);
            // parametersMap.put("rid", roleid == null ? 0 : roleid);//
            // 儿童模式加角色id,默认为0

            StringBuilder subUrl = new StringBuilder();
            subUrl.append(UserTpConstant.getDeletePlayLogUrl()).append("?");
            subUrl.append("sso_tk=").append(commonParam.getUserToken());
            if (roleId != null) {
                subUrl.append("&rid=").append(roleId);
            }
            if (StringUtil.isNotBlank(albumId)) {
                subUrl.append("&pid=").append(albumId);
            }
            if (StringUtil.isNotBlank(videoId)) {
                subUrl.append("&vid=").append(videoId);
            }
            if (StringUtil.isNotBlank(globalId)) {
                subUrl.append("&lid=").append(globalId);
            }
            if (flush != null) {
                subUrl.append("&flush=").append(flush);
            }
            if (appId != null) {
                subUrl.append("&appid=").append(appId.getCode());
            }

            // http://api.my.letv.com/vcs/delete?uid={uid}&sso_tk={sso_tk}&pid={pid}&vid={vid}&rid={rid}&lid={lid}
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                response = objectMapper.readValue(result, PlaylogCommonTp.class);
            }
        } catch (Exception e) {
            this.log.info("deletePlaylog_" + commonParam.getMac() + "_" + commonParam.getUserId()
                    + ": delete playlog [albumid=" + albumId + ",videoid=" + videoId + ",globalid=" + globalId
                    + "] [error]:", e);
        }

        return response;
    }

    /**
     * delete user all play log
     * @param roleid
     *            角色ID,ID为正整数（不传默认为主账号）
     * @param appId
     *            应用ID,见 {@link UserTpConstant.APPID}
     * @param commonParam
     * @return
     */
    // public PlaylogCommonTp deleteAllPlaylog(Long roleid, APPID appId,
    // CommonParam commonParam) {
    // PlaylogCommonTp response = null;
    //
    // try {
    // Map<String, Object> parametersMap = new HashMap<String, Object>();
    // parametersMap.put("uid", commonParam.getUserId());
    // parametersMap.put("sso_tk", commonParam.getUserToken());
    // // if roleid is not empty then delete the roleid play log
    // parametersMap.put("rid", roleid == null ? 0 : roleid);
    // if (appId != null) {
    // parametersMap.put("appid", appId.getCode());
    // }
    // //
    // http://api.my.letv.com/vcs/delete?uid={uid}&sso_tk={sso_tk}&flush=1&rid={rid}
    // String result =
    // this.restTemplate.getForObject(UserTpConstant.DELETE_PLAYLOG_ALL_URL,
    // String.class,
    // parametersMap);
    // if (StringUtil.isNotBlank(result)) {
    // result = URLDecoder.decode(result, "UTF-8");
    // response = objectMapper.readValue(result, PlaylogCommonTp.class);
    // }
    // } catch (Exception e) {
    // this.log.info(
    // "deleteAllPlaylog_" + commonParam.getMac() + "_" +
    // commonParam.getUserId() + " return error:", e);
    // }
    // return response;
    // }

    /**
     * update user video play log
     * @param albumid
     * @param videoid
     * @param categoryId
     * @param nextVideoId
     * @param from
     * @param playTime
     * @param roleid
     * @param commonParam
     * @return
     */
    public PlaylogCommonTp updatePlayTime(Long albumId, Long videoId, String globalId, Integer categoryId,
            Long playTime, Long roleId, Integer from, APPID appId, String region, CommonParam commonParam) {
        PlaylogCommonTp response = null;
        try {
            // Map<String, Object> parametersMap = new HashMap<String,
            // Object>();
            // parametersMap.put("cid", categoryId);
            // parametersMap.put("pid", albumId);
            // parametersMap.put("vid", videoId);
            // // parametersMap.put("nvid", nextVideoId);
            // // parametersMap.put("uid", this.userid);
            // // parametersMap.put("vtype", this.vtype);
            // // It means play log from tv device
            // parametersMap.put("from", from);
            // parametersMap.put("htime", playTime);// last play time
            // parametersMap.put("product", commonParam.getTerminalSeries());
            // parametersMap.put("sso_tk", commonParam.getUserToken());
            // // If roleid is not empty it means add play log to user roleid
            // parametersMap.put("rid", roleId == null ? 0 : roleId);
            // parametersMap.put("clientIp", commonParam.getClientIp());
            // parametersMap.put("lid", globalId);
            // if (appId != null) {
            // parametersMap.put("appid", appId.getCode());
            // }

            StringBuilder subUrl = new StringBuilder();
            subUrl.append(UserTpConstant.getUpdatePlayTimeUrl()).append("?");
            subUrl.append("sso_tk=").append(commonParam.getUserToken());
            if (roleId != null) {
                subUrl.append("&rid=").append(roleId);
            }
            if (albumId != null) {
                subUrl.append("&pid=").append(albumId);
            }
            if (videoId != null) {
                subUrl.append("&vid=").append(videoId);
            }
            if (globalId != null) {
                subUrl.append("&lid=").append(globalId);
            }
            if (categoryId != null) {
                subUrl.append("&cid=").append(categoryId);
            }
            if (playTime != null) {
                subUrl.append("&htime=").append(playTime);
            }
            if (from != null) {
                subUrl.append("&from=").append(from);
            }
            if (appId != null) {
                subUrl.append("&appid=").append(appId.getCode());
            }
            subUrl.append("&clientIp=").append(commonParam.getClientIp());
            subUrl.append("&product=").append(commonParam.getTerminalSeries());
            if (StringUtil.isNotBlank(region)) {
                subUrl.append("&region=").append(region.toUpperCase());
            }

            // http://api.my.letv.com/vcs/set?cid={cid}&pid={pid}&vid={vid}&nvid={nvid}&from={from}&htime={htime}&sso_tk={sso_tk}&product={product}&rid={rid}&clientip={clientIp}
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            log.info("updatePlayTime_" + commonParam.getMac() + "_" + commonParam.getUserId() + ". invoke return :"
                    + result);
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                response = objectMapper.readValue(result, PlaylogCommonTp.class);
            }
        } catch (Exception e) {
            this.log.error("updatePlayTime_" + commonParam.getMac() + "_" + commonParam.getUserId()
                    + ": update play time [pid=" + albumId + ",vid=" + videoId + ",globalid:" + globalId + ",htime="
                    + playTime + "] [error]" + e.getMessage(), e);
        }

        return response;
    }

    /**
     * get user info by userId
     * @param commonParam
     * @return
     */
    public GetUserInfoTpResponse getUserInfoByUserid(CommonParam commonParam) {
        GetUserInfoTpResponse response = null;
        String logPrefix = "getUserInfoByUserid_" + commonParam.getMac() + "_" + commonParam.getUserId();

        try {
            // http://api.sso.letv.com/api/getUserById/uid/{userid}/dlevel/total
            String result = this.restTemplate.getForObject(UserTpConstant.GET_USER_INFO_BY_USERID_URL, String.class,
                    commonParam.getUserId());
            this.log.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, GetUserInfoTpResponse.class);
            }
        } catch (Exception e) {
            this.log.error(logPrefix + " return error:", e);
        }
        return response;
    }

    public RoleCommonPostTP addRole(Long roleid, String nickname, String roleType, String birthday, String gender,
            String duration, String timeStart, String timeEnd, String setAge, CommonParam commonParam) {
        RoleCommonPostTP response = null;
        String logPrefix = "addRole_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + roleType;
        try {
            MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
            parametersMap.add("tk", StringUtils.trimToEmpty(commonParam.getUserToken()));
            parametersMap.add("ip", StringUtils.trimToEmpty(commonParam.getClientIp()));
            // get role info and parse to json string
            parametersMap
                    .add("content", this.getContent(roleid, nickname, roleType, birthday, gender, duration, timeStart,
                            timeEnd, setAge));
            this.log.info("tk=" + commonParam.getUserToken() + " ip=" + commonParam.getClientIp() + " content="
                    + parametersMap.get("content"));
            // http://api.sso.letv.com/api/addUserRole
            String result = this.restTemplate.postForObject(UserTpConstant.ADD_ROLE_INFO_URL, parametersMap,
                    String.class);
            this.log.info(logPrefix + ": invoke return [" + result + "] and the server model is PostMethod");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, RoleCommonPostTP.class);
            }
        } catch (Exception e) {
            this.log.error(logPrefix + ": [error]", e);
        }

        return response;
    }

    /**
     * update user info
     * @param roleid
     * @param nickname
     * @param roleType
     * @param birthday
     * @param gender
     * @param duration
     * @param timeStart
     * @param timeEnd
     * @param setAge
     * @param commonParam
     * @return
     */
    public RoleCommonPostTP updateRole(Long roleid, String nickname, String roleType, String birthday, String gender,
            String duration, String timeStart, String timeEnd, String setAge, CommonParam commonParam) {
        RoleCommonPostTP response = null;
        String logPrefix = "updateRole_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + roleid;

        try {
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
            param.add("tk", StringUtils.trimToEmpty(commonParam.getUserToken()));
            // get user role info and parse to json string
            param.add("content",
                    this.getContent(roleid, nickname, roleType, birthday, gender, duration, timeStart, timeEnd, setAge));
            param.add("rid", String.valueOf(roleid));// user roleid
            // http://api.sso.letv.com/api/modifyUserRoleInfo
            String result = this.restTemplate.postForObject(UserTpConstant.UPDATE_ROLE_INFO_URL, param, String.class);
            this.log.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, RoleCommonPostTP.class);
            }
        } catch (Exception e) {
            this.log.error(logPrefix + ": [error]", e);
        }

        return response;
    }

    /**
     * 删除角色
     * @param UserRoleRequest
     * @param locale
     * @return
     */
    public RoleCommonPostTP deleteRole(UserRoleRequest UserRoleRequest, String logPrefix) {
        RoleCommonPostTP response = null;

        try {
            // http://api.sso.letv.com/api/delUserRoleInfo，因为是post类型请求，所以参数不拼接在url里
            // post参数列表[tk,rid]
            String result = this.restTemplate.postForObject(UserTpConstant.DELETE_ROLE_INFO_URL,
                    UserRoleRequest.deleteParametersMap(), String.class);
            this.log.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, RoleCommonPostTP.class);
            }
        } catch (Exception e) {
            this.log.error(logPrefix + ": [error]", e);
        }

        return response;
    }

    /**
     * 获取角色列表
     * @param UserRoleRequest
     * @param locale
     * @return
     */
    public RoleCommonGetTP getRole(UserRoleRequest UserRoleRequest, String logPrefix) {
        RoleCommonGetTP roleTp = null;
        try {
            // http://api.sso.letv.com/api/getUserRoleList?uid={uid}
            String result = this.restTemplate.getForObject(UserTpConstant.GET_ROLE_INFO_URL, String.class,
                    UserRoleRequest.getParametersMap());
            this.log.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                roleTp = objectMapper.readValue(result, RoleCommonGetTP.class);
            }
        } catch (Exception e) {
            this.log.error(logPrefix + ": [error]", e);
        }
        return roleTp;
    }

    /**
     * 加入播单
     * @param UserRoleRequest
     * @param locale
     * @return
     */
    public RoleCommonPlayListTP editRolePlayList(RolePlayListRequest rolePlayListRequest, String logPrefix) {
        RoleCommonPlayListTP response = null;

        try {
            // http://api.my.letv.com/leset/edit
            String result = this.restTemplate.getForObject(UserTpConstant.ADD_ROLE_PLAY_LIST_URL, String.class,
                    rolePlayListRequest.getEditParametersMap());
            this.log.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, RoleCommonPlayListTP.class);
            }
        } catch (Exception e) {
            this.log.error(logPrefix + ": [error]", e);
        }

        return response;
    }

    /**
     * 删除播单中专辑信息
     * @param UserRoleRequest
     * @param locale
     * @return
     */
    public RoleCommonPlayListTP deleteRolePlayList(RolePlayListRequest rolePlayListRequest, String logPrefix) {
        RoleCommonPlayListTP response = null;

        try {
            // http://api.my.letv.com/leset/deleteVideo
            String result = this.restTemplate.getForObject(UserTpConstant.DELETE_ALUMBS_BY_LESETID_URL, String.class,
                    rolePlayListRequest.getDelParametersMap());
            this.log.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, RoleCommonPlayListTP.class);
            }
        } catch (Exception e) {
            this.log.error(logPrefix + ": [error]", e);
        }

        return response;
    }

    /**
     * 获得播单中专辑信息
     * @param UserRoleRequest
     * @param locale
     * @return
     */
    public RolePlayListTp getRolePlayList(RolePlayListRequest rolePlayListRequest, String logPrefix) {
        RolePlayListTp response = null;
        String result = null;
        try {
            // http://api.my.letv.com/leset/videolist
            result = this.restTemplate.getForObject(UserTpConstant.GET_ALUMBS_BY_LESETID_URL, String.class,
                    rolePlayListRequest.getParametersMap());
            this.log.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, RolePlayListTp.class);
            }
        } catch (Exception e) {
            RolePlayError rolePlayError = null;
            if (StringUtil.isNotBlank(result)) {
                try {
                    rolePlayError = objectMapper.readValue(result, RolePlayError.class);
                } catch (Exception e1) {
                    this.log.error(logPrefix + ": [error]", e1);
                }
            }
            response = new RolePlayListTp();
            if (rolePlayError != null) {
                response.setCode(rolePlayError.getCode());
            }
        }
        return response;
    }

    public LetvUserTokenResponse checkTokenLogin(CommonParam commonParam) {
        LetvUserTokenResponse response = null;
        StringBuilder url = new StringBuilder(UserTpConstant.USER_TOKEN_LOGIN_URL);
        url.append(commonParam.getUserToken()).append("?");
        // get all user info
        url.append("all=").append(UserTpConstant.USER_TOKEN_LOGIN_ALL);
        // tv platform value

        String ssoPlat = TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                .getTerminalApplication()) ? UserTpConstant.LECOM_SSO_PLAT_TV
                : UserTpConstant.USER_TOKEN_LOGIN_PLATFORM_TV;
        url.append("&plat=").append(ssoPlat);
        try {
            // http://api.sso.letv.com/api/checkTicket/tk/{token}?all=1&plat={plat}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            this.log.info("checkTokenLogin_" + commonParam.getMac() + "_" + commonParam.getUserId() + "invoke return:"
                    + result);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, LetvUserTokenResponse.class);
            }
        } catch (Exception e) {
            this.log.error("checkTokenLogin_" + commonParam.getMac() + "_" + commonParam.getUserId()
                    + "checkTokenLogin exception: " + e.getMessage(), e);
        }
        return response;
    }

    /**
     * get user lepoint balance
     * @param commonParam
     * @return
     */
    public BalanceQueryResultResponse queryBalance(CommonParam commonParam) {
        String logPrefix = "queryBalance_" + commonParam.getMac() + "_" + commonParam.getUserId();
        BalanceQueryResultResponse response = null;

        try {
            StringBuilder url = new StringBuilder(UserTpConstant.USER_LEPOINT_BALANCE_GET_URL);
            url.append(commonParam.getUserId()).append("?");
            // from tv terminal
            url.append("origin=").append(UserTpConstant.BALANCE_QUERY_DEFAULT_ORIGIN);
            // generate md5 sign by userId
            url.append("&auth=").append(this.getBalanceQueryAuth(commonParam.getUserId()));

            // http://api.zhifu.letv.com/querylepoint/{userid}?origin={origin}&auth={auth}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            this.log.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, BalanceQueryResultResponse.class);
            }
        } catch (Exception e) {
            this.log.error(logPrefix + " [error]: ", e);
        }

        return response;
    }

    /**
     * get user info by username
     * @param username
     * @return
     */
    public LetvUserResponse getUserInfo(String username) {
        LetvUserResponse response = null;
        String logPrefix = "getUserInfo_" + username;

        try {
            String result = this.restTemplate.getForObject(UserTpConstant.USER_INFO_GET_URL, String.class, username);
            this.log.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, LetvUserResponse.class);
            }
        } catch (Exception e) {
            this.log.error(logPrefix, e);
        }
        return response;
    }

    /**
     * get user vip info by userId,mac and deviceKey
     * @param commonParam
     * @return
     */
    @Deprecated
    public VIPInfoDto getVIPInfo(CommonParam commonParam) {
        VIPInfoDto vipDto = null;
        String logPrefix = "getVIPInfo_" + commonParam.getMac() + "_" + commonParam.getDeviceKey() + "_"
                + commonParam.getUserId();
        try {
            StringBuilder url = new StringBuilder(UserTpConstant.USER_VIPINFO_GET_URL);
            url.append("from=").append("center");
            url.append("&userid=").append(commonParam.getUserId());
            url.append("&mac=").append(commonParam.getMac());
            url.append("&devicekey=").append(commonParam.getDeviceKey());
            // need return sports vip info
            // url.append("&needSport=").append(1);
            // http://yuanxian.letv.com/letv/getService.ldo?from=center&userid={userid}&mac={mac}&devicekey={deviceKey}&needSport={needSport}
            String response = this.restTemplate.getForObject(url.toString(), String.class);
            this.log.info(logPrefix + ": invoke return [" + response + "]");
            if (response != null) {
                response = URLDecoder.decode(response, "UTF-8");
                vipDto = objectMapper.readValue(response, VIPInfoDto.class);
            }
        } catch (Exception e) {
            this.log.error(logPrefix + " [error]:", e);
        }

        return vipDto;
    }

    /**
     * 将用户登录信息（userid和mac）同步到boss
     * @param userid
     * @param mac
     */
    public void syncUserLoginInfoToBoss(Long userid, String mac) {
        Map<String, String> parametersMap = new HashMap<String, String>();
        parametersMap.put("userid", String.valueOf(userid));
        parametersMap.put("mac", mac);
        String logPrefix = "syncUserLoginInfoToBoss_" + userid + "_" + mac;

        try {
            String result = this.restTemplate.getForObject(UserTpConstant.SYNC_USER_LOGIN_INFO_TO_BOSS, String.class,
                    parametersMap);

            this.log.info(logPrefix + ": invoke return [" + result + "]");
        } catch (Exception e) {
            this.log.error(logPrefix + " return error:", e);
        }
    }

    /**
     * 获取角色列表
     * @param UserRoleRequest
     * @param locale
     * @return
     */
    public RoleCommonGetTP getRole(Long uid) {
        String logPrefix = "getRole_" + uid;
        RoleCommonGetTP roleTp = null;
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("uid", uid);
            String result = this.restTemplate.getForObject(UserTpConstant.GET_ROLE_INFO_URL, String.class, param);
            this.log.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                roleTp = this.objectMapper.readValue(result, RoleCommonGetTP.class);
            }
        } catch (Exception e) {
            this.log.error(logPrefix + ": [error]", e);
        }
        return roleTp;
    }

    /**
     * 校验手机号是否存在（是否是注册用户）
     * @param mobile
     * @param mac
     * @param channel
     * @param broadcastId
     * @return
     */
    public UserCenterCommonResponse<UserInfoCommonResponse> checkMobileExisting(String mobile, String mac,
            String channel, Integer broadcastId) {
        String logPrefix = "checkMobileExisting_" + mobile + "_" + mac;
        UserCenterCommonResponse<UserInfoCommonResponse> response = null;
        try {
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("mobile", StringUtils.trimToEmpty(mobile));
            paramsMap.put("deviceid", StringUtils.trimToEmpty(mac));
            paramsMap.put("channel", StringUtils.trimToEmpty(channel));
            if (broadcastId != null && BROADCAST_TYPE.getById(broadcastId) != null) {
                paramsMap.put("licensetype", StringUtils.trimToEmpty(CommonConstants.BROADCAST_TYPE.getById(broadcastId).getName()));
            }
            // http://api.sso.letv.com/api/checkMobileExists/mobile/{mobile}?deviceid={deviceid}&channel={channel}&licensetype={licensetype}
            String result = this.restTemplate.getForObject(UserTpConstant.CHECK_MOBILE_EXISTS, String.class, paramsMap);
            this.log.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result,
                        new TypeReference<UserCenterCommonResponse<UserInfoCommonResponse>>() {
                        });
            }

            if (response == null) {
                ErrorCodeConstant.throwLetvCommonException(ErrorCodeConstant.USER_MOBILE_FORMAT_ERROR);
            }
        } catch (Exception e) {
            this.log.error(logPrefix + " return error: ", e);
            ErrorCodeConstant.throwLetvCommonException(e, ErrorCodeConstant.USER_MOBILE_FORMAT_ERROR);
        }
        return response;
    }

    private String getBalanceQueryAuth(String userId) {
        try {
            return MessageDigestUtil.md5((VipTpConstant.BOSS_API_ZHIFU_SIGN_KEY + userId).toString().getBytes(
                    Charset.forName("UTF-8")));
        } catch (Exception e) {
            log.error("getBalanceQueryAuth_error", e.getMessage(), e);
        }
        return "";
    }

    /**
     * 获取收藏列表
     * @param category
     *            频道ID（0：全部 ，1：电影，2：电视剧，5：动漫，11：综艺，16：纪录片，支持媒资所有频道
     * @param favoriteType
     *            收藏类型（0：全部，1：点播，2：直播，3：轮播，4：卫视，5：专题，10：其他外网资源；
     * @param fromType
     *            平台标识（1：WEB，2：TV端，3：移动端）
     * @param appId
     *            应用ID，see {@link UserTpConstant.APPID}
     * @param page
     *            当前页,从1开始
     * @param pageSize
     *            每页条数
     *            <<<<<<< HEAD
     *            =======
     * @param region
     *            地域
     *            >>>>>>> 985623242de2d5887734c7d73578cdbe24c12d99
     * @param commonParam
     * @return
     */
    public LetvUserFavoriteListTp getFavoriteList(Integer category, String favoriteType, Integer fromType, APPID appId,
            Integer page, Integer pageSize, String region, CommonParam commonParam) {
        StringBuilder subUrl = new StringBuilder();
        subUrl.append(UserTpConstant.getFavoriteListUrl()).append("?");
        subUrl.append("sso_tk=").append(commonParam.getUserToken());
        if (favoriteType != null) {
            subUrl.append("&favorite_type=").append(favoriteType);
        }
        if (category != null) {
            subUrl.append("&category=").append(category);
        }
        if (fromType != null) {
            subUrl.append("&from_type=").append(fromType);
        }
        if (appId != null) {
            subUrl.append("&appid=").append(appId.getCode());
        }
        if (page != null) {
            subUrl.append("&page=").append(page);
        }
        if (pageSize != null) {
            subUrl.append("&pagesize=").append(pageSize);
        }
        if (StringUtil.isNotBlank(region)) {
            subUrl.append("&regionFilter=").append(region.toUpperCase());
        }
        // http://api.my.letv.com/favorite/listfavorite?category={category}&favorite_type={favorite_type}&from_type={from_type}&page={page}&pagesize={pagesize}&sso_tk={sso_tk}&appid={appid}
        LetvUserFavoriteListTp favTp = null;
        try {
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                favTp = objectMapper.readValue(result, LetvUserFavoriteListTp.class);
            }
        } catch (Exception e) {
            this.log.error("getFavoriteList_" + commonParam.getUserToken() + "_" + commonParam.getUserId()
                    + " return error", e);
        }
        return favTp;
    }

    /**
     * 添加收藏
     * @param ztId
     *            专题id
     * @param albumId
     *            专辑id
     * @param favoriteType
     *            收藏类型0:全部 1:点播 2:直播 3:轮播 4:卫视 5:专题 10:其他外网资源
     * @param product
     *            硬件型号
     * @param fromType
     *            平台标识（1：WEB，2：TV端，3：移动端）
     * @param globalId
     *            作品库数据的globalId
     * @param appId
     *            应用ID，see {@link UserTpConstant.APPID}
     * @param commonParam
     * @return
     */
    public LetvUserCommonFavoriteListTp addFavorite(Integer ztId, Long albumId, Integer favoriteType, Integer product,
            Integer fromType, String globalId, APPID appId, String region, CommonParam commonParam) {
        LetvUserCommonFavoriteListTp commonTp = null;
        try {
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(UserTpConstant.getAddFavoriteUrl()).append("?");
            subUrl.append("sso_tk=").append(commonParam.getUserToken());
            if (favoriteType != null) {
                subUrl.append("&favorite_type=").append(favoriteType);
            }
            if (ztId != null) {
                subUrl.append("&zt_id=").append(ztId);
            }
            if (albumId != null) {
                subUrl.append("&play_id=").append(albumId);
            }
            if (globalId != null) {
                subUrl.append("&global_id=").append(globalId);
            }
            if (fromType != null) {
                subUrl.append("&from_type=").append(fromType);
            }
            if (appId != null) {
                subUrl.append("&appid=").append(appId.getCode());
            }
            if (product != null) {
                subUrl.append("&product=").append(product);
            }
            if (StringUtil.isNotBlank(region)) {
                subUrl.append("&region=").append(region.toUpperCase());
            }
            // http://api.my.letv.com/favorite/add?zt_id={zt_id}&favorite_type={favorite_type}&product={product}&from_type={from_type}&sso_tk={sso_tk}
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            log.info("addFavorite_" + commonParam.getUsername() + " | url:" + subUrl + ", invoke return:" + result);
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                commonTp = objectMapper.readValue(result, LetvUserCommonFavoriteListTp.class);
            }
        } catch (Exception e) {
            log.error("addFavorite_" + commonParam.getUsername() + " | return error: ", e);
        }

        return commonTp;
    }

    /**
     * 验证是否收藏
     * @param ztId
     *            专题id
     * @param albumId
     *            专辑id
     * @param favoriteType
     *            收藏类型0:全部 1:点播 2:直播 3:轮播 4:卫视 5:专题 10:其他外网资源
     * @param globalId
     *            作品库数据的globalId
     * @param appId
     *            应用ID，see {@link UserTpConstant.APPID}
     * @param commonParam
     * @return
     */
    public LetvUserCommonFavoriteListTp checkIsFavorite(Integer ztId, Long albumId, Integer favoriteType,
            String globalId, APPID appId, CommonParam commonParam) {
        LetvUserCommonFavoriteListTp commonTp = null;
        try {
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(UserTpConstant.getCheckFavoriteUrl()).append("?");
            subUrl.append("sso_tk=").append(commonParam.getUserToken());
            if (favoriteType != null) {
                subUrl.append("&favorite_type=").append(favoriteType);
            }
            if (ztId != null) {
                subUrl.append("&zt_id=").append(ztId);
            }
            if (albumId != null) {
                subUrl.append("&play_id=").append(albumId);
            }
            if (globalId != null) {
                subUrl.append("&global_id=").append(globalId);
            }
            if (appId != null) {
                subUrl.append("&appid=").append(appId.getCode());
            }
            // http://api.my.letv.com/favorite/isfavorite?zt_id={zt_id}&favorite_type={favorite_type}&sso_tk={sso_tk}
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                commonTp = objectMapper.readValue(result, LetvUserCommonFavoriteListTp.class);
            }
        } catch (Exception e) {
            this.log.error("checkIsFavorite_" + commonParam.getUsername() + " return error: ", e);
        }

        return commonTp;
    }

    /**
     * 批量删除收藏，可以删除专题、专辑等等
     * @param favoriteType
     *            收藏类型0:全部 1:点播 2:直播 3:轮播 4:卫视 5:专题 10:其他外网资源
     * @param flush
     *            flush=1 ，清空（flush=0或不存在时，favorite_id必传）
     * @param favoriteIds
     *            收藏ID（多个ID以英文逗号相隔）
     * @param appId
     *            应用ID,see {@link UserTpConstant.APPID}
     * @param commonParam
     * @return
     */
    public LetvUserCommonFavoriteListTp multiDelFavorite(Integer favoriteType, Integer flush, String favoriteIds,
            APPID appId, CommonParam commonParam) {
        LetvUserCommonFavoriteListTp commonTp = null;
        try {
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(UserTpConstant.getMultiDelFavoriteUrl()).append("?");
            subUrl.append("sso_tk=").append(commonParam.getUserToken());
            if (flush == null) {
                flush = 0;
            }
            subUrl.append("&flush=").append(flush);
            if (favoriteType != null) {
                subUrl.append("&favorite_type=").append(favoriteType);
            }
            if (StringUtil.isNotBlank(favoriteIds)) {
                subUrl.append("&favorite_id=").append(favoriteIds);
            }
            if (appId != null) {
                subUrl.append("&appid=").append(appId.getCode());
            }
            // http://api.my.letv.com/favorite/multidelete?favorite_id={favorite_id}&sso_tk={sso_tk}&flush={flush}&favorite_type={favorite_type}
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                commonTp = objectMapper.readValue(result, LetvUserCommonFavoriteListTp.class);
            }
        } catch (Exception e) {
            this.log.error("multiDelFavorite_" + commonParam.getUsername() + "_" + favoriteType + "_" + flush
                    + " return error: ", e);
        }

        return commonTp;
    }

    /**
     * 删除收藏
     * @param ztId
     *            专题id
     * @param albumId
     *            专辑id
     * @param globalId
     *            作品库数据的globalId
     * @param favoriteType
     *            收藏类型0:全部 1:点播 2:直播 3:轮播 4:卫视 5:专题 10:其他外网资源
     * @param appId
     *            应用ID，see {@link UserTpConstant.APPID}
     * @param commonParam
     * @return
     */
    public LetvUserCommonFavoriteListTp delFavorite(String ztId, Long albumId, String globalId, Integer favoriteType,
            APPID appId, CommonParam commonParam) {
        LetvUserCommonFavoriteListTp commonTp = null;
        try {
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(UserTpConstant.getDelFavoriteUrl()).append("?");
            subUrl.append("sso_tk=").append(commonParam.getUserToken());
            if (favoriteType != null) {
                subUrl.append("&favorite_type=").append(favoriteType);
            }
            if (StringUtil.isNotBlank(ztId)) {
                subUrl.append("&zt_id=").append(ztId);
            }
            if (albumId != null) {
                subUrl.append("&play_id=").append(albumId);
            }
            if (StringUtil.isNotBlank(globalId)) {
                subUrl.append("&global_id=").append(globalId);
            }
            if (appId != null) {
                subUrl.append("&appid=").append(appId.getCode());
            }
            // http://api.my.letv.com/favorite/delete?zt_id={zt_id}&favorite_type={favorite_type}&sso_tk={sso_tk}
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                commonTp = objectMapper.readValue(result, LetvUserCommonFavoriteListTp.class);
            }
        } catch (Exception e) {
            this.log.error("delFavorite_" + commonParam.getUsername() + " return error: ", e);
        }

        return commonTp;
    }

    /**
     * 获取关注列表
     * @param followType
     *            关注类型,见{@link UserTpConstant.FOLLOW_TYPE}
     * @param fromType
     *            平台标识（1：移动端，3：TV端）,见{@link UserTpConstant.FOLLOW_FROM}
     * @param page
     *            从1开始
     * @param pageSize
     * @param commonParam
     * @return
     */
    public UserTpResponse<FollowListTp> getFollowList(FOLLOW_TYPE followType, FOLLOW_FROM fromType, Integer page,
            Integer pageSize, CommonParam commonParam) {
        UserTpResponse<FollowListTp> response = null;
        try {
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(UserTpConstant.getFollowListUrl()).append("?");
            subUrl.append("sso_tk=").append(commonParam.getUserToken());
            subUrl.append("&followid=").append(commonParam.getUserId());
            if (followType != null) {
                subUrl.append("&type=").append(followType.getCode());
            }
            if (fromType != null) {
                subUrl.append("&from=").append(fromType.getCode());
            }
            if (page != null) {
                subUrl.append("&page=").append(page - 1); // 用户中心的分页从0开始
            }
            if (pageSize != null) {
                subUrl.append("&pagesize=").append(pageSize);
            }
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            log.info("getFollowList|url:" + subUrl + ", invoke return:" + result);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new LetvTypeReference<UserTpResponse<FollowListTp>>() {
                });
            }
        } catch (Exception e) {
            log.error("getFollowList|return error: ", e);
        }
        return response;
    }

    /**
     * 添加关注
     * @param followId
     *            订阅传cpId
     * @param followType
     *            关注类型,见{@link UserTpConstant.FOLLOW_TYPE}
     * @param fromType
     *            平台标识（1：移动端，3：TV端）,见{@link UserTpConstant.FOLLOW_FROM}
     * @param commonParam
     * @return
     */
    public UserTpResponse<String> addFollow(String followId, FOLLOW_TYPE followType, FOLLOW_FROM fromType,
            CommonParam commonParam) {
        UserTpResponse<String> response = null;
        if (followId != null) {
            try {
                StringBuilder subUrl = new StringBuilder();
                subUrl.append(UserTpConstant.getAddFollowUrl()).append("?");
                subUrl.append("sso_tk=").append(commonParam.getUserToken());
                subUrl.append("&followid=").append(followId);
                if (followType != null) {
                    subUrl.append("&type=").append(followType.getCode());
                }
                if (fromType != null) {
                    subUrl.append("&from=").append(fromType.getCode());
                }
                subUrl.append("&sign=").append(this.getFollowSign(followId));
                String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
                log.info("addFollow_" + followId + "|url:" + subUrl + ", invoke return:" + result);
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, new LetvTypeReference<UserTpResponse<String>>() {
                    });
                }
            } catch (Exception e) {
                log.error("addFollow_" + followId + "|return error: ", e);
            }
        }
        return response;
    }

    private String getFollowSign(String plainText) {
        String key = "!@#)fo@llo^w!@#";
        if (plainText == null) {
            plainText = "";
        }
        String signKey = plainText + key;
        return MD5Util.md5(signKey);
    }

    /**
     * 验证是否关注
     * @param followId
     *            订阅传cpId
     * @param followType
     *            关注类型,见{@link UserTpConstant.FOLLOW_TYPE}
     * @param commonParam
     * @return
     */
    public UserTpResponse<String> checkIsFollow(String followId, FOLLOW_TYPE followType, CommonParam commonParam) {
        UserTpResponse<String> response = null;
        if (followId != null) {
            try {
                StringBuilder subUrl = new StringBuilder();
                subUrl.append(UserTpConstant.getCheckIsFollowUrl()).append("?");
                subUrl.append("sso_tk=").append(commonParam.getUserToken());
                subUrl.append("&userid=").append(commonParam.getUserId());
                subUrl.append("&followid=").append(followId);
                if (followType != null) {
                    subUrl.append("&type=").append(followType.getCode());
                }
                subUrl.append("&sign=").append(this.getFollowSign(commonParam.getUserId() + followId));
                String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
                log.info("checkIsFollow_" + followId + "|url:" + subUrl + ", invoke return:" + result);
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, new LetvTypeReference<UserTpResponse<String>>() {
                    });
                }
            } catch (Exception e) {
                log.error("checkIsFollow_" + followId + "|return error: ", e);
            }
        }
        return response;
    }

    /**
     * 删除关注信息
     * @param followId
     *            订阅传cpId
     * @param followType
     *            关注类型,见{@link UserTpConstant.FOLLOW_TYPE}
     * @param commonParam
     * @return
     */
    public UserTpResponse<String> delFollow(String followId, FOLLOW_TYPE followType, CommonParam commonParam) {
        UserTpResponse<String> response = null;
        if (followId != null) {
            try {
                StringBuilder subUrl = new StringBuilder();
                subUrl.append(UserTpConstant.getDelFollowUrl()).append("?");
                subUrl.append("sso_tk=").append(commonParam.getUserToken());
                subUrl.append("&followid=").append(followId);
                if (followType != null) {
                    subUrl.append("&type=").append(followType.getCode());
                }
                String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
                log.info("delFollow_" + followId + "|invoke return:" + result);
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, new LetvTypeReference<UserTpResponse<String>>() {
                    });
                }
            } catch (Exception e) {
                log.error("delFollow_" + followId + "|return error: ", e);
            }
        }
        return response;
    }

    // /**
    // * 一键删除全部专题收藏
    // * @param username
    // * @param ztId
    // * @param favoriteType
    // * @param commonParam
    // * @return
    // */
    // public LetvUserCommonFavoriteListTp delAllFavoriteSubject(String
    // username, String ztId, Integer favoriteType,
    // CommonParam commonParam) {
    // LetvUserCommonFavoriteListTp commonTp = null;
    // String logPrefix = "delFavoriteByZtId_" + username;
    // try {
    // Map<String, Object> parametersMap = new HashMap<String, Object>();
    // parametersMap.put("sso_tk", commonParam.getUserToken());
    // parametersMap.put("zt_id", ztId);
    // parametersMap.put("favorite_type", favoriteType);
    // //
    // String result =
    // this.restTemplate.getForObject(UserTpConstant.GET_DELFAVORITEBYZTID_URL,
    // String.class,
    // parametersMap);
    // if (StringUtil.isNotBlank(result)) {
    // result = URLDecoder.decode(result, "UTF-8");
    // commonTp = this.objectMapper.readValue(result,
    // LetvUserCommonFavoriteListTp.class);
    // }
    // } catch (Exception e) {
    // this.log.error(logPrefix + " return error: ", e);
    // }
    //
    // return commonTp;
    // }

    /**
     * 2016-01-27，临时恢复线上接口
     * @param request
     * @return
     */
    public LetvUserResponse loginToUserCenter_v2(LoginRequest request) {
        String logPrefix = "loginToUserCenter_" + request.getLoginTime() + "_" + request.getMac();
        LetvUserResponse letvUserLoginResponse = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
            params.add("loginname", request.getUsername());
            params.add("password", request.getPassword());
            params.add("registService", UserTpConstant.USER_CENTER_REGIST_SERVICE);
            params.add("plat", "letv_box_tv");
            params.add("ip", request.getClientIp());

            if (request.getMac() != null) {
                params.add("deviceId", request.getMac());
            }
            if (request.getChannel() != null) {
                params.add("channel", request.getChannel());
            }
            if (request.getBroadcastId() != null && BROADCAST_TYPE.getById(request.getBroadcastId()) != null) {
                params.add("licensetype", BROADCAST_TYPE.getById(request.getBroadcastId()).getName());
            }

            String result = this.restTemplate.postForObject(UserTpConstant.USERCENTER_LOGIN, params, String.class);
            this.log.info(logPrefix + ": invoke return [" + result + "]");

            LetvUserTmpResponse tmp = objectMapper.readValue(result, LetvUserTmpResponse.class);
            if (tmp.isResult()) {
                letvUserLoginResponse = objectMapper.readValue(result, LetvUserResponse.class);
                if (letvUserLoginResponse != null && letvUserLoginResponse.getBean() != null
                        && letvUserLoginResponse.getBean().size() > 0) {
                    request.setUsername(letvUserLoginResponse.getBean().get(0).getUsername());
                }
            }
        } catch (Exception e) {
            this.log.error(logPrefix + " return error: ", e);
            ErrorCodeConstant.throwLetvCommonException(e, ErrorCodeConstant.USER_LOGIN_BAD);
        }
        return letvUserLoginResponse;
    }

    /**
     * 2016-01-27，临时恢复线上接口
     * @param mobile
     * @param code
     * @param isCIBN
     * @param mac
     * @param channel
     * @param broadcastId
     * @return
     */
    public LetvUserResponse register_v3(String mobile, String code, Integer isCIBN, String mac, String channel,
            Integer broadcastId) {
        String logPrefix = "register_" + mobile + "_" + mac;
        LetvUserResponse response = null;
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
            params.add("mobile", mobile);
            params.add("code", code);
            params.add("plat", "letv_box_tv");
            params.add("isCIBN", String.valueOf(isCIBN));

            if (mac != null) {
                params.add("deviceId", mac);
            }
            if (channel != null) {
                params.add("channel", channel);
            }
            if (broadcastId != null && BROADCAST_TYPE.getById(broadcastId) != null) {
                params.add("licensetype", BROADCAST_TYPE.getById(broadcastId).getName());
            }

            /*
             * 2014-10-16测试发现restTemplate#<T> T postForObject(String url, Object
             * request, Class<T> responseType, Object...
             * uriVariables)方法在不传uriVariables时有Spring bug；
             * 示例参数：[params]:{mobile=[18511451320], code=[576906],
             * plat=[letv_box_tv], isCIBN=[1]}； 这里修改为必传uriVariables
             */
            // http://api.sso.letv.com/api/clientLogin/api/clientShortcutReg?mobile={mobile}&code={code}&plat={plat}&isCIBN={isCIBN}
            String result = this.restTemplate.postForObject(UserTpConstant.USERCENTER_REGISTER, params, String.class,
                    params);
            this.log.info(logPrefix + ": invoke return [" + result + "]");
            LetvUserTmpResponse tmp = null;
            if (StringUtil.isNotBlank(result)) {
                tmp = objectMapper.readValue(result, LetvUserTmpResponse.class);
            }
            if (tmp != null && tmp.isResult()) {
                response = objectMapper.readValue(result, LetvUserResponse.class);
            } else {
                ErrorCodeConstant.checkErrorCode(tmp.getErrorCode().toString(), new String[] { "1021", "1012" },
                        new String[] { ErrorCodeConstant.USER_REGISTER_CODE_MOBILE_ERROR,
                                ErrorCodeConstant.USER_MOBILE_EXIST }, ErrorCodeConstant.USER_REGISTER_ERROR);
            }
        } catch (Exception e) {
            this.log.error(logPrefix + " return error: ", e);
            ErrorCodeConstant.throwLetvCommonException(e, ErrorCodeConstant.USER_REGISTER_ERROR);
        }
        return response;
    }

    /**
     * 从用户中心获取oAuthCode
     * @param userToken
     *            用户token
     * @return
     */
    public OauthCodeResponse getOauthCode(String userToken) {
        OauthCodeResponse response = new OauthCodeResponse();
        if (userToken != null) {
            try {
                MultiValueMap<String, String> multiMap = new LinkedMultiValueMap<String, String>();
                multiMap.add("client_id", "100015"); // 客户端id,100015:eros
                multiMap.add("scope", "user_basic_silent");// scope：请求需要授权的api列表
                                                           // user_basic_silent:不需要先授权，如果使用user_basic_show则要先授权才能获取到oAuthCode
                multiMap.add("sso_tk", userToken); // 用户登录token
                String responseStr = this.restTemplate.postForObject(UserTpConstant.USER_OAUTH_CODE_URL, multiMap,
                        String.class);
                this.log.info("getOauthCode|userToken:" + userToken + "|result:" + responseStr);
                if (responseStr != null) {
                    response = objectMapper.readValue(responseStr, OauthCodeResponse.class);
                }
            } catch (Exception e) {
                this.log.error("getOauthCode error,userToken:" + userToken + "|errorMsg" + e.getMessage(), e);
            }
        }
        return response;
    }

    /**
     * 从eros获取eros-token，eros-token在获取流地址时使用，90天失效
     * @param userId
     * @param oAuthCode
     *            用户code
     * @param deviceType
     * @return
     */
    public ErosTokenResponse getErosToken(String userId, Integer deviceType, String oAuthCode) {
        ErosTokenResponse response = null;
        if (StringUtil.isNotBlank(userId) && StringUtil.isNotBlank(oAuthCode) && deviceType != null) {
            try {
                StringBuilder subUrl = new StringBuilder();
                subUrl.append(UserTpConstant.USER_API_EROSNOW_URL);
                MultiValueMap<String, String> header = this.createErosTokenHeader(userId, oAuthCode, deviceType,
                        subUrl.toString(), "GET");
                this.log.info("getErosToken|" + userId + "|" + deviceType + "|" + oAuthCode
                        + "|httpHeader:[Authorization:" + header.getFirst("Authorization") + "]");
                subUrl.append("?code=").append(CommonUtil.urlEncode(oAuthCode));
                subUrl.append("&uid=").append(CommonUtil.urlEncode(userId));
                subUrl.append("&device_type=").append(deviceType);

                // 需要传递HttpHeader
                ResponseEntity<String> result = this.restTemplate.exchange(subUrl.toString(), HttpMethod.GET,
                        new HttpEntity<Object>(header), String.class);
                this.log.info("getErosToken|" + userId + "|" + deviceType + "|" + oAuthCode + "|return:" + result);
                if (result != null && result.getBody() != null && StringUtil.isNotBlank(result.getBody())) {
                    response = objectMapper.readValue(result.getBody(), ErosTokenResponse.class);
                }
            } catch (Exception e) {
                this.log.error("getErosToken return error|" + userId + "|" + deviceType + "|" + oAuthCode
                        + "|error mssage:" + e.getMessage(), e);
            }
        }
        return response;
    }

    /**
     * get device info by mac
     * @param commonParam
     * @return
     */
    public GetDeviceInfoResponse getDeviceInfo(CommonParam commonParam) {
        GetDeviceInfoResponse response = null;
        String logPrefix = "getDeviceInfo_" + commonParam.getMac();
        try {
            StringBuilder url = new StringBuilder(UserTpConstant.USER_DEVICE_INFO_URL);
            url.append("mac=").append(commonParam.getMac());
            // http://price.zhifu.letv.com/devicekeyinfo/get?mac={mac}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = this.objectMapper.readValue(result, GetDeviceInfoResponse.class);
            }
        } catch (Exception e) {
            this.log.error(logPrefix + " return error: ", e);
        }

        return response;
    }

    /**
     * create http header for requesting eros-token API
     * @param uid
     *            userId
     * @param code
     *            oAuthCode
     * @param deviceType
     * @param url
     *            request url
     * @param method
     *            request method.sample:GET,POST
     * @return
     */
    private MultiValueMap<String, String> createErosTokenHeader(String uid, String code, Integer deviceType,
            String url, String method) {
        Map<String, Object> parameterMap = new TreeMap<String, Object>();
        parameterMap.put("oauth_signature_method", "HMAC-SHA1");
        parameterMap.put("oauth_timestamp", new Date().getTime() / 1000);
        parameterMap.put("oauth_version", "1.0");
        parameterMap.put("oauth_consumer_key", this.consumer_key);
        parameterMap.put("oauth_nonce", MD5Util.md5(UUID.randomUUID().toString()));
        parameterMap.put("oauth_token", "");
        parameterMap.put("uid", uid);
        parameterMap.put("code", code);
        parameterMap.put("device_type", deviceType);
        String headerStr = "OAuth " + this.mosaicString(parameterMap, ",");
        String signature = this.createErosTokenSignature(url, method, parameterMap);
        if (signature != null) {
            headerStr += ",oauth_signature=" + signature;
        }
        MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<String, String>();
        headerMap.add("Authorization", headerStr);
        return headerMap;
    }

    private String createErosTokenSignature(String url, String method, Map<String, Object> parameterMap) {
        String encodeStr = null;
        try {
            String secretKey = this.consumer_secret + "&";
            String parameterStr = this.mosaicString(parameterMap, "&");
            String encryptText = method.toUpperCase() + "&" + CommonUtil.rawUrlEncode(url) + "&"
                    + CommonUtil.rawUrlEncode(parameterStr);
            encodeStr = Base64.encode(CommonUtil.HmacSHA1Encrypt(encryptText, secretKey));
        } catch (Exception e) {
            this.log.error("createErosTokenSignature_" + url + "_" + method + ": createErosTokenSignature error");
        }
        return encodeStr;
    }

    /**
     * 拼接map中的key-value键值对，形式为[key][=][value][separator]
     * @param parameterMap
     *            要拼接的map
     * @param separator
     *            分隔符
     * @return
     */
    private String mosaicString(Map<String, Object> parameterMap, String separator) {
        String mosaicResult = "";
        if (separator == null) {
            separator = "";
        }
        if (parameterMap != null) {
            Set<String> keys = parameterMap.keySet();
            if (!keys.isEmpty()) {
                Iterator<String> iter = keys.iterator();
                while (iter.hasNext()) {
                    String key = iter.next();
                    if (parameterMap.get(key) != null) {
                        try {
                            mosaicResult += key + "=" + CommonUtil.rawUrlEncode(parameterMap.get(key) + "") + separator;
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        if (separator.length() > 0 && mosaicResult.length() > separator.length()) {
            mosaicResult = mosaicResult.substring(0, mosaicResult.length() - separator.length());
        }
        return mosaicResult;
    }

    /**
     * get the role info and parse to json string
     * @param roleid
     * @param nickname
     * @param roleType
     * @param birthday
     * @param gender
     * @param duration
     * @param timeStart
     * @param timeEnd
     * @param setAge
     * @return
     */
    private String getContent(Long roleid, String nickname, String roleType, String birthday, String gender,
            String duration, String timeStart, String timeEnd, String setAge) {
        LetvUserRoleDto LetvUserRoleDto = new LetvUserRoleDto();
        LetvUserRoleDto.setRoleid(roleid);
        LetvUserRoleDto.setNickName(nickname);
        LetvUserRoleDto.setRoleType(roleType);
        LetvUserRoleDto.setBrithday(birthday);
        LetvUserRoleDto.setGender(gender);
        LetvUserRoleDto.setDuration(duration);
        LetvUserRoleDto.setTimeStart(timeStart);
        LetvUserRoleDto.setTimeEnd(timeEnd);
        LetvUserRoleDto.setSetAge(setAge);
        LetvUserRoleDto.setTimestamp(System.currentTimeMillis());
        ObjectMapper m = new ObjectMapper();
        String value = null;
        try {
            value = m.writeValueAsString(LetvUserRoleDto);
        } catch (Exception e) {
            this.log.error("getContent: ObjectMapper writeValueAsString error", e.getMessage(), e);
        }
        return value;
    }

    public RolePlayListCheckTPResponse checkPlayList(String albumIds, String roleId, CommonParam commonParam,
            String logPrefix) {
        RolePlayListCheckTPResponse response = null;
        String result = null;
        if (StringUtil.isNotBlank(commonParam.getUserId()) && StringUtil.isNotBlank(roleId)) {
            try {
                String leset_id = Long.valueOf(commonParam.getUserId())
                        + String.format(("%0" + 8 + "d"), Long.valueOf(roleId));
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("leset_id", leset_id);
                param.put("pid", albumIds);
                param.put("sso_tk", commonParam.getUserToken());
                result = this.restTemplate.getForObject(UserTpConstant.CHECK_ROLE_ALBUM_EXISTS, String.class, param);
                this.log.info(logPrefix + ": invoke return [" + result + "]");
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, RolePlayListCheckTPResponse.class);
                }
            } catch (Exception e) {
                RolePlayError rolePlayError = null;
                if (StringUtil.isNotBlank(result)) {
                    try {
                        rolePlayError = objectMapper.readValue(result, RolePlayError.class);
                    } catch (Exception e1) {
                        this.log.error(logPrefix + ": [error]", e1);
                    }
                }
                response = new RolePlayListCheckTPResponse();
                if (rolePlayError != null) {
                    response.setCode(rolePlayError.getCode());
                }
            }
        }
        return response;
    }

    /**
     * 获取A/B测试的实验数据信息
     * @param appId
     * @param experimentName
     * @param commonParam
     * @return
     */
    public Map<String, Object> getExperimentInfo(String appId, String experimentName, CommonParam commonParam) {
        Map<String, Object> response = null;

        try {
            StringBuilder url = new StringBuilder(UserTpConstant.EXPERIMENT_ABTEST_URL);
            // experiment name
            url.append(appId).append("/exp/").append(experimentName);
            // use mac for a/b test
            url.append("?clientId=").append(commonParam.getMac());
            // http://dm-exp-planout4j-service.go.le.com/param/${appId}/exp/${expName}?clientId=${clientId}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            log.info("getExperimentInfo_" + commonParam.getMac() + "_" + commonParam.getUserId() + ": invoke return ["
                    + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new LetvTypeReference<Map<String, Object>>() {
                });
            }
        } catch (Exception e) {
            log.error("getExperimentInfo_" + commonParam.getMac() + "_" + commonParam.getUserId() + " error :"
                    + e.getMessage());
        }
        return response;
    }

    /**
     * 获取评论列表
     * @param dataType
     *            类型：1、专辑；2、视频,暂时无用，因为用户中心的type目前只能等于"video"
     * @param videoId
     *            视频id
     * @param albumId
     *            专辑id
     * @param page
     * @param pageSize
     * @param source
     *            来源，见 {@link UserTpConstant.COMMIT_SOURCE}
     * @param ctype
     *            cmt：普通评论、img:图片评论、vote:投票评论，可以同时传多个值，用‘,’隔开，如需要文字+投票评论则传：cmt,
     *            vote
     * @param commonParam
     * @return
     */
    public LetvUserCommentResponse getCommentList(Integer dataType, String videoId, String albumId, Integer page,
            Integer pageSize, Integer source, String ctype, CommonParam commonParam) {
        LetvUserCommentResponse letvUserCommitRespose = null;
        StringBuilder subUrl = new StringBuilder();
        subUrl.append(UserTpConstant.getCommitListUrl()).append("?");
        subUrl.append("type=video");
        subUrl.append("&clientIp=").append(commonParam.getClientIp());
        if (commonParam.getUserToken() != null) {
            subUrl.append("&sso_tk=").append(commonParam.getUserToken());
        }
        if (StringUtil.isNotBlank(albumId) && StringUtil.toLong(albumId, -1L) > 0) { // 必须是有效的albumId
            subUrl.append("&pid=").append(albumId);
        }
        if (StringUtil.isNotBlank(videoId) && StringUtil.toLong(videoId, -1L) > 0) { // 必须是有效的videoId
            subUrl.append("&xid=").append(videoId);
        }
        if (page != null) {
            subUrl.append("&page=").append(page);
        }
        if (pageSize != null && pageSize > 0) {
            subUrl.append("&rows=").append(pageSize);
        }
        if (source != null) {
            subUrl.append("&source=").append(source);
        }
        if (ctype != null) {
            subUrl.append("&ctype=").append(ctype);
        }

        String result = null;
        try {
            result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                letvUserCommitRespose = objectMapper.readValue(result, LetvUserCommentResponse.class);
            }
        } catch (Exception e) {
            log.error(
                    "getCommitList_" + albumId + "_" + videoId + ", invoke return error. Error msg:" + e.getMessage(),
                    e);
        }
        return letvUserCommitRespose;
    }

    /**
     * 添加收藏(新接口)
     * @param globalId
     *            globalId
     * @param categoryId
     *            分类id
     * @param commonParam
     * @return
     */
    public CollectionTpResponse<Object[]> addCollection(String globalId, Integer categoryId, CommonParam commonParam) {
        CollectionTpResponse<Object[]> collectionTpResponse = null;
        if (StringUtil.isNotBlank(globalId)) {
            try {
                StringBuilder subUrl = new StringBuilder();
                subUrl.append(UserTpConstant.getAddCollectionUrl()).append("?"); // http://api.my.le.com/collection/set
                subUrl.append("sso_tk=").append(commonParam.getUserToken());
                subUrl.append("&ip=").append(commonParam.getClientIp());
                subUrl.append("&lid=").append(globalId);
                if (categoryId != null) {
                    subUrl.append("&cid=").append(categoryId);
                }
                APPID appId = APPID.getAppId(commonParam.getTerminalApplication());// 获取appId
                if (appId != null) {
                    subUrl.append("&sappid=").append(appId.getCode());
                }
                if (StringUtil.isNotBlank(commonParam.getTerminalSeries())) {
                    subUrl.append("&product=").append(commonParam.getTerminalSeries());
                }
                if (StringUtil.isNotBlank(commonParam.getWcode())) {
                    subUrl.append("&region=").append(commonParam.getWcode());
                }
                String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
                log.info("addCollection_" + commonParam.getUsername() + " | url:" + subUrl + ", invoke return:"
                        + result);
                if (StringUtil.isNotBlank(result)) {
                    // result = URLDecoder.decode(result, "UTF-8");
                    collectionTpResponse = objectMapper.readValue(result,
                            new LetvTypeReference<CollectionTpResponse<Object[]>>() {
                            });
                }
            } catch (Exception e) {
                log.error("addCollection_" + commonParam.getUsername() + " | return error: ", e);
            }
        }
        return collectionTpResponse;
    }

    /**
     * 获取收藏列表新接口
     * @param type
     *            lid 类型，即lid的前缀，视频：101，专辑：110。默认返回所有，多个类型用英文逗号分隔，lidtype=101,11
     * @param categoryId
     *            分类id
     * @param page
     *            第几页,从1开始
     * @param pageSize
     *            每页大小
     * @param commonParam
     * @return
     */
    public CollectionTpResponse<CollectionListInfo> getCollectionList(String type, Integer categoryId, Integer page,
            Integer pageSize, CommonParam commonParam) {
        CollectionTpResponse<CollectionListInfo> collectionTpResponse = null;
        try {
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(UserTpConstant.getCollectionListUrl()).append("?"); // http://api.my.le.com/collection/list
            subUrl.append("sso_tk=").append(commonParam.getUserToken());
            subUrl.append("&detail=0"); // 是否需要返回详情,1:返回详情,0:不返回详情
            subUrl.append("&vcs=0"); // 是否需要播放记录，1：返回，0：不返回
            if (categoryId != null) {
                subUrl.append("&cid=").append(categoryId);
            }
            APPID appId = APPID.getAppId(commonParam.getTerminalApplication());// 获取appId
            if (appId != null) {
                subUrl.append("&sappid=").append(appId.getCode());
            }
            if (StringUtil.isNotBlank(type)) {
                subUrl.append("&lidtype=").append(type);
            }
            if (page != null && page > 0) {
                subUrl.append("&page=").append(page);
            }
            if (pageSize != null && pageSize > 0) {
                subUrl.append("&pagesize=").append(pageSize);
            }
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            log.info("addCollection_" + commonParam.getUsername() + " | url:" + subUrl + ", invoke return:" + result);
            if (StringUtil.isNotBlank(result)) {
                // result = URLDecoder.decode(result, "UTF-8");
                collectionTpResponse = objectMapper.readValue(result,
                        new LetvTypeReference<CollectionTpResponse<CollectionListInfo>>() {
                        });
            }
        } catch (Exception e) {
            log.error("getCollectionList_" + commonParam.getUsername() + " | return error: ", e);
        }
        return collectionTpResponse;
    }

    /**
     * 根据globalid获取收藏记录(新接口)
     * @param globalIds
     *            globalId列表,多个id以英文逗号分隔
     *            每页大小
     * @param commonParam
     * @return
     */
    public CollectionTpResponse<Map<String, CollectionListInfo.CollectionInfo>> getCollection(String globalIds,
            CommonParam commonParam) {
        CollectionTpResponse<Map<String, CollectionListInfo.CollectionInfo>> collectionTpResponse = null;
        try {
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(UserTpConstant.getCollectionUrl()).append("?"); // http://api.my.le.com/collection/get
            subUrl.append("sso_tk=").append(commonParam.getUserToken());
            APPID appId = APPID.getAppId(commonParam.getTerminalApplication());// 获取appId
            if (appId != null) {
                subUrl.append("&sappid=").append(appId.getCode());
            }
            if (StringUtil.isNotBlank(globalIds)) {
                subUrl.append("&lids=").append(globalIds);
            }
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            log.info("getCollection_" + commonParam.getUsername() + " | url:" + subUrl + ", invoke return:" + result);
            if (StringUtil.isNotBlank(result)) {
                // result = URLDecoder.decode(result, "UTF-8");
                collectionTpResponse = objectMapper.readValue(result,
                        new LetvTypeReference<CollectionTpResponse<Map<String, CollectionListInfo.CollectionInfo>>>() {
                        });
            }
        } catch (Exception e) {
            log.error("getCollection_" + commonParam.getUsername() + " | return error: ", e);
        }
        return collectionTpResponse;
    }

    /**
     * 根据globalid获取收藏记录(新接口)
     * @param globalIds
     *            globalId列表,多个id以英文逗号分隔
     * @param flush
     *            是否清空,1.清空
     * @param commonParam
     * @return
     */
    public CollectionTpResponse<List<Object>> delCollection(String globalIds, Integer flush, CommonParam commonParam) {
        CollectionTpResponse<List<Object>> collectionTpResponse = null;
        try {
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(UserTpConstant.getDelCollectionUrl()).append("?"); // http://api.my.le.com/collection/get
            subUrl.append("sso_tk=").append(commonParam.getUserToken());
            APPID appId = APPID.getAppId(commonParam.getTerminalApplication());// 获取appId
            if (appId != null) {
                subUrl.append("&sappid=").append(appId.getCode());
            }
            if (StringUtil.isNotBlank(globalIds)) {
                subUrl.append("&lids=").append(globalIds);
            }
            if (flush != null) {
                subUrl.append("&flush=").append(flush);
            }
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            log.info("delCollection_" + commonParam.getUsername() + " | url:" + subUrl + ", invoke return:" + result);
            if (StringUtil.isNotBlank(result)) {
                // result = URLDecoder.decode(result, "UTF-8");
                collectionTpResponse = objectMapper.readValue(result,
                        new LetvTypeReference<CollectionTpResponse<List<Object>>>() {
                        });
            }
        } catch (Exception e) {
            log.error("delCollection_" + commonParam.getUsername() + " | return error: ", e);
        }
        return collectionTpResponse;
    }

    public VerifyAccountPasswordTpResponse verifyAccountPassword(String pwd, CommonParam commonParam) {
        VerifyAccountPasswordTpResponse response = null;
        String logPrefix = "verifyAccountPassword_" + commonParam.getUserId() + "_" + commonParam.getMac() + "_"
                + commonParam.getUserToken() + "_" + pwd;

        MultiValueMap<String, String> parameterMap = new LinkedMultiValueMap<String, String>();
        parameterMap.add("ssotk", commonParam.getUserToken());
        parameterMap.add("password", StringUtils.lowerCase(pwd));
        parameterMap.add("plat", UserTpConstant.LECOM_SSO_PLAT_TV);
        parameterMap.add("lang", StringUtils.replace(commonParam.getLangcode(), "_", "-"));
        parameterMap.add("country", StringUtils.upperCase(commonParam.getSalesArea()));

        try {
            String result = this.restTemplate.postForObject(UserTpConstant.VERIFY_USER_ACCOUNT_PWSSWORD_URL,
                    parameterMap, String.class);
            log.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, VerifyAccountPasswordTpResponse.class);
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }

        return response;
    }

    /**
     * ODP代理请求
     * refer:
     * http://wiki.letv.cn/pages/viewpage.action?pageId=55654456#id-%E5%A4
     * %A7%E5%B1%8FODP%E6%
     * 8E%A5%E5%8F%A3%E6%96%87%E6%A1%A3-%E6%88%91%E7%9A%84%E6%88%90%E9%95%BF%E5%80%BC%E4%BF%A1%E6%81
     * %
     * A
     * F
     * @param uri
     * @param params
     * @return
     */
    public OdpTpResponse<JSONObject> odpProxyRequest(String uri, Map params) {
        OdpTpResponse<JSONObject> response = null;
        String logPrefix = "odpProxyRequest: uri=" + uri;
        String channel = "119";
        String secretkey = "26fe3b44e5a52763f3239ced9fbd033d";
        String saltkey = null;
        try {
            StringBuilder subUrl = new StringBuilder();
            subUrl.append(uri).append("?");
            if (!params.containsKey("channel") || null == params.get("channel")
                    || !(params.get("channel") instanceof String) || StringUtil.isBlank((String) params.get("channel"))) { // 如果外部未传设置默认
                params.put("channel", channel);
            }
            if (!params.containsKey("times") || null == params.get("times") || !(params.get("times") instanceof String)
                    || StringUtil.isBlank((String) params.get("times"))) { // 开口子外部传递
                params.put("times", CommonUtil.getCurSecTimestamp());
            }
            if (!params.containsKey("saltkey") || null == params.get("saltkey")
                    || !(params.get("saltkey") instanceof String) || StringUtil.isBlank((String) params.get("saltkey"))) { // 开口子外部传递
                saltkey = HttpClientUtil.genOdpParamsSignature(params, secretkey);
                params.put("saltkey", saltkey);
            }
            subUrl.append(HttpClientUtil.getUrlParamsByMap(params));
            String result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            log.info(logPrefix + "; invoke return [" + result + "]");
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new LetvTypeReference<OdpTpResponse<JSONObject>>() {
                });
            }
        } catch (Exception e) {
            log.error(logPrefix + "; return error: ", e);
        }

        return response;
    }
}
