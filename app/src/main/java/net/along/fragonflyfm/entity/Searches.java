package net.along.fragonflyfm.entity;

import net.lzzy.sqllib.Ignored;
import net.lzzy.sqllib.Sqlitable;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/11
 **/

public class Searches extends BaseRntity implements Sqlitable {
    private String name;   //名称
    private String province;  //省
    private String city;   //地址
    private String district; //区域
    private String location;  //位置

    @Ignored
    static final String COL_LOCATION="location";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @Override
    public boolean needUpdate() {
        return false;
    }
}
