package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;


import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.ChargeInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.VipObjInfo;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：apple on 17/8/14 15:45
 * eMail：dengliwei@le.com
 */
public class BossChargeInfoData {
    private List<VipObjInfo> videos;
    private List<VipObjInfo> albums;

    public List<VipObjInfo> getVideos() {
        return videos;
    }

    public void setVideos(List<VipObjInfo> videos) {
        this.videos = videos;
    }

    public List<VipObjInfo> getAlbums() {
        return albums;
    }

    public void setAlbums(List<VipObjInfo> albums) {
        this.albums = albums;
    }

    public List<ChargeInfo> drawAlbumsChargeInfos(String chargePlatform) {
        List<ChargeInfo> ret = null;
        if (null != this.albums) {
            ChargeInfo chargeInfo = null;
            List<ChargeInfo> chargeInfosTmp = null;
            ret = new ArrayList<ChargeInfo>();
            for (int i = 0, len = this.albums.size(); i < len; i++) {
                chargeInfosTmp = this.albums.get(i).getTerminalCharges();
                for (int j = 0, size = chargeInfosTmp.size(); j < size; j++) {
                    chargeInfosTmp.get(j).setPid(this.albums.get(i).getPid());
                    chargeInfosTmp.get(j).setStatus(this.albums.get(i).getStatus());
                    if (StringUtil.isNotBlank(chargePlatform)) {
                        if (StringUtil.isNotBlank(chargeInfosTmp.get(j).getChargePlatform())
                                && chargeInfosTmp.get(j).getChargePlatform().equals(chargePlatform)) {
                            ret.add(chargeInfosTmp.get(j));
                        }
                    } else {
                        ret.add(chargeInfosTmp.get(j));
                    }
                }
            }
        }
        return ret;
    }

    public List<ChargeInfo> drawVideosChargeInfos(String chargePlatform) {
        List<ChargeInfo> ret = null;
        if (null != this.videos) {
            ChargeInfo chargeInfo = null;
            List<ChargeInfo> chargeInfosTmp = null;
            ret = new ArrayList<ChargeInfo>();
            for (int i = 0, len = this.videos.size(); i < len; i++) {
                chargeInfosTmp = this.videos.get(i).getTerminalCharges();
                for (int j = 0, size = chargeInfosTmp.size(); j < size; j++) {
                    chargeInfosTmp.get(j).setVid(this.videos.get(i).getVid());
                    chargeInfosTmp.get(j).setStatus(this.videos.get(i).getStatus());
                    if (StringUtil.isNotBlank(chargePlatform)) {
                        if (StringUtil.isNotBlank(chargeInfosTmp.get(j).getChargePlatform())
                                && chargeInfosTmp.get(j).getChargePlatform().equals(chargePlatform)) {
                            ret.add(chargeInfosTmp.get(j));
                        }
                    } else {
                        ret.add(chargeInfosTmp.get(j));
                    }
                }
            }
        }
        return ret;
    }
}
