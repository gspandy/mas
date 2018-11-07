package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.UserChildLockTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserMysqlDao {

    public void insertChildLock(@Param(value = "userChildLockTable") UserChildLockTable userChildLockTable);

    public void updateChildLock(@Param(value = "userChildLockTable") UserChildLockTable userChildLockTable);

    public UserChildLockTable getChildLockByUserId(@Param(value = "userId") String userId);
}
