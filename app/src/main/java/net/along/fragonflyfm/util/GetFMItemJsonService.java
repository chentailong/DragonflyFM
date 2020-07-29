package net.along.fragonflyfm.util;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import net.along.fragonflyfm.base.CommonHttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;


/**
 * 用于下载指定地区的FM数据
 */
public class GetFMItemJsonService extends IntentService {

    private static JSONArray LAST_GET_JSON;
    private static String TAG = "GetFMItemJsonService";


    public GetFMItemJsonService(String name) {
        super(name);
    }

    public GetFMItemJsonService() {
        super("GetFMItemJsonService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e("GetFMItemJsonService","数据下载服务开始");
        int provinceId=intent.getIntExtra("provinceId",239);
        int page=intent.getIntExtra("page",1);
        int pageSize=intent.getIntExtra("pageSize",18);
        ResponseBody responseBody=
        CommonHttpRequest.getHttp("https://rapi.qingting.fm/categories/"+provinceId+
                "/channels?with_total=true&page="+page+"&pagesize="+pageSize).body();
        try {
            String jsonData=responseBody.string();
            JSONObject dataJson=new JSONObject(jsonData);
            JSONArray item=dataJson.getJSONObject("Data").getJSONArray("items");
            LAST_GET_JSON=item;
            Log.e("获取结果",item.toString());

        }
      catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("GetFMItemJsonService","数据下载服务结束");
    }

    public static JSONArray getLastGetJson() {
        if (LAST_GET_JSON!=null)
        return LAST_GET_JSON;
        Log.d(TAG, "getLastGetJson: ");
        return null;
    }
}
