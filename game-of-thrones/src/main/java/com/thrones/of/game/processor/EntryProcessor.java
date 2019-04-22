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

    public void registerPlayer(String name) {
        if (!validator.validateRegistry(name)){
            session.setUpdateStagePostCommand(Boolean.FALSE);
            return;
        }

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
        if(session.getPlayerProfile() != null){
            System.out.println(GREEN + helpProperties.getProperty(NEW_GAME).replace("$$", name));
            PlayerProfile playerProfile = session.getPlayerProfile().clone();
            session.clearSession();
            Session.getInstance().setPlayerProfile(playerProfile);
            // player already registered, so when new game there is not need for him to register again
            Session.getInstance().setCurrentStage(20);
        } else {
            System.out.println(GREEN + helpProperties.getProperty(NEW_GAME_NOT_ALLOWED));
        }

    }
}

