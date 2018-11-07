package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.SearchConditionMysqlTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 访问搜索条件
 */
@Component(value = "v2.SearchConditionMysqlDao")
public interface SearchConditionMysqlDao {

    public List<SearchConditionMysqlTable> getList(@Param(value = "channelId") Integer channelId);
}
