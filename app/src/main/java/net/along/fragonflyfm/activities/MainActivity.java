package net.along.fragonflyfm.activities;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.adapter.IndicatorAdapter;
import net.along.fragonflyfm.Constants.Constants;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private IndicatorAdapter mAdapter;
    private ViewPager mViewPager;
    private RadioGroup mGroup;
    private RadioButton mRadio;
    private RadioButton mSearch;
    private RadioButton mAnalyze;
    private final static String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mAdapter = new IndicatorAdapter(getSupportFragmentManager()); //将适配器绑定于FragmentAdapter，用于数据的更新
        mGroup = findViewById(R.id.button_rg_tab_bar);
        mRadio = findViewById(R.id.main_radio);                     //电台
        mSearch = findViewById(R.id.main_search);                   //搜索
        mAnalyze = findViewById(R.id.main_analyze);                 //分析
        mGroup.setOnCheckedChangeListener(this);                    //滑动设置监听器
        mViewPager = findViewById(R.id.view_pager);                 //内容存储器，存放Fragment中的数据显示
        mViewPager.setAdapter(mAdapter);                            //为内容添加适配器
        mViewPager.addOnPageChangeListener(this);                   //设置监听
        mViewPager.setCurrentItem(Constants.INDEX_SUBSCRIPTION);    //默认跳转界面为ViewPager的第二页
        mSearch.setChecked(true);                                   //设置底部导航栏默认为第二页，颜色也跟随变化
    }

    /**
     * 点击图标实现跳转
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_radio:
                mViewPager.setCurrentItem(Constants.INDEX_RECOMMEND);
                break;
            case R.id.main_search:
                mViewPager.setCurrentItem(Constants.INDEX_SUBSCRIPTION);
                break;
            case R.id.main_analyze:
                mViewPager.setCurrentItem(Constants.INDEX_HISTORY);
                break;
            default:
                break;
        }
    }


    /**
     * 重写VIew Page的页面切换方法
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    /**
     * 滑动底部导航栏同步更新
     *
     * @param state
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        //state等于2时滑动结束 1正在滑动 0未滑动
        if (state == 2) {
            switch (mViewPager.getCurrentItem()) {
                case Constants.WHAT_COUNT_DOWN:
                    mRadio.setChecked(true);
                    break;
                case Constants.INDEX_SUBSCRIPTION:
                    mSearch.setChecked(true);
                    break;
                case Constants.INDEX_HISTORY:
                    mAnalyze.setChecked(true);
                    break;
                default:
                    break;
            }
        }
    }
}
