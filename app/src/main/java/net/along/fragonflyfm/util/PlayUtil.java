package net.along.fragonflyfm.util;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import net.along.fragonflyfm.service.PlayService;

import java.io.IOException;

/**
 * 播放器对象
 */

public class PlayUtil {
    private static MediaPlayer fmPlay;
    public static int currentIndex;
    private String TAG = "PlayUtil";

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
            fmPlay.setOnPreparedListener(onPreparedListener);
            fmPlay.setOnErrorListener(errorListener);
            PlayService.IS_SERVICING=true;
            fmPlay.setOnCompletionListener(mp -> {  //播放结束以后的监听事件
                if (hasNext()) {
                    currentIndex++;
                    playUrl(PlayService.getPlayingList().get(currentIndex).getPlayUrl());
                } else {
                    release();
                    return;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void pause(){
        fmPlay.pause();
    }

    public static void stop(){
        fmPlay.stop();
    }

    public void release(){
        PlayService.IS_SERVICING=false;
        fmPlay.release();
    }

    public boolean hasNext(){
        if (currentIndex+1>= PlayService.getPlayingList().size()){
            return false;
        }else{
            return true;
        }
    }

    private MediaPlayer.OnErrorListener errorListener=new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
            Log.e("MyPlayer","播放错误：");
            return false;
        }
    };

    private MediaPlayer.OnPreparedListener onPreparedListener=new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            fmPlay.start();
            Log.e("MyPlayer","加载媒体流完毕，开始播放");
        }
    };

}
