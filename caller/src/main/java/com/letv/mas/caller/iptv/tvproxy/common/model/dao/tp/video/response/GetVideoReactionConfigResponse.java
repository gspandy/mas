package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.io.Serializable;
import java.util.Map;

public class GetVideoReactionConfigResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6899096654575862509L;

    /**
     * 互动后台配置了互动信息的专辑集合，其中key--专辑id，value--0或者1
     */
    private Map<String, String> pid;
    /**
     * 互动后台配置了互动信息的视频集合，其中key--专辑id，value--0或者1
     */
    private Map<String, String> vid;

    public Map<String, String> getPid() {
        return this.pid;
    }

    public void setPid(Map<String, String> pid) {
        this.pid = pid;
    }

    public Map<String, String> getVid() {
        return this.vid;
    }

    public void setVid(Map<String, String> vid) {
        this.vid = vid;
    }

}
