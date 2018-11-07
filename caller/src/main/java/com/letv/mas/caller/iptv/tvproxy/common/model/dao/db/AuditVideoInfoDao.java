package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AuditVideoInfo;

/**
 * Store the video information of previous auditing
 */
public interface AuditVideoInfoDao {

    /**
     * get the information of specified item
     * @param id
     *            video id
     * @return the video audit information
     */
    AuditVideoInfo getAuditVideoInfo(long id);

    /**
     * insert the first audit information
     * @param auditVideoInfo
     *            audit information
     */
    void insertAuditVideoInfo(AuditVideoInfo auditVideoInfo);

    /**
     * update the audit messages of the specified item.
     * @param auditVideoInfo
     *            audit information
     */
    void updateAuditVideoInfo(AuditVideoInfo auditVideoInfo);

}
