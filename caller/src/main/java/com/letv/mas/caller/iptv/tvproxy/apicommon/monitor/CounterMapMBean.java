package com.letv.mas.caller.iptv.tvproxy.apicommon.monitor;

import java.util.Map;

/**
 * @author guoyunfeng
 */
public interface CounterMapMBean {

    /**
     * 重置接口计数
     */
    void clear();

    /**
     * @return 接口访问计数
     */
    Map<String, Long> getCounterMap();
}
