package net.along.fragonflyfm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityConfig;
import com.lljjcoder.style.cityjd.JDCityPicker;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.base.BaseFragment;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/1
 **/

public class SearchesFragment extends BaseFragment {
    private String province;
    private TextView tv_location;
    private View mRootView;
    private SearchView sv_location;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        mRootView = inflater.inflate(R.layout.fragment_searches, container, false);
        initView();
        inDialog();
        return mRootView;
    }

    /**
     * 地址选择
     */
    private void inDialog() {
        province = "广西";
        tv_location.findViewById(R.id.fragment_searches_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //地址选择器
                JDCityPicker cityPicker = new JDCityPicker();
                JDCityConfig jdCityConfig = new JDCityConfig.Builder().build();
                jdCityConfig.setShowType(JDCityConfig.ShowType.PRO_CITY_DIS);
                cityPicker.init(getContext());
                cityPicker.setConfig(jdCityConfig);
                cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        SearchesFragment.this.province = province.getName();
                        String area = province.getName();
                        tv_location.setText(area);
                        sv_location.setQueryHint(area);
                    }
                    @Override
                    public void onCancel() {
                    }
                });
                cityPicker.showCityPicker();
            }
        });
    }

    /**
     * 组件的绑定
     */
    private void initView() {
        tv_location = mRootView.findViewById(R.id.fragment_searches_location);
        sv_location = mRootView.findViewById(R.id.fragment_searches_search);
    }
}
