package com.letv.mas.caller.iptv.tvproxy.vip.model.bean.bo;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.MemberDeskActivityBaseData;

import java.util.List;

/**
 * 会员桌面URM活动信息
 */
public class MemberDeskActivity extends BaseData {
    /**
     * 焦点图数据
     */
    private List<MemberDeskActivityBaseData> recommendList;

    /**
     * 会员权益数据
     */
    private List<MemberDeskActivityBaseData> rightsList;

    /**
     * 会员活动数据
     */
    private List<MemberDeskActivityBaseData> activitiesList;

    public List<MemberDeskActivityBaseData> getRecommendList() {
        return recommendList;
    }

    public void setRecommendList(List<MemberDeskActivityBaseData> recommendList) {
        this.recommendList = recommendList;
    }

    public List<MemberDeskActivityBaseData> getRightsList() {
        return rightsList;
    }

    public void setRightsList(List<MemberDeskActivityBaseData> rightsList) {
        this.rightsList = rightsList;
    }

    public List<MemberDeskActivityBaseData> getActivitiesList() {
        return activitiesList;
    }

    public void setActivitiesList(List<MemberDeskActivityBaseData> activitiesList) {
        this.activitiesList = activitiesList;
    }

}