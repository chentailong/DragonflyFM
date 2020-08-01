package net.along.fragonflyfm.util;


import java.util.Calendar;

public class GetTime {
    private static Calendar calendar=Calendar.getInstance();

    public static int dayOFWeek(){
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static String getDate(){
        int year=calendar.get(Calendar.YEAR);
        int monthInt=1+calendar.get(Calendar.MONTH);
        String month=monthInt>9?monthInt+"":"0"+monthInt;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        return ""+year+month+day;
    }

    public static String changeToPlayUrl(int channelId,String startTime,String endTime){
        startTime=startTime.replace(":","");
        endTime=endTime.replace(":","");
        String urlStr="https://lcache.qingting.fm/cache/"+getDate()+"/"
                +channelId+"/"+channelId+"_"+getDate()+"_"+startTime+
                "_"+endTime+"_24_0.aac";
        return urlStr;
    }
}
