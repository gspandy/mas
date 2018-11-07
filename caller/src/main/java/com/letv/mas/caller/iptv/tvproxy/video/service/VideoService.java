package com.letv.mas.caller.iptv.tvproxy.video.service;

import com.letv.mas.caller.iptv.tvproxy.common.model.dto.IptvVideoCacheInfoDto;
import com.letv.mas.caller.iptv.tvproxy.channel.service.ChannelService;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.GetVideoReactionTpResponse.VideoReactionInfo.ReactionData;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.ValidateServiceTp;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.BaseResponse;
import com.letv.mas.caller.iptv.tvproxy.notification.QrCodeService;
import com.letv.mas.caller.iptv.tvproxy.video.constants.ChargeTypeConstants;
import com.letv.mas.caller.iptv.tvproxy.video.model.bean.bo.ResourceInfo;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoReactionDto.VideoReaction.ReactionDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.SportsLiveProgramDto.PROGRAM_TYPE;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.LiveTpDao;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoReactionDto.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.GetVideoReactionTpResponse.*;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.*;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.PageResponse;
import com.letv.mas.caller.iptv.tvproxy.apicommon.util.localcache.LocalCacheAnnotation;
import com.letv.mas.caller.iptv.tvproxy.apicommon.util.localcache.LocalCacheInterface;
import com.letv.mas.caller.iptv.tvproxy.apicommon.util.localcache.LocalCacheManager;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.constant.*;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ErrorCodeConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoAccess;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.StatRankWeekData;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.UserChildLockTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.TpCallBack;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.activity.ActivityTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.LiveTpConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.RecommendationTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response.RecommendationLevidiResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.TotalCountStatTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.CheckPlaylistTp;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.VideoMsgCodeConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.VideoTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.VideoTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.ValidateServiceTp.VodTryPlayInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.BossTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.LiveProgram;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.LiveRoomChannel;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import com.letv.mas.caller.iptv.tvproxy.live.LiveService;
import com.letv.mas.caller.iptv.tvproxy.notification.model.dto.QrCodeDto;
import com.letv.mas.caller.iptv.tvproxy.recommendation.RecommendationService;
import com.letv.mas.caller.iptv.tvproxy.user.UserConstants;
import com.letv.mas.caller.iptv.tvproxy.user.UserService;
import com.letv.mas.caller.iptv.tvproxy.user.model.dto.ChildLockDto;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.video.util.VideoUtil;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.PromotionDto;
import com.letv.mas.caller.iptv.tvproxy.vip.service.VipV2Service;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import serving.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service(value = "v2.VideoService")
public class VideoService extends BaseService {

    private final static Logger log = LoggerFactory.getLogger(VideoService.class);

    @Autowired
    LocalCacheManager localCacheManager;
    
    @Autowired
    RecommendationService recommendationService;

    @Autowired
    AlbumVideoAccess albumVideoAccess;

    @Autowired
    UserService userService;

    @Autowired
    LiveService liveService;
    
    @Autowired
    VipV2Service vipV2Service;
    
    @Autowired
    ChannelService channelService;
    
    @Autowired
    PlayService playService;

    @Autowired
    QrCodeService qrCodeService;
    
    /**
     * 专辑详情
     * @param albumId
     *            专辑ID
     * @param globalId
     *            全局专辑ID
     * @param seriesPaged
     *            是否分页
     * @param commonParam
     *            接口通用参数
     * @param isThirdParty
     *            是否第三方
     * @param supportDV
     *            是否支持杜比视界
     * @return Response
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Response<AlbumDto> getAlbumDetail(Long albumId, String globalId, Integer seriesPaged,
                                             CommonParam commonParam, boolean isThirdParty, boolean supportDV) {
        String logPrefix = "getAlbumDetail_" + albumId + "_" + commonParam.getMac();

        AlbumDto album = null;
        Response res = new Response();
        if (CommonConstants.TERMINAL_APPLICATION_LEVIDI.equals(commonParam.getTerminalApplication())) {
            if ("show".equals(commonParam.getBizCode())) {
                Set<String> ids = new TreeSet<String>();
                ids.add("205_335807");
                ids.add("205_335810");
                res.setData(this.getShowDetail(new ArrayList(ids), commonParam));
            } else {
                album = this.getWebsiteAlbumDetail(globalId, commonParam);
                if (album != null) {
                    res.setData(album);
                }
            }
            return res;
        }
        AlbumMysqlTable albumMysql = albumVideoAccess.getAlbum(albumId, commonParam);
        boolean albumCharge = false;
        if (albumMysql != null) {
            album = new AlbumDto();

            album.setAlbumId(albumMysql.getId() != null ? albumMysql.getId().toString() : "");
            album.setCategoryId(albumMysql.getCategory());

            // 首先判断专辑收费属性，因为选集列表是否出现角标，条件是：专辑和视频都收费、播放平台有TV、收费平台也有TV
            albumCharge = albumMysql.isPay(commonParam.getP_devType());// 专辑付费

            album.setPlayStatus(albumMysql.getPlay_status());
            album.setName(albumMysql.getName_cn());
            album.setDescription(albumMysql.getDescription());
            album.setPositive(albumMysql.getAlbum_attr() != null && albumMysql.getAlbum_attr() == 1);
            album.setAlbumTypeId(albumMysql.getAlbum_type());
            album.setAlbumTypeName(MessageUtils.getMessage(getAlbumTypeNameTextKey(albumMysql.getAlbum_type()),
                    commonParam.getLangcode()));
            album.setAreaName(albumMysql.getArea_name());

            // TotalCountStatTpResponse statResp =
            // this.facadeTpDao.getStatTpDao().getTotalCountStat(albumId);
            TotalCountStatTpResponse statResp = this.getAlbumTotalCountStat(albumId, commonParam);
            if (statResp != null) {
                album.setVv(statResp.getPlist_play_count());
                album.setCommentCnt(statResp.getPcomm_count());
                // 详情页改版
                if (StringUtil.isNotBlank(statResp.getPlist_score())) {
                    try {
                        Float score = Float.parseFloat(statResp.getPlist_score());
                        if (null != score) {
                            album.setScore(score);
                            albumMysql.setScore(score);
                        }
                    } catch (Exception e) {
                    }
                }
            }

            // 获取专辑排行榜名次
            StatRankWeekData statRankWeekData = this.getWeeklyRankInfo(String.valueOf(albumId), album.getCategoryId(),
                    commonParam.isFlush());
            if (null != statRankWeekData) {
                album.setRank(statRankWeekData.getRank());
            }

            album.setPlayPlatform(albumMysql.getPlay_platform());
            if (ProductLineConstants.WCODE.LETV_HK.equalsIgnoreCase(commonParam.getWcode())) {
                album.setPlayPlatform(albumMysql.getPlay_platform() + ",420009");
            }

            String dp = albumMysql.getDownload_platform();
            if (dp != null && dp.length() > 0) {
                Map<String, String> dpm = new HashMap<String, String>();
                List<String> platList = Arrays.asList(dp.split(","));
                for (CommonConstants.DOWNLAOD_PLATFORM plat : CommonConstants.DOWNLAOD_PLATFORM.values()) {
                    if (platList.contains(plat.getMs_code())) {
                        dpm.put(plat.getName_code(), plat.getNameByLangcode(commonParam.getLangcode()));
                    }
                }

                album.setDownloadPlatform(dpm);
            }

            // 设置专辑信息
            this.setAlbumStyle(album, albumMysql, albumMysql.getVarietyShow(), commonParam);

            /*
             * 2015-07-05 更新专辑详情的码流逻辑，专辑详情的码流策略:
             * 如果存在预告片和正片时，详情页中码流应显示所有正片码流的集合
             * 如果只存在正片时，详情页中码流应显示所有正片码流的集合
             * 如果只存在预告片时，详情页中码流应显示所有预告片码流的集合
             */
            Set<String> positiveSeriesStreamSet = new HashSet<String>(); // 正片码流集合
            Set<String> preSeriesStreamSet = new HashSet<String>(); // 预告码流集合

            if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) {
                album = this.getLecomAlbumDetail(album, albumMysql, positiveSeriesStreamSet, preSeriesStreamSet,
                        seriesPaged, logPrefix, commonParam);
            } else {
                albumCharge = albumCharge || this.payChannelIsCharge(albumMysql.getCategory(), logPrefix);
                album = this.getLetvAlbumDetail(album, albumMysql, positiveSeriesStreamSet, preSeriesStreamSet,
                        albumCharge, logPrefix, commonParam);
            }

            // 设置码流
            List<Stream> streamList = null;
            String isYuanXian = String.valueOf(albumMysql.getIsyuanxian());
            if (!"1".equals(isYuanXian) && albumCharge) {
                isYuanXian = "1";
            }
            if (!CollectionUtils.isEmpty(positiveSeriesStreamSet)) {
                // 只要存在正片，详情页中码流应显示所有正片码流的集合
                streamList = this.sortAndFilterStreamCode(positiveSeriesStreamSet, isYuanXian,
                        albumMysql.getCategory(), commonParam, isThirdParty);
                log.info(logPrefix + ": generate streams from positive series");
            } else if (!CollectionUtils.isEmpty(preSeriesStreamSet)) {
                // 如果只存在预告片时，详情页中码流应显示所有预告片码流的集合
                streamList = this.sortAndFilterStreamCode(preSeriesStreamSet, isYuanXian, albumMysql.getCategory(),
                        commonParam, isThirdParty);
                log.info(logPrefix + ": generate streams from pre series");
            }
            if (CollectionUtils.isEmpty(streamList)) {
                // 如果经过上面的处理，还是没有码流，就设置默认码流
                String playStreams = albumMysql.getPositive_play_streams();
                if (StringUtils.isEmpty(playStreams)) {
                    playStreams = albumMysql.getPlay_streams();
                }
                streamList = this.sortAndFilterStreamCode(playStreams, albumMysql.getCategory(),
                        commonParam.getTerminalBrand(), commonParam.getLangcode(), isYuanXian, commonParam);
                log.info(logPrefix + ": generate streams from default");
            }
            // ====================================非电影频道过滤1080P(B)=====================================//
            VideoUtil.playStreamFilter(streamList, albumMysql.getCategory(), commonParam);
            // ====================================非电影频道过滤1080P(E)=====================================//
            album.setStreams(streamList);

            if (albumMysql != null) {// 专辑收费或者付费频道收费，专辑详情页展示会员专享
                if (albumCharge) {
                    album.setCharge(true);
                } else {
                    album.setCharge(false);
                }
                // for tvod icon type
                album.setIfCharge(album.getCharge() ? "1" : "0");
                Integer chargeType = JumpUtil.getChargeType(albumMysql.getPay_platform(), commonParam);
                if (null == chargeType) {
                    chargeType = JumpUtil.getChargeType(albumMysql.getPay_platform());
                }
                album.setChargeType(chargeType);
                album.setIconType(JumpUtil.getIconType(null, album.getIfCharge(), album.getChargeType(), null, null));
            }
            res.setData(album);
        } else {
            res.setErrCode(VideoConstants.DETAIL_PAGE_NULL);
            String errorMsg = MessageUtils.getMessage(VideoConstants.DETAIL_PAGE_NULL, commonParam.getLangcode());
            String tipsMsg = null;
            // 通用版电话不一样
            if (ProductLineConstants.LETV_COMMON.equals(commonParam.getTerminalApplication())) {
                tipsMsg = MessageUtils.getMessage(VideoConstants.VIDEO_ALBUM_DETAIL_ERROR_TIPS_COMMON,
                        commonParam.getLangcode());
            } else {
                tipsMsg = MessageUtils.getMessage(VideoConstants.VIDEO_ALBUM_DETAIL_ERROR_TIPS,
                        commonParam.getLangcode());
            }

            if (!StringUtil.isBlank(tipsMsg)) {
                res.setErrMsg(errorMsg + ";" + StringUtils.trimToEmpty(tipsMsg));
            } else {
                res.setErrMsg(errorMsg);
            }
            res.setResultStatus(0);
            log.error("[mac]" + commonParam.getMac() + "[url][/video/album/detailandseries/get][errcode]["
                    + VideoConstants.DETAIL_PAGE_NULL + "]");
        }

        return res;
    }

    private boolean isFreeTimeLimit(Long pid, CommonParam commonParam, Boolean isUseCache) {
        if (pid == null) {
            return false;
        }
        String albumId = String.valueOf(pid);
        String pids = null;
        if (isUseCache) {
            pids = this.facadeCacheDao.getVideoCacheDao().getFreeTempPids(commonParam);
        }
        if (StringUtil.isBlank(pids)) {
            pids = this.facadeTpDao.getCmsTpDao().getFreeTimeLimitPids(commonParam);
            if (isUseCache && StringUtil.isNotBlank(pids)) {
                this.facadeCacheDao.getVideoCacheDao().setFreeTempPids(pids, commonParam);
            }
        }

        if (StringUtil.isBlank(pids)) {
            return false;
        }
        String[] pidArray = pids.split(",");
        if (pidArray == null || pidArray.length == 0) {
            return false;
        }
        for (String pidStr : pidArray) {
            if (pidStr != null && pidStr.equals(albumId)) {
                return true;
            }
        }
        return false;
    }

    private AlbumDto getLecomAlbumDetail(AlbumDto album, AlbumMysqlTable albumMysql,
            Set<String> positiveSeriesStreamSet, Set<String> preSeriesStreamSet, Integer seriesPaged, String logPrefix,
            CommonParam commonParam) {
        String[] picKeys = new String[] { "1440*810" };
        album.setImg(albumMysql.getPic(picKeys));

        Set<VideoDto> posSerieses = new TreeSet<VideoDto>(); // 剧集列表（正片）
        List<VideoDto> preSeries = new ArrayList<VideoDto>(); // 预告
        Set<VideoDto> posAddSeries = new TreeSet<VideoDto>(); // 剧集列表扩展（预告、抢先看等，正片剧集的补充）
        List<VideoDto> attachingSeries = new ArrayList<VideoDto>(); // 周边视频

        // 正片专辑
        List<VideoMysqlTable> videoList = null;
        if (seriesPaged == null || seriesPaged != VideoConstants.ALBUM_VIDEO_SERIES_IS_PAGED) {
            // 不分页
            if (album.getPositive() != null && album.getPositive() == true) {
                videoList = this.getLecomAlbumSeriesList(albumMysql,
                        VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_POSITIVE, album.getVarietyShow(), 0,
                        Integer.MAX_VALUE, commonParam);
                if (!CollectionUtils.isEmpty(videoList)) {
                    for (VideoMysqlTable vi : videoList) {
                        setVideoInfo4US(true, album.getVarietyShow(), albumMysql.getCategory(), vi, posSerieses,
                                preSeries, posAddSeries, attachingSeries, positiveSeriesStreamSet, preSeriesStreamSet);
                    }
                }

                videoList = this.getLecomAlbumSeriesList(albumMysql,
                        VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_ATTACHING, album.getVarietyShow(), 0,
                        Integer.MAX_VALUE, commonParam);
                if (!CollectionUtils.isEmpty(videoList)) {
                    for (VideoMysqlTable vi : videoList) {
                        setVideoInfo4US(true, album.getVarietyShow(), albumMysql.getCategory(), vi, posSerieses,
                                preSeries, posAddSeries, attachingSeries, positiveSeriesStreamSet, preSeriesStreamSet);
                    }
                }
            } else {
                // 预告专辑
                videoList = this.getLecomAlbumSeriesList(albumMysql,
                        VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_ALL, album.getVarietyShow(), 0,
                        Integer.MAX_VALUE, commonParam);
                if (!CollectionUtils.isEmpty(videoList)) {
                    for (VideoMysqlTable vi : videoList) {
                        setVideoInfo4US(false, album.getVarietyShow(), albumMysql.getCategory(), vi, posSerieses,
                                preSeries, posAddSeries, attachingSeries, positiveSeriesStreamSet, preSeriesStreamSet);
                    }
                }
            }
            album.setPositiveSeries(posSerieses);
            album.setPreSeries(preSeries);
            album.setPositiveAddSeries(posAddSeries);
            album.setAttachingSeries(attachingSeries);
        } else {
            // 分页
            if (album.getPositive() != null && album.getPositive() == true) {
                // 正片
                fillLecomAlbumPagedSeries(album, albumMysql, VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_POSITIVE,
                        positiveSeriesStreamSet, preSeriesStreamSet, commonParam);

                // 周边视频
                fillLecomAlbumPagedSeries(album, albumMysql,
                        VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_ATTACHING, positiveSeriesStreamSet,
                        preSeriesStreamSet, commonParam);
            } else {
                fillLecomAlbumPagedSeries(album, albumMysql, VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_POSITIVE,
                        positiveSeriesStreamSet, preSeriesStreamSet, commonParam);
            }
        }

        // 相关系列
        if (StringUtils.isNotEmpty(albumMysql.getRelationAlbumId())) {
            List<BaseData> relatedSeries = new ArrayList<BaseData>(); // 相关系列，取媒资关联专辑字段，内容以专辑形式呈现
            String[] relationAlbumIds = StringUtils.split(albumMysql.getRelationAlbumId(), ",");
            if (relationAlbumIds != null) {
                for (String relationAlbumId : relationAlbumIds) {
                    AlbumDto relationAlbum = null;
                    AlbumMysqlTable relationAlbumMysql = albumVideoAccess.getAlbum(
                            StringUtil.toLong(relationAlbumId, 0L), commonParam);
                    if (relationAlbumMysql != null) {
                        relationAlbum = new AlbumDto();
                        // album.setTvCopyright(1);
                        relationAlbum.setName(relationAlbumMysql.getName_cn());
                        relationAlbum.setSubName(relationAlbumMysql.getSub_title());
                        relationAlbum.setImg(relationAlbumMysql.getPic(400, 300));
                        relationAlbum.setImgSize("400*300");
                        relationAlbum.setCategoryId(relationAlbumMysql.getCategory());
                        relationAlbum.setSubCategoryId(relationAlbumMysql.getSub_category());
                        if (!StringUtil.isBlank(relationAlbumMysql.getPay_platform())
                                && relationAlbumMysql.getPay_platform().indexOf("141007") > -1) {
                            relationAlbum.setIfCharge("1");
                        } else {
                            relationAlbum.setIfCharge("0");
                        }
                        // this.setNowEpisode(album, albumMysql,
                        // commonParam);

                        relatedSeries.add(JumpUtil.bulidJumpObj(relationAlbum, DataConstant.DATA_TYPE_ALBUM, null,
                                null, commonParam));
                    }
                }
            }

            album.setRelatedSeries(relatedSeries);

        }
        // 相关推荐
        List<BaseData> recommendationSeries = new ArrayList<BaseData>(); // 相关推荐
        album.setRecommendationSeries(recommendationSeries);
        Collection<BaseData> relation_data = this.getRelation(album.getCategoryId(),
                StringUtil.toInteger(album.getAlbumId(), 0), commonParam);
        if (relation_data != null) {
            recommendationSeries.addAll(relation_data);
        }
        // 鉴权
        validateAlbumPlayAuth4Lecom(album, albumMysql, null, logPrefix, commonParam);

        // 儿童锁
        UserChildLockTable childLockTable = userService.getUserChildLockFromCache(commonParam);
        ChildLockDto childLockDto = userService.parseUserChildLockTable(
                UserConstants.USER_CHILD_LOCK_CHECK_ACTION_TYPE_CHECK_STATUS, childLockTable);
        album.setChildLock(childLockDto);

        return album;
    }

    private void fillLecomAlbumPagedSeries(AlbumDto album, AlbumMysqlTable albumMysql, int sourceType,
            Set<String> positiveSeriesStreamSet, Set<String> preSeriesStreamSet, CommonParam commonParam) {

        boolean albumPositive = albumMysql.getAlbum_attr() != null && albumMysql.getAlbum_attr() == 1;
        int categoryId = MmsTpConstant.MMS_CATEGARY_ALL;
        if (albumMysql.getCategory() != null) {
            categoryId = albumMysql.getCategory();
        }

        // 取数据
        AlbumSeriesPlayListPageDto seriesPage = new AlbumSeriesPlayListPageDto();
        seriesPage.setAlbumId(String.valueOf(albumMysql.getId()));
        seriesPage.setAlbumPositive(albumPositive ? 1 : 0);
        seriesPage.setCurPage(1);
        seriesPage.setOrderType(VideoTpConstant.VIDEO_MMS_SERIES_ORDER_ASC);
        seriesPage.setPageSize(VideoTpConstant.ALBUM_SERIES_PAGE_SIZE);
        seriesPage.setStype(sourceType);
        seriesPage.setVarietyShow(album.getVarietyShow());
        List<VideoMysqlTable> videoList = getLecomAlbumSeriesPage(albumMysql.getId(), categoryId, false, seriesPage,
                commonParam);

        // 清洗、组装数据
        if (!CollectionUtils.isEmpty(videoList)) {
            Collection<BaseData> series = new ArrayList<BaseData>();
            if (albumPositive) {
                switch (sourceType) {
                case VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_POSITIVE:
                    // 正片直接封装
                    for (VideoMysqlTable vi : videoList) {
                        VideoDto video = parseLecomAlbumSeriesVideo(vi);
                        if (video != null) {
                            series.add(video);
                            this.addVideoStreamToSet(vi.getPlay_streams(), positiveSeriesStreamSet);
                        }
                    }
                    seriesPage.setData(series);
                    album.setPositiveSeriesPage(seriesPage);
                    break;
                case VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_ATTACHING:
                    // 周边视频有区分
                    switch (categoryId) {
                    case MmsTpConstant.MMS_CATEGARY_FILM:
                        for (VideoMysqlTable vi : videoList) {
                            if (vi != null) {
                                VideoDto video = parseLecomAlbumSeriesVideo(vi);
                                if (video != null) {
                                    series.add(video);
                                    this.addVideoStreamToSet(vi.getPlay_streams(), preSeriesStreamSet);
                                }
                            }
                        }
                        seriesPage.setData(series);
                        album.setAttachingSeriesPage(seriesPage);
                        break;
                    case MmsTpConstant.MMS_CATEGARY_TV:
                    case MmsTpConstant.MMS_CATEGARY_VARIETY:
                    case MmsTpConstant.MMS_CATEGARY_CARTOON:
                    case MmsTpConstant.MMS_CATEGARY_DFILM:
                    case MmsTpConstant.MMS_CATEGARY_EDUCATION:
                        // 电视剧、综艺、动漫、纪录片、教育频道，周边视频取全部非正片视频（但不包含预告），故这里不做分页，取所有非正片视频后再做筛选
                        for (VideoMysqlTable vi : videoList) {
                            if (vi != null && vi.getVideo_type() != null
                                    && MmsTpConstant.MMS_VIDEO_TYPE_YU_GAO_PIAN != vi.getVideo_type()) {
                                VideoDto video = parseLecomAlbumSeriesVideo(vi);
                                if (video != null) {
                                    series.add(video);
                                    this.addVideoStreamToSet(vi.getPlay_streams(), preSeriesStreamSet);
                                }
                            }
                        }
                        seriesPage.setData(series);
                        album.setAttachingSeriesPage(seriesPage);
                        break;
                    default:
                        // 其他频道无周边视频
                        break;
                    }
                    break;
                default:
                    break;
                }
            } else {
                // 非正片剧集，只有选集视频
                switch (sourceType) {
                case VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_POSITIVE:
                    for (VideoMysqlTable vi : videoList) {
                        VideoDto video = parseLecomAlbumSeriesVideo(vi);
                        if (video != null) {
                            series.add(video);
                            this.addVideoStreamToSet(vi.getPlay_streams(), positiveSeriesStreamSet);
                        }
                    }
                    seriesPage.setData(series);
                    album.setPositiveSeriesPage(seriesPage);
                    break;
                default:
                    break;
                }
            }
        }
    }

    /**
     * 获取剧集列表分页数据最底层实现方法；只负责取数据（什么场景下取什么数据）；
     * 这里没有直接返回AlbumSeriesPlayListPageDto，而是返回List<VideoMysqlTable>，
     * 是因该方法供专辑详情页接口和下拉列表接口公用，其中专辑详情页接口还需获取码流列表，故这里做不完整处理，
     * 但需要在AlbumSeriesPlayListPageDto填充非剧集列表的其他属性，以便接口调用者使用
     * @param albumId
     * @param categoryId
     * @param forcePage
     *            是否强制分页；专辑详情页因特殊需求，不能强制分页，而播放下拉列表，可以强制分页
     * @param forcePage
     * @param seriesPage
     * @param commonParam
     * @return
     */
    private List<VideoMysqlTable> getLecomAlbumSeriesPage(Long albumId, Integer categoryId, boolean forcePage,
            AlbumSeriesPlayListPageDto seriesPage, CommonParam commonParam) {
        List<VideoMysqlTable> iptvVideoList = null;

        if (categoryId == null) {
            categoryId = MmsTpConstant.MMS_CATEGARY_ALL;
        }
        Integer totalNum = seriesPage.getTotalNum();

        if (seriesPage.getAlbumPositive() != null && seriesPage.getAlbumPositive() == 1) {
            switch (seriesPage.getStype()) { // 先按请求资源类型来分
            case VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_POSITIVE:
                switch (categoryId) {
                case MmsTpConstant.MMS_CATEGARY_FILM:
                case MmsTpConstant.MMS_CATEGARY_TV:
                case MmsTpConstant.MMS_CATEGARY_VARIETY:
                case MmsTpConstant.MMS_CATEGARY_CARTOON:
                case MmsTpConstant.MMS_CATEGARY_DFILM:
                case MmsTpConstant.MMS_CATEGARY_EDUCATION:
                    // 电影、电视剧、综艺、动漫、纪录片、教育频道，正片剧集去全部正片视频，正序
                    iptvVideoList = albumVideoAccess.getVideoList(albumId,
                            VideoTpConstant.QUERY_TYPE_POSITIVE, seriesPage.getOrderType(), seriesPage.getPageSize(),
                            seriesPage.getCurPage(), commonParam);
                    if (totalNum == null) {
                        // 如果之前不知道totalNum，这里补全
                        totalNum = albumVideoAccess.getVideoTotalNum(albumId,
                                VideoTpConstant.QUERY_TYPE_POSITIVE, commonParam);
                        if (totalNum != null && totalNum > 0) {
                            seriesPage.setTotalPage((totalNum / seriesPage.getPageSize()) + 1);
                        }
                        seriesPage.setTotalNum(totalNum);
                    }
                    break;
                default:
                    // 音乐频道或其他频道，正片取全部数据，正序
                    iptvVideoList = albumVideoAccess.getVideoList(albumId,
                            VideoTpConstant.QUERY_TYPE_ALL, seriesPage.getOrderType(), seriesPage.getPageSize(),
                            seriesPage.getCurPage(), commonParam);
                    if (totalNum == null) {
                        // 如果之前不知道totalNum，这里补全
                        totalNum = albumVideoAccess.getVideoTotalNum(albumId,
                                VideoTpConstant.QUERY_TYPE_ALL, commonParam);
                        if (totalNum != null && totalNum > 0) {
                            seriesPage.setTotalPage((totalNum / seriesPage.getPageSize()) + 1);
                        }
                        seriesPage.setTotalNum(totalNum);
                    }
                    break;
                }
                break;
            case VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_ATTACHING:
                switch (categoryId) {
                case MmsTpConstant.MMS_CATEGARY_FILM:
                    iptvVideoList = albumVideoAccess.getVideoList(albumId,
                            VideoTpConstant.QUERY_TYPE_NON_POSITIVE, seriesPage.getOrderType(),
                            seriesPage.getPageSize(), seriesPage.getCurPage(), commonParam);
                    if (totalNum == null) {
                        // 如果之前不知道totalNum，这里补全
                        totalNum = albumVideoAccess.getVideoTotalNum(albumId,
                                VideoTpConstant.QUERY_TYPE_NON_POSITIVE, commonParam);
                        if (totalNum != null && totalNum > 0) {
                            seriesPage.setTotalPage((totalNum / seriesPage.getPageSize()) + 1);
                        }
                        seriesPage.setTotalNum(totalNum);
                    }
                    break;
                case MmsTpConstant.MMS_CATEGARY_TV:
                case MmsTpConstant.MMS_CATEGARY_VARIETY:
                case MmsTpConstant.MMS_CATEGARY_CARTOON:
                case MmsTpConstant.MMS_CATEGARY_DFILM:
                case MmsTpConstant.MMS_CATEGARY_EDUCATION:
                    if (forcePage) {
                        // 播放下拉，强制分页
                        iptvVideoList = albumVideoAccess.getVideoList(albumId,
                                VideoTpConstant.QUERY_TYPE_NON_POSITIVE, seriesPage.getOrderType(),
                                seriesPage.getPageSize(), seriesPage.getCurPage(), commonParam);
                        if (totalNum == null) {
                            // 如果之前不知道totalNum，这里补全
                            totalNum = albumVideoAccess.getVideoTotalNum(albumId,
                                    VideoTpConstant.QUERY_TYPE_NON_POSITIVE, commonParam);
                            if (totalNum != null && totalNum > 0) {
                                seriesPage.setTotalPage((totalNum / seriesPage.getPageSize()) + 1);
                            }
                            seriesPage.setTotalNum(totalNum);
                        }
                    } else {
                        // 专辑详情页，因特殊需求，不强制分页--电视剧、综艺、动漫、纪录片、教育频道，周边视频取全部非正片视频（但不包含预告），故这里不做分页，只在请求第一页时取所有非正片视频后再做筛选
                        if (seriesPage.getCurPage() != null && seriesPage.getCurPage() == 1) {
                            iptvVideoList = albumVideoAccess.getVideoRange(albumId,
                                    VideoTpConstant.QUERY_TYPE_NON_POSITIVE, seriesPage.getOrderType(), 0,
                                    Integer.MAX_VALUE, commonParam, 0);
                            // pageSize需要解析后才能给出
                            if (totalNum == null) {
                                // 如果之前不知道totalNum，这里补全
                                totalNum = albumVideoAccess.getVideoTotalNum(albumId,
                                        VideoTpConstant.QUERY_TYPE_NON_POSITIVE, commonParam);
                                seriesPage.setTotalNum(totalNum);
                            }
                            seriesPage.setTotalPage(1);
                        }
                    }
                    break;
                default:
                    // 其他频道无周边视频
                    break;
                }
                break;
            default:
                break;
            }
        } else {
            // 非正片剧集，只有选集视频
            switch (seriesPage.getStype()) {
            case VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_POSITIVE:
                iptvVideoList = albumVideoAccess.getVideoList(albumId,
                        VideoTpConstant.QUERY_TYPE_ALL, seriesPage.getOrderType(), seriesPage.getPageSize(),
                        seriesPage.getCurPage(), commonParam);
                if (totalNum == null) {
                    // 如果之前不知道totalNum，这里补全
                    totalNum = albumVideoAccess.getVideoTotalNum(albumId,
                            VideoTpConstant.QUERY_TYPE_ALL, commonParam);
                    if (totalNum != null && totalNum > 0) {
                        seriesPage.setTotalPage((totalNum / seriesPage.getPageSize()) + 1);
                    }
                    seriesPage.setTotalNum(totalNum);
                }
                break;
            default:
                break;
            }
        }

        return iptvVideoList;
    }

    private AlbumDto getLetvAlbumDetail(AlbumDto album, AlbumMysqlTable albumMysql,
            Set<String> positiveSeriesStreamSet, Set<String> preSeriesStreamSet, boolean albumCharge, String logPrefix,
            CommonParam commonParam) {
        String[] picKeys = new String[] { MmsTpConstant.MMS_ALBUM_DYNAMIC_GRAPH_FIELD_ANME, "600*800", "300*400" };
        album.setImg(albumMysql.getPic(picKeys));
        // 详情页改版
        album.setBigImg(albumMysql.getPicNew(970, 300));

        // 相关明星
        Integer category = albumMysql.getCategory();
        List<VideoDto> segments = new ArrayList<VideoDto>(); // 片段，对应详情页“片段”tab标签
        List<VideoDto> attachingSeries = new ArrayList<VideoDto>(); // 周边看点
                                                                    // 取该专辑内非正片视频数据，发布时间逆序

        List<VideoMysqlTable> iptvVideoList = this.getLetvAlbumSeriesList(albumMysql, albumMysql.getId(),
                commonParam.getBroadcastId(), 0, Integer.MAX_VALUE, commonParam);
        if (!CollectionUtils.isEmpty(iptvVideoList)) {
            Set<VideoDto> posSerieses = new TreeSet<VideoDto>(); // 剧集列表（正片）
            List<VideoDto> preSeries = new ArrayList<VideoDto>(); // 预告
            Set<VideoDto> posAddSeries = new TreeSet<VideoDto>(); // 剧集列表扩展（预告、抢先看等，正片剧集的补充）
            for (VideoMysqlTable vi : iptvVideoList) {
                this.setVideoInfo(vi, category, albumMysql.getVarietyShow(), album, albumMysql, posSerieses, preSeries,
                        posAddSeries, positiveSeriesStreamSet, preSeriesStreamSet, segments, attachingSeries,
                        commonParam);
            }
            for (VideoDto videoDto : posSerieses) {// 判断视频是否需要展示角标
                if (albumCharge
                        && VideoCommonUtil.isCharge(videoDto.getPayPlatForm(), videoDto.getPlayPlatform(),
                                commonParam.getP_devType())
                        && MmsDataUtil.isSupportPayPlatform(videoDto.getPayPlatForm(), commonParam.getP_devType())) {
                    // 专辑收费且视频收费
                    videoDto.setIfCharge("1");
                } else {
                    videoDto.setIfCharge("0");
                }
                // for tvod icon type
                Integer chargeType = JumpUtil.getChargeType(videoDto.getPayPlatForm(), commonParam);
                if (null == chargeType) {
                    chargeType = JumpUtil.getChargeType(videoDto.getPayPlatForm());
                }
                videoDto.setChargeType(chargeType);
                videoDto.setIconType(JumpUtil.getIconType(null, videoDto.getIfCharge(), videoDto.getChargeType(), null,
                        null));
                if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                    videoDto.setChargeInfos(JumpUtil.genChargeInfos(videoDto.getPayPlatForm()));
                }
            }
            if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                boolean isFreeTimeLimit = isFreeTimeLimit(albumMysql.getId(), commonParam, true);
                album.setChargeInfos(JumpUtil.genChargeInfos(albumMysql.getPay_platform(), null, null, null,
                        isFreeTimeLimit ? 1 : null, commonParam));
            }
            album.setPositiveSeries(posSerieses);
            if (CollectionUtils.isEmpty(posSerieses)) {
                // if no positive video list then set album positive
                // false.
                album.setPositive(false);
            }
            album.setPositiveAddSeries(posAddSeries);

            // 因为去所有剧集列表是按proder正序排列，而电视剧和卡通的预告和片花需要倒序展示，所以这里特殊处理一下，将preSeries、segments倒过来
            if (category != null
                    && (VideoConstants.Category.TV == category || VideoConstants.Category.CARTOON == category)) {
                // 电视剧、卡通 --
                // 正片和扩展剧集按视频在专辑中的顺序升序排列，顺序相同的再按照创建时间降序排列；预告和片段按以上顺序整理后翻转降序；
                Collections.reverse(preSeries);
                Collections.reverse(segments);
            }

            album.setAttachingSeries(attachingSeries);
            album.setPreSeries(preSeries);
            album.setSegments(segments);
        }

        if (album.getPositive() != null && album.getPositive() && category != null
                && category == VideoConstants.Category.VARIETY && CollectionUtils.isEmpty(segments)) {
            if (StringUtils.isNotEmpty(albumMysql.getRelationId())) {
                List<VideoMysqlTable> seg = this.getLetvAlbumSeriesList(albumMysql,
                        Long.valueOf(albumMysql.getRelationId()), commonParam.getBroadcastId(), 0,
                        VideoConstants.SCREEN_SIZE * 3, commonParam);// 片段，表示正片关联的花絮
                if (!CollectionUtils.isEmpty(seg)) {
                    for (VideoMysqlTable v : seg) {
                        VideoDto vs = new VideoDto();
                        vs.setVideoId(v.getId() != null ? v.getId().toString() : "");
                        vs.setCategoryId(v.getCategory());
                        vs.setAlbumId(v.getPid() != null ? v.getPid().toString() : "");
                        vs.setPositive(this.isPositive(v.getVideo_type(), v.getCategory()));
                        vs.setVideoTypeId(v.getVideo_type());

                        vs.setName(v.getName_cn());
                        vs.setSubName(v.getSub_title());
                        vs.setImg(v.getPic(400, 300));
                        vs.setImgSize("400*300");

                        segments.add(vs);
                    }
                    album.setSegments(segments);
                }
            }
        } else {
            album.setSegments(segments);
        }
        // old dp
        // album.setRelation(this.getRelation(category, album, commonParam));
        // new dp
        album.setRelation(this.getRelation(albumMysql, album, commonParam));
        album.setActorInfo(this.getActorsById(albumMysql, commonParam.getLangcode()));

        return album;
    }

    public AlbumDto convertToAlbumDto(ResultDocInfo docInfo, CommonParam commonParam) {
        AlbumDto albumDto = null;
        if (docInfo == null) {
            return albumDto;
        }
        AlbumAttribute album = docInfo.getAlbum_attribute();
        if (album != null) {
            albumDto = new AlbumDto();
            albumDto.setSrc(Integer.valueOf(album.getSrc()));
            albumDto.setAlbumId(docInfo.getId());
            albumDto.setGlobalId(docInfo.getLetv_original_id());
            albumDto.setCategoryId(StringUtil.isBlank(docInfo.getCategory()) ? null : Integer.valueOf(docInfo
                    .getCategory()));
            albumDto.setSubCategoryName(album.getSub_category_name());
            albumDto.setImg(getPic4Sarrs(album.getImages(), 1));
            albumDto.setBigImg(getPic4Sarrs(album.getImages(), 2));
            albumDto.setName(album.getName());
            albumDto.setSubName(album.getSub_name());
            albumDto.setDescription(album.getShort_desc());
            albumDto.setDuration(album.getDuration());
            albumDto.setPositive(this.isSarrsPositive(album.getVideo_type() + ""));
            albumDto.setAlbumTypeId(album.getVideo_type());
            albumDto.setGlobalCid(docInfo.getCategory());
            // TODO老版本levidi兼容，2016.10.11
            if (CommonConstants.TERMINAL_APPLICATION_LEVIDI.equals(commonParam.getTerminalApplication())
                    && commonParam.getAppCode() != null && Integer.valueOf(commonParam.getAppCode()) < 400) {
                albumDto.setCharge(false);
            }
            albumDto.setAreaName(album.getArea_name());
            String releaseDate = TimeUtil.getDateStringFromLong(album.getRelease_date(), TimeUtil.SHORT_DATE_FORMAT);
            if (!StringUtils.isBlank(releaseDate) && releaseDate.length() >= 4) {
                releaseDate = releaseDate.substring(0, 4);
            }
            albumDto.setReleaseDate(releaseDate);
            albumDto.setVv(album.getPlay_count() + "");
            albumDto.setCommentCnt(null);
            albumDto.setPlayPlatform(getSarrsPlayPlatForm(album.getPush_flag()));
            albumDto.setPositiveSeries(this.getSarrsVideoList(docInfo, albumDto.getPositive()));
            albumDto.setIfCharge(recommendationService.isTvCopyRightOrPay(
                    album.getPay_platform(), true)
                    + "");
            // for tvod icon type search
            // albumDto.setChargeType();
            albumDto.setIconType(JumpUtil.getIconType(null, albumDto.getIfCharge(), albumDto.getChargeType(), null,
                    null));
            albumDto.setActorInfo(this.getSarrsActor(album));

            // 设置专辑中的 推荐专辑数据
            if (StringUtil.isNotBlank(commonParam.getAppCode())
                    && StringUtil.toInteger(commonParam.getAppCode()) >= 400) {
                RecommendationLevidiResponse recommendationLevidiResponse = this.facadeTpDao.getRecommendationTpDao()
                        .getLevidiRecommendation(docInfo.getLetv_original_id(), null, null, 10, "rec_0904", null, null,
                                null, commonParam);
                if (recommendationLevidiResponse != null
                        && !CollectionUtils.isEmpty(recommendationLevidiResponse.getData())) {
                    albumDto.setRelation(recommendationService.getLevidiRecDataList(
                            recommendationLevidiResponse.getData(), commonParam));
                }
            }
        }
        return albumDto;
    }

    public AlbumDto getWebsiteAlbumDetail(String albumId, CommonParam commonParam) {
        AlbumDto albumDto = null;
        GenericDetailResponse deatailResponse = this
                .getWebSiteDetails(albumId, (short) 0, Short.MAX_VALUE, commonParam);
        if (deatailResponse != null) {
            List<ResultDocInfo> docInfos = deatailResponse.getDetails();
            if (docInfos != null) {
                ResultDocInfo docInfo = docInfos.get(0);
                if (docInfo != null) {
                    albumDto = this.convertToAlbumDto(docInfo, commonParam);
                }
            }
        }
        if (albumDto == null) {
            log.info("[mac]" + commonParam.getMac() + "[albumId]" + albumId + "album info is null");
        }
        return albumDto;
    }

    public AlbumDto getShowDetail(List<String> ids, CommonParam commonParam) {
        AlbumDto albumDto = null;
        GenericDetailResponse deatailResponse = this.getDetails(ids, (short) 0, Short.MAX_VALUE, commonParam);
        if (deatailResponse != null) {
            List<ResultDocInfo> docInfos = deatailResponse.getDetails();
            if (docInfos != null && docInfos.size() > 0) {
                List<AlbumDto> albumList = new ArrayList<AlbumDto>();
                for (int i = 0; i < docInfos.size(); i++) {
                    ResultDocInfo docInfo = docInfos.get(i);
                    if (docInfo != null) {
                        if (i == 0) {
                            albumDto = this.convertToAlbumDto(docInfo, commonParam);
                        } else {
                            AlbumDto albumInfo = this.convertToAlbumDto(docInfo, commonParam);
                            if (albumInfo != null) {
                                albumList.add(albumInfo);
                            }
                        }
                    }
                }
                if (albumDto != null) {
                    albumDto.setRelation(albumList);
                }
            }
        }
        return albumDto;
    }

    private void setAlbumStyle(AlbumDto album, AlbumMysqlTable albumMysql, String varietyShow, CommonParam commonParam) {
        if (albumMysql == null || albumMysql.getCategory() == null) {
            return;
        }
        String ra = albumMysql.getRelationAlbumId();

        // Lecom需求，所有频道均添加评分,所有频道添加分级信息
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) {
            if (null == album.getScore()) {
                album.setScore(albumMysql.getScore());
            }

            // 分级信息
            String contentRatingId = albumMysql.getContentRatingId() == null ? null : String.valueOf(albumMysql
                    .getContentRatingId());
            album.setContentRatingId(contentRatingId);
            album.setContentRatingValue(albumMysql.getContentRatingValue());
            album.setContentRatingDesc(albumMysql.getContentRatingDesc());
            album.setSubCateName(albumMysql.getSub_category_name_all());
        } else { // 详情页改版
            album.setTags(AlbumDto.buildTags(albumMysql, album, commonParam));
        }

        // 勾选栏目，则直接设置成综艺样式
        if (VideoUtil.isVarietyShow(albumMysql, commonParam)) {
            album.setVarietyShow(1);
            this.setVarietyAlbumStyle(album, albumMysql, ra);
        } else {
            album.setVarietyShow(0);

            switch (albumMysql.getCategory()) {
            case VideoConstants.Category.TV:
                album.setReleaseDate(this.cast2year(albumMysql.getRelease_date()));
                album.setSubCategoryName(this.getSubCategoryName(albumMysql.getId(),
                        albumMysql.getSub_category_name_all()));
                album.setUpdateFrequency(albumMysql.getPlay_status());
                album.setNowEpisode(albumMysql.getNowEpisodes());
                album.setEpisodes(albumMysql.getEpisode());
                album.setEnd((albumMysql.getIs_end() != null && albumMysql.getIs_end() == 1));

                if (StringUtils.isNotBlank(ra)) {
                    album.setRelationAlbumCnt(ra.split(",").length);
                    String area = albumMysql.getArea();
                    if (StringUtils.isNotBlank(area) && VideoConstants.Area.US.equalsIgnoreCase(area)) {
                        album.setRelationAlbumType(VideoConstants.AlbumDetail.ALBUM_RELATION_POSTFIX_JI);
                    } else {
                        album.setRelationAlbumType(VideoConstants.AlbumDetail.ALBUM_RELATION_POSTFIX_BU);
                    }
                }
                break;
            case VideoConstants.Category.FILM:
                album.setAlias(albumMysql.getAlias());
                album.setReleaseDate(this.cast2year(albumMysql.getRelease_date()));
                album.setSubCategoryName(this.getSubCategoryName(albumMysql.getId(),
                        albumMysql.getSub_category_name_all()));
                album.setDuration(albumMysql.getDuration() != null ? albumMysql.getDuration().toString() : null);
                if (null == album.getScore()) {
                    album.setScore(albumMysql.getScore());
                }
                album.setSubName(albumMysql.getSub_title());// 电影详情页看点

                if (StringUtils.isNotBlank(ra)) {
                    album.setRelationAlbumCnt(ra.split(",").length);
                    album.setRelationAlbumType(VideoConstants.AlbumDetail.ALBUM_RELATION_POSTFIX_BU);
                }

                if (ProductLineConstants.WCODE.LETV_HK.equalsIgnoreCase(commonParam.getWcode())) {
                    album.setContentRatingValue(this.getContentRatingValue(albumMysql.getContentRatingId() + "",
                            commonParam.getLangcode()));
                    album.setContentRatingDesc(this.getContentRatingDesc(albumMysql.getContentRatingId() + "",
                            commonParam.getLangcode()));
                }

                break;
            case VideoConstants.Category.DFILM:
                album.setSubCategoryName(this.getSubCategoryName(albumMysql.getId(),
                        albumMysql.getSub_category_name_all()));

                album.setNowIssue(albumMysql.getNowIssue());
                album.setReleaseDate(this.cast2year(albumMysql.getRelease_date()));
                album.setUpdateFrequency(albumMysql.getPlay_status());
                album.setNowEpisode(albumMysql.getFollownum() == null ? null : albumMysql.getFollownum().toString());
                album.setEpisodes(albumMysql.getEpisode());
                String dv = albumMysql.getVarietyShow();
                if (dv.equals(VideoConstants.AlbumDetail.DFILM_IS_VARIETY_NO)) {
                    album.setVarietyShow(0);
                } else {
                    album.setVarietyShow(1);
                }
                break;
            case VideoConstants.Category.SPORT:
                String sv = albumMysql.getVarietyShow();

                if (sv.equals(VideoConstants.AlbumDetail.DFILM_IS_VARIETY_NO)) {
                    album.setNowIssue(albumMysql.getNowIssue());
                    album.setVarietyShow(0);
                } else {
                    album.setReleaseDate(this.cast2year(albumMysql.getRelease_date()));
                    album.setUpdateFrequency(albumMysql.getPlay_status());
                    album.setNowEpisode(albumMysql.getNowEpisodes());
                    album.setEpisodes(albumMysql.getEpisode());
                    album.setVarietyShow(1);
                }
                break;
            case VideoConstants.Category.VARIETY:
                this.setVarietyAlbumStyle(album, albumMysql, ra);
                break;
            case VideoConstants.Category.ENT:
                album.setSubCategoryName(this.getSubCategoryName(albumMysql.getId(),
                        albumMysql.getSub_category_name_all()));
                album.setUpdateFrequency(albumMysql.getPlay_status());
                album.setTagName(albumMysql.getTag());
                break;
            case VideoConstants.Category.CARTOON:
                // 动漫正片：正片180001,TV版 181031, OVA版 181032 剧场版181033, OAD版181034
                album.setPositive(albumMysql.getAlbum_attr() != null && albumMysql.getAlbum_attr() == 1);
            case VideoConstants.Category.PARENTING:
                album.setReleaseDate(this.cast2year(albumMysql.getRelease_date()));
                album.setFitAge(albumMysql.getFit_age());
                album.setSubCategoryName(this.getSubCategoryName(albumMysql.getId(),
                        albumMysql.getSub_category_name_all()));
                album.setNowEpisode(albumMysql.getNowEpisodes());
                album.setEpisodes(albumMysql.getEpisode());
                album.setUpdateFrequency(albumMysql.getPlay_status());
                if (StringUtils.isNotBlank(ra)) {
                    album.setRelationAlbumCnt(ra.split(",").length);
                    album.setRelationAlbumType(VideoConstants.AlbumDetail.ALBUM_RELATION_POSTFIX_BU);
                }
                album.setEnd((albumMysql.getIs_end() != null && albumMysql.getIs_end() == 1));
                break;
            case VideoConstants.Category.MUSIC:
                album.setReleaseDate(this.cast2year(albumMysql.getRelease_date()));
                album.setSinger(albumMysql.getStarring());
                if (StringUtils.isNotBlank(ra)) {
                    album.setRelationAlbumCnt(ra.split(",").length);
                    album.setRelationAlbumType(VideoConstants.AlbumDetail.ALBUM_RELATION_POSTFIX_JIE);
                }
                break;
            case VideoConstants.Category.TEACH:
                album.setReleaseDate(this.cast2year(albumMysql.getRelease_date()));
                album.setFitAge(albumMysql.getFit_age());
                album.setSubCategoryName(this.getSubCategoryName(albumMysql.getId(),
                        albumMysql.getSub_category_name_all()));
                break;
            default:
                break;
            }
        }
    }

    private void setVarietyAlbumStyle(AlbumDto album, AlbumMysqlTable albumMysql, String ra) {
        album.setSubCategoryName(this.getSubCategoryName(albumMysql.getId(), albumMysql.getSub_category_name_all()));
        album.setCompere(albumMysql.getCompere());
        album.setPlayTvName(albumMysql.getPlay_tv_name());
        album.setNowIssue(albumMysql.getFollownum() != null ? albumMysql.getFollownum().toString() : null);

        if (StringUtils.isNotBlank(ra) && albumMysql.getCategory() != null) {
            switch (albumMysql.getCategory()) {
            case VideoConstants.Category.VARIETY:
                album.setRelationAlbumCnt(ra.split(",").length);
                album.setRelationAlbumType(VideoConstants.AlbumDetail.ALBUM_RELATION_POSTFIX_JI);
                break;
            case VideoConstants.Category.SPORT:
                album.setRelationAlbumCnt(ra.split(",").length);
                album.setRelationAlbumType(VideoConstants.AlbumDetail.ALBUM_RELATION_POSTFIX_CHANG);
                break;
            default:
                album.setRelationAlbumCnt(ra.split(",").length);
                album.setRelationAlbumType(VideoConstants.AlbumDetail.ALBUM_RELATION_POSTFIX_JI);
                break;
            }
        }
    }

    /**
     * 设置视频信息
     * @param vi
     * @param category
     * @param varietyShow
     * @param album
     * @param albumMysql
     * @param posSerieses
     * @param preSeries
     * @param posAddSeries
     * @param positiveSeriesStreamSet
     * @param preSeriesStreamSet
     * @param segments
     * @param commonParam
     */
    private void setVideoInfo(VideoMysqlTable vi, int category, String varietyShow, AlbumDto album,
            AlbumMysqlTable albumMysql, Set<VideoDto> posSerieses, List<VideoDto> preSeries,
            Set<VideoDto> posAddSeries, Set<String> positiveSeriesStreamSet, Set<String> preSeriesStreamSet,
            List<VideoDto> segments, List<VideoDto> attachingSeries, CommonParam commonParam) {
        VideoDto video = new VideoDto();
        video.setVideoId(vi.getId() != null ? vi.getId().toString() : "");
        video.setCategoryId(vi.getCategory());
        video.setAlbumId(vi.getPid() != null ? vi.getPid().toString() : "");
        video.setPositive(this.isPositive(vi.getVideo_type(), vi.getCategory()));
        video.setVideoTypeId(vi.getVideo_type());
        video.setPayPlatForm(vi.getPay_platform());
        video.setPlayPlatform(vi.getPlay_platform());

        if (VideoUtil.isVarietyShow(albumMysql, commonParam)) {
            video.setVarietyShow(Integer.valueOf(VideoConstants.AlbumDetail.ALBUM_IS_VARITY_SHOW));
            this.setVarietyVideoInfo(video, vi, category, album, posSerieses, positiveSeriesStreamSet,
                    preSeriesStreamSet, segments, preSeries, attachingSeries);
            return;
        }

        switch (category) {
        case VideoConstants.Category.TV:
            video.setEpisode(vi.getEpisode());
            video.setOrderInAlbum(vi.getPorder());

            if (StringUtil.isNotBlank(vi.getSub_title())) {
                video.setTitle(vi.getSub_title());
            }

            if (null != vi.getDuration()) {
                video.setDuration(Long.valueOf(vi.getDuration()));
            }

            if (!video.getPositive()) {
                video.setName(vi.getName_cn());
                video.setImg(vi.getPic(400, 300));
                video.setBigImg(vi.getPic(320, 200));
                if ((segments.size() <= VideoConstants.SCREEN_SIZE * 3) && album.getPositive()) {
                    segments.add(video);
                }
                preSeries.add(video);
                // if (null != video.getVideoTypeId()) {
                // if (video.getVideoTypeId().intValue() ==
                // VideoConstants.VideoType.YU_GAO_PIAN) {
                // preSeries.add(video); // 老逻辑是非正片都认为是预告片
                // } else {
                // attachingSeries.add(video); // 详情页改版
                // }
                // }
                this.addVideoStreamToSet(vi.getPlay_streams(), preSeriesStreamSet);
            }

            if (video.getPositive()) {
                posSerieses.add(video);
                this.addVideoStreamToSet(vi.getPlay_streams(), positiveSeriesStreamSet);
            }

            if (album != null && !album.getEnd() && StringUtils.isNotEmpty(vi.getEpisode())
                    && vi.getEpisode().matches("\\d*") && albumMysql.getFollownum() != null
                    && Long.valueOf(vi.getEpisode()) <= (Long.valueOf(albumMysql.getFollownum()) + 3)
                    && Long.valueOf(vi.getEpisode()) > (Long.valueOf(albumMysql.getFollownum()))
                    && (vi.getVideo_type() != null)
                    && (vi.getVideo_type().intValue() == VideoConstants.VideoType.YU_GAO_PIAN)) {
                video.setIconType(IconConstants.ICON_TYPE_PREVIEW);
                video.setPositive(false);
                video.setSeriesType(0);// 预告
                posAddSeries.add(video);
            }

            break;
        case VideoConstants.Category.FILM:
            video.setName(vi.getName_cn());
            video.setSubName(vi.getSub_title());
            if (!vi.isPositive()) {
                video.setImg(vi.getPic(400, 300));
                video.setBigImg(vi.getPic(320, 200));
            }
            if (null != vi.getDuration()) {
                video.setDuration(Long.valueOf(vi.getDuration()));
            }
            if (video.getPositive()) {
                posSerieses.add(video);
                this.addVideoStreamToSet(vi.getPlay_streams(), positiveSeriesStreamSet);
            } else {
                preSeries.add(video);
                this.addVideoStreamToSet(vi.getPlay_streams(), preSeriesStreamSet);
            }
            break;
        case VideoConstants.Category.DFILM:

            video.setEpisode(vi.getEpisode());
            video.setName(vi.getName_cn());
            video.setGuest(vi.getGuest());
            video.setOrderInAlbum(vi.getPorder());
            video.setImg(vi.getPic(400, 300));
            video.setBigImg(vi.getPic(320, 200));
            if (null != vi.getDuration()) {
                video.setDuration(Long.valueOf(vi.getDuration()));
            }
            if (video.getPositive()) {
                posSerieses.add(video);
                this.addVideoStreamToSet(vi.getPlay_streams(), positiveSeriesStreamSet);
            } else {

                if ((segments.size() <= VideoConstants.SCREEN_SIZE * 3)) {
                    segments.add(video);
                }

                preSeries.add(video);
                // if (null != video.getVideoTypeId()) {
                // if (video.getVideoTypeId().intValue() ==
                // VideoConstants.VideoType.YU_GAO_PIAN) {
                // preSeries.add(video); // 老逻辑是非正片都认为是预告片
                // } else {
                // attachingSeries.add(video); // 详情页改版
                // }
                // }
                this.addVideoStreamToSet(vi.getPlay_streams(), preSeriesStreamSet);
            }
            break;
        case VideoConstants.Category.SPORT:
            video.setEpisode(vi.getEpisode());
            video.setName(vi.getName_cn());
            video.setGuest(vi.getGuest());
            video.setOrderInAlbum(vi.getPorder());
            video.setImg(vi.getPic(400, 300));
            video.setBigImg(vi.getPic(320, 200));
            if (null != vi.getDuration()) {
                video.setDuration(Long.valueOf(vi.getDuration()));
            }
            if (video.getPositive()) {
                posSerieses.add(video);
                this.addVideoStreamToSet(vi.getPlay_streams(), positiveSeriesStreamSet);
            } else {
                preSeries.add(video);
                // if (null != video.getVideoTypeId()) {
                // if (video.getVideoTypeId().intValue() ==
                // VideoConstants.VideoType.YU_GAO_PIAN) {
                // preSeries.add(video); // 老逻辑是非正片都认为是预告片
                // } else {
                // attachingSeries.add(video); // 详情页改版
                // }
                // }
                this.addVideoStreamToSet(vi.getPlay_streams(), preSeriesStreamSet);
            }
            break;
        case VideoConstants.Category.VARIETY:
            this.setVarietyVideoInfo(video, vi, category, album, posSerieses, positiveSeriesStreamSet,
                    preSeriesStreamSet, segments, preSeries, attachingSeries);
            break;
        case VideoConstants.Category.ENT:
        case VideoConstants.Category.FENG_SHANG:
        case VideoConstants.Category.HOTSPOT:
        case VideoConstants.Category.CAR:
            video.setName(vi.getName_cn());
            video.setSubName(vi.getSub_title());
            video.setDesc(vi.getDescription());
            if (null != vi.getDuration()) {
                video.setDuration(Long.valueOf(vi.getDuration()));
            }
            video.setImg(vi.getPic(400, 300));
            video.setBigImg(vi.getPic(320, 200));
            if (video.getPositive()) {
                posSerieses.add(video);
                this.addVideoStreamToSet(vi.getPlay_streams(), positiveSeriesStreamSet);
            } else {
                preSeries.add(video);
                // if (null != video.getVideoTypeId()) {
                // if (video.getVideoTypeId().intValue() ==
                // VideoConstants.VideoType.YU_GAO_PIAN) {
                // preSeries.add(video); // 老逻辑是非正片都认为是预告片
                // } else {
                // attachingSeries.add(video); // 详情页改版
                // }
                // }
                this.addVideoStreamToSet(vi.getPlay_streams(), preSeriesStreamSet);
            }
            break;
        case VideoConstants.Category.CARTOON:
            video.setEpisode(vi.getEpisode());
            video.setOrderInAlbum(vi.getPorder());

            if (!video.getPositive()) {
                video.setName(vi.getName_cn());
                video.setImg(vi.getPic(400, 300));
                video.setBigImg(vi.getPic(320, 200));
                if ((segments.size() <= VideoConstants.SCREEN_SIZE * 3) && album.getPositive()) {
                    segments.add(video);
                }
                preSeries.add(video);
                this.addVideoStreamToSet(vi.getPlay_streams(), preSeriesStreamSet);
            }
            if (null != vi.getDuration()) {
                video.setDuration(Long.valueOf(vi.getDuration()));
            }
            if (video.getPositive()) {
                posSerieses.add(video);
                this.addVideoStreamToSet(vi.getPlay_streams(), positiveSeriesStreamSet);
            }

            if (album != null && album.getEnd() != null && !album.getEnd() && StringUtils.isNotEmpty(vi.getEpisode())
                    && vi.getEpisode().matches("\\d*") && albumMysql.getFollownum() != null
                    && Long.valueOf(vi.getEpisode()) <= (Long.valueOf(albumMysql.getFollownum()) + 3)
                    && Long.valueOf(vi.getEpisode()) > (Long.valueOf(albumMysql.getFollownum()))
                    && (vi.getVideo_type() != null)
                    && (vi.getVideo_type().intValue() == VideoConstants.VideoType.YU_GAO_PIAN)) {
                video.setIconType(IconConstants.ICON_TYPE_PREVIEW);
                video.setPositive(false);
                video.setSeriesType(0);// 预告
                posAddSeries.add(video);
            }
            break;
        case VideoConstants.Category.PARENTING:
            video.setEpisode(vi.getEpisode());
            video.setOrderInAlbum(vi.getPorder());
            video.setName(vi.getName_cn());
            video.setImg(vi.getPic(400, 300));
            video.setBigImg(vi.getPic(320, 200));
            if (null != vi.getDuration()) {
                video.setDuration(Long.valueOf(vi.getDuration()));
            }
            if (!video.getPositive()) {
                video.setName(vi.getName_cn());
                video.setImg(vi.getPic(400, 300));

                if ((segments.size() <= VideoConstants.SCREEN_SIZE * 3) && album.getPositive()) {
                    segments.add(video);
                }
                preSeries.add(video);
                // if (null != video.getVideoTypeId()) {
                // if (video.getVideoTypeId().intValue() ==
                // VideoConstants.VideoType.YU_GAO_PIAN) {
                // preSeries.add(video); // 老逻辑是非正片都认为是预告片
                // } else {
                // attachingSeries.add(video); // 详情页改版
                // }
                // }
                this.addVideoStreamToSet(vi.getPlay_streams(), preSeriesStreamSet);
            }

            if (video.getPositive()) {
                posSerieses.add(video);
                this.addVideoStreamToSet(vi.getPlay_streams(), positiveSeriesStreamSet);
            }

            if (album != null && !album.getEnd() && StringUtils.isNotEmpty(vi.getEpisode())
                    && vi.getEpisode().matches("\\d*") && albumMysql.getFollownum() != null
                    && Long.valueOf(vi.getEpisode()) <= (Long.valueOf(albumMysql.getFollownum()) + 3)
                    && Long.valueOf(vi.getEpisode()) > (Long.valueOf(albumMysql.getFollownum()))
                    && (vi.getVideo_type() != null) && (vi.getVideo_type() == VideoConstants.VideoType.YU_GAO_PIAN)) {
                video.setPositive(false);
                video.setSeriesType(0);// 预告
                posAddSeries.add(video);
            }
            break;
        case VideoConstants.Category.MUSIC:
            video.setName(vi.getName_cn());
            if (StringUtils.isNotEmpty(vi.getSinger())) {
                video.setSinger(vi.getSinger().replace(",", "  "));
            }
            if (null != vi.getDuration()) {
                video.setDuration(Long.valueOf(vi.getDuration()));
            }
            video.setImg(vi.getPic(400, 300));
            video.setBigImg(vi.getPic(320, 200));
            if (video.getPositive()) {
                posSerieses.add(video);
                this.addVideoStreamToSet(vi.getPlay_streams(), positiveSeriesStreamSet);
            } else {
                preSeries.add(video);
                // if (null != video.getVideoTypeId()) {
                // if (video.getVideoTypeId().intValue() ==
                // VideoConstants.VideoType.YU_GAO_PIAN) {
                // preSeries.add(video); // 老逻辑是非正片都认为是预告片
                // } else {
                // attachingSeries.add(video); // 详情页改版
                // }
                // }
                this.addVideoStreamToSet(vi.getPlay_streams(), preSeriesStreamSet);
            }
            break;
        case VideoConstants.Category.TEACH:
            video.setName(vi.getName_cn());
            video.setImg(vi.getPic(400, 300));
            video.setBigImg(vi.getPic(320, 200));
            if (null != vi.getDuration()) {
                video.setDuration(Long.valueOf(vi.getDuration()));
            }
            video.setEpisode(vi.getEpisode());
            video.setOrderInAlbum(vi.getPorder());
            if (video.getPositive()) {
                posSerieses.add(video);
                this.addVideoStreamToSet(vi.getPlay_streams(), positiveSeriesStreamSet);
            } else {
                preSeries.add(video);
                // if (null != video.getVideoTypeId()) {
                // if (video.getVideoTypeId().intValue() ==
                // VideoConstants.VideoType.YU_GAO_PIAN) {
                // preSeries.add(video); // 老逻辑是非正片都认为是预告片
                // } else {
                // attachingSeries.add(video); // 详情页改版
                // }
                // }
                this.addVideoStreamToSet(vi.getPlay_streams(), preSeriesStreamSet);
            }
            break;
        default:
            video.setName(vi.getName_cn());
            video.setImg(vi.getPic(400, 300));
            video.setBigImg(vi.getPic(320, 200));
            if (null != vi.getDuration()) {
                video.setDuration(Long.valueOf(vi.getDuration()));
            }
            video.setEpisode(vi.getEpisode());
            if (video.getPositive()) {
                posSerieses.add(video);
                this.addVideoStreamToSet(vi.getPlay_streams(), positiveSeriesStreamSet);
            } else {
                preSeries.add(video);
                // if (null != video.getVideoTypeId()) {
                // if (video.getVideoTypeId().intValue() ==
                // VideoConstants.VideoType.YU_GAO_PIAN) {
                // preSeries.add(video); // 老逻辑是非正片都认为是预告片
                // } else {
                // attachingSeries.add(video); // 详情页改版
                // }
                // }
                this.addVideoStreamToSet(vi.getPlay_streams(), preSeriesStreamSet);
            }
            break;
        }
    }

    /**
     * 美国版分长视频频道（电影、电视剧、综艺、动漫、纪录片、教育、体育）和短视频频道（其他频道）
     * @param vi
     * @param posSerieses
     * @param preSeries
     * @param posAddSeries
     * @param attachingSeries
     * @param positiveSeriesStreamSet
     * @param preSeriesStreamSet
     */
    private void setVideoInfo4US(boolean albumPositive, Integer albumVarietyShow, Integer albumCategary,
            VideoMysqlTable vi, Collection<VideoDto> posSerieses, Collection<VideoDto> preSeries,
            Collection<VideoDto> posAddSeries, Collection<VideoDto> attachingSeries,
            Collection<String> positiveSeriesStreamSet, Collection<String> preSeriesStreamSet) {
        VideoDto video = new VideoDto();
        video.setVideoId(vi.getId() != null ? vi.getId().toString() : "");
        video.setCategoryId(vi.getCategory());
        video.setAlbumId(vi.getPid() != null ? vi.getPid().toString() : "");
        Integer videoType = vi.getVideo_type();
        video.setPositive(this.isPositive(videoType, vi.getCategory()));
        video.setVideoTypeId(vi.getVideo_type());
        video.setPayPlatForm(vi.getPay_platform());
        video.setPlayPlatform(vi.getPlay_platform());
        video.setName(vi.getName_cn());
        video.setSubName(vi.getSub_title());

        Integer imgWidth = 400;// 设置尺寸固定400*300
        Integer imgHeight = 300;
        video.setImgSize(imgWidth + "*" + imgHeight);
        video.setImg(vi.getPic(imgWidth, imgHeight));
        video.setEpisode(vi.getEpisode());
        video.setOrderInAlbum(vi.getPorder());
        Map<String, String> pics = new HashMap<String, String>();
        pics.put(CommonConstants.PIXEL_400_225, vi.getPic(400, 225));
        video.setPic_urls(pics);

        if (vi.getDuration() != null) {
            video.setDuration(vi.getDuration().longValue() * 1000L);
        }

        if (albumPositive) {
            int categary = albumCategary == null ? MmsTpConstant.MMS_CATEGARY_ALL : albumCategary;
            switch (categary) {
            case MmsTpConstant.MMS_CATEGARY_FILM:
                if (video.getPositive()) {
                    posSerieses.add(video); // 电影频道，客户端不展示“选集”tab，由客户端控制
                    this.addVideoStreamToSet(vi.getPlay_streams(), positiveSeriesStreamSet);
                    // 2016.11.22原定义周边为 非正片&非预告片 ，
                    // 产品要求电影频道的周边加入预告片，所以电影频道的周边内容为（非正片）
                } else {
                    // 周边视频
                    attachingSeries.add(video);
                    this.addVideoStreamToSet(vi.getPlay_streams(), preSeriesStreamSet);
                }
                break;
            case MmsTpConstant.MMS_CATEGARY_TV:
            case MmsTpConstant.MMS_CATEGARY_VARIETY:
            case MmsTpConstant.MMS_CATEGARY_CARTOON:
            case MmsTpConstant.MMS_CATEGARY_DFILM:
            case MmsTpConstant.MMS_CATEGARY_SPORT:
            case MmsTpConstant.MMS_CATEGARY_EDUCATION:
                if (video.getPositive()) {
                    posSerieses.add(video);
                    this.addVideoStreamToSet(vi.getPlay_streams(), positiveSeriesStreamSet);
                } else if (isVideoNotPositiveOrPre(videoType)) {
                    // 电影、电视剧、综艺、动漫、纪录片、教育频道，周边视频取全部非正片视频，倒序，体育频道只有在设置了综艺展示才有周边视频；
                    if (categary != MmsTpConstant.MMS_CATEGARY_SPORT
                            || (albumVarietyShow != null && 1 == albumVarietyShow)) {
                        attachingSeries.add(video);
                        this.addVideoStreamToSet(vi.getPlay_streams(), preSeriesStreamSet);
                    }

                }
                break;
            default:
                // 其他短视频频道，所有视频全部放入选集
                posSerieses.add(video);
                this.addVideoStreamToSet(vi.getPlay_streams(), positiveSeriesStreamSet);
                break;
            }
        } else {
            preSeries.add(video);
            this.addVideoStreamToSet(vi.getPlay_streams(), preSeriesStreamSet);
        }
    }

    private VideoDto parseLecomAlbumSeriesVideo(VideoMysqlTable vi) {
        VideoDto video = null;
        if (vi != null) {
            video = new VideoDto();
            video.setVideoId(vi.getId() != null ? vi.getId().toString() : "");
            video.setCategoryId(vi.getCategory());
            video.setAlbumId(vi.getPid() != null ? vi.getPid().toString() : "");
            Integer videoType = vi.getVideo_type();
            video.setPositive(this.isPositive(videoType, vi.getCategory()));
            video.setVideoTypeId(vi.getVideo_type());
            // video.setPayPlatForm(vi.getPay_platform());
            // video.setPlayPlatform(vi.getPlay_platform());
            video.setName(vi.getName_cn());
            video.setSubName(vi.getSub_title());

            Integer imgWidth = 400;// 设置尺寸固定400*300
            Integer imgHeight = 300;
            video.setImgSize(imgWidth + "*" + imgHeight);
            video.setImg(vi.getPic(imgWidth, imgHeight));
            Map<String, String> pics = new HashMap<String, String>();
            pics.put(CommonConstants.PIXEL_400_225, vi.getPic(400, 225));
            video.setPic_urls(pics);
            video.setEpisode(vi.getEpisode());
            video.setOrderInAlbum(vi.getPorder());
        }
        return video;
    }

    private void setVarietyVideoInfo(VideoDto video, VideoMysqlTable vi, int category, AlbumDto album,
            Set<VideoDto> posSerieses, Set<String> positiveSeriesStreamSet, Set<String> preSeriesStreamSet,
            List<VideoDto> segments, List<VideoDto> preSeries, List<VideoDto> attachingSeries) {
        video.setEpisode(vi.getEpisode());
        video.setName(vi.getName_cn());
        video.setGuest(vi.getGuest());
        video.setOrderInAlbum(vi.getPorder());
        video.setImg(vi.getPic(400, 300));
        video.setBigImg(vi.getPicNew(320, 200));
        if (null != vi.getDuration()) {
            video.setDuration(Long.valueOf(vi.getDuration()));
        }
        video.setSubName(vi.getSub_title());

        if (category != VideoConstants.Category.VARIETY) {
            String episode = vi.getRelease_date(); // 如2015-11-11的格式转成20151111格式
            if (episode != null && episode.indexOf('-') > -1) {
                episode = episode.replaceAll("-", "");
            }
            video.setEpisode(episode);
        }

        if (album.getPositive()) {
            if (video.getPositive()) {
                posSerieses.add(video);
                this.addVideoStreamToSet(vi.getPlay_streams(), positiveSeriesStreamSet);
            } else {
                if (category == VideoConstants.Category.MUSIC) {// 音乐类型片段只取{其他,片段}两种类型
                    if (VideoUtil.isMusicSegment(vi)) {
                        segments.add(video);
                    }
                } else {
                    segments.add(video);
                }
                // if (null != video.getVideoTypeId()) {
                // if (video.getVideoTypeId().intValue() ==
                // VideoConstants.VideoType.YU_GAO_PIAN) {
                // preSeries.add(video); // 老逻辑是非正片都认为是预告片
                // } else {
                // attachingSeries.add(video); // 详情页改版
                // }
                // }
                preSeries.add(video); // 老逻辑是非正片都认为是预告片

                this.addVideoStreamToSet(vi.getPlay_streams(), preSeriesStreamSet);
            }
        } else {// 综艺非正片专辑只有选集，选集中有正片和非正片
            posSerieses.add(video);
            this.addVideoStreamToSet(vi.getPlay_streams(), positiveSeriesStreamSet);
        }
    }

    private String cast2year(String date) {

        String year = date;

        if (StringUtils.isNotEmpty(date)) {
            if (date.matches("\\d{4}")) {
                year = date;
            } else if (date.length() > 4 && (date.substring(0, 4)).matches("\\d{4}")) {
                year = date.substring(0, 4);
            }
        }

        return year;
    }

    private Actor initActor(CmsActorResponse cmsActor, String id) {
        Actor actor = new Actor();
        actor.setId(id); // 与cmsActor.getLeId().toString()值相同
        String name = cmsActor.getName();
        actor.setName(name);
        actor.setImg(cmsActor.getHeadPicWeb34() + "/34_150_200.jpg"); // 先默认写死去300*400的图
        actor.setImgSize("150*200");
        return actor;
    }

    /**
     * 根据id获得演员信息
     * @param albumMysql
     * @return
     */
    private List<Actor> getActorsById(AlbumMysqlTable albumMysql, String langcode) {
        List<String> directorIds = null;
        List<String> starringIds = null;

        if (StringUtils.isNotEmpty(albumMysql.getDirectory_id())) {
            directorIds = Arrays.asList(albumMysql.getDirectory_id().split(","));
        }
        if (StringUtils.isNotEmpty(albumMysql.getStarring_id())) {
            starringIds = Arrays.asList(albumMysql.getStarring_id().split(","));
        }

        List<String> ids = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(directorIds)) {
            ids.addAll(directorIds);
        }
        if (!CollectionUtils.isEmpty(starringIds)) {
            ids.addAll(starringIds);
        }

        List<Actor> actors = new ArrayList<Actor>();
        Map<String, HashSet<Integer>> actorRole = new HashMap<String, HashSet<Integer>>();
        HashSet<Integer> roles = null;

        List<String> idsWithoutDup = new ArrayList<>(new LinkedHashSet<>(ids));

        for (String id : idsWithoutDup) {
            MmsResponse<List<CmsActorResponse>> response = this.facadeTpDao.getVideoTpDao().getCmsStar(id, langcode);

            if (response != null && !CollectionUtils.isEmpty(response.getData())) {
                CmsActorResponse cmsActor = response.getData().get(0);
                if (cmsActor != null) {
                    Actor actor = initActor(cmsActor, id);
                    String name = cmsActor.getName();
                    // 过滤制片人及编剧
                    // if (StringUtils.isNotEmpty(albumMysql.getMaker())
                    // && (CollectionUtils.isEmpty(actorRole.get(actor.getId()))
                    // || !actorRole.get(actor.getId())
                    // .contains(VideoConstants.ActorRole.PRODUCER))
                    // && albumMysql.getMaker().contains(name)) {
                    // Actor actor2 = initActor(cmsActor, id);
                    // actor2.setRole(VideoConstants.ActorRole.PRODUCER);
                    // roles = new HashSet<Integer>();
                    // roles.add(VideoConstants.ActorRole.PRODUCER);
                    // actorRole.put(actor2.getId(), roles);
                    // actors.add(actor2);
                    // }
                    // if (StringUtils.isNotEmpty(albumMysql.getScreen_writer())
                    // && (CollectionUtils.isEmpty(actorRole.get(actor.getId()))
                    // || !actorRole.get(actor.getId())
                    // .contains(VideoConstants.ActorRole.SCRIPTWRITER))
                    // && albumMysql.getScreen_writer().contains(name)) {
                    // Actor actor3 = initActor(cmsActor, id);
                    // actor3.setRole(VideoConstants.ActorRole.SCRIPTWRITER);
                    // roles = new HashSet<Integer>();
                    // roles.add(VideoConstants.ActorRole.SCRIPTWRITER);
                    // actorRole.put(actor3.getId(), roles);
                    // actors.add(actor3);
                    // }
                    if (StringUtils.isNotEmpty(albumMysql.getDirectory_id())
                            && (CollectionUtils.isEmpty(actorRole.get(actor.getId())) || !actorRole.get(actor.getId())
                                    .contains(VideoConstants.ActorRole.DIRECTOR))
                            && albumMysql.getDirectory().contains(name)) {
                        Actor actor4 = initActor(cmsActor, id);
                        actor4.setRole(VideoConstants.ActorRole.DIRECTOR);
                        roles = new HashSet<Integer>();
                        roles.add(VideoConstants.ActorRole.DIRECTOR);
                        actorRole.put(actor4.getId(), roles);
                        actors.add(actor4);
                    }
                    if (StringUtils.isNotEmpty(albumMysql.getStarring())
                            && (CollectionUtils.isEmpty(actorRole.get(actor.getId())) || !actorRole.get(actor.getId())
                                    .contains(VideoConstants.ActorRole.STARRING))
                            && albumMysql.getStarring().contains(name)) {
                        Actor actor5 = initActor(cmsActor, id);
                        actor5.setRole(VideoConstants.ActorRole.STARRING);
                        roles = new HashSet<Integer>();
                        roles.add(VideoConstants.ActorRole.STARRING);
                        actorRole.put(actor5.getId(), roles);
                        actors.add(actor5);
                    }
                }
            }

        }
        Collections.sort(actors, new Comparator<Actor>() {
            @Override
            public int compare(Actor a1, Actor a2) {
                Actor actor1 = (Actor) a1;
                Actor actor2 = (Actor) a2;
                if (actor1.getRole() - actor2.getRole() > 0) {
                    return -1;
                } else if (actor1.getRole() - actor2.getRole() < 0) {
                    return 1;
                }
                return 0;
            }
        });
        return actors;
    }

    /**
     * thrift获取外网详情
     * @param id
     * @param page
     * @param pageSize
     * @param commonParam
     * @return
     */
    public GenericDetailResponse getWebSiteDetails(String id, Short page, Short pageSize, CommonParam commonParam) {

        GenericDetailResponse deatailResponse = null;
        if (id != null) {
            deatailResponse = this.facadeCacheDao.getVideoCacheDao().getWebsiteAlbumInfo(id, commonParam);
            // 之前由于逻辑错误，缓存放入了一些不合法的数据
            if (!isValidWebsitData(deatailResponse)) {
                deatailResponse = this.facadeTpDao.getSearchTpDao().getWebsiteDetails(id, page, pageSize);
                if (isValidWebsitData(deatailResponse)) {
                    this.facadeCacheDao.getVideoCacheDao().setWebsiteAlbumInfo(id, deatailResponse, commonParam);
                }
            }
        }
        return deatailResponse;
    }

    /**
     * 外网详情合法性校验
     * @param deatailResponse
     * @return
     */
    private boolean isValidWebsitData(GenericDetailResponse deatailResponse) {
        boolean isValid = false;
        if (deatailResponse != null) {
            List<ResultDocInfo> docInfos = deatailResponse.getDetails();
            if (docInfos != null) {
                ResultDocInfo docInfo = docInfos.get(0);
                if (docInfo != null) {
                    isValid = true;
                }
            }
        }
        return isValid;
    }

    /**
     * 获取作品库专辑或视频详情
     * @param idList
     * @param page
     * @param pageSize
     * @param commonParam
     * @return
     */
    public GenericDetailResponse getDetails(List<String> idList, Short page, Short pageSize, CommonParam commonParam) {
        GenericDetailResponse deatailResponse = null;
        if (!CollectionUtils.isEmpty(idList)) {
            deatailResponse = this.facadeTpDao.getSearchTpDao().getDetails(idList, page, pageSize);
        }
        return deatailResponse;
    }

    // 方法未调用
    public AlbumMysqlTable getBroadcastAlbum(AlbumMysqlTable album, Integer broadcastId) {
        if (album != null) {
            if (broadcastId != null && broadcastId > 0) {// 如果受播控已下线，则置null
                if (broadcastId == CommonConstants.BROADCAST_TYPE.CNTV.getValue()) {
                    if (album != null && album.getCntv() != null && album.getCntv() == 0) {// 下线
                        album = null;
                    }
                } else if (broadcastId == CommonConstants.BROADCAST_TYPE.WASU.getValue()) {
                    if (album != null && album.getWasu() != null && album.getWasu() == 0) {// 下线
                        album = null;
                    }
                } else if (broadcastId == CommonConstants.BROADCAST_TYPE.CIBN.getValue()) {
                    if (album != null && album.getCibn() != null && album.getCibn() == 0) {// 下线
                        album = null;
                    }
                }
            }
            if (album != null && album.getPushflag() != null && album.getPushflag() != 0 && album.getPushflag() != 1) {// 不是TV版也不是TV外跳
                album = null;
            }
        }
        return album;
    }

    /**
     * 获取明星列表
     * @param album
     * @return
     */
    private List<Actor> getSarrsActor(AlbumAttribute album) {
        List<Actor> actors = new ArrayList<Actor>();
        if (album.getStarring() != null) {
            for (IdAndName star : album.getStarring()) {
                if (star != null) {
                    Actor actor = new Actor();
                    actor.setId(star.getId());
                    actor.setName(star.getName());
                    actor.setRole(0);
                    actors.add(actor);
                }
            }
            for (IdAndName star : album.getDirector()) {
                if (star != null) {
                    Actor actor = new Actor();
                    actor.setId(star.getId());
                    actor.setName(star.getName());
                    actor.setRole(1);
                    actors.add(actor);
                }
            }
        }
        return actors;
    }

    /**
     * 印度版获取专辑图片
     * @param img
     * @param type
     *            1、取竖图 2 取横图
     * @return
     */
    public String getPic4Sarrs(Map<String, String> img, Integer type) {
        String pic = "";
        if (img == null) {
            return pic;
        }
        String cmsImages = img.get("cmsImages");
        List<Map<String, String>> cmsImageMaps = JsonUtil.parse(cmsImages,
                new LetvTypeReference<List<Map<String, String>>>() {
                });
        if (type == 1) {
            if (StringUtil.isBlank(pic)) {
                if (cmsImageMaps != null && cmsImageMaps.size() > 0) {
                    for (Map<String, String> cmsImageMap : cmsImageMaps) {
                        pic = cmsImageMap.get("450*600");
                        if (StringUtil.isNotBlank(pic)) {
                            break;
                        }
                    }
                }
            }
            if (StringUtil.isBlank(pic)) {
                return img.get("postS1");
            }
        } else if (type == 2) {
            if (cmsImageMaps != null && cmsImageMaps.size() > 0) {
                for (Map<String, String> cmsImageMap : cmsImageMaps) {
                    pic = cmsImageMap.get("640*360");
                    if (StringUtil.isNotBlank(pic)) {
                        break;
                    }
                }
            }
        }

        return pic;
    }

    /**
     * 印度版获取播放平台
     * @param pushFlags
     * @return
     */
    public static String getSarrsPlayPlatForm(List<IdAndName> pushFlags) {
        StringBuilder playForm = new StringBuilder();
        if (pushFlags != null && pushFlags.size() > 0) {
            for (IdAndName play_form : pushFlags) {
                if (play_form != null) {
                    if (!StringUtil.isBlank(play_form.getId())) {
                        playForm.append(play_form.getId()).append(",");
                    }
                }
            }
        }
        if (!StringUtil.isBlank(playForm.toString())) {
            playForm = new StringBuilder(playForm.toString().substring(0, playForm.toString().length() - 1));
        }
        return playForm.toString();
    }

    /**
     * 判断是否为正片
     * @param video_type
     * @return
     */
    public boolean isSarrsPositive(String video_type) {
        return "180001".equals(video_type);
    }

    /**
     * 获取专辑内的视频列表
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Set<VideoDto> getSarrsVideoList(ResultDocInfo docInfo, boolean isPositive) {
        if (docInfo == null) {
            return null;
        }
        AlbumAttribute album = docInfo.getAlbum_attribute();
        if (album == null) {
            return null;
        }
        List<VideoAttributeInAlbum> videoAttributes = album.getVideo_list();
        List<VideoDto> videoList = new ArrayList<VideoDto>();
        if (videoAttributes != null) {
            if (videoAttributes.size() > 0) {
                Collections.sort(videoAttributes, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        VideoAttributeInAlbum video1 = (VideoAttributeInAlbum) o1;
                        VideoAttributeInAlbum video2 = (VideoAttributeInAlbum) o2;
                        if (video1.getPorder() - video2.getPorder() > 0) {
                            return 1;
                        } else if (video1.getPorder() - video2.getPorder() < 0) {
                            return -1;
                        }
                        return 0;
                    }
                });
            }
            for (VideoAttributeInAlbum video : videoAttributes) {
                VideoDto videoDto = new VideoDto();
                videoDto.setCategoryId(StringUtil.isBlank(video.getCategory()) ? null : Integer.valueOf(video
                        .getCategory()));
                videoDto.setAlbumId(album.getAid());
                videoDto.setPositive(this.isSarrsPositive(video.getVideo_type()));
                videoDto.setName(docInfo.getName());
                videoDto.setSubName(video.getSub_name());
                videoDto.setImg(video.getImages() != null ? video.getImages().get("images") : null);
                videoDto.setPlayPlatform(getSarrsPlayPlatForm(video.getPush_flag()));
                // 印度默认片子全部免费
                videoDto.setIfCharge("0");
                // 临时逻辑处理，二期修改
                videoDto.setPositive(isPositive);
                if (!String.valueOf(VideoConstants.Category.FILM).equals(docInfo.getCategory())) {
                    videoDto.setEpisode(video.getEpisode());
                }
                videoDto.setGlobalCid(docInfo.getCategory());
                videoDto.setGlobalId(video.getLetv_original_id());
                videoDto.setMid(video.getMid());
                videoDto.setVideoId(video.getId());
                videoDto.setExternal_id(video.getExternal_id());
                videoDto.setIfCharge(video.getIs_pay() + "");
                videoDto.setPayPlatForm(null);
                videoDto.setSource(album.getSub_src());
                videoList.add(videoDto);
            }
        }
        return new TreeSet(videoList);
    }

    /**
     * 排序、过滤码流列表，只返回视频拥有的码流
     * @param streams
     * @param categoryId
     * @param terminalBrand
     * @param locale
     * @param isYuanxian
     * @param commonParam
     * @return
     */
    private List<Stream> sortAndFilterStreamCode(String streams, Integer categoryId, String terminalBrand,
            String locale, String isYuanxian, CommonParam commonParam) {
        List<Stream> realList = new ArrayList<Stream>();

        if (StringUtils.isNotEmpty(streams)) {
            String allStreams = "";
            // Set<String> chargeStreamSet =
            // LetvStreamCommonConstants.CHARGE_STREAM_SET;
            if (ProductLineConstants.LETV_COMMON.equalsIgnoreCase(commonParam.getTerminalApplication())) {
                allStreams = LetvStreamCommonConstants.LETV_COMMON_STREAMS;
            } else {
                allStreams = LetvStreamCommonConstants.ALL_STREAMS;
                if (TerminalUtil.isLetvUs(commonParam)) {// 美国码流列表
                    allStreams = LetvStreamCommonConstants.ALL_STREAMS_US;
                    // chargeStreamSet =
                    // LetvStreamCommonConstants.CHARGE_STREAM_SET_US;
                } else {
                    allStreams = LetvStreamCommonConstants.ALL_STREAMS;
                }
            }
            List<Stream> allVideoStreamList = StreamConstants.getStreamCode(allStreams, locale);
            String[] list = streams.split(",");

            if (list != null && list.length > 0) {
                for (int i = 0; i < allVideoStreamList.size(); i++) {
                    Stream allV = allVideoStreamList.get(i);
                    allV.setKbps(LetvStreamCommonConstants.getMbps(allV.getCode()));
                    for (int j = 0; j < list.length; j++) {
                        String realStream = list[j];
                        // if (realStream.equalsIgnoreCase(allV.getCode()))
                        // {
                        if (realStream.equalsIgnoreCase(allV.getCode())
                                || (allV.getCode().equalsIgnoreCase(LetvStreamCommonConstants.CODE_NAME_1080p6m))
                                && realStream.equalsIgnoreCase(LetvStreamCommonConstants.CODE_NAME_1080p3m)) {
                            realList.add(allV);
                            // if
                            // (ProductLineConstants.LETV_COMMON.equals(terminalApplication))
                            // {
                            if (LetvStreamCommonConstants.CHARGE_STREAM_SET.contains(realStream)
                                    || "1".equals(isYuanxian)) {
                                allV.setIfCharge(1);// 收费
                            } else {
                                allV.setIfCharge(0);
                            }
                            // }
                            break;
                        }
                    }
                }
            }
        }

        return realList;
    }

    /**
     * 获得专辑下视频列表;
     * 取剧集列表逻辑:
     * 电视剧、卡通 -- 按视频在专辑中的顺序升序排列，顺序相同的再按照创建时间降序排列；
     * 电影、记录片 -- 按视频在专辑中的顺序降序排列；
     * 音乐频道下，二级分类是演唱会、颁奖礼、音乐电影、纪录片的，只取正片，且按视频在专辑中的顺序升序排列，顺序相同的再按照创建时间升序排列；其他二级分类
     * ，只取正片，且按视频在专辑中的顺序降序排列，顺序相同的再按照创建时间降序排列；
     * 其他--按视频在专辑中的顺序降序排列
     * @param broadcastId
     * @return
     */
    public List<VideoMysqlTable> getLetvAlbumSeriesList(AlbumMysqlTable albumMysql, Long targetAlbumId,
            Integer broadcastId, Integer start, Integer end, CommonParam commonParam) {
        List<VideoMysqlTable> iptvVideoList = null;

        int categoryId = -1;
        if (albumMysql.getCategory() != null) {
            categoryId = albumMysql.getCategory();
        }
        switch (categoryId) {
        case VideoConstants.Category.TV:
        case VideoConstants.Category.CARTOON:
            // 电视剧、卡通 -- 按视频在专辑中的顺序升序排列，顺序相同的再按照创建时间降序排列；
            iptvVideoList = albumVideoAccess.getVideoRange(targetAlbumId,
                    VideoTpConstant.QUERY_TYPE_ALL, -1, start, end, commonParam, 1);
            break;
        case VideoConstants.Category.FILM:
        case VideoConstants.Category.DFILM:
            // 电影、记录片 -- 按视频在专辑中的顺序降序排列
            // iptvVideoList =
            // this.facadeMysqlDao.getVideoMysqlDao().getVideoListByLimit(albumId,
            // page, pageSize,
            // broadcastId, SQLConstants.SQL_ORDER_DESC);
            if (albumMysql.getIs_end() != null && albumMysql.getIs_end() == 1) {
                iptvVideoList = albumVideoAccess.getVideoRange(targetAlbumId,
                        VideoTpConstant.QUERY_TYPE_ALL, -1, start, end, commonParam, 2);
            } else {
                iptvVideoList = albumVideoAccess.getVideoRange(targetAlbumId,
                        VideoTpConstant.QUERY_TYPE_ALL, -1, start, end, commonParam, 2);
            }
            break;
        case VideoConstants.Category.MUSIC:
            /*
             * 音乐频道下如果是按栏目展示，则按视频在专辑中的顺序升序排列，顺序相同的再按照创建时间降序排列；
             * 否则，二级分类是演唱会、颁奖礼、音乐电影、纪录片的，只取正片，且按视频在专辑中的顺序升序排列，顺序相同的再按照创建时间升序排列
             * ；其他二级分类，只取正片，且按视频在专辑中的顺序降序排列，顺序相同的再按照创建时间降序排列；
             */
            String subCategoryId = albumMysql.getSub_category();
            if (VideoUtil.isVarietyShow(albumMysql, commonParam)) {
                // iptvVideoList =
                // this.facadeMysqlDao.getVideoMysqlDao().getVideoListByPorder(albumId,
                // page, pageSize,
                // broadcastId);
                iptvVideoList = albumVideoAccess.getVideoRange(targetAlbumId,
                        VideoTpConstant.QUERY_TYPE_ALL, -1, start, end, commonParam, 1);
            } else if (StringUtils.isNotEmpty(subCategoryId)
                    && (subCategoryId.contains(VideoConstants.MusicSubCategory.CONCERT)
                            || subCategoryId.contains(VideoConstants.MusicSubCategory.AWARDS)
                            || subCategoryId.contains(VideoConstants.MusicSubCategory.MUSIC_FILM) || subCategoryId
                                .contains(VideoConstants.MusicSubCategory.DFILM))) {
                // 升序
                // iptvVideoList =
                // this.facadeMysqlDao.getVideoMysqlDao().getVideoListByLimit(albumId,
                // page, pageSize,
                // broadcastId, SQLConstants.SQL_ORDER_ASC);
                iptvVideoList = albumVideoAccess.getVideoRange(targetAlbumId,
                        VideoTpConstant.QUERY_TYPE_ALL, -1, start, end, commonParam, 2);
            } else {
                // 降序
                // iptvVideoList =
                // this.facadeMysqlDao.getVideoMysqlDao().getVideoListByLimit(albumId,
                // page, pageSize,
                // broadcastId, SQLConstants.SQL_ORDER_DESC);
                iptvVideoList = albumVideoAccess.getVideoRange(targetAlbumId,
                        VideoTpConstant.QUERY_TYPE_ALL, 1, start, end, commonParam, 2);
            }

            break;
        default:
            // 其他--按视频在专辑中的顺序降序排列
            // iptvVideoList =
            // this.facadeMysqlDao.getVideoMysqlDao().getVideoListByLimit(albumId,
            // page, pageSize,
            // broadcastId, SQLConstants.SQL_ORDER_DESC);
            iptvVideoList = albumVideoAccess.getVideoRange(targetAlbumId,
                    VideoTpConstant.QUERY_TYPE_ALL, 1, start, end, commonParam, 2);
            break;
        }

        return iptvVideoList;
    }

    /**
     * @param albumMysql
     * @param start
     * @param end
     * @param sourceType
     *            参见VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_*
     * @param commonParam
     * @return
     */
    public List<VideoMysqlTable> getLecomAlbumSeriesList(AlbumMysqlTable albumMysql, int sourceType,
            Integer albumVarietyShow, Integer start, Integer end, CommonParam commonParam) {
        List<VideoMysqlTable> iptvVideoList = null;

        int categoryId = MmsTpConstant.MMS_CATEGARY_ALL;
        if (albumMysql.getCategory() != null) {
            categoryId = albumMysql.getCategory();
        }

        switch (sourceType) { // 先按请求资源类型来分
        case VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_ALL:
            iptvVideoList = albumVideoAccess.getVideoRange(albumMysql.getId(),
                    VideoTpConstant.QUERY_TYPE_ALL, -1, start, end, commonParam, 0);
            break;
        case VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_POSITIVE:
            switch (categoryId) {
            case MmsTpConstant.MMS_CATEGARY_FILM:
            case MmsTpConstant.MMS_CATEGARY_TV:
            case MmsTpConstant.MMS_CATEGARY_VARIETY:
            case MmsTpConstant.MMS_CATEGARY_CARTOON:
            case MmsTpConstant.MMS_CATEGARY_DFILM:
            case MmsTpConstant.MMS_CATEGARY_SPORT:
            case MmsTpConstant.MMS_CATEGARY_EDUCATION:
                // 电影、电视剧、综艺、动漫、纪录片、体育、教育频道，正片剧集去全部正片视频，正序
                iptvVideoList = albumVideoAccess.getVideoRange(albumMysql.getId(),
                        VideoTpConstant.QUERY_TYPE_POSITIVE, -1, start, end, commonParam, 0);
                break;
            default:
                // 音乐频道或其他频道，正片取全部数据，正序
                iptvVideoList = albumVideoAccess.getVideoRange(albumMysql.getId(),
                        VideoTpConstant.QUERY_TYPE_ALL, -1, start, end, commonParam, 0);
                break;
            }
            break;
        case VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_ATTACHING:
            switch (categoryId) {
            case MmsTpConstant.MMS_CATEGARY_FILM:
            case MmsTpConstant.MMS_CATEGARY_TV:
            case MmsTpConstant.MMS_CATEGARY_VARIETY:
            case MmsTpConstant.MMS_CATEGARY_CARTOON:
            case MmsTpConstant.MMS_CATEGARY_DFILM:
            case MmsTpConstant.MMS_CATEGARY_SPORT:
            case MmsTpConstant.MMS_CATEGARY_EDUCATION:
                // 电影、电视剧、综艺、动漫、纪录片、教育频道，周边视频取全部非正片视频，倒序，体育频道只有在设置了综艺展示才有周边视频；获取数据后再筛选
                if (categoryId != MmsTpConstant.MMS_CATEGARY_SPORT
                        || (albumVarietyShow != null && 1 == albumVarietyShow)) {
                    iptvVideoList = albumVideoAccess.getVideoRange(albumMysql.getId(),
                            VideoTpConstant.QUERY_TYPE_NON_POSITIVE, 1, start, end, commonParam, 0);
                }
                break;
            default:
                // 其他频道无周边视频
                break;
            }
            break;
        default:
            break;
        }

        return iptvVideoList;
    }

    /**
     * 老板需求，纸牌屋专辑增加“纸牌屋系列”子分类
     * @param albumId
     * @param subCategoryNames
     * @return
     */
    private String getSubCategoryName(Long albumId, String subCategoryNames) {
        if (albumId != null && (albumId == 92849 || albumId == 95134 || albumId == 96123)) {
            subCategoryNames = ("纸牌屋合集" + ",") + subCategoryNames;
        }

        return (subCategoryNames == null ? "" : subCategoryNames).replace(",", " ");
    }

    /**
     * @param commonParam
     * @return
     */
    public Stream getStream(CommonParam commonParam) {
        return VideoUtil.getUserSettringStreams(commonParam);
    }

    /**
     * 获得视频的码流文件信息
     */
    public List<Stream> getVideoStreams(Long videoId, Integer channelId, CommonParam commonParam, Boolean supportDV) {
        VideoMysqlTable video = albumVideoAccess.getVideo(videoId, commonParam);

        if (video == null) {
            ErrorCodeConstant.throwLetvCommonException(ErrorCodeConstant.VIDEO_NOT_FOUND, commonParam.getLangcode());
        }

        String streams = video.getPlay_streams();
        List<Stream> streamList = this.sortAndFilterStreamCode(streams, video.getCategory(),
                commonParam.getTerminalBrand(), commonParam.getLangcode(), "0", commonParam);
        String videoTypes = "";
        // String[] videoStreams = streams.split(",");
        for (Stream stream : streamList) {
            if ("Android TV on MStar Amber3 S40".equalsIgnoreCase(commonParam.getTerminalSeries())
                    || "Android TV on MStar Amber3".equalsIgnoreCase(commonParam.getTerminalSeries())
                    || "Letv S40".equalsIgnoreCase(commonParam.getTerminalSeries())) {// S40去掉3D码流
                if (stream.getCode().startsWith("3d")) {
                    // stream.setEnable(false);
                    stream.setCanDown("0");
                }
            }
            // if (TerminalTool.isNotSupport4KBySeries(terminalSeries)) {//
            // S40去掉3D码流
            // if (stream.getCode().startsWith("4k")) {
            // stream.setEnabled("0");
            // }
            // }
            String streamCode = stream.getCode();
            // if
            // (stream.getCode().equalsIgnoreCase(StreamConstants.CODE_NAME_1080p6m)
            // ||
            // stream.getCode().equalsIgnoreCase(StreamConstants.CODE_NAME_1080p3m))
            // {
            // for (String realStream : videoStreams) {
            // if (realStream.equalsIgnoreCase(stream.getCode())
            // ||
            // ((stream.getCode().equalsIgnoreCase(StreamConstants.CODE_NAME_1080p6m))
            // && realStream
            // .equalsIgnoreCase(StreamConstants.CODE_NAME_1080p3m))) {
            // streamCode = realStream;
            // }
            // }
            // }
            videoTypes += VideoCommonUtil.getVideoTypeByStream(streamCode) + ",";
        }

        String mids = video.getMid();

        MmsStore mmsStore = this.facadeTpDao.getVideoTpDao().getPlayUrlByVid_V3(mids, videoTypes, false);
        List<Stream> finalList = new LinkedList<Stream>();
        if (mmsStore != null) {
            List<MmsInfo> listInfo = mmsStore.getData();
            if (!CollectionUtils.isEmpty(listInfo)) {
                for (MmsInfo info : listInfo) {
                    List<MmsFile> fileList = info.getInfos();
                    if (!CollectionUtils.isEmpty(fileList)) {
                        for (MmsFile file : fileList) {
                            Integer vtype = file.getVtype();
                            String code = VideoTpDao.CODE_STREAM_MAP.get(vtype);
                            if (code != null) {
                                String realCode = this.getStreamByVideoType(code);
                                Long fileSize = file.getGsize();
                                for (Stream stream : streamList) {
                                    if (realCode.equals(LetvStreamCommonConstants.CODE_NAME_DOLBY_VISION_4K)
                                            && !supportDV) {
                                        continue;
                                    }
                                    if (stream.getCode().equalsIgnoreCase(realCode)) {
                                        stream.setFileSize(fileSize);
                                        finalList.add(stream);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        streamList = finalList;
        if (!CollectionUtils.isEmpty(streamList)) {
            // 包含3Ｍ但是不包含６Ｍ，则不可以下载
            if (!streams.contains(LetvStreamCommonConstants.CODE_NAME_1080p6m)) {
                for (Stream stream : streamList) {
                    if (LetvStreamCommonConstants.CODE_NAME_1080p6m.equalsIgnoreCase(stream.getCode())) {
                        stream.setCanDown("0");
                    }
                }
            }
        }
        return streamList;
    }

    /**
     * @return
     */
    private String getStreamByVideoType(String videoType) {
        String stream = "";
        String code = videoType.substring(videoType.indexOf("_") + 1);
        if ("1080p_3D".equalsIgnoreCase(code)) {
            stream = "3d1080p";
        } else if ("1080p6m_3D".equalsIgnoreCase(code)) {
            stream = "3d1080p6m";
        } else if ("720p_3D".equalsIgnoreCase(code)) {
            stream = "3d720p";
        } else {
            stream = code;
        }
        return stream;
    }

    /**
     * 下载鉴权
     * @param stream
     * @param loginTime
     * @param terminalType
     * @return
     */
    public Response<String> checkDownload(Long videoId, String stream, String loginTime, String channelId,
            Integer terminalType, CommonParam commonParam) {

        Response<String> rps = new Response<String>();
        VideoMysqlTable video = albumVideoAccess.getVideo(videoId, commonParam);
        if (video == null) {
            rps.setResultStatus(0);
            rps.setErrCode(VideoConstants.VIDEO_DOWNLOAD_NULL);
            rps.setErrMsg(MessageUtils.getMessage(VideoConstants.VIDEO_DOWNLOAD_NULL, commonParam.getLangcode()));
            return rps;
        }

        if (StringUtils.isBlank(commonParam.getUserId()) || (StringUtil.toLong(commonParam.getUserId(), -1L) <= 0)) {
            // 兼容老版本不传userId的问题
            Long userId = userService.getUserCenterIdByUsername(commonParam);
            if (LetvUserVipCommonConstant.isUseridLegal(userId)) {
                commonParam.setUserId(Long.toString(userId));
            } else {
                rps.setResultStatus(0);
                rps.setErrCode(ErrorCodeConstants.USER_NOT_EXIST);
                rps.setErrMsg(MessageUtils.getMessage(ErrorCodeConstants.USER_NOT_EXIST, commonParam.getLangcode()));
                return rps;
            }
        }

        AlbumMysqlTable album = albumVideoAccess.getAlbum(video.getPid(), commonParam);

        UserPlayAuth authResult = null;
        if (album != null) {
            // 先判断是否单点片子，单点片子不支持下载
            if (this.isSingleOrderAlbum(video, album, commonParam)) {
                rps.setResultStatus(0);
                rps.setErrCode(VideoConstants.VIDEO_DOWNLOAD_AUTHFAIL);
                rps.setErrMsg(MessageUtils.getMessage(VideoConstants.VIDEO_DOWNLOAD_AUTHFAIL, commonParam.getLangcode()));

                return rps;
            }

            stream = this.getFinalDownLoadStream(stream);
            Long mid = video.getMidByStream(stream);
            Integer splatId = 502;

            MmsStore store = this.getVideoPlayInfo(mid, stream, false, "download", "download", video.getPlay_streams()
                    .split(","), splatId, video.getPid(), video.getId(), false, commonParam);

            MmsInfo data = store.getData().get(0);
            MmsFile file = data.getInfos().get(0);

            String storePath = null;
            if (file != null) {
                storePath = file.getStorePath();
            }

            authResult = this.canPlay(album.getId(), videoId, terminalType, null, null, storePath, commonParam);
            if (authResult != null && authResult.getStatus() != null
                    && authResult.getStatus() == CommonConstants.PLAY_SERVICE_UNOPEN) {
                ErrorCodeConstant.throwLetvCommonExceptionByThirdpatyCode(authResult.getErrorCode(),
                        ErrorCodeConstant.LETV_THIRDPARTY_CONSUME_MAP);
            }
        }

        if (authResult != null) {
            return new Response<String>(authResult.getToken());
        }

        rps.setResultStatus(0);
        rps.setErrCode(VideoConstants.VIDEO_DOWNLOAD_AUTHFAIL);
        rps.setErrMsg(MessageUtils.getMessage(VideoConstants.VIDEO_DOWNLOAD_AUTHFAIL, commonParam.getLangcode()));
        return rps;

    }

    /**
     * 下载鉴权V2
     * @param stream
     * @param loginTime
     * @param terminalType
     * @return
     */
    public Response<String> checkDownloadV2(Long videoId, String stream, String loginTime, String channelId,
            Integer terminalType, CommonParam commonParam) {

        Response<String> rps = new Response<String>();
        VideoMysqlTable video = albumVideoAccess.getVideo(videoId, commonParam);
        if (video == null) {
            rps.setResultStatus(0);
            rps.setErrCode(VideoConstants.VIDEO_DOWNLOAD_NULL);
            rps.setErrMsg(MessageUtils.getMessage(VideoConstants.VIDEO_DOWNLOAD_NULL, commonParam.getLangcode()));
            return rps;
        }

        if (StringUtils.isBlank(commonParam.getUserId()) || (StringUtil.toLong(commonParam.getUserId(), -1L) <= 0)) {
            // 兼容老版本不传userId的问题
            Long userId = userService.getUserCenterIdByUsername(commonParam);
            if (LetvUserVipCommonConstant.isUseridLegal(userId)) {
                commonParam.setUserId(Long.toString(userId));
            } else {
                rps.setResultStatus(0);
                rps.setErrCode(ErrorCodeConstants.USER_NOT_EXIST);
                rps.setErrMsg(MessageUtils.getMessage(ErrorCodeConstants.USER_NOT_EXIST, commonParam.getLangcode()));
                return rps;
            }
        }

        AlbumMysqlTable album = albumVideoAccess.getAlbum(video.getPid(), commonParam);

        BossTpResponse<ValidateServiceTp> response = null;
        if (album != null) {
            // 先判断是否单点片子，单点片子不支持下载
            if (this.isSingleOrderAlbum(video, album, commonParam)) {
                rps.setResultStatus(0);
                rps.setErrCode(VideoConstants.VIDEO_DOWNLOAD_AUTHFAIL);
                rps.setErrMsg(MessageUtils.getMessage(VideoConstants.VIDEO_DOWNLOAD_AUTHFAIL, commonParam.getLangcode()));

                return rps;
            }

            stream = this.getFinalDownLoadStream(stream);
            Long mid = video.getMidByStream(stream);
            Integer splatId = 502;

            MmsStore store = this.getVideoPlayInfo(mid, stream, false, "download", "download", video.getPlay_streams()
                    .split(","), splatId, video.getPid(), video.getId(), false, commonParam);

            MmsInfo data = store.getData().get(0);
            MmsFile file = data.getInfos().get(0);

            String storePath = null;
            if (file != null) {
                storePath = file.getStorePath();
            }

            boolean needVipValidate = false;
            if (TerminalUtil.supportDistributedPaying(commonParam)) {
                if ((null != album && album.isPay(commonParam.getP_devType()))
                        || (null != video && VideoCommonUtil.isCharge(video.getPay_platform(),
                                video.getPlay_platform(), commonParam.getP_devType()))) {
                    needVipValidate = true;
                } else { // free case: 限制仅低码率可下载
                    String streamIsCharge = LetvStreamCommonConstants.ifCharge(stream);
                    if (null == streamIsCharge || "0".equals(streamIsCharge)) {
                        return new Response<String>("free");
                    }
                }
            } else {
                needVipValidate = true;
            }

            if (needVipValidate) {
                response = this.facadeTpDao.getVipTpDao().validateVideoPlayService(commonParam.getUserId(),
                        album.getId().toString(), storePath, commonParam, -1);
                if (response.getCode() == 0 && response.getData() != null
                        && response.getData().getStatus() == CommonConstants.PLAY_SERVICE_UNOPEN
                        && response.getData().getErrorCode() != null) {
                    ErrorCodeConstant.throwLetvCommonExceptionByThirdpatyCode(response.getData().getErrorCode()
                            .toString(), ErrorCodeConstant.LETV_THIRDPARTY_CONSUME_MAP);
                }
            }
        }

        if (null != response && response.getCode() == 0 && response.getData() != null
                && response.getData().getToken() != null) {
            return new Response<String>(response.getData().getToken());
        }

        rps.setResultStatus(0);
        rps.setErrCode(VideoConstants.VIDEO_DOWNLOAD_AUTHFAIL);
        rps.setErrMsg(MessageUtils.getMessage(VideoConstants.VIDEO_DOWNLOAD_AUTHFAIL, commonParam.getLangcode()));
        return rps;

    }

    /**
     * 15M线下，为保证边播边放同步
     * @param stream
     * @return
     */
    private String getFinalDownLoadStream(String stream) {
        if ("1080p".equalsIgnoreCase(stream)) {
            stream = LetvStreamCommonConstants.CODE_NAME_1080p6m;
        } else if ("3d1080p".equalsIgnoreCase(stream)) {
            stream = LetvStreamCommonConstants.CODE_NAME_3d1080p6M;
        }
        return stream;
    }

    /**
     * @param mid
     * @param stream
     * @param ts
     * @param actionType
     * @return
     */
    public MmsStore getVideoPlayInfo(Long mid, String stream, Boolean ts, String actionType, String playType,
            String[] hasStreams, Integer splatId, Long aid, Long vid, boolean isMarlin, CommonParam commonParam) {
        // //杜比频道特殊处理
        // if(!("download").equalsIgnoreCase(actionType)){
        // ts=getFinalTS(stream);
        // }
        stream = this.getFinalPlayStream(hasStreams, stream, playType, actionType, aid);
        MmsStore store = this.getMmsPlayInfo(vid, mid, stream, ts, splatId, isMarlin, commonParam);
        Boolean validate = this.validateMmsStore(store, commonParam.getLangcode());
        if (!validate) {
            ErrorCodeConstant.throwLetvCommonException(ErrorCodeConstant.GET_PLAYURL_ERROR, commonParam.getLangcode());
        }
        return store;
    }

    private String getFinalPlayStream(String[] hasStreams, String currentStream, String playType, String actionType,
            Long aid) {
        String finalPlayStream = currentStream;
        playType = (playType == null || "".equalsIgnoreCase(playType.trim())) ? "db" : playType;// 默认点播
        if (LetvStreamCommonConstants.CODE_NAME_1080p6m.equalsIgnoreCase(currentStream) && "play".equals(actionType)
                && "db".equalsIgnoreCase(playType) && !LetvStreamCommonConstants.contains(aid)) {// 播放(点播)的1080p6m强制为3m
            List<String> streams = Arrays.asList(hasStreams);
            if (streams.contains(LetvStreamCommonConstants.CODE_NAME_1080p3m)) {
                finalPlayStream = LetvStreamCommonConstants.CODE_NAME_1080p3m;
            }
        }
        return finalPlayStream;
    }

    /**
     * 获取播放信息
     * @param mid
     *            媒资ID
     * @param stream
     *            码流
     * @param ts
     *            是否请求m3u8文件
     * @return
     */
    private MmsStore getMmsPlayInfo(Long vid, Long mid, String stream, Boolean ts, Integer splatId, boolean isMarlin,
            CommonParam commonParam) {
        if (mid == null) {
            return null;
        }
        String videoType = VideoCommonUtil.getVideoTypeByStream(stream);
        MmsStore store = this.facadeTpDao.getVideoTpDao().getPlayUrl_V2(commonParam.getClientIp(), vid, mid, videoType,
                ts, "", splatId, isMarlin, commonParam.getBroadcastId());
        if (videoType != null
                && videoType.equalsIgnoreCase(LetvStreamCommonConstants.VIDEO_TYPE_MP4_PREFIX
                        + LetvStreamCommonConstants.CODE_NAME_4K)) {
            MmsFile finalFile = null;
            if (store != null && store.getData() != null && store.getData().size() > 0) {
                MmsInfo info = store.getData().get(0);
                if (!CollectionUtils.isEmpty(info.getInfos())) {
                    Integer[] vt_4k = { 65, 45, 55, 54 };
                    for (Integer vt : vt_4k) {
                        for (MmsFile file : info.getInfos()) {
                            if (file.getVtype().intValue() == vt.intValue()) {
                                finalFile = file;
                            }
                        }
                        if (finalFile != null) {
                            info.getInfos().set(0, finalFile);// 确保第一个是最终想要的
                            break;
                        }
                    }
                }
            }
        }
        return store;
    }

    private String checkUrl(String url) {
        String tmpUrl = url.substring(0, url.indexOf("?") + 1);
        String param = url.substring(url.indexOf("?") + 1);
        String[] arr = param.split("&");
        StringBuffer sb = new StringBuffer(tmpUrl);
        for (String p : arr) {
            if (p.equals("b=0")) {
                p = "b=600";
            }
            sb.append(p).append("&");
        }
        sb.delete(sb.lastIndexOf("&"), sb.length());
        return sb.toString();
    }

    public UserPlayAuth canPlay(Long albumId, Long videoId, Integer terminalType, Boolean isFromCntv,
            Boolean isFromCibn, String storePath, CommonParam commonParam) {
        UserPlayAuth result = new UserPlayAuth(VideoTpConstant.VIDEO_PLAY_SERVICE_OPEN);
        if (isFromCntv == null) {
            isFromCntv = false;
        }
        if (isFromCibn == null) {
            isFromCibn = false;
        }
        if (terminalType == null) {
            terminalType = VideoTpConstant.VIDEO_PLAY_CHECK_AUTH_TERMINAL_TYPE;
        }
        AlbumMysqlTable album = null;
        if (albumId != null) {
            album = albumVideoAccess.getAlbum(albumId, commonParam);
        } else if (videoId != null) {
            VideoMysqlTable video = albumVideoAccess.getVideo(videoId, commonParam);
            if (video != null && video.getPid() != null && video.getPid() > 0) {
                album = albumVideoAccess.getAlbum(video.getPid(), commonParam);
            }
        }
        if (album != null) {
            result = this.facadeTpDao.getVideoTpDao().getUserPlayAuth(albumId,
                    StringUtil.toLong(commonParam.getUserId()), terminalType, isFromCntv, isFromCibn, storePath,
                    commonParam);
        }
        return result;
    }

    /**
     * 验证签名
     */
    public Boolean checkSig(Long videoId, Long timestamp, String stream, String sig) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vrsVideoInfoId", videoId);
        params.put("timestamp", timestamp);
        params.put("stream", stream);
        params.put("sig", sig);
        Boolean isValid = CommonUtil.checkSig(params);
        return isValid;
    }

    /**
     * 验证签名
     */
    public Boolean checkSig(Long videoId, Long timestamp, String sig) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("videoid", videoId);
        params.put("timestamp", timestamp);
        params.put("sig", sig);
        Boolean isValid = CommonUtil.checkSig(params);
        return isValid;
    }

    /**
     * 获得向下兼容码流
     * @param currentStream
     * @return
     */
    public static String getBelowStream(String[] hasStreams, String currentStream, String sortStreams) {
        String finalStream = "";

        String[] sortArr = sortStreams.split("#");
        List<String> sortList = Arrays.asList(sortArr);
        Integer index = sortList.indexOf(currentStream);
        if (index > -1 && (index != sortList.size() - 1)) {// 存在且不是最后一个
            sortList = sortList.subList(index + 1, sortList.size());
            for (String s : sortList) {
                for (String hs : hasStreams) {
                    if (s.equalsIgnoreCase(hs)) {
                        finalStream = s;
                    }
                }
                if (StringUtils.isNotEmpty(finalStream)) {
                    break;
                }
            }
        }

        return finalStream;
    }

    /**
     * 根据格式 获取最终url
     */
    public String getFinalPlayUrlByFormat(String playUrl, String ifCharge, String terminalSeries, Boolean expectTS,
            Boolean expectTSActual, Boolean dispach302, Boolean exceptNomalActual, Integer termid, Boolean clientDeside) {
        String actualPlayUrl = playUrl;
        if (ifCharge == null || "".equals(ifCharge)) {
            ifCharge = "0";
        }
        if (terminalSeries == null || "".equals(terminalSeries)) {
            terminalSeries = "un";
        }
        terminalSeries = this.StringFilter(terminalSeries);
        if (termid == null) {
            termid = 4;// 电视
            if (TerminalTool.isBox(terminalSeries)) {
                termid = 3;// 盒子
            }
        }
        Boolean actual = false;
        if (Boolean.TRUE == expectTS) {// m3u8文件
            actualPlayUrl = playUrl + "&tag=tvts&termid=" + termid + "&ostype=android&pay=" + ifCharge + "&hwtype="
                    + terminalSeries;// termid: 0：未知 1：PC端 2：移动端(移动设备)
                                     // 3：盒端(各种型号盒子) 4：电视(超级电视/海信电视)
            if (Boolean.TRUE == expectTSActual) {
                actual = true;
            }
        } else if (Boolean.TRUE == dispach302) {// 期待302跳转
            if (clientDeside) {
                playUrl += "&format=0";
            }
            actualPlayUrl = playUrl + "&tag=tv&termid=" + termid + "&ostype=android&pay=" + ifCharge + "&hwtype="
                    + terminalSeries;// termid: 0：未知 1：PC端 2：移动端(移动设备)
                                     // 3：盒端(各种型号盒子) 4：电视(超级电视/海信电视)
        } else {// 获得正常文件
            if (!clientDeside) {
                playUrl += "&expect=6&format=2";
            }
            actualPlayUrl = playUrl + "&tag=tv&termid=" + termid + "&ostype=android&pay=" + ifCharge + "&hwtype="
                    + terminalSeries;// 6个节点用于多线程 /format 0:302跳转；1json 2xml
            if (exceptNomalActual) {
                actual = true;
            }
        }
        if (actual) {// 获得真实播放地址
            CdnDispatchResponse cndDispatchResponse = this.facadeTpDao.getVideoTpDao().cndDispatchResponse(
                    actualPlayUrl);
            List<String> urlList = cndDispatchResponse.getNodelist().getUrls();
            if (urlList.size() > 0) {
                actualPlayUrl = urlList.get(0);
            }
        }
        return actualPlayUrl;
    }

    public String StringFilter(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String result = m.replaceAll("").trim();
        try {
            result = URLEncoder.encode(result, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("StringFilter error", e.getMessage(), e);
        }
        return result;
    }

    public VideoHot getHeadTailInfo(Integer cid, VideoMysqlTable video) {
        // 获取片头片尾
        VideoHot h = new VideoHot();
        if (cid != null && cid == VideoConstants.Category.TV && video != null) {
            Integer b = video.getBtime();
            Integer e = video.getEtime();
            h.setT(b == null ? 0 : b);
            h.setF(e == null ? 0 : e);
        }
        return h;
    }

    /**
     * 是否收费
     * @return
     */
    public Boolean ifChargeByVideoType(VideoMysqlTable video) {
        Boolean isCharge = false;

        if (video != null) {
            // 电影、电视剧、动漫的正片收费，其余免费
            if (video.getCategory() == VideoConstants.Category.TV
                    || video.getCategory() == VideoConstants.Category.FILM) {
                if (video.getVideo_type() != null && video.getVideo_type() == VideoConstants.VideoType.ZHENG_PIAN) {
                    isCharge = true;
                }
            } else if (video.getCategory() == VideoConstants.Category.CARTOON) {
                isCharge = true;
            }

            if (84866 == video.getPid() || 92063 == video.getPid() || 10000466 == video.getPid()) {
                isCharge = false;// 3个专辑内容属于优质内容，本期可放这3个专辑
                // 兔小贝儿歌84866，贝瓦儿歌92063，哈利学前班儿歌篇10000466
            }

            if (VideoConstants.Category.VARIETY == video.getCategory()) {
                if (video.getVideo_type() != null && video.getVideo_type() == VideoConstants.VideoType.ZHENG_PIAN) {
                    isCharge = true;
                }
            }
        }
        return isCharge;
    }

    /**
     * 验证合法性
     * @param store
     * @return
     */
    private Boolean validateMmsStore(MmsStore store, String langcode) {

        if (store == null) {
            return false;
        }
        if (store.getPlayStatus() != null && store.getPlayStatus() == 1 && StringUtils.isNotEmpty(store.getCountry())
                && store.getCountry().equals("CN")) {
            ErrorCodeConstant.throwLetvCommonException(ErrorCodeConstant.CN_PLAY_FORBIDDEN_CODE, langcode);
        }
        if (store.getPlayStatus() != null && store.getPlayStatus() == 1 && StringUtils.isNotEmpty(store.getCountry())
                && !store.getCountry().equals("CN")) {
            ErrorCodeConstant.throwLetvCommonException(ErrorCodeConstant.NOT_CN_PLAY_FORBIDDEN_CODE, langcode);
        }

        if (!store.getStatusCode().equalsIgnoreCase(VideoTpConstant.VIDEO_PLAY_MMS_DATA_CODE_SUCCESS)) {
            return false;
        }
        if (store.getData() == null) {
            return false;
        }
        if (store.getData().size() < 1) {
            return false;
        }
        if (store.getData().get(0).getInfos() == null) {
            return false;
        }
        if (store.getData().get(0).getInfos().size() < 1) {
            return false;
        }
        try {
            MmsInfo data = store.getData().get(0);
            MmsFile file = data.getInfos().get(0);

            file.setMainUrl(this.checkUrl(file.getMainUrl()));
            file.setBackUrl0(this.checkUrl(file.getBackUrl0()));
            file.setBackUrl1(this.checkUrl(file.getBackUrl1()));
            file.setBackUrl2(this.checkUrl(file.getBackUrl2()));
        } catch (Exception e) {
        }
        return true;
    }

    /**
     * 判断视频、专辑是否为正片
     * @param typeId
     *            类型id
     * @param categoryId
     * @return
     */
    private Boolean isPositive(Integer typeId, Integer categoryId) {
        return VideoCommonUtil.isPositive(1, categoryId, null, typeId);
    }

    private Collection<BaseData> touPingData(Long videoId, Integer startPos, Integer initPos, Integer pageSize,
            String history, Integer model, Long albumId, Integer pageNum, Integer categoryId, String imei,
            Boolean positive, CommonParam commonParam) {
        log.info("getPlayList_" + videoId + "_" + albumId + "_" + startPos + "_" + initPos + "_" + pageSize + "_"
                + positive);

        Collection<BaseData> data = null;
        pageSize = (pageSize == null || pageSize <= 0) ? 20 : pageSize; // pageSize容错
        if (categoryId != null
                && (VideoConstants.Category.TV == categoryId || VideoConstants.Category.CARTOON == categoryId)) {
            // 领先版仅电影、电视剧、卡通频道走按porder每页100条数据进行分页，其他频道不是，故这里只处理这3个频道的数据

            if (pageNum == null || pageNum < 0) {
                pageNum = 0;
            }

            int startIndex = 1;
            int endIndex = 1;

            if (positive == null || Boolean.FALSE == positive) {
                // 如果当前是预告片，则忽略startPos、initPos属性，直接去正片剧集列表中的1-100跳数据
                startIndex = 1;
            } else if (initPos != null && initPos != -1) {
                // 说明是第一次请求下拉列表，当前视频在分页数据中，则就取当前页数据
                startIndex = (initPos - pageSize / 2) >= 1 ? (initPos - pageSize / 2) : 1;
            } else if (startPos != null) {
                // 不是第一次请求，则当前视频不再分页数据中
                startIndex = startPos;
            }
            if (startIndex < 1) {
                startIndex = 1;
            }
            endIndex = startIndex + pageSize - 1;
            pageNum = startIndex / VideoTpConstant.LETV_LEADING_ALBUM_SERIES_PAGE_SIZE;
            int nexePageNum = endIndex / VideoTpConstant.LETV_LEADING_ALBUM_SERIES_PAGE_SIZE; // 可能存在的下一页

            data = new ArrayList<BaseData>((int) (VideoTpConstant.LETV_LEADING_ALBUM_SERIES_PAGE_SIZE / 0.75));
            LetvLeadingCommonResponse<LetvLeadingAlbumSeries> albumSeriesReponse = this.facadeTpDao.getVideoTpDao()
                    .getLetvLeadingAlbumSeriesInfo(albumId, pageNum, commonParam);
            int currentEndIndex = nexePageNum > pageNum ? (pageNum + 1)
                    * VideoTpConstant.LETV_LEADING_ALBUM_SERIES_PAGE_SIZE : endIndex;
            if (albumSeriesReponse != null) {
                currentEndIndex += 1;
                data.addAll(this.parseVideoSeriesFromLetvLeadingAlbumSeries(albumSeriesReponse.getData(), startIndex,
                        currentEndIndex, pageNum, commonParam));
            }
            if (nexePageNum > pageNum) {
                albumSeriesReponse = this.facadeTpDao.getVideoTpDao().getLetvLeadingAlbumSeriesInfo(albumId,
                        nexePageNum, commonParam);
                if (albumSeriesReponse != null) {
                    currentEndIndex += 1;
                    data.addAll(this.parseVideoSeriesFromLetvLeadingAlbumSeries(albumSeriesReponse.getData(),
                            currentEndIndex, endIndex, nexePageNum, commonParam));
                }
            }
        }
        return data;
    }

    public Collection<BaseData> getPlayList(Long videoId, Integer startPos, Integer initPos, Integer pageSize,
            String history, Integer model, Long albumId, Integer pageNum, Integer categoryId, String imei,
            Boolean positive, CommonParam commonParam) {

        boolean isTouPing = model != null && VideoConstants.PLAY_MODEL_TOUPING == model; // 是否是投屏，true-是，false-不是
        if (isTouPing) {
            return this.touPingData(videoId, startPos, initPos, pageSize, history, model, albumId, pageNum, categoryId,
                    imei, positive, commonParam);
        } else if (this.isLeChild(model)) {
            return this.getChildPlayList(videoId, startPos, initPos, pageSize, history, model, albumId, categoryId,
                    commonParam);
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LE
                .equalsIgnoreCase(commonParam.getTerminalApplication())) {
            return this.getLecomPlayList(videoId, startPos, initPos, pageSize, history, model, albumId, commonParam);
        } else {
            return this.getLetvPlayList(videoId, startPos, initPos, pageSize, history, model, albumId, categoryId,
                    commonParam);
        }

    }

    public Response<AlbumSeriesPlayListPageDto> getVideoPlaySeriesList(Integer actionType, Long videoId, Long albumId,
            Integer stype, Integer page, Integer functionType, String history, CommonParam commonParam) {
        Response<AlbumSeriesPlayListPageDto> response = new Response<AlbumSeriesPlayListPageDto>();
        AlbumSeriesPlayListPageDto seriesPage = null;
        if (actionType != null) {
            seriesPage = new AlbumSeriesPlayListPageDto();
            boolean forcePage = true;
            switch (actionType) {
            case VideoConstants.VIDEO_PLAY_SERIES_PAGE_GET_BY_VIDEO_ID:
                VideoMysqlTable video = albumVideoAccess.getVideo(videoId, commonParam);
                if (video != null) {
                    if (video.getPid() == null || video.getPid() <= 0) {
                        // 单视频走推荐
                        seriesPage = getRecVideoPlaySeriesPage(false, history, video, seriesPage, commonParam);
                    } else {
                        // 按id获取，只能是下拉列表，这里使用forcePage = true
                        seriesPage = getLecomAlbumSeriesPageByVid(video.getPid(), video, forcePage, history,
                                commonParam);
                    }
                }
                break;
            case VideoConstants.VIDEO_PLAY_SERIES_PAGE_GET_BY_PAGE:
                AlbumMysqlTable albumMysql = albumVideoAccess.getAlbum(albumId, commonParam);
                if (albumMysql != null) {
                    // 专辑详情页和播放下拉列表均能使用分页，但实际分页效果不同，专辑详情页（电影、电视剧、综艺、动漫、纪录片、体育、教育）周边视频不分页
                    forcePage = !(functionType != null && VideoConstants.VIDEO_PLAY_SERIES_PAGE_FUNCTION_TYPE_ALBUM_DETAIL == functionType);
                    boolean albumPositive = albumMysql.getAlbum_attr() != null && albumMysql.getAlbum_attr() == 1;
                    Integer categoryId = albumMysql.getCategory();

                    seriesPage.setAlbumId(String.valueOf(albumId));
                    seriesPage.setAlbumPositive(albumPositive ? 1 : 0);
                    seriesPage.setCurPage(page);
                    seriesPage.setOrderType(VideoTpConstant.VIDEO_MMS_SERIES_ORDER_ASC);
                    seriesPage.setPageSize(VideoTpConstant.ALBUM_SERIES_PAGE_SIZE);
                    seriesPage.setStype(stype);
                    if (categoryId != null
                            && (categoryId == MmsTpConstant.MMS_CATEGARY_TV
                                    || categoryId == MmsTpConstant.MMS_CATEGARY_VARIETY
                                    || categoryId == MmsTpConstant.MMS_CATEGARY_CARTOON
                                    || categoryId == MmsTpConstant.MMS_CATEGARY_DFILM || categoryId == MmsTpConstant.MMS_CATEGARY_EDUCATION)) {
                        // 电视剧/综艺/动漫/纪录片/教育频道考虑媒资后台“是否剧集展示-varietyShow”字段
                        seriesPage.setVarietyShow(VideoUtil.isVarietyShow(albumMysql, commonParam) ? 1 : 0);
                    }

                    List<VideoMysqlTable> videoList = getLecomAlbumSeriesPage(albumMysql.getId(), categoryId,
                            forcePage, seriesPage, commonParam);

                    // 清洗、组装数据
                    if (!CollectionUtils.isEmpty(videoList)) {
                        Collection<BaseData> series = new ArrayList<BaseData>();
                        if (albumPositive) {
                            switch (stype) {
                            case VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_POSITIVE:
                                // 正片直接封装
                                for (VideoMysqlTable vi : videoList) {
                                    VideoDto videoDto = parseLecomAlbumSeriesVideo(vi);
                                    if (videoDto != null) {
                                        series.add(videoDto);
                                    }
                                }
                                seriesPage.setData(series);
                                break;
                            case VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_ATTACHING:
                                // 周边视频有区分
                                switch (categoryId) {
                                case MmsTpConstant.MMS_CATEGARY_FILM:
                                    for (VideoMysqlTable vi : videoList) {
                                        if (vi != null) {
                                            VideoDto videoDto = parseLecomAlbumSeriesVideo(vi);
                                            if (videoDto != null) {
                                                series.add(videoDto);
                                            }
                                        }
                                    }
                                    seriesPage.setData(series);
                                    break;
                                case MmsTpConstant.MMS_CATEGARY_TV:
                                case MmsTpConstant.MMS_CATEGARY_VARIETY:
                                case MmsTpConstant.MMS_CATEGARY_CARTOON:
                                case MmsTpConstant.MMS_CATEGARY_DFILM:
                                case MmsTpConstant.MMS_CATEGARY_EDUCATION:
                                    // 播放下拉，或电视剧、综艺、动漫、纪录片、教育频道，周边视频取全部非正片视频（但不包含预告），故这里不做分页，取所有非正片视频后再做筛选
                                    for (VideoMysqlTable vi : videoList) {
                                        if (forcePage
                                                || (vi != null && vi.getVideo_type() != null && MmsTpConstant.MMS_VIDEO_TYPE_YU_GAO_PIAN != vi
                                                        .getVideo_type())) {
                                            VideoDto videoDto = parseLecomAlbumSeriesVideo(vi);
                                            if (videoDto != null) {
                                                series.add(videoDto);
                                            }
                                        }
                                    }
                                    seriesPage.setData(series);
                                    break;
                                default:
                                    // 其他频道无周边视频
                                    break;
                                }
                                break;
                            default:
                                break;
                            }
                        } else {
                            // 非正片剧集，只有选集视频
                            switch (stype) {
                            case VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_POSITIVE:
                                for (VideoMysqlTable vi : videoList) {
                                    VideoDto videoDto = parseLecomAlbumSeriesVideo(vi);
                                    if (videoDto != null) {
                                        series.add(videoDto);
                                    }
                                }
                                seriesPage.setData(series);
                                break;
                            default:
                                break;
                            }
                        }
                    }
                }
                break;
            default:
                break;
            }

            response.setData(seriesPage);
        }

        return response;
    }

    private AlbumSeriesPlayListPageDto getRecVideoPlaySeriesPage(boolean peakAlbum, String history,
            VideoMysqlTable video, AlbumSeriesPlayListPageDto seriesPage, CommonParam commonParam) {
        Collection<BaseData> playList = new ArrayList<BaseData>();

        if (!peakAlbum) {
            VideoDto current = new VideoDto();
            current.setVideoId(String.valueOf(video.getId()));
            current.setName(video.getName_cn());
            current.setImg(video.getPic(400, 300));
            current.setAlbumId(String.valueOf(video.getPid()));
            current.setCategoryId(video.getCategory());
            if (video.getCategory() == VideoConstants.Category.MUSIC) {// 音乐
                current.setSubName(video.getStarring());// 副标题为歌星
            } else if (video.getCategory() == VideoConstants.Category.SPORT) {// 体育
                // 无副标题
            } else {
                current.setSubName(video.getSub_title());
            }
            playList.add(current);
        }

        Collection<BaseData> recList = recommendationService
                .getSingleBlock(video.getCategory(), null, video.getId().intValue(), null,
                        VideoConstants.LE_SCREEN_SIZE * 2, "rec_0010", null, history, null, commonParam).getData();
        if (!CollectionUtils.isEmpty(recList)) {
            for (BaseData baseData : recList) {
                if ((peakAlbum && baseData instanceof AlbumDto) || (!peakAlbum && baseData instanceof VideoDto)) {
                    playList.add(baseData);
                }
            }
        }

        seriesPage.setData(playList);
        seriesPage.setCurPage(1);
        seriesPage.setTotalPage(1);
        seriesPage.setPageSize(playList.size());
        seriesPage.setTotalNum(playList.size());
        seriesPage.setOrderType(VideoTpConstant.VIDEO_MMS_SERIES_ORDER_ASC);
        seriesPage.setStype(VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_REC);
        return seriesPage;
    }

    public Collection<BaseData> getChildPlayList(Long videoId, Integer startPos, Integer initPos, Integer pageSize,
            String history, Integer model, Long albumId, Integer categoryId, CommonParam commonParam) {

        Collection<BaseData> playList = new ArrayList<BaseData>();
        VideoMysqlTable video = albumVideoAccess.getVideo(videoId, commonParam);
        if (video != null) {
            // 视频无专辑逻辑，走单板块推荐
            if ((video.getPid() == null) || (video.getPid() == 0)) {
                VideoDto current = new VideoDto();
                current.setVideoId(String.valueOf(video.getId()));
                current.setName(video.getName_cn());
                current.setImg(video.getPic(400, 300));
                current.setAlbumId(String.valueOf(video.getPid()));
                current.setCategoryId(video.getCategory());
                if (video.getCategory() == VideoConstants.Category.MUSIC) {// 音乐
                    current.setSubName(video.getStarring());// 副标题为歌星
                } else if (video.getCategory() == VideoConstants.Category.SPORT) {// 体育
                    // 无副标题
                } else {
                    current.setSubName(video.getSub_title());
                }
                playList.add(current);

                Collection<BaseData> recList = recommendationService
                        .getSingleBlock(video.getCategory(), null, videoId.intValue(), null,
                                VideoConstants.SCREEN_SIZE * 3, "rec_0010", null, history, null, commonParam).getData();
                if (!CollectionUtils.isEmpty(recList)) {
                    for (BaseData baseData : recList) {
                        if (baseData instanceof VideoDto) {
                            playList.add(baseData);
                        }
                    }
                }
            } else {
                albumId = video.getPid();
            }
        }
        // 专辑逻辑的儿童下拉列表
        AlbumMysqlTable iptvAlbumInfo = null;
        if (albumId != null) {
            iptvAlbumInfo = albumVideoAccess.getAlbum(albumId, commonParam);
            if (iptvAlbumInfo != null) {
                Integer isPushChild = iptvAlbumInfo.getIsPushChild();
                if (categoryId == VideoConstants.Category.FILM) {
                    // 如果当前视频属于电影频道，则走单板块推荐；可以确定的是当前视频是正片
                    this.getFilmPlayListOfLeChild(playList, albumId, iptvAlbumInfo, initPos, pageSize, startPos,
                            history, commonParam);
                } else if (isPushChild != null && isPushChild == 1) {// 推送到儿童
                    this.getPlayListOfLeChild(playList, albumId, iptvAlbumInfo, initPos, pageSize, startPos,
                            commonParam);
                } else {
                    this.getOtherPlayListOfLeChild(playList, albumId, iptvAlbumInfo, initPos, pageSize, startPos,
                            commonParam);
                }
            } else {
                log.info(" getChildPlayList albumId:" + albumId + " album object is null");
            }
        } else {
            log.info("getChildPlayList albumId  is null");
        }
        return playList;
    }

    /**
     * 是否儿童请求
     * @param model
     * @return
     */
    private boolean isLeChild(Integer model) {
        return model != null && model == VideoConstants.PLAY_MODEL_CHILD;
    }

    /**
     * 正片下拉列表setVideoDto统一处理
     * @param videoDto
     * @param videoMysqlTable
     * @param albumCharge
     * @param isVarietyShow
     */
    private void setVideoDtoOfPositive(VideoDto videoDto, VideoMysqlTable videoMysqlTable,
            AlbumMysqlTable iptvAlbumInfo, boolean albumCharge, boolean isVarietyShow, CommonParam commonParam) {
        if (videoMysqlTable != null) {
            videoDto.setName(videoMysqlTable.getName_cn());
            videoDto.setSubName(videoMysqlTable.getSub_title());

            if (videoMysqlTable.getDuration() != null) {
                if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                        .getTerminalApplication())) {
                    videoDto.setDuration(videoMysqlTable.getDuration().longValue() * 1000L);
                } else {
                    videoDto.setDuration(videoMysqlTable.getDuration().longValue());
                }
            }

            videoDto.setVideoId(videoMysqlTable.getId().toString());
            videoDto.setCategoryId(videoMysqlTable.getCategory());
            videoDto.setImg(videoMysqlTable.getPic(400, 300));
            videoDto.setAlbumId(videoMysqlTable.getPid().toString());
            videoDto.setEpisode(videoMysqlTable.getEpisode());
            videoDto.setOrderInAlbum(videoMysqlTable.getPorder());
            videoDto.setPositive(this.isPositive(videoMysqlTable.getVideo_type(), videoMysqlTable.getCategory()));
            videoDto.setGuest(videoMysqlTable.getGuest());
            if (albumCharge
                    && VideoCommonUtil.isCharge(videoMysqlTable.getPay_platform(), videoMysqlTable.getPlay_platform(),
                            commonParam.getP_devType())) {
                videoDto.setIfCharge("1");
            } else {
                videoDto.setIfCharge("0");
            }
            // for tvod icon type
            Integer chargeType = JumpUtil.getChargeType(videoMysqlTable.getPay_platform(), commonParam);
            String chargeTypeStr = MmsDataUtil.getChargeTypeFromPlatform(videoMysqlTable.getPay_platform(),
                    commonParam.getP_devType());
            if (null == chargeType) {
                chargeType = JumpUtil.getChargeType(videoMysqlTable.getPay_platform());
            }
            videoDto.setChargeType(chargeType);
            videoDto.setIconType(JumpUtil.getIconType(videoDto.getSrc(), videoDto.getIfCharge(), chargeTypeStr,
                    videoDto.getStreams(), videoDto.getCategoryId(), videoMysqlTable.getSource_id(), commonParam));

            if (StringUtils.isNotEmpty(videoMysqlTable.getSinger())) {
                videoDto.setSinger(videoMysqlTable.getSinger().replace(",", "  "));
            }
            if (isVarietyShow
                    && !TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                            .getTerminalApplication())) { // 如果显示成综艺样式，剧集用release_data
                String episode = videoMysqlTable.getRelease_date();
                if (episode != null && episode.indexOf('-') > -1) {
                    episode = episode.replaceAll("-", "");
                }
                videoDto.setEpisode(episode);
            }
            if (iptvAlbumInfo != null && StringUtil.isNotBlank(iptvAlbumInfo.getName_cn())) {
                videoDto.setAlbumName(iptvAlbumInfo.getName_cn());
            }
        }
    }

    /**
     * 非正片下拉列表setVideoDto统一处理
     * @param videoDto
     * @param videoMysqlTable
     */
    private void setVideoDtoOfTrailer(VideoDto videoDto, VideoMysqlTable videoMysqlTable, CommonParam commonParam) {
        if (videoMysqlTable != null) {
            videoDto.setName(videoMysqlTable.getName_cn());
            videoDto.setSubName(videoMysqlTable.getSub_title());

            if (videoMysqlTable.getDuration() != null) {
                if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                        .getTerminalApplication())) {
                    videoDto.setDuration(videoMysqlTable.getDuration().longValue() * 1000L);
                } else {
                    videoDto.setDuration(videoMysqlTable.getDuration().longValue());
                }
            }

            videoDto.setVideoId(videoMysqlTable.getId().toString());
            videoDto.setCategoryId(videoMysqlTable.getCategory());
            videoDto.setImg(videoMysqlTable.getPic(400, 300));
            videoDto.setAlbumId(videoMysqlTable.getPid().toString());
            videoDto.setEpisode(videoMysqlTable.getEpisode());
            videoDto.setPositive(this.isPositive(videoMysqlTable.getVideo_type(), videoMysqlTable.getCategory()));
            videoDto.setGuest(videoMysqlTable.getGuest());
            if (StringUtils.isNotEmpty(videoMysqlTable.getSinger())) {
                videoDto.setSinger(videoMysqlTable.getSinger().replace(",", "  "));
            }
            videoDto.setOrderInAlbum(videoMysqlTable.getPorder());
        }
    }

    /**
     * 儿童video缓存下拉列表
     * @param v
     * @param vmt
     */
    private void setVideoDtoOfLeChildCache(VideoDto v, IptvVideoCacheInfoDto vmt, CommonParam commonParam) {
        if (vmt != null) {
            v.setName(vmt.getName_cn());
            v.setSubName(vmt.getSub_title());
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())
                    && vmt.getDuration() != null) {
                v.setDuration(vmt.getDuration().longValue() * 1000L);
            } else {
                v.setDuration(vmt.getDuration().longValue());
            }
            v.setVideoId(vmt.getId().toString());
            v.setCategoryId(vmt.getCategory());
            v.setImg(vmt.getPic());// 缓存中已经取出400*300
            v.setAlbumId(vmt.getPid().toString());
            v.setEpisode(vmt.getEpisode());
            v.setOrderInAlbum(vmt.getPorder());
            v.setPositive(true);
            v.setGuest(vmt.getGuest());
            v.setPreType(0);
            if (StringUtils.isNotEmpty(vmt.getSinger())) {
                v.setSinger(vmt.getSinger().replace(",", "  "));
            }
            if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                v.setChargeInfos(JumpUtil.genChargeInfos(vmt.getPayPlatform(), commonParam));
            }
        }
    }

    /**
     * 儿童播放记录
     * @param playList
     * @param albumId
     * @param initPos
     * @param pageSize
     * @param startPos
     */
    private void getPlayListOfLeChild(Collection<BaseData> playList, Long albumId, AlbumMysqlTable iptvAlbumInfo,
            Integer initPos, Integer pageSize, Integer startPos, CommonParam commonParam) {
        // 以下分页list取值不同于sql取值,满足同电视剧同样的逻辑
        int s = 0;
        if (initPos != -1) {
            s = (initPos - pageSize / 2) >= 0 ? (initPos - pageSize / 2) - 1 : 0;
        } else {
            s = startPos - 1;
        }
        if (s < 0) {
            s = 0;
        }
        int end = s + pageSize;

        boolean isVarietyShow = VideoUtil.isVarietyShow(iptvAlbumInfo, commonParam); // 是否设置成综艺样式

        List<IptvVideoCacheInfoDto> videoCacheList = this.facadeCacheDao.getVideoCacheDao().getVideoChildPlayList(
                albumId);

        if (videoCacheList != null && videoCacheList.size() > 0) {
            int listSize = videoCacheList.size();
            if (isVarietyShow) {
                s = 0;
                end = s + pageSize;
            }
            for (int i = s; i < end && i < listSize; i++) {
                IptvVideoCacheInfoDto vmt = videoCacheList.get(i);
                VideoDto v = new VideoDto();
                this.setVideoDtoOfLeChildCache(v, vmt, commonParam);
                if (iptvAlbumInfo != null && StringUtil.isNotBlank(iptvAlbumInfo.getName_cn())) {
                    v.setAlbumName(iptvAlbumInfo.getName_cn());
                }
                playList.add(v);
            }

            if (isVarietyShow && null != iptvAlbumInfo && StringUtil.isNotBlank(iptvAlbumInfo.getSource_id())
                    && iptvAlbumInfo.getSource_id().contains("200037")) {
                // 当来源为【童话侠】内容时，采取倒序显示!
                if (playList instanceof List) {
                    Collections.reverse((List) playList); // 倒序排列
                }
            }
        }
    }

    private void getOtherPlayListOfLeChild(Collection<BaseData> playList, Long albumId, AlbumMysqlTable iptvAlbumInfo,
            Integer initPos, Integer pageSize, Integer startPos, CommonParam commonParam) {

        int s = 0;
        if (initPos != -1) {
            s = (initPos - pageSize / 2) >= 0 ? (initPos - pageSize / 2) : 0;
        } else {
            s = startPos;
        }
        int end = s + pageSize - 1;
        List<VideoMysqlTable> videoList = null;
        boolean isVarietyShow = VideoUtil.isVarietyShow(iptvAlbumInfo, commonParam);
        if (isVarietyShow || iptvAlbumInfo.getCategory() == VideoConstants.Category.VARIETY) { // 如果是按照综艺样式展示，则倒序
            // videoList =
            // this.facadeMysqlDao.getVideoMysqlDao().getVideoListByPorderAndCreateTime(albumId,
            // true,
            // CommonConstants.ASC, commonParam.getBroadcastId());
            videoList = albumVideoAccess.getVideoRange(albumId,
                    VideoTpConstant.QUERY_TYPE_POSITIVE, 1, 0, Integer.MAX_VALUE, commonParam, 4);

        } else {
            // videoList =
            // this.facadeMysqlDao.getVideoMysqlDao().getVideoListByPorder(albumId,
            // s, end,
            // commonParam.getBroadcastId());
            videoList = albumVideoAccess.getVideoRange(albumId, VideoTpConstant.QUERY_TYPE_ALL,
                    -1, s, end, commonParam, 1);
        }
        for (VideoMysqlTable vmt : videoList) {
            if (this.isPositive(vmt.getVideo_type(), vmt.getCategory())
                    || (StringUtils.isNotEmpty(vmt.getEpisode()) && vmt.getEpisode().matches("\\d*")
                            && iptvAlbumInfo != null && iptvAlbumInfo.getFollownum() != null
                            && (Long.valueOf(vmt.getEpisode()) <= (iptvAlbumInfo.getFollownum() + 3))
                            && (Long.valueOf(vmt.getEpisode()) > Long.valueOf(iptvAlbumInfo.getFollownum()))
                            && (vmt.getVideo_type() != null) && (vmt.getVideo_type() == VideoConstants.VideoType.YU_GAO_PIAN))) {
                VideoDto v = new VideoDto();
                // 同样的VideoDto set功能重重复，统一提出了
                this.setVideoDtoOfPositive(v, vmt, iptvAlbumInfo, VideoCommonUtil.isCharge(
                        iptvAlbumInfo.getPay_platform(), iptvAlbumInfo.getPlay_platform(), commonParam.getP_devType()),
                        isVarietyShow, commonParam);
                v.setPreType(0);
                playList.add(v);
            }
        }

    }

    private void getFilmPlayListOfLeChild(Collection<BaseData> playList, Long albumId, AlbumMysqlTable iptvAlbumInfo,
            Integer initPos, Integer pageSize, Integer startPos, String history, CommonParam commonParam) {

        playList = recommendationService.getSingleBlock(iptvAlbumInfo.getCategory(), albumId.intValue(), null, null, 5, "rec_0010", null,
                        history, null, commonParam).getData();
        List<BaseData> delList = new LinkedList<BaseData>();
        for (BaseData bd : playList) {
            if (bd instanceof AlbumDto) {
                AlbumMysqlTable a = albumVideoAccess.getAlbum(
                        Long.valueOf(((AlbumDto) bd).getAlbumId()), commonParam);
                if (a != null) {
                    ((AlbumDto) bd).setScore(a.getScore());
                } else {
                    delList.add(bd);
                }
            } else if (bd instanceof VideoDto) { // 电影频道客户端直接收专辑,过滤掉视频
                delList.add(bd);
            }
        }
        if (delList.size() > 0) {
            playList.removeAll(delList);
        }

    }

    public Collection<BaseData> getRelation(Integer categoryId, Integer albumId, CommonParam commonParam) {
        int num = VideoConstants.SCREEN_SIZE * 3;
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) {
            num = VideoConstants.LE_SCREEN_SIZE * 2;
        }
        PageResponse<BaseData> rec = recommendationService.getSingleBlock(categoryId,
                albumId.intValue(), 0, null, num, "rec_0010", null, null, null, commonParam);
        if (rec != null) {
            return rec.getData();
        }

        // dataListRelation.addAll(dataList);

        return null;
    }

    /**
     * REC接口获取
     * @param categoryId
     * @param album
     * @param commonParam
     * @return
     */
    public Collection<BaseData> getRelation(Integer categoryId, AlbumDto album, CommonParam commonParam) {
        if (album == null) {
            return null;
        }

        int num = VideoConstants.SCREEN_SIZE * 3;
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) {
            num = VideoConstants.LE_SCREEN_SIZE * 2;
        }

        Integer albumId = StringUtil.toInteger(album.getAlbumId());
        Integer videoId = null;
        if (album.getPositiveSeries() != null && album.getPositiveSeries().size() > 0) {
            Iterator videoDtoSet = album.getPositiveSeries().iterator();
            while (videoDtoSet.hasNext()) {
                VideoDto video = (VideoDto) videoDtoSet.next();
                if (video != null && StringUtil.isNotBlank(video.getVideoId())) {
                    videoId = StringUtil.toInteger(video.getVideoId());
                    if (videoId != null) {
                        break;
                    }
                }
            }
        }
        if (albumId == null) {
            return null;
        }

        PageResponse<BaseData> rec = recommendationService.getSingleBlock(categoryId, albumId,
                videoId, null, num, RecommendationTpConstant.RECOMMENDATION_AREA_ALBUM_DP_REC, null, null, null,
                commonParam);
        if (rec != null) {
            return rec.getData();
        }

        return null;
    }

    /**
     * 老版本从REC接口获取，新版详情页为VRS+REC
     * @param albumMysql
     * @param album
     * @param commonParam
     * @return
     */
    public Collection<BaseData> getRelation(AlbumMysqlTable albumMysql, AlbumDto album, CommonParam commonParam) {
        Collection<BaseData> ret = null;
        Collection<BaseData> relations = getRelation(albumMysql.getCategory(), album, commonParam);
        Collection<BaseData> manualRelations = makeManualRelation(albumMysql, commonParam);

        // add the VRS
        if (null != manualRelations && manualRelations.size() > 0) {
            album.setRelationAddCount(manualRelations.size());
            ret = new LinkedList<BaseData>();
            ret.addAll(manualRelations);
        }

        if (null != relations && relations.size() > 0) {
            if (null == ret) {
                ret = new LinkedList<BaseData>();
            }

            if (null != manualRelations && manualRelations.size() > 0) {
                // build the index for filter
                Map<String, AlbumDto> manualRelationMap = new HashMap<String, AlbumDto>();
                AlbumDto albumDto = null;
                for (BaseData manualRelation : manualRelations) {
                    if (manualRelation instanceof AlbumDto) {
                        albumDto = (AlbumDto) manualRelation;
                        manualRelationMap.put(albumDto.getAlbumId(), albumDto);
                    }
                }

                // filter the REC with VRS
                Iterator<BaseData> relationsIterator = relations.iterator();
                BaseData relation = null;
                ResourceInfo resourceInfo = null;
                while (relationsIterator.hasNext()) {
                    relation = relationsIterator.next();
                    if (relation instanceof ResourceInfo) {
                        resourceInfo = (ResourceInfo) relation;
                        if (resourceInfo.getDataType() == DataConstant.DATA_TYPE_ALBUM) {
                            albumDto = (AlbumDto) relation;
                            if (manualRelationMap.containsKey(albumDto.getAlbumId())) {
                                relationsIterator.remove();
                            }
                        }
                    }
                }

                // add the REC
                ret.addAll(relations);
            } else {
                if (null != relations && relations.size() > 0) {
                    ret.addAll(relations);
                }
            }
        }
        return ret;
    }

    /**
     * 体育直播大厅
     * @param size
     *            显示的节目单个数
     * @return
     */
    public List<SportsLiveProgramDto> getSportLiveForIndex(Integer size, CommonParam commonParam) {
        // 只返回体育直播大厅数据
        List<LiveProgram> sportsLivePrograms = this.getSportsLivePrograms(LiveTpConstants.LIVE_SPLITID_TV_LIVE_ROOM,
                commonParam);

        List<SportsLiveProgramDto> sortedSportLives = this.sortSportLiveForIndex(sportsLivePrograms);

        if (sortedSportLives.size() > size) {
            sortedSportLives = sortedSportLives.subList(0, size);
        }

        return sortedSportLives;
    }

    /**
     * 获取体育直播数据
     * @param splatid
     * @return
     */
    private List<LiveProgram> getSportsLivePrograms(String splatid, CommonParam commonParam) {
        if (commonParam.getBroadcastId() == null) {
            commonParam.setBroadcastId(0);
        }
        List<LiveProgram> liveList = this.facadeCacheDao.getLiveCacheDao().getSportsLivePrograms(
                commonParam.getBroadcastId());
        if (CollectionUtils.isEmpty(liveList)) {
            String module = LiveConstants.LIVE_LIST;
            Map<String, LiveProgram> programMap = liveService.getLiveProgramsByChannel(
                    new LiveRoomChannel(LiveTpConstants.LIVE_ROOM_CHANNEL_SPORT,
                            LiveTpConstants.LIVE_ROOM_CATEGORY_ID_SPORT), splatid, module, commonParam.getLangcode(),
                    commonParam.getBroadcastId());
            if (programMap != null) {// 直播数据不为空，缓存数据30天
                Set<LiveProgram> livePrograms = new TreeSet<LiveProgram>(programMap.values());
                liveList = new ArrayList<LiveProgram>();
                liveList.addAll(livePrograms);
                this.facadeCacheDao.getLiveCacheDao().setSportsLivePrograms(commonParam.getBroadcastId(), liveList);
                log.info("getLivePrograms getSportsLivePrograms : set cache[: " + liveList.size() + "]");
                return liveList;
            } else {// 数据为空，则删除缓存
                this.facadeCacheDao.getLiveCacheDao().deleteSportsLivePrograms(commonParam.getBroadcastId());
                log.info("getLivePrograms getSportsLivePrograms : delete cache[: " + commonParam.getBroadcastId() + "]");
            }
        }
        return liveList;
    }

    /**
     * 体育直播大厅节目单排序
     * @return
     */
    private List<SportsLiveProgramDto> sortSportLiveForIndex(List<LiveProgram> sportsLivePrograms) {
        List<SportsLiveProgramDto> sportsLiveProgramDtos = new LinkedList<SportsLiveProgramDto>();
        if (!CollectionUtils.isEmpty(sportsLivePrograms)) {
            // long ctime = System.currentTimeMillis();
            long near48Hourour = System.currentTimeMillis() - 1000 * 3600 * 48L;

            int liveCount = 0;// 直播计数
            int pastLiveCount = 0;// 回看计数
            SportsLiveProgramDto huikanLiveDto = null;// 记录最近的一条回看直播数据
            for (LiveProgram liveProgram : sportsLivePrograms) {
                SportsLiveProgramDto sportsLiveProgramDto = new SportsLiveProgramDto(liveProgram);
                try {
                    Integer state = sportsLiveProgramDto.getState();
                    if (state == null) {
                        continue;
                    }
                    Long stime = StringUtil.toLong(sportsLiveProgramDto.getStartTime());
                    if (state == LiveConstants.PREVIEW.intValue()) {// 预告
                        sportsLiveProgramDto.setStateName(PROGRAM_TYPE.PRELIVE.getName());
                        sportsLiveProgramDto.setState(LiveTpDao.PROGRAM_TYPE.PRELIVE.getIndex());
                        sportsLiveProgramDtos.add(pastLiveCount + liveCount, sportsLiveProgramDto);
                    } else if (state == LiveConstants.LIVE.intValue()) {// 直播
                        sportsLiveProgramDto.setStateName(PROGRAM_TYPE.LIVE.getName());
                        sportsLiveProgramDto.setState(PROGRAM_TYPE.LIVE.getIndex());
                        sportsLiveProgramDtos.add(liveCount, sportsLiveProgramDto);
                        liveCount++;
                    } else {// 回看--只提供48小时之内的回看
                        if ((stime != null) && (stime < near48Hourour)) {
                            continue;
                        }
                        sportsLiveProgramDto.setStateName(PROGRAM_TYPE.HUI_KAN.getName());
                        sportsLiveProgramDto.setState(PROGRAM_TYPE.HUI_KAN.getIndex());
                        // sportsLiveProgramDto.setPk(s.getHome() +
                        // sportsLiveProgramDto.getScore() + s.getGuest());
                        huikanLiveDto = sportsLiveProgramDto;// 按照之前的逻辑，只取一条回看数据，所以需要记录最近的一场回看直播
                    }
                    sportsLiveProgramDto.setLiveName(TimeUtil.getDateString(new Date(stime),
                            TimeUtil.SIMPLE_DATE_FORMAT));

                    // 计算前台显示的时间，分别为昨天，今天，明天，后天，或者具体时间
                    String mtime = TimeUtil.getDateString(TimeUtil.startOfDayTomorrow(), TimeUtil.DATE_MM_DD_FORMAT1);// 明天

                    Calendar ttomorrow = TimeUtil.startOfDayTomorrow();
                    String htime = TimeUtil.getDateString(ttomorrow, TimeUtil.DATE_MM_DD_FORMAT1);// 后天

                    Calendar yesterday = TimeUtil.startOfDayYesterday();
                    String ztime = TimeUtil.getDateString(yesterday, TimeUtil.DATE_MM_DD_FORMAT1);// 昨天
                    String qtime = TimeUtil.getDateString(TimeUtil.backOneDay(yesterday), TimeUtil.DATE_MM_DD_FORMAT1);// 前天
                    String ttime = TimeUtil.getDateString(TimeUtil.truncateDay(Calendar.getInstance()),
                            TimeUtil.DATE_MM_DD_FORMAT1);// 今天

                    String str = TimeUtil.getDateString(new Date(stime), TimeUtil.DATE_MM_DD_FORMAT1);
                    if (str.equals(ttime)) {
                        sportsLiveProgramDto.setLiveDate("今天");
                    } else if (str.equals(mtime)) {
                        sportsLiveProgramDto.setLiveDate("明天");
                    } else if (str.equals(htime)) {
                        sportsLiveProgramDto.setLiveDate("后天");
                    } else if (str.equals(ztime)) {
                        sportsLiveProgramDto.setLiveDate("昨天");
                    } else if (str.equals(qtime)) {
                        sportsLiveProgramDto.setLiveDate("前天");
                    } else {
                        sportsLiveProgramDto.setLiveDate(str);
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            if (huikanLiveDto != null) {// 将最近的一场回看加入列表中，加在进行中的直播后面第一个位置
                sportsLiveProgramDtos.add(liveCount, huikanLiveDto);
            }
        }

        return sportsLiveProgramDtos;
    }

    /**
     * 获得内容分级描述
     * @param dictId
     * @return
     */
    public String getContentRatingDesc(String dictId, String langCode) {
        String desc = null;
        if (StringUtils.isNotEmpty(dictId)) {
            String dictKey = dictId + "-" + langCode.toLowerCase();
            if (VideoUtil.DICT_MAP.containsKey(dictKey)) {
                DictTp dictTp = VideoUtil.DICT_MAP.get(dictKey);
                if (dictTp != null && dictTp.getData() != null) {
                    desc = dictTp.getData().getDescription();
                }
            }
            if (desc == null) {
                DictTp dictTp = this.facadeTpDao.getDictTpDao().getDictById(dictId, langCode);
                if (dictTp != null && dictTp.getData() != null) {
                    desc = dictTp.getData().getDescription();
                    VideoUtil.DICT_MAP.put(dictKey, dictTp);
                }
            }
        }

        return desc;
    }

    /**
     * 获得内容分级描述
     * @param dictId
     * @return
     */
    public String getContentRatingValue(String dictId, String langCode) {
        String value = null;
        if (StringUtils.isNotEmpty(dictId)) {
            String dictKey = dictId + "-" + langCode.toLowerCase();
            if (VideoUtil.DICT_MAP.containsKey(dictKey)) {
                DictTp dictTp = VideoUtil.DICT_MAP.get(dictKey);
                if (dictTp != null && dictTp.getData() != null) {
                    value = dictTp.getData().getValue();
                }
            }
            if (value == null) {
                DictTp dictTp = this.facadeTpDao.getDictTpDao().getDictById(dictId, langCode);
                if (dictTp != null && dictTp.getData() != null) {
                    value = dictTp.getData().getValue();
                    VideoUtil.DICT_MAP.put(dictKey, dictTp);
                }
            }
        }

        if (StringUtils.isNotEmpty(value)) {
            int leftBracket = value.indexOf("(") == -1 ? value.indexOf("（") : value.indexOf("(");
            if (leftBracket != -1) {
                value = value.substring(0, leftBracket);
            }
        }

        return value;
    }

    public AlbumDto getAlbumDetailAndSeriesPersonal(Long albumId, Long userId, Integer pricePackageType,
            Boolean isFromCntv, Boolean isFromCibn, String userIp, CommonParam commonParam) {

        AlbumDto album = new AlbumDto();

        UserPlayAuth userPlayAuth = this.facadeTpDao.getVideoTpDao().getUserPlayAuth(albumId, userId, pricePackageType,
                isFromCntv, isFromCibn, null, commonParam);
        Integer expiredTime = null;
        if (userPlayAuth != null) {
            expiredTime = userPlayAuth.getExpiredTime();

            if (userPlayAuth.getChargeType() != null && 0 == userPlayAuth.getChargeType().intValue()) {
                album.setPayType(1);// 支持单点付费
            }
            album.setExpiredTime(expiredTime);
            album.setExpired(true);
            if (userPlayAuth.getLeftTime() != null) {
                Integer leftSeconds = (int) (userPlayAuth.getLeftTime() / 1000);
                album.setSingleOrderLeftTime(TimeUtil.seconds2HHmmWithoutZero(leftSeconds));
                album.setExpired(0 == userPlayAuth.getLeftTime());
            }

        }
        return album;
    }

    /**
     * 获取播放页的互动信息
     * @param commonParam
     * @param stream
     * @param albumId
     * @param videoId
     * @return
     */
    public Response<VideoReactionDto> getVideoReaction(String type, String videoId, String albumId, String stream,
            CommonParam commonParam) {
        Response<VideoReactionDto> response = new Response<VideoReactionDto>();
        String errorCode = null;
        String logPrefix = "getVideoReaction_" + videoId + "_" + albumId + "_" + type;

        // 参数校验
        int validCode = CommonMsgCodeConstant.REQUEST_SUCCESS;
        if (StringUtil.isBlank(videoId)) {
            validCode = VideoMsgCodeConstants.GET_VIDEO_REACTION_REQUEST_VIDEOID_ERROR;
        }
        if (validCode != CommonMsgCodeConstant.REQUEST_SUCCESS) {
            errorCode = VideoConstants.VIDEO_ILLEGAL_PARAMETER;
            ResponseUtil.setErrorResponse(response, errorCode,
                    ErrorMsgCodeUtil.parseErrorMsgCode(errorCode, BusinessCodeConstant.PLAY, validCode),
                    commonParam.getLangcode());
            log.error(logPrefix + "[errorCode=" + errorCode + "]: illegal parameters.");
        } else {
            VideoReactionDto data = new VideoReactionDto();
            response.setData(data);
            /*
             * GetVideoReactionConfigResponse configResponse =
             * this.facadeCacheDao.getVideoCacheDao()
             * .getVideoReactionConfigData();
             * if (configResponse == null) {
             * configResponse =
             * this.facadeTpDao.getVideoTpDao().getVideoReactionConfig();
             * if (configResponse != null) {
             * this.facadeCacheDao.getVideoCacheDao().
             * setVideoReactionConfigData(
             * configResponse);
             * }
             * }
             * boolean hasData = false;// 是否配置了扫码互动信息标志位
             * if (configResponse != null) {
             * Map<String, String> vidMap = configResponse.getVid();
             * Map<String, String> pidMap = configResponse.getPid();
             * if (!CollectionUtils.isEmpty(vidMap) &&
             * "1".equals(vidMap.get(videoId))) {// 先判断是否为视频配置了互动信息
             * hasData = true;
             * } else if (!CollectionUtils.isEmpty(pidMap) &&
             * "1".equals(pidMap.get(albumId))) {// 再判断是否为专辑配置了互动信息
             * hasData = true;
             * }
             * }
             * if (!hasData) {// 没有配置互动信息，则不请求
             * log.error(logPrefix +
             * ", get video reaction config info failure or no config info.");
             * QrCodeDto qrCodeDto = this.getQrCode(videoId, stream, logPrefix,
             * commonParam);
             * if (qrCodeDto != null) {// 获取频道二维码
             * data.setQrCodeDto(qrCodeDto);
             * }
             * } else {// 否则，则取请求扫码互动数据
             */
            List<VideoReaction> reactionList = new ArrayList<VideoReaction>();
            GetVideoReactionTpResponse tpResponse = this.facadeTpDao.getVideoTpDao().getVideoReactionInfo(type,
                    albumId, videoId, logPrefix);
            if (tpResponse == null || !"200".equals(tpResponse.getCode())
                    || CollectionUtils.isEmpty(tpResponse.getData())) {// 接口响应失败
                errorCode = VideoConstants.VIDEO_GET_VIDEO_REACTION_ERROR;
                log.warn(logPrefix + "[errorCode=" + errorCode + "]: get video reaction info failure.");
                QrCodeDto qrCodeDto = this.getQrCode(videoId, stream, logPrefix, commonParam);
                if (qrCodeDto != null) {// 获取频道二维码
                    data.setQrCodeDto(qrCodeDto);
                }
            } else {
                List<VideoReactionInfo> dataList = tpResponse.getData();
                Map<String, VideoReaction> map = new HashMap<String, VideoReactionDto.VideoReaction>();
                for (VideoReactionInfo videoReactionInfo : dataList) {
                    VideoReaction videoReaction = this.getVideoReaction(videoReactionInfo, type, logPrefix);
                    if (videoReaction != null) {// 需要根据触发时间进行升序排序，所以先放到map集合里，触发时间为键值
                        map.put(String.valueOf(videoReaction.getSecond()), videoReaction);
                    }
                }
                if (!CollectionUtils.isEmpty(map)) {
                    Set<String> set = map.keySet();
                    String[] keys = set.toArray(new String[0]);
                    Arrays.sort(keys, new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {// 自定义排序方式
                            int c1 = Integer.valueOf(o1);
                            int c2 = Integer.valueOf(o2);
                            return c1 - c2;
                        }
                    });
                    for (String key : keys) {
                        reactionList.add(map.get(key));
                    }
                }
                if (reactionList.size() > 0) {// 如果有扫码互动，就不展示频道二维码
                    data.setReactionList(reactionList);
                } else {// 需要请求频道二维码数据
                    QrCodeDto qrCodeDto = this.getQrCode(videoId, stream, logPrefix, commonParam);
                    if (qrCodeDto != null) {// 获取频道二维码
                        data.setQrCodeDto(qrCodeDto);
                    }
                }
            }
        }

        return response;
    }

    /**
     * 解析视频码流并添加至指定集合
     * @param videoPlayStream
     * @param playStreamSet
     */
    private void addVideoStreamToSet(String videoPlayStream, Collection<String> playStreamSet) {
        if (StringUtils.isNotBlank(videoPlayStream) && playStreamSet != null) {
            String[] streams = videoPlayStream.split(",");
            playStreamSet.addAll(Arrays.asList(streams));
        }
    }

    /**
     * 解析播放互动信息
     * @param videoReactionInfo
     * @return
     */
    private VideoReaction getVideoReaction(VideoReactionInfo videoReactionInfo, String type, String logPrefix) {
        if (videoReactionInfo == null) {
            return null;
        }
        VideoReaction reaction = new VideoReaction();
        reaction.setTitle(videoReactionInfo.getTitle());
        reaction.setSubTitle(videoReactionInfo.getSubtitle());
        reaction.setSecond(videoReactionInfo.getSecond());
        reaction.setLifeStart(videoReactionInfo.getLifestart());
        reaction.setLifeEnd(videoReactionInfo.getLifeend());
        reaction.setRule(videoReactionInfo.getRule());
        reaction.setVoteId(videoReactionInfo.getVote_id());
        reaction.setVid(videoReactionInfo.getVid());
        reaction.setPid(videoReactionInfo.getPid());
        reaction.setVoteType(videoReactionInfo.getVotetype());
        List<ReactionDto> dataList = new ArrayList<ReactionDto>();
        if (VideoConstants.VIDEO_REACTION_TYPE_1.equals(type)) {
            List<ReactionData> effect = videoReactionInfo.getEffect();
            if (!CollectionUtils.isEmpty(effect)) {// 动效数据
                for (ReactionData reactionData : effect) {
                    ReactionDto reactionDto = this.getReactionData(reactionData, VideoConstants.VIDEO_REACTION_TYPE_1,
                            logPrefix);
                    if (reactionDto != null) {
                        dataList.add(reactionDto);
                    }
                }
            }
        } else if (VideoConstants.VIDEO_REACTION_TYPE_2.equals(type)) {
            List<ReactionData> options = videoReactionInfo.getOptions();
            if (!CollectionUtils.isEmpty(options)) {// 投票选项数据
                for (ReactionData reactionData : options) {
                    ReactionDto reactionDto = this.getReactionData(reactionData, VideoConstants.VIDEO_REACTION_TYPE_2,
                            logPrefix);
                    if (reactionDto != null) {
                        dataList.add(reactionDto);
                    }
                }
            }
        } else if (VideoConstants.VIDEO_REACTION_TYPE_3.equals(type)) {
            List<ReactionData> effect = videoReactionInfo.getEffect();
            if (!CollectionUtils.isEmpty(effect)) {// 动效数据
                for (ReactionData reactionData : effect) {
                    ReactionDto reactionDto = this.getReactionData(reactionData, VideoConstants.VIDEO_REACTION_TYPE_1,
                            logPrefix);
                    if (reactionDto != null) {
                        dataList.add(reactionDto);
                    }
                }
            }
            List<ReactionData> share = videoReactionInfo.getShare();
            if (!CollectionUtils.isEmpty(share)) {// 分享数据
                for (ReactionData reactionData : share) {
                    ReactionDto reactionDto = this.getReactionData(reactionData, VideoConstants.VIDEO_REACTION_TYPE_3,
                            logPrefix);
                    if (reactionDto != null) {
                        dataList.add(reactionDto);
                    }
                }
            }
        }
        if (dataList.size() > 0) {
            reaction.setReactonData(dataList);
        }
        return reaction;
    }

    /**
     * 解析播放互动信息中具体的数据内容
     * @param reactionData
     * @param type
     * @return
     */
    private ReactionDto getReactionData(VideoReactionInfo.ReactionData reactionData, String type, String logPrefix) {
        if (reactionData == null || type == null) {// 判空处理
            return null;
        }
        ReactionDto reactionDto = new ReactionDto();
        reactionDto.setTitle(reactionData.getTitle());
        reactionDto.setSubTitle(reactionData.getSubtitle());
        reactionDto.setOptionId(reactionData.getOption_id());
        reactionDto.setRemarks(reactionData.getRemarks());
        if (VideoConstants.VIDEO_REACTION_TYPE_3.equals(type)) {// 分享数据返回的是二位码地址
            reactionDto.setImgUrl(reactionData.getUrl());
        } else {// 投票或者动态效果则返回图片地址
            reactionDto.setImgUrl(reactionData.getImg());
        }
        reactionDto.setType(Integer.valueOf(type));

        return reactionDto;
    }

    /**
     * 获取频道二维码信息
     */
    private QrCodeDto getQrCode(String videoId, String stream, String logPrefix, CommonParam commonParam) {
        VideoMysqlTable video = albumVideoAccess.getVideo(Long.valueOf(videoId), commonParam);
        if (video != null) {// 先查询是否存在此视频
            Integer categoryId = video.getCategory();
            if (stream != null) {
                QrCodeDto dto = qrCodeService.getQrCodeByStream(stream, categoryId,
                        video.getId(), video.getPid(), commonParam);
                if (dto != null) {
                    return dto;
                }
            }
        }
        return null;
    }

    /**
     * 排序、过滤码流列表 只返回视频拥有的码流
     * @param streams
     *            目标码流集合
     * @param isYuanxian
     *            是否院线
     * @param categoryId
     *            分类ID
     * @param commonParam
     *            接口通用参数
     * @param isThirdParty
     *            是否第三方
     * @return List
     */
    private List<Stream> sortAndFilterStreamCode(Set<String> streams, String isYuanxian, Integer categoryId,
            CommonParam commonParam, boolean isThirdParty) {
        String allStreams = "";
        Set<String> chargeStreamSet;
        if (isThirdParty) {
            chargeStreamSet = LetvStreamCommonConstants.THIRD_PARTY_CHARGE_STREAM_SET;
        } else {
            chargeStreamSet = LetvStreamCommonConstants.CHARGE_STREAM_SET;
        }
        if (ProductLineConstants.LETV_COMMON.equalsIgnoreCase(commonParam.getTerminalApplication())) {
            allStreams = LetvStreamCommonConstants.LETV_COMMON_STREAMS;
        } else {
            if (TerminalUtil.isLetvUs(commonParam)) {// 美国码流列表
                allStreams = LetvStreamCommonConstants.ALL_STREAMS_US;
                chargeStreamSet = LetvStreamCommonConstants.CHARGE_STREAM_SET_US;
            } else {
                allStreams = LetvStreamCommonConstants.ALL_STREAMS;
            }
        }

        List<Stream> allVideoStreamList = null;
        if ("1".equals(isYuanxian)) {
            allVideoStreamList = StreamConstants.getStreamCodeForYuanxian(allStreams, commonParam.getLangcode());
        } else {
            allVideoStreamList = StreamConstants.getStreamCode(allStreams, commonParam.getLangcode());
        }
        List<Stream> realList = new ArrayList<Stream>();
        if (!TerminalUtil.isLetvUs(commonParam)) {// 美国版还是有1000码流
            // 2016-03-07TV版码流修改，1000码率不转，降为800，重新罗列清晰度对应表，标清由1000修改为800，这里直接去掉1000码流
            if (streams != null) {
                streams.remove(LetvStreamCommonConstants.CODE_NAME_1000);
            }
        } else {
            if (streams != null && streams.contains(LetvStreamCommonConstants.CODE_NAME_1000)) {
                // us version replace 1000 to 800
                streams.remove(LetvStreamCommonConstants.CODE_NAME_1000);
                streams.add(LetvStreamCommonConstants.CODE_NAME_800);
            }
        }

        if (!CollectionUtils.isEmpty(streams)) {
            for (int i = 0; i < allVideoStreamList.size(); i++) {
                Stream allV = allVideoStreamList.get(i);
                allV.setKbps(LetvStreamCommonConstants.getMbps(allV.getCode()));
                for (String realStream : streams) {
                    if (StringUtils.equalsIgnoreCase(realStream, allV.getCode())
                            || (StringUtils.equalsIgnoreCase(allV.getCode(),
                                    LetvStreamCommonConstants.CODE_NAME_1080p6m) && StringUtils.equalsIgnoreCase(
                                    realStream, LetvStreamCommonConstants.CODE_NAME_1080p3m))) {
                        if (chargeStreamSet.contains(realStream) || "1".equals(isYuanxian)) {
                            allV.setIfCharge(1);// 收费
                        } else {
                            allV.setIfCharge(0);
                        }
                        realList.add(allV);
                        break;
                    }
                }
            }
        }

        return realList;
    }

    /**
     * 判断一个视频对应专辑是否是单点
     * @param videoMysqlTable
     * @param albumMysqlTable
     * @return
     */
    private boolean isSingleOrderAlbum(VideoMysqlTable videoMysqlTable, AlbumMysqlTable albumMysqlTable,
            CommonParam commonParam) {
        boolean isSingleOrder = false;
        if (videoMysqlTable != null && albumMysqlTable != null && videoMysqlTable.getVideo_attr() != null
                && 1 == videoMysqlTable.getVideo_attr() && albumMysqlTable.isPay(commonParam.getP_devType())) {

            GetAlbumChargeInfoTpResponse response = this.facadeTpDao.getVideoTpDao().getAlbumChargeInfo(
                    albumMysqlTable.getId());

            Integer isCharge = null;
            if (response != null) {
                Map<String, String> chargeMap = response.getChargeflatform();
                String price = "0";
                if (!CollectionUtils.isEmpty(chargeMap)) {// TV--141007
                    String platform = "141007";
                    if (null != commonParam) {
                        platform = MmsDataUtil.getPayPlatform(commonParam.getP_devType());
                    }
                    if (StringUtils.isNotEmpty(chargeMap.get(platform))) {
                        price = chargeMap.get(platform);
                    }
                }

                isCharge = response.getIscharge() != null ? response.getIscharge() : 0;

                // isCharge指定了收费后，price节点依然有可能为0
                if (isCharge == 1) {
                    if (!("0".equalsIgnoreCase(price) || "0.00".equalsIgnoreCase(price) || "0.0"
                            .equalsIgnoreCase(price))) {
                        isSingleOrder = true;
                    }
                }
            }
        }

        return isSingleOrder;
    }

    /**
     * 判断专辑所属频道是否收费
     * @param category
     * @return
     */
    private boolean payChannelIsCharge(Integer category, String logPrefix) {
        if (category != null) {
            GetPayChannelResponse tpResponse = this.facadeCacheDao.getVideoCacheDao().getPayChannel();
            if (tpResponse == null) {
                tpResponse = this.facadeTpDao.getVideoTpDao().getPayChannel(logPrefix);
                if (tpResponse != null) {
                    this.facadeCacheDao.getVideoCacheDao().setPayChannel(tpResponse);
                }
            }
            if (tpResponse == null || !"0".equals(tpResponse.getCode())
                    || CollectionUtils.isEmpty(tpResponse.getData())) {
                // 响应失败或者数据为空
                log.info(logPrefix + ",get pay channnel data failure.");
            } else {
                List<GetPayChannelResponse.PayChannel> payChannelList = tpResponse.getData();
                if (!CollectionUtils.isEmpty(payChannelList)) {
                    String categoryStr = String.valueOf(category);
                    for (GetPayChannelResponse.PayChannel payChannel : payChannelList) {
                        if (categoryStr.equals(payChannel.getChannelId())) {
                            Integer ifCh = payChannel.getIsCharge();
                            if (ifCh != null && ifCh == 1) {
                                return true; // 全频道付费，专辑详情页展示会员专享
                            }
                            break;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 从分好页的剧集数据中，筛选数据；注意，领先版下拉列表（剧集列表），仅电视剧频道不返图片
     * @param albumSeries
     *            分好页的剧集数据
     * @param startIndex
     *            开始位置，对应LetvLeadingPlayInfo.orderInAlbum
     * @param endIndex
     *            结束位置，对应LetvLeadingPlayInfo.orderInAlbum
     * @return
     */
    private List<VideoDto> parseVideoSeriesFromLetvLeadingAlbumSeries(LetvLeadingAlbumSeries albumSeries,
            int startIndex, int endIndex, Integer pageNum, CommonParam commonParam) {
        List<VideoDto> videos = new ArrayList<VideoDto>();
        if (albumSeries != null && albumSeries.getPositiveSeries() != null
                && albumSeries.getPositiveSeries().size() > pageNum
                && albumSeries.getPositiveSeries().get(pageNum) != null
                && !CollectionUtils.isEmpty(albumSeries.getPositiveSeries().get(pageNum).getPositiveSeries())) {
            List<LetvLeadingPlayInfo> playInfos = albumSeries.getPositiveSeries().get(pageNum).getPositiveSeries();
            for (LetvLeadingPlayInfo playInfo : playInfos) {
                if (playInfo.getOrderInAlbum() != null && playInfo.getOrderInAlbum() >= startIndex
                        && playInfo.getOrderInAlbum() <= endIndex) {
                    VideoDto video = new VideoDto();
                    video.setVideoId(playInfo.getVideoId());

                    if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                            .getTerminalApplication())) {
                        video.setName(playInfo.getName());
                        video.setImg(playInfo.getImg());
                    }

                    video.setCategoryId(playInfo.getCategoryId());
                    video.setOrderInAlbum(playInfo.getOrderInAlbum());
                    video.setPositive((playInfo.getPositive() != null && 1 == playInfo.getPositive()));
                    video.setVideoTypeId(playInfo.getVideoTypeId());
                    video.setEpisode(playInfo.getEpisode());
                    video.setIfCharge(playInfo.getIfCharge());
                    video.setPageNum(playInfo.getPage());

                    videos.add(video);
                }

            }
        }
        return videos;
    }

    /**
     * 三屏不同终端显示的数据结构一致，因此这使用AlbumSerieResponse
     */
    public AlbumSeriesDto getVideoInfoByid(Long vid, Long videoInfoId, CommonParam commonParam) {
        AlbumSeriesDto dto = new AlbumSeriesDto();
        VideoMysqlTable videoInfo = albumVideoAccess.getVideo(vid, commonParam);
        if (videoInfo != null) {
            dto = new AlbumSeriesDto(videoInfo);
            Long aid = videoInfo.getPid();
            if (aid != null) {
                AlbumMysqlTable album = albumVideoAccess.getAlbum(aid, commonParam);
                if (album != null) {
                    dto.setAlbumName(album.getName_cn());
                }
            }
        }
        return dto;
    }

    public Response<Map<String, List<AlbumDto>>> getPlaylist4Voice(Integer size, String cid, String cmsBlockId,
            CommonParam commonParam) {
        Map<String, List<AlbumDto>> albumDtoMap = new HashMap<String, List<AlbumDto>>();
        if (StringUtil.isNotBlank(cmsBlockId)) {
            channelService.setChildPlay4Voide(cmsBlockId, albumDtoMap, commonParam);
        } else {
            String statDate = this.facadeMysqlDao.getStatRankWeekDataDao().getLatestStatisticsDate();
            List<StatRankWeekData> statRankWeekDataList = null;
            List<AlbumDto> albumDtoList = new ArrayList<AlbumDto>();
            if (StringUtils.isNotEmpty(statDate)) {
                List<Integer> cids = new LinkedList<Integer>();
                String[] cidStrArr = cid.split(",");
                for (String cidStr : cidStrArr) {
                    if (StringUtil.isNotBlank(cidStr)) {
                        cids.add(StringUtil.toInteger(cidStr));
                    }
                }
                statRankWeekDataList = this.facadeMysqlDao.getStatRankWeekDataDao().listByStatDateAndMultiType(
                        statDate, cids, 0, size);
            }
            if (statRankWeekDataList != null) {
                for (StatRankWeekData statRankWeekData : statRankWeekDataList) {
                    if (statRankWeekDataList != null && statRankWeekData.getPid() != null) {
                        String key = null;
                        Long albumId = StringUtil.toLong(statRankWeekData.getPid());
                        // AlbumMysqlTable album =
                        // this.facadeCacheDao.getVideoCacheDao().getAlbum(albumId,
                        // null);
                        AlbumMysqlTable album = albumVideoAccess.getAlbum(albumId, commonParam);
                        if (album != null) {
                            key = album.getName_cn();
                            if (albumDtoMap.containsKey(key)) { // 每个关键词只去一个播放量最高的专辑
                                continue;
                            }
                            albumDtoList = new ArrayList<AlbumDto>();

                            AlbumDto albumDto = new AlbumDto();
                            albumDto.setAlbumId(statRankWeekData.getPid());
                            albumDto.setCategoryId(StringUtil.toInteger(statRankWeekData.getCid()));
                            albumDto.setName(album.getName_cn());
                            // List<VideoMysqlTable> videoList =
                            // this.facadeMysqlDao.getVideoMysqlDao()
                            // .getVideoListByPorderAndCreateTime(albumId, true,
                            // 0,
                            // null);
                            String queryType = VideoTpConstant.QUERY_TYPE_POSITIVE;
                            if (VideoCommonUtil.isPositive(2, album.getCategory(), album.getAlbum_type(), null)) {
                                if (album.getAlbum_type() != null && album.getAlbum_type() != 180001) {
                                    queryType = VideoTpConstant.QUERY_TYPE_ALL;
                                }
                            }
                            List<VideoMysqlTable> videoList = albumVideoAccess.getVideoRange(
                                    albumId, queryType, -1, 0, Integer.MAX_VALUE, commonParam, 4);

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
                            albumDtoList.add(albumDto);
                            albumDtoMap.put(key, albumDtoList);
                        }
                    }
                }
            }

        }
        Response<Map<String, List<AlbumDto>>> response = new Response<Map<String, List<AlbumDto>>>();
        response.setResultStatus(1);
        response.setData(albumDtoMap);
        return response;
    }

    /**
     * 获取专辑播放数、评论数信息；优先从缓存中获取，其次从TP获取，成功后更新缓存
     * @param albumId
     * @param commonParam
     * @return
     */
    public TotalCountStatTpResponse getAlbumTotalCountStat(Long albumId, CommonParam commonParam) {
        TotalCountStatTpResponse response = null;
        if (albumId != null && commonParam != null) {
            if (!commonParam.isFlush()) {
                response = this.facadeCacheDao.getVideoCacheDao().getAlbumTotalCountStat(albumId, commonParam);
            }
            if (response == null) {
                response = this.facadeTpDao.getStatTpDao().getTotalCountStat(albumId);
                if (response != null) {
                    if (!this.isInvalidResponse(response)) {
                        this.facadeCacheDao.getVideoCacheDao().setAlbumTotalCountStat(albumId,
                                CacheContentConstants.CACHE_EXPIRES_ONE_HOUR, response, commonParam);
                    }
                }
            } else {
                if (this.isInvalidResponse(response)) {
                    this.facadeCacheDao.getVideoCacheDao().deleteAlbumTotalCountStat(albumId, commonParam);
                }
            }
        }

        return response;
    }

    /**
     * 批量获取专辑播放数、评论数信息；优先从缓存中获取，其次从TP获取，成功后更新缓存
     * @param albumIds
     * @param commonParam
     * @return
     */
    public Map<Long, TotalCountStatTpResponse> getAlbumTotalCountStats(List<Long> albumIds, CommonParam commonParam) {
        Map<Long, TotalCountStatTpResponse> response = new HashMap<Long, TotalCountStatTpResponse>();
        Map<Long, TotalCountStatTpResponse> cacheMap = null;
        if (albumIds != null && !albumIds.isEmpty() && commonParam != null) {
            if (!commonParam.isFlush()) {
                cacheMap = this.facadeCacheDao.getVideoCacheDao().getAlbumTotalCountStats(albumIds, commonParam);
            }
            List<Long> albumIdsNotInCache = new ArrayList<Long>();
            if (null != cacheMap && !cacheMap.isEmpty()) {
                Iterator iter = cacheMap.entrySet().iterator();
                Map.Entry entry = null;
                while (iter.hasNext()) {
                    entry = (Map.Entry) iter.next();
                    if (null == entry.getValue()) {
                        albumIdsNotInCache.add((Long) entry.getKey());
                    } else {
                        if (this.isInvalidResponse((TotalCountStatTpResponse) entry.getValue())) {
                            albumIdsNotInCache.add((Long) entry.getKey());
                        } else {
                            response.put((Long) entry.getKey(), (TotalCountStatTpResponse) entry.getValue());
                        }
                    }
                }
            } else {
                albumIdsNotInCache.addAll(albumIds);
            }
            if (!albumIdsNotInCache.isEmpty()) {
                if (null != cacheMap && cacheMap.size() > 0) {
                    response.putAll(cacheMap);
                }

                String albumIdsStr = StringUtil.getListToStringEx(albumIdsNotInCache, ",");
                List<TotalCountStatTpResponse> tpResponse = this.facadeTpDao.getStatTpDao().mgetTotalCountStat(
                        albumIdsStr);
                if (tpResponse != null) {
                    // if
                    // (!this.facadeTpDao.getStatTpDao().isInvalidTotalCountStatTpResponse(response))
                    // // 允许部分数据异常也可以入缓存
                    {
                        this.facadeCacheDao.getVideoCacheDao().setAlbumTotalCountStats(tpResponse,
                                CacheContentConstants.CACHE_EXPIRES_ONE_HOUR, commonParam, new TpCallBack() {
                                    public boolean canDo(Object bundle) {
                                        boolean ret = false;
                                        if (null != bundle && bundle instanceof TotalCountStatTpResponse) {
                                            // ret =
                                            // !facadeTpDao.getStatTpDao().isInvalidTotalCountStatTpResponse(
                                            // (TotalCountStatTpResponse)
                                            // bundle);
                                            ret = true;
                                        }
                                        return ret;
                                    }
                                });
                    }

                    for (TotalCountStatTpResponse totalCountStatTpResponse : tpResponse) {
                        // if
                        // (!this.facadeTpDao.getStatTpDao().isInvalidTotalCountStatTpResponse(response))
                        {
                            try {
                                response.put(Long.parseLong(totalCountStatTpResponse.getId()), totalCountStatTpResponse);
                            } catch (NumberFormatException nfe) {
                            }
                        }
                    }
                }
            } else {
                response = cacheMap;
            }
        }

        return response;
    }

    /**
     * 解决片源详情页“播放数”、“评论数”更新不及时的问题 @dengliwei 20170113
     * @param response
     * @return
     */
    private boolean isInvalidResponse(Object response) {
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
        }
        return ret;
    }

    /**
     * 判断视频是否是既非正片又非预告片；2016-07-15美国乐视视频需求
     * @param videoType
     * @return
     */
    private boolean isVideoNotPositiveOrPre(Integer videoType) {
        return videoType != null && MmsTpConstant.MMS_VIDEO_TYPE_ZHENG_PIAN != videoType
                && MmsTpConstant.MMS_VIDEO_TYPE_YU_GAO_PIAN != videoType;
    }

    private void validateAlbumPlayAuth4Lecom(AlbumDto album, AlbumMysqlTable albumMysqlTable,
            VideoMysqlTable videoMysqlTable, String logPrefix, CommonParam commonParam) {
        Integer chargeType = ChargeTypeConstants.chargePolicy.FREE;
        String playStream = LetvStreamCommonConstants.CODE_NAME_720p;
        chargeType = playService.chargePolicy(albumMysqlTable, videoMysqlTable, playStream,
                commonParam.getTerminalApplication(), commonParam.getAppCode(), VideoConstants.PLAY_MODEL_COMMON,
                commonParam);
        UserPlayAuth4LecomUS userPlayAuth = new UserPlayAuth4LecomUS(CommonConstants.PLAY_SERVICE_UNOPEN);
        /**
         * 付费端无TV -->直接免费看 ，不需要走boss鉴权
         */
        if ((albumMysqlTable.getPay_platform() == null)
                || (albumMysqlTable.getPay_platform() != null && !(albumMysqlTable.getPay_platform().indexOf(
                        CommonConstants.TV_PAY_CODE) > -1))) {
            chargeType = ChargeTypeConstants.chargePolicy.FREE;
        } else {
            chargeType = this.setAlbumPlayInfo4LecomUS(album, userPlayAuth,
                    VideoConstants.BOSS_V2_ALBUM_PLAY_AUTH_MOCK_STOREPATH, chargeType, logPrefix, commonParam);
        }

        if ((userPlayAuth != null) && (userPlayAuth.getStatus() != null)
                && (CommonConstants.PLAY_SERVICE_OPEN == userPlayAuth.getStatus())) {
            chargeType = ChargeTypeConstants.chargePolicy.FREE;
        }

        if (chargeType == ChargeTypeConstants.chargePolicy.CHARGE_BY_VIP && userPlayAuth != null) {
            this.getTryPlayTime4LecomUS(album, userPlayAuth);
            album.setPackageInfo(userPlayAuth.getPackageInfo());
        }

        album.setPlayType(chargeType);
    }

    private Integer setAlbumPlayInfo4LecomUS(AlbumDto album, UserPlayAuth4LecomUS userPlayAuth, String storePath,
            Integer chargeType, String logPrefix, CommonParam commonParam) {
        BossTpResponse<ValidateServiceTp> playAuthResponse = this.facadeTpDao.getVipTpDao().validateAlbumPlayService(
                commonParam.getUserId(), album.getAlbumId(), storePath, commonParam, -1);

        return playService.parsePlayAuth(chargeType, logPrefix, userPlayAuth, playAuthResponse,
                commonParam);
    }

    private void getTryPlayTime4LecomUS(AlbumDto album, UserPlayAuth4LecomUS playAuth) {
        VodTryPlayInfo tryInfo = playAuth.getTryInfo();
        if (tryInfo != null && tryInfo.getTryStartTime() != null && tryInfo.getTryStartTime() > 0
                && tryInfo.getTryEndTime() != null && tryInfo.getTryEndTime() > 0) {
            album.setTryPlayTime(tryInfo.getTryEndTime() - tryInfo.getTryStartTime());
        } else {
            Integer category = album.getCategoryId();
            if (category == null || category == VideoConstants.Category.FILM) {
                // 分类ID为空或者电影，试看6分钟
                album.setTryPlayTime(360000L);
            } else {
                switch (category) {
                case VideoConstants.Category.TV:
                    // 仅会员付费类型，电视剧和动漫的单点，没有试看
                    album.setTryPlayTime(360000L);
                    break;
                case VideoConstants.Category.CARTOON:
                    // 仅会员付费类型，电视剧和动漫的单点，没有试看
                    album.setTryPlayTime(180000L);
                    break;
                case VideoConstants.Category.PARENTING:
                    album.setTryPlayTime(60000L);
                    break;
                default:
                    album.setTryPlayTime(360000L);
                    break;
                }
            }
        }
    }

    private Collection<BaseData> getLecomPlayList(Long videoId, Integer startPos, Integer initPos, Integer pageSize,
            String history, Integer model, Long albumId, CommonParam commonParam) {

        Collection<BaseData> playList = new ArrayList<BaseData>();

        VideoMysqlTable video = albumVideoAccess.getVideo(videoId, commonParam);
        if (video != null) {
            albumId = video.getPid();
            Integer categoryId = video.getCategory();
            Integer videoType = video.getVideo_type();
            int num = VideoConstants.LE_SCREEN_SIZE * 2;
            if (albumId == null || albumId == 0) {
                // 视频无专辑逻辑，走单板块推荐
                VideoDto current = new VideoDto();
                current.setVideoId(String.valueOf(video.getId()));
                current.setName(video.getName_cn());
                current.setImg(video.getPic(400, 300));
                current.setAlbumId(String.valueOf(video.getPid()));
                current.setCategoryId(video.getCategory());
                if (video.getCategory() == VideoConstants.Category.MUSIC) {// 音乐
                    current.setSubName(video.getStarring());// 副标题为歌星
                } else if (video.getCategory() == VideoConstants.Category.SPORT) {// 体育
                    // 无副标题
                } else {
                    current.setSubName(video.getSub_title());
                }

                playList.add(current);
                Collection<BaseData> recList = recommendationService
                        .getSingleBlock(categoryId, null, videoId.intValue(), null, num, "rec_0010", null, history,
                                null, commonParam).getData();
                if (!CollectionUtils.isEmpty(recList)) {
                    for (BaseData baseData : recList) {
                        if (baseData instanceof VideoDto) {
                            playList.add(baseData);
                        }
                    }
                }
            } else {
                // 视频有专辑逻辑
                AlbumMysqlTable iptvAlbumInfo = albumVideoAccess.getAlbum(albumId, commonParam);

                boolean isVarietyShow = false; // 是否设置成综艺样式
                boolean albumCharge = false;// 专辑是否收费
                if (iptvAlbumInfo != null) {
                    albumCharge = VideoCommonUtil.isCharge(iptvAlbumInfo.getPay_platform(),
                            iptvAlbumInfo.getPlay_platform(), commonParam.getP_devType());
                    isVarietyShow = VideoUtil.isVarietyShow(iptvAlbumInfo, commonParam);

                    // 先考虑频道，在考虑视频属性

                    if (this.isPositive(videoType, categoryId)
                            || ((categoryId == VideoConstants.Category.TV || categoryId == VideoConstants.Category.CARTOON) && ((StringUtils
                                    .isNotEmpty(video.getEpisode())
                                    && video.getEpisode().matches("\\d*")
                                    && iptvAlbumInfo != null
                                    && iptvAlbumInfo.getFollownum() != null
                                    && (Long.valueOf(video.getEpisode()) <= (iptvAlbumInfo.getFollownum() + 3))
                                    && (Long.valueOf(video.getEpisode()) > Long.valueOf(iptvAlbumInfo.getFollownum()))
                                    && (video.getVideo_type() != null) && (video.getVideo_type() == VideoConstants.VideoType.YU_GAO_PIAN))))) {
                        // 如果是正片视频，或电视剧、卡通下，当前视频集数大于正剧跟播集数3集以内的预告片
                        if (categoryId == VideoConstants.Category.FILM && iptvAlbumInfo != null) {
                            // 如果当前视频属于电影频道，则走单板块推荐；可以确定的是当前视频是正片
                            Collection<BaseData> recList = recommendationService
                                    .getSingleBlock(categoryId, albumId.intValue(), videoId.intValue(), null, num,
                                            "rec_0010", null, history, null, commonParam).getData();
                            if (recList != null) {
                                List<BaseData> delList = new LinkedList<>();
                                for (BaseData bd : recList) {
                                    if (bd instanceof AlbumDto) {
                                        AlbumMysqlTable a = albumVideoAccess.getAlbum(
                                                Long.valueOf(((AlbumDto) bd).getAlbumId()), commonParam);
                                        if (a != null) {
                                            ((AlbumDto) bd).setScore(a.getScore());
                                        } else {
                                            delList.add(bd);
                                        }
                                    } else if (bd instanceof VideoDto) { // 电影频道客户端直接收专辑,过滤掉视频
                                        delList.add(bd);
                                    }
                                }
                                if (delList.size() > 0) {
                                    recList.removeAll(delList);
                                }
                                playList = recList;
                            }
                        } else {
                            List<VideoMysqlTable> videoList = null;
                            if (categoryId != null
                                    && (categoryId == VideoConstants.Category.TV || categoryId == VideoConstants.Category.CARTOON)) {
                                // 如果当前视频属于电视剧、卡通频道，则要么是正片，要么是当前视频集数大于正剧跟播集数3集以内的预告片
                                int s = 0;
                                if (initPos != null && initPos != -1) {
                                    s = (initPos - pageSize / 2) >= 0 ? (initPos - pageSize / 2) : 0;
                                } else {
                                    s = startPos;
                                }

                                int end = s + pageSize - 1;

                                if (isVarietyShow) { // 如果是按照综艺样式展示，则倒序
                                    videoList = albumVideoAccess.getVideoRange(albumId,
                                            VideoTpConstant.QUERY_TYPE_POSITIVE, 1, 0, Integer.MAX_VALUE, commonParam,
                                            4);
                                } else {
                                    videoList = albumVideoAccess.getVideoRange(albumId,
                                            VideoTpConstant.QUERY_TYPE_ALL, -1, s, end, commonParam, 1);
                                }

                                if (videoList != null) {
                                    for (VideoMysqlTable vmt : videoList) {
                                        if (this.isPositive(vmt.getVideo_type(), vmt.getCategory())
                                                || (StringUtils.isNotEmpty(vmt.getEpisode())
                                                        && vmt.getEpisode().matches("\\d*")
                                                        && iptvAlbumInfo != null
                                                        && iptvAlbumInfo.getFollownum() != null
                                                        && (Long.valueOf(vmt.getEpisode()) <= (iptvAlbumInfo
                                                                .getFollownum() + 3))
                                                        && (Long.valueOf(vmt.getEpisode()) > Long.valueOf(iptvAlbumInfo
                                                                .getFollownum())) && (vmt.getVideo_type() != null) && (vmt
                                                        .getVideo_type() == VideoConstants.VideoType.YU_GAO_PIAN))) {
                                            VideoDto v = new VideoDto();
                                            // 同样的VideoDto set功能重重复，统一提出了
                                            this.setVideoDtoOfPositive(v, vmt, iptvAlbumInfo, albumCharge,
                                                    isVarietyShow, commonParam);
                                            // 唯一一个差异字端
                                            v.setPreType(0);
                                            playList.add(v);
                                        }
                                    }
                                }
                            } else if (categoryId != null && categoryId == VideoConstants.Category.TEACH) {// 教育频道
                                String queryType = VideoTpConstant.QUERY_TYPE_POSITIVE;
                                if (iptvAlbumInfo != null) {
                                    Integer albumType = iptvAlbumInfo.getAlbum_type();
                                    if (VideoCommonUtil.isPositive(2, iptvAlbumInfo.getCategory(), albumType, null)) {
                                        if (albumType != null && albumType != 180001) {
                                            queryType = VideoTpConstant.QUERY_TYPE_ALL;
                                        }
                                    }
                                }

                                videoList = albumVideoAccess.getVideoRange(albumId, queryType,
                                        -1, 0, Integer.MAX_VALUE, commonParam, 4);
                                int midSize = pageSize / 2;// 前后各取多少集
                                int start = initPos - midSize >= 0 ? initPos - midSize : 0;// 开始剧集数
                                int end = initPos + midSize;// 结束剧集数
                                if (initPos != -1) {
                                    Set<VideoDto> beforeList = new TreeSet<VideoDto>();// 当前剧集位置之前
                                    Set<VideoDto> afterList = new TreeSet<VideoDto>();// 当前剧集位置之后
                                    for (VideoMysqlTable vmt : videoList) {
                                        if (vmt.getPorder() < initPos) {
                                            if (vmt.getPorder() < start) {
                                                continue;
                                            }
                                            VideoDto v = new VideoDto();
                                            // 同样的VideoDto set功能重重复，统一提出了
                                            this.setVideoDtoOfPositive(v, vmt, iptvAlbumInfo, albumCharge,
                                                    isVarietyShow, commonParam);

                                            beforeList.add(v);
                                        } else {
                                            if (vmt.getPorder() <= end) {
                                                VideoDto v = new VideoDto();
                                                // 同样的VideoDto set功能重重复，统一提出了
                                                this.setVideoDtoOfPositive(v, vmt, iptvAlbumInfo, albumCharge,
                                                        isVarietyShow, commonParam);
                                                afterList.add(v);
                                            }
                                        }
                                    }

                                    playList.addAll(beforeList);
                                    VideoDto v = new VideoDto();
                                    // 同样的VideoDto set功能重重复，统一提出了
                                    this.setVideoDtoOfPositive(v, video, iptvAlbumInfo, albumCharge, isVarietyShow,
                                            commonParam);
                                    boolean contain = false;
                                    for (VideoDto sv : beforeList) {
                                        if (sv.getVideoId().equalsIgnoreCase(v.getVideoId())) {
                                            contain = true;
                                        }
                                    }
                                    for (VideoDto qv : afterList) {
                                        if (qv.getVideoId().equalsIgnoreCase(v.getVideoId())) {
                                            contain = true;
                                        }
                                    }
                                    if (!contain) {
                                        playList.add(v);
                                    }
                                    playList.addAll(afterList);
                                } else {
                                    for (VideoMysqlTable vmt : videoList) {
                                        if ((vmt.getPorder() >= startPos) && playList.size() <= pageSize) {
                                            VideoDto v = new VideoDto();
                                            // 同样的VideoDto set功能重重复，统一提出了
                                            this.setVideoDtoOfPositive(v, vmt, iptvAlbumInfo, albumCharge,
                                                    isVarietyShow, commonParam);
                                            playList.add(v);
                                        }
                                    }
                                }
                            } else {
                                // 其他频道，必是正片
                                Integer orderType = CommonConstants.ASC;
                                Integer porder = -1;// 等价于CommonConstants.DESC
                                if (iptvAlbumInfo != null
                                        && (iptvAlbumInfo.getCategory() == VideoConstants.Category.FILM || iptvAlbumInfo
                                                .getCategory() == VideoConstants.Category.DFILM)) {
                                    orderType = CommonConstants.DESC;
                                    porder = 1;
                                }

                                if (iptvAlbumInfo != null
                                        && iptvAlbumInfo.getCategory() == VideoConstants.Category.MUSIC
                                        && StringUtils.isNotEmpty(iptvAlbumInfo.getSub_category())
                                        && (iptvAlbumInfo.getSub_category().contains(
                                                VideoConstants.MusicSubCategory.CONCERT)
                                                || iptvAlbumInfo.getSub_category().contains(
                                                        VideoConstants.MusicSubCategory.AWARDS)
                                                || iptvAlbumInfo.getSub_category().contains(
                                                        VideoConstants.MusicSubCategory.MUSIC_FILM) || iptvAlbumInfo
                                                .getSub_category().contains(VideoConstants.MusicSubCategory.DFILM))) {

                                    orderType = CommonConstants.ASC;
                                    porder = -1;
                                } else {
                                    orderType = CommonConstants.DESC;
                                    porder = 1;
                                }

                                if (categoryId != null && categoryId == VideoConstants.Category.TEACH) {
                                    // 教育频道下拉列表修改为正序，但是getVideoListByPorderAndCreateTime的SQL正序倒序正好相反，所以取值DESC
                                    orderType = CommonConstants.ASC;
                                    porder = -1;
                                }
                                Integer albumType = (iptvAlbumInfo == null ? null : iptvAlbumInfo.getAlbum_type());
                                String queryType = VideoTpConstant.QUERY_TYPE_POSITIVE;
                                if (VideoCommonUtil.isPositive(2, categoryId, albumType, null)) {
                                    if (albumType != null && albumType != 180001) {
                                        queryType = VideoTpConstant.QUERY_TYPE_ALL;
                                    }
                                }
                                videoList = albumVideoAccess.getVideoRange(albumId, queryType,
                                        porder, 0, Integer.MAX_VALUE, commonParam, 4);

                                int queueSize = pageSize / 2;

                                if (initPos != -1) {
                                    int fromIndex = 0, toIndex = 0, listSize = videoList.size();
                                    for (int i = 0; i < listSize; i++) {
                                        if ((orderType == CommonConstants.ASC && videoList.get(i).getPorder() >= initPos)
                                                || (orderType == CommonConstants.DESC && videoList.get(i).getPorder() <= initPos)
                                                || i == (listSize - 1)) {
                                            fromIndex = (i - queueSize) < 0 ? 0 : (i - queueSize);
                                            toIndex = (i + queueSize) > listSize ? listSize : i + queueSize;
                                            break;
                                        }
                                    }
                                    List<VideoMysqlTable> subList = videoList.subList(fromIndex, toIndex);
                                    for (VideoMysqlTable v : subList) {
                                        VideoDto dto = new VideoDto();
                                        setVideoDtoOfPositive(dto, v, iptvAlbumInfo, albumCharge, isVarietyShow,
                                                commonParam);
                                        playList.add(dto);
                                    }
                                } else {
                                    for (VideoMysqlTable vmt : videoList) {
                                        if ((vmt.getPorder() >= startPos) && playList.size() <= pageSize) {
                                            VideoDto videoDto = new VideoDto();
                                            // set方法重复统一提出了
                                            this.setVideoDtoOfPositive(videoDto, vmt, iptvAlbumInfo, albumCharge,
                                                    isVarietyShow, commonParam);

                                            playList.add(videoDto);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        List<VideoMysqlTable> videoList = null;
                        boolean isMusicSegment = VideoUtil.isMusicSegment(video);
                        if (categoryId != null && categoryId == 9) {
                            videoList = albumVideoAccess.getVideoRange(albumId,
                                    VideoTpConstant.QUERY_TYPE_ALL, -1, 0, Integer.MAX_VALUE, commonParam, 1);
                        } else {
                            // 非音乐类型非正片
                            videoList = albumVideoAccess.getVideoRange(albumId,
                                    VideoTpConstant.QUERY_TYPE_NON_POSITIVE, 1, 0, Integer.MAX_VALUE, commonParam, 3);
                        }

                        for (VideoMysqlTable vmt : videoList) {
                            VideoDto videoDto = new VideoDto();
                            this.setVideoDtoOfTrailer(videoDto, vmt, commonParam);
                            if (isMusicSegment) {// 音乐片段，列表只返只要片段
                                if (VideoUtil.isMusicSegment(vmt)) {
                                    playList.add(videoDto);
                                }
                            } else {
                                playList.add(videoDto);
                            }
                        }
                    }
                }
            }
        }
        return playList;
    }

    private Collection<BaseData> getLetvPlayList(Long videoId, Integer startPos, Integer initPos, Integer pageSize,
            String history, Integer model, Long albumId, Integer categoryId, CommonParam commonParam) {
        Collection<BaseData> playList = new ArrayList<BaseData>();

        VideoMysqlTable video = albumVideoAccess.getVideo(videoId, commonParam);
        if (video != null) {
            albumId = video.getPid();
            categoryId = video.getCategory();
            Integer videoType = video.getVideo_type();
            int num = VideoConstants.SCREEN_SIZE * 3;
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) {
                num = VideoConstants.LE_SCREEN_SIZE * 2;
            }
            if (albumId == null || albumId == 0) {
                // 视频无专辑逻辑，走单板块推荐
                VideoDto current = new VideoDto();
                current.setVideoId(String.valueOf(video.getId()));
                current.setName(video.getName_cn());
                current.setImg(video.getPic(400, 300));
                current.setAlbumId(String.valueOf(video.getPid()));
                current.setCategoryId(video.getCategory());
                if (video.getCategory() == VideoConstants.Category.MUSIC) {// 音乐
                    current.setSubName(video.getStarring());// 副标题为歌星
                } else if (video.getCategory() == VideoConstants.Category.SPORT) {// 体育
                    // 无副标题
                } else {
                    current.setSubName(video.getSub_title());
                }

                playList.add(current);
                Collection<BaseData> recList = recommendationService
                        .getSingleBlock(categoryId, null, videoId.intValue(), null, num, "rec_0010", null, history,
                                null, commonParam).getData();
                if (!CollectionUtils.isEmpty(recList)) {
                    for (BaseData baseData : recList) {
                        if (baseData instanceof VideoDto) {
                            playList.add(baseData);
                        }
                    }
                }
            } else {
                // 视频有专辑逻辑
                AlbumMysqlTable iptvAlbumInfo = null;

                if (albumId != null) {
                    iptvAlbumInfo = albumVideoAccess.getAlbum(albumId, commonParam);

                    boolean isVarietyShow = false; // 是否设置成综艺样式
                    boolean albumCharge = false;// 专辑是否收费
                    if (iptvAlbumInfo != null) {
                        albumCharge = VideoCommonUtil.isCharge(iptvAlbumInfo.getPay_platform(),
                                iptvAlbumInfo.getPlay_platform(), commonParam.getP_devType());
                        isVarietyShow = VideoUtil.isVarietyShow(iptvAlbumInfo, commonParam);
                    }

                    if (this.isPositive(videoType, categoryId)
                            || ((categoryId == VideoConstants.Category.TV || categoryId == VideoConstants.Category.CARTOON) && ((StringUtils
                                    .isNotEmpty(video.getEpisode())
                                    && video.getEpisode().matches("\\d*")
                                    && iptvAlbumInfo != null
                                    && iptvAlbumInfo.getFollownum() != null
                                    && (Long.valueOf(video.getEpisode()) <= (iptvAlbumInfo.getFollownum() + 3))
                                    && (Long.valueOf(video.getEpisode()) > Long.valueOf(iptvAlbumInfo.getFollownum()))
                                    && (video.getVideo_type() != null) && (video.getVideo_type() == VideoConstants.VideoType.YU_GAO_PIAN))))) {
                        // 如果是正片视频，或电视剧、卡通下，当前视频集数大于正剧跟播集数3集以内的预告片
                        if (categoryId == VideoConstants.Category.FILM && iptvAlbumInfo != null) {
                            // 如果当前视频属于电影频道，则走单板块推荐；可以确定的是当前视频是正片
                            Collection<BaseData> recList = recommendationService
                                    .getSingleBlock(categoryId, albumId.intValue(), videoId.intValue(), null, num,
                                            "rec_0010", null, history, null, commonParam).getData();
                            if (recList != null) {
                                List<BaseData> delList = new LinkedList<>();
                                for (BaseData bd : recList) {
                                    if (bd instanceof AlbumDto) {
                                        AlbumMysqlTable a = albumVideoAccess.getAlbum(
                                                Long.valueOf(((AlbumDto) bd).getAlbumId()), commonParam);
                                        if (a != null) {
                                            ((AlbumDto) bd).setScore(a.getScore());
                                        } else {
                                            delList.add(bd);
                                        }
                                    } else if (bd instanceof VideoDto) { // 电影频道客户端直接收专辑,过滤掉视频
                                        delList.add(bd);
                                    }
                                }
                                if (delList.size() > 0) {
                                    recList.removeAll(delList);
                                }
                                playList = recList;
                            }
                        } else {
                            List<VideoMysqlTable> videoList = null;
                            if (categoryId != null
                                    && (categoryId == VideoConstants.Category.TV || categoryId == VideoConstants.Category.CARTOON)) {
                                // 如果当前视频属于电视剧、卡通频道，则要么是正片，要么是当前视频集数大于正剧跟播集数3集以内的预告片
                                int s = 0;
                                if (initPos != null && initPos != -1) {
                                    s = (initPos - pageSize / 2) >= 0 ? (initPos - pageSize / 2) : 0;
                                } else {
                                    s = startPos;
                                }

                                int end = s + pageSize - 1;

                                if (isVarietyShow) { // 如果是按照综艺样式展示，则倒序
                                    videoList = albumVideoAccess.getVideoRange(albumId,
                                            VideoTpConstant.QUERY_TYPE_POSITIVE, 1, 0, Integer.MAX_VALUE, commonParam,
                                            4);
                                } else {
                                    videoList = albumVideoAccess.getVideoRange(albumId,
                                            VideoTpConstant.QUERY_TYPE_ALL, -1, s, end, commonParam, 1);
                                }

                                if (videoList != null) {
                                    for (VideoMysqlTable vmt : videoList) {
                                        if (this.isPositive(vmt.getVideo_type(), vmt.getCategory())
                                                || (StringUtils.isNotEmpty(vmt.getEpisode())
                                                        && vmt.getEpisode().matches("\\d*")
                                                        && iptvAlbumInfo != null
                                                        && iptvAlbumInfo.getFollownum() != null
                                                        && (Long.valueOf(vmt.getEpisode()) <= (iptvAlbumInfo
                                                                .getFollownum() + 3))
                                                        && (Long.valueOf(vmt.getEpisode()) > Long.valueOf(iptvAlbumInfo
                                                                .getFollownum())) && (vmt.getVideo_type() != null) && (vmt
                                                        .getVideo_type() == VideoConstants.VideoType.YU_GAO_PIAN))) {
                                            VideoDto v = new VideoDto();
                                            // 同样的VideoDto set功能重重复，统一提出了
                                            this.setVideoDtoOfPositive(v, vmt, iptvAlbumInfo, albumCharge,
                                                    isVarietyShow, commonParam);
                                            // 唯一一个差异字端
                                            v.setPreType(0);
                                            playList.add(v);
                                        }
                                    }
                                }
                            } else if (categoryId != null && categoryId == VideoConstants.Category.TEACH) {// 教育频道
                                String queryType = VideoTpConstant.QUERY_TYPE_POSITIVE;
                                if (iptvAlbumInfo != null) {
                                    Integer albumType = iptvAlbumInfo.getAlbum_type();
                                    if (VideoCommonUtil.isPositive(2, iptvAlbumInfo.getCategory(), albumType, null)) {
                                        if (albumType != null && albumType != 180001) {
                                            queryType = VideoTpConstant.QUERY_TYPE_ALL;
                                        }
                                    }
                                }

                                videoList = albumVideoAccess.getVideoRange(albumId, queryType,
                                        -1, 0, Integer.MAX_VALUE, commonParam, 4);
                                int midSize = pageSize / 2;// 前后各取多少集
                                int start = initPos - midSize >= 0 ? initPos - midSize : 0;// 开始剧集数
                                int end = initPos + midSize;// 结束剧集数
                                if (initPos != -1) {
                                    Set<VideoDto> beforeList = new TreeSet<VideoDto>();// 当前剧集位置之前
                                    Set<VideoDto> afterList = new TreeSet<VideoDto>();// 当前剧集位置之后
                                    for (VideoMysqlTable vmt : videoList) {
                                        if (vmt.getPorder() < initPos) {
                                            if (vmt.getPorder() < start) {
                                                continue;
                                            }
                                            VideoDto v = new VideoDto();
                                            // 同样的VideoDto set功能重重复，统一提出了
                                            this.setVideoDtoOfPositive(v, vmt, iptvAlbumInfo, albumCharge,
                                                    isVarietyShow, commonParam);

                                            beforeList.add(v);
                                        } else {
                                            if (vmt.getPorder() <= end) {
                                                VideoDto v = new VideoDto();
                                                // 同样的VideoDto set功能重重复，统一提出了
                                                this.setVideoDtoOfPositive(v, vmt, iptvAlbumInfo, albumCharge,
                                                        isVarietyShow, commonParam);
                                                afterList.add(v);
                                            }
                                        }
                                    }

                                    playList.addAll(beforeList);
                                    VideoDto v = new VideoDto();
                                    // 同样的VideoDto set功能重重复，统一提出了
                                    this.setVideoDtoOfPositive(v, video, iptvAlbumInfo, albumCharge, isVarietyShow,
                                            commonParam);
                                    boolean contain = false;
                                    for (VideoDto sv : beforeList) {
                                        if (sv.getVideoId().equalsIgnoreCase(v.getVideoId())) {
                                            contain = true;
                                        }
                                    }
                                    for (VideoDto qv : afterList) {
                                        if (qv.getVideoId().equalsIgnoreCase(v.getVideoId())) {
                                            contain = true;
                                        }
                                    }
                                    if (!contain) {
                                        playList.add(v);
                                    }
                                    playList.addAll(afterList);
                                } else {
                                    for (VideoMysqlTable vmt : videoList) {
                                        if ((vmt.getPorder() >= startPos) && playList.size() <= pageSize) {
                                            VideoDto v = new VideoDto();
                                            // 同样的VideoDto set功能重重复，统一提出了
                                            this.setVideoDtoOfPositive(v, vmt, iptvAlbumInfo, albumCharge,
                                                    isVarietyShow, commonParam);
                                            playList.add(v);
                                        }
                                    }
                                }
                            } else {
                                // 其他频道，必是正片
                                Integer orderType = CommonConstants.ASC;
                                Integer porder = -1;// 等价于CommonConstants.DESC
                                if (iptvAlbumInfo != null
                                        && (iptvAlbumInfo.getCategory() == VideoConstants.Category.FILM || iptvAlbumInfo
                                                .getCategory() == VideoConstants.Category.DFILM)) {
                                    orderType = CommonConstants.DESC;
                                    porder = 1;
                                }

                                if (iptvAlbumInfo != null
                                        && iptvAlbumInfo.getCategory() == VideoConstants.Category.MUSIC
                                        && StringUtils.isNotEmpty(iptvAlbumInfo.getSub_category())
                                        && (iptvAlbumInfo.getSub_category().contains(
                                                VideoConstants.MusicSubCategory.CONCERT)
                                                || iptvAlbumInfo.getSub_category().contains(
                                                        VideoConstants.MusicSubCategory.AWARDS)
                                                || iptvAlbumInfo.getSub_category().contains(
                                                        VideoConstants.MusicSubCategory.MUSIC_FILM) || iptvAlbumInfo
                                                .getSub_category().contains(VideoConstants.MusicSubCategory.DFILM))) {

                                    orderType = CommonConstants.ASC;
                                    porder = -1;
                                } else {
                                    orderType = CommonConstants.DESC;
                                    porder = 1;
                                }

                                if (categoryId != null && categoryId == VideoConstants.Category.TEACH) {
                                    // 教育频道下拉列表修改为正序，但是getVideoListByPorderAndCreateTime的SQL正序倒序正好相反，所以取值DESC
                                    orderType = CommonConstants.ASC;
                                    porder = -1;
                                }
                                Integer albumType = (iptvAlbumInfo == null ? null : iptvAlbumInfo.getAlbum_type());
                                String queryType = VideoTpConstant.QUERY_TYPE_POSITIVE;
                                if (VideoCommonUtil.isPositive(2, categoryId, albumType, null)) {
                                    if (albumType != null && albumType != 180001) {
                                        queryType = VideoTpConstant.QUERY_TYPE_ALL;
                                    }
                                }

                                int start = 0;
                                int end = Integer.MAX_VALUE;
                                if (porder == -1) {
                                    // 2016-11-28目前仅做正序下的分页
                                    if (initPos != null && initPos != -1) {
                                        start = (initPos - pageSize / 2) >= 0 ? (initPos - pageSize / 2) : 0;
                                    } else {
                                        start = startPos;
                                    }
                                    end = start + pageSize - 1;
                                } else {
                                    // 降序不方便处理分页，暂定只取部分数据
                                    if (initPos != null && initPos != -1) {
                                        // 第一次取数据,
                                        end = pageSize;
                                    } else {
                                        // 非第一次取
                                        start = -1;
                                        end = -1;
                                    }
                                }

                                if (categoryId != null && categoryId == VideoConstants.Category.DFILM) {
                                    porder = -1;
                                }

                                if (start >= 0 && end > 0) {
                                    videoList = albumVideoAccess.getVideoRange(albumId,
                                            queryType, porder, start, end, commonParam, 4);

                                    int queueSize = pageSize / 2;

                                    if (initPos != -1) {
                                        int fromIndex = 0, toIndex = 0, listSize = videoList.size();
                                        for (int i = 0; i < listSize; i++) {
                                            if ((orderType == CommonConstants.ASC && videoList.get(i).getPorder() >= initPos)
                                                    || (orderType == CommonConstants.DESC && videoList.get(i)
                                                            .getPorder() <= initPos) || i == (listSize - 1)) {
                                                fromIndex = (i - queueSize) < 0 ? 0 : (i - queueSize);
                                                toIndex = (i + queueSize) > listSize ? listSize : i + queueSize;
                                                break;
                                            }
                                        }
                                        List<VideoMysqlTable> subList = videoList.subList(fromIndex, toIndex);
                                        for (VideoMysqlTable v : subList) {
                                            VideoDto dto = new VideoDto();
                                            setVideoDtoOfPositive(dto, v, iptvAlbumInfo, albumCharge, isVarietyShow,
                                                    commonParam);
                                            playList.add(dto);
                                        }
                                    } else {
                                        for (VideoMysqlTable vmt : videoList) {
                                            if ((vmt.getPorder() >= startPos) && playList.size() <= pageSize) {
                                                VideoDto videoDto = new VideoDto();
                                                // set方法重复统一提出了
                                                this.setVideoDtoOfPositive(videoDto, vmt, iptvAlbumInfo, albumCharge,
                                                        isVarietyShow, commonParam);

                                                playList.add(videoDto);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {

                        List<VideoMysqlTable> videoList = null;
                        boolean isMusicSegment = VideoUtil.isMusicSegment(video);
                        if (categoryId != null && categoryId == 9) {
                            videoList = albumVideoAccess.getVideoRange(albumId,
                                    VideoTpConstant.QUERY_TYPE_ALL, -1, 0, Integer.MAX_VALUE, commonParam, 1);
                        } else {
                            // 非音乐类型非正片
                            videoList = albumVideoAccess.getVideoRange(albumId,
                                    VideoTpConstant.QUERY_TYPE_NON_POSITIVE, 1, 0, Integer.MAX_VALUE, commonParam, 3);
                        }

                        for (VideoMysqlTable vmt : videoList) {
                            VideoDto videoDto = new VideoDto();
                            this.setVideoDtoOfTrailer(videoDto, vmt, commonParam);
                            if (isMusicSegment) {// 音乐片段，列表只返只要片段
                                if (VideoUtil.isMusicSegment(vmt)) {
                                    playList.add(videoDto);
                                }
                            } else {
                                playList.add(videoDto);
                            }
                        }
                    }
                }
            }
        }
        return playList;
    }

    /**
     * 获取专辑类型文案完整key
     * @param albumType
     * @return
     */
    private String getAlbumTypeNameTextKey(Integer albumType) {
        if (albumType != null) {
            return VideoConstants.VIDEO_AND_ALBUM_TYPE_NAME_TEXT_PREFIX + albumType;
        }
        return null;
    }

    /**
     * 根据vid获取当前视频所在的剧集列表分页
     * @param history
     * @param albumId
     * @param commonParam
     * @return
     */
    private AlbumSeriesPlayListPageDto getLecomAlbumSeriesPageByVid(Long albumId, VideoMysqlTable video,
            boolean forcePage, String history, CommonParam commonParam) {
        AlbumSeriesPlayListPageDto seriesPage = null;

        AlbumMysqlTable iptvAlbumInfo = albumVideoAccess.getAlbum(albumId, commonParam);
        if (iptvAlbumInfo != null) {
            seriesPage = new AlbumSeriesPlayListPageDto();

            // 如果所属专辑有版权，则取剧集，否则不做处理，则无下拉列表（2016-12-22与产品卢彦合确认该需求）
            Integer categoryId = iptvAlbumInfo.getCategory();
            boolean albumPositive = iptvAlbumInfo.isPositive();
            boolean videoPositive = video.isPositive();

            seriesPage.setAlbumId(String.valueOf(albumId));
            seriesPage.setAlbumPositive(albumPositive ? 1 : 0);
            // seriesPage.setCurPage(page);
            seriesPage.setOrderType(VideoTpConstant.VIDEO_MMS_SERIES_ORDER_ASC);
            seriesPage.setPageSize(VideoTpConstant.ALBUM_SERIES_PAGE_SIZE);
            // seriesPage.setStype(stype);
            if (categoryId != null
                    && (categoryId == MmsTpConstant.MMS_CATEGARY_TV || categoryId == MmsTpConstant.MMS_CATEGARY_VARIETY
                            || categoryId == MmsTpConstant.MMS_CATEGARY_CARTOON
                            || categoryId == MmsTpConstant.MMS_CATEGARY_DFILM || categoryId == MmsTpConstant.MMS_CATEGARY_EDUCATION)) {
                // 电视剧/综艺/动漫/纪录片/教育频道考虑媒资后台“是否剧集展示-varietyShow”字段
                seriesPage.setVarietyShow(VideoUtil.isVarietyShow(iptvAlbumInfo, commonParam) ? 1 : 0);
            }

            List<VideoMysqlTable> videoList = null;
            if (albumPositive) {
                if (videoPositive) {
                    // 先按频道，再按所播视频的videoType属性
                    switch (categoryId) {
                    case MmsTpConstant.MMS_CATEGARY_FILM:
                        seriesPage = getRecVideoPlaySeriesPage(true, history, video, seriesPage, commonParam);
                        break;
                    default:
                        Integer totalNum = null;
                        if (MmsTpConstant.MMS_CATEGARY_TV == categoryId
                                || MmsTpConstant.MMS_CATEGARY_VARIETY == categoryId
                                || MmsTpConstant.MMS_CATEGARY_CARTOON == categoryId
                                || MmsTpConstant.MMS_CATEGARY_DFILM == categoryId
                                || MmsTpConstant.MMS_CATEGARY_SPORT == categoryId
                                || MmsTpConstant.MMS_CATEGARY_EDUCATION == categoryId) {
                            // 电影、电视剧、综艺、动漫、纪录片、体育、教育频道，正片剧集去全部正片视频，正序，可以支持分页
                            totalNum = albumVideoAccess.getVideoTotalNum(albumId,
                                    VideoTpConstant.QUERY_TYPE_POSITIVE, commonParam);
                        } else {
                            // 除以上其他频道，正片取所有视频
                            totalNum = albumVideoAccess.getVideoTotalNum(albumId,
                                    VideoTpConstant.QUERY_TYPE_ALL, commonParam);
                        }
                        if (totalNum != null && totalNum > 0) {
                            seriesPage.setTotalNum(totalNum);
                            seriesPage.setStype(VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_POSITIVE);
                            int totalPage = (totalNum / seriesPage.getPageSize()) + 1;
                            seriesPage.setTotalPage(totalPage);
                            for (int i = 1; i <= totalPage; i++) {
                                boolean containVid = false;
                                seriesPage.setCurPage(i);
                                videoList = getLecomAlbumSeriesPage(iptvAlbumInfo.getId(), categoryId, true,
                                        seriesPage, commonParam);

                                if (!CollectionUtils.isEmpty(videoList)) {
                                    StringBuilder sb = new StringBuilder();
                                    for (VideoMysqlTable vi : videoList) {
                                        if (vi != null && vi.getId() != null
                                                && video.getId().longValue() == vi.getId().longValue()) {
                                            sb.append(String.valueOf(video.getId()));
                                            containVid = true;
                                            break;
                                        }

                                    }
                                }
                                if (containVid) {
                                    break;
                                } else {
                                    videoList = null;// 不匹配则清空数据
                                }
                            }
                            if (!CollectionUtils.isEmpty(videoList)) {
                                Collection<BaseData> series = new ArrayList<BaseData>();
                                for (VideoMysqlTable vi : videoList) {
                                    if (vi != null) {
                                        VideoDto videoDto = parseLecomAlbumSeriesVideo(vi);
                                        if (videoDto != null) {
                                            series.add(videoDto);
                                        }
                                    }
                                }
                                seriesPage.setData(series);
                            }
                        }
                        break;
                    }
                } else {
                    // 非正片，则取周边视频
                    Integer totalNum = albumVideoAccess.getVideoTotalNum(albumId,
                            VideoTpConstant.QUERY_TYPE_NON_POSITIVE, commonParam);
                    if (totalNum != null && totalNum > 0) {
                        seriesPage.setTotalNum(totalNum);
                        seriesPage.setStype(VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_ATTACHING);
                        int totalPage = (totalNum / seriesPage.getPageSize()) + 1;
                        seriesPage.setTotalPage(totalPage);
                        for (int i = 1; i <= totalPage; i++) {
                            seriesPage.setCurPage(i);
                            videoList = getLecomAlbumSeriesPage(iptvAlbumInfo.getId(), categoryId, true, seriesPage,
                                    commonParam);
                            if (!CollectionUtils.isEmpty(videoList)) {
                                for (VideoMysqlTable vi : videoList) {
                                    if (vi != null && vi.getId() != null
                                            && video.getId().longValue() == vi.getId().longValue()) {
                                        break;
                                    }
                                }
                            }
                        }
                        if (!CollectionUtils.isEmpty(videoList)) {
                            Collection<BaseData> series = new ArrayList<BaseData>();
                            for (VideoMysqlTable vi : videoList) {
                                if (vi != null) {
                                    VideoDto videoDto = parseLecomAlbumSeriesVideo(vi);
                                    if (videoDto != null) {
                                        series.add(videoDto);
                                    }
                                }
                            }
                            seriesPage.setData(series);
                        }
                    }
                }
            } else {
                // 预告专辑，只有正片剧集，取所有视频；这里可以考虑按分页或全部取出
                // 这里只针对第一次结果出数据，后续左右横滑不再返回数据
                Integer totalNum = albumVideoAccess.getVideoTotalNum(albumId,
                        VideoTpConstant.QUERY_TYPE_ALL, commonParam);
                if (totalNum != null && totalNum > 0) {
                    seriesPage.setTotalNum(totalNum);
                    seriesPage.setStype(VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_POSITIVE);
                    int totalPage = (totalNum / seriesPage.getPageSize()) + 1;
                    seriesPage.setTotalPage(totalPage);
                    for (int i = 1; i <= totalPage; i++) {
                        seriesPage.setCurPage(i);
                        videoList = getLecomAlbumSeriesPage(iptvAlbumInfo.getId(), categoryId, true, seriesPage,
                                commonParam);
                        if (!CollectionUtils.isEmpty(videoList)) {
                            for (VideoMysqlTable vi : videoList) {
                                if (vi != null && vi.getId() != null
                                        && video.getId().longValue() == vi.getId().longValue()) {
                                    break;
                                }
                            }
                        }
                    }
                    if (!CollectionUtils.isEmpty(videoList)) {
                        Collection<BaseData> series = new ArrayList<BaseData>();
                        for (VideoMysqlTable vi : videoList) {
                            if (vi != null) {
                                VideoDto videoDto = parseLecomAlbumSeriesVideo(vi);
                                if (videoDto != null) {
                                    series.add(videoDto);
                                }
                            }
                        }
                        seriesPage.setData(series);
                    }
                }
            }
        }
        return seriesPage;
    }

    public BaseResponse clearCache(String key, CommonParam commonParam) {
        log.info("The opt of clearCache() visited by clientIp: " + HttpUtil.getIP());
        String errorCode = null;
        Response response = new Response();
        String[] whiteIps = { "127.0.0.1", "10.58.89.201" };
        if (Arrays.asList(whiteIps).contains(HttpUtil.getIP())) {
            if (StringUtil.isNotBlank(key)) {
                this.facadeCacheDao.getVideoCacheDao().deleteCache(key);
            }
        } else {
            errorCode = ErrorCodeConstants.COMMON_ILLEGAL_PARAMETER;
            Integer valcode = CommonMsgCodeConstant.REUQEST_ILLEGAL_VISIT_ERROR;
            String errorMsg = MessageUtils.getMessage(
                    ErrorMsgCodeUtil.parseErrorMsgCode(errorCode, BusinessCodeConstant.PLAY, valcode),
                    commonParam.getLangcode());
            ResponseUtil.setErrorResponse(response, errorCode, errorMsg, commonParam.getWcode());
        }
        return response;
    }

    /**
     * 详情页动态接口数据组装
     * @param albumId
     * @param posids
     * @param commonParam
     * @return
     */
    public Response<AlbumDetailMoreDto> getAlbumDetailMore(Long albumId,
    // 观星位置
            String posids,
            // 收藏状态
            Integer favoriteType, Integer ztId, String globalId, CommonParam commonParam) {
        Response response = null;
        String logPrefix = "getAlbumDetailMore_" + albumId + "_" + commonParam.getMac();
        AlbumDetailMoreDto albumDetailMoreDto = null;
        AlbumDto albumDto = null;
        String errorCode = null, errorMsg = null;
        Boolean useCache = true;

        if (null != commonParam) {
            commonParam.setIsDync(1);
        }

        if (albumId.intValue() == 0 && commonParam.isFlush()) {
            this.flushAllWeeklyRankInfo();
            log.info(logPrefix + ": successfully clear all cache!");
            return new Response();
        }

        if (useCache && !commonParam.isFlush()) {
            response = this.getAlbumDpMoreFromCache(String.valueOf(albumId), commonParam);
        }

        AlbumMysqlTable albumMysqlTable = null;
        if (null == response) {
            response = new Response();
            albumMysqlTable = albumVideoAccess.getAlbum(albumId, commonParam);
        }

        if (albumMysqlTable != null) {
            albumDetailMoreDto = new AlbumDetailMoreDto();
            albumDto = new AlbumDto();

            // 标签组
            TotalCountStatTpResponse statResp = this.getAlbumTotalCountStat(albumId, commonParam);
            if (statResp != null) {
                albumDto.setAlbumId(String.valueOf(albumId));
                albumDto.setVv(statResp.getPlist_play_count());
                albumDto.setCommentCnt(statResp.getPcomm_count());
                if (StringUtil.isNotBlank(statResp.getPlist_score())) {
                    try {
                        Float score = Float.parseFloat(statResp.getPlist_score());
                        if (null != score) {
                            albumDto.setScore(score);
                            albumMysqlTable.setScore(score);
                        }
                    } catch (Exception e) {
                    }
                }

                if (StringUtil.isNotBlank(statResp.getDbsp())) {
                    try {
                        Float score = Float.parseFloat(statResp.getDbsp());
                        if (null != score) {
                            albumDto.setDb_score(score);
                            albumMysqlTable.setDb_score(score);
                        }
                    } catch (Exception e) {
                    }
                }
            } else {
                log.info(logPrefix + ": failed to get the album's total count");
            }
            // 获取专辑排行榜名次
            StatRankWeekData statRankWeekData = this.getWeeklyRankInfo(String.valueOf(albumId),
                    albumMysqlTable.getCategory(), commonParam.isFlush());
            if (null != statRankWeekData) {
                albumDto.setRank(statRankWeekData.getRank());
            }
            albumDetailMoreDto.setTags(AlbumDto.buildTags(albumMysqlTable, albumDto, commonParam));

            // 推荐视频集合
            List<VideoMysqlTable> iptvVideoList = albumVideoAccess.getVideoRange(
                    albumMysqlTable.getId(), VideoTpConstant.QUERY_TYPE_POSITIVE, -1, 0, 1, commonParam, 4);
            if (!CollectionUtils.isEmpty(iptvVideoList)) {
                Set<VideoDto> positiveSeries = new TreeSet<VideoDto>(); // 剧集列表扩展（预告、抢先看等，正片剧集的补充）
                VideoDto videoDto = null;
                for (VideoMysqlTable vi : iptvVideoList) {
                    videoDto = new VideoDto();
                    videoDto.setAlbumId(String.valueOf(vi.getItv_album_id()));
                    videoDto.setVideoId(String.valueOf(vi.getId()));
                    positiveSeries.add(videoDto);
                }
                albumDto.setPositiveSeries(positiveSeries);
                Collection<BaseData> relations = this.getRelation(albumMysqlTable, albumDto, commonParam);

                // ====================================人工编辑按规则拆成两组给前端展示(B)==================================//
                if (null != albumDto.getRelationAddCount() && albumDto.getRelationAddCount().intValue() >= 4) {
                    List<BaseData> relationAdd = new LinkedList<>();
                    BaseData baseData = null;
                    albumDetailMoreDto.setRelationAddCount(albumDto.getRelationAddCount());
                    Iterator<BaseData> relationsIterator = relations.iterator();
                    int index = 0;
                    while (relationsIterator.hasNext()) {
                        if (++index > albumDto.getRelationAddCount().intValue()) {
                            break;
                        }
                        baseData = relationsIterator.next();
                        relationAdd.add(baseData);
                        relationsIterator.remove();
                    }
                    albumDetailMoreDto.setRelationAdd(relationAdd);
                }
                // ====================================人工编辑按规则拆成两组给前端展示(E)==================================//
                albumDetailMoreDto.setRelation(relations);
            } else {
                log.info(logPrefix + ": failed to get the videos");
            }

            // 观星活动
            if (StringUtil.isNotBlank(posids)) {
                posids = this.filterDpMorePosIds(posids);
                Response<Map<String, List<PromotionDto>>> promotionResp = vipV2Service
                        .getGuanXingPromotion(posids, commonParam, null, null);
                if (null != promotionResp) {
                    if (null != promotionResp.getData()) {
                        albumDetailMoreDto.setVipActivity(promotionResp.getData().get("promotions"));
                    } else {
                        if (StringUtil.isNotBlank(promotionResp.getErrCode())) {
                            log.info(logPrefix + ": failed to get the activities (" + promotionResp.getErrCode() + ")");
                        } else {
                            log.info(logPrefix + ": failed to get the activities");
                        }
                    }
                }
            }

            response.setData(albumDetailMoreDto);

            if (useCache) {
                this.setAlbumDpMoreToCache(String.valueOf(albumId), response);
            }
        } else {
            if (null == response) {
                errorCode = ErrorCodeConstants.COMMON_SERVICE_BUSY;
                errorMsg = MessageUtils.getMessage(errorCode, commonParam.getLangcode());
                ResponseUtil.setErrorResponse(response, errorCode, errorMsg, commonParam.getWcode());
                log.info(logPrefix + ": failed to get the album");
            }
        }

        if (null != favoriteType && null != response && null == response.getErrCode()) {
            if (StringUtil.isBlank(commonParam.getUserToken()) && StringUtil.isNotBlank(commonParam.getToken())) {
                commonParam.setUserToken(commonParam.getToken());
            }
            try {
                Response favoriteRsp = null;
                /**
                 * 目前用户中心提供了2套收藏接口集：play－追剧收藏，favorite－专题（不支持基于专辑ID的综艺、教育等分类）
                 * 目前方式2者兼容，以后待随上游［用户中心］统一！
                 */
                if (favoriteType == 12 || favoriteType == 5) { // 专题
                    favoriteRsp = userService.checkIsFavorite(favoriteType, ztId, albumId,
                            globalId, commonParam);
                } else if (favoriteType == 1) { // 普通点播
                    CheckPlaylistTp checkTp = this.facadeTpDao.getUserTpDao().checkPlaylist(albumId, commonParam);//检查剧集追剧,收藏状态返回参数
                    if (checkTp == null || !"200".equals(checkTp.getCode())) {
                        errorCode = ErrorCodeConstants.USER_CHECK_PLAYLIST_FAILURE;
                        log.info(logPrefix + ": errorcode= " + errorCode + ", failed to get the favorite status");
                    } else {
                        favoriteRsp = new Response();
                        if (null != checkTp.getData().getId() && checkTp.getData().getId().intValue() > 0
                                && checkTp.getData().getFromtype().intValue() > 0) {
                            favoriteRsp.setData(new Boolean(true));
                        } else {
                            favoriteRsp.setData(new Boolean(false));
                        }
                    }
                }
                if (null != favoriteRsp && null != favoriteRsp.getData()) {
                    if (null != response.getData()) {
                        if (response.getData() instanceof LinkedHashMap) {
                            ((LinkedHashMap) response.getData()).put("favStatus", (Boolean) favoriteRsp.getData() ? 1
                                    : 0);
                        } else if (response.getData() instanceof AlbumDetailMoreDto) {
                            ((AlbumDetailMoreDto) response.getData()).setFavStatus((Boolean) favoriteRsp.getData() ? 1
                                    : 0);
                        }
                    }
                }
            } catch (Exception e) {
                log.info(logPrefix + ": failed to get the favorite status");
            }
        }

        return response;
    }

    /**
     * 生成VRS人工编辑推荐数据
     * @param albumMysqlTable
     * @param commonParam
     * @return
     */
    private List<BaseData> makeManualRelation(AlbumMysqlTable albumMysqlTable, CommonParam commonParam) {

        List<BaseData> ret = new LinkedList<BaseData>();

        if (StringUtil.isNotBlank(albumMysqlTable.getRelationAlbumId())) {
            List<Long> aidList = new ArrayList<>();
            for (String aid : albumMysqlTable.getRelationAlbumId().split(",")) {
                try {
                    if (StringUtil.isNotBlank(aid)) {
                        aidList.add(Long.valueOf(aid));
                    }
                } catch (NumberFormatException nfe) {
                }
            }

            int max_aid_count = 6;
            if (aidList.size() > max_aid_count) {
                aidList = aidList.subList(0, max_aid_count);
            }
            if (aidList.size() > 0) {
                Map<String, AlbumMysqlTable> albums = albumVideoAccess.getAlbums(aidList,
                        commonParam);
                // 从［用户中心］获取评分、豆瓣评分
                Map<Long, TotalCountStatTpResponse> albumTotalCountStats = this.getAlbumTotalCountStats(aidList,
                        commonParam);
                TotalCountStatTpResponse totalCountStatTpResponse = null;
                if (albums.size() > 0) {
                    BaseData tmpBaseData = null;
                    for (Long aid : aidList) {
                        AlbumMysqlTable album = albums.get(String.valueOf(aid));
                        if (null != albumTotalCountStats && albumTotalCountStats.size() > 0) {
                            totalCountStatTpResponse = albumTotalCountStats.get(aid);
                            if (null != totalCountStatTpResponse) {
                                if (StringUtil.isNotBlank(totalCountStatTpResponse.getPlist_score())) {
                                    try {
                                        Float score = Float.parseFloat(totalCountStatTpResponse.getPlist_score());
                                        if (null != score) {
                                            album.setScore(score);
                                        }
                                    } catch (Exception e) {
                                    }
                                }

                                if (StringUtil.isNotBlank(totalCountStatTpResponse.getDbsp())) {
                                    try {
                                        Float score = Float.parseFloat(totalCountStatTpResponse.getDbsp());
                                        if (null != score) {
                                            album.setDb_score(score);
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                        if (null != album) {
                            tmpBaseData = JumpUtil.genJumpData(JumpUtil.genAlbumDto(album,
                                    RecommendationTpConstant.RECOMMENDATION_AREA_ALBUM_DP_VRS, commonParam),
                                    commonParam);
                            if (null != tmpBaseData) {
                                ret.add(tmpBaseData);
                            }
                        }
                    }
                }
            }
        }

        return ret;
    }

    /**
     * 获取排行榜最新统计日期
     * @return
     */
    private String getLatestRankDate() {
        String statDate = this.facadeCacheDao.getChannelCacheDao().getChartsStartDate();
        if (StringUtil.isBlank(statDate)) {
            statDate = this.facadeMysqlDao.getStatRankWeekDataDao().getLatestStatisticsDate();
            if (StringUtil.isNotBlank(statDate)) {
                this.facadeCacheDao.getChannelCacheDao()
                        .setChartsStartDate(statDate, CommonConstants.SECONDS_OF_1_HOUR);
            }
        }
        return statDate;
    }

    /**
     * 根据albumId, categoryId获取专辑排行信息，如未进入排行榜则返回空
     * @param albumId
     *            专辑ID
     * @param categoryId
     *            专辑分类ID：1:电影 2:电视剧 5:动漫 11:综艺 1021:教育 16:纪录片 34:亲子 10000:总排行
     * @param flush
     *            强制刷新
     * @return 详见StatRankWeekData
     */

    public StatRankWeekData getWeeklyRankInfo(String albumId, Integer categoryId, boolean flush) {
        StatRankWeekData statRankWeekData = null;
        if (flush) {
            this.flushWeeklyRankInfo(categoryId);
        }
        Map<String, StatRankWeekData> statRankWeekDataMap = getWeeklyRankInfo(
                categoryId);
        if (statRankWeekDataMap != null) {
            statRankWeekData = statRankWeekDataMap.get(albumId);
        }
        return statRankWeekData;
    }

    @LocalCacheAnnotation(name = LocalCacheConstant.KEY_WEEKLY_RANK_INFO, expireInMillis = LocalCacheInterface.TEN_MINUTES)
    public Map<String, StatRankWeekData> getWeeklyRankInfo(Integer categoryId) {
        Map<String, StatRankWeekData> statRankWeekDataMap = new HashMap<String, StatRankWeekData>();
        List<StatRankWeekData> statRankWeekDataList = this.facadeMysqlDao.getStatRankWeekDataDao()
                .listLatestByStatDateAndType(categoryId, 0, 100);
        for (StatRankWeekData statData : statRankWeekDataList) {
            statRankWeekDataMap.put(statData.getPid(), statData);
        }
        return statRankWeekDataMap;
    }

    private void flushWeeklyRankInfo(Integer categoryId) {
        if (null != localCacheManager && categoryId != null) {
            LocalCacheInterface localCache = localCacheManager.getCache(LocalCacheConstant.KEY_WEEKLY_RANK_INFO);
            if (localCache != null) {
                localCache.flush(categoryId);
            }
        }
    }

    private void flushAllWeeklyRankInfo() {
        if (null != localCacheManager) {
            LocalCacheInterface localCache = localCacheManager.getCache(LocalCacheConstant.KEY_WEEKLY_RANK_INFO);
            if (localCache != null) {
                localCache.flush(VideoConstants.Category.FILM);
                localCache.flush(VideoConstants.Category.TV);
                localCache.flush(VideoConstants.Category.CARTOON);
                localCache.flush(VideoConstants.Category.VARIETY);
                // TODO: add more
            }
        }
    }

    /**
     * 限制此场景观星活动提取
     * @param posIds
     * @return
     */
    private String filterDpMorePosIds(String posIds) {
        String ret = null;
        String[] posIdsIn = posIds.split(",");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < posIdsIn.length; i++) {
            if (ActivityTpConstant.ACTIVITY_GUANXING_POSITION_DP_VIP_BTN.equals(posIdsIn[i])) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(posIdsIn[i]);
            }
        }

        ret = sb.length() > 0 ? sb.toString() : null;
        return ret;
    }

    private Response getAlbumDpMoreFromCache(String pid, CommonParam commonParam) {
        String key = CacheContentConstants.ALBUM_DP_MORE_KEY_PREFIX;
        if (commonParam.getFlush() != null && commonParam.getFlush() == 1) {
            this.cacheTemplate.delete(key + pid);
            return null;
        }
        return this.cacheTemplate.get(key + pid, Response.class);
    }

    private void setAlbumDpMoreToCache(String pid, Response response) {
        String key = CacheContentConstants.ALBUM_DP_MORE_KEY_PREFIX + pid;
        this.cacheTemplate.set(key, response, CommonConstants.SECONDS_OF_10_MINUTE);
    }
}
