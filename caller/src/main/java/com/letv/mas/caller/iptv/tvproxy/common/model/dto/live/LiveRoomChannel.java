package com.letv.mas.caller.iptv.tvproxy.common.model.dto.live;

import java.io.Serializable;

/**
 * 直播大厅类型，如体育、财经、资讯、娱乐、游戏、音乐、品牌、其他直播大厅；
 * 其中频道分类和媒资定义一致（但是否取自媒资，还得再确认）
 * @author KevinYi
 */
public class LiveRoomChannel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5246490922341602561L;

    /**
     * 频道英文名，如体育--sports，财经--finance、资讯--information、娱乐--entertainment、游戏--game、
     * 音乐--music、品牌--brand、其他--other;
     */
    private String ename;

    /**
     * sports:4;music:9;entertainment:3;finance:22;information:1009;game:-1（无对应值
     * ）;brand:-1;other:8
     */
    private Integer categoryId;

    public LiveRoomChannel() {
        super();
    }

    public LiveRoomChannel(String ename, Integer categoryId) {
        super();
        this.ename = ename;
        this.categoryId = categoryId;
    }

    public String getEname() {
        return this.ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

}
