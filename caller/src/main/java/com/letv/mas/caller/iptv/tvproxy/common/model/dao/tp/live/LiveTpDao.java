package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LocaleConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.MD5Util;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("v2.LiveTpDao")
public class LiveTpDao extends BaseTpDao {

    private final static Logger log = LoggerFactory.getLogger(LiveTpDao.class);

    public LiveProjResponseTp filterLiveItems(LiveProjResponseTp liveResponseTp) {
        List<LiveProjectTp> rows = liveResponseTp.getRows();
        Iterator<LiveProjectTp> iter = rows.iterator();
        while (iter.hasNext()) {
            LiveProjectTp row = iter.next();
            if (row.getStatus() == 3 && row.getRecordingId() == null) {
                iter.remove();
            }
        }
        return liveResponseTp;
    }

    /**
     * 新版本的获取直播信息接口
     */
    public LiveProjResponseTp getLivePrograms(String channelType, String beginDate, Integer broadcastId, String splatid) {
        LiveProjResponseTp liveResponseTp = null;
        try {
            String url = ApplicationUtils.get(ApplicationConstants.LIVE_ROOM_LIST_GET_2);
            // 国广版的直播大厅需要加参数
            if (broadcastId != null && CommonConstants.CIBN == broadcastId) {
                url += "&onlyCibnData=1";
            }
            // http://api.live.letv.com/v1/liveRoom/{channnelType}/afterSpecialDate/{splatid}?date={date}
            String result = this.restTemplate.getForObject(url, String.class, channelType, splatid, beginDate);
            if (StringUtils.isNotBlank(result)) {
                liveResponseTp = objectMapper.readValue(result, LiveProjResponseTp.class);
                liveResponseTp = filterLiveItems(liveResponseTp);
            }

            log.debug("[url]:" + url + "[params]:" + "[channelType]" + channelType + "[beginDate]" + beginDate
                    + "[result]:" + result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return liveResponseTp;
    }

    /**
     * 获取live直播首页数据
     * @param commonParam
     * @return
     */
    public LiveHomePageResponse getLiveHomePage(CommonParam commonParam) {
        LiveHomePageResponse liveHomePageResponse = null;
        String result = null;
        try {
            StringBuilder subUrl = new StringBuilder(LiveTpConstants.LIVE_HOME_PAGE_URL);
            subUrl.append("?");
            subUrl.append("splatClient=").append("tvlive");
            subUrl.append("&wcode=").append(LocaleConstant.Wcode.CN);// 美国没测试live数据，暂时先用cn
            subUrl.append("&broadcastId=").append(commonParam.getBroadcastId());
            subUrl.append("&langcode=").append(commonParam.getLangcode());
            result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            log.info("getLiveHomePage" + ": invoke return [" + result + "]");
            if (StringUtils.isNotEmpty(result)) {
                liveHomePageResponse = objectMapper.readValue(result, LiveHomePageResponse.class);
            }
        } catch (Exception e) {
            log.error("getLiveHomePage" + " return error: ", e);
        }
        return liveHomePageResponse;
    }

    /**
     * 获取直播频道在线人数
     * @param channelId
     * @param channelName
     * @param type
     * @param commonParam
     * @return
     */
    public LivePeopleCountResponse getLivePeopleCount(String channelId, String channelName, String type,
                                                      CommonParam commonParam) {
        String logPrefix = "getLivePeopleCount_" + channelId + "_" + channelName + "_" + type;
        LivePeopleCountResponse livePeopleCountResponse = null;
        String result = null;
        try {
            StringBuilder subUrl = new StringBuilder(LiveTpConstants.LIVE_PEOPLE_COUNT_GET_URL);
            subUrl.append("?");
            subUrl.append("channelid=").append(channelId);
            subUrl.append("&channelEname=").append(channelName);
            subUrl.append("&channeltype=").append(type);
            subUrl.append("&wcode=").append(commonParam.getWcode());
            subUrl.append("&broadcastId=").append(commonParam.getBroadcastId());
            subUrl.append("&langcode=").append(commonParam.getLangcode());
            result = this.restTemplate.getForObject(subUrl.toString(), String.class);
            log.info(logPrefix + ": invoke return [" + result + "]");
            if (StringUtils.isNotEmpty(result)) {
                livePeopleCountResponse = objectMapper.readValue(result, LivePeopleCountResponse.class);
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }
        return livePeopleCountResponse;
    }

    /**
     * 获取直播大厅中多条直播数据详细信息，先多用于查询单条直播
     * http://api.live.letv.com/v1/liveRoom/multi/{clientId}?ids=xxxxxxxxxx,
     * sssssssss
     * @param splatid
     * @param liveIds
     * @return
     */
    public LiveProjResponseTp getMultiLivePrograms(String splatid, String liveIds) {
        LiveProjResponseTp liveResponseTp = null;
        String logPrefix = "getMultiLivePrograms_" + splatid + "_" + liveIds;

        StringBuilder urlBuilder = new StringBuilder(LiveTpConstants.LIVE_ROOM_MULTI_LIVE_PROGRAM_GET_URL);
        urlBuilder.append(splatid).append("?ids=").append(liveIds);
        try {

            String result = this.restTemplate.getForObject(urlBuilder.toString(), String.class);
            log.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                liveResponseTp = objectMapper.readValue(result, LiveProjResponseTp.class);
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }
        return liveResponseTp;
    }

    /**
     * 新版本的获取直播信息接口
     */
    public List<TvStreamInfo> getTvStreamResponseTp(String selectId, String splatid) {
        TvStreamResponseTp tvStreamResponseTp = null;
        try {
            String url = ApplicationUtils.get(ApplicationConstants.LIVE_ROOM_STREAM_LIST);
            String result = this.restTemplate.getForObject(url, String.class, splatid, selectId);
            if (StringUtils.isNotBlank(result)) {
                tvStreamResponseTp = objectMapper.readValue(result, TvStreamResponseTp.class);
            }
            log.debug("[url]:" + url + "[params]:" + "[selectId]" + selectId + "[result]:" + result);

            if (tvStreamResponseTp != null && tvStreamResponseTp.getRows() != null
                    && tvStreamResponseTp.getRows().size() > 0) {
                return tvStreamResponseTp.getRows();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * @param zid
     *            专题id
     */
    public LiveProjectResponseTp getLiveProjects(String zid) {
        String url = ApplicationUtils.get(ApplicationConstants.CMS_BLOCK_LIVE_PROJECT_URL);
        String result = this.restTemplate.getForObject(url, String.class, zid);
        LiveProjectResponseTp project = null;
        try {
            // if (StringUtil.isNotBlank(result)) {
            // result = new String(result.getBytes("ISO-8859-1"), "UTF-8");
            // }
            if (StringUtil.isNotBlank(result)) {
                project = objectMapper.readValue(result, LiveProjectResponseTp.class);
            }

            if (project != null && project.getCode() == 200 && project.getData() != null
                    && project.getData().getTjPackages() != null && project.getData().getTjPackages().size() > 0
                    && project.getData().getTjPackages().get(0).getDataList() != null
                    && project.getData().getTjPackages().get(0).getDataList().size() > 0) {
                // for (ProjectPackage pk : project.getData().getTjPackages()) {
                // if (pk.getPtype().equals("3") && pk.getDataList() != null
                // && pk.getDataList().size() > 0) {// 直播专题
                // project.getData().getTjPackage().getDataList()
                // .addAll(pk.getDataList());
                // project.getData().setTjPackages(null);
                // }
                // }
                // 这里只取直播专题包里面的第一个包，和产品确认过，出现问题的可能性比较大
                project.getData().getTjPackage().getDataList()
                        .addAll(project.getData().getTjPackages().get(0).getDataList());
                project.getData().setTjPackages(null);

                return project;
            }
        } catch (Exception e) {
            log.error("Exception:" + url);
            return null;
        }
        log.error("The data is not available:" + url);
        return null;
    }

    /**
     * 直播专题数据
     * @param zid
     *            专题id
     */
    public LiveProjectResponseTp getLiveProjectsNew(String zid, CommonParam commonParam) {
        StringBuilder url = new StringBuilder(CmsTpConstant.GET_CMS_SUBJECT_BY_ID_URL);
        if (!StringUtil.isBlank(zid)) {
            url.append("?zid=").append(zid);
        }
        // if (commonParam != null &&
        // !StringUtil.isBlank(commonParam.getLangcode())) {
        // String langcode = commonParam.getLangcode();
        // if (langcode != null && "en_us".equals(langcode)) {
        // langcode = "en";
        // }
        // url.append("&lang=").append(langcode);
        // }
        url.append("&lang=").append(LocaleConstant.Langcode.ZH_CN);
        url.append("&platform=tv");
        String result = this.restTemplate.getForObject(url.toString(), String.class);
        LiveProjectResponseTp project = null;
        try {
            if (StringUtil.isNotBlank(result)) {
                project = objectMapper.readValue(result, LiveProjectResponseTp.class);
            }
            if (project != null && project.getCode() == 200 && project.getData() != null
                    && project.getData().getPackageIds() != null) {
                List<String> packageIds = project.getData().getPackageIds();
                if (packageIds != null && packageIds.size() > 0) {
                    LivePackageTPResponse packageTPResponse = this.getLivePackageData(packageIds.get(0), commonParam);
                    if (packageTPResponse != null) {
                        project.getData().getTjPackage().getDataList()
                                .addAll(packageTPResponse.getData().getDataList());
                        project.getData().setTjPackages(null);
                        return project;
                    }
                }
            }
        } catch (Exception e) {
            log.error("Exception:" + url);
            return null;
        }
        log.error("The data is not available:" + url);
        return null;
    }

    /**
     * 直播专题包数据
     * @param packageId
     * @param commonParam
     * @return
     */
    public LivePackageTPResponse getLivePackageData(String packageId, CommonParam commonParam) {
        LivePackageTPResponse response = null;
        String logPrefix = "LivePackageTPResponse_" + packageId + "_" + commonParam.getMac();
        try {
            StringBuilder url = new StringBuilder(CmsTpConstant.GET_CMS_PACKAGE_BY_ID_URL);
            if (!StringUtil.isBlank(packageId)) {
                url.append("?pkgid=").append(packageId);
            }
            // if (commonParam != null &&
            // !StringUtil.isBlank(commonParam.getLangcode())) {
            // String langcode = commonParam.getLangcode();
            // if (langcode != null && "en_us".equals(langcode)) {
            // langcode = "en";
            // }
            // url.append("&lang=").append(langcode);
            // }
            url.append("&lang=").append(LocaleConstant.Langcode.ZH_CN);
            url.append("&platform=tv");
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                log.info(logPrefix + ": invoke return [length=" + result.length() + "]");
                response = objectMapper.readValue(result, LivePackageTPResponse.class);
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: " + e.getMessage(), e);
        }

        return response;
    }

    public Map<String, LiveProjectPermissionResponseTp.PermissionInfo> getPriceInfo(String ids, String mac,
            String deviceKey) {
        return this.getPermission(ids, null, mac, deviceKey);
    }

    /**
     * 批量鉴权
     * @param ids
     *            付费场次ID，逗号分隔
     * @param userId
     *            用户ID
     * @return
     */
    public Map<String, LiveProjectPermissionResponseTp.PermissionInfo> getPermission(String ids, String userId,
            String mac, String deviceKey) {
        Map<String, LiveProjectPermissionResponseTp.PermissionInfo> res = null;
        String logPrefix = "getPermission_" + ids + "_" + userId;

        try {
            String type = "";
            String sign = "";
            if (!StringUtils.isEmpty(userId)) {// 批量鉴权
                type = "batch";
                sign = MD5Util.md5(userId + VipTpConstant.LIVE_CHECK_SIGN_KEY);
            } else {// 获取价格接口
                type = "batchPrice";
                sign = MD5Util.md5(ids + VipTpConstant.LIVE_CHECK_SIGN_KEY);
            }
            // http://yuanxian.letv.com/letv/live.ldo?terminal=141007&type={type}&liveids={liveids}&userid={userId}&sign={sign}&mac={mac}&devicekey={deviceKey}
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("type", type);
            paramsMap.put("liveids", ids);
            paramsMap.put("userId", userId);
            paramsMap.put("sign", sign);
            paramsMap.put("mac", mac);
            paramsMap.put("deviceKey", StringUtils.trimToEmpty(deviceKey));

            String result = this.restTemplate.getForObject(LiveTpConstants.BOSS_YUANXIAN_PERMISION_URL, String.class,
                    paramsMap);
            log.info(logPrefix + ": invoke return [" + result + "]");

            LiveProjectPermissionResponseTp project = null;
            if (StringUtil.isNotBlank(result)) {
                project = objectMapper.readValue(result, LiveProjectPermissionResponseTp.class);
            }
            if (project != null && project.getResult() != null && project.getResult().size() > 0) {
                res = new HashMap<String, LiveProjectPermissionResponseTp.PermissionInfo>();
                for (LiveProjectPermissionResponseTp.PermissionInfo p : project.getResult()) {
                    res.put(p.getLiveid(), p);
                }
                return res;
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }

        return null;
    }

    public Map<String, LiveProjectPermissionResponseTp.PermissionInfo> getPermissionByMac(String liveids, String mac) {
        Map<String, LiveProjectPermissionResponseTp.PermissionInfo> res = null;
        String logPrefix = "getPermissionByMac_" + liveids + "_" + mac;

        String sign = MD5Util.md5(mac + VipTpConstant.LIVE_CHECK_SIGN_KEY);
        String url = ApplicationUtils.get(ApplicationConstants.BOSS_YUANXIAN_PERMISION_BY_MAC);
        String result = this.restTemplate.getForObject(url, String.class, liveids, mac, sign);
        log.info(logPrefix + ": invoke return [" + result + "]");
        try {
            if (StringUtil.isNotBlank(result)) {
                LiveProjectPermissionResponseTp project = objectMapper.readValue(result,
                        LiveProjectPermissionResponseTp.class);
                if (project != null && project.getResult() != null && project.getResult().size() > 0) {
                    res = new HashMap<String, LiveProjectPermissionResponseTp.PermissionInfo>();
                    for (LiveProjectPermissionResponseTp.PermissionInfo p : project.getResult()) {
                        res.put(p.getLiveid(), p);
                    }
                }
            }
            return res;
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
            return null;
        }
    }

    public LiveChatRoomHistoryResponse getLiveChatRoomHistory(String chatroomId) {
        for (int i = 0; i < 3; i++) {
            try {
                String result = this.restTemplate.getForObject(
                        ApplicationUtils.get(ApplicationConstants.LIVE_ROOM_CHAT_HISTORY), String.class, chatroomId);
                log.info("getLiveChatRoomHisotry url : " + result + ".roomId=" + chatroomId);
                LiveChatRoomHistoryResponse response = null;
                if (StringUtil.isNotBlank(result)) {
                    response = objectMapper.readValue(result, LiveChatRoomHistoryResponse.class);
                }
                if (response != null && LiveChatRoomHistoryResponse.SUCCESS_CODE.equals(response.getCode())) {
                    return response;
                }
            } catch (Exception e) {
                log.error(
                        "getLiveChatRoomHisotry url : "
                                + ApplicationUtils.get(ApplicationConstants.LIVE_ROOM_CHAT_HISTORY) + ".roomId="
                                + chatroomId, e);
            }
        }
        return null;
    }

    /**
     * 获取直播部门的单个轮播、卫视台等频道信息
     * http://api.live.letv.com/v1/channel/letv/{belongArea}/{splatid}/{
     * channelId}
     * @param channelId
     * @param splatid
     * @param belongArea
     *            版权地域信息，参见http://wiki.letv.cn/pages/viewpage.action?pageId=
     *            46191967，http://api.live.letv.com/v1/dictionary/00012；100--
     *            中国大陆，101--中国香港；102--美国
     * @return
     */
    public LiveSingleDataCommonTpResponse<LiveChannelTpResponse> getLiveChannel(String channelId, String splatid,
            String belongArea) {
        String logPrefix = "getLiveChannel_" + channelId;
        LiveSingleDataCommonTpResponse<LiveChannelTpResponse> response = null;
        StringBuilder urlBuilder = new StringBuilder(LiveTpConstants.LIVE_CHANNEL_BASE_URL);
        urlBuilder.append(belongArea).append("/").append(splatid).append("/").append(channelId);
        try {
            String result = this.restTemplate.getForObject(urlBuilder.toString(), String.class);
            log.info(logPrefix + ": invoke return [" + result + "]");

            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result,
                        new LetvTypeReference<LiveSingleDataCommonTpResponse<LiveChannelTpResponse>>() {
                        });
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }

        return response;
    }

    public enum PROGRAM_TYPE {
        LIVE("Live", 2),
        PRELIVE("预告", 1),
        JI_JIN("集锦", 3),
        HUI_KAN("回看", 4);
        private String name;
        private Integer index;

        private PROGRAM_TYPE(String type, Integer index) {
            this.name = type;
            this.index = index;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getIndex() {
            return this.index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

    }
}
