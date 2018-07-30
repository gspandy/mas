package com.letv.mas.common.stream.model.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

/**
 * Stream消息类
 * <p>
 * Created by dalvikchen on 18/7/9.
 */
public class StreamMessage {
    private String id;
    private int type;
    private int action;
    private int partition = -1;
    private String from;
    private String content;
    private Date date;

    public String getId() {
        return id;
    }

    public StreamMessage setId(String id) {
        this.id = id;
        return this;
    }

    public int getType() {
        return type;
    }

    public StreamMessage setType(int type) {
        this.type = type;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public StreamMessage setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getContent() {
        return content;
    }

    public StreamMessage setContent(String content) {
        this.content = content;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public StreamMessage setDate(Date date) {
        this.date = date;
        return this;
    }

    public int getAction() {
        return action;
    }

    public StreamMessage setAction(int action) {
        this.action = action;
        return this;
    }

    public int getPartition() {
        return partition;
    }

    public StreamMessage setPartition(int partition) {
        this.partition = partition;
        return this;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "";
        try {
            s = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }
}
