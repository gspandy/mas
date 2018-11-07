package com.letv.mas.caller.iptv.tvproxy.desk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.BusinessCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.ErrorCodeConstants;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.bean.bo.Subject;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseBlock;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.channel.constants.ChannelConstants;
import com.letv.mas.caller.iptv.tvproxy.channel.service.ChannelService;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.ChannelDataMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.DeskConfigMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockContentTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.desk.DeskMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.ErrorMsgCodeUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.JsonUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import com.letv.mas.caller.iptv.tvproxy.desk.constants.DeskConstants;
import com.letv.mas.caller.iptv.tvproxy.desk.model.dto.DeskDto;
import com.letv.mas.caller.iptv.tvproxy.desk.model.dto.DeskListDto;
import com.letv.mas.caller.iptv.tvproxy.desk.model.dto.DeskValueDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.AlbumDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component(value = "v2.DeskService")
public class DeskService extends BaseService {
    private final static Logger log = LoggerFactory.getLogger(DeskService.class);

    @Autowired
    ChannelService channelService;

    /**
     * 获取桌面打动参数接口model=1:儿童桌面
     * @param channelId
     * @param model
     *            0:点播桌面;1儿童桌面;2音乐桌面;3搜索桌面;
     * @param commonParam
     *            通用参数
     * @param locale
     * @return
     */
    public Response<DeskListDto> GetDesk(Integer channelId, Integer model, CommonParam commonParam, Locale locale) {
        String logPrefix = "GetDesk_" + channelId + "_" + model + "_" + commonParam.getMac();
        Response<DeskListDto> response = new Response<DeskListDto>();
        String errorCode = null;
        String errorMsgCode = null;
        DeskListDto data = new DeskListDto();

        int validCode = this.assertValid(channelId, model);
        if (CommonMsgCodeConstant.REQUEST_SUCCESS != validCode) {
            errorCode = ErrorCodeConstants.DESK_ILLEGAL_PARAMETER;
            errorMsgCode = MessageUtils.getMessage(
                    ErrorMsgCodeUtil.parseErrorMsgCode(errorCode, BusinessCodeConstant.DESK, validCode), locale);
            response.setResultStatus(0);
            response.setErrCode(errorCode);
            response.setErrMsg(errorMsgCode);
            log.info(logPrefix + "[errorCode=" + errorCode + "]:.parameter illegal.");
        } else {
            List<DeskConfigMysqlTable> deskConfigMysqlTableList = this.facadeMysqlDao.getDeskConfigMysqlDao().getList(
                    model);
            // 服务端表配置数据不为空，填充到Dto
            if (deskConfigMysqlTableList != null && deskConfigMysqlTableList.size() > 0) {
                this.setDeskListDto(data, deskConfigMysqlTableList);
            }
            // Cms数据不为空，填充到Dto
            List<BaseData> baseDatalist = this.getDeskDataCms(channelId, commonParam);
            if (baseDatalist != null && baseDatalist.size() > 0) {
                this.setDeskListDtoCms(data, baseDatalist);
            }
        }
        response.setData(data);
        return response;
    }

    public void setDeskListDtoCms(DeskListDto data, List<BaseData> baseDatalist) {
        if (baseDatalist != null && baseDatalist.size() > 0) {
            List<DeskDto> fdtoData = new ArrayList<DeskDto>();
            if (data != null) {
                fdtoData = data.getFirstDeskData();
            }
            for (BaseData baseData : baseDatalist) {
                DeskDto deskDto = new DeskDto();
                DeskValueDto value = new DeskValueDto();
                if (baseData instanceof VideoDto) {
                    VideoDto video = (VideoDto) baseData;
                    deskDto.setAction(DeskConstants.DESK_ACTION);// TODO需要产品提供
                    deskDto.setTitle(video.getName());
                    deskDto.setSubTitle(video.getSubName());
                    deskDto.setType(DataConstant.DATA_TYPE_VIDEO);
                    deskDto.setImg(video.getImg());
                    deskDto.setSmall_img(video.getPic1());
                    deskDto.setModel(DeskConstants.DESK_MODEL_CHILDERN);// 标注那个桌面打洞过来的
                    value.setId(video.getVideoId());
                    value.setVideoname(video.getName());
                    String pid = video.getAlbumId();
                    if (pid != null && StringUtils.isNumeric(pid)) {
                        if (Integer.parseInt(pid) != 0) {
                            value.setAlbumid(pid);
                        }
                    }
                    if (video.getCategoryId() != null) {
                        value.setCategoryid(String.valueOf(video.getCategoryId()));
                    }
                    value.setIsvideo(true);
                    value.setType(1);// 固定传1
                    deskDto.setValue(value);
                    deskDto.setResource(DeskConstants.THROUGH_RESOURCE_DESK);
                    fdtoData.add(deskDto);
                } else if (baseData instanceof AlbumDto) {
                    AlbumDto album = (AlbumDto) baseData;
                    deskDto.setAction(DeskConstants.DESK_ACTION);// 需要产品提供
                    deskDto.setTitle(album.getName());
                    deskDto.setSubTitle(album.getSubName());
                    deskDto.setType(DataConstant.DATA_TYPE_VIDEO);// 儿童直接播放是视频type
                    deskDto.setImg(album.getImg());
                    deskDto.setSmall_img(album.getPic1());
                    deskDto.setModel(DeskConstants.DESK_MODEL_CHILDERN);// 标注那个桌面打洞过来的
                    // value.setId(album.getAlbumId());
                    value.setAlbumid(album.getAlbumId());// 客户端按照专辑id播放
                    if (album.getCategoryId() != null) {
                        value.setCategoryid(String.valueOf(album.getCategoryId()));
                    }
                    deskDto.setValue(value);
                    deskDto.setResource(DeskConstants.THROUGH_RESOURCE_DESK);
                    fdtoData.add(deskDto);
                } else if (baseData instanceof Subject) {
                    Subject subject = (Subject) baseData;
                    deskDto.setAction(DeskConstants.DESK_ACTION);// 需要产品提供
                    deskDto.setTitle(subject.getName());
                    deskDto.setSubTitle(subject.getSubName());
                    deskDto.setType(DataConstant.DATA_TYPE_SUBJECT);
                    deskDto.setImg(subject.getImg());
                    deskDto.setSmall_img(subject.getPic1());
                    deskDto.setModel(DeskConstants.DESK_MODEL_CHILDERN);// 标注那个桌面打洞过来的
                    value.setId(subject.getSubjectId());
                    value.setType(subject.getSubjectType());
                    deskDto.setValue(value);
                    deskDto.setResource(DeskConstants.THROUGH_RESOURCE_DESK);
                    fdtoData.add(deskDto);
                } else if (baseData instanceof BaseBlock) {
                    BaseBlock block = (BaseBlock) baseData;
                    String[] types = block.getExtendText().split("\\|");
                    deskDto.setAction(DeskConstants.DESK_ACTION);// 需要产品提供
                    deskDto.setTitle(block.getName());
                    deskDto.setSubTitle(block.getSubName());
                    deskDto.setImg(block.getImg());
                    deskDto.setSmall_img(block.getExtendImg());
                    deskDto.setModel(DeskConstants.DESK_MODEL_CHILDERN);// 标注那个桌面打洞过来的
                    deskDto.setType(block.getDataType());
                    if (types.length >= 3) {// 19|102|5|
                        value.setType(Integer.valueOf(types[2]));
                        if (types.length > 3) {
                            value.setId(types[3]);
                        }
                    }
                    deskDto.setValue(value);
                    deskDto.setResource(DeskConstants.THROUGH_RESOURCE_DESK);
                    fdtoData.add(deskDto);
                }
            }
            data.setFirstDeskData(fdtoData);
        }
    }

    public void setDeskListDto(DeskListDto data, List<DeskConfigMysqlTable> deskConfigMysqlTableList) {
        String logPrefix = "setDeskListDto" + "";
        // 第一屏数据
        List<DeskDto> fdtoData = new ArrayList<DeskDto>();
        // 第二屏数据
        List<DeskDto> sdtoData = new ArrayList<DeskDto>();
        for (DeskConfigMysqlTable deskConfigMysqlTable : deskConfigMysqlTableList) {
            DeskDto deskDto = new DeskDto();
            deskDto.setTitle(deskConfigMysqlTable.getTitle());
            deskDto.setSubTitle(deskConfigMysqlTable.getSubtitle());
            deskDto.setImg(deskConfigMysqlTable.getImg());
            deskDto.setSmall_img(deskConfigMysqlTable.getSmall_img());
            deskDto.setAction(deskConfigMysqlTable.getAction());
            deskDto.setType(deskConfigMysqlTable.getType());
            deskDto.setModel(deskConfigMysqlTable.getModel());
            DeskValueDto value = new DeskValueDto();
            // value部分在表中以json串存储
            String values = StringUtils.trimToNull(deskConfigMysqlTable.getValue());
            Map<String, String> valuesMap = null;
            if (values != null) {
                valuesMap = JsonUtil.parse(values, new TypeReference<Map<String, String>>() {
                });
            }
            if (valuesMap != null) {
                value.setId(valuesMap.get("id"));
                String type = valuesMap.get("type");
                if (StringUtils.isNotBlank(type)) {
                    value.setType(Integer.parseInt(type));
                }
                String albumid = valuesMap.get("albumid");
                if (StringUtils.isNotBlank(albumid)) {
                    value.setAlbumid(albumid);
                }
            }

            deskDto.setValue(value);
            // resource指打洞来源
            deskDto.setResource(deskConfigMysqlTable.getResource());
            Integer page = deskConfigMysqlTable.getPage();
            if (page != null && page == 1) {
                fdtoData.add(deskDto);
                log.info(logPrefix + "_page=" + page + ": get data from  table iptv.ITV_Desk_config.");
            } else {
                sdtoData.add(deskDto);
                log.info(logPrefix + "_page=" + page + ": get data from  table iptv.ITV_Desk_config.");
            }
        }
        data.setFirstDeskData(fdtoData);
        data.setSecondDeskData(sdtoData);
    }

    /**
     * 获取CMS数据
     * @param channelId
     * @param commonParam
     * @return
     */
    public List<BaseData> getDeskDataCms(Integer channelId, CommonParam commonParam) {
        String logPrefix = "getDeskDataCms_" + channelId + "_" + commonParam.getMac();
        List<ChannelDataMysqlTable> channelDataMysqlTableList = this.facadeMysqlDao.getChannelDataMysqlDao().getList(
                channelId);
        List<BaseData> dataList = new LinkedList<BaseData>();
        for (ChannelDataMysqlTable channelDataMysqlTable : channelDataMysqlTableList) {
            Integer dataSource = channelDataMysqlTable.getData_source();
            if (dataSource == null) {
                continue;
            }
            if (dataSource == ChannelConstants.DATASOURCE_CMS) {
                String url = channelDataMysqlTable.getData_url();
                // CMS板块数据
                CmsBlockTpResponse cmsBlockTpResponse = this.facadeTpDao.getCmsTpDao().getCmsBlockNew(url, commonParam);

                if (cmsBlockTpResponse != null) {
                    List<CmsBlockContentTpResponse> cmsBlockContentTpResponseList = cmsBlockTpResponse
                            .getBlockContent();
                    int count = 0;
                    Integer dataSize = channelDataMysqlTable.getData_preloadsize();
                    for (CmsBlockContentTpResponse cmsBlockContentTpResponse : cmsBlockContentTpResponseList) {
                        BaseData data = channelService.getDataFromCms(
                                cmsBlockContentTpResponse, commonParam, null, null, null);
                        if (data != null) {
                            dataList.add(data);
                            count++;
                        }
                        if (dataSize != null && dataSize.intValue() > 0 && dataSize.intValue() == count) {
                            break;
                        }
                    }
                    if (CollectionUtils.isEmpty(dataList)) {
                        log.info(logPrefix + "_" + url + ": parse empty data.");
                    }
                } else {
                    log.info(logPrefix + "_" + url + ": get cms block data failed.");
                }
            }
        }
        return dataList;
    }

    /**
     * 参数校验
     * @param channelId
     * @param model
     * @return
     */
    private int assertValid(Integer channelId, Integer model) {
        if (channelId == null) {
            return DeskMsgCodeConstant.DESK_REQUEST_CHANNELID_ERROR;
        }
        if (model == null) {
            return DeskMsgCodeConstant.DESK_REQUEST_MODEL_ERROR;
        }
        return DeskMsgCodeConstant.REQUEST_SUCCESS;
    }

}
