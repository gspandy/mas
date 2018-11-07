package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1;

import java.io.Serializable;

/**
 * 弹幕配置实体
 */
public class DanMuDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1399216915720282335L;
    private Integer enable;
    private Integer def;
    private Integer line;
    private Integer renderType;

    public Integer getRenderType() {
        return this.renderType;
    }

    public void setRenderType(Integer renderType) {
        this.renderType = renderType;
    }

    public Integer getEnable() {
        return this.enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getDef() {
        return this.def;
    }

    public void setDef(Integer def) {
        this.def = def;
    }

    public Integer getLine() {
        return this.line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

}
