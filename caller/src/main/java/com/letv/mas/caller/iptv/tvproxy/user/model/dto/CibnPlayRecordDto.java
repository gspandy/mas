package com.letv.mas.caller.iptv.tvproxy.user.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.PlayLogTp.PlayLogInfo;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants.Category;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public class CibnPlayRecordDto extends PlayRecordDto {
    private String img_400X300;// 400X300图
    private String img_200X150;// 200X150图
    private String img_150X200;// 150x200图
    private String img_300X400;// 300x400图

    /**
     * @param ablum
     *            专辑信息
     * @param video
     *            视频信息
     * @param playLog
     *            第三方接口返回的播放记录
     * @param isTV
     *            是否有TV播放版权
     */
    public CibnPlayRecordDto(AlbumMysqlTable album, VideoMysqlTable video, PlayLogInfo playLog, boolean isTV,
                             CommonParam commonParam) {
        this.categoryId = 0;
        if (StringUtils.isNotBlank(playLog.getCid())) {
            this.categoryId = StringUtil.toInteger(playLog.getCid());
        }
        if (album != null) {
            this.albumId = (album.getId() == null) ? null : album.getId().toString();
            this.tv_out = (album.getPushflag() != null && album.getPushflag() == 1) ? 0 : 1;
            this.img_200X150 = getAvailableImg(album, video, 200, 150);
            this.img_400X300 = getAvailableImg(album, video, 400, 300);
            this.img_150X200 = getAvailableImg(album, video, 150, 200);
            this.img_300X400 = getAvailableImg(album, video, 300, 400);
            this.episodes = album.getEpisode();
            this.follownum = album.getFollownum();
            this.albumName = album.getName_cn();
            this.isCopyright = isTV;
            this.albumTvCopyright = 1;
        } else {
            if ("258".compareTo(commonParam.getAppCode()) <= 0) {
                // 2.9.8版本之前，还是过滤专辑id，2.9.8版本之后，把pid都放开，用另一字段标识专辑是否有版权
                this.albumId = (playLog.getPid() == null) ? null : playLog.getPid().toString();
                this.albumTvCopyright = 0;
            }
        }
        this.isEnd = playLog.getIsend() != null && playLog.getIsend() == 1;

        if (this.categoryId == Category.FILM) {
            this.videoType = 1;
            this.follownum = 1;
        } else if (this.categoryId == Category.TV || this.categoryId == Category.CARTOON
                || this.categoryId == Category.VARIETY) {
            this.videoType = 2;
        } else {
            this.videoType = 0;
        }

        Integer nc = playLog.getNc();
        if (nc != null) {
            if (nc == 0) {
                nc = 1;
            }
            this.seriesNum = nc;
        }
        this.videoId = (playLog.getVid() == null) ? null : playLog.getVid().toString();
        this.videoName = playLog.getTitle();
        // 默认图处理（长视频显示专辑图片、短视频或者无专辑视频优先调用视频图，无图则调用用户中心）
        if (this.categoryId == Category.TV || this.categoryId == Category.FILM || this.categoryId == Category.PARENTING
                || this.categoryId == Category.DFILM || this.categoryId == Category.CARTOON
                || this.categoryId == Category.VARIETY) {
            this.imgPic = getAvailableImg(album, video, 400, 300) == null ? getAvailableImg(album, video, 200, 150)
                    : getAvailableImg(album, video, 400, 300);
        } else {
            this.imgPic = getAvailableImg(video, 400, 300) == null ? getAvailableImg(video, 200, 150)
                    : getAvailableImg(video, 400, 300);
        }
        // 纪录片 有专辑属性展示视频名
        if (this.categoryId == Category.DFILM && album != null) {
            this.subtitle = playLog.getTitle();
        }
        Long htime = playLog.getHtime();
        if (htime == null || htime < 0 || (htime > Integer.MAX_VALUE / 1000)) {
            // 2015-09-22播放记录bug修改，针对非法数据，直接设置为播放结束
            this.playTime = -1000L;
        } else {
            this.playTime = htime * 1000L;
        }

        this.duration = playLog.getVtime() * 1000L;
        this.from = this.getPlayLogFrom(playLog.getFrom());
        this.source = playLog.getFrom();
        if (playLog.getUtime() != null) {
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) { // le.com传时间戳
                this.lastTime = String.valueOf(playLog.getUtime());
            } else {
                this.lastTime = TimeUtil
                        .getDateString(new Date(playLog.getUtime() * 1000), TimeUtil.SIMPLE_DATE_FORMAT);
            }
        }
        if (playLog.getUtime() != null) {
            this.lastUpdateTime = playLog.getUtime() * 1000;
        }

        // 2015-07-29添加一个过滤，过滤掉综艺的下一集id
        this.nvid = 0L;// 默认为0，表示没有下一集
        if (this.videoType == 2) {// videoType=2时，表示是电视剧，动漫或者综艺类型
            if (this.categoryId == Category.TV || this.categoryId == Category.CARTOON
                    || this.categoryId == Category.VARIETY) {
                // 只有是电视剧和卡通，才返回下一集id(2015-08-26，如果当前剧集数大于等于最新剧集数，就不返回下一集id)
                if (this.follownum != null && this.seriesNum != null
                        && this.seriesNum.intValue() < this.follownum.intValue()) {
                    this.nvid = playLog.getNvid();
                }
            }
        }
        this.url = DataConstant.PC_PLAY_URL + this.videoId + ".html";
    }

    public String getImg_400X300() {
        return img_400X300;
    }

    public void setImg_400X300(String img_400x300) {
        img_400X300 = img_400x300;
    }

    public String getImg_200X150() {
        return img_200X150;
    }

    public void setImg_200X150(String img_200x150) {
        img_200X150 = img_200x150;
    }

    public String getImg_150X200() {
        return img_150X200;
    }

    public void setImg_150X200(String img_150x200) {
        img_150X200 = img_150x200;
    }

    public String getImg_300X400() {
        return img_300X400;
    }

    public void setImg_300X400(String img_300x400) {
        img_300X400 = img_300x400;
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

    private String getPlayLogFrom(Integer from) {
        if (from == null) {
            return "";
        }
        String[] s = { "", "网页", "手机客户端", "平板", "电视", "桌面客户端", "超级汽车" };
        if (from > -1 && from < s.length) {
            return s[from];
        }
        return "";
    }
}
