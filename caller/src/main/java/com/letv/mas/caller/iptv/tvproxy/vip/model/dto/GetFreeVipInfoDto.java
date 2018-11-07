package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

public class GetFreeVipInfoDto extends ShowInfoDto {

    private ShowInfoDto rule;// 白条规则
    private ShowInfoDto statement;// 免责声明

    public ShowInfoDto getRule() {
        return rule;
    }

    public void setRule(ShowInfoDto rule) {
        this.rule = rule;
    }

    public ShowInfoDto getStatement() {
        return statement;
    }

    public void setStatement(ShowInfoDto statement) {
        this.statement = statement;
    }

}
