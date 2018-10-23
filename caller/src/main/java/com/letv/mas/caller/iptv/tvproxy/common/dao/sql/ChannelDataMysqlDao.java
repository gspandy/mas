package com.letv.mas.caller.iptv.tvproxy.common.dao.sql;

import com.letv.mas.caller.iptv.tvproxy.common.dao.sql.pojo.ChannelDataMysqlTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import java.util.List;
/**
 * 访问频道数据
 */
@Component
public interface ChannelDataMysqlDao {

    public List<ChannelDataMysqlTable> getList(@Param(value = "channelId") Integer channelId);
}
