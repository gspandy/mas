package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;

public abstract class AbstractBurrow {

    protected BurrowUtil burrowUtil;

    public AbstractBurrow(BurrowUtil burrowUtil) {
        this.burrowUtil = burrowUtil;
    }

    public AbstractBurrow() {
    }

    public abstract BaseData burrowObj(BaseData data, CommonParam commonParam);

}
