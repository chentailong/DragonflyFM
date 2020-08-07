package net.along.fragonflyfm.util;


import java.util.Calendar;

public class GetTime {
    private static Calendar calendar = Calendar.getInstance();

    public static int dayOFWeek() {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static String getTime() {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return "" + hour + minute + second;
    }

    public static String getDate() {
        int year = calendar.get(Calendar.YEAR);
        int monthInt = 1 + calendar.get(Calendar.MONTH);
        String month = monthInt > 9 ? monthInt + "" : "0" + monthInt;
        int days = calendar.get(Calendar.DAY_OF_MONTH);
        String day = days > 9 ? days + "" : "0" + days;
        return "" + year + month + day;
    }

    public static String changeToPlayUrl(int channelId, String startTime, String endTime) {
        startTime = startTime.replace(":", "");
        endTime = endTime.replace(":", "");
        String urlStr = "https://lcache.qingting.fm/cache/" + getDate() + "/"
                + channelId + "/" + channelId + "_" + getDate() + "_" + startTime +
                "_" + endTime + "_24_0.aac";
        return urlStr;
        //https://lhttp.qingting.fm/live/ "content_id =1259"/64k.mp3?app_id=web&ts=5f2d0afe&sign=72cd8cbc545f646c05bc74cedcdf4f1e
        //https://lhttp.qingting.fm/live/1753/64k.mp3?app_id=web&ts=5f2d0b7f&sign=7a3f3667a476d8a3ed40c27da5cb3176
        //https://lhttp.qingting.fm/live/1753/64k.mp3?app_id=web&ts=5f2d0bad&sign=ec1c3115ceb1ec7ab08905e7b24235d0
    }
}
