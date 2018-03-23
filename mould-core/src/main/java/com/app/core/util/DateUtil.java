package com.app.core.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class DateUtil {
    /**
     * 日期格式    yyyy-MM-dd
     */
    public static final String FMT_DATE = "yyyy-MM-dd";
    /**
     * 时间格式    yyyy-MM-dd HH:mm:ss
     */
    public static final String FMT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式    yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String FMT_FULL_DATETIME = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * 短日期格式   yyyyMMdd
     */
    public static final String FMT_DATE_SHORT = "yyyyMMdd";
    /**
     * 短时间格式   yyyyMMddHHmmss
     */
    public static final String FMT_DATETIME_SHORT = "yyyyMMddHHmmss";

    /**
     * 获取秒级时间戳
     *
     * @return
     */
    public static String getSecondTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 获取毫秒级时间戳
     *
     * @return
     */
    public static String getMillisTimestamp() {
        return Long.toString(System.currentTimeMillis());
    }

    /**
     * 获取当前日期时间
     * <p>返回示例: yyyy-MM-dd HH:mm:ss </p>
     *
     * @return
     */
    public static String getCurrentDateTime() {
        return getCurrentDateTime(FMT_FULL_DATETIME);
    }

    /**
     * 获取当前日期时间
     *
     * @param pattern
     * @return
     */
    public static String getCurrentDateTime(String pattern) {
        return getDateTime(new Date(), pattern);
    }

    /**
     * 将日期类型转换为指定格式字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String getDateTime(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }
}