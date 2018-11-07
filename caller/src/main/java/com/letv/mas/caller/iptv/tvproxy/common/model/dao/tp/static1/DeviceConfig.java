package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * Author：apple on 17/6/20 13:15
 * eMail：dengliwei@le.com
 * {
 * "type":"u4s",
 * "name":"DEVICE_U4S",
 * "keyCode":-1,
 * "letvPlayView":"C1",
 * "isLetvBox":true,
 * "isBufferPolicyForBox":false,
 * "is3DPermitted":true,
 * "is4KPermitted":true,
 * "isAnimationPermitted":true,
 * "isAudioCtrlPermitted":false,
 * "isSupportFirstSeek":false,
 * "isSupportPreBuffering":true,
 * "isSupportDolbyVision":true,
 * "isSupportExo":true
 * },
 */
@ApiModel(value = "DeviceConfig", description = "设备配置信息")
public class DeviceConfig implements Serializable {
    private String type;
    private String name;
    private String keyCode;
    private String letvPlayView;

    @JsonProperty("isLetvBox")
    private boolean bIsLetvBox;
    @JsonProperty("isBufferPolicyForBox")
    private boolean bIsBufferPolicyForBox;
    @JsonProperty("is3DPermitted")
    private boolean bIs3DPermitted;
    @JsonProperty("is4KPermitted")
    private boolean bIs4KPermitted;
    @JsonProperty("isAnimationPermitted")
    private boolean bIsAnimationPermitted;
    @JsonProperty("isAudioCtrlPermitted")
    private boolean bIsAudioCtrlPermitted;
    @JsonProperty("isSupportFirstSeek")
    private boolean bIsSupportFirstSeek;
    @JsonProperty("isSupportPreBuffering")
    private boolean bIsSupportPreBuffering;
    @JsonProperty("isSupportDolbyVision")
    private boolean bIsSupportDolbyVision;
    @JsonProperty("isSupportExo")
    private boolean bIsSupportExo;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public String getLetvPlayView() {
        return letvPlayView;
    }

    public void setLetvPlayView(String letvPlayView) {
        this.letvPlayView = letvPlayView;
    }

    public boolean isbIsLetvBox() {
        return bIsLetvBox;
    }

    public void setbIsLetvBox(boolean bIsLetvBox) {
        this.bIsLetvBox = bIsLetvBox;
    }

    public boolean isbIsBufferPolicyForBox() {
        return bIsBufferPolicyForBox;
    }

    public void setbIsBufferPolicyForBox(boolean bIsBufferPolicyForBox) {
        this.bIsBufferPolicyForBox = bIsBufferPolicyForBox;
    }

    public boolean isbIs3DPermitted() {
        return bIs3DPermitted;
    }

    public void setbIs3DPermitted(boolean bIs3DPermitted) {
        this.bIs3DPermitted = bIs3DPermitted;
    }

    public boolean isbIs4KPermitted() {
        return bIs4KPermitted;
    }

    public void setbIs4KPermitted(boolean bIs4KPermitted) {
        this.bIs4KPermitted = bIs4KPermitted;
    }

    public boolean isbIsAnimationPermitted() {
        return bIsAnimationPermitted;
    }

    public void setbIsAnimationPermitted(boolean bIsAnimationPermitted) {
        this.bIsAnimationPermitted = bIsAnimationPermitted;
    }

    public boolean isbIsAudioCtrlPermitted() {
        return bIsAudioCtrlPermitted;
    }

    public void setbIsAudioCtrlPermitted(boolean bIsAudioCtrlPermitted) {
        this.bIsAudioCtrlPermitted = bIsAudioCtrlPermitted;
    }

    public boolean isbIsSupportFirstSeek() {
        return bIsSupportFirstSeek;
    }

    public void setbIsSupportFirstSeek(boolean bIsSupportFirstSeek) {
        this.bIsSupportFirstSeek = bIsSupportFirstSeek;
    }

    public boolean isbIsSupportPreBuffering() {
        return bIsSupportPreBuffering;
    }

    public void setbIsSupportPreBuffering(boolean bIsSupportPreBuffering) {
        this.bIsSupportPreBuffering = bIsSupportPreBuffering;
    }

    public boolean isbIsSupportDolbyVision() {
        return bIsSupportDolbyVision;
    }

    public void setbIsSupportDolbyVision(boolean bIsSupportDolbyVision) {
        this.bIsSupportDolbyVision = bIsSupportDolbyVision;
    }

    public boolean isbIsSupportExo() {
        return bIsSupportExo;
    }

    public void setbIsSupportExo(boolean bIsSupportExo) {
        this.bIsSupportExo = bIsSupportExo;
    }
}
