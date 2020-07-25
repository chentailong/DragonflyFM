package net.along.fragonflyfm.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.adapter.ProgramAdapter;
import net.along.fragonflyfm.entity.Program;
import net.along.fragonflyfm.entity.Searches;
import net.along.fragonflyfm.interfaces.ISearchesDetailViewCallback;
import net.along.fragonflyfm.presenters.SearchesDetailProgram;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/15
 **/

public class ProgramActivity extends BaseActivity implements ISearchesDetailViewCallback {

    private final static String TAG = "ProgramActivity";
    private TextView mTv_return;
    private TextView mTv_program_name;
    private TextView tv;
    private SearchesDetailProgram mSearchesDetailProgram;
    private ProgramAdapter mAdapter;
    private List<Program> mList;
    private ListView mListView;
    private int mData;   //获取到的ID，点击时传过来的id
    private String json = "https://rapi.qingting.fm/categories/239/channels?with_total=true&page=1&pagesize=12";
    private Program program = null;
    private JSONObject jsonObject2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_list);
        initView();
        getReturn();
        okHttp();
        mSearchesDetailProgram = SearchesDetailProgram.getInstance();
        mSearchesDetailProgram.registerViewCallback(this);
    }

    /**
     * 绑定适配器，并创建子线程，使用OKHttp解析JSON数据，获取数据源
     */
    private void okHttp() {
        mAdapter = new ProgramAdapter(this, mList);
        mListView.setAdapter(mAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url(json).build();
                    Response response = okHttpClient.newCall(request).execute();
                    //得到服务器返回的数据后，调用parseJSONWithJSONObject进行解析
                    String returnData = response.body().string();
                    parseJSONWithJSONObject(returnData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 解析JSON数据，转换成字符串，加载至list中
     *
     * @param jsonData
     */
    private void parseJSONWithJSONObject(String jsonData) {
        if (jsonData != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonData);
                String Data = jsonObject.getString("Data");
                JSONObject jsonDataEs = new JSONObject(Data);
                JSONArray jsonItems = jsonDataEs.getJSONArray("items");
                for (int i = 0; i < jsonItems.length(); i++) {
                    program = new Program();
                    jsonObject2 = jsonItems.getJSONObject(i);
                    program.setContent_id(jsonObject2.getInt("content_id"));
                    if (program.getContent_id() == mData) {
                        JSONObject jsonObject3 = jsonObject2.getJSONObject("nowplaying");
                        JSONArray jsonBroadcasters = jsonObject3.getJSONArray("broadcasters");

                        for (int j = 0 ; j<jsonBroadcasters.length(); j++){
                            JSONObject jsonObjectBroadcasters = jsonBroadcasters.getJSONObject(j);
                            if (j==0){
                                program.setUsername(jsonObjectBroadcasters.getString("username"));
                            }
                                program.setUsernames(jsonObjectBroadcasters.getString("username"));
                        }//循环输出主播姓名

                        program.setAudience_count(jsonObject2.getString("audience_count"));
                        program.setTitle(jsonObject3.getString("title"));
                        program.setStart_time(jsonObject3.getString("start_time"));
                        mList.add(program);
                        break;
                    }
                }
                mHandler.sendEmptyMessageDelayed(1, 100);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理器类、
     * 当线程传回信息时解析加载
     * 开始队列
     */
    @SuppressLint("HandlerLeak")
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    /**
     * 初始化控件
     */
    private void initView() {
        mTv_return = this.findViewById(R.id.program_return);
        mTv_program_name = this.findViewById(R.id.program_name);
        tv = this.findViewById(R.id.tv);
        mListView = findViewById(R.id.program_list);
        mList = new ArrayList<>();
        Intent intent = getIntent();
        mData = intent.getIntExtra("id", 0);
        Log.d(TAG, "initView: " + mData);
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
