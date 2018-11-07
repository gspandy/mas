package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.ChatMsgCommonResposne;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.DanmuCountResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.DanmuListResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.LiveSendChatMsgResposne;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value = "v2.DanmuTpDao")
public class DanmuTpDao extends BaseTpDao {

    private final static Logger log = LoggerFactory.getLogger(DanmuTpDao.class);

    /**
     * 发送点播弹幕
     * @return
     */
    public LiveSendChatMsgResposne sendDanmuMsg(Integer vid, Integer pid, Integer cid, Integer start, String txt,
                                                String color, String font, String type, Integer position, Integer from, String callback, String ip,
                                                Integer isIdentify, String x, String y, CommonParam commonParam) {
        LiveSendChatMsgResposne commonTp = null;
        try {
            if (vid == null || pid == null || cid == null || start == null || txt == null || txt.length() > 140/* 字数限制 */) {
                return commonTp;
            }
            Map<String, Object> parametersMap = new HashMap<String, Object>();
            parametersMap.put("vid", vid);
            parametersMap.put("pid", pid);
            parametersMap.put("cid", cid);
            if (start != null) {
                parametersMap.put("start", start);
            }
            parametersMap.put("txt", URLDecoder.decode(txt, "UTF-8"));
            parametersMap.put("color", color);
            parametersMap.put("font", font);
            parametersMap.put("type", type);
            if (position != null) {
                parametersMap.put("position", position);
            }
            if (from != null) {
                parametersMap.put("from", from);
            }
            parametersMap.put("ip", ip);
            parametersMap.put("isIdentify", isIdentify);
            parametersMap.put("x", x);
            parametersMap.put("y", y);
            parametersMap.put("sso_tk", commonParam.getUserToken());
            String result = this.restTemplate.getForObject(UserTpConstant.USERCENTER_DM_ADD_URL, String.class,
                    parametersMap);
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                commonTp = objectMapper.readValue(result, LiveSendChatMsgResposne.class);
            }
        } catch (Exception e) {
            log.error(commonTp + " sendDanmuMsg return error", e);
        }

        return commonTp;
    }

    public ChatMsgCommonResposne<String> sendDanmuMsgV2(Integer vid, Integer pid, Integer cid, Integer start,
                                                        String txt, String color, String font, String type, Integer position, Integer from, String callback,
                                                        String ip, Integer isIdentify, String x, String y, CommonParam commonParam) {
        ChatMsgCommonResposne<String> commonTp = null;
        try {
            if (vid == null || pid == null || cid == null || start == null || txt == null || txt.length() > 140/* 字数限制 */) {
                return commonTp;
            }
            Map<String, Object> parametersMap = new HashMap<String, Object>();
            parametersMap.put("vid", vid);
            parametersMap.put("pid", pid);
            parametersMap.put("cid", cid);
            if (start != null) {
                parametersMap.put("start", start);
            }
            parametersMap.put("txt", URLDecoder.decode(txt, "UTF-8"));
            parametersMap.put("color", color);
            parametersMap.put("font", font);
            parametersMap.put("type", type);
            if (position != null) {
                parametersMap.put("position", position);
            }
            if (from != null) {
                parametersMap.put("from", from);
            }
            parametersMap.put("ip", ip);
            parametersMap.put("isIdentify", isIdentify);
            parametersMap.put("x", x);
            parametersMap.put("y", y);
            parametersMap.put("sso_tk", commonParam.getUserToken());
            String result = this.restTemplate.getForObject(UserTpConstant.USERCENTER_DM_ADD_URL, String.class,
                    parametersMap);
            if (StringUtil.isNotBlank(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                commonTp = objectMapper.readValue(result, new TypeReference<ChatMsgCommonResposne<String>>() {
                });
            }
        } catch (Exception e) {
            log.error(commonTp + " sendDanmuMsg return error", e);
        }

        return commonTp;
    }

    /**
     * 提交直播（和轮播、卫视）聊天记录
     * http://api.my.letv.com/chat/message?roomId={roomId}&clientIp={clientIp}&
     * message
     * ={message}&color={color}&font={font}&position={position}&from={from
     * }&forhost
     * ={forhost}&sso_tk={sso_tk}&type={type}&realname={realname}&x={x}&
     * y={y}&vtkey={vtkey}&callback={callback}
     * @return
     */
    public LiveSendChatMsgResposne sendChatMsg(String userId, String roomId, String clientIp, Boolean forhost,
            String message, String userToken, String color, String font, Integer position, Integer from,
            String callback, Integer type, Integer realname, String x, String y, String vtkey) {
        String logPrefix = "sendChatMsg_" + userId + "_" + roomId;
        LiveSendChatMsgResposne response = null;
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("roomId", roomId);
        paramsMap.put("clientIp", clientIp);
        paramsMap.put("message", message);
        paramsMap.put("color", color);
        paramsMap.put("font", font);
        paramsMap.put("position", position);
        paramsMap.put("from", from);
        paramsMap.put("forhost", forhost);
        paramsMap.put("sso_tk", userToken);
        paramsMap.put("callback", callback);
        paramsMap.put("type", type);
        paramsMap.put("realname", realname);
        paramsMap.put("x", x);
        paramsMap.put("y", y);
        paramsMap.put("vtkey", vtkey);

        try {
            String result = this.restTemplate.getForObject(UserTpConstant.USERCENTER_LIVE_CHATROOM_SEND_MSG_URL,
                    String.class, paramsMap);
            log.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, LiveSendChatMsgResposne.class);
            }
        } catch (Exception e) {
            log.error(logPrefix + ":return error ", e);
        }
        return response;
    }

    public DanmuListResponse getDanmuList(Long videoId, Long playTime, CommonParam commonParam) {
        DanmuListResponse response = new DanmuListResponse();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vid", videoId);
        params.put("amount", 1500);
        params.put("start", playTime);
        params.put("sso_tk", commonParam.getUserToken());
        params.put("from", 4);// 来源[1:Web 2:iPhone 3:iPad4:TV 5:PC桌面
        // 6:Android Phone7:LePhone],默认值:1
        String url = UserTpConstant.GET_DMLIST_URL;
        try {
            String result = this.restTemplate.getForObject(url, String.class, params);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, DanmuListResponse.class);
            }
        } catch (Exception e) {
            log.error(response + " return error: ", e);
        }
        return response;
    }

    public DanmuCountResponse getDanmuCount(Long videoId, Long pid) {
        DanmuCountResponse response = new DanmuCountResponse();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vid", videoId);
        params.put("pid", pid);
        // 6:Android Phone7:LePhone],默认值:1
        String url = UserTpConstant.GET_DMCOUNT_URL;
        try {
            String result = this.restTemplate.getForObject(url, String.class, params);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, DanmuCountResponse.class);
            }
        } catch (Exception e) {
            log.error(response + " return error: ", e);
        }
        return response;
    }
}
