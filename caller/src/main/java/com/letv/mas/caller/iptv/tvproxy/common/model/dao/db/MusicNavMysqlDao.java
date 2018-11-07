package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.MusicNavMysqlTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
/**
 * 访问频道
 */
@Component(value = "v2.MusicNavMysqlDao")
public interface MusicNavMysqlDao {

    public List<MusicNavMysqlTable> getList(@Param(value = "parent_id") Long fId,
                                            @Param(value = "broadcastId") Integer broadcastId, @Param(value = "vnum") Float vnum);

}
