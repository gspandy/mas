package com.letv.mas.caller.iptv.tvproxy.channel.service;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.ErrorCodeConstants;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.LetvUserVipCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.*;
import com.letv.mas.caller.iptv.tvproxy.channel.constants.ChannelConstants;
import com.letv.mas.caller.iptv.tvproxy.channel.model.dto.BlockContentDto;
import com.letv.mas.caller.iptv.tvproxy.channel.model.dto.ChannelDto;
import com.letv.mas.caller.iptv.tvproxy.channel.model.dto.ResourceDto;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ProductLineConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoAccess;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.activity.ActivityTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.WFSubjectDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.WFSubjectPackageDataDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.WFSubjectPackageDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.WFSubjectTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockContentTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsPageTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.AlbumDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoDto;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.DPilotDto;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.PilotDto;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.PromotionDto;
import com.letv.mas.caller.iptv.tvproxy.vip.service.VipV2Service;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsPageTpResponse.CmsPageTpResponseData.CmsPageTpResponseFrag;

import java.util.*;

@Component(value = "v2.ChannelV2Service")
public class ChannelV2Service extends BaseService {

    @Autowired
    ChannelService channelService;
    
    @Autowired
    VipV2Service vipV2Service;

    @Autowired
    AlbumVideoAccess albumVideoAccess;
    
    public List<ChannelDto> getMine(CommonParam commonParam) {
        List<ChannelDto> dataList = new ArrayList<ChannelDto>();
        // TODO: 测试和线上环境配置切换！！！
        String url = ApplicationUtils.get(ApplicationConstants.CMS_BLOCK_MINE_1190_GET);
        if ("hk".equals(commonParam.getWcode())) {
            url = ApplicationUtils.get(ApplicationConstants.CMS_BLOCK_MINE_1598_GET);
        }

        // 组装该频道所需板块资源
        CmsBlockTpResponse response = this.facadeTpDao.getCmsTpDao().getCmsBlockNew(url, commonParam);
        ChannelDto channelDto = new ChannelDto();
        dataList.add(channelDto);
        this.rebuildCmsData(ChannelConstants.MY, channelDto, response, commonParam);

        if (TerminalUtil.isLetvUs(commonParam) || TerminalUtil.isLetvCommon(commonParam)) {
            return dataList;// letv_us or letv_common version no activity
        }

        // 组装该频道所需推荐数据
        BlockContentDto vipRecommend = new BlockContentDto();// “我的”第3列，会员推荐位数据
        vipRecommend.setType(null);
        this.rebuildCmsData(ChannelConstants.MY, vipRecommend, response, commonParam);

        channelDto.setVipRecommend(vipRecommend);

        // 2016-03-08活动走会员运营中心，不走CMS，如果第7个触达位没有活动触达，则走CMS获取4个会员专享专辑
        List<BaseData> commonActivityList = new LinkedList<BaseData>();
        String recTvPic = null;
        String activityDefaultImg = null;
        Map<String, String> configMap = channelService.getVideoSaleConfigs();
        if (!CollectionUtils.isEmpty(configMap)) {
            recTvPic = configMap.get(ChannelConstants.CHANNEL_MINE_RECOMMEND_DEFAULT_IMG);
            activityDefaultImg = configMap.get(ChannelConstants.CHANNEL_MINE_DEFAULT_IMG);
        }
        // String[] positions = VipTpConstant.urm_positions_mine;
        // for (String position : positions) {
        // GetUrmActivityResponse tpResponse =
        // this.facadeTpDao.getVipTpDao().getUrmTouchData(position, deviceType,
        // commonParam);
        // PilotDto dto =
        // this.facadeService.getVipMetadataService().parseUrmActivityData(tpResponse,
        // position,
        // commonParam);
        // if (dto != null) {
        // if (position != null &&
        // position.equals(VipTpConstant.URM_POSITION_MINE_4)) {// 第4个位置，独立出来
        // vipRecList.add(dto);
        // } else {
        // commonActivityList.add(dto);
        // }
        // } else {
        // if (!(position != null &&
        // position.equals(VipTpConstant.URM_POSITION_MINE_4))) {// 第4个位置，独立出来
        // Browser browser = new Browser();
        // browser.setImg(activityDefaultImg);
        // JumpData jump = new JumpData();
        // jump.setType(DataConstant.DATA_TYPE_BROWSER);
        // Browser skip = new Browser();
        // skip.setBrowserType(DataConstant.BROWSER_TYPE_BUILTIN);
        // skip.setOpenType(-1);
        // jump.setValue(skip);
        // browser.setJump(jump);
        // commonActivityList.add(browser);
        // }
        // }
        // }
        String posIds = ActivityTpConstant.ACTIVITY_CLIENT_POSITION_MINE1 + ","
                + ActivityTpConstant.ACTIVITY_CLIENT_POSITION_MINE2 + ","
                + ActivityTpConstant.ACTIVITY_CLIENT_POSITION_MINE3;
        String[] positions = ActivityTpConstant.positions_mine;
        // Map<String,PromotionDto> starGazerData =
        // vipV2Service.getStarGazerMapData("",posIds,
        // commonParam);
        // if(starGazerData != null && starGazerData.size() > 0){
        // String[] positions = ActivityTpConstant.positions_mine;
        // for (String position : positions) {
        // PromotionDto mPromotionDto = starGazerData.get(position);
        // if(mPromotionDto != null){
        // PilotDto dto =
        // vipV2Service.parseGuanXingActivityData(mPromotionDto,
        // commonParam);
        // if (dto != null) {
        // commonActivityList.add(dto);
        // }
        // }else{
        // Browser browser = new Browser();
        // browser.setImg(activityDefaultImg);
        // JumpData jump = new JumpData();
        // jump.setType(DataConstant.DATA_TYPE_BROWSER);
        // Browser skip = new Browser();
        // skip.setBrowserType(DataConstant.BROWSER_TYPE_BUILTIN);
        // skip.setOpenType(-1);
        // jump.setValue(skip);
        // browser.setJump(jump);
        // commonActivityList.add(browser);
        // }
        // }
        // }
        if (!TerminalUtil.supportMineThreeInOne(commonParam)) {
            Map<String, DPilotDto> pilotDtoMap = vipV2Service.getStarGazerMapDataForUrm("",
                    posIds, commonParam);
            if (pilotDtoMap != null && pilotDtoMap.size() > 0) {
                for (String position : positions) {
                    PilotDto dto = pilotDtoMap.get(position);
                    if (dto != null) {
                        commonActivityList.add(dto);
                    } else {
                        Browser browser = new Browser();
                        browser.setImg(activityDefaultImg);
                        JumpData jump = new JumpData();
                        jump.setType(DataConstant.DATA_TYPE_BROWSER);
                        Browser skip = new Browser();
                        skip.setBrowserType(DataConstant.BROWSER_TYPE_BUILTIN);
                        skip.setOpenType(-1);
                        jump.setValue(skip);
                        browser.setJump(jump);
                        commonActivityList.add(browser);
                    }
                }
            }
        }
        if (!CollectionUtils.isEmpty(commonActivityList)) {// 中间列活动数据不为空，则返回
            channelDto.setActivities(commonActivityList);
        }

        return dataList;
    }

    /**
     * 根据客户端版本处理频道
     * @param baseDto
     * @param response
     * @param commonParam
     */
    private void rebuildCmsData(int cType, BaseDto baseDto, CmsBlockTpResponse response, CommonParam commonParam) {
        if (baseDto == null) {
            return;
        }

        String area = StringUtils.trimToNull(commonParam.getSalesArea());
        Integer ac = TerminalUtil.parseAppCode(commonParam.getAppCode());
        if (area == null) {
            area = commonParam.getWcode();
        }

        if (cType == ChannelConstants.MY) { // ［我的］
            if (baseDto instanceof ChannelDto) {
                ChannelDto channelDto = (ChannelDto) baseDto;
                if ((TerminalCommonConstant.TERMINAL_APPLICATION_CIBN.equalsIgnoreCase(commonParam
                        .getTerminalApplication()) && ProductLineConstants.WCODE.LETV_CN.equalsIgnoreCase(area) && ac > 304)) {
                    String[] positions = new String[] { "focus", "col4" };
                    List<BaseData> resources = new ArrayList<BaseData>();
                    if (response != null && response.getBlockContent() != null) {
                        for (CmsBlockContentTpResponse dataInfo : response.getBlockContent()) {
                            if (dataInfo != null && StringUtil.isNotBlank(dataInfo.getPosition())
                                    && ArrayUtils.contains(positions, dataInfo.getPosition())) {
                                // 2015-09-09，将“我的”Tab页老业务扩展，该功能需要升级
                                BaseData resource = channelService.getDataFromCms(dataInfo);
                                if (resource != null) {
                                    resources.add(resource);
                                }
                            }
                        }
                    }
                    channelDto.setResources(resources);
                } else {
                    List<BaseData> resources = new ArrayList<BaseData>();
                    if (response != null && response.getBlockContent() != null) {
                        for (CmsBlockContentTpResponse dataInfo : response.getBlockContent()) {
                            if (dataInfo != null) {
                                // 2015-09-09，将“我的”Tab页老业务扩展，该功能需要升级
                                BaseData resource = channelService.getDataFromCms(dataInfo);
                                if (resource != null) {
                                    resources.add(resource);
                                }
                            }
                        }
                    }
                    channelDto.setResources(resources);
                }
            } else if (baseDto instanceof BlockContentDto) {
                BlockContentDto blockContentDto = (BlockContentDto) baseDto;
                boolean needGetRecommendationBlock = true;

                if (needGetRecommendationBlock) {
                    List<BaseData> vipRecList = new LinkedList<BaseData>();
                    String recTvPic = null;
                    String activityDefaultImg = null;

                    Map<String, String> configMap = channelService.getVideoSaleConfigs();
                    if (!CollectionUtils.isEmpty(configMap)) {
                        recTvPic = configMap.get(ChannelConstants.CHANNEL_MINE_RECOMMEND_DEFAULT_IMG);
                        activityDefaultImg = configMap.get(ChannelConstants.CHANNEL_MINE_DEFAULT_IMG);
                    }

                    if (!CollectionUtils.isEmpty(vipRecList)) {
                        blockContentDto.setDataList(vipRecList);
                        blockContentDto.setRecommendDataType(0);
                        blockContentDto.setTvPic(recTvPic);
                    } else if (response != null && !CollectionUtils.isEmpty(response.getSubBlockList())) {// 第三列活动位如果没有触达，则需要从CMS获取配置的会员专享专辑
                        List<String> subBlocksList = response.getSubBlockList();
                        String blockIds = StringUtil.getListToString(subBlocksList, ",");
                        Map<String, CmsBlockTpResponse> cmsDataResponses = this.facadeTpDao.getCmsTpDao()
                                .getCmsBlockNewsById(blockIds, commonParam);
                        if (null != cmsDataResponses) {
                            CmsBlockTpResponse cmsDataResponse = null;
                            int recommendDataType = 1;
                            for (String blockId : subBlocksList) {
                                if (StringUtils.isNotBlank(blockId) && null != cmsDataResponses.get(blockId)) {
                                    cmsDataResponse = cmsDataResponses.get(blockId);
                                    if (cmsDataResponse == null) {
                                        continue;
                                    }
                                    String name = cmsDataResponse.getName();
                                    if (ChannelConstants.CHANNEL_MINE_BLOCK_HUIYUAN.equalsIgnoreCase(name)) {
                                        // 会员专享版块，年前需求暂时无法对接大屏会员中心后台数据，所以通过在“我的”版块中配置子版块"huiyuan"
                                        List<BaseData> albumList = new LinkedList<BaseData>();
                                        BaseData col3Data = null;
                                        for (CmsBlockContentTpResponse dataInfo : cmsDataResponse.getBlockContent()) {
                                            if (dataInfo != null
                                                    && dataInfo.getPushflag() != null
                                                    && dataInfo.getPushflag()
                                                            .contains(CommonConstants.TV_PLATFROM_CODE)) {
                                                if (StringUtil.isNotBlank(dataInfo.getPosition())
                                                        && "col3".equals(dataInfo.getPosition())) {
                                                    col3Data = channelService.getDataFromCms(
                                                            dataInfo);
                                                } else {
                                                    BaseData baseData = channelService
                                                            .getDataFromCms(dataInfo, commonParam, null, null, null);
                                                    if (baseData != null) {// 所有用户都推单视频
                                                        if (baseData instanceof AlbumDto) {// 专辑数据列表
                                                            albumList.add(baseData);
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        if ((TerminalCommonConstant.TERMINAL_APPLICATION_CIBN
                                                .equalsIgnoreCase(commonParam.getTerminalApplication())
                                                && ProductLineConstants.WCODE.LETV_CN.equalsIgnoreCase(area) && ac > 304)) {
                                            // TODO-DONE: 新版本:
                                            // 当标题、副标题、TV图片3个字段均为空值或对应板块下没有权重≥0的数据时，显示原影视会员推荐板块内容
                                            if (null != col3Data && col3Data instanceof ResourceDto) {
                                                ResourceDto resourceDto = (ResourceDto) col3Data;
                                                if (StringUtil.isNotBlank(resourceDto.getTitle())
                                                        || StringUtil.isNotBlank(resourceDto.getSubTitle())
                                                        || StringUtil.isNotBlank(resourceDto.getImg())) {
                                                    albumList.clear();
                                                    albumList.add(resourceDto);
                                                    recommendDataType = 2;
                                                    recTvPic = null; // TODO:
                                                                     // 设置默认图片！
                                                }
                                            }
                                        }

                                        if (albumList.size() > 0) {
                                            blockContentDto.setDataList(albumList);
                                            blockContentDto.setRecommendDataType(recommendDataType);
                                            blockContentDto.setTvPic(recTvPic);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 新瀑布流专题详情接口
     * @param subjectId
     * @param commonParam
     * @return
     */
    public Response<WFSubjectDto> getSubjectPackagesById(String subjectId, CommonParam commonParam, boolean isUseCache) {
        Response<WFSubjectDto> response = new Response<WFSubjectDto>();
        if (StringUtil.isBlank(subjectId)) {
            response = (Response<WFSubjectDto>) LetvUserVipCommonConstant.setErrorResponse(response,
                    ErrorCodeConstants.GET_ACTIVITY_PARAM_FAILURE, commonParam.getLangcode());
            return response;
        }

        WFSubjectDto mWFSubjectDto = null;

        if (isUseCache) {
            mWFSubjectDto = this.facadeCacheDao.getChannelCacheDao().getWFSubjectData(subjectId, commonParam);
        }

        if (mWFSubjectDto == null) {
            mWFSubjectDto = getWFSubjectDtoById(subjectId, commonParam);
            if (mWFSubjectDto != null && isUseCache) {
                this.facadeCacheDao.getChannelCacheDao().setWFSubjectData(subjectId, commonParam, mWFSubjectDto);
            }
        }

        /*
         * 因为该未知跟专题内容没有关系，因此由客户端在app启动时随所有位置请求一次，再拼接即可。
         */
        // supplementWFSubjectDataFormGuanXing(mWFSubjectDto,commonParam);

        /*
         * 由于分端付费，实时解析banner数据，生成icon图标
         */
        supplementParseSubjectPackageDataForBannerIcon(mWFSubjectDto, commonParam);

        if (mWFSubjectDto != null) {
            response.setData(mWFSubjectDto);
        }

        return response;
    }

    /**
     * 新瀑布流专题详情生成静态文件接口
     * @param subjectId
     * @param commonParam
     * @return
     */
    public Response<WFSubjectDto> getSubjectPackagesByIdCreateStatic(String subjectId, CommonParam commonParam) {
        WFSubjectDto mWFSubjectDto = getWFSubjectDtoById(subjectId, commonParam);
        Response<WFSubjectDto> response = new Response<WFSubjectDto>();
        response.setData(mWFSubjectDto);
        return response;
    }

    public WFSubjectDto getWFSubjectDtoByStatic(String subjectId, CommonParam commonParam) {
        WFSubjectDto wfSubjectDto = null;
        WFSubjectTpResponse mWFSubjectTpResponse = this.facadeTpDao.getChannelTpDao().getWFSubjectById(subjectId);
        if (mWFSubjectTpResponse != null && mWFSubjectTpResponse.getData() != null) {
            wfSubjectDto = mWFSubjectTpResponse.getData();
        } else {
            wfSubjectDto = new WFSubjectDto();
        }
        return wfSubjectDto;
    }

    /**
     * 从cms获取瀑布流专题的内容
     * @param subjectId
     * @param commonParam
     * @return
     */
    public WFSubjectDto getWFSubjectDtoById(String subjectId, CommonParam commonParam) {
        WFSubjectDto mWFSubjectDto = null;
        List<WFSubjectPackageDto> list = new ArrayList<WFSubjectPackageDto>();
        boolean hasBanner = false;
        CmsPageTpResponse cmsPageTpResponse = this.facadeTpDao.getCmsTpDao().getCmsPage(subjectId, commonParam);
        if (cmsPageTpResponse != null && cmsPageTpResponse.getData() != null) {
            mWFSubjectDto = new WFSubjectDto();
            // supplementVideoForCmsPackageData(cmsPageTpResponse,commonParam);
            List<CmsPageTpResponseFrag> cmsPageTpResponseFragList = cmsPageTpResponse.getData().getFrags();
            if (cmsPageTpResponseFragList != null && cmsPageTpResponseFragList.size() > 0) {
                for (CmsPageTpResponseFrag cmsPageTpResponseFrag : cmsPageTpResponseFragList) {
                    if (cmsPageTpResponseFrag == null) {
                        continue;
                    }
                    String style = cmsPageTpResponseFrag.getContentStyle();
                    if (style == null) {
                        continue;
                    }
                    if (style.equals(StringUtil.toString(ChannelConstants.WF_SUBJECT_CMS_TYPE_BANNER_1))
                            || style.equals(StringUtil.toString(ChannelConstants.WF_SUBJECT_CMS_TYPE_BANNER_2))) {
                        if (!hasBanner) {
                            hasBanner = true;
                        } else {
                            continue;
                        }
                    }
                    list.add(parseSubjectPackage(cmsPageTpResponseFrag, commonParam));
                }
                mWFSubjectDto.setPackageList(list);
            }
            mWFSubjectDto.setId(subjectId);
        }
        return mWFSubjectDto;
    }

    /**
     * 解析cms瀑布流专题数据
     * @param cmsPageTpResponseFrag
     * @param commonParam
     * @return
     */
    public WFSubjectPackageDto parseSubjectPackage(CmsPageTpResponseFrag cmsPageTpResponseFrag, CommonParam commonParam) {
        WFSubjectPackageDto mWFSubjectPackageDto = new WFSubjectPackageDto();
        List<WFSubjectPackageDataDto> wfSubjectPackageDataDtosList = new ArrayList<WFSubjectPackageDataDto>();
        if (cmsPageTpResponseFrag != null) {
            mWFSubjectPackageDto.setId(cmsPageTpResponseFrag.getContentId());
            mWFSubjectPackageDto.setName(cmsPageTpResponseFrag.getContentName());
            mWFSubjectPackageDto.setType(parseContentStyle(cmsPageTpResponseFrag.getContentStyle()));
            List<CmsBlockContentTpResponse> list = cmsPageTpResponseFrag.getBlockContents();
            Integer dateSize = cmsPageTpResponseFrag.getContentManulNum();// cms显示的data的数量，解析时只解析datasize个
            if (dateSize == null || dateSize < 1) {
                return mWFSubjectPackageDto;
            }
            if (list != null && list.size() > 0) {
                for (CmsBlockContentTpResponse cmsBlockContentTpResponse : list) {
                    if (cmsBlockContentTpResponse != null) {
                        Map<String, String> platForms = cmsBlockContentTpResponse.getPlayPlatform();
                        if (platForms == null || !platForms.containsKey(CommonConstants.TV_PLAY_PLAT_FROM)) {
                            continue;
                        }
                        WFSubjectPackageDataDto mWFSubjectPackageDataDto = new WFSubjectPackageDataDto();
                        mWFSubjectPackageDataDto.setmType(mWFSubjectPackageDto.getType());
                        parseSubjectPackageData(mWFSubjectPackageDataDto, cmsBlockContentTpResponse, commonParam);
                        setJumpData(mWFSubjectPackageDataDto, commonParam);
                        supplementParseSubjectPackageData(mWFSubjectPackageDataDto, cmsBlockContentTpResponse,
                                commonParam);
                        wfSubjectPackageDataDtosList.add(mWFSubjectPackageDataDto);
                        /*
                         * if(mWFSubjectPackageDto.getType().equals(ChannelConstants
                         * .WF_SUBJECT_TYPE_BANNER_1)){
                         * if(wfSubjectPackageDataDtosList.size() == 1){
                         * break;
                         * }
                         * }
                         */
                        if (wfSubjectPackageDataDtosList.size() == dateSize) {
                            break;
                        }
                    }
                }
            }
            if (wfSubjectPackageDataDtosList.size() > 0) {
                mWFSubjectPackageDto.setDataList(wfSubjectPackageDataDtosList);
            }
        }

        return mWFSubjectPackageDto;
    }

    public void supplementVideoForCmsPackageData(CmsPageTpResponse cmsPageTpResponse, CommonParam commonParam) {
        String[] idarray = null;
        Set<String> ids = new HashSet<>();
        List<CmsPageTpResponseFrag> cmsPageTpResponseFragList = cmsPageTpResponse.getData().getFrags();
        if (cmsPageTpResponseFragList != null && cmsPageTpResponseFragList.size() > 0) {
            for (CmsPageTpResponseFrag cmsPageTpResponseFrag : cmsPageTpResponseFragList) {
                List<CmsBlockContentTpResponse> list = cmsPageTpResponseFrag.getBlockContents();
                if (list != null && list.size() > 0) {
                    for (CmsBlockContentTpResponse cmsBlockContentTpResponse : list) {
                        if (cmsBlockContentTpResponse != null
                                && cmsBlockContentTpResponse.getType() != null
                                && cmsBlockContentTpResponse.getType()
                                        .equals(CmsTpConstant.CMS_BLOCKCONTENT_TYPE_VIDEO)) {
                            ids.add(cmsBlockContentTpResponse.getContent());
                        }
                    }
                }
            }
        }
        if (ids.size() == 0) {
            return;
        }
        idarray = ids.toArray(new String[ids.size()]);
        if (idarray == null) {
            return;
        }
        Map<String, CmsBlockContentTpResponse.BlockContentVideo> maps = new HashMap<>();
        List<VideoMysqlTable> videoMysqlTables = albumVideoAccess.getVideoList(idarray,
                commonParam);
        if (videoMysqlTables != null) {
            for (VideoMysqlTable videoMysqlTable : videoMysqlTables) {
                if (videoMysqlTable != null) {
                    maps.put(videoMysqlTable.getId() + "", changeValue(videoMysqlTable));
                }
            }
        }
        if (maps.size() == 0) {
            return;
        }
        if (cmsPageTpResponseFragList != null && cmsPageTpResponseFragList.size() > 0) {
            for (CmsPageTpResponseFrag cmsPageTpResponseFrag : cmsPageTpResponseFragList) {
                List<CmsBlockContentTpResponse> list = cmsPageTpResponseFrag.getBlockContents();
                if (list != null && list.size() > 0) {
                    for (CmsBlockContentTpResponse cmsBlockContentTpResponse : list) {
                        if (cmsBlockContentTpResponse != null
                                && cmsBlockContentTpResponse.getType() != null
                                && cmsBlockContentTpResponse.getType()
                                        .equals(CmsTpConstant.CMS_BLOCKCONTENT_TYPE_VIDEO)) {
                            CmsBlockContentTpResponse.BlockContentVideo video = maps.get(cmsBlockContentTpResponse
                                    .getContent());
                            if (video != null) {
                                cmsBlockContentTpResponse.setVideo(video);
                            }
                        }
                    }
                }
            }
        }
    }

    public CmsBlockContentTpResponse.BlockContentVideo changeValue(VideoMysqlTable mVideoMysqlTable) {
        CmsBlockContentTpResponse.BlockContentVideo video = null;
        if (mVideoMysqlTable != null) {
            video = new CmsBlockContentTpResponse.BlockContentVideo();
            Long pid = mVideoMysqlTable.getPid();
            if (pid != null) {
                video.setPid(pid.intValue());
            }
        }
        return video;
    }

    /**
     * 解析cms定义的模版类型
     * @param contentStyle
     * @return
     */
    public Integer parseContentStyle(String contentStyle) {
        Integer type = StringUtil.toInteger(contentStyle, null);
        if (type != null) {
            if (type == ChannelConstants.WF_SUBJECT_CMS_TYPE_BANNER_1) {
                return ChannelConstants.WF_SUBJECT_TYPE_BANNER_1;
            } else if (type == ChannelConstants.WF_SUBJECT_CMS_TYPE_BANNER_2) {
                return ChannelConstants.WF_SUBJECT_TYPE_BANNER_2;
            } else if (type == ChannelConstants.WF_SUBJECT_CMS_TYPE_BODY_1) {
                return ChannelConstants.WF_SUBJECT_TYPE_BODY_1;
            } else if (type == ChannelConstants.WF_SUBJECT_CMS_TYPE_BODY_2) {
                return ChannelConstants.WF_SUBJECT_TYPE_BODY_2;
            } else if (type == ChannelConstants.WF_SUBJECT_CMS_TYPE_BODY_3) {
                return ChannelConstants.WF_SUBJECT_TYPE_BODY_3;
            } else if (type == ChannelConstants.WF_SUBJECT_CMS_TYPE_BODY_4) {
                return ChannelConstants.WF_SUBJECT_TYPE_BODY_4;
            } else if (type == ChannelConstants.WF_SUBJECT_CMS_TYPE_BODY_5) {
                return ChannelConstants.WF_SUBJECT_TYPE_BODY_5;
            } else if (type == ChannelConstants.WF_SUBJECT_CMS_TYPE_BODY_6) {
                return ChannelConstants.WF_SUBJECT_TYPE_BODY_6;
            } else if (type == ChannelConstants.WF_SUBJECT_CMS_TYPE_BODY_7) {
                return ChannelConstants.WF_SUBJECT_TYPE_BODY_7;
            }
        }
        return -1;
    }

    /**
     * 解析cms数据
     * @param mWFSubjectPackageDataDto
     * @param cmsBlockContentTpResponse
     * @param commonParam
     */
    public void parseSubjectPackageData(WFSubjectPackageDataDto mWFSubjectPackageDataDto,
            CmsBlockContentTpResponse cmsBlockContentTpResponse, CommonParam commonParam) {
        mWFSubjectPackageDataDto.setTitle(cmsBlockContentTpResponse.getTitle());
        mWFSubjectPackageDataDto.setSubTitle(cmsBlockContentTpResponse.getSubTitle());
        mWFSubjectPackageDataDto.setTvPic(cmsBlockContentTpResponse.getTvPic());
        mWFSubjectPackageDataDto.setId(cmsBlockContentTpResponse.getContent());
        mWFSubjectPackageDataDto.setdType(cmsBlockContentTpResponse.getType());
        mWFSubjectPackageDataDto.setPicList(cmsBlockContentTpResponse.getPicList());
        Integer type = mWFSubjectPackageDataDto.getdType();
        if (type.equals(CmsTpConstant.CMS_BLOCKCONTENT_TYPE_VIDEO)) {
            CmsBlockContentTpResponse.BlockContentVideo video = cmsBlockContentTpResponse.getVideo();
            if (video != null && video.getPid() != null) {
                mWFSubjectPackageDataDto.setfId(video.getPid() + "");
            }
        }
    }

    /**
     * 设置每个item的jump跳转信息
     * @param mWFSubjectPackageDataDto
     * @param commonParam
     */
    public void setJumpData(WFSubjectPackageDataDto mWFSubjectPackageDataDto, CommonParam commonParam) {
        Integer type = mWFSubjectPackageDataDto.getdType();
        WFSubjectPackageDataDto.JumpData jumpData = new WFSubjectPackageDataDto.JumpData();
        if (type != null) {
            Extension extend = new Extension();
            extend.setIsParse(0);
            extend.setAction("com.letv.external.new");
            extend.setResource("1");
            jumpData.setExtend(extend);
            if (type.equals(CmsTpConstant.CMS_BLOCKCONTENT_TYPE_VIDEO)) {
                /*
                 * VideoMysqlTable video =
                 * albumVideoAccess.getVideo(
                 * Long.valueOf(mWFSubjectPackageDataDto.getId()), commonParam);
                 * if(video != null){
                 * mWFSubjectPackageDataDto.setfId(video.getPid()+"");
                 * }
                 */
                VideoDto video_skip = new VideoDto();
                video_skip.setAlbumId(mWFSubjectPackageDataDto.getfId());
                video_skip.setVideoId(mWFSubjectPackageDataDto.getId());
                video_skip.setName(mWFSubjectPackageDataDto.getTitle());
                video_skip.setSubName(mWFSubjectPackageDataDto.getSubTitle());
                video_skip.setCategoryId(mWFSubjectPackageDataDto.getCategoryId());
                video_skip.setTvCopyright(1);
                video_skip.setWebPlayUrl("");
                video_skip.setWebsite("");
                jumpData.setValue(video_skip);
                jumpData.setType(DataConstant.DATA_TYPE_VIDEO);
            } else if (type.equals(CmsTpConstant.CMS_BLOCKCONTENT_TYPE_ALBUM)) {
                AlbumDto album_skip = new AlbumDto();
                album_skip.setTvCopyright(1);
                album_skip.setAlbumId(mWFSubjectPackageDataDto.getId());
                album_skip.setSrc(1);
                album_skip.setDataType(type);
                jumpData.setValue(album_skip);
                jumpData.setType(DataConstant.DATA_TYPE_ALBUM);

            }
        }
        mWFSubjectPackageDataDto.setDataType(jumpData.getType());
        mWFSubjectPackageDataDto.setJump(jumpData);
    }

    /**
     * 额外解析banner1的数据
     * @param wFSubjectPackageDataDto
     * @param cmsBlockContentTpResponse
     * @param commonParam
     */
    public void supplementParseSubjectPackageData(WFSubjectPackageDataDto wFSubjectPackageDataDto,
            CmsBlockContentTpResponse cmsBlockContentTpResponse, CommonParam commonParam) {
        /*
         * 类型 banner1 需要解析是否是会员片
         */
        if (wFSubjectPackageDataDto != null) {
            Integer dType = wFSubjectPackageDataDto.getdType();
            if (wFSubjectPackageDataDto.getmType() != null
                    && wFSubjectPackageDataDto.getmType() == ChannelConstants.WF_SUBJECT_TYPE_BANNER_1) {
                if (dType != null && dType.equals(CmsTpConstant.CMS_BLOCKCONTENT_TYPE_VIDEO)) {
                    String videoId = wFSubjectPackageDataDto.getId();
                    if (StringUtil.isBlank(videoId)) {
                        return;
                    }
                    Long vid = StringUtil.toLong(videoId, null);
                    Long pid = null;
                    if (StringUtil.isNotBlank(wFSubjectPackageDataDto.getfId())) {
                        pid = StringUtil.toLong(wFSubjectPackageDataDto.getfId(), null);
                    }
                    /*
                     * String name = null;
                     * if(vid != null && pid == null){
                     * VideoMysqlTable videoMysql =
                     * albumVideoAccess.getVideo(vid,
                     * commonParam);
                     * if(videoMysql != null){
                     * pid = videoMysql.getPid();
                     * wFSubjectPackageDataDto.setfId(pid);
                     * }
                     * }
                     */
                    wFSubjectPackageDataDto.setIcon(ChannelConstants.WF_SUBJECT_BANNER_ICON_TYPE_0);
                    if (pid == null) {
                        VideoMysqlTable videoMysqlTable = albumVideoAccess.getVideo(vid,
                                commonParam);
                        if (videoMysqlTable != null) {
                            pid = videoMysqlTable.getPid();
                            if (pid != null) {
                                wFSubjectPackageDataDto.setfId(String.valueOf(pid));
                            }
                        }
                    }
                    if (pid != null) {
                        AlbumMysqlTable albumMysql = albumVideoAccess
                                .getAlbum(pid, commonParam);
                        if (albumMysql != null) {
                            String a_payPlatform = albumMysql.getPay_platform();
                            String a_playPlatform = albumMysql.getPlay_platform();
                            Map<String, String> map = new HashMap<>();
                            if (a_payPlatform != null) {
                                map.put("payPlatform", a_payPlatform);
                            }
                            if (a_playPlatform != null) {
                                map.put("playPlatform", a_playPlatform);
                            }
                            try {
                                String str = JsonUtil.parseToString(map);
                                wFSubjectPackageDataDto.setExtend(str);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    List<String> content = new ArrayList<String>();
                    wFSubjectPackageDataDto.setContent(content);

                    String subTitle = cmsBlockContentTpResponse.getSubTitle();
                    if (StringUtil.isNotBlank(subTitle)) {
                        content.add(subTitle);
                        wFSubjectPackageDataDto.setSubTitle(null);
                    }

                    String desc = cmsBlockContentTpResponse.getShorDesc();
                    if (StringUtil.isNotBlank(desc)) {
                        content.add(desc);
                    }
                    if (wFSubjectPackageDataDto.getJump() != null
                            && wFSubjectPackageDataDto.getJump().getValue() != null) {
                        List<WFSubjectPackageDataDto> buttonList = new ArrayList<WFSubjectPackageDataDto>();
                        wFSubjectPackageDataDto.setButton(buttonList);

                        WFSubjectPackageDataDto button_1 = new WFSubjectPackageDataDto();
                        button_1.setTitle("立即播放");
                        VideoDto videoDto = (VideoDto) wFSubjectPackageDataDto.getJump().getValue();
                        videoDto.setSubName(null);
                        button_1.setJump(wFSubjectPackageDataDto.getJump());
                        buttonList.add(button_1);
                        // supplementParseSubjectPackageDataFormGuanXing(wFSubjectPackageDataDto,commonParam);
                        wFSubjectPackageDataDto.setJump(null);
                    }
                }
            }
        }
    }

    /**
     * 由于分端付费，实时解析banner数据，生成icon图标
     * @param mWFSubjectDto
     * @param commonParam
     */
    public void supplementParseSubjectPackageDataForBannerIcon(WFSubjectDto mWFSubjectDto, CommonParam commonParam) {
        if (mWFSubjectDto == null) {
            return;
        }
        List<WFSubjectPackageDto> packages = mWFSubjectDto.getPackageList();
        if (packages == null || packages.size() == 0) {
            return;
        }
        WFSubjectPackageDto wWFSubjectPackageDto = packages.get(0);
        if (wWFSubjectPackageDto == null || wWFSubjectPackageDto.getType() == null
                || !wWFSubjectPackageDto.getType().equals(ChannelConstants.WF_SUBJECT_TYPE_BANNER_1)) {
            return;
        }
        List<WFSubjectPackageDataDto> dataList = wWFSubjectPackageDto.getDataList();
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        WFSubjectPackageDataDto wFSubjectPackageDataDto = dataList.get(0);
        if (wFSubjectPackageDataDto != null) {
            String extend = wFSubjectPackageDataDto.getExtend();
            if (StringUtil.isNotBlank(extend)) {
                Map<String, String> map = JsonUtil.parse(extend, Map.class);
                if (map != null) {
                    String a_payPlatform = map.get("payPlatform");
                    String a_playPlatform = map.get("playPlatform");
                    if (VideoCommonUtil.isCharge(a_payPlatform, a_playPlatform, commonParam.getP_devType())) {
                        wFSubjectPackageDataDto.setIcon(ChannelConstants.WF_SUBJECT_BANNER_ICON_TYPE_1);
                    }
                }
                wFSubjectPackageDataDto.setExtend(null);
            }
        }
    }

    /**
     * 解析banner1接观星数据，暂不用了
     * @param wFSubjectPackageDataDto
     * @param commonParam
     */
    public void supplementParseSubjectPackageDataFormGuanXing(WFSubjectPackageDataDto wFSubjectPackageDataDto,
            CommonParam commonParam) {
        if (wFSubjectPackageDataDto == null) {
            return;
        }
        List<WFSubjectPackageDataDto> buttonList = wFSubjectPackageDataDto.getButton();
        if (buttonList == null) {
            return;
        }
        /*
         * PromotionDto button_2 = getButtonFormGuanXing(commonParam);
         * if(button_2 != null){
         * buttonList.add(button_2);
         * }
         */
    }

    public void supplementWFSubjectDataFormGuanXing(WFSubjectDto mWFSubjectDto, CommonParam commonParam) {
        if (mWFSubjectDto == null) {
            return;
        }
        List<WFSubjectPackageDto> packages = mWFSubjectDto.getPackageList();
        if (packages == null || packages.size() == 0) {
            return;
        }
        WFSubjectPackageDto wWFSubjectPackageDto = packages.get(0);
        if (wWFSubjectPackageDto == null || wWFSubjectPackageDto.getType() == null
                || !wWFSubjectPackageDto.getType().equals(ChannelConstants.WF_SUBJECT_TYPE_BANNER_1)) {
            return;
        }
        List<WFSubjectPackageDataDto> dataList = wWFSubjectPackageDto.getDataList();
        if (dataList == null || dataList.size() == 0) {
            return;
        }
        WFSubjectPackageDataDto wFSubjectPackageDataDto = dataList.get(0);
        supplementParseSubjectPackageDataFormGuanXing(wFSubjectPackageDataDto, commonParam);
    }

    /*
     * public WFSubjectPackageDataDto getButtonFormGuanXing(CommonParam
     * commonParam){
     * if(commonParam == null || commonParam.getMac() == null){
     * return null;
     * }
     * WFSubjectPackageDataDto button_2 = new WFSubjectPackageDataDto();
     * String posStr = ActivityTpConstant.ACTIVITY_GUANXING_POSITION_WF_SUBJECT;
     * String direct = null;
     * List<PromotionDto> lists =
     * vipV2Service.getStarGazerData
     * (direct,posStr,commonParam,false);
     * if(lists != null && lists.size() == 1){
     * PromotionDto mPromotionDto = lists.get(0);
     * if(mPromotionDto != null && mPromotionDto.getPosition() != null &&
     * mPromotionDto
     * .getPosition().equals(ActivityTpConstant.ACTIVITY_CLIENT_POSITION_WF_SUBJECT
     * )){
     * String title = mPromotionDto.getTitle();
     * if(StringUtil.isNotBlank(title)){
     * button_2.setTitle(title);
     * }
     * button_2.setJump(changeJumpData(mPromotionDto.getJump()));
     * return button_2;
     * }
     * }
     * return null;
     * }
     */
    public PromotionDto getButtonFormGuanXing(CommonParam commonParam) {
        if (commonParam == null || commonParam.getMac() == null) {
            return null;
        }
        String posStr = ActivityTpConstant.ACTIVITY_GUANXING_POSITION_WF_SUBJECT;
        String direct = null;
        List<PromotionDto> lists = vipV2Service.getStarGazerData(direct, posStr, commonParam,
                false);
        if (lists != null && lists.size() == 1) {
            PromotionDto mPromotionDto = lists.get(0);
            if (mPromotionDto != null && mPromotionDto.getPosition() != null
                    && mPromotionDto.getPosition().equals(ActivityTpConstant.ACTIVITY_CLIENT_POSITION_WF_SUBJECT)) {
                return mPromotionDto;
            }
        }
        return null;
    }

    public WFSubjectPackageDataDto.JumpData changeJumpData(JumpData data) {
        if (data == null) {
            return null;
        }
        WFSubjectPackageDataDto.JumpData jumpData = new WFSubjectPackageDataDto.JumpData();
        jumpData.setType(data.getType());
        jumpData.setValue(data.getValue());
        jumpData.setExtend(data.getExtend());
        return jumpData;
    }
}
