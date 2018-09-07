package com.letv.mas.caller.iptv.tvproxy.user.plugin;

/**
 * 第三方服务状态
 */
public class TpServiceStatus {

    /**
     * 开始计数时间
     */
    private long recordStartTime;

    /**
     * 总请求次数
     */
    private int count;

    /**
     * 总响应时间
     */
    private long totalResponseTime;

    /**
     * 最近一次关闭时间
     */
    private long closeTime;

    /**
     * 是否正在检查
     */
    private Boolean checking = false;

    public TpServiceStatus() {
        this.recordStartTime = System.currentTimeMillis();
        this.count = 0;
        this.totalResponseTime = 0;
        this.closeTime = 0;
        this.checking = false;
    }

    public long getRecordStartTime() {
        return recordStartTime;
    }

    public void setRecordStartTime(long recordStartTime) {
        this.recordStartTime = recordStartTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTotalResponseTime() {
        return totalResponseTime;
    }

    public long getResponseTime() {
        if (count < 1) {
            return 0;
        }
        long avgTime = totalResponseTime / count;
        return avgTime;
    }

    public void setTotalResponseTime(long totalResponseTime) {
        this.totalResponseTime = totalResponseTime;
    }

    public long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
    }

    public Boolean getChecking() {
        return checking;
    }

    public void setChecking(Boolean checking) {
        this.checking = checking;
    }
}
