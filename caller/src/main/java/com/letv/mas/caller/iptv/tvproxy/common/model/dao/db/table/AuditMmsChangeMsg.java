package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

/**
 * Entity class for the table AUDIT_MMS_CHANGE_MSG
 */
public class AuditMmsChangeMsg {
    private String id;
    private long objectId;
    private int consumerId;
    private int actionType;
    private int processStatus;
    private int priority;
    private int pushFlag;
    private String operator = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public int getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(int consumerId) {
        this.consumerId = consumerId;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public int getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(int processStatus) {
        this.processStatus = processStatus;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPushFlag() {
        return pushFlag;
    }

    public void setPushFlag(int pushFlag) {
        this.pushFlag = pushFlag;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
