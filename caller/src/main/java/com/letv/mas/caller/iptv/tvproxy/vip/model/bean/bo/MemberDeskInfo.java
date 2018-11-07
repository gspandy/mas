package com.letv.mas.caller.iptv.tvproxy.vip.model.bean.bo;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.MemberDeskInfoBaseData;

import java.util.List;

/**
 * 会员信息结果
 */
public class MemberDeskInfo extends BaseData {
    /**
     * 到期时间或者运营触达位
     */
    private String showContent;

    /**
     * 是否体育会员 0-非会员，1是会员，2过期会员
     */
    private int sportMember;

    /**
     * 是否影视会员 0-非会员，1是会员，2过期会员
     */
    private int movieMember;

    /**
     * 跳转数据
     */
    private List<MemberDeskInfoBaseData> dataList;

    public List<MemberDeskInfoBaseData> getDataList() {
        return dataList;
    }

    public void setDataList(List<MemberDeskInfoBaseData> dataList) {
        this.dataList = dataList;
    }

    public String getShowContent() {
        return showContent;
    }

    public void setShowContent(String showContent) {
        this.showContent = showContent;
    }

    public int getSportMember() {
        return sportMember;
    }

    public void setSportMember(int sportMember) {
        this.sportMember = sportMember;
    }

    public int getMovieMember() {
        return movieMember;
    }

    public void setMovieMember(int movieMember) {
        this.movieMember = movieMember;
    }
}