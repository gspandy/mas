package com.letv.mas.caller.iptv.tvproxy.video.service;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.ErrorCodeCommonUtil;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.StreamConstants;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto.NewResponse;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoDataWrapper;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.LiveUserPlayAuth;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.MmsFile;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.MmsStore;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.VideoCommonUtil;
import com.letv.mas.caller.iptv.tvproxy.video.ServerConfPath;
import com.letv.mas.caller.iptv.tvproxy.video.constants.ChargeTypeConstants;
import com.letv.mas.caller.iptv.tvproxy.video.constants.MobileConstant;
import com.letv.mas.caller.iptv.tvproxy.video.constants.MobileErrorConstant;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.Stream;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VODDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoHot;
import com.letv.mas.caller.iptv.tvproxy.video.util.MobileUtil;
import com.letv.mas.caller.iptv.tvproxy.video.util.VideoUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LivePlayService extends BaseService {
    private final static Logger log = LoggerFactory.getLogger(LivePlayService.class);
    private static final Integer PAGE_SIZE = 100;
    
    @Autowired
    LiveVideoService liveVideoService;
    
    public NewResponse<VODDto> getVideoPlay4Live(Long videoId, Long albumId, String stream, Long timestamp, String sig,
                                                 String businessId, Integer rand, String uid, CommonParam commonParam) {
        String errorCode = null;

        commonParam.setUserId(uid);
        String clientIp = commonParam.getClientIp();
        if (StringUtils.isNotEmpty(commonParam.getDevId())
                && MobileConstant.CRACK_PHONE_IMEI.contains(commonParam.getDevId())) {
            clientIp = "111.206.208.120";
        }

        VODDto videoPlay = new VODDto();
        /**
         * 签名算法 各业务方传入md5(videoId+albumId+timestamp+业务方businessId+业务方app密钥)
         * 生成[0-32)之间的随机整数rand
         * 通用播放器使用md5(videoId+albumId+timestamp+通用播放器businessId+通用播放器密钥).subStr(
         * 0,rand)+md5(videoId+
         * albumId+timestamp+业务方appId+业务方密钥).rand(rand)传入sig值
         */
        // if (!VODUtil.checkSig(videoId, albumId, timestamp, businessId, rand,
        // sig)) {
        // TODO 签名错误
        // log.error("wrong signature,
        // videoid:"+videoId+"albumid:"+albumId+"timestamp:"+timestamp+
        // "businessid:"+businessId+"rand:"+rand+"sig:"+sig);
        // } else {
        VideoMysqlTable video = null;
        AlbumMysqlTable album = null;
        AlbumVideoDataWrapper videoWrapper = null;
        if (videoId != null) {
            videoWrapper =liveVideoService.getVideoWrapper(videoId, commonParam);
        } else if (albumId != null) {
            AlbumVideoDataWrapper albumWrapper =liveVideoService.getAlbumWrapper(albumId,
                    commonParam);
            if (albumWrapper != null
                    && (album = VideoCommonUtil.filterByBroadcastId(albumWrapper.getAlbum(),
                            commonParam.getBroadcastId())) != null) {
                videoWrapper =liveVideoService
                        .getVideoWrapper(album.getVideoId(), commonParam);
            }
        }

        String streams = null;// live点播只要部分码流
        if (videoWrapper == null) {// 无版权
            errorCode = MobileErrorConstant.MOBILE_VIDEO_NO_COPYRIGHT;
        } else if ((video = VideoCommonUtil.filterByBroadcastId(videoWrapper.getVideo(), commonParam.getBroadcastId())) == null) {
            if (videoWrapper.getStatus() == AlbumVideoDataWrapper.STATUS_BLOCKED) {
                errorCode = MobileErrorConstant.MOBILE_VIDEO_HK_OVERSEA_IP_LIMIT;
            } else {
                errorCode = MobileErrorConstant.MOBILE_VIDEO_NO_COPYRIGHT;
            }
        } else if (StringUtil.isBlank(streams = MobileUtil.getStreams4LivePlay(video.getPlay_streams()))) {// 无可播码流
            errorCode = MobileErrorConstant.MOBILE_VIDEO_MISSING_PARAMETER;
        } else {
            String[] streamArray = streams.split(",");
            video.setPlay_streams(streams);
            String playStream = getVideoPlayStream(stream, streamArray, videoPlay, commonParam.getLangcode());
            videoPlay.setCurrentStream(playStream);

            // 从缓存对象获得定时后台跑出的属性
            setVideoPlayFromCacheEntity(video, album, videoPlay, commonParam);
            // 从VRS获得视频的基本信息
            this.getVideoPlayFromVRS(video, album, videoPlay, playStream, commonParam.getLangcode());

            // 获得媒资视频文件播放信息
            MmsStore mmsStore = this.getMmsFilePlayInfo(videoId, StringUtil.toLong(video.getMid()), playStream,
                    clientIp, 0, businessId, commonParam);

            // 播放鉴权
            LiveUserPlayAuth userPlayAuth = this.getUserAuth(ChargeTypeConstants.chargePolicy.FREE, video.getPid(),
                    commonParam.getUserId(), mmsStore, playStream, commonParam.getDevId(), "", clientIp);

            int validateMmsStore = VideoUtil.validateMmsStore(mmsStore);

            if (VideoUtil.validateMmsStore.SUCCESS == validateMmsStore) {
                // 从媒资视频文件解析播放信息
                this.setVideoPlayFromMmsFile(mmsStore, videoPlay, playStream, commonParam.getUserId(), userPlayAuth);
                // 设置playType和tipMsg
                this.getPlayTypeByUserAuth(video.getCategory(), userPlayAuth, videoPlay,
                        ChargeTypeConstants.chargePolicy.FREE, commonParam.getLangcode());

                // 播放视频附加服务
                this.handleAdditionalService(videoPlay, video);
            } else if (VideoUtil.validateMmsStore.VIDEO_CN_PLAY_FORBIDDEN == validateMmsStore) {
                // 错误码：大陆地区禁止播放
                errorCode = MobileErrorConstant.MOBILE_VIDEO_CN_IP_LIMIT;
            } else if (VideoUtil.validateMmsStore.VIDEO_HK_PLAY_FORBIDDEN == validateMmsStore) {
                // 错误码：香港地区禁止播放
                errorCode = MobileErrorConstant.MOBILE_VIDEO_HK_IP_LIMIT;
            } else if (VideoUtil.validateMmsStore.VIDEO_NOT_CN_NOT_HK_PLAY_FORBIDDEN == validateMmsStore) {
                // 错误码：大陆外禁止播放
                errorCode = MobileErrorConstant.MOBILE_VIDEO_HK_OVERSEA_IP_LIMIT;
            } else if (VideoUtil.validateMmsStore.VIDEO_UNKNOW_PLAY_FORBIDDEN == validateMmsStore) {
                // 未知ip受限
                errorCode = MobileErrorConstant.MOBILE_VIDEO_USER_LIMIT_OR_UNHONW_IP;
            } else if (VideoUtil.validateMmsStore.STORE_STATUS_NOT_SUCCESS == validateMmsStore) {
                // 错误码：防盗链接口失败
                errorCode = MobileErrorConstant.MOBILE_VIDEO_GET_PLAYURL_FAILURE;
            } else if (VideoUtil.validateMmsStore.INFO_NULL == validateMmsStore) {
                // 错误码：防盗链接口失败
                errorCode = MobileErrorConstant.MOBILE_VIDEO_GET_PLAYURL_FAILURE;
            } else if (VideoUtil.validateMmsStore.STORE_STATUS_NOT_SUCCESS == validateMmsStore) {
                // 错误码：防盗链接口失败
                errorCode = MobileErrorConstant.MOBILE_VIDEO_GET_PLAYURL_FAILURE;
            } else if (VideoUtil.validateMmsStore.NULL_STORE == validateMmsStore) {
                // 错误码：防盗链接口失败
                errorCode = MobileErrorConstant.MOBILE_VIDEO_GET_PLAYURL_FAILURE;
            } else {
                // 错误码：防盗链接口失败
                errorCode = MobileErrorConstant.MOBILE_VIDEO_GET_PLAYURL_FAILURE;
            }
        }

        NewResponse<VODDto> response = new NewResponse<VODDto>();
        if (errorCode == null) {
            response.setData(videoPlay);
            if (TerminalCommonConstant.TERMINAL_APPLICATION_SUPER_LIVE.equalsIgnoreCase(commonParam
                    .getTerminalApplication())) {
                response.setStatus(1);
            }
        } else {
            String errMsgCode = "COMMON_PLAY_" + errorCode;
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, errMsgCode, commonParam.getLangcode());
            if (TerminalCommonConstant.TERMINAL_APPLICATION_SUPER_LIVE.equalsIgnoreCase(commonParam
                    .getTerminalApplication())) {
                if (MobileErrorConstant.MOBILE_VIDEO_NO_COPYRIGHT.equals(errorCode)) {// 0015错误码客户端不要
                    response.setErrorCode(null);
                }
                response.setStatus(0);
            }
        }

        return response;
    }

    /**
     * 获得视频最终播放码流
     * @return
     */
    private String getVideoPlayStream(String clientRequestStream, String[] videoStreams, VODDto videoPlay, String locale) {
        String playStream = "";
        videoPlay.setHasBelow(VideoConstants.HAS_BELOW_NO);// 默认标识不降码流
        if (StringUtils.isEmpty(clientRequestStream)) {
            // 客户端为传入码流获得默认播放码流
            playStream = this.getDefaultVideoPlayStream();
        } else if (!ArrayUtils.contains(videoStreams, clientRequestStream)) {
            // 降码流处理
            playStream = this.getReducedStream(clientRequestStream, videoStreams, MobileConstant.PLAY_SORT_STREAM_T2);
        } else {
            playStream = clientRequestStream;
        }

        return playStream;
    }

    /**
     * 客户端若不传播放码流，获得视频的默认播放码流
     * @return
     */
    private String getDefaultVideoPlayStream() {
        return StreamConstants.CODE_NAME_350;
    }

    /**
     * 若客户端传入码流该视频没有，则降码流播放
     * @return
     */
    private String getReducedStream(String clientRequestStream, String[] videoStreams, String sortStreams) {
        String playStream = "";

        Integer clientRequestIndex = MobileConstant.PLAY_STREAM_CODE2ORDER.get(clientRequestStream);
        Integer videoMinIndex = MobileConstant.PLAY_STREAM_CODE2ORDER.get(videoStreams[0]);
        if ((clientRequestIndex != null && videoMinIndex != null) && (clientRequestIndex.intValue() < videoMinIndex)) {
            playStream = videoStreams[0];
        } else {
            playStream = videoStreams[videoStreams.length - 1];
        }
        return playStream;
    }

    private void setVideoPlayFromCacheEntity(VideoMysqlTable video, AlbumMysqlTable album, VODDto videoPlay,
            CommonParam commonParam) {
        String sourceSite = commonParam.getSalesAreaTmp();
        if (StringUtil.isBlank(sourceSite)) {
            sourceSite = commonParam.getWcodeTmp();
        }
        List<Stream> streams = null;
        List<Stream> theatreStreams = null;
        List<Stream> threeDStreams = null;
        List<Stream> normalStreams = null;
        String varietyShow = (album == null) ? "0" : album.getVarietyShow();
        if (video != null) {
            videoPlay.setVideoId(video.getId() == null ? null : video.getId().toString());
            Integer page = getPage(video.getId(), video.getPorder(), video.getPid(), video.getCategory(), varietyShow,
                    video.getRelease_date(), video.getCreate_time());
            videoPlay.setPage(page);
            if (StringUtils.isNotEmpty(video.getPlay_streams())) {
                streams = getVideoStreams(video.getPlay_streams(), null, sourceSite);
                if (video.getPlay_streams().contains("_dts") || video.getPlay_streams().contains("_db")) {
                    theatreStreams = getTheatreStreams(video.getPlay_streams(), sourceSite);
                }
                if (video.getPlay_streams().contains("3d")) {
                    threeDStreams = getThreeDStreams(video.getPlay_streams(), sourceSite);
                }
                normalStreams = getNormalStreams(video.getPlay_streams(), sourceSite);
            }
        }
        String wcode = commonParam.getWcodeTmp();
        String langCode = commonParam.getLangcode();
        String terminalApplication = commonParam.getTerminalApplication();
        videoPlay.setStreams(getI18NStream(streams, wcode, langCode, terminalApplication));
        videoPlay.setNormalStreams(getI18NStream(normalStreams, wcode, langCode, terminalApplication));
        videoPlay.setTheatreStreams(getI18NStream(theatreStreams, wcode, langCode, terminalApplication));
        videoPlay.setThreeDStreams(getI18NStream(threeDStreams, wcode, langCode, terminalApplication));
    }

    /**
     * 对码流名称进行多语言国际化
     * @param streamList
     * @param wcode
     * @param langCode
     * @return
     */
    private List<Stream> getI18NStream(List<Stream> streamList, String wcode, String langCode,
            String terminalApplication) {
        if (streamList != null && streamList.size() > 0) {
            for (Stream stream : streamList) {
                String name = MessageUtils.getMessage(stream.getCode(), langCode,
                        ServerConfPath.getTerminal(terminalApplication));
                if (StringUtils.isNotBlank(name)) {
                    stream.setName(name);
                }
            }
        }
        return streamList;
    }

    /**
     * 从VRS获得视频的基本信息
     * @param videoMysqlTable
     * @param albumMysqlTable
     * @param videoPlay
     * @param playStream
     * @param locale
     * @return
     */
    public VODDto getVideoPlayFromVRS(VideoMysqlTable playCacheEntity, AlbumMysqlTable album, VODDto videoPlay,
            String playStream, String locale) {
        videoPlay.setName(playCacheEntity.getName_cn());
        videoPlay.setCategoryId(playCacheEntity.getCategory());
        videoPlay.setVideoTypeId(playCacheEntity.getVideo_type());
        videoPlay.setPositive(playCacheEntity.getVideo_attr());
        videoPlay.setOrderInAlbum(playCacheEntity.getPorder());
        videoPlay.setEpisode(playCacheEntity.getEpisode());
        videoPlay.setPlayPlatform(playCacheEntity.getPlay_platform());

        // 是否收费
        if (album != null && MobileUtil.isMobPay(1, album.getPay_platform())) {
            videoPlay.setIfCharge(ChargeTypeConstants.CHARGE);
        } else {
            videoPlay.setIfCharge(ChargeTypeConstants.NOT_CHARGE);
        }

        // 设置视频片头、片尾时间
        VideoHot vh = MobileUtil.getHeadTailInfo(playCacheEntity.getCategory(), playCacheEntity.getBtime(),
                playCacheEntity.getEtime());
        videoPlay.setVideoHeadTime(vh.getT());
        videoPlay.setVideoTailTime(vh.getF());

        // DRM标识
        videoPlay.setDrmFlagId(playCacheEntity.getDrmFlagId());

        // 家长锁功能
        videoPlay.setIsPlayLock(playCacheEntity.getIsPlayLock());
        // 以下为兼容处理
        // 1、播放记录图片兼容
        if (album != null && playCacheEntity.getVideo_type().intValue() == VideoConstants.VideoType.ZHENG_PIAN) {
            videoPlay.setImg(MobileUtil.getPic(album.getPic_collections(), 400, 250));
        } else {
            videoPlay.setImg(MobileUtil.getPic(400, 250, playCacheEntity.getPic_all(),
                    playCacheEntity.getTrans_code_prefix()));
        }

        // 2、专辑名称兼容
        // String albumName = playCacheEntity.getName_cn();
        // if (album != null) {
        // albumName = album.getName_cn();
        // }
        videoPlay.setAlbumId(playCacheEntity.getPid() != null ? playCacheEntity.getPid().toString() : null);
        videoPlay.setHasAlbum((playCacheEntity.getPid() != null && playCacheEntity.getPid() != 0) ? 1 : 0);

        return videoPlay;
    }

    /**
     * 从防盗链获得媒资视频文件播放信息
     * @param actType
     *            播放类型：0:点播 1:直播 2:下载
     */
    public MmsStore getMmsFilePlayInfo(Long videoId, Long mid, String playStream, String clientIp, int actType,
            String businessId, CommonParam commonParam) {
        String vType = MobileUtil.getVType(playStream);
        Integer splatid = MobileUtil.getSplatId(commonParam.getWcode(), commonParam.getTerminalApplication(),
                commonParam.getTerminalSeries(), businessId);
        int platid = MobileUtil.getPlatId(commonParam.getWcode(), commonParam.getTerminalApplication(),
                commonParam.getTerminalSeries(), businessId);
        String tss = MobileUtil.getTss4VideoPlay(commonParam);
        MmsStore mmsStore = this.facadeTpDao.getCommonVideoTpDao().getMmsPlayInfo4Live(clientIp, videoId, mid, vType,
                actType, splatid, platid, tss, commonParam);
        return mmsStore;
    }

    /**
     * 根据用户权限获得播放类型
     * @return
     */
    public LiveUserPlayAuth getUserAuth(Integer chargeType, Long pid, String userId, MmsStore mmsStore,
            String playStream, String imei, String deviceKey, String clientIp) {
        LiveUserPlayAuth userPlayAuth = null;
        if (ChargeTypeConstants.chargePolicy.FREE != chargeType) {
            MmsFile mmsFile = VideoUtil.getMmsFileByVTypeOrder(playStream, mmsStore);
            String storePath = null;
            if (mmsFile != null) {
                storePath = mmsFile.getStorePath();
            }
            userPlayAuth = this.facadeTpDao.getVideoTpDao().getUserPlayAuth4Live(pid, userId, storePath, imei,
                    deviceKey, clientIp);
        }

        return userPlayAuth;
    }

    public void setVideoPlayFromMmsFile(MmsStore mmsStore, VODDto videoPlay, String playStream, String uid,
            LiveUserPlayAuth userPlayAuth) {
        MmsFile mmsFile = VideoUtil.getMmsFileByVTypeOrder(playStream, mmsStore);
        if (mmsFile != null) {
            if (mmsFile.getGdur() != null) {
                videoPlay.setDuration(mmsFile.getGdur() * 1000);// 播放视频片长（毫秒）
            }
            videoPlay.setGsize(mmsFile.getGsize());

            videoPlay.setPlayUrl(MobileUtil.getVIPUrl(uid, userPlayAuth, mmsFile.getMainUrl()));
            videoPlay.setBackUrl0(MobileUtil.getVIPUrl(uid, userPlayAuth, mmsFile.getBackUrl0()));
            videoPlay.setBackUrl1(MobileUtil.getVIPUrl(uid, userPlayAuth, mmsFile.getBackUrl1()));
            videoPlay.setBackUrl2(MobileUtil.getVIPUrl(uid, userPlayAuth, mmsFile.getBackUrl2()));

            videoPlay.setPlayUrl(MobileUtil.getMobileUrlWithParam(videoPlay.getPlayUrl(), videoPlay.getVideoId()));
            videoPlay.setBackUrl0(MobileUtil.getMobileUrlWithParam(videoPlay.getBackUrl0(), videoPlay.getVideoId()));
            videoPlay.setBackUrl1(MobileUtil.getMobileUrlWithParam(videoPlay.getBackUrl1(), videoPlay.getVideoId()));
            videoPlay.setBackUrl2(MobileUtil.getMobileUrlWithParam(videoPlay.getBackUrl2(), videoPlay.getVideoId()));

            videoPlay.setPlayUrl(MobileUtil.getMobileUrl4MediaPlayer(videoPlay.getPlayUrl()));
            videoPlay.setBackUrl0(MobileUtil.getMobileUrl4MediaPlayer(videoPlay.getBackUrl0()));
            videoPlay.setBackUrl1(MobileUtil.getMobileUrl4MediaPlayer(videoPlay.getBackUrl1()));
            videoPlay.setBackUrl2(MobileUtil.getMobileUrl4MediaPlayer(videoPlay.getBackUrl2()));
        }
    }

    /**
     * 根据用户权限获得播放类型
     * @param albumMysqlTable
     * @param videoMysqlTable
     * @param userPlayAuth
     * @param videoPlay
     * @param login
     * @param chargeType
     */
    public void getPlayTypeByUserAuth(Integer categoryId, LiveUserPlayAuth userPlayAuth, VODDto videoPlay,
            Integer chargeType, String locale) {
        // if (userPlayAuth != null &&
        // VideoTpConstants.UserPlayAuth.PLAY_SERVICE_OPEN ==
        // userPlayAuth.getStatus()) {
        // chargeType = ChargeTypeConstants.chargePolicy.FREE;
        // }

        // 根据不同的收费策略展示不同的试看提示
        if (ChargeTypeConstants.chargePolicy.CHARGE_BY_STREAM == chargeType
                || ChargeTypeConstants.chargePolicy.CHARGE_YUAN_XIAN == chargeType) {
            videoPlay.setTryPlayTime(new Long(1000 * 60 * 6));
            videoPlay.setTryPlayTipMsg(MessageUtils.getMessage("VIDEO.SHIKAN.SIXMIN", locale));
        } else if (ChargeTypeConstants.chargePolicy.CHARGE_BY_CONTENT == chargeType) {
            videoPlay.setTryPlayTime(VideoUtil.getTryPlayTime(categoryId, videoPlay.getVideoTailTime(),
                    videoPlay.getDuration()));
            videoPlay
                    .setTryPlayTipMsg((categoryId != null && (VideoConstants.Category.FILM == categoryId)) ? MessageUtils
                            .getMessage("VIDEO.SHIKAN.SIXMIN", locale) : MessageUtils.getMessage("VIDEO.SHIKAN.DIYIJI",
                            locale));
        }

        videoPlay.setPlayType(ChargeTypeConstants.chargePolicy.FREE);
    }

    /**
     * 播放视频附加服务
     * @param videoPlay
     * @param videoMysqlTable
     * @return
     */
    public void handleAdditionalService(VODDto videoPlay, VideoMysqlTable playCacheEntity) {
        // 2、视点图
        videoPlay.setViewPic(this.getVideoViewPicUrl(playCacheEntity.getVideoPic()));
    }

    /**
     * 视点图功能
     * @param vrsVideoPic
     * @return
     */
    public String getVideoViewPicUrl(String vrsVideoPic) {
        if (StringUtils.isNotEmpty(vrsVideoPic)) {
            return vrsVideoPic + "/viewpoint/desc_200x170.json";
        }

        return null;
    }

    /**
     * 获得视频在第几页
     * @param porder
     * @param albumId
     * @param categoryId
     * @return
     */
    private Integer getPage(Long videoId, Integer porder, Long albumId, Integer categoryId, String varietyShow,
            String releaseDate, Date createTime) {
        Integer page = 0;
        switch (categoryId) {
        case VideoConstants.Category.TV:
        case VideoConstants.Category.CARTOON:
            page = getPageNum(porder, PAGE_SIZE);
            break;
        case VideoConstants.Category.FILM:
            break;
        case VideoConstants.Category.ENT:
        case VideoConstants.Category.HOTSPOT:
        case VideoConstants.Category.CAR:
        case VideoConstants.Category.TRAVEL:
        case VideoConstants.Category.FENG_SHANG:
        case VideoConstants.Category.AD:
        case VideoConstants.Category.ZIXUN:
            if ("1".equals(varietyShow)) {
                if (StringUtils.isNotEmpty(releaseDate)) {
                    if (releaseDate.matches("\\d{4}-\\d{2}") || releaseDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        page = Integer.valueOf(releaseDate.replaceAll("-", "").substring(0, 6));
                    }
                }
                if (page.intValue() == 0) {
                    if (createTime != null) {
                        page = NumberUtils.toInt(date2String(createTime));
                    }
                }
            }

            break;
        case VideoConstants.Category.MUSIC:
            if ("1".equals(varietyShow)) {
                // page = porder / PAGE_SIZE;
            }

            break;
        case VideoConstants.Category.SPORT:
            if ("1".equals(varietyShow)) {
                if (porder.toString().length() >= 6) {
                    page = Integer.valueOf(porder.toString().substring(0, 6));
                }
                if (page.intValue() == 0) {
                    if (StringUtils.isNotEmpty(releaseDate)) {
                        if (releaseDate.matches("\\d{4}-\\d{2}") || releaseDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            page = Integer.valueOf(releaseDate.replaceAll("-", "").substring(0, 6));
                        }
                    }
                }
                if (page.intValue() == 0) {
                    if (createTime != null) {
                        page = NumberUtils.toInt(date2String(createTime));
                    }
                }
            }

            break;
        case VideoConstants.Category.PARENTING:
            if ("1".equals(varietyShow)) {
                if (porder.toString().length() >= 6) {
                    page = Integer.valueOf(porder.toString().substring(0, 6));
                }

                if (page.intValue() == 0) {
                    if (StringUtils.isNotEmpty(releaseDate)) {
                        if (releaseDate.matches("\\d{4}-\\d{2}") || releaseDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            page = Integer.valueOf(releaseDate.replaceAll("-", "").substring(0, 6));
                        }
                    }
                }
                if (page.intValue() == 0) {
                    if (createTime != null) {
                        page = NumberUtils.toInt(date2String(createTime));
                    }
                }

            } else {
                page = getPageNum(porder, PAGE_SIZE);
            }

            break;
        case VideoConstants.Category.CAI_JING:
        case VideoConstants.Category.GONG_KAI_KE:
            if ("1".equals(varietyShow)) {
                if (porder.toString().length() >= 6) {
                    page = Integer.valueOf(porder.toString().substring(0, 6));
                }
                if (page.intValue() == 0) {
                    if (StringUtils.isNotEmpty(releaseDate)) {
                        if (releaseDate.matches("\\d{4}-\\d{2}") || releaseDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            page = Integer.valueOf(releaseDate.replaceAll("-", "").substring(0, 6));
                        }
                    }
                }
                if (page.intValue() == 0) {
                    if (createTime != null) {
                        page = NumberUtils.toInt(date2String(createTime));
                    }
                }
            } else {
                page = getPageNum(porder, PAGE_SIZE);
            }

            break;
        case VideoConstants.Category.VARIETY:
            if (porder.toString().length() >= 6) {
                page = Integer.valueOf(porder.toString().substring(0, 6));
            }

            if (page.intValue() == 0) {
                if (StringUtils.isNotEmpty(releaseDate)) {
                    if (releaseDate.matches("\\d{4}-\\d{2}") || releaseDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        page = Integer.valueOf(releaseDate.replaceAll("-", "").substring(0, 6));
                    }
                }
            }
            if (page.intValue() == 0) {
                if (createTime != null) {
                    page = NumberUtils.toInt(date2String(createTime));
                }
            }
            break;
        case VideoConstants.Category.DFILM:
        case VideoConstants.Category.TEACH:
            if ("1".equals(varietyShow)) {
                if (porder.toString().length() >= 6) {
                    page = Integer.valueOf(porder.toString().substring(0, 6));
                }

                if (page.intValue() == 0) {
                    if (StringUtils.isNotEmpty(releaseDate)) {
                        if (releaseDate.matches("\\d{4}-\\d{2}") || releaseDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            page = Integer.valueOf(releaseDate.replaceAll("-", "").substring(0, 6));
                        }
                    }
                }
                if (page.intValue() == 0) {
                    if (createTime != null) {
                        page = NumberUtils.toInt(date2String(createTime));
                    }
                }
            } else {
                page = getPageNum(porder, PAGE_SIZE);
            }

            break;
        default:
            break;
        }
        this.log.debug("getPage:videoId-" + videoId + "-categoryId-" + categoryId + "-porder-" + porder + "-albumId-"
                + albumId + "-varietyShow-" + varietyShow + "-releaseDate-" + releaseDate + "-page-" + page);
        return page;
    }

    public String date2String(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        return format.format(date);
    }

    public Integer getPageNum(Integer porder, int pageSize) {
        int pageNum = 0;
        if (porder != null && porder.intValue() > 0) {
            pageNum = (porder % PAGE_SIZE == 0) ? (porder / PAGE_SIZE - 1) : porder / PAGE_SIZE;
        }
        return pageNum;
    }

    /**
     * 设置视频码流对象
     * @param videoStreams
     * @param locale
     */
    public List<Stream> getVideoStreams(String videoStreams, String locale, String sourceSite) {
        // 获得预设码流配置信息
        Map<String, Stream> preSetStream = MobileUtil.getPreSetStream(locale, sourceSite);
        return getPlayStreamByStreamCode(videoStreams, preSetStream);
    }

    /**
     * 获得影院声码流
     * @param videoStreams
     * @return
     */
    public List<Stream> getTheatreStreams(String videoStreams, String sourceSite) {
        String[] streamArray = videoStreams.split(",");
        Set<Integer> streamIds = new HashSet<Integer>();
        for (String stream : streamArray) {
            if (MobileConstant.PLAY_THEATRE_STREAM_CODE2ORDER.get(stream) != null) {
                streamIds.add(MobileConstant.PLAY_THEATRE_STREAM_CODE2ORDER.get(stream));
            }
        }

        StringBuffer streamsStringBuffer = new StringBuffer();
        for (Integer sId : streamIds) {
            String s = MobileConstant.PLAY_THEATRE_STREAM_ORDER2CODE.get(sId);
            if (StringUtils.isNotEmpty(s)) {
                streamsStringBuffer.append(s).append(",");
            }
        }
        String theatreS = streamsStringBuffer.toString();
        if (StringUtils.isNotEmpty(theatreS)) {
            theatreS = theatreS.substring(0, theatreS.length() - 1);
        }
        // 获得预设码流配置信息
        Map<String, Stream> preSetStream = MobileUtil.getPreSetStream("", sourceSite);
        return getPlayStreamByStreamCode(theatreS, preSetStream);
    }

    /**
     * 获得DTS码流
     * @param videoStreams
     * @return
     */
    public List<Stream> getDtsStreams(String videoStreams, String sourceSite) {
        String[] streamArray = videoStreams.split(",");
        StringBuffer dtsStreamBuffer = new StringBuffer();
        for (String stream : streamArray) {
            if (MobileConstant.PLAY_DTS_STREAM.contains(stream)) {
                dtsStreamBuffer.append(stream).append(",");
            }
        }
        String dtsStream = dtsStreamBuffer.toString();
        if (StringUtils.isNotEmpty(dtsStream)) {
            dtsStream = dtsStream.substring(0, dtsStream.length() - 1);
        }
        // 获得预设码流配置信息
        Map<String, Stream> preSetStream = MobileUtil.getPreSetStream("", sourceSite);
        return getPlayStreamByStreamCode(dtsStream, preSetStream);
    }

    /**
     * 获得杜比码流
     * @param videoStreams
     * @return
     */
    public List<Stream> getDolbyStreams(String videoStreams, String sourceSite) {
        String[] streamArray = videoStreams.split(",");
        StringBuffer dolbyStreamBuffer = new StringBuffer();
        for (String stream : streamArray) {
            if (MobileConstant.PLAY_DOLBY_STREAM.contains(stream)) {
                dolbyStreamBuffer.append(stream).append(",");
            }
        }
        String dolbyStream = dolbyStreamBuffer.toString();
        if (StringUtils.isNotEmpty(dolbyStream)) {
            dolbyStream = dolbyStream.substring(0, dolbyStream.length() - 1);
        }
        // 获得预设码流配置信息
        Map<String, Stream> preSetStream = MobileUtil.getPreSetStream("", sourceSite);
        return getPlayStreamByStreamCode(dolbyStream, preSetStream);
    }

    /**
     * 获得3D码流
     * @param videoStreams
     * @return
     */
    public List<Stream> getThreeDStreams(String videoStreams, String sourceSite) {
        String[] streamArray = videoStreams.split(",");
        StringBuffer threeDStreamBuffer = new StringBuffer();
        for (String stream : streamArray) {
            if (MobileConstant.PLAY_3D_STREAM.contains(stream)) {
                threeDStreamBuffer.append(stream).append(",");
            }
        }
        String threeDStream = threeDStreamBuffer.toString();
        if (StringUtils.isNotEmpty(threeDStream)) {
            threeDStream = threeDStream.substring(0, threeDStream.length() - 1);
        }
        // 获得预设码流配置信息
        Map<String, Stream> preSetStream = MobileUtil.getPreSetStream("", sourceSite);
        return getPlayStreamByStreamCode(threeDStream, preSetStream);
    }

    public List<Stream> get360Streams(String videoStreams, String sourceSite) {
        String[] streamArray = videoStreams.split(",");
        StringBuffer streamBuffer = new StringBuffer();
        for (String stream : streamArray) {
            if (MobileConstant.PLAY_360_STREAM.contains(stream)) {
                streamBuffer.append(stream).append(",");
            }
        }
        String Stream = streamBuffer.toString();
        if (StringUtils.isNotEmpty(Stream)) {
            Stream = Stream.substring(0, Stream.length() - 1);
        }
        // 获得预设码流配置信息
        Map<String, Stream> preSetStream = MobileUtil.getPreSetStream("", sourceSite);
        return getPlayStreamByStreamCode(Stream, preSetStream);
    }

    /**
     * 获得普通码流
     * @param videoStreams
     * @return
     */
    public List<Stream> getNormalStreams(String videoStreams, String sourceSite) {
        String[] streamArray = videoStreams.split(",");
        StringBuffer normalStreamBuffer = new StringBuffer();
        for (String stream : streamArray) {
            if (MobileConstant.PLAY_NORMAL_STREAM.contains(stream)) {
                normalStreamBuffer.append(stream).append(",");
            }
        }
        String normalStream = normalStreamBuffer.toString();
        if (StringUtils.isNotEmpty(normalStream)) {
            normalStream = normalStream.substring(0, normalStream.length() - 1);
        }

        // 获得预设码流配置信息
        Map<String, Stream> preSetStream = MobileUtil.getPreSetStream("", sourceSite);
        return getPlayStreamByStreamCode(normalStream, preSetStream);
    }

    /**
     * 从码流配置MAP中获得播放码流对象
     * @param streamCodes
     * @param preSetStream
     * @return
     */
    private List<Stream> getPlayStreamByStreamCode(String streamCodes, Map<String, Stream> preSetStream) {
        List<Stream> playStreams = new ArrayList<Stream>();
        String[] streams = streamCodes.split(",");

        for (int i = streams.length - 1; i >= 0; i--) {
            if (StringUtils.isNotEmpty(streams[i])) {
                if (preSetStream.get(streams[i]) != null && !playStreams.contains(preSetStream.get(streams[i]))) {
                    playStreams.add(preSetStream.get(streams[i]));
                }
            }
        }
        return playStreams;
    }
}
