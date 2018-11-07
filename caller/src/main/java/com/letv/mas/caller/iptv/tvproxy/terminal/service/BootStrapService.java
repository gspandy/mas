package com.letv.mas.caller.iptv.tvproxy.terminal.service;

import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.SeriesAppRelationMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.SeriesAppVersionInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.TpSeriesAppVersionInfoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.terminal.response.UpgradeTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;
import com.letv.mas.caller.iptv.tvproxy.terminal.model.dto.BootStrapRequest;
import com.letv.mas.caller.iptv.tvproxy.terminal.model.dto.BootStrapResultsDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.List;

@Component(value = "v2.bootStrapService")
public class BootStrapService extends BaseService {
    private final Logger upgradeLog = LoggerFactory.getLogger("upgradeDataLog");
    private final Logger log = LoggerFactory.getLogger(BootStrapService.class);

    // private int series_app_id = 0;// 平台、型号和应用的关系表id

    /**
     * 激活验证，获取版本信息
     * @param request
     * @param commonParam
     * @return
     */
    public Response<BootStrapResultsDto> bootStrap(BootStrapRequest request, CommonParam commonParam) {
        long stime = System.currentTimeMillis();
        this.log.info("terminalAuth is start,requst:" + request);
        Response<BootStrapResultsDto> res = new Response<BootStrapResultsDto>();
        BootStrapResultsDto data = new BootStrapResultsDto();
        res.setData(data);
        try {
            /************* 酷派临时特殊处理 **************/
            if (request.getTerminalApplication().equals("letv_superlive_app")
                    && request.getTerminalBrand().equals("coolpad")
                    && request.getInstallVersion().compareTo("1.0.32") < 0) {
                data.setVersionName("1.0.32");
                data.setVersionUrl("http://g3.letv.cn/180/19/26/letv-itv/0/ext/common/data/html/static/android/20151229105938/signed-superLive_v1.0.32_android_release.apk?b=123456&platid=5&splatid=500");
                data.setDescription("LIVE播放组件有重要升级，为保证正常播放，强烈建议您立即升级版本。带来的不便，我们深感歉意！");
                data.setPublishTime("2015-12-29 11:00:00");// 升级包发布时间
                data.setStatus(TerminalCommonConstant.STATUS_RECOMMEND);
                data.setMessage(this.getMsg(TerminalCommonConstant.TERMINAL_VERSION_HAS_NEW, request.getLangcode()));
                return res;
            }
            /************* Live桌面临时特殊处理 **************/
            if (request.getTerminalApplication().equals("LIVE") && request.getTerminalBrand().equals("letv")
                    && request.getTerminalSeries().length() <= 1 && request.getInstallVersion().compareTo("1.0.38") < 0) {
                data.setVersionName("1.0.38");
                data.setVersionUrl("http://static.itv.letv.com/ext/common/data/html/static/android/20160112112014/superLive_38.apk");
                data.setDescription("LIVE播放组件有重要升级，为保证正常播放，强烈建议您立即升级版本。带来的不便，我们深感歉意！");
                data.setPublishTime("2016-01-15 11:00:00");// 升级包发布时间
                data.setStatus(TerminalCommonConstant.STATUS_RECOMMEND);
                data.setMessage(this.getMsg(TerminalCommonConstant.TERMINAL_VERSION_HAS_NEW, request.getLangcode()));
                return res;
            }
            /************* 激活认证 **************/
            data = this.doRegister(request, data);
            /************ 获取升级信息 ************/
            // data = this.getVerionInfo(request, data);
            String upgradeSwitch = ApplicationUtils.get(ApplicationConstants.IPTV_TERMINAL_UPGRADE_SWITCH);
            if (TerminalCommonConstant.TERMINAL_UPGRADE_SWITCH_ON.equals(upgradeSwitch)) {
                // 添加开关，控制是否调用新升级接口
                data = this.getVerionInfoNew(request, data, commonParam);
            } else {
                data = this.getVerionInfo(request, data);
            }
        } catch (Exception e) {
            this.log.error(e.getMessage(), e);
        } finally {
            this.log.info("ip:" + request.getIp());
            this.log.info("outter status:" + data.getStatus() + "  message:" + data.getMessage());
            this.log.info("terminalAuth is end,time:" + (System.currentTimeMillis() - stime));
        }
        return res;
    }

    private BootStrapResultsDto doRegister(BootStrapRequest request, BootStrapResultsDto data) {
        SeriesAppRelationMysqlTable seriesAppRelation = this.facadeMysqlDao.getTpTerminalMysqlDao().getSeriesAppInfo(
                request.getTerminalBrand().trim(), "android", request.getTerminalSeries(),
                request.getTerminalApplication().trim());

        if (seriesAppRelation != null) {
            request.setSeries_app_id(seriesAppRelation.getSeries_app_id());
        }

        return data;
    }

    /**
     * 用于组织版本信息
     * @param request
     * @return
     * @throws ParseException
     */
    private BootStrapResultsDto getVerionInfo(BootStrapRequest request, BootStrapResultsDto data) {
        List<TpSeriesAppVersionInfoMysqlTable> highVersions = this.facadeMysqlDao.getTpVersionMysqlDao()
                .getHighVersionList(request.getSeries_app_id(), request.getInstallVersion());
        if (highVersions != null && highVersions.size() > 0) {
            // highVersions里面存储了当前版本之上的所有强制和推荐升级版本，推荐升级的type=2，强制升级的type=1
            // 如果所有条目的type之和=highVersions.size()*2，那么说明，highVersions中都是推荐升级
            // 如果所有条目的type之和<highVersions.size()*2，那么说明，highVersions中都是推荐升级
            boolean forceUpdate = false;// 强制升级标记
            if (highVersions.get(0).getEnforce_up_id() > 0) {// 最新版本强制升级
                forceUpdate = true;
            } else {// 或者中间有一版本强制升级
                for (TpSeriesAppVersionInfoMysqlTable s : highVersions) {
                    if (s.getType() != null && s.getType() == 1) {
                        forceUpdate = true;
                        break;
                    }
                }
            }
            if (forceUpdate) {// 强升逻辑
                data.setStatus(TerminalCommonConstant.STATUS_FORCE);
                data.setMessage(this.getMsg(TerminalCommonConstant.TERMINAL_VERSION_HAS_NEW, request.getLangcode()));
                upgradeLog.info(request + ",upgrade level:5");
            } else {// 推荐升级
                data.setStatus(TerminalCommonConstant.STATUS_RECOMMEND);
                data.setMessage(this.getMsg(TerminalCommonConstant.TERMINAL_VERSION_HAS_NEW, request.getLangcode()));
                upgradeLog.info(request + ",upgrade level:6");
            }
            data.setVersionUrl(highVersions.get(0).getDownload_url());
            data.setVersionName(highVersions.get(0).getVersion_name());
            data.setDescription(highVersions.get(0).getDescription());
            data.setPublishTime(TimeUtil.getDateString(highVersions.get(0).getCreate_time(),
                    TimeUtil.SIMPLE_DATE_FORMAT));// 升级包发布时间
        } else {
            data.setStatus(TerminalCommonConstant.STATUS_NORMAL);
            data.setMessage(this.getMsg(TerminalCommonConstant.TERMINAL_VERSION_UNNEED_UPGRADE, request.getLangcode()));
            upgradeLog.info(request + ",upgrade level:7");
        }
        return data;
    }

    private BootStrapResultsDto getVerionInfoNew(BootStrapRequest request, BootStrapResultsDto dataResult,
            CommonParam commonParam) {
        String appVersion = StringUtils.trimToEmpty(request.getInstallVersion());
        List<SeriesAppVersionInfo> versionList = this.facadeMysqlDao.getTpVersionMysqlDao().getVersionList(
                request.getSeries_app_id(), appVersion);
        boolean isUpgrade = false;
        if (!CollectionUtils.isEmpty(versionList)) {
            Integer userVersionCode = Integer.MAX_VALUE;
            for (SeriesAppVersionInfo seriesAppVersionInfo : versionList) {
                userVersionCode = seriesAppVersionInfo.getVersion_code();
                break;
            }
            UpgradeTpResponse tpResponse = this.facadeTpDao.getTerminalTpDao().getUpgradeInfo(userVersionCode,
                    appVersion, null, commonParam);
            if ((tpResponse != null) && (tpResponse.getStatus() != null) && (tpResponse.getStatus() == 1)
                    && (tpResponse.getData() != null)) {
                UpgradeTpResponse.UpgradeInfo data = tpResponse.getData();
                Integer status = data.getStatus();
                if (status != null && (status == 5 || status == 6)) {
                    isUpgrade = true;
                    dataResult.setVersionUrl(data.getVersionUrl());
                    dataResult.setVersionName(data.getVersionName());
                    dataResult.setDescription(data.getDescription());
                    dataResult.setPublishTime(TimeUtil.getDateStringFromLong(data.getTimeStamp(),
                            TimeUtil.SIMPLE_DATE_FORMAT));// 升级包发布时间
                    dataResult.setStatus(String.valueOf(status));
                    dataResult.setMessage(data.getMessage());
                    upgradeLog.info(request + ",upgrade level:" + status);
                }
            }
        }
        if (!isUpgrade) {
            dataResult = getVerionInfo(request, dataResult);
        }

        return dataResult;
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

}
