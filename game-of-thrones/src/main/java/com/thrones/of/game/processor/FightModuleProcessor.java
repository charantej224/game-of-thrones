package com.thrones.of.game.processor;

import com.thrones.of.game.Validator.GameValidator;
import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.Member;
import com.thrones.of.game.domain.Session;
import com.thrones.of.game.domain.Weapon;

import java.util.Optional;
import java.util.Properties;

import static com.thrones.of.game.config.Constants.*;

public class FightModuleProcessor {

    GameValidator gameValidator;
    Session session = Session.getInstance();
    Properties helpProperties = ApplicationConfiguration.getApplicationConfiguration().getHelptextProperties();

    public void fightEnemy(String name) {
        gameValidator = new GameValidator();
        if (session.getCurrentGameOver() == null) {
            session.setCurrentGameOver(Boolean.FALSE);
        }

        if (session.getCurrentGameOver()) {
            System.out.println(helpProperties.getProperty(NO_FIGHT_GAME_OVER));
            System.out.println(helpProperties.getProperty(HINT_NO_FIGHT_GAME_OVER));
        }

        if (!gameValidator.isSelectedUserHavingWeapons()) {
            System.out.println(helpProperties.getProperty(LOST_GAME_NO_WEAPON_ELIGIBLE));
            session.getPlayerProfile().updateLostGames();
        }

        Optional<Weapon> weaponOptional = session.getSelectedWeapons().stream()
                .filter(weapon -> name.contains(weapon.getName().toLowerCase()) ||
                        weapon.getName().toLowerCase().contains(name)).findFirst();

        if (weaponOptional.isPresent()) {
            if (!gameValidator.canUserFightWithWeapon(session.getSelected(), weaponOptional.get())) {
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

        for (Weapon enemy : session.getEnemyWeapons()) {
            if (enemy.getStrength() == playerWeapon.getStrength()) {
                selectedWeapon = enemy;
                status = "EQUAL";
                break;
            } else if (enemy.getStrength() < playerWeapon.getStrength()) {
                if (selectedWeapon == null) {
                    selectedWeapon = enemy;
                    status = "MORE";
                } else if (selectedWeapon.getStrength() < enemy.getStrength()) {
                    selectedWeapon = enemy;
                    status = "MORE";
                }
            } else if (enemy.getStrength() > playerWeapon.getStrength() && selectedWeapon == null) {
                selectedWeapon = enemy;
                status = "LESS";
            }
        }
        if (!precheckSelectedWeapon(selectedWeapon))
            return;
        handleComms(playerWeapon, selectedWeapon, status);
        checkGameStatus();
    }


    public void handleComms(Weapon playerWeapon, Weapon enemyWeapon, String status) {
        Member selected = session.getSelected();
        Member enemy = session.getEnemy();
        selected.setStrength(selected.getStrength() - playerWeapon.getStrength());
        enemy.setStrength(enemy.getStrength() - enemyWeapon.getStrength());

        if ("EQUAL".equalsIgnoreCase(status)) {
            System.out.println(helpProperties.getProperty(FIGHT_WEAPON).replace("$$", playerWeapon.getName()).replace("**", enemyWeapon.getName()));
            session.getSelectedWeapons().remove(playerWeapon);
            session.getEnemyWeapons().remove(enemyWeapon);
        } else if ("MORE".equalsIgnoreCase(status)) {
            System.out.println(helpProperties.getProperty(ENEMY_WEAPON_KILLED).replace("$$", playerWeapon.getName()).replace("**", enemyWeapon.getName()));
            session.getEnemyWeapons().remove(enemyWeapon);
        } else if ("LESS".equalsIgnoreCase(status)) {
            System.out.println(helpProperties.getProperty(YOUR_WEAPON_KILLED).replace("$$", playerWeapon.getName()).replace("**", enemyWeapon.getName()));
            session.getEnemyWeapons().remove(enemyWeapon);
        }
    }

    public void checkGameStatus() {
        if (session.getEnemyWeapons().isEmpty() && session.getSelectedWeapons().isEmpty()) {
            if (session.getSelected().getStrength() < session.getEnemy().getStrength()) {
                System.out.println(helpProperties.getProperty(YOU_WIN));
                session.getPlayerProfile().updateWins();
                session.setCurrentGameOver(Boolean.TRUE);
            } else if (session.getSelected().getStrength() == session.getEnemy().getStrength()) {
                System.out.println(helpProperties.getProperty(YOU_TIED));
                session.getPlayerProfile().updateTies();
                session.setCurrentGameOver(Boolean.TRUE);
            } else {
                System.out.println(helpProperties.getProperty(YOU_LOSE));
                session.getPlayerProfile().updateLostGames();
                session.setCurrentGameOver(Boolean.TRUE);
            }
        } else if (session.getEnemyWeapons().isEmpty() && !session.getSelectedWeapons().isEmpty()) {
            System.out.println(helpProperties.getProperty(ENEMY_NO_WEAPONS_LOST));
            session.getPlayerProfile().updateWins();
            session.setCurrentGameOver(Boolean.TRUE);
        } else if (!session.getEnemyWeapons().isEmpty() && session.getSelectedWeapons().isEmpty()) {
            System.out.println(helpProperties.getProperty(YOU_NO_WEAPONS_LOST));
            session.getPlayerProfile().updateLostGames();
            session.setCurrentGameOver(Boolean.TRUE);
        }
    }

    public Boolean precheckSelectedWeapon(Weapon selectedWeapon) {
        if (!gameValidator.canUserFightWithWeapon(session.getEnemy(), selectedWeapon)) {
            System.out.println(helpProperties.getProperty(ENEMY_NO_WEAPONS_LOST));
            session.getPlayerProfile().updateWins();
            session.setCurrentGameOver(Boolean.TRUE);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }


}
