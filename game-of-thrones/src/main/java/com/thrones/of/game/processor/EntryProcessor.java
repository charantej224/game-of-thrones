package com.thrones.of.game.processor;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.PlayerProfile;
import com.thrones.of.game.domain.Session;

import java.util.Properties;

import static com.thrones.of.game.config.CONSTANTS.*;

public class EntryProcessor {

    private ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getApplicationConfiguration();
    private Properties helpProperties = applicationConfiguration.getHelptextProperties();
    private Session session = Session.getInstance();

    public void welcomePlayer(String action) {
        System.out.println(GREEN + helpProperties.getProperty(ENTRY_WELCOME));
    }

    public void registerPlayer(String name) {
        if (null != session.getPlayerProfile()) {
            System.out.println(GREEN + helpProperties.getProperty(NAME_ALREADY_REGISTERED).replace("$$", name));
        } else {
            PlayerProfile playerProfile = new PlayerProfile();
            playerProfile.setPlayerName(name);
            session.setPlayerProfile(playerProfile);
            System.out.println(GREEN + helpProperties.getProperty(NAME_REGISTERED).replace("$$", name));
        }
    }

    public void deregisterPlayer(String name) {
        System.out.println(GREEN + helpProperties.getProperty(NAME_DEREGISTERED).replace("$$", name));
        session.clearSession();
    }
}
