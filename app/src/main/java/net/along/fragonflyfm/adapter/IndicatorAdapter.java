package net.along.fragonflyfm.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import net.along.fragonflyfm.base.Constants;
import net.along.fragonflyfm.base.FragmentCreator;

/**
 * 创建者 by:陈泰龙
 * <p> 主页滑动
 * 2020/7/7
 **/

public class IndicatorAdapter extends FragmentPagerAdapter {

    public IndicatorAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public int getCount() {
        return Constants.PAGE_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }


    @Override
    public Fragment getItem(int position) {
      return FragmentCreator.getFragment(position);
    }
}
