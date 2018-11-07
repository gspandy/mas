package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.ChannelMysqlTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 访问频道
 */
@Component(value = "v2.ChannelMysqlDao")
public interface ChannelMysqlDao {

    public List<ChannelMysqlTable> getList(@Param(value = "broadcastId") Integer broadcastId,
                                           @Param(value = "parentChannelId") Integer parentChannelId, @Param(value = "mcode") Integer mcode,
                                           @Param(value = "vnum") String vnum);

    public ChannelMysqlTable get(@Param(value = "channelId") Integer channelId);
}
