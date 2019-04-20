package com.thrones.of.game.processor;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.Member;
import com.thrones.of.game.domain.Session;
import com.thrones.of.game.domain.Weapon;

import java.util.Optional;
import java.util.Properties;

import static com.thrones.of.game.config.CONSTANTS.*;

public class FightModuleProcessor {

    Session session = Session.getInstance();
    Properties helpProperties = ApplicationConfiguration.getApplicationConfiguration().getHelptextProperties();

    public void fightEnemy(String name) {
        Optional<Weapon> weaponCheck = session.getSelectedWeapons().stream().filter(weapon -> weapon.getStrength() < session.getSelected().getStrength())
                .findFirst();
        if(!weaponCheck.isPresent()){
            System.out.println("You lost game as you don't have enough strength");
            session.getPlayerProfile().updateLostGames();
        }

        Optional<Weapon> weaponOptional = session.getSelectedWeapons().stream()
                .filter(weapon -> name.contains(weapon.getName().toLowerCase()) ||
                        weapon.getName().toLowerCase().contains(name)).findFirst();

        if (weaponOptional.isPresent()) {
            if(weaponOptional.get().getStrength() > session.getSelected().getStrength()) {
                System.out.println(helpProperties.getProperty(NO_STRENGTH));
                return;
            }
            fightEnemy(weaponOptional.get());
        } else {
            System.out.println(helpProperties.getProperty(NO_WEAPON_FOUND));
        }
    }

    public void fightEnemy(Weapon playerWeapon) {
        Weapon selectedWeapon = null;
        String status = null;

        for(Weapon enemy: session.getEnemyWeapons()){
            if(enemy.getStrength() == playerWeapon.getStrength()){
                selectedWeapon = enemy;
                status = "EQUAL";
                break;
            } else if(enemy.getStrength() < playerWeapon.getStrength()){
                if(selectedWeapon == null){
                    selectedWeapon = enemy;
                    status = "MORE";
                } else if(selectedWeapon.getStrength() < enemy.getStrength()){
                    selectedWeapon = enemy;
                    status = "MORE";
                }
            } else if(enemy.getStrength() > playerWeapon.getStrength() && selectedWeapon == null){
                selectedWeapon = enemy;
                status = "LESS";
            }
        }
        //prechecks(playerWeapon,selectedWeapon);
        handleComms(playerWeapon,selectedWeapon,status);
        checkGameStatus();
    }


    public void handleComms(Weapon playerWeapon,Weapon enemyWeapon,String status){
        if("EQUAL".equalsIgnoreCase(status)){
            System.out.println(helpProperties.getProperty(FIGHT_WEAPON).replace("$$", playerWeapon.getName()).replace("**", enemyWeapon.getName()));
            session.getSelectedWeapons().remove(playerWeapon);
            session.getEnemyWeapons().remove(enemyWeapon);
            Member selected = session.getSelected();
            Member enemy = session.getEnemy();
            selected.setStrength(selected.getStrength() - playerWeapon.getStrength());
            enemy.setStrength(enemy.getStrength() - enemyWeapon.getStrength());
        } else if("MORE".equalsIgnoreCase(status)){
            System.out.println(helpProperties.getProperty(YOUR_WEAPON_KILLED).replace("$$", playerWeapon.getName()).replace("**", enemyWeapon.getName()));
            session.getSelectedWeapons().remove(playerWeapon);
            session.getEnemyWeapons().remove(enemyWeapon);
            Member selected = session.getSelected();
            Member enemy = session.getEnemy();
            selected.setStrength(selected.getStrength() - playerWeapon.getStrength());
            enemy.setStrength(enemy.getStrength() - enemyWeapon.getStrength());
        } else if("LESS".equalsIgnoreCase(status)){
            System.out.println(helpProperties.getProperty(YOUR_WEAPON_KILLED).replace("$$", playerWeapon.getName()).replace("**", enemyWeapon.getName()));
            session.getSelectedWeapons().remove(playerWeapon);
            session.getEnemyWeapons().remove(enemyWeapon);
            Member selected = session.getSelected();
            Member enemy = session.getEnemy();
            selected.setStrength(selected.getStrength() - playerWeapon.getStrength());
            enemy.setStrength(enemy.getStrength() - enemyWeapon.getStrength());
        }
    }

    public void checkGameStatus() {
        if (session.getEnemyWeapons().isEmpty() && session.getSelectedWeapons().isEmpty()) {
            if (session.getSelected().getStrength() < session.getEnemy().getStrength()) {
                System.out.println(helpProperties.getProperty(YOU_WIN));
                session.getPlayerProfile().updateWins();
            } else if (session.getSelected().getStrength() == session.getEnemy().getStrength()) {
                System.out.println(helpProperties.getProperty(YOU_TIED));
                session.getPlayerProfile().updateTies();
            } else {
                System.out.println(helpProperties.getProperty(YOU_LOSE));
                session.getPlayerProfile().updateLostGames();
            }
        } else if (session.getEnemyWeapons().isEmpty() && !session.getSelectedWeapons().isEmpty()) {
            System.out.println(helpProperties.getProperty(ENEMY_NO_WEAPONS_LOST));
            session.getPlayerProfile().updateWins();
        } else if (!session.getEnemyWeapons().isEmpty() && session.getSelectedWeapons().isEmpty()) {
            System.out.println(helpProperties.getProperty(YOU_NO_WEAPONS_LOST));
            session.getPlayerProfile().updateLostGames();
        }
    }


}
