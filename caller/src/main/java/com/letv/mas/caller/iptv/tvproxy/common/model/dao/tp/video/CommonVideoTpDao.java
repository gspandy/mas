package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.VideoCommonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class CommonVideoTpDao extends BaseTpDao {
    private final static Logger log = LoggerFactory.getLogger(CommonVideoTpDao.class);
    private final static Logger IP_MASKING_LOG = LoggerFactory.getLogger("ipmaskingLog");
    public static final String CIPHER_LIVE = "sefUI(*q3JKsdf[[k%-90sdf";

    /**
     * 获取媒资视频信息
     * @param id
     *            视频ID
     * @param region
     *            地域
     * @param lang
     *            语言
     * @param hasHit
     *            是否获取屏蔽视频信息
     * @return 视频信息
     */
    public MmsResponse<MmsVideoInfo> getMmsVideoById(Long id, String platform, String region, String lang,
                                                     boolean hasHit) {
        MmsResponse<MmsVideoInfo> response = null;
        try {
            StringBuilder url = new StringBuilder(VideoTpConstant.TP_VIDEO_INFO_GET);
            url.append("id=").append(id);
            // 1视频基本信息 2视频基本信息+码率
            url.append("&type=2");
            // 控制视频信息输出 0：全量 1：精简 2:乐拍专用模式 3-播放记录专用(精简信息)
            url.append("&vmode=0");
            url.append("&token=").append(VideoCommonUtil.getToken(id, platform));
            url.append("&platform=").append(platform);
            url.append("&lang=").append(StringUtils.trimToEmpty(lang));
            url.append("&region=").append(region); // 站点信息
            if (hasHit) {
                // 当hit=1时, 返回被屏蔽视频信息
                url.append("&hit=1");
            }
            // 可播地区白名单,
            // 如：whiteList=US,允许美国播放.该参数可以与region结合使用，当传入whiteList=US&region=US表示获取美国站，允许美国播放的数据.
            url.append("&whiteList=").append(StringUtils.trimToEmpty(region));

            // http://i.api.letv.com/mms/inner/video/get?id={id}&type={type}&token={token}&platform={platform}&vmode={vmode}&lang={lang}&region={region}&whiteList={whiteList}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                log.info("getMmsVideoById_" + id + "_" + region + "_" + lang + " result length:" + result.length());
                result = result.replaceAll("　", " ");
                response = objectMapper.readValue(result, new TypeReference<MmsResponse<MmsVideoInfo>>() {
                });
            }
        } catch (Exception e) {
            log.error("getMmsVideoById " + id + " error:" + e.getMessage(), e);
        }
        return response;
    }

    /**
     * 获取媒资专辑信息
     * @param id
     *            专辑ID
     * @param region
     *            地域
     * @param lang
     *            语言
     * @param hasHit
     *            是否获取屏蔽专辑信息
     * @return 专辑信息
     */
    public MmsResponse<MmsAlbumInfo> getMmsAlbumById(Long id, String platform, String region, String lang,
                                                     boolean hasHit) {
        MmsResponse<MmsAlbumInfo> response = null;
        try {
            StringBuilder url = new StringBuilder(VideoTpConstant.TP_ALBUM_INFO_GET);
            url.append("id=").append(id);
            // 控制专辑信息输出 0：全量 (只支持单个id格式) 1：精简(支持多个id格式)
            url.append("&amode=0");
            url.append("&token=").append(VideoCommonUtil.getToken(id, platform));
            url.append("&platform=").append(platform);
            url.append("&lang=").append(StringUtils.trimToEmpty(lang));
            url.append("&region=").append(region);
            if (hasHit) {
                // 当hit=1时, 返回被屏蔽专辑信息
                url.append("&hit=1");
            }
            // 可播地区白名单,
            // 如：whiteList=US,允许美国播放.该参数可以与region结合使用，当传入whiteList=US&region=US表示获取美国站，允许美国播放的数据.
            url.append("&whiteList=").append(StringUtils.trimToEmpty(region));

            // http://i.api.letv.com/mms/inner/albumInfo/get?id={id}&amode={amode}&format={format}&token={token}&platform={platform}&lang={lang}&region={region}&whiteList={whiteList}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                log.info("getMmsAlbumById_" + id + "_" + region + "_" + lang + " result length: " + result.length());
                result = result.replaceAll("　", " ");
                response = objectMapper.readValue(result, new TypeReference<MmsResponse<MmsAlbumInfo>>() {
                });
            }
        } catch (Exception e) {
            log.error("getMmsAlbumById is error,id:" + id + "_" + e.getMessage(), e);
        }

        return response;
    }

    /**
     * 获取专辑下视频列表数据
     * @param albumId
     *            专辑id
     * @param videoId
     *            该专辑下的视频id，返回vid所在页视频列表，并输出vid所在当前页码，必须与参数s共同使用。此时参数 b 无效
     * @param page
     *            第几页，用于分页使用
     * @param pageSize
     *            每页数量 用于分页用，最大不超过60
     * @param porder
     *            分页排序方式排序，-1:按照集数porder升序，1:按照集数porder降序，默认-1
     * @param region
     *            地区
     * @param lang
     *            语言
     * @param queryType
     *            查询类型，0:非正片视频，1:正片视频，2:所有视频
     * @return
     */
    public MmsResponse<MmsVideoList> getVideoListByAlbumId(Long albumId, Long videoId, Integer page, Integer pageSize,
                                                           Integer porder, String platform, String region, String lang, String queryType) {
        if (page == null || page < 0) {
            page = 1;
        }
        if (pageSize == null || pageSize < 0 || pageSize > VideoTpConstant.ALBUM_SERIES_PAGE_SIZE) {
            pageSize = VideoTpConstant.ALBUM_SERIES_PAGE_SIZE;
        }
        if (porder == null) {
            porder = -1;
        }
        MmsResponse<MmsVideoList> response = null;
        StringBuilder url = new StringBuilder();
        if (VideoTpConstant.QUERY_TYPE_POSITIVE.equals(queryType)) {// 获取专辑下正片视频
            url.append(VideoTpConstant.VIDEO_LIST_POSITIVE);
        } else if (VideoTpConstant.QUERY_TYPE_NON_POSITIVE.equals(queryType)) {// 获取专辑下非正片视频
            url.append(VideoTpConstant.VIDEO_LIST_NON_POSITIVE);
        } else {// 获取专辑下所有视频
            url.append(VideoTpConstant.VIDEO_LIST_ALL);
        }
        url.append("id=").append(albumId);// 专辑id
        url.append("&b=").append(page);// 第几页，用于分页使用
        url.append("&s=").append(pageSize);// 每页数量 用于分页用，最大不超过60
        url.append("&o=").append(porder);// 分页排序方式排序，-1:按照集数porder升序，1:按照集数porder降序，默认-1
        if (videoId != null) {
            // 此时参数 b 无效
            url.append("&vid=").append(videoId);// 该专辑下的视频id，返回vid所在页视频列表，并输出vid所在当前页码，必须与参数s共同使用。
        }
        url.append("&platform=").append(platform);// 播放平台
        url.append("&p=").append(platform);// 指定播放平台，可传空
        url.append("&lang=").append(lang);// 语言
        url.append("&region=").append(region);// 地区
        try {
            // http://i.api.letv.com/mms/inner/albumInfo/getVideoList?id={id}&b={b}&s={s}&o={o}&vid={vid}&platform={platform}&p={p}&lang={lang}&region={region}
            String result = this.restTemplate.getForObject(url.toString(), String.class);
            if (StringUtil.isNotBlank(result)) {
                response = objectMapper.readValue(result, new LetvTypeReference<MmsResponse<MmsVideoList>>() {
                });
            }
        } catch (Exception e) {
            log.error("getPositiveVideoListByAlbumId return error: " + " return error: ", e);
        }

        return response;
    }

    /**
     * 防盗链 获取播放调度地址（通用播放器）
     * @param request
     * @param actType
     * @return
     */
    public MmsStore getMmsPlayInfo4Live(String clientIp, Long vid, Long mid, String videoType, int actType,
                                        Integer splatid, int platid, String tss, CommonParam commonParam) {
        MmsStore response = null;
        try {
            StringBuilder url = new StringBuilder("http://i.api.letv.com/geturl?");
            url.append("mmsid=").append(mid);
            url.append("&platid=").append(platid);
            url.append("&splatid=").append(splatid);
            url.append("&playid=").append(actType);
            url.append("&vid=").append(vid);
            url.append("&vtype=").append(videoType);
            url.append("&key=").append(getKey4Live(mid, platid));
            url.append("&format=").append(1);
            url.append("&version=").append(2.0);
            url.append("&clientip=").append(clientIp);
            url.append("&tss=").append(tss);
            for (int i = 0; i < 3; i++) {
                String result = this.restTemplate.getForObject(url.toString(), String.class);
                if (StringUtil.isNotBlank(result)) {
                    log.info("getMmsPlayInfo4Live_" + vid + "_" + clientIp + ": invoke return length is  ["
                            + result.length() + "]");
                    response = objectMapper.readValue(result, MmsStore.class);
                    // 打印ip屏蔽，请运维纠正
                    if (response != null && response.getData() == null) {
                        IP_MASKING_LOG.info("getMmsPlayInfo4Live_" + vid + "_" + mid + " ip forbidden " + "ip="
                                + clientIp + ",country=" + response.getCountry());
                    }
                }
                if (response != null) {
                    break;
                }
            }
        } catch (Exception e) {
            log.info("getMmsPlayInfo4Live_" + vid + "_" + clientIp + " error:" + e.getMessage(), e);
        }

        return response;
    }

    /**
     * 获取调度地址时需要传这个key
     * @param mmsId
     * @param platid
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private static String getKey4Live(Long mmsId, Integer platid) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        String str = mmsId + CIPHER_LIVE + platid;
        String md5 = MessageDigestUtil.md5(str.getBytes());
        return md5;
    }
}
