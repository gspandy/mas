package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

/**
 * Entity of information whether push video in the album
 */
public class AuditAlbumPush {
    private long id;
    private int pushCibn = 0;
    private int pushWasu = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPushCibn() {
        return pushCibn;
    }

    public void setPushCibn(int pushCibn) {
        this.pushCibn = pushCibn;
    }

    public int getPushWasu() {
        return pushWasu;
    }

    public void setPushWasu(int pushWasu) {
        this.pushWasu = pushWasu;
    }
}
