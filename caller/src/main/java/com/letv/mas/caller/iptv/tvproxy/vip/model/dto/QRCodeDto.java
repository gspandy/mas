package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

/**
 * 获取二维码DTO
 * @author dunhongqin
 */

public class QRCodeDto extends BaseDto {

    /**
     * 二维码内容
     */
    private String qrcode;

    /**
     * 过期时间，单位秒
     */
    private String expire;

    /**
     * 抽奖二维码说明文案
     */
    private String qrcodeText;

    /**
     * 标题里的图标，展示在文案之前
     */
    private String titleIcon;

    public String getQrcode() {
        return this.qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getExpire() {
        return this.expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getQrcodeText() {
        return this.qrcodeText;
    }

    public void setQrcodeText(String qrcodeText) {
        this.qrcodeText = qrcodeText;
    }

    public String getTitleIcon() {
        return this.titleIcon;
    }

    public void setTitleIcon(String titleIcon) {
        this.titleIcon = titleIcon;
    }

}
