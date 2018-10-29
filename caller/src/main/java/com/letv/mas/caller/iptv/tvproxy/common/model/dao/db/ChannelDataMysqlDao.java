package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.pojo.ChannelDataMysqlTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import java.util.List;
/**
 * 访问频道数据
 */
@Component
@Mapper
public interface ChannelDataMysqlDao {

    public List<ChannelDataMysqlTable> getList(@Param(value = "channelId") Integer channelId);
}
