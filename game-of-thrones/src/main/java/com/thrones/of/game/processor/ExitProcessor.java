package com.thrones.of.game.processor;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.Session;
import com.thrones.of.game.utils.InputOutputHelper;

import java.io.IOException;
import java.util.Properties;

import static com.thrones.of.game.config.Constants.*;

/**
 * Class : ExitProcessor.
 * this class deals with leaving the game and ensuring the session is maintained to save the state of the game.
 */
public class ExitProcessor {

    Properties helpProperties = ApplicationConfiguration.getInstance().getHelptextProperties();
    Session session = Session.getInstance();

    /**
     * Method : quitGame
     * @param action
     * This method gets executed based on the request from the command line to quit the game after saving resources.
     */
    public void quitGame(String action) {
        System.out.println(PURPLE + helpProperties.getProperty(EXIT_MESSAGE));
        System.out.println(PURPLE + helpProperties.getProperty(PROFILE_SAVED));
        InputOutputHelper<Session> inputOutputHelper = new InputOutputHelper<>();
        try {
            inputOutputHelper.writeFile("session.ser", session);
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
        System.exit(0);
    }

}
