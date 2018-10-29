package com.letv.mas.caller.iptv.tvproxy.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {

    private static final Log log = LogFactory.getLog(TimeUtil.class);
    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String SHORT_DATE_FORMAT_NO_DASH = "yyyyMMdd";
    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String SHORT_DATE_HOUR_MINUTE_FORMAT = "MM-dd HH:mm";
    public static final String SIMPLE_DATE_HOUR_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String SIMPLE_DATE_FORMAT_NO_DASH = "yyyyMMddHHmmss";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATE_MM_DD_FORMAT1 = "MM-dd";

    /**
     * 直播专题展示使用的时间格式，未添加国际化支持，后期需添加
     */
    public static final String SIMPLE_DATE_HOUR_MINUTE_FORMAT_FOR_LIVE = "y年M月d日 HH:mm";
    public static final String SHORT_DATE_HOUR_MINUTE_FORMAT_FOR_LIVE = "M月d日 HH:mm";

    public static final String TIME_FORMAT_SEPARATOR = ":"; // 时间格式分隔符，即":"

    /**
     * 返回days天之后的时间戳
     * @param days
     * @return
     */
    public static Long getTimestampAfterDays(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTimeInMillis();
    }

    /**
     * 时间点比较，看compareTime是否在time之后
     * @param time
     *            基准时间
     * @param compareTime
     *            要比较的时间
     * @return
     */
    public static boolean afterTime(String time, String compareTime) {
        if (time == null || compareTime == null) {
            return false;
        }
        try {
            String[] times = time.split(":");
            String[] compareTimes = compareTime.split(":");
            for (int i = 0; i < times.length; i++) {
                int a = Integer.parseInt(times[i]);
                int b = Integer.parseInt(compareTimes[i]);
                if (a != b) {
                    return b > a;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    /**
     * 时间点比较，看compareTime是否在time之前
     * @param time
     *            基准时间
     * @param compareTime
     *            要比较的时间
     * @return
     */
    public static boolean beforeTime(String time, String compareTime) {
        if (time == null || compareTime == null) {
            return false;
        }
        try {
            String[] times = time.split(":");
            String[] compareTimes = compareTime.split(":");
            for (int i = 0; i < times.length; i++) {
                int a = Integer.parseInt(times[i]);
                int b = Integer.parseInt(compareTimes[i]);
                if (a != b) {
                    return b < a;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static int daysBetween(Calendar startTime, Calendar endTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("startTime is null");
        }
        if (endTime == null) {
            throw new IllegalArgumentException("endTime is null");
        }
        if (startTime.compareTo(endTime) > 0) {
            throw new IllegalArgumentException("endTime is before the startTime");
        }
        return (int) ((endTime.getTimeInMillis() - startTime.getTimeInMillis()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 根据传入的日期格式化pattern将传入的日期格式化成字符串�??
     * @param date
     *            要格式化的日期对�?
     * @param pattern
     *            日期格式化pattern
     * @return 格式化后的日期字符串
     */
    public static String format(final Date date, final String pattern) {
        try {
            DateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        } catch (Exception e) {
            log.error("format Date Error!" + e.getMessage(), e);
        }
        return null;
    }

    public static Date getDate(String time, String strFormat) {
        Date date = null;
        if (time == null || time.equals("")) {
            return date = null;
        }
        time = StringUtil.dealNull(time);
        try {
            SimpleDateFormat simple = new SimpleDateFormat(strFormat);
            date = simple.parse(time);
        } catch (Exception e) {
            log.error("Get Date Error!" + e.getMessage(), e);
        }
        return date;
    }

    /**
     * Long 型 timestamp 时间转成 yyyy-MM-dd HH:mm:ss 格式
     * @param timestamp
     * @return
     */
    public static String timestamp2date(Long timestamp) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp ts = new Timestamp(timestamp);
        return sd.format(ts);
    }

    public static Long string2timestamp(String dateStr) {
        if (StringUtil.isBlank(dateStr)) {
            return null;
        }
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        try {
            ts = Timestamp.valueOf(dateStr);
        } catch (Exception e) {
            ts = new Timestamp(Long.valueOf(dateStr));
        }
        return ts.getTime();
    }

    public static String seconds2HHmmWithoutZero(Integer seconds) {
        String result = "";
        int hhi = seconds / 60 / 60;
        int mmi = (seconds - hhi * 60 * 60) / 60;
        String mm = String.valueOf(mmi);
        String hh = String.valueOf(hhi);
        result = hh + ":" + mm;
        return result;
    }

    /**
     * 将yyyy | yyyy-MM | yyyy-MM-dd 格式的String转成long型
     * @param date
     * @return
     */
    public static Long date2Timestamp(String date) {
        Long timestamp = 0l;

        if (StringUtils.isNotEmpty(date)) {
            try {
                if (date.matches("\\d{4}")) {
                    DateFormat sdf = new SimpleDateFormat("yyyy");
                    timestamp = sdf.parse(date).getTime();
                } else if (date.matches("\\d{4}-\\d{2}")) {
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM");
                    timestamp = sdf.parse(date).getTime();
                } else if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    timestamp = sdf.parse(date).getTime();
                }
            } catch (Exception e) {
                log.error("date2Timestamp_ error", e);
            }
        }

        return timestamp;
    }

    /**
     * 获取零点时间
     * @param timestamp
     *            毫秒时间戳
     * @param offset
     *            偏移量
     *            例如timestamp = 1420507775463并且offset =
     *            24*60*60*1000l的时候，返回的时间戳是第二天的0点
     * @return
     */
    public static Long getZeroPiont(Long timestamp, Long offset) {
        Calendar cal = Calendar.getInstance();
        timestamp = timestamp + offset;
        cal.setTimeInMillis(timestamp);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static Calendar startOfDayTomorrow() {
        Calendar calendar = Calendar.getInstance();
        truncateDay(calendar);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }

    /**
     * create a calendar for start of day yesterday.
     * @return
     */
    public static Calendar startOfDayYesterday() {
        Calendar yesterday = Calendar.getInstance();
        truncateDay(yesterday);
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        return yesterday;
    }

    public static String someDayAfter(int n, String pattern) {
        Calendar _day = Calendar.getInstance();
        truncateDay(_day);
        _day.add(Calendar.DAY_OF_MONTH, n);
        return TimeUtil.format(_day.getTime(), pattern);
    }

    /**
     * Truncate the calendar's Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND,
     * Calendar.MILLISECOND to ZERO.
     * @param calendar
     * @return
     */
    public static Calendar truncateDay(Calendar calendar) {
        if (calendar == null) {
            throw new IllegalArgumentException("input is null");
        }
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * Return the String representation of the Calendar against the given
     * format.
     * @param calendar
     *            the date to format, such as 'yyyy-MM-dd HH:mm:ss' for long
     *            date format with 24H
     * @param format
     *            the date format pattern
     * @return the format Date String.
     */
    public static String getDateString(Calendar calendar, String format) {
        if (calendar == null) {
            return null;
        }
        return getDateString(calendar.getTime(), format);
    }

    /**
     * Return the String representation of the Date against the given format.
     * @param date
     *            the date to format
     * @param format
     *            the date format pattern
     * @return the format Date String.
     */
    public static String getDateString(Date date, String format) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * Parses the <tt>Date</tt> from the given date string and the format
     * pattern.
     * @param dateString
     * @param pattern
     *            the date format
     * @throws {@link IllegalArgumentException} if date format error
     * @return
     */
    public static Date parseDate(String dateString, String pattern) {
        Date date = null;
        try {
            if (StringUtils.isNotBlank(dateString)) {
                DateFormat format = new SimpleDateFormat(pattern);
                date = format.parse(dateString);
            }
        } catch (ParseException ex) {
        }
        return date;
    }

    /**
     * Parses the <tt>Date</tt> from the given date string and the format
     * pattern and return it as a {@link Calendar} instance.
     * @param dateString
     * @param pattern
     *            the date format
     * @throws {@link IllegalArgumentException} if date format error
     * @return
     */
    public static Calendar parseCalendar(String dateString, String pattern) {
        Calendar c = null;
        Date date = TimeUtil.parseDate(dateString, pattern);
        if (date != null) {
            c = Calendar.getInstance();
            c.setTime(date);
        }
        return c;
    }

    /**
     * return one day before the given date.
     * @param date
     *            the given date
     * @return the adjusted date.
     */
    public static Calendar backOneDay(Calendar date) {
        Calendar cal = (Calendar) date.clone();
        cal.add(Calendar.DATE, -1);
        return cal;
    }

    public static long getSimpleDateTimeMillis(long timeMillis) {
        Date date = new Date(timeMillis);
        String dateStr = TimeUtil.getDateString(date, TimeUtil.SHORT_DATE_FORMAT);
        Date transformDate = TimeUtil.parseDate(dateStr, TimeUtil.SHORT_DATE_FORMAT);
        return transformDate.getTime();
    }

    public static long getSimpleDateTimeMillis(long timeMillis, String format) {
        Date date = new Date(timeMillis);
        String dateStr = TimeUtil.getDateString(date, format);
        Date transformDate = TimeUtil.parseDate(dateStr, format);
        return transformDate.getTime();
    }

    /**
     * get the date from a day with days
     * @author <a href="mailto:wang-shuai@letv.com">Ousui</a>
     * @param from
     *            which day
     * @param days
     *            interval days, 0: today; positive: future; negative: history.
     * @return
     */
    public static Calendar getDateFromDate(Date from, int days) {
        Calendar now = null;

        if (from != null) {
            long froml = from.getTime();
            long interval = days * 24l * 60l * 60l * 1000l;
            long millis = froml + interval;
            now = Calendar.getInstance();
            now.setTimeInMillis(millis);
        }

        return now;
    }

    public static String getDateFromDate(String from, int days, String format) {
        Date d = TimeUtil.parseDate(from, format);
        Calendar c = TimeUtil.getDateFromDate(d, days);
        return TimeUtil.getDateString(c, format);
    }

    public static String getDateStringFromLong(Long valueOf, String shortDateFormat) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(shortDateFormat);
            Date dt = new Date(valueOf);
            return sdf.format(dt);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getDateFromDate(Date from, int days, String format) {
        Calendar c = TimeUtil.getDateFromDate(from, days);
        return TimeUtil.getDateString(c, format);
    }

    /**
     * Local time converted to a timeStamp GMT
     * @param dateString
     * @param pattern
     * @return
     */
    public static long getGmtTime(String dateString, String pattern) {
        if (StringUtil.isNotBlank(dateString) && StringUtil.isNotBlank(pattern)) {
            Calendar cal = parseCalendar(dateString, pattern);
            // Time Offset
            int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
            // Daylight saving time difference
            int dstOffset = cal.get(Calendar.DST_OFFSET);
            cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
            return cal.getTimeInMillis();
        } else {
            return 0;
        }
    }

    /**
     * Local time converted to a timeStamp GMT
     * @param date
     * @param pattern
     * @return
     */
    public static long getGmtTime(Date date, String pattern) {
        if (date != null && StringUtil.isNotBlank(pattern)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            // Time Offset
            int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
            // Daylight saving time difference
            int dstOffset = cal.get(Calendar.DST_OFFSET);
            cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
            return cal.getTimeInMillis();
        } else {
            return 0;
        }
    }

    /**
     * 将北京时间转换成时间戳
     * @param date
     * @param format
     * @return
     */
    public static String parseDate2Timestamp(String date, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return "" + dateFormat.parse(date).getTime();
        } catch (ParseException e) {
        }
        return "";
    }

    /**
     * 将 HH:mm:ss时间格式的string转成long型（毫秒）
     */
    public static Long hhmmss2Timestamp(String time) {
        Long timestamp = 0l;

        if (StringUtils.isNotEmpty(time) && time.matches("\\d{2}:\\d{2}:\\d{2}")) {
            String[] timeStr = time.split(":");
            timestamp = Long.valueOf(timeStr[0]) * 60 * 60 * 1000 + Long.valueOf(timeStr[1]) * 60 * 1000
                    + Long.valueOf(timeStr[2]) * 1000;
        }

        return timestamp;
    }

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
