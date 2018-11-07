package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

import java.util.Date;

/**
 * 多语言字典表
 */
public class LanguageDictionaryMysqlTable {

    /**
     * 多语言目标表名称
     */
    private String targetTable;

    /**
     * 多语言目标行唯一标识
     */
    private String targetRowId;

    /**
     * 多语言目标列名称
     */
    private String targetField;

    /**
     * 多语言的值
     */
    private String message;

    /**
     * 多语言标识
     */
    private String lang;

    /**
     * 数据状态
     * 1有效 0 无效
     */
    private String status;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 最后更新日期
     */
    private Date lastUpdateDate;

    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    public String getTargetRowId() {
        return targetRowId;
    }

    public void setTargetRowId(String targetRowId) {
        this.targetRowId = targetRowId;
    }

    public String getTargetField() {
        return targetField;
    }

    public void setTargetField(String targetField) {
        this.targetField = targetField;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

}
