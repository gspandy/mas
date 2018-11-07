package com.letv.mas.caller.iptv.tvproxy.terminal.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@XmlRootElement
public class ResultsDto extends BaseDto {
    private static final long serialVersionUID = 7507886246559215170L;

    private String status = CommonConstants.RESPONSE_STATUS_FAILURE;
    private String message = CommonConstants.RESPONSE_MESSAGE_FAILURE;
    private Map<String, String> data = new LinkedHashMap<String, String>();
    private Map<String, String> staticUrl = new HashMap<String, String>();

    {
        this.data.put(CommonConstants.RESPONSE_KEY_STATUS, CommonConstants.RESPONSE_STATUS_FAILURE);
        this.data.put(CommonConstants.RESPONSE_KEY_MESSAGE, CommonConstants.RESPONSE_MESSAGE_FAILURE);
        this.data.put(CommonConstants.RESPONSE_KEY_TERMINALUUID, "aaa");
        this.data.put(CommonConstants.RESPONSE_KEY_USERNAME, "");
        this.data.put(CommonConstants.RESPONSE_KEY_IDENTIFYCODE, "aaa");
        this.data.put(CommonConstants.RESPONSE_KEY_VERSIONURL, "");
        this.data.put(CommonConstants.RESPONSE_KEY_VERSIONNAME, "");
        this.data.put(CommonConstants.RESPONSE_KEY_VERSIONID, "");
        this.data.put(CommonConstants.RESPONSE_KEY_OTHER, "");
        this.data.put(CommonConstants.RESPONSE_KEY_DESCRIPTION, "");
        this.data.put(CommonConstants.RESPONSE_KEY_PLAYFORMATISTS, "true");
        this.data.put(CommonConstants.RESPONSE_KEY_BROADCASTID, "1");
        this.data.put(CommonConstants.RESPONSE_KEY_CONFIG, "");
        this.data.put(CommonConstants.RESPONSE_KEY_ROM_MINIMUM, "");
        this.data.put(CommonConstants.RESPONSE_KEY_CURROM_MINIMUM, "");
        this.data.put(CommonConstants.RESPONSE_KEY_PUBLISH_TIME, "");
        this.data.put(CommonConstants.RESPONSE_KEY_POJIESTATUS, "0");
    }

    public Map<String, String> getData() {
        return this.data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getStaticUrl() {
        return staticUrl;
    }

    public void setStaticUrl(Map<String, String> staticUrl) {
        this.staticUrl = staticUrl;
    }

}
