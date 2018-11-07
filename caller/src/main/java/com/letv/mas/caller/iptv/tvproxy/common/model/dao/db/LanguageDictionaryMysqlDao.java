package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.LanguageDictionaryMysqlTable;
import org.springframework.stereotype.Component;

/**
 * 访问多语言字典数据
 */
@Component(value = "v2.LanguageDictionaryMysqlDao")
public interface LanguageDictionaryMysqlDao {

    public List<LanguageDictionaryMysqlTable> getAllMessage();
}
