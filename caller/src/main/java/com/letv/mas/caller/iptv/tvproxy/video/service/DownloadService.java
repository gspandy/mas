package com.letv.mas.caller.iptv.tvproxy.video.service;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.StreamConstants;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.constant.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.AlbumVideoAccess;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1.StreamCode;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.VideoTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoHot;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoPlayDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DownloadService extends BaseService {

    @Autowired
    AlbumVideoAccess albumVideoAccess;
    
    private Logger log = LoggerFactory.getLogger(DownloadService.class);

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

    /**
     * 获得播放信息
     * @param vrsVideoInfoId
     * @param stream
     * @param channelCode
     * @param ifCharge
     * @param cntv
     * @param expectTS
     * @param expectTSActual
     * @param expectDispath302
     * @param exceptNomalActual
     * @param actionType
     * @param playType
     * @param playAuth
     * @param token
     * @param tokenUserId
     * @param commonParam
     * @return
     */
    public Response<VideoPlayDto> getVideoPlayForDB(Long vrsVideoInfoId, String stream, String channelCode,
                                                    Boolean ifCharge, Integer cntv, Boolean expectTS, Boolean expectTSActual, Boolean expectDispath302,
                                                    Boolean exceptNomalActual, String actionType, String playType, Boolean playAuth, String token,
                                                    String tokenUserId, CommonParam commonParam) {
        String errorCode = null;
        VideoPlayDto videoPlay = null;
        // 获得视频
        VideoMysqlTable iptvVideoInfo = this.getIptvVideoInfo(vrsVideoInfoId, null, cntv, commonParam);
        if (iptvVideoInfo == null) {// avoid null pointer exception
            errorCode = ErrorCodeConstant.VIDEO_NOT_FOUND;
        } else {
            // 获得专辑
            AlbumMysqlTable iptvAlbumInfo = this.getIptvAlbumInfo(null, iptvVideoInfo.getItv_album_id(), cntv,
                    commonParam);
            // 获得可用码流
            Map<String, Object> streamMap = this.getVideoPlayStream(iptvVideoInfo, stream, channelCode, actionType,
                    expectTS, playType, expectTSActual, expectDispath302, exceptNomalActual, commonParam);
            videoPlay = (VideoPlayDto) streamMap.get("VideoPlayDto");

            String validateStream = videoPlay.getCurrentStream();
            videoPlay = this.getVideoPlayInfo(videoPlay, (MmsStore) streamMap.get("MmsStore"), ifCharge,
                    validateStream, commonParam.getTerminalSeries(), expectTS, expectTSActual, expectDispath302,
                    exceptNomalActual);

            // 添加视频码流其他详情：片名、片头、片尾等信息
            this.videoPlayDetail(stream, iptvVideoInfo, iptvAlbumInfo, videoPlay, (MmsStore) streamMap.get("MmsStore"),
                    expectTS, commonParam.getTerminalSeries(), expectTSActual, expectDispath302, exceptNomalActual,
                    playAuth, token, tokenUserId, ifCharge, "download", commonParam);
        }
        Response<VideoPlayDto> response = new Response<VideoPlayDto>();
        if (errorCode != null) {
            ErrorCodeCommonUtil.setErrorResponse(response, errorCode, commonParam.getLangcode());
        } else {
            response.setData(videoPlay);
        }
        return response;
    }

    /**
     * 返回试看时长
     * @param category
     * @param tailTime
     * @param duration
     * @return
     */
    private Long getTryPlayTime(Integer category, Integer tailTime, Long duration) {
        Long trytime = 0l;
        if (category != null
                && (category == MmsTpConstant.MMS_CATEGARY_TV || category == MmsTpConstant.MMS_CATEGARY_CARTOON)
        // || category == CategoryIdConstants.DFILM_TYPE
        ) {// 电视剧、动漫有片尾返回片尾时间、否则返回时长
            if (tailTime != null && tailTime > 0) {
                trytime = new Long(tailTime);
            } else if (duration != null && duration > 0) {
                trytime = duration;
            }
        } else {// 电影返回6分钟
            trytime = new Long(1000 * 60 * 6);
        }
        return trytime;
    }

    /**
     * 获得水印信息
     * @param cid
     * @param pid
     * @return
     */
    private List<WaterMarkImage> getWaterMark(Integer cid, Long pid) {
        List<WaterMarkImage> list = new ArrayList<WaterMarkImage>();
        try {
            // list =
            // this.cacheDao.get(CacheContentConstants.D_IPTVVIDEOINFO_WATERMARK_LIST
            // + cid + "_" + pid,
            // new LetvTypeReference<List<WaterMarkImage>>() {
            // });
            list = this.facadeCacheDao.getVideoCacheDao().getWaterMark(cid, pid);
            if (list == null) {
                MmsResponse<WaterMark> markResponse = this.facadeTpDao.getVideoTpDao().getWaterMark(cid, pid);
                if (markResponse != null && markResponse.getData() != null) {
                    list = markResponse.getData().getImgs();
                    if (list != null && !list.isEmpty()) {
                        // this.cacheDao.set(CacheContentConstants.D_IPTVVIDEOINFO_WATERMARK_LIST
                        // + cid + "_" + pid, list,
                        // CacheConstants.CACHE_EXPIRES_ONE_DAY);
                        this.facadeCacheDao.getVideoCacheDao().setWaterMark(cid, pid, list,
                                CommonConstants.SECONDS_OF_1_DAY);
                    }
                }
            }
        } catch (Exception e) {
            this.log.error(e.getMessage(), e);
        }
        return list;
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

    public VideoHot getHeadTailInfo(Integer cid, VideoMysqlTable video) {
        // 获取片头片尾
        VideoHot h = new VideoHot();
        if (cid != null && cid == MmsTpConstant.MMS_CATEGARY_TV && video != null) {
            Integer b = video.getBtime();
            Integer e = video.getEtime();
            h.setT(b == null ? 0 : b);
            h.setF(e == null ? 0 : e);
        }
        return h;
    }

    /**
     * 获得视频下载地址
     * @param vrsVideoInfoId
     * @param stream
     * @param timestamp
     * @param sig
     * @param useAuth
     * @param token
     * @param tokenUserId
     * @return
     */
    public Response<VideoPlayDto> getDownloadInfo(Long vrsVideoInfoId, String stream, Long timestamp, String sig,
            Boolean useAuth, String token, String tokenUserId, CommonParam commonParam) {

        this.log.info("getDownloadInfo:" + "vrsVideoInfoId[" + vrsVideoInfoId + "]stream[" + stream + "]timestamp["
                + timestamp + "]sig" + sig + "]useAuth[" + useAuth + "]terminalBrand[" + commonParam.getTerminalBrand()
                + "]terminalSeries[" + commonParam.getTerminalSeries() + "]token[" + token + "]tokenUserId["
                + tokenUserId + "]");
        // 验证加密key
        if (useAuth) {
            Boolean validate = this.checkSig(vrsVideoInfoId, timestamp, stream, sig);
            if (!validate) {
                ErrorCodeConstant.throwLetvCommonException(ErrorCodeConstant.SIG_ERROR, commonParam.getLangcode());
            }
        }
        stream = this.getFinalDownLoadStream(stream);

        Boolean playAuth = Boolean.TRUE;
        Response<VideoPlayDto> response = this.getVideoPlayForDB(vrsVideoInfoId, stream, null, true, null, false,
                false, false, false, "download", "download", playAuth, token, tokenUserId, commonParam);
        if (response.getData() != null) {
            this.log.info("getDownloadInfo:" + "playUrl[" + response.getData().getPlayUrl() + "]");
        }

        return response;
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
     * 排序、过滤码流列表 只返回视频拥有的码流
     * @param streams
     * @param channelCode
     * @return
     */
    private List<StreamCode> sortAndfilterStreamCode(String streams, String channelCode, Integer categoryId,
                                                     String langcode) {
        if (channelCode == null) {
            channelCode = "";
        }
        String sort_stream = "";
        if (channelCode.indexOf("dolby") > -1) {
            sort_stream = LetvStreamCommonConstants.SORT_STREAM_T2_DB + "#"
                    + LetvStreamCommonConstants.SORT_STREAM_T2_COMMON + "#"
                    + LetvStreamCommonConstants.SORT_STREAM_T2_3D;
        } else if (channelCode.indexOf("3d") > -1) {
            sort_stream = LetvStreamCommonConstants.SORT_STREAM_T2_3D + "#"
                    + LetvStreamCommonConstants.SORT_STREAM_T2_COMMON + "#"
                    + LetvStreamCommonConstants.SORT_STREAM_T2_DB;
        } else if (channelCode.indexOf("1080p") > -1) {
            sort_stream = LetvStreamCommonConstants.SORT_STREAM_T2_COMMON + "#"
                    + LetvStreamCommonConstants.SORT_STREAM_T2_DB + "#" + LetvStreamCommonConstants.SORT_STREAM_T2_3D;
        } else {
            sort_stream = LetvStreamCommonConstants.SORT_STREAM_T2_COMMON + "#"
                    + LetvStreamCommonConstants.SORT_STREAM_T2_DB + "#" + LetvStreamCommonConstants.SORT_STREAM_T2_3D;
        }
        List<StreamCode> allVideoStreamList = StreamConstants.getStreamCode(sort_stream, categoryId, langcode);
        String[] list = streams.split(",");
        List<StreamCode> realList = new ArrayList<StreamCode>();
        if (list != null && list.length > 0) {
            for (int i = 0; i < allVideoStreamList.size(); i++) {
                StreamCode allV = allVideoStreamList.get(i);
                allV.setKbps(LetvStreamCommonConstants.getMbps(allV.getCode()));
                for (int j = 0; j < list.length; j++) {
                    String realStream = list[j];
                    // if (realStream.equalsIgnoreCase(allV.getCode())) {
                    if (realStream.equalsIgnoreCase(allV.getCode())
                            || (allV.getCode().equalsIgnoreCase(LetvStreamCommonConstants.CODE_NAME_1080p6m))
                            && realStream.equalsIgnoreCase(LetvStreamCommonConstants.CODE_NAME_1080p3m)) {
                        allV.setEnabled("1");
                        break;
                    }
                }
                realList.add(allV);
            }
        }

        return realList;
    }

    // 播放显示名称
    private String getPlayShowName(Integer categoryId, String albumName, String videoName, Integer order,
            String stream, String starring, String langcode) {
        StringBuffer sb = new StringBuffer();
        if (categoryId == null) {
            return videoName + " " + LetvStreamCommonConstants.nameOf(stream, langcode);
        }
        String streamName = LetvStreamCommonConstants.nameOf(stream, langcode);
        if (categoryId == MmsTpConstant.MMS_CATEGARY_FILM) {// 电影
            sb.append(albumName);
        } else if (categoryId == MmsTpConstant.MMS_CATEGARY_CARTOON || categoryId == MmsTpConstant.MMS_CATEGARY_TV) {// 电视剧、动漫
            sb.append(albumName);
            if (order != null) {
                sb.append(" ").append("第").append(order).append("集");
            }
        } else if (categoryId == MmsTpConstant.MMS_CATEGARY_VARIETY) {// 综艺
            sb.append(albumName);
            if (order != null) {
                sb.append(" ").append(order).append("期");
            }
        } else {
            if (categoryId == MmsTpConstant.MMS_CATEGARY_MUSIC) {
                sb.append(starring == null ? "" : starring.replace(",", "")).append(" ").append(videoName);
            } else {
                sb.append(videoName);
            }
        }
        return sb.append(" ").append(streamName).toString();
    }

    /**
     * @return
     */
    private VideoPlayDto getVideoPlayInfo(VideoPlayDto videoPlay, MmsStore store, Boolean ifCharge,
            String currentStream, String terminalSeries, Boolean expectTS, Boolean expectTSActual,
            Boolean expectDispath302, Boolean exceptNomalActual) {
        // VideoPlayDto videoPlayInfo = new VideoPlayDto();

        MmsInfo data = store.getData().get(0);
        MmsFile file = data.getInfos().get(0);
        if (file != null) {
            videoPlay.setCurrentStream(currentStream);
            if (file.getGdur() != null) {
                videoPlay.setDuration(file.getGdur().intValue() * 1000l);// 毫秒
            }
            if (file.getGbr() != null) {
                videoPlay.setCurrentStream_kps(file.getGbr());
            } else {
                videoPlay.setCurrentStream_kps(LetvStreamCommonConstants.getMbps(currentStream));
            }

            String mainUrl = this.getFinalPlayUrlByFormat(file.getMainUrl(), ifCharge ? "1" : "0", terminalSeries,
                    expectTS, expectTSActual, expectDispath302, exceptNomalActual, VideoConstants.TERMINAL_X60, true);
            String backup0 = this.getFinalPlayUrlByFormat(file.getBackUrl0(), ifCharge ? "1" : "0", terminalSeries,
                    expectTS, expectTSActual, expectDispath302, exceptNomalActual, VideoConstants.TERMINAL_X60, true);
            String backup1 = this.getFinalPlayUrlByFormat(file.getBackUrl1(), ifCharge ? "1" : "0", terminalSeries,
                    expectTS, expectTSActual, expectDispath302, exceptNomalActual, VideoConstants.TERMINAL_X60, true);
            String backup2 = this.getFinalPlayUrlByFormat(file.getBackUrl2(), ifCharge ? "1" : "0", terminalSeries,
                    expectTS, expectTSActual, expectDispath302, exceptNomalActual, VideoConstants.TERMINAL_X60, true);
            videoPlay.setPlayUrl(mainUrl);
            videoPlay.setBackUrl0(backup0);
            videoPlay.setBackUrl1(backup1);
            videoPlay.setBackUrl2(backup2);
            videoPlay.setMd5(file.getMd5());
            videoPlay.setStorePath(file.getStorePath());
            videoPlay.setGsize(file.getGsize());
        }
        return videoPlay;
    }

    /**
     * @param mid
     * @param stream
     * @param ts
     * @param actionType
     * @return
     */
    public MmsStore getVideoPlayInfo(Long mid, String stream, Boolean ts, String actionType, String playType,
            String[] hasStreams, Boolean isLetvTv, Long aid, Long vid, boolean isMarlin, CommonParam commonParam) {
        // //杜比频道特殊处理
        // if(!("download").equalsIgnoreCase(actionType)){
        // ts=getFinalTS(stream);
        // }
        stream = this.getFinalPlayStream(hasStreams, stream, playType, actionType, aid);

        Integer splatId = null;

        String[] LETV_SERIES_TV = { "Android TV on MStar Amber3", "OTT-letv", "LeTVX60",
                "Android TV on MStar Amber3 S50", "Android TV on MStar Amber3 S40",
                "Android TV on MStar Amber3 S50_hotel", "LeTVX60_hotel", "Android TV on MStar Amber3 S50_hotel_C",
                "LeTVX60_hotel_C", "MStar Android TV", "LeTVX60_MAX70", "MStar Android TV_S250F",
                "Android TV on MStar Amber3 S40_hotel_C", "MStar Android TV_S240F", "MStar+Android TV_S250F_THTF",
                "letv25" };

        if (Arrays.asList(LETV_SERIES_TV).contains(commonParam.getTerminalSeries())) { // 电视为501
            splatId = 501;
        } else if ("CIBN".equals(commonParam.getTerminalSeries())) {// 国广后台审核，为504，同海外
            splatId = 504;
            this.log.info("CIBN check video flag 504........");
        } else { // 盒子为503
            splatId = 503;
        }
        if ("download".equals(actionType)) { // 下载为502
            splatId = 502;
        }
        MmsStore store = this.getMmsPlayInfo(vid, mid, stream, ts, isLetvTv, splatId, isMarlin, commonParam);
        Boolean validate = this.validatMmsStore(store);
        if (!validate) {
            ErrorCodeConstant.throwLetvCommonException(ErrorCodeConstant.GET_PLAYURL_ERROR, commonParam.getLangcode());
        }
        return store;
    }

    /**
     * 如果是x60型号的杜比频道或3D，则播放mp4的，不能播放m3u8的
     * @param stream
     * @return
     */
    private Boolean getFinalTS(String stream) {
        if ("720p_db".equalsIgnoreCase(stream) || "1300_db".equalsIgnoreCase(stream)
                || "800_db".equalsIgnoreCase(stream) || "1080p6m_db".equalsIgnoreCase(stream)
                || stream.toLowerCase().indexOf("3d") > -1) {
            return false;
        }
        return true;
    }

    /**
     * 获取可用码流
     * 规则：已客户端传入码流为主,若传入的码流当前视频没有，则按照向下兼容的逻辑逐级获取；若用户没有传入，则走默认
     * @param currentStream
     * @param channelCode
     * @return
     */
    private VideoPlayDto getValidateStream(String currentStream, VideoMysqlTable iptvVideoInfo, String channelCode,
            VideoPlayDto videoPlay, String langcode) {

        String playStreams = iptvVideoInfo.getPlay_streams();
        if (StringUtils.isEmpty(playStreams)) {
            ErrorCodeConstant.throwLetvCommonException(ErrorCodeConstant.STREAM_NOT_FOUND);
        }

        String[] streams = playStreams.split(",");
        String finalStream = "";
        if (StringUtils.isBlank(currentStream)) {// 客户端未传入码流，取默认码流
            if (currentStream != null && (currentStream.indexOf("3D") > -1 || currentStream.indexOf("3d") > -1)) {// 兼容播放记录播放3D影片
                channelCode = "3d";
            }
            if (channelCode != null && "3d".equalsIgnoreCase(channelCode)) {
                finalStream = this.getDefaultStream(streams, LetvStreamCommonConstants.DEFAULT_STREAM_3D);
            } else {
                finalStream = this.getDefaultStream(streams, LetvStreamCommonConstants.DEFAULT_STREAM);
            }
        } else {// 客户端传入码流
            for (String stream : streams) {
                if (currentStream.equalsIgnoreCase(stream)
                        || (LetvStreamCommonConstants.CODE_NAME_1080p6m.equalsIgnoreCase(currentStream) && LetvStreamCommonConstants.CODE_NAME_1080p3m
                                .equalsIgnoreCase(stream))) {
                    finalStream = currentStream;
                }
            }

            if (finalStream.isEmpty()) {
                finalStream = getBelowStream(streams, currentStream, LetvStreamCommonConstants.PLAY_SORT_STREAM_T2);
                if (!finalStream.isEmpty()) {
                    videoPlay.setHasBelow(1);
                }
            }
        }

        if (finalStream.isEmpty()) {
            ErrorCodeConstant.throwLetvCommonException(ErrorCodeConstant.PLAY_NO_STREAM,
                    LetvStreamCommonConstants.nameOf(currentStream));
        }

        videoPlay.setCurrentStream(finalStream);
        return videoPlay;
    }

    /**
     * 获得默认码流
     * @param hasStreams
     * @return
     */
    private String getDefaultStream(String[] hasStreams, String defaultStreams) {
        String defaultStream = "";
        String[] defaults = defaultStreams.split("#");
        if (hasStreams != null) {
            for (String ds : defaults) {
                for (String hs : hasStreams) {
                    if (ds.equalsIgnoreCase(hs)) {
                        defaultStream = ds;
                    }
                }
                if (StringUtils.isNotEmpty(defaultStream)) {
                    break;
                }
            }
        }
        return defaultStream;
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
     * 验证签名
     */
    public Boolean checkSig(Long vrs_videoInfoId, Long timestamp, String sig) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vrsVideoInfoId", vrs_videoInfoId);
        params.put("timestamp", timestamp);
        params.put("sig", sig);
        Boolean isValid = CommonUtil.checkSig(params);
        return isValid;
    }

    /**
     * 验证签名
     */
    public Boolean checkSig(Long vrs_videoInfoId, Long timestamp, String stream, String sig) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("vrsVideoInfoId", vrs_videoInfoId);
        params.put("timestamp", timestamp);
        params.put("stream", stream);
        params.put("sig", sig);
        Boolean isValid = CommonUtil.checkSig(params);
        return isValid;
    }

    public String StringFilter(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String result = m.replaceAll("").trim();
        try {
            result = URLEncoder.encode(result, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.warn("StringFilter error", e.getMessage(), e);
        }
        return result;
    }

    /**
     * 验证合法性
     * @param store
     * @return
     */
    private Boolean validatMmsStore(MmsStore store) {
        if (store == null) {
            return false;
        }

        if (store.getPlayStatus() != null && store.getPlayStatus() == 1 && StringUtils.isNotEmpty(store.getCountry())
                && store.getCountry().equals("CN")) {
            ErrorCodeConstant.throwLetvCommonException(ErrorCodeConstant.CN_PLAY_FORBIDDEN_CODE);
        }
        if (store.getPlayStatus() != null && store.getPlayStatus() == 1 && StringUtils.isNotEmpty(store.getCountry())
                && !store.getCountry().equals("CN")) {
            ErrorCodeConstant.throwLetvCommonException(ErrorCodeConstant.NOT_CN_PLAY_FORBIDDEN_CODE);
        }

        if (!VideoTpConstant.VIDEO_PLAY_MMS_DATA_CODE_SUCCESS.equalsIgnoreCase(store.getStatusCode())) {
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
            this.log.error(e.getMessage(), e);
        }
        return true;
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
    private MmsStore getMmsPlayInfo(Long vid, Long mid, String stream, Boolean ts, Boolean isLetvTv, Integer splatId,
            boolean isMarlin, CommonParam commonParam) {
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
                if (!CollectionUtils.isEmpty(info.getInfos())) {// 此处判空，避免循环时出现空指针
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

    private VideoPlayDto getPlayResultByAuth(VideoMysqlTable video, AlbumMysqlTable album, Boolean playAuth,
            VideoPlayDto videoPlay, String token, String tokenUserId, String playOrDownload) {

        Integer playResult = CommonConstants.PLAY_TYPE.ZHENGCHANG.getValue();// 默认正常返回

        if (playAuth && (videoPlay != null) && "play".equalsIgnoreCase(playOrDownload)) {
            String newPlayUrl = videoPlay.getPlayUrl() + "&token=" + token + "&uid=" + tokenUserId;
            String newBackUrl0 = videoPlay.getBackUrl0() + "&token=" + token + "&uid=" + tokenUserId;// 备用播放地址1
            String newBackUrl1 = videoPlay.getBackUrl1() + "&token=" + token + "&uid=" + tokenUserId;// 备用播放地址2
            String newBackUrl2 = videoPlay.getBackUrl2() + "&token=" + token + "&uid=" + tokenUserId;// 备用播放地址3

            videoPlay.setPlayUrl(newPlayUrl);
            videoPlay.setBackUrl0(newBackUrl0);
            videoPlay.setBackUrl1(newBackUrl1);
            videoPlay.setBackUrl2(newBackUrl2);
        }

        if (playAuth && (videoPlay != null) && "download".equalsIgnoreCase(playOrDownload)) {
            String newPlayUrl = videoPlay.getPlayUrl() + "&token=" + token + "&uid=" + tokenUserId;
            // + "&format=1&expect=2";
            String newBackUrl0 = videoPlay.getBackUrl0() + "&token=" + token + "&uid=" + tokenUserId;
            // + "&format=1&expect=2";// 备用播放地址1
            String newBackUrl1 = videoPlay.getBackUrl1() + "&token=" + token + "&uid=" + tokenUserId;
            // + "&format=1&expect=2";// 备用播放地址2
            String newBackUrl2 = videoPlay.getBackUrl2() + "&token=" + token + "&uid=" + tokenUserId;
            // + "&format=1&expect=2";// 备用播放地址3

            videoPlay.setPlayUrl(newPlayUrl);
            videoPlay.setBackUrl0(newBackUrl0);
            videoPlay.setBackUrl1(newBackUrl1);
            videoPlay.setBackUrl2(newBackUrl2);
        }

        if (!playAuth) {// 未通过鉴权
            // 如果是电视剧、动漫第一集,或电影的正片 则走试看逻辑
            if (((video.getCategory() != null && (video.getCategory() == MmsTpConstant.MMS_CATEGARY_TV || video
                    .getCategory() == MmsTpConstant.MMS_CATEGARY_CARTOON)) && video.getPorder() != null && video
                    .getPorder() == 1)
                    || (video.getCategory() != null && (video.getCategory() == MmsTpConstant.MMS_CATEGARY_FILM) && (CommonConstants.ALBUM_TYPE.ZHENGPIAN
                            .getMs_videotype().equals(video.getVideo_type() + "")))) {
                if (album != null && album.getIsyuanxian() != null && album.getIsyuanxian() == 1) {
                    playResult = CommonConstants.PLAY_TYPE.SHIKAN_YUANXIAN.getValue();
                } else {
                    playResult = CommonConstants.PLAY_TYPE.SHIKAN.getValue();
                }
            } else { // 350免费播放
                playResult = CommonConstants.PLAY_TYPE.MIANFEI_350.getValue();
                // stream = StreamConstants.CODE_NAME_350;
            }
        }

        // playMap.put("playResult", playResult);
        videoPlay.setPlayType(playResult);

        return videoPlay;

    }

    private VideoPlayDto videoPlayDetail(String stream, VideoMysqlTable video, AlbumMysqlTable album,
            VideoPlayDto videoPlay, MmsStore store, Boolean expectTS, String terminalSeries, Boolean expectTSActual,
            Boolean expectDispath302, Boolean exceptNomalActual, Boolean playAuth, String token, String tokenUserId,
            Boolean ifCharge, String playOrDownload, CommonParam commonParam) {
        long start;
        long end;

        String validateStream = videoPlay.getCurrentStream();
        if (videoPlay.getHasBelow() == 1) {
            // playInfo.setHasBelow(1);
            Object[] codeMap = { LetvStreamCommonConstants.nameOf(stream, commonParam.getLangcode()),
                    LetvStreamCommonConstants.nameOf(validateStream, commonParam.getLangcode()) };
            String tipMsg = MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_REDUCESTREAM,
                    commonParam.getLangcode(), codeMap);
            videoPlay.setTipMsg(tipMsg);
        }

        String albumName = video.getName_cn();
        if (album != null) {
            albumName = album.getName_cn();
        }
        videoPlay.setShowName(this.getPlayShowName(video.getCategory(), albumName, video.getName_cn(),
                video.getPorder(), validateStream, video.getStarring_all(), commonParam.getLangcode()));
        start = System.currentTimeMillis();
        VideoHot hot = this.getHeadTailInfo(video.getCategory(), video);
        end = System.currentTimeMillis();
        this.log.info("[2.0播放]-获取片头片尾信息:" + (end - start) + "ms");
        if (hot != null) {
            Integer b = hot.getT() == null ? 0 : hot.getT();
            Integer e = hot.getF() == null ? 0 : hot.getF();
            videoPlay.setVideoHeadTime(b * 1000);
            videoPlay.setVideoTailTime(e * 1000);
        }

        videoPlay = this.getVideoPlayInfo(videoPlay, store, ifCharge, validateStream, terminalSeries, expectTS,
                expectTSActual, expectDispath302, exceptNomalActual);

        // 设置播放地址
        videoPlay = this.getPlayResultByAuth(video, album, playAuth, videoPlay, token, tokenUserId, playOrDownload);

        Integer playResult = videoPlay.getPlayType();
        // videoPlay.setPlayType(playResult);
        if (playResult == CommonConstants.PLAY_TYPE.SHIKAN.getValue()
                || playResult == CommonConstants.PLAY_TYPE.SHIKAN_YUANXIAN.getValue()) {
            videoPlay.setTryPlayTime(this.getTryPlayTime(video.getCategory(), videoPlay.getVideoTailTime(),
                    videoPlay.getDuration()));
            String tipMsg = (video.getCategory() != null && (video.getCategory() == MmsTpConstant.MMS_CATEGARY_FILM)) ? MessageUtils
                    .getMessage(VideoConstants.VIDEO_TIPMSG_TRY_PLAY_SIX_MINUTES, commonParam.getLangcode())
                    : MessageUtils.getMessage(VideoConstants.VIDEO_TIPMSG_FOR_FREE_PLAY_FIRST_EPISODE,
                            commonParam.getLangcode());
            videoPlay.setTryPlayTipMsg(tipMsg);
        }
        // String static_categoyCode =
        // CategoryIdConstants.STATISTICS_CATEGORY_CODE.get(video.getCategory_all());
        // videoPlay.setStatisticsCode(static_categoyCode);

        videoPlay.setNeedWaterMarking(video.getLogonum() == null ? 0 : video.getLogonum());
        if (videoPlay.getNeedWaterMarking() == 1) {
            List<WaterMarkImage> waterList = this.getWaterMark(video.getCategory(), video.getPid());
            videoPlay.setWaterMarkImage(waterList);
        }

        if (StringUtils.isNotEmpty(videoPlay.getStorePath())) {
            videoPlay.setDrmTokenUrl(ApplicationConstants.PLAY_G3_BASE_HOST + "/"
                    + videoPlay.getStorePath().replace(".mpd", ".xml") + "?" + videoPlay.getPlayUrl().split("\\?")[1]);
        }

        if (video != null) {
            videoPlay.setCategoryId(video.getCategory());
            videoPlay.setVideoType(video.getVideo_type());
            videoPlay.setPorder(video.getPorder());
            videoPlay.setEpisode(video.getEpisode());
        }

        return videoPlay;
    }

    private VideoMysqlTable getIptvVideoInfo(Long vrsVideoInfoId, Long videoId, Integer cntv, CommonParam commonParam) {
        VideoMysqlTable iptvVideoInfo = null;
        if ((vrsVideoInfoId == null || vrsVideoInfoId <= 0) && videoId > 0) {
            iptvVideoInfo = albumVideoAccess.getVideo(videoId, commonParam);
        } else {
            iptvVideoInfo = albumVideoAccess.getVideo(vrsVideoInfoId, commonParam);
        }

        return iptvVideoInfo;
    }

    private AlbumMysqlTable getIptvAlbumInfo(Long vrsAlbumId, Long iptvAlbumId, Integer broadcastId,
            CommonParam commonParam) {
        Long id = null;
        if (vrsAlbumId != null) {
            id = vrsAlbumId;
        } else if (iptvAlbumId != null) {
            id = iptvAlbumId;
        }
        AlbumMysqlTable album = albumVideoAccess.getAlbum(id, commonParam);
        return album;
    }

    private Map<String, Object> getVideoPlayStream(VideoMysqlTable iptvVideoInfo, String stream, String channelCode,
            String actionType, Boolean expectTS, String playType, Boolean expectTSActual, Boolean expectDispath302,
            Boolean exceptNomalActual, CommonParam commonParam) {

        Map<String, Object> streamMap = new HashMap<String, Object>();

        // 获得可用码流
        String streams = iptvVideoInfo.getPlay_streams();
        if (StringUtils.isEmpty(streams)) {
            ErrorCodeConstant.throwLetvCommonException(ErrorCodeConstant.STREAM_NOT_FOUND);
        }

        List<StreamCode> streamCodes = this.sortAndfilterStreamCode(streams, channelCode,
                iptvVideoInfo.getCategory_all(), commonParam.getLangcode());

        VideoPlayDto videoPlay = new VideoPlayDto();
        videoPlay = this.getValidateStream(stream, iptvVideoInfo, channelCode, videoPlay, commonParam.getLangcode());
        videoPlay.setStreams(streamCodes);

        String validateStream = videoPlay.getCurrentStream();
        // 播放信息
        Long mid = iptvVideoInfo.getMidByStream(validateStream);
        // 杜比频道特殊处理
        if (!("download").equalsIgnoreCase(actionType)) {
            expectTS = this.getFinalTS(stream);// Marlin DRM 码流为整个文件
        }

        boolean isMarlin = false;
        // 加入DRM码流标志
        if (iptvVideoInfo != null && StringUtils.isNotEmpty(iptvVideoInfo.getDrmFlagId())) {
            videoPlay.setDrmFlagId(iptvVideoInfo.getDrmFlagId());
            // isMarlin =iptvVideoInfo.getDrmFlagId().contains("642002");
            if (isMarlin) {
                expectTS = false;
            }
        }

        // 获得最终播放码流
        MmsStore store = this.getVideoPlayInfo(mid, validateStream, expectTS, actionType, playType, streams.split(","),
                true, iptvVideoInfo.getPid(), iptvVideoInfo.getId(), isMarlin, commonParam);

        streamMap.put("VideoPlayDto", videoPlay);
        streamMap.put("MmsStore", store);
        streamMap.put("expectTS", expectTS);
        return streamMap;
    }
}
