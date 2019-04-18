package com.thrones.of.game;

import com.thrones.of.game.utils.FileLoader;

import java.io.IOException;

public class GameApplication {

    public static void main(String[] args) throws IOException {
        FileLoader.loadFiles();
        //GameLauncher.getGamelauncher().launchGame();
    }

}
