package com.letv.mas.caller.iptv.tvproxy.video.service;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.ChargeInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.GetPlayUserTokenResponse.PlayUserTokenInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.SubscribeInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.GetPayChannelResponse.PayChannel;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.JumpData;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.VideoGuideResponse.VideoGuideInfoTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.Vip;
import com.letv.mas.caller.iptv.tvproxy.video.authentication.PlayAuthV2;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.LetvLeadingPlayInfo.*;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoDto.AudioTrackDto.KbpsDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoDto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.JumpUtil;
import com.letv.mas.caller.iptv.tvproxy.apicommon.util.localcache.LocalCacheManager;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.constant.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoAccess;
import com.letv.mas.caller.iptv.tvproxy.common.model.bean.UserStatus;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.UserChildLockTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockContentTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.ErosToken;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.VideoTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.bean.TrailerVideoDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.ValidateServiceTp;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.VipPackage;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.BossTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.GetUrmActivityBatchResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.GetUrmActivityListResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.BaseResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.response.ReportTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.SessionCache;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import com.letv.mas.caller.iptv.tvproxy.terminal.constant.TerminalConstant;
import com.letv.mas.caller.iptv.tvproxy.terminal.service.TerminalService;
import com.letv.mas.caller.iptv.tvproxy.user.UserConstants;
import com.letv.mas.caller.iptv.tvproxy.user.UserService;
import com.letv.mas.caller.iptv.tvproxy.user.model.dto.ChildLockDto;
import com.letv.mas.caller.iptv.tvproxy.video.chargepolicy.*;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.video.util.VideoUtil;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.PackageInfoDto;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.PilotDto;
import com.letv.mas.caller.iptv.tvproxy.vip.service.VipMetadataService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import search2.extractor.ExtractData;
import search2.extractor.ResultCode;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;

@Service(value = "v2.PlayService")
public class PlayService extends BaseService {

    private final static Logger log = LoggerFactory.getLogger(PlayService.class);

    @Autowired(required = false)
    private SessionCache sessionCache;

    @Autowired
    LocalCacheManager localCacheManager;

    @Autowired
    AlbumVideoAccess albumVideoAccess;

    @Autowired
    UserService userService;

    @Autowired
    TerminalService terminalService;

    @Autowired
    VipMetadataService vipMetadataService;

    @Autowired
    PlayService playService;

    /**
     * 获得视频播放信息——播放接口总方法
     * @param videoId
     *            播放视频id
     * @param clientRequestStream
     *            客户端请求播放的码流
     * @param timestamp
     *            用户登录或启动TV版时拿到的服务器时间戳
     * @param signature
     *            签名
     * @param userName
     *            用户名称
     * @param loginTime
     *            用户登录时间
     * @return
     */
    public Response<VideoDto> getVideoPlayInfo(Long videoId, Long albumId, String clientRequestStream, Long timestamp,
                                               String signature, String userName, String loginTime, Integer pricePackageType, Boolean isFromCntv,
                                               Boolean isFromCibn, Boolean support3d, Integer model, String appCode, String validDate, String audioId,
                                               Integer channelId, String langType, String kbpsType, String imei, String routerId, Integer operType,
                                               String supportStream, Boolean trailer, CommonParam commonParam, boolean isThirdParty, Boolean support4k) {
        String mac = commonParam.getMac();
        Integer broadcastId = commonParam.getBroadcastId();
        String langcode = commonParam.getLangcode();
        String wcode = commonParam.getWcode();
        Response<VideoDto> response = new Response<VideoDto>();
        String errorCode = null;
        String tips = null;
        String logPrefix = "getVideoPlayInfo_" + commonParam.getUsername() + "_" + commonParam.getUserId() + "_"
                + videoId + "_" + clientRequestStream + "_" + mac;

        AlbumMysqlTable albumMysqlTable = null;
        VideoMysqlTable videoMysqlTable = null;
        // 参考频道下默认码流数据返回给客户端的码流，被直接用来播放，这里需要容错
        if ("3d".equals(clientRequestStream)) {
            clientRequestStream = LetvStreamCommonConstants.CODE_NAME_3d720p;
        } else if ("db".equals(clientRequestStream)) {
            clientRequestStream = LetvStreamCommonConstants.CODE_NAME_DOLBY_720p;
        }
        //model播放模式,0--TV版播放，1--儿童播放，2--投屏播放
        if (model != null && VideoConstants.PLAY_MODEL_TOUPING == model) {
            this.playVideoOfTouping(response, videoId, imei, routerId, model, support3d, clientRequestStream,
                    commonParam, isThirdParty, support4k);
        } else {
            VideoDto videoPlay = new VideoDto();
            Long userId = StringUtil.toLong(commonParam.getUserId(), 0L);
            if (userId == null || userId <= 0) {
                // 非投屏播放才校验用户登录信息。客户端未传userId时兼容，老版本问题
                UserStatus user = userService.getUserStatus(commonParam);
                if (user != null) {
                    userId = user.getUserId();
                    commonParam.setUserId(String.valueOf(userId));
                }
            }
            // 对一些特殊的客户端做地区播放限制放行
            String clientIp = this.unlimitedClientId(commonParam) ? null : commonParam.getClientIp();
            // 签名校验
            boolean doSign = true;
            if (null != commonParam.getDebug() && commonParam.getDebug() > 0) {
                videoPlay.setUrlSign(this.genSignature(model, videoId, albumId, timestamp, signature, commonParam));
                doSign = false;
            }
            if (doSign && !this.checkSignature(model, videoId, albumId, timestamp, signature, commonParam)) {
                errorCode = VideoConstants.VIDEO_SIG_ERROR;
                log.info(logPrefix + "[errorCode=" + errorCode + "]: check Signature failed.]");
            } else {
                // 针对乐视儿童做专辑播放逻辑；投屏播放不再考虑专辑信息 && ［播放记录］下一集循环连播操作
                if (videoId == null /*
                                     * && VideoConstants.PLAY_MODEL_CHILD ==
                                     * model
                                     */&& albumId != null) {
                    albumMysqlTable = albumVideoAccess.getAlbum(albumId, commonParam);
                    if (albumMysqlTable != null) {
                        videoId = albumMysqlTable.getVideoId();
                        if (/* VideoConstants.PLAY_MODEL_CHILD != model && */
                        albumMysqlTable.getAlbum_attr() != null && albumMysqlTable.getAlbum_attr() == 1) {
                            List<VideoMysqlTable> iptvVideoList = albumVideoAccess
                                    .getVideoRange(albumMysqlTable.getId(), VideoTpConstant.QUERY_TYPE_POSITIVE, null,
                                            0, 1, commonParam, 5);
                            // FIXME: 修正儿童只传pid时选推送到儿童的第一集
                            for (VideoMysqlTable video : iptvVideoList) {
                                if (model == VideoConstants.PLAY_MODEL_CHILD) {
                                    if (video.getIsPushChild().intValue() == 1) {
                                        videoId = video.getId();
                                        break;
                                    }
                                } else {
                                    videoId = video.getId();
                                    break;
                                }
                            }
                        }
                    }
                }
                videoPlay.setVideoId(String.valueOf(videoId));

                // 获得视频、专辑
                videoMysqlTable = albumVideoAccess.getVideo(videoId, commonParam);
                boolean isSpecial = false;// 特殊处理（太子妃不弹异地登陆，可以免费观看）
                boolean login = false;
                if (TerminalUtil.isLetvUs(commonParam)) {
                    if (videoMysqlTable != null && "10015173".equals(String.valueOf(videoMysqlTable.getPid()))) {
                        isSpecial = true;
                        if (StringUtil.isNotBlank(commonParam.getUsername())) {
                            login = true;
                        }
                    } else {
                        isSpecial = false;
                        // check multiple login
                        login = userService.checkLogin(commonParam);
                    }
                    if (!login) {// mock user for us application
                        login = this.tryMockNotVipAccountLogin(commonParam);
                    }
                } else {
                    // 用户登录验证
                    login = userService.checkLogin(commonParam);
                }

                if (videoMysqlTable == null) {// 如果该视频没有TV版权或者未推到TV版
                    errorCode = this.videoOffLineErrCode(model, albumId, commonParam);
                    if (model == null || VideoConstants.PLAY_MODEL_CHILD != model) {
                        // 通用版提示信息不同
                        if (ProductLineConstants.LETV_COMMON.equals(commonParam.getTerminalApplication())) {
                            tips = VideoConstants.VIDEO_ALBUM_DETAIL_ERROR_TIPS_COMMON;
                        } else {
                            tips = VideoConstants.VIDEO_PLAY_ERROR_TIPS_CN;
                        }
                    }
                    log.info(logPrefix + "[errorCode=" + errorCode + "]: video null.]");
                } else if (StringUtils.isEmpty(videoMysqlTable.getPlay_streams())) {
                    errorCode = VideoConstants.VIDEO_STREAM_NOT_FOUND; // 无可播放码流(无播放码流找到视频显示未找到视频)
                    log.info(logPrefix + "[errorCode=" + errorCode + "]: no play stream.]");
                } else {
                    // 提前设置分类已方便码率过滤
                    videoPlay.setCategoryId(videoMysqlTable.getCategory());

                    boolean support360Stream = terminalService
                            .isTerminalSeriesSupport360Stream(commonParam);
                    // 获得播放码流，包括降码流处理和设置降码流提示
                    String playStream = this.getPlayStream(clientRequestStream, videoMysqlTable.getPlay_streams(),
                            videoPlay, model, support3d, supportStream, support360Stream, commonParam, support4k);

                    // 2016-05-30，全景码流兼容逻辑，当使用普通码流播放无普通码流的视频时（如配置在运营位且未指定起播全景码流），服务端兼容播放
                    if (StringUtils.isNotEmpty(clientRequestStream) && StringUtils.isEmpty(playStream)
                            && StringUtils.contains(videoMysqlTable.getPlay_streams(), "_360") && support360Stream) {
                        playStream = this.getAvaliable360Stream(videoMysqlTable.getPlay_streams());
                        videoPlay.setCurrentStream(playStream);
                        Object[] codeMap = {
                                LetvStreamCommonConstants.nameOf(clientRequestStream, commonParam.getLangcode()),
                                LetvStreamCommonConstants.nameOf(playStream, commonParam.getLangcode()) };
                        String tipMsg = MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_REDUCESTREAM,
                                commonParam.getLangcode(), codeMap);
                        videoPlay.setTipMsg(tipMsg);

                        playStream = this.getPlayStream(playStream, videoMysqlTable.getPlay_streams(), videoPlay,
                                model, support3d, supportStream, support360Stream, commonParam, support4k);

                        // 注意，这一步放后面，是因为默认选码流已经是一种降码流操作，而其下一步的getPlayStream操作有可能不再进行降码流而会将hasBelow设置为0
                        videoPlay.setHasBelow(VideoConstants.HAS_BELOW_YES);
                    }

                    boolean isMarlin = VideoUtil.isMarlin(videoMysqlTable.getDrmFlagId(), wcode);
                    // 获得媒资视频文件播放信息
                    MmsStore mmsStore = null;
                    // ===========20170702 强制降码流(B)============//
                    boolean force2ReduceStream = true;
                    if (force2ReduceStream && StringUtils.isNotEmpty(clientRequestStream)
                            && clientRequestStream.indexOf("4k") != -1 && !support4k
                    /*
                     * && clientRequestStream.indexOf("3d") == -1
                     * && clientRequestStream.indexOf("db") == -1
                     * && ((clientRequestStream.indexOf("4k") == -1)
                     * || (clientRequestStream.indexOf("4k") != -1 &&
                     * !support4k))
                     */) {
                        String defaultStream = LetvStreamCommonConstants.DEFAULT_STREAM;
                        if (clientRequestStream.indexOf("4k") != -1 && !support4k) {
                            defaultStream = LetvStreamCommonConstants.LETV_COMMON_USER_SETTING_PLAY_STREAM;
                        }
                        String playStreamTmp = this.reduceVideoPlayStream(clientRequestStream, defaultStream);
                        mmsStore = this.getMmsFilePlayInfo(videoId, videoMysqlTable.getPid(),
                                videoMysqlTable.getPlay_streams(), videoMysqlTable.getMid_streams(), playStreamTmp,
                                clientIp, isMarlin, model, null, videoPlay, commonParam);
                        if (null == mmsStore || null == mmsStore.getData() || mmsStore.getData().size() == 0) {
                            mmsStore = this.getMmsFilePlayInfo(videoId, videoMysqlTable.getPid(),
                                    videoMysqlTable.getPlay_streams(), videoMysqlTable.getMid_streams(), playStream,
                                    clientIp, isMarlin, model, null, videoPlay, commonParam);
                        }
                    } else {
                        mmsStore = this.getMmsFilePlayInfo(videoId, videoMysqlTable.getPid(),
                                videoMysqlTable.getPlay_streams(), videoMysqlTable.getMid_streams(), playStream,
                                clientIp, isMarlin, model, null, videoPlay, commonParam);
                    }
                    // ===========20170702 强制降码流(E)============//

                    int validateMmsStore = VideoUtil.validateMmsStore(mmsStore);

                    if (albumMysqlTable == null) {
                        albumMysqlTable = albumVideoAccess.getAlbum(videoMysqlTable.getPid(),
                                commonParam);
                    }
                    if (TerminalUtil.isLetvUs(commonParam) && (albumMysqlTable != null) && albumMysqlTable.isTVPay()) {
                        albumMysqlTable.setPay_platform(null);
                    }
                    // 从VRS获得视频的基本信息
                    this.getVideoPlayFromVRS(videoMysqlTable, albumMysqlTable, videoPlay, playStream, model,
                            commonParam);

                    // mmsStore获取失败时，playType值也要设置
                    if (VideoUtil.validateMmsStore.SUCCESS == validateMmsStore) {
                        // 将此步提前，减少一次解析
                        MmsFile mmsFile = VideoUtil.getMmsFileByVTypeOrder(playStream, mmsStore);
                        String storePath = (mmsFile != null ? mmsFile.getStorePath() : null);

                        Integer chargeType = this.chargePolicy(albumMysqlTable, videoMysqlTable, playStream,
                                commonParam.getTerminalApplication(), appCode, model, commonParam); // 获取收费策略

                        PlayAuthV2 userPlayAuth = new PlayAuthV2(PlayAuthV2.STATUS_UNPASS); // 默认鉴权不过
                        if (model == VideoConstants.PLAY_MODEL_CHILD
                                && chargeType == ChargeTypeConstants.chargePolicy.FREE) {
                            // 儿童免费视频直接播放，没有降码流逻辑，不同于非儿童模式
                            userPlayAuth.setStatus(PlayAuthV2.STATUS_PASS);
                        }

                        // TVOD标识: chargeType = 6
                        if (null != videoMysqlTable) {
                            Integer chargeTypeTmp = JumpUtil.getChargeType(videoMysqlTable.getPay_platform(),
                                    commonParam);
                            if (null == chargeTypeTmp) { // for old version
                                chargeTypeTmp = JumpUtil.getChargeType(videoMysqlTable.getPay_platform());
                            }
                            videoPlay.setChargeType(chargeTypeTmp);
                        }

                        // 正常TV点播或乐视儿童播放，需要走正常鉴权逻辑
                        if (TerminalUtil.isLetvUs(commonParam)) {
                            if (isSpecial && login) {
                                userPlayAuth.setStatus(PlayAuthV2.STATUS_PASS);
                            } else {
                                userPlayAuth = this.getPlayAuthInfoV2(login, chargeType,
                                        StringUtil.toString(videoMysqlTable.getPid()), storePath, validDate,
                                        commonParam, -1, videoPlay);
                                if (userPlayAuth != null && userPlayAuth.getVodInfo() != null) {
                                    String price = null;
                                    /*
                                     * 设置单点价格
                                     */
                                    if (userPlayAuth.getVodInfo().getChargeTerminalPrice() != null) {
                                        userPlayAuth.getVodInfo().getChargeTerminalPrice();
                                        if (MmsDataUtil.existPayPlatform(this.sessionCache)) {
                                            price = userPlayAuth.getVodInfo().getChargeTerminalPrice()
                                                    .get(MmsDataUtil.getPayPlatform(this.sessionCache)); // 从map中获取tv端的价格
                                        } else {
                                            price = userPlayAuth.getVodInfo().getChargeTerminalPrice()
                                                    .get(String.valueOf(VipConstants.BossTerminalType.TV.getCode())); // 从map中获取tv端的价格
                                        }

                                        if (StringUtil.isNotBlank(price)) {
                                            videoPlay.setPrice(price);
                                        }
                                    }
                                    /*
                                     * 设置单点有效天数
                                     */
                                    if (userPlayAuth.getVodInfo().getCharge() != null) {
                                        Integer validTime = userPlayAuth.getVodInfo().getCharge().getValidTime();
                                        if (validTime != null) {
                                            videoPlay.setExpiredTime(validTime * 24); // 转换成小时
                                        }
                                    }
                                }
                            }
                        } else {
                            if (login) {// 登录情况
                                chargeType = this.setUserPlayInfo4LoginV2(videoPlay, videoMysqlTable, storePath,
                                        userPlayAuth, chargeType, login, userId, validDate, model, commonParam,
                                        logPrefix);
                            } else {// 未登录情况
                                chargeType = this.setUserPlayInfo4UnLogin(videoPlay, albumMysqlTable, videoMysqlTable,
                                        chargeType, model, wcode, logPrefix, commonParam, storePath, validDate);
                            }
                        }

                        if (errorCode == null) {
                            // 2015-11-24将此步骤提前，设置播放码流列表--上一步getPlayTypeByUserAuth方法中重新设置了chargeType
                            this.setVideoPlayStreamList(clientRequestStream, videoMysqlTable, response,
                                    commonParam.getTerminalSeries(), videoPlay, chargeType, model, commonParam,
                                    isThirdParty);
                            /*
                             * 设置专辑视频付费类型(playType)
                             * 设置规则:
                             * 1:根据媒资专辑视频付费规则设置chargeType
                             * 2:登录用户boss鉴权,根据鉴权结果修改chargeType;未登录用户获取单片价格判断
                             * 3:根据chargeType设置付费类型
                             */
                            this.getPlayTypeByUserAuth(albumMysqlTable, videoMysqlTable, userPlayAuth, videoPlay,
                                    chargeType, commonParam, isThirdParty); // 设置playType和tipMsg
                            // 从媒资视频文件解析播放信息
                            boolean needTips = this.setVideoPlayFromMmsFile(mmsStore, mmsFile, videoPlay, playStream,
                                    userPlayAuth, isMarlin, audioId, channelId, langType, kbpsType, commonParam);
                            if (needTips && operType != null && operType == 1) {
                                errorCode = VideoConstants.VIDEO_PLAY_SWITCH_AUDIO_TRACK_FAIL;
                                log.info(logPrefix + "[errorCode=" + errorCode + "]:switch audio track failured.");
                            }
                            // 设置播放结束是否需要获取推荐短视频数据
                            if (TerminalUtil.isLetvUs(commonParam)) {
                                // 美国行货没有联播推荐
                                videoPlay.setNeedRecommendForEnd(0);
                            } else {
                                videoPlay.setNeedRecommendForEnd(this.setNeedRecommend4EndValue(albumMysqlTable,
                                        videoMysqlTable));
                            }
                            // 播放视频附加服务
                            this.handleAdditionalService(videoPlay, videoMysqlTable, commonParam);

                            // 处理第三方服务
                            this.handleTPService(videoPlay, videoId, videoMysqlTable.getName_cn());

                            // 统计数据-上报
                            this.setStatisticData(videoMysqlTable.getCategory_all(), videoPlay, videoPlay.getVtype());

                            if (TerminalUtil.isLetvUs(commonParam)) {
                                // us version logic
                                videoPlay.setPauseImg(this.getPauseImg4Us());
                            }
                            videoPlay.setPauseDefaultImg(VideoConstants.VIDEO_PLAY_PAUSE_DEFAULT_IMG);

                            if (TerminalUtil.isLetvUs(commonParam)) {
                                // 美国暂时不出广告-start
                                videoPlay.setPlayFloatAd(0);
                                videoPlay.setShowPauseAdPage(0);
                                // 美国暂时不出广告-end
                            } else {
                                // 设置是否展示暂停广告页面
                                videoPlay.setShowPauseAdPage(this.getShowPauseAdPageConfig());
                                // 设置是否展示浮层广告
                                videoPlay.setPlayFloatAd(this.getPlayFloatAdConfig());
                            }

                            videoPlay.setIsDanmaku(videoMysqlTable.getIsDanmaku());

                            // 如果是单点且有权限播放，则设置播放地址有效期
                            if (/*
                                 * videoPlay.getPayType() != null && 1 ==
                                 * videoPlay.getPayType()
                                 * &&
                                 */
                            (StringUtil.isNotBlank(videoPlay.getIfCharge()) && "0".equals(videoPlay.getIfCharge()))
                                    || (videoPlay.getPlayType() != null && 1 == videoPlay.getPlayType())) {
                                videoPlay.setTokenExpireTime(this.getVideoPlayTokenExpireTime());
                            }

                            if (StringUtils.isNotEmpty(videoPlay.getCurrentStream())
                                    && videoPlay.getCurrentStream().endsWith("_360")) {
                                // 全景码流特殊逻辑，全部正常播放，享受会员权益，不展示广告
                                videoPlay.setPlayType(1);
                                videoPlay.setVip(1);
                                videoPlay.setPlayAd(0);
                                videoPlay.setShowPauseAdPage(0);
                                videoPlay.setPlayFloatAd(0);
                            }
                        }
                    } else if (VideoUtil.validateMmsStore.VIDEO_CN_PLAY_FORBIDDEN == validateMmsStore) {
                        errorCode = VideoConstants.VIDEO_CN_IP_FORBIDDEN;
                        tips = VideoConstants.VIDEO_PLAY_ERROR_TIPS_CN;
                        log.info(logPrefix + "[errorCode=" + errorCode + "]: play forbidden in cn.]");
                    } else if (VideoUtil.validateMmsStore.VIDEO_HK_PLAY_FORBIDDEN == validateMmsStore) {
                        errorCode = VideoConstants.VIDEO_HK_IP_FORBIDDEN;
                        tips = VideoConstants.VIDEO_PLAY_ERROR_TIPS_HK;
                        log.info(logPrefix + "[errorCode=" + errorCode + "]: play forbidden in hk.]");
                    } else if (VideoUtil.validateMmsStore.VIDEO_NOT_CN_NOT_HK_PLAY_FORBIDDEN == validateMmsStore) {
                        errorCode = VideoConstants.VIDEO_NOT_CN_NOT_HK_IP_FORBIDDEN;
                        tips = VideoConstants.VIDEO_PLAY_ERROR_TIPS_OVERSEAS;
                        log.info(logPrefix + "[errorCode=" + errorCode + "]: play forbidden not in cn not in hk.]");
                    } else if (VideoUtil.validateMmsStore.VIDEO_UNKNOW_PLAY_FORBIDDEN == validateMmsStore) {
                        errorCode = VideoConstants.VIDEO_UNKNOW_IP_FORBIDDEN;
                        tips = VideoConstants.VIDEO_PLAY_ERROR_TIPS_OTHER;
                        log.info(logPrefix + "[errorCode=" + errorCode + "]: play forbidden unknow area.]");
                    } else if (StringUtils.isEmpty(playStream) && !support360Stream) {
                        errorCode = VideoConstants.VIDEO_GET_PLAY_STRAM_FAIL;
                        tips = VideoConstants.VIDEO_TIPMSG_UNSUPPORT_360_STREAM;
                    } else {
                        errorCode = VideoConstants.VIDEO_GET_MMS_INFO_ERROR;
                        tips = VideoConstants.VIDEO_PLAY_ERROR_TIPS_CN;
                        log.error(logPrefix + "[errorCode=" + errorCode + "]: play error.防盗链拿视频信息错误："
                                + validateMmsStore + "]");
                    }
                }
            }
            if (errorCode == null) {
                /*
                 * 设置连续播放时显示的预告片,要求最高码流,因此clientRequestStream为null
                 */
                /*
                 * if (trailer) {
                 * setTrailerVideoDto(videoPlay, null, supportStream, model,
                 * support3d, commonParam, support4k);
                 * }
                 */

                // 特殊处理输出数据
                VideoUtil.rebuildVideoDto(albumMysqlTable, videoMysqlTable, commonParam, videoPlay, this.facadeCacheDao
                        .getVipCacheDao().getVipInfoV3(commonParam.getUserId()));
                // ［异步执行］防盗链行为上报
                this.asyncSetAntiReport(model, commonParam, logPrefix);
                response.setData(videoPlay);
            } else {
                response.setResultStatus(BaseResponse.STATUS_ERROR);
                response.setErrCode(errorCode);
                String errorMsg = MessageUtils.getMessage(errorCode, langcode);
                if (!StringUtil.isBlank(tips)) {// 需要提示
                    String tipsMsg = MessageUtils.getMessage(tips, langcode);
                    if (StringUtil.isBlank(tipsMsg)) {
                        response.setErrMsg(errorMsg);
                    } else if (StringUtil.isBlank(errorMsg)) {
                        response.setErrMsg(StringUtils.trimToEmpty(tipsMsg));
                    } else {// 成功获取提示文案
                        response.setErrMsg(errorMsg + ";" + StringUtils.trimToEmpty(tipsMsg));
                    }
                } else {
                    response.setErrMsg(errorMsg);
                }
            }
        }

        return response;
    }

    public Response<VideoDto> getVideoPlayInfo(Long videoId, Long albumId, String clientRequestStream, Long timestamp,
            String signature, String userName, String loginTime, Integer pricePackageType, Boolean isFromCntv,
            Boolean isFromCibn, Boolean support3d, Integer model, String appCode, String validDate, String audioId,
            Integer channelId, String langType, String kbpsType, String imei, String routerId, Integer operType,
            String supportStream, Boolean trailer, CommonParam commonParam, Boolean support4k) {
        return this.getVideoPlayInfo(videoId, albumId, clientRequestStream, timestamp, signature, userName, loginTime,
                pricePackageType, isFromCntv, isFromCibn, support3d, model, appCode, validDate, audioId, channelId,
                langType, kbpsType, imei, routerId, operType, supportStream, trailer, commonParam, false, support4k);
    }

    public Response<VideoDto> getVideoPlayInfo4LecomUS(Long videoId, Long albumId, String clientRequestStream,
            Long timestamp, String signature, String userName, String loginTime, Integer pricePackageType,
            Boolean isFromCntv, Boolean isFromCibn, Boolean support3d, Integer model, String appCode, String validDate,
            String audioId, Integer channelId, String langType, String kbpsType, String imei, String routerId,
            Integer operType, String supportStream, CommonParam commonParam) {
        return this.getVideoPlayInfo4LecomUS(videoId, albumId, clientRequestStream, timestamp, signature, userName,
                loginTime, pricePackageType, isFromCntv, isFromCibn, support3d, model, appCode, validDate, audioId,
                channelId, langType, kbpsType, imei, routerId, operType, supportStream, commonParam, false);
    }

    public Response<VideoDto> getVideoPlayInfo4LecomUS(Long videoId, Long albumId, String clientRequestStream,
            Long timestamp, String signature, String userName, String loginTime, Integer pricePackageType,
            Boolean isFromCntv, Boolean isFromCibn, Boolean support3d, Integer model, String appCode, String validDate,
            String audioId, Integer channelId, String langType, String kbpsType, String imei, String routerId,
            Integer operType, String supportStream, CommonParam commonParam, boolean isThirdParty) {
        Response<VideoDto> response = new Response<VideoDto>();
        String errorCode = null;
        String tips = null;

        String logPrefix = "getVideoPlayInfo_" + commonParam.getUsername() + "_" + commonParam.getUserId() + "_"
                + videoId + "_" + clientRequestStream + "_" + commonParam.getMac();

        // 参考频道下默认码流数据返回给客户端的码流，被直接用来播放，这里需要容错
        if ("3d".equals(clientRequestStream)) {
            clientRequestStream = LetvStreamCommonConstants.CODE_NAME_3d720p;
        } else if ("db".equals(clientRequestStream)) {
            clientRequestStream = LetvStreamCommonConstants.CODE_NAME_DOLBY_720p;
        }

        if (model != null && VideoConstants.PLAY_MODEL_TOUPING == model) {
            this.playVideoOfTouping(response, videoId, imei, routerId, model, support3d, clientRequestStream,
                    commonParam, isThirdParty, true);
        } else {
            VideoDto videoPlay = new VideoDto();
            // 对一些特殊的客户端做地区播放限制放行
            String clientIp = this.unlimitedClientId(commonParam) ? VideoConstants.PLAY_WHITE_LIST_DEFAULT_CLIENT_IP
                    : commonParam.getClientIp();
            // 签名校验
            if (!this.checkSignature(model, videoId, albumId, timestamp, signature, commonParam)) {
                errorCode = VideoConstants.VIDEO_SIG_ERROR;
                log.info(logPrefix + "[errorCode=" + errorCode + "]: check Signature failed.]");
            } else {
                // 针对乐视儿童做专辑播放逻辑；投屏播放不再考虑专辑信息
                AlbumMysqlTable albumMysqlTable = null;
                if (videoId == null && VideoConstants.PLAY_MODEL_CHILD == model && albumId != null) {
                    albumMysqlTable = albumVideoAccess.getAlbum(albumId, commonParam);
                    if (albumMysqlTable != null) {
                        videoId = albumMysqlTable.getVideoId();
                    }
                }
                videoPlay.setVideoId(String.valueOf(videoId));

                // 获得视频、专辑
                VideoMysqlTable videoMysqlTable = albumVideoAccess.getVideo(videoId,
                        commonParam);
                // 用户登录验证
                boolean login = userService.checkLogin(commonParam);

                if (videoMysqlTable == null) {// 如果该视频没有TV版权或者未推到TV版
                    errorCode = this.videoOffLineErrCode(model, albumId, commonParam);
                    if (model == null || VideoConstants.PLAY_MODEL_CHILD != model) {
                        tips = VideoConstants.VIDEO_PLAY_ERROR_TIPS_CN;
                    }
                    log.info(logPrefix + "[errorCode=" + errorCode + "]: video null.]");
                } else if (StringUtils.isEmpty(videoMysqlTable.getPlay_streams())) {
                    errorCode = VideoConstants.VIDEO_STREAM_NOT_FOUND; // 无可播放码流
                    log.info(logPrefix + "[errorCode=" + errorCode + "]: no play stream.]");
                } else {

                    // 视频有分级信息时，先校验分级是否可播
                    UserChildLockTable childLockTable = userService.getUserChildLockFromCache(
                            commonParam);
                    if (childLockTable != null) {
                        ChildLockDto childLockDto = userService.parseUserChildLockTable(
                                UserConstants.USER_CHILD_LOCK_CHECK_ACTION_TYPE_CHECK_STATUS, childLockTable);
                        if (videoMysqlTable.getContentRatingId() != null
                                && childLockDto != null
                                && UserConstants.USER_CHILD_LOCK_STATUS_ON == childLockDto.getStatus()
                                && childLockDto.getCanPlayCRIds() != null
                                && !childLockDto.getCanPlayCRIds().contains(
                                        String.valueOf(videoMysqlTable.getContentRatingId()))) {
                            // 视频有分级、用户设置了儿童锁，儿童锁启用、儿童锁允许播放级别不包含当前视频级别，则禁播
                            errorCode = VideoConstants.LECOM_VIDEO_PLAY_CHILD_LOCK_FORBIDDED;
                        } else {
                            videoPlay.setChildLock(childLockDto);
                        }
                    }

                    if (errorCode == null) {
                        boolean support360Stream = terminalService
                                .isTerminalSeriesSupport360Stream(commonParam);
                        // 获得播放码流，包括降码流处理和设置降码流提示
                        String playStream = this.getPlayStream(clientRequestStream, videoMysqlTable.getPlay_streams(),
                                videoPlay, model, support3d, supportStream, support360Stream, commonParam, true);

                        // 2016-05-30，全景码流兼容逻辑，当使用普通码流播放无普通码流的视频时（如配置在运营位且未指定起播全景码流），服务端兼容播放
                        if (StringUtils.isNotEmpty(clientRequestStream) && StringUtils.isEmpty(playStream)
                                && StringUtils.contains(videoMysqlTable.getPlay_streams(), "_360") && support360Stream) {
                            playStream = getAvaliable360Stream(videoMysqlTable.getPlay_streams());
                            videoPlay.setCurrentStream(playStream);
                            Object[] codeMap = {
                                    LetvStreamCommonConstants.nameOf(clientRequestStream, commonParam.getLangcode()),
                                    LetvStreamCommonConstants.nameOf(playStream, commonParam.getLangcode()) };
                            String tipMsg = MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_REDUCESTREAM,
                                    commonParam.getLangcode(), codeMap);
                            videoPlay.setTipMsg(tipMsg);

                            playStream = this.getPlayStream(playStream, videoMysqlTable.getPlay_streams(), videoPlay,
                                    model, support3d, supportStream, support360Stream, commonParam, true);

                            // 注意，这一步放后面，是因为默认选码流已经是一种降码流操作，而其下一步的getPlayStream操作有可能不再进行降码流而会将hasBelow设置为0
                            videoPlay.setHasBelow(VideoConstants.HAS_BELOW_YES);
                        }

                        // 获得媒资视频文件播放信息
                        MmsStore mmsStore = this.getMmsFilePlayInfo(videoId, videoMysqlTable.getPid(),
                                videoMysqlTable.getPlay_streams(), videoMysqlTable.getMid_streams(), playStream,
                                clientIp, false, model, null, videoPlay, commonParam);
                        int validateMmsStore = VideoUtil.validateMmsStore(mmsStore);

                        Integer chargeType = ChargeTypeConstants.chargePolicy.FREE;
                        if (albumMysqlTable == null) {
                            albumMysqlTable = albumVideoAccess.getAlbum(
                                    videoMysqlTable.getPid(), commonParam);
                        }
                        // 收费策略
                        chargeType = this.chargePolicy(albumMysqlTable, videoMysqlTable, playStream,
                                commonParam.getTerminalApplication(), appCode, model, commonParam);

                        // 从VRS获得视频的基本信息
                        this.getVideoPlayFromVRS4LecomUS(videoMysqlTable, albumMysqlTable, videoPlay, playStream,
                                model, commonParam);

                        // mmsStore获取失败时，playType值也要设置
                        if (VideoUtil.validateMmsStore.SUCCESS == validateMmsStore) {
                            // 将此步提前，减少一次解析
                            MmsFile mmsFile = VideoUtil.getMmsFileByVTypeOrder(playStream, mmsStore);

                            String storePath = null;
                            if (mmsFile != null) {
                                storePath = mmsFile.getStorePath();
                            }

                            // 默认点播
                            UserPlayAuth4LecomUS userPlayAuth = new UserPlayAuth4LecomUS(
                                    CommonConstants.PLAY_SERVICE_UNOPEN);
                            // 临时兼容处理，添加鉴权uid 王胜凯20161010
                            userPlayAuth.setTokenUserId(commonParam.getUserId());
                            // 如果付费则需要鉴权
                            if (ChargeTypeConstants.chargePolicy.CHARGE_BY_VIP == chargeType) {
                                chargeType = this.setUserPlayInfo4LecomUS(videoPlay, albumMysqlTable, storePath,
                                        userPlayAuth, playStream, chargeType, model, isFromCntv, isFromCibn,
                                        pricePackageType, logPrefix, commonParam);

                                if (ChargeTypeConstants.chargePolicy.CHARGE_BY_VIP == chargeType) {
                                    // 如果鉴权结果仍是该视频有付费属性，则设置
                                    videoPlay.setIfCharge(ChargeTypeConstants.CHARGE);
                                }
                            }

                            if (errorCode == null) {
                                if (chargeType == ChargeTypeConstants.chargePolicy.CHARGE_NO_COPYRIGTH) {
                                    errorCode = this.videoOffLineErrCode(model, albumId, commonParam);
                                    log.info(logPrefix + "[errorCode=" + errorCode + "]: can't get charge config.");
                                } else {
                                    // 2015-11-24将此步骤提前，设置播放码流列表--上一步getPlayTypeByUserAuth方法中重新设置了chargeType
                                    this.setVideoPlayStreamList(clientRequestStream, videoMysqlTable, response,
                                            commonParam.getTerminalSeries(), videoPlay, chargeType, model, commonParam,
                                            isThirdParty);

                                    // 设置playType和tipMsg
                                    this.getPlayTypeByUserAuth4LecomUS(albumMysqlTable, videoMysqlTable, userPlayAuth,
                                            videoPlay, login, chargeType, commonParam);
                                    // 从媒资视频文件解析播放信息
                                    boolean needTips = setVideoPlayFromMmsFile(mmsStore, mmsFile, videoPlay,
                                            videoMysqlTable.getPid(), videoMysqlTable.getPlay_streams(), playStream,
                                            login, userPlayAuth, false, audioId, channelId, langType, kbpsType,
                                            commonParam);
                                    if (needTips && operType != null && operType == 1) {
                                        errorCode = VideoConstants.VIDEO_PLAY_SWITCH_AUDIO_TRACK_FAIL;
                                        log.info(logPrefix + "[errorCode=" + errorCode
                                                + "]:switch audio track failured.");
                                    }

                                    // 播放视频附加服务
                                    // this.handleAdditionalService(videoPlay,
                                    // videoMysqlTable, commonParam);

                                    videoPlay.setCoopPlatform(videoMysqlTable.getCoopPlatform());

                                    // 美国lecom不展示降码或切音轨提示
                                    videoPlay.setTipMsg(null);

                                    // lecom需求，如果是付费且有权限播放，则设置播放地址有效期
                                    if (ChargeTypeConstants.CHARGE.equalsIgnoreCase(videoPlay.getIfCharge())
                                            && videoPlay.getPlayType() != null && 1 == videoPlay.getPlayType()) {
                                        videoPlay.setTokenExpireTime(this.getVideoPlayTokenExpireTime());
                                    }

                                    if (StringUtils.isNotEmpty(videoPlay.getCurrentStream())
                                            && videoPlay.getCurrentStream().endsWith("_360")) {
                                        // 全景码流特殊逻辑，全部正常播放，享受会员权益，不展示广告
                                        videoPlay.setPlayType(1);
                                        videoPlay.setVip(1);
                                        videoPlay.setPlayAd(0);
                                        videoPlay.setShowPauseAdPage(0);
                                        videoPlay.setPlayFloatAd(0);
                                    }
                                }
                            }
                        } else if (VideoUtil.validateMmsStore.VIDEO_CN_PLAY_FORBIDDEN == validateMmsStore) {
                            errorCode = VideoConstants.VIDEO_CN_IP_FORBIDDEN;
                            tips = VideoConstants.VIDEO_PLAY_ERROR_TIPS_CN;
                            log.info(logPrefix + "[errorCode=" + errorCode + "]: play forbidden in cn.]");
                        } else if (VideoUtil.validateMmsStore.VIDEO_HK_PLAY_FORBIDDEN == validateMmsStore) {
                            errorCode = VideoConstants.VIDEO_HK_IP_FORBIDDEN;
                            tips = VideoConstants.VIDEO_PLAY_ERROR_TIPS_HK;
                            log.info(logPrefix + "[errorCode=" + errorCode + "]: play forbidden in hk.]");
                        } else if (VideoUtil.validateMmsStore.VIDEO_NOT_CN_NOT_HK_PLAY_FORBIDDEN == validateMmsStore) {
                            errorCode = VideoConstants.VIDEO_NOT_CN_NOT_HK_IP_FORBIDDEN;
                            tips = VideoConstants.VIDEO_PLAY_ERROR_TIPS_OVERSEAS;
                            log.info(logPrefix + "[errorCode=" + errorCode + "]: play forbidden not in cn not in hk.]");
                        } else if (VideoUtil.validateMmsStore.VIDEO_UNKNOW_PLAY_FORBIDDEN == validateMmsStore) {
                            errorCode = VideoConstants.VIDEO_UNKNOW_IP_FORBIDDEN;
                            tips = VideoConstants.VIDEO_PLAY_ERROR_TIPS_OTHER;
                            log.info(logPrefix + "[errorCode=" + errorCode + "]: play forbidden unknow area.]");
                        } else if (StringUtils.isEmpty(playStream) && !support360Stream) {
                            errorCode = VideoConstants.VIDEO_GET_PLAY_STRAM_FAIL;
                            tips = VideoConstants.VIDEO_TIPMSG_UNSUPPORT_360_STREAM;
                        } else {
                            errorCode = VideoConstants.VIDEO_GET_MMS_INFO_ERROR;
                            tips = VideoConstants.VIDEO_PLAY_ERROR_TIPS_CN;
                            log.info(logPrefix + "[errorCode=" + errorCode + "]: play error.防盗链拿视频信息错误："
                                    + validateMmsStore + "]");
                        }
                    }
                }
            }
            if (errorCode == null) {
                response.setData(videoPlay);
            } else {
                response.setResultStatus(0);
                response.setErrCode(errorCode);
                String errorMsg = MessageUtils.getMessage(errorCode, commonParam.getLangcode());
                if (!StringUtil.isBlank(tips)) {// 需要提示
                    String tipsMsg = MessageUtils.getMessage(tips, commonParam.getLangcode());
                    if (StringUtil.isBlank(tipsMsg)) {
                        response.setErrMsg(errorMsg);
                    } else if (StringUtil.isBlank(errorMsg)) {
                        response.setErrMsg(StringUtils.trimToEmpty(tipsMsg));
                    } else {// 成功获取提示文案
                        response.setErrMsg(errorMsg + ";" + StringUtils.trimToEmpty(tipsMsg));
                    }
                } else {
                    response.setErrMsg(errorMsg);
                }
            }
        }

        return response;
    }

    private String videoOffLineErrCode(Integer model, Long albumId, CommonParam commonParam) {
        // TV版视频下线或者地域限制错误码
        String errorCode = VideoConstants.VIDEO_PLAY_NULL;
        // 乐视儿童视频专辑单视频下线错误码
        if (model != null && VideoConstants.PLAY_MODEL_CHILD == model) {
            if (albumId != null && albumId > 0) {
                AlbumMysqlTable albumMysqlTable = albumVideoAccess.getAlbum(albumId,
                        commonParam);
                if (albumMysqlTable != null) {
                    // 视频下线
                    errorCode = VideoConstants.VIDEO_PLAY_CHILD_VIDEO_OFFLINE;
                } else {
                    // 专辑下线
                    errorCode = VideoConstants.VIDEO_PLAY_CHILD_ALBUM_OFFLINE;
                }
            } else {
                // 单视频下线
                errorCode = VideoConstants.VIDEO_PLAY_CHILD_SINGLE_VIDEO_OFFLINE;
            }
        }
        return errorCode;
    }

    /**
     * @param isPay
     *            是否付费
     * @param clientRequestStream
     *            客户端请求播放的码流
     * @param timestamp
     *            用户登录或启动TV版时拿到的服务器时间戳
     * @param signature
     *            签名
     * @param userName
     *            用户名称
     * @param commonParam
     * @return
     */
    public Response<VideoDto> getVideoPlayInfo(String videoIdTmp, String albumIdTmp, String externalId, Long mid,
                                               String source, Integer isPay, String clientRequestStream, Long timestamp, String signature,
                                               String userName, Integer model, String audioId, Integer channelId, String langType, String kbpsType,
                                               CommonParam commonParam) {
        VideoDto videoPlay = new VideoDto();
        String errorCode = null;
        if (!this.checkSignature(videoIdTmp, externalId, timestamp, signature, commonParam)) { // 签名校验
            errorCode = VideoConstants.VIDEO_SIG_ERROR;
        } else {
            if (source != null && "youtube".equalsIgnoreCase(source)) {// youtube数据
                String content = this.facadeTpDao.getVideoTpDao().getYoutubeContent(externalId, commonParam);
                final String key = "dashmpd";
                String value = null;
                try {
                    int startPos = content.lastIndexOf(key);
                    int endPos = content.indexOf("&", startPos);
                    if (startPos >= 0 && startPos < endPos) {
                        String strTmp = content.substring(startPos, endPos);
                        value = strTmp.substring(strTmp.indexOf("http"));
                        // if (StringUtils.isNotBlank(strTmp)) {
                        // value = URLDecoder.decode(strTmp, "UTF-8");
                        // } else {
                        // errorCode =
                        // VideoConstants.VIDEO_GET_PLAY_STRAM_ADDRESS_FAIL;
                        // }
                    } else {
                        errorCode = VideoConstants.VIDEO_GET_PLAY_STRAM_ADDRESS_FAIL;
                    }
                } catch (Exception e) {
                    errorCode = VideoConstants.VIDEO_PLAY_ERROR;
                    log.error("getVideoPlayInfo_" + externalId + "get play url error,content:" + content);
                }
                log.info("getVideoPlayInfo_" + externalId + ":playUrl" + value);
                videoPlay.setPlayUrl(value);
            } else {// && source == "eros") {//eros数据
                if (!userService.checkIsLogin(commonParam)) { // 用户未登录
                    errorCode = ErrorCodeConstants.USER_NOT_LOGIN_4_INDIA;
                } else {
                    Integer vip = 0;
                    boolean isOldVersion = false;
                    if (StringUtil.isNotBlank(commonParam.getAppCode())
                            && StringUtil.toInteger(commonParam.getAppCode(), 0) < 400) {
                        isOldVersion = true;
                    }
                    BossTpResponse<List<SubscribeInfo>> bossTpResponse = this.facadeTpDao.getVipTpDao().getVipInfo(
                            BossTerminalType.TV.getCode(), commonParam);
                    if (bossTpResponse != null && bossTpResponse.getData() != null
                            && bossTpResponse.getData().size() > 0) {
                        for (SubscribeInfo subscribeInfo : bossTpResponse.getData()) {
                            if (VipTpConstant.VIP_INDIA.equals(subscribeInfo.getProductId())
                                    && System.currentTimeMillis() < subscribeInfo.getEndTime()) {
                                vip = 1;
                                break;
                            }
                        }
                    }
                    if (isOldVersion || vip == 1) { // TODO(liudaojie)
                        // 客户端强升后去掉对老版本的兼容
                        if (!userService.checkLogin(commonParam)) { // 用户异地登录验证
                            errorCode = ErrorCodeConstants.USER_NOT_LOGIN_4_INDIA;
                        } else {
                            Long videoId = null;
                            Long albumId = null;
                            if (StringUtil.isNotBlank(videoIdTmp)) {
                                if (videoIdTmp.contains("_")) { // 客户端传过来的有可能包含前缀，需要截取掉前缀
                                    videoIdTmp = videoIdTmp.substring(videoIdTmp.indexOf("_") + 1);
                                }
                                videoId = StringUtil.toLong(videoIdTmp);
                            }
                            if (StringUtil.isNotBlank(albumIdTmp)) { //
                                // 客户端传过来的有可能包含前缀，需要截取掉前缀
                                if (albumIdTmp.contains("_")) { // Intercept
                                    // the
                                    // real
                                    // albumId
                                    albumIdTmp = albumIdTmp.substring(albumIdTmp.indexOf("_") + 1);
                                }
                                albumId = StringUtil.toLong(albumIdTmp);
                            }
                            if (mid != null) { // 有mid
                                VideoMysqlTable videoMysqlTable = new VideoMysqlTable();
                                AlbumMysqlTable albumMysqlTable = new AlbumMysqlTable();
                                videoMysqlTable.setId(videoId);
                                videoMysqlTable.setPid(albumId);
                                albumMysqlTable.setId(albumId);
                                videoPlay.setIsThroughCDE(true);// CDE播放.

                                // 获得媒资视频文件播放信息
                                MmsStore mmsStore = this.getMmsFilePlayInfo4India(videoId, albumId, mid,
                                        clientRequestStream, false, userName, model, commonParam);
                                int validateMmsStore = VideoUtil.validateMmsStore(mmsStore);

                                // mmsStore获取失败时，playType值也要设置
                                if (VideoUtil.validateMmsStore.SUCCESS == validateMmsStore) {
                                    MmsFile mmsFile = VideoUtil.getMmsFileByVTypeOrder(clientRequestStream, mmsStore);

                                    // 从媒资视频文件解析播放信息
                                    this.setVideoPlayFromMmsFile(mmsStore, mmsFile, videoPlay, albumId,
                                            videoMysqlTable.getPlay_streams(), clientRequestStream, true, null, false,
                                            audioId, channelId, langType, kbpsType, commonParam);
                                } else if (VideoUtil.validateMmsStore.VIDEO_CN_PLAY_FORBIDDEN == validateMmsStore) {
                                    errorCode = VideoConstants.VIDEO_CN_IP_FORBIDDEN;
                                } else if (VideoUtil.validateMmsStore.VIDEO_HK_PLAY_FORBIDDEN == validateMmsStore) {
                                    errorCode = VideoConstants.VIDEO_HK_IP_FORBIDDEN;
                                } else if (VideoUtil.validateMmsStore.VIDEO_NOT_CN_NOT_HK_PLAY_FORBIDDEN == validateMmsStore) {
                                    errorCode = VideoConstants.VIDEO_NOT_CN_NOT_HK_IP_FORBIDDEN;
                                } else if (VideoUtil.validateMmsStore.VIDEO_UNKNOW_PLAY_FORBIDDEN == validateMmsStore) {
                                    errorCode = VideoConstants.VIDEO_UNKNOW_IP_FORBIDDEN;
                                } else {
                                    errorCode = VideoConstants.VIDEO_GET_MMS_INFO_ERROR;
                                }
                            } else { // 无mid
                                videoPlay.setIsThroughCDE(false);// 不通过CDE，而是通过流地址播放
                                String playUrl = this.getPlayStream(externalId, null, commonParam, 0);
                                if (StringUtil.isBlank(playUrl)) {
                                    errorCode = VideoConstants.VIDEO_GET_PLAY_STRAM_ADDRESS_FAIL;
                                }
                                if (errorCode == null) {
                                    videoPlay.setPlayUrl(playUrl);
                                }
                            }

                            videoPlay.setPlayType(1); // 设置为正常播放
                            videoPlay.setPositive(true); // 设置为正片
                        }
                    }
                    videoPlay.setVip(vip);
                }
            }
        }
        Response<VideoDto> response = new Response<VideoDto>();
        if (errorCode != null) {
            String message = MessageUtils.getMessage(errorCode, commonParam.getLangcode());
            response.setResultStatus(0);
            response.setErrCode(errorCode);
            response.setErrMsg(message);
            log.error("getVideoPlayInfo4India|" + videoIdTmp + "|" + albumIdTmp + "|" + commonParam.getUsername() + "_"
                    + commonParam.getMac() + "|video play error! errorCode: " + errorCode + ", errorMessage:" + message
                    + ".");
        } else {
            response.setResultStatus(1);
            response.setData(videoPlay);
        }
        return response;
    }

    /**
     * 获取播放流地址
     * @param videoId
     * @param commonParam
     * @param callCount
     *            递归调用的次数。传0或null
     * @return
     */
    private String getPlayStream(String videoId, ErosToken erosToken, CommonParam commonParam, Integer callCount) {
        callCount = (callCount == null ? 0 : callCount);
        if (callCount > 3) { // 这个接口会出现递归调用的情况。设置最多递归三次
            return null;
        }
        String playUrl = null;
        if (erosToken == null) { // eros-token为空，则再获取一次
            erosToken = userService.getErosToken(commonParam.getUserId(),
                    commonParam.getUserToken(), null, commonParam.getTerminalSeries()); // 先获取eros-token
        }
        if (erosToken != null) {
            Integer deviceType = null;
            if (UserConstants.hignStreamDeviceList.contains(commonParam.getTerminalSeries())) {
                deviceType = UserConstants.ErosConstant.DeviceType.HIGNSTREAM;
            } else {
                deviceType = UserConstants.ErosConstant.DeviceType.LOWSTREAM;
            }
            GetStreamTpResponse getStreamTpResponse = this.facadeTpDao.getVideoTpDao().getStream(videoId,
                    erosToken.getToken(), erosToken.getTokenSecret(), deviceType, commonParam);
            if (getStreamTpResponse != null && getStreamTpResponse.getData() != null) {
                for (ExtractData data : getStreamTpResponse.getData()) {
                    if (data != null && data.getInfo() != null) {
                        ResultCode resultCode = data.getInfo().getCode();
                        if (resultCode != null) {
                            if (resultCode.getValue() == ResultCode.OK.getValue()) { // 状态为ok
                                if (!CollectionUtils.isEmpty(data.getStream_list())) {
                                    playUrl = data.getStream_list().get(0); // 取流地址中第一个不为空的播放地址
                                }
                            } else if (resultCode.getValue() == ResultCode.USER_NOT_LOGGED_IN.getValue()) { // eros-token失效，则重新再取一次
                                ErosToken erosTokenLastest = userService.getErosTokenFromEros(
                                        commonParam.getUserId(), commonParam.getUserToken(), deviceType);
                                if (erosTokenLastest != null) {
                                    userService.updateErosToken(commonParam.getUserId(),
                                            deviceType, erosTokenLastest); // 更新eros-token
                                    return this.getPlayStream(videoId, erosTokenLastest, commonParam, callCount + 1); // 递归本方法，递归次数+1
                                }
                            }
                        }
                    }
                }
            }
        }
        return playUrl;
    }

    /**
     * 对一些特殊的客户端做地区播放限制放行；
     * 目前仅有部分地域支持白名单服务
     */
    public boolean unlimitedClientId(CommonParam commonParam) {
        boolean flag = Boolean.FALSE;
        if (StringUtils.isNotEmpty(commonParam.getMac())) {
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) {
                this.updateNoAreaPlayRestrictMacs(commonParam);
                if (VideoUtil.macWhiteListIsEmpty()) {
                    // 如果内存缓存为空，则先使用缓存数据判断，但不更新内存
                    Set<String> macWhiteList = this.facadeCacheDao.getChannelCacheDao().getMacWhiteList();
                    if (macWhiteList != null) {
                        flag = macWhiteList.contains(commonParam.getMac());
                    }
                } else {
                    flag = VideoUtil.isMacCanPlayNoArea(commonParam.getMac());
                }
            } else {
                this.updateNoAreaPlayRestrictMacs();
                flag = VideoUtil.isMacCanPlayNoArea(commonParam.getMac());
            }
        }
        return flag;
    }

    /**
     * 根据应用编号判断是否支持白名单服务；目前仅大陆版和香港版支持白名单
     * @param terminalApplication
     * @return
     */
    private boolean isSupportWhiteListService(String terminalApplication) {
        return TerminalCommonConstant.TERMINAL_APPLICATION_LETV.equalsIgnoreCase(terminalApplication)
                || TerminalCommonConstant.TERMINAL_APPLICATION_LETV_HK.equalsIgnoreCase(terminalApplication)
                || TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(terminalApplication);
    }

    private void setVideoPlayStreamList(String clientRequestStream, VideoMysqlTable videoMysqlTable,
            Response<VideoDto> res, String terminalSeries, VideoDto videoPlay, Integer chargeType, Integer model,
            CommonParam commonParam, boolean isThirdParty) {
        String videoStreams = videoMysqlTable.getPlay_streams();
        Map<String, Stream> preSetStream = null;
        if (model != null && VideoConstants.PLAY_MODEL_TOUPING == model) {
            // 投屏播放所有码流均免费
            preSetStream = VideoUtil.getPreSetStream(commonParam.getLangcode(), isThirdParty);
            this.setPlayStreamByStreamCode(clientRequestStream, videoStreams, preSetStream, videoPlay, model,
                    commonParam);
        } else {
            Integer payType = videoPlay.getPayType();
            String logPrerfix = "setVideoPlayStreamList_" + videoPlay.getVideoId() + "_" + chargeType + "_" + payType;
            // 预设码流配置信息
            if (chargeType == ChargeTypeConstants.chargePolicy.CHARGE_YUAN_XIAN
                    && !VideoCommonUtil.isCharge(videoMysqlTable.getPay_platform(),
                            videoMysqlTable.getPlay_platform(), commonParam.getP_devType())) {
                // 如果专辑收费但是视频免费，则走码流付费逻辑
                chargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM;
            }
            if (chargeType != null
                    && (chargeType == ChargeTypeConstants.chargePolicy.CHARGE_YUAN_XIAN || chargeType == ChargeTypeConstants.chargePolicy.CHARGE_BY_VIP)) {
                preSetStream = VideoUtil.getYuanXianPreSetStream(commonParam.getLangcode());
                log.info(logPrerfix + "_getYuanXianPreSetStream");
            } else {
                preSetStream = VideoUtil.getPreSetStream(commonParam.getLangcode(), isThirdParty);
                log.info(logPrerfix + "_getPreSetStream");
            }

            // if (TerminalUtil.isLetvUs(commonParam)) {
            // this.setPlayStreamByStreamCode4Us(videoPlay, clientRequestStream,
            // videoStreams, preSetStream, model,
            // commonParam);
            // } else {
            this.setPlayStreamByStreamCode(clientRequestStream, videoStreams, preSetStream, videoPlay, model,
                    commonParam);
            // }
        }
    }

    public String getPlayStream(String clientRequestStream, String videoStreams, VideoDto videoPlay, Integer model,
            Boolean support3d, String supportStream, boolean support360Stream, CommonParam commonParam,
            Boolean support4k) {
        // 码流等于1080p则处理成1080p6m
        String playStream = this.handleSpecialStream(clientRequestStream, videoStreams, commonParam);

        // playStream = this.getVideoPlayStream(playStream, videoStreams,
        // videoPlay, model, support3d, commonParam);
        if (TerminalUtil.isLetvUs(commonParam)) {
            support3d = null;// us version doesn't deal with 3d stream
            videoPlay.setHasBelow(VideoConstants.HAS_BELOW_NO);// 默认标识不降码流
            playStream = this.getVideoPlayStream4Us(videoPlay, playStream, model, videoStreams, support3d, commonParam);
        } else {
            playStream = this.getVideoPlayStreamNew(videoPlay, playStream, videoStreams, support3d, model,
                    supportStream, support360Stream, commonParam, support4k);
        }

        // 注意该行对方法外逻辑的影响
        videoPlay.setCurrentStream(playStream);

        return playStream;
    }

    /**
     * 判断用户是否是会员
     * @param validDate
     *            取客户端传来的会员有效期字段
     * @param userId
     *            用户id,为空返回非会员,有值则先验证缓存的会员信息,缓存没有则判断传来的根据validDate与当前日前进行比较
     * @return
     */
    public Boolean isVip(String validDate, Long userId, String deviceKey) {
        Boolean isVip = Boolean.FALSE;
        if (userId != null) {
            // 有效期
            Long seniorendtime = null;

            // 取缓存里有效期
            // Object cacheEndTime = RedisUtil.get(key);
            Long cacheEndTime = this.facadeCacheDao.getVipCacheDao().getUserVipSeniorEndTime(String.valueOf(userId),
                    deviceKey);
            if (cacheEndTime != null) {
                seniorendtime = cacheEndTime;
            } else {
                // 取客户端传来的有效期字段
                seniorendtime = TimeUtil.date2Timestamp(validDate);
            }

            if (seniorendtime != null && seniorendtime != 0) {
                // 判断是否过期
                Long currentTime = System.currentTimeMillis();
                if (currentTime < seniorendtime) {
                    // 会员
                    isVip = Boolean.TRUE;
                }
            }
        }

        return isVip;
    }

    /**
     * 签名校验
     * @param videoId
     * @param timestamp
     * @param signature
     * @return
     */
    public boolean checkSignature(Integer model, Object videoId, Object albumId, Long timestamp, String signature,
            CommonParam commonParam) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("videoid", videoId);
        if (model == 1) {
            params.put("albumid", albumId);
        }
        params.put("timestamp", timestamp);
        params.put("sig", signature);
        if (TerminalUtil.supportAntiReport(commonParam)) {
            params.put("appCode", commonParam.getAppCode());
            params.put("devSign", commonParam.getDevSign());
        }
        Boolean isValid = CommonUtil.checkSig(params);

        return isValid;
    }

    /**
     * 签名校验
     * @param videoId
     * @param timestamp
     * @param signature
     * @return
     */
    public boolean checkSignature(Object videoId, String externalId, Long timestamp, String signature,
            CommonParam commonParam) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (externalId != null) {
            params.put("externalId", externalId);
        }
        if (videoId != null) {
            params.put("videoid", videoId);
        }
        params.put("timestamp", timestamp);
        params.put("sig", signature);
        if (TerminalUtil.supportAntiReport(commonParam)) {
            params.put("appCode", commonParam.getAppCode());
            params.put("devSign", commonParam.getDevSign());
        }
        Boolean isValid = CommonUtil.checkSig(params);

        return isValid;
    }

    /**
     * 签名校验
     * @param videoId
     * @param timestamp
     * @param signature
     * @return
     */
    public String genSignature(Integer model, Object videoId, Object albumId, Long timestamp, String signature,
            CommonParam commonParam) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("videoid", videoId);
        if (model == 1) {
            params.put("albumid", albumId);
        }
        params.put("timestamp", timestamp);
        params.put("sig", signature);
        if (TerminalUtil.supportAntiReport(commonParam)) {
            params.put("appCode", commonParam.getAppCode());
            params.put("devSign", commonParam.getDevSign());
        }
        return CommonUtil.genSig(params);
    }

    /**
     * 处理码流
     * @param streams
     * @return
     *         public String handleStream(String clientRequestStream, String
     *         videoStreams, String locale, Response<VideoDto> res,
     *         String terminalSeries, VideoDto videoPlay) {
     *         // 获得预设码流配置信息
     *         Map<String, Stream> preSetStream =
     *         VideoUtil.getPreSetStream(locale);
     *         // String terminalSupportedVideoStreams =
     *         // this.getVideoStreamByTerminalSeries(videoStreams,
     *         terminalSeries);
     *         String playStream = this.getVideoPlayStream(clientRequestStream,
     *         videoStreams, videoPlay, terminalSeries,
     *         locale);
     *         this.setPlayStreamByStreamCode(videoStreams, preSetStream,
     *         videoPlay, null);
     *         playStream = this.handleSpecialStream(playStream);
     *         videoPlay.setCurrentStream(playStream);
     *         return playStream;
     *         }
     */

    /**
     * 根据设备型号过滤视频自身码流
     * @param terminalSeries
     * @return
     */
    private String getVideoStreamByTerminalSeries(String streams, String terminalSeries) {
        if (!TerminalTool.SUPPORT_4K_DEVICES.contains(terminalSeries)) {
            streams = streams.replace("4k", "");
        }
        return streams;
    }

    private String getVideoPlayStreamNew(VideoDto videoPlay, String clientRequestStream, String streams,
            Boolean support3d, Integer model, String supportStream, boolean support360Stream, CommonParam commonParam,
            Boolean support4k) {
        String playStream = "";
        String streamsTmp = StringUtils.trimToEmpty(streams);
        streams = this.handleVideoStream(clientRequestStream, streamsTmp, commonParam, videoPlay);
        boolean support3D = true;
        boolean support4K = true;
        boolean supportDB = true;
        boolean isTouPing = model != null && VideoConstants.PLAY_MODEL_TOUPING == model; // 是否是投屏，true-是，false-不是
        if (StringUtil.isNotBlank(supportStream)) {
            this.updateSupport4KDevice(commonParam);
            support3D = TerminalTool.support3D(supportStream, commonParam.getTerminalSeries());
            support4K = TerminalTool.support4K(supportStream, commonParam.getTerminalSeries())
            /* && this.isDeviceSupport4K(commonParam, support4k) */;
            supportDB = TerminalTool.supportDB(supportStream, commonParam.getTerminalSeries());
        } else {
            support3D = (support3d != null) && support3d;
            if (!TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) {
                // Lecom 码流支持情况直接全部由客户端传值，不再走服务单备案
                support4K = this.isDeviceSupport4K(commonParam, support4k);
            }
        }
        videoPlay.setHasBelow(VideoConstants.HAS_BELOW_NO);// 默认标识不降码流
        if (StringUtils.isEmpty(clientRequestStream)) {
            // 客户端未传入码流获得默认播放码流
            playStream = this.getDefaultVideoPlayStream(streams, LetvStreamCommonConstants.DEFAULT_STREAM);
        } else if (isTouPing
                && (LetvStreamCommonConstants.CODE_NAME_LETV_LEADING_2K.equalsIgnoreCase(clientRequestStream) || LetvStreamCommonConstants.CODE_NAME_LETV_LEADING_2K_H265
                        .equalsIgnoreCase(clientRequestStream))) {
            playStream = LetvStreamCommonConstants.CODE_NAME_1080p6m;
            videoPlay.setHasBelow(VideoConstants.HAS_BELOW_YES);
            Object[] codeMap = { LetvStreamCommonConstants.nameOf(clientRequestStream, commonParam.getLangcode()),
                    LetvStreamCommonConstants.nameOf(playStream, commonParam.getLangcode()) };
            String tipMsg = MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_REDUCESTREAM,
                    commonParam.getLangcode(), codeMap);
            videoPlay.setTipMsg(tipMsg);
        } else if (LetvStreamCommonConstants.CODE_NAME_1080p6m.equalsIgnoreCase(clientRequestStream)
                && streams.contains(LetvStreamCommonConstants.CODE_NAME_1080p3m)) {
            playStream = LetvStreamCommonConstants.CODE_NAME_1080p6m;
        } else {
            String reduceStreamSorts = null;
            String tipMsgCode = null;
            if (!ArrayUtils.contains(streams.split(","), clientRequestStream)) {
                tipMsgCode = VideoConstants.VIDEO_TIPMSG_REDUCESTREAM;
                if (isTouPing) {
                    if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                            .getTerminalApplication())) {
                        reduceStreamSorts = LetvStreamCommonConstants.USER_SETTING_PLAY_STREAM_LE;

                    } else {
                        reduceStreamSorts = LetvStreamCommonConstants.USER_SETTING_PLAY_STREAM;
                    }
                } else if (support360Stream) {
                    reduceStreamSorts = LetvStreamCommonConstants.PLAY_SORT_STREAM_T3;
                } else {
                    reduceStreamSorts = LetvStreamCommonConstants.PLAY_SORT_STREAM_T2;
                }
            } else {
                if (!support360Stream && clientRequestStream.endsWith("_360")) {
                    // 设备不支持全景码流但客户端请求了全景码流时需要降码流
                    streams = streams.replace("180_360", "").replace("350_360", "").replace("800_360", "")
                            .replace("1300_360", "").replace("720p_360", "").replace("1080p_360", "")
                            .replace("4k_360", "");
                    tipMsgCode = VideoConstants.VIDEO_TIPMSG_UNSUPPORT_4KSTREAM;
                    reduceStreamSorts = isTouPing ? LetvStreamCommonConstants.USER_SETTING_PLAY_STREAM
                            : LetvStreamCommonConstants.PLAY_SORT_STREAM_T2;
                }
                if (!support3D
                        && (LetvStreamCommonConstants.CODE_NAME_3d720p.equalsIgnoreCase(clientRequestStream)
                                || LetvStreamCommonConstants.CODE_NAME_3d1080p.equalsIgnoreCase(clientRequestStream) || LetvStreamCommonConstants.CODE_NAME_3d1080p6M
                                    .equalsIgnoreCase(clientRequestStream))) {
                    // 如果设备不支持3D，但是客户端又请求了3D码流，则需要将码流处理
                    if (isTouPing) {
                        // 投屏模式下，之前传过来的码流中，3d码流断层，现有降码流逻辑（getReducedStream）会有升码流（到4K）的问题，这里修改一下可用码流
                        streams = StringUtils.replace(
                                new String(LetvStreamCommonConstants.PLAY_SORT_STREAM_FOR_TOUPING), "#", ",");
                    }
                    streams = streams.replaceAll(LetvStreamCommonConstants.CODE_NAME_3d720p, "")
                            .replaceAll(LetvStreamCommonConstants.CODE_NAME_3d1080p, "")
                            .replaceAll(LetvStreamCommonConstants.CODE_NAME_3d1080p6M, "");

                    tipMsgCode = VideoConstants.VIDEO_TIPMSG_UNSUPPORT_4KSTREAM;
                    reduceStreamSorts = isTouPing ? LetvStreamCommonConstants.PLAY_SORT_STREAM_FOR_TOUPING
                            : LetvStreamCommonConstants.LETV_COMMON_USER_SETTING_PLAY_STREAM;
                } else if (streams.indexOf("4k") > -1
                        && ("4k".equalsIgnoreCase(clientRequestStream) || "4k_360"
                                .equalsIgnoreCase(clientRequestStream)) && !support4K) {
                    streams = streams.replace("4k_360", "").replace("4k", "");
                    tipMsgCode = VideoConstants.VIDEO_TIPMSG_UNSUPPORT_4KSTREAM;
                    reduceStreamSorts = isTouPing ? LetvStreamCommonConstants.USER_SETTING_PLAY_STREAM
                            : LetvStreamCommonConstants.PLAY_SORT_STREAM_T2;
                } else if (!supportDB && clientRequestStream.indexOf("db") > -1) {
                    tipMsgCode = VideoConstants.VIDEO_TIPMSG_UNSUPPORT_4KSTREAM;
                    reduceStreamSorts = isTouPing ? LetvStreamCommonConstants.PLAY_SORT_STREAM_FOR_TOUPING
                            : LetvStreamCommonConstants.LETV_COMMON_USER_SETTING_PLAY_STREAM;
                } else {
                    playStream = clientRequestStream;
                }
            }
            if (reduceStreamSorts != null) {// reduce stream
                playStream = this.getReducedStream(clientRequestStream, streams, reduceStreamSorts, support3D,
                        support4K);
                Object[] codeMap = { LetvStreamCommonConstants.nameOf(clientRequestStream, commonParam.getLangcode()),
                        LetvStreamCommonConstants.nameOf(playStream, commonParam.getLangcode()) };

                // 验证视频码流是否被处理，有处理不做提示
                if (streams.length() == streamsTmp.length()
                        || !ArrayUtils.contains(streamsTmp.split(","), clientRequestStream)) {
                    videoPlay.setHasBelow(VideoConstants.HAS_BELOW_YES);
                    String tipMsg = MessageUtils.getMessage(tipMsgCode, commonParam.getLangcode(), codeMap);
                    videoPlay.setTipMsg(tipMsg);
                }

            }
        }
        return playStream;
    }

    /**
     * 客户端若不传播放码流，获得视频的默认播放码流
     * @return
     */
    private String getDefaultVideoPlayStream(String videoStreams, String defaultStreams) {
        String defaultStream = "";
        String[] defaults = defaultStreams.split("#");
        for (String ds : defaults) {
            if (videoStreams.contains(ds)) {
                defaultStream = ds;
                break;
            }
        }
        return defaultStream;
    }

    private String reduceVideoPlayStream(String clientRequestStream, String defaultStreams) {
        String[] defaults = defaultStreams.split("#");
        String defaultStream = defaults[0];
        for (String ds : defaults) {
            if (clientRequestStream.contains(ds)) {
                defaultStream = ds;
                break;
            }
        }
        return defaultStream;
    }

    /**
     * 若客户端传入码流该视频没有，则降码流播放
     * @return
     */
    private String getReducedStream(String clientRequestStream, String videoStreams, String sortStreams,
            Boolean support3D) {
        if (support3D == null) {
            support3D = false;
        }
        String playStream = "";

        String[] sortStreamsArray = sortStreams.split("#");
        int clientStreamIndex = ArrayUtils.indexOf(sortStreamsArray, clientRequestStream);
        List<String> list = Arrays.asList(videoStreams.split(","));
        for (String stream : sortStreamsArray) {
            if (!support3D && (stream != null) && (stream.indexOf("3d") > -1)) {
                continue;// machine doesn't support 3d stream so continue
            }
            if (list.contains(stream) && (clientStreamIndex <= ArrayUtils.indexOf(sortStreamsArray, stream))) {
                // 针对已排序默认码流中的每一个码流，如果它在视频支持码流中，且其在已排序默认码流中的顺序比请求播放的码流在已排序默认码流中的位置靠后，就选择它作为降码流
                playStream = stream;
                break;
            }
        }
        return playStream;
    }

    private String getReducedStream(String clientRequestStream, String videoStreams, String sortStreams,
            Boolean support3D, Boolean support4K) {
        if (support3D == null) {
            support3D = false;
        }
        String playStream = "";

        String[] sortStreamsArray = sortStreams.split("#");
        int clientStreamIndex = ArrayUtils.indexOf(sortStreamsArray, clientRequestStream);
        List<String> list = Arrays.asList(videoStreams.split(","));
        for (String stream : sortStreamsArray) {
            if (stream == null) {
                continue;
            }
            if (!support3D && (stream.indexOf("3d") > -1)) {
                continue;// machine doesn't support 3d stream so continue
            }
            if (!support4K && (stream.equalsIgnoreCase("4k"))) {
                continue;
            }
            if (list.contains(stream) && (clientStreamIndex <= ArrayUtils.indexOf(sortStreamsArray, stream))) {
                // 针对已排序默认码流中的每一个码流，如果它在视频支持码流中，且其在已排序默认码流中的顺序比请求播放的码流在已排序默认码流中的位置靠后，就选择它作为降码流
                playStream = stream;
                break;
            }
        }
        return playStream;
    }

    /**
     * 针对特殊码流进行处理/对客户端传入的一些码流做约定处理
     * 2016-03-07TV版码流修改，1000码率不转，降为800，重新罗列清晰度对应表，这里将1000码率转为实际800播放
     * @return
     */
    private String handleSpecialStream(String playStream, String videoStreams, CommonParam commonParam) {
        if ("1080p".equalsIgnoreCase(playStream)) {
            playStream = playStream + "6m";
        }

        if (LetvStreamCommonConstants.CODE_NAME_1000.equalsIgnoreCase(playStream) && (videoStreams != null)
                && (Arrays.asList(videoStreams.split(",")).contains("800"))) {
            playStream = LetvStreamCommonConstants.CODE_NAME_800;
        }

        return playStream;
    }

    /**
     * 针对特定码率过滤播放视频码表
     * @param playStream
     * @param videoStreams
     * @param commonParam
     * @param videoPlay
     * @return
     */
    private String handleVideoStream(String playStream, String videoStreams, CommonParam commonParam, VideoDto videoPlay) {
        Boolean switcher = ApplicationUtils.getBoolean(ApplicationConstants.IPTV_PLAY_STREAM_FILTER_SWITCH);
        if (null == switcher || !switcher) {
            return videoStreams;
        }
        String[] streams = VideoUtil.playStreamFilter(videoStreams.split(","), null, commonParam, videoPlay);
        return StringUtil.getArrayToString(streams, ",");
    }

    /**
     * 对特殊客户端做IP放开限制
     * @return
     */
    public String handleSpecialClient() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 从码流配置MAP中获得播放码流对象
     * @param streamCodes
     * @param preSetStream
     * @return
     */
    private void setPlayStreamByStreamCode(String clientRequestStream, String streamCodes,
            Map<String, Stream> preSetStream, VideoDto videoPlay, Integer model, CommonParam commonParam) {
        if (streamCodes == null) {// 避免空指针
            return;
        }
        List<Stream> playStreams = new ArrayList<Stream>();

        // =================避免前端v2.10.1版本文案提示NPE dengliwei
        // 20170622(B)=====================//
        if (streamCodes.contains(LetvStreamCommonConstants.CODE_NAME_1080p3m) && null != videoPlay.getCurrentStream()
                && videoPlay.getCurrentStream().equals(LetvStreamCommonConstants.CODE_NAME_1080p3m)) {
            videoPlay.setCurrentStream(LetvStreamCommonConstants.CODE_NAME_1080p6m);
        }
        // =================避免前端v2.10.1版本文案提示NPE dengliwei
        // 20170622(E)=====================//

        streamCodes = streamCodes.replace(LetvStreamCommonConstants.CODE_NAME_1080p3m,
                LetvStreamCommonConstants.CODE_NAME_1080p6m);

        // 码流过滤处理
        String[] streams = VideoUtil.playStreamFilter(streamCodes.split(","), model, commonParam, videoPlay);

        // 是否是2016-03-07TV版码流修改之前的标清1000
        boolean isOldStandardDefinition = LetvStreamCommonConstants.CODE_NAME_1000
                .equalsIgnoreCase(clientRequestStream);
        /*
         * 2016-03-07TV版码流修改，1000码率不转，降为800，重新罗列清晰度对应表，这里添加特殊处理，针对
         * 当前视频支持的所有码流，如果请求的是1000，说明是老版本，这里不再返回800（也不处理是否返回1000的
         * 情况）；如果请求的是800，则说明是新版本，这里不返回1000（也不处理是否返回800的情况）
         */
        for (int i = streams.length - 1; i >= 0; i--) {
            if (StringUtils.isNotEmpty(streams[i])) {
                Stream currentSteam = preSetStream.get(streams[i]);
                if (currentSteam != null && !playStreams.contains(currentSteam)) {
                    if (
                    // =================避免前端v2.10.1版本文案提示NPE dengliwei
                    // 20170704(B)=====================//
                    /*
                     * (isOldStandardDefinition &&
                     * LetvStreamCommonConstants.CODE_NAME_800
                     * .equalsIgnoreCase(streams[i]))
                     * ||
                     */
                    // =================避免前端v2.10.1版本文案提示NPE dengliwei
                    // 20170704(E)=====================//
                    (!isOldStandardDefinition && LetvStreamCommonConstants.CODE_NAME_1000.equalsIgnoreCase(streams[i]))) {
                        continue;
                    }
                    playStreams.add(currentSteam);
                    if (0 == currentSteam.getIfCharge() && "1".equals(currentSteam.getCanPlay())) {
                        videoPlay.setFreeStream(currentSteam);
                    }
                }
            }
        }

        if (model != null && VideoConstants.PLAY_MODEL_TOUPING == model) {
            // 投屏模式下所有码流免费
            List<Stream> tmp = new ArrayList<Stream>();
            for (Stream stream : playStreams) {
                Stream v = new Stream();
                v.setCode(stream.getCode());
                v.setCanDown(stream.getCanDown());
                v.setCanPlay(stream.getCanPlay());
                v.setName(stream.getName());
                v.setBandWidth(stream.getBandWidth());
                v.setIfCharge(0);
                v.setKbps(stream.getKbps());
                tmp.add(v);
            }

            // 取最后一个低分辨率码流作为默认免费码流
            if (tmp.size() > 0) {
                if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                        .getTerminalApplication())) {
                    // Lecom TV Le要求投屏码流和TV版一致，高码流在前
                    Collections.sort(tmp);
                }

                videoPlay.setFreeStream(tmp.get(tmp.size() - 1));
                videoPlay.setStreams(tmp);
            }
        } else {
            // 按码流分辨率从高到低排序
            Collections.sort(playStreams);

            // 如果有杜比全景声，则过滤其他杜比码流
            this.filterDBStream(playStreams);

            videoPlay.setStreams(playStreams);
        }
    }

    /**
     * 如果有杜比全景声，则过滤其他杜比码流
     * @param playStreams
     */
    public void filterDBStream(List<Stream> playStreams) {
        Boolean haveDbatmos = false;
        for (Stream s : playStreams) {
            if (LetvStreamCommonConstants.CODE_NAME_DOLBY_1080p_ATMOS.equalsIgnoreCase(s.getCode())) {
                haveDbatmos = true;
                break;
            }
        }
        if (haveDbatmos) {
            Iterator<Stream> iterator = playStreams.iterator();
            while (iterator.hasNext()) {
                Stream s = iterator.next();
                if (LetvStreamCommonConstants.CODE_NAME_DOLBY_720p.equalsIgnoreCase(s.getCode())
                        || LetvStreamCommonConstants.CODE_NAME_DOLBY_1300.equalsIgnoreCase(s.getCode())
                        || LetvStreamCommonConstants.CODE_NAME_DOLBY_1000.equalsIgnoreCase(s.getCode())
                        || LetvStreamCommonConstants.CODE_NAME_DOLBY_1080p.equalsIgnoreCase(s.getCode())
                        || LetvStreamCommonConstants.CODE_NAME_DB.equalsIgnoreCase(s.getCode())) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 收费逻辑处理
     * @return
     */
    public Integer chargePolicy(AlbumMysqlTable albumMysqlTable, VideoMysqlTable videoMysqlTable, String playStream,
            String terminalApplication, String appCode, Integer model, CommonParam commonParam) {
        ChargePolicyContext chargePolicyContext = null;

        if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(terminalApplication)) {
            // 美国Le.com，全部按会员收费
            chargePolicyContext = new ChargePolicyContext(new ChargePolicyLeUS(videoMysqlTable, albumMysqlTable));
        } else {
            String area = StringUtils.trimToNull(commonParam.getSalesArea());
            if (area == null) {
                area = commonParam.getWcode();
            }

            if (ProductLineConstants.WCODE.LETV_CN.equalsIgnoreCase(area)) {
                Integer ac = TerminalUtil.parseAppCode(appCode);
                if (TerminalConstant.TERMINAL_APPLICATION_CIBN.equalsIgnoreCase(terminalApplication) && ac != null
                        && ac < 20) {
                    chargePolicyContext = new ChargePolicyContext(
                            new ChargePolicyCIBN(albumMysqlTable, videoMysqlTable));
                } else {
                    // chargePolicyContext = new ChargePolicyContext(new
                    // ChargePolicyCN(playStream, albumMysqlTable,
                    // 1 == videoMysqlTable.getVideo_attr() ? true : false,
                    // model, commonParam));
                    chargePolicyContext = new ChargePolicyContext(new ChargePolicyCN(playStream, albumMysqlTable,
                            videoMysqlTable, model, commonParam));
                }
            } else if (ProductLineConstants.WCODE.LETV_HK.equalsIgnoreCase(area)) {
                Integer ac = TerminalUtil.parseAppCode(appCode);
                if (ac < 231 || 2229 == ac.intValue())// 内容收费香港版
                {
                    chargePolicyContext = new ChargePolicyContext(new ChargePolicyV1HK(videoMysqlTable));
                } else {
                    chargePolicyContext = new ChargePolicyContext(new ChargePolicyHK(albumMysqlTable,
                            1 == videoMysqlTable.getVideo_attr() ? true : false));
                }
            } else {
                // 默认大陆收费策略
                chargePolicyContext = new ChargePolicyContext(new ChargePolicyCN(playStream, albumMysqlTable,
                        1 == videoMysqlTable.getVideo_attr() ? true : false, model, commonParam));
            }
        }

        return chargePolicyContext.charge();
    }

    /**
     * 播放鉴权:根据用户权限获得播放类型
     * @return
     */
    public UserPlayAuth getUserAuth(Boolean login, Integer chargeType, Long pid, Long userId, Integer pricePackageType,
                                    Boolean isFromCntv, Boolean isFromCibn, String storePath, String playStream, String validDate,
                                    Integer model, CommonParam commonParam) {
        UserPlayAuth userPlayAuth = null;
        if (login && ChargeTypeConstants.chargePolicy.FREE != chargeType) {
            // 只有不是免费观看才去boss鉴权
            if (StringUtils.isNotEmpty(commonParam.getUsername()) && userId == null) {
                UserStatus user = userService.getUserStatus(commonParam);
                if (user != null) {
                    userId = user.getUserId();
                } else {
                    commonParam.setUserId(String.valueOf(userId));
                }
            }

            Boolean needAuth = true;
            if (!TerminalUtil.isLetvUs(commonParam)) {// 美国行货一直需要鉴权拿token
                if (ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM == chargeType) {
                    // 如果是码流付费而且是会员则不需要鉴权
                    if (this.isVip(validDate, userId, commonParam.getDeviceKey())) {
                        needAuth = false;
                    }
                }
                // 儿童模式收费的片子--包月，会员可观看，非会员不可看
                // if (ChargeTypeConstants.chargePolicy.CHARGE_YUAN_XIAN ==
                // chargeType && model == 1) {
                // // 如果儿童模式付费，并且是会员可观看
                // if (this.isVip(chargeType, validDate, userId,
                // commonParam.getDeviceKey())) {
                // needAuth = false;
                // }
                // }
            }

            if (needAuth) {
                userPlayAuth = this.facadeTpDao.getVideoTpDao().getUserPlayAuth(pid, userId, pricePackageType,
                        isFromCntv, isFromCibn, storePath, commonParam);
            } else {
                userPlayAuth = new UserPlayAuth(1);
            }
        }

        return userPlayAuth;
    }

    /**
     * 播放鉴权:根据用户权限获得播放类型
     * @param login
     * @param chargeType
     * @param pid
     * @param storePath
     * @param validDate
     * @param commonParam
     * @param isVod
     * @return
     */
    public PlayAuthV2 getPlayAuthInfoV2(Boolean login, Integer chargeType, String pid, String storePath,
            String validDate, CommonParam commonParam, int isVod, VideoDto videoPlay) {
        PlayAuthV2 userPlayAuth = null;
        if ((login || isVod == 2) && ChargeTypeConstants.chargePolicy.FREE != chargeType) { // 只有不是免费观看才去boss鉴权
            Boolean needAuth = true;
            if (!TerminalUtil.isLetvUs(commonParam)) {// 美国行货一直需要鉴权拿token
                if (ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM == chargeType) {// 如果是码流付费而且是会员则不需要鉴权
                    if (this.isVip(validDate, StringUtil.toLong(commonParam.getUserId()), commonParam.getDeviceKey())) {
                        needAuth = false;
                    }
                }
            }
            if (needAuth) {
                BossTpResponse<ValidateServiceTp> validateServiceTpBossTpResponse = null;
                if (false && null != videoPlay && StringUtil.isNotBlank(videoPlay.getVideoId())) {
                    validateServiceTpBossTpResponse = this.facadeTpDao.getVipTpDao().validateVideoPlayServiceV2(
                            commonParam.getUserId(), videoPlay.getVideoId(), storePath, commonParam, isVod);
                } else {
                    validateServiceTpBossTpResponse = this.facadeTpDao.getVipTpDao().validateVideoPlayService(
                            commonParam.getUserId(), pid, storePath, commonParam, isVod);
                }

                userPlayAuth = this.parsePlayAuthV2(validateServiceTpBossTpResponse, commonParam, videoPlay);
            } else {
                userPlayAuth = new PlayAuthV2(PlayAuthV2.STATUS_PASS); // 不用鉴权,直接设置鉴权成功
            }
        }

        return userPlayAuth;
    }

    private PlayAuthV2 parsePlayAuthV2(BossTpResponse<ValidateServiceTp> validateServiceTpBossTpResponse,
            CommonParam commonParam, VideoDto videoPlay) {
        PlayAuthV2 userPlayAuth = new PlayAuthV2();
        if (validateServiceTpBossTpResponse != null && validateServiceTpBossTpResponse.isSucceed()
        /*
         * && (null != validateServiceTpBossTpResponse.getData()
         * && PlayAuthV2.STATUS_PASS ==
         * validateServiceTpBossTpResponse.getData().getStatus())
         */) { // boss鉴权响应成功
            ValidateServiceTp validateServiceTp = validateServiceTpBossTpResponse.getData();
            userPlayAuth.setIsForbidden(PlayAuthV2.FORBIDDEN_FALSE); // 设置用户未被封禁
            userPlayAuth.setStatus(validateServiceTp.getStatus());
            if (validateServiceTp.getStatus() == PlayAuthV2.STATUS_PASS) {
                userPlayAuth.setPlayEndTime(validateServiceTp.getTvodRts());
            }
            userPlayAuth.setToken(validateServiceTp.getToken());
            userPlayAuth.setTokenUserId(commonParam.getUserId()); // userId从commonParam里获取
            userPlayAuth.setUinfo(validateServiceTp.getUinfo());
            userPlayAuth.setErrorCode(validateServiceTp.getErrorCode());
            userPlayAuth.setMsg(validateServiceTp.getMsg());
            userPlayAuth.setVipTypeInfo(validateServiceTp.getVipTypeInfo());
            userPlayAuth.setVipInfo(validateServiceTp.getVipInfo());
            userPlayAuth.setPackageInfo(validateServiceTp.getPackageInfo());
            /*
             * 设置VodInfo
             */
            if (validateServiceTp.getVodInfo() != null) {
                ValidateServiceTp.VodInfo vodInfoTp = validateServiceTp.getVodInfo();
                PlayAuthV2.VodInfo vodInfo = new PlayAuthV2.VodInfo();
                userPlayAuth.setVodInfo(vodInfo);

                vodInfo.setChargeTerminalPrice(vodInfoTp.getChargeTerminalPrice());
                /*
                 * 设置定价信息
                 */
                if (vodInfoTp.getCharge() != null) {
                    PlayAuthV2.VodChargeInfo vodChargeInfo = new PlayAuthV2.VodChargeInfo();
                    vodInfo.setCharge(vodChargeInfo);

                    ValidateServiceTp.VodChargeInfo vodChargeInfoTp = vodInfoTp.getCharge();
                    vodChargeInfo.setApp(vodChargeInfoTp.getApp());
                    vodChargeInfo.setChargeId(vodChargeInfoTp.getChargeId());
                    vodChargeInfo.setMemberDiscounts(vodChargeInfoTp.getMemberDiscounts());
                    vodChargeInfo.setValidTime(vodChargeInfoTp.getValidTime());
                    vodChargeInfo.setChargeName(vodChargeInfoTp.getChargeName());
                }
                /*
                 * 设置付费信息
                 */
                if (vodInfoTp.getVideo() != null) {
                    PlayAuthV2.VodChargeConfig vodChargeConfig = new PlayAuthV2.VodChargeConfig();
                    vodInfo.setVideo(vodChargeConfig);

                    ValidateServiceTp.VodChargeConfig vodChargeConfigTp = vodInfoTp.getVideo();
                    vodChargeConfig.setChargeRule(vodChargeConfigTp.getChargeRule());
                    vodChargeConfig.setChargeType(vodChargeConfigTp.getChargeType());
                    vodChargeConfig.setIsCharge(vodChargeConfigTp.getIsCharge());
                    vodChargeConfig.setIsSupportTicket(vodChargeConfigTp.getIsSupportTicket());
                }
            }

            /*
             * [分端付费]设置ChargeInfo
             */
            if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE && validateServiceTp.getChargeInfo() != null) {
                ValidateServiceTp.ChargeInfo chargeInfoTp = validateServiceTp.getChargeInfo();
                if (null != chargeInfoTp.getTerminalCharges() && chargeInfoTp.getTerminalCharges().size() > 0) {
                    ChargeInfo chargeInfo = chargeInfoTp.getTerminalCharges().get(0);
                    if (null != chargeInfo) {
                        PlayAuthV2.VodInfo vodInfo = null;
                        if (null != userPlayAuth.getVodInfo()) {
                            vodInfo = userPlayAuth.getVodInfo();
                        } else {
                            vodInfo = new PlayAuthV2.VodInfo();
                            userPlayAuth.setVodInfo(vodInfo);
                        }
                        /*
                         * 设置定价信息
                         */
                        if (true || null != chargeInfo.getChargeRule()) {
                            Map<String, String> chargeTerminalPrice = new HashMap<String, String>();
                            chargeTerminalPrice.put(chargeInfo.getChargePlatform(),
                                    chargeInfo.getChargeRule() == null ? null : chargeInfo.getChargeRule().getPrice());
                            if (chargeInfo.getChargePlatform().equals(
                                    MmsDataUtil.getPayPlatform(commonParam.getP_devType()))) { // 二次校验
                                PlayAuthV2.VodChargeInfo vodChargeInfo = null;
                                if (null != vodInfo.getCharge()) {
                                    vodChargeInfo = vodInfo.getCharge();
                                } else {
                                    vodChargeInfo = new PlayAuthV2.VodChargeInfo();
                                    vodInfo.setCharge(vodChargeInfo);
                                }

                                vodInfo.setChargeTerminalPrice(chargeTerminalPrice);
                                vodChargeInfo.setApp(chargeInfo.getApp());
                                vodChargeInfo.setChargeId(chargeInfo.getChargeId());
                                if (null != chargeInfo.getChargeRule()
                                        && StringUtil.isNotBlank(chargeInfo.getChargeRule().getMemberPrice())
                                        && Float.parseFloat(chargeInfo.getChargeRule().getMemberPrice()) > 0) {
                                    // fixed: 处理会员价
                                    vodChargeInfo.setMemberPrice(chargeInfo.getChargeRule().getMemberPrice());
                                    vodChargeInfo.setMemberDiscounts(1);
                                } else {
                                    vodChargeInfo.setMemberDiscounts(0);
                                }
                                vodChargeInfo.setValidTime(chargeInfo.getValidTime());
                                vodChargeInfo.setChargeName(chargeInfoTp.getName());

                                /*
                                 * 设置付费信息
                                 */
                                PlayAuthV2.VodChargeConfig vodChargeConfig = null;
                                if (vodInfo.getVideo() != null) {
                                    vodChargeConfig = vodInfo.getVideo();
                                } else {
                                    vodChargeConfig = new PlayAuthV2.VodChargeConfig();
                                    vodInfo.setVideo(vodChargeConfig);
                                }
                                // vodChargeConfig.setChargeRule(chargeInfo.getChargeRule());
                                vodChargeConfig.setChargeType(chargeInfo.getChargeType4i());
                                vodChargeConfig.setIsCharge(Integer.parseInt(chargeInfo.getIsCharge()));
                                vodChargeConfig.setIsSupportTicket(Integer.parseInt(chargeInfo.getIscoupon()));
                            }
                        }

                        // 设置试看时长
                        if (StringUtil.isNotBlank(chargeInfo.getTryLookTime())) {
                            Long tryLookTime = null;
                            try {
                                tryLookTime = Long.parseLong(chargeInfo.getTryLookTime());
                            } catch (NumberFormatException nfe) {

                            }
                            if (null != tryLookTime) {
                                videoPlay.setTryPlayTime(tryLookTime.longValue() * 1000);
                                long tryPlayMin = (tryLookTime.longValue()) / 60;
                                String message = MessageUtils
                                        .getMessage(VideoConstants.VIDEO_TIPMSG_TRY_PLAY_DYNAMIC_MINUTES,
                                                commonParam.getLangcode());
                                videoPlay.setTryPlayTipMsg(String.format(message, tryPlayMin));
                            }
                        }
                    }
                }
            }

            /*
             * 设置试看信息
             */
            if (validateServiceTp.getTryInfo() != null) {
                PlayAuthV2.VodTryPlayInfo vodTryPlayInfo = new PlayAuthV2.VodTryPlayInfo();
                userPlayAuth.setTryInfo(vodTryPlayInfo);

                ValidateServiceTp.VodTryPlayInfo vodTryPlayInfoTp = validateServiceTp.getTryInfo();
                vodTryPlayInfo.setTryStartTime(vodTryPlayInfoTp.getTryStartTime());
                vodTryPlayInfo.setTryEndTime(vodTryPlayInfoTp.getTryEndTime());
                vodTryPlayInfo.setServerTime(vodTryPlayInfoTp.getServerTime());

                // 设置videoDto的tryPlayTime试看时长
                videoPlay.setTryPlayTime(vodTryPlayInfoTp.getTryEndTime() - vodTryPlayInfoTp.getTryStartTime());
                long tryPlayMin = (vodTryPlayInfoTp.getTryEndTime() - vodTryPlayInfoTp.getTryStartTime()) / 60000;
                String message = MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_TRY_PLAY_DYNAMIC_MINUTES,
                        commonParam.getLangcode());
                videoPlay.setTryPlayTipMsg(String.format(message, tryPlayMin));
            }
        } else if (validateServiceTpBossTpResponse != null && validateServiceTpBossTpResponse.isForbiddin()) { // 用户被封禁
            userPlayAuth.setStatus(PlayAuthV2.STATUS_UNPASS); // 设置为鉴权未通过
            userPlayAuth.setIsForbidden(PlayAuthV2.FORBIDDEN_TRUE); // 设置用户被封禁
            // TODO: 待直接抛出异常！
        } else {
            userPlayAuth.setStatus(PlayAuthV2.STATUS_UNPASS); // 设置为鉴权未通过
        }
        return userPlayAuth;
    }

    public UserPlayAuth getUserAuth4LecomUS(Boolean login, Integer chargeType, Long pid, Long userId,
            Integer pricePackageType, Boolean isFromCntv, Boolean isFromCibn, String storePath, String playStream,
            String validDate, Integer model, CommonParam commonParam) {
        UserPlayAuth userPlayAuth = null;
        if (login && ChargeTypeConstants.chargePolicy.FREE != chargeType) {

            userPlayAuth = this.facadeTpDao.getVideoTpDao().getUserPlayAuth(pid, userId, pricePackageType, isFromCntv,
                    isFromCibn, storePath, commonParam);
            if (userPlayAuth == null) {
                // 异常鉴权则默认放行
                userPlayAuth = new UserPlayAuth(1);
            }

        }

        return userPlayAuth;
    }

    /**
     * 单点付费逻辑
     * @return
     */
    @Deprecated
    public Integer chargePolicyOrderSingle(UserPlayAuth userPlayAuth, Integer chargeType, VideoDto videoPlay,
            AlbumMysqlTable albumMysqlTable, String wcode, boolean login, Integer model) {
        Integer finalChargeType = chargeType; // 最终的收费策略
        String logPrefix = "chargePolicyOrderSingle_" + chargeType;
        String price = null;
        Integer expiredTime = null;
        if (userPlayAuth != null && ChargeTypeConstants.chargePolicy.FREE != chargeType) {// 非正常播放
            expiredTime = userPlayAuth.getExpiredTime();
            price = userPlayAuth.getPrice();

            // 大陆--价格不为0即为单点；香港--价格不为0且boss设置为仅单点
            boolean single = this.singleFlag(userPlayAuth.getChargeType(), wcode, price);
            log.info(logPrefix + "_" + price + "_" + wcode + ": single[" + single + "]'");
            if (single) {
                // 支持单点付费；实际还有bug，只有chargeType=4才有意义
                videoPlay.setPayType(1);
                if (albumMysqlTable != null) {
                    videoPlay.setAreaName(albumMysqlTable.getArea_name());
                    videoPlay.setDirector(albumMysqlTable.getDirectory());
                    videoPlay.setPoster(albumMysqlTable.getPic(300, 400));
                }
            } else {
                // 如果价格为0，则要考虑当chargeType=4修改为降码流播放，即
                // modeType==1为乐视儿童模式，不转码流收费
                if (ChargeTypeConstants.chargePolicy.CHARGE_YUAN_XIAN == chargeType && model != 1) {
                    finalChargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM;
                    videoPlay.setIfCharge(ChargeTypeConstants.NOT_CHARGE);
                }
                videoPlay.setPayType(0);
            }

            videoPlay.setPrice(price);
            videoPlay.setExpiredTime(expiredTime);
        } else {
            log.info(logPrefix + ": no auth charge");
            // 未登录&&单点收费
            if (!login && ChargeTypeConstants.chargePolicy.CHARGE_YUAN_XIAN == chargeType) {
                if (model != 1) {
                    Integer isCharge = this.getUnloginCharge(videoPlay, albumMysqlTable);
                    // 未登录播放鉴权接口有可能取不到数据，此时isCharge=null，该如何处理？
                    if ((isCharge != null && isCharge == 0)) {
                        finalChargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM;
                        videoPlay.setIfCharge(ChargeTypeConstants.NOT_CHARGE);
                        videoPlay.setPayType(0);
                        log.info(logPrefix + "_login:" + login + "_isCharge:" + isCharge);
                    } else if (isCharge == null) {
                        log.error(logPrefix + "_login:" + login + "_isCharge:" + isCharge);
                    }
                }
            }
        }

        return finalChargeType;
    }

    /**
     * 未登录用户播放鉴权（未登录用户获取收费、资费信息）
     * @return
     */
    @Deprecated
    private Integer getUnloginCharge(VideoDto videoPlay, AlbumMysqlTable albumMysqlTable) {
        String logPrefix = "getUnloginCharge_" + albumMysqlTable.getId();
        GetAlbumChargeInfoTpResponse response = this.facadeTpDao.getVideoTpDao().getAlbumChargeInfo(
                albumMysqlTable.getId());
        Integer isCharge = null;
        if (response != null) {
            Map<String, String> chargeMap = response.getChargeflatform();
            String price = "0";
            if (!CollectionUtils.isEmpty(chargeMap)) {// TV--141007
                if (StringUtils.isNotEmpty(chargeMap.get("141007"))) {
                    price = chargeMap.get("141007");
                }
            }
            videoPlay.setPrice(price);

            // 设置单点付费有效时长
            Integer validDays = response.getValidDays();
            if (validDays != null && validDays > 0) {
                videoPlay.setExpiredTime(24 * validDays);
            }

            isCharge = response.getIscharge() != null ? response.getIscharge() : 0;

            // isCharge指定了收费后，price节点依然有可能为0
            if (isCharge == 1) {
                if (!("0".equalsIgnoreCase(price) || "0.00".equalsIgnoreCase(price) || "0.0".equalsIgnoreCase(price))) {
                    videoPlay.setIfCharge(String.valueOf(isCharge));
                    videoPlay.setPayType(1);
                    if (albumMysqlTable != null) {
                        videoPlay.setAreaName(albumMysqlTable.getArea_name());
                        videoPlay.setDirector(albumMysqlTable.getDirectory());
                        videoPlay.setPoster(albumMysqlTable.getPic(300, 400));
                    }
                    log.info(logPrefix + "_isCharge" + isCharge);
                } else {
                    isCharge = 0;
                }
            }
        }
        return isCharge;
    }

    /**
     * 登录鉴权通过时，根据price是否为零确定是否转码流收费
     * @param wcode
     * @return
     */
    private boolean singleFlag(Integer chargeType, String wcode, String price) {
        boolean single = false;// 是否单点
        if (wcode != null) {
            if (ProductLineConstants.WCODE.LETV_HK.equals(wcode)) {
                if (!"0".equalsIgnoreCase(price) && !"0.0".equalsIgnoreCase(price) && (chargeType != null)
                        && (0 == chargeType)) {
                    // 香港--价格不为0且boss设置为仅单点
                    single = true;
                }
            } else {
                if (price != null && !"0".equalsIgnoreCase(price) && !"0.0".equalsIgnoreCase(price)
                        && (chargeType != null) && (0 == chargeType)) {
                    // 大陆--价格不为0即为单点
                    single = true;
                }
            }
        }
        return single;
    }

    /**
     * 用户登录情况下的播放鉴权
     * @param videoPlay
     * @param pricePackageType
     * @param logPrefix
     * @return
     */
    private Integer setUserPlayInfo4Login(VideoDto videoPlay, VideoMysqlTable videoMysqlTable, String storePath,
            UserPlayAuth userPlayAuth, String playStream, Integer chargeType, Boolean login, Long userId,
            String validDate, Integer model, Boolean isFromCntv, Boolean isFromCibn, Integer pricePackageType,
            CommonParam commonParam, String logPrefix) {
        // 登录的情况
        Boolean isVip = this.isVip(validDate, userId, commonParam.getDeviceKey());
        // 2016-05-06，播放接口vip字段设置为当前用户真实会员信息
        videoPlay.setVip(isVip ? 1 : 0);
        if (isVip && (ChargeTypeConstants.chargePolicy.CHARGE_YUAN_XIAN == chargeType && model == 1)) {
            // 1、如果是码流付费而且是会员则不需要鉴权
            // 2、儿童模式收费的片子--包月，会员可观看，非会员不可看
            userPlayAuth.setStatus(1);// 不需要加速
            UserPlayAuth us = this.getUserAuth(login, chargeType, videoMysqlTable.getPid(), userId, pricePackageType,
                    isFromCntv, isFromCibn, storePath, playStream, validDate, model, commonParam);
            if (us != null) {// 儿童鉴权只为获取token信息
                userPlayAuth.setUinfo(us.getUinfo());
                userPlayAuth.setToken(us.getToken());
                userPlayAuth.setTokenUserId(us.getTokenUserId());
            }
        } else {
            boolean single = false;// 是否单点
            boolean hasAuth = false;// 是否有权限播放
            if (chargeType == ChargeTypeConstants.chargePolicy.CHARGE_YUAN_XIAN) {// 院线才进行单点鉴权
                UserPlayAuth us = this.getUserAuth(login, chargeType, videoMysqlTable.getPid(), userId,
                        pricePackageType, isFromCntv, isFromCibn, storePath, playStream, validDate, model, commonParam);
                if (us != null) {// 不为空才判断是否单点
                    String price = us.getPrice();
                    Integer expiredTime = us.getExpiredTime();
                    Integer status = us.getStatus();
                    userPlayAuth.setStatus(status);
                    if (status != null && status == 1) {// 鉴权通过
                        hasAuth = true;
                    }
                    userPlayAuth.setChargeType(us.getChargeType());
                    userPlayAuth.setUinfo(us.getUinfo());
                    userPlayAuth.setToken(us.getToken());
                    userPlayAuth.setTokenUserId(us.getTokenUserId());
                    userPlayAuth.setIsPay(us.getIsPay());
                    userPlayAuth.setPrice(price);
                    userPlayAuth.setVipPrice(us.getVipPrice());
                    userPlayAuth.setExpiredTime(expiredTime);
                    userPlayAuth.setLeftTime(us.getLeftTime());
                    userPlayAuth.setErrorCode(us.getErrorCode());
                    userPlayAuth.setErrorMsg(us.getErrorMsg());
                    userPlayAuth.setIsForbidden(us.getIsForbidden());
                    videoPlay.setPrice(this.getPrice(price));
                    videoPlay.setExpiredTime(expiredTime);
                    single = this.singleFlag(userPlayAuth.getChargeType(), commonParam.getWcode(), price);
                    if (single) {
                        videoPlay.setPayType(1);
                    } else {
                        Integer ct = us.getChargeType();
                        if (ct != null) {
                            if (ct == 0) {// 客户端认为仅单点也是会员且单点，所以此步要兼容
                                ct = 1;
                            }
                            videoPlay.setPayType(ct);// 视频专辑的付费类型
                            if (ct == 1 && !single) {// 不是真正的单点
                                videoPlay.setPayType(2);
                            }
                        }
                    }
                }
            }
            if (!hasAuth) {// 鉴权不通过
                if (single) {// 引导单点购买
                    if (!VideoCommonUtil.isCharge(videoMysqlTable.getPay_platform(),
                            videoMysqlTable.getPlay_platform(), commonParam.getP_devType())) {
                        chargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM;
                    } else {
                        videoPlay.setPayType(1);
                    }
                } else {
                    Integer payType = 0;// 默认免费， 0--免费，1--单点，2--全频道付费，3--非全频道付费
                    // 如果是院线付费但是鉴权不过，那就不管了
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
                        List<PayChannel> payChannelList = tpResponse.getData();
                        String category = String.valueOf(videoMysqlTable.getCategory());
                        if (category != null && !CollectionUtils.isEmpty(payChannelList)) {
                            for (PayChannel payChannel : payChannelList) {
                                if (category.equals(payChannel.getChannelId())) {
                                    Integer ifCh = payChannel.getIsCharge();
                                    if (ifCh != null && ifCh == 1) {
                                        payType = 2;// 全频道付费，仅会员逻辑
                                    } else {
                                        payType = 3;// 非全频道付费
                                    }
                                    String price = payChannel.getPrice();
                                    videoPlay.setPrice(this.getPrice(price));
                                    Integer ct = payChannel.getChargeType();
                                    if (ct != null) {
                                        if (ct == 0) {// 客户端认为仅单点也是会员且单点，所以此步要兼容
                                            ct = 1;
                                        }
                                        videoPlay.setPayType(ct);
                                        if (ct == 1) {
                                            if (StringUtils.isBlank(price) || StringUtil.toFloat(price, 0.0F) <= 0.0F) {
                                                // 价格不为0，才是真正的单点
                                                videoPlay.setPayType(2);
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    if (payType == 2) {// 频道付费，则不管视频是什么收费类型，都要变成yuanxian收费
                        chargeType = ChargeTypeConstants.chargePolicy.CHARGE_YUAN_XIAN;
                    } else {// 不是频道付费，免费视频则码流收费
                        if (!VideoCommonUtil.isCharge(videoMysqlTable.getPay_platform(),
                                videoMysqlTable.getPlay_platform(), commonParam.getP_devType())) {
                            chargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM;
                        }
                    }
                    if (isVip) {// 会员鉴权通过，此时已经除去会员在BOSS鉴权不过的情况了
                        userPlayAuth.setStatus(1);
                        GetPlayUserTokenResponse authRes = this.facadeTpDao.getVideoTpDao().getPayChannelTokenInfo(
                                userId, storePath, logPrefix);
                        if (authRes == null || !"0".equals(authRes.getCode()) || authRes.getValues() == null) {
                            // 鉴权结果失败
                            log.info(logPrefix + ",get pay channnel auth failure.");
                        } else {// 付费频道鉴权结果对userPlayAuth赋值
                            PlayUserTokenInfo info = authRes.getValues();
                            userPlayAuth.setToken(info.getToken());
                            userPlayAuth.setTokenUserId(info.getTokenUserId());
                            userPlayAuth.setUinfo(info.getUinfo());
                        }
                    }
                }
            }
        }

        return chargeType;
    }

    /**
     * 用户登录情况下的播放鉴权
     * @param videoPlay
     * @param videoMysqlTable
     * @param storePath
     * @param userPlayAuth
     * @param chargeType
     * @param login
     * @param userId
     * @param validDate
     * @param model
     * @param commonParam
     * @param logPrefix
     * @return
     */
    private Integer setUserPlayInfo4LoginV2(VideoDto videoPlay, VideoMysqlTable videoMysqlTable, String storePath,
            PlayAuthV2 userPlayAuth, Integer chargeType, Boolean login, Long userId, String validDate, Integer model,
            CommonParam commonParam, String logPrefix) {
        // 登录的情况
        Boolean isVip = this.isVip(validDate, userId, commonParam.getDeviceKey());
        // 2016-05-06，播放接口vip字段设置为当前用户真实会员信息
        videoPlay.setVip(isVip ? 1 : 0);
        if (false
                && isVip
                && (ChargeTypeConstants.chargePolicy.CHARGE_YUAN_XIAN == chargeType && model == VideoConstants.PLAY_MODEL_CHILD)) {
            // 1、如果是码流付费而且是会员则不需要鉴权
            // 2、儿童模式收费的片子--包月，会员可观看，非会员不可看
            userPlayAuth.setStatus(PlayAuthV2.STATUS_PASS);// 不需要加速
            PlayAuthV2 us = this.getPlayAuthInfoV2(login, chargeType, videoMysqlTable.getPid() == null ? null
                    : videoMysqlTable.getPid().toString(), storePath, validDate, commonParam, TerminalUtil
                    .isTvodScope(commonParam) ? 2 : 1, videoPlay);
            if (us != null) {// 儿童鉴权只为获取token信息
                userPlayAuth.setUinfo(us.getUinfo());
                userPlayAuth.setToken(us.getToken());
                userPlayAuth.setTokenUserId(us.getTokenUserId());
            }
        } else {
            boolean single = false;// 是否单点
            boolean hasAuth = false;// 是否有权限播放
            if (chargeType == ChargeTypeConstants.chargePolicy.CHARGE_YUAN_XIAN) {// 院线才进行单点鉴权
                PlayAuthV2 userPlayAuthTmp = this.getPlayAuthInfoV2(login, chargeType,
                        videoMysqlTable.getPid() == null ? null : videoMysqlTable.getPid().toString(), storePath,
                        validDate, commonParam, TerminalUtil.isTvodScope(commonParam) ? 2 : 1, videoPlay);
                if (userPlayAuthTmp != null) {// 不为空才判断是否单点
                    if (userPlayAuth == null) {
                        userPlayAuth = new PlayAuthV2();
                    }
                    userPlayAuth.copyProperties(userPlayAuthTmp);
                    if (userPlayAuth.isAuthenticationPass()) {// 鉴权通过
                        hasAuth = true;
                        videoPlay.setPlayEndTime(userPlayAuth.getPlayEndTime());
                    }
                    String price = null;
                    if (userPlayAuth.getVodInfo() != null) {
                        /*
                         * 设置单点价格
                         */
                        if (userPlayAuth.getVodInfo().getChargeTerminalPrice() != null) {
                            if (MmsDataUtil.existPayPlatform(this.sessionCache)) {
                                price = userPlayAuth.getVodInfo().getChargeTerminalPrice()
                                        .get(MmsDataUtil.getPayPlatform(this.sessionCache)); // 从map中获取tv端的价格
                            } else {
                                price = userPlayAuth.getVodInfo().getChargeTerminalPrice()
                                        .get(BossTerminalType.TV.getCode().toString()); // 从map中获取tv端的价格
                            }

                            if (StringUtil.isNotBlank(price)) {
                                videoPlay.setPrice(price);
                            }

                            if (TerminalUtil.isTvodScope(commonParam) && StringUtil.isNotBlank(price)) {
                                Float curPrice = Float.parseFloat(price);
                                String memberPrice = userPlayAuth.getVodInfo().getCharge().getMemberPrice();
                                if (userPlayAuth.getVodInfo().getCharge().getMemberDiscounts() == 1) {
                                    if (StringUtil.isNotBlank(memberPrice)) {
                                        curPrice = Float.parseFloat(memberPrice);
                                    } else {
                                        curPrice /= 2;
                                    }
                                }
                                videoPlay.setCurPrice((new DecimalFormat("0.00")).format(curPrice));
                            }
                        }
                        /*
                         * 设置单点有效天数
                         */
                        if (userPlayAuth.getVodInfo().getCharge() != null) {
                            Integer validTime = userPlayAuth.getVodInfo().getCharge().getValidTime();
                            if (validTime != null) {
                                videoPlay.setExpiredTime(validTime * 24); // 转换成小时
                            }
                        }
                        /*
                         * 设置single(是否单点)及付费类型payType
                         */
                        if (userPlayAuth.getVodInfo().getVideo() != null) {
                            Integer chargeTypeTmp = userPlayAuth.getVodInfo().getVideo().getChargeType();
                            single = this.singleFlag(chargeTypeTmp, commonParam.getWcode(), price);
                            if (single) {
                                videoPlay.setPayType(1); // 设置成1:表示单点
                            } else if (chargeTypeTmp != null) { // 非单点逻辑处理
                                if (chargeTypeTmp == 0 || chargeTypeTmp == 1 || chargeTypeTmp == 2) { // 皆不是真正的单点,故设置成2(非1值)
                                    videoPlay.setPayType(2);
                                } else {
                                    videoPlay.setPayType(chargeTypeTmp);
                                }
                            }
                            // =========== boss分端付费策略同步问题捡漏 dengliwei
                            // 20180709(B)============//
                            if (null != userPlayAuth.getVodInfo().getVideo().getIsCharge()
                                    && userPlayAuth.getVodInfo().getVideo().getIsCharge() == 0) {
                                hasAuth = true;
                                chargeType = ChargeTypeConstants.chargePolicy.FREE;
                                videoPlay.setIfCharge("0");
                            }
                            // =========== boss分端付费策略同步问题捡漏 dengliwei
                            // 20180709(E)============//
                        }
                    }
                }
            }
            if (!hasAuth) {// 鉴权不通过
                if (single) {// 引导单点购买
                    if (!VideoCommonUtil.isCharge(videoMysqlTable.getPay_platform(),
                            videoMysqlTable.getPlay_platform(), commonParam.getP_devType())) {
                        chargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM;
                    } else {
                        videoPlay.setPayType(1);
                    }
                    if (TerminalUtil.isTvodScope(commonParam)
                            && 0 == userPlayAuth.getVodInfo().getVideo().getChargeType()
                            && 0 == userPlayAuth.getVodInfo().getVideo().getIsSupportTicket()) {
                        // videoPlay.setPlayType(ChargeTypeConstants.chargePolicy.CHARGE_BY_VIP);
                        chargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_VIP;
                    }
                } else {
                    Integer payType = 0;// 默认免费， 0--免费，1--单点，2--全频道付费，3--非全频道付费
                    if (CommonConstants.VIP_CHANNEL_PAYING_ENBABLE) {
                        // 如果是院线付费但是鉴权不过，那就不管了
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
                            List<PayChannel> payChannelList = tpResponse.getData();
                            String category = String.valueOf(videoMysqlTable.getCategory());
                            if (category != null && !CollectionUtils.isEmpty(payChannelList)) {
                                for (PayChannel payChannel : payChannelList) {
                                    if (category.equals(payChannel.getChannelId())) {
                                        Integer ifCh = payChannel.getIsCharge();
                                        if (ifCh != null && ifCh == 1) {
                                            payType = 2;// 全频道付费，仅会员逻辑
                                        } else {
                                            payType = 3;// 非全频道付费
                                        }
                                        String price = payChannel.getPrice();
                                        videoPlay.setPrice(this.getPrice(price));
                                        Integer ct = payChannel.getChargeType();
                                        if (ct != null) {
                                            if (ct == 0) {// 客户端认为仅单点也是会员且单点，所以此步要兼容
                                                ct = 1;
                                            }
                                            videoPlay.setPayType(ct);
                                            if (ct == 1) {
                                                if (StringUtils.isBlank(price)
                                                        || StringUtil.toFloat(price, 0.0F) <= 0.0F) {
                                                    // 价格不为0，才是真正的单点
                                                    videoPlay.setPayType(2);
                                                }
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (payType == 2) {// 频道付费，则不管视频是什么收费类型，都要变成yuanxian收费
                        chargeType = ChargeTypeConstants.chargePolicy.CHARGE_YUAN_XIAN;
                    } else {// 不是频道付费，免费视频则码流收费
                        if (!VideoCommonUtil.isCharge(videoMysqlTable.getPay_platform(),
                                videoMysqlTable.getPlay_platform(), commonParam.getP_devType())) {
                            // =========== 会员非正片为可看高清 dengliwei
                            // 20180709(B)============//
                            if (isVip
                                    && (StringUtil.isBlank(videoMysqlTable.getPay_platform()) || (StringUtil
                                            .isNotBlank(videoPlay.getIfCharge()) && videoPlay.getIfCharge().equals("0")))) {
                                chargeType = ChargeTypeConstants.chargePolicy.FREE;
                            } else {
                                chargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM;
                            }
                            // =========== 会员非正片为可看高清 dengliwei
                            // 20180709(E)============//
                        }
                    }
                    if (isVip) {// 会员鉴权通过，此时已经除去会员在BOSS鉴权不过的情况了
                        if (false && MmsDataUtil.existPayPlatform(this.sessionCache)) {
                            if (null != videoMysqlTable && StringUtil.isNotBlank(videoMysqlTable.getPay_platform())) {
                                String curPayPlatform = MmsDataUtil.getPayPlatform(this.sessionCache);
                                if (StringUtil.isNotBlank(curPayPlatform)
                                        && !videoMysqlTable.getPay_platform().contains(curPayPlatform)) {
                                    userPlayAuth.setStatus(1);
                                }
                            } else if (null != videoMysqlTable) { // 兼容旧版
                                userPlayAuth.setStatus(1);
                            }
                        } else {
                            userPlayAuth.setStatus(0);
                        }
                        if (CommonConstants.VIP_CHANNEL_PAYING_ENBABLE) {
                            GetPlayUserTokenResponse authRes = this.facadeTpDao.getVideoTpDao().getPayChannelTokenInfo(
                                    userId, storePath, logPrefix);
                            if (authRes == null || !"0".equals(authRes.getCode()) || authRes.getValues() == null) {
                                // 鉴权结果失败
                                log.info(logPrefix + ",get pay channnel auth failure.");
                            } else {// 付费频道鉴权结果对userPlayAuth赋值
                                PlayUserTokenInfo info = authRes.getValues();
                                userPlayAuth.setToken(info.getToken());
                                userPlayAuth.setTokenUserId(info.getTokenUserId());
                                userPlayAuth.setUinfo(info.getUinfo());
                            }
                        }
                    }
                }
            }
        }

        return chargeType;
    }

    private Integer setUserPlayInfo4LecomUSLogin(VideoDto videoPlay, VideoMysqlTable videoMysqlTable, String storePath,
            UserPlayAuth4LecomUS userPlayAuth, String playStream, Integer chargeType, Boolean login, Long userId,
            String validDate, Integer model, Boolean isFromCntv, Boolean isFromCibn, Integer pricePackageType,
            CommonParam commonParam, String logPrefix) {
        // 登录的情况
        Boolean isVip = this.isVip(validDate, userId, commonParam.getDeviceKey());
        // 2016-05-06，播放接口vip字段设置为当前用户真实会员信息
        videoPlay.setVip(isVip ? 1 : 0);

        BossTpResponse<ValidateServiceTp> playAuthResponse = this.facadeTpDao.getVipTpDao().validateVideoPlayService(
                String.valueOf(userId), videoPlay.getVideoId(), storePath, commonParam, -1);

        if (playAuthResponse != null) {
            if (playAuthResponse.getCode() == null || 0 != playAuthResponse.getCode()
                    || playAuthResponse.getData() == null) {
                // 异常鉴权，boss服务异常，返回鉴权通过，正常观看，但没有token
                userPlayAuth.setStatus(CommonConstants.PLAY_SERVICE_OPEN);
                log.warn(logPrefix + ": play auth return illegal data, set play service on ");
            } else {
                // 正常鉴权
                ValidateServiceTp playAuth = playAuthResponse.getData();
                if (playAuth.getStatus() == null) {
                    // 非法数据，默认正常
                    userPlayAuth.setStatus(CommonConstants.PLAY_SERVICE_OPEN);
                    log.warn(logPrefix + ": play auth return illegal data[null 'status'], set play service on ");
                } else if (CommonConstants.PLAY_SERVICE_OPEN == playAuth.getStatus()) {
                    // 鉴权通过，正常播放
                    userPlayAuth.setStatus(CommonConstants.PLAY_SERVICE_OPEN);
                    userPlayAuth.setToken(playAuth.getToken());
                } else {
                    // 鉴权不过，试看
                    userPlayAuth.setStatus(CommonConstants.PLAY_SERVICE_UNOPEN);
                    userPlayAuth.setToken(playAuth.getToken());
                    userPlayAuth.setTryInfo(playAuth.getTryInfo());

                    Map<String, List<String>> vipTypeInfo = playAuth.getVipTypeInfo();
                    if (CollectionUtils.isEmpty(vipTypeInfo)
                            || CollectionUtils.isEmpty(vipTypeInfo.get(String.valueOf(Type_Group_US.BASIC.getCode())))
                            || CollectionUtils.isEmpty(vipTypeInfo.get(Type_Group_US.ADD_ON.getCode()))) {
                        chargeType = ChargeTypeConstants.chargePolicy.CHARGE_NO_COPYRIGTH;
                    }
                }
            }
        } else {
            // 异常鉴权，可能是切服，返回鉴权通过，正常观看，但没有token
            userPlayAuth.setStatus(CommonConstants.PLAY_SERVICE_OPEN);
            log.warn(logPrefix + ": play auth return empty data, set play service on ");
        }

        return chargeType;

    }

    private Integer setUserPlayInfo4LecomUS(VideoDto videoPlay, AlbumMysqlTable albumMysqlTable, String storePath,
            UserPlayAuth4LecomUS userPlayAuth, String playStream, Integer chargeType, Integer model,
            Boolean isFromCntv, Boolean isFromCibn, Integer pricePackageType, String logPrefix, CommonParam commonParam) {
        if (albumMysqlTable == null) {
            return ChargeTypeConstants.chargePolicy.FREE;
        }
        BossTpResponse<ValidateServiceTp> playAuthResponse = this.facadeTpDao.getVipTpDao().validateVideoPlayService(
                commonParam.getUserId(), String.valueOf(albumMysqlTable.getId()), storePath, commonParam, -1);

        return parsePlayAuth(chargeType, logPrefix, userPlayAuth, playAuthResponse, commonParam);
    }

    public Integer parsePlayAuth(Integer chargeType, String logPrefix, UserPlayAuth4LecomUS userPlayAuth,
            BossTpResponse<ValidateServiceTp> playAuthResponse, CommonParam commonParam) {
        if (playAuthResponse != null) {
            if (playAuthResponse.getCode() == null || 0 != playAuthResponse.getCode()
                    || playAuthResponse.getData() == null) {
                // 异常鉴权，boss服务异常，返回鉴权通过，正常观看，但没有token
                userPlayAuth.setStatus(CommonConstants.PLAY_SERVICE_OPEN);
                log.warn(logPrefix + ": play auth return illegal data, set play service on ");
            } else {
                // 正常鉴权
                ValidateServiceTp playAuth = playAuthResponse.getData();
                if (playAuth.getStatus() == null) {
                    // 非法数据，默认正常
                    userPlayAuth.setStatus(CommonConstants.PLAY_SERVICE_OPEN);
                    log.warn(logPrefix + ": play auth return illegal data[null 'status'], set play service on ");
                } else if (CommonConstants.PLAY_SERVICE_OPEN == playAuth.getStatus()) {
                    // 鉴权通过，正常播放
                    userPlayAuth.setStatus(CommonConstants.PLAY_SERVICE_OPEN);
                    userPlayAuth.setToken(playAuth.getToken());
                } else {
                    // 鉴权不过，试看
                    userPlayAuth.setStatus(CommonConstants.PLAY_SERVICE_UNOPEN);
                    userPlayAuth.setToken(playAuth.getToken());
                    userPlayAuth.setTryInfo(playAuth.getTryInfo());
                    userPlayAuth.setUinfo(playAuth.getUinfo());

                    Map<String, List<String>> vipTypeInfo = playAuth.getVipTypeInfo();
                    if (CollectionUtils.isEmpty(vipTypeInfo)
                            || (CollectionUtils.isEmpty(vipTypeInfo.get(String.valueOf(Type_Group_US.BASIC.getCode()))) && CollectionUtils
                                    .isEmpty(vipTypeInfo.get(String.valueOf(Type_Group_US.ADD_ON.getCode()))))) {
                        chargeType = ChargeTypeConstants.chargePolicy.CHARGE_NO_COPYRIGTH;
                    }

                    /**
                     * 获取会员详情
                     */
                    List<String> vipIds = playAuth.getVipInfo();
                    // 对于此用户来说，可以试看的Product map
                    Map<Integer, Vip> canTrialProductMap = new HashMap<Integer, Vip>();

                    if (!CollectionUtils.isEmpty(vipIds)) {
                        // 批量获取会员详情
                        BossTpResponse<List<Vip>> vipInfoResponse = this.facadeTpDao.getVipTpDao().getVipListByIds(
                                vipIds, commonParam);

                        if (vipInfoResponse != null && vipInfoResponse.getData() != null
                                && vipInfoResponse.getData().size() > 0) {
                            // 会员历史map
                            Map<Integer, SubscribeInfo> vipInfoMap = null;

                            for (Vip vip : vipInfoResponse.getData()) {
                                // 判断会员是否支持试看
                                if (vip != null && vip.getTrialData() != null
                                        && vip.getTrialData().getTrialDuration() != null
                                        && vip.getTrialData().getTrialDuration() > 0) {
                                    // 获取会员历史map
                                    if (vipInfoMap == null) {
                                        vipInfoMap = vipMetadataService.getVipInfoV2(false,
                                                commonParam);
                                    }
                                    // 判断用户是否订阅过此会员(包括领取过试用)
                                    if (vipInfoMap != null) {
                                        if (vipInfoMap.get(vip.getId()) != null) {
                                            continue;
                                        } else {
                                            canTrialProductMap.put(vip.getId(), vip);
                                        }
                                    } else {
                                        canTrialProductMap.put(vip.getId(), vip);
                                    }
                                }
                            }
                        }
                    }

                    List<Integer> packageIds = playAuth.getPackageInfo();

                    if (!CollectionUtils.isEmpty(packageIds)) {
                        List<PackageInfoDto> packageInfo = new ArrayList<PackageInfoDto>();
                        BossTpResponse<List<VipPackage>> packagesInfoResponse = this.facadeTpDao.getVipTpDao()
                                .getPackageByIds(packageIds, commonParam);

                        if (packagesInfoResponse != null && packagesInfoResponse.getData() != null
                                && packagesInfoResponse.getData().size() > 0) {
                            for (VipPackage vipPackage : packagesInfoResponse.getData()) {
                                PackageInfoDto packageInfoDto = vipMetadataService
                                        .parseVipPackage(vipPackage, VipPackage.TERMINAL_TV, commonParam);
                                if (packageInfoDto != null) {
                                    // 如果是可以试用的包就add 到最前面(客户端目前只显示一个
                                    // package，让他们取第一个，二期方案的时候可能是个list，所以保留这个list的结构)
                                    if (canTrialProductMap.containsKey(packageInfoDto.getProductId())) {
                                        // 修改试用单位
                                        Vip vip = canTrialProductMap.get(packageInfoDto.getProductId());
                                        vip.getTrialData().setTrialField(
                                                VipConstants.getTrialField(vip.getTrialData().getTrialField(), vip
                                                        .getTrialData().getTrialDuration(), commonParam));

                                        packageInfoDto.setTrialData(vip.getTrialData());
                                        packageInfo.add(0, packageInfoDto);
                                    } else {
                                        packageInfo.add(packageInfoDto);
                                    }
                                }
                            }
                        }

                        userPlayAuth.setPackageInfo(packageInfo);
                    } else {// 没配置付费包的，免费看
                        chargeType = ChargeTypeConstants.chargePolicy.FREE;
                    }

                }
            }
        } else {
            // 异常鉴权，可能是切服，返回鉴权通过，正常观看，但没有token
            userPlayAuth.setStatus(CommonConstants.PLAY_SERVICE_OPEN);
            log.warn(logPrefix + ": play auth return empty data, set play service on ");
        }

        return chargeType;
    }

    /**
     * 未登录情况下的播放鉴权处理
     * @param videoPlay
     * @param albumMysqlTable
     * @param chargeType
     * @param model
     * @param wcode
     * @param logPrefix
     * @param commonParam
     * @return
     */
    private Integer setUserPlayInfo4UnLogin(VideoDto videoPlay, AlbumMysqlTable albumMysqlTable,
            VideoMysqlTable videoMysqlTable, Integer chargeType, Integer model, String wcode, String logPrefix,
            CommonParam commonParam, String storePath, String validDate) {
        Integer payType = 0;// 默认免费， 0--免费，1--单点，2--全频道付费，3--非全频道付费
        // 未登录，此处不能将两个条件放在一起，原因是两个if的else分支不一样
        Integer isCharge = 0;// 专辑视频是否收费,0--免费，1--收费
        // 2016-05-06，播放接口vip字段设置为当前用户真实会员信息，未登录按非会员处理
        videoPlay.setVip(0);
        if (chargeType == ChargeTypeConstants.chargePolicy.CHARGE_YUAN_XIAN && albumMysqlTable != null) {
            if (TerminalUtil.isTvodScope(commonParam)) { // new
                PlayAuthV2 userPlayAuth = null, userPlayAuthTmp;
                BossTpResponse<ValidateServiceTp> validateServiceTpBossTpResponse = null;
                if (false && null != this.sessionCache && null != videoPlay
                        && StringUtil.isNotBlank(videoPlay.getVideoId())) { // 分端付费针对视频
                    validateServiceTpBossTpResponse = this.facadeTpDao.getVipTpDao().validateVideoPlayServiceV2(
                            commonParam.getUserId(), videoPlay.getVideoId(), storePath, commonParam, 2);
                } else {
                    validateServiceTpBossTpResponse = this.facadeTpDao.getVipTpDao()
                            .validateVideoPlayService(commonParam.getUserId(), String.valueOf(albumMysqlTable.getId()),
                                    storePath, commonParam, 2);
                }

                userPlayAuthTmp = this.parsePlayAuthV2(validateServiceTpBossTpResponse, commonParam, videoPlay);
                if (userPlayAuthTmp != null) {// 不为空才判断是否单点
                    if (userPlayAuth == null) {
                        userPlayAuth = new PlayAuthV2();
                    }
                    userPlayAuth.copyProperties(userPlayAuthTmp);
                }
                if (userPlayAuth != null) {// 不为空才判断是否单点
                    String price = null;
                    if (null != userPlayAuth.getVodInfo() && null != userPlayAuth.getVodInfo().getVideo()) {
                        isCharge = (null != userPlayAuth.getVodInfo().getVideo()) ? userPlayAuth.getVodInfo()
                                .getVideo().getIsCharge() : 0;
                        if (1 == isCharge && 0 == userPlayAuth.getVodInfo().getVideo().getChargeType()
                                && 0 == userPlayAuth.getVodInfo().getVideo().getIsSupportTicket()) {
                            /*
                             * 设置单点价格
                             */
                            if (userPlayAuth.getVodInfo().getChargeTerminalPrice() != null) {
                                if (MmsDataUtil.existPayPlatform(this.sessionCache)) {
                                    price = userPlayAuth.getVodInfo().getChargeTerminalPrice()
                                            .get(MmsDataUtil.getPayPlatform(this.sessionCache)); // 从map中获取tv端的价格
                                } else {
                                    price = userPlayAuth.getVodInfo().getChargeTerminalPrice()
                                            .get(BossTerminalType.TV.getCode().toString()); // 从map中获取tv端的价格
                                }
                                if (StringUtil.isNotBlank(price)) {
                                    videoPlay.setPrice(price);
                                }

                                if (TerminalUtil.isTvodScope(commonParam) && StringUtil.isNotBlank(price)) {
                                    Float curPrice = Float.parseFloat(price);
                                    String memberPrice = userPlayAuth.getVodInfo().getCharge().getMemberPrice();
                                    if (userPlayAuth.getVodInfo().getCharge().getMemberDiscounts() == 1) {
                                        if (StringUtil.isNotBlank(memberPrice)) {
                                            curPrice = Float.parseFloat(memberPrice);
                                        } else {
                                            curPrice /= 2;
                                        }
                                    }
                                    videoPlay.setCurPrice((new DecimalFormat("0.00")).format(curPrice));
                                }
                            }
                            /*
                             * 设置单点有效天数
                             */
                            if (userPlayAuth.getVodInfo().getCharge() != null) {
                                Integer validTime = userPlayAuth.getVodInfo().getCharge().getValidTime();
                                if (validTime != null) {
                                    videoPlay.setExpiredTime(validTime * 24); // 转换成小时
                                }
                            }
                            /*
                             * 设置single(是否单点)及付费类型payType
                             */
                            if (userPlayAuth.getVodInfo().getVideo() != null) {
                                Integer chargeTypeTmp = userPlayAuth.getVodInfo().getVideo().getChargeType();
                                boolean single = this.singleFlag(chargeTypeTmp, commonParam.getWcode(), price);
                                if (single) {
                                    videoPlay.setPayType(1); // 设置成1:表示单点
                                } else if (chargeTypeTmp != null) { // 非单点逻辑处理
                                    if (chargeTypeTmp == 0 || chargeTypeTmp == 1 || chargeTypeTmp == 2) { // 皆不是真正的单点,故设置成2(非1值)
                                        videoPlay.setPayType(2);
                                    } else {
                                        videoPlay.setPayType(chargeTypeTmp);
                                    }

                                }
                                chargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_VIP;
                            }
                        }
                    }
                }
            } else { // old
                GetAlbumChargeInfoTpResponse res = this.facadeTpDao.getVideoTpDao().getAlbumChargeInfo(
                        albumMysqlTable.getId());
                if (res != null) {
                    isCharge = res.getIscharge() == null ? 0 : res.getIscharge();
                    Integer charType = res.getChargetype();
                    videoPlay.setPayType(charType);
                    Map<String, String> chargeMap = res.getChargeflatform();
                    String price = "0";
                    String platform = MmsDataUtil.getPayPlatform(commonParam.getP_devType());
                    if (!CollectionUtils.isEmpty(chargeMap) && StringUtil.isNotBlank(platform)
                            && StringUtils.isNotEmpty(chargeMap.get(platform))) {// TV--141007
                        price = chargeMap.get(platform);
                        if (StringUtil.toFloat(price, 0.0F) > 0.0F) {// 首先价格大于0
                            if (ProductLineConstants.WCODE.LETV_HK.equals(wcode)) {
                                if (charType != null && charType == 0) {
                                    payType = 1;
                                    videoPlay.setPayType(1);
                                }
                            } else {// 非香港地区不用判断chargeType是否为0，即BOSS定义为单点
                                payType = 1;
                                videoPlay.setPayType(1);
                            }
                        }
                    }
                    if (payType != 1 && charType != null && charType <= 1) {// 不是真正的单点，做一步容错
                        videoPlay.setPayType(2);
                    }
                    videoPlay.setPrice(this.getPrice(price));
                    // 设置单点付费有效时长
                    Integer validDays = res.getValidDays();
                    if (validDays != null && validDays > 0) {
                        videoPlay.setExpiredTime(24 * validDays);
                    }
                    if (model != 1) {
                        if (isCharge == 1 && payType == 1) {// 收费且价格不为0
                            videoPlay.setIfCharge(String.valueOf(isCharge));
                            videoPlay.setPayType(1);
                            log.info(logPrefix + "_isCharge" + isCharge);
                        } else {
                            /*
                             * 要考虑付费频道，所以此时非单点还不能填充数据
                             * chargeType =
                             * ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM;
                             * videoPlay.setIfCharge(ChargeTypeConstants.NOT_CHARGE
                             * )
                             * ;
                             * videoPlay.setPayType(0);
                             * log.info(logPrefix + "_login:" + login +
                             * "_isCharge:"
                             * + isCharge);
                             */
                        }
                    }
                } else {
                    log.info(logPrefix + ",getAlbumChargeInfo data failure.");
                }
            }
        }
        if (isCharge == 0
                || !VideoCommonUtil.isCharge(videoMysqlTable.getPay_platform(), videoMysqlTable.getPlay_platform(),
                        commonParam.getP_devType())) {
            // 免费专辑或者免费视频
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
                List<PayChannel> payChannelList = tpResponse.getData();
                String category = albumMysqlTable != null ? String.valueOf(albumMysqlTable.getCategory()) : null;
                if (category == null) {
                    category = videoMysqlTable != null ? String.valueOf(videoMysqlTable.getCategory()) : null;
                }
                if (category != null && !CollectionUtils.isEmpty(payChannelList)) {
                    for (PayChannel payChannel : payChannelList) {
                        if (category.equals(payChannel.getChannelId())) {
                            Integer ifCh = payChannel.getIsCharge();
                            if (ifCh != null && ifCh == 1) {
                                payType = 2;// 全频道付费，仅会员逻辑
                            } else {
                                payType = 3;// 非全频道付费
                            }
                            String price = payChannel.getPrice();
                            videoPlay.setPrice(this.getPrice(price));
                            Integer ct = payChannel.getChargeType();
                            if (ct != null) {
                                if (ct == 0) {// 客户端认为仅单点也是会员且单点，所以此步要兼容
                                    ct = 1;
                                }
                                videoPlay.setPayType(ct);
                                if (ct == 1) {
                                    if (StringUtils.isBlank(price) || StringUtil.toFloat(price, 0.0F) <= 0.0F) {
                                        // 价格不为0，才是真正的单点
                                        videoPlay.setPayType(2);
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
            if (payType == 2) {// 未登录情况下全频道付费，调整成院线，有的频道不允许试看
                chargeType = ChargeTypeConstants.chargePolicy.CHARGE_YUAN_XIAN;
            } else if (model != 1) {// 调整成降码流播放
                // mode==1为乐视儿童模式，不转码流收费
                chargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM;
                videoPlay.setIfCharge(ChargeTypeConstants.NOT_CHARGE);
                videoPlay.setPlayType(0);
            }
        }

        return chargeType;
    }

    /**
     * 未登录情况下的播放鉴权处理
     * @param videoPlay
     * @param albumMysqlTable
     * @param userPlayAuth
     * @param chargeType
     * @param model
     * @param wcode
     * @param logPrefix
     * @return
     */
    private Integer setUserPlayInfo4UnLoginV2(VideoDto videoPlay, AlbumMysqlTable albumMysqlTable,
            VideoMysqlTable videoMysqlTable, PlayAuthV2 userPlayAuth, Integer chargeType, Integer model, String wcode,
            String logPrefix) {
        return chargeType;
    }

    private Integer setUserPlayInfo4LecomUSUnLogin(VideoDto videoPlay, AlbumMysqlTable albumMysqlTable,
            VideoMysqlTable videoMysqlTable, UserPlayAuth4LecomUS userPlayAuth, Integer chargeType, Integer model,
            String wcode, CommonParam commonParam, String logPrefix) {
        // 2016-05-06，播放接口vip字段设置为当前用户真实会员信息，未登录按非会员处理
        videoPlay.setVip(0);

        BossTpResponse<List<VipPackage>> videoChargePackageResponse = this.facadeTpDao.getVipTpDao()
                .getVideoChargePackage(videoPlay.getId(), commonParam);

        List<PackageInfoDto> packageInfo = null;
        if (videoChargePackageResponse != null && videoChargePackageResponse.isSucceed()) {
            packageInfo = new ArrayList<PackageInfoDto>();
            userPlayAuth.setPackageInfo(packageInfo);
            List<VipPackage> vipPackageList = videoChargePackageResponse.getData();
            for (VipPackage vipPackage : vipPackageList) {
                if (vipPackage != null && vipPackage.getStatus() == VipPackage.STATUS_PUBLISHED) {
                    if (vipPackage.getTerminal() == null || !vipPackage.getTerminal().contains(VipPackage.TERMINAL_TV)) { // 不包含可用终端，则过滤掉
                        continue;
                    }
                    PackageInfoDto packageInfoDto = new PackageInfoDto();
                    packageInfoDto.setId(vipPackage.getId());
                    packageInfoDto.setName(vipPackage.getName());
                    packageInfoDto.setDesc(vipPackage.getDesc());
                    packageInfoDto.setImg(vipPackage.getPic());
                    packageInfoDto.setPrice(vipPackage.getPrice());
                    packageInfoDto.setDuration(vipPackage.getDuration());
                    packageInfoDto.setDurationType(vipPackage.getDurationType());
                    packageInfoDto.setAutoRenew(vipPackage.getAutoRenew());
                    packageInfoDto.setAutoRenewPeriod(vipPackage.getAutoRenewPeriod());
                    packageInfo.add(packageInfoDto);
                }
            }
        }

        if (CollectionUtils.isEmpty(packageInfo)) {
            chargeType = ChargeTypeConstants.chargePolicy.CHARGE_NO_COPYRIGTH;
        }

        return chargeType;
    }

    /**
     * 从防盗链获得媒资视频文件播放信息
     */
    public MmsStore getMmsFilePlayInfo(Long videoId, Long pid, String videoStreams, String midStreams,
            String playStream, String clientIp, Boolean isMarlin, Integer model, String actionType, VideoDto videoPlay,
            CommonParam commonParam) {
        int splatId = VideoUtil.getSplatId(model, commonParam);
        if ("download".equals(actionType)) { // 下载为502
            splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_TV_COMMON;
        }
        playStream = VideoUtil.transPlay6M2Play3M(playStream, videoStreams, pid);
        String vType = VideoUtil.getVType(playStream, isMarlin, commonParam.getTerminalSeries());
        if ("4k_dv".equals(playStream)) {
            // playStream = "dolby_vision_4k";
            vType = "252";
        }
        Boolean expectTS = VideoUtil.expectTS(playStream, isMarlin);

        // 2016-10-19,tv 2.9.20 新增需求 ，特殊码流使用何种播放媒介，cde和防盗链都不想指定，先放在服务端做
        if (expectTS) {
            videoPlay.setPlayMediaFormat(VideoConstants.VIDEO_PLAY_MEDIA_FORMAT_EXT_M3U8_DEFAULT);
        } else {
            videoPlay.setPlayMediaFormat(VideoConstants.VIDEO_PLAY_MEDIA_FORMAT_MEDIATYPE_MP4_DEFAULT);
        }

        Long mid = VideoUtil.getMidByStream(pid, playStream, videoStreams, midStreams);

        MmsStore mmsStore = this.facadeTpDao.getVideoTpDao().getMmsPlayInfo(clientIp, videoId, mid, vType, expectTS,
                splatId, commonParam.getMac(), commonParam.getBsChannel(), commonParam.getBroadcastId());

        return mmsStore;
    }

    public MmsStore getMmsFilePlayInfoVCache(Long videoId, Long pid, String videoStreams, String midStreams,
            String playStream, String clientIp, Boolean isMarlin, Integer model, String actionType, VideoDto videoPlay,
            CommonParam commonParam) {
        int splatId = VideoUtil.getSplatId(model, commonParam);
        if ("download".equals(actionType)) { // 下载为502
            splatId = VideoTpConstant.CDE_VIDEO_PLAY_SPLATID_LETV_TV_COMMON;
        }
        playStream = VideoUtil.transPlay6M2Play3M(playStream, videoStreams, pid);
        String vType = VideoUtil.getVType(playStream, isMarlin, commonParam.getTerminalSeries());
        if ("4k_dv".equals(playStream)) {
            // playStream = "dolby_vision_4k";
            vType = "252";
        }
        Boolean expectTS = VideoUtil.expectTS(playStream, isMarlin);

        // 2016-10-19,tv 2.9.20 新增需求 ，特殊码流使用何种播放媒介，cde和防盗链都不想指定，先放在服务端做
        if (expectTS) {
            videoPlay.setPlayMediaFormat(VideoConstants.VIDEO_PLAY_MEDIA_FORMAT_EXT_M3U8_DEFAULT);
        } else {
            videoPlay.setPlayMediaFormat(VideoConstants.VIDEO_PLAY_MEDIA_FORMAT_MEDIATYPE_MP4_DEFAULT);
        }
        Long mid = VideoUtil.getMidByStream(pid, playStream, videoStreams, midStreams);
        long begin = System.currentTimeMillis();
        MmsStore mmsStore = this.facadeCacheDao.getVideoCacheDao().getTrailerMmsStore(videoId, mid, vType, expectTS,
                splatId, commonParam.getBsChannel(), commonParam.getBroadcastId(), commonParam);
        if (mmsStore != null) {
            log.info("getMmsFilePlayInfoVCache time " + (System.currentTimeMillis() - begin));
            return mmsStore;
        }
        mmsStore = this.facadeTpDao.getVideoTpDao().getMmsPlayInfo(clientIp, videoId, mid, vType, expectTS, splatId,
                commonParam.getMac(), commonParam.getBsChannel(), commonParam.getBroadcastId());
        if (mmsStore != null) {
            this.facadeCacheDao.getVideoCacheDao().setTrailerMmsStore(videoId, mid, vType, expectTS, splatId,
                    commonParam.getBsChannel(), commonParam.getBroadcastId(), mmsStore);
        }
        log.info("getMmsFilePlayInfoVCache 2222 time " + (System.currentTimeMillis() - begin));
        return mmsStore;
    }

    /**
     * 从防盗链获得媒资视频文件播放信息(For India)
     */
    public MmsStore getMmsFilePlayInfo4India(Long videoId, Long pid, Long mid, String playStream, Boolean isMarlin,
            String userName, Integer model, CommonParam commonParam) {
        int splatId = VideoUtil.getSplatId(model, commonParam);

        String vType = VideoUtil.getVType(playStream, isMarlin, commonParam.getTerminalSeries());
        Boolean expectTS = VideoUtil.expectTS(playStream, isMarlin);

        MmsStore mmsStore = this.facadeTpDao.getVideoTpDao().getMmsPlayInfo(commonParam.getClientIp(), videoId, mid,
                vType, expectTS, splatId, commonParam.getMac(), commonParam.getBsChannel(),
                commonParam.getBroadcastId());

        return mmsStore;
    }

    private boolean setVideoPlayFromMmsFile(MmsStore mmsStore, MmsFile mmsFile, VideoDto videoPlay, Long pid,
            String videoStreams, String playStream, Boolean login, UserPlayAuth userPlayAuth, Boolean isMarlin,
            String audioId, Integer channelId, String langType, String kpbsType, CommonParam commonParam) {
        boolean needTips = false;
        if (mmsFile != null) {
            if (!LocaleConstant.Wcode.IN.equalsIgnoreCase(commonParam.getWcode())) {
                // not India
                needTips = this.setVideoSubtitleAudioValue(videoPlay, mmsStore, mmsFile, commonParam.getWcode(),
                        channelId, langType, kpbsType, commonParam);
                AudioTrackDto audioTrackDto = videoPlay.getDefaultAudioTrack();
                if (audioTrackDto != null) {// 默认音轨id
                    audioId = StringUtils.trimToEmpty(audioTrackDto.getAudioId());
                } else {
                    audioId = "";
                }
            }
            if (mmsFile.getGdur() != null) {
                videoPlay.setDuration(mmsFile.getGdur() * 1000L);// 播放视频片长（毫秒）
            }
            if (mmsFile.getGbr() != null) {
                videoPlay.setCurrentStreamKps(mmsFile.getGbr());// 码率
            } else {
                videoPlay.setCurrentStreamKps(LetvStreamCommonConstants.getMbps(playStream));// 码率(默认)
            }
            videoPlay.setVtype(mmsFile.getVtype());// 把vtype返回出去
            videoPlay.setPlayUrl(VideoUtil.getVIPUrl(login, userPlayAuth, mmsFile.getMainUrl(), commonParam));
            videoPlay.setBackUrl0(VideoUtil.getVIPUrl(login, userPlayAuth, mmsFile.getBackUrl0(), commonParam));
            videoPlay.setBackUrl1(VideoUtil.getVIPUrl(login, userPlayAuth, mmsFile.getBackUrl1(), commonParam));
            videoPlay.setBackUrl2(VideoUtil.getVIPUrl(login, userPlayAuth, mmsFile.getBackUrl2(), commonParam));
            videoPlay.setMd5(mmsFile.getMd5());
            videoPlay.setStorePath(mmsFile.getStorePath());

            if (isMarlin) {// DRM使用302跳转不走UTP
                videoPlay.setPlayUrl(videoPlay.getPlayUrl() + "&format=0&expect=1");
                videoPlay.setBackUrl0(videoPlay.getBackUrl0() + "&format=0&expect=1");
                videoPlay.setBackUrl1(videoPlay.getBackUrl1() + "&format=0&expect=1");
                videoPlay.setBackUrl2(videoPlay.getBackUrl2() + "&format=0&expect=1");
                videoPlay.setDrmTokenUrl("http://g3.letv.cn/" + videoPlay.getStorePath().replace(".mpd", ".xml") + "?"
                        + videoPlay.getPlayUrl().split("\\?")[1]);
            }
            if (VideoTpConstant.CIBN_BSCHANNEL_OPERATORS.equals(commonParam.getBsChannel())) {
                videoPlay.setPlayUrl(VideoUtil.getCibnBsChannelUrlWithParam(videoPlay.getPlayUrl(),
                        videoPlay.getVideoId(), audioId));
                videoPlay.setBackUrl0(VideoUtil.getCibnBsChannelUrlWithParam(videoPlay.getBackUrl0(),
                        videoPlay.getVideoId(), audioId));
                videoPlay.setBackUrl1(VideoUtil.getCibnBsChannelUrlWithParam(videoPlay.getBackUrl1(),
                        videoPlay.getVideoId(), audioId));
                videoPlay.setBackUrl2(VideoUtil.getCibnBsChannelUrlWithParam(videoPlay.getBackUrl2(),
                        videoPlay.getVideoId(), audioId));
            } else {
                Integer broadcastId = commonParam.getBroadcastId();
                if ((broadcastId != null) && (CommonConstants.CIBN == broadcastId)) {
                    String terminalApplication = commonParam.getTerminalApplication();
                    videoPlay
                            .setPlayUrl(VideoUtil.getCibnUrlWithParam(DomainMapping.changeDomainByBraodcastId(
                                    videoPlay.getPlayUrl(), broadcastId, terminalApplication), videoPlay.getVideoId(),
                                    audioId));
                    videoPlay
                            .setBackUrl0(VideoUtil.getCibnUrlWithParam(DomainMapping.changeDomainByBraodcastId(
                                    videoPlay.getBackUrl0(), broadcastId, terminalApplication), videoPlay.getVideoId(),
                                    audioId));
                    videoPlay
                            .setBackUrl1(VideoUtil.getCibnUrlWithParam(DomainMapping.changeDomainByBraodcastId(
                                    videoPlay.getBackUrl1(), broadcastId, terminalApplication), videoPlay.getVideoId(),
                                    audioId));
                    videoPlay
                            .setBackUrl2(VideoUtil.getCibnUrlWithParam(DomainMapping.changeDomainByBraodcastId(
                                    videoPlay.getBackUrl2(), broadcastId, terminalApplication), videoPlay.getVideoId(),
                                    audioId));
                } else {
                    videoPlay.setPlayUrl(VideoUtil.getLetvUrlWithParam(videoPlay.getPlayUrl(), videoPlay.getVideoId(),
                            audioId));
                    videoPlay.setBackUrl0(VideoUtil.getLetvUrlWithParam(videoPlay.getBackUrl0(),
                            videoPlay.getVideoId(), audioId));
                    videoPlay.setBackUrl1(VideoUtil.getLetvUrlWithParam(videoPlay.getBackUrl1(),
                            videoPlay.getVideoId(), audioId));
                    videoPlay.setBackUrl2(VideoUtil.getLetvUrlWithParam(videoPlay.getBackUrl2(),
                            videoPlay.getVideoId(), audioId));
                }
            }
        }
        return needTips;
    }

    private boolean setVideoPlayFromMmsFile(MmsStore mmsStore, MmsFile mmsFile, VideoDto videoPlay, String playStream,
            PlayAuthV2 userPlayAuth, Boolean isMarlin, String audioId, Integer channelId, String langType,
            String kpbsType, CommonParam commonParam) {
        boolean needTips = false;

        if (mmsFile != null) {
            if (!LocaleConstant.Wcode.IN.equalsIgnoreCase(commonParam.getWcode())) {
                // not India
                needTips = this.setVideoSubtitleAudioValue(videoPlay, mmsStore, mmsFile, commonParam.getWcode(),
                        channelId, langType, kpbsType, commonParam);
                AudioTrackDto audioTrackDto = videoPlay.getDefaultAudioTrack();
                if (audioTrackDto != null) {// 默认音轨id
                    audioId = StringUtils.trimToEmpty(audioTrackDto.getAudioId());
                } else {
                    audioId = "";
                }
            }
            if (mmsFile.getGdur() != null) {
                videoPlay.setDuration(mmsFile.getGdur() * 1000L);// 播放视频片长（毫秒）
            }
            if (mmsFile.getGbr() != null) {
                videoPlay.setCurrentStreamKps(mmsFile.getGbr());// 码率
            } else {
                videoPlay.setCurrentStreamKps(LetvStreamCommonConstants.getMbps(playStream));// 码率(默认)
            }
            videoPlay.setVtype(mmsFile.getVtype());// 把vtype返回出去
            videoPlay.setPlayUrl(VideoUtil.getVIPUrl(userPlayAuth, mmsFile.getMainUrl()));
            videoPlay.setBackUrl0(VideoUtil.getVIPUrl(userPlayAuth, mmsFile.getBackUrl0()));
            videoPlay.setBackUrl1(VideoUtil.getVIPUrl(userPlayAuth, mmsFile.getBackUrl1()));
            videoPlay.setBackUrl2(VideoUtil.getVIPUrl(userPlayAuth, mmsFile.getBackUrl2()));
            videoPlay.setMd5(mmsFile.getMd5());
            videoPlay.setStorePath(mmsFile.getStorePath());

            if (isMarlin) {// DRM使用302跳转不走UTP
                videoPlay.setPlayUrl(videoPlay.getPlayUrl() + "&format=0&expect=1");
                videoPlay.setBackUrl0(videoPlay.getBackUrl0() + "&format=0&expect=1");
                videoPlay.setBackUrl1(videoPlay.getBackUrl1() + "&format=0&expect=1");
                videoPlay.setBackUrl2(videoPlay.getBackUrl2() + "&format=0&expect=1");
                videoPlay.setDrmTokenUrl("http://g3.letv.cn/" + videoPlay.getStorePath().replace(".mpd", ".xml") + "?"
                        + videoPlay.getPlayUrl().split("\\?")[1]);
            }
            if (VideoTpConstant.CIBN_BSCHANNEL_OPERATORS.equals(commonParam.getBsChannel())) {
                videoPlay.setPlayUrl(VideoUtil.getCibnBsChannelUrlWithParam(videoPlay.getPlayUrl(),
                        videoPlay.getVideoId(), audioId));
                videoPlay.setBackUrl0(VideoUtil.getCibnBsChannelUrlWithParam(videoPlay.getBackUrl0(),
                        videoPlay.getVideoId(), audioId));
                videoPlay.setBackUrl1(VideoUtil.getCibnBsChannelUrlWithParam(videoPlay.getBackUrl1(),
                        videoPlay.getVideoId(), audioId));
                videoPlay.setBackUrl2(VideoUtil.getCibnBsChannelUrlWithParam(videoPlay.getBackUrl2(),
                        videoPlay.getVideoId(), audioId));
            } else {
                Integer broadcastId = commonParam.getBroadcastId();
                if ((broadcastId != null) && (CommonConstants.CIBN == broadcastId)) {
                    String terminalApplication = commonParam.getTerminalApplication();
                    videoPlay.setPlayUrl(VideoUtil.rebuildPlayUrl(VideoUtil.getCibnUrlWithParam(DomainMapping
                            .changeDomainByBraodcastId(videoPlay.getPlayUrl(), broadcastId, terminalApplication),
                            videoPlay.getVideoId(), audioId), commonParam));
                    videoPlay.setBackUrl0(VideoUtil.rebuildPlayUrl(VideoUtil.getCibnUrlWithParam(DomainMapping
                            .changeDomainByBraodcastId(videoPlay.getBackUrl0(), broadcastId, terminalApplication),
                            videoPlay.getVideoId(), audioId), commonParam));
                    videoPlay.setBackUrl1(VideoUtil.rebuildPlayUrl(VideoUtil.getCibnUrlWithParam(DomainMapping
                            .changeDomainByBraodcastId(videoPlay.getBackUrl1(), broadcastId, terminalApplication),
                            videoPlay.getVideoId(), audioId), commonParam));
                    videoPlay.setBackUrl2(VideoUtil.rebuildPlayUrl(VideoUtil.getCibnUrlWithParam(DomainMapping
                            .changeDomainByBraodcastId(videoPlay.getBackUrl2(), broadcastId, terminalApplication),
                            videoPlay.getVideoId(), audioId), commonParam));
                } else {
                    videoPlay.setPlayUrl(VideoUtil.getLetvUrlWithParam(videoPlay.getPlayUrl(), videoPlay.getVideoId(),
                            audioId));
                    videoPlay.setBackUrl0(VideoUtil.getLetvUrlWithParam(videoPlay.getBackUrl0(),
                            videoPlay.getVideoId(), audioId));
                    videoPlay.setBackUrl1(VideoUtil.getLetvUrlWithParam(videoPlay.getBackUrl1(),
                            videoPlay.getVideoId(), audioId));
                    videoPlay.setBackUrl2(VideoUtil.getLetvUrlWithParam(videoPlay.getBackUrl2(),
                            videoPlay.getVideoId(), audioId));
                }
            }
        }
        return needTips;
    }

    /**
     * 处理第三方服务
     * @param videoPlay
     * @param videoId
     * @param videoName
     */
    public void handleTPService(VideoDto videoPlay, Long videoId, String videoName) {

        // 1、声纹服务
        // videoPlay.setSoundInkCodeUrl(this.getSoundInkService(videoId,
        // videoName));

    }

    /**
     * 声纹服务
     * @param videoId
     * @param videoName
     * @return
     */
    public String getSoundInkService(Long videoId, String videoName) {
        SoundInkCodeTp soundInkCodeTp = this.facadeTpDao.getSoundInkTpDao().getSoundInkCode(videoId, videoName);
        String soundInkCodeUrl = null;
        if (soundInkCodeTp != null) {
            soundInkCodeUrl = soundInkCodeTp.getDownload() + "?app_key=4f7ad46a881a46ca7366e5c47a4dbfcf&audio_type=mp3";
        }

        return soundInkCodeUrl;
    }

    /**
     * 播放视频附加服务
     * @param videoPlay
     * @param videoMysqlTable
     * @return
     */
    public void handleAdditionalService(VideoDto videoPlay, VideoMysqlTable videoMysqlTable, CommonParam commonParam) {

        // 1、水印
        // if (1 == videoMysqlTable.getLogonum()) {
        //
        // }

        // 2、视点图
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) {
            videoPlay.setViewPic(this.getVideoViewPicUrl(videoMysqlTable.getVideoPic()));
        }
        // 3、播放视频前是否播放广告
        // videoPlay.setPlayAd(videoPlay.getPlayType() == 1 ? 0 : 1);
    }

    /**
     * 从VRS获得视频的基本信息
     * @param videoMysqlTable
     * @param albumMysqlTable
     * @param videoPlay
     * @param playStream
     * @return
     */
    public VideoDto getVideoPlayFromVRS(VideoMysqlTable videoMysqlTable, AlbumMysqlTable albumMysqlTable,
            VideoDto videoPlay, String playStream, Integer model, CommonParam commonParam) {
        if ("4k_dv".equals(playStream)) {
            playStream = "dolby_vision_4k";
        }
        videoPlay.setCategoryId(videoMysqlTable.getCategory());
        videoPlay.setVideoTypeId(videoMysqlTable.getVideo_type());
        if (videoMysqlTable.getVideo_attr() != null && videoMysqlTable.getVideo_attr() == 1) {
            videoPlay.setPositive(true);
        } else {
            videoPlay.setPositive(false);
        }

        videoPlay.setOrderInAlbum(videoMysqlTable.getPorder());
        videoPlay.setEpisode(videoMysqlTable.getEpisode());
        videoPlay.setPlayPlatform(videoMysqlTable.getPlay_platform());

        // 是否按照综艺样式展示
        videoPlay.setVarietyShow(VideoUtil.isVarietyShow(albumMysqlTable, commonParam) ? 1 : 0);

        // 在更新剧集三集之内的预告片做特殊处理
        if (videoPlay.getPositive()
                || (StringUtils.isNotEmpty(videoMysqlTable.getEpisode())
                        && videoMysqlTable.getEpisode().matches("\\d*") && albumMysqlTable != null
                        && albumMysqlTable.getFollownum() != null
                        && (Long.valueOf(videoMysqlTable.getEpisode()) <= (albumMysqlTable.getFollownum() + 3))
                        && (Long.valueOf(videoMysqlTable.getEpisode()) > Long.valueOf(albumMysqlTable.getFollownum()))
                        && videoMysqlTable.getVideo_type() != null && videoMysqlTable.getVideo_type() == VideoConstants.VideoType.YU_GAO_PIAN)) {
            videoPlay.setPreType(0);
        } else if (!videoPlay.getPositive()) { // 解决下拉列表无限循环bug，设置为非有效的预告片
            videoPlay.setPreType(1);
        }

        // 是否收费
        if (videoMysqlTable != null
                && VideoCommonUtil.isCharge(videoMysqlTable.getPay_platform(), videoMysqlTable.getPlay_platform(),
                        commonParam.getP_devType())) {
            videoPlay.setIfCharge(ChargeTypeConstants.CHARGE);
        } else {
            videoPlay.setIfCharge(ChargeTypeConstants.NOT_CHARGE);
        }

        // 设置视频片头、片尾时间
        VideoHot vh = VideoUtil.getHeadTailInfo(videoMysqlTable.getCategory(), videoMysqlTable.getBtime(),
                videoMysqlTable.getEtime(), model);
        videoPlay.setVideoHeadTime(vh.getT());
        videoPlay.setVideoTailTime(vh.getF());

        // DRM标识
        videoPlay.setDrmFlagId(videoMysqlTable.getDrmFlagId());

        // 家长锁功能
        videoPlay.setIsPlayLock(videoMysqlTable.getIsPlayLock());

        // 视频播放内容分级信息
        String contentRatingId = videoMysqlTable.getContentRatingId() == null ? null : String.valueOf(videoMysqlTable
                .getContentRatingId());
        videoPlay.setContentRatingId(contentRatingId);
        videoPlay.setContentRatingValue(videoMysqlTable.getContentRatingValue());
        videoPlay.setContentRatingDesc(videoMysqlTable.getContentRatingDesc());

        // 以下为兼容处理
        // 1、播放记录图片兼容
        if (videoMysqlTable.getPid() == 0 || albumMysqlTable == null) {
            videoPlay.setImg(videoMysqlTable.getPic(400, 300));
        } else {
            videoPlay.setImg(albumMysqlTable.getPic(400, 300));
        }

        // le播放记录图片处理,统一和getPlaylog图片处理逻辑一致
        // 默认图处理（长视频显示专辑图片、短视频或者无专辑视频优先调用视频图，无图则调用用户中心）
        String playlogImg = null;
        Integer categoryId = videoMysqlTable.getCategory();
        if (categoryId == VideoConstants.Category.TV || categoryId == VideoConstants.Category.FILM
                || categoryId == VideoConstants.Category.PARENTING || categoryId == VideoConstants.Category.DFILM
                || categoryId == VideoConstants.Category.CARTOON || categoryId == VideoConstants.Category.VARIETY) {
            playlogImg = getAvailableImg(albumMysqlTable, videoMysqlTable, 400, 225);
        } else {
            playlogImg = getAvailableImg(videoMysqlTable, 400, 225);
        }
        videoPlay.setPlaylogImg(playlogImg);

        // 2、专辑名称兼容
        String albumName = videoMysqlTable.getName_cn();
        if (albumMysqlTable != null) { // 设置专辑基本信息
            albumName = albumMysqlTable.getName_cn();
            videoPlay.setAlbumTvCopyright(1);
            videoPlay.setAreaName(albumMysqlTable.getArea_name());
            videoPlay.setDirector(albumMysqlTable.getDirectory());
            videoPlay.setPoster(albumMysqlTable.getPic(300, 400));
        } else {
            videoPlay.setAlbumTvCopyright(0);
        }
        videoPlay.setAlbumId(videoMysqlTable.getPid() != null ? videoMysqlTable.getPid().toString() : null);
        videoPlay.setAlbumName(albumName);

        // 播放loading页显示名称 -- 需要在 专辑名称兼容之后处理
        videoPlay.setShowName(VideoUtil.getPlayShowName(videoMysqlTable.getCategory(), albumName,
                videoMysqlTable.getName_cn(), videoMysqlTable.getPorder(), playStream,
                videoMysqlTable.getStarring_all(), videoMysqlTable.getVideo_attr(), commonParam, albumMysqlTable));

        return videoPlay;
    }

    public VideoDto getVideoPlayFromVRS4LecomUS(VideoMysqlTable videoMysqlTable, AlbumMysqlTable albumMysqlTable,
            VideoDto videoPlay, String playStream, Integer model, CommonParam commonParam) {
        this.getVideoPlayFromVRS(videoMysqlTable, albumMysqlTable, videoPlay, playStream, model, commonParam);

        if (albumMysqlTable != null) {
            videoPlay.setAlbumPositive(albumMysqlTable.isPositive());
        }

        return videoPlay;
    }

    /**
     * 获取播放记录背景缩略图，优先从album中取，取不到则从video中取
     * @param album
     *            专辑
     * @param video
     *            视频
     * @param width
     *            图片尺寸，宽
     * @param hight
     *            图片尺寸，高
     * @return
     */
    private static String getAvailableImg(AlbumMysqlTable album, VideoMysqlTable video, Integer width, Integer hight) {
        String img = null;
        if (album != null) {
            img = album.getPic(width, hight);
        }
        if (StringUtils.isBlank(img) && video != null) {
            img = video.getPic(width, hight);
        }
        return img;
    }

    /**
     * 获取播放记录背景缩略图，从video中取
     * @param video
     *            视频
     * @param width
     *            图片尺寸，宽
     * @param hight
     *            图片尺寸，高
     * @return
     */
    private static String getAvailableImg(VideoMysqlTable video, Integer width, Integer hight) {
        String img = null;
        if (StringUtils.isBlank(img) && video != null) {
            img = video.getPic(width, hight);
        }
        return img;
    }

    private static String getAvailableImg(AlbumMysqlTable album, Integer width, Integer hight) {
        String img = null;
        if (album != null) {
            img = album.getPic(width, hight);
        }
        return img;
    }

    /**
     * 根据用户权限获得播放类型
     * @param albumMysqlTable
     * @param videoMysqlTable
     * @param userPlayAuth
     * @param videoPlay
     * @param chargeType
     */
    public void getPlayTypeByUserAuth(AlbumMysqlTable albumMysqlTable, VideoMysqlTable videoMysqlTable,
            PlayAuthV2 userPlayAuth, VideoDto videoPlay, Integer chargeType, CommonParam commonParam,
            boolean isThirdParty) {
        /*
         * 判断用户是否被封禁
         */
        if (userPlayAuth != null) {
            Integer userIsForbidden = StringUtil.toInteger(userPlayAuth.getIsForbidden());
            videoPlay.setUserIsForbidden(userIsForbidden);
            if (userIsForbidden != null && userIsForbidden == 1) {// 被封禁需要提示语，客户端用于弹toast
                String msg = MessageUtils.getMessage(VideoConstants.VIDEO_PLAY_USER_ISFORBIDDEN_MSG,
                        commonParam.getLangcode());
                String tips = null;
                // 通用版提示信息不同
                if (ProductLineConstants.LETV_COMMON.equals(commonParam.getTerminalApplication())) {
                    tips = MessageUtils.getMessage(VideoConstants.VIDEO_PLAY_ERROR_TIPS_CN_COMMON,
                            commonParam.getLangcode());
                } else {
                    tips = MessageUtils.getMessage(VideoConstants.VIDEO_PLAY_ERROR_TIPS_CN, commonParam.getLangcode());
                }

                videoPlay.setTipsMessage(msg + ";" + tips);
                userPlayAuth.setStatus(PlayAuthV2.STATUS_UNPASS);
            }
        }

        Integer vip = videoPlay.getVip();
        int playAd = 1;
        // letv_us application no stream charge type
        if (TerminalUtil.isLetvUs(commonParam)) {
            playAd = 0;
            vip = 0;
            if (ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM == chargeType) {
                videoPlay.setIfCharge(ChargeTypeConstants.NOT_CHARGE);
            }
        }
        if (userPlayAuth != null && userPlayAuth.isAuthenticationPass()) {
            chargeType = CommonConstants.PLAY_TYPE.ZHENGCHANG.getValue();
            if (TerminalUtil.isLetvUs(commonParam)) {
                videoPlay.setVip(1);// us version
            }
        } else {// 没有权限的情况下
            if (albumMysqlTable == null) {// 单视频走码流付费
                chargeType = ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM;
            }
        }
        Integer albumType = null;
        if (albumMysqlTable != null) {
            albumType = albumMysqlTable.getAlbum_type();
        }

        Long bossTryPlayTime = videoPlay.getTryPlayTime();// 从boss获取的试看时长
        String tryPlayTipMsg = videoPlay.getTryPlayTipMsg();// 试看时长提示信息
        videoPlay.setTryPlayTime(null);
        videoPlay.setTryPlayTipMsg(null);

        // PlayType 1:正常播放 2:试看播放 3:350免费播放 4:院线试看 5:码流收费
        switch (chargeType) {
        // 4按单点收费
            case ChargeTypeConstants.chargePolicy.CHARGE_BY_VIP:
            case ChargeTypeConstants.chargePolicy.CHARGE_YUAN_XIAN:
                videoPlay.setPlayAd(playAd);
                videoPlay.setVip(vip);
                if (TerminalUtil.isLetvUs(commonParam)) {
                    videoPlay.setTryPlayTime(360000L);
                    videoPlay.setTryPlayTipMsg(MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_TRY_PLAY_SIX_MINUTES,
                            commonParam.getLangcode()));
                } else {
                    this.getTryPlayTime(videoPlay, chargeType, albumType, commonParam.getLangcode());
                }
                videoPlay.setPlayType(chargeType);
                break;
            // 5码流收费逻辑350免费正常播放
            case ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM:
                // 码流收费ifCharge=0
                videoPlay.setIfCharge(ChargeTypeConstants.NOT_CHARGE);
                if (TerminalUtil.isLetvUs(commonParam)) {
                    if (LetvStreamCommonConstants.CODE_NAME_350.equalsIgnoreCase(videoPlay.getCurrentStream())) {
                        chargeType = CommonConstants.PLAY_TYPE.ZHENGCHANG.getValue();
                    } else {
                        videoPlay.setTryPlayTime(360000L);
                        videoPlay.setTryPlayTipMsg(MessageUtils.getMessage(
                                VideoConstants.VIDEO_TIPMSG_TRY_PLAY_SIX_MINUTES, commonParam.getLangcode()));
                    }
                } else {
                    if (isThirdParty) {
                        chargeType = this.thirdPartyStreamCharge(videoPlay.getCurrentStream(), chargeType, albumType,
                                videoPlay, commonParam.getLangcode());
                    } else {
                        chargeType = this.streamCharge(videoPlay.getCurrentStream(), chargeType, albumType, videoPlay,
                                commonParam.getLangcode());
                    }
                }
                videoPlay.setPlayType(chargeType);
                // 和广告SDK现有保持一致，如存在试看时长则禁掉广告
                if (videoPlay.getTryPlayTime() == null)
                    videoPlay.setPlayAd(playAd);
                videoPlay.setVip(vip);
                break;
            // 2按内容收费
            case ChargeTypeConstants.chargePolicy.CHARGE_BY_CONTENT:
                videoPlay.setTryPlayTime(VideoUtil.getTryPlayTime(videoMysqlTable.getCategory(),
                        videoPlay.getVideoTailTime(), videoPlay.getDuration()));
                videoPlay
                        .setTryPlayTipMsg((videoMysqlTable.getCategory() != null && (VideoConstants.Category.FILM == videoMysqlTable
                                .getCategory())) ? MessageUtils.getMessage(
                                VideoConstants.VIDEO_TIPMSG_TRY_PLAY_SIX_MINUTES, commonParam.getLangcode()) : MessageUtils
                                .getMessage(VideoConstants.VIDEO_TIPMSG_FOR_FREE_PLAY_FIRST_EPISODE,
                                        commonParam.getLangcode()));
                // videoPlay.setPlayType(chargeType);
                break;
        default:
            videoPlay.setPlayType(chargeType);
            break;
        }

        if (videoPlay.getTryPlayTime() != null && bossTryPlayTime != null) {
            // boss的试看时长不为空，并且上面逻辑处理的时长也不为空，这时候需要用boss的试看时长替换上面逻辑的试看时长
            videoPlay.setTryPlayTime(bossTryPlayTime);
            videoPlay.setTryPlayTipMsg(tryPlayTipMsg);
        }
    }

    public void getPlayTypeByUserAuth4LecomUS(AlbumMysqlTable albumMysqlTable, VideoMysqlTable videoMysqlTable,
            UserPlayAuth4LecomUS userPlayAuth, VideoDto videoPlay, Boolean login, Integer chargeType,
            CommonParam commonParam) {
        if (userPlayAuth != null) {// 用户是否被封禁
            Integer userIsForbidden = StringUtil.toInteger(userPlayAuth.getIsForbidden());
            videoPlay.setUserIsForbidden(userIsForbidden);
            if (userIsForbidden != null && userIsForbidden == 1) {
                // 被封禁需要提示语，客户端用于弹toast
                String msg = MessageUtils.getMessage(VideoConstants.VIDEO_PLAY_USER_ISFORBIDDEN_MSG,
                        commonParam.getLangcode());
                String tips = MessageUtils.getMessage(VideoConstants.VIDEO_PLAY_ERROR_TIPS_CN,
                        commonParam.getLangcode());
                videoPlay.setTipsMessage(msg + ";" + tips);
                userPlayAuth.setStatus(0);
            }
        }

        if ((userPlayAuth != null) && (userPlayAuth.getStatus() != null)
                && (CommonConstants.PLAY_SERVICE_OPEN == userPlayAuth.getStatus())) {
            chargeType = CommonConstants.PLAY_TYPE.ZHENGCHANG.getValue();
        }

        videoPlay.setPlayType(chargeType);
        // PlayType，1-正常播放，6-会员付费，7--无付费配置，无版权
        switch (chargeType) {
        // 4按单点收费
            case ChargeTypeConstants.chargePolicy.FREE:
                videoPlay.setPlayAd(0);
                videoPlay.setVip(1);
                break;
            case ChargeTypeConstants.chargePolicy.CHARGE_BY_VIP:
                this.getTryPlayTime4LecomUS(videoPlay, userPlayAuth, commonParam.getLangcode());
                videoPlay.setPackageInfo(userPlayAuth.getPackageInfo());
                break;
            default:
                videoPlay.setPlayAd(1);
                videoPlay.setVip(0);
                break;
        }
    }

    /**
     * 码流收费逻辑350免费正常播放
     * @param curStream
     *            当前码流
     * @param chargeType
     *            付费类型
     * @param videoPlay
     *            播放类
     * @param locale
     * @return
     */
    private Integer streamCharge(String curStream, Integer chargeType, Integer albumType, VideoDto videoPlay,
            String locale) {
        if (StringUtils.isNotEmpty(curStream) && "350".equalsIgnoreCase(curStream)) {
            // videoPlay.setPlayAd(1);
            chargeType = CommonConstants.PLAY_TYPE.ZHENGCHANG.getValue();
            // videoPlay.setVip(0);
        } else {
            // 其他码流试看
            // videoPlay.setPlayAd(1);
            // videoPlay.setVip(0);
            this.getTryPlayTime(videoPlay, chargeType, albumType, locale);
        }
        return chargeType;
    }

    private Integer thirdPartyStreamCharge(String curStream, Integer chargeType, Integer albumType, VideoDto videoPlay,
            String locale) {
        if (StringUtils.isNotEmpty(curStream)
                && Arrays.asList(LetvStreamCommonConstants.THIRD_PARTY_CHARGE_STREAM).contains(curStream)) {
            // videoPlay.setPlayAd(1);
            this.getTryPlayTime(videoPlay, chargeType, albumType, locale);
            // videoPlay.setVip(0);
        } else {
            // 其他码流免费看
            // videoPlay.setPlayAd(1);
            // videoPlay.setVip(0);
            chargeType = CommonConstants.PLAY_TYPE.ZHENGCHANG.getValue();
        }
        return chargeType;
    }

    /**
     * 统计数据-上报
     * @param videoPlay
     */
    public void setStatisticData(Integer category, VideoDto videoPlay, Integer vtype) {
        // String static_categoyCode =
        // CategoryIdConstants.STATISTICS_CATEGORY_CODE.get(category);
        // videoPlay.setStatisticsCode(static_categoyCode);
        // 2015-09-07,2.8.9中上报策略修改，不再上报码流，而是上报码流id，产品-刘升才
        // videoPlay.setCurrentStreamOriginal(StreamConstants.CODE_STREAM_MAP.get(vtype));
        videoPlay.setCurrentStreamOriginal(String.valueOf(vtype));
    }

    /**
     * 视点图功能
     * @param vrsVideoPic
     * @return
     */
    public String getVideoViewPicUrl(String vrsVideoPic) {
        if (StringUtils.isNotEmpty(vrsVideoPic)) {
            return vrsVideoPic + "/viewpoint/desc_tv.json";
        }

        return null;
    }

    /**
     * mock an non vip user for get username and userid
     * @param commonParam
     * @return
     */
    private boolean tryMockNotVipAccountLogin(CommonParam commonParam) {
        String logPrefix = "tryMockNotVipAccountLogin_";
        Boolean needMook = null;
        try {
            needMook = this.facadeCacheDao.getUserCacheDao().getNeedMockUserFlag();
        } catch (Exception e) {
            log.info("cbase_get_tryMockNotVipAccountLogin_key:"
                    + CacheContentConstants.VIDEO_PLAY_IS_NEED_MOOK_NOT_VIP_LOGIN_KEY);
        }
        if (needMook == null) {
            needMook = VideoConstants.VIDEO_PLAY_IS_NEED_MOOK_NOT_VIP_LOGIN;
            // RedisUtil.set(RedisConstants.VIDEO_PLAY_IS_NEED_MOOK_NOT_VIP_LOGIN_KEY,
            // needMook);
            this.facadeCacheDao.getUserCacheDao().setNeedMockUserFlag(needMook);
        }

        if (needMook) {
            String username = VideoConstants.VIDEO_PLAY_MOOK_NOT_VIP_LOGIN_USERNAME;
            Long userId = StringUtil.toLong(VideoConstants.VIDEO_PLAY_MOOK_NOT_VIP_LOGIN_USERID, 0L);
            commonParam.setUsername(username);
            commonParam.setUserId(String.valueOf(userId));
            log.info(logPrefix + username + "_" + userId);
            return true;
        }

        return false;
    }

    /**
     * 获取“是否展示暂停广告页面”配置；
     * 先从缓存中获取，如果获取不到，则读取默认值
     * @return
     */
    private Integer getShowPauseAdPageConfig() {
        // Integer config =
        // this.cacheDao.get(CacheContentConstants.VIDEO_SHOW_PLAY_PAUSE_AD_PAGE,
        // Integer.class);
        Integer config = this.facadeCacheDao.getVideoCacheDao().getShowPauseAdPage();
        if (config == null) {
            config = StringUtil.toInteger(VideoConstants.VIDEO_PLAY_SHOW_PAUSE_AD_PAGE_DEFAULT, 1);
        }
        return config;
    }

    /**
     * 获取“是否展示浮层广告”配置；
     * 先从缓存中获取，如果获取不到，则读取默认值
     * @return
     */
    private Integer getPlayFloatAdConfig() {
        // Integer config =
        // this.cacheDao.get(CacheContentConstants.VIDEO_PLAY_FLOAT_AD_POLICY,
        // Integer.class);
        Integer config = this.facadeCacheDao.getVideoCacheDao().getShowPlayFloatAd();
        if (config == null) {
            config = StringUtil.toInteger(VideoConstants.VIDEO_PLAY_FLOAT_AD_POLICY_DEFAULT, 3);
        }
        return config;
    }

    /**
     * 读取卡顿图
     * @param type
     * @return
     */
    private String getTvNonMemberGuideImg(int type) {
        String ImgUrl = null;
        VideoGuideResponse videoGuideResponse = this.facadeTpDao.getVideoTpDao().getTvNonMemberGuideImg();
        if (videoGuideResponse != null && "true".equals(videoGuideResponse.getResult())) {
            List<VideoGuideInfoTpResponse> VideoGuideDaoList = videoGuideResponse.getData();
            for (VideoGuideInfoTpResponse VideoGuideDao : VideoGuideDaoList) {
                Integer userType = VideoGuideDao.getUserType();
                if (userType != null && userType == type) {
                    ImgUrl = VideoGuideDao.getImgUrl();
                }
            }
        }
        return ImgUrl;
    }

    public Response<VideoPlayConsumeGuideDto> getPlayGuideInfo(CommonParam commonParam) {
        Integer resultStatus = 1;
        String errorCode = null;
        String errorMsg = null;
        Response<VideoPlayConsumeGuideDto> response = new Response<VideoPlayConsumeGuideDto>();
        VideoPlayConsumeGuideDto data = null;

        List<VideoPlayConsumeGuideInfoDto> videoGuideList = this.getTvNonMemberGuideList();
        if (!CollectionUtils.isEmpty(videoGuideList)) {
            Map<String, VideoPlayConsumeGuideInfoDto> dataMap = new HashMap<String, VideoPlayConsumeGuideInfoDto>();
            for (VideoPlayConsumeGuideInfoDto guideInfo : videoGuideList) {
                dataMap.put(String.valueOf(guideInfo.getUserType()), guideInfo);
            }

            data = new VideoPlayConsumeGuideDto();
            data.setData(dataMap);
            log.info("getPlayGuideInfo to boss.price.zhifu.nonmember.guide.list, the data return success !");
        } else {
            resultStatus = 0;
            errorCode = VideoConstants.VIDEO_GET_VIDEO_PAY_INFO_ERROR; // 未调用到
            errorMsg = MessageUtils.getMessage(errorCode, commonParam.getLangcode());
            log.info("getPlayGuideInfo to boss.price.zhifu.nonmember.guide.list, the data return is null !");
        }
        response.setResultStatus(resultStatus);
        response.setErrCode(errorCode);
        response.setErrMsg(errorMsg);
        response.setData(data);
        return response;
    }

    public Response<VideoPlayConsumeGuideDto> getPlayGuideInfoV2(Integer deviceType, CommonParam commonParam) {
        Boolean isVip = playService.isVip(null, StringUtil.toLong(commonParam.getUserId()),
                commonParam.getDeviceKey());
        if ((isVip != null && isVip) || (TerminalUtil.isLetvUs(commonParam)) || TerminalUtil.isLetvCommon(commonParam)) {
            // vip user or letv_us or letv_common app no urm activity data
            return new Response<VideoPlayConsumeGuideDto>();
        }
        VideoPlayConsumeGuideDto data = new VideoPlayConsumeGuideDto();
        String showButton = null;
        // 判断开关是否打开
        Integer experimentSwitch = StringUtil.toInteger(ApplicationUtils
                .get(ApplicationConstants.VIDEO_EXPERIMENT_SWITCH));
        if (experimentSwitch != null && experimentSwitch == 1) {
            if (LocaleConstant.Wcode.CN.equalsIgnoreCase(commonParam.getSalesArea())
                    && (TerminalCommonConstant.TERMINAL_APPLICATION_LETV.equalsIgnoreCase(commonParam
                            .getTerminalApplication()) || TerminalCommonConstant.TERMINAL_APPLICATION_MEDIA_CIBN
                            .equalsIgnoreCase(commonParam.getTerminalApplication()))) {
                showButton = UserTpConstant.USER_EXPERIMENT_DEFAULT_VALUE;
                // 获取A/B Test实验数据
                Map<String, Object> experimentInfo = this.facadeTpDao.getUserTpDao().getExperimentInfo(
                        UserTpConstant.USER_EXPERIMENT_APPID_LETV, UserTpConstant.USER_EXPERIMENT_VIDEO_BUY_BUTTON,
                        commonParam);
                if (experimentInfo != null) {
                    Object object = experimentInfo.get(UserTpConstant.USER_EXPERIMENT_PARAMETER_SHOW);
                    if (object != null && StringUtil.isNotBlank(object.toString())) {
                        Float value = StringUtil.toFloat(object.toString());
                        if (value != null && value > 0) {
                            showButton = "1";
                        } else {
                            showButton = "0";
                        }
                    }
                }
            }
        } else {
            // a/b test关闭，从配置文件中读取是否展示5元点播按钮的默认值
            showButton = ApplicationUtils.get(ApplicationConstants.VIDEO_EXPERIMENT_DEFAULT_VALUE_LETV);
        }

        Map<String, VideoPlayConsumeGuideInfoDto> map = new HashMap<String, VideoPlayConsumeGuideInfoDto>();
        data.setData(map);
        String[] positions = VipTpConstant.urm_positions_play;
        StringBuilder pos = new StringBuilder();
        for (String p : positions) {
            if (pos.length() == 0) {
                pos.append(p);
            } else {
                pos.append(",").append(p);
            }
        }
        GetUrmActivityBatchResponse res = this.facadeTpDao.getVipTpDao().getUrmTouchDataList(pos.toString(),
                deviceType, commonParam);
        if (res != null && !CollectionUtils.isEmpty(res.getMessages())) {
            List<GetUrmActivityListResponse> list = res.getMessages();
            for (GetUrmActivityListResponse urmData : list) {
                PilotDto dto = vipMetadataService.parseUrmActivityData(urmData,
                        urmData.getTouchSpotId(), commonParam);
                if (dto != null) {
                    VideoPlayConsumeGuideInfoDto guideInfo = new VideoPlayConsumeGuideInfoDto();
                    String userType = VipTpConstant.getUserType(urmData.getTouchSpotId());
                    Integer ut = StringUtil.toInteger(userType);
                    if (ut == null) {
                        continue;
                    }
                    guideInfo.setUserType(ut);
                    guideInfo.setActivityIds(dto.getActivityIds());
                    if (dto.getOrderType() != null) {
                        guideInfo.setOrderType(String.valueOf(dto.getOrderType()));
                    }
                    guideInfo.setId(StringUtil.toInteger(dto.getId()));
                    guideInfo.setDataType(dto.getDataType());
                    guideInfo.setImgUrl(dto.getImg());
                    guideInfo.setKeyDesc(dto.getButtonContent());
                    guideInfo.setMainTitle(dto.getTitle());
                    guideInfo.setSubTitle(dto.getPrivilegeTips());
                    guideInfo.setPosition(dto.getPosition());
                    guideInfo.setJump(dto.getJump());
                    guideInfo.setShowButton(showButton);
                    guideInfo.setCampaignId(dto.getCampaignId());
                    guideInfo.setTouchMessageId(dto.getTouchMessageId());
                    guideInfo.setTouchSpotId(dto.getTouchSpotId());
                    map.put(userType, guideInfo);
                }
            }
        }

        Response<VideoPlayConsumeGuideDto> response = new Response<VideoPlayConsumeGuideDto>();
        response.setData(data);
        return response;
    }

    /**
     * 读取boss接口的引导用户支付信息
     * @return
     */
    private List<VideoPlayConsumeGuideInfoDto> getTvNonMemberGuideList() {
        List<VideoPlayConsumeGuideInfoDto> videoGuideList = null;
        VideoGuideResponse videoGuideResponse = this.facadeTpDao.getVideoTpDao().getTvNonMemberGuideImg();
        if (videoGuideResponse != null && "true".equals(videoGuideResponse.getResult())) {
            videoGuideList = new ArrayList<VideoPlayConsumeGuideInfoDto>();
            List<VideoGuideInfoTpResponse> videoGuideDaoList = videoGuideResponse.getData();
            for (VideoGuideInfoTpResponse VideoGuideDao : videoGuideDaoList) {
                VideoPlayConsumeGuideInfoDto videoGuide = new VideoPlayConsumeGuideInfoDto();
                videoGuide.setId(VideoGuideDao.getId());
                videoGuide.setImgUrl(VideoGuideDao.getImgUrl());
                // -1的情况需要处理;当支付方式、套餐为-1：未配置时，调用系统指定设置
                videoGuide.setUserType(VideoGuideDao.getUserType());
                videoGuide.setPackageType(!"-1".equals(VideoGuideDao.getPackageType()) ? VideoGuideDao.getPackageType()
                        : StringUtils.EMPTY);
                videoGuide.setPayType(!"-1".equals(VideoGuideDao.getPayType()) ? VideoGuideDao.getPayType()
                        : StringUtils.EMPTY);
                videoGuide.setKeyDesc(VideoGuideDao.getKeyDesc());
                videoGuide.setMainTitle(VideoGuideDao.getMainTitle());
                videoGuide.setSubTitle(VideoGuideDao.getSubTitle());
                JumpData jumpData = new JumpData();
                VideoPlayConsumeGuideInfoDto jumpValue = new VideoPlayConsumeGuideInfoDto();
                jumpValue.setDataType(DataConstant.DATA_TYPE_CHECKSTAND);
                jumpData.setValue(jumpValue);
                jumpData.setType(DataConstant.DATA_TYPE_CHECKSTAND);
                videoGuide.setJump(jumpData);
                videoGuide.setDataType(DataConstant.DATA_TYPE_CHECKSTAND);
                videoGuideList.add(videoGuide);
            }
        }
        return videoGuideList;
    }

    /**
     * 更新无地域显示mac白名单
     * @return
     */
    public boolean updateNoAreaPlayRestrictMacs() {
        boolean result = false;
        String logPrefix = "updateNoAreaPlayRestrictMacs_";

        if (VideoUtil.macWhiteListIsEmpty()
                || (System.currentTimeMillis() - VideoUtil.NO_AREA_PLAY_RESTRICT_MAC_LASTUPDATE_TIME) > VideoUtil.NO_AREA_PLAY_RESTRICT_MAC_UPDATE_INTERVAL) {
            // 更新数据
            if (VideoUtil.NO_AREA_PLAY_RESTRICT_MAC_UPDATE_Lock.tryLock()) {
                log.info(logPrefix + ": locked.");
                try {
                    Set<String> macWhiteList = this.facadeTpDao.getStaticTpDao().getHkMacSet();
                    VideoUtil.updatePlayMacWhiteList(macWhiteList);

                    VideoUtil.NO_AREA_PLAY_RESTRICT_MAC_LASTUPDATE_TIME = System.currentTimeMillis();
                    result = true;
                    log.info(logPrefix + "update no area play restrict macs success");
                } catch (Exception e) {
                    log.error(logPrefix + "update no area play restrict macs error", e);
                } finally {
                    VideoUtil.NO_AREA_PLAY_RESTRICT_MAC_UPDATE_Lock.unlock();
                    log.info(logPrefix + ": unlocked.");
                }
            }
        }
        return result;
    }

    /**
     * 更新无地域显示mac白名单
     * @return
     */
    public boolean updateNoAreaPlayRestrictMacs(final CommonParam commonParam) {
        boolean result = false;
        String logPrefix = "updateNoAreaPlayRestrictMacs_" + commonParam.getMac();

        if (commonParam != null && StringUtils.isNotBlank(commonParam.getTerminalApplication())) {
            if ((System.currentTimeMillis() - VideoUtil.NO_AREA_PLAY_RESTRICT_MAC_LASTUPDATE_TIME) > VideoUtil.NO_AREA_PLAY_RESTRICT_MAC_UPDATE_INTERVAL) {
                // 更新数据
                if (VideoUtil.NO_AREA_PLAY_RESTRICT_MAC_UPDATE_Lock.tryLock()) {
                    log.info(logPrefix + ": locked.");
                    try {
                        new Thread() {
                            @Override
                            public void run() {
                                PlayService.this.updatePlayMacWhiteList(commonParam);
                            }

                            ;
                        }.start();

                        VideoUtil.NO_AREA_PLAY_RESTRICT_MAC_LASTUPDATE_TIME = System.currentTimeMillis();
                        log.info(logPrefix + "update no area play restrict macs success");
                        result = true;
                    } catch (Exception e) {
                        log.error(logPrefix + "update no area play restrict macs error", e);
                    } finally {
                        VideoUtil.NO_AREA_PLAY_RESTRICT_MAC_UPDATE_Lock.unlock();
                        log.info(logPrefix + ": unlocked.");

                    }
                }
            }
        } else {
            log.warn(logPrefix + ": illegal terminal application.");
        }

        return result;
    }

    /**
     * 更新4K设备信息
     * @return
     */
    public boolean updatePlayMacWhiteList(final CommonParam commonParam) {
        boolean result = false;
        long startTime = System.currentTimeMillis();
        if (commonParam != null
                && TerminalCommonConstant.TERMINAL_APPLICATION_LE
                        .equalsIgnoreCase(commonParam.getTerminalApplication())) {
            Set<String> playMacWhiteList = this.facadeTpDao.getStaticTpDao().getPlayMacWhiteList(
                    commonParam.getTerminalApplication());

            if (!CollectionUtils.isEmpty(playMacWhiteList)) {
                this.facadeCacheDao.getChannelCacheDao().setMacWhiteList(playMacWhiteList);
            }

            result = VideoUtil.updatePlayMacWhiteList(playMacWhiteList);
        }
        log.info("updatePlayMacWhiteList_" + commonParam.getMac() + ": result-" + result + ", timecost-"
                + (System.currentTimeMillis() - startTime));
        return result;
    }

    /**
     * 根据媒资调度返回值，解析字幕音轨信息，然后根据码流、频道、区域等信息，选择默认字幕和音轨<br/>
     * 规则：字幕音轨语种跟区域有关；音质跟码流有关
     * @param mmsStore
     * @return
     */
    private boolean setVideoSubtitleAudioValue(VideoDto videoPlay, MmsStore mmsStore, MmsFile mmsFile, String wcode,
            Integer channelId, String langType, String kbpsType, CommonParam commonParam) {
        if (mmsStore != null && !CollectionUtils.isEmpty(mmsStore.getData())) {
            MmsInfo mmsInfo = mmsStore.getData().get(0);
            if (wcode != null) {
                wcode = wcode.toLowerCase();
            }
            List<PromptDto> subTitle = this.getSubtitleValue(mmsInfo, commonParam);
            if (subTitle != null) {
                // 已经按照优先级排序，最高优先级在第一个
                videoPlay.setDefaultSubTitle(subTitle.get(0));
                videoPlay.setSubTitle(subTitle);
            }

            String defaultAudioTrack = null;
            if (mmsInfo != null) {
                Map<String, String> defAudioTrack = this.getMapValueFromObject(mmsInfo.getDefAudioTrack());
                if (defAudioTrack != null && !CollectionUtils.isEmpty(defAudioTrack.keySet())) {
                    Set<String> keySet = defAudioTrack.keySet();
                    for (String key : keySet) {
                        if (StringUtils.isNotBlank(key)) {
                            defaultAudioTrack = key;
                            break;
                        }
                    }
                }
            }
            List<AudioTrackDto> audioTrackList = this.setAudioTrackValue(mmsFile, wcode, commonParam.getLangcode(),
                    channelId, defaultAudioTrack);
            String sameLangAid = null;// temporary variable
            String notSameLangAid = null;// temporary variable
            boolean needTips = true;
            if (audioTrackList != null) {
                // the list is already sort and the first one was highest
                // priority
                if (StringUtils.isNotBlank(langType)) {
                    for (AudioTrackDto audioTrackDto : audioTrackList) {
                        if (langType.equals(audioTrackDto.getLangType())) {
                            if (StringUtils.isBlank(sameLangAid)) {// 按照产品定义的优先级顺序，选择第一个匹配上音质语种类型的音轨
                                sameLangAid = audioTrackDto.getAudioId();
                                videoPlay.setDefaultAudioTrack(audioTrackDto);
                            }
                            if (kbpsType != null && kbpsType.equals(audioTrackDto.getKbpsType())) {// 如果语种匹配上，音质码率也匹配上，则选择最优的
                                needTips = false;
                                videoPlay.setDefaultAudioTrack(audioTrackDto);
                                break;
                            }
                        } else if (StringUtil.isBlank(sameLangAid) && StringUtil.isBlank(notSameLangAid)) {
                            notSameLangAid = audioTrackDto.getAudioId();
                            videoPlay.setDefaultAudioTrack(audioTrackDto);
                        }
                    }
                } else {// 第一次播放，选择默认音轨播放
                    videoPlay.setDefaultAudioTrack(audioTrackList.get(0));
                }
                videoPlay.setAudioTrackList(audioTrackList);
            }
            if (needTips && StringUtil.isNotBlank(langType) && videoPlay.getDefaultAudioTrack() != null) {
                // should make some tips to user
                AudioTrackDto audioTrackDto = videoPlay.getDefaultAudioTrack();
                String tipMsg = videoPlay.getTipMsg();
                if (StringUtil.isBlank(tipMsg)) {
                    String langName = MessageUtils.getMessage(VideoConstants.getVideoLangTextKey(langType),
                            commonParam.getLangcode());
                    String kpbsName = MessageUtils.getMessage(VideoConstants.getVideoKpbsTextKey(kbpsType),
                            commonParam.getLangcode());
                    if (StringUtil.isNotBlank(kpbsName)) {
                        langName = langName + "(" + kpbsName + ")";
                        Object[] codeMap = { langName, audioTrackDto.getAudioName() };
                        videoPlay.setTipMsg(MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_NO_AUDIO_TRACK,
                                commonParam.getLangcode(), codeMap));
                    } else if (StringUtil.toInteger(audioTrackDto.getKbpsType(), 0) > 1002) {
                        // if both of two is low kbpstype,it's no need to give
                        // some tips
                        Object[] codeMap = { langName, audioTrackDto.getAudioName() };
                        videoPlay.setTipMsg(MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_NO_AUDIO_TRACK,
                                commonParam.getLangcode(), codeMap));
                    } else {
                        needTips = false;
                    }
                } else {
                    videoPlay.setTipMsg(MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_NO_AUDIO_TRACK_PLAY_STREAM,
                            commonParam.getLangcode()));
                }
                videoPlay.setHasBelow(1);
            }
            return needTips;
        }
        return false;
    }

    /**
     * 根据媒资返回值解析字幕信息，包含按照优先级排序，最高优先级在第一个
     * @param mmsInfo
     * @return
     */
    private List<PromptDto> getSubtitleValue(MmsInfo mmsInfo, String wcode, String langcode, CommonParam commonParam) {
        if (mmsInfo == null) {
            return null;
        }
        Map<String, String> defSubTitle = this.getMapValueFromObject(mmsInfo.getDefSubTrack());
        String defaultSubTitle = null;
        if (defSubTitle != null && !CollectionUtils.isEmpty(defSubTitle.keySet())) {
            Set<String> keySet = defSubTitle.keySet();
            for (String key : keySet) {
                if (StringUtils.isNotBlank(key)) {
                    defaultSubTitle = key;
                    break;
                }
            }
        }
        Map<String, String> subtitle = this.getMapValueFromObject(mmsInfo.getSubtitle());
        if (!CollectionUtils.isEmpty(subtitle)) {
            List<PromptDto> list = new ArrayList<PromptDto>();
            Map<String, PromptDto> map = new HashMap<String, PromptDto>();
            for (Entry<String, String> entry : subtitle.entrySet()) {
                String subtitleType = entry.getKey();
                String name = MessageUtils.getMessage(VideoConstants.getVideoSubtitleTextKey(subtitleType), langcode);
                String value = entry.getValue();
                if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)) {
                    // 只有合法的数据才解析
                    PromptDto promptDto = new PromptDto();
                    promptDto.setSubtitleType(subtitleType);
                    promptDto.setName(name);
                    promptDto.setUrl(DomainMapping.changeDomainByBraodcastId(value, commonParam.getBroadcastId(),
                            commonParam.getTerminalApplication()));
                    map.put(subtitleType, promptDto);
                }
            }
            if (!CollectionUtils.isEmpty(map)) {
                String[] sort = VideoConstants.getDefaultSubtitleType(wcode);
                if (sort == null) {// 第一次为空，表示属于其他地区，所以再读一次
                    sort = VideoConstants.getDefaultSubtitleType(LocaleConstant.Wcode.OTHER);
                }
                if (sort == null) {// 如果读取不到排序，就直接返回
                    if (StringUtils.isNotBlank(defaultSubTitle)) {// 优先编辑勾选的默认字幕
                        PromptDto defaultPromptDto = map.remove(defaultSubTitle);
                        if (defaultPromptDto != null) {
                            list.add(defaultPromptDto);
                        }
                    }
                    list.addAll(map.values());
                } else {// 需要排序
                    if (StringUtils.isNotBlank(defaultSubTitle)) {// 优先编辑勾选的默认字幕
                        PromptDto defaultPromptDto = map.remove(defaultSubTitle);
                        if (defaultPromptDto != null) {
                            list.add(defaultPromptDto);
                        }
                    }
                    for (String type : sort) {// 循环优先级数组
                        PromptDto promptDto = map.remove(type);// 用于判断减少循环
                        if (promptDto != null) {
                            list.add(promptDto);
                        }
                        if (map.size() < 1) {// 减少不必要的循环
                            break;
                        }
                    }
                }
            }
            if (list.size() > 0) {// 设置字幕信息
                return list;
            }
        }
        return null;
    }

    private List<PromptDto> getSubtitleValue(MmsInfo mmsInfo, CommonParam commonParam) {
        if (mmsInfo == null) {
            return null;
        }
        Map<String, String> defSubTitle = this.getMapValueFromObject(mmsInfo.getDefSubTrack());
        String defaultSubTitle = null;
        if (defSubTitle != null && !CollectionUtils.isEmpty(defSubTitle.keySet())) {
            Set<String> keySet = defSubTitle.keySet();
            for (String key : keySet) {
                if (StringUtils.isNotBlank(key)) {
                    defaultSubTitle = key;
                    break;
                }
            }
        }
        Map<String, String> subtitle = this.getMapValueFromObject(mmsInfo.getSubtitle());
        if (!CollectionUtils.isEmpty(subtitle)) {
            List<PromptDto> list = new ArrayList<PromptDto>();
            Map<String, PromptDto> map = new HashMap<String, PromptDto>();
            for (Entry<String, String> entry : subtitle.entrySet()) {
                String subtitleType = entry.getKey();
                String name = MessageUtils.getMessage(VideoConstants.getVideoSubtitleTextKey(subtitleType),
                        commonParam.getLangcode());
                String value = entry.getValue();
                if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)) {
                    // 只有合法的数据才解析
                    PromptDto promptDto = new PromptDto();
                    promptDto.setSubtitleType(subtitleType);
                    promptDto.setName(name);
                    promptDto.setUrl(DomainMapping.changeDomainByBraodcastId(value, commonParam.getBroadcastId(),
                            commonParam.getTerminalApplication()));
                    map.put(subtitleType, promptDto);
                }
            }
            if (!CollectionUtils.isEmpty(map)) {
                String[] sort = null;
                if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                        .getTerminalApplication())) {
                    sort = VideoConstants.getDefaultSubtitleType(commonParam);
                } else {
                    sort = VideoConstants.getDefaultSubtitleType(commonParam.getWcode());
                }

                if (sort == null) {// 第一次为空，表示属于其他地区，所以再读一次
                    sort = VideoConstants.getDefaultSubtitleType(LocaleConstant.Wcode.OTHER);
                }
                if (sort == null) {// 如果读取不到排序，就直接返回
                    if (StringUtils.isNotBlank(defaultSubTitle)) {// 优先编辑勾选的默认字幕
                        PromptDto defaultPromptDto = map.remove(defaultSubTitle);
                        if (defaultPromptDto != null) {
                            list.add(defaultPromptDto);
                        }
                    }
                    list.addAll(map.values());
                } else {// 需要排序
                    if (StringUtils.isNotBlank(defaultSubTitle)) {// 优先编辑勾选的默认字幕
                        PromptDto defaultPromptDto = map.remove(defaultSubTitle);
                        if (defaultPromptDto != null) {
                            list.add(defaultPromptDto);
                        }
                    }
                    for (String type : sort) {// 循环优先级数组
                        PromptDto promptDto = map.remove(type);// 用于判断减少循环
                        if (promptDto != null) {
                            list.add(promptDto);
                        }
                        if (map.size() < 1) {// 减少不必要的循环
                            break;
                        }
                    }
                }
            }
            if (list.size() > 0) {// 设置字幕信息
                return list;
            }
        }
        return null;
    }

    /**
     * 根据媒资返回值解析音轨信息，解析结果已经按照优先级排序，最高优先级在第一个
     * @param mmsFile
     * @return
     */
    private List<AudioTrackDto> setAudioTrackValue(MmsFile mmsFile, String wcode, String langcode, Integer channelId,
                                                            String defaultAudioTrack) {
        if (mmsFile != null) {
            // 解析音轨信息
            Map<String, AudioTrackDto> map = this.getAudioTracksMap(mmsFile, langcode);
            // 第一次音轨排序，优先编辑勾选默认音轨，其次按照wcode字段对语种进行排序
            String defaultLangType = null;// 默认语种（优先级：编辑勾选、粤语优先粤语，杜比频道优先国语杜比）
            String defaultKbpsType = null;// 默认音质（优先级：编辑勾选、杜比频道优先国语杜比），杜比是一种音质类型
            if (StringUtils.isNotBlank(defaultAudioTrack)) {// 根据编辑勾选的默认值来确定优先音轨
                String[] s = defaultAudioTrack.split("_");
                if (s != null && s.length == 2) {
                    defaultLangType = s[0];
                    defaultKbpsType = s[1];
                }
            } else if (StringUtils.isBlank(defaultAudioTrack)) {// 如果没有编辑勾选默认音轨，则按照产品定义的区域方案处理(粤语和杜比频道特殊处理)
                if (channelId != null) {
                    if (channelId == ChannelConstants.YUE_YU) {// 粤语频道，优先粤语音轨
                        defaultLangType = MmsTpConstant.VIDEO_LANG_TYPE_1001;
                    } else if (channelId == ChannelConstants.DOLBY) {// 杜比频道，优先国语(杜比)音轨
                        defaultLangType = MmsTpConstant.VIDEO_LANG_TYPE_1000;// 国语
                        defaultKbpsType = MmsTpConstant.VIDEO_AUDIO_TYPE_1003;// 杜比
                    }
                }
            }
            List<AudioTrackDto> audioTrackList = this.sortAudioTracksByWcode(map, wcode, defaultLangType);
            // 第二步排序，按照码流类型对音质排序
            return this.sortAudioTracksByVtype(audioTrackList, mmsFile.getVtype(), defaultLangType, defaultKbpsType);
        }
        return null;
    }

    /**
     * 将媒资的音轨信息解析到map中，key是语种类型，value是音轨信息
     * @param mmsFile
     * @return
     */
    private Map<String, AudioTrackDto> getAudioTracksMap(MmsFile mmsFile, String langcode) {
        if (mmsFile == null || mmsFile.getAudioTracks() == null) {
            return null;
        }
        Map<String, String> audioTracks = this.getMapValueFromObject(mmsFile.getAudioTracks());
        Map<String, AudioTrackDto> map = new HashMap<String, AudioTrackDto>();
        for (Entry<String, String> entry : audioTracks.entrySet()) {
            String audioKey = entry.getKey();// 媒资返回的"语种_音质"主键
            if (StringUtils.isBlank(audioKey)) {
                continue;
            }
            String[] keys = audioKey.split("_");
            if (keys != null && keys.length == 2) {
                String lang = keys[0];// 语种类型
                String kpbs = keys[1];// 音质类型
                if (MmsTpConstant.VIDEO_AUDIO_TYPE_1004.equals(kpbs)) {// 20160303先过滤掉dts音轨
                    continue;
                }
                String langName = MessageUtils.getMessage(VideoConstants.getVideoLangTextKey(lang), langcode);
                String kpbsName = MessageUtils.getMessage(VideoConstants.getVideoKpbsTextKey(kpbs), langcode);
                String audioId = entry.getValue();// 音轨id
                if (StringUtils.isNotBlank(langName) && StringUtils.isNotBlank(audioId)) {
                    KbpsDto kbpsDto = new KbpsDto();
                    kbpsDto.setKpbsType(kpbs);
                    kbpsDto.setKpbsName(kpbsName);
                    kbpsDto.setAudioId(audioId);
                    if (StringUtils.isNotBlank(kpbsName)) {// 如果是要显示的音质，就加括号
                        kbpsDto.setAudioName(langName + "(" + kpbsName + ")");
                    } else {
                        kbpsDto.setAudioName(langName);
                    }
                    AudioTrackDto audioTrackDto = map.get(lang);
                    if (audioTrackDto == null) {// 集合中一个语种存一个音轨对象
                        audioTrackDto = new AudioTrackDto();
                        audioTrackDto.setLangType(lang);
                        audioTrackDto.setLangName(langName);
                        List<KbpsDto> list = new ArrayList<KbpsDto>();
                        list.add(kbpsDto);
                        audioTrackDto.setKbpsTypeList(list);
                        map.put(lang, audioTrackDto);
                    } else {
                        List<KbpsDto> list = audioTrackDto.getKbpsTypeList();
                        if (list == null) {
                            list = new ArrayList<KbpsDto>();
                            list.add(kbpsDto);
                            audioTrackDto.setKbpsTypeList(list);
                        } else {
                            list.add(kbpsDto);
                        }
                    }
                }
            }
        }
        return map;
    }

    /**
     * 先按照区域对音轨的语种排序，也处理特例（粤语频道优先粤语音轨，杜比频道优先国语杜比音轨）
     * @param map
     * @param wcode
     * @return
     */
    private List<AudioTrackDto> sortAudioTracksByWcode(Map<String, AudioTrackDto> map, String wcode,
            String defaultLangType) {
        if (!CollectionUtils.isEmpty(map)) {
            if (!CollectionUtils.isEmpty(map)) {
                List<AudioTrackDto> audioTrackList = new LinkedList<AudioTrackDto>();
                String[] sort = VideoConstants.getDefaultAudioTrackLangType(wcode);
                if (sort == null) {// 第一次为空，表示属于其他地区，所以再读一次
                    sort = VideoConstants.getDefaultAudioTrackLangType(LocaleConstant.Wcode.OTHER);
                }
                if (StringUtils.isNotBlank(defaultLangType)) {
                    AudioTrackDto audioTrackDto = map.remove(defaultLangType);
                    if (audioTrackDto != null) {
                        audioTrackList.add(audioTrackDto);
                    }
                }
                if (sort == null) {// 第二次还是读不到，就直接返回
                    audioTrackList.addAll(map.values());
                } else {// 需要排序
                    for (String type : sort) {// 遍历循环排序
                        AudioTrackDto audioTrackDto = map.remove(type);// 用于判断减少循环
                        if (audioTrackDto != null) {
                            audioTrackList.add(audioTrackDto);
                        }
                        if (map.size() < 1) {// 减少不必要的循环
                            break;
                        }
                    }
                }
                return audioTrackList;
            }
            return null;
        }
        return null;
    }

    /**
     * 根据码流类型，对已经按照语种类型排好序的音轨，进行音质排序，也处理特例（杜比频道优先国语杜比音轨）
     * @param audioTrackList
     * @param vtype
     * @return
     */
    private List<AudioTrackDto> sortAudioTracksByVtype(List<AudioTrackDto> audioTrackList, Integer vtype,
            String defaultLangType, String defaultKbpsType) {
        if (!CollectionUtils.isEmpty(audioTrackList)) {
            String[] kpbsArray = VideoConstants.getAtypeByVtype(vtype);// 当前码流支持的音质列表
            if (kpbsArray != null && kpbsArray.length > 0) {
                List<AudioTrackDto> finalList = new ArrayList<VideoDto.AudioTrackDto>();
                boolean need = false;// 是否需要优先某种语种的某种音质
                if (StringUtils.isNotBlank(defaultKbpsType)) {// 优先音质
                    need = true;// 初步判断
                }
                // 优先某种语种的某种音质类型
                for (AudioTrackDto audioTrackDto : audioTrackList) {
                    List<KbpsDto> list = audioTrackDto.getKbpsTypeList();
                    if (!CollectionUtils.isEmpty(list)) {
                        list = new LinkedList<KbpsDto>(list);
                        if (need && defaultLangType != null && !defaultLangType.equals(audioTrackDto.getLangType())) {
                            need = false;// 二次判断，如果优先的语种不为空，则需要满足条件才可以对音质处理
                        }
                        for (String kpbs : kpbsArray) {
                            if (StringUtils.isBlank(kpbs)) {
                                continue;
                            }
                            for (KbpsDto kd : list) {
                                if (kpbs.equals(kd.getKpbsType())) {
                                    AudioTrackDto dto = new AudioTrackDto();
                                    dto.setLangType(audioTrackDto.getLangType());
                                    dto.setLangName(audioTrackDto.getLangName());
                                    dto.setKbpsType(kpbs);
                                    dto.setKbpsName(kd.getKpbsName());
                                    dto.setAudioId(kd.getAudioId());
                                    dto.setAudioName(kd.getAudioName());
                                    list.remove(kd);
                                    if (need && kpbs.equals(defaultKbpsType)) {// 优先的音质
                                        finalList.add(0, dto);
                                    } else {
                                        finalList.add(dto);
                                    }
                                }
                            }
                        }
                    }
                }
                if (finalList.size() > 0) {
                    return finalList;
                }
            }
        }
        return null;
    }

    /**
     * 设置试看时间
     * @param videoPlay
     * @param locale
     */
    private void getTryPlayTime(VideoDto videoPlay, Integer chargeType, Integer albumType, String locale) {
        Integer category = videoPlay.getCategoryId();
        // 付费类型，1支持单点付费，2点播且包月，3包月，4免费但TV包月收费，5包年，6码流付费
        if (category == null || category == VideoConstants.Category.FILM) {
            // 分类ID为空或者电影，试看6分钟
            videoPlay.setTryPlayTime(360000L);
            videoPlay.setTryPlayTipMsg(MessageUtils
                    .getMessage(VideoConstants.VIDEO_TIPMSG_TRY_PLAY_SIX_MINUTES, locale));
        } else {
            switch (category) {
            case VideoConstants.Category.TV:
                // 仅会员付费类型，电视剧和动漫的单点，没有试看
                if (chargeType != null && chargeType == 4) {
                    videoPlay.setPlayAd(0);
                    videoPlay.setTryPlayTime(0L);
                } else {
                    videoPlay.setPlayAd(0);
                    videoPlay.setTryPlayTime(360000L);
                    videoPlay.setTryPlayTipMsg(MessageUtils.getMessage(
                            VideoConstants.VIDEO_TIPMSG_TRY_PLAY_SIX_MINUTES, locale));
                }
                break;
            case VideoConstants.Category.CARTOON:
                // 仅会员付费类型，电视剧和动漫的单点，没有试看
                if (chargeType != null && chargeType == 4) {
                    if ((albumType != null) && ((albumType == 181033) || (albumType == 181032))) {// 剧场版专辑，试看6分钟
                        videoPlay.setPlayAd(0);
                        videoPlay.setTryPlayTime(360000L);
                        videoPlay.setTryPlayTipMsg(MessageUtils.getMessage(
                                VideoConstants.VIDEO_TIPMSG_TRY_PLAY_SIX_MINUTES, locale));
                    } else {// 除了剧场版和OVA版，其他类型就没有试看了
                        videoPlay.setPlayAd(0);
                        videoPlay.setTryPlayTime(0L);
                    }
                } else {// 免费视频可以试看3分钟
                    videoPlay.setTryPlayTime(180000L);
                    videoPlay.setTryPlayTipMsg(MessageUtils.getMessage(
                            VideoConstants.VIDEO_TIPMSG_TRY_PLAY_THREE_MINUTES, locale));
                }
                // 仅会员付费类型，电视剧和动漫的单点，没有试看
                // if (chargeType != null && chargeType == 4) {
                // videoPlay.setPlayAd(0);
                // videoPlay.setTryPlayTime(0L);
                // } else {
                // videoPlay.setTryPlayTime(180000L);
                // videoPlay.setTryPlayTipMsg(MessageUtils.getMessage(
                // VideoConstants.VIDEO_TIPMSG_TRY_PLAY_THREE_MINUTES, locale));
                // }
                break;
            case VideoConstants.Category.PARENTING:
                videoPlay.setTryPlayTime(60000L);
                videoPlay.setTryPlayTipMsg(MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_TRY_PLAY_ONE_MINUTES,
                        locale));
                break;
            default:
                videoPlay.setTryPlayTime(360000L);
                videoPlay.setTryPlayTipMsg(MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_TRY_PLAY_SIX_MINUTES,
                        locale));
                break;
            }
        }
    }

    private void getTryPlayTime4LecomUS(VideoDto videoPlay, UserPlayAuth4LecomUS playAuth, String locale) {
        ValidateServiceTp.VodTryPlayInfo tryInfo = playAuth.getTryInfo();
        if (tryInfo != null && tryInfo.getServerTime() != null && tryInfo.getServerTime() > 0
                && tryInfo.getTryEndTime() != null && tryInfo.getTryEndTime() > 0) {
            long tryPlayTime = tryInfo.getTryEndTime() - tryInfo.getServerTime();
            if (tryPlayTime > 60000L) {
                // 提示试看xxx分钟
                String[] tryPlayTimes = { String.valueOf(tryPlayTime / 60000) };
                videoPlay.setTryPlayTipMsg(MessageUtils.getMessage(
                        VideoConstants.VIDEO_TIPMSG_TRY_PLAY_SEVERAL_MINUTES, locale, tryPlayTimes));
            } else if (tryPlayTime > 1000) {
                // 提示试看xxx秒
                String[] tryPlayTimes = { String.valueOf(tryPlayTime / 1000) };
                videoPlay.setTryPlayTipMsg(MessageUtils.getMessage(
                        VideoConstants.VIDEO_TIPMSG_TRY_PLAY_SEVERAL_SECONDS, locale, tryPlayTimes));
            } else {
                // 不试看，直接跳转至付费引导
                tryPlayTime = 0;
            }
            videoPlay.setTryPlayTime(tryPlayTime);
        } else {
            Integer category = videoPlay.getCategoryId();
            if (category == null || category == VideoConstants.Category.FILM) {
                // 分类ID为空或者电影，试看6分钟
                videoPlay.setTryPlayTime(360000L);
                videoPlay.setTryPlayTipMsg(MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_TRY_PLAY_SIX_MINUTES,
                        locale));
            } else {
                switch (category) {
                case VideoConstants.Category.TV:
                    // 仅会员付费类型，电视剧和动漫的单点，没有试看
                    videoPlay.setTryPlayTime(360000L);
                    videoPlay.setTryPlayTipMsg(MessageUtils.getMessage(
                            VideoConstants.VIDEO_TIPMSG_TRY_PLAY_SIX_MINUTES, locale));
                    break;
                case VideoConstants.Category.CARTOON:
                    // 仅会员付费类型，电视剧和动漫的单点，没有试看
                    videoPlay.setTryPlayTime(180000L);
                    videoPlay.setTryPlayTipMsg(MessageUtils.getMessage(
                            VideoConstants.VIDEO_TIPMSG_TRY_PLAY_THREE_MINUTES, locale));
                    break;
                case VideoConstants.Category.PARENTING:
                    videoPlay.setTryPlayTime(60000L);
                    videoPlay.setTryPlayTipMsg(MessageUtils.getMessage(
                            VideoConstants.VIDEO_TIPMSG_TRY_PLAY_ONE_MINUTES, locale));
                    break;
                default:
                    videoPlay.setTryPlayTime(360000L);
                    videoPlay.setTryPlayTipMsg(MessageUtils.getMessage(
                            VideoConstants.VIDEO_TIPMSG_TRY_PLAY_SIX_MINUTES, locale));
                    break;
                }
            }
        }
    }

    /**
     * 如果是整数，则不要小数点
     * @param price
     * @return
     */
    private String getPrice(String price) {
        if (StringUtils.isBlank(price)) {
            return price;
        }
        Double p = Double.valueOf(price);
        if (p > Double.valueOf(p.intValue())) {// 小数点后不为0
            return price;
        } else {// 小数点后为0
            return String.valueOf(p.intValue());
        }
    }

    /**
     * 判断播放结束是否需要推荐数据
     * @param albumMysqlTable
     * @param videoMysqlTable
     * @return
     */
    private Integer setNeedRecommend4EndValue(AlbumMysqlTable albumMysqlTable, VideoMysqlTable videoMysqlTable) {
        if (albumMysqlTable != null && videoMysqlTable != null) {
            Integer category = albumMysqlTable.getCategory();
            if (category != null) {
                // 只有电影、电视剧、动漫频道播放到最后一集(期)需要推荐短视频
                if (category == VideoConstants.Category.FILM || category == VideoConstants.Category.TV
                        || category == VideoConstants.Category.CARTOON || category == VideoConstants.Category.VARIETY) {
                    return 1;
                }
            }
        }
        return 0;
    }

    private Map<String, String> getMapValueFromObject(Object object) {
        if (object != null && object.toString().length() > 0) {
            try {
                return (Map<String, String>) object;
            } catch (Exception e) {
                log.info("getMapValueFromObject " + object + " failure." + e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 投屏播放处理逻辑；
     * 2016-03-07TV版码流修改，1000码率不转，降为800，重新罗列清晰度对应表，标清由1000修改为800；这里修改逻辑，
     * 区分客户端原始请求码流originalClientRequestStream
     * （用作返回码流的判断依据之一），和当前请求码流（TV端降码流逻辑之一，考虑其他终端码流因素）
     * @param videoId
     * @param model
     * @param imei
     * @param originalClientRequestStream
     * @param currentClientRequestStream
     * @param response
     * @param routerId
     * @param support3d
     * @param commonParam
     */
    private void getTouPingPlay(Long videoId, Integer model, String imei, String originalClientRequestStream,
            String currentClientRequestStream, Response<VideoDto> response, String routerId, Boolean support3d,
            String unSupportStream, CommonParam commonParam, boolean isThirdParty, Boolean support4k) {
        /*
         * 投屏播放中，涉及到的码流问题：
         * 1.领先版中，“1080P”指1080p，TV版中“1080P”指1080p6m、1080p3m，显示1080p6m，
         * 实际使用1080p3m
         * ；“标清”指800p，TV版中“标清”指1000p；需要转换
         * 2.领先版中有“极速”指180p，TV版中没有，不参与投屏；
         * 3.领先版中有“2K”指2k，TV版中没有，需要降码流到1080p；
         * 4.领先版中码流顺序由高到低为"4K#2K#3D*#1080p#720p#1300p#1000p#800p#350#180"，
         * 则当播放4K时
         * ，先按TV版规则（考虑设备支持信息）降码流；如果4K没有降码流，则实际到
         * 领先版的播放响应，可能是4K也可能是2K，其中2K对TV版无意义，需要重新降码流并请求播放；当播放2k时，遵守3的规则；
         */
        VideoDto videoPlay = new VideoDto();
        // 按照TV逻辑先降码流，默认支持4K，但需要根据设备信息降码流；能处理2K降到1080p6m，但是不处理1080p
        String videoStreams = StringUtils.replace(new String(LetvStreamCommonConstants.USER_SETTING_PLAY_STREAM), "#",
                ",");
        String realRequestStream = this.getPlayStream(currentClientRequestStream, videoStreams, videoPlay, model,
                support3d, unSupportStream, true, commonParam, support4k);
        // 处理1080P的码流
        realRequestStream = this.parseTvStream2TerminalStream(realRequestStream);
        //
        // 投屏播放，暂走领先版播放逻辑
        LetvLeadingCommonResponse<LetvLeadingPlayInfo> videoPlayesponse = this.facadeTpDao.getVideoTpDao()
                .getLetvLeadingPlayInfo(videoId, imei, realRequestStream, routerId, commonParam);
        if (videoPlayesponse == null || videoPlayesponse.getStatus() == null || 0 == videoPlayesponse.getStatus()
                || StringUtils.isNotEmpty(videoPlayesponse.getErrorCode()) || videoPlayesponse.getData() == null) {
            ErrorCodeCommonUtil.setErrorResponse(response, VideoConstants.VIDEO_PLAY_NULL, commonParam.getLangcode());
        } else {
            this.fillVideoPlayInfoFromLetvLeading(videoPlay, videoPlayesponse.getData(), originalClientRequestStream,
                    commonParam, isThirdParty);
            if (!StringUtils.equals(realRequestStream, videoPlay.getCurrentStream())) {
                videoPlay.setHasBelow(VideoConstants.HAS_BELOW_YES);
                Object[] codeMap = {
                        LetvStreamCommonConstants.nameOf(currentClientRequestStream, commonParam.getLangcode()),
                        LetvStreamCommonConstants.nameOf(videoPlay.getCurrentStream(), commonParam.getLangcode()) };
                String tipMsg = MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_REDUCESTREAM,
                        commonParam.getLangcode(), codeMap);
                videoPlay.setTipMsg(tipMsg);
            }
            videoPlay.setCurrentStream(this.parseTerminalStream2TvStream(videoPlay.getCurrentStream()));

            // 针对投屏播放，设置默认值--会员身份，不展示广告
            videoPlay.setPlayType(1);
            videoPlay.setVip(1);
            videoPlay.setPlayAd(0);
            videoPlay.setShowPauseAdPage(0);
            videoPlay.setPlayFloatAd(0);

            response.setResultStatus(1);
            response.setData(videoPlay);
        }
    }

    /**
     * 2016-01-23，点播投屏新加业务，将TV端码流转换成其他终端（领先版）可以播放的码流；
     * 领先版中，对于“1080p”码流不区分“1080p3m”和“1080p6m”，而TV端不使用“1080p”；
     * @param tvStream
     * @return
     */
    private String parseTvStream2TerminalStream(String tvStream) {
        String realRequestStream = tvStream;
        if (LetvStreamCommonConstants.CODE_NAME_1080p3m.equalsIgnoreCase(tvStream)
                || LetvStreamCommonConstants.CODE_NAME_1080p6m.equalsIgnoreCase(tvStream)) {
            // 领先版只有1080p，没有TV的1080p3m和1080p6m，这里需要转换
            realRequestStream = LetvStreamCommonConstants.CODE_NAME_1080p;
        } else if (LetvStreamCommonConstants.CODE_NAME_1000.equals(realRequestStream)) {
            // 领先版800是标清，TV版1000是标清
            realRequestStream = LetvStreamCommonConstants.CODE_NAME_800;
        }
        return realRequestStream;
    }

    private void fillVideoPlayInfoFromLetvLeading(VideoDto videoPlay, LetvLeadingPlayInfo playInfo,
            String clientRequestStream, CommonParam commonParam, boolean isThirdParty) {
        videoPlay.setVideoId(playInfo.getVideoId());
        videoPlay.setCategoryId(playInfo.getCategoryId());
        videoPlay.setAlbumId(playInfo.getAlbumId());
        videoPlay.setOrderInAlbum(playInfo.getOrderInAlbum());
        videoPlay.setPositive((playInfo.getPositive() != null && 1 == playInfo.getPositive()) ? true : false);
        videoPlay.setVideoTypeId(playInfo.getVideoTypeId());
        videoPlay.setEpisode(playInfo.getEpisode());
        videoPlay.setImg(playInfo.getImg());
        videoPlay.setDuration(playInfo.getDuration());
        videoPlay.setIfCharge(playInfo.getIfCharge());
        videoPlay.setStorePath(playInfo.getStorePath());
        videoPlay.setPlayUrl(playInfo.getPlayUrl());
        videoPlay.setBackUrl0(playInfo.getBackUrl0());
        videoPlay.setBackUrl1(playInfo.getBackUrl1());
        videoPlay.setBackUrl2(playInfo.getBackUrl2());
        videoPlay.setPageNum(playInfo.getPageNum());
        videoPlay.setCoopPlatform(playInfo.getCoopPlatform());
        videoPlay.setAlbumPositive(VideoCommonUtil.isPositive(2, playInfo.getCategoryId(), playInfo.getAlbumType(),
                null));
        videoPlay.setVarietyShow(playInfo.getVarietyShow());

        // 码流转换
        videoPlay.setCurrentStream(playInfo.getCurrentStream());

        StringBuilder streamBuilder = new StringBuilder();
        List<LetvPlayStream> normalStreams = playInfo.getNormalStreams();
        if (!CollectionUtils.isEmpty(normalStreams)) {
            for (LetvPlayStream letvLeadingPlayStream : normalStreams) {
                if (letvLeadingPlayStream != null && letvLeadingPlayStream.getCode() != null) {
                    String streamCode = this.parseTerminalStream2TvStream(letvLeadingPlayStream.getCode());
                    streamCode = VideoCommonUtil.filterTerminalValidStream(streamCode);
                    if (streamCode != null) {
                        streamBuilder.append(streamCode).append(",");
                    }
                }
            }
            if (streamBuilder.length() > 0) {
                streamBuilder = streamBuilder.deleteCharAt(streamBuilder.length() - 1);
            }
        }
        this.setTouPingVideoPlayStreamList(clientRequestStream, videoPlay, streamBuilder.toString(), commonParam,
                isThirdParty);

        videoPlay.setShowName(VideoUtil.getPlayShowName(playInfo.getCategoryId(), playInfo.getName(),
                playInfo.getName(), playInfo.getOrderInAlbum(), videoPlay.getCurrentStream(), null,
                playInfo.getPositive(), commonParam));

        videoPlay.setVideoHeadTime(playInfo.getVideoHeadTime());
        videoPlay.setVideoTailTime(playInfo.getVideoTailTime());

        videoPlay.setTryPlayTime(playInfo.getTryPlayTime());
        videoPlay.setTryPlayTipMsg(playInfo.getTryPlayTipMsg());
        videoPlay.setIsDanmaku(playInfo.getDanmu());

        // 字幕音轨
        videoPlay.setDefaultSubTitle(convertLeadSubtitle2TV(playInfo.getCurrentSubtitle()));
        if (playInfo.getSubtitles() != null && playInfo.getSubtitles().size() > 0) {
            List<PromptDto> subTitles = new ArrayList<VideoDto.PromptDto>();
            for (Subtitle leadSubtitle : playInfo.getSubtitles()) {
                PromptDto s = convertLeadSubtitle2TV(leadSubtitle);
                if (s != null) {
                    subTitles.add(s);
                }

            }
            if (subTitles.size() > 0) {
                videoPlay.setSubTitle(subTitles);
            }
        }
        // 音轨信息
        videoPlay.setDefaultAudioTrack(convertLeadAudioTrack2TV(playInfo.getCurrentAudioTrack()));
        if (playInfo.getAudioTracks() != null && playInfo.getAudioTracks().size() > 0) {
            List<AudioTrackDto> audioTracks = new ArrayList<VideoDto.AudioTrackDto>();
            for (AudioTrack leadAudioTrack : playInfo.getAudioTracks()) {
                AudioTrackDto a = convertLeadAudioTrack2TV(leadAudioTrack);
                if (a != null) {
                    audioTracks.add(a);
                }
            }
            if (audioTracks.size() > 0) {
                videoPlay.setAudioTrackList(audioTracks);
            }
        }

    }

    /**
     * 领先版字幕转为TV
     * @param leadSubtitle
     * @return
     */
    private PromptDto convertLeadSubtitle2TV(LetvLeadingPlayInfo.Subtitle leadSubtitle) {
        PromptDto defaultSubTitle = null;
        if (leadSubtitle != null) {
            defaultSubTitle = new PromptDto();
            defaultSubTitle.setSubtitleType(leadSubtitle.getId());
            defaultSubTitle.setName(leadSubtitle.getName());
            defaultSubTitle.setUrl(leadSubtitle.getAddr());
        }
        return defaultSubTitle;
    }

    /**
     * 领先版音轨转为TV
     * @return
     */
    private AudioTrackDto convertLeadAudioTrack2TV(AudioTrack leadAudioTrack) {
        AudioTrackDto defaultAudioTrack = null;
        if (leadAudioTrack != null) {
            defaultAudioTrack = new AudioTrackDto();
            defaultAudioTrack.setLangType(leadAudioTrack.getLangCode());
            defaultAudioTrack.setKbpsType(leadAudioTrack.getAtype());
            defaultAudioTrack.setLangName(leadAudioTrack.getLang());
            defaultAudioTrack.setAudioId(leadAudioTrack.getAtId());
            // 手机对于同一语言 只会出一个音质 所以暂时没有 类似 CN(Dolby)
            // 的音轨名称，等产品做出合理的设计，再做此AudioName 的逻辑
            defaultAudioTrack.setAudioName(leadAudioTrack.getLang());
        }
        return defaultAudioTrack;
    }

    private void setTouPingVideoPlayStreamList(String clientRequestStream, VideoDto videoPlay, String videoStreams,
            CommonParam commonParam, boolean isThirdParty) {
        // 投屏播放所有码流均免费
        Map<String, Stream> preSetStream = VideoUtil.getPreSetStream(commonParam.getLangcode(), isThirdParty);
        this.setPlayStreamByStreamCode(clientRequestStream, videoStreams, preSetStream, videoPlay,
                VideoConstants.PLAY_MODEL_TOUPING, commonParam);
    }

    /**
     * 2016-01-23，点播投屏新加业务，将其他终端（领先版）可以播放的码流转换成TV端可播放的码流；
     * 领先版中，对于“1080p”码流不区分“1080p3m”和“1080p6m”，而TV端不使用“1080p”；
     * 领先版中，"800"标清，TV版中“1000”标清；
     * @param terminalStream
     * @return
     */
    private String parseTerminalStream2TvStream(String terminalStream) {
        String tvStream = terminalStream;
        if (LetvStreamCommonConstants.CODE_NAME_1080p.equals(terminalStream)) {
            tvStream = LetvStreamCommonConstants.CODE_NAME_1080p6m;
        } else if (LetvStreamCommonConstants.CODE_NAME_800.equals(terminalStream)) {
            tvStream = LetvStreamCommonConstants.CODE_NAME_1000;
        }
        return tvStream;
    }

    /**
     * 从缓存中读取配置的播放鉴权token有效时长
     * @return
     */
    private Long getVideoPlayTokenExpireTime() {
        // Long tokenExpireTime =
        // this.cacheDao.get(CacheContentConstants.VIDEO_PLAY_TOKEN_EXPIRE_TIME_DEFAULT_KEY,
        // Long.class);
        Long tokenExpireTime = null;// this.facadeCacheDao.getVideoCacheDao().getVideoPlayTokenExpireTime();
        if (tokenExpireTime == null) {
            tokenExpireTime = VideoConstants.VIDEO_PLAY_TOKEN_EXPIRE_TIME_DEFAULT;
        }
        return tokenExpireTime;
    }

    /**
     * 判断设备系列是否支持4K；包含更新4K设备信息操作
     * @return
     */
    private boolean isDeviceSupport4K(CommonParam commonParam) {
        return isDeviceSupport4K(commonParam, true);
    }

    private boolean isDeviceSupport4K(CommonParam commonParam, Boolean support4k) {
        this.updateSupport4KDevice(commonParam);
        return support4k && TerminalTool.isDeviceSupport4K(commonParam.getTerminalSeries());
    }

    /**
     * 更新4K设备信息
     * @return
     */
    private boolean updateSupport4KDevice(final CommonParam commonParam) {
        boolean result = false;
        if ((System.currentTimeMillis() - TerminalConstant.SUPPORT_4K_TERMINAL_SERIES_LASTUPDATE_TIME) > TerminalConstant.SUPPORT_4K_TERMINAL_SERIES_UPDATE_INTERVAL) {
            if (TerminalConstant.SUPPORT_4K_TERMINAL_SERIES_UPDATE_Lock.tryLock()) {
                log.info("updateSupport4KDevice_" + commonParam.getMac() + "_" + commonParam.getUserId()
                        + ": updateSupport4KDevice locked.");
                try {
                    // Set<String> terminalSeries =
                    // this.facadeTpDao.getTerminalTpDao().getSupport4KDevices();
                    // result =
                    // TerminalTool.updateSupport4KDevices(terminalSeries);

                    new Thread() {
                        @Override
                        public void run() {
                            PlayService.this.reloadSupport4KDeviceInfo(commonParam);
                        }

                        ;
                    }.start();

                    TerminalConstant.SUPPORT_4K_TERMINAL_SERIES_LASTUPDATE_TIME = System.currentTimeMillis();

                    result = true;
                } catch (Exception e) {
                    log.error("updateSupport4KDevice_" + commonParam.getMac() + "_" + commonParam.getUserId()
                            + "updateSupport4KDevice error", e);
                } finally {
                    TerminalConstant.SUPPORT_4K_TERMINAL_SERIES_UPDATE_Lock.unlock();
                    log.info("updateSupport4KDevice_" + commonParam.getMac() + "_" + commonParam.getUserId()
                            + ": updateSupport4KDevice unlocked.");
                }
            }
        }
        return result;
    }

    private boolean reloadSupport4KDeviceInfo(final CommonParam commonParam) {
        boolean result = false;
        long cur = System.currentTimeMillis();
        Set<String> terminalSeries = this.facadeTpDao.getTerminalTpDao().getSupport4KDevices();
        result = TerminalTool.updateSupport4KDevices(terminalSeries);
        this.log.info("updateSupport4KDevice_" + commonParam.getMac() + "update support 4K devoce cost: "
                + (cur - TerminalConstant.SUPPORT_4K_TERMINAL_SERIES_LASTUPDATE_TIME));
        return result;
    }

    /**
     * play video of touping
     * @param response
     * @param videoId
     * @param imei
     * @param routerId
     * @param model
     * @param support3d
     * @param clientRequestStream
     * @param commonParam
     */
    private void playVideoOfTouping(Response<VideoDto> response, Long videoId, String imei, String routerId,
            Integer model, Boolean support3d, String clientRequestStream, CommonParam commonParam,
            boolean isThirdParty, Boolean support4k) {
        String logPrefix = "playVideoOfTouping_" + commonParam.getMac() + "_" + commonParam.getUserId() + "_" + videoId;
        log.info(logPrefix + "_touping_" + imei + "_" + routerId);
        /*
         * 投屏播放中，涉及到的码流问题：
         * 1.领先版中，“1080P”指1080p，TV版中“1080P”指1080p6m、1080p3m，显示1080p6m，
         * 实际使用1080p3m；“标清”指800p，TV版中“标清”指1000p；需要转换
         * 2.领先版中有“极速”指180p，TV版中没有，不参与投屏；
         * 3.领先版中有“2K”指2k，TV版中没有，需要降码流到1080p；
         * 4.领先版中码流顺序由高到低为"4K#2K#3D*#1080p#720p#1300p#1000p#800p#350#180"，
         * 则当播放4K时，先按TV版规则（考虑设备支持信息）降码流；如果4K没有降码流，则实际到
         * 领先版的播放响应，可能是4K也可能是2K，其中2K对TV版无意义，需要重新降码流并请求播放；当播放2k时，遵守3的规则；
         */
        // 第一次播放，包含4K播放尝试
        this.getTouPingPlay(videoId, model, imei, clientRequestStream, clientRequestStream, response, routerId,
                support3d, logPrefix, commonParam, isThirdParty, support4k);
        if (response.getResultStatus() != null && ErrorCodeCommonUtil.RESPONSE_SUCCESS == response.getResultStatus()) {
            // 响应成功，检查是否是播了2K
            VideoDto tryVideoPlay = response.getData();
            if (tryVideoPlay != null) {
                String currentRequestStream = tryVideoPlay.getCurrentStream();
                if (LetvStreamCommonConstants.CODE_NAME_LETV_LEADING_2K.equalsIgnoreCase(currentRequestStream)
                        || LetvStreamCommonConstants.CODE_NAME_LETV_LEADING_2K_H265
                                .equalsIgnoreCase(currentRequestStream)) {
                    // 如果返回的是2K，则需要降码流再播一次
                    this.getTouPingPlay(videoId, model, imei, clientRequestStream, currentRequestStream, response,
                            routerId, support3d, logPrefix, commonParam, isThirdParty, support4k);
                    if (response.getResultStatus() != null
                            && ErrorCodeCommonUtil.RESPONSE_SUCCESS == response.getResultStatus()) {
                        VideoDto reviseVideoPlay = response.getData();
                        if (!StringUtils.equals(currentRequestStream, reviseVideoPlay.getCurrentStream())) {
                            reviseVideoPlay.setHasBelow(VideoConstants.HAS_BELOW_YES);
                            reviseVideoPlay.setCurrentStream(this.parseTerminalStream2TvStream(reviseVideoPlay
                                    .getCurrentStream()));
                            Object[] codeMap = {
                                    LetvStreamCommonConstants.nameOf(clientRequestStream, commonParam.getLangcode()),
                                    LetvStreamCommonConstants.nameOf(reviseVideoPlay.getCurrentStream(),
                                            commonParam.getLangcode()) };
                            String tipMsg = MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_REDUCESTREAM,
                                    commonParam.getLangcode(), codeMap);
                            reviseVideoPlay.setTipMsg(tipMsg);
                        } else {
                            // 如果重试没有降码流，就复用原降码流信息
                            reviseVideoPlay.setHasBelow(tryVideoPlay.getHasBelow());
                            reviseVideoPlay.setTipMsg(tryVideoPlay.getTipMsg());
                        }
                    }
                }
            }
        }
    }

    /**
     * 从视频码流中获取匹配到的可以播放的第一个全景码流
     * @param playStreams
     * @return
     */
    public String getAvaliable360Stream(String playStreams) {
        String avaliableStream = null;
        playStreams = StringUtils.trimToEmpty(playStreams);
        String[] streams = StringUtils.split(playStreams, ",");
        if (streams != null && streams.length > 0) {
            for (String stream : streams) {
                if (stream != null && stream.endsWith("_360")) {
                    avaliableStream = VideoCommonUtil.filterLetvValidStream(stream,
                            VideoCommonUtil.FILTER_VALID_STARTM_MODE_DISPLAY);
                    if (avaliableStream != null) {
                        break;
                    }
                }
            }
        }
        return avaliableStream;
    }

    private String getPauseImg4Us() {
        // 设置非会员播放卡顿图片
        String pauseImg = this.getTvNonMemberGuideImg(VideoConstants.NonMemberGuideType.userType_4);
        if (StringUtil.isBlank(pauseImg)) {
            pauseImg = this.facadeCacheDao.getVideoCacheDao().getPauseImg();
        }
        return pauseImg;
    }

    /**
     * get video play final stream code
     * @param videoPlay
     *            video play info model
     * @param clientStream
     *            play request stream
     * @param streams
     *            streams of videoMysqlTable
     * @param model
     *            play model,1--child,2--touping
     * @param support3d
     *            if the device support 3d video
     * @param commonParam
     * @return
     */
    private String getVideoPlayStream(VideoDto videoPlay, String clientStream, String streams, Integer model,
            Boolean support3d, CommonParam commonParam) {
        String playStream = "";
        videoPlay.setHasBelow(VideoConstants.HAS_BELOW_NO);// 默认标识不降码流
        if ((model != null) && (VideoConstants.PLAY_MODEL_TOUPING == model)) {
            // touping play
            playStream = this.getVideoPlayStream4Touping(videoPlay, clientStream, model, streams, support3d,
                    commonParam);
        } else {// normal play
            if (TerminalUtil.isLetvUs(commonParam)) {// us version
                support3d = null;// us version doesn't deal with 3d stream
                playStream = this
                        .getVideoPlayStream4Us(videoPlay, clientStream, model, streams, support3d, commonParam);
            } else {// mainland version
                playStream = this.getVideoPlayStream4Normal(videoPlay, clientStream, model, streams, support3d,
                        commonParam);
            }
        }
        return playStream;
    }

    private String getVideoPlayStream4Touping(VideoDto videoPlay, String clientStream, Integer model, String streams,
            Boolean support3d, CommonParam commonParam) {
        String playStream = null;
        if (StringUtil.isBlank(clientStream)) {
            playStream = this.getDefaultVideoPlayStream(streams, LetvStreamCommonConstants.DEFAULT_STREAM);
        } else if (LetvStreamCommonConstants.CODE_NAME_LETV_LEADING_2K.equalsIgnoreCase(clientStream)
                || LetvStreamCommonConstants.CODE_NAME_LETV_LEADING_2K_H265.equalsIgnoreCase(clientStream)) {
            playStream = LetvStreamCommonConstants.CODE_NAME_1080p6m;
            videoPlay.setHasBelow(VideoConstants.HAS_BELOW_YES);
            Object[] codeMap = { LetvStreamCommonConstants.nameOf(clientStream, commonParam.getLangcode()),
                    LetvStreamCommonConstants.nameOf(playStream, commonParam.getLangcode()) };
            String tipMsg = MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_REDUCESTREAM,
                    commonParam.getLangcode(), codeMap);
            videoPlay.setTipMsg(tipMsg);
        } else if (LetvStreamCommonConstants.CODE_NAME_1080p6m.equalsIgnoreCase(clientStream)
                && streams.contains(LetvStreamCommonConstants.CODE_NAME_1080p3m)) {
            playStream = LetvStreamCommonConstants.CODE_NAME_1080p6m;
        } else if (support3d != null
                && Boolean.FALSE == support3d
                && (LetvStreamCommonConstants.CODE_NAME_3d720p.equalsIgnoreCase(clientStream)
                        || LetvStreamCommonConstants.CODE_NAME_3d1080p.equalsIgnoreCase(clientStream) || LetvStreamCommonConstants.CODE_NAME_3d1080p6M
                            .equalsIgnoreCase(clientStream))) {
            // 如果设备不支持3D，但是客户端又请求了3D码流，则需要将码流处理
            // 投屏模式下，之前传过来的码流中，3d码流断层，现有降码流逻辑（getReducedStream）会有升码流（到4K）的问题，这里修改一下可用码流
            streams = StringUtils.replace(new String(LetvStreamCommonConstants.PLAY_SORT_STREAM_FOR_TOUPING), "#", ",");
            streams.replaceAll(LetvStreamCommonConstants.CODE_NAME_3d720p, "");
            streams.replaceAll(LetvStreamCommonConstants.CODE_NAME_3d1080p, "");
            streams.replaceAll(LetvStreamCommonConstants.CODE_NAME_3d1080p6M, "");

            playStream = this.getReducedStream(clientStream, streams,
                    LetvStreamCommonConstants.PLAY_SORT_STREAM_FOR_TOUPING, support3d);

            videoPlay.setHasBelow(VideoConstants.HAS_BELOW_YES);
            Object[] codeMap = { LetvStreamCommonConstants.nameOf(clientStream, commonParam.getLangcode()),
                    LetvStreamCommonConstants.nameOf(playStream, commonParam.getLangcode()) };
            String tipMsg = MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_UNSUPPORT_4KSTREAM,
                    commonParam.getLangcode(), codeMap);
            videoPlay.setTipMsg(tipMsg);
        } else if (!ArrayUtils.contains(streams.split(","), clientStream)) {
            // 降码流处理，按照降码流顺序往下找，直到找到第一个视频支持的码流且码流排序在客户端请求的码流之后；其中，投屏需要特殊处理2K码流的问题
            playStream = this.getReducedStream(clientStream, streams,
                    LetvStreamCommonConstants.USER_SETTING_PLAY_STREAM, support3d);

            videoPlay.setHasBelow(VideoConstants.HAS_BELOW_YES);
            Object[] codeMap = { LetvStreamCommonConstants.nameOf(clientStream, commonParam.getLangcode()),
                    LetvStreamCommonConstants.nameOf(playStream, commonParam.getLangcode()) };
            String tipMsg = MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_REDUCESTREAM,
                    commonParam.getLangcode(), codeMap);
            videoPlay.setTipMsg(tipMsg);
        } else {
            if (streams.indexOf("4k") > -1 && "4k".equalsIgnoreCase(clientStream)
                    && !this.isDeviceSupport4K(commonParam)) {
                // 设备不支持4K且请求播放的码流为4K，降码流播放
                streams = streams.replace("4k", "");
                playStream = this.getReducedStream(clientStream, streams,
                        LetvStreamCommonConstants.USER_SETTING_PLAY_STREAM, support3d);

                videoPlay.setHasBelow(VideoConstants.HAS_BELOW_YES);
                Object[] codeMap = { LetvStreamCommonConstants.nameOf(clientStream, commonParam.getLangcode()),
                        LetvStreamCommonConstants.nameOf(playStream, commonParam.getLangcode()) };
                String tipMsg = MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_UNSUPPORT_4KSTREAM,
                        commonParam.getLangcode(), codeMap);
                videoPlay.setTipMsg(tipMsg);
            } else {
                playStream = clientStream;
            }
        }

        return playStream;
    }

    private String getVideoPlayStream4Normal(VideoDto videoPlay, String clientStream, Integer model, String streams,
            Boolean support3d, CommonParam commonParam) {
        String playStream = null;
        if (StringUtil.isBlank(clientStream)) {
            if (TerminalUtil.isLetvUs(commonParam)) {
                playStream = this.getDefaultVideoPlayStream(streams, LetvStreamCommonConstants.DEFAULT_STREAM_US);
            } else {
                playStream = this.getDefaultVideoPlayStream(streams, LetvStreamCommonConstants.DEFAULT_STREAM);
            }
        } else if (LetvStreamCommonConstants.CODE_NAME_1080p6m.equalsIgnoreCase(clientStream)
                && streams.contains(LetvStreamCommonConstants.CODE_NAME_1080p3m)) {
            playStream = LetvStreamCommonConstants.CODE_NAME_1080p6m;
        } else if (support3d != null
                && Boolean.FALSE == support3d
                && (LetvStreamCommonConstants.CODE_NAME_3d720p.equalsIgnoreCase(clientStream)
                        || LetvStreamCommonConstants.CODE_NAME_3d1080p.equalsIgnoreCase(clientStream) || LetvStreamCommonConstants.CODE_NAME_3d1080p6M
                            .equalsIgnoreCase(clientStream))) {
            // 如果设备不支持3D，但是客户端又请求了3D码流，则需要将码流处理
            streams.replaceAll(LetvStreamCommonConstants.CODE_NAME_3d720p, "");
            streams.replaceAll(LetvStreamCommonConstants.CODE_NAME_3d1080p, "");
            streams.replaceAll(LetvStreamCommonConstants.CODE_NAME_3d1080p6M, "");

            playStream = this.getReducedStream(clientStream, streams,
                    LetvStreamCommonConstants.LETV_COMMON_USER_SETTING_PLAY_STREAM, support3d);

            videoPlay.setHasBelow(VideoConstants.HAS_BELOW_YES);
            Object[] codeMap = { LetvStreamCommonConstants.nameOf(clientStream, commonParam.getLangcode()),
                    LetvStreamCommonConstants.nameOf(playStream, commonParam.getLangcode()) };
            String tipMsg = MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_UNSUPPORT_4KSTREAM,
                    commonParam.getLangcode(), codeMap);
            videoPlay.setTipMsg(tipMsg);
        } else if (!ArrayUtils.contains(streams.split(","), clientStream)) {
            playStream = this.getReducedStream(clientStream, streams, LetvStreamCommonConstants.PLAY_SORT_STREAM_T2,
                    support3d);

            videoPlay.setHasBelow(VideoConstants.HAS_BELOW_YES);
            Object[] codeMap = { LetvStreamCommonConstants.nameOf(clientStream, commonParam.getLangcode()),
                    LetvStreamCommonConstants.nameOf(playStream, commonParam.getLangcode()) };
            String tipMsg = MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_REDUCESTREAM,
                    commonParam.getLangcode(), codeMap);
            videoPlay.setTipMsg(tipMsg);
        } else {
            if (streams.indexOf("4k") > -1 && "4k".equalsIgnoreCase(clientStream)
                    && !this.isDeviceSupport4K(commonParam)) {
                // 设备不支持4K且请求播放的码流为4K，降码流播放
                streams = streams.replace("4k", "");
                playStream = this.getReducedStream(clientStream, streams,
                        LetvStreamCommonConstants.PLAY_SORT_STREAM_T2, support3d);

                videoPlay.setHasBelow(VideoConstants.HAS_BELOW_YES);
                Object[] codeMap = { LetvStreamCommonConstants.nameOf(clientStream, commonParam.getLangcode()),
                        LetvStreamCommonConstants.nameOf(playStream, commonParam.getLangcode()) };
                String tipMsg = MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_UNSUPPORT_4KSTREAM,
                        commonParam.getLangcode(), codeMap);
                videoPlay.setTipMsg(tipMsg);
            } else {
                playStream = clientStream;
            }
        }
        return playStream;
    }

    private String getVideoPlayStream4Us(VideoDto videoPlay, String clientStream, Integer model, String streams,
            Boolean support3d, CommonParam commonParam) {
        String playStream = null;
        if (StringUtil.isBlank(clientStream)) {
            playStream = this.getDefaultVideoPlayStream(streams, LetvStreamCommonConstants.DEFAULT_STREAM_US);
        } else if (LetvStreamCommonConstants.CODE_NAME_1080p6m.equalsIgnoreCase(clientStream)
                && streams.contains(LetvStreamCommonConstants.CODE_NAME_1080p3m)) {
            playStream = LetvStreamCommonConstants.CODE_NAME_1080p6m;
        } else if (support3d != null
                && Boolean.FALSE == support3d
                && (LetvStreamCommonConstants.CODE_NAME_3d720p.equalsIgnoreCase(clientStream)
                        || LetvStreamCommonConstants.CODE_NAME_3d1080p.equalsIgnoreCase(clientStream) || LetvStreamCommonConstants.CODE_NAME_3d1080p6M
                            .equalsIgnoreCase(clientStream))) {
            // 如果设备不支持3D，但是客户端又请求了3D码流，则需要将码流处理
            streams = streams.replaceAll(LetvStreamCommonConstants.CODE_NAME_3d720p, "")
                    .replaceAll(LetvStreamCommonConstants.CODE_NAME_3d1080p, "")
                    .replaceAll(LetvStreamCommonConstants.CODE_NAME_3d1080p6M, "");

            playStream = this.getReducedStream(clientStream, streams,
                    LetvStreamCommonConstants.LETV_COMMON_USER_SETTING_PLAY_STREAM, support3d);

            videoPlay.setHasBelow(VideoConstants.HAS_BELOW_YES);
            Object[] codeMap = { LetvStreamCommonConstants.nameOf(clientStream, commonParam.getLangcode()),
                    LetvStreamCommonConstants.nameOf(playStream, commonParam.getLangcode()) };
            String tipMsg = MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_UNSUPPORT_4KSTREAM,
                    commonParam.getLangcode(), codeMap);
            videoPlay.setTipMsg(tipMsg);
        } else if (!ArrayUtils.contains(streams.split(","), clientStream)) {
            playStream = this.getReducedStream(clientStream, streams, LetvStreamCommonConstants.PLAY_SORT_STREAM_T2_US,
                    support3d);

            videoPlay.setHasBelow(VideoConstants.HAS_BELOW_YES);
            Object[] codeMap = { LetvStreamCommonConstants.nameOf(clientStream, commonParam.getLangcode()),
                    LetvStreamCommonConstants.nameOf(playStream, commonParam.getLangcode()) };
            String tipMsg = MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_REDUCESTREAM,
                    commonParam.getLangcode(), codeMap);
            videoPlay.setTipMsg(tipMsg);
        } else {
            if (streams.indexOf("4k") > -1 && "4k".equalsIgnoreCase(clientStream)
                    && !this.isDeviceSupport4K(commonParam)) {
                // 设备不支持4K且请求播放的码流为4K，降码流播放
                streams = streams.replace("4k", "");
                playStream = this.getReducedStream(clientStream, streams,
                        LetvStreamCommonConstants.PLAY_SORT_STREAM_T2_US, support3d);

                videoPlay.setHasBelow(VideoConstants.HAS_BELOW_YES);
                Object[] codeMap = { LetvStreamCommonConstants.nameOf(clientStream, commonParam.getLangcode()),
                        LetvStreamCommonConstants.nameOf(playStream, commonParam.getLangcode()) };
                String tipMsg = MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_UNSUPPORT_4KSTREAM,
                        commonParam.getLangcode(), codeMap);
                videoPlay.setTipMsg(tipMsg);
            } else {
                playStream = clientStream;
            }
        }
        return playStream;
    }

    private VideoDto getTrailerVideoVideoDto(VideoMysqlTable videoMysqlTable, String clientRequestStream,
            Integer model, Boolean support3d, String supportStream, CommonParam commonParam, Boolean support4k) {
        log.info("----------getTrailerVideoVideoDto-------begin------ ");
        long begin = System.currentTimeMillis();
        VideoDto videoPlay = new VideoDto();

        Long videoId = videoMysqlTable.getId();
        String videoPlayStreams = videoMysqlTable.getPlay_streams();
        Long pid = videoMysqlTable.getPid();
        String videoMidStreams = videoMysqlTable.getMid_streams();
        String drmFlagId = videoMysqlTable.getDrmFlagId();

        videoPlay.setVideoId(String.valueOf(videoId));

        if (StringUtil.isNotBlank(videoMysqlTable.getPic_original())) {
            videoPlay.setImg(videoMysqlTable.getPic_original());
        }

        if (StringUtil.isNotBlank(videoMysqlTable.getTv_title())) {
            videoPlay.setName(videoMysqlTable.getTv_title());
        }

        if (StringUtil.isNotBlank(videoMysqlTable.getSub_title())) {
            videoPlay.setSubName(videoMysqlTable.getSub_title());
        }
        String playStream = this.getPlayStreamForTrailer(videoPlayStreams, videoPlay, clientRequestStream, model,
                support3d, supportStream, commonParam, support4k);

        String wcode = commonParam.getWcode();
        boolean isMarlin = VideoUtil.isMarlin(drmFlagId, wcode);
        // 对一些特殊的客户端做地区播放限制放行
        String clientIp = this.unlimitedClientId(commonParam) ? null : commonParam.getClientIp();
        // 获得媒资视频文件播放信息
        MmsStore mmsStore = null;
        // ===========20170702 强制降码流(B)============//
        boolean force2ReduceStream = true;
        if (force2ReduceStream
                && StringUtils.isNotEmpty(clientRequestStream)
                && clientRequestStream.indexOf("3d") == -1
                && clientRequestStream.indexOf("db") == -1
                && ((clientRequestStream.indexOf("4k") == -1) || (clientRequestStream.indexOf("4k") != -1 && !support4k))) {
            String defaultStream = LetvStreamCommonConstants.DEFAULT_STREAM;
            if (clientRequestStream.indexOf("4k") != -1 && !support4k) {
                defaultStream = LetvStreamCommonConstants.LETV_COMMON_USER_SETTING_PLAY_STREAM;
            }
            String playStreamTmp = this.reduceVideoPlayStream(clientRequestStream, defaultStream);
            mmsStore = this.getMmsFilePlayInfoVCache(videoId, pid, videoPlayStreams, videoMidStreams, playStreamTmp,
                    clientIp, isMarlin, model, null, videoPlay, commonParam);
            if (null == mmsStore || null == mmsStore.getData() || mmsStore.getData().size() == 0) {
                mmsStore = this.getMmsFilePlayInfoVCache(videoId, pid, videoPlayStreams, videoMidStreams, playStream,
                        clientIp, isMarlin, model, null, videoPlay, commonParam);
            }
        } else {
            mmsStore = this.getMmsFilePlayInfoVCache(videoId, pid, videoPlayStreams, videoMidStreams, playStream,
                    clientIp, isMarlin, model, null, videoPlay, commonParam);
        }
        int validateMmsStore = VideoUtil.validateMmsStore(mmsStore);

        if (VideoUtil.validateMmsStore.SUCCESS == validateMmsStore) {

            MmsFile mmsFile = VideoUtil.getMmsFileByVTypeOrder(playStream, mmsStore);
            PlayAuthV2 userPlayAuth = new PlayAuthV2(PlayAuthV2.STATUS_PASS);
            // 从媒资视频文件解析播放信息
            setVideoPlayFromMmsFile(mmsStore, mmsFile, videoPlay, playStream, userPlayAuth, isMarlin, null, null, null,
                    null, commonParam);
            log.info("----------getTrailerVideoVideoDto-------end------ " + (System.currentTimeMillis() - begin));
            return videoPlay;
        }
        return null;
    }

    public String getPlayStreamForTrailer(String videoPlayStreams, VideoDto videoPlay, String clientRequestStream,
            Integer model, Boolean support3d, String supportStream, CommonParam commonParam, Boolean support4k) {
        boolean support360Stream = terminalService
                .isTerminalSeriesSupport360Stream(commonParam);
        // 获得播放码流，包括降码流处理和设置降码流提示
        String playStream = this.getPlayStream(clientRequestStream, videoPlayStreams, videoPlay, model, support3d,
                supportStream, support360Stream, commonParam, support4k);

        // 2016-05-30，全景码流兼容逻辑，当使用普通码流播放无普通码流的视频时（如配置在运营位且未指定起播全景码流），服务端兼容播放
        if (StringUtils.isNotEmpty(clientRequestStream) && StringUtils.isEmpty(playStream)
                && StringUtils.contains(videoPlayStreams, "_360") && support360Stream) {
            playStream = this.getAvaliable360Stream(videoPlayStreams);
            videoPlay.setCurrentStream(playStream);
            Object[] codeMap = { LetvStreamCommonConstants.nameOf(clientRequestStream, commonParam.getLangcode()),
                    LetvStreamCommonConstants.nameOf(playStream, commonParam.getLangcode()) };
            String tipMsg = MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_REDUCESTREAM,
                    commonParam.getLangcode(), codeMap);
            videoPlay.setTipMsg(tipMsg);

            playStream = this.getPlayStream(playStream, videoPlayStreams, videoPlay, model, support3d, supportStream,
                    support360Stream, commonParam, support4k);

            // 注意，这一步放后面，是因为默认选码流已经是一种降码流操作，而其下一步的getPlayStream操作有可能不再进行降码流而会将hasBelow设置为0
            videoPlay.setHasBelow(VideoConstants.HAS_BELOW_YES);
        }
        return playStream;
    }

    /**
     * 获得连续播放时的预告片由cms配置
     * @param clientRequestStream
     * @param supportStream
     * @param model
     * @param support3d
     * @param commonParam
     * @return
     */
    private TrailerVideoDto getTrailerVideoDto(String clientRequestStream, String supportStream, Integer model,
                                               Boolean support3d, CommonParam commonParam, Boolean support4k, Integer categoryId) {

        /*
         * TrailerVideoDto mTrailerVideoDto =
         * this.facadeCacheDao.getVideoCacheDao
         * ().getTrailerVideoDto(clientRequestStream, model, support3d,
         * supportStream, commonParam);
         * if (mTrailerVideoDto != null) {
         * return mTrailerVideoDto;
         * }
         */

        // VideoMysqlTable videoMysqlTable =
        // this.getVideoMysqlTableForTrailerVideo(commonParam);
        VideoMysqlTable videoMysqlTable = this.getVideoMysqlTableForTrailerVideoV2(commonParam, categoryId);
        if (videoMysqlTable == null || videoMysqlTable.getVideo_info_id() == null) {
            return null;
        }
        if (videoMysqlTable.getVideo_info_id().intValue() == -1) {
            /*
             * TrailerVideoDto mVideoDto = new TrailerVideoDto();
             * this.facadeCacheDao.getVideoCacheDao().setTrailerVideoDto(
             * clientRequestStream, model, support3d, supportStream,
             * commonParam, mVideoDto);
             * return mVideoDto;
             */
            return null;
        }
        TrailerVideoDto mVideoDto = this.getTrailerVideoNet(videoMysqlTable, clientRequestStream, model, support3d,
                supportStream, commonParam, support4k);
        /*
         * if (mVideoDto != null) {
         * this.facadeCacheDao.getVideoCacheDao().setTrailerVideoDto(
         * clientRequestStream, model, support3d, supportStream, commonParam,
         * mVideoDto);
         * }
         */
        return mVideoDto;
    }

    public Response<TrailerVideoDto> setTrailerVideoDto(Long videoId, String clientRequestStream, String supportStream,
            Integer model, Boolean support3d, CommonParam commonParam, Boolean support4k, Integer channelId,
            Integer type) {
        Response<TrailerVideoDto> response = new Response<TrailerVideoDto>();
        if (videoId == null) {
            response.setResultStatus(BaseResponse.STATUS_ERROR);
            String errorCode = VideoConstants.VIDEO_PLAY_NULL;
            response.setErrCode(errorCode);
            return response;
        }
        if (channelId == null || type == null) {
            VideoMysqlTable videoMysqlTable = albumVideoAccess.getVideo(videoId, commonParam);
            if (videoMysqlTable != null) {
                channelId = videoMysqlTable.getCategory();
                type = videoMysqlTable.getVideo_type();
            }
        }

        if (channelId == null || type == null) {
            response.setResultStatus(BaseResponse.STATUS_ERROR);
            String errorCode = VideoConstants.VIDEO_PLAY_NULL;
            response.setErrCode(errorCode);
            return response;
        }
        response.setResultStatus(BaseResponse.STATUS_OK);
        if (type == MmsTpConstant.MMS_VIDEO_TYPE_ZHENG_PIAN) {
            /*
             * 连续播放时显示的预告片，只有当是电影、电视剧、综艺、动漫的正片才出
             */
            if (channelId != null
                    && (channelId == VideoConstants.Category.FILM || channelId == VideoConstants.Category.TV
                            || channelId == VideoConstants.Category.VARIETY || channelId == VideoConstants.Category.CARTOON)) {
                // TrailerVideoDto trailerVideoDto =
                // this.getTrailerVideoDto(clientRequestStream, supportStream,
                // model, support3d, commonParam, support4k);
                TrailerVideoDto trailerVideoDto = this.getTrailerVideoDto(clientRequestStream, supportStream, model,
                        support3d, commonParam, support4k, channelId);
                if (trailerVideoDto != null && StringUtil.isNotBlank(trailerVideoDto.getPlayUrl())) {
                    response.setData(trailerVideoDto);
                }
            }
        }
        return response;
    }

    /**
     * 获得连续播放预告片的video数据
     * @param commonParam
     * @return
     */
    private VideoMysqlTable getVideoMysqlTableForTrailerVideo(CommonParam commonParam) {
        VideoMysqlTable videoMysqlTable = this.facadeCacheDao.getVideoCacheDao().getTrailerVideoMysqlTable(commonParam);
        if (videoMysqlTable != null) {
            return videoMysqlTable;
        }
        videoMysqlTable = this.getVideoMysqlTableForTrailerVideoWithNet(commonParam);
        if (videoMysqlTable != null) {
            this.facadeCacheDao.getVideoCacheDao().setTrailerVideoMysqlTable(commonParam, videoMysqlTable);
        }
        return videoMysqlTable;
    }

    private VideoMysqlTable getVideoMysqlTableForTrailerVideoV2(CommonParam commonParam, Integer categoryId) {
        VideoMysqlTable videoMysqlTable = this.facadeCacheDao.getVideoCacheDao().getTrailerVideoMysqlTableV2(
                commonParam, categoryId);
        if (videoMysqlTable != null) {
            return videoMysqlTable;
        }
        videoMysqlTable = this.getVideoMysqlTableForTrailerVideoWithNetV2(commonParam, categoryId);
        if (videoMysqlTable != null) {
            this.facadeCacheDao.getVideoCacheDao()
                    .setTrailerVideoMysqlTableV2(commonParam, videoMysqlTable, categoryId);
        }
        return videoMysqlTable;
    }

    /**
     * 获得连续播放预告片的video数据
     * @param commonParam
     * @return
     */
    private VideoMysqlTable getVideoMysqlTableForTrailerVideoWithNet(CommonParam commonParam) {
        String blockId = ApplicationUtils.get(ApplicationConstants.CMS_BLOCKID_TRAILER_VIDEO_TV_GET);
        CmsBlockTpResponse cmsBlockTpResponse = this.facadeTpDao.getCmsTpDao().getCmsBlockNewByIdV2(blockId,
                commonParam);
        Long vid = null;
        String title = null;
        String sub_title = null;
        String img = null;
        if (cmsBlockTpResponse != null) {
            if (cmsBlockTpResponse.getBlockContent() != null && cmsBlockTpResponse.getBlockContent().size() > 0) {
                for (CmsBlockContentTpResponse cmsBlockContentTpResponse : cmsBlockTpResponse.getBlockContent()) {
                    String content = cmsBlockContentTpResponse.getContent();
                    title = cmsBlockContentTpResponse.getTitle();
                    sub_title = cmsBlockContentTpResponse.getSubTitle();
                    img = cmsBlockContentTpResponse.getTvPic();
                    if (StringUtil.isNotBlank(content)) {
                        vid = StringUtil.toLong(content);
                        break;
                    }
                }
            }
            if (vid == null) {// cms没有配置相关数据，也设置缓存，避免次次去取
                VideoMysqlTable mVideoMysqlTable = new VideoMysqlTable();
                mVideoMysqlTable.setVideo_info_id(-1L);
                return mVideoMysqlTable;
            }
        }
        if (vid == null) {
            return null;
        }
        /*
         * 先查数据库,否则查vrs
         */
        VideoMysqlTable tempVideoMysql = albumVideoAccess.getVideo(vid, commonParam);
        VideoMysqlTable videoMysql = new VideoMysqlTable();
        if (tempVideoMysql == null) {
            this.initVideoMysqlTableFormVRS(videoMysql, vid);
        } else {
            videoMysql.setId(tempVideoMysql.getId());
            videoMysql.setVideo_info_id(tempVideoMysql.getVideo_info_id());
            videoMysql.setPid(tempVideoMysql.getPid());
            videoMysql.setMid(tempVideoMysql.getMid());
            videoMysql.setPlay_streams(tempVideoMysql.getPlay_streams());
            videoMysql.setMid_streams(tempVideoMysql.getMid_streams());
            videoMysql.setDrmFlagId(tempVideoMysql.getDrmFlagId());
        }

        videoMysql.setTv_title(title);
        videoMysql.setSub_title(sub_title);
        videoMysql.setPic_original(img);
        /*
         * if(StringUtil.isNotBlank(title)){
         * videoMysql.setTv_title(title);
         * }
         * if(StringUtil.isNotBlank(sub_title)){
         * videoMysql.setSub_title(sub_title);
         * }
         * if(StringUtil.isNotBlank(img)){
         * videoMysql.setPic_original(img);
         * }
         */
        if (videoMysql.getVideo_info_id() == null && videoMysql.getId() == null) {
            return null;
        }
        return videoMysql;
    }

    private VideoMysqlTable getVideoMysqlTableForTrailerVideoWithNetV2(CommonParam commonParam, Integer categoryId) {
        String blockId = ApplicationUtils.get(ApplicationConstants.CMS_BLOCKID_TRAILER_VIDEO_TV_GET + "." + categoryId,
                "");
        if (StringUtil.isBlank(blockId)) {
            return null;
        }
        CmsBlockTpResponse cmsBlockTpResponse = this.facadeTpDao.getCmsTpDao().getCmsBlockNewByIdV2(blockId,
                commonParam);
        Long vid = null;
        String title = null;
        String sub_title = null;
        String img = null;
        if (cmsBlockTpResponse != null) {
            if (cmsBlockTpResponse.getBlockContent() != null && cmsBlockTpResponse.getBlockContent().size() > 0) {
                for (CmsBlockContentTpResponse cmsBlockContentTpResponse : cmsBlockTpResponse.getBlockContent()) {
                    String content = cmsBlockContentTpResponse.getContent();
                    title = cmsBlockContentTpResponse.getTitle();
                    sub_title = cmsBlockContentTpResponse.getSubTitle();
                    img = cmsBlockContentTpResponse.getTvPic();
                    if (StringUtil.isNotBlank(content)) {
                        vid = StringUtil.toLong(content);
                        break;
                    }
                }
            }
            if (vid == null) {// cms没有配置相关数据，也设置缓存，避免次次去取
                VideoMysqlTable mVideoMysqlTable = new VideoMysqlTable();
                mVideoMysqlTable.setVideo_info_id(-1L);
                return mVideoMysqlTable;
            }
        }
        if (vid == null) {
            return null;
        }
        /*
         * 先查数据库,否则查vrs
         */
        VideoMysqlTable tempVideoMysql = albumVideoAccess.getVideo(vid, commonParam);
        VideoMysqlTable videoMysql = new VideoMysqlTable();
        if (tempVideoMysql == null) {
            this.initVideoMysqlTableFormVRS(videoMysql, vid);
        } else {
            videoMysql.setId(tempVideoMysql.getId());
            videoMysql.setVideo_info_id(tempVideoMysql.getVideo_info_id());
            videoMysql.setPid(tempVideoMysql.getPid());
            videoMysql.setMid(tempVideoMysql.getMid());
            videoMysql.setPlay_streams(tempVideoMysql.getPlay_streams());
            videoMysql.setMid_streams(tempVideoMysql.getMid_streams());
            videoMysql.setDrmFlagId(tempVideoMysql.getDrmFlagId());
        }

        videoMysql.setTv_title(title);
        videoMysql.setSub_title(sub_title);
        videoMysql.setPic_original(img);
        /*
         * if(StringUtil.isNotBlank(title)){
         * videoMysql.setTv_title(title);
         * }
         * if(StringUtil.isNotBlank(sub_title)){
         * videoMysql.setSub_title(sub_title);
         * }
         * if(StringUtil.isNotBlank(img)){
         * videoMysql.setPic_original(img);
         * }
         */
        if (videoMysql.getVideo_info_id() == null && videoMysql.getId() == null) {
            return null;
        }
        return videoMysql;
    }

    /**
     * 根据vid取vrs数据拼装成VideoMysqlTable
     * @param vid
     * @return
     */
    private VideoMysqlTable initVideoMysqlTableFormVRS(VideoMysqlTable videoMysqlTable, Long vid) {
        MmsResponse<MmsVideoInfo> mmsResponse = this.facadeTpDao.getVideoTpDao().getMmsVideoById(vid, 2, 0);
        if (mmsResponse == null || mmsResponse.getData() == null) {
            return null;
        }
        MmsVideoInfo mmsVideoInfo = mmsResponse.getData();
        videoMysqlTable.setId(vid);
        videoMysqlTable.setVideo_info_id(vid);
        videoMysqlTable.setPid(mmsVideoInfo.getPid());
        videoMysqlTable.setItv_album_id(mmsVideoInfo.getPid());
        videoMysqlTable.setMid(mmsVideoInfo.getMid());
        // 媒资id,去除首尾","
        if (videoMysqlTable.getMid() != null && videoMysqlTable.getMid().startsWith(",")) {
            videoMysqlTable.setMid(videoMysqlTable.getMid().substring(1));
        }
        if (videoMysqlTable.getMid() != null && videoMysqlTable.getMid().endsWith(",")) {
            videoMysqlTable.setMid(videoMysqlTable.getMid().substring(0, videoMysqlTable.getMid().length() - 1));
        }

        StringWriter str = null;
        /*
         * 2016-03-25测试环境添加逻辑，为应对媒资测试环境码流不全问题，TV服务端采用从线上同步码流数据，测试环境同步基础数据的方式规避;
         * 该逻辑仅在测试环境使用，严禁合并至trunk--by YiKun
         */
        // 码流-视频所有码流，统一格式为",{码流},{码流}..."，最后截取开头与结尾的","，保留"{码流},{码流}"形式入库
        StringBuffer playStreams = new StringBuffer(",");
        Map<Integer, String> midStreams = new HashMap<Integer, String>();// 媒资id对应的码流信息，以防止多个媒资id的时候播放找不到媒资id的问题
        Map<Integer, List<MmsVideoCode>> videocode = mmsVideoInfo.getVideocode();
        if (!CollectionUtils.isEmpty(videocode)) {
            for (Integer mid : videocode.keySet()) {
                List<MmsVideoCode> midVideoCodes = videocode.get(mid);
                StringBuffer midStreamsBuffer = new StringBuffer(",");
                if (midVideoCodes != null && midVideoCodes.size() > 0) {
                    for (MmsVideoCode mmsVideoCode : midVideoCodes) {
                        String status = mmsVideoCode.getStatus();
                        if (status == null || !status.contains("300006")) {// 没有发布成功的不保存
                            continue;
                        }
                        Map<Integer, String> code = mmsVideoCode.getCode();
                        if (code != null && code.size() > 0) {
                            for (String stream : code.values()) {

                                String filteredstream = VideoCommonUtil.filterValidStream(stream,
                                        VideoCommonUtil.FILTER_VALID_STARTM_MODE_SAVE);
                                if (filteredstream == null) {
                                    continue;
                                }
                                if (playStreams.indexOf("," + filteredstream + ",") < 0) {
                                    playStreams.append(filteredstream).append(",");
                                }
                                if (midStreamsBuffer.indexOf("," + filteredstream + ",") < 0) {
                                    midStreamsBuffer.append(filteredstream).append(",");
                                }
                            }
                        }

                    }
                }
                String midStreamsStr = midStreamsBuffer.toString();
                if (midStreamsStr.startsWith(",")) {
                    // 去开头的","
                    midStreamsStr = midStreamsStr.substring(1);
                }
                if (midStreamsStr.endsWith(",")) {
                    midStreamsStr = midStreamsStr.substring(0, midStreamsStr.length() - 1);
                }
                midStreams.put(mid, midStreamsStr);
            }
        }

        String playStreamsStr = playStreams.toString();
        if (playStreamsStr.startsWith(",")) {
            // 去开头的","
            playStreamsStr = playStreamsStr.substring(1);
        }
        if (playStreamsStr.endsWith(",")) {
            // 去结尾的","
            playStreamsStr = playStreamsStr.substring(0, playStreamsStr.length() - 1);
        }

        videoMysqlTable.setPlay_streams(playStreamsStr);

        str = new StringWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(str, midStreams);
        } catch (Exception e) {
        }
        videoMysqlTable.setMid_streams(str.toString());

        if (!CollectionUtils.isEmpty(mmsResponse.getData().getDrmFlag())) {
            videoMysqlTable.setDrmFlagId(mmsResponse.getData().getDrmFlag().keySet().toString());
        }

        return videoMysqlTable;
    }

    private TrailerVideoDto getTrailerVideoNet(VideoMysqlTable videoMysqlTable, String clientRequestStream,
            Integer model, Boolean support3d, String supportStream, CommonParam commonParam, Boolean support4k) {

        TrailerVideoDto mTrailerVideoDto = null;
        VideoDto videoDto = this.getTrailerVideoVideoDto(videoMysqlTable, clientRequestStream, model, support3d,
                supportStream, commonParam, support4k);
        if (videoDto != null) {
            mTrailerVideoDto = new TrailerVideoDto();
            mTrailerVideoDto.setVideoId(videoDto.getVideoId());
            mTrailerVideoDto.setImg(videoDto.getImg());
            mTrailerVideoDto.setName(videoDto.getName());
            mTrailerVideoDto.setSubName(videoDto.getSubName());
            mTrailerVideoDto.setPlayUrl(videoDto.getPlayUrl());
            mTrailerVideoDto.setDuration(videoDto.getDuration());
            mTrailerVideoDto.setCurrentStream(videoDto.getCurrentStream());
            mTrailerVideoDto.setVtype(videoDto.getVtype());
            mTrailerVideoDto.setCurrentStreamKps(videoDto.getCurrentStreamKps());
            mTrailerVideoDto.setHasBelow(videoDto.getHasBelow());
            mTrailerVideoDto.setBackUrl0(videoDto.getBackUrl0());
            mTrailerVideoDto.setBackUrl1(videoDto.getBackUrl1());
            mTrailerVideoDto.setBackUrl2(videoDto.getBackUrl2());
            mTrailerVideoDto.setMd5(videoDto.getMd5());
            mTrailerVideoDto.setPlayMediaFormat(videoDto.getPlayMediaFormat());
        }
        return mTrailerVideoDto;
    }

    public void asyncSetAntiReport(Integer model, CommonParam commonParam, String logPrefix) {
        if (TerminalUtil.supportAntiReport(commonParam)) {
            if (null != this.facadeCacheDao.getVideoCacheDao().getAntiReport(commonParam.getDevSign(), commonParam)) {
                return;
            }
            int splatId = VideoUtil.getSplatId(model, commonParam);
            try {
                ReportTpResponse response = this.facadeTpDao.getActReportDao().setAntiReport(String.valueOf(splatId),
                        commonParam);
                if (null != response && response.getCode() > 0) {
                    this.facadeCacheDao.getVideoCacheDao().setAntiReport(commonParam.getDevSign(), commonParam);
                }
            } catch (Exception e) {
                log.error(logPrefix + "[antiReport]: error=" + e.getMessage(), e);
            }
        }
    }
}
