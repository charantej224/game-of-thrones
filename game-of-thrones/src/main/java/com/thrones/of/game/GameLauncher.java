package com.thrones.of.game;

import com.thrones.of.game.domain.Session;
import com.thrones.of.game.resolver.QueryResolver;

import java.util.Scanner;

import static com.thrones.of.game.config.CONSTANTS.RED;

public class GameLauncher {

    private static GameLauncher gameLauncher;
    private Session session = Session.getInstance();

    private GameLauncher() {
    }

    public static GameLauncher getGamelauncher() {
        return gameLauncher = (null != gameLauncher) ? gameLauncher : new GameLauncher();
    }

    public void launchGame() throws Exception{
        boolean isGameRunning = true;
        Scanner in = new Scanner(System.in);
        while (isGameRunning){
            System.out.println(RED + "your command my Load ");
            String s = in.nextLine();
            QueryResolver queryResolver = new QueryResolver();
            queryResolver.resolveQuery(s);
        }
    }

}
