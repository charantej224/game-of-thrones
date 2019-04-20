package com.thrones.of.game.processor;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.HousesModel;
import com.thrones.of.game.domain.Session;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import static com.thrones.of.game.config.CONSTANTS.*;

public class HelpProcessor {

    ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getApplicationConfiguration();
    Properties helpProperties = applicationConfiguration.getHelptextProperties();
    Session session = Session.getInstance();

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


    public void getInfo(String input) {
        if (session.getPlayerProfile() != null) {
            System.out.println(BLUE + helpProperties.getProperty(SESSION_INFO).replace("$$", session.getPlayerProfile().getPlayerName()));
            System.out.println(BLUE + helpProperties.getProperty(HOUSE_INFO).replace("$$", session.getSelectedHouse().getHouseName()).replace("**", session.getSelectedHouse().getTagLines()));
            System.out.println(BLUE + helpProperties.getProperty(MEMBER_INFO).replace("$$", session.getSelected().getName()));
            System.out.println(BLUE + helpProperties.getProperty(ENEMY_HOUSE_INFO).replace("$$", session.getEnemyHouse().getHouseName()).replace("**", session.getSelectedHouse().getTagLines()));
            System.out.println(BLUE + helpProperties.getProperty(ENEMY_INFO).replace("$$", session.getEnemy().getName()));
        } else {
            System.out.println(BLUE + helpProperties.getProperty(SESSION_NOT_READY));
        }
    }

    public void getmyWeapons(String input) {
        System.out.println(BLUE + helpProperties.getProperty(PLAYER_STRENGTH).replace("$$", session.getSelected().getStrength().toString()));
        System.out.println(BLUE + helpProperties.getProperty(PLAYER_WEAPONS).replace("$$", session.getSelectedWeapons().toString()));

    }
}
