package net.along.fragonflyfm.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.activities.ProgramActivity;
import net.along.fragonflyfm.adapter.SearchesAdapter;
import net.along.fragonflyfm.base.BaseFragment;
import net.along.fragonflyfm.entity.Searches;
import net.along.fragonflyfm.presenters.SearchesDetailProgram;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/1
 **/

public class SearchesFragment extends BaseFragment implements SearchesAdapter.onSearchesItemClickListener {
    private String province;
    private TextView tv_location;
    private View mRootView;
    private SearchView sv_location;
    private final static String TAG = "SearchesFragment";
    private final static int mWhat = 1;
    private List<Searches> mList;
    private Handler mHandler;
    private SearchesAdapter mAdapter;
    private GridView mGridView;
    private String mContent;


    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        mRootView = inflater.inflate(R.layout.fragment_searches, container, false);
        okHttp();
        initView();
        inDialog();
        return mRootView;
    }

    /**
     * 地址选择
     */
    private void inDialog() {

    }

    /**
     * 组件的绑定
     */
    private void initView() {
        tv_location = mRootView.findViewById(R.id.fragment_searches_location);
        sv_location = mRootView.findViewById(R.id.fragment_searches_search);
        mGridView = mRootView.findViewById(R.id.fragment_searches_data);
        mHandler = new Handler(new InnerCallBack());
        mList = new ArrayList<>();
    }


    /**
     * 数据解析
     */

    private void setAdapter(List<Searches> list) {
        mAdapter = new SearchesAdapter(getContext(), list);
        mGridView.setAdapter(mAdapter);
        mAdapter.setOnSearchesItemClickListener(this);
    }

    @Override
    public void onItemClick(int position, Searches searches) {
        //根据位置拿到数据
        SearchesDetailProgram.getInstance().setTargetSearches(searches);
        //item被点击了,跳转到详情
        Intent intent = new Intent(getContext(), ProgramActivity.class);
        startActivity(intent);
    }


    /**
     * OkHttp的使用，将数据解析
     */
    public void okHttp() {
        String json = "https://rapi.qingting.fm/categories/239/channels?with_total=true&page=1&pagesize=12";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(json).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                message.what = 0;
                String string = response.body().string();
                message.obj = string;
                mHandler.sendMessage(message);
            }
        });
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
    private MyHandler mHandlers = new MyHandler();

    @SuppressLint("HandlerLeak")
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == mWhat) {
                List<Searches> mList = (List<Searches>) msg.obj;
                setAdapter(mList);
            }
        }
    }

    /**
     * 主线程
     */
    private class InnerCallBack implements android.os.Handler.Callback {
        @Override
        public boolean handleMessage(Message msg) {  //主线程处理方式
            switch (msg.what) {
                case 0:
                    mContent = (String) msg.obj;
                    analysisJsonContent(mContent);
                    break;
            }
            return true;
        }

        /**
         * 解析JSON数据，给数据赋值
         *
         * @param content
         */
        private void analysisJsonContent(String content) {
            /**
             * 子线程
             */
            new Thread(new Runnable() {
                private Message mMessage;

                @Override
                public void run() {
                    try {
                        List<Searches> list = new ArrayList<>();   //存放数据数组
                        JSONObject jsonObject = new JSONObject(content);  //解析数据
                        String Data = jsonObject.getString("Data"); //数据头Data
                        JSONObject jsonObject1 = new JSONObject(Data);     //解析数据头
                        JSONArray items = jsonObject1.getJSONArray("items"); //数据items，数据头下的数据存放源，主要数据存放处，解析数据成字符串
                        Searches searches = null;    //创建实体类对象，但不赋值
                        for (int i = 0; i < items.length(); i++) {  //循环加载数据
                            searches = new Searches();        //实体类对象
                            JSONObject jsonObject2 = items.getJSONObject(i);
                            searches.setTitle(jsonObject2.getString("title"));  //将JSON数据中的名称写入
                            searches.setAudience_count(jsonObject2.getString("audience_count"));//将JSON数据中的观看人数写入
                            Searches finalSearches = searches;
                            //图像的显示代码
                            HttpURLConnection coon = null;
                            InputStream inputStream = null;
                            try {
                                URL url = new URL(jsonObject2.getString("cover"));
                                coon = (HttpURLConnection) url.openConnection();
                                coon.setDoInput(true);
                                coon.connect();
                                inputStream = coon.getInputStream();
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                finalSearches.setBitmap(bitmap);
                                list.add(finalSearches);    //将图片放入数据数组中
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            } finally {
                                if (coon != null) {
                                    coon.disconnect();
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                            }
                        }                   //循环结束
                        mList.addAll(list);  //将数据放入实体类中
                        mMessage = new Message();  //子线程结束后创建Message
                        mMessage.obj = mList;
                        mMessage.what = mWhat;       //给Message赋值，用于传回处理器类时使用
                        Log.d(TAG, "run: 正在加载适配器");
                        mHandlers.sendMessage(mMessage); //传回参数给处理器类，进行下一步的处理
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}