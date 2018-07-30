package com.letv.mas.common.stream.model.dto;

import java.util.Date;

/**
 * 确认消息，单元测试使用
 *
 * @author dalvikchen
 * @date 18/7/11
 */
public class ConfirmMessage {
    /**
     * 确认消息id
     */
    private String id;
    /**
     * 原始消息体
     */
    private StreamMessage originMessage;
    /**
     * 消息接收方
     */
    private String receiver;
    /**
     * 消息类型：broadcast,multicast,pointcast,partition
     */
    private String messageType;
    /**
     * 消息确认时间
     */
    private Date date;

    public String getId() {
        return id;
    }

    public ConfirmMessage setId(String id) {
        this.id = id;
        return this;
    }

    public StreamMessage getOriginMessage() {
        return originMessage;
    }

    public ConfirmMessage setOriginMessage(StreamMessage originMessage) {
        this.originMessage = originMessage;
        return this;
    }

    public String getReceiver() {
        return receiver;
    }

    public ConfirmMessage setReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public String getMessageType() {
        return messageType;
    }

    public ConfirmMessage setMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public ConfirmMessage setDate(Date date) {
        this.date = date;
        return this;
    }
}
