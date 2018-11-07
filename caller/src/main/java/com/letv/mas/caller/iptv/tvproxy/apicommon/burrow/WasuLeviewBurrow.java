package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow;

import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto.WasuDto;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseBlock;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.AlbumDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WasuLeviewBurrow extends AbstractBurrow {

    public WasuLeviewBurrow() {
    }

    public WasuLeviewBurrow(BurrowUtil burrowUtil) {
        super(burrowUtil);
    }

    @Override
    public BaseData burrowObj(BaseData data, CommonParam commonParam) {
        if (data instanceof AlbumDto) {
            AlbumDto album = (AlbumDto) data;
            WasuDto wDto = new WasuDto();
            String external_play_id = album.getExternal_play_id();
            if (!StringUtil.isBlank(external_play_id)) {
                String regEx_catId = "\"catId\":[a-zA-Z0-9]*";
                String regEx_assetId = "\"assetId\":[a-zA-Z0-9]*";

                Pattern pat_assetId = Pattern.compile(regEx_assetId);
                String assetId = "";
                Matcher mMatcher_assetId = pat_assetId.matcher(external_play_id);
                while (mMatcher_assetId.find()) {
                    assetId = mMatcher_assetId.group(0);
                    if (!StringUtil.isBlank(assetId)) {
                        break;
                    }
                }
                Pattern pat_catId = Pattern.compile(regEx_catId);
                String catId = "";
                Matcher mMatcher = pat_catId.matcher(external_play_id);
                while (mMatcher.find()) {
                    catId = mMatcher.group(0);
                    if (!StringUtil.isBlank(catId)) {
                        break;
                    }
                }
                if (!StringUtil.isBlank(catId)) {
                    wDto.setCategoryid(catId.substring(catId.indexOf(":") + 1));
                }
                if (!StringUtil.isBlank(assetId)) {
                    wDto.setId(assetId.substring(assetId.indexOf(":") + 1));
                }
                album.setExternal_play_id(null);
            }
            BurrowUtil.buildBurrowV2(album, BurrowUtil.BurrowObj.WASU_ALBUM, wDto, commonParam);
        } else if (data instanceof BaseBlock) {
            BaseBlock block = (BaseBlock) data;
            BurrowUtil.buildBurrowV2(data, BurrowUtil.BurrowObj.WASU_COMMON, block.getAppParam(), commonParam);
            block.setAppCode(null);
            block.setAppParam(null);
            block.setVipFlag(null);
            block.setExtendImg(null);
            block.setButtonImg(null);
        }
        return data;
    }
}
