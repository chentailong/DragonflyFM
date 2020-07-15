package net.along.fragonflyfm.interfaces;

import net.along.fragonflyfm.entity.Searches;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/15
 **/

public interface ISearchesDetailViewCallback {

    /**
     * 把实体类中的数据传给UI使用
     *
     * @param searches
     */
    void onSearchesLaded(Searches searches);

}
