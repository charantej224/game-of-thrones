package com.thrones.of.game.Validator;

import com.thrones.of.game.config.ApplicationConfiguration;
import com.thrones.of.game.domain.Member;
import com.thrones.of.game.domain.Session;
import com.thrones.of.game.domain.Weapon;
import com.thrones.of.game.processor.CommandHelper;

import java.util.Optional;
import java.util.Properties;

import static com.thrones.of.game.config.Constants.*;

/**
 * Class : GameValidator
 * this validator class helps to run through the validations required in the due course of the game.
 */
public class GameValidator {

    private Session session = Session.getInstance();
    Properties helpProperties = ApplicationConfiguration.getInstance().getHelptextProperties();
    Properties patternProperties = ApplicationConfiguration.getInstance().getPatternProperties();

    /**
     * Method : canUserFightWithWeapon
     * @param member
     * @param weapon
     * @return Boolean - TRUE/FALSE
     * this method is used to understand if user can fight with the weapon he has chosen
     */
    public boolean canUserFightWithWeapon(Member member, Weapon weapon) {
        return member.getStrength() > weapon.getStrength();
    }

    /**
     * Method : isSelectedUserHavingWeapons
     * @return Boolean - TRUE/FALSE
     * this is understand if the selected user has any weapon with this strength to fight.
     */
    public boolean isSelectedUserHavingWeapons() {
        Optional<Weapon> optionalWeapon = session.getSelectedWeapons().stream().filter(weapon -> session.getSelected().getStrength() > weapon.getStrength())
                .findFirst();
        return optionalWeapon.isPresent();
    }

    /**
     * Method : ValidateRegistry
     * @param name
     * @return
     * this method helps to check if the valid names are presented at the time of registering the user.
     */
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

    /**
     * Method : validatePlayerLevel
     * @param input
     * @return Boolean
     * this methods helps to validate if user has right level of the context to use specific commands.
     */
    public boolean validatePlayerLevel(String input) {
        String entryLevelStr = patternProperties.getProperty(input + "_entry_level");
        Integer entryLevel = Integer.valueOf(entryLevelStr);
        if (entryLevel == 0 || entryLevel.intValue() == session.getCurrentStage().intValue())
            return true;
        else {
            System.out.println(BLUE_BOLD_BRIGHT + helpProperties.getProperty(COMMAND_NOT_ALLOWED));
            int i = 1;
            String commands = "";
            while (null != patternProperties.getProperty("pattern" + i)) {
                if (session.getCurrentStage().intValue() == Integer.parseInt(patternProperties.getProperty("pattern" + i + "_entry_level"))) {
                    commands = commands + " - " + patternProperties.getProperty("pattern" + i);
                }
                i++;
            }
            System.out.println(BLUE_BOLD_BRIGHT + helpProperties.getProperty(HINT_COMMAND_NOT_ALLOWED).replace("$$", commands));
            System.out.println(RESET);
            return false;
        }

    }

    /**
     * Method : checkIfRegisteredUser
     * this method helps to see if the user is registered or not with the application.
     */
    public void checkIfRegisteredUser() {
        if (session.getPlayerProfile() == null) {
            System.out.println(CYAN + helpProperties.getProperty(ENTRY_WELCOME));
        } else {
            CommandHelper commandHelper = new CommandHelper();
            System.out.println(CYAN + helpProperties.getProperty(WELCOME_BACK).replace("$$", session.getPlayerProfile().getPlayerName()));
            commandHelper.canUseCommands(true);
        }
    }
}
