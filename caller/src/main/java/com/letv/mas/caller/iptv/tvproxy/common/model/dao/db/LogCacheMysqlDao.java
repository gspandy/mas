package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.MmsChangeLog;
import org.apache.ibatis.annotations.Param;

public interface LogCacheMysqlDao {

    void saveLog(MmsChangeLog log);

    List<MmsChangeLog> getMmsChangeLogsByTypes(@Param("types") String[] types);

    void initMmsChangeLogStatusByTypes(@Param("new_status") Integer new_status,
            @Param("old_status") Integer old_status, @Param("types") String[] types);

    void updateMmsChangeLogStatusById(@Param("id") String id, @Param("status") Integer status);

    void deleteMmsChangeLogById(String id);

    Long getCount();

    List<MmsChangeLog> findByObjectId(@Param("object_id") String object_id);
}
