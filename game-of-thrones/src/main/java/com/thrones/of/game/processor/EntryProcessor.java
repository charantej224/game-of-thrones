package com.thrones.of.game.processor;

import com.thrones.of.game.Validator.GameValidator;
import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.PlayerProfile;
import com.thrones.of.game.domain.Session;

import java.util.Properties;

import static com.thrones.of.game.config.Constants.*;

public class EntryProcessor {

    GameValidator validator = new GameValidator();
    private ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getApplicationConfiguration();
    private Properties helpProperties = applicationConfiguration.getHelptextProperties();
    private Session session = Session.getInstance();

    public void welcomePlayer(String action) {
        System.out.println(GREEN + helpProperties.getProperty(ENTRY_WELCOME));
    }

    public void registerPlayer(String name) {
        if (!validator.validateRegistry(name))
            return;
        PlayerProfile playerProfile = new PlayerProfile();
        playerProfile.setPlayerName(name);
        session.setPlayerProfile(playerProfile);
        System.out.println(GREEN + helpProperties.getProperty(NAME_REGISTERED).replace("$$", name));
    }

    public void deregisterPlayer(String name) {
        System.out.println(GREEN + helpProperties.getProperty(NAME_DEREGISTERED).replace("$$", name));
        session.clearSession();
    }

    public void startNewGame(String name) {
        System.out.println(GREEN + helpProperties.getProperty(NEW_GAME).replace("$$", name));
        PlayerProfile playerProfile = session.getPlayerProfile().clone();
        session.clearSession();
        Session.getInstance().setPlayerProfile(playerProfile);
    }
}

