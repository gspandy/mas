package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component(value = "macWhiteListMysqlDao")
public interface MacWhiteListMysqlDao {

    List<String> getMacWhiteList(@Param("area") String area, @Param("device") String device);
}
