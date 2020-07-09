package net.along.fragonflyfm.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import net.along.fragonflyfm.base.FragmentCreator;
/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/1
 **/

public class MainContentAdapter extends FragmentPagerAdapter {

    public MainContentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return FragmentCreator.getFragment(position);
    }

    @Override
    public int getCount() {
        return FragmentCreator.PAGE_COUNT;
    }

}
