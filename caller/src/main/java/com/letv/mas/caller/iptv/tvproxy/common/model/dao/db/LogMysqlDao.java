package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.MmsChangeLog;
import org.apache.ibatis.annotations.Param;

public interface LogMysqlDao {
    /*
     * 插入日志
     */
    public void saveLog(MmsChangeLog log);

    public List<MmsChangeLog> getMmsChangeLogsByTypes(@Param("types") String[] types);

    public void initMmsChangeLogStatusByTypes(@Param("new_status") Integer new_status,
            @Param("old_status") Integer old_status, @Param("types") String[] types);

    public void updateMmsChangeLogStatusById(@Param("id") String id, @Param("status") Integer status);

    public void deleteMmsChangeLogById(String id);

    public Long getCount();

    public List<MmsChangeLog> findByObjectId(@Param("object_id") String object_id);
}
