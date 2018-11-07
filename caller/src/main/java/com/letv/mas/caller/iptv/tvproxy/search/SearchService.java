package com.letv.mas.caller.iptv.tvproxy.search;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.*;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageCommonResponse;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageResponse;
import com.letv.mas.caller.iptv.tvproxy.common.constant.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoAccess;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.ItvLabelResource;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.SearchConditionMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.GetSubjectTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockContentTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.SearchTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.SuggestTpResponse.Suggest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.WebsiteTpResponse.WebsiteDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.TotalCountStatTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.VideoTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.BaseResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.SessionCache;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.service.MutilLanguageService;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import com.letv.mas.caller.iptv.tvproxy.search.constant.SearchTpConstant;
import com.letv.mas.caller.iptv.tvproxy.search.model.bean.bo.SearchPageResponse;
import com.letv.mas.caller.iptv.tvproxy.search.model.bean.bo.SearchPageResponse.DataReport;
import com.letv.mas.caller.iptv.tvproxy.search.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.search.model.dto.AlbumDetailDto.AlbumDetailVideoInfoDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.StarWorksTpResponse.VideoList;
import com.letv.mas.caller.iptv.tvproxy.search.model.dto.SearchResultsDto.VientianeDto;
import com.letv.mas.caller.iptv.tvproxy.search.model.dto.SearchStarWorksDto.StarWorks;
import com.letv.mas.caller.iptv.tvproxy.video.service.VideoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import search2.extractor.ExtractData;
import search2.extractor.RequestSource;
import search2.proto.PanoSearchStat;
import search2.proto.StatInfo;
import serving.DataType;
import serving.GenericServingResponse;
import serving.ResultDocInfo;
import serving.ServingResult;

import java.util.*;

import static com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.RankingListTpResponse.*;

@Component(value = "v2.SearchService")
@SuppressWarnings("all")
public class SearchService extends BaseService {

    private final static Logger log = LoggerFactory.getLogger(SearchService.class);

    @Autowired
    private SearchTpDao searchTpDao;

    @Autowired
    MutilLanguageService mutilLanguageService;
    
    @Autowired
    AlbumVideoAccess albumVideoAccess;

    @Autowired
    VideoService videoService;

    /**
     * 复合检索
     * @param channelId
     * @param searchRequest
     *            检索条件
     * @param locale
     *            检索语言环境
     * @return
     */
    public GenericServingResponse searchTypes2(String from, String dt, Integer page, Integer pageSize,
                                                         String word, String categoryId, String ph, String searchContent, String id, String splatid,
                                                         Integer channelId, String leIds, String productId, String pushChild, String repo_type, String pcp,
                                                         CommonParam commonParam) {
        GenericServingResponse result = searchTpDao.search(from, dt, page, pageSize, word,
                categoryId, null, ph, "1", 1, null, searchContent, splatid, leIds, null, repo_type, null, null, null,
                null, null, null, null, productId, pushChild, pcp, commonParam);
        return result;
    }

    @Autowired(required = false)
    private SessionCache sessionCache;

    // 二级标签检索的缓存时间点
    private static Map<String, Long> searchMenuRetrieveStartTime = new HashMap<String, Long>();
    // 二级标签 Cms数据的缓存时间点
    private static Map<String, Long> searchMenuCmsStartTime = new HashMap<String, Long>();
    // 二级标签 猜你喜欢 缓存时间点
    private static Map<String, Long> searchMenuUserLikeStartTime = new HashMap<String, Long>();
    // 二级标签 labelId数据缓存时间点
    private static Map<String, Long> searchMenuByLabelIdStartTime = new HashMap<String, Long>();
    // 二级标签 label专题 缓存时间点
    private static Map<String, Long> searchMenuLabelStartTime = new HashMap<String, Long>();
    // 二级标签 专辑 缓存时间点
    private static Map<String, Long> searchMenuByAlbumIdStartTime = new HashMap<String, Long>();
    // 二级标签 访问searchTypes接口缓存时间点
    private static Map<String, Long> SearchTypesStartTime = new HashMap<String, Long>();

    /**
     * 获取搜索条件
     * @param channelId
     * @return
     */
    public PageResponse<SearchCondition> getSearchConditionList(Integer channelId, CommonParam commonParam) {
        List<SearchCondition> searchConditionList = new LinkedList<SearchCondition>();
        PageResponse<SearchCondition> response = new PageResponse<SearchCondition>(searchConditionList);

        if ((channelId != null) && (channelId > 0)) {
            Map<Integer, SearchCondition> sortedScMap = new TreeMap<Integer, SearchCondition>();
            Map<Integer, List<SearchCondition.SearchConditionItem>> sortedScItemMap = new TreeMap<Integer, List<SearchCondition.SearchConditionItem>>();

            List<SearchConditionMysqlTable> searchConditionMysqlTableList = this.facadeMysqlDao
                    .getSearchConditionMysqlDao().getList(channelId);
            String name = null;
            for (SearchConditionMysqlTable searchConditionMysqlTable : searchConditionMysqlTableList) {
                int isScItem = searchConditionMysqlTable.getIs_sc_item();
                if (isScItem == SearchConstant.IS_SEARCHCONDITION_ITEM) {// 检索项
                    Integer scType = searchConditionMysqlTable.getSc_type();
                    List<SearchCondition.SearchConditionItem> childScList = sortedScItemMap.get(scType);
                    if (childScList == null) {
                        childScList = new LinkedList<SearchCondition.SearchConditionItem>();
                        sortedScItemMap.put(scType, childScList);
                    }

                    SearchCondition.SearchConditionItem childSc = new SearchCondition.SearchConditionItem();
                    childSc.setScType(searchConditionMysqlTable.getSc_type());
                    childSc.setScValue(searchConditionMysqlTable.getSc_value());
                    name = mutilLanguageService.getMessage(
                            SearchConstant.ITV_SEARCH_CONDITION_TABLE, searchConditionMysqlTable.getId(),
                            SearchConstant.ITV_SEARCH_CONDITION_SC_NAME, commonParam.getLangcode());
                    if (name == null) {
                        name = searchConditionMysqlTable.getSc_name();
                    }
                    childSc.setScName(name);
                    childSc.setOrderNum(searchConditionMysqlTable.getOrder_num());
                    childSc.setCategoryId(searchConditionMysqlTable.getCategory_id());

                    childSc.setStatus(searchConditionMysqlTable.getStatus());
                    childScList.add(childSc);
                } else {// 检索项组
                    Integer scType = searchConditionMysqlTable.getSc_type();
                    SearchCondition childSc = sortedScMap.get(scType);
                    if (childSc == null) {
                        childSc = new SearchCondition();
                        sortedScMap.put(scType, childSc);
                    }

                    childSc.setScType(scType);
                    name = mutilLanguageService.getMessage(
                            SearchConstant.ITV_SEARCH_CONDITION_TABLE, searchConditionMysqlTable.getId(),
                            SearchConstant.ITV_SEARCH_CONDITION_SC_NAME, commonParam.getLangcode());
                    if (name == null) {
                        name = searchConditionMysqlTable.getSc_name();
                    }
                    childSc.setScName(name);
                    childSc.setOrderNum(searchConditionMysqlTable.getOrder_num());
                    childSc.setCategoryId(searchConditionMysqlTable.getCategory_id());
                    childSc.setStatus(searchConditionMysqlTable.getStatus());
                }
            }

            for (Integer scType : sortedScMap.keySet()) {
                SearchCondition chdSc = sortedScMap.get(scType);
                List<SearchCondition.SearchConditionItem> itemList = sortedScItemMap.get(scType);
                if (itemList != null) {
                    Collections.sort(itemList);
                }
                chdSc.setItems(itemList);

                searchConditionList.add(chdSc);
            }
        }
        Collections.sort(searchConditionList);

        return response;
    }
    
    /**
     * 处理le上的addon的排序:searchContent参数无排序则加上排序
     * @param searchContent
     *            检索条件
     * @param productId
     *            addon的会员id
     * @param commonParam
     * @return
     */
    private String dealSearchSort4Addon(String searchContent, String productId, CommonParam commonParam) {
        StringBuilder sc = new StringBuilder();
        if (StringUtil.isNotBlank(searchContent)) {
            sc.append(searchContent);
        }
        /*
         * le addon列表加上排序条件
         */
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())
                && StringUtil.isNotBlank(productId)) { // le的addon列表
            String searchSort = ApplicationUtils.get(ApplicationConstants.SEARCH_ADDON_SEARCH_CONTENT_SORT); // 读取配置的检索排序
            if (StringUtil.isNotBlank(searchSort)) {
                if (StringUtil.isBlank(searchContent)) { // 搜索条件为空
                    sc.append("20:").append(searchSort);
                } else if (!searchContent.contains("20:")) { // 搜索条件不为空,但不包含排序条件
                    sc.append("&20:").append(searchSort);
                }
            }
        }
        return sc.toString();
    }

    private List<AlbumInfoDto> parseSearchMixResult(GenericServingResponse result, String defaultStream,
                                                    String defaultStreamName, CommonParam commonParam) {
        List<AlbumInfoDto> videoList = new LinkedList<AlbumInfoDto>();
        if (result != null && result.getSearch_response() != null
                && !CollectionUtils.isEmpty(result.getSearch_response().getServing_result_list())) {
            for (ServingResult servingResult : result.getSearch_response().getServing_result_list()) {
                if (servingResult != null && servingResult.getData_type() != null) {
                    if (SearchConstant.SEARCH_DATA_TYPE_ALBUM == servingResult.getData_type().getValue()) {
                        AlbumMysqlTable albumInfo = albumVideoAccess.getAlbum(
                                Long.parseLong(servingResult.getDoc_info().getId()), commonParam);
                        if (albumInfo != null) {
                            AlbumInfoDto albumInfoDto = (AlbumInfoDto) JumpUtil.bulidJumpObj(new AlbumInfoDto(
                                            albumInfo, commonParam), DataConstant.DATA_TYPE_ALBUM, defaultStream,
                                    defaultStreamName);
                            albumInfoDto.setDataType(DataConstant.DATA_TYPE_ALBUM);
                            videoList.add(albumInfoDto);
                        }
                    } else if (SearchConstant.SEARCH_DATA_TYPE_VIDEO == servingResult.getData_type().getValue()) {
                        VideoMysqlTable videoInfo = albumVideoAccess.getVideo(
                                Long.parseLong(servingResult.getDoc_info().getId()), commonParam);
                        if (videoInfo != null) {
                            AlbumInfoDto albumInfoDto = (AlbumInfoDto) JumpUtil.bulidJumpObj(new ResourceInfoDto(
                                            videoInfo, commonParam.getLangcode(), commonParam), DataConstant.DATA_TYPE_VIDEO,
                                    defaultStream, defaultStreamName);
                            albumInfoDto.setDataType(DataConstant.DATA_TYPE_VIDEO);
                            videoList.add(albumInfoDto);
                        }
                    } else if (SearchConstant.SEARCH_DATA_TYPE_SUBJECT == servingResult.getData_type().getValue()) {
                        // 体育专题 直接跳体育app 这里不用设置付费字段，albumInfoDto构造函数会设为0
                        SearchData searchData = new SearchData();
                        ResultDocInfo resultDocInfo = servingResult.getDoc_info();
                        if (resultDocInfo == null) {
                            continue;
                        }
                        searchData.setId(Long.parseLong(resultDocInfo.getId()));
                        searchData.setName_cn(resultDocInfo.getName());
                        searchData.setDescription(resultDocInfo.getDescription());
                        searchData.setTag(resultDocInfo.getSubject_attribute().getDisplay_tag());
                        if (!StringUtil.isBlank(resultDocInfo.getCategory())) {
                            searchData.setCategory(Integer.parseInt(resultDocInfo.getCategory()));
                        }
                        searchData.setCategory_name(resultDocInfo.getCategory_name());
                        if (resultDocInfo.getData_type() != null) {
                            searchData.setData_type(resultDocInfo.getData_type().getValue());
                        }
                        if (resultDocInfo.getSubject_attribute() != null) {
                            searchData.setImages(resultDocInfo.getSubject_attribute().getImages());
                            // 2015-07-21扩展专题模板需求
                            searchData.setTemplateType(SubjectConstant.getSubjectTypeFromTemplate(resultDocInfo
                                    .getSubject_attribute().getTv_template()));
                            searchData.setSub_category(resultDocInfo.getSubject_attribute().getSub_category());
                        }
                        AlbumInfoDto albumInfoDto = (AlbumInfoDto) JumpUtil.bulidJumpObj(new AlbumInfoDto(searchData),
                                DataConstant.DATA_TYPE_SUBJECT, defaultStream, defaultStreamName);
                        albumInfoDto.setDataType(DataConstant.DATA_TYPE_SUBJECT);
                        videoList.add(albumInfoDto);
                    }
                }
            }
        }
        return videoList;

    }

    private List<AlbumInfoDto> parseSearchMixResult2(GenericServingResponse result, String defaultStream,
                                                     String defaultStreamName, CommonParam commonParam) {
        List<AlbumInfoDto> videoList = new LinkedList<AlbumInfoDto>();
        if (result != null && result.getSearch_response() != null
                && !CollectionUtils.isEmpty(result.getSearch_response().getServing_result_list())) {
            for (ServingResult servingResult : result.getSearch_response().getServing_result_list()) {
                if (servingResult != null && servingResult.getData_type() != null) {
                    AlbumInfoDto albumInfoDto = null;
                    if (SearchConstant.SEARCH_DATA_TYPE_ALBUM == servingResult.getData_type().getValue()) {
                        albumInfoDto = (AlbumInfoDto) JumpUtil.bulidJumpObj(
                                new AlbumInfoDto(servingResult, commonParam), DataConstant.DATA_TYPE_ALBUM,
                                defaultStream, defaultStreamName);
                        albumInfoDto.setDataType(DataConstant.DATA_TYPE_ALBUM);
                    } else if (SearchConstant.SEARCH_DATA_TYPE_VIDEO == servingResult.getData_type().getValue()) {
                        albumInfoDto = (AlbumInfoDto) JumpUtil.bulidJumpObj(new ResourceInfoDto(servingResult,
                                commonParam), DataConstant.DATA_TYPE_VIDEO, defaultStream, defaultStreamName);
                        albumInfoDto.setDataType(DataConstant.DATA_TYPE_VIDEO);
                    } else if (SearchConstant.SEARCH_DATA_TYPE_SUBJECT == servingResult.getData_type().getValue()) {
                        // 体育专题 直接跳体育app 这里不用设置付费字段，albumInfoDto构造函数会设为0
                        SearchData searchData = new SearchData();
                        ResultDocInfo resultDocInfo = servingResult.getDoc_info();
                        if (resultDocInfo == null) {
                            continue;
                        }
                        searchData.setId(Long.parseLong(resultDocInfo.getId()));
                        searchData.setName_cn(resultDocInfo.getName());
                        searchData.setDescription(resultDocInfo.getDescription());
                        searchData.setTag(resultDocInfo.getSubject_attribute().getDisplay_tag());
                        if (!StringUtil.isBlank(resultDocInfo.getCategory())) {
                            searchData.setCategory(Integer.parseInt(resultDocInfo.getCategory()));
                        }
                        searchData.setCategory_name(resultDocInfo.getCategory_name());
                        if (resultDocInfo.getData_type() != null) {
                            searchData.setData_type(resultDocInfo.getData_type().getValue());
                        }
                        if (resultDocInfo.getSubject_attribute() != null) {
                            searchData.setImages(resultDocInfo.getSubject_attribute().getImages());
                            // 2015-07-21扩展专题模板需求
                            searchData.setTemplateType(SubjectConstant.getSubjectTypeFromTemplate(resultDocInfo
                                    .getSubject_attribute().getTv_template()));
                            searchData.setSub_category(resultDocInfo.getSubject_attribute().getSub_category());
                        }
                        albumInfoDto = (AlbumInfoDto) JumpUtil.bulidJumpObj(new AlbumInfoDto(searchData),
                                DataConstant.DATA_TYPE_SUBJECT, defaultStream, defaultStreamName);
                        albumInfoDto.setDataType(DataConstant.DATA_TYPE_SUBJECT);
                    }
                    if (albumInfoDto != null) {
                        videoList.add(albumInfoDto);
                    }
                }
            }
        }
        return videoList;

    }

    /**
     * 频道二级标签页检索
     * @param channelId
     * @param searchRequest
     *            检索条件请求
     * @param locale
     *            检索语言环境
     * @return
     */
    public PageCommonResponse<AlbumInfoDto> searchMenu(Integer searchType, Integer newCategoryId, Integer page,
                                                       Integer pageSize, String searchValue, Integer orderType, String from, String dt, String ph, String splatid,
                                                       Integer channelId, String leIds, String pcp, CommonParam commonParam) {
        String defaultStream = this.facadeCacheDao.getChannelCacheDao().getChannelDefaultStreamById(
                String.valueOf(channelId));
        String defaultStreamName = LetvStreamCommonConstants.getStreamName(defaultStream, commonParam.getLangcode());
        PageControl<AlbumInfoDto> pageControl = new PageControl<AlbumInfoDto>(pageSize, page);
        PageCommonResponse<AlbumInfoDto> response = new PageCommonResponse<AlbumInfoDto>(pageControl);

        // 2:标签分类(从数据库查询)
        // TODO ligang 20151218
        // 下一步这样，下面这些ifelse拆分接口，检索的合并到检索，其他的该删的删。这些接口保证响应格式一样即可
        if (SearchTpConstant.SEARCH_BY_LABEL == searchType) {
            Integer categoryId = CategoryIdConstant.getOldCategory(newCategoryId);
            Integer broadcastId = commonParam.getBroadcastId();
            Long labelId = null;
            try {
                // labelId = Long.parseLong(searchValue, 10);
                labelId = StringUtil.toLong(searchValue);
            } catch (Exception e) {
                return this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_CONDITION_ERROR,
                        commonParam.getWcode());
            }
            // 获取总数量
            int count = 0;
            try {
                count = this.facadeMysqlDao.getAlbumMysqlDao().getCountByLabelId(categoryId, labelId, broadcastId);
            } catch (Exception e) {
                count = -1;
            }

            if (count > 0 || count == -1) {
                // 检索视频
                List<AlbumInfoDto> albumList = this.searchVideoByLabel(newCategoryId, searchValue, page, pageSize,
                        defaultStream, defaultStreamName, commonParam);
                pageControl = new PageControl<AlbumInfoDto>(pageSize, page, count);
                pageControl.setList(albumList);
                response = new PageCommonResponse<AlbumInfoDto>(pageControl);
            } else {
                return this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_RESULT_NULL, commonParam.getWcode());
            }

            // 11:动漫排行 12:电影排行 13:电视剧排行
        } else if (SearchTpConstant.CARTONGRANKLIST == searchType || SearchTpConstant.TVRANKLIST == searchType
                || SearchTpConstant.FILMRANKLIST == searchType) {
            // 获取排行数据
            RankingListTpResponse rankingListTpResponse = this.facadeCacheDao.getSearchCacheDao().getRankingList(
                    searchValue);

            if (rankingListTpResponse == null) {
                rankingListTpResponse = this.facadeTpDao.getSearchTpDao().getRankingList(searchValue, commonParam);
                if (rankingListTpResponse != null) {
                    this.facadeCacheDao.getSearchCacheDao().setRankingList(searchValue, rankingListTpResponse);
                }
            }

            if (rankingListTpResponse == null || CollectionUtils.isEmpty(rankingListTpResponse.getRankingList())) {
                return this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_RESULT_NULL, commonParam.getWcode());
            }

            List<AlbumInfoDto> dtoList = new ArrayList<AlbumInfoDto>();
            for (RankingList rank : rankingListTpResponse.getRankingList()) {
                AlbumMysqlTable album = albumVideoAccess.getAlbum(rank.getVrsAlbumId(),
                        commonParam);
                if (album != null) {
                    dtoList.add((AlbumInfoDto) JumpUtil.bulidJumpObj(new AlbumInfoDto(album, commonParam),
                            DataConstant.DATA_TYPE_ALBUM, defaultStream, defaultStreamName));
                }
            }

            // 排行获取的是全部数据，需要根据分页信息获取对应的数据
            response = this.parsePage(dtoList, page, pageSize);

            // 亲子，体育，娱乐，综艺，音乐，纪录片排行
        } else if (SearchTpConstant.QZRANKLIST == searchType || SearchTpConstant.SPORTRANKLIST == searchType
                || SearchTpConstant.ENTRANKLIST == searchType || SearchTpConstant.VARRANKLIST == searchType
                || SearchTpConstant.MUSICRANKLIST == searchType || SearchTpConstant.DOCRANKLIST == searchType) {
            // 获取排行数据
            RankingListTpResponse rankingListTpResponse = this.facadeCacheDao.getSearchCacheDao().getRankingList(
                    searchValue);

            if (rankingListTpResponse == null) {
                rankingListTpResponse = this.facadeTpDao.getSearchTpDao().getRankingList(searchValue, commonParam);
                if (rankingListTpResponse != null) {
                    this.facadeCacheDao.getSearchCacheDao().setRankingList(searchValue, rankingListTpResponse);
                }
            }

            if (rankingListTpResponse == null || CollectionUtils.isEmpty(rankingListTpResponse.getRankingList())) {
                return this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_RESULT_NULL, commonParam.getWcode());
            }

            List<AlbumInfoDto> dtoList = new ArrayList<AlbumInfoDto>();
            for (RankingList rank : rankingListTpResponse.getRankingList()) {
                VideoMysqlTable video = albumVideoAccess.getVideo(rank.getVrsAlbumId(),
                        commonParam);
                if (video != null) {
                    dtoList.add((ResourceInfoDto) JumpUtil.bulidJumpObj(
                            new ResourceInfoDto(video, commonParam.getLangcode(), commonParam),
                            DataConstant.DATA_TYPE_VIDEO, defaultStream, defaultStreamName));
                }
            }

            // 排行获取的是全部数据，需要根据分页信息获取对应的数据
            response = this.parsePage(dtoList, page, pageSize);

            // CMS数据
        } else if (SearchTpConstant.SEARCH_BY_MMS_CMS_DATA == searchType) {
            // 获取CMS板块数据
            List<AlbumInfoDto> albumList = this.getCmsData(searchValue, defaultStream, defaultStreamName, commonParam);
            if (CollectionUtils.isEmpty(albumList)) {
                return this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_RESULT_NULL, commonParam.getWcode());
            }
            // CMS获取的是全部数据，需要根据分页信息获取对应的数据
            response = this.parsePage(albumList, page, pageSize);

            // label专题数据
        } else if (SearchTpConstant.SEARCH_BY_ZHUANTI_DATA == searchType) {
            response = this.getLabelDataInfo(page, pageSize, newCategoryId, searchValue, commonParam);
            if (response == null || response.getResultStatus() == 0) {
                return response;
            }

            // 指定专辑id自动抓取视频列表
        } else if (SearchTpConstant.SEARCH_BY_PID == searchType) {
            Integer vtype = CommonConstants.VIDEO_REQUEST_TYPE.ZHENGPIAN.getV();
            Integer order = CommonConstants.DESC;
            Integer porder = 1;
            if (orderType != null && orderType == 1) {
                order = CommonConstants.ASC;
                porder = -1;
            }
            Long pids[] = null;
            try {
                if (StringUtil.isBlank(searchValue)) {
                    return this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_CONDITION_ERROR,
                            commonParam.getWcode());
                }
                String[] pidStrs = searchValue.split(",");
                pids = new Long[pidStrs.length];
                for (int i = 0; i < pidStrs.length; i++) {
                    pids[i] = Long.parseLong(pidStrs[i]);
                }
                if (pids.length == 0) {
                    return this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_CONDITION_ERROR,
                            commonParam.getWcode());
                }
            } catch (Exception e) {
                return this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_CONDITION_ERROR,
                        commonParam.getWcode());
            }

            long count = 0;
            List<VideoMysqlTable> videoList = null;
            try {
                // count =
                // this.facadeMysqlDao.getVideoMysqlDao().getAlbumSeriesCountByPids(pids,
                // commonParam.getBroadcastId(), vtype);
                for (Long pid : pids) {
                    Integer total = albumVideoAccess.getVideoTotalNum(pid,
                            VideoTpConstant.QUERY_TYPE_POSITIVE, commonParam);
                    if (total != null) {
                        count = count + total;
                    }
                }
                if (count > 0) {
                    int start = (page - 1) * pageSize;
                    int end = page * pageSize;
                    // videoList =
                    // this.facadeMysqlDao.getVideoMysqlDao().getVideoListByPids(pids,
                    // start, pageSize,
                    // commonParam.getBroadcastId(), order, vtype);
                    videoList = albumVideoAccess.getVideoRange(pids[0],
                            VideoTpConstant.QUERY_TYPE_POSITIVE, porder, start, end, commonParam, 5);
                }
            } catch (Exception e) {
                // 用这两个变量标志数据库挂掉
                videoList = null;
                count = -1;
            }
            if (page == 1) {
                StringBuilder str = new StringBuilder();
                for (long temp : pids) {
                    str.append(temp);
                }
                String key = CacheContentConstants.SEARCH_MENU_BYALBUMID_id + str.toString() + "_" + page;
                Long startTime = searchMenuByAlbumIdStartTime.get(key);
                if (videoList == null && count == -1) {
                    videoList = this.facadeCacheDao.getSearchCacheDao().getSearchMenuByAlbumId(str.toString(), page);
                    if (videoList != null) {
                        count = videoList.size();
                    }
                } else if ((startTime == null || System.currentTimeMillis() - startTime > 300 * 1000)
                        && !CollectionUtils.isEmpty(videoList)) {
                    searchMenuByAlbumIdStartTime.put(key, System.currentTimeMillis());
                    this.facadeCacheDao.getSearchCacheDao().setSearchMenuByAlbumId(str.toString(), page, videoList);
                }
            }

            if (count <= 0) {
                return this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_RESULT_NULL, commonParam.getWcode());
            } else {
                if (CollectionUtils.isEmpty(videoList)) {
                    return this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_RESULT_NULL,
                            commonParam.getWcode());
                }

                List<AlbumInfoDto> dtoList = new LinkedList<AlbumInfoDto>();
                for (VideoMysqlTable video : videoList) {
                    if (video != null) {
                        dtoList.add((ResourceInfoDto) JumpUtil.bulidJumpObj(
                                new ResourceInfoDto(video, commonParam.getLangcode(), commonParam),
                                DataConstant.DATA_TYPE_VIDEO, defaultStream, defaultStreamName));
                    }
                }

                pageControl = new PageControl<AlbumInfoDto>(pageSize, page, (int) count);
                pageControl.setList(dtoList);
                response = new PageCommonResponse<AlbumInfoDto>(pageControl);
            }

        } else {
            String searchContent = searchType + ":" + searchValue;
            if (orderType != null) {
                searchContent = searchContent + ";" + SearchTpConstant.SEARCH_BY_ORDER + ":" + orderType;
            }

            GenericServingResponse result = this.facadeTpDao.getSearchTpDao().search(from, dt, page, pageSize, null,
                    newCategoryId == null ? null : newCategoryId.toString(), null, ph, "1", 1, null, searchContent,
                    splatid, leIds, null, null, null, null, null, null, null, null, null, null, null, pcp, commonParam);

            // 只缓存第一页
            if (page == 1) {
                String key = CacheContentConstants.SEARCH_MENU_RETRIEVE_ID + newCategoryId + "_" + searchContent + "_"
                        + page;
                Long startTime = searchMenuRetrieveStartTime.get(key);
                if (result == null) {
                    result = this.facadeCacheDao.getSearchCacheDao().getSearchMenuRetrieve(newCategoryId,
                            searchContent, page);
                } else if ((startTime == null || System.currentTimeMillis() - startTime > 300 * 1000)
                        && result.getSearch_response() != null
                        && !CollectionUtils.isEmpty(result.getSearch_response().getServing_result_list())) { // 5分钟更新一次
                    searchMenuRetrieveStartTime.put(key, System.currentTimeMillis());
                    // TODO: kv数据近10M，待优化！
                    this.facadeCacheDao.getSearchCacheDao().setSearchMenuRetrieve(newCategoryId, searchContent, page,
                            result);
                }
            }

            if (result == null || result.getSearch_response() == null) {
                return this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_RESULT_NULL, commonParam.getWcode());
            }
            List<AlbumInfoDto> albumList = this.parseSearchMixResult2(result, defaultStream, defaultStreamName,
                    commonParam);
            pageControl = new PageControl<AlbumInfoDto>(pageSize, page, result.getSearch_response().getData_count());
            pageControl.setList(albumList);
            response = new PageCommonResponse<AlbumInfoDto>(pageControl);
        }

        // 检索条件中可能会拼categoryId, 例如searchContent="-1:2;3:50041";
        try {
            if ((newCategoryId == null || newCategoryId == 0) && searchValue != null) {
                String[] params = searchValue.split(";");
                if (params != null) {
                    for (String str : params) {
                        String[] sc = str.split(":");
                        if ("-1".equals(sc[0])) {
                            newCategoryId = Integer.parseInt(sc[1]);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {

        }

        if ("CN".equalsIgnoreCase(commonParam.getWcode()) && response != null
                && !CollectionUtils.isEmpty(response.getItems()) && newCategoryId != null) {
            this.changeSubTitle(response.getItems(), newCategoryId);
        }

        return response;
    }

    /**
     * 通过标签，从库中搜索内容
     * @param defaultStream
     * @param defaultStreamName
     * @param searchRequest
     * @param locale
     * @return
     */
    private List<AlbumInfoDto> searchVideoByLabel(Integer newCategoryId, String searchValue, Integer page,
                                                  Integer pageSize, String defaultStream, String defaultStreamName, CommonParam commonParam) {
        List<AlbumInfoDto> videoList = null;
        // searchRequest中存储的是新categoryId
        Integer categoryId = CategoryIdConstant.getOldCategory(newCategoryId);
        Integer broadcastId = commonParam.getBroadcastId();
        Long labelId = null;
        try {
            labelId = Long.parseLong(searchValue, 10);
        } catch (Exception e) {
            return null;
        }
        int begin = (page - 1) * pageSize;

        List<ItvLabelResource> resList = null;
        try {
            resList = this.facadeMysqlDao.getAlbumMysqlDao().getListByLabelId(categoryId, labelId, broadcastId, begin,
                    pageSize);
        } catch (Exception e) {
            resList = null;
        }
        if (page == 1) {
            String key = CacheContentConstants.SEARCH_MENU_BYLABELID_ID + categoryId + "_" + searchValue + "_" + page;
            Long startTime = searchMenuByLabelIdStartTime.get(key);
            if (resList == null) {
                resList = this.facadeCacheDao.getSearchCacheDao().getSearchMenuByLabelId(categoryId, searchValue, page);
            } else if ((startTime == null || System.currentTimeMillis() - startTime > 300 * 1000)
                    && !CollectionUtils.isEmpty(resList)) {
                searchMenuByLabelIdStartTime.put(key, System.currentTimeMillis());
                this.facadeCacheDao.getSearchCacheDao().setSearchMenuByLabelId(categoryId, searchValue, page, resList);
            }
        }

        if (resList != null && resList.size() > 0) {
            videoList = new LinkedList<AlbumInfoDto>();
            ResourceInfoDto resourceInfo = null;
            AlbumMysqlTable album = null;
            VideoMysqlTable video = null;
            // 获取检索视频列表
            for (ItvLabelResource itvLabelResource : resList) {
                if (itvLabelResource.getResourceType() == 0) { // 专辑数据
                    album = albumVideoAccess.getAlbum(
                            itvLabelResource.getItvAlbumId().longValue(), commonParam);
                    if (album == null) {
                        this.log.warn("searchVideoByLabel: get Album [" + itvLabelResource.getItvAlbumId()
                                + "] failure, please check ther album");
                        continue;
                    }
                    resourceInfo = new ResourceInfoDto(album, commonParam);
                    resourceInfo.setResourceType(itvLabelResource.getResourceType());
                    videoList.add((ResourceInfoDto) JumpUtil.bulidJumpObj(resourceInfo, DataConstant.DATA_TYPE_ALBUM,
                            defaultStream, defaultStreamName));
                } else { // 视频数据
                    video = albumVideoAccess.getVideo(
                            itvLabelResource.getVideoInfoId().longValue(), commonParam);
                    if (video == null) {
                        this.log.warn("searchVideoByLabel: get video [" + itvLabelResource.getVideoInfoId()
                                + "] failure, please check ther video");
                        continue;
                    }
                    resourceInfo = new ResourceInfoDto();
                    resourceInfo.setVrsVideoinfoId(itvLabelResource.getVideoInfoId().longValue());
                    resourceInfo.setImg_vertical_300x400(itvLabelResource.getPic());
                    resourceInfo.setDesc(video.getDescription());
                    resourceInfo.setPic_400X300(video.getPic(400, 300));
                    String titleString = itvLabelResource.getTitle();
                    if (titleString != null && titleString.trim().length() > 0) {
                        resourceInfo.setName(titleString);
                    } else {
                        resourceInfo.setName(video.getName_cn());
                    }
                    resourceInfo.setResourceType(itvLabelResource.getResourceType());
                    resourceInfo.setCategoryId(video.getCategory());
                    resourceInfo.setNewCategoryId(video.getCategory());
                    resourceInfo.setCategoryName(video.getCategory_name());
                    resourceInfo.setStarring(video.getStarring());
                    resourceInfo.setVideoType(SearchConstant.VIDEO_TYPE_VIDEO);
                    resourceInfo.setDataType(DataConstant.DATA_TYPE_VIDEO);
                    resourceInfo.setVid(video.getId());
                    resourceInfo.setSubcategoryId(video.getSub_category());
                    resourceInfo.setSubcategoryName(video.getSub_category_name());
                    resourceInfo.setIptvAlbumId(video.getItv_album_id());
                    resourceInfo.setPic(itvLabelResource.getPic());
                    resourceInfo.setImg(video.getPic(400, 300));
                    resourceInfo.setImgSize("400*300");
                    resourceInfo.setCodeVersion(itvLabelResource.getCodeVersion());
                    resourceInfo.setStreamName(LetvStreamCommonConstants.nameOf(itvLabelResource.getCodeVersion(),
                            commonParam.getLangcode()));
                    resourceInfo.setReleaseDate(video.getRelease_date());
                    if (video.getCategory() != VideoConstants.Category.ENT) {
                        resourceInfo.setTag(video.getVideo_type_name());
                    }
                    resourceInfo.setScore(video.getScore());
                    resourceInfo.setSubTitle(video.getSub_title());
                    resourceInfo.setDuration(video.getDuration());
                    resourceInfo.setWatchFocus(video.getWatching_focus());
                    if (!StringUtil.isBlank(video.getPay_platform())
                            && MmsDataUtil.isSupportPayPlatform(video.getPay_platform(), commonParam.getP_devType())) {
                        resourceInfo.setIfCharge("1");
                    } else {
                        resourceInfo.setIfCharge("0");
                    }
                    // for tvod icon type search
                    Integer chargeType = null;
                    if (null != sessionCache) {
                        chargeType = JumpUtil.getChargeType(video.getPay_platform(),
                                MmsDataUtil.getPayPlatform(this.sessionCache));
                    } else {
                        chargeType = JumpUtil.getChargeType(video.getPay_platform());
                    }
                    resourceInfo.setChargeType(chargeType);
                    videoList.add((ResourceInfoDto) JumpUtil.bulidJumpObj(resourceInfo, DataConstant.DATA_TYPE_VIDEO,
                            defaultStream, defaultStreamName));
                }
            }
        }
        return videoList;
    }

    /**
     * 获取搜索建议列表
     * @param request
     * @return
     */
    public PageResponse<SuggestDto> getSuggestList(String p, String type, String query, String method, String from,
                                                   String splatid, String dt, String ph, String countryArea, String pushChild, String src, Integer num,
                                                   CommonParam commonParam) {
        PageResponse<SuggestDto> response = new PageResponse<SuggestDto>();
        List<Suggest> tpList = null;
        String errCode = null;
        SuggestTpResponse sugResponse = null;
        if (errCode == null) {
            sugResponse = this.facadeTpDao.getSearchTpDao().getSuggestList(p, type, query, method, from, splatid, dt,
                    ph, countryArea, pushChild, src, num, commonParam);
            if (sugResponse == null || CollectionUtils.isEmpty(sugResponse.getData_list())) {
                errCode = SearchConstant.SEARCH_ERROR_RESULT_NULL;
                log.info("getSuggestList result is null");
            } else {
                tpList = sugResponse.getData_list();
            }
        }

        if (errCode == null) {
            List<SuggestDto> sugList = new LinkedList<SuggestDto>();
            for (Suggest tp : tpList) {
                if (tp != null) {
                    sugList.add(new SuggestDto(tp.getName(), tp.getCategory(), tp.getGlobal_id()));
                }
            }
            response.setTotalCount(sugList.size());
            response.setData(sugList);
            DataReport dataReport = new DataReport(sugResponse.getEid(), sugResponse.getExperiment_bucket_str(),
                    sugResponse.getTrigger_str());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dataReport", dataReport);
            response.setPlus(map);
        } else {
            response = this.setErrorResponse(response, errCode, commonParam.getWcode());
        }

        return response;
    }

    /**
     * 根据关键字搜索影片
     * @param searchRequest
     * @return
     */
    public SearchPageResponse<SearchResultDto> searchByKeyword2(String from, String src, String orderType, String ph,
                                                                String dt, Integer page, Integer pageSize, String word, Integer categoryId, Integer subCategoryId,
                                                                String splatid, String leIds, String eid, String repo_type, String ispay, String stat, String countryArea,
                                                                String pushChild, String pcp, CommonParam commonParam) {
        SearchPageResponse<SearchResultDto> response = new SearchPageResponse<SearchResultDto>();

        GenericServingResponse result = this.facadeTpDao.getSearchTpDao().search(from, dt, page, pageSize, word,
                categoryId == null ? null : categoryId.toString(), subCategoryId, ph, src, 1, null, null, splatid,
                leIds, eid, repo_type, ispay, null, null, null, null, stat, countryArea, null, pushChild, pcp,
                commonParam);
        if (result != null && result.getSearch_response() != null
                && result.getSearch_response().getQuery_info() != null) {
            response.setLegal(result.getSearch_response().getQuery_info().is_illegal);
        }
        // 暂时先设置为1 后面和客户端约定规则
        if (result == null || result.getSearch_response() == null
                || CollectionUtils.isEmpty(result.getSearch_response().getServing_result_list())) {
            response.setResultStatus(1);
            response.setErrCode(SearchConstant.SEARCH_ERROR_RESULT_NULL);
            response.setErrMsg(MessageUtils.getMessage(SearchConstant.SEARCH_ERROR_RESULT_NULL,
                    commonParam.getLangcode()));
            return response;
        }

        // 搜索结果列表
        List<SearchResultDto> searchList = new ArrayList<SearchResultDto>();
        // 直播列表
        List<SearchLiveDto> newLiveList = new ArrayList<SearchLiveDto>();
        if (!CollectionUtils.isEmpty(result.getSearch_response().getServing_result_list())) {
            for (ServingResult servingResult : result.getSearch_response().getServing_result_list()) {
                if (servingResult.getData_type() != null
                        && (servingResult.getData_type().getValue() == SearchConstant.SEARCH_DATA_TYPE_ALBUM
                        || servingResult.getData_type().getValue() == SearchConstant.SEARCH_DATA_TYPE_STAR
                        || servingResult.getData_type().getValue() == SearchConstant.SEARCH_DATA_TYPE_SUBJECT || servingResult
                        .getData_type().getValue() == SearchConstant.SEARCH_DATA_TYPE_VIDEO)) {
                    SearchResultDto searchResultDto = new SearchResultDto(servingResult, commonParam);
                    // 乐搜中不出华数数据
                    if (!CommonConstants.WASU_NEW_NAME.equals(searchResultDto.getSubSrc())) {
                        searchList.add(searchResultDto);
                    }
                } else if (servingResult.getData_type() != null
                        && servingResult.getData_type().getValue() == SearchConstant.SEARCH_DATA_TYPE_LIVE) {
                    newLiveList.add(new SearchLiveDto(servingResult, commonParam));
                }
            }
        }

        // 获取分类情况
        List<SearchCategoryDto2> categoryList = new LinkedList<SearchCategoryDto2>();
        Map<String, Map<Short, StatInfo>> map = result.getSearch_response().getStat_detail();
        if (!CollectionUtils.isEmpty(map)) {
            Map<Short, StatInfo> mixCategory = map.get("data"); // 混排 的主键为data
            if (!CollectionUtils.isEmpty(mixCategory)) {
                for (short i : mixCategory.keySet()) {
                    categoryList.add(new SearchCategoryDto2(SearchCategoryDto2.categoryCategory, mixCategory.get(i)
                            .getCategory_name(), Short.toString(mixCategory.get(i).getCategory()), null));
                }
            }
        }
        // 整合所有搜索结果
        this.putCategory(categoryList, searchList, newLiveList, from, commonParam);// 将特殊tab放入
        response.setDataReport(new DataReport(result.getSearch_response().getEid(), result.getSearch_response()// 将特殊tab放入
                .getExperiment_bucket_str(), result.getSearch_response().getTrigger_str()));
        response.setPageSize(pageSize);
        response.setCurrentPage(page);
        response.setItems(searchList);
        response.setCategory_list(categoryList);
        int count = 0;
        if (result.getSearch_response().getData_count() > 0) {
            count = result.getSearch_response().getData_count();
            count = count > SearchConstant.SEARCH_RESULT_MAX_SIZE ? SearchConstant.SEARCH_RESULT_MAX_SIZE : count;
        }
        response.setCount(count);
        response.setLive_count(newLiveList == null ? 0 : newLiveList.size());
        response.setLiveList(newLiveList);
        // 兼容老客户端版本，如果items为空，客户端会崩溃
        if (response != null && response.getItems() == null) {
            response.setItems(new LinkedList<SearchResultDto>());
        }
        if (result.getSearch_response().getQuery_info() != null) {
            response.setLegal(result.getSearch_response().getQuery_info().is_illegal);
        }

        return response;
    }

    /**
     * 将 全部 直播 明星 三个tab放入列表中
     * @param categoryList
     * @param searchList
     * @param newLiveList
     */
    private void putCategory(List<SearchCategoryDto2> categoryList, List<SearchResultDto> searchList,
                             List<SearchLiveDto> newLiveList, String from, CommonParam commonParam) {
        List<SearchCategoryDto2> tempCategoryList = new LinkedList<SearchCategoryDto2>();

        tempCategoryList.add(new SearchCategoryDto2(SearchCategoryDto2.allCategory, MessageUtils.getMessage(
                "SEARCH.CATEGORY.ALL", commonParam.getLangcode()), null, null));// 放入全部tab
        if (!CollectionUtils.isEmpty(newLiveList)) {
            tempCategoryList.add(new SearchCategoryDto2(SearchCategoryDto2.dataTypeCategory, MessageUtils.getMessage(
                    "SEARCH.CATEGORY.LIVE", commonParam.getLangcode()), null, String
                    .valueOf(SearchConstant.SEARCH_DATA_TYPE_LIVE)));
        }
        if (from != null && !from.contains("tv_live") && !from.contains("tv_le")) { // 美国LIVE暂时处理下
            tempCategoryList.add(new SearchCategoryDto2(SearchCategoryDto2.dataTypeCategory, MessageUtils.getMessage(
                    "SEARCH.CATEGORY.STAR", commonParam.getLangcode()), null, String
                    .valueOf(SearchConstant.SEARCH_DATA_TYPE_STAR)));
        }
        categoryList.addAll(0, tempCategoryList);
    }

    public Response<VientianeDto> panoSearch(String from, String dt, Integer page, Integer pageSize, String word,
                                             String ph, Integer mix, String splatid, String limitRst, String pcp, String categoryId, String eid,
                                             CommonParam commonParam) {
        String logPrefix = "vientianeSearch_" + from + "_" + dt + "_" + page + "_" + pageSize + "_" + word + "_" + ph
                + "_" + mix + "_" + splatid + "_" + limitRst + "_" + pcp + "_" + categoryId + "_" + eid;

        Response<VientianeDto> response = new Response<VientianeDto>();
        GenericServingResponse result = this.facadeTpDao.getSearchTpDao().panoSearch(from, dt, page, pageSize, word,
                ph, mix, splatid, limitRst, pcp, categoryId, eid, commonParam);

        if (result == null || result.getSearch_response() == null) {
            this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_RESULT_NULL, commonParam.getLangcode());
            log.error(logPrefix + "search result is null");
            return response;
        }

        List<BaseData> searchList = new LinkedList<BaseData>();
        if (!CollectionUtils.isEmpty(result.getSearch_response().getServing_result_list())) {
            for (ServingResult servingResult : result.getSearch_response().getServing_result_list()) {
                searchList.add(SearchResultsDto.getResultDto(servingResult, commonParam));
            }
        }

        DataReport dataReport = new DataReport(result.getSearch_response().getEid(), result.getSearch_response()
                .getExperiment_bucket_str(), result.getSearch_response().getTrigger_str());

        VientianeDto vientianeDto = new VientianeDto();
        vientianeDto.setList(searchList);
        vientianeDto.setDataReport(dataReport);
        List<PanoSearchStat> list = result.getSearch_response().getPano_search_stat_list();
        vientianeDto.setCategoryList(this.getCategoryList(list, from, commonParam));
        vientianeDto.setPageSize(pageSize);
        vientianeDto.setCurrentPage(page);
        vientianeDto.setCount(result.getSearch_response().getData_count());

        response.setData(vientianeDto);

        return response;
    }

    /**
     * 获取分类列表
     * @param list
     * @param commonParam
     * @return
     */
    private List<SearchCategoryDto2> getCategoryList(List<PanoSearchStat> list, String from, CommonParam commonParam) {
        // 万象桌面、应用的请求 走万象标签
        if (SearchConstant.SHOW_PANOTAG.contains(from)) {
            return this.getPanoCategoryList(list, commonParam);
        } else {
            return this.getVideoCategoryList(list, commonParam);
        }
    }

    /**
     * 万象搜索分类
     */
    private List<SearchCategoryDto2> getPanoCategoryList(List<PanoSearchStat> list, CommonParam commonParam) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        List<SearchCategoryDto2> result = new LinkedList<SearchCategoryDto2>();
        // 用于标志该 tag是否已被添加到result中
        Map<String, SearchCategoryDto2> added = new HashMap<String, SearchCategoryDto2>();

        for (PanoSearchStat panoSearchStat : list) {
            DataType dataType = panoSearchStat.getData_type();
            if (dataType != null) {
                int value = dataType.getValue();
                String tagName = null;
                SearchCategoryDto2 tag = null;
                // 专辑，视频，专题，明星，直播，存在一种即展示 “视频”标签
                if (value == SearchConstant.SEARCH_DATA_TYPE_ALBUM || value == SearchConstant.SEARCH_DATA_TYPE_VIDEO
                        || value == SearchConstant.SEARCH_DATA_TYPE_SUBJECT
                        || value == SearchConstant.SEARCH_DATA_TYPE_STAR
                        || value == SearchConstant.SEARCH_DATA_TYPE_LIVE) {
                    tagName = MessageUtils.getMessage("PANO.TAG.VIDEO", commonParam.getLangcode());
                    if (added.get(tagName) == null) {
                        tag = new SearchCategoryDto2(tagName, null, "1,2,3,4,6");
                    }
                } else if (value == SearchConstant.SEARCH_DATA_TYPE_MUSICALBUM
                        || value == SearchConstant.SEARCH_DATA_TYPE_MUSICSINGLE) {
                    // 音乐专辑、音乐单曲存在一种即展示“音乐”标签
                    tagName = MessageUtils.getMessage("PANO.TAG.MUSIC", commonParam.getLangcode());
                    if (added.get(tagName) == null) {
                        tag = new SearchCategoryDto2(tagName, null, "8,9");
                    }
                } else if (value == SearchConstant.SEARCH_DATA_TYPE_APP) {
                    Map<Short, StatInfo> map = panoSearchStat.getCategory_map();
                    if (!CollectionUtils.isEmpty(map)) {
                        StatInfo appstore = map.get(new Short(String.valueOf(CategoryConstants.APPSTORE)));
                        StatInfo game = map.get(new Short(String.valueOf(CategoryConstants.GAMECENTER)));
                        // 应用、游戏至少一个不为空
                        if (appstore != null || game != null) {
                            // 展示“应用”标签
                            tagName = MessageUtils.getMessage("PANO.TAG.APP", commonParam.getLangcode());
                            if (added.get(tagName) == null) {
                                tag = new SearchCategoryDto2(tagName, String.valueOf(CategoryConstants.APPSTORE + ","
                                        + CategoryConstants.GAMECENTER),
                                        String.valueOf(SearchConstant.SEARCH_DATA_TYPE_APP));
                            }
                        }
                    }
                }
                // tag为空时，表示该标签已存在，不需要再添加
                if (tag != null) {
                    result.add(tag);
                    added.put(tagName, tag);
                }
            }
        }
        return result;
    }

    /**
     * 万象视频搜索分类
     * @param list
     * @param commonParam
     * @return
     */
    private List<SearchCategoryDto2> getVideoCategoryList(List<PanoSearchStat> list, CommonParam commonParam) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        List<SearchCategoryDto2> categoryTags = new LinkedList<SearchCategoryDto2>();// 存放cg型tag
        List<SearchCategoryDto2> dtTags = new LinkedList<SearchCategoryDto2>();// 存放dt型tag
        // 用于标志该 tag是否已被添加到result中
        Map<String, SearchCategoryDto2> added = new HashMap<String, SearchCategoryDto2>();

        for (PanoSearchStat panoSearchStat : list) {
            DataType dataType = panoSearchStat.getData_type();
            SearchCategoryDto2 tag = null;
            if (dataType != null) {
                String name = null;
                // 添加dt型标签，现场，明星
                if (dataType.getValue() == SearchConstant.SEARCH_DATA_TYPE_STAR) {
                    name = MessageUtils.getMessage("PANO.TAG.STAR", commonParam.getLangcode());
                } else if (dataType.getValue() == SearchConstant.SEARCH_DATA_TYPE_LIVE) {
                    name = MessageUtils.getMessage("PANO.TAG.LIVE", commonParam.getLangcode());
                }

                if (name != null && added.get(name) == null) {
                    tag = new SearchCategoryDto2(name, null, String.valueOf(dataType.getValue()));
                    // dt型标签放入
                    dtTags.add(tag);
                    added.put(tag.getName(), tag);
                }

            }

            // 添加category型标签
            Map<Short, StatInfo> statInfoList = panoSearchStat.getCategory_map();
            if (!CollectionUtils.isEmpty(statInfoList)) {
                Iterator<Short> keySet = statInfoList.keySet().iterator();
                while (keySet.hasNext()) {
                    StatInfo statInfo = statInfoList.get(keySet.next());
                    // 防止重复添加
                    if (added.get(statInfo.getCategory_name()) == null) {
                        tag = new SearchCategoryDto2(statInfo.getCategory_name(),
                                String.valueOf(statInfo.getCategory()), null);
                        // cg型标签放入
                        categoryTags.add(tag);
                        added.put(tag.getName(), tag);
                    }
                }
            }
        }
        List<SearchCategoryDto2> allTags = new LinkedList<SearchCategoryDto2>();
        allTags.addAll(dtTags);
        allTags.addAll(categoryTags);
        return allTags;
    }

    /**
     * 获取搜索锁需要的所有频道分类
     * @param commonParam
     * @return
     */
    public PageResponse<SearchCategoryDto> getAllCateogry(CommonParam commonParam) {
        PageResponse<SearchCategoryDto> response = new PageResponse<SearchCategoryDto>();
        String langcode = commonParam == null ? null : commonParam.getLangcode();
        Integer broadcastId = commonParam == null ? null : commonParam.getBroadcastId();
        Integer cid;
        List<SearchCategoryDto> categoryList = new LinkedList<SearchCategoryDto>();
        for (Map.Entry<Integer, String> cateEntry : CategoryConstants.SEARCH_CATEGORY_MAP.entrySet()) {
            cid = cateEntry.getKey();

            // 国广版本过滤热点，娱乐，风尚，财经，旅游
            if (cid != null && broadcastId != null && broadcastId == CommonConstants.CIBN) {
                if (cid == CategoryConstants.HOT || cid == CategoryConstants.TRAVELING
                        || cid == CategoryConstants.FASHION || cid == CategoryConstants.CAIJING) {
                    continue;
                }
            }
            categoryList.add(new SearchCategoryDto(cateEntry.getKey(), MessageUtils.getMessage(cateEntry.getValue(),
                    langcode)));
        }

        response.setData(categoryList);
        response.setTotalCount(categoryList.size());

        return response;
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

    /**
     * 获取CMS数据
     * @param blockId
     *            CMS的板块ID
     * @param defaultStream
     * @param defaultStreamName
     * @param broadcastId
     *            播控方
     * @param locale
     *            语言环境
     * @return
     */
    private List<AlbumInfoDto> getCmsData(String blockId, String defaultStream, String defaultStreamName,
                                          CommonParam commonParam) {
        List<AlbumInfoDto> cmsList = null;
        // CMSBlockTpResponse response =
        // this.facadeTpDao.getSearchTpDao().getCMSBlockData(blockId,
        // commonParam);
        CmsBlockTpResponse response = this.facadeTpDao.getCmsTpDao().getCmsBlockNewById(blockId, commonParam);
        String keyTmp = CacheContentConstants.SEARCH_MENU_CMS_ID + blockId;
        Long startTime = searchMenuCmsStartTime.get(keyTmp);
        if (response == null) {
            response = this.facadeCacheDao.getSearchCacheDao().getSearchMenuCMSBlock(blockId);
        } else if ((startTime == null || System.currentTimeMillis() - startTime > 300 * 1000)
                && !CollectionUtils.isEmpty(response.getBlockContent())) {
            searchMenuCmsStartTime.put(keyTmp, System.currentTimeMillis());
            this.facadeCacheDao.getSearchCacheDao().setSearchMenuCMSBlock(blockId, response);
        }
        if (response != null && !CollectionUtils.isEmpty(response.getBlockContent())) {
            cmsList = new ArrayList<AlbumInfoDto>();
            AlbumMysqlTable album = null;
            VideoMysqlTable video = null;
            for (CmsBlockContentTpResponse contentResponse : response.getBlockContent()) {
                if ((contentResponse.getPushflag() == null)
                        || !contentResponse.getPushflag().contains(CommonConstants.TV_PLATFROM_CODE)) {
                    continue;
                }
                if (StringUtils.isNotEmpty(contentResponse.getContent())) {
                    Integer type = contentResponse.getType() == null ? Integer.valueOf(0) : contentResponse.getType();
                    if (type == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_ALBUM) { // 专辑
                        album = albumVideoAccess.getAlbum(
                                Long.valueOf(contentResponse.getContent()), commonParam);
                        if (album != null) {
                            AlbumInfoDto albumDto = new AlbumInfoDto(album, commonParam);
                            if (contentResponse.getPic2() != null && !"".equals(contentResponse.getPic2())) {
                                albumDto.setPic_400X300(contentResponse.getPic2());
                            }
                            // 重构，没人清楚这段逻辑，暂时copy老逻辑
                            if ("1045".equals(blockId)) {
                                albumDto.setName(contentResponse.getTitle());
                            } else {
                                if (StringUtil.isBlank(contentResponse.getTitle())) {
                                    albumDto.setName(contentResponse.getSubTitle());
                                } else if (StringUtil.isBlank(contentResponse.getSubTitle())) {
                                    albumDto.setName(contentResponse.getTitle());
                                } else {
                                    albumDto.setName(contentResponse.getTitle() + "：" + contentResponse.getSubTitle());
                                }
                            }
                            cmsList.add((AlbumInfoDto) JumpUtil.bulidJumpObj(albumDto, DataConstant.DATA_TYPE_ALBUM,
                                    defaultStream, defaultStreamName));
                        }
                    } else if (type == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_VIDEO) { // 视频
                        video = albumVideoAccess.getVideo(
                                Long.valueOf(contentResponse.getContent()), commonParam);
                        if (video != null) {
                            if (!StringUtils.isEmpty(contentResponse.getTitle())) {
                                video.setName_cn(contentResponse.getTitle());
                            }
                            if (!StringUtils.isEmpty(contentResponse.getSubTitle())) {
                                video.setSub_title(contentResponse.getSubTitle());
                            }
                            cmsList.add((ResourceInfoDto) JumpUtil.bulidJumpObj(
                                    new ResourceInfoDto(video, commonParam.getLangcode(), commonParam, contentResponse
                                            .getTvPic()), DataConstant.DATA_TYPE_VIDEO, defaultStream,
                                    defaultStreamName));
                        }
                    } else if (type == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_SUBJECT) { // 专题数据
                        AlbumInfoDto subject = new AlbumInfoDto();
                        subject.setId(Long.parseLong(contentResponse.getContent()));

                        subject.setName(contentResponse.getTitle());
                        subject.setDesc(contentResponse.getShorDesc());
                        subject.setTag(contentResponse.getTag());
                        subject.setVideoType("subject");
                        // 2015-07-21，专题模板字段逻辑修改，这里出现字段占用问题，保留原字段，添加新字段templateType
                        if ("1".equals(contentResponse.getZidType())) {
                            subject.setSubjectType(SearchConstant.SUBJECT_TYPE_HORIZONTAL);
                        } else {
                            subject.setSubjectType(SearchConstant.SUBJECT_TYPE_VERTICAL);
                        }
                        // 2015-07-21 修改为从接口新字段中获取专题模板信息
                        Integer templateType = null; // 专题模板
                        GetSubjectTpResponse.SubjectDataTpResponse subjectInfo = contentResponse.getKztInfo();
                        if (subjectInfo != null) {
                            // 获取返回的模板信息，只有专题包才返回此部分内容
                            GetSubjectTpResponse.SubjectDataTpResponse.TemplateJson templateJson = subjectInfo.getTemplateJson();
                            if (templateJson != null) {
                                String key = templateJson.getTvTemplate();
                                if (StringUtils.isNotBlank(key)) {
                                    // 根据模板和subjectType的转换规则获取subjectType的值
                                    templateType = SubjectConstant.getSubjectTypeFromTemplate(key);
                                    subject.setTemplateType(templateType);
                                } else {
                                    log.info(" can't get template from subject info.");
                                }
                            } else {
                                log.info(" templateJson is null.");
                            }
                        } else {
                            log.info(" subject info is null");
                        }
                        subject.setNewCategoryId(response.getCid());
                        subject.setCategoryId(response.getCid());
                        subject.setPic_400X300(contentResponse.getTvPic());
                        subject.setSubTitle(contentResponse.getSubTitle());
                        cmsList.add((AlbumInfoDto) JumpUtil.bulidJumpObj(subject, DataConstant.DATA_TYPE_SUBJECT,
                                defaultStream, defaultStreamName));
                    } else if (type == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_STAR) { // 明星
                        AlbumInfoDto star = new AlbumInfoDto();
                        star.setId(StringUtil.toLong(contentResponse.getContent()));
                        star.setName(contentResponse.getTitle());
                        star.setPic_400X300(contentResponse.getTvPic());
                        cmsList.add((AlbumInfoDto) JumpUtil.bulidJumpObj(star, DataConstant.DATA_TYPE_MUSIC,
                                defaultStream, defaultStreamName, commonParam));
                    }
                } else {
                    CmsBlockContentTpResponse.CmsTpResponseExt extendJson = contentResponse.getExtendJson();
                    String extendPage = extendJson.getExtendPage();
                    String[] extendPageElements = extendPage.split("\\|");
                    int paramsLength = extendPageElements.length;
                    if (extendPageElements != null || paramsLength > 0) {
                        Integer dataType = StringUtil.toInteger(extendPageElements[0], DataConstant.DATA_TYPE_BLANK);
                        if (DataConstant.DATA_TYPE_BLANK != dataType) {
                            // 老业务，走默认逻辑
                            AlbumInfoDto star = new AlbumInfoDto();
                            if (extendPageElements.length > 1) {
                                star.setId(StringUtil.toLong(extendPageElements[1].trim()));
                            }
                            star.setName(contentResponse.getTitle());
                            star.setPic_400X300(contentResponse.getTvPic());
                            cmsList.add((AlbumInfoDto) JumpUtil.bulidJumpObj(star, dataType, defaultStream,
                                    defaultStreamName, commonParam));
                        }
                    }
                }
            }
        }
        return cmsList;
    }

    /**
     * 根据标签搜索专题数据
     * @param labelId
     *            标签ID
     * @param categoryId
     *            分类ID
     * @param locale
     *            语言环境
     * @return
     */
    private PageCommonResponse<AlbumInfoDto> getLabelDataInfo(Integer page, Integer pageSize, Integer newCategoryId,
                                                              String searchValue, CommonParam commonParam) {
        PageCommonResponse<AlbumInfoDto> response = new PageCommonResponse<AlbumInfoDto>();
        Integer categoryId = CategoryIdConstant.getOldCategory(newCategoryId);
        Long labelId = Long.parseLong(searchValue);

        int count = 0;
        List<ItvLabelResource> list = null;
        try {
            count = this.facadeMysqlDao.getAlbumMysqlDao().getCountByLabelIdForZT(categoryId, labelId);
            if (count > 0) {
                list = this.facadeMysqlDao.getAlbumMysqlDao().getListByLabelIdForZT(categoryId, new Long(labelId),
                        (page - 1) * pageSize, pageSize);
            }
        } catch (Exception e) {
            // 这两个变量来 确定数据库连接挂了
            count = -1;
            list = null;
        }

        if (page == 1) {
            String key = CacheContentConstants.SEARCH_MENU_LABEL_ID + categoryId + "_" + searchValue + "_" + page;
            Long startTime = searchMenuLabelStartTime.get(key);
            if (list == null && count == -1) {
                list = this.facadeCacheDao.getSearchCacheDao().getSearchMenuLabelData(categoryId, searchValue, page);
                if (list != null) {
                    count = list.size();
                }
            } else if ((startTime == null || System.currentTimeMillis() - startTime > 300 * 1000)
                    && !CollectionUtils.isEmpty(list)) {
                searchMenuLabelStartTime.put(key, System.currentTimeMillis());
                this.facadeCacheDao.getSearchCacheDao().setSearchMenuLabelData(categoryId, searchValue, page, list);
            }
        }

        if (count > 0) {
            if (list != null || list.size() > 0) {
                List<AlbumInfoDto> items = new ArrayList<AlbumInfoDto>();
                ResourceInfoDto video = null;
                for (ItvLabelResource itvLabelItvAlbum : list) {
                    video = new ResourceInfoDto();
                    video.setVideoType(String.valueOf(itvLabelItvAlbum.getResourceType()));
                    if (itvLabelItvAlbum.getItvAlbumId() != null) {
                        video.setIptvAlbumId(itvLabelItvAlbum.getItvAlbumId().longValue());
                    }
                    video.setName(itvLabelItvAlbum.getTitle());
                    video.setPic_400X300(itvLabelItvAlbum.getPic());
                    video.setImg_vertical_300x400(itvLabelItvAlbum.getPic());
                    video.setResourceType(itvLabelItvAlbum.getResourceType());
                    items.add(video);
                }

                PageControl<AlbumInfoDto> pageControl = new PageControl<AlbumInfoDto>(pageSize, page, count);
                pageControl.setList(items);
                return new PageCommonResponse<AlbumInfoDto>(pageControl);

            }
        } else {
            return this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_RESULT_NULL, commonParam.getWcode());
        }
        return response;
    }

    /**
     * 对List进行分页取数据
     * @param albumList
     * @param page
     *            页数
     * @param pageSize
     *            页大小
     * @return
     */
    private PageCommonResponse<AlbumInfoDto> parsePage(List<AlbumInfoDto> albumList, int page, int pageSize) {
        if (albumList == null || albumList.size() == 0) {
            return null;
        }

        int pageBegin = ((page - 1) * pageSize < 0) ? 0 : ((page - 1) * pageSize);
        int pageEnd = (page * pageSize > albumList.size()) ? albumList.size() : (page * pageSize);
        if (pageBegin > pageEnd) {
            return null;
        }
        List<AlbumInfoDto> subList = albumList.subList(pageBegin, pageEnd);

        PageControl<AlbumInfoDto> pageControl = new PageControl<AlbumInfoDto>(pageSize, page, albumList.size());
        pageControl.setList(subList);
        return new PageCommonResponse<AlbumInfoDto>(pageControl);
    }

    /**
     * 不合理需求
     * 电视剧，动漫，综艺，音乐强行更改subTitle
     * @param dtoList
     * @param cid
     *            新的类别ID
     */
    private void changeSubTitle(List<AlbumInfoDto> dtoList, Integer cid) {
        if (cid == null || dtoList == null || dtoList.size() == 0) {
            return;
        }

        if (VideoConstants.Category.TV == cid) {
            for (AlbumInfoDto dto : dtoList) {
                if ("album".equals(dto.getVideoType())) { // 针对专辑才修改subTitle
                    if (!dto.isEnd() && dto.getFollownum() != null && dto.getFollownum() > 0) {

                        dto.setSubTitle("更新至" + dto.getFollownum() + "集");
                    }
                }
            }
        } else if (VideoConstants.Category.MUSIC == cid) {
            for (AlbumInfoDto dto : dtoList) {
                if ("video".equals(dto.getVideoType())) {
                    String star = ((ResourceInfoDto) dto).getSinger();
                    if (star != null) {
                        if (star.startsWith(",")) {
                            star = star.substring(1);
                        }
                        if (star.endsWith(",")) {
                            star = star.substring(0, star.length() - 1);
                        }
                    }
                    dto.setSubTitle(star);
                }
            }
        } else if (VideoConstants.Category.VARIETY == cid) {
            for (AlbumInfoDto dto : dtoList) {
                if ("album".equals(dto.getVideoType())) {
                    // 除正片类专辑外，不显示副标题 @dengliwei 20170113
                    if (180001 == dto.getAlbumTypeId()) {
                        dto.setSubTitle("更新至" + dto.getFollownum() + "期");
                    } else {
                        dto.setSubTitle(null);
                    }
                }
            }
        } else if (VideoConstants.Category.CARTOON == cid) {
            for (AlbumInfoDto dto : dtoList) {
                if ("album".equals(dto.getVideoType())) { // 针对专辑才修改subTitle
                    if (dto.isEnd()
                            || (dto.getEpisodes() != null && dto.getFollownum() != null && dto.getEpisodes().equals(
                            dto.getFollownum()))) {
                        dto.setSubTitle(dto.getEpisodes() + "集全");
                    } else {
                        if (dto.getFollownum() != null) {
                            dto.setSubTitle("更新至" + dto.getFollownum() + "集");
                        }
                    }
                }
            }
        }
    }

    /**
     * 乐搜首页获取专辑推送
     * @param request
     * @return
     */
    public PageResponse<MainPageHeadDto> getMainPagePushAlbums(String platform, String ph, String from, String splatid,
                                                               String displayAppId, String displayPlatformId, CommonParam commonParam) {
        PageResponse<MainPageHeadDto> response = new PageResponse<MainPageHeadDto>();

        MainPagePushAlbumsTpResponse tpResponse = this.facadeTpDao.getSearchTpDao().getMainPagePushAlbums(platform, ph,
                from, splatid, displayAppId, displayPlatformId, commonParam);
        if (tpResponse == null || !tpResponse.success() || tpResponse.getContent() == null
                || tpResponse.getContent().getMasthead() == null || tpResponse.getContent().getMasthead().size() == 0) {
            return this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_RESULT_NULL, commonParam.getLangcode());
        }
        List<MainPageHeadDto> dtoList = new LinkedList<MainPageHeadDto>();
        List<MainPagePushAlbumsTpResponse.MastHeadObject> mastHeadList = tpResponse.getContent().getMasthead();
        MainPageHeadDto headDto = null;
        for (MainPagePushAlbumsTpResponse.MastHeadObject mastHead : mastHeadList) {
            headDto = new MainPageHeadDto(mastHead, tpResponse.getModule());
            dtoList.add(headDto);
        }

        response.setTotalCount(dtoList.size());
        response.setData(dtoList);
        response.setResultStatus(1);

        DataReport dataReport = new DataReport(tpResponse.getEid(), tpResponse.getExperiment_bucket_str(),
                tpResponse.getTrigger_str());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dataReport", dataReport);
        response.setPlus(map);

        return response;
    }

    /**
     * 获取乐搜新首页频道列表
     * @param request
     * @return
     */
    public PageResponse<MainPageChannelDto> getMainPageChannels(String platform, String ph, String from,
                                                                String splatid, String displayAppId, String displayPlatformId, CommonParam commonParam) {
        PageResponse<MainPageChannelDto> response = new PageResponse<MainPageChannelDto>();
        String errorCode = null;

        MainPageChannelsTpResponse tpResponse = this.facadeTpDao.getSearchTpDao().getMainPageChannels(platform, ph,
                from, splatid, displayAppId, displayPlatformId, commonParam);
        if (tpResponse == null || !tpResponse.success() || tpResponse.getContent() == null
                || tpResponse.getContent().getChannels() == null || tpResponse.getContent().getChannels().size() == 0) {
            errorCode = SearchConstant.SEARCH_ERROR_RESULT_NULL;
            return this.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }
        List<MainPageChannelDto> dtoList = new LinkedList<MainPageChannelDto>();
        List<MainPageChannelsTpResponse.ChannelObject> channelList = tpResponse.getContent().getChannels();
        MainPageChannelDto channelDto = null;
        for (MainPageChannelsTpResponse.ChannelObject channel : channelList) {
            channelDto = new MainPageChannelDto(channel);
            dtoList.add(channelDto);
        }

        response.setTotalCount(dtoList.size());
        response.setData(dtoList);
        response.setResultStatus(1);

        DataReport dataReport = new DataReport(tpResponse.getEid(), tpResponse.getExperiment_bucket_str(),
                tpResponse.getTrigger_str());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dataReport", dataReport);
        response.setPlus(map);

        return response;
    }

    /**
     * 获取乐搜新首页频道下的话题列表
     * @param request
     * @return
     */
    public Response<MainPageTopicsDto> getMainPageTopics(String platform, String ph, String channelId, String from,
                                                         String splatid, String displayAppId, String displayPlatformId, CommonParam commonParam) {
        Response<MainPageTopicsDto> response = new Response<MainPageTopicsDto>();
        MainPageTopicsTpResponse tpResponse = this.facadeTpDao.getSearchTpDao().getMainPageTopics(platform, ph,
                channelId, from, splatid, displayAppId, displayPlatformId, commonParam);
        if (tpResponse == null || !tpResponse.success() || tpResponse.getContent() == null
                || CollectionUtils.isEmpty(tpResponse.getContent().getTopic_list())) {
            return this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_RESULT_NULL, commonParam.getLangcode());
        }
        MainPageTopicsDto topicDto = new MainPageTopicsDto(tpResponse.getContent());
        response.setData(topicDto);
        response.setResultStatus(1);

        return response;
    }

    /**
     * 乐搜新首页频道下topic内的数据
     * @param request
     * @return
     */
    public Response<MainPageTopicDataDto> getMainPageTopicData(String platform, String ph, String channelId,
                                                               String subtopic, Integer page, Integer pageSize, String history, String from, String splatid,
                                                               String displayAppId, String displayPlatformId, CommonParam commonParam) {
        Response<MainPageTopicDataDto> response = new Response<MainPageTopicDataDto>();
        MainPageTopicDataTpResponse tpResponse = this.facadeTpDao.getSearchTpDao().getMainPageTopicData(platform, ph,
                channelId, subtopic, page, pageSize, history, from, splatid, displayAppId, displayPlatformId,
                commonParam);
        if (tpResponse == null || !tpResponse.success() || tpResponse.getContent() == null) {
            return this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_RESULT_NULL, commonParam.getLangcode());
        }

        MainPageTopicDataDto topicDto = new MainPageTopicDataDto(tpResponse.getContent());
        topicDto.setCurrentPage(page);
        topicDto.setPageSize(pageSize);
        response.setData(topicDto);
        response.setResultStatus(1);
        DataReport dataReport = new DataReport(tpResponse.getEid(), tpResponse.getExperiment_bucket_str(),
                tpResponse.getTrigger_str());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dataReport", dataReport);
        response.setPlus(map);
        return response;
    }

    /**
     * 根据IP地址获取地域信息
     * @param ip
     * @param wcode
     * @return
     */
    public Response<IpLocationDto> getIpLocation(String ip, String splatid, CommonParam commonParam) {
        Response<IpLocationDto> response = new Response();
        if (ip != null) {
            IpLocationResponse tpResponse = this.facadeTpDao.getSearchTpDao().getIpLocation(ip, splatid, commonParam);
            IpLocationDto dto = new IpLocationDto(tpResponse);
            response.setData(dto);
            response.setResultStatus(1);

        }
        return response;
    }

    /**
     * 结果页中，从内网搜索专辑详情页
     * @param aid
     *            专辑id
     * @param type
     *            专辑类型 0 来自PTV；1来自VRS；2来自外网；3来自IPTV
     * @param from
     *            来源，搜索统计需要 230表示TV
     * @param page
     *            页码
     * @param pageSize
     *            每页数据量
     * @return
     */
    public Response<AlbumDetailDto> getAlbumDetail(Long aid, String type, String from, String ph, Integer page,
                                                   Integer pageSize, CommonParam commonParam) {

        Response<AlbumDetailDto> response = new Response<AlbumDetailDto>();
        AlbumDetailTpResponse tpResponse = this.facadeTpDao.getSearchTpDao().getAlbumDetail(aid, type, from, ph, page,
                pageSize, commonParam);

        if (tpResponse == null) {
            // TODO ligang 20151216 为什么酱紫实现
            return this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_RESULT_NULL, commonParam.getLangcode());
        }

        AlbumDetailDto detailDto = new AlbumDetailDto(tpResponse, commonParam);
        /*
         * 香港详情页有contentRating字段，需要从媒资获取这个字段的具体含义
         */
        if (SearchTpConstant.SEARCH_WCODE_HK.equalsIgnoreCase(commonParam.getWcode())
                && tpResponse.getContentRating() != null) {
            // TODO ligang 20151216 这两步可以合并吧？另外，这是什么需求？
            String value = videoService.getContentRatingValue(tpResponse.getContentRating(),
                    commonParam.getLangcode());
            String desc = videoService.getContentRatingDesc(tpResponse.getContentRating(),
                    commonParam.getLangcode());
            value = value == null ? "" : value;
            desc = desc == null ? "" : desc;
            detailDto.setContentRating(value + " " + desc);
        }

        // TODO ligang 20151216 香港的处理逻辑应该集中在一起，另外，这是什么需求？
        /**
         * 香港媒资二期TV版版权字段改动，导致香港乐搜外跳播放;
         * 结果中发现420007,强行加入420009，保证香港乐搜识别TV版权
         */
        if (detailDto != null && !CollectionUtils.isEmpty(detailDto.getVideoList())
                && SearchTpConstant.SEARCH_WCODE_HK.equalsIgnoreCase(commonParam.getWcode())) {
            List<AlbumDetailVideoInfoDto> videoList = detailDto.getVideoList();
            for (AlbumDetailVideoInfoDto video : videoList) {
                if (video.getNewPushFlag() != null
                        && video.getNewPushFlag().contains(CommonConstants.TV_PLAY_PLAT_FROM)) {
                    video.setNewPushFlag(video.getNewPushFlag() + "," + CommonConstants.TV_PLAY_BAOFENG_PLAT_FROM);
                }
            }
        }

        // 国广ROM
        if (BroadcastConstant.BROADCAST_CIBN == commonParam.getBroadcastId()) {
            // 内网数据
            if (SearchTpConstant.LESO_SRC_VRS.equals(detailDto.getSrc())) {
                List<AlbumDetailVideoInfoDto> videoList = detailDto.getVideoList();
                if (videoList != null && videoList.size() > 0) {
                    List<AlbumDetailVideoInfoDto> delList = new LinkedList<AlbumDetailVideoInfoDto>();
                    for (AlbumDetailVideoInfoDto video : videoList) {
                        if (video.getNewPushFlag() == null
                                || !video.getNewPushFlag().contains(CommonConstants.TV_PLAY_PLAT_FROM)) { // 国广过滤没有TV版版权的
                            delList.add(video);
                        }
                    }
                    videoList.removeAll(delList);
                }
            } else if (!CommonConstants.CIBN_NAME.equals(detailDto.getSubSrc())
                    && !CommonConstants.WASU_NEW_NAME.equals(detailDto.getSubSrc())) {
                detailDto.setVideoList(null);
            }
        }

        response.setData(detailDto);
        return response;
    }

    /**
     * 结果页中，从外网搜索专辑详情页
     * @param aid
     *            专辑id
     * @param site
     *            站源信息
     * @param from
     *            请求来源 tv为tv_01
     * @return
     */
    public Response<AlbumDetailWebsiteTpResponse.AlbumDetailWebSiteDto> getAlbumDetailWebSite(Long aid, String site, String from,
                                                                                              CommonParam commonParam) {
        Response<AlbumDetailWebsiteTpResponse.AlbumDetailWebSiteDto> response = new Response<AlbumDetailWebsiteTpResponse.AlbumDetailWebSiteDto>();

        AlbumDetailWebsiteTpResponse result = this.facadeTpDao.getSearchTpDao().getAlbumDetailWebSite(aid, site, from,
                commonParam);
        if (result == null || result.getData() == null) {
            return this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_RESULT_NULL, commonParam.getLangcode());
        }

        AlbumDetailWebsiteTpResponse.AlbumDetailWebSiteDto webSiteSeriesDTO = null;
        if (!CollectionUtils.isEmpty(result.getData())) {
            for (AlbumDetailWebsiteTpResponse.AlbumDetailWebSiteDto w : result.getData()) {
                // 遍历所有站源取出数据
                if (!StringUtil.isBlank(site) && site.equals(w.getSite())) {
                    webSiteSeriesDTO = w;
                    break;
                }
            }
        }

        response.setData(webSiteSeriesDTO);
        return response;
    }

    /**
     * 搜索专辑所有站源信息
     * @param aid
     *            专辑id
     * @param type
     *            专辑类型 0 来自PTV；1来自VRS；2来自外网；3来自IPTV
     * @param ph
     *            版权
     * @param from
     *            请求来源 tv为tv_01
     * @return
     */
    public PageResponse<WebsiteDto> getWebSiteList(Long aid, String type, String ph, String from,
                                                   CommonParam commonParam) {
        PageResponse<WebsiteDto> response = new PageResponse<WebsiteDto>();
        WebsiteTpResponse websiteResponse = this.facadeTpDao.getSearchTpDao().getWebSite(aid, type, ph, from,
                commonParam);
        if (websiteResponse == null) {
            return this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_RESULT_NULL, commonParam.getLangcode());
        }
        List<WebsiteDto> result = new LinkedList<WebsiteDto>();
        response.setData(result);

        WebsiteDto letvSite = null;
        WebsiteDto cibnSite = null;
        WebsiteDto wasuSite = null;
        if (!CollectionUtils.isEmpty(websiteResponse.getData())) {
            for (WebsiteDto w : websiteResponse.getData()) {
                WebsiteTpResponse.WEB_SITE webSite = WebsiteTpResponse.WEB_SITE.getCNNAME(w.getSite());
                w.setSiteName(webSite.getName());
                w.setIcon(webSite.getIcon());
                if (WebsiteTpResponse.WEB_SITE.LETV.getPinYin().equals(w.getSite())) {
                    letvSite = w;
                } else if (WebsiteTpResponse.WEB_SITE.CIBN.getPinYin().equals(w.getSite())) {
                    cibnSite = w;
                } else if (WebsiteTpResponse.WEB_SITE.WASU.getPinYin().equals(w.getSite())) {
                    wasuSite = w;
                }
            }
            // 站源顺序letv>cibn>wasu
            if (letvSite != null) {
                result.add(letvSite);
            }
            if (cibnSite != null) {
                result.add(cibnSite);
            }
            if (wasuSite != null) {
                result.add(wasuSite);
            }
        }

        return response;
    }

    /**
     * 获取明星详情，主要包含明星简介等数据
     * @param detailRequest
     * @return
     */
    public Response<SearchStarDetailDto> getStarDetail(String id, CommonParam commonParam) {
        Response<SearchStarDetailDto> response = new Response<SearchStarDetailDto>();
        String errCode = null;
        StarDetailTpResponse detailResponse = this.facadeTpDao.getSearchTpDao().getStarDetail(id, commonParam);

        if (detailResponse == null || CollectionUtils.isEmpty(detailResponse.getData())) {
            errCode = SearchConstant.SEARCH_ERROR_RESULT_NULL;
        }

        if (errCode == null) {
            // 如果没有错误码，则取0号位置数据
            SearchStarDetailDto detailDto = new SearchStarDetailDto(detailResponse.getData().get(0));
            response.setData(detailDto);
        } else {
            response = this.setErrorResponse(response, errCode, commonParam.getLangcode());
        }

        return response;
    }

    /**
     * 获取明星作品类别
     * @param request
     * @return
     */
    public Response<SearchStarCategoryDto> getStarCategory(String word, String session, String ph, Integer page,
                                                           Integer pageSize, String from, String splatid, String displayAppId, String displayPlatformId,
                                                           CommonParam commonParam) {
        Response<SearchStarCategoryDto> response = new Response<SearchStarCategoryDto>();
        String errCode = null;
        SearchResultTpResponse searchResponse = this.facadeTpDao.getSearchTpDao().getStarCategory(word, session, ph,
                page, pageSize, from, splatid, displayAppId, displayPlatformId, commonParam);

        if (searchResponse == null) {
            errCode = SearchConstant.SEARCH_ERROR_RESULT_NULL;
        }

        if (errCode == null) {
            SearchStarCategoryDto categoryDto = new SearchStarCategoryDto();
            categoryDto.setCategory_count_list(searchResponse.getCategory_count_list());
            response.setData(categoryDto);
        } else {
            response = this.setErrorResponse(response, errCode, commonParam.getLangcode());
        }

        return response;
    }

    /**
     * public Response<SearchStarCategoryDto> getStarCategory2(String from,
     * String dt, Integer page, Integer pageSize,
     * String word, String ph, String src, String order, String splatid, String
     * albumFilter, String videoFilter,
     * String jf, String sf, CommonParam commonParam) {
     * Response<SearchStarCategoryDto> response = new
     * Response<SearchStarCategoryDto>();
     * String errCode = null;
     * GenericServingResponse result =
     * this.facadeTpDao.getSearchTpDao().search(from, dt, page, pageSize, word,
     * null,
     * null, ph, src, null, order, null, splatid, null, null, null, null,
     * albumFilter, videoFilter, jf, sf,
     * commonParam);
     * result.getSearch_response().getCategory_num()
     * result.getSearch_response().getServing_result_list().
     * if (searchResponse == null) {
     * errCode = SearchConstant.SEARCH_ERROR_RESULT_NULL;
     * }
     * if (errCode == null) {
     * SearchStarCategoryDto categoryDto = new SearchStarCategoryDto();
     * categoryDto.setCategory_count_list(searchResponse.getCategory_count_list(
     * ));
     * response.setData(categoryDto);
     * } else {
     * response = this.setErrorResponse(response, errCode,
     * commonParam.getLangcode());
     * }
     * return response;
     * }
     **/

    /**
     * 获取明星作品街道口
     * @param from
     *            搜索来源,客户端传入 乐搜-tv_01 tv版-tv_02
     * @param dt
     *            搜索数据类型 参照CategoryConstant的SEARCH_DATA_TYPE
     * @param page
     *            页码
     * @param pageSize
     *            每页大小
     * @param word
     *            搜索关键字
     * @param categoryId
     *            频道分类id
     * @param ph
     *            版权限制
     * @param src
     *            内外网数据 内网-1 外网-2
     * @param order
     *            排序
     * @param splatid
     *            直播平台id
     * @param ispay
     *            是否付费
     * @param sf
     *            搜索限定字段
     * @param starId
     *            明星ID，对应搜索的le_ids
     * @param commonParam
     * @return
     */
    public Response<SearchStarWorksDto> getStarWorks2(String from, String dt, Integer page, Integer pageSize,
                                                      String word, Integer categoryId, String ph, String src, String order, String splatid, String sf,
                                                      String pcp, String starId, CommonParam commonParam) {
        GenericServingResponse result = this.facadeTpDao.getSearchTpDao().search(from, dt, page, pageSize, word,
                categoryId == null ? null : categoryId.toString(), null, ph, src, null, order, null, splatid, starId,
                null, null, null, null, null, null, sf, null, null, null, null, pcp, commonParam);
        Response<SearchStarWorksDto> response = new Response<SearchStarWorksDto>();

        if (result == null || result.getSearch_response() == null
                || CollectionUtils.isEmpty(result.getSearch_response().getServing_result_list())) {
            response = this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_RESULT_NULL,
                    commonParam.getLangcode());
            return response;
        }

        List<VideoList> videoList = new LinkedList<VideoList>();
        List<StarWorksTpResponse.AlbumList> albumList = new LinkedList<StarWorksTpResponse.AlbumList>();

        for (ServingResult servingResult : result.getSearch_response().getServing_result_list()) {
            if (servingResult.getData_type() != null
                    && servingResult.getData_type().getValue() == SearchConstant.SEARCH_DATA_TYPE_ALBUM) {
                albumList.add(new StarWorksTpResponse.AlbumList(servingResult));
            } else if (servingResult.getData_type() != null
                    && servingResult.getData_type().getValue() == SearchConstant.SEARCH_DATA_TYPE_VIDEO) {
                VideoList video = new VideoList(servingResult);
                if ((video.getUrl() == null || "".equals(video.getUrl().trim())) && video.getVid() != null
                        && video.getVid().matches("\\d+")) {
                    video.setUrl(DataConstant.PC_PLAY_URL + video.getVid() + ".html");
                }
                videoList.add(video);
            }
        }

        StarWorks works = new StarWorks();
        works.setAlbum_list(albumList);
        works.setVideo_list(videoList);

        SearchStarWorksDto worksDto = new SearchStarWorksDto();
        worksDto.setCategory(categoryId);
        worksDto.setDataType(SearchConstant.SEARCH_DATA_TYPE_ALBUM + "," + SearchConstant.SEARCH_DATA_TYPE_VIDEO);
        // categoryname做多语言操作
        String categoryName = CategoryConstants.SEARCH_CATEGORY_MAP.get(categoryId);
        worksDto.setCategory_name(MessageUtils.getMessage(categoryName, commonParam.getLangcode()));
        worksDto.setDataList(works);

        response.setData(worksDto);
        return response;
    }

    public List<AlbumInfoDto> getUserLikeAlbums(String pt, String lc, String area, Integer pageSize, String cid,
                                                String history, Integer channelId, CommonParam commonParam) {
        List<AlbumInfoDto> data = new ArrayList<AlbumInfoDto>();

        SearchMaybeLikeTpResponse response = this.facadeTpDao.getSearchTpDao().getSearchMaybeLike(pt, lc, area,
                pageSize, cid, history, null, commonParam);// 放大10倍

        // 只缓存 频道 二级标签猜你喜欢(cid=0 area=rec_0002)
        String key = CacheContentConstants.SEARCH_MENU_USERLIKE_ID + area + "_" + cid + "_" + pageSize;
        Long startTime = searchMenuUserLikeStartTime.get(key);
        if (response == null) {
            response = this.facadeCacheDao.getSearchCacheDao().getSearchMenuUserLike(area, cid, pageSize);
        } else if ((startTime == null || System.currentTimeMillis() - startTime > 300 * 1000)
                && !CollectionUtils.isEmpty(response.getRec())) {
            searchMenuUserLikeStartTime.put(key, System.currentTimeMillis());
            this.facadeCacheDao.getSearchCacheDao().setSearchMenuUserLike(area, cid, pageSize, response);
        }

        if (response == null || CollectionUtils.isEmpty(response.getRec())) {
            return new ArrayList<AlbumInfoDto>();
        }
        String defaultStream = this.facadeCacheDao.getChannelCacheDao().getChannelDefaultStreamById(
                String.valueOf(channelId));
        String defaultStreamName = LetvStreamCommonConstants.getStreamName(defaultStream, commonParam.getLangcode());
        for (SearchMaybeLikeTpResponse.SearchMayLikeDTO dto : response.getRec()) {
            if (dto.getIsalbum() == null) {
                continue;
            }
            if (dto.getIsalbum() == 0) { // 单视频
                Long d1 = System.currentTimeMillis();
                VideoMysqlTable video = albumVideoAccess.getVideo(dto.getVid(), commonParam);
                if (video != null) {
                    ResourceInfo resourceInfo = ParseUtil.parseObjectByConstructor(ResourceInfo.class,
                            new Class[] { VideoMysqlTable.class }, new Object[] { video });
                    resourceInfo.setReid(response.getReid());
                    resourceInfo.setBucket(response.getBucket());
                    resourceInfo.setAreaRec(response.getArea());
                    resourceInfo.setIs_rec(dto.getIs_rec());
                    resourceInfo.setId(video.getId());
                    resourceInfo = (ResourceInfo) JumpUtil.bulidJumpObj(resourceInfo, DataConstant.DATA_TYPE_VIDEO,
                            defaultStream, defaultStreamName);
                    data.add(resourceInfo);
                }
            } else if (dto.getIsalbum() == 1) { // 专辑
                Long id = dto.getPid();
                // AlbumMysqlTable album =
                // videoService.getAlbumById(id,
                // commonParam.getBroadcastId());
                AlbumMysqlTable album = albumVideoAccess.getAlbum(id, commonParam);
                if (album != null) {
                    ResourceInfo resourceInfo = new ResourceInfo(album, commonParam);
                    if (!TerminalUtil.isLetvUs(commonParam)) {
                        // TotalCountStatTpResponse stat =
                        // this.facadeTpDao.getStatTpDao().getTotalCountStat(id);
                        TotalCountStatTpResponse stat = videoService.getAlbumTotalCountStat(id,
                                commonParam);
                        if (stat != null) {
                            resourceInfo.setVv((stat.getPlist_play_count()));
                        }
                    }
                    if (album.getNowEpisodes() != null) {
                        resourceInfo.setEpisodes(Integer.parseInt(album.getNowEpisodes()));
                    }
                    resourceInfo.setReid(response.getReid());
                    resourceInfo.setBucket(response.getBucket());
                    resourceInfo.setAreaRec(response.getArea());
                    resourceInfo.setIs_rec(dto.getIs_rec());
                    resourceInfo = (ResourceInfo) JumpUtil.bulidJumpObj(resourceInfo, DataConstant.DATA_TYPE_ALBUM,
                            defaultStream, defaultStreamName);
                    data.add(resourceInfo);
                }
            }
        }

        return data;
    }

    /**
     * 获取卡片信息，搜索桌面
     * @param cardId
     *            卡片id
     * @param num
     *            数量
     * @param history
     * @param commonParam
     *            通用参数
     * @return
     */
    public PageResponse<SearchCardDto> getCardData(String cardId, Integer num, String history, String splatid,
                                                   String ph, String platform, String from, String src, String displayAppId, String displayPlatformId,
                                                   String fromWX, CommonParam commonParam) {
        String logPrefix = "getCardData_" + commonParam.getUserId() + "_" + commonParam.getMac() + "_"
                + commonParam.getBroadcastId();
        PageResponse<SearchCardDto> response = new PageResponse<SearchCardDto>();
        List<SearchCardDto> dataList = new ArrayList<SearchCardDto>();
        response.setData(dataList);
        String errCode = null;
        SearchCardDataResponse cardDataResponse = this.facadeTpDao.getSearchTpDao().getCardData(cardId, history, num,
                src, ph, platform, splatid, from, displayAppId, displayPlatformId, commonParam);

        if (cardDataResponse != null) {
            if (!CollectionUtils.isEmpty(cardDataResponse.getCard_data_list())) {
                for (SearchCardDataResponse.SearchCardData searchCardData : cardDataResponse.getCard_data_list()) {
                    SearchCardDto searchCardDto = new SearchCardDto(searchCardData, fromWX, commonParam);
                    dataList.add(searchCardDto);
                }
            }
            log.info(logPrefix + " return success ");
        } else {
            errCode = SearchConstant.SEARCH_ERROR_RESULT_NULL;
            log.info(logPrefix + " return null for " + cardId);
        }

        if (errCode != null) {
            response = this.setErrorResponse(response, errCode, commonParam.getLangcode());
        }
        return response;
    }

    public SearchCardDataResponse getCardData(String cardId, String history, Integer num, String src, String ph,
                                              String platform, String splatid, String from, String displayAppId, String displayPlatformId,
                                              CommonParam commonParam) {
        SearchCardDataResponse response = this.facadeTpDao.getSearchTpDao().getCardData(cardId, history, num, src, ph,
                platform, splatid, from, displayAppId, displayPlatformId, commonParam);

        return response;
    }

    /**
     * 获取流服务
     * @param url
     *            请求播放的url
     * @param os
     *            请求的操作系统
     * @param format
     *            清晰度详情 Format
     * @param source
     *            请求来源 TV 乐见等
     * @param provinceId
     *            请求所在的省份id
     * @param distinctId
     *            请求所在的地区id
     * @param site
     *            站源
     * @param aid
     *            视频id
     * @param vid
     *            视频所属专辑id
     * @param commonParam
     * @return
     */
    public PageResponse<GetStreamDto> getStream(String url, String os, String format, Integer source,
                                                String provinceId, String distinctId, String site, String aid, String vid, CommonParam commonParam) {
        List<GetStreamDto> list = new LinkedList<GetStreamDto>();
        PageResponse<GetStreamDto> response = new PageResponse<GetStreamDto>(list);
        if (!this.streamRequestFilter(provinceId, distinctId, site, aid, vid, commonParam)) {
            this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_FIBBDENAREA, commonParam.getWcode());
            return response;
        }

        GetStreamTpResponse result = this.facadeTpDao.getSearchTpDao().getStream(url, os, format,
                RequestSource.LEVIEW.getValue(), commonParam);
        if (result == null || CollectionUtils.isEmpty(result.getData())) {
            this.setErrorResponse(response, SearchConstant.SEARCH_ERROR_RESULT_NULL, commonParam.getWcode());
            return response;
        }

        for (ExtractData data : result.getData()) {
            list.add(new GetStreamDto(data));
        }

        return response;
    }

    private boolean streamRequestFilter(String provinceId, String distinctId, String site, String aid, String vid,
                                        CommonParam commonParam) {
        // ip白名单
        if (!StringUtil.isBlank(commonParam.getClientIp())
                && SearchConstant.STREAMFILTER_IP.contains(commonParam.getClientIp())) {
            return true;
        }
        // mac 白名单
        if (!StringUtil.isBlank(commonParam.getMac()) && SearchConstant.STREAMFILTER_MAC.contains(commonParam.getMac())) {
            return true;
        }
        // 总开关不限制白名单
        if (!SearchConstant.STREAMFILTER_SWITCH) {
            return false;
        }
        if (!StringUtil.isBlank(aid) && SearchConstant.STREAMFILTER_AID.contains(aid)) {
            return false;
        }
        if (!StringUtil.isBlank(provinceId)) {
            for (String tempProvinceId : SearchConstant.STREAMFILTER_AREA_PROVINCEID) {
                if (provinceId.equals(tempProvinceId)) {
                    return false;
                }
            }
        }
        if (!StringUtil.isBlank(site) && SearchConstant.STREAMFILTER_SITE.contains(site)) {
            return false;
        }
        return true;
    }

    /**
     * 更新流地址
     * @param url
     *            请求播放的url
     * @param os
     *            请求的操作系统
     * @param format
     *            清晰度详情 Format
     * @param source
     *            请求来源 TV 乐见等
     * @param failState
     *            流失败的原因 详情见FailState
     */
    public Boolean updateStream(String url, String os, String format, Integer source, Integer failState,
                                CommonParam commonParam) {
        return this.facadeTpDao.getSearchTpDao().updateStream(url, os, format, source, failState, commonParam);
    }

    /**
     * 上传客户端解析的流
     * @param url
     *            请求播放的Url
     * @param streamList
     *            解析的流地址
     * @param os
     *            请求的操作系统
     * @param format
     *            请求的流清晰度
     */
    public Boolean uploadLocalStream(String url, List<String> streamList, Integer os, Integer format,
                                     CommonParam commonParam) {
        return this.facadeTpDao.getSearchTpDao().uploadLocalStream(url, streamList, os, format, commonParam);
    }

    public Response<JSVersionDto> getJSVersion() {
        JSVersionDto jSVersionDto = this.facadeCacheDao.getSearchCacheDao().getJSVersion();
        return new Response<JSVersionDto>(jSVersionDto);
    }

}
