package net.along.fragonflyfm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orm.SugarRecord;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.adapter.CollectAdapter;
import net.along.fragonflyfm.record.CollectRadio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 创建者 by:胡大航
 * <p>
 * 2020/7/7
 **/

public class RadioFragment extends Fragment {
    private static final String TAG = "RadioFragment";
    private RecyclerView listView;//列表控件
    private SwipeRefreshLayout refreshLayout;//刷新控件
    private View mView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_radio, container, false);
        Toolbar toolbar = mView.findViewById(R.id.favor_toolbar);
        toolbar.setTitle("我的收藏");
        listView = mView.findViewById(R.id.favor_recycler_view);
        refreshLayout = mView.findViewById(R.id.refresh_layout);
        listView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));
        refreshLayout.setColorSchemeResources(R.color.colorRed);
        refreshLayout.setOnRefreshListener(() -> {
            showFavourite();
            refreshLayout.setRefreshing(false);
        });
        showFavourite();
        return mView;
    }

    //刷新列表，显示收藏数据
    private void showFavourite() {
        Iterator<CollectRadio> iterator = SugarRecord.findAll(CollectRadio.class);
        List<CollectRadio> radios = new ArrayList<>();        //将所有数据添到List，以供使用
        while (iterator.hasNext()) {
            radios.add(iterator.next());
        }
        CollectAdapter adapter = new CollectAdapter(getActivity(), radios);
        listView.setAdapter(adapter);
    }
}
