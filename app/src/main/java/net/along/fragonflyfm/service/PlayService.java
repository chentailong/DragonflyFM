package net.along.fragonflyfm.service;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import net.along.fragonflyfm.entity.Player;
import net.along.fragonflyfm.util.PlayUtil;

import java.util.List;

public class PlayService extends IntentService {

    private static final String TAG = "PlayUtil";
    public static boolean IS_SERVICING = false;
    private static List<Player> PLAYER;
    private static MediaPlayer fmPlay;
    public static int currentIndex;
    private PlayUtil mPlayUtil;

    public PlayService(String name) {
        super(name);
    }

    public PlayService() {
        super("PlayService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (IS_SERVICING)return;
        mPlayUtil=new PlayUtil();
        mPlayUtil.playUrl(PLAYER.get(intent.getIntExtra("startIndex",0)).getPlayUrl());
    }
    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        IS_SERVICING=false;
        if (mPlayUtil!=null){
            mPlayUtil.release();
            mPlayUtil=null;
        }

        Log.e("PlayService","播放服务开启");
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
