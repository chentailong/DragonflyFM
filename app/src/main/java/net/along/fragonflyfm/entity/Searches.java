package net.along.fragonflyfm.entity;

import android.graphics.Bitmap;

import net.lzzy.sqllib.Ignored;
import net.lzzy.sqllib.Sqlitable;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/11
 **/

public class Searches implements Sqlitable {

    private String province;  //省

    private String city;   //地址
    private String district; //区域
    private String location;  //位置
    private String name;
    private String title;   //电台名称
    private int id;    //电台ID
    private int cover;//电台图片
    private String audience_count;   //观看人数
    private Bitmap mBitmap;

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    @Ignored
    static final String COL_LOCATION = "location";

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    public String getAudience_count() {
        return audience_count;
    }

    public void setAudience_count(String audience_count) {
        this.audience_count = audience_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Searches{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", location='" + location + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                ", cover=" + cover +
                ", audience_count='" + audience_count + '\'' +
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
