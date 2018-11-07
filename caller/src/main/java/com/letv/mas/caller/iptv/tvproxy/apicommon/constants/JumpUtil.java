package com.letv.mas.caller.iptv.tvproxy.apicommon.constants;

import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.BurrowUtil;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.bean.bo.Subject;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.bean.bo.SubjectPreLive;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.dto.AlbumInfoDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.dto.ResourceInfo;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.dto.ResourceInfoDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.dto.SearchResultDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.*;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.JumpData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letv.mas.caller.iptv.tvproxy.channel.constants.ChannelConstants;
import com.letv.mas.caller.iptv.tvproxy.channel.model.dto.*;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LocaleConstant;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.bean.ChargeInfoDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.ChargeInfo;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import com.letv.mas.caller.iptv.tvproxy.live.model.bean.bo.Live;
import com.letv.mas.caller.iptv.tvproxy.recommendation.model.dto.RecomendationVideoByIdDto;
import com.letv.mas.caller.iptv.tvproxy.recommendation.model.dto.RecommendationMaybeLikeDto;
import com.letv.mas.caller.iptv.tvproxy.terminal.constant.TerminalConstant;
import com.letv.mas.caller.iptv.tvproxy.video.constants.ChargeTypeConstants;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.AlbumDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.Stream;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoDto;
import com.letv.mas.caller.iptv.tvproxy.video.util.MobileUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.letv.mas.caller.iptv.tvproxy.recommendation.model.dto.RecommendationRelationDto.RelationResource;
import java.util.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public class JumpUtil {
    private final static Logger log = LoggerFactory.getLogger(JumpUtil.class);
    private final static Map<String, ArrayList<String>> unsupportedJumps = new HashMap<String, ArrayList<String>>();

    static {
        /**
         * 首页：10006294 公主日记2
         * 会员首屏：10009800 萌娃72变
         * 会员二屏：10006294 公主日记2
         * 电视剧频道推荐TAB：10001462 产科医生
         */
        // unsupportedJumps.put("AlbumDto", new
        // ArrayList<String>(Arrays.asList("10009800", "10006294",
        // "10001462")));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static BaseData bulidJumpObj(BaseData obj, Integer dataType, String defaultStream, String defaultStreamName) {
        JumpData jump = new JumpData();
        if (obj instanceof Channel) {
            Channel channel = (Channel) obj;
            Channel channel_skip = new Channel();
            channel_skip.setChannelId(channel.getChannelId());
            channel_skip.setPageId(channel.getPageId());
            channel_skip.setDataUrl(channel.getDataUrl());
            channel_skip.setDataType(dataType);
            channel_skip.setCpCategoryId(channel.getCpCategoryId());
            channel_skip.setCpId(channel.getCpId());
            channel_skip.setGlobalId(channel.getGlobalId());
            jump.setType(dataType);
            jump.setValue(channel_skip);
            channel.setJump(jump);
        } else if (obj instanceof ChannelData) {
            ChannelData channelData = (ChannelData) obj;
            ChannelData channelData_skip = new ChannelData();
            channelData_skip.setCpId(channelData.getCpId());
            channelData_skip.setCpCategoryId(channelData.getCpCategoryId());
            channelData_skip.setGlobalId(channelData.getGlobalId());
            channelData_skip.setChannelId(channelData.getChannelId());
            channelData_skip.setTitleSearchCondition(channelData.getTitleSearchCondition());
            jump.setType(dataType);
            jump.setValue(channelData_skip);
            channelData.setJump(jump);
        } else if (obj instanceof Browser) {
            Browser browser = (Browser) obj;
            Browser browser_skip = new Browser();
            jump.setType(dataType);
            browser_skip.setBrowserType(browser.getBrowserType());
            browser_skip.setOpenType(browser.getOpenType());
            browser_skip.setUrl(browser.getUrl());
            browser_skip.setUrlMap(browser.getUrlMap());
            browser_skip.setSrc(browser.getSrc());
            browser_skip.setWebsite(browser.getWebsite());
            browser_skip.setAlbumId(browser.getAlbumId());
            browser_skip.setName(browser.getName());
            browser_skip.setDataType(dataType);
            jump.setValue(browser_skip);
            browser.setJump(jump);
        } else if (obj instanceof SubjectPreLive) {
            SubjectPreLive preSubject = (SubjectPreLive) obj;
            SubjectPreLive preSubject_skip = new SubjectPreLive();
            jump.setType(DataConstant.DATA_TYPE_PRELIVE);
            preSubject_skip.setCinemaId(preSubject.getCinemaId());
            preSubject_skip.setDataType(DataConstant.DATA_TYPE_PRELIVE);
            jump.setValue(preSubject_skip);
            preSubject.setJump(jump);
        } else if (obj instanceof Subject) {
            Subject subject = (Subject) obj;
            Subject subject_skip = new Subject();
            subject_skip.setSubjectId(subject.getSubjectId());
            subject_skip.setSubjectType(subject.getSubjectType());
            subject_skip.setCategoryId(subject.getCategoryId());
            subject_skip.setSubCategoryId(subject.getSubCategoryId());
            subject_skip.setId(subject.getSubjectId());
            subject_skip.setType(subject.getSubjectType());
            jump.setValue(subject_skip);
            jump.setType(dataType);
            subject.setJump(JumpModel(subject_skip, jump, dataType));
        } else if (obj instanceof BaseBlock) {
            BaseBlock block = (BaseBlock) obj;
            BaseBlock block_skip = new BaseBlock();
            jump.setType(dataType);
            block_skip.setBlockId(block.getBlockId());
            block_skip.setLabelIdToPlay(block.getLabelIdToPlay());
            block_skip.setDataType(dataType);
            block_skip.setType(block.getType());
            block_skip.setId(block.getId());
            block_skip.setContainerId(block.getContainerId());
            block_skip.setChannelId(block.getChannelId());
            block_skip.setExtendText(block.getExtendText());
            block_skip.setDefaultStream(defaultStream);
            block_skip.setDefaultStreamName(defaultStreamName);
            block_skip.setPackageId(block.getPackageId());
            jump.setValue(block_skip);
            block.setDataType(dataType);
            block.setJump(JumpModel(block_skip, jump, dataType));
        } else if (obj instanceof Container) {// 后续删掉
            Container container = (Container) obj;
            Container container_skip = new Container();
            container_skip.setContainerId(container.getContainerId());
            container_skip.setDataType(dataType);
            jump.setType(dataType);
            jump.setValue(container_skip);
            container.setJump(jump);
        } else if (obj instanceof StaticBlock) {
            StaticBlock block = (StaticBlock) obj;
            StaticBlock staticBlock_skip = new StaticBlock();
            jump.setValue(staticBlock_skip);
            jump.setType(dataType);
            block.setJump(jump);
        } else if (obj instanceof AlbumDto) {
            AlbumDto album = (AlbumDto) obj;
            AlbumDto album_skip = new AlbumDto();
            album_skip.setTvCopyright(album.getTvCopyright());
            album_skip.setAlbumId(album.getAlbumId());
            album_skip.setCategoryId(album.getCategoryId());
            album_skip.setGlobalCid(album.getGlobalCid());
            album_skip.setSubCategoryId(getMapSets(album.getSubCategoryId(), album.getSubCategory()));
            album_skip.setSrc(album.getSrc());
            album_skip.setDataType(dataType);
            album_skip.setDefaultStream(defaultStream);
            album_skip.setDefaultStreamName(defaultStreamName);
            album_skip.setGlobalId(album.getGlobalId());
            album_skip.setId(album.getAlbumId());
            album_skip.setIfCharge(album.getIfCharge());
            // for tvod icon type
            // album_skip.setChargeType(album.getChargeType());
            jump.setType(dataType);
            // =======================未知类型联调(B)======================//
            if (unsupportedJumps.containsKey("AlbumDto") && null != unsupportedJumps.get("AlbumDto")
                    && unsupportedJumps.get("AlbumDto").size() > 0
                    && unsupportedJumps.get("AlbumDto").contains(album.getAlbumId())) {
                // dataType = new Integer(DataConstant.DATA_TYPE_UNKOWN);
                // album.setDataType(dataType);
                jump.setType(new Integer(DataConstant.DATA_TYPE_UNKOWN));
            }
            // =======================未知类型联调(E)======================//
            jump.setValue(album_skip);
            album.setIconType(getIconType(album.getSrc(), album.getIfCharge(), album.getChargeType(),
                    album.getStreams(), album.getCategoryId()));
            album.setStreams(null);
            album.setJump(JumpModel(album_skip, jump, dataType));
        } else if (obj instanceof VideoDto) {
            VideoDto video = (VideoDto) obj;
            VideoDto video_skip = new VideoDto();
            video_skip.setAlbumId(video.getAlbumId());
            video_skip.setVideoId(video.getVideoId());
            video_skip.setName(video.getName());
            video_skip.setSubName(video.getSubName());
            video_skip.setCategoryId(video.getCategoryId());
            video_skip.setGlobalCid(video.getGlobalCid());
            video_skip.setSubCategoryId(getMapSets(video.getSubCategoryId(), video.getSubCategory()));
            video_skip.setTvCopyright(video.getTvCopyright());
            video_skip.setWebPlayUrl(video.getWebPlayUrl());
            video_skip.setWebsite(video.getWebsite());
            video_skip.setExternal_id(video.getExternal_id());
            video_skip.setSource(video.getSource());
            video_skip.setIfCharge(video.getIfCharge());
            // for tvod icon type
            // video_skip.setChargeType(video.getChargeType());
            video_skip.setDataType(dataType);
            video_skip.setDefaultStream(defaultStream);
            video_skip.setDefaultStreamName(defaultStreamName);
            video_skip.setGlobalId(video.getGlobalId());
            video_skip.setId(video.getVideoId());
            jump.setType(dataType);
            jump.setValue(video_skip);
            video.setIconType(getIconType(video.getSrc(), video.getIfCharge(), video.getChargeType(),
                    video.getStreams(), video.getCategoryId()));
            video.setJump(JumpModel(video_skip, jump, dataType));
        } else if (obj instanceof Live) {
            Live live = (Live) obj;
            Live live_skip = new Live();
            live_skip.setLiveId(live.getLiveId());
            live_skip.setCategoryId(live.getCategoryId());
            live_skip.setDataType(dataType);
            live_skip.setIsFree(live.getIsFree());
            live_skip.setSourceType(live.getSourceType());
            jump.setType(dataType);
            jump.setValue(live_skip);
            live.setJump(jump);
        } else if (obj instanceof Page) {
            Page page = (Page) obj;
            Page page_skip = new Page();
            page_skip.setPageId(page.getPageId());
            page_skip.setDataType(dataType);
            page_skip.setChannelId(page.getChannelId());
            page_skip.setDataUrl(page.getDataUrl());
            jump.setType(dataType);
            jump.setValue(page_skip);
            page.setJump(jump);
        } else if (obj instanceof WFSubject) {
            WFSubject page = (WFSubject) obj;
            WFSubject page_skip = new WFSubject();
            page_skip.setPageId(page.getPageId());
            page_skip.setDataType(dataType);
            jump.setType(dataType);
            jump.setValue(page_skip);
            page.setJump(jump);
        } else if (obj instanceof StarCms) {
            StarCms star = (StarCms) obj;
            jump.setType(dataType);
            StarCms star_skip = new StarCms();
            star_skip.setStarId(star.getStarId());
            star_skip.setDataType(dataType);
            jump.setValue(star_skip);
            star.setJump(jump);
        } else if (obj instanceof VideoSaleDto) {
            VideoSaleDto videoSaleDto = (VideoSaleDto) obj;
            VideoSaleDto videoSale_skip = new VideoSaleDto();
            videoSale_skip.setDataList(videoSaleDto.getDataList());
            videoSale_skip.setButtonBuy(videoSaleDto.getButtonBuy());
            videoSale_skip.setButtonGift(videoSaleDto.getButtonGift());
            videoSale_skip.setBackground(videoSaleDto.getBackground());
            videoSale_skip.setTitleMap(videoSaleDto.getTitleMap());
            videoSale_skip.setImg(videoSaleDto.getImg());
            videoSale_skip.setUrl(videoSaleDto.getUrl());
            videoSale_skip.setDataType(dataType);
            jump.setValue(videoSale_skip);
            jump.setType(dataType);
            obj.setJump(jump);
        } else if (obj instanceof ResourceInfoDto) { // 检索视频
            ResourceInfoDto resourceInfoDto = (ResourceInfoDto) obj;
            // for tvod icon type search
            resourceInfoDto.setIconType(getIconType(null, resourceInfoDto.getIfCharge(),
                    resourceInfoDto.getChargeType(), null, resourceInfoDto.getCategoryId()));
            if (DataConstant.DATA_TYPE_ALBUM == dataType) {
                AlbumDto album_skip = new AlbumDto();
                album_skip.setTvCopyright(1);
                album_skip.setAlbumId(String.valueOf(resourceInfoDto.getIptvAlbumId()));
                album_skip.setCategoryId(resourceInfoDto.getCategoryId());
                album_skip.setSubCategoryId(resourceInfoDto.getSubcategoryId());
                album_skip.setSrc(1);
                album_skip.setDataType(dataType);
                album_skip.setIfCharge(resourceInfoDto.getIfCharge());
                // for tvod icon type search
                // album_skip.setChargeType(resourceInfoDto.getChargeType());
                album_skip.setDefaultStream(defaultStream);
                album_skip.setDefaultStreamName(defaultStreamName);
                jump.setValue(album_skip);
                jump.setType(dataType);
                jump = JumpModel(album_skip, jump, dataType);
            } else if (DataConstant.DATA_TYPE_VIDEO == dataType) {// TV版内部分类检索视频
                VideoDto video_skip = new VideoDto();
                video_skip.setAlbumId(String.valueOf(resourceInfoDto.getIptvAlbumId()));
                video_skip.setVideoId(String.valueOf(resourceInfoDto.getVid()));
                video_skip.setName(resourceInfoDto.getName());
                video_skip.setSubName(resourceInfoDto.getSubTitle());
                video_skip.setCategoryId(resourceInfoDto.getCategoryId());
                video_skip.setSubCategoryId(resourceInfoDto.getSubcategoryId());
                video_skip.setTvCopyright(1);
                video_skip.setWebPlayUrl("");
                video_skip.setWebsite("");
                video_skip.setDataType(dataType);
                video_skip.setIfCharge(resourceInfoDto.getIfCharge());
                // for tvod icon type search
                // video_skip.setChargeType(resourceInfoDto.getChargeType());
                video_skip.setDefaultStream(defaultStream);
                video_skip.setDefaultStreamName(defaultStreamName);
                jump.setValue(video_skip);
                jump.setType(dataType);
                jump = JumpModel(video_skip, jump, dataType);
            } else if (DataConstant.DATA_TYPE_SUBJECT == dataType) {
                Subject subject_skip = new Subject();
                jump = new JumpData<Subject, Extension>();
                subject_skip.setSubjectId(String.valueOf(resourceInfoDto.getId()));
                subject_skip.setSubjectType(Integer.valueOf(resourceInfoDto.getTemplateType()));
                subject_skip.setCategoryId(resourceInfoDto.getCategoryId());
                if (resourceInfoDto.getSubcategoryId() != null && !"0".equals(resourceInfoDto.getSubcategoryId())) {
                    subject_skip.setSubCategoryId(resourceInfoDto.getSubcategoryId());
                }
                subject_skip.setDataType(dataType);
                subject_skip.setIfCharge(resourceInfoDto.getIfCharge());
                jump.setValue(subject_skip);
                jump.setType(dataType);
                jump = JumpModel(subject_skip, jump, dataType);
            }
            obj.setJump(jump);
        } else if (obj instanceof ResourceInfo) {
            ResourceInfo resourceInfo = (ResourceInfo) obj;

            // for tvod icon type search
            resourceInfo.setIconType(getIconType(null, resourceInfo.getIfCharge(), resourceInfo.getChargeType(), null,
                    resourceInfo.getCategoryId()));

            if (DataConstant.DATA_TYPE_ALBUM == dataType) {
                AlbumDto album_skip = new AlbumDto();
                album_skip.setTvCopyright(1);
                album_skip.setAlbumId(String.valueOf(resourceInfo.getIptvAlbumId()));
                album_skip.setCategoryId(resourceInfo.getCategoryId());
                album_skip.setSubCategoryId(resourceInfo.getSubcategoryId());
                album_skip.setSrc(1);
                album_skip.setDataType(dataType);
                album_skip.setDefaultStream(defaultStream);
                album_skip.setDefaultStreamName(defaultStreamName);
                jump.setValue(album_skip);
                jump.setType(dataType);
            } else if (DataConstant.DATA_TYPE_VIDEO == dataType) {// TV版内部分类检索视频
                VideoDto video_skip = new VideoDto();
                video_skip.setAlbumId(String.valueOf(resourceInfo.getIptvAlbumId()));
                video_skip.setVideoId(String.valueOf(resourceInfo.getVid()));
                video_skip.setName(resourceInfo.getName());
                video_skip.setSubName(resourceInfo.getSubTitle());
                video_skip.setCategoryId(resourceInfo.getCategoryId());
                video_skip.setSubCategoryId(resourceInfo.getSubcategoryId());
                video_skip.setTvCopyright(1);
                video_skip.setWebPlayUrl("");
                video_skip.setWebsite("");
                video_skip.setDataType(dataType);
                video_skip.setDefaultStream(defaultStream);
                video_skip.setDefaultStreamName(defaultStreamName);
                jump.setValue(video_skip);
                jump.setType(dataType);
            } else if (DataConstant.DATA_TYPE_SUBJECT == dataType) {
                Subject subject_skip = new Subject();
                jump = new JumpData<Subject, Extension>();
                subject_skip.setSubjectId(String.valueOf(resourceInfo.getId()));
                subject_skip.setSubjectType(Integer.valueOf(resourceInfo.getTemplateType()));
                subject_skip.setCategoryId(resourceInfo.getCategoryId());
                if (resourceInfo.getSubcategoryId() != null && !"0".equals(resourceInfo.getSubcategoryId())) {
                    subject_skip.setSubCategoryId(resourceInfo.getSubcategoryId());
                }
                subject_skip.setDataType(dataType);
                jump.setValue(subject_skip);
                jump.setType(dataType);
            }
            obj.setJump(jump);
        } else if (obj instanceof AlbumInfoDto) {// 检索 专辑或专题
            AlbumInfoDto albumInfoDto = (AlbumInfoDto) obj;
            albumInfoDto.setIconType(getIconType(null, albumInfoDto.getIfCharge(), albumInfoDto.getChargeType(), null,
                    albumInfoDto.getCategoryId()));
            if (DataConstant.DATA_TYPE_ALBUM == dataType) {// 分类检索暂定有版权
                AlbumDto album_skip = new AlbumDto();
                album_skip.setTvCopyright(1);
                album_skip.setAlbumId(String.valueOf(albumInfoDto.getIptvAlbumId()));
                album_skip.setCategoryId(albumInfoDto.getCategoryId());
                album_skip.setSubCategoryId(albumInfoDto.getSubcategoryId());
                album_skip.setSrc(1);
                album_skip.setDataType(dataType);
                album_skip.setIfCharge(albumInfoDto.getIfCharge());
                // for tvod icon type search
                // album_skip.setChargeType(albumInfoDto.getChargeType());
                album_skip.setDefaultStream(defaultStream);
                album_skip.setDefaultStreamName(defaultStreamName);
                jump.setValue(album_skip);
                jump.setType(dataType);
            } else if (DataConstant.DATA_TYPE_VIDEO == dataType) {
                VideoDto video_skip = new VideoDto();
                video_skip.setAlbumId(String.valueOf(albumInfoDto.getIptvAlbumId()));
                video_skip.setVideoId(String.valueOf(albumInfoDto.getId()));
                video_skip.setName(albumInfoDto.getName());
                video_skip.setSubName(albumInfoDto.getSubTitle());
                video_skip.setCategoryId(albumInfoDto.getCategoryId());
                video_skip.setSubCategoryId(albumInfoDto.getSubcategoryId());
                video_skip.setTvCopyright(1);
                video_skip.setWebPlayUrl("");
                video_skip.setWebsite("");
                video_skip.setDataType(dataType);
                video_skip.setIfCharge(albumInfoDto.getIfCharge());
                // for tvod icon type search
                // video_skip.setChargeType(albumInfoDto.getChargeType());
                video_skip.setDefaultStream(defaultStream);
                video_skip.setDefaultStreamName(defaultStreamName);
                jump.setValue(video_skip);
                jump.setType(dataType);
            } else if (DataConstant.DATA_TYPE_SUBJECT == dataType) {
                Subject subject_skip = new Subject();
                jump = new JumpData<Subject, Extension>();
                subject_skip.setSubjectId(String.valueOf(albumInfoDto.getId()));
                if (albumInfoDto.getTemplateType() != null) {
                    subject_skip.setSubjectType(Integer.valueOf(albumInfoDto.getTemplateType()));// 注释显示TemplateType为新版的专题类型
                }
                subject_skip.setCategoryId(albumInfoDto.getCategoryId());
                if (albumInfoDto.getSubcategoryId() != null && !"0".equals(albumInfoDto.getSubcategoryId())) {
                    subject_skip.setSubCategoryId(albumInfoDto.getSubcategoryId());
                }
                subject_skip.setDataType(dataType);
                subject_skip.setIfCharge(albumInfoDto.getIfCharge());
                jump.setValue(subject_skip);
                jump.setType(dataType);
            } else if (DataConstant.DATA_TYPE_MUSIC == dataType) {
                BaseBlock block_skip = new BaseBlock();
                jump.setType(dataType);
                block_skip.setLabelIdToPlay(String.valueOf(albumInfoDto.getId()));
                block_skip.setDataType(dataType);
                jump.setValue(block_skip);
                jump.setType(dataType);
            }
            obj.setJump(jump);
        } else if (obj instanceof SearchResultDto) {//
            // SearchConstant几个数据类型1.专辑 2.视频 3.明星 4.专题 6.直播
            // SearchResultDto只包括1234没有6
            // 对应dataConstant数据类型1专辑2视频3专题4直播
            SearchResultDto searchResultDto = (SearchResultDto) obj;
            if (DataConstant.DATA_TYPE_ALBUM == dataType) {
                AlbumDto album_skip = new AlbumDto();
                album_skip.setAlbumId(String.valueOf(searchResultDto.getAid()));
                if (searchResultDto.getSrc() != null && !"1".equals(searchResultDto.getSrc())) {
                    album_skip.setTvCopyright(0);
                    album_skip.setSrc(Integer.valueOf(searchResultDto.getSrc()));
                } else {
                    album_skip.setTvCopyright(1);
                    album_skip.setSrc(1);
                }
                album_skip.setCategoryId(searchResultDto.getCategoryId());
                album_skip.setSubCategoryId(searchResultDto.getSubCategory());
                album_skip.setDataType(dataType);
                album_skip.setDefaultStream(defaultStream);
                album_skip.setDefaultStreamName(defaultStreamName);
                jump.setValue(album_skip);
                jump.setType(dataType);
            } else if (DataConstant.DATA_TYPE_VIDEO == dataType) {
                VideoDto video_skip = new VideoDto();
                video_skip.setAlbumId(String.valueOf(searchResultDto.getAid()));
                video_skip.setVideoId(String.valueOf(searchResultDto.getVid()));
                video_skip.setName(searchResultDto.getName());
                video_skip.setSubName(searchResultDto.getSubTitle());
                video_skip.setCategoryId(searchResultDto.getCategoryId());
                video_skip.setSubCategoryId(searchResultDto.getSubCategory());
                video_skip.setDefaultStream(defaultStream);
                video_skip.setDefaultStreamName(defaultStreamName);
                if (searchResultDto.getSrc() != null && !"1".equals(searchResultDto.getSrc())) {
                    video_skip.setTvCopyright(0);
                } else {
                    video_skip.setTvCopyright(1);
                }
                video_skip.setWebPlayUrl(searchResultDto.getUrl());
                video_skip.setWebsite(ApplicationConstants.WEBSITE_WWW_LETV_COM);
                video_skip.setDataType(dataType);
                jump.setValue(video_skip);
                jump.setType(dataType);
            } else if (SearchConstant.SEARCH_DATA_TYPE_SUBJECT == dataType) {
                dataType = DataConstant.DATA_TYPE_SUBJECT;
                Subject subject_skip = new Subject();
                jump = new JumpData<Subject, Extension>();
                subject_skip.setSubjectId(String.valueOf(searchResultDto.getAid()));
                if (searchResultDto.getSubjectType() != null) {
                    subject_skip.setSubjectType(Integer.valueOf(searchResultDto.getSubjectType()));
                }
                subject_skip.setCategoryId(searchResultDto.getCategoryId());
                if (searchResultDto.getSubCategory() != null && !"0".equals(searchResultDto.getSubCategory())) {
                    subject_skip.setSubCategoryId(searchResultDto.getSubCategory());
                }
                subject_skip.setDataType(dataType);
                jump.setValue(subject_skip);
                jump.setType(dataType);
            }
            obj.setJump(jump);
        } else if (obj instanceof RecommendationMaybeLikeDto) {
            RecommendationMaybeLikeDto mayBeLike = (RecommendationMaybeLikeDto) obj;
            if (dataType == DataConstant.DATA_TYPE_ALBUM) {
                AlbumDto album_skip = new AlbumDto();
                album_skip.setTvCopyright(1);// TODO 推荐的版权节点
                album_skip.setAlbumId(String.valueOf(mayBeLike.getAid()));
                album_skip.setSrc(Integer.valueOf(mayBeLike.getSrc()));
                album_skip.setDataType(dataType);
                // TODO --一、二级分类
                album_skip.setCategoryId(mayBeLike.getCategoryId());
                album_skip.setSubCategoryId(mayBeLike.getSubCategoryId());
                album_skip.setDefaultStream(defaultStream);
                album_skip.setDefaultStreamName(defaultStreamName);
                album_skip.setGlobalId(mayBeLike.getGlobalId());
                jump.setValue(album_skip);
                jump.setType(dataType);
            }
            obj.setJump(jump);
        } else if (obj instanceof RelationResource) {// 专辑相关内容推荐
            RelationResource relationResource = (RelationResource) obj;
            if (dataType == DataConstant.DATA_TYPE_ALBUM) {
                AlbumDto album_skip = new AlbumDto();
                album_skip.setTvCopyright(1);
                album_skip.setAlbumId(String.valueOf(relationResource.getAlbumId()));
                album_skip.setSrc(1);
                album_skip.setDataType(dataType);
                // TODO --一、二级分类
                album_skip.setCategoryId(relationResource.getCategoryId());
                album_skip.setSubCategoryId(relationResource.getSubCategoryId());
                album_skip.setDefaultStream(defaultStream);
                album_skip.setDefaultStreamName(defaultStreamName);
                jump.setValue(album_skip);
                jump.setType(dataType);
            }
            obj.setJump(jump);
        } else if (obj instanceof RecomendationVideoByIdDto) {
            RecomendationVideoByIdDto recomendationVideoByIdDto = (RecomendationVideoByIdDto) obj;
            if (dataType == DataConstant.DATA_TYPE_ALBUM) {
                AlbumDto album_skip = new AlbumDto();
                album_skip.setAlbumId(String.valueOf(recomendationVideoByIdDto.getId()));
                String src = recomendationVideoByIdDto.getSrc();
                if (src != null && "1".equals(src)) {
                    album_skip.setTvCopyright(1);
                    album_skip.setSrc(Integer.valueOf(src));
                } else {
                    album_skip.setTvCopyright(0);
                    album_skip.setSrc(Integer.valueOf(src));
                }
                album_skip.setDataType(dataType);
                album_skip.setDefaultStream(defaultStream);
                album_skip.setDefaultStreamName(defaultStreamName);
                // TODO --一、二级分类
                // album_skip.setCategoryId(recomendationVideoByIdDto.getCategoryId());
                // album_skip.setSubCategoryId(recomendationVideoByIdDto.getSubCategory());
                jump.setValue(album_skip);
            }
            jump.setType(dataType);
            obj.setJump(jump);
        }
        return obj;
    }

    /**
     * 无边界跳转规范:桌面请求需要包一层
     * @param data
     * @param dataType
     * @param defaultStream
     * @param defaultStreamName
     * @param commonParam
     * @return
     */
    public static BaseData bulidJumpObj(BaseData data, Integer dataType, String defaultStream,
            String defaultStreamName, CommonParam commonParam) {
        // 国广桌面走跳转新逻辑
        boolean burrow_switch = ApplicationUtils.getBoolean(ApplicationConstants.IPTV_BURROW_SWITCH_PARAM, false);
        if (commonParam != null
                && (TerminalCommonConstant.TERMINAL_APPLICATION_CIBN_LEVIEW.equalsIgnoreCase(commonParam
                        .getTerminalApplication()) || TerminalCommonConstant.TERMINAL_APPLICATION_WASU_LEVIEW
                        .equalsIgnoreCase(commonParam.getTerminalApplication()))) {
            return BurrowUtil.buildBurrow(data, commonParam);
        }
        // 新跳转逻辑添加开关
        if ((commonParam != null)
                && burrow_switch
                && ((TerminalCommonConstant.TERMINAL_APPLICATION_LEVIEW.equals(commonParam.getTerminalApplication())) || (TerminalCommonConstant.TERMINAL_APPLICATION_LECHILD_DESK
                        .equals(commonParam.getTerminalApplication())))) {
            return BurrowUtil.buildBurrow(data, commonParam);
        } else {
            return bulidJumpObjOld(data, dataType, defaultStream, defaultStreamName, commonParam);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static BaseData bulidJumpObjOld(BaseData data, Integer dataType, String defaultStream,
            String defaultStreamName, CommonParam commonParam) {
        data = bulidJumpObj(data, dataType, defaultStream, defaultStreamName);
        JumpData jump = new JumpData();
        jump.setType(DataConstant.DATA_TYPE_EXT_APP);
        Extension ext = new Extension();
        if ((commonParam != null)
                && ((TerminalCommonConstant.TERMINAL_APPLICATION_LEVIEW.equals(commonParam.getTerminalApplication()))
                        || (TerminalCommonConstant.TERMINAL_APPLICATION_LECHILD_DESK.equals(commonParam
                                .getTerminalApplication())) || (TerminalCommonConstant.TERMINAL_APPLICATION_CIBN_LEVIEW
                            .equalsIgnoreCase(commonParam.getTerminalApplication())))) {
            if (data instanceof Live) {
                Live live = (Live) data;
                if (String.valueOf(VideoConstants.Category.SPORT).equals(live.getCategoryId())) {// 体育直播
                    ext.setAction(VipMsgCodeConstant.ACTION_SPORT);
                    ext.setIsParse(1);
                    ext.setLaunchMode(1);
                    Map map = new HashMap();
                    // TODO 临时方案
                    String jsonVal = "{\"type\":5,\"source\":\"lesports\",\"value\":{\"liveId\":" + live.getLiveId()
                            + "}}";
                    map.put("burrowContent", jsonVal);
                    jump.setValue(map);
                } else {// 跳转tvlive
                    Map map = new HashMap();
                    // tvlive只能接收string类型
                    map.put("type", "1");
                    map.put("channelId", live.getLiveId());
                    ext.setAction("com.letv.switch_channel");
                    ext.setIsParse(1);
                    jump.setExtend(ext);
                    jump.setValue(map);
                }
                jump.setExtend(ext);
            } else if (data instanceof VideoDto) {
                VideoDto video = (VideoDto) data;
                if (video.getCategoryId() != null && VideoConstants.Category.SPORT == video.getCategoryId()) {// 单视频跳转体育
                    ext.setAction(VipMsgCodeConstant.ACTION_SPORT);
                    ext.setIsParse(1);
                    ext.setLaunchMode(1);
                    Map map = new HashMap();
                    String jsonVal = "{\"type\":6,\"source\":\"lesports\",\"value\":{\"vid\":" + video.getVideoId()
                            + ",\"videoName\":\"" + video.getName() + "\"}}";
                    map.put("burrowContent", jsonVal);
                    jump.setValue(map);
                    jump.setExtend(ext);
                } else {
                    buildJumpToTv(data, jump, ext, commonParam);
                }
            } else if (data instanceof Subject) {
                Subject subject = (Subject) data;
                if (subject.getCategoryId() != null && subject.getCategoryId() == VideoConstants.Category.SPORT) {// 体育专题跳转体育
                    ext.setAction(VipMsgCodeConstant.ACTION_SPORT);
                    ext.setIsParse(1);
                    ext.setLaunchMode(1);
                    Map map = new HashMap();
                    String jsonVal = "{\"type\":2,\"source\":\"lesports\",\"value\":{\"topicId\":"
                            + subject.getSubjectId() + "}}";
                    map.put("burrowContent", jsonVal);
                    jump.setValue(map);
                    jump.setExtend(ext);
                } else {
                    buildJumpToTv(data, jump, ext, commonParam);
                }
            } else if (data instanceof BaseBlock) {
                BaseBlock block = (BaseBlock) data;
                if (block.getType() != null && DataConstant.DATA_TYPE_SEARCH == block.getType()) {
                    ext.setAction("com.letv.leso.desktop.receiver");
                    ext.setIsParse(1);
                    JumpData jump_search = new JumpData();
                    jump_search.setType(1);
                    jump_search.setValue("{\"keyword\":\"\"}");
                    jump.setValue(jump_search);
                    jump.setExtend(ext);
                } else if (block.getType() != null && (DataConstant.DATA_TYPE_CHANNEL_LIST == block.getType())) {
                    buildJumpToTvNavigation(jump, ext, DataConstant.DATA_TYPE_CHANNEL_LIST, 2);
                } else if (block.getType() != null && (DataConstant.DATA_TYPE_MINE_LIST == block.getType())) {
                    buildJumpToTvNavigation(jump, ext, DataConstant.DATA_TYPE_MINE_LIST, 0);
                } else if (block.getType() != null && (DataConstant.DATA_TYPE_INDEX_LIST == block.getType())) {
                    buildJumpToTvNavigation(jump, ext, DataConstant.DATA_TYPE_INDEX_LIST, 1);
                } else if (block.getType() != null && (DataConstant.DATA_TYPE_LIVE_LIST == block.getType())) {
                    buildJumpToTvNavigation(jump, ext, DataConstant.DATA_TYPE_LIVE_LIST, 3);
                } else if (block.getType() != null && (DataConstant.DATA_TYPE_LEGUIDE == block.getType())) {
                    buildJumpToTvNavigation(jump, ext, DataConstant.DATA_TYPE_LEGUIDE, 3);
                } else if (block.getType() != null && block.getDataType() == DataConstant.DATA_TYPE_TVSTATION) {
                    Map map = new HashMap();
                    map.put("type", block.getType());
                    map.put("channelId", block.getBlockId());
                    ext.setAction("com.letv.switch_channel");
                    ext.setIsParse(1);
                    jump.setExtend(ext);
                    jump.setValue(map);
                } else if (!StringUtil.isBlank(block.getAppCode())) {
                    Object value = JsonUtil.parse(block.getAppParam(), Object.class);
                    ext.setIsParse(1);
                    ext.setLaunchMode(0);
                    if (TerminalConstant.THIRD_PARTY_SHOP.equals(block.getAppCode())) {
                        ext.setAction(VipMsgCodeConstant.ACTION_SHOPPING);
                    } else if (TerminalConstant.THIRD_PARTY_GOLIVE.equals(block.getAppCode())) {
                        if (commonParam.getBroadcastId() != null
                                && commonParam.getBroadcastId() == CommonConstants.CIBN) {
                            ext.setLaunchMode(1);
                        }
                        ext.setAction("com.letv.plugins.golive");
                        ext.setAppPackageName("com.golive.letvcinema");// golive包名判断有没有安装
                    } else if (TerminalConstant.THIRD_PARTY_LESTORE_SUBJECT.equals(block.getAppCode())) {
                        ext.setAction(VipMsgCodeConstant.ACTION_LESTORE_SUBJECT);
                    } else if (TerminalConstant.THIRD_PARTY_GAMECENTER.equals(block.getAppCode())) {
                        ext.setAction(VipMsgCodeConstant.ACTION_GAMECENTER);
                    }

                    block.setAppParam(null);
                    block.setAppCode(null);
                    jump.setExtend(ext);
                    jump.setValue(value);
                } else if (block.getDataType() == DataConstant.DATA_TYPE_PLAY_CENTER) {
                    jump = appJumpData(jump, ext, block);
                } else {
                    buildJumpToTv(data, jump, ext, commonParam);
                }
            } else {
                buildJumpToTv(data, jump, ext, commonParam);
            }
            data.setJump(jump);
        } else if (data instanceof BaseBlock) {
            BaseBlock block = (BaseBlock) data;
            if (!StringUtil.isBlank(block.getAppCode())) {
                Object value = JsonUtil.parse(block.getAppParam(), Object.class);
                ext.setIsParse(1);
                ext.setLaunchMode(0);
                if (TerminalConstant.THIRD_PARTY_SHOP.equals(block.getAppCode())) {
                    ext.setAction("com.stv.shopping.action.external");
                } else if (TerminalConstant.THIRD_PARTY_GOLIVE.equals(block.getAppCode())) {
                    ext.setAction("com.letv.plugins.golive");
                    ext.setAppPackageName("com.golive.letvcinema");// golive包名判断有没有安装
                }
                block.setAppParam(null);
                block.setAppCode(null);
                jump.setExtend(ext);
                jump.setValue(value);
                data.setJump(jump);
            } else if (block.getDataType() == DataConstant.DATA_TYPE_PLAY_CENTER) {
                data.setJump(appJumpData(jump, ext, block));
            }
        }
        /*
         * if (commonParam.getAppCode() == null ||
         * (commonParam.getTerminalApplication
         * ().equals(TerminalCommonConstant.TERMINAL_APPLICATION_MEDIA_CIBN) &&
         * TerminalUtil.supportJumpLeChildApp(commonParam))) {
         * buildJumpToLeChild(data,commonParam,1);
         * }
         */
        return data;
    }

    @SuppressWarnings("rawtypes")
    private static JumpData appJumpData(JumpData jump, Extension ext, BaseBlock block) {

        if (jump != null && ext != null && block != null) {
            Map<String, Long> map = new HashMap<String, Long>();
            JumpData<Map<String, Long>, Extension> jumptmp = new JumpData<Map<String, Long>, Extension>();
            map.put("id", StringUtil.toLong(block.getId()));
            ext.setAction(ApplicationUtils.get(ApplicationConstants.GAME_CONTER_ACTION));
            ext.setResource(ChannelConstants.JUMP_PARAMS_GAMECONTER_RESOURCE);
            ext.setTypeString(ChannelConstants.JUMP_PARAMS_GAMECONTER_TYPESTRING);
            ext.setIsParse(1);
            ext.setDataType(block.getDataType());
            ext.setAppPackageName(block.getExtendText());
            ext.setStorePackageName(ApplicationUtils.get(ApplicationConstants.GAME_CONTER_PACKAGENAME));
            jumptmp.setType(ChannelConstants.JUMP_PARAMS_GAMECONTER_TYPE);
            jumptmp.setValue(map);
            jump.setExtend(ext);
            jump.setValue(jumptmp);
        }
        return jump;

    }

    public static BaseData buildJumpToLESTORE(BaseData data, CommonParam commonParam) {
        JumpData jump = new JumpData();
        jump.setType(DataConstant.DATA_TYPE_EXT_APP);
        Extension ext = new Extension();
        ext.setAction(VipMsgCodeConstant.ACTION_LESTORE_SUBJECT);
        ext.setAppPackageName("com.letv.tv");
        ext.setIsParse(1);
        jump.setExtend(ext);
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("type", 3);
        Map<String, Object> value = new HashMap<String, Object>();
        value.put("type", 0);
        value.put("pkg", "com.letv.tv");
        value.put("from", "com.letv.tv");
        maps.put("value", value);
        jump.setValue(maps);
        data.setJump(jump);
        return data;
    }

    public static BaseData buildJumpToLeChildHome(BaseData data, CommonParam commonParam) {

        Map<String, Object> value = new HashMap();
        value.put("history", 0);
        value.put("subjectType", 0);
        value.put("type", 1);
        buildJumpToLeChild(data, commonParam, 0, 106, value);
        return data;
    }

    public static BaseData buildJumpToLeChild(BaseData data, CommonParam commonParam, int parse) {
        if (commonParam == null || data.getJump() == null || data.getJump().getExtend() == null) {
            return data;
        }
        Extension extend = (Extension) data.getJump().getExtend();
        if (!extend.getAction().equals("com.letv.external.lekids")
                || !extend.getAppPackageName().equals("com.letv.lekids")) {
            return data;
        }
        int type = data.getJump().getType();
        Object obj = data.getJump().getValue();
        if (obj == null) {
            return data;
        }
        buildJumpToLeChild(data, commonParam, parse, type, obj);
        return data;
    }

    public static BaseData buildJumpToLeChild(BaseData data, CommonParam commonParam, int parse, int type, Object value) {
        if (commonParam == null || type == DataConstant.DATA_TYPE_EXT_APP) {
            return data;
        }
        JumpData jump = new JumpData();
        jump.setType(DataConstant.DATA_TYPE_EXT_APP);
        Extension ext = new Extension();
        ext.setAction("com.letv.external.lekids");
        ext.setAppPackageName("com.letv.lekids");
        ext.setIsParse(parse);
        jump.setExtend(ext);
        Map<String, Object> maps = new HashMap();
        maps.put("type", type);
        maps.put("value", value);
        jump.setValue(maps);
        if (commonParam.getAppCode() == null) {
            data.setJump(jump);
        } else if (commonParam.getTerminalApplication().equals(TerminalCommonConstant.TERMINAL_APPLICATION_MEDIA_CIBN)
                && TerminalUtil.supportJumpLeChildApp(commonParam)) {
            data.setJump(jump);
        }
        return data;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void buildJumpToTv(BaseData data, JumpData jump, Extension ext, CommonParam commonParam) {
        ext.setAction("com.letv.external.new");
        ext.setLaunchMode(ApplicationUtils.getInt(ApplicationConstants.IPTV_BURROW_MODE_START_TV));
        // ext.setIsParse(0);
        // 儿童桌面所有专辑视频都进儿童播放
        if (TerminalCommonConstant.TERMINAL_APPLICATION_LECHILD_DESK.equals(commonParam.getTerminalApplication())) {
            ext.setModel(1);
            ext.setIsParse(1);
        } else {
            if (data.getJump() != null && data.getJump().getExtend() != null) {
                Extension ext_m = (Extension) data.getJump().getExtend();
                ext.setModel(ext_m.getModel());
                if (ext.getModel() == 1) {
                    ext.setIsParse(1);
                }
            }
        }
        jump.setValue(data.getJump());
        jump.setExtend(ext);
        data.getJump().setExtend(null);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void buildJumpToTvNavigation(JumpData jump, Extension ext, Integer type, Integer id) {
        ext.setAction("com.letv.external.new");
        ext.setLaunchMode(ApplicationUtils.getInt(ApplicationConstants.IPTV_BURROW_MODE_START_TV));
        ext.setIsParse(0);
        JumpData jump_nav = new JumpData();
        jump_nav.setType(type);
        Map map = new HashMap();
        map.put("id", id);
        jump_nav.setValue(map);
        jump.setValue(jump_nav);
        jump.setExtend(ext);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static BaseChannel buildJumpObjFromDb(BaseChannel desChannel, Integer dataType, String defaultStream,
                                                 String defaultStreamName, CommonParam commonParam) {
        desChannel = buildJumpObjFromDb(desChannel, dataType, defaultStream, defaultStreamName);
        JumpData jump = new JumpData();
        jump.setType(DataConstant.DATA_TYPE_EXT_APP);//外部应用跳转
        if ((commonParam != null)
                && ((TerminalCommonConstant.TERMINAL_APPLICATION_LEVIEW.equals(commonParam.getTerminalApplication())) || (TerminalCommonConstant.TERMINAL_APPLICATION_LECHILD_DESK
                        .equals(commonParam.getTerminalApplication())))) {
            Extension ext = new Extension();
            ext.setAction("com.letv.external.new");
            ext.setIsParse(0);

            if (TerminalCommonConstant.TERMINAL_APPLICATION_LECHILD_DESK.equals(commonParam.getTerminalApplication())) {
                ext.setModel(1);
                ext.setIsParse(1);
            } else if (desChannel.getJump() != null && desChannel.getJump().getExtend() != null) {
                Extension ext_m = (Extension) desChannel.getJump().getExtend();
                ext.setModel(ext_m.getModel());
                ext.setModel(ext_m.getIsParse());
            }
            jump.setValue(desChannel.getJump());
            jump.setExtend(ext);
            desChannel.getJump().setExtend(null);
            desChannel.setJump(jump);
        }
        return desChannel;
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
    private static JumpData JumpModel(BaseData obj, JumpData jump, Integer dataType) {
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
                    setExtOfChild(jump);
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
                    setExtOfChild(jump);
                }
            } else if (obj instanceof Subject) {
                Subject subject = (Subject) obj;
                if (subject.getCategoryId() != null && subject.getCategoryId() == VideoConstants.Category.TEACH
                        && subject.getSubCategoryId() != null
                        && subject.getSubCategoryId().contains(String.valueOf(VideoConstants.Category.TEACH_CHILD))) {
                    setExtOfChild(jump);
                }
            } else if (obj instanceof BaseBlock) {
                BaseBlock block = (BaseBlock) obj;
                if (StringUtil.isNotBlank(block.getExtendText())) {
                    String[] ExtendText = block.getExtendText().split("\\|");
                    if (ExtendText.length > 0) {
                        String type = ExtendText[0];
                        if (StringUtils.isNumeric(type)
                                && DataConstant.DATA_TYPE_CHILDREN == StringUtil.toInteger(type)) {
                            setExtOfChild(jump);
                        }
                    }
                }
            }
        }
        return jump;
    }

    private static void setExtOfChild(JumpData jump) {
        Extension ext = new Extension();
        ext.setModel(1);
        ext.setIsParse(1);
        ext.setAction("com.letv.external.lekids");
        ext.setAppPackageName("com.letv.lekids");
        jump.setExtend(ext);
    }

    /**
     * 频道、色块构建跳转对象
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static BaseChannel buildJumpObjFromDb(BaseChannel desChannel, Integer dataType, String defaultStream,
            String defaultStreamName) {
        JumpData jump = null;
        try {
            /**
             * 兼容老逻辑的频道跳转配置处理
             */
            jump = new JumpData();
            if (desChannel.getConfigInfo() == null || "".equals(desChannel.getConfigInfo())) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.setSerializationInclusion(NON_NULL);
                if (desChannel instanceof Channel && dataType != null) {
                    Channel channel = (Channel) desChannel;
                    switch (dataType) {
                    case DataConstant.DATA_TYPE_MULTILIST://多级列表
                    case DataConstant.DATA_TYPE_LIST://数据列表
                    case DataConstant.DATA_TYPE_LIST_RECOMMENDATION://猜你喜欢
                    case DataConstant.DATA_TYPE_CHILDREN://儿童频道
                    case DataConstant.DATA_TYPE_CHANNEL://频道
                        Channel channel_c = new Channel();
                        channel_c.setChannelId(channel.getChannelId());
                        channel_c.setDataUrl(channel.getDataUrl());
                        channel_c.setChannelCode(channel.getChannelCode());
                        channel_c.setDefaultStream(channel.getDefaultStream());
                        channel_c.setDefaultStreamName(defaultStreamName);
                        jump.setValue(channel_c);
                        break;
                    case DataConstant.DATA_TYPE_SUBJECT://专题
                        Subject subject = new Subject();
                        String str = desChannel.getDataUrl();
                        if (str != null && !"".equals(str) && str.indexOf(":") > 0) {
                            String[] arr = str.split(":");
                            subject.setSubjectType(Integer.valueOf(arr[0]));
                            subject.setSubjectId(arr[1]);
                        }
                        jump.setValue(subject);
                        break;
                    }
                } else if (desChannel instanceof ChannelData && dataType != null) {
                    ChannelData channelData = (ChannelData) desChannel;
                    switch (dataType) {
                    case DataConstant.DATA_TYPE_ALBUM://专辑
                        AlbumDto album = new AlbumDto();
                        album.setAlbumId(channelData.getTitleAlbumId() + "");
                        album.setTvCopyright(1);
                        album.setSrc(1);
                        jump.setValue(album);
                        break;
                    case DataConstant.DATA_TYPE_CHANNEL:
                    case DataConstant.DATA_TYPE_MULTILIST:
                    case DataConstant.DATA_TYPE_LIST:
                    case DataConstant.DATA_TYPE_LIST_RECOMMENDATION:
                    case DataConstant.DATA_TYPE_CHILDREN:
                        Channel channel = new Channel();
                        channel.setChannelId(channelData.getTitleChannelId());
                        channel.setDataUrl(channelData.getDataUrl());
                        jump.setValue(channel);
                        break;
                    case DataConstant.DATA_TYPE_RETRIEVE://检索
                        break;
                    default:
                        BaseData data = new BaseData();
                        jump.setValue(data);
                    }
                }
                /*
                 * if (DataConstant.DATA_TYPE_CHILDREN == dataType) {
                 * jump.setMainType(DataConstant.JUMP_MAINTYPE_1);
                 * }
                 */
                jump.setType(dataType);
                // 老逻辑跳检索，客户端需要channelId，服务端无法提供。所以不做jump
                if (dataType != DataConstant.DATA_TYPE_RETRIEVE) {
                    desChannel.setJump(jump);
                }
                return desChannel;
            }
            if (dataType != null) {
                switch (dataType) {
                case DataConstant.DATA_TYPE_BLANK:
                    StaticBlock staticBlock = new StaticBlock();
                    staticBlock = (StaticBlock) parseSkipConfigInfo(desChannel.getConfigInfo(), staticBlock);
                    jump.setValue(staticBlock);
                    break;
                case DataConstant.DATA_TYPE_ALBUM:// 1
                    AlbumDto albumDto = new AlbumDto();
                    albumDto = (AlbumDto) parseSkipConfigInfo(desChannel.getConfigInfo(), albumDto);
                    albumDto.setDefaultStream(defaultStream);
                    albumDto.setDefaultStreamName(defaultStreamName);
                    jump.setValue(albumDto);
                    break;
                case DataConstant.DATA_TYPE_VIDEO:// 2
                    VideoDto videoDto = new VideoDto();
                    videoDto = (VideoDto) parseSkipConfigInfo(desChannel.getConfigInfo(), videoDto);
                    videoDto.setDefaultStream(defaultStream);
                    videoDto.setDefaultStreamName(defaultStreamName);
                    jump.setValue(videoDto);
                    break;
                case DataConstant.DATA_TYPE_SUBJECT:
                    Subject subject = new Subject();
                    subject = (Subject) parseSkipConfigInfo(desChannel.getConfigInfo(), subject);
                    jump.setValue(subject);
                    break;
                case DataConstant.DATA_TYPE_LIVE:// 4
                    Live live = new Live();
                    live = (Live) parseSkipConfigInfo(desChannel.getConfigInfo(), live);
                    jump.setValue(live);
                    break;
                case DataConstant.DATA_TYPE_SOARING_LIST:
                    ChannleLeGuide channelLeGuide = new ChannleLeGuide();
                    channelLeGuide = (ChannleLeGuide) parseSkipConfigInfo(desChannel.getConfigInfo(), channelLeGuide);
                    jump.setValue(channelLeGuide);
                    break;
                case DataConstant.DATA_TYPE_CHANNEL:// 6
                case DataConstant.DATA_TYPE_LEGUIDE:
                case DataConstant.DATA_TYPE_SUBJECT_LIST:
                case DataConstant.DATA_TYPE_RANK_LIST:
                case DataConstant.DATA_TYPE_CHANNEL_LIST:
                case DataConstant.DATA_TYPE_LIVE_LIST:
                case DataConstant.DATA_TYPE_INDEX_LIST:
                case DataConstant.DATA_TYPE_MINE_LIST:
                case DataConstant.DATA_TYPE_CONTENT_LIST:
                    Channel channel = new Channel();
                    channel = (Channel) parseSkipConfigInfo(desChannel.getConfigInfo(), channel);
                    jump.setValue(channel);
                    break;
                case DataConstant.DATA_TYPE_LIST:// 兼容老的频道墙跳转逻辑
                    Channel channel_10 = new Channel();
                    channel_10 = (Channel) parseSkipConfigInfo(desChannel.getConfigInfo(), channel_10);
                    jump.setValue(channel_10);
                    break;
                case DataConstant.DATA_TYPE_MULTILIST:// 兼容老的频道墙跳转逻辑
                    Channel channel_14 = new Channel();
                    channel_14 = (Channel) parseSkipConfigInfo(desChannel.getConfigInfo(), channel_14);
                    jump.setValue(channel_14);
                    break;
                case DataConstant.DATA_TYPE_LIST_RECOMMENDATION:// 兼容老的频道墙跳转逻辑
                    Channel channel_11 = new Channel();
                    channel_11 = (Channel) parseSkipConfigInfo(desChannel.getConfigInfo(), channel_11);
                    jump.setValue(channel_11);
                    break;
                case DataConstant.DATA_TYPE_CHILDREN:
                    Channel channel_19 = new Channel();
                    channel_19 = (Channel) parseSkipConfigInfo(desChannel.getConfigInfo(), channel_19);
                    jump.setValue(channel_19);
                    // jump.setMainType(DataConstant.JUMP_MAINTYPE_1);
                    break;
                case DataConstant.DATA_TYPE_RETRIEVE:// 8
                    Retrieve retrieve = new Retrieve();
                    retrieve = (Retrieve) parseSkipConfigInfo(desChannel.getConfigInfo(), retrieve);
                    jump.setValue(retrieve);
                    break;
                case DataConstant.DATA_TYPE_BROWSER:// 9
                    Browser browser = new Browser();
                    browser = (Browser) parseSkipConfigInfo(desChannel.getConfigInfo(), browser);
                    jump.setValue(browser);
                    break;
                case DataConstant.DATA_TYPE_PAGE:// 15
                    Page page = new Page();
                    page = (Page) parseSkipConfigInfo(desChannel.getConfigInfo(), page);
                    jump.setValue(page);
                    break;
                case DataConstant.DATA_TYPE_TOPIC_ACTIVITY:// 23
                    BaseBlock block = new BaseBlock();
                    block = (BaseBlock) parseSkipConfigInfo(desChannel.getConfigInfo(), block);
                    jump.setValue(block);
                    break;
                case DataConstant.DATA_TYPE_PRELIVE:// 22
                    SubjectPreLive preSubject = new SubjectPreLive();
                    preSubject = (SubjectPreLive) parseSkipConfigInfo(desChannel.getConfigInfo(), preSubject);
                    jump.setValue(preSubject);
                    break;
                case DataConstant.DATA_TYPE_CONTAINER:// 27
                    Container container = new Container();
                    container = (Container) parseSkipConfigInfo(desChannel.getConfigInfo(), container);
                    jump.setValue(container);
                    break;
                case DataConstant.DATA_TYPE_STAR:
                    StarCms star = new StarCms();
                    star = (StarCms) parseSkipConfigInfo(desChannel.getConfigInfo(), star);
                    jump.setValue(star);
                    break;
                case DataConstant.DATA_TYPE_LIST_RANK:
                    ChartsDto charts = new ChartsDto();
                    charts = (ChartsDto) parseSkipConfigInfo(desChannel.getConfigInfo(), charts);
                    jump.setValue(charts);
                    break;
                case DataConstant.DATA_TYPE_CHECKSTAND:
                    jump.setValue(new BaseData());// 跳收银台，客户端只需要type，但是为了客户端格式统一，需要value对象
                    break;
                case DataConstant.DATA_TYPE_MUSIC:
                    Mtv mtv = new Mtv();
                    mtv = (Mtv) parseSkipConfigInfo(desChannel.getConfigInfo(), mtv);
                    jump.setValue(mtv);
                    break;
                case DataConstant.DATA_TYPE_CONTACT_US:
                case DataConstant.DATA_TYPE_ABOUT_US:
                    StaticHtml shtml = new StaticHtml();
                    shtml = (StaticHtml) parseSkipConfigInfo(desChannel.getConfigInfo(), shtml);
                    jump.setValue(shtml);
                    break;
                default:
                    BaseBlock defaultBlock = new BaseBlock();
                    defaultBlock = (BaseBlock) parseSkipConfigInfo(desChannel.getConfigInfo(), defaultBlock);
                    jump.setValue(defaultBlock);
                    break;
                }
                jump.setType(dataType);
            } else {
                jump.setType(DataConstant.DATA_TYPE_BLANK);
            }
        } catch (Exception ex) {
            log.warn("buildJumpObj_" + dataType + "_" + ex.getMessage());
        }
        desChannel.setJump(jump);
        desChannel.setConfigInfo(null);// 属性对客户端不可见
        return desChannel;
    }

    private static Object parseSkipConfigInfo(String jsonStr, Object destObj) {
        if (jsonStr != null && !"".equals(jsonStr)) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.setSerializationInclusion(NON_NULL);
                if (destObj instanceof AlbumDto) {
                    destObj = mapper.readValue(jsonStr, AlbumDto.class);
                } else if (destObj instanceof VideoDto) {
                    destObj = mapper.readValue(jsonStr, VideoDto.class);
                } else if (destObj instanceof SubjectPreLive) {
                    destObj = mapper.readValue(jsonStr, SubjectPreLive.class);
                } else if (destObj instanceof Subject) {
                    destObj = mapper.readValue(jsonStr, Subject.class);
                } else if (destObj instanceof Live) {
                    destObj = mapper.readValue(jsonStr, Live.class);
                } else if (destObj instanceof Channel) {
                    destObj = mapper.readValue(jsonStr, Channel.class);
                } else if (destObj instanceof ChannleLeGuide) {
                    destObj = mapper.readValue(jsonStr, ChannleLeGuide.class);
                } else if (destObj instanceof Retrieve) {
                    destObj = mapper.readValue(jsonStr, Retrieve.class);
                } else if (destObj instanceof Browser) {
                    destObj = mapper.readValue(jsonStr, Browser.class);
                } else if (destObj instanceof Page) {
                    destObj = mapper.readValue(jsonStr, Page.class);
                } else if (destObj instanceof BaseBlock) {
                    destObj = mapper.readValue(jsonStr, BaseBlock.class);
                } else if (destObj instanceof Container) {
                    destObj = mapper.readValue(jsonStr, Container.class);
                } else if (destObj instanceof StarCms) {
                    destObj = mapper.readValue(jsonStr, StarCms.class);
                } else if (destObj instanceof ChartsDto) {
                    destObj = mapper.readValue(jsonStr, ChartsDto.class);
                } else if (destObj instanceof StaticBlock) {
                    destObj = mapper.readValue(jsonStr, StaticBlock.class);
                } else if (destObj instanceof Mtv) {
                    destObj = mapper.readValue(jsonStr, Mtv.class);
                } else if (destObj instanceof StaticHtml) {
                    destObj = mapper.readValue(jsonStr, StaticHtml.class);
                }
            } catch (Exception e) {
                log.error("channelService_parseSkipConfigInfo:" + jsonStr, e.getMessage());
            }

        }
        return destObj;
    }

    private static String getMapSets(String subCate, Map<String, String> subCateMap) {
        String reStr = "";
        if (StringUtil.isNotBlank(subCate)) {
            reStr = subCate;
        } else if (subCateMap != null) {
            Iterator it = subCateMap.keySet().iterator();
            while (it.hasNext()) {
                reStr += "," + it.next();
            }
            if (StringUtil.isNotBlank(reStr)) {
                reStr = reStr.substring(1, reStr.length());
            }
        }
        return reStr;
    }

    public static String getIconType(Integer src, String ifCharge, List<Stream> streams, Integer cid) {
        return getIconType(src, ifCharge, null, streams, cid);
    }

    /**
     * icon角标类型
     * @param src
     * @param ifCharge
     * @param chargeType
     * @param streams
     * @param cid
     * @return
     */
    public static String getIconType(Integer src, String ifCharge, Integer chargeType, List<Stream> streams, Integer cid) {
        return getIconType(src, ifCharge, chargeType, streams, cid, null, null);
    }

    public static String getIconType(Integer src, String ifCharge, Integer chargeType, List<Stream> streams,
            Integer cid, String srcId, CommonParam commonParam) {
        return getIconType(src, ifCharge, String.valueOf(chargeType), streams, cid, null, null);
    }

    public static String getIconType(Integer src, String ifCharge, String chargeType, List<Stream> streams,
            Integer cid, String srcId, CommonParam commonParam) {
        String iconType = IconConstants.ICON_TYPE_NOICON;
        if (src != null && src == 2) {// 外网
            iconType = IconConstants.ICON_TYPE_ALL;
        } else if (StringUtil.isNotBlank(ifCharge) && "1".equals(ifCharge)) {// 会员
            if (chargeType != null && String.valueOf(ChargeTypeConstants.chargePolicy.CHARGE_BY_VIP).equals(chargeType)) {
                iconType = IconConstants.ICON_TYPE_TVOD;
            } else {
                iconType = IconConstants.ICON_TYPE_VIP;
            }
            // if (StringUtil.isNotBlank(srcId) && srcId.contains("200037")) {
            // if (null != commonParam &&
            // TerminalUtil.isSupportFairyTaleTown(commonParam)) {
            // iconType = IconConstants.ICON_TYPE_VIP_HOME;
            // }
            // }
            if (null != commonParam && TerminalUtil.isSupportFairyTaleTown(commonParam)) {
                if (ChargeInfo.isHomeVip(chargeType)) {
                    iconType = IconConstants.ICON_TYPE_VIP_HOME;
                }
            }
        } else if (streams != null) {// 码流
            for (Stream stream : streams) {
                if (stream.getCode().toLowerCase().indexOf("4k") > -1) {
                    iconType = IconConstants.ICON_TYPE_4K;
                } else if (stream.getCode().toLowerCase().indexOf("1080p") > -1) {
                    iconType = IconConstants.ICON_TYPE_1080P;
                } else if (stream.getCode().toLowerCase().indexOf("3d") > -1) {
                    iconType = IconConstants.ICON_TYPE_3D;
                } else if (stream.getCode().toLowerCase().indexOf("db") > -1) {
                    iconType = IconConstants.ICON_TYPE_DU;
                }
            }
        } else if (cid != null) {// 视频分类
            iconType = IconConstants.ICON_TYPE_CID;
        }

        return iconType;
    }

    // v1
    public static Integer getChargeType(String pay_platforms) {
        return getChargeType(pay_platforms, CommonConstants.TV_PAY_CODE); // old
                                                                          // version
    }

    // v2
    public static Integer getChargeType(String pay_platforms, String pay_platform) {
        if (StringUtil.isNotBlank(pay_platforms) && MmsDataUtil.isSupportPayPlatform(pay_platforms)
                && StringUtil.isNotBlank(pay_platform)) {
            if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                if (pay_platforms.contains(";1;860001;0") // v1
                        || pay_platforms.contains(pay_platform + ":1;0;0")
                        || pay_platforms.contains(pay_platform + ":1;16-0;0")) { // TODO
                                                                                 // 16-0:
                                                                                 // 家庭会员点播
                    return ChargeTypeConstants.chargePolicy.CHARGE_BY_VIP;
                }
            } else {
                if (pay_platforms.indexOf(";1;860001;0") > -1) {
                    return ChargeTypeConstants.chargePolicy.CHARGE_BY_VIP;
                }
            }
        }
        return null;
    }

    public static Integer getChargeType(Object obj) {
        Integer ret = null;

        if (null != obj) {
            if (obj instanceof ChargeInfo) {
                ChargeInfo chargeInfo = (ChargeInfo) obj;
                ret = getChargeType(chargeInfo.getIsCharge(), String.valueOf(chargeInfo.getChargeType4i()),
                        Integer.parseInt(chargeInfo.getIscoupon()));
            } else if (obj instanceof ChargeInfoDto) {
                ChargeInfoDto chargeInfoDto = (ChargeInfoDto) obj;
                ret = getChargeType(chargeInfoDto.getIsCharge(), chargeInfoDto.getPayType(),
                        Integer.parseInt(chargeInfoDto.getIscoupon()));
            }
        }

        return ret;
    }

    public static Integer getChargeType(String pay_platforms, CommonParam commonParam) {
        String pay_platform = null;
        if (null != commonParam && null != commonParam.getP_devType()) {
            pay_platform = MmsDataUtil.getPayPlatform(commonParam.getP_devType());
        }
        return getChargeType(pay_platforms, pay_platform);
    }

    public static Integer getChargeType(String is_pay, String payType, Integer isCoupon) {

        if (StringUtil.isNotBlank(is_pay) && StringUtil.isNotBlank(payType) && isCoupon != null) {
            if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                if (is_pay.equals("1") && (payType.equals("860001") || payType.equals("0") || payType.contains("-0")) // 点播
                        && isCoupon.intValue() == 0) {
                    return ChargeTypeConstants.chargePolicy.CHARGE_BY_VIP;
                }
            } else {
                if (is_pay.equals("1") && payType.equals("860001") && isCoupon.intValue() == 0) {
                    return ChargeTypeConstants.chargePolicy.CHARGE_BY_VIP;
                }
            }
        }
        return null;
    }

    public static ChargeInfoDto genChargeInfoDto(Integer devType, String pay_platform) {
        ChargeInfoDto ret = null;
        List<ChargeInfoDto> chargeInfoDtos = genChargeInfos(pay_platform);
        if (null != chargeInfoDtos) {
            for (int i = 0; i < chargeInfoDtos.size(); i++) {
                if (StringUtil.isNotBlank(chargeInfoDtos.get(i).getPlatForm())) {
                    Integer devTypeTmp = MmsDataUtil.getDevType(chargeInfoDtos.get(i).getPlatForm());
                    if (null != devTypeTmp && devTypeTmp.intValue() == devType.intValue()) {
                        ret = chargeInfoDtos.get(i);
                        break;
                    }
                }
            }
        }
        return ret;
    }

    public static List<ChargeInfoDto> genChargeInfos(String pay_platform) {
        return genChargeInfos(pay_platform, null, null, null, null, null);
    }

    public static List<ChargeInfoDto> genChargeInfos(String pay_platform, CommonParam commonParam) {
        return genChargeInfos(pay_platform, null, null, null, null, commonParam);
    }

    public static List<ChargeInfoDto> genChargeInfos(String pay_platform, Integer src, List<Stream> streams,
            Integer cid, Integer isTempFree, CommonParam commonParam) {
        List<ChargeInfoDto> ret = null;
        String[] pay_platforms = null;
        String chargeInfo_141007 = null;

        if (StringUtil.isBlank(pay_platform)) {
            return ret;
        }

        if (pay_platform.indexOf(";") == -1 || pay_platform.indexOf(":") >= 0) {
            // 含V2 pay_platform:getIsPay;getPayType;getIsCoupon
            pay_platforms = pay_platform.split(",");
        } else { // 清洗老数据V1 ;getIsPay;getPayType;getIsCoupon
            String[] pay_platform_tmp = pay_platform.split(";");
            if (pay_platform_tmp.length > 1) {
                pay_platforms = pay_platform_tmp[0].split(",");
                pay_platform_tmp[2] = MmsDataUtil.formatPayType(pay_platform_tmp[2]); // 格式化payType，节省字典存储
                chargeInfo_141007 = StringUtil.getArrayToString(
                        Arrays.copyOfRange(pay_platform_tmp, 1, pay_platform_tmp.length), ";");
            }
        }

        if (null != pay_platforms) {
            ret = new ArrayList<ChargeInfoDto>();
            ChargeInfoDto chargeInfoDto = null;
            Integer devType = null;
            for (int i = 0, len = pay_platforms.length; i < len; i++) {
                if (pay_platforms[i].contains(":")) { // V2-data
                    String[] pay_platform_tmp = pay_platforms[i].split(":");
                    String[] pay_chargeInfo_tmp = pay_platform_tmp[1].split(";");
                    devType = MmsDataUtil.getDevType(pay_platform_tmp[0]);
                    if (null == devType) { // 过滤不支持的终端配置
                        continue;
                    }
                    chargeInfoDto = new ChargeInfoDto();
                    chargeInfoDto.setDevType(devType);
                    chargeInfoDto.setPlatForm(pay_platform_tmp[0]);
                    if (pay_chargeInfo_tmp.length == 3) {
                        chargeInfoDto.setIsCharge(pay_chargeInfo_tmp[0]);
                        chargeInfoDto.setPayType(String.valueOf(ChargeInfo.getChargeType4i(pay_chargeInfo_tmp[1])));
                        chargeInfoDto.setIscoupon(pay_chargeInfo_tmp[2]);
                        chargeInfoDto.setChargeType(getChargeType(pay_chargeInfo_tmp[0], pay_chargeInfo_tmp[1],
                                Integer.valueOf(pay_chargeInfo_tmp[2])));
                    }
                    if (null != isTempFree && isTempFree.intValue() == 1) {
                        chargeInfoDto.setIconType(IconConstants.ICON_TYPE_TEMP_FREE);
                    } else {
                        chargeInfoDto.setIconType(getIconType(src, chargeInfoDto.getIsCharge(), pay_chargeInfo_tmp[1],
                                streams, cid, null, commonParam));
                    }
                    ret.add(chargeInfoDto);
                } else { // v1-data
                    devType = MmsDataUtil.getDevType(pay_platforms[i]);
                    if (null == devType) { // 过滤不支持的终端配置
                        continue;
                    }
                    chargeInfoDto = new ChargeInfoDto();
                    chargeInfoDto.setDevType(devType);
                    if (null != chargeInfo_141007) {
                        if (pay_platforms[i].equals(CommonConstants.TV_PAY_CODE)) {
                            String[] pay_chargeInfo_tmp = chargeInfo_141007.split(";");
                            chargeInfoDto.setPlatForm(CommonConstants.TV_PAY_CODE);
                            if (pay_chargeInfo_tmp.length == 3) {
                                chargeInfoDto.setIsCharge(pay_chargeInfo_tmp[0]);
                                chargeInfoDto.setPayType(pay_chargeInfo_tmp[1]);
                                chargeInfoDto.setIscoupon(pay_chargeInfo_tmp[2]);
                                chargeInfoDto.setChargeType(getChargeType(pay_chargeInfo_tmp[0], pay_chargeInfo_tmp[1],
                                        Integer.valueOf(pay_chargeInfo_tmp[2])));
                            }
                        } else { // follow vrs
                            chargeInfoDto.setPlatForm(pay_platforms[i]);
                        }
                    } else { // follow vrs
                        chargeInfoDto.setPlatForm(pay_platforms[i]);
                    }
                    if (null != isTempFree && isTempFree.intValue() == 1) {
                        chargeInfoDto.setIconType(IconConstants.ICON_TYPE_TEMP_FREE);
                    } else {
                        chargeInfoDto.setIconType(getIconType(src, chargeInfoDto.getIsCharge(),
                                chargeInfoDto.getChargeType(), streams, cid));
                    }
                    ret.add(chargeInfoDto);
                }
            }
        }
        return ret;
    }

    public static List<ChargeInfoDto> genChargeInfos(List<ChargeInfo> chargeInfos) {
        List<ChargeInfoDto> chargeInfoDtos = null;

        if (null != chargeInfos) {
            chargeInfoDtos = new ArrayList<ChargeInfoDto>();
            ChargeInfoDto chargeInfoDto = null;
            Integer devType = null;
            for (int i = 0; i < chargeInfos.size(); i++) {
                devType = MmsDataUtil.getDevType(chargeInfos.get(i).getChargePlatform());
                if (null == devType) { // 过滤不支持的终端配置
                    continue;
                }
                chargeInfoDto = new ChargeInfoDto();
                chargeInfoDto.setPlatForm(chargeInfos.get(i).getChargePlatform());
                chargeInfoDto.setDevType(devType);
                chargeInfoDto.setIsCharge(chargeInfos.get(i).getIsCharge());
                chargeInfoDto.setPayType(String.valueOf(chargeInfos.get(i).getChargeType4i()));
                chargeInfoDto.setIscoupon(chargeInfos.get(i).getIscoupon());
                chargeInfoDto.setChargeType(getChargeType(chargeInfos.get(i).getIsCharge(),
                        String.valueOf(chargeInfos.get(i).getChargeType4i()),
                        Integer.valueOf(chargeInfos.get(i).getIscoupon())));
                chargeInfoDto.setIconType(getIconType(null, chargeInfoDto.getIsCharge(), chargeInfoDto.getChargeType(),
                        null, null));
                chargeInfoDtos.add(chargeInfoDto);
            }
        }

        return chargeInfoDtos;
    }

    public static AlbumDto genAlbumDto(AlbumMysqlTable albumMysqlTable, String area, CommonParam commonParam) {
        AlbumDto albumDto = null;

        if (null != albumMysqlTable && null != commonParam) {
            albumDto = new AlbumDto();

            albumDto.setTvCopyright(1);

            if (StringUtil.isNotBlank(commonParam.getLangcode())) {
                commonParam.setLangcode(LocaleConstant.Langcode.ZH_CN);
            }
            if (LocaleConstant.Langcode.ZH_CN.equals(commonParam.getLangcode())) {
                albumDto.setName(albumMysqlTable.getName_cn());
            } else if (LocaleConstant.Langcode.EN_US.equals(commonParam.getLangcode())) {
                albumDto.setName(albumMysqlTable.getName_en());
            } else {
                albumDto.setName(albumMysqlTable.getName_cn());
            }
            albumDto.setSubName(albumMysqlTable.getSub_title());

            String imgUrl = MobileUtil.getPic(albumMysqlTable.getPic_collections(), 300, 400);
            if (StringUtil.isBlank(imgUrl)) {
                imgUrl = MobileUtil.getPic(albumMysqlTable.getPic_collections(), 400, 800);
            }
            albumDto.setBigImg(imgUrl);
            imgUrl = MobileUtil.getPic(albumMysqlTable.getPic_collections(), 400, 300);
            albumDto.setImg(imgUrl);
            albumDto.setAreaRec(area);
            if (null != albumMysqlTable.getScore()) {
                albumDto.setScore(albumMysqlTable.getScore());
            }
            if (null != albumMysqlTable.getDb_score() && albumMysqlTable.getDb_score().floatValue() > 0) {
                albumDto.setDb_score(albumMysqlTable.getDb_score());
            }
            albumDto.setAlbumId(String.valueOf(albumMysqlTable.getId()));
            albumDto.setCategoryId(albumMysqlTable.getCategory());
            albumDto.setSubCategoryId(albumMysqlTable.getSub_category());
            albumDto.setDataType(DataConstant.DATA_TYPE_ALBUM);
            albumDto.setId(String.valueOf(albumMysqlTable.getId()));
            if (!StringUtil.isBlank(albumMysqlTable.getPay_platform())
                    && VideoCommonUtil.isCharge(albumMysqlTable.getPay_platform(), albumMysqlTable.getPlay_platform(),
                            commonParam.getP_devType())) {
                albumDto.setIfCharge("1");
            } else {
                albumDto.setIfCharge("0");
            }
            // for tvod icon type
            Integer chargeType = JumpUtil.getChargeType(albumMysqlTable.getPay_platform(), commonParam);
            if (null == chargeType) {
                chargeType = JumpUtil.getChargeType(albumMysqlTable.getPay_platform());
            }
            albumDto.setChargeType(chargeType);

            if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                if (null == commonParam.getIsDync() || commonParam.getIsDync().intValue() != 1) {
                    albumDto.setChargeInfos(JumpUtil.genChargeInfos(albumMysqlTable.getPay_platform()));
                }
                ChargeInfoDto chargeInfoDto = JumpUtil.genChargeInfoDto(commonParam.getP_devType(),
                        albumMysqlTable.getPay_platform());
                if (null != chargeInfoDto) {
                    albumDto.setIfCharge(chargeInfoDto.getIsCharge());
                    albumDto.setChargeType(JumpUtil.getChargeType(chargeInfoDto));
                }
            }
        }

        return albumDto;
    }

    public static BaseData genJumpData(AlbumDto albumDto, CommonParam commonParam) {
        if (null != albumDto) {
            return JumpUtil.bulidJumpObj(albumDto, DataConstant.DATA_TYPE_ALBUM, null, null, commonParam);
        } else {
            return null;
        }
    }
}
