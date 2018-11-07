package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.ChannelCmsChannelMysqlTable;
import org.springframework.stereotype.Component;

/**
 * 访问频道-CMS频道关系
 */
@Component(value = "v2.ChannelCmsChannelMysqlDao")
public interface ChannelCmsChannelMysqlDao {

    public List<ChannelCmsChannelMysqlTable> getList();

    public void save(ChannelCmsChannelMysqlTable channelCmsChannelMysqlTable);
}
