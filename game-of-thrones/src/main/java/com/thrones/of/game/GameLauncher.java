package com.thrones.of.game;

import com.thrones.of.game.Validator.GameValidator;
import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.resolver.QueryResolver;
import com.thrones.of.game.utils.MessagePrinter;

import java.util.Properties;
import java.util.Scanner;

import static com.thrones.of.game.config.Constants.*;

/**
 * GameLauncher following singleton design-pattern. This is to help game launch in single instance.
 * further more could be achieved with file locking to truly represent single launch of system.
 * In the scope of this version, its not implemented yet.
 */
public class GameLauncher {

    private static GameLauncher gameLauncher;
    private Properties helpText = ApplicationConfiguration.getInstance().getHelptextProperties();

    /**
     * constructor defined private to avoid creating instances outside the class to help singleton pattern.
     */
    private GameLauncher() {
    }

    /**
     * Method name : getInstance
     * @return GameLauncher
     * This helps to get one instance of GameLauncher.
     */
    public static GameLauncher getInstance() {
        return gameLauncher = (null != gameLauncher) ? gameLauncher : new GameLauncher();
    }

    /**
     * Method name:  lanchGame
     * @return void
     *  Launch game helps the scanner running to read the inputs from console for playing the game.
     *  This works reading the command line inputs as per requirement.
     */
    public void launchGame() {
        init();
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println(CYAN + helpText.getProperty(BLANK_LINE));
            System.out.println(CYAN + helpText.getProperty(YOUR_COMMAND));
            String s = in.nextLine();
            QueryResolver queryResolver = new QueryResolver();
            queryResolver.resolveQuery(s);
        }
    }

    /**
     * Method name:  init
     * @return void
     *  helps to validate the launch of the game.
     */
    private void init() {
        (new MessagePrinter()).printStartupMessages();
        GameValidator validator = new GameValidator();
        validator.checkIfRegisteredUser();
    }
}
