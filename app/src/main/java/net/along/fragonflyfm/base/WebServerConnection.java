package net.along.fragonflyfm.base;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.core.util.Pair;

import net.along.fragonflyfm.Constants.ApiConstants;
import net.along.fragonflyfm.util.AppUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

/**
 * WEB服务器连接配置及测试连接方法
 *
 */
public class WebServerConnection {
    private static final String SP_SETTING = "setting";
    private static final String URL_IP = "ip";
    private static final String URL_PORT = "port";
    private static final String PROTOCOL = "http://";

    public static void saveServerParams(Context context, String ip, String port) {
        SharedPreferences spSetting = context.getSharedPreferences(SP_SETTING, MODE_PRIVATE);
        spSetting.edit()
                .putString(URL_IP, ip)
                .putString(URL_PORT, port)
                .apply();
    }

    public static Pair<String, String> loadServerParams() {
        SharedPreferences spSetting = AppUtils.getContext().getSharedPreferences(SP_SETTING, MODE_PRIVATE);
        String ip = spSetting.getString(URL_IP, ApiConstants.DEFAULT_SERVER_IP);
        String port = spSetting.getString(URL_PORT, ApiConstants.DEFAULT_SERVER_PORT);
        return new Pair<>(ip, port);
    }

    public static String getServerAddress() {
        Pair<String, String> address = loadServerParams();
        String ip = address.first;
        String port = address.second;
        return PROTOCOL.concat(Objects.requireNonNull(ip))
                .concat(":")
                .concat(Objects.requireNonNull(port));
    }

    public static void tryConnectToServer() throws IOException {
        URL url = new URL(getServerAddress());
        HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
        urlConnect.setConnectTimeout(5000);
        urlConnect.getContent();
    }

    public static String getPracticesAddress() {
        return getServerAddress().concat(ApiConstants.ACTION_PRACTICES);
    }

    public static String getQuestionsAddress(int apiId){
        return getServerAddress().concat(ApiConstants.ACTION_QUESTIONS) + apiId;
    }

    public static String getResultAddress(){
        return getServerAddress().concat(ApiConstants.ACTION_RESULT);
    }
}
