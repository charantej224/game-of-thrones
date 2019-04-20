package com.thrones.of.game;

import com.thrones.of.game.domain.Session;
import com.thrones.of.game.timers.StrengthBooster;
import com.thrones.of.game.utils.FileLoader;

import java.util.Timer;
import java.util.TimerTask;

public class GameApplication {

    public static void main(String[] args) throws Exception {
        FileLoader.loadFiles();
        Session.getInstance();
        Timer timer = new Timer();
        TimerTask task = new StrengthBooster();
        timer.schedule(task,1000*60);
        GameLauncher.getGamelauncher().launchGame();
    }

}
