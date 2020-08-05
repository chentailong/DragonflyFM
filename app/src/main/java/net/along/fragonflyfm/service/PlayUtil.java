package net.along.fragonflyfm.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import net.along.fragonflyfm.entity.Player;

import java.io.IOException;
import java.util.List;

public class PlayUtil extends Service {

    private static final String TAG = "PlayUtil";
    public static boolean IS_SERVICING = false;
    private static List<Player> PLAYER;
    private static MediaPlayer fmPlay;
    private int currentIndex = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 启动服务，实现播放
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (IS_SERVICING) ;
        playUrl(PLAYER.get(0).getPlayUrl());
        return START_STICKY;
    }

    /**
     * 下载数据并开始播放
     *
     * @param url
     */
    public void playUrl(String url) {
        fmPlay = new MediaPlayer();
        try {
            fmPlay.setAudioStreamType(AudioManager.STREAM_MUSIC);
            fmPlay.setDataSource(url);
            Log.e(TAG, "playUrl: " + url);
            start();
            IS_SERVICING = true;
            fmPlay.setOnCompletionListener(mp -> {  //播放结束以后的监听事件
                if (hasNext()) {
                    currentIndex++;
                    playUrl(getPlayingList().get(currentIndex).getPlayUrl());
                } else {
                    release();
                    return;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放音乐
     */
    public static void start() {
        try {
            fmPlay.setAudioStreamType(AudioManager.STREAM_MUSIC);
            fmPlay.prepare();
            fmPlay.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止播放
     */
    public static void stop() {
        fmPlay.stop();
    }

    /**
     * 暂停播放
     */
    public static void pause() {
        fmPlay.pause();
    }

    /**
     * 释放资源
     */
    public static void release() {
        IS_SERVICING = false;
        fmPlay.release();
    }

    /**
     * 是否有下一首
     *
     * @return
     */
    public boolean hasNext() {
        if (currentIndex + 1 >= PlayUtil.getPlayingList().size()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 停止服务
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 传递数据，将所需数据传输到这，存入List数组中，在服务启动时启用，显示出来
     *
     * @param player
     */
    public static void setPlayingList(List<Player> player) {
        PLAYER = player;
        IS_SERVICING = false;
    }

    public static List<Player> getPlayingList() {
        return PLAYER;
    }


}
