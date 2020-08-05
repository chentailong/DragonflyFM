package net.along.fragonflyfm.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.adapter.ProgramAdapter;
import net.along.fragonflyfm.entity.Program;
import net.along.fragonflyfm.util.CommonHttpRequest;
import net.along.fragonflyfm.util.GetTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/15
 **/

public class ProgramActivity extends AppCompatActivity {

    private final static String TAG = "ProgramActivity";
    private TextView TvPrevious;
    private TextView TvName;
    private RecyclerView mRecyclerView;
    private List<Program> mPrograms;
    public String cover;
    public String channelName;
    public int channelId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_list);
        initView();
        inData();
    }


    /**
     * 初始化控件
     */
    private void initView() {
        this.findViewById(R.id.play_list_return).setOnClickListener(onClickListener);  //返回键
        this.findViewById(R.id.play_list_previous).setOnClickListener(onClickListener);
        TvPrevious = this.findViewById(R.id.play_list_previous);  //返回
        TvName = this.findViewById(R.id.program_name);   //电台节目名称
        mRecyclerView = this.findViewById(R.id.play_list_program);
        TvName.setText(getIntent().getStringExtra("channel"));
        TvPrevious.setText(getIntent().getStringExtra("previous"));
    }

    /**
     * 加载电台数据
     */
    private void inData() {
        cover = getIntent().getStringExtra("cover");
        channelName = getIntent().getStringExtra("channelName");
        channelId = getIntent().getIntExtra("channel_id", 20697);
        final int dayOFWeek = GetTime.dayOFWeek();
        final String baseUrl = "https://rapi.qingting.fm/v2/channels/" + channelId + "/playbills?day=" + dayOFWeek;
        final AlertDialog loadingDialog = new AlertDialog.Builder(ProgramActivity.this).create();
        View loadView = View.inflate(this, R.layout.loading_dialog_data, null);
        loadingDialog.setView(loadView);
        loadingDialog.show();
        CommonHttpRequest.getHttp(baseUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(ProgramActivity.this, "获取电台数据失败", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                });
            }

            @Override
            public void onResponse(Call call, Response response) {
                ResponseBody responseBody = response.body();
                try {
                    JSONObject rootJson = new JSONObject(responseBody.string());
                    JSONObject dataObj = rootJson.getJSONObject("data");
                    JSONArray dayJson = dataObj.getJSONArray("" + dayOFWeek);
                    Gson gson = new Gson();
                    mPrograms = gson.fromJson(dayJson.toString(), new TypeToken<List<Program>>() {}.getType());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(() -> {
                    loadingDialog.dismiss();
                    showPlayList();
                });

            }
        });
    }

    private void showPlayList() {
        ProgramAdapter programAdapter = new ProgramAdapter(this, mPrograms);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(programAdapter);
    }


    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.play_list_previous:
            case R.id.play_list_return:
                finish();
                break;
        }
    };

}
