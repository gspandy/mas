package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.SeriesAppRelationMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.TpTerminalMysqlTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 访问频道
 */
@Component(value = "v2.TpTerminalMysqlDao")
public interface TpTerminalMysqlDao {

    public SeriesAppRelationMysqlTable getSeriesAppInfo(@Param(value = "terminalBrandCode") String terminalBrandCode,
                                                        @Param(value = "platformCode") String platformCode,
                                                        @Param(value = "terminalSeriesCode") String terminalSeriesCode,
                                                        @Param(value = "terminalApplicationCode") String terminalApplicationCode);

    TpTerminalMysqlTable getTerminalByUuidAndSeriesAppId(@Param("terminalUUId") String terminalUUId,
                                                         @Param("series_app_id") Integer series_app_id);

    void updateTerminalUpdateTimeById(@Param("id") Long id, @Param("brandName") String brandName,
            @Param("seriesName") String seriesName, @Param("versionName") String versionName);

    void insertTerminal(TpTerminalMysqlTable terminal);

}
