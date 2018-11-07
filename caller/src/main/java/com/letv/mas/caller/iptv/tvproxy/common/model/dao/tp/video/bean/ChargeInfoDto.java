package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Author：apple on 17/9/1 10:44
 * eMail：dengliwei@le.com
 */
@ApiModel(value = "ChargeInfoDto", description = "分端付费参数集")
public class ChargeInfoDto {

    /**
     * 终端类型（前端）
     */
    @ApiModelProperty(value = "终端类型（前端），1手机，2电视（自有），3pad，4汽车，5盒子（自有），6北美TV（非自有），7其它第三方。")
    private Integer devType;

    /**
     * 付费平台id（BOSS）
     */
    @ApiModelProperty(value = "付费平台id（BOSS）, 141007:TV自有版, 141010:第三方市场, 141011:盒子自有版")
    private String platForm;

    /**
     * 是否收费：1，免费：0
     */
    @ApiModelProperty(value = "是否收费(BOSS), 收费：1，免费：0")
    private String isCharge = "0";

    /**
     * 付费类型(BOSS) 0：点播 1：点播和包月 2：包月，3：免费但TV包月收费
     */
    @ApiModelProperty(value = "付费类型(BOSS), 0：点播 1：点播和包月 2：包月，3：免费但TV包月收费")
    private String payType = "0";

    /**
     * 是否支持观影券。0：不支持, 1：支持
     */
    @ApiModelProperty(value = "是否支持观影券(BOSS), 0：不支持, 1：支持")
    private String iscoupon = "0";

    /**
     * 角标类型（前端）
     */
    @ApiModelProperty(value = "角标类型（前端）,0:无角标, 1:全网(外网), 1:全网(外网), 3:会员, 4:独播, 5:自制, 6:专题, 7:预告, 8:4K, 9:2K, 10:1080P, 11:影院音, 12:花絮, 13:直播, 14:3d, 15:杜比, 16:按影视分类catagory, 17:显示app下载(客户端判断是否已安装), 18:tvod, 19:限时免费, 20:家庭会员")
    private String iconType;

    /**
     * 付费类型(前端)：ChargeTypeConstants.chargePolicy
     */
    @ApiModelProperty(value = "付费类型(前端), 1:免费, 2:按内容收费, 3:小窗试看(电视剧、动漫非第一集), 4:院线收费, 5:按码流收费, 6:按会员购买收费, 7:因内容未设置付费策略，提示无版权观看，美国Le.com使用")
    private Integer chargeType;

    // /**
    // * 限时免费(前端)：null, 0: 不免费，1: 免费
    // */
    // @ApiModelProperty(value = "限时免费(前端)：null, 0: 不免费，1: 免费")
    // private Integer isTempFree;

    public Integer getDevType() {
        return devType;
    }

    public void setDevType(Integer devType) {
        this.devType = devType;
    }

    public String getPlatForm() {
        return platForm;
    }

    public void setPlatForm(String platForm) {
        this.platForm = platForm;
    }

    public String getIsCharge() {
        return isCharge;
    }

    public void setIsCharge(String isCharge) {
        this.isCharge = isCharge;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getIscoupon() {
        return iscoupon;
    }

    public void setIscoupon(String iscoupon) {
        this.iscoupon = iscoupon;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    // public Integer getIsTempFree() {
    // return isTempFree;
    // }
    //
    // public void setIsTempFree(Integer isTempFree) {
    // this.isTempFree = isTempFree;
    // }
}
