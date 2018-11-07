package com.letv.mas.caller.iptv.tvproxy.channel.util;

import com.letv.mas.caller.iptv.tvproxy.channel.constants.ChannelConstants;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelUtil {
    private static final Logger log = LoggerFactory.getLogger(ChannelUtil.class);

    public static String buildRecUrlDir(String pageId) {
        if (StringUtil.isNotBlank(pageId)) {
            pageId = ChannelConstants.CHANNEL_UITYPE_RECURL_FIXPRE + pageId;
        }
        return pageId;

    }

    /**
     * 儿童桌面获取年龄对应的页面id
     * @param age
     * @return
     */
    public static String getPageIdByAge(Integer age) {
        ChannelConstants.setChildPageIdsMap();
        if (age != null && age < 12) {
            return ChannelConstants.CHILD_AGE_PAGEID.get(age);
        } else {
            return ChannelConstants.CHILD_AGE_PAGEID.get(0);
        }
    }

    /**
     * 儿童答题游戏页面id
     * @param age
     * @return
     */
    public static String getPageIdByAgeOfAQ(Integer age) {
        ChannelConstants.setChildPageIdsMap();
        if (age != null && age < 12) {
            return ChannelConstants.CHILD_AGE_PAGEID.get(age);
        } else {
            return ChannelConstants.CHILD_AGE_PAGEID.get(0);
        }
    }

}
