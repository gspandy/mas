package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.BroadcastWhiteList;
import org.apache.ibatis.annotations.Param;

public interface BroadcastWhiteListMysqlDao {

    BroadcastWhiteList find(@Param(value = "type") Integer type, @Param(value = "rid") Long rid,
                            @Param(value = "broadcastId") Integer broadcastId);

    void save(BroadcastWhiteList broadcastWhiteList);

    void delete(@Param(value = "id") Long id);

    void update(BroadcastWhiteList broadcastWhiteList);
}
