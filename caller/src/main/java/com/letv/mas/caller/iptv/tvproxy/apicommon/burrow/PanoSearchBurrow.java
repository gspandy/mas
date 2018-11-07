package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.CategoryConstants;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.springframework.util.CollectionUtils;

/**
 * 万象搜索打洞规则
 * @author chenjian
 */
public class PanoSearchBurrow extends AbstractBurrow {

    private PanoDesktopBurrow panoDesktopBurrow;

    public PanoSearchBurrow(BurrowUtil burrowUtil, PanoDesktopBurrow panoDesktopBurrow) {
        super(burrowUtil);
        this.panoDesktopBurrow = panoDesktopBurrow;
    }

    @Override
    public BaseData burrowObj(BaseData data, CommonParam commonParam) {
//        if (data != null) {
//            // CIBN APP打洞单独一套，其他应用打洞到万象用万象本身打洞规则
//            if (CommonConstants.APP_CIBN.equals(commonParam.getDisplayAppId())) {
//                return this.burrowObjForCibn(data, commonParam);
//            } else {
//                return this.burrowObjForPano(data, commonParam);
//            }
//        }
        return data;
    }

    /**
     * 万象APP 原生打洞规则
     * @param data
     * @param commonParam
     * @return
     */
//    private BaseData burrowObjForPano(BaseData data, CommonParam commonParam) {
//        if (data instanceof SearchAlbumDto) {
//            SearchAlbumDto searchAlbumDto = (SearchAlbumDto) data;
//            /*
//             * // CIBN跳乐搜
//             * if (CommonConstants.CIBN_NAME.equals(searchAlbumDto.getSubSrc())
//             * ||
//             * CommonConstants.WASU_NEW_NAME.equals(searchAlbumDto.getSubSrc()))
//             * {
//             * PanoAlbumBurrowDto panoAlbumBurrowDto = new PanoAlbumBurrowDto();
//             * panoAlbumBurrowDto.setAid(searchAlbumDto.getAlbumId());
//             * panoAlbumBurrowDto.setResource(BurrowPanoResource.PANO_SEARCH);
//             * return this.burrowUtil.burrowPanoAlbum(searchAlbumDto,
//             * panoAlbumBurrowDto, commonParam);
//             * }
//             * int tvCopyRight = 0;
//             * if (!StringUtil.isBlank(searchAlbumDto.getPushFlag())
//             * && searchAlbumDto.getPushFlag().contains(CommonConstants.
//             * TV_PLAY_PLAT_FROM)) {
//             * tvCopyRight = 1;
//             * }
//             * TVAlbumBurrowDto tVAlbumBurrowDto = new TVAlbumBurrowDto();
//             * tVAlbumBurrowDto.setResource(BurrowTVResource.PANO_SEARCH);
//             * tVAlbumBurrowDto.setAlbumId(searchAlbumDto.getAlbumId());
//             * tVAlbumBurrowDto.setSrc(StringUtil.toInteger(searchAlbumDto.getSrc
//             * ()));
//             * tVAlbumBurrowDto.setTvCopyright(tvCopyRight);
//             * // 跳儿童
//             * if (CategoryConstants.EDUCATION == searchAlbumDto.getCategoryId()
//             * && !StringUtil.isBlank(searchAlbumDto.getSubCategoryId())
//             * && searchAlbumDto.getSubCategoryId().contains("542015")) {
//             * return this.burrowUtil.burrowChildAlbum(searchAlbumDto,
//             * tVAlbumBurrowDto, commonParam);
//             * }
//             * // 跳TV版
//             * return this.burrowUtil.burrowTVAlbum(searchAlbumDto,
//             * tVAlbumBurrowDto, commonParam);
//             */
//            PanoAlbumBurrowDto panoAlbumBurrowDto = new PanoAlbumBurrowDto();
//            panoAlbumBurrowDto.setAid(searchAlbumDto.getAlbumId());
//            panoAlbumBurrowDto.setResource(BurrowPanoResource.PANO_SEARCH);
//            panoAlbumBurrowDto.setSrc(searchAlbumDto.getSrc());
//            panoAlbumBurrowDto.setPushflag(searchAlbumDto.getPushFlag());
//            panoAlbumBurrowDto.setCategoryid(StringUtil.toString(searchAlbumDto.getCategoryId()));
//            return this.burrowUtil.burrowPanoAlbum(data, panoAlbumBurrowDto, commonParam);
//        } else if (data instanceof SearchVideoDto) {
//            SearchVideoDto searchVideoDto = (SearchVideoDto) data;
//            // 跳体育
//            if (CategoryConstants.SPORT == searchVideoDto.getCategoryId()) {
//                SportsVideoBurrowDto sportsVideoBurrowDto = new SportsVideoBurrowDto();
//                sportsVideoBurrowDto.setFrom(BurrowPackageName.PANO_SEARCH);
//                sportsVideoBurrowDto.setVid(StringUtil.toLong(searchVideoDto.getVideoId()));
//                sportsVideoBurrowDto.setVideoName(searchVideoDto.getName());
//                return this.burrowUtil.burrowSportsVideo(data, sportsVideoBurrowDto, commonParam);
//            }
//
//            TVVideoBurrowDto tVVideoBurrowDto = new TVVideoBurrowDto();
//            tVVideoBurrowDto.setAlbumId(searchVideoDto.getAlbumId());
//            tVVideoBurrowDto.setResource(BurrowTVResource.PANO_SEARCH);
//            tVVideoBurrowDto.setVideoId(searchVideoDto.getVideoId());
//            // 跳儿童
//            if (CategoryConstants.EDUCATION == searchVideoDto.getCategoryId()
//                    && !StringUtil.isBlank(searchVideoDto.getSubCategoryId())
//                    && searchVideoDto.getSubCategoryId().contains("542015")) {
//                tVVideoBurrowDto.setModel(1);
//                return this.burrowUtil.burrowChildVideo(searchVideoDto, tVVideoBurrowDto, commonParam);
//            }
//            // 跳TV版
//            return this.burrowUtil.burrowTVVideo(data, tVVideoBurrowDto, commonParam);
//        } else if (data instanceof SearchSubjectDto) {
//            SearchSubjectDto searchSubjectDto = (SearchSubjectDto) data;
//            // 跳体育
//            if (CategoryConstants.SPORT == searchSubjectDto.getCategoryId()) {
//                SportsSubjectBurrowDto sportsSubjectBurrowDto = new SportsSubjectBurrowDto();
//                sportsSubjectBurrowDto.setFrom(BurrowPackageName.PANO_SEARCH);
//                sportsSubjectBurrowDto.setTopicId(searchSubjectDto.getSubjectId());
//                return this.burrowUtil.burrowSportsSubject(data, sportsSubjectBurrowDto, commonParam);
//            }
//
//            TVSubjectBurrowDto tVSubjectBurrowDto = new TVSubjectBurrowDto();
//            tVSubjectBurrowDto.setResource(BurrowTVResource.PANO_SEARCH);
//            tVSubjectBurrowDto.setSubjectId(searchSubjectDto.getSubjectId());
//            tVSubjectBurrowDto.setSubjectType(searchSubjectDto.getSubjectType());
//            // 跳儿童
//            if (CategoryConstants.EDUCATION == searchSubjectDto.getCategoryId()
//                    && !StringUtil.isBlank(searchSubjectDto.getSubCategoryId())
//                    && searchSubjectDto.getSubCategoryId().contains("542015")) {
//                return this.burrowUtil.burrowChildSubject(data, tVSubjectBurrowDto, commonParam);
//            }
//            // 跳TV版
//            return this.burrowUtil.burrowTVSubject(data, tVSubjectBurrowDto, commonParam);
//        } else if (data instanceof SearchStarDto) {
//            // 明星
//            SearchStarDto searchStarDto = (SearchStarDto) data;
//            PanoStarBurrowDto panoStarBurrowDto = new PanoStarBurrowDto();
//            panoStarBurrowDto.setResource(BurrowPanoResource.PANO_SEARCH);
//            panoStarBurrowDto.setStarId(searchStarDto.getStarId());
//            return this.burrowUtil.burrowPanoStar(data, panoStarBurrowDto, commonParam);
//        } else if (data instanceof SearchAppDto) {
//            SearchAppDto searchAppDto = (SearchAppDto) data;
//            if (searchAppDto != null) {
//                // 应用商店
//                if (CategoryConstants.APPSTORE == StringUtil.toInteger(searchAppDto.getCategoryId())) {
//                    AppStoreBurrowDto appStoreBurrowDto = new AppStoreBurrowDto();
//                    appStoreBurrowDto.setFrom(BurrowPackageName.PANO_SEARCH);
//                    appStoreBurrowDto.setPkg(searchAppDto.getPackageName());
//                    return this.burrowUtil.burrowAppStore(searchAppDto, appStoreBurrowDto, commonParam);
//                } else if (CategoryConstants.GAMECENTER == StringUtil.toInteger(searchAppDto.getCategoryId())) {
//                    // 游戏中心
//                    GameCenterBurrowDto gameCenterBurrowDto = new GameCenterBurrowDto();
//                    gameCenterBurrowDto.setGc_resource(GameCenterGcResource.PANO);
//                    gameCenterBurrowDto.setId(searchAppDto.getTvAppId());
//                    gameCenterBurrowDto.setAppPackageName(searchAppDto.getPackageName());
//                    return this.burrowUtil.burrowGameCenter(searchAppDto, gameCenterBurrowDto, commonParam);
//                }
//            }
//
//        } else if (data instanceof SearchLiveSportDto) {
//            SearchLiveSportDto searchLiveSportDto = (SearchLiveSportDto) data;
//            if (!CollectionUtils.isEmpty(searchLiveSportDto.getSportProgramDto())) {
//                long currentTime = System.currentTimeMillis() / 1000;
//                SportProgramDto sportProgramDto = searchLiveSportDto.getSportProgramDto().get(0);
//                // 正在直播跳TVLIVE
//                if (currentTime >= sportProgramDto.getBeginTimeStamp()
//                        && currentTime <= sportProgramDto.getEndTimeStamp()) {
//                    TVLiveBurrowDto tVLiveBurrowDto = new TVLiveBurrowDto();
//                    tVLiveBurrowDto.setChannelId(sportProgramDto.getId());
//                    return this.burrowUtil.burrowLive(data, tVLiveBurrowDto, commonParam);
//                } else if (currentTime > sportProgramDto.getEndTimeStamp()) { // 回看
//
//                    if (!StringUtil.isBlank(sportProgramDto.getRecordingId())) {
//                        // 转码成功跳体育APP
//                        SportsVideoBurrowDto sportsVideoBurrowDto = new SportsVideoBurrowDto();
//                        sportsVideoBurrowDto.setFrom(BurrowPackageName.PANO_SEARCH);
//                        sportsVideoBurrowDto.setVid(StringUtil.toLong(sportProgramDto.getRecordingId()));
//                        sportsVideoBurrowDto.setVideoName(sportProgramDto.getTitle());
//                        return this.burrowUtil.burrowSportsVideo(data, sportsVideoBurrowDto, commonParam);
//                    } else {
//                        // 转码未结束跳TVLIVE
//                        TVLiveBurrowDto tVLiveBurrowDto = new TVLiveBurrowDto();
//                        tVLiveBurrowDto.setChannelId(sportProgramDto.getId());
//                        return this.burrowUtil.burrowLive(data, tVLiveBurrowDto, commonParam);
//                    }
//                }
//                // 预约状态客户端弹浮层，此处不用处理
//            }
//
//            return data;
//        } else if (data instanceof SearchLiveMusicDto) {
//            SearchLiveMusicDto searchLiveMusicDto = (SearchLiveMusicDto) data;
//            if (!CollectionUtils.isEmpty(searchLiveMusicDto.getMusicProgramDto())) {
//                long currentTime = System.currentTimeMillis() / 1000;
//                MusicProgramDto musicProgramDto = searchLiveMusicDto.getMusicProgramDto().get(0);
//                // 正在直播跳TVLIVE
//                if (currentTime >= musicProgramDto.getBeginTimeStamp()
//                        && currentTime <= musicProgramDto.getEndTimeStamp()) {
//                    TVLiveBurrowDto tVLiveBurrowDto = new TVLiveBurrowDto();
//                    tVLiveBurrowDto.setChannelId(musicProgramDto.getId());
//                    return this.burrowUtil.burrowLive(data, tVLiveBurrowDto, commonParam);
//                } else if (currentTime > musicProgramDto.getEndTimeStamp()) {// 回看
//                    // 转码成功跳超级影视
//                    if (!StringUtil.isBlank(musicProgramDto.getRecordingId())) {
//                        TVAlbumBurrowDto tVAlbumBurrowDto = new TVAlbumBurrowDto();
//                        tVAlbumBurrowDto.setResource(BurrowTVResource.PANO_SEARCH);
//                        tVAlbumBurrowDto.setAlbumId(musicProgramDto.getRecordingId());
//                        tVAlbumBurrowDto.setSrc(1);// 直播肯定为内网数据
//                        tVAlbumBurrowDto.setTvCopyright(1);
//                        return this.burrowUtil.burrowTVAlbum(data, tVAlbumBurrowDto, commonParam);
//                    } else {
//                        // 转码未结束跳TVLIVE
//                        TVLiveBurrowDto tVLiveBurrowDto = new TVLiveBurrowDto();
//                        tVLiveBurrowDto.setChannelId(musicProgramDto.getId());
//                        return this.burrowUtil.burrowLive(data, tVLiveBurrowDto, commonParam);
//
//                    }
//                }
//                // 预约状态客户端弹浮层，此处不用处理
//            }
//
//            return data;
//        } else if (data instanceof SearchLiveCarouselDto) {
//            // 轮播、卫视台
//            SearchLiveCarouselDto searchLiveCarouselDto = (SearchLiveCarouselDto) data;
//            TVLiveBurrowDto tVLiveBurrowDto = new TVLiveBurrowDto();
//            tVLiveBurrowDto.setChannelId(searchLiveCarouselDto.getId());
//
//            if (searchLiveCarouselDto.liveType == SearchLiveCarouselDto.lunbo) {
//                return this.burrowUtil.burrowLiveLunbo(data, tVLiveBurrowDto, commonParam);
//            } else if (searchLiveCarouselDto.liveType == SearchLiveCarouselDto.weishi) {
//                return this.burrowUtil.burrowLiveWeishi(data, tVLiveBurrowDto, commonParam);
//            }
//        } else if (data instanceof SearchMusicAlbumDto) {
//            // 音乐专辑
//            SearchMusicAlbumDto searchMusicAlbumDto = (SearchMusicAlbumDto) data;
//            MusicAlbumBurrowDto musicAlbumBurrowDto = new MusicAlbumBurrowDto();
//            musicAlbumBurrowDto.setAlbumId(searchMusicAlbumDto.getAlbumId());
//            return this.burrowUtil.burrowMusicAlbum(data, musicAlbumBurrowDto, commonParam);
//        } else if (data instanceof SearchMusicSingleDto) {
//            // 音乐单曲
//            SearchMusicSingleDto searchMusicSingleDto = (SearchMusicSingleDto) data;
//            MusicSingleBurrowDto musicSingleBurrowDto = new MusicSingleBurrowDto();
//            musicSingleBurrowDto.setArtist(searchMusicSingleDto.getSingers());
//            musicSingleBurrowDto.setSong(searchMusicSingleDto.getName());
//            musicSingleBurrowDto.setSongId(StringUtil.toLong(searchMusicSingleDto.getXiamiId()));
//            return this.burrowUtil.burrowMusicSingle(searchMusicSingleDto, musicSingleBurrowDto, commonParam);
//        } else {
//            // 没有的话尝试桌面的数据结构
//            return this.panoDesktopBurrow.burrowObj(data, commonParam);
//        }
//        return data;
//
//    }
//
//    /**
//     * 万象插件，为CIBN应用提供的打洞规则
//     * @param data
//     * @param commonParam
//     * @return
//     */
//    private BaseData burrowObjForCibn(BaseData data, CommonParam commonParam) {
//        if (data instanceof SearchAlbumDto) {
//            SearchAlbumDto searchAlbumDto = (SearchAlbumDto) data;
//            // CIBN数据跳CIBN APP
//            if (CommonConstants.CIBN_NAME.equals(searchAlbumDto.getSubSrc())) {
//                CibnAlbumBurrowDto cibnAlbumBurrowDto = new CibnAlbumBurrowDto();
//                cibnAlbumBurrowDto.setAlbumId(searchAlbumDto.getExternalId());
//                return this.burrowUtil.burrowCIBNAlbum(data, cibnAlbumBurrowDto, commonParam);
//            }
//        }
//        return data;
//    }
}
