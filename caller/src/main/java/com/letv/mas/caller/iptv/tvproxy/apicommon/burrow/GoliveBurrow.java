package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow;

import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.GoliveDto.GoliveAlbumBurrowDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.LiveSdkDto.TVLiveBurrowDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.TVDto.*;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.BurrowUtil.BurrowTVResource;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.GoliveNavigationsDto.GoliveResultDto;

/**
 * 同步院线桌面打洞规则
 * @author chenjian
 */
public class GoliveBurrow extends AbstractBurrow {

    public GoliveBurrow(BurrowUtil burrowUtil) {
        super(burrowUtil);
    }

    public BaseData burrowObj(BaseData data, CommonParam commonParam) {

        if (data instanceof GoliveResultDto) {
            GoliveResultDto goliveDto = (GoliveResultDto) data;
            int dataType = goliveDto.getDataType();
            // 专辑型数据
            if (dataType == DataConstant.DATA_TYPE_ALBUM) {
                int src = goliveDto.getSrc();
                // 内网数据
                if (src == 1) {
                    TVAlbumBurrowDto tVAlbumBurrowDto = new TVAlbumBurrowDto();
                    tVAlbumBurrowDto.setResource(BurrowTVResource.GOLIVE);
                    tVAlbumBurrowDto.setAlbumId(goliveDto.getId());
                    tVAlbumBurrowDto.setSrc(1);
                    tVAlbumBurrowDto.setTvCopyright(1);
                    return this.burrowUtil.burrowTVAlbum(data, tVAlbumBurrowDto, commonParam);
                } else {
                    // 跳GOLIVE专辑详情
                    if (CommonConstants.GOLIVE_NAME.equals(goliveDto.getSubSrc())) {
                        GoliveAlbumBurrowDto goliveAlbumBurrowDto = new GoliveAlbumBurrowDto();
                        goliveAlbumBurrowDto.setId(goliveDto.getId());
                        return this.burrowUtil.burrowGoliveAlbum(data, goliveAlbumBurrowDto, commonParam);
                    }
                }
            } else if (dataType == DataConstant.DATA_TYPE_VIDEO) {
                // TV视频
                TVVideoBurrowDto tVVideoBurrowDto = new TVVideoBurrowDto();
                tVVideoBurrowDto.setResource(BurrowTVResource.GOLIVE);
                tVVideoBurrowDto.setVideoId(goliveDto.getId());
                return this.burrowUtil.burrowTVVideo(data, tVVideoBurrowDto, commonParam);
            } else if (dataType == DataConstant.DATA_TYPE_SUBJECT) {
                // TV专题
                TVSubjectBurrowDto tVSubjectBurrowDto = new TVSubjectBurrowDto();
                tVSubjectBurrowDto.setResource(BurrowTVResource.GOLIVE);
                tVSubjectBurrowDto.setSubjectId(goliveDto.getId());
                tVSubjectBurrowDto.setSubjectType(goliveDto.getSubjectType());
                return this.burrowUtil.burrowTVSubject(data, tVSubjectBurrowDto, commonParam);
            } else if (dataType == DataConstant.DATA_TYPE_TVSTATION) {
                // TVLIVE
                TVLiveBurrowDto tVLiveBurrowDto = new TVLiveBurrowDto();
                tVLiveBurrowDto.setChannelId(goliveDto.getId());
                return this.burrowUtil.burrowLiveLunbo(data, tVLiveBurrowDto, commonParam);
            } else if (dataType == DataConstant.DATA_TYPE_CONTAINER) {
                // TV 容器
                TVContainer tVContainer = new TVContainer();
                tVContainer.setContainerId(goliveDto.getId());
                return this.burrowUtil.burrowTVContainer(data, tVContainer, commonParam);
            } else if (dataType == DataConstant.DATA_TYPE_BROWSER) {
                // 浏览器
                TVBrowseDto tVBrowseDto = new TVBrowseDto();
                tVBrowseDto.setUrl(goliveDto.getUrl());
                tVBrowseDto.setOpenType(goliveDto.getOpenType());
                return this.burrowUtil.burrowTVBrowse(data, tVBrowseDto, commonParam);
            }

        }
        return data;
    }
}
