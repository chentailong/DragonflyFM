package net.along.fragonflyfm.interfaces;

import com.ximalaya.ting.android.opensdk.model.track.Track;

import net.along.fragonflyfm.entity.Searches;

import java.util.List;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/15
 **/

public interface ISearchesDetailViewCallback {

    /**
     * 加载专辑成功
     *
     * @param tracks
     */
    void onDetailListLoaded(List<Track> tracks);

    /**
     * 把实体类中的数据传给UI使用
     *
     * @param searches
     */
    void onSearchesLaded(Searches searches);

}
