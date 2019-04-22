package com.thrones.of.game.processor;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.HousesModel;
import com.thrones.of.game.domain.Session;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import static com.thrones.of.game.config.Constants.*;

/**
 * Class : GameExplorer
 * this class helps players to identify the resources available for the user to play with.
 */
public class GameExplorer {

    ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getInstance();
    Properties helpProperties = applicationConfiguration.getHelptextProperties();
    Session session = Session.getInstance();

    /**
     * Method : exploreHouse
     * @param houseName
     * this helps to explore all houses available and then helps to print required details as a help to user.
     */
    public void exploreHouse(String houseName) {
        Map<String, HousesModel> houseMap = applicationConfiguration.getHouseMap();

        if ("all".equalsIgnoreCase(houseName)) {
            houseMap.entrySet().forEach(entry -> {
                System.out.println(BLUE + entry.getValue().getHouseName() + " - " + entry.getValue().getTagLines());
            });
        } else {
            Optional<Map.Entry<String, HousesModel>> optional = houseMap.entrySet().stream()
                    .filter(entry ->
                            entry.getValue().getHouseName().equalsIgnoreCase(houseName) ||
                                    entry.getValue().getHouseName().toLowerCase().contains(houseName.toLowerCase())
                    ).findFirst();
            if (optional.isPresent()) {
                System.out.println(BLUE + optional.get().getValue().toString());
            } else {
                System.out.println(BLUE + "No such house present, my load. Please try again");
            }
        }
    }


    /**
     * Method : getInfo
     * @param input
     * helps user to understand his chosen players, house and enemy players and house.
     */
    public void getInfo(String input) {
        if (session.getPlayerProfile() != null) {
            System.out.println(BLUE + helpProperties.getProperty(SESSION_INFO).replace("$$", session.getPlayerProfile().getPlayerName()));
            System.out.println(BLUE + helpProperties.getProperty(HOUSE_INFO).replace("$$", session.getSelectedHouse().getHouseName()).replace("**", session.getSelectedHouse().getTagLines()));
            System.out.println(BLUE + helpProperties.getProperty(MEMBER_INFO).replace("$$", session.getSelected().getName()));
            System.out.println(BLUE + helpProperties.getProperty(ENEMY_HOUSE_INFO).replace("$$", session.getEnemyHouse().getHouseName()).replace("**", session.getEnemyHouse().getTagLines()));
            System.out.println(BLUE + helpProperties.getProperty(ENEMY_INFO).replace("$$", session.getEnemy().getName()));
        } else {
            System.out.println(BLUE + helpProperties.getProperty(SESSION_NOT_READY));
        }
    }

    /**
     * Method : getmyWeapons
     * @param input
     * this method gets executed by issuing a command in the console, this helps user to understand the type of weapons his
     * house has to offer him
     */
    public void getmyWeapons(String input) {
        System.out.println(BLUE + helpProperties.getProperty(PLAYER_STRENGTH).replace("$$", session.getSelected().getStrength().toString()));
        System.out.println(BLUE + helpProperties.getProperty(PLAYER_WEAPONS).replace("$$", session.getSelectedWeapons().toString()));
    }

    /**
     * Method : playerProfile.
     * @param name
     * this methods gets executed by issuing a commnd in the console, it helps to understand the criteria of player and his profile.
     */
    public void playerProfile(String name) {
        System.out.println(BLUE + helpProperties.getProperty(PLAYER_NAME).replace("$$", session.getPlayerProfile().getPlayerName()));
        System.out.println(BLUE + helpProperties.getProperty(PLAYER_LEVEL).replace("$$", session.getPlayerProfile().getPlayerLevel()));
        System.out.println(BLUE + helpProperties.getProperty(PLAYER_POINTS).replace("$$", session.getPlayerProfile().getPlayerPoints().toString()));
        System.out.println(BLUE + helpProperties.getProperty(PLAYER_WINS).replace("$$", session.getPlayerProfile().getWins().toString()));
        System.out.println(BLUE + helpProperties.getProperty(PLAYER_LOST).replace("$$", session.getPlayerProfile().getLost().toString()));
        System.out.println(BLUE + helpProperties.getProperty(PLAYER_TIES).replace("$$", session.getPlayerProfile().getTies().toString()));
    }
}
