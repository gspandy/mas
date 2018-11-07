package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.WasuDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.CategoryConstants;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.LiveDto;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;

/**
 * 万象桌面打洞规则
 * @author chenjian
 */
public class PanoDesktopBurrow extends AbstractBurrow {

    public PanoDesktopBurrow(BurrowUtil burrowUtil) {
        super(burrowUtil);
    }

    @Override
    public BaseData burrowObj(BaseData data, CommonParam commonParam) {
        if (data != null) {
            /*// 万象桌面 专辑
            if (data instanceof AlbumCardDto) {
                AlbumCardDto albumCardDto = (AlbumCardDto) data;

                // CIBN的APP
                if (CommonConstants.CIBN_NAME.equals(albumCardDto.getSubSrc())) {
                    CibnAlbumBurrowDto cibnAlbumBurrowDto = new CibnAlbumBurrowDto();
                    cibnAlbumBurrowDto.setAlbumId(albumCardDto.getExternalId());
                    return this.burrowUtil.burrowCIBNAlbum(data, cibnAlbumBurrowDto, commonParam);
                } else if (CommonConstants.WASU_NEW_NAME.equals(albumCardDto.getSubSrc())) {
                    // 华数数据
                    WasuDto wasuDto = new WasuDto();
                    try {
                        JSONArray jSONArray = albumCardDto.getVideoList().get(0).getJSONObject("external_play_id")
                                .getJSONArray("param");
                        JSONObject jSONObject = jSONArray.getJSONObject(0);
                        wasuDto.setId(jSONObject.get("assetId").toString());
                        wasuDto.setCategoryid(jSONObject.get("catId").toString());
                    } catch (Exception e) {
                    }
                    return this.burrowUtil.burrowWasuAlbum(data, wasuDto, commonParam);
                }
                int tvCopyRight = 0;
                if (!StringUtil.isBlank(albumCardDto.getPushFlag())
                        && albumCardDto.getPushFlag().contains(CommonConstants.TV_PLAY_PLAT_FROM)) {
                    tvCopyRight = 1;
                }
                TVAlbumBurrowDto tVAlbumBurrowDto = new TVAlbumBurrowDto();
                tVAlbumBurrowDto.setResource(BurrowTVResource.PANO_DESKTOP);
                tVAlbumBurrowDto.setAlbumId(albumCardDto.getAid());
                tVAlbumBurrowDto.setSrc(StringUtil.toInteger(albumCardDto.getSrc()));
                tVAlbumBurrowDto.setTvCopyright(tvCopyRight);
                // 跳TV版
                return this.burrowUtil.burrowTVAlbum(albumCardDto, tVAlbumBurrowDto, commonParam);

                *//*
                 * PanoAlbumBurrowDto panoAlbumBurrowDto = new
                 * PanoAlbumBurrowDto();
                 * panoAlbumBurrowDto.setAid(albumCardDto.getAid());
                 * panoAlbumBurrowDto.setResource(BurrowPanoResource.PANO_DESKTOP
                 * );
                 * panoAlbumBurrowDto.setSrc(albumCardDto.getSrc());
                 * panoAlbumBurrowDto.setPushflag(albumCardDto.getPushFlag());
                 * panoAlbumBurrowDto.setCategoryid(albumCardDto.getCategoryId())
                 * ;
                 * return this.burrowUtil.burrowPanoAlbum(data,
                 * panoAlbumBurrowDto, commonParam);
                 *//*
            } else if (data instanceof AppCardDto) {
                // 万象桌面 应用中心
                AppCardDto appCardDto = (AppCardDto) data;
                if (appCardDto != null) {
                    // 应用商店
                    if (CategoryConstants.APPSTORE == StringUtil.toInteger(appCardDto.getCategoryId())) {
                        AppStoreBurrowDto appStoreBurrowDto = new AppStoreBurrowDto();
                        appStoreBurrowDto.setFrom(BurrowPackageName.PANO_DESKTOP);
                        appStoreBurrowDto.setPkg(appCardDto.getPackageName());
                        return this.burrowUtil.burrowAppStore(appCardDto, appStoreBurrowDto, commonParam);
                    } else if (CategoryConstants.GAMECENTER == StringUtil.toInteger(appCardDto.getCategoryId())) {
                        // 游戏中心
                        GameCenterBurrowDto gameCenterBurrowDto = new GameCenterBurrowDto();
                        gameCenterBurrowDto.setGc_resource(GameCenterGcResource.PANO);
                        gameCenterBurrowDto.setId(StringUtil.toLong(appCardDto.getTvAppId()));
                        gameCenterBurrowDto.setAppPackageName(appCardDto.getPackageName());
                        return this.burrowUtil.burrowGameCenter(appCardDto, gameCenterBurrowDto, commonParam);
                    }
                }
            } else if (data instanceof MusicSingleCardDto) {
                // 万象桌面 音乐单曲
                MusicSingleCardDto musicSingleCardDto = (MusicSingleCardDto) data;
                MusicSingleBurrowDto musicSingleBurrowDto = new MusicSingleBurrowDto();
                musicSingleBurrowDto.setArtist(musicSingleCardDto.getSingerName());
                musicSingleBurrowDto.setSong(musicSingleCardDto.getName());
                musicSingleBurrowDto.setSongId(StringUtil.toLong(musicSingleCardDto.getXiamiId()));
                return this.burrowUtil.burrowMusicSingle(musicSingleCardDto, musicSingleBurrowDto, commonParam);
            } else if (data instanceof LiveDto) {
                LiveDto liveDto = (LiveDto) data;
                long currentTime = System.currentTimeMillis();
                // 直播中
                if (currentTime >= liveDto.getStartTime() && currentTime <= liveDto.getEndTime()) {
                    TVLive tVLive = new TVLive();
                    tVLive.setLiveId(liveDto.getLiveId());
                    return this.burrowUtil.burrowTVLive(data, tVLive, commonParam);
                } else if (currentTime > liveDto.getEndTime()) {
                    // 回看
                    if (!StringUtil.isBlank(liveDto.getRecordId())) {
                        TVVideoBurrowDto tVVideoBurrowDto = new TVVideoBurrowDto();
                        // tVVideoBurrowDto.setAlbumId(searchVideoDto.getAlbumId());
                        tVVideoBurrowDto.setResource(BurrowTVResource.PANO_SEARCH);
                        tVVideoBurrowDto.setVideoId(liveDto.getRecordId());
                        return this.burrowUtil.burrowTVVideo(data, tVVideoBurrowDto, commonParam);
                    } else {
                        TVLive tVLive = new TVLive();
                        tVLive.setLiveId(liveDto.getLiveId());
                        return this.burrowUtil.burrowTVLive(data, tVLive, commonParam);
                    }
                }
            }
*/
        }
        return data;
    }
}
