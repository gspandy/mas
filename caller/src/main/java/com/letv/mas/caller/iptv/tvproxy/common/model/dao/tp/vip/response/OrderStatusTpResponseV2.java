package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.util.List;

/**
 * 检查订单支付状态返回参数
 * @author dunhongqin
 */
public class OrderStatusTpResponseV2 {
    private boolean success;
    private Integer code;
    private String msg;
    private UserPackageInfoV2 data;
    private ConsumptionCenterErrorV2 error;

    public OrderStatusTpResponseV2() {
    }

    public OrderStatusTpResponseV2(boolean success, Integer code, UserPackageInfoV2 data, String msg) {
        this.success = success;
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserPackageInfoV2 getData() {
        return this.data;
    }

    public void setData(UserPackageInfoV2 data) {
        this.data = data;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ConsumptionCenterErrorV2 getError() {
        return this.error;
    }

    public void setError(ConsumptionCenterErrorV2 error) {
        this.error = error;
    }

    public static class ConsumptionCenterErrorV2 {
        private Integer code;
        private String msg;
        private String end;
        private String pId;

        public ConsumptionCenterErrorV2() {
        }

        public ConsumptionCenterErrorV2(Integer code, String msg, String end, String pId) {
            this.code = code;
            this.msg = msg;
            this.end = end;
            this.pId = pId;
        }

        public Integer getCode() {
            return this.code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getEnd() {
            return this.end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getpId() {
            return this.pId;
        }

        public void setpId(String pId) {
            this.pId = pId;
        }
    }

    public static class UserPackageInfoV2 {
        private boolean empty;
        private Integer total;
        private List<UserPackage> rows;

        public UserPackageInfoV2() {
        }

        public UserPackageInfoV2(boolean empty, Integer total, List<UserPackage> rows) {
            this.empty = empty;
            this.total = total;
            this.rows = rows;
        }

        public boolean isEmpty() {
            return this.empty;
        }

        public void setEmpty(boolean empty) {
            this.empty = empty;
        }

        public Integer getTotal() {
            return this.total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public List<UserPackage> getRows() {
            return this.rows;
        }

        public void setRows(List<UserPackage> rows) {
            this.rows = rows;
        }

        public static class UserPackage {
            private String status;
            private String orderid;
            private String startTime;
            private String productType;
            private String endTime;
            private String orderName;
            private float payPrice;
            private String createTime;
            private String order_flag;
            private String platform;
            private String productId;

            public String getStatus() {
                return this.status;
            }

            public String getCreateTime() {
                return createTime;
            }

            public String getOrderid() {
                return this.orderid;
            }

            public String getStartTime() {
                return this.startTime;
            }

            public String getProductType() {
                return this.productType;
            }

            public String getEndTime() {
                return this.endTime;
            }

            public String getOrderName() {
                return this.orderName;
            }

            public float getPayPrice() {
                return this.payPrice;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public void setProductType(String productType) {
                this.productType = productType;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public void setOrderName(String orderName) {
                this.orderName = orderName;
            }

            public void setPayPrice(float payPrice) {
                this.payPrice = payPrice;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getOrder_flag() {
                return order_flag;
            }

            public void setOrder_flag(String order_flag) {
                this.order_flag = order_flag;
            }

            public String getPlatform() {
                return platform;
            }

            public void setPlatform(String platform) {
                this.platform = platform;
            }

            public String getIsShowRepay() {
                return "1";
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }
        }
    }

}
