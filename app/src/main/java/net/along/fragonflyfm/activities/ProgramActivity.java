package net.along.fragonflyfm.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.adapter.BaseActivity;
import net.along.fragonflyfm.adapter.ProgramAdapter;
import net.along.fragonflyfm.entity.Program;
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
    private ProgramAdapter mAdapter;
    private final static String TAG = "ProgramActivity";
    private List<Program> mProgramList;
    private Handler mHandler;
    private ListView mListView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_list);
        initView();
        getReturn();
        mSearchesDetailProgram = SearchesDetailProgram.getInstance();
        mSearchesDetailProgram.registerViewCallback(this);
    }

    /**
     * 返回上一页
     */
    private void getReturn() {
        mTv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mTv_return = this.findViewById(R.id.program_return);
        mTv_program_name = this.findViewById(R.id.program_name);
        mListView = findViewById(R.id.program_list);
        mHandler = new Handler();
    }

    /**
     * 数据解析
     */
    private void setAdapter(List<Program> list) {
        mAdapter = new ProgramAdapter(this, list);
        mListView.setAdapter(mAdapter);
    }

    /**
     * 处理器类
     * <p>
     * 当任务结束转回参数时，开始队列
     * <p>
     * 前提是要触碰到网络线程或UI
     * <p>
     * 如果过多线程，建议使用线程池
     */
    private MyHandler mMyHandler = new MyHandler();

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    }

    /**
     * 将上一页的数据显示在这一层
     *
     * @param searches
     */
    @Override
    public void onSearchesLaded(Searches searches) {
        if (mTv_program_name != null) {
            mTv_program_name.setText(searches.getTitle());
        }
    }

}
