package net.along.fragonflyfm.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ximalaya.ting.android.opensdk.model.track.Track;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.adapter.BaseActivity;
import net.along.fragonflyfm.entity.Searches;
import net.along.fragonflyfm.interfaces.ISearchesDetailViewCallback;
import net.along.fragonflyfm.presenters.SearchesDetailProgram;

import java.util.List;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/15
 **/

public class ProgramActivity extends BaseActivity implements ISearchesDetailViewCallback {

    private TextView mTv_return;
    private TextView mTv_program_name;
    private SearchesDetailProgram mSearchesDetailProgram;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_list);
        initView();
        getReturn();
        mSearchesDetailProgram = SearchesDetailProgram.getInstance();
        mSearchesDetailProgram.registerViewCallback(this);
    }

    private void getReturn() {
        mTv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTv_return = this.findViewById(R.id.program_return);
        mTv_program_name =this.findViewById(R.id.program_name);
    }

    @Override
    public void onDetailListLoaded(List<Track> tracks) {

    }

    @Override
    public void onSearchesLaded(Searches searches) {
        if (mTv_program_name != null) {
            mTv_program_name.setText(searches.getTitle());
        }
    }
}
