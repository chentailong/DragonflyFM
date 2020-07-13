package net.along.fragonflyfm.base;


import net.along.fragonflyfm.Constants.Constants;
import net.along.fragonflyfm.fragment.AnalyzeFragment;;
import net.along.fragonflyfm.fragment.RadioFragment;
import net.along.fragonflyfm.fragment.SearchesFragment;

import java.util.HashMap;
import java.util.Map;


/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/2
 **/

public class FragmentCreator extends Constants{
    private static Map<Integer, BaseFragment> sCache = new HashMap<>();

    public static BaseFragment getFragment(int index) {
        BaseFragment baseFragment = sCache.get(index);
        if (baseFragment != null) {
            return baseFragment;
        }
        switch (index) {
            case Constants.INDEX_RECOMMEND:
                baseFragment = new RadioFragment();
                break;
            case Constants.INDEX_SUBSCRIPTION:
                baseFragment = new SearchesFragment();
                break;
            case Constants.INDEX_HISTORY:
                baseFragment = new AnalyzeFragment();
                break;

        }
        sCache.put(index, baseFragment);
        return baseFragment;
    }
}
