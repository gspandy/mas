package com.letv.mas.caller.iptv.tvproxy.user.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants.Category;
import com.letv.mas.caller.iptv.tvproxy.video.service.VideoService;
import org.apache.commons.lang.StringUtils;
import serving.AlbumAttribute;
import serving.ResultDocInfo;
import serving.VideoAttribute;
import serving.VideoAttributeInAlbum;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.PlayLogTp.PlayLogInfo;

public class LevidiPlayRecordDto extends PlayRecordDto {

    private String aGlobalId; // 专辑globalId
    private String vGlobalId; // 视频globalId
    private String externalId; // 视频externalId
    private String mid; // 媒资id
    private String sourceType; // 来源, "youtube" etc.

    public LevidiPlayRecordDto(PlayLogInfo playLog, ResultDocInfo docInfo, AlbumAttribute albumAttribute,
            VideoAttributeInAlbum videoAttributeInAlbum, CommonParam commonParam) {
        if (docInfo != null && StringUtils.isNotBlank(docInfo.getCategory())) {
            this.categoryId = StringUtil.toInteger(docInfo.getCategory());
            this.aGlobalId = docInfo.getLetv_original_id();
            this.albumId = docInfo.getId();
        }
        if (albumAttribute != null) {
            String playPlatform = VideoService.getSarrsPlayPlatForm(albumAttribute.getPush_flag());
            this.tv_out = (playPlatform != null && playPlatform.contains("420007")) ? 0 : 1;
            this.episodes = (int) albumAttribute.getEpisodes();
            this.albumName = albumAttribute.getName();
            this.imgPic = this.getPic(albumAttribute.getImages(), 640, 360);
            this.sourceType = albumAttribute.getSub_src();
        }

        if (videoAttributeInAlbum != null) {
            this.videoId = videoAttributeInAlbum.getVid();
            this.videoName = videoAttributeInAlbum.getName();
            this.vGlobalId = videoAttributeInAlbum.getLetv_original_id();
            this.externalId = videoAttributeInAlbum.getExternal_id();
            this.mid = videoAttributeInAlbum.getMid();
            Short is_pay = videoAttributeInAlbum.getIs_pay();
            if (is_pay != null) {
                this.setIsPay(is_pay.intValue());
            }
            if (StringUtil.isNotBlank(videoAttributeInAlbum.getDuration())) {
                this.duration = StringUtil.toLong(videoAttributeInAlbum.getDuration() + "000", 0L);
            }
        }

        if (this.categoryId != null) {
            if (this.categoryId == Category.FILM) {
                this.videoType = 1;
            } else if (this.categoryId == Category.TV || this.categoryId == Category.CARTOON
                    || this.categoryId == Category.VARIETY) {
                this.videoType = 2;
            } else {
                this.videoType = 0;
            }
        } else {
            this.videoType = 0;
        }

        if (playLog != null) {
            Long htime = playLog.getHtime();
            if (htime == null || htime < 0 || (htime > Integer.MAX_VALUE / 1000)) {
                // 2015-09-22播放记录bug修改，针对非法数据，直接设置为播放结束
                this.playTime = -1000L;
            } else {
                this.playTime = htime * 1000L;
            }

            if (playLog.getUtime() != null) { // 上传时间
                this.lastTime = String.valueOf(playLog.getUtime());
            }
            if (playLog.getIsend() != null) {
                this.isEnd = playLog.getIsend() == 0 ? false : true;
            }
        }
        this.from = String.valueOf(UserTpConstant.PLAY_LOG_FROM.TV.getCode());
        this.nvid = 0L;// 默认为0，表示没有下一集
        this.url = DataConstant.PC_PLAY_URL + this.videoId + ".html";
    }

    public LevidiPlayRecordDto(PlayLogInfo playLog, ResultDocInfo docInfo, VideoAttribute videoAttribute,
            CommonParam commonParam) {
        if (docInfo != null) {
            this.vGlobalId = docInfo.getLetv_original_id();
            this.categoryId = StringUtil.toInteger(docInfo.getCategory());
        }
        if (videoAttribute != null) {
            this.videoId = videoAttribute.getVid();
            this.duration = StringUtil.toLong(videoAttribute.getDuration() + "000", 0L);
            this.externalId = videoAttribute.getExternal_id();
            this.videoName = videoAttribute.getName();
            this.tv_out = 0; // 详情借接口数据不全， 暂时写死为0
            this.mid = videoAttribute.getMid();
            this.episodes = videoAttribute.getEpisodes();
            this.aGlobalId = videoAttribute.getAlbum_original_id();
            this.albumId = videoAttribute.getAid();
            this.albumId = videoAttribute.getAid();
            this.sourceType = videoAttribute.getSource();
        }

        this.nvid = 0L;// 默认为0，表示没有下一集
        this.url = DataConstant.PC_PLAY_URL + this.videoId + ".html";
        if ("youtube".equals(videoAttribute.getSource())) {
            if (videoAttribute.getImages() != null) {
                String pic = videoAttribute.getImages().get("1280*720");// youtube内容取1280*720尺寸的图片
                if (pic == null) {
                    pic = videoAttribute.getImages().get("320*180");// 取不到再取320*180尺寸的图片
                }
                this.imgPic = pic;
            }
        }
        if (this.categoryId != null) {
            if (this.categoryId == Category.FILM) {
                this.videoType = 1;
            } else if (this.categoryId == Category.TV || this.categoryId == Category.CARTOON
                    || this.categoryId == Category.VARIETY) {
                this.videoType = 2;
            } else {
                this.videoType = 0;
            }
        } else {
            this.videoType = 0;
        }

        if (playLog != null) {
            Long htime = playLog.getHtime();
            if (htime == null || htime < 0 || (htime > Integer.MAX_VALUE / 1000)) {
                // 2015-09-22播放记录bug修改，针对非法数据，直接设置为播放结束
                this.playTime = -1000L;
            } else {
                this.playTime = htime * 1000L;
            }

            if (playLog.getUtime() != null) { // 上传时间
                this.lastTime = String.valueOf(playLog.getUtime());
            }
            if (playLog.getIsend() != null) {
                this.isEnd = playLog.getIsend() == 0 ? false : true;
            }
            this.from = String.valueOf(playLog.getFrom());
            this.nvid = playLog.getNvid();
        }
    }

    public String getaGlobalId() {
        return aGlobalId;
    }

    public void setaGlobalId(String aGlobalId) {
        this.aGlobalId = aGlobalId;
    }

    public String getvGlobalId() {
        return vGlobalId;
    }

    public void setvGlobalId(String vGlobalId) {
        this.vGlobalId = vGlobalId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

}
