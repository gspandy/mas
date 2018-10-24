package com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift;


import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThriftServiceStatus {

    private static Log serviceStatusLoger = LogFactory.getLog("serviceStatusLoger");
    private static Log apiSwitchMonitor = LogFactory.getLog("apiSwitchMonitorLog");

    private static final int INTERFACE_TOTAL_COUNT = 30;// 服务访问次数达到该值进行切服计算
    private static final long INTERFACE_RECORD_COUNT_RESET_TIME = 1 * 60 * 1000;// 服务统计量重置时间间隔
    private static final long INTERFACE_AUTO_NORMAL_TIME = 5 * 60 * 1000;// 接口自动恢复时间5分钟

    /**
     * 第三方服务异常次数
     */
    private int count;

    /**
     * 第三方服务开始计数时间
     */
    private long recordStartTime;

    /**
     * 第三方服务最近一次关闭时间
     */
    private long closeTime;

    private Lock lock;

    public ThriftServiceStatus() {
        this.recordStartTime = System.currentTimeMillis();
        this.count = 0;
        this.closeTime = 0;
        this.lock = new ReentrantLock();
    }

    /**
     * 在切服时间内，服务处于不可用状态
     * @return
     */
    public boolean ifServiceUsable() {
        return (System.currentTimeMillis() - this.closeTime) > INTERFACE_AUTO_NORMAL_TIME;
    }

    public void checkThriftServiceStatus(String serviceName) {
        this.lock.lock();
        try {
            this.count++;

            serviceStatusLoger.info("[this service " + serviceName + "]" + "  has exceptions. count:[" + this.count
                    + "] recordStartTime:[" + TimeUtil.timestamp2date(recordStartTime) + "] nowTime:["
                    + TimeUtil.timestamp2date(System.currentTimeMillis()) + "]");
            // 服务异常次数 超过统计阀值时 进行 切服
            if (this.count >= INTERFACE_TOTAL_COUNT) {
                this.closeTime = System.currentTimeMillis();// 更新关闭时间
                this.count = 0;// 切服后计数归0
                this.recordStartTime = System.currentTimeMillis();
                apiSwitchMonitor.info("[" + serviceName + "]" + " close time:["
                        + TimeUtil.timestamp2date(this.closeTime) + "]" + "average response time:[10000]");
            }
            // 1分钟后 重置计数
            if ((System.currentTimeMillis() - recordStartTime) > INTERFACE_RECORD_COUNT_RESET_TIME) {
                this.count = 0;
                this.recordStartTime = System.currentTimeMillis();
            }
        } finally {
            this.lock.unlock();
        }
    }

}
