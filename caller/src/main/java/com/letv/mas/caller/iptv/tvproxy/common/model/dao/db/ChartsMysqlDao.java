package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.ChartsMysqlTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 排行榜数据访问DAO
 */
@Component(value = "chartsMysqlDao")
public interface ChartsMysqlDao {

    /**
     * 获取频道channelId下的所有排行榜信息
     * @param channelId
     * @return
     */
    public List<ChartsMysqlTable> getListByChannelId(@Param(value = "channelId") Integer channelId);

    /**
     * 获取频道下所有排行榜信息；获取排行榜详情信息
     * @param channelId
     *            排行榜频道Id
     * @param chartsId
     *            排行榜榜单id
     * @return
     */
    public List<ChartsMysqlTable> getListById(@Param(value = "channelId") Integer channelId,
            @Param(value = "chartsId") Integer chartsId);

    /**
     * 根据id获取ChartsMysqlTable
     * @param chartsId
     * @return
     */
    public ChartsMysqlTable getById(@Param(value = "chartsId") Integer chartsId);

    /**
     * 根据状态查询所有排行榜id
     * @param status
     * @return
     */
    public List<Integer> getChartsIdsByStatus(@Param(value = "status") Integer status);
}
