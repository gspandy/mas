package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.DeskConfigMysqlTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component(value = "v2.DeskConfigMysqlDao")
public interface DeskConfigMysqlDao {
    public List<DeskConfigMysqlTable> getList(@Param(value = "model") Integer model);
}
