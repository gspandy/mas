package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel;

import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.GetUsUrlDataTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.WFSubjectTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("v2.ChannelTpDao")
public class ChannelTpDao extends BaseTpDao {

    private final static Logger log = LoggerFactory.getLogger(ChannelTpDao.class);

    /**
     * 接口功能：获取各个频道视频更新数
     */
    public Map<String, Integer> getUpdateCnt() {
        String url = ApplicationUtils.get(ApplicationConstants.USERCENTER_STAT_CHANNEL_QUERY);

        Map<String, Integer> res = new HashMap<String, Integer>();

        try {
            String result = this.restTemplate.getForObject(url, String.class);
            if (StringUtil.isNotBlank(result)) {
                res = objectMapper.readValue(result, Map.class);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return res;
    }

    /**
     * 获得北美版收银台url地址
     * @return
     */
    public GetUsUrlDataTpResponse getUsUrlData() {
        GetUsUrlDataTpResponse response = null;

        try {
            StringBuilder url = new StringBuilder(ChannelTpConstant.US_CASHIER_URL);
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, GetUsUrlDataTpResponse.class);
            }
        } catch (Exception e) {
            log.error("getUsUrlData_error:" + e.getMessage(), e);
        }

        return response;
    }

    public WFSubjectTpResponse getWFSubjectById(String pageId) {
        WFSubjectTpResponse response = null;
        try {
            StringBuilder url = new StringBuilder(ChannelTpConstant.WF_SUBJECT_STATIC_URL);
            url.append("subjectId=").append(pageId);// 用户ID
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, WFSubjectTpResponse.class);
            }
        } catch (Exception e) {
            log.error("getUsUrlData_error:" + e.getMessage(), e);
        }

        return response;
    }
}
