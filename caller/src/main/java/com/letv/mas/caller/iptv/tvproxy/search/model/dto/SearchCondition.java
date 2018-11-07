package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.util.List;

/**
 * 搜索条件
 */
public class SearchCondition extends BaseDto implements Comparable {

    private static final long serialVersionUID = 6202045339658933175L;

    /**
     * 类型
     */
    private Integer scType;

    /**
     * 名称
     */
    private String scName;

    /**
     * 排序
     */
    private Integer orderNum;

    private Integer categoryId;

    /**
     * 检索条件状态, 0:失效 1:有效 2:有效，隐藏条件，但不在客户端界面展示
     */
    private Integer status;

    /**
     * 条件项
     */
    private List<SearchConditionItem> items;

    public Integer getScType() {
        return this.scType;
    }

    public void setScType(Integer scType) {
        this.scType = scType;
    }

    public String getScName() {
        return this.scName;
    }

    public void setScName(String scName) {
        this.scName = scName;
    }

    public Integer getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public List<SearchConditionItem> getItems() {
        return this.items;
    }

    public void setItems(List<SearchConditionItem> items) {
        this.items = items;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        if (o != null && o instanceof SearchCondition) {
            SearchCondition obj = (SearchCondition) o;
            if (this.orderNum != null && obj.getOrderNum() != null) {
                return (this.orderNum > obj.getOrderNum()) ? 1 : -1;
            }
            return 0;
        }
        return 0;
    }

    /**
     * 搜索条件项
     */
    public static class SearchConditionItem implements Comparable {

        /**
         * 类型
         */
        private Integer scType;

        /**
         * 名称
         */
        private String scName;

        /**
         * 值
         */
        private String scValue;

        /**
         * 排序
         */
        private Integer orderNum;

        private Integer categoryId;

        /**
         * 检索条件状态, 0:失效 1:有效 2:有效，隐藏条件，但不在客户端界面展示
         */
        private Integer status;

        public Integer getScType() {
            return this.scType;
        }

        public void setScType(Integer scType) {
            this.scType = scType;
        }

        public String getScName() {
            return this.scName;
        }

        public void setScName(String scName) {
            this.scName = scName;
        }

        public String getScValue() {
            return this.scValue;
        }

        public void setScValue(String scValue) {
            this.scValue = scValue;
        }

        public Integer getOrderNum() {
            return this.orderNum;
        }

        public void setOrderNum(Integer orderNum) {
            this.orderNum = orderNum;
        }

        public Integer getCategoryId() {
            return this.categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Integer getStatus() {
            return this.status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        @Override
        public int compareTo(Object o) {
            // TODO Auto-generated method stub
            if (o != null && o instanceof SearchConditionItem) {
                SearchConditionItem obj = (SearchConditionItem) o;
                if (this.orderNum != null && obj.getOrderNum() != null) {
                    return (this.orderNum > obj.getOrderNum()) ? 1 : -1;
                }
                return 0;
            }
            return 0;
        }

    }
}
