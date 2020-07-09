package net.along.fragonflyfm.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import net.along.fragonflyfm.R;
import net.along.fragonflyfm.base.Constants;
import net.along.fragonflyfm.base.WebServerConnection;
import net.along.fragonflyfm.fragment.AwaitFragment;
import net.along.fragonflyfm.util.AbstractStaticHandler;
import net.along.fragonflyfm.util.AppUtils;
import net.along.fragonflyfm.util.DeviceUtils;
import net.along.fragonflyfm.util.ViewUtils;
import net.lzzy.commutils.BaseActivity;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 创建者 by:陈泰龙
 * <p>进入界面时的画面
 * 2020/7/7
 **/

public class AwaitActivity extends BaseActivity implements AwaitFragment.OnCancelSplashListener {
    private TextView mTextView;
    private boolean isServerOn = true;
    private final SplashHandler handler = new SplashHandler(this);

    @Override
    public void cancelCountDown() {
        Constants.seconds = 0;
    }

    /**
     * 消息处理器，处理倒计时及探测服务器状态的各类消息
     */
    private static class SplashHandler extends AbstractStaticHandler<AwaitActivity> {

         SplashHandler(AwaitActivity context) {
            super(context);
        }

        @Override
        public void handleMessage(Message msg, AwaitActivity context) {
            switch (msg.what) {
                case Constants.WHAT_COUNT_DOWN:
                    String display = msg.obj + "秒";  //生成时间
                    context.mTextView.setText(display); //显示时间
                    break;
                case Constants.WHAT_EXCEPTION:
                    new AlertDialog.Builder(context)
                            .setMessage(msg.obj.toString())
                            .setPositiveButton("继续", (dialog, which) -> funcGoBack.gotoMain(context))
                            .setNegativeButton("退出", (dialog, which) -> AppUtils.exit())
                            .show();
                    break;
                case Constants.WHAT_COUNT_DONE:
                    if (context.isServerOn) {
                        funcGoBack.gotoMain(context);
                    }
                    break;
                case Constants.WHAT_SERVER_OFF:
                    Context running = AppUtils.getRunningActivity();
                    new AlertDialog.Builder(Objects.requireNonNull(running)).setTitle("提示")
                            .setMessage("服务器没有响应，是否继续？\n" + msg.obj.toString())
                            .setPositiveButton("退出", (dialog, which) -> AppUtils.exit())
                            .setNeutralButton("设置", (dialog, which) -> ViewUtils.gotoSettings(running, funcGoBack))
                            .setNegativeButton("确定", (dialog, which) -> funcGoBack.gotoMain(context))
                            .show();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 跳转至主界面
     */
    private static final ViewUtils.GoBackFunction funcGoBack = context -> {
        if (context instanceof AwaitActivity) {
            context.startActivity(new Intent(context, MainActivity.class));
            ((AwaitActivity) context).finish();
        }
    };

    @Override
    protected boolean isFeatureNoTitle() {
        return true;
    }

    @Override
    protected void initViews() {
        AppUtils.addActivity(this);
        mTextView = findViewById(R.id.await_tv_count_down);
        if (!DeviceUtils.isNetworkAvailable()) {
            new AlertDialog.Builder(this).setTitle("提示")
                    .setMessage("网络不可用，是否继续？")
                    .setPositiveButton("退出", (dialog, which) -> AppUtils.exit())
                    .setNegativeButton("确定", (dialog, which) -> funcGoBack.gotoMain(this))
                    .show();
        } else {
            ThreadPoolExecutor executor = AppUtils.getExecutor();
            executor.execute(this::countDown);
            executor.execute(this::detectWebServer);
        }
    }

    private void countDown() {
        while (Constants.seconds >= 0) {
            handler.sendMessage(handler.obtainMessage(Constants.WHAT_COUNT_DOWN, Constants.seconds));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                handler.sendMessage(handler.obtainMessage(Constants.WHAT_EXCEPTION, e.getMessage()));
            }
            Constants.seconds--;
        }
        handler.sendEmptyMessage(Constants.WHAT_COUNT_DONE);
    }

    private void detectWebServer() {
        try {
            WebServerConnection.tryConnectToServer();
        } catch (IOException e) {
            isServerOn = false;
            handler.sendMessage(handler.obtainMessage(Constants.WHAT_SERVER_OFF, e.getMessage()));
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_await;
    }

    @Override
    protected int getContainerId() {
        return R.id.fragment_await_container;
    }

    @Override
    protected Fragment createFragment() {
        return new AwaitFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUtils.removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppUtils.setRunning(getLocalClassName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppUtils.setStopped(getLocalClassName());
    }
}

