package com.letv.mas.caller.iptv.tvproxy.video.service;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.SubjectConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageResponse;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TpCPConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoAccess;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.GetSubjectTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockContentTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsPageTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.GoLiveNavigationDto;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.GoliveNavigationsDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.GoLiveNavigationDto.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsPageTpResponse.CmsPageTpResponseData.CmsPageTpResponseFrag;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.GoliveNavigationsDto.GoliveResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service(value = "v2.GoLiveService")
public class GoLiveService extends BaseService {

    private final static Logger LOG = LoggerFactory.getLogger(GoLiveService.class);

    public PageResponse<GoLiveNavigationDto> getMovieList(CommonParam commonParam) {
        String logPrefix = "getMovieList_";
        PageResponse<GoLiveNavigationDto> response = new PageResponse<GoLiveNavigationDto>();
        List<GoLiveNavigationDto> list = this.facadeCacheDao.getGoliveCacheDao()
                .getGoLiveNavigationDtoList(commonParam);
        if (!CollectionUtils.isEmpty(list)) {
            for (GoLiveNavigationDto goLiveNavigationDto : list) {
                List<GoLiveNavigationDto.MovieDto> movieDtoList = goLiveNavigationDto.getList();
                List<MovieDto> movieDtoListFilter = new LinkedList<MovieDto>();// 需要将golive片子分机型过滤
                String seriesList = ApplicationUtils.get(ApplicationConstants.IPTV_GOLIVE_SERIES_LIST, "");
                if (!CollectionUtils.isEmpty(movieDtoList)) {
                    for (MovieDto movieDto : movieDtoList) {
                        if (movieDto != null && movieDto.getValue() != null && movieDto.getValue().getCp() != null) {
                            if (movieDto.getValue().getCp().equals("golive")
                                    && (seriesList.contains("GOLIVE_ALL") || (commonParam.getTerminalSeries() != null && seriesList
                                            .contains(commonParam.getTerminalSeries())))) {
                                movieDtoListFilter.add(movieDto);
                                LOG.info(logPrefix + "golive ok ");
                            } else if (((MovieType) movieDto.getValue()).getCp().equals("letv")) {
                                movieDtoListFilter.add(movieDto);
                            } else {
                                LOG.info(logPrefix + commonParam.getTerminalApplication() + "is not in" + seriesList);
                            }
                        }
                    }
                    goLiveNavigationDto.setList(movieDtoListFilter);
                }
            }
        }
        response.setData(list);
        return response;
    }

    public PageResponse<List<GoliveNavigationsDto>> getMovieList2(CommonParam commonParam) {
        String logPrefix = "getMovieList3_";
        PageResponse<List<GoliveNavigationsDto>> response = new PageResponse<List<GoliveNavigationsDto>>();
        List<CmsPageTpResponse> cacheList = this.facadeCacheDao.getGoliveCacheDao().getCmsResultList(commonParam);

        if (!CollectionUtils.isEmpty(cacheList)) {
            List<List<GoliveNavigationsDto>> resultList = new LinkedList<List<GoliveNavigationsDto>>();
            response.setData(resultList);

            // 轮循所有页面结果解析
            for (CmsPageTpResponse cmsPageTpResponse : cacheList) {
                // 页面中没内容也添加，和客户端约定好固定位置的解析方式
                List<GoliveNavigationsDto> pageResult = new LinkedList<GoliveNavigationsDto>();
                resultList.add(pageResult);

                if (cmsPageTpResponse != null && cmsPageTpResponse.getData() != null
                        && !CollectionUtils.isEmpty(cmsPageTpResponse.getData().getFrags())) {
                    for (CmsPageTpResponseFrag cmsPageTpResponseFrag : cmsPageTpResponse.getData().getFrags()) {
                        // 页面内每一版块解析
                        GoliveNavigationsDto blockParsedResult = new GoliveNavigationsDto();
                        List<BaseData> baseDataList = new LinkedList<BaseData>();
                        blockParsedResult.setName(cmsPageTpResponseFrag.getContentName());
                        blockParsedResult.setList(baseDataList);
                        // 版块内的节目列表解析
                        if (!CollectionUtils.isEmpty(cmsPageTpResponseFrag.getBlockContents())) {
                            for (CmsBlockContentTpResponse cmsBlockContentTpResponse : cmsPageTpResponseFrag
                                    .getBlockContents()) {
                                BaseData data = this.parseGoliveData(cmsBlockContentTpResponse, commonParam);
                                if (data != null) {
                                    baseDataList.add(data);
                                }
                            }
                        } else {
                            LOG.info(logPrefix + "版块:" + cmsPageTpResponseFrag.getContentName() + "内容列表为空");
                        }
                        // 解析后版块内的数据列表不为空才添加
                        if (!CollectionUtils.isEmpty(blockParsedResult.getList())) {
                            pageResult.add(blockParsedResult);
                        }

                    }
                }

            }
        }

        return response;
    }

    private GoliveResultDto parseGoliveData(CmsBlockContentTpResponse cmsBlockContentTpResponse, CommonParam commonParam) {
        String logPrefix = "parseGoliveData_";
        try {
            int src = 0;
            String subSrc = null;
            int dataType = 0;
            Integer subjectType = null;
            String id = cmsBlockContentTpResponse.getContent();
            String name = cmsBlockContentTpResponse.getTitle();
            String subName = cmsBlockContentTpResponse.getSubTitle();
            String introduction = cmsBlockContentTpResponse.getShorDesc();
            Long startTime = null;
            Long endTime = null;
            String releaseTime = cmsBlockContentTpResponse.getExtendJson().getExtendRadio();
            if (!StringUtil.isBlank(releaseTime)) {
                String[] time = releaseTime.split(",");
                startTime = TimeUtil.string2timestamp(time[0]);
                endTime = TimeUtil.string2timestamp(time[1]);
            }
            String score = cmsBlockContentTpResponse.getRemark();
            String actor = cmsBlockContentTpResponse.getPosition();
            String ipc = cmsBlockContentTpResponse.getUrl();
            String url = cmsBlockContentTpResponse.getSkipUrl();
            String tvskip = cmsBlockContentTpResponse.getExtendJson().getExtendPage();
            String price = cmsBlockContentTpResponse.getAndroidUrl();
            String message = cmsBlockContentTpResponse.getTag();
            String duration = cmsBlockContentTpResponse.getTagUrl();// 该字段用于运营的上下线操作
            String poster = cmsBlockContentTpResponse.getPic1();
            String tagImg = cmsBlockContentTpResponse.getPic2();
            String jumpImg = cmsBlockContentTpResponse.getTvPic();
            Integer openType = null;
            Map<String, String> playPlatform = cmsBlockContentTpResponse.getPlayPlatform();
            Integer saleStatus = null;
            String typeId = cmsBlockContentTpResponse.getExtendJson().getExtendSubscript();
            if (typeId.equals("271")) {// 测试为271
                saleStatus = TpCPConstants.GOLIVE_SALESTATUS_FREE;
            } else if (typeId.equals("269")) {// 测试为269
                saleStatus = TpCPConstants.GOLIVE_SALESTATUS_FUTURE;
            } else if (typeId.equals("270")) {// 测试为103
                saleStatus = TpCPConstants.GOLIVE_SALESTATUS_SALE;
            }

            // 上映时间段过滤
            if (!StringUtil.isBlank(duration)) {
                String[] time = duration.split(",");
                long now = System.currentTimeMillis();
                // 不在运营设置的时间段 则下线
                if (TimeUtil.string2timestamp(time[0]) > now || now > TimeUtil.string2timestamp(time[1])) {
                    LOG.error(logPrefix + cmsBlockContentTpResponse.getTitle() + "该片子不在上线期间");
                    return null;
                }
            }
            // 平台过滤
            if (!CollectionUtils.isEmpty(playPlatform)) {
                Iterator<String> iterator = playPlatform.keySet().iterator();
                boolean play = false;
                while (iterator.hasNext()) {
                    if (CommonConstants.TV_PLAY_PLAT_FROM.equals(iterator.next())) {
                        play = true;
                        break;
                    }
                }
                if (!play) {
                    LOG.error(logPrefix + cmsBlockContentTpResponse.getTitle() + "该片子未勾选TV平台");
                    return null;
                }
            } else {
                LOG.error(logPrefix + cmsBlockContentTpResponse.getTitle() + "该片子未勾选TV平台");
                return null;
            }

            if (StringUtil.isBlank(id)) {
                if (!StringUtil.isBlank(tvskip)) {
                    // 芈月传容器
                    String[] extendPageElements = tvskip.split("\\|");
                    id = extendPageElements[2];
                    dataType = DataConstant.DATA_TYPE_CONTAINER;
                    src = 1;
                } else if (!StringUtil.isBlank(url)) {
                    // 外跳浏览器
                    dataType = DataConstant.DATA_TYPE_BROWSER;
                    openType = 3;
                }
            } else if (cmsBlockContentTpResponse.getType() == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_VIDEO) {
                dataType = DataConstant.DATA_TYPE_VIDEO;
                src = 1;
            } else if (cmsBlockContentTpResponse.getType() == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_ALBUM) {
                dataType = DataConstant.DATA_TYPE_ALBUM;
                src = 1;
            } else if (cmsBlockContentTpResponse.getType() == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_WEBSITE_ALBUM) {
                dataType = DataConstant.DATA_TYPE_ALBUM;
                src = 2;
                subSrc = "golive";// 来源方，暂时写死在这，后面改为cms配
            } else if (cmsBlockContentTpResponse.getType() == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_SUBJECT) {
                dataType = DataConstant.DATA_TYPE_SUBJECT;
                src = 1;
                GetSubjectTpResponse getSubjectTpResponse = this.facadeTpDao.getCmsTpDao().getCmsSubjectById(
                        cmsBlockContentTpResponse.getContent(), commonParam);
                subjectType = SubjectConstant.getSubjectTypeFromTemplate(getSubjectTpResponse.getData()
                        .getTemplateJson().getTvTemplate());
            } else if (cmsBlockContentTpResponse.getType() == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_TVSTATION) {
                dataType = DataConstant.DATA_TYPE_TVSTATION;
                src = 1;
            }

            GoliveResultDto goliveDto = new GoliveResultDto(id, src, subSrc, dataType, subjectType, name, actor, score,
                    introduction, jumpImg, ipc, tagImg, url, openType, price, poster, subName, startTime, endTime,
                    saleStatus, message, commonParam);

            return goliveDto;
        } catch (Exception e) {
            LOG.info(logPrefix + cmsBlockContentTpResponse.getTitle() + "该片子配置有误，请确认", e);
            return null;
        }
    }
}
