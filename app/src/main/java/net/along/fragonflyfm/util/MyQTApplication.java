package net.along.fragonflyfm.util;

import android.app.Application;

import fm.qingting.qtsdk.QTSDK;

/**
 * 蜻蜓FM : Key 获取直播源最根本的方式
 */

public class MyQTApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        QTSDK.setHost("https://open.staging.qingting.fm");
        QTSDK.init(getApplicationContext(), "MmYxYThlY2EtYWMxMi0xMWU4LTkyM2YtMDAxNjNlMDAyMGFk"
        );
        QTSDK.setAuthRedirectUrl("http://qttest.qingting.fm");
    }
}
