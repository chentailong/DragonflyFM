package net.along.fragonflyfm.util;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 创建者 by:陈泰龙
 * <p> JSON
 * 2020/7/15
 **/

public class CommonHttpRequest {
    private static MediaType type=MediaType.parse("application/json;charset=utf-8");
    public static Response getHttp(String url){
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response=okHttpClient.newCall(request).execute();
           return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getHttp(String url, Callback callback){
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(callback);
    }


    public static Response postHttp(String url, String json) {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(type, json);
        Request request = new Request.Builder().url(url).patch(requestBody).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
