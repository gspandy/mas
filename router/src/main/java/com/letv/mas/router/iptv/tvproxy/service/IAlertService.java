package com.letv.mas.router.iptv.tvproxy.service;

/**
 * Created by leeco on 18/10/8.
 */
public interface IAlertService {

    public enum AlertType {
        DINGDING(1),
        EMAIL(2);

        private Integer type;

        private AlertType(Integer type) {
            this.type = type;
        }

        public Integer getType() {
            return this.type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return this.type + "";
        }
    }

    public boolean handleMessage(Object msg, AlertType alertType);
}
