package com.letv.mas.caller.iptv.tvproxy.terminal.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.BrandSourceMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.PlatformSourceMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.SeriesSourceMysqlTable;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class RelationInfoDto extends BaseDto {
    public List<BrandSourceMysqlTable> brandSource;

    public List<SeriesSourceMysqlTable> seriesSource;

    public List<PlatformSourceMysqlTable> platAndAppformSource;

    public RelationInfoDto(List<BrandSourceMysqlTable> brandSource, List<SeriesSourceMysqlTable> seriesSource,
            List<PlatformSourceMysqlTable> platAndAppformSource) {
        this.brandSource = brandSource;
        this.seriesSource = seriesSource;
        this.platAndAppformSource = platAndAppformSource;
    }

    public List<BrandSourceMysqlTable> getBrandSource() {
        return this.brandSource;
    }

    public void setBrandSource(List<BrandSourceMysqlTable> brandSource) {
        this.brandSource = brandSource;
    }

    public List<SeriesSourceMysqlTable> getSeriesSource() {
        return this.seriesSource;
    }

    public void setSeriesSource(List<SeriesSourceMysqlTable> seriesSource) {
        this.seriesSource = seriesSource;
    }

    public List<PlatformSourceMysqlTable> getPlatAndAppformSource() {
        return this.platAndAppformSource;
    }

    public void setPlatAndAppformSource(List<PlatformSourceMysqlTable> platAndAppformSource) {
        this.platAndAppformSource = platAndAppformSource;
    }

}
