package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AdvertisementPicture;
import org.apache.ibatis.annotations.Param;


public interface AdvertisementMysqlDao {

    /**
     * @param pos
     * @param start
     * @param pageSize
     * @return
     */
    public List<AdvertisementPicture> getStartupAdvertisePic(@Param("type") Integer type, @Param("pos") String pos,
                                                             @Param("start") Integer start, @Param("pageSize") Integer pageSize);
}
