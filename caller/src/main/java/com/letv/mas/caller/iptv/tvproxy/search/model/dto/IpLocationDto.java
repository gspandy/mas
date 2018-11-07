package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.IpLocationResponse;

public class IpLocationDto extends BaseDto {
    private String countryCode; // 国家编码
    private String countryName; // 国家名称
    private String provinceId; // 省份ID
    private String provinceName; // 省份名称
    private String distinctId; // 地区id
    private String distinctName; // 地区名称
    private String ispId; // 运营商id
    private String ispName; // 运行商name
    private String cityName; // 城市名称

    public IpLocationDto() {
    }

    public IpLocationDto(IpLocationResponse location) {
        if (location != null) {
            this.countryCode = location.getCountry_code();
            this.countryName = location.getCountry_name();
            this.provinceId = location.getProvince_id();
            this.provinceName = location.getProvince_name();
            this.distinctId = location.getDistinct_id();
            this.distinctName = location.getDistinct_name();
            this.ispId = location.getIsp_id();
            this.ispName = location.getIsp_name();
            this.cityName = location.getCity_name();
        }
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getProvinceId() {
        return this.provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return this.provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getDistinctId() {
        return this.distinctId;
    }

    public void setDistinctId(String distinctId) {
        this.distinctId = distinctId;
    }

    public String getDistinctName() {
        return this.distinctName;
    }

    public void setDistinctName(String distinctName) {
        this.distinctName = distinctName;
    }

    public String getIspId() {
        return this.ispId;
    }

    public void setIspId(String ispId) {
        this.ispId = ispId;
    }

    public String getIspName() {
        return this.ispName;
    }

    public void setIspName(String ispName) {
        this.ispName = ispName;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}
