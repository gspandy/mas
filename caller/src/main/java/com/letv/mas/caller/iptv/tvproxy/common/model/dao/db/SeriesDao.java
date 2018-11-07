package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.Series;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.SeriesApplication;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.SeriesApplicationRelation;
import org.apache.ibatis.annotations.Param;

public interface SeriesDao {

    SeriesApplicationRelation getSeriesApplicationRelationBySidAndSAidAndPid(@Param("series_id") Long series_id,
                                                                             @Param("app_id") Long app_id, @Param("platform_id") Long platform_id);

    List<Series> getSeriesByBrandId(@Param("brand_id") Long brand_id);

    List<SeriesApplication> getSeriesApplicationList();
}
