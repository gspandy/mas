package com.letv.mas.caller.iptv.tvproxy.common.config;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.TpServiceStatus;
import com.letv.mas.caller.iptv.tvproxy.common.util.HttpClientUtil;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.lang.NonNullApi;
import io.micrometer.core.lang.NonNullFields;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

import static java.util.Collections.emptyList;

@NonNullApi
@NonNullFields
public class CustomMetrics implements MeterBinder {

    private static final Logger logger = Logger.getLogger(CustomMetrics.class);

    private Iterable<Tag> tags;

    //private static final Map<String, Gauge> tpServerGaugeMap = new HashMap<>();
    private Set nodes = new HashSet();

    private MeterRegistry registry;

    public CustomMetrics() {
        this(emptyList());
    }

    CustomMetrics(Iterable<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public void bindTo(MeterRegistry registry) {
        this.registry = registry;
        scheduledBind();
    }
    @Scheduled(fixedDelayString = "${management.metrics.export.delay:10000}")
    public void scheduledBind(){
        if (registry == null) {
            return;
        }
        Map<String, TpServiceStatus> tpServerStatusMap = HttpClientUtil.getServerStatusMap();
        if (tpServerStatusMap.isEmpty()) {
            return;
        }
        logger.info(" ---CustomMetrics scheduledBind size--- "+tpServerStatusMap.size());
        Iterator<Map.Entry<String, TpServiceStatus>> entries = tpServerStatusMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, TpServiceStatus> entry = entries.next();
            String key = entry.getKey();
            if (key == null) {
                continue;
            }
            TpServiceStatus tpServiceStatus = entry.getValue();
            if(tpServiceStatus == null){
                continue;
            }
            if (!nodes.contains(key)) {
                logger.info(" ---CustomMetrics scheduledBind key--- "+key);
                Gauge.builder("net_uptime", tpServiceStatus, TpServiceStatus::getResponseTime).tag("uri",key).register(registry);
                //Gauge.builder("net_count", tpServiceStatus, TpServiceStatus::getCount).tag("uri",key).register(registry);
                nodes.add(key);
            }
        }
    }
}
