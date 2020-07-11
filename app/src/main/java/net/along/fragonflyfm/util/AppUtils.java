package net.along.fragonflyfm.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.lang.ref.WeakReference;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 创建者 by:陈泰龙
 * <p>
 * 2020/7/7
 **/

public class AppUtils extends Application {
    // region 1.context相关
    private static WeakReference<Application> wContext;
    private static WeakReference<Application>context;
    @Override
    public void onCreate() {
        super.onCreate();
        wContext = new WeakReference<Application>(this);
        context = new WeakReference<Application>(this);
    }

    public static Context getContext() {
        return wContext.get();
    }
    //endregion

    // region 2.activity相关
    // ArrayList随机读取快但插入删除性能不行，LinkedList随机读取性能低，但插入删除快；这里无需频繁读取
    private static final List<Activity> activities = new LinkedList<>();
    private static String runningActivity;

    public static void setRunning(String runningActivity) {
        AppUtils.runningActivity = runningActivity;
    }

    public static void setStopped(String stoppedActivity) {
        if (stoppedActivity.equals(AppUtils.runningActivity)) {
            AppUtils.runningActivity = "";
        }
    }

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static Context getRunningActivity() {
        for (Activity activity : activities) {
            String name = activity.getLocalClassName();
            if (AppUtils.runningActivity.equals(name)) {
                return activity;
            }
        }
        return null;
    }

    public static void exit() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }
    // endregion

    // region 3.创建线程的Executor
    // We want at least 2 threads and at most 4 threads in the core pool,
    // preferring to have 1 less than the CPU count to avoid saturating
    // the CPU with background work
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;
    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger count = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "thread #" + count.getAndIncrement());
        }
    };
    private static final BlockingQueue<Runnable> POOL_QUEUE = new LinkedBlockingQueue<>(128);

    public static ThreadPoolExecutor getExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                POOL_QUEUE, THREAD_FACTORY);
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }
    // endregion
    public static int px2dp(int pxValue) {
        float scale = context.get().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(int dpValue) {
        float scale = context.get().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 创建二维码位图
     *
     * @param content 字符串内容(支持中文)
     * @param width   位图宽度(单位:dp)
     * @param height  位图高度(单位:dp)
     * @return
     */
    public static Bitmap createQRCodeBitmap(String content, int width, int height) {
        return createQRCodeBitmap(content, dp2px(width), dp2px(height), "UTF-8",
                "H", "2", Color.BLACK, Color.WHITE);
    }

    /**
     * 创建二维码位图 (支持自定义配置和自定义样式)
     *
     * @param content         字符串内容
     * @param width           位图宽度,要求>=0(单位:px)
     * @param height          位图高度,要求>=0(单位:px)
     * @param characterSet    字符集/字符转码格式 (支持格式:{@link CharacterSetECI })。传null时,zxing源码默认使用 "ISO-8859-1"
     * @param errorCorrection 容错级别 (支持级别:{@link ErrorCorrectionLevel })。传null时,zxing源码默认使用 "L"
     * @param margin          空白边距 (可修改,要求:整型且>=0), 传null时,zxing源码默认使用"4"。
     * @param colorBlack      黑色色块的自定义颜色值
     * @param colorWhite      白色色块的自定义颜色值
     * @return
     */
    private static Bitmap createQRCodeBitmap(String content, int width, int height, String characterSet,
                                             String errorCorrection, String margin,
                                             int colorBlack, int colorWhite) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        if (width < 0 || height < 0) {
            return null;
        }
        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            if (!TextUtils.isEmpty(characterSet)) {
                hints.put(EncodeHintType.CHARACTER_SET, characterSet);
            }
            if (!TextUtils.isEmpty(errorCorrection)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrection);
            }
            if (!TextUtils.isEmpty(margin)) {
                hints.put(EncodeHintType.MARGIN, margin);
            }
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = colorBlack;
                    } else {
                        pixels[y * width + x] = colorWhite;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String readQRCode(Bitmap qrCodeBitmap) {
        int width = qrCodeBitmap.getWidth();
        int height = qrCodeBitmap.getHeight();
        int[] pixels = new int[width * height];
        qrCodeBitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap).getText();
        }catch (NotFoundException | ChecksumException | FormatException e){
            return null;
        }
    }

}
