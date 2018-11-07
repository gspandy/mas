package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.ContainerConfMysqlTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 访问频道数据
 */
@Component(value = "v2.ContainerConfMysqlDao")
public interface ContainerConfMysqlDao {

    public List<ContainerConfMysqlTable> getList(@Param(value = "containerId") Integer containerId);
}
