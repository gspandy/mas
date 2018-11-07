package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.IptvDictionnaryInfo;

public interface DictionnaryMysqlDao {
    public IptvDictionnaryInfo getIptvDictionnaryInfoById(Integer id);

    public void insertIptvDictionnaryInfo(IptvDictionnaryInfo dictionnaryInfo);

    public void updateIptvDictionnaryInfo(IptvDictionnaryInfo dictionnaryInfo);

    public void deleteIptvDictionnaryInfoById(Integer id);
}
