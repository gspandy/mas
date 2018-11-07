package com.letv.mas.caller.iptv.tvproxy.common.model.dto.live;

import java.io.Serializable;
import java.util.List;

public class LiveDto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -780175904163610756L;
    private List<LiveProgram> programs;

    /**
     * 刷新单场或若干场直播实时信息的时间间隔（用户点击预告，节目单手动刷新时间间），这段时间内的所有刷新请求只执行一次；该逻辑由客户端执行，
     * 服务端只返回时间值，单位毫秒
     */
    private Long interval;

    /**
     * 当前数据刷新模式，0--非实时数据，不保证数据从直播后台实时获取，1--实时数据，数据从直播后台实时获取
     */
    private Integer flushMode;

    public List<LiveProgram> getPrograms() {
        return this.programs;
    }

    public void setPrograms(List<LiveProgram> programs) {
        this.programs = programs;
    }

    public Long getInterval() {
        return this.interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public Integer getFlushMode() {
        return this.flushMode;
    }

    public void setFlushMode(Integer flushMode) {
        this.flushMode = flushMode;
    }

}
