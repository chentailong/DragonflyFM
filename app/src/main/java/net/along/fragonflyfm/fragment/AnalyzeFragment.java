package net.along.fragonflyfm.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.record.AppVisitCount;
import net.along.fragonflyfm.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/1
 **/

public class AnalyzeFragment extends Fragment {
    private final String[] titles = {"APP访问次数", "各地区访问比例（%）", "最受欢迎电台", "最受欢迎节目", "电台类型倾向"};
    private static final String TAG = "AnalyzeFragment";
    private String pattern = "yyyy-MM-dd";
    private static final float MIN_TOUCH_DISTANCE = 10f;
    private TextView title_message;
    private int chartIndex = 0;
    private View mRootView;
    private float touchX1;
    private View[] dots;
    private Chart[] mChart;
    private PieChart mPieChart;
    private LineChart mLineChart1;
    private LineChart mLineChart2;
    private BarChart mBarChart;
    private RadarChart mRadarChart;
    private RadioGroup mRadioGroup;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_analyze, container, false);
        initView();
        PieChart();   //饼图
        return mRootView;
    }

    /**
     * 初始化视图数据，实现滑动变化 MPAndroid布局
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        mRadioGroup = mRootView.findViewById(R.id.radioGroup_time);
        mRadioGroup.setOnCheckedChangeListener(RadioGroup);
        title_message = mRootView.findViewById(R.id.analyze_titles);
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
        for (Chart chart : mChart) {
            chart.setTouchEnabled(false);     //可滑动
            chart.setVisibility(View.GONE);  //隐藏其他视图
            chart.setNoDataText("数据获取中......");
            chart.setExtraOffsets(5, 10, 5, 25);
        }
        mRootView.findViewById(R.id.fragment_chart_container).setOnTouchListener((v, event) -> {
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
        }); //滑动界面的变化
        mLineChart1.setVisibility(View.VISIBLE);
    }

    /**
     * App点击次数的线性图表测试，暂无真实数据
     *  @param
     * @param count
     */
    private void LineChart( int count) {
        ArrayList<Entry> entry = new ArrayList<>();
        entry.add(new Entry(2,count));

//        entry.add(new Entry(6, 15));
//        entry.add(new Entry(9, 20));
//        entry.add(new Entry(12, 5));
//        entry.add(new Entry(15, 30));
        LineDataSet set1;
        Description desc = new Description();
        desc.setText("");
        mLineChart1.setDescription(desc);
        if (mLineChart1.getData() != null && mLineChart1.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mLineChart1.getData().getDataSetByIndex(0);
            set1.setValues(entry);
            mLineChart1.getData().notifyDataChanged();
            mLineChart1.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(entry, "X：日期  Y：访问次数");
            set1.setColor(Color.RED); //字体颜色
            set1.setCircleColor(Color.RED);  //圆圈颜色
            set1.setFillColor(Color.RED);   //圆圈颜色
            set1.setLineWidth(1f);//设置线宽
            set1.setCircleRadius(4f);//设置焦点圆心的大小
            set1.setValueTextSize(12f);//设置显示值的文字大小
            set1.setDrawFilled(false);//设置禁用范围背景填充

            XAxis xAxis = mLineChart1.getXAxis(); //X轴
            xAxis.setEnabled(true);//设置轴启用或禁用 如果禁用以下的设置全部不生效
            xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
            xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
            xAxis.setAvoidFirstLastClipping(true);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
            xAxis.setLabelRotationAngle(10f);//设置x轴标签的旋转角
            YAxis rightAxis = mLineChart1.getAxisRight();  //获取右边的轴线
            rightAxis.setEnabled(false);//设置图表右边的y轴禁用
            YAxis leftAxis = mLineChart1.getAxisLeft();//获取左边的轴线
            leftAxis.setDrawZeroLine(true);//是否绘制0所在的网格线
            Legend l = mLineChart1.getLegend();// 设置图例
            l.setTextSize(10f);//设置文字大小
            l.setForm(Legend.LegendForm.CIRCLE);//正方形，圆形或线
            l.setFormSize(10f); // 设置Form的大小
            l.setWordWrapEnabled(true);//是否支持自动换行
            l.setFormLineWidth(10f);//设置Form的宽度

            ArrayList<ILineDataSet> dataSets = new ArrayList<>(); //保存LineDataSet集合
            dataSets.add(set1);  //保存数据设置
            LineData data = new LineData(dataSets); //创建LineData对象 属于LineChart折线图的数据集合
            mLineChart1.setData(data);  // 添加到图表中
            mLineChart1.invalidate(); //绘制图表
        }
    }

    /**
     * 各地区访问次数的饼图测试，暂无真实数据
     */
    private void PieChart() {
        ArrayList<PieEntry> entry = new ArrayList<>();
        entry.clear();
        entry.add(new PieEntry(10f, "广西"));
        entry.add(new PieEntry(30f, "广东"));
        entry.add(new PieEntry(20f, "北京"));
        entry.add(new PieEntry(20f, "上海"));
        entry.add(new PieEntry(10f, "天津"));
        entry.add(new PieEntry(10f, "海南"));
        mPieChart.setUsePercentValues(true); //设置是否显示百分比
        mPieChart.getDescription().setEnabled(false);  //取消Description字体的显示
        mPieChart.setExtraOffsets(5, 5, 5, 5);
        mPieChart.setEntryLabelColor(Color.BLACK);//设置pieChart图表文本字体颜色
        mPieChart.setEntryLabelTextSize(15f);//设置pieChart图表文本字体大小
        mPieChart.setDrawHoleEnabled(false);  //取消中心圆
        mPieChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        Legend legend = mPieChart.getLegend(); //获取图例
        legend.setXEntrySpace(7f); //设置图例实体之间延X轴的间距（setOrientation = HORIZONTAL有效）
        legend.setYEntrySpace(0f); //设置图例实体之间延Y轴的间距（setOrientation = VERTICAL 有效）
        legend.setYOffset(25f);  //图例的y偏移量
        legend.setXOffset(10f);  //图例x的偏移量
        legend.setTextSize(13);  //图例文字的大小
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);  //设置图例水平显示
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM); //顶部
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT); //右对其

        PieDataSet dataSet = new PieDataSet(entry, "各地区访问比例%");
        ArrayList<Integer> integer = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            integer.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            integer.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            integer.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            integer.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            integer.add(c);
        integer.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(integer);  //设置颜色

        PieData data = new PieData(dataSet);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(15f);
        data.setValueFormatter(new PercentFormatter());
        mPieChart.setData(data);
        mPieChart.invalidate();
    }

    /**
     * 切换时底部圆圈颜色变化 And 标题变化
     */
    private void switchChart() {
        for (int i = 0; i < mChart.length; i++) {
            if (chartIndex == i) {
                mChart[i].setVisibility(View.VISIBLE);
                dots[i].setBackgroundResource(R.drawable.btn_flat_fill_style);
                title_message.setText(titles[i]);
            } else {
                mChart[i].setVisibility(View.GONE);
                dots[i].setBackgroundResource(R.drawable.btn_flat_style);
            }
        }
    }


    /**
     * 点击选择时间，查询活动轨迹
     */
    private RadioGroup.OnCheckedChangeListener RadioGroup = (group, checkedId) -> {
        switch (group.getCheckedRadioButtonId()) {
            case R.id.button_day:
                String nowTime = DateUtils.getCurrentDate(pattern);  //获取当前时间
                List<AppVisitCount> list = AppVisitCount.listAll(AppVisitCount.class); //查询所有数据
                Log.e(TAG, ": " + list);
                AppVisitCount app = AppVisitCount.findById(AppVisitCount.class, 4);  //根据ID查询数据
                int count = app.getCount();  //次数
                long timeStamp = app.getTimeStamp();  //时间戳
                String time = DateUtils.getDateString(timeStamp, pattern);   //将时间戳转化成字符串显示出来
                Log.e(TAG,  time + count);
                Log.d(TAG, ": 当天");
                if (nowTime != time) {
                    LineChart(count);  //线型图表
                }
                break;
            case R.id.button_week:
                Log.d(TAG, ": 前一周");
                break;
            case R.id.button_month:
                Log.d(TAG, ": 前一月");
                break;
            default:
                break;
        }
    };
}