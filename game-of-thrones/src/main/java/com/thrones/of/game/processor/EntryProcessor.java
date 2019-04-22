package com.thrones.of.game.processor;

import com.thrones.of.game.Validator.GameValidator;
import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.PlayerProfile;
import com.thrones.of.game.domain.Session;

import java.util.Properties;

import static com.thrones.of.game.config.Constants.*;

/**
 * Class : EntryProcessor.
 * This is a processor class, which process the requests based on the commands.
 * basically, this helps in the login & registration level requests.
 */
public class EntryProcessor {

    GameValidator validator = new GameValidator();
    private ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance();
    private Properties helpProperties = applicationConfiguration.getHelptextProperties();
    private Session session = Session.getInstance();

    /**
     * Method : registerPlayer
     * @param name
     * this helps to register player, who has not yet signed up with the application.
     */
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

    /**
     * Method: deregisterPlayer
     * @param name
     * this helps to remove the user who has signed up with the application.
     */
    public void deregisterPlayer(String name) {
        System.out.println(GREEN + helpProperties.getProperty(NAME_DEREGISTERED).replace("$$", name));
        session.clearSession();
    }

    /**
     * Method : startNewGame
     * @param name
     * once the game is over, this gets triggerred by a command issued to clear the existing game and start a new game.
     * this however, can be applied at anypoint to remove the existing game.
     */
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

