package net.along.fragonflyfm.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import net.along.fragonflyfm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/1
 **/

public class AnalyzeFragment extends Fragment {
    private static final String TAG = "SubscriptionFragment";
    private View mRootView;
    private PieChart mPieChart;
    private LineChart mLineChart;
    private BarChart mBarChart;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_analyze, container, false);
        init();
        return mRootView;
    }

    private void init() {
        mPieChart = mRootView.findViewById(R.id.fragment_chart_p);
        mLineChart = mRootView.findViewById(R.id.fragment_chart_l);
        mBarChart = mRootView.findViewById(R.id.fragment_chart_b);

        mPieChart.setExtraOffsets(20f,20f,20f,20f);
        List<String> xvals=new ArrayList<>();//每个扇形的描述
        xvals.add("美国");
        xvals.add("中国");
        List<Integer> colors=new ArrayList<>();//每个扇形的颜色
        colors.add(Color.parseColor("#FF6633"));
        colors.add(Color.parseColor("#66CC99"));
    }
}
