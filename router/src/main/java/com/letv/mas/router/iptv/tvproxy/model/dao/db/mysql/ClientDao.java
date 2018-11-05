package com.letv.mas.router.iptv.tvproxy.model.dao.db.mysql;

import com.letv.mas.router.iptv.tvproxy.model.xdo.ClientDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by leeco on 18/10/19.
 */
@Repository
@Mapper
public interface ClientDao {
    public ClientDo getClientById(@Param("user_id") String id);
}
