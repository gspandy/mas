package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.io.Serializable;

/**
 * 媒资词典封装类
 * @author KevinYi
 */
public class DictTp implements Serializable {

    /**
     * 结果状态，1--成功，0--失败
     */
    private String callback;

    private DictData data;

    /**
     * 结果描述，错误原因
     */
    private String[] msgs;

    private int result;

    private String statusCode;

    public class DictData implements Serializable {

        /**
         * 字典值所属频道id
         */
        private String channelId;

        /**
         * 字典描述
         */
        private String description;

        /**
         * 字典id
         */
        private Integer id;

        /**
         * 父类字典值id
         */
        private Integer parentValueId;

        /**
         * 
         */
        private Integer propertyObject;

        /**
         * 所属字典类型（大类）
         */
        private Integer typeId;

        /**
         * 字典值
         */
        private String value;

        public String getChannelId() {
            return this.channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getId() {
            return this.id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getParentValueId() {
            return this.parentValueId;
        }

        public void setParentValueId(Integer parentValueId) {
            this.parentValueId = parentValueId;
        }

        public Integer getPropertyObject() {
            return this.propertyObject;
        }

        public void setPropertyObject(Integer propertyObject) {
            this.propertyObject = propertyObject;
        }

        public Integer getTypeId() {
            return this.typeId;
        }

        public void setTypeId(Integer typeId) {
            this.typeId = typeId;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public String getCallback() {
        return this.callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public DictData getData() {
        return this.data;
    }

    public void setData(DictData data) {
        this.data = data;
    }

    public String[] getMsgs() {
        return this.msgs;
    }

    public void setMsgs(String[] msgs) {
        this.msgs = msgs;
    }

    public int getResult() {
        return this.result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}
