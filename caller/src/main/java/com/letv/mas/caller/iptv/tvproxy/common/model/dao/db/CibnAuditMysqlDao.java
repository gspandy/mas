package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.CibnAuditInfo;
import org.apache.ibatis.annotations.Param;

/**
 * Store the history of CIBN audit messages
 */
public interface CibnAuditMysqlDao {

    /**
     * get the information of specified item
     * @param itemId
     *            id
     * @param itemType
     *            type, 1-album 2-video
     * @return the item audit information
     */
    CibnAuditInfo getCibnAuditInfo(@Param("itemId") long itemId, @Param("itemType") int itemType);

    /**
     * insert the first audit information
     * @param cibnAuditInfo
     *            audit information
     */
    void insertCibnAuditInfo(CibnAuditInfo cibnAuditInfo);

    /**
     * update the audit messages of the specified item.
     * @param cibnAuditInfo
     *            audit information
     */
    void updateCibnAuditInfo(CibnAuditInfo cibnAuditInfo);

}
