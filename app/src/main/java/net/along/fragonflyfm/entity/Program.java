package net.along.fragonflyfm.entity;

import net.lzzy.sqllib.Sqlitable;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/16
 **/

public class Program  implements Sqlitable {
    private String title;  //节目名称
    private String username;  //主播
    private int number;  //观看人数
    private String start_time; //开始时间
    private String end_time; //结束时间

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    @Override
    public String toString() {
        return "Program{" +
                "title='" + title + '\'' +
                ", username='" + username + '\'' +
                ", number=" + number +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                '}';
    }
    @Override
    public Object getIdentityValue() {
        return null;
    }

    @Override
    public boolean needUpdate() {
        return false;
    }
}
