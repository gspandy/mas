package com.letv.mas.caller.iptv.tvproxy.user.model.dao.tp;

import com.letv.mas.caller.iptv.tvproxy.user.annotation.Iptv;
import com.letv.mas.caller.iptv.tvproxy.user.util.ConfigOperationUtil;
import com.letv.mas.caller.iptv.tvproxy.user.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.user.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.user.model.dto.response.ReportTpResponse;
import com.letv.mas.caller.iptv.tvproxy.user.util.CommonUtil;
import com.letv.mas.caller.iptv.tvproxy.user.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Iptv
public class ActReportDao extends BaseTpDao {

    // refer: http://wiki.letv.cn/pages/viewpage.action?pageId=70590725
    private final static Logger log = LoggerFactory.getLogger(ActReportDao.class);
    private final Log actReportLog = LogFactory.getLog("actReportLog");

    /**
     * 接口功能：防盗链行为上报接口
     */
    //private final String URI_ANTI_REPORT = ConfigOperationUtil.get(ApplicationConstants.ACT_REPORT_ANTI_SET_URI);

    private String getUriAntiReport(){
        return ConfigOperationUtil.get(ApplicationConstants.ACT_REPORT_ANTI_SET_URI);
    }

    /**
     * 接口功能：防盗链行为上报更新接口
     */
    //private final String URI_ANTI_OPT = ConfigOperationUtil.get(ApplicationConstants.ACT_REPORT_ANTI_OPT_URI);
    private String getUriAntiOpt(){
        return ConfigOperationUtil.get(ApplicationConstants.ACT_REPORT_ANTI_OPT_URI);
    }
    // 删除key，ttl可不传
    public static final int ANTI_OPT_TYPE_DELETE = 0;
    // 只更新key的ttl，value不变
    public static final int ANTI_OPT_TYPE_UPDATE_TTL = 1;
    // 查询key、value、ttl
    public static final int ANTI_OPT_TYPE_QUERY = 2;
    // ttl存在：更新key、value、ttl, ttl不存在：更新key、value、ttl使用默认
    public static final int ANTI_OPT_TYPE_UPDATE_ALL = 3;
    // 删除黑名单中的key
    public static final int ANTI_OPT_TYPE_DELETE_B = 4;
    // 查询key是否在黑名单中，并返回value
    public static final int ANTI_OPT_TYPE_QUERY_B = 5;
    // 查询key是否在黑名单中，并返回key、value、ttl
    public static final int ANTI_OPT_TYPE_QUERY_B_ALL = 6;

    /**
     * 防盗链行为上报
     * @param splatid
     * @param commonParam
     * @return
     */
    // @Async("antiReportAsyncExecutor")
    public ReportTpResponse setAntiReport(String splatid, CommonParam commonParam) {
        ReportTpResponse response = new ReportTpResponse();
        StringBuilder url = new StringBuilder(this.getUriAntiReport());
        try {
            if (StringUtil.isNotBlank(splatid)) {
                url.append("?anti_splatid=").append(splatid);
            } else {
                throw new RuntimeException("param[anti_splatid] is null.");
            }

            if (StringUtil.isNotBlank(commonParam.getDevSign())) {
                url.append("&sign_dev=").append(commonParam.getDevSign());
            } else {
                throw new RuntimeException("param[sign_dev] is null.");
            }

            if (StringUtil.isNotBlank(commonParam.getMac())) {
                url.append("&mac=").append(commonParam.getMac());
            }

            if (StringUtil.isNotBlank(commonParam.getDevId())) {
                url.append("&devId=").append(commonParam.getDevId());
            }

            if (StringUtil.isNotBlank(commonParam.getTerminalSeries())) {
                String ts = CommonUtil.urlEncode(commonParam.getTerminalSeries());
                url.append("&terminalSeries=").append(ts);
            }

            if (StringUtil.isNotBlank(commonParam.getUserId())) {
                url.append("&uid=").append(commonParam.getUserId());
            }

            if (StringUtil.isNotBlank(commonParam.getAppCode())) {
                url.append("&appCode=").append(commonParam.getAppCode());
            }

            if (StringUtil.isNotBlank(commonParam.getTerminalApplication())) {
                url.append("&terminalApplication=").append(commonParam.getTerminalApplication());
            }

            if (StringUtil.isNotBlank(commonParam.getBsChannel())) {
                url.append("&bsChannel=").append(commonParam.getBsChannel());
            }

            if (StringUtil.isNotBlank(commonParam.getWcode())) {
                url.append("&wcode=").append(commonParam.getWcode());
            }

            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response.setTtl(Long.parseLong(result));
                response.setCode(1);
            } else {
                response.setCode(0);
                result = "";
            }
            //log.info("[ActReportDao.setAntiReport]:url=" + url + ", result=" + response.getCode());
            actReportLog.info("|[setAntiReport]|" + url + "|" + result);
        } catch (Exception e) {
            response.setCode(-1);
            log.error("[ActReportDao.setAntiReport]:url=" + url + ",result=" + response.getCode()
                    + ",error=" + e.getMessage(), e);
            actReportLog.info("|[setAntiReport]|" + url + "|" + -1);
        }
        return response;
    }


    /**
     * 防盗链行为上报更新
     * @param type
     * @param key
     * @param value
     * @param ttl
     * @return
     */
    public ReportTpResponse updateAntiReport(int type, String key, String value, int ttl) {
        ReportTpResponse response = new ReportTpResponse();
        StringBuilder url = new StringBuilder(this.getUriAntiOpt());
        try {
            if (type >= ANTI_OPT_TYPE_DELETE && ANTI_OPT_TYPE_DELETE <= ANTI_OPT_TYPE_QUERY_B_ALL) {
                url.append("?type=").append(type);
            } else {
                throw new RuntimeException("param[type] is illegal.");
            }

            if (StringUtil.isNotBlank(key)) {
                url.append("&key=").append(key);
            } else {
                throw new RuntimeException("param[key] is null.");
            }

            if (StringUtil.isNotBlank(value)) {
                url.append("&value=").append(value);
            }

            if (ttl > -2) {
                url.append("&ttl=").append(ttl);
            }

            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = this.parseAntiReport(result);
                response.setCode(1);
            } else {
                response.setCode(0);
                result = "";
            }
            //log.info("[ActReportDao.updateAntiReport]:url=" + url + ", result=" + response.getCode());
            actReportLog.info("|[setAntiReport]|" + url + "|" + result);
        } catch (Exception e) {
            response.setCode(-1);
            log.error("[ActReportDao.updateAntiReport]:url=" + url + ",result=" + response.getCode()
                    + ",error=" + e.getMessage(), e);
            actReportLog.info("|[setAntiReport]|" + url + "|" + -1);
        }
        return response;
    }

    private ReportTpResponse parseAntiReport(String result) {
        ReportTpResponse response = new ReportTpResponse();
        String[] resultAry = result.split(",");
        if (null != resultAry) {
            String[] keyValue = null;
            for (int i = 0; i < resultAry.length; i++) {
                if (null != resultAry[i]) {
                    keyValue = resultAry[i].split(":");
                    if (keyValue.length == 2) {
                        if ("key".equals(keyValue[0])) {
                            response.setKey(keyValue[1]);
                        } else if ("value".equals(keyValue[0])) {
                            response.setValue(keyValue[1]);
                        } else if ("ttl".equals(keyValue[0])) {
                            Long ttl = null;
                            try {
                                ttl = Long.parseLong(keyValue[1]);
                                response.setTtl(ttl);
                            } catch (NumberFormatException nfe) {

                            }
                        }
                    }
                }
            }
        }
        return response;
    }
}
