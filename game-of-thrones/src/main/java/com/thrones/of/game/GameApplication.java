package com.thrones.of.game;

import com.thrones.of.game.utils.FileLoader;

import java.io.IOException;

public class GameApplication {

    public static void main(String[] args) throws Exception {
        FileLoader.loadFiles();
        GameLauncher.getGamelauncher().launchGame();
    }

}
