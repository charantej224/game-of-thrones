package com.thrones.of.game;

import com.thrones.of.game.Validator.GameValidator;
import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.resolver.QueryResolver;
import com.thrones.of.game.utils.MessagePrinter;

import java.util.Properties;
import java.util.Scanner;

import static com.thrones.of.game.config.Constants.*;

public class GameLauncher {

    private static GameLauncher gameLauncher;
    private Properties helpText = ApplicationConfiguration.getApplicationConfiguration().getHelptextProperties();

    private GameLauncher() {
    }

    public static GameLauncher getGamelauncher() {
        return gameLauncher = (null != gameLauncher) ? gameLauncher : new GameLauncher();
    }

    public void launchGame() throws Exception {
        init();
        boolean isGameRunning = true;
        Scanner in = new Scanner(System.in);
        while (isGameRunning) {
            System.out.println(CYAN + helpText.getProperty(BLANK_LINE));
            System.out.println(CYAN + helpText.getProperty(YOUR_COMMAND));
            String s = in.nextLine();
            QueryResolver queryResolver = new QueryResolver();
            queryResolver.resolveQuery(s);
        }
    }

    private void init() {
        (new MessagePrinter()).printStartupMessages();
        GameValidator validator = new GameValidator();
        validator.checkIfRegisteredUser();
    }
}
