package net.along.fragonflyfm.interfaces;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/15
 **/

public interface ISearchesDetailProgram {

    /**
     * 下拉刷新更多内容
     */
    void pullRefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();

    /**
     * 获取页码
     *
     * @param page
     */
    void getAlbumDetail(int page);


    /**
     * 注册UI通知接口
     *
     * @param detailViewCallback
     */
    void registerViewCallback(ISearchesDetailViewCallback detailViewCallback);


    /**
     * 删除UI通知接口
     *
     * @param detailViewCallback
     */
    void unRegisterViewCallback(ISearchesDetailViewCallback detailViewCallback);
}
