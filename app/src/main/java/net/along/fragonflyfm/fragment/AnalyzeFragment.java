package net.along.fragonflyfm.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.orm.SugarRecord;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.record.AppVisitCount;
import net.along.fragonflyfm.record.RegionTable;
import net.along.fragonflyfm.util.DateBaseUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/1
 **/

@RequiresApi(api = Build.VERSION_CODES.M)
public class AnalyzeFragment extends Fragment {
    private final String[] titles = {"APP访问次数", "各地区访问比例（%）", "最受欢迎电台", "最受欢迎节目", "电台类型倾向"};
    private static final String TAG = "AnalyzeFragment";
    private String pattern = "MM-dd";
    private static final float MIN_TOUCH_DISTANCE = 10f;
    private TextView title_message;
    private int chartIndex = 0;
    private View mRootView;
    private float touchX1;
    private View[] dots;
    private Chart[] mChart;
    private PieChart mPieChart;
    private LineChart mLineChart1;
    private BarChart mBarChart1;
    private BarChart mBarChart2;
    private RadarChart mRadarChart;
    private RadioGroup mRadioGroup;
    private long id;
    private Iterator visit;
    private String mNowTime;
    private Map<String, Integer> mMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_analyze, container, false);
        initView();
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
        mBarChart1 = mRootView.findViewById(R.id.fragment_chart_b1);
        mBarChart2 = mRootView.findViewById(R.id.fragment_chart_b2);
        mRadarChart = mRootView.findViewById(R.id.fragment_chart_r);
        mChart = new Chart[]{mLineChart1, mPieChart, mBarChart1, mBarChart2, mRadarChart};
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
     * App点击次数的折线图，实现次数的传递显示
     *
     * @param weekCount
     * @param weekDate
     */
    private void LineChart(ArrayList<Integer> weekCount, ArrayList<Long> weekDate) {
        //显示数据，传入访问次数
        ArrayList<Entry> entry = new ArrayList<>();
        List<String> time = new ArrayList<>();
        int j = 0;
        for (Integer i = 0; i < weekCount.size(); i++) {
            entry.add(new Entry(j, weekCount.get(i)));
            j++;
        }
        LineDataSet lineDataSet = new LineDataSet(entry, "");
        lineDataSet.setColor(Color.parseColor("#F15A4A"));
        lineDataSet.setLineWidth(1.6f);//线宽度
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER); //线条平滑
        lineDataSet.setValueTextSize(12f);  //设置显示值的文字大小
        lineDataSet.setValueFormatter(new DefaultAxisValueFormatter(0));    //不要小数点
        LineData data = new LineData(lineDataSet);    //设置数据

        XAxis xAxis = mLineChart1.getXAxis();     //得到X轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //设置X轴的位置（默认在上方)
        xAxis.setDrawGridLines(false);   //不显示网格线
        xAxis.setGranularity(1f); //设置X轴坐标之间的最小间隔
        xAxis.setLabelRotationAngle(45);     //标签倾斜
        YAxis yAxis = mLineChart1.getAxisLeft(); //得到Y轴
        YAxis rightYAxis = mLineChart1.getAxisRight();
        rightYAxis.setEnabled(false); //右侧Y轴不显示
        yAxis.setDrawGridLines(true); //不显示网格线
        yAxis.setGranularity(10f);   //设置Y轴坐标之间的最小间隔
        yAxis.setAxisMinimum(0f); //设置从Y轴值

        mLineChart1.getDescription().setEnabled(false); //隐藏提示信息
        mLineChart1.setData(data); //设置数据
        mLineChart1.invalidate();  //图标刷新

    }

    /**
     * 各地区访问次数的饼图测试，暂无真实数据
     */
    private void PieChart() { //ArrayList<Integer> count, ArrayList<String> regionName
        ArrayList<PieEntry> entry = new ArrayList<>();
        entry.clear();
//        for (int j = 0; j < regionName.size(); j++) {
//            entry.add(new PieEntry(count.get(j), regionName.get(j)));
//        }

        Iterator<String> iterator =mMap.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            entry.add(new PieEntry(mMap.get(key),key));
        }

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
                //APP访问次数的实现
                visit = AppVisitCount.findAll(AppVisitCount.class);  //访问sugar数据库
                while (visit.hasNext()) {
                    AppVisitCount tableObj = (AppVisitCount) visit.next();
                    SugarRecord sugarRecord = tableObj;
                    id = sugarRecord.getId(); //获取最新id
                }
                mNowTime = DateBaseUtil.getCurrentDate(pattern);   //获取当前时间
                AppVisitCount DayVisitCount = AppVisitCount.findById(AppVisitCount.class, id);  //根据ID查询数据
                int VisitCount = DayVisitCount.getCount();  //次数
                ArrayList<Integer> DayCount = new ArrayList<>();  //将次数装入
                ArrayList<Long> DayDate = new ArrayList<>();  //将时间装入
                DayCount.add(VisitCount);
                long VisitTimeStamp = DayVisitCount.getTimeStamp();  //时间戳
                DayDate.add(VisitTimeStamp);
                String time = DateBaseUtil.getDateString(VisitTimeStamp, pattern);   //将时间戳转化成字符串显示出来
                Log.e(TAG, VisitTimeStamp + " , " + time + " , " + VisitCount);
                if (mNowTime != time) {
                    LineChart(DayCount, DayDate);  //线型图表
                }
                //============各地区访问比例=====================
                long nowTimeStamp = System.currentTimeMillis();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date(nowTimeStamp));
                visit = RegionTable.findAll(RegionTable.class);  //访问sugar数据库
                mMap = new HashMap<>();
                while (visit.hasNext()) {
                    RegionTable tableObj = (RegionTable) visit.next();
                    long stamp = tableObj.getStamp();
                    Calendar thisCalendar = Calendar.getInstance();
                    thisCalendar.setTime(new Date(stamp));

                    if (DateBaseUtil.isToday(calendar, thisCalendar)) {
                        mMap.put(tableObj.getProvince(),tableObj.getCount());
                    }
                }
                PieChart();
                break;
//===================================================================================================================================================
            case R.id.button_week:
                Log.d(TAG, "前周 ");
                ArrayList<Integer> WeekCount = new ArrayList<>();
                ArrayList<Long> WeekDate = new ArrayList<>();
                List<AppVisitCount> WeekVisitCount = AppVisitCount.listAll(AppVisitCount.class);
                for (int i = 0; i < WeekVisitCount.size(); i++) {
                    if (i < 7) {
                        WeekCount.add(WeekVisitCount.get(i).getCount());
                        WeekDate.add(WeekVisitCount.get(i).getTimeStamp());
                    }
                    continue;
                }
                LineChart(WeekCount, WeekDate);
                Log.e(TAG, ":总次数 " + WeekCount + " , " + WeekDate);
                //=====================各地区访问比例===================================
                ArrayList<String> WeekRegion = new ArrayList<>(); //访问地区
                Iterator<RegionTable> iterator = SugarRecord.findAll(RegionTable.class);
                List<RegionTable> tables = new ArrayList<>();
                //获取数据
                while (iterator.hasNext()) {
                    tables.add(iterator.next());
                }
                for (RegionTable table : tables) {
                    String Area = table.getProvince();
                    boolean judge = false;
                    for (String key : WeekRegion) {
                        if (key.equals(Area)) {
                            judge = true;
                            break;
                        }
                    }
                    if (!judge) {
                        WeekRegion.add(Area);
                    }
                }
                mMap = new HashMap<>();
                for (String key : WeekRegion) {
                    int count = 0;
                    for (RegionTable table : tables){
                        if (table.getProvince().equals(key)){
                            count +=table.getCount();
                        }
                    }
                    mMap.put(key,count);
                }
                PieChart();
                break;
//===================================================================================================================================================
            case R.id.button_month:
                Log.d(TAG, ": 前一月");
                ArrayList<Integer> MonthCount = new ArrayList<>();
                ArrayList<Long> MonthDate = new ArrayList<>();
                List<AppVisitCount> MonthVisitCount = AppVisitCount.listAll(AppVisitCount.class);
                for (int i = 0; i < MonthVisitCount.size(); i++) {
                    MonthCount.add(MonthVisitCount.get(i).getCount());
                    MonthDate.add(MonthVisitCount.get(i).getTimeStamp());
                }
                LineChart(MonthCount, MonthDate);
                //=====================各地区访问比例===================================
                ArrayList<String> MonthRegion = new ArrayList<>(); //访问地区
                Iterator<RegionTable> iterators = SugarRecord.findAll(RegionTable.class);
                List<RegionTable> tab = new ArrayList<>();
                //获取数据
                while (iterators.hasNext()) {
                    tab.add(iterators.next());
                }
                for (RegionTable table : tab) {
                    String Area = table.getProvince();
                    boolean judge = false;
                    for (String key : MonthRegion) {
                        if (key.equals(Area)) {
                            judge = true;
                            break;
                        }
                    }
                    if (!judge) {
                        MonthRegion.add(Area);
                    }
                }
                mMap = new HashMap<>();
                for (String key : MonthRegion) {
                    int count = 0;
                    for (RegionTable table : tab){
                        if (table.getProvince().equals(key)){
                            count +=table.getCount();
                        }
                    }
                    mMap.put(key,count);
                }
                PieChart();
                break;
            default:
                break;
        }
    };
}