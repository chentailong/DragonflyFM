package net.along.fragonflyfm.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/7
 **/
public class DeviceUtils {
    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) AppUtils.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm != null ? cm.getActiveNetworkInfo() : null;
        return ni != null && ni.isConnected();
    }

    @SuppressLint("HardwareIds")
    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) AppUtils.getContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return tm.getImei();
        } else {
            return tm.getDeviceId();
        }
    }

    @SuppressLint("HardwareIds")
    public static String getPhoneNo() {
        TelephonyManager tm = (TelephonyManager) AppUtils.getContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(AppUtils.getContext(), Manifest.permission.READ_SMS) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(AppUtils.getContext(), Manifest.permission.READ_PHONE_NUMBERS) !=
                        PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(AppUtils.getContext(), Manifest.permission.READ_PHONE_STATE) !=
                        PackageManager.PERMISSION_GRANTED) {
            //凡是调用该方法的Activity，若无法获取权限，则调用下面这行，在onActivityResult里，根据授权结果再操作
            //       ActivityCompat.requestPermissions(activity, new String[]{
            //            Manifest.permission.READ_SMS,
            //            Manifest.permission.READ_PHONE_NUMBERS,
            //            Manifest.permission.READ_PHONE_STATE}, 1);
            return null;
        }
        return tm.getLine1Number();
    }
}
