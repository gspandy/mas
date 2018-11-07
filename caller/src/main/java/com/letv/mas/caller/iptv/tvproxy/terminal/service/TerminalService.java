package com.letv.mas.caller.iptv.tvproxy.terminal.service;

import com.alibaba.fastjson.JSONObject;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.JumpData;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.constant.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.terminal.response.UpgradeTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import com.letv.mas.caller.iptv.tvproxy.terminal.constant.TerminalConstant;
import com.letv.mas.caller.iptv.tvproxy.terminal.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.vip.service.VipV2Service;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component(value = "v2.TerminalService")
public class TerminalService extends BaseService {
    private final Logger upgradeLog = LoggerFactory.getLogger("upgradeDataLog");
    private final Logger log = LoggerFactory.getLogger(TerminalService.class);

    private static final String TERMINAL_BROADCAST_PASS = "TERMINAL_BROADCAST_PASS";
    private static final String TERMINAL_BROADCAST_UNPASS = "TERMINAL_BROADCAST_UNPASS";
    private static final String TERMINAL_VERSION_HAS_NEW = "TERMINAL_VERSION_HAS_NEW";
    private static final String TERMINAL_VERSION_UNNEED_UPGRADE = "TERMINAL_VERSION_UNNEED_UPGRADE";
    private static final String TERMINAL_AUTH_PASS = "TERMINAL_AUTH_PASS";
    private static final String TERMINAL_AUTH_UNPASS = "TERMINAL_AUTH_UNPASS";
    public static final int TERMINAL_NOT_AUDIT = 0;// 无需审核
    public static final int TERMINAL_CIBN_AUDIT = 1;// 国广审核

    // 升级信息，目前SQL语句没法过滤不升级类型，否则就没法获取所有版本信息了
    private UpgradeConfigDto UCD = new UpgradeConfigDto(new HashMap<String, List<SeriesAppVersionInfo>>(),
            new HashMap<String, SeriesAppVersionInfo>(), new HashMap<String, SeriesApplicationRelation>());
    private long UPGRADE_LASTLOAD_TIME = 0l;
    private final long UPGRADE_RELOAD_INTERVAL = 5 * 60 * 1000;
    private boolean reloading = false;
    private final Object lock = new Object();

    
    @Autowired
    TerminalService terminalService;
    
    /**
     * 终端版本信息升级时互斥的锁
     */
    private Lock RELOAD_PACKAGE_UPDATE_VERSION_LOCK = new ReentrantLock();

    /**
     * 升级接口
     * @param request
     * @return
     */
    public ResultsDto terminalAuth(TerminalAuthRequest request, CommonParam commonParam) {
        // update tv white series data
        this.updateTvWhiteSeries();
        /************* Live桌面临时特殊处理 **************/
        if (request.getAuditType() != null && request.getAuditType() == 3) {
            TerminalCommonConstant.isUpgrade = true;
        }
        if (request.getAuditType() != null && request.getAuditType() == 4) {
            TerminalCommonConstant.isUpgrade = false;
        }
        this.upgradeLog.info("terminalAuth:" + request.getUrl());
        if ("LIVE".equals(request.getTerminalApplication()) && "letv".equals(request.getTerminalBrand())
                && (TerminalCommonConstant.LIVE_CAN_UPGRADE_VERSION.contains(request.getInstallVersion()))
                && TerminalCommonConstant.isUpgrade
                && !TerminalCommonConstant.LIVE_CANNOT_UPGRADE_APPCODE.contains(request.getAppCode())) {
            ResultsDto result = new ResultsDto();
            Map<String, String> dataMap = result.getData();
            dataMap.put(TerminalCommonConstant.RESPONSE_KEY_VERSIONNAME, TerminalCommonConstant.LIVE_UPGRADE_VERSION);
            dataMap.put(TerminalCommonConstant.RESPONSE_KEY_VERSIONURL,
                    TerminalCommonConstant.LIVE_UPGRADE_APK_DOWNLOAD_PATH);
            // http://static.itv.letv.com/ext/common/data/html/static/android/20160113111439/superLive-39.apk
            dataMap.put(TerminalCommonConstant.RESPONSE_KEY_DESCRIPTION, "");
            dataMap.put(TerminalCommonConstant.RESPONSE_KEY_PUBLISH_TIME, "");
            dataMap.put("md5", TerminalCommonConstant.LIVE_UPGRADE_MD5);
            dataMap.put(TerminalCommonConstant.RESPONSE_KEY_STATUS, TerminalCommonConstant.STATUS_RECOMMEND);
            dataMap.put(
                    TerminalCommonConstant.RESPONSE_KEY_MESSAGE,
                    TerminalCommonConstant.getMsg(TerminalCommonConstant.TERMINAL_VERSION_HAS_NEW,
                            request.getLangcode()));
            result.setStatus(CommonConstants.RESPONSE_STATUS_SUCCESS);
            result.setMessage(TerminalCommonConstant.getMsg(TERMINAL_AUTH_PASS, request.getLangcode()));
            return result;
        }
        // 美国水货推荐升级
        if ("letv".equals(request.getTerminalApplication()) && "letv".equals(request.getTerminalBrand())
                && LocaleConstant.Wcode.US.equalsIgnoreCase(request.getWcode())
                && "257".compareTo(request.getAppCode()) > 0) {
            ResultsDto result = new ResultsDto();
            Map<String, String> dataMap = result.getData();
            dataMap.put(TerminalCommonConstant.RESPONSE_KEY_VERSIONNAME, "2.9.7");
            dataMap.put(
                    TerminalCommonConstant.RESPONSE_KEY_VERSIONURL,
                    "http://g3.letv.cn/194/23/58/letv-itv/0/ext/common/data/html/static/android/20160202120023/letv-release_us.apk?b=123456&platid=5&splatid=500");
            dataMap.put(TerminalCommonConstant.RESPONSE_KEY_DESCRIPTION, "乐视视频发布重要新春版本，祝广大乐迷猴年新春快乐：<br/>"
                    + "1、升级全球品牌Logo，新的一年，将颠覆创新进行到底；<br/>" + "2、乐视儿童上线啦，专为孩子和家长打造的大屏生态教育产品；<br/>"
                    + "3、全新弹幕样式high翻屏幕，不吐不欢乐；<br/>" + "4、“我的”全面升级换代，您的会员福利就要不一样！<br/>" + "5、全面优化播放记录/收藏，让您追剧更方便；<br/>"
                    + "6、优化产品细节供35项，提升体验，为您我们变得更好！<br/>" + "感谢乐迷一直以来的关注和支持！");
            dataMap.put(TerminalCommonConstant.RESPONSE_KEY_PUBLISH_TIME, "2016-02-01 11:00:00");
            dataMap.put(TerminalCommonConstant.RESPONSE_KEY_STATUS, TerminalCommonConstant.STATUS_RECOMMEND);
            dataMap.put(
                    TerminalCommonConstant.RESPONSE_KEY_MESSAGE,
                    TerminalCommonConstant.getMsg(TerminalCommonConstant.TERMINAL_VERSION_HAS_NEW,
                            request.getLangcode()));
            result.setStatus(CommonConstants.RESPONSE_STATUS_SUCCESS);
            result.setMessage(TerminalCommonConstant.getMsg(TERMINAL_AUTH_PASS, request.getLangcode()));
            return result;
        }
        // 香港水货推荐升级
        if ("letv".equals(request.getTerminalApplication()) && "letv".equals(request.getTerminalBrand())
                && LocaleConstant.Wcode.HK.equalsIgnoreCase(request.getWcode())
                && "257".compareTo(request.getAppCode()) > 0) {
            ResultsDto result = new ResultsDto();
            Map<String, String> dataMap = result.getData();
            dataMap.put(TerminalCommonConstant.RESPONSE_KEY_VERSIONNAME, "2.9.7");
            dataMap.put(
                    TerminalCommonConstant.RESPONSE_KEY_VERSIONURL,
                    "http://g3.letv.cn/186/6/7/letv-itv/0/ext/common/data/html/static/android/20160202111551/letv-release.apk?b=123456&platid=5&splatid=500");
            dataMap.put(TerminalCommonConstant.RESPONSE_KEY_DESCRIPTION, "乐视视频发布重要新春版本，祝广大乐迷猴年新春快乐：<br/>"
                    + "1、升级全球品牌Logo，新的一年，将颠覆创新进行到底；<br/>" + "2、乐视儿童上线啦，专为孩子和家长打造的大屏生态教育产品；<br/>"
                    + "3、全新弹幕样式high翻屏幕，不吐不欢乐；<br/>" + "4、“我的”全面升级换代，您的会员福利就要不一样！<br/>" + "5、全面优化播放记录/收藏，让您追剧更方便；<br/>"
                    + "6、优化产品细节供35项，提升体验，为您我们变得更好！<br/>" + "感谢乐迷一直以来的关注和支持！");
            dataMap.put(TerminalCommonConstant.RESPONSE_KEY_PUBLISH_TIME, "2016-02-01 11:00:00");
            dataMap.put(TerminalCommonConstant.RESPONSE_KEY_STATUS, TerminalCommonConstant.STATUS_RECOMMEND);
            dataMap.put(
                    TerminalCommonConstant.RESPONSE_KEY_MESSAGE,
                    TerminalCommonConstant.getMsg(TerminalCommonConstant.TERMINAL_VERSION_HAS_NEW,
                            request.getLangcode()));
            result.setStatus(CommonConstants.RESPONSE_STATUS_SUCCESS);
            result.setMessage(TerminalCommonConstant.getMsg(TERMINAL_AUTH_PASS, request.getLangcode()));
            return result;
        }
        if ("letv".equals(request.getTerminalApplication()) && "letv".equals(request.getTerminalBrand())
                && "2.7.4".compareTo(request.getInstallVersion()) >= 0) {
            // hotel application version 2.7.4 no need to upgrade
            return this.specialDealWithUnUpgrade(request);
        }

        String terminalApplication = request.getTerminalApplication();
        String terminalSeries = request.getTerminalSeries();
        String terminalUiCode = request.getTerminalUiCode();
        String terminalBrand = request.getTerminalBrand();
        String installVersion = request.getInstallVersion();
        StringBuilder logPrefix = new StringBuilder("terminalAuth_").append(installVersion).append("_")
                .append(request.getMac()).append("_").append(terminalSeries).append("_").append(terminalUiCode)
                .append(",");
        if (StringUtils.isBlank(terminalApplication)) {
            if (terminalSeries != null && terminalSeries.indexOf(TerminalCommonConstant.APPLICATION_LIXIAOLU) > -1) {
                terminalApplication = TerminalCommonConstant.APPLICATION_LIXIAOLU;
            } else {
                terminalApplication = TerminalCommonConstant.TERMINAL_APPLICATION_LETV;
            }
        }
        if (TerminalCommonConstant.LETV_UI.equalsIgnoreCase(terminalUiCode)
                || TerminalCommonConstant.BS_FUZE.equalsIgnoreCase(request.getBsChannel())) {
            // 如果是乐视的ui版本，放行
        } else if (TerminalCommonConstant.BRAND_LETV.equalsIgnoreCase(terminalBrand)
                && TerminalCommonConstant.isSoldOutForBox(terminalSeries)) {
            // 盒子下线声明
            this.log.info(logPrefix.append(" box sold out statement.").toString());
            return terminalService.boxSoldOutStatement();
        } else if (installVersion.startsWith("1.5")) {
            // 1.5全部下线
            this.log.info(logPrefix.append(" version 1.5 haved been history.").toString());
            return terminalService.soldOutForLowVersionStatement();
        } else if (TerminalCommonConstant.TV_BRAND_WHITE_LIST.contains(terminalBrand)
                && TerminalCommonConstant.TV_SERIES_WHITE_LIST.contains(terminalSeries)) {
            // 放行
        } else if (terminalApplication.contains(TerminalCommonConstant.TERMINAL_APPLICATION_LETV_SEARCH)
                || "LIVE".equals(request.getTerminalApplication())) {
            // 乐搜放行
        } else {// 停止服务声明
            this.log.info(logPrefix.append(" stop service statement.").toString());
            // return
            // terminalService.stopServiceStatement(installVersion);
        }
        // 加载升级信息
        this.reloadVersionInfoNew();

        long stime = System.currentTimeMillis();
        this.log.info(logPrefix.toString() + "terminalAuth is start,requst:" + request);
        ResultsDto result = new ResultsDto();
        result.setMessage(TerminalCommonConstant.getMsg(TERMINAL_AUTH_UNPASS, request.getLangcode()));
        result.getData().put(CommonConstants.RESPONSE_KEY_MESSAGE,
                TerminalCommonConstant.getMsg(TERMINAL_AUTH_UNPASS, request.getLangcode()));
        Map<String, String> dataMap = result.getData();
        try {
            String broadcastStatus = this.checkBroadcast(request);

            dataMap.put(CommonConstants.RESPONSE_KEY_BROADCAST_STATUS, broadcastStatus);
            dataMap.put(CommonConstants.RESPONSE_KEY_BROADCAST_SWITCH, "0");
            if (broadcastStatus.equals("0")) {
                dataMap.put(CommonConstants.RESPONSE_KEY_BROADCAST_MESSAGE,
                        TerminalCommonConstant.getMsg(TERMINAL_BROADCAST_UNPASS, request.getLangcode()));
            } else {
                dataMap.put(CommonConstants.RESPONSE_KEY_BROADCAST_MESSAGE,
                        TerminalCommonConstant.getMsg(TERMINAL_BROADCAST_PASS, request.getLangcode()));
            }
            // 判断客户端生成的clientUuid与服务端同一方式同一参数生成的是否相同
            if (request.getClientUuid() != null
                    && !request.getClientUuid().equals(
                            StringUtil.getUUIDString(request.getTerminalBrand(), request.getTerminalSeries(),
                                    request.getTerminalUnique(), 4, "0"))) {
                return result;
            }
            if (request.getMcode() != null && request.getMcode().length() > 0) {
                Boolean isPojie = this.checkMcode(request.getMac(), request.getMcode());
                if (isPojie) {
                    dataMap.put(CommonConstants.RESPONSE_KEY_POJIESTATUS, "1");
                }
            }

            String playFormatIsTs = "";
            Integer dbPFIT = 0;
            Integer broadcastId = 0;
            String config = "{'P3':2160000}";
            String terBrand = request.getTerminalBrand();
            if ("LeEco".equalsIgnoreCase(terBrand) || "Letv".equalsIgnoreCase(terBrand)) {
                terBrand = "letv";
            }
            String key = StringUtils.trimToEmpty(request.getClient()) + "_" + StringUtils.trimToEmpty(terBrand) + "_"
                    + StringUtils.trimToEmpty(request.getTerminalSeries()) + "_" + request.getTerminalApplication();
            UpgradeConfigDto upgradeConfigDto = this.UCD;
            SeriesApplicationRelation seriesApplicationRelation = upgradeConfigDto.getSERIES_APPLICATION_RELATION_MAP()
                    .get(key);

            if (seriesApplicationRelation != null) {
                dbPFIT = seriesApplicationRelation.getPlay_format_ls_ts() == null ? 0 : seriesApplicationRelation
                        .getPlay_format_ls_ts();
                broadcastId = seriesApplicationRelation.getBroadcastId();
                config = seriesApplicationRelation.getConfig();
            }
            // }
            // }
            if (dbPFIT == 1) {
                playFormatIsTs = "true";
            } else if (dbPFIT == 2) {
                playFormatIsTs = "false";
            } else {
                playFormatIsTs = dbPFIT + "";
            }
            // 播控、码流、config信息
            dataMap.put(CommonConstants.RESPONSE_KEY_PLAYFORMATISTS, playFormatIsTs);
            dataMap.put(CommonConstants.RESPONSE_KEY_BROADCASTID, broadcastId + "");
            dataMap.put(CommonConstants.RESPONSE_KEY_CONFIG, config);

            SeriesAppVersionInfo userSeriesAppVersionInfo = upgradeConfigDto.getVERSION_MAP().get(
                    request.getTerminalApplication() + "_" + request.getInstallVersion());
            boolean isUpgrade = false;
            int userVersionCode = Integer.MAX_VALUE;
            Integer versionCode = null;// invoke new upgrade service parameter
            if (userSeriesAppVersionInfo != null) {
                userVersionCode = userSeriesAppVersionInfo.getVersion_code();
                versionCode = userSeriesAppVersionInfo.getVersion_code();
            }
            String upgradeSwitch = ApplicationUtils.get(ApplicationConstants.IPTV_TERMINAL_UPGRADE_SWITCH);
            if (TerminalCommonConstant.TERMINAL_UPGRADE_SWITCH_ON.equals(upgradeSwitch)) {
                // 添加开关，控制是否调用新升级接口
                // 旧通用版升级修改成统一版本，新版本直接走新升级后台。 王胜凯 20161125
                if (TerminalCommonConstant.TERMINAL_APPLICATION_LETV_COMMON
                        .equals(commonParam.getTerminalApplication())) {
                    versionCode = 83;
                    request.setInstallVersion("1.0.83");
                    request.setBsChannel("common");
                    commonParam.setBsChannel("common");
                }
                UpgradeTpResponse tpResponse = this.facadeTpDao.getTerminalTpDao().getUpgradeInfo(versionCode,
                        request.getInstallVersion(), request.getTerminalUnique(), commonParam);
                if ((tpResponse != null) && (tpResponse.getStatus() != null) && (tpResponse.getStatus() == 1)
                        && (tpResponse.getData() != null)) {
                    UpgradeTpResponse.UpgradeInfo data = tpResponse.getData();
                    Integer status = data.getStatus();
                    if (status != null && (status == 5 || status == 6)) {
                        isUpgrade = true;
                        dataMap.put("md5", data.getDstmd5());
                        dataMap.put(CommonConstants.RESPONSE_KEY_VERSIONURL, data.getVersionUrl());
                        dataMap.put(CommonConstants.RESPONSE_KEY_VERSIONNAME, data.getVersionName());
                        dataMap.put(CommonConstants.RESPONSE_KEY_DESCRIPTION, data.getDescription());
                        dataMap.put(CommonConstants.RESPONSE_KEY_PUBLISH_TIME,
                                TimeUtil.getDateStringFromLong(data.getTimeStamp(), TimeUtil.SIMPLE_DATE_FORMAT));// 升级包发布时间
                        dataMap.put(CommonConstants.RESPONSE_KEY_STATUS, String.valueOf(status));
                        dataMap.put(CommonConstants.RESPONSE_KEY_MESSAGE, data.getMessage());
                        this.upgradeLog.info(logPrefix.toString() + ",upgrade level:" + data.getStatus());
                    }
                }
            }
            if (!isUpgrade) {// 如果升级服务接口已提示升级，就不用再处理老升级逻辑
                List<SeriesAppVersionInfo> upgradeList = upgradeConfigDto.getUPGRADE_MAP().get(key);
                SeriesAppVersionInfo maxVersion = null;
                SeriesAppVersionInfo maxForceVersion = null;
                if ((upgradeList != null) && (upgradeList.size() > 0)) {
                    for (SeriesAppVersionInfo seriesAppVersionInfo : upgradeList) {
                        if (seriesAppVersionInfo.getType() != null && seriesAppVersionInfo.getType() == 3) {
                            continue;// 不升级版本，跳过继续下一个
                        }
                        if (seriesAppVersionInfo.getVersion_code() <= userVersionCode) {
                            break;
                        } else {
                            if (maxVersion == null) {
                                maxVersion = seriesAppVersionInfo;
                            }
                            if (maxForceVersion == null) {
                                if (seriesAppVersionInfo.getType() == 1) {// 强升
                                    maxForceVersion = seriesAppVersionInfo;
                                }
                            }
                            if ((maxVersion != null) && (maxForceVersion != null)) {
                                break;
                            }
                        }
                    }
                }

                if (maxVersion == null) {
                    this.upgradeLog.info(logPrefix.toString() + ",upgrade level:7");
                    dataMap.put(CommonConstants.RESPONSE_KEY_STATUS, "7");
                    dataMap.put(CommonConstants.RESPONSE_KEY_MESSAGE,
                            TerminalCommonConstant.getMsg(TERMINAL_VERSION_UNNEED_UPGRADE, request.getLangcode()));
                } else {
                    boolean isForce = false;
                    if (maxForceVersion != null) {
                        isForce = true;
                    }

                    dataMap.put("md5", maxVersion.getMd5());
                    dataMap.put(CommonConstants.RESPONSE_KEY_VERSIONURL, maxVersion.getDownload_url());
                    dataMap.put(CommonConstants.RESPONSE_KEY_VERSIONID, maxVersion.getVersion_id() + "");
                    dataMap.put(CommonConstants.RESPONSE_KEY_VERSIONNAME, maxVersion.getVersion_name());
                    dataMap.put(CommonConstants.RESPONSE_KEY_OTHER, maxVersion.getOther());
                    dataMap.put(CommonConstants.RESPONSE_KEY_DESCRIPTION, maxVersion.getDescription());
                    dataMap.put(CommonConstants.RESPONSE_KEY_ROM_MINIMUM, maxVersion.getRom_minimum());// 需要升级版本的最低room版本
                    dataMap.put(CommonConstants.RESPONSE_KEY_PUBLISH_TIME,
                            TimeUtil.getDateString(maxVersion.getCreate_time(), TimeUtil.SIMPLE_DATE_FORMAT));// 升级包发布时间
                    this.log.info(logPrefix.toString() + "=====maxVersionCode:" + maxVersion.getVersion_code()
                            + " || type:" + maxVersion.getType() + "=====");
                    if (maxVersion.getType() == 1 || isForce == true) {
                        dataMap.put(CommonConstants.RESPONSE_KEY_STATUS, "5");
                        dataMap.put(CommonConstants.RESPONSE_KEY_MESSAGE,
                                TerminalCommonConstant.getMsg(TERMINAL_VERSION_HAS_NEW, request.getLangcode()));
                        this.upgradeLog.info(logPrefix.toString() + ",upgrade level:5");
                    } else if (maxVersion.getType() == 2) {
                        dataMap.put(CommonConstants.RESPONSE_KEY_STATUS, "6");
                        dataMap.put(CommonConstants.RESPONSE_KEY_MESSAGE,
                                TerminalCommonConstant.getMsg(TERMINAL_VERSION_HAS_NEW, request.getLangcode()));
                        this.upgradeLog.info(logPrefix.toString() + ",upgrade level:6");
                    } else if (maxVersion.getType() == 3) {

                        dataMap.put(CommonConstants.RESPONSE_KEY_STATUS, "7");
                        dataMap.put(CommonConstants.RESPONSE_KEY_MESSAGE,
                                TerminalCommonConstant.getMsg(TERMINAL_VERSION_UNNEED_UPGRADE, request.getLangcode()));
                    }
                }
            }

            result.setStatus(CommonConstants.RESPONSE_STATUS_SUCCESS);
            result.setMessage(TerminalCommonConstant.getMsg(TERMINAL_AUTH_PASS, request.getLangcode()));
            // 天猫盒子拥有30天会员试用，其他盒子拥有7天; 用户客户端判断试用期
            if (TmallMagicConstants.canTrust(request.getTerminalSeries())) {
                dataMap.put(CommonConstants.RESPONSE_KEY_FREE_VIP, "30");
            } else {
                dataMap.put(CommonConstants.RESPONSE_KEY_FREE_VIP, "7");
            }
        } catch (Exception e) {
            this.log.error(logPrefix.toString() + e.getMessage(), e);
            result.setStatus(CommonConstants.RESPONSE_STATUS_FAILURE);
            result.setMessage(TerminalCommonConstant.getMsg(TERMINAL_AUTH_UNPASS, request.getLangcode()));
        } finally {
            this.log.info(logPrefix.toString() + "broadcastId:" + dataMap.get(CommonConstants.RESPONSE_KEY_BROADCASTID));
            this.log.info(logPrefix.toString() + "playFormatIsTs:"
                    + dataMap.get(CommonConstants.RESPONSE_KEY_PLAYFORMATISTS));
            this.log.info(logPrefix.toString() + "identifyCode:"
                    + dataMap.get(CommonConstants.RESPONSE_KEY_IDENTIFYCODE) + ",terminalUuid:"
                    + dataMap.get(CommonConstants.RESPONSE_KEY_TERMINALUUID));
            this.log.info(logPrefix.toString() + "outter status:" + result.getStatus() + ",inner status:"
                    + dataMap.get(CommonConstants.RESPONSE_KEY_STATUS));
            this.log.info(logPrefix.toString() + "outter status:" + result.getStatus() + "  message:"
                    + result.getMessage());
        }
        this.log.info(logPrefix.toString() + "terminalAuth is end,time:" + (System.currentTimeMillis() - stime));
        result.getData().put(TerminalCommonConstant.UPDATE_SWITCH_KEY, TerminalCommonConstant.UPDATE_SWITCH_ON);
        return result;
    }

    private String checkBroadcast(TerminalAuthRequest request) {
        String broadcastStatus = "1";

        if (request.getIp().startsWith("10.")) {// 内网放行
            return broadcastStatus;
        }
        if (request.getAuditType() != null && TERMINAL_NOT_AUDIT == request.getAuditType()) {
            return broadcastStatus;
        }
//        if ((request.getBroadcastId() != null) && request.getBroadcastId().equals("2")) {
//            if (!this.facadeTpDao.getCibnTpDao().checkBroadcast(request.getMac())) {
//                broadcastStatus = "0";
//            }
//        }

        return broadcastStatus;
    }

    public Boolean checkMcode(String mac, String mcode) {
        Boolean isPojie = false;
        try {
            StringBuffer tmpMac = new StringBuffer();
            for (int i = 0; i < mac.length(); i++) {
                tmpMac.append(mac.charAt(i));
                if (i % 2 == 1 && i != (mac.length() - 1)) {
                    tmpMac.append(":");
                }
            }
            String md5 = MessageDigestUtil.md5(("letvabc " + tmpMac.toString().toUpperCase()).getBytes(Charset
                    .forName("UTF-8")));
            isPojie = md5.equalsIgnoreCase(mcode);
        } catch (NoSuchAlgorithmException e) {
            log.error("checkMcode_NoSuchAlgorithmException", e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            log.error("checkMcode_UnsupportedEncodingException", e.getMessage(), e);
        }
        return isPojie;
    }

    /**
     * 加载升级信息
     */
    @Deprecated
    private void reloadVersionInfo() {
        if ((System.currentTimeMillis() - this.UPGRADE_LASTLOAD_TIME) < this.UPGRADE_RELOAD_INTERVAL) {
            return;
        }
        if (this.reloading) {// 正在加载
            return;
        } else {
            synchronized (this.lock) {
                if (this.reloading) {// 正在加载
                    return;
                } else {
                    this.reloading = true;
                }
            }
        }
        if ((System.currentTimeMillis() - this.UPGRADE_LASTLOAD_TIME) < this.UPGRADE_RELOAD_INTERVAL) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                long cur = System.currentTimeMillis();
                TerminalService.this.log.info("load upgrade info start cur:" + cur);
                TerminalService.this.reloadVersionInfo1();
                long end = System.currentTimeMillis();
                TerminalService.this.log.info("load upgrade info start cur:" + cur + ",end:" + end + ",waste time:"
                        + (end - cur));
            }

        }.start();

    }

    private void reloadVersionInfoNew() {
        String logPrefix = "reloadVersionInfoNew_";
        if ((System.currentTimeMillis() - this.UPGRADE_LASTLOAD_TIME) >= this.UPGRADE_RELOAD_INTERVAL) {
            if (this.RELOAD_PACKAGE_UPDATE_VERSION_LOCK.tryLock()) {
                this.log.info(logPrefix + ": locked.");
                try {
                    long cur = System.currentTimeMillis();
                    new Thread() {
                        @Override
                        public void run() {
                            TerminalService.this.reloadVersionInfo1();
                        };
                    }.start();
                    this.UPGRADE_LASTLOAD_TIME = System.currentTimeMillis();
                    long end = System.currentTimeMillis();
                    this.log.info(logPrefix + "load upgrade info start cur:" + cur + ",end:" + end + ",waste time:"
                            + (end - cur));
                } catch (Exception e) {
                    this.log.error(logPrefix + ":return error-", e);
                } finally {
                    this.RELOAD_PACKAGE_UPDATE_VERSION_LOCK.unlock();
                    this.log.info(logPrefix + ": unlocked.");
                }
            }
        }
    }

    private void reloadVersionInfo1() {
        HashMap<String, List<SeriesAppVersionInfo>> UPGRADE_MAP = new HashMap<String, List<SeriesAppVersionInfo>>();
        // 存放所有版本信息，包括（强升、推荐和不升级）三个类型的版本
        HashMap<String, SeriesAppVersionInfo> VERSION_MAP = new HashMap<String, SeriesAppVersionInfo>();
        HashMap<String, SeriesApplicationRelation> SERIES_APPLICATION_RELATION_MAP = new HashMap<String, SeriesApplicationRelation>();
        List<Platform> platformList = this.facadeMysqlDao.getPlatformDao().getPlatformList();
        List<Brand> brandList = this.facadeMysqlDao.getBrandDao().getBrandList();
        List<SeriesApplication> seriesApplicationList = this.facadeMysqlDao.getSeriesDao().getSeriesApplicationList();
        for (Platform platform : platformList) {
            for (Brand brand : brandList) {
                List<Series> seriesList = this.facadeMysqlDao.getSeriesDao().getSeriesByBrandId(brand.getId());

                for (Series series : seriesList) {
                    for (SeriesApplication seriesApplication : seriesApplicationList) {
                        SeriesApplicationRelation seriesApplicationRelation = this.facadeMysqlDao.getSeriesDao()
                                .getSeriesApplicationRelationBySidAndSAidAndPid(series.getId(),
                                        seriesApplication.getId(), platform.getId());
                        if (seriesApplicationRelation != null) {
                            String key = platform.getHardware_code() + "_" + brand.getHardware_code() + "_"
                                    + series.getHardware_code() + "_" + seriesApplication.getApp_code();
                            SERIES_APPLICATION_RELATION_MAP.put(key, seriesApplicationRelation);

                            List<SeriesAppVersionInfo> versionList = this.facadeMysqlDao.getVersionDao()
                                    .getVersionInfoBySAPPId(seriesApplicationRelation.getId());
                            UPGRADE_MAP.put(key, versionList);

                            for (SeriesAppVersionInfo version : versionList) {// 此步不能过滤，否则用户安装了不升级的版本就没法继续升级
                                // if (version.getType() != null &&
                                // (version.getType() == 1) ||
                                // (version.getType() == 2)) {
                                VERSION_MAP.put(seriesApplication.getApp_code() + "_" + version.getVersion_name(),
                                        version);
                                // }
                            }
                        }
                    }
                }
            }
        }
        this.UCD = new UpgradeConfigDto(UPGRADE_MAP, VERSION_MAP, SERIES_APPLICATION_RELATION_MAP);
    }

    /**
     * 盒子下线声明
     */
    private ResultsDto boxSoldOutStatement() {
        // t1盒子下线
        ResultsDto result = new ResultsDto();
        StringBuffer message = new StringBuffer();
        message.append("乐视盒子T1免费换购及停服公告：");
        message.append("亲爱的乐视盒子T1用户：");
        message.append("感谢您对乐视的支持与信任！ 为期一个月的乐视盒子T1换购活动已经结束，我们将于2015年8月20日停止对乐视盒子T1的相关服务。如果您还有T1盒子换购需求，欢迎拨打客服电话：1010 9000咨询具体政策。");
        message.append("乐视网信息技术（北京）股份有限公司");
        message.append("2015年8月");
        result.getData().put(CommonConstants.RESPONSE_KEY_STATUS, "5");
        result.getData().put(CommonConstants.RESPONSE_KEY_DESCRIPTION, message.toString());
        result.getData().put(CommonConstants.RESPONSE_KEY_TERMINALUUID, "5b95e417590a52ed6773b1045064157b");
        result.getData().put(CommonConstants.RESPONSE_KEY_USERNAME, "");
        result.getData().put(CommonConstants.RESPONSE_KEY_IDENTIFYCODE, "aaaa");
        result.getData().put(CommonConstants.RESPONSE_KEY_VERSIONID, "99");
        result.getData().put(CommonConstants.RESPONSE_KEY_PLAYFORMATISTS, "true");
        result.getData().put(CommonConstants.RESPONSE_KEY_BROADCASTID, "0");
        result.getData().put(CommonConstants.RESPONSE_KEY_CONFIG, "");
        result.getData().put(CommonConstants.RESPONSE_KEY_ROM_MINIMUM, "");
        result.getData().put(CommonConstants.RESPONSE_KEY_CURROM_MINIMUM, "");
        result.getData().put(CommonConstants.RESPONSE_KEY_BROADCAST_STATUS, "1");
        result.getData().put(CommonConstants.RESPONSE_KEY_BROADCAST_SWITCH, "1");
        result.getData().put(CommonConstants.RESPONSE_KEY_VERSIONURL, "");
        result.getData().put("versionName", "");
        result.getData().put("publishTime", "2013-08-20 09:37:44");
        result.getData().put("pojieVersion", "0");
        result.getData().put("versionId", "");
        result.getData().put(CommonConstants.RESPONSE_KEY_MESSAGE, message.toString());
        result.setMessage(message.toString());
        return result;
    }

    /**
     * 1.5全部下线
     * @return
     */
    private ResultsDto soldOutForLowVersionStatement() {
        // 1.5全部下线
        ResultsDto result = new ResultsDto();
        StringBuffer message = new StringBuffer();
        message.append("您当前系统版本过低，享受更好服务请您尽快升级系统版本！");
        result.getData().put(CommonConstants.RESPONSE_KEY_STATUS, "5");
        result.getData().put(CommonConstants.RESPONSE_KEY_DESCRIPTION, message.toString());
        result.getData().put(CommonConstants.RESPONSE_KEY_TERMINALUUID, "5b95e417590a52ed6773b1045064157b");
        result.getData().put(CommonConstants.RESPONSE_KEY_USERNAME, "");
        result.getData().put(CommonConstants.RESPONSE_KEY_IDENTIFYCODE, "aaaa");
        result.getData().put(CommonConstants.RESPONSE_KEY_VERSIONID, "99");
        result.getData().put(CommonConstants.RESPONSE_KEY_PLAYFORMATISTS, "true");
        result.getData().put(CommonConstants.RESPONSE_KEY_BROADCASTID, "0");
        result.getData().put(CommonConstants.RESPONSE_KEY_CONFIG, "");
        result.getData().put(CommonConstants.RESPONSE_KEY_ROM_MINIMUM, "");
        result.getData().put(CommonConstants.RESPONSE_KEY_CURROM_MINIMUM, "");
        result.getData().put(CommonConstants.RESPONSE_KEY_BROADCAST_STATUS, "1");
        result.getData().put(CommonConstants.RESPONSE_KEY_BROADCAST_SWITCH, "1");
        result.getData().put(CommonConstants.RESPONSE_KEY_VERSIONURL, "");
        result.getData().put("versionName", "");
        result.getData().put("publishTime", "2013-09-07 12:00:00");
        result.getData().put("pojieVersion", "0");
        result.getData().put("versionId", "");
        result.getData().put(CommonConstants.RESPONSE_KEY_MESSAGE, message.toString());
        result.setMessage(message.toString());
        return result;
    }

    /**
     * 停止服务声明
     * @param installVersion
     * @return
     */
    private ResultsDto stopServiceStatement(String installVersion) {
        ResultsDto result = new ResultsDto();
        StringBuffer message = new StringBuffer();
        message.append("乐视视频停止服务公告：");
        if (installVersion != null && installVersion.startsWith("1.5")) {
            message.append("\r\n");
        } else {
            message.append("<br>");
        }
        message.append("亲爱的用户：遵照国家相关监管政策要求，您目前在这台设备上使用的乐视视频从即日起停止服务！带来的不便我们深表歉意，敬请谅解！若有疑问可咨询400-030-0104。");
        result.getData().put(CommonConstants.RESPONSE_KEY_STATUS, TerminalCommonConstant.STATUS_FORCE);
        result.getData().put(CommonConstants.RESPONSE_KEY_DESCRIPTION, message.toString());
        result.getData().put(CommonConstants.RESPONSE_KEY_VERSIONURL, "");
        result.getData().put("versionName", "无");
        result.getData().put("publishTime", "无");
        result.getData().put("pojieVersion", "无");
        result.getData().put("versionId", "无");
        result.getData().put(CommonConstants.RESPONSE_KEY_MESSAGE, message.toString());

        result.setMessage(message.toString());
        return result;
    }

    public Response<TerminalConfigDto> trimResponse(Response<TerminalConfigDto> response,
                                                    TerminalConfigDto terminalConfigDto) {
        TerminalConfigDto terminalConfigDto_new = new TerminalConfigDto();
        terminalConfigDto_new.setDeviceConfig(terminalConfigDto.getDeviceConfig());
        JSONObject appConfig = (JSONObject) terminalConfigDto.getAppConfig();
        HashMap<String, Object> defaultUnknown = new HashMap();
        defaultUnknown.put("defaultUnknown", appConfig.get("defaultUnknown"));
        terminalConfigDto_new.setAppConfig(defaultUnknown);
        response.setData(terminalConfigDto_new);
        return response;
    }

    public Response<TerminalConfigDto> filterByVersion(Response<TerminalConfigDto> response,
            TerminalConfigDto terminalConfigDto, CommonParam commonParam) {
        if (StringUtil.isNotBlank(commonParam.getAppCode())
                && StringUtil.isNotBlank(commonParam.getTerminalApplication())
                && Integer.valueOf(commonParam.getAppCode()) >= 310
                && commonParam.getTerminalApplication().equals("media_cibn")) {
            this.trimResponse(response, terminalConfigDto);
        } else if (StringUtil.isNotBlank(commonParam.getAppCode())
                && StringUtil.isNotBlank(commonParam.getTerminalApplication())
                && Integer.valueOf(commonParam.getAppCode()) >= 40
                && commonParam.getTerminalApplication().equals("child_alone")) {
            this.trimResponse(response, terminalConfigDto);
        } else {
            response.setData(terminalConfigDto);
        }
        return response;
    }

    public Response<TerminalConfigDto> config(Integer model, String deviceModelType, CommonParam commonParam) {
        String logPrefix = "config_" + commonParam.getMac() + "_" + commonParam.getTerminalSeries();
        Response<TerminalConfigDto> response = new Response<TerminalConfigDto>();
        response.setPlus(null);
        TerminalConfigDto terminalConfigDto = new TerminalConfigDto();
        Map<String, Object> configInfo = new HashMap<String, Object>();
        if ((model != null) && (model == 1)) {//
            // feature下发,除了configInfo以外的内容都不要返回
            // 从缓存中获取该应用所配置的feature
            Map<String, Object> features = this.getFeatures(commonParam);
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LETV_SEARCH.equalsIgnoreCase(commonParam
                    .getTerminalApplication())
                    || TerminalCommonConstant.TERMINAL_APPLICATION_LETV.equalsIgnoreCase(commonParam
                            .getTerminalApplication())
                    || TerminalCommonConstant.TERMINAL_APPLICATION_CIBN.equalsIgnoreCase(commonParam
                            .getTerminalApplication())) {// 乐视视频向前向后兼容
                getTerminalCommonConfig(terminalConfigDto, configInfo, commonParam, logPrefix);
            }
            if (!CollectionUtils.isEmpty(features)) {// feature下发
                configInfo.putAll(features);
            }
        } else {// 非features下发，走以前的逻辑
            getTerminalCommonConfig(terminalConfigDto, configInfo, commonParam, logPrefix);
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LESO.equalsIgnoreCase(commonParam.getTerminalApplication())) {
                terminalConfigDto
                        .setLesoSports(this.getLesoSportsJumpConfig(commonParam.getTerminalSeries(), logPrefix));
            } else {
                terminalConfigDto.setSports(this.getLetvSportsJumpConfig(commonParam.getTerminalSeries(), logPrefix));
            }
        }
        terminalConfigDto.setConfigInfo(configInfo);

        this.getTerminalAppConfig(terminalConfigDto, commonParam, logPrefix, true);

        this.getTerminalDeviceConfig(terminalConfigDto, deviceModelType, commonParam, logPrefix, true);

        this.filterByVersion(response, terminalConfigDto, commonParam);
        return response;
    }

    public Response<ServiceTermDto> getServiceTerm(Integer model, CommonParam commonParam) {
        String logPrefix = "getServiceTerm_" + commonParam.getMac();
        Response<ServiceTermDto> response = new Response<ServiceTermDto>();

        TerminalConstant.SerciceTermType termType = TerminalConstant.SerciceTermType.getSerciceTermTypeByType(model);
        if (termType == null) {
            String errorCode = ErrorCodeConstants.COMMON_ILLEGAL_PARAMETER;
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            String content = getServiceTermFromCache(null, termType, commonParam);
            if (content == null) {
                content = getServiceTermFromCache(CommonConstants.DEFAULT_LANGCODE, termType, commonParam);
            }

            if (content == null) {
                String errorCode = ErrorCodeConstants.TERMINAL_GET_SERVICE_TERM_FAIL;
                ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
            } else {
                ServiceTermDto term = new ServiceTermDto();
                term.setTermType(model);
                term.setContent(content);
                term.setContentType(termType.getContentType());
                response.setData(term);
            }
        }

        return response;
    }

    /**
     * update tv white series from static file
     */
    private void updateTvWhiteSeries() {
        long curTime = System.currentTimeMillis();
        if (TerminalCommonConstant.TERMINAL_WHITE_SERIES_LAST_UPDATE_TIME
                + TerminalCommonConstant.TERMINAL_WHITE_SERIES_UPDATE_INTERVAL < curTime) {
            synchronized (TerminalCommonConstant.TV_SERIES_WHITE_LIST) {
                if (TerminalCommonConstant.TERMINAL_WHITE_SERIES_LAST_UPDATE_TIME
                        + TerminalCommonConstant.TERMINAL_WHITE_SERIES_UPDATE_INTERVAL < curTime) {
                    TerminalCommonConstant.TERMINAL_WHITE_SERIES_LAST_UPDATE_TIME = curTime;
                    // get tv white series from static file
                    String content = this.facadeTpDao.getStaticTpDao().getIptvStaticFileContentByUrl(
                            TerminalCommonConstant.getTerminalWhiteSeries(null));
                    if (content != null) {
                        String[] series = content.split(",");
                        List<String> list = new LinkedList<String>();
                        for (String s : series) {
                            if (StringUtil.isNotBlank(s)) {
                                list.add(s.trim());
                            }
                        }
                        TerminalCommonConstant.TV_SERIES_WHITE_LIST = list;
                    }
                }
            }
        }
    }

    /**
     * 读取TV版跳转体育配置；
     * 先更新缓存，1.如果数据为空，直接从缓存中更新缓存；2.如果数据不为空，则根据更新规则频次限制来更新数据
     */
    private LetvContentSportsJumpConfig getLetvSportsJumpConfig(String terminalSeries, String logPrefix) {
        LetvContentSportsJumpConfig sportsJump = null;

        Map<String, LetvContentSportsJumpConfig> letvSportsJumpConfigMap = this.facadeCacheDao.getTerminalCacheDao()
                .getLetvSportsJumpConfig();
        if (CollectionUtils.isEmpty(letvSportsJumpConfigMap)) {
            letvSportsJumpConfigMap = this.facadeTpDao.getStaticTpDao().getLetvSportsJumpConfig();
            this.facadeCacheDao.getTerminalCacheDao().setLetvSportsJumpConfig(letvSportsJumpConfigMap, 300);
        }

        if (!CollectionUtils.isEmpty(letvSportsJumpConfigMap)) {
            String configTerminalSeries = null;
            for (Entry<String, LetvContentSportsJumpConfig> letvSportsJumpConfigEntry : letvSportsJumpConfigMap
                    .entrySet()) {
                if (letvSportsJumpConfigEntry != null) {
                    configTerminalSeries = letvSportsJumpConfigEntry.getKey();
                    if (configTerminalSeries != null
                            && ArrayUtils.contains(StringUtils.split(configTerminalSeries,
                                    TerminalConstant.SPORTS_JUMP_CONFIG_KEY_SEPARATOR), terminalSeries)) {
                        sportsJump = letvSportsJumpConfigEntry.getValue();
                        break;
                    }
                }
            }
            if (sportsJump == null) {
                sportsJump = letvSportsJumpConfigMap.get("Default");
            }
        } else {
            this.log.info(logPrefix + ": getLetvSportsJumpConfig empty ");
        }

        return sportsJump;
    }

    /**
     * 读取乐搜跳转体育配置
     */
    private LesoContentSportsJumpConfig getLesoSportsJumpConfig(String terminalSeries, String logPrefix) {
        LesoContentSportsJumpConfig sportsJump = null;

        Map<String, LesoContentSportsJumpConfig> lesoSportsJumpConfigMap = this.facadeCacheDao.getTerminalCacheDao()
                .getLesoSportsJumpConfig();
        if (CollectionUtils.isEmpty(lesoSportsJumpConfigMap)) {
            lesoSportsJumpConfigMap = this.facadeTpDao.getStaticTpDao().getLesoSportsJumpConfig();
            this.facadeCacheDao.getTerminalCacheDao().setLesoSportsJumpConfig(lesoSportsJumpConfigMap, 300);
        }

        if (!CollectionUtils.isEmpty(lesoSportsJumpConfigMap)) {
            String configTerminalSeries = null;
            for (Entry<String, LesoContentSportsJumpConfig> letvSportsJumpConfigEntry : lesoSportsJumpConfigMap
                    .entrySet()) {
                if (letvSportsJumpConfigEntry != null) {
                    configTerminalSeries = letvSportsJumpConfigEntry.getKey();
                    if (configTerminalSeries != null
                            && ArrayUtils.contains(StringUtils.split(configTerminalSeries,
                                    TerminalConstant.SPORTS_JUMP_CONFIG_KEY_SEPARATOR), terminalSeries)) {
                        sportsJump = letvSportsJumpConfigEntry.getValue();
                        break;
                    }
                }
            }
            if (sportsJump == null) {
                sportsJump = lesoSportsJumpConfigMap.get("Default");
                this.log.info(logPrefix + ": getLesoSportsJumpConfig empty ");
            }
        } else {
            this.log.info(logPrefix + ": getLesoSportsJumpConfig empty ");
        }
        return sportsJump;
    }

    /**
     * sepcial deal with unupgrade version
     * @param request
     * @return
     */
    private ResultsDto specialDealWithUnUpgrade(TerminalAuthRequest request) {
        ResultsDto result = new ResultsDto();
        result.setStatus(CommonConstants.RESPONSE_STATUS_SUCCESS);
        result.setMessage(TerminalCommonConstant.getMsg(TERMINAL_AUTH_PASS, request.getLangcode()));
        Map<String, String> dataMap = result.getData();
        dataMap.put(TerminalCommonConstant.RESPONSE_KEY_STATUS, TerminalCommonConstant.STATUS_NORMAL);
        dataMap.put(
                TerminalCommonConstant.RESPONSE_KEY_MESSAGE,
                TerminalCommonConstant.getMsg(TerminalCommonConstant.TERMINAL_VERSION_UNNEED_UPGRADE,
                        request.getLangcode()));
        dataMap.put(CommonConstants.RESPONSE_KEY_VERSIONURL, "");
        dataMap.put(CommonConstants.RESPONSE_KEY_VERSIONID, "");
        dataMap.put(CommonConstants.RESPONSE_KEY_VERSIONNAME, "");
        dataMap.put(CommonConstants.RESPONSE_KEY_OTHER, "");
        dataMap.put(CommonConstants.RESPONSE_KEY_DESCRIPTION, "");
        dataMap.put(CommonConstants.RESPONSE_KEY_ROM_MINIMUM, "");
        dataMap.put(CommonConstants.RESPONSE_KEY_PUBLISH_TIME, "");
        dataMap.put(CommonConstants.RESPONSE_KEY_BROADCAST_STATUS, "1");
        dataMap.put(CommonConstants.RESPONSE_KEY_BROADCAST_SWITCH, "1");
        dataMap.put(CommonConstants.RESPONSE_KEY_BROADCAST_MESSAGE,
                TerminalCommonConstant.getMsg(TERMINAL_BROADCAST_PASS, request.getLangcode()));
        dataMap.put(CommonConstants.RESPONSE_KEY_POJIESTATUS, "0");
        dataMap.put(CommonConstants.RESPONSE_KEY_PLAYFORMATISTS, "true");
        dataMap.put(CommonConstants.RESPONSE_KEY_BROADCASTID, "0");
        dataMap.put(CommonConstants.RESPONSE_KEY_CONFIG, "");
        dataMap.put(CommonConstants.RESPONSE_KEY_FREE_VIP, "7");
        dataMap.put(TerminalCommonConstant.UPDATE_SWITCH_KEY, TerminalCommonConstant.UPDATE_SWITCH_ON);

        return result;
    }

    private void getTerminalCommonConfig(TerminalConfigDto terminalConfigDto, Map<String, Object> configInfo,
            CommonParam commonParam, String logPrefix) {
        logPrefix = "getTerminalCommonConfig_" + commonParam.getMac() + "_" + commonParam.getDevId() + "_"
                + commonParam.getTerminalApplication() + "_" + commonParam.getTerminalSeries()
                + commonParam.getCountryArea() + "_" + commonParam.getSalesArea();

        String area = StringUtils.isNotEmpty(commonParam.getCountryArea()) ? commonParam.getCountryArea() : commonParam
                .getSalesArea();
        area = StringUtils.isNotEmpty(area) ? area : commonParam.getWcode();
        area = StringUtils.trimToEmpty(area).toLowerCase();
        String terminalApplication = StringUtils.trimToEmpty(commonParam.getTerminalApplication()).toLowerCase();

        // 弹幕配置，根据电视型号判断是否支持弹幕功能，读取配置文件
        Map<String, DanMuDto> resultsMap = this.facadeTpDao.getStaticTpDao().getDanMuConfig(
                commonParam.getBroadcastId());
        if (!CollectionUtils.isEmpty(resultsMap)) {
            DanMuDto danMu = resultsMap.get(commonParam.getTerminalSeries());
            if (danMu == null) {
                this.log.info("not be found danmu config.series=" + commonParam.getTerminalSeries() + ",mac="
                        + commonParam.getMac());
                danMu = resultsMap.get("Default");
            }
            terminalConfigDto.setDanmu(danMu);
        }

        // 跳转体育APP配置
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LETV_SEARCH.equalsIgnoreCase(commonParam
                .getTerminalApplication())) {
            terminalConfigDto.setLesoSports(this.getLesoSportsJumpConfig(commonParam.getTerminalSeries(), logPrefix));
        }
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LETV.equalsIgnoreCase(commonParam.getTerminalApplication())
                || TerminalCommonConstant.TERMINAL_APPLICATION_CIBN.equalsIgnoreCase(commonParam
                        .getTerminalApplication())) {
            terminalConfigDto.setLesoSports(this.getLesoSportsJumpConfig(commonParam.getTerminalSeries(), logPrefix));
            terminalConfigDto.setSports(this.getLetvSportsJumpConfig(commonParam.getTerminalSeries(), logPrefix));
        }

        List<String> tabConfig = null;
        Integer support360Stream = null;

        Map<String, String> configMap = null;
        String holidayConfigKey = terminalApplication + ".holiday." + area;
        String holidayConfig = this.facadeCacheDao.getTerminalCacheDao()
                .getLetvHolidayConfig(terminalApplication, area);
        if (StringUtils.isEmpty(holidayConfig)) {
            if (CollectionUtils.isEmpty(configMap)) {
                configMap = this.getConfigMap();
            }
            holidayConfig = configMap.get(holidayConfigKey);
            this.facadeCacheDao.getTerminalCacheDao().setLetvHolidayConfig(terminalApplication, area, holidayConfig);
        }
        if (StringUtils.isNotEmpty(holidayConfig)) {
            // 节日元素支持配置，根据电视型号判断是否支持节日元素功能，不支持的型号
            // Set<String> holidaySet =
            // this.facadeTpDao.getStaticTpDao().getHolidayConfig();
            List<String> holidayList = Arrays.asList(StringUtils.split(holidayConfig, ","));
            if (holidayList != null && holidayList.contains(commonParam.getTerminalSeries())) {
                this.log.info("this Terminal " + commonParam.getTerminalSeries()
                        + "can not support holiday element ,mac=" + commonParam.getMac());
                terminalConfigDto.setHoliday(new HolidayDto(0));
            } else {
                terminalConfigDto.setHoliday(new HolidayDto(1));
            }
        } else {
            log.warn(logPrefix + ": get empty holiday config ");
        }

        // 下导航配置
        String tabConfigKey = terminalApplication + ".tab.config." + area;
        tabConfig = this.facadeCacheDao.getTerminalCacheDao().getLetvTabConfigList(terminalApplication, area);
        if (CollectionUtils.isEmpty(tabConfig)) {
            if (CollectionUtils.isEmpty(configMap)) {
                configMap = this.getConfigMap();
            }
            String tabConfigValue = configMap.get(tabConfigKey);
            if (StringUtils.isNotEmpty(tabConfigValue)) {
                tabConfig = Arrays.asList(StringUtils.split(tabConfigValue, ","));
                this.facadeCacheDao.getTerminalCacheDao().setLetvTabConfigList(terminalApplication, area, tabConfig);
            } else {
                this.log.warn(logPrefix + ": get empty tab config ");
            }
        }

        // 设备支持全景声配置
        String unsupport360StreamConfigKey = terminalApplication + ".unsupport.360.stream." + area;
        List<String> unsupport360StreamList = this.facadeCacheDao.getTerminalCacheDao().getUnsupport360StreamSeries(
                unsupport360StreamConfigKey);
        if (unsupport360StreamList == null) {
            if (CollectionUtils.isEmpty(configMap)) {
                configMap = this.getConfigMap();
            }
            String unsupport360StreamConfig = configMap.get(unsupport360StreamConfigKey);
            if (StringUtils.isNotEmpty(unsupport360StreamConfig)) {
                unsupport360StreamList = Arrays.asList(StringUtils.split(unsupport360StreamConfig, ","));
                this.facadeCacheDao.getTerminalCacheDao().setUnsupport360StreamSeries(unsupport360StreamConfigKey,
                        unsupport360StreamList);
            } else {
                this.log.warn(logPrefix + ": get empty support360Stream config ");
            }
        }
        if (!CollectionUtils.isEmpty(unsupport360StreamList)
                && !unsupport360StreamList.contains(commonParam.getTerminalSeries())) {
            // 注意，必须明确配置了支持，才能确定支持，即配置文件中，不支持设备列表不要为空，可以添加“default”进行占位
            support360Stream = 1;
        } else {
            support360Stream = 0;
        }

        terminalConfigDto.setConfigInfo(configInfo);

        configInfo.put("danmu", terminalConfigDto.getDanmu());
        configInfo.put("sports", terminalConfigDto.getSports());
        configInfo.put("lesoSports", terminalConfigDto.getLesoSports());
        configInfo.put("holiday", terminalConfigDto.getHoliday());
        configInfo.put("tab_config", tabConfig);
        configInfo.put("support360Stream", support360Stream);
    }

    /**
     * 判断当前设备是否支持全景码流
     * @param commonParam
     * @return
     */
    public boolean isTerminalSeriesSupport360Stream(CommonParam commonParam) {
        boolean support360Stream = false;
        String area = StringUtils.isNotEmpty(commonParam.getCountryArea()) ? commonParam.getCountryArea() : commonParam
                .getSalesArea();
        area = StringUtils.isNotEmpty(area) ? area : commonParam.getWcode();
        area = StringUtils.trimToEmpty(area).toLowerCase();
        String terminalApplication = StringUtils.trimToEmpty(commonParam.getTerminalApplication()).toLowerCase();

        String logPrefix = "isSupport360Stream_" + commonParam.getMac() + "_" + commonParam.getDevId() + "_" + area
                + "_" + terminalApplication;

        String unsupport360StreamConfigKey = terminalApplication + ".unsupport.360.stream." + area;
        List<String> unsupport360StreamList = this.facadeCacheDao.getTerminalCacheDao().getUnsupport360StreamSeries(
                unsupport360StreamConfigKey);
        if (CollectionUtils.isEmpty(unsupport360StreamList)) {
            log.warn(logPrefix + ": get unsupport360StreamList cache empty ");
        } else {
            String terminalSeries = StringUtils.trimToEmpty(commonParam.getTerminalSeries());
            support360Stream = !unsupport360StreamList.contains(terminalSeries);
        }

        return support360Stream;
    }

    /**
     * 获取应用配置的features
     * @param commonParam
     * @return
     */
    private Map<String, Object> getFeatures(CommonParam commonParam) {
        Map<String, Object> features = null;
        String terminalApplication = commonParam.getTerminalApplication();
        String area = commonParam.getSalesAreaTmp();
        if (StringUtil.isBlank(area)) {
            if ("letv_superlive_app".equalsIgnoreCase(terminalApplication)
                    || "tvlive".equalsIgnoreCase(terminalApplication)) {
                area = getFinalArea(commonParam);
            } else {
                // 销售地为空，默认CN
                area = LocaleConstant.Wcode.CN;
            }
        }
        Integer broadcastId = commonParam.getBroadcastId();
        // 获取“应用+地区+播控方+版本名称”4个维度的配置
        features = this.facadeCacheDao.getTerminalCacheDao().getTerminalConfig(terminalApplication, area, broadcastId,
                commonParam.getAppVersion());
        if (CollectionUtils.isEmpty(features)) {
            // 获取“应用+地区+播控方”3个维度的配置
            features = this.facadeCacheDao.getTerminalCacheDao().getTerminalConfig(terminalApplication, area,
                    broadcastId, null);
            if (CollectionUtils.isEmpty(features)) {
                // 获取“应用+地区”2个维度的配置
                features = this.facadeCacheDao.getTerminalCacheDao().getTerminalConfig(terminalApplication, area, null,
                        null);
                if (CollectionUtils.isEmpty(features)) {
                    // 获取“应用”1个维度的配置
                    features = this.facadeCacheDao.getTerminalCacheDao().getTerminalConfig(terminalApplication, "",
                            null, null);
                }
            }
        }
        return features;
    }

    private Map<String, String> getConfigMap() {
        // 对于简单的配置，可以放在同一个文件中，减少网络请求的次数
        Map<String, String> configMap = this.facadeTpDao.getStaticTpDao().getStaticFileContent(
                ApplicationConstants.I_STATIC_ITV_BASE_HOST + "/api/conf/terminal/terminalConfig.properties");
        if (configMap == null) {
            configMap = new HashMap<String, String>(0);
        }
        return configMap;
    }

    /**
     * get final area for letv_superlive_app
     * @param commonParam
     * @return
     */
    public String getFinalArea(CommonParam commonParam) {
        String area = "";
        if (StringUtils.isNotBlank(commonParam.getSalesAreaTmp())) {
            area = processSpecialSalesArea(commonParam);
        } else if (StringUtils.isNotBlank(commonParam.getWcodeTmp())) {
            area = commonParam.getWcodeTmp();
        } else if (isChinaNoAreaVersion(commonParam)) {
            area = "cn";
        } else if (StringUtils.isNotBlank(commonParam.getClientIp())) {
            String ipAdd = IpAddrUtil.getIPInfo(commonParam.getClientIp());
            if (StringUtils.isNotBlank(ipAdd)) {
                String[] add = ipAdd.split("_");
                if (add.length >= 0) {
                    area = add[0];
                }
            }
        }
        if (StringUtils.isNotBlank(area)) {
            area = area.toLowerCase();
        }
        return area;
    }

    private static String processSpecialSalesArea(CommonParam param) {
        String salesArea = null;
        if (param != null && StringUtils.isNotBlank(param.getSalesAreaTmp())) {
            salesArea = param.getSalesAreaTmp();
            if ("FULL_US".equalsIgnoreCase(salesArea)) {
                salesArea = "US";
            } else if ("FULL".equalsIgnoreCase(salesArea)) {
                salesArea = "CN";
            }
        }
        return salesArea;
    }

    /**
     * 大陆老版本并没有传入任何地域相关的参数
     * 根据terminalApplition以及版本号确定大陆老版本
     * @param param
     * @return
     */
    private static boolean isChinaNoAreaVersion(CommonParam param) {
        if (param != null && "letv_superlive_app".equals(param.getTerminalApplication())
                && compareVersionCode(param.getAppVersion(), "1.2.3") < 0) {
            return true;
        }
        return false;
    }

    /**
     * 比较版本号大小
     * @param curVersion
     *            当前版本号
     * @param targetVersion
     *            目标版本号
     * @return 如果当前版本号大，则返回大于0的整数
     *         如果目标版本大,返回小于0的整数
     *         如果版本号相同，则返回0
     */
    public static int compareVersionCode(String curVersion, String targetVersion) {
        if (StringUtils.isNotBlank(curVersion) && StringUtils.isNotBlank(targetVersion)) {
            String[] curVerStrs = curVersion.split("\\.");
            String[] tagVerStrs = targetVersion.split("\\.");
            if (curVerStrs.length > 0 && tagVerStrs.length > 0) {
                try {
                    int minLength = (curVerStrs.length > tagVerStrs.length) ? tagVerStrs.length : curVerStrs.length;
                    for (int i = 0; i < minLength; i++) {
                        int curVer = Integer.parseInt(curVerStrs[i]);
                        int tagVer = Integer.parseInt(tagVerStrs[i]);
                        if (curVer != tagVer) {
                            return curVer - tagVer;
                        }
                    }
                    // 如果前面的版本号全相同，则返回长度长的版本号 例如 1.1和 1.1.1
                    return curVerStrs.length - tagVerStrs.length;

                } catch (Exception e) {
                    return 0;
                }
            }
        }

        return 0;
    }

    public Response<BootStrapResultsDto> bootStrap(String appVersion, String ip, CommonParam commonParam) {
        long stime = System.currentTimeMillis();
        String logPrefix = "bootStrap_" + commonParam.getTerminalBrand() + "_" + commonParam.getMac() + "_"
                + commonParam.getTerminalApplication() + "_" + commonParam.getTerminalSeries() + "_" + appVersion + "_"
                + ip + " ";
        Response<BootStrapResultsDto> res = new Response<BootStrapResultsDto>();
        BootStrapResultsDto data = new BootStrapResultsDto();
        res.setData(data);
        try {
            /************* 酷派临时特殊处理 **************/
            if ("letv_superlive_app".equals(commonParam.getTerminalApplication())
                    && "coolpad".equals(commonParam.getTerminalBrand())
                    && (appVersion != null && appVersion.compareTo("1.0.32") < 0)) {
                data.setVersionName("1.0.32");
                data.setVersionUrl("http://g3.letv.cn/180/19/26/letv-itv/0/ext/common/data/html/static/android/20151229105938/signed-superLive_v1.0.32_android_release.apk?b=123456&platid=5&splatid=500");
                data.setDescription("LIVE播放组件有重要升级，为保证正常播放，强烈建议您立即升级版本。带来的不便，我们深感歉意！");
                data.setPublishTime("2015-12-29 11:00:00");// 升级包发布时间
                data.setStatus(TerminalCommonConstant.STATUS_RECOMMEND);
                data.setMessage(this.getMsg(TerminalCommonConstant.TERMINAL_VERSION_HAS_NEW, commonParam.getLangcode()));
                this.log.info(logPrefix + " coolpad special logic.");
                return res;
            }
            /************* Live桌面临时特殊处理 **************/
            if ("LIVE".equals(commonParam.getTerminalApplication()) && "letv".equals(commonParam.getTerminalBrand())
                    && (commonParam.getTerminalSeries() == null || commonParam.getTerminalSeries().length() <= 1)
                    && (appVersion != null && appVersion.compareTo("1.0.38") < 0)) {
                data.setVersionName("1.0.38");
                data.setVersionUrl("http://static.itv.letv.com/ext/common/data/html/static/android/20160112112014/superLive_38.apk");
                data.setDescription("LIVE播放组件有重要升级，为保证正常播放，强烈建议您立即升级版本。带来的不便，我们深感歉意！");
                data.setPublishTime("2016-01-15 11:00:00");// 升级包发布时间
                data.setStatus(TerminalCommonConstant.STATUS_RECOMMEND);
                data.setMessage(this.getMsg(TerminalCommonConstant.TERMINAL_VERSION_HAS_NEW, commonParam.getLangcode()));
                this.log.info(logPrefix + " LIVE special logic.");
                return res;
            }
            // 加载升级信息
            this.reloadVersionInfoNew();
            UpgradeConfigDto upgradeConfigDto = this.UCD;
            SeriesAppVersionInfo userSeriesAppVersionInfo = upgradeConfigDto.getVERSION_MAP().get(
                    commonParam.getTerminalApplication() + "_" + appVersion);
            int userVersionCode = Integer.MAX_VALUE;
            Integer versionCode = null;// invoke new upgrade service parameter
            boolean isUpgrade = false;
            if (userSeriesAppVersionInfo != null) {
                userVersionCode = userSeriesAppVersionInfo.getVersion_code();
                versionCode = userSeriesAppVersionInfo.getVersion_code();
            }
            String upgradeSwitch = ApplicationUtils.get(ApplicationConstants.IPTV_TERMINAL_UPGRADE_SWITCH);
            if (TerminalCommonConstant.TERMINAL_UPGRADE_SWITCH_ON.equals(upgradeSwitch)) {
                // 添加开关，控制是否调用新升级接口
                UpgradeTpResponse tpResponse = this.facadeTpDao.getTerminalTpDao().getUpgradeInfo(versionCode,
                        appVersion, null, commonParam);
                if ((tpResponse != null) && (tpResponse.getStatus() != null) && (tpResponse.getStatus() == 1)
                        && (tpResponse.getData() != null)) {
                    UpgradeTpResponse.UpgradeInfo upgradeInfo = tpResponse.getData();
                    Integer status = upgradeInfo.getStatus();
                    if (status != null && (status == 5 || status == 6)) {
                        isUpgrade = true;
                        data.setVersionUrl(upgradeInfo.getVersionUrl());
                        data.setVersionName(upgradeInfo.getVersionName());
                        data.setDescription(upgradeInfo.getDescription());
                        data.setPublishTime(TimeUtil.getDateStringFromLong(upgradeInfo.getTimeStamp(),
                                TimeUtil.SIMPLE_DATE_FORMAT));// 升级包发布时间
                        data.setStatus(String.valueOf(status));
                        data.setMessage(upgradeInfo.getMessage());
                        upgradeLog.info(logPrefix + ",upgrade level:" + status);
                    }
                }
            }
            if (!isUpgrade) {// 如果升级服务接口已提示升级，就不用再处理老升级逻辑
                String key = "android_" + StringUtils.trimToEmpty(commonParam.getTerminalBrand()) + "_"
                        + StringUtils.trimToEmpty(commonParam.getTerminalSeries()) + "_"
                        + commonParam.getTerminalApplication();
                List<SeriesAppVersionInfo> upgradeList = upgradeConfigDto.getUPGRADE_MAP().get(key);
                SeriesAppVersionInfo maxVersion = null;
                SeriesAppVersionInfo maxForceVersion = null;
                if ((upgradeList != null) && (upgradeList.size() > 0)) {
                    for (SeriesAppVersionInfo seriesAppVersionInfo : upgradeList) {
                        if (seriesAppVersionInfo.getType() != null && seriesAppVersionInfo.getType() == 3) {
                            continue;// 不升级版本，跳过继续下一个
                        }
                        if (seriesAppVersionInfo.getVersion_code() <= userVersionCode) {
                            break;
                        } else {
                            if (maxVersion == null) {
                                maxVersion = seriesAppVersionInfo;
                            }
                            if (maxForceVersion == null) {
                                if (seriesAppVersionInfo.getType() != null && seriesAppVersionInfo.getType() == 1) {// 强升
                                    maxForceVersion = seriesAppVersionInfo;
                                }
                            }
                            if ((maxVersion != null) && (maxForceVersion != null)) {
                                break;
                            }
                        }
                    }
                }
                if (maxVersion == null) {
                    data.setStatus("7");
                    data.setMessage(TerminalCommonConstant.getMsg(TERMINAL_VERSION_UNNEED_UPGRADE,
                            commonParam.getLangcode()));
                    this.upgradeLog.info(logPrefix + ",upgrade level:7");
                } else {
                    boolean isForce = false;
                    if (maxForceVersion != null) {
                        isForce = true;
                    }
                    data.setVersionUrl(maxVersion.getDownload_url());
                    data.setVersionName(maxVersion.getVersion_name());
                    data.setDescription(maxVersion.getDescription());
                    data.setPublishTime(TimeUtil.getDateString(maxVersion.getCreate_time(), TimeUtil.SIMPLE_DATE_FORMAT));// 升级包发布时间
                    if (maxVersion.getType() == 1 || isForce == true) {
                        data.setStatus("5");
                        data.setMessage(TerminalCommonConstant.getMsg(TERMINAL_VERSION_HAS_NEW,
                                commonParam.getLangcode()));
                        this.upgradeLog.info(logPrefix + ",upgrade level:5");
                    } else if (maxVersion.getType() == 2) {
                        data.setStatus("6");
                        data.setMessage(TerminalCommonConstant.getMsg(TERMINAL_VERSION_HAS_NEW,
                                commonParam.getLangcode()));
                        this.upgradeLog.info(logPrefix + ",upgrade level:6");
                    } else if (maxVersion.getType() == 3) {
                        data.setStatus("7");
                        data.setMessage(TerminalCommonConstant.getMsg(TERMINAL_VERSION_UNNEED_UPGRADE,
                                commonParam.getLangcode()));
                        upgradeLog.info(logPrefix + ",upgrade level:7");
                    }
                }
            }
        } catch (Exception e) {
            this.log.error(logPrefix + "error:" + e.getMessage(), e);
        } finally {
            this.log.info(logPrefix + "outter status:" + data.getStatus() + "  message:" + data.getMessage()
                    + " bootStrap is end,time:" + (System.currentTimeMillis() - stime));
        }
        return res;
    }

    private String getMsg(String code, String lang) {
        String msg = "";
        try {
            msg = MessageUtils.getMessage(code, lang);
            if (msg == null || "".equals(msg)) {

                msg = TerminalCommonConstant.MSG.get(code);
            }
        } catch (Exception e) {
        }
        return msg;
    }

    private String getTermKey(String termNameKey, String targetLangCode, CommonParam commonParam) {
        StringBuilder termKeyBuilder = new StringBuilder(StringUtils.trimToEmpty(commonParam.getTerminalApplication())
                .toUpperCase()).append("_").append(StringUtils.trimToEmpty(termNameKey).toUpperCase()).append("_")
                .append(StringUtils.trimToEmpty(commonParam.getSalesArea()).toUpperCase()).append("_");
        targetLangCode = StringUtils.trimToNull(targetLangCode);
        if (targetLangCode != null) {
            termKeyBuilder.append(StringUtils.trimToEmpty(targetLangCode).toUpperCase());
        } else {
            termKeyBuilder.append(StringUtils.trimToEmpty(commonParam.getLangcode()).toUpperCase());
        }
        return termKeyBuilder.toString();
    }

    private String getServiceTermFromCache(String targetLangCode, TerminalConstant.SerciceTermType termType, CommonParam commonParam) {
        String termKey = this.getTermKey(termType.getTermNameKey(), targetLangCode, commonParam);
        String content = StringUtils.trimToNull(this.facadeCacheDao.getTerminalCacheDao()
                .getServiceTermContent(termKey));
        if (content == null) {
            content = StringUtils.trimToNull(this.facadeTpDao.getStaticTpDao().getServiceTermContent(termKey));
            if (content != null) {
                this.facadeCacheDao.getTerminalCacheDao().setServiceTermContent(termKey, content);
            }
        }
        return content;
    }

    private void getTerminalDeviceConfig(TerminalConfigDto terminalConfigDto, String deviceModelType,
            CommonParam commonParam, String logPrefix, boolean useCache) {

        if (StringUtil.isBlank(deviceModelType)) {
            deviceModelType = "other";
        }

        if (StringUtil.isNotBlank(deviceModelType)) {
            DeviceConfig deviceConfig = null;
            Map<String, DeviceConfig> deviceConfigs = null;
            if (useCache) {
                deviceConfigs = this.facadeCacheDao.getTerminalCacheDao().getDeviceConfigs();
            }
            if (null == deviceConfigs || (null != commonParam.getFlush() && 1 == commonParam.getFlush())) {
                Object retDeviceConfigs = this.facadeTpDao.getStaticTpDao().getDeviceConfig(null);
                if (null != retDeviceConfigs && retDeviceConfigs instanceof Map) {
                    deviceConfigs = (Map<String, DeviceConfig>) retDeviceConfigs;
                }
                this.facadeCacheDao.getTerminalCacheDao().setDeviceConfigs(deviceConfigs, 86400); // 24hr
            }
            if (null != deviceConfigs) {
                deviceConfig = deviceConfigs.get(deviceModelType);
            }
            terminalConfigDto.setDeviceConfig(deviceConfig);
        }
    }

    private void getTerminalAppConfig(TerminalConfigDto terminalConfigDto, CommonParam commonParam, String logPrefix,
            boolean useCache) {
        if (null != terminalConfigDto) {
            String appConfigs = null;
            String profile = ApplicationUtils.get(ApplicationConstants.IPTV_TERMINAL_CONFIG_PROFILE);
            if (StringUtil.isBlank(profile) || "prod".equals(profile)) {
                profile = "";
            } else {
                profile = "-" + profile;
            }
            if (useCache) {
                appConfigs = this.facadeCacheDao.getTerminalCacheDao().getAppConfigs(profile);
            }
            if (null == appConfigs || (null != commonParam.getFlush() && 1 == commonParam.getFlush())) {
                appConfigs = this.facadeTpDao.getStaticTpDao().getAppConfig(commonParam);
                if (StringUtil.isNotBlank(appConfigs)) {
                    appConfigs = getDefaultWzJump(appConfigs, logPrefix);
                    this.facadeCacheDao.getTerminalCacheDao().setAppConfigs(appConfigs, 86400, profile); // 24hr
                }
            }

            if (StringUtil.isNotBlank(appConfigs)) {
                if (null == terminalConfigDto.getConfigInfo()) {
                    terminalConfigDto.setConfigInfo(new HashMap<String, Object>());
                }

                try {
                    terminalConfigDto.setAppConfig(JSONObject.parse(appConfigs));
                } catch (Exception e) {
                    this.log.error(logPrefix + "getTerminalAppConfig() error:" + e.getMessage(), e);
                }
            }
        }
    }

    private String getDefaultWzJump(String appConfigs, String logPrefix) {
        if (logPrefix == null) {
            return appConfigs;
        }
        if (StringUtil.isNotBlank(appConfigs)) {
            try {
                Map<String, Object> results = (Map<String, Object>) JSONObject.parse(appConfigs);
                if (results != null) {
                    Map<String, Object> defaultUnknown = (Map<String, Object>) results.get("defaultUnknown");
                    if (defaultUnknown == null) {
                        return appConfigs;
                    }
                    if (defaultUnknown != null) {
                        String jumpSource = (String) defaultUnknown.get("jumpSource");
                        if (StringUtil.isBlank(jumpSource)) {
                            return appConfigs;
                        }
                        JumpData jumpData = VipV2Service.getJumpData(jumpSource, "", "", null);
                        if (jumpData != null) {
                            // String newJump =
                            // JSONObject.toJSONString(jumpData);
                            defaultUnknown.put("jump", jumpData);
                            defaultUnknown.remove("jumpSource");
                            return JSONObject.toJSONString(results);
                        }
                    }
                }
            } catch (Exception e) {
                this.log.error(logPrefix + "getDefaultWzJump() error:" + e.getMessage(), e);
            }
        }
        return appConfigs;
    }
}
