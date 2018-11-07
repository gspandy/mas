package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.util.List;
import java.util.Map;

/**
 * 向boss查询专辑付费信息结果封装类
 * @author KevinYi
 */
public class GetAlbumChargeInfoTpResponse {

    /**
     * 专辑名称
     */
    private String albumName;

    /**
     * 是否收费，0--不收费，1--收费
     */
    private Integer ischarge;

    /**
     * 专辑状态，1--为正常
     */
    private Integer status;

    /**
     * 收费类型，0.点播类型， 1.点播且包月，点播和包月类型都可以看 2包月只有包月类型才可以看 3. 免费，但tv收看收费？？？
     * 0--点播；非会员购买，会员用券；1--点播且包月；仅会员可以观看，且需使用观影券；2--包月；仅会员可以观看，但无需使用观影券；3--
     * 免费但TV包月收费；
     */
    private Integer chargetype;

    /**
     * 点播有效天数，单位-天
     */
    private Integer validDays;

    /**
     * 频道id，媒资推动到TV端的，即categary
     */
    private Integer contentType;

    /**
     * 付费标准，key--boss定义的付费平台编号，如141007--TV端；value--价格；
     * 注意，key值取值范围在boss后台“单片价格管理”中决定
     */
    private Map<String, String> chargeflatform;

    /**
     * 付费平台列表，取值在boss后台“影视剧管理”--“编辑影片价格”编辑也中的“收费平台”配置项勾选决定，如141007--TV端
     */
    private List<Integer> terminals;

    public String getAlbumName() {
        return this.albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Integer getIscharge() {
        return this.ischarge;
    }

    public void setIscharge(Integer ischarge) {
        this.ischarge = ischarge;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getChargetype() {
        return this.chargetype;
    }

    public void setChargetype(Integer chargetype) {
        this.chargetype = chargetype;
    }

    public Integer getValidDays() {
        return this.validDays;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public Integer getContentType() {
        return this.contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Map<String, String> getChargeflatform() {
        return this.chargeflatform;
    }

    public void setChargeflatform(Map<String, String> chargeflatform) {
        this.chargeflatform = chargeflatform;
    }

    public List<Integer> getTerminals() {
        return this.terminals;
    }

    public void setTerminals(List<Integer> terminals) {
        this.terminals = terminals;
    }

}
