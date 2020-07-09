package net.along.fragonflyfm.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.base.BaseFragment;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/7
 **/

public class RadioFragment extends BaseFragment {
    private static final String TAG = "RadioFragment";
    private RecyclerView mRecyclerView;

    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_radio, container, false);
        mRecyclerView = rootView.findViewById(R.id.fragment_radio_rv);
        return rootView;
    }
}
