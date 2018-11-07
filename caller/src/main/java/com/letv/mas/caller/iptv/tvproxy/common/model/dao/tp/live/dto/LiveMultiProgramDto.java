package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.dto;

import java.util.List;

public class LiveMultiProgramDto {
    private String branchDesc;
    private List<LiveChannelDto> branches;

    public String getBranchDesc() {
        return branchDesc;
    }

    public void setBranchDesc(String branchDesc) {
        this.branchDesc = branchDesc;
    }

    public List<LiveChannelDto> getBranches() {
        return branches;
    }

    public void setBranches(List<LiveChannelDto> branches) {
        this.branches = branches;
    }
}
