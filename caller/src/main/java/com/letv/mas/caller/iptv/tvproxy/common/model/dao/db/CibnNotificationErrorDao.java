package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import java.util.List;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.CibnNotificationError;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Store the history of CIBN notification errors
 */
@Component
public interface CibnNotificationErrorDao {

    /**
     * insert the CIBN notification error
     * @param cibnNotificationError
     *            notification error
     */
    void insertCibnNotificationError(CibnNotificationError cibnNotificationError);

    /**
     * @return
     */
    List<CibnNotificationError> getCibnNotificationErrors(@Param("start") int start, @Param("size") int size);

    /**
     * @param error
     */
    void updateCibnNotificationError(CibnNotificationError error);

    /**
     * @param id
     */
    void deleteCibnNotificationError(long id);

}
