package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.util.Map;

/**
 * 视频播放时的会员引导信息封装类
 * @author KevinYi
 */
public class VideoPlayConsumeGuideDto extends BaseDto {

    /**
     * 会员引导信息Map，key--会员引导信息记录的id，value--会员引导信息
     */
    private Map<String, VideoPlayConsumeGuideInfoDto> data;

    public Map<String, VideoPlayConsumeGuideInfoDto> getData() {
        return this.data;
    }

    public void setData(Map<String, VideoPlayConsumeGuideInfoDto> data) {
        this.data = data;
    }

}
