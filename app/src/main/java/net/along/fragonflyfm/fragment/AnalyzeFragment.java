package net.along.fragonflyfm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import net.along.fragonflyfm.R;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/1
 **/

public class AnalyzeFragment extends Fragment {
    private static final String TAG = "SubscriptionFragment";
    private static final float MIN_TOUCH_DISTANCE = 10f;
    private final String[] titles = {"APP访问次数", "各地区访问比例（%）", "最受欢迎电台", "最受欢迎节目", "电台类型倾向"};
    private View mRootView;
    private float touchX1;
    private int chartIndex = 0;
    private View[] dots;
    private Chart[] mChart;
    private PieChart mPieChart;
    private LineChart mLineChart1;
    private LineChart mLineChart2;
    private BarChart mBarChart;
    private RadarChart mRadarChart;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_analyze, container, false);
        init();
        return mRootView;
    }

    /**
     * 初始化
     */
    private void init() {
        View dot1 = mRootView.findViewById(R.id.fragment_chart_dot1);
        View dot2 = mRootView.findViewById(R.id.fragment_chart_dot2);
        View dot3 = mRootView.findViewById(R.id.fragment_chart_dot3);
        View dot4 = mRootView.findViewById(R.id.fragment_chart_dot4);
        View dot5 = mRootView.findViewById(R.id.fragment_chart_dot5);
        dots = new View[]{dot1, dot2, dot3, dot4, dot5};
        mPieChart = mRootView.findViewById(R.id.fragment_chart_p);
        mLineChart1 = mRootView.findViewById(R.id.fragment_chart_l1);
        mLineChart2 = mRootView.findViewById(R.id.fragment_chart_l2);
        mBarChart = mRootView.findViewById(R.id.fragment_chart_b);
        mRadarChart = mRootView.findViewById(R.id.fragment_chart_r);
        mChart = new Chart[]{mLineChart1, mPieChart, mBarChart, mLineChart2, mRadarChart};

        int i = 0;
        for (Chart chart : mChart) {
            chart.setTouchEnabled(false);
            chart.setVisibility(View.VISIBLE);
            Description desc = new Description();
            desc.setText(titles[i++]);
            chart.setDescription(desc);
            chart.setNoDataText("数据获取中......");
            chart.setExtraOffsets(5, 10, 5, 25);
        }
        mRootView.findViewById(R.id.fragment_chart_container).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    touchX1 = event.getX();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float touchX2 = event.getX();
                    if (Math.abs(touchX1 - touchX2) > MIN_TOUCH_DISTANCE) {
                        if (touchX2 < touchX1) {
                            if (chartIndex < mChart.length - 1) {
                                chartIndex++;
                            } else {
                                chartIndex = 0;
                            }
                        } else {
                            if (chartIndex > 0) {
                                chartIndex--;
                            } else {
                                chartIndex = mChart.length - 1;
                            }
                        }
                        switchChart();
                    }
                }
                return true;
            }
        });
        configPieChart();
        configBarLineChart(mLineChart1);
        configBarLineChart(mBarChart);
        mPieChart.setVisibility(View.VISIBLE);
    }

    private void configPieChart() {
        mPieChart.setUsePercentValues(true);
        mPieChart.setDrawHoleEnabled(false);
        mPieChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
    }


    private void configBarLineChart(BarLineChartBase chart) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(8f);
        xAxis.setGranularity(1f);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setTextSize(8f);
        leftAxis.setGranularity(1f);
        leftAxis.setAxisMinimum(0f);
        chart.getLegend().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.setPinchZoom(false);
    }

    //切换时颜色变化
    private void switchChart() {
        for (int i = 0; i < mChart.length; i++) {
            if (chartIndex == i) {
                mChart[i].setVisibility(View.VISIBLE);
                dots[i].setBackgroundResource(R.drawable.btn_flat_fill_style);
            } else {
                mChart[i].setVisibility(View.GONE);
                dots[i].setBackgroundResource(R.drawable.btn_flat_style);
            }
        }
    }
}
