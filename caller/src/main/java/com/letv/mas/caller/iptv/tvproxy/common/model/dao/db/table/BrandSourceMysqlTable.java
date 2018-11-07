package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

public class BrandSourceMysqlTable {

    private String brand_name;
    private String brand_code;// 品牌编码

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBrand_code() {
        return brand_code;
    }

    public void setBrand_code(String brand_code) {
        this.brand_code = brand_code;
    }
}
