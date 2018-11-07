package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

/**
 * 码流相关
 */
public class VideoStream {
    Integer FILM_1080P = 1080;
    Integer FILM_720 = 720;

    // String stream code.
    String CODE_1000 = "1000";
    String CODE_350 = "350";

    private String name;
    private String code;
    private String ifgetDown = "0";
    private String enabled = "1";// 可用
    private String ifCharge = "1";// 是否收费:0 免费，1 收费
    private String ifCanDown;
    private String kbps;

    public String getKbps() {
        return this.kbps;
    }

    public void setKbps(String kbps) {
        this.kbps = kbps;
    }

    public String getIfCanDown() {
        return this.ifCanDown;
    }

    public void setIfCanDown(String ifCanDown) {
        this.ifCanDown = ifCanDown;
    }

    public String getIfCharge() {
        return this.ifCharge;
    }

    public void setIfCharge(String ifCharge) {
        this.ifCharge = ifCharge;
    }

    public String getEnabled() {
        return this.enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getIfgetDown() {
        return this.ifgetDown;
    }

    public void setIfgetDown(String ifgetDown) {
        this.ifgetDown = ifgetDown;
    }

    public VideoStream() {
    }

    public VideoStream(String code) {
        this.code = code;
        if (VideoStreamCode.CODE_NAME_350.equalsIgnoreCase(code)) {
            this.name = "流畅";
        } else if (VideoStreamCode.CODE_NAME_1000.equalsIgnoreCase(code)) {
            this.name = "标清";
        } else if (VideoStreamCode.CODE_NAME_1300.equalsIgnoreCase(code)) {
            this.name = "高清";
        } else if (VideoStreamCode.CODE_NAME_720p.equalsIgnoreCase(code)) {
            this.name = "超清";
        } else if (VideoStreamCode.CODE_NAME_1080p6m.equalsIgnoreCase(code)) {
            this.name = "1080P";
        } else if (VideoStreamCode.CODE_NAME_3d720p.equalsIgnoreCase(code)) {
            this.name = "3D超清";
        } else if (VideoStreamCode.CODE_NAME_3d1080p6M.equalsIgnoreCase(code)) {
            this.name = "3D1080P";
        } else if (VideoStreamCode.CODE_NAME_1080p.equalsIgnoreCase(code)) {
            this.name = "1080P";
        } else if (VideoStreamCode.CODE_NAME_3d1080p.equalsIgnoreCase(code)) {
            this.name = "3D1080P";
        } else if (VideoStreamCode.CODE_NAME_DOLBY_1000.equalsIgnoreCase(code)) {
            this.name = "杜比标清";
        } else if (VideoStreamCode.CODE_NAME_DOLBY_1300.equalsIgnoreCase(code)) {
            this.name = "杜比高清";
        } else if (VideoStreamCode.CODE_NAME_DOLBY_720p.equalsIgnoreCase(code)) {
            this.name = "杜比超清";
        } else if (VideoStreamCode.CODE_NAME_DOLBY_1080p.equalsIgnoreCase(code)) {
            this.name = "杜比1080P";
        }
    }

    public VideoStream(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
}