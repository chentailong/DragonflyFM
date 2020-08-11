package net.along.fragonflyfm.util;

import java.util.Calendar;

/**
 * 获取时间类
 */

public class GetTime {
    private static Calendar calendar = Calendar.getInstance();

    /**
     * 获取一周的时间
     *
     * @return
     */
    public static int dayOFWeek() {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取时分秒
     *
     * @return
     */
    public static String getTime() {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return "" + hour + minute + second;
    }

    /**
     * 获取年月日
     *
     * @return
     */
    public static String getDate() {
        int year = calendar.get(Calendar.YEAR);
        int monthInt = 1 + calendar.get(Calendar.MONTH);
        String month = monthInt > 9 ? monthInt + "" : "0" + monthInt;
        int days = calendar.get(Calendar.DAY_OF_MONTH);
        String day = days > 9 ? days + "" : "0" + days;
        return "" + year + month + day;
    }
}
