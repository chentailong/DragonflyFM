package net.along.fragonflyfm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.base.Constants;
import net.along.fragonflyfm.fragment.AwaitFragment;
import net.lzzy.commutils.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 创建者 by:陈泰龙
 * <p>进入界面时的画面
 * 2020/7/7
 **/

public class AwaitActivity extends BaseActivity implements AwaitFragment.OnCancelSplashListener, View.OnClickListener {
    private TextView mTextView;
    private Handler mHandler;
    private Runnable mRunnable;
    Timer mTimer = new Timer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, flag);
        setContentView(R.layout.activity_await);
        initVIew();

        mTimer.schedule(task, 1000, 1000);//等待时间一秒，停顿时间一秒
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable = new Runnable() {
            @Override
            public void run() {
                //从闪屏界面跳转到首界面
                Intent intent = new Intent(AwaitActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);  //延迟5秒后发送handler信息
    }

    private void initVIew() {
        mTextView = findViewById(R.id.await_tv_count_down);  //跳过
        mTextView.setOnClickListener(this); //跳过的监听事件
    }

    /**
     * 倒计时开始运转
     */
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String display = Constants.recLen + "秒";
                    mTextView.setText(display);
                    Constants.recLen--;   //时间减一
                    if (Constants.recLen < 0) {
                        mTimer.cancel();
                        mTextView.setVisibility(View.VISIBLE);  //倒计时到0时隐藏字体
                    }
                }
            });
        }
    };

    /**
     * 点击事件
     *
     * @param
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.await_tv_count_down:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                if (mRunnable != null) {
                    mHandler.removeCallbacks(mRunnable);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 时间计时等于0
     */
    @Override
    public void cancelCountDown() {
        Constants.seconds = 0;
    }

    @Override
    protected boolean isFeatureNoTitle() {
        return true;
    }

    /**
     * 获取网络信息时，应该出现的提示信息，无网络时，是否退出，有网络时应该如何操作
     */
    @Override
    protected void initViews() {

    }

    /**
     * 链接布局界面
     *
     * @return
     */
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_await;
    }

    /**
     * 绑定Fragment id
     *
     * @return
     */
    @Override
    protected int getContainerId() {
        return R.id.fragment_await_container;
    }

    /**
     * 返回Fragment
     *
     * @return
     */
    @Override
    protected Fragment createFragment() {
        return new AwaitFragment();
    }
}

