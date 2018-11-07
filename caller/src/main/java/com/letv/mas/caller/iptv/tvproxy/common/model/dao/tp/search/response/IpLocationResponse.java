package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

public class IpLocationResponse {

    private String country_code; // 国家编码
    private String country_name; // 国家名称
    private String province_id; // 省份ID
    private String province_name; // 省份名称
    private String distinct_id; // 地区id
    private String distinct_name; // 地区名称
    private String isp_id; // 运营商id
    private String isp_name; // 运行商name
    private String city_name; // 城市名称

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getDistinct_id() {
        return distinct_id;
    }

    public void setDistinct_id(String distinct_id) {
        this.distinct_id = distinct_id;
    }

    public String getDistinct_name() {
        return distinct_name;
    }

    public void setDistinct_name(String distinct_name) {
        this.distinct_name = distinct_name;
    }

    public String getIsp_id() {
        return isp_id;
    }

    public void setIsp_id(String isp_id) {
        this.isp_id = isp_id;
    }

    public String getIsp_name() {
        return isp_name;
    }

    public void setIsp_name(String isp_name) {
        this.isp_name = isp_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

}
