package net.along.fragonflyfm.util;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import net.along.fragonflyfm.service.PlayUtil;

import java.io.IOException;

public class GetPlayer {
    private MediaPlayer fmPlay;
    private int currentIndex = 0;
    private String TAG = "GetPlayer";

    public void playUrl(String url) {
        fmPlay = new MediaPlayer();
        fmPlay.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            fmPlay.setDataSource(url);
            Log.e(TAG, "playUrl: " + url );
            fmPlay.prepare();
            fmPlay.start();
            PlayUtil.IS_SERVICING = true;
            fmPlay.setOnCompletionListener(mp -> {
                if (hasNext()) {
                    currentIndex++;
                    playUrl(PlayUtil.getPlayingList().get(currentIndex).getPlayUrl());
                } else {
                    stop();
                    return;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean next() {
        if (hasNext()) {
            currentIndex++;
            playUrl(PlayUtil.getPlayingList().get(currentIndex).getPlayUrl());
            return true;
        } else {
            return false;
        }
    }

    public boolean previous() {
        if (currentIndex - 1 >= 0) {
            currentIndex--;
            playUrl(PlayUtil.getPlayingList().get(currentIndex).getPlayUrl());
            return true;
        } else {
            return false;
        }
    }

    public void stop() {
        PlayUtil.IS_SERVICING = false;
        fmPlay.release();
    }

    public boolean hasNext() {
        if (currentIndex + 1 >= PlayUtil.getPlayingList().size()) {
            return false;
        } else {
            return true;
        }
    }
}
