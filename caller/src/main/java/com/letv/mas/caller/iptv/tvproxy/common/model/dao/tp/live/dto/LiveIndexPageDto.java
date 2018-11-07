package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.dto;

import java.util.List;

public class LiveIndexPageDto {
    private List<LiveCategoryDto> categoryList;
    private String walletSwitch = "0"; // 首页我的红包开关

    public String getWalletSwitch() {
        return walletSwitch;
    }

    public void setWalletSwitch(String walletSwitch) {
        this.walletSwitch = walletSwitch;
    }

    public List<LiveCategoryDto> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<LiveCategoryDto> categoryList) {
        this.categoryList = categoryList;
    }
}
