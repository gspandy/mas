package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow;

import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.GameSdkDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.LiveSdkDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.bean.bo.Subject;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.bean.bo.SubjectPreLive;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.*;
import com.letv.mas.caller.iptv.tvproxy.channel.model.dto.Channel;
import com.letv.mas.caller.iptv.tvproxy.channel.model.dto.ChannelData;
import com.letv.mas.caller.iptv.tvproxy.common.constant.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.live.model.bean.bo.Live;
import com.letv.mas.caller.iptv.tvproxy.terminal.constant.TerminalConstant;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.AlbumDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.BurrowUtil.BurrowObj;

public class ChildBurrow extends AbstractBurrow {

    public ChildBurrow(BurrowUtil burrowUtil) {
        super(burrowUtil);
    }

    @Override
    public BaseData burrowObj(BaseData data, CommonParam commonParam) {
        if (data instanceof Live) {
            Live live = (Live) data;
            if (String.valueOf(VideoConstants.Category.SPORT).equals(live.getCategoryId())) {// 体育直播
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_SPORTS_LIVE, null, commonParam);
            } else {// 跳转tvlive
                // tvlive只能接收string类型
                LiveSdkDto.TVLiveBurrowDto tlbd = new LiveSdkDto.TVLiveBurrowDto();
                tlbd.setChannelId(live.getLiveId());
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_LIVE_LIVE, tlbd, commonParam);
            }
        } else if (data instanceof VideoDto) {
            VideoDto video = (VideoDto) data;
            if (video.getCategoryId() != null && VideoConstants.Category.SPORT == video.getCategoryId()) {// 单视频跳转体育
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_SPORTS_VIDEO, null, commonParam);
            } else {
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_CHILD_VIDEO, null, commonParam);
            }
        } else if (data instanceof Subject) {
            Subject subject = (Subject) data;
            if (subject.getCategoryId() != null && subject.getCategoryId() == VideoConstants.Category.SPORT) {// 体育专题跳转体育
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_SPORTS_SUBJECT, null, commonParam);
            } else {
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_CHILD_SUBJECT, null, commonParam);
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
                LiveSdkDto.TVLiveBurrowDto liveSdk = new LiveSdkDto.TVLiveBurrowDto();
                liveSdk.setChannelId(block.getBlockId());
                liveSdk.setType(block.getType() + "");
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_GUIDE, liveSdk, commonParam);
            } else if (!StringUtil.isBlank(block.getAppCode())) {
                if (TerminalConstant.THIRD_PARTY_SHOP.equals(block.getAppCode())) {
                    BurrowUtil.buildBurrowV2(data, BurrowObj.LE_SHOP_COMMON, block.getAppParam(), commonParam);
                } else if (TerminalConstant.THIRD_PARTY_GOLIVE.equals(block.getAppCode())) {
                    BurrowUtil.buildBurrowV2(data, BurrowObj.LE_GOLIVE_COMMON, block.getAppParam(), commonParam);
                } else if (TerminalConstant.THIRD_PARTY_LESTORE_SUBJECT.equals(block.getAppCode())) {
                    BurrowUtil.buildBurrowV2(data, BurrowObj.LE_APPSTORE_COMMON, block.getAppParam(), commonParam);
                }
            } else if (block.getDataType() == DataConstant.DATA_TYPE_PLAY_CENTER) {
                GameSdkDto.GameCenterBurrowDto gameSdkDto = new GameSdkDto.GameCenterBurrowDto();
                gameSdkDto.setId(StringUtil.toLong(block.getId()));
                gameSdkDto.setResource(BurrowUtil.GameCenterGcResource.CHILD);
                gameSdkDto.setAppPackageName(block.getExtendText());
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_GAME_START, gameSdkDto, commonParam);
            } else {
                this.burrowChildApp(data, commonParam);
            }
        } else {
            this.burrowChildApp(data, commonParam);
        }
        return data;
    }

    private void burrowChildApp(BaseData data, CommonParam commonParam) {
        if (data instanceof Channel || data instanceof ChannelData) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_CHILD_CHANNEL, null, commonParam);
        } else if (data instanceof Browser) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_CHILD_BORROW, null, commonParam);
        } else if (data instanceof SubjectPreLive) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_CHILD_PRELIVE, null, commonParam);
        } else if (data instanceof Subject) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_CHILD_SUBJECT, null, commonParam);
        } else if (data instanceof BaseBlock) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_CHILD_BASEBLOCK, null, commonParam);
        } else if (data instanceof Container) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_CHILD_CONTAINER, null, commonParam);
        } else if (data instanceof StaticBlock) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_CHILD_STATICBLOCK, null, commonParam);
        } else if (data instanceof AlbumDto) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_CHILD_ALBUM, null, commonParam);
        } else if (data instanceof VideoDto) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_CHILD_VIDEO, null, commonParam);
        } else if (data instanceof Live) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_CHILD_LIVE, null, commonParam);
        } else if (data instanceof Page) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_CHILD_PAGE, null, commonParam);
        } else if (data instanceof StarCms) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_CHILD_STAR, null, commonParam);
        }
    }

}
