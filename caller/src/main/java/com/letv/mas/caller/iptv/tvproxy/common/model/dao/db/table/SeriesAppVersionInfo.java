package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

import java.util.Date;

/**
 * SERIES_APP_VERSION表和VERSION表组合
 */
public class SeriesAppVersionInfo {
    private static final long serialVersionUID = -4976016354614124110L;
    private Long id;
    private Long version_id;
    private Integer series_app_id;
    private Integer recommend_up_id;// 推荐升级版本id
    private Integer enforce_up_id;// 强制升级id
    private Integer no_up_id;// 禁止升级id

    private String version_name;
    private Integer version_code;
    private Long platform_id;
    private String download_url;
    private String description;
    private Integer status;
    private Date create_time;
    private String created_by;
    private Date update_time;
    private String updated_by;
    private Integer type;
    private String other;
    private String rom_minimum;
    private String md5;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion_id() {
        return this.version_id;
    }

    public void setVersion_id(Long version_id) {
        this.version_id = version_id;
    }

    public Integer getSeries_app_id() {
        return this.series_app_id;
    }

    public void setSeries_app_id(Integer series_app_id) {
        this.series_app_id = series_app_id;
    }

    public Integer getRecommend_up_id() {
        return this.recommend_up_id;
    }

    public void setRecommend_up_id(Integer recommend_up_id) {
        this.recommend_up_id = recommend_up_id;
    }

    public Integer getEnforce_up_id() {
        return this.enforce_up_id;
    }

    public void setEnforce_up_id(Integer enforce_up_id) {
        this.enforce_up_id = enforce_up_id;
    }

    public Integer getNo_up_id() {
        return this.no_up_id;
    }

    public void setNo_up_id(Integer no_up_id) {
        this.no_up_id = no_up_id;
    }

    public String getVersion_name() {
        return this.version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public Integer getVersion_code() {
        return this.version_code;
    }

    public void setVersion_code(Integer version_code) {
        this.version_code = version_code;
    }

    public Long getPlatform_id() {
        return this.platform_id;
    }

    public void setPlatform_id(Long platform_id) {
        this.platform_id = platform_id;
    }

    public String getDownload_url() {
        return this.download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreate_time() {
        return this.create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getCreated_by() {
        return this.created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public Date getUpdate_time() {
        return this.update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getUpdated_by() {
        return this.updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOther() {
        return this.other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getRom_minimum() {
        return this.rom_minimum;
    }

    public void setRom_minimum(String rom_minimum) {
        this.rom_minimum = rom_minimum;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

}
