package com.thrones.of.game;

import com.thrones.of.game.domain.Session;
import com.thrones.of.game.timers.StrengthBooster;
import com.thrones.of.game.utils.FileLoader;

import java.util.Timer;
import java.util.TimerTask;

/**
 *  This is the main class from where application would be kicked off.
 *  through its main method, it helps to load files, load session,
 *  and start timer to work for strength booster.
 */
public class GameApplication {

    /**
     * main method to launch the game application.
     * @param args
     */
    public static void main(String[] args) {
        FileLoader.loadFiles();
        Session.getInstance();
        Timer timer = new Timer();
        TimerTask task = new StrengthBooster();
        System.out.println("Starting the strength booster in the background");
        timer.scheduleAtFixedRate(task,1000*60,1000*60);
        GameLauncher.getInstance().launchGame();
    }

}
