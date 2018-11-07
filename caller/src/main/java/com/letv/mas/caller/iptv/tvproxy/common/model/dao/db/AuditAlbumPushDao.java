package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AuditAlbumPush;

/**
 * information whether push video in the album
 */
public interface AuditAlbumPushDao {

    AuditAlbumPush getAuditAlbumPush(long id);

    void insertAuditAlbumPush(AuditAlbumPush auditAlbumPush);

    void updateAuditAlbumPush(AuditAlbumPush auditAlbumPush);

    void deleteAuditAlbumPush(long id);
}
