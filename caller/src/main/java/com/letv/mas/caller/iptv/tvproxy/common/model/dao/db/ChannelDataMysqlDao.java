package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.ChannelDataMysqlTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 访问频道数据
 */
@Component(value = "v2.ChannelDataMysqlDao")
public interface ChannelDataMysqlDao {

    public List<ChannelDataMysqlTable> getList(@Param(value = "channelId") Integer channelId);
}
