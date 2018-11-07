package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto;

import java.io.Serializable;

public class WasuDto implements Serializable {

    private static final long serialVersionUID = -6646173745056456718L;

    private Integer Type;
    private String Id;
    private String Categoryid;
    private String Isvideo;

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCategoryid() {
        return Categoryid;
    }

    public void setCategoryid(String categoryid) {
        Categoryid = categoryid;
    }

    public String getIsvideo() {
        return Isvideo;
    }

    public void setIsvideo(String isvideo) {
        Isvideo = isvideo;
    }

}
