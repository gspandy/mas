package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow;

import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.GameSdkDto.GameCenterBurrowDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.LiveSdkDto.TVLiveBurrowDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.SportsDto.*;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.bean.bo.Subject;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseBlock;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.live.model.bean.bo.Live;
import com.letv.mas.caller.iptv.tvproxy.terminal.constant.TerminalConstant;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.AlbumDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.BurrowUtil.BurrowObj;

public class LeviewBurrow extends AbstractBurrow {

    private static TvBurrow tvBurrow = new TvBurrow();
    private static CibnLeviewBurrow cibnLeviewBurrow = new CibnLeviewBurrow();
    private static WasuLeviewBurrow wasuLeviewBurrow = new WasuLeviewBurrow();

    public LeviewBurrow(BurrowUtil burrowUtil) {
        super(burrowUtil);
    }

    @SuppressWarnings("static-access")
    @Override
    public BaseData burrowObj(BaseData data, CommonParam commonParam) {
        if (data instanceof Live) {
            Live live = (Live) data;
            if (String.valueOf(VideoConstants.Category.SPORT).equals(live.getCategoryId())) {// 体育直播
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_SPORTS_LIVE, null, commonParam);
            } else {// 跳转tvlive
                // tvlive只能接收string类型
                TVLiveBurrowDto tlbd = new TVLiveBurrowDto();
                tlbd.setChannelId(live.getLiveId());
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_LIVE_LIVE, tlbd, commonParam);
            }
        } else if (data instanceof VideoDto) {
            VideoDto video = (VideoDto) data;
            if (video.getCategoryId() != null && VideoConstants.Category.SPORT == video.getCategoryId()) {// 单视频跳转体育
                SportsVideoBurrowDto svb = new SportsVideoBurrowDto();
                svb.setVid(StringUtil.isBlank(video.getVideoId()) ? 0 : Long.valueOf(video.getVideoId()));
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_SPORTS_VIDEO, svb, commonParam);
            } else {
                // BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_VIDEO, null,
                // commonParam);
                this.tvBurrow.burrowObj(data, commonParam);
            }
        } else if (data instanceof Subject) {
            Subject subject = (Subject) data;
            if (subject.getCategoryId() != null && subject.getCategoryId() == VideoConstants.Category.SPORT) {// 体育专题跳转体育
                SportsSubjectBurrowDto ssb = new SportsSubjectBurrowDto();
                ssb.setTopicId(subject.getSubjectId());
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_SPORTS_SUBJECT, ssb, commonParam);
            } else {
                // BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_SUBJECT, null,
                // commonParam);
                this.tvBurrow.burrowObj(data, commonParam);
            }
        } else if (data instanceof BaseBlock) {
            BaseBlock block = (BaseBlock) data;
            if (block.getType() != null && DataConstant.DATA_TYPE_SEARCH == block.getType()) {
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_PANO_MINE, null, commonParam);
            } else if (block.getType() != null && (DataConstant.DATA_TYPE_CHANNEL_LIST == block.getType())) {
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_CHANNELLIST, null, commonParam);
            } else if (block.getType() != null && (DataConstant.DATA_TYPE_MINE_LIST == block.getType())) {
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_MINE, null, commonParam);
            } else if (block.getType() != null && (DataConstant.DATA_TYPE_INDEX_LIST == block.getType())) {
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_INDEX, null, commonParam);
            } else if (block.getType() != null && (DataConstant.DATA_TYPE_LIVE_LIST == block.getType())) {
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_LIVEROOM, null, commonParam);
            } else if (block.getType() != null && (DataConstant.DATA_TYPE_LEGUIDE == block.getType())) {
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_GUIDE, null, commonParam);
            } else if (block.getType() != null && block.getDataType() == DataConstant.DATA_TYPE_TVSTATION) {
                TVLiveBurrowDto liveSdk = new TVLiveBurrowDto();
                liveSdk.setChannelId(block.getBlockId());
                liveSdk.setType(block.getType() + "");
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_LIVE_CHANNEL, liveSdk, commonParam);
            } else if (!StringUtil.isBlank(block.getAppCode())) {
                if (TerminalConstant.THIRD_PARTY_SHOP.equals(block.getAppCode())) {
                    BurrowUtil.buildBurrowV2(data, BurrowObj.LE_SHOP_COMMON, block.getAppParam(), commonParam);
                } else if (TerminalConstant.THIRD_PARTY_GOLIVE.equals(block.getAppCode())) {
                    BurrowUtil.buildBurrowV2(data, BurrowObj.LE_GOLIVE_COMMON, block.getAppParam(), commonParam);
                } else if (TerminalConstant.THIRD_PARTY_LESTORE_SUBJECT.equals(block.getAppCode())) {
                    BurrowUtil.buildBurrowV2(data, BurrowObj.LE_APPSTORE_COMMON, block.getAppParam(), commonParam);
                } else if (TerminalConstant.THIRD_PARTY_GAMECENTER.equals(block.getAppCode())) {
                    BurrowUtil.buildBurrowV2(data, BurrowObj.LE_GAME_ALBUM, block.getAppParam(), commonParam);
                } else if (TerminalConstant.THIRD_PARTY_CIBN.equals(block.getAppCode())) {
                    BurrowUtil.buildBurrowV2(data, BurrowObj.CIBN_COMMON, block.getAppParam(), commonParam);
                } else if (TerminalConstant.THIRD_PARTY_WASU.equals(block.getAppCode())) {
                    block.setVipFlag(null);
                    block.setExtendImg(null);
                    block.setButtonImg(null);
                    BurrowUtil.buildBurrowV2(data, BurrowObj.WASU_COMMON, block.getAppParam(), commonParam);
                }
                block.setAppCode(null);
                block.setAppParam(null);
                block.setVipFlag(null);
            } else if (block.getDataType() == DataConstant.DATA_TYPE_PLAY_CENTER) {
                GameCenterBurrowDto gameSdkDto = new GameCenterBurrowDto();
                gameSdkDto.setId(StringUtil.toLong(block.getId()));
                gameSdkDto.setResource(BurrowUtil.GameCenterGcResource.CHILD);
                gameSdkDto.setAppPackageName(block.getExtendText());
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_GAME_START, gameSdkDto, commonParam);
            } else {// 打洞到tv
                this.tvBurrow.burrowObj(data, commonParam);
            }
        } else {// 打洞到tv或国广
            if (data instanceof AlbumDto) {
                AlbumDto album = (AlbumDto) data;
                if (StringUtil.equals(album.getSource(), CommonConstants.CIBN_NAME)) {
                    return this.cibnLeviewBurrow.burrowObj(data, commonParam);
                } else if (StringUtil.equals(album.getSource(), CommonConstants.WASU_NEW_NAME)) {
                    return this.wasuLeviewBurrow.burrowObj(data, commonParam);
                }
            } else if (data instanceof VideoDto) {
                VideoDto video = (VideoDto) data;
                if (StringUtil.equals(video.getSource(), CommonConstants.CIBN_NAME)) {
                    return this.cibnLeviewBurrow.burrowObj(data, commonParam);
                }
            }
            this.tvBurrow.burrowObj(data, commonParam);
        }
        return data;
    }
}
