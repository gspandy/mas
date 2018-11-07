package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.RecommendationTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(value = "v2.StatTpDao")
public class StatTpDao extends BaseTpDao {

    // refer: http://wiki.letv.cn/pages/viewpage.action?pageId=19760167

    private final static Logger log = LoggerFactory.getLogger(RecommendationTpDao.class);

    /**
     * 接口功能：播放数、打分数、评论数查询
     */
    private final String STAT_URL = ApplicationUtils.get(ApplicationConstants.USERCENTER_STAT_ALBUM_QUERY);
    private final String STATS_URL = ApplicationUtils.get(ApplicationConstants.USERCENTER_STAT_ALBUMS_QUERY);

    /**
     * 接口功能：获取各个频道视频更新数
     */
    private final String CHANNEL_UPDATE_CNT = ApplicationUtils.get(ApplicationConstants.USERCENTER_STAT_CHANNEL_QUERY);

    /**
     * 获取播放数、打分数、评论数
     * @param pid
     *            专辑id
     * @return
     */
    public TotalCountStatTpResponse getTotalCountStat(Long pid) {
        TotalCountStatTpResponse response = new TotalCountStatTpResponse();
        StringBuilder url = new StringBuilder(this.STAT_URL);
        try {
            if (pid != null) {
                url.append("?pid=").append(pid);
            }
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, TotalCountStatTpResponse.class);
                if (null != response
                        && ((StringUtil.isBlank(response.getPlist_count())
                                || StringUtil.isBlank(response.getMedia_play_count()) || StringUtil.isBlank(response
                                .getMedia_play_count())) || (StringUtil.isNotBlank(response.getPlist_count()) && "0"
                                .equals(response.getPlist_count()))
                                && (StringUtil.isNotBlank(response.getMedia_play_count()) && "0".equals(response
                                        .getMedia_play_count()))
                                && (StringUtil.isNotBlank(response.getPcomm_count()) && "0".equals(response
                                        .getPcomm_count())))) {
                    log.error("[getTotalCountStat]get total count stat fail, url:[" + url
                            + "]. message: result could be error");
                }
            } else {
                log.error("[getTotalCountStat]get total count stat fail, url:[" + url + "]. message: result is null");
            }
        } catch (Exception e) {
            log.error("[getTotalCountStat]get total count stat fail, url:[" + url + "]. message:" + e.getMessage(), e);
        }
        return response;
    }

    /**
     * 批量获取播放数、打分数、评论数
     * @param pids
     *            专辑id
     * @return
     */
    public List<TotalCountStatTpResponse> mgetTotalCountStat(String pids) {
        List<TotalCountStatTpResponse> response = null;
        StringBuilder url = new StringBuilder(this.STATS_URL);
        try {
            url.append("?type=plist");
            if (StringUtil.isNotBlank(pids)) {
                url.append("&ids=").append(pids);
            }
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new TypeReference<List<TotalCountStatTpResponse>>() {
                });
                if (null != response && (response.isEmpty() || this.isInvalidTotalCountStatTpResponse(response))) {
                    log.error("[mgetTotalCountStat]get total count stat fail, url:[" + url
                            + "]. message: result could be error");
                }
            } else {
                log.error("[mgetTotalCountStat]get total count stat fail, url:[" + url + "]. message: result is null");
            }
        } catch (Exception e) {
            log.error("[mgetTotalCountStat]get total count stat fail, url:[" + url + "]. message:" + e.getMessage(), e);
        }
        return response;
    }

    /**
     * 接口功能：获取各个频道视频更新数
     */
    public Map<String, Integer> getUpdateCnt() {
        Map<String, Integer> res = new HashMap<String, Integer>();

        try {
            String result = this.restTemplate.getForObject(this.CHANNEL_UPDATE_CNT, String.class);
            if (StringUtil.isNotBlank(result)) {
                res = objectMapper.readValue(result, Map.class);
            }
        } catch (Exception e) {
            this.log.error(e.getMessage(), e);
        }

        return res;
    }

    public boolean isInvalidTotalCountStatTpResponse(Object response) {
        boolean ret = false;
        if (response instanceof TotalCountStatTpResponse) {
            TotalCountStatTpResponse totalCountStatTpResponse = (TotalCountStatTpResponse) response;
            if ((StringUtil.isBlank(totalCountStatTpResponse.getPlist_count()) || "0".equals(totalCountStatTpResponse
                    .getPlist_count()))
                    && (StringUtil.isBlank(totalCountStatTpResponse.getMedia_play_count()) || "0"
                            .equals(totalCountStatTpResponse.getMedia_play_count()))
                    && (StringUtil.isBlank(totalCountStatTpResponse.getPcomm_count()) || "0"
                            .equals(totalCountStatTpResponse.getPcomm_count()))) {
                ret = true;
            }
        } else if (response instanceof List) {
            List responseList = (List) response;
            for (Object responseItem : responseList) { // TODO: 目前全检
                if (ret = this.isInvalidTotalCountStatTpResponse(responseItem)) {
                    break;
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        TotalCountStatTpResponse r = new StatTpDao().getTotalCountStat(92520l);
        System.out.print(r.getPlist_play_count());
    }
}
