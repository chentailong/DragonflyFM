package net.along.fragonflyfm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.adapter.IndicatorAdapter;
import net.along.fragonflyfm.adapter.MainContentAdapter;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MagicIndicator mMagicIndicator;
    private ViewPager mViewPager;
    private IndicatorAdapter mIndicatorAdapter;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();  //设置事件
    }

    private void initEvent() {
        mIndicatorAdapter.setOnIndicatorTapClickListener(new IndicatorAdapter.onIndicatorTapClickListener() {
            @Override
            public void onTabClick(int index) {
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(index);
                }
            }
        });

    }

    private void initView() {
        //绑定底部导航栏
        mMagicIndicator = this.findViewById(R.id.magic_navigation);
        //背景颜色
        mMagicIndicator.setBackgroundColor(this.getResources().getColor(R.color.colorGray));
        //创建适配器
        mIndicatorAdapter = new IndicatorAdapter(this);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);  //平铺内容
        commonNavigator.setAdapter(this.mIndicatorAdapter);
        //内容显示id绑定
        mViewPager = this.findViewById(R.id.view_pager);
        //创建内容适配器
        FragmentManager fragmentManager = getSupportFragmentManager();
        MainContentAdapter mainContentAdapter = new MainContentAdapter(fragmentManager);
        mViewPager.setAdapter(mainContentAdapter);

        //把View和indicator绑定在一起
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator,mViewPager);

    }

}
