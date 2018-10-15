package com.letv.mas.router.iptv.tvproxy.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * refer: http://docs.grafana.org/alerting/notifications/, [Webhook]
 * Created by leeco on 18/10/8.
 */
@ApiModel(value = "GrafanaNotifyDto", description = "Grafana通知参数集合")
public class GrafanaNotifyDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 669277880313271099L;

    @ApiModelProperty(value = "通知标题", required = false)
    private String title;
    @ApiModelProperty(value = "报警规则id", required = true)
    private String ruleId;
    @ApiModelProperty(value = "报警规则名称", required = false)
    private String ruleName;
    @ApiModelProperty(value = "报警详情地址", required = false)
    private String ruleUrl;
    @ApiModelProperty(value = "报警状态", required = true, allowableValues = "")
    private String state;
    @ApiModelProperty(value = "报警截图", required = false)
    private String imageUrl;
    @ApiModelProperty(value = "报警描述", required = false)
    private String message;
    @ApiModelProperty(value = "报警评估指标", required = true)
    private List<GrafanaNotifyDto.EvalMatche> evalMatches;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleUrl() {
        return ruleUrl;
    }

    public void setRuleUrl(String ruleUrl) {
        this.ruleUrl = ruleUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<EvalMatche> getEvalMatches() {
        return evalMatches;
    }

    public void setEvalMatches(List<EvalMatche> evalMatches) {
        this.evalMatches = evalMatches;
    }

    @ApiModel(value = "EvalMatche", description = "评估指标")
    public static class EvalMatche extends BaseDto implements Serializable {

        private static final long serialVersionUID = -7940240032627451474L;

        @ApiModelProperty(value = "评估项", required = true)
        private String metric;
        // private Map tags;
        @ApiModelProperty(value = "评估值", required = false)
        private String value;

        public String getMetric() {
            return metric;
        }

        public void setMetric(String metric) {
            this.metric = metric;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
