package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

/**
 * TV版每部定向弹窗配置信息封装类
 * @author KevinYi
 */
public class DPilotDto extends PilotDto {

    private String timeOut;

    private String nowTime;

    private PromotionDto.ReportData report;

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public PromotionDto.ReportData getReport() {
        return report;
    }

    public void setReport(PromotionDto.ReportData reportData) {
        this.report = reportData;
    }
}
