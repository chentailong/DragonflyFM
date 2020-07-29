package net.along.fragonflyfm.base;

import android.media.MediaPlayer;
import android.util.Log;

import net.along.fragonflyfm.util.PlayUtil;

import java.io.IOException;

public class GetPlayer {
    private MediaPlayer fmPlay;
    private int currentIndex = 0;

    public void playUrl(String url) {
        fmPlay = new MediaPlayer();
        try {
            fmPlay.setDataSource(url);
            Log.e("MyPlayer", url);
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
