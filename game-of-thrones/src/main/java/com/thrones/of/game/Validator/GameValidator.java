package com.thrones.of.game.Validator;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.Member;
import com.thrones.of.game.domain.Session;
import com.thrones.of.game.domain.Weapon;
import com.thrones.of.game.processor.CommandHelper;

import java.util.Optional;
import java.util.Properties;

import static com.thrones.of.game.config.Constants.*;

public class GameValidator {

    private Session session = Session.getInstance();
    Properties helpProperties = ApplicationConfiguration.getApplicationConfiguration().getHelptextProperties();
    Properties patternProperties = ApplicationConfiguration.getApplicationConfiguration().getPatternProperties();

    public boolean canUserFightWithWeapon(Member member, Weapon weapon) {
        return member.getStrength() > weapon.getStrength();
    }

    public boolean isSelectedUserHavingWeapons() {
        Optional<Weapon> optionalWeapon = session.getSelectedWeapons().stream().filter(weapon -> session.getSelected().getStrength() > weapon.getStrength())
                .findFirst();
        return optionalWeapon.isPresent();
    }

    public boolean isEnemyUserHavingWeapons() {
        Optional<Weapon> optionalWeapon = session.getEnemyWeapons().stream().filter(weapon -> session.getEnemy().getStrength() > weapon.getStrength())
                .findFirst();
        return optionalWeapon.isPresent();
    }

    public boolean validateRegistry(String name) {
        if (session.getPlayerProfile() != null) {
            System.out.println(GREEN + helpProperties.getProperty(NAME_ALREADY_REGISTERED).replace("$$", session.getPlayerProfile().getPlayerName()));
            return false;
        }
        if ("".equalsIgnoreCase(name)) {
            System.out.println(GREEN + helpProperties.getProperty(NAME_INVALID));
            return false;
        }
        return true;
    }

    public boolean validatePlayerLevel(String input) {
        String entryLevelStr = patternProperties.getProperty(input + "_entry_level");
        Integer entryLevel = Integer.valueOf(entryLevelStr);
        if (entryLevel == 0 || entryLevel == session.getCurrentStage())
            return true;
        else {
            System.out.println(BLUE_BOLD_BRIGHT + helpProperties.getProperty(COMMAND_NOT_ALLOWED));
            int i = 1;
            String commands = "";
            while (null != patternProperties.getProperty("pattern" + i)) {
                if (session.getCurrentStage() == Integer.parseInt(patternProperties.getProperty("pattern" + i + "_entry_level"))) {
                    commands = commands + " - " + patternProperties.getProperty("pattern" + i);
                }
                i++;
            }
            System.out.println(BLUE_BOLD_BRIGHT + helpProperties.getProperty(HINT_COMMAND_NOT_ALLOWED).replace("$$", commands));
            System.out.println(RESET);
            return false;
        }

    }

    public void checkIfRegisteredUser() {
        if(session.getPlayerProfile() == null){
            System.out.println(CYAN + helpProperties.getProperty(ENTRY_WELCOME));
            System.out.println(CYAN + helpProperties.getProperty(ENTRY_WELCOME));
        } else {
            CommandHelper commandHelper = new CommandHelper();
            System.out.println(CYAN + helpProperties.getProperty(WELCOME_BACK).replace("$$",session.getPlayerProfile().getPlayerName()));
            commandHelper.canUseCommands();
        }
    }
}
