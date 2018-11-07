package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow;

import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.CibnSdkDto.CibnAlbumBurrowDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.BurrowUtil.BurrowObj;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.bean.bo.Subject;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseBlock;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.AlbumDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.VideoDto;

public class CibnLeviewBurrow extends AbstractBurrow {

    public CibnLeviewBurrow() {
    }

    public CibnLeviewBurrow(BurrowUtil burrowUtil) {
        super(burrowUtil);
    }

    @Override
    public BaseData burrowObj(BaseData data, CommonParam commonParam) {
        if (data instanceof AlbumDto) {
            AlbumDto album = (AlbumDto) data;
            CibnAlbumBurrowDto cibnAlbum = new CibnAlbumBurrowDto();
            cibnAlbum.setAlbumId(album.getExternal_id());
            BurrowUtil.buildBurrowV2(data, BurrowUtil.BurrowObj.CIBN_ALBUM, cibnAlbum, commonParam);
        } else if (data instanceof VideoDto) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.CIBN_VIDEO, null, commonParam);
        } else if (data instanceof Subject) {
            BurrowUtil.buildBurrowV2(data, BurrowObj.CIBN_SUBJECT, null, commonParam);
        } else if (data instanceof BaseBlock) {
            BaseBlock block = (BaseBlock) data;
            BurrowUtil.buildBurrowV2(data, BurrowObj.CIBN_COMMON, block.getAppParam(), commonParam);
            block.setAppCode(null);
            block.setAppParam(null);
            block.setVipFlag(null);
            block.setExtendImg(null);
            block.setButtonImg(null);
        }
        return data;
    }
}
