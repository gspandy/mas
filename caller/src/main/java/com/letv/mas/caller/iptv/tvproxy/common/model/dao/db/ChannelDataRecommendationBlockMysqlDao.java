package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.ChannelDataRecommendationBlockMysqlTable;
import org.springframework.stereotype.Component;

/**
 * 访问频道数据-推荐版块关系
 */
@Component(value = "v2.ChannelDataRecommendationBlockMysqlDao")
public interface ChannelDataRecommendationBlockMysqlDao {

    public List<ChannelDataRecommendationBlockMysqlTable> getList();

    public void save(ChannelDataRecommendationBlockMysqlTable channelDataRecommendationBlockMysqlTable);
}
