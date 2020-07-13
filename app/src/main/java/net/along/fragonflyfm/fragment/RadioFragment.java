package net.along.fragonflyfm.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.base.BaseFragment;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/7
 **/

public class RadioFragment extends BaseFragment {
    private static final String TAG = "RadioFragment";
    private GridView mGridView;

    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_radio, container, false);
        mGridView = rootView.findViewById(R.id.fragment_radio_rv);
        return rootView;
    }
}
