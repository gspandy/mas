package com.letv.mas.caller.iptv.tvproxy.apicommon.monitor;

import com.google.common.util.concurrent.AtomicLongMap;

import java.util.*;

/**
 * @author guoyunfeng
 */
public class CounterMapImp implements CounterMapMBean {

    public final AtomicLongMap<String> counterMap;

    public CounterMapImp(AtomicLongMap<String> counterMap) {
        this.counterMap = counterMap;
    }

    @Override
    public void clear() {
        counterMap.clear();
    }

    @Override
    public Map<String, Long> getCounterMap() {
        List<Map.Entry<String, Long>> entryList = new ArrayList<>(counterMap.asMap().entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                Long v1 = o1.getValue();
                Long v2 = o2.getValue();
                if (v1 > v2) {
                    return -1;
                } else if (v1 < v2) {
                    return 1;
                } else {
                    return o1.getKey().compareTo(o2.getKey());
                }
            }
        });
        Map<String, Long> resultMap = new LinkedHashMap<>();
        for (Map.Entry<String, Long> entry : entryList) {
            resultMap.put(entry.getKey(), entry.getValue());
        }
        return resultMap;
    }
}
