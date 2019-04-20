package com.thrones.of.game.processor;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.Session;
import com.thrones.of.game.utils.InputOutputHelper;

import java.io.IOException;
import java.util.Properties;

import static com.thrones.of.game.config.Constants.*;

public class ExitProcessor {

    Properties helpProperties = ApplicationConfiguration.getApplicationConfiguration().getHelptextProperties();
    Session session = Session.getInstance();

    public void quitGame(String action) {
        System.out.println(PURPLE + helpProperties.getProperty(EXIT_MESSAGE).replace("$$", session.getPlayerProfile().getPlayerName()));
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
