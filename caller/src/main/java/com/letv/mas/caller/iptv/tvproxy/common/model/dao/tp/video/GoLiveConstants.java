package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;

public class GoLiveConstants {
    public static final String GOLIVE_SIGN = "golive";
    public static final String LETV_SIGN = "letv";

    public static final String GOLIVEDESKTOP_CMS_PAGEID = ApplicationUtils
            .get(ApplicationConstants.CMS_GOLIVEDESKTOP_PAGE_ID);

    public static String GOLIVEDESKTOP_CMS_PAGEID2 = ApplicationUtils
            .get(ApplicationConstants.CMS_GOLIVEDESKTOP_PAGE_ID2);
}
