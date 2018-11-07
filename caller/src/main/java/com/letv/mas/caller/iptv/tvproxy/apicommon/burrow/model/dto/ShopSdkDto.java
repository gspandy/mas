package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.io.Serializable;

public class ShopSdkDto {

    public static class ShopSubject extends BaseDto implements Serializable {
        private static final long serialVersionUID = -5303895743412977598L;
        private int item_id;
        private int category_id;
        private int product_type;

        public int getItem_id() {
            return item_id;
        }

        public void setItem_id(int item_id) {
            this.item_id = item_id;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public int getProduct_type() {
            return product_type;
        }

        public void setProduct_type(int product_type) {
            this.product_type = product_type;
        }
    }

}
