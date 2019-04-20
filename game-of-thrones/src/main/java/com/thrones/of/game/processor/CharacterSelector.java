package com.thrones.of.game.processor;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.HousesModel;
import com.thrones.of.game.domain.Member;
import com.thrones.of.game.domain.Session;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import static com.thrones.of.game.config.CONSTANTS.*;

public class CharacterSelector {

    private Session session = Session.getInstance();
    private ApplicationConfiguration applicationConfiguration = ApplicationConfiguration.getApplicationConfiguration();
    private Map<String, HousesModel> housesModelMap = applicationConfiguration.getHouseMap();
    private Properties helpProperties = applicationConfiguration.getHelptextProperties();

    public void selectPlayerCharacterHouse(String name) {
        Optional<HousesModel> optionalHouse = selectHouse(name);
        if (optionalHouse.isPresent()) {
            HousesModel house = optionalHouse.get();
            System.out.println(helpProperties.getProperty(HOUSE_SELECTED).replace("$$", house.getHouseName()));
            System.out.println(helpProperties.getProperty(HOUSE_TAG).replace("$$", house.getTagLines()));
            System.out.println(helpProperties.getProperty(CHOOSE_MEMBERS).replace("$$", house.getMembers().toString()));
            HousesModel selectedHouse = house.clone();
            session.setSelectedHouse(selectedHouse);
            session.setSelectedWeapons(selectedHouse.getWeapons());
        } else {
            System.out.println(helpProperties.getProperty(HOUSE_NOT_FOUND));
        }
    }

    public void selectEnemyCharacterHouse(String name) {
        if (session.getSelectedHouse() == null) {
            System.out.println(helpProperties.getProperty(HOUSE_NOT_SELECTED));
            return;
        } else if (session.getSelectedHouse().getHouseName().toLowerCase().contains(name) ||
                name.contains(session.getSelectedHouse().getHouseName().toLowerCase())) {
            System.out.println(helpProperties.getProperty(ENEMY_SELECTED_SAMEHOUSE));
            return;
        }
        Optional<HousesModel> optionalHouse = selectHouse(name);
        if (optionalHouse.isPresent()) {
            HousesModel house = optionalHouse.get();
            System.out.println(helpProperties.getProperty(ENEMY_HOUSE_SELECTED).replace("$$", house.getHouseName()));
            System.out.println(helpProperties.getProperty(HOUSE_TAG).replace("$$", house.getTagLines()));
            System.out.println(helpProperties.getProperty(CHOOSE_MEMBERS).replace("$$", house.getMembers().toString()));
            HousesModel enemyHouse = house.clone();
            session.setEnemyHouse(enemyHouse);
            session.setEnemyWeapons(enemyHouse.getWeapons());
        } else {
            System.out.println(helpProperties.getProperty(HOUSE_NOT_FOUND));
        }
    }

    public void selectEnemyCharacter(String name) {
        Optional<Member> optionalMember =
                session.getEnemyHouse().getMembers().stream()
                        .filter(member ->
                                member.getName().toLowerCase().contains(name) ||
                                        name.contains(member.getName().toLowerCase()))
                        .findFirst();
        if(optionalMember.isPresent()){
            System.out.println(helpProperties.getProperty(ENEMY_SELECTED).replace("$$", optionalMember.get().getName()));
            session.setEnemy(optionalMember.get().clone());
        } else {
            System.out.println(helpProperties.getProperty(NO_MEMBER));

        }
    }

    public void selectCharacter(String name) {
        Optional<Member> optionalMember =
                session.getSelectedHouse().getMembers().stream()
                        .filter(member ->
                                member.getName().toLowerCase().contains(name) ||
                                        name.contains(member.getName().toLowerCase()))
                        .findFirst();
        if(optionalMember.isPresent()){
            System.out.println(helpProperties.getProperty(MEMBER_SELECTED).replace("$$", optionalMember.get().getName()));
            session.setSelected(optionalMember.get().clone());
        } else {
            System.out.println(helpProperties.getProperty(NO_MEMBER));

        }
    }


    public Optional<HousesModel> selectHouse(String name) {
        return housesModelMap.entrySet().stream()
                .filter(entry ->
                        entry.getValue().getHouseName().toLowerCase().contains(name) || name.contains(entry.getValue().getHouseName().toLowerCase()))
                .map(Map.Entry::getValue)
                .findFirst();
    }
}
