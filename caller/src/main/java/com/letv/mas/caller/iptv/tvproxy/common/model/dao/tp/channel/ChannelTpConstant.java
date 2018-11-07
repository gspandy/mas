package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;

public class ChannelTpConstant {

    /**
     * get commodity data from cms block id
     */
    public static final String COMMODITY_CMS_BLOCKID = ApplicationUtils.get(ApplicationConstants.CMS_BLOCK_BUY_ID_GET);

    /**
     * get us url from cashier
     */
    public static final String US_CASHIER_URL = ApplicationUtils.get(ApplicationConstants.US_CASHIER_URL);

    /**
     * get us url from cashier
     */
    public static final String WF_SUBJECT_STATIC_URL = "http://10.58.93.22:8080/iptv/api/new/channel/v2/data/WFSubject/content/createWF?";
}
