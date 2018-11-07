package com.letv.mas.caller.iptv.tvproxy.live.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.util.LiveUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LiveProjectDto implements Serializable {
    private static final long serialVersionUID = 7507885546559215270L;
    private List<LiveProject> projects = new ArrayList<LiveProject>();
    private String bgImg;
    private String title;
    private Integer size;
    private String splatId;

    public LiveProjectDto() {

    }

    public LiveProjectDto(List<LiveProject> projects, String bgImg, String title) {
        this.projects = projects;
        this.bgImg = bgImg;
        this.title = title;
        this.size = projects == null ? 0 : projects.size();
    }

    public List<LiveProject> getProjects() {
        return this.projects;
    }

    public void setProjects(List<LiveProject> projects) {
        this.projects = projects;
        this.size = projects == null ? 0 : projects.size();
    }

    public String getBgImg() {
        return this.bgImg;
    }

    public void setBgImg(String bgImg) {
        this.bgImg = bgImg;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSplatId() {
        return this.splatId;
    }

    public void setSplatId(String splatId) {
        this.splatId = splatId;
    }

    /** 以下字段不是用于po对象传参 */

    /**
     * 用于存储需要付费的场次id，不对外提供,注意不能重复，故使用set
     */
    private Set<String> payIdSet;

    public String showPayIds() {
        if (!CollectionUtils.isEmpty(this.payIdSet)) {
            return StringUtils.join(this.payIdSet, ",");
        }
        return null;
    }

    // 该字段不是用于po携带数据给客户端的，是短暂的携带付费场次id组，所以方法内容重写
    public void buildPayIds(String id) {
        if (StringUtils.isEmpty(id)) {
            return;
        }

        if (this.payIdSet == null) {
            this.payIdSet = new HashSet<String>();
        }
        this.payIdSet.add(id);
    }

    private StringBuffer liveKeys; // 用于存储需要付费的直播id，不对外提供

    public String showLiveKeys() {
        if (this.liveKeys != null && this.liveKeys.length() > 0) {
            return this.liveKeys.toString();
        }
        return null;
    }

    public void buildLiveKeys(String id, int broadcastId) {
        if (id == null || id.length() == 0) {
            return;
        }

        String newKey = LiveUtil.buildLiveKey(id, broadcastId, this.splatId);

        if (this.liveKeys != null && this.liveKeys.length() > 0) {
            this.liveKeys.append(",");
            this.liveKeys.append(newKey);

        } else {
            this.liveKeys = new StringBuffer(newKey);
        }
    }

    /** 以上字段不是用于po对象传参 */

    /**
     * 刷新
     */
    // public void fresh() {
    // this.payIds = null;
    // this.liveKeys = null;
    // this.size = this.projects == null ? 0 : this.projects.size();
    // }

}
