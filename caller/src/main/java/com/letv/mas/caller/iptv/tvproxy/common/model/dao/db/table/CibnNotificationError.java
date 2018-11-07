package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

/**
 * Entity class for the table iptv.CIBN_NOTIFICATION_ERROR
 */
public class CibnNotificationError {
    private long id;
    private long itemId;
    private int itemType;
    private int errorCode;
    private String errorMessage;

    public CibnNotificationError() {
        // for mybatis
    }

    public CibnNotificationError(long itemId, int itemType) {
        this.itemId = itemId;
        this.itemType = itemType;
    }

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

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
