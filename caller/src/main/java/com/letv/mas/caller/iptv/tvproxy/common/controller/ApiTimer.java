package com.letv.mas.caller.iptv.tvproxy.common.controller;

import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.HttpClientUtil;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ApiTimer {

    /**
     * 每晚0点，定时向各log文件生成一条log记录,目的是能切换log文件名.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void printLogForSplitLogFile() {
        String splitLogInfo = "this log for splitting log file.";
        // 向apiSwitchMonitor定时添加一个log
        LoggerFactory.getLogger("apiSwitchMonitorLog").info(splitLogInfo);
        // 向server-api.log定时添加一个log
        LoggerFactory.getLogger(this.getClass()).info(splitLogInfo);
        // 向httpClient.log定时添加一个log
        LoggerFactory.getLogger(HttpClientUtil.class).info(splitLogInfo);
        // 向slowTime.log定时添加一个log
        LoggerFactory.getLogger("slowLog").info(splitLogInfo);
        // 向errorLog.log定时添加一个log
        LoggerFactory.getLogger("errorLog").info(splitLogInfo);
    }

    /**
     * 每隔30秒，定时扫描data.conf目录下的配置文件，是否被更新
     */
    @Scheduled(fixedDelay = 30 * 1000)
    public void detectReloadableProperties() {
        LoggerFactory.getLogger(this.getClass()).debug("Start detecting if there any modified property files");
        ApplicationUtils.updateReloadableProperties();
    }
}
