package com.thrones.of.game;

import com.thrones.of.game.domain.Session;
import com.thrones.of.game.utils.FileLoader;

public class GameApplication {

    public static void main(String[] args) throws Exception {
        FileLoader.loadFiles();
        Session.getInstance();
        GameLauncher.getGamelauncher().launchGame();
    }

}
