package net.along.fragonflyfm.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/1
 **/

public abstract class BaseFragment extends Fragment {
    private  View mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = onSubViewLoaded(inflater,container);
        return mRootView;
    }


    protected abstract View onSubViewLoaded(LayoutInflater inflater, ViewGroup container);
}
