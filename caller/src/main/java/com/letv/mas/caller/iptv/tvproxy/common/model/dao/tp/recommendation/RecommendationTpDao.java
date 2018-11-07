package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation;

import java.net.URLDecoder;
import java.util.Map;

import javax.annotation.Resource;

import com.fasterxml.jackson.core.JsonParseException;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LocaleConstant;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.RecommendationTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import io.netty.util.internal.StringUtil;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.fasterxml.jackson.core.type.TypeReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import rec.recommend.RecommendationRequest;
import rec.recommend.RecommendationResponse;
import serving.GenericServing;
import serving.GenericServingRequest;
import serving.GenericServingResponse;
import serving.PlayControlPlatform;
import serving.RequestDocType;
import shared.datatypes.ProductCode;
import com.letv.scheduler.thrift.core.CountryCode;

@Component
public class RecommendationTpDao extends BaseTpDao {

    private final static Logger log = LoggerFactory.getLogger(RecommendationTpDao.class);

    @Resource
    private GenericServing.Iface recommendationServing;

    // @Resource
    private GenericServing.AsyncIface asyncRecommendationServing;

    /**
     * 多板块推荐
     * uid: 用户Id
     * lc: 未登录用户标识 ，取mac值
     * cid：频道Id
     * type：限定推荐视频类型
     * pid：专辑Id
     * vid：视频Id
     * pageid：推荐页面Id
     * area：推荐页面区域 rec_0001相关推荐
     * history：相关历史
     * num：推荐返回数据量
     * region：区域
     * lang：语言
     * @return
     */
    public Map<String,RecommendationTpResponse> getMultiBlocks(Integer cid, String type, Integer pid, Integer vid,
                                                                                                  String pageid, String area, String history, Integer num, CommonParam commonParam) {
        String logPrefix = "getMultiBlocks_" + commonParam.getUserId() + "_" + commonParam.getMac() + "_" + type + "_"
                + pid + "_" + vid + "_" + area + "_" + pageid + "_";
        Map<String, RecommendationTpResponse> recommendationTpResponse = null;
        String response = null;
        StringBuilder url = new StringBuilder();
        try {
            url.append(RecommendationTpConstant.getRecURL(commonParam));
            url.append("pt=").append(RecommendationTpConstant.REQUEST_URL_PARAM_PT);// 播放平台，固定是TV
            url.append("&random=").append(System.currentTimeMillis());// 随机整数，防缓存
            url.append("&versiontype=IntelligentOperation").append("&action=rec").append("&disable_record_exposure=1");
            if (commonParam.getUserId() != null) {
                url.append("&uid=").append(commonParam.getUserId());
            }
            if (!StringUtil.isNullOrEmpty(commonParam.getMac())) {
                url.append("&lc=").append(commonParam.getMac());
            }
            if (cid != null) {
                url.append("&cid=").append(cid);
            }
            if (!StringUtil.isNullOrEmpty(type)) {
                url.append("&type=").append(type);
            }
            if (pid != null) {
                url.append("&pid=").append(pid);
            }
            if (vid != null) {
                url.append("&vid=").append(vid);
            }
            if (!StringUtil.isNullOrEmpty(pageid)) {
                url.append("&pageid=").append(pageid);
            }
            if (!StringUtil.isNullOrEmpty(area)) {
                url.append("&area=").append(area);
            }
            if (!StringUtil.isNullOrEmpty(history)) {
                url.append("&history=").append(history);
            }
            if (num != null) {
                url.append("&num=").append(num);
            }
            if (commonParam.getWcode() != null) {
                url.append("&region=").append(commonParam.getWcode());
            }
            if (commonParam.getLangcode() != null && !commonParam.getLangcode().equalsIgnoreCase("zh_cn")
                    && !commonParam.getLangcode().equalsIgnoreCase("zh-cn")) {
                url.append("&lang=").append(commonParam.getLangcode());
            }
            if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() > 0) {
                url.append("&bc=").append(commonParam.getBroadcastId());
            }
            response = this.restTemplate.getForObject(url.toString(), String.class);
            if (!StringUtil.isNullOrEmpty(response)) {
                recommendationTpResponse = objectMapper.readValue(response,
                        new TypeReference<Map<String, RecommendationTpResponse>>() {
                        });
            }
        } catch (Exception e) {
            log.error(logPrefix + " url:" + url.toString() + "response:" + response, e);
        }

        return recommendationTpResponse;
    }

    /**
     * 单板块推荐
     * @param cid
     *            推荐类别
     * @param pid
     *            专辑Id
     * @param vid
     *            视频Id
     * @param num
     *            推荐返回数量
     * @param area
     *            页面区域
     * @param cr
     *            视频分级信息
     * @return
     */
    public RecommendationTpResponse getSingleBlock(Integer cid, Integer pid, Integer vid, Integer num, String area,
                                                                                     String history, String cr, CommonParam commonParam) {
        RecommendationTpResponse recommendationTpResponse = null;
        String logPrefix = "getSingleBlock_" + cid + "_" + pid + "_" + vid + "_" + area + "_" + commonParam.getMac();

        try {
            StringBuilder url = new StringBuilder();
            url.append(RecommendationTpConstant.getRecURL(commonParam));
            url.append("pt=").append(RecommendationTpConstant.REQUEST_URL_PARAM_PT);// 播放平台，固定是TV
            url.append("&random=").append(System.currentTimeMillis());// 随机整数，防缓存
            url.append("&versiontype=IntelligentOperation").append("&action=rec").append("&disable_record_exposure=1");
            url.append("&cr=").append(cr);
            if (!StringUtil.isNullOrEmpty(commonParam.getUserId())) {
                url.append("&uid=").append(commonParam.getUserId());
            }
            if (!StringUtil.isNullOrEmpty(commonParam.getMac())) {
                url.append("&lc=").append(commonParam.getMac());
            }
            if (cid != null) {
                url.append("&cid=").append(cid);
            }
            if (pid != null) {
                url.append("&pid=").append(pid);
            }
            if (vid != null) {
                url.append("&vid=").append(vid);
            }
            if (num != null) {
                url.append("&num=").append(num);
            }
            if (!StringUtil.isNullOrEmpty(area)) {
                url.append("&area=").append(area);
            }
            if (commonParam.getWcode() != null) {
                url.append("&region=").append(commonParam.getWcode());
            }

            if (history != null) {
                url.append("&history=").append(history);
            }

            if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() > 0) {
                url.append("&bc=").append(commonParam.getBroadcastId());
            }
            if ((commonParam.getLangcode() != null) && !commonParam.getLangcode().equalsIgnoreCase("zh_cn")
                    && !commonParam.getLangcode().equalsIgnoreCase("zh-cn")) {
                url.append("&lang=").append(commonParam.getLangcode());
            }

            String result = this.restTemplate.getForObject(url.toString(), String.class);

            log.debug(logPrefix + ": invoke return [" + result + "]");

            if (!StringUtil.isNullOrEmpty(result)) {
                // 2015-07-13，当出现%时，decode会出现问题，所以先注释掉
                // result = URLDecoder.decode(result, "UTF-8");
                recommendationTpResponse = objectMapper.readValue(result, RecommendationTpResponse.class);
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }

        return recommendationTpResponse;
    }

    /**
     * 乐视儿童-- 获取标签列表
     * @param pageId
     *            标签配置的ID
     * @param region
     *            地区代表
     * @param bc
     *            播控方ID
     * @param mac
     *            设备mac
     * @param lang
     *            语言
     * @param history
     *            最后十次播放记录
     * @return
     */
    public RecommendationTagTpResponse getChildTags(String pageid, String history, CommonParam commonParam) {
        RecommendationTagTpResponse recomTpResponse = null;
        String logPrefix = "getChildTags_" + pageid + "_" + commonParam.getMac();

        try {
            // http://rec.letv.com/tv?
            StringBuilder url = new StringBuilder(RecommendationTpConstant.getRecURL(commonParam));
            url.append("pt=").append(RecommendationTpConstant.REQUEST_URL_PARAM_PT);
            url.append("&pageid=page_cms").append(pageid);
            url.append("&region=").append(commonParam.getWcode());
            url.append("&lc=").append(commonParam.getMac());
            url.append("&versiontype=IntelligentOperation").append("&action=rec").append("&disable_record_exposure=1");
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            log.debug(logPrefix + ": invoke return [" + result + "]");
            if (!StringUtil.isNullOrEmpty(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                recomTpResponse = objectMapper.readValue(result, RecommendationTagTpResponse.class);
                return recomTpResponse;
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }
        return null;

    }

    /**
     * 乐见桌面
     * @param area
     * @param cid
     * @param num
     * @param commonParam
     * @return
     */
    public RecommendationResponse getLeViewData(String area, Integer num, CommonParam commonParam) {
        String logPrefix = "getLeViewData_" + commonParam.getMac();
        RecommendationResponse recResponse = null;
        GenericServingRequest request = new GenericServingRequest();
        RecommendationRequest recReq = new RecommendationRequest();
        recReq.setLc(commonParam.getMac());
        recReq.setCid("0");
        recReq.setFrom_("tv");
        recReq.setVersion_type("LeJianMovie");
        recReq.setArea(area);
        recReq.setNum(num);
        recReq.setProduct_code(ProductCode.LEJIAN_MOVIE);
        recReq.setUid(commonParam.getUserId());
        recReq.setApp_version(commonParam.getAppVersion());
        recReq.setSales_area(this.getLeviewAreaInfo(commonParam.getSalesArea()));
        recReq.setCountry_info(this.getLeviewAreaInfo(commonParam.getWcode()));
        // 国广桌面特殊参数
        if (TerminalCommonConstant.TERMINAL_APPLICATION_CIBN_LEVIEW.equalsIgnoreCase(commonParam
                .getTerminalApplication())) {
            recReq.setRequest_doc_type(RequestDocType.CIBN);
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_WASU_LEVIEW.equalsIgnoreCase(commonParam
                .getTerminalApplication())) {
            recReq.setRequest_doc_type(RequestDocType.WASU);
        }
        if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() == CommonConstants.CIBN) {
            recReq.setPcp(PlayControlPlatform.GUO_GUANG);
        } else {
            recReq.setPcp(PlayControlPlatform.UNKNOWN);
        }
        request.setRecommendation_request(recReq);
        GenericServingResponse servResponse = null;
        try {
            servResponse = this.recommendationServing.Serve(request);
        } catch (Exception e) {
            log.error(logPrefix + "return error: ", e);
        }
        if (servResponse == null) {
            log.error(logPrefix + " servResponse is null");
        } else {
            recResponse = servResponse.getRecommendation_response();
        }
        return recResponse;
    }

    /**
     * 乐见桌面地域信息
     * @param wcode
     * @return
     */
    private CountryCode getLeviewAreaInfo(String wcode) {
        if (StringUtil.isNullOrEmpty(wcode)) {
            return CountryCode.CN;
        } else {
            if (LocaleConstant.Wcode.CN.equalsIgnoreCase(wcode)) {
                return CountryCode.CN;
            } else if (LocaleConstant.Wcode.US.equalsIgnoreCase(wcode)) {
                return CountryCode.US;
            } else if (LocaleConstant.Wcode.IN.equalsIgnoreCase(wcode)) {
                return CountryCode.IN;
            } else {
                return CountryCode.CN;
            }
        }
    }

    /**
     * 更新推荐容错缓存
     * @param commonParam
     *            通用参数
     */
    public String updateRecCache(String url) {
        String result = null;
        try {
            if (url != null && !"".equals(url)) {
                result = this.restTemplate.getForObject(url, String.class);
            }
            log.info("update Recommend Recovery Cache is ok");
            // String[] recUrls = RecommendationTpConstant.REC_URL;
            // if ("HK".equalsIgnoreCase(commonParam.getWcode())) {
            // recUrls = RecommendationTpConstant.REC_URL_HK;
            // }
            //
            // for (String url : recUrls) {
            // url = domain + url + "&" + "region=" + commonParam.getWcode() +
            // "&lang=" + commonParam.getLangcode();
            // result = this.restTemplate.getForObject(url, String.class);
            // if (result != null && result.length() > 1024) {
            // RedisUtil.set(this.parseCacheKey(url), result);
            // }
            //
            // if ("CN".equalsIgnoreCase(commonParam.getWcode())) {
            // // 为国广版本做缓存
            // url = url + "&bc=2";
            // result = this.restTemplate.getForObject(url, String.class);
            // if (result != null && result.length() > 1024) {
            // RedisUtil.set(this.parseCacheKey(url), result);
            // }
            // }
            // }
        } catch (Exception e) {
            log.error("update Recommend Recovery Cache error.", e);
        }
        return result;
    }

    /**
     * @param cid
     *            推荐类别
     * @param aid
     *            专辑id
     * @param src
     * @param num
     * @param commonParam
     * @return
     */
    public RecommendByVideoResponse recommendVideoById(Integer cid, Long aid, String src, Integer num,
                                                       CommonParam commonParam) {
        RecommendByVideoResponse response = null;
        String url = null;
        try {
            StringBuilder subUrl = new StringBuilder();
            int random = (int) (Math.random() * 100000 + 1);
            subUrl.append(RecommendationTpConstant.getWebRecURL(commonParam.getLangcode()))
                    .append("?pt=0004&ph=-121,420007&area=rec_0401&p1=2&p2=64").append("&cid=").append(cid + "")
                    .append("&aid=").append(aid).append("&src=").append(src).append("&num=").append(num).append("&lc=")
                    .append(commonParam.getMac()).append("&random=").append(random).append("&region=")
                    .append(commonParam.getWcode()).append("&versiontype=IntelligentOperation").append("&action=rec")
                    .append("&disable_record_exposure=1");
            if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() > 0) {
                subUrl.append("&bc=").append(commonParam.getBroadcastId());
            }
            url = subUrl.toString();
            String result = this.restTemplate.getForObject(url, String.class);
            if (!StringUtil.isNullOrEmpty(result)) {
                response = objectMapper.readValue(result, RecommendByVideoResponse.class);
            }
        } catch (Exception e) {
            log.error("recommendVideoById error for URL[" + url + "]", e);
        }
        return response;
    }

    /**
     * 局推荐接口，底层调用第三方推荐
     * 返回推荐部门的统一格式，上层调用需要根据各自需求进行解析
     * @param uid
     *            用户id
     * @param cid
     *            推荐类别
     * @param type
     *            限定推荐的视频类型
     * @param pid
     *            专辑id
     * @param vid
     *            视频id
     * @param pt
     *            播放平台
     * @param pageid
     *            推荐页面id
     * @param area
     *            页面区域
     * @param jsonp
     *            回调函数名称
     * @param feedback
     *            用户反馈记录
     * @param playtime
     *            观看时长
     * @param totaltime
     *            视频总时长
     * @param history
     *            用户观看历史
     * @param num
     *            返回结果条数
     * @param citylevel
     *            城市级别标识
     * @param city
     *            城市标识
     * @param mpt
     *            移动端客户端平台标识
     * @param is_rec
     *            视频是否为推荐数据
     * @param ph
     *            版权信息
     * @param commonParam
     *            通用参数
     * @return
     */

    public RecBaseResponse recommend(Integer cid, String type, Long pid, Long vid, String pageid, String area,
                                     String jsonp, Integer feedback, Long playtime, Long totaltime, String history, Integer num,
                                     String citylevel, String city, String mpt, Boolean is_rec, String ph, CommonParam commonParam) {
        RecBaseResponse response = null;
        StringBuilder url = null;
        try {
            int random = (int) (Math.random() * 100000);
            url = new StringBuilder(RecommendationTpConstant.getRecURL(commonParam));
            url.append("pt=").append(RecommendationTpConstant.REQUEST_URL_PARAM_PT)
                    .append("&versiontype=IntelligentOperation").append("&action=rec")
                    .append("&disable_record_exposure=1");
            if (ph == null || "".equals(ph)) {
                ph = "420007";
                url.append("&ph=").append(ph);
            } else {
                url.append("&ph=").append(ph);
            }
            if (num == null || num == 0) {
                num = 20;
                url.append("&num=").append(num);
            } else {
                url.append("&num=").append(num);
            }
            if (commonParam.getUserId() != null && !"".equals(commonParam.getUserId())) {
                url.append("&uid=").append(commonParam.getUserId());
            }
            if (commonParam.getMac() != null && !"".equals(commonParam.getMac())) {
                url.append("&lc=").append(commonParam.getMac());
            }
            if (cid != null) {
                url.append("&cid=").append(cid);
            }
            if (type != null && !"".equals(type)) {
                url.append("&type=").append(type);
            }
            if (pid != null) {
                url.append("&pid=").append(pid);
            }
            if (vid != null) {
                url.append("&vid=").append(vid);
            }
            if (pageid != null && !"".equals(pageid)) {
                url.append("&pageid=").append(pageid);
            }
            if (area != null && !"".equals(area)) {
                url.append("&area=").append(area);
            }
            if (jsonp != null && !"".equals(jsonp)) {
                url.append("&jsonp=").append(jsonp);
            }
            if (feedback != null) {
                url.append("&feedback=").append(feedback);
            }
            if (playtime != null) {
                url.append("&playtime=").append(playtime);
            }
            if (totaltime != null) {
                url.append("&totaltime=").append(totaltime);
            }
            if (history != null && !"".equals(history)) {
                url.append("&history=").append(history);
            }
            if (citylevel != null && !"".equals(citylevel)) {
                url.append("&citylevel=").append(citylevel);
            }
            if (city != null && !"".equals(city)) {
                url.append("&city=").append(city);
            }
            if (commonParam.getBroadcastId() != null && !"".equals(commonParam.getBroadcastId())) {
                url.append("&bc=").append(commonParam.getBroadcastId());
            }
            if (is_rec != null && !"".equals(is_rec)) {
                url.append("&is_rec=").append(is_rec);
            }
            if (commonParam.getLangcode() != null && !"".equals(commonParam.getLangcode())) {
                url.append("&lang=").append(commonParam.getLangcode());
            }
            if (commonParam.getWcode() != null && !"".equals(commonParam.getWcode())) {
                url.append("&region=").append(commonParam.getWcode());
            }
            url.append("&random=").append(random);
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (!StringUtil.isNullOrEmpty(result)) {
                response = objectMapper.readValue(result, RecBaseResponse.class);
            }
        } catch (Exception e) {
            log.error("recommend error for URL [" + url + "]", e);
        }
        return response;
    }

    /**
     * 游戏中心获取所有专辑id列表
     * http://gc.letvstore.com/api/outer/getpids
     * @return
     */
    public RecommendaionPidListResponse getRecommendationPidList() {
        RecommendaionPidListResponse response = null;
        String logPrefix = "getRecommendationPidList_";
        try {
            StringBuilder url = new StringBuilder(ApplicationUtils.get(ApplicationConstants.PLAY_GAME_PID_LIST));
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (!StringUtil.isNullOrEmpty(result)) {
                response = objectMapper.readValue(result, RecommendaionPidListResponse.class);
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    /**
     * 游戏中心接口，底层调用第三方
     * http://gc.letvstore.com/api/outer/findappbypid/{pid}?
     * @return
     */
    public RecommendaionByPidResponse getRecommendationByPid(String pid) {
        String logPrefix = "getRecommendationByPid_" + pid;
        RecommendaionByPidResponse response = null;
        try {
            if (pid != null) {
                StringBuilder url = new StringBuilder(ApplicationUtils.get(ApplicationConstants.GET_PLAY_GAME_BY_PID));
                url.append(pid);
                String result = this.restTemplate.getForObject(url.toString(), String.class);
                if (!StringUtil.isNullOrEmpty(result)) {
                    result = URLDecoder.decode(result, "UTF-8");
                    log.debug(logPrefix + ": invoke return [" + result + "]");
                    response = objectMapper.readValue(result, RecommendaionByPidResponse.class);
                }
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error: ", e);
        }
        return response;
    }

    /**
     * 获取cp分类下的数据
     * @param cpId
     *            cpId
     * @param cid
     *            cp下的某一类别id
     * @param uid
     *            用户id
     * @param lc
     *            用户登录标识 mac
     * @param version
     *            推荐版本信息
     * @param num
     *            请求的数据量
     * @param area
     *            推荐area
     * @return
     */
    public RecommendationLevidiResponse getLevidiRecommendation(String cpId, String cid, String videoId, Integer num,
            String area, Integer startIdex, String bizType, String subscribeIds, CommonParam commonParam) {
        String logPrefix = "getSarrsRecommendation_cid:" + cid + "_uid:" + commonParam.getUserId() + "_lc:"
                + commonParam.getMac();
        RecommendationLevidiResponse sarrsResponse = null;
        StringBuilder sb = new StringBuilder(RecommendationTpConstant.getLevidiRecURL());
        sb.append("action=").append("rec").append("&area=").append(area).append("&versiontype=")
                .append("InternationalVideo").append("&platform=").append(1).append("&from=tv")
                .append("&version=1.1.0");
        if (!StringUtil.isNullOrEmpty(cpId)) {
            sb.append("&id=").append(cpId);
        }
        if (!StringUtil.isNullOrEmpty(videoId)) {
            sb.append("&id=").append(videoId);
        }
        if (!StringUtil.isNullOrEmpty(cid)) {
            sb.append("&cid=").append(cid);
        }
        if (!StringUtil.isNullOrEmpty(commonParam.getUserId())) {
            sb.append("&uid=").append(commonParam.getUserId());
        }
        if (!StringUtil.isNullOrEmpty(commonParam.getMac())) {
            sb.append("&lc=").append(commonParam.getMac());
        }
        if (num != null) {
            sb.append("&num=").append(num);
        }
        if (startIdex != null) {
            sb.append("&start_index=").append(startIdex);
        }
        if (!StringUtil.isNullOrEmpty(bizType)) {
            if ("1".equals(bizType)) {// 清曝光
                sb.append("&clear_exposure=").append(bizType);
            }
        }
        if (!StringUtil.isNullOrEmpty(subscribeIds)) {
            sb.append("&subscription=").append(subscribeIds);
        }
        if (!StringUtil.isNullOrEmpty(commonParam.getWcode())) {
            sb.append("&region=").append(commonParam.getWcode().toUpperCase());
        }
        if (!StringUtil.isNullOrEmpty(commonParam.getSalesArea())) {
            sb.append("&sales_area=").append(commonParam.getSalesArea().toUpperCase());
        }
        if (commonParam != null && LocaleConstant.Wcode.US.equals(commonParam.getWcode())) {
            sb.append("&user_setting_country=").append(commonParam.getWcode().toUpperCase());
        }
        String result = null;
        try {
            result = this.restTemplate.getForObject(sb.toString(), String.class);
            if (!StringUtil.isNullOrEmpty(result)) {
                sarrsResponse = objectMapper.readValue(result, RecommendationLevidiResponse.class);
            }
        } catch (JsonParseException jsonException) {
            log.error(logPrefix + "_" + result, jsonException);
        } catch (Exception e) {
            log.error(logPrefix, e);
        }
        return sarrsResponse;
    }

    /**
     * 获取cp分类(印度)
     * @param cpId
     *            cpId
     * @param commonParam
     *            通用参数
     * @return
     */
    public RecommendationInLevidiCategoryResponse getSarrsCategoryRecommendation(String cpId, CommonParam commonParam) {
        String logPrefix = "getSarrsCategoryRecommendation_cpId:" + cpId + "_lc:" + commonParam.getMac();
        RecommendationInLevidiCategoryResponse sarrsCategoryResponse = null;

        try {
            StringBuilder sb = new StringBuilder(RecommendationTpConstant.getLevidiRecURL());
            sb.append("action=").append("getcpcategory");
            if (!StringUtil.isNullOrEmpty(commonParam.getMac())) {
                sb.append("&lc=").append(commonParam.getMac());
            }
            if (!StringUtil.isNullOrEmpty(cpId)) {
                sb.append("&id=").append(cpId);
            }
            if (!StringUtil.isNullOrEmpty(commonParam.getWcode())) {
                sb.append("&user_setting_country=").append(commonParam.getWcode().toUpperCase());
            }
            if (!StringUtil.isNullOrEmpty(commonParam.getSalesArea())) {
                sb.append("&sales_area=").append(commonParam.getSalesArea().toUpperCase());
            }
            if (!StringUtil.isNullOrEmpty(commonParam.getWcode())) {
                sb.append("&region=").append(commonParam.getWcode().toUpperCase());
            }
            String result = this.restTemplate.getForObject(sb.toString(), String.class);
            if (!StringUtil.isNullOrEmpty(result)) {
                sarrsCategoryResponse = objectMapper.readValue(result, RecommendationInLevidiCategoryResponse.class);
            }
        } catch (Exception e) {
            log.error(logPrefix, e);
        }
        return sarrsCategoryResponse;
    }

    /**
     * 获取cp分类(美国)
     * @param cpId
     *            cpId
     * @param commonParam
     *            通用参数
     * @return
     */
    public RecommendationUsLevidiCategoryResponse getUsLevidiCategoryRecommendation(CommonParam commonParam) {
        String logPrefix = "getUsLevidiCategoryRecommendation:_lc:" + commonParam.getMac();
        RecommendationUsLevidiCategoryResponse usCategoryResponse = null;

        try {
            StringBuilder sb = new StringBuilder(RecommendationTpConstant.getLevidiRecURL());
            sb.append("action=").append("getexplore");
            if (!StringUtil.isNullOrEmpty(commonParam.getMac())) {
                sb.append("&lc=").append(commonParam.getMac());
            } else {
                sb.append("&lc=oversea");
            }
            if (!StringUtil.isNullOrEmpty(commonParam.getWcode())) {
                sb.append("&user_setting_country=").append(commonParam.getWcode().toUpperCase());
            }
            if (!StringUtil.isNullOrEmpty(commonParam.getSalesArea())) {
                sb.append("&sales_area=").append(commonParam.getSalesArea().toUpperCase());
            }
            if (!StringUtil.isNullOrEmpty(commonParam.getWcode())) {
                sb.append("&region=").append(commonParam.getWcode().toUpperCase());
            }
            String result = this.restTemplate.getForObject(sb.toString(), String.class);
            if (!StringUtil.isNullOrEmpty(result)) {
                usCategoryResponse = objectMapper.readValue(result, RecommendationUsLevidiCategoryResponse.class);
            }
        } catch (Exception e) {
            log.error(logPrefix, e);
        }
        return usCategoryResponse;
    }

    public RecommendationUsLevidiPublisherResponse getUsLevidiPublisherRecommendation(CommonParam commonParam) {
        String logPrefix = "getUsLevidiCategoryRecommendation:_lc:" + commonParam.getMac();
        RecommendationUsLevidiPublisherResponse usPublisherResponse = null;

        try {
            StringBuilder sb = new StringBuilder(RecommendationTpConstant.getLevidiRecURL());
            sb.append("action=").append("rec").append("&area=").append("publisher").append("&versiontype=")
                    .append("InternationalVideo").append("&from=").append("tv");
            if (!StringUtil.isNullOrEmpty(commonParam.getMac())) {
                sb.append("&lc=").append(commonParam.getMac());
            }
            if (!StringUtil.isNullOrEmpty(commonParam.getSalesArea())) {
                sb.append("&sales_area=").append(commonParam.getSalesArea().toUpperCase());
            }
            if (!StringUtil.isNullOrEmpty(commonParam.getWcode())) {
                sb.append("&region=").append(commonParam.getWcode().toUpperCase());
            }
            String result = this.restTemplate.getForObject(sb.toString(), String.class);
            if (!StringUtil.isNullOrEmpty(result)) {
                usPublisherResponse = objectMapper.readValue(result, RecommendationUsLevidiPublisherResponse.class);
            }
        } catch (Exception e) {
            log.error(logPrefix, e);
        }
        return usPublisherResponse;
    }

    /**
     * 儿童搜索个性化推荐
     * @param pageid
     * @param history
     * @param commonParam
     * @return
     */
    public Map<String, RecBaseResponse> getRecommend(int num, String history, CommonParam commonParam) {
        String logPrefix = "getRecommend_page_cms" + RecommendationTpConstant.REC_MAYBELIKE_CHILD_PAGEID
                + commonParam.getMac();
        Map<String, RecBaseResponse> recomTpResponse = null;
        String result = null;
        StringBuilder url = null;
        try {
            url = new StringBuilder(RecommendationTpConstant.getRecURL(commonParam));
            url.append("pt=").append(RecommendationTpConstant.REQUEST_URL_PARAM_PT)
                    .append("&versiontype=IntelligentOperation").append("&action=rec")
                    .append("&disable_record_exposure=1");
            if (!StringUtil.isNullOrEmpty(commonParam.getUserId())) {
                url.append("&uid=").append(commonParam.getUserId());
            }
            if (!StringUtil.isNullOrEmpty(commonParam.getMac())) {
                url.append("&lc=").append(commonParam.getMac());
            }
            url.append("&pageid=").append(RecommendationTpConstant.REC_MAYBELIKE_CHILD_PAGEID);
            url.append("&num=").append(num);
            if (!StringUtil.isNullOrEmpty(commonParam.getWcode())) {
                url.append("&region=").append(commonParam.getWcode());
            }
            if (commonParam.getBroadcastId() != null && !"".equals(commonParam.getBroadcastId())) {
                url.append("&bc=").append(commonParam.getBroadcastId());
            }
            result = this.restTemplate.getForObject(url.toString(), String.class);

            if (!StringUtil.isNullOrEmpty(result)) {
                result = URLDecoder.decode(result, "UTF-8");
                recomTpResponse = objectMapper.readValue(result, new TypeReference<Map<String, RecBaseResponse>>() {
                });
            }
        } catch (Exception e) {
            log.error(logPrefix + " return error url:[" + url + "] result:[" + result + "]" + e.getMessage(), e);
        }
        return recomTpResponse;
    }
}
