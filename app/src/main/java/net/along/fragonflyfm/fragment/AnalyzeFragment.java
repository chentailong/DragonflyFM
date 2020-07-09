package net.along.fragonflyfm.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.along.fragonflyfm.R;
import net.along.fragonflyfm.base.BaseFragment;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/1
 **/

public class AnalyzeFragment extends BaseFragment {
    private static final String TAG = "SubscriptionFragment";
    private View mRootView;

    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        mRootView = inflater.inflate(R.layout.fragment_analyze, container, false);
        return mRootView;
    }
}
