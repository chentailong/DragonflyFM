package net.along.fragonflyfm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import net.along.fragonflyfm.R;
import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/**
 * 创建者 by:陈泰龙
 * <p> 主页滑动
 * 2020/7/7
 **/

public class IndicatorAdapter extends CommonNavigatorAdapter {

    private final String[] mTitles;
    private onIndicatorTapClickListener mOnTabClickListener;

    public IndicatorAdapter(Context context) {
        mTitles = context.getResources().getStringArray(R.array.main_indicator_name);
    }

    /**
     * 显示长度
     *
     * @return
     */
    @Override
    public int getCount() {
        if (mTitles != null) {
            return mTitles.length;
        }
        return 0;
    }

    @Override
    public IPagerTitleView getTitleView(Context context, int index) {
        ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
        //创建View
        ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
        //设置一般情况下颜色
        colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#aaffffff"));
        //选中情况下的颜色
        colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
        //单位大小
        colorTransitionPagerTitleView.setTextSize(18);
        //设置显示的内容
        colorTransitionPagerTitleView.setText(mTitles[index]);
//    设置title的点击事件，当点击title时，下方的ViewPager就会跳转到对应的index中去
//    也就是说，当我们点击title时，下面的内容ViewPager会对应着index进行内容切换
        colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnTabClickListener != null) {
                    mOnTabClickListener.onTabClick(index);
                }
            }
        });
//        返回View视图
        return colorTransitionPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        return null;
    }

    //设置修改
    public void setOnIndicatorTapClickListener(onIndicatorTapClickListener listener) {
        this.mOnTabClickListener = listener;
    }

    //暴露接口
    public interface onIndicatorTapClickListener {
        void onTabClick(int index);
    }
}
