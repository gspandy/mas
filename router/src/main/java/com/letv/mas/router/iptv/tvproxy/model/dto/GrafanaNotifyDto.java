package com.letv.mas.router.iptv.tvproxy.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * refer: http://docs.grafana.org/alerting/notifications/, [Webhook]
 * Created by leeco on 18/10/8.
 */
public class GrafanaNotifyDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 669277880313271099L;
    private String title;
    private String ruleId;
    private String ruleName;
    private String ruleUrl;
    private String state;
    private String imageUrl;
    private String message;
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

    public static class EvalMatche extends BaseDto implements Serializable {

        private static final long serialVersionUID = -7940240032627451474L;
        private String metric;
        // private Map tags;
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
