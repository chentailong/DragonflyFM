package net.along.fragonflyfm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 创建者 by:陈泰龙
 * <p>时间戳与字符串的关系 以及获取当前时间
 * 2020/8/21
 **/

public class DateUtils {

    /**
     * 获取当前时间
     * @return
     */
    public static String getCurrentDate(String pattern){
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new java.util.Date());
    }

    /**
     * 时间戳转化成字符串
     *
     * @param time
     * @param pattern
     * @return
     */
    public static String getDateString(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(date);
    }

    public static long getDateStrings(long time, String pattern) throws ParseException {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date dates = format.parse("2020/08/21");
        long times = dates.getTime();
        return times;
    }
}
