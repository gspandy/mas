package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.bean;

import java.util.List;

/**
 * Created by root on 4/28/17.
 * 商品信息类
 */
public class PVProduct {

    // 收银台产品信息
    private List<ProductInfo> cashier_products;

    public List<ProductInfo> getCashier_products() {
        return cashier_products;
    }

    public void setCashier_products(List<ProductInfo> cashier_products) {
        this.cashier_products = cashier_products;
    }

    public static class ProductInfo {

        // 收银台商品 Id
        private String cashier_product_id;
        // 收银台商品名称
        private String cashier_product_name;
        // 收银台商品价格
        private String cashier_product_price;

        public String getCashier_product_id() {
            return cashier_product_id;
        }

        public void setCashier_product_id(String cashier_product_id) {
            this.cashier_product_id = cashier_product_id;
        }

        public String getCashier_product_name() {
            return cashier_product_name;
        }

        public void setCashier_product_name(String cashier_product_name) {
            this.cashier_product_name = cashier_product_name;
        }

        public String getCashier_product_price() {
            return cashier_product_price;
        }

        public void setCashier_product_price(String cashier_product_price) {
            this.cashier_product_price = cashier_product_price;
        }
    }
}
