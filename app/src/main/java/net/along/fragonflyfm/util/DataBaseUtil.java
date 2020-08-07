package net.along.fragonflyfm.util;

import java.util.Calendar;

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
}
