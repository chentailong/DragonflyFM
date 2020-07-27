package net.along.fragonflyfm.base;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * 创建者 by:陈泰龙
 * <p>用于下载指定地区FM数据
 * 2020/7/25
 **/

public class DownloadData extends IntentService {
    private static final String TAG = "DownloadData";
    private static JSONArray GET_JSON;


    public DownloadData() {
        super("DownloadData");
    }

    public DownloadData(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int provinceId = intent.getIntExtra("provinceId", 239);
        int page = intent.getIntExtra("page", 1);
        int pageSize = intent.getIntExtra("pageSize", 18);
        ResponseBody responseBody
                = CommonHttpRequest.getHttp("https://rapi.qingting.fm/categories/" + provinceId +
                "/channels?with_total=true&page=" + page + "&pagesize=" + pageSize).body();

        try {
            assert responseBody != null;
            String jsonData =responseBody.string();
            JSONObject dataJson = new JSONObject(jsonData);
            JSONArray jsonArray = dataJson.getJSONObject("Data").getJSONArray("items");
            GET_JSON = jsonArray;
            Log.e(TAG, "onHandleIntent: 获取结果" + jsonArray.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭服务
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static JSONArray GetJSON() {
        if (GET_JSON != null) {
            return GET_JSON;
        }
        return null;
    }
}
