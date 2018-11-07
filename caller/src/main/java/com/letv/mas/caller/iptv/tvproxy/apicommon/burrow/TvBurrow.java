package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow;

import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.GameSdkDto.GameCenterBurrowDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.bean.bo.Subject;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.bean.bo.SubjectPreLive;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.*;
import com.letv.mas.caller.iptv.tvproxy.channel.model.dto.Channel;
import com.letv.mas.caller.iptv.tvproxy.channel.model.dto.ChannelData;
import com.letv.mas.caller.iptv.tvproxy.channel.model.dto.VideoSaleDto;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.live.model.bean.bo.Live;
import com.letv.mas.caller.iptv.tvproxy.terminal.constant.TerminalConstant;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.AlbumDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.BurrowUtil.BurrowObj;

@Component
public class TvBurrow extends AbstractBurrow {

    public TvBurrow() {
    }

    public TvBurrow(BurrowUtil burrowUtil) {
        super(burrowUtil);
    }

    @Override
    public BaseData burrowObj(BaseData data, CommonParam commonParam) {
        if (data instanceof Channel || data instanceof ChannelData) {
            BurrowUtil.buildBurrowV2(data, BurrowUtil.BurrowObj.LE_TV_CHANNEL, null, commonParam);
        } else if (data instanceof Browser) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_BORROW, null, commonParam);
        } else if (data instanceof SubjectPreLive) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_PRELIVE, null, commonParam);
        } else if (data instanceof Subject) {
            if (isChildData(data)) {
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_CHILD_SUBJECT, null, commonParam);
            } else {
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_SUBJECT, null, commonParam);
            }
        } else if (data instanceof BaseBlock) {
            BaseBlock block = (BaseBlock) data;
            if (!StringUtil.isBlank(block.getAppCode())) {
                if (TerminalConstant.THIRD_PARTY_SHOP.equals(block.getAppCode())) {
                    BurrowUtil.buildBurrowV2(data, BurrowObj.LE_SHOP_COMMON, block.getAppParam(), commonParam);
                } else if (TerminalConstant.THIRD_PARTY_GOLIVE.equals(block.getAppCode())) {
                    BurrowUtil.buildBurrowV2(data, BurrowObj.LE_GOLIVE_COMMON, block.getAppParam(), commonParam);
                } else if (TerminalConstant.THIRD_PARTY_LESTORE_SUBJECT.equals(block.getAppCode())) {
                    BurrowUtil.buildBurrowV2(data, BurrowObj.LE_APPSTORE_COMMON, block.getAppParam(), commonParam);
                }
            } else if (block.getDataType() == DataConstant.DATA_TYPE_PLAY_CENTER) {
                GameCenterBurrowDto gameSdkDto = new GameCenterBurrowDto();
                gameSdkDto.setId(StringUtil.toLong(block.getId()));
                gameSdkDto.setResource(BurrowUtil.GameCenterGcResource.LETV);
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_GAME_START, gameSdkDto, commonParam);
            } else {
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_BASEBLOCK, null, commonParam);
            }
        } else if (data instanceof Container) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_CONTAINER, null, commonParam);
        } else if (data instanceof StaticBlock) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_STATICBLOCK, null, commonParam);
        } else if (data instanceof AlbumDto) {
            if (isChildData(data)) {
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_CHILD_ALBUM, null, commonParam);
            } else {
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_ALBUM, null, commonParam);
            }
        } else if (data instanceof VideoDto) {
            if (isChildData(data)) {
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_CHILD_VIDEO, null, commonParam);
            } else {
                BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_VIDEO, null, commonParam);
            }
        } else if (data instanceof Live) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_LIVE, null, commonParam);
        } else if (data instanceof Page) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_PAGE, null, commonParam);
        } else if (data instanceof StarCms) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.LE_TV_STAR, null, commonParam);
        } else if (data instanceof VideoSaleDto) {
            // TODO 逻辑迁移到业务层
        }
        return data;
    }

    /**
     * 判断专辑、视频、专题是否属于乐视儿童内容
     * @param obj
     * @return
     *         当专辑categroy为1021时 一级分类为教育,当subCategory包含541021时 二级分类为幼儿
     *         教育频道-幼儿表明是乐视儿童数据，mainType字段负值为1，跳乐视儿童,同时儿童没有专辑详情
     *         但是频道的专辑AlbumDto、视频VideoDto返回的二级分类是个map，
     *         而搜索返回的是个String所以做了兼容处理
     */
    private boolean isChildData(BaseData obj) {
        if (obj != null) {
            if (obj instanceof AlbumDto) {
                AlbumDto album = (AlbumDto) obj;
                if (album.getCategoryId() != null
                        && album.getCategoryId() == VideoConstants.Category.TEACH
                        && ((album.getSubCategory() != null && album.getSubCategory().keySet()
                                .contains(String.valueOf(VideoConstants.Category.TEACH_CHILD))) || album
                                .getSubCategoryId() != null
                                && album.getSubCategoryId().contains(
                                        String.valueOf(VideoConstants.Category.TEACH_CHILD)))) {
                    return true;
                }
            } else if (obj instanceof VideoDto) {
                VideoDto video = (VideoDto) obj;
                if (video.getCategoryId() != null
                        && video.getCategoryId() == VideoConstants.Category.TEACH
                        && ((video.getSubCategory() != null && video.getSubCategory().keySet()
                                .contains(String.valueOf(VideoConstants.Category.TEACH_CHILD))) || video
                                .getSubCategoryId() != null
                                && video.getSubCategoryId().contains(
                                        String.valueOf(VideoConstants.Category.TEACH_CHILD)))) {
                    return true;
                }
            } else if (obj instanceof Subject) {
                Subject subject = (Subject) obj;
                if (subject.getCategoryId() != null && subject.getCategoryId() == VideoConstants.Category.TEACH
                        && subject.getSubCategoryId() != null
                        && subject.getSubCategoryId().contains(String.valueOf(VideoConstants.Category.TEACH_CHILD))) {
                    return true;
                }
            } else if (obj instanceof BaseBlock) {
                BaseBlock block = (BaseBlock) obj;
                if (StringUtil.isNotBlank(block.getExtendText())) {
                    String[] ExtendText = block.getExtendText().split("\\|");
                    if (ExtendText.length > 0) {
                        String type = ExtendText[0];
                        if (StringUtils.isNumeric(type)
                                && DataConstant.DATA_TYPE_CHILDREN == StringUtil.toInteger(type)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
