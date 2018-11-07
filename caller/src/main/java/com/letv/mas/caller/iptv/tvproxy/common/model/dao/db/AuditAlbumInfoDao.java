package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AuditAlbumInfo;

/**
 * Store the album information of previous auditing
 */
public interface AuditAlbumInfoDao {

    /**
     * get the audit information of the specified album.
     * @param id
     *            album id
     * @return album audit information
     */
    AuditAlbumInfo getAuditAlbumInfo(long id);

    /**
     * insert the first audit information
     * @param auditAlbumInfo
     *            album audit information
     */
    void insertAuditAlbumInfo(AuditAlbumInfo auditAlbumInfo);

    /**
     * update the audit information of the specified album.
     * @param auditAlbumInfo
     *            album audit information
     */
    void updateAuditAlbumInfo(AuditAlbumInfo auditAlbumInfo);

}
