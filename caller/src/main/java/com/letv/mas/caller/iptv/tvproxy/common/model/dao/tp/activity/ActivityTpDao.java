package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.activity;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.VipTpDaoBak;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.activity.response.ActivityBatchTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.activity.response.AlbumDetailTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ActivityTpDao extends BaseTpDao {
    private static final Logger log = Logger.getLogger(VipTpDaoBak.class);

    /**
     * 根据观星系统定义的运营位标识获取对应位置的活动数据
     * @param posids
     *            观星系统定义的运营位标识，多个值之间用英文逗号隔开
     * @param commonParam
     *            通用参数
     * @return
     */
    public ActivityBatchTpResponse getActivity(String direct, String posids, CommonParam commonParam) {
        ActivityBatchTpResponse response = null;

        try {
            StringBuilder url = new StringBuilder(ActivityTpConstant.ACTIVITY_PROMOTION_URL);
            if (StringUtil.isBlank(commonParam.getMac())) {
                return response;
            }
            if (StringUtil.isErrorMacForGuanXing(commonParam)) {
                url = new StringBuilder(ActivityTpConstant.GET_GUANXING_DATA_ERROR_URL);
            } else {
                url.append("posid=").append(posids);// 运营位标识
                url.append("&mac=").append(StringUtils.trimToEmpty(commonParam.getMac()).toLowerCase());// 设备mac
                if (StringUtil.isNotBlank(commonParam.getUserId())) {
                    url.append("&uid=").append(commonParam.getUserId());// 用户ID
                }
                if (StringUtil.isNotBlank(direct)) {
                    url.append("&direct=").append(direct);
                }
                if (StringUtil.isNotBlank(commonParam.getDeviceKey())) {
                    url.append("&deviceKey=").append(commonParam.getDeviceKey());// 乐视机卡电视的机卡标识
                }
            }

            String result = this.restTemplate.getForObject(url.toString(), String.class);
            /*
             * log.info("getPromotionActivity_" + commonParam.getMac() + "_" +
             * commonParam.getUserId() + "_" + posids
             * + " invoke return [" + result + "]");
             */
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, ActivityBatchTpResponse.class);
            }
        } catch (Exception e) {
            log.error("getActivity_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + posids + " error:"
                    + e.getMessage(), e);
        }

        return response;
    }

    /**
     * 当从观星获取不到数据时，走代理层的static数据
     */
    public ActivityBatchTpResponse getActivityDefaultData(String direct, String posids, CommonParam commonParam) {
        ActivityBatchTpResponse response = null;

        try {
            StringBuilder url = new StringBuilder(ActivityTpConstant.ACTIVITY_PROMOTION_NO_DATA_URL);
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            /*
             * log.info("getPromotionActivityNoData_" + commonParam.getMac() +
             * "_" + commonParam.getUserId() + "_" + posids
             * + " invoke return [" + result + "]");
             */
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, ActivityBatchTpResponse.class);
            }
        } catch (Exception e) {
            log.error("getActivityDefaultData_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + posids
                    + " error:" + e.getMessage(), e);
        }

        return response;
    }

    /**
     * 获取静态详情页用于分析多少集开始收费
     */

    public AlbumDetailTpResponse getAlbumDetail(String albumid, CommonParam commonParam) {
        AlbumDetailTpResponse response = null;

        try {
            StringBuilder url = new StringBuilder(ActivityTpConstant.ALBUM_DETAIL_URL);
            url.append("albumid=").append(albumid);// 专辑ID
            if (StringUtil.isNotBlank(commonParam.getMac())) {
                url.append("&mac=").append(commonParam.getMac());
            }
            if (StringUtil.isNotBlank(commonParam.getSalesArea())) {
                url.append("&salesArea=").append(commonParam.getSalesArea());
            }
            if (StringUtil.isNotBlank(commonParam.getTerminalApplication())) {
                url.append("&terminalApplication=").append(commonParam.getTerminalApplication());
            }
            if (StringUtil.isNotBlank(commonParam.getAppCode())) {
                url.append("&appCode=").append(commonParam.getAppCode());
            }
            if (StringUtil.isNotBlank(commonParam.getToken())) {
                url.append("&token=").append(commonParam.getToken());
            }
            if (StringUtil.isNotBlank(commonParam.getUserId())) {
                url.append("&userId=").append(commonParam.getUserId());
            }
            if (StringUtil.isNotBlank(commonParam.getBsChannel())) {
                url.append("&bsChannel=").append(commonParam.getBsChannel());
            }
            if (StringUtil.isNotBlank(commonParam.getAppVersion())) {
                url.append("&appVersion=").append(commonParam.getAppVersion());
            }
            if (StringUtil.isNotBlank(commonParam.getTerminalBrand())) {
                url.append("&terminalBrand=").append(commonParam.getTerminalBrand());
            }
            if (StringUtil.isNotBlank(commonParam.getClient())) {
                url.append("&client=").append(commonParam.getClient());
            }
            if (StringUtil.isNotBlank(commonParam.getDeviceKey())) {
                url.append("&deviceKey=").append(commonParam.getDeviceKey());
            }
            if (StringUtil.isNotBlank(commonParam.getTerminalSeries())) {
                url.append("&terminalSeries=").append(commonParam.getTerminalSeries());
            }
            if (commonParam.getP_devType() != null) {
                url.append("&p_devType=").append(commonParam.getP_devType());
            }
            if (StringUtil.isNotBlank(commonParam.getWcode())) {
                url.append("&wcode=").append(commonParam.getWcode());
            }
            if (commonParam.getBroadcastId() != null) {
                url.append("&broadcastId=").append(commonParam.getBroadcastId());
            }
            if (StringUtil.isNotBlank(commonParam.getLangcode())) {
                url.append("&langcode=").append(commonParam.getLangcode());
            }
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            /*
             * log.info("getAlbumDetail_" + commonParam.getMac() + "_" +
             * commonParam.getUserId()
             * + " invoke return [" + result + "]");
             */
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, AlbumDetailTpResponse.class);
            }
        } catch (Exception e) {
            log.error(
                    "getAlbumDetail_" + commonParam.getMac() + "_" + commonParam.getUserId() + " error:"
                            + e.getMessage(), e);
        }

        return response;
    }

    /**
     * 自动拉取观星打底接口
     */
    public ActivityBatchTpResponse getActivityDefaultData(String posIds, String uid, String mac, String[] str) {
        ActivityBatchTpResponse response = null;
        if (StringUtil.isBlank(posIds) || StringUtil.isBlank(mac)) {
            return response;
        }
        try {
            String urlBase = ActivityTpConstant.ACTIVITY_PROMOTION_URL;
            StringBuilder url = new StringBuilder(urlBase);
            url.append("posid=").append(posIds);// 运营位标识
            url.append("&mac=").append(mac);// 设备mac
            String direct = null;
            if (StringUtil.isNotBlank(uid)) {
                url.append("&uid=").append(uid);// uid
                direct = ActivityTpConstant.ACTIVITY_LOGIN_DIRECT;
            } else {
                uid = "";
                direct = ActivityTpConstant.ACTIVITY_NOLOGIN_DIRECT;
            }
            if (StringUtil.isNotBlank(direct)) {
                url.append("&direct=").append(direct);
            }

            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, ActivityBatchTpResponse.class);
                if (str != null && str.length > 0) {
                    str[0] = result;
                }
                log.info("daemon--getActivityDefaultData:" + mac + "_" + uid + "_" + posIds + " invoke return ["
                        + result + "]");
            } else {
                log.info("daemon--getActivityDefaultData:" + mac + "_" + uid + "_" + posIds + " invoke return is null ");
            }
        } catch (Exception e) {
            log.error("getActivityDefaultData" + " error:" + e.getMessage(), e);
        }
        return response;
    }
}
