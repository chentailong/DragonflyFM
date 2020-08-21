package net.along.fragonflyfm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.along.fragonflyfm.R;

/**
 * 创建者 by:胡大航
 * <p>
 * 2020/7/7
 **/

public class RadioFragment extends Fragment {
    private static final String TAG = "RadioFragment";
    private GridView mGridView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_radio, container, false);
        mGridView = rootView.findViewById(R.id.fragment_radio_rv);
        return rootView;
    }
}
