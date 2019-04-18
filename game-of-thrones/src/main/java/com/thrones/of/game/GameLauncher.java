package com.thrones.of.game;

public class GameLauncher {

    private static GameLauncher gameLauncher;

    private GameLauncher() {
    }

    public static GameLauncher getGamelauncher() {
        return gameLauncher = (null != gameLauncher) ? gameLauncher : new GameLauncher();
    }

    public void launchGame() {
    }

}
