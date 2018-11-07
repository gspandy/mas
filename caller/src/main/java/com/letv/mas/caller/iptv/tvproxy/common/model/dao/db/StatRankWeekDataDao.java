package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.StatRankWeekData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 周排行Dao
 */
@Component(value = "v2.StatRankWeekDataDao")
public interface StatRankWeekDataDao {
    /**
     * 根据日期和排行类型获取排行列表
     * 0全部
     * 1电影
     * 2电视剧
     * 5动漫
     * 11综艺
     * @param stat_date
     * @param type
     * @param start
     *            分页数据的开始索引，包含
     * @param end
     *            分页数据的结束索引，不包含
     * @return
     */
    public List<StatRankWeekData> listByStatDateAndType(@Param("stat_date") String stat_date,
                                                        @Param("type") String type, @Param("start") Integer start, @Param("end") Integer end);

    /**
     * 根据排行类型获取最新排行列表
     * @param type
     *            专辑类型ID
     * @param start
     *            分页数据的开始索引，包含
     * @param end
     *            分页数据的结束索引，不包含
     * @return
     */
    public List<StatRankWeekData> listLatestByStatDateAndType(@Param("type") Integer type,
            @Param("start") Integer start, @Param("end") Integer end);

    /**
     * 根据日期和多种排行类型获取排行列表
     * 0全部
     * 1电影
     * 2电视剧
     * 5动漫
     * 11综艺
     * @param stat_date
     * @param type
     * @param start
     *            分页数据的开始索引，包含
     * @param end
     *            分页数据的结束索引，不包含
     * @return
     */
    public List<StatRankWeekData> listByStatDateAndMultiType(@Param("stat_date") String stat_date,
            @Param("types") List<Integer> types, @Param("start") Integer start, @Param("end") Integer end);

    /**
     * 根据albumId获取最近一次统计的专辑StatRankWeekData信息
     * @param albumId
     * @param type
     * @return
     */
    public StatRankWeekData getLatestRankStatByAlbumId(@Param("albumId") String albumId, @Param("type") Integer type);

    /**
     * 查询飙升榜；
     * 当previousStatDate为空，或对应时间无数据时，查的就是排行榜；可以认为当前的排行就是从0开始的飙升值，即兼容
     * listByStatDateAndType的结果，但这里仍将其区分开
     * @param currentStatData
     *            本次查询的时间，也即最近一次更新数据时间
     * @param previousStatDate
     *            本次之间的上一次数据更新时间
     * @param start
     *            分页数据的开始索引，包含
     * @param end
     *            分页数据的结束索引，不包含
     * @return
     */
    public List<StatRankWeekData> listSoaring(@Param("currentStatData") String currentStatData,
            @Param("previousStatDate") String previousStatDate, @Param("tPreviousStatDate") String tPreviousStatDate,
            @Param("start") Integer start, @Param("end") Integer end);

    /**
     * 获取某一视频第一次的统计时间，如果之前未统计过，则返回null；时间格式为“yyyyMMdd”，故这里使用int类型
     * @param albumId
     * @return
     */
    public Integer getFirstStatisticsDateByAlbumId(@Param("albumId") String albumId);

    /**
     * 获取最近一次的统计时间
     * @return
     */
    public String getLatestStatisticsDate();

}
