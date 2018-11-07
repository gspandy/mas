package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.TerminalConfigMysqlTable;
import org.springframework.stereotype.Component;

@Component(value = "terminalConfigMysqlDao")
public interface TerminalConfigMysqlDao {

    /**
     * 查询配置信息
     * @return
     */
    public List<TerminalConfigMysqlTable> getTerminalConfig();

}
