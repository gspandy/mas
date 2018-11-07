package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video;

import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.BaseTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.SoundInkCodeTp;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("v2.SoundInkTpDao")
public class SoundInkTpDao extends BaseTpDao {

    private final static Logger log = LoggerFactory.getLogger(SoundInkTpDao.class);

    /**
     * 声纹接口
     */
    private static final String getSoundInkUrl() {
        return "https://cnapi.soundink.net/b/v1/soundink_codes/apply";
    }

    public SoundInkCodeTp getSoundInkCode(Long videoId, String videoName) {

        SoundInkCodeTp soundInkCodeTp = null;
        try {
            Map<String, Object> multiMap = new HashMap<String, Object>();
            multiMap.put("app_key", "4f7ad46a881a46ca7366e5c47a4dbfcf");
            multiMap.put("video_id", videoId); // id of video program
            multiMap.put("title", videoName); // [optional] title of program
            // SoundInkCodeRequest request = new SoundInkCodeRequest();
            // request.setVideo_id(videoId);
            // request.setTitle(videoName);

            String response = this.restTemplate.postForObject(getSoundInkUrl(), multiMap, String.class);
            log.info("[url]:" + getSoundInkUrl() + "[params]:" + "[video_id]" + videoId + "[videoName]" + videoName
                    + "[result]:" + response);
            if (StringUtil.isNotBlank(response)) {
                soundInkCodeTp = objectMapper.readValue(response, SoundInkCodeTp.class);
            }
        } catch (Exception e) {
            log.error("[url]:" + getSoundInkUrl() + "[params]:" + "[video_id]" + videoId + "[videoName]" + videoName
                    + "[Exception]" + e);
        }

        return soundInkCodeTp;
    }
}
