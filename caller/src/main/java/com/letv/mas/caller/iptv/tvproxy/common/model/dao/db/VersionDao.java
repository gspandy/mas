package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.SeriesAppVersionInfo;
import org.apache.ibatis.annotations.Param;

public interface VersionDao {

    List<SeriesAppVersionInfo> getVersionInfoBySAPPId(@Param("series_app_id") Integer series_app_id);
}
