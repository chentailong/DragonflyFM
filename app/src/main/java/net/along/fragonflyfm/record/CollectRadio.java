package net.along.fragonflyfm.record;

import com.orm.SugarRecord;

/**
 * 创建者 by:陈泰龙
 * <p>实现收藏功能的保存
 * * 2020/9/2
 **/

public class CollectRadio extends SugarRecord {

    private String title;
    private String imgUrl;
    private int channel_id;
    private int audience_count;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public int getAudience_count() {
        return audience_count;
    }

    public void setAudience_count(int audience_count) {
        this.audience_count = audience_count;
    }

    @Override
    public String toString() {
        return "CollectRadio{" +
                "title='" + title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", channel_id=" + channel_id +
                ", audience_count=" + audience_count +
                '}';
    }
}
