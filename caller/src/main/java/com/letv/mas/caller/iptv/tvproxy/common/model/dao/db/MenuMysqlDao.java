package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.Itv_Menu;
import org.apache.ibatis.annotations.Param;

public interface MenuMysqlDao {
    /**
     * 根据id获取菜单
     * @param id
     * @return
     */
    public Itv_Menu findById(@Param(value = "id") Integer id);

    /**
     * 获取一级菜单
     * @param client
     * @param vnum
     * @param broadcastId
     * @param terminalBrand
     * @param terminalSeries
     * @return
     */
    public List<Itv_Menu> getMainMenu(@Param(value = "fid") Integer fid, @Param(value = "client") String client,
            @Param(value = "vnum") Float vnum, @Param(value = "broadcastId") Integer broadcastId,
            @Param(value = "terminalBrand") String terminalBrand,
            @Param(value = "terminalSeries") String terminalSeries, @Param(value = "orderBy") String orderBy);

    public void updateItvMenu(Itv_Menu itv_Menu);
}
