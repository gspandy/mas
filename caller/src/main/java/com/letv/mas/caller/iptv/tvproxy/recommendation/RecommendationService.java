package com.letv.mas.caller.iptv.tvproxy.recommendation;

import com.letv.mas.caller.iptv.tvproxy.channel.model.dto.Channel;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response.RecBaseResponse.RecommendDetail;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response.RecommendationUsLevidiPublisherResponse.UsLevidiPublisher;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.BurrowUtil;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.*;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.ErrorCodeCommonUtil;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseBlock;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageResponse;
import com.letv.mas.caller.iptv.tvproxy.channel.model.dto.ChannelData;
import com.letv.mas.caller.iptv.tvproxy.channel.service.ChannelService;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.constant.*;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ErrorCodeConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.UserChildLockTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockContentTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.RecommendationTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.RecommendationTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.RecommendationTpResponseRec;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.ChargeInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.BaseResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import com.letv.mas.caller.iptv.tvproxy.live.model.bean.bo.Live;
import com.letv.mas.caller.iptv.tvproxy.recommendation.constants.RecommendationConstants;
import com.letv.mas.caller.iptv.tvproxy.recommendation.model.bean.bo.Recommendation;
import com.letv.mas.caller.iptv.tvproxy.recommendation.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.user.UserService;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.video.model.bean.bo.PlayCenterInfo;
import com.letv.mas.caller.iptv.tvproxy.video.model.bean.bo.ResourceInfo;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.AlbumDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoDto;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import rec.recommend.RecommendationResponse;
import serving.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component(value = "v2.RecommendationService")
public class RecommendationService extends BaseService {
    /**
     * [电影、电视剧、动漫、纪录片、综艺]的categoryId数组
     */
    public static int[] categoryList = new int[] { VideoConstants.Category.FILM, VideoConstants.Category.TV,
            VideoConstants.Category.CARTOON, VideoConstants.Category.VARIETY, VideoConstants.Category.DFILM };

    private final static Logger log = LoggerFactory.getLogger(RecommendationService.class);

    @Autowired
    ChannelService channelService;

    @Autowired
    UserService userService;
    
    /**
     * 多板块推荐
     * @param rcPageid
     *            推荐页面id
     * @param commonParam
     *            通用参数
     * @return
     */

    public PageResponse<Recommendation> getMultiBlocks(String rcPageid, Integer model, String rcHistory,
                                                       Integer channelId, String defaultStream, String defaultStreamName, CommonParam commonParam) {
        String logPrefix = "getMultiBlocks_" + rcPageid + "_" + commonParam.getUserId() + "_" + commonParam.getMac()
                + "_";
        TreeSet<Recommendation> recommendationList = null;

        if (model != null && model == 1) {
            rcPageid = "page_cms" + rcPageid;
        }
        // 推荐板块
        Map<String, RecommendationTpResponse> recommendationTpResponses = this.facadeTpDao.getRecommendationTpDao()
                .getMultiBlocks(null, null, null, null, rcPageid, null, rcHistory, null, commonParam);
        if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() > 0) {
            commonParam.setBroadcastId(2);
        } else {
            commonParam.setBroadcastId(null);
        }
        // 缓存只区分无播控方或者国广。缓存中直接放入 recommendationTpResponses
        // 会导致对象属性list中的对象数据丢失，故采用下面的方式
        if (recommendationTpResponses == null) {// 从缓存中读取数据
            try {
                recommendationTpResponses = this.facadeCacheDao.getRecommendationCacheDao().getMultiBlockCache(
                        rcPageid, null, null, logPrefix, commonParam);
                log.info(logPrefix + "get rec data from cache ok");
            } catch (Exception e) {
                log.error(logPrefix + "get rec data from cache error", e);
                recommendationTpResponses = new HashMap<String, RecommendationTpResponse>();
            }
        } else {
            try {
                this.facadeCacheDao.getRecommendationCacheDao().setMultiBlockCache(rcPageid, null, null,
                        recommendationTpResponses, logPrefix, commonParam);
            } catch (Exception e) {
                log.error(logPrefix + "set rec data to cache error:", e);
            }
        }
        recommendationList = this.parseRecommendationTpResponse(channelId, model, defaultStream, defaultStreamName,
                logPrefix, recommendationTpResponses, commonParam);
        return new PageResponse<Recommendation>(recommendationList);
    }

    public PageResponse<Recommendation> getMultiBlocks(String rcPageid, Integer model, String rcHistory,
            Integer channelId, String defaultStream, String defaultStreamName, int getDataModel, CommonParam commonParam) {
        String logPrefix = "getMultiBlocks_" + rcPageid + "_" + commonParam.getUserId() + "_" + commonParam.getMac()
                + "_";
        TreeSet<Recommendation> recommendationList = null;

        if (model != null && model == 1) {
            rcPageid = "page_cms" + rcPageid;
        }

        if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() > 0) {
            commonParam.setBroadcastId(2);
        } else {
            commonParam.setBroadcastId(null);
        }

        Map<String, RecommendationTpResponse> recommendationTpResponses = null;

        // 推荐板块
        switch (getDataModel) {
        case DataConstant.GET_DATA_CATEGARY_TP_CACHE:
            recommendationTpResponses = this.facadeTpDao.getRecommendationTpDao().getMultiBlocks(null, null, null,
                    null, rcPageid, null, rcHistory, null, commonParam);
            // 缓存只区分无播控方或者国广。缓存中直接放入 recommendationTpResponses
            // 会导致对象属性list中的对象数据丢失，故采用下面的方式
            if (recommendationTpResponses == null) {// 从缓存中读取数据
                try {
                    recommendationTpResponses = this.facadeCacheDao.getRecommendationCacheDao().getMultiBlockCache(
                            rcPageid, null, null, logPrefix, commonParam);
                    log.info(logPrefix + "get rec data from cache ok");
                } catch (Exception e) {
                    log.error(logPrefix + "get rec data from cache error", e);
                    recommendationTpResponses = new HashMap<String, RecommendationTpResponse>();
                }
            } else {
                try {
                    this.facadeCacheDao.getRecommendationCacheDao().setMultiBlockCache(rcPageid, null, null,
                            recommendationTpResponses, logPrefix, commonParam);
                    log.info(logPrefix + "set rec data to cache ok:");
                } catch (Exception e) {
                    log.error(logPrefix + "set rec data to cache error:", e);
                }
            }
            break;
        case DataConstant.GET_DATA_CATEGARY_CACHE_TP:
            try {
                recommendationTpResponses = this.facadeCacheDao.getRecommendationCacheDao().getMultiBlockCache(
                        rcPageid, null, null, logPrefix, commonParam);

                log.info(logPrefix + "get rec data from cache ok");
                if (recommendationTpResponses == null) {
                    recommendationTpResponses = this.facadeTpDao.getRecommendationTpDao().getMultiBlocks(null, null,
                            null, null, rcPageid, null, rcHistory, null, commonParam);
                    if (recommendationTpResponses != null) {
                        try {
                            this.facadeCacheDao.getRecommendationCacheDao().setMultiBlockCache(rcPageid, null, null,
                                    recommendationTpResponses, logPrefix, commonParam);
                            log.info(logPrefix + "set rec data to cache ok:");
                        } catch (Exception e) {
                            log.error(logPrefix + "set rec data to cache error:", e);
                        }
                    }
                }
            } catch (Exception e) {
                log.error(logPrefix + "get rec data from cache error", e);
                recommendationTpResponses = new HashMap<String, RecommendationTpResponse>();
            }
            break;
        default:
            break;
        }

        recommendationList = this.parseRecommendationTpResponse(channelId, model, defaultStream, defaultStreamName,
                logPrefix, recommendationTpResponses, commonParam);

        return new PageResponse<Recommendation>(recommendationList);

    }

    private TreeSet<Recommendation> parseRecommendationTpResponse(Integer channelId, Integer model,
            String defaultStream, String defaultStreamName, String logPrefix,
            Map<String, RecommendationTpResponse> recommendationTpResponses, CommonParam commonParam) {
        TreeSet<Recommendation> recommendationList = new TreeSet<Recommendation>();
        if (recommendationTpResponses != null) {
            for (String key : recommendationTpResponses.keySet()) {
                Recommendation recommendation = null;

                // 推荐数据
                RecommendationTpResponse recommendationTpResponse = recommendationTpResponses.get(key);
                List<RecommendationTpResponseRec> recommendationTpResponseRecList = recommendationTpResponse.getRec();
                if (recommendationTpResponseRecList != null && recommendationTpResponseRecList.size() > 0) {
                    List<BaseData> dataList = new LinkedList<BaseData>();
                    for (int i = 0; i < recommendationTpResponse.getCms_num(); i++) {// CMS
                        if (i >= recommendationTpResponseRecList.size()) {
                            break;
                        }
                        RecommendationTpResponseRec recommendationTpResponseRec = recommendationTpResponseRecList
                                .get(i);
                        // 不加这段逻辑，色块会出错
                        if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                                .getTerminalApplication())) {
                            recommendationTpResponse.setBlock_id(recommendationTpResponseRec.getBid());
                        }
                        BaseData data = channelService.getDataFromCms(
                                recommendationTpResponseRec, commonParam, null, defaultStream, defaultStreamName);
                        if (data != null) {
                            dataList.add(data);
                        }
                    }
                    for (int i = recommendationTpResponse.getCms_num(); i < recommendationTpResponse.getNum(); i++) {// 推荐
                        if (i >= recommendationTpResponseRecList.size()) {
                            break;
                        }
                        RecommendationTpResponseRec recommendationTpResponseRec = recommendationTpResponseRecList
                                .get(i);

                        BaseData data = this.getDataFromRecommendation(recommendationTpResponseRec,
                                recommendationTpResponseRec.getIsalbum(), null, null, channelId, defaultStream,
                                defaultStreamName, logPrefix, commonParam);
                        if (data != null) {
                            dataList.add(data);
                        }
                    }

                    if (dataList.size() > 0) {
                        recommendation = new Recommendation();
                        recommendation.setOrderNum(key);
                        recommendation.setDataList(dataList);

                        if (model != null && model == 1) { // 儿童推荐的特殊处理
                            int size = dataList.size();
                            if (size <= 8) {
                                recommendation.setDataList(dataList);
                            } else if (8 < size && size < 16) {
                                recommendation.setDataList(dataList.subList(0, 8));
                            } else if (16 <= size && size < 24) {
                                recommendation.setDataList(dataList.subList(0, 16));
                            } else {
                                recommendation.setDataList(dataList.subList(0, 24));
                            }
                        }
                        if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equals(commonParam.getTerminalApplication())) {
                            if (recommendationTpResponse.getCms_num() > 1
                                    && dataList.size() >= recommendationTpResponse.getCms_num()) {
                                BaseData data_func = dataList.get(recommendationTpResponse.getCms_num() - 1);
                                if (data_func != null) {
                                    Integer type = data_func.getJump().getType();
                                    if (type != null && type == DataConstant.DATA_TYPE_CHANNEL) {
                                        dataList.remove(recommendationTpResponse.getCms_num() - 1);
                                        dataList.add(data_func);
                                    }
                                }
                            }
                        }
                        recommendation.setBlockName(recommendationTpResponse.getBlockname());
                        recommendation.setBlockType(recommendationTpResponse.getType());
                        recommendation.setBlockId(recommendationTpResponse.getBlock_id());
                        recommendation.setCategoryId(recommendationTpResponse.getCid());
                        recommendation.setArea(recommendationTpResponse.getArea());
                        recommendation.setBucket(recommendationTpResponse.getBucket());
                        recommendation.setReid(recommendationTpResponse.getReid());
                        recommendation.setBlockId(recommendationTpResponse.getBlock_id());
                        recommendation.setFragId(recommendationTpResponse.getFragId());
                    } else {
                        log.warn(logPrefix + ": block[" + recommendationTpResponse.getBlockname() + "] empty data");
                    }
                }

                if (recommendation != null) {
                    recommendationList.add(recommendation);
                }
            }
        }
        return recommendationList;
    }

    /**
     * 单板块推荐
     * @param categoryId
     *            推荐类别
     * @param albumId
     *            专辑Id
     * @param videoId
     *            视频Id
     * @param number
     *            请求结果条数
     * @param area
     *            页面区域
     * @param commonParam
     *            通用参数
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public PageResponse<BaseData> getSingleBlock(Integer categoryId, Integer albumId, Integer videoId, String globalId,
            Integer number, String area, String pageId, String history, String rcType, CommonParam commonParam) {
        PageResponse response = null;
        StringBuilder logPrefix = new StringBuilder("getSingleBlock_").append(categoryId).append("_").append(albumId)
                .append("_").append(videoId).append("_").append(area).append("_").append(commonParam.getMac());
        if (CommonConstants.TERMINAL_APPLICATION_LEVIDI.equals(commonParam.getTerminalApplication())) {
            return this.getLevidiRecommendation(null, null, globalId, "rec_0904", 4, null, null, null, commonParam);

        }
        // le视频分级信息
        String cr = "";
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equals(commonParam.getTerminalApplication())) {
            // 获取儿童锁分级信息
            UserChildLockTable childLockTable = userService.getUserChildLockFromCache(
                    commonParam);
            if (childLockTable != null) {
                cr = childLockTable.getCanPlayCRIds();
                cr = cr.replaceAll(",", "-");
            }
        }
        if (RecommendationConstants.REC_TYPE_PLAY_END.equals(rcType)) {
            area = RecommendationTpConstant.RECOMMENDATION_AREA_PLAY_END;// 播放结束的推荐参数
        } else if (RecommendationConstants.REC_TYPE_ALBUM_NO_TVCOPYRIGHT.equals(rcType)) {
            area = RecommendationTpConstant.RECOMMENDATION_AREA_PLAY_END;// 专辑无版权时进入详情页的推荐参数
        }

        // 乐见处理逻辑
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIEW.equals(commonParam.getTerminalApplication())) {
            if (RecommendationConstants.LEVIEW_DATA_SOURCE_REC.equals(rcType)) {
                response = new PageResponse<Recommendation>();
                area = "rec_0603";
                List<Recommendation> rec_data = this.getLeviewDataFromRec(area, number, logPrefix.toString()
                        + "_leview_", commonParam);

                // 乐见缓存逻辑
                if (rec_data == null || rec_data.size() == 0) {// 走缓存
                    rec_data = (List) this.facadeCacheDao.getRecommendationCacheDao().getLeviewCache(rcType,
                            commonParam);
                } else {
                    if (rec_data != null && rec_data.size() > 0) { // 客户端一次请求30条但是推荐返回可能小于30，故做此判断
                        List<BaseData> data_list = rec_data.get(0).getDataList();
                        if (data_list != null && data_list.size() >= 20) {
                            this.facadeCacheDao.getRecommendationCacheDao().setLeviewCache(rcType, rec_data,
                                    commonParam);
                        }
                    }
                }
                response.setData(rec_data);
            } else {
                response = new PageResponse<Recommendation>();
                response.setData(this.getLeviewDataFromCms(commonParam, logPrefix.toString() + "_leview_"));
            }
            return response;
        }
        response = new PageResponse<BaseData>();
        boolean pickAlbum = false;// 优选专辑标志位
        int dataSize = number;// 如果优选专辑，此数值取number * 2
        if (RecommendationTpConstant.RECOMMENDATION_AREA_RELATED_REC.equals(area) && this.isInCategoryType(categoryId)) {// 退出播放推荐
            // 当area的值表示是相关推荐且分类ID在[电影、电视剧、动漫、纪录片、综艺]中
            pickAlbum = true;
            dataSize *= 2;
        }
        RecommendationTpResponse recommendationTpResponse = this.facadeTpDao.getRecommendationTpDao().getSingleBlock(
                categoryId, albumId, videoId, dataSize, area, history, cr, commonParam);

        List<BaseData> dataList = new LinkedList<BaseData>();
        this.getRecommendData(recommendationTpResponse, dataList, pickAlbum, number, albumId, area, rcType, categoryId,
                logPrefix.toString(), commonParam);
        if (RecommendationTpConstant.RECOMMENDATION_AREA_RELATED_REC.equals(area) && CollectionUtils.isEmpty(dataList)) {// 退出播放推荐
            // 当退出播放时调用第三方接口失败，需要读取缓存中的对应分类id的数据
            dataList = (List<BaseData>) this.facadeCacheDao.getRecommendationCacheDao().getSingleCache(categoryId,
                    Object.class, logPrefix.toString(), commonParam);
            log.info(logPrefix + " get recommend data failure. Get data from cache.");
        }

        response.setData(dataList);
        return response;
    }

    /**
     * 根据标签等参数获取推荐儿童专辑
     * @param pageId
     *            代表标签的id
     * @param history
     *            用户最近观看的10个视频vid历史，例如：vid2-vid3-vid4-vid5-vid6
     * @param commonParam
     *            通用参数
     * @return
     */
    public Response<Recommendation> getRecommendationChildList(String pageId, String history, CommonParam commonParam) {
        String logPrefix = "getRecommendationChildList_" + pageId;

        RecommendationTpResponse recommendationTpResponse = null;
        pageId = "page_cms" + pageId;
        Map<String, RecommendationTpResponse> map = this.facadeTpDao.getRecommendationTpDao().getMultiBlocks(null,
                null, null, null, pageId, null, history, null, commonParam);
        // 确定只有一个
        if (map != null) {
            for (String key : map.keySet()) {
                recommendationTpResponse = map.get(key);
                break;
            }
        }

        if (recommendationTpResponse == null || CollectionUtils.isEmpty(recommendationTpResponse.getRec())) {
            log.info(logPrefix + " get child data failure. ");
            Locale locale = ErrorCodeCommonUtil.getLocale(commonParam.getLangcode(), commonParam.getWcode());
            Response<Recommendation> response = new Response<Recommendation>();
            response.setResultStatus(1);
            response.setErrCode(RecommendationConstants.RECOMMENDATION_ERROR_RESULT_NULL);
            response.setErrMsg(MessageUtils
                    .getMessage(RecommendationConstants.RECOMMENDATION_ERROR_RESULT_NULL, locale));
            return response;
        }

        Recommendation recommendation = new Recommendation();
        List<BaseData> dataList = new LinkedList<BaseData>();
        // 推荐数据
        List<RecommendationTpResponseRec> recommendationTpResponseRecList = recommendationTpResponse.getRec();
        for (int i = 0; i < recommendationTpResponse.getCms_num(); i++) {// CMS
            if (i >= recommendationTpResponseRecList.size()) {
                break;
            }
            RecommendationTpResponseRec recommendationTpResponseRec = recommendationTpResponseRecList.get(i);
            BaseData data = channelService.getDataFromCms(recommendationTpResponseRec,
                    commonParam, null, null, null);
            if (data != null) {
                dataList.add(data);
            }
        }
        for (int i = recommendationTpResponse.getCms_num(); i < recommendationTpResponse.getNum(); i++) {// 推荐
            if (i >= recommendationTpResponseRecList.size()) {
                break;
            }
            RecommendationTpResponseRec recommendationTpResponseRec = recommendationTpResponseRecList.get(i);

            BaseData data = this.getDataFromRecommendation(recommendationTpResponseRec,
                    recommendationTpResponseRec.getIsalbum(), null, null, null, null, null, logPrefix, commonParam);
            if (data != null) {
                dataList.add(data);
            }
        }
        int size = dataList.size();
        if (size <= 8) {
            recommendation.setDataList(dataList);
        } else if (8 < size && size < 16) {
            recommendation.setDataList(dataList.subList(0, 8));
        } else if (16 <= size && size < 24) {
            recommendation.setDataList(dataList.subList(0, 16));
        } else {
            recommendation.setDataList(dataList.subList(0, 24));
        }
        recommendation.setBlockName(recommendationTpResponse.getBlockname());
        recommendation.setBlockType(recommendationTpResponse.getType());
        recommendation.setBlockId(recommendationTpResponse.getBlock_id());
        recommendation.setCategoryId(recommendationTpResponse.getCid());
        recommendation.setArea(recommendationTpResponse.getArea());
        recommendation.setBucket(recommendationTpResponse.getBucket());
        recommendation.setReid(recommendationTpResponse.getReid());

        return new Response<Recommendation>(recommendation);
    }

    /**
     * 根据年龄去取儿童标签
     * @param age
     *            年龄
     * @return
     */
    public PageResponse<RecommendationChildTagDTO> getChildTags(Integer age, CommonParam commonParam) {
        String logPrefix = "getChildTags_" + age + commonParam.getMac();
        PageResponse<RecommendationChildTagDTO> response = new PageResponse<RecommendationChildTagDTO>();
        // 获取标签时只有age参数，根据age去取 默认标签的pageId
        String pageId = RecommendationConstants.getPageIdByAge(age);
        RecommendationTagTpResponse result = this.facadeTpDao.getRecommendationTpDao().getChildTags(pageId, null,
                commonParam);
        if (result == null || result.getRec_1() == null || CollectionUtils.isEmpty(result.getRec_1().getRec())) {
            log.info(logPrefix + " get child tag failure. ");
            Locale locale = new Locale(commonParam.getLangcode(), commonParam.getWcode());
            response.setResultStatus(1);
            response.setErrCode(RecommendationConstants.RECOMMENDATION_ERROR_RESULT_NULL);
            response.setErrMsg(MessageUtils
                    .getMessage(RecommendationConstants.RECOMMENDATION_ERROR_RESULT_NULL, locale));
            return response;
        }
        RecommendationChildTagDTO tagDTO = null;
        List<RecommendationChildTagDTO> tagList = new LinkedList<RecommendationChildTagDTO>();
        for (RecommendationNavigationTpResponseRec rec : result.getRec_1().getRec()) {
            tagDTO = new RecommendationChildTagDTO(rec);
            tagList.add(tagDTO);
        }
        response.setData(tagList);

        return response;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public PageResponse<BaseData> getLevidiRecommendation(String cpId, String cid, String globlId, String area,
            Integer num, Integer startIdex, String channelCode, String bizType, CommonParam commonParam) {

        String logPrefix = "getStarrsRecommendation_" + cid + "_" + commonParam.getUserId() + "_"
                + commonParam.getMac();
        PageResponse<BaseData> pageResponse = new PageResponse<BaseData>();
        try {
            if ("home".equals(channelCode)) {
                // cid = "tv_home";
                // num = 15;
                // RecommendationLevidiResponse levidiResponse =
                // this.getLevidiBaseTpResponse(cpId, cid, null, "rec_1902",
                // num, startIdex, bizType, null, logPrefix, commonParam);
                // pageResponse.setData(this.getLevidiHomePage(levidiResponse.getData(),
                // commonParam));

                // levidi过渡le版本，首页不走推荐直接走cms
                String pageId = ApplicationUtils.get(ApplicationConstants.CMS_LEVIDI_HOME_PAGE_ID);
                pageResponse = (PageResponse) channelService.getPageDataResponse(null, pageId,
                        null, null, null, null, null, commonParam);
            } else if ("publisher".equals(channelCode)) {
                pageResponse.setData(this.getLevidiPublisherPage(commonParam, logPrefix));
            } else if ("category".equals(channelCode)) {
                pageResponse.setData(this.getLevidiCategoryPage(commonParam, logPrefix));
            } else {
                RecommendationLevidiResponse levidiResponse = this.getLevidiBaseTpResponse(cpId, cid, globlId, area,
                        num, startIdex, bizType, null, logPrefix, commonParam);
                pageResponse.setData(this.getLevidiRecDataList(levidiResponse.getData(), commonParam));
            }

        } catch (Exception e) {
            log.info(logPrefix, e);
        }
        return pageResponse;
    }

    /**
     * levidi首页接口
     * @param recLevidiList
     * @param commonParam
     * @return
     */
    private List<BaseData> getLevidiHomePage(List<RecommendationLevidiResponse.RecLevidi> recLevidiList,
            CommonParam commonParam) {
        List<BaseData> sarrDtoList = new ArrayList<BaseData>();
        for (RecommendationLevidiResponse.RecLevidi recData : recLevidiList) {
            List<RecommendationLevidiResponse.RecLevidi> units = recData.getUnits();
            if (units != null) {
                RecommendationSarrDto<BaseData> sarrDto = new RecommendationSarrDto<BaseData>();
                List<BaseData> sarrDataList = new ArrayList<BaseData>();
                sarrDto.setTitle(recData.getSection_name());
                for (RecommendationLevidiResponse.RecLevidi rec : units) {
                    sarrDataList.add(this.getDataFromLevidiRecommendation(rec.getData(), commonParam));
                }
                sarrDto.setDataList(sarrDataList);
                if (!StringUtil.isBlank(recData.getOrder())) {
                    Integer order = Integer.valueOf(recData.getOrder());
                    if (order < sarrDtoList.size() && order >= 0) {
                        sarrDtoList.add(order, sarrDto);
                        continue;
                    }
                }
                sarrDtoList.add(sarrDto);
            }
        }
        return sarrDtoList;
    }

    /**
     * 获取publisher页面
     * @param commonParam
     * @param logPrefix
     * @return
     */
    private List<BaseData> getLevidiPublisherPage(CommonParam commonParam, String logPrefix) {
        List<BaseData> dataList = new ArrayList<BaseData>();
        // 获取所有publisher列表
        RecommendationUsLevidiPublisherResponse usPublisherResponse = this.facadeTpDao.getRecommendationTpDao()
                .getUsLevidiPublisherRecommendation(commonParam);
        StringBuilder cpIds = new StringBuilder();
        if (usPublisherResponse == null) {
            return dataList;
        }
        List<UsLevidiPublisher> list = usPublisherResponse.getData();
        if (list == null || list.size() == 0) {
            return dataList;
        }
        for (UsLevidiPublisher usPubliser : list) {
            if (usPubliser == null) {
                continue;
            }
            RecommendationUsLevidiPublisherResponse.UsLevidiContent publisherC = usPubliser.getData();
            if (publisherC == null) {
                continue;
            }
            ChannelData channelData = new ChannelData();
            channelData.setCpId(publisherC.getChannel_id());
            channelData.setGlobalId(publisherC.getChannel_id());
            cpIds.append(publisherC.getChannel_id()).append(",");
            channelData.setImg(publisherC.getPic_url());
            channelData.setTitle(publisherC.getName());
            channelData.setIconType(publisherC.getRemark());
            JumpUtil.bulidJumpObj(channelData, DataConstant.DATA_TYPE_CONTENT_LIST, null, null);
            dataList.add(channelData);
        }
        // 获取多个publisher下的数据
        RecommendationLevidiResponse levidiResponse = this.getLevidiBaseTpResponse(cpIds.toString(), null, null, null,
                14, null, "1", null, logPrefix, commonParam);
        this.getLevidiData(levidiResponse, dataList, logPrefix, commonParam);
        return dataList;
    }

    /**
     * 获取category页
     * @param commonParam
     * @param logPrefix
     * @return
     */
    private List<BaseData> getLevidiCategoryPage(CommonParam commonParam, String logPrefix) {
        List<BaseData> dataList = new ArrayList<BaseData>();
        StringBuilder cpCategoryIds = new StringBuilder();
        // 获取所有的分类
        RecommendationUsLevidiCategoryResponse usCategoryResponse = this.facadeTpDao.getRecommendationTpDao()
                .getUsLevidiCategoryRecommendation(commonParam);
        List<RecommendationUsLevidiCategoryResponse.UsLevidi> list = usCategoryResponse.getData();
        List<RecommendationUsLevidiCategoryResponse.UsLevidiCategory> categorys = null;
        if (list != null && list.size() > 0) {
            RecommendationUsLevidiCategoryResponse.UsLevidi usLevidi = list.get(0);
            categorys = usLevidi.getNo_pic_content();
            if (categorys != null) {
                categorys.addAll(usLevidi.getPic_category());
            } else {
                categorys = usLevidi.getPic_category();
            }
        }
        if (categorys != null && categorys.size() > 0) {
            for (RecommendationUsLevidiCategoryResponse.UsLevidiCategory category : categorys) {
                if (category == null || category.getType() == null || category.getType() != 1) {
                    continue;
                }
                ChannelData channelData = new ChannelData();
                channelData.setCpCategoryId(category.getCid());
                if (category.getMulti_pic_urls() != null) {
                    channelData.setImg(category.getMulti_pic_urls().get("tvPic"));
                }
                channelData.setTitle(category.getName());
                cpCategoryIds.append(category.getCid()).append(",");
                JumpUtil.bulidJumpObj(channelData, DataConstant.DATA_TYPE_CONTENT_LIST, null, null);
                dataList.add(channelData);
            }
        }
        // 获取所有category下的数据
        RecommendationLevidiResponse levidiResponse = this.getLevidiBaseTpResponse(null, cpCategoryIds.toString(),
                null, null, 14, null, "1", null, logPrefix, commonParam);
        this.getLevidiData(levidiResponse, dataList, logPrefix, commonParam);
        return dataList;
    }

    private void getLevidiData(RecommendationLevidiResponse levidiResponse, List<BaseData> dataList, String logPrefix,
            CommonParam commonParam) {
        List<RecommendationLevidiResponse.RecLevidi> recLevidiList = levidiResponse.getData();
        List<RecommendationLevidiResponse.Group> groupList = levidiResponse.getGroups();
        if (groupList == null || groupList.size() == 0) {
            log.info(logPrefix + "group info is null");
            return;
        }
        for (int i = 0; i < groupList.size(); i++) {
            RecommendationLevidiResponse.Group group = groupList.get(i);
            int count = group.getCount();
            int offset = group.getOffset();
            List<RecommendationLevidiResponse.RecLevidi> subList = null;
            if (recLevidiList.size() < offset) {
                log.info(logPrefix + "group info has no match rec data!");
                break;
            }
            if (recLevidiList.size() < (offset + count)) {
                log.info(logPrefix + "group info has no match rec data!");
                subList = recLevidiList.subList(offset, recLevidiList.size());
                break;
            }
            subList = recLevidiList.subList(offset, offset + count);
            ChannelData channelData = (ChannelData) dataList.get(i);
            channelData.setDataList(this.getLevidiRecDataList(subList, commonParam));
        }
    }

    /**
     * 推荐数据列表
     * @param recLevidiList
     * @param commonParam
     * @return
     */
    public List<BaseData> getLevidiRecDataList(List<RecommendationLevidiResponse.RecLevidi> recLevidiList,
            CommonParam commonParam) {
        List<BaseData> sarrDataList = new ArrayList<BaseData>();
        if (recLevidiList == null) {
            return sarrDataList;
        }
        for (RecommendationLevidiResponse.RecLevidi recData : recLevidiList) {
            RecommendationTpResponseLevidiRec data = recData.getData();
            sarrDataList.add(this.getDataFromLevidiRecommendation(data, commonParam));
        }
        // 老版本leividi结构定义存在问题，新版本发布后代码会删掉
        if (LocaleConstant.Wcode.IN.equals(commonParam.getWcode()) && !StringUtil.isBlank(commonParam.getAppCode())
                && Integer.valueOf(commonParam.getAppCode()) < 400) {
            RecommendationSarrDto<BaseData> sarrDto = new RecommendationSarrDto<BaseData>();
            List<BaseData> sarrDtoList = new ArrayList<BaseData>();
            sarrDto.setDataList(sarrDataList);
            sarrDtoList.add(sarrDto);
            return sarrDtoList;
        } else {
            return sarrDataList;
        }
    }

    /**
     * 获取levidi基本推荐数据
     * @param cpId
     * @param cid
     * @param num
     * @param startIdex
     * @param logPrefix
     * @param commonParam
     * @return
     */
    private RecommendationLevidiResponse getLevidiBaseTpResponse(String cpId, String cid, String globalId, String area,
            Integer num, Integer startIdex, String bizType, String subscribeIds, String logPrefix,
            CommonParam commonParam) {
        if (StringUtil.isBlank(area)) {
            if (StringUtil.isNotBlank(cpId)) {
                area = "rec_2104";
            } else if (StringUtil.isNotBlank(cid)) {
                area = "rec_1903";
            } else {
                area = "rec_2104";
            }
        }
        RecommendationLevidiResponse levidiResponse = this.facadeTpDao.getRecommendationTpDao()
                .getLevidiRecommendation(cpId, cid, globalId, num, area, startIdex, bizType, subscribeIds, commonParam);
        if (levidiResponse == null || levidiResponse.getData() == null || levidiResponse.getData().size() == 0) {
            log.info(logPrefix + "rec data is null");
        }
        return levidiResponse;
    }

    /**
     * 推荐数据列表
     * @param commonParam
     * @return
     */
    private BaseData getDataFromLevidiRecommendation(RecommendationTpResponseLevidiRec sarrRec, CommonParam commonParam) {

        if ("Album".equals(sarrRec.getData_type())) {
            AlbumDto albumDto = new AlbumDto();
            albumDto.setAlbumId(sarrRec.getAid());
            albumDto.setIfCharge(sarrRec.getIs_pay());
            albumDto.setName(sarrRec.getName());
            albumDto.setSubName(sarrRec.getSubname());
            albumDto.setCp(sarrRec.getCp_provider());
            albumDto.setGlobalId(sarrRec.getId());
            albumDto.setDescription(sarrRec.getDescription());
            albumDto.setGlobalCid(sarrRec.getCid());
            if (LocaleConstant.Wcode.IN.equals(commonParam.getWcode())) {
                if (!StringUtil.isBlank(commonParam.getAppCode())) {
                    if (Integer.valueOf(commonParam.getAppCode()) >= 400) {
                        albumDto.setImg(this.getLevidiNewPic(sarrRec.getPoster(), sarrRec.getMini_poster()));
                    } else {
                        albumDto.setImg(this.getLevidiOldPic(sarrRec.getPoster(), sarrRec.getMini_poster()));
                    }
                } else {
                    albumDto.setImg(this.getLevidiNewPic(sarrRec.getPoster(), sarrRec.getMini_poster()));
                }
            } else if (LocaleConstant.Wcode.US.equals(commonParam.getWcode())) {
                albumDto.setImg(this.getLevidiNewPic(sarrRec.getPoster(), sarrRec.getMini_poster()));
            }
            if (!StringUtil.isBlank(sarrRec.getIs_pay())) {
                albumDto.setPayType(Integer.valueOf(sarrRec.getIs_pay()));
            }
            JumpUtil.bulidJumpObj(albumDto, DataConstant.DATA_TYPE_ALBUM, "", "");
            return albumDto;
        } else if ("Video".equals(sarrRec.getData_type())) {
            VideoDto videoDto = new VideoDto();
            videoDto.setVideoId(sarrRec.getVid());
            videoDto.setAlbumId(sarrRec.getAid());
            videoDto.setName(sarrRec.getName());
            videoDto.setSubName(sarrRec.getSubname());
            videoDto.setMid(sarrRec.getMmid());
            videoDto.setGlobalId(sarrRec.getId());
            videoDto.setExternal_id(sarrRec.getExternal_id());
            videoDto.setDesc(sarrRec.getDescription());
            videoDto.setSource(sarrRec.getSource());
            videoDto.setGlobalCid(sarrRec.getCid());
            if (LocaleConstant.Wcode.IN.equals(commonParam.getWcode())) {
                if (!StringUtil.isBlank(commonParam.getAppCode())) {
                    if (Integer.valueOf(commonParam.getAppCode()) >= 400) {
                        videoDto.setImg(this.getLevidiNewPic(sarrRec.getPoster(), sarrRec.getMini_poster()));
                    } else {
                        videoDto.setImg(this.getLevidiOldPic(sarrRec.getPoster(), sarrRec.getMini_poster()));
                    }
                } else {
                    videoDto.setImg(this.getLevidiNewPic(sarrRec.getPoster(), sarrRec.getMini_poster()));
                }
            } else if (LocaleConstant.Wcode.US.equals(commonParam.getWcode())) {
                videoDto.setImg(this.getLevidiNewPic(sarrRec.getPoster(), sarrRec.getMini_poster()));
            }
            if (!StringUtil.isBlank(sarrRec.getSrc())) {
                videoDto.setSrc(Integer.valueOf(sarrRec.getSrc()));
            }

            if (!StringUtil.isBlank(sarrRec.getIs_pay())) {
                videoDto.setIfCharge(sarrRec.getIs_pay());
            }
            JumpUtil.bulidJumpObj(videoDto, DataConstant.DATA_TYPE_VIDEO, "", "");
            return videoDto;
        } else if ("publisher".equals(sarrRec.getData_type())) {
            Channel channel = new Channel();
            channel.setCpId(sarrRec.getChannel_id());
            channel.setGlobalId(sarrRec.getChannel_id());
            channel.setName(sarrRec.getName());
            channel.setImg(sarrRec.getPic_url());
            channel.setIconType(sarrRec.getRemark());
            JumpUtil.bulidJumpObj(channel, DataConstant.DATA_TYPE_CONTENT_LIST, "", "");
            return channel;
        } else if ("Category".equals(sarrRec.getData_type())) {
            Channel channel = new Channel();
            channel.setCpCategoryId(sarrRec.getCategory_id());
            channel.setName(sarrRec.getCategory_name());
            channel.setImg(sarrRec.getCategory_pic());
            JumpUtil.bulidJumpObj(channel, DataConstant.DATA_TYPE_CONTENT_LIST, "", "");
            return channel;
        } else {
            return new BaseData();
        }
    }

    private String getLevidiOldPic(Map<String, String> pics, String pic) {
        String imgUrl = null;
        if (pics == null || pics.size() == 0) {
            return imgUrl;
        }
        imgUrl = pics.get("tvPic");
        if (StringUtils.isBlank(imgUrl)) {
            imgUrl = pics.get("450*600");
        }
        if (StringUtils.isBlank(imgUrl)) {
            imgUrl = pic;
        }
        return imgUrl;
    }

    private String getLevidiNewPic(Map<String, String> pics, String pic) {
        String imgUrl = null;
        if (pics == null || pics.size() == 0) {
            return imgUrl;
        }
        imgUrl = pics.get("tvPic");
        if (StringUtils.isBlank(imgUrl)) {
            imgUrl = pics.get("1280*720");
        }
        if (StringUtils.isBlank(imgUrl)) {
            imgUrl = pics.get("960*540");
        }
        if (StringUtils.isBlank(imgUrl)) {
            imgUrl = pics.get("640*360");
        }
        if (StringUtils.isBlank(imgUrl)) {
            imgUrl = pics.get("320*180");
        }
        if (StringUtils.isBlank(imgUrl)) {
            imgUrl = pic;
        }
        return imgUrl;
    }

    /**
     * 获取退出播放推荐、自然播放结束推荐的推荐数据
     * @param recommendationTpResponse
     * @param dataList
     * @param pickAlbum
     * @param number
     * @param albumId
     * @param area
     * @param rcType
     * @param categoryId
     * @param logPrefix
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void getRecommendData(RecommendationTpResponse recommendationTpResponse, List<BaseData> dataList,
            boolean pickAlbum, Integer number, Integer albumId, String area, String rcType, Integer categoryId,
            String logPrefix, CommonParam commonParam) {
        if (recommendationTpResponse == null || CollectionUtils.isEmpty(recommendationTpResponse.getRec())) {
            return;
        }
        List<RecommendationTpResponseRec> recommendationTpResponseRecList = recommendationTpResponse.getRec();
        List<String> albumIdList = new ArrayList<String>();// 专辑id列表
        List<String> videoIdList = new ArrayList<String>();// 视频id列表
        Set<BaseData> albumSet = new TreeSet(new Comparator<AlbumDto>() {
            @Override
            public int compare(AlbumDto o1, AlbumDto o2) {
                if (o1 != null && o2 != null && !StringUtil.isBlank(o1.getAlbumId())
                        && o1.getAlbumId().equals(o2.getAlbumId())) {
                    return 0;
                } else {
                    return 1;
                }
            }

        });
        Set<BaseData> videoSet = new TreeSet(new Comparator<VideoDto>() {

            @Override
            public int compare(VideoDto o1, VideoDto o2) {
                if (o1 != null && o2 != null && !StringUtil.isBlank(o1.getVideoId())
                        && o1.getVideoId().equals(o2.getVideoId())) {
                    return 0;
                } else {
                    return 1;
                }
            }

        });
        List<BaseData> recommendList = new LinkedList<BaseData>();// 推荐数据
        for (RecommendationTpResponseRec recResponse : recommendationTpResponseRecList) {
            Integer dataType = recResponse.getIsalbum();// 判断是否是专辑或者视频，只跟此字段值有关
            if (dataType == null) {// dataType为空，继续循环下一个
                continue;
            }
            if (RecommendationConstants.REC_TYPE_PLAY_END.equals(rcType)
                    && dataType == RecommendationTpConstant.RECOMMENDATION_DATA_TYPE_ALBUM) {
                dataType = RecommendationTpConstant.RECOMMENDATION_DATA_TYPE_VIDEO;
            }
            ResourceInfo data = (ResourceInfo) this.getDataFromRecommendation(recResponse, dataType, area, rcType,
                    null, null, null, logPrefix, commonParam);
            if (data != null) {// 判空处理后再分别处理视频专辑
                data.setAreaRec(recommendationTpResponse.getArea());
                data.setReid(recommendationTpResponse.getReid());
                data.setBucket(recommendationTpResponse.getBucket());
                data.setBlockType(recommendationTpResponse.getType());
                recommendList.add(data);// 不是退出播放推荐或者播放结束的推荐
                if (dataType == RecommendationTpConstant.RECOMMENDATION_DATA_TYPE_VIDEO) {
                    VideoDto video = (VideoDto) data;
                    String id = video.getVideoId();
                    videoIdList.add(id);
                    videoSet.add(data);
                } else if (dataType == RecommendationTpConstant.RECOMMENDATION_DATA_TYPE_ALBUM) {
                    AlbumDto album = (AlbumDto) data;
                    if (RecommendationTpConstant.RECOMMENDATION_AREA_RELATED_REC.equals(area)) {
                        if (album.getTvCopyright() != null && album.getTvCopyright() == 1) {// 退出播放推荐需要对专辑进行版权过滤
                            String id = album.getAlbumId();
                            albumIdList.add(id);
                            albumSet.add(data);
                        }
                    } else {
                        String id = album.getAlbumId();
                        albumIdList.add(id);
                        albumSet.add(data);
                    }
                }
            }
        }
        if (RecommendationConstants.REC_TYPE_PLAY_END.equals(rcType)) {// 播放结束推荐，取视频列表
            dataList.addAll(videoSet);
        } else if (RecommendationTpConstant.RECOMMENDATION_AREA_RELATED_REC.equals(area)) {// 退出播放推荐
            String info = null;
            if (pickAlbum && albumSet.size() >= number) {// 优先推专辑数据
                PlayCenterInfo playInfo = null;
                if (!TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                        .getTerminalApplication()) && !TerminalUtil.isLetvUs(commonParam)) {
                    // Lecom退出播放推荐和结束联播推荐不对接游戏数据
                    playInfo = this.getPlayCenterRecommendData(albumId);
                }
                if (playInfo != null) {
                    dataList.add(playInfo);
                } else {
                    // 如果游戏中心不存在此专辑 ,则走以前专辑逻辑
                    dataList.addAll(new LinkedList<BaseData>(albumSet));
                    if (albumSet.size() < albumIdList.size()) {
                        info = " ,recommend data have repeat albumn.";
                    }
                    log.info(logPrefix + ":recommendate albumn list,ids "
                            + Arrays.toString(albumIdList.toArray(new String[0])) + info);
                }
            } else {// 推单视频数据
                if (videoSet.size() < 3) {// 视频列表小于3个，返回专辑列表
                    if (albumSet.size() < albumIdList.size()) {
                        info = " ,recommend data have repeat albumn.";
                    }
                    log.info(logPrefix + ",video list size less than 3,so recommendate album list,ids "
                            + Arrays.toString(albumIdList.toArray(new String[0])) + info);
                    dataList.addAll(new LinkedList<BaseData>(albumSet));
                } else {
                    if (videoSet.size() < videoIdList.size()) {
                        info = " ,recommend data have repeat video.";
                    }
                    log.info(logPrefix + ":recommendate video list,ids "
                            + Arrays.toString(videoIdList.toArray(new String[0])) + info);
                    dataList.addAll(new LinkedList<BaseData>(videoSet));
                }
            }
            // 缓存数据的key，目的是当调用推荐接口失败时，可以读取缓存的数据
            String key = RecommendationTpConstant.RECOMMENDATION_PREFIX + categoryId;
            // 当退出播放时调用第三方接口失败，需要读取缓存中的对应分类id的数据
            if (CollectionUtils.isEmpty(dataList)) {
                dataList = (List<BaseData>) this.facadeCacheDao.getRecommendationCacheDao().getSingleCache(categoryId,
                        Object.class, logPrefix, commonParam);
                log.info(logPrefix + " get recommend data failure. Get data from cache.");
            } else {
                // 缓存一个标志位10分钟，当标志位失效时，需要更新一次缓存 RedisUtil.get(markKey)
                this.facadeCacheDao.getRecommendationCacheDao().setSingleCache(categoryId, dataList, logPrefix,
                        commonParam);
            }
        } else {
            // 不是播放结束推荐，也不是退出播放推荐
            dataList.addAll(recommendList);
        }
    }

    /**
     * 解析推荐数据，为了灵活可控，增加参数dataType
     * @param recommendationTpResponseRec
     * @param area
     *            页面区域，推荐接口关键参数；目前仅支持rec_0001--相关推荐，rec_0020--TV音乐台，
     *            2.8.2新增需求，相关推荐rec_0001中，专辑取竖图，单视频取横图
     * @param dataType
     *            用于解析[视频数据、专辑数据或者板块数据]
     * @param rcType
     *            与客户端约定的推荐类型，2--播放结束时短视频推荐
     * @param logPrefix
     * @param channelId
     * @param logPrefix
     * @return
     */
    private BaseData getDataFromRecommendation(RecommendationTpResponseRec recommendationTpResponseRec,
            Integer dataType, String area, String rcType, Integer channelId, String defaultStream,
            String defaultStreamName, String logPrefix, CommonParam commonParam) {
        if (dataType != null && dataType == RecommendationTpConstant.RECOMMENDATION_DATA_TYPE_ALBUM) {
            AlbumDto album = new AlbumDto();
            if (this.hasTvCopyright(recommendationTpResponseRec.getAlbum_play_platform())) {
                album.setTvCopyright(1);
            } else {// 无版权
                if (ChannelConstants.BROADCAST_ID == CommonConstants.CIBN) {
                    return null;// 国广过滤无版权数据
                }
                album.setTvCopyright(0);
                album.setSrc(1);// 目前只有内网的专辑详情页
            }
            album.setAlbumId(String.valueOf(recommendationTpResponseRec.getPid()));
            album.setName(recommendationTpResponseRec.getPidname());
            if (!StringUtil.isBlank(recommendationTpResponseRec.getScore())) {
                album.setScore(Float.valueOf(recommendationTpResponseRec.getScore()));
                // TODO: 测试！
                // album.setDb_score(Float.valueOf(recommendationTpResponseRec.getScore()));
            }
            if (!StringUtil.isBlank(recommendationTpResponseRec.getDouban_rating())) {
                album.setDb_score(Float.valueOf(recommendationTpResponseRec.getDouban_rating()));
            }
            String img = null, bigImg = null;
            String imgSize = null;
            if (RecommendationConstants.REC_TYPE_PLAY_EXIT.equalsIgnoreCase(rcType)
                    || RecommendationConstants.REC_TYPE_ALBUM_NO_TVCOPYRIGHT.equalsIgnoreCase(rcType)) {
                // 退出播放推荐、专辑详情页无版权，专辑取竖图
                img = recommendationTpResponseRec.getPicurl_st();
                if (StringUtils.isBlank(img)) {// 没有专辑竖图，则取横图
                    img = recommendationTpResponseRec.getPicurl();
                    log.info(logPrefix + ",play exit recommend data album picurl_st is null.");
                }
            } else {// 否则取横图
                if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                        .getTerminalApplication())) { // 美国乐视视频去竖图
                    img = recommendationTpResponseRec.getPicurl_st();
                    Map<String, String> pics = new HashMap<String, String>();
                    pics.put(CommonConstants.PIXEL_400_225, recommendationTpResponseRec.getPic_16_9());
                    pics.put(CommonConstants.PIXEL_300_400, img);
                    album.setPic_urls(pics);
                } else {
                    img = recommendationTpResponseRec.getPicurl();
                    bigImg = recommendationTpResponseRec.getPicurl_st(); // 详情页改版
                }
            }
            album.setImg(img);
            album.setBigImg(bigImg);
            album.setImgSize(imgSize);
            Integer category = recommendationTpResponseRec.getCid();
            album.setCategoryId(category);
            album.setSubCategoryId(recommendationTpResponseRec.getAlbum_sub_category_code());
            album.setIs_rec(recommendationTpResponseRec.getIs_rec());
            Integer isEnd = 0;

            album.setIfCharge(StringUtil.isBlank(recommendationTpResponseRec.getIs_pay()) ? "0"
                    : recommendationTpResponseRec.getIs_pay());
            // for tvod icon type recommendation
            album.setChargeType(JumpUtil.getChargeType(recommendationTpResponseRec.getIs_pay(),
                    recommendationTpResponseRec.getPay_type(), recommendationTpResponseRec.getIs_coupon()));

            if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                List<ChargeInfo> chargeInfos = MmsDataUtil.parserPayDetail(recommendationTpResponseRec.getPay_detail());
                album.setChargeInfos(JumpUtil.genChargeInfos(chargeInfos));
                ChargeInfo chargeInfo = MmsDataUtil.getChargeInfoFromPayDetail(commonParam.getP_devType(), chargeInfos);
                if (null != chargeInfo) {
                    album.setIfCharge(chargeInfo.getIsCharge());
                    album.setChargeType(JumpUtil.getChargeType(chargeInfo));
                }
            }

            if (recommendationTpResponseRec.getIsend() != null) {// 判断是否完结
                isEnd = recommendationTpResponseRec.getIsend();
            }
            Integer vcount = recommendationTpResponseRec.getVcount();
            if (isEnd == RecommendationTpConstant.RECOMMENDATION_ALBUM_END_YES) {// 已完结
                if (category != null && category == VideoConstants.Category.CARTOON && (vcount != null && vcount != 0)) {// 动漫
                    album.setSubName(vcount + "集全");
                } else {
                    album.setSubName(recommendationTpResponseRec.getSubtitle());
                }
            } else if (isEnd == RecommendationTpConstant.RECOMMENDATION_ALBUM_END_NO) {// 未完结
                if (category != null && (vcount != null && vcount != 0)) {
                    if ((category == VideoConstants.Category.CARTOON) || (category == VideoConstants.Category.TV)) {// 动漫、电视剧
                        // 2016-03-28推荐推出预告片，为了防止出现更新集数为“0”的情况
                        album.setSubName("更新至" + recommendationTpResponseRec.getVcount() + "集");
                    } else if (category == VideoConstants.Category.VARIETY) {// 综艺
                        album.setSubName("更新至" + recommendationTpResponseRec.getVcount() + "期");
                    } else {
                        album.setSubName(recommendationTpResponseRec.getSubtitle());
                    }
                } else {
                    album.setSubName(recommendationTpResponseRec.getSubtitle());
                }
            }
            if (RecommendationConstants.REC_TYPE_PLAY_EXIT.equals(rcType)
                    || RecommendationConstants.REC_TYPE_PLAY_END.equals(rcType)) {
                album.setAction(RecommendationTpConstant.RECOMMEND_ACTION);
                album.setExposure(RecommendationTpConstant.RECOMMEND_EXPOSURE);
            }
            return JumpUtil.bulidJumpObj(album, DataConstant.DATA_TYPE_ALBUM, defaultStream, defaultStreamName,
                    commonParam);
        } else if (dataType != null && dataType == RecommendationTpConstant.RECOMMENDATION_DATA_TYPE_VIDEO) {
            VideoDto video = new VideoDto();
            Integer categoryId = recommendationTpResponseRec.getCid();
            Integer videoId = recommendationTpResponseRec.getVid();
            if (this.hasTvCopyright(recommendationTpResponseRec.getVideo_play_platform())) {
                video.setTvCopyright(1);
            } else {// 无版权
                if (ChannelConstants.BROADCAST_ID == CommonConstants.CIBN) {
                    return null;// 国广不允许跳外网播放
                }
                video.setTvCopyright(0);
                video.setSrc(1);// 目前只有内网的专辑详情页
                // 其实这个字段2.9.1版本不应该在此返回，客户端直接写死，但是为了后期扩展外网播放，所以由服务端返回，以后做外网播放时，此字段要废掉
                video.setWebsite(ApplicationConstants.WEBSITE_WWW_LETV_COM);
                video.setWebPlayUrl(DataConstant.PC_PLAY_URL + videoId + ".html");
            }
            video.setVideoId(String.valueOf(videoId));
            video.setName(recommendationTpResponseRec.getTitle());
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) { // 美国乐视视频去竖图
                Map<String, String> pics = new HashMap<String, String>();
                pics.put(CommonConstants.PIXEL_400_225, recommendationTpResponseRec.getPic_16_9());
                video.setPic_urls(pics);
            }
            video.setImg(recommendationTpResponseRec.getPicurl());
            video.setBigImg(recommendationTpResponseRec.getPicurl_st()); // 详情页改版
            video.setAlbumId(String.valueOf(recommendationTpResponseRec.getPid()));
            video.setCategoryId(categoryId);
            video.setSubCategoryId(recommendationTpResponseRec.getAlbum_sub_category_code());
            video.setIfCharge(StringUtil.isBlank(recommendationTpResponseRec.getIs_pay()) ? "0"
                    : recommendationTpResponseRec.getIs_pay());
            // for tvod icon type recommendation
            video.setChargeType(JumpUtil.getChargeType(recommendationTpResponseRec.getIs_pay(),
                    recommendationTpResponseRec.getPay_type(), recommendationTpResponseRec.getIs_coupon()));

            if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                List<ChargeInfo> chargeInfos = MmsDataUtil.parserPayDetail(recommendationTpResponseRec.getPay_detail());
                video.setChargeInfos(JumpUtil.genChargeInfos(chargeInfos));
                ChargeInfo chargeInfo = MmsDataUtil.getChargeInfoFromPayDetail(commonParam.getP_devType(), chargeInfos);
                if (null != chargeInfo) {
                    video.setIfCharge(chargeInfo.getIsCharge());
                    video.setChargeType(JumpUtil.getChargeType(chargeInfo));
                }
            }

            if (recommendationTpResponseRec.getDuration() != null) {
                if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                        .getTerminalApplication())) {
                    video.setDuration(recommendationTpResponseRec.getDuration() * 1000L);
                } else {
                    video.setDuration(recommendationTpResponseRec.getDuration());
                }
            }
            video.setIs_rec(recommendationTpResponseRec.getIs_rec());

            if (!StringUtil.isBlank(recommendationTpResponseRec.getStarring())) {
                video.setSinger(recommendationTpResponseRec.getStarring());
            } else {
                video.setSinger(MessageUtils.getMessage(SearchConstant.SEARCH_SEARCHRESULT_SINGER,
                        commonParam.getLangcode()));
            }

            if (categoryId == null) {// 分类为空，默认取视频副标题
                video.setSubName(recommendationTpResponseRec.getSubtitle());
            } else {
                if (categoryId == VideoConstants.Category.MUSIC) {// 音乐
                    video.setSubName(recommendationTpResponseRec.getStarring());// 副标题为歌星
                } else if (categoryId == VideoConstants.Category.SPORT) {// 体育
                    // 无副标题
                } else {
                    if (RecommendationConstants.REC_TYPE_PLAY_END.equals(rcType)) {// 播放结束的短视频推荐
                        if (categoryId == VideoConstants.Category.FILM || categoryId == VideoConstants.Category.TV) {
                            // 播放结束推荐中，电影、电视剧只要视频标题，不要副标题
                        } else {
                            video.setSubName(recommendationTpResponseRec.getSubtitle());
                        }
                    } else {// 不是播放结束推荐，则取视频副标题
                        video.setSubName(recommendationTpResponseRec.getSubtitle());
                    }
                }
            }
            if (RecommendationConstants.REC_TYPE_PLAY_EXIT.equals(rcType)
                    || RecommendationConstants.REC_TYPE_PLAY_END.equals(rcType)) {
                video.setAction(RecommendationTpConstant.RECOMMEND_ACTION);
                video.setExposure(RecommendationTpConstant.RECOMMEND_EXPOSURE);
            }
            return JumpUtil.bulidJumpObj(video, DataConstant.DATA_TYPE_VIDEO, defaultStream, defaultStreamName,
                    commonParam);
        } else if (recommendationTpResponseRec.getExtendJson() != null
                && recommendationTpResponseRec.getExtendJson().getExtendPage() != null
                && recommendationTpResponseRec.getExtendJson().getExtendPage().length() > 0) {// 配置默认版块
            BaseBlock block = new BaseBlock();
            block.setName(recommendationTpResponseRec.getTitle());
            block.setSubName(recommendationTpResponseRec.getSubTitle());
            block.setImg(recommendationTpResponseRec.getTvPic());
            block.setDataType(Integer
                    .parseInt(recommendationTpResponseRec.getExtendJson().getExtendPage().split("\\|")[0]));
            if (recommendationTpResponseRec.getExtendJson().getExtendPage().split("\\|").length > 1) {
                block.setLabelIdToPlay(recommendationTpResponseRec.getExtendJson().getExtendPage().split("\\|")[1]);
            } else {
                block.setLabelIdToPlay(null);
            }
            return JumpUtil.bulidJumpObj(block, block.getDataType(), defaultStream, defaultStreamName, commonParam);
        }
        return null;
    }

    /**
     * 根据专辑id推荐游戏中心数据
     * @param albumId
     * @return
     */
    private PlayCenterInfo getPlayCenterRecommendData(Integer albumId) {
        if (albumId == null) {
            return null;
        }
        // 如果是专辑 则游戏中心调用专辑列表
        boolean isCache = true;
        RecommendaionPidListResponse pidListResponse = this.facadeCacheDao.getRecommendationCacheDao()
                .getRecommendaionPidList();
        if (pidListResponse == null || !pidListResponse.isSuccess()) {
            pidListResponse = this.facadeTpDao.getRecommendationTpDao().getRecommendationPidList();
            isCache = false;
        }
        String[] pids = null;
        if (pidListResponse != null && pidListResponse.isSuccess()) {
            pids = pidListResponse.getEntity();
            if (!isCache) {
                this.facadeCacheDao.getRecommendationCacheDao().setRecommendaionPidList(pidListResponse);
            }
        }
        // 判断播放专辑是否显示游戏,如显示，调用游戏中心导流接口，退出执行；否则执行下面的方法
        RecommendaionByPidResponse res = null;
        if (this.isInGamePids(pids, albumId)) {
            res = this.facadeTpDao.getRecommendationTpDao().getRecommendationByPid(albumId.toString());
        }
        // 获取游戏中心数据，并返回接口数据
        if (res != null) {
            RecommendaionByPidResponse.Ent ent = res.getEntity();
            PlayCenterInfo playInfo = new PlayCenterInfo();
            playInfo.setAccessories(ent.getAccessories());
            playInfo.setDownloadCount(ent.getDownloadCount());
            playInfo.setIcon(ent.getIcon());
            playInfo.setName(ent.getName());
            playInfo.setSubTitle(ent.getSubTitle());
            playInfo.setType(ent.getType());
            playInfo.setValue(ent.getValue());
            playInfo.setDataType(DataConstant.DATA_TYPE_PLAY_CENTER);
            return playInfo;
        }
        return null;
    }

    /**
     * 更新推荐容错缓存
     */
    public void updateRecCache(CommonParam commonParam) {
        // this.facadeTpDao.getRecommendationTpDao().updateRecCache(commonParm);
        String domain = RecommendationTpConstant.getRecURL(commonParam);
        String[] recUrls = RecommendationTpConstant.REC_URL;
        if ("HK".equalsIgnoreCase(commonParam.getWcode())) {
            recUrls = RecommendationTpConstant.REC_URL_HK;
        }

        for (String url : recUrls) {
            url = domain + url + "&" + "region=" + commonParam.getWcode() + "&lang=" + commonParam.getLangcode();
            String result = this.facadeTpDao.getRecommendationTpDao().updateRecCache(url);
            if (result != null && result.length() > 1024) {
                this.facadeCacheDao.getRecommendationCacheDao().updateRecCache(url, result, null, commonParam);
                log.info("method[updateRecCache]  update Recommend Recovery Cache is ok url[" + url + "]");
            }
            if ("CN".equalsIgnoreCase(commonParam.getWcode())) {
                // 为国广版本做缓存
                url = url + "&bc=2";
                result = this.facadeTpDao.getRecommendationTpDao().updateRecCache(url);
                if (result != null && result.length() > 1024) {
                    this.facadeCacheDao.getRecommendationCacheDao().updateRecCache(url, result, null, commonParam);
                    log.info("method[updateRecCache]  update Cibn Recommend Recovery Cache is url[" + url + "]");
                }
            }
        }
    }

    /**
     * 新的容错缓存逻辑
     * @param commonParam
     */
    public void updateNewRecCache(Integer type, CommonParam commonParam) {
        String categoryIds = "null,0,1,2,3,4,5,7,8,9,10,11,14,16,17,20,22,23,30,32,34,36,39,43,46,1009,1021,1029";
        String rcPages = "page_0008,page_0006,page_0005,page_0026,page_0002,page_0015,page_0011,page_0023,page_0010,page_0027,page_0004,page_0001,page_0016,page_0009,page_0020"
                + ",page_0003,page_0013,page_0017,page_0022,page_0014,page_0025,page_0024,page_0012,page_0032,page_0007";
        switch (type) {
        case 0:
            String[] pageIds = rcPages.split(",");
            for (String pageid : pageIds) {
                Map<String, RecommendationTpResponse> recommendationTpResponses = this.facadeTpDao
                        .getRecommendationTpDao().getMultiBlocks(null, null, null, null, pageid, null, null, null,
                                commonParam);
                if (recommendationTpResponses != null) {
                    this.facadeCacheDao.getRecommendationCacheDao().updateNewRecCache(recommendationTpResponses,
                            pageid, null, null, type, null, commonParam);
                }
            }
            break;
        case 1:
            String[] cids = categoryIds.split(",");
            String area = "rec_0001";
            int dataSize = 4;
            for (String cid : cids) {
                PageResponse<BaseData> response = (PageResponse<BaseData>) this.getSingleBlock(StringUtil.isBlank(cid)
                        || "null".equals(cid) ? null : Integer.valueOf(cid), null, null, null, dataSize, area, null,
                        null, null, commonParam);
                if (response != null) {
                    this.facadeCacheDao.getRecommendationCacheDao().updateNewRecCache(response.getData(), null, area,
                            cid, type, null, commonParam);
                }
            }
            break;
        }
        log.info("update Recommend Recovery Cache is ok");
    }

    /**
     * 乐见下拉获取视频数据
     * @param area
     * @param num
     * @param logPrefix
     * @param commonParam
     * @return
     */
    private List<Recommendation> getLeviewDataFromRec(String area, Integer num, String logPrefix,
            CommonParam commonParam) {
        RecommendationResponse recResponse = this.facadeTpDao.getRecommendationTpDao().getLeViewData(area, num,
                commonParam);
        if (recResponse == null) {
            return null;
        }
        Recommendation recLeview = new Recommendation();
        List<BaseData> dataList = new ArrayList<BaseData>();
        List<Recommendation> leviewDataList = new ArrayList<Recommendation>();
        List<ServingResult> servResult = recResponse.getServing_result_list();
        recLeview.setArea(area);
        recLeview.setRec_id(recResponse.getReid());
        recLeview.setExp_var_id(recResponse.getExperiment_variants());
        if (servResult != null && servResult.size() > 0) {
            for (ServingResult result : servResult) {
                if (result == null) {
                    log.info(logPrefix + "rec[ServingResult] result is null");
                    continue;
                }
                ResultDocInfo docInfo = result.getDoc_info();
                if (docInfo == null) {
                    log.info(logPrefix + "rec[ResultDocInfo] result is null");
                    continue;
                }
                if (docInfo.getData_type() == null) {
                    log.info(logPrefix + "rec[ResultDocInfo.data_type] data type is null");
                    continue;
                }
                BaseData data = this.getLeviewDataMore(docInfo, logPrefix, commonParam);
                if (data != null) {
                    dataList.add(data);
                }
            }
        }
        recLeview.setDataList(dataList);
        leviewDataList.add(recLeview);

        return leviewDataList;
    }

    /**
     * 桌面数据过滤
     * 乐见桌面>=1.0.8版本显示国广数据,>=1.0.9显示华数数据
     * @param commonParam
     * @return flag true:过滤， false：放开
     */
    private boolean isFilteredByAppVersion(String source, CommonParam commonParam) {
        boolean flag = false;
        if (StringUtil.isBlank(source)) {// 数据无source，过滤
            flag = true;
        }
        String appVersion = commonParam.getAppVersion();
        if (StringUtil.isBlank(appVersion)) {
            return true;
        }
        String[] num = appVersion.split("\\.");
        if (num.length < 3) {
            return true;
        }
        int version = Integer.valueOf(num[0]) * (int) Math.pow(10, 6) + Integer.valueOf(num[1]) * (int) Math.pow(10, 3)
                + Integer.valueOf(num[2]);
        if (CommonConstants.CIBN_NAME.equals(source)) {// 乐见1.0.8版本及以上放开国广数据
            if (version < 1000008) {
                flag = true;
            }
        }
        if (CommonConstants.WASU_NEW_NAME.equals(source)) {// 乐见1.0.9版本及以上放开华数数据
            if (version < 1000009) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 乐见下拉数据
     * @param docInfo
     * @param commonParam
     * @return
     */
    private BaseData getLeviewDataMore(ResultDocInfo docInfo, String logPrefix, CommonParam commonParam) {
        AlbumDto album = null;
        // 乐见桌面只有专辑,综艺频道推荐以单视频的方式推出，故做特殊处理
        if (DataConstant.DATA_TYPE_ALBUM == docInfo.getData_type().getValue()
                || docInfo.getCid() == VideoConstants.Category.VARIETY) {
            AlbumAttribute albumInfo = docInfo.getAlbum_attribute();
            if (albumInfo == null) {
                log.info(logPrefix + "rec AlbumAttribute is null");
                return null;
            }
            // 乐见桌面不同版本数据过滤
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIEW.equalsIgnoreCase(commonParam
                    .getTerminalApplication())) {
                if (isFilteredByAppVersion(albumInfo.getSource(), commonParam)) {
                    return album;
                }
            }
            VideoAttribute video = docInfo.getVideo_attribute();
            // 专辑类型管理（电影，电视剧，动漫，综艺，纪录片，频道的正片专辑）
            if (ArrayUtils.indexOf(RecommendationConstants.LEVIEW_REC_DATA_CID, docInfo.getCid()) == -1) {
                return album;
            }
            // 综艺频道先取视频图，视频图没有的情况下取专辑图
            String img = null;
            if (docInfo.getCid() == VideoConstants.Category.VARIETY) {
                if (video != null) {
                    img = this.getLeviewPic(video.getImages(), commonParam.getMac(), albumInfo.getAid());
                } else {
                    log.info(logPrefix + albumInfo.getAid() + "  variety video is null");
                }
                if (StringUtil.isBlank(img)) {
                    img = this.getLeviewPic(albumInfo.getImages(), commonParam.getMac(), albumInfo.getAid());
                }
            } else {
                img = this.getLeviewPic(albumInfo.getImages(), commonParam.getMac(), albumInfo.getAid());
            }
            // 无图的情况，过滤数据
            if (StringUtil.isBlank(img)) {
                return album;
            }
            album = new AlbumDto();
            album.setName(albumInfo.getName());
            album.setImg(img);
            album.setSource(albumInfo.getSource());
            album.setSubName(albumInfo.getSub_name());
            int is_end = albumInfo.getIs_end();
            if (is_end == 0) {
                is_end = 1;// 未完结
            } else {
                is_end = 2;// 完结
            }
            if (docInfo.getCid() == VideoConstants.Category.TV) {
                album.setSubName(VideoCommonUtil.getVideoOrAlbumEpisodeText(is_end, VideoConstants.Category.TV,
                        is_end == 1 ? albumInfo.getNow_episode() : albumInfo.getEpisodes() + "",
                        commonParam.getLangcode()));
            } else if (docInfo.getCid() == VideoConstants.Category.CARTOON) {
                if (is_end == 1) {
                    album.setSubName(VideoCommonUtil.getVideoOrAlbumEpisodeText(is_end,
                            VideoConstants.Category.CARTOON, albumInfo.getNow_episode() + "", commonParam.getLangcode()));
                }
            } else if (docInfo.getCid() == VideoConstants.Category.VARIETY) {
                if (video != null) {
                    album.setSubName(VideoCommonUtil.getVideoOrAlbumEpisodeText(is_end,
                            VideoConstants.Category.VARIETY, video.getEpisode() + "", commonParam.getLangcode()));
                }
            }
            album.setAlbumId(albumInfo.getAid());
            album.setGlobalId(docInfo.getGlobal_id());
            album.setSrc(Integer.valueOf(albumInfo.getSrc()));
            album.setIfCharge(this.isTvCopyRightOrPay(albumInfo.getPay_platform(), true) + "");
            album.setTvCopyright(this.isTvCopyRightOrPay(albumInfo.getPlay_platform(), false));
            album.setExternal_play_id(albumInfo.getExternal_play_id());
            album.setCategoryId(docInfo.getCid());
            if (album.getSrc() != 1) {// 外网专辑，个别字段与内网专辑取的不一致
                album.setName(docInfo.getName());
                album.setGlobalId(docInfo.getGlobal_id());
                album.setExternal_id(albumInfo.getExternal_id());
            }
            // 角标
            album.setIconType(BurrowUtil.getIconType(album.getSrc(), album.getIfCharge(), album.getStreams(),
                    album.getCategoryId()));
            // 封装jump
            BurrowUtil.buildBurrow(album, commonParam);
        }
        return album;
    }

    /**
     * 乐见首屏cms数据
     * @param commonParam
     * @return
     */
    private List<Recommendation> getLeviewDataFromCms(CommonParam commonParam, String logPrefix) {
        List<Recommendation> leviewDataList = new ArrayList<Recommendation>();
        // 从缓存中读取cms数据
        String cache_key = CacheContentConstants.LEVEIW_CACHE_KEY;
        if (TerminalCommonConstant.TERMINAL_APPLICATION_CIBN_LEVIEW.equalsIgnoreCase(commonParam
                .getTerminalApplication())) {
            cache_key = CacheContentConstants.CIBN_LEVEIW_CACHE_KEY;
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_WASU_LEVIEW.equalsIgnoreCase(commonParam
                .getTerminalApplication())) {
            cache_key = CacheContentConstants.WASU_LEVEIW_CACHE_KEY;
        }
        List<CmsBlockTpResponse> responseList = this.facadeCacheDao.getChannelCacheDao().getBlockContent(cache_key);
        if (responseList == null) {
            return leviewDataList;
        }
        for (CmsBlockTpResponse cmsBlockTpResponse : responseList) {
            List<BaseData> dataList = new ArrayList<BaseData>();
            Recommendation leview = new Recommendation();
            leview.setDataList(dataList);
            if (cmsBlockTpResponse != null && cmsBlockTpResponse.getBlockContent() != null) {
                leview.setBlockName(cmsBlockTpResponse.getName());
                List<CmsBlockContentTpResponse> cmsBlockContentTpResponseList = cmsBlockTpResponse.getBlockContent();
                if (cmsBlockContentTpResponseList != null && cmsBlockContentTpResponseList.size() > 0) {
                    for (CmsBlockContentTpResponse cmsBlockContentTpResponse : cmsBlockContentTpResponseList) {
                        // 没有推送TV端的数据需要过滤
                        if ((cmsBlockContentTpResponse.getPushflag() == null)
                                || !cmsBlockContentTpResponse.getPushflag().contains("420007")) {
                            log.info(logPrefix + " has no tv copyright!");
                            continue;
                        }
                        BaseData data = channelService.getDataFromCms(
                                cmsBlockContentTpResponse, commonParam, null, null, null);
                        // cms不显示角标
                        if (data != null) {
                            if (data instanceof AlbumDto) {
                                AlbumDto album = (AlbumDto) data;
                                album.setIconType(null);
                                data = album;
                            }
                            if (data instanceof VideoDto) {
                                VideoDto video = (VideoDto) data;
                                video.setIconType(null);
                                data = video;
                            }
                            if (data instanceof Live) {
                                Live live = (Live) data;
                                live.setIconType(null);
                                data = live;
                            }
                            dataList.add(data);
                        }
                    }
                }
            }
            leviewDataList.add(leview);
        }
        return leviewDataList;
    }

    /**
     * 专辑视频是否有版权
     */
    public Integer isTvCopyRightOrPay(List<IdAndName> play_platform, boolean checkPay) {
        if (play_platform == null || play_platform.size() <= 0) {
            return 0;
        } else {
            Iterator<?> it = play_platform.iterator();
            while (it.hasNext()) {
                IdAndName platform = (IdAndName) it.next();
                if (platform != null) {
                    if (checkPay) {
                        if (CommonConstants.TV_PAY_CODE.equals(platform.getId())) {
                            return 1;
                        }
                    } else {
                        if (CommonConstants.TV_PLATFROM_CODE.equals(platform.getId())) {
                            return 1;
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 乐见获取4:3图片
     * @param imgs
     * @return
     */
    private String getLeviewPic(Map<String, String> imgs, String mac, String albumId) {
        String url = null;
        if (imgs == null || imgs.size() <= 0) {
            log.info("LeviewAlbumPic_mac: " + mac + "_albumId: " + albumId + "img list is null");
            return url;
        }
        url = imgs.get("960*540");
        if (StringUtil.isBlank(url)) {
            url = imgs.get("1440*810");
            if (StringUtil.isBlank(url)) {
                url = imgs.get("400*225");
            }
        }
        if (StringUtils.isBlank(url)) {
            log.info("LeviewAlbumPic_mac: " + mac + "_albumId: " + albumId + imgs.keySet());
        }
        return url;
    }

    /**
     * 乐见桌面子分类
     * @param sub_categories
     * @return
     */
    private Map<String, String> getSubCategory(List<IdAndName> sub_categories) {
        Map<String, String> category = new HashMap<String, String>();
        if (sub_categories != null && sub_categories.size() != 0) {
            Iterator<IdAndName> it = sub_categories.iterator();
            while (it.hasNext()) {
                IdAndName id_name = it.next();
                if (id_name != null) {
                    category.put(id_name.getId(), id_name.getName());
                }
            }
        }
        return category;
    }

    /**
     * 判断categoryId是否在[电影、电视剧、动漫、纪录片、综艺]中
     * @param categoryId
     * @return
     */
    private boolean isInCategoryType(Integer categoryId) {
        if (categoryId == null) {// 分类ID为空，为FALSE
            return false;
        }
        for (int i : categoryList) {
            if (i == categoryId) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断视频或者专辑数据是否有TV版权
     * @param playPlatform
     * @return
     */
    private boolean hasTvCopyright(String playPlatform) {
        if (StringUtils.isBlank(playPlatform)) {
            return false;
        }
        if (playPlatform.contains("420007")) {
            return true;
        }
        return false;
    }

    private boolean isInGamePids(String[] pids, Integer albumId) {

        if (albumId == null || pids == null) {
            return false;
        }
        for (String s : pids) {
            if (s.equals(albumId.toString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 以片搜片，其实走推荐
     * @return
     */
    public PageResponse<RecomendationVideoByIdDto> searchVideoById(Integer categoryId, Long albumId, String src,
                                                                   Integer num, Long vrsVideoInfoId, CommonParam commonParam) {
        PageResponse<RecomendationVideoByIdDto> response = new PageResponse<RecomendationVideoByIdDto>();
        String logPrefix = "searchVideoById_" + commonParam.getMac() + "_" + albumId + "_" + vrsVideoInfoId + "_"
                + categoryId;
        String errorCode = null;

        if (vrsVideoInfoId == null && albumId == null) {
            errorCode = ErrorCodeConstants.SEARCH_PARAMETER_ERROR;
            log.info(logPrefix + "[errorCode=" + errorCode + "]: illegal parameters.]");
            this.setErrorResponse(response, errorCode, commonParam.getLangcode());

        } else {
            // 调用第三方，获取推荐视频信息
            RecommendByVideoResponse recResponse = this.facadeTpDao.getRecommendationTpDao().recommendVideoById(
                    categoryId, albumId, src, num, commonParam);
            if (recResponse != null) {
                List<RecommendByVideoResponse.Rec> recList = recResponse.getRec();
                List<RecomendationVideoByIdDto> dtoList = new LinkedList<RecomendationVideoByIdDto>();
                if (recList != null) {
                    for (RecommendByVideoResponse.Rec rec : recList) {
                        dtoList.add((RecomendationVideoByIdDto) JumpUtil.bulidJumpObj(
                                new RecomendationVideoByIdDto(rec), DataConstant.DATA_TYPE_ALBUM, null, null));
                    }
                }
                response.setTotalCount(dtoList.size());
                response.setData(dtoList);
            } else {
                errorCode = RecommendationConstants.RECOMMENDATION_ERROR_RESULT_NULL;
                log.info("searchVideoById return null for albumId:" + albumId + " videoId:" + vrsVideoInfoId);
                this.setErrorResponse(response, errorCode, commonParam.getLangcode());
            }
        }

        return response;
    }

    /**
     * 乐搜专辑详情页相关内容
     * @return
     */
    public Response<RecommendationRelationDto> recRelation(Long albumId, Integer categoryId, int num, String uid,
                                                           String pt, String area, String history, CommonParam commonParam) {
        Response<RecommendationRelationDto> response = new Response<RecommendationRelationDto>();
        RecBaseResponse recResponse = null;
        String errCode = null;
        if (albumId == null) {
            errCode = RecommendationConstants.RECOMMENDATION_ERROR_CONDITION_ERROR;
            log.info("searchMaybeLike param is null");
        } else {
            // 调用全网推荐接口
            recResponse = this.facadeTpDao.getRecommendationTpDao().recommend(categoryId, null, albumId, null, null,
                    area, null, null, null, null, history, num, null, null, null, null, null, commonParam);
            if (recResponse == null) {
                errCode = RecommendationConstants.RECOMMENDATION_ERROR_RESULT_NULL;
                log.info("searchMaybeLike return null for " + albumId);
            }

        }
        if (errCode == null) {
            if (recResponse.getRec() != null && recResponse.getRec().size() > 0) {
                List<RecommendDetail> recList = recResponse.getRec();
                List<RecommendationRelationDto.RelationResource> resources = new LinkedList<RecommendationRelationDto.RelationResource>();
                for (RecommendDetail rec : recList) {
                    // isAlbum=1代表专辑
                    if (rec != null && rec.getIsalbum() != null && rec.getIsalbum() == 1) {
                        resources.add((RecommendationRelationDto.RelationResource) JumpUtil.bulidJumpObj(new RecommendationRelationDto.RelationResource(rec),
                                DataConstant.DATA_TYPE_ALBUM, null, null));
                    }
                }

                RecommendationRelationDto relationDto = new RecommendationRelationDto();
                relationDto.setResources(resources);
                response.setData(relationDto);
            }
        } else {
            response = this.setErrorResponse(response, errCode, commonParam.getLangcode() == null ? "zh_cn"
                    : commonParam.getLangcode());
        }

        return response;
    }

    /**
     * 猜你喜欢
     * @param channelId
     * @return
     */
    public PageResponse<RecommendationMaybeLikeDto> recMaybeLike(String pt, String area, int size, String history,
                                                                 String model, Integer channelId, CommonParam commonParam) {
        String logPreFix = "recMaybeLike_" + "model:" + model + "_" + commonParam.getMac() + "_"
                + commonParam.getUserId();
        PageResponse<RecommendationMaybeLikeDto> response = new PageResponse<RecommendationMaybeLikeDto>();
        RecBaseResponse recResponse = null;
        String errCode = null;
        if (StringUtils.isNotBlank(model) && "1".equals(model)) {
            // 儿童搜索个性化推荐
            Map<String, RecBaseResponse> recBaseResponse = this.facadeTpDao.getRecommendationTpDao().getRecommend(size,
                    history, commonParam);
            if (recBaseResponse != null && recBaseResponse.get("rec_1") != null) {
                recResponse = recBaseResponse.get("rec_1");
            } else {
                errCode = RecommendationConstants.RECOMMENDATION_ERROR_RESULT_NULL;
                log.info(logPreFix + " pt:" + pt + " area:" + area + " history:" + history);
            }
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equals(commonParam.getTerminalApplication())) {
            return this.recSarrMaybeLike(response, commonParam);
        } else {
            // 调用全网推荐接口
            recResponse = this.facadeTpDao.getRecommendationTpDao().recommend(null, null, null, null, null, area, null,
                    null, null, null, history, size, null, null, null, null, pt, commonParam);
            if (recResponse == null) {
                errCode = RecommendationConstants.RECOMMENDATION_ERROR_RESULT_NULL;
                log.info(logPreFix + " pt:" + pt + " area:" + area + " history:" + history);
            }
        }
        if (errCode == null) {
            if (recResponse.getRec() != null && recResponse.getRec().size() > 0) {
                List<RecommendDetail> recList = recResponse.getRec();
                List<RecommendationMaybeLikeDto> dtoList = new LinkedList<RecommendationMaybeLikeDto>();
                // AlbumMysqlTable album = null;
                String defaultStream = this.facadeCacheDao.getChannelCacheDao().getChannelDefaultStreamById(
                        String.valueOf(channelId));
                String defaultStreamName = LetvStreamCommonConstants.getStreamName(defaultStream,
                        commonParam.getLangcode());
                for (RecommendDetail rec : recList) {
                    // isAlbum=1代表专辑
                    if (rec != null && null != rec.getPid() && StringUtil.isNotBlank(rec.getVideo_type())
                            && "180001".equals(rec.getVideo_type())
                    /* && rec.getIsalbum() != null && rec.getIsalbum() == 1 */) {
                        /*
                         * 不再二次查询专辑信息
                         * album =
                         * this.facadeService.getAlbumVideoAccess().getAlbum
                         * (rec.getPid(), request.getBc());
                         * if (album != null) {
                         * dtoList.add(new SearchMaybeLikeDto(album));
                         * }
                         */
                        RecommendationMaybeLikeDto dto = new RecommendationMaybeLikeDto();
                        dto.setAid(rec.getPid() + "");
                        dto.setPushFlag(rec.getAlbum_play_platform());
                        dto.setPoster20(rec.getPicurl_st());
                        dto.setModel("search");
                        dto.setName(rec.getPidname());
                        dto.setSubTitle(rec.getSubtitle());
                        dto.setImg400x300(rec.getPicurl());
                        dto.setSrc("1");
                        dto.setCategoryName(MessageUtils.getMessage(
                                CategoryConstants.SEARCH_CATEGORY_MAP.get(rec.getCid()), commonParam.getLangcode()));
                        dto.setImg(rec.getPicurl_st());
                        if (rec.getIsalbum() != null) {
                            Integer dataType = rec.getIsalbum() == 0 ? DataConstant.DATA_TYPE_VIDEO : rec.getIsalbum();
                            dto.setDataType(dataType);
                        }
                        dto.setVid(rec.getVid());
                        dto.setCategoryId(rec.getCid());
                        String score = "";
                        if (!"0.0".equals(rec.getScore()) && "1".equals(rec.getCid())) {
                            score = rec.getScore();
                        }
                        dto.setScore(score);
                        dto.setSubCategoryId(rec.getAlbum_sub_category_code());
                        dto.setNowEpisodes(this.setNowEpisode(rec.getCid(), rec.getIsend(), rec.getVcount(),
                                commonParam));
                        dtoList.add((RecommendationMaybeLikeDto) JumpUtil.bulidJumpObj(dto, dto.getDataType(),
                                defaultStream, defaultStreamName));
                        // dtoList.add(dto);
                    }
                }
                response.setTotalCount(dtoList.size());
                response.setData(dtoList);
            }
        } else {
            response = this.setErrorResponse(response, errCode, commonParam.getLangcode() == null ? "zh_cn"
                    : commonParam.getLangcode());
        }

        return response;
    }

    private PageResponse<RecommendationMaybeLikeDto> recSarrMaybeLike(
            PageResponse<RecommendationMaybeLikeDto> response, CommonParam commonParam) {
        RecommendationLevidiResponse sarrsResponse = null;
        if (LocaleConstant.Wcode.IN.equals(commonParam.getWcode())) {
            sarrsResponse = this.facadeTpDao.getRecommendationTpDao().getLevidiRecommendation("eros",
                    "movies_trailers", null, 12, "rec_2104", 4, null, null, commonParam);
        } else if (LocaleConstant.Wcode.US.equals(commonParam.getWcode())) {
            sarrsResponse = this.facadeTpDao.getRecommendationTpDao().getLevidiRecommendation(null, "tv_home", null,
                    12, "rec_1903", 0, null, null, commonParam);
        }
        if (sarrsResponse == null || sarrsResponse.getData() == null || sarrsResponse.getData().size() == 0) {
            response = this.setErrorResponse(response, RecommendationConstants.RECOMMENDATION_ERROR_RESULT_NULL,
                    commonParam.getLangcode() == null ? "zh_cn" : commonParam.getLangcode());
            return response;
        }
        List<RecommendationLevidiResponse.RecLevidi> recStarrList = sarrsResponse.getData();
        List<RecommendationMaybeLikeDto> dtoList = new LinkedList<RecommendationMaybeLikeDto>();
        ;
        if (recStarrList != null) {
            for (RecommendationLevidiResponse.RecLevidi recStarr : recStarrList) {
                if (recStarr != null) {
                    RecommendationTpResponseLevidiRec sarrRec = recStarr.getData();
                    if (sarrRec != null) {
                        if ("Album".equals(sarrRec.getData_type())) {
                            RecommendationMaybeLikeDto dto = new RecommendationMaybeLikeDto();
                            dto.setAid(sarrRec.getId());
                            dto.setGlobalId(sarrRec.getId());
                            dto.setPushFlag(sarrRec.getPlay_platform());
                            dto.setModel("search");
                            dto.setName(sarrRec.getName());
                            dto.setSubTitle(sarrRec.getSubname());
                            dto.setSrc("5");
                            dto.setCategoryName(MessageUtils.getMessage(
                                    CategoryConstants.SEARCH_CATEGORY_MAP.get(sarrRec.getCid()),
                                    commonParam.getLangcode()));
                            dto.setImg(this.getLevidiOldPic(sarrRec.getPoster(), sarrRec.getMini_poster()));
                            dto.setPoster20(dto.getImg());
                            dto.setDataType(DataConstant.DATA_TYPE_ALBUM);
                            dto.setCategoryId(StringUtil.isBlank(sarrRec.getCid()) ? null : Integer.valueOf(sarrRec
                                    .getCid()));
                            dtoList.add((RecommendationMaybeLikeDto) JumpUtil.bulidJumpObj(dto, dto.getDataType(), "",
                                    ""));
                        }
                    }
                }
            }
        }
        response.setTotalCount(dtoList.size());
        response.setData(dtoList);
        return response;
    }

    private String setNowEpisode(Integer categroy, Integer isEnd, Integer nowEpisodes, CommonParam commonParam) {
        String nowEpisodesTxt = "";
        if (categroy != null && nowEpisodes != null && nowEpisodes != 0) {
            if (VideoConstants.Category.TV == categroy || VideoConstants.Category.CARTOON == categroy) {
                Object[] params = { nowEpisodes };
                if (isEnd != null && isEnd == 0) { // 未完结
                    nowEpisodesTxt = MessageUtils.getMessage(
                            ChannelConstants.CHANNEL_ALBUM_UPDATED_TO_NOWEPISODES_NORMAL, commonParam.getLangcode(),
                            params);
                } else {
                    nowEpisodesTxt = MessageUtils.getMessage(ChannelConstants.CHANNEL_ALBUM_IS_END,
                            commonParam.getLangcode(), params);
                }
            } else if (VideoConstants.Category.VARIETY == categroy) {

                if (String.valueOf(nowEpisodes).length() == 8) {
                    Object[] params = { this.getFollowNum(nowEpisodes, "yyyy-MM-dd") };
                    nowEpisodesTxt = MessageUtils.getMessage(ChannelConstants.CHANNEL_ALBUM_UPDATED_TO_NOWISSUE_NORMAL,
                            commonParam.getLangcode(), params);
                } else {
                    Object[] params = { nowEpisodes };
                    if (isEnd != null && "0".equals(isEnd)) { // 未完结
                        nowEpisodesTxt = MessageUtils.getMessage(
                                ChannelConstants.CHANNEL_ALBUM_UPDATED_TO_NOWEPISODES_NORMAL,
                                commonParam.getLangcode(), params);
                    } else {
                        nowEpisodesTxt = MessageUtils.getMessage(ChannelConstants.CHANNEL_ALBUM_IS_END,
                                commonParam.getLangcode(), params);
                    }
                }
            }
        }
        return nowEpisodesTxt;
    }

    private String getFollowNum(Integer Num, String format) {
        try {
            if (Num == null) {
                return "";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat df1 = new SimpleDateFormat(format);
            Date followDate = sdf.parse(Num.toString());
            return df1.format(followDate);
        } catch (ParseException e) {
            log.error("getFollowNum_format " + format + " error Num" + Num + e.getMessage(), e);
        }
        return "";
    }

    /**
     * 设置接口处理失败时的返回值
     * @param response
     *            返回的BaseResponse
     * @param errCode
     *            错误代码
     * @param langcode
     *            用户语言环境
     */
    private <T extends BaseResponse> T setErrorResponse(T response, String errCode, String langcode) {
        if (response != null) {
            response.setResultStatus(0);
            response.setErrCode(errCode);
            response.setErrMsg(MessageUtils.getMessage(errCode, langcode));
        }
        return response;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public PageResponse getLeviewData(Integer num, String rcType, CommonParam commonParam) {
        String logPrefix = "getLeViewData_" + commonParam.getMac();
        PageResponse response = new PageResponse<Recommendation>();
        // area定义
        if (RecommendationConstants.LEVIEW_DATA_SOURCE_REC.equals(rcType)) {
            String area = getLeviewArea(commonParam);
            if (StringUtil.isBlank(area)) {
                return response;
            }

            List<Recommendation> rec_data = this.getLeviewDataFromRec(area, num, logPrefix, commonParam);
            // 乐见缓存逻辑
            if (rec_data == null) {// 走缓存
                rec_data = (List) this.facadeCacheDao.getRecommendationCacheDao().getLeviewCache(rcType, commonParam);
            } else {
                if (rec_data != null && rec_data.size() > 0) { // 客户端一次请求30条但是推荐返回可能小于30，故做此判断
                    List<BaseData> data_list = rec_data.get(0).getDataList();
                    if (data_list != null && data_list.size() >= 20) {
                        this.facadeCacheDao.getRecommendationCacheDao().setLeviewCache(rcType, rec_data, commonParam);
                    }
                }
            }
            response.setData(rec_data);
        } else {
            response = new PageResponse<Recommendation>();
            response.setData(this.getLeviewDataFromCms(commonParam, logPrefix));
        }
        return response;
    }

    /**
     * 判断leview的area
     * @param commonParam
     * @return
     */
    private String getLeviewArea(CommonParam commonParam) {
        String area = "";
        if (commonParam == null || commonParam.getTerminalApplication() == null) {
            return area;
        }
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIEW.equalsIgnoreCase(commonParam.getTerminalApplication())) {
            area = "rec_0603";
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_CIBN_LEVIEW.equalsIgnoreCase(commonParam
                .getTerminalApplication())) {
            area = "rec_0613";
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_WASU_LEVIEW.equalsIgnoreCase(commonParam
                .getTerminalApplication())) {
            area = "rec_0615";
        }
        return area;
    }
}
