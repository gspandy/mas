package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.SeriesAppVersionInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.TpSeriesAppVersionInfoMysqlTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component(value = "v2.TpVersionMysqlDao")
public interface TpVersionMysqlDao {

    TpSeriesAppVersionInfoMysqlTable getVersionInfoBySAPPIdAndName(@Param("series_app_id") Integer series_app_id,
                                                                   @Param("version_name") String version_name);

    //
    // SeriesAppVersionInfoMysqlTable
    // getVersionInfoByName(@Param("version_name") String version_name);
    //
    // List<SeriesAppVersionInfoMysqlTable> getAllVersionBySAPPId(Integer
    // series_app_id);

    List<TpSeriesAppVersionInfoMysqlTable> getCanAllVersionBySAPPIdAndCode(
            @Param("series_app_id") Integer series_app_id, @Param("version_code") Integer version_code);

    List<TpSeriesAppVersionInfoMysqlTable> getCommonVersionByCode(@Param("version_code") Integer version_code);

    List<TpSeriesAppVersionInfoMysqlTable> getHighVersionList(@Param("series_app_id") Integer series_app_id,
            @Param("version_name") String version_name);

    List<SeriesAppVersionInfo> getVersionList(@Param("series_app_id") Integer series_app_id,
                                              @Param("version_name") String version_name);

}
