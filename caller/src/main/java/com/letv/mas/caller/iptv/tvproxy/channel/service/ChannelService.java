package com.letv.mas.caller.iptv.tvproxy.channel.service;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.ContentPackage.ContentInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsCourseTpResponse.CmsCourseDataTpResponse.CourseDataTpResponse.ThemesTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsCourseTpResponse.CmsCourseDataTpResponse.CourseDataTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.constant.VideoConstants.Category;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.GetSubjectTpResponse.SubjectDataTpResponse.TemplateJson;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.SubjectTjPackageTpResponse.SubjectTjPackageDataContent;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockContentTpResponse.CmsTpResponseExt;
import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoAccess;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsCourseTpResponse.CmsCourseDataTpResponse;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.*;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.bean.bo.Subject;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.bean.bo.SubjectPreLive;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ErrorCodeConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.GetSubjectTpResponse.SubjectDataTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response.RecommendationUsLevidiPublisherResponse.UsLevidiContent;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response.RecommendationInLevidiCategoryResponse.SarrsCategory;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response.RecommendationUsLevidiPublisherResponse.UsLevidiPublisher;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response.RecommendationUsLevidiCategoryResponse.UsLevidiCategory;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response.RecommendationUsLevidiCategoryResponse.UsLevidi;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsPageTpResponse.CmsPageTpResponseData.CmsPageTpResponseFrag;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.*;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageResponse;
import com.letv.mas.caller.iptv.tvproxy.channel.constants.ChannelConstants;
import com.letv.mas.caller.iptv.tvproxy.channel.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.channel.util.ChannelUtil;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.service.MutilLanguageService;
import com.letv.mas.caller.iptv.tvproxy.common.constant.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.ChannelMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.SubjectDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.SubjectPackageDataDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.SubjectPackageDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.GetPackageDataTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.GetSubjectTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.GetUsUrlDataTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.SubjectTjPackageTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.RecommendationTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response.RecommendationInLevidiCategoryResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response.RecommendationUsLevidiCategoryResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response.RecommendationUsLevidiPublisherResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1.StartupAdResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.VideoTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.bean.ChargeInfoDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.ContentPackage;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.Vip;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.VipPackage;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.BossTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.GetUrmActivityResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.BaseResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.LiveProgram;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import com.letv.mas.caller.iptv.tvproxy.common.util.ErrorCodeCommonUtil;
import com.letv.mas.caller.iptv.tvproxy.live.model.bean.bo.Live;
import com.letv.mas.caller.iptv.tvproxy.recommendation.RecommendationService;
import com.letv.mas.caller.iptv.tvproxy.recommendation.model.bean.bo.Recommendation;
import com.letv.mas.caller.iptv.tvproxy.terminal.service.TerminalService;
import com.letv.mas.caller.iptv.tvproxy.user.UserService;
import com.letv.mas.caller.iptv.tvproxy.video.constants.ChargeTypeConstants;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.AlbumDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoDto;
import com.letv.mas.caller.iptv.tvproxy.video.service.PlayService;
import com.letv.mas.caller.iptv.tvproxy.video.service.VideoService;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.PilotDto;
import com.letv.mas.caller.iptv.tvproxy.vip.service.VipMetadataService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import serving.ContentProviderAttribute;
import serving.GenericDetailResponse;
import serving.ResultDocInfo;
import serving.Thumbnail;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component(value = "v2.ChannelService")
public class ChannelService extends BaseService {

    private final static Logger log = LoggerFactory.getLogger(ChannelService.class);

    private static volatile long LAST_CACHETIME = 0;// 上次缓存时间
    private static int CACHE_TIMEOUT = 10 * 60 * 1000;// 缓存超时
    private static volatile Map<String, ChannelData> channelDataRecommendationBlockMap;
    private static String PLUS_CHANNELLIST = "channelList";
    private static String PLUS_CONTENT = "content";

    @Autowired
    TerminalService terminalService;
    
    @Autowired
    MutilLanguageService mutilLanguageService;

    @Autowired
    RecommendationService recommendationService;
    
    @Autowired
    AlbumVideoAccess albumVideoAccess;

    @Autowired
    VideoService videoService;
    
    @Autowired
    VipMetadataService vipMetadataService;

    @Autowired
    PlayService playService;

    @Autowired
    UserService userService;
    
    /**
     * 视频营销更新策略相关配置
     */
    private static long VIDEO_SALE_CONFIG_UPDATE_INTERVAL_MILLIS = 5 * 60 * 1000L; // 5分钟
    private static long VIDEO_SALE_CONFIG_LASTUPDATE_TIME_MILLIS = 0L; // 上次更新时间
    private Lock VIDEO_SALE_CONFIG_UPDATE_LOCK = new ReentrantLock(); // 用于定时更新的可重入锁
    Map<String, BaseData> tmpBaseDataMap = new HashMap<String, BaseData>();

    private static long PAGEID_UPDATE_INTERVAL_MILLIS = 10 * 60 * 1000L; // 10分钟
    private static long PAGEID_LASTUPDATE_TIME_MILLIS = 0L; // 上次更新时间
    private Lock PAGEID_UPDATE_LOCK = new ReentrantLock(); // 用于定时更新的可重入锁

    /**
     * @param parentChannelId
     *            频道id
     * @param vnum
     *            版本号
     * @param commonParam
     *            通用参数
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public PageResponse getChannelList(Integer parentChannelId, String cmsPageId, String support3d, String vnum,
                                       Integer mcode, String supportStream, String channelCode, Integer page, Integer pageSize,
                                       CommonParam commonParam) {
        String logPrefix = "getChannelList_" + commonParam.getMac();
        List<BaseData> channelList = new LinkedList<BaseData>();
        // 美国levidi导航信息走cms
        if (CommonConstants.TERMINAL_APPLICATION_LEVIDI.equals(commonParam.getTerminalApplication())
                && LocaleConstant.Wcode.IN.equals(commonParam.getWcode())) {
            if (CommonConstants.BIZCODE_SUBSCRIBE.equalsIgnoreCase(commonParam.getBizCode())) {
                return new PageResponse(this.getPublisherList(page, pageSize, commonParam));
            }
            if (StringUtil.isBlank(commonParam.getAppCode())) {
                log.info(logPrefix + "app code is null!");
            } else {
                // 通过appCode对老的levidi进行兼容
                if (Integer.valueOf(commonParam.getAppCode()) >= 400) {
                    return new PageResponse(this.getLevidiChannelList(commonParam, channelCode, logPrefix));
                }
            }
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LE
                .equalsIgnoreCase(commonParam.getTerminalApplication())) {
            if (CommonConstants.BIZCODE_ADD_ON.equalsIgnoreCase(commonParam.getBizCode())) {// 美国乐视视频下的会员频道add-on二级标签
                channelList = this.getAddOnChannelList(parentChannelId, commonParam);
                return new PageResponse(channelList);
            } else if (ChannelConstants.CHANNEL_CODE_LEPARTER.equalsIgnoreCase(channelCode)) {
                String pageId = ApplicationUtils.get(ApplicationConstants.CMS_LECOM_LEPARTER_PAGE_ID);
                return (PageResponse) this.getPageDataList(null, pageId, null, null, null, null, null, null,
                        commonParam);
            } else if (ChannelConstants.CHANNEL_CODE_CATEGORY.equalsIgnoreCase(channelCode)) {
                String pageId = ApplicationUtils.get(ApplicationConstants.CMS_LECOM_CATEGORY_PAGE_ID);
                return (PageResponse) this.getPageDataList(null, pageId, null, null, null, null, null, null,
                        commonParam);
            }
        }
        // 获取频道数据
        // if (StringUtil.isBlank(cmsPageId)) {
        // cmsPageId = this.getPageId(parentChannelId, commonParam.getWcode());
        // }
        if (StringUtil.isNotBlank(cmsPageId)) {
            return this.getPageList(cmsPageId, support3d, mcode, supportStream, commonParam);
        }

        List<ChannelMysqlTable> channelMysqlTableList = this.facadeMysqlDao.getChannelMysqlDao().getList(
                commonParam.getBroadcastId(), parentChannelId, mcode, vnum);
        boolean support3D = true;
        boolean support4K = true;
        boolean supportDB = true;
        if (StringUtil.isNotBlank(supportStream)) {
            support3D = TerminalTool.support3D(supportStream, commonParam.getTerminalSeries());
            support4K = TerminalTool.support4K(supportStream, commonParam.getTerminalSeries());
            supportDB = TerminalTool.supportDB(supportStream, commonParam.getTerminalSeries());
        } else {
            support3D = (support3d == null) || (!"0".equals(support3d));
        }
        // 将数据库的返回数据重组，返回给前台
        String channelName;
        for (ChannelMysqlTable channelMysqlTable : channelMysqlTableList) {
            channelName = channelMysqlTable.getName();

            // 如果客户端为分端付费之后版本, 显示节目导视
            if ("节目导视".equals(channelName) && TerminalUtil.supportDistributedPaying(commonParam)) {
                continue;
            }

            // 如果客户端为分端付费之前版本，显示飙升榜
            if ("飙升榜".equals(channelName) && !TerminalUtil.supportDistributedPaying(commonParam)) {
                continue;
            }

            if (channelName != null
                    && channelName.contains("儿童")
                    && commonParam.getTerminalApplication().equals(
                            TerminalCommonConstant.TERMINAL_APPLICATION_LETV_COMMON)) {
                continue;
            }
            if (!StringUtils.isBlank(channelMysqlTable.getChannelcode())
                    && channelMysqlTable.getChannelcode().indexOf("uiversion_") > -1
                    && !channelMysqlTable.getChannelcode().equals("uiversion_" + commonParam.getUiType())) {
                continue;
            }
            if (!this.showToUser(channelMysqlTable, support3D, support4K, supportDB)) {
                continue;
            }
            String terminalSeries = commonParam.getTerminalSeries();
            if (terminalSeries != null && channelMysqlTable.getId() != null) {
                // 如果不支持DTS的设备，直接跳过；如果设备不支持全景码流，则过滤全景频道
                if (StringUtils.equals("360", channelMysqlTable.getDefault_stream())
                        && !terminalService.isTerminalSeriesSupport360Stream(commonParam)) {
                    continue;
                }
            }
            // 国际化频道名称
            String name = mutilLanguageService.getMessage(ChannelConstants.ITV_MENU_TABLE,
                    String.valueOf(channelMysqlTable.getId()), ChannelConstants.ITV_MENU_NAME,
                    commonParam.getLangcode());
            if (name != null) {
                channelMysqlTable.setName(name);
            }
            Channel channel = new Channel(channelMysqlTable, commonParam.getBroadcastId(),
                    commonParam.getTerminalApplication(), vnum, parentChannelId);
            this.setChannelTitle(channel, commonParam);
            this.setChannelWallPicFromCache(channel, commonParam);
            if (TerminalUtil.isLetvUs(commonParam)) {
                channel.setTitleFocus1(null);
                channel.setTitleFocus2(null);
            }
            JumpUtil.buildJumpObjFromDb(channel, channel.getTitleDataType(), null, null, commonParam);
            if (channelName != null
                    && channelName.contains("儿童")
                    && commonParam.getTerminalApplication().equals(
                            TerminalCommonConstant.TERMINAL_APPLICATION_MEDIA_CIBN)
                    && TerminalUtil.supportJumpLeChildApp(commonParam)) {
                JumpUtil.buildJumpToLeChildHome(channel, commonParam);
            }
            channelList.add(channel);
        }

        return new PageResponse(channelList);
    }

    /**
     * 频道墙前三个入口图片放到板块中维护
     * @param channel
     * @param commonParam
     */
    public void setChannelWallPicFromCache(Channel channel, CommonParam commonParam) {
        if (channel != null) {
            Integer channelId = channel.getChannelId();
            if (channelId != null
                    && (channelId == ChannelConstants.TV || channelId == ChannelConstants.FILM || channelId == ChannelConstants.CHILD)) {
                String tvPic = this.facadeCacheDao.getChannelCacheDao().getChannelPic(
                        channel.getChannelId() + "_" + commonParam.getTerminalApplication());
                if (StringUtil.isBlank(tvPic)) {
                    String blockId = ChannelConstants.CHANNEL_WALL_PIC_BLOCKID;
                    CmsBlockTpResponse cmsBlockTpResponse = this.facadeTpDao.getCmsTpDao().getCmsBlockNewById(blockId,
                            commonParam);
                    if (cmsBlockTpResponse != null) {
                        List<CmsBlockContentTpResponse> cmsBlockContentTpResponseList = cmsBlockTpResponse
                                .getBlockContent();
                        if (cmsBlockContentTpResponseList != null && cmsBlockContentTpResponseList.size() > 0) {
                            for (CmsBlockContentTpResponse cmsBlockContentTpResponse : cmsBlockContentTpResponseList) {
                                if ((cmsBlockContentTpResponse.getPushflag() == null)
                                        || !cmsBlockContentTpResponse.getPushflag().contains(
                                                CommonConstants.TV_PLATFROM_CODE)) {
                                    continue;
                                }
                                if (StringUtils.isNotEmpty(cmsBlockContentTpResponse.getShorDesc())) {// 跳频道页
                                    Page pageTemp = this.getPageBean(cmsBlockContentTpResponse.getShorDesc());
                                    if (pageTemp != null) {
                                        Integer channelIdTemp = pageTemp.getChannelId();
                                        String tvPicTemp = cmsBlockContentTpResponse.getTvPic();
                                        this.facadeCacheDao.getChannelCacheDao().setChannelPic(
                                                channelIdTemp + "_" + commonParam.getTerminalApplication(), tvPicTemp);
                                    }
                                }
                            }
                        }
                    }
                    tvPic = this.facadeCacheDao.getChannelCacheDao().getChannelPic(
                            channel.getChannelId() + "_" + commonParam.getTerminalApplication());
                }
                if (StringUtil.isNotBlank(tvPic)) {
                    channel.setImg(tvPic);
                }
            }
        }
    }

    /**
     * 获取add-on会员列表
     * @param channelId
     * @param commonParam
     * @return
     */
    private List<BaseData> getAddOnChannelList(Integer channelId, CommonParam commonParam) {
        List<BaseData> channelList = new ArrayList<BaseData>();
        ChannelMysqlTable channelMysqlTable = this.facadeMysqlDao.getChannelMysqlDao().get(channelId);
        BossTpResponse<List<Vip>> vipTpResponse = null;
        vipTpResponse = this.facadeTpDao.getVipTpDao().getVipListByType(VipTpConstant.Type_Group_US.ADD_ON.getCode(), commonParam);
        if (vipTpResponse != null && vipTpResponse.isSucceed()) {
            List<Vip> vipList = vipTpResponse.getData();
            if (vipList != null) {
                for (Vip vip : vipList) {
                    BossTpResponse<List<VipPackage>> vipPackageListTpResponse = this.facadeTpDao.getVipTpDao()
                            .getPackageByProductId(vip.getProductId(), commonParam); // 获取套餐列表
                    if (vipPackageListTpResponse != null && vipPackageListTpResponse.isSucceed()) {
                        List<VipPackage> vipPackageList = vipPackageListTpResponse.getData();
                        for (VipPackage vipPackage : vipPackageList) {
                            if (VipPackage.STATUS_PUBLISHED == vipPackage.getStatus() // 套餐状态为已发布
                                    && vipPackage.getTerminal() != null
                                    && vipPackage.getTerminal().contains(VipPackage.TERMINAL_TV)) {// 可用终端包含tv
                                Channel channel = new Channel(vip, channelMysqlTable);
                                JumpUtil.buildJumpObjFromDb(channel, channel.getDataType(), null, null, commonParam);
                                channelList.add(channel);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return channelList;
    }

    public PageResponse<Channel> getPageList(String cmsPageId, String support3d, Integer mcode, String supportStream,
            CommonParam commonParam) {
        String logPrefix = "getPageList" + cmsPageId + "_" + commonParam.getMac() + "_" + commonParam.getBroadcastId();
        boolean support3D = true;
        boolean support4K = true;
        boolean supportDB = true;
        if (StringUtil.isNotBlank(supportStream)) {
            support3D = TerminalTool.support3D(supportStream, commonParam.getTerminalSeries());
            support4K = TerminalTool.support4K(supportStream, commonParam.getTerminalSeries());
            supportDB = TerminalTool.supportDB(supportStream, commonParam.getTerminalSeries());
        } else {
            support3D = (support3d == null) || (!"0".equals(support3d));
        }
        List<Channel> channelList = new LinkedList<Channel>();

        CmsPageTpResponse cmsPageTpResponse = this.facadeTpDao.getCmsTpDao().getCmsPage(cmsPageId, commonParam);
        if (cmsPageTpResponse != null && cmsPageTpResponse.getData() != null) {
            List<CmsPageTpResponseFrag> cmsPageTpResponseFragList = cmsPageTpResponse.getData().getFrags();
            if (cmsPageTpResponseFragList != null && cmsPageTpResponseFragList.size() > 0) {
                for (CmsPageTpResponseFrag cmsPageTpResponseFrag : cmsPageTpResponseFragList) {
                    List<CmsBlockContentTpResponse> cmsBlockContentTpResponseList = cmsPageTpResponseFrag
                            .getBlockContents();
                    if (cmsBlockContentTpResponseList != null && cmsBlockContentTpResponseList.size() > 0) {
                        for (CmsBlockContentTpResponse cmsBlockContentTpResponse : cmsBlockContentTpResponseList) {
                            if ((cmsBlockContentTpResponse.getPushflag() == null)
                                    || !cmsBlockContentTpResponse.getPushflag().contains(
                                            CommonConstants.TV_PLATFROM_CODE)) {
                                continue;
                            }
                            Channel channel = new Channel();
                            BaseData data = this.getDataFromCms(cmsBlockContentTpResponse, commonParam, null, null,
                                    null);
                            if (data != null) {
                                if (data instanceof Page) {
                                    Page page = (Page) data;
                                    channel.setDataType(page.getDataType());
                                    channel.setTitleDataType(page.getTitleDataType());
                                    channel.setChannelId(page.getChannelId());
                                    channel.setPageId(page.getPageId());
                                    channel.setName(page.getName());
                                    channel.setChannelName(page.getChannelName());
                                    channel.setImg(page.getImg());
                                    channel.setTitleIcon(page.getTitleIcon());
                                    channel.setTitleFocus1(page.getTitleFocus1());
                                    channel.setTitleFocus2(page.getTitleFocus2());
                                    channel.setTitleBgColor(page.getTitleBgColor());
                                    channel.setConfigInfo(page.getConfigInfo());
                                    JumpUtil.buildJumpObjFromDb(channel, channel.getTitleDataType(), null, null,
                                            commonParam);
                                } else if (data instanceof Channel) {
                                    channel = (Channel) data;
                                    JumpUtil.bulidJumpObj(channel, channel.getDataType(), null, null, commonParam);

                                } else if (data instanceof BaseBlock) {
                                    BaseBlock block = (BaseBlock) data;
                                    channel.setDataType(block.getDataType());
                                    channel.setTitleDataType(block.getDataType());
                                    channel.setChannelId(StringUtil.toInteger(block.getChannelId()));
                                    channel.setName(block.getName());
                                    channel.setImg(block.getImg());
                                    JumpUtil.bulidJumpObj(channel, channel.getTitleDataType(), null, null, commonParam);
                                }
                                // 3D/4K/杜比过滤
                                if (this.isContinue(channel, support3D, support4K, supportDB, commonParam)) {
                                    continue;
                                }
                                this.setChannelTitle(channel, commonParam);
                                if (TerminalUtil.isLetvUs(commonParam)) {
                                    channel.setTitleFocus1(null);
                                    channel.setTitleFocus2(null);
                                }
                                channelList.add(channel);
                            }
                        }
                    }
                }
            }
        }
        return new PageResponse<Channel>(channelList);
    }

    private boolean isContinue(Channel channel, boolean support3D, boolean support4K, boolean supportDB,
            CommonParam commonParam) {
        Boolean flag = Boolean.FALSE;
        if (channel != null) {
            if (!StringUtils.isBlank(channel.getChannelCode()) && channel.getChannelCode().indexOf("uiversion_") > -1
                    && !channel.getChannelCode().equals("uiversion_" + commonParam.getUiType())) {
                flag = Boolean.TRUE;
            }
            if (!this.showToUser(channel, support3D, support4K, supportDB)) {
                flag = Boolean.TRUE;
            }
            String terminalSeries = commonParam.getTerminalSeries();
            if (terminalSeries != null && channel.getChannelId() != null) {
                // 如果不支持DTS的设备，直接跳过；如果设备不支持全景码流，则过滤全景频道
                if (StringUtils.equals("360", channel.getDefaultStream())
                        && !terminalService.isTerminalSeriesSupport360Stream(commonParam)) {
                    flag = Boolean.TRUE;
                }
            }
        }
        return flag;
    }

    /**
     * 获取映射页面id
     * @param channelId
     * @param wcode
     * @return
     */
    public String getPageId(Integer channelId, String wcode) {
        String pageId = null;
        if (channelId != null && StringUtil.isNotBlank(wcode)) {
            pageId = ChannelConstants.CHANNELID_CMS_PAGEID.get(channelId + "_" + wcode);
        }
        return pageId;
    }

    /**
     * 频道墙电视剧、电影、乐视儿童更新*集
     * @param channel
     *            Channel类
     * @param commonParam
     *            通用参数
     */
    private void setChannelTitle(Channel channel, CommonParam commonParam) {
        if (channel != null) {
            Integer channelId = channel.getChannelId();
            if (channelId != null
                    && (channelId == ChannelConstants.TV || channelId == ChannelConstants.FILM || channelId == ChannelConstants.CHILD)) {
                String num = this.facadeCacheDao.getChannelCacheDao().getChannelTitle(channelId);
                if (StringUtils.isBlank(num)) {
                    switch (channelId) {
                    case ChannelConstants.TV:
                        num = "" + (int) (80 * Math.random() + 20);// 20到100
                        break;
                    case ChannelConstants.FILM:
                        num = "" + (int) (30 * Math.random() + 20);// 20到50
                        break;
                    case ChannelConstants.CHILD:
                        num = "" + (int) (80 * Math.random() + 20);// 20到100
                        break;
                    }
                    if (StringUtils.isNotBlank(num)) {
                        // 缓存一天，每天生成一个新随机数
                        this.facadeCacheDao.getChannelCacheDao().setChannelTitle(channelId, num);
                    }
                }
                String preText = MessageUtils.getMessage(ChannelConstants.CHANNEL_UPDATE_TEXT,
                        commonParam.getLangcode());
                channel.setTitleFocus1(preText + num);
            }
        }
    }

    /**
     * levidi频道对接cms
     * @param commonParam
     * @param channelCode
     * @param logPrefix
     * @return
     */
    private List<BaseData> getLevidiChannelList(CommonParam commonParam, String channelCode, String logPrefix) {
        List<BaseData> channelList = new ArrayList<BaseData>();
        String blockId = "";
        blockId = ApplicationUtils.get(ApplicationConstants.CMS_LEVIDI_NAVIGATION_BLOCK_ID);
        CmsBlockTpResponse cmsBlockTpResponse = this.facadeTpDao.getCmsTpDao().getCmsBlockNewById(blockId, commonParam);
        if (cmsBlockTpResponse != null) {
            if (cmsBlockTpResponse.getBlockContent() != null) {
                for (CmsBlockContentTpResponse cmsBlockContentTpResponse : cmsBlockTpResponse.getBlockContent()) {
                    if ((cmsBlockContentTpResponse.getPushflag() == null)
                            || !cmsBlockContentTpResponse.getPushflag().contains(CommonConstants.TV_PLATFROM_CODE)) {
                        continue;
                    }
                    BaseData baseData = this.getDataFromCms(cmsBlockContentTpResponse, commonParam, null, null, null);
                    if (baseData != null) {
                        channelList.add(baseData);
                    }
                }
            }
        } else {
            log.info(logPrefix + ": get cms block data failed.");
        }
        return channelList;
    }

    /**
     * 频道数据列表
     * @param channelId
     *            频道id
     * @param support3D
     *            是否支持3D
     * @param age
     *            年龄
     * @param gender
     *            性别
     * @param vipType
     *            会员标记
     * @param supportStream
     * @param commonParam
     *            客户端通用参数
     * @return
     */
    public BaseResponse getChannelDataList(Integer channelId, String pageId, String support3D, String age,
                                           String gender, String week, Integer vipType, Integer model, String vnum, String cpId, String cid,
                                           Integer num, String channelCode, String supportStream, CommonParam commonParam) {
        String logPrefix = "getChannelDataList_" + channelId + "_" + commonParam.getMac() + "_"
                + commonParam.getBroadcastId();
        List<ChannelData> channelDataList = new LinkedList<ChannelData>();
        HashMap<String, Object> plus = null;
        // 印度版逻辑//9.0美国
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equals(commonParam.getTerminalApplication())) {
            return this.getLevidiChannelDataList(channelId, support3D, cpId, cid, num, null, vnum, channelCode,
                    supportStream, commonParam);
        }

        if (StringUtil.isNotBlank(pageId) && pageId.length() > 4) {
            return this.getPageDataList(channelId, pageId, age, gender, week, vipType, model, null, commonParam);
        }

        String defaultStream = this.facadeCacheDao.getChannelCacheDao().getChannelDefaultStreamById(
                String.valueOf(channelId));
        String defaultStreamName = LetvStreamCommonConstants.getStreamName(defaultStream, commonParam.getLangcode());
        // 数据源列表
        // if (StringUtil.isBlank(cmsPageId)) {
        // cmsPageId = this.getPageId(channelId, commonParam.getWcode());
        // }
        if (StringUtil.isNotBlank(pageId)) {
            return this.getPageDataList(channelId, pageId, age, gender, week, vipType, model, 0, commonParam);
        }
        List<ChannelDataMysqlTable> channelDataMysqlTableList = this.facadeMysqlDao.getChannelDataMysqlDao().getList(
                channelId);
        for (ChannelDataMysqlTable channelDataMysqlTable : channelDataMysqlTableList) {
            Integer dataSource = channelDataMysqlTable.getData_source();
            if (dataSource == null) {
                continue;
            }
            // CMS数据
            if (dataSource == ChannelConstants.DATASOURCE_CMS) {
                String url = channelDataMysqlTable.getData_url();
                CmsBlockTpResponse cmsBlockTpResponse = this.facadeTpDao.getCmsTpDao().getCmsBlockNew(url, commonParam);
                if (cmsBlockTpResponse != null) {
                    this.getBlockContent(channelDataList, cmsBlockTpResponse, channelId, vipType,
                            channelDataMysqlTable, defaultStream, defaultStreamName, commonParam);
                } else {
                    log.info(logPrefix + "_" + url + ": get cms block data failed.");
                }
                // 推荐数据猜你喜欢
            } else if (dataSource == ChannelConstants.DATASOURCE_RECOMMENDATION) {
                this.setRecommendationData(channelDataList, channelDataMysqlTable, commonParam);
            } else if (dataSource == ChannelConstants.DATASOURCE_CHARTS) {//排行榜
                // Ranking list
                List<ChartsMysqlTable> chartsMysqlTableList = this.facadeMysqlDao.getChartsMysqlDao()
                        .getListByChannelId(channelId);
                if (!CollectionUtils.isEmpty(chartsMysqlTableList)) {
                    for (ChartsMysqlTable chartsMysqlTable : chartsMysqlTableList) {
                        // 设置基本信息
                        ChartsDto charts = this.parseChartsDto(chartsMysqlTable, commonParam,
                                ChannelConstants.CHARTS_PARSE_CHARTS_TYPE_PREVIEW);
                        if (charts != null) {
                            channelDataList.add(charts);
                        }
                    }
                }
            } else if (dataSource == ChannelConstants.DATASOURCE_CMS_SERVER) {//服务端映射&CMS
                String parentBlockId = null;
                if (channelDataMysqlTable.getChannel_id() != null) {
                    parentBlockId = String.valueOf(channelDataMysqlTable.getChannel_id());
                }
                // 根据映射关系读取板块id
                String blockId = this.getSubBlockIdByParams(channelId, parentBlockId, age, gender, week);
                CmsBlockTpResponse cmsBlockTpResponse = null;
                if (StringUtil.isNotBlank(blockId)) {
                    cmsBlockTpResponse = this.facadeTpDao.getCmsTpDao().getCmsBlockNewById(blockId, commonParam);
                }
                if (cmsBlockTpResponse != null) {
                    this.getBlockContent(channelDataList, cmsBlockTpResponse, channelId, vipType,
                            channelDataMysqlTable, defaultStream, defaultStreamName, commonParam);
                } else {
                    log.info(logPrefix + "_" + blockId + ": get cms block data failed.");
                }
            } else if (dataSource == ChannelConstants.DATASOURCE_CMS_CACHE) {//缓存板块内容
                if (channelDataMysqlTable.getChannel_id() != null) {
                    String blockId = String.valueOf(channelDataMysqlTable.getChannel_id());
                    List<CmsBlockTpResponse> cmsBlockTpResponseList = this.facadeCacheDao.getChannelCacheDao()
                            .getBlockContent(blockId);
                    if (cmsBlockTpResponseList != null && cmsBlockTpResponseList.size() > 0) {
                        for (CmsBlockTpResponse resp : cmsBlockTpResponseList) {
                            if (resp != null) {
                                // 解析板块内容，并add到channelDataList中
                                this.getBlockContent(channelDataList, resp, channelId, vipType, channelDataMysqlTable,
                                        defaultStream, defaultStreamName, commonParam);
                            } else {
                                log.info(logPrefix + "_" + blockId + ": get cms block data failed.");
                            }
                        }
                    } else {
                        log.info(logPrefix + "_" + blockId + " list is empoty from cache");
                    }
                }
            }

            if ((channelDataMysqlTable.getUi_plate_type() != null)
                    && (ChannelConstants.CHANNEL_UIPLATE_TYPE_BGIMG == channelDataMysqlTable.getUi_plate_type())) {
                Response<BaseData> content = new Response<BaseData>(
                        this.tmpBaseDataMap.get(ChannelConstants.CHANNEL_TYPE_BACKGROUND_PRE + channelId));
                if (content != null && content.getData() != null) {
                    if (plus == null) {
                        plus = new HashMap<String, Object>();
                    }
                    plus.put(PLUS_CONTENT, content.getData());
                }
            }
        }

        // 二级导航
        if (plus == null) {
            plus = new HashMap<String, Object>();
        }
        PageResponse<Channel> channelList = this.getChannelList(channelId, null, support3D, null, null, supportStream,
                null, null, null, commonParam);
        if ((channelList.getData() != null) && (channelList.getData().size() > 0)) {
            plus.put(PLUS_CHANNELLIST, channelList.getData());
        }

        if (TerminalCommonConstant.TERMINAL_APPLICATION_LETV_US.equalsIgnoreCase(commonParam.getTerminalApplication())) {
            plus.put("channelName",
                    this.facadeCacheDao.getChannelCacheDao().getChannelNameById(String.valueOf(channelId)));
        }
        return new PageResponse<ChannelData>(channelDataList, plus);
    }

    /**
     * 组装猜你喜欢数据
     * @param channelDataList
     * @param channelDataMysqlTable
     * @param commonParam
     */
    private void setRecommendationData(List<ChannelData> channelDataList, ChannelDataMysqlTable channelDataMysqlTable,
            CommonParam commonParam) {
        if (channelDataList != null && channelDataMysqlTable != null) {
            ChannelData channelData = new ChannelData();
            channelData.setTitle(channelDataMysqlTable.getTitle());
            channelData.setUiPlateType(ChannelConstants.CHANNEL_UI_TYPE_EXTENDLIST);
            channelData.setTitleDataType(DataConstant.DATA_TYPE_MULTILIST_RECOMMENDATION);
            channelData.setDataUrl(DomainMapping.changeDomainByBraodcastId(channelDataMysqlTable.getData_url(),
                    commonParam.getBroadcastId(), commonParam.getTerminalApplication()));
            channelDataList.add(channelData);
        }
    }

    /**
     * 印度频道列表(新版本发布后，方法会删除，levidi统一走channel/data/recommendation/list)
     * @param channelId
     *            频道id
     * @param support3D
     *            是否支持3D
     * @param cpId
     * @param cid
     * @param num
     * @param supportStream
     * @param commonParam
     * @return
     */
    @Deprecated
    private BaseResponse getLevidiChannelDataList(Integer channelId, String support3D, String cpId, String cid,
            Integer num, Integer startIdex, String vnum, String channelCode, String supportStream,
            CommonParam commonParam) {
        PageResponse<BaseData> response = new PageResponse<BaseData>();
        if (!StringUtils.isBlank(cid)) {
            response = recommendationService.getLevidiRecommendation(cpId, cid, null, null,
                    num, null, channelCode, null, commonParam);
        }

        HashMap<String, Object> plus = null;
        /**
         * 附频道列表
         */
        if (cid != null && ChannelConstants.CID_CONTAIN_CHANNEL_LIST.indexOf(cid) > -1) {// 数据库中获取频道列表
            PageResponse<Channel> channelList = this.getChannelList(channelId, null, support3D, vnum, null,
                    supportStream, null, null, null, commonParam);
            if ((channelList.getData() != null) && (channelList.getData().size() > 0)) {
                plus = new HashMap<String, Object>();
                plus.put(PLUS_CHANNELLIST, channelList.getData());
            }
        } else {// cp类别列表
            List<BaseData> categoryList = new ArrayList<BaseData>();
            if (StringUtil.isNotBlank(cpId) || StringUtil.isNotBlank(cid)) {
                RecommendationInLevidiCategoryResponse inCategoryResponse = this.facadeTpDao.getRecommendationTpDao()
                        .getSarrsCategoryRecommendation(cpId, commonParam);
                if (inCategoryResponse != null) {
                    List<SarrsCategory> categorys = inCategoryResponse.getData();
                    if (categorys != null && categorys.size() > 0) {
                        for (SarrsCategory category : categorys) {
                            Channel channel = new Channel();
                            channel.setCpId(cpId);
                            channel.setCpCategoryId(category.getName());
                            String title = mutilLanguageService.getMessage(
                                    ChannelConstants.ITV_CHANNELDATA_RECOMMENDATIONBLOCK_TABLE,
                                    cpId + "_" + category.getName(),
                                    ChannelConstants.ITV_CHANNELDATA_RECOMMENDATIONBLOCK_TITLE,
                                    commonParam.getLangcode());
                            channel.setName(title);
                            categoryList.add(channel);
                        }
                    }
                }
            } else {
                this.getLevidiCategoryList(commonParam, channelCode, categoryList);
            }
            plus = new HashMap<String, Object>();
            plus.put(PLUS_CHANNELLIST, categoryList);
        }
        return new PageResponse<BaseData>(response.getData(), plus);
    }

    private void getLevidiCategoryList(CommonParam commonParam, String channelCode, List<BaseData> categoryList) {
        if ("category".equals(channelCode)) {
            RecommendationUsLevidiCategoryResponse usCategoryResponse = this.facadeTpDao.getRecommendationTpDao()
                    .getUsLevidiCategoryRecommendation(commonParam);
            List<UsLevidi> list = usCategoryResponse.getData();
            List<UsLevidiCategory> categorys = null;
            if (list != null && list.size() > 0) {
                UsLevidi usLevidi = list.get(0);
                categorys = usLevidi.getNo_pic_content();
                if (categorys != null) {
                    categorys.addAll(usLevidi.getPic_category());
                } else {
                    categorys = usLevidi.getPic_category();
                }
            }
            if (categorys != null && categorys.size() > 0) {
                for (UsLevidiCategory category : categorys) {
                    if (category == null || category.getType() == null || category.getType() != 1) {
                        continue;
                    }
                    Channel channel = new Channel();
                    channel.setCpCategoryId(category.getCid());
                    channel.setImg(category.getPic_url());
                    String title = mutilLanguageService.getMessage(
                            ChannelConstants.ITV_CHANNELDATA_RECOMMENDATIONBLOCK_TABLE,
                            category.getCid() + "-" + category.getId(),
                            ChannelConstants.ITV_CHANNELDATA_RECOMMENDATIONBLOCK_TITLE, commonParam.getLangcode());
                    channel.setName(title);
                    JumpUtil.bulidJumpObj(channel, DataConstant.DATA_TYPE_CONTENT_LIST, null, null);
                    categoryList.add(channel);
                }
            }
        } else if ("publisher".equals(channelCode)) {
            RecommendationUsLevidiPublisherResponse usPublisherResponse = this.facadeTpDao.getRecommendationTpDao()
                    .getUsLevidiPublisherRecommendation(commonParam);
            List<UsLevidiPublisher> list = usPublisherResponse.getData();
            if (list != null && list.size() > 0) {
                for (UsLevidiPublisher usPubliser : list) {
                    if (usPubliser == null) {
                        continue;
                    }
                    UsLevidiContent publisherC = usPubliser.getData();
                    if (publisherC == null) {
                        continue;
                    }
                    Channel channel = new Channel();
                    channel.setCpId(publisherC.getChannel_id());
                    channel.setImg(publisherC.getPic_url());
                    channel.setName(publisherC.getName());
                    JumpUtil.bulidJumpObj(channel, DataConstant.DATA_TYPE_CONTENT_LIST, null, null);
                    categoryList.add(channel);
                }
            }
        }

    }

    /**
     * 解析Cms板块信息，装载channelDto
     * @param channelDataList
     *            目的集合类
     * @param cmsBlockTpResponse
     *            读取Cms板块返回的信息
     * @param channelId
     *            频道id
     * @param channelDataMysqlTable
     *            频道表数据
     * @param defaultStreamName
     * @param commonParam
     *            通用参数
     */
    private void getBlockContent(List<ChannelData> channelDataList, CmsBlockTpResponse cmsBlockTpResponse,
            Integer channelId, Integer vipType, ChannelDataMysqlTable channelDataMysqlTable, String defaultStream,
            String defaultStreamName, CommonParam commonParam) {

        ChannelData channelData = new ChannelData();
        List<BaseData> dataList = new LinkedList<BaseData>();
        channelData.setDataList(dataList);
        channelData.setUiPlateType(channelDataMysqlTable.getUi_plate_type());
        channelData.setTitle(cmsBlockTpResponse.getName());
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LETV_US.equalsIgnoreCase(commonParam.getTerminalApplication())) {
            if (channelId != null && channelId == 798) {
                channelData.setTitle("");// 美国版首页不需要这个标题
            } else {
                channelData.setTitle("All");
            }
        }
        channelData.setGmt(this.getTimeStamps(cmsBlockTpResponse.getStartTime()));//时间
        Integer dataSize = channelDataMysqlTable.getData_preloadsize();//Data_preloadsize数量限制
        if (dataSize == null || dataSize.intValue() <= 0) {// 数据库中此字段没有值，表示不限制数量
            dataSize = Integer.MAX_VALUE;
        }
        boolean isSpecial = false;// 小焦点图特殊处理
        if (channelId != null && channelId == 798) {
            String indexMark = "id=974&";
            if ((commonParam.getBroadcastId() != null) && (commonParam.getBroadcastId() == CommonConstants.CIBN)) {
                indexMark = "id=6616&";// cibn小焦点图版块id是6616
            }
            String contentUrl = channelDataMysqlTable.getData_url();
            if (contentUrl != null && contentUrl.indexOf(indexMark) > -1) {// 小焦点图6张，现在需要取8个数据
                isSpecial = true;
                dataSize = dataSize - 1;// 小焦点图6张最多从CMS取7条数据
            }
        }
        // 当前板块中存在的页面信息
        if (cmsBlockTpResponse.getBlockContent() != null) {
            this.setBlockContent(cmsBlockTpResponse, dataList, dataSize, channelId, vipType, defaultStream,
                    defaultStreamName, channelDataMysqlTable.getUi_plate_type(), commonParam);
        }
        // 儿童桌面布局特数处理
        this.specialPositionGet(channelId, channelData, dataList);
        // 小焦点图6张，最后一个必须是视频营销数据
        this.setVideoSale(isSpecial, dataList);

        // 北美版将第5个数据替换成收银台数据
        this.setUsSale(commonParam, dataList);

        // 美国版不显示“推荐”两个字
        if ("en_us".equalsIgnoreCase(commonParam.getLangcode()) || "en".equalsIgnoreCase(commonParam.getLangcode())) {
            channelData.setTitle("");
        }
        // 美国版为空也要有节点
        if (TerminalUtil.isLetvUs(commonParam)
                || (channelData.getDataList() != null && channelData.getDataList().size() > 0)) {
            channelDataList.add(channelData);
        }

        // 当前板块的下级板块的页面内容,多层次板块采用递归方式调用
        List<String> subBlockIdList = cmsBlockTpResponse.getSubBlockList();
        if (subBlockIdList != null && subBlockIdList.size() > 0) {
            for (String blockId : subBlockIdList) {
                cmsBlockTpResponse = this.facadeTpDao.getCmsTpDao().getCmsBlockNewById(blockId, commonParam);
                if (cmsBlockTpResponse != null) {
                    this.getBlockContent(channelDataList, cmsBlockTpResponse, channelId, vipType,
                            channelDataMysqlTable, defaultStream, defaultStreamName, commonParam);
                }
            }
        }
    }

    /**
     * @param channelId
     * @param channelData
     * @param dataList
     */
    private void specialPositionGet(Integer channelId, ChannelData channelData, List<BaseData> dataList) {
        if (channelId != null && channelData != null && dataList != null) {
            if (channelId == 1083 && this.tmpBaseDataMap != null) {
                String key = "";
                if (ChannelConstants.CHANNEL_UI_TYPE_FUNCLIST == channelData.getUiPlateType()) {
                    key = ChannelConstants.CHANNEL_TYPE_POSITION_PREFIX + channelId + "_" + 5;
                    if (this.tmpBaseDataMap.get(key) != null) {
                        dataList.add(4, this.tmpBaseDataMap.get(key));
                    }
                } else if (ChannelConstants.CHANNEL_UI_TYPE_CARTONLIST == channelData.getUiPlateType()) {
                    key = ChannelConstants.CHANNEL_TYPE_POSITION_PREFIX + channelId + "_" + 6;
                    if (this.tmpBaseDataMap.get(key) != null) {
                        dataList.add(0, this.tmpBaseDataMap.get(key));
                    }
                }
            }
        }
    }

    /**
     * 视频营销6张小焦点图后
     * @param dataList
     */
    private void setVideoSale(boolean flag, List<BaseData> dataList) {
        if (flag && dataList != null) {
            BaseBlock baseBlock = new BaseBlock();
            Map<String, String> map = this.getVideoSaleConfigs();
            baseBlock.setBlockId(this.getTextFromFile(map, ChannelConstants.INDEX_VIDEO_SALE_BLOCKID));
            baseBlock.setImg(this.getTextFromFile(map, ChannelConstants.INDEX_VIDEO_SALE_IMG));
            Integer dataType = StringUtil.toInteger(
                    this.getTextFromFile(map, ChannelConstants.INDEX_VIDEO_SALE_DATATYPE), 0);
            baseBlock.setName(this.getTextFromFile(map, ChannelConstants.INDEX_VIDEO_SALE_NAME));
            baseBlock.setSubName(this.getTextFromFile(map, ChannelConstants.INDEX_VIDEO_SALE_SUBNAME));
            baseBlock.setDataType(dataType);
            JumpUtil.bulidJumpObj(baseBlock, dataType, null, null);
            dataList.add(baseBlock);
        }
    }

    /**
     * 北美版将第5个数据替换成收银台数据
     */
    private void setUsSale(CommonParam commonParam, List<BaseData> dataList) {
        if (TerminalUtil.isLetvUs(commonParam) && dataList != null && dataList.size() > 4) {
            String url = this.getUsCashierUrl(commonParam);
            if (StringUtil.isNotBlank(url)) {
                Browser baseBlock = new Browser();
                baseBlock.setImg("");
                /*
                 * if(commonParam != null &&
                 * StringUtil.isNotBlank(commonParam.getUserId())){
                 * url = url +"?userId="+commonParam.getUserId();
                 * }
                 */
                baseBlock.setUrl(url);
                baseBlock.setDataType(DataConstant.DATA_TYPE_BROWSER);
                baseBlock.setBrowserType(DataConstant.BROWSER_TYPE_BUILTIN);
                baseBlock.setOpenType(DataConstant.BROWSER_OPENTYPE_INTERACTIVE);
                JumpUtil.bulidJumpObj(baseBlock, DataConstant.DATA_TYPE_BROWSER, null, null);
                dataList.set(4, baseBlock);
            }
        }
    }

    /**
     * 时间戳
     * @param time
     * @return
     */
    private Long getTimeStamps(String time) {
        if (StringUtil.isNotBlank(time) && StringUtils.isNumeric(time) && time.length() >= 8) {
            return TimeUtil.getGmtTime(time, TimeUtil.SHORT_DATE_FORMAT_NO_DASH);
        } else {
            return null;
        }
    }

    /**
     * 最底层板块内容解析并放到dataList容器中
     * @param cmsBlockTpResponse
     * @param dataList
     * @param dataSize
     * @param channelId
     * @param vipType
     * @param defaultStream
     * @param commonParam
     */
    private void setBlockContent(CmsBlockTpResponse cmsBlockTpResponse, List<BaseData> dataList, Integer dataSize,
            Integer channelId, Integer vipType, String defaultStream, String defaultStreamName, Integer uiType,
            CommonParam commonParam) {
        List<CmsBlockContentTpResponse> cmsBlockContentTpResponseList = cmsBlockTpResponse.getBlockContent();
        if (cmsBlockContentTpResponseList != null && cmsBlockContentTpResponseList.size() > 0) {
            for (CmsBlockContentTpResponse cmsBlockContentTpResponse : cmsBlockContentTpResponseList) {
                BaseData data = this.getDataFromCms(cmsBlockContentTpResponse, commonParam, vipType, defaultStream,
                        defaultStreamName);
                if (uiType != null && ChannelConstants.CHANNEL_UIPLATE_TYPE_BGIMG == uiType) {
                    this.tmpBaseDataMap.put(ChannelConstants.CHANNEL_TYPE_BACKGROUND_PRE + channelId, data);
                    continue;
                }
                if (data != null) {
                    if (dataList.size() == dataSize) {
                        break;
                    } else {
                        dataList.add(data);
                    }
                    this.specialPositionPut(channelId, data, cmsBlockContentTpResponse);
                }
            }
        }
    }

    /**
     * 这是个性化需求确保板块中配置某个内容展示在列表第几个位置
     * @param channelId
     * @param data
     * @param cmsBlockContentTpResponse
     */
    private void specialPositionPut(Integer channelId, BaseData data,
            CmsBlockContentTpResponse cmsBlockContentTpResponse) {
        if (channelId != null
                && channelId == 1083
                && cmsBlockContentTpResponse.getPosition() != null
                && ("5".equals(cmsBlockContentTpResponse.getPosition()) || "6".equals(cmsBlockContentTpResponse
                        .getPosition()))) {
            String key = ChannelConstants.CHANNEL_TYPE_POSITION_PREFIX + channelId + "_"
                    + cmsBlockContentTpResponse.getPosition();
            this.tmpBaseDataMap.put(key, data);
        }
    }

    /**
     * 频道推荐数据列表
     * @param rcPageid
     * @param rcHistory
     * @param channelId
     * @return
     */
    public BaseResponse getChannelDataRecommendationList(String rcPageid, String rcHistory, String cpId, String cid,
            Integer num, Integer channelId, Integer startIdex, String channelCode, String bizType,
            CommonParam commonParam) {
        // 印度版逻辑
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equals(commonParam.getTerminalApplication())) {
            return this.getLevidiChannelDataRecommendationList(channelId, cpId, cid, num, startIdex, channelCode,
                    bizType, commonParam);
        }
        List<ChannelData> channelDataList = new LinkedList<ChannelData>();
        int block_color_index = 0;
        String defaultStream = this.facadeCacheDao.getChannelCacheDao().getChannelDefaultStreamById(
                String.valueOf(channelId));
        String defaultStreamName = LetvStreamCommonConstants.getStreamName(defaultStream, commonParam.getLangcode());
        // 推荐列表
        PageResponse<Recommendation> recommendationList = recommendationService.getMultiBlocks(
                rcPageid, null, rcHistory, channelId, defaultStream, defaultStreamName, commonParam);
        for (Recommendation recommendation : recommendationList.getData()) {
            ChannelData channelData = new ChannelData();
            channelData.setDataList(recommendation.getDataList());
            channelData.setTitle(recommendation.getBlockName());
            channelData.setArea(recommendation.getArea());
            channelData.setBucket(recommendation.getBucket());
            channelData.setBlockType(recommendation.getBlockType());
            channelData.setReid(recommendation.getReid());
            channelData.setFragId(recommendation.getFragId());
            // for 电影推广位 标识
            if (StringUtil.isNotBlank(channelData.getTitle())) {
                if (channelData.getTitle().equals("电影")) {
                    channelData.setChannelType(ChannelConstants.MOVIE_CHANNEL_TYPE);
                }
            }

            if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equals(commonParam.getTerminalApplication())) {
                if (StringUtil.equals(ChannelConstants.LECOM_HOME_CATEGORY_BLOCKID, recommendation.getBlockId())) {
                    channelData.setUiPlateType(ChannelConstants.UI_PLATE_TYPE_4);
                } else if (StringUtil.equals(ChannelConstants.LECOM_HOME_ADDON_BLOCKID, recommendation.getBlockId())) {
                    channelData.setUiPlateType(ChannelConstants.CHANNEL_UIPLATE_TYPE_6);
                } else {
                    channelData.setUiPlateType(ChannelConstants.UI_PLATE_TYPE_5);
                }
            }
            String blockKey = null;
            // 香港推荐没有blockId，根据type进行key的配置
            if (!TerminalCommonConstant.TERMINAL_APPLICATION_LE.equals(commonParam.getTerminalApplication())) {
                if ("HK".equalsIgnoreCase(commonParam.getWcode())) {
                    blockKey = recommendation.getBlockType() + "-" + recommendation.getCategoryId();
                } else {
                    if (RecommendationTpConstant.REC_MAYBELIKE_CHILD_PAGEID.equals(rcPageid)) {
                        // 儿童桌面猜你喜欢数据配置
                        blockKey = rcPageid + "-" + recommendation.getCategoryId();
                    } else {
                        blockKey = recommendation.getBlockId() + "-" + recommendation.getCategoryId();
                    }
                }
                ChannelData channelDataPlus = this.getChannelDataPlusFromBlockKey(blockKey, commonParam.getFlush());
                if (channelDataPlus != null) {
                    // 国际化多语言title字段
                    String title = mutilLanguageService.getMessage(
                            ChannelConstants.ITV_CHANNELDATA_RECOMMENDATIONBLOCK_TABLE, blockKey,
                            ChannelConstants.ITV_CHANNELDATA_RECOMMENDATIONBLOCK_TITLE, commonParam.getLangcode());
                    if (title == null) {
                        title = channelDataPlus.getTitle();
                    }
                    channelData.setTitle(title);
                    channelData.setTitleDataType(channelDataPlus.getTitleDataType());
                    if (ChannelConstants.PAGEID_BLOCK_COLOR_FROM_DB.contains(rcPageid)) {
                        channelData.setTitleBgColor(ChannelConstants.BLOCK_COLOR_VIP);
                    } else {
                        // 保证tv同一屏色块背景色不同,
                        String color = this.facadeCacheDao.getChannelCacheDao().getBlockColor();
                        String[] colors = null;
                        if (color == null || "".equals(color)) {
                            color = ChannelConstants.BLOCK_COLOR;
                            // 放入缓存
                            this.facadeCacheDao.getChannelCacheDao().setBlockColor(color);
                        }
                        colors = color.split(",");
                        int pos = block_color_index % (colors.length);
                        channelData.setTitleBgColor(colors[pos]);
                        block_color_index = block_color_index + 1;
                    }
                    channelData.setTitleChannelId(channelDataPlus.getTitleChannelId());
                    channelData.setTitleSearchCondition(channelDataPlus.getTitleSearchCondition());
                    channelData.setTitleAlbumId(channelDataPlus.getTitleAlbumId());
                    channelData.setConfigInfo(channelDataPlus.getConfigInfo());
                    if (RecommendationTpConstant.REC_MAYBELIKE_CHILD_PAGEID.equals(rcPageid)) {
                        channelData.setImg(channelDataPlus.getTitleSearchCondition());
                    }
                }
                if (ProductLineConstants.LETV_COMMON.equals(commonParam.getTerminalApplication())) {// 通用版本过滤4k
                    if ((channelData.getChannelId() != null && 707 == channelData.getChannelId())
                            || "4K".equals(channelData.getTitle())) {
                        continue;
                    }
                }
            }
            JumpUtil.buildJumpObjFromDb(channelData, channelData.getTitleDataType(), defaultStream, defaultStreamName,
                    commonParam);
            channelDataList.add(channelData);
        }

        // 2016-08-31，lecom美国版需求添加，这里设置数据，供SDK使用
        // setChannelDataRecDataList4Sdk(rcPageid, channelDataList,
        // commonParam);

        return new PageResponse<ChannelData>(channelDataList);
    }

    public BaseResponse getChannelDataSdkRecommendationList(String rcPageid, String dataIndex, String dataTypes,
            Integer size, CommonParam commonParam) {
        PageResponse<ChannelData> response = new PageResponse<ChannelData>();
        // String logPrefix = "getChannelDataSdkRecommendationList_" + rcPageid
        // + "_" + commonParam.getMac();

        if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) {

            if (size != null && size <= 0) {
                ErrorCodeCommonUtil.setErrorResponse(response, ErrorCodeConstants.COMMON_ILLEGAL_PARAMETER,
                        commonParam.getLangcode());
            } else {
                if (size == null || size > 20) {
                    size = 20;
                }

                List<String> dataTypeList = null;
                String[] dataTypeArray = dataTypes.split(",");
                if (dataTypeArray != null) {
                    dataTypeList = new ArrayList<String>();
                    for (String dataType : dataTypeArray) {
                        dataType = StringUtils.trimToNull(dataType);
                        if (dataType != null) {
                            dataTypeList.add(dataType);
                        }
                    }
                }

                Set<String> dataIndexSet = null;
                dataIndex = StringUtils.trimToNull(dataIndex);
                if (dataIndex != null) {
                    String[] dataIndexStrArray = dataIndex.split(",");
                    if (dataIndexStrArray != null) {
                        dataIndexSet = new HashSet<String>();
                        for (String dataIndexStr : dataIndexStrArray) {
                            Integer dataIndexInt = StringUtil.toInteger(dataIndexStr, null);
                            if (dataIndexInt != null && dataIndexInt >= 0) {
                                dataIndexSet.add(dataIndexStr);
                            }
                        }
                    }
                }

                // 推荐列表

                String originLangcode = commonParam.getLangcode();
                updateChannelDataRecDataList4Sdk(rcPageid, commonParam);
                List<ChannelData> sourceChannelDataList = ChannelConstants
                        .getChannelDataRecList4LecomSDK(genChannelDataRecListKey4LeconUS(rcPageid, originLangcode,
                                commonParam));

                List<ChannelData> channelDataList = new ArrayList<ChannelData>();
                if (!CollectionUtils.isEmpty(sourceChannelDataList)) {
                    if (dataIndexSet == null || dataIndexSet.isEmpty()) {
                        // 默认取第一行
                        ChannelData channelData = filterChannelData4Sdk(size, dataTypeList,
                                sourceChannelDataList.get(0));
                        if (channelData != null) {
                            channelDataList.add(channelData);
                        }
                    } else {
                        for (String dataIndexEle : dataIndexSet) {
                            Integer dataIndexInt = StringUtil.toInteger(dataIndexEle);
                            if (dataIndexInt < sourceChannelDataList.size()) {
                                ChannelData channelData = filterChannelData4Sdk(size, dataTypeList,
                                        sourceChannelDataList.get(dataIndexInt));
                                if (channelData != null) {
                                    channelDataList.add(channelData);
                                }
                            }
                        }
                    }
                }

                response.setData(channelDataList);
            }
        }

        return response;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private PageResponse getLevidiChannelDataRecommendationList(Integer channelId, String cpId, String cid,
            Integer num, Integer startIdex, String channelCode, String bizType, CommonParam commonParam) {
        PageResponse pageResponse = recommendationService.getLevidiRecommendation(cpId, cid,
                null, null, num, startIdex, channelCode, bizType, commonParam);
        return pageResponse;
    }

    /**
     * 专题详情接口
     * 专题包参数不为空即可认为客户端要获取指定包数据
     * @param subjectId
     *            专题Id
     * @param packageIds
     *            专题包Id
     * @param commonParam
     * @return
     */
    public Response<SubjectDto> getSubjectPackagesById(String subjectId, String packageIds, Integer model,
                                                       CommonParam commonParam) {
        Response<SubjectDto> response = new Response<SubjectDto>();
        String logPrefix = "getSubjectPackagesById_" + subjectId + "_" + commonParam.getMac();
        String errorCode = null;
        String errorMsgCode = null;
        if (StringUtil.isNotBlank(packageIds)) {
            // 读取包数据
            SubjectDto subjectDto = this.getPackagesData(subjectId, packageIds, model, commonParam, response);
            response.setData(subjectDto);
        } else if (StringUtil.isNotBlank(subjectId)) {
            // 读取专题数据
            GetSubjectTpResponse tpResponse = this.facadeTpDao.getCmsTpDao().getCmsSubjectById(subjectId, commonParam);
            if (tpResponse != null && tpResponse.getCode() == 200 && tpResponse.getData() != null) {
                SubjectDataTpResponse subjectData = tpResponse.getData();
                String playPlatform = subjectData.getPlayPlatform();
                if (playPlatform == null || playPlatform.indexOf(CommonConstants.TV_PLATFROM_CODE) == -1) {
                    errorCode = ErrorCodeConstants.CHANNEL_GET_SUBJECT_DATA_OFFLINE;
                    ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
                    log.warn(logPrefix + "[errorCode=" + errorCode + "]: tjpackage data is offline.");
                } else {
                    // 根据调用第三方接口的返回值解析出SubjectDto数据
                    SubjectDto subjectDto = null;
                    if ("4835".equals(subjectId)) { // TODO: 临时策略
                        subjectDto = this.facadeCacheDao.getChannelCacheDao().getSubjectDto(subjectId);
                        if (null == subjectDto || (null != commonParam.getFlush() && 1 == commonParam.getFlush())) {
                            subjectDto = this.parserTjPackageTpResponse(tpResponse, model,
                                    commonParam.getBroadcastId(), logPrefix, commonParam);
                            if (null != subjectDto.getPackageList() && subjectDto.getPackageList().size() > 0) {
                                this.facadeCacheDao.getChannelCacheDao().setSubjectDto(subjectId, subjectDto);
                            }
                        }
                    } else {
                        subjectDto = this.parserTjPackageTpResponse(tpResponse, model, commonParam.getBroadcastId(),
                                logPrefix, commonParam);
                    }

                    if (subjectDto.getSubjectType() == SubjectConstant.SUBJECT_TYPE_HOTSPOT) {// 热点，需要缓存热点专题
                        List<SubjectPackageDto> list = subjectDto.getPackageList();
                        if (list != null && list.size() > 0) {
                            Map<String, SubjectPackageDto> videoPackageMap = new HashMap<String, SubjectPackageDto>();
                            for (int i = 0; i < list.size(); i++) {
                                SubjectPackageDto subjectPackageDto = list.get(i);
                                if (subjectPackageDto != null) {
                                    String packageId = subjectPackageDto.getId();
                                    if (StringUtils.isNotBlank(packageId)) {
                                        videoPackageMap.put(subjectPackageDto.getId(), subjectPackageDto);
                                    }
                                }
                            }
                            if (videoPackageMap != null && videoPackageMap.size() > 0) {
                                // 热点专题，缓存的数据结构：<专题id，<视频包id，视频包>>
                                this.facadeCacheDao.getChannelCacheDao().setSubjectPackages(subjectDto.getId(),
                                        videoPackageMap);
                            }
                            for (int i = 2; i < list.size(); i++) {
                                // 只取热点专题包中的前两个视频包中视频列表详情信息，后面的只取视频包信息
                                list.get(i).setDataList(null);
                            }
                        }
                    }
                    response.setData(subjectDto);
                }
            } else {
                errorMsgCode = MessageUtils.getMessage(errorCode, commonParam.getLangcode());
                ResponseUtil.setErrorResponse(response, ErrorCodeConstants.CHANNEL_GET_TJPACKAGE_ERROR, errorMsgCode,
                        commonParam.getLangcode());
                log.warn(logPrefix + "[errorCode=" + errorCode + "]: get tjpackage failure.");
            }
        } else {
            // parameters subjectId and packageIds are both empoty
            errorCode = ErrorCodeConstants.CHANNEL_PARAMETER_ERROR;
            errorMsgCode = errorCode
                    + ErrorMsgCodeUtil.parseErrorMsgCode(ChannelConstants.SUBJECT,
                            ChannelMsgCodeConstant.CHANNEL_GET_TJPACKAGE_REQUEST_SUBJECTID_EMPTY);
            ResponseUtil.setErrorResponse(response, errorCode, errorMsgCode, commonParam.getLangcode());
            log.info(logPrefix + "[errorCode=" + errorCode + "]: illegal parameters.");
        }
        return response;
    }

    private SubjectDto getPackagesData(String subjectId, String packageIds, Integer model, CommonParam commonParam,
            Response<SubjectDto> response) {
        String logPrefix = "getHotspotSubjectDto_" + subjectId + "_" + packageIds;
        SubjectDto subjectDto = null;
        String[] packageIdArray = packageIds.split(",");
        if (packageIdArray == null || packageIdArray.length == 0) {
            log.info(logPrefix + ",packageids is null.");
        } else {
            subjectDto = new SubjectDto();
            // 记录需要从CMS获取视频包的包id
            List<String> needGetDataFromCMS = new ArrayList<String>();
            // 存储视频包列表
            List<SubjectPackageDto> dataList = new ArrayList<SubjectPackageDto>();
            if (StringUtils.isNotBlank(subjectId)) {
                Map<String, SubjectPackageDto> packageMap = this.facadeCacheDao.getChannelCacheDao()
                        .getSubjectPackages(subjectId);
                if (packageMap != null && packageMap.size() > 0) {
                    for (String key : packageIdArray) {
                        SubjectPackageDto data = packageMap.get(key);
                        if (data != null) {
                            dataList.add(data);
                        } else {// 缓存中没有此视频的数据，要从CMS中获取
                            needGetDataFromCMS.add(key);
                            log.info(logPrefix + "_" + key + ",this package is null in the cache.");
                        }
                    }
                } else {// 缓存中没数据，全部要从CMS获取
                    log.info(logPrefix + "_" + subjectId + ",this subject is null in the cache.");
                    Collections.addAll(needGetDataFromCMS, packageIdArray);
                }
            } else {// 专题包id为空，直接从CMS获取视频包信息
                log.info(logPrefix + ", get the package data from CMS.");
                Collections.addAll(needGetDataFromCMS, packageIdArray);
            }
            if (needGetDataFromCMS != null && needGetDataFromCMS.size() > 0) {
                for (String packageId : needGetDataFromCMS) {
                    GetPackageDataTpResponse tpResponse = this.facadeTpDao.getCmsTpDao().getSubjectPackageData(
                            packageId, commonParam);
                    if (tpResponse != null) {
                        SubjectTjPackageTpResponse dataResponse = tpResponse.getData();
                        if (dataResponse == null || dataResponse.getDataList() == null
                                || dataResponse.getDataList().size() == 0) {
                            log.info(logPrefix + "_" + packageId + ",get this package data from CMS the data is empty.");
                            continue;
                        } else {// 可以正常解析的情况
                            SubjectPackageDto data = this.parseSubjectPackageData(dataResponse,
                                    SubjectConstant.SUBJECT_TYPE_HOTSPOT, model, logPrefix, commonParam);
                            if (data != null) {
                                dataList.add(data);
                            } else {
                                log.info(logPrefix + "_" + packageId + ",parse this package is failure.");
                            }
                        }
                    } else {
                        String errorCode = ErrorCodeConstants.CHANNEL_GET_SUBJECT_PACKAGE_ERROR;
                        ResponseUtil.setErrorResponse(response, errorCode,
                                MessageUtils.getMessage(errorCode, commonParam.getLangcode()),
                                commonParam.getLangcode());
                        log.info(logPrefix + "_" + packageId + ",get this package data from CMS is failure.");
                        break;// 跳出循环
                    }
                }
            }
            subjectDto.setSubjectType(SubjectConstant.SUBJECT_TYPE_HOTSPOT);
            subjectDto.setPackageList(dataList);
        }
        return subjectDto;
    }

    public Response<ChartsDto> getChartsContentById(Integer chartsId, CommonParam commonParam) {
        Response<ChartsDto> response = new Response<ChartsDto>();
        String logPrefix = "getChartsContentById_" + chartsId;
        ChartsDto charts = null;
        String errorCode = null;
        // 参数校验
        if (chartsId != null && chartsId.intValue() > 0) {
            ChartsMysqlTable chartsMysqlTable = this.facadeMysqlDao.getChartsMysqlDao().getById(chartsId);
            if (chartsMysqlTable != null) {
                charts = this.parseChartsDto(chartsMysqlTable, commonParam,
                        ChannelConstants.CHARTS_PARSE_CHARTS_TYPE_DETAIL);
                response.setData(charts);
            }
        } else {
            errorCode = ErrorCodeConstants.CHANNEL_GET_RANK_ERROR;
            response.setResultStatus(0);
            response.setErrCode(errorCode);
            response.setErrMsg(MessageUtils.getMessage(errorCode, commonParam.getLangcode()));
            log.error(logPrefix + "[errorCode=" + errorCode + "]: get rank failure.");
        }

        return response;
    }

    /**
     * 从CMS单元数据提取
     * @param cmsBlockContentTpResponse
     * @param commonParam
     * @param vipType
     * @param defaultStream
     * @param defaultStreamName
     * @return
     */
    public BaseData getDataFromCms(CmsBlockContentTpResponse cmsBlockContentTpResponse, CommonParam commonParam,
            Integer vipType, String defaultStream, String defaultStreamName) {
        return this.getDataFromCms(cmsBlockContentTpResponse, commonParam, vipType, defaultStream, defaultStreamName,
                null);
    }

    public BaseData getDataFromCms(CmsBlockContentTpResponse cmsBlockContentTpResponse, CommonParam commonParam,
            Integer vipType, String defaultStream, String defaultStreamName, Integer dataType) {
        // 没有推送TV端的数据需要过滤
        if ((cmsBlockContentTpResponse.getPushflag() == null)
                || !cmsBlockContentTpResponse.getPushflag().contains(CommonConstants.TV_PLATFROM_CODE)) {
            return null;
        }
        Integer type = Integer.valueOf(0);
        if (cmsBlockContentTpResponse.getType() != null) {
            type = cmsBlockContentTpResponse.getType();
        }
        BaseData baseData = null;
        if (StringUtils.isEmpty(cmsBlockContentTpResponse.getContent())) {
            /**
             * 如果content为空，则说明是跳转H5，收银台，功能入口，不跳转的图片等
             */
            baseData = this.getCmsDataOfEmptyContent(cmsBlockContentTpResponse, vipType, defaultStream,
                    defaultStreamName, commonParam, dataType);
        } else if (type == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_ALBUM) {// 专辑从缓存中获取相册信息，当它在缓存中不存在时，从VRS获取它，然后更新缓存
            baseData = this.getCmsDataOfAlbum(cmsBlockContentTpResponse, defaultStream, defaultStreamName, commonParam);
        } else if (type == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_VIDEO) {// 视频同上
            baseData = this.getCmsDataOfVideo(cmsBlockContentTpResponse, defaultStream, defaultStreamName, commonParam);
        } else if (type == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_SUBJECT) {// 专题第三方获取
            baseData = this.getCmsDataOfSubject(cmsBlockContentTpResponse, defaultStream, defaultStreamName,
                    commonParam);
        } else if (type == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_LIVE) {// 直播缓存
            baseData = this.getCmsDataOfLive(cmsBlockContentTpResponse, defaultStream, defaultStreamName, commonParam);
        } else if (type == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_PAGE) {// 定制页面
            baseData = this.getCmsDataOfPage(cmsBlockContentTpResponse, defaultStream, defaultStreamName, commonParam,
                    dataType);
        } else if (type == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_STAR) { // 明星
            baseData = this.getCmsDataOfStar(cmsBlockContentTpResponse, defaultStream, defaultStreamName, commonParam);
        } else if (type == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_TVSTATION) {// 轮播台
            baseData = this.getCmsDataOfTvStation(cmsBlockContentTpResponse, defaultStream, defaultStreamName,
                    commonParam);
        } else if (type == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_APP) {// app
            baseData = this.getCmsDataOfAPP(cmsBlockContentTpResponse, defaultStream, defaultStreamName, commonParam);
        } else if (type == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_ADD_ON) {// addon
            baseData = this.getCmsDataOfAddOn(cmsBlockContentTpResponse, commonParam);
        } else if (type == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_PUBLISHER) {// publiser
            baseData = this.getCmsDataOfPublisher(cmsBlockContentTpResponse, commonParam);
        } else if (type == CmsTpConstant.CMS_BLOCKCONTENT_TYPE_WEBSITE_ALBUM) {// 外网专辑
            baseData = this.getCmsDataOfWebsiteAlbum(cmsBlockContentTpResponse, commonParam);
        }
        return baseData;
    }

    /**
     * cms数据获取addon配置信息
     * @param cmsBlockContentTpResponse
     * @param commonParam
     * @return
     */
    private BaseData getCmsDataOfAddOn(CmsBlockContentTpResponse cmsBlockContentTpResponse, CommonParam commonParam) {
        String packageId = "";
        String productId = "";
        if (StringUtil.isNotBlank(cmsBlockContentTpResponse.getContent())) {
            String[] ids = cmsBlockContentTpResponse.getContent().split("\\|");
            packageId = ids[0];
            if (ids.length >= 2) {
                productId = ids[1];
            }
        }
        BaseBlock block_com = new BaseBlock();
        block_com.setId(productId);
        block_com.setPackageId(packageId);
        block_com.setName(cmsBlockContentTpResponse.getTitle());
        block_com.setImg(cmsBlockContentTpResponse.getTvPic());
        return JumpUtil.bulidJumpObj(block_com, DataConstant.DATA_TYPE_LEPARTER, null, null, commonParam);
    }

    private BaseData getCmsDataOfPublisher(CmsBlockContentTpResponse cmsBlockContentTpResponse, CommonParam commonParam) {

        Channel channel = new Channel();
        channel.setPic1(cmsBlockContentTpResponse.getPic1());
        channel.setPic2(cmsBlockContentTpResponse.getPic2());
        channel.setCpId(cmsBlockContentTpResponse.getContent());
        channel.setName(cmsBlockContentTpResponse.getTitle());
        return JumpUtil.bulidJumpObj(channel, DataConstant.DATA_TYPE_CONTENT_LIST, null, null, commonParam);
    }

    /**
     * 外网专辑
     * @param cmsBlockContentTpResponse
     * @param commonParam
     * @return
     */
    private BaseData getCmsDataOfWebsiteAlbum(CmsBlockContentTpResponse cmsBlockContentTpResponse,
            CommonParam commonParam) {

        AlbumDto album = new AlbumDto();
        album.setGlobalId(cmsBlockContentTpResponse.getContent());
        if (StringUtil.isBlank(album.getGlobalId())) {
            return album;
        }
        album.setName(cmsBlockContentTpResponse.getTitle());
        album.setSubName(cmsBlockContentTpResponse.getSubTitle());
        album.setImg(cmsBlockContentTpResponse.getTvPic());
        AlbumDto albumDetail = videoService.getWebsiteAlbumDetail(album.getGlobalId(),
                commonParam);
        if (albumDetail != null) {
            album.setIfCharge(albumDetail.getIfCharge());
            // for tvod icon type
            album.setChargeType(albumDetail.getChargeType());
        }

        return JumpUtil.bulidJumpObj(album, DataConstant.DATA_TYPE_ALBUM, null, null, commonParam);
    }

    /**
     * cms数据提取轮播台信息
     * @param cmsBlockContentTpResponse
     * @param defaultStream
     * @param defaultStreamName
     * @param commonParam
     * @return
     */
    private BaseData getCmsDataOfTvStation(CmsBlockContentTpResponse cmsBlockContentTpResponse, String defaultStream,
            String defaultStreamName, CommonParam commonParam) {
        BaseBlock block = new BaseBlock();
        block.setBlockId(cmsBlockContentTpResponse.getContent());
        block.setDataType(DataConstant.DATA_TYPE_TVSTATION);
        block.setType(2);// 轮播台
        block.setImg(cmsBlockContentTpResponse.getTvPic());
        block.setName(cmsBlockContentTpResponse.getTitle());
        block.setSubName(cmsBlockContentTpResponse.getSubTitle());
        return JumpUtil.bulidJumpObj(block, DataConstant.DATA_TYPE_TVSTATION, defaultStream, defaultStreamName,
                commonParam);
    }

    /**
     * cms数据提取游戏数据
     * @param cmsBlockContentTpResponse
     * @param defaultStream
     * @param defaultStreamName
     * @param commonParam
     * @return
     */
    private BaseData getCmsDataOfAPP(CmsBlockContentTpResponse cmsBlockContentTpResponse, String defaultStream,
            String defaultStreamName, CommonParam commonParam) {
        BaseBlock block = new BaseBlock();
        block.setDataType(DataConstant.DATA_TYPE_PLAY_CENTER);
        block.setId(cmsBlockContentTpResponse.getContent());
        block.setName(cmsBlockContentTpResponse.getTitle());
        block.setSubName(cmsBlockContentTpResponse.getSubTitle());
        block.setShortDesc(cmsBlockContentTpResponse.getShorDesc());
        block.setImg(cmsBlockContentTpResponse.getTvPic());
        block.setExtendText(cmsBlockContentTpResponse.getRemark());// app安装包路径
        block.setPic1(cmsBlockContentTpResponse.getPic1());
        block.setIconType(IconConstants.ICON_TYPE_APP);
        return JumpUtil.bulidJumpObj(block, DataConstant.DATA_TYPE_PLAY_CENTER, defaultStream, defaultStreamName,
                commonParam);
    }

    /**
     * cms数据提取明星信息
     * @param cmsBlockContentTpResponse
     * @param defaultStream
     * @param defaultStreamName
     * @param commonParam
     * @return
     */
    private BaseData getCmsDataOfStar(CmsBlockContentTpResponse cmsBlockContentTpResponse, String defaultStream,
            String defaultStreamName, CommonParam commonParam) {
        StarCms star = new StarCms();
        star.setStarId(cmsBlockContentTpResponse.getContent());
        star.setName(cmsBlockContentTpResponse.getTitle());
        star.setImg(cmsBlockContentTpResponse.getTvPic());
        return JumpUtil.bulidJumpObj(star, DataConstant.DATA_TYPE_STAR, defaultStream, defaultStreamName, commonParam);
    }

    /**
     * cms数据提取定制页面信息
     * @param cmsBlockContentTpResponse
     * @param defaultStream
     * @param defaultStreamName
     * @param commonParam
     * @param pDataType
     * @return
     */
    private BaseData getCmsDataOfPage(CmsBlockContentTpResponse cmsBlockContentTpResponse, String defaultStream,
            String defaultStreamName, CommonParam commonParam, Integer pDataType) {
        Page page = new Page();
        page.setPageId(Integer.valueOf(cmsBlockContentTpResponse.getContent()));
        page.setName(cmsBlockContentTpResponse.getTitle());
        page.setSubName(cmsBlockContentTpResponse.getSubTitle());
        page.setImg(cmsBlockContentTpResponse.getTvPic());

        page.setTitleIcon(cmsBlockContentTpResponse.getPic1());// icon图
        page.setConfigInfo(cmsBlockContentTpResponse.getSubTitle());
        page.setDataUrl(cmsBlockContentTpResponse.getTagUrl());
        Page pageTmp = this.getPageBean(cmsBlockContentTpResponse.getShorDesc());
        if (pageTmp != null) {
            page.setChannelId(pageTmp.getChannelId());
            page.setTitleFocus1(pageTmp.getTitleFocus1());
            page.setTitleFocus2(pageTmp.getTitleFocus2());
            page.setTitleDataType(pageTmp.getTitleDataType());
            page.setTitleBgColor(pageTmp.getTitleBgColor());
            page.setChannelCode(pageTmp.getChannelCode());
        }
        Integer dataType = DataConstant.DATA_TYPE_PAGE;
        if (page.getTitleDataType() != null) {
            dataType = page.getTitleDataType();
        }
        if (isWfSubject(cmsBlockContentTpResponse)) {
            WFSubject mWFSubject = new WFSubject();
            mWFSubject.setName(page.getName());
            mWFSubject.setImg(page.getImg());
            mWFSubject.setSubName(page.getSubName());
            mWFSubject.setPageId(page.getPageId());
            dataType = DataConstant.DATA_TYPE_WF_SUBJECT;
            JumpUtil.bulidJumpObj(mWFSubject, dataType, defaultStream, defaultStreamName, commonParam);
            if (TerminalUtil.isSupportWFSubject(commonParam)) {
                return mWFSubject;
            } else {
                mWFSubject.setWFJump(mWFSubject.getJump());
                return JumpUtil.buildJumpToLESTORE(mWFSubject, commonParam);
            }
        }
        return JumpUtil.bulidJumpObj(page, dataType, defaultStream, defaultStreamName, commonParam);
    }

    private boolean isWfSubject(CmsBlockContentTpResponse cmsBlockContentTpResponse) {
        CmsTpResponseExt cmsTpResponseExt = cmsBlockContentTpResponse.getExtendJson();
        if (cmsTpResponseExt == null) {
            return false;
        }
        Integer pageType = cmsTpResponseExt.getExtendTvPageType();
        if (pageType == null) {
            return false;
        }
        return pageType == ChannelConstants.CMS_PAGE_WF_SUBJECT_TYPE;
    }

    private Page getPageBean(String str) {
        Page page = null;
        if (StringUtil.isNotBlank(str)) {
            page = JsonUtil.parse(str, Page.class);
        }
        return page;
    }

    /**
     * cms数据提取直播信息
     * @param cmsBlockContentTpResponse
     * @param defaultStream
     * @param defaultStreamName
     * @param commonParam
     * @return
     */
    private BaseData getCmsDataOfLive(CmsBlockContentTpResponse cmsBlockContentTpResponse, String defaultStream,
            String defaultStreamName, CommonParam commonParam) {
        Live live = new Live();
        live.setLiveId(cmsBlockContentTpResponse.getContent());
        live.setName(cmsBlockContentTpResponse.getTitle());
        live.setSubName(cmsBlockContentTpResponse.getSubTitle());
        live.setImg(cmsBlockContentTpResponse.getTvPic());
        LiveProgram liveProgram = this.facadeCacheDao.getLiveCacheDao().getLiveData(live.getLiveId(), "1007", 0);
        if (liveProgram != null) {
            live.setState(liveProgram.getState());
            live.setCategoryId(liveProgram.getCid());
            if ((liveProgram.getIsPay() != null) && (liveProgram.getIsPay() == 1)) {
                live.setIsFree(false);
            } else {
                live.setIsFree(true);
            }
        }
        return JumpUtil.bulidJumpObj(live, DataConstant.DATA_TYPE_LIVE, defaultStream, defaultStreamName, commonParam);
    }

    /**
     * cms数据中提取视频信息
     * @param cmsBlockContentTpResponse
     * @param defaultStream
     * @param defaultStreamName
     * @param commonParam
     * @return
     */
    private BaseData getCmsDataOfVideo(CmsBlockContentTpResponse cmsBlockContentTpResponse, String defaultStream,
            String defaultStreamName, CommonParam commonParam) {
        VideoDto video = new VideoDto();
        // BlockContentVideo cmsVideo = cmsBlockContentTpResponse.getVideo();
        video.setVideoId(cmsBlockContentTpResponse.getContent());
        Long videoId = StringUtil.toLong(video.getVideoId(), null);
        VideoMysqlTable videoMysql = albumVideoAccess.getVideo(videoId, commonParam);
        // 国广机器接收到国广请求时如果没审核通过，此数据不展示
        if (ChannelConstants.BROADCAST_ID == CommonConstants.CIBN && videoMysql == null) {
            return null;
        }
        video.setName(cmsBlockContentTpResponse.getTitle());
        if ((video.getName() == null || "".equals(video.getName())) && cmsBlockContentTpResponse.getNameCn() != null) {
            video.setName(cmsBlockContentTpResponse.getNameCn());
        }
        video.setSubName(cmsBlockContentTpResponse.getSubTitle());
        video.setImg(cmsBlockContentTpResponse.getTvPic());
        video.setPic1(cmsBlockContentTpResponse.getPic1());

        if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) {
            if (videoMysql != null) {
                video.setBigImg(videoMysql.getPicNew(1440, 810));
            }
            Map<String, String> pics = new HashMap<String, String>();
            pics.put(CommonConstants.CMS_TV_PIC, cmsBlockContentTpResponse.getTvPic());
            if (StringUtil.isBlank(cmsBlockContentTpResponse.getTvPic())
                    && cmsBlockContentTpResponse.getPicList() != null) {
                pics.put(CommonConstants.PIXEL_400_225,
                        cmsBlockContentTpResponse.getPicList().get(CommonConstants.PIXEL_400_225));
            }
            video.setPic_urls(pics);
            if (!StringUtil.isBlank(cmsBlockContentTpResponse.getRemark())
                    && cmsBlockContentTpResponse.getRemark().matches("^\\d+\\*\\d+$")) {
                video.setImgSize(cmsBlockContentTpResponse.getRemark());
            } else {
                if (videoMysql != null) {
                    video.setImg(videoMysql.getPicNew(800, 600));
                    if (StringUtil.isBlank(video.getImg())) {
                        video.setImg(videoMysql.getPicNew(400, 300));
                    }
                }
            }
        }

        if (videoMysql != null) {
            video.setTvCopyright(1);
        } else {
            video.setTvCopyright(0);
            video.setSrc(1);// 目前只有内网的专辑详情页
            // 其实这个字段2.9.1版本不应该在此返回，客户端直接写死，但是为了后期扩展外网播放，所以由服务端返回，以后做外网播放时，此字段要废掉
            video.setWebsite(ApplicationConstants.WEBSITE_WWW_LETV_COM);
            video.setWebPlayUrl(DataConstant.PC_PLAY_URL + cmsBlockContentTpResponse.getContent() + ".html");
        }
        if (videoMysql != null) {
            if (videoMysql.getDuration() != null && videoMysql.getDuration() != 0) {
                video.setDuration(videoMysql.getDuration() * 1000L);
            }
            video.setAlbumId(String.valueOf(videoMysql.getPid()));
            video.setCategoryId(videoMysql.getCategory());
            video.setSubCategoryId(videoMysql.getSub_category());
            if (videoMysql.getPay_platform() != null) {
                video.setIfCharge(MmsDataUtil.isSupportPayPlatform(videoMysql.getPay_platform(),
                        commonParam.getP_devType()) ? "1" : "0");
            } else {
                video.setIfCharge("0");
            }
            // for tvod icon type
            Integer chargeType = JumpUtil.getChargeType(videoMysql.getPay_platform(), commonParam);
            if (null == chargeType) {
                chargeType = JumpUtil.getChargeType(videoMysql.getPay_platform());
            }
            video.setChargeType(chargeType);

            if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                video.setChargeInfos(JumpUtil.genChargeInfos(videoMysql.getPay_platform()));
                ChargeInfoDto chargeInfoDto = JumpUtil.genChargeInfoDto(commonParam.getP_devType(),
                        videoMysql.getPay_platform());
                if (null != chargeInfoDto) {
                    video.setIfCharge(chargeInfoDto.getIsCharge());
                    video.setChargeType(JumpUtil.getChargeType(chargeInfoDto));
                }
            }
        }
        if (TerminalCommonConstant.TERMINAL_APPLICATION_CHILD_APP.equals(commonParam.getTerminalApplication())
                || TerminalCommonConstant.TERMINAL_APPLICATION_LECHILD_ALONE.equals(commonParam
                        .getTerminalApplication())) {
            video.setPic2(cmsBlockContentTpResponse.getPic2());
            video.setPic3(cmsBlockContentTpResponse.getMobilePic());
            video.setPic4(cmsBlockContentTpResponse.getPadPic());
            if (StringUtil.isNotBlank(cmsBlockContentTpResponse.getShorDesc())
                    && cmsBlockContentTpResponse.getShorDesc().matches("[a-dA-D]+")) {
                video.setDesc(cmsBlockContentTpResponse.getShorDesc().toUpperCase());
            }
        }
        return JumpUtil
                .bulidJumpObj(video, DataConstant.DATA_TYPE_VIDEO, defaultStream, defaultStreamName, commonParam);
    }

    /**
     * cms数据提取专辑信息
     * @param cmsBlockContentTpResponse
     * @param defaultStream
     * @param defaultStreamName
     * @param commonParam
     * @return
     */
    private BaseData getCmsDataOfAlbum(CmsBlockContentTpResponse cmsBlockContentTpResponse, String defaultStream,
            String defaultStreamName, CommonParam commonParam) {
        AlbumDto album = new AlbumDto();
        album.setAlbumId(cmsBlockContentTpResponse.getContent());
        Long albumId = StringUtil.toLong(album.getAlbumId(), null);
        AlbumMysqlTable albumMysql = albumVideoAccess.getAlbum(albumId, commonParam);
        // 国广机器接收到国广请求时如果没审核通过，此数据不展示
        if (ChannelConstants.BROADCAST_ID == CommonConstants.CIBN && albumMysql == null) {
            return null;
        }
        album.setName(cmsBlockContentTpResponse.getTitle());
        album.setSubName(cmsBlockContentTpResponse.getSubTitle());
        album.setImg(cmsBlockContentTpResponse.getTvPic());
        album.setPic1(cmsBlockContentTpResponse.getPic1());
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) {
            if (albumMysql != null) {
                album.setBigImg(albumMysql.getPicNew(1440, 810));
            }
            Map<String, String> pics = new HashMap<String, String>();
            pics.put(CommonConstants.CMS_TV_PIC, album.getImg());
            if (StringUtil.isBlank(cmsBlockContentTpResponse.getTvPic())
                    && cmsBlockContentTpResponse.getPicList() != null) {
                pics.put(CommonConstants.PIXEL_400_225,
                        cmsBlockContentTpResponse.getPicList().get(CommonConstants.PIXEL_400_225));
                pics.put(CommonConstants.PIXEL_300_400,
                        cmsBlockContentTpResponse.getPicList().get(CommonConstants.PIXEL_300_400));
            }
            album.setPic_urls(pics);
            if (!StringUtil.isBlank(cmsBlockContentTpResponse.getRemark())
                    && cmsBlockContentTpResponse.getRemark().matches("^\\d+\\*\\d+$")) {
                album.setImgSize(cmsBlockContentTpResponse.getRemark());
            } else {
                if (albumMysql != null) {
                    album.setImg(albumMysql.getPicNew(600, 800));
                    if (StringUtil.isBlank(album.getImg())) {
                        album.setImg(albumMysql.getPicNew(300, 400));
                    }
                }
            }
        }
        if (albumMysql != null) {
            album.setTvCopyright(1);//是否有版权，0没有1有
        } else {
            album.setTvCopyright(0);
            album.setSrc(1);// 目前只有内网的专辑详情页
        }
        if (albumMysql != null) {
            album.setScore(albumMysql.getScore());
            album.setCategoryId(albumMysql.getCategory());
            album.setSubCategoryId(albumMysql.getSub_category());
            if (!StringUtil.isBlank(albumMysql.getPay_platform())
                    && VideoCommonUtil.isCharge(albumMysql.getPay_platform(), albumMysql.getPlay_platform(),
                            commonParam.getP_devType())) {
                album.setIfCharge("1");//是否收费，1收费0不收
            } else {
                album.setIfCharge("0");
            }

            // for tvod icon type
            Integer chargeType = JumpUtil.getChargeType(albumMysql.getPay_platform(), commonParam);
            if (null == chargeType) {
                chargeType = JumpUtil.getChargeType(albumMysql.getPay_platform());
            }
            album.setChargeType(chargeType);//收费平台

            if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                album.setChargeInfos(JumpUtil.genChargeInfos(albumMysql.getPay_platform()));
                ChargeInfoDto chargeInfoDto = JumpUtil.genChargeInfoDto(commonParam.getP_devType(),
                        albumMysql.getPay_platform());
                if (null != chargeInfoDto) {
                    album.setIfCharge(chargeInfoDto.getIsCharge());
                    album.setChargeType(JumpUtil.getChargeType(chargeInfoDto));
                }
            }

            // 芈月传容器新增更新到{*}集
            this.setNowEpisode(album, albumMysql, commonParam);
        }
        return JumpUtil
                .bulidJumpObj(album, DataConstant.DATA_TYPE_ALBUM, defaultStream, defaultStreamName, commonParam);
    }

    /**
     * cms配置的跳转H5,收银台，功能入口，非跳转页等方式的解析
     * @param cmsBlockContentTpResponse
     * @param vipType
     * @param defaultStream
     * @return
     */
    private BaseData getCmsDataOfEmptyContent(CmsBlockContentTpResponse cmsBlockContentTpResponse, Integer vipType,
            String defaultStream, String defaultStreamName, CommonParam commonParam) {
        return this.getCmsDataOfEmptyContent(cmsBlockContentTpResponse, vipType, defaultStream, defaultStreamName,
                commonParam, null);
    }

    private BaseData getCmsDataOfEmptyContent(CmsBlockContentTpResponse cmsBlockContentTpResponse, Integer vipType,
            String defaultStream, String defaultStreamName, CommonParam commonParam, Integer dataType) {
        if (StringUtils.isNotEmpty(cmsBlockContentTpResponse.getSkipPage())) {// 跳频道页
            Channel channel = new Channel();
            channel.setChannelId(this.getChannelIdFromCmsChannelId(cmsBlockContentTpResponse.getSkipPage()));
            channel.setName(cmsBlockContentTpResponse.getTitle());
            channel.setSubName(cmsBlockContentTpResponse.getSubTitle());
            channel.setImg(cmsBlockContentTpResponse.getTvPic());
            channel.setTitleDataType(DataConstant.DATA_TYPE_CHANNEL);
            channel.setTitleIcon(cmsBlockContentTpResponse.getPic1());
            Page pageTmp = this.getPageBean(cmsBlockContentTpResponse.getShorDesc());
            if (pageTmp != null) {
                if (pageTmp.getPageId() != null) {
                    channel.setPageId(pageTmp.getPageId());
                }
                channel.setTitleFocus1(pageTmp.getTitleFocus1());
                channel.setTitleFocus2(pageTmp.getTitleFocus2());
                channel.setTitleBgColor(pageTmp.getTitleBgColor());
            }
            return JumpUtil.bulidJumpObj(channel, DataConstant.DATA_TYPE_CHANNEL, defaultStream, defaultStreamName,
                    commonParam);
        } else if (StringUtils.isNotEmpty(cmsBlockContentTpResponse.getTvUrl())) {// 跳浏览器
            Browser browser = new Browser();
            browser.setName(cmsBlockContentTpResponse.getTitle());
            browser.setImg(cmsBlockContentTpResponse.getTvPic());
            browser.setUrl(cmsBlockContentTpResponse.getTvUrl());
            browser.setBrowserType(DataConstant.BROWSER_TYPE_BUILTIN);// 外置

            Map<String, String> urlMap = new HashMap<String, String>();
            urlMap.put("0", cmsBlockContentTpResponse.getTvUrl()); // 默认
            browser.setUrlMap(urlMap);
            return JumpUtil.bulidJumpObj(browser, DataConstant.DATA_TYPE_BROWSER, defaultStream, defaultStreamName,
                    commonParam);
        } else if (StringUtils.isNotEmpty(cmsBlockContentTpResponse.getSkipUrl())
                && (CmsTpConstant.CMS_BROWSER_TYPE_BUILTIN.equals(cmsBlockContentTpResponse.getSkipType()) || CmsTpConstant.CMS_BROWSER_TYPE
                        .equals(cmsBlockContentTpResponse.getSkipType()))) {
            /*
             * 跳浏览器（新逻辑）；
             * 4--TV内跳解释为跳转收银台；
             * 5--TV外跳，设置为不跳收银台且不做响应
             * 2016-01-23产品新需求，4--跳h5，5--跳外网播放
             */
            Browser browser = new Browser();
            browser.setName(cmsBlockContentTpResponse.getTitle());
            browser.setSubName(cmsBlockContentTpResponse.getSubTitle());
            browser.setExtendText(cmsBlockContentTpResponse.getShorDesc());
            browser.setImg(cmsBlockContentTpResponse.getTvPic());
            browser.setUrl(cmsBlockContentTpResponse.getSkipUrl());// 外网播放表示是播放地址
            if (CmsTpConstant.CMS_BROWSER_TYPE_BUILTIN.equals(cmsBlockContentTpResponse.getSkipType())) {// 跳h5
                browser.setBrowserType(DataConstant.BROWSER_TYPE_BUILTIN);
                browser.setOpenType(DataConstant.BROWSER_OPENTYPE_NONE);
            } else {// 跳外网播放，需要src、website、playurl参数
                if (ChannelConstants.BROADCAST_ID == CommonConstants.CIBN) {
                    return null;// 国广版不允许外网播放
                }
                browser.setBrowserType(DataConstant.BROWSER_TYPE_EXTERNAL);
                browser.setOpenType(DataConstant.BROWSER_OPENTYPE_NONE);
                browser.setWebsite(ApplicationConstants.WEBSITE_WWW_LETV_COM);
                browser.setSrc(1);
            }
            browser.setVipFlag(cmsBlockContentTpResponse.getPosition());
            browser.setExtendImg(cmsBlockContentTpResponse.getPic1());
            browser.setButtonImg(cmsBlockContentTpResponse.getPic2());
            Map<String, String> urlMap = new HashMap<String, String>();
            urlMap.put("0", cmsBlockContentTpResponse.getSkipUrl()); // 默认
            browser.setUrlMap(urlMap);
            return JumpUtil.bulidJumpObj(browser, DataConstant.DATA_TYPE_BROWSER, defaultStream, defaultStreamName,
                    commonParam);
        } else if (cmsBlockContentTpResponse.getExtendJson() != null
                && cmsBlockContentTpResponse.getExtendJson().getExtendPage() != null
                && cmsBlockContentTpResponse.getExtendJson().getExtendPage().length() > 0) {
            // cmsTV管理配置功能入口
            return this.getCmsDataOfExtendJson(cmsBlockContentTpResponse, vipType, defaultStream, defaultStreamName,
                    commonParam);
        } else if (!StringUtil.isBlank(cmsBlockContentTpResponse.getRemark())
                && ChannelConstants.THIRD_PART_APP_CODE.indexOf(cmsBlockContentTpResponse.getRemark()) > -1) {// 乐见桌面跳转第三方app
            BaseBlock block = new BaseBlock();
            block.setAppCode(cmsBlockContentTpResponse.getRemark());
            block.setAppParam(cmsBlockContentTpResponse.getPosition());
            block.setName(cmsBlockContentTpResponse.getTitle());
            block.setSubName(cmsBlockContentTpResponse.getSubTitle());
            block.setImg(cmsBlockContentTpResponse.getTvPic());
            // 芈月传容器会员推广跳golive
            if ("vip".equals(block.getAppParam()) || "notvip".equals(block.getAppParam())) {
                block.setAppParam(cmsBlockContentTpResponse.getShorDesc());
            }
            block.setVipFlag(cmsBlockContentTpResponse.getPosition());
            block.setExtendImg(cmsBlockContentTpResponse.getPic1());
            block.setButtonImg(cmsBlockContentTpResponse.getPic2());
            return JumpUtil.bulidJumpObj(block, 0, defaultStream, defaultStreamName, commonParam);
        } else {
            if (null != dataType && ChannelService.existPageType(dataType)) {
                StaticBlock staticBlock = new StaticBlock();
                staticBlock.setName(cmsBlockContentTpResponse.getTitle());
                staticBlock.setSubName(cmsBlockContentTpResponse.getSubTitle());
                staticBlock.setShortDesc(cmsBlockContentTpResponse.getShorDesc());
                if (StringUtil.isBlank(cmsBlockContentTpResponse.getTvPic())) {
                    staticBlock.setImg(cmsBlockContentTpResponse.getPic1());
                    staticBlock.setPic1(cmsBlockContentTpResponse.getPic2());
                } else {
                    staticBlock.setImg(cmsBlockContentTpResponse.getTvPic());
                    staticBlock.setPic1(cmsBlockContentTpResponse.getPic1());
                }
                staticBlock.setDataType(dataType);
                return staticBlock;
            } else {
                // 儿童专家建议
                StaticBlock staticBlock = new StaticBlock();
                staticBlock.setName(cmsBlockContentTpResponse.getTitle());
                staticBlock.setSubName(cmsBlockContentTpResponse.getSubTitle());
                staticBlock.setShortDesc(cmsBlockContentTpResponse.getShorDesc());
                staticBlock.setImg(cmsBlockContentTpResponse.getTvPic());
                staticBlock.setPic1(cmsBlockContentTpResponse.getPic1());
                // 下边的4各节点待客户端儿童强升一次后可以删掉20160429
                staticBlock.setAdviceContent(cmsBlockContentTpResponse.getShorDesc());
                staticBlock.setAdviceImg(cmsBlockContentTpResponse.getTvPic());
                staticBlock.setAdviceHourLong(cmsBlockContentTpResponse.getTitle());
                staticBlock.setAdviceDayLong(cmsBlockContentTpResponse.getSubTitle());
                staticBlock.setPos(cmsBlockContentTpResponse.getPosition());
                return JumpUtil.bulidJumpObj(staticBlock, DataConstant.DATA_TYPE_BLANK, defaultStream,
                        defaultStreamName, commonParam);
            }
        }
    }

    /**
     * 根据请求类型获取cms板块id
     * @param blockId
     * @param commonParam
     * @return
     */
    public String getDefBlockId(String blockId, Integer dataType, CommonParam commonParam) {
        if (StringUtil.isBlank(blockId)) {
            if (null != dataType) {
                blockId = ChannelService.translatePageType2BlockId(dataType);
            }
        }
        return blockId;
    }

    public static boolean existPageType(Integer dataType) {
        String pageIds = ApplicationUtils.get(ApplicationConstants.CMS_BLOCKID_PAGES);
        return StringUtil.isNotBlank(pageIds)
                && (pageIds.startsWith(dataType + "-") || pageIds.contains("," + dataType + "-"));
    }

    public static String translatePageType2BlockId(Integer dataType) {
        String blockId = null;
        String pageIds = ApplicationUtils.get(ApplicationConstants.CMS_BLOCKID_PAGES);
        if (StringUtil.isNotBlank(pageIds)) {
            String[] pageIdArr = pageIds.split(",");
            String[] pageIdVal = null;
            for (int i = 0, len = pageIdArr.length; i < len; i++) {
                pageIdVal = pageIdArr[i].split("-");
                if (dataType.intValue() == Integer.parseInt(pageIdVal[0])) {
                    return pageIdVal[1];
                }
            }

        }
        return blockId;
    }

    /**
     * cmsTV管理配置功能入口的方式，先把代码提出来后续考虑怎么精简
     * @param cmsBlockContentTpResponse
     * @param vipType
     * @param defaultStream
     * @return
     */
    private BaseData getCmsDataOfExtendJson(CmsBlockContentTpResponse cmsBlockContentTpResponse, Integer vipType,
            String defaultStream, String defaultStreamName, CommonParam commonParam) {
        BaseData baseData = null;
        CmsTpResponseExt extendJson = cmsBlockContentTpResponse.getExtendJson();
        String extendPage = extendJson.getExtendPage();
        String[] extendPageElements = extendPage.split("\\|");
        int paramsLength = extendPageElements.length;
        if (extendPageElements != null || paramsLength > 0) {
            Integer dataType = StringUtil.toInteger(extendPageElements[0], DataConstant.DATA_TYPE_BLANK);
            if (DataConstant.DATA_TYPE_BLANK != dataType) {
                switch (dataType.intValue()) {
                case DataConstant.DATA_TYPE_BROWSER:
                    // 跳浏览器，默认就是展示H5
                    Map<String, String> urls = null;
                    if (extendJson.getExtendTvDict() != null && extendJson.getExtendTvDict().getExtendJson() != null) {
                        urls = extendJson.getExtendTvDict().getExtendJson().getUrls();
                    }
                    if (!CollectionUtils.isEmpty(urls)) {
                        Browser browser = new Browser();
                        browser.setName(cmsBlockContentTpResponse.getTitle());
                        browser.setImg(cmsBlockContentTpResponse.getTvPic());
                        browser.setUrl(cmsBlockContentTpResponse.getSkipUrl());
                        browser.setBrowserType(DataConstant.BROWSER_TYPE_BUILTIN); // 默认打开内置浏览器
                        if (paramsLength == 3) {// 配置3个参数，表示跳h5后h5支持交互
                            browser.setOpenType(DataConstant.BROWSER_OPENTYPE_INTERACTIVE); // 展示H5，并支持h5交互
                            browser.setUrl(urls.get("url"));
                        } else {
                            browser.setOpenType(DataConstant.BROWSER_OPENTYPE_NONE); // 默认只展示H5，无交互
                        }
                        // 此部分针对乐视儿童公告是否会员的特殊处理
                        if (vipType != null) {
                            browser.setBenefitDeadline(cmsBlockContentTpResponse.getSubTitle());
                            if (VipTpConstant.SVIP_PC == vipType) {
                                browser.setUrl(urls.get("vipPage"));
                            } else {
                                browser.setUrl(urls.get("un_vipPage"));
                            }
                        }
                        urls.put("0", browser.getUrl());
                        browser.setUrlMap(urls);
                        baseData = JumpUtil.bulidJumpObj(browser, DataConstant.DATA_TYPE_BROWSER, defaultStream,
                                defaultStreamName, commonParam);
                    } else {
                        if (paramsLength == 3) {// 配置3个参数，表示跳h5后h5支持交互
                            Browser browser = new Browser();
                            browser.setName(cmsBlockContentTpResponse.getTitle());
                            browser.setImg(cmsBlockContentTpResponse.getTvPic());
                            browser.setUrl(cmsBlockContentTpResponse.getSkipUrl());
                            browser.setBrowserType(DataConstant.BROWSER_TYPE_BUILTIN); // 默认打开内置浏览器
                            browser.setOpenType(DataConstant.BROWSER_OPENTYPE_INTERACTIVE); // 展示H5，并支持h5交互
                            browser.setUrl(cmsBlockContentTpResponse.getSkipUrl());
                            Map<String, String> urlMap = new HashMap<String, String>();
                            urlMap.put("0", cmsBlockContentTpResponse.getSkipUrl());
                            browser.setUrlMap(urlMap);
                            baseData = JumpUtil.bulidJumpObj(browser, DataConstant.DATA_TYPE_BROWSER, defaultStream,
                                    defaultStreamName, commonParam);
                        }
                    }
                    break;
                case DataConstant.DATA_TYPE_VIDEO_SALE:// 跳视频营销容器
                    if (paramsLength >= 2) {
                        if (paramsLength == 2) {// 长度为2，表示只有两个参数，表示版块
                            BaseBlock block = new BaseBlock();
                            block.setDataType(dataType);
                            block.setName(cmsBlockContentTpResponse.getTitle());
                            block.setSubName(cmsBlockContentTpResponse.getSubTitle());
                            block.setImg(cmsBlockContentTpResponse.getTvPic());
                            block.setBlockId(extendPageElements[1]);
                            baseData = JumpUtil.bulidJumpObj(block, dataType, defaultStream, defaultStreamName,
                                    commonParam);
                        } else {// 长度为3，表示有3个参数，最后一个字段有特殊意义
                            if (extendJson.getExtendTvDict() != null
                                    && extendJson.getExtendTvDict().getExtendJson() != null
                                    && extendJson.getExtendTvDict().getExtendJson().getUrls() != null) {
                                Map<String, String> map = extendJson.getExtendTvDict().getExtendJson().getUrls();
                                VideoSaleDto videoSaleDto = new VideoSaleDto();
                                videoSaleDto.setTitleMap(map);
                                videoSaleDto.setType(extendPageElements[2]);
                                baseData = JumpUtil.bulidJumpObj(videoSaleDto, dataType, defaultStream,
                                        defaultStreamName, commonParam);
                            }
                        }
                    }
                    break;
                case DataConstant.DATA_TYPE_CHILDREN:// 儿童桌面cms任意配置更多打洞功能入口
                    if (paramsLength >= 2) {
                        BaseBlock block = new BaseBlock();
                        block.setName(cmsBlockContentTpResponse.getTitle());
                        block.setSubName(cmsBlockContentTpResponse.getSubTitle());
                        block.setExtendText(extendPage);// 打洞参数例如：19|102|5
                        block.setSubType(Integer.valueOf(extendPageElements[1]));
                        // 新桌面打洞
                        if (paramsLength >= 3) {
                            block.setType(Integer.valueOf(extendPageElements[2]));
                            if (paramsLength > 3) {
                                block.setId(extendPageElements[3]);
                            }
                        }
                        block.setImg(cmsBlockContentTpResponse.getTvPic());
                        block.setExtendImg(cmsBlockContentTpResponse.getPic1());
                        baseData = JumpUtil.bulidJumpObj(block, block.getSubType(), defaultStream, defaultStreamName,
                                commonParam);
                    }
                    break;
                case DataConstant.DATA_TYPE_RETRIEVE:// 跳转检索页
                    if (paramsLength == 2) {
                        ChannelData channelData = new ChannelData();
                        channelData.setTitleDataType(Integer.valueOf(extendPageElements[0]));
                        channelData.setChannelId(Integer.valueOf(extendPageElements[1]));
                        channelData.setTitle(cmsBlockContentTpResponse.getTitle());
                        channelData.setTitleSearchCondition(cmsBlockContentTpResponse.getSubTitle());
                        channelData.setImg(cmsBlockContentTpResponse.getTvPic());
                        baseData = JumpUtil.bulidJumpObj(channelData, channelData.getTitleDataType(), defaultStream,
                                defaultStreamName, commonParam);
                    }
                    break;
                // 播放记录／追剧收藏页／专题收藏专题（所有专题类型）
                case DataConstant.DATA_TYPE_TOPIC_ACTIVITY:// 919活动
                case DataConstant.DATA_TYPE_CONTAINER: // 芈月传容器
                case DataConstant.DATA_TYPE_HISTORY: // 乐见桌面打洞
                case DataConstant.DATA_TYPE_WATCHLIST: // 追剧收藏
                case DataConstant.DATA_TYPE_SUBJECT_FAV: // 专题收藏
                case DataConstant.DATA_TYPE_LEGUIDE: // 乐导视
                case DataConstant.DATA_TYPE_SEARCH: // 搜索
                case DataConstant.DATA_TYPE_CHANNEL_LIST: // 频道列表
                case DataConstant.DATA_TYPE_LIVE_LIST: // 直播大厅
                case DataConstant.DATA_TYPE_INDEX_LIST: // TV版首页
                case DataConstant.DATA_TYPE_MINE_LIST: // TV版我的
                case DataConstant.DATA_TYPE_CHANNEL: // 频道墙
                case DataConstant.DATA_TYPE_APP_LIST: // APP List
                case DataConstant.DATA_TYPE_SUBJECT_LIST: // 专题list
                case DataConstant.DATA_TYPE_VIP_LIST: // tv版会员
                case DataConstant.DATA_TYPE_DISCOVER_LIST: // tv版发现
                case DataConstant.DATA_TYPE_SETTINGS_LIST: // levidi setting
                case DataConstant.DATA_TYPE_HOME_LIST: // levidi home
                case DataConstant.DATA_TYPE_PUBLISHER_LIST:// levidi publisher
                case DataConstant.DATA_TYPE_CONTENT_LIST:// eros
                    BaseBlock block_com = new BaseBlock();
                    // 容器data_type
                    block_com.setType(dataType);
                    // 跳转走模板特殊处理
                    block_com.setSubType(dataType);
                    if (paramsLength >= 2) {
                        // 当前类型容器的id,冗余id比较多
                        String id = extendPageElements[1];
                        block_com.setId(id);
                        block_com.setBlockId(id);
                        block_com.setContainerId(id);
                        block_com.setChannelId(id);
                        if (StringUtil.isBlank(defaultStream) && StringUtil.isNotBlank(id)) {
                            defaultStream = this.facadeCacheDao.getChannelCacheDao().getChannelDefaultStreamById(
                                    String.valueOf(id));
                            defaultStreamName = LetvStreamCommonConstants.getStreamName(defaultStream,
                                    commonParam.getLangcode());
                        }
                    }
                    block_com.setName(cmsBlockContentTpResponse.getTitle());
                    block_com.setSubName(cmsBlockContentTpResponse.getSubTitle());
                    block_com.setImg(cmsBlockContentTpResponse.getTvPic());
                    block_com.setExtendImg(cmsBlockContentTpResponse.getPic1());
                    // levidi导航前面的icon
                    block_com.setPic1(cmsBlockContentTpResponse.getPic1());
                    block_com.setPic2(cmsBlockContentTpResponse.getPic2());
                    baseData = JumpUtil
                            .bulidJumpObj(block_com, dataType, defaultStream, defaultStreamName, commonParam);
                    break;
                default:
                    // 老业务，走默认逻辑
                    BaseBlock block = new BaseBlock();
                    block.setType(dataType);
                    block.setName(cmsBlockContentTpResponse.getTitle());
                    block.setSubName(cmsBlockContentTpResponse.getSubTitle());
                    block.setExtendText(cmsBlockContentTpResponse.getShorDesc());
                    block.setImg(cmsBlockContentTpResponse.getTvPic());
                    if (extendPageElements.length > 1) {
                        block.setLabelIdToPlay(extendPageElements[1]);
                    }
                    // 芈月传容器加会员推广
                    block.setVipFlag(cmsBlockContentTpResponse.getPosition());
                    block.setExtendImg(cmsBlockContentTpResponse.getPic1());
                    block.setButtonImg(cmsBlockContentTpResponse.getPic2());
                    baseData = JumpUtil.bulidJumpObj(block, dataType, defaultStream, defaultStreamName, commonParam);
                    break;
                }
            }
        }
        return baseData;
    }

    /**
     * cms专辑信息补充更新到{*}集
     * @param album
     */
    private void setNowEpisode(AlbumDto album, AlbumMysqlTable albumMysql, CommonParam commonParam) {
        if (album != null) {
            Integer nowEpisodes = StringUtil.isBlank(albumMysql.getNowEpisodes()) ? 0 : Integer.valueOf(albumMysql
                    .getNowEpisodes());
            if (album.getCategoryId() != null
                    && (VideoConstants.Category.TV == album.getCategoryId() || VideoConstants.Category.CARTOON == album
                            .getCategoryId())) {
                Integer isEnd = albumMysql.getIs_end();
                Object[] params = { nowEpisodes };
                if (isEnd != null && isEnd == 0) { // 未完结
                    if (nowEpisodes != null && nowEpisodes > 0) {
                        album.setNowEpisode(MessageUtils.getMessage(
                                ChannelConstants.CHANNEL_ALBUM_UPDATED_TO_NOWEPISODES, commonParam.getLangcode(),
                                params));
                    } else {
                        album.setNowEpisode(MessageUtils.getMessage(ChannelConstants.CHANNEL_ALBUM_ABOUT_ONLINE,
                                commonParam.getLangcode()));
                    }
                } else {
                    album.setNowEpisode(MessageUtils.getMessage(ChannelConstants.CHANNEL_ALBUM_IS_END,
                            commonParam.getLangcode(), params));
                }
            } else if (album.getCategoryId() != null && VideoConstants.Category.VARIETY == album.getCategoryId()
                    && nowEpisodes != 0) {
                Object[] params = { this.getFollowNum(nowEpisodes, "yyyy", "yyyy-MM-dd", "MM-dd") };
                album.setNowEpisode(MessageUtils.getMessage(ChannelConstants.CHANNEL_ALBUM_UPDATED_TO_NOWISSUE,
                        commonParam.getLangcode(), params));
            }
        }
    }

    private String getFollowNum(Integer Num, String format1, String format2, String format3) {
        try {
            if (Num == null || Integer.toString(Num).length() < 8) {
                return "";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat df = new SimpleDateFormat(format1);
            SimpleDateFormat df1 = new SimpleDateFormat(format2);
            SimpleDateFormat df2 = new SimpleDateFormat(format3);
            Date followDate = sdf.parse(Num.toString());
            Date nowDate = new Date();
            String followStr = null;
            String nowStr = null;
            String followNum = "";
            followStr = df.format(followDate);
            nowStr = df.format(nowDate);
            if (Integer.parseInt(followStr) < Integer.parseInt(nowStr)) {
                followNum = df1.format(followDate);
            } else {
                followNum = df2.format(followDate);
            }
            return followNum;
        } catch (Exception e) {
            log.error("getFollowNum_ error" + e.getMessage(), e);
        }
        return "";
    }

    /**
     * 获取专题中视频包信息
     * @param subjectId
     *            专题id
     * @param packageIds
     *            热点专题中专题包id
     * @param commonParam
     *            通用参数
     * @return
     */
    public Response<SubjectDto> getHotspotSubjectPackageData(String subjectId, String packageIds, Integer model,
            CommonParam commonParam) {
        String logPrefix = "getHotspotSubjectPackageData_" + subjectId + "_" + packageIds + "_" + commonParam.getMac();
        Response<SubjectDto> response = new Response<SubjectDto>();
        String errorCode = null;
        String errorMsgCode = null;

        if (StringUtils.isBlank(packageIds)) {
            errorCode = ErrorCodeConstants.CHANNEL_PARAMETER_ERROR;
            Integer validCode = ChannelMsgCodeConstant.CHANNEL_GET_SUBJECT_PACKAGE_REQUEST_PACKAGEIDS_EMPTY;
            errorMsgCode = errorCode + ErrorMsgCodeUtil.parseErrorMsgCode(ChannelConstants.SUBJECT, validCode);
            response.setResultStatus(0);
            response.setErrCode(errorCode);
            response.setErrMsg(MessageUtils.getMessage(errorMsgCode, commonParam.getLangcode()));
        } else {
            // 视频包id，拆分成数组
            String[] packageIdArray = packageIds.split(",");
            if (packageIdArray == null || packageIdArray.length == 0) {
                log.info(logPrefix + ",packageid is null.");
            } else {
                SubjectDto subjectDto = new SubjectDto();
                // 记录需要从CMS获取视频包的包id
                List<String> needGetDataFromCMS = new ArrayList<String>();
                // 存储视频包列表
                List<SubjectPackageDto> dataList = new ArrayList<SubjectPackageDto>();
                if (StringUtils.isNotBlank(subjectId)) {
                    // 如果专题包id不为空，则先读取缓存
                    Map<String, SubjectPackageDto> packageMap = this.facadeCacheDao.getChannelCacheDao()
                            .getSubjectPackages(subjectId);
                    if (packageMap != null && packageMap.size() > 0) {
                        for (String key : packageIdArray) {
                            SubjectPackageDto data = packageMap.get(key);
                            if (data != null) {
                                dataList.add(data);
                            } else {// 缓存中没有此视频的数据，要从CMS中获取
                                needGetDataFromCMS.add(key);
                                log.info(logPrefix + "_" + key + ",this package is null in the cache.");
                            }
                        }
                    } else {// 缓存中没数据，全部要从CMS获取
                        log.info(logPrefix + "_" + subjectId + ",this subject is null in the cache.");
                        Collections.addAll(needGetDataFromCMS, packageIdArray);
                    }
                } else {// 专题包id为空，直接从CMS获取视频包信息
                    log.info(logPrefix + ", get the package data from CMS.");
                    Collections.addAll(needGetDataFromCMS, packageIdArray);
                }
                if (needGetDataFromCMS != null && needGetDataFromCMS.size() > 0) {
                    for (String packageId : needGetDataFromCMS) {
                        GetPackageDataTpResponse tpResponse = this.facadeTpDao.getCmsTpDao().getSubjectPackageData(
                                packageId, commonParam);
                        if (tpResponse != null) {
                            SubjectTjPackageTpResponse dataResponse = tpResponse.getData();
                            if (dataResponse == null) {
                                log.info(logPrefix + "_" + packageId
                                        + ",get this package data from CMS the data is empty.");
                                continue;
                            }
                            List<SubjectTjPackageDataContent> list = dataResponse.getDataList();
                            if (list == null || list.size() == 0) {
                                log.info(logPrefix + "_" + packageId
                                        + ",get this package data from CMS the data list is empty.");
                            } else {// 可以正常解析的情况
                                SubjectPackageDto data = this.parseSubjectPackageData(dataResponse,
                                        SubjectConstant.SUBJECT_TYPE_HOTSPOT, model, logPrefix, commonParam);
                                if (data != null) {
                                    dataList.add(data);
                                } else {
                                    log.info(logPrefix + "_" + packageId + ",parse this package is failure.");
                                }
                            }
                        } else {
                            errorCode = ErrorCodeConstants.CHANNEL_GET_SUBJECT_PACKAGE_ERROR;
                            response.setResultStatus(0);
                            response.setErrCode(errorCode);
                            response.setErrMsg(MessageUtils.getMessage(errorCode, commonParam.getLangcode()));
                            log.info(logPrefix + "_" + packageId + ",get this package data from CMS is failure.");
                            break;// 跳出循环
                        }
                    }
                }
                subjectDto.setSubjectType(SubjectConstant.SUBJECT_TYPE_HOTSPOT);
                subjectDto.setPackageList(dataList);
                response.setData(subjectDto);
            }
        }
        return response;
    }

    public Response<BaseData> getCmsBlockContent(String blockId, Integer dataType, CommonParam commonParam) {
        Response<BaseData> response = new Response<BaseData>();
        String logPrefix = "getCmsBlockContent_" + blockId;
        String errorCode = null;

        int validCode = CommonMsgCodeConstant.REQUEST_SUCCESS;
        blockId = this.getDefBlockId(blockId, dataType, commonParam);
        if (StringUtils.isBlank(blockId)) {
            validCode = ChannelMsgCodeConstant.CHANNEL_GET_CMSBLOCK_REQUEST_BLOCKID_EMPTY;
        }
        if (CommonMsgCodeConstant.REQUEST_SUCCESS != validCode) {
            errorCode = ErrorCodeConstants.CHANNEL_PARAMETER_ERROR;
            response.setResultStatus(0);
            response.setErrCode(errorCode);
            response.setErrMsg(MessageUtils.getMessage(
                    ErrorMsgCodeUtil.parseErrorMsgCode(errorCode, ChannelConstants.CMSBLOCK, validCode),
                    commonParam.getLangcode()));
            log.info(logPrefix + "[errorCode=" + errorCode + "]:.parameter illegal.");
        } else {
            CmsBlockTpResponse tpResponse = this.facadeTpDao.getCmsTpDao().getCmsBlockNewById(blockId, commonParam);
            if (tpResponse == null || tpResponse.getBlockContent() == null) {// 获取数据失败
                errorCode = ErrorCodeConstants.CHANNEL_GET_BLOCK_CONTENT_ERROR;
                response.setResultStatus(0);
                response.setErrCode(errorCode);
                response.setErrMsg(MessageUtils.getMessage(
                        ErrorMsgCodeUtil.parseErrorMsgCode(errorCode, ChannelConstants.PRELIVE, validCode),
                        commonParam.getLangcode()));
                log.info(logPrefix + "[errorCode=" + errorCode + "]:.parameter illegal.");
            } else {
                if (dataType != null && dataType == DataConstant.DATA_TYPE_VIDEO_SALE) {// 视频营销数据类型
                    VideoSaleDto videoSaleDto = new VideoSaleDto();
                    videoSaleDto.setDataType(DataConstant.DATA_TYPE_VIDEO_SALE);
                    List<BaseData> dataList = new LinkedList<BaseData>();// 数据列表（目前是视频列表）
                    List<CmsBlockContentTpResponse> contentList = tpResponse.getBlockContent();
                    Map<String, String> buttonMap = null;// 按钮文案集合
                    String year = null;// 活动年限
                    for (CmsBlockContentTpResponse cmsBlockContentTpResponse : contentList) {
                        if (cmsBlockContentTpResponse == null) {
                            continue;
                        }
                        BaseData baseData = this.getDataFromCms(cmsBlockContentTpResponse, commonParam, null, null,
                                null);
                        if (baseData != null) {
                            if (baseData instanceof VideoSaleDto) {
                                VideoSaleDto v = (VideoSaleDto) baseData;
                                String type = v.getType();
                                Map<String, String> map = v.getTitleMap();
                                if (ChannelConstants.VIDEO_SALE_PAGE_TITLE.equalsIgnoreCase(type)) {// 使用标题集合
                                    videoSaleDto.setTitleMap(map);
                                } else if (ChannelConstants.VIDEO_SALE_PAGE_BUTTON.equalsIgnoreCase(type)) {// 按钮文案集合
                                    buttonMap = map;
                                } else if (ChannelConstants.VIDEO_SALE_PAGE_URL.equalsIgnoreCase(type)) {// 二维码集合
                                    if (map != null) {
                                        String img = map.get(ChannelConstants.VIDEO_SALE_BASEDATA_IMG);
                                        videoSaleDto.setUrl(map.get(ChannelConstants.VIDEO_SALE_BASEDATA_URL));
                                        videoSaleDto.setImg(img);
                                        videoSaleDto.setBackground(map
                                                .get(ChannelConstants.VIDEO_SALE_BASEDATA_BACKGROUND));
                                        year = map.get(ChannelConstants.VIDEO_SALE_BASEDATA_YEAR);
                                        if (StringUtil.isNotBlank(img)) {
                                            this.facadeCacheDao.getVipCacheDao().setDeliverAddress(
                                                    commonParam.getUserId(),
                                                    VipTpConstant.USER_TOUCH_POSITION_VIDEO_SALE + blockId, img);
                                        }
                                    }
                                }
                            } else if (baseData instanceof VideoDto) {// 视频列表，但是此处不需要跳转信息，提高客户端效率
                                baseData.setJump(null);
                                dataList.add(baseData);
                            }
                        }
                    }
                    if (buttonMap != null) {
                        if (StringUtils.isNotBlank(year) && StringUtil.toInteger(year, 1) > 1) {// 没有购买按钮，只有跳h5按钮
                            ButtonDto gift = new ButtonDto();
                            gift.setTitle(buttonMap.get(ChannelConstants.VIDEO_SALE_BASEDATA_BUTTON_GIFT));
                            gift.setOpenType(DataConstant.OPENTYPE_H5);// 只跳h5
                            gift.setBrowserType(DataConstant.BROWSER_TYPE_BUILTIN);
                            gift.setDataType(DataConstant.DATA_TYPE_BROWSER);
                            Browser browser = new Browser();
                            browser.setDataType(DataConstant.DATA_TYPE_BROWSER);
                            browser.setPosition(VipTpConstant.USER_TOUCH_POSITION_VIDEO_SALE + blockId);
                            browser.setBrowserType(DataConstant.BROWSER_TYPE_BUILTIN);
                            browser.setOpenType(DataConstant.BROWSER_OPENTYPE_NONE);
                            browser.setUrl(videoSaleDto.getUrl());
                            Map<String, String> urlMap = new HashMap<String, String>();
                            urlMap.put("0", videoSaleDto.getUrl());
                            browser.setUrlMap(urlMap);
                            JumpData jump = new JumpData();
                            jump.setValue(browser);
                            jump.setType(DataConstant.DATA_TYPE_BROWSER);
                            gift.setJump(jump);
                            videoSaleDto.setButtonGift(gift);
                        } else {
                            ButtonDto buy = new ButtonDto();
                            buy.setTitle(buttonMap.get(ChannelConstants.VIDEO_SALE_BASEDATA_BUTTON_BUY));
                            buy.setOpenType(DataConstant.BROWSER_OPENTYPE_CHECKOUT);
                            buy.setBrowserType(DataConstant.BROWSER_TYPE_BUILTIN);
                            PilotDto pilotDto_skip = new PilotDto();
                            pilotDto_skip.setDataType(DataConstant.DATA_TYPE_CHECKSTAND);
                            pilotDto_skip.setPosition(VipTpConstant.USER_TOUCH_POSITION_VIDEO_SALE + blockId);
                            JumpData jump = new JumpData();
                            jump.setValue(pilotDto_skip);
                            jump.setType(DataConstant.DATA_TYPE_CHECKSTAND);
                            buy.setJump(jump);
                            buy.setDataType(DataConstant.DATA_TYPE_CHECKSTAND);

                            ButtonDto gift = new ButtonDto();
                            gift.setTitle(buttonMap.get(ChannelConstants.VIDEO_SALE_BASEDATA_BUTTON_GIFT));
                            gift.setOpenType(DataConstant.OPENTYPE_H5);
                            gift.setBrowserType(DataConstant.BROWSER_TYPE_BUILTIN);
                            gift.setDataType(DataConstant.DATA_TYPE_BROWSER);
                            Browser browser = new Browser();
                            browser.setOpenType(DataConstant.BROWSER_OPENTYPE_NONE);
                            browser.setBrowserType(DataConstant.BROWSER_TYPE_BUILTIN);
                            browser.setUrl(videoSaleDto.getUrl());
                            Map<String, String> urlMap = new HashMap<String, String>();
                            urlMap.put("0", videoSaleDto.getUrl());
                            browser.setUrlMap(urlMap);
                            JumpData jump2 = new JumpData();
                            jump2.setValue(browser);
                            jump2.setType(DataConstant.DATA_TYPE_BROWSER);
                            gift.setJump(jump2);
                            videoSaleDto.setButtonBuy(buy);
                            videoSaleDto.setButtonGift(gift);
                        }
                    }
                    videoSaleDto.setDataList(dataList);
                    response.setData(videoSaleDto);
                } else {// 其他数据类型，老逻辑
                    BlockContentDto data = new BlockContentDto();
                    data.setName(tpResponse.getName());
                    if (null == dataType || !ChannelService.existPageType(dataType)) {
                        data.setTvPic(this.getBlockTvPicById(String.valueOf(tpResponse.getId())));
                    }
                    List<BaseData> dataList = new ArrayList<BaseData>();
                    data.setDataList(dataList);
                    List<CmsBlockContentTpResponse> contentList = tpResponse.getBlockContent();
                    for (CmsBlockContentTpResponse cmsBlockContentTpResponse : contentList) {
                        BaseData baseData = this.getDataFromCms(cmsBlockContentTpResponse, commonParam, null, null,
                                null, dataType);
                        if (baseData != null) {
                            if (baseData instanceof VideoSaleDto) {
                                continue;
                            }
                            dataList.add(baseData);
                        }
                    }
                    response.setData(data);
                }
            }
        }

        return response;

    }

    /**
     * 解析调用第三方接口的返回值专题包信息
     * @param tpResponse
     *            第三方接口返回值
     * @return
     */
    private SubjectDto parserTjPackageTpResponse(GetSubjectTpResponse tpResponse, Integer model, Integer broadcastId,
            String logPrefix, CommonParam commonParam) {
        SubjectDto subjectDto = null;
        SubjectDataTpResponse data = tpResponse.getData();
        if (data != null) {// 只有当调用第三方接口返回结果数据不为空时，才给客户端返回数据
            // 第一步：解析专题包信息
            subjectDto = new SubjectDto();
            subjectDto.setCid(data.getCid());
            subjectDto.setId(data.getId());
            subjectDto.setName(data.getName());
            subjectDto.setPubName(data.getPubName());
            Integer tvTemplate = null;// 包样式
            TemplateJson templateJson = data.getTemplateJson();
            if (templateJson != null) {
                String key = templateJson.getTvTemplate();
                if (StringUtils.isNotBlank(key)) {
                    tvTemplate = SubjectConstant.getSubjectTypeFromTemplate(key);
                    subjectDto.setSubjectType(tvTemplate);
                }
            }
            subjectDto.setTvPic(data.getTvPic());
            subjectDto.setCornerPic(data.getFocusPic());
            if (tvTemplate == null) {// 如果包样式为空，则不解析该视频包
                log.info(logPrefix + " parserTjPackageTpResponse " + data + " tvTemplate is null.");
            } else {
                List<SubjectPackageDto> packageList = new ArrayList<SubjectPackageDto>();
                List<String> packageIds = data.getPackageIds();
                if (packageIds != null && packageIds.size() > 0) {
                    Map<String, GetPackageDataTpResponse> responseMap = this.facadeTpDao.getCmsTpDao()
                            .getSubjectPackagesData(StringUtil.getListToString(packageIds, ","), commonParam);
                    if (null == responseMap || responseMap.size() == 0) {
                        return subjectDto;
                    }
                    GetPackageDataTpResponse packageDataResponse = null;
                    for (String packageId : packageIds) {
                        packageDataResponse = /*
                                               * this.facadeTpDao.getCmsTpDao()
                                               * .getSubjectPackageData(packageId
                                               * , commonParam);
                                               */
                        responseMap.get(packageId);
                        if (packageDataResponse != null) {
                            SubjectTjPackageTpResponse dataResponse = packageDataResponse.getData();
                            // 第二步：解析专题包中[视频包、专题包或者直播包]信息
                            SubjectPackageDto subjectPackageDto = this.parseSubjectPackageData(dataResponse,
                                    tvTemplate, model, logPrefix, commonParam);
                            if (subjectPackageDto != null && subjectPackageDto.getDataList() != null
                                    && subjectPackageDto.getDataList().size() > 0) {
                                packageList.add(subjectPackageDto);
                            }
                        }
                    }
                }
                subjectDto.setPackageList(packageList);
            }
        }
        return subjectDto;
    }

    /**
     * 解析专题包中的视频包、直播包、专辑包信息
     * @param subjectPackageData
     *            专题包数据
     * @param commonParam
     * @return
     */
    private SubjectPackageDto parseSubjectPackageData(SubjectTjPackageTpResponse subjectPackageData,
            Integer subjectType, Integer model, String logPrefix, CommonParam commonParam) {
        if (subjectPackageData == null) {
            return null;
        }
        Integer ptype = subjectPackageData.getPtype();// 视频包，直播包，专辑包类型
        // 如果不是该专题类型需要的包数据，则返回空
        if (!SubjectConstant.isContainPackageTypeBySubjectType(subjectType, ptype)) {
            return null;
        }
        SubjectPackageDto subjectPackageDto = new SubjectPackageDto();
        subjectPackageDto.setId(subjectPackageData.getId());
        subjectPackageDto.setName(subjectPackageData.getName());
        subjectPackageDto.setPorder(subjectPackageData.getPorder());
        subjectPackageDto.setPtype(ptype);
        subjectPackageDto.setTjId(subjectPackageData.getTjId());
        Map<String, String> jsonMap = subjectPackageData.getJsonMap();
        // 测试环境没有此字段，线上环境有此字段
        String packagePic = subjectPackageData.getPackagePic();
        if (StringUtils.isBlank(packagePic)) {
            if (jsonMap != null && jsonMap.size() > 0) {
                packagePic = jsonMap.get("packagePic");// 测试环境中此字段有值
            }
        }
        subjectPackageDto.setPackagePic(packagePic);

        List<SubjectTjPackageDataContent> list = subjectPackageData.getDataList();
        List<SubjectPackageDataDto> dataList = new ArrayList<SubjectPackageDataDto>();
        if (!CollectionUtils.isEmpty(list)) {
            for (SubjectTjPackageDataContent data : list) {
                // 第三步：解析[视频包、专辑包或者直播包]中各个视频信息
                SubjectPackageDataDto subjectPackageDataDto = this.parseSubjectData(data, ptype, subjectType,
                        logPrefix, commonParam);
                if (subjectPackageDataDto != null) {
                    dataList.add(subjectPackageDataDto);
                }
            }
        }
        // 儿童的多视频包专题包图片使用包内第一个视频的图片
        if (model != null && model == 1 && dataList != null && dataList.size() > 0 && ptype != null && ptype == 2) {
            subjectPackageDto.setPackagePic(dataList.get(0).getTvPic());
            subjectPackageDto.setDataListSize(dataList.size());
        }
        subjectPackageDto.setDataList(dataList);

        return subjectPackageDto;
    }

    /**
     * 解析专辑包、视频包、直播包中视频信息，加上TV版权过滤
     * @param data
     *            包数据
     * @param ptype
     *            包类型，1--专辑包，2--视频包，3--直播包
     * @param commonParam
     * @return
     */
    private SubjectPackageDataDto parseSubjectData(SubjectTjPackageDataContent data, Integer ptype,
            Integer subjectType, String logPrefix, CommonParam commonParam) {
        if (data == null || ptype == null) {
            return null;
        }
        String id = data.getId();// 获取包内视频ID|专辑ID|直播ID
        boolean hasCopyRight = false;// 是否有版权的标志位
        Integer dataType = null;// 定义此字段是为了根据ptype进行赋值，传给客户端
        String tvPic = null;// 不同的包取不同的图片
        // 当subjectType=5即时间轴专题时，标题取CMS中的标题字段值，副标题名称的值取数据库中的值
        String name = data.getNameCn();// 标题
        // String subTitle = data.getSubTitle();
        SubjectPackageDataDto subjectPackageDataDto = new SubjectPackageDataDto();
        if (ptype == 1) {// 专辑包
            // 专辑包过滤cibn专辑
            if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() == CommonConstants.CIBN
                    && StringUtil.isNotBlank(id)) {
                // AlbumMysqlTable albumMysql =
                // videoService.getAlbumById(StringUtil.toLong(id),
                // broadcastId);
                AlbumMysqlTable albumMysql = albumVideoAccess.getAlbum(StringUtil.toLong(id),
                        commonParam);
                if (albumMysql == null) {
                    return null;
                }
                if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                    subjectPackageDataDto.setChargeInfos(JumpUtil.genChargeInfos(albumMysql.getPay_platform()));
                }
            }
            Map<String, String> playPlatForm = (Map<String, String>) data.getPlayPlatform();
            if (playPlatForm != null && StringUtils.isNotBlank(playPlatForm.get(CommonConstants.TV_PLATFROM_CODE))) {
                hasCopyRight = true;
                dataType = DataConstant.DATA_TYPE_ALBUM;
                Map<String, String> picCollections = data.getPicCollections();
                if (picCollections != null && picCollections.size() > 0) {
                    tvPic = picCollections.get("300*400");// 专辑取300*400尺寸
                }
            } else {
                log.info(logPrefix + " album " + id + " has no TV copyright.");
            }
        } else if (ptype == 2) {// 视频包
            // 视频包过滤cibn视频
            if (commonParam.getBroadcastId() != null && commonParam.getBroadcastId() == CommonConstants.CIBN
                    && StringUtil.isNotBlank(id)) {
                // VideoMysqlTable videoMysql =
                // videoService.getVideoById(StringUtil.toLong(id),
                // commonParam.getBroadcastId());
                VideoMysqlTable videoMysql = albumVideoAccess.getVideo(StringUtil.toLong(id),
                        commonParam);
                if (videoMysql == null) {
                    return null;
                }
                if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                    subjectPackageDataDto.setChargeInfos(JumpUtil.genChargeInfos(videoMysql.getPay_platform()));
                }
            }
            Map<String, String> playPlatForm = (Map<String, String>) data.getPlayPlatform();
            if (playPlatForm != null && StringUtils.isNotBlank(playPlatForm.get(CommonConstants.TV_PLATFROM_CODE))) {
                hasCopyRight = true;
                dataType = DataConstant.DATA_TYPE_VIDEO;
                Map<String, String> picAll = data.getPicAll();
                if (picAll != null && picAll.size() > 0) {
                    if (subjectType == 5) {// 2015-07-30时间轴专题取16：9的图片
                        tvPic = picAll.get("400*225");
                    } else {
                        tvPic = picAll.get("400*300");// 视频取400*300尺寸
                    }
                }
            } else {
                log.info(logPrefix + " video " + id + " has no TV copyright.");
            }
        } else if (ptype == 3) {// 直播包
            hasCopyRight = true;
            dataType = DataConstant.DATA_TYPE_LIVE;
            id = data.getRid();// 直播包的中直播的ID取rid的值
            tvPic = data.getPic43();// 直播取pic43
            name = data.getRname();
        }
        if (!hasCopyRight) {
            return null;
        }

        String categoryId = null;
        Map<String, String> category = data.getCategory();
        if (category != null && category.size() > 0) {
            for (Entry<String, String> entry : category.entrySet()) {// 一般只有一个类型，默认取第一个值
                categoryId = entry.getKey();
                if (StringUtils.isNotBlank(categoryId)) {
                    // 如果分类ID不为空，才跳出循环
                    break;
                }
            }
        }
        subjectPackageDataDto.setCategoryId(categoryId);
        subjectPackageDataDto.setId(id);
        // 标题取得是
        subjectPackageDataDto.setName(name);
        // 副标题取的是标题的数据
        subjectPackageDataDto.setSubTitle(data.getSubTitle());
        subjectPackageDataDto.setDataType(dataType);
        subjectPackageDataDto.setTvPic(tvPic);
        return subjectPackageDataDto;
    }

    /**
     * 从排行榜数据库信息中解析出ChartsDto
     * @param chartsMysqlTable
     *            排行榜配置表
     * @param commonParam
     *            通用参数
     * @param parseChartsType
     *            解析操作类型，1--解析排行榜列表，获取简要信息；2--获取某一榜单详细信息
     * @return
     */
    private ChartsDto parseChartsDto(ChartsMysqlTable chartsMysqlTable, CommonParam commonParam, int parseChartsType) {
        ChartsDto charts = null;
        boolean isLetvUs = TerminalUtil.isLetvUs(commonParam);
        if (chartsMysqlTable != null) {
            if (isLetvUs && ChannelConstants.CHARTS_PARSE_CHARTS_TYPE_PREVIEW == parseChartsType) {
                // letv_us version need add cache because no static file.
                Object value = this.facadeCacheDao.getChannelCacheDao().getChartsDto(chartsMysqlTable.getId(),
                        commonParam.getLangcode());
                if (value != null) {
                    if (value instanceof ChartsDto) {
                        charts = (ChartsDto) value;
                    } else {
                        try {
                            charts = JsonUtil.parse(JsonUtil.parseToString(value), ChartsDto.class);
                        } catch (Exception e) {
                            log.error("parseChartsDto error:" + e.getMessage());
                        }
                    }
                }
            }

            if (charts == null) {
                // 加载排行榜配置信息
                charts = new ChartsDto();
                charts.setChannelId(charts.getChannelId());
                charts.setChartsId(chartsMysqlTable.getId());
                // 国际化频道名称
                String title = mutilLanguageService.getMessage(
                        ChannelConstants.ITV_CHARTS_TABLE, String.valueOf(chartsMysqlTable.getId()),
                        ChannelConstants.ITV_MENU_NAME, commonParam.getLangcode());
                if (StringUtil.isBlank(title)) {
                    charts.setTitle(chartsMysqlTable.getName());
                } else {
                    charts.setTitle(title);
                }
                charts.setChartsType(chartsMysqlTable.getType());
                charts.setChartsBgColor(chartsMysqlTable.getBg_color());
                charts.setChartsImg(chartsMysqlTable.getSmall_pic());
                charts.setChartsCornerColor(chartsMysqlTable.getCorner_color());
                // 加载排行榜剧集数据
                this.loadChartsData(chartsMysqlTable, charts, commonParam, parseChartsType);
                if (isLetvUs && ChannelConstants.CHARTS_PARSE_CHARTS_TYPE_PREVIEW == parseChartsType) {
                    this.facadeCacheDao.getChannelCacheDao().setChartsDto(chartsMysqlTable.getId(),
                            commonParam.getLangcode(), charts, CommonConstants.SECONDS_OF_1_HOUR);
                }
            }
        }
        return charts;
    }

    /**
     * 根据数据库信息，预加载排行榜信息，一般为加载排行榜前3的图片信息和数据总量
     * @param chartsMysqlTable
     *            一条排行榜数据库记录
     * @param charts
     *            需要填充数据的DTO
     * @param commonParam
     *            通用参数
     * @param parseChartsType
     *            解析操作类型，1--解析排行榜列表，获取简要信息；2--获取某一榜单详细信息
     */
    private void loadChartsData(ChartsMysqlTable chartsMysqlTable, ChartsDto charts, CommonParam commonParam,
            int parseChartsType) {
        Integer broadcastId = commonParam.getBroadcastId();
        String logPrefix = "loadChartsData_" + charts.getChannelId() + "_" + charts.getChartsId() + "_"
                + charts.getChartsType() + "_" + parseChartsType;
        /*
         * 针对每一个排行榜，总的步骤:
         * 1.获取专辑id获取视频id列表；
         * 2.针对每一个专辑或视频id，获取对应的mysqlTable，填充专辑或视频信息
         * 3.针对每一个专辑或视频id，获取对应的播放排行统计信息，填充播放排行数据
         * 2015-10-04添加音乐专题排行榜，榜单元素为专题，需要注意图片仍需选取3:4的竖图
         */

        // 如果新片推荐中，编辑的全是正片，则本处就不用解析cmsBlockContentTpResponse了
        // 根据数据来源不同，预读数据
        // Integer dataPreloadtype = chartsMysqlTable.getData_preloadtype();
        Integer chartsType = chartsMysqlTable.getType();
        Integer dataSource = chartsMysqlTable.getData_source();
        List<BaseData> dataList = null;
        List<String> rankListImgs = null;

        if (dataSource != null && chartsType != null) {
            if (ChannelConstants.CHARTS_TYPE_FRESH_RECOMMENDATION == chartsType
                    || ChannelConstants.CHARTS_TYPE_MUSIC_RANK == chartsType) {
                charts.setChartsName(charts.getTitle() + "  ");
            } else {
                charts.setChartsName(charts.getTitle() + "  TOP ");
            }
            /**
             * 根据排行榜配置表dataSource区分，新片推荐、音乐榜数据来自Cms；其他榜单数据来自数据库
             */
            switch (dataSource.intValue()) {
            case ChannelConstants.CHARTS_DATASOURCE_CMS:
                // 如果是从CMS读取数据，解析视频列表，目前就认为是新片推荐，特殊处理；后面可以提取出通用处理
                String url = chartsMysqlTable.getData_url();
                // CMS板块数据
                CmsBlockTpResponse cmsBlockTpResponse = this.facadeTpDao.getCmsTpDao().getCmsBlockNew(url, commonParam);
                dataList = new LinkedList<BaseData>();
                rankListImgs = new ArrayList<String>();
                // 图片
                int count = 0;
                if (cmsBlockTpResponse != null) {
                    // A1.获取专辑id获取视频id列表；
                    List<CmsBlockContentTpResponse> cmsBlockContentTpResponseList = cmsBlockTpResponse
                            .getBlockContent();
                    ChartsDataDto chartsData = null;

                    switch (chartsType.intValue()) {
                    case ChannelConstants.CHARTS_TYPE_FRESH_RECOMMENDATION:
                        // 新片推荐，只取专辑
                        AlbumMysqlTable albumMysqlTable = null;
                        count = 0;

                        for (CmsBlockContentTpResponse cmsBlockContentTpResponse : cmsBlockContentTpResponseList) {
                            String pushFlag = cmsBlockContentTpResponse.getPushflag();
                            String cmsBlockContent = cmsBlockContentTpResponse.getContent();
                            Integer cmsBlockContentType = cmsBlockContentTpResponse.getType();

                            if ((pushFlag == null) || !pushFlag.contains(CommonConstants.TV_PLATFROM_CODE)) {
                                // 推送平台必须有TV
                                log.info(logPrefix + "_" + cmsBlockTpResponse.getId() + "_" + cmsBlockContent + "_"
                                        + cmsBlockContentTpResponse.getTitle() + ": pushflag no TV.");
                                continue;
                            }

                            // 如果新片推荐中，编辑的全是正片，则本处就不用解析cmsBlockContentTpResponse了
                            // A2.针对每一个专辑或视频id，获取对应的mysqlTable，填充专辑或视频信息
                            if (cmsBlockContentType != null
                                    && CmsTpConstant.CMS_BLOCKCONTENT_TYPE_ALBUM == cmsBlockContentType
                                    && StringUtils.isNotEmpty(cmsBlockContent)) {
                                try {
                                    albumMysqlTable = albumVideoAccess.getAlbum(
                                            Long.parseLong(cmsBlockContent), commonParam);
                                } catch (Exception e) {
                                }
                            }

                            if (albumMysqlTable != null
                                    && VideoCommonUtil.isPositive(2, albumMysqlTable.getCategory(),
                                            albumMysqlTable.getAlbum_type(), null)) {
                                count++;

                                // 只选取正片，2015-10-05修改，新片推荐榜中，专辑不区分频道，故只要albumMysqlTable只要有数据，就一定能解析出来
                                String img300_400 = albumMysqlTable.getPic(300, 400);
                                String img600_800 = albumMysqlTable.getPic(600, 800);

                                if (ChannelConstants.CHARTS_PARSE_CHARTS_TYPE_PREVIEW == parseChartsType) {
                                    // 排行榜图片预加载4张，第一张大图600*800；后3张优先取300*400的图，次选600*800
                                    if (count == 1) {
                                        rankListImgs.add(StringUtils.isNotEmpty(img600_800) ? img600_800 : img300_400);
                                    } else if (count < 5) {
                                        rankListImgs.add(StringUtils.isNotEmpty(img300_400) ? img300_400 : img600_800);
                                    }
                                } else {
                                    chartsData = this.buildChartsDataByAlbum(albumMysqlTable, null, logPrefix + "_"
                                            + cmsBlockContent, commonParam);

                                    if (chartsData != null) {
                                        // 取3:4的图，优先取600*800的图，次选300*400
                                        chartsData.setImg(StringUtils.isNotEmpty(img600_800) ? img600_800 : img300_400);

                                        // 设置TV端上映时间，策略为，获取有统计记录至今的时间间隔，未统计的则认为是上映第1天
                                        int publishTime = 0; // TV上映时长，值等于publishDruation+1，默认未上映
                                        int publishDruation = -1; // TV上映时间间隔

                                        // 优先取专辑表中的第一集正片上映时间，如果不存在则取当前视频第一次统计排行的时间
                                        String tvFirstOnTime = StringUtils.trimToNull(albumMysqlTable
                                                .getTvFirstOnTime());
                                        Calendar tvFirstOnTimeCalendar = TimeUtil.parseCalendar(tvFirstOnTime,
                                                TimeUtil.SIMPLE_DATE_FORMAT);
                                        Calendar tvFirstOnStatisticsTime = this
                                                .getTvFirstOnStatisticsTime(tvFirstOnTimeCalendar);
                                        Calendar currentStatisticsTime = TimeUtil.truncateDay(Calendar.getInstance());
                                        if (tvFirstOnStatisticsTime != null && currentStatisticsTime != null) {
                                            publishDruation = TimeUtil.daysBetween(tvFirstOnStatisticsTime,
                                                    currentStatisticsTime);
                                            log.info(logPrefix + "_" + cmsBlockTpResponse.getId() + "_"
                                                    + cmsBlockContent + "_" + chartsData.getName() + "_publishTime_"
                                                    + tvFirstOnTime + "_" + (publishDruation + 1)
                                                    + ": get publishDruation from tvFirstOnTime.");
                                        } else {
                                            Integer publishDateShort = this.facadeMysqlDao.getStatRankWeekDataDao()
                                                    .getFirstStatisticsDateByAlbumId(cmsBlockContent);
                                            Date publishDate = TimeUtil.parseDate(String.valueOf(publishDateShort),
                                                    TimeUtil.SHORT_DATE_FORMAT_NO_DASH);

                                            if (publishDate != null) {
                                                publishDruation = (int) ((System.currentTimeMillis() - publishDate
                                                        .getTime()) / (CommonConstants.SECONDS_OF_1_DAY * 1000));

                                                log.info(logPrefix + "_" + cmsBlockTpResponse.getId() + "_"
                                                        + cmsBlockContent + "_" + chartsData.getName()
                                                        + "_publishTime_" + publishDateShort + "_"
                                                        + (publishDruation + 1)
                                                        + ": get publishDruation from first Statistics Date.");
                                            } else {
                                                // 2015-10-05新加逻辑，如果没有查到统计日期，则有可能：1--很老的片子，在排行榜需求之前推出；2.新片，今天刚推，还没有统计数据
                                                Date createTime = albumMysqlTable.getCreate_time();
                                                if (createTime != null) {
                                                    int createDruation = (int) ((System.currentTimeMillis() - createTime
                                                            .getTime()) / (CommonConstants.SECONDS_OF_1_DAY * 1000));
                                                    if (createDruation <= 31) {
                                                        // 近一个月内创建的专辑，默认刚推到TV端
                                                        publishDruation = 0;
                                                    }

                                                    log.info(logPrefix + "_" + cmsBlockTpResponse.getId() + "_"
                                                            + cmsBlockContent + "_" + chartsData.getName()
                                                            + "_publishTime_" + createTime.getTime() + "_"
                                                            + (publishDruation + 1)
                                                            + ": get publishDruation from create time.");
                                                } else {
                                                    log.info(logPrefix + "_" + cmsBlockTpResponse.getId() + "_"
                                                            + cmsBlockContent + "_" + chartsData.getName()
                                                            + "_publishTime_"
                                                            + ": can't get publishDruation from create time.");
                                                }
                                            }
                                        }

                                        if (publishDruation >= 0) {
                                            publishTime = publishDruation + 1;
                                        } else {
                                            log.info(logPrefix + "_" + cmsBlockTpResponse.getId() + "_"
                                                    + cmsBlockContent + "_" + chartsData.getName()
                                                    + "_publishTime_publishDruation_" + publishDruation
                                                    + ": illegal album time data.");
                                        }

                                        if (publishTime > 0 && publishTime <= 3) {
                                            chartsData.setPublishTime(publishTime);
                                        }

                                        chartsData.setRankTitle("No." + count);

                                        dataList.add(chartsData);
                                    }
                                }
                            } else {
                                log.info(logPrefix + "_" + cmsBlockContent
                                        + ",albumMysqlTable is null or not positive.");
                            }
                        }
                        break;
                    case ChannelConstants.CHARTS_TYPE_MUSIC_RANK:
                        // 音乐排行榜，只取专题
                        Subject subject = null;
                        count = 0;

                        for (CmsBlockContentTpResponse cmsBlockContentTpResponse : cmsBlockContentTpResponseList) {
                            String pushFlag = cmsBlockContentTpResponse.getPushflag();
                            String cmsBlockContent = cmsBlockContentTpResponse.getContent();

                            if ((pushFlag == null) || !pushFlag.contains(CommonConstants.TV_PLATFROM_CODE)) {
                                // 推送平台必须有TV
                                log.info(logPrefix + "_" + cmsBlockContent + "_" + cmsBlockContentTpResponse.getTitle()
                                        + ": pushflag no TV.");
                                continue;
                            }
                            subject = this.getCmsDataOfSubject(cmsBlockContentTpResponse, null, null, commonParam);

                            if (subject != null) {
                                // subject不为空才说明数据有效
                                count++;

                                String img = cmsBlockContentTpResponse.getPic1();
                                if (StringUtils.isBlank(img)) {
                                    log.info(logPrefix + "_" + cmsBlockContent + "_"
                                            + cmsBlockContentTpResponse.getType() + ": no pic1.");
                                    img = cmsBlockContentTpResponse.getTvPic();
                                }

                                if (ChannelConstants.CHARTS_PARSE_CHARTS_TYPE_PREVIEW == parseChartsType) {
                                    if (count < 5) {
                                        rankListImgs.add(img);
                                    }
                                } else {
                                    // 只加载数据不加图片
                                    chartsData = this.buildChartsDataBySubject(subject);
                                    chartsData.setImg(img);
                                    chartsData.setDescription(cmsBlockContentTpResponse.getShorDesc());
                                    chartsData.setRankTitle("No." + count);

                                    dataList.add(chartsData);
                                }
                            } else {
                                log.info(logPrefix + "_" + cmsBlockContent + "_" + cmsBlockContentTpResponse.getType()
                                        + ": parse cms content to subject failed.");
                            }
                        }
                        break;
                    default:
                        break;
                    }
                } else {
                    log.info(logPrefix + "_" + url + ": get cms block data failed.");
                }

                if (ChannelConstants.CHARTS_PARSE_CHARTS_TYPE_PREVIEW == parseChartsType) {
                    // 排行榜tab页，不需要数据列表但需要4张预览图
                    charts.setRankListImgs(rankListImgs);
                    if (CollectionUtils.isEmpty(rankListImgs)) {
                        log.info(logPrefix + "_" + url + ": parse empty data.");
                    }
                } else {
                    // 排行榜详情页，需要数据列表而不需要4张预览图
                    charts.setDataList(dataList);
                    if (CollectionUtils.isEmpty(dataList)) {
                        log.info(logPrefix + "_" + url + ": parse empty data.");
                    }
                }

                if (!(ChannelConstants.CHARTS_PARSE_CHARTS_TYPE_DETAIL == parseChartsType && ChannelConstants.CHARTS_TYPE_MUSIC_RANK == chartsType)) {
                    // 具体榜单中，音乐排行榜不展示top xxx，不设值；其他都需要
                    charts.setContentAmount(count);
                }

                break;
            case ChannelConstants.CHARTS_DATASOURCE_DATABASE:
                // 如果是从数据库读取，则根据排行榜类型，分别从播放统计数据表中读取不同信息，需要确定不同榜单的预加载数据量大小
                // 目前仅飙升榜、热播榜从数据库中读取数据
                Integer dataPreloadsize = chartsMysqlTable.getData_preloadsize();
                Integer requiredCategoryId = chartsMysqlTable.getCategory_id();
                List<StatRankWeekData> statRankWeekDataList = null;
                dataList = new ArrayList<BaseData>();
                rankListImgs = new ArrayList<String>();
                // 先读换粗，缓存没有再查库
                String statDate = this.facadeCacheDao.getChannelCacheDao().getChartsStartDate();
                if (StringUtil.isBlank(statDate)) {
                    statDate = this.facadeMysqlDao.getStatRankWeekDataDao().getLatestStatisticsDate();
                    if (StringUtil.isNotBlank(statDate)) {
                        this.facadeCacheDao.getChannelCacheDao().setChartsStartDate(statDate,
                                CommonConstants.SECONDS_OF_1_HOUR);
                    }
                }
                // B1.获取专辑id获取视频id列表；
                switch (chartsType.intValue()) {
                case ChannelConstants.CHARTS_TYPE_SOARING:
                    // 飙升榜
                    String previousStatData = TimeUtil
                            .getDateFromDate(statDate, -7, TimeUtil.SHORT_DATE_FORMAT_NO_DASH);
                    // 参数用来确定不是14天之内上映的影片
                    String tPreviousStatData = TimeUtil.getDateFromDate(statDate, -14,
                            TimeUtil.SHORT_DATE_FORMAT_NO_DASH);
                    if (StringUtils.isNotEmpty(statDate) && StringUtils.isNotEmpty(previousStatData)
                            && StringUtils.isNotEmpty(tPreviousStatData)) {
                        statRankWeekDataList = this.facadeMysqlDao.getStatRankWeekDataDao().listSoaring(statDate,
                                previousStatData, tPreviousStatData, 0, 30);
                    }
                    break;
                case ChannelConstants.CHARTS_TYPE_FILM:
                case ChannelConstants.CHARTS_TYPE_TV:
                case ChannelConstants.CHARTS_TYPE_VARIETY:
                case ChannelConstants.CHARTS_TYPE_CARTOON:
                    // 热播榜
                    if (StringUtils.isNotEmpty(statDate)) {
                        statRankWeekDataList = this.facadeMysqlDao.getStatRankWeekDataDao().listByStatDateAndType(
                                statDate, String.valueOf(requiredCategoryId), 0, 30);
                    }
                    break;
                default:
                    break;
                }
                Integer maxRank = 0;// 所有片子的飙升数最大值
                if (!CollectionUtils.isEmpty(statRankWeekDataList)) {
                    // A2.针对每一个专辑或视频id，获取对应的mysqlTable，填充专辑或视频信息

                    count = 0;
                    ChartsDataDto chartsData = null;
                    AlbumMysqlTable albumMysqlTable = null;
                    for (StatRankWeekData rankData : statRankWeekDataList) {
                        Integer rank = rankData.getRank();
                        if (rank == null || rank < 1) {// 只要排名上升的
                            continue;
                        }
                        String pid = rankData.getPid();
                        if (StringUtils.isNotEmpty(pid)) {
                            try {
                                albumMysqlTable = albumVideoAccess.getAlbum(
                                        Long.parseLong(pid), commonParam);
                            } catch (Exception e) {
                            }
                        }

                        if (albumMysqlTable != null
                                && VideoCommonUtil.isPositive(2, albumMysqlTable.getCategory(),
                                        albumMysqlTable.getAlbum_type(), null)) {
                            // 只选取正片

                            chartsData = this.buildChartsDataByAlbum(albumMysqlTable, requiredCategoryId, logPrefix
                                    + "_" + pid, commonParam);

                            if (chartsData != null) {
                                Map<String, String> images = new HashMap<String, String>();
                                if (albumMysqlTable.getPic(1440, 810) != null) {
                                    images.put("1440*810", albumMysqlTable.getPic(1440, 810));
                                }
                                if (albumMysqlTable.getPic(400, 300) != null) {
                                    images.put("400*300", albumMysqlTable.getPic(400, 300));
                                }
                                if (albumMysqlTable.getPic(960, 640) != null) {
                                    images.put("960*640", albumMysqlTable.getPic(960, 640));
                                }
                                chartsData.setImgs(images);

                                String img300_400 = albumMysqlTable.getPic(300, 400);
                                String img600_800 = albumMysqlTable.getPic(600, 800);
                                // 取3:4的图，优先取600*800的图，次选300*400
                                chartsData.setImg(StringUtils.isNotEmpty(img600_800) ? img600_800 : img300_400);
                                // 排行榜图片预加载4张，第一张取600*800；后3张优先取300*400的图，次选600*800
                                if (count == 0) {
                                    rankListImgs.add(StringUtils.isNotEmpty(img600_800) ? img600_800 : img300_400);
                                } else if (count < 4) {
                                    rankListImgs.add(StringUtils.isNotEmpty(img300_400) ? img300_400 : img600_800);
                                }

                                if (ChannelConstants.CHARTS_TYPE_SOARING == chartsType) {
                                    // 飙升榜要显示排名提升
                                    chartsData.setMarkLabel("排名提升");
                                    chartsData.setMarkContent(String.valueOf(rankData.getRank()));
                                }

                                // 非新片推荐榜要设置播放量
                                chartsData.setVvLabel("一周播放量 ");

                                String vvContent = null;
                                Long vv = rankData.getVv();
                                if (vv != null) {
                                    if (vv.longValue() > 10000) {
                                        vvContent = String.format("%.1f", vv.longValue()
                                                * ChannelConstants.CHANNEL_CHARTS_TYPE_SOARING_VV_COEFFICIENT
                                                / 10000.0f)
                                                + "万";
                                    } else {
                                        vvContent = String.valueOf(vv);
                                    }
                                }
                                chartsData.setVvContent(vvContent);

                                count++;
                                chartsData.setRankTitle("No." + count);
                                if (rank > maxRank) {
                                    maxRank = rank;
                                }
                                dataList.add(chartsData);
                            } else {
                                log.info(logPrefix + "_" + pid + ": illegal charts data from album");
                            }
                        } else {
                            log.info(logPrefix + "_" + pid + ",albumMysqlTable is null or not positive film");
                        }

                        if (dataList.size() >= dataPreloadsize) {
                            break;
                        }
                    }
                }
                if (ChannelConstants.CHARTS_PARSE_CHARTS_TYPE_PREVIEW == parseChartsType) {
                    // 如果不需要填充数据，说明是排行榜tab页，不需要数据列表但需要3张预览图
                    charts.setRankListImgs(rankListImgs);
                } else {
                    // 如果需要填充数据，说明是排行榜详情页，需要数据列表而不需要3张预览图
                    int maxRankUp = maxRank + 20;
                    for (BaseData baseData : dataList) {
                        ChartsDataDto data = (ChartsDataDto) baseData;
                        String markContent = data.getMarkContent();
                        if (StringUtils.isNotBlank(markContent)) {
                            /*
                             * 将所有飙升数压缩到0~79之间，然后加上基数20，排名整体加20，
                             * 之后如果最大值超过100则收缩到99
                             * ~91之间
                             */
                            Integer finalRank = Integer.parseInt(markContent) + 20;
                            if (maxRankUp >= 100 && finalRank > 90) {
                                finalRank = (finalRank) * 9 / maxRankUp + 90;
                            }
                            markContent = String.valueOf(finalRank);
                        }
                        data.setMarkContent(markContent);
                    }
                    charts.setDataList(dataList);
                }
                // 如果新片推荐中，编辑的全是正片，则本处就不用解析cmsBlockContentTpResponse了
                charts.setContentAmount(dataList.size());
                break;
            default:
                break;
            }
        }
    }

    /**
     * 清空缓存
     * @return
     */
    private boolean forClearCache() {
        long l = System.currentTimeMillis();
        long timeLag = l - LAST_CACHETIME;
        if (timeLag > CACHE_TIMEOUT) {
            LAST_CACHETIME = l;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 加载推荐板块和频道数据对应关系
     * @param blockKey
     * @param isFlush
     * @return
     */
    private ChannelData getChannelDataPlusFromBlockKey(String blockKey, Integer isFlush) {
        if (channelDataRecommendationBlockMap != null && !this.forClearCache() && (null == isFlush || isFlush == 0)) {
            ChannelData channelData = channelDataRecommendationBlockMap.get(blockKey);
            if (channelData != null) {
                return channelData;
            } else {
                // log.info("getChannelDataPlusFromBlockKey: " + blockKey);
            }
        } else {
            this.initCacheData();
        }
        return channelDataRecommendationBlockMap.get(blockKey);
    }

    private void initCacheData() {
        Map<String, ChannelData> mapTmp = new HashMap<String, ChannelData>();
        List<ChannelDataRecommendationBlockMysqlTable> channelDataRecommendationBlockMysqlTableList = this.facadeMysqlDao
                .getChannelDataRecommendationBlockMysqlDao().getList();
        for (ChannelDataRecommendationBlockMysqlTable channelDataRecommendationBlockMysqlTable : channelDataRecommendationBlockMysqlTableList) {
            String rcBlockKey = channelDataRecommendationBlockMysqlTable.getRc_blockkey();
            String title = channelDataRecommendationBlockMysqlTable.getTitle();
            Integer titleDataType = channelDataRecommendationBlockMysqlTable.getTitle_dataType();
            String titleBgColor = channelDataRecommendationBlockMysqlTable.getTitle_bgcolor();
            Integer titleChannelId = channelDataRecommendationBlockMysqlTable.getTitle_channelid();
            String titleSearchCondtion = channelDataRecommendationBlockMysqlTable.getTitle_searchcondition();
            Integer titleAlbumId = channelDataRecommendationBlockMysqlTable.getTitle_albumid();

            ChannelData channelData = new ChannelData();
            channelData.setTitle(title);
            channelData.setTitleDataType(titleDataType);
            channelData.setTitleBgColor(titleBgColor);
            channelData.setTitleChannelId(titleChannelId);
            channelData.setTitleSearchCondition(titleSearchCondtion);
            channelData.setTitleAlbumId(titleAlbumId);
            channelData.setConfigInfo(channelDataRecommendationBlockMysqlTable.getConfig_info());
            mapTmp.put(rcBlockKey, channelData);
        }
        channelDataRecommendationBlockMap = mapTmp;
    }

    /**
     * 加载CMS频道和频道对应关系
     * @param cmsChannelId
     * @return
     */
    private Integer getChannelIdFromCmsChannelId(String cmsChannelId) {

        List<ChannelCmsChannelMysqlTable> channelCmsChannelMysqlTableList = this.facadeMysqlDao
                .getChannelCmsChannelMysqlDao().getList();
        for (ChannelCmsChannelMysqlTable channelCmsChannelMysqlTab : channelCmsChannelMysqlTableList) {
            Integer value = channelCmsChannelMysqlTab.getChannel_id();
            String key = String.valueOf(channelCmsChannelMysqlTab.getCms_channelid());
            if (cmsChannelId != null && cmsChannelId.equals(key)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 根据专辑表，构建基本的ChartsDataDto信息；
     * 构建原则为，ChartsDataDto的name和subName不能为空，且专辑分类必须是电影、电视剧、综艺或动漫其中之一；
     * 否则返回null
     * @param albumMysqlTable
     * @param requiredCategoryId
     *            需要过滤的内容分类id，如果不为null或0则进行过滤
     * @return
     */
    private ChartsDataDto buildChartsDataByAlbum(AlbumMysqlTable albumMysqlTable, Integer requiredCategoryId,
            String logPrefix, CommonParam commonParam) {
        ChartsDataDto chartsData = null;

        // 只选取正片
        String chartsName = albumMysqlTable.getName_cn();
        String subName = null;
        Integer categatryId = albumMysqlTable.getCategory();

        // 针对category的过滤
        if (requiredCategoryId != null && 0 != requiredCategoryId
                && (categatryId == null || requiredCategoryId.intValue() != categatryId)) {
            // 如果需要过滤，但又不符合要求，则直接返回
            return chartsData;
        }

        // for tvod filter
        Integer chargeType = JumpUtil.getChargeType(albumMysqlTable.getPay_platform(), commonParam);
        if (null == chargeType) {
            chargeType = JumpUtil.getChargeType(albumMysqlTable.getPay_platform());
        }
        if (chargeType != null && chargeType == ChargeTypeConstants.chargePolicy.CHARGE_BY_VIP) {
            return chartsData;
        }

        Integer isEnd = albumMysqlTable.getIs_end();
        if (isEnd != null) {
            Integer follownu = albumMysqlTable.getFollownum();
            if (isEnd == 0 && follownu != null && categatryId != null) {
                if (Category.TV == categatryId || Category.CARTOON == categatryId) {
                    subName = "更新至" + follownu + "集";
                } else if (Category.VARIETY == categatryId) {
                    subName = "更新至" + VideoUtil.getFollowNum(follownu, "yyyy", "yyyy-MM-dd", "MM-dd") + "期";
                } else {
                    subName = albumMysqlTable.getSub_title();
                }
            } else {
                // 已完结
                subName = albumMysqlTable.getSub_title();
            }
        }

        if (StringUtils.isNotBlank(chartsName) && StringUtils.isNotBlank(subName)
                && ChannelConstants.isLegalChartsDataCategary(categatryId)) {
            // name和subName不能为空，且专辑分类必须是电影、电视剧、综艺或动漫其中之一，否则过滤之

            chartsData = new ChartsDataDto();
            chartsData.setAlbumId(String.valueOf(albumMysqlTable.getId()));
            chartsData.setName(chartsName);
            chartsData.setSubName(subName);

            chartsData.setCategoryId(categatryId);
            chartsData.setChargeType(albumMysqlTable.isPay(commonParam.getP_devType()) ? 1 : 0);
            // for tvod icon type
            chartsData.setIconType(JumpUtil.getIconType(null, chartsData.getChargeType() + "", chargeType, null, null));

            if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                chartsData.setChargeInfos(JumpUtil.genChargeInfos(albumMysqlTable.getPay_platform()));
            }

            String subCategoryName = albumMysqlTable.getSub_category_name();
            subCategoryName = StringUtils.isNotEmpty(subCategoryName) ? subCategoryName.replaceAll(",", "/").trim()
                    : "未知";
            chartsData.setContentCategory("类型：" + subCategoryName);

            String areaName = albumMysqlTable.getArea_name();
            areaName = StringUtils.isNotEmpty(areaName) ? areaName.replaceAll(",", "/").trim() : "未知";
            chartsData.setAreaContent("国家（地区）：" + areaName);

            String performers = null;
            if (categatryId != null) {
                if (Category.TV == categatryId || Category.FILM == categatryId) {
                    // 电影电视剧优先取演员表，次选导演
                    String performersPrefix = "主演：";
                    String actors = StringUtils.isEmpty(albumMysqlTable.getActor()) ? albumMysqlTable.getStarring()
                            : albumMysqlTable.getActor();
                    actors = StringUtils.isEmpty(actors) ? actors : actors.replaceAll(",", "/").trim();

                    // 有演员则显示演员，无演员就显示导演；若也无导演就显示演员：未知
                    if (StringUtils.isBlank(actors)) {
                        String director = albumMysqlTable.getDirectory();
                        director = StringUtils.isEmpty(director) ? director : director.replaceAll(",", "/").trim();
                        if (StringUtils.isBlank(director)) {
                            performers = "未知";
                        } else {
                            performersPrefix = "导演：";
                            performers = director;
                        }
                    } else {
                        performers = actors;
                    }

                    performers = performersPrefix + performers;
                } else if (Category.CARTOON == categatryId) {
                    // 动漫取适用年龄
                    performers = StringUtils.isNotEmpty(albumMysqlTable.getFit_age()) ? albumMysqlTable.getFit_age()
                            .replaceAll(",", " ").trim() : "未知";
                    performers = "适用年龄：" + performers;
                } else if (Category.VARIETY == categatryId) {
                    String performersPrefix = "主持人：";
                    String compere = albumMysqlTable.getCompere();
                    compere = StringUtils.isEmpty(compere) ? compere : compere.replaceAll(",", "/").trim();

                    // 有主持人则显示主持人，无主持人就显示播出电视台；若也无播出电视台就显示主持人：未知
                    if (StringUtils.isBlank(compere)) {
                        String playTv = albumMysqlTable.getPlay_tv_name();
                        playTv = StringUtils.isEmpty(playTv) ? playTv : playTv.replaceAll(",", "/").trim();
                        if (StringUtils.isBlank(playTv)) {
                            performers = "未知";
                        } else {
                            performersPrefix = "播出电视台：";
                            performers = playTv;
                        }
                    } else {
                        performers = compere;
                    }

                    performers = performersPrefix + performers;
                }
            }
            chartsData.setPerformers(performers);
            chartsData.setDataType(DataConstant.DATA_TYPE_ALBUM);
        } else {
            log.info(logPrefix + "cat't generate chart data for [chartsName-" + chartsName + ", subName-" + subName
                    + ", requiredCategoryId-" + requiredCategoryId + ", categatryId-" + categatryId + "]");
        }

        return chartsData;
    }

    /**
     * 视频营销配置缓存数据
     * 策略是，
     * 1.先读缓存；
     * 2.读到则返回；
     * 3.如果读不到，则读配置文件；
     * 4.如果不为空，则更新缓存和内存；
     * 5.如果为空，则直接使用内存
     * @return
     */
    public Map<String, String> getVideoSaleConfigs() {
        String logPrefix = "getVideoSaleConfigs_";
        Map<String, String> map = null;
        this.updateVideoSaleConfig();
        map = ChannelConstants.VIDEO_SALE_CONFIG_MAP;
        if (map == null) {
            map = this.facadeCacheDao.getChannelCacheDao().getVideoSaleConfig();
            log.info(logPrefix + ": get button configs from redis");
        }
        return map;
    }

    /**
     * 根据板块id，获取其配置的背景图，先从缓存中获取，如果缓存失效，再从配置文件中获取
     * @param blockId
     * @return
     */
    private String getBlockTvPicById(String blockId) {
        String blockTvPic = null;

        blockId = StringUtils.trimToNull(blockId);
        if (blockId != null) {
            blockTvPic = this.facadeCacheDao.getChannelCacheDao().getBlockTvPic(blockId);
            if (StringUtils.trimToNull(blockTvPic) == null) {
                blockTvPic = ApplicationUtils.get(ApplicationConstants.CHANNEL_BOLCK_TVPIC_PREFIX + blockId);
                // 从配置文件读到之后更新缓存，虽然配置文件能读到的不一定是最新的，但还是要更新缓存，为了避免每次都读缓存-读配置的操作；发现缓存失效，更新缓存就行
                this.facadeCacheDao.getChannelCacheDao().setBlockTvPic(blockId, blockTvPic);
            }
        }

        return blockTvPic;
    }

    /**
     * 判断一个视频或者专辑是否TV版权
     * @param playPlatform
     * @return
     */
    private boolean hasTvCopyright(Map<String, String> playPlatform) {
        if (CollectionUtils.isEmpty(playPlatform)) {
            return false;
        }
        if (playPlatform.keySet().contains(CommonConstants.TV_PLATFROM_CODE)) {
            return true;
        }
        return false;
    }

    /**
     * 定向解析，从cms板块内容数据中解析出专题信息，包含普通专题和超前院线专题
     * @param cmsBlockContentTpResponse
     * @return
     */
    private Subject getCmsDataOfSubject(CmsBlockContentTpResponse cmsBlockContentTpResponse, String defaultStream,
                                        String defaultStreamName, CommonParam commonParam) {
        String logPrefix = "getCmsDataOfSubject_" + commonParam.getMac();
        Integer subjectType = null; // 专题模板
        String subjectId = cmsBlockContentTpResponse.getContent();
        Integer categoryId = null;
        String subCategoryId = null;

        // 2015-07-15 修改为从老接口中获取专题模板信息
        SubjectDataTpResponse subjectInfo = cmsBlockContentTpResponse.getKztInfo();
        if (StringUtil.isNotBlank(subjectId)
                && (subjectInfo == null || (StringUtil.isNotBlank(commonParam.getTerminalApplication()) && TerminalCommonConstant.TERMINAL_APPLICATION_CMS_LE
                        .contains(commonParam.getTerminalApplication())))) {
            GetSubjectTpResponse tpResponse = this.facadeTpDao.getCmsTpDao().getCmsSubjectById(subjectId, commonParam);
            if (tpResponse != null && tpResponse.getCode() == 200 && tpResponse.getData() != null) {
                subjectInfo = tpResponse.getData();
            }
        }
        if (subjectInfo != null) {// 因为只有专题包才返回此部分内容
            // 获取返回的模板信息
            SubjectDataTpResponse.TemplateJson templateJson = subjectInfo.getTemplateJson();
            if (templateJson != null) {
                String key = templateJson.getTvTemplate();
                if (StringUtils.isNotBlank(key)) {
                    // 根据模板和subjectType的转换规则获取subjectType的值
                    subjectType = SubjectConstant.getSubjectTypeFromTemplate(key);
                } else {
                    log.info(logPrefix + "_" + subjectId + " can't get template from subject info.");
                }
            } else {
                log.info(logPrefix + "_" + subjectId + " templateJson is null.");
            }
            if (subjectInfo.getCid() != null) {
                categoryId = Integer.valueOf(subjectInfo.getCid());
            }
            subCategoryId = String.valueOf(subjectInfo.getSubcategory());
        } else {
            log.info(logPrefix + "_" + subjectId + " subject info is null");
        }
        if (subjectType == null) {
            // 兼容老版本
            subjectType = this.getSubjectType(cmsBlockContentTpResponse, subjectType);
        }
        Subject subject = null;
        if (subjectType != null) {
            subject = new Subject();
            subject.setSubjectId(subjectId);
            subject.setName(cmsBlockContentTpResponse.getTitle());
            subject.setSubName(cmsBlockContentTpResponse.getSubTitle());
            subject.setShortDesc(cmsBlockContentTpResponse.getShorDesc());
            subject.setImg(cmsBlockContentTpResponse.getTvPic());
            subject.setPic1(cmsBlockContentTpResponse.getPic1());
            subject.setCategoryId(categoryId);
            subject.setSubCategoryId(subCategoryId);
            subject.setSubjectType(subjectType);
            JumpUtil.bulidJumpObj(subject, DataConstant.DATA_TYPE_SUBJECT, "", "", commonParam);
        } else {
            log.info(logPrefix + "_" + subjectId + " can't get final template from subject info.");
        }

        return subject;
    }

    private Integer getSubjectType(CmsBlockContentTpResponse cmsBlockContentTpResponse, Integer subjectType) {
        if (cmsBlockContentTpResponse != null) {
            if (cmsBlockContentTpResponse.getSkipType() != null && cmsBlockContentTpResponse.getSkipUrl() != null) {
                // 现在基本不使用该配置方法了
                if (cmsBlockContentTpResponse.getSkipType() != null && cmsBlockContentTpResponse.getSkipUrl() != null
                        && cmsBlockContentTpResponse.getSkipType().equalsIgnoreCase("3")
                        && cmsBlockContentTpResponse.getSkipUrl().equalsIgnoreCase("1")) {
                    // 专辑专题
                    subjectType = SubjectConstant.SUBJECT_TYPE_ALBUM;
                } else if (cmsBlockContentTpResponse.getSkipType().equalsIgnoreCase("3")
                        && cmsBlockContentTpResponse.getSkipUrl().equalsIgnoreCase("2")) {
                    // 视频专题
                    subjectType = SubjectConstant.SUBJECT_TYPE_VIDEO;
                }
            }
            if (cmsBlockContentTpResponse.getZidType() != null) {
                if (cmsBlockContentTpResponse.getZidType() == CmsTpConstant.CMS_SUBJECT_TYPE_ALBUM) {
                    // 专辑专题
                    subjectType = SubjectConstant.SUBJECT_TYPE_ALBUM;
                } else if (cmsBlockContentTpResponse.getZidType() == CmsTpConstant.CMS_SUBJECT_TYPE_VIDEO) {
                    // 视频专题
                    subjectType = SubjectConstant.SUBJECT_TYPE_VIDEO;
                } else if (cmsBlockContentTpResponse.getZidType() == CmsTpConstant.CMS_SUBJECT_TYPE_LIVE) {
                    // 直播专题
                    subjectType = SubjectConstant.SUBJECT_TYPE_LIVE;
                }
            }
        }
        return subjectType;
    }

    /**
     * 根据专题信息，构造排行榜数据；兼容超前院线
     * @param subject
     * @return
     */
    private ChartsDataDto buildChartsDataBySubject(Subject subject) {
        ChartsDataDto chartsDataDto = null;

        if (subject != null && subject.getSubjectType() != null) {
            chartsDataDto = new ChartsDataDto();
            if (SubjectConstant.SUBJECT_TYPE_PRELIVE == subject.getSubjectType()) {
                // 超前院线
                SubjectPreLive preLive = (SubjectPreLive) subject;
                chartsDataDto.setCinemaId(preLive.getCinemaId());
            }
            chartsDataDto.setSubjectId(subject.getSubjectId());
            chartsDataDto.setSubjectType(subject.getSubjectType());
            chartsDataDto.setName(subject.getName());
            chartsDataDto.setSubName(subject.getSubName());
            chartsDataDto.setDataType(subject.getJump().getType());
        }

        return chartsDataDto;
    }

    /**
     * 2015-10-22添加上映时间特殊处理逻辑，存在运营于半夜更新数据，故产品决定，在当前23:30之前在TV上映的，属于当天上映；23:30之后的
     * ，属于次日上映
     * @return
     */
    private Calendar getTvFirstOnStatisticsTime(Calendar tvFirstOnTimeCalendar) {
        Calendar tvFirstOnStatisticsTime = null;
        if (tvFirstOnTimeCalendar != null) {
            int hour = tvFirstOnTimeCalendar.get(Calendar.HOUR_OF_DAY);
            int minute = tvFirstOnTimeCalendar.get(Calendar.MINUTE);
            if (hour == 23 && minute >= 30) {
                // 在23:30--24:00之间，直接加到第二天，然后归零到0点
                tvFirstOnTimeCalendar.add(Calendar.MINUTE, 40);
                tvFirstOnStatisticsTime = TimeUtil.truncateDay(tvFirstOnTimeCalendar);
            } else {
                tvFirstOnStatisticsTime = TimeUtil.truncateDay(tvFirstOnTimeCalendar);
            }
        }

        return tvFirstOnStatisticsTime;
    }

    /**
     * 根据年龄、板块id获取CMS板块内容
     * @return
     */
    public Response<BlockContentDto> getCourse(Integer channelId, String age, String gender, String week,
            CommonParam commonParam) {
        String logPrefix = "getCmsBlockContent_" + channelId + "_" + age + "_" + week + "_" + commonParam.getMac();
        Response<BlockContentDto> response = new Response<BlockContentDto>();
        String errorCode = null;
        if (channelId != null && channelId > 0) {
            // 数据源列表
            List<ChannelDataMysqlTable> channelDataMysqlTableList = this.facadeMysqlDao.getChannelDataMysqlDao()
                    .getList(channelId);
            for (ChannelDataMysqlTable channelDataMysqlTable : channelDataMysqlTableList) {
                Integer dataSource = channelDataMysqlTable.getData_source();
                if (dataSource == null) {
                    continue;
                }
                if (dataSource == ChannelConstants.DATASOURCE_CMS_SERVER) {
                    String subBlockId = this.getSubBlockIdByParams(channelId,
                            String.valueOf(channelDataMysqlTable.getChannel_id()), age, "", week);
                    CmsBlockTpResponse tpResponse = null;
                    if (StringUtil.isNotBlank(subBlockId)) {
                        tpResponse = this.facadeTpDao.getCmsTpDao().getCmsBlockNewById(subBlockId, commonParam);
                    }
                    if (tpResponse != null) {
                        BlockContentDto data = new BlockContentDto();
                        data.setName(tpResponse.getName());
                        List<BlockContentDto> dataPackageList = new ArrayList<BlockContentDto>();
                        data.setDataPackageList(dataPackageList);
                        List<String> subBlockList = tpResponse.getSubBlockList();
                        if (subBlockList != null && subBlockList.size() > 0) {
                            int size = subBlockList.size();
                            int count = 0;
                            for (String blockId : subBlockList) {
                                CmsBlockTpResponse blockContent = this.facadeTpDao.getCmsTpDao().getCmsBlockNewById(
                                        blockId, commonParam);
                                BlockContentDto blockdata = new BlockContentDto();
                                if (blockContent != null) {
                                    if (blockContent.getName() != null) {
                                        String[] course = blockContent.getName().split("\\|");
                                        if (course.length > 1) {
                                            blockdata.setSubName(course[1]);
                                            blockdata.setType(ChannelConstants.CHANNEL_COURSE_TYPE_ON_CLASS);
                                        }
                                        blockdata.setName(course[0]);
                                    }
                                    blockdata.setId(blockContent.getId());
                                    List<BaseData> dataList = new ArrayList<BaseData>();
                                    blockdata.setDataList(dataList);
                                    List<CmsBlockContentTpResponse> contentList = blockContent.getBlockContent();
                                    for (CmsBlockContentTpResponse cmsBlockContentTpResponse : contentList) {
                                        BaseData baseData = this.getDataFromCms(cmsBlockContentTpResponse, commonParam,
                                                null, null, null);
                                        if (baseData != null) {
                                            dataList.add(baseData);
                                        }
                                    }
                                }
                                count++;
                                if (count == size
                                        && blockdata.getType() == ChannelConstants.CHANNEL_COURSE_TYPE_OFF_CLASS) {
                                    blockdata.setType(ChannelConstants.CHANNEL_COURSE_TYPE_INTEREST_CLASS);
                                }
                                dataPackageList.add(blockdata);
                            }
                        }
                        response.setData(data);
                    } else {
                        log.info(logPrefix + "_" + subBlockId + ": get cms block data failed.");
                    }
                }
            }
        } else {
            errorCode = ErrorCodeConstants.CHANNEL_PARAMETER_ERROR;
            Integer valcode = ChannelMsgCodeConstant.CHANNEL_GET_CMSBLOCK_REQUEST_BLOCKID_EMPTY;
            String errorMsg = MessageUtils.getMessage(
                    ErrorMsgCodeUtil.parseErrorMsgCode(errorCode, ChannelConstants.CMSBLOCK, valcode),
                    commonParam.getLangcode());
            ResponseUtil.setErrorResponse(response, errorCode, errorMsg, commonParam.getWcode());
            log.info(logPrefix + "[errorCode=" + errorCode + "]:.parameter illegal.");
        }
        return response;
    }

    public Response<BlockContentDto> getCourse_v2(String age, String week, CommonParam commonParam) {
        String logPrefix = "getCourse_v2 age:" + age + " week:" + week + commonParam.getMac();
        Response<BlockContentDto> response = new Response<BlockContentDto>();
        if (StringUtil.isBlank(age)) {
            age = 3 + "";
        }
        String dataParam = this.getDateStr(week);
        CmsCourseTpResponse cmsCourseTpResponse = this.facadeTpDao.getCmsTpDao().getCmsCourseData(dataParam, dataParam,
                StringUtil.toInteger(age), commonParam);
        if (cmsCourseTpResponse != null && cmsCourseTpResponse.getData() != null
                && cmsCourseTpResponse.getData().size() > 0) {
            List<CmsCourseDataTpResponse> coursesData = cmsCourseTpResponse.getData();
            for (CmsCourseDataTpResponse courseData : coursesData) {
                if (courseData != null && courseData.getCourses() != null && courseData.getCourses().size() > 0) {
                    BlockContentDto data = new BlockContentDto();
                    data.setName("");
                    List<BlockContentDto> dataPackageList = new ArrayList<BlockContentDto>();
                    data.setDataPackageList(dataPackageList);
                    List<CourseDataTpResponse> courses = courseData.getCourses();
                    for (CourseDataTpResponse course : courses) {
                        BlockContentDto blockdata = new BlockContentDto();
                        // blockdata.setType(ChannelConstants.CHANNEL_COURSE_TYPE_ON_CLASS);
                        blockdata.setName(course.getName());
                        blockdata.setSubName(course.getDesc());
                        blockdata.setId(course.getId());
                        List<BaseData> dataList = new ArrayList<BaseData>();
                        blockdata.setDataList(dataList);
                        if (course.getThemes() != null && course.getThemes().size() > 0) {
                            List<ThemesTpResponse> themes = course.getThemes();
                            for (ThemesTpResponse theme : themes) {
                                String vids = theme.getVids();
                                String[] videoIds = vids.split(",");
                                if (videoIds != null && videoIds.length > 0) {
                                    for (String videoId : videoIds) {
                                        BaseData baseData = this.getVideoData(videoId, commonParam);
                                        if (baseData != null) {
                                            dataList.add(baseData);
                                        }
                                    }
                                }
                            }
                        }
                        dataPackageList.add(blockdata);
                    }
                    response.setData(data);
                }
            }
        }
        return response;
    }

    private String getDateStr(String week) {
        Integer week_int = StringUtil.toInteger(week);
        Integer sysWeek = StringUtil.toInteger(this.getWeek());
        if (StringUtil.isNotBlank(week)) {
            if (week_int > 7) {
                week_int = week_int - 7;
            }
        } else {
            week_int = sysWeek;
        }
        return TimeUtil.someDayAfter((week_int - sysWeek), TimeUtil.SHORT_DATE_FORMAT_NO_DASH);
    }

    private BaseData getVideoData(String vId, CommonParam commonParam) {
        VideoDto video = null;
        Long videoId = StringUtil.toLong(vId, null);
        VideoMysqlTable videoMysql = albumVideoAccess.getVideo(videoId, commonParam);
        if (videoMysql != null) {
            video = new VideoDto();
            video.setVideoId(vId);
            video.setName(videoMysql.getName_cn());
            video.setImg(videoMysql.getPic(400, 300));
            video.setSubName(videoMysql.getSub_title());
            video.setAlbumId(String.valueOf(videoMysql.getPid()));
            video.setCategoryId(videoMysql.getCategory());
            video.setSubCategoryId(videoMysql.getSub_category());
            if (videoMysql.getPay_platform() != null) {
                video.setIfCharge(MmsDataUtil.isSupportPayPlatform(videoMysql.getPay_platform(),
                        commonParam.getP_devType()) ? "1" : "0");
            } else {
                video.setIfCharge("0");
            }
            // for tvod icon type
            Integer chargeType = JumpUtil.getChargeType(videoMysql.getPay_platform(), commonParam);
            if (null == chargeType) {
                chargeType = JumpUtil.getChargeType(videoMysql.getPay_platform());
            }
            video.setChargeType(chargeType);

            if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                video.setChargeInfos(JumpUtil.genChargeInfos(videoMysql.getPay_platform()));
            }
        }
        return video;
    }

    private String getSubBlockIdByParams(Integer channnelid, String blockId, String age, String gender, String week) {
        String preFixLog = "blockidMatch_" + blockId + "" + age + "" + gender + "" + week;
        String subBlockId = null;
        if (channnelid != null && blockId != null) {
            List<ChannelDataExtendMysqlTable> channelDataExtendList = null;
            if (gender == null) {
                gender = "";
            }
            age = this.defultAge(channnelid, age, gender);
            if (ChannelConstants.CHANNEL_MATCH_BLOCK_AGE_LIST.contains(channnelid)) {
                channelDataExtendList = this.facadeMysqlDao.getChannelDataExtendMysqlDao()
                        .getBlockIdByAge(blockId, age);
            } else if (ChannelConstants.CHANNEL_MATCH_BLOCK_AGE_GENDER_LIST.contains(channnelid)) {
                channelDataExtendList = this.facadeMysqlDao.getChannelDataExtendMysqlDao().getBlockIdByAgeGender(
                        blockId, age, gender);
            } else if (ChannelConstants.CHANNEL_MATCH_BLOCK_AGE_WEEK_LIST.contains(channnelid)) {
                if (StringUtil.isBlank(week)) {
                    week = this.getWeek();
                }
                channelDataExtendList = this.facadeMysqlDao.getChannelDataExtendMysqlDao().getBlockIdByAgeWeek(blockId,
                        age, week);
            }
            for (ChannelDataExtendMysqlTable channelDataExtend : channelDataExtendList) {
                if (channelDataExtend != null) {
                    subBlockId = channelDataExtend.getBlock_id();
                    log.info(preFixLog + " mapping a new blockid :" + subBlockId);
                    break;
                }
            }
        } else {
            log.error(preFixLog + " blockId is null error ");
        }
        return subBlockId;
    }

    private String defultAge(Integer channelId, String age, String gender) {
        if (StringUtil.isNotBlank(gender) && (StringUtil.isBlank(age) || Integer.parseInt(age) < 1)) {
            age = ChannelConstants.CHILDERN_MIN_AGE_DEFULT;
        } else {
            // if (channelId == 1083) {
            // if (StringUtil.isNotBlank(age) && Integer.parseInt(age) > 12) {
            // age = "12";
            // }
            // } else {
            if (StringUtil.isNotBlank(age) && Integer.parseInt(age) > 6) {
                age = ChannelConstants.CHILDERN_MAX_AGE_DEFULT;
            }
        }
        // }
        return age;
    }

    /**
     * 获取系统星期数
     * @return
     */
    private String getWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        int number = calendar.get(Calendar.DAY_OF_WEEK);
        return String.valueOf(number);
    }

    /**
     * 更新视频营销相关配置
     * @return
     */
    private boolean updateVideoSaleConfig() {
        boolean result = false;
        String logPrefix = "updateVideoSaleConfig_";

        if ((System.currentTimeMillis() - VIDEO_SALE_CONFIG_LASTUPDATE_TIME_MILLIS) > VIDEO_SALE_CONFIG_UPDATE_INTERVAL_MILLIS) {
            if (this.VIDEO_SALE_CONFIG_UPDATE_LOCK.tryLock()) {
                log.info(logPrefix + ": locked.");
                try {
                    // http://static.itv.letv.com/api/conf/channel/profile.txt
                    Map<String, String> map = this.facadeTpDao.getStaticTpDao().getStaticFileContent(
                            ApplicationUtils.get(ApplicationConstants.CHANNEL_PRELIVE_PROFILE_URL));// 静态文件内容

                    if (map != null) {
                        if (ChannelConstants.VIDEO_SALE_CONFIG_MAP == null) {
                            ChannelConstants.VIDEO_SALE_CONFIG_MAP = new HashMap<String, String>();
                        }
                        ChannelConstants.VIDEO_SALE_CONFIG_MAP.clear();
                        ChannelConstants.VIDEO_SALE_CONFIG_MAP.putAll(map);

                        this.facadeCacheDao.getChannelCacheDao().setVideoSaleConfig(map);
                        VIDEO_SALE_CONFIG_LASTUPDATE_TIME_MILLIS = System.currentTimeMillis();
                        log.info(logPrefix + ": update pre live button config success, value-" + map);
                        result = true;
                    } else {
                        log.info(logPrefix + ": update pre live button config failure");
                    }
                } catch (Exception e) {
                    log.error(logPrefix + "update pre live button config error", e);
                } finally {
                    this.VIDEO_SALE_CONFIG_UPDATE_LOCK.unlock();
                    log.info(logPrefix + ": unlocked.");
                }
            }
        }

        return result;
    }

    public List<AdvertisementPicture> getStartupAdvertisePic(Integer type, String pos, Integer page, Integer pageSize) {
        return this.facadeMysqlDao.getAdvertisementMysqlDao().getStartupAdvertisePic(type, pos, (page - 1) * pageSize,
                pageSize);
    }

    public Response<StartupAdResponse> getStartupAdvertisePic(CommonParam commonParam) {
        String blockId = ApplicationUtils.get(ApplicationConstants.CMS_BLOCKID_STARTUP_PIC_TV_GET);
        List<BaseData> dataList = new ArrayList<BaseData>();
        String errorCode = null;
        StartupAdResponse<BaseData> responseData = new StartupAdResponse<BaseData>();
        CmsBlockTpResponse cmsBlockTpResponse = this.facadeTpDao.getCmsTpDao().getCmsBlockNewById(blockId, commonParam);
        if (cmsBlockTpResponse != null) {
            if (cmsBlockTpResponse.getBlockContent() != null && cmsBlockTpResponse.getBlockContent().size() > 0) {
                for (CmsBlockContentTpResponse cmsBlockContentTpResponse : cmsBlockTpResponse.getBlockContent()) {
                    BaseData baseData = this.getDataFromCms(cmsBlockContentTpResponse, commonParam, null, null, null);
                    if (baseData != null) {
                        dataList.add(baseData);
                    }
                }
            }
        } else {
            // errorCode = ErrorCodeConstants.STARTUP_PIC_GET_ERROR;
        }
        responseData.setItems(dataList);
        responseData.setMsg("加载广告图片");

        /**
         * 启播loading图,借开机广告的通道
         */
        String loadingTvPiv = getLoadingPic(commonParam);

        if (StringUtil.isNotBlank(loadingTvPiv)) {
            responseData.setLoadingPic(loadingTvPiv);
        }

        Response<StartupAdResponse> response = new Response<StartupAdResponse>();
        if (errorCode == null) {
            response.setData(responseData);
        } else {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }
        return response;
    }

    public String getLoadingPic(CommonParam commonParam) {
        String blockId = ApplicationUtils.get(ApplicationConstants.CMS_BLOCKID_LOADING_PIC_TV_GET);
        String loadingTvPiv = this.facadeCacheDao.getChannelCacheDao().getLoadingPic(commonParam);
        if (loadingTvPiv != null) {
            if (loadingTvPiv.equals("empty")) {
                return null;
            }
            return loadingTvPiv;
        }
        CmsBlockTpResponse cmsBlockTpResponse = this.facadeTpDao.getCmsTpDao().getCmsBlockNewByIdV2(blockId,
                commonParam);

        if (cmsBlockTpResponse != null) {
            if (cmsBlockTpResponse.getBlockContent() != null && cmsBlockTpResponse.getBlockContent().size() > 0) {
                for (CmsBlockContentTpResponse cmsBlockContentTpResponse : cmsBlockTpResponse.getBlockContent()) {
                    if (cmsBlockContentTpResponse != null
                            && StringUtil.isNotBlank(cmsBlockContentTpResponse.getTvPic())) {
                        loadingTvPiv = cmsBlockContentTpResponse.getTvPic();
                        break;
                    }
                }
            }
            if (StringUtil.isBlank(loadingTvPiv)) {
                loadingTvPiv = "empty";
            }
        }
        if (loadingTvPiv != null) {
            this.facadeCacheDao.getChannelCacheDao().setLoadingPic(commonParam, loadingTvPiv);
        }
        if (loadingTvPiv != null && loadingTvPiv.equals("empty")) {
            return null;
        }
        return loadingTvPiv;
    }

    public List<ChannelDto> getMine(String channelId, String validDate, Integer deviceType, CommonParam commonParam) {
        List<ChannelDto> dataList = new ArrayList<ChannelDto>();
        String url = ApplicationUtils.get(ApplicationConstants.CMS_BLOCK_MINE_1190_GET);
        if ("hk".equals(commonParam.getWcode())) {
            url = ApplicationUtils.get(ApplicationConstants.CMS_BLOCK_MINE_1598_GET);
        }

        CmsBlockTpResponse response = this.facadeTpDao.getCmsTpDao().getCmsBlockNew(url, commonParam);
        ChannelDto channelDto = new ChannelDto();
        dataList.add(channelDto);
        BlockContentDto vipRecommend = new BlockContentDto();// “我的”第3列，会员推荐位数据
        vipRecommend.setType(null);
        List<BaseData> resources = new ArrayList<BaseData>();
        if (response != null && response.getBlockContent() != null) {
            for (CmsBlockContentTpResponse dataInfo : response.getBlockContent()) {
                if (dataInfo != null) {
                    // 2015-09-09，将“我的”Tab页老业务扩展，该功能需要升级
                    BaseData resource = this.getDataFromCms(dataInfo);
                    if (resource != null) {
                        resources.add(resource);
                    }
                }
            }
        }
        channelDto.setResources(resources);
        if (TerminalUtil.isLetvUs(commonParam) || TerminalUtil.isLetvCommon(commonParam)) {
            return dataList;// letv_us or letv_common version no activity
        }
        // 2016-03-08活动走会员运营中心，不走CMS，如果第7个触达位没有活动触达，则走CMS获取4个会员专享专辑
        List<BaseData> commonActivityList = new LinkedList<BaseData>();
        List<BaseData> vipRecList = new LinkedList<BaseData>();
        String recTvPic = null;
        String activityDefaultImg = null;
        Map<String, String> configMap = this.getVideoSaleConfigs();
        if (!CollectionUtils.isEmpty(configMap)) {
            recTvPic = configMap.get(ChannelConstants.CHANNEL_MINE_RECOMMEND_DEFAULT_IMG);
            activityDefaultImg = configMap.get(ChannelConstants.CHANNEL_MINE_DEFAULT_IMG);
        }
        String[] positions = VipTpConstant.urm_positions_mine;
        for (String position : positions) {
            GetUrmActivityResponse tpResponse = this.facadeTpDao.getVipTpDao().getUrmTouchData(position, deviceType,
                    commonParam);
            PilotDto dto = vipMetadataService.parseUrmActivityData(tpResponse, position,
                    commonParam);
            if (dto != null) {
                if (position != null && position.equals(VipTpConstant.URM_POSITION_MINE_4)) {// 第4个位置，独立出来
                    vipRecList.add(dto);
                } else {
                    commonActivityList.add(dto);
                }
            } else {
                if (!(position != null && position.equals(VipTpConstant.URM_POSITION_MINE_4))) {// 第4个位置，独立出来
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
        if (!CollectionUtils.isEmpty(commonActivityList)) {// 中间列活动数据不为空，则返回
            channelDto.setActivities(commonActivityList);
        }
        if (!CollectionUtils.isEmpty(vipRecList)) {
            vipRecommend.setDataList(vipRecList);
            vipRecommend.setRecommendDataType(0);
            vipRecommend.setTvPic(recTvPic);
            channelDto.setVipRecommend(vipRecommend);
        } else if (response != null && !CollectionUtils.isEmpty(response.getSubBlockList())) {// 第三列活动位如果没有触达，则需要从CMS获取配置的会员专享专辑
            List<String> subBlocksList = response.getSubBlockList();
            for (String blockId : subBlocksList) {
                if (StringUtils.isNotBlank(blockId)) {
                    CmsBlockTpResponse cmsDataResponse = this.facadeTpDao.getCmsTpDao().getCmsBlockNewById(blockId,
                            commonParam);
                    if (cmsDataResponse == null) {
                        continue;
                    }
                    String name = cmsDataResponse.getName();
                    if (ChannelConstants.CHANNEL_MINE_BLOCK_HUIYUAN.equalsIgnoreCase(name)) {
                        // 会员专享版块，年前需求暂时无法对接大屏会员中心后台数据，所以通过在“我的”版块中配置子版块"huiyuan"
                        List<BaseData> albumList = new LinkedList<BaseData>();
                        for (CmsBlockContentTpResponse dataInfo : cmsDataResponse.getBlockContent()) {
                            if (dataInfo != null && dataInfo.getPushflag() != null
                                    && dataInfo.getPushflag().contains(CommonConstants.TV_PLATFROM_CODE)) {
                                BaseData baseData = this.getDataFromCms(dataInfo, commonParam, null, null, null);
                                if (baseData != null) {// 所有用户都推单视频
                                    if (baseData instanceof AlbumDto) {// 专辑数据列表
                                        albumList.add(baseData);
                                    }
                                }
                            }
                        }
                        if (albumList.size() > 0) {
                            vipRecommend.setDataList(albumList);
                            vipRecommend.setRecommendDataType(1);
                            vipRecommend.setTvPic(recTvPic);
                            channelDto.setVipRecommend(vipRecommend);
                        }
                    }

                }
            }
        }

        return dataList;
    }

    /**
     * 清空色块缓存
     */
    public BaseResponse clearRecBlockCache() {
        if ("10.200.91.43".equals(HttpUtil.getIP())) {
            if (channelDataRecommendationBlockMap != null && channelDataRecommendationBlockMap.size() > 0) {
                channelDataRecommendationBlockMap.clear();
                log.info("clearRecBlockCache_channelDataRecommendationBlockMap has been clear!");
            }
        }
        return new PageResponse();
    }

    public BaseResponse clearCache(Integer clearId, CommonParam commonParam) {
        log.info("clearCache bean visited by clientIp: " + HttpUtil.getIP());
        if ("10.200.91.43".equals(HttpUtil.getIP()) && clearId != null && clearId != 0) {
            switch (clearId) {
            case 1:
                // 清空色块内存
                if (channelDataRecommendationBlockMap != null && channelDataRecommendationBlockMap.size() > 0) {
                    channelDataRecommendationBlockMap.clear();
                    log.info("clearCache_channelDataRecommendationBlockMap has been clear!");
                }
                break;
            case 2:
                // 更新播放mac白名单
                if (commonParam != null
                        && TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                                .getTerminalApplication())) {
                    playService.updatePlayMacWhiteList(commonParam);
                } else {
                    VideoUtil.clearMacWhiteList();
                    playService.updateNoAreaPlayRestrictMacs();
                }
                log.info("clearCache_NO_AREA_PLAY_RESTRICT_MAC_SET has been update!");
                break;
            case 3:
                // 更新国际化表内存
                mutilLanguageService.updateCache();
                break;
            case 4:
                // 更新message
                MessageUtils.initMessageCache();
                log.info("clearCache_MessageUtils.initMessageCache has been update!");
                break;
            case 5:
                // 更新httpurl config
                ApplicationUtils.initApplicationCache();
                log.info("clearCache_ApplicationUtils.initApplicationCache has been update!");
                break;
            case 6:
                // 更新多设备登录userId白名单
                if (VideoUtil.MUTIL_MACHINE_LOGIN_USERID_WHITE_SET != null) {
                    userService.clearMutilMachineLoginUidLastUpdateTime();// 清除最新更新时间
                    userService.updateMutilMachineLoginUids(); // 更新内存数据
                    log.info("clearCache_MUTIL_MACHINE_LOGIN_USERID_WHITE_SET has been update!");
                }
            case 7:
                // 更新burrow config
                BurrowUtilsV3.initBurrowCache();
                log.info("clearCache_BurrowUtils.initBurrowCache has been update!");
                break;
            default:
                log.info("clearCache_no case  has been update!");
                break;
            }
        }
        return new PageResponse();
    }

    /**
     * 从CMS解析数据；
     * 2015-09-09，该方法参考了com.letv.itv.v2.api.module.channel.ChannelService.
     * getDataFromCms()方法，因为2.5之前的频道接口仅剩“我的”Tab页在继续使用，故该方法仅为了扩展老接口的“我的”Tab页功能，
     * 在其迁移至205之后，该接口将废弃
     * @return
     */
    public BaseData getDataFromCms(CmsBlockContentTpResponse cmsDataInfo) {
        ResourceDto resource = new ResourceDto();
        resource.setImg615x225(cmsDataInfo.getTvPic());
        resource.setImg929x466(cmsDataInfo.getPic1());
        resource.setImg400x300(cmsDataInfo.getPic2());
        resource.setImg(cmsDataInfo.getTvPic());
        resource.setTitle(cmsDataInfo.getTitle());
        resource.setSubTitle(cmsDataInfo.getSubTitle());
        resource.setPos(cmsDataInfo.getPosition());
        resource.setShortDesc(cmsDataInfo.getShorDesc());
        if (cmsDataInfo.getVideo() != null) {
            resource.setCid(String.valueOf(cmsDataInfo.getVideo().getCid()));
        }
        if (StringUtils.isEmpty(cmsDataInfo.getContent())) {
            if (StringUtils.isNotEmpty(cmsDataInfo.getTvUrl())) {
                // 仅配置tvUrl就认为是tv外跳，展示H5之后无交互
                resource.setUrl(cmsDataInfo.getTvUrl());
                // 相关常量参见BrowserConstant.*
                resource.setBrowserType(1); // TV内跳
                resource.setOpenType(0); // 无交互

                Map<String, String> urlMap = new HashMap<String, String>();
                urlMap.put("0", cmsDataInfo.getTvUrl());
                resource.setUrlMap(urlMap);
                resource.setDataType(DataConstant.DATA_TYPE_BROWSER);
            } else if (cmsDataInfo.getSkipType() != null && !"".equals(cmsDataInfo.getSkipType())
                    && cmsDataInfo.getSkipUrl() != null && !"".equals(cmsDataInfo.getSkipUrl())) {
                // 跳浏览器（新逻辑）；2015-08-17，与产品王斐沟通，将CMS后台所有设置TV跳转定为打开内置浏览器，4--TV内跳，解释为跳转收银台，5--TV外跳，设置为不跳收银台且不做
                Map<String, String> urlMap = new HashMap<String, String>();
                urlMap.put("0", cmsDataInfo.getSkipUrl());
                resource.setUrlMap(urlMap);
                resource.setUrl(cmsDataInfo.getSkipUrl());
                // 相关常量参见BrowserConstant.*
                resource.setBrowserType(2); // 默认内置
                if (cmsDataInfo.getSkipType().equals(CmsTpConstant.CMS_BROWSER_TYPE_BUILTIN)) {// 内置
                    // 先展示H5再跳收银台
                    resource.setOpenType(1);
                } else {
                    // 只展示
                    resource.setOpenType(0);
                }
                resource.setDataType(DataConstant.DATA_TYPE_BROWSER);
            } else if (cmsDataInfo.getExtendJson() != null && cmsDataInfo.getExtendJson().getExtendPage() != null
                    && cmsDataInfo.getExtendJson().getExtendPage().length() > 0) {// 配置默认版块
                CmsTpResponseExt extendJson = cmsDataInfo.getExtendJson();

                // 自定义的配置，数据格式为{dataType}|{param1}|{param2}，其中dataType一定有，param1、param2不一定有；
                String extendPage = extendJson.getExtendPage();
                String[] extendPageElements = extendPage.split("\\|");
                int paramsLength = extendPageElements.length;
                if (extendPageElements != null || paramsLength > 0) {
                    // dataType值将对比com.letv.itv.v2.api.constants.DataConstant.DATA_TYPE_*
                    Integer dataType = StringUtil.toInteger(extendPageElements[0], 0);
                    if (0 != dataType) {
                        switch (dataType.intValue()) {
                        case 9:
                            // 跳浏览器，默认就是展示H5
                            Map<String, String> urls = null;
                            if (extendJson.getExtendTvDict() != null
                                    && extendJson.getExtendTvDict().getExtendJson() != null) {
                                urls = extendJson.getExtendTvDict().getExtendJson().getUrls();
                            }
                            resource.setBrowserType(2); // 默认内置
                            resource.setOpenType(0); // 默认只展示H5，无交互
                            resource.setUrlMap(urls);
                            break;
                        case 22:
                            if (paramsLength >= 2) {
                                resource.setCinemaId(extendPageElements[1]);
                            }
                            break;
                        case 23:
                            // 跳919活动页，实际上是跳板块
                            if (paramsLength >= 2) {
                                resource.setBlockId(extendPageElements[1]);
                            }
                            break;
                        default:
                            // 老业务，走默认逻辑
                            if (extendPageElements.length >= 2) {
                                resource.setLabelIdToPlay(extendPageElements[1]);
                            }
                            break;
                        }
                    }
                    resource.setDataType(dataType);
                }
            }
            return resource;
        }
        return null;
    }

    /**
     * 大剧推广容器
     * @param containerId
     *            容器id
     * @param comParam
     *            通用参数
     * @return
     */
    public Response<ContainerDto> getContainerDataById(Integer containerId, Integer viptype, CommonParam comParam) {
        Response<ContainerDto> response = new Response<ContainerDto>();
        String logPrefix = "getContainerDataById_" + containerId + "_" + viptype + "_" + comParam.getMac();
        String errorCode = null;
        if (containerId == null) {
            errorCode = ErrorCodeConstants.CHANNEL_PARAMETER_ERROR;
            String errorMessage = MessageUtils.getMessage(ErrorMsgCodeUtil.parseErrorMsgCode(errorCode,
                    ChannelConstants.CMSBLOCK, ChannelMsgCodeConstant.CHANNEL_GET_CMSBLOCK_REQUEST_CONTAINERID_EMPTY),
                    comParam.getLangcode());
            response.setResultStatus(0);
            response.setErrCode(errorCode);
            response.setErrMsg(errorMessage);
            log.info(logPrefix + "[errorCode=" + errorCode + "]:.parameter illegal.");
        } else {
            ContainerDto containerDto = this.getContainerData(containerId, viptype, comParam);
            if (containerDto != null) {
                response.setResultStatus(1);
                response.setData(containerDto);
            }
        }
        return response;
    }

    /**
     * CMS数据封装
     * @param containerId
     *            容器id
     * @return
     */
    private ContainerDto getContainerData(Integer containerId, Integer viptype, CommonParam commonParam) {
        String logPrefix = "getContainerData_" + containerId + "_" + commonParam.getMac();
        ContainerDto containerDto = new ContainerDto();
        if (containerId != null) {
            List<ContainerConfMysqlTable> containerConfMysqlTableList = this.facadeMysqlDao.getContainerConfMysqlDao()
                    .getList(containerId);
            List<BaseData> playDataList = new LinkedList<BaseData>();
            List<BaseData> liveDataList = new LinkedList<BaseData>();
            List<BaseData> vipPlayDataList = new LinkedList<BaseData>();
            List<BaseData> focusDataList = new LinkedList<BaseData>();
            List<BaseData> vipAboutList = new LinkedList<BaseData>();
            List<ChannelData> modelDataList = new LinkedList<ChannelData>();
            for (ContainerConfMysqlTable containerConfMysqlTable : containerConfMysqlTableList) {
                Integer dataSource = containerConfMysqlTable.getData_source();
                Integer uiPlateType = containerConfMysqlTable.getUi_plate_type();
                if (dataSource == null) {
                    log.info(logPrefix + "dataSource is null !");
                    continue;
                }
                if (dataSource == ChannelConstants.DATASOURCE_CMS) {
                    String url = containerConfMysqlTable.getData_url();
                    CmsBlockTpResponse cmsBlockTpResponse = this.facadeTpDao.getCmsTpDao().getCmsBlockNew(url,
                            commonParam);
                    if (cmsBlockTpResponse != null) {
                        List<CmsBlockContentTpResponse> cmsBlockContentTpResponseList = cmsBlockTpResponse
                                .getBlockContent();
                        int count = 0;
                        Integer dataSize = containerConfMysqlTable.getData_preloadsize();
                        Integer is_vip = containerConfMysqlTable.getIs_vip();
                        ChannelData channelData = new ChannelData();
                        List<BaseData> dataList = new LinkedList<BaseData>();
                        for (CmsBlockContentTpResponse cmsBlockContentTpResponse : cmsBlockContentTpResponseList) {
                            BaseData data = this.getDataFromCms(cmsBlockContentTpResponse, commonParam, null, null,
                                    null);
                            if (data != null && uiPlateType != null) {
                                switch (uiPlateType) {
                                case ChannelConstants.CHANNEL_UIPLATE_TYPE_BGIMG:
                                    StaticBlock bgBlock = (StaticBlock) data;
                                    containerDto.setBgImg(bgBlock.getImg());
                                    break;
                                case ChannelConstants.CHANNEL_UIPLATE_TYPE_ADIMG:
                                    StaticBlock adBlock = (StaticBlock) data;
                                    containerDto.setAdImg(adBlock.getImg());
                                    break;
                                case ChannelConstants.CHANNEL_UIPLATE_TYPE_6:
                                    if (data instanceof VideoDto) {
                                        VideoDto video = (VideoDto) data;
                                        VideoContainerDto videoDataDto = new VideoContainerDto();
                                        videoDataDto.setId(video.getVideoId());
                                        if (StringUtil.isNotBlank(video.getAlbumId())
                                                && !"0".equals(video.getAlbumId())) {
                                            videoDataDto.setAlbumId(video.getAlbumId());
                                        }
                                        videoDataDto.setName(video.getName());
                                        videoDataDto.setSubTitle(video.getSubName());
                                        videoDataDto.setTvPic(video.getImg());
                                        videoDataDto.setCategoryId(video.getCategoryId());
                                        videoDataDto.setDataType(video.getDataType());
                                        if (is_vip != null && is_vip == 1) {// 会员播放区数据
                                            vipPlayDataList.add(videoDataDto);
                                        } else {// 非会员播放数据同时也是默认播放数据
                                            playDataList.add(videoDataDto);
                                        }
                                    } else if (data instanceof Live) {
                                        liveDataList.add(data);
                                    }
                                    break;

                                case ChannelConstants.CHANNEL_UIPLATE_TYPE_FOCUSLIST:
                                    focusDataList.add(data);
                                    break;
                                /**
                                 * 会员推广部分需要判断是否会员和读取cms信息匹配上，cms中用位置标记是否会员，vip:
                                 * 会员；notvip不是会员
                                 */
                                case ChannelConstants.CHANNEL_UIPLATE_TYPE_VIPABOUT:
                                    String vipFlag = "notvip";
                                    if (data instanceof Browser) {
                                        Browser b = (Browser) data;
                                        vipFlag = b.getVipFlag();
                                    } else if (data instanceof BaseBlock) {
                                        BaseBlock b = (BaseBlock) data;
                                        vipFlag = b.getVipFlag();
                                    }
                                    if (("vip".equals(vipFlag) && VipTpConstant.SVIP_PC == viptype)
                                            || ("notvip".equals(vipFlag) && VipTpConstant.SVIP_NOT_NUMBER == viptype)) {
                                        vipAboutList.add(data);
                                    }
                                    break;
                                case ChannelConstants.CHANNEL_UIPLATE_TYPE_MODELLIST:
                                    dataList.add(data);
                                    break;
                                }
                                count++;
                            }
                            if (dataSize != null && dataSize.intValue() > 0 && dataSize.intValue() == count) {
                                break;
                            }
                        }
                        if (uiPlateType != null && uiPlateType == ChannelConstants.CHANNEL_UIPLATE_TYPE_MODELLIST) {
                            if (dataList != null && dataList.size() > 0) {
                                channelData.setTitle(cmsBlockTpResponse.getName());
                                channelData.setDataList(dataList);
                                modelDataList.add(channelData);
                            }
                        }
                    } else {
                        log.info(logPrefix + "_" + url + ": get cms block data failed.");
                    }
                }
            }
            /**
             * 播放窗有两套数据，如果是登录会员请求，优先返回运营的会员板块数据，如果没有运营此部分数据，则返回原playList
             */
            if ((vipPlayDataList != null && vipPlayDataList.size() > 0)
                    && (VipTpConstant.SVIP_PC == viptype || (playDataList == null))) {
                containerDto.setPlayDataList(vipPlayDataList);
            } else {
                containerDto.setPlayDataList(playDataList);
            }
            containerDto.setLiveDataList(liveDataList);
            /** 运营焦点图 */
            containerDto.setFocusDataList(focusDataList);
            containerDto.setVipAboutList(vipAboutList);
            /** 右侧浮层模板 */
            containerDto.setModelDataList(modelDataList);

        }
        return containerDto;
    }

    private String getTextFromFile(Map<String, String> map, String key) {
        if (CollectionUtils.isEmpty(map)) {
            return "";
        }
        return StringUtils.trimToEmpty(map.get(key));
    }

    /**
     * 儿童看视频答题游戏
     * @param pageId
     * @param commonParam
     * @return
     */
    public Response<ChannelComRespDto> getChildDataList(String pageId, String age, String gender,
            CommonParam commonParam) {
        Response<ChannelComRespDto> response = new Response<ChannelComRespDto>();
        ChannelComRespDto channelComRespDto = new ChannelComRespDto();
        HashMap<String, Object> plus = null;
        if (StringUtil.isBlank(pageId)) {
            pageId = this.getPageIdByAge(StringUtil.toInteger(age, 0));
        }
        this.setChannelDataList(pageId, channelComRespDto, plus, commonParam);
        if (channelComRespDto != null) {
            response.setResultStatus(1);
            response.setData(channelComRespDto);
            response.setPlus(plus);
        }
        return response;
    }

    /**
     * 新版频道services
     * @param channelId
     * @param pageId
     * @param commonParam
     * @return
     */
    public Response<ChannelComRespDto> getChannelDataList(Integer channelId, String pageId, CommonParam commonParam) {
        String logPrefix = "getChannelDataList_channelId=" + channelId + " pageId=" + pageId + "_"
                + commonParam.getMac();
        Response<ChannelComRespDto> response = new Response<ChannelComRespDto>();
        // 会员频道测试页面id1003551106 => 1003551292
        if (StringUtil.isBlank(pageId)) {
            pageId = this.getPageIdByChannelId(channelId);
        }
        ChannelComRespDto channelComRespDto = new ChannelComRespDto();
        HashMap<String, Object> plus = null;
        this.setChannelDataList(pageId, channelComRespDto, plus, commonParam);
        if (channelComRespDto != null) {
            response.setResultStatus(1);
            response.setData(channelComRespDto);
            response.setPlus(plus);
        }
        return response;
    }

    /**
     * 新版频道Dao封装
     * @param pageId
     * @param channelComRespDto
     * @param plus
     * @param commonParam
     */
    public void setChannelDataList(String pageId, ChannelComRespDto channelComRespDto, HashMap<String, Object> plus,
            CommonParam commonParam) {
        String logPrefix = "setChannelDataList_pageId=" + pageId + "_" + commonParam.getMac();
        if (StringUtil.isNotBlank(pageId)) {
            CmsPageTpResponse cmsPageTpResponse = this.facadeTpDao.getCmsTpDao().getCmsPage(pageId, commonParam);
            if (cmsPageTpResponse != null && cmsPageTpResponse.getData() != null) {
                List<CmsPageTpResponseFrag> cmsPageTpResponseFragList = cmsPageTpResponse.getData().getFrags();
                if (cmsPageTpResponseFragList != null && cmsPageTpResponseFragList.size() > 0) {
                    for (CmsPageTpResponseFrag cmsPageTpResponseFrag : cmsPageTpResponseFragList) {
                        ChannelData channelData = new ChannelData();
                        List<BaseData> dataList = new LinkedList<BaseData>();
                        channelData.setDataList(dataList);
                        channelData.setUiPlateType(StringUtil.toInteger(cmsPageTpResponseFrag.getContentStyle()));
                        channelData.setTitle(cmsPageTpResponseFrag.getContentName());
                        // 封装色块推荐部分数据到url
                        if (cmsPageTpResponseFrag.getContentStyle() != null
                                && String.valueOf(ChannelConstants.CHANNEL_UITYPE_RECURL).equals(
                                        cmsPageTpResponseFrag.getContentStyle())) {
                            String dataUrl = ChannelUtil.buildRecUrlDir(cmsPageTpResponseFrag.getRedirectPageId());
                            if (StringUtil.isBlank(dataUrl)) {
                                dataUrl = cmsPageTpResponseFrag.getRedirectUrl();
                            }
                            channelData.setDataUrl(dataUrl);
                            channelData.setTitleDataType(DataConstant.DATA_TYPE_MULTILIST_RECOMMENDATION);
                        }
                        Integer dataSize = cmsPageTpResponseFrag.getContentManulNum();
                        List<CmsBlockContentTpResponse> cmsBlockContentTpResponseList = cmsPageTpResponseFrag
                                .getBlockContents();
                        if (cmsBlockContentTpResponseList != null && cmsBlockContentTpResponseList.size() > 0) {
                            for (CmsBlockContentTpResponse cmsBlockContentTpResponse : cmsBlockContentTpResponseList) {
                                if ((cmsBlockContentTpResponse.getPushflag() == null)
                                        || !cmsBlockContentTpResponse.getPushflag().contains(
                                                CommonConstants.TV_PLATFROM_CODE)) {
                                    continue;
                                }
                                BaseData data = this.getDataFromCms(cmsBlockContentTpResponse, commonParam, null, null,
                                        null);
                                if (data != null) {
                                    if (dataList.size() == dataSize) {
                                        break;
                                    } else {
                                        dataList.add(data);
                                    }
                                }
                            }
                        }
                        this.setChannelComRespDto(channelComRespDto, channelData, plus);
                    }
                }
            } else {
                log.info(logPrefix + "cmsPageTpResponse is null or cmsPageTpResponse.getData is null");
            }
        }
    }

    public String getPageIdByAge(Integer age) {
        String pageId = null;
        this.updateChannelPageId();
        if (age == null || age > 12) {
            age = 0;
        }
        if (ChannelConstants.CHANNEL_MATCH_PAGEID_MAP != null && !ChannelConstants.CHANNEL_MATCH_PAGEID_MAP.isEmpty()) {
            pageId = ChannelConstants.CHANNEL_MATCH_PAGEID_MAP.get(ChannelConstants.CHILD_AQ_PAGEID_BY_AGE_PRE + age);
        }
        return pageId;
    }

    public String getPageIdByChannelId(Integer channelId) {
        String pageId = null;
        this.updateChannelPageId();
        if (channelId != null && ChannelConstants.CHANNEL_MATCH_PAGEID_MAP != null
                && !ChannelConstants.CHANNEL_MATCH_PAGEID_MAP.isEmpty()) {
            pageId = ChannelConstants.CHANNEL_MATCH_PAGEID_MAP.get(ChannelConstants.CHANNEL_MATCH_PAGEID_PRE
                    + channelId);
        }
        return pageId;
    }

    private void updateChannelPageId() {
        String logPrefix = "updateChannelPageId_";
        if ((System.currentTimeMillis() - PAGEID_LASTUPDATE_TIME_MILLIS) > PAGEID_UPDATE_INTERVAL_MILLIS) {
            if (this.PAGEID_UPDATE_LOCK.tryLock()) {
                try {
                    Map<String, String> map = this.facadeTpDao.getStaticTpDao().getStaticFileContent(
                            ApplicationUtils.get(ApplicationConstants.CHANNEL_PAGEID_STATIC_URL));// 静态文件内容
                    if (map == null) { // only for test!!!
                        map = new HashMap<String, String>();
                        // map.put("CHANNEL.MATCH.PAGEID.1104", "1003551106");
                        map.put("CHANNEL.MATCH.PAGEID.1104", "1003551292");
                    }
                    if (map != null) {
                        if (ChannelConstants.CHANNEL_MATCH_PAGEID_MAP == null) {
                            ChannelConstants.CHANNEL_MATCH_PAGEID_MAP = new HashMap<String, String>();
                        }
                        ChannelConstants.CHANNEL_MATCH_PAGEID_MAP.clear();
                        ChannelConstants.CHANNEL_MATCH_PAGEID_MAP.putAll(map);

                        PAGEID_LASTUPDATE_TIME_MILLIS = System.currentTimeMillis();
                    } else {
                        log.info(logPrefix + ": the data from static server is null.");
                    }
                } catch (Exception e) {
                    log.warn(logPrefix + ": .");
                } finally {
                    this.PAGEID_UPDATE_LOCK.unlock();
                    log.info(logPrefix + ": unlocked.");
                }
            }
        }
    }

    /**
     * 根据UItype封装到不同到key
     * @param channelComRespDto
     * @param channelData
     */
    private void setChannelComRespDto(ChannelComRespDto channelComRespDto, ChannelData channelData,
            HashMap<String, Object> plus) {
        if (channelComRespDto != null && channelData != null && channelData.getUiPlateType() != null) {
            Integer uiType = channelData.getUiPlateType();
            if (ChannelConstants.CHANNEL_UITYPE_BGPIC == uiType) {
                if (channelData.getDataList() != null && channelData.getDataList().size() > 0) {
                    if (channelData.getDataList().get(0) instanceof StaticBlock) {
                        StaticBlock bgBlock = (StaticBlock) channelData.getDataList().get(0);
                        channelComRespDto.setBgImg(bgBlock.getImg());
                    }
                }
            } else if (ChannelConstants.CHANNEL_UITYPE_RECURL == uiType) {
                channelComRespDto.setRecUrl(channelData);
            } else if (ChannelConstants.CHANNEL_UITYPE_HOME_1 == uiType) {
                channelComRespDto.setfDataList(channelData.getDataList());
            } else if (ChannelConstants.CHANNEL_UITYPE_HOME_2 == uiType) {
                channelComRespDto.setsDataList(channelData.getDataList());
            } else if (ChannelConstants.CHANNEL_UITYPE_SUBNAV == uiType) {
                if (channelData.getDataList() != null && channelData.getDataList().size() > 0) {
                    plus = new HashMap<String, Object>();
                    plus.put(PLUS_CHANNELLIST, channelData.getDataList());
                }
            } else {
                channelComRespDto.setDataList(channelData.getDataList());
            }
        }
    }

    /**
     * 页面数据
     * @param channelId
     * @param cmsPageId
     * @param age
     * @param gender
     * @param week
     * @param vipType
     * @param model
     * @param flushCode
     *            定时刷数据标示1
     * @param commonParam
     * @return
     */
    public BaseResponse getPageDataList(Integer channelId, String cmsPageId, String age, String gender, String week,
            Integer vipType, Integer model, Integer flushCode, CommonParam commonParam) {
        String logPrefix = "getPageDataList_" + channelId + "_" + commonParam.getMac() + "_"
                + commonParam.getBroadcastId();
        PageResponse<ChannelData> response = new PageResponse<>();
        if (StringUtil.isBlank(cmsPageId)) {
            Integer ageParam = StringUtil.toInteger(age, 0);
            cmsPageId = ChannelUtil.getPageIdByAge(ageParam);
        }
        // 整体缓存定时刷
        if (StringUtil.isNotBlank(cmsPageId)) {
            if (!ChannelConstants.PAGEDATALIST_CACHE_SWITCH || (flushCode != null && flushCode == 1)) {
                response = this.getPageResponse(channelId, cmsPageId, age, gender, week, vipType, model, response,
                        commonParam);
            } else {
                List<ChannelData> channelDataList = (List<ChannelData>) this.facadeCacheDao.getChannelCacheDao()
                        .getPageDataResponseData(cmsPageId + "_" + age + "_" + gender);
                HashMap<String, Object> plus = (HashMap<String, Object>) this.facadeCacheDao.getChannelCacheDao()
                        .getPageDataResponsePlus(cmsPageId + "_" + age + "_" + gender);
                if (channelDataList != null || plus != null) {
                    response.setData(channelDataList);
                    response.setPlus(plus);
                } else {
                    response = this.getPageResponse(channelId, cmsPageId, age, gender, week, vipType, model, response,
                            commonParam);
                }
            }
        }
        return response;
    }

    private PageResponse<ChannelData> getPageResponse(Integer channelId, String cmsPageId, String age, String gender,
            String week, Integer vipType, Integer model, PageResponse<ChannelData> response, CommonParam commonParam) {
        try {
            response = this.getPageDataResponse(channelId, cmsPageId, age, gender, week, vipType, model, commonParam);
            if (response != null) {
                this.facadeCacheDao.getChannelCacheDao().setPageDataResponseData(cmsPageId + "_" + age + "_" + gender,
                        response.getData());
                this.facadeCacheDao.getChannelCacheDao().setPageDataResponsePlus(cmsPageId + "_" + age + "_" + gender,
                        response.getPlus());
            }
        } catch (Exception e) {
            log.error("getPageResponse_error", e.getMessage(), e);
        }
        return response;
    }

    public PageResponse<ChannelData> getPageDataResponse(Integer channelId, String cmsPageId, String age,
            String gender, String week, Integer vipType, Integer model, CommonParam commonParam) {
        String logPrefix = "getPageResponse_" + channelId + "_" + cmsPageId + "_" + commonParam.getMac() + "_"
                + commonParam.getBroadcastId();
        List<ChannelData> channelDataList = new LinkedList<ChannelData>();
        HashMap<String, Object> plus = null;
        CmsPageTpResponse cmsPageTpResponse = this.facadeTpDao.getCmsTpDao().getCmsPage(cmsPageId, commonParam);

        if (cmsPageTpResponse != null && cmsPageTpResponse.getData() != null) {
            List<CmsPageTpResponseFrag> cmsPageTpResponseFragList = cmsPageTpResponse.getData().getFrags();
            if (cmsPageTpResponseFragList != null && cmsPageTpResponseFragList.size() > 0) {
                for (CmsPageTpResponseFrag cmsPageTpResponseFrag : cmsPageTpResponseFragList) {
                    ChannelData channelData = new ChannelData();
                    List<BaseData> dataList = new LinkedList<BaseData>();
                    channelData.setDataList(dataList);
                    channelData.setUiPlateType(StringUtil.toInteger(cmsPageTpResponseFrag.getContentStyle()));
                    channelData.setTitle(cmsPageTpResponseFrag.getContentName());
                    boolean UiFlag = Boolean.FALSE;
                    if (cmsPageTpResponseFrag.getContentStyle() != null
                            && ("241".equals(cmsPageTpResponseFrag.getContentStyle()) || "258"
                                    .equals(cmsPageTpResponseFrag.getContentStyle()))) {
                        UiFlag = Boolean.TRUE;
                        channelData.setDataUrl(cmsPageTpResponseFrag.getRedirectUrl());
                        // 复线bug日志
                        log.info(logPrefix + "dataUrl :" + channelData.getDataUrl());
                        channelData.setTitleDataType(DataConstant.DATA_TYPE_MULTILIST_RECOMMENDATION);
                    }
                    Integer dataSize = cmsPageTpResponseFrag.getContentManulNum();
                    String sexParam = cmsPageTpResponseFrag.getRedirectSubPageId();
                    if (StringUtil.isNotBlank(sexParam) && StringUtil.isNotBlank(gender) && !sexParam.contains(gender)) {
                        continue;
                    }
                    if (cmsPageTpResponseFrag.getContentType() != null && cmsPageTpResponseFrag.getContentType() == 4) {
                        // 根据推荐id匹配对应板块内容
                        String blockId = getBlockId(cmsPageTpResponseFrag.getContentRid(), age, gender);
                        CmsBlockTpResponse cmsBlockTpResponse = this.facadeTpDao.getCmsTpDao().getCmsBlockNewById(
                                blockId, commonParam);
                        if (cmsBlockTpResponse != null) {
                            this.setBlockContent(cmsBlockTpResponse, dataList, dataSize, channelId, vipType, null,
                                    null, channelData.getUiPlateType(), commonParam);
                            // 儿童桌面布局特殊处理
                            this.specialPositionGet(channelId, channelData, dataList);
                        } else {
                            log.info(logPrefix + "_" + blockId + ": get cms block data failed.");
                        }

                    } else {
                        List<CmsBlockContentTpResponse> cmsBlockContentTpResponseList = cmsPageTpResponseFrag
                                .getBlockContents();
                        if (cmsBlockContentTpResponseList != null && cmsBlockContentTpResponseList.size() > 0) {
                            for (CmsBlockContentTpResponse cmsBlockContentTpResponse : cmsBlockContentTpResponseList) {
                                if ((cmsBlockContentTpResponse.getPushflag() == null)
                                        || !cmsBlockContentTpResponse.getPushflag().contains(
                                                CommonConstants.TV_PLATFROM_CODE)) {
                                    continue;
                                }
                                BaseData data = this.getDataFromCms(cmsBlockContentTpResponse, commonParam, vipType,
                                        null, null);
                                if (data != null) {
                                    dataList.add(data);
                                }
                                this.specialPositionPut(channelId, data, cmsBlockContentTpResponse);
                            }
                        }
                    }

                    if (cmsPageTpResponseFrag.getContentStyle() != null
                            && "242".equals(cmsPageTpResponseFrag.getContentStyle())) {
                        if (channelData != null && channelData.getDataList() != null
                                && channelData.getDataList().size() > 0) {
                            plus = new HashMap<String, Object>();
                            plus.put(PLUS_CHANNELLIST, channelData.getDataList());
                        }
                    } else {
                        // le
                        // addon列表，客户端第一行显示大图，其他行显示小图。兼容运营不配置第一行数据导致显示错乱的错误。逻辑已告知产品，后期会改版
                        if (commonParam != null
                                && TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                                        .getTerminalApplication())) {
                            UiFlag = true;
                        }
                        if (UiFlag
                                || (channelData != null && channelData.getDataList() != null && channelData
                                        .getDataList().size() > 0)) {
                            channelDataList.add(channelData);
                        }
                    }
                }
            }
        }
        return new PageResponse<ChannelData>(channelDataList, plus);
    }

    public void setChildPlay4Voide(String cmsBlockId, Map<String, List<AlbumDto>> albumDtoMap, CommonParam commonParam) {
        if (StringUtil.isNotBlank(cmsBlockId)) {
            CmsBlockTpResponse tpResponse = this.facadeTpDao.getCmsTpDao().getCmsBlockNewById(cmsBlockId, commonParam);
            if (tpResponse != null) {
                BlockContentDto data = new BlockContentDto();
                data.setName(tpResponse.getName());
                data.setTvPic(this.getBlockTvPicById(String.valueOf(tpResponse.getId())));
                List<BaseData> dataList = new ArrayList<BaseData>();
                data.setDataList(dataList);
                List<CmsBlockContentTpResponse> contentList = tpResponse.getBlockContent();
                for (CmsBlockContentTpResponse cmsBlockContentTpResponse : contentList) {
                    List<AlbumDto> albumDtoList = new ArrayList<AlbumDto>();
                    BaseData baseData = this.getDataFromCms(cmsBlockContentTpResponse, commonParam, null, null, null);
                    if (baseData != null) {

                        if (baseData instanceof AlbumDto) {
                            AlbumDto albumDto = (AlbumDto) baseData;
                            String key = albumDto.getName();
                            if (albumDtoMap.containsKey(key)) { // 每个关键词只去一个播放量最高的专辑
                                continue;
                            }
                            Long alubmId = StringUtil.toLong(albumDto.getAlbumId(), null);
                            if (alubmId != null) {
                                // List<VideoMysqlTable> videoList =
                                // this.facadeMysqlDao.getVideoMysqlDao()
                                // .getVideoListByPorderAndCreateTime(alubmId,
                                // true, 0,
                                // commonParam.getBroadcastId());
                                List<VideoMysqlTable> videoList = albumVideoAccess.getVideoRange(
                                        alubmId, VideoTpConstant.QUERY_TYPE_POSITIVE, -1, 0, Integer.MAX_VALUE,
                                        commonParam, 4);
                                Set<VideoDto> videoDtoList = new TreeSet<VideoDto>();
                                if (videoList != null && videoList.size() > 0) {
                                    for (VideoMysqlTable video : videoList) {
                                        if (video != null && video.getId() != null) {
                                            VideoDto videoDto = new VideoDto();
                                            videoDto.setVideoId(video.getId() + "");
                                            videoDto.setName(video.getName_cn());
                                            videoDto.setCategoryId(video.getCategory());
                                            videoDtoList.add(videoDto);
                                        }
                                    }
                                }
                                if (videoDtoList.size() > 0) {
                                    albumDto.setPositiveSeries(videoDtoList);
                                }
                                albumDto.setJump(null);
                                albumDtoList.add(albumDto);
                                albumDtoMap.put(key, albumDtoList);
                            }
                        }
                    }
                }
            }
        }
    }

    private static String getBlockId(String strParam, String age, String gender) {
        String blockId = null;
        if (StringUtil.isNotBlank(strParam)) {
            String[] key_values = strParam.split(";");
            if (key_values != null && key_values.length > 0) {
                Map<String, String> maptmp = new HashMap<String, String>();
                for (String key_value : key_values) {
                    if (StringUtil.isNotBlank(key_value) && key_value.indexOf(":") > -1) {
                        String[] keyValue = key_value.split(":");
                        if (keyValue.length >= 2) {
                            maptmp.put(keyValue[0], keyValue[1]);
                        }
                    }
                }
                if (maptmp != null) {
                    String key = age + "-" + gender;
                    blockId = maptmp.get(key);
                }
            }
        }
        return blockId;
    }

    private boolean showToUser(ChannelMysqlTable channelMysqlTable, boolean support3D, boolean support4K,
            boolean supportDB) {
        if ((channelMysqlTable != null) && (channelMysqlTable.getId() != null)) {
            Integer id = channelMysqlTable.getId();
            if (id == ChannelConstants.THREE_D) {
                if ("all".equals(channelMysqlTable.getTerminal_series())) {
                    return true;
                }
                return support3D;
            } else if (id == ChannelConstants.DOLBY) {
                if ("all".equals(channelMysqlTable.getTerminal_series())) {
                    return true;
                }
                return supportDB;
            } else if (id == ChannelConstants.FOUR_K) {
                if ("all".equals(channelMysqlTable.getTerminal_series())) {
                    return true;
                }
                return support4K;
            }
        }
        return true;
    }

    private boolean showToUser(Channel channel, boolean support3D, boolean support4K, boolean supportDB) {
        if ((channel != null) && (channel.getChannelId() != null)) {
            Integer id = channel.getChannelId();
            if (id == ChannelConstants.THREE_D) {
                // if ("all".equals(channelMysqlTable.getTerminal_series())) {
                // return true;
                // }
                return support3D;
            } else if (id == ChannelConstants.DOLBY) {
                // if ("all".equals(channelMysqlTable.getTerminal_series())) {
                // return true;
                // }
                return supportDB;
            } else if (id == ChannelConstants.FOUR_K) {
                // if ("all".equals(channelMysqlTable.getTerminal_series())) {
                // return true;
                // }
                return support4K;
            }
        }
        return true;
    }

    public PageResponse<ContentPackageDto> getContentPackage(Integer productId, Integer page, Integer pageSize,
            CommonParam commonParam) {
        String errorCode = null;
        List<ContentPackageDto> contentPackageDtoList = new LinkedList<ContentPackageDto>();
        Integer totalCount = null;
        if (productId == null) {
            errorCode = ErrorCodeConstants.VIP_GET_CONTENT_PACKAGE_FAIL;
        } else {
            BossTpResponse<List<VipPackage>> vipPackageListTpResponse = this.facadeTpDao.getVipTpDao()
                    .getPackageByProductId(productId, commonParam); // 获取会员下所有套餐信息
            if (vipPackageListTpResponse == null || !vipPackageListTpResponse.isSucceed()) {
                errorCode = ErrorCodeConstants.PAY_GET_PACKAGE_TYPE_FAILURE;
            } else {
                List<VipPackage> vipPackageList = vipPackageListTpResponse.getData();
                if (vipPackageList != null && vipPackageList.size() > 0) {
                    VipPackage vipPackage = null;
                    for (VipPackage vPackage : vipPackageList) { // 找到第一个可用的套餐包
                        if (vPackage.getTerminal() != null
                                && vPackage.getTerminal().contains(CommonConstants.TV_PAY_CODE)) { // 套餐在tv端可用
                            vipPackage = vPackage;
                            break;
                        }
                    }
                    if (vipPackage == null) {
                        errorCode = ErrorCodeConstants.VIP_GET_PACKAGE_INFO_FAIL;
                    } else {
                        Integer packageId = vipPackage.getId();
                        BossTpResponse<ContentPackage> contentPackageTpResponse = this.facadeTpDao.getVipTpDao()
                                .getPackageInfoById(packageId, page, pageSize, commonParam); // 获取内容包
                        if (contentPackageTpResponse == null || !contentPackageTpResponse.isSucceed()) {
                            errorCode = ErrorCodeConstants.PAY_GET_PACKAGE_TYPE_FAILURE;
                        } else {
                            ContentPackage contentPackage = contentPackageTpResponse.getData();
                            if (contentPackage != null) {
                                totalCount = contentPackage.getTotal(); // 获取总数
                                List<ContentInfo> contentInfoList = contentPackage.getcInfo(); // 获取具体内容包
                                if (contentInfoList != null) {
                                    for (ContentInfo contentInfo : contentInfoList) {
                                        if (VipTpConstant.ContentType.ALBUM.getCode().equals(contentInfo.getCtype())) {// 专辑
                                            AlbumMysqlTable album = albumVideoAccess.getAlbum(
                                                    contentInfo.getCid(), commonParam);
                                            if (album != null) {
                                                ContentPackageDto contentPackageDto = new ContentPackageDto(album,
                                                        commonParam);
                                                contentPackageDtoList.add(contentPackageDto);
                                            }
                                        } else if (VipTpConstant.ContentType.VIDEO.getCode().equals(contentInfo.getCtype())) { // 视频
                                            VideoMysqlTable video = albumVideoAccess.getVideo(
                                                    contentInfo.getCid(), commonParam);
                                            if (video != null) {
                                                ContentPackageDto contentPackageDto = new ContentPackageDto(video,
                                                        commonParam);
                                                contentPackageDtoList.add(contentPackageDto);
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

        PageResponse<ContentPackageDto> response = new PageResponse<ContentPackageDto>();
        if (errorCode == null) {
            response.setTotalCount(totalCount);
            response.setCurrentIndex(page);
            response.setData(contentPackageDtoList);
        } else {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        }
        return response;
    }

    public List<BaseData> getPublisherList(Integer page, Integer pageSize, CommonParam commonParam) {
        String logPrefix = "getPublisherList" + commonParam.getMac() + "_" + commonParam.getUserId() + "_";
        List<BaseData> channelList = null;
        int start = 0; // 截取数据起始位置
        int end = 0; // 截取数组结束位置
        List<String> cpIds = this.facadeCacheDao.getChannelCacheDao().getLevidiPublisherIds();
        if (page == null || pageSize == null || cpIds == null || cpIds.size() == 0) {
            log.info(logPrefix + "get publisherid from cache return null");
            return channelList;
        }
        start = (page - 1) * pageSize;
        end = pageSize * page;
        if (start < 0 || start >= cpIds.size() || end <= 0) {
            log.info(logPrefix + "page error;totalSize:" + cpIds.size() + ";start:" + start + ";end:" + end);
            return channelList;
        }
        if (end > cpIds.size()) {
            end = cpIds.size();
        }
        List<String> ids = cpIds.subList(start, end);
        List<String> subscribedIds = userService.getUserSubscribeIds(
                UserTpConstant.FOLLOW_TYPE.LEVIDI, 1, Integer.MAX_VALUE, commonParam);
        channelList = this.getCpInfo(ids, subscribedIds, commonParam);
        return channelList;
    }

    /**
     * 获取cp信息
     * @param ids
     *            cp的id列表
     * @param subscribedIds
     *            已订阅的id列表
     * @param commonParam
     * @return
     */
    public List<BaseData> getCpInfo(List<String> ids, List<String> subscribedIds, CommonParam commonParam) {
        String logPrefix = "getCpInfo_mac_" + commonParam.getMac() + "_";
        List<BaseData> channelList = null;
        GenericDetailResponse detailResponse = videoService.getDetails(ids, (short) 0,
                (short) 0, commonParam);
        if (detailResponse == null || detailResponse.details == null || detailResponse.details.size() == 0) {
            log.info(logPrefix + "third party return is null");
            return channelList;
        }
        if (subscribedIds == null || subscribedIds.size() == 0) {
            log.info(logPrefix + "subscribed list is null!");
        }
        channelList = new ArrayList<BaseData>();
        for (ResultDocInfo docInfo : detailResponse.getDetails()) {
            if (docInfo == null) {
                continue;
            }
            ContentProviderAttribute cpAttr = docInfo.getContent_provider_attribute();
            if (cpAttr == null) {
                continue;
            }
            if (StringUtil.isBlank(cpAttr.getLetv_original_id())) {
                continue;
            }
            Channel channel = new Channel();
            channel.setIsSelected(ChannelConstants.CHANNEL_IS_UNSUBSCRIBED);
            if (subscribedIds != null && subscribedIds.size() > 0) {
                if (subscribedIds.contains(cpAttr.getLetv_original_id())) {
                    channel.setIsSelected(ChannelConstants.CHANNEL_IS_SUBSCRIBED);
                }
            }
            channel.setCpId(cpAttr.getChannel_id());

            // 订阅模块,cp详情作品库无法返回cp付费信息，由于产品要求eros付费故特殊处理
            if (ChannelConstants.CP_EROS_ID.equals(cpAttr.getLetv_original_id())) {
                channel.setIconType(IconConstants.ICON_TYPE_VIP);
            }
            channel.setCpId(cpAttr.getLetv_original_id());
            channel.setImg(getCpPic(cpAttr.getThumbnail_list()));
            channel.setName(cpAttr.getName());
            channel.setGlobalId(cpAttr.getLetv_original_id());
            channelList.add(JumpUtil
                    .bulidJumpObj(channel, DataConstant.DATA_TYPE_CONTENT_LIST, null, null, commonParam));
        }
        return channelList;
    }

    private boolean updateChannelDataRecDataList4Sdk(final String rcPageid, final CommonParam commonParam) {
        boolean result = false;

        if ((System.currentTimeMillis() - ChannelConstants.CHANNEL_DATA_REC_LIST_LE_LAST_UPDATE_TIME_MILLIS) > ChannelConstants.CHANNEL_DATA_REC_LIST_LE_UPDATE_INTERVAL_MILLIS) {
            if (ChannelConstants.CHANNEL_DATA_REC_LIST_LE_UPDATE_LOCK.tryLock()) {
                final String logPrefix = "updateChannelDataRecDataList4Sdk_" + commonParam.getMac() + "_"
                        + commonParam.getUserId();
                log.info(logPrefix + ": operation locked.");
                try {
                    new Thread() {
                        @Override
                        public void run() {
                            ChannelService.this.updateChannelDataRecDataList(rcPageid, logPrefix, commonParam);
                        };
                    }.start();

                    ChannelConstants.CHANNEL_DATA_REC_LIST_LE_LAST_UPDATE_TIME_MILLIS = System.currentTimeMillis();
                    result = true;
                } catch (Exception e) {
                    log.error(logPrefix + ": invoke error ", e);
                } finally {
                    ChannelConstants.CHANNEL_DATA_REC_LIST_LE_UPDATE_LOCK.unlock();
                    log.info(logPrefix + ": operation unlocked.");
                }
            }
        }
        return result;
    }

    private void updateChannelDataRecDataList(String rcPageid, String logPrefix, CommonParam commonParam) {
        long startTime = System.currentTimeMillis();
        for (String langcode : CommonConstants.LANG_CODES) {
            commonParam.setLangcode(langcode);
            PageResponse<Recommendation> recommendationList = recommendationService
                    .getMultiBlocks(rcPageid, null, null, null, null, null, DataConstant.GET_DATA_CATEGARY_TP_CACHE,
                            commonParam);
            List<ChannelData> channelDataList = null;
            if (recommendationList != null && !CollectionUtils.isEmpty(recommendationList.getData())) {
                channelDataList = new LinkedList<ChannelData>();
                Collection<Recommendation> recDatas = recommendationList.getData();
                for (Recommendation recData : recDatas) {
                    ChannelData channelData = new ChannelData();
                    channelData.setDataList(recData.getDataList());
                    channelData.setTitle(recData.getBlockName());
                    channelData.setArea(recData.getArea());
                    channelData.setBucket(recData.getBucket());
                    channelData.setBlockType(recData.getBlockType());
                    channelData.setReid(recData.getReid());
                    if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equals(commonParam.getTerminalApplication())) {
                        if (StringUtil.equals(ChannelConstants.LECOM_HOME_CATEGORY_BLOCKID, recData.getBlockId())) {
                            channelData.setUiPlateType(ChannelConstants.UI_PLATE_TYPE_4);
                        } else {
                            channelData.setUiPlateType(ChannelConstants.UI_PLATE_TYPE_5);
                        }
                    }
                    // filterChannelData4Sdk(size, channelData, dataTypeList);
                    // JumpUtil.buildJumpObjFromDb(channelData,
                    // channelData.getTitleDataType(), null, null, commonParam);
                    channelDataList.add(channelData);
                }
                ChannelConstants.updateChannelDataRec4LecomSDK(
                        genChannelDataRecListKey4LeconUS(rcPageid, langcode, commonParam), channelDataList);
            }
        }
        log.info(logPrefix + ": operation cost: " + (System.currentTimeMillis() - startTime));
    }

    private String genChannelDataRecListKey4LeconUS(String rcPageid, String langcode, CommonParam commonParam) {
        return new StringBuilder(ChannelConstants.CHANNEL_DATA_REC_LIST_MEMORY_KEY_PREFIX)
                .append(StringUtils.upperCase(commonParam.getTerminalApplication())).append("_")
                .append(StringUtils.upperCase(commonParam.getSalesArea())).append("_").append(rcPageid).append("_")
                .append(StringUtils.upperCase(langcode)).toString();
    }

    private ChannelData filterChannelData4Sdk(Integer size, List<String> dataTypeList, ChannelData channelData) {
        ChannelData targetChannelData = null;
        if (channelData != null) {
            targetChannelData = new ChannelData();
            boolean needFilterDataType = CollectionUtils.isEmpty(dataTypeList) ? false : true;
            List<BaseData> dataList = channelData.getDataList();
            List<BaseData> filteredDataList = null;
            if (!CollectionUtils.isEmpty(dataList)) {
                filteredDataList = new ArrayList<BaseData>();
                int currentSize = 0;
                for (BaseData baseData : dataList) {
                    if (baseData != null) {
                        if (!needFilterDataType || dataTypeList.contains(String.valueOf(baseData.getDataType()))) {
                            filteredDataList.add(baseData);
                            currentSize++;
                        }
                    }
                    if (currentSize >= size) {
                        break;
                    }
                }
                targetChannelData.setDataList(filteredDataList);
            }
        }

        return targetChannelData;
    }

    // 获取cp的图片信息
    @Deprecated
    private String getCpPic(List<Thumbnail> pics) {
        String pic = "";
        if (pics != null || pics.size() > 0) {
            pic = pics.get(pics.size() - 1).url;
        }
        return pic;
    }

    /**
     * 得到北美版收银台URL
     */
    public String getUsCashierUrl(CommonParam commonParam) {
        String url = this.facadeCacheDao.getChannelCacheDao().getUsCashierUrlDate(commonParam);
        if (StringUtil.isNotBlank(url)) {
            return url;
        }
        url = this.getUsCashierUrlForNet(commonParam);
        if (StringUtil.isNotBlank(url)) {
            this.facadeCacheDao.getChannelCacheDao().setUsCashierUrlDate(url);
        }
        return url;
    }

    private String getUsCashierUrlForNet(CommonParam commonParam) {
        GetUsUrlDataTpResponse mGetUsUrlDataTpResponse = this.facadeTpDao.getChannelTpDao().getUsUrlData();
        if (mGetUsUrlDataTpResponse != null && mGetUsUrlDataTpResponse.getData() != null) {
            return mGetUsUrlDataTpResponse.getData().getRenewUrl();
        }
        return null;
    }

}
