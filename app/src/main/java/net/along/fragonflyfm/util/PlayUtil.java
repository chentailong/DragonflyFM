package net.along.fragonflyfm.util;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import net.along.fragonflyfm.base.GetPlayer;
import net.along.fragonflyfm.entity.Player;

import java.util.List;

public class PlayUtil extends IntentService {

    public static boolean IS_SERVICING=false;
    private static List<Player> PLAYER;
    private static boolean RUNNING=false;
    private static GetPlayer myPlayer;

    public PlayUtil(String name) {
        super(name);
    }

    public PlayUtil() {
        super("PlayService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (IS_SERVICING)return;
        myPlayer=new GetPlayer();
        myPlayer.playUrl(PLAYER.get(0).getPlayUrl());
    }

    public static void setPlayingList(List<Player> player) {
        PLAYER = player;
        IS_SERVICING=false;
    }

    public static List<Player> getPlayingList(){
        return PLAYER;
    }
}
