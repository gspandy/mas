package com.letv.mas.router.iptv.tvproxy.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by leeco on 18/10/16.
 */
public class TimeUtil {

    /**
     * 传入Data类型日期，返回字符串类型时间（ISO8601标准时间）
     *
     * @param date
     * @return
     */
    public static String getISO8601Timestamp(Date date) {
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        df.setTimeZone(tz);
        String nowAsISO = df.format(date);
        return nowAsISO;
    }

}
