package com.letv.mas.caller.iptv.tvproxy.terminal.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.SeriesAppVersionInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.SeriesApplicationRelation;

import java.util.HashMap;
import java.util.List;

public class UpgradeConfigDto {
    private HashMap<String, List<SeriesAppVersionInfo>> UPGRADE_MAP = new HashMap<String, List<SeriesAppVersionInfo>>();
    // 存放所有版本信息，包括（强升、推荐和不升级）三个类型的版本
    private HashMap<String, SeriesAppVersionInfo> VERSION_MAP = new HashMap<String, SeriesAppVersionInfo>();
    private HashMap<String, SeriesApplicationRelation> SERIES_APPLICATION_RELATION_MAP = new HashMap<String, SeriesApplicationRelation>();

    public UpgradeConfigDto(HashMap<String, List<SeriesAppVersionInfo>> uPGRADE_MAP,
            HashMap<String, SeriesAppVersionInfo> vERSION_MAP,
            HashMap<String, SeriesApplicationRelation> sERIES_APPLICATION_RELATION_MAP) {
        super();
        UPGRADE_MAP = uPGRADE_MAP;
        VERSION_MAP = vERSION_MAP;
        SERIES_APPLICATION_RELATION_MAP = sERIES_APPLICATION_RELATION_MAP;
    }

    public HashMap<String, List<SeriesAppVersionInfo>> getUPGRADE_MAP() {
        return UPGRADE_MAP;
    }

    public HashMap<String, SeriesAppVersionInfo> getVERSION_MAP() {
        return VERSION_MAP;
    }

    public HashMap<String, SeriesApplicationRelation> getSERIES_APPLICATION_RELATION_MAP() {
        return SERIES_APPLICATION_RELATION_MAP;
    }

}
