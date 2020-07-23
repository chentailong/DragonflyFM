package net.along.fragonflyfm.entity;

import android.graphics.Bitmap;

import net.lzzy.sqllib.Sqlitable;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/16
 **/

public class Program  implements Sqlitable {

    private int content_id;
    private String title;  //节目名称
    private String username;  //主播名字
    private String usernames;
    private String audience_count;  //观看人数
    private String start_time; //开始时间
    private String end_time; //结束时间
    private Bitmap mBitmap;
    private String thumb;

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public String getUsernames() {
        return usernames;
    }

    public void setUsernames(String usernames) {
        this.usernames = usernames;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

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

    public String getAudience_count() {
        return audience_count;
    }

    public void setAudience_count(String audience_count) {
        this.audience_count = audience_count;
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
                "content_id='" + content_id + '\'' +
                ", title='" + title + '\'' +
                ", username='" + username + '\'' +
                ", audience_count='" + audience_count + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", mBitmap=" + mBitmap +
                ", thumb='" + thumb + '\'' +
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
