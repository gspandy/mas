package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

/**
 * Entity class for the table iptv.CIBN_AUDIT
 */
public class CibnAuditInfo {
    private long id;
    private long itemId;
    private int itemType;
    private int auditResult;
    private String auditMessages;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(int auditResult) {
        this.auditResult = auditResult;
    }

    public String getAuditMessages() {
        return auditMessages;
    }

    public void setAuditMessages(String auditMessages) {
        this.auditMessages = auditMessages;
    }
}
