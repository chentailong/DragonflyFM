package net.along.fragonflyfm.presenters;

import net.along.fragonflyfm.entity.Searches;
import net.along.fragonflyfm.interfaces.ISearchesDetailProgram;
import net.along.fragonflyfm.interfaces.ISearchesDetailViewCallback;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/15
 **/

public class SearchesDetailProgram implements ISearchesDetailProgram {

    private List<ISearchesDetailViewCallback> mCallbacks = new ArrayList<>();

    private Searches mTargetSearches = null;

    private SearchesDetailProgram() {
    }

    private static SearchesDetailProgram sInstance = null;

    public static SearchesDetailProgram getInstance() {
        if (sInstance == null) {
            synchronized (SearchesDetailProgram.class) {
                if (sInstance == null) {
                    sInstance = new SearchesDetailProgram();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void pullRefreshMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void getAlbumDetail(int page) {

    }

    @Override
    public void registerViewCallback(ISearchesDetailViewCallback detailViewCallback) {
        if (!mCallbacks.contains(detailViewCallback)) {
            mCallbacks.add(detailViewCallback);
            if (mTargetSearches != null) {
                detailViewCallback.onSearchesLaded(mTargetSearches);
            }
        }
    }

    @Override
    public void unRegisterViewCallback(ISearchesDetailViewCallback detailViewCallback) {
        mCallbacks.remove(detailViewCallback);
    }

    public void setTargetSearches(Searches searches) {
        this.mTargetSearches = searches;
    }
}
