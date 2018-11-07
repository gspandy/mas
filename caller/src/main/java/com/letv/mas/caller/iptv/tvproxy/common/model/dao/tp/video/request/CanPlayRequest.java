package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.request;

@Deprecated
public class CanPlayRequest {
    private Long productId;// 专辑Id
    private String username;// 用户名
    private Integer terminal;//
    private Boolean isFromCntv;
    private Boolean isFromCibn;
    private String cntvMac;
    private String storePath;
    private String deviceKey;

    public CanPlayRequest() {
    }

    public CanPlayRequest(Long productId, String username, Integer terminal, Boolean isFromCntv, String cntvMac,
            Boolean isFromCibn, String storePath, String deviceKey) {
        this.productId = productId;
        this.username = username;
        this.terminal = terminal;
        this.isFromCntv = isFromCntv;
        this.cntvMac = cntvMac;
        this.isFromCibn = isFromCibn;
        this.storePath = storePath;
        this.deviceKey = deviceKey;
    }

    public Boolean getIsFromCibn() {
        return isFromCibn;
    }

    public void setIsFromCibn(Boolean isFromCibn) {
        this.isFromCibn = isFromCibn;
    }

    /**
     * @return the productId
     */
    public Long getProductId() {
        return this.productId;
    }

    /**
     * @param productId
     *            the productId to set
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the terminal
     */
    public Integer getTerminal() {
        return this.terminal;
    }

    /**
     * @param terminal
     *            the terminal to set
     */
    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }

    public Boolean getIsFromCntv() {
        return this.isFromCntv == null ? Boolean.FALSE : this.isFromCntv;
    }

    public void setIsFromCntv(Boolean isFromCntv) {
        this.isFromCntv = isFromCntv;
    }

    public String getCntvMac() {
        return this.cntvMac;
    }

    public void setCntvMac(String cntvMac) {
        this.cntvMac = cntvMac;
    }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    @Override
    public String toString() {
        return "CanPlayRequest [productId=" + this.productId + ", username=" + this.username + ", terminal="
                + this.terminal + ", isFromCntv=" + this.isFromCntv + ", cntvMac=" + this.cntvMac + "]";
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

}
