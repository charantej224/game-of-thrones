package com.thrones.of.game.processor;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.Session;

import java.util.Properties;

import static com.thrones.of.game.config.CONSTANTS.EXIT_MESSAGE;
import static com.thrones.of.game.config.CONSTANTS.PURPLE;

public class ExitProcessor {

    Properties helpProperties = ApplicationConfiguration.getApplicationConfiguration().getHelptextProperties();
    Session session = Session.getInstance();

    public void quitGame(String action) {
        System.out.println(PURPLE + helpProperties.getProperty(EXIT_MESSAGE).replace("$$", session.getPlayerName()));
        System.exit(0);
    }

}
