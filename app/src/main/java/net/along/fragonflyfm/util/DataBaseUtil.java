package net.along.fragonflyfm.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 创建者 by:陈泰龙
 * <p>时间戳与字符串的关系 以及获取当前时间  判断数据库时间与当前时间是否一致
 * 2020/8/21
 **/

public class DataBaseUtil {

    /**
     * 判断是否是同一天
     * @param nowCalendar
     * @param objCalendar
     * @return
     */
    public static boolean isToday(Calendar nowCalendar,Calendar objCalendar){
        int nowYear=nowCalendar.get(Calendar.YEAR);
        int nowMoth=nowCalendar.get(Calendar.MONTH)+1;
        int nowDay=nowCalendar.get(Calendar.DAY_OF_MONTH);

        int objYear=objCalendar.get(Calendar.YEAR);
        int objMoth=objCalendar.get(Calendar.MONTH)+1;
        int objDay=objCalendar.get(Calendar.DAY_OF_MONTH);

        if (objDay==nowDay&&objMoth==nowMoth&&objYear==nowYear)return true; else return false;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentDate(String pattern) {
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
}
